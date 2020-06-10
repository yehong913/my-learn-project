package com.glaway.ids.project.projecttemplate.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.SwitchConstants;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;


/**
 * @Title: Controller
 * @Description: 项目模板详细
 * @author onlineGenerator
 * @date 2015-03-27 16:54:10
 * @version V1.0
 */
@Controller
@RequestMapping("/projTemplateDetailController")
public class ProjTemplateDetailController extends BaseController {

    
    /**
     *  sessionFacade
     */
    @Autowired
    private SessionFacade sessionFacade;


    /**
     *  配置业务接口
     */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;

    @Autowired
    private ProjTemplateDetailRemoteFeignServiceI projTemplateDetailService;

    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

    /**
     * redisService
     */
    @Autowired
    private RedisService redisService;

    @Autowired
    private ProjTemplateRemoteFeignServiceI projTemplateService;

    @Autowired
    private FeignSystemService feignSystemService;
    
    /**
     *  message
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getPlanList")
    public void getPlanList(PlanTemplateDetailDto planTemplateDetail, HttpServletRequest request,
                            HttpServletResponse response) {
        String projTemplateId = request.getParameter("id");
        String viewHistory = request.getParameter("viewHistory");
        ProjTemplateDto projTemplate = projTemplateService.getProjTemplateEntity(projTemplateId);
        ProjTemplateDto projTemplate2 = projTemplateService.getProjTemplateByBizId(projTemplate.getBizId());
        List<PlanDto> templateDetailList = new ArrayList<PlanDto>();
        if(StringUtil.equals(viewHistory, "viewHistory")){
            String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projTemplateId);
            if(!CommonUtil.isEmpty(detailStr)){
                templateDetailList = JSON.parseArray(detailStr,PlanDto.class);
            }
            if(CommonUtil.isEmpty(templateDetailList)) {
                templateDetailList = projTemplateDetailService.convertPlanjTemplateDetail2Plan(projTemplateId);
                Collections.sort(templateDetailList, new Comparator<PlanDto>() {
                    @Override
                    public int compare(PlanDto o1, PlanDto o2) {
                        return o1.getStoreyNo() - o2.getStoreyNo();
                    }});
                String delStr = JSON.toJSONString(templateDetailList);
                redisService.setToRedis("PROJTMPPLANLIST", projTemplateId, delStr);
            }
        }else{

             String projStr= (String)redisService.getFromRedis("PROJTMPPLANLIST", projTemplate2.getId());
             if(!CommonUtil.isEmpty(projStr)){
                 templateDetailList = JSON.parseArray(projStr,PlanDto.class);
                 Collections.sort(templateDetailList, new Comparator<PlanDto>() {
                     @Override
                     public int compare(PlanDto o1, PlanDto o2) {
                         return o1.getStoreyNo() - o2.getStoreyNo();
                     }});
             }
            if(CommonUtil.isEmpty(templateDetailList)) {
                templateDetailList = projTemplateDetailService.convertPlanjTemplateDetail2Plan(projTemplate2.getId());
                Collections.sort(templateDetailList, new Comparator<PlanDto>() {
                    @Override
                    public int compare(PlanDto o1, PlanDto o2) {
                        return o1.getStoreyNo().compareTo(o2.getStoreyNo());
                    }});
                String temStr = JSON.toJSONString(templateDetailList);
                redisService.setToRedis("PROJTMPPLANLIST", projTemplate2.getId(), temStr);
            }
        }
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        // 获得计划模板后，组装treeGrid
        try {
            if(StringUtils.isNotBlank(projTemplateId)){
                rootList = changePlansToJSONObjects(templateDetailList);
                String jsonStr = JSON.toJSONString(rootList);
                TagUtil.ajaxResponse(response, jsonStr);
            }
        }
        catch (Exception e) {
            message = "项目模板详细查询失败";
        }
    }


    /**
     * 将计划list组装为树节点json
     *
     * @param planList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjects(List<PlanDto> planList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        Map<String, String> planLevelMap =  getPlanLevelMap();
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
        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);
        FeignJson feignJson = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
        String warningDay = String.valueOf(feignJson.getObj());

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
                root.put("optBtn", this.generateOptBtn1(p));
                root.put("displayName", p.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(p.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", p.getPlanName());
                    treeNode.put("image", "folder.gif");
                    root.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.planNameFormat1(p));
                    treeNode1.put("image", "folder.gif");
                    root.put("planName", treeNode1);
                }
                else {
                    root.put("displayNameNode", p.getPlanName());
                    root.put("planName", this.planNameFormat1(p));
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
                    for(String preposeId : p.getPreposeIds().split(",")){
                        if(CommonUtil.isEmpty(preposePlans)){
                            preposePlans = planMap.get(preposeId);
                        }else{
                            preposePlans = preposePlans+","+planMap.get(preposeId);
                        }
                    }
                }
                root.put("preposePlans", preposePlans);

                String inputs = "";
                if(!CommonUtil.isEmpty(p.getInputList())){
                    List<InputsDto> inputList = p.getInputList();
                    for(InputsDto input : inputList){
                        if(CommonUtil.isEmpty(inputs)){
                            if(!StringUtil.isEmpty(input.getName())){
                                inputs = input.getName();
                            }
                        }else{
                            if(!StringUtil.isEmpty(input.getName())){
                                inputs = inputs+","+input.getName();
                            }
                        }

                    }
                }
                root.put("inputs", inputs);




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

                List<DeliverablesInfoDto> deliverablesInfos = p.getDeliInfoList();

                String delivaryName = "";
                for(DeliverablesInfoDto d :deliverablesInfos){
                    if(!CommonUtil.isEmpty(d) && !CommonUtil.isEmpty(d.getName())){
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
    //    List<BusinessConfig> planLevelConfigs = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
        for (BusinessConfig confog : planLevelConfigs) {
            planLevelMap.put(confog.getId(),confog.getName() );
        }
        return planLevelMap;
    }


    /**
     * 构造计划树操作栏操作按钮
     *
     * @param detail
     * @return
     * @see
     */
    private String generateOptBtn1(PlanDto detail) {

        String returnStr = "";
        String modifyBtnStr = "<a class='basis ui-icon-pencil' style='display:inline-block;cursor:pointer;' onClick='modifyPlanOnTree_(\""
                + detail.getId() + "\",\""
                + detail.getParentPlanId() + "\")' title='修改'></a>";

        String deleteBtnStr = "<a class='basis ui-icon-minus' style='display:inline-block;cursor:pointer;' onClick='deleteOnTree_(\""
                + detail.getId() + "\")' title='删除'></a>";


        returnStr = returnStr + modifyBtnStr;

        returnStr = returnStr + deleteBtnStr;


        return returnStr;
    }


