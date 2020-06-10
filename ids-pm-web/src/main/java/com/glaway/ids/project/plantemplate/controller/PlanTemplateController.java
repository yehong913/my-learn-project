package com.glaway.ids.project.plantemplate.controller;


import static org.springframework.web.util.UriUtils.encode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.BrowserUtils;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.POIExcelUtil;
import com.glaway.foundation.common.util.RequestMapUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.util.StringUtil;
import com.glaway.foundation.common.util.TimeUtil;
import com.glaway.foundation.common.util.UUIDGenerator;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.common.util.param.ExcelVo;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.common.UploadFile;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.activiti.FeignActivitiCommonService;
import com.glaway.foundation.fdk.dev.service.calendar.FeignCalendarService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.CommonConstants;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.BpmnConstants;
import com.glaway.ids.constant.NameStandardSwitchConstants;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectStatusConstants;
import com.glaway.ids.constant.SwitchConstants;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.NameStandardDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PreposePlanDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.DeliveryStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.InputsRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.NameStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PreposePlanRemoteFeignServiceI;
import com.glaway.ids.project.plan.vo.TemplatePlanTreeNode;
import com.glaway.ids.project.plantemplate.constant.PlantemplateConstant;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateRspItem;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateExcelVo;
import com.glaway.ids.project.plantemplate.utils.SupportFlagConstants;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.util.mpputil.MppConstants;
import com.glaway.ids.util.mpputil.MppDirector;
import com.glaway.ids.util.mpputil.MppInfo;
import com.glaway.ids.util.mpputil.MppParseUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jodd.servlet.URLDecoder;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.writer.ProjectWriter;


/**
 * @Title: Controller
 * @Description: 项目计划模板列表
 * @author zhousuxia
 * @date 2015-03-20 15:40:03
 * @version V1.0
 */

