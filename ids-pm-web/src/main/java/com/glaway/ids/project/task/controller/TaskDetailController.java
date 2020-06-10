package com.glaway.ids.project.task.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.searchengine.client.SearchEngineClient;
import com.glaway.foundation.searchengine.vo.SearchInput;
import com.glaway.foundation.searchengine.vo.SearchOutput;
import com.glaway.foundation.system.GetFeignSystemServiceI;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.IDSPluginConstants;
import com.glaway.ids.common.constant.KnowledgeWebServiceConstants;
import com.glaway.ids.common.constant.NameStandardConstants;
import com.glaway.ids.common.constant.PluginConstants;
import com.glaway.ids.common.helper.JSONHelper;
import com.glaway.ids.common.service.PluginValidateServiceI;
import com.glaway.ids.common.vo.*;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.EpsConfig;
import com.glaway.ids.constant.*;
import com.glaway.ids.models.JsonRequery;
import com.glaway.ids.models.JsonResult;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.project.menu.service.RecentlyProjectRemoteFeignServiceI;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.rdtask.task.service.PlanKnowledgeReferenceFeignServiceI;
import com.glaway.ids.rdtask.task.vo.KnowledgeInfoReq;
import com.glaway.ids.rdtask.task.vo.TaskInfoReq;
import com.glaway.ids.review.service.ReviewFeignServiceI;
import com.glaway.ids.rule.GroupRule;
import com.glaway.ids.rule.PlanRule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jodd.servlet.URLDecoder;
import org.apache.commons.lang.StringUtils;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 项目计划
 * @author blcao
 * @date 2015-03-23 16:32:26
 * @version V1.0
 */
@Controller
@RequestMapping("/taskDetailController")
public class TaskDetailController extends BaseController {
    /**
     * 
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(TaskDetailController.class);

    /**
     * 项目计划管理接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;

    
    /**
     * 项目计划管理接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    /**
     * 参数WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;
    
    /**
     * 
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;
    
    /**
     * 名称库<br>
     */
    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;
    
    /**
     * 插件验证接口服务
     */
    @Autowired
    public PluginValidateServiceI pluginValidateService;
    
    /**
     * 
     */
    @Autowired
    private RecentlyProjectRemoteFeignServiceI recentlyProjectService;
    
    /**
     * 
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;
    
    /**
     * 计划输出Service
     */
    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;
    
    /**
     * 前置计划Service
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;

    @Autowired
    private InputsRemoteFeignServiceI inputsRemoteFeignService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    /**
     * 项目计划管理接口
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;

    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoService;

    @Autowired
    private ResourceRemoteFeignServiceI resourceService;

    @Autowired
    private ReviewFeignServiceI reviewFeignService;

    @Autowired
    private FeignSystemService feignSystemService;

    @Autowired
    private FeignRepService repService;

    @Autowired
    private FeignUserService userService;
    
    @Autowired
    private FeignDepartService deptService;

    @Autowired
    private PlanFeedBackFeignServiceI feedBackFeignService;

    @Value(value="${spring.application.name}")
    private String appKey;
    
    /**
     * repFile接口
     */
    @Autowired
    private FeignRepService repFileService;

    /**
     * 计划类别接口
     */
    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    /**
     * 知识接口
     */
    @Autowired
    private PlanKnowledgeReferenceFeignServiceI planKnowledgeReferenceFeignService;

    @Autowired
    private SearchEngineClient searchEngineClient;

    @Autowired
    private FeignAttentionWordsRemoteServiceI  attentionWordsRemoteService;

    @Autowired
    private FeignHotWordsRemoteServiceI feignHotWordsRemoteService;

    @Autowired
    private FeignKnowledgeCategoryRemoteServiceI feignKnowledgeCategoryServiceI;


