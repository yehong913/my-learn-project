package com.glaway.ids.project.plantemplate.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.CommonConstants;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.service.InputsRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.dto.PlanTempOptLogDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateDetailInfo;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateDetailReq;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.JsonUtil;
import com.glaway.foundation.common.util.PropertiesUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * @Title: Controller
 * @Description: 计划模板详细
 * @author duanpengfei
 * @date 2015-03-27 16:54:10
 * @version V1.0
 */
@Controller
@RequestMapping("/planTemplateDetailController")
public class PlanTemplateDetailController extends BaseController {

    @Autowired
    private PlanTemplateDetailRemoteFeignServiceI planTemplateDetailService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Autowired
    private PlanTemplateRemoteFeignServiceI planTemplateService;
    
    /**
     * 任务输入Service
     */
    @Autowired
    private InputsRemoteFeignServiceI inputsService;

    /**
     * 计划模板详细列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "planTemplateDetail")
    public ModelAndView planTemplateDetail(HttpServletRequest request) {
        String taskNumber = (String)request.getParameter("taskNumber");
        String taskId = (String)request.getParameter("taskId");
        String id = request.getParameter("planTemplateId");
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
            PlanTemplateDto planTemplate = planTemplateService.getPlanTemplateEntity(id);
            request.setAttribute("planTemplate_", planTemplate);
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateDetailList");
    }



    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(PlanTemplateDetailDto planTemplateDetail, HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            String planTemplateId = (String)request.getParameter("id");
            PlanTemplateDetailReq planTemplateDetailReq = new PlanTemplateDetailReq();
            planTemplateDetailReq.setPlanTemplateId(planTemplateId);
            List<PlanTemplateDetailInfo> planTemplateDetailInfoList = new ArrayList<PlanTemplateDetailInfo>();
            List<PlanTemplateDetailDto> list = new ArrayList<>();
            try{
                FeignJson fjson = planTemplateDetailService.getPlanTemplateDetailList(planTemplateId);
                list = JSON.parseArray(JSON.toJSONString(fjson.getObj()),PlanTemplateDetailDto.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            Map<String, String> detailIdDeliverablesMap = planTemplateDetailService.getPlanTemplateOrProjTemplateDetailDeliverables(planTemplateId, "");
            Map<String, String> detailIdPreposeMap = new HashMap<>();
            try{
                FeignJson fj  = planTemplateDetailService.getPlanTemplateOrProjTemplateDetailPreposes(planTemplateId, "");
                detailIdPreposeMap = (Map<String, String>)fj.getObj();
            }catch (Exception e){
                e.printStackTrace();
            }

            List<InputsDto> inputsList = inputsService.getInputsInfoByPlanTemplateId(planTemplateId);
            Map<String, String> inputsNameMap = planTemplateDetailService.getPlanTemplateDetailInputsName(inputsList);

            Map<String,Object> paramMap = new HashMap();
            paramMap.put("inputsList",inputsList);
            paramMap.put("planTemplateDetailList",list);
            paramMap.put("detailIdDeliverablesMap",detailIdDeliverablesMap);

            Map<String, String> inputsOriginMap = planTemplateDetailService.getPlanTemplateDetailInputsOrigin(paramMap);
            // 遍历获得的数据
            for (PlanTemplateDetailDto obj : list) {
                PlanTemplateDetailInfo planTemplateDetailInfo = new PlanTemplateDetailInfo();
                PropertyUtils.copyProperties(planTemplateDetailInfo, obj);
                planTemplateDetailInfo.setPlanTmpNumber(obj.getPlanNumber());
                if (obj.getPlanLevelInfo() != null) {
                    planTemplateDetailInfo.setPlanLevel(obj.getPlanLevelInfo().getName());;
                }
                else {
                    planTemplateDetailInfo.setPlanLevel("");
                }

                if(StringUtils.isNotEmpty(obj.getWorkTime())){
                    if(obj.getWorkTime().contains(".")){
                        planTemplateDetailInfo.setWorkTime(obj.getWorkTime().split("\\.")[0]);
                    }
                }

                if (StringUtils.isNotEmpty(obj.getMilestone())) {
                    if (CommonConstants.FALSE.equals(obj.getMilestone())) {
                        planTemplateDetailInfo.setMilestone("否");
                    }
                    if (CommonConstants.TRUE.equals(obj.getMilestone())) {
                        planTemplateDetailInfo.setMilestone("是");
                    }
                }
                if (StringUtils.isNotEmpty(detailIdDeliverablesMap.get(obj.getId()))) {
                    planTemplateDetailInfo.setDeliverablesName(detailIdDeliverablesMap.get(obj.getId()));
                }
                if (StringUtils.isNotEmpty(detailIdPreposeMap.get(obj.getId()))) {
                    planTemplateDetailInfo.setPreposeName(detailIdPreposeMap.get(obj.getId()));
                }
                if(!CommonUtil.isEmpty(inputsNameMap.get(obj.getId()))) {
                    planTemplateDetailInfo.setInputsName(inputsNameMap.get(obj.getId()));
                }
                if(!CommonUtil.isEmpty(inputsOriginMap.get(obj.getId()))) {
                    planTemplateDetailInfo.setOrigin(inputsOriginMap.get(obj.getId()));
                }
                planTemplateDetailInfoList.add(planTemplateDetailInfo);
            }

            List<JSONObject> rootList = changePlansToJSONObjects(planTemplateDetailInfoList);
            String resultJSON = JSON.toJSONString(rootList);
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 将计划list组装为树节点json
     *
     * @param detailList
     * @return
     * @see
     */

