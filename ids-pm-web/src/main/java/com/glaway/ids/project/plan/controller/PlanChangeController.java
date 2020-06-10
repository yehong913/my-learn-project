package com.glaway.ids.project.plan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.activiti.core.WorkFlowCommonServiceI;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.common.UploadFile;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.service.FeignSelectUserService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.*;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.dto.TabCombinationTemplateDto;
import com.glaway.ids.planGeneral.tabCombinationTemplate.feign.TabCombinationTemplateFeignServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.vo.CombinationTemplateVo;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.plan.vo.PlanChangeRecordVo;
import com.glaway.ids.project.plan.vo.PlanChangeVo;
import com.glaway.ids.project.plan.vo.PlanDataVo;
import com.glaway.ids.project.plan.vo.PlanLoadUrlVo;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.util.CodeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ucar.httpservices.HTTPSession.log;

/**
 * Created by LHR on 2019/8/6.
 */
@Controller
@RequestMapping("/planChangeController")
public class PlanChangeController extends BaseController {


    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;

    @Autowired
    private PlanRemoteFeignServiceI planRemoteFeignServiceI;

    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleRemoteFeignServiceI;

    @Autowired
    private ProjectRemoteFeignServiceI projectRemoteFeignServiceI;

    @Autowired
    private FeignSelectUserService feignSelectUserService;

    @Autowired
    private FeignUserService feignUserService;

    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanRemoteFeignServiceI;

    @Autowired
    private FeignPlanChangeServiceI feignPlanChangeServiceI;

    @Autowired
    private InputsRemoteFeignServiceI inputsRemoteFeignServiceI;

    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;

    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoRemoteFeignServiceI;

    @Autowired
    private ResourceRemoteFeignServiceI resourceRemoteFeignServiceI;

    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardRemoteFeignServiceI;

    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkFeignServiceI;

    @Autowired
    private WorkFlowCommonServiceI workFlowCommonServiceI;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCombinationTemplateFeignService;

    //页签模版管理Service
    @Autowired
    private TabTemplateServiceI tabTemplateServiceImpl;

    @Autowired
    private ActivityTypeManageFeign activityTypeManageService;

    @Autowired
    private FeignDepartService departService;

    @Autowired
    private FeignUserService userService;

    @Autowired
    private TabCombinationTemplateFeignServiceI tabCbTemplateFeignService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    @Autowired
    private FeignRepService repService;

//    @Autowired
//    private RepFileTypeConfigServiceI repFileTypeConfigServiceI;

    /**
     * 项目计划单条变更页面跳转
     *
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangePlanOne")
    public ModelAndView goChangePlanOne(TemporaryPlanDto temporaryPlan, HttpServletRequest req) {
        // 计划变更类型
        BusinessConfig planChangeCategory = new BusinessConfig();
        planChangeCategory.setConfigType(ConfigTypeConstants.PLANCHANGECATEGORY);
        planChangeCategory.setStopFlag(ConfigStateConstants.START);
        planChangeCategory.setAvaliable("1");
        List<BusinessConfig> planChangeCategoryList = businessConfigService.searchBusinessConfigs(planChangeCategory);
        req.setAttribute("planChangeCategoryList", planChangeCategoryList);
        String jonStr = JsonUtil.getCodeTitleJson(planChangeCategoryList, "id", "name");
        req.setAttribute("planChangeCategoryListStr", jonStr);

        List<TSUserDto> userListTemp = userService.getAllUsers();

        if (temporaryPlan != null) {
            req.getSession().setAttribute("planchange-planId", temporaryPlan.getPlanId());
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanId())) {
                PlanDto plan = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
                if (plan != null) {
                    Date parentStartTime = new Date();
                    Date parentEndTime = new Date();
                    List<TSUserDto> userList = new ArrayList<>();
                    if (StringUtils.isNotEmpty(plan.getProjectId())) {
                        userList = projRoleRemoteFeignServiceI.getUserInProject(plan.getProjectId());
                    }
                    if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                        PlanDto parent = planRemoteFeignServiceI.getPlanEntity(plan.getParentPlanId());
                        parentStartTime = parent.getPlanStartTime();
                        parentEndTime = parent.getPlanEndTime();
                    }
                    else {
                        Project project = projectRemoteFeignServiceI.getProjectEntity(plan.getProjectId());
                        parentStartTime = project.getStartProjectTime();
                        parentEndTime = project.getEndProjectTime();
                    }
                    Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");

                    JSONArray deptList = new JSONArray();
                    JSONObject obj = null;
                    Map<String, TSUserDto> allUserMap = feignUserService.getCommonUserAll();
                    for (TSUserDto user : userList) {
                        TSUserDto commonUser = allUserMap.get(user.getUserName());
                        String departname = "";
                        List<TSDepartDto> departList = departMap.get(user.getId());

                        obj = new JSONObject();
                        obj.put("userId", user.getId());
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


                    BusinessConfig planLevel = new BusinessConfig();
                    planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                    planLevel.setStopFlag(ConfigStateConstants.START);
                    planLevel.setAvaliable("1");
                    List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
                    if (plan.getOwnerInfo() != null && plan.getOwnerInfo().getTSDepart() != null) {
                        plan.setOwnerDept(plan.getOwnerInfo().getTSDepart().getDepartname());
                    }
                    List<PreposePlanDto> preposePlanList = preposePlanRemoteFeignServiceI.getPreposePlansByPlanId(plan);
                    // 组装前置数据
                    StringBuffer preposeSb = new StringBuffer();
                    StringBuffer preposeIds = new StringBuffer();
                    String preposeEndTime = "";
                    String preposePlanName = "";
                    int k = 0;
                    for (PreposePlanDto preposePlan : preposePlanList) {
                        if (k > 0) {
                            preposeSb.append(",");
                            preposeIds.append(",");
                        }
                        if (preposePlan.getPreposePlanInfo() == null) {
                            continue;
                        }
                        String endTime = "";
                        if (preposePlan.getPreposePlanInfo().getPlanEndTime() != null) {
                            endTime = DateUtil.getStringFromDate(
                                    preposePlan.getPreposePlanInfo().getPlanEndTime(),
                                    DateUtil.YYYY_MM_DD);
                            if (StringUtils.isEmpty(preposeEndTime)) {
                                if (StringUtils.isNotEmpty(endTime)) {
                                    preposeEndTime = endTime;
                                    preposePlanName = preposePlan.getPreposePlanInfo().getPlanName();
                                }
                            }
                            else {
                                if (StringUtils.isNotEmpty(endTime)) {
                                    Date endTimeDate = DateUtil.stringtoDate(endTime,
                                            DateUtil.LONG_DATE_FORMAT);
                                    Date preposeEndTimeDate = DateUtil.stringtoDate(
                                            preposeEndTime, DateUtil.LONG_DATE_FORMAT);
                                    if (endTimeDate.getTime() > preposeEndTimeDate.getTime()) {
                                        preposeEndTime = endTime;
                                        preposePlanName = preposePlan.getPreposePlanInfo().getPlanName();
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

                   /* String init = feignPlanChangeServiceI.initTemporaryPlanDto(temporaryPlan);
                    temporaryPlan = JSON.parseObject(JsonFromatUtil.formatJsonToList(init),TemporaryPlanDto.class);*/

                    temporaryPlan.setPlanId(plan.getId());
                    temporaryPlan.setActualStartTime(plan.getActualStartTime());
                    temporaryPlan.setActualEndTime(plan.getActualEndTime());
                    temporaryPlan.setAssigner(plan.getAssigner());
                    temporaryPlan.setAssignerInfo(plan.getAssignerInfo());
                    temporaryPlan.setAssignTime(plan.getAssignTime());
                    temporaryPlan.setAvaliable(plan.getAvaliable());
                    temporaryPlan.setBeforePlanId(plan.getBeforePlanId());
                    temporaryPlan.setBizCurrent(plan.getBizCurrent());
                    temporaryPlan.setBizId(plan.getBizId());
                    temporaryPlan.setBizVersion(plan.getBizVersion());
                    temporaryPlan.setCreator(plan.getCreator());
                    temporaryPlan.setFlowStatus(plan.getFlowStatus());
                    temporaryPlan.setImplementation(plan.getImplementation());
                    temporaryPlan.setMilestone(plan.getMilestone());
                    temporaryPlan.setOwner(plan.getOwner());

                    if (temporaryPlan.getOwner() != null) {
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
                                temporaryPlan.setOwnerDept(depName);
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

                  /* com.alibaba.fastjson.JSONObject obj = null;
                    // 责任人对应的责任部门list取值

                    com.alibaba.fastjson.JSONArray deptList = new com.alibaba.fastjson.JSONArray();

                    if (!CommonUtil.isEmpty(userList)) {
                        for (TSUserDto user : userList) {
                            obj = new com.alibaba.fastjson.JSONObject();
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
                    }*/

           //         temporaryPlan.setOwnerDept(plan.getOwnerDept());
                    temporaryPlan.setOwnerInfo(plan.getOwnerInfo());
                    temporaryPlan.setParentPlanId(plan.getParentPlanId());
                    temporaryPlan.setPlanEndTime(plan.getPlanEndTime());
                    temporaryPlan.setPlanLevel("");
                    for (BusinessConfig b : planLevelList) {
                        if (b.getId().equals(plan.getPlanLevel())) {
                            temporaryPlan.setPlanLevel(plan.getPlanLevel());
                        }
                    }

                    List<BusinessConfig> resPlanLevelList = new ArrayList<BusinessConfig>();

                    // 计划等级重新排序
                    if (plan.getPlanLevel() != null && !"===".equals(plan.getPlanLevel()) ) {
                        BusinessConfig b = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                        if (b != null) {
                            resPlanLevelList.add(b);
                            for (int i = 0; i < planLevelList.size(); i++ ) {
                                if (!b.getId().equals(planLevelList.get(i).getId())) {
                                    resPlanLevelList.add(planLevelList.get(i));
                                }
                            }
                        }
                    }

                    temporaryPlan.setPlanLevelInfo(plan.getPlanLevelInfo());
                    temporaryPlan.setPlanName(plan.getPlanName());
                    temporaryPlan.setPlanNumber(plan.getPlanNumber());
                    temporaryPlan.setPlanOrder(plan.getPlanOrder());
                    temporaryPlan.setPlanStartTime(plan.getPlanStartTime());
//                    temporaryPlan.setPolicy(plan.getPolicy());
                    temporaryPlan.setPreposeIds(plan.getPreposeIds());
                    temporaryPlan.setPreposePlans(plan.getPreposePlans());
                    temporaryPlan.setProgressRate(plan.getProgressRate());
                    temporaryPlan.setProject(plan.getProject());
                    temporaryPlan.setProjectId(plan.getProjectId());
                    temporaryPlan.setProjectStatus(plan.getProjectStatus());
                    temporaryPlan.setRemark(plan.getRemark());
                    temporaryPlan.setSecurityLevel(plan.getSecurityLevel());
                    temporaryPlan.setStoreyNo(plan.getStoreyNo());
                    temporaryPlan.setWorkTime(plan.getWorkTime());
                    temporaryPlan.setTaskType(plan.getTaskType());
                    temporaryPlan.setTaskNameType(plan.getTaskNameType());
                    temporaryPlan.setCreateBy(plan.getCreateBy());
                    temporaryPlan.setCreateFullName(plan.getCreateFullName());
                    temporaryPlan.setCreateName(plan.getCreateName());
                    temporaryPlan.setCreateTime(plan.getCreateTime());


                    if (!PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
                            && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
                        if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())) {
                            temporaryPlan.setStatus(ProjectStatusConstants.PAUSE_CHI);
                        }
                        else if (ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())) {
                            temporaryPlan.setStatus(ProjectStatusConstants.CLOSE_CHI);
                        }
                        else {
                            // 获取计划的生命周期
                            FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                            String lifeCycStr = String.valueOf(fj.getObj());
                            List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);
                            for (LifeCycleStatus status : statusList) {
                                if (status.getName().equals(plan.getBizCurrent())) {
                                    temporaryPlan.setStatus(status.getTitle());
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        // 获取计划的生命周期
                        FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                        String lifeCycStr = String.valueOf(fj.getObj());
                        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);
                        for (LifeCycleStatus status : statusList) {
                            if (status.getName().equals(plan.getBizCurrent())) {
                                plan.setStatus(status.getTitle());
                                break;
                            }
                        }
                    }

                    List<TempPlanInputsDto> inputList = new ArrayList<>();
                    InputsDto inputs = new InputsDto();
                    inputs.setUseObjectId(plan.getId());
                    inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
