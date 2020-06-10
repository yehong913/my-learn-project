package com.glaway.ids.project.plan.controller;


import com.alibaba.fastjson.JSON;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.constant.NameStandardSwitchConstants;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.constant.SwitchConstants;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 交付物
 * @author blcao
 * @date 2015-03-30 09:11:17
 * @version V1.0
 */
@Controller
@RequestMapping("/deliverablesInfoController")
public class DeliverablesInfoController extends BaseController {
    /**
     * Logger for this class
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(DeliverablesInfoController.class);



    /** 业务对象 */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    /**
     * 项目计划管理接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;
    
    /**
     * redis缓存服务
     */
    @Autowired
    private RedisService redisService;

    /**
     * 项目计划管理接口
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;


    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;


    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

    @Autowired
    private NameStandardDeliveryRelationRemoteFeignServiceI nameStandardDeliveryRelationService;


    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardService;


    @Autowired
    private FeignRepService repService;


    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;


    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkService;
    
    /**
     * repFile接口
     */
    @Autowired
    private FeignRepService repFileService;

    @Value(value="${spring.application.name}")
    private String appKey;

    /**
     * 项目计划页面初始化时获取项目库
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    public void list(DeliverablesInfoDto deliverablesInfo, String planName,
                     HttpServletRequest request, HttpServletResponse response) {
        if (deliverablesInfo != null && StringUtils.isNotEmpty(deliverablesInfo.getUseObjectId())
            && StringUtils.isNotEmpty(deliverablesInfo.getUseObjectType())) {
            PlanDto planDto = planService.getPlanEntity(deliverablesInfo.getUseObjectId());
            if("PLANTEMPLATE".equals(deliverablesInfo.getUseObjectType())){
                if(!CommonUtil.isEmpty(planDto)){
                    deliverablesInfo.setUseObjectType("PLAN");
                }else{
                    deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                }
            }

            List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
            long count = deliverablesInfoService.getCount(deliverablesInfo);
            String json = JsonUtil.getListJsonWithoutQuote(deliverablesInfoList2);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);
        }
    }


    /**
     * 是否在名称库中
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "pdName")
    @ResponseBody
    public AjaxJson pdName(String planName, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        NameStandardDto condition = new NameStandardDto();
        condition.setName(planName);
        List<NameStandardDto> nameStandardList = nameStandardService.searchNameStandards(condition);
        List<NameStandardDto> nameStandardList2 = new ArrayList<NameStandardDto>();
        for (NameStandardDto n : nameStandardList) {
            if (n.getName().equals(planName)) {
                nameStandardList2.add(n);
                break;
            }
        }
        List<NameStandardDto> nameStandardListTemp = new ArrayList<NameStandardDto>();
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(useObjectId);
        deliverablesInfo.setUseObjectType(useObjectType);
        List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        String pdNext = request.getParameter("pdNext");
        if ("1".equals(pdNext)) {
            if (deliverablesInfoList2.size() > 0) {
                for (DeliverablesInfoDto d : deliverablesInfoList2) {
                    if (PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD.equals(d.getOrigin())) {
                        deliverablesInfoService.deleteDeliverablesById(d.getId());
                    }
                }
            }
        }

        FeignJson fJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = String.valueOf(fJson.getObj());
        if (switchStr == null || switchStr == "null" || CommonUtil.isEmpty(switchStr)) {
            j.setMsg("没有勾选名称库");
        }
        else {
            if (!CommonUtil.isEmpty(nameStandardList2)) {
                for (NameStandardDto n : nameStandardList2) {
                    if ("启用".equals(n.getStopFlag())) {
                        nameStandardListTemp.add(n);
                        NameStandardDeliveryRelationDto relation = new NameStandardDeliveryRelationDto();
                        relation.setNameStandardId(n.getId());
                        List<NameStandardDeliveryRelationDto> list = nameStandardDeliveryRelationService.searchForPage(
                                relation, 1, 10);
                        DeliverablesInfoDto dto = new DeliverablesInfoDto();
                        dto.setOrigin(PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD);
                        dto.setUseObjectId(useObjectId);
                        dto.setUseObjectType(useObjectType);
                        List<DeliverablesInfoDto> deliverablesInfoList = deliverablesInfoService.queryDeliverableList(
                                deliverablesInfo, 1, 10, false);
                        String deliverName = "";
                        if(!CommonUtil.isEmpty(deliverablesInfoList)){
                            for(DeliverablesInfoDto deli : deliverablesInfoList){
                                if(CommonUtil.isEmpty(deliverName)){
                                    deliverName = deli.getName();
                                }else{
                                    deliverName = deliverName + "," +deli.getName();
                                }
                            }
                        }

                        if (!CommonUtil.isEmpty(list)) {
                            for (NameStandardDeliveryRelationDto t : list) {
                                if(!CommonUtil.isEmpty(t.getDeliveryStandardId())) {
                                    t.setDeliveryStandard(deliveryStandardService.getDeliveryStandardEntity(t.getDeliveryStandardId()));  
                                }
                                if (StringUtils.isNotEmpty(t.getDeliveryStandardId()) && !deliverName.contains(t.getDeliveryStandard().getName())
                                        && t.getDeliveryStandard()!=null && !"禁用".equals(t.getDeliveryStandard().getStopFlag())) {
                                    DeliverablesInfoDto deliverablesInfoTemp = new DeliverablesInfoDto();
                                    String deliStr = deliverablesInfoService.initDeliverablesInfo(deliverablesInfoTemp);
                                    deliverablesInfoTemp = JSON.parseObject(JsonFromatUtil.formatJsonToList(deliStr),DeliverablesInfoDto.class);
                                    deliverablesInfoTemp.setId(null);
                                    deliverablesInfoTemp.setName(t.getDeliveryStandard().getName());
                                    deliverablesInfoTemp.setUseObjectId(useObjectId);
                                    deliverablesInfoTemp.setUseObjectType(useObjectType);
                                    if (!NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                                        deliverablesInfoTemp.setOrigin(PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD);
                                    }
                                    deliverablesInfoService.saveDeliverablesInfo(deliverablesInfoTemp);
                                }
                            }
                        }
                    }
                }
            }

            if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                    && !switchStr.equals(NameStandardSwitchConstants.DELIVERABLEUPATEABLE)) {
                if (!CommonUtil.isEmpty(nameStandardListTemp)) {
                    request.setAttribute("pdName", planName);
                    j.setMsg("在项目名称库中");
                }
            }
            else if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)) {
                if (!CommonUtil.isEmpty(nameStandardListTemp)) {
                    request.setAttribute("pdName", planName);
                    j.setMsg("在项目名称库中！");
                }
                else {
                    j.setMsg("没有勾选名称库");
                }
            }
            else if (NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                if (!CommonUtil.isEmpty(nameStandardListTemp)) {
                    j.setMsg("可以修改");
                }
            }
        }
        return j;
    }


    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        return new ModelAndView("com/glaway/ids/pm/project/plan/planDocument-add");
    }


    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridlist")
    @ResponseBody
    public void datagridlist(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getParameter("method");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

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

        int rows = Integer.valueOf(request.getParameter("rows"));
        int page = Integer.valueOf(request.getParameter("page"));


        List<DeliveryStandardDto> list = deliveryStandardService.searchUseableDeliveryStandards(condition);
        List<DeliveryStandardDto> list2 = new ArrayList<DeliveryStandardDto>();
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(useObjectId);
        deliverablesInfo.setUseObjectType(useObjectType);
        List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        if (CommonUtil.isEmpty(deliverablesInfoList2)) {
            list2.addAll(list);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (DeliverablesInfoDto deliver : deliverablesInfoList2) {
                nameMap.put(deliver.getName(), deliver.getName());
            }
            for (DeliveryStandardDto deliveryStandard : list) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    list2.add(deliveryStandard);
                }
            }
        }

        int count = list2.size();
        List<DeliveryStandardDto> resList = new ArrayList<DeliveryStandardDto>();
        if (count > page * rows) {
            resList = list2.subList((page - 1) * rows, page * rows);
        }
        else {
            resList = list2.subList((page - 1) * rows, list2.size());
        }


        String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
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
     * 获取交付物名称库列表(计划变更)
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridlistForPlanChange")
    @ResponseBody
    public void datagridlistForPlanChange(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getParameter("method");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        DeliveryStandardDto condition = new DeliveryStandardDto();
        if (method != null && method.equals("search")) {
            condition.setNo(request.getParameter("no"));
            condition.setName(request.getParameter("name"));

            String stopFlag = request.getParameter("stopFlag");
            if(!CommonUtil.isEmpty(stopFlag)) {
                if (stopFlag.equals("START")) {
                    condition.setStopFlag(ConfigStateConstants.START);
                } else if (stopFlag.equals("STOP")) {
                    condition.setStopFlag(ConfigStateConstants.STOP);
                }
            }

        }

        int rows =10;
        int page = 1;
        if(!CommonUtil.isEmpty(request.getParameter("rows")) && !CommonUtil.isEmpty(request.getParameter("page"))) {
            rows = Integer.valueOf(request.getParameter("rows"));
            page = Integer.valueOf(request.getParameter("page"));
        }

        Map<String,String> isExistsMap = new HashMap<String,String>();
        List<TempPlanDeliverablesInfoDto> deliverablesInfoList = null;
        if(!CommonUtil.isEmpty(request.getSession().getAttribute("planChange-deliverablesInfoList"))) {
            deliverablesInfoList = (List<TempPlanDeliverablesInfoDto>) request.getSession().getAttribute(
                    "planChange-deliverablesInfoList");
            for(TempPlanDeliverablesInfoDto dto : deliverablesInfoList){
                isExistsMap.put(dto.getName(),dto.getName());
            }
        }

        List<DeliveryStandardDto> list = deliveryStandardService.searchUseableDeliveryStandards(condition);
        List<DeliveryStandardDto> list2 = new ArrayList<DeliveryStandardDto>();
        for (DeliveryStandardDto deliveryStandard : list) {
            if (CommonUtil.isEmpty(isExistsMap.get(deliveryStandard.getName()))) {
                list2.add(deliveryStandard);
            }
        }

        int count = list2.size();
        List<DeliveryStandardDto> resList = new ArrayList<DeliveryStandardDto>();
        if (count > page * rows) {
            resList = list2.subList((page - 1) * rows, page * rows);
        }
        else {
            resList = list2.subList((page - 1) * rows, list2.size());
        }


        String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
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
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(String names, DeliverablesInfoDto deliverablesInfo,
                          HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        deliverablesInfo.setName(request.getParameter("name"));
        String failMessage = "";
        String failMessageCode = "";
        deliverablesInfo.setId(null);
        String message = "";
        try {
            deliverablesInfoService.doAddDelDeliverForWork(names, deliverablesInfo);
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
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridSearchlist")
    @ResponseBody
    public void datagridSearchlist(HttpServletRequest request, HttpServletResponse response) {
        DeliveryStandardDto condition = new DeliveryStandardDto();
        condition.setNo(request.getParameter("no"));
        condition.setName(request.getParameter("name"));
        condition.setStopFlag(ConfigStateConstants.START);
        condition.setAvaliable("1");
        List<DeliveryStandardDto> list = deliveryStandardService.searchDeliveryStandardAccurate(condition);

        List<DeliveryStandardDto> list2 = new ArrayList<DeliveryStandardDto>();
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(request.getParameter("useObjectId"));
        deliverablesInfo.setUseObjectType(request.getParameter("useObjectType"));
        List<DeliverablesInfoDto> deliverablesInfoList2 = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);
        if (CommonUtil.isEmpty(deliverablesInfoList2)) {
            list2.addAll(list);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (DeliverablesInfoDto deliver : deliverablesInfoList2) {
                nameMap.put(deliver.getName(), deliver.getName());
            }
            for (DeliveryStandardDto deliveryStandard : list) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    list2.add(deliveryStandard);
                }
            }
        }

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
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.doBatchDelSuccess");
        try {
            deliverablesInfoService.doBatchDelDeliverForWork(ids);
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
     * 更新交付物
     *
     * @param deliverablesInfo
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "update")
    @ResponseBody
    public FeignJson update(DeliverablesInfoDto deliverablesInfo, HttpServletRequest request) {
        FeignJson j = deliverablesInfoService.updateDeliverablesInfo(deliverablesInfo);
        return j;
    }

    /**
     * 通过PLM更新交付物
     *
     * @param deliverablesInfo
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "updateByPlm")
    @ResponseBody
    public FeignJson updateByPlm(DeliverablesInfoDto deliverablesInfo, HttpServletRequest request) {
        FeignJson j = deliverablesInfoService.updateDeliverablesInfoByPlm(deliverablesInfo);
        return j;
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "updateInputs")
    @ResponseBody
    public AjaxJson updateInputs(HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String fileId = request.getParameter("fileId");
        String rowId = request.getParameter("rowId");
        String folderId = request.getParameter("folderId");
        String projectId = request.getParameter("projectId");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        try {
            List<InputsDto> list = new ArrayList<>();
            String inputStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }

            if(CommonUtil.isEmpty(list)){
                list = new ArrayList<InputsDto>();;
            }

            List<RepFileDto> repList = new ArrayList<RepFileDto>();
            RepFileDto rep = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(), fileId);
            repList.add(rep);

            List<ProjLibDocumentVo> voList = projLibService.getRepList(fileId,folderId,projectId,ResourceUtil.getCurrentUser().getId());

            if(!CommonUtil.isEmpty(list)){
                for(InputsDto input :list){
                    if(!CommonUtil.isEmpty(input.getId()) && input.getId().equals(rowId)){
                        if(!CommonUtil.isEmpty(voList)){
                            input.setDocName(voList.get(0).getDocName());
//                                input.setOriginPath(voList.get(0).getPath());
                            input.setOriginType("PROJECTLIBDOC");
                            input.setDocId(voList.get(0).getBizId());
                            input.setExt1(String.valueOf(voList.get(0).isDownload()));
                            input.setExt2(String.valueOf(voList.get(0).isHistory()));
                            input.setExt3(String.valueOf(voList.get(0).isDetail()));
                        }
                    }
                }
            }

            String inpStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId,inpStr);
        }
        catch (Exception e) {
            Object[] params = new Object[] {failMessage, ""};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            return j;
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
        req.setAttribute("deliverablesInfo_", deliverablesInfo);
        req.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        return new ModelAndView("com/glaway/ids/project/plan/planDocument-check");
    }

    /**
     * 项目计划页面查看初始化时获取项目库
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "listView")
    @ResponseBody
    public void listView(DeliverablesInfoDto deliverablesInfo, PlanDto plan, HttpServletRequest request,
                         HttpServletResponse response) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.listViewSuccess");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("DeliverablesInfoDto",deliverablesInfo);
        map.put("PlanDto",plan);
        map.put("userId",ResourceUtil.getCurrentUser().getId());

        try {
            FeignJson j = deliverablesInfoService.listView(map);
            String json = "";
            if (j.isSuccess()) {
                json = j.getObj() == null ? "" : j.getObj().toString();
            }
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.listViewFail");
            Object[] params = new Object[] {message,
                    PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
    }
    
    /**
     * 是否在名称库中
     * 
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "pdNameForOther")
    @ResponseBody
    public AjaxJson pdNameForOther(String planName, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        NameStandardDto condition = new NameStandardDto();
        condition.setName(planName);
        List<NameStandardDto> nameStandardList = nameStandardService.searchNameStandards(condition);
        if(!CommonUtil.isEmpty(nameStandardList)){
            j.setSuccess(true);
        }else{
            j.setSuccess(false);
        }
        return j;
    }
    
    /**
     * 是否在名称库中
     * 
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "pdNameForOthers")
    @ResponseBody
    public AjaxJson pdNameForOthers(String planName, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String[] cellName = request.getParameter("cellName").split(",");
        for (int i = 0; i < cellName.length; i++ ) {
            NameStandardDto condition = new NameStandardDto();
            condition.setName(cellName[i]);
            List<NameStandardDto> nameStandardList = nameStandardService.searchNameStandards(condition);
            if (!CommonUtil.isEmpty(nameStandardList)) {
                j.setSuccess(true);
            }
            else {
                j.setSuccess(false);
                j.setMsg("【"+cellName[i] + "】不在活动名称库中，请重新填写");
                return j;
            }
        }
        return j;
    }


    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddInheritForTemplate")
    public ModelAndView goAddInheritForTemplate(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        String planIdForInherit = req.getParameter("planId");
        String parentPlanIdForInherit = req.getParameter("parentPlanId");
        String useObjectId = req.getParameter("useObjectId");
        String useObjectType = req.getParameter("useObjectType");
        req.getSession().setAttribute("planIdForInherit", planIdForInherit);
        req.getSession().setAttribute("parentPlanIdForInherit", parentPlanIdForInherit);
        req.setAttribute("useObjectId", useObjectId);
        req.setAttribute("useObjectType", useObjectType);
        req.setAttribute("planTemplateId", req.getParameter("planTemplateId"));
        req.setAttribute("projectTemplateId", req.getParameter("projectTemplateId"));
        return new ModelAndView("com/glaway/ids/project/plan/planTemplateInheritDocument-add");
    }

    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddInherit")
    public ModelAndView goAddInherit(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        String planIdForInherit = req.getParameter("planId");
        String parentPlanIdForInherit = req.getParameter("parentPlanId");
        String useObjectId = req.getParameter("useObjectId");
        String useObjectType = req.getParameter("useObjectType");
        req.getSession().setAttribute("planIdForInherit", planIdForInherit);
        req.getSession().setAttribute("parentPlanIdForInherit", parentPlanIdForInherit);
        req.setAttribute("useObjectId", useObjectId);
        req.setAttribute("useObjectType", useObjectType);
        return new ModelAndView("com/glaway/ids/project/plan/planInheritDocument-add");
    }


    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridInheritlistForTemplate")
    @ResponseBody
    public void datagridInheritlistForTemplate(HttpServletRequest request, HttpServletResponse response) {
        String planIdForInherit = (String)request.getSession().getAttribute("planIdForInherit");
        String parentPlanIdForInherit = (String)request.getSession().getAttribute("parentPlanIdForInherit");
        String planTemplateId = request.getParameter("planTemplateId");
        String projectTemplateId = request.getParameter("projectTemplateId");
        PlanDto plan = planService.getPlanEntity(planIdForInherit);
        if(plan == null){
            plan = new PlanDto();
            plan.setId(planIdForInherit);
            plan.setParentPlanId(parentPlanIdForInherit);
        }
        List<DeliverablesInfoDto> deliverableslist = new ArrayList<DeliverablesInfoDto>();
        List<DeliverablesInfoDto> deliverablesParentlist = new ArrayList<DeliverablesInfoDto>();
        List<PlanDto> allPlan = new ArrayList<PlanDto>();
        if(!CommonUtil.isEmpty(planTemplateId)){
            deliverableslist = deliverablesInfoService.getDeliverablesByUseObeject(
                    "PLANTEMPLATE", planIdForInherit);
            deliverablesParentlist = deliverablesInfoService.getDeliverablesByUseObeject(
                    "PLANTEMPLATE", plan.getParentPlanId());
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", planTemplateId);

            if(!CommonUtil.isEmpty(detailStr)){
                allPlan = JSON.parseArray(detailStr,PlanDto.class);
            }
        }else{
            deliverableslist = deliverablesInfoService.getDeliverablesByUseObeject(
                    "PROJECTTEMPLATE", planIdForInherit);
            deliverablesParentlist = deliverablesInfoService.getDeliverablesByUseObeject(
                    "PROJECTTEMPLATE", plan.getParentPlanId());
            String detailStr = (String)redisService.getFromRedis("TEMPLATEPLANLIST", projectTemplateId);

            if(!CommonUtil.isEmpty(detailStr)){
                allPlan = JSON.parseArray(detailStr,PlanDto.class);
            }
        }

        List<DeliverablesInfoDto> list = new ArrayList<DeliverablesInfoDto>();

        PlanDto parentPlan = planService.getPlanEntity(plan.getParentPlanId());
        List<PlanDto> planList = new ArrayList<PlanDto>();


        if (CommonUtil.isEmpty(deliverableslist)) {
            list.addAll(deliverablesParentlist);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (DeliverablesInfoDto deliver : deliverableslist) {
                nameMap.put(deliver.getName(), deliver.getName());
            }
            for (DeliverablesInfoDto deliveryStandard : deliverablesParentlist) {
                if (StringUtils.isEmpty(nameMap.get(deliveryStandard.getName()))) {
                    list.add(deliveryStandard);
                }
            }
        }

        if(!CommonUtil.isEmpty(allPlan)){
            for(PlanDto pl : allPlan){
                if(!CommonUtil.isEmpty(pl.getParentPlanId()) && pl.getParentPlanId().equals(parentPlanIdForInherit)){
                    planList.add(pl);
                }
            }
        }

        List<DeliverablesInfoDto> list2 = new ArrayList<DeliverablesInfoDto>();
        for (PlanDto p : planList) {
            if (!p.getId().equals(plan.getId()) && !p.getId().equals(plan.getParentPlanId())) {
                List<DeliverablesInfoDto> deliverableslistTemp = new ArrayList<DeliverablesInfoDto>();
                if(!CommonUtil.isEmpty(planTemplateId)){
                    deliverableslistTemp = deliverablesInfoService.getDeliverablesByUseObeject(
                            "PLANTEMPLATE", p.getId());
                }else{
                    deliverableslistTemp = deliverablesInfoService.getDeliverablesByUseObeject(
                            "PROJECTTEMPLATE", p.getId());
                }
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
        String parentPlanIdForInherit = (String)request.getSession().getAttribute("parentPlanIdForInherit");
        PlanDto plan = planService.getPlanEntity(planIdForInherit);
        if(plan == null){
            plan = new PlanDto();
            plan.setId(planIdForInherit);
            plan.setParentPlanId(parentPlanIdForInherit);
        }
        List<DeliverablesInfoDto> deliverableslist = deliverablesInfoService.getDeliverablesByUseObeject(
                PlanConstants.USEOBJECT_TYPE_PLAN, planIdForInherit);
        List<DeliverablesInfoDto> deliverablesParentlist = deliverablesInfoService.getDeliverablesByUseObeject(
                PlanConstants.USEOBJECT_TYPE_PLAN, plan.getParentPlanId());
        List<DeliverablesInfoDto> list = new ArrayList<DeliverablesInfoDto>();

        PlanDto parentPlan = planService.getPlanEntity(plan.getParentPlanId());
        List<PlanDto> planList = planService.getPlanAllChildren(parentPlan);

        if (CommonUtil.isEmpty(deliverableslist)) {
            list.addAll(deliverablesParentlist);
        }
        else {
            Map<String, String> nameMap = new HashMap<String, String>();
            for (DeliverablesInfoDto deliver : deliverableslist) {
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
    @RequestMapping(params = "doAddInheritlist")
    @ResponseBody
    public AjaxJson doAddInheritlist(String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planIdForInherit = (String)request.getSession().getAttribute("planIdForInherit");
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            planFlowForworkService.doAddInheritlist(names, planIdForInherit,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
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
     * 新增交付物
     *
     * @param names
     * @param deliverablesInfo
     * @param request
     * @return
     * @see
     */
   /* @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(String names, DeliverablesInfoDto deliverablesInfo,
                          HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        deliverablesInfo.setName(request.getParameter("name"));
        String failMessage = "";
        String failMessageCode = "";
        deliverablesInfo.setId(null);
        String message = "";
        try {
            planFlowForworkService.doAddDelDeliverForWork(names, deliverablesInfo);
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
    }*/


