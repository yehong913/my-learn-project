package com.glaway.ids.project.plan.controller;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PlanLogDto;
import com.glaway.ids.project.plan.form.PlanLogInfo;
import com.glaway.ids.project.plan.form.PlanLogInfoRsp;
import com.glaway.ids.project.plan.service.PlanLogRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;


/**
 * @Title: Controller
 * @Description: 项目计划日志记录
 * @author duanpengfei
 * @date 2015-03-27 15:08:40
 * @version V1.0
 */
@Controller
@RequestMapping("/planLogController")
public class PlanLogController extends BaseController {
    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanLogController.class);

    @Autowired
    private PlanLogRemoteFeignServiceI planLogService;

    @Autowired
    private PlanRemoteFeignServiceI planService;

    @Autowired
    private FeignUserService userService;
    
    /**
     * 项目计划页面初始化时获取计划记录列表
     * 
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    public void list(PlanLogInfo planLogInfo, HttpServletRequest request,
                     HttpServletResponse response) {
//        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.list");
        String message = "";
        // planLogInfo.setPlanId("4028efe54d1d01f9014d1d0415470004");
        PlanLogInfoRsp rsp = new PlanLogInfoRsp();
        try {
            List<PlanLogDto> planLogList = planLogService.findPlanLogByPlanId(planLogInfo, 1, 10,
                false);
            rsp = makeList(planLogList);
        }
        catch (Exception e) {
            message = e.getMessage();
            log.error(message, e, null, message);
            Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }

        String json = JsonUtil.getListJsonWithoutQuote(rsp.getRows());
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * Description: <br>
     * 计划记录数据组装<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param planLogList
     * @return
     * @throws GWException
     * @see
     */
    private PlanLogInfoRsp makeList(List<PlanLogDto> planLogList)
        throws GWException {
        PlanLogInfoRsp rsp = new PlanLogInfoRsp();
        List<PlanLogInfo> items = new ArrayList<PlanLogInfo>();
        try {
            for (PlanLogDto planLog : planLogList) {
                PlanLogInfo item = new PlanLogInfo();
                PropertyUtils.copyProperties(item, planLog);
                if (planLog.getCreateBy() != null) {
                    TSUserDto userDto =  userService.getUserByUserId(planLog.getCreateBy());
                    item.setCreateName(userDto.getRealName() + "-"
                                       + userDto.getUserName());
                }
                else {
                    item.setCreateName("");
                }

                if (StringUtils.isEmpty(planLog.getRemark())) {
                    item.setRemark("");
                }
                if (StringUtils.isEmpty(planLog.getFilePath())) {
                    item.setFilePath("");
                }
                String createTimeStr = DateUtil.dateToString(planLog.getCreateTime(),
                    DateUtil.FORMAT_ONE);
                item.setCreateTimeStr(createTimeStr);
                items.add(item);
            }
            rsp.setRows(items);
        }
        catch (Exception e) {
//            log.error(I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.makeList"), e);
//            throw new GWException(I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.makeList"));
        }
        return rsp;
    }

    @RequestMapping(params = "savePlanSplitLog")
    @ResponseBody
    public String savePlanSplitLog(String planId) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.savePlanSplitLogSuccess");
        try {
//            PlanDto plan = planService.getPlanEntity(planId);
//            plan.setOpContent(PlanConstants.PLAN_LOGINFO_SPLIT);
            planService.updateOpContentByPlanId(UserUtil.getCurrentUser(),planId,PlanConstants.PLAN_LOGINFO_SPLIT);
            PlanLogDto planLog = new PlanLogDto();
            planLog.setPlanId(planId);
            planLog.setLogInfo(PlanConstants.PLAN_LOGINFO_SPLIT);
            TSUserDto user = UserUtil.getInstance().getUser();
            planLog.setCreateBy(user.getId());
            planLog.setCreateName(user.getUserName());
            planLog.setCreateFullName(user.getRealName());
            planLog.setCreateTime(new Date());
            planLogService.savePlanLog(planLog);
            
            
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.savePlanSplitLogFail");
            log.error(message, e, null, message);
            Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return "";
    }

    @RequestMapping(params = "saveLogWithFlowResolve")
    @ResponseBody
    public String saveLogWithFlowResolve(String planId) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.saveLogWithFlowResolveSuccess");
        try {
            PlanDto plan = planService.getPlanEntity(planId);
            plan.setOpContent(PlanConstants.PLAN_LOGINFO_FLOW_SPLIT);
            planService.updateOpContentByPlanId(UserUtil.getCurrentUser(),planId, PlanConstants.PLAN_LOGINFO_FLOW_SPLIT);
            PlanLogDto planLog = new PlanLogDto();
            planLog.setPlanId(planId);
            planLog.setLogInfo(PlanConstants.PLAN_LOGINFO_FLOW_SPLIT);
            TSUserDto user = UserUtil.getInstance().getUser();
            planLog.setCreateBy(user.getId());
            planLog.setCreateName(user.getUserName());
            planLog.setCreateFullName(user.getRealName());
            planLog.setCreateTime(new Date());
            planLogService.savePlanLog(planLog);

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.planLog.saveLogWithFlowResolveFail");
            log.error(message, e, null, message);
            Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return "";
    }
    
    /**
     * Description: <br>
     * 下载文件附件<br>
     * Implement: <br>
     * <br>
     * 
     * @param request
     * @param response
     * @return 返回
     * @see
     */
    @RequestMapping(params = "downFile")
    @ResponseBody
    public void downFile(PlanLogDto planLog, HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            planLog = planLogService.getPlanLogEntity(planLog.getId());
            is = JackrabbitUtil.downLoadFile(planLog.getFilePath());
            String contentType = "application/x-download";
            String fileName = "";
            String[] filePathArr = planLog.getFilePath().split("/");
            int pathArrLength = filePathArr.length;
            if(pathArrLength>0){
                fileName = filePathArr[pathArrLength-1];
                int len1 = fileName.lastIndexOf("_");
                int len2 = fileName.lastIndexOf(".");
                fileName = fileName.substring(0, len1) + fileName.substring(len2, fileName.length());
            }
            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            out = response.getOutputStream();
            byte[] bytes = new byte[0xffff];
            int b = 0;
            while ((b = is.read(bytes, 0, 0xffff)) > 0) {
                out.write(bytes, 0, b);
            }
            is.close();
            out.flush();
        }
        catch (Exception e) {
            throw new GWException(e);
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception e) {
                throw new GWException(e);
            }
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (Exception e) {
                throw new GWException(e);
            }
        }
    }
}
