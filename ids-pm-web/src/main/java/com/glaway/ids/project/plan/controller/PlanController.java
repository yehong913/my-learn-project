package com.glaway.ids.project.plan.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.fdexception.FdException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.DateUtil;
import com.glaway.foundation.common.util.*;
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
import com.glaway.foundation.fdk.dev.dto.outwardextension.OutwardExtensionDto;
import com.glaway.foundation.fdk.dev.service.FeignOutwardExtensionService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.calendar.FeignCalendarService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignGroupService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.RiskProblemsSearchStatus;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.*;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.DataSourceObjectFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.dto.TabCombinationTemplateDto;
import com.glaway.ids.planGeneral.tabCombinationTemplate.feign.TabCombinationTemplateFeignServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.vo.CombinationTemplateVo;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.plan.vo.PlanAuthorityVo;
import com.glaway.ids.project.plan.vo.PlanDataVo;
import com.glaway.ids.project.plan.vo.PlanExcelSaveVo;
import com.glaway.ids.project.plan.vo.PlanLoadUrlVo;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateDetailReq;
import com.glaway.ids.project.planview.dto.*;
import com.glaway.ids.project.plm.service.IDSIntegratedPLMService;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.AttachmentVo;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.util.CommonInitUtil;
import com.glaway.ids.util.mpputil.MppConstants;
import com.glaway.ids.util.mpputil.MppDirector;
import com.glaway.ids.util.mpputil.MppInfo;
import com.glaway.ids.util.mpputil.MppParseUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Task;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.writer.ProjectWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 项目模板Controller
 * @author wangshen
 * @date 2015-03-23 15:59:25
 * @version V1.0
 */
@Controller
@RequestMapping("/planController")
public class PlanController extends BaseController {

    /**
     *
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanController.class);

    /**
     * 项目服务实现接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;


    /**
     * 项目团队服务实现接口
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private PlanRemoteFeignServiceI planService;

    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoService;


    @Autowired
    private FeignSystemService feignSystemService;


    @Autowired
    private FeignGroupService groupService;


    @Autowired
    private FeignUserService userService;


    @Autowired
    private FeignCalendarService calendarService;


    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;


    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;


    @Autowired
    private RedisService redisService;


    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;


    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliveryStandardService;

    @Autowired
    PreposePlanRemoteFeignServiceI planRemoteFeignServiceI;

    @Autowired
    ParamSwitchRemoteFeignServiceI paramSwitchRemoteFeignServiceI;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    @Autowired
    private FeignOutwardExtensionService outwardExtensionService;

    @Autowired
    private InputsRemoteFeignServiceI inputsFeignService;

    @Autowired
    private FeignDepartService departService;

    @Autowired
    private ActivityTypeManageFeign activityTypeManage;

    //页签模版管理Service
    @Autowired
    private TabTemplateServiceI tabTemplateServiceImpl;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCombinationTemplateFeignService;

    @Autowired
    private DataSourceObjectFeign dataSourceObjectService;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCbTemplateFeignService;

    @Autowired
    private PlanFeedBackFeignServiceI feedBackFeignService;

    @Autowired
    private IDSIntegratedPLMService idsIntegratedPLMService;

    /**
     * 接口
     */
    @Value(value="${spring.application.name}")
    private String appKey;

    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkService;

    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;

    /**
     * 计划前置接口
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;

    @Autowired
    private ActivityTypeManageFeign activityTypeManageService;
    
    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    @Autowired
    private ProjectRemoteFeignServiceI projectRemoteFeignServiceI;

    /**
     * 项目计划列表 页面跳转
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "plan")
    public ModelAndView plan(HttpServletRequest request) {
        // 获取所属项目ID
        String projectId = request.getParameter("id");
        Object operationCodes = OpeartionUtils.getOperationCodes(request);
        String orgId = ResourceUtil.getCurrentUserOrg().getId();

        boolean planAdd = false;
        boolean planImport = false;
        boolean planMultiAssign = false;
        boolean planMultiModifyPlan = false;
        boolean planMultiChangePlan = false;
        boolean basicLineManager = false;
        boolean plansaveAsPlanTemplateForMore = false;
        boolean planExportPlanMppForMore = false;
        boolean planAddSubPlan = false;
        boolean planImportSubPlan = false;
        boolean planInsert = false;
        boolean planSaveAsPlanTemplate = false;
        boolean viewPlanTask = false;

        boolean isPlanAddPlanAfter = false;
        boolean isPlanInsert = false;
        boolean planModifyOperationCode = false;
        boolean planAssignOperationCode = false;
        boolean planDeleteOperationCode = false;
        boolean planChangeOperationCode = false;
        boolean planDiscardOperationCode = false;
        boolean planRevocationOperationCode = false;
        boolean planConcernOperationCode = false;
        boolean planUnconcernOperationCode = false;

        for (String operationCode : operationCodes.toString().split(",")) {
            if (operationCode.contains("planAdd")) {
                planAdd = true;
            }
            if (operationCode.contains("planImport")) {
                planImport = true;
            }
            if (operationCode.contains("planMultiAssign")) {
                planMultiAssign = true;
            }
            if (operationCode.contains("planMultiModifyPlan")) {
                planMultiModifyPlan = true;
            }
            if (operationCode.contains("planMultiChangePlan")) {
                planMultiChangePlan = true;
            }
            if (operationCode.contains("basicLineManager")) {
                basicLineManager = true;
            }
            if (operationCode.contains("plansaveAsPlanTemplateForMore")) {
                plansaveAsPlanTemplateForMore = true;
            }
            if (operationCode.contains("planExportPlanMppForMore")) {
                planExportPlanMppForMore = true;
            }
            if (operationCode.contains("planAddSubPlan")) {
                planAddSubPlan = true;
            }
            if (operationCode.contains("planImportSubPlan")) {
                planImportSubPlan = true;
            }
            if (operationCode.contains("planSaveAsPlanTemplate")) {
                planSaveAsPlanTemplate = true;
            }
            if (operationCode.contains("viewPlanTask")) {
                viewPlanTask = true;
            }

            if (operationCode.contains("planAddPlanAfter")) {
                isPlanAddPlanAfter = true;
            }
            if (operationCode.contains("planInsert")) {
                isPlanInsert = true;
            }
            if (operationCode.contains("planModify")) {
                planModifyOperationCode = true;
            }
            if (operationCode.contains("planAssign")) {
                planAssignOperationCode = true;
            }
            if (operationCode.contains("planDelete")) {
                planDeleteOperationCode = true;
            }
            if (operationCode.contains("planChange")) {
                planChangeOperationCode = true;
            }
            if (operationCode.contains("planRevocation")) {
                planRevocationOperationCode = true;
            }
            if (operationCode.contains("planDiscard")) {
                planDiscardOperationCode = true;
            }
            if (operationCode.contains("planConcern")) {
                planConcernOperationCode = true;
            }
            if (operationCode.contains("planUnconcern")) {
                planUnconcernOperationCode = true;
            }
        }
        request.setAttribute("planAdd", planAdd);
        request.setAttribute("planImport", planImport);
        request.setAttribute("planMultiAssign", planMultiAssign);
        request.setAttribute("planMultiModifyPlan", planMultiModifyPlan);
        request.setAttribute("planMultiChangePlan", planMultiChangePlan);
        request.setAttribute("basicLineManager", basicLineManager);
        request.setAttribute("plansaveAsPlanTemplateForMore", plansaveAsPlanTemplateForMore);
        request.setAttribute("planExportPlanMppForMore", planExportPlanMppForMore);
        request.setAttribute("planAddSubPlan", planAddSubPlan);
        request.setAttribute("planImportSubPlan", planImportSubPlan);
        request.setAttribute("planSaveAsPlanTemplate", planSaveAsPlanTemplate);
        request.setAttribute("viewPlanTask", viewPlanTask);

        request.setAttribute("isPlanAddPlanAfter", isPlanAddPlanAfter);
        request.setAttribute("isPlanInsert", isPlanInsert);
        boolean isModify = true;
        String isViewPage = request.getParameter("isViewPage");
        String projectBizCurrent = "";
        Project project = projectService.getProjectEntity(projectId);
        /* Project project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),Project.class);*/

        if (project != null) {
            projectBizCurrent = project.getBizCurrent();
        }
        else {
            project = new Project();
            projectBizCurrent = project.getBizCurrent();
        }

        if (StringUtils.isNotEmpty(isViewPage)) {
            if ("true".equals(isViewPage)) {
                isModify = false;
            }
        }
        else {
            if (ProjectConstants.PAUSED.equals(projectBizCurrent)
                    || ProjectConstants.CLOSED.equals(projectBizCurrent)) {
                isModify = false;
            }
            else {
                if (ProjectConstants.EDITING.equals(projectBizCurrent)
                        && ProjectConstants.APPROVING.equals(projectBizCurrent)
                        && !ProjectConstants.REFUSED.equals(projectBizCurrent)) {
                    isModify = false;
                }
                else {
                    isModify = true;
                }
            }
        }

        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson fJson = projRoleService.getTeamIdByProjectId(project.getId());
        String teamId =String.valueOf(fJson.getObj());

        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);


        FeignJson jsonF = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(jsonF.getObj());
        String warningDayFlag = "before";
        if (StringUtils.isNotEmpty(warningDay)) {
            if (warningDay.trim().startsWith("-")) {
                warningDayFlag = "after";
                warningDay = warningDay.trim().replace("-", "");
            }
        }
        else {
            warningDay = "0";
        }

        request.setAttribute("isModify", isModify);
        request.setAttribute("isViewPage", isViewPage);
        request.setAttribute("planModifyOperationCode", planModifyOperationCode);
        request.setAttribute("planAssignOperationCode", planAssignOperationCode);
        request.setAttribute("planDeleteOperationCode", planDeleteOperationCode);
        request.setAttribute("planChangeOperationCode", planChangeOperationCode);
        request.setAttribute("planDiscardOperationCode", planDiscardOperationCode);
        request.setAttribute("planRevocationOperationCode", planRevocationOperationCode);
        request.setAttribute("planConcernOperationCode", planConcernOperationCode);
        request.setAttribute("planUnconcernOperationCode", planUnconcernOperationCode);
        request.setAttribute("currentUserId", userId);

        request.setAttribute("isProjectManger", isProjectManger);
        request.setAttribute("isPmo", isPmo);
        request.setAttribute("teamId", teamId);
        request.setAttribute("warningDay", warningDay);
        request.setAttribute("warningDayFlag", warningDayFlag);
        request.setAttribute("projectId", projectId);
        request.setAttribute("projectBizCurrent", projectBizCurrent);
        String currentDate = DateUtil.getStringFromDate(new Date(), DateUtil.YYYY_MM_DD);
        request.setAttribute("currentDate", currentDate);

        // 从任务详细计划分解获得的计划ID，需要使计划高亮展示
        String taskDetailGetPlanId = request.getParameter("taskDetailGetPlanId");
        if (StringUtils.isNotEmpty(taskDetailGetPlanId)) {
            request.setAttribute("taskDetailGetPlanId", taskDetailGetPlanId);
        }

        // 是否刷新右侧树区域
        String refreshTree = request.getParameter("refreshTree");
        if (StringUtil.isNotEmpty(refreshTree)) {
            request.setAttribute("refreshTree", refreshTree);
        }

        UserPlanViewProjectDto link = planService.getUserViewProjectLinkByProjectId(projectId,UserUtil.getInstance().getUser().getId());
        if(!CommonUtil.isEmpty(link.getPlanViewInfoId())){

            if(!CommonUtil.isEmpty(link.getProjectId())){
                if(link.getProjectId().equals(projectId)){
                    List<PlanViewInfoDto> viewInfoList =  planService.constructionPlanViewTree(projectId, PlanviewConstant.PUBLIC,userId, orgId);

                    List<PlanViewColumnInfoDto> columnInfoList = planService.getColumnInfoListByPlanViewInfoId(link.getPlanViewInfoId());

                    if(!CommonUtil.isEmpty(columnInfoList)){
                        PlanViewColumnInfoDto columnInfo = columnInfoList.get(0);
                        request.setAttribute("showColumn", columnInfo.getColumnId());
                        String header = "";
                        String headers = "";
                        String columnIds = "";
                        if(!CommonUtil.isEmpty(columnInfo.getColumnId())){
                            if(columnInfo.getColumnId().contains("planNo")){
                                header = header + "编号,";
                                headers = headers + "编号,";
                                columnIds = columnIds + "planNumber,";
                            }
                            header = header + "进度,";
                            columnIds = columnIds + "progressRate,";
                            header = header + "操作,";
                            columnIds = columnIds + "optBtn,";
                            header = header + "计划名称,";
                            columnIds = columnIds + "planName,";
                            if(columnInfo.getColumnId().contains("planLevel")){
                                header = header + "计划等级,";
                                headers = headers + "计划等级,";
                                columnIds = columnIds + "planLevelInfo,";
                            }
                            if(columnInfo.getColumnId().contains("planType")){
                                header = header + "计划类型,";
                                headers = headers + "计划类型,";
                                columnIds = columnIds + "taskNameTypeDisplay,";
                            }
                            if(columnInfo.getColumnId().contains("planTaskType")){
                                header = header + "计划类别,";
                                headers = headers + "计划类别,";
                                columnIds = columnIds + "taskType,";
                            }
                            if(columnInfo.getColumnId().contains("status")){
                                header = header + "状态,";
                                headers = headers + "状态,";
                                columnIds = columnIds + "bizCurrentInfo,";
                            }
                            if(columnInfo.getColumnId().contains("owner")){
                                header = header + "负责人,";
                                headers = headers + "负责人,";
                                columnIds = columnIds + "ownerInfo,";
                            }
                            if(columnInfo.getColumnId().contains("planStartTime")){
                                header = header + "开始时间,";
                                headers = headers + "开始时间,";
                                columnIds = columnIds + "planStartTime,";
                            }
                            if(columnInfo.getColumnId().contains("planEndTime")){
                                header = header + "结束时间,";
                                headers = headers + "结束时间,";
                                columnIds = columnIds + "planEndTime,";
                            }
                            if(columnInfo.getColumnId().contains("assigner")){
                                header = header + "发布人,";
                                headers = headers + "发布人,";
                                columnIds = columnIds + "assignerInfo,";
                            }
                            if(columnInfo.getColumnId().contains("assignTime")){
                                header = header + "发布时间,";
                                headers = headers + "发布时间,";
                                columnIds = columnIds + "assignTime,";
                            }
                            if(columnInfo.getColumnId().contains("workTime")){
                                header = header + "工期(天),";
                                headers = headers + "工期(天),";
                                columnIds = columnIds + "workTime,";
                            }
                            if(columnInfo.getColumnId().contains("preposePlan")){
                                header = header + "前置计划,";
                                headers = headers + "前置计划,";
                                columnIds = columnIds + "preposePlans,";
                            }
                            if(columnInfo.getColumnId().contains("mileStone")){
                                header = header + "里程碑,";
                                headers = headers + "里程碑,";
                                columnIds = columnIds + "milestone,";
                            }
                            if(columnInfo.getColumnId().contains("creator")){
                                header = header + "创建者,";
                                headers = headers + "创建者,";
                                columnIds = columnIds + "creator,";
                            }
                            if(columnInfo.getColumnId().contains("createTime")){
                                header = header + "创建时间,";
                                headers = headers + "创建时间,";
                                columnIds = columnIds + "createTime,";
                            }
                        }else{
//                            request.setAttribute("showColumn", "planName");
                            header = header + "进度,";
                            columnIds = columnIds + "progressRate,";
                            header = header + "操作,";
                            columnIds = columnIds + "optBtn,";
                            header = header + "计划名称,";
                            columnIds = columnIds + "planName,";
                        }

                        header = header.substring(0, header.length()-1);
                        request.setAttribute("header", header);
                        request.setAttribute("headers", headers);
                        columnIds = columnIds.substring(0, columnIds.length()-1);
                        request.setAttribute("columnIds", columnIds);
                    }
                    Map<String,String> viewInfoMap = new HashMap<String, String>();
                    if(!CommonUtil.isEmpty(viewInfoList)){
                        for(PlanViewInfoDto view : viewInfoList){
                            viewInfoMap.put(view.getId(), view.getName());
                        }
                    }

                    if(!CommonUtil.isEmpty(viewInfoMap.get(link.getPlanViewInfoId()))){
                        PlanViewInfoDto info = planService.getPlanViewEntity(link.getPlanViewInfoId());
                        if(!CommonUtil.isEmpty(info) && !CommonUtil.isEmpty(info.getName())){
                            request.setAttribute("planViewInfoName", info.getName());
                            request.setAttribute("planViewInfoId", info.getId());
                        }

                        if(!CommonUtil.isEmpty(link.getPlanViewInfoId())){
                            List<PlanViewSearchConditionDto> searchConditionList = planService.getSearchConditionListByViewId(link.getPlanViewInfoId());
                            if(!CommonUtil.isEmpty(searchConditionList)){
                                for(PlanViewSearchConditionDto search : searchConditionList){
                                    if(search.getAttributeName().equals("Plan.planName")){
                                        request.setAttribute("planName", search.getAttributeValue());
                                    }else if(search.getAttributeName().equals("Plan.isDelay")){
                                        request.setAttribute("isDelay", "DELAY");
                                    }else if(search.getAttributeName().equals("Plan.planLevelInfo.id")){
                                        request.setAttribute("planLevel", search.getAttributeValue());
                                    }else if(search.getAttributeName().equals("Plan.bizCurrent")){
                                        request.setAttribute("bizCurrent", search.getAttributeValue());
                                    }else if(("Plan.planNumber").equals(search.getAttributeName())){
                                        request.setAttribute("planNumber", search.getAttributeValue());
                                    }else if(("Plan.owner").equals(search.getAttributeName())){
                                        request.setAttribute("owner", search.getAttributeValue());
                                    }else if(("Plan.planStartTime").equals(search.getAttributeName())){
                                        request.setAttribute("planStartTime1", search.getAttributeValue().split(",")[0]);
                                        request.setAttribute("planStartTime2", search.getAttributeValue().split(",")[1]);
                                    }else if(("Plan.planEndTime").equals(search.getAttributeName())){
                                        request.setAttribute("planEndTime1", search.getAttributeValue().split(",")[0]);
                                        request.setAttribute("planEndTime2", search.getAttributeValue().split(",")[1]);
                                    }else if(("Plan.workTime").equals(search.getAttributeName())){
                                        request.setAttribute("workTime", search.getAttributeValue());
                                        request.setAttribute("workTimeCondition", search.getAttributeCondition());
                                    }else if(("Plan.progressRate").equals(search.getAttributeName())){
                                        request.setAttribute("progressRate", search.getAttributeValue());
                                        request.setAttribute("progressRateCondition", search.getAttributeCondition());
                                    }else if(("Plan.taskNameType").equals(search.getAttributeName())){
                                        request.setAttribute("taskNameType", search.getAttributeValue());
                                    }else if(("Plan.taskType").equals(search.getAttributeName())){
                                        request.setAttribute("taskType", search.getAttributeValue());
                                    }
                                }
                            }
                        }
                    }else{
                        request.setAttribute("planViewInfoName", "全部计划");
                        request.setAttribute("planViewInfoId", "4028f00763ba3b200163ba6446bf000f");
                        request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
                        request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                        request.setAttribute("header", PlanviewConstant.SHOW_HEADER);
                        request.setAttribute("headers", PlanviewConstant.SHOW_HEADERS);
                        planService.saveUserViewProjectLink(projectId,"4028f00763ba3b200163ba6446bf000f",ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());    //保存当前用户与视图项目之间的关系
                    }
                }else{
                    request.setAttribute("planViewInfoName", "全部计划");
                    request.setAttribute("planViewInfoId", "4028f00763ba3b200163ba6446bf000f");
                    request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
                    request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                    request.setAttribute("header", PlanviewConstant.SHOW_HEADER);
                    request.setAttribute("headers", PlanviewConstant.SHOW_HEADERS);
                }
            }else{

                List<PlanViewInfoDto> viewInfoList =  planService.constructionPlanViewTree(projectId, PlanviewConstant.PUBLIC,userId, orgId);

                List<PlanViewColumnInfoDto> columnInfoList = planService.getColumnInfoListByPlanViewInfoId(link.getPlanViewInfoId());

                if(!CommonUtil.isEmpty(columnInfoList)){
                    PlanViewColumnInfoDto columnInfo = columnInfoList.get(0);
                    request.setAttribute("showColumn", columnInfo.getColumnId());
                    String header = "";
                    String headers = "";
                    String columnIds = "";
                    if(!CommonUtil.isEmpty(columnInfo.getColumnId())){
                        if(columnInfo.getColumnId().contains("planNo")){
                            header = header + "编号,";
                            headers = headers + "编号,";
                            columnIds = columnIds + "planNumber,";
                        }
                        header = header + "进度,";
                        columnIds = columnIds + "progressRate,";
                        header = header + "操作,";
                        columnIds = columnIds + "optBtn,";
                        header = header + "计划名称,";
                        columnIds = columnIds + "planName,";
                        if(columnInfo.getColumnId().contains("planLevel")){
                            header = header + "计划等级,";
                            headers = headers + "计划等级,";
                            columnIds = columnIds + "planLevelInfo,";
                        }
                        if(columnInfo.getColumnId().contains("planType")){
                            header = header + "计划类型,";
                            headers = headers + "计划类型,";
                            columnIds = columnIds + "taskNameTypeDisplay,";
                        }
                        if(columnInfo.getColumnId().contains("planTaskType")){
                            header = header + "计划类别,";
                            headers = headers + "计划类别,";
                            columnIds = columnIds + "taskType,";
                        }
                        if(columnInfo.getColumnId().contains("status")) {
                            header = header + "状态,";
                            headers = headers + "状态,";
                            columnIds = columnIds + "bizCurrentInfo,";
                        }
                        if(columnInfo.getColumnId().contains("owner")){
                            header = header + "负责人,";
                            headers = headers + "负责人,";
                            columnIds = columnIds + "ownerInfo,";
                        }
                        if(columnInfo.getColumnId().contains("planStartTime")){
                            header = header + "开始时间,";
                            headers = headers + "开始时间,";
                            columnIds = columnIds + "planStartTime,";
                        }
                        if(columnInfo.getColumnId().contains("planEndTime")){
                            header = header + "结束时间,";
                            headers = headers + "结束时间,";
                            columnIds = columnIds + "planEndTime,";
                        }
                        if(columnInfo.getColumnId().contains("assigner")){
                            header = header + "发布人,";
                            headers = headers + "发布人,";
                            columnIds = columnIds + "assignerInfo,";
                        }
                        if(columnInfo.getColumnId().contains("assignTime")){
                            header = header + "发布时间,";
                            headers = headers + "发布时间,";
                            columnIds = columnIds + "assignTime,";
                        }
                        if(columnInfo.getColumnId().contains("workTime")){
                            header = header + "工期(天),";
                            headers = headers + "工期(天),";
                            columnIds = columnIds + "workTime,";
                        }
                        if(columnInfo.getColumnId().contains("preposePlan")){
                            header = header + "前置计划,";
                            headers = headers + "前置计划,";
                            columnIds = columnIds + "preposePlans,";
                        }
                        if(columnInfo.getColumnId().contains("mileStone")){
                            header = header + "里程碑,";
                            headers = headers + "里程碑,";
                            columnIds = columnIds + "milestone,";
                        }
                        if(columnInfo.getColumnId().contains("creator")){
                            header = header + "创建者,";
                            headers = headers + "创建者,";
                            columnIds = columnIds + "creator,";
                        }
                        if(columnInfo.getColumnId().contains("createTime")){
                            header = header + "创建时间,";
                            headers = headers + "创建时间,";
                            columnIds = columnIds + "createTime,";
                        }
                    }else{
//                        request.setAttribute("showColumn", "planName");
                        header = header + "进度,";
                        columnIds = columnIds + "progressRate,";
                        header = header + "操作,";
                        columnIds = columnIds + "optBtn,";
                        header = header + "计划名称,";
                        columnIds = columnIds + "planName,";
                    }

                    header = header.substring(0, header.length()-1);
                    request.setAttribute("header", header);
                    request.setAttribute("headers", headers);
                    columnIds = columnIds.substring(0, columnIds.length()-1);
                    request.setAttribute("columnIds", columnIds);
                }
                Map<String,String> viewInfoMap = new HashMap<String, String>();
                if(!CommonUtil.isEmpty(viewInfoList)){
                    for(PlanViewInfoDto view : viewInfoList){
                        viewInfoMap.put(view.getId(), view.getName());
                    }
                }

                if(!CommonUtil.isEmpty(viewInfoMap.get(link.getPlanViewInfoId()))){
                    PlanViewInfoDto info = planService.getPlanViewEntity(link.getPlanViewInfoId());
                    if(!CommonUtil.isEmpty(info) && !CommonUtil.isEmpty(info.getName())){
                        request.setAttribute("planViewInfoName", info.getName());
                        request.setAttribute("planViewInfoId", info.getId());
                    }

                    if(!CommonUtil.isEmpty(link.getPlanViewInfoId())){
                        List<PlanViewSearchConditionDto> searchConditionList = planService.getSearchConditionListByViewId(link.getPlanViewInfoId());
                        if(!CommonUtil.isEmpty(searchConditionList)){
                            for(PlanViewSearchConditionDto search : searchConditionList){
                                if(search.getAttributeName().equals("Plan.planName")){
                                    request.setAttribute("planName", search.getAttributeValue());
                                }else if(search.getAttributeName().equals("Plan.isDelay")){
                                    request.setAttribute("isDelay", "DELAY");
                                }else if(search.getAttributeName().equals("Plan.planLevelInfo.id")){
                                    request.setAttribute("planLevel", search.getAttributeValue());
                                }else if(search.getAttributeName().equals("Plan.bizCurrent")){
                                    request.setAttribute("bizCurrent", search.getAttributeValue());
                                }else if(("Plan.planNumber").equals(search.getAttributeName())){
                                    request.setAttribute("planNumber", search.getAttributeValue());
                                }else if(("Plan.owner").equals(search.getAttributeName())){
                                    request.setAttribute("owner", search.getAttributeValue());
                                }else if(("Plan.planStartTime").equals(search.getAttributeName())){
                                    request.setAttribute("planStartTime1", search.getAttributeValue().split(",")[0]);
                                    request.setAttribute("planStartTime2", search.getAttributeValue().split(",")[1]);
                                }else if(("Plan.planEndTime").equals(search.getAttributeName())){
                                    request.setAttribute("planEndTime1", search.getAttributeValue().split(",")[0]);
                                    request.setAttribute("planEndTime2", search.getAttributeValue().split(",")[1]);
                                }else if(("Plan.workTime").equals(search.getAttributeName())){
                                    request.setAttribute("workTime", search.getAttributeValue());
                                    request.setAttribute("workTimeCondition", search.getAttributeCondition());
                                }else if(("Plan.progressRate").equals(search.getAttributeName())){
                                    request.setAttribute("progressRate", search.getAttributeValue());
                                    request.setAttribute("progressRateCondition", search.getAttributeCondition());
                                }else if(("Plan.taskNameType").equals(search.getAttributeName())){
                                    request.setAttribute("taskNameType", search.getAttributeValue());
                                }else if(("Plan.taskType").equals(search.getAttributeName())){
                                    request.setAttribute("taskType", search.getAttributeValue());
                                }
                            }
                        }
                    }
                }else{
                    request.setAttribute("planViewInfoName", "全部计划");
                    request.setAttribute("planViewInfoId", "4028f00763ba3b200163ba6446bf000f");
                    request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
                    request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                    request.setAttribute("header", PlanviewConstant.SHOW_HEADER);
                    request.setAttribute("headers", PlanviewConstant.SHOW_HEADERS);
                    planService.saveUserViewProjectLink(projectId,"4028f00763ba3b200163ba6446bf000f",ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());    //保存当前用户与视图项目之间的关系
                }

            }


        }else{
            request.setAttribute("planViewInfoName", "全部计划");
            request.setAttribute("planViewInfoId", "4028f00763ba3b200163ba6446bf000f");
            request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
            request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
            request.setAttribute("headerShow", PlanviewConstant.SHOW_HEADER);
            request.setAttribute("headers", PlanviewConstant.SHOW_HEADERS);
        }

        request.setAttribute("viewPlan", request.getParameter("viewPlan"));
        request.setAttribute("isView", request.getParameter("isView"));

        request.getSession().setAttribute("planNumber","");
        request.getSession().setAttribute("isDelay","");
        request.getSession().setAttribute("planName","");
        request.getSession().setAttribute("planLevel","");
        request.getSession().setAttribute("bizCurrent","");
        request.getSession().setAttribute("userName","");
        request.getSession().setAttribute("planStartTime","");
        request.getSession().setAttribute("planEndTime","");
        request.getSession().setAttribute("workTime","");
        request.getSession().setAttribute("workTimeCondition","");
        request.getSession().setAttribute("progressRate","");
        request.getSession().setAttribute("progressRateCondition","");
        request.getSession().setAttribute("taskNameType","");
        request.getSession().setAttribute("taskType","");

        return new ModelAndView("com/glaway/ids/pm/project/plan/planList");
    }


    @RequestMapping(params = "setConditionToSession")
    @ResponseBody
    public void setConditionToSession(HttpServletRequest request){
        String planNumber = request.getParameter("planNumber");
        String isDelay = request.getParameter("isDelay");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");

        request.getSession().setAttribute("planNumber",planNumber);
        request.getSession().setAttribute("isDelay",isDelay);
        request.getSession().setAttribute("planName",planName);
        request.getSession().setAttribute("planLevel",planLevel);
        request.getSession().setAttribute("bizCurrent",bizCurrent);
        request.getSession().setAttribute("userName",userName);
        request.getSession().setAttribute("planStartTime",planStartTime);
        request.getSession().setAttribute("planEndTime",planEndTime);
        request.getSession().setAttribute("workTime",workTime);
        request.getSession().setAttribute("workTimeCondition",workTimeCondition);
        request.getSession().setAttribute("progressRate",progressRate);
        request.getSession().setAttribute("progressRateCondition",progressRateCondition);
        request.getSession().setAttribute("taskNameType",taskNameType);
        request.getSession().setAttribute("taskType",taskType);
    }

    @RequestMapping(params = "removeSession")
    @ResponseBody
    public void removeSession(HttpServletRequest request){
        request.getSession().removeAttribute("planNumber");
        request.getSession().removeAttribute("isDelay");
        request.getSession().removeAttribute("planName");
        request.getSession().removeAttribute("planLevel");
        request.getSession().removeAttribute("bizCurrent");
        request.getSession().removeAttribute("userName");
        request.getSession().removeAttribute("planStartTime");
        request.getSession().removeAttribute("planEndTime");
        request.getSession().removeAttribute("workTime");
        request.getSession().removeAttribute("workTimeCondition");
        request.getSession().removeAttribute("progressRate");
        request.getSession().removeAttribute("progressRateCondition");
        request.getSession().removeAttribute("taskNameType");
        request.getSession().removeAttribute("taskType");
    }


    @RequestMapping(params = "goDatagridPage")
    public ModelAndView goDatagridPage(HttpServletRequest request){

        String projectId = request.getParameter("projectId");
        Object operationCodes = OpeartionUtils.getOperationCodes(request);
        String orgId = ResourceUtil.getCurrentUserOrg().getId();

        boolean planInsert_ = false;
        boolean planAddSubPlan_ = false;
        boolean planSaveAsPlanTemplate_ = false;
        boolean planExportPlanMppForMore_ = false;
        boolean viewPlanTask_ = false;
        boolean planImportSubPlan_ = false;
        boolean planAddPlanAfter_ = false;

        if(!CommonUtil.isEmpty(operationCodes)){
            for(String operationCode : operationCodes.toString().split(",")){
                if (operationCode.contains("planInsert_")) {
                    planInsert_ = true;
                    continue;
                }
                if (operationCode.contains("planAddSubPlan_")) {
                    planAddSubPlan_ = true;
                    continue;
                }
                if (operationCode.contains("planSaveAsPlanTemplate_")) {
                    planSaveAsPlanTemplate_ = true;
                    continue;
                }
                if (operationCode.contains("planExportPlanMppForMore_")) {
                    planExportPlanMppForMore_ = true;
                    continue;
                }
                if (operationCode.contains("viewPlanTask_")) {
                    viewPlanTask_ = true;
                    continue;
                }
                if (operationCode.contains("planImportSubPlan_")) {
                    planImportSubPlan_ = true;
                    continue;
                }
                if (operationCode.contains("planAddPlanAfter_")) {
                    planAddPlanAfter_ = true;
                    continue;
                }
            }
        }

        request.setAttribute("planInsert_",planInsert_);
        request.setAttribute("planAddSubPlan_",planAddSubPlan_);
        request.setAttribute("planSaveAsPlanTemplate_",planSaveAsPlanTemplate_);
        request.setAttribute("planExportPlanMppForMore_",planExportPlanMppForMore_);
        request.setAttribute("viewPlanTask_",viewPlanTask_);
        request.setAttribute("planImportSubPlan_",planImportSubPlan_);
        request.setAttribute("planAddPlanAfter_",planAddPlanAfter_);

        /*String planNumber = request.getParameter("planNumber");
        String isDelay = request.getParameter("isDelay");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");

        request.setAttribute("planNumber",planNumber);
        request.setAttribute("isDelay",isDelay);
        request.setAttribute("planName",planName);
        request.setAttribute("planLevel",planLevel);
        request.setAttribute("bizCurrent",bizCurrent);
        request.setAttribute("userName",userName);
        request.setAttribute("planStartTime",planStartTime);
        request.setAttribute("planEndTime",planEndTime);
        request.setAttribute("workTime",workTime);
        request.setAttribute("workTimeCondition",workTimeCondition);
        request.setAttribute("progressRate",progressRate);
        request.setAttribute("progressRateCondition",progressRateCondition);
        request.setAttribute("taskNameType",taskNameType);
        request.setAttribute("taskType",taskType);
*/
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planRevocationOperationCode = request.getParameter("planRevocationOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");

        String isPmo = request.getParameter("isPmo");
        String isProjectManger = request.getParameter("isProjectManger");
        String isViewPage = request.getParameter("isViewPage");
        String mygridHeight = request.getParameter("mygridHeight");

        request.setAttribute("projectId", projectId);
        request.setAttribute("isModify", isModify);
        request.setAttribute("planModifyOperationCode", planModifyOperationCode);
        request.setAttribute("planAssignOperationCode", planAssignOperationCode);
        request.setAttribute("planDeleteOperationCode", planDeleteOperationCode);
        request.setAttribute("planChangeOperationCode", planChangeOperationCode);
        request.setAttribute("planDiscardOperationCode", planDiscardOperationCode);
        request.setAttribute("planRevocationOperationCode", planRevocationOperationCode);
        request.setAttribute("planConcernOperationCode", planConcernOperationCode);
        request.setAttribute("planUnconcernOperationCode", planUnconcernOperationCode);
        request.setAttribute("isPmo", isPmo);
        request.setAttribute("isProjectManger", isProjectManger);
        request.setAttribute("isViewPage", isViewPage);
        request.setAttribute("mygridHeight", mygridHeight);

        boolean isPlanInsert = false;
        boolean isPlanAddPlanAfter = false;
        if(!CommonUtil.isEmpty(operationCodes)){
            for (String operationCode : operationCodes.toString().split(",")) {
                if (operationCode.contains("planAddPlanAfter")) {
                    isPlanAddPlanAfter = true;
                }
                if (operationCode.contains("planInsert")) {
                    isPlanInsert = true;
                }
            }
        }

        request.setAttribute("isPlanAddPlanAfter", isPlanAddPlanAfter);
        request.setAttribute("isPlanInsert", isPlanInsert);

        UserPlanViewProjectDto link = planService.getUserViewProjectLinkByProjectId(projectId,UserUtil.getInstance().getUser().getId());
        if(!CommonUtil.isEmpty(link.getPlanViewInfoId())){

            List<PlanViewInfoDto> viewInfoList =  planService.constructionPlanViewTree(projectId, PlanviewConstant.PUBLIC,UserUtil.getCurrentUser().getId(), orgId);

            List<PlanViewColumnInfoDto> columnInfoList = planService.getColumnInfoListByPlanViewInfoId(link.getPlanViewInfoId());

            if(!CommonUtil.isEmpty(link.getProjectId())){
                if(link.getProjectId().equals(projectId)){


                    if(!CommonUtil.isEmpty(columnInfoList)){
                        PlanViewColumnInfoDto columnInfo = columnInfoList.get(0);
                        String header = "";
                        String columnIds = "";
                        if(!CommonUtil.isEmpty(columnInfo.getColumnId())){
                            if(columnInfo.getColumnId().contains("planNo")){
                                header = header + "编号,";
                                columnIds = columnIds + "planNumber,";
                            }
                            header = header + "进度,";
                            columnIds = columnIds + "progressRate,";
                            header = header + "操作,";
                            columnIds = columnIds + "optBtn,";
                            header = header + "计划名称,";
                            columnIds = columnIds + "planName,";
                            if(columnInfo.getColumnId().contains("planLevel")){
                                header = header + "计划等级,";
                                columnIds = columnIds + "planLevelInfo,";
                            }
                            if(columnInfo.getColumnId().contains("planType")){
                                header = header + "计划类型,";
                                columnIds = columnIds + "taskNameTypeDisplay,";
                            }
                            if(columnInfo.getColumnId().contains("planTaskType")){
                                header = header + "计划类别,";
                                columnIds = columnIds + "taskType,";
                            }
                            if(columnInfo.getColumnId().contains("status")){
                                header = header + "状态,";
                                columnIds = columnIds + "bizCurrentInfo,";
                            }
                            if(columnInfo.getColumnId().contains("owner")){
                                header = header + "负责人,";
                                columnIds = columnIds + "ownerInfo,";
                            }
                            if(columnInfo.getColumnId().contains("planStartTime")){
                                header = header + "开始时间,";
                                columnIds = columnIds + "planStartTime,";
                            }
                            if(columnInfo.getColumnId().contains("planEndTime")){
                                header = header + "结束时间,";
                                columnIds = columnIds + "planEndTime,";
                            }
                            if(columnInfo.getColumnId().contains("assigner")){
                                header = header + "发布人,";
                                columnIds = columnIds + "assignerInfo,";
                            }
                            if(columnInfo.getColumnId().contains("assignTime")){
                                header = header + "发布时间,";
                                columnIds = columnIds + "assignTime,";
                            }
                            if(columnInfo.getColumnId().contains("workTime")){
                                header = header + "工期(天),";
                                columnIds = columnIds + "workTime,";
                            }
                            if(columnInfo.getColumnId().contains("preposePlan")){
                                header = header + "前置计划,";
                                columnIds = columnIds + "preposePlans,";
                            }
                            if(columnInfo.getColumnId().contains("mileStone")){
                                header = header + "里程碑,";
                                columnIds = columnIds + "milestone,";
                            }
                            if(columnInfo.getColumnId().contains("creator")){
                                header = header + "创建者,";
                                columnIds = columnIds + "creator,";
                            }
                            if(columnInfo.getColumnId().contains("createTime")){
                                header = header + "创建时间,";
                                columnIds = columnIds + "createTime,";
                            }
                        }else{
//                            request.setAttribute("showColumn", "planName");
                            header = header + "进度,";
                            columnIds = columnIds + "progressRate,";
                            header = header + "操作,";
                            columnIds = columnIds + "optBtn,";
                            header = header + "计划名称,";
                            columnIds = columnIds + "planName,";
                        }

                        header = header.substring(0, header.length()-1);
                        request.setAttribute("headerShow", header);
                        columnIds = columnIds.substring(0, columnIds.length()-1);
                        request.setAttribute("columnIds", columnIds);
                    }
                    Map<String,String> viewInfoMap = new HashMap<String, String>();
                    if(!CommonUtil.isEmpty(viewInfoList)){
                        for(PlanViewInfoDto view : viewInfoList){
                            viewInfoMap.put(view.getId(), view.getName());
                        }
                    }

                    if(CommonUtil.isEmpty(viewInfoMap.get(link.getPlanViewInfoId()))){
                        request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
                        request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                        request.setAttribute("headerShow", PlanviewConstant.SHOW_HEADER);

                    }
                }else{
                    request.setAttribute("showColumn", PlanviewConstant.LINK_COLUMN);
                    request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                    request.setAttribute("headerShow", PlanviewConstant.SHOW_HEADER);
                }
            }else{


                if(!CommonUtil.isEmpty(columnInfoList)){
                    PlanViewColumnInfoDto columnInfo = columnInfoList.get(0);
                    String header = "";
                    String columnIds = "";
                    if(!CommonUtil.isEmpty(columnInfo.getColumnId())){
                        if(columnInfo.getColumnId().contains("planNo")){
                            header = header + "编号,";
                            columnIds = columnIds + "planNumber,";
                        }
                        header = header + "进度,";
                        columnIds = columnIds + "progressRate,";
                        header = header + "操作,";
                        columnIds = columnIds + "optBtn,";
                        header = header + "计划名称,";
                        columnIds = columnIds + "planName,";
                        if(columnInfo.getColumnId().contains("planLevel")){
                            header = header + "计划等级,";
                            columnIds = columnIds + "planLevelInfo,";
                        }
                        if(columnInfo.getColumnId().contains("planType")){
                            header = header + "计划类型,";
                            columnIds = columnIds + "taskNameTypeDisplay,";
                        }
                        if(columnInfo.getColumnId().contains("planTaskType")){
                            header = header + "计划类别,";
                            columnIds = columnIds + "taskType,";
                        }
                        if(columnInfo.getColumnId().contains("status")){
                            header = header + "状态,";
                            columnIds = columnIds + "bizCurrentInfo,";
                        }
                        if(columnInfo.getColumnId().contains("owner")){
                            header = header + "负责人,";
                            columnIds = columnIds + "ownerInfo,";
                        }
                        if(columnInfo.getColumnId().contains("planStartTime")){
                            header = header + "开始时间,";
                            columnIds = columnIds + "planStartTime,";
                        }
                        if(columnInfo.getColumnId().contains("planEndTime")){
                            header = header + "结束时间,";
                            columnIds = columnIds + "planEndTime,";
                        }
                        if(columnInfo.getColumnId().contains("assigner")){
                            header = header + "发布人,";
                            columnIds = columnIds + "assignerInfo,";
                        }
                        if(columnInfo.getColumnId().contains("assignTime")){
                            header = header + "发布时间,";
                            columnIds = columnIds + "assignTime,";
                        }
                        if(columnInfo.getColumnId().contains("workTime")){
                            header = header + "工期(天),";
                            columnIds = columnIds + "workTime,";
                        }
                        if(columnInfo.getColumnId().contains("preposePlan")){
                            header = header + "前置计划,";
                            columnIds = columnIds + "preposePlans,";
                        }
                        if(columnInfo.getColumnId().contains("mileStone")){
                            header = header + "里程碑,";
                            columnIds = columnIds + "milestone,";
                        }
                        if(columnInfo.getColumnId().contains("creator")){
                            header = header + "创建者,";
                            columnIds = columnIds + "creator,";
                        }
                        if(columnInfo.getColumnId().contains("createTime")){
                            header = header + "创建时间,";
                            columnIds = columnIds + "createTime,";
                        }
                    }else{
//                        request.setAttribute("showColumn", "planName");
                        header = header + "进度,";
                        columnIds = columnIds + "progressRate,";
                        header = header + "操作,";
                        columnIds = columnIds + "optBtn,";
                        header = header + "计划名称,";
                        columnIds = columnIds + "planName,";
                    }

                    header = header.substring(0, header.length()-1);
                    request.setAttribute("headerShow", header);
                    columnIds = columnIds.substring(0, columnIds.length()-1);
                    request.setAttribute("columnIds", columnIds);
                }
                Map<String,String> viewInfoMap = new HashMap<String, String>();
                if(!CommonUtil.isEmpty(viewInfoList)){
                    for(PlanViewInfoDto view : viewInfoList){
                        viewInfoMap.put(view.getId(), view.getName());
                    }
                }

                if(CommonUtil.isEmpty(viewInfoMap.get(link.getPlanViewInfoId()))){
                    request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
                    request.setAttribute("headerShow", PlanviewConstant.SHOW_HEADER);
                }
            }


        }else{
            request.setAttribute("columnIds", PlanviewConstant.LINK_COLUMN_SHOW);
            request.setAttribute("headerShow", PlanviewConstant.SHOW_HEADER);
        }

        return new ModelAndView(
                "com/glaway/ids/pm/project/plan/planGridList");
    }



    /**
     * 项目计划页面初始化时获取计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    public void list(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
    //    long time = System.currentTimeMillis();
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planRevocationOperationCode = request.getParameter("planRevocationOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        boolean isPmo = "true".equals(request.getParameter("isPmo"));
        boolean isProjectManger = "true".equals(request.getParameter("isProjectManger"));
        plan.setProjectId(projectId);

        String planNumber = (String)request.getSession().getAttribute("planNumber");
        String isDelay = (String)request.getSession().getAttribute("isDelay");
        String planName = (String)request.getSession().getAttribute("planName");
        String planLevel = (String)request.getSession().getAttribute("planLevel");
        String bizCurrent = (String)request.getSession().getAttribute("bizCurrent");
        String userName = (String)request.getSession().getAttribute("userName");
        String planStartTime = (String)request.getSession().getAttribute("planStartTime");
        String planEndTime = (String)request.getSession().getAttribute("planEndTime");
        String workTime = (String)request.getSession().getAttribute("workTime");
        String workTimeCondition = (String)request.getSession().getAttribute("workTimeCondition");
        String progressRate = (String)request.getSession().getAttribute("progressRate");
        String progressRateCondition = (String)request.getSession().getAttribute("progressRateCondition");
        String taskNameType = (String)request.getSession().getAttribute("taskNameType");
        String taskType = (String)request.getSession().getAttribute("taskType");

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();

        if (StringUtils.isNotEmpty(planNumber)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(isDelay)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(isDelay.split(","));
            vo.setCondition("in");
            vo.setValue(isDelay);
            vo.setKey("Plan.isDelay");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }

        List<PlanDto> planList = new ArrayList<PlanDto>();
        //先注释，勿删
       /* UserPlanViewProjectDto link = planService.getUserViewProjectLinkByProjectId(projectId,UserUtil.getInstance().getUser().getId());
        long time1 = System.currentTimeMillis();
        if(!CommonUtil.isEmpty(link.getPlanViewInfoId())){
            if(!CommonUtil.isEmpty(link.getProjectId())){
                if(link.getProjectId().equals(projectId)){
                    Map<String,Object> mapList = new HashMap<String,Object>();
                    mapList.put("planDto",plan);
                    mapList.put("planViewInfoId",link.getPlanViewInfoId());
                    mapList.put("conditionList",conditionList);
                    mapList.put("userName",userName);
                    mapList.put("progressRate",progressRate);
                    mapList.put("workTime",workTime);
                    mapList.put("userId",ResourceUtil.getCurrentUser().getId());
                    List<PlanDto> resultPlanList = planService.queryPlanListForTreegridView(mapList);
                    planList = planService.queryPlanListForTreegridWithView(resultPlanList,link.getPlanViewInfoId(),ResourceUtil.getCurrentUser().getId());

                //    planList = planService.queryPlanList();
                }else{
                    Map<String,Object> mapList = new HashMap<String,Object>();
                    mapList.put("planDto",plan);
                    mapList.put("planViewInfoId","4028f00763ba3b200163ba6446bf000f");
                    mapList.put("conditionList",conditionList);
                    mapList.put("userName",userName);
                    mapList.put("progressRate",progressRate);
                    mapList.put("workTime",workTime);
                    mapList.put("userId",ResourceUtil.getCurrentUser().getId());
                    List<PlanDto> resultPlanList = planService.queryPlanListForTreegridView(mapList);
                    planList = planService.queryPlanListForTreegridWithView(resultPlanList,"4028f00763ba3b200163ba6446bf000f",ResourceUtil.getCurrentUser().getId());
                }
            }else{
                Map<String,Object> mapList = new HashMap<String,Object>();
                mapList.put("planDto",plan);
                mapList.put("planViewInfoId",link.getPlanViewInfoId());
                mapList.put("conditionList",conditionList);
                mapList.put("userName",userName);
                mapList.put("progressRate",progressRate);
                mapList.put("workTime",workTime);
                mapList.put("userId",ResourceUtil.getCurrentUser().getId());
                List<PlanDto> resultPlanList = planService.queryPlanListForTreegridView(mapList);
                long time3 = System.currentTimeMillis();
                System.out.println("计划查询时间1------------------------------------------:"+(time3-time1));
                planList = planService.queryPlanListForTreegridWithView(resultPlanList,link.getPlanViewInfoId(),ResourceUtil.getCurrentUser().getId());
                System.out.println("计划查询时间2------------------------------------------:"+(System.currentTimeMillis()-time3));
            }

        }else{
            planList = planService.queryPlanListForTreegrid(plan);
        }*/
     //   long time1 = System.currentTimeMillis();
        Map<String,Object> mapList = new HashMap<String,Object>();
        mapList.put("planDto",plan);
        mapList.put("conditionList",conditionList);
        mapList.put("projectId",projectId);
        mapList.put("userName",userName);
        mapList.put("progressRate",progressRate);
        mapList.put("workTime",workTime);
        mapList.put("userId",ResourceUtil.getCurrentUser().getId());
        planList = planService.queryPlanListByMap(mapList);
       /*long time2 = System.currentTimeMillis();
        System.out.println("计划查询时间------------------------------------------:"+(time2-time1));*/

        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(plan.getId())) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto pla = new PlanDto();
        pla.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(pla);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanRevocationOperationCode(planRevocationOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);
        Project project= projectService.getProjectEntity(projectId);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        List<JSONObject> rootList = new ArrayList<JSONObject>();

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
         if(!CommonUtil.isEmpty(request.getParameter("flag")) && request.getParameter("flag").equals("customView")){
            rootList = changePlansToJSONObjectsForCustomView(tempList, parameVo, project,
                    statusList,activityTypeManageList);
        }else{
            rootList = changePlansToJSONObjects(tempList, parameVo, project,
                    statusList,activityTypeManageList);
        }
        String resultJSON = JSON.toJSONString(rootList);
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
     * 获取父计划（只向上追一层）
     *
     * @author zhousuxia
     * @version 2018年6月8日
     * @see PlanController
     * @since
     */
    private void getParentPlan(List<PlanDto> tempList, String parentPlanId,
                               Map<String, String> planIdMaps, Map<String, PlanDto> listMap) {
        if (StringUtils.isNotEmpty(parentPlanId)
                && StringUtils.isEmpty(planIdMaps.get(parentPlanId))) {
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
           /* if (StringUtils.isNotEmpty(parentPlanId)) {
                getParent(tempList, parentPlanId, planIdMaps, listMap);
            }*/
        }
    }


    /**
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjects(List<PlanDto> planList,
                                                      PlanAuthorityVo parameVo, Project project,
                                                      List<LifeCycleStatus> statusList,List<ActivityTypeManageDto> activityTypeManageList ) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }
       /* String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);*/
        /*    List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());*/

        /*String warningDay = "0";*/
        FeignJson fJson = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(fJson.getObj());
        Map<String, TSUserDto> userMap = userService.getAllUserIdsMap();
        //获取计划等级的id和名称集合：
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        //   String planLevelListStr = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchAllBusinessConfigs(ConfigTypeConstants.PLANLEVEL);
        Map<String, String> planLevelIdAndNameMap = new HashMap<String, String>();
        for(BusinessConfig cur : planLevelList) {
            planLevelIdAndNameMap.put(cur.getId(), cur.getName());
        }
        for (PlanDto p : planList) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planNumber", p.getPlanNumber());
                root.put("optBtn", this.generateOptBtn(p, parameVo, project));
                root.put("progressRate", this.generateProgressRate(p, warningDay));
                root.put("displayName", p.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getPlanName());
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);
                    JSONObject treeNode1 = new JSONObject();

                    treeNode1.put("value", this.generatePlanNameUrl(p));
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", p.getPlanName());
                    root.put("planName", this.generatePlanNameUrl(p));
                }
                if(!CommonUtil.isEmpty(p.getPlanLevel())) {
                    root.put("planLevelInfo",
                            planLevelIdAndNameMap.get(p.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(p.getPlanLevel()));
                }
//                root.put("ownerInfo",
//                        p.getOwnerInfo() == null ? "" : p.getOwnerInfo().getRealName() + "-"
//                                + p.getOwnerInfo().getUserName());
                if(!CommonUtil.isEmpty(p.getOwner())) {
                    root.put("ownerInfo",
                            userMap.get(p.getOwner()) == null ? "" :  userMap.get(p.getOwner()).getRealName() + "-"
                                    +  userMap.get(p.getOwner()).getUserName());
                }
                root.put("bizCurrentInfo", this.generatePlanBizCurrent(p, statusList));
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
                root.put("isAssignSingleBack", p.getIsAssignSingleBack());
                root.put("bizCurrent", p.getBizCurrent());
                root.put("parent_owner",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getOwner());
                root.put("parent_createBy",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getCreateBy());
                root.put("parent_flowStatus",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getFlowStatus());
                root.put("parent_isAssignSingleBack",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getIsAssignSingleBack());
                root.put("parent_bizCurrent",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getBizCurrent());
                root.put("project_bizCurrent",
                        p.getProject() == null ? "" : p.getProject().getBizCurrent());
                root.put("opContent", p.getOpContent());

                root.put("creator", CommonUtil.isEmpty(userMap.get(p.getCreateBy())) ? "" : userMap.get(p.getCreateBy()).getRealName()
                        + "-"
                        + userMap.get(p.getCreateBy()).getUserName());
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
                root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(p, activityTypeManageList));
                if("1".equals(p.getTaskType())){
                    root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else if("2".equals(p.getTaskType())){
                    root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else{
                    root.put("taskType", p.getTaskType());
                }
                root.put("planType", p.getPlanType());

                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPid(parentPlanIds, planList, rootList.get(i), parameVo, activityTypeManageList,
                    project, statusList, warningDay,planLevelIdAndNameMap,userMap);
        }
        return rootList;
    }


    /**
     * 构造计划树操作栏操作按钮
     *
     * @param plan
     * @return
     * @see
     */
    private String generateOptBtn(PlanDto plan, PlanAuthorityVo parameVo, Project project) {

        String returnStr = "";
        String modifyBtnStr = "<a class='basis ui-icon-pencil' style='display:inline-block;cursor:pointer;' onClick=\"modifyPlanOnTree_('"
                + plan.getId() + "')\" title='修改'> </a>";
        String assignBtnStr = "<a class='basis ui-icon-issue' style='display:inline-block;cursor:pointer;' onClick=\"assignPlanOnTree_('"
                + plan.getId() + "')\" title='发布'></a>";
        String deleteBtnStr = "<a class='basis ui-icon-minus' style='display:inline-block;cursor:pointer;' onClick='deleteOnTree_(\""
                + plan.getId() + "\")' title='删除'></a>";
        String concernBtnStr = "<a class='basis ui-icon-mark' style='display:inline-block;cursor:pointer;' onClick='concernOnTree_(\""
                               + plan.getId() + "\")' title='关注'></a>";
        String unconcernBtnStr = "<a class='basis ui-icon-cancel-follow' style='display:inline-block;cursor:pointer;' onClick='unconcernOnTree_(\""
                                 + plan.getId() + "\")' title='取消关注'></a>";
        String changeBtnStr = "";
        if ("true".equals(plan.getIsChangeSingleBack())) {
            changeBtnStr = "<a class='basis ui-icon-planchange' style='display:inline-block;cursor:pointer;' onClick='changePlanOnTreeFlow_(\""
                    + plan.getId() + "\")' title='变更'></a>";
        }
        else {
            changeBtnStr = "<a class='basis ui-icon-planchange' style='display:inline-block;cursor:pointer;' onClick='changePlanOnTree_(\""
                    + plan.getId() + "\")' title='变更'></a>";
        }
        String discardBtnStr = "<a class='basis ui-icon-trash' style='display:inline-block;cursor:pointer;' onClick='discardPlan_(\""
                + plan.getId() + "\")' title='废弃'></a>";
        String revocationBtnStr = "<a class='basis ui-icon-return' style='display:inline-block;cursor:pointer;' onClick='revocationPlanOnTree_(\""
                + plan.getId() + "\")' title='撤消'></a>";
        if (!hideModify(plan, parameVo, project)) {
            returnStr = returnStr + modifyBtnStr;
        }
        if (!hideAssign(plan, parameVo, project)) {
            returnStr = returnStr + assignBtnStr;
        }
        if (!hideDelete(plan, parameVo, project)) {
            returnStr = returnStr + deleteBtnStr;
        }
        if (!hideChange(plan, parameVo, project)) {
            returnStr = returnStr + changeBtnStr;
        }
        if(!hideRevocation(plan, parameVo, project)){
            returnStr = returnStr + revocationBtnStr;
        }
        if (!hideDiscard(plan, parameVo, project)) {
            returnStr = returnStr + discardBtnStr;
        }
        if (!hideUnconcern(plan, parameVo)){
            returnStr = returnStr + unconcernBtnStr;
        }
        if (!hideConcern(plan, parameVo)){
            returnStr = returnStr + concernBtnStr;
        }
        return returnStr;
    }


    /**
     * 功能描述：判断是否隐藏取消关注按钮
     * @param: [plan, parameVo, currentProject]
     * @Date: 2019/10/15
     * @return:
     */
    private boolean hideUnconcern(PlanDto plan, PlanAuthorityVo parameVo){
        boolean result = true;
        String planUnconcernOperationCode = parameVo.getPlanUnconcernOperationCode();
        if ("true".equals(planUnconcernOperationCode)){
            if (plan.getConcernCode().equals("1")&&!plan.getBizCurrent().equals("EDITING")){
                if (!plan.getResult().equals("false")){
                    result = true;
                }else{
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * 功能描述：判断是否隐藏关注按钮
     * @param: [plan, parameVo, currentProject]
     * @Date: 2019/10/15
     * @return:
     */
    private boolean hideConcern(PlanDto plan, PlanAuthorityVo parameVo){
        boolean result = true;
        String planConcernOperationCode = parameVo.getPlanConcernOperationCode();
        if ("true".equals(planConcernOperationCode)){
            if (plan.getConcernCode().equals("0")&&!plan.getBizCurrent().equals("EDITING")){
                if (!plan.getResult().equals("false")){
                    result = true;
                }else{
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * 判断操作栏修改按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideModify(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planModifyOperationCode = parameVo.getPlanModifyOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        // 需求变跟 权限变跟 如果父计划如果有负责人则给负责人权限 如果没有负责人则给 创建者权限 现在如果不是一级计划计划状态为拟制中时 计划操作权限跟着创建者走
        // 其它情况跟着父计划的负责人走
        if (StringUtils.isNotEmpty(plan.getParentPlanId())
                && plan.getParentPlan() != null
                &&
                // 需求变动 非拟制中的计划和 拟制中计划且父计划是非拟制中计划走新逻辑 其它的走原本逻辑
                (!("EDITING".equals(plan.getBizCurrent())) || (!("EDITING".equals(plan.getParentPlan().getBizCurrent())) && "EDITING".equals(plan.getBizCurrent()))

                )) {
            // 父计划如果有负责人 则权限跟着负责人走 如果 没有负责人权限跟着创建者走
            if (UserUtil.getInstance().getUser().getId().equals(plan.getParentPlan().getOwner())
                    || (UserUtil.getInstance().getUser().getId().equals(
                    plan.getParentPlan().getCreateBy()) && StringUtils.isEmpty(plan.getParentPlan().getOwner()))) {
                if ("true".equals(planModifyOperationCode)) {
                    if ("false".equals(plan.getResult())) {
                        if ("true".equals(isModify)
                                && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                            if (plan.getProject() == null) {
                                plan.setProject(currentProject);
                            }
                            if ("STARTING".equals(plan.getProject().getBizCurrent())) {

                                if (("EDITING".equals(plan.getBizCurrent()) && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack())))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }

                            }
                            else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                                if (!isPmo) {
                                    return true;
                                }
                                else {

                                    if (("EDITING".equals(plan.getBizCurrent()) && (StringUtils.isEmpty(plan.getFlowStatus())
                                            || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack())))) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }

                                }
                            }
                            else {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }

            }
            else {

                return true;
            }

        }
        // 如果是一级计划用原先逻辑
        else {
            if ("true".equals(planModifyOperationCode)) {
                if ("false".equals(plan.getResult())) {
                    if ("true".equals(isModify)
                            && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                        if (plan.getProject() == null) {
                            plan.setProject(currentProject);
                        }
                        if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                            if (UserUtil.getInstance().getUser().getId().equals(plan.getCreateBy())
                                    || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                if (("EDITING".equals(plan.getBizCurrent()) && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack())))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {
                                return true;
                            }
                        }
                        else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                            if (!isPmo) {
                                return true;
                            }
                            else {
                                if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getCreateBy())
                                        || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                    if (("EDITING".equals(plan.getBizCurrent()) && (StringUtils.isEmpty(plan.getFlowStatus())
                                            || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack())))) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }
                                }
                                else {
                                    return true;
                                }
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }

    }

    /**
     * 判断操作栏下达按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideAssign(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planAssignOperationCode = parameVo.getPlanAssignOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        if (StringUtils.isNotEmpty(plan.getParentPlanId())
                && plan.getParentPlan() != null
                &&
                // 需求变动 非拟制中的计划和 拟制中计划且父计划是非拟制中计划走新逻辑 其它的走原本逻辑
                (!("EDITING".equals(plan.getBizCurrent())) || (!("EDITING".equals(plan.getParentPlan().getBizCurrent())) && "EDITING".equals(plan.getBizCurrent()))

                )) {
            // 父计划如果有负责人 则权限跟着负责人走 如果 没有负责人权限跟着创建者走
            if (UserUtil.getInstance().getUser().getId().equals(plan.getParentPlan().getOwner())
                    || (UserUtil.getInstance().getUser().getId().equals(
                    plan.getParentPlan().getCreateBy()) && StringUtils.isEmpty(plan.getParentPlan().getOwner()))) {
                if ("true".equals(planAssignOperationCode)) {
                    if ("false".equals(plan.getResult())) {
                        if ("true".equals(isModify)
                                && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                            if (plan.getProject() == null) {
                                plan.setProject(currentProject);
                            }
                            if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                                if ("EDITING".equals(plan.getBizCurrent())
                                        && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {

                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }

            }
            else {
                return true;

            }

        }
        else {
            if ("true".equals(planAssignOperationCode)) {
                if ("false".equals(plan.getResult())) {
                    if ("true".equals(isModify)
                            && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                        if (plan.getProject() == null) {
                            plan.setProject(currentProject);
                        }
                        if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                            if (UserUtil.getInstance().getUser().getId().equals(plan.getCreateBy())
                                    || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                if ("EDITING".equals(plan.getBizCurrent())
                                        && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {
                                return true;
                            }
                        }
                        else {

                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }

        }

    }

    /**
     * 判断操作栏删除按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideDelete(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planDeleteOperationCode = parameVo.getPlanDeleteOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        if (StringUtils.isNotEmpty(plan.getParentPlanId())
                && plan.getParentPlan() != null
                &&
                // 需求变动 非拟制中的计划和 拟制中计划且父计划是非拟制中计划走新逻辑 其它的走原本逻辑
                (!("EDITING".equals(plan.getBizCurrent())) || (!("EDITING".equals(plan.getParentPlan().getBizCurrent())) && "EDITING".equals(plan.getBizCurrent()))

                )) {
            // 父计划如果有负责人 则权限跟着负责人走 如果 没有负责人权限跟着创建者走
            if (UserUtil.getInstance().getUser().getId().equals(plan.getParentPlan().getOwner())
                    || (UserUtil.getInstance().getUser().getId().equals(
                    plan.getParentPlan().getCreateBy()) && StringUtils.isEmpty(plan.getParentPlan().getOwner()))) {
                if ("true".equals(planDeleteOperationCode)) {
                    if ("false".equals(plan.getResult())) {
                        if ("true".equals(isModify)
                                && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                            if (plan.getProject() == null) {
                                plan.setProject(currentProject);
                            }
                            if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                                if ("EDITING".equals(plan.getBizCurrent())
                                        && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                                if (!isPmo) {
                                    return true;
                                }
                                else {
                                    if ("EDITING".equals(plan.getBizCurrent())
                                            && (StringUtils.isEmpty(plan.getFlowStatus())
                                            || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }
                                }
                            }
                            else {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        else {
            if ("true".equals(planDeleteOperationCode)) {
                if ("false".equals(plan.getResult())) {
                    if ("true".equals(isModify)
                            && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                        if (plan.getProject() == null) {
                            plan.setProject(currentProject);
                        }
                        if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                            if (UserUtil.getInstance().getUser().getId().equals(plan.getCreateBy())
                                    || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                if ("EDITING".equals(plan.getBizCurrent())
                                        && (StringUtils.isEmpty(plan.getFlowStatus())
                                        || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {
                                return true;
                            }
                        }
                        else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                            if (!isPmo) {
                                return true;
                            }
                            else {
                                if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getCreateBy())
                                        || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                    if ("EDITING".equals(plan.getBizCurrent())
                                            && (StringUtils.isEmpty(plan.getFlowStatus())
                                            || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsAssignSingleBack()))) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }
                                }
                                else {
                                    return true;
                                }
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }

    }

    /**
     * 判断操作栏变更按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideChange(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planChangeOperationCode = parameVo.getPlanChangeOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        if ("true".equals(planChangeOperationCode)) {
            if ("false".equals(plan.getResult())) {
                if ("true".equals(isModify)
                        && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                    if (plan.getProject() == null) {
                        plan.setProject(currentProject);
                    }
                    if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                        if (("ORDERED".equals(plan.getBizCurrent())
                                || PlanConstants.PLAN_FLOWSTATUS_LAUNCHED.equals(plan.getBizCurrent())
                                || PlanConstants.PLAN_FLOWSTATUS_TOBERECEIVED.equals(plan.getBizCurrent()))
                                && (StringUtils.isEmpty(plan.getFlowStatus())
                                || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsChangeSingleBack()))) {
                            if (StringUtils.isNotEmpty(plan.getParentPlanId())
                                    && plan.getParentPlan() != null) {
                                boolean isPmoPd1 = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),
                                        ProjectRoleConstants.PMO, plan.getParentPlan().getOwner(),
                                        null);
                                if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getParentPlan().getOwner())
                                        || isPmoPd1) {
                                    return false;
                                }
                                else {
                                    return true;
                                }

                            }
                            else if (UserUtil.getInstance().getUser().getId().equals(
                                    plan.getCreateBy())
                                    || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                return false;
                            }
                            else {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                        if (!isPmo) {
                            return true;
                        }
                        else {
                            if ("ORDERED".equals(plan.getBizCurrent())
                                    && (StringUtils.isEmpty(plan.getFlowStatus())
                                    || PlanConstants.PLAN_FLOWSTATUS_LAUNCHED.equals(plan.getBizCurrent())
                                    || PlanConstants.PLAN_FLOWSTATUS_TOBERECEIVED.equals(plan.getBizCurrent())
                                    || "NORMAL".equals(plan.getFlowStatus()) || "true".equals(plan.getIsChangeSingleBack()))) {
                                if (StringUtils.isNotEmpty(plan.getParentPlanId())
                                        && plan.getParentPlan() != null) {
                                    boolean isPmoPd1 = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),
                                            ProjectRoleConstants.PMO, plan.getParentPlan().getOwner(),
                                            null);
                                    if (UserUtil.getInstance().getUser().getId().equals(
                                            plan.getParentPlan().getOwner())
                                            || isPmoPd1) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }

                                }
                                else if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getCreateBy())
                                        || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {
                                return true;
                            }
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }

    }


    /**
     * 判断操作栏撤消按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideRevocation(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planRevocationOperationCode = parameVo.getPlanRevocationOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        if ("true".equals(planRevocationOperationCode)) {
            if ("false".equals(plan.getResult())) {
                if ("true".equals(isModify)
                        && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                    if (plan.getProject() == null) {
                        plan.setProject(currentProject);
                    }
                    if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                        if ("ORDERED".equals(plan.getBizCurrent())
                                && (!CommonUtil.isEmpty(plan.getFlowStatus()) && "CHANGE".equals(plan.getFlowStatus())
                                && "true".equals(plan.getIsChangeSingleBack()))) {
                            //TODO
                         /*   List<TemporaryPlan> planList = planService.getTemporaryPlanList(plan.getId());
                            if(!CommonUtil.isEmpty(planList)){
                                if(!CommonUtil.isEmpty(planList.get(0).getLauncher())
                                        && planList.get(0).getLauncher().equals(UserUtil.getInstance().getUser().getId())){
                                    return false;
                                }else{
                                    return true;
                                }
                            }else{
                                return true;
                            }*/

                            return true;
                        }else{
                            return true;
                        }
                    }else if("EDITING".equals(plan.getProject().getBizCurrent())){
                        if ("ORDERED".equals(plan.getBizCurrent())
                                && (!CommonUtil.isEmpty(plan.getFlowStatus()) && "CHANGE".equals(plan.getFlowStatus())
                                && "true".equals(plan.getIsChangeSingleBack()))) {
                            //TODO
                            return true;
                            /*List<TemporaryPlan> planList = planService.getTemporaryPlanList(plan.getId());
                            if(!CommonUtil.isEmpty(planList)){
                                if(!CommonUtil.isEmpty(planList.get(0).getLauncher())
                                        && planList.get(0).getLauncher().equals(UserUtil.getInstance().getUser().getId())){
                                    return false;
                                }else{
                                    return true;
                                }
                            }else{
                                return true;
                            }*/
                        }else{
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
                else
                {
                    return true;
                }
            }else{
                return true;
            }
        }else{
            return true;
        }

    }

    /**
     * 判断操作栏废弃按钮是否隐藏
     *
     * @param plan
     * @return
     * @see
     */
    private boolean hideDiscard(PlanDto plan, PlanAuthorityVo parameVo, Project currentProject) {
        String isModify = parameVo.getIsModify();
        String planDiscardOperationCode = parameVo.getPlanDiscardOperationCode();
        boolean isPmo = parameVo.isPmo();
        boolean isProjectManger = parameVo.isProjectManger();
        if ("true".equals(planDiscardOperationCode)) {
            if ("false".equals(plan.getResult())) {
                if ("true".equals(isModify)
                        && !PlanConstants.PLAN_TYPE_FLOW.equals(plan.getTaskType())) {
                    if (plan.getProject() == null) {
                        plan.setProject(currentProject);
                    }
                    if ("STARTING".equals(plan.getProject().getBizCurrent())) {
                        if (!PlanConstants.PLAN_EDITING.equals(plan.getBizCurrent())
                                && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
                            if (StringUtils.isNotEmpty(plan.getParentPlanId())
                                    && plan.getParentPlan() != null) {
                                if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getParentPlan().getOwner())) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else if (UserUtil.getInstance().getUser().getId().equals(
                                    plan.getCreateBy())
                                    || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                return false;
                            }
                            else {
                                return true;
                            }
                        }
                        else {
                            return true;
                        }
                    }
                    else if ("EDITING".equals(plan.getProject().getBizCurrent())) {
                        if (!isPmo) {
                            return true;
                        }
                        else {
                            if (!PlanConstants.PLAN_EDITING.equals(plan.getBizCurrent())
                                    && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
                                if (StringUtils.isNotEmpty(plan.getParentPlanId())
                                        && plan.getParentPlan() != null) {
                                    if (UserUtil.getInstance().getUser().getId().equals(
                                            plan.getParentPlan().getOwner())) {
                                        return false;
                                    }
                                    else {
                                        return true;
                                    }
                                }
                                else if (UserUtil.getInstance().getUser().getId().equals(
                                        plan.getCreateBy())
                                        || (isProjectManger && (plan.getIsCreateByPmo() != null && plan.getIsCreateByPmo()))) {
                                    return false;
                                }
                                else {
                                    return true;
                                }
                            }
                            else {
                                return true;
                            }
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }

    }


    /**
     * 构造计划进度页面展示
     *
     * @param plan
     * @return
     * @see
     */
    private String generateProgressRate(PlanDto plan, String warningDay) {
        String progressRate = plan.getProgressRate();
        if (StringUtils.isEmpty(plan.getProgressRate())) {
            progressRate = "0";
        }
        String warningDayFlag = "before";
        if (StringUtils.isNotEmpty(warningDay)) {
            if (warningDay.trim().startsWith("-")) {
                warningDayFlag = "after";
                warningDay = warningDay.trim().replace("-", "");
            }
        }
        else {
            warningDay = "0";
        }
        if (StringUtils.isEmpty(warningDay)) {
            warningDay = "0";
        }
        Date planEndTime = plan.getPlanEndTime();
        if (planEndTime != null) {
            Date warningDate = dateChange(planEndTime, Integer.parseInt(warningDay),
                    warningDayFlag);
            String currentDate = DateUtil.getStringFromDate(new Date(), DateUtil.YYYY_MM_DD);
            Date nowDate = DateUtil.convertStringDate2DateFormat(
                    currentDate, DateUtil.YYYY_MM_DD);
            //00B050绿色绿色，未完工，未预警 
            //FFFF00黄色黄色，未完工，已预警
            //FF0000红色红色，未完工，已超期 
            //BFBFBF灰色，已完工，未超期
            //0070C0蓝色蓝色，已完工，且超期完工
            	
            	if(warningDate.getTime() <= nowDate.getTime()) {
            		
            		if(!"FINISH".equals(plan.getBizCurrent())) {//超期未完成
            			return "<span style='width:100%;border:1px solid #dee5e7; display:inline-block; height:24px;overflow:hidden;vertical-align:middle;' ><span style='width:"
                                + progressRate + "%;background:#FF0000;color:#000000;padding-left:10px; display:inline-block; height:24px;line-height:24px;' id='"+plan.getId()+"'>"
                                + progressRate + "%</span></span>";
            		}else {//超期已完成
            			return "<span style='width:100%;border:1px solid #dee5e7; display:inline-block; height:24px;overflow:hidden;vertical-align:middle;' ><span style='width:"
                                + progressRate + "%;background:#0070C0;color:#0070C0;padding-left:10px; display:inline-block; height:24px;line-height:24px;' id='"+plan.getId()+"'>"
                                + progressRate + "%</span></span>";
            			
            		}
            
            	}else {
            		if(!"FINISH".equals(plan.getBizCurrent())){//未超期 未完成
            			return "<span style='width:100%;border:1px solid #dee5e7; display:inline-block; height:24px;overflow:hidden;vertical-align:middle;' ><span style='width:"
                                + progressRate + "%;background:#BFBFBF;color:#000000;padding-left:10px; display:inline-block; height:24px;line-height:24px;' id='"+plan.getId()+"'>"
                                + progressRate + "%</span></span>";
            		}else {//未超期 已完成
            			return "<span style='width:100%;border:1px solid #dee5e7; display:inline-block; height:24px;overflow:hidden;vertical-align:middle;' ><span style='width:"
                                + progressRate + "%;background:#00B050;color:#000000;padding-left:10px; display:inline-block; height:24px;line-height:24px;' id='"+plan.getId()+"'>"
                                + progressRate + "%</span></span>";
            			
            			
            		}
            		
            	}
        }else {//预警
            return "<span style='width:100%;border:1px solid #dee5e7;display:inline-block; height:24px;overflow:hidden;vertical-align:middle;' ><span style='width:"
                    + progressRate + "%;background:#FFFF00;color:#000000;padding-left:10px;display:inline-block; height:24px;line-height:24px;' id='"+plan.getId()+"';>"
                    + progressRate + "%</span></span>";
        }
    }


    /**
     * Description: <br>
     *
     * @param date
     * @param day
     * @param beforOrAfter
     * @return
     * @see
     */
    private Date dateChange(Date date, int day, String beforOrAfter) {
        Date changeDate = new Date();
        if ("after".equals(beforOrAfter)) {
            changeDate = DateUtil.nextDay(date, day);
        }
        else {
            changeDate = DateUtil.nextDay(date, 0 - day);
        }
        return changeDate;
    }


    /**
     * 构造计划名称页面链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanNameUrl(PlanDto plan) {
        if ("true".equals(plan.getResult())) {
            return "<a href='javascript:void(0);' onclick=\"viewPlan_(\'" + plan.getId()
                    + "\')\" style='color:gray'>" + plan.getPlanName() + "</a>";
        }
        else {
            return "<a href='javascript:void(0);' onclick=\"viewPlan_(\'" + plan.getId()
                    + "\')\" style='color:blue'>" + plan.getPlanName() + "</a>";
        }
    }


    /**
     * 构造计划状态显示及链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanBizCurrent(PlanDto plan, List<LifeCycleStatus> statusList) {
        String status = "";
        if ("PAUSE".equals(plan.getProjectStatus())) {
            status = "已暂停";
        }
        else if ("CLOSE".equals(plan.getProjectStatus())) {
            status = "已关闭";
        }
        else {
            for (LifeCycleStatus lifeCycleStatus : statusList) {
                if (lifeCycleStatus.getName().equals(plan.getBizCurrent())) {
                    status = lifeCycleStatus.getTitle();
                    break;
                }
            }
        }
        if ("1".equals(plan.getFlowFlag()) && !"true".equals(plan.getIsAssignBack())) {
            status = "<a href='javascript:void(0);' onclick='openFlowPlans_(\"" + plan.getId()
                    + "\")'><font color='blue'>" + status + "</font></a>";
        }
        return status;
    }


    /**
     * 转化计划类型
     *
     * @param plan
     * @return
     * @see
     */
    private String generateTaskNameTypeUrl(PlanDto plan, List<ActivityTypeManageDto> activityTypeManageList) {
        String taskNameTypeDisplay = "";
        for (ActivityTypeManageDto type : activityTypeManageList) {
            if (type.getId().equals(plan.getTaskNameType())) {
                taskNameTypeDisplay = type.getName();
                break;
            }
        }
        return taskNameTypeDisplay;
    }


    /**
     * Description:递归查询获取所有子节点
     *
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                 JSONObject parentObject, PlanAuthorityVo parameVo,
                                 List<ActivityTypeManageDto> activityTypeManageList, Project currentProject,
                                 List<LifeCycleStatus> statusList, String warningDay,Map<String, String> planLevelIdAndNameMap,Map<String, TSUserDto> userMap) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        for (PlanDto plan : planList) {
            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planNumber", plan.getPlanNumber());
                newNode.put("optBtn", this.generateOptBtn(plan, parameVo, currentProject));
                newNode.put("progressRate", this.generateProgressRate(plan, warningDay));
                newNode.put("displayName", plan.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", plan.getPlanName());
                    treeNode.put("image", "folder.gif");
                    newNode.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.generatePlanNameUrl(plan));
                    treeNode1.put("image", "folder.gif");
                    newNode.put("planName", treeNode1);
                }
                else {
                    newNode.put("displayNameNode", plan.getPlanName());
                    newNode.put("planName", this.generatePlanNameUrl(plan));
                }

                newNode.put("planLevelInfo",
                        planLevelIdAndNameMap.get(plan.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(plan.getPlanLevel()));
                String ownerInfo = "";
                if (plan.getOwnerInfo() == null) {
                    if (StringUtils.isNotBlank(plan.getOwner())) {
                        TSUserDto owner = userMap.get(plan.getOwner());
                        ownerInfo = owner.getRealName() + "-" + owner.getUserName();
                    }
                } else {
                    ownerInfo = plan.getOwnerInfo().getRealName() + "-" + plan.getOwnerInfo().getUserName();
                }
                newNode.put("ownerInfo",ownerInfo);
                newNode.put("bizCurrentInfo", this.generatePlanBizCurrent(plan, statusList));
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
                        userMap.get(plan.getCreateBy()) == null ? "" : userMap.get(plan.getCreateBy()).getRealName() + "-"
                                + userMap.get(plan.getCreateBy()).getUserName());
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
                newNode.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(plan, activityTypeManageList));
                if("1".equals(plan.getTaskType())){
                    newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else if("2".equals(plan.getTaskType())){
                    newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else{
                    newNode.put("taskType", plan.getTaskType());
                }

                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPid(parentPlanIds, planList, subNodeList.get(i), parameVo,
                        activityTypeManageList, currentProject, statusList, warningDay,planLevelIdAndNameMap,userMap);
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
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjectsForCustomView(List<PlanDto> planList,
                                                                   PlanAuthorityVo parameVo, Project project,
                                                                   List<LifeCycleStatus> statusList,List<ActivityTypeManageDto> activityTypeManageList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }

        //获取计划等级的id和名称集合：
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        //   String planLevelListStr = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchAllBusinessConfigs(ConfigTypeConstants.PLANLEVEL);
        Map<String, String> planLevelIdAndNameMap = new HashMap<String, String>();
        for(BusinessConfig cur : planLevelList) {
            planLevelIdAndNameMap.put(cur.getId(), cur.getName());
        }
        Map<String,TSUserDto> userAllMap = userService.getAllUserIdsMap();
       /* String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);*/
        /*    List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());*/

        FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(feignJson.getObj());
        for (PlanDto p : planList) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planNumber", p.getPlanNumber());
                root.put("optBtn", this.generateOptBtn(p, parameVo, project));
                root.put("progressRate", this.generateProgressRate(p, warningDay));
                root.put("displayName", this.generatePlanNameUrlForCustomView(p));
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", this.generatePlanNameUrlForCustomView(p));
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.generatePlanNameUrlForCustomView(p));
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", this.generatePlanNameUrlForCustomView(p));
                    root.put("planName", this.generatePlanNameUrlForCustomView(p));
                }

                root.put("planLevelInfo",
                        planLevelIdAndNameMap.get(p.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(p.getPlanLevel()) );
                root.put("ownerInfo",
                        userAllMap.get(p.getOwner()) == null ? "" : userAllMap.get(p.getOwner()).getRealName() + "-"
                                + userAllMap.get(p.getOwner()).getUserName());
                root.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomView(p, statusList));
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
                root.put("isAssignSingleBack", p.getIsAssignSingleBack());
                root.put("bizCurrent", p.getBizCurrent());
                root.put("parent_owner",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getOwner());
                root.put("parent_createBy",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getCreateBy());
                root.put("parent_flowStatus",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getFlowStatus());
                root.put("parent_isAssignSingleBack",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getIsAssignSingleBack());
                root.put("parent_bizCurrent",
                        p.getParentPlan() == null ? "" : p.getParentPlan().getBizCurrent());
                root.put("project_bizCurrent",
                        p.getProject() == null ? "" : p.getProject().getBizCurrent());
                root.put("opContent", p.getOpContent());

                root.put("creator", userAllMap.get(p.getCreateBy()) == null ? "" : userAllMap.get(p.getCreateBy()).getRealName()
                        + "-"
                        + userAllMap.get(p.getCreateBy()).getUserName());
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
                root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(p, activityTypeManageList));
                if("1".equals(p.getTaskType())){
                    root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else if("2".equals(p.getTaskType())){
                    root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else{
                    root.put("taskType", p.getTaskType());
                }
                root.put("planType", p.getPlanType());

                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPidForCustomView(parentPlanIds, planList, rootList.get(i), parameVo, activityTypeManageList,
                    project, statusList, warningDay,planLevelIdAndNameMap,userAllMap);
        }
        return rootList;
    }

    /**
     * 构造计划名称页面链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanNameUrlForCustomView(PlanDto plan) {
        if ("true".equals(plan.getResult())) {
            return "<a style='color:gray;cursor:pointer;'>" + plan.getPlanName() + "</a>";
        }
        else {
            return plan.getPlanName();
        }
    }


    /**
     * 构造计划状态显示及链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanBizCurrentForCustomView(PlanDto plan, List<LifeCycleStatus> statusList) {
        String status = "";
        if ("PAUSE".equals(plan.getProjectStatus())) {
            status = "已暂停";
        }
        else if ("CLOSE".equals(plan.getProjectStatus())) {
            status = "已关闭";
        }
        else {
            for (LifeCycleStatus lifeCycleStatus : statusList) {
                if (lifeCycleStatus.getName().equals(plan.getBizCurrent())) {
                    status = lifeCycleStatus.getTitle();
                    break;
                }
            }
        }

        /*if ("1".equals(plan.getFlowFlag()) && !"true".equals(plan.getIsAssignBack())) {
            status = "<a href='#' onclick='openFlowPlans_(\"" + plan.getId()
                + "\")'><font color='blue'>" + status + "</font></a>";
        }*/
        return status;
    }



    /**
     * Description:递归查询获取所有子节点
     *
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPidForCustomView(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                              JSONObject parentObject, PlanAuthorityVo parameVo,
                                              List<ActivityTypeManageDto> activityTypeManageList, Project currentProject,
                                              List<LifeCycleStatus> statusList, String warningDay,Map<String, String> planLevelIdAndNameMap,Map<String,TSUserDto> userAllMap) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        for (PlanDto plan : planList) {
            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planNumber", plan.getPlanNumber());
                newNode.put("optBtn", this.generateOptBtn(plan, parameVo, currentProject));
                newNode.put("progressRate", this.generateProgressRate(plan, warningDay));
                newNode.put("displayName", plan.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", this.generatePlanNameUrlForCustomView(plan));
                    treeNode.put("image", "folder.gif");
                    newNode.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.generatePlanNameUrlForCustomView(plan));
                    treeNode1.put("image", "folder.gif");
                    newNode.put("planName", treeNode1);
                }
                else {
                    newNode.put("displayNameNode", this.generatePlanNameUrlForCustomView(plan));
                    newNode.put("planName", this.generatePlanNameUrlForCustomView(plan));
                }

                newNode.put("planLevelInfo",
                        planLevelIdAndNameMap.get(plan.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(plan.getPlanLevel()));
                newNode.put("ownerInfo",
                        userAllMap.get(plan.getOwner()) == null ? "" : userAllMap.get(plan.getOwner()).getRealName() + "-"
                                + userAllMap.get(plan.getOwner()).getUserName());
                newNode.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomView(plan, statusList));
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
                        userAllMap.get(plan.getCreateBy()) == null ? "" : userAllMap.get(plan.getCreateBy()).getRealName() + "-"
                                + userAllMap.get(plan.getCreateBy()).getUserName());
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
                newNode.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(plan, activityTypeManageList));
                if("1".equals(plan.getTaskType())){
                    newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else if("2".equals(plan.getTaskType())){
                    newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                }else{
                    newNode.put("taskType", plan.getTaskType());
                }
                newNode.put("planType", plan.getPlanType());

                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPidForCustomView(parentPlanIds, planList, subNodeList.get(i), parameVo,
                        activityTypeManageList, currentProject, statusList, warningDay,planLevelIdAndNameMap,userAllMap);
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
     * 查询是否延期列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "planIsDelayList")
    @ResponseBody
    public void planIsDelayList(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "DELAY");
        obj2.put("name", "延期未完成");
        jsonList.add(obj2);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "useablePlanLevelList")
    @ResponseBody
    public void useablePlanLevelList(HttpServletRequest request, HttpServletResponse response) {

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
     //   List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusListForProblem")
    @ResponseBody
    public void statusListForProblem(HttpServletRequest request, HttpServletResponse response) {

        List<LifeCycleStatus> lifeCycleStatus = new ArrayList<LifeCycleStatus>();
        LifeCycleStatus a = new LifeCycleStatus();
        a.setName("ORDERED");
        a.setTitle("执行中");
        lifeCycleStatus.add(a);
        LifeCycleStatus b = new LifeCycleStatus();
        b.setName("FINISH");
        b.setTitle("已完工");
        lifeCycleStatus.add(b);
        LifeCycleStatus c = new LifeCycleStatus();
        c.setName("FEEDBACKING");
        c.setTitle("完工确认");
        lifeCycleStatus.add(c);

        String jonStr = JsonUtil.getCodeTitleJson(lifeCycleStatus, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusList")
    @ResponseBody
    public void statusList(HttpServletRequest request, HttpServletResponse response) {

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> lifeCycleStatus = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        String jonStr = JsonUtil.getCodeTitleJson(lifeCycleStatus, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
 * 查询活动名称类型
 * 
 * @param request
 * @param response
 */
@RequestMapping(params = "getTaskNameTypes")
@ResponseBody
private void getTaskNameTypes(HttpServletRequest request, HttpServletResponse response) {
    // 给计划类型设置初始值
    List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(true);
    String jonStr = JsonUtil.getCodeTitleJson(activityTypeManageList, "name", "id");
    jonStr = jonStr.replaceAll("'", "\"");
    try {
        TagUtil.ajaxResponse(response, jonStr);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}


    /**
     * 查询所有活动名称类型
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTaskNameTypesAll")
    @ResponseBody
    private void getTaskNameTypesAll(HttpServletRequest request, HttpServletResponse response) {
        // 给计划类型设置初始值
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        String jonStr = JsonUtil.getCodeTitleJson(activityTypeManageList, "name", "id");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询依赖关系
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getRelayOns")
    @ResponseBody
    public void getRelayOns(HttpServletRequest request, HttpServletResponse response) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "FS");
        obj.put("name", "完成到开始（FS）");
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "SS");
        obj2.put("name", "开始到开始（SS）");
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "FF");
        obj3.put("name", "结束到结束（FF）");
        jsonList.add(obj3);
        JSONObject obj4 = new JSONObject();
        obj4.put("id", "SF");
        obj4.put("name", "开始到结束（SF）");
        jsonList.add(obj4);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询限制类别
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getLimitTypes")
    @ResponseBody
    public void getLimitTypes(HttpServletRequest request, HttpServletResponse response) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "1");
        obj.put("name", "越早越好");
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "2");
        obj2.put("name", "不得早于…开始");
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "3");
        obj3.put("name", "不得晚于…结束");
        jsonList.add(obj3);
        
        JSONObject obj4 = new JSONObject();
        obj4.put("id", "4");
        obj4.put("name", "不得早于…结束");
        jsonList.add(obj4);
        
        JSONObject obj5 = new JSONObject();
        obj5.put("id", "5");
        obj5.put("name", "不得晚于…开始");
        jsonList.add(obj5);
        
        JSONObject obj6 = new JSONObject();
        obj6.put("id", "6");
        obj6.put("name", "必须开始与");
        jsonList.add(obj6);
        
        JSONObject obj7 = new JSONObject();
        obj7.put("id", "7");
        obj7.put("name", "必须结束于");
        jsonList.add(obj7);
        
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询计划类别
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "taskTypeList")
    @ResponseBody
    public void taskTypeList(HttpServletRequest request, HttpServletResponse response) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "1");
        obj.put("name", PlanConstants.PLAN_TYPE_WBS);
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "2");
        obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "3");
        obj3.put("name", PlanConstants.PLAN_TYPE_FLOW);
        jsonList.add(obj3);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 项目计划新增页面跳转
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(PlanDto plan, HttpServletRequest req) {
        // String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = false;
        boolean isProjectManger = false;
        String isPmoPd = req.getParameter("isPmo");
        String isProjectMangerPd = req.getParameter("isProjectManger");
        String teamId = req.getParameter("teamId");
        if (isPmoPd.equals("true")) {
            isPmo = true;
        }
        if (isProjectMangerPd.equals("true")) {
            isProjectManger = true;
        }
        // boolean isPmo = roleUserService.judgeHasLimit(ProjectRoleConstants.PMO, userId, null);
        // String teamId = projRoleService.getTeamIdByProjectId(plan.getProjectId());
        /*
         * boolean isProjectManger =
         * roleUserService.judgeHasLimit(ProjectRoleConstants.PROJ_MANAGER,
         * userId, teamId);
         */
        String projectIdForAdd = req.getParameter("projectId");
        Project project = new Project();
        Date parentStartTime = new Date();
        Date parentEndTime = new Date();
        // 新建计划子计划时随机生成计划的UUID
        plan.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
        plan.setBizCurrent(PlanConstants.PLAN_EDITING);
        plan.setCreateBy(ResourceUtil.getCurrentUser().getId());
        plan.setCreateName(ResourceUtil.getCurrentUser().getUserName());
        plan.setCreateFullName(ResourceUtil.getCurrentUser().getRealName());
        plan.setCreateTime(new Date());
        // 责任人List取值
        List<TSUserDto> userList = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(projectIdForAdd)) {
            project = projectService.getProjectEntity(projectIdForAdd);
            /*project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
            if (project != null) {
                plan.setProject(project);
                parentStartTime = project.getStartProjectTime();
                parentEndTime = project.getEndProjectTime();
                List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                for (TSUserDto r : users) {
                    if (r != null) {
                        TSUserDto o = userService.getUserByUserId(r.getId());
                        if (o != null) {
                            userList.add(o);
                        }
                    }
                }
            }
        }

        // 根据parentPlanId是否为空判断是创建计划还是创建子计划
        // 给计划类别设置初始值
        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            parentStartTime = parent.getPlanStartTime();
            parentEndTime = parent.getPlanEndTime();
            String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
            String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");
            req.setAttribute("planStartTimeRm", parentStartTimeStr);
            req.setAttribute("planEndTimeRm", parentEndTimeStr);

            if (parent == null || PlanConstants.PLAN_TYPE_WBS.equals(parent.getTaskType())) {
                if (isPmo || isProjectManger) {
                    plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                }
                else {
                    plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                }
            }
            else {
                plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
            }

        }
        else {
            if (isPmo || isProjectManger) {
                plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
            }
            else {
                plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
            }
        }

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

        if (project != null && StringUtils.isNotEmpty(project.getProjectTimeType())) {
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

        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            plan.setPlanStartTime(parent.getPlanStartTime());
            Date date = (Date)plan.getPlanStartTime().clone();
            plan.setPlanEndTime(DateUtil.nextWorkDay(date, 0));
            req.setAttribute("planStartTimeRm", plan.getPlanStartTime());
            req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
        }

        plan.setMilestone("");

        // 责任人对应的责任部门list取值
        Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");
        JSONArray deptList = new JSONArray();
        JSONObject obj = null;
        if (!CommonUtil.isEmpty(userList)) {
            for (TSUserDto user : userList) {
                obj = new JSONObject();
                obj.put("userId", user.getId());
                // 部门为空判断
                List<TSDepartDto> departList = departMap.get(user.getId());
                String departName = "";
                if (!CommonUtil.isEmpty(departList)) {
                    for(TSDepartDto departDto : departList){
                        if(CommonUtil.isEmpty(departName)){
                            departName = departDto.getDepartname();
                        }else{
                            departName = departName+","+departDto.getDepartname();
                        }
                    }
                    obj.put("departname", departName);
                }
                else {
                    obj.put("departname", "");
                }

                deptList.add(obj);
            }
        }

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

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

        /* List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());*/
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

        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        req.setAttribute("userList2", jonStr2);
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr3);
        req.setAttribute("userList", userList);

        req.setAttribute("ownerShow", "");
        req.setAttribute("planLevelShow", "");
        req.getSession().setAttribute("peojectIdForAdd", projectIdForAdd);
        String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
        String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");
        req.setAttribute("parentStartTime", parentStartTimeStr);
        req.setAttribute("parentEndTime", parentEndTimeStr);
        req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
        // req.setAttribute("planLevelList", planLevelList);
        req.setAttribute("disabled", "false");
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
               /* PlanDto statusP = new PlanDto();
                planService.initBusinessObject(statusP);*/
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
        req.setAttribute("plan_", plan);
        req.setAttribute("useObjectId", plan.getId());
        req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
        req.setAttribute("projectId", projectIdForAdd);

        redisService.setToRedis("INPUTSLIST", plan.getId(), null);

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

        short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
        List<TSTypeDto> curType = tsMap.get(dictCode);
        List<Object> list = new ArrayList<Object>();
        // this.setTypeList(list);
        if(!CommonUtil.isEmpty(curType)){
            req.setAttribute("fileSecurityLevel", curType.get(0).getTypecode());
        }


        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectIdForAdd);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        req.setAttribute("yanfa", "yanfa");


        return new ModelAndView("com/glaway/ids/pm/project/plan/plan-add");
    }


    /**
     * 查询名称库键值对
     *
     * @param request
     * @param response
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "standardValueExceptDesign")
    @ResponseBody
    public void standardValueExceptDesign(HttpServletRequest request, HttpServletResponse response) {
        String message = "没有名称库";
        String activeCategory = request.getParameter("activeCategory");
        NameStandardDto nameStandard = new NameStandardDto();
        nameStandard.setStopFlag("启用");
        nameStandard.setActiveCategory(activeCategory);
        List<NameStandardDto> list = nameStandardService.searchNameStandardsExceptDesign(nameStandard);

        String jonStr = JsonUtil.getCodeTitleJson(list, "id", "name");
        request.getSession().setAttribute("standardValue", jonStr);
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "userList2")
    @ResponseBody
    public void userList2(HttpServletRequest request, HttpServletResponse response) {
        String projectId2 = request.getParameter("projectId");
        /*
         * if (StringUtils.isEmpty(projectId2) && StringUtils.isNotEmpty(projectId)) {
         * projectId2 = projectId;
         * }
         */
        List<TSUserDto> userListForPlan = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(projectId2)) {
            Project project = projectService.getProjectEntity(projectId2);
            /* Project project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
            if (project != null) {
                List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                for (TSUserDto r : users) {
                    if (r != null) {
                        TSUserDto o = userService.getUserByUserId(r.getId());
                        if (o != null) {
                            userListForPlan.add(o);
                        }
                    }
                }
            }
        }

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        String userId = "";
        for (TSUserDto t : userListForPlan) {
            if(!userId.contains(t.getId())){
                JSONObject obj = new JSONObject();
                obj.put("id", t.getId());
                obj.put("realName", t.getRealName() + "-" + t.getUserName());
                jsonList.add(obj);
            }
            userId = userId+","+t.getId();
        }

        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getUserList")
    @ResponseBody
    public AjaxJson getUserList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String projectId2 = request.getParameter("projectId");
        /*
         * if (StringUtils.isEmpty(projectId2) && StringUtils.isNotEmpty(projectId)) {
         * projectId2 = projectId;
         * }
         */
        List<TSUserDto> userListForPlan = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(projectId2)) {
            Project project = projectService.getProjectEntity(projectId2);
            /* Project project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
            if (project != null) {
                List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                for (TSUserDto r : users) {
                    if (r != null) {
                        TSUserDto o = userService.getUserByUserId(r.getId());
                        if (o != null) {
                            userListForPlan.add(o);
                        }
                    }
                }
            }
        }

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userListForPlan) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj);
        }

        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        j.setObj(jonStr);
        return j;
    }


    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "milestoneList")
    @ResponseBody
    public void milestonelList(HttpServletRequest request, HttpServletResponse response) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "true");
        obj.put("name", "是");
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "false");
        obj2.put("name", "否");
        jsonList.add(obj2);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 计划前置选择页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goPlanPreposeTree")
    public ModelAndView goPlanPreposeTree(PlanDto plan, HttpServletRequest req) {
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);

        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr);
        req.setAttribute("planId", plan.getId());
        req.setAttribute("projectId", plan.getProjectId());
        req.setAttribute("preposeIds", plan.getPreposeIds());

        return new ModelAndView("com/glaway/ids/pm/project/plan/planPreposeList");
    }



    /**
     * 计划前置选择页面初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planPreposeList")
    public void planPreposeList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        PlanDto p = new PlanDto();
        p.setProjectId(projectId);
        List<PlanDto> planList = planService.queryPlansExceptInvalid(p);
        List<PlanDto> list = new ArrayList<PlanDto>();
        if (plan != null && !plan.getId().startsWith(PlanConstants.PLAN_CREATE_UUID)) {
            // this.modifyId = plan.getId();
            Map<String, String> map = getPlanAllChildren(plan, planList);
            for (PlanDto node : planList) {
                if (StringUtils.isEmpty(map.get(node.getId()))) {
                    list.add(node);
                }
            }
        }
        else {
            list = planList;
        }
        boolean isModifyBoo = false;
        isModifyBoo = projectService.isModifyForPlan(projectId);
        String isModify = "false";
        if (isModifyBoo) {
            isModify = "true";
        }

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = String.valueOf(fJson.getObj());
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode("false");
        parameVo.setPlanAssignOperationCode("false");
        parameVo.setPlanDeleteOperationCode("false");
        parameVo.setPlanChangeOperationCode("false");
        parameVo.setPlanDiscardOperationCode("false");
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);
        /*Project project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(list, parameVo, project, statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);

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
     * 根据计划ID查找其所有子计划（包括计划本身和所有子孙计划）
     *
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
     *
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
     * 项目计划新增或更新后保存
     *
     * @param plan
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSave")
    @ResponseBody
    public FeignJson doSave(PlanDto plan, HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        //添加创建人信息
        CommonInitUtil.initGLVDataForCreate(plan);
        /*
         * // 活动名称的Id
         * String taskNameId = request.getParameter("taskNameId");
         */

        try {
            if (StringUtils.isNotEmpty(plan.getId())) {

                TSUserDto user = ResourceUtil.getCurrentUser();
                plan.setLauncher(user.getId());

                if (plan.getId().startsWith(PlanConstants.PLAN_CREATE_UUID)) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanSuccess");
                    failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanFail");
                    failMessageCode = GWConstants.ERROR_2002;
                    // plan.setId(null);
                    String planStr = planService.initPlan(plan);
                    plan = JSON.parseObject(JsonFromatUtil.formatJsonToList(planStr),PlanDto.class);
                    if (StringUtils.isNotEmpty(request.getParameter("planSource"))) {
                        plan.setPlanSource(request.getParameter("planSource"));
                    }


                    if("wbs".equals(plan.getTaskType())){
                        plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                    }else if("task".equals(plan.getTaskType())){
                        plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                    }

                    plan.setPlanType(PlanConstants.PLAN_TYPE_TASK);
                    if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                        PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                        if (parent != null) {
                            if (PlanConstants.PLAN_TYPE_WBS.equals(parent.getPlanType())) {
                                String userId = UserUtil.getInstance().getUser().getId();
                                boolean isProjectManger = projRoleService.isProjRoleByUserIdAndRoleCode(
                                        plan.getProjectId(), ProjectRoleConstants.PROJ_MANAGER, userId);
                                boolean isPmo = projRoleService.isSysRoleByUserIdAndRoleCode(
                                        userId, ProjectRoleConstants.PMO);
                                if (isProjectManger || isPmo) {
                                    plan.setPlanType(PlanConstants.PLAN_TYPE_WBS);
                                }
                            }
                        }
                        else {
                            plan.setPlanType(PlanConstants.PLAN_TYPE_WBS);
                        }
                    }
                    else {
                        plan.setPlanType(PlanConstants.PLAN_TYPE_WBS);
                    }
                    /*
                     * 如果计划名称是从活动名称库中选中的,则把该名称的类型赋值给计划的taskNameType
                     * if (taskNameId != null) {
                     * NameStandard nameStandard = planService.getEntity(
                     * NameStandard.class, taskNameId);
                     * if (nameStandard != null) {
                     * plan.setPlanName(nameStandard.getName());
                     * if (CommonConstants.NAMESTANDARD_TYPE_REVIEW
                     * .equals(nameStandard.getActiveCategory())) {
                     * plan.setTaskNameType("评审任务");
                     * }
                     * }
                     * }
                     */
                   /* Map<String,Object> map = new HashMap<String,Object>();
                    map.put("plan",plan);
                    map.put("user",UserUtil.getInstance().getUser());*/
                    CommonInitUtil.initGLVDataForCreate(plan);
                    List<InputsDto> inputsList = new ArrayList<>();
                    String inpStr = (String)redisService.getFromRedis("INPUTSLIST", plan.getId());
                    if(!CommonUtil.isEmpty(inpStr)){
                        inputsList = JSON.parseArray(inpStr,InputsDto.class);
                    }
                    String inputsStr = JsonUtil.getListJsonWithoutQuote(inputsList);
                    String planToStr = JSON.toJSONString(plan);
                    List<ResourceLinkInfoDto> resourceLinkInfoList = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
                    Map<String,Object> map = new HashMap<>();
                    map.put("user",UserUtil.getInstance().getUser());
                    map.put("inputList",inputsList);
                    map.put("resourceLinkInfoList",resourceLinkInfoList);
                    map.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
                    FeignJson fj = planService.saveAsCreate(planToStr,map);
                    if (fj.isSuccess()) {
                        j.setObj(fj.getObj());
                    }
                }
                else {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.updatePlanSuccess");
                    failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.updatePlanFail");
                    failMessageCode = GWConstants.ERROR_2003;
                    PlanDto t = planService.getPlanEntity(plan.getId());
                    plan.setPlanNumber(t.getPlanNumber());
                    plan.setProgressRate(t.getProgressRate());
                    String isAssignSingleBack = t.getIsAssignSingleBack();
                    BeanUtil.copyBeanNotNull2Bean(plan, t);
                    if ("true".equals(isAssignSingleBack)) {
                        t.setIsAssignSingleBack("true");
                    }
                    List<ResourceLinkInfoDto> resourceLinkInfoList = ( List<ResourceLinkInfoDto>)request.getSession().getAttribute("resourceListForPlan");
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("plan",t);
                    map.put("user",UserUtil.getInstance().getUser());
                    map.put("user",UserUtil.getInstance().getUser());
                    map.put("resourceLinkInfoList",resourceLinkInfoList);
                    map.put("orgId",ResourceUtil.getCurrentUserOrg().getId());
                    planService.saveAsUpdate(map);

                }
                request.getSession().setAttribute("id", plan.getId());
                request.getSession().setAttribute("useObjectType", "PLAN");
                request.getSession().setAttribute("useObjectId", plan.getId());
                /*     log.info(message, plan.getId(), plan.getId().toString());*/
            }
        }
        catch (Exception e) {
            log.error(failMessage, e, "", plan.getId().toString());
            Object[] params = new Object[] {failMessage, plan.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }



    /**
     * 获取列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "namestandardDeliverables")
    @ResponseBody
    public void namestandardDeliverables(HttpServletRequest request, HttpServletResponse response)
    {
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        String flag = request.getParameter("flag");
        String ids = request.getParameter("ids");
        String source = request.getParameter("source");
        String useObjectId = request.getParameter("useObjectId");

        DeliveryStandardDto condition = new DeliveryStandardDto();
        if (flag != null && flag.equals("start"))
        {
            condition.setStopFlag(ConfigStateConstants.START);
        }
        else if (flag != null && flag.equals("stop"))
        {
            condition.setStopFlag(ConfigStateConstants.STOP);
        }


        if("planChange".equals(source)){
            List<TempPlanInputsDto> tempInputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");
            if(!CommonUtil.isEmpty(tempInputList))
             {
                for(TempPlanInputsDto tempInp : tempInputList)
                {
                    if(CommonUtil.isEmpty(ids))
                    {
                        ids = tempInp.getOriginObjectId();
                    }else {
                        ids = ids+","+tempInp.getOriginObjectId();
                    }
                }
            }
        }else{
            List<InputsDto> inputsList = new ArrayList<>();
            String inpStr = "";
            if(!CommonUtil.isEmpty(useObjectId)) {
                inpStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            }
            if(!CommonUtil.isEmpty(inpStr)){
                inputsList = JSON.parseArray(inpStr,InputsDto.class);
            }
            if(!CommonUtil.isEmpty(inputsList)){
                for(InputsDto inp : inputsList){
                    if(!CommonUtil.isEmpty(inp.getOriginObjectId())){
                        if(CommonUtil.isEmpty(ids)){
                            ids = inp.getOriginObjectId();
                        }else{
                            ids = ids+","+inp.getOriginObjectId();
                        }
                    }
                }
            }
        }




        List<DeliveryStandardDto> list = nameStandardService.searchDeliverablesForPage(
                condition, page, rows, ids);
        /*List<DeliveryStandardDto> tempList = new ArrayList<>();
        List<DeliveryStandardDto> list = nameStandardService.searchDeliverablesForPage(
                condition, page, rows, ids);

        if(CommonUtil.isEmpty(inputsList)){
            tempList.addAll(list);
        }else{
            Map<String, String> nameMap = new HashMap<String, String>();
            for (InputsDto inp : inputsList) {
                nameMap.put(inp.getName(), inp.getName());
            }
            for (DeliveryStandardDto deliveryStandard : list) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    tempList.add(deliveryStandard);
                }
            }
        }

        int count = tempList.size();
        List<DeliveryStandardDto> resList = new ArrayList<DeliveryStandardDto>();
        if (count > page * rows) {
            resList = tempList.subList((page - 1) * rows, page * rows);
        }
        else {
            resList = tempList.subList((page - 1) * rows, tempList.size());
        }*/

        long count = nameStandardService.getDeliverablesCount(condition, ids);

        String datagridStr = "{\"rows\":" + JSON.toJSONString(list) + ",\"total\":" + count + "}";

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try
        {
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(datagridStr);
            pw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 搜索配置项
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchConfigsFromParams")
    @ResponseBody
    public void searchConfigsFromParams(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException
    {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        String ids = request.getParameter("ids");
        name = URLDecoder.decode(name, "UTF-8");
        no = URLDecoder.decode(no, "UTF-8");

        DeliveryStandardDto condition = new DeliveryStandardDto();
        condition.setName(name);
        condition.setNo(no);
        condition.setStopFlag(ConfigStateConstants.START);

        List<DeliveryStandardDto> list = new ArrayList<DeliveryStandardDto>();
        long count = 0;
        if (!StringUtil.isEmpty(ids))
        {
            list = nameStandardService.searchDeliverablesForPage(condition,
                    Integer.parseInt(page), Integer.parseInt(rows), ids);
            count = nameStandardService.getDeliverablesCount(condition, ids);
        }
        else
        {
            list = nameStandardService.searchDeliveryStandardsForPage(condition,
                    Integer.parseInt(page), Integer.parseInt(rows));
            count = nameStandardService.getSearchCount(condition);
        }

        String datagridStr = "{\"rows\":" + JSON.toJSONString(list) + ",\"total\":" + count + "}";

        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try
        {
            PrintWriter pw = response.getWriter();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            pw.write(datagridStr);
            pw.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Description: <br>
     * 1、存储库结构管理<br>
     * 2、添加文件附件<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addFileAttachments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addFileAttachments(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String fileId = "";
            fileId = planService.getFoldIdByProjectId(request.getParameter("projectId"));
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();
            String attachmentName = "";
            String attachmentURL = "";
            String uuidNew = "";
            String uuid = "";
            String[] fileArr = null;
            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                InputStream fos = mf.getInputStream();// 获取文件流
                attachmentName = mf.getOriginalFilename();
                fileArr = StringUtils.split(attachmentName, "\\.");
                String fileType = fileArr[fileArr.length - 1];
                uuidNew = UUID.randomUUID().toString();
                uuid = uuidNew.replace("-", "") + "." + fileType;
                attachmentURL = JackrabbitUtil.handleFileUpload(fos, uuid,
                        JackrabbitConstants.PROJECT_LIBRARY_FILE_PATH, true);
                AttachmentVo addProjLibDocumentVo = new AttachmentVo();
                addProjLibDocumentVo.setAttachmentName(attachmentName);
                addProjLibDocumentVo.setDowmLoadUrl(attachmentURL);
                addProjLibDocumentVo.setUuid(uuidNew);
                addProjLibDocumentVo.setAttachmentShowName(attachmentName.substring(0, attachmentName.lastIndexOf(".")));
            }
            j.setObj(attachmentName + "," + attachmentURL + "," + uuidNew +"," + attachmentName.substring(0, attachmentName.lastIndexOf("."))+","+UserUtil.getInstance().getUser().getRealName()+"-"+UserUtil.getInstance().getUser().getUserName()+","+new Date());
            // 计划分解需要。使用msg存放fileid
            j.setMsg(fileId);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GWException(e);
        }
        finally {
            return j;
        }
    }

    /**
     * 获取执行中的项目列表
     *
     * @author zhousuxia
     * @version 2018年4月19日
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "searchProjectDatagrid")
    public void searchProjectDatagrid(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        /*PageList pageList = planService.queryProjectList(conditionList);
        List<Project> projectList = pageList.getResultList();
        long count = pageList.getCount();*/
        FeignJson j = planService.queryProjectList(conditionList);

        List<Project> projectList = new ArrayList<>();
        long count = 0;
        if (j.isSuccess()) {
            Map<String,Object> attributes = j.getAttributes();
            projectList = (List<Project>) attributes.get("resultList");
            count = StringUtils.isNotBlank(attributes.get("count").toString()) ? (Integer) attributes.get("count") : 0;
        }
        String json = JsonUtil.getListJsonWithoutQuote(projectList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 项目计划页面初始化时获取计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "reloadPlanListWithView")
    @ResponseBody
    public AjaxJson reloadPlanListWithView(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {

        String planNumber_ = request.getParameter("planNumber_");
        String isDelay = request.getParameter("isDelay");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planNumber_)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber_);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(isDelay)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(isDelay.split(","));
            vo.setCondition("in");
            vo.setValue(isDelay);
            vo.setKey("Plan.isDelay");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }


        // 获取所属项目
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planRevocationOperationCode = request.getParameter("planRevocationOperationCode");
        String planViewId = request.getParameter("planViewId");
        boolean isPmo = "true".equals(request.getParameter("isPmo"));
        boolean isProjectManger = "true".equals(request.getParameter("isProjectManger"));
        plan.setProjectId(projectId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("planDto",plan);
        map.put("planViewInfoId",planViewId);
        map.put("conditionList",conditionList);
        map.put("userName",userName);
        map.put("progressRate",progressRate);
        map.put("workTime",workTime);
        map.put("userId",ResourceUtil.getCurrentUser().getId());
        List<PlanDto> planList = planService.queryPlanListForTreegridView(map);
        List<PlanDto> resultPlanList = planService.queryPlanListForTreegridWithView(planList,planViewId,UserUtil.getCurrentUser().getId());

        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(resultPlanList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : resultPlanList) {
                if (p.getId().equals(plan.getId())) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                resultPlanList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto pla = new PlanDto();
        pla.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(pla);
        Map<String, PlanDto> listMap = new HashMap<>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<>();
        for (PlanDto p : resultPlanList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(resultPlanList);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanRevocationOperationCode(planRevocationOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);
        Project project = projectService.getProjectEntity(projectId);
        FeignJson fj = planService.getLifeCycleStatusList();

        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
//        List<LifeCycleStatus> statusList = pl.getPolicy().getLifeCycleStatusList();
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(tempList, parameVo, project,
                statusList,activityTypeManageList);
        String resultJSON = JSON.toJSONString(rootList);


        List<PlanViewColumnInfoDto> columnInfoList = planService.getColumnInfoListByPlanViewInfoId(planViewId);
        PlanViewColumnInfoDto columnInfo = new PlanViewColumnInfoDto();
        String header = "";
        if(!CommonUtil.isEmpty(columnInfoList)){
            columnInfo = columnInfoList.get(0);
            if(!CommonUtil.isEmpty(columnInfo) && !CommonUtil.isEmpty(columnInfo.getColumnId())){
                if(columnInfo.getColumnId().contains("planNo")){
                    header = header + "编号,";
                }
                header = header + "进度,";
                header = header + "操作,";
                header = header + "计划名称,";
                if(columnInfo.getColumnId().contains("planLevel")){
                    header = header + "计划等级,";
                }
                if(columnInfo.getColumnId().contains("planType")){
                    header = header + "计划类型,";
                }
                if(columnInfo.getColumnId().contains("planTaskType")){
                    header = header + "计划类别,";
                }
                if(columnInfo.getColumnId().contains("status")){
                    header = header + "状态,";
                }
                if(columnInfo.getColumnId().contains("owner")){
                    header = header + "负责人,";
                }
                if(columnInfo.getColumnId().contains("planStartTime")){
                    header = header + "开始时间,";
                }
                if(columnInfo.getColumnId().contains("planEndTime")){
                    header = header + "结束时间,";
                }
                if(columnInfo.getColumnId().contains("assigner")){
                    header = header + "发布人,";
                }
                if(columnInfo.getColumnId().contains("assignTime")){
                    header = header + "发布时间,";
                }
                if(columnInfo.getColumnId().contains("workTime")){
                    header = header + "工期(天),";
                }
                if(columnInfo.getColumnId().contains("preposePlan")){
                    header = header + "前置计划,";
                }
                if(columnInfo.getColumnId().contains("mileStone")){
                    header = header + "里程碑,";
                }
                if(columnInfo.getColumnId().contains("creator")){
                    header = header + "创建者,";
                }
                if(columnInfo.getColumnId().contains("createTime")){
                    header = header + "创建时间,";
                }
            }else{
                header = header + "进度,";
                header = header + "操作,";
                header = header + "计划名称,";
            }

            header = header.substring(0, header.length()-1);
        }

       /* try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg(header);
        j.setObj(resultJSON);
        return j;


    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "saveUseProjectPlanViewLink")
    @ResponseBody
    public AjaxJson saveUseProjectPlanViewLink(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String planViewId = request.getParameter("planViewId");
            String projectId = request.getParameter("projectId");
            planService.saveUserViewProjectLink(projectId,planViewId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());    //保存当前用户与视图项目之间的关系
            j.setSuccess(true);
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }

    /**
     * 获取查询条件
     *
     * @author zhousuxia
     * @version 2018年6月8日
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "getSearchCondition")
    @ResponseBody
    public AjaxJson getSearchCondition(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String planViewInfoId = request.getParameter("planViewId");
        List<PlanViewSearchConditionDto> searchConditionList = new ArrayList<>();
        if(!CommonUtil.isEmpty(planViewInfoId)){
            searchConditionList = planService.getSearchConditionListByViewId(planViewInfoId);
        }
        String resultJSON = JSON.toJSONString(searchConditionList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;

    }

    /**
     * 获取展示列
     *
     * @author zhousuxia
     * @version 2018年6月13日
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "getShowColumn")
    @ResponseBody
    public AjaxJson getShowColumn(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String planViewInfoId = request.getParameter("planViewId");
        List<PlanViewColumnInfoDto> columnInfoList = new ArrayList<>();
        String headers = "";
        if(!CommonUtil.isEmpty(planViewInfoId)){
            columnInfoList = planService.getColumnInfoListByPlanViewInfoId(planViewInfoId);

            if(!CommonUtil.isEmpty(columnInfoList.get(0).getColumnId())){
                String columnIds = columnInfoList.get(0).getColumnId();
                if(columnIds.contains("planNo")){
                    headers = headers + "编号,";
                }
                if(columnIds.contains("planLevel")){
                    headers = headers + "计划等级,";
                }
                if(columnIds.contains("planType")){
                    headers = headers + "计划类型,";
                }
                if(columnIds.contains("planTaskType")){
                    headers = headers + "计划类别,";
                }
                if(columnIds.contains("status")){
                    headers = headers + "状态,";
                }
                if(columnIds.contains("owner")){
                    headers = headers + "负责人,";
                }
                if(columnIds.contains("planStartTime")){
                    headers = headers + "开始时间,";
                }
                if(columnIds.contains("planEndTime")){
                    headers = headers + "结束时间,";
                }
                if(columnIds.contains("assigner")){
                    headers = headers + "发布人,";
                }
                if(columnIds.contains("assignTime")){
                    headers = headers + "发布时间,";
                }
                if(columnIds.contains("workTime")){
                    headers = headers + "工期(天),";
                }
                if(columnIds.contains("preposePlan")){
                    headers = headers + "前置计划,";
                }
                if(columnIds.contains("mileStone")){
                    headers = headers + "里程碑,";
                }
                if(columnIds.contains("creator")){
                    headers = headers + "创建者,";
                }
                if(columnIds.contains("createTime")){
                    headers = headers + "创建时间,";
                }
            }

        }
        String resultJSON = JSON.toJSONString(columnInfoList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        j.setMsg(headers);
        return j;

    }

    /**
     * 计划下达前校验来源是否为空
     *
     * @author zhousuxia
     * @version 2018年5月21日
     * @see PlanController
     * @sin
     */
    @RequestMapping(params = "checkOriginIsNullBeforeSub")
    @ResponseBody
    public FeignJson checkOriginIsNullBeforeSub(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        String useObjectType = request.getParameter("useObjectType");
        FeignJson j = planService.checkOriginIsNullBeforeSub(ids,useObjectType);
        return j;
    }
    
    /**
     * 判断是否是父子节点一起发布的
     *
     * @author wqb
     * @version 2019年11月21日 16:24:24
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "checkisParentChildAllPublish")
    @ResponseBody
    public FeignJson checkisParentChildAllPublish(HttpServletRequest request) {
        String id = request.getParameter("id");
        FeignJson j = planService.checkisParentChildAllPublish(id);
        return j;
    }
    

    /**
     * 判断父节点是否是拟制中
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "pdAssign")
    @ResponseBody
    public FeignJson pdAssign(PlanDto plan, HttpServletRequest req) {
        String id = req.getParameter("id");
        FeignJson j = planService.pdAssign(plan,id);
        if (!j.isSuccess()) {
            Map<String,Object> map = j.getAttributes();
            if (!CommonUtil.isEmpty(map) && map.get("planSonName") != null) {
                String planSonName = map.get("planSonName").toString();
                req.getSession().setAttribute("planSonName", planSonName);
            }
        }
        return j;
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "getPlanBizCurrentById")
    @ResponseBody
    public AjaxJson getPlanBizCurrentById(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            PlanDto plan = planService.getPlanEntity(request.getParameter("id"));
            PlanDto parentPlan =  planService.getPlanEntity(plan.getParentPlanId());
            if("EDITING".equals(parentPlan.getBizCurrent())){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }
    
    /**获取父计划的状态
     * @param request
     * @return 
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getPlanBizCurrentAndOwnerInfoById")
    @ResponseBody
    public AjaxJson getPlanBizCurrentAndOwnerInfoById(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            PlanDto plan = planService.getPlanEntity(request.getParameter("id"));
            PlanDto parentPlan =  planService.getPlanEntity(plan.getParentPlanId());
            if(!CommonUtil.isEmpty(parentPlan)) {
                j.setObj(parentPlan.getBizCurrent()+"-"+parentPlan.getOwner());
            }else {
                j.setSuccess(false);
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    /**
     * 删除项目计划
     *
     * @param ids
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "";
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteSuccess");
        try {
            List<PlanDto> list = new ArrayList<PlanDto>();
            for (String id : ids.split(",")) {
                PlanDto plan = planService.getPlanEntity(id);
                // 将是否删除的逻辑标识设为删除（"0"）
                plan.setAvaliable("0");
                list.add(plan);

            }
            planService.deletePlans(list);

            for(PlanDto p : list){
                PlanDto pla = new PlanDto();
                pla.setId(p.getParentPlanId());
                long count = planService.judgePlanStatusByPlan(pla);
                long editingCount = planService.getEditingPlanCount(pla);
                if(count == 0 && editingCount == 0){
                    PlanDto planDb = planService.getPlanEntity(p.getParentPlanId());
                    planDb.setOpContent(PlanConstants.PLAN_DELETE_SPLIT);
                    planDb.setFlowResolveXml("");
                    CommonInitUtil.initGLVDataForUpdate(planDb);
                    planService.updatePlan(planDb);
                }

            }

            log.info(message, I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteSuccess"), "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteFail");
            j.setSuccess(false);
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 判断能否有导入覆盖
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkBeforeImport")
    @ResponseBody
    public AjaxJson checkBeforeImport(PlanDto plan, HttpServletRequest request, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.doSuccess");
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            plan.setProjectId(projectId);
        }
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        if (CommonUtil.isEmpty(list)) {
            msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.noData");
        }
        else {
            for (PlanDto p : list) {
                if (!PlanConstants.PLAN_EDITING.equals(p.getBizCurrent())) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.cannotImportOne");
                    break;
                }
                if (!StringUtils.isEmpty(p.getFlowStatus())
                        && !PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(p.getFlowStatus())) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.cannotImportTwo");
                    break;
                }
            }
        }

        j.setMsg(msg);
        return j;
    }

    /**
     * "查看任务信息"选项,计划状态判断
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkPlanStatus")
    @ResponseBody
    public AjaxJson checkPlanStatus(HttpServletRequest request, HttpSession session) {
        AjaxJson j = new AjaxJson();
        String message = "";
        TSUserDto curruntUser = UserUtil.getInstance().getUser();
        String id = request.getParameter("id");
        PlanDto plan = planService.getPlanEntity(id);
        session.setAttribute("plan_", plan);
        j.setSuccess(true);
        try {

            String message1 = "";
            // 当前用户为计划的负责人
            if (plan != null) {
                if (curruntUser.getId().equals(plan.getOwner())) {
                    message1 = "true";
                }
                else {
                    message1 = "false";
                }
            }
            session.setAttribute("isOwner", message1);
        }
        catch (Exception e) {
            j.setSuccess(false);
            log.error(message, e, "", plan.getId().toString());
            Object[] params = new Object[] {message, plan.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            j.setMsg(message);
            j.setObj(plan.getPlanName());
            return j;
        }
    }


    /**
     * 导入计划\子计划页面跳转
     *
     * @param parentPlanId
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goImportPlan")
    public ModelAndView goImportPlan(String parentPlanId, HttpServletRequest req) {
        String fmsBpmPath = "";
        List<ServiceInstance> inss = discoveryClient.getInstances("IDS-PM-SERVICE-96");
        if (!CommonUtil.isEmpty(inss)) {
            EurekaDiscoveryClient.EurekaServiceInstance serviceIns = (EurekaDiscoveryClient.EurekaServiceInstance) inss.get(0);
            fmsBpmPath = serviceIns.getInstanceInfo().getInstanceId().toString();
        }
        req.setAttribute("serviceName",fmsBpmPath);
        req.setAttribute("userId",UserUtil.getCurrentUser().getId());


        String type = req.getParameter("type");
        String currentUserId = req.getParameter("currentUserId");
        PlanDto plan = new PlanDto();
        // 如果是下方导入计划
        if (StringUtils.isNotEmpty(type)) {
            req.setAttribute("type", type);
            plan.setId(req.getParameter("planId"));
            plan.setParentPlanId(((PlanDto)planService.getPlanEntity(
                    req.getParameter("planId"))).getParentPlanId());
        }
        // 根据parentPlanId是否为空判断是导入计划还是导入子计划
        if (StringUtils.isNotEmpty(parentPlanId)) {
            req.setAttribute("parentPlanId", parentPlanId);
            plan.setParentPlanId(parentPlanId);
        }

        plan.setProjectId(req.getParameter("projectId"));
        req.setAttribute("currentUserId", currentUserId);
        req.setAttribute("plan_", plan);

        return new ModelAndView("com/glaway/ids/pm/project/plan/plan-import");
    }


    /**
     * 导入计划/子计划
     *
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "importPlan")
    @ResponseBody
    public AjaxJson importPlan(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importSuccess");
        String projectId = request.getParameter("projectId");
        String currentUserId = request.getParameter("currentUserId");
        try {

            PlanDto plan = new PlanDto();
            if (StringUtils.isNotEmpty(projectId)) {
                plan.setProjectId(projectId);
            }
            if (StringUtils.isNotEmpty(request.getParameter("type"))) {
                plan.setId(request.getParameter("realPlanId"));
                plan.setParentPlanId(request.getParameter("planId"));
            }
            else {
                plan.setId(request.getParameter("planId"));
            }
            if (StringUtils.isNotEmpty(request.getParameter("planSource"))) {
                plan.setPlanSource(request.getParameter("planSource"));
            }
            PlanDto p = new PlanDto();
            if (StringUtils.isNotEmpty(plan.getId())) {
                p = planService.getPlanEntity(plan.getId());
            }
            if (p != null) {
                plan.setStoreyNo(p.getStoreyNo());
            }
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();
            //List<List<String>> preposePlanIdList = new ArrayList<List<String>>();
            Map<String, List<String>> preposePlanIdMap = new HashMap<>();
            List<List<Map<String, Object>>> taskMapList = new ArrayList<List<Map<String, Object>>>();
            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                CommonsMultipartFile cf = (CommonsMultipartFile)mf;// 转成File传输
                InputStream inputstream = cf.getInputStream();
                MppDirector mppDirector = new MppDirector();
                List<Task> taskList = mppDirector.construct(inputstream);
                preposePlanIdMap = getPreposePlanIdList(taskList);
                taskMapList.add(getDetailInfo(taskList));
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("mapKeys",mapKeys);
            map.put("planDto",plan);
            map.put("taskList",taskMapList);
            map.put("preposePlanIdMap",preposePlanIdMap);
            String type = request.getParameter("type");
            FeignJson feignJson = planService.inputForWork(map, projectId, type, currentUserId);
            log.info("项目计划"+message);
            if (!feignJson.isSuccess()){
                message = feignJson.getMsg();
                j.setSuccess(false);
                log.info("项目计划导入失败");
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = e.getMessage();
            log.info("项目计划导入失败");
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /*public List<String> getPreposePlanIdList(List<Task> taskList) {
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
    }*/

    public Map<String, List<String>> getPreposePlanIdList(List<Task> taskList) {
        Map<String, List<String>> map = new HashMap<>();
        for (Task task : taskList) {
            if (task.getID() != 0 && StringUtils.isNotEmpty(task.getName())
                    && !CommonUtil.isEmpty(task.getPredecessors())) {
                List<Relation> predList = task.getPredecessors();
                List<String> ids = new ArrayList<>();
                for (Relation relation : predList) {
                    String id = relation.getTargetTask().getID().toString();
                    if (StringUtils.isNotEmpty(id)) {
                        ids.add(id);
                    }
                }
                if (!CommonUtil.isEmpty(ids)) {
                    map.put(task.getID().toString(),ids);
                }
            }
        }
        return map;
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
                if (task.getParentTask()!=null&&StringUtils.isNotEmpty(task.getParentTask().getID().toString())){
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
                }else{
                    throw new GWException("任务类型不能为空");
                }

                list.add(map);
            }
        }
        return list;
    }



    /**
     * 项目计划编辑页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @throws ParseException
     * @see
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(PlanDto plan, HttpServletRequest req)
            throws ParseException {
        Date parentStartTime = new Date();
        Date parentEndTime = new Date();

        plan = planService.getPlanEntity(plan.getId());
        if(!CommonUtil.isEmpty(plan.getOwner())){
            TSUserDto userDto = userService.getUserByUserId(plan.getOwner());
            plan.setOwnerInfo(userDto);
        }
        List<TSUserDto> userList = new ArrayList<>();
        if (StringUtils.isNotEmpty(plan.getProjectId())) {
            Project project = projectService.getProjectEntity(plan.getProjectId());
            parentStartTime = project.getStartProjectTime();
            parentEndTime = project.getEndProjectTime();
            List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
            for (int i = 0; i < users.size(); i++ ) {
                TSUserDto o = userService.getUserByUserId(users.get(i).getId());
                userList.add(o);
            }
        }
//        String parentStartTimeStr = DateUtil.getStringFromDate(parentStartTime, DateUtil.YYYY_MM_DD);
//        String parentEndTimeStr = DateUtil.getStringFromDate(parentEndTime, DateUtil.YYYY_MM_DD);

        String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
        String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");

        // 根据parentPlanId是否为空判断是创建计划还是创建子计划
        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            parentStartTime = parent.getPlanStartTime();
            parentEndTime = parent.getPlanEndTime();
            req.setAttribute("planStartTimeRm", parentStartTimeStr);
            req.setAttribute("planEndTimeRm", parentEndTimeStr);
        }

        // userlist重新排序
//        List<TSUserDto> userListTemp = userService.queryUserList();
        Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");
        List<TSUserDto> userListTemp = userService.getAllUsers();
        if (plan.getOwner() != null) {
//            TSUserDto o = userService.getEntity(TSUser.class, plan.getOwner());
            TSUserDto o  = userService.getUserByUserId(plan.getOwner());
            if (o != null) {
                List<TSDepartDto> departDtoList = departMap.get(plan.getOwner());
                String depName = "";
                if (!CommonUtil.isEmpty(departDtoList)) {
                    for(TSDepartDto dep : departDtoList){
                        if(CommonUtil.isEmpty(depName)){
                            depName = dep.getDepartname();
                        }else {
                            depName = depName +","+ dep.getDepartname();
                        }
                    }
                    plan.setOwnerDept(depName);
                }
                userList.add(o);
                for (int i = 0; i < userListTemp.size(); i++ ) {
                    if (!o.getId().equals(userListTemp.get(i).getId())) {
                        userList.add(userListTemp.get(i));
                    }
                }
            }
        }
        else {
            userList.addAll(userListTemp);
        }

        JSONObject obj = null;
        // 责任人对应的责任部门list取值

        JSONArray deptList = new JSONArray();
        if (!CommonUtil.isEmpty(userList)) {
            for (TSUserDto user : userList) {
                obj = new JSONObject();
                obj.put("userId", user.getId());
                // 部门为空判断
                List<TSDepartDto> departList = departMap.get(user.getId());
                String departName = "";
                if (!CommonUtil.isEmpty(departList)) {
                    for(TSDepartDto departDto : departList){
                        if(CommonUtil.isEmpty(departName)){
                            departName = departDto.getDepartname();
                        }else{
                            departName = departName+","+departDto.getDepartname();
                        }
                    }
                    obj.put("departname", departName);
                }
                else {
                    obj.put("departname", "");
                }

                deptList.add(obj);
            }
        }


        List<BusinessConfig> planLevelList = new ArrayList<BusinessConfig>();
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
//        List<BusinessConfig> planLevelListTemp = businessConfigService.searchUseableBusinessConfigs(planLevel);

        List<BusinessConfig> planLevelListTemp = businessConfigService.searchUseableBusinessConfigs(planLevel);
  //      List<BusinessConfig> planLevelListTemp = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        // 计划等级重新排序
        if (plan.getPlanLevel() != null) {
            BusinessConfig b = businessConfigService.getBusinessConfig(plan.getPlanLevel());
            if (b != null) {
                planLevelList.add(b);
                for (int i = 0; i < planLevelListTemp.size(); i++ ) {
                    if (!b.getId().equals(planLevelListTemp.get(i).getId())) {
                        planLevelList.add(planLevelListTemp.get(i));
                    }
                }
            }
        }

        // 根据parentPlanId是否为空判断是创建计划还是创建子计划
        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            parentStartTime = parent.getPlanStartTime();
            parentEndTime = parent.getPlanEndTime();
            req.setAttribute("planStartTimeRm", parentStartTimeStr);
            req.setAttribute("planEndTimeRm", parentEndTimeStr);
        }

        // 如果父级开始时间晚于当前时间，则计划开始时间取父级开始时间
        if (parentStartTime.getTime() > (new Date()).getTime()) {
            req.setAttribute("planStartTimeRm", parentStartTimeStr);
        }
        // 如果父级开始时间早于等于当前时间，则计划开始时间取当前时间
        else {
            req.setAttribute("planStartTimeRm", parentStartTimeStr);
        }
        Project parentProject = projectService.getProjectEntity(plan.getProjectId());
        if (StringUtils.isNotEmpty(parentProject.getProjectTimeType())) {
            if (ProjectConstants.WORKDAY.equals(parentProject.getProjectTimeType())) {
                Date date = (Date)plan.getPlanStartTime().clone();
                req.setAttribute("planEndTimeRm", DateUtil.dateToString(DateUtil.nextWorkDay(date, 0),"yyyy-MM-dd"));
            }
            else if (ProjectConstants.COMPANYDAY.equals(parentProject.getProjectTimeType())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",DateUtil.dateToString(plan.getPlanStartTime(),"yyyy-MM-dd"));
                params.put("days",0);
                Date date = (Date)plan.getPlanStartTime().clone();
                req.setAttribute("planEndTimeRm", DateUtil.dateToString(calendarService.getNextWorkingDay(ResourceUtil.getApplicationInformation().getAppKey(),params),"yyyy-MM-dd"));
            }
            else {
                Date date = (Date)plan.getPlanStartTime().clone();
                req.setAttribute("planEndTimeRm", DateUtil.dateToString(TimeUtil.getExtraDate(date, 0),"yyyy-MM-dd"));
            }
        }
        else {
            req.setAttribute("planEndTimeRm", DateUtil.dateToString(TimeUtil.getExtraDate(plan.getPlanStartTime(), 0),"yyyy-MM-dd"));
        }

        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            Date date = (Date)plan.getPlanStartTime().clone();
            req.setAttribute("planStartTimeRm", parent.getPlanStartTime());
            req.setAttribute("planEndTimeRm", DateUtil.dateToString(DateUtil.nextWorkDay(date, 0),"yyyy-MM-dd"));
        }

        if ("true".equals(plan.getMilestone())) {
            plan.setMilestoneName("是");
        }
        else {
            plan.setMilestoneName("否");
        }

        // 获取计划的生命周期
        // 获取计划的生命周期
               /* PlanDto statusP = new PlanDto();
                planService.initBusinessObject(statusP);*/
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
        for (LifeCycleStatus status : statusList) {
            if (status.getName().equals(plan.getBizCurrent())) {
                plan.setStatus(status.getTitle());
                break;
            }
        }

//        PlanDto statusP = new PlanDto();
//        planService.initBusinessObject(statusP);
//        List<LifeCycleStatus> statusList = statusP.getPolicy().getLifeCycleStatusList();
//
//        if (!CommonUtil.isEmpty(statusList)) {
//            for (LifeCycleStatus status : statusList) {
//                if (status.getName().equals(plan.getBizCurrent())) {
//                    plan.setStatus(status.getTitle());
//                    break;
//                }
//            }
//        }

        List<PreposePlanDto> preposePlanList = planRemoteFeignServiceI.getPreposePlansByPlanId(plan);
        // 组装前置数据
        StringBuffer preposeSb = new StringBuffer();
        StringBuffer preposeIds = new StringBuffer();
        String preposeEndTime = "";
        int k = 0;
        for (PreposePlanDto preposePlan : preposePlanList) {
            if (k > 0) {
                preposeSb.append(",");
                preposeIds.append(",");
            }
            PlanDto preposePlanDto = planService.getPlanEntity(preposePlan.getPreposePlanId());
            preposePlan.setPreposePlanInfo(preposePlanDto);
            if (preposePlan.getPreposePlanInfo() == null) {
                continue;
            }
            String endTime = "";
            if (preposePlan.getPreposePlanInfo().getPlanEndTime() != null) {
                endTime = DateUtil.getStringFromDate(
                        preposePlan.getPreposePlanInfo().getPlanEndTime(), DateUtil.YYYY_MM_DD);
                if (StringUtils.isEmpty(preposeEndTime)) {
                    if (StringUtils.isNotEmpty(endTime)) {
                        preposeEndTime = endTime;
                    }
                }
                else {
                    if (StringUtils.isNotEmpty(endTime)) {
                        Date endTimeDate = DateUtil.getDateFromString(endTime, DateUtil.YYYY_MM_DD);
                        Date preposeEndTimeDate = DateUtil.getDateFromString(preposeEndTime,
                                DateUtil.YYYY_MM_DD);
                        if (endTimeDate.getTime() > preposeEndTimeDate.getTime()) {
                            preposeEndTime = endTime;
                        }
                    }
                }
            }

            preposeSb.append(preposePlan.getPreposePlanInfo().getPlanName());
            preposeIds.append(preposePlan.getPreposePlanInfo().getId());
            k++ ;
        }
        plan.setPreposePlans(preposeSb.toString());
        plan.setPreposeIds(preposeIds.toString());

        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("userList2", jonStr2);
        req.setAttribute("planLevelList", jonStr3);
        req.setAttribute("userList", userList);
        if (plan.getOwnerInfo() != null) {
            req.setAttribute("ownerShow", plan.getOwnerInfo().getRealName() + "-"
                    + plan.getOwnerInfo().getUserName());
        }
        else {
            req.setAttribute("ownerShow", "");
        }

        if (!CommonUtil.isEmpty(plan.getPlanLevel())) {
            if(!CommonUtil.isEmpty(planLevelList)){
                for(BusinessConfig bc : planLevelList){
                    if(bc.getId().equals(plan.getPlanLevel())){
                        req.setAttribute("planLevelShow", bc.getName());
                        req.setAttribute("planLevelId", plan.getPlanLevel());
                        break;
                    }
                }
            }

        }
        else {
            req.setAttribute("planLevelShow", "");
        }

        req.setAttribute("parentStartTime", parentStartTimeStr);
        req.setAttribute("parentEndTime", parentEndTimeStr);
        req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
        // req.setAttribute("planLevelList", planLevelList);
        req.setAttribute("disabled", "true");
        req.setAttribute("plan_", plan);
        req.setAttribute("useObjectId", plan.getId());
        req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
        req.setAttribute("preposeEndTime", preposeEndTime);
        req.setAttribute("isUpdate", "true");

        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(plan.getProjectId());
        req.setAttribute("doc", vo);
        req.setAttribute("yanfa", "yanfa");

        redisService.setToRedis("INPUTSLIST", plan.getId(), "");

        return new ModelAndView("com/glaway/ids/pm/project/plan/plan-add");
    }

    /**
     * 项目计划单条下达选人页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanOneEdit")
    public ModelAndView goAssignPlanOneEdit(PlanDto plan, HttpServletRequest req) {
        String id = req.getParameter("id");
        String taskId = (String)req.getSession().getAttribute("taskIdSave");
        Map<String, Object> variables = workFlowFacade.getWorkFlowCommonService().getVariablesByTaskId(
                taskId);
        String leader = (String)variables.get("leader");
        String deptLeader = (String)variables.get("deptLeader");
        TSUserDto t = userService.getUserByUserName(leader);
        TSUserDto t2 = userService.getUserByUserName(deptLeader);
        req.setAttribute("leaderId", t.getId());
        req.setAttribute("deptLeaderId", t2.getId());
        req.setAttribute("leader", leader);
        req.setAttribute("deptLeader", deptLeader);
        req.setAttribute("assignId", id);
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantOneEdit");
    }

    /**
     * 项目计划单条下达选人页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanOne")
    public ModelAndView goAssignPlanOnTree(PlanDto plan, HttpServletRequest req) {
        String assignId = req.getParameter("id");
        String userId = ResourceUtil.getCurrentUser().getId();
        req.setAttribute("assignId", assignId);
        String leader = "";
        String leaderId = "";
        String deptLeader = "";
        String deptLeaderId = "";
        FeignJson j = planService.goAssignPlanOne(assignId,userId);
        if (j.isSuccess()) {
            Map<String,Object> map = j.getAttributes();
            leader = map.get("leader") == null ? "" : map.get("leader").toString();
            leaderId = map.get("leaderId") == null ? "" : map.get("leaderId").toString();
            deptLeader = map.get("deptLeader") == null ? "" : map.get("deptLeader").toString();
            deptLeaderId = map.get("deptLeaderId") == null ? "" : map.get("deptLeaderId").toString();
        }
        req.setAttribute("leaderId", leaderId);
        req.setAttribute("deptLeaderId", deptLeaderId);
        req.setAttribute("leader", leader);
        req.setAttribute("deptLeader", deptLeader);
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantOne");
    }

    /**
     * 启动并提交计划下达审批流
     *
     * @param ids
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "startAssignProcess")
    @ResponseBody
    public FeignJson startAssignProcess(String ids, HttpServletRequest request) {
        String kddProductType = request.getParameter("kddProductType");
        String assignId = request.getParameter("assignId");
        String userId = ResourceUtil.getCurrentUser().getId();
        String leader = request.getParameter("leader");
        String deptLeader = request.getParameter("deptLeader");
        String assignType = request.getParameter("assignType");
        try {
            leader = URLDecoder.decode(leader, "UTF-8");
            deptLeader = URLDecoder.decode(deptLeader, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,String> params = new HashMap<String, String>();
        params.put("ids",ids);
        params.put("kddProductType",kddProductType);
        params.put("assignId",assignId);
        params.put("userId",userId);
        params.put("leader",leader);
        params.put("deptLeader",deptLeader);
        params.put("assignType",assignType);
        FeignJson j = planService.startAssignProcess(params);
        return j;
    }

    /**
     * 默认进入的首页面
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    @ResponseBody
    public AjaxJson searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planNumber = request.getParameter("planNumber");
        String isDelay = request.getParameter("isDelay");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planRevocationOperationCode = request.getParameter("planRevocationOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        String projectId = request.getParameter("projectId");

        String planId = request.getParameter("planId");

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planNumber)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(isDelay)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(isDelay.split(","));
            vo.setCondition("in");
            vo.setValue(isDelay);
            vo.setKey("Plan.isDelay");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }

//        FeignJson json = new FeignJson();
        Map<String, Object> map = new HashMap<>();
        map.put("conditionList",conditionList);
        map.put("projectId",projectId);
        map.put("userName",userName);
        map.put("progressRate",progressRate);
        map.put("workTime",workTime);
//        json.setAttributes(map);

        PageList pageList = planService.queryEntity(map);
        List<LinkedHashMap> linkedHashMapList = pageList.getResultList();
        List<PlanDto> planList = new ArrayList<>();
        linkedHashMapList.stream().forEach(it -> {
            ObjectMapper mapper = new ObjectMapper();
            PlanDto planDto = mapper.convertValue(it, PlanDto.class);
            planList.add(planDto);
        });


        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(planId)) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto plan = new PlanDto();
        plan.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(plan);
        Map<String, PlanDto> listMap = new HashMap<>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<>();
        for (PlanDto p : planList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }


        String appKey = ResourceUtil.getApplicationInformation().getAppKey();

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        FeignJson feignJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        boolean isProjectManger = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanRevocationOperationCode(planRevocationOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        PlanDto pl = new PlanDto();
        planService.initPlan(pl);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
//        List<LifeCycleStatus> statusList = pl.getPolicy().getLifeCycleStatusList();
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(true);
        List<JSONObject> rootList = changePlansToJSONObjects(tempList, parameVo, project,
                statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }

    /**
     * 计划处理反馈时的判断
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "getPlanFeedbackJudge")
    @ResponseBody
    public void getPlanFeedbackJudge(PlanDto plan, HttpServletRequest request,
                                     HttpServletResponse response) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanFeedbackJudgeSuccess");
        try {
            Integer planFeedbackFlag = getplanFeedbackFlag(plan.getId());
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            String json = gson.toJson(planFeedbackFlag);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanFeedbackJudgeFail");
            log.error(message, e, plan.getId(), "");
            Object[] params = new Object[] {message,
                PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
    }

    public Integer getplanFeedbackFlag(String planId) {
        PlanDto newPlan = new PlanDto();
        newPlan.setParentPlanId(planId);
        //查询未废弃的子计划
        List<PlanDto> list = planService.queryPlansExceptInvalid(newPlan);
        long planCount = list.size();

        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(planId);
        List<DeliverablesInfoDto> deliverablesInfos = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        long deliverablesCount = deliverablesInfos.size();
        Integer planFeedbackFlag = null;
        // 如果是叶子节点的底层任务并且有交付项
        if (planCount == 0 && deliverablesCount > 0) {
            boolean isPay = false;
            List<ProjDocRelationDto> allDocRelationList = projLibService.getAllDocRelationList();
            for (DeliverablesInfoDto d : deliverablesInfos) {
                List<ProjDocRelationDto> projDocRelations = new ArrayList<ProjDocRelationDto>();
                for (ProjDocRelationDto relation : allDocRelationList) {
                    if (d.getId().equals(relation.getQuoteId())) {
                        projDocRelations.add(relation);
                    }
                }
                if (!CommonUtil.isEmpty(projDocRelations)) {
                    isPay = true;
                    break;
                }
            }
            // 交付项挂接了文档
            if (isPay) {
                planFeedbackFlag = 4;
            }
            else {
                planFeedbackFlag = 0;
            }
        }
        else if (planCount == 0 && deliverablesCount == 0) {// 如果是叶子节点的底层任务并且无交付物
            planFeedbackFlag = 1;
        }
        else if (planCount > 0) {
            Boolean plansFinishFlag = true;
            PlanDto parent = planService.getPlanEntity(planId);
            if (!CommonUtil.isEmpty(parent) && !CommonUtil.isEmpty(parent.getProjectId())) {
                PlanDto condition = new PlanDto();
                condition.setProjectId(parent.getProjectId());
                List<PlanDto> projectPlanList = planService.queryPlansExceptInvalid(condition);
                plansFinishFlag = isChildPlanStatusFinish(newPlan, plansFinishFlag,
                        projectPlanList);
                if (plansFinishFlag) { // 如果不是叶子节点的底层任务并且下方叶子都是已完工
                    planFeedbackFlag = 2;
                }
                else { // 如果不是叶子节点的底层任务并且下方叶子有不是完工中的状态
                    planFeedbackFlag = 3;
                }
            }
        }

        return planFeedbackFlag;
    }

    /**
     * Description: <br>
     * 判断下方叶子是否都已完工,有一个没有完工都不可以<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @return
     * @see
     */
    private Boolean isChildPlanStatusFinish(PlanDto plan, Boolean plansFinishFlag,
                                            List<PlanDto> projectPlanList) {
        List<PlanDto> planList = new ArrayList<>();
        for (PlanDto p : projectPlanList) {
            if (plan.getParentPlanId().equals(p.getParentPlanId())) {
                planList.add(p);
            }
        }
        for (PlanDto planDb : planList) {
            if (!PlanConstants.PLAN_FINISH.equals(planDb.getBizCurrent())
                && !PlanConstants.PLAN_INVALID.equals(planDb.getBizCurrent())) {
                plansFinishFlag = false;
                return plansFinishFlag;
            }
            PlanDto newPlan = new PlanDto();
            newPlan.setParentPlanId(planDb.getId());
            isChildPlanStatusFinish(newPlan, plansFinishFlag, projectPlanList);
        }
        return plansFinishFlag;
    }

    /**
     * 检查子计划是否全部完工
     * @param plan
     * @return
     */
    @RequestMapping(params = "checkChildPlanStatusFinish")
    @ResponseBody
    public FeignJson checkChildPlanStatusFinish(PlanDto plan) {
        FeignJson j = new FeignJson();
        Boolean plansFinishFlag = true;
        //查询未废弃的子计划
        PlanDto newPlan = new PlanDto();
        newPlan.setParentPlanId(plan.getId());
        List<PlanDto> list = planService.queryPlansExceptInvalid(newPlan);
        if (list.size() > 0) {
            PlanDto parent = planService.getPlanEntity(plan.getId());
            if (!CommonUtil.isEmpty(parent) && !CommonUtil.isEmpty(parent.getProjectId())) {
                PlanDto condition = new PlanDto();
                condition.setProjectId(parent.getProjectId());
                List<PlanDto> projectPlanList = planService.queryPlansExceptInvalid(condition);
                plansFinishFlag = isChildPlanStatusFinish(newPlan, plansFinishFlag,
                        projectPlanList);
            }
        }
        j.setSuccess(plansFinishFlag);
        return j;
    }

    /**
     * 查询计划类别
     *
     * @param request
     * @param response
     */
    /*@RequestMapping(params = "getTaskTypes")
    @ResponseBody
    public void getTaskTypes(HttpServletRequest request, HttpServletResponse response) {
        String parentPlanId = (String)request.getParameter("parentPlanId");
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        String userId = UserUtil.getInstance().getUser().getId();
        String projectId = request.getParameter("projectId");
        FeignJson teamIdJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = teamIdJson.getObj().toString();
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto parent = planService.getPlanEntity(parentPlanId);
            if (parent != null) {
                if (PlanConstants.PLAN_TYPE_WBS.equals(parent.getTaskType())) {
                    if (isPmo || isProjectManger) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", "wbs");
                        obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                        jsonList.add(obj);
                        JSONObject obj2 = new JSONObject();
                        obj2.put("id", "task");
                        obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                        jsonList.add(obj2);
                    }
                    else {
                        JSONObject obj = new JSONObject();
                        obj.put("id", "task");
                        obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                        jsonList.add(obj);
                        JSONObject obj2 = new JSONObject();
                        obj2.put("id", "wbs");
                        obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                        jsonList.add(obj2);
                    }
                }
                else {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "task");
                    obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj);
                }
            }
            else {
                if (isPmo || isProjectManger) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "wbs");
                    obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "task");
                    obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj2);
                }
                else {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "task");
                    obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "wbs");
                    obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj2);
                }
            }
        }
        else {
            if (isPmo || isProjectManger) {
                JSONObject obj = new JSONObject();
                obj.put("id", "wbs");
                obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "task");
                obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj2);
            }
            else {
                JSONObject obj = new JSONObject();
                obj.put("id", "task");
                obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "wbs");
                obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj2);
            }
        }
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 查询计划类别
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTaskTypes")
    @ResponseBody
    public void getTaskTypes(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        String parentPlanId = request.getParameter("parentPlanId");
        PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
        //任务4937
        if(CommonUtil.isEmpty(parentPlan)){
            if("4028f00c6cefbdc9016cefcd30c00007".equals(activityId)){    //研发类
                JSONObject obj = new JSONObject();
                obj.put("id", "wbs");
                obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "task");
                obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj2);
            }else{
                JSONObject obj = new JSONObject();
                obj.put("id", "task");
                obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "wbs");
                obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj2);
            }
        }else{
            if(PlanConstants.PLAN_TYPE_TASK.equals(parentPlan.getTaskType())){
                JSONObject obj = new JSONObject();
                obj.put("id", "task");
                obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj);
            }else{
                if("4028f00c6cefbdc9016cefcd30c00007".equals(activityId)){
                    JSONObject obj = new JSONObject();
                    obj.put("id", "wbs");
                    obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "task");
                    obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj2);
                }else{
                    JSONObject obj = new JSONObject();
                    obj.put("id", "task");
                    obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "wbs");
                    obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj2);
                }

            }
        }




      /*  String userId = UserUtil.getInstance().getUser().getId();
        String projectId = request.getParameter("projectId");
        FeignJson teamIdJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = teamIdJson.getObj().toString();
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto parent = planService.getPlanEntity(parentPlanId);
            if (parent != null) {
                if (PlanConstants.PLAN_TYPE_WBS.equals(parent.getTaskType())) {
                    if (isPmo || isProjectManger) {
                        JSONObject obj = new JSONObject();
                        obj.put("id", "wbs");
                        obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                        jsonList.add(obj);
                        JSONObject obj2 = new JSONObject();
                        obj2.put("id", "task");
                        obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                        jsonList.add(obj2);
                    }
                    else {
                        JSONObject obj = new JSONObject();
                        obj.put("id", "task");
                        obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                        jsonList.add(obj);
                        JSONObject obj2 = new JSONObject();
                        obj2.put("id", "wbs");
                        obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                        jsonList.add(obj2);
                    }
                }
                else {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "task");
                    obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj);
                }
            }
            else {
                if (isPmo || isProjectManger) {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "wbs");
                    obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "task");
                    obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj2);
                }
                else {
                    JSONObject obj = new JSONObject();
                    obj.put("id", "task");
                    obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                    jsonList.add(obj);
                    JSONObject obj2 = new JSONObject();
                    obj2.put("id", "wbs");
                    obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                    jsonList.add(obj2);
                }
            }
        }
        else {
            if (isPmo || isProjectManger) {
                JSONObject obj = new JSONObject();
                obj.put("id", "wbs");
                obj.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "task");
                obj2.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj2);
            }
            else {
                JSONObject obj = new JSONObject();
                obj.put("id", "task");
                obj.put("name", PlanConstants.PLAN_TYPE_TASK);
                jsonList.add(obj);
                JSONObject obj2 = new JSONObject();
                obj2.put("id", "wbs");
                obj2.put("name", PlanConstants.PLAN_TYPE_WBS);
                jsonList.add(obj2);
            }
        }*/
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前计划操作
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "getPlanOption")
    @ResponseBody
    public void getPlanOption(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanOptionSuccess");
        try {
            PlanDto planDb = planService.getPlanEntity(plan.getId());

            Integer planOptionStatue = null;
            // 如果是叶子节点的底层任务并且有交付项
            if (PlanConstants.PLAN_LOGINFO_SPLIT.equals(planDb.getOpContent())) {
                PlanDto newPlan = new PlanDto();
                newPlan.setParentPlanId(plan.getId());
                List<PlanDto> list = planService.queryPlansExceptInvalid(newPlan);
                if(!CommonUtil.isEmpty(list)){
                    planOptionStatue = 0;
                }

            }
            else if (PlanConstants.PLAN_DELETE_SPLIT.equals(planDb.getOpContent())) {
                PlanDto newPlan = new PlanDto();
                newPlan.setParentPlanId(plan.getId());
                List<PlanDto> list = planService.queryPlansExceptInvalid(newPlan);
                if (CommonUtil.isEmpty(list)) {
                    planOptionStatue = 1;
                }
                else {
                    planOptionStatue = 0;
                }
            }
            else if (PlanConstants.PLAN_LOGINFO_FLOW_SPLIT.equals(planDb.getOpContent())) {
                planOptionStatue = 2;
            }
            else {
                // 查看子计划。如果有子计划，则为计划分解，因为流程分解必为流程分解的计划
                PlanDto planNew = new PlanDto();
                planNew.setParentPlanId(plan.getId());
                List<PlanDto> parentPlanList = planService.queryPlanList(planNew, 1, 10, false);
                if (!CommonUtil.isEmpty(parentPlanList)) {
                    planOptionStatue = 0;
                }
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            String json = gson.toJson(planOptionStatue);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanOptionFail");
            log.error(message, e, plan.getId(), "");
            Object[] params = new Object[] {message,
                PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
    }

    /**
     * 流程中项目计划下达查看页面
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanView")
    public ModelAndView goAssignPlanView(PlanDto plan, HttpServletRequest req) {
        String formId = req.getParameter("taskNumber");
        req.getSession().setAttribute("formId", formId);
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantViewTree");
    }

    /**
     * 计划前置选择页面初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "assignListViewTree")
    public void assignListViewTree(PlanDto plan, HttpServletRequest request,
                                   HttpServletResponse response) {
        // 获取所属项目
        String formId = (String)request.getSession().getAttribute("formId");
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson j = planService.assignListViewTree(plan,formId,userId);
        String resultJSON = "";
        if (j.isSuccess()) {
            resultJSON = j.getObj() == null ? "" : j.getObj().toString();
        }

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
     * 项目计划编辑页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goCheck")
    public ModelAndView goCheck(PlanDto plan, HttpServletRequest req) {
        String type = req.getParameter("type");
        req.setAttribute("type", type);
        if (StringUtils.isNotEmpty(plan.getId())) {
            String id = req.getParameter("id");
            if (CommonUtil.isEmpty(plan.getId())) {
                plan.setId(id);
            }
            plan = planService.getPlanEntity(plan.getId());
            req.setAttribute("plan_", plan);
            String projectId = plan.getProjectId();
            String teamId = req.getParameter("teamId");
            if (CommonUtil.isEmpty(teamId)) {
                FeignJson teamIdFj = projRoleService.getTeamIdByProjectId(projectId);
                if (teamIdFj.isSuccess()) {
                    teamId = teamIdFj.getObj() == null ? "" : teamIdFj.getObj().toString();
                }

            }
            req.setAttribute("teamId", teamId);

            req.setAttribute("nameStandardId", "");

            req.setAttribute("projectId", projectId);

            req.setAttribute("isOut", "2");
            List<OutwardExtensionDto> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"flowTempActiveCategory");
            if (!CommonUtil.isEmpty(outwardExtensionList)) {
                for (OutwardExtensionDto ext : outwardExtensionList) {
                    if (ext.getOptionValue().equals(plan.getTaskNameType())) {
                        req.setAttribute("isOut", "1");
                        NameStandardDto nameStandard = new NameStandardDto();
                        nameStandard.setStopFlag("启用");
                        nameStandard.setName(plan.getPlanName());
                        List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                        req.setAttribute("nameStandardId", list.get(0).getId());
                        break;
                    }
                }
            }
        }
        return new ModelAndView("com/glaway/ids/project/plan/plan-check");
    }

    /**
     * 下达流程驳回后页面
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanFlow")
    public ModelAndView goAssignPlanFlow(PlanDto plan, HttpServletRequest req) {
        String formId = req.getParameter("formId");
        String taskId = req.getParameter("taskId");
        req.getSession().setAttribute("taskIdSave", taskId);
        req.getSession().setAttribute("formId", formId);
        req.setAttribute("formId", formId);
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantFlowTree");
    }

    /**
     * 判断是否有子节点
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "pdChild")
    @ResponseBody
    public AjaxJson pdChild(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        PlanDto plan = planService.getPlanEntity(id);
        List<PlanDto> plans = this.planService.getPlanAllChildren(plan);
        if (plans != null && plans.size() > 1) {
            j.setSuccess(true);
        }
        else {
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 废弃
     *
     * @param req
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDiscard")
    @ResponseBody
    public AjaxJson doDiscard(String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.doDiscardSuccess");
        try {
            String userId = UserUtil.getCurrentUser().getId();
            planService.discardPlans(id,userId);
            log.info(message, I18nUtil.getValue("com.glaway.ids.pm.project.plan.doDiscardSuccess"),
                    "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.doDiscardFail");
            log.error(message, e, id, "");
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids:" + id};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * getChil
     *
     * @param plan
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "getPlanOptionNew")
    @ResponseBody
    public void getPlanOptionNew(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanOptionSuccess");
        try {
            PlanDto planDb = planService.getPlanEntity(plan.getId());
            Integer planOptionStatue = null;
            // 如果是叶子节点的底层任务并且有交付项
            if (PlanConstants.PLAN_LOGINFO_SPLIT.equals(planDb.getOpContent())) {
                PlanDto newPlan = new PlanDto();
                newPlan.setParentPlanId(plan.getId());
                newPlan.setAvaliable("1");
                List<PlanDto> list = planService.queryPlanList(newPlan, 1, 10, false);
                if(!CommonUtil.isEmpty(list)){
                    planOptionStatue = 0;
                }

            }
            else if (PlanConstants.PLAN_DELETE_SPLIT.equals(planDb.getOpContent())) {
                PlanDto newPlan = new PlanDto();
                newPlan.setParentPlanId(plan.getId());
                List<PlanDto> list = planService.queryPlansExceptInvalid(newPlan);
                if (CommonUtil.isEmpty(list)) {
                    planOptionStatue = 1;
                }
                else {
                    planOptionStatue = 0;
                }
            }
            else if (PlanConstants.PLAN_LOGINFO_FLOW_SPLIT.equals(planDb.getOpContent())) {
                planOptionStatue = 2;
            }
            else {
                // 查看子计划。如果有子计划，则为计划分解，因为流程分解必为流程分解的计划
                PlanDto planNew = new PlanDto();
                planNew.setParentPlanId(plan.getId());
                List<PlanDto> parentPlanList = planService.queryPlanList(planNew, 1, 10, false);
                if (!CommonUtil.isEmpty(parentPlanList)) {
                    planOptionStatue = 0;
                }
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            String json = gson.toJson(planOptionStatue);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getPlanOptionFail");
            log.error(message, e, plan.getId(), "");
            Object[] params = new Object[] {message,
                PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
    }

    /**
     * 获取计划的结束时间
     *
     * @param plan
     * @return
     */
    @RequestMapping(params = "getEndTimeByStartTimeAndWorkTime")
    @ResponseBody
    public AjaxJson getEndTimeByStartTimeAndWorkTime(PlanDto plan, HttpServletRequest request,
                                                     HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        Date planEndTime = null;
        try {
            Project project = projectService.getProjectEntity(plan.getProjectId());
            if (project != null) {
                if (StringUtils.isNotEmpty(project.getProjectTimeType())) {
                    plan.setStatus(project.getProjectTimeType());
                }
                else {
                    plan.setStatus(ProjectConstants.NATURALDAY);
                }
                // 工作日获取结束时间
                if (ProjectConstants.WORKDAY.equals(plan.getStatus())) {
                    Date date = (Date)plan.getPlanStartTime().clone();
                    Boolean a = CalendarParser.judgeDefaultWorkingDay(date);
                    if (a) {
                        // 若该计划为里程碑且工期为0
                        if ("true".equals(plan.getMilestone()) && "0".equals(plan.getWorkTime())) {
                            planEndTime = date;
                        }
                        else {
                            planEndTime = DateUtil.nextWorkDay(date,
                                Integer.valueOf(plan.getWorkTime()) - 1);
                        }
                    }
                    else {
                        Date date2 = DateUtil.nextWorkDay(date, 1);
                        Boolean b = CalendarParser.judgeDefaultWorkingDay(date2);
                        if (b) {
                            // 若该计划为里程碑且工期为0
                            if ("true".equals(plan.getMilestone())
                                && "0".equals(plan.getWorkTime())) {
                                planEndTime = date2;
                            }
                            else {
                                planEndTime = DateUtil.nextWorkDay(date2,
                                    Integer.valueOf(plan.getWorkTime()) - 1);
                            }
                        }
                        else {
                            Date date3 = DateUtil.nextWorkDay(date2, 1);
                            // 若该计划为里程碑且工期为0
                            if ("true".equals(plan.getMilestone())
                                && "0".equals(plan.getWorkTime())) {
                                planEndTime = date3;
                            }
                            else {
                                planEndTime = DateUtil.nextWorkDay(date3,
                                    Integer.valueOf(plan.getWorkTime()) - 1);
                            }
                        }
                    }

                }
                // 公司日获取结束时间
                else if (ProjectConstants.COMPANYDAY.equals(plan.getStatus())) {
                    // 若该计划为里程碑且工期为0
                    if ("true".equals(plan.getMilestone()) && "0".equals(plan.getWorkTime())) {
                        planEndTime = (Date)plan.getPlanStartTime().clone();
                    }
                    else {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("startDate",plan.getPlanStartTime());
                        params.put("days",Integer.valueOf(plan.getWorkTime()) - 1);
                        planEndTime = calendarService.getNextWorkingDay(appKey,params);
                    }
                }
                // 自然日获取结束时间
                else {
                    Date date = (Date)plan.getPlanStartTime().clone();
                    // 若该计划为里程碑且工期为0
                    if ("true".equals(plan.getMilestone()) && "0".equals(plan.getWorkTime())) {
                        planEndTime = date;
                    }
                    else {
                        planEndTime = DateUtil.nextDay(date,
                            Integer.valueOf(plan.getWorkTime()) - 1);
                    }
                }
                j.setObj(DateUtil.getStringFromDate(planEndTime, DateUtil.YYYY_MM_DD));
                j.setMsg(String.valueOf(planEndTime.getTime()));
                j.setSuccess(true);

            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getEndTimeByStartTimeAndWorkTimeFail");
                j.setMsg(message);
                j.setSuccess(false);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getEndTimeByStartTimeAndWorkTimeFailTwo");
            j.setMsg(message);
            j.setSuccess(false);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        return j;
    }

    /**
     * 获取计划的工期
     *
     * @param plan
     * @return
     */
    @RequestMapping(params = "getWorkTimeByStartTimeAndEndTime")
    @ResponseBody
    public AjaxJson getWorkTimeByStartTimeAndEndTime(PlanDto plan, HttpServletRequest request,
                                                     HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        long workTime = 0;
        try {

            Project project = projectService.getProjectEntity(plan.getProjectId());
            if (project != null) {
                if (StringUtils.isNotEmpty(project.getProjectTimeType())) {
                    plan.setStatus(project.getProjectTimeType());
                }
                else {
                    plan.setStatus(ProjectConstants.NATURALDAY);
                }
                // 若该计划开始结束时间相等，且为里程碑，且工期为0
                if ((plan.getPlanStartTime() == plan.getPlanEndTime())
                    && "true".equals(plan.getMilestone()) && "0".equals(plan.getWorkTime())) {
                    workTime = 0;
                }
                else if (ProjectConstants.WORKDAY.equals(plan.getStatus())) {
                    workTime = TimeUtil.getWorkDayNumber(plan.getPlanStartTime(),
                        plan.getPlanEndTime());
                }
                else if (ProjectConstants.COMPANYDAY.equals(plan.getStatus())) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("startDate",plan.getPlanStartTime());
                    params.put("endDate",plan.getPlanEndTime());
                    workTime = calendarService.getAllWorkingDay(appKey,params).size();

                }
                else {
                    Date start = (Date)plan.getPlanStartTime().clone();
                    Date end = (Date)plan.getPlanEndTime().clone();
                    workTime = DateUtil.dayDiff(start, end) + 1;
                }
                j.setObj(String.valueOf(workTime));
                j.setSuccess(true);

            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkTimeByStartTimeAndEndTimeFail");
                j.setSuccess(false);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkTimeByStartTimeAndEndTimeFail");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return j;
    }

    /**
     * 驳回到首节点再次提交工作流
     *
     * @return
     */
    @RequestMapping(params = "startPlanFlow")
    @ResponseBody
    public FeignJson startPlanFlow(String ids, HttpServletRequest req) {
        String formId = (String)req.getSession().getAttribute("formId");
        String taskId = (String)req.getSession().getAttribute("taskIdSave");
        String leader = req.getParameter("leader");
        String deptLeader = req.getParameter("deptLeader");
        String userId = ResourceUtil.getCurrentUser().getId();
        Map<String,String> map = new HashMap<String, String>();
        map.put("ids",ids);
        map.put("formId",formId);
        map.put("taskId",taskId);
        map.put("leader",leader);
        map.put("deptLeader",deptLeader);
        map.put("userId",userId);
        FeignJson j = planService.startPlanFlow(map);
        return j;
    }

    /**
     * 项目计划基本信息tab页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goBasicCheck")
    public ModelAndView goBasicCheck(PlanDto plan, HttpServletRequest req) {
        Map<String,Object> map  = planService.goBasicCheck(plan);

        //生命周期获取接口
        FeignJson j = planService.getPlanLifeStatus();
        String statusStr = "";
        if (j.isSuccess()) {
            statusStr = j.getObj() == null ? "" : j.getObj().toString();
        }
        List<LifeCycleStatus> statuses = JSON.parseArray(statusStr,LifeCycleStatus.class);
        req.setAttribute("statusList", statuses);
        req.setAttribute("ownerShow", map.get("ownerShow"));
        req.setAttribute("plan_", map.get("plan"));
        return new ModelAndView("com/glaway/ids/project/plan/planBasic-check");
    }

    /**
     * 撤消分解前先进行校验
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "judgeBeforeAbolishResolve")
    @ResponseBody
    public AjaxJson judgeBeforeAbolishResolve(PlanDto plan,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            long count = planService.judgePlanStatusByPlan(plan);
            if(count > 0){
                j.setSuccess(false);
                String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteChildPlanMiddle");
                j.setMsg(message);
            }else{
                j.setSuccess(true);
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    /**
     * 撤消分解删除拟制中的子计划
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "deleteChildPlan")
    @ResponseBody
    public AjaxJson deleteChildPlan(PlanDto plan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Boolean success = true;
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteChildPlanSuccess");
        try {
            String userId = UserUtil.getCurrentUser().getId();
            planFlowForworkService.deleteChildPlan(plan, userId);
            // 删除关联任务：
            //TODO..
//            // if(getRdFlowPluginValid()){
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String userId = UserUtil.getInstance().getUser().getId();
//            rdfConfigSupport.getDeleteChildInfo(plan.getId(), userId);
//            // }
        }
        catch (Exception e) {
            success = false;
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteChildPlanFail");
            log.error(message, e, plan.getId(), "");
            Object[] params = new Object[] {message,
                PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            j.setSuccess(success);
            return j;
        }

    }

    /**
     * 获取计划类型
     *
     * @return
     */
    @RequestMapping(params = "getTaskNameType")
    @ResponseBody
    public FeignJson getTaskNameType(HttpServletRequest request, HttpServletResponse response) {
        String planName = (String)request.getParameter("planName");
        FeignJson j = planService.getTaskNameType(planName);
        return j;

    }

    /**
     * 将交付项名称放入session
     *
     * @author zhousuxia
     * @version 2018年5月22日
     * @see PlanController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "setInputsNameToSession")
    @ResponseBody
    public AjaxJson setInputsNameToSession(HttpServletRequest request, HttpSession session) {
        AjaxJson j = new AjaxJson();
        try {
            session.setAttribute("inputsName", request.getParameter("inputsName"));
            j.setSuccess(true);
        }
        catch (Exception e) {
            j.setSuccess(false);
        }
        finally {
            return j;
        }
    }

    /**
     * 跳转选择来源计划页面
     *
     * @author zhousuxia
     * @version 2018年5月19日
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "goSelectPlanInputs")
    public ModelAndView goSelectPlanInputs(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        String inputsName = request.getParameter("inputsName");
        String tempId = request.getParameter("tempId");
        String parentPlanId = request.getParameter("parentPlanId");
        request.setAttribute("parentPlanId", parentPlanId);
        request.setAttribute("projectId", projectId);
        request.setAttribute("useObjectId", useObjectId);
        request.setAttribute("useObjectType", useObjectType);
        request.setAttribute("inputsName", inputsName);
        request.setAttribute("tempId", tempId);
        request.setAttribute("planId", useObjectId);

        return new ModelAndView("com/glaway/ids/project/plan/selectPlanInputs");
    }

    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusListForInputs")
    @ResponseBody
    public void statusListForInputs(HttpServletRequest request, HttpServletResponse response) {

        List<LifeCycleStatus> lifeCycleStatus = new ArrayList<LifeCycleStatus>();
        LifeCycleStatus a = new LifeCycleStatus();
        a.setName("EDITING");
        a.setTitle("拟制中");
        lifeCycleStatus.add(a);
        LifeCycleStatus f = new LifeCycleStatus();
        f.setName("LAUNCHED");
        f.setTitle("已发布");
        lifeCycleStatus.add(f);
        LifeCycleStatus g = new LifeCycleStatus();
        g.setName("TOBERECEIVED");
        g.setTitle("待接收");
        lifeCycleStatus.add(g);
        LifeCycleStatus d = new LifeCycleStatus();
        d.setName("ORDERED");
        d.setTitle("执行中");
        lifeCycleStatus.add(d);
        LifeCycleStatus b = new LifeCycleStatus();
        b.setName("FINISH");
        b.setTitle("已完工");
        lifeCycleStatus.add(b);
        LifeCycleStatus c = new LifeCycleStatus();
        c.setName("FEEDBACKING");
        c.setTitle("完工确认");
        lifeCycleStatus.add(c);

        String jonStr = JsonUtil.getCodeTitleJson(lifeCycleStatus, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
    public void planInputsList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        String id = request.getParameter("id");
        String inputsName = String.valueOf(request.getSession().getAttribute("inputsName"));
        try {
            inputsName = URLDecoder.decode(inputsName, "UTF-8");
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        PlanDto p = new PlanDto();
        p.setProjectId(projectId);
        List<PlanDto> planList = planService.queryPlanInputsList(p);

        List<PlanDto> tempList = new ArrayList<PlanDto>();
        /*
         * Map<String,String> mapList = planService.queryDeliverableInfoMap();
         * if(!CommonUtil.isEmpty(planList)){
         * for(Plan temp : planList ){
         * if(!CommonUtil.isEmpty(mapList.get(inputsName)) &&
         * mapList.get(inputsName).equals(temp.getId())){
         * tempList.add(temp);
         * }
         * }
         * }
         */

        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto temp : planList) {
                List<ProjDocVo> projDocRelationList = inputsFeignService.getDocRelationList(temp,UserUtil.getInstance().getUser().getId());
                if (!CommonUtil.isEmpty(projDocRelationList)) {
                    for (ProjDocVo vo : projDocRelationList) {
                        if(!CommonUtil.isEmpty(temp.getId()) && !CommonUtil.isEmpty(id)){
                            if (vo.getDeliverableName().equals(inputsName) && !temp.getId().equals(id)) {
                                tempList.add(temp);
                            }
                        }else{
                            if (vo.getDeliverableName().equals(inputsName)) {
                                tempList.add(temp);
                            }
                        }
                    }
                }

            }
        }

        List<PlanDto> list = new ArrayList<PlanDto>();
        if (plan != null && !plan.getId().startsWith(PlanConstants.PLAN_CREATE_UUID)) {
            Map<String, String> map = getPlanAllChildren(plan, tempList);
            for (PlanDto node : tempList) {
                if (StringUtils.isEmpty(map.get(node.getId()))) {
                    list.add(node);
                }
            }
        }
        else {
            list = tempList;
        }

        boolean isModifyBoo = false;
        isModifyBoo = projectService.isModifyForPlan(projectId);
        String isModify = "false";
        if (isModifyBoo) {
            isModify = "true";
        }

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        FeignJson teamIdFj = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = "";
        if (teamIdFj.isSuccess()) {
            teamId = teamIdFj.getObj() == null ? "" : teamIdFj.getObj().toString();
        }
        boolean isProjectManger = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode("false");
        parameVo.setPlanAssignOperationCode("false");
        parameVo.setPlanDeleteOperationCode("false");
        parameVo.setPlanChangeOperationCode("false");
        parameVo.setPlanDiscardOperationCode("false");
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        FeignJson statusFeign = planService.getPlanLifeStatus();
        List<LifeCycleStatus> statusList = new ArrayList<>();

        if (statusFeign.isSuccess()) {
            statusList = JSON.parseArray(statusFeign.getObj().toString(),LifeCycleStatus.class);
        }

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjectsForProblem(list, parameVo, project,
                statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);

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
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjectsForProblem(List<PlanDto> planList,
                                                                PlanAuthorityVo parameVo,
                                                                Project project,
                                                                List<LifeCycleStatus> statusList,List<ActivityTypeManageDto> activityTypeManageList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        //   String planLevelListStr = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchAllBusinessConfigs(ConfigTypeConstants.PLANLEVEL);
        Map<String, String> planLevelIdAndNameMap = new HashMap<String, String>();
        for(BusinessConfig cur : planLevelList) {
            planLevelIdAndNameMap.put(cur.getId(), cur.getName());
        }
        /*String dictCode = "activeCategory";

        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);*/
        Map<String,TSUserDto> userMap = userService.getAllUserIdsMap();
        FeignJson warninDayFj = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = "";
        if (warninDayFj.isSuccess()) {
            warningDay = warninDayFj.getObj() == null ? "" : warninDayFj.getObj().toString();
        }
        for (PlanDto p : planList) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            /*
             * if (StringUtils.isEmpty(p.getParentPlanId())
             * || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             */
            JSONObject root = new JSONObject();
            root.put("id", p.getId());
            root.put("parentPlanId", p.getParentPlanId());
            root.put("planNumber", p.getPlanNumber());
            root.put("optBtn", this.generateOptBtn(p, parameVo, project));
            root.put("progressRate", this.generateProgressRate(p, warningDay));
            root.put("displayName", p.getPlanName());
            if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                JSONObject treeNode = new JSONObject();
                treeNode.put("value", p.getPlanName());
                treeNode.put("image", "folder.gif");
                root.put("displayNameNode", treeNode);

                JSONObject treeNode1 = new JSONObject();
                treeNode1.put("value", this.generatePlanNameUrl(p));
                treeNode1.put("image", "folder.gif");
                root.put("planName", treeNode1);
            }
            else {
                root.put("displayNameNode", p.getPlanName());
                root.put("planName", this.generatePlanNameUrl(p));
            }

            root.put("planLevelInfo",
                    planLevelIdAndNameMap.get(p.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(p.getPlanLevel()));
            if (StringUtils.isNotBlank(p.getOwner())) {
                TSUserDto userDto = userService.getUserByUserId(p.getOwner());
                p.setOwnerInfo(userDto);
            }
            root.put("ownerInfo", userMap.get(p.getOwner()) == null ? "" : userMap.get(p.getOwner()).getRealName()
                    + "-"
                    + userMap.get(p.getOwner()).getUserName());
            root.put("bizCurrentInfo", this.generatePlanBizCurrent(p, statusList));
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
            root.put("parent_owner", p.getParentPlan() == null ? "" : p.getParentPlan().getOwner());
            root.put("parent_createBy",
                    p.getParentPlan() == null ? "" : p.getParentPlan().getCreateBy());
            root.put("parent_flowStatus",
                    p.getParentPlan() == null ? "" : p.getParentPlan().getFlowStatus());
            root.put("parent_bizCurrent",
                    p.getParentPlan() == null ? "" : p.getParentPlan().getBizCurrent());
            root.put("project_bizCurrent",
                    p.getProject() == null ? "" : p.getProject().getBizCurrent());
            root.put("opContent", p.getOpContent());

            root.put("creator", userMap.get(p.getCreateBy()) == null ? "" : userMap.get(p.getCreateBy()).getRealName() + "-"
                    + userMap.get(p.getCreateBy()).getUserName());
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
            root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(p, activityTypeManageList));
            if("1".equals(p.getTaskType())){
                root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
            }else if("2".equals(p.getTaskType())){
                root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
            }else{
                root.put("taskType", p.getTaskType());
            }
            root.put("planType", p.getPlanType());

            List<JSONObject> rows = new ArrayList<JSONObject>();
            root.put("rows", rows);
            rootList.add(root);
            // }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPid(parentPlanIds, planList, rootList.get(i), parameVo, activityTypeManageList,
                    project, statusList, warningDay,planLevelIdAndNameMap,userMap);
        }
        return rootList;
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
        String projectId = req.getParameter("projectId");
        List<TSUserDto> userList = new ArrayList<TSUserDto>();

        userList = projRoleService.getUserInProject(projectId);

        PlanDto p = new PlanDto();
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        String jonStr3 = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr3);
        Project project = projectService.getProjectEntity(projectId);

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        // List<BusinessConfig> planLevelList =
        // businessConfigService.searchBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
     //   List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        String b = JSON.toJSONString(planLevelList);
        req.setAttribute("planLevelList1", b);
        req.setAttribute("planLevelList", jonStr);

        for (TSUserDto t : userList) {
            t.setRealName(t.getRealName() + "-" + t.getUserName());
        }

        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        String aaa = JSON.toJSONString(userList);

        req.setAttribute("userList1", aaa);
        req.setAttribute("userList", jonStr2);
        req.setAttribute("projectId", projectId);
        String parentStartTimeStr = DateUtil.dateToString(project.getStartProjectTime(),"yyyy-MM-dd");
        String parentEndTimeStr = DateUtil.dateToString(project.getEndProjectTime(),"yyyy-MM-dd");
        req.setAttribute("qualityPlanStartTime", parentStartTimeStr);
        req.setAttribute("qualityPlanEndTime", parentEndTimeStr);

        return new ModelAndView("com/glaway/ids/pm/project/plan/planListModifyMass");
    }


    /**
     * 批量修改初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planModifyList")
    public void planModifyList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        plan.setProjectId(projectId);
        plan.setAvaliable("1");
        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId =String.valueOf(fJson.getObj()) ;
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);
        List<PlanDto> planListTemp = new ArrayList<PlanDto>();
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        for (PlanDto node : list) {
            if (isProjectManger) {
                if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getTaskType())) {
                    boolean isPmoPd = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO,
                            node.getCreateBy(), null);
                    if (isPmoPd || userId.equals(node.getCreateBy())) {
                        if (PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())
                                && PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(node.getFlowStatus())) {
                            node.set_parentId(node.getParentPlanId());
                            node.setOrder(String.valueOf(node.getStoreyNo()));
                            planListTemp.add(node);
                        }
                    }
                }
            }
            else {
                if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getTaskType())) {
                    if (userId.equals(node.getCreateBy())) {
                        if (PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())
                                && PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(node.getFlowStatus())) {
                            node.set_parentId(node.getParentPlanId());
                            node.setOrder(String.valueOf(node.getStoreyNo()));
                            planListTemp.add(node);
                        }
                    }
                }
            }
        }

        List<PlanDto> planListTempParent = new ArrayList<PlanDto>();

        for (PlanDto planTemp : planListTemp) {
            if (planTemp.getParentPlanId() != null) {
                PlanDto parent = planService.getPlanEntity(planTemp.getParentPlanId());
                if (parent != null) {
                    planListTempParent.add(parent);
                }

            }
        }
        List<PlanDto> planListTempParent2 = new ArrayList<PlanDto>();
        for (PlanDto p : planListTempParent) {
            String a = "";
            if (planListTempParent2.size() == 0) {
                planListTempParent2.add(p);
            }
            for (PlanDto p2 : planListTempParent2) {
                if (p.getId().equals(p2.getId())) {
                    a = "1";
                }
            }
            if ("".equals(a)) {
                planListTempParent2.add(p);
            }
        }

        List<PlanDto> planListTempParent3 = new ArrayList<PlanDto>();
        for (PlanDto node : planListTempParent2) {
            if (node.getParentPlan() != null) {
                if (!PlanConstants.PLAN_EDITING.equals(node.getParentPlan().getBizCurrent())) {
                    node.set_parentId(null);
                }
                else {
                    node.set_parentId(node.getParentPlanId());
                }
            }
            else {
                node.set_parentId(node.getParentPlanId());
            }
            node.setOrder(String.valueOf(node.getStoreyNo()));
            planListTempParent3.add(node);
        }

        planListTemp.addAll(planListTempParent3);

        List<PlanDto> planListTempParent4 = new ArrayList<PlanDto>();
        for (PlanDto p : planListTemp) {
            String a = "";
            if (planListTempParent4.size() == 0) {
                planListTempParent4.add(p);
            }
            for (PlanDto p2 : planListTempParent4) {
                if (p.getId().equals(p2.getId())) {
                    a = "1";
                }
            }
            if ("".equals(a)) {
                planListTempParent4.add(p);
            }
        }

        for (PlanDto node : planListTempParent4) {
            for (PlanDto node2 : planListTempParent4) {
                if (node.getParentPlanId() != null) {
                    if (node.getParentPlanId().equals(node2.getId())) {
                        node.set_parentId(node2.getId());
                    }
                }
            }
            node.setOrder(String.valueOf(node.getStoreyNo()));
        }

        DataGridReturn data = new DataGridReturn(planListTempParent4.size(), planListTempParent4);

        String json = gson.toJson(data);

        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 获取datagrid中的值
     *
     * @param request
     * @param response
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveModifyList")
    @ResponseBody
    public AjaxJson saveModifyList(String ids, String planLeveIds, String ownerIds,
                                   String planStartTimes, String planEndTimes, String workTimes,
                                   String milestones, HttpServletRequest request,
                                   HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.savePlanModifyListSuccess");
            failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.savePlanModifyListFail");

            String changePlan = request.getParameter("changePlanList");
        /*    List<Object> changePlanList = (List<Object>)JSON.parseArray(changePlan);*/

            FeignJson fj = new FeignJson();
            fj.setObj(changePlan);
            planFlowForworkService.saveModifyListForWork(fj);
            log.info(message);
        }
        catch (Exception e) {
            log.error(failMessage, e, "", ids);
            System.out.println(e.getMessage());
            Object[] params = new Object[] {failMessage, ids};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }

    }


    /**
     * 获取日期的下一天
     *
     * @return
     */
    @RequestMapping(params = "getAfterTime")
    @ResponseBody
    public AjaxJson getAfterTime(String id, Date preposeEndTime, HttpServletRequest request,
                                 HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            if (preposeEndTime != null) {
                Date preposeEndTimeNext = TimeUtil.getExtraDate(preposeEndTime, 1);
                PlanDto plan = planService.getPlanEntity(id);
                plan.setPreposeIds(id);
                List<PreposePlanDto> preposePlanList = preposePlanService.getPostposesByPreposeId(plan);

                for (PreposePlanDto s : preposePlanList) {
                    PlanDto p = planService.getPlanEntity(s.getPlanId());
                    if (preposeEndTimeNext.getTime() < p.getPlanStartTime().getTime()) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {p.getPlanName(),
                                p.getPlanEndTime().toString()};
                        I18nUtil.getValue("com.glaway.ids.pm.project.plan.getAfterTime", arguments);
                        return j;
                    }
                }

                String preposePlanIds = "";
                for (PreposePlanDto s : preposePlanList) {
                    preposePlanIds = preposePlanIds + s.getPlanId() + ",";
                }
                if (preposePlanIds.endsWith(",")) {
                    preposePlanIds = preposePlanIds.substring(0, preposePlanIds.length() - 1);
                }

                j.setObj(DateUtil.getStringFromDate(preposeEndTimeNext, DateUtil.YYYY_MM_DD));
                j.setMsg(preposePlanIds);
                j.setSuccess(true);

            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkTimeByStartTimeAndEndTimeFail");
                j.setSuccess(false);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkFail");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return j;
    }


    /**
     * 获取计划的工期
     *
     * @param plan
     * @return
     */
    @RequestMapping(params = "getWork")
    @ResponseBody
    public AjaxJson getWork(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        long workTime = 0;
        try {

            Project project = projectService.getProjectEntity(plan.getProjectId());
            if (project != null) {
                if (StringUtils.isNotEmpty(project.getProjectTimeType())) {
                    plan.setStatus(project.getProjectTimeType());
                }
                else {
                    plan.setStatus(ProjectConstants.NATURALDAY);
                }
                if (ProjectConstants.WORKDAY.equals(plan.getStatus())) {
                    workTime = TimeUtil.getWorkDayNumber(plan.getPlanStartTime(),
                            plan.getPlanEndTime());
                }
                else if (ProjectConstants.COMPANYDAY.equals(plan.getStatus())) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("startDate",plan.getPlanStartTime());
                    params.put("endDate",plan.getPlanEndTime());
                    workTime = calendarService.getAllWorkingDay(appKey,params).size();
                }
                else {
                    Date start = (Date)plan.getPlanStartTime().clone();
                    Date end = (Date)plan.getPlanEndTime().clone();
                    workTime = DateUtil.dayDiff(start, end) + 1;
                }
                j.setObj(String.valueOf(workTime));
                j.setSuccess(true);

            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkTimeByStartTimeAndEndTimeFail");
                j.setSuccess(false);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkFail");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return j;
    }



    /**
     * 默认进入的首页面
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagridForSelectedPlanList")
    @ResponseBody
    public AjaxJson searchDatagridForSelectedPlanList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planNumber = request.getParameter("planNumber");
        String isDelay = request.getParameter("isDelay");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        String projectId = request.getParameter("projectId");

        String planId = request.getParameter("planId");

        String tempViewId = request.getParameter("tempViewId");
        String type = request.getParameter("type");

        String planIds = "";
        List<PlanViewSetConditionDto> viewConditionList = new ArrayList<PlanViewSetConditionDto>();
        if(!CommonUtil.isEmpty(tempViewId)){
            String viewStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
            if(!CommonUtil.isEmpty(viewStr)){
                viewConditionList = JSON.parseArray(viewStr,PlanViewSetConditionDto.class);
            }
            if(!CommonUtil.isEmpty(viewConditionList)){
                for(PlanViewSetConditionDto condition : viewConditionList){
                    if(CommonUtil.isEmpty(planIds)){
                        planIds = "'"+condition.getPlanId()+"'";
                    }else{
                        planIds = planIds +","+ "'"+condition.getPlanId()+"'";
                    }
                }
            }
        }

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planNumber)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(isDelay)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(isDelay.split(","));
            vo.setCondition("in");
            vo.setValue(isDelay);
            vo.setKey("Plan.isDelay");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }
        PageList pageList = new PageList();
        if(!CommonUtil.isEmpty(type)){
            if(type.equals("selectedPlan")){
                if(!CommonUtil.isEmpty(planIds)){
                    Map<String, Object> map = new HashMap<>();
                    map.put("conditionList",conditionList);
                    map.put("projectId",projectId);
                    map.put("userName",userName);
                    map.put("progressRate",progressRate);
                    map.put("workTime",workTime);
                    map.put("planIds",planIds);

                    pageList = planService.queryEntityForSelectedPlan(map);
                }
            }else if(type.equals("mySelect")){
                Map<String, Object> map = new HashMap<>();
                map.put("conditionList",conditionList);
                map.put("projectId",projectId);
                map.put("userName",userName);
                map.put("progressRate",progressRate);
                map.put("workTime",workTime);

                pageList = planService.queryEntity(map);
            }
        }

        List<PlanDto> planList = JSON.parseArray(JSON.toJSONString(pageList.getResultList()),PlanDto.class);

        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = new PlanDto();
            for (PlanDto p : planList) {
                if (p.getId().equals(planId)) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto plan = new PlanDto();
        plan.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(plan);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParent(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId =String.valueOf(fJson.getObj());
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        List<JSONObject> rootList = new ArrayList<JSONObject>();

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        if(!CommonUtil.isEmpty(type) && type.equals("mySelect")){
            rootList = changePlansToJSONObjectsForCustomViewSearch(tempList, parameVo, project,
                    statusList,viewConditionList,activityTypeManageList);
        }else{
            rootList = changePlansToJSONObjectsForCustomView(tempList, parameVo, project,
                    statusList,activityTypeManageList);
        }


        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }


    private void getParent(List<PlanDto> tempList, String parentPlanId,
                           Map<String, String> planIdMaps, Map<String, PlanDto> listMap) {
        if (StringUtils.isNotEmpty(parentPlanId)
                && StringUtils.isEmpty(planIdMaps.get(parentPlanId))) {
            PlanDto planDto = planService.getPlanEntity(parentPlanId);
            if("1".equals(planDto.getAvaliable())){
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
                    getParent(tempList, parentPlanId, planIdMaps, listMap);
                }
            }
        }
    }


    /**
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjectsForCustomViewSearch(List<PlanDto> planList,
                                                                         PlanAuthorityVo parameVo, Project project,
                                                                         List<LifeCycleStatus> statusList,List<PlanViewSetConditionDto> conditionList,List<ActivityTypeManageDto> activityTypeManageList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }

        Map<String,String> conditionMap = new HashMap<String, String>();
        if(!CommonUtil.isEmpty(conditionList)){
            for(PlanViewSetConditionDto con : conditionList){
                conditionMap.put(con.getPlanId(), con.getPlanViewInfoId());
            }
        }
        Map<String,TSUserDto> userMap = userService.getAllUserIdsMap();
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
                root.put("parentPlanId", p.getParentPlanId());
                root.put("optBtn", this.generateOptBtn(p, parameVo, project));
                root.put("progressRate", this.generateProgressRate(p, warningDay));
                root.put("displayName", p.getPlanName());

                if(!CommonUtil.isEmpty(conditionMap.get(p.getId()))){

                    root.put("planNumber", this.generatePlanNameUrlForCustomViewSearch(String.valueOf(p.getPlanNumber())));

                    if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                        JSONObject treeNode = new JSONObject();
                        treeNode.put("value", this.generatePlanNameUrlForCustomViewSearch(p.getPlanName()));
                        treeNode.put("image", "folder.gif");
                        root.put("displayNameNode", treeNode);

                        JSONObject treeNode1 = new JSONObject();
                        treeNode1.put("value", this.generatePlanNameUrlForCustomViewSearch(p.getPlanName()));
                        treeNode1.put("image", "folder.gif");
                        root.put("planName", treeNode1);
                    }
                    else {
                        root.put("displayNameNode", this.generatePlanNameUrlForCustomViewSearch(p.getPlanName()));
                        root.put("planName", this.generatePlanNameUrlForCustomViewSearch(p.getPlanName()));
                    }

                    root.put("planLevelInfo",
                            p.getPlanLevelInfo() == null ? "" : this.generatePlanNameUrlForCustomViewSearch(p.getPlanLevelInfo().getName()));
                    root.put("ownerInfo",
                            userMap.get(p.getOwner()) == null ? "" : this.generatePlanNameUrlForCustomViewSearch(userMap.get(p.getOwner()).getRealName() + "-"
                                    + userMap.get(p.getOwner()).getUserName()));
                    root.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomViewGrey(p, statusList));
                    root.put("planStartTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(p.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT)));
                    root.put("planEndTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(p.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT)));

                    root.put("assignerInfo",
                            p.getAssignerInfo() == null ? "" : this.generatePlanNameUrlForCustomViewSearch(p.getAssignerInfo().getRealName() + "-"
                                    + p.getAssignerInfo().getUserName()));

                    root.put("assignTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(p.getAssignTime(), DateUtil.LONG_DATE_FORMAT)));
                    root.put("workTime", this.generatePlanNameUrlForCustomViewSearch(p.getWorkTime()));

                    root.put("preposePlans", this.generatePlanNameUrlForCustomViewSearch(p.getPreposePlans()));

                    root.put("milestone", "true".equals(p.getMilestone()) ? this.generatePlanNameUrlForCustomViewSearch("是") : this.generatePlanNameUrlForCustomViewSearch("否"));

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

                    root.put("creator", userMap.get(p.getCreateBy()) == null ? "" : this.generatePlanNameUrlForCustomViewSearch(userMap.get(p.getCreateBy()).getRealName()
                            + "-"
                            + userMap.get(p.getCreateBy()).getUserName()));
                    root.put("createTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(p.getCreateTime(), DateUtil.LONG_DATE_FORMAT)));

                    root.put("flowStatus", p.getFlowStatus());
                    root.put("result", p.getResult());

                    // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                    root.put("actualEndTime",
                            DateUtil.dateToString(p.getActualEndTime(), DateUtil.LONG_DATE_FORMAT));
                    root.put("invalidTime",
                            DateUtil.dateToString(p.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                    root.put("taskNameType", this.generatePlanNameUrlForCustomViewSearch(p.getTaskNameType()));
                    root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrlSearch(p,activityTypeManageList));
                    if("1".equals(p.getTaskType())){
                        root.put("taskType", this.generatePlanNameUrlForCustomViewSearch(PlanConstants.PLAN_TYPE_WBS));
                    }else if("2".equals(p.getTaskType())){
                        root.put("taskType", this.generatePlanNameUrlForCustomViewSearch(PlanConstants.PLAN_TYPE_WBS));
                    }else{
                        root.put("taskType", p.getTaskType());
                    }
                    root.put("planType", this.generatePlanNameUrlForCustomViewSearch(p.getPlanType()));


                }else{

                    root.put("planNumber", p.getPlanNumber());

                    if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                        JSONObject treeNode = new JSONObject();
                        treeNode.put("value", p.getPlanName());
                        treeNode.put("image", "folder.gif");
                        root.put("displayNameNode", treeNode);

                        JSONObject treeNode1 = new JSONObject();
                        treeNode1.put("value", this.generatePlanNameUrlForCustomView(p));
                        treeNode1.put("image", "folder.gif");
                        root.put("planName", treeNode1);
                    }
                    else {
                        root.put("displayNameNode", p.getPlanName());
                        root.put("planName", this.generatePlanNameUrlForCustomView(p));
                    }

                    root.put("planLevelInfo",
                            p.getPlanLevelInfo() == null ? "" : p.getPlanLevelInfo().getName());
                    root.put("ownerInfo",
                            userMap.get(p.getOwner()) == null ? "" : userMap.get(p.getOwner()).getRealName() + "-"
                                    + userMap.get(p.getOwner()).getUserName());
                    root.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomView(p, statusList));
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

                    root.put("creator", userMap.get(p.getCreateBy()) == null ? "" : userMap.get(p.getCreateBy()).getRealName()
                            + "-"
                            + userMap.get(p.getCreateBy()).getUserName());
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
                    root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(p, activityTypeManageList));
                    if("1".equals(p.getTaskType())){
                        root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else if("2".equals(p.getTaskType())){
                        root.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else{
                        root.put("taskType", p.getTaskType());
                    }
                    root.put("planType", p.getPlanType());
                }



                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPidForCustomViewSearch(parentPlanIds, planList, rootList.get(i), parameVo, types,
                    project, statusList, warningDay,conditionList,activityTypeManageList,userMap);
        }
        return rootList;
    }


    /**
     * 构造计划名称页面链接
     *
     * @return
     * @see
     */
    private String generatePlanNameUrlForCustomViewSearch(String obj) {

        return "<a style='color:gray;cursor:pointer;'>" + obj + "</a>";

    }

    /**
     * 构造计划状态显示及链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanBizCurrentForCustomViewGrey(PlanDto plan, List<LifeCycleStatus> statusList) {
        String status = "";
        if ("PAUSE".equals(plan.getProjectStatus())) {
            status = "已暂停";
        }
        else if ("CLOSE".equals(plan.getProjectStatus())) {
            status = "已关闭";
        }
        else {
            for (LifeCycleStatus lifeCycleStatus : statusList) {
                if (lifeCycleStatus.getName().equals(plan.getBizCurrent())) {
                    status = lifeCycleStatus.getTitle();
                    break;
                }
            }
        }
        status = "<font color='grey'>" + status + "</font>";
        /*if ("1".equals(plan.getFlowFlag()) && !"true".equals(plan.getIsAssignBack())) {
            status = "<a href='#' onclick='openFlowPlans_(\"" + plan.getId()
                + "\")'><font color='blue'>" + status + "</font></a>";
        }*/
        return status;
    }


    /**
     * 转化计划类型
     *
     * @param plan
     * @return
     * @see
     */
    private String generateTaskNameTypeUrlSearch(PlanDto plan, List<ActivityTypeManageDto> activityTypeManageList) {
        String taskNameTypeDisplay = "";
        for (ActivityTypeManageDto type : activityTypeManageList) {
            if (type.getId().equals(plan.getTaskNameType())) {
                taskNameTypeDisplay = type.getName();
                break;
            }
        }
        return "<a style='color:gray;cursor:pointer;'>" + taskNameTypeDisplay + "</a>";
    }



    /**
     * Description:递归查询获取所有子节点
     *
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPidForCustomViewSearch(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                                    JSONObject parentObject, PlanAuthorityVo parameVo,
                                                    List<TSTypeDto> types, Project currentProject,
                                                    List<LifeCycleStatus> statusList, String warningDay,List<PlanViewSetConditionDto> conditionList,List<ActivityTypeManageDto> activityTypeManageList,Map<String,TSUserDto> userMap) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();

        Map<String,String> conditionMap = new HashMap<String, String>();
        if(!CommonUtil.isEmpty(conditionList)){
            for(PlanViewSetConditionDto con : conditionList){
                conditionMap.put(con.getPlanId(), con.getPlanViewInfoId());
            }
        }

        for (PlanDto plan : planList) {
            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();

                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("optBtn", this.generateOptBtn(plan, parameVo, currentProject));
                newNode.put("progressRate", this.generateProgressRate(plan, warningDay));

                if(!CommonUtil.isEmpty(conditionMap.get(plan.getId()))){

                    newNode.put("planNumber", this.generatePlanNameUrlForCustomViewSearch(String.valueOf(plan.getPlanNumber())));

                    newNode.put("displayName", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanName()));
                    if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                        JSONObject treeNode = new JSONObject();
                        treeNode.put("value", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanName()));
                        treeNode.put("image", "folder.gif");
                        newNode.put("displayNameNode", treeNode);

                        JSONObject treeNode1 = new JSONObject();
                        treeNode1.put("value", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanName()));
                        treeNode1.put("image", "folder.gif");
                        newNode.put("planName", treeNode1);
                    }
                    else {
                        newNode.put("displayNameNode", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanName()));
                        newNode.put("planName", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanName()));
                    }

                    newNode.put("planLevelInfo",
                            plan.getPlanLevelInfo() == null ? "" : this.generatePlanNameUrlForCustomViewSearch(plan.getPlanLevelInfo().getName()));
                    newNode.put("ownerInfo",
                            userMap.get(plan.getOwner()) == null ? "" : this.generatePlanNameUrlForCustomViewSearch(userMap.get(plan.getOwner()).getRealName() + "-"
                                    + userMap.get(plan.getOwner()).getUserName()));
                    newNode.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomViewGrey(plan, statusList));
                    newNode.put("planStartTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(plan.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT)));
                    newNode.put("planEndTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(plan.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT)));
                    newNode.put("assignerInfo",
                            plan.getAssignerInfo() == null ? "" : this.generatePlanNameUrlForCustomViewSearch(plan.getAssignerInfo().getRealName()
                                    + "-"
                                    + plan.getAssignerInfo().getUserName()));
                    newNode.put("assignTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(plan.getAssignTime(), DateUtil.LONG_DATE_FORMAT)));
                    newNode.put("workTime", this.generatePlanNameUrlForCustomViewSearch(plan.getWorkTime()));

                    newNode.put("preposePlans", this.generatePlanNameUrlForCustomViewSearch(plan.getPreposePlans()));

                    newNode.put("milestone", "true".equals(plan.getMilestone()) ? this.generatePlanNameUrlForCustomViewSearch("是") : this.generatePlanNameUrlForCustomViewSearch("否"));

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
                            userMap.get(plan.getCreateBy()) == null ? "" : this.generatePlanNameUrlForCustomViewSearch(userMap.get(plan.getCreateBy()).getRealName() + "-"
                                    + userMap.get(plan.getCreateBy()).getUserName()));
                    newNode.put("createTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(plan.getCreateTime(), DateUtil.LONG_DATE_FORMAT)));

                    newNode.put("flowStatus", plan.getFlowStatus());
                    newNode.put("result", plan.getResult());

                    // 增加计划时间完成时间、废弃时间、计划类别和计划类型的展示
                    newNode.put("actualEndTime",
                            this.generatePlanNameUrlForCustomViewSearch(DateUtil.dateToString(plan.getActualEndTime(), DateUtil.LONG_DATE_FORMAT)));
                    newNode.put("invalidTime",
                            DateUtil.dateToString(plan.getInvalidTime(), DateUtil.LONG_DATE_FORMAT));
                    newNode.put("taskNameType", this.generatePlanNameUrlForCustomViewSearch(plan.getTaskNameType()));
                    newNode.put("taskNameTypeDisplay", this.generateTaskNameTypeUrlSearch(plan, activityTypeManageList));
                    if("1".equals(plan.getTaskType())){
                        newNode.put("taskType", this.generatePlanNameUrlForCustomViewSearch(PlanConstants.PLAN_TYPE_WBS));
                    }else if("2".equals(plan.getTaskType())){
                        newNode.put("taskType", this.generatePlanNameUrlForCustomViewSearch(PlanConstants.PLAN_TYPE_WBS));
                    }else{
                        newNode.put("taskType", plan.getTaskType());
                    }
                    newNode.put("planType", this.generatePlanNameUrlForCustomViewSearch(plan.getPlanType()));
                }else{
                    newNode.put("planNumber", plan.getPlanNumber());

                    newNode.put("displayName", plan.getPlanName());
                    if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                        JSONObject treeNode = new JSONObject();
                        treeNode.put("value", plan.getPlanName());
                        treeNode.put("image", "folder.gif");
                        newNode.put("displayNameNode", treeNode);

                        JSONObject treeNode1 = new JSONObject();
                        treeNode1.put("value", this.generatePlanNameUrlForCustomView(plan));
                        treeNode1.put("image", "folder.gif");
                        newNode.put("planName", treeNode1);
                    }
                    else {
                        newNode.put("displayNameNode", plan.getPlanName());
                        newNode.put("planName", this.generatePlanNameUrlForCustomView(plan));
                    }

                    newNode.put("planLevelInfo",
                            plan.getPlanLevelInfo() == null ? "" : plan.getPlanLevelInfo().getName());
                    newNode.put("ownerInfo",
                            userMap.get(plan.getOwner()) == null ? "" : userMap.get(plan.getOwner()).getRealName() + "-"
                                    + userMap.get(plan.getOwner()).getUserName());
                    newNode.put("bizCurrentInfo", this.generatePlanBizCurrentForCustomView(plan, statusList));
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
                            userMap.get(plan.getCreateBy()) == null ? "" : userMap.get(plan.getCreateBy()).getRealName() + "-"
                                    + userMap.get(plan.getCreateBy()).getUserName());
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
                    newNode.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(plan, activityTypeManageList));
                    if("1".equals(plan.getTaskType())){
                        newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else if("2".equals(plan.getTaskType())){
                        newNode.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else{
                        newNode.put("taskType", plan.getTaskType());
                    }
                    newNode.put("planType", plan.getPlanType());
                }


                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPidForCustomViewSearch(parentPlanIds, planList, subNodeList.get(i), parameVo,
                        types, currentProject, statusList, warningDay,conditionList,activityTypeManageList,userMap);
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
     * 视图自定义设置初始化时获取已选计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getSelectedPlanList")
    @ResponseBody
    public AjaxJson getSelectedPlanList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        boolean isPmo = "true".equals(request.getParameter("isPmo"));
        boolean isProjectManger = "true".equals(request.getParameter("isProjectManger"));
        plan.setProjectId(projectId);

        String tempViewId = request.getParameter("tempViewId");
        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto pla = new PlanDto();
        pla.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(pla);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        List<PlanDto> planList = new ArrayList<PlanDto>();

        String planIds = "";
        List<PlanViewSetConditionDto> conditionList = new ArrayList<>();
        String conditionStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
        if(!CommonUtil.isEmpty(conditionStr)){
            conditionList = JSON.parseArray(conditionStr,PlanViewSetConditionDto.class);
        }
        if(!CommonUtil.isEmpty(conditionList)){
            for(PlanViewSetConditionDto condition : conditionList){
                if(CommonUtil.isEmpty(planIds)){
                    planIds = "'"+condition.getPlanId()+"'";
                }else{
                    planIds = planIds +","+ "'"+condition.getPlanId()+"'";
                }

          /*      if(!CommonUtil.isEmpty(listMap.get(condition.getPlanId()))){
                    planList.add(listMap.get(condition.getPlanId()));
                }*/
            }
        }
        if(!CommonUtil.isEmpty(planIds)){
            planList = planService.queryPlanListForCustomViewTreegrid(plan,planIds);
        }

        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(plan.getId())) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);


        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);
        Project project = projectService.getProjectEntity(projectId);
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjectsForCustomView(tempList, parameVo, project,
                statusList,activityTypeManageList);
        String resultJSON = JSON.toJSONString(rootList);
        j.setObj(resultJSON);
        j.setSuccess(true);
        return j;

    }



    /**
     * 项目计划页面初始化时获取计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "listForCustomView")
    public void listForCustomView(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        boolean isPmo = "true".equals(request.getParameter("isPmo"));
        boolean isProjectManger = "true".equals(request.getParameter("isProjectManger"));
        plan.setProjectId(projectId);

        List<PlanDto> planList = new ArrayList<PlanDto>();

        planList = planService.queryPlanListForTreegrid(plan);


        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(plan.getId())) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
//        Plan pla = new Plan();
//        pla.setProjectId(projectId);
//        List<Plan> list = planService.queryPlanListForTreegrid(pla);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : planList) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);
        Project project = projectService.getProjectEntity(projectId);
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        if(!CommonUtil.isEmpty(request.getParameter("flag")) && request.getParameter("flag").equals("customView")){
            rootList = changePlansToJSONObjectsForCustomView(tempList, parameVo, project,
                    statusList,activityTypeManageList);
        }else{
            rootList = changePlansToJSONObjects(tempList, parameVo, project,
                    statusList,activityTypeManageList);
        }

        String resultJSON = JSON.toJSONString(rootList);
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
     * 视图自定义设置初始化时获取已选计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getSelectedPlanListAll")
    public void getSelectedPlanListAll(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planConcernOperationCode = request.getParameter("planConcernOperationCode");
        String planUnconcernOperationCode = request.getParameter("planUnconcernOperationCode");
        boolean isPmo = "true".equals(request.getParameter("isPmo"));
        boolean isProjectManger = "true".equals(request.getParameter("isProjectManger"));
        plan.setProjectId(projectId);

        String tempViewId = request.getParameter("tempViewId");
        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto pla = new PlanDto();
        pla.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(pla);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        List<PlanDto> planList = new ArrayList<PlanDto>();

        List<PlanViewSetConditionDto> conditionList = new ArrayList<>();
        String conditionStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
        if(!CommonUtil.isEmpty(conditionStr)){
            conditionList = JSON.parseArray(conditionStr,PlanViewSetConditionDto.class);
        }
        if(!CommonUtil.isEmpty(conditionList)){
            for(PlanViewSetConditionDto condition : conditionList){
                if(!CommonUtil.isEmpty(listMap.get(condition.getPlanId()))){
                    planList.add(listMap.get(condition.getPlanId()));
                }
            }
        }


        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(plan.getId())) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParentPlan(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPlanConcernOperationCode(planConcernOperationCode);
        parameVo.setPlanUnconcernOperationCode(planUnconcernOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);
        Project project = projectService.getProjectEntity(projectId);
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(tempList, parameVo, project,
                statusList,activityTypeManageList);
        String resultJSON = JSON.toJSONString(rootList);
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
     * 计划前置选择页面跳转 ——2016年4月6日 10:03:05 wqb
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "outPlanPreposeTree")
    public ModelAndView outPlanPreposeTree(PlanDto plan, HttpServletRequest req) {
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr);
        req.setAttribute("planId", plan.getId());
        req.setAttribute("parentPlanId", plan.getParentPlanId());

        return new ModelAndView("com/glaway/ids/pm/project/plan/planoutPreposeList");
    }

    /**
     * 外部前置选择页面初始化 ——2016年4月6日 10:33:05 wqb
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "outplanPreposeList")
    public void outplanPreposeList(PlanDto plan, HttpServletRequest request,
                                   HttpServletResponse response) {
        PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
        PlanDto p = new PlanDto();
        p.setProjectId(parent.getProjectId());
        List<PlanDto> planList = planService.queryPlansExceptInvalid(p);
        List<PlanDto> list = new ArrayList<PlanDto>();
        for (PlanDto node : planList) {
            if (plan.getParentPlanId().equals(node.getId())) {
                parent = node;
            }
        }

        Map<String, String> map = getPlanAllChildren(parent, planList);
        for (PlanDto node : planList) {
            if (StringUtils.isEmpty(map.get(node.getId()))) {
                list.add(node);
            }
        }
        boolean isModifyBoo = false;
        if(!CommonUtil.isEmpty(plan.getProjectId())) {
            isModifyBoo = projectService.isModifyForPlan(plan.getProjectId());
        }
        String isModify = "false";
        if (isModifyBoo) {
            isModify = "true";
        }

        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson fJson = projRoleService.getTeamIdByProjectId(plan.getProjectId());
        String teamId =String.valueOf(fJson.getObj());
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode("false");
        parameVo.setPlanAssignOperationCode("false");
        parameVo.setPlanDeleteOperationCode("false");
        parameVo.setPlanChangeOperationCode("false");
        parameVo.setPlanDiscardOperationCode("false");
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(parent.getProjectId());

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(list, parameVo, project, statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);

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
     * 外部前置选择页面检索
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "outplanPreposesForSearch")
    @ResponseBody
    public AjaxJson outplanPreposesForSearch(HttpServletRequest request,
                                             HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String parentPlanId = request.getParameter("parentPlanId");
        String planNumber = request.getParameter("planNumber");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planNumber)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }else {
            progressRate = "";
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }

        // 获取检索命中的计划List，将其ID放入planIdMaps中
        String projectId = parent.getProjectId();
        List<PlanDto> resultList = planService.getOutplanPreposesForSearch(conditionList, projectId, userName, progressRate, workTime);
        // 去除含有父计划关系的所有节点
        PlanDto parentP = null;
        List<PlanDto> planList = new ArrayList<PlanDto>();
        for (PlanDto node : resultList) {
            if (parentPlanId.equals(node.getId())) {
                parentP = node;
            }
        }
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (parentP != null) {
            Map<String, String> map = getPlanAllChildren(parentP, resultList);
            for (PlanDto node : resultList) {
                if (StringUtils.isEmpty(map.get(node.getId()))) {
                    node.setFlowFlag("");
                    planList.add(node);
                    planIdMaps.put(node.getId(), node.getId());
                }
            }
        }
        else {
            for (PlanDto node : resultList) {
                node.setFlowFlag("");
                planList.add(node);
                planIdMaps.put(node.getId(), node.getId());
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto plan = new PlanDto();
        plan.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(plan);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            if (StringUtils.isNotEmpty(p.getParentPlanId())
                    && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                if (listMap.get(p.getParentPlanId()) != null) {
                    PlanDto parentPlan = listMap.get(p.getParentPlanId());
                    parentPlan.setFlowFlag("");
                    parentPlan.setResult("true");
                    tempList.add(parentPlan);
                }
            }
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);
        boolean isModifyBoo = false;
        isModifyBoo = projectService.isModifyForPlan(plan.getProjectId());
        String isModify = "false";
        if (isModifyBoo) {
            isModify = "true";
        }

        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson fJson = projRoleService.getTeamIdByProjectId(plan.getProjectId());
        String teamId =String.valueOf(fJson.getObj());

        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);
        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode("false");
        parameVo.setPlanAssignOperationCode("false");
        parameVo.setPlanDeleteOperationCode("false");
        parameVo.setPlanChangeOperationCode("false");
        parameVo.setPlanDiscardOperationCode("false");
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(tempList, parameVo, project,
                statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }


    /**
     * 查询计划等级
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "planLevelList")
    @ResponseBody
    public void planLevelList(HttpServletRequest request, HttpServletResponse response) {

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);

        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取日期的下一天
     *
     * @param
     * @return
     */
    @RequestMapping(params = "getNextDay")
    @ResponseBody
    public AjaxJson getNextDay(String projectId, Date preposeEndTime, HttpServletRequest request,
                               HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        // long workTime = 0;
        try {
            Project project = projectService.getProjectEntity(projectId);
            if (project != null) {
                if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                    Date date = (Date)preposeEndTime.clone();
                    Boolean a = CalendarParser.judgeDefaultWorkingDay(date);
                    if (a) {
                        preposeEndTime = DateUtil.nextWorkDay(date, 1);
                    }
                    else {
                        Date date2 = DateUtil.nextWorkDay(date, 1);
                        Boolean b = CalendarParser.judgeDefaultWorkingDay(date2);
                        if (b) {
                            preposeEndTime = date2;
                        }
                        else {
                            preposeEndTime = DateUtil.nextWorkDay(date2, 1);
                        }
                    }

                }
                else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("startDate",preposeEndTime);
                    params.put("days",0);
                    preposeEndTime = calendarService.getNextWorkingDay(ResourceUtil.getApplicationInformation().getAppKey(),params);
                }
                else {
                    Date date = (Date)preposeEndTime.clone();
                    preposeEndTime = DateUtil.nextDay(date, 1);
                }
                j.setObj(DateUtil.getStringFromDate(preposeEndTime, DateUtil.YYYY_MM_DD));
                j.setSuccess(true);

            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkTimeByStartTimeAndEndTimeFail");
                j.setMsg(message);
                j.setSuccess(false);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.getWorkFail");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2001, params, e);
        }
        return j;
    }

    /**
     * 项目计划批量下达页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanMass")
    public ModelAndView goAssignPlanMass(PlanDto plan, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if (StringUtils.isNotEmpty(plan.getId())) {
            plan = planService.getPlanEntity(plan.getId());
            req.setAttribute("plan_", plan);
        }
        if (StringUtils.isNotEmpty(projectId)) {
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantMass");
    }

    /**
     * 计划下达页面初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planAssignList")
    public void planAssignList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String appKey = ResourceUtil.getApplicationInformation().getAppKey();

        // 获取所属项目
        String projectId = request.getParameter("projectId");
        plan.setProjectId(projectId);
        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        FeignJson teamIdJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = teamIdJson.getObj().toString();
        boolean isProjectManger = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        List<PlanDto> planList = new ArrayList<>();
        List<PlanDto> list = planService.queryPlanListForTreegrid(plan);
        for (PlanDto node : list) {
            if (isProjectManger) {
                if (node.getIsCreateByPmo() || userId.equals(node.getCreateBy())) {
                    if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getPlanType())) {
                        if(CommonUtil.isEmpty(node.getFormId()) || (!CommonUtil.isEmpty(node.getFormId()) && "false".equals(node.getIsAssignSingleBack())) ){
                            if (node.getParentPlanId() == null
                                    && PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())) {
                                planList.add(node);
                            }
                            else if (node.getParentPlanId() != null && node.getParentPlan() != null
                                    && PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())) {
                                if (!PlanConstants.PLAN_EDITING.equals(node.getParentPlan().getBizCurrent())) {
                                    node.setParentPlanId(null);
                                }
                                planList.add(node);
                            }
                        }
                    }
                }
            }
            else {
                if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getPlanType())) {
                    if(CommonUtil.isEmpty(node.getFormId()) || (!CommonUtil.isEmpty(node.getFormId()) && "false".equals(node.getIsAssignSingleBack())) ){
                        if (userId.equals(node.getCreateBy())) {
                            if (node.getParentPlanId() == null
                                    && PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())) {
                                planList.add(node);
                            }
                            else if (node.getParentPlanId() != null && node.getParentPlan() != null
                                    && PlanConstants.PLAN_EDITING.equals(node.getBizCurrent())) {
                                if (!PlanConstants.PLAN_EDITING.equals(node.getParentPlan().getBizCurrent())) {
                                    node.setParentPlanId(null);
                                }
                                planList.add(node);
                            }
                        }
                    }
                }

            }
        }

        boolean isModifyBoo = projectService.isModifyForPlan(projectId);
        String isModify = "false";
        if (isModifyBoo) {
            isModify = "true";
        }
        Project project = projectService.getProjectEntity(projectId);
        // 批量下达计划根据计划下达的逻辑重新处理存放的集合
        List<PlanDto> planListNew = new ArrayList<>();

        PlanAuthorityVo parameVoNew = new PlanAuthorityVo();
        parameVoNew.setIsModify(isModify);
        parameVoNew.setPlanAssignOperationCode("true");
        parameVoNew.setPmo(isPmo);
        parameVoNew.setProjectManger(isProjectManger);
        // 批量下达计划权限的处理 逻辑等同于单条计划的下达
        for (PlanDto plan2 : planList) {
            // 参数用来 用单条下达的方法判断 判断批量下达的集合中 能下达计划的数据 true 标示改计划没有下达权限 false 标示有下达权限
            boolean flag = hideAssign(plan2, parameVoNew, project);
            if (!flag) {
                planListNew.add(plan2);
            }
        }

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode("false");
        parameVo.setPlanAssignOperationCode("false");
        parameVo.setPlanDeleteOperationCode("false");
        parameVo.setPlanChangeOperationCode("false");
        parameVo.setPlanDiscardOperationCode("false");
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);


        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);
        List<JSONObject> rootList = changePlansToJSONObjects(planListNew, parameVo, project,
                statusList,activityTypeManageList);

        String resultJSON = JSON.toJSONString(rootList);

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
     * 计划前置选择页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goProblemTaskList")
    public ModelAndView goProblemTaskList(PlanDto plan, HttpServletRequest req) {
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        String taskId = req.getParameter("taskId");

        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr);
        req.setAttribute("planId", plan.getId());
        req.setAttribute("projectId", plan.getProjectId());
        req.setAttribute("preposeIds", plan.getPreposeIds());
        req.setAttribute("taskId", taskId);
        String str = (String)redisService.getFromRedis("PROBLEMTASKLIST", taskId);
        List<PlanDto> planlist = JSON.parseArray(str,PlanDto.class);
        String selectIds = "";
        if (!CommonUtil.isEmpty(planlist)) {
            for (PlanDto p1 : planlist) {
                if (CommonUtil.isEmpty(selectIds)) {
                    selectIds = p1.getId();
                }
                else {
                    selectIds = selectIds + "," + p1.getId();
                }

            }
        }
        req.setAttribute("selectIds", selectIds);

        return new ModelAndView("com/glaway/ids/project/plan/selectProblemTask");
    }

    /**
     * 计划前置选择页面初始化
     *
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagridForProblem")
    public void searchDatagridForProblem(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String projectId = request.getParameter("projectId");

        if (!StringUtil.isEmpty(planName)) {
            planName = URLDecoder.decode(planName, "UTF-8");
        }
        if (!StringUtil.isEmpty(planLevel)) {
            planLevel = URLDecoder.decode(planLevel, "UTF-8");
        }
        if (!StringUtil.isEmpty(bizCurrent)) {
            bizCurrent = URLDecoder.decode(bizCurrent, "UTF-8");
        } else {
            //默认加载执行中，已完工，完工确认的计划
            bizCurrent = RiskProblemsSearchStatus.PlanBizCurrent;
        }

        if (!StringUtil.isEmpty(userName)) {
            userName = URLDecoder.decode(userName, "UTF-8");
        }

        String planId = request.getParameter("planId");

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("conditionList",conditionList);
        map.put("projectId",projectId);
        map.put("userName",userName);
        map.put("progressRate","");
        map.put("workTime",workTime);

        PageList pageList = planService.queryEntity(map);
        List<PlanDto> planList = JSON.parseArray(JSON.toJSONString(pageList.getResultList()),PlanDto.class);
        List<PlanDto> tempList = new ArrayList<PlanDto>();

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO, userId, null);
        FeignJson teamFj = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = "";
        if (teamFj.isSuccess()) {
            teamId = teamFj.getObj() == null ? "" : teamFj.getObj().toString();
        }
        boolean isProjectManger = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(true);
        List<JSONObject> rootList = changePlansToJSONObjectsForProblem(tempList, parameVo,
                project, statusList,activityTypeManageList);

        /*
         * String resultJSON = JSON.toJSONString(rootList);
         * j.setSuccess(true);
         * j.setObj(resultJSON);
         * return j;
         */
        String resultJSON = JSON.toJSONString(rootList);

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
     * 判断父节点是否是拟制中
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "pdAssignAll")
    @ResponseBody
    public AjaxJson pdAssignAll(String ids, PlanDto plan, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String projectId = req.getParameter("projectId");
        plan.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        Map<String, PlanDto> plansMap = new HashMap<>();
        for (PlanDto p : list) {
            plansMap.put(p.getId(), p);
        }
        List<PlanDto> planList = new ArrayList<>();
        if(!CommonUtil.isEmpty(ids)){
            for (String id : ids.split(",")) {
                PlanDto pTemple = plansMap.get(id);
                if (PlanConstants.PLAN_FLOWSTATUS_ORDERED.equals(pTemple.getFlowStatus())) {}
                else if (PlanConstants.PLAN_ORDERED.equals(pTemple.getBizCurrent())) {}
                else {
                    planList.add(pTemple);
                }
            }
        }

        List<ResourceLinkInfoDto> allResourceList = new ArrayList<>();
        if (!CommonUtil.isEmpty(planList)) {
            allResourceList = planFlowForworkService.getResourceLinkInfosByProject(projectId);
        }

        if(!CommonUtil.isEmpty(planList)){
            for (PlanDto p : planList) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(p);

                if(!CommonUtil.isEmpty(preposePlanList)){
                    for (PreposePlanDto preposePlan : preposePlanList) {
                        PlanDto pPlan = planService.getPlanEntity(preposePlan.getPreposePlanId());
                        if (pPlan.getPlanEndTime().getTime() > p.getPlanStartTime().getTime()) {
                            j.setSuccess(false);
                            j.setMsg("【" + p.getPlanName() + "】开始时间不能早于其前置计划的结束时间");
                            return j;
                        }
                    }
                }


                if (p.getParentPlanId() != null) {
                    PlanDto planp = plansMap.get(p.getParentPlanId());
                    Boolean a = ids.contains(p.getParentPlanId());
                    if (planp.getBizCurrent() != null
                            && PlanConstants.PLAN_EDITING.equals(planp.getBizCurrent()) && !a) {
                        Object[] arguments = new String[] {planp.getPlanName()};
                        j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignMassOne",
                                arguments));
                        j.setSuccess(false);
                        return j;
                    }
                    else if (planp.getPlanStartTime() != null && p.getPlanEndTime() != null
                            && planp.getPlanEndTime() != null && p.getPlanStartTime() != null
                            && planp.getBizCurrent() != null
                            && !PlanConstants.PLAN_EDITING.equals(planp.getBizCurrent())) {
                        String time1 = sdf.format(p.getPlanStartTime());
                        String time2 = sdf.format(planp.getPlanStartTime());
                        String time3 = sdf.format(p.getPlanEndTime());
                        String time4 = sdf.format(planp.getPlanEndTime());
                        if ((Integer.parseInt(time2) > Integer.parseInt(time1))
                                || (Integer.parseInt(time3) > Integer.parseInt(time4))) {
                            Object[] arguments = new String[] {p.getPlanName(), planp.getPlanName(),
                                    sdf1.format(planp.getPlanStartTime()),
                                    sdf1.format(planp.getPlanEndTime())};
                            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignTwo",
                                    arguments));
                            j.setSuccess(false);
                            return j;
                        }
                    }

                    List<PlanDto> paramList = new ArrayList<>();
                    paramList.addAll(planList);
                    if (!childrenPlanCoverParent(paramList, req)) {
                        String planSonName = (String)req.getSession().getAttribute("planSonName");
                        Object[] arguments = new String[] {planSonName};
                        j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignThree",
                                arguments));
                        j.setSuccess(false);
                        return j;
                    }
                }
                List<ResourceLinkInfoDto> resourceList = new ArrayList<>();
                for (ResourceLinkInfoDto info : allResourceList) {
                    if (p.getId().equals(info.getUseObjectId())) {
                        resourceList.add(info);
                    }
                }
                if (p.getOwner() == null || (p.getOwner() != null && "".equals(p.getOwner()))) {
                    Object[] arguments = new String[] {p.getPlanName()};
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignFour",
                            arguments));
                    j.setSuccess(false);
                    return j;
                }
                if (resourceList != null && resourceList.size() > 0) {
                    for (int i = 0; i < resourceList.size(); i++ ) {
                        if (StringUtils.isEmpty(resourceList.get(i).getUseRate())) {
                            if (resourceList.get(i).getResourceInfo() != null) {
                                Object[] arguments = new String[] {p.getPlanName(),
                                        resourceList.get(i).getResourceInfo().getName()};
                                j.setMsg(I18nUtil.getValue(
                                        "com.glaway.ids.pm.project.plan.checkAssignFive", arguments));
                                j.setSuccess(false);
                            }
                            else {
                                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignSix"));
                                j.setSuccess(false);
                            }
                            break;
                        }
                        if (resourceList.get(i).getStartTime() == null) {
                            if (StringUtils.isEmpty(resourceList.get(i).getUseRate())) {
                                if (resourceList.get(i).getResourceInfo() != null) {
                                    Object[] arguments = new String[] {p.getPlanName(),
                                            resourceList.get(i).getResourceInfo().getName()};
                                    j.setMsg(I18nUtil.getValue(
                                            "com.glaway.ids.pm.project.plan.checkAssignSeven", arguments));
                                    j.setSuccess(false);
                                }
                                else {
                                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignEight"));
                                    j.setSuccess(false);
                                }
                                break;
                            }
                            if (resourceList.get(i).getEndTime() == null) {
                                if (resourceList.get(i).getResourceInfo() != null) {
                                    Object[] arguments = new String[] {p.getPlanName(),
                                            resourceList.get(i).getResourceInfo().getName()};
                                    j.setMsg(I18nUtil.getValue(
                                            "com.glaway.ids.pm.project.plan.checkAssignNine", arguments));
                                    j.setSuccess(false);
                                }
                                else {
                                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignTen"));
                                    j.setSuccess(false);
                                }
                                break;
                            }
                            if (resourceList.get(i).getStartTime() != null
                                    && resourceList.get(i).getEndTime() != null) {
                                String time1 = sdf.format(resourceList.get(i).getStartTime());
                                String time2 = sdf.format(resourceList.get(i).getEndTime());
                                String time3 = sdf.format(p.getPlanStartTime());
                                String time4 = sdf.format(p.getPlanEndTime());
                                if ((Integer.parseInt(time3) > Integer.parseInt(time1))
                                        || (Integer.parseInt(time2) > Integer.parseInt(time4))) {
                                    if (resourceList.get(i).getResourceInfo() != null) {
                                        Object[] arguments = new String[] {p.getPlanName(),
                                                resourceList.get(i).getResourceInfo().getName()};
                                        j.setMsg(I18nUtil.getValue(
                                                "com.glaway.ids.pm.project.plan.checkAssignEleven", arguments));
                                        j.setSuccess(false);
                                    }
                                    else {
                                        j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkAssignTweleve"));
                                        j.setSuccess(false);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return j;
    }

    /**
     * 判断子计划的输出是否覆盖父计划
     *
     * @param children
     * @return
     * @see
     */
    private boolean childrenPlanCoverParent(List<PlanDto> children, HttpServletRequest req) {
        for (PlanDto p : children) {
            p = planService.getPlanEntity(p.getId());
            if (StringUtils.isNotEmpty(p.getParentPlanId())) {
                DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(p.getParentPlanId());
                deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                        deliverablesInfo, 1, 10, false);
                if (!CommonUtil.isEmpty(deliverablesList)) {
                    Map<String, String> deliverablesMap = new HashMap<String, String>();

                    // 将p的同级且和p一起下达的计划的输出添加到deliverablesMap
                    List<PlanDto> sameParentPlanList = getSameParentPlanList(p, children);
                    for (PlanDto sameParent : sameParentPlanList) {
                        deliverablesInfo = new DeliverablesInfoDto();
                        deliverablesInfo.setUseObjectId(sameParent.getId());
                        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                        List<DeliverablesInfoDto> currentDeliverables = deliverablesInfoService.queryDeliverableList(
                                deliverablesInfo, 1, 10, false);
                        for (DeliverablesInfoDto deli : currentDeliverables) {
                            deliverablesMap.put(deli.getName(), deli.getName());
                        }
                    }

                    // 将p的父计划的所有非拟制中的子计划的输出添加到deliverablesMap
                    PlanDto parent = new PlanDto();
                    parent.setParentPlanId(p.getParentPlanId());
                    List<PlanDto> childList = planService.queryPlanList(parent, 1, 10, false);
                    for (PlanDto child : childList) {
                        if (!PlanConstants.PLAN_EDITING.equals(child.getBizCurrent())) {
                            deliverablesInfo = new DeliverablesInfoDto();
                            deliverablesInfo.setUseObjectId(child.getId());
                            deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                            List<DeliverablesInfoDto> childDeliverables = deliverablesInfoService.queryDeliverableList(
                                    deliverablesInfo, 1, 10, false);
                            for (DeliverablesInfoDto childDeli : childDeliverables) {
                                deliverablesMap.put(childDeli.getName(), childDeli.getName());
                            }
                        }
                    }
                    for (DeliverablesInfoDto parentDeli : deliverablesList) {
                        // 判断父级交付物书否被全部覆盖
                        if (StringUtils.isEmpty(deliverablesMap.get(parentDeli.getName()))) {
                            PlanDto pa = planService.getPlanEntity(p.getParentPlanId());
                            String planSonName = pa.getPlanName();
                            req.getSession().setAttribute("planSonName", planSonName);
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }

    /**
     * 返回和plan同属一个父级的list（包括plan）
     *
     * @param plan
     * @param plans
     * @return
     * @see
     */
    private List<PlanDto> getSameParentPlanList(PlanDto plan, List<PlanDto> plans) {
        List<PlanDto> list = new ArrayList<>();
        plan = planService.getPlanEntity(plan.getId());
        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            for (PlanDto p : plans) {
                p = planService.getPlanEntity(p.getId());
                if (!list.contains(p) && plan.getParentPlanId().equals(p.getParentPlanId())) {
                    list.add(p);
                }
            }
        }
        return list;
    }

    /**
     * 项目计划批量下达选人页面跳转
     *
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanMassUser")
    public ModelAndView goAssignPlanOnTreeMass(HttpServletRequest req) {
        return new ModelAndView("com/glaway/ids/project/plan/planListGrantMassUser");
    }


    /**
     * 通过导入计划模板到计划
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "savePlanByPlanTemplateId")
    @ResponseBody
    public AjaxJson savePlanByPlanTemplateId(PlanTemplateDto planTemplate,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        PlanDto plan = new PlanDto();
        String type = request.getParameter("type");
        String planId = request.getParameter("planId");
        String projectId = request.getParameter("projectId");
        String parentPlanId = request.getParameter("parentPlanId");
        String planTemplateId = request.getParameter("planTemplateId");
        String currentUserId = request.getParameter("currentUserId");
        String message = "";
        // 如果是下方导入计划
        if (StringUtils.isNotEmpty(type)) {
            plan.setId(planId);
            plan.setParentPlanId(((PlanDto)planService.getPlanEntity(planId)).getParentPlanId());
        }
        // 根据parentPlanId是否为空判断是导入计划还是导入子计划
        if (StringUtils.isNotEmpty(parentPlanId) && !parentPlanId.equals("undefined")) {
            request.setAttribute("parentPlanId", parentPlanId);
            plan.setParentPlanId(parentPlanId);
        }

        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdSuccess");
        try {
            if (StringUtils.isNotEmpty(planTemplateId)) {
                PlanTemplateDetailReq planTemplateDetailReq = new PlanTemplateDetailReq();
                planTemplateDetailReq.setPlanId(planId);
                planTemplateDetailReq.setPlanTemplateId(planTemplateId);
                planTemplateDetailReq.setProjectNumber(projectId);
                planTemplateDetailReq.setCreateBy(UserUtil.getInstance().getUser().getId());
                // 如果是下方导入计划
                if (StringUtils.isNotEmpty(type)) {
                    planTemplateDetailReq.setUpPlanId(planId);
                }
                // 如果是有计划来源
                if (StringUtils.isNotEmpty(request.getParameter("planSource"))) {
                    planTemplateDetailReq.setPlanSource(request.getParameter("planSource"));
                }
                if(CommonUtil.isEmpty(planId)){
                    planId = "";
                }
                if(CommonUtil.isEmpty(type)){
                    type = "";
                }
                FeignJson fj = planService.savePlanByPlanTemplateId(planTemplateDetailReq, planId, type,
                        currentUserId,ResourceUtil.getCurrentUserOrg().getId());
                if(fj.isSuccess()){
                    j.setSuccess(true);
                    j.setMsg(message);
                }else{
                    j.setSuccess(false);
                    j.setMsg(fj.getMsg());
                }
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.savePlanByPlanTemplateIdFail");
                j.setMsg(message);
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            j.setMsg(message);
            j.setSuccess(false);
            // throw new GWException(e);
        }
        finally {
         //   j.setMsg(message);
            return j;
        }
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
        String projectId = request.getParameter("projectId");
        List<PlanDto> planLst = planService.exportXls(plan,projectId);
        response.setContentType("application/vnd.ms-excel");
        fileDownLoad(response, planLst, null, "true");
    }

    /**
     * 关联的计划
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planList")
    public void planList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        plan.setProjectId(projectId);

        List<PlanDto> planList = planService.queryPlanList(plan, 1, 10, false);

        List<JSONObject> rootList = planService.changePlansToJSONObjects(planList);

        String resultJSON = JSON.toJSONString(rootList);

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
     * 导出Excel
     *
     * @return
     */
    private void fileDownLoad(HttpServletResponse response, List<PlanDto> dataList, Map<String, String> errorMsgMap, String isExport) {
        String title = PlanConstants.EXPORT_PLAN_NAME;
        String excelName = POIExcelUtil.createExcelName(isExport, title, errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        String attention = "注意事项：前置计划序号、交付项名称为多个时，通过英文半角逗号隔开；备注说明不能超过200个中文字符";

        List<String> columns = new ArrayList<String>();
        if(bExport) {
            columns = Arrays.asList(new String[] {"planNumber:编号", "parentPlanNo:父计划编号", "planName:计划名称","taskNameType:计划类型", "ownerRealName:负责人",
                    "planLevelName:计划等级", "workTime:工期（天）", "milestoneName:里程碑", "planStartTime:开始时间", "planEndTime:结束时间",
                    "preposeNos:前置计划编号", "deliverablesName:交付项名称", "remark:备注说明"});
        } else {
            columns = Arrays.asList(new String[] {"planNumber:序号", "parentPlanNo:父计划序号", "planName:计划名称", "taskNameType:计划类型","ownerRealName:负责人",
                    "planLevelName:计划等级", "milestoneName:里程碑", "planStartTime:开始时间", "planEndTime:结束时间",
                    "preposeNos:前置计划序号", "deliverablesName:交付项名称", "remark:备注说明"});
        }

        List<String> requiredHeaders = Arrays.asList(new String[] {"序号", "计划名称", "计划类型", "负责人", "里程碑", "开始时间"});
        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        List<String> validationDataList = new ArrayList<String>();
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
        for(BusinessConfig c : planLevelList) {
            if (c.getStopFlag()!=null&&"启用".equals(c.getStopFlag().trim())) {
                validationDataList.add(c.getName());
            }
        }

        List<ActivityTypeManageDto> allActivityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(false);
        List<String> validationDataList3 = new ArrayList<String>();
        for (ActivityTypeManageDto dto: allActivityTypeManageList){
            validationDataList3.add(dto.getName());
        }

        List<String> validationDataList2 = new ArrayList<String>();
        validationDataList2.add("是");
        validationDataList2.add("否");

        validationDataMap.put("planLevelName", validationDataList);
        validationDataMap.put("milestoneName", validationDataList2);
        validationDataMap.put("taskNameType", validationDataList3);

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
     * 导出计划excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsSingle")
    public void exportXlsSingle(PlanDto plan, HttpServletRequest request, HttpServletResponse response,
                                DataGrid dataGrid) {
        response.setContentType("application/vnd.ms-excel");
        String planId = request.getParameter("planId");
        plan.setId(planId);
        List<PlanDto> planLst = planService.exportXlsSingle(plan);
        fileDownLoad(response, planLst, null, "true");
    }

    /**
     * Description: <br>
     * 导出计划MPP
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @see
     */
    @RequestMapping(params = "exportPlan")
    @ResponseBody
    public void exportPlan(PlanDto plan, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 获得传入的参数
        response.setContentType("application/octet-stream");
        String codedFileName = null;
        OutputStream fOut = null;

        codedFileName = PlanConstants.EXPORT_PLAN_NAME;
        if (BrowserUtils.isIE(request)) {
            response.setHeader("content-disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(codedFileName, "UTF-8")
                            + ".xml");
        }
        else {
            String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("content-disposition", "attachment;filename=" + newtitle + ".xml");
        }
        String projectId = request.getParameter("projectId");
        if (StringUtils.isNotEmpty(projectId)) {
            plan.setProjectId(projectId);
        }
        List<PlanDto> plans = planService.getPlanAllChildrenByProjectId(plan.getProjectId());
        List<MppInfo> mppList = planService.saveMppInfo(plans);

        MppDirector mppDirector = new MppDirector();
        Project project = projectService.getProjectEntity(projectId);
        String type = "";
        if (ProjectConstants.NATURALDAY.equals(project.getProjectTimeType())) {
            type = "自然日";
        }
        else if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
            type = "工作日";
        }
        else {
            type = "公司日";
        }
        ProjectFile projectFile = mppDirector.getMppFile(MppConstants.IDS_PLANTEMPLATE_MPP,
                mppList, type);
        ProjectWriter writer = new MSPDIWriter();
        writer.write(projectFile, response.getOutputStream());
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
            log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plan.exportMppSuccess"));
        }
    }

    /**
     * Description: <br>
     * 导出计划MPP
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @see
     */
    @RequestMapping(params = "exportPlanSingle")
    @ResponseBody
    public void exportPlanSingle(PlanDto plan, HttpServletRequest request,
                                 HttpServletResponse response)
            throws IOException {
        // 获得传入的参数
        response.setContentType("application/octet-stream");
        String codedFileName = null;
        OutputStream fOut = null;
        String planIdForMpp = request.getParameter("planIdForMpp");
        codedFileName = PlanConstants.EXPORT_PLAN_NAME;
        if (BrowserUtils.isIE(request)) {
            response.setHeader("content-disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(codedFileName, "UTF-8")
                            + ".xml");
        }
        else {
            String newtitle = new String(codedFileName.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("content-disposition", "attachment;filename=" + newtitle + ".xml");
        }
        plan.setId(planIdForMpp);
        List<PlanDto> plans = this.planService.getPlanAllChildren(plan);
        List<MppInfo> mppList = planService.saveMppInfo(plans);

        MppDirector mppDirector = new MppDirector();
        Project project = projectService.getProjectEntity(plans.get(0).getProjectId());
        String type = "";
        if (ProjectConstants.NATURALDAY.equals(project.getProjectTimeType())) {
            type = "自然日";
        }
        else if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
            type = "工作日";
        }
        else {
            type = "公司日";
        }
        ProjectFile projectFile = mppDirector.getMppFile(MppConstants.IDS_PLANTEMPLATE_MPP,
                mppList, type);
        ProjectWriter writer = new MSPDIWriter();
        writer.write(projectFile, response.getOutputStream());
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
            log.info(I18nUtil.getValue("com.glaway.ids.pm.project.plan.exportMppSuccess"));
        }
    }

    /**
     * 导入计划\子计划页面跳转（EXCEL）
     *
     * @param parentPlanId
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goImportExcel")
    public ModelAndView goImportExcel(String parentPlanId, HttpServletRequest req) {
        String fmsBpmPath = "";
        List<ServiceInstance> inss = discoveryClient.getInstances("IDS-PM-SERVICE-96");
        if (!CommonUtil.isEmpty(inss)) {
            EurekaDiscoveryClient.EurekaServiceInstance serviceIns = (EurekaDiscoveryClient.EurekaServiceInstance) inss.get(0);
            fmsBpmPath = serviceIns.getInstanceInfo().getInstanceId().toString();
        }
        req.setAttribute("serviceName",fmsBpmPath);
        req.setAttribute("userId",UserUtil.getCurrentUser().getId());


        String currentUserId = req.getParameter("currentUserId");
        PlanDto plan = new PlanDto();
        // 如果是下方导入计划
        if (StringUtils.isNotEmpty(req.getParameter("type"))) {
            req.setAttribute("type", req.getParameter("type"));
            plan.setId(req.getParameter("planId"));
            plan.setParentPlanId((planService.getPlanEntity(
                    req.getParameter("planId"))).getParentPlanId());
        }
        // 根据parentPlanId是否为空判断是导入计划还是导入子计划
        if (StringUtils.isNotEmpty(parentPlanId)) {
            req.setAttribute("parentPlanId", parentPlanId);
            plan.setParentPlanId(parentPlanId);
        }

        plan.setProjectId(req.getParameter("projectId"));
        req.setAttribute("currentUserId", currentUserId);
        req.setAttribute("plan_", plan);
        return new ModelAndView("com/glaway/ids/project/plan/plan-importExcel");
    }


    /**
     * Description: <br>
     * 下载模板
     *
     * @see
     */
    @RequestMapping(params = "doDownloadExcelTemplate")
    public void doDownloadExcelTemplate(HttpServletRequest request, HttpServletResponse response) throws FdException, Exception {
        String isExport=request.getParameter("isExport");
        List<PlanDto> planList = new ArrayList<PlanDto>();
        //示例数据
        PlanDto plan = new PlanDto();
        plan.setOwnerRealName(UserUtil.getCurrentUser().getRealName() + "-" + UserUtil.getCurrentUser().getUserName());
        plan.setPlanStartTime(DateUtil.getDate());
        planList.add(plan);
        fileDownLoad(response, planList, null, isExport);
    }

    /**
     * Description: <br>
     * 导入计划(EXCEL)
     *
     * @see
     * @author lky
     */
    @RequestMapping(params = "doImportExcel")
    @ResponseBody
    public AjaxJson doImportExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        String userId = request.getParameter("userId");
        String type = request.getParameter("type");
        String planId = request.getParameter("planId");
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.importExcel.success");
        TSUserDto userDto = userService.getUserByUserId(userId);
        try {
            String typeName = "项目计划";
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                //存储导入的数据
                List<PlanExcelSaveVo> dataTempList = new ArrayList<PlanExcelSaveVo>();
                //存储错误信息
                Map<String, String> errorMsgMap = new HashMap<String, String>();
                // 获取上传文件对象
                MultipartFile file = entity.getValue();
                InputStream is = file.getInputStream();
                Workbook book = WorkbookFactory.create(is);
                Sheet sheet = book.getSheetAt(0);
                List<String> headers = Arrays.asList(new String[]{"序号", "父计划序号", "计划名称", "计划类型", "负责人", "计划等级",
                    "里程碑", "开始时间", "结束时间", "前置计划序号", "交付项名称", "备注说明"});
                if (!POIExcelUtil.doJudgeImport(sheet, headers)) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.errorTemplate") + ";");
                    j.setSuccess(false);
                    log.error(typeName + "：导入失败，模板有问题");
                    book.close();
                    is.close();
                    return j;
                } else {
                    List<Map<String,String>> map= getDataList(sheet);
                    String currectUserId = UserUtil.getInstance().getUser().getId();
                    FeignJson feignJson = planService.doImportExcel(currectUserId,projectId, type, planId, map);
                    Map<String,Object> returnMap = new HashMap<>();
                    if (feignJson.isSuccess()){
                        returnMap = (Map<String, Object>)feignJson.getObj();
                        errorMsgMap = (Map<String, String>)returnMap.get("errorMsgMap");
                        dataTempList = (List<PlanExcelSaveVo>)returnMap.get("dataTempList");
                    }
                    if (0 < errorMsgMap.size()) {
                        j.setMsg(I18nUtil.getValue("com.glaway.ids.common.importFailure.invalidData"));
                        j.setSuccess(false);
                        log.error(typeName + "：批量导入失败，部分数据无效");
                        //组装数据传到前台
                        Map<String, Object> dataAndErrorMap = new HashMap<String, Object>();
                        dataAndErrorMap.put("dataTempList", dataTempList);
                        request.getSession().setAttribute(userDto.getUserName() + "_planImportErrTmpList", dataTempList);
                        request.getSession().setAttribute(userDto.getUserName() + "_planImportErrorMsgMapList", errorMsgMap);
                        dataAndErrorMap.put("errorMsgMap", errorMsgMap);
                   //     request.getSession().setAttribute(userDto.getUserName() + "_planImportErrorDataAndError", dataAndErrorMap);
                 //       j.setObj(dataAndErrorMap);
                        book.close();
                        is.close();
                        return j;
                    } else {
                        log.info(message);
                    }
                }
            }
        } catch (Exception e) {
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
                Cell ownerCell = row.getCell(4);
                Cell levelCell = row.getCell(5);
                Cell milestoneCell = row.getCell(6);
                Cell startTimeCell = row.getCell(7);
                Cell endTimeCell = row.getCell(8);
                Cell preposeNumberCell = row.getCell(9);
                Cell deliverNameCell = row.getCell(10);
                Cell remarkCell = row.getCell(11);
                String number = POIExcelUtil.getCellValue(numberCell).trim();
                String parentNumber = POIExcelUtil.getCellValue(parentNumberCell).trim();
                String taskNameType = POIExcelUtil.getCellValue(taskNameTypeCell).trim();
                String name = POIExcelUtil.getCellValue(nameCell).trim();
                String owner = POIExcelUtil.getCellValue(ownerCell).trim();
                String level = POIExcelUtil.getCellValue(levelCell).trim();
                String milestone = POIExcelUtil.getCellValue(milestoneCell).trim();
                String startTime = getCellValue(startTimeCell).trim();
                String endTime = getCellValue(endTimeCell).trim();
                String preposeNumbers = POIExcelUtil.getCellValue(preposeNumberCell).trim();
                preposeNumbers = preposeNumbers.replace("，", ",");
                String deliverName = POIExcelUtil.getCellValue(deliverNameCell).trim();
                deliverName = deliverName.replace("，", ",");
                String remark = POIExcelUtil.getCellValue(remarkCell).trim();
                map.put("number", number);
                map.put("parentNumber", parentNumber);
                map.put("taskNameType", taskNameType);
                map.put("name", name);
                map.put("owner", owner);
                map.put("level",level);
                map.put("milestone", milestone);
                map.put("startTime", startTime);
                map.put("endTime", endTime);
                map.put("preposeNumbers", preposeNumbers);
                map.put("deliverName", deliverName);
                map.put("remark", remark);
                list.add(map);
            }
        }
        return list;
    }



        /**
         * 获取单元格值
         *
         * @param cell
         * @return
         * @see
         */
        private String getCellValue(Cell cell) {
            String value = "";
            if (cell != null) {// 判断Cell格式
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue().trim();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            if (date != null) {
                                value = new SimpleDateFormat("yyyy/MM/dd").format(date);
                            }
                            else {
                                value = "";
                            }
                        }
                        else {
                            value = new DecimalFormat("0").format(cell.getNumericCellValue());
                        }
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        value = "";
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = (cell.getBooleanCellValue() ? "Y" : "N");
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = cell.getStringCellValue().trim();
                        break;
                    default:
                        value = "";
                }
            }
            return value;
        }


    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    private Date stringtoDate(String dateStr, String format, int rowNum, Map<String, String> errorMsgMap,
                              String message) {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            }
            catch (ParseException e) {
                POIExcelUtil.addErrorMsg(rowNum, message, errorMsgMap);
            }
        }
        return date;
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
        List<JSONObject> objList = new ArrayList<JSONObject>();
        //转换时间格式
        if(!CommonUtil.isEmpty(request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planImportErrTmpList"))) {//if(!CommonUtil.isEmpty(jsonMap)) {
//            objList = (List<JSONObject>)jsonMap.get("dataTempList");
            List<JSONObject> dataTempList = (List<JSONObject>)request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planImportErrTmpList");
   /*         if(!CommonUtil.isEmpty(objList) && objList.size()!=0){
                for (JSONObject jsonObject : objList) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    if(!CommonUtil.isEmpty(jsonObject.get("planStartTime"))) {
                        String planStartTime = jsonObject.get("planStartTime").toString();
                        Date date = new Date(Long.parseLong(planStartTime));
                        jsonObject.replace("planStartTime", sdf.format(date));
                    }
                    if(!CommonUtil.isEmpty(jsonObject.get("planEndTime"))) {
                        String planEndTime = jsonObject.get("planEndTime").toString();
                        Date date2 = new Date(Long.parseLong(planEndTime));
                        jsonObject.replace("planEndTime", sdf.format(date2));
                    }
                }
            }*/
//            Map<String, String> errorMsgMap = (Map<String, String>)jsonMap.get("errorMsgMap");
            Map<String, String> errorMsgMap = (Map<String, String>)request.getSession().getAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planImportErrorMsgMapList");;
            fileDownLoadError(response, dataTempList, errorMsgMap, "error");
            request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planImportErrTmpList","");
            request.getSession().setAttribute(ResourceUtil.getCurrentUser().getUserName()+"_planImportErrorMsgMapList","");
        }

    }

    /**
     * 导出Excel错误 (包含下载模板)
     *
     * @return
     */
    private void fileDownLoadError(HttpServletResponse response, List<JSONObject> dataList, Map<String, String> errorMsgMap, String isExport) {
        String title = PlanConstants.EXPORT_PLAN_NAME;
        String excelName = POIExcelUtil.createExcelName(isExport, title, errorMsgMap);

        boolean bExport = false;
        if ("true".equals(isExport)) {
            bExport = true;
        }

        String attention = "注意事项：前置计划序号、交付项名称为多个时，通过英文半角逗号隔开；备注说明不能超过200个中文字符";
        List<String> columns = new ArrayList<String>();
        columns = Arrays.asList(new String[] {"planNumber:序号", "parentPlanNo:父计划序号", "planName:计划名称", "taskNameType:计划类型","ownerRealName:负责人",
                "planLevelName:计划等级", "milestoneName:里程碑", "planStartTime:开始时间", "planEndTime:结束时间",
                "preposeNos:前置计划序号", "deliverablesName:交付项名称", "remark:备注说明"});

        List<String> requiredHeaders = Arrays.asList(new String[] {"序号", "计划名称", "计划类型","负责人", "里程碑", "开始时间"});
        Map<String, List<String>> validationDataMap = new HashMap<String, List<String>>();

        List<String> validationDataList = new ArrayList<String>();
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
        for(BusinessConfig c : planLevelList) {
            validationDataList.add(c.getName());
        }

        List<String> validationDataList2 = new ArrayList<String>();
        List<String> validationDataList3 = tabCbTemplateFeignService.queryAllName();
        validationDataList2.add("是");
        validationDataList2.add("否");

        validationDataMap.put("planLevelName", validationDataList);
        validationDataMap.put("milestoneName", validationDataList2);
        validationDataMap.put("taskNameType", validationDataList3);

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
     * 判断父计划的输出是否被子计划全部覆盖
     * 
     * @param parent
     * @return
     * @see
     */
    @RequestMapping(params = "parentPlanOutCover")
    @ResponseBody
    private AjaxJson parentPlanOutCover(PlanDto parent) {
        AjaxJson j = new AjaxJson();
        j.setObj(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.parentPlanOutCoverSuccess");
        // 获取父计划的输出
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(parent.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);
        if (!CommonUtil.isEmpty(deliverablesList)) {

            // 获取其子计划的所有输出、并将其存入deliverablesMap
            Map<String, String> deliverablesMap = new HashMap<String, String>();
            PlanDto p = new PlanDto();
            p.setParentPlanId(parent.getId());
            List<PlanDto> childList = planService.queryPlanList(p, 1, 10, false);
            for (PlanDto child : childList) {
                deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(child.getId());
                deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                List<DeliverablesInfoDto> childDeliverables = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);
                for (DeliverablesInfoDto childDeli : childDeliverables) {
                    deliverablesMap.put(childDeli.getName(), childDeli.getName());
                }
            }

            // 判断父计划输出是否被全部覆盖
            for (DeliverablesInfoDto parentDeli : deliverablesList) {
                if (StringUtils.isEmpty(deliverablesMap.get(parentDeli.getName()))) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.parentPlanOutCoverFail");
                    j.setObj(false);
                }
            }
        }
        j.setMsg(message);
        return j;
    }



    @RequestMapping(params = "goAddPlanForGeneralPage")
    public ModelAndView goAddPlanForGeneralPage(HttpServletRequest request){
        request.setAttribute("projectId",request.getParameter("projectId"));
        request.setAttribute("isPmo",request.getParameter("isPmo"));
        request.setAttribute("isProjectManger",request.getParameter("isProjectManger"));
        request.setAttribute("teamId",request.getParameter("teamId"));
        request.setAttribute("beforePlanId",request.getParameter("beforePlanId"));
        request.setAttribute("parentPlanId",request.getParameter("parentPlanId"));
        FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(feignJson.getObj());
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            request.setAttribute("isStandard", true);
        }
        else {
            request.setAttribute("isStandard", false);
        }

        if(NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr) || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)){
            request.setAttribute("isForce",true);
        }else{
            request.setAttribute("isForce",false);
        }
        return new ModelAndView(
                "com/glaway/ids/project/plan/planGeneralAddBefore");
    }


    @RequestMapping(params = "checkTabCombinationTemplateIsExist")
    @ResponseBody
    public AjaxJson checkTabCombinationTemplateIsExist(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        try{
            String activityId = request.getParameter("activityId");
            String planId = request.getParameter("planId");
            List<TabCombinationTemplateDto> list = new ArrayList<>();
            if(!CommonUtil.isEmpty(activityId)){
                list = tabCombinationTemplateFeignService.findTabCbTempByActivityId(activityId);
            }else{
                PlanDto planDto = planService.getPlanEntity(planId);
                list = tabCombinationTemplateFeignService.findTabCbTempByActivityId(planDto.getTaskNameType());
            }

            if(CommonUtil.isEmpty(list)){
                j.setSuccess(false);
            } else {
                j.setObj(list.get(0).getId());
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }



    @RequestMapping(params = "loadPlanAddInfo")
    public ModelAndView loadPlanAddInfo(PlanDto plan, HttpServletRequest req){

        String fromType = req.getParameter("fromType");
        req.setAttribute("fromType",fromType);

        String tabCbTemplateId = req.getParameter("tabCbTemplateId");
        req.setAttribute("tabCbTemplateId",tabCbTemplateId);

        String activityId = "";

        List<ActivityTypeManageDto> types = activityTypeManage.getAllActivityTypeManage(false);

        if(!"update".equals(fromType)){
            String projectId=req.getParameter("projectId");
            req.setAttribute("projectId",projectId);
            req.setAttribute("isPmo",req.getParameter("isPmo"));
            req.setAttribute("isProjectManger",req.getParameter("isProjectManger"));
            req.setAttribute("teamId",req.getParameter("teamId"));
            activityId = req.getParameter("activityId");

            boolean isPmo = false;
            boolean isProjectManger = false;
            String isPmoPd = req.getParameter("isPmo");
            String isProjectMangerPd = req.getParameter("isProjectManger");
            String teamId = req.getParameter("teamId");
            String beforePlanId = req.getParameter("beforePlanId");
            String parentPlanId = req.getParameter("parentPlanId");
            if (isPmoPd.equals("true")) {
                isPmo = true;
            }
            if (isProjectMangerPd.equals("true")) {
                isProjectManger = true;
            }
            plan.setBeforePlanId(beforePlanId);
            plan.setParentPlanId(parentPlanId);
            // boolean isPmo = roleUserService.judgeHasLimit(ProjectRoleConstants.PMO, userId, null);
            // String teamId = projRoleService.getTeamIdByProjectId(plan.getProjectId());
            /*
             * boolean isProjectManger =
             * roleUserService.judgeHasLimit(ProjectRoleConstants.PROJ_MANAGER,
             * userId, teamId);
             */
            String projectIdForAdd = req.getParameter("projectId");
            Project project = new Project();
            Date parentStartTime = new Date();
            Date parentEndTime = new Date();
            // 新建计划子计划时随机生成计划的UUID
            plan.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
            plan.setBizCurrent(PlanConstants.PLAN_EDITING);
            plan.setCreateBy(ResourceUtil.getCurrentUser().getId());
            plan.setCreateName(ResourceUtil.getCurrentUser().getUserName());
            plan.setCreateFullName(ResourceUtil.getCurrentUser().getRealName());
            plan.setCreateTime(new Date());

            // 责任人List取值
            List<TSUserDto> userList = new ArrayList<TSUserDto>();
            if (StringUtils.isNotEmpty(projectIdForAdd)) {
                project = projectService.getProjectEntity(projectIdForAdd);
                /*project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
                if (project != null) {
                    plan.setProject(project);
                    parentStartTime = project.getStartProjectTime();
                    parentEndTime = project.getEndProjectTime();
                    List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                    for (TSUserDto r : users) {
                        if (r != null) {
                            TSUserDto o = userService.getUserByUserId(r.getId());
                            if (o != null) {
                                userList.add(o);
                            }
                        }
                    }
                }
            }

            // 根据parentPlanId是否为空判断是创建计划还是创建子计划
            // 给计划类别设置初始值
            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                parentStartTime = parent.getPlanStartTime();
                parentEndTime = parent.getPlanEndTime();
                String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
                String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");
                req.setAttribute("planStartTimeRm", parentStartTimeStr);
                req.setAttribute("planEndTimeRm", parentEndTimeStr);

                if (parent == null) {
                    /*if (isPmo || isProjectManger) {
                        plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                    }
                    else {
                        plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                    }*/
                   if(PlanConstants.PLAN_TYPE_TASK.equals(parent.getTaskType())){
                       plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                   }else{
                       plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                   }
                }
                else {
                    if(PlanConstants.PLAN_TYPE_TASK.equals(parent.getTaskType())){
                        plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                    }else{
                        plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                    }
                }

            }
            else {
               /* if (isPmo || isProjectManger) {
                    plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                }
                else {
                    plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                }*/
                if("4028f00c6cefbdc9016cefcd30c00007".equals(activityId)){   //任务4937
                    plan.setTaskType(PlanConstants.PLAN_TYPE_WBS);
                }else{
                    plan.setTaskType(PlanConstants.PLAN_TYPE_TASK);
                }
            }
            plan.setPlanType(plan.getTaskType());

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

            if (project != null && StringUtils.isNotEmpty(project.getProjectTimeType())) {
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

            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                plan.setPlanStartTime(parent.getPlanStartTime());
                Date date = (Date)plan.getPlanStartTime().clone();
                plan.setPlanEndTime(DateUtil.nextWorkDay(date, 0));
                req.setAttribute("planStartTimeRm", plan.getPlanStartTime());
                req.setAttribute("planEndTimeRm", plan.getPlanEndTime());
            }

            plan.setMilestone("");

            // 责任人对应的责任部门list取值
            Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");
            JSONArray deptList = new JSONArray();
            JSONObject obj = null;
            if (!CommonUtil.isEmpty(userList)) {
                for (TSUserDto user : userList) {
                    obj = new JSONObject();
                    obj.put("userId", user.getId());
                    // 部门为空判断
                    List<TSDepartDto> departList = departMap.get(user.getId());
                    String departName = "";
                    if (!CommonUtil.isEmpty(departList)) {
                        for(TSDepartDto departDto : departList){
                            if(CommonUtil.isEmpty(departName)){
                                departName = departDto.getDepartname();
                            }else{
                                departName = departName+","+departDto.getDepartname();
                            }
                        }
                        obj.put("departname", departName);
                    }
                    else {
                        obj.put("departname", "");
                    }

                    deptList.add(obj);
                }
            }

            // 计划等级
            BusinessConfig planLevel = new BusinessConfig();
            planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
            planLevel.setStopFlag(ConfigStateConstants.START);
            planLevel.setAvaliable("1");
            List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);

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



            // 给计划类型设置初始值
            if (!CommonUtil.isEmpty(types)) {
                plan.setTaskNameType(types.get(0).getName());
            }


            plan.setTaskNameType(activityId);

            String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
            req.setAttribute("userList2", jonStr2);
            String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
            req.setAttribute("planLevelList", jonStr3);
            req.setAttribute("userList", userList);

            req.setAttribute("ownerShow", "");
            req.setAttribute("planLevelShow", "");
            req.getSession().setAttribute("peojectIdForAdd", projectIdForAdd);
            String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
            String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");
            req.setAttribute("parentStartTime", parentStartTimeStr);
            req.setAttribute("parentEndTime", parentEndTimeStr);
            req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
            // req.setAttribute("planLevelList", planLevelList);
            req.setAttribute("disabled", "false");
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
               /* PlanDto statusP = new PlanDto();
                planService.initBusinessObject(statusP);*/
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
                List<LifeCycleStatus> statusList =  JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
                for (LifeCycleStatus status : statusList) {
                    if (status.getName().equals(plan.getBizCurrent())) {
                        plan.setStatus(status.getTitle());
                        break;
                    }
                }
            }
            req.setAttribute("plan_", plan);
            req.setAttribute("useObjectId", plan.getId());
            req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
            req.setAttribute("projectId", projectIdForAdd);

            redisService.setToRedis("INPUTSLIST", plan.getId(), null);

            req.getSession().setAttribute("resourceListForPlan", new ArrayList<ResourceLinkInfoDto>());

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

            short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
       //     List<TSTypeDto> curType = tsMap.get(dictCode);
            List<Object> list = new ArrayList<Object>();
            // this.setTypeList(list);
            if(!CommonUtil.isEmpty(types)){
                req.setAttribute("fileSecurityLevel", types.get(0).getId());
            }


            ProjLibDocumentVo vo = new ProjLibDocumentVo();
            vo.setProjectId(projectIdForAdd);
            vo.setPath(docNamePath);
            vo.setParentId(parentVo.getId());
            req.setAttribute("folderId", folderId);
            req.setAttribute("doc", vo);
            req.setAttribute("yanfa", "yanfa");
        }else{
            Date parentStartTime = new Date();
            Date parentEndTime = new Date();

            plan = planService.getPlanEntity(plan.getId());
            activityId = plan.getTaskNameType();
            if(!CommonUtil.isEmpty(plan.getOwner())){
                TSUserDto userDto = userService.getUserByUserId(plan.getOwner());
                plan.setOwnerInfo(userDto);
            }
            List<TSUserDto> userList = new ArrayList<>();
            if (StringUtils.isNotEmpty(plan.getProjectId())) {
                Project project = projectService.getProjectEntity(plan.getProjectId());
                parentStartTime = project.getStartProjectTime();
                parentEndTime = project.getEndProjectTime();
                List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                for (int i = 0; i < users.size(); i++ ) {
                    TSUserDto o = userService.getUserByUserId(users.get(i).getId());
                    userList.add(o);
                }
            }

            String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
            String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");

            // 根据parentPlanId是否为空判断是创建计划还是创建子计划
            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                parentStartTime = parent.getPlanStartTime();
                parentEndTime = parent.getPlanEndTime();
                req.setAttribute("planStartTimeRm", parentStartTimeStr);
                req.setAttribute("planEndTimeRm", parentEndTimeStr);
            }

            // userlist重新排序
//        List<TSUserDto> userListTemp = userService.queryUserList();
            Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");
            List<TSUserDto> userListTemp = userService.getAllUsers();
            if (plan.getOwner() != null) {
//            TSUserDto o = userService.getEntity(TSUser.class, plan.getOwner());
                TSUserDto o  = userService.getUserByUserId(plan.getOwner());
                if (o != null) {
                    List<TSDepartDto> departDtoList = departMap.get(plan.getOwner());
                    String depName = "";
                    if (!CommonUtil.isEmpty(departDtoList)) {
                        for(TSDepartDto dep : departDtoList){
                            if(CommonUtil.isEmpty(depName)){
                                depName = dep.getDepartname();
                            }else {
                                depName = depName +","+ dep.getDepartname();
                            }
                        }
                        plan.setOwnerDept(depName);
                    }
                    userList.add(o);
                    for (int i = 0; i < userListTemp.size(); i++ ) {
                        if (!o.getId().equals(userListTemp.get(i).getId())) {
                            userList.add(userListTemp.get(i));
                        }
                    }
                }
            }
            else {
                userList.addAll(userListTemp);
            }

            JSONObject obj = null;
            // 责任人对应的责任部门list取值

            JSONArray deptList = new JSONArray();
            if (!CommonUtil.isEmpty(userList)) {
                for (TSUserDto user : userList) {
                    obj = new JSONObject();
                    obj.put("userId", user.getId());
                    // 部门为空判断
                    List<TSDepartDto> departList = departMap.get(user.getId());
                    String departName = "";
                    if (!CommonUtil.isEmpty(departList)) {
                        for(TSDepartDto departDto : departList){
                            if(CommonUtil.isEmpty(departName)){
                                departName = departDto.getDepartname();
                            }else{
                                departName = departName+","+departDto.getDepartname();
                            }
                        }
                        obj.put("departname", departName);
                    }
                    else {
                        obj.put("departname", "");
                    }

                    deptList.add(obj);
                }
            }


            List<BusinessConfig> planLevelList = new ArrayList<BusinessConfig>();
            BusinessConfig planLevel = new BusinessConfig();
            planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
//        List<BusinessConfig> planLevelListTemp = businessConfigService.searchUseableBusinessConfigs(planLevel);

            planLevel.setStopFlag(ConfigStateConstants.START);
            planLevel.setAvaliable("1");
            List<BusinessConfig> planLevelListTemp = businessConfigService.searchBusinessConfigs(planLevel);

            // 计划等级重新排序
            if (plan.getPlanLevel() != null) {
                BusinessConfig b = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                if (b != null) {
                    planLevelList.add(b);
                    for (int i = 0; i < planLevelListTemp.size(); i++ ) {
                        if (!b.getId().equals(planLevelListTemp.get(i).getId())) {
                            planLevelList.add(planLevelListTemp.get(i));
                        }
                    }
                }
            }

            // 根据parentPlanId是否为空判断是创建计划还是创建子计划
            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                parentStartTime = parent.getPlanStartTime();
                parentEndTime = parent.getPlanEndTime();
                req.setAttribute("planStartTimeRm", parentStartTimeStr);
                req.setAttribute("planEndTimeRm", parentEndTimeStr);
            }

            // 如果父级开始时间晚于当前时间，则计划开始时间取父级开始时间
            if (parentStartTime.getTime() > (new Date()).getTime()) {
                req.setAttribute("planStartTimeRm", parentStartTimeStr);
            }
            // 如果父级开始时间早于等于当前时间，则计划开始时间取当前时间
            else {
                req.setAttribute("planStartTimeRm", parentStartTimeStr);
            }
            Project parentProject = projectService.getProjectEntity(plan.getProjectId());
            if (parentProject != null
                    && StringUtils.isNotEmpty(parentProject.getProjectTimeType())) {
                if (ProjectConstants.WORKDAY.equals(parentProject.getProjectTimeType())) {
                    Date date = (Date)plan.getPlanStartTime().clone();
                    req.setAttribute("planEndTimeRm", DateUtil.dateToString(DateUtil.nextWorkDay(date, 0),"yyyy-MM-dd"));
                }
                else if (ProjectConstants.COMPANYDAY.equals(parentProject.getProjectTimeType())) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("startDate",DateUtil.dateToString(plan.getPlanStartTime(),"yyyy-MM-dd"));
                    params.put("days",0);
                    Date date = (Date)plan.getPlanStartTime().clone();
                    req.setAttribute("planEndTimeRm", DateUtil.dateToString(calendarService.getNextWorkingDay(ResourceUtil.getApplicationInformation().getAppKey(),params),"yyyy-MM-dd"));
                }
                else {
                    Date date = (Date)plan.getPlanStartTime().clone();
                    req.setAttribute("planEndTimeRm", DateUtil.dateToString(TimeUtil.getExtraDate(date, 0),"yyyy-MM-dd"));
                }
            }
            else {
                req.setAttribute("planEndTimeRm", DateUtil.dateToString(TimeUtil.getExtraDate(plan.getPlanStartTime(), 0),"yyyy-MM-dd"));
            }

            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
                Date date = (Date)plan.getPlanStartTime().clone();
                req.setAttribute("planStartTimeRm", parent.getPlanStartTime());
                req.setAttribute("planEndTimeRm", DateUtil.dateToString(DateUtil.nextWorkDay(date, 0),"yyyy-MM-dd"));
            }

            if ("true".equals(plan.getMilestone())) {
                plan.setMilestoneName("是");
            }
            else {
                plan.setMilestoneName("否");
            }

            // 获取计划的生命周期
            // 获取计划的生命周期
               /* PlanDto statusP = new PlanDto();
                planService.initBusinessObject(statusP);*/
            FeignJson fj = planService.getLifeCycleStatusList();
            String lifeStaStr = String.valueOf(fj.getObj());
            List<LifeCycleStatus> statusList =  JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
            for (LifeCycleStatus status : statusList) {
                if (status.getName().equals(plan.getBizCurrent())) {
                    plan.setStatus(status.getTitle());
                    break;
                }
            }

            List<PreposePlanDto> preposePlanList = planRemoteFeignServiceI.getPreposePlansByPlanId(plan);
            // 组装前置数据
            StringBuffer preposeSb = new StringBuffer();
            StringBuffer preposeIds = new StringBuffer();
            String preposeEndTime = "";
            int k = 0;
            for (PreposePlanDto preposePlan : preposePlanList) {
                if (k > 0) {
                    preposeSb.append(",");
                    preposeIds.append(",");
                }
                PlanDto preposePlanDto = planService.getPlanEntity(preposePlan.getPreposePlanId());
                preposePlan.setPreposePlanInfo(preposePlanDto);
                if (preposePlan.getPreposePlanInfo() == null) {
                    continue;
                }
                String endTime = "";
                if (preposePlan.getPreposePlanInfo().getPlanEndTime() != null) {
                    endTime = DateUtil.getStringFromDate(
                            preposePlan.getPreposePlanInfo().getPlanEndTime(), DateUtil.YYYY_MM_DD);
                    if (StringUtils.isEmpty(preposeEndTime)) {
                        if (StringUtils.isNotEmpty(endTime)) {
                            preposeEndTime = endTime;
                        }
                    }
                    else {
                        if (StringUtils.isNotEmpty(endTime)) {
                            Date endTimeDate = null;
                            try {
                                endTimeDate = DateUtil.getDateFromString(endTime, DateUtil.YYYY_MM_DD);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date preposeEndTimeDate = null;
                            try {
                                preposeEndTimeDate = DateUtil.getDateFromString(preposeEndTime,
                                        DateUtil.YYYY_MM_DD);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (endTimeDate.getTime() > preposeEndTimeDate.getTime()) {
                                preposeEndTime = endTime;
                            }
                        }
                    }
                }

                preposeSb.append(preposePlan.getPreposePlanInfo().getPlanName());
                preposeIds.append(preposePlan.getPreposePlanInfo().getId());
                k++ ;
            }
            plan.setPreposePlans(preposeSb.toString());
            plan.setPreposeIds(preposeIds.toString());

            String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
            String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
            req.setAttribute("userList2", jonStr2);
            req.setAttribute("planLevelList", jonStr3);
            req.setAttribute("userList", userList);
            if (plan.getOwnerInfo() != null) {
                req.setAttribute("ownerShow", plan.getOwnerInfo().getRealName() + "-"
                        + plan.getOwnerInfo().getUserName());
            }
            else {
                req.setAttribute("ownerShow", "");
            }

            if (!CommonUtil.isEmpty(plan.getPlanLevel())) {
                if(!CommonUtil.isEmpty(planLevelList)){
                    for(BusinessConfig bc : planLevelList){
                        if(bc.getId().equals(plan.getPlanLevel())){
                            req.setAttribute("planLevelShow", bc.getName());
                            req.setAttribute("planLevelId", plan.getPlanLevel());
                            break;
                        }
                    }
                }

            }
            else {
                req.setAttribute("planLevelShow", "");
            }

            req.setAttribute("parentStartTime", parentStartTimeStr);
            req.setAttribute("parentEndTime", parentEndTimeStr);
            req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
            // req.setAttribute("planLevelList", planLevelList);
            req.setAttribute("disabled", "true");
            req.setAttribute("plan_", plan);
            req.setAttribute("useObjectId", plan.getId());
            req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
            req.setAttribute("preposeEndTime", preposeEndTime);
            req.setAttribute("isUpdate", "true");

            ProjLibDocumentVo vo = new ProjLibDocumentVo();
            vo.setProjectId(plan.getProjectId());
            req.setAttribute("doc", vo);
            req.setAttribute("yanfa", "yanfa");

            redisService.setToRedis("INPUTSLIST", plan.getId(), "");

            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setUseObjectId(plan.getId());
            resourceLinkInfo.setUseObjectType("PLAN");
            List<ResourceLinkInfoDto> resourceLinkInfoList2 = resourceLinkInfoService.queryResourceList(
                    resourceLinkInfo, 1, 10, false);

            req.getSession().setAttribute("resourceListForPlan", resourceLinkInfoList2);
        }

        Map<String,Map<String,String>> map = new HashMap<>();
        List<TabCombinationTemplateDto> templateList = new ArrayList<>();

        if("update".equals(fromType) && StringUtils.isNotBlank(plan.getTabCbTemplateId())){
            TabCombinationTemplateDto templateDto = tabCombinationTemplateFeignService.findTabCbTempByPlanId(plan.getId());
            templateList.add(templateDto);
        } else {
            templateList = tabCombinationTemplateFeignService.findTabCbTempByActivityId(plan.getTaskNameType());
        }

        List<String> objectPathList = new ArrayList<>();
        List<String> objectPathStrList = new ArrayList<>();

        String typeIds = req.getParameter("typeIds");

        //获取js
        if(!CommonUtil.isEmpty(templateList)){
            String displayAccess = "0";   //编制时显示的页签
            List<List<List<List<ObjectPropertyInfoDto>>>> lists = tabTemplateServiceImpl.goTabsView(req,templateList.get(0).getId(),displayAccess,"",typeIds);
            List<TabTemplateDto> tabTemplateList = new ArrayList<>();
            FeignJson combTemplateInfos = tabCombinationTemplateFeignService.getCombTemplateInfos(templateList.get(0).getId());


            if(!CommonUtil.isEmpty(combTemplateInfos.getObj())){
                ObjectMapper mapper = new ObjectMapper();
                List<CombinationTemplateVo> voList = mapper.convertValue(combTemplateInfos.getObj(),new com.fasterxml.jackson.core.type.TypeReference<List<CombinationTemplateVo>>(){});
                for(CombinationTemplateVo cvo : voList){
                    String curTypeId = "";
                    if(!CommonUtil.isEmpty(typeIds)){
                        for(String typeId : typeIds.split(",")){
                            if(!CommonUtil.isEmpty(typeId) && cvo.getTypeId().equals(typeId)){
                                curTypeId = cvo.getTypeId();
                                break;
                            }
                        }
                    }
                    if(!CommonUtil.isEmpty(curTypeId)){
                        TabTemplateDto tp = tabTemplateServiceImpl.queryInfoById(cvo.getTypeId());
                        tp.setExt1(String.valueOf(tabTemplateList.size()+1));
                        tp.setName(cvo.getName());
                        tp.setExt2(tp.getCode());
                        tabTemplateList.add(tp);

                        if(!CommonUtil.isEmpty(objectPathStrList)){
                            boolean flag = true;
                            for(String str : objectPathStrList){
                                if(str.equals(tp.getCode())){
                                    flag = false;
                                    break;
                                }
                            }
                            if(flag){
                                objectPathStrList.add(tp.getCode());
                            }
                        }else{
                            objectPathStrList.add(tp.getCode());
                        }

                        String jsPath = "webpage/com/glaway/ids/project/plan/planGeneralJs/"+tp.getCode()+".js";
                        String filePath = req.getSession().getServletContext().getRealPath("/")+jsPath;
                        if(!CommonUtil.isEmpty(filePath)){
                            File file = new File(filePath);
                            if (file.exists()) {
                                if(!CommonUtil.isEmpty(objectPathList)){
                                    boolean flag = true;
                                    for(String str : objectPathList){
                                        if(str.equals(jsPath)){
                                            flag = false;
                                            break;
                                        }
                                    }
                                    if(flag){
                                        objectPathList.add(jsPath);
                                    }
                                }else{
                                    objectPathList.add(jsPath);
                                }
                            }
                        }
                    }
                }
            }

            Map<String, Object> planMap = new HashMap<>();;
            try {
                planMap = objectToMap(plan);
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            List<PlanLoadUrlVo> planLoadUrlVoList = new ArrayList<>();
            PlanLoadUrlVo loadUrlVo1 = new PlanLoadUrlVo();
            loadUrlVo1.setId("1");
            loadUrlVo1.setPropertyName("owner");
            loadUrlVo1.setLoadUrl("planController.do?userList2&projectId="+plan.getProjectId());
            loadUrlVo1.setTextField("realName");
            loadUrlVo1.setValueField("id");
            planLoadUrlVoList.add(loadUrlVo1);
            PlanLoadUrlVo loadUrlVo2 = new PlanLoadUrlVo();
            loadUrlVo2.setId("2");
            loadUrlVo2.setPropertyName("planLevel");
            loadUrlVo2.setLoadUrl("planController.do?useablePlanLevelList");
            loadUrlVo2.setTextField("name");
            loadUrlVo2.setValueField("id");
            planLoadUrlVoList.add(loadUrlVo2);
            PlanLoadUrlVo loadUrlVo3 = new PlanLoadUrlVo();
            loadUrlVo3.setId("3");
            loadUrlVo3.setPropertyName("taskType");
            loadUrlVo3.setLoadUrl("planController.do?getTaskTypes&parentPlanId="+plan.getParentPlanId()+"&projectId="+req.getParameter("projectId")+"&activityId="+activityId);
            loadUrlVo3.setTextField("name");
            loadUrlVo3.setValueField("id");
            planLoadUrlVoList.add(loadUrlVo3);
            PlanLoadUrlVo loadUrlVo4 = new PlanLoadUrlVo();
            loadUrlVo4.setId("4");
            loadUrlVo4.setPropertyName("milestone");
            loadUrlVo4.setLoadUrl("planController.do?milestoneList");
            loadUrlVo4.setTextField("name");
            loadUrlVo4.setValueField("id");
            planLoadUrlVoList.add(loadUrlVo4);
           /* Map<String, Object> planUrlMap = new HashMap<>();;
            try {
                planUrlMap = objectToMap(planLoadUrlVoList);
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            String enterType = "0";

            for(List<List<List<ObjectPropertyInfoDto>>> objList1 : lists){
                for(List<List<ObjectPropertyInfoDto>> objList2 : objList1){
                    for(List<ObjectPropertyInfoDto> objList3 : objList2){
                        for(int i=objList3.size()-1;i>=0; i--){
                            ObjectPropertyInfoDto objList = new ObjectPropertyInfoDto();
                            objList = objList3.get(i);
                            if(objList3.get(i).getControl().equals("6") || objList3.get(i).getControl().equals("7")) {

                                if(!CommonUtil.isEmpty(objList3.get(i).getObjectPath()) && !"com.glaway.ids.rdflow.task.entity.TaskCellDeliverItem".equals(objList3.get(i).getObjectPath())) {
                                }else {
                                    objList3.remove(i);
                                    continue;
                                }
                            }
                 /*           if(!CommonUtil.isEmpty(objList.getObjectPath()) && !"/".equals(objList.getObjectPath())){
                                String objectPath = objList.getObjectPath();
                                String curType =  objectPath.substring(objectPath.lastIndexOf(".")+1, objectPath.length());
                                objList.setExt3(curType);
                                String lowerPath = lowerFirst(curType);



                            }*/

                            String path = "";
                            if(!objList.getControl().equals("8") && !objList.getControl().equals("7")){
                                String objectPath = objList.getObjectPath();
                                if(!CommonUtil.isEmpty(objectPath)){
                                    path = objectPath.substring(objectPath.lastIndexOf(".")+1);
                                    if (CommonUtil.isEmpty(objList.getPropertyValue())) {
                                        objList.setId(path);
                                    } else {
                                        objList.setId(path+"-"+objList.getPropertyValue());
                                    }
                                }

                            }

                            if(!CommonUtil.isEmpty(objList.getPropertyValue()) && objList.getObjectPath().equals("com.glaway.ids.project.plan.entity.Plan")) {
                                for(PlanLoadUrlVo urlVo : planLoadUrlVoList){
                                    if(urlVo.getPropertyName().equals(objList.getPropertyValue())){
                                        objList.setLoadUrl(urlVo.getLoadUrl());
                                        objList.setTextField(urlVo.getTextField());
                                        objList.setValueField(urlVo.getValueField());
                                        break;
                                    }
                                }
                            }

                            if(!CommonUtil.isEmpty(objList.getPropertyValue()) && !CommonUtil.isEmpty(planMap) && !CommonUtil.isEmpty(planMap.get(objList.getPropertyValue()))) {
                                objList.setValueInfo(planMap.get(objList.getPropertyValue()).toString());
                            }


                            if(!CommonUtil.isEmpty(objList.getObjectPath()) && objList.getObjectPath().equals("com.glaway.ids.project.plan.entity.Plan")){
                                if("planStartTime".equals(objList.getPropertyValue())){
                                    objList.setDefaultValue(plan.getPlanStartTime());
                                }
                                if("planEndTime".equals(objList.getPropertyValue())){
                                    objList.setDefaultValue(plan.getPlanEndTime());
                                }

                                if("preposeIds".equals(objList.getPropertyValue())){
                                    objList.setValueInfo(plan.getPreposePlans());
                                }

                                if("taskType".equals(objList.getPropertyValue())){
                                    if(PlanConstants.PLAN_TYPE_WBS.equals(plan.getTaskType())){
                                        objList.setValueInfo("wbs");
                                    }else if(PlanConstants.PLAN_TYPE_TASK.equals(plan.getTaskType())){
                                        objList.setValueInfo("task");
                                    }
                                }

                                if("milestone".equals(objList.getPropertyValue())){
                                    if("是".equals(plan.getMilestone())){
                                        objList.setValueInfo("true");
                                    }else if("否".equals(plan.getMilestone())){
                                        objList.setValueInfo("false");
                                    }
                                }

                                if("planName".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("taskNameType".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("taskNameType".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("createFullName".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("createTime".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("bizCurrent".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("ownerDept".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }


                            }

                            if(!CommonUtil.isEmpty(objList.getObjectPath()) && objList.getObjectPath().equals("com.glaway.ids.project.plan.entity.Inputs") && objList.getControl().equals("6")) {
                                objList.setId("inputsList");
                                objList.setLoadUrl("inputsController.do?list");
                            }
                            if(!CommonUtil.isEmpty(objList.getObjectPath()) && objList.getObjectPath().equals("com.glaway.ids.common.entity.ResourceLinkInfo") && objList.getControl().equals("6")) {
                                objList.setId("resourceList");
                                objList.setLoadUrl("resourceLinkInfoController.do?listForPlan");
                            }
                            if(!CommonUtil.isEmpty(objList.getObjectPath()) && objList.getObjectPath().equals("com.glaway.ids.project.plan.entity.DeliverablesInfo") && objList.getControl().equals("6")) {
                                objList.setId("deliverablesInfoList");
                                objList.setLoadUrl("deliverablesInfoController.do?list");
                            }
                            if(!CommonUtil.isEmpty(objList.getOperationEvent())&& !objList.getControl().equals("8")){
                                Map<String,String> paramMap = new HashMap<>();
                                String[] oper = objList.getOperationEvent().split(";");
                                for(String op : oper){
                                    if(op.indexOf("=") != -1){
                                        String[] operation = op.split("=");
                                        paramMap.put(operation[0],operation[1].substring(1,operation[1].length()-1));
                                    }
                                }
                                map.put(path+"-"+objList.getPropertyValue(),paramMap);
                            }
                        }
                    }
                }
            }

            String objectPathStrListStr = JSON.toJSONString(objectPathStrList);
            String objectPathStr = stringToJson(objectPathStrListStr,true);
            req.setAttribute("objectPathStr",objectPathStr);

            req.setAttribute("objectPathList",objectPathList);
            req.setAttribute("tabTempList",tabTemplateList);
            req.setAttribute("tabTempCnt",tabTemplateList.size());
            String tabTemplateListStr = JSON.toJSONString(tabTemplateList);
            String tabResStr = stringToJson(tabTemplateListStr,true);
            req.setAttribute("tabTemplateList",tabResStr);
            //   tabTemplateServiceImpl.findTabElementByActivtyId(activityId);
            req.setAttribute("listss",lists);


            String mapStr = JsonUtil.map2json(map).toString();
            String resStr = stringToJson(mapStr,true);


            PlanDataVo planDataVo = new PlanDataVo();
            if(!CommonUtil.isEmpty(req.getParameter("planNameText"))){
                planDataVo.setPlanName(req.getParameter("planNameText"));
            }else{
                planDataVo.setPlanName(plan.getPlanName());
            }
            planDataVo.setOwner(plan.getOwner());
            planDataVo.setOwnerDept(plan.getOwnerDept());
            if(!CommonUtil.isEmpty(plan.getPlanLevel())){
                BusinessConfig businessConfig = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                planDataVo.setPlanLevel(businessConfig.getName());
            }

            planDataVo.setPlanEndTime(DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setPlanStartTime(DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setCreateTime(DateUtil.getStringFromDate(plan.getCreateTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setCreateFullName(plan.getCreateFullName()+"-"+plan.getCreateName());
            planDataVo.setMilestone(plan.getMilestone());
            planDataVo.setBizCurrent(plan.getStatus());
            planDataVo.setWorkTime(plan.getWorkTime());
           /* if(PlanConstants.PLAN_TYPE_WBS.equals(plan.getTaskType())){
                planDataVo.setTaskType("wbs");
            }else if(PlanConstants.PLAN_TYPE_TASK.equals(plan.getTaskType())){
                planDataVo.setTaskType("task");
            }*/
            planDataVo.setTaskType(plan.getTaskType());

            planDataVo.setRemark(plan.getRemark());
            if(!CommonUtil.isEmpty(req.getParameter("taskNameType"))){
                planDataVo.setTaskNameType(req.getParameter("taskNameType"));
            }else{
                ActivityTypeManageDto activityTypeManageDto = activityTypeManageService.queryActivityTypeManageById(plan.getTaskNameType());
                planDataVo.setTaskNameType(activityTypeManageDto.getName());
            }

            req.setAttribute("operation",resStr);
            String planStr = JsonUtil.map2json(planDataVo);
            String resPlanStr = stringToJson(planStr,true);
            req.setAttribute("planDefault",resPlanStr);
            req.setAttribute("enterType","0");
        }

        if(!"update".equals(fromType)){
            return new ModelAndView(
                    "com/glaway/ids/project/plan/planGeneralAddPage");
        }else{
            return new ModelAndView(
                    "com/glaway/ids/project/plan/planGeneralUpdatePage");
        }

    }


    private String lowerFirst(String obj){
        char[] chars = obj.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static Map<String, Object> objectToMap(Object obj) throws IllegalArgumentException, IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for(java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            map.put(fieldName, value);
        }
        return map;
    }


    public String stringToJson(String str,boolean isJSJson){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<str.length();i++){
            char c = str.charAt(i);
            switch (c){
                case '\'':
                    if(isJSJson){
                        sb.append("\\\'");
                    }else{
                        sb.append("\'");
                    }
                    break;
                case '\"' :
                    if(!isJSJson){
                        sb.append("\\\"");
                    }else{
                        sb.append("\"");
                    }
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:sb.append(c);
            }
        }
        return sb.toString();
    }


    @RequestMapping(params = "goAddLocalFile")
    public ModelAndView goAddLocalFile(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId",projectId);
        return new ModelAndView(
                "com/glaway/ids/project/plan/planAddLocalFile");
    }

    /**
     * 项目计划编辑页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goPlanReceivedPage")
    public ModelAndView goPlanReceivedPage(PlanDto plan, HttpServletRequest req) {
        String type = req.getParameter("type");
        String id = req.getParameter("taskNumber"); //获取业务id
        String action = req.getParameter("action");
        req.setAttribute("type", type);
        req.setAttribute("action", action);
        if (CommonUtil.isEmpty(plan.getId())) {
            plan.setId(id);
        }
        plan = planService.getPlanEntity(plan.getId());
        req.setAttribute("plan_", plan);
        String projectId = plan.getProjectId();

        String teamId = req.getParameter("teamId");
        if (CommonUtil.isEmpty(teamId)) {
            FeignJson teamIdFj = projRoleService.getTeamIdByProjectId(projectId);
            if (teamIdFj.isSuccess()) {
                teamId = teamIdFj.getObj() == null ? "" : teamIdFj.getObj().toString();
            }

        }
        req.setAttribute("teamId", teamId);

        req.setAttribute("nameStandardId", "");

        req.setAttribute("projectId", projectId);

        req.setAttribute("isOut", "2");
        List<OutwardExtensionDto> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"flowTempActiveCategory");
        if (!CommonUtil.isEmpty(outwardExtensionList)) {
            for (OutwardExtensionDto ext : outwardExtensionList) {
                if (ext.getOptionValue().equals(plan.getTaskNameType())) {
                    req.setAttribute("isOut", "1");
                    NameStandardDto nameStandard = new NameStandardDto();
                    nameStandard.setStopFlag("启用");
                    nameStandard.setName(plan.getPlanName());
                    List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                    req.setAttribute("nameStandardId", list.get(0).getId());
                    break;
                }
            }
        }
        return new ModelAndView("com/glaway/ids/project/plan/planReceivedPage");
    }

    /**
     * 取消/关注计划操作
     *
     * @return
     */
    @RequestMapping(params = "concernPlan")
    @ResponseBody
    public FeignJson concernPlan(HttpServletRequest request) {
        String id = (String)request.getParameter("id");
        String concernCode = (String)request.getParameter("concernCode");
        FeignJson j = planService.concernPlan(id, concernCode, ResourceUtil.getCurrentUser().getId());
        return j;
    }

    /**
     * 计划待接收流程--接收
     * @param request
     * @return
     */
    @RequestMapping(params = "receivePlan")
    @ResponseBody
    public AjaxJson receivePlan(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String planId = request.getParameter("planId");
            String flag = request.getParameter("flag");
            FeignJson fj = planService.continuePlanReceiveProcess(planId,flag,ResourceUtil.getCurrentUser());
            if(fj.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }


    /**
     * 计划待接收流程--拒绝
     * @param request
     * @return
     */
    @RequestMapping(params = "refusePlan")
    @ResponseBody
    public AjaxJson refusePlan(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String planId = request.getParameter("planId");
            String refuseRemark = request.getParameter("refuseRemark");
            String refuseReason = request.getParameter("refuseReason");
            FeignJson fj = planService.refusePlanReceiveProcess(planId,ResourceUtil.getCurrentUser(),refuseReason,refuseRemark,ResourceUtil.getCurrentUserOrg().getId());
            if(fj.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }


    @RequestMapping(params = "goRefusePlanPage")
    public ModelAndView goRefusePlanPage(HttpServletRequest request){
        request.setAttribute("planId",request.getParameter("planId"));
        String refuseId = request.getParameter("refuseId");
        request.setAttribute("refuseId",refuseId);
        String planId = request.getParameter("planId");
        if(!CommonUtil.isEmpty(refuseId)){
            PlanRefuseInfoDto planRefuseInfoDto = planService.getPlanRefuseInfoEntity(refuseId);
            request.setAttribute("planRefuseInfo_",planRefuseInfoDto);
            planId = planRefuseInfoDto.getPlanId();
        }
        PlanDto planDto = planService.getPlanEntity(planId);
        request.setAttribute("plan_",planDto);
        return new ModelAndView(
                "com/glaway/ids/project/plan/planRefusePage");
    }

    /**
     * 查询是否延期列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "planRefuseReasonList")
    @ResponseBody
    public void planRefuseReasonList(HttpServletRequest request, HttpServletResponse response) {
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj1 = new JSONObject();
        obj1.put("id", "planInfoMistake");
        obj1.put("name", "计划信息错误");
        jsonList.add(obj1);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "inputMistake");
        obj2.put("name", "输入错误");
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "outputMistake");
        obj3.put("name", "输出错误");
        jsonList.add(obj3);
        JSONObject obj4 = new JSONObject();
        obj4.put("id", "otherMistake");
        obj4.put("name", "其他");
        jsonList.add(obj4);
        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = "goAddPlm")
    public ModelAndView goAddPlm(HttpServletRequest request){
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        request.setAttribute("useObjectId", useObjectId);
        request.setAttribute("useObjectType", useObjectType);
        return new ModelAndView(
            "com/glaway/ids/project/plan/planAddPlm");
    }

    /**
     * 检查PLM系统是否在线
     * @return
     */
    @RequestMapping(params = "checkPlm")
    @ResponseBody
    public AjaxJson checkPlm(){
        AjaxJson j = new AjaxJson();
        try{
            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            jsonList = idsIntegratedPLMService.getPrimaryObjectContext(jsonList, UserUtil.getCurrentUser().getId());
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }

    @RequestMapping(params = "goDelegatePlanPage")
    public ModelAndView goDelegatePlanPage(HttpServletRequest request){
        request.setAttribute("planId",request.getParameter("planId"));
        String temporyPlanId = request.getParameter("temporyPlanId");
        request.setAttribute("temporyPlanId",temporyPlanId);
        String tabId = request.getParameter("tabId");
        request.setAttribute("tabId", tabId);
        if(!CommonUtil.isEmpty(temporyPlanId)){
            TemporaryPlanDto temporaryPlanDto = planService.getTemporaryPlanEntity(temporyPlanId);
            BusinessConfig businessConfigDto = businessConfigService.getBusinessConfig(temporaryPlanDto.getChangeType());
            temporaryPlanDto.setChangeTypeInfo(businessConfigDto);
            request.setAttribute("TemporaryPlan_",temporaryPlanDto);
        }else if(!CommonUtil.isEmpty(request.getParameter("planId"))){
            PlanDto p = planService.getPlanEntity(request.getParameter("planId"));
            request.setAttribute("TemporaryPlan_",p);
        }
        return new ModelAndView(
                "com/glaway/ids/project/plan/planDelegatePage");
    }

    /**
     * 计划待接收流程--委派
     * @param request
     * @return
     */
    @RequestMapping(params = "delegatePlan")
    @ResponseBody
    public AjaxJson delegatePlan(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String planId = request.getParameter("planId");
            String delegateUserId = request.getParameter("delegateUserId");
            String changeType = request.getParameter("changeType");
            String leaderId = request.getParameter("leaderId");
            String departLeaderId = request.getParameter("departLeaderId");
            String changeRemark = request.getParameter("changeRemark");
            String flag = request.getParameter("flag");
            FeignJson fj = planService.delegatePlanReceiveProcess(planId,ResourceUtil.getCurrentUser(),delegateUserId,changeType,
                    leaderId,departLeaderId,changeRemark,flag,ResourceUtil.getCurrentUserOrg().getId());
            if(fj.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }

    @RequestMapping(params = "goSelectPart")
    public ModelAndView goSelectPart(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        String planId = request.getParameter("planId");
        request.setAttribute("planId", planId);
        return new ModelAndView(
            "com/glaway/ids/project/plan/goSelectPart");
    }

    @RequestMapping(params = "goBomNodes")
    public ModelAndView goBomNodes(HttpServletRequest request){
        String partId = request.getParameter("partId");
        request.setAttribute("partId", partId);
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        String planId = request.getParameter("planId");
        request.setAttribute("planId", planId);
        return new ModelAndView("com/glaway/ids/project/plan/goBomNodes");
    }

    /**
     * 保存BOM节点
     * @param request
     * @return
     */
    @RequestMapping(params = "doSaveBom")
    @ResponseBody
    public AjaxJson doSaveBom(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            //计划Id
            String planId = request.getParameter("planId");
            //项目Id
            String projectId = request.getParameter("projectId");
            //BOMId
            String bomId = request.getParameter("bomId");
            //名称
            String name = request.getParameter("name");
            //编码
            String code = request.getParameter("code");
            FeignJson feignJson = planService.doSaveBom(planId,ResourceUtil.getCurrentUser(),bomId,projectId,
                code,name,ResourceUtil.getCurrentUserOrg().getId());
            if(feignJson.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }

    /**
     * 默认进入的首页面
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagridForPrepose")
    @ResponseBody
    public AjaxJson searchDatagridForPrepose(HttpServletRequest request,
                                             HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planNumber = request.getParameter("planNumber");
        String planName = request.getParameter("planName");
        String planLevel = request.getParameter("planLevel");
        String bizCurrent = request.getParameter("bizCurrent");
        String userName = request.getParameter("userName");
        String planStartTime = request.getParameter("planStartTime");
        String planEndTime = request.getParameter("planEndTime");
        String workTime = request.getParameter("workTime");
        String workTimeCondition = request.getParameter("workTime_condition");
        String progressRate = request.getParameter("progressRate");
        String progressRateCondition = request.getParameter("progressRate_condition");
        String taskNameType = request.getParameter("taskNameType");
        String taskType = request.getParameter("taskType");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String projectId = request.getParameter("projectId");

        String planId = request.getParameter("planId");

        List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
        if (StringUtils.isNotEmpty(planNumber)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planNumber");
            conditionVO.setValue(planNumber);
            conditionList.add(conditionVO);
        }
        if (StringUtils.isNotEmpty(planName)) {
            ConditionVO conditionVO = new ConditionVO();
            conditionVO.setKey("Plan.planName");
            conditionVO.setValue(planName);
            conditionList.add(conditionVO);
        }

        if (StringUtils.isNotEmpty(planLevel)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(planLevel.split(","));
            vo.setCondition("in");
            vo.setValue(planLevel);
            vo.setKey("Plan.planLevelInfo.id");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Plan.bizCurrent");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planStartTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planStartTime);
            vo.setKey("Plan.planStartTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(planEndTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(planEndTime);
            vo.setKey("Plan.planEndTime");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(workTime)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(workTime);
            vo.setKey("Plan.workTime");
            vo.setCondition(workTimeCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(progressRate)) {
            ConditionVO vo = new ConditionVO();
            vo.setValue(progressRate);
            vo.setKey("Plan.progressRate");
            vo.setCondition(progressRateCondition);
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskNameType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskNameType.split(","));
            vo.setCondition("in");
            vo.setValue(taskNameType);
            vo.setKey("Plan.taskNameType");
            conditionList.add(vo);
        }

        if (StringUtils.isNotEmpty(taskType)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(taskType.split(","));
            vo.setCondition("in");
            vo.setValue(taskType);
            vo.setKey("Plan.taskType");
            conditionList.add(vo);
        }

        PageList pageList = planService.queryEntityForPrepose(conditionList, projectId, userName,
                progressRate, workTime);
        List<PlanDto> planList = new ObjectMapper().convertValue(pageList.getResultList(),new com.fasterxml.jackson.core.type.TypeReference<List<PlanDto>>(){});
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            PlanDto currentPlan = null;
            for (PlanDto p : planList) {
                if (p.getId().equals(planId)) {
                    currentPlan = p;
                }
                else {
                    planIdMaps.put(p.getId(), p.getId());
                }
            }
            if (currentPlan != null) {
                planList.remove(currentPlan);
            }
        }

        // 获取所有该项目下的计划List,将其放入listMap中
        PlanDto plan = new PlanDto();
        plan.setProjectId(projectId);
        List<PlanDto> list = planService.queryPlanListForTreegrid(plan);
        Map<String, PlanDto> listMap = new HashMap<String, PlanDto>();
        for (PlanDto p : list) {
            listMap.put(p.getId(), p);
        }

        // 若
        // 获取检索命中的计划List中的某个计划其父计划不再planIdMaps中,则将其父计划加入到tempList，并将其result属性设为"true"
        List<PlanDto> tempList = new ArrayList<PlanDto>();
        for (PlanDto p : planList) {
            getParent(tempList, p.getParentPlanId(), planIdMaps, listMap);
            /*
             * if (StringUtils.isNotEmpty(p.getParentPlanId())
             * && StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
             * if (listMap.get(p.getParentPlanId()) != null) {
             * Plan parent = listMap.get(p.getParentPlanId());
             * parent.setResult("true");
             * tempList.add(parent);
             * }
             * }
             */
        }

        // 将tempList合入检索命中的结果中
        tempList.addAll(planList);

        String userId = UserUtil.getInstance().getUser().getId();
        boolean isPmo = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PMO, userId, null);
        FeignJson fJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = String.valueOf(fJson.getObj());
        boolean isProjectManger = groupService.judgeHasLimit(ResourceUtil.getApplicationInformation().getAppKey(),ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        PlanAuthorityVo parameVo = new PlanAuthorityVo();
        parameVo.setIsModify(isModify);
        parameVo.setPlanModifyOperationCode(planModifyOperationCode);
        parameVo.setPlanAssignOperationCode(planAssignOperationCode);
        parameVo.setPlanDeleteOperationCode(planDeleteOperationCode);
        parameVo.setPlanChangeOperationCode(planChangeOperationCode);
        parameVo.setPlanDiscardOperationCode(planDiscardOperationCode);
        parameVo.setPmo(isPmo);
        parameVo.setProjectManger(isProjectManger);

        Project project = projectService.getProjectEntity(projectId);

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);

        List<JSONObject> rootList = changePlansToJSONObjectsForPerpose(tempList, parameVo,
                project, statusList);

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }


    /**
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjectsForPerpose(List<PlanDto> planList,
                                                                PlanAuthorityVo parameVo,
                                                                Project project,
                                                                List<LifeCycleStatus> statusList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(planList)) {
            for (PlanDto p : planList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }

        Map<String,TSUserDto> userAllMap = userService.getAllUserIdsMap();

        //获取计划等级的id和名称集合：
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        //   String planLevelListStr = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchAllBusinessConfigs(ConfigTypeConstants.PLANLEVEL);
        Map<String, String> planLevelIdAndNameMap = new HashMap<String, String>();
        for(BusinessConfig cur : planLevelList) {
            planLevelIdAndNameMap.put(cur.getId(), cur.getName());
        }

/*        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);*/

        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManage.getAllActivityTypeManage(false);

        FeignJson fj = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(fj.getObj());
        for (PlanDto p : planList) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planNumber", p.getPlanNumber());
                root.put("optBtn", this.generateOptBtn(p, parameVo, project));
                root.put("progressRate", this.generateProgressRate(p, warningDay));
                root.put("displayName", p.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getPlanName());
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.generatePlanNameUrl(p));
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", p.getPlanName());
                    root.put("planName", this.generatePlanNameUrl(p));
                }

                root.put("planLevelInfo",
                        planLevelIdAndNameMap.get(p.getPlanLevel()) == null ? "" : planLevelIdAndNameMap.get(p.getPlanLevel()));
                root.put("ownerInfo",
                        userAllMap.get(p.getOwner()) == null ? "" : userAllMap.get(p.getOwner()).getRealName() + "-"
                                + userAllMap.get(p.getOwner()).getUserName());
                root.put("bizCurrentInfo", this.generatePlanBizCurrentForPerpose(p, statusList));
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
                root.put("taskNameTypeDisplay", this.generateTaskNameTypeUrl(p, activityTypeManageList));
                root.put("taskType", p.getTaskType());

                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPidForCustomView(parentPlanIds, planList, rootList.get(i), parameVo, activityTypeManageList,
                    project, statusList, warningDay,planLevelIdAndNameMap,userAllMap);
        }
        return rootList;
    }

    /**
     * 构造计划状态显示及链接
     *
     * @param plan
     * @return
     * @see
     */
    private String generatePlanBizCurrentForPerpose(PlanDto plan, List<LifeCycleStatus> statusList) {
        String status = "";
        if ("PAUSE".equals(plan.getProjectStatus())) {
            status = "已暂停";
        }
        else if ("CLOSE".equals(plan.getProjectStatus())) {
            status = "已关闭";
        }
        else {
            for (LifeCycleStatus lifeCycleStatus : statusList) {
                if (lifeCycleStatus.getName().equals(plan.getBizCurrent())) {
                    status = lifeCycleStatus.getTitle();
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 判断子计划状态是否存在非拟制
     * @param request
     * @return
     */
    @RequestMapping(params = "childBizCurrentExistAllEditing")
    @ResponseBody
    public AjaxJson childBizCurrentExistAllEditing(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String parentPlanId = request.getParameter("parentPlanId");          
            PlanDto parent = new PlanDto();
            parent.setParentPlanId(parentPlanId);
            parent.setAvaliable("1");
            List<PlanDto> childList = planService.queryPlanList(parent, 1, 10, false);
            boolean flag = false;
            if(childList.size()>0) {
                for(PlanDto planDto : childList) {
                    if(!"EDITING".equals(planDto.getBizCurrent())) {
                        flag = true;
                        break;
                    }
                }
                j.setSuccess(flag); 
            }else {
                j.setSuccess(false); 
            }   
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }


    /**
     * 启动计划委派流程
     * @param request
     * @return
     */
    @RequestMapping(params = "startPlanDelegateProcess")
    @ResponseBody
    public AjaxJson startPlanDelegateProcess(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        try{
            String planId = request.getParameter("planId");
            String delegateUserId = request.getParameter("delegateUserId");
            String changeType = request.getParameter("changeType");
            String leaderId = request.getParameter("leaderId");
            String departLeaderId = request.getParameter("departLeaderId");
            String changeRemark = request.getParameter("changeRemark");
            FeignJson fj = planService.startPlanDelegateProcess(planId,ResourceUtil.getCurrentUser(),delegateUserId,changeType,
                    leaderId,departLeaderId,changeRemark,ResourceUtil.getCurrentUserOrg().getId());
            if(fj.isSuccess()){
                j.setSuccess(true);
            }else{
                j.setSuccess(false);
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }


    @RequestMapping(params = "checkBeforePlanDel")
    @ResponseBody
    public AjaxJson checkBeforePlanDel(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String planId = request.getParameter("planId");
        try{
            PlanDto planDto = new PlanDto();
            planDto.setId(planId);
            List<PlanDto> childPlanList = planService.getPlanAllChildren(planDto);
            if(CommonUtil.isEmpty(childPlanList)){
                j.setSuccess(true);
            }else{
                if(childPlanList.size() ==1){
                    if(childPlanList.get(0).getId().equals(planId)){
                        j.setSuccess(true);
                    }else{
                        j.setSuccess(false);
                    }
                }else{
                    j.setSuccess(false);
                }
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            return j;
        }
    }

    @RequestMapping(params = "isHaveChildrenNotEditing")
    @ResponseBody
    public AjaxJson isHaveChildrenNotEditing(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        Boolean isNotEditing = false;
        String planId = request.getParameter("planId");
        try{
            PlanDto p = new PlanDto();
            p.setParentPlanId(planId);
            p.setAvaliable("1");
            List<PlanDto> childPlanList = planService.queryPlanList(p, 1, 10, false);
            if(childPlanList.size()>0) {
                for (PlanDto child : childPlanList) {
                    if (!PlanConstants.PLAN_EDITING.equals(child.getBizCurrent())) {
                        isNotEditing = true;
                        break;
                    }
                }
            }
        }catch (Exception e){
            j.setSuccess(false);
        }finally {
            j.setSuccess(isNotEditing);
            return j;
        }
    }


    @RequestMapping(params = "goProjectPlanStatistic")
    public ModelAndView goProjectStatistic(HttpServletRequest request){
        String userId = UserUtil.getCurrentUser().getId();
        String value = ",STARTING";
        List<ConditionVO> conditionList = new ArrayList<>();
        ConditionVO vo = new ConditionVO();
        vo.setKey("Project.bizCurrent");
        vo.setCondition("in");
        vo.setValueArr(value.split(","));
        vo.setValue(value);

        ConditionVO pageVo = new ConditionVO();
        pageVo.setKey("page");
        pageVo.setValue("1");

        ConditionVO rowsVo = new ConditionVO();
        rowsVo.setKey("rows");
        rowsVo.setValue("1");

        conditionList.add(vo);
        conditionList.add(pageVo);
        conditionList.add(rowsVo);
        PageList pageList = projectService.queryEntityBySql(conditionList, "",
            "", null, null,userId,ResourceUtil.getCurrentUserOrg().getId());
        List<Map<String,String>> projectList = pageList.getResultList();
        String selectedValue = "";
        if (!CollectionUtils.isEmpty(projectList)) {
            Map<String,String> map = projectList.get(0);
            selectedValue = map.get("id").toString();
        }
        request.setAttribute("selectedValue", selectedValue);
        return new ModelAndView("com/glaway/ids/project/plan/goProjectPlanStatistic");
    }

    /**
     * Description: <br>获取下拉框数据
     *
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "getStartingProjByUserId")
    public void getStartingProjByUserId(HttpServletResponse response) {
        String userId = UserUtil.getCurrentUser().getId();
        String value = ",STARTING";
        List<ConditionVO> conditionList = new ArrayList<>();
        ConditionVO vo = new ConditionVO();
        vo.setKey("Project.bizCurrent");
        vo.setCondition("in");
        vo.setValueArr(value.split(","));
        vo.setValue(value);

        ConditionVO pageVo = new ConditionVO();
        pageVo.setKey("page");
        pageVo.setValue("1");

        ConditionVO rowsVo = new ConditionVO();
        rowsVo.setKey("rows");
        rowsVo.setValue("1000");

        conditionList.add(vo);
        conditionList.add(pageVo);
        conditionList.add(rowsVo);
        PageList pageList = projectService.queryEntityBySql(conditionList, "",
            "", null, null,userId,ResourceUtil.getCurrentUserOrg().getId());
        List<Map<String,String>> projectList = pageList.getResultList();
        JSONArray jList = new JSONArray();
        if (!CollectionUtils.isEmpty(projectList)) {
            for (Map<String,String> map: projectList){
                JSONObject obj = new JSONObject();
                obj.put("id", map.get("name").toString());
                obj.put("text", map.get("id").toString());
                jList.add(obj);
            }
        }
        String projListStr = jList.toString();
        TagUtil.ajaxResponse(response, projListStr);
    }

    @RequestMapping(params = "goPlanLifeCycle")
    public ModelAndView goPlanLifeCycle(HttpServletRequest request){
        String projId = request.getParameter("projId");
        String lifeCycleArray = "0,0,0,0,0,0";
        if (StringUtils.isNotEmpty(projId)){
            FeignJson feignJson = planService.getAllPlanLifeCycleArrayByProjId(projId);
            if (feignJson.isSuccess()){
                lifeCycleArray = (String)feignJson.getObj();
            }
        }
        request.setAttribute("lifeCycleArray", lifeCycleArray);
        return new ModelAndView("com/glaway/ids/project/plan/goPlanLifeCycle");
    }

    @RequestMapping(params = "goPlanExecutCondition")
    public ModelAndView goPlanExecutCondition(HttpServletRequest request){
        String projId = request.getParameter("projId");
        String wbs = "0,0,0";
        String flow = "0,0,0";
        String task = "0,0,0";
        if (StringUtils.isNotEmpty(projId)){
            FeignJson feignJson = planService.getAllPlanExecutByProjId(projId);
            if (feignJson.isSuccess()){
                Map<String, String> map = (Map<String, String>)feignJson.getObj();
                wbs = map.get("wbs");
                flow = map.get("flow");
                task = map.get("task");
            }
        }
        request.setAttribute("wbs", wbs);
        request.setAttribute("flow", flow);
        request.setAttribute("task", task);
        return new ModelAndView("com/glaway/ids/project/plan/goPlanExecutCondition");
    }

    @RequestMapping(params = "goPlanCompletion")
    public ModelAndView goPlanCompletion(HttpServletRequest request){
        String projId = request.getParameter("projId");
        String wbs = "0,0,0";
        String flow = "0,0,0";
        String task = "0,0,0";
        if (StringUtils.isNotEmpty(projId)){
            FeignJson feignJson = planService.getAllPlanCompletionByProjId(projId);
            if (feignJson.isSuccess()){
                Map<String, String> map = (Map<String, String>)feignJson.getObj();
                wbs = map.get("wbs");
                flow = map.get("flow");
                task = map.get("task");
            }
        }
        request.setAttribute("wbs", wbs);
        request.setAttribute("flow", flow);
        request.setAttribute("task", task);
        return new ModelAndView("com/glaway/ids/project/plan/goPlanCompletion");
    }

    /**
     * 详情页面按钮显示
     * @param planId
     * @param request
     * @return
     */
    @RequestMapping(params = "isButtonShow")
    @ResponseBody
    public FeignJson isButtonShow(String planId, HttpServletRequest request) {
        FeignJson j = new FeignJson();
        j.setSuccess(false);
        PlanDto planDto = new PlanDto();
        if (StringUtils.isNotBlank(planId)) {
            planDto = planService.getPlanEntity(planId);
        }
        Integer planFeedbackFlag = getplanFeedbackFlag(planId);
        //获取执行中生命周期权重占比
        FeignJson weightFj = feedBackFeignService.calculateWeightByStatus(PlanConstants.PLAN_ORDERED);
        double weight = 0;
        if (weightFj.isSuccess()) {
            weight = Double.parseDouble(weightFj.getObj() == null ? "0" : weightFj.getObj().toString());
        }
        if (StringUtils.isNotBlank(planDto.getProgressRate())) {
            if (weight == Double.parseDouble(planDto.getProgressRate()) && planFeedbackFlag != '3') {
                if (planDto.getFlowStatus().equals(PlanConstants.PLAN_FLOWSTATUS_NORMAL) ||
                        (planDto.getFlowStatus().equals(PlanConstants.PLAN_FEEDBACKING) &&
                                (StringUtils.isNotBlank(planDto.getFlowStatus()) || StringUtils.isNotBlank(planDto.getFeedbackProcInstId())))) {
                    j.setSuccess(true);
                }
            }
        }

        return j;
    }

    /**
     * 计划时序校验完工按钮
     * @param plan
     * @return
     */
    @RequestMapping(params = "planSeqApproveBtnCheck")
    @ResponseBody
    public FeignJson planSeqApproveBtnCheck(PlanDto plan) {
        FeignJson j = planService.planSeqApproveBtnCheck(plan.getId());
        return j;
    }

    @RequestMapping(params = "getParentProcess")
    @ResponseBody
    public FeignJson getParentProcess(HttpServletRequest request) {
        String planId = request.getParameter("planId");
        FeignJson j = planService.getParentProcess(planId);
        return j;
    }
}