    /**
     * 新增继承交付物
     *
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInheritlistForTemplate")
    @ResponseBody
    public AjaxJson doAddInheritlistForTemplate(String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planIdForInherit = (String)request.getSession().getAttribute("planIdForInherit");
        String useObjectType = request.getParameter("useObjectType");
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            planFlowForworkService.doAddInheritlistForTemplate(names, planIdForInherit,useObjectType,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
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
     * 项目计划页面查看初始化时获取项目库
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "templateDeliverablesListView")
    @ResponseBody
    public void templateDeliverablesListView(DeliverablesInfoDto deliverablesInfo, PlanDto plan, HttpServletRequest request,
                                             HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.listViewSuccess");
        try {
            if (deliverablesInfo != null
                    && StringUtils.isNotEmpty(deliverablesInfo.getUseObjectId())
                    && StringUtils.isNotEmpty(deliverablesInfo.getUseObjectType())) {
                plan.setId(deliverablesInfo.getUseObjectId());
                PlanDto planDto = planService.getPlanEntity(deliverablesInfo.getUseObjectId());
                if(!CommonUtil.isEmpty(planDto)){
                    deliverablesInfo.setUseObjectType("PLAN");
                }else{
                    if (CommonUtil.isEmpty(deliverablesInfo.getUseObjectType())) {
                        deliverablesInfo.setUseObjectType("PLANTEMPLATE");
                    }
                }
//                plan.setProjectId(newProjectId);
            }

            List<ProjDocVo> projDocRelationList = getDocRelationListForTemplate(plan,deliverablesInfo);
            if (projDocRelationList == null) {
                projDocRelationList = new ArrayList<ProjDocVo>();
            }
            String json = gson.toJson(projDocRelationList);
            TagUtil.ajaxResponse(response, json);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deliverables.listViewFail");
            Object[] params = new Object[] {message,
                    PlanDto.class.getClass() + " oids:" + plan.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
    }


    /**
     * 项目计划页面初始化时获取项目库
     *
     * @param plan
     * @see
     */
    public List<ProjDocVo> getDocRelationListForTemplate(PlanDto plan,DeliverablesInfoDto deli) {
        String userId = UserUtil.getCurrentUser().getId();
        // 通过交付项判断子计划是否包括项目库
        // 获取计划的输出
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
/*        if(!CommonUtil.isEmpty(plan.getProjectId()) && !"undefined".equals(plan.getProjectId())){
            deliverablesInfo.setUseObjectType("PLAN");
        }else{
            deliverablesInfo.setUseObjectType("PROJECTTEMPLATE");
        }*/
        deliverablesInfo.setUseObjectType(deli.getUseObjectType());

        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);

