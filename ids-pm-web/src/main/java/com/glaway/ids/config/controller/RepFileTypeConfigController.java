

package com.glaway.ids.config.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.businessobject.attribute.dto.AdditionalAttributeDto;
import com.glaway.foundation.businessobject.attribute.dto.EntityAdditionalAttributeDto;
import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.businessobject.attribute.dto.InstanceAttributeValDto;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.foundation.fdk.dev.service.FeignAttributeService;
import com.glaway.foundation.fdk.dev.service.FeignSerialNumberService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.system.serial.dto.SerialNumberGeneratorInfoDto;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.pbmn.activity.dto.BpmnTaskDto;
import com.glaway.ids.config.constant.RepFileTypeConfigConstants;
import com.glaway.ids.config.service.RepFileTypeConfigRemoteFeignServiceI;
import com.glaway.ids.config.vo.BpmnTaskVo;
import com.glaway.ids.config.vo.RepFileTypeConfigVo;
import com.glaway.ids.constant.BpmnConstants;
import com.glaway.ids.util.CodeUtils;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/*
 * 文件名：RepFileTypeConfigController.java
 * 版权：Copyright by www.glaway.com
 * 描述：文档类型设置
 * 修改人：zhousuxia
 * 修改时间：2018年7月26日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */


@Controller
@RequestMapping(value = "/repFileTypeConfigController")
public class RepFileTypeConfigController extends BaseController {

    private static final OperationLog log = BaseLogFactory.getOperationLog(RepFileTypeConfigController.class);

    /**
     * 文档类型设置服务类
     */
    @Autowired
    private RepFileTypeConfigRemoteFeignServiceI repFileTypeConfigService;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     *
     */
    @Autowired
    private FeignRepService repService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private FeignAttributeService attributeService;

    @Autowired
    private FeignSerialNumberService serialNumberService;

    String appKey = ResourceUtil.getApplicationInformation().getAppKey();


    /**
     * 工作流服务类
     */
 /*   @Autowired
    private BpmnServiceI bpmnService;*/

    /**
     * 跳转文档类型设置主页面
     *
     * @author zhousuxia
     * @version 2018年7月26日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "repFileTypeConfigIndex")
    public ModelAndView repFileTypeConfigIndex(HttpServletRequest request){
        request.setAttribute("entrance", "repFileType");

        String fmsBpmPath = "";
        List<ServiceInstance> inss = discoveryClient.getInstances("IDS-PM-SERVICE");
        if (!CommonUtil.isEmpty(inss)) {
            EurekaDiscoveryClient.EurekaServiceInstance serviceIns = (EurekaDiscoveryClient.EurekaServiceInstance) inss.get(0);
            fmsBpmPath = serviceIns.getInstanceInfo().getInstanceId().toString();
        }
        request.setAttribute("serviceName",fmsBpmPath);

        return new ModelAndView("com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigList");
    }



    /**
     * 文档类型设置列表获取
     *
     * @author zhousuxia
     * @version 2018年7月26日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "repFileTypeConfigList")
    public void repFileTypeConfigList(HttpServletRequest request, HttpServletResponse response) {

        String fileTypeCode = request.getParameter("fileTypeCode");
        String fileTypeName = request.getParameter("fileTypeName");
        String entrance = request.getParameter("entrance");
        String docTypeId = request.getParameter("docTypeId");

        RepFileTypeDto repFileTypeDto = new RepFileTypeDto();
        repFileTypeDto.setFileTypeCode(fileTypeCode);
        repFileTypeDto.setFileTypeName(fileTypeName);
        repFileTypeDto.setParentId(RepFileTypeConfigConstants.REPFILETYPECONFIG_PARENTID);
        List<RepFileTypeDto> fileTypeList = repService.getRepFileTypeConfigList(ResourceUtil.getApplicationInformation().getAppKey(),entrance,docTypeId,repFileTypeDto);
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        if (!CommonUtil.isEmpty(fileTypeList)) {
            for (int i = 0; i < fileTypeList.size(); i++ ) {
                RepFileTypeDto fileType = fileTypeList.get(i);
                JSONObject json = new JSONObject();
                /*
                 * TSIcon icon =null;
                 * if(fileType.getIconId()!=null){
                 * icon = systemService.getEntity(TSIcon.class, fileType.getIconId());
                 * json.put("iconCls", icon.getIconClas());
                 * }
                 */
                json.put("iconCls", fileType.getIconName());
                json.put("_parentId", fileType.getParentId());
                json.put("id", fileType.getId());
                json.put("fileTypeName", fileType.getFileTypeName());
                json.put("fileTypeCode", fileType.getFileTypeCode());

                json.put("lifecyclePolicyId", fileType.getLifecyclePolicyId());
                json.put("generateRuleId", fileType.getGenerateRuleId());

                if(!CommonUtil.isEmpty(fileType.getGenerateRuleId())){
                    SerialNumberGeneratorInfoDto info = serialNumberService.getSerialNumberGeneratorInfo(ResourceUtil.getApplicationInformation().getAppKey(), fileType.getGenerateRuleId());
                    if(!CommonUtil.isEmpty(info)){
                        json.put("generateRuleName", info.getName());
                        json.put("generateRuleDesc", info.getDescription());
                    }
                }


                json.put("remark", fileType.getDescription());