    private String planNameFormat1(PlanDto detail){
        return "<a href='#' onclick=\"viewPlan_(\'" + detail.getId()
                + "\')\" style='color:blue'>" + detail.getPlanName() + "</a>";
    }


    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(Map<String, String> parentPlanIds, List<PlanDto> planList,
                                 JSONObject parentObject,
                                 List<TSTypeDto> types,
                                 String warningDay) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        Map<String, String> planLevelMap = getPlanLevelMap();
        Map<String,String> planMap = new HashMap<String, String>();

        for(PlanDto pl : planList){
            planMap.put(pl.getId(), pl.getPlanName());
        }

        for (PlanDto plan : planList) {

            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planNumber", plan.getPlanNumber());
                newNode.put("optBtn", this.generateOptBtn1(plan));
                newNode.put("displayName", plan.getPlanName());
                if (StringUtils.isNotEmpty(parentPlanIds.get(plan.getId()))) {
                    JSONObject treeNode = new JSONObject();
                    treeNode.put("value", plan.getPlanName());
                    treeNode.put("image", "folder.gif");
                    newNode.put("displayNameNode", treeNode);

                    JSONObject treeNode1 = new JSONObject();
                    treeNode1.put("value", this.planNameFormat1(plan));
                    treeNode1.put("image", "folder.gif");
                    newNode.put("planName", treeNode1);
                }
                else {
                    newNode.put("displayNameNode", plan.getPlanName());
                    newNode.put("planName", this.planNameFormat1(plan));
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
                    /**
                     * TODO 项目模板子计划前置计划不显示
                     * 查看时将项目模板id设为计划的项目id,那么这里应当是不为空
                     * 但是这里判断计划的项目id不为空的意义是什么呢
                     * 先删除
                     */
                    /*if(CommonUtil.isEmpty(plan.getProjectId())){
                        for(String preposeId : plan.getPreposeIds().split(",")){
                            if(CommonUtil.isEmpty(preposePlans)){
                                preposePlans = planMap.get(preposeId);
                            }else{
                                preposePlans = preposePlans+","+planMap.get(preposeId);
                            }
                        }
                    }else{
                        preposePlans = plan.getPreposePlans();
                    }*/

                    for(String preposeId : plan.getPreposeIds().split(",")){
                        if(CommonUtil.isEmpty(preposePlans)){
                            preposePlans = planMap.get(preposeId);
                        }else{
                            preposePlans = preposePlans+","+planMap.get(preposeId);
                        }
                    }

                }

                newNode.put("preposePlans", preposePlans);

                String inputs = "";
                if(!CommonUtil.isEmpty(plan.getInputList())){
                    List<InputsDto> inputList = plan.getInputList();
                    for(InputsDto input : inputList){
                        if(CommonUtil.isEmpty(inputs)){
                            if(!StringUtil.isEmpty(input.getName())){
                                inputs = input.getName();
                            }
                        }else{
                            if(!StringUtil.isEmpty(input.getName())){
                                inputs = inputs+","+input.getName();
                            }
                        }

                    }
                }
                newNode.put("inputs", inputs);


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
                List<DeliverablesInfoDto> deliverablesInfos = plan.getDeliInfoList();


                String delivaryName = "";
                for(DeliverablesInfoDto d :deliverablesInfos){
                    if(!CommonUtil.isEmpty(d) && !CommonUtil.isEmpty(d.getName())){
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


    @SuppressWarnings("unchecked")
    @RequestMapping(params = "getPlanListFor")
    @ResponseBody
    public AjaxJson getPlanListFor(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        List<JSONObject> data= new ArrayList<JSONObject>();
        String projectTemplateId = request.getParameter("projectTemplateId");
        if(CommonUtil.isEmpty(projectTemplateId)){
            projectTemplateId = "";
        }
        ProjTemplateDto projTemplate = projTemplateService.getProjTemplateEntity(projectTemplateId);
        String bizId = projTemplate.getBizId();
        if(CommonUtil.isEmpty(bizId)){
            bizId = "";
        }
        ProjTemplateDto projTemplate2 = projTemplateService.getProjTemplateByBizId(bizId);
        List<PlanDto> detailList = new ArrayList<>();
        String detailStr = (String)redisService.getFromRedis("PROJTMPPLANLIST", projTemplate2.getId());
        if(!CommonUtil.isEmpty(detailStr)){
            detailList = JSON.parseArray(detailStr,PlanDto.class);
        }
        Collections.sort(detailList, new Comparator<PlanDto>() {
            @Override
            public int compare(PlanDto o1, PlanDto o2) {
                return o1.getStoreyNo().compareTo(o2.getStoreyNo());
            }});
        if(!CommonUtil.isEmpty(detailList) && !CommonUtil.isEmpty(detailList)){
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
     * 计划模板详细列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "projTemplateDetailEdit")
    public ModelAndView projTemplateDetailEdit(HttpServletRequest request) {
        String taskNumber = (String)request.getParameter("taskNumber");
        String taskId = (String)request.getParameter("taskId");
        if (StringUtils.isNotEmpty(taskId)) {
            request.setAttribute("taskId", taskId);
        }
        if (StringUtils.isNotEmpty(taskNumber)) {
            request.setAttribute("taskNumber", taskNumber);
        }
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateDetailListBack");
    }


    /**
     * 项目模板详情跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goTemplateDetail")
    public ModelAndView goTemplateDetail(HttpServletRequest request) {
        String projectTemplateId = request.getParameter("id");
        if(CommonUtil.isEmpty(projectTemplateId)){
            projectTemplateId = "";
        }
        ProjTemplateDto template = projTemplateService.getProjTemplateEntity(projectTemplateId);
        String userName = template.getCreateName();
        if (StringUtils.isBlank(userName)) {
            userName = UserUtil.getFormatUserNameId(template.getCreateBy());
        }
        template.setCreateName(userName);
        FeignJson fj = projTemplateService.getLifeCycleStatusList();
        List<LifeCycleStatus> statusList = JSON.parseArray(String.valueOf(fj.getObj()),LifeCycleStatus.class);
    //    List<LifeCycleStatus> statusList = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeCycleStr),new TypeReference<List<LifeCycleStatus>>(){});
        for(LifeCycleStatus status : statusList){
            if(!CommonUtil.isEmpty(template.getBizCurrent()) && status.getName().equals(template.getBizCurrent())){
                template.setStatus(status.getTitle());
                break;
            }
        }
        request.setAttribute("projectTemplateId", projectTemplateId);
        request.setAttribute("projectTemplate", template);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateDetail");
    }


    /**
     * 项目模板详细列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "projTemplateDetail")
    public ModelAndView projTemplateDetail(HttpServletRequest request) {
        String taskNumber = (String)request.getParameter("taskNumber");
        String taskId = (String)request.getParameter("taskId");
        String id = request.getParameter("projTemplateId");
        if (StringUtils.isNotEmpty(taskId)) {
            request.setAttribute("taskId", taskId);
        }
        if (StringUtils.isNotEmpty(taskNumber)) {
            request.setAttribute("taskNumber", taskNumber);
        }
        if (StringUtils.isNotEmpty(taskNumber)) {
            id = taskNumber;
        }
        if (StringUtils.isNotEmpty(id)) {
            ProjTemplateDto projTemplate = projTemplateService.getProjTemplateEntity(id);
            request.setAttribute("projTemplate_", projTemplate);
        }
        request.setAttribute("projectTemplateId", id);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTemplateLayout");
    }

}
