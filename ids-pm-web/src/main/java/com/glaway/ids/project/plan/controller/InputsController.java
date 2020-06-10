package com.glaway.ids.project.plan.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.TempPlanInputsDto;
import com.glaway.ids.project.plan.service.DeliveryStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.InputsRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.NameStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;


/**
 * @Title: Controller
 * @Description: 交付物
 * @author blcao
 * @date 2016-03-30 09:11:17
 * @version V1.0
 */
@Controller
@RequestMapping("/inputsController")
public class InputsController extends BaseController {
    /**
     * Logger for this class
     */
    // private static final Logger logger = Logger.getLogger(DeliverablesInfoController.class);
    private static final OperationLog log = BaseLogFactory.getOperationLog(InputsController.class);


    /**
     * redis缓存服务
     */
    @Autowired
    private RedisService redisService;


    @Autowired
    private InputsRemoteFeignServiceI inputsService;

    /**
     * 项目计划管理接口
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;


    @Autowired
    private PlanRemoteFeignServiceI planService;

    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;

    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardRemoteFeignServiceI;


    @Autowired
    private FeignSystemService feignSystemService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;


    /**
     * 计划输入初始化时获取输入列表
     *
     * @param request
     * @param response
     * @see
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "list")
    public void list(InputsDto inputs, HttpServletRequest request, HttpServletResponse response) {
       /* String preposeIds = request.getParameter("preposeIds");
        Map<String, String> preposeMap = new HashMap<String, String>();
        List<Inputs> inputsList = new ArrayList<Inputs>();
        if (StringUtils.isNotEmpty(preposeIds)) {
            String[] preposeArr = preposeIds.split(",");
            for (String str : preposeArr) {
                preposeMap.put(str, str);
            }
        }
        List<Inputs> delInputs = new ArrayList<Inputs>();
        if (inputs != null && StringUtils.isNotEmpty(inputs.getUseObjectId())
            && StringUtils.isNotEmpty(inputs.getUseObjectType())) {
            List<Inputs> list = inputsService.queryInputsDetailList(inputs);
            for (Inputs in : list) {
                if (StringUtils.isNotEmpty(in.getOriginObjectId())
                    && StringUtils.isNotEmpty(preposeMap.get(in.getOriginObjectId()))) {
                    inputsList.add(in);
                }
                else {
                    delInputs.add(in);
                }
            }
        }
        // 删除被移除、或者前置变更所移除的输入
        if (!CommonUtil.isEmpty(delInputs)) {
            inputsService.deleteInputList(delInputs);
        }*/


        String useObjectId = request.getParameter("useObjectId");
        String inputStr = (String) redisService.getFromRedis("INPUTSLIST", useObjectId);
        List<InputsDto> inputsList = new ArrayList<>();
        if(!CommonUtil.isEmpty(inputStr)){
            inputsList = JSON.parseArray(inputStr,InputsDto.class);
        }

        if (CommonUtil.isEmpty(inputsList)) {
            if (inputs != null && StringUtils.isNotEmpty(inputs.getUseObjectId())
                    && StringUtils.isNotEmpty(inputs.getUseObjectType())) {
                List<InputsDto> list = inputsService.queryNewInputsList(inputs);
                String inpStr = JSON.toJSONString(list);
                if (!CommonUtil.isEmpty(list)) {
                    redisService.setToRedis("INPUTSLIST", inputs.getUseObjectId(), inpStr);
                }
            }
        }

        String inStr = (String) redisService.getFromRedis("INPUTSLIST", useObjectId);
        if(!CommonUtil.isEmpty(inStr)){
            inputsList = JSON.parseArray(inStr,InputsDto.class);
        }
        PlanDto p = planService.getPlanEntity(useObjectId);

        String libId = "";
        if (!CommonUtil.isEmpty(p)) {
            String projectId = p.getProjectId();
            FeignJson fj = projRoleService.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        } else {
            String projectId = request.getParameter("projectId");
            FeignJson fj = projRoleService.getLibIdByProjectId(projectId);
            libId = String.valueOf(fj.getObj());
        }


        Map<String, String> fileNameMap = new HashMap<String, String>();

        Map<String, String> filePathMap = new HashMap<String, String>();

        Map<String, String> fileIdMap = new HashMap<String, String>();