    /**
     * 项目计划编辑页面跳转
     * 
     * @param plan
     * @param req
     * @return
     * @throws InterruptedException
     * @see
     */
    @RequestMapping(params = "goCheck")
    public ModelAndView goCheck(PlanDto plan, HttpServletRequest req)
        throws InterruptedException {
        String isOwner = "false";
        if (StringUtils.isNotEmpty(plan.getId())) {
            //Thread.sleep(2000);
            plan = planService.getPlanEntity(plan.getId());
            TSUserDto curruntUser = UserUtil.getInstance().getUser();
            if(!CommonUtil.isEmpty(plan) && curruntUser.getId().equals(plan.getOwner())){
                isOwner = "true";
            }
        }
        req.setAttribute("isOwner", isOwner);
        // 添加最近访问到项目列表
        updateResentProject(plan.getProjectId());

        req.setAttribute("plan_", plan);
        String projectId = plan.getProjectId();

        String teamId = req.getParameter("teamId");
        if(CommonUtil.isEmpty(teamId)){
            FeignJson teamIdFeignJson = projRoleService.getTeamIdByProjectId(projectId);
            teamId = teamIdFeignJson.getObj().toString();
        }
        req.setAttribute("teamId", teamId);
        
        req.setAttribute("nameStandardId", "");
        
        req.setAttribute("isOut", "2");
//        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//        if(!CommonUtil.isEmpty(outwardExtensionList)) {
//            req.setAttribute("outwards", outwardExtensionList);
//        }
/*        if(!CommonUtil.isEmpty(plan.getTaskNameType())) {
            if(!CommonUtil.isEmpty(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(plan.getTaskNameType())) {
                req.setAttribute("isOut", "1");
                NameStandardDto nameStandard = new NameStandardDto();
                nameStandard.setName(plan.getPlanName());
                List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                if(!CommonUtil.isEmpty(list)){
                    req.setAttribute("nameStandardId", list.get(0).getId());
                }
            }
        }*/
        //获取研发类的页签组合
        
       String url = "com/glaway/ids/pm/project/task/taskDetail";

        String taskNameTypeReview = "";
//        String dictCode = "activeCategory";
//        List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
//        if (!CommonUtil.isEmpty(types)) {
//            for (TSType type : types) {
//                if (type.getTypename().contains(PlanConstants.TASKNAMETYPE_REVIEW_CHI)) {
//                    taskNameTypeReview = type.getTypecode();
//                    break;
//                }
//            }
//        }
        GetFeignSystemServiceI systemService = (GetFeignSystemServiceI)ServiceDelegate.getService(
            "getFeignSystemService");
        List<Map<String, String>> typeMapList = systemService.getDictData("activeCategory");
        List<JSONObject> list = new ArrayList<>();
        if (!CommonUtil.isEmpty(typeMapList)) {
            for(Map<String, String> map : typeMapList) {
                JSONObject json = new JSONObject();
                json.put("typename", map.get("text"));
                json.put("typecode", map.get("id"));
                list.add(json);
            }
            
            taskNameTypeReview = (String)list.get(0).get("typecode");
        }
        
        TSUserDto curruntUser = UserUtil.getInstance().getUser();
        req.setAttribute("ownerpd", "");
        if (StringUtils.isNotEmpty(plan.getOwner())) {
            if (!plan.getOwner().equals(curruntUser.getId())) {
                req.setAttribute("ownerpd", "pause");
            }
        }
        // 如果项目为暂停或者关闭，只能查看
        if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())
            || ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())
            || PlanConstants.PLAN_EDITING.equals(plan.getBizCurrent())) {
            // url = "com/glaway/ids/project/task/taskDetail-view";
            req.setAttribute("status", "pause");
        }
        else {
            req.setAttribute("status", "");
        }
        req.setAttribute("taskNameTypeReview", taskNameTypeReview);
        //获取执行中生命周期权重占比
        FeignJson weightFj = feedBackFeignService.calculateWeightByStatus(PlanConstants.PLAN_ORDERED);
        if (weightFj.isSuccess()) {
            int weight = Integer.parseInt(weightFj.getObj() == null ? "0" : weightFj.getObj().toString());
            req.setAttribute("ORDEREDWeight",weight);
        }
        //查询是否安装riskproblem插件
        boolean isRiskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);
        //获取riskproblems服务路径，调用riskproblems服务
        if (isRiskPluginValid) {
            String path = pluginValidateService.getServicePath(PluginConstants.RISK_PLUGIN_NAME);
            req.setAttribute("riskproblemsPath",path);
        }
        boolean isKlmPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.KLM_PLUGIN_NAME);
        boolean isRDFlowPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RDFLOW_PLUGIN_NAME);
        
        req.setAttribute("isRiskPluginValid", isRiskPluginValid);
        req.setAttribute("isKlmPluginValid", isKlmPluginValid);
        req.setAttribute("isRDFlowPluginValid", isRDFlowPluginValid);
        return new ModelAndView(url);
    }
    
    /**
     * 添加最近访问到项目列表
     * 
     * @param projectId
     */
    private void updateResentProject(String projectId) {
        TSUserDto user = UserUtil.getInstance().getUser();
        if (StringUtils.isNotEmpty(projectId)) {
            // 判断用户为项目团队成员后，才能加“最近访问”
            List<TSUserDto> userlist = projRoleService.getUserInProject(projectId);
            if (StringUtils.isNotEmpty(user.getId())) {
                for (TSUserDto tsUser : userlist) {
                    if (user.getId().equals(tsUser.getId())) {
                        recentlyProjectService.updateRecentlyByProjectId(projectId,user);
                        break;
                    }
                }
            }
        }
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
        plan = planService.getPlanEntity(plan.getId());
        //手动添加负责人
        if (StringUtils.isNotEmpty(plan.getOwner())) {
            TSUserDto ownerInfo = userService.getUserByUserId(plan.getOwner());
            plan.setOwnerInfo(ownerInfo);
        }
        //手动添加下达人
        if (StringUtils.isNotEmpty(plan.getAssigner())) {
            TSUserDto assignerInfo = userService.getUserByUserId(plan.getAssigner());
            plan.setAssignerInfo(assignerInfo);
        }
        if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
            PlanDto planTemp = planService.getPlanEntity(plan.getParentPlanId());
            plan.setParentPlanName(planTemp.getPlanName());
        }
        Project project = new Project();
        if (CommonUtil.isEmpty(plan.getProject())) {
            if (StringUtils.isNotEmpty(plan.getProjectId())) {
                project = projectService.getProjectEntity(plan.getProjectId());
            }
        }
        else {
            project = plan.getProject();
        }
        EpsConfig epsInfo = new EpsConfig();
        if (!CommonUtil.isEmpty(project)) {
            if (CommonUtil.isEmpty(project.getEpsInfo())) {
                if (StringUtils.isNotEmpty(project.getEps())) {
                    String epsConfigStr = epsConfigService.getEpsConfig(project.getEps());
                    epsInfo = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsConfigStr),new TypeReference<EpsConfig>(){});
                }
            }
            else {
                epsInfo = project.getEpsInfo();
            }
        }
        String epsName = "";
        if (!CommonUtil.isEmpty(epsInfo)) {
            String epsPath = epsInfo.getPath();
            if (StringUtils.isNotEmpty(epsPath)) {
                epsPath = epsPath.substring(1);
                String epsConfigStr = epsConfigService.searchTreeNode(new EpsConfig());
                List<EpsConfig> epsList =JSON.parseObject(JsonFromatUtil.formatJsonToList(epsConfigStr),new TypeReference<List<EpsConfig>>(){});
                Map<String, String> epsConfigMap = new HashMap<String, String>();
                for (EpsConfig config : epsList) {
                    epsConfigMap.put(config.getId(), config.getName());
                }
                String[] epsPathArr = epsPath.split("/");
                for (int i = 0; i < epsPathArr.length; i++ ) {
                    if(StringUtils.isNotEmpty(epsConfigMap.get(epsPathArr[i]))){
                        if(StringUtils.isNotEmpty(epsName)){
                            epsName = epsName + "/" + epsConfigMap.get(epsPathArr[i]);
                        }
                        else{
                            epsName = epsConfigMap.get(epsPathArr[i]);
                        }
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(epsName)){
            project.setEpsName(epsName);
            plan.setProject(project);
        }

        // 通过计划获得前置，并把前置组装
        List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(plan);
        // 组装前置数据
        StringBuffer preposeSb = new StringBuffer();
        int k = 0;
        for (PreposePlanDto preposePlan : preposePlanList) {
            if (k > 0) {
                preposeSb.append(",");
            }
            if (preposePlan.getPreposePlanInfo() == null) {
                continue;
            }
            preposeSb.append(preposePlan.getPreposePlanInfo().getPlanName());
            k++ ;
        }
        plan.setPreposePlans(preposeSb.toString());

        // 获取计划的生命周期
        PlanDto statusP = new PlanDto();
        planService.initPlan(statusP);
        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeCycleStatusStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
        req.setAttribute("statusList", statusList);

        DeliverablesInfoDto document = new DeliverablesInfoDto();
        deliverablesInfoService.initDeliverablesInfo(document);
        
        
//        List<LifeCycleStatus> documentStatusList = document.getPolicy().getLifeCycleStatusList();
//        String documentStatusListStr = deliverablesInfoService.getLifeCycleStatusList();
//        List<LifeCycleStatus> documentStatusList = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeCycleStatusStr),new TypeReference<List<LifeCycleStatus>>(){});
        FeignJson documentStatusListFeignJson = deliverablesInfoService.getLifeCycleStatusList();
        String documentStatusListStr = String.valueOf(documentStatusListFeignJson.getObj());
        List<LifeCycleStatus> documentStatusList = JSON.parseArray(documentStatusListStr,LifeCycleStatus.class);       
        req.setAttribute("documentStatusList", documentStatusList);

        if (!CommonUtil.isEmpty(plan.getOwnerInfo()) && !CommonUtil.isEmpty(plan.getCreateOrgId())) {
            //初始化责任部门：
            TSDepartDto curTSDepartDto = deptService.getTSDepartById(appKey, plan.getCreateOrgId());
            plan.setOwnerDept(curTSDepartDto.getDepartname());
            // plan.setOwnerName(plan.getOwnerInfo().getRealName());
//            if (plan.getOwnerInfo().getTSDepart() != null) {
//                plan.setOwnerDept(plan.getOwnerInfo().getTSDepart().getDepartname());
//            }
        }

        for (LifeCycleStatus status : statusList) {
            if (status.getName().equals(plan.getBizCurrent())) {
                plan.setStatus(status.getTitle());
                break;
            }
        }

        if ("true".equals(plan.getMilestone())) {
            plan.setMilestoneName("是");
        }
        else {
            plan.setMilestoneName("否");
        }
        if (StringUtils.isEmpty(plan.getProgressRate())) {
            plan.setProgressRate("0.00");
        }
        // 若研发任务已完成或已废弃，则显示研发任务自己的状态；
        // 若研发任务 非已完成和已废弃状态、且项目为已关闭或已暂停，则显示项目状态
        if (!PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
            && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
            if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())) {
                plan.setStatus(ProjectStatusConstants.PAUSE_CHI);
            }
            else if (ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())) {
                plan.setStatus(ProjectStatusConstants.CLOSE_CHI);
            }
        }
        req.setAttribute("plan_", plan);
        boolean kddValid = pluginValidateService.isValidatePlugin(IDSPluginConstants.KES_PLUGIN_KDD);
        req.setAttribute("kddValid", kddValid);
        return new ModelAndView("com/glaway/ids/pm/project/task/taskDetailBasic-check");
    }
    
    /**
     * 判断计划交付项是否全部挂接
     * 
     * @param plan
     * @return
     */
    @RequestMapping(params = "judgePlanAllDocumant")
    @ResponseBody
    public AjaxJson judgePlanAllDocumant(PlanDto plan, HttpServletRequest request,
                                         HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        Object[] arguments = new String[] {
            I18nUtil.getValue("com.glaway.ids.pm.project.task.checkPlanExist"),
            I18nUtil.getValue("com.glaway.ids.pm.project.task.deliverablesInfo")};
        String message = I18nUtil.getValue("com.glaway.ids.common.search.success", arguments);
        try {
            String isOut = request.getParameter("isOut");
            Integer flag = judgePlanAllDocumant(plan,isOut);
            j.setObj(flag);
        }
        catch (Exception e) {
            message = "判断失败,有异常";
//            Object[] params = new Object[] {message,
//                PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
//            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 判断计划交付项是否全部挂接
     * 
     * @param plan
     * @return
     */
    private Integer judgePlanAllDocumant(PlanDto plan,String isOut) {
       /* Integer planFeedbackFlag = null;

        Plan newPlan = new Plan();
        newPlan.setParentPlanId(plan.getId());
        List<Plan> list = planService.queryPlansExceptInvalid(newPlan);
        long planCount = list.size();

        DeliverablesInfo deliverablesInfo = new DeliverablesInfo();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setAvaliable("1");
        List<DeliverablesInfo> deliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);
        
        //如果要判断计划完工与交付项状态关联的话加入下列判断，可以与下面的判断是否有交付物合并
        // 如果是叶子节点的底层任务并且有交付项
        if (planCount == 0 && deliverablesList != null && deliverablesList.size() > 0) {
            for (DeliverablesInfo childDeli : deliverablesList) {
                //判断交付物的状态，如果有一个状态不是最终状态，就break出去， planFeedbackFlag = 4; 
            }
        }
        else if (planCount > 0 && (deliverablesList == null || deliverablesList.size() <= 0)) {
         // 有子但本身无交付项
        }
        else if (planCount > 0 && deliverablesList != null && deliverablesList.size() > 0) {
            List<Plan> childList = planService.queryPlansExceptInvalid(newPlan);
          //拿到所有的子计划
            for (Plan child : childList) {
                DeliverablesInfo deliverablesInfoTwo = new DeliverablesInfo();
                deliverablesInfoTwo.setUseObjectId(child.getId());
                deliverablesInfoTwo.setAvaliable("1");
                //拿到每个子计划的交付物
                List<DeliverablesInfo> deliverablesTwoList = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfoTwo, 1, 10, false);
                planFeedbackFlag = 3; // 有子，本身有交付项。子全部挂接成功
                if (deliverablesTwoList != null && deliverablesTwoList.size() > 0) { // 子计划无交付项
                    for (DeliverablesInfo childDeli : deliverablesList) {
                      //判断交付物的状态，如果有一个状态不是最终状态，就break出去， planFeedbackFlag = 4; 
                    }
                }
            }
        }

        // 如果是叶子节点的底层任务并且有交付项
        if (planCount == 0 && deliverablesList != null && deliverablesList.size() > 0) {
            planFeedbackFlag = 0; // 无子有交付项全部挂接
            for (DeliverablesInfo childDeli : deliverablesList) {
                if (StringUtils.isEmpty(childDeli.getFileId())) {
                    planFeedbackFlag = 1; // 无子有交付项没有全部挂接
                }
            }
        }
        else if (planCount > 0 && (deliverablesList == null || deliverablesList.size() <= 0)) {
            planFeedbackFlag = 2; // 有子但本身无交付项
        }
        else if (planCount > 0 && deliverablesList != null && deliverablesList.size() > 0) {
            List<Plan> childList = planService.queryPlansExceptInvalid(newPlan);
            for (Plan child : childList) {
                DeliverablesInfo deliverablesInfoTwo = new DeliverablesInfo();
                deliverablesInfoTwo.setUseObjectId(child.getId());
                deliverablesInfoTwo.setAvaliable("1");
                List<DeliverablesInfo> deliverablesTwoList = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfoTwo, 1, 10, false);
                planFeedbackFlag = 3; // 有子，本身有交付项。子全部挂接成功
                if (deliverablesTwoList != null && deliverablesTwoList.size() > 0) { // 子计划无交付项
                    for (DeliverablesInfo childDeli : deliverablesList) {
                        if (StringUtils.isEmpty(childDeli.getFileId())) {
                            planFeedbackFlag = 4; // 有子，本身有交付项。子交付项没有全部挂接
                        }
                    }
                }
                else {
                    planFeedbackFlag = 4; // 有子，本身有交付项。子无交付项
                }
            }
        }*/
        return deliverablesInfoService.getJudgePlanAllDocumantWithStatus(plan,isOut);
    }

    @RequestMapping(params = "goInputCheck")
    public ModelAndView goInputCheck(InputsDto inputs, HttpServletRequest req) {
        // 从修改页面或者查看页面切换到该TAB、直接用req.getParameter中的值
        req.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        req.setAttribute("planId", req.getParameter("useObjectId"));
        req.setAttribute("projectId", req.getParameter("projectId"));
        return new ModelAndView("com/glaway/ids/project/task/taskInputs-check");
    }

    /**
     * 输入查询
     *
     * @param plan
     * @param request
     * @param response
     */
    @RequestMapping(params = "getInputsRelationList")
    @ResponseBody
    public void getInputsRelationList(PlanDto plan, HttpServletRequest request,
                                      HttpServletResponse response) {
        int page = 1;
        int rows = 10;
        if(!CommonUtil.isEmpty(request.getParameter("page"))) {
            page = Integer.valueOf(request.getParameter("page"));
            rows = Integer.valueOf(request.getParameter("rows"));
        }
        String projectId = request.getParameter("projectId");
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson j = inputsRemoteFeignService.getInputsRelationList(plan,page,rows,projectId,userId);
        long count = 0;
        String json = "";
        if (j.isSuccess()) {
            Map<String,Object> attrMap = j.getAttributes();
            count = Integer.valueOf(attrMap.get("count").toString());
            json = String.valueOf(attrMap.get("json"));
        }
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * @param plan
     * @param deliverablesInfo
     * @param req
     * @param session
     * @return
     */
    @RequestMapping(params = "goDocumentCheck")
    public ModelAndView goDocumentCheck(PlanDto plan, DeliverablesInfoDto deliverablesInfo,
                                        HttpServletRequest req, HttpSession session) {
        // 从修改页面或者查看页面切换到该TAB、直接用req.getParameter中的值
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        // 获取交付项的生命周期
        DeliverablesInfoDto document = new DeliverablesInfoDto();
        deliverablesInfoService.initBusinessObject(document);
        req.setAttribute("viewType", req.getParameter("viewType"));
        req.setAttribute("planId", req.getParameter("useObjectId"));
        session.setAttribute("planId", req.getParameter("useObjectId"));
        TSUserDto curruntUser = UserUtil.getInstance().getUser();
        plan = planService.getPlanEntity(req.getParameter("useObjectId"));
        String owner = "false";
        if (!CommonUtil.isEmpty(plan) && curruntUser.getId().equals(plan.getOwner())) {
            owner = "true";
        }
        req.setAttribute("isOwner", owner);
        if (StringUtils.isNotEmpty(req.getParameter("useObjectId"))) {
            plan = planService.getPlanEntity(req.getParameter("useObjectId"));
            req.setAttribute("plan_", plan);
        }
        // req.setAttribute("documentStatusList", jonStr);
        req.setAttribute("projectId", plan.getProjectId());

        // 计划是否分解
        boolean hasChild = true;
        PlanDto condition = new PlanDto();
        condition.setParentPlanId(plan.getId());
        List<PlanDto> childList = planService.queryPlanList(condition, 1, 10, false);
        if (CommonUtil.isEmpty(childList)) {
            hasChild = false;
        }

        if (hasChild || ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())
                || ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())
                || PlanConstants.PLAN_LOGINFO_SPLIT.equals(plan.getOpContent())
                || PlanConstants.PLAN_LOGINFO_FLOW_SPLIT.equals(plan.getOpContent())) {
            req.setAttribute("output_status", "pause");
        }
        else {
            req.setAttribute("output_status", "true");
        }
        String taskNameTypeRisk = "";
        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);
        if (!CommonUtil.isEmpty(types)) {
            for (TSTypeDto type : types) {
                if (type.getTypename().contains(PlanConstants.TASKNAMETYPE_RISK_CHI)) {
                    taskNameTypeRisk = type.getTypecode();
                    break;
                }
            }
        }
        //判断riskproblem插件是否有效
        boolean isRiskPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.RISK_PLUGIN_NAME);

        req.setAttribute("taskNameTypeRisk", taskNameTypeRisk);
        req.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        req.setAttribute("isRiskPluginValid", isRiskPluginValid);
        req.setAttribute("entityName", BpmnConstants.BPMN_ENTITIY_NAME_PROJECT_FILE);
        req.setAttribute("businessType", BpmnConstants.OBJECT_BUSINESS_BPMN_LINK_BUSINESSTYPE);
        req.setAttribute("currentUserId", UserUtil.getInstance().getUser().getId());
        return new ModelAndView("com/glaway/ids/pm/project/task/taskDocument-check");
    }

    /**
     * 项目计划页面初始化时获取项目库
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "getDocRelationList")
    @ResponseBody
    public void getDocRelationList(PlanDto plan, HttpServletRequest request,
                                   HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<ProjDocVo> ProjDocRelationList = new ArrayList<ProjDocVo>();
        long count = 0;
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        int page = 1;
        int rows = 10;
        if(!CommonUtil.isEmpty(request.getParameter("page")) && !CommonUtil.isEmpty(request.getParameter("rows"))) {
            page = Integer.valueOf(request.getParameter("page"));
            rows = Integer.valueOf(request.getParameter("rows"));
        }        
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, page, rows, true);
        count = deliverablesInfoService.getCount(deliverablesInfo);

        if (!CommonUtil.isEmpty(deliverablesList)) {
            // 获取其子计划的所有输出、并将其存入deliverablesMap
            Map<String, ProjDocRelationDto> deliverablesMap = new HashMap<String, ProjDocRelationDto>();
            deliverablesMap = deliverablesInfoService.queryFinishDeliverable(plan.getId());

            for (DeliverablesInfoDto parentDeli : deliverablesList) {
                ProjDocVo projDocVo = new ProjDocVo();
                projDocVo.setId(parentDeli.getId());
                projDocVo.setDeliverableId(parentDeli.getId());
                projDocVo.setDeliverableName(parentDeli.getName());
                if (StringUtils.isNotEmpty(parentDeli.getOrginType())&&"PLM".equals(parentDeli.getOrginType())){
                    projDocVo.setVersion(parentDeli.getVersionCode());
                    projDocVo.setStatus(parentDeli.getStatusCode());
                    projDocVo.setDocName(parentDeli.getDocName());
                    projDocVo.setDocId(parentDeli.getDocId());
                    projDocVo.setOrginType(parentDeli.getOrginType());
                    projDocVo.setFileType(parentDeli.getFileType());
                    ProjDocRelationList.add(projDocVo);
                }else{
                    if (deliverablesMap.get(parentDeli.getName()) != null) { // 如果在子计划中包含交付项，需要使用子计划的交付项
                        ProjDocRelationDto projDocRelation = (ProjDocRelationDto) deliverablesMap.get(parentDeli.getName());
                        //出输入挂接交付项标志
                        projDocVo.setInputViewFlog("true");
                        convertToVo(projDocVo, projDocRelation, plan.getId());
                        projDocVo.setDeliverableName(parentDeli.getName());
                        projDocVo.setOpFlag(false);
                        ProjDocRelationList.add(projDocVo);
                    }
                    else { // 如果子计划中没有的，需要查询自己的交付项
                        List<ProjDocRelationDto> ProjDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
                        if (ProjDocRelationDbList != null && ProjDocRelationDbList.size() > 0) {
                            //出输入挂接交付项标志
                            projDocVo.setInputViewFlog("true");
                            convertToVo(projDocVo, ProjDocRelationDbList.get(0), plan.getId());
                        }
                        projDocVo.setOpFlag(true);
                        ProjDocRelationList.add(projDocVo);
                    }
                }
            }
        }
        String json = gson.toJson(ProjDocRelationList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 组装挂接交付项的VO对象
     *
     * @param projDocRelation
     * @return
     */
    private ProjDocVo convertToVo(ProjDocVo projDocVo, ProjDocRelationDto projDocRelation,
                                  String planId) {
        if (StringUtils.isEmpty(projDocRelation.getDocId())) {
            return projDocVo;
        }
        projDocVo.setDocId(projDocRelation.getDocId());

        //接口获取RepFile塞入对象
        if (StringUtils.isNotBlank(projDocRelation.getDocId())) {
            RepFileDto repFileDto = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(),projDocRelation.getDocId());
            projDocRelation.setRepFile(repFileDto);
        }

        //if(StringUtils.isEmpty(projDocVo.getInputViewFlog())){
        FeignJson havePowerFj = planService.getOutPowerFj(projDocRelation.getDocId(), planId,UserUtil.getCurrentUser().getId());
        String havePower = "";
        if (havePowerFj.isSuccess()) {
            havePower = havePowerFj.getObj() == null ? "" : havePowerFj.getObj().toString();
        }
        if ("downloadDetail".equals(havePower)) {
            projDocVo.setDownload(true);
            projDocVo.setDetail(true);
            projDocVo.setHavePower(true);
        }
        else if ("detail".equals(havePower)) {
            projDocVo.setDownload(false);
            projDocVo.setDetail(true);
            projDocVo.setHavePower(true);
        }
        else {
            projDocVo.setDownload(false);
            projDocVo.setDetail(false);
            projDocVo.setHavePower(false);
        }
        //修改BUG 如果在文档中已经控制了权限但项目负责人不在权限中  但是要查看的账户是项目的负责人   提供负责人查看和下载
        String userId = UserUtil.getInstance().getUser().getId();
        PlanDto plan=planService.getPlanEntity(planId);
        if(plan.getOwner().equals(userId)){
            projDocVo.setDownload(true);
            projDocVo.setDetail(true);
            projDocVo.setHavePower(true);
        }

        if (projDocRelation.getRepFile() == null && !CommonUtil.isEmpty(projDocRelation.getDocId())) {
            RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, projDocRelation.getDocId());  
            projDocRelation.setRepFile(rep);
        }
        projDocVo.setDocName(projDocRelation.getRepFile().getFileName());
        projDocVo.setFileTypeId(projDocRelation.getRepFile().getFileTypeId());
        projDocVo.setDocCreateBy(projDocRelation.getRepFile().getCreateBy());
        projDocVo.setVersion(projDocRelation.getRepFile().getBizVersion());

        String policyId = "";
        String approveStatus = "";
        if (!CommonUtil.isEmpty(projDocRelation.getRepFile().getPolicy())) {
            policyId =  projDocRelation.getRepFile().getPolicy().getId();
            approveStatus = lifeCycleStatusService.getTitleByPolicyIdAndName(policyId,projDocRelation.getRepFile().getBizCurrent());
        }
        projDocVo.setSecurityLevel(projDocRelation.getRepFile().getSecurityLevel());
        projDocVo.setStatus(approveStatus);
        return projDocVo;
    }

    /**
     * 查看时、资源tab页
     *
     * @return
     */
    @RequestMapping(params = "goResourceCheck")
    public ModelAndView goResourceCheck(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        PlanDto plan = planService.getPlanEntity(deliverablesInfo.getUseObjectId());
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        String startTime = DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD);
        String endTime = DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD);
        req.setAttribute("startTime", startTime);
        req.setAttribute("endTime", endTime);
        return new ModelAndView("com/glaway/ids/project/task/taskResource-check");
    }

    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param resourceLinkInfo
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceList")
    public void resourceList(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                             HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        int page = Integer.valueOf(request.getParameter("page"));
        int rows = Integer.valueOf(request.getParameter("rows"));
        if (resourceLinkInfo != null && StringUtils.isNotEmpty(resourceLinkInfo.getUseObjectId())
                && StringUtils.isNotEmpty(resourceLinkInfo.getUseObjectType())) {
            List<ResourceLinkInfoDto> resourceLinkInfoList = resourceLinkInfoService.queryResourceList(
                    resourceLinkInfo, page, rows, true);
            for (ResourceLinkInfoDto info : resourceLinkInfoList) {
                if (info.getResourceInfo() != null) {
                    info.setResourceName(info.getResourceInfo().getName());
                    ResourceDto r = new ResourceDto();
                    r.setId(info.getResourceId());
                    List<ResourceDto> resourceListTemp = resourceService.searchUsables(r);
                    if (!CommonUtil.isEmpty(resourceListTemp)) {
                        info.setResourceType(resourceListTemp.get(0).getPath());
                    }
                }
            }
            long count = resourceLinkInfoService.getCount(resourceLinkInfo);
            String json = gson.toJson(resourceLinkInfoList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);

        }
    }

    @RequestMapping(params = "goFeedBack")
    public ModelAndView goFeedBack(HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(request.getParameter("progressRate"))) {
            request.setAttribute("progressRate", request.getParameter("progressRate"));
        }
        else {
            request.setAttribute("progressRate", TaskFeedbackConstants.TASK_FEEDBACK_RATE_FIRST);
        }
        PlanDto planDto = planService.getPlanEntity(request.getParameter("taskId"));
        request.setAttribute("plan_",planDto);
        request.setAttribute("taskId", request.getParameter("taskId"));
        request.setAttribute("planType", request.getParameter("planType"));
        request.setAttribute("isAllHasFile", request.getParameter("isAllHasFile"));
        request.setAttribute("plan_bizcurrent", request.getParameter("plan_bizcurrent"));
        return new ModelAndView("com/glaway/ids/pm/project/task/taskDetail-feedBack");
    }

    /**
     * 提交完工反馈
     *
     * @param request
     * @param response
     * @return
     * @throws GWException
     */
    @RequestMapping(params = "goSubmit")
    public ModelAndView goSubmit(HttpServletRequest request, HttpServletResponse response)
            throws GWException {
        String tabId = request.getParameter("tabId");
        request.setAttribute("taskId", request.getParameter("taskId"));
        PlanDto plan = planService.getPlanEntity(request.getParameter("taskId"));
        if(!CommonUtil.isEmpty(plan.getId())){
            request.setAttribute("taskName", plan.getPlanName());
        }

        String userId = "";
        try {
            userId = getUserIdByPlanId(request.getParameter("taskId"));
        }
        catch (GWException e) {
            log.warn(e.getMessage());
            throw new GWException(e.getMessage());
        }

        TSUserDto tsUser = userService.getUserByUserId(userId);
        if (tsUser == null) {
            log.warn(I18nUtil.getValue("com.glaway.ids.pm.project.task.parentOwnerEmpty"));
            throw new GWException(
                    I18nUtil.getValue("com.glaway.ids.pm.project.task.parentOwnerEmpty"));
        }
        request.setAttribute("leaderId", userId);
        request.setAttribute("leader", tsUser.getUserName());
        request.setAttribute("plan_", plan);
        request.setAttribute("tabId", tabId);
        return new ModelAndView("com/glaway/ids/pm/project/task/taskDetail-submit");
    }

    /**
     * Description: <br>
     * 634 通过计划查找上一负责人,如果是顶层计划则返回下达人<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param planId
     *            计划ID
     * @return
     * @see
     */
    private String getUserIdByPlanId(String planId)
            throws GWException {
        PlanDto plan = planService.getPlanEntity(planId);
        Object[] arguments = new String[] {"", ""};
        if (plan == null) {
            arguments = new String[] {
                    I18nUtil.getValue("com.glaway.ids.pm.project.task.currentPlan"),
                    I18nUtil.getValue("com.glaway.ids.pm.project.task.inexistence")};
            throw new GWException(I18nUtil.getValue(
                    "com.glaway.ids.pm.project.task.communicateWithAdmin", arguments));
        }

        if (StringUtils.isNotEmpty(plan.getParentPlanId())) { // 如果有上级计划，则查找上级计划的负责人
            PlanDto parentPlan = planService.getPlanEntity(plan.getParentPlanId());
            if (parentPlan == null) {
                arguments = new String[] {
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.parentPlan"),
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.inexistence")};
                throw new GWException(I18nUtil.getValue(
                        "com.glaway.ids.pm.project.task.communicateWithAdmin", arguments));
            }

            if (StringUtils.isEmpty(parentPlan.getOwner())) {
                arguments = new String[] {
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.parentOwner"),
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.inexistence")};
                throw new GWException(I18nUtil.getValue(
                        "com.glaway.ids.pm.project.task.communicateWithAdmin", arguments));
            }
            return parentPlan.getOwner();
        }
        else { // 如果没有则返回下达人

            if (plan.getAssigner() == null) {
                arguments = new String[] {
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.currentPlan"),
                        I18nUtil.getValue("com.glaway.ids.pm.project.task.noAssigner")};
                throw new GWException(I18nUtil.getValue(
                        "com.glaway.ids.pm.project.task.communicateWithAdmin", arguments));
            }
            return plan.getAssigner();
        }
    }

    /**
     * 项目计划流程查看页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goCheckFlow")
    public ModelAndView goCheckFlow(PlanDto plan, HttpServletRequest req) {
        String planId = req.getParameter("taskNumber");
        if (StringUtils.isNotEmpty(planId)) {
            plan = planService.getPlanEntity(planId);
            req.setAttribute("plan_", plan);
        }
        String nameStandardId = "";
        req.setAttribute("isOut", "2");
        if(!CommonUtil.isEmpty(plan.getTaskNameType())) {
            if(!CommonUtil.isEmpty(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(plan.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(plan.getTaskNameType())) {
                req.setAttribute("isOut", "1");
                NameStandardDto nameStandard = new NameStandardDto();
                nameStandard.setName(plan.getPlanName());
                List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                if(!CommonUtil.isEmpty(list)){
                    nameStandardId = list.get(0).getId();
                }
            }
        }
        req.setAttribute("nameStandardId", nameStandardId);
        req.setAttribute("status", "pause");
        return new ModelAndView("com/glaway/ids/pm/project/task/taskDetail-view");
    }

    /**
     * 项目计划编辑页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goUpdateFlow")
    public ModelAndView goUpdateFlow(PlanDto plan, HttpServletRequest req) {
        String planId = req.getParameter("taskNumber");
        String taskId = req.getParameter("taskId");
        // String procInstId = req.getParameter("procInstId");
        req.getSession().setAttribute("planIdSave", planId);
        req.getSession().setAttribute("taskIdSave", taskId);
        if (StringUtils.isNotEmpty(planId)) {
            plan = planService.getPlanEntity(planId);
            req.setAttribute("plan_", plan);
        }
        Map<String, Object> variables = workFlowFacade.getWorkFlowCommonService().getVariablesByTaskId(
                taskId);

        Map<String, TSUserDto> userMap = userService.getAllUsersMap();
        if(!CommonUtil.isEmpty(variables.get("leader"))){
            TSUserDto user = userMap.get(variables.get("leader"));
            if (!CommonUtil.isEmpty(user)) {
                req.setAttribute("leaderId", user.getId());
                req.setAttribute("leaderUserName", user.getUserName());
                req.setAttribute("leaderRealName", user.getRealName());
            } else {
                req.setAttribute("leaderId", "");
                req.setAttribute("leaderUserName", "");
                req.setAttribute("leaderRealName", "");
            }
        }

        return new ModelAndView("com/glaway/ids/pm/project/task/taskDetail-update");
    }

    /**
     * 跳转到下发评审任务模版
     *
     * @param req
     * @return
     */
    @RequestMapping(params = "goOrderReviewTaskDialog")
    private ModelAndView goOrderReviewTaskDialog(HttpServletRequest req) {

        String taskId = req.getParameter("taskId");
        PlanDto plan = planService.getPlanEntity(taskId);
        req.setAttribute("plan_", plan);
        return new ModelAndView("com/glaway/ids/pm/project/task/orderReviewTask");
    }

    /**
     * 评审类型下拉框
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "reviewTypeCombo")
    @ResponseBody
    public void reviewTypeCombo(HttpServletRequest request, HttpServletResponse response) {
        FeignJson fj = reviewFeignService.getReciewTypeList();
        String jonStr = "";
        if (fj.isSuccess()) {
            jonStr = fj.getObj() == null ? "" : fj.getObj().toString();
        }

        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下发评审任务
     *
     * @param request
     * @param reviewTask
     * @return
     */
    @RequestMapping(params = "orderReviewTask")
    @ResponseBody
    public FeignJson orderReviewTask(HttpServletRequest request, PlanDto reviewTask) {
        CommonUtil.getInputSearchUserIds(reviewTask, request);
        String orderReviewTaskType = request.getParameter("orderReviewTaskType");
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson j = planService.orderReviewTask(reviewTask,orderReviewTaskType,userId);
        return j;
    }


    /**
     * 判断计划是否有挂接过交付项
     *
     * @param plan
     * @return
     */
    @RequestMapping(params = "judgePlanDocumant")
    @ResponseBody
    public AjaxJson judgePlanDocumant(PlanDto plan, HttpServletRequest request,
                                      HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "判断计划是否有挂接过交付项成功";
        try {
            boolean flag = judgePlanDocumant(plan);
            j.setObj(flag);

        }
        catch (Exception e) {
            message = "判断计划是否有挂接过交付项失败";
            Object[] params = new Object[] {message,
                    PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 判断计划是否有挂接过交付项
     *
     * @param plan
     * @return
     */
    private boolean judgePlanDocumant(PlanDto plan) {
        // 获取计划的输出
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);

        if (CommonUtil.isEmpty(deliverablesList)) { // 如果没有交付项，可以分解
            return false;
        }
        for (DeliverablesInfoDto childDeli : deliverablesList) {
            List<ProjDocRelationDto> ProjDocRelationList = projLibService.getDocRelation(childDeli.getId());
            if (ProjDocRelationList != null && ProjDocRelationList.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 任务参考知识页面跳转taskDetailController
     * 
     * @see
     */
    @RequestMapping(params = "goReferenceCheck")
    public ModelAndView goReferenceCheck(HttpServletRequest req) {
        String planId = req.getParameter("useObjectId");
        String refrenceCellId = req.getParameter("refrenceCellId");
        String isEnableFlag = req.getParameter("isEnableFlag");
        req.setAttribute("isEnableFlag", isEnableFlag);
        if(CommonUtil.isEmpty(refrenceCellId)) {
            req.setAttribute("planId", req.getParameter("useObjectId"));
//        Properties prop = PropertiesUtil.getProperties(KnowledgeSupportConstants.KNOWLEDGE_ADDRESS);
//        String openUrl = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
//                         + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_SEARCH_URL);
//        String openUrlView = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
//                             + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_VIEW_URL);
//        req.setAttribute("openUrl", "");
//        req.setAttribute("openUrlView", "");
            return new ModelAndView("com/glaway/ids/pm/project/task/taskReference-check-view");
        }else{
            req.setAttribute("refrenceCellId", refrenceCellId);
            return new ModelAndView("com/glaway/ids/pm/project/task/taskCellTab-reference");
        }
    }
    
    
    @RequestMapping(params = "getReferenceList")
    @ResponseBody
    public void getReferenceList(HttpServletRequest request, HttpServletResponse response,
                                 HttpSession session) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") // 时间转化为特定格式
        .setPrettyPrinting() // 对json结果格式化
        .create();
        String planId = request.getParameter("taskId");
        String type = request.getParameter("type");
        String url = "list";
        if(CommonUtil.isEmpty(type)){
            type = "planTask";
        }
        // 查询条件
        TaskInfoReq taskInfo = new TaskInfoReq();
        taskInfo.setTaskId(planId);
        taskInfo.setType(type);
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(CommonUtil.isEmpty(page) || CommonUtil.isEmpty(rows)){
            page = "1";
            rows = "10";
        }
        taskInfo.setPageSize(Integer.parseInt(page));
        taskInfo.setPageNum(Integer.parseInt(rows));
        JsonResult jsonResult = new JsonResult();
        try {
            getRuleForKnowledgeOperation(taskInfo);
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("jsonResult",jsonResult);
            paramsMap.put("taskInfoReq",taskInfo);
            jsonResult = planKnowledgeReferenceFeignService.findKnowledgeInfo(paramsMap);
        }
        catch (GWException e) {
            String errMsg = I18nUtil.getValue("com.glaway.ids.knowledge.support.viewTaskKnowledgeRecord")
                            + I18nUtil.getValue("com.glaway.ids.knowledge.support.failure");
            log.warn(errMsg);
            throw new GWException(errMsg);
        }
        long count = 0;
        String json = "";
        if (jsonResult.isSuccess()) {
            Map<String,Object> attrMap = (Map<String,Object>)jsonResult.getRetObj();
            count = Integer.valueOf(attrMap.get("total").toString());
            json = gson.toJson(attrMap.get("rows"));
        }

        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    public String getRuleForKnowledgeOperation(TaskInfoReq taskInfo){
//        Map<String, String> map = new HashMap<String, String>();
//        Facts facts = new Facts();
//        facts.put("taskInfo", taskInfo);
//        facts.put("map", map);
//        MVELRule knowledgeOperationRule = new MVELRule().name("planTask rule").priority(1).when("taskInfo.getType().equals('planTask')").then("map.put(\"flag\",\"1\")");
//        Rules rules = new Rules();
//        rules.register(knowledgeOperationRule);
//        RulesEngine rulesEngine = new DefaultRulesEngine();
//        rulesEngine.fire(rules, facts);
        //规则引用：
        Map<String, String> map = new HashMap<String, String>();
        RulesEngine groupEngine = new DefaultRulesEngine();
        Rules rules = new Rules();
        rules.register(new GroupRule(new PlanRule()));
        //调用规则：
        Facts facts = new Facts();
        map.put("taskReference","taskReference");
        facts.put("map", map);
        facts.put("taskInfo", taskInfo);
        groupEngine.fire(rules,facts);
        String flagStr = String.valueOf(map.get("flag"));
        if(flagStr.equals("true")){

            StringBuffer sbForKeyWords = new StringBuffer();

            // 通过任务编号获得任务名称
            PlanDto plan = planService.getPlanEntity(taskInfo.getTaskId());
            if (!CommonUtil.isEmpty(plan)) {
                if (StringUtils.isNotEmpty(plan.getPlanName())) {
                    sbForKeyWords.append(" " + plan.getPlanName());
                }
            }
            // 通过任务编号获得交付项
            PlanDto planNew = new PlanDto();
            planNew.setId(taskInfo.getTaskId());
            List<ProjDocVo> projDocRelationList = getDocRelationList(planNew);

            // 遍历交付项，组装成字符串
            StringBuffer sb = new StringBuffer();
            for (ProjDocVo projDocVo : projDocRelationList) {
                sb.append(" " + projDocVo.getDeliverableName());
            }
            if (StringUtils.isNotEmpty(sb.toString())) {
                sbForKeyWords.append(sb.substring(1));
            }

            if (!CommonUtil.isEmpty(plan)) {
                //计划类型
                if (StringUtils.isNotEmpty(plan.getPlanType())) {
                    sbForKeyWords.append(" " + plan.getPlanType());
                }
                //计划类别
                if (StringUtils.isNotEmpty(plan.getTaskNameType())) {
                    //获取计划类别对应的名称：
                    ActivityTypeManageDto activityTypeManageDto = activityTypeManageFeign.queryActivityTypeManageById(plan.getTaskNameType());
                    if(!CommonUtil.isEmpty(activityTypeManageDto)){
                        sbForKeyWords.append(" " + activityTypeManageDto.getName());
                    }

                }
                //项目名称
                if (StringUtils.isNotEmpty(plan.getProjectId())) {
                    //获取项目对应的名称：
                    Project project = projectService.getProjectEntity(plan.getProjectId());
                    if(!CommonUtil.isEmpty(project)) {
//                        taskInfo.setProjectName(project.getName());
                        sbForKeyWords.append(" " + project.getName());
                    }
                }
            }



            // 获得当前用户的密级
            TSUserDto user = UserUtil.getInstance().getUser();
            if (user != null && user.getSecurityLevel() != null) {
                sbForKeyWords.append(" " + user.getSecurityLevel());
            }
            taskInfo.setSearchWords(sbForKeyWords.substring(1));
        }
        return taskInfo.getSearchWords();
    }
    
    /**
     * 项目计划页面初始化时获取项目库
     * 
     * @param plan
     * @see
     */
    public List<ProjDocVo> getDocRelationList(PlanDto plan) {
        // 通过交付项判断子计划是否包括项目库
        // 获取计划的输出
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);

        if (CommonUtil.isEmpty(deliverablesList)) {
            return new ArrayList<ProjDocVo>();
        }

        // 获取其子计划的所有输出、并将其存入deliverablesMap
        Map<String, ProjDocRelationDto> deliverablesMap = new HashMap<String, ProjDocRelationDto>();
        deliverablesMap = deliverablesInfoService.queryFinishDeliverable(plan.getId());

        List<ProjDocVo> ProjDocRelationList = new ArrayList<ProjDocVo>();

        for (DeliverablesInfoDto parentDeli : deliverablesList) {
            ProjDocVo projDocVo = new ProjDocVo();
            projDocVo.setDeliverableId(parentDeli.getId());
            projDocVo.setDeliverableName(parentDeli.getName());
            if (deliverablesMap.get(parentDeli.getName()) != null) { // 如果在子计划中包含交付项，需要使用子计划的交付项
                ProjDocRelationDto projDocRelation = (ProjDocRelationDto)deliverablesMap.get(parentDeli.getName());
                convertToVo(projDocVo, projDocRelation, plan.getId());
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                ProjDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
                List<ProjDocRelationDto> ProjDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
                if (ProjDocRelationDbList != null && ProjDocRelationDbList.size() > 0) {
                    convertToVo(projDocVo, ProjDocRelationDbList.get(0), plan.getId());
                }
                projDocVo.setOpFlag(true);
                ProjDocRelationList.add(projDocVo);
            }
        }

        return ProjDocRelationList;
    }


    @RequestMapping(params = "goSubmitTaskFeedback")
    public ModelAndView goSubmitTaskFeedback(HttpServletRequest request){
        request.setAttribute("leaderId", request.getParameter("leaderId"));
        request.setAttribute("leader", request.getParameter("leaderUserName"));
        request.setAttribute("leaderRealName", request.getParameter("leaderRealName"));
        return new ModelAndView(
                "com/glaway/ids/pm/project/task/taskDetail-submit");
    }

    /**
     * 删除管参考知识
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "deleteReference")
    @ResponseBody
    public AjaxJson deleteReference(String code, String libId, String taskId,
                                    HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.project.task.clear"),
                ""};
        String message = I18nUtil.getValue("com.glaway.ids.common.search.success", arguments);
        String type = request.getParameter("type");
        String opType = request.getParameter("opType");
        KnowledgeInfoReq req = new KnowledgeInfoReq();
        req.setCode(code);
        req.setLibId(libId);
        req.setTaskId(taskId);
        req.setType(type);
        String userId = UserUtil.getInstance().getUser().getId();
        req.setUserId(userId);
        // req.setWorkId(workId);

        Gson gson = new Gson();
        JsonRequery jsonReq = new JsonRequery();
        jsonReq.setReqObj(gson.toJson(req));
        String jdelete = gson.toJson(jsonReq);
        JsonResult jsonResult = new JsonResult();
        try {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("jsonResult",jsonResult);

            paramsMap.put("knowledgeInfoReq",req);
            jsonResult = planKnowledgeReferenceFeignService.deleteKnowledgeInfo(paramsMap);
//            /*KnowledgeSupport support = SupportFactory.getInstance().getSupport(
//                KnowledgeSupport.class, false);*/
//            KnowledgeSupportImplService supportService = new KnowledgeSupportImplService();
//            KnowledgeSupport support = supportService.getKnowledgeSupportImplPort();
//            if(CommonUtil.isEmpty(opType)){
//                opType = "delete";
//            }
//            String listStr = support.operationKnowledge(jdelete, opType);
//            jsonResult = gson.fromJson(listStr, JsonResult.class);
            if (!"0".equals(jsonResult.getRetCode())) {
                j.setSuccess(false);
                message = I18nUtil.getValue("com.glaway.ids.common.search.failure", arguments);
                log.warn(jsonResult.getRetMsg());
            }

        }
        catch (Exception e) {
            log.warn(e.getMessage());
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.common.search.failure", arguments);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * index检索页面跳转
     *
     * @param
     * @param
     * @return ModelAndView
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "goSearchForIDS")
    public ModelAndView goSearchForIDS(HttpServletRequest req, HttpServletResponse res)
            throws UnsupportedEncodingException {
        String taskId = req.getParameter("taskId");
        String referenceType = req.getParameter("type");
        String searchType = req.getParameter("searchType");
        String searchQuery = req.getParameter("searchQuery");
        String input = req.getParameter("currentInput");
        String userId = UserUtil.getCurrentUser().getId();
        ModelAndView mv = new ModelAndView("com/glaway/ids/pm/project/task/knowledgeSearchForIDS");// 跳转的页面
        mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("sortElement", KnowledgeConstants.SEARCH_CONDITION_DEFAULT);
        mv.addObject("searchType", searchType);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(req.getParameter("httpFlag"))) {
            mv.addObject("httpFlag", req.getParameter("httpFlag"));
        }
        String isEnableFlag = req.getParameter("isEnableFlag");
        if(!"1".equals(isEnableFlag)){
            isEnableFlag = "";
        }
        mv.addObject("isEnableFlag", isEnableFlag);
        if(!CommonUtil.isEmpty(taskId) && !CommonUtil.isEmpty(referenceType)){
            mv.addObject("taskId", taskId);
            mv.addObject("referenceType", referenceType);
        }
        input = java.net.URLDecoder.decode(input, "UTF-8");
        if (!org.apache.commons.lang3.StringUtils.isEmpty(input)) {
            // 记录关键字
            try {
                SearchWordsVo vo = new SearchWordsVo();
                vo.setSearchWords(input);
                vo.setType(searchType);
                vo.setSearchQuery(searchQuery);
                attentionWordsRemoteService.saveAttentionWords(vo,userId);
            }
            catch (Exception e) {
                throw new GWException(
                        I18nUtil.getValue("com.glaway.kes.knowledge.keywordException"));
            }
            // 调用检索方法，获取返回的List的属性值
            doSearchForIds(req, res, input, mv);
            return mv;
        }
        else {
            doSearchForIds(req, res, input, mv);
            return mv;
        }
    }


    /**
     * 一般检索业务处理
     *
     * @param
     * @return
     */
    @SuppressWarnings({"finally", "unchecked"})
    @RequestMapping(params = "doSearchForIds")
    @ResponseBody
    public AjaxJson doSearchForIds(HttpServletRequest req, HttpServletResponse res, String input,
                             ModelAndView mv) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.kes.knowledge.searchSuccess");
        try {
            String searchType = req.getParameter("searchType");
            String pageNumber = req.getParameter("pageNumber");
            if (CommonUtil.isEmpty(pageNumber)){
                pageNumber = "1";
            }
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(input)) {


                HotWordsDto hotWords = new HotWordsDto();
                hotWords.setContent(input);
                hotWords.setType(searchType);
                feignHotWordsRemoteService.addHotWords(hotWords);
                // 记录热词
                List<String> hlList = new ArrayList<String>();
                hlList.add("title");
                hlList.add("abstruct");
                SearchInput searchInput = new SearchInput();
                searchInput.setQ(input.trim());
                searchInput.setResultClassType(HitDocVo.class);
                searchInput.setRows(10);
                Integer start = 0;
                if (Integer.valueOf(pageNumber) > 1){
                    start = Integer.valueOf(pageNumber) * 10;
                }
                searchInput.setStart(start);
                searchInput.setHl(hlList);
                SearchOutput sout = searchEngineClient.search(searchInput, searchType);

                List<HitDocVo> sdoc = new ArrayList<HitDocVo>();
                if(!CommonUtil.isEmpty(sout)){
                    sdoc = (List<HitDocVo>)sout.getDocs();
                }
                String solrQueryStr = input.trim();
                List<String> filterQueriesList = new ArrayList<String>();
                mv.addObject("input01", input);
                // 拼接知识分类的分隔符“、”
                if (!CommonUtil.isEmpty(sdoc)) {
                    mv.addObject("sdoc", sout);
                    sdoc = getFinalHitDocs(mv,sdoc);
                    mv.addObject("docs", sdoc);
                }
                else {
                    message = I18nUtil.getValue("com.glaway.kes.knowledge.indexFailure");
                }
                mv.addObject("pageNumber", pageNumber);
                mv.addObject("total", sout.getHits());
                // 获取热词
                HotWordsDto condition = new HotWordsDto();
                condition.setType(searchType);
                List<String> hotWordList = feignHotWordsRemoteService.queryTheMostHotWords(condition, 5);
                req.setAttribute("solrQueryStr", solrQueryStr);
                mv.addObject("hotWordList", hotWordList);
            }
            else{
                List<String> filterQueriesList = new ArrayList<String>();
                mv.addObject("input01", input);
                // 拼接知识分类的分隔符“、”
                SearchOutput sdoc = new SearchOutput();
                sdoc.setPage(1+"");
                sdoc.setPageSize(10 + "");
                mv.addObject("sdoc", sdoc);
                mv.addObject("docs", new ArrayList<HitDocVo>());
                // 获取热词
                HotWordsDto condition = new HotWordsDto();
                condition.setType(searchType);
                List<String> hotWordList = feignHotWordsRemoteService.queryTheMostHotWords(condition, 5);
                mv.addObject("hotWordList", hotWordList);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            Object[] params = new Object[] {message, input.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     *
     * 检索结果初始化展示
     *
     * @param
     * @param sdoc
     * @return
     * @see
     */
    private List<HitDocVo> getFinalHitDocs(ModelAndView mv,List<HitDocVo> sdoc){
        Map<String, Object> mvMap =  mv.getModel();
        List<KnowledgeReferenceDto> references = new ArrayList<KnowledgeReferenceDto>();
        String str = feignKnowledgeCategoryServiceI.getKnowledgeCategoryList(new KnowledgeCatgoryDto());
        List<KnowledgeCatgoryDto> knowledgeCatgorys = JSON.parseArray(JSONHelper.consume(str), KnowledgeCatgoryDto.class);
        Map<String, KnowledgeCatgoryDto> catgoryMap = new HashMap<String, KnowledgeCatgoryDto>();
        for (KnowledgeCatgoryDto catgory : knowledgeCatgorys) {
            catgoryMap.put(catgory.getId(), catgory);
        }
        Map<String, String> libraryNameMap = feignKnowledgeCategoryServiceI.getLibraryNameMap();

        String taskId = "";
        String referenceType = "";
        if(!CommonUtil.isEmpty(mvMap)){
            taskId = (String)mvMap.get("taskId");
            referenceType = (String)mvMap.get("referenceType");
            if(!CommonUtil.isEmpty(taskId)){
                KnowledgeReferenceDto knowledgeReference = new KnowledgeReferenceDto();
                knowledgeReference.setTaskId(taskId);
                references = feignKnowledgeCategoryServiceI.getKnowledgeReferenceList(knowledgeReference, 1, 10, false);
            }
        }
        Map<String, KnowledgeReferenceDto> referenceMap = new HashMap<String, KnowledgeReferenceDto>();
        if(!CommonUtil.isEmpty(references)){
            for(KnowledgeReferenceDto reference2 : references){
                referenceMap.put(reference2.getCode(), reference2);
            }
        }
        KnowledgeReferenceDto reference = new KnowledgeReferenceDto();

        for (int i = 0; i < sdoc.size(); i++ ) {
            if(!CommonUtil.isEmpty(referenceMap)){
                reference = referenceMap.get(sdoc.get(i).getCode());
                if(!CommonUtil.isEmpty(reference)
                        && sdoc.get(i).getLibrary().equals(reference.getLibId())){
                    if(!KnowledgeWebServiceConstants.KNOWLEDGE_REFERENCE_TYPE_FLOWTEMPLATE.equals(referenceType)
                            && KnowledgeWebServiceConstants.KNOWLEDGE_REFERENCE_TYPE_FLOWTEMPLATE.equals(reference.getType())){
                        sdoc.get(i).setFromTemplate("true");
                    }
                    sdoc.get(i).setChecked("true");
                }
            }
            // 分类
            sdoc.get(i).setCategorys(convertCategoryIdToName(sdoc.get(i), catgoryMap));
            // 来源库
            if (!CommonUtil.isEmpty(libraryNameMap)) {
                sdoc.get(i).setLibraryName(libraryNameMap.get(sdoc.get(i).getLibrary()));
            }
            if (!CommonUtil.isEmpty(sdoc.get(i).getCategorys())) {
                for (int n = 0; n < sdoc.get(i).getCategorys().size(); n++ ) {
                    if (n == sdoc.get(i).getCategorys().size() - 1) {
                        String cat = sdoc.get(i).getCategorys().get(n);
                        sdoc.get(i).getCategorys().set(n, cat);
                    }
                    else {
                        String cat = sdoc.get(i).getCategorys().get(n) + ",";
                        sdoc.get(i).getCategorys().set(n, cat);
                    }
                }
            }
        }
        return sdoc;
    }

    private List<String> convertCategoryIdToName(HitDocVo doc,
                                                 Map<String, KnowledgeCatgoryDto> catgoryMap) {
        List<String> categorys = new ArrayList<String>();
        if (!StringUtil.isEmpty(doc.getCategories())) {
            String[] categoriesArr = doc.getCategories().split(" ");
            for (String categoryId : categoriesArr) {
                KnowledgeCatgoryDto catgory = catgoryMap.get(categoryId);
                if (catgory != null) {
                    String realNamePath = "";
                    String path = catgory.getPath();
                    String[] ids = org.apache.commons.lang3.StringUtils.split(path, "/");
                    for (String cId : ids) {
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(cId)) {
                            KnowledgeCatgoryDto parentCatgory = catgoryMap.get(cId);
                            if (parentCatgory != null) {
                                realNamePath = realNamePath + "/" + parentCatgory.getName();
                            }
                        }
                    }
                    categorys.add(realNamePath);
                }
            }
        }
        return categorys;
    }


    /**
     * 检索页面中的一般检索
     *
     * @param req
     * @param res
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "goSearchAgain")
    public ModelAndView goSearchAgain(HttpServletRequest req, HttpServletResponse res,
                                      String inputIndex) {
        String searchType = req.getParameter("searchType");
        String searchQuery = req.getParameter("searchQuery");
        String isEnableFlag = req.getParameter("isEnableFlag");
        String taskId = req.getParameter("taskId");
        String userId = UserUtil.getCurrentUser().getId();
        ModelAndView mv = new ModelAndView("com/glaway/ids/pm/project/task/resultSearchForIDS");// 跳转的页面
        mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("sortElement", KnowledgeConstants.SEARCH_CONDITION_DEFAULT);
        mv.addObject("searchType", searchType);
        mv.addObject("isEnableFlag", isEnableFlag);
        mv.addObject("taskId", taskId);
        String referenceType = req.getParameter("referenceType");
        mv.addObject("referenceType", referenceType);
        String input = req.getParameter("inPut");
        try {
            input = java.net.URLDecoder.decode(input, "UTF-8");
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        // 记录关键字
        try {
            SearchWordsVo vo = new SearchWordsVo();
            vo.setSearchWords(input);
            vo.setType(searchType);
            vo.setSearchQuery(searchQuery);
            attentionWordsRemoteService.saveAttentionWords(vo,userId);
        }
        catch (Exception e) {
            throw new GWException(I18nUtil.getValue("com.glaway.ids.knowledge.keywordException"));
        }
        // 调用检索方法，获取返回的List的属性值
        doSearchForIds(req, res, input, mv);
        return mv;
    }

    /**
     * 检索结果排序
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "searchByElementType")
    public ModelAndView searchByElementType(HttpServletRequest request, HttpServletResponse response) {
        String searchType = request.getParameter("searchType");
        ModelAndView mv = new ModelAndView("com/glaway/ids/pm/project/task/resultSearchForIDS");// 跳转的页面
        String isEnableFlag = request.getParameter("isEnableFlag");
        String taskId = request.getParameter("taskId");
        mv.addObject("isEnableFlag", isEnableFlag);
        mv.addObject("taskId", taskId);
        mv.addObject("searchType", searchType);
        String referenceType = request.getParameter("referenceType");
        mv.addObject("referenceType", referenceType);
        // 排序字段
        String sortElement = request.getParameter("sortElement");
        // 排序DESC/ASC
        String sortType = request.getParameter("sortType");
        // 页码
        String pageNumber = request.getParameter("pageNumber");
        if (CommonUtil.isEmpty(pageNumber)){
            pageNumber = "1";
        }
        // 每页显示数量
        String pageSize = request.getParameter("pageSize");
        if (CommonUtil.isEmpty(pageSize)){
            pageSize = "10";
        }

        String solrQueryStr = request.getParameter("solrQueryStr");
        solrQueryStr = URLDecoder.decode(solrQueryStr, "UTF-8");
        mv.addObject("solrQueryStr", solrQueryStr);
        List<String> hlList = new ArrayList<String>();
        hlList.add("title");
        hlList.add("author");
        hlList.add("abstruct");
        SearchInput input = new SearchInput();
        input.setHl(hlList);
        input.setResultClassType(HitDocVo.class);
        boolean isOrder = false;
        boolean isPaging = false;
        // SolrOutputDoc sdoc = new SolrOutputDoc();
        SearchOutput sout = new SearchOutput();
        // 计算翻页数据
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(pageNumber) && org.apache.commons.lang3.StringUtils.isNotEmpty(pageSize)) {
            input.setRows(Integer.valueOf(pageSize));
            input.setStart(Integer.valueOf(pageSize) * (Integer.valueOf(pageNumber) - 1));
            isPaging = true;
        }
        // 设置排序属性
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sortElement) && org.apache.commons.lang3.StringUtils.isNotEmpty(sortType)) {
            isOrder = true;
            if (!KnowledgeConstants.SEARCH_CONDITION_DEFAULT.equals(sortElement)) {
                if (!isPaging) {
                    sortType = getAdverseSortType(sortType);
                }
                if (KnowledgeConstants.SEARCH_CONDITION_UPDATETIME.equals(sortElement)) {
                    mv.addObject("updateTimeSortType", sortType);
                    mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
                }
                else {
                    mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
                    mv.addObject("authorNameSortType", sortType);
                }
                input.setSort(sortElement + " " + sortType);
            }
            else {
                mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
                mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
            }
            mv.addObject("sortElement", sortElement);
        }
        else {
            mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
            mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
            mv.addObject("sortElement", KnowledgeConstants.SEARCH_CONDITION_DEFAULT);
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(solrQueryStr)) { // 如果搜索框为空。则为高级搜索
//            AdvancedCondition advancedContion = (AdvancedCondition)request.getSession().getAttribute(
//                    ResourceUtil.getSessionId() + "_advancedContion");
//            // sdoc = solrSearchService.advancedSearch(input, advancedContion);
//            sout = client.advancedSearch(input, advancedContion);
        }
        else {
            List<String> filterQueriesList = (List<String>)request.getSession().getAttribute(
                    ResourceUtil.getSessionId() + "_filterQueriesList");
            // sdoc = solrSearchService.resultSearch(input, solrQueryStr, filterQueriesList);
            hlList.add("title");
            hlList.add("abstruct");
            SearchInput searchInput = new SearchInput();
//                searchInput.setQ(input.trim());
            searchInput.setResultClassType(HitDocVo.class);
            searchInput.setRows(10);
            Integer start = 0;
            if (Integer.valueOf(pageNumber) > 1){
                start = Integer.valueOf(pageNumber) * 10;
            }
            searchInput.setHl(hlList);
//                SearchOutput sout = searchEngineClient.search(searchInput, searchEngineConfigs.get(0).getCatalog());

            sout = searchEngineClient.resultSearch(input, solrQueryStr, filterQueriesList,searchType);
        }
        List<HitDocVo> sdoc = new ArrayList<HitDocVo>();
        if(!CommonUtil.isEmpty(sout)){
            sdoc = (List<HitDocVo>)sout.getDocs();
        }
        mv.addObject("pageNumber", pageNumber);
        mv.addObject("total", sout.getHits());

        // 拼接知识分类的分隔符“、”
        if (sdoc != null) {
            mv.addObject("sdoc", sout);
            sdoc = getFinalHitDocs(mv,sdoc);
            mv.addObject("docs", sdoc);
            // 获取热词
            HotWordsDto condition = new HotWordsDto();
            condition.setType(searchType);
            List<String> hotWordList = feignHotWordsRemoteService.queryTheMostHotWords(condition, 5);
            request.setAttribute("solrQueryStr", solrQueryStr);
            mv.addObject("hotWordList", hotWordList);
        }
        return mv;
    }

    /**
     * 将sortType取反
     *
     * @param sortType
     * @return
     * @see
     */
    private String getAdverseSortType(String sortType) {
        if (KnowledgeConstants.SORT_TYPE_DESC.equals(sortType)) {
            return KnowledgeConstants.SORT_TYPE_ASC;
        }
        else {
            return KnowledgeConstants.SORT_TYPE_DESC;
        }
    }


    /**
     * 结果检索刷新结果模块
     *
     * @param req
     * @param res
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "goResultSearch")
    public ModelAndView goResultSearch(HttpServletRequest req, HttpServletResponse res,
                                       String inputIndex) {
        String searchType = req.getParameter("searchType");
        String isEnableFlag = req.getParameter("isEnableFlag");
        String taskId = req.getParameter("taskId");
        ModelAndView mv = new ModelAndView("com/glaway/ids/pm/project/task/resultSearchForIDS");// 跳转的页面
        mv.addObject("updateTimeSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("authorNameSortType", KnowledgeConstants.SORT_TYPE_ASC);
        mv.addObject("sortElement", KnowledgeConstants.SEARCH_CONDITION_DEFAULT);
        mv.addObject("searchType", searchType);
        mv.addObject("isEnableFlag", isEnableFlag);
        mv.addObject("taskId", taskId);
        String referenceType = req.getParameter("referenceType");
        mv.addObject("referenceType", referenceType);
        String beforeInput = req.getParameter("beforeInput");
        String resultInput = req.getParameter("resultInput");
        req.getSession().setAttribute("currentInput", resultInput);
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(resultInput)) {
                resultInput = java.net.URLDecoder.decode(resultInput, "UTF-8");
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(beforeInput)) {
                beforeInput = java.net.URLDecoder.decode(beforeInput, "UTF-8");
            }

        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        // 判断输入值的来源
        if (org.apache.commons.lang3.StringUtils.isEmpty(resultInput)) {
            resultInput = (String)req.getSession().getAttribute("inputForSearch");
        }
        else if (!org.apache.commons.lang3.StringUtils.isEmpty(inputIndex)) {
            resultInput = inputIndex;
        }
        // 记录关键字
        try {
            SearchWordsVo vo = new SearchWordsVo();
            vo.setSearchWords(resultInput);
            vo.setType(searchType);
            attentionWordsRemoteService.saveAttentionWords(vo,UserUtil.getCurrentUser().getId());
        }
        catch (Exception e) {
            throw new GWException(I18nUtil.getValue("com.glaway.kes.knowledge.keywordException"));
        }
        // 调用检索方法，获取返回的List的属性值
        doResultSearch(req, res, mv);
        return mv;
    }

    /**
     * 结果检索业务处理
     *
     * @param
     * @return
     */
    @SuppressWarnings({"finally", "unchecked"})
    @RequestMapping(params = "doResultSearch")
    @ResponseBody
    public AjaxJson doResultSearch(HttpServletRequest req, HttpServletResponse res, ModelAndView mv) {
        AjaxJson j = new AjaxJson();
        String beforeInput = req.getParameter("beforeInput");
        String resultInput = req.getParameter("resultInput");
        String searchType = req.getParameter("searchType");
        mv.addObject("searchType", searchType);
        String pageNumber = req.getParameter("pageNumber");
        if (CommonUtil.isEmpty(pageNumber)){
            pageNumber = "1";
        }
        // 每页显示数量
        String pageSize = req.getParameter("pageSize");
        if (CommonUtil.isEmpty(pageSize)){
            pageSize = "10";
        }
        String solrQueryStr = null;
        String message = I18nUtil.getValue("com.glaway.kes.knowledge.searchSuccess");
        try {
            if (org.apache.commons.lang.StringUtils.isNotEmpty(resultInput)) {
                resultInput = java.net.URLDecoder.decode(resultInput, "UTF-8");
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(beforeInput)) {
                beforeInput = java.net.URLDecoder.decode(beforeInput, "UTF-8");
            }

            // 记录热词
            List<String> hlList = new ArrayList<String>();
            hlList.add("title");
            hlList.add("author");
            hlList.add("abstruct");
            SearchInput searchInput = new SearchInput();
            searchInput.setQ(resultInput);
            searchInput.setHl(hlList);
            searchInput.setRows(10);
            Integer start = 0;
            if (Integer.valueOf(pageNumber) > 1){
                start = Integer.valueOf(pageNumber) * 10;
            }
            searchInput.setStart(start);
            searchInput.setResultClassType(HitDocVo.class);
            if (org.apache.commons.lang.StringUtils.isNotEmpty(beforeInput)) {
                solrQueryStr = "(" + resultInput.trim() + ") AND " + beforeInput.trim();
            }
            else {
                solrQueryStr = resultInput.trim();
            }

            List<String> filterQueriesList = (List<String>)req.getSession().getAttribute(
                    ResourceUtil.getSessionId() + "_filterQueriesList");
            // SolrOutputDoc sdoc = solrSearchService.resultSearch(searchInput, solrQueryStr,
            // filterQueriesList);
            SearchOutput sout = searchEngineClient.resultSearch(searchInput, solrQueryStr, filterQueriesList,searchType);
            mv.addObject("input01", solrQueryStr);
            mv.addObject("solrQueryStr", solrQueryStr);
            List<HitDocVo> sdoc = new ArrayList<HitDocVo>();
            if(!CommonUtil.isEmpty(sout)){
                sdoc = (List<HitDocVo>)sout.getDocs();
            }
            // 拼接知识分类的分隔符“、”
            if (sdoc != null) {
                mv.addObject("sdoc", sout);
                sdoc = getFinalHitDocs(mv,sdoc);
                mv.addObject("docs", sdoc);
                // 获取热词
                HotWordsDto condition = new HotWordsDto();
                condition.setType(searchType);
                List<String> hotWordList = feignHotWordsRemoteService.queryTheMostHotWords(condition, 5);
                mv.addObject("hotWordList", hotWordList);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.knowledge.indexFailure");
            }
            mv.addObject("pageNumber", pageNumber);
            mv.addObject("total", sout.getHits());

        }
        catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            Object[] params = new Object[] {message, resultInput.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


}

