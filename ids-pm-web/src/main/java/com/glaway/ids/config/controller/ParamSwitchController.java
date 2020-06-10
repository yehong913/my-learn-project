package com.glaway.ids.config.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.ids.config.dto.ParamSwitchDto;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;


/**
 * @Title: Controller
 * @Description: 计划参数开关
 * @author wqb
 * @date 2019年7月29日 13:39:29
 * @version V1.0
 */
@Controller
@RequestMapping("/paramSwitchController")
public class ParamSwitchController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ParamSwitchController.class);

    /**
     * 项目计划参数接口
     */
    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

//    /**
//     * 配置业务接口
//     */
//    @Autowired
//    private PlanBusinessConfigServiceI businessConfigService;

    /**
     * 计划参数开关列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "paramSwitch")
    public ModelAndView paramSwitch(HttpServletRequest request) {
        return new ModelAndView("com/glaway/ids/pm/config/paramSwitchList");
    }

    /**
     * 获取参数列表
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridlist")
    @ResponseBody
    public void datagridlist(HttpServletRequest request, HttpServletResponse response) {
        List<ParamSwitchDto> list = paramSwitchService.search(new ParamSwitchDto());
        String datagridStr = JSON.toJSONString(list);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(datagridStr);
            pw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存
     * 
     * @param ids
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(String statusString, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.config.paramSwitch.saveSuccess");
        try {
            String[] ids = statusString.split("#");
            if (ids != null && ids.length > 0) {
                for (String idStatus : ids) {
                    String[] sp = idStatus.split(",");
                    String id = sp[0];
                    String status = "";
                    if (sp.length > 1) {
                        status = sp[1];
                    }
//                    ParamSwitch paramSwitch = paramSwitchService.getEntity(ParamSwitch.class, id);
//                    paramSwitch.setStatus(status);
//                    paramSwitchService.update(paramSwitch);
                    paramSwitchService.updateStatusById(status,id);
                    
                }
            }
            log.info(message, statusString, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.paramSwitch.saveError");
            log.error(message, e, statusString, "");
            Object[] params = new Object[] {message,
                ParamSwitchDto.class.getClass() + " oids:" + statusString};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

}
