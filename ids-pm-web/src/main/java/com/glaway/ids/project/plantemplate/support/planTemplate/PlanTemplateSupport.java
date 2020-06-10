/*
 * 文件名：PlanTemplateSupport.java 版权：Copyright by www.glaway.com 描述： 修改人：Administrator
 * 修改时间：2015年3月23日 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.project.plantemplate.support.planTemplate;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.pbmn.activity.entity.TaskActityInfo;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.models.JsonResult;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.utils.SupportFlagConstants;
import com.glaway.ids.util.mpputil.MppConstants;
import com.glaway.ids.util.mpputil.MppDirector;
import com.glaway.ids.util.mpputil.MppInfo;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.writer.ProjectWriter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.glaway.foundation.common.log.BaseLog;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.ids.common.pbmn.activity.ActivitiI;
import com.glaway.ids.models.JsonRequery;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * 〈计划模板对外的接口〉 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月23日
 * @see PlanTemplateSupport
 * @since
 */
@Controller
@RequestMapping("/planTemplateSupport")
public class PlanTemplateSupport {
    private static final BaseLog log = BaseLogFactory.getSystemLog(PlanTemplateSupport.class);

    @Autowired
    private PlanTemplateRemoteFeignServiceI planTemplateService;

    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;


    private static Map<String, Object> activityMap = new HashMap<String, Object>();

/*    static {
        activityMap.put(SupportFlagConstants.APPROVE, new StartActivity()); // 开启流程
        activityMap.put(SupportFlagConstants.BACK_APPROVE, new BackStartActivity()); // 返回后开启流程
    }*/

    /**
     * Description: 查询计划模板列表
     * 
     * @param request
     * @param response
     * @return
     * @see
     */