//                    List<Inputs> inList = inputsService.queryInputsDetailList(inputs);
                    List<InputsDto> inList = inputsRemoteFeignServiceI.queryNewInputsList(inputs);

                    for (InputsDto i : inList) {
                        TempPlanInputsDto tempPlanInputs = new TempPlanInputsDto();
                        tempPlanInputs.setInputId(i.getId());
                        tempPlanInputs.setId(i.getId());
                        tempPlanInputs.setName(i.getName());
                        tempPlanInputs.setUseObjectId(i.getUseObjectId());
                        tempPlanInputs.setUseObjectType(i.getUseObjectType());
                        tempPlanInputs.setFileId(i.getFileId());
                        tempPlanInputs.setOrigin(i.getOrigin());
                        tempPlanInputs.setOriginType(i.getOriginType());
                        tempPlanInputs.setDocName(i.getDocName());
                        tempPlanInputs.setDocNameShow(i.getDocName());
                        tempPlanInputs.setDocId(i.getDocId());
                        tempPlanInputs.setRequired(i.getRequired());
                        tempPlanInputs.setDocId(i.getDocId());
                        tempPlanInputs.setDocName(i.getDocName());
                        tempPlanInputs.setOriginObjectId(i.getOriginObjectId());
                        tempPlanInputs.setOriginDeliverablesInfoId(i.getOriginDeliverablesInfoId());
                        tempPlanInputs.setChecked(i.getChecked());
                        tempPlanInputs.setDocument(i.getDocument());
                        inputList.add(tempPlanInputs);
                    }
                    req.getSession().setAttribute("planChange-inputList", inputList);
                    List<TempPlanDeliverablesInfoDto> deliverablesInfoList = new ArrayList<>();
                    DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                    deliverablesInfo.setUseObjectId(plan.getId());
                    deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);

                    FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
                    String switchStr = String.valueOf(feignJson.getObj());

                    List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                            deliverablesInfo, 1, 10, false);
                    List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
                    for (DeliverablesInfoDto deliverables : deliverablesList) {
                        TempPlanDeliverablesInfoDto tempDeliverables = new TempPlanDeliverablesInfoDto();
                        if (switchStr != null
                                && switchStr.equals(NameStandardSwitchConstants.DELIVERABLEUPATEABLE)) {
                            tempDeliverables.setOrigin(null);
                        }
                        else {
                            tempDeliverables.setOrigin(deliverables.getOrigin());
                        }
                        for (ProjDocVo p : projDocRelationList) {
                            if (p.getDeliverableName().equals(deliverables.getName())) {
                                tempDeliverables.setDocId(p.getDocId());
                                //接口获取RepFile塞入对象
                                if (StringUtils.isNotBlank(p.getDocId())) {
                                    RepFileDto repFileDto = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(),p.getDocId());
                                    tempDeliverables.setDocName(repFileDto.getFileName());
                                }

                            }
                        }
                        tempDeliverables.setId(UUID.randomUUID().toString());
                        tempDeliverables.setAvaliable(deliverables.getAvaliable());
                        tempDeliverables.setBizCurrent(deliverables.getBizCurrent());
                        tempDeliverables.setBizId(deliverables.getBizId());
                        tempDeliverables.setBizVersion(deliverables.getBizVersion());
                        tempDeliverables.setDeliverablesId(deliverables.getId());
                        tempDeliverables.setDocument(deliverables.getDocument());
                        tempDeliverables.setName(deliverables.getName());
                        tempDeliverables.setSecurityLevel(deliverables.getSecurityLevel());
                        tempDeliverables.setUseObjectId(deliverables.getUseObjectId());
                        tempDeliverables.setUseObjectType(deliverables.getUseObjectType());

                        deliverablesInfoList.add(tempDeliverables);
                    }
                    req.getSession().setAttribute("planChange-deliverablesInfoList",
                            deliverablesInfoList);

                    List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = new ArrayList<TempPlanResourceLinkInfoDto>();
                    ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
                    resourceLinkInfo.setUseObjectId(plan.getId());
                    resourceLinkInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);

                    List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                            resourceLinkInfo, 1, 10, false);
                    for (ResourceLinkInfoDto resourceLink : resourceLinkList) {
                        TempPlanResourceLinkInfoDto tempResourceLink = new TempPlanResourceLinkInfoDto();
                        tempResourceLink.setAvaliable(resourceLink.getAvaliable());
                        tempResourceLink.setBizCurrent(resourceLink.getBizCurrent());
                        tempResourceLink.setBizId(resourceLink.getBizId());
                        tempResourceLink.setBizVersion(resourceLink.getBizVersion());
                        tempResourceLink.setResourceId(resourceLink.getResourceId());
                        tempResourceLink.setResourceInfo(resourceLink.getResourceInfo());
                        tempResourceLink.setResourceLinkId(plan.getId());
                        tempResourceLink.setResourceName(resourceLink.getResourceName());
                        tempResourceLink.setResourceType(resourceLink.getResourceType());
                        tempResourceLink.setSecurityLevel(resourceLink.getSecurityLevel());
                        tempResourceLink.setUseObjectId(resourceLink.getUseObjectId());
                        tempResourceLink.setUseObjectType(resourceLink.getUseObjectType());
                        tempResourceLink.setUseRate(resourceLink.getUseRate());
                        tempResourceLink.setStartTime(resourceLink.getStartTime());
                        tempResourceLink.setEndTime(resourceLink.getEndTime());
                        tempResourceLink.setId(resourceLink.getId());

                        resourceLinkInfoList.add(tempResourceLink);
                    }
                    req.getSession().setAttribute("planChange-resourceLinkInfoList",
                            resourceLinkInfoList);

                    userList = feignUserService.getAllUsers();
                    String jonStr3 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
                    req.setAttribute("userList2", jonStr3);
                    req.setAttribute("userList", jonStr3);
                    String jonStr4 = JsonUtil.getCodeTitleJson(resPlanLevelList, "id", "name");
                    req.setAttribute("planLevelList_", jonStr4);

                    // req.setAttribute("userList", userList);

                    req.setAttribute("preposeplanName", preposePlanName);

                    req.setAttribute("parentPlanName", plan.getParentPlanName());

                    // req.setAttribute("ownerShow", plan.getOwnerInfo().getRealName());
                    TSUserDto userDto = feignUserService.getUserByUserId(plan.getOwner());
                    plan.setOwnerInfo(userDto);
                    if (plan.getOwnerInfo() != null) {
                        req.setAttribute("ownerShow", plan.getOwnerInfo().getRealName() + "-"
                                + plan.getOwnerInfo().getUserName());
                    }
                    else {
                        req.setAttribute("ownerShow", "");
                    }

                    String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
                    String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");

                    req.setAttribute("parentStartTime", parentStartTimeStr);
                    req.setAttribute("parentEndTime", parentEndTimeStr);
                    req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
                    req.setAttribute("planLevelList", planLevelList);
                    req.setAttribute("disabled", "true");
                    req.setAttribute("useObjectId", plan.getId());
                    req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
                    req.setAttribute("preposeEndTime", preposeEndTime);
                    req.setAttribute("projectId", plan.getProjectId());
                    if (StringUtils.isEmpty(temporaryPlan.getPreposeIds())) {
                        temporaryPlan.setPreposePlans("");
                    }
                    req.setAttribute("temporaryPlan_", temporaryPlan);
                    req.getSession().setAttribute("planchange-uploadSuccessPath", "");
                    req.getSession().setAttribute("planchange-uploadSuccessFileName", "");

                }

                Map<String,Map<String,String>> map = new HashMap<>();
                //List<TabCombinationTemplateDto> templateList = tabCombinationTemplateFeignService.findTabCbTempByActivityId(temporaryPlan.getTaskNameType());

                TabCombinationTemplateDto templateDto = tabCombinationTemplateFeignService.findTabCbTempByPlanId(temporaryPlan.getPlanId());

                List<String> objectPathList = new ArrayList<>();
                List<String> objectPathStrList = new ArrayList<>();

                String typeIds = req.getParameter("typeIds");
                req.setAttribute("typeIds",typeIds);

                if(!CommonUtil.isEmpty(templateDto)){
                    String displayAccess = "0";   //编制时显示的页签
                    List<List<List<List<ObjectPropertyInfoDto>>>> lists = tabTemplateServiceImpl.goTabsView(req,templateDto.getId(),displayAccess,"",typeIds);
                    List<TabTemplateDto> tabTemplateList = new ArrayList<>();
                    FeignJson combTemplateInfos = tabCombinationTemplateFeignService.getCombTemplateInfos(templateDto.getId());


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
                        planMap = objectToMap(temporaryPlan);
                    }
                    catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    List<PlanLoadUrlVo> planLoadUrlVoList = new ArrayList<>();
                    PlanLoadUrlVo loadUrlVo1 = new PlanLoadUrlVo();
                    loadUrlVo1.setId("1");
                    loadUrlVo1.setPropertyName("owner");
                    loadUrlVo1.setLoadUrl("planController.do?userList2&projectId="+temporaryPlan.getProjectId());
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
                    loadUrlVo3.setLoadUrl("planController.do?getTaskTypes&parentPlanId="+temporaryPlan.getParentPlanId()+"&projectId="+req.getParameter("projectId"));
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
                                    if(!objList.getControl().equals("8") &&!objList.getControl().equals("7") ){
                                        String objectPath = objList.getObjectPath();
                                        if(!CommonUtil.isEmpty(objectPath)){
                                            path = objectPath.substring(objectPath.lastIndexOf(".")+1);
                                            objList.setId(path+"-"+objList.getPropertyValue());
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
                                            objList.setDefaultValue(temporaryPlan.getPlanStartTime());
                                        }
                                        if("planEndTime".equals(objList.getPropertyValue())){
                                            objList.setDefaultValue(temporaryPlan.getPlanEndTime());
                                        }

                                        if("assignTime".equals(objList.getPropertyValue()) && !CommonUtil.isEmpty(temporaryPlan.getAssignTime())){
                                            objList.setDefaultValue(temporaryPlan.getAssignTime());
                                        }

                                        if("assigner".equals(objList.getPropertyValue()) && !CommonUtil.isEmpty(temporaryPlan.getAssigner())){
                                            for(TSUserDto userDto : userListTemp){
                                                if(temporaryPlan.getAssigner().equals(userDto.getId())){
                                                    objList.setValueInfo(userDto.getRealName()+"-"+userDto.getUserName());
                                                }
                                            }
                                        }

                                        if("preposeIds".equals(objList.getPropertyValue())){
                                            objList.setValueInfo(temporaryPlan.getPreposePlans());
                                        }

                                        if("planLevel".equals(objList.getPropertyValue())){
                                            objList.setValueInfo(temporaryPlan.getPlanLevel());
                                        }

                                        if("planName".equals(objList.getPropertyValue())){
                                            objList.setReadOnly(true);
                                        }
                                        if("taskType".equals(objList.getPropertyValue())){
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
                                        objList.setLoadUrl("resourceLinkInfoController.do?list");
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
                        planDataVo.setPlanName(temporaryPlan.getPlanName());
                    }
                    planDataVo.setOwner(temporaryPlan.getOwner());
                    planDataVo.setOwnerDept(temporaryPlan.getOwnerDept());
                    if(!CommonUtil.isEmpty(temporaryPlan.getPlanLevel())){
                        BusinessConfig businessConfig = businessConfigService.getBusinessConfig(temporaryPlan.getPlanLevel());
                        planDataVo.setPlanLevel(businessConfig.getName());
                    }

                    planDataVo.setPlanEndTime(DateUtil.getStringFromDate(temporaryPlan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
                    planDataVo.setPlanStartTime(DateUtil.getStringFromDate(temporaryPlan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                    planDataVo.setCreateTime(DateUtil.getStringFromDate(temporaryPlan.getCreateTime(), DateUtil.YYYY_MM_DD));
                    planDataVo.setCreateFullName(temporaryPlan.getCreateFullName()+"-"+temporaryPlan.getCreateName());
                    planDataVo.setMilestone(temporaryPlan.getMilestone());
                    planDataVo.setBizCurrent(temporaryPlan.getStatus());
                    planDataVo.setWorkTime(temporaryPlan.getWorkTime());
                    planDataVo.setTaskType(temporaryPlan.getTaskType());
                    planDataVo.setRemark(temporaryPlan.getRemark());
                    if(!CommonUtil.isEmpty(req.getParameter("taskNameType"))){
                        planDataVo.setTaskNameType(req.getParameter("taskNameType"));
                    }else{
                        ActivityTypeManageDto activityTypeManageDto = activityTypeManageService.queryActivityTypeManageById(temporaryPlan.getTaskNameType());
                        planDataVo.setTaskNameType(activityTypeManageDto.getName());
                    }

                    req.setAttribute("operation",resStr);
                    String planStr = JsonUtil.map2json(planDataVo);
                    String resPlanStr = stringToJson(planStr,true);
                    req.setAttribute("planDefault",resPlanStr);
                    req.setAttribute("enterType","0");
                    req.setAttribute("isPlanChange","planChange");
                }
            }
        }

        /*return new ModelAndView("com/glaway/ids/pm/project/plan/plan-changeOne");*/
        return new ModelAndView("com/glaway/ids/pm/project/plan/plan-changeOneForGeneral");   //计划信息页签改为页签组合模板形式
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
            return null;
        }

        // 获取其子计划的所有输出、并将其存入deliverablesMap
        Map<String, Object> deliverablesMap = new HashMap<String, Object>();
        PlanDto childPlan = new PlanDto();
        childPlan.setParentPlanId(plan.getId());
        List<PlanDto> childList = planRemoteFeignServiceI.queryPlanList(childPlan, 1, 10, false);

        for (PlanDto child : childList) {
            deliverablesInfo = new DeliverablesInfoDto();
            deliverablesInfo.setUseObjectId(child.getId());
            deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            List<DeliverablesInfoDto> childDeliverables = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);
            for (DeliverablesInfoDto childDeli : childDeliverables) {
                List<ProjDocRelationDto> projDocRelationList = projLibService.getDocRelation(childDeli.getId());
                if (CommonUtil.isEmpty(projDocRelationList)) {
                    continue;
                }
                deliverablesMap.put(childDeli.getName(), projDocRelationList.get(0));
            }
        }

        List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();

        for (DeliverablesInfoDto parentDeli : deliverablesList) {
            ProjDocVo projDocVo = new ProjDocVo();
            projDocVo.setDeliverableId(parentDeli.getId());
            projDocVo.setDeliverableName(parentDeli.getName());
            if (deliverablesMap.get(parentDeli.getName()) != null) { // 如果在子计划中包含交付项，需要使用子计划的交付项
                ProjDocRelationDto projDocRelation = (ProjDocRelationDto)deliverablesMap.get(parentDeli.getName());
                convertToVo(projDocVo, projDocRelation, plan.getId());
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                projDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
                List<ProjDocRelationDto> projDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());

                if (!CommonUtil.isEmpty(projDocRelationDbList)) {
                    convertToVo(projDocVo, projDocRelationDbList.get(0), plan.getId());
                }
                projDocVo.setOpFlag(true);
                projDocRelationList.add(projDocVo);
            }
        }

        return projDocRelationList;
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
        String havePower = planRemoteFeignServiceI.getOutPowerForPlanList(projDocRelation.getDocId(), planId, UserUtil.getInstance().getUser().getId());
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
        if (projDocRelation.getRepFile() == null) {
            return projDocVo;
        }
        projDocVo.setDocName(projDocRelation.getRepFile().getFileName());
        projDocVo.setVersion(projDocRelation.getRepFile().getBizVersion());
        String approveStatus = lifeCycleStatusService.getTitleByPolicyIdAndName(
                projDocRelation.getRepFile().getPolicy().getId(),
                projDocRelation.getRepFile().getBizCurrent());
        projDocVo.setStatus(approveStatus);
        projDocVo.setSecurityLevel(projDocRelation.getRepFile().getSecurityLevel());
        return projDocVo;
    }

    /**
     * 输入页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "inputList")
    public void inputList(TempPlanInputsDto tempPlanInputs, HttpServletRequest request,
                          HttpServletResponse response) {
        List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                "planChange-inputList");
        long count = inputList.size();

        PlanDto p = planRemoteFeignServiceI.getPlanEntity(request.getParameter("useObjectId"));

        String libId = "";
        if(!CommonUtil.isEmpty(p)){
            String projectId = p.getProjectId();
            FeignJson fj = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        }else{
            String projectId = request.getParameter("projectId");
            FeignJson fj  = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        }


        Map<String, String> fileNameMap = new HashMap<String, String>();

        Map<String, String> filePathMap = new HashMap<String, String>();

        Map<String, String> fileIdMap = new HashMap<String, String>();

        if(!CommonUtil.isEmpty(libId)){
            fileNameMap = inputsRemoteFeignServiceI.getRepFileNameAndBizIdMap(libId);

            filePathMap = inputsRemoteFeignServiceI.getRepFilePathAndBizIdMap(libId);

            fileIdMap = inputsRemoteFeignServiceI.getRepFileIdAndBizIdMap(libId);
        }

        if(!CommonUtil.isEmpty(inputList)){
            for(TempPlanInputsDto input : inputList){
                if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("LOCAL")){
                    input.setDocNameShow(input.getDocName());
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PROJECTLIBDOC")){
//                    RepFile rep = inputsService.getEntity(RepFile.class, input.getDocId());
                    /*List<RepFile> repList = inputsService.getRepFileByBizId(input.getDocId());
                    RepFile rep = new RepFile();
                    if(!CommonUtil.isEmpty(repList)){
                        rep = repList.get(0);

                    }
                    String realPath = "";
                    if(!CommonUtil.isEmpty(rep)){
                        input.setDocNameShow(rep.getFileName());
                        String path = projLibService.getDocNamePath(rep.getId());
                        String[] temp = path.split("/");
                        for (int i = 1; i < temp.length - 1; i++ ) {
                            realPath += "/" + temp[i];
                        }
                    }else{
                        input.setDocNameShow("");
                    }
                    input.setOriginPath(realPath);*/
                    if(!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))){
                        input.setDocNameShow(fileNameMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))){
                        input.setOriginPath(filePathMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))){
                        input.setDocIdShow(fileIdMap.get(input.getDocId()));
                    }
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PLAN")){
                    PlanDto plan = planRemoteFeignServiceI.getPlanEntity(input.getOriginObjectId());
                    List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                    if(!CommonUtil.isEmpty(plan)){
                        projDocRelationList = inputsRemoteFeignServiceI.getDocRelationList(plan, UserUtil.getInstance().getUser().getId());
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
                    input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                    input.setDocId(projDoc.getDocId());
                    input.setDocNameShow(projDoc.getDocName());
                    input.setExt1(String.valueOf(projDoc.isDownload()));
                    input.setExt2(String.valueOf(projDoc.isHavePower()));
                    input.setExt3(String.valueOf(projDoc.isDetail()));
                }
            }
        }
       /*
        for (TempPlanInputs t : inputList) {
            if (StringUtils.isNotEmpty(t.getOriginObjectId())) {
                Plan p = planService.getEntity(Plan.class, t.getOriginObjectId());
                if (p != null) {
                    t.setOrigin(p.getPlanName());
                }
            }
        }*/
        request.getSession().setAttribute("planChange-inputList", inputList);
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    /**
     * 交付物页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "documentList")
    public void documentList(TempPlanDeliverablesInfoDto deliverablesInfo, String planName,
                             HttpServletRequest request, HttpServletResponse response) {
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");

       /* if (!CommonUtil.isEmpty(deliverablesInfoList)) {
            // 获取其子计划的所有输出、并将其存入deliverablesMap
            Map<String, Object> deliverablesMap = new HashMap<String, Object>();
            deliverablesMap = deliverablesInfoService.queryFinishDeliverable(plan.getId(),
                    deliverablesMap);

            for (TempPlanDeliverablesInfoDto parentDeli : deliverablesInfoList) {
                ProjDocVo projDocVo = new ProjDocVo();
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
        }*/

        long count = deliverablesInfoList.size();
        request.getSession().setAttribute("planChange-deliverablesInfoList", deliverablesInfoList);
        String json = com.alibaba.fastjson.JSONArray.toJSONString(deliverablesInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceList")
    public void resourceList(TempPlanResourceLinkInfoDto resourceLinkInfo,
                             HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");
        Map<String, String> resourcePathMap = new HashMap<String, String>();
        Map<String, String> resourceNameMap = new HashMap<String, String>();
        for (ResourceDto resource : resourceListTemp) {
            resourcePathMap.put(resource.getId(), resource.getPath());
            resourceNameMap.put(resource.getId(), resource.getName());
        }
        for (TempPlanResourceLinkInfoDto info : resourceLinkInfoList) {
            if (info.getResourceInfo() != null) {
                info.setResourceName(resourceNameMap.get(info.getResourceId()));
                info.setResourceType(resourcePathMap.get(info.getResourceId()));
            }
        }
        request.getSession().setAttribute("planChange-resourceLinkInfoList", resourceLinkInfoList);
        DataGridReturn data = new DataGridReturn(resourceLinkInfoList.size(), resourceLinkInfoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 判断能否有导入覆盖
     *
     * @param
     * @param
     * @return
     * @see
     */
    @RequestMapping(params = "checkChildPostposeInfluenced")
    @ResponseBody
    public AjaxJson checkChildPostposeInfluenced(TemporaryPlanDto temporaryPlan,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String msg = "";
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");
        if (resourceLinkInfoList != null && resourceLinkInfoList.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            for (int i = 0; i < resourceLinkInfoList.size(); i++ ) {
                if (resourceLinkInfoList.get(i).getResourceInfo() == null) {
                    ResourceDto r = resourceRemoteFeignServiceI.getEntity(resourceLinkInfoList.get(i).getResourceId());
                    resourceLinkInfoList.get(i).setResourceInfo(r);
                }

                if (StringUtils.isEmpty(resourceLinkInfoList.get(i).getUseRate())) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        msg = I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedOne",
                                arguments);
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).getStartTime() == null) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        msg = I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedTwo",
                                arguments);
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).getEndTime() == null) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        msg = I18nUtil.getValue(
                                "com.glaway.ids.project.plan.checkChildPostposeInfluencedThree",
                                arguments);
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).getStartTime() != null
                        && resourceLinkInfoList.get(i).getEndTime() != null) {
                    String time1 = sdf.format(resourceLinkInfoList.get(i).getStartTime());
                    String time2 = sdf.format(resourceLinkInfoList.get(i).getEndTime());
                    String time3 = sdf.format(temporaryPlan.getPlanStartTime());
                    String time4 = sdf.format(temporaryPlan.getPlanEndTime());
                    if ((Integer.parseInt(time3) > Integer.parseInt(time1))
                            || (Integer.parseInt(time2) > Integer.parseInt(time4))) {
                        if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                            Object[] arguments = new String[] {
                                    resourceLinkInfoList.get(i).getResourceInfo().getName(), time3,
                                    time4};
                            msg = I18nUtil.getValue(
                                    "com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFour",
                                    arguments);
                        }
                        break;
                    }
                }

            }
        }
        if (StringUtils.isEmpty(msg)) {
            String parentPlanId = (String)request.getSession().getAttribute("planchange-planId");
            List<TemporaryPlanChild> childList = getChildList(parentPlanId, temporaryPlan);
            for (TemporaryPlanChild child : childList) {
                if (child.isStartTimeOverflow()) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFive");
                    break;
                }
                if (child.isEndTimeOverflow()) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFive");
                    break;
                }

            }
        }
        if (StringUtils.isEmpty(msg)) {
            String preposeIds = (String)request.getSession().getAttribute("planchange-planId");
            List<TemporaryPlanPostpose> postposeList = getTemporaryPlanPostposeList(preposeIds,
                    temporaryPlan);
            for (TemporaryPlanPostpose postpose : postposeList) {
                if (postpose.isStartTimeOverflow()) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFive");
                    break;
                }
                if (postpose.isEndTimeOverflow()) {
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFive");
                    break;
                }
            }
        }
        request.getSession().setAttribute("planChange-resourceLinkInfoList", resourceLinkInfoList);
        j.setMsg(msg);
        Date preposeEndTimeNext = TimeUtil.getExtraDate(temporaryPlan.getPlanEndTime(), 1);
        j.setObj(DateUtil.getStringFromDate(preposeEndTimeNext, DateUtil.YYYY_MM_DD));
        return j;
    }

    /**
     * 获取子计划
     *
     * @param
     * @param
     * @param
     * @see
     */
    private List<TemporaryPlanChild> getChildList(String parentPlanId, TemporaryPlanDto temporaryPlan) {
        List<TemporaryPlanChild> childList = new ArrayList<TemporaryPlanChild>();
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> list = planRemoteFeignServiceI.queryPlanList(plan, 1, 10, false);
        for (PlanDto p : list) {
            if (!PlanConstants.PLAN_INVALID.equals(p.getBizCurrent())) {
                TemporaryPlanChild planChild = new TemporaryPlanChild();
                planChild.setPlanId(p.getId());
                planChild.setPlanName(p.getPlanName());
                planChild.setParentPlanId(p.getParentPlanId());
                if (!CommonUtil.isEmpty(p.getParentPlan())){
                    planChild.setParentPlanName(p.getParentPlan().getPlanName());
                }

                if (StringUtils.isNotEmpty(p.getOwner())) {
                    planChild.setOwner(p.getOwner());
                    if (p.getOwnerInfo() != null) {
                        planChild.setOwnerName(p.getOwnerInfo().getRealName() + "-"
                                + p.getOwnerInfo().getUserName());
                        planChild.setOwnerInfo(p.getOwnerInfo());
                    }
                }

                if (StringUtils.isNotEmpty(p.getAssigner())) {
                    planChild.setAssigner(p.getAssigner());
                    if (p.getAssignerInfo() != null) {
                        planChild.setAssignerName(p.getAssignerInfo().getRealName() + "-"
                                + p.getAssignerInfo().getUserName());
                        planChild.setAssignerInfo(p.getAssignerInfo());
                    }
                }

                if (StringUtils.isNotEmpty(p.getCreateBy())) {
                    planChild.setCreateBy(p.getCreateBy());
                    if (p.getCreator() != null) {
                        planChild.setCreateByName(p.getCreator().getRealName() + "-"
                                + p.getCreator().getUserName());
                        planChild.setCreator(p.getCreator());
                    }
                }

                planChild.setStartTimeOverflow(false);
                if ((p.getPlanStartTime().getTime() < temporaryPlan.getPlanStartTime().getTime())
                        || (p.getPlanStartTime().getTime() > temporaryPlan.getPlanEndTime().getTime())) {
                    planChild.setStartTimeOverflow(true);
                }
                planChild.setPlanStartTime(DateUtil.dateToString(p.getPlanStartTime(),
                        DateUtil.LONG_DATE_FORMAT));

                planChild.setEndTimeOverflow(false);
                if ((p.getPlanEndTime().getTime() > temporaryPlan.getPlanEndTime().getTime())
                        || (p.getPlanEndTime().getTime() < temporaryPlan.getPlanStartTime().getTime())) {
                    planChild.setEndTimeOverflow(true);
                }
                planChild.setPlanEndTime(DateUtil.dateToString(p.getPlanEndTime(),
                        DateUtil.LONG_DATE_FORMAT));
                childList.add(planChild);
            }
        }
        return childList;
    }

    /**
     * 获取后置计划
     *
     * @param preposeIds
     * @param temporaryPlan
     * @return
     * @see
     */
    private List<TemporaryPlanPostpose> getTemporaryPlanPostposeList(String preposeIds,
                                                                     TemporaryPlanDto temporaryPlan) {
        List<TemporaryPlanPostpose> postposeList = new ArrayList<TemporaryPlanPostpose>();
        PlanDto plan = new PlanDto();
        plan.setPreposeIds(preposeIds);
        List<PreposePlanDto> list = preposePlanRemoteFeignServiceI.getPostposesByPreposeId(plan);
        for (PreposePlanDto prepose : list) {
            TemporaryPlanPostpose planPostpose = new TemporaryPlanPostpose();
            PlanDto p = planRemoteFeignServiceI.getPlanEntity(prepose.getPlanId());
            if (p != null) {
                if (!PlanConstants.PLAN_INVALID.equals(p.getBizCurrent())) {
                    planPostpose.setPlanId(p.getId());
                    planPostpose.setPlanName(p.getPlanName());
                    planPostpose.setPreposePlanId(plan.getPreposeIds());
                    PlanDto preposePlan = planRemoteFeignServiceI.getPlanEntity(prepose.getPreposePlanId());
                    if (preposePlan != null) {
                        planPostpose.setPreposePlanName(preposePlan.getPlanName());
                    }
                    if (StringUtils.isNotEmpty(p.getOwner())) {
                        planPostpose.setOwner(p.getOwner());
                        if (p.getOwnerInfo() != null) {
                            planPostpose.setOwnerName(p.getOwnerInfo().getRealName() + "-"
                                    + p.getOwnerInfo().getUserName());
                            planPostpose.setOwnerInfo(p.getOwnerInfo());
                        }
                    }

                    if (StringUtils.isNotEmpty(p.getAssigner())) {
                        planPostpose.setAssigner(p.getAssigner());
                        if (p.getAssignerInfo() != null) {
                            planPostpose.setAssignerName(p.getAssignerInfo().getRealName() + "-"
                                    + p.getAssignerInfo().getUserName());
                            planPostpose.setAssignerInfo(p.getAssignerInfo());
                        }
                    }

                    if (StringUtils.isNotEmpty(p.getCreateBy())) {
                        planPostpose.setCreateBy(p.getCreateBy());
                        if (p.getCreator() != null) {
                            planPostpose.setCreateByName(p.getCreator().getRealName() + "-"
                                    + p.getCreator().getUserName());
                            planPostpose.setCreator(p.getCreator());
                        }
                    }

                    planPostpose.setStartTimeOverflow(false);
                    Date preposeEndTimeNext = TimeUtil.getExtraDate(
                            temporaryPlan.getPlanEndTime(), 1);
                    if (p.getPlanStartTime().getTime() < preposeEndTimeNext.getTime()) {
                        planPostpose.setStartTimeOverflow(true);
                    }
                    planPostpose.setPlanStartTime(DateUtil.dateToString(p.getPlanStartTime(),
                            DateUtil.LONG_DATE_FORMAT));
                    planPostpose.setEndTimeOverflow(false);
                    if (p.getPlanEndTime().getTime() < preposeEndTimeNext.getTime()) {
                        planPostpose.setEndTimeOverflow(true);
                    }
                    planPostpose.setPlanEndTime(DateUtil.dateToString(p.getPlanEndTime(),
                            DateUtil.LONG_DATE_FORMAT));
                    postposeList.add(planPostpose);
                }
            }
        }
        return postposeList;
    }

    /**
     * 变更对比
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planChangeVo")
    public void planChangeVo(TemporaryPlanDto temporaryPlan, HttpServletRequest request,
                             HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");
        PlanDto plan = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");
        ResourceLinkInfoDto resourceLink = new ResourceLinkInfoDto();
        resourceLink.setUseObjectId(temporaryPlan.getPlanId());
        List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                resourceLink, 1, 10, false);

        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(temporaryPlan.getPlanId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                "planChange-inputList");
        InputsDto inputs = new InputsDto();
        inputs.setUseObjectId(temporaryPlan.getPlanId());

        List<InputsDto> inList = inputsRemoteFeignServiceI.queryInputList(inputs, 1, 10, false);

        Map<String,TSUserDto> userDtoMap = userService.getAllUserIdsMap();

        if (StringUtils.isNotEmpty(plan.getOwner())
                && StringUtils.isNotEmpty(temporaryPlan.getOwner())) {
            if (!plan.getOwner().equals(temporaryPlan.getOwner())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("负责人");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(userDtoMap.get(plan.getOwner()).getRealName() + "-"
                        + userDtoMap.get(plan.getOwner()).getUserName());
                planChangeVo.setChangeAfter(temporaryPlan.getOwnerRealName());
                planChangeVoList.add(planChangeVo);
            }
        }

        if("===".equals(plan.getPlanLevel())){
            plan.setPlanLevel("");
        }

        if (StringUtils.isNotEmpty(plan.getPlanLevel())) {
            if (StringUtils.isEmpty(temporaryPlan.getPlanLevel())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("计划等级");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getPlanLevelInfo().getName());
                planChangeVo.setChangeAfter("");
                planChangeVoList.add(planChangeVo);
            }
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                if (!plan.getPlanLevel().equals(temporaryPlan.getPlanLevel())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("计划等级");
                    planChangeVo.setType("修改");
                    BusinessConfig b = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                    planChangeVo.setChangeBefore(b.getName());
                    planChangeVo.setChangeAfter(temporaryPlan.getPlanLevelName());
                    planChangeVoList.add(planChangeVo);
                }
            }
        }

        if (StringUtils.isEmpty(plan.getPlanLevel())) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("计划等级");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(temporaryPlan.getPlanLevelName());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (!CommonUtil.isEmpty(plan.getOwnerInfo()) && StringUtils.isNotEmpty(plan.getOwnerInfo().getTSDepart().getDepartname())
                && StringUtils.isNotEmpty(temporaryPlan.getOwnerDept())) {
            if (!plan.getOwnerInfo().getTSDepart().getDepartname().equals(
                    temporaryPlan.getOwnerDept())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("责任部门");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getOwnerInfo().getTSDepart().getDepartname());
                planChangeVo.setChangeAfter(temporaryPlan.getOwnerDept());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getPlanStartTime() != null && temporaryPlan.getPlanStartTime() != null) {
            if (!plan.getPlanStartTime().equals(temporaryPlan.getPlanStartTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("开始时间");
                planChangeVo.setType("修改");
                String planStartTimeBefore = DateUtil.dateToString(plan.getPlanStartTime(),"yyyy-MM-dd");
                planChangeVo.setChangeBefore(planStartTimeBefore);
                planChangeVo.setChangeAfter(temporaryPlan.getPlanStartTimeView());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getPlanEndTime() != null && temporaryPlan.getPlanEndTime() != null) {
            if (!plan.getPlanEndTime().equals(temporaryPlan.getPlanEndTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("结束时间");
                planChangeVo.setType("修改");
                String planEndTimeBefore = DateUtil.dateToString(plan.getPlanEndTime(),"yyyy-MM-dd");
                planChangeVo.setChangeBefore(planEndTimeBefore);
                planChangeVo.setChangeAfter(temporaryPlan.getPlanEndTimeView());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getWorkTime() != null && temporaryPlan.getWorkTime() != null) {
            if (!plan.getWorkTime().equals(temporaryPlan.getWorkTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("工期");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getWorkTime());
                planChangeVo.setChangeAfter(temporaryPlan.getWorkTime());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (StringUtils.isNotEmpty(plan.getMilestone())
                && StringUtils.isNotEmpty(temporaryPlan.getMilestone())) {
            if("否".equals(plan.getMilestone()) || "false".equals(plan.getMilestone())){
                plan.setMilestone("false");
            }else{
                plan.setMilestone("true");
            }
            if("否".equals(temporaryPlan.getMilestone()) || "false".equals(temporaryPlan.getMilestone())){
                temporaryPlan.setMilestone("false");
            }else{
                temporaryPlan.setMilestone("true");
            }
            if (!plan.getMilestone().equals(temporaryPlan.getMilestone())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("里程碑");
                planChangeVo.setType("修改");
                if ("true".equals(plan.getMilestone())) {
                    planChangeVo.setChangeBefore("是");
                }
                else {
                    planChangeVo.setChangeBefore("否");
                }
                if ("true".equals(temporaryPlan.getMilestone())) {
                    planChangeVo.setChangeAfter("是");
                }
                else {
                    planChangeVo.setChangeAfter("否");
                }
                planChangeVoList.add(planChangeVo);
            }
        }


        List<PreposePlanDto> preposePlanList = preposePlanRemoteFeignServiceI.getPreposePlansByPlanId(plan);
        if (preposePlanList != null && preposePlanList.size() > 0) {
            Boolean a = false;
            for (PreposePlanDto preposePlan : preposePlanList) {
                if (StringUtils.isNotEmpty(temporaryPlan.getPreposePlans())) {
                    if (temporaryPlan.getPreposePlans().contains(
                            preposePlan.getPreposePlanInfo().getPlanName())) {

                    }
                    else {
                        a = true;
                    }
                }
            }
            if (a) {
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
                if (StringUtils.isEmpty(temporaryPlan.getPreposeIds())
                        && StringUtils.isEmpty(temporaryPlan.getPreposePlans())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("前置计划");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(preposeSb.toString());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
                else {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("前置计划");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(preposeSb.toString());
                    planChangeVo.setChangeAfter(temporaryPlan.getPreposePlans());
                    planChangeVoList.add(planChangeVo);
                }
            }

        }

        if (preposePlanList == null || preposePlanList.size() == 0) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPreposeIds())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("前置计划");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(temporaryPlan.getPreposePlans());
                planChangeVoList.add(planChangeVo);
            }
        }

        // 输入对比
        for (InputsDto d : inList) {
            Boolean a = false;
            for (TempPlanInputsDto dev : inputList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输入)" + d.getName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }

        if (!CommonUtil.isEmpty(inputList)) {
            for (TempPlanInputsDto dev : inputList) {
                Boolean a = false;
                for (InputsDto d : inList) {
                    if (d.getName().equals(dev.getName())) {
                        a = true;
                        break;
                    }
                }
                if (!a) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("(输入)" + dev.getName());
                    planChangeVo.setType("新增");
                    planChangeVo.setChangeBefore("-");
                    planChangeVo.setChangeAfter(dev.getName());
                    planChangeVoList.add(planChangeVo);
                }
            }
        }

        // 输出对比
        for (DeliverablesInfoDto d : deliverablesList) {
            Boolean a = false;
            for (TempPlanDeliverablesInfoDto dev : deliverablesInfoList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输出)" + d.getName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }

        for (TempPlanDeliverablesInfoDto dev : deliverablesInfoList) {
            Boolean a = false;
            for (DeliverablesInfoDto d : deliverablesList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输出)" + dev.getName());
                planChangeVo.setType("新增");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(dev.getName());
                planChangeVoList.add(planChangeVo);
            }
        }

        // 资源对比
        for (ResourceLinkInfoDto d : resourceLinkList) {
            Boolean a = false;
            for (TempPlanResourceLinkInfoDto dev : resourceLinkInfoList) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                if (dev.getResourceInfo() != null) {
                    dev.setResourceName(dev.getResourceInfo().getName());
                }
                if (d.getResourceName().equals(dev.getResourceName())) {
                    a = true;
                    if (!d.getUseRate().equals(dev.getUseRate())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(使用百分比)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getUseRate());
                        planChangeVo.setChangeAfter(dev.getUseRate());
                        planChangeVoList.add(planChangeVo);
                    }
                    if (!d.getStartTime().equals(dev.getStartTime())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(开始时间)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getStartTime().toString());
                        String time = DateUtil.getStringFromDate(dev.getStartTime(),
                                DateUtil.YYYY_MM_DD);
                        planChangeVo.setChangeAfter(time);
                        planChangeVoList.add(planChangeVo);
                    }
                    if (!d.getEndTime().equals(dev.getEndTime())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(结束时间)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getEndTime().toString());
                        String time = DateUtil.getStringFromDate(dev.getEndTime(),
                                DateUtil.YYYY_MM_DD);
                        planChangeVo.setChangeAfter(time);
                        planChangeVoList.add(planChangeVo);
                    }
                    break;
                }
            }
            if (!a) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(资源)" + d.getResourceName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getResourceName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }

        for (TempPlanResourceLinkInfoDto dev : resourceLinkInfoList) {
            Boolean a = false;
            for (ResourceLinkInfoDto d : resourceLinkList) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                if (dev.getResourceInfo() != null) {
                    dev.setResourceName(dev.getResourceInfo().getName());
                }
                if (d.getResourceName().equals(dev.getResourceName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(资源)" + dev.getResourceName());
                planChangeVo.setType("新增");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(dev.getResourceName());
                planChangeVoList.add(planChangeVo);
            }
        }
        DataGridReturn data = new DataGridReturn(planChangeVoList.size(), planChangeVoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 项目计划临时表新增或更新后保存
     *
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSave")
    @ResponseBody
    public AjaxJson doSave(TemporaryPlanDto temporaryPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanFail");
        String failMessageCode = "";
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanSuccess");
        try {



            if (StringUtils.isNotEmpty(temporaryPlan.getId())) {
                String id = feignPlanChangeServiceI.saveOrUpdateTemporaryPlan(temporaryPlan,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
                request.getSession().setAttribute("planchange-temporaryId", CodeUtils.replaceQuotes(id));
                request.getSession().setAttribute("planchange-useObjectType", "PLAN");
                request.getSession().setAttribute("planchange-useObjectId", id);
                log.info(message, id, id);
            }
            else {
                String uploadSuccessPath = (String)request.getSession().getAttribute(
                        "planchange-uploadSuccessPath");
                String uploadSuccessFileName = (String)request.getSession().getAttribute(
                        "planchange-uploadSuccessFileName");

                String userId = UserUtil.getCurrentUser().getId();
                String temporaryPlanId = feignPlanChangeServiceI.getTemporaryPlanId(temporaryPlan, uploadSuccessPath, uploadSuccessFileName,userId,ResourceUtil.getCurrentUserOrg().getId());
                temporaryPlanId = CodeUtils.replaceQuotes(temporaryPlanId);
//                String temporaryPlanId = "4028f00c6c6a0075016c6a02cea40001";
                request.getSession().setAttribute("planchange-temporaryId", temporaryPlanId);
                request.getSession().setAttribute("planchange-useObjectType", "PLAN");
                request.getSession().setAttribute("planchange-useObjectId", temporaryPlanId);
            }
            j.setSuccess(true);
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanFail");
            log.error(failMessage, e, "", temporaryPlan.getId().toString());
            Object[] params = new Object[] {failMessage, temporaryPlan.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 启动并提交工作流
     *
     * @param ids
     * @param
     * @param
     * @param
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startPlanChange")
    @ResponseBody
    public AjaxJson startPlanChange(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.startPlanChangeSuccess");
        try {
            TSUserDto actor = new TSUserDto();
            actor.setId(UserUtil.getInstance().getUser().getId());
            actor.setUserName(UserUtil.getInstance().getUser().getUserName());
            actor.setRealName(UserUtil.getInstance().getUser().getRealName());

            String leader = request.getParameter("leader");
            String deptLeader = request.getParameter("deptLeader");

            leader = URLDecoder.decode(leader, "UTF-8");
            deptLeader = URLDecoder.decode(deptLeader, "UTF-8");

            String changeType = request.getParameter("changeType");
            String temporaryId = (String)request.getSession().getAttribute(
                    "planchange-temporaryId");
            List<PlanDto> planList = new ArrayList<>();
            List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");
            FeignJson json = new FeignJson();
            Map<String, Object> map = new HashMap<>();
            map.put("planList",planList);
            map.put("resourceLinkInfoList",resourceLinkInfoList);
            map.put("inputList",inputList);
            map.put("deliverablesInfoList",deliverablesInfoList);
            map.put("actor",actor);
            map.put("leader",leader);
            map.put("deptLeader",deptLeader);
            map.put("changeType",changeType);
            map.put("temporaryId",temporaryId);
            map.put("userId",UserUtil.getCurrentUser().getId());
            map.put("typeIds",request.getParameter("typeIds"));
            json.setAttributes(map);
            feignPlanChangeServiceI.startPlanChangeForWork(json);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.startPlanChangeFail");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 计划变更-子计划
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "temporaryPlanChildList")
    public void temporaryPlanChildList(TemporaryPlanDto temporaryPlan, HttpServletRequest request,
                                       HttpServletResponse response) {
        String parentPlanId = (String)request.getSession().getAttribute("planchange-planId");
        List<TemporaryPlanChild> temporaryPlanChildList = getChildList(parentPlanId, temporaryPlan);
        long count = temporaryPlanChildList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanChildList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 计划变更-后置
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "temporaryPlanPostposeList")
    public void temporaryPlanPostposeList(TemporaryPlanDto temporaryPlan, HttpServletRequest request,
                                          HttpServletResponse response) {
        String preposeIds = (String)request.getSession().getAttribute("planchange-planId");
        List<TemporaryPlanPostpose> temporaryPlanPostposeList = getTemporaryPlanPostposeList(
                preposeIds, temporaryPlan);
        long count = temporaryPlanPostposeList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanPostposeList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    @RequestMapping(params = "saveFiles", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveFiles(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            Set<String> mapKeys = fileMap.keySet();
            for (String mapKey : mapKeys) {
                MultipartFile mf = fileMap.get(mapKey);
                InputStream fos = mf.getInputStream();// 获取文件流
                String fileName = mf.getOriginalFilename();
                String realName = "UPLOAD_" + DateUtil.getCurrDate("yyyyMMddHHmmss") + "."
                        + FileUtil.getExtend(fileName);
                String uploadSuccessPath = JackrabbitUtil.handleFileUpload(fos, realName,
                        JackrabbitConstants.PLAN_CHANGE_FILE_PATH, false);
                request.getSession().setAttribute("planchange-uploadSuccessPath",
                        uploadSuccessPath);
                request.getSession().setAttribute("planchange-uploadSuccessFileName", fileName);
                j.setObj(fileName + "," + uploadSuccessPath);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new GWException(e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 跳转变更记录页面
     *
     * @author zhousuxia
     * @version 2018年5月3日
     * @see PlanChangeController
     * @since
     */
    @RequestMapping(params = "goPlanChangeRecord")
    public ModelAndView goPlanChangeRecord(HttpServletRequest request) {
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        request.setAttribute("useObjectId", useObjectId);
        request.setAttribute("useObjectType", useObjectType);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        List<PlanChangeRecordVo> recordList = new ArrayList<PlanChangeRecordVo>();
        // 查找计划变更历史
        List<PlanHistoryDto> planHistoryList = feignPlanChangeServiceI.getPlanHistoryListByPlanId(useObjectId);
        // 查找计划变更临时历史数据recordDatagrid
        List<TemporaryPlanDto> temporyHistoryList = feignPlanChangeServiceI.getTemporaryPlanListByPlanId(useObjectId);
        if (!CommonUtil.isEmpty(planHistoryList)) {
            for (PlanHistoryDto plan : planHistoryList) {
                for (TemporaryPlanDto temPlan : temporyHistoryList) {
                    if (plan.getFormId().equals(temPlan.getFormId())) {
                        PlanChangeRecordVo vo = new PlanChangeRecordVo();
                        vo.setId(plan.getId());
                        vo.setChangePlanId(plan.getPlanId());
                        vo.setChangeFormId(plan.getFormId());
                        vo.setChangeOwner(UserUtil.getFormatUserNameId(temPlan.getCreateBy()));
                        vo.setChangeRemark(temPlan.getChangeRemark());
                        vo.setChangeType(temPlan.getChangeTypeInfo().getName());
                        vo.setChangeTime(df.format(plan.getCreateTime()));
                        recordList.add(vo);
                    }
                }
            }
        }
        request.setAttribute("recordList", recordList);
        return new ModelAndView("com/glaway/ids/project/plan/planChangeRecordInfo");
    }