    private List<JSONObject> changePlansToJSONObjects(List<PlanTemplateDetailInfo> detailList) {
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        Map<String, String> parentPlanIds = new HashMap<String, String>();
        Map<String, String> planIdMaps = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(detailList)) {
            for (PlanTemplateDetailInfo p : detailList) {
                planIdMaps.put(p.getId(), p.getId());
                String parentPlanId = p.getParentPlanId();
                if (!StringUtils.isEmpty(parentPlanId)
                        && StringUtils.isEmpty(parentPlanIds.get(parentPlanId))) {
                    parentPlanIds.put(parentPlanId, parentPlanId);
                }
            }
        }
        for (PlanTemplateDetailInfo p : detailList) {
            // 若其无父计划或者其父计划不在结果集中，则将其设为root节点
            if (StringUtils.isEmpty(p.getParentPlanId())
                    || StringUtils.isEmpty(planIdMaps.get(p.getParentPlanId()))) {
                JSONObject root = new JSONObject();
                root.put("id", p.getId());
                root.put("num", p.getNum());
                root.put("deliverablesCount", p.getDeliverablesCount());
                root.put("parentPlanId", p.getParentPlanId());
                root.put("planTmpNumber", p.getPlanTmpNumber());
                root.put("name", p.getName());
                root.put("planLevel", p.getPlanLevel());
                root.put("workTime", p.getWorkTime());
                root.put("workTimeDisplay", p.getWorkTime());
                root.put("deliverablesName", p.getDeliverablesName());
                root.put("preposeName", p.getPreposeName());
                root.put("milestone", p.getMilestone());
                root.put("inputsName", p.getInputsName());
                root.put("origin", p.getOrigin());
                List<JSONObject> rows = new ArrayList<JSONObject>();
                root.put("rows", rows);
                rootList.add(root);
            }
        }
        for (int i = 0; i < rootList.size(); i++ ) {
            this.findSubNodeByPid(parentPlanIds, detailList, rootList.get(i));
        }
        return rootList;
    }


    /**
     * Description:递归查询获取所有子节点
     *
     * @param parentPlanIds
     * @param parentObject
     * @see
     */
    @SuppressWarnings("unchecked")
    public void findSubNodeByPid(Map<String, String> parentPlanIds,
                                 List<PlanTemplateDetailInfo> detailList, JSONObject parentObject) {
        String pid = parentObject.getString("id");
        List<JSONObject> subNodeList = new ArrayList<JSONObject>();
        for (PlanTemplateDetailInfo plan : detailList) {
            if (pid.equals(plan.getParentPlanId())) {
                JSONObject newNode = new JSONObject();
                newNode.put("id", plan.getId());
                newNode.put("num", plan.getNum());
                newNode.put("deliverablesCount", plan.getDeliverablesCount());
                newNode.put("parentPlanId", plan.getParentPlanId());
                newNode.put("planTmpNumber", plan.getPlanTmpNumber());
                newNode.put("name", plan.getName());
                newNode.put("planLevel", plan.getPlanLevel());
                newNode.put("workTime", plan.getWorkTime());
                newNode.put("workTimeDisplay", plan.getWorkTime());
                newNode.put("deliverablesName", plan.getDeliverablesName());
                newNode.put("preposeName", plan.getPreposeName());
                newNode.put("milestone", plan.getMilestone());
                newNode.put("inputsName", plan.getInputsName());
                newNode.put("origin", plan.getOrigin());
                List<JSONObject> rows = new ArrayList<JSONObject>();
                newNode.put("rows", rows);
                subNodeList.add(newNode);
            }
        }
        if (subNodeList.size() > 0) {
            for (int i = 0; i < subNodeList.size(); i++ ) {
                List<JSONObject> rows = (List<JSONObject>)parentObject.get("rows");
                this.findSubNodeByPid(parentPlanIds, detailList, subNodeList.get(i));
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
     * 计划模板详细列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "planTemplateDetailEdit")
    public ModelAndView planTemplateDetailEdit(HttpServletRequest request) {
        String taskNumber = (String)request.getParameter("taskNumber");
        String taskId = (String)request.getParameter("taskId");
        String id = request.getParameter("planTemplateId");
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
            PlanTemplateDto planTemplate = planTemplateService.getPlanTemplateEntity(id);
            request.setAttribute("planTemplate_", planTemplate);
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateDetailListBack");
    }


    /**
     * 计划模板版本信息 页面跳转
     *
     * @returnplanTemplateVersionDetail
     */
    @RequestMapping(params = "planTemplateVersionDetail")
    public ModelAndView planTemplateVersionDetail(HttpServletRequest request) {
        String id = request.getParameter("planTemplateId");
        if (!CommonUtil.isEmpty(id)) {
            PlanTemplateDto planTemplate = planTemplateService.getPlanTemplateEntity(id);
            request.setAttribute("planTemplate_", planTemplate);
        }
        return new ModelAndView("com/glaway/ids/project/plantemplate/planTemplateVesionDetailList");
    }


    @RequestMapping(params = "getVersionHistory")
    public void getVersionHistory(String bizId, HttpServletRequest req,
                                  HttpServletResponse response) {
        try {
            // 自定义追加查询条件
            FeignJson fj = planTemplateService.getVersionHistoryAndCount(bizId, Integer.valueOf(req.getParameter("rows")),
                    Integer.valueOf(req.getParameter("page")));
            Map<String,Object> map = (Map<String,Object>)fj.getObj();
            ObjectMapper mapper = new ObjectMapper();
            List<PlanTemplateDto> planTemplateList = mapper.convertValue(map.get("planTemplateDtoList"),new TypeReference<List<PlanTemplateDto>>(){});

            long count = Long.valueOf(String.valueOf(map.get("count")));

            if (!CommonUtil.isEmpty(planTemplateList)) {
                for (PlanTemplateDto planTemplate : planTemplateList) {
                    if (!CommonUtil.isEmpty(planTemplate.getCreateBy())) {
                        planTemplate.setCreator(planTemplate.getCreateFullName()+"-"+planTemplate.getCreateName());
                    }
                }
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().setVersion(
                    1.0).create();
            String json = gson.toJson(planTemplateList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(params = "getOptLogList")
    public void getOptLogList(String bizId, HttpServletRequest req,
                              HttpServletResponse response) {
        try {
            List<PlanTempOptLogDto> list = new ArrayList<PlanTempOptLogDto>();
            list = planTemplateService.findPlanTempOptLogById(bizId);
            for (PlanTempOptLogDto log : list) {
                if (log.getCreateBy() != null) {
                    log.setCreateName(log.getCreateFullName()+ "-"
                            + log.getCreateName());
                }
            }
            String json = JsonUtil.getListJsonWithoutQuote(list);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