                json.put("status", fileType.getStatus());
                json.put("createTime",
                        DateUtil.dateToString(fileType.getCreateTime(), DateUtil.FORMAT_ONE));
                json.put("createName",
                        fileType.getCreateFullName() + "-" + fileType.getCreateName());
                json.put("updateTime",
                        DateUtil.dateToString(fileType.getUpdateTime(), DateUtil.FORMAT_ONE));
                json.put("updateName",
                        fileType.getUpdateFullName() + "-" + fileType.getUpdateName());
                jsonList.add(json);
            }
        }
        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));
        List<JSONObject> list = new ArrayList<JSONObject>();

        int count = jsonList.size();
        if (count > page * rows) {
            list = jsonList.subList((page - 1) * rows, page * rows);
        }
        else {
            list = jsonList.subList((page - 1) * rows, jsonList.size());
        }

        DataGridReturn fileTypeTreeGrid = new DataGridReturn(jsonList.size(), list);
        String json = JSONObject.toJSONString(fileTypeTreeGrid);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 跳转文档类型设置新增页面
     *
     * @author zhousuxia
     * @version 2018年7月26日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goAddRepFileTypeConfig")
    public ModelAndView goAddRepFileTypeConfig(HttpServletRequest request){

        String type = request.getParameter("type");
        request.setAttribute("type", type);

        String id = request.getParameter("id");
        if(!CommonUtil.isEmpty(id)){
            RepFileTypeDto fileType = repService.getRepFileTypeById(ResourceUtil.getApplicationInformation().getAppKey(), id);
            request.setAttribute("fileType", fileType);
            if(!CommonUtil.isEmpty(fileType.getGenerateRuleId())){
                SerialNumberGeneratorInfoDto info = serialNumberService.getSerialNumberGeneratorInfo(ResourceUtil.getApplicationInformation().getAppKey(), fileType.getGenerateRuleId());
                if(!CommonUtil.isEmpty(info)){
                    request.setAttribute("generateRuleName", info.getName());
                    request.setAttribute("generateRuleDesc", info.getDescription());
                }
            }

            request.setAttribute("description", fileType.getDescription());
        }

        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigAdd");
    }


    /**
     * 打开选择businessObject对象的dialog
     *
     * @return
     */
    @RequestMapping(params = "goSelectGenerator")
    public ModelAndView goSelectGenerator(SerialNumberGeneratorInfoDto generatorInfo,
                                          HttpServletRequest req) {
        req.setAttribute("rowId", req.getParameter("generateRuleId"));
        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigSelectSNGenerator");
    }


    /**
     * 保存前校验编号是否存在
     *
     * @author zhousuxia
     * @version 2018年7月26日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkFileTypeCodeBeforeSave")
    @ResponseBody
    public AjaxJson checkFileTypeCodeBeforeSave(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        boolean flag = true;
        try{
            String fileTypeCode = request.getParameter("fileTypeCode");
            String repFileTypeId = request.getParameter("repFileTypeId");
            flag = repFileTypeConfigService.checkFileTypeCodeBeforeSave(RepFileTypeConfigConstants.REPFILETYPECONFIG_PARENTID,repFileTypeId,fileTypeCode);
            if(!flag){
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.fileTypeCodeRepeat"));
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }




    /**
     * 保存文档类型设置
     *
     * @author zhousuxia
     * @version 2018年7月26日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveRepFileTypeConfig")
    @ResponseBody
    public FeignJson saveRepFileTypeConfig(HttpServletRequest request){
        FeignJson j = new FeignJson();
        String repFileTypeId = request.getParameter("repFileTypeId");
        String fileTypeCode = request.getParameter("fileTypeCode");
        String fileTypeName = request.getParameter("fileTypeName");
        String generatorInfoId = request.getParameter("generatorInfoId");
        String description = request.getParameter("description");

        try{
            j = repFileTypeConfigService.saveRepFileTypeConfig(repFileTypeId,fileTypeCode,fileTypeName,generatorInfoId,description,ResourceUtil.getCurrentUser().getId());
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            return j;
        }
    }


    /**
     * 跳转至详情页面
     * @param request
     * @return
     */
    @RequestMapping(params = "goCheckRepFileTypeConfig")
    public ModelAndView goCheckRepFileTypeConfig(HttpServletRequest request){
        String type = request.getParameter("type");
        request.setAttribute("type", type);
        String id = request.getParameter("id");
        request.setAttribute("repFileTypeId", id);
        String entityUri = "com.glaway.foundation.rep.entity.RepFileType";
        request.setAttribute("entityUri", entityUri);
        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigCheck");
    }


    /**
     * 删除文档类型设置
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelRepFileTypeConfig")
    @ResponseBody
    public AjaxJson doDelRepFileTypeConfig(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        try{
            repFileTypeConfigService.deleteRepFileTypeConfig(ids);
            j.setSuccess(true);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.delSuccess"));
            log.info("文档类型删除成功");
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.delFailure"));
            log.info("文档类型删除失败");
        }finally{
            return j;
        }
    }


    /**
     * 启用/禁用文档类型设置
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doChangeRepFileTypeConfigStatus")
    @ResponseBody
    public AjaxJson doChangeRepFileTypeConfigStatus(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("type");
        String ids = request.getParameter("ids");
        try{
            repFileTypeConfigService.changeRepFileTypeStatus(type,ids);
            j.setSuccess(true);
            if(type.equals("enable")){
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.enableSuccess"));
                log.info("文档类型启用成功");
            }else if(type.equals("disable")){
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.disableSuccess"));
                log.info("文档类型禁用成功");
            }
        }catch(Exception e){
            if(type.equals("enable")){
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.enableFailure"));
                log.info("文档类型启用失败");
            }else if(type.equals("disable")){
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.disableFailure"));
                log.info("文档类型禁用失败");
            }
        }finally{
            return j;
        }

    }



    /**
     * 应用对象format
     * @param request
     * @param response
     */
    @RequestMapping(params = "applyObjFormat")
    public void applyObjFormat(String id, String ruleRelateId, HttpServletRequest request, HttpServletResponse response) {
        SerialNumberGeneratorInfoDto info = serialNumberService.getSerialNumberGeneratorInfo(ResourceUtil.getApplicationInformation().getAppKey(), id);
        JSONObject jObj = new JSONObject();
        jObj.put("description", info.getDescription());
        jObj.put("generatorType", info.getGeneratorType());

/*        SerialNumberGeneratorRuleDto rule = serialNumberService.get(SerialNumberGeneratorRule.class, info.getGenerateRuleId());
        if(StringUtil.isNotEmpty(ruleRelateId)){
            SerialNumberGeneratorRuleRelate ruleRelate = systemService.get(SerialNumberGeneratorRuleRelate.class, ruleRelateId);
            if(StringUtil.isNotEmpty(ruleRelate.getGeneratorInfoId()) && ruleRelate.getGeneratorInfoId().equals(id)){
                jObj.put("startValue", ruleRelate.getInitValue());
            }
            else if(info != null && GeneratorRuleConstant.GENERATOR_RULE_TYPE_RULE.equals(info.getGeneratorType())){
                jObj.put("startValue", rule.getStartValue());
            }
        }
        else if(info != null && GeneratorRuleConstant.GENERATOR_RULE_TYPE_RULE.equals(info.getGeneratorType())){
            jObj.put("startValue", rule.getStartValue());
        }*/

        Object json = JSONArray.toJSON(jObj);
        TagUtil.ajaxResponse(response, json.toString());
    }


    /**
     * 跳转设置页面
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goSetRepFileTypeConfig")
    public ModelAndView goSetRepFileTypeConfig(HttpServletRequest request){
        String id = request.getParameter("id");
        request.setAttribute("repFileTypeId", id);
        request.setAttribute("fileTypeName", request.getParameter("fileTypeName"));
        String entityUri = "com.glaway.foundation.rep.entity.RepFileType";
        request.setAttribute("entityUri", entityUri);

        repFileTypeConfigService.clearBpmnTaskVoList(BpmnConstants.TASKVO_REPFILETYPE, id);
        List<BpmnTaskVo> taskVoList = repFileTypeConfigService.queryBpmnTaskList(id);
        if(!CommonUtil.isEmpty(taskVoList)){
            repFileTypeConfigService.addListToRedis(BpmnConstants.TASKVO_REPFILETYPE, id, taskVoList);
        }

        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigSet");
    }


    /**
     * 跳转流程设置页面
     *
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goApproveProcessTab")
    public ModelAndView goApproveProcessTab(HttpServletRequest request){
        String repFileTypeId = request.getParameter("repFileTypeId");
        request.setAttribute("repFileTypeId", repFileTypeId);
        String entityUri = request.getParameter("entityUri");
        request.setAttribute("entityUri", entityUri);
        request.setAttribute("bpmnUri","");
        String fieldName = request.getParameter("fieldName");
        request.setAttribute("fieldName", fieldName);
        String fieldValue = request.getParameter("fieldValue");
        request.setAttribute("fieldValue", fieldValue);
        String type = request.getParameter("type");
        request.setAttribute("type", type);
        List<BpmnTaskVo> taskVoList = repFileTypeConfigService.getFromRedis(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId);
        if(!CommonUtil.isEmpty(taskVoList)){
            request.setAttribute("formId",taskVoList.get(0).getFormId());
        }

        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeApproveProcessTab");
    }



    /**
     * 跳转新增流程节点页面
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goAddTask")
    public ModelAndView goAddTask(HttpServletRequest request) {
        request.setAttribute("repFileTypeId", request.getParameter("repFileTypeId"));
        request.setAttribute("formId", request.getParameter("formId"));
        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeTaskDialog");
    }


    /**
     * 新增流程节点
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddTask")
    @ResponseBody
    public AjaxJson doAddTask(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String repFileTypeId = request.getParameter("repFileTypeId");
        String taskName = request.getParameter("taskName");
        String roleName = request.getParameter("roleName");
        String taskType = request.getParameter("taskType");
        String numbers = request.getParameter("numbers");
        String remark = request.getParameter("remark");
        String formId = request.getParameter("formId");
        String approvePercent = request.getParameter("approvePercent");
        BpmnTaskVo task = new BpmnTaskVo();
        task.setId(UUID.randomUUID().toString());
        task.setName(taskName);
        task.setRoles(roleName);
        task.setApproveType(taskType);
        task.setNumbers(numbers);
        task.setRemark(remark);
        task.setFormId(formId);
        task.setOriginId(repFileTypeId);
        if(StringUtil.equals(numbers, BpmnConstants.TASK_ASSIGN_MODEL_MULTI)){
            task.setApprovePercent(approvePercent);
        }
        try{
            if(repFileTypeConfigService.isTaskNameRepeat(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId, taskName)){
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.taskNameCannotRepeat"));
            }else {
                repFileTypeConfigService.addTaskVoToRedis(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId, task);
                j.setSuccess(true);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.addProcessSuccess"));
                log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.addProcessSuccess"));
            }
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.addProcessFail"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.addProcessFail"));
        }finally{
            return j;
        }
    }


    /**
     * 移动文档类型流程节点
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doMoveRepFileTypeTask")
    @ResponseBody
    public AjaxJson doMoveRepFileTypeTask(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String type = request.getParameter("type");
        String repFileTypeId = request.getParameter("repFileTypeId");
        try{
            repFileTypeConfigService.moveTaskVoById(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId, ids, type);
            j.setSuccess(true);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.moveDataSuccess"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.moveDataSuccess"));
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.moveDataSuccess"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.moveDataSuccess"));
        }finally{
            return j;
        }
    }


    /**
     * 删除文档类型流程节点
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelRepFileTypeTask")
    @ResponseBody
    public AjaxJson doDelRepFileTypeTask(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String repFileTypeId = request.getParameter("repFileTypeId");
        try{
            repFileTypeConfigService.batchDeleteFromRedis(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId, ids);
            j.setSuccess(true);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.delProcessTaskSuccess"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.delProcessTaskSuccess"));
        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.delProcessTaskFail"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.delProcessTaskFail"));
        }finally{
            return j;
        }
    }


    /**
     * 获取自定义设置页面列表
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "getRepFileTypeApproveProcess")
    public void getRepFileTypeApproveProcess(String entityUri, String entityAttrName,String entityAttrVal,
                                             HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("repFileTypeId");
        String view = request.getParameter("view");
        List<BpmnTaskVo> taskVoList = new ArrayList<BpmnTaskVo>();
        if(StringUtil.equals(view, "view")){
            repFileTypeConfigService.clearBpmnTaskVoList(BpmnConstants.TASKVO_REPFILETYPE, id);
            taskVoList = repFileTypeConfigService.queryBpmnTaskList(id);
            if(!CommonUtil.isEmpty(taskVoList)){
                repFileTypeConfigService.addListToRedis(BpmnConstants.TASKVO_REPFILETYPE, id, taskVoList);
            }
        }else{
            taskVoList = repFileTypeConfigService.getFromRedis(BpmnConstants.TASKVO_REPFILETYPE, id);
        }
        /*List<EntityAttributeAdditionalAttribute> allList = repFileTypeConfigService.getEntityAttributeAdditionalAttributeListByCondition(entityUri, entityAttrName, entityAttrVal);
        List<AdditionalAttribute> list = new ArrayList<AdditionalAttribute>();
        if(!CommonUtil.isEmpty(allList)){
            for(EntityAttributeAdditionalAttribute row : allList) {
                if(!CommonUtil.isEmpty(row.getAddAttrId())){
                    AdditionalAttribute additionalAttribute = repFileTypeConfigService.getEntity(AdditionalAttribute.class , row.getAddAttrId());
                    list.add(additionalAttribute);
                }
            }
        }*/

        String json = JSON.toJSONString(taskVoList);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 跳转设置-自定义设置页面
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goCustomAttributeTab")
    public ModelAndView goCustomAttributeTab(HttpServletRequest request){
        String repFileTypeId = request.getParameter("repFileTypeId");
        request.setAttribute("repFileTypeId", repFileTypeId);
        String entityUri = request.getParameter("entityUri");
        request.setAttribute("entityUri", entityUri);
        String fieldName = request.getParameter("fieldName");
        request.setAttribute("fieldName", fieldName);
        String fieldValue = request.getParameter("fieldValue");
        request.setAttribute("fieldValue", fieldValue);
        String type = request.getParameter("type");
        request.setAttribute("type", type);
        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigCustomAttr");
    }


    /**
     * 跳转新增软属性页面
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "goAddAttr")
    public ModelAndView goAddAttr(HttpServletRequest request) {
        request.setAttribute("entityUri", request.getParameter("entityUri"));
        request.setAttribute("fieldName", request.getParameter("fieldName"));
        request.setAttribute("fieldValue", request.getParameter("fieldValue"));
        List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition(request.getParameter("entityUri"), request.getParameter("fieldName"), request.getParameter("fieldValue"));
        if(!CommonUtil.isEmpty(allList)){
            request.setAttribute("oldId",allList.get(0).getId());
        }
        return new ModelAndView(
                "com/glaway/ids/config/repFileTypeConfig/repFileTypeConfigSelectAttributeDialog");
    }


    /**
     * 获取自定义设置页面列表
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @RequestMapping(params = "getEntityAttributeAdditionalAttributeByEntityUri")
    public void getEntityAttributeAdditionalAttributeByEntityUri(String entityUri, String entityAttrName,String entityAttrVal,
                                                                 HttpServletRequest request, HttpServletResponse response) {
        List<EntityAttributeAdditionalAttributeDto> allList = attributeService.getEntityAttributeAdditionalAttributeListByCondition(entityUri, entityAttrName, entityAttrVal);
        List<AdditionalAttributeDto> list = new ArrayList<AdditionalAttributeDto>();
        if(!CommonUtil.isEmpty(allList)){
            for(EntityAttributeAdditionalAttributeDto row : allList) {
                if(!CommonUtil.isEmpty(row.getAddAttrId())){
                    AdditionalAttributeDto additionalAttribute = attributeService.getAdditionalAttribute(ResourceUtil.getApplicationInformation().getAppKey(), row.getAddAttrId());
                    list.add(additionalAttribute);
                }
            }
        }

        String json = JSON.toJSONString(list);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 删除软属性
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "deleteAdditionalAttributeInfo")
    @ResponseBody
    public AjaxJson deleteAdditionalAttributeInfo(HttpServletRequest request,String ids) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.delSuccess");
        String fieldValue = request.getParameter("fieldValue");
        try {
            if (ids.length() > 1) {
                for (String id : ids.split(",")) {
                    List<EntityAttributeAdditionalAttributeDto> list = attributeService.getEntityAttributeAdditionalAttributeListByCondition(request.getParameter("entityUri"),
                            request.getParameter("fieldName"),fieldValue);
                    if(!CommonUtil.isEmpty(list)){
                        for(EntityAttributeAdditionalAttributeDto row : list){
                            if(row.getAddAttrId().equals(id)){
                                attributeService.deleteEntityAttributeAdditionalAttributeById(row.getId());
                            }
                        }
                    }
                }
            }
            else {
                List<EntityAttributeAdditionalAttributeDto> list = attributeService.getEntityAttributeAdditionalAttributeListByCondition(request.getParameter("entityUri"),
                        request.getParameter("fieldName"),fieldValue);
                if(!CommonUtil.isEmpty(list)){
                    for(EntityAttributeAdditionalAttributeDto row : list){
                        if(row.getAddAttrId().equals(ids)){
                            attributeService.deleteEntityAttributeAdditionalAttributeById(row.getId());
                        }
                    }
                }
            }
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.config.repFileTypeConfig.delFailure");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                    EntityAttributeAdditionalAttributeDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }



    @RequestMapping(params = "getAdditionalAttributeList")
    public void getAdditionalAttributeList(HttpServletRequest request, HttpServletResponse response) {
        String entityUri = request.getParameter("entityUri");
        String entityAttrName = request.getParameter("fieldName");
        String entityAttrVal = request.getParameter("fieldValue");
        List<AdditionalAttributeDto> additionalAttributeList = attributeService.getAdditionalAttributeList(entityUri, entityAttrName, entityAttrVal);
        String json = JSON.toJSONString(additionalAttributeList);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(AdditionalAttributeDto attr, HttpServletRequest request,
                         HttpServletResponse response, DataGrid dataGrid) {
        String message = "业务对象类型查询成功";


        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        String bPage = request.getParameter("bPage");
        PageList pageList = new PageList();
        if(bPage == null || bPage.equals("true")){
            pageList= attributeService.findAllAdditionalAttribute(conditionList,true);
        }else{
            pageList = attributeService.findAllAdditionalAttribute(conditionList,false);
        }

        Gson gson = new Gson();

        //查询实体类-软属性关联表
        List<EntityAdditionalAttributeDto> allEntityAttrList =  attributeService.getAllEntityAdditionalAttribute(ResourceUtil.getApplicationInformation().getAppKey());
        //查询实体属性值-软属性关联表
        List<EntityAttributeAdditionalAttributeDto> allEntityAttrAddAttrList =  attributeService.getAllEntityAttributeAdditionalAttribute(ResourceUtil.getApplicationInformation().getAppKey());
        //查询实例-软属性值关联表
        List<InstanceAttributeValDto> existsInstanceAttrValList = attributeService.getAllInstanceAttributeVal(ResourceUtil.getApplicationInformation().getAppKey());

        for (Object rr : pageList.getResultList()) {
            if(rr instanceof AdditionalAttributeDto){
                AdditionalAttributeDto attribute = (AdditionalAttributeDto) rr;
                List<EntityAdditionalAttributeDto> enAddAttrList = new ArrayList<EntityAdditionalAttributeDto>();
                if(allEntityAttrList != null && allEntityAttrList.size() > 0){
                    for (EntityAdditionalAttributeDto a : allEntityAttrList) {
                        if(attribute.getId().equals(a.getAddAttrId())){
                            enAddAttrList.add(a);
                        }
                    }
                }

                List<EntityAttributeAdditionalAttributeDto> enAttrAddAttrList = new ArrayList<EntityAttributeAdditionalAttributeDto>();
                if(allEntityAttrAddAttrList != null && allEntityAttrAddAttrList.size() > 0){
                    for (EntityAttributeAdditionalAttributeDto a : allEntityAttrAddAttrList) {
                        if(attribute.getId().equals(a.getAddAttrId())){
                            enAttrAddAttrList.add(a);
                        }
                    }
                }

                List<InstanceAttributeValDto> instanceAddAttrValList = new ArrayList<InstanceAttributeValDto>();
                if(existsInstanceAttrValList != null && existsInstanceAttrValList.size() > 0){
                    for (InstanceAttributeValDto a : existsInstanceAttrValList) {
                        if(attribute.getId().equals(a.getAddAttrId())){
                            instanceAddAttrValList.add(a);
                        }
                    }
                }

                attribute.setExt1(enAddAttrList.size() + enAttrAddAttrList.size() + instanceAddAttrValList.size() + "");
            }
        }
        String json = gson.toJson(new DataGridReturn(pageList.getCount(), pageList.getResultList()));
        TagUtil.ajaxResponse(response, json);

    }



    /**
     * 保存软属性
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "saveOrUpdateEntityAttributeAdditionalAttribute")
    @ResponseBody
    public FeignJson saveOrUpdateEntityAttributeAdditionalAttribute(HttpServletRequest request, HttpServletResponse response) {
        List<EntityAttributeAdditionalAttributeDto> entityAttributeAdditionalAttributeList = new ArrayList<EntityAttributeAdditionalAttributeDto>();
        String configDataMapStr = request.getParameter("configDataMapStr");
        String oldId = "";
        List<Map> configDataMapList = JSONObject.parseArray("[" + configDataMapStr + "]", Map.class);
        if (configDataMapList != null && configDataMapList.size() > 0) {
            Map config = configDataMapList.get(0);
            String entityUri = config.get("entityUri")==null?"":config.get("entityUri").toString();
            String entityAttrName = config.get("fieldName")==null?"":config.get("fieldName").toString();
            String entityAttrVal = config.get("fieldValue")==null?"":config.get("fieldValue").toString();
            oldId = config.get("oldId")==null?"":config.get("oldId").toString();
            List<Map<String, Object>> attrMapList = (List<Map<String, Object>>)config.get("addAttrRows");
            int attrOrder = 1;
            for(Map<String, Object> map : attrMapList) {
                EntityAttributeAdditionalAttributeDto entityAttributeAdditionalAttribute = new EntityAttributeAdditionalAttributeDto();
                entityAttributeAdditionalAttribute.setEntityUri(entityUri);
                entityAttributeAdditionalAttribute.setEntityAttrName(entityAttrName);
                entityAttributeAdditionalAttribute.setEntityAttrVal(entityAttrVal);
                entityAttributeAdditionalAttribute.setAddAttrId(map.get("id").toString());
                entityAttributeAdditionalAttribute.setAddAttrOrder(attrOrder++);
                entityAttributeAdditionalAttributeList.add(entityAttributeAdditionalAttribute);
            }
        }
//        }
        return repFileTypeConfigService.editEntityAttributeAdditionalAttribute(oldId, entityAttributeAdditionalAttributeList);
    }



    /**
     * 发布文档类型任务流程
     *
     * @author zhousuxia
     * @version 2018年7月27日
     * @see RepFileTypeConfigController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDeployRepFileTypeTaskFlow")
    @ResponseBody
    public AjaxJson doDeployRepFileTypeTaskFlow(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String repFileTypeId = request.getParameter("repFileTypeId");
        String basePath = request.getServletContext().getRealPath(
                "/") + "bpmn\\";
       /* String basePath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(
                "/") + "bpmn\\";*/
        RepFileTypeDto entity = repService.getRepFileTypeById(ResourceUtil.getApplicationInformation().getAppKey(), repFileTypeId);
        String fileTypeName=entity.getFileTypeName();
        try{
            //     if(!CommonUtil.isEmpty(ids)){
            repFileTypeConfigService.deployTaskFlow(BpmnConstants.TASKVO_REPFILETYPE, repFileTypeId, ids, fileTypeName,basePath,ResourceUtil.getCurrentUser());
            //     }
            j.setSuccess(true);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.setProcessSuccess"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.setProcessSuccess"));

        }catch(Exception e){
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.updateProcessFail"));
            log.info(I18nUtil.getValue("com.glaway.ids.pm.config.bpmn.updateProcessFail"));
        }finally{
            return j;
        }
    }


    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
   /* @RequestMapping(params = "doExportXls")
    public void doExportXls(HttpServletRequest request, HttpServletResponse response) {
        String fileTypeCode = request.getParameter("fileTypeCode");
        String fileTypeName = request.getParameter("fileTypeName");
        try{
            repFileTypeConfigService.doExportXls(fileTypeCode,fileTypeName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/


    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "doExportXls")
    public void doExportXls(HttpServletRequest request, HttpServletResponse response) {
        try {
            String fileTypeCode = request.getParameter("fileTypeCode");
            String fileTypeName = request.getParameter("fileTypeName");
            List<RepFileTypeConfigVo> list = new ArrayList<RepFileTypeConfigVo>();
            List<RepFileTypeDto> paramslist = repFileTypeConfigService.getRepFileTypeConfigList(RepFileTypeConfigConstants.REPFILETYPECONFIG_PARENTID, fileTypeCode, fileTypeName, "", "");
            Map<String, String> idAttrMap = new HashMap<String, String>();
            Map<String, SerialNumberGeneratorInfoDto> idRuleDescMap = new HashMap<String, SerialNumberGeneratorInfoDto>();
            Map<String, String> idRemarkMap = new HashMap<String, String>();
            idAttrMap = repFileTypeConfigService.getCustomAttrMap(idAttrMap, RepFileTypeConfigConstants.ENTITY_URI);
            idRemarkMap = repService.getRepFileTypeRemarkMap(ResourceUtil.getApplicationInformation().getAppKey());
            idRuleDescMap = repService.getRuleMap(ResourceUtil.getApplicationInformation().getAppKey());
            //最多的审批环节数,默认为0
            int maxApprove = 0;
            if(!CommonUtil.isEmpty(paramslist)) {
                for(RepFileTypeDto filetype : paramslist) {
                    RepFileTypeConfigVo vo = new RepFileTypeConfigVo();
                    vo.setCode(filetype.getFileTypeCode());
                    vo.setName(filetype.getFileTypeName());
                    if(!CommonUtil.isEmpty(filetype.getGenerateRuleId())) {
                        vo.setGenerateRuleName(idRuleDescMap.get(filetype.getGenerateRuleId()).getName());
                        vo.setGenerateRuleDesc(idRuleDescMap.get(filetype.getGenerateRuleId()).getDescription());
                    }
                    //审批流程
                    List<BpmnTaskDto> bpmnList = repFileTypeConfigService.getBpmnTaskList(filetype.getId());
                    List<BpmnTaskVo> taskList = CodeUtils.JsonListToList(bpmnList,BpmnTaskVo.class);
                    if(!CommonUtil.isEmpty(taskList)) {
                        for(BpmnTaskVo bt : taskList) {
                            String approveType = bt.getApproveType();
                            if("singleSign".equals(approveType)){
                                bt.setApproveTypeName("单人审批");
                            } else if("vieSign".equals(approveType)){
                                bt.setApproveTypeName("竞争审批");
                            }else{
                                bt.setApproveTypeName("会签模式");
                            }
                        }
                    }

                    if(!CommonUtil.isEmpty(taskList) && taskList.size() > maxApprove) {
                        maxApprove = taskList.size();
                    }
                    vo.setList(taskList);
                    if("-1".equals(filetype.getStatus())){
                        vo.setStatus("拟制中");
                    }else if("1".equals(filetype.getStatus())){
                        vo.setStatus("启用");
                    }else{
                        vo.setStatus("禁用");
                    }

                    vo.setCustomAttr(idAttrMap.get(filetype.getId()));
                    vo.setRemark(idRemarkMap.get(filetype.getId()));
                    list.add(vo);
                }
            }
            String excelName = POIExcelUtil.createExcelName("true", RepFileTypeConfigConstants.EXPORT_TITLE, null);
            HSSFWorkbook workbook = export(list, maxApprove);
            POIExcelUtil.responseReportWithName(response, workbook, excelName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private HSSFWorkbook export(List<RepFileTypeConfigVo> list, int max) {
        if(CommonUtil.isEmpty(list)) {
            return null;
        }
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第一行表头字段
        List<String> headList0 = new LinkedList<String>();
        List<String> headList1 = new LinkedList<String>();
        List<String> mergeList = new ArrayList<String>();

        headList0.add(RepFileTypeConfigConstants.CODE);
        headList0.add(RepFileTypeConfigConstants.NAME);
        headList0.add(RepFileTypeConfigConstants.GENERATE_RULE_NAME);
        headList0.add(RepFileTypeConfigConstants.GENERATE_RULE_DESC);
        headList1.add("");
        headList1.add("");
        headList1.add("");
        headList1.add("");
        mergeList.add("0,1,0,0");
        mergeList.add("0,1,1,1");
        mergeList.add("0,1,2,2");
        mergeList.add("0,1,3,3");

        for(int i = 0 ; i < max; i++) {
            headList0.add(RepFileTypeConfigConstants.APPROVE_PROCESS+ (i+1));
            headList0.add("");
            headList0.add("");
            headList0.add("");
            headList0.add("");

            headList1.add(RepFileTypeConfigConstants.NAME);
            headList1.add(RepFileTypeConfigConstants.APPROVE_ROLE);
            headList1.add(RepFileTypeConfigConstants.APPROVE_WAY);
            headList1.add(RepFileTypeConfigConstants.APPROVE_SELECT_NUMBER);
            headList1.add(RepFileTypeConfigConstants.REMARK);

            int num = 4 + i * 5;
            mergeList.add("0,0," + num + "," + (num + 4));
        }

        headList0.add(RepFileTypeConfigConstants.CUSTOMATTR);
        headList0.add(RepFileTypeConfigConstants.STATUS);
        headList0.add(RepFileTypeConfigConstants.REMARK);
        headList1.add("");
        headList1.add("");
        headList1.add("");
        String[] excelHeader0 = new String[headList0.size()];
        headList0.toArray(excelHeader0);

        //第二行表头字段
        String[] excelHeader1 = new String[headList1.size()];
        headList1.toArray(excelHeader1);

        int length3 = excelHeader0.length -3;
        int length2 = excelHeader0.length -2;
        int length = excelHeader0.length -1;
        mergeList.add("0,1,"+ length3 + "," + length3);
        mergeList.add("0,1,"+ length2 + "," + length2);
        mergeList.add("0,1,"+ length + "," + length);
        String[] mergeNum= new String[mergeList.size()];
        mergeList.toArray(mergeNum);

        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(RepFileTypeConfigConstants.EXPORT_TITLE);
        // 设置表格默认列宽度
        sheet.setDefaultColumnWidth(20);
        sheet.setColumnWidth(excelHeader0.length, 11000);
        //生成样式
        HSSFCellStyle headerStyle = createHeaderStyle(workbook);
        HSSFCellStyle dataStyle = createDataStyle(workbook);
        //生成表格的第一行
        HSSFRow row = sheet.createRow(0);
        for(int i = 0; i < excelHeader0.length; i++) {
//            sheet.autoSizeColumn(i, true);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader0[i]);
            cell.setCellStyle(headerStyle);
        }

        //动态合并单元格
        for(int i = 0; i < mergeNum.length; i++) {
//            sheet.autoSizeColumn(i, true);
            String[] temp = mergeNum[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));

        }

        //第二行表头
        row = sheet.createRow(1);
        for(int i = 0; i < excelHeader1.length; i++) {
//            sheet.autoSizeColumn(i, true);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader1[i]);
            cell.setCellStyle(headerStyle);
        }

        //生成表格数据
        if(!CommonUtil.isEmpty(list)) {

            for(int i =0; i < list.size(); i++) {
                row = sheet.createRow(i + 2);
                RepFileTypeConfigVo vo = list.get(i);

                //导入对应列数据
                HSSFCell cell = row.createCell(0);
                cell.setCellValue(vo.getCode());
                cell.setCellStyle(dataStyle);

                HSSFCell cell1 = row.createCell(1);
                cell1.setCellValue(vo.getName());
                cell1.setCellStyle(dataStyle);

                HSSFCell cell2 = row.createCell(2);
                cell2.setCellValue(vo.getGenerateRuleName());
                cell2.setCellStyle(dataStyle);

                HSSFCell cell3 = row.createCell(3);
                cell3.setCellValue(vo.getGenerateRuleDesc());
                cell3.setCellStyle(dataStyle);

                //写入审批环节
                List<BpmnTaskVo> approveList = vo.getList();
                int startCol = 4;
                if(!CommonUtil.isEmpty(approveList)) {
                    for(BpmnTaskVo bt : approveList) {
                        HSSFCell cell4 = row.createCell(startCol);
                        cell4.setCellValue(bt.getName());
                        cell4.setCellStyle(dataStyle);
                        startCol++;
                        HSSFCell cell5 = row.createCell(startCol);
                        cell5.setCellValue(bt.getRoles());
                        cell5.setCellStyle(dataStyle);
                        startCol++;
                        HSSFCell cell6 = row.createCell(startCol);
                        cell6.setCellValue(bt.getApproveTypeName());
                        cell6.setCellStyle(dataStyle);
                        startCol++;
                        HSSFCell cell7 = row.createCell(startCol);
                        cell7.setCellValue(bt.getNumbers());
                        cell7.setCellStyle(dataStyle);
                        startCol++;
                        HSSFCell cell8 = row.createCell(startCol);
                        cell8.setCellValue(bt.getRemark());
                        cell8.setCellStyle(dataStyle);
                        startCol++;
                    }
                }

                for(int j = startCol; j < length3; j++) {
                    HSSFCell cellBlank = row.createCell(j);
                    cellBlank.setCellValue("");
                    cellBlank.setCellStyle(dataStyle);
                }

                HSSFCell lastCell3 = row.createCell(length3);
                lastCell3.setCellValue(vo.getCustomAttr());
                lastCell3.setCellStyle(dataStyle);

                HSSFCell lastCell2 = row.createCell(length2);
                lastCell2.setCellValue(vo.getStatus());
                lastCell2.setCellStyle(dataStyle);

                HSSFCell lastCell = row.createCell(length);
                lastCell.setCellValue(vo.getRemark());
                lastCell.setCellStyle(dataStyle);

            }
        }
        return workbook;
    }


    /**
     * 生成表头样式
     *
     * @param workbook
     * @return
     */
    private HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {
        // 设置表头字体 - 宋体、不加粗、12
        HSSFFont headerFont = generateHSSFFont(workbook, "宋体", false, 12);
        // 设置表头样式 - 居中、边框宽度1
        HSSFCellStyle headerStyle = generateHSSFCellStyle(workbook, headerFont,
                HSSFCellStyle.ALIGN_CENTER, new int[] {1, 1, 1, 1});

        // 只用前景色填充
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 自定义前景色索引
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex((short)9, (byte)217, (byte)217, (byte)217);

        // 设置前景色(使用上面定义的颜色)
        headerStyle.setFillForegroundColor((short)9);

        return headerStyle;
    }

    /**
     * 生成普通数据样式
     *
     * @param workbook
     * @return
     */
    private HSSFCellStyle createDataStyle(HSSFWorkbook workbook) {
        // 设置数据字体 - 宋体、不加粗、11
        HSSFFont dataFont = generateHSSFFont(workbook, "宋体", false, 11);
        // 设置数据样式 - 居中、边框宽度1
        HSSFCellStyle dataStyle = generateHSSFCellStyle(workbook, dataFont,
                HSSFCellStyle.ALIGN_CENTER, new int[] {1, 1, 1, 1});
        // 设置内容自动换行
        // dataStyle.setWrapText(true);

        return dataStyle;
    }


    /**
     * 创建简单HSSFFont的方法
     *
     * @param workbook
     * @param fontName
     *            设置字体
     * @param isBold
     *            设置是否加粗
     * @param fontSize
     *            设置字号
     * @return
     */
    private HSSFFont generateHSSFFont(HSSFWorkbook workbook, String fontName, boolean isBold, int fontSize) {
        HSSFFont font = workbook.createFont();
        font.setFontName(fontName);
        font.setBoldweight((short)(isBold ? 600 : 1));
        font.setFontHeightInPoints((short)fontSize);
        return font;
    }

    /**
     * 创建简单HSSFCellStyle的方法
     *
     * @param workbook
     * @param font
     *            字体
     * @param alignment
     *            对齐方式: 1-左对齐, 2-居中, 3-右对齐
     * @param border
     *            上右下左 - 边框宽度
     * @return
     */
    private HSSFCellStyle generateHSSFCellStyle(HSSFWorkbook workbook, HSSFFont font, int alignment, int[] border) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment((short)alignment);
        cellStyle.setBorderTop((short)border[0]);
        cellStyle.setBorderRight((short)border[1]);
        cellStyle.setBorderBottom((short)border[2]);
        cellStyle.setBorderLeft((short)border[3]);
        cellStyle.setFont(font);
        return cellStyle;
    }


}