    @RequestMapping(params = "doDel")
    @ResponseBody
    public JsonResult doDel(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") // 时间转化为特定格式
        .setPrettyPrinting() // 对json结果格式化
        .create();

        // 默认返回成功的数据
        JsonResult jsonResult = new JsonResult();

        // 获得传入的参数
        String jsonStr = (String)request.getParameter("data");
        JsonRequery req = gson.fromJson(jsonStr, JsonRequery.class);
        PlanTemplateReq planTemplateReq = gson.fromJson(req.getReqObj().toString(),
            PlanTemplateReq.class);
        //状态字段变换
        if("启用".equals(planTemplateReq.getStatus())){
            planTemplateReq.setBizCurrent("qiyong");
        }
        else if("禁用".equals(planTemplateReq.getStatus())){
            planTemplateReq.setBizCurrent("jinyong");
        }
        
        try {
            if (StringUtils.isEmpty(planTemplateReq.getId())) {
                jsonResult.setRetCode("Y0001");
                jsonResult.setRetMsg("操作计划模板,id为空！");
                return jsonResult;
            }

            if (StringUtils.isEmpty(planTemplateReq.getSupportFlag())) {
                jsonResult.setRetCode("Y0002");
                jsonResult.setRetMsg("操作计划模板,没有提交操作标识！");
                return jsonResult;
            }

            if (SupportFlagConstants.DELETE.equals(planTemplateReq.getSupportFlag())) {
                String sessionId = request.getHeader("sessionId");
                planTemplateService.deletePlanTemplate(planTemplateReq, UserUtil.getUserInfo(sessionId));
            }
            else if (SupportFlagConstants.UPDATE.equals(planTemplateReq.getSupportFlag())) {
                String sessionId = request.getHeader("sessionId");
                planTemplateService.updatePlanTemplate(planTemplateReq, UserUtil.getUserInfo(sessionId));
            }
            else if (SupportFlagConstants.APPROVE.equals(planTemplateReq.getSupportFlag())
                     || SupportFlagConstants.BACK_APPROVE.equals(planTemplateReq.getSupportFlag())) {
                ActivitiI activitiI = (ActivitiI)activityMap.get(planTemplateReq.getSupportFlag());
                if(SupportFlagConstants.BACK_APPROVE.equals(planTemplateReq.getSupportFlag())){
                    TaskActityInfo info = new TaskActityInfo();
                    //PropertyUtils.copyProperties(info, planTemplateReq);
                    activitiI.approve(planTemplateReq);
                }else{
                    activitiI.approve(planTemplateReq);
                }
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            jsonResult.setRetCode("X0001");
            String retMsg = "";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                retMsg = e.getMessage();
            }
            jsonResult.setRetMsg(retMsg);
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * Description: <br>
     * 导入计划模板
     * 
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "importPlanTemplate")
    @ResponseBody
    public JsonResult importPlanTemplate(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") // 时间转化为特定格式
        .setPrettyPrinting() // 对json结果格式化
        .create();

        // 默认返回成功的数据
        JsonResult jsonResult = new JsonResult();

        // 获得传入的参数
        String jsonStr = (String)request.getParameter("data");
        JsonRequery req = gson.fromJson(jsonStr, new TypeToken<JsonRequery>() {}.getType());
        PlanTemplateReq planTemplateReq = gson.fromJson(req.getReqObj().toString(),
            PlanTemplateReq.class);
        InputStream inputstream = null;
        try {
            PlanTemplateDto planTemplate = new PlanTemplateDto();
            if (StringUtils.isEmpty(planTemplateReq.getName())) {
                jsonResult.setRetCode("Y0001");
                jsonResult.setRetMsg("模板名称不能为空！");
                return jsonResult;
            }
            else {
                planTemplate.setName(planTemplateReq.getName());
            }
            if (!StringUtils.isEmpty(planTemplateReq.getRemark())) {
                planTemplate.setRemark(planTemplateReq.getRemark());
            }
           /* planTemplate.setCreateBy(UserUtil.getUserInfo(request.getHeader("sessionId")));*/
            planTemplate.setCreateBy(planTemplateReq.getCreator());
            planTemplate.setFirstBy(planTemplateReq.getFirstBy());
            planTemplate.setFirstFullName(planTemplateReq.getFirstFullName());
            planTemplate.setFirstName(planTemplateReq.getFirstName());
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                jsonResult.setRetCode("X0003");
                jsonResult.setRetMsg("导入计划模板MPP文件失败！");
                return jsonResult;
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            CommonsMultipartFile file = (CommonsMultipartFile)multipartRequest.getFile("file");
            inputstream = file.getInputStream();
            MppDirector mppDirector = new MppDirector();
            List<Task> taskList = mppDirector.construct(inputstream);
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("planTemplate",planTemplate);
            paramMap.put("taskList",taskList);
            paramMap.put("curUserDto", ResourceUtil.getCurrentUser());
            paramMap.put("orgId", ResourceUtil.getCurrentUserOrg().getId());
            FeignJson fJson = planTemplateService.savePlanTemplateDetail(paramMap);
            planTemplateReq.setId(planTemplate.getId());
            jsonResult.setRetObj(planTemplateReq);
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            jsonResult.setRetCode("X0001");
            jsonResult.setRetMsg(e.getMessage());
            return jsonResult;
        }
        finally {
            try {
            	if(inputstream!=null){
            	    inputstream.close();  
            	}
            }
            catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * Description: <br>
     * 导入计划模板
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @see
     */
    @RequestMapping(params = "exportMpp")
    @ResponseBody
    public void exportMpp(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") // 时间转化为特定格式
        .setPrettyPrinting() // 对json结果格式化
        .create();
        // 获得传入的参数
        String jsonStr = (String)request.getParameter("data");
        JsonRequery req = gson.fromJson(jsonStr, new TypeToken<JsonRequery>() {}.getType());
        PlanTemplateReq planTemplateReq = gson.fromJson(req.getReqObj().toString(),
            PlanTemplateReq.class);
        ProjectWriter writer = new MSPDIWriter();
        try {
            List<MppInfo> mppList = planTemplateService.saveMppInfo(planTemplateReq);
            MppDirector mppDirector = new MppDirector();
            ProjectFile projectFile = mppDirector.getMppFile(MppConstants.IDS_PLANTEMPLATE_MPP,
                mppList,null);
            writer.write(projectFile, response.getOutputStream());
        }
        catch (Exception e) {
            log.warn(e.getMessage());
        }

    }
}
