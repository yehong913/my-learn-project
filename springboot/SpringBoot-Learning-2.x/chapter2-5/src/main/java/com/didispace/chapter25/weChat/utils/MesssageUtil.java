package com.didispace.chapter25.weChat.utils;




import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage.Item;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MesssageUtil {


    public   static   final  String  MESSAGE_NEWS="news";
    public   static   final  String  MESSAGE_TEXT="text";
    public   static   final  String  MESSAGE_IMAGE="image";
    public   static   final  String  MESSAGE_VOICE="vocie";
    public   static   final  String  MESSAGE_VIDEO="video";
    public   static   final  String  MESSAGE_LINK="link";
    public   static   final  String  MESSAGE_LOCATION="location";
    public   static   final  String  MESSAGE_EVENT="event";
    public   static   final  String  MESSAGE_SUBSCRIBE="subscribe";
    public   static   final  String  MESSAGE_UNSCRIBE="unsubscribe";
    public   static   final  String  MESSAGE_CLICK="CLICK";
    public   static   final  String  MESSAGE_VIDE="VIEW";



    /**
     * XML 转map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public  static  Map<String,String> xmltoMap(HttpServletRequest request) throws IOException, DocumentException {
       Map<String,String> map= new HashMap<String,String>();
        SAXReader  reader=new SAXReader();
        InputStream in = request.getInputStream();
        Document  document=  reader.read(in);
        Element  root=  document.getRootElement();
        List<Element> list=root.elements();
        for(Element e: list){
            map.put(e.getName(),e.getText());
        }
        return  map;
    }

    public  static  String textMessageToXml(TextMessage textMessage) throws IOException, DocumentException {
        XStream xStream=new XStream(new DomDriver("UTF-8"));
        xStream.alias("xml",TextMessage.class);
        xStream.aliasSystemAttribute("xml","class");
        return xStream.toXML(textMessage);
    }


    /**
     * 主菜单
     * @return
     */
    public  static String  menText(){
        StringBuilder  stringBuilder= new StringBuilder();
        stringBuilder.append("感谢您的关注，请按照以下菜单进行回复");
        stringBuilder.append("1:百度\n");
        stringBuilder.append("2:微信\n");
        stringBuilder.append("3:头条\n");
        return  stringBuilder.toString();
    }

    public  static String  firstMenu(){
        StringBuilder  stringBuilder= new StringBuilder();
        stringBuilder.append("感谢您的关注1baidu");
        return  stringBuilder.toString();
    }
    public  static String  secondMenu(){
        StringBuilder  stringBuilder= new StringBuilder();
        stringBuilder.append("感谢您的关注2222");
        return  stringBuilder.toString();
    }
    public  static String  thirdMenu(){
        StringBuilder  stringBuilder= new StringBuilder();
        stringBuilder.append("感谢您的关注333");
        return  stringBuilder.toString();
    }


    public  static String  errordMenu(){
        StringBuilder  stringBuilder= new StringBuilder();
        stringBuilder.append("未知引导");
        return  stringBuilder.toString();
    }

    /**
     * 首次关注进来的菜单
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String  initMenuText(String toUserName,String fromUserName,String content) throws IOException, DocumentException {

        TextMessage textMessage=new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setContent(content);
        textMessage.setCreateTime(String.valueOf(new Date().getTime()));
        textMessage.setMsgType("text");
        return MesssageUtil.textMessageToXml(textMessage);
    }


    public  static  String returnNews(String toUserTime,String fromToUser){
        WxMpXmlOutNewsMessage  wxMpXmlOutNewsMessage= new WxMpXmlOutNewsMessage();
        Item item= new Item();
        item.setUrl("http://www.baidu.com");
        item.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1577955125763&di=c1dfe62adb0d847768793471c7c123b4&imgtype=0&src=http%3A%2F%2Fm.tuniucdn.com%2Ffb2%2Ft1%2FG1%2FM00%2F80%2F7F%2FCii9EFcXdLCIH2y5ACfmhZIAZwoAADuCwJQsvcAJ-ad731_w700_h0_c0_t0.jpg");
        item.setDescription("描述测试");
        item.setTitle("消息头");
        wxMpXmlOutNewsMessage.addArticle(item);
        wxMpXmlOutNewsMessage.setArticleCount(1);
        wxMpXmlOutNewsMessage.setCreateTime( new Date().getTime());
        wxMpXmlOutNewsMessage.setMsgType(MESSAGE_NEWS);
        wxMpXmlOutNewsMessage.setFromUserName(toUserTime);
        wxMpXmlOutNewsMessage.setToUserName(fromToUser);
        return wxMpXmlOutNewsMessage.toXml();
    }
}