        if (!CommonUtil.isEmpty(libId)) {
            fileNameMap = inputsService.getRepFileNameAndBizIdMap(libId);

            filePathMap = inputsService.getRepFilePathAndBizIdMap(libId);

            fileIdMap = inputsService.getRepFileIdAndBizIdMap(libId);
        }


        if (!CommonUtil.isEmpty(inputsList)) {
            for (InputsDto input : inputsList) {
                if (!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("LOCAL")) {
                    input.setDocNameShow(input.getDocName());
                } else if (!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PROJECTLIBDOC")) {
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
                    if (!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))) {
                        input.setDocNameShow(fileNameMap.get(input.getDocId()));
                    }
                    if (!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))) {
                        input.setOriginPath(filePathMap.get(input.getDocId()));
                    }
                    if (!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))) {
                        input.setDocIdShow(fileIdMap.get(input.getDocId()));
                    }
                } else if (!CommonUtil.isEmpty(input.getOriginType()) && input.getOriginType().equals("PLAN")) {
                    PlanDto plan = planService.getPlanEntity(input.getOriginObjectId());
                    List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                    if (!CommonUtil.isEmpty(plan)) {
                        projDocRelationList = inputsService.getDocRelationList(plan, UserUtil.getInstance().getUser().getId());
                    }
                    ProjDocVo projDoc = new ProjDocVo();
                    if (!CommonUtil.isEmpty(projDocRelationList)) {
                        for (ProjDocVo vo : projDocRelationList) {
                            if (vo.getDeliverableId().equals(input.getOriginDeliverablesInfoId())) {
                                projDoc = vo;
                                break;
                            }
                        }
                    }
                    input.setOriginPath(plan.getPlanNumber() + "." + plan.getPlanName());
                    input.setDocId(projDoc.getDocId());
                    input.setDocNameShow(projDoc.getDocName());
                    input.setExt1(String.valueOf(projDoc.isDownload()));
                    input.setExt2(String.valueOf(projDoc.isHavePower()));
                    input.setExt3(String.valueOf(projDoc.isDetail()));
                }
            }
        }

        String json = "[]";
        String datagridStr = "";
        if (!CommonUtil.isEmpty(inputsList)) {
            /*String inpList = JSON.toJSONString(inputsList);
            redisService.setToRedis("INPUTSLIST", useObjectId, inpList);*/

           /* int rows = Integer.valueOf(request.getParameter("rows"));
            int page = Integer.valueOf(request.getParameter("page"));

            List<InputsDto> resList = new ArrayList<InputsDto>();
            int count = inputsList.size();

            if (count > page * rows) {
                resList = inputsList.subList((page - 1) * rows, page * rows);
            }
            else {
                resList = inputsList.subList((page - 1) * rows, inputsList.size());
            }

            DataGridReturn inpGrid = new DataGridReturn(inputsList.size(), resList);
            json = JSONObject.toJSONString(inpGrid);*/

            json = com.alibaba.fastjson.JSONArray.toJSONString(inputsList);
            datagridStr = "{\"rows\":" + json + ",\"total\":" + inputsList.size() + "}";
        }

        TagUtil.ajaxResponse(response, datagridStr);
    }


    @RequestMapping(params = "goAddInputs")
    public ModelAndView goAddInputs(HttpServletRequest req) {
        String useObjectId = req.getParameter("useObjectId");
        String useObjectType = req.getParameter("useObjectType");
        req.setAttribute("useObjectType", useObjectType);
        req.setAttribute("useObjectId", useObjectId);
        req.setAttribute("projectId", req.getParameter("projectId"));
        req.setAttribute("source", req.getParameter("source"));
        req.setAttribute("hideMoreShow", req.getParameter("hideMoreShow") == null ? "true" : "false");
        return new ModelAndView("com/glaway/ids/pm/project/plan/plan-selectDelivery");
    }


    /**
     * 新增任务
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "setInputs")
    @ResponseBody
    public AjaxJson setInputs(HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String ids = request.getParameter("ids");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        try {
            List<InputsDto> list = new ArrayList<>();
            String inputStr = (String) redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }

            if (CommonUtil.isEmpty(list)) {
                list = new ArrayList<InputsDto>();
            }

            for (String id : ids.split(",")) {
                DeliveryStandardDto delivery = nameStandardService.getDeliveryStandardEntity(id);
                InputsDto inputs = new InputsDto();
                inputs.setId(UUID.randomUUID().toString());
                inputs.setUseObjectId(useObjectId);
                inputs.setUseObjectType(useObjectType);
                inputs.setName(delivery.getName());
                inputs.setTempId(UUID.randomUUID().toString());
                inputs.setOriginObjectId(delivery.getId());
                list.add(inputs);
            }
            String inpStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId, inpStr);
        } catch (Exception e) {
            Object[] params = new Object[]{failMessage, ""};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        } finally {
            return j;
        }
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDelInputs")
    @ResponseBody
    public AjaxJson doBatchDelInputs(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.inputs.doBatchDelSuccess");
        String useObjectId = request.getParameter("useObjectId");
        try {
            List<InputsDto> list = new ArrayList<>();
            String inputStr = (String) redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }
            if (!CommonUtil.isEmpty(ids)) {
                for (String id : ids.split(",")) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getId().equals(id)) {
                            list.remove(i);
                        }
                    }
                    InputsDto in = inputsService.getInputEntity(id);
                    if (!CommonUtil.isEmpty(in)) {
                        inputsService.deleteInputs(in);
                    }
                }
            }
            String inpStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId, inpStr);
        } catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.inputs.doBatchDelFail");
            log.error(message, e, ids, "");
            Object[] params = new Object[]{message,
                    InputsDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        } finally {
            // systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 查看时、项目计划输入tab页
     *
     * @return
     */
    @RequestMapping(params = "goInputCheck")
    public ModelAndView goInputCheck(InputsDto inputs, HttpServletRequest req) {
        // 从修改页面或者查看页面切换到该TAB、直接用req.getParameter中的值
        req.setAttribute("inputs_", inputs);
        req.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        req.setAttribute("beTask", "false");
        req.setAttribute("projectId", req.getParameter("projectId"));
        return new ModelAndView("com/glaway/ids/project/plan/planInput-check");
    }

    /**
     * 项目计划页面查看初始化时获取输入
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "listView")
    @ResponseBody
    public void listView(InputsDto inputs, PlanDto plan, HttpServletRequest request,
                         HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Inputs", inputs);
        map.put("Plan", plan);
        map.put("projectId", projectId);
        map.put("userId", ResourceUtil.getCurrentUser().getId());
        FeignJson j = inputsService.listView(map);
        String json = "";
        if (j.isSuccess()) {
            json = j.getObj() == null ? "" : j.getObj().toString();
        }
        TagUtil.ajaxResponse(response, json);
    }


    @SuppressWarnings("finally")
    @RequestMapping(params = "setInputsForPlanChange")
    @ResponseBody
    public AjaxJson setInputsForPlanChange(HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String ids = request.getParameter("ids");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        try {
            List<TempPlanInputsDto> inputList = (List<TempPlanInputsDto>) request.getSession().getAttribute(
                    "planChange-inputList");


            if (CommonUtil.isEmpty(inputList)) {
                inputList = new ArrayList<TempPlanInputsDto>();
                ;
            }

            for (String id : ids.split(",")) {
                DeliveryStandardDto delivery = deliveryStandardRemoteFeignServiceI.getDeliveryStandardEntity(id);
                TempPlanInputsDto inputs = new TempPlanInputsDto();
                String uuid = UUID.randomUUID().toString();
                inputs.setId(uuid);
                inputs.setInputId(uuid);
                inputs.setUseObjectId(useObjectId);
                inputs.setUseObjectType(useObjectType);
                inputs.setName(delivery.getName());
                inputs.setOriginObjectId(delivery.getId());
//                inputs.setTempId(UUID.randomUUID().toString());
                inputList.add(inputs);
            }
            request.getSession().setAttribute("planChange-inputList", inputList);
        } catch (Exception e) {
            Object[] params = new Object[]{failMessage, ""};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        } finally {
            return j;
        }

    }


    @RequestMapping(params = "goAddLocalDoc")
    public ModelAndView goAddLocalDoc (HttpServletRequest req, HttpServletResponse resp){

        String useObjectId = req.getParameter("useObjectId");
        String useObjectType = req.getParameter("useObjectType");
        req.setAttribute("useObjectId", useObjectId);
        req.setAttribute("useObjectType", useObjectType);
        String projectId = req.getParameter("projectId");
        String deliverableId = req.getParameter("deliverableId");
        String folderId = "";
        if (StringUtil.isNotEmpty(deliverableId)) {
            List<ProjDocRelationDto> repFileList = projLibService.getDocRelation(deliverableId);
            if (!CommonUtil.isEmpty(repFileList)) {
                for (ProjDocRelationDto projDocRelation : repFileList) {
                    String docId = projDocRelation.getDocId();
                    ProjLibDocumentVo projLibDocumentVo = planService.getProjDocmentVoById(docId);
                    folderId = projLibDocumentVo.getParentId();
                }
            }
        } else {
            folderId = planService.getFoldIdByProjectId(projectId);
        }

        ProjLibDocumentVo parentVo = new ProjLibDocumentVo();
        String docNamePath = null;

        String userId = UserUtil.getInstance().getUser().getId();
//        String categoryFileAuths = projLibAuthService.getCategoryFileAuths(folderId, userId);
//        if (StringUtil.isNotEmpty(categoryFileAuths)) {
//            if (categoryFileAuths.contains("upload")) {
//                req.setAttribute("upload", true);
//            }
//            else {
//                req.setAttribute("upload", false);
//            }
//
//            if (categoryFileAuths.contains("download")) {
//                req.setAttribute("download", true);
//            }
//            else {
//                req.setAttribute("download", false);
//            }
//        }
//        else {
        req.setAttribute("upload", true);
        req.setAttribute("download", true);
//        }
        String dictCode = "secretLevel";

        short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();

        Map<String, List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> curType = tsMap.get(dictCode);
        List<Object> list = new ArrayList<Object>();
        // this.setTypeList(list);
        req.setAttribute("fileSecurityLevel", curType.get(0).getTypecode());

        ProjLibDocumentVo vo = new ProjLibDocumentVo();
        vo.setProjectId(projectId);
        vo.setPath(docNamePath);
        vo.setParentId(parentVo.getId());
        req.setAttribute("folderId", folderId);
        req.setAttribute("doc", vo);
        req.setAttribute("yanfa", "yanfa");
        // this.projLibDocumentlist.clear();
        return new ModelAndView("com/glaway/ids/project/plan/planInputsDoc-add");
    }

    @RequestMapping(params = "goProjTempAddDoc")
    public ModelAndView goProjTempAddDoc (HttpServletRequest req, HttpServletResponse resp){

        String projectId = req.getParameter("projectId");
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/projecttemplate/projTempInputsDoc-add");
    }

    /**
     * 新增任务
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @RequestMapping(params = "setPlmInputs")
    @ResponseBody
    public AjaxJson setPlmInputs(HttpServletRequest request) throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String[] docId = request.getParameter("docId").split(",");
        String[] name = request.getParameter("name").split(",");
        String[] fileType = request.getParameter("fileType").split(",");
        String[] versionCode = request.getParameter("versionCode").split(",");
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");

        try {
            List<InputsDto> list = new ArrayList<>();
            String inputStr = (String) redisService.getFromRedis("INPUTSLIST", useObjectId);
            if(!CommonUtil.isEmpty(inputStr)){
                list = JSON.parseArray(inputStr,InputsDto.class);
            }

            if (CommonUtil.isEmpty(list)) {
                list = new ArrayList<InputsDto>();
            }

            for (int i=0; i<docId.length; i++) {
                InputsDto inputs = new InputsDto();
                inputs.setId(UUID.randomUUID().toString());
                inputs.setUseObjectId(useObjectId);
                inputs.setUseObjectType(useObjectType);
                inputs.setTempId(UUID.randomUUID().toString());
                inputs.setDocId(docId[i]);
                inputs.setName(name[i]);
                inputs.setFileType(fileType[i]);
                inputs.setVersionCode(versionCode[i]);
                inputs.setOriginType("PLM");
                list.add(inputs);
            }
            String inpStr = JSON.toJSONString(list);
            redisService.setToRedis("INPUTSLIST", useObjectId, inpStr);
        } catch (Exception e) {
            Object[] params = new Object[]{failMessage, ""};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        } finally {
            return j;
        }
    }
}