@Controller
@RequestMapping("/planTemplateController")
public class PlanTemplateController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanTemplateController.class);

    @Autowired
    private SessionFacade sessionFacade;
    
    /**
     * redis缓存服务
     */
    @Autowired
    private RedisService redisService;


    @Autowired
    private FeignCalendarService calendarService;

    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardService;

    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleRemoteFeignServiceI;
    
    /**
     * 配置业务接口
     */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;


    @Autowired
    private PlanTemplateRemoteFeignServiceI planTemplateService;


    @Autowired
    private PlanTemplateDetailRemoteFeignServiceI planTemplateDetailService;

    @Autowired
    private InputsRemoteFeignServiceI inputsRemoteFeignServiceI;


    @Autowired
    private FeignActivitiCommonService activitiCommonService;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;


    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanTemplateService;


    @Autowired
    private FeignUserService userService;


    @Autowired
    private FeignSystemService feignSystemService;


    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;


    @Autowired
    private PlanRemoteFeignServiceI planService;

    /**
     * 计划前置接口
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;

    @Autowired
    private InputsRemoteFeignServiceI inputsService;


    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;


    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;

    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 项目计划模板列表列表 页面跳转
     *  @Author zhousuxia
     * @return
     */
    @RequestMapping(params = "planTemplate")
    public ModelAndView planTemplate(HttpServletRequest request) {
        String userId = UserUtil.getInstance().getUser().getId();
        request.setAttribute("currentUserId", userId);
        //防止和其他页面冲突
        request.setAttribute("entry", "PlanTemp");
        if (StringUtils.isNotEmpty(request.getParameter("isDialog"))) {
            return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateList-dialog");
        }
        else {
            return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateList");
        }
    }



    /**
     * 获取生命周期状态
     * @Author zhousuxia
     * @param request
     * @param response
     */
    @RequestMapping(params = "lifeCycleStatus")
    public void lifeCycleStatus(HttpServletRequest request, HttpServletResponse response) {

        FeignJson fj = planTemplateService.getLifeCycleStatusList();
        List<LifeCycleStatus> lifeCycleStatus = JSON.parseArray(String.valueOf(fj.getObj()),LifeCycleStatus.class);
        List<LifeCycleStatus> lifeCycleStatusSorted = new ArrayList<LifeCycleStatus>();
        LifeCycleStatus[] sorted = new LifeCycleStatus[lifeCycleStatus.size()];
        for (LifeCycleStatus life : lifeCycleStatus) {
            int i = life.getOrderNum();
            sorted[i] = life;
        }
        for (int i = 0; i < sorted.length; i++ ) {
            lifeCycleStatusSorted.add(sorted[i]);
        }

        JsonConfig jc = new JsonConfig();
        jc.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray ja = JSONArray.fromObject(lifeCycleStatusSorted, jc);
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw = response.getWriter();
            pw.write(ja.toString());
            pw.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量删除项目计划模板列表
     * @Author zhousuxia
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doBatchDelSuccess");
        try {
            PlanTemplateReq planTemplateReq = new PlanTemplateReq();
            planTemplateReq.setId(ids);
            planTemplateReq.setSupportFlag(SupportFlagConstants.DELETE);

            planTemplateService.deletePlanTemplate(planTemplateReq, ResourceUtil.getCurrentUser().getId());

            /*Properties prop = PropertiesUtil.getProperties(SupportConstants.IDS_SUPPORT_ADDRESS);
            JsonResult jsonResult;
            String httpUrl = "http://" + request.getServerName() + ":" + request.getLocalPort()
                    + request.getContextPath();
            jsonResult = HttpClientUtil.httpClientPostByObject(
                    httpUrl + prop.getProperty(SupportConstants.IDS_PLANTEMPLATE_DELETE_URL),
                    planTemplateReq, null);
            // message = jsonResult.getRetMsg();
            // log.info(message, ids, "");
            if (!"0".equals(jsonResult.getRetCode())) {
                // message = jsonResult.getRetMsg();
                log.error(jsonResult.getRetMsg());

            } else {
                log.info(message);
            }*/
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doBatchDelFail");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                    PlanTemplateDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    @RequestMapping(params = "conditionSearch")
    @ResponseBody
    public void conditionSearch(HttpServletRequest req, HttpServletResponse response) {
        String isDialog = req.getParameter("isDialog");

        Map<String, String> params = new HashMap<String, String>();
        if (StringUtil.isNotEmpty(req.getParameter("name"))) {
            String name = req.getParameter("name");
            params.put("name", name);
        }
        if (StringUtil.isNotEmpty(req.getParameter("createName"))) {
            String createName = req.getParameter("createName");
            params.put("createName", createName);
        }
        List<ConditionVO> conditionLst = RequestMapUtil.getQueryCondition(req);
        if ("true".equals(isDialog)) {
            ConditionVO qiyong = new ConditionVO("PlanTemplate.bizCurrent", ",qiyong", null, null,
                    "eq");
            conditionLst.add(qiyong);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("conditionLst",conditionLst);
        map.put("params",params);
        PageList pageList = planTemplateService.queryEntity(map);
        long count = pageList.getCount();
        List<PlanTemplateRspItem> list = pageList.getResultList();
        String json = JsonUtil.getListJsonWithoutQuote(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }



    /**
     * 项目计划模板导出
     * @Author zhousuxia
     * @return
     */
    @RequestMapping(params = "doExport")
    public void doExport(HttpServletRequest req, HttpServletResponse response) {
        // 获得传入的参数
        response.setContentType("application/x-download");
        String codedFileName = null;
        OutputStream fOut = null;

        codedFileName =  PlantemplateConstant.XML_FILENAME;
        if (BrowserUtils.isIE(req)) {
            response.setHeader("content-disposition",
                    "attachment;filename=" + encode(codedFileName, "UTF-8")
                            + ".xml");
        }
        else {
            String newtitle = null;
            try {
                newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setHeader("content-disposition", "attachment;filename=" + newtitle + ".xml");
        }
        PlanTemplateReq planTemplateReq = new PlanTemplateReq();
        planTemplateReq.setId(req.getParameter("id"));
        planTemplateReq.setExportType(req.getParameter("type"));
        try {
           /* String httpUrl = "http://" + req.getServerName() + ":" + req.getLocalPort()
                    + req.getContextPath();
            inputStream = HttpClientUtil.httpClientPostStreamByObject(
                    httpUrl + prop.getProperty(SupportConstants.IDS_PLANTEMPLATE_EXPORT_URL),
                    planTemplateReq);
            response.setContentType("application/x-download");
            String fileName = PlantemplateConstant.XML_FILENAME;
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            OutputStream out = response.getOutputStream();
            byte[] bytes = new byte[0xffff];
            int b = 0;
            while ((b = inputStream.read(bytes, 0, 0xffff)) > 0) {
                out.write(bytes, 0, b);
            }
            inputStream.close();
            out.close();*/
            // 获得传入的参数
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
            try {
                fOut = response.getOutputStream();
                fOut.flush();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (fOut != null) {
                        fOut.close();
                    }
                }
                catch (Exception e) {
                    throw new GWException(e);
                }
                log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doExportSuccess"));
            }
           /* log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doExportSuccess"));*/
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doExportFail"), e);
        }

    }



    /**
     * 计划模板启用禁用
     * @Author zhousuxia
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doStatusChange")
    @ResponseBody
    public AjaxJson doStatusChange(HttpServletRequest req, HttpServletResponse response) {
        PlanTemplateReq planTemplateReq = new PlanTemplateReq();
        String status = URLDecoder.decode(URLDecoder.decode(req.getParameter("status"), "utf-8"));
        String planTemplateId = req.getParameter("planTemplateId");
        planTemplateReq.setStatus(status);
        planTemplateReq.setId(planTemplateId);
        planTemplateReq.setSupportFlag(SupportFlagConstants.UPDATE);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String jmessage = "";
        try {

            FeignJson fJson = planTemplateService.doStatusChange(planTemplateReq,planTemplateId,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            jmessage = fJson.getMsg();
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doStartFail");
            log.error(message, e);
            j.setSuccess(false);
        }
        finally {
            j.setMsg(status + jmessage);
            return j;
        }
    }



    /**
     * 提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goSubmitApproveForList")
    public ModelAndView goSubmitApproveForList(PlanTemplateDto planTemplate, HttpServletRequest req) {
        PlanTemplateDto t = planTemplateService.getPlanTemplateEntity(req.getParameter("planTemplateId"));
        if(!CommonUtil.isEmpty(t.getProcessInstanceId())){
            Map<String, Object> variables = new HashMap<String, Object>();
            FeignJson fJson = activitiCommonService.getAssignTaskIdByObjectIdForIDS(t.getId(),"startPlanTemplateProcess");
            String taskId = String.valueOf(fJson.getObj());
            variables = workFlowFacade.getWorkFlowCommonService().getVariablesByTaskId(taskId);
            Object leader = variables.get("leader");
            Object deptLeader = variables.get("deptLeader");
            Map<String, TSUserDto> userMap = userService.getAllUsersMap();
            req.setAttribute("leadersId", userMap.get(leader).getId());
            req.setAttribute("deptLeaderId", userMap.get(deptLeader).getId());
        }
        req.setAttribute("planTemplate_", t);
        req.setAttribute("supportFlag", "approve");
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-submitApproveForList");
    }


    /**
     * 提交审批
     *
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSubmitApprove")
    @ResponseBody
    public AjaxJson doSubmitApprove(HttpServletRequest req, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planTemplateId = req.getParameter("planTemplateId");
        String leader = req.getParameter("leader");
        String deptLeader = req.getParameter("deptLeader");
        PlanTemplateDto planTemplate = new PlanTemplateDto();
        planTemplate = planTemplateService.getPlanTemplateEntity(planTemplateId);
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveSuccess");

        try{

            // 1.获取设置map
            Map<String, Object> variables = setFlowMap(leader, deptLeader, planTemplate.getId(), planTemplate.getBizCurrent());
            // 2.获取业务
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("variables",variables);
            paramMap.put("planTemplate",planTemplate);
            paramMap.put("curUser",ResourceUtil.getCurrentUser());
            paramMap.put("orgId",ResourceUtil.getCurrentUserOrg().getId());

            planTemplateService.startPlanTemplateProcess(paramMap);
            // 3.保存日志
            planTemplateService.saveTemplateOptLog(planTemplate.getBizId(), PlantemplateConstant.PLAN_TEMPLATE_APPROVE,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            j.setObj(planTemplateId);
            log.info(message, planTemplateId, "");

        }catch(Exception e){
            System.out.println(e.getMessage());
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveFail");
            log.error(message, e, planTemplateId, "");
            Object[] params = new Object[] {message,
                    PlanTemplateDto.class.getClass() + " oids:" + planTemplateId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }finally{
            return j;
        }
    }


    /**
     * 获取工作流map信息
     *
     * @param leader
     * @return
     */
    private Map<String, Object> setFlowMap(String leader, String deptLeader,
                                           String id, String status) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("leader", leader);
        variables.put("assigner", UserUtil.getInstance().getUserName());
        variables.put("deptLeader", deptLeader);
        variables.put("editUrl",
                "/ids-pm-web/planTemplateDetailController.do?planTemplateDetailEdit&dataHeight=500&dataWidth=900&planTemplateId="
                        + id);
        variables.put("viewUrl",
                "/ids-pm-web/planTemplateDetailController.do?planTemplateDetail&dataHeight=500&dataWidth=900&planTemplateId="
                        + id);
        variables.put("oid", BpmnConstants.OID_PLANTEMPLATE + id);
        variables.put("status", status);
        variables.put("cancelEventListener",
                "com.glaway.foundation.activiti.task.common.CommonCancelListener");
        // 设置流程审批意见变量
        variables.put("desc", "");
        variables.put("userId", ResourceUtil.getCurrentUser().getId());
        return variables;
    }


    /**
     * 驳回提交审批
     *
     * @params
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBackSubmit")
    @ResponseBody
    public AjaxJson doBackSubmit(HttpServletRequest req, HttpServletResponse response) {
        PlanTemplateReq planTemplateReq = new PlanTemplateReq();
        String taskId = req.getParameter("taskId");
        String planTemplateId = req.getParameter("planTemplateId");
        PlanTemplateDto planTemplate = new PlanTemplateDto();
        planTemplate = planTemplateService.getPlanTemplateEntity(planTemplateId);
        planTemplateReq.setProcInstId(planTemplate.getProcessInstanceId());
        planTemplateReq.setId(planTemplateId);
        planTemplateReq.setApproveTaskId(taskId);
        planTemplateReq.setSupportFlag(SupportFlagConstants.BACK_APPROVE);
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveSuccess");
        try {
            planTemplateService.completePlanTemplateProcess(taskId,planTemplate,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            j.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doSubmitApproveFail");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 跳转计划模板新增页面
     *
     * @author zhousuxia
     * @version 2018年6月27日
     * @see PlanTemplateController
     * @since
     */
    @RequestMapping(params = "goAddPlanTemplate")
    public ModelAndView goAddPlanTemplate(HttpServletRequest request){
        if(!CommonUtil.isEmpty(request.getParameter("type")) && request.getParameter("type").equals("add")){
            request.setAttribute("planTemplateId", UUIDGenerator.generate());
        }else{
            request.setAttribute("planTemplateId", request.getParameter("planTemplateId"));
            PlanTemplateDto template = planTemplateService.getPlanTemplateEntity(request.getParameter("planTemplateId"));
            request.setAttribute("template_", template);
        }
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("flag", request.getParameter("type"));
        String url = "planTemplateController.do?getPlanList";
        request.setAttribute("url", url);
        return new ModelAndView(
                "com/glaway/ids/project/plan/planTemplateAdd");
    }


    @RequestMapping(params = "getPlanList")
    public void getPlanList(HttpServletRequest request, HttpServletResponse response) {

        String type = request.getParameter("type");

        List<JSONObject> data= new ArrayList<JSONObject>();
        String planTemplateId = request.getParameter("planTemplateId");
        List<PlanDto> detailList = new ArrayList<PlanDto>();
        if(type.equals("add")){
            if(!CommonUtil.isEmpty(request.getParameter("projectId"))){
                PlanDto plan = new PlanDto();
                plan.setProjectId(request.getParameter("projectId"));
                String flag = request.getParameter("flag");
                if("main".equals(flag)) {
                    detailList = planService.queryPlanListForTemplateTreegrid(plan);
                } else if("right".equals(flag)){
                    String planId = request.getParameter("planId");
                    plan.setId(planId);
                    detailList = planService.getPlanAllChildren(plan);
                }
                Map<String, List<InputsDto>> inputListMap = planService.getDetailInputsList(plan);
                Map<String,List<DeliverablesInfoDto>> deliListMap = new HashMap<String, List<DeliverablesInfoDto>>();
                deliListMap = planService.getDeliverableListMap();
                for(PlanDto p : detailList) {
                    if(!CommonUtil.isEmpty(inputListMap.get(p.getId()))) {
                        String inputStr = JSON.toJSONString(inputListMap.get(p.getId()));
                        p.setInputList(inputListMap.get(p.getId()));
                        redisService.setToRedis("INPUTSLIST", p.getId(), inputStr);
                    }
                    if(!CommonUtil.isEmpty(deliListMap.get(p.getId()))){
                        p.setDeliInfoList(deliListMap.get(p.getId()));
                    }
                }
//                Map<String,List<Inputs>> planInputsListMap = new HashMap<String, List<Inputs>>();
//                planInputsListMap = planService.getPlanInputsListMap();

                if(!CommonUtil.isEmpty(detailList)){
                    for(PlanDto p : detailList){

                        List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(p);
                        // 组装前置数据
                        StringBuffer preposeSb = new StringBuffer();
                        StringBuffer preposeIds = new StringBuffer();
//                        String preposeEndTime = "";
                        int k = 0;
                        for (PreposePlanDto preposePlan : preposePlanList) {
                            if (k > 0) {
                                preposeSb.append(",");
                                preposeIds.append(",");
                            }
                            PlanDto preposePlanDto = planService.getPlanEntity(preposePlan.getPreposePlanId());
                            preposeSb.append(preposePlanDto.getPlanName());
                            preposeIds.append(preposePlanDto.getId());
                            k++ ;
                        }
                        p.setPreposePlans(preposeSb.toString());
                        p.setPreposeIds(preposeIds.toString());

//                        if(!CommonUtil.isEmpty(planInputsListMap.get(p.getId()))){
//                            p.setInputList(planInputsListMap.get(p.getId()));
//                        }
                        if(!CommonUtil.isEmpty(deliListMap.get(p.getId()))){
                            p.setDeliInfoList(deliListMap.get(p.getId()));
                        }
                    }
                }
                String detailStr = JSON.toJSONString(detailList);
                redisService.setToRedis("TEMPLATEPLANLIST", planTemplateId, detailStr);
            }else{

                 String detaiStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
                detailList = JSON.parseArray(detaiStr,PlanDto.class);
            }
        }else{
            List<PlanTemplateDetailDto> tempDetailList = planTemplateService.getTemplatePlanDetailById(planTemplateId);
            /*try {
                FeignJson fj = planTemplateDetailService.getPlanTemplateOrProjTemplateDetailPreposes(planTemplateId, "");
                Map<String, String> detailIdPreposeMap = (Map<String, String>)fj.getObj();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            Map<String, String> detailIdPreposeIdMap = new HashMap<>();
            try{
                FeignJson fj = planTemplateDetailService.getPlanTemplateDetailPreposesId(planTemplateId);
                detailIdPreposeIdMap = (Map<String, String>)fj.getObj();
            }catch (Exception e){
                e.printStackTrace();
            }

            Map<String,List<InputsDto>> inputsListMap = planTemplateService.queryInputsListMap();

            if(!CommonUtil.isEmpty(tempDetailList)){
                for(PlanTemplateDetailDto del : tempDetailList){

                    redisService.setToRedis("INPUTSLIST", del.getId(), "");

                    DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                    deliverablesInfo.setUseObjectId(del.getId());
                    deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                    List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                            deliverablesInfo, 1, 10, false);

                    PlanDto plan = new PlanDto();
                    plan.setId(del.getId());
                    plan.setPlanName(del.getName());
                    plan.setMilestone(del.getMilestone());
                    plan.setPlanOrder(String.valueOf(del.getNum()));
                    plan.setParentPlanId(del.getParentPlanId());
                    plan.setPlanLevel(del.getPlanLevel());
                    plan.setPlanNumber(del.getPlanNumber());
                    plan.setStoreyNo(del.getStoreyNo());
                    plan.setPlanTemplateId(del.getPlanTemplateId());
                    plan.setWorkTime(del.getWorkTime());
                    plan.setRemark(del.getRemark());
                    plan.setDeliInfoList(deliverablesInfoList2);
                    plan.setPreposeIds(detailIdPreposeIdMap.get(del.getId()));
                    plan.setTaskNameType(del.getTaskNameType());
                    plan.setTabCbTemplateId(del.getTabCbTemplateId());

                    if(!CommonUtil.isEmpty(inputsListMap.get(del.getId()))){
                        plan.setInputList(inputsListMap.get(del.getId()));
                    }else{
                        String inputsStr = (String)redisService.getFromRedis("INPUTSLIST", del.getId());
                        List<InputsDto> inputsList = new ArrayList<>();
                        if(!CommonUtil.isEmpty(inputsStr)){
                            inputsList= JSON.parseArray(inputsStr,InputsDto.class);
                        }
                        plan.setInputList(inputsList);
                    }
                    detailList.add(plan);
                }
            }
            String detailStr = JSON.toJSONString(detailList);
            redisService.setToRedis("TEMPLATEPLANLIST", planTemplateId, detailStr);
        }

        if(!CommonUtil.isEmpty(detailList)){
            data = changePlansToJSONObjects(detailList);
        }

        String json = JSON.toJSONString(data);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 将计划list组装为树节点json
     * @Author zhousuxia
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjects(List<PlanDto> planList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        String projectId = "";

        Map<String, String> planLevelMap =  getPlanLevelMap();

        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                projectId = p.getProjectId();
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }
        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);

        FeignJson jsonF = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(jsonF.getObj());

        Map<String,String> deliverMap = new HashMap<String, String>();

        Map<String,String> planMap = new HashMap<String, String>();

        for(PlanDto pl : planList){
            planMap.put(pl.getId(), pl.getPlanName());
        }

        for (PlanDto p : planList) {


            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("projectId", p.getProjectId());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planNumber", p.getPlanNumber());
                root.put("optBtn", this.generateOptBtn(p));
                root.put("displayName", p.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getPlanName());
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.planNameFormat(p));
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", p.getPlanName());
                    root.put("planName", this.planNameFormat(p));
                }

                root.put("planLevelInfo",
                        p.getPlanLevel() == null ? "" : planLevelMap.get(p.getPlanLevel()));
                root.put("ownerInfo",
                        p.getOwnerInfo() == null ? "" : p.getOwnerInfo().getRealName() + "-"
                                + p.getOwnerInfo().getUserName());

                root.put("planStartTime",
                        DateUtil.dateToString(p.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("planEndTime",
                        DateUtil.dateToString(p.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));

                root.put("assignerInfo",
                        p.getAssignerInfo() == null ? "" : p.getAssignerInfo().getRealName() + "-"
                                + p.getAssignerInfo().getUserName());

                root.put("assignTime",
                        DateUtil.dateToString(p.getAssignTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("workTime", p.getWorkTime());

                String preposePlans = "";

                if(!CommonUtil.isEmpty(p.getPreposeIds())){
                    if(CommonUtil.isEmpty(p.getProjectId())){
                        for(String preposeId : p.getPreposeIds().split(",")){
                            if(CommonUtil.isEmpty(preposePlans)){
                                preposePlans = planMap.get(preposeId);
                            }else{
                                preposePlans = preposePlans+","+planMap.get(preposeId);
                            }
                        }
                    }else{
                        preposePlans = p.getPreposePlans();
                    }

                }

                root.put("preposePlans", preposePlans);

                root.put("milestone", "true".equals(p.getMilestone()) ? "是" : "否");

                root.put("createBy", p.getCreateBy());
                root.put("owner", p.getOwner());
                root.put("parent_Id", p.getParentPlan() == null ? "" : p.getParentPlan().getId());
                root.put("isCreateByPmo", p.getIsCreateByPmo());
                root.put("flowStatus", p.getFlowStatus());
                root.put("bizCurrent", p.getBizCurrent());
                root.put("parent_owner",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getOwner());
                root.put("parent_createBy",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getCreateBy());
                root.put("parent_flowStatus",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getFlowStatus());
                root.put("parent_bizCurrent",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getBizCurrent());
                root.put("project_bizCurrent",
                        p.getProject() == null ? "" : p.getProject().getBizCurrent());
                root.put("opContent", p.getOpContent());

                root.put("creator", p.getCreator() == null ? "" : p.getCreator().getRealName()
                        + "-"
                        + p.getCreator().getUserName());
                root.put("createTime",
                        DateUtil.dateToString(p.getCreateTime(), DateUtil.LONG_DATE_FORMAT));

                root.put("flowStatus", p.getFlowStatus());
                root.put("result", p.getResult());

                // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                root.put("actualEndTime",
                        DateUtil.dateToString(p.getActualEndTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("invalidTime",
                        DateUtil.dateToString(p.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("taskNameType", p.getTaskNameType());
                root.put("taskType", p.getTaskType());
                root.put("planType", p.getPlanType());

                String inputs = "";
                if(!CommonUtil.isEmpty(p.getInputList())){
                    for(InputsDto inp : p.getInputList()){
                        if(CommonUtil.isEmpty(inputs)){
                            inputs = inp.getName();
                        }else{
                            inputs = inputs+","+inp.getName();
                        }
                    }
                }
                root.put("inputs", inputs);
              /*  DeliverablesInfo deliverablesInfo = new DeliverablesInfo();
                deliverablesInfo.setUseObjectId(p.getId());
                deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                List<DeliverablesInfo> deliverablesInfos = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);*/
                List<DeliverablesInfoDto> deliverablesInfos = p.getDeliInfoList();


                String delivaryName = "";
                if(!CommonUtil.isEmpty(deliverablesInfos)){
                    for(DeliverablesInfoDto d :deliverablesInfos){
                        if(CommonUtil.isEmpty(delivaryName)){
                            delivaryName = d.getName();
                        }else{
                            delivaryName = delivaryName +","+d.getName();
                        }
                    }
                }



                root.put("delivaryName", delivaryName);

                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPid(parentPlanIds, planList, rootList.get(i), types,
                    warningDay);
        }
        return rootList;
    }


    private Map<String,String> getPlanLevelMap(){
        Map<String, String> planLevelMap = new HashMap<String, String>();
        BusinessConfig businessConfig = new BusinessConfig();
        businessConfig.setConfigType(ConfigTypeConstants.PLANLEVEL);
        businessConfig.setStopFlag(ConfigStateConstants.START);
        businessConfig.setAvaliable("1");
        List<BusinessConfig> planLevelConfigs = businessConfigService.searchUseableBusinessConfigs(businessConfig);
   //     List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getId(),confog.getName() );
        }
        return planLevelMap;
    }


    /**
     * 构造计划树操作栏操作按钮
     * @Author zhousuxia
     * @param detail
     * @return
     * @see
     */
    private String generateOptBtn(PlanDto detail) {
        String returnStr = "";
        String modifyBtnStr = "<a class='basis ui-icon-pencil' style='display:inline-block;cursor:pointer;' onClick='modifyPlanOnTree_(\""
                + detail.getId() + "\",\""
                + detail.getParentPlanId() + "\")' title='修改'></a>";

        String deleteBtnStr = "<a class='basis ui-icon-minus' style='display:inline-block;cursor:pointer;' onClick='deleteOnTree_(\""
                + detail.getId() + "\")' title='删除'></a>";
        returnStr = returnStr + modifyBtnStr + deleteBtnStr;
        return returnStr;
    }


    private String planNameFormat(PlanDto detail){
        return "<a href='#' onclick=\"viewPlan_(\'" + detail.getId()
                + "\')\" style='color:blue'>" + detail.getPlanName() + "</a>";
    }


    /**
     * Description:递归查询获取所有子节点
     * @Author zhousuxia
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                 JSONObject parentObject,
                                 List<TSTypeDto> types,
                                 String warningDay) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
//        Map<String,String> deliverMap = new HashMap<String, String>();

        Map<String, String> planLevelMap = getPlanLevelMap();

        Map<String,String> planMap = new HashMap<String, String>();

        for(PlanDto pl : planList){
            planMap.put(pl.getId(), pl.getPlanName());
        }


        for (PlanDto plan : planList) {


            if (!CommonUtil.isEmpty(plan.getParentPlanId()) && pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planNumber", plan.getPlanNumber());
                newNode.put("optBtn", this.generateOptBtn(plan));
                newNode.put("displayName", plan.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", plan.getPlanName());
                    treeNode.put("image", "folder.gif");
                    newNode.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.planNameFormat(plan));
                    treeNode1.put("image", "folder.gif");
                    newNode.put("planName", treeNode1);
                }
                else {
                    newNode.put("displayNameNode", plan.getPlanName());
                    newNode.put("planName", this.planNameFormat(plan));
                }

                newNode.put("planLevelInfo",
                        plan.getPlanLevel() == null ? "" : planLevelMap.get(plan.getPlanLevel()));
                newNode.put("ownerInfo",
                        plan.getOwnerInfo() == null ? "" : plan.getOwnerInfo().getRealName() + "-"
                                + plan.getOwnerInfo().getUserName());
                newNode.put("planStartTime",
                        DateUtil.dateToString(plan.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("planEndTime",
                        DateUtil.dateToString(plan.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("assignerInfo",
                        plan.getAssignerInfo() == null ? "" : plan.getAssignerInfo().getRealName()
                                + "-"
                                + plan.getAssignerInfo().getUserName());
                newNode.put("assignTime",
                        DateUtil.dateToString(plan.getAssignTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("workTime", plan.getWorkTime());

                String preposePlans = "";
                if(!CommonUtil.isEmpty(plan.getPreposeIds())){
                    if(CommonUtil.isEmpty(plan.getProjectId())){
                        for(String preposeId : plan.getPreposeIds().split(",")){
                            if(CommonUtil.isEmpty(preposePlans)){
                                preposePlans = planMap.get(preposeId);
                            }else{
                                preposePlans = preposePlans+","+planMap.get(preposeId);
                            }
                        }
                    }else{
                        preposePlans = plan.getPreposePlans();
                    }

                }

                newNode.put("preposePlans", preposePlans);

                newNode.put("milestone", "true".equals(plan.getMilestone()) ? "是" : "否");

                newNode.put("createBy", plan.getCreateBy());
                newNode.put("owner", plan.getOwner());
                newNode.put("parent_Id",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getId());
                newNode.put("isCreateByPmo", plan.getIsCreateByPmo());
                newNode.put("flowStatus", plan.getFlowStatus());
                newNode.put("bizCurrent", plan.getBizCurrent());
                newNode.put("parent_owner",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getOwner());
                newNode.put("parent_createBy",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getCreateBy());
                newNode.put("parent_flowStatus",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getFlowStatus());
                newNode.put("parent_bizCurrent",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getBizCurrent());
                newNode.put("project_bizCurrent",
                        plan.getProject() == null ? "" : plan.getProject().getBizCurrent());
                newNode.put("opContent", plan.getOpContent());

                newNode.put("creator",
                        plan.getCreator() == null ? "" : plan.getCreator().getRealName() + "-"
                                + plan.getCreator().getUserName());
                newNode.put("createTime",
                        DateUtil.dateToString(plan.getCreateTime(), DateUtil.LONG_DATE_FORMAT));

                newNode.put("flowStatus", plan.getFlowStatus());
                newNode.put("result", plan.getResult());

                // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                newNode.put("actualEndTime",
                        DateUtil.dateToString(plan.getActualEndTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("invalidTime",
                        DateUtil.dateToString(plan.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("taskNameType", plan.getTaskNameType());
                newNode.put("taskType", plan.getTaskType());
                newNode.put("planType", plan.getPlanType());

                String inputs = "";
                if(!CommonUtil.isEmpty(plan.getInputList())){
                    for(InputsDto inp : plan.getInputList()){
                        if(CommonUtil.isEmpty(inputs)){
                            inputs = inp.getName();
                        }else{
                            inputs = inputs+","+inp.getName();
                        }
                    }
                }
                newNode.put("inputs", inputs);

               /* DeliverablesInfo deliverablesInfo = new DeliverablesInfo();
                deliverablesInfo.setUseObjectId(plan.getId());
                deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                List<DeliverablesInfo> deliverablesInfos = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);*/
                List<DeliverablesInfoDto> deliverablesInfos = plan.getDeliInfoList();


                String delivaryName = "";
                if(!CommonUtil.isEmpty(deliverablesInfos)){
                    for(DeliverablesInfoDto d :deliverablesInfos){
                        if(CommonUtil.isEmpty(delivaryName)){
                            delivaryName = d.getName();
                        }else{
                            delivaryName = delivaryName +","+d.getName();
                        }
                    }
                }


                newNode.put("delivaryName", delivaryName);

                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPid(parentPlanIds, planList, subNodeList.get(i),
                        types,warningDay);
                JSONObject currentNode = subNodeList.get(i);
                rows.add(currentNode);
                parentObject.put("rows", rows);
            }
        }
        else {
            return;
        }
    }

    /**
     * 项目计划新增页面跳转
     * @Author zhousuxia
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(PlanDto plan, HttpServletRequest req) {
        String planId = req.getParameter("planId");
        String projectIdForAdd = req.getParameter("projectId");
        String projectTemplateId = req.getParameter("id");
        String planName = req.getParameter("planName");
        String activityId = req.getParameter("activityId");
        String tabCbTemplateId = req.getParameter("tabCbTemplateId");
        //修改计划模板时通过id获取活动类型id和页签模板id
        if ((StringUtils.isBlank(activityId) || StringUtils.isBlank(tabCbTemplateId)) && StringUtils.isNotBlank(planId)) {
            PlanDto planDto = planService.getPlanEntity(planId);
            if (!CommonUtil.isEmpty(planDto)) {
                if (!CommonUtil.isEmpty(planDto.getTaskNameType())) {
                    activityId = planDto.getTaskNameType();

                }
                if (!CommonUtil.isEmpty(planDto.getTabCbTemplateId())) {
                    tabCbTemplateId = planDto.getTabCbTemplateId();

                }
            } else {
                List<PlanDto> templateDetailList = new ArrayList<PlanDto>();
                String projStr= (String)redisService.getFromRedis("PROJTMPPLANLIST",projectTemplateId);
                if(!CommonUtil.isEmpty(projStr)){
                    templateDetailList = JSON.parseArray(projStr,PlanDto.class);
                    for (PlanDto planDto1 : templateDetailList) {
                        if (StringUtils.isNotEmpty(planDto1.getId()) && planDto1.getId().equals(planId)) {
                            activityId = planDto1.getTaskNameType();
                            tabCbTemplateId = planDto1.getTabCbTemplateId();
                            break;
                        }
                    }
                }

            }
        }
        Project project = new Project();
        Date parentStartTime = new Date();
        Date parentEndTime = new Date();
        // 新建计划子计划时随机生成计划的UUID
        plan.setId(UUID.randomUUID().toString());
        plan.setBizCurrent(PlanConstants.PLAN_EDITING);
        plan.setCreateBy(ResourceUtil.getCurrentUser().getId());
        plan.setCreateName(ResourceUtil.getCurrentUser().getUserName());
        plan.setCreateFullName(ResourceUtil.getCurrentUser().getRealName());
        plan.setCreateTime(new Date());
        // 如果父级开始时间晚于当前时间，则计划开始时间取父级开始时间
        if (parentStartTime.getTime() > (new Date()).getTime()) {
            plan.setPlanStartTime(parentStartTime);
            req.setAttribute("planStartTimeRm", parentStartTime);
            plan.setWorkTime("1");
        }
        // 如果父级开始时间早于等于当前时间，则计划开始时间取当前时间
        else {
            plan.setPlanStartTime(parentStartTime);
            req.setAttribute("planStartTimeRm", parentStartTime);
            plan.setWorkTime("1");
        }

        if (!CommonUtil.isEmpty(project) && !CommonUtil.isEmpty(project.getProjectTimeType())) {
            if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                Date date = (Date)plan.getPlanStartTime().clone();
                plan.setPlanEndTime(DateUtil.nextWorkDay(date, 0));
                req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
            }
            else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",plan.getPlanStartTime());
                params.put("days",0);
                plan.setPlanEndTime(calendarService.getNextWorkingDay(ResourceUtil.getApplicationInformation().getAppKey(),params));
                req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
            }
            else {
                Date date = (Date)plan.getPlanStartTime().clone();
                plan.setPlanEndTime(TimeUtil.getExtraDate(date, 0));
                req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
            }
        }
        else {
            plan.setPlanEndTime(TimeUtil.getExtraDate(plan.getPlanStartTime(), 0));
            req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
        }
        plan.setMilestone("");
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
  //      List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(feignJson.getObj());
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            req.setAttribute("isStandard", true);
        }
        else {
            req.setAttribute("isStandard", false);
        }

        plan.setMilestone("否");
        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);
        // 给计划类型设置初始值
        if (!CommonUtil.isEmpty(types)) {
            plan.setTaskNameType(types.get(0).getTypecode());
        }
        else {
            dictCode = "activeCategory";
            types = tsMap.get(dictCode);
            if (!CommonUtil.isEmpty(types)) {
                plan.setTaskNameType(types.get(0).getTypecode());
            }
        }

      /*  String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        req.setAttribute("userList2", jonStr2);*/
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr3);
//        req.setAttribute("userList", userList);

        req.setAttribute("ownerShow", "");
        req.setAttribute("planLevelShow", "");
        req.getSession().setAttribute("peojectIdForAdd", projectIdForAdd);
        req.setAttribute("parentStartTime", parentStartTime);
        req.setAttribute("parentEndTime", parentEndTime);

        if (!PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
                && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
            if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())) {
                plan.setStatus(ProjectStatusConstants.PAUSE_CHI);
            }
            else if (ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())) {
                plan.setStatus(ProjectStatusConstants.CLOSE_CHI);
            }
            else {
                // 获取计划的生命周期
                FeignJson fj = planService.getLifeCycleStatusList();
                String lifeStaStr = String.valueOf(fj.getObj());
                List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
                for (LifeCycleStatus status : statusList) {
                    if (status.getName().equals(plan.getBizCurrent())) {
                        plan.setStatus(status.getTitle());
                        break;
                    }
                }
            }
        }
        else {
            // 获取计划的生命周期
            FeignJson fj = planService.getLifeCycleStatusList();
            String lifeStaStr = String.valueOf(fj.getObj());
            List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
            for (LifeCycleStatus status : statusList) {
                if (status.getName().equals(plan.getBizCurrent())) {
                    plan.setStatus(status.getTitle());
                    break;
                }
            }
        }
        req.setAttribute("useObjectId", plan.getId());
        if(!CommonUtil.isEmpty(projectIdForAdd)){
            req.setAttribute("useObjectType", "PLAN");
        }else{
            if(!CommonUtil.isEmpty(projectTemplateId)){
                req.setAttribute("useObjectType", CommonConstants.DELIVERABLE_TYPE_PROJECTTEMPLATE);
            } else{
                req.setAttribute("useObjectType", "PLANTEMPLATE");
            }
        }

        req.setAttribute("projectId", projectIdForAdd);
        req.setAttribute("projectTemplateId", projectTemplateId);
        req.setAttribute("type", req.getParameter("type"));


        String folderId = "";
        String deliverableId = req.getParameter("deliverableId");
        if (StringUtil.isNotEmpty(deliverableId)) {
            List<ProjDocRelationDto> repFileList = projLibService.getDocRelation(deliverableId);
            if (!CommonUtil.isEmpty(repFileList)) {
                for (ProjDocRelationDto projDocRelation : repFileList) {
                    String docId = projDocRelation.getDocId();
                    ProjLibDocumentVo projLibDocumentVo = planService.getProjDocmentVoById(docId);
                    folderId = projLibDocumentVo.getParentId();
                }
            }
        }
        else {
            folderId = planService.getFoldIdByProjectId(projectIdForAdd);
        }

        ProjLibDocumentVo parentVo = new ProjLibDocumentVo();
        String docNamePath = null;
        List<TSTypeDto> curType = tsMap.get(dictCode);
        if (!CommonUtil.isEmpty(curType)) {
            req.setAttribute("fileSecurityLevel", curType.get(0).getTypecode());
        }
        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectIdForAdd);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        req.setAttribute("yanfa", "yanfa");

        String planTemplateId = req.getParameter("planTemplateId");
        req.setAttribute("planTemplateId", planTemplateId);


        List<PlanDto> templateDetailList = new ArrayList<PlanDto>();
        if(!CommonUtil.isEmpty(projectTemplateId)){
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }else {
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }

        PlanDto delTemp = new PlanDto();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if(!CommonUtil.isEmpty(templateDetailList)){
            List<PlanDto> childrenPlanList = new ArrayList<PlanDto>();
            for(PlanDto tp : templateDetailList){
                if(!CommonUtil.isEmpty(tp.getParentPlanId()) && tp.getParentPlanId().equals(req.getParameter("planId"))){
                    childrenPlanList.add(tp);
                }
            }

            JSONArray jsonArr = new JSONArray();
            if(!CommonUtil.isEmpty(childrenPlanList)){
                for(PlanDto cp : childrenPlanList){
                    PlanDto cPlan = new PlanDto();
                    cPlan.setId(cp.getId());
                    cPlan.setParentPlanId(cp.getParentPlanId());
                    cPlan.setWorkTime(cp.getWorkTime());
                    jsonArr.add(cPlan);
                }
            }

            req.setAttribute("childrenPlanList", jsonArr.toString());
            for(PlanDto p : templateDetailList){
                planIdMaps.put(p.getId(), p.getPlanName());
            }

            for(PlanDto detail : templateDetailList){
                if(!CommonUtil.isEmpty(req.getParameter("parentPlanId")) && detail.getId().equals(req.getParameter("parentPlanId"))){
                    req.setAttribute("workTime", detail.getWorkTime());
                }
                if(!CommonUtil.isEmpty(req.getParameter("planId")) && req.getParameter("planId").equals(detail.getId())){
                    delTemp = detail;
                }
            }
        }
        String type = req.getParameter("type");
        if(!CommonUtil.isEmpty(type) && type.equals("update")){
            if(!CommonUtil.isEmpty(delTemp.getPreposeIds())){
                String preposePlanName = "";
                for(String preposeId : delTemp.getPreposeIds().split(",")){
                    //PlanDto preposeDto = planService.getPlanEntity(preposeId);
                    PlanDto preposeDto = templateDetailList.stream().filter(dto -> dto.getId().equals(preposeId)).findAny().orElse(null);
                    if(CommonUtil.isEmpty(preposePlanName)){
                        preposePlanName =  preposeDto.getPlanName();
                    }else{
                        preposePlanName = preposePlanName+","+preposeDto.getPlanName();
                    }
                }
                delTemp.setPreposePlans(preposePlanName);
            }
            req.setAttribute("planTemplate", delTemp);
            req.setAttribute("planId", delTemp.getId());
            req.setAttribute("useObjectId", delTemp.getId());
            req.setAttribute("disabled", "true");
            req.setAttribute("activityId", activityId);
            req.setAttribute("tabCbTemplateId", tabCbTemplateId);
            plan.setParentPlanId(delTemp.getParentPlanId());
//            redisService.setToRedis("INPUTSLIST", delTemp.getId(), null);
        }else{
        	if (!CommonUtil.isEmpty(planName)) {
                delTemp.setPlanName(planName);
            }
            req.setAttribute("disabled", "false");
            delTemp.setMilestone("false");
            delTemp.setWorkTime("1");
            req.setAttribute("planTemplate", delTemp);
            req.setAttribute("planId", "");
            req.setAttribute("activityId", activityId);
            req.setAttribute("tabCbTemplateId", tabCbTemplateId);
            redisService.setToRedis("INPUTSLIST", plan.getId(), "");
        }
        req.setAttribute("plan_", plan);

        return new ModelAndView("com/glaway/ids/project/plan/planAddForTemplate");
    }

    /**
     * 项目计划新增页面跳转
     * @Author zhousuxia
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAddBefore")
    public ModelAndView goAddBefore(HttpServletRequest req) {
        String projectTemplateId = req.getParameter("id");
        req.setAttribute("projectTemplateId", projectTemplateId);
        String planTemplateId = req.getParameter("planTemplateId");
        req.setAttribute("planTemplateId", planTemplateId);
        String parentPlanId = req.getParameter("parentPlanId");
        req.setAttribute("parentPlanId", parentPlanId);
        String  beforePlanId = req.getParameter(" beforePlanId");
        req.setAttribute(" beforePlanId",  beforePlanId);
        FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(feignJson.getObj());
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            req.setAttribute("isStandard", true);
        }
        else {
            req.setAttribute("isStandard", false);
        }
        if(NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr) || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)){
            req.setAttribute("isForce",true);
        }else{
            req.setAttribute("isForce",false);
        }

        return new ModelAndView("com/glaway/ids/project/plan/planAddTemplateBefore");
    }

    /**
     * 计划前置选择页面跳转
     * @Author zhousuxia
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goPlanPreposeTree")
    public ModelAndView goPlanPreposeTree(PlanDto plan, HttpServletRequest req) {
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr);
        req.setAttribute("planId", req.getParameter("planId"));
        req.setAttribute("projectId", plan.getProjectId());
        req.setAttribute("preposeIds", plan.getPreposeIds());
        req.setAttribute("planTemplateId", req.getParameter("planTemplateId"));
        req.setAttribute("projectTemplateId", req.getParameter("projectTemplateId"));
        req.setAttribute("parentPlanId", req.getParameter("parentPlanId"));
        return new ModelAndView("com/glaway/ids/project/plan/planTemplatePreposeList");
    }

    /**
     * 计划前置列表
     * @Author zhousuxia
     * @return
     * @see
     */
    @RequestMapping(params = "planPreposeList")
    public void planPreposeList(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> data= new ArrayList<JSONObject>();
        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("projectTemplateId");
        String planId = request.getParameter("id");
        String parentPlanId = request.getParameter("parentPlanId");
        PlanDto plan = new PlanDto();
        plan.setId(planId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> detailList = new ArrayList<PlanDto>();
        if(StringUtil.isNotEmpty(projectTemplateId)){
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }else{
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }

        }

        List<PlanDto> list = new ArrayList<PlanDto>();

        if(!CommonUtil.isEmpty(detailList)){
            Map<String, String> planIdMaps = new HashMap<String, String>();

            for(PlanDto p : detailList){
                planIdMaps.put(p.getId(), p.getPlanName());
            }
            Map<String, String> map = new HashMap<String, String>();
            if(!CommonUtil.isEmpty(planId)) {
                map = getPlanAllChildren(plan, detailList);
            }
            Map<String, String> parentMap = getPlanAllParent(plan, detailList);
            for (PlanDto node : detailList) {
                if (StringUtils.isEmpty(map.get(node.getId())) && CommonUtil.isEmpty(parentMap.get(node.getId()))) {
                    list.add(node);
                }
            }
            data = changePlansToJSONObjectsForPrepose(list);
        }
        String json = JSON.toJSONString(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 根据计划ID查找其所有子计划（包括计划本身和所有子孙计划）
     * @Author zhousuxia
     * @param plan
     * @return
     * @see
     */
    private Map<String, String> getPlanAllChildren(PlanDto plan, List<PlanDto> allList) {
        Map<String, String> map = new HashMap<String, String>();
        if (plan != null) {
            map.put(plan.getId(), plan.getId());
            getPlanChildren(allList, plan, map);
        }
        return map;
    }

    /**
     * 根据当前计划，查找其所有子孙计划
     * @Author zhousuxia
     * @param currentPlan
     * @return
     * @see
     */
    private void getPlanChildren(List<PlanDto> allList, PlanDto currentPlan, Map<String, String> map) {
        if (currentPlan != null) {
            PlanDto conditionPlan = new PlanDto();
            conditionPlan.setParentPlanId(currentPlan.getId());
            List<PlanDto> childrenPlan = new ArrayList<PlanDto>();
            for (PlanDto p : allList) {
                if (currentPlan.getId().equals(p.getParentPlanId())) {
                    map.put(p.getId(), p.getId());
                    childrenPlan.add(p);
                }
            }
            if (!CommonUtil.isEmpty(childrenPlan)) {
                for (PlanDto condition : childrenPlan) {
                    getPlanChildren(allList, condition, map);
                }
            }
        }
    }


    /**
     * 根据计划ID查找其所有父计划（包括爷爷计划等）
     * @Author zhousuxia
     * @param plan
     * @return
     * @see
     */
    private Map<String, String> getPlanAllParent(PlanDto plan, List<PlanDto> allList) {
        Map<String, String> map = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(plan.getParentPlanId())) {
            getPlanParent(allList, plan, map);
        }
        return map;
    }


    /**
     * 根据当前计划，查找其所有父计划
     * @Author zhousuxia
     * @param currentPlan
     * @return
     * @see
     */
    private void getPlanParent(List<PlanDto> allList, PlanDto currentPlan, Map<String, String> map) {
        if (!CommonUtil.isEmpty(currentPlan.getParentPlanId())) {
            PlanDto conditionPlan = new PlanDto();
            conditionPlan.setId(currentPlan.getParentPlanId());
            PlanDto parentPlan = new PlanDto();
            for (PlanDto p : allList) {
                if (!CommonUtil.isEmpty(conditionPlan.getId()) && conditionPlan.getId().equals(p.getId())) {
                    map.put(p.getId(), p.getId());
                    parentPlan = p;
                }
            }
            getPlanParent(allList, parentPlan, map);
        }
    }


    /**
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjectsForPrepose(List<PlanDto> planList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        String projectId = "";
        Map<String, String> planLevelMap =  getPlanLevelMap();

        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                projectId = p.getProjectId();
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }
        String dictCode = "activeCategory";

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);

        FeignJson jsonF = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(jsonF.getObj());

        for (PlanDto p : planList) {


            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("projectId", p.getProjectId());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planNumber", p.getPlanNumber());
                root.put("optBtn", this.generateOptBtn(p));
                root.put("displayName", p.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getPlanName());
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", p.getPlanName());
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", p.getPlanName());
                    root.put("planName", p.getPlanName());
                }

                root.put("planLevelInfo",
                        p.getPlanLevel() == null ? "" : planLevelMap.get(p.getPlanLevel()));
                root.put("ownerInfo",
                        p.getOwnerInfo() == null ? "" : p.getOwnerInfo().getRealName() + "-"
                                + p.getOwnerInfo().getUserName());

                root.put("planStartTime",
                        DateUtil.dateToString(p.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("planEndTime",
                        DateUtil.dateToString(p.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));

                root.put("assignerInfo",
                        p.getAssignerInfo() == null ? "" : p.getAssignerInfo().getRealName() + "-"
                                + p.getAssignerInfo().getUserName());

                root.put("assignTime",
                        DateUtil.dateToString(p.getAssignTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("workTime", p.getWorkTime());

                root.put("preposePlans", p.getPreposePlans());

                root.put("milestone", "true".equals(p.getMilestone()) ? "是" : "否");

                root.put("createBy", p.getCreateBy());
                root.put("owner", p.getOwner());
                root.put("parent_Id", p.getParentPlan() == null ? "" : p.getParentPlan().getId());
                root.put("isCreateByPmo", p.getIsCreateByPmo());
                root.put("flowStatus", p.getFlowStatus());
                root.put("bizCurrent", p.getBizCurrent());
                root.put("parent_owner",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getOwner());
                root.put("parent_createBy",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getCreateBy());
                root.put("parent_flowStatus",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getFlowStatus());
                root.put("parent_bizCurrent",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getBizCurrent());
                root.put("project_bizCurrent",
                        p.getProject() == null ? "" : p.getProject().getBizCurrent());
                root.put("opContent", p.getOpContent());

                root.put("creator", p.getCreator() == null ? "" : p.getCreator().getRealName()
                        + "-"
                        + p.getCreator().getUserName());
                root.put("createTime",
                        DateUtil.dateToString(p.getCreateTime(), DateUtil.LONG_DATE_FORMAT));

                root.put("flowStatus", p.getFlowStatus());
                root.put("result", p.getResult());

                // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                root.put("actualEndTime",
                        DateUtil.dateToString(p.getActualEndTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("invalidTime",
                        DateUtil.dateToString(p.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                root.put("taskNameType", p.getTaskNameType());
                root.put("taskType", p.getTaskType());
                root.put("planType", p.getPlanType());

                DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(p.getId());
                deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                List<DeliverablesInfoDto> deliverablesInfos = deliverablesInfoService.queryDeliverableList(
                        deliverablesInfo, 1, 10, false);


                String delivaryName = "";
                for(DeliverablesInfoDto d :deliverablesInfos){
                    if(CommonUtil.isEmpty(delivaryName)){
                        delivaryName = d.getName();
                    }else{
                        delivaryName = delivaryName +","+d.getName();
                    }
                }

                root.put("delivaryName", delivaryName);

                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPidForPrepose(parentPlanIds, planList, rootList.get(i), types,
                    warningDay);
        }
        return rootList;
    }


    /**
     * Description:递归查询获取所有子节点
     *
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPidForPrepose(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                           JSONObject parentObject,
                                           List<TSTypeDto> types,
                                           String warningDay) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();

        Map<String, String> planLevelMap =  getPlanLevelMap();



        for (PlanDto plan : planList) {


            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planNumber", plan.getPlanNumber());
                newNode.put("optBtn", this.generateOptBtn(plan));
                newNode.put("displayName", plan.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", plan.getPlanName());
                    treeNode.put("image", "folder.gif");
                    newNode.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", plan.getPlanName());
                    treeNode1.put("image", "folder.gif");
                    newNode.put("planName", treeNode1);
                }
                else {
                    newNode.put("displayNameNode", plan.getPlanName());
                    newNode.put("planName", plan.getPlanName());
                }

                newNode.put("planLevelInfo",
                        plan.getPlanLevel() == null ? "" : planLevelMap.get(plan.getPlanLevel()));
                newNode.put("ownerInfo",
                        plan.getOwnerInfo() == null ? "" : plan.getOwnerInfo().getRealName() + "-"
                                + plan.getOwnerInfo().getUserName());
                newNode.put("planStartTime",
                        DateUtil.dateToString(plan.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("planEndTime",
                        DateUtil.dateToString(plan.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("assignerInfo",
                        plan.getAssignerInfo() == null ? "" : plan.getAssignerInfo().getRealName()
                                + "-"
                                + plan.getAssignerInfo().getUserName());
                newNode.put("assignTime",
                        DateUtil.dateToString(plan.getAssignTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("workTime", plan.getWorkTime());

                newNode.put("preposePlans", plan.getPreposePlans());

                newNode.put("milestone", "true".equals(plan.getMilestone()) ? "是" : "否");

                newNode.put("createBy", plan.getCreateBy());
                newNode.put("owner", plan.getOwner());
                newNode.put("parent_Id",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getId());
                newNode.put("isCreateByPmo", plan.getIsCreateByPmo());
                newNode.put("flowStatus", plan.getFlowStatus());
                newNode.put("bizCurrent", plan.getBizCurrent());
                newNode.put("parent_owner",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getOwner());
                newNode.put("parent_createBy",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getCreateBy());
                newNode.put("parent_flowStatus",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getFlowStatus());
                newNode.put("parent_bizCurrent",
                        plan.getParentPlan() == null ? "" : plan.getParentPlan().getBizCurrent());
                newNode.put("project_bizCurrent",
                        plan.getProject() == null ? "" : plan.getProject().getBizCurrent());
                newNode.put("opContent", plan.getOpContent());

                newNode.put("creator",
                        plan.getCreator() == null ? "" : plan.getCreator().getRealName() + "-"
                                + plan.getCreator().getUserName());
                newNode.put("createTime",
                        DateUtil.dateToString(plan.getCreateTime(), DateUtil.LONG_DATE_FORMAT));

                newNode.put("flowStatus", plan.getFlowStatus());
                newNode.put("result", plan.getResult());

                // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                newNode.put("actualEndTime",
                        DateUtil.dateToString(plan.getActualEndTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("invalidTime",
                        DateUtil.dateToString(plan.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                newNode.put("taskNameType", plan.getTaskNameType());
                newNode.put("taskType", plan.getTaskType());
                newNode.put("planType", plan.getPlanType());

                DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(plan.getId());
                deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                List<DeliverablesInfoDto> deliverablesInfos = deliverablesInfoService.queryDeliverableList(
                        deliverablesInfo, 1, 10, false);


                String delivaryName = "";
                for(DeliverablesInfoDto d :deliverablesInfos){
                    if(CommonUtil.isEmpty(delivaryName)){
                        delivaryName = d.getName();
                    }else{
                        delivaryName = delivaryName +","+d.getName();
                    }
                }
                newNode.put("delivaryName", delivaryName);

                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPidForPrepose(parentPlanIds, planList, subNodeList.get(i),
                        types,warningDay);
                JSONObject currentNode = subNodeList.get(i);
                rows.add(currentNode);
                parentObject.put("rows", rows);
            }
        }
        else {
            return;
        }
    }


    /**
     * 跳转选择来源计划页面
     *
     * @author zhousuxia
     * @version 2018年6月28日
     * @see PlanTemplateController
     * @since
     */
    @RequestMapping(params = "goSelectPlanInputs")
    public ModelAndView goSelectPlanInputs(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        String inputsName = request.getParameter("inputsName");
        String tempId = request.getParameter("tempId");
        String planTemplateId = request.getParameter("planTemplateId");
        request.setAttribute("projectId", projectId);
        request.setAttribute("useObjectId", useObjectId);
        request.setAttribute("useObjectType", useObjectType);
        request.setAttribute("inputsName", inputsName);
        request.setAttribute("tempId", tempId);
        request.setAttribute("planId", useObjectId);
        request.setAttribute("planTemplateId",planTemplateId);

        return new ModelAndView("com/glaway/ids/project/plan/selectPlanTemplateInputs");
    }


    /**
     * 计划前置选择页面初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planInputsList")
    public void planInputsList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String inputsName = String.valueOf(request.getSession().getAttribute("inputsName"));
        inputsName = URLDecoder.decode(inputsName, "UTF-8");

        String planId = request.getParameter("id");

        List<TemplatePlanTreeNode> data= new ArrayList<TemplatePlanTreeNode>();
        String planTemplateId = request.getParameter("planTemplateId");
        String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
        List<PlanDto> detailList = new ArrayList<>();
        if(!CommonUtil.isEmpty(detailStr)){
            detailList = JSON.parseArray(detailStr,PlanDto.class);
        }
        List<PlanDto> tempList = new ArrayList<PlanDto>();

        if(!CommonUtil.isEmpty(detailList)){

            for (PlanDto temp : detailList) {

                if(!CommonUtil.isEmpty(temp.getDeliInfoList())){
                    for(DeliverablesInfoDto del : temp.getDeliInfoList()){
                        if(!del.getUseObjectId().equals(planId)){
                            if(del.getName().equals(inputsName)){
                                tempList.add(temp);
                                break;
                            }
                        }
                    }
                }
            }
            if(!CommonUtil.isEmpty(tempList)){
                for(PlanDto detail : tempList){
                    TemplatePlanTreeNode node = new TemplatePlanTreeNode();
                    node.setId(detail.getId());
                    node.setPlanNumber(String.valueOf(detail.getPlanNumber()));
                    node.setPlanName(detail.getPlanName());
                    if(!CommonUtil.isEmpty(detail.getPlanLevel())){
                        BusinessConfigDto config = businessConfigService.getBusinessConfigEntity(detail.getPlanLevel());
                        node.setPlanLevel(config.getName());
                    }
                    if(!CommonUtil.isEmpty(detail.getMilestone())){
                        if(detail.getMilestone().equals("true")){
                            node.setMileStone("是");
                        }else{
                            node.setMileStone("否");
                        }
                    }

//                    if(detail.getIsNecessary().equals("true")){
//                        node.setIsNecessary("是");
//                    }else{
//                        node.setIsNecessary("否");
//                    }
                    node.setWorkTime(detail.getWorkTime());
                    node.setOptBtn(this.generateOptBtn(detail));

                    data.add(node);
                }
//                data = changePlansToJSONObjects(tempList);
            }
        }
        String json = JSON.toJSONString(data);
        //   System.out.println(json);
        TagUtil.ajaxResponse(response, json);
    }

    @RequestMapping(params = "goViewPlan")
    public ModelAndView goViewPlan(HttpServletRequest request){
        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("id");
        String planId = request.getParameter("planId");
        String projectId = request.getParameter("projectId");
        if(!CommonUtil.isEmpty(projectTemplateId)){
            request.setAttribute("useObjectType", "PROJECTTEMPLATE");
        }else if(CommonUtil.isEmpty(projectId)){
            request.setAttribute("useObjectType", "PLANTEMPLATE");
        }else{
            request.setAttribute("useObjectType", "PLAN");
        }
        request.setAttribute("projectId", projectId);
        request.setAttribute("planTemplateId", planTemplateId);
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("planId", planId);

        return new ModelAndView(
                "com/glaway/ids/project/plan/planTemplate-check");
    }

    @RequestMapping(params = "goTemplateInputCheck")
    public ModelAndView goTemplateInputCheck(HttpServletRequest request){
        request.setAttribute("useObjectId", request.getParameter("useObjectId"));
        request.setAttribute("useObjectType", request.getParameter("useObjectType"));
        request.setAttribute("planTemplateId", request.getParameter("planTemplateId"));
        return new ModelAndView(
                "com/glaway/ids/project/plan/planTemplateInput-check");
    }


    /**
     * 计划输入初始化时获取输入列表
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "inputsList")
    public void inputsList(InputsDto inputs, HttpServletRequest request, HttpServletResponse response) {

        String planTemplateId = request.getParameter("planTemplateId") == null ? "" : request.getParameter("planTemplateId");
        String projectId = request.getParameter("projectId");

        String useObjectId = request.getParameter("useObjectId");
        String inputStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
        List<InputsDto> inputsList = new ArrayList<>();
        if(!CommonUtil.isEmpty(inputStr)){
            inputsList = JSON.parseArray(inputStr,InputsDto.class);
        }

        if(CommonUtil.isEmpty(inputsList)){
            if (inputs != null && StringUtils.isNotEmpty(inputs.getUseObjectId())
                    && StringUtils.isNotEmpty(inputs.getUseObjectType())) {
                List<InputsDto> list = inputsService.queryNewInputsList(inputs);
                String inputsStr = JSON.toJSONString(list);
                if(!CommonUtil.isEmpty(list)){
                    redisService.setToRedis("INPUTSLIST", inputs.getUseObjectId(), inputsStr);
                }
            }
        }

        String inpStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
        if(!CommonUtil.isEmpty(inpStr)){
            inputsList = JSON.parseArray(inpStr,InputsDto.class);
        }

        if(!CommonUtil.isEmpty(inputsList)){

            String libId = "";
            if(!CommonUtil.isEmpty(projectId) ){
                FeignJson fj = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
                libId = String.valueOf(fj.getObj());

            }else{
                PlanDto plan = planService.getPlanEntity(useObjectId);
                if(!CommonUtil.isEmpty(plan)){
                    projectId = plan.getProjectId();
                    FeignJson fj = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
                    libId = String.valueOf(fj.getObj());
                }
            }

            Map<String, String> fileNameMap = new HashMap<String, String>();

            Map<String, String> filePathMap = new HashMap<String, String>();

            Map<String, String> fileIdMap = new HashMap<String, String>();

            if(!CommonUtil.isEmpty(libId)){
                fileNameMap = inputsRemoteFeignServiceI.getRepFileNameAndBizIdMap(libId);

                filePathMap = inputsRemoteFeignServiceI.getRepFilePathAndBizIdMap(libId);

                fileIdMap = inputsRemoteFeignServiceI.getRepFileIdAndBizIdMap(libId);
            }

            for(InputsDto input : inputsList){
                if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("LOCAL")){
                    input.setDocNameShow(input.getDocName());
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PROJECTLIBDOC")){
                    if(!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))){
                        input.setDocNameShow(fileNameMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))){
                        input.setOriginPath(filePathMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))){
                        input.setDocIdShow(fileIdMap.get(input.getDocId()));
                    }
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PLANTEMPLATE")){

                    String delStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
                    List<PlanDto> detailList = new ArrayList<>();
                    if(!CommonUtil.isEmpty(delStr)){
                        detailList = JSON.parseArray(delStr,PlanDto.class);
                    }


                    List<DeliverablesInfoDto> deliList = new ArrayList<DeliverablesInfoDto>();
                    PlanDto plan = new PlanDto();
                    DeliverablesInfoDto deli = new DeliverablesInfoDto();

                    if(!CommonUtil.isEmpty(detailList)){
                        for(PlanDto detail : detailList){
                            if(detail.getId().equals(input.getOriginObjectId())){
                                deliList = detail.getDeliInfoList();
                                if(!CommonUtil.isEmpty(deliList)){
                                    for(DeliverablesInfoDto de : deliList){
                                        if(de.getName().equals(input.getName())){
                                            deli = de;
                                            plan = detail;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(!CommonUtil.isEmpty(plan.getId())){
                        input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                    }else{
                        input.setOriginPath("");
                    }

                    input.setDocId(deli.getDocId());
                    input.setDocNameShow(deli.getDocName());
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PLAN")){
                    PlanDto p = planService.getPlanEntity(input.getOriginObjectId());
                    List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                    if(!CommonUtil.isEmpty(p)){
                        projDocRelationList = inputsRemoteFeignServiceI.getDocRelationList(p,UserUtil.getInstance().getUser().getId());
                    }
                    ProjDocVo projDoc = new ProjDocVo();
                    if(!CommonUtil.isEmpty(projDocRelationList)){
                        for(ProjDocVo vo:projDocRelationList){
                            if(vo.getDeliverableId().equals(input.getOriginDeliverablesInfoId())){
                                projDoc = vo;
                                break;
                            }
                        }
                    }
                    input.setOriginPath(p.getPlanNumber()+"."+p.getPlanName());
                    input.setDocId(projDoc.getDocId());
                    input.setDocNameShow(projDoc.getDocName());
                    input.setExt1(String.valueOf(projDoc.isDownload()));
                    input.setExt2(String.valueOf(projDoc.isHavePower()));
                    input.setExt3(String.valueOf(projDoc.isDetail()));

                }
            }
        }

        String json = "[]";
        String datagridStr = "[]";
        if(!CommonUtil.isEmpty(inputsList)){
            json = com.alibaba.fastjson.JSONArray.toJSONString(inputsList);
            datagridStr = "{\"rows\":" + json + ",\"total\":" + inputsList.size() + "}";
        }

        TagUtil.ajaxResponse(response, datagridStr);
    }


    @RequestMapping(params = "searchDatagridForPlanInputs")
    public void searchDatagridForPlanInputs(HttpServletRequest request,HttpServletResponse response) {
        String planId = request.getParameter("planId");
        String planTemplateId = request.getParameter("planTemplateId");
        String inputsName = String.valueOf(request.getSession().getAttribute("inputsName"));
        inputsName = URLDecoder.decode(inputsName, "UTF-8");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String workTime = request.getParameter("workTime");
        String workTime_condition = request.getParameter("workTime_condition");

        String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
        List<PlanDto> detailList = new ArrayList<>();
        if(!CommonUtil.isEmpty(detailStr)){
            detailList=  JSON.parseArray(detailStr,PlanDto.class);
        }

        List<PlanDto> tempList = new ArrayList<PlanDto>();

        List<PlanDto> resTempList = new ArrayList<PlanDto>();

        List<TemplatePlanTreeNode> data= new ArrayList<TemplatePlanTreeNode>();


        if(!CommonUtil.isEmpty(detailList)){

            for (PlanDto temp : detailList) {

                if(!CommonUtil.isEmpty(temp.getDeliInfoList())){
                    for(DeliverablesInfoDto del : temp.getDeliInfoList()){
                        if(!del.getUseObjectId().equals(planId)){
                            if(del.getName().equals(inputsName)){
                                tempList.add(temp);
                            }
                        }
                    }
                }
            }


            if(!CommonUtil.isEmpty(tempList)){

                if(CommonUtil.isEmpty(planName) && CommonUtil.isEmpty(planLevel) && CommonUtil.isEmpty(workTime)){
                    resTempList = tempList;
                }else{
                    for(PlanDto plan : tempList){
                        if(!CommonUtil.isEmpty(planName) && plan.getPlanName().toLowerCase().contains(planName.toLowerCase())){
                            if(CommonUtil.isEmpty(planLevel) && CommonUtil.isEmpty(workTime)){
                                resTempList.add(plan);
                            }else{
                                if(!CommonUtil.isEmpty(planLevel) && !CommonUtil.isEmpty(plan.getPlanLevel()) && plan.getPlanLevel().equals(planLevel)){
                                    if(CommonUtil.isEmpty(workTime)){
                                        resTempList.add(plan);
                                    }else{
                                        if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                                            resTempList.add(plan);
                                        }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                                            resTempList.add(plan);
                                        }
                                    }
                                }
                            }
                        }else if(!CommonUtil.isEmpty(planLevel) && !CommonUtil.isEmpty(plan.getPlanLevel()) && plan.getPlanLevel().equals(planLevel)){
                            if(CommonUtil.isEmpty(workTime)){
                                resTempList.add(plan);
                            }else{
                                if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                                    resTempList.add(plan);
                                }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                                    resTempList.add(plan);
                                }
                            }
                        }else if(!CommonUtil.isEmpty(workTime)){
                            if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                                resTempList.add(plan);
                            }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                                resTempList.add(plan);
                            }
                        }
                    }

                }
            }


        }

        if(!CommonUtil.isEmpty(resTempList)){
            for(PlanDto detail : resTempList){
                TemplatePlanTreeNode node = new TemplatePlanTreeNode();
                node.setId(detail.getId());
                node.setPlanNumber(String.valueOf(detail.getPlanNumber()));
                node.setPlanName(detail.getPlanName());
                node.setDisplayName(detail.getPlanName());
                if(!CommonUtil.isEmpty(detail.getPlanLevel())){
                    BusinessConfigDto config = businessConfigService.getBusinessConfigEntity(detail.getPlanLevel());
                    node.setPlanLevel(config.getName());
                    node.setPlanLevelInfo(config.getName());
                }
                if(!CommonUtil.isEmpty(detail.getMilestone())){
                    if(detail.getMilestone().equals("true")){
                        node.setMileStone("是");
                    }else{
                        node.setMileStone("否");
                    }
                }

                /*if(detail.getIsNecessary().equals("true")){
                    node.setIsNecessary("是");
                }else{
                    node.setIsNecessary("否");
                }*/
                node.setWorkTime(detail.getWorkTime());
                node.setOptBtn(this.generateOptBtn(detail));

                data.add(node);
            }
        }

        Object json = com.alibaba.fastjson.JSONArray.toJSON(data);
        String resultJSON = JSON.toJSONString(json);

        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查看时、项目计划交付物tab页
     *
     * @return
     */
    @RequestMapping(params = "goDocumentCheck")
    public ModelAndView goDocumentCheck(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        // 从修改页面或者查看页面切换到该TAB、直接用req.getParameter中的值
        req.setAttribute("useObjectId", req.getParameter("useObjectId"));
        req.setAttribute("useObjectType", req.getParameter("useObjectType"));
        req.setAttribute("projectId", req.getParameter("projectId"));
        return new ModelAndView("com/glaway/ids/project/plan/planTemplateDocument-check");
    }



    @SuppressWarnings({"finally", "unchecked"})
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        try{
            String planId = request.getParameter("planId");
            String planName = request.getParameter("planName");
            String parentPlanId = request.getParameter("parentPlanId");
            String planLevel = request.getParameter("planLevel");
            String workTime = request.getParameter("workTime");
            String remark = request.getParameter("remark");
            String planTemplateId = request.getParameter("planTemplateId");
            String projectTemplateId = request.getParameter("projectTemplateId");
//            String type = request.getParameter("type");
            String milestone = request.getParameter("milestone");
            String useObjectId = request.getParameter("useObjectId");
            String useObjectType = request.getParameter("useObjectType");
//            String isNecessary = request.getParameter("isNecessary");
            String preposeIds = request.getParameter("preposeIds");
            String beforePlanId = request.getParameter("beforePlanId");
            String activityId = request.getParameter("activityId");
            String tabCbTemplateId = request.getParameter("tabCbTemplateId");
            int planNumber = 1;
            int storeyNo = 1;
            String inputStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            List<InputsDto> inputList = new ArrayList<>();
            if(!CommonUtil.isEmpty(inputStr)){
                inputList = JSON.parseArray(inputStr,InputsDto.class);
            }


            DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
            deliverablesInfo.setUseObjectId(useObjectId);
            deliverablesInfo.setUseObjectType(useObjectType);
            List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);

            List<PlanDto> templateDetailList = new ArrayList<PlanDto>();
            if(!CommonUtil.isBlank(projectTemplateId)){
                String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
                if(!CommonUtil.isEmpty(detailStr)){
                    templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
                }
            }else{
                String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
                if(!CommonUtil.isEmpty(detailStr)){
                    templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
                }
            }
            if(CommonUtil.isEmpty(templateDetailList)){
                templateDetailList = new ArrayList<PlanDto>();
            }else{

                sortPlanNumber(templateDetailList);
                planNumber = templateDetailList.get(templateDetailList.size()-1).getPlanNumber()+1;

                if(!CommonUtil.isEmpty(beforePlanId)){
                    PlanDto temDe = new PlanDto();
                    for(PlanDto de : templateDetailList){
                        if(de.getId().equals(beforePlanId)){
                            temDe = de;
                        }
                    }

                    if(CommonUtil.isEmpty(temDe.getParentPlanId())){
                        for(PlanDto de1 : templateDetailList){
                            if(de1.getStoreyNo() > temDe.getStoreyNo()){
                                de1.setStoreyNo(de1.getStoreyNo()+1);
                            }
                        }
                        storeyNo = temDe.getStoreyNo()+1;
                    }else{
                        List<PlanDto> tempDeList = new ArrayList<PlanDto>();
                        for(PlanDto de2 : templateDetailList){
                            if(!CommonUtil.isEmpty(de2.getParentPlanId()) && de2.getParentPlanId().equals(temDe.getParentPlanId())){
                                tempDeList.add(de2);
                            }
                        }
                        if(!CommonUtil.isEmpty(tempDeList)){
                            for(PlanDto te : tempDeList){
                                if(te.getStoreyNo() > temDe.getStoreyNo()){
                                    te.setStoreyNo(te.getStoreyNo()+1);
                                }
                            }
                            storeyNo = temDe.getStoreyNo()+1;
                        }
                        parentPlanId = temDe.getParentPlanId();
                    }
                }else{
                    List<PlanDto> deList = new ArrayList<PlanDto>();
                    if(CommonUtil.isEmpty(parentPlanId)){
                        for(PlanDto de : templateDetailList){
                            if(CommonUtil.isEmpty(de.getParentPlanId())){
                                deList.add(de);
                            }
                        }
                    }else{
                        for(PlanDto de : templateDetailList){
                            if(!CommonUtil.isEmpty(de.getParentPlanId()) && de.getParentPlanId().equals(parentPlanId)){
                                deList.add(de);
                            }
                        }
                    }
                    if(!CommonUtil.isEmpty(deList)){
                        sortPlanStoreyNoForTemp(deList);
                        storeyNo = deList.get(deList.size()-1).getStoreyNo()+1;
                    }
                }
            }
            String tempPlanId = "";
            if(!CommonUtil.isEmpty(planId)) {
                tempPlanId = planId;
            } else {
                tempPlanId = UUIDGenerator.generate();
            }
            List<String> pathList = new ArrayList<String>();
            List<String> repeatPathList = new ArrayList<String>();
            Map<String, String> preposeMap = new HashMap<String, String>();
            Map<String, Integer> planWorktimeMap = new HashMap<String, Integer>();
            Map<String, String> planNameMap = new HashMap<String, String>();
            Map<String, String> planParentMap = new HashMap<String, String>();
            Map<String,PlanDto> planMap = new HashMap<String,PlanDto>();
            if(!CommonUtil.isEmpty(templateDetailList)){
                for(PlanDto planT : templateDetailList){
                    planMap.put(planT.getId(), planT);
                }
                for(PlanDto plan : templateDetailList) {

                    if(!CommonUtil.isEmpty(plan.getPreposeIds())) {
                        preposeMap.put(plan.getId(), plan.getPreposeIds());
                        if(!CommonUtil.isEmpty(planMap.get(plan.getPreposeIds()))){
                            plan.setPreposePlans(planMap.get(plan.getPreposeIds()).getPlanName());
                        }
                    }
                    planWorktimeMap.put(plan.getId(), Integer.valueOf(plan.getWorkTime()));
                    planNameMap.put(plan.getId(), String.valueOf(plan.getPlanName()));
                    planParentMap.put(plan.getId(), plan.getParentPlanId());
                }
            }

            planNameMap.put(tempPlanId, planName);
            planWorktimeMap.put(tempPlanId, Integer.valueOf(workTime));
            if(!CommonUtil.isEmpty(preposeIds)) {
                preposeMap.put(tempPlanId, preposeIds);
            }
            if(!CommonUtil.isEmpty(parentPlanId)) {
                planParentMap.put(tempPlanId, parentPlanId);
            }
            if(!CommonUtil.isEmpty(preposeIds)) {
                getLinkPath(tempPlanId, tempPlanId, preposeMap, pathList, repeatPathList);
                checkPreposePostTime(repeatPathList, pathList, planNameMap, planParentMap, planWorktimeMap);
            } else {
                FeignJson fJson = preposePlanTemplateService.getPostPlanListByPlanId(tempPlanId, preposeMap);
                String postIds = String.valueOf(fJson.getObj());
                if(!CommonUtil.isEmpty(postIds)) {
                    String[] postIdArray = postIds.split(",");
                    for(String postId: postIdArray) {
                        getLinkPath(postId, postId, preposeMap, pathList, repeatPathList);
                        checkPreposePostTime(repeatPathList, pathList, planNameMap, planParentMap, planWorktimeMap);
                    }
                }
            }


            //修改父计划时判断父计划工期是否小于串行计划的工期和  --2019/1/18 zhousuxia
            List<PlanDto> childrenPlanList = new ArrayList<PlanDto>();
            if(!CommonUtil.isEmpty(templateDetailList)){
                for(PlanDto cPlan : templateDetailList){
                    if(!CommonUtil.isEmpty(cPlan.getParentPlanId()) && tempPlanId.equals(cPlan.getParentPlanId())){
                        childrenPlanList.add(cPlan);
                    }
                }

                Map<String, String> preposeMapForChildPlan = new HashMap<String, String>();
                List<String> pathListForChild = new ArrayList<String>();
                String path = "";
                if(!CommonUtil.isEmpty(childrenPlanList)){
                    for(PlanDto chPlan : childrenPlanList){
                        if(!CommonUtil.isEmpty(chPlan.getPreposeIds())) {
                            preposeMapForChildPlan.put(chPlan.getId(), chPlan.getPreposeIds());
                            if(!CommonUtil.isEmpty(path)){
                                if(!path.contains(chPlan.getId())){
                                    path = path +","+ chPlan.getId();
                                }
                                if(!path.contains(chPlan.getPreposeIds())){
                                    path = path +","+ chPlan.getPreposeIds();
                                }
                            }else{
                                path = chPlan.getId();
                                path = path +","+ chPlan.getPreposeIds();
                            }

                        }
                    }
                    pathListForChild.add(path);
                    if(!CommonUtil.isEmpty(preposeMapForChildPlan)) {

                      /*  Set<String> keySet = preposeMapForChildPlan.keySet();
                        for(String key : keySet) {
                            if(!CommonUtil.isEmpty(preposeMapForChildPlan.get(key))) {
                                String[] valueArrray = preposeMapForChildPlan.get(key).split(",");
                                for(String postId: valueArrray) {
                                    getLinkPath(postId, postId, preposeMapForChildPlan, pathList, repeatPathList);
                                    checkPreposePostTime(repeatPathList, pathList, planNameMap, planParentMap, planWorktimeMap);
                                }
                            }
                        }*/
                        checkPreposePostTime(repeatPathList, pathListForChild, planNameMap, planParentMap, planWorktimeMap);
                    }
                }

            }
            PlanDto detail = new PlanDto();
            if(CommonUtil.isEmpty(planId)){
                detail.setId(useObjectId);
                detail.setPreposeIds(preposeIds);
                detail.setPlanNumber(planNumber);
                detail.setInputList(inputList);
                detail.setDeliInfoList(deliverablesInfoList2);
                detail.setPlanName(planName);
                detail.setParentPlanId(parentPlanId);
                detail.setPlanLevel(planLevel);
                detail.setRemark(remark);
                detail.setWorkTime(workTime);
                detail.setMilestone(milestone);
                detail.setPlanTemplateId(planTemplateId);
                detail.setTaskNameType(activityId);
                detail.setTabCbTemplateId(tabCbTemplateId);
//                detail.setIsNecessary(isNecessary);
                detail.setStoreyNo(storeyNo);
                templateDetailList.add(detail);
            }else{

                for(PlanDto de : templateDetailList){
                    if(de.getId().equals(planId)){
                        detail = de;
                        break;
                    }
                }
                detail.setPreposeIds(preposeIds);
                detail.setInputList(inputList);
                detail.setDeliInfoList(deliverablesInfoList2);
                detail.setPlanName(planName);
                detail.setParentPlanId(parentPlanId);
                detail.setPlanLevel(planLevel);
                detail.setRemark(remark);
                detail.setWorkTime(workTime);
                detail.setMilestone(milestone);
                detail.setPlanTemplateId(planTemplateId);
                detail.setTaskNameType(activityId);
                detail.setTabCbTemplateId(tabCbTemplateId);
//                detail.setIsNecessary(isNecessary);
            }
            if(!CommonUtil.isEmpty(templateDetailList)){
                for(PlanDto plan : templateDetailList) {

                    if(!CommonUtil.isEmpty(plan.getPreposeIds())) {
                        if(!CommonUtil.isEmpty(planMap.get(plan.getPreposeIds()))){
                            plan.setPreposePlans(planMap.get(plan.getPreposeIds()).getPlanName());
                        }
                    }
                }
            }

            if(!CommonUtil.isBlank(projectTemplateId)){
                Collections.sort(templateDetailList, new Comparator<PlanDto>() {
                    @Override
                    public int compare(PlanDto o1, PlanDto o2) {
                        return o1.getStoreyNo().compareTo(o2.getStoreyNo());
                    }});
            }

            String deltailStr = JSON.toJSONString(templateDetailList);

            if(!CommonUtil.isBlank(projectTemplateId)){
                redisService.setToRedis("PROJTMPPLANLIST", projectTemplateId, deltailStr);
            }else{
                redisService.setToRedis("TEMPLATEPLANLIST", planTemplateId, deltailStr);
            }

        }catch(Exception e){
            j.setMsg(e.getMessage());
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    public void sortPlanNumber(List<? extends PlanDto> templateDetailList) {
        //templateDetailList.stream().sorted(Comparator.comparing(PlanDto::getPlanNumber).reversed()).collect(Collectors.toList());

       Collections.sort(templateDetailList, new Comparator<PlanDto>() {
            @Override
           public int compare(PlanDto o1, PlanDto o2) {
                return o1.getPlanNumber() - o2.getPlanNumber();
           }
       });
    }

    public void sortPlanStoreyNoForTemp(List<? extends PlanDto> templateDetailList) {
        Collections.sort(templateDetailList, new Comparator<PlanDto>() {
            @Override
            public int compare(PlanDto o1, PlanDto o2) {
                return o1.getStoreyNo().compareTo(o2.getStoreyNo());
            }
        });
    }


    private void getLinkPath(String tempPlanId, String path, Map<String, String> preposeMap, List<String> pathList, List<String> repeatPathList) {
        if(!CommonUtil.isEmpty(preposeMap) && !CommonUtil.isEmpty(preposeMap.get(tempPlanId))) {
            String preposeIds = preposeMap.get(tempPlanId);
            String[] detailIds = preposeIds.split(",");
            for(String detailId : detailIds) {
                String[] pathArr = path.split(",");
                List<String> tempPathList = Arrays.asList(pathArr);
                if(tempPathList.contains(detailId)){
//                    String repeatPath = path + "," + detailId;
                    repeatPathList.add(path);
                    break;
                }
                else{
                    String curPath = path + "," + detailId;
                    getLinkPath(detailId, curPath, preposeMap, pathList, repeatPathList);
                }
            }
        }
        else{
            pathList.add(path);
        }
    }


    private void checkPreposePostTime(List<String> repeatPathList, List<String> pathList, Map<String, String> planNameMap,
                                      Map<String, String> planParentMap, Map<String, Integer> planWorktimeMap) {
        if(!CommonUtil.isEmpty(repeatPathList)){
            String repeatMessage = "";
            String[] repeatIdList = repeatPathList.get(0).split(",");
            for (int i = 0; i < repeatIdList.length; i++) {
                if(!CommonUtil.isEmpty(repeatMessage)) {
                    repeatMessage = repeatMessage + "【" + planNameMap.get(repeatIdList[i]) +"】";
                } else {
                    repeatMessage = "【" + planNameMap.get(repeatIdList[i]) +"】";
                }
            }
            Object[] repeatArguments = new String[] {repeatMessage};
            throw new GWException(
                    I18nUtil.getValue(
                            "com.glaway.ids.pm.project.plantemplate.savePlan.checkPlanRelation",
                            repeatArguments));
        }
        else if(!CommonUtil.isEmpty(pathList)) {
            int length = 0;
            String message = "";
            for(String node : pathList) {
                if(!CommonUtil.isEmpty(node)) {
                    String[] nodeArr = node.split(",");
                    for (int i = 0; i < nodeArr.length; i++) {
                        if(!CommonUtil.isEmpty(message)) {
                            message = message + "【" + planNameMap.get(nodeArr[i]) +"】";
                        } else {
                            message = "【" + planNameMap.get(nodeArr[i]) +"】";
                        }
                        length = length + planWorktimeMap.get(nodeArr[i]);
                        if(i >= 1 && !CommonUtil.isEmpty(planParentMap.get(nodeArr[i])) &&
                                length > planWorktimeMap.get(planParentMap.get(nodeArr[i]))) {
                            Object[] arguments = new String[] {message};
                            throw new GWException(
                                    I18nUtil.getValue(
                                            "com.glaway.ids.pm.project.plantemplate.savePlan.checkPreposeWorkTime",
                                            arguments));
                        }
                    }
                }
            }
        }
    }



    /**
     * 模板名称重名校验
     *
     * @author zhousuxia
     * @version 2018年7月1日
     * @see PlanTemplateController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkTemplateNameBeforeSave")
    @ResponseBody
    public AjaxJson checkTemplateNameBeforeSave(HttpServletRequest request){
        AjaxJson j =new AjaxJson();
        boolean flag = true;
        try{
            String templateName = request.getParameter("templateName");
            String planTemplateId = request.getParameter("planTemplateId");
            String type = request.getParameter("type");
            //仅新增校验名称是否重复
            if(PlantemplateConstant.ADD.equals(type)) {
                flag = planTemplateService.checkTemplateNameBeforeSave(templateName,planTemplateId);
                if(flag){
                    j.setSuccess(true);
                }else{
                    j.setSuccess(false);
                }
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "savePlanTemplate")
    @ResponseBody
    public FeignJson savePlanTemplate(HttpServletRequest request){
        String type = request.getParameter("type");
        FeignJson fJson = new FeignJson();
        try{
            String templateName = request.getParameter("templateName");
            String planTemplateId = request.getParameter("planTemplateId");
            String remark = request.getParameter("remark");
            if(CommonUtil.isEmpty(remark)){
                remark = "";
            }

            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST",
                    planTemplateId);
            List<PlanDto> planList = new ArrayList<>();
            if(!CommonUtil.isEmpty(detailStr)){
                planList =JSON.parseArray(detailStr,PlanDto.class);
            }

 /*           if(!CommonUtil.isEmpty(planList)){
                for(PlanDto plan : planList){
                    List<InputsDto> inputsList = (List<InputsDto>)redisService.getFromRedis("INPUTSLIST",
                            plan.getId());
                    plan.setInputList(inputsList);
                }
            }*/
            if(CommonUtil.isEmpty(planList)){
                planList = new ArrayList<>();
            }

            fJson = planTemplateService.savePlanTemplate(templateName,planTemplateId,remark,type,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId(),planList);

        }catch(Exception e){
            fJson.setSuccess(false);
            fJson.setMsg(e.getMessage());
            if(PlantemplateConstant.ADD.equals(type)){
                log.error(I18nUtil.getValue("com.glaway.ids.pm.plantemplate.plantemplate.addFail"),e);
            } else if(PlantemplateConstant.UPADTE.equals(type)) {
                log.error(I18nUtil.getValue("com.glaway.ids.pm.plantemplate.plantemplate.updateFail"),e);
            } else if(PlantemplateConstant.REVISE.equals(type)) {
                log.error(I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.reviseFail"), e);
            }

        }finally{
            return fJson;
        }
    }


    @RequestMapping(params = "getPlanListFor")
    @ResponseBody
    public AjaxJson getPlanListFor(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        List<JSONObject> data= new ArrayList<JSONObject>();
        String planTemplateId = request.getParameter("planTemplateId");
        String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
        List<PlanDto> detailList = new ArrayList<>();
        if(!CommonUtil.isEmpty(detailStr)){
            detailList = JSON.parseArray(detailStr,PlanDto.class);
        }

        if(CommonUtil.isEmpty(detailList)){
            detailList = new ArrayList<PlanDto>();
            List<PlanTemplateDetailDto> tempDetailList = planTemplateService.getTemplatePlanDetailById(planTemplateId);
            /*Map<String, String> detailIdPreposeMap = planTemplateDetailService.getPlanTemplateOrProjTemplateDetailPreposes(planTemplateId, "");*/
            Map<String, String> detailIdPreposeIdMap = new HashMap<>();
            try{
                FeignJson fj = planTemplateDetailService.getPlanTemplateDetailPreposesId(planTemplateId);
                detailIdPreposeIdMap = (Map<String, String>)fj.getObj();
            }catch (Exception e){
                e.printStackTrace();
            }

            if(!CommonUtil.isEmpty(tempDetailList)){
                for(PlanTemplateDetailDto del : tempDetailList){

                    DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                    deliverablesInfo.setUseObjectId(del.getId());
                    deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                    List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                            deliverablesInfo, 1, 10, false);

                    PlanDto plan = new PlanDto();
                    plan.setId(del.getId());
                    plan.setPlanName(del.getName());
                    plan.setMilestone(del.getMilestone());
                    plan.setPlanOrder(String.valueOf(del.getNum()));
                    plan.setParentPlanId(del.getParentPlanId());
                    plan.setPlanLevel(del.getPlanLevel());
                    plan.setPlanNumber(del.getPlanNumber());
                    plan.setStoreyNo(del.getStoreyNo());
                    plan.setPlanTemplateId(del.getPlanTemplateId());
                    plan.setWorkTime(del.getWorkTime());
                    plan.setRemark(del.getRemark());
                    plan.setDeliInfoList(deliverablesInfoList2);
                    plan.setPreposeIds(detailIdPreposeIdMap.get(del.getId()));
                    detailList.add(plan);
                }
            }
            String detStr = JSON.toJSONString(detailList);
            redisService.setToRedis("TEMPLATEPLANLIST", planTemplateId, detStr);
        }


        if(!CommonUtil.isEmpty(detailList)){
            sortPlanStoreyNoForTemp(detailList);
            data = changePlansToJSONObjects(detailList);
        }
        Object json = com.alibaba.fastjson.JSONArray.toJSON(data);
        String resultJSON = JSON.toJSONString(data);
        j.setSuccess(true);
        j.setObj(resultJSON);
        j.setObj(json.toString());
        return j;
    }


    /**
     * 导出计划excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public void exportXls(PlanDto plan, HttpServletRequest request, HttpServletResponse response,
                          DataGrid dataGrid) {
        response.setContentType("application/vnd.ms-excel");
        String planTemplateId = request.getParameter("planTemplateId");
        List<PlanTemplateExcelVo> resultList = new ArrayList<PlanTemplateExcelVo>();
        Map<String, String> planLevelMap = new HashMap<String, String>();
        BusinessConfig businessConfig = new BusinessConfig();
        businessConfig.setConfigType(ConfigTypeConstants.PLANLEVEL);
        businessConfig.setStopFlag(ConfigStateConstants.START);
        businessConfig.setAvaliable("1");
        List<BusinessConfig> planLevelConfigs = businessConfigService.searchUseableBusinessConfigs(businessConfig);
     //   List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getId(), confog.getName());
        }
        Map<String, String> acticityTypaManagerMap = activityTypeManageFeign.getAllActivityTypeManageMap();
        try {
            //模板里面的计划ID-序号
            Map<String, String> map = new HashMap<String, String>();
            List<PlanDto> detailList = new ArrayList<>();
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
            if(!CommonUtil.isEmpty(detailList)) {
                for(PlanDto detail : detailList) {
                    map.put(detail.getId(), String.valueOf(detail.getPlanNumber()));
                }
                for(PlanDto detail : detailList) {
                    PlanTemplateExcelVo vo = new PlanTemplateExcelVo();
                    if(!CommonUtil.isEmpty(detail.getPlanNumber())) {
                        vo.setPlanNumber(String.valueOf(detail.getPlanNumber()));
                    }
                    if(!CommonUtil.isEmpty(detail.getParentPlanId())) {
                        vo.setParentNo(map.get(detail.getParentPlanId()));
                    }
                    vo.setPlanName(detail.getPlanName());
                    if(!CommonUtil.isEmpty(detail.getPlanLevel())) {
                        vo.setPlanLevel(planLevelMap.get(detail.getPlanLevel()));
                    }
                    vo.setWorktime(detail.getWorkTime());
                    if("true".equals(detail.getMilestone())) {
                        vo.setMilestone("是");
                    } else {
                        vo.setMilestone("否");
                    }
                    if("true".equals(detail.getIsNecessary())) {
                        vo.setIsNecessary("是");
                    } else {
                        vo.setIsNecessary("否");
                    }
                    String deliverablesName = "";
                    List<DeliverablesInfoDto> list = detail.getDeliInfoList();
                    for(DeliverablesInfoDto d : list) {
                        if(!CommonUtil.isEmpty(deliverablesName)) {
                            deliverablesName = deliverablesName + "," + d.getName();
                        } else {
                            deliverablesName = d.getName();
                        }
                    }
                    if(!CommonUtil.isEmpty(deliverablesName)) {
                        vo.setDeliverablesName(deliverablesName);
                    }
                    String preposeNos = "";
                    if(!CommonUtil.isEmpty(detail.getPreposeIds())) {
                        String[] preposeIds = detail.getPreposeIds().split(",");
                        for(String preposeId : preposeIds) {
                            if(!CommonUtil.isEmpty(preposeNos)) {
                                preposeNos = preposeNos + "," + map.get(preposeId);
                            } else {
                                preposeNos = map.get(preposeId);
                            }
                        }
                    }

                    if (!CommonUtil.isEmpty(detail.getTaskNameType()) && !CommonUtil.isEmpty(acticityTypaManagerMap)) {
                        String taskNameType = acticityTypaManagerMap.get(detail.getTaskNameType()) == null ? "" : acticityTypaManagerMap.get(detail.getTaskNameType());
                        vo.setTaskNameType(taskNameType);
                    }

                    vo.setPreposeNos(preposeNos);
                    resultList.add(vo);
                }
            }
            fileDownLoad(response, resultList, null, "true");
            log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plan.exportExcelSuccess"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 导出Excel（包含下载模板）
     *
     * @return
     */
    private void fileDownLoad(HttpServletResponse response, List<PlanTemplateExcelVo> dataList, Map<String, String> errorMsgMap, String isExport) {
        String title = PlantemplateConstant.TEMPLATE_EXPORT_NAME;
        String excelName = POIExcelUtil.createExcelName(isExport, title, errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        String attention = "注意事项：参考工期为1~9999之间的整数（里程碑工期可以为0）；前置计划序号和交付物物可以为多个，用逗号隔开";
        List<String> columns = new ArrayList<String>();
        columns = Arrays.asList(new String[] {"planNumber:序号", "parentNo:父计划序号", "planName:计划名称","taskNameType:计划类型",
                "planLevel:计划等级", "worktime:参考工期（天）", "milestone:里程碑", "isNecessary:是否必要",
                "preposeNos:前置计划序号", "deliverablesName:交付项名称"});
        List<String> requiredHeaders = Arrays.asList(new String[] {"序号", "计划名称", "计划类型", "里程碑", "参考工期（天）"});
        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        List<String> validationDataList = new ArrayList<String>();
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
        for(BusinessConfig c : planLevelList) {
            validationDataList.add(c.getName());
        }

        List<ActivityTypeManageDto> allActivityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(false);
        List<String> validationDataList3 = new ArrayList<String>();
        for (ActivityTypeManageDto dto: allActivityTypeManageList){
            validationDataList3.add(dto.getName());
        }

        List<String> validationDataList2 = new ArrayList<String>();
        validationDataList2.add("是");
        validationDataList2.add("否");

        validationDataMap.put("planLevel", validationDataList);
        validationDataMap.put("milestone", validationDataList2);
        validationDataMap.put("isNecessary", validationDataList2);
        validationDataMap.put("taskNameType", validationDataList3);

        //下载错误报告，活动类型数据转换
        if (!CommonUtil.isEmpty(dataList)) {
            Map<String, String> acvitityMap = allActivityTypeManageList.stream().collect(Collectors.toMap(ActivityTypeManageDto::getId,ActivityTypeManageDto::getName));
            for (int i = 0; i < dataList.size(); i++) {
                PlanTemplateExcelVo vo = new ObjectMapper().convertValue(dataList.get(i), PlanTemplateExcelVo.class);
                vo.setTaskNameType(acvitityMap.get(vo.getTaskNameType()));
                dataList.set(i,vo);
            }
//            for (Object obj : dataList) {
//                PlanTemplateExcelVo vo = new ObjectMapper().convertValue(obj, PlanTemplateExcelVo.class);
//                vo.setTaskNameType(acvitityMap.get(vo.getTaskNameType()));
//            }
        }

        ExcelVo excelVo = new ExcelVo();
        excelVo.setTitle(title);
        excelVo.setAttention(attention);
        excelVo.setColumns(columns);
        excelVo.setDataList(dataList);
        excelVo.setErrorMsgMap(errorMsgMap);
        excelVo.setRequiredHeaders(requiredHeaders);
        excelVo.setValidationDataMap(validationDataMap);

        HSSFWorkbook workbook = POIExcelUtil.getInstance().exportExcel(bExport, excelVo, "yyyy/MM/dd");

        POIExcelUtil.responseReportWithName(response, workbook, excelName);
    }


    /**
     * 计划模板excel导入页面
     * @param req
     * @return
     */
    @RequestMapping(params = "goImportExcel")
    public ModelAndView goImportExcel(HttpServletRequest req) {
        req.setAttribute("planTemplateId", req.getParameter("planTemplateId"));
        req.setAttribute("projectTemplateId", req.getParameter("projectTemplateId"));
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-importExcel");
    }

    /**
     * Description: <br>
     * 下载Excel模板
     *
     * @see
     */
    @RequestMapping(params = "doDownloadExcelTemplate")
    public void doDownloadExcelTemplate(HttpServletRequest request, HttpServletResponse response) throws FdException, Exception {
        String isExport=request.getParameter("isExport");
        List<PlanTemplateExcelVo> planList = new ArrayList<PlanTemplateExcelVo>();
        fileDownLoad(response, planList, null, isExport);
    }


    @RequestMapping(params = "doImportExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doImportExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planTemplateId = request.getParameter("planTemplateId");
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importExcel.success");
        try {
            String typeName = PlantemplateConstant.TEMPLATE_EXPORT_NAME;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                //存储导入的数据
                List<PlanTemplateExcelVo> dataTempList = new ArrayList<PlanTemplateExcelVo>();
                //存储错误信息
                Map<String, String> errorMsgMap = new HashMap<String, String>();
                // 获取上传文件对象
                MultipartFile file = entity.getValue();
                InputStream is = file.getInputStream();
                Workbook book = WorkbookFactory.create(is);
                Sheet sheet = book.getSheetAt(0);
                List<String> headers = Arrays.asList(new String[]{"序号", "父计划序号", "计划名称", "计划类型", "计划等级",
                        "参考工期（天）", "里程碑", "是否必要", "前置计划序号", "交付项名称"});
                if (!POIExcelUtil.doJudgeImport(sheet, headers)) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.errorTemplate")+ ";");
                    j.setSuccess(false);
                    log.error(typeName + "：导入失败，模板有问题");
                    book.close();
                    is.close();
                    return j;
                }
                else{
                    try{
                        long time = System.currentTimeMillis();
                        List<Map<String,String>> map= getDataList(sheet);
                        FeignJson fj = planTemplateService.getImportDataList(map,ResourceUtil.getCurrentUser().getId(), planTemplateId,ResourceUtil.getCurrentUserOrg().getId());
                        System.out.println("导入时间："+(System.currentTimeMillis()-time));
                        Map<String,Object> returnMap = new HashMap<>();
                        if (fj.isSuccess()){
                            returnMap = (Map<String, Object>)fj.getObj();
                            errorMsgMap = (Map<String, String>)returnMap.get("errorMsgMap");
                            dataTempList = (List<PlanTemplateExcelVo>)returnMap.get("dataTempList");
                        }
                    }
                    catch(Exception e){
                        j.setMsg(e.getMessage());
                        j.setSuccess(false);
                        e.printStackTrace();
                        return j;
                    }
                    if (0 < errorMsgMap.size()) {
                        j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.invalidData"));
                        j.setSuccess(false);
                        log.error(typeName + "：批量导入失败，部分数据无效");
                        //组装数据传到前台
                        Map<String, Object> dataAndErrorMap=new HashMap<String, Object>();
                        dataAndErrorMap.put("dataTempList", dataTempList);
                        dataAndErrorMap.put("errorMsgMap", errorMsgMap);
                        request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName() + "_planTemplateImportErrTmpList", dataTempList);
                        request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName() + "_planTemplateImportErrorMsgMapList", errorMsgMap);
                       j.setObj(dataAndErrorMap);
                        book.close();
                        is.close();
                        return j;
                    }
                    else{
                        //成功返回模板ID
                        j.setObj(planTemplateId);
                        log.info(message);
                    }
                }
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importFail");
            log.error(message, e);
            j.setMsg(e.getMessage());
            j.setSuccess(false);
            return j;
        }
        //校验成功
        j.setSuccess(true);
        j.setMsg(message);
        return j;
    }


    /**
     * Description: <br> 1、下载错误报告<br>
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "downErrorReport")
    public void downErrorReport(HttpServletRequest request, HttpServletResponse response) {
        String objStr = request.getParameter("dataListAndErrorMap");
        Map<String, Object> jsonMap = (Map<String, Object>)JSON.parse(objStr);
       /* if(!CommonUtil.isEmpty(request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planTemplateImportErrTmpList"))) {//if(!CommonUtil.isEmpty(jsonMap)) {
            List<PlanTemplateExcelVo> dataTempList = (List<PlanTemplateExcelVo>)request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planTemplateImportErrTmpList");
            Map<String, String> errorMsgMap = (Map<String, String>)request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planTemplateImportErrorMsgMapList");;
            fileDownLoad(response, dataTempList, errorMsgMap, "error");
            request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planTemplateImportErrTmpList","");
            request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planTemplateImportErrorMsgMapList","");
        }*/

       if (!CommonUtil.isEmpty(jsonMap)) {
           List<PlanTemplateExcelVo> objList = (List<PlanTemplateExcelVo>)jsonMap.get("dataTempList");
           Map<String, String> errorMsgMap = (Map<String, String>)jsonMap.get("errorMsgMap");
           fileDownLoad(response, objList, errorMsgMap, "error");
       }
    }


    private List<Map<String,String>> getDataList(Sheet sheet){
        List <Map<String,String>> list = new ArrayList<>();
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row r = it.next();
            int rowNum = r.getRowNum();
            Row row = sheet.getRow(rowNum);
            if (rowNum >= 3) {
                Map<String,String> map = new HashMap<>();
                Cell numberCell = row.getCell(0);
                Cell parentNumberCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                Cell taskNameTypeCell = row.getCell(3);
                Cell levelCell = row.getCell(4);
                Cell worktimeCell = row.getCell(5);
                Cell milestoneCell = row.getCell(6);
                Cell isNecessaryCell = row.getCell(7);
                Cell preposeNumberCell = row.getCell(8);
                Cell deliverNameCell = row.getCell(9);

                String number = POIExcelUtil.getCellValue(numberCell).trim();
                String parentNumber = POIExcelUtil.getCellValue(parentNumberCell).trim();
                String name = POIExcelUtil.getCellValue(nameCell).trim();
                String taskNameType = POIExcelUtil.getCellValue(taskNameTypeCell).trim();
                String level = POIExcelUtil.getCellValue(levelCell).trim();
                String worktime = POIExcelUtil.getCellValue(worktimeCell).trim();
                String milestone = POIExcelUtil.getCellValue(milestoneCell).trim();
                String isNecessary = POIExcelUtil.getCellValue(isNecessaryCell).trim();
                String preposeNumbers = POIExcelUtil.getCellValue(preposeNumberCell).trim();
                preposeNumbers = preposeNumbers.replace("，", ",");
                String deliverNames = POIExcelUtil.getCellValue(deliverNameCell).trim();
                deliverNames = deliverNames.replace("，", ",");
                map.put("number", number);
                map.put("parentNumber", parentNumber);
                map.put("taskNameType", taskNameType);
                map.put("name", name);
                map.put("worktime", worktime);
                map.put("level",level);
                map.put("milestone", milestone);
                map.put("preposeNumbers", preposeNumbers);
                map.put("deliverName", deliverNames);
                map.put("isNecessary", isNecessary);
                list.add(map);
            }
        }
        return list;
    }



    private void getImportDataList(Sheet sheet, List<PlanTemplateExcelVo> dataTempList, Map<String, String> errorMsgMap,
                                   String planTemplateId) {
        // 获取当前系统是否启用标准名称库
        FeignJson jsonF = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(jsonF.getObj());
        boolean isStandard = false;
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                || NameStandardSwitchConstants.FORCEUSENAMESTANDARD
                .equals(switchStr)
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE
                .equals(switchStr)) {
            isStandard = true;
        }
        Map<String, String> standardNameMap = new HashMap<String, String>();
        Map<String, String> deliveryNameMap = new HashMap<String, String>();
        if (isStandard) {
            NameStandardDto ns = new NameStandardDto();
            ns.setStopFlag(ConfigStateConstants.START);
            List<NameStandardDto> list = nameStandardService.searchNameStandardsAccurate(ns);
            for (NameStandardDto n : list) {
                standardNameMap.put(n.getName(), n.getActiveCategory());
            }

            DeliveryStandardDto ds = new DeliveryStandardDto();
            ds.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> list2 = deliveryStandardService.searchDeliveryStandardAccurate(ds);
            for (DeliveryStandardDto d : list2) {
                deliveryNameMap.put(d.getName(), d.getName());
            }
        }
        Iterator<Row> it = sheet.iterator();
        Map<String, String> planLevelMap = new HashMap<String, String>();
        BusinessConfig businessConfig = new BusinessConfig();
        businessConfig.setConfigType(ConfigTypeConstants.PLANLEVEL);
        businessConfig.setStopFlag(ConfigStateConstants.START);
        businessConfig.setAvaliable("1");
        List<BusinessConfig> planLevelConfigs = businessConfigService.searchUseableBusinessConfigs(businessConfig);
    //    List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getName(), confog.getId());
        }
        // 用于校验和预设数据的保存
        Map<String, PlanTemplateExcelVo> excelMap = new HashMap<String, PlanTemplateExcelVo>();
        // 计划序号集合
        List<String> numList = new ArrayList<String>();
        while (it.hasNext()) {
            Row r = it.next();
            int rowNum = r.getRowNum();
            Row row = sheet.getRow(rowNum);
            if (rowNum >= 3) {
                Cell numberCell = row.getCell(0);
                Cell parentNumberCell = row.getCell(1);
                Cell nameCell = row.getCell(2);
                Cell taskNameTypeCell = row.getCell(3);
                Cell levelCell = row.getCell(4);
                Cell worktimeCell = row.getCell(5);
                Cell milestoneCell = row.getCell(6);
                Cell isNecessaryCell = row.getCell(7);
                Cell preposeNumberCell = row.getCell(8);
                Cell deliverNameCell = row.getCell(9);

                String number = POIExcelUtil.getCellValue(numberCell).trim();
                String parentNumber = POIExcelUtil.getCellValue(parentNumberCell).trim();
                String name = POIExcelUtil.getCellValue(nameCell).trim();
                String taskNameType = POIExcelUtil.getCellValue(taskNameTypeCell).trim();
                String level = POIExcelUtil.getCellValue(levelCell).trim();
                String worktime = POIExcelUtil.getCellValue(worktimeCell).trim();
                String milestone = POIExcelUtil.getCellValue(milestoneCell).trim();
                String isNecessary = POIExcelUtil.getCellValue(isNecessaryCell).trim();
                String preposeNumbers = POIExcelUtil.getCellValue(preposeNumberCell).trim();
                preposeNumbers = preposeNumbers.replace("，", ",");
                String deliverNames = POIExcelUtil.getCellValue(deliverNameCell).trim();
                deliverNames = deliverNames.replace("，", ",");

                String checkStr = number+";" + name + ";" + taskNameType + ";" + level + ";" + worktime + ";" + milestone + ";" +
                        deliverNames + ";" + rowNum;
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("rowNum",rowNum);
                paramMap.put("checkStr",checkStr);
                paramMap.put("switchStr",switchStr);
                paramMap.put("standardNameMap",standardNameMap);
                paramMap.put("errorMsgMap",errorMsgMap);
                paramMap.put("deliveryNameMap",deliveryNameMap);
                paramMap.put("numList",numList);
                paramMap.put("planLevelMap",planLevelMap);
                planTemplateService.checkData(paramMap);

                PlanTemplateExcelVo vo =  new PlanTemplateExcelVo();
                //计划ID
                vo.setId(UUIDGenerator.generate());
                vo.setPlanNumber(number);
                vo.setParentNo(parentNumber);
                vo.setPlanName(name);
                vo.setTaskNameType(taskNameType);
                vo.setPlanLevel(level);
                vo.setWorktime(worktime);
                vo.setMilestone(milestone);
                vo.setIsNecessary(isNecessary);
                vo.setPreposeNos(preposeNumbers);
                vo.setDeliverablesName(deliverNames);
                vo.setRowNum(rowNum);
                dataTempList.add(vo);
                excelMap.put(number, vo);
            }
        }
        // 若模板中的业务数据为空，则提示且不进行导入操作
        if (CommonUtil.isEmpty(dataTempList)) {
            throw new GWException(
                    I18nUtil.getValue("com.glaway.ids.common.importDataIsNull"));
        } else if (dataTempList.size() < 1) {
            throw new GWException(
                    I18nUtil.getValue("com.glaway.ids.pm.project.plan.importExcel.checkLeastOnePlan"));
        }
        //父计划,前置计划
        Map<String,Object> map = new HashMap<>();
        map.put("excelMap",excelMap);
        map.put("errorMsgMap",errorMsgMap);
        map.put("numList",numList);
        planTemplateService.checkData2(map);
        if (0 == errorMsgMap.size()) {
            //计划模板ID
            PlanTemplateDto planTemplate = new PlanTemplateDto();
            planTemplate.setId(planTemplateId);
            Map<String,Object> objMap = new HashMap<>();
            objMap.put("planTemplate",planTemplate);
            objMap.put("dataTempList",dataTempList);
            objMap.put("excelMap",excelMap);
            objMap.put("planLevelMap",planLevelMap);
            objMap.put("switchStr",switchStr);
            objMap.put("curUser",ResourceUtil.getCurrentUser());
            objMap.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
            planTemplateService.savePlanTemplateDetailByExcel(objMap);
        }
    }


    /**
     * 跳转至复制模板页面
     * @param tptmpl
     * @param req
     * @return
     */
    @RequestMapping(params = "goCopy")
    public ModelAndView goCopy(PlanTemplateDto tptmpl, HttpServletRequest req) {
        String planTemplateId = req.getParameter("planTemplateId");
        if(!CommonUtil.isEmpty(planTemplateId)) {
            tptmpl = planTemplateService.getPlanTemplateEntity(planTemplateId);
            req.setAttribute("tptmpl_", tptmpl);
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-copy");
    }


    /**
     * 复制模板
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doCopy")
    @ResponseBody
    public AjaxJson doCopy(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.copySuccess");
        try{
            String planTemplateId = request.getParameter("id");
            String name = request.getParameter("name");
            String remark = request.getParameter("remark");
            boolean flag = planTemplateService.checkTemplateNameBeforeSave(name, "");
            if(flag){
                planTemplateService.copyTemplate(planTemplateId, name, remark,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
                j.setSuccess(true);
                j.setMsg(message);
                log.info(message);
            }else{
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.nameRepeated"));
            }
        } catch(Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.copyFail");
            j.setSuccess(false);
            j.setMsg(message);
            log.error(message);
        }finally{
            return j;
        }
    }


    /**
     * 回退模板
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.plantemplate.plantemplate.backSuccess");
        try{
            String id = request.getParameter("id");
            String bizVersion = request.getParameter("bizVersion");
            if(!CommonUtil.isEmpty(bizVersion)){
                String versonNumber = bizVersion.split("\\.")[1];
                if (versonNumber.equals("1")) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.plantemplate.cannotBackFirst");
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }
                PlanTemplateDto template = planTemplateService.findBusinessObjectById(id);
                if(!CommonUtil.isEmpty(template)) {
                    planTemplateService.backVesion(template,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
                    j.setSuccess(true);
                    j.setMsg(message);
                    log.info(message);
                }
            } else {
                j.setSuccess(false);
                return j;
            }
        } catch(Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.plantemplate.plantemplate.backFail");
            j.setSuccess(false);
            j.setMsg(message);
            log.error(message);
        } finally {
            return j;
        }
    }


    /**
     * 撤销模板
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doRevoke")
    @ResponseBody
    public AjaxJson doRevoke(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.revokeSuccess");
        try{
            String id = request.getParameter("id");
            String bizVersion = request.getParameter("bizVersion");
            if(!CommonUtil.isEmpty(bizVersion)){
                String versonNumber = bizVersion.split("\\.")[0];
                if ("A".equals(versonNumber)) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.plantemplate.cannotBackFirstMajor");
                    j.setSuccess(false);
                    return j;
                }
                PlanTemplateDto template = planTemplateService.findBusinessObjectById(id);
                if(!CommonUtil.isEmpty(template)) {
                    planTemplateService.revokeVesion(template,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
                    j.setSuccess(true);
                    j.setMsg(message);
                    log.info(message);
                }
            } else {
                j.setSuccess(false);
                return j;
            }
        } catch(Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.revokeFail");
            j.setSuccess(false);
            j.setMsg(message);
            log.error(message);
        }finally{
            return j;
        }
    }


    /**
     * 批量页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goModifyMass")
    public ModelAndView goModifyMass(PlanDto plan, HttpServletRequest req) {

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        // List<BusinessConfig> planLevelList =
        // businessConfigService.searchBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        String b = JSON.toJSONString(planLevelList);
        req.setAttribute("planLevelList1", b);
        req.setAttribute("planLevelList", jonStr);
        req.setAttribute("planTemplateId", req.getParameter("planTemplateId"));
        req.setAttribute("projectTemplateId", req.getParameter("id"));
        req.setAttribute("type", req.getParameter("type"));

        return new ModelAndView("com/glaway/ids/project/plantemplate/planListModifyMassForTemplate");
    }

    /**
     * 批量修改初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "getListForPlanMassModify")
    public void getListForPlanMassModify(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();

        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("projectTemplateId");
        List<PlanDto> detailList = new ArrayList<PlanDto>();
        String detailStr = "";
        if(StringUtil.isNotEmpty(projectTemplateId)){
            detailStr= (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }

        }else{
            detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }

        if(!CommonUtil.isEmpty(detailList)){
            for(PlanDto node : detailList){
                if(!CommonUtil.isEmpty(node.getParentPlanId())){
                    node.set_parentId(node.getParentPlanId());
                }
                node.setOrder(String.valueOf(node.getStoreyNo()));
            }
        }

        DataGridReturn data = new DataGridReturn(detailList.size(), detailList);

        String json = gson.toJson(data);

        TagUtil.ajaxResponse(response, json);
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "saveModifyList")
    @ResponseBody
    public AjaxJson saveModifyList(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        try{
            String projectTemplateId = request.getParameter("projectTemplateId");
            String planTemplateId = request.getParameter("planTemplateId");
            String[] id = request.getParameter("ids").split(",");
            String[] planLevelId = request.getParameter("planLeveIds").split(",");
            String[] milestone = request.getParameter("milestones").split(",");
            String[] workTime = request.getParameter("workTimes").split(",");
            List<PlanDto> templateDetailList = new ArrayList<PlanDto>();
            String detailStr = "";
            if(StringUtil.isNotEmpty(projectTemplateId)){
                detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
                if(!CommonUtil.isEmpty(detailStr)){
                    templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
                }
            }else{
                detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
                if(!CommonUtil.isEmpty(detailStr)){
                    templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
                }
            }
            for(int i = 0; i < id.length; i++){
                for(PlanDto p : templateDetailList){
                    if(p.getId().equals(id[i])){
                        if(!CommonUtil.isEmpty(planLevelId)){
                            if(planLevelId.length > i){
                                if(!CommonUtil.isEmpty(planLevelId[i])){
                                    p.setPlanLevel(planLevelId[i].trim());
                                }
                            }
                        }

                        if(milestone[i].trim().equals("是") || milestone[i].trim().equals("true")){
                            p.setMilestone("true");
                        }else{
                            p.setMilestone("false");
                        }
                        p.setWorkTime(workTime[i].trim());
                    }
                }
            }
            String delStr = JSON.toJSONString(templateDetailList);
            if(StringUtil.isNotEmpty(projectTemplateId)){
                redisService.setToRedis("PROJTMPPLANLIST", projectTemplateId, delStr);
            }else{
                redisService.setToRedis("TEMPLATEPLANLIST", planTemplateId, delStr);
            }

        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }

    /**
     * 跳转至版本点击页面
     * @param tptmpl
     * @param req
     * @return
     */
    @RequestMapping(params = "goShowDetail")
    public ModelAndView goShowDetail(PlanTemplateDto tptmpl, HttpServletRequest req) {
        String planTemplateId = req.getParameter("id");
        if(!CommonUtil.isEmpty(planTemplateId)) {
            tptmpl = planTemplateService.getPlanTemplateEntity(planTemplateId);
            req.setAttribute("tptmpl_", tptmpl);
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-showDetail");
    }


    /**
     * 计划另存为模板页面跳转
     *
     * @params
     */
    @RequestMapping(params = "goSavePlanAsTemple")
    public ModelAndView goSavePlanAsTemple(HttpServletRequest req) {
        PlanDto t = new PlanDto();
        if (StringUtils.isNotEmpty(req.getParameter("planId"))) {
            t = planService.getPlanEntity( req.getParameter("planId"));
        }
        TSUserDto creator = UserUtil.getInstance().getUser();
        String projectId = req.getParameter("projectId");
        String type = req.getParameter("type");
        String url = "planTemplateController.do?getPlanList";
        req.setAttribute("plan_", t);
        req.setAttribute("creator_", creator);
        req.setAttribute("projectId", projectId);
        req.setAttribute("planTemplateId", UUIDGenerator.generate());
        req.setAttribute("url", url);
        if(!CommonUtil.isEmpty(type)){
            req.setAttribute("type", "add");
            req.setAttribute("fromType",req.getParameter("fromType"));
            req.setAttribute("flag", type);
            return new ModelAndView("com/glaway/ids/project/plan/planTemplateAdd");
        }
        return new ModelAndView("com/glaway/ids/project/plan/plan-savePlanAsTemple");
    }


    /**
     * 提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goSubmitApprove")
    public ModelAndView goSubmitApprove(PlanTemplateDto planTemplate, HttpServletRequest req) {
        PlanTemplateDto t = planTemplateService.getPlanTemplateEntity(req.getParameter("planTemplateId"));
        req.setAttribute("planTemplate_", t);
        req.setAttribute("supportFlag", "approve");
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-submitApprove");
    }


    /**
     * 通过计划保存模板
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "savePlanTemplateByPlanject")
    @ResponseBody
    public AjaxJson savePlanTemplateByPlanject(PlanTemplateDto planTemplate,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.savePlanTemplateByPlanjectSuccess");
        try {
            planTemplate = new PlanTemplateDto();
            String planId = request.getParameter("planId");
            String projectId = request.getParameter("projectId");
            planTemplate.setName(request.getParameter("name"));
            planTemplate.setCreateBy(request.getParameter("creatorId"));
            planTemplate.setRemark(request.getParameter("remark"));
            //修改BUG另存为计划模板时 批量保存计划带入不全问题   验证顺序颠倒
            if (StringUtils.isNotEmpty(projectId)) {
                planTemplateService.savePlanTemplateByPlanject(projectId, "", planTemplate,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            }
            else if (StringUtils.isNotEmpty(planId)) {
                planTemplateService.savePlanTemplateByPlanject("", planId, planTemplate,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
            }

            j.setObj(planTemplate.getId());
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.savePlanTemplateByPlanjectFail");
            if(StringUtil.isNotEmpty(e.getMessage())){
                message = e.getMessage();
            }
            j.setSuccess(false);
            e.printStackTrace();
            throw new GWException(e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 项目计划模板列表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "planTemplate_import")
    public ModelAndView planTemplate_import(HttpServletRequest request) {
        if (StringUtils.isNotEmpty(request.getParameter("isDialog"))) {
            return new ModelAndView(
                    "com/glaway/ids/project/plantemplate/planTemplateList-dialog-import");
        }
        else {
            return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateList");
        }
    }


    @RequestMapping(params = "goBasicCheck")
    public ModelAndView goBasicCheck(HttpServletRequest request){
        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("projectTemplateId");
        String planId = request.getParameter("planId");
        List<PlanDto> detailList = new ArrayList<PlanDto>();
        if(StringUtil.isNotEmpty(projectTemplateId)){
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                 detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }else{
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                detailList = JSON.parseArray(detailStr,PlanDto.class);
            }
        }
        PlanDto detail = new PlanDto();
        if(!CommonUtil.isEmpty(detailList)){

          /*  Map<String, String> planIdMaps = new HashMap<String, String>();

            for(PlanDto p : detailList){
                planIdMaps.put(p.getId(), p.getPlanName());
            }*/


            for(PlanDto template : detailList){
                if(template.getId().equals(planId)){
                    detail = template;
                    if(!CommonUtil.isEmpty(detail.getMilestone())){
                        if(detail.getMilestone().equals("true")){
                            detail.setMilestoneName("是");
                        }else{
                            detail.setMilestoneName("否");
                        }
                    }

                    if(!CommonUtil.isEmpty(detail.getPlanLevel())){
                        BusinessConfig config = businessConfigService.getBusinessConfig(detail.getPlanLevel());
                        if(!CommonUtil.isEmpty(config)){
                            detail.setPlanLevelName(config.getName() == null ? "" : config.getName());
                        }
                    }

                    if(!CommonUtil.isEmpty(detail.getPreposeIds())){
                        String preposePlanName = "";
                        if(!CommonUtil.isEmpty(detail.getPreposePlans())) {
                            preposePlanName = detail.getPreposePlans();
                        }else {
                            for(String preposeId : detail.getPreposeIds().split(",")){
                                PlanDto planDto = planService.getPlanEntity(preposeId);
                                if (CommonUtil.isEmpty(planDto)) {
                                    for (PlanDto dto : detailList) {
                                        if (dto.getId().equals(preposeId)) {
                                            planDto = dto;
                                            break;
                                        }
                                    }
                                }
                                if(CommonUtil.isEmpty(preposePlanName)){
                                    preposePlanName =  planDto.getPlanName();
                                }else{
                                    preposePlanName = preposePlanName+","+planDto.getPlanName();
                                }
                            }
                        }

                        detail.setPreposePlans(preposePlanName);
                    }

                    break;
                }
            }
        }
        request.setAttribute("detail", detail);
        return new ModelAndView(
                "com/glaway/ids/project/plan/planTemplateBasic-check");
    }


    /**
     * 判断能否有导入覆盖
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkBeforeImport")
    @ResponseBody
    public AjaxJson checkBeforeImport( HttpServletRequest request, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.doSuccess");
        String type = request.getParameter("type");
        String templateId = "";
        List<PlanTemplateDetailDto> list = new ArrayList<PlanTemplateDetailDto>();
        if("projtemplate".equals(type)) {
            templateId = request.getParameter("projectTemplateId");
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", templateId);
            if(!CommonUtil.isEmpty(detailStr)){
                list = JSON.parseArray(detailStr,PlanTemplateDetailDto.class);
            }
        } else {
            templateId = request.getParameter("planTemplateId");
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", templateId);
            if(!CommonUtil.isEmpty(detailStr)){
                list = JSON.parseArray(detailStr,PlanTemplateDetailDto.class);
            }
        }
        if (CommonUtil.isEmpty(list)) {
            msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.noData");
        }
        j.setMsg(msg);
        return j;
    }

    /**
     * 计划模板mpp导入
     *
     * @author zhousuxia
     * @version 2019年1月24日
     * @see PlanTemplateController
     * @since
     */
    @RequestMapping(params = "goImportTemplatePlan")
    public ModelAndView goImportTemplatePlan(PlanTemplateDto planTemplate,HttpServletRequest req) {
        req.setAttribute("currentUserId", UserUtil.getInstance().getUser().getId());
        req.setAttribute("planTemplateId", req.getParameter("planTemplateId"));
        if (StringUtil.isNotEmpty(planTemplate.getId())) {
            planTemplate = planTemplateService.getPlanTemplateEntity(planTemplate.getId());
            req.setAttribute("planTemplate_", planTemplate);
            req.setAttribute("planTemplateId", planTemplate.getId());
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplate-importMpp");
    }



    /**
     * 导入MPP文件，生成计划模板
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doImportPlanTemMpp", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doImportPlanTemMpp(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.importSuccess");
        try {
            String currentUserId = request.getParameter("currentUserId");
            String planTemplateId = request.getParameter("planTemplateId");
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();

            PlanTemplateDto planTemplateReq = new PlanTemplateDto();
            planTemplateReq.setId(planTemplateId);
            planTemplateReq.setCreator(currentUserId);

            List<List<String>> preposePlanIdList = new ArrayList<List<String>>();
            List<List<Map<String, Object>>> taskMapList = new ArrayList<List<Map<String, Object>>>();

            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                CommonsMultipartFile cf = (CommonsMultipartFile)mf;// 转成File传输
                InputStream inputstream = cf.getInputStream();
                MppDirector mppDirector = new MppDirector();
                List<Task> taskList = mppDirector.construct(inputstream);
                preposePlanIdList.add(getPreposePlanIdList(taskList));
                taskMapList.add(getDetailInfo(taskList));
            }

            Map<String,Object> map = new HashMap<>();
            map.put("planTemplate",planTemplateReq);
            map.put("mapKeys",mapKeys);
            map.put("taskList",taskMapList);
            map.put("preposePlanIdList",preposePlanIdList);
            map.put("curUser",ResourceUtil.getCurrentUser());
            map.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
            planTemplateService.importPlanTemplateMpp(map);
            j.setSuccess(true);
            j.setMsg(message);

        }
        catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            j.setMsg(e.getMessage());
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plantemplate.plantemplate.doImportTwo");
            log.error(message, e);
            throw new GWException(e);
        }
        finally {
            return j;
        }
    }

    public List<String> getPreposePlanIdList(List<Task> taskList) {
        List<String> list = new ArrayList<String>();
        for (Task task : taskList) {
            if (task.getID() != 0 && StringUtils.isNotEmpty(task.getName())
                    && !CommonUtil.isEmpty(task.getPredecessors())) {
                List<Relation> predList = task.getPredecessors();
                for (Relation relation : predList) {
                    String id = relation.getTargetTask().getID().toString();
                    if (StringUtils.isNotEmpty(id)) {
                        list.add(id);
                    }
                }
            }
        }
        return list;
    }

    public List<Map<String, Object>> getDetailInfo(List<Task> taskList) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Task task : taskList) {
            if (task.getID()>0){
                Map<String,Object> map = new HashMap<>();
                map.put("id", task.getID());
                map.put("name", task.getName());
                map.put("ownerName", task.getText((int) MppParseUtil.columnIndexMap.get(ConfigTypeConstants.OWNERNAME)));
                map.put("start", task.getStart());
                map.put("finish", task.getFinish());
                map.put("planlevelpd", task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.PLANLEVEL)));
                if (task.getParentTask()!=null&& StringUtils.isNotEmpty(task.getParentTask().getID().toString())){
                    map.put("parentId", task.getParentTask().getID());
                }else{
                    map.put("parentId", null);
                }
                map.put("milestone", task.getMilestone());
                if (StringUtils.isNotEmpty(task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.DELIVER_STANDARDNAME)))){
                    map.put("documents", task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.DELIVER_STANDARDNAME)));
                }else{
                    map.put("documents", "");
                }
                if (StringUtils.isNotEmpty(task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.TASK_NAME_TYPE)))){
                    map.put("taskNameType", task.getText((int)MppParseUtil.columnIndexMap.get(ConfigTypeConstants.TASK_NAME_TYPE)));
                }
                /*else{
                    throw new GWException("任务类型不能为空");
                }*/

                list.add(map);
            }
        }
        return list;
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchPlanTemplate")
    @ResponseBody
    public AjaxJson doBatchPlanTemplate(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String ids = request.getParameter("ids");
            String projectTemplateId = request.getParameter("projectTemplateId");
            String planTemplateId = request.getParameter("planTemplateId");
            FeignJson fj = planTemplateService.doDeletePlanTemplate(ids,planTemplateId, projectTemplateId);
            if(fj.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }

        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "setPlanInputs")
    @ResponseBody
    public AjaxJson setPlanInputs(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String planId = request.getParameter("planId");
        String tempId = request.getParameter("tempId");
        String useObjectId = request.getParameter("useObjectId");
        String inputsName = request.getParameter("inputsName");
        String planTemplateId = request.getParameter("planTemplateId");
        try{
            List<InputsDto> list = new ArrayList<>();
            String inpStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inpStr)){
                list = JSON.parseArray(inpStr,InputsDto.class);
            }

            List<PlanDto> detailList = new ArrayList<>();
            String planTemStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
            if(!CommonUtil.isEmpty(planTemStr)){
                detailList = JSON.parseArray(planTemStr,PlanDto.class);
            }

            List<DeliverablesInfoDto> deliList = new ArrayList<DeliverablesInfoDto>();
            DeliverablesInfoDto deli = new DeliverablesInfoDto();
            if(!CommonUtil.isEmpty(detailList)){
                for(PlanDto detail : detailList){
                    if(detail.getId().equals(planId)){
                        deliList = detail.getDeliInfoList();
                        if(!CommonUtil.isEmpty(deliList)){
                            for(DeliverablesInfoDto de : deliList){
                                if(de.getName().equals(inputsName)){
                                    deli = de;
                                }
                            }
                        }
                    }
                }
            }

            if(!CommonUtil.isEmpty(list)){
                for(InputsDto input :list){
                    if(!CommonUtil.isEmpty(input.getId()) && input.getId().equals(tempId)){
                        if(!CommonUtil.isEmpty(deli)){
                            input.setDocName(deli.getDocName());
//                            input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                            input.setOriginType("PLANTEMPLATE");
                            input.setDocId(deli.getDocId());
                            input.setOriginObjectId(planId);
                            input.setOriginDeliverablesInfoId(deli.getId());
                        }
                    }
                }
            }

            String inputStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId,inputStr);

        }catch(Exception e){

        }finally{
            return j;
        }
    }

    @RequestMapping(params = "searchDatagridForPrepose")
    @ResponseBody
    public AjaxJson searchDatagridForPrepose(HttpServletRequest request,HttpServletResponse response){
        AjaxJson j = new AjaxJson();
        List<JSONObject> data= new ArrayList<JSONObject>();
        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("projectTemplateId");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String workTime = request.getParameter("workTime");
        String workTime_condition = request.getParameter("workTime_condition");

        String planId = request.getParameter("planId");

//        List<Plan> detailList = (List<Plan>)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
        List<PlanDto> detailList = new ArrayList<PlanDto>();
        if(StringUtil.isNotEmpty(projectTemplateId)){
            detailList = (List<PlanDto>)redisService.getFromRedis("PROJTMPPLANLIST", projectTemplateId);
        }else{
            detailList = (List<PlanDto>)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);
        }

        int i = 0;
        if(!CommonUtil.isEmpty(detailList)){
            Map<String, String> planIdMaps = new HashMap<String, String>();
            Map<String, PlanDto> listMaps = new HashMap<String, PlanDto>();
            List<PlanDto> curPlanList = new ArrayList<PlanDto>();

            PlanDto currentPlan = null;
            int length = detailList.size();
            for(PlanDto p : detailList){
                if (p.getId().equals(planId)) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }

                if (currentPlan != null) {
                    detailList.remove(currentPlan);
                }
                listMaps.put(p.getId(), p);
                i++;
                if(i == length){
                    break;
                }
            }
            if(CommonUtil.isEmpty(planName) && CommonUtil.isEmpty(planLevel) && CommonUtil.isEmpty(workTime)){
                curPlanList = detailList;
            }else{
                for(PlanDto plan : detailList){
                    if(!CommonUtil.isEmpty(planName) && plan.getPlanName().toLowerCase().contains(planName.toLowerCase())){
                        if(CommonUtil.isEmpty(planLevel) && CommonUtil.isEmpty(workTime)){
                            curPlanList.add(plan);
                        }else{
                            if(!CommonUtil.isEmpty(planLevel) && !CommonUtil.isEmpty(plan.getPlanLevel()) && planLevel.contains(plan.getPlanLevel())){
                                if(CommonUtil.isEmpty(workTime)){
                                    curPlanList.add(plan);
                                }else{
                                    if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                                        curPlanList.add(plan);
                                    }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                                        curPlanList.add(plan);
                                    }
                                }
                            }
                        }
                    }else if(!CommonUtil.isEmpty(planLevel) && !CommonUtil.isEmpty(plan.getPlanLevel()) && planLevel.contains(plan.getPlanLevel())){
                        if(CommonUtil.isEmpty(workTime)){
                            curPlanList.add(plan);
                        }else{
                            if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                                curPlanList.add(plan);
                            }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                                curPlanList.add(plan);
                            }
                        }
                    }else if(CommonUtil.isEmpty(planName) && CommonUtil.isEmpty(planLevel) && !CommonUtil.isEmpty(workTime)){
                        if(workTime_condition.equals("le") && Integer.valueOf(plan.getWorkTime()) <= Integer.valueOf(workTime)){
                            curPlanList.add(plan);
                        }else if(workTime_condition.equals("ge") && Integer.valueOf(plan.getWorkTime()) >= Integer.valueOf(workTime)){
                            curPlanList.add(plan);
                        }
                    }
                }

            }

            List<PlanDto> tempList = new ArrayList<PlanDto>();
            for(PlanDto p : curPlanList){
                getParentList(tempList,p.getParentPlanId(),planIdMaps, listMaps);
            }

            tempList.addAll(curPlanList);

            data = changePlansToJSONObjectsForPrepose(tempList);

            /* }*/

            /*for(int i = 0;i<data.size();i++){
                this.findSubNodeByPidForPrepose(detailList,data.get(i),planIdMaps);
            }*/

        }



        String resultJSON = JSON.toJSONString(data);

        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;

    }

    private void getParentList(List<PlanDto> tempList, String parentPlanId,
                               Map<String, String> planIdMaps, Map<String, PlanDto> listMap) {
        if (StringUtils.isNotEmpty(parentPlanId)
                && StringUtils.isNotEmpty(planIdMaps.get(parentPlanId))) {
            if (listMap.get(parentPlanId) != null) {
                PlanDto parent = listMap.get(parentPlanId);
                if (StringUtils.isNotEmpty(parent.getParentPlanId())) {
                    parentPlanId = parent.getParentPlanId();
                }
                else {
                    parentPlanId = "";
                }
                parent.setResult("true");
                tempList.add(parent);
            }
            if (StringUtils.isNotEmpty(parentPlanId)) {
                getParentList(tempList, parentPlanId, planIdMaps, listMap);
            }
        }
    }

}
