package com.didispace.chapter25.weChat.controller;


import com.didispace.chapter25.weChat.utils.MesssageUtil;
import com.didispace.chapter25.weChat.utils.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;


import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/weixin/{appid}")
public class weChatController {
    @Autowired
    private  final WxMpService wxService;

    @GetMapping(produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String authGet(@PathVariable String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return   "非法请求";
    }

    @PostMapping
    @ResponseBody
    public void authPost(HttpServletRequest request, HttpServletResponse response){
        PrintWriter  pw=null;
        TextMessage  textMessage=null;
        response.setCharacterEncoding("UTF-8");
        String xml=null;
        try {
           pw=response.getWriter();
           Map<String,String> map= MesssageUtil.xmltoMap(request);
            String  toUserName=map.get("ToUserName");
            String  fromUserName=map.get("FromUserName");
            String createTime=map.get("CreateTime");
            String msgType=map.get("MsgType");
            String content=map.get("Content");
            String msgId=map.get("MsgId");
            log.info(toUserName);
            if(MesssageUtil.MESSAGE_TEXT.equals(msgType)){
                if("1".equals(content)){
                    xml=MesssageUtil.initMenuText(toUserName,fromUserName,MesssageUtil.firstMenu());
                }else if("2".equals(content)){
                    xml=MesssageUtil.initMenuText(toUserName,fromUserName,MesssageUtil.secondMenu());
                }else if("3".equals(content)){
                    xml=MesssageUtil.initMenuText(toUserName,fromUserName,MesssageUtil.thirdMenu());
                }else if("news".equals(content)){
                    xml=MesssageUtil.returnNews(toUserName,fromUserName);
                }else{
                    xml=MesssageUtil.returnNews(toUserName,fromUserName);
                }
            }else if(MesssageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType=map.get("Event");
                if(MesssageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                    xml =MesssageUtil.initMenuText(toUserName,fromUserName,MesssageUtil.menText());
                }else if("VIEW".equals(eventType)){
                    String   reUrl=map.get("EventKey");
                    xml =MesssageUtil.initMenuText(toUserName,fromUserName,reUrl);
                }else if("location_select".equals(eventType)){
                    String key=map.get("lable");
                    xml =MesssageUtil.initMenuText(toUserName,fromUserName,key);
                }else if("CLICK".equals(eventType)){
                    String key=map.get("lable");
                    xml =MesssageUtil.initMenuText(toUserName,fromUserName,MesssageUtil.menText());
                }
            }
            pw.write(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pw.close();
        }

    }




}