        if (CommonUtil.isEmpty(deliverablesList)) {
            return null;
        }

        // 获取其子计划的所有输出、并将其存入deliverablesMap
        Map<String, Object> deliverablesMap = new HashMap<String, Object>();
        PlanDto childPlan = new PlanDto();
        childPlan.setParentPlanId(plan.getId());
        List<PlanDto> childList = planService.queryPlanList(childPlan, 1, 10, false);

        for (PlanDto child : childList) {
            deliverablesInfo = new DeliverablesInfoDto();
            deliverablesInfo.setUseObjectId(child.getId());
            deliverablesInfo.setUseObjectType("PLANTEMPLATE");
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
                ProjDocRelationDto projDocRelation = (ProjDocRelationDto) deliverablesMap.get(parentDeli.getName());
                convertToVo(projDocVo, projDocRelation, plan.getId(),userId);
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                projDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
                List<ProjDocRelationDto> projDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
                if (!CommonUtil.isEmpty(projDocRelationDbList)) {
                    convertToVo(projDocVo, projDocRelationDbList.get(0), plan.getId(),userId);
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
                                  String planId,String userId) {
        if (StringUtils.isEmpty(projDocRelation.getDocId())) {
            return projDocVo;
        }
        projDocVo.setDocId(projDocRelation.getDocId());
        String havePower = planService.getOutPowerForPlanList(projDocRelation.getDocId(), planId, userId);
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
        if (projDocRelation.getRepFile() == null && !CommonUtil.isEmpty(projDocRelation.getDocId())) {
            RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, projDocRelation.getDocId());  
            projDocRelation.setRepFile(rep);
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


    @SuppressWarnings("finally")
    @RequestMapping(params = "setPlanInputs")
    @ResponseBody
    public AjaxJson setPlanInputs(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String planId = request.getParameter("planId");
        String tempId = request.getParameter("tempId");
        String useObjectId = request.getParameter("useObjectId");
        String inputsName = request.getParameter("inputsName");
        try{
            List<InputsDto> list = new ArrayList<>();
            String detailStr = (String)redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(detailStr)){
                list = JSON.parseArray(detailStr,InputsDto.class);
            }

            PlanDto plan = planService.getPlanEntity(planId);
            List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
            /*List<DeliverablesInfo> deliverableInfoList = deliverablesInfoService.getDeliverablesByUseObeject("PLAN", plan.getId());
            DeliverablesInfo info = new DeliverablesInfo();
            if(!CommonUtil.isEmpty(deliverableInfoList)){
                for(DeliverablesInfo delivery : deliverableInfoList){
                    if(delivery.getName().equals(inputsName)){
                        info = delivery;
                        break;
                    }
                }
            }*/
            ProjDocVo projDoc = new ProjDocVo();
            if(!CommonUtil.isEmpty(projDocRelationList)){
                for(ProjDocVo vo:projDocRelationList){
                    if(vo.getDeliverableName().equals(inputsName)){
                        projDoc = vo;
                        break;
                    }
                }
            }

            if(!CommonUtil.isEmpty(list)){
                for(InputsDto input :list){
                    if(!CommonUtil.isEmpty(input.getId()) && input.getId().equals(tempId)){
                        if(!CommonUtil.isEmpty(projDoc)){
                            input.setDocName(projDoc.getDocName());
//                            input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                            input.setOriginType("PLAN");
                            input.setDocId(projDoc.getDocId());
                            input.setOriginObjectId(plan.getId());
                            input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                            input.setExt1(String.valueOf(projDoc.isDownload()));
                            input.setExt2(String.valueOf(projDoc.isHavePower()));
                            input.setExt3(String.valueOf(projDoc.isDetail()));
                        }
                    }
                }
            }

            String listStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId,listStr);

        }catch(Exception e){

        }finally{
            return j;
        }
    }