//
//    /**
//     * 交付物新增页面跳转
//     *
//     * @return
//     */
//    @RequestMapping(params = "goAddDocumentTemp")
//    public ModelAndView goAddDocumentTemp(TempPlanDeliverablesInfoDto tempPlanDeliverablesInfo,
//                                          HttpServletRequest req) {
//        req.setAttribute("tempPlanDeliverablesInfo_", tempPlanDeliverablesInfo);
//        return new ModelAndView("com/glaway/ids/project/plan/planDocumentTemp-add");
//    }


//
//    @SuppressWarnings("finally")
//    @RequestMapping(params = "doAddForPlanChangeLocalDoc")
//    @ResponseBody
//    public AjaxJson doAddForPlanChangeLocalDoc(ProjLibDocumentVo document, HttpServletRequest request,
//                                               HttpSession session, RepFileTypeDto repFileType) {
//        AjaxJson j = new AjaxJson();
//        String projectId = request.getParameter("projectId");
//        Project project = projectService.getEntity(Project.class, projectId);
//        String projectNo = project.getProjectNumber();
//        SerialNumberManager.getDataMap().put("projectNo", projectNo);
//        String docattachmentName = request.getParameter("docattachmentName");
//        String docattachmentURL = request.getParameter("docattachmentURL");
//        String docAttachmentShowName = request.getParameter("docAttachmentShowName");
//        String docattachmentNames = "";
//        String docattachmentURLs = "";
//        String docAttachmentShowNames = "";
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(docattachmentName)) {
//            docattachmentNames = docattachmentName.trim().toString();
//        }
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(docattachmentName)) {
//            docattachmentURLs = docattachmentURL.trim().toString();
//        }
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(docattachmentName)) {
//            docAttachmentShowNames = docAttachmentShowName.trim().toString();
//        }
//
//        String type = "文档";
//        /**
//         * 文件类型由RepFileType
//         */
//        String fileTypeId=repFileTypeQueryService.getFileTypeIdByCode(RepFileTypeConstants.REP_FILE_TYPE_PRO);
//        document.setFileTypeId(fileTypeId);
//
//        message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createSuccess");
//        String dictCode = "secretLevel";
//        List<TSType> curTypes = TSTypegroup.allTypes.get(dictCode.toLowerCase());
//
//        Map<String, String> secretLevelCodeAndNameMap = new HashMap<String, String>();
//        for (TSType curData : curTypes) {
//            secretLevelCodeAndNameMap.put(curData.getTypename(), curData.getTypecode());
//        }
//        try {
//
//            if (projLibService.validateReptDocNum(document.getDocNumber())) {
//                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.docNumberExist");
//                j.setSuccess(false);
//                return j;
//            }
//            document.setType(1);
//            String fileId = projLibService.createFile(document);
//
//            String useObjectId = request.getParameter("useObjectId");
//            String useObjectType = request.getParameter("useObjectType");
//
//            List<TempPlanInputs> list = (List<TempPlanInputs>)request.getSession().getAttribute(
//                    "planChange-inputList");
//
//            if(CommonUtil.isEmpty(list)){
//                list = new ArrayList<TempPlanInputs>();;
//            }
//
//            if (CommonUtils.isNotEmpty(docattachmentNames)) {
//                for (int i = 0; i < docattachmentNames.split(",").length; i++ ) {
//
//                    RepFileAttachment fileAttachment = new RepFileAttachment();
//                    fileAttachment.setAttachmentName(docattachmentNames.split(",")[i]);
//                    fileAttachment.setAttachmentURL(docattachmentURLs.split(",")[i]);
//                    fileAttachment.setFileId(fileId);
//                    projLibService.addRepFileAttachment(fileAttachment);
//                    fileAttachment.setFirstName(fileAttachment.getCreateName());
//                    fileAttachment.setFirstFullName(fileAttachment.getCreateFullName());
//                    fileAttachment.setFirstTime(fileAttachment.getCreateTime());
//                    projectService.update(fileAttachment);
//
//                    TempPlanInputs inputs = new TempPlanInputs();
//                    String uuid = UUID.randomUUID().toString();
//                    inputs.setId(uuid);
//                    inputs.setInputId(uuid);
//                    inputs.setUseObjectId(useObjectId);
//                    inputs.setUseObjectType(useObjectType);
//                    inputs.setName(docAttachmentShowNames.split(",")[i]);
//                    inputs.setOriginType("LOCAL");
//                    inputs.setDocName(docattachmentNames.split(",")[i]);
//                    inputs.setDocId(docattachmentURLs.split(",")[i]);
//                    list.add(inputs);
//                }
//                request.getSession().setAttribute("planChange-inputList", list);
//
//            }
//            j.setObj(fileId);
//            // 计划提交项操作记录
//            String message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.addDeliverySuccess");
//            try {
//
//            }
//            catch (Exception e) {
//                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.saveLogFailure");
//                log.error(message, e, null, message);
//                Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
//                throw new GWException(GWConstants.ERROR_2001, params, e);
//            }
//
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            message = type + I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.library.createFailure");
//            j.setSuccess(false);
//            log.error(message, e, document.getId(), document.toString());
//            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
//            throw new GWException(GWConstants.ERROR_2003, params, e);
//        }
//        finally {
//            j.setMsg(message);
//            return j;
//        }
//    }


    @RequestMapping(params = "recordDatagrid")
    public void recordDatagrid(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {

        String planId = request.getParameter("planId");
        String formId = request.getParameter("formId");

        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();

        List<PlanHistoryDto> planHistoryList = feignPlanChangeServiceI.getPlanHistoryListByPlanId(planId);
        PlanDto plan = planRemoteFeignServiceI.getPlanEntity(planId);
        // List<TemporaryPlan> temporyHistoryList=
        // planchangeService.getTemporaryPlanListByPlanIdAndFormId(planId, formId);

        if (!CommonUtil.isEmpty(planHistoryList) && !CommonUtil.isEmpty(plan)) {

            for (int i = 0; i < planHistoryList.size(); i++ ) {

                if (planHistoryList.get(i).getFormId().equals(formId)) {

                    if (i == 0) {
                        if (StringUtils.isNotEmpty(planHistoryList.get(0).getOwner())
                                && StringUtils.isNotEmpty(plan.getOwner())) {
                            if (!planHistoryList.get(0).getOwner().equals(plan.getOwner())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("负责人");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(0).getOwnerInfo().getRealName()
                                        + "-"
                                        + planHistoryList.get(0).getOwnerInfo().getUserName());
                                planChangeVo.setChangeAfter(plan.getOwnerRealName());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(0).getPlanLevel())) {
                            if (StringUtils.isEmpty(plan.getPlanLevel())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("计划等级");
                                planChangeVo.setType("修改");
                                if (!CommonUtil.isEmpty(planHistoryList.get(0).getPlanLevelInfo())) {
                                    planChangeVo.setChangeBefore(planHistoryList.get(0).getPlanLevelInfo().getName());
                                }
                                planChangeVo.setChangeAfter("");
                                planChangeVoList.add(planChangeVo);
                            }
                            if (StringUtils.isNotEmpty(plan.getPlanLevel())) {
                                if (!planHistoryList.get(0).getPlanLevel().equals(
                                        plan.getPlanLevel())) {
                                    PlanChangeVo planChangeVo = new PlanChangeVo();
                                    planChangeVo.setField("计划等级");
                                    planChangeVo.setType("修改");
                                    if (!CommonUtil.isEmpty(planHistoryList.get(0).getPlanLevelInfo())) {
                                        planChangeVo.setChangeBefore(planHistoryList.get(0).getPlanLevelInfo().getName());
                                    }
                                    planChangeVo.setChangeAfter(plan.getPlanLevelInfo().getName());
                                    planChangeVoList.add(planChangeVo);
                                }
                            }
                        }

                        if (StringUtils.isEmpty(planHistoryList.get(0).getPlanLevel())) {
                            if (StringUtils.isNotEmpty(plan.getPlanLevel())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("计划等级");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore("-");
                                planChangeVo.setChangeAfter(plan.getPlanLevelName());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(0).getOwnerInfo().getTSDepart().getDepartname())
                                && StringUtils.isNotEmpty(plan.getOwnerDept())) {
                            if (!planHistoryList.get(0).getOwnerInfo().getTSDepart().getDepartname().equals(
                                    plan.getOwnerDept())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("责任部门");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(0).getOwnerInfo().getTSDepart().getDepartname());
                                planChangeVo.setChangeAfter(plan.getOwnerDept());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(0).getPlanStartTime() != null
                                && plan.getPlanStartTime() != null) {
                            if (!planHistoryList.get(0).getPlanStartTime().equals(
                                    plan.getPlanStartTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("开始时间");
                                planChangeVo.setType("修改");
                                String planStartTimeBefore = DateUtil.dateToString(planHistoryList.get(0).getPlanStartTime(),"yyyy-MM-dd");
                                planChangeVo.setChangeBefore(planStartTimeBefore);
                                planChangeVo.setChangeAfter(DateUtil.dateToString(
                                        plan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(0).getPlanEndTime() != null
                                && plan.getPlanEndTime() != null) {
                            if (!planHistoryList.get(0).getPlanEndTime().equals(
                                    plan.getPlanEndTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("结束时间");
                                planChangeVo.setType("修改");
                                String planEndTimeBefore = DateUtil.dateToString(planHistoryList.get(0).getPlanEndTime(),"yyyy-MM-dd");
                                planChangeVo.setChangeBefore(planEndTimeBefore);
                                planChangeVo.setChangeAfter(DateUtil.dateToString(
                                        plan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(0).getWorkTime() != null
                                && plan.getWorkTime() != null) {
                            if (!planHistoryList.get(0).getWorkTime().equals(plan.getWorkTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("工期");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(0).getWorkTime());
                                planChangeVo.setChangeAfter(plan.getWorkTime());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(0).getMilestone())
                                && StringUtils.isNotEmpty(plan.getMilestone())) {
                            if (!planHistoryList.get(0).getMilestone().equals(plan.getMilestone())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("里程碑");
                                planChangeVo.setType("修改");
                                if ("true".equals(planHistoryList.get(0).getMilestone())) {
                                    planChangeVo.setChangeBefore("是");
                                }
                                else {
                                    planChangeVo.setChangeBefore("否");
                                }
                                if ("true".equals(plan.getMilestone())) {
                                    planChangeVo.setChangeAfter("是");
                                }
                                else {
                                    planChangeVo.setChangeAfter("否");
                                }
                                planChangeVoList.add(planChangeVo);
                            }
                        }
                    }
                    else {

                        if (StringUtils.isNotEmpty(planHistoryList.get(i).getOwner())
                                && StringUtils.isNotEmpty(planHistoryList.get(i - 1).getOwner())) {
                            if (!planHistoryList.get(i).getOwner().equals(
                                    planHistoryList.get(i - 1).getOwner())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("负责人");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(i).getOwnerInfo().getRealName()
                                        + "-"
                                        + planHistoryList.get(i).getOwnerInfo().getUserName());
                                planChangeVo.setChangeAfter(planHistoryList.get(i - 1).getOwnerRealName());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(i).getPlanLevel())) {
                            if (StringUtils.isEmpty(planHistoryList.get(i - 1).getPlanLevel())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("计划等级");
                                planChangeVo.setType("修改");
                                if (!CommonUtil.isEmpty(planHistoryList.get(i).getPlanLevelInfo())) {
                                    planChangeVo.setChangeBefore(planHistoryList.get(i).getPlanLevelInfo().getName());
                                }
                                planChangeVo.setChangeAfter("");
                                planChangeVoList.add(planChangeVo);
                            }
                            if (StringUtils.isNotEmpty(planHistoryList.get(i - 1).getPlanLevel())) {
                                if (!planHistoryList.get(i).getPlanLevel().equals(
                                        planHistoryList.get(i - 1).getPlanLevel())) {
                                    PlanChangeVo planChangeVo = new PlanChangeVo();
                                    planChangeVo.setField("计划等级");
                                    planChangeVo.setType("修改");
                                    if (!CommonUtil.isEmpty(planHistoryList.get(i).getPlanLevelInfo())) {
                                        planChangeVo.setChangeBefore(planHistoryList.get(i).getPlanLevelInfo().getName());
                                    }
                                    planChangeVo.setChangeAfter(planHistoryList.get(i - 1).getPlanLevelInfo().getName());
                                    planChangeVoList.add(planChangeVo);
                                }
                            }
                        }

                        if (StringUtils.isEmpty(planHistoryList.get(i).getPlanLevel())) {
                            if (StringUtils.isNotEmpty(planHistoryList.get(i - 1).getPlanLevel())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("计划等级");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore("-");
                                planChangeVo.setChangeAfter(planHistoryList.get(i - 1).getPlanLevelName());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(i).getOwnerInfo().getTSDepart().getDepartname())
                                && StringUtils.isNotEmpty(planHistoryList.get(i - 1).getOwnerDept())) {
                            if (!planHistoryList.get(i).getOwnerInfo().getTSDepart().getDepartname().equals(
                                    planHistoryList.get(i - 1).getOwnerDept())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("责任部门");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(i).getOwnerInfo().getTSDepart().getDepartname());
                                planChangeVo.setChangeAfter(planHistoryList.get(i - 1).getOwnerDept());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(i).getPlanStartTime() != null
                                && planHistoryList.get(i - 1).getPlanStartTime() != null) {
                            if (!planHistoryList.get(i).getPlanStartTime().equals(
                                    planHistoryList.get(i - 1).getPlanStartTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("开始时间");
                                planChangeVo.setType("修改");
                                String planStartTimeBefore = DateUtil.dateToString(planHistoryList.get(i).getPlanStartTime(),"yyyy-MM-dd");
                                planChangeVo.setChangeBefore(planStartTimeBefore);
                                planChangeVo.setChangeAfter(DateUtil.dateToString(
                                        planHistoryList.get(i - 1).getPlanStartTime(),
                                        DateUtil.YYYY_MM_DD));
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(i).getPlanEndTime() != null
                                && planHistoryList.get(i - 1).getPlanEndTime() != null) {
                            if (!planHistoryList.get(i).getPlanEndTime().equals(
                                    planHistoryList.get(i - 1).getPlanEndTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("结束时间");
                                planChangeVo.setType("修改");
                                String planEndTimeBefore = DateUtil.dateToString(planHistoryList.get(i).getPlanEndTime(),"yyyy-MM-dd");
                                planChangeVo.setChangeBefore(planEndTimeBefore);
                                planChangeVo.setChangeAfter(DateUtil.dateToString(
                                        planHistoryList.get(i - 1).getPlanEndTime(),
                                        DateUtil.YYYY_MM_DD));
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (planHistoryList.get(i).getWorkTime() != null
                                && planHistoryList.get(i - 1).getWorkTime() != null) {
                            if (!planHistoryList.get(i).getWorkTime().equals(
                                    planHistoryList.get(i - 1).getWorkTime())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("工期");
                                planChangeVo.setType("修改");
                                planChangeVo.setChangeBefore(planHistoryList.get(i).getWorkTime());
                                planChangeVo.setChangeAfter(planHistoryList.get(i - 1).getWorkTime());
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                        if (StringUtils.isNotEmpty(planHistoryList.get(i).getMilestone())
                                && StringUtils.isNotEmpty(planHistoryList.get(i - 1).getMilestone())) {
                            if (!planHistoryList.get(i).getMilestone().equals(
                                    planHistoryList.get(i - 1).getMilestone())) {
                                PlanChangeVo planChangeVo = new PlanChangeVo();
                                planChangeVo.setField("里程碑");
                                planChangeVo.setType("修改");
                                if ("true".equals(planHistoryList.get(i).getMilestone())) {
                                    planChangeVo.setChangeBefore("是");
                                }
                                else {
                                    planChangeVo.setChangeBefore("否");
                                }
                                if ("true".equals(planHistoryList.get(i - 1).getMilestone())) {
                                    planChangeVo.setChangeAfter("是");
                                }
                                else {
                                    planChangeVo.setChangeAfter("否");
                                }
                                planChangeVoList.add(planChangeVo);
                            }
                        }

                    }

                }


            }
        }

        long count = planChangeVoList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(planChangeVoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelInput")
    @ResponseBody
    public AjaxJson doDelInput(String ids, String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelSuccess");
        try {
            List<TempPlanInputsDto> list = new ArrayList<>();
            List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");
            for (int i = 0; i < inputList.size(); i++ ) {
                if (ids.contains(inputList.get(i).getId())) {}
                else {
                    list.add(inputList.get(i));
                }
            }
            inputList.clear();
            inputList.addAll(list);
            request.getSession().setAttribute("planChange-inputList", inputList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelFail");
            log.error(message, e, ids, "");
//            Object[] params = new Object[] {message,
//                    DeliverablesInfo.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
//            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddDocumentTemp")
    public ModelAndView goAddDocumentTemp(TempPlanDeliverablesInfoDto tempPlanDeliverablesInfo,
                                          HttpServletRequest req) {
        req.setAttribute("tempPlanDeliverablesInfo_", tempPlanDeliverablesInfo);
        return new ModelAndView("com/glaway/ids/project/plan/planDocumentTemp-add");
    }

    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "documentdatagridlist")
    @ResponseBody
    public void documentdatagridlist(HttpServletRequest request, HttpServletResponse response) {
        /*
         * String page = request.getParameter("page");
         * String rows = request.getParameter("rows");
         */
        String method = request.getParameter("method");
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");
        List<DeliveryStandardDto> searchInfoList = new ArrayList<DeliveryStandardDto>();
        List<DeliveryStandardDto> searchRmInfoList = new ArrayList<DeliveryStandardDto>();
        /*
         * BusinessConfig condition = new BusinessConfig();
         * condition.setConfigType("DELIVERSTANDARDNAME");
         * if (method != null && method.equals("search")) {
         * condition.setNo(request.getParameter("no"));
         * condition.setName(request.getParameter("name"));
         * String stopFlag = request.getParameter("stopFlag");
         * if (stopFlag.equals("START")) {
         * condition.setStopFlag(ConfigStateConstants.START);
         * }
         * else if (stopFlag.equals("STOP")) {
         * condition.setStopFlag(ConfigStateConstants.STOP);
         * }
         * }
         */

        /*
         * List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
         * ConditionVO a = new ConditionVO();
         * a.setCondition("eq");
         * a.setKey("DeliveryStandard.stopFlag");
         * a.setValue("启用");
         * conditionList.add(a);
         * if (method != null && method.equals("search")) {
         * ConditionVO b = new ConditionVO();
         * b.setCondition("like");
         * b.setKey("DeliveryStandard.no");
         * b.setValue(request.getParameter("no"));
         * conditionList.add(b);
         * ConditionVO c = new ConditionVO();
         * c.setCondition("like");
         * c.setKey("DeliveryStandard.name");
         * c.setValue(request.getParameter("name"));
         * conditionList.add(c);
         * }
         * PageList pageList = deliveryStandardService.queryEntity(conditionList);
         */
        DeliveryStandardDto condition = new DeliveryStandardDto();
        if (method != null && method.equals("search")) {
            condition.setNo(request.getParameter("no"));
            condition.setName(request.getParameter("name"));

            String stopFlag = request.getParameter("stopFlag");
            if (stopFlag.equals("START")) {
                condition.setStopFlag(ConfigStateConstants.START);
            }
            else if (stopFlag.equals("STOP")) {
                condition.setStopFlag(ConfigStateConstants.STOP);
            }

        }

        List<DeliveryStandardDto> list = this.deliveryStandardRemoteFeignServiceI.searchUseableDeliveryStandards(condition);
        /*
         * List<BusinessConfig> list =
         * this.businessConfigService.searchUseableBusinessConfigs(condition);
         * List<BusinessConfig> list2 =
         * this.businessConfigService.searchUseableBusinessConfigs(condition);
         */
        // List<DeliveryStandard> list = pageList.getResultList();
        List<DeliveryStandardDto> list2 = new ArrayList<DeliveryStandardDto>();
        list2.clear();
        // long count = this.businessConfigService.getSearchCount(condition);
        int c = 0;
        if (deliverablesInfoList != null && deliverablesInfoList.size() == 0) {
            list2.addAll(list);
        }
        else {
            for (int i = 0; i < list.size(); i++ ) {
                c = 0;
                for (int j = 0; j < deliverablesInfoList.size(); j++ ) {
                    if (deliverablesInfoList.get(j).getName() != null) {
                        if (deliverablesInfoList.get(j).getName().equals(list.get(i).getName())) {}
                        else {
                            c++ ;
                        }
                    }

                }
                if (c == deliverablesInfoList.size()) {
                    list2.add(list.get(i));
                }
            }
        }

        searchInfoList.clear();
        searchInfoList.addAll(list2);
        request.getSession().setAttribute("planChange-searchInfoList", searchInfoList);
        searchRmInfoList.clear();
        searchRmInfoList.addAll(list2);
        request.getSession().setAttribute("planChange-searchRmInfoList", searchRmInfoList);
        // String datagridStr = "{\"rows\":" + JSON.toJSONString(list) + ",\"total\":" + count +
        // "}";

        String json = com.alibaba.fastjson.JSONArray.toJSONString(list2);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + list2.size() + "}";

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
     * 新增交付物
     *
     * @param names
     * @param deliverablesInfo
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddDocument")
    @ResponseBody
    public AjaxJson doAddDocument(String names, String ids,
                                  TempPlanDeliverablesInfoDto deliverablesInfo,
                                  HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        // deliverablesInfo.setName(request.getParameter("name"));
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        deliverablesInfo.setId(null);
        try {
            String[] name = names.split(",");
            String[] id = ids.split(",");
            List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            for (int i = 0; i < name.length; i++ ) {
                TempPlanDeliverablesInfoDto tempPlanDeliverablesInfo = new TempPlanDeliverablesInfoDto();
                tempPlanDeliverablesInfo.setName(name[i]);
                tempPlanDeliverablesInfo.setId(id[i]);
                tempPlanDeliverablesInfo.setDeliverablesId(id[i]);
                deliverablesInfoList.add(tempPlanDeliverablesInfo);
            }
            request.getSession().setAttribute("planChange-deliverablesInfoList",
                    deliverablesInfoList);
            log.info(message, deliverablesInfo.getId(), deliverablesInfo.getId().toString());
        }
        catch (Exception e) {
            log.error(failMessage, e, "", deliverablesInfo.getId().toString());
            Object[] params = new Object[] {failMessage, deliverablesInfo.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelDocument")
    @ResponseBody
    public AjaxJson doDelDocument(String ids, String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelSuccess");
        try {
            List<TempPlanDeliverablesInfoDto> list = new ArrayList<TempPlanDeliverablesInfoDto>();
            List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            for (int i = 0; i < deliverablesInfoList.size(); i++ ) {
                if (StringUtil.isNotEmpty(deliverablesInfoList.get(i).getDeliverablesId())) {
                    if (ids.contains(deliverablesInfoList.get(i).getDeliverablesId())) {}
                    else {
                        list.add(deliverablesInfoList.get(i));
                    }
                }
                else {
                    if (names.contains(deliverablesInfoList.get(i).getName())) {}
                    else {
                        list.add(deliverablesInfoList.get(i));
                    }
                }
            }
            deliverablesInfoList.clear();
            deliverablesInfoList.addAll(list);
            request.getSession().setAttribute("planChange-deliverablesInfoList",
                    deliverablesInfoList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelFail");
            log.error(message, e, ids, "");
//            Object[] params = new Object[] {message,
//                    DeliverablesInfo.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
//            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 资源新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddResourceTemp")
    public ModelAndView goAddResourceTemp(TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo,
                                          HttpServletRequest req) {
        try {
            String planStartTimeForResource = req.getParameter("planStartTime");
            String planEndTimeForResource = req.getParameter("planEndTime");

            req.getSession().setAttribute("planStartTimeForResource", planStartTimeForResource);
            req.getSession().setAttribute("planEndTimeForResource", planEndTimeForResource);
            tempPlanResourceLinkInfo.setStartTime(DateUtil.getDateFromString(
                    planStartTimeForResource, DateUtil.YYYY_MM_DD));
            tempPlanResourceLinkInfo.setEndTime(DateUtil.getDateFromString(planEndTimeForResource,
                    DateUtil.YYYY_MM_DD));
            req.setAttribute("tempPlanResourceLinkInfo_", tempPlanResourceLinkInfo);
        }
        catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ModelAndView("com/glaway/ids/project/plan/planResourceTemp-addList");
    }

    /**
     * 新增交付物
     *
     * @param
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "searchResource")
    @ResponseBody
    public AjaxJson searchResource(String name, String no, String path, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            List<ResourceDto> searchResourceInfoListTemp = new ArrayList<>();
            List<ResourceDto> searchResourceInfoList = new ArrayList<>();
            List<ResourceDto> searchResourceRmInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                    "planChange-searchResourceRmInfoList");
            for (ResourceDto r : searchResourceRmInfoList) {
                Boolean a = false;
                for (String pt : path.split(",")) {
                    if (r.getPath().contains(pt)) {
                        a = true;
                    }
                }
                if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                    searchResourceInfoListTemp.add(r);
                }
            }
            searchResourceInfoList.clear();
            searchResourceInfoList.addAll(searchResourceInfoListTemp);
            request.getSession().setAttribute("planChange-searchResourceInfoList",
                    searchResourceInfoList);
            log.info(message, name, name);
        }
        catch (Exception e) {
            log.error(failMessage, e, "", name.toString());
            Object[] params = new Object[] {failMessage, name.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceAddlist")
    @ResponseBody
    public void resourceAddlist(TempPlanResourceLinkInfoDto resourceLinkInfo,
                                HttpServletRequest request, HttpServletResponse response) {
        List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.searchUsables(new ResourceDto());
        List<ResourceDto> resourceListTemp2 = new ArrayList<>();
        List<ResourceDto> searchResourceInfoList = new ArrayList<>();
        List<ResourceDto> searchResourceRmInfoList = new ArrayList<>();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");
        int c = 0;
        if (resourceListTemp != null) {
            if (resourceLinkInfoList != null && resourceLinkInfoList.size() == 0) {
                resourceListTemp2.addAll(resourceListTemp);
            }
            else {
                /*for (int i = 0; i < resourceListTemp.size(); i++ ) {
                    c = 0;
                    if (resourceLinkInfoList != null) {
                        for (int j = 0; j < resourceLinkInfoList.size(); j++ ) {
                            if (resourceLinkInfoList.get(j).getResourceInfo() != null) {
                                if (resourceLinkInfoList.get(j).getResourceId().equals(
                                        resourceListTemp.get(i).getId())) {}
                                else {
                                    c++ ;
                                }
                            }

                        }
                    }
                    if (resourceLinkInfoList != null && c == resourceLinkInfoList.size()) {
                        resourceListTemp2.add(resourceListTemp.get(i));
                    }

                }*/
                String resourceIds = "";
                for(TempPlanResourceLinkInfoDto tempResource : resourceLinkInfoList){
                    if(CommonUtil.isEmpty(resourceIds)){
                        resourceIds = tempResource.getResourceId();
                    }else{
                        resourceIds = resourceIds+ "," +tempResource.getResourceId();
                    }
                }
                for(ResourceDto resource : resourceListTemp){
                    if(!resourceIds.contains(resource.getId())){
                        resourceListTemp2.add(resource);
                    }
                }
            }
        }

        if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {

            String planStartTime = (String)request.getSession().getAttribute("planStartTime");
            String planEndTime = (String)request.getSession().getAttribute("planEndTime");
            List<ResourceDto> resourceListTempOld = new ArrayList<>();
            // 复制原先的集合用于判断需要删除的资源
            for (int i = 0; i < resourceListTemp2.size(); i++ ) {
                resourceListTempOld.add(resourceListTemp2.get(i));
            }

            try {
                for (int i = 0; i < resourceListTempOld.size(); i++ ) {
                    // 判断资源设置中的开始时间与结束时间知不是在计划的开始时间与结束时间的区间内 不是移除 true 代表第二个日期在后面 修改BUG
                    // 资源时间与计划时间有交集就可以 及资源时间 结束时间 大于等于计划时间 或者开始时间小于等于计划的结束时间
                    if (StringUtils.isNotEmpty(DateUtil.getStringFromDate(
                            resourceListTempOld.get(i).getStartTime(), DateUtil.YYYY_MM_DD))
                            && StringUtils.isNotEmpty(DateUtil.getStringFromDate(
                            resourceListTempOld.get(i).getEndTime(), DateUtil.YYYY_MM_DD))) {
                        /*
                         * if(!(DateUtil.dateCompare(resourceListTempOld.get(i).getStartTime(),DateUtil
                         * .getDateFromString(planStartTime, DateUtil.YYYY_MM_DD))
                         * ||DateUtil.dateCompare(DateUtil.getDateFromString(planEndTime,
                         * DateUtil.YYYY_MM_DD), resourceListTempOld.get(i).getEndTime())))
                         * {
                         * resourceListTemp2.remove(resourceListTempOld.get(i));
                         * }
                         */
                        if (DateUtil.dateCompare(
                                DateUtil.getDateFromString(planEndTime, DateUtil.YYYY_MM_DD),
                                resourceListTempOld.get(i).getStartTime())
                                || DateUtil.dateCompare(resourceListTempOld.get(i).getEndTime(),
                                DateUtil.getDateFromString(planStartTime, DateUtil.YYYY_MM_DD))) {
                            resourceListTemp2.remove(resourceListTempOld.get(i));
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            searchResourceInfoList.clear();
            searchResourceInfoList.addAll(resourceListTemp2);
            searchResourceRmInfoList.clear();
            searchResourceRmInfoList.addAll(resourceListTemp2);

            String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceListTemp2);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + resourceListTemp2.size()
                    + "}";

            TagUtil.ajaxResponse(response, datagridStr);
        }
        request.getSession().setAttribute("planChange-searchResourceRmInfoList",
                searchResourceRmInfoList);

    }

    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridResourceSearchlist")
    @ResponseBody
    public void datagridResourceSearchlist(HttpServletRequest request, HttpServletResponse response) {
        List<ResourceDto> searchResourceInfoList = (List<ResourceDto>)request.getSession().getAttribute(
                "planChange-searchResourceInfoList");
        String json = com.alibaba.fastjson.JSONArray.toJSONString(searchResourceInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + searchResourceInfoList.size()
                + "}";

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
     * 新增交付物
     *
     * @param
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddResource")
    @ResponseBody
    public AjaxJson doAddResource(String ids, TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo,
                                  HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            String planStartTimeForResource = (String)request.getSession().getAttribute(
                    "planStartTimeForResource");
            String planEndTimeForResource = (String)request.getSession().getAttribute(
                    "planEndTimeForResource");
            Date planStartTime = DateUtil.getDateFromString(planStartTimeForResource,
                    DateUtil.YYYY_MM_DD);
            Date planEndTime = DateUtil.getDateFromString(planEndTimeForResource,
                    DateUtil.YYYY_MM_DD);
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
            Map<String, String> resourcePathMap = new HashMap<String, String>();
            Map<String, String> resourceNameMap = new HashMap<String, String>();
            for (ResourceDto resource : resourceListTemp) {
                resourcePathMap.put(resource.getId(), resource.getPath());
                resourceNameMap.put(resource.getId(), resource.getName());
            }

            for (String id : ids.split(",")) {
                TempPlanResourceLinkInfoDto t = new TempPlanResourceLinkInfoDto();
                ResourceDto resource = resourceRemoteFeignServiceI.getEntity(id);
                t.setResourceName(resource.getName());
                t.setStartTime(planStartTime);
                t.setEndTime(planEndTime);
                t.setUseRate("100");
                t.setId(resource.getId());
                t.setResourceId(resource.getId());
                t.setResourceInfo(resource);
                // t.setResourceInfo(resource);
                t.setResourceType(resourcePathMap.get(id));

                resourceLinkInfoList.add(t);
            }
            request.getSession().setAttribute("planChange-resourceLinkInfoList",
                    resourceLinkInfoList);
            log.info(message, ids, ids);
        }
        catch (Exception e) {
            log.error(failMessage, e, "", ids);
            Object[] params = new Object[] {failMessage, ids};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelResource")
    @ResponseBody
    public AjaxJson doDelResource(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelSuccess");
        try {
            List<TempPlanResourceLinkInfoDto> list = new ArrayList<>();
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            for (int i = 0; i < resourceLinkInfoList.size(); i++ ) {
                if (ids.contains(resourceLinkInfoList.get(i).getResourceId())) {}
                else {
                    list.add(resourceLinkInfoList.get(i));
                }
            }
            resourceLinkInfoList.clear();
            resourceLinkInfoList.addAll(list);
            request.getSession().setAttribute("planChange-resourceLinkInfoList",
                    resourceLinkInfoList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelFail");
            log.error(message, e, ids, "");
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 获取datagrid中的值
     *
     * @param
     * @param request
     * @param response
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "modifyResourceList")
    @ResponseBody
    public AjaxJson modifyResourceList(String resourceNames, String useRates, String startTimes,
                                       String endTimes, HttpServletRequest request,
                                       HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            String[] resourceName = resourceNames.split(",");
            String[] useRate = useRates.split(",");
            String[] startTime = startTimes.split(",");
            String[] endTime = endTimes.split(",");

            List<TempPlanResourceLinkInfoDto> resourceLst = new ArrayList<>();
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoTempList = new ArrayList<TempPlanResourceLinkInfoDto>();
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            for (int i = 0; i < resourceName.length; i++ ) {
                TempPlanResourceLinkInfoDto p = new TempPlanResourceLinkInfoDto();
                p.setResourceName(resourceName[i]);
                p.setUseRate(useRate[i]);
                Date startTime2 = DateUtil.getDateFromString(startTime[i], DateUtil.YYYY_MM_DD);
                p.setStartTime(startTime2);
                Date endTime2 = DateUtil.getDateFromString(endTime[i], DateUtil.YYYY_MM_DD);
                p.setEndTime(endTime2);
                resourceLst.add(p);
            }
            for (TempPlanResourceLinkInfoDto r : resourceLinkInfoList) {
                for (TempPlanResourceLinkInfoDto rTemp : resourceLst) {
                    if (r.getResourceName().equals(rTemp.getResourceName())) {
                        r.setUseRate(rTemp.getUseRate());
                        r.setStartTime(rTemp.getStartTime());
                        r.setEndTime(rTemp.getEndTime());
                        resourceLinkInfoTempList.add(r);
                        break;
                    }
                }
            }
            resourceLinkInfoList.clear();
            resourceLinkInfoList.addAll(resourceLinkInfoTempList);
            request.getSession().setAttribute("planChange-resourceLinkInfoList",
                    resourceLinkInfoList);

        }
        catch (Exception e) {
            log.error(failMessage, e, "", resourceNames);
            Object[] params = new Object[] {failMessage, resourceNames};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }

    }

    /**
     * 项目计划变更首节点页面跳转
     *
     * @param
     * @param req
     * @return
     * @throws ParseException
     * @see
     */
    @RequestMapping(params = "goChangePlanFlow")
    public ModelAndView goChangePlanFlow(TemporaryPlanDto temporaryPlan, HttpServletRequest req)
            throws ParseException {
        String formId = req.getParameter("formId");
        String taskId = req.getParameter("taskId");
        String path = req.getParameter("path");
        if (StringUtil.isEmpty(path)) {}
        else {
            String planId = req.getParameter("planId");
            PlanDto plan = planRemoteFeignServiceI.getPlanEntity(planId);
            formId = plan.getFormId();
            taskId = planFlowForworkFeignServiceI.getChangeTaskIdByFormId(formId);
            taskId = CodeUtils.replaceQuotes(taskId);
            req.setAttribute("fromPath", "tree");
            req.setAttribute("useObjectId", plan.getId());
            req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
        }
        req.getSession().setAttribute("planchange-taskIdSave", taskId);
        req.getSession().setAttribute("planchange-formId", formId);

        Map<String, Object> variables = workFlowCommonServiceI.getVariablesByTaskId(taskId);
        String leader = (String)variables.get("leader");
        String deptLeader = (String)variables.get("deptLeader");
        TSUserDto t = feignUserService.getUserByUserName(leader);
        TSUserDto t2 = feignUserService.getUserByUserName(deptLeader);
        req.setAttribute("leaderId", t.getId());
        req.setAttribute("deptLeaderId", t2.getId());
        req.setAttribute("leader", leader);
        req.setAttribute("deptLeader", deptLeader);

        // 计划变更类型
        BusinessConfig planChangeCategory = new BusinessConfig();
        planChangeCategory.setConfigType(ConfigTypeConstants.PLANCHANGECATEGORY);
        planChangeCategory.setStopFlag(ConfigStateConstants.START);
        planChangeCategory.setAvaliable("1");
        List<BusinessConfig> planChangeCategoryList = businessConfigService.searchBusinessConfigs(planChangeCategory);
//        List<BusinessConfig> planChangeCategoryList = businessConfigService.searchUseableBusinessConfigs(planChangeCategory);

        req.setAttribute("planChangeCategoryList", planChangeCategoryList);
        String jonStr = JsonUtil.getCodeTitleJson(planChangeCategoryList, "id", "name");
        req.setAttribute("planChangeCategoryListStr", jonStr);

        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        temporaryPlan = planRemoteFeignServiceI.getTemporaryPlanEntity(approve.get(0).getPlanId());
        PlanDto plan = new PlanDto();

        List<TSUserDto> userList = new ArrayList<>();
        if (StringUtils.isNotEmpty(temporaryPlan.getProjectId())) {
            Project project = projectRemoteFeignServiceI.getProjectEntity(temporaryPlan.getProjectId());
            List<TSUserDto> users = projRoleRemoteFeignServiceI.getUserInProject(project.getId());
            for (int i = 0; i < users.size(); i++ ) {
                TSUserDto o = feignUserService.getUserByUserId(users.get(i).getId());
                userList.add(o);
            }
        }

        JSONArray deptList = new JSONArray();
        JSONObject obj = null;
        for (TSUserDto user : userList) {
            obj = new JSONObject();
            obj.put("userId", user.getId());

            // 部门为空判断
            if (user.getTSDepart() != null
                    && StringUtils.isNotEmpty(user.getTSDepart().getDepartname())) {
                obj.put("departname", user.getTSDepart().getDepartname());
            }
            else {
                obj.put("departname", "");
            }

            deptList.add(obj);
        }

        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
//        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
        planLevel.setStopFlag(ConfigStateConstants.START);
        planLevel.setAvaliable("1");
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);

        /* temporaryPlan.setPlanLevel(""); */// getEntity直接改变值了，危险、
        String a = "0";
        if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
            for (BusinessConfig b : planLevelList) {
                if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                    temporaryPlan.setPlanLevel(temporaryPlan.getPlanLevel());
                    a = "1";
                    break;
                }
            }
        }
        else {
            temporaryPlan.setPlanLevel("");
        }
        if ("0".equals(a)) {
            temporaryPlan.setPlanLevel("");
        }

        if (temporaryPlan.getOwnerInfo() != null
                && temporaryPlan.getOwnerInfo().getTSDepart() != null) {
            temporaryPlan.setOwnerDept(temporaryPlan.getOwnerInfo().getTSDepart().getDepartname());
        }

        List<BusinessConfig> resPlanLevelList = new ArrayList<BusinessConfig>();

        if(!CommonUtil.isEmpty(planLevelList)){
            for (int i = 0; i < planLevelList.size(); i++ ) {
                resPlanLevelList.add(planLevelList.get(i));
            }
        }


        // 组装前置数据
        String preposeEndTime = "";
        temporaryPlan = planRemoteFeignServiceI.getTemporaryPlanEntity(approve.get(0).getPlanId());
        Date parentStartTime = new Date();
        Date parentEndTime = new Date();
        if (temporaryPlan != null) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanId())) {
                plan = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
            }
            if (StringUtils.isNotEmpty(temporaryPlan.getProjectId())) {
                Project project = projectRemoteFeignServiceI.getProjectEntity(temporaryPlan.getProjectId());
                parentStartTime = project.getStartProjectTime();
                parentEndTime = project.getEndProjectTime();
            }
            if (StringUtils.isNotEmpty(temporaryPlan.getParentPlanId())) {
                PlanDto parent = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getParentPlanId());
                parentStartTime = parent.getPlanStartTime();
                parentEndTime = parent.getPlanEndTime();
            }

            if (!CommonUtil.isEmpty(plan)) {
                temporaryPlan.setAssigner(plan.getAssigner());
                temporaryPlan.setAssignerInfo(plan.getAssignerInfo());
                temporaryPlan.setAssignTime(plan.getAssignTime());
                temporaryPlan.setCreateBy(plan.getCreateBy());
                temporaryPlan.setCreateFullName(plan.getCreateFullName());
                temporaryPlan.setCreateName(plan.getCreateName());
                temporaryPlan.setCreateTime(plan.getCreateTime());

                if (!PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
                        && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
                    if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.PAUSE_CHI);
                    }
                    else if (ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.CLOSE_CHI);
                    }
                    else {
                        // 获取计划的生命周期
                        PlanDto statusP = new PlanDto();

                        FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                        String lifeCycStr = String.valueOf(fj.getObj());
                        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);

                        for (LifeCycleStatus status : statusList) {
                            if (status.getName().equals(plan.getBizCurrent())) {
                                temporaryPlan.setStatus(status.getTitle());
                                break;
                            }
                        }
                    }
                }
                else {
                    // 获取计划的生命周期
                    FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                    String lifeCycStr = String.valueOf(fj.getObj());
                    List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);
                    for (LifeCycleStatus status : statusList) {
                        if (status.getName().equals(plan.getBizCurrent())) {
                            plan.setStatus(status.getTitle());
                            break;
                        }
                    }
                }
            }

            String preposePlans = "";
            if (StringUtils.isNotEmpty(temporaryPlan.getPreposeIds())) {
                for (String id : temporaryPlan.getPreposeIds().split(",")) {
                    PlanDto preposePlan = planRemoteFeignServiceI.getPlanEntity(id);
                    if (preposePlan != null) {
                        String endTime = "";
                        if (preposePlan.getPlanEndTime() != null) {
                            endTime = DateUtil.getStringFromDate(preposePlan.getPlanEndTime(),
                                    DateUtil.YYYY_MM_DD);
                            if (StringUtils.isEmpty(preposeEndTime)) {
                                if (StringUtils.isNotEmpty(endTime)) {
                                    preposeEndTime = endTime;
                                }
                            }
                            else {
                                if (StringUtils.isNotEmpty(endTime)) {
                                    Date endTimeDate = DateUtil.getDateFromString(endTime,
                                            DateUtil.YYYY_MM_DD);
                                    Date preposeEndTimeDate = DateUtil.getDateFromString(
                                            preposeEndTime, DateUtil.YYYY_MM_DD);
                                    if (endTimeDate.getTime() > preposeEndTimeDate.getTime()) {
                                        preposeEndTime = endTime;
                                    }
                                }
                            }
                        }

                        if (StringUtils.isNotEmpty(preposePlans)) {
                            preposePlans = preposePlans + "," + preposePlan.getPlanName();
                        }
                        else {
                            preposePlans = preposePlan.getPlanName();
                        }
                    }

                }
            }
            temporaryPlan.setPreposePlans(preposePlans);
            req.getSession().setAttribute("planchange-planId", temporaryPlan.getPlanId());
            req.getSession().setAttribute("planchange-uploadSuccessFileName",
                    temporaryPlan.getChangeInfoDocName());
        }

        userList = feignUserService.getAllUsers();
        String jonStr3 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        req.setAttribute("userList2", jonStr3);
        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        req.setAttribute("userList", jonStr2);
        String jonStr4 = JsonUtil.getCodeTitleJson(resPlanLevelList, "id", "name");
        req.setAttribute("planLevelList_", jonStr4);

        String parentStartTimeStr = DateUtil.dateToString(parentStartTime,"yyyy-MM-dd");
        String parentEndTimeStr = DateUtil.dateToString(parentEndTime,"yyyy-MM-dd");

        req.setAttribute("parentStartTime", parentStartTimeStr);
        req.setAttribute("parentEndTime", parentEndTimeStr);
        // req.setAttribute("userList", userList);
        req.setAttribute("preposeEndTime", preposeEndTime);
        req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
        // req.setAttribute("ownerShow", temporaryPlan.getOwnerInfo().getRealName());

        TSUserDto userDto = feignUserService.getUserByUserId(temporaryPlan.getOwner());
        temporaryPlan.setOwnerInfo(userDto);
        Map<String, List<TSDepartDto>> departMap = departService.getAllTSDepartByCache(ResourceUtil.getApplicationInformation().getAppKey(),"");
        if(!CommonUtil.isEmpty(temporaryPlan.getOwner())){
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
                temporaryPlan.setOwnerDept(depName);
            }
        }
        if (temporaryPlan.getOwnerInfo() != null) {
            req.setAttribute("ownerShow", temporaryPlan.getOwnerInfo().getRealName() + "-"
                    + temporaryPlan.getOwnerInfo().getUserName());
        }
        else {
            req.setAttribute("ownerShow", "");
        }
        req.setAttribute("planLevelList", planLevelList);
        req.setAttribute("disabled", "true");
        req.getSession().setAttribute("planchange-temporaryId", approve.get(0).getPlanId());
        req.setAttribute("temporaryPlan_", temporaryPlan);

        PlanDto p = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
        temporaryPlan.setTaskNameType(p.getTaskNameType());
        Map<String,Map<String,String>> map = new HashMap<>();

        TabCombinationTemplateDto templateDto = tabCombinationTemplateFeignService.findTabCbTempByPlanId(temporaryPlan.getPlanId());

        List<String> objectPathList = new ArrayList<>();
        List<String> objectPathStrList = new ArrayList<>();

        String typeIds = req.getParameter("typeIds");
        req.setAttribute("typeIds",typeIds);

        List<TSUserDto> userListTemp = userService.getAllUsers();

        if(!CommonUtil.isEmpty(templateDto)){
            String displayAccess = "0";   //编制时显示的页签
            List<List<List<List<ObjectPropertyInfoDto>>>> lists = tabTemplateServiceImpl.goTabsView(req,templateDto.getId(),displayAccess,"",typeIds);
            List<TabTemplateDto> tabTemplateList = new ArrayList<>();
            FeignJson combTemplateInfos = tabCombinationTemplateFeignService.getCombTemplateInfos(templateDto.getId());


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
                planMap = objectToMap(temporaryPlan);
            }
            catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            List<PlanLoadUrlVo> planLoadUrlVoList = new ArrayList<>();
            PlanLoadUrlVo loadUrlVo1 = new PlanLoadUrlVo();
            loadUrlVo1.setId("1");
            loadUrlVo1.setPropertyName("owner");
            loadUrlVo1.setLoadUrl("planController.do?userList2&projectId="+temporaryPlan.getProjectId());
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
            loadUrlVo3.setLoadUrl("planController.do?getTaskTypes&parentPlanId="+temporaryPlan.getParentPlanId()+"&projectId="+req.getParameter("projectId"));
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

                            String pathStr = "";
                            if(!objList.getControl().equals("8") &&!objList.getControl().equals("7") ){
                                String objectPath = objList.getObjectPath();
                                if(!CommonUtil.isEmpty(objectPath)){
                                    pathStr = objectPath.substring(objectPath.lastIndexOf(".")+1);
                                    objList.setId(pathStr+"-"+objList.getPropertyValue());
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
                                    objList.setDefaultValue(temporaryPlan.getPlanStartTime());
                                }
                                if("planEndTime".equals(objList.getPropertyValue())){
                                    objList.setDefaultValue(temporaryPlan.getPlanEndTime());
                                }

                                if("assignTime".equals(objList.getPropertyValue()) && !CommonUtil.isEmpty(temporaryPlan.getAssignTime())){
                                    objList.setDefaultValue(temporaryPlan.getAssignTime());
                                }

                                if("assigner".equals(objList.getPropertyValue()) && !CommonUtil.isEmpty(temporaryPlan.getAssigner())){
                                    for(TSUserDto user : userListTemp){
                                        if(temporaryPlan.getAssigner().equals(user.getId())){
                                            objList.setValueInfo(user.getRealName()+"-"+user.getUserName());
                                        }
                                    }
                                }

                                if("preposeIds".equals(objList.getPropertyValue())){
                                    objList.setValueInfo(temporaryPlan.getPreposePlans());
                                }

                                if("planLevel".equals(objList.getPropertyValue())){
                                    objList.setValueInfo(temporaryPlan.getPlanLevel());
                                }

                                if("planName".equals(objList.getPropertyValue())){
                                    objList.setReadOnly(true);
                                }
                                if("taskType".equals(objList.getPropertyValue())){
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
                                objList.setLoadUrl("resourceLinkInfoController.do?list");
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
                planDataVo.setPlanName(temporaryPlan.getPlanName());
            }
            if(!CommonUtil.isEmpty(temporaryPlan.getOwner())){
                TSUserDto user = userService.getUserByUserId(temporaryPlan.getOwner());
                planDataVo.setOwner(user.getRealName()+"-"+user.getUserName());
            }

            planDataVo.setOwnerDept(temporaryPlan.getOwnerDept());
            if(!CommonUtil.isEmpty(temporaryPlan.getPlanLevel()) && !"===".equals(temporaryPlan.getPlanLevel())){
                BusinessConfig businessConfig = businessConfigService.getBusinessConfig(temporaryPlan.getPlanLevel());
                planDataVo.setPlanLevel(businessConfig.getName());
            }

            planDataVo.setPlanEndTime(DateUtil.getStringFromDate(temporaryPlan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setPlanStartTime(DateUtil.getStringFromDate(temporaryPlan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setCreateTime(DateUtil.getStringFromDate(temporaryPlan.getCreateTime(), DateUtil.YYYY_MM_DD));
            planDataVo.setCreateFullName(temporaryPlan.getCreateFullName()+"-"+temporaryPlan.getCreateName());
            if("false".equals(temporaryPlan.getMilestone()) || "否".equals(temporaryPlan.getMilestone())){
                planDataVo.setMilestone("否");
            }else{
                planDataVo.setMilestone("是");
            }
            planDataVo.setBizCurrent(temporaryPlan.getStatus());
            planDataVo.setWorkTime(temporaryPlan.getWorkTime());
            planDataVo.setTaskType(temporaryPlan.getTaskType());
            planDataVo.setRemark(temporaryPlan.getRemark());
            if(!CommonUtil.isEmpty(req.getParameter("taskNameType"))){
                planDataVo.setTaskNameType(req.getParameter("taskNameType"));
            }else{
                ActivityTypeManageDto activityTypeManageDto = activityTypeManageService.queryActivityTypeManageById(temporaryPlan.getTaskNameType());
                planDataVo.setTaskNameType(activityTypeManageDto.getName());
            }

            req.setAttribute("operation",resStr);
            String planStr = JsonUtil.map2json(planDataVo);
            String resPlanStr = stringToJson(planStr,true);
            req.setAttribute("planDefault",resPlanStr);
            req.setAttribute("enterType","0");
            req.setAttribute("isPlanChange","planChangeView");
        }


   //     return new ModelAndView("com/glaway/ids/project/plan/plan-changeOneEdit");
        return new ModelAndView("com/glaway/ids/project/plan/plan-changeOneEditForGeneral");  //计划信息页签改为页签组合模板形式
    }

    /**
     * 项目计划单条变更查看页面跳转
     *
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangePlanView")
    public ModelAndView goChangePlanView(HttpServletRequest req) {
        String taskNumber = req.getParameter("taskNumber");
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(taskNumber);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto temporaryPlan = null;
        List<com.alibaba.fastjson.JSONObject> objList = new ArrayList<>();
        if (!approve.isEmpty()){
            temporaryPlan = feignPlanChangeServiceI.getTemporaryPlan(approve.get(0).getPlanId());
            if (temporaryPlan == null) {
                TemporaryPlanDto t = new TemporaryPlanDto();
                t.setPlanId(approve.get(0).getPlanId());
                t.setFormId(taskNumber);
                List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
                temporaryPlan = list.get(0);
            }
            FeignJson combTemplateInfos = tabCbTemplateFeignService.getCombTemplateInfosByPlanId(temporaryPlan.getPlanId(),"","");
            ObjectMapper mapper = new ObjectMapper();
            List<CombinationTemplateVo> voList = mapper.convertValue(combTemplateInfos.getObj(),new com.fasterxml.jackson.core.type.TypeReference<List<CombinationTemplateVo>>(){});
            com.alibaba.fastjson.JSONObject obj1 = new com.alibaba.fastjson.JSONObject();
            obj1.put("title","变更原因");
            obj1.put("id","changeReason");
            obj1.put("displayAccess","");
            obj1.put("href","planChangeController.do?goChangePlanViewTab&tabIndex=1&taskNumber="+taskNumber);
            objList.add(obj1);
            if(!CommonUtil.isEmpty(voList)){
                for(int i = 0;i<voList.size();i++){
                    com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
                    obj.put("title",voList.get(i).getName());
                    obj.put("id",voList.get(i).getTypeId());
                    obj.put("displayAccess",voList.get(i).getDisplayAccess());
                    int index = i+1;
                    obj.put("href","tabCombinationTemplateController.do?goTabCommonDetailForPlanChange&onlyReadonly=1&taskNumber="+taskNumber+"&id="+temporaryPlan.getProjectId()+"&planId="+temporaryPlan.getPlanId()+"&enterType=0&tabId="+voList.get(i).getTypeId()+"&index="+index);
                    objList.add(obj);
                }
            }
            com.alibaba.fastjson.JSONObject obj2 = new com.alibaba.fastjson.JSONObject();
            obj2.put("title","影响分析");
            obj2.put("id","changeAnalysis");
            obj2.put("displayAccess","");
            obj2.put("href","planChangeController.do?goChangePlanViewTab&tabIndex=3&taskNumber="+taskNumber);
            objList.add(obj2);
            com.alibaba.fastjson.JSONObject obj3 = new com.alibaba.fastjson.JSONObject();
            obj3.put("title","变更对比");
            obj3.put("id","changeTotal");
            obj3.put("displayAccess","");
            obj3.put("href","planChangeController.do?goChangePlanViewTab&tabIndex=4&taskNumber="+taskNumber);
            objList.add(obj3);
        }


        ModelAndView mav = new ModelAndView("com/glaway/ids/project/plan/plan-changeOneView");
        mav.addObject("taskNumber", taskNumber);
        mav.addObject("temporaryPlan_", temporaryPlan);
        mav.addObject("tabList",JSON.toJSONString(objList));
        return mav;
    }

    /**
     * 项目计划单条变更查看tab页面跳转
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangePlanViewTab")
    public ModelAndView goChangePlanViewTab(HttpServletRequest req) {
        ModelAndView mav = null;
        String tabIndex = req.getParameter("tabIndex");
        String formId = req.getParameter("taskNumber");
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto temporaryPlan = feignPlanChangeServiceI.getTemporaryPlan(
                approve.get(0).getPlanId());
        PlanDto plan = new PlanDto();
        if (temporaryPlan == null) {
            TemporaryPlanDto t = new TemporaryPlanDto();
            t.setPlanId(approve.get(0).getPlanId());
            t.setFormId(formId);
            List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
            temporaryPlan = list.get(0);
        }
        if (temporaryPlan != null) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanId())) {
                plan = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
            }
            if(!CommonUtil.isEmpty(temporaryPlan.getChangeType())){
                BusinessConfig bc = businessConfigService.getBusinessConfig(temporaryPlan.getChangeType());
                temporaryPlan.setChangeTypeInfo(bc);
            }
            if (temporaryPlan.getOwnerInfo() != null) {
                temporaryPlan.setOwnerDept(temporaryPlan.getOwnerInfo().getTSDepart().getDepartname());
            }

            if (temporaryPlan.getOwnerInfo() != null) {
                temporaryPlan.setOwnerRealName(temporaryPlan.getOwnerInfo().getRealName() + "-"
                        + temporaryPlan.getOwnerInfo().getUserName());
            }
            if (temporaryPlan.getPlanLevelInfo() != null) {
                temporaryPlan.setPlanLevelName(temporaryPlan.getPlanLevelInfo().getName());
            }

            if ("true".equals(temporaryPlan.getMilestone())) {
                temporaryPlan.setMilestoneName("是");
            }
            else {
                temporaryPlan.setMilestoneName("否");
            }

            String preposePlans = "";
            if (StringUtils.isNotEmpty(temporaryPlan.getPreposeIds())) {
                for (String id : temporaryPlan.getPreposeIds().split(",")) {
                    PlanDto p = planRemoteFeignServiceI.getPlanEntity(id);
                    if (p != null) {
                        if (StringUtils.isNotEmpty(preposePlans)) {
                            preposePlans = preposePlans + "," + p.getPlanName();
                        }
                        else {
                            preposePlans = p.getPlanName();
                        }
                    }

                }
            }
            temporaryPlan.setPreposePlans(preposePlans);
            req.getSession().setAttribute("planchange-planId", temporaryPlan.getPlanId());
            req.getSession().setAttribute("planchange-uploadSuccessFileName",
                    temporaryPlan.getChangeInfoDocName());
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            temporaryPlan.setPlanStartTime(DateUtil.stringtoDate(
                    sdf1.format(temporaryPlan.getPlanStartTime()), "yyyy-MM-dd"));
            temporaryPlan.setPlanEndTime(DateUtil.stringtoDate(
                    sdf1.format(temporaryPlan.getPlanEndTime()), "yyyy-MM-dd"));
            if (!CommonUtil.isEmpty(plan)) {
                temporaryPlan.setAssigner(plan.getAssigner());
                temporaryPlan.setAssignerInfo(plan.getAssignerInfo());
                temporaryPlan.setAssignTime(plan.getAssignTime());
                temporaryPlan.setCreateBy(plan.getCreateBy());
                temporaryPlan.setCreateFullName(plan.getCreateFullName());
                temporaryPlan.setCreateName(plan.getCreateName());
                temporaryPlan.setCreateTime(plan.getCreateTime());

                if (!PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
                        && !PlanConstants.PLAN_INVALID.equals(plan.getBizCurrent())) {
                    if (ProjectStatusConstants.PAUSE.equals(plan.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.PAUSE_CHI);
                    }
                    else if (ProjectStatusConstants.CLOSE.equals(plan.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.CLOSE_CHI);
                    }
                    else {
                        // 获取计划的生命周期
                        FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                        String lifeStaStr = String.valueOf(fj.getObj());
                        List<LifeCycleStatus> statusList =  JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
                        for (LifeCycleStatus status : statusList) {
                            if (status.getName().equals(plan.getBizCurrent())) {
                                temporaryPlan.setStatus(status.getTitle());
                                break;
                            }
                        }
                    }
                }
                else {
                    // 获取计划的生命周期
                    FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                    String lifeStaStr = String.valueOf(fj.getObj());
                    List<LifeCycleStatus> statusList =  JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
                    for (LifeCycleStatus status : statusList) {
                        if (status.getName().equals(plan.getBizCurrent())) {
                            plan.setStatus(status.getTitle());
                            break;
                        }
                    }
                }
            }
        }

        req.getSession().setAttribute("planchange-formId", formId);
        if (StringUtils.isEmpty(temporaryPlan.getChangeInfoDocPath())) {
            temporaryPlan.setChangeInfoDocName("");
        }
        switch (tabIndex) { // tab索引
            case "1":
                mav = new ModelAndView("com/glaway/ids/project/plan/changeOneView-reason"); // 变更原因
                break;
            case "2":
                mav = new ModelAndView("com/glaway/ids/project/plan/changeOneView-info");// 详细信息
                break;
            case "3":
                mav = new ModelAndView("com/glaway/ids/project/plan/changeOneView-analysis"); // 关联分析
                String planStartTime = DateUtil.dateToString(temporaryPlan.getPlanStartTime(),
                        DateUtil.YYYY_MM_DD);
                String planEndTime = DateUtil.dateToString(temporaryPlan.getPlanEndTime(),
                        DateUtil.YYYY_MM_DD);
                String infoFlg = "nothing";
                String childInfluenceFlg = "false";
                String postposeInfluenceFlg = "false";
                String msg = "系统未检测到此次变更产生的影响";
                String msg_child = "子计划（时间需要收敛在" + planStartTime + "~" + planEndTime + "）";
                String msg_postpose = msg_postpose = "后置计划（开始时间不得早于" + planEndTime + "）";

                String parentPlanId = temporaryPlan.getPlanId();
                List<TemporaryPlanChild> childList = getChildList(parentPlanId, temporaryPlan);
                for (TemporaryPlanChild child : childList) {
                    if (child.isStartTimeOverflow()) {
                        // msg =
                        // I18nUtil.getValue("com.glaway.ids.project.plan.checkChildPostposeInfluencedFive");
                        infoFlg = "influence";
                        childInfluenceFlg = "true";
                        break;
                    }
                    if (child.isEndTimeOverflow()) {
                        // msg =
                        // I18nUtil.getValue("com.glaway.ids.project.plan.checkChildPostposeInfluencedFive");
                        infoFlg = "influence";
                        childInfluenceFlg = "true";
                        break;
                    }
                }

                String preposeIds = temporaryPlan.getPlanId();
                List<TemporaryPlanPostpose> postposeList = getTemporaryPlanPostposeList(
                        preposeIds, temporaryPlan);
                for (TemporaryPlanPostpose postpose : postposeList) {
                    if (postpose.isStartTimeOverflow()) {
                        // msg =
                        // I18nUtil.getValue("com.glaway.ids.project.plan.checkChildPostposeInfluencedFive");
                        infoFlg = "influence";
                        postposeInfluenceFlg = "true";
                        break;
                    }
                    if (postpose.isEndTimeOverflow()) {
                        // msg =
                        // I18nUtil.getValue("com.glaway.ids.project.plan.checkChildPostposeInfluencedFive");
                        infoFlg = "influence";
                        postposeInfluenceFlg = "true";
                        break;
                    }
                }
                mav.addObject("msg", msg);
                mav.addObject("infoFlg", infoFlg);
                mav.addObject("childInfluenceFlg", childInfluenceFlg);
                mav.addObject("postposeInfluenceFlg", postposeInfluenceFlg);
                mav.addObject("msg_child", msg_child);
                mav.addObject("msg_postpose", msg_postpose);
                mav.addObject("formId", formId);
                break;
            case "4":
                mav = new ModelAndView("com/glaway/ids/project/plan/changeOneView-total"); // 统计分析

                TempPlanInputsDto deliverablesInfo = new TempPlanInputsDto();
                deliverablesInfo.setUseObjectId(formId);
                deliverablesInfo.setFormId(null);
                List<TempPlanInputsDto> inputList = planRemoteFeignServiceI.queryInputChangeList(
                        deliverablesInfo, 1, 10, false);
                req.getSession().setAttribute("planChange-inputList", inputList);

                TempPlanDeliverablesInfoDto deliverablesInfo2 = new TempPlanDeliverablesInfoDto();
                deliverablesInfo2.setFormId(null);
                deliverablesInfo2.setUseObjectId(formId);
                List<TempPlanDeliverablesInfoDto> deliverablesInfoList = planRemoteFeignServiceI.queryDeliverableChangeList(
                        deliverablesInfo2, 1, 10, false);
                req.getSession().setAttribute("planChange-deliverablesInfoList",
                        deliverablesInfoList);

                TempPlanResourceLinkInfoDto resourceLinkInfo = new TempPlanResourceLinkInfoDto();
                resourceLinkInfo.setFormId(null);
                resourceLinkInfo.setUseObjectId(formId);
                List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = planRemoteFeignServiceI.queryResourceChangeList(
                        resourceLinkInfo, 1, 10, false);

                List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
                Map<String, String> resourcePathMap = new HashMap<String, String>();
                Map<String, String> resourceNameMap = new HashMap<String, String>();
                for (ResourceDto resource : resourceListTemp) {
                    resourcePathMap.put(resource.getId(), resource.getPath());
                    resourceNameMap.put(resource.getId(), resource.getName());
                }
                for (TempPlanResourceLinkInfoDto info : resourceLinkInfoList) {
                    if (StringUtil.isNotEmpty(info.getResourceId())) {
                        info.setResourceName(resourceNameMap.get(info.getResourceId()));
                        info.setResourceType(resourcePathMap.get(info.getResourceId()));
                    }
                }
                req.getSession().setAttribute("planChange-resourceLinkInfoList",
                        resourceLinkInfoList);
                mav.addObject("formId", formId);
                break;
            default:
                mav = new ModelAndView("com/glaway/ids/project/plan/changeOneView-reason"); // 变更原因
        }
        mav.addObject("temporaryPlan_", temporaryPlan);
        return mav;
    }


    /**
     * 变更对比
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "changeAnalysisTotal")
    public void changeAnalysisTotal(String formId, HttpServletRequest request,
                                    HttpServletResponse response) {
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto temporaryPlan = feignPlanChangeServiceI.getTemporaryPlan(
                approve.get(0).getPlanId());
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");
        List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                "planChange-inputList");
        PlanDto p = new PlanDto();
        if (temporaryPlan == null) {
            TemporaryPlanDto t = new TemporaryPlanDto();
            t.setPlanId(approve.get(0).getPlanId());
            t.setFormId(formId);
            List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
            temporaryPlan = list.get(0);
        }
        if (temporaryPlan != null) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanId())) {
                p = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
            }
            if (temporaryPlan.getOwnerInfo() != null) {
                temporaryPlan.setOwnerDept(temporaryPlan.getOwnerInfo().getTSDepart().getDepartname());
            }

            if (temporaryPlan.getOwnerInfo() != null) {
                temporaryPlan.setOwnerRealName(temporaryPlan.getOwnerInfo().getRealName() + "-"
                        + temporaryPlan.getOwnerInfo().getUserName());
            }
            if (temporaryPlan.getPlanLevelInfo() != null) {
                temporaryPlan.setPlanLevelName(temporaryPlan.getPlanLevelInfo().getName());
            }

            if ("true".equals(temporaryPlan.getMilestone())) {
                temporaryPlan.setMilestoneName("是");
            }
            else {
                temporaryPlan.setMilestoneName("否");
            }

            String preposePlans = "";
            if (StringUtils.isNotEmpty(temporaryPlan.getPreposeIds())) {
                for (String id : temporaryPlan.getPreposeIds().split(",")) {
                    PlanDto pl = planRemoteFeignServiceI.getPlanEntity(id);
                    if (pl != null) {
                        if (StringUtils.isNotEmpty(preposePlans)) {
                            preposePlans = preposePlans + "," + pl.getPlanName();
                        }
                        else {
                            preposePlans = pl.getPlanName();
                        }
                    }
                }
            }
            temporaryPlan.setPreposePlans(preposePlans);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            temporaryPlan.setPlanStartTime(DateUtil.stringtoDate(
                    sdf1.format(temporaryPlan.getPlanStartTime()), "yyyy-MM-dd"));
            temporaryPlan.setPlanEndTime(DateUtil.stringtoDate(
                    sdf1.format(temporaryPlan.getPlanEndTime()), "yyyy-MM-dd"));
            if (!CommonUtil.isEmpty(p)) {
                temporaryPlan.setAssigner(p.getAssigner());
                temporaryPlan.setAssignerInfo(p.getAssignerInfo());
                temporaryPlan.setAssignTime(p.getAssignTime());
                temporaryPlan.setCreateBy(p.getCreateBy());
                temporaryPlan.setCreateFullName(p.getCreateFullName());
                temporaryPlan.setCreateName(p.getCreateName());
                temporaryPlan.setCreateTime(p.getCreateTime());

                if (!PlanConstants.PLAN_FINISH.equals(p.getBizCurrent())
                        && !PlanConstants.PLAN_INVALID.equals(p.getBizCurrent())) {
                    if (ProjectStatusConstants.PAUSE.equals(p.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.PAUSE_CHI);
                    }
                    else if (ProjectStatusConstants.CLOSE.equals(p.getProjectStatus())) {
                        temporaryPlan.setStatus(ProjectStatusConstants.CLOSE_CHI);
                    }
                    else {
                        // 获取计划的生命周期
                        FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                        String lifeCycStr = String.valueOf(fj.getObj());
                        List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);
                        for (LifeCycleStatus status : statusList) {
                            if (status.getName().equals(p.getBizCurrent())) {
                                temporaryPlan.setStatus(status.getTitle());
                                break;
                            }
                        }
                    }
                }
                else {
                    // 获取计划的生命周期
                    FeignJson fj = planRemoteFeignServiceI.getLifeCycleStatusList();
                    String lifeCycStr = String.valueOf(fj.getObj());
                    List<LifeCycleStatus> statusList = JSON.parseArray(lifeCycStr,LifeCycleStatus.class);
                    for (LifeCycleStatus status : statusList) {
                        if (status.getName().equals(p.getBizCurrent())) {
                            p.setStatus(status.getTitle());
                            break;
                        }
                    }
                }
            }
        }

        if (StringUtils.isEmpty(temporaryPlan.getChangeInfoDocPath())) {
            temporaryPlan.setChangeInfoDocName("");
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();

        // List<TempPlanInputs> inputList = new ArrayList<TempPlanInputs>();
        PlanDto plan = planRemoteFeignServiceI.getPlanEntity(temporaryPlan.getPlanId());
        InputsDto inputs = new InputsDto();
        inputs.setUseObjectId(plan.getId());
        inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
//        List<Inputs> inList = inputsService.queryInputsDetailList(inputs);
        List<InputsDto> inList = inputsRemoteFeignServiceI.queryNewInputsList(inputs);


        // List<TempPlanDeliverablesInfo> deliverablesInfoList = new
        // ArrayList<TempPlanDeliverablesInfo>();
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);

        FeignJson fJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(fJson.getObj());

        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
        ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
        resourceLinkInfo.setUseObjectId(plan.getId());
        resourceLinkInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);

        List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                resourceLinkInfo, 1, 10, false);

        if (StringUtils.isNotEmpty(plan.getOwner())
                && StringUtils.isNotEmpty(temporaryPlan.getOwner())) {
            if (!plan.getOwner().equals(temporaryPlan.getOwner())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("负责人");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getOwnerInfo().getRealName() + "-"
                        + plan.getOwnerInfo().getUserName());
                planChangeVo.setChangeAfter(temporaryPlan.getOwnerRealName());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (StringUtils.isNotEmpty(plan.getPlanLevel()) && !"===".equals(plan.getPlanLevel())) {
            BusinessConfig b = businessConfigService.getBusinessConfig(plan.getPlanLevel());
            if (StringUtils.isEmpty(temporaryPlan.getPlanLevel())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("计划等级");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(b.getName());
                planChangeVo.setChangeAfter("");
                planChangeVoList.add(planChangeVo);
            }
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel()) && !"===".equals(temporaryPlan.getPlanLevel())) {
                if (!plan.getPlanLevel().equals(temporaryPlan.getPlanLevel())) {
                    BusinessConfig bc = businessConfigService.getBusinessConfig(temporaryPlan.getPlanLevel());
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("计划等级");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(b.getName());
                    planChangeVo.setChangeAfter(bc.getName());
                    planChangeVoList.add(planChangeVo);
                }
            }
        }

        if (StringUtils.isEmpty(plan.getPlanLevel())) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("计划等级");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore("-");
                BusinessConfig b = businessConfigService.getBusinessConfig(temporaryPlan.getPlanLevel());
                planChangeVo.setChangeAfter(b.getName());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (!CommonUtil.isEmpty(plan.getOwnerInfo()) && StringUtils.isNotEmpty(plan.getOwnerInfo().getTSDepart().getDepartname())
                && StringUtils.isNotEmpty(temporaryPlan.getOwnerDept())) {
            if (!plan.getOwnerInfo().getTSDepart().getDepartname().equals(
                    temporaryPlan.getOwnerDept())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("责任部门");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getOwnerInfo().getTSDepart().getDepartname());
                planChangeVo.setChangeAfter(temporaryPlan.getOwnerDept());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getPlanStartTime() != null && temporaryPlan.getPlanStartTime() != null) {
            if (!plan.getPlanStartTime().equals(temporaryPlan.getPlanStartTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("开始时间");
                planChangeVo.setType("修改");
                String planStartTimeBefore = DateUtil.dateToString(plan.getPlanStartTime(),"yyyy-MM-dd");
                planChangeVo.setChangeBefore(planStartTimeBefore);
                planChangeVo.setChangeAfter(DateUtil.dateToString(
                        temporaryPlan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getPlanEndTime() != null && temporaryPlan.getPlanEndTime() != null) {
            if (!plan.getPlanEndTime().equals(temporaryPlan.getPlanEndTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("结束时间");
                planChangeVo.setType("修改");
                String planEndTimeBefore = DateUtil.dateToString(plan.getPlanEndTime(),"yyyy-MM-dd");
                planChangeVo.setChangeBefore(planEndTimeBefore);
                planChangeVo.setChangeAfter(DateUtil.dateToString(temporaryPlan.getPlanEndTime(),
                        DateUtil.YYYY_MM_DD));
                planChangeVoList.add(planChangeVo);
            }
        }

        if (plan.getWorkTime() != null && temporaryPlan.getWorkTime() != null) {
            if (!plan.getWorkTime().equals(temporaryPlan.getWorkTime())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("工期");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore(plan.getWorkTime());
                planChangeVo.setChangeAfter(temporaryPlan.getWorkTime());
                planChangeVoList.add(planChangeVo);
            }
        }

        if (StringUtils.isNotEmpty(plan.getMilestone())
                && StringUtils.isNotEmpty(temporaryPlan.getMilestone())) {
            if (!plan.getMilestone().equals(temporaryPlan.getMilestone())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("里程碑");
                planChangeVo.setType("修改");
                if ("true".equals(plan.getMilestone())) {
                    planChangeVo.setChangeBefore("是");
                }
                else {
                    planChangeVo.setChangeBefore("否");
                }
                if ("true".equals(temporaryPlan.getMilestone())) {
                    planChangeVo.setChangeAfter("是");
                }
                else {
                    planChangeVo.setChangeAfter("否");
                }
                planChangeVoList.add(planChangeVo);
            }
        }

        List<PreposePlanDto> preposePlanList = preposePlanRemoteFeignServiceI.getPreposePlansByPlanId(plan);
        List<PreposePlanDto> TempPreposePlanList = new ArrayList<PreposePlanDto>();
        if (preposePlanList != null && preposePlanList.size() > 0) {
            Boolean a = false;
            for (PreposePlanDto preposePlan : preposePlanList) {
                if (StringUtils.isNotEmpty(temporaryPlan.getPreposePlans())) {
                    if (temporaryPlan.getPreposePlans().contains(
                            preposePlan.getPreposePlanInfo().getPlanName())) {

                    }
                    else {
                        a = true;
                    }
                }
            }
            if (a) {
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
                if (StringUtils.isEmpty(temporaryPlan.getPreposeIds())
                        && StringUtils.isEmpty(temporaryPlan.getPreposePlans())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("前置计划");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(preposeSb.toString());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
                else {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("前置计划");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(preposeSb.toString());
                    planChangeVo.setChangeAfter(temporaryPlan.getPreposePlans());
                    planChangeVoList.add(planChangeVo);
                }
            }

        }

        if (preposePlanList == null || preposePlanList.size() == 0) {
            if (StringUtils.isNotEmpty(temporaryPlan.getPreposeIds())) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("前置计划");
                planChangeVo.setType("修改");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(temporaryPlan.getPreposePlans());
                planChangeVoList.add(planChangeVo);
            }
        }

        // 输入对比
        for (InputsDto d : inList) {
            Boolean a = false;
            for (TempPlanInputsDto dev : inputList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输入)" + d.getName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }


        for (InputsDto i : inList) {
            TempPlanInputsDto tempPlanInputs = new TempPlanInputsDto();
            tempPlanInputs.setInputId(i.getId());
            tempPlanInputs.setId(i.getId());
            tempPlanInputs.setName(i.getName());
            tempPlanInputs.setUseObjectId(i.getUseObjectId());
            tempPlanInputs.setUseObjectType(i.getUseObjectType());
            tempPlanInputs.setFileId(i.getFileId());
            tempPlanInputs.setOrigin(i.getOrigin());
            tempPlanInputs.setDocName(i.getDocName());
            tempPlanInputs.setDocId(i.getDocId());
            tempPlanInputs.setRequired(i.getRequired());
            tempPlanInputs.setDocId(i.getDocId());
            tempPlanInputs.setDocName(i.getDocName());
            tempPlanInputs.setOriginObjectId(i.getOriginObjectId());
            tempPlanInputs.setOriginDeliverablesInfoId(i.getOriginDeliverablesInfoId());
            tempPlanInputs.setChecked(i.getChecked());
            tempPlanInputs.setDocument(i.getDocument());
            inputList.add(tempPlanInputs);
        }


        if (!CommonUtil.isEmpty(inputList)) {
            for (TempPlanInputsDto dev : inputList) {
                Boolean a = false;
                for (InputsDto d : inList) {
                    if (d.getName().equals(dev.getName())) {
                        a = true;
                        break;
                    }
                }
                if (!a) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setField("(输入)" + dev.getName());
                    planChangeVo.setType("新增");
                    planChangeVo.setChangeBefore("-");
                    planChangeVo.setChangeAfter(dev.getName());
                    planChangeVoList.add(planChangeVo);
                }
            }
        }

        // 输出对比
        for (DeliverablesInfoDto d : deliverablesList) {
            Boolean a = false;
            for (TempPlanDeliverablesInfoDto dev : deliverablesInfoList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输出)" + d.getName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }

        for (TempPlanDeliverablesInfoDto dev : deliverablesInfoList) {
            Boolean a = false;
            for (DeliverablesInfoDto d : deliverablesList) {
                if (d.getName().equals(dev.getName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(输出)" + dev.getName());
                planChangeVo.setType("新增");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(dev.getName());
                planChangeVoList.add(planChangeVo);
            }
        }

        // 资源对比
        for (ResourceLinkInfoDto d : resourceLinkList) {
            Boolean a = false;
            for (TempPlanResourceLinkInfoDto dev : resourceLinkInfoList) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                if (dev.getResourceInfo() != null) {
                    dev.setResourceName(dev.getResourceInfo().getName());
                }
                if (d.getResourceName().equals(dev.getResourceName())) {
                    a = true;
                    if (!d.getUseRate().equals(dev.getUseRate())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(使用百分比)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getUseRate());
                        planChangeVo.setChangeAfter(dev.getUseRate());
                        planChangeVoList.add(planChangeVo);
                    }
                    if (!d.getStartTime().equals(dev.getStartTime())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(开始时间)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getStartTime().toString());
                        String time = DateUtil.getStringFromDate(dev.getStartTime(),
                                DateUtil.YYYY_MM_DD);
                        planChangeVo.setChangeAfter(time);
                        planChangeVoList.add(planChangeVo);
                    }
                    if (!d.getEndTime().equals(dev.getEndTime())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("(资源)" + d.getResourceName() + "(结束时间)");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore(d.getEndTime().toString());
                        String time = DateUtil.getStringFromDate(dev.getEndTime(),
                                DateUtil.YYYY_MM_DD);
                        planChangeVo.setChangeAfter(time);
                        planChangeVoList.add(planChangeVo);
                    }
                    break;
                }
            }
            if (!a) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(资源)" + d.getResourceName());
                planChangeVo.setType("删除");
                planChangeVo.setChangeBefore(d.getResourceName());
                planChangeVo.setChangeAfter("-");
                planChangeVoList.add(planChangeVo);
            }
        }

        for (TempPlanResourceLinkInfoDto dev : resourceLinkInfoList) {
            Boolean a = false;
            for (ResourceLinkInfoDto d : resourceLinkList) {
                if (d.getResourceInfo() != null) {
                    d.setResourceName(d.getResourceInfo().getName());
                }
                if (dev.getResourceInfo() != null) {
                    dev.setResourceName(dev.getResourceInfo().getName());
                }
                if (d.getResourceName().equals(dev.getResourceName())) {
                    a = true;
                    break;
                }
            }
            if (!a) {
                PlanChangeVo planChangeVo = new PlanChangeVo();
                planChangeVo.setField("(资源)" + dev.getResourceName());
                planChangeVo.setType("新增");
                planChangeVo.setChangeBefore("-");
                planChangeVo.setChangeAfter(dev.getResourceName());
                planChangeVoList.add(planChangeVo);
            }
        }
        DataGridReturn data = new DataGridReturn(planChangeVoList.size(), planChangeVoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);

    }

    /**
     * 输入查看列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "inputListView")
    public void inputListView(TempPlanInputsDto deliverablesInfo, HttpServletRequest request,
                              HttpServletResponse response) {
        String formId = (String)request.getSession().getAttribute("planchange-formId");
        deliverablesInfo.setUseObjectId(formId);
        deliverablesInfo.setFormId(null);
        List<TempPlanInputsDto> inputList = planRemoteFeignServiceI.queryInputChangeList(
                deliverablesInfo, 1, 10, false);
        /*List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                "planChange-inputList");*/

        String planId = request.getParameter("planId");
        if(CommonUtil.isEmpty(planId)){
            planId = "";
        }
        PlanDto p = planRemoteFeignServiceI.getPlanEntity(planId);

        String libId = "";
        if(!CommonUtil.isEmpty(p)){
            String projectId = p.getProjectId();
            FeignJson fj = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        }else{
            String projectId = request.getParameter("projectId");
            FeignJson fj  = projRoleRemoteFeignServiceI.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        }


        Map<String, String> fileNameMap = new HashMap<String, String>();

        Map<String, String> filePathMap = new HashMap<String, String>();

        Map<String, String> fileIdMap = new HashMap<String, String>();

        if(!CommonUtil.isEmpty(libId)){
            fileNameMap = inputsRemoteFeignServiceI.getRepFileNameAndBizIdMap(libId);

            filePathMap = inputsRemoteFeignServiceI.getRepFilePathAndBizIdMap(libId);

            fileIdMap = inputsRemoteFeignServiceI.getRepFileIdAndBizIdMap(libId);
        }

        if(!CommonUtil.isEmpty(inputList)){
            for(TempPlanInputsDto input : inputList){
                if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("LOCAL")){
                    input.setDocNameShow(input.getDocName());
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PROJECTLIBDOC")){
//                    RepFile rep = inputsService.getEntity(RepFile.class, input.getDocId());
                    /*List<RepFile> repList = inputsService.getRepFileByBizId(input.getDocId());
                    RepFile rep = new RepFile();
                    if(!CommonUtil.isEmpty(repList)){
                        rep = repList.get(0);

                    }
                    String realPath = "";
                    if(!CommonUtil.isEmpty(rep)){
                        input.setDocNameShow(rep.getFileName());
                        String path = projLibService.getDocNamePath(rep.getId());
                        String[] temp = path.split("/");
                        for (int i = 1; i < temp.length - 1; i++ ) {
                            realPath += "/" + temp[i];
                        }
                    }else{
                        input.setDocNameShow("");
                    }
                    input.setOriginPath(realPath);*/
                    if(!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))){
                        input.setDocNameShow(fileNameMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))){
                        input.setOriginPath(filePathMap.get(input.getDocId()));
                    }
                    if(!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))){
                        input.setDocIdShow(fileIdMap.get(input.getDocId()));
                    }
                }else if(!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PLAN")){
                    PlanDto plan = planRemoteFeignServiceI.getPlanEntity(input.getOriginObjectId());
                    List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                    if(!CommonUtil.isEmpty(plan)){
                        projDocRelationList = inputsRemoteFeignServiceI.getDocRelationList(plan, UserUtil.getInstance().getUser().getId());
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
                    input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                    input.setDocId(projDoc.getDocId());
                    input.setDocIdShow(projDoc.getDocId());
                    input.setDocNameShow(projDoc.getDocName());
                    input.setExt1(String.valueOf(projDoc.isDownload()));
                    input.setExt2(String.valueOf(projDoc.isHavePower()));
                    input.setExt3(String.valueOf(projDoc.isDetail()));
                }
            }
        }

        request.getSession().setAttribute("planChange-inputList", inputList);
        long count = inputList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 交付物页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "documentListView")
    public void documentListView(TempPlanDeliverablesInfoDto deliverablesInfo, String planName,
                                 HttpServletRequest request, HttpServletResponse response) {
        String formId = (String)request.getSession().getAttribute("planchange-formId");
        deliverablesInfo.setFormId(null);
        deliverablesInfo.setUseObjectId(formId);
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = planRemoteFeignServiceI.queryDeliverableChangeList(
                deliverablesInfo, 1, 10, false);
        /*List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");*/
        if(!CommonUtil.isEmpty(deliverablesInfoList)){
            for(TempPlanDeliverablesInfoDto dto : deliverablesInfoList){
                dto.setExt1(dto.getName());
            }
        }
        deliverablesInfo.setUseObjectId(formId);
        request.getSession().setAttribute("planChange-deliverablesInfoList", deliverablesInfoList);
        long count = deliverablesInfoList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(deliverablesInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 交付物页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "documentListViewForPlanChange")
    public void documentListViewForPlanChange(TempPlanDeliverablesInfoDto deliverablesInfo, String planName,
                                 HttpServletRequest request, HttpServletResponse response) {
        String formId = (String)request.getSession().getAttribute("planchange-formId");
        deliverablesInfo.setFormId(null);
        deliverablesInfo.setUseObjectId(formId);
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = planRemoteFeignServiceI.queryDeliverableChangeList(
                deliverablesInfo, 1, 10, false);
        /*List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");*/
        if(!CommonUtil.isEmpty(deliverablesInfoList)){
            for(TempPlanDeliverablesInfoDto dto : deliverablesInfoList){
                dto.setExt1(dto.getName());
            }
        }
        deliverablesInfo.setUseObjectId(formId);
        request.getSession().setAttribute("planChange-deliverablesInfoList", deliverablesInfoList);

        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));

        List<TempPlanDeliverablesInfoDto> resList = new ArrayList<TempPlanDeliverablesInfoDto>();
        long count = deliverablesInfoList.size();
        if (count > page * rows) {
            resList = deliverablesInfoList.subList((page - 1) * rows, page * rows);
        }
        else {
            resList = deliverablesInfoList.subList((page - 1) * rows, deliverablesInfoList.size());
        }


        String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    /**
     * 资源页面在流程中初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceListView")
    public void resourceListView(TempPlanResourceLinkInfoDto resourceLinkInfo,
                                 HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        String formId = (String)request.getSession().getAttribute("planchange-formId");
        resourceLinkInfo.setFormId(null);
        resourceLinkInfo.setUseObjectId(formId);
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = planRemoteFeignServiceI.queryResourceChangeList(
                resourceLinkInfo, 1, 10, false);
       /* List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChange-resourceLinkInfoList");*/
        List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
        Map<String, String> resourcePathMap = new HashMap<String, String>();
        Map<String, String> resourceNameMap = new HashMap<String, String>();
        for (ResourceDto resource : resourceListTemp) {
            resourcePathMap.put(resource.getId(), resource.getPath());
            resourceNameMap.put(resource.getId(), resource.getName());
        }
        for (TempPlanResourceLinkInfoDto info : resourceLinkInfoList) {
            if (StringUtil.isNotEmpty(info.getResourceId())) {
                info.setResourceName(resourceNameMap.get(info.getResourceId()));
                info.setResourceType(resourcePathMap.get(info.getResourceId()));
            }
        }
        request.getSession().setAttribute("planChange-resourceLinkInfoList", resourceLinkInfoList);
        DataGridReturn data = new DataGridReturn(resourceLinkInfoList.size(), resourceLinkInfoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);

    }

    /**
     * 驳回到首节点再次提交工作流
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startPlanChangeFlow")
    @ResponseBody
    public AjaxJson startPlanChangeFlow(String ids, String oldAttachmentPath,
                                        HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.startPlanChangeFlowSuccess");
        try {
            String formId = (String)req.getSession().getAttribute("planchange-formId");
            String taskId = (String)req.getSession().getAttribute("planchange-taskIdSave");
            String temporaryId = (String)req.getSession().getAttribute("planchange-temporaryId");
            String leader = req.getParameter("leader");
            String deptLeader = req.getParameter("deptLeader");

            List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)req.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)req.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)req.getSession().getAttribute(
                    "planChange-inputList");
            FeignJson feignJson = new FeignJson();
            Map<String, Object> map = new HashMap<>();
            map.put("formId",formId);
            map.put("taskId",taskId);
            map.put("temporaryId",temporaryId);
            map.put("leader",leader);
            map.put("deptLeader",deptLeader);
            map.put("deliverablesInfoList",deliverablesInfoList);
            map.put("resourceLinkInfoList",resourceLinkInfoList);
            map.put("inputList",inputList);
            map.put("userId",UserUtil.getCurrentUser().getId());
            feignJson.setAttributes(map);
            planFlowForworkFeignServiceI.startPlanChangeFlowForWork(feignJson);
            if (StringUtil.isNotEmpty(oldAttachmentPath)) {
                JackrabbitUtil.deleteFile(oldAttachmentPath);
            }
            log.info(message, formId, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.startPlanChangeFlowFail");
            log.error(message, e, "", "");
//            Object[] params = new Object[] {message, ApprovePlanForm.class.getClass() + " oids:"};// 异常原因：{0}；异常描述：{1}
//            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddTempInherit")
    public ModelAndView goAddTempInherit(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        String planIdForInherit = req.getParameter("planId");
        String parentPlanIdForInherit = req.getParameter("parentPlanId");
        req.getSession().setAttribute("planIdForInherit", planIdForInherit);
        req.getSession().setAttribute("parentPlanIdForInherit", parentPlanIdForInherit);
        return new ModelAndView("com/glaway/ids/project/plan/planInheritDocumentTemp-add");
    }


    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridInheritlist")
    @ResponseBody
    public void datagridInheritlist(HttpServletRequest request, HttpServletResponse response) {
        String planIdForInherit = (String)request.getSession().getAttribute("planIdForInherit");
        String parentPlanIdForInherit = (String)request.getSession().getAttribute(
                "parentPlanIdForInherit");
        PlanDto plan = planRemoteFeignServiceI.getPlanEntity(planIdForInherit);
        if (plan == null) {
            plan = new PlanDto();
            plan.setId(planIdForInherit);
            plan.setParentPlanId(parentPlanIdForInherit);
        }
        List<TempPlanDeliverablesInfoDto> deliverableslist = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                "planChange-deliverablesInfoList");
        List<DeliverablesInfoDto> deliverablesParentlist = deliverablesInfoService.getDeliverablesByUseObeject(
                PlanConstants.USEOBJECT_TYPE_PLAN, plan.getParentPlanId());
        List<DeliverablesInfoDto> list = new ArrayList<DeliverablesInfoDto>();

        PlanDto parentPlan = planRemoteFeignServiceI.getPlanEntity(plan.getParentPlanId());
        List<PlanDto> planList = planRemoteFeignServiceI.getPlanAllChildren(parentPlan);

        if (CommonUtil.isEmpty(deliverableslist)) {
            list.addAll(deliverablesParentlist);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (TempPlanDeliverablesInfoDto deliver : deliverableslist) {
                nameMap.put(deliver.getName(), deliver.getName());
            }
            for (DeliverablesInfoDto deliveryStandard : deliverablesParentlist) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    list.add(deliveryStandard);
                }
            }
        }

        List<DeliverablesInfoDto> list2 = new ArrayList<DeliverablesInfoDto>();
        for (PlanDto p : planList) {
            if (!p.getId().equals(plan.getId()) && !p.getId().equals(plan.getParentPlanId())) {
                List<DeliverablesInfoDto> deliverableslistTemp = deliverablesInfoService.getDeliverablesByUseObeject(
                        PlanConstants.USEOBJECT_TYPE_PLAN, p.getId());
                for (int i = 0; i < list.size(); i++ ) {
                    for (int j = 0; j < deliverableslistTemp.size(); j++ ) {
                        if (list.get(i).getName().equals(deliverableslistTemp.get(j).getName())) {
                            list2.add(deliverableslistTemp.get(j));
                        }
                    }
                }
            }
        }

        List<DeliverablesInfoDto> list3 = new ArrayList<DeliverablesInfoDto>();
        if (CommonUtil.isEmpty(list2)) {
            list3.addAll(list);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (DeliverablesInfoDto deliver : list2) {
                nameMap.put(deliver.getName(), deliver.getName());
            }
            for (DeliverablesInfoDto deliveryStandard : list) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    list3.add(deliveryStandard);
                }
            }
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(list3);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + list3.size() + "}";

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
     * 新增继承交付物
     *
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddTempInheritlist")
    @ResponseBody
    public AjaxJson doAddTempInheritlist(String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planIdForInherit = (String)request.getSession().getAttribute("planIdForInherit");
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            List<TempPlanDeliverablesInfoDto> deliverableslist = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            for (String name : names.split(",")) {
                TempPlanDeliverablesInfoDto deliverablesInfoTemp = new TempPlanDeliverablesInfoDto();
                // deliverablesInfoService.initBusinessObject(deliverablesInfoTemp);
                deliverablesInfoTemp.setId(null);
                deliverablesInfoTemp.setName(name);
                deliverablesInfoTemp.setUseObjectId(planIdForInherit);
                deliverablesInfoTemp.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                deliverableslist.add(deliverablesInfoTemp);
            }
            request.getSession().setAttribute("planChange-deliverablesInfoList", deliverableslist);
            // planFlowForworkService.doAddInheritlist(names, planIdForInherit);
            log.info(message, planIdForInherit, planIdForInherit.toString());
        }
        catch (Exception e) {
            log.error(failMessage, e, "", planIdForInherit.toString());
            Object[] params = new Object[] {failMessage, planIdForInherit.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelDocumentEdit")
    @ResponseBody
    public AjaxJson doDelDocumentEdit(String ids, String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelSuccess");
        try {
            List<TempPlanDeliverablesInfoDto> list = new ArrayList<TempPlanDeliverablesInfoDto>();
            List<TempPlanDeliverablesInfoDto> deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>)request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            for (int i = 0; i < deliverablesInfoList.size(); i++ ) {
                if (ids.contains(deliverablesInfoList.get(i).getId())) {}
                else {
                    list.add(deliverablesInfoList.get(i));
                }
            }
            deliverablesInfoList.clear();
            deliverablesInfoList.addAll(list);
            request.getSession().setAttribute("planChange-deliverablesInfoList",
                    deliverablesInfoList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelFail");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                    DeliverablesInfoDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelResourceEdit")
    @ResponseBody
    public AjaxJson doDelResourceEdit(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteResourceSuccess");
        try {
            List<TempPlanResourceLinkInfoDto> list = new ArrayList<TempPlanResourceLinkInfoDto>();
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChange-resourceLinkInfoList");
            for (int i = 0; i < resourceLinkInfoList.size(); i++ ) {
                if (ids.contains(resourceLinkInfoList.get(i).getResourceId())) {}
                else {
                    list.add(resourceLinkInfoList.get(i));
                }
            }
            resourceLinkInfoList.clear();
            resourceLinkInfoList.addAll(list);
            request.getSession().setAttribute("planChange-resourceLinkInfoList",
                    resourceLinkInfoList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteResourceFail");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                    TempPlanResourceLinkInfoDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 计划变更提交前校验来源是否为空
     *
     * @author zhousuxia
     * @version 2018年5月21日
     * @see PlanController
     * @since
     */
    @RequestMapping(params = "checkOriginIsNullBeforeSub")
    @ResponseBody
    public FeignJson checkOriginIsNullBeforeSub(HttpServletRequest request) {
        FeignJson j = new FeignJson();
        j.setSuccess(true);
        List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                "planChange-inputList");
        if(!CommonUtil.isEmpty(inputList)){
            for(TempPlanInputsDto input : inputList){
                if(CommonUtil.isEmpty(input.getOriginType())){
                   j.setSuccess(false);
                   break;
                }
            }
        }

        return j;
    }


    /**
     * 计划变更-子计划
     *
     * @param formId
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "childListForView")
    public void childListForView(String formId, HttpServletRequest request,
                                 HttpServletResponse response) {
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto temporaryPlan = feignPlanChangeServiceI.getTemporaryPlan(approve.get(0).getPlanId());
        if (temporaryPlan == null) {
            TemporaryPlanDto t = new TemporaryPlanDto();
            t.setPlanId(approve.get(0).getPlanId());
            t.setFormId(formId);
            List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
            temporaryPlan = list.get(0);
        }
        String parentPlanId = (String)request.getSession().getAttribute("planchange-planId");
        List<TemporaryPlanChild> temporaryPlanChildList = getChildList(parentPlanId, temporaryPlan);
        long count = temporaryPlanChildList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanChildList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }


    /**
     * 计划变更-后置
     *
     * @param formId
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "postposeListForView")
    public void postposeListForView(String formId, HttpServletRequest request,
                                    HttpServletResponse response) {
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planRemoteFeignServiceI.queryAssignList(approvePlanInfo, 1, 10, false);
        TemporaryPlanDto temporaryPlan = feignPlanChangeServiceI.getTemporaryPlan(approve.get(0).getPlanId());
        if (temporaryPlan == null) {
            TemporaryPlanDto t = new TemporaryPlanDto();
            t.setPlanId(approve.get(0).getPlanId());
            t.setFormId(formId);
            List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
            temporaryPlan = list.get(0);
        }
        String preposeIds = (String)request.getSession().getAttribute("planchange-planId");
        List<TemporaryPlanPostpose> temporaryPlanPostposeList = getTemporaryPlanPostposeList(
                preposeIds, temporaryPlan);
        long count = temporaryPlanPostposeList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanPostposeList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

}