    /**
     * 项目计划页面初始化时获取项目库
     *
     * @param plan
     * @see
     */
    public List<ProjDocVo> getDocRelationList(PlanDto plan) {
        String userId = UserUtil.getCurrentUser().getId();
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
        List<PlanDto> childList = planService.queryPlanList(childPlan, 1, 10, false);

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
                ProjDocRelationDto projDocRelation = (ProjDocRelationDto) deliverablesMap.get(parentDeli.getName());
                convertToVo(projDocVo, projDocRelation, plan.getId(),userId);
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                projDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
                List<ProjDocRelationDto> projDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
                if (!CommonUtil.isEmpty(projDocRelationDbList)) {
                    convertToVo(projDocVo, projDocRelationDbList.get(0), plan.getId(),userId);
                }
                projDocVo.setOpFlag(true);
                projDocRelationList.add(projDocVo);
            }
        }

        return projDocRelationList;
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "updateInputsForPlanChange")
    @ResponseBody
    public AjaxJson updateInputsForPlanChange(HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String fileId = request.getParameter("fileId");
        String rowId = request.getParameter("rowId");
        String folderId = request.getParameter("folderId");
        String projectId = request.getParameter("projectId");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        try {
            List<TempPlanInputsDto> list = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");

            if(CommonUtil.isEmpty(list)){
                list = new ArrayList<TempPlanInputsDto>();;
            }

            List<RepFileDto> repList = new ArrayList<RepFileDto>();
            RepFileDto rep = repService.getRepFileByRepFileId(ResourceUtil.getApplicationInformation().getAppKey(),fileId);
            repList.add(rep);

            List<ProjLibDocumentVo> voList = projLibService.getRepList(fileId,folderId,projectId,ResourceUtil.getCurrentUser().getId());

            if(!CommonUtil.isEmpty(list)){
                for(TempPlanInputsDto input :list){
                    if(!CommonUtil.isEmpty(input.getId()) && input.getId().equals(rowId)){
                        if(!CommonUtil.isEmpty(voList)){
                            input.setDocName(voList.get(0).getDocName());
//                                input.setOriginPath(voList.get(0).getPath());
                            input.setOriginType("PROJECTLIBDOC");
                            input.setDocId(voList.get(0).getBizId());
                            input.setExt1(String.valueOf(voList.get(0).isDownload()));
                            input.setExt2(String.valueOf(voList.get(0).isHistory()));
                            input.setExt3(String.valueOf(voList.get(0).isDetail()));
                        }
                    }
                }
            }




            request.getSession().setAttribute("planChange-inputList", list);
        }
        catch (Exception e) {
            Object[] params = new Object[] {failMessage, ""};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            return j;
        }
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "setPlanInputsForPlanChange")
    @ResponseBody
    public AjaxJson setPlanInputsForPlanChange(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String planId = request.getParameter("planId");
        String tempId = request.getParameter("tempId");
        String useObjectId = request.getParameter("useObjectId");
        String inputsName = request.getParameter("inputsName");
        try{
            List<TempPlanInputsDto> list = (List<TempPlanInputsDto>)request.getSession().getAttribute(
                    "planChange-inputList");

            PlanDto plan = planService.getPlanEntity(planId);
            List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
            /*List<DeliverablesInfo> deliverableInfoList = deliverablesInfoService.getDeliverablesByUseObeject("PLAN", plan.getId());
            DeliverablesInfo info = new DeliverablesInfo();
            if(!CommonUtil.isEmpty(deliverableInfoList)){
                for(DeliverablesInfo delivery : deliverableInfoList){
                    if(delivery.getName().equals(inputsName)){
                        info = delivery;
                        break;
                    }
                }
            }*/
            ProjDocVo projDoc = new ProjDocVo();
            if(!CommonUtil.isEmpty(projDocRelationList)){
                for(ProjDocVo vo:projDocRelationList){
                    if(vo.getDeliverableName().equals(inputsName)){
                        projDoc = vo;
                        break;
                    }
                }
            }

            if(!CommonUtil.isEmpty(list)){
                for(TempPlanInputsDto input :list){
                    if(!CommonUtil.isEmpty(input.getId()) && input.getId().equals(tempId)){
                        if(!CommonUtil.isEmpty(projDoc)){
                            input.setDocName(projDoc.getDocName());
//                            input.setOriginPath(plan.getPlanNumber()+"."+plan.getPlanName());
                            input.setOriginType("PLAN");
                            input.setDocId(projDoc.getDocId());
                            input.setOriginObjectId(plan.getId());
                            input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                            input.setExt1(String.valueOf(projDoc.isDownload()));
                            input.setExt2(String.valueOf(projDoc.isHavePower()));
                            input.setExt3(String.valueOf(projDoc.isDetail()));
                        }
                    }
                }
            }

            request.getSession().setAttribute("planChange-inputList", list);

        }catch(Exception e){

        }finally{
            return j;
        }
    }


}
