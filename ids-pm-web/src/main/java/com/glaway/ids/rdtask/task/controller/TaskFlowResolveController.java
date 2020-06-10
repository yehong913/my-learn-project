package com.glaway.ids.rdtask.task.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.activiti.core.dto.MyStartedTaskDto;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.common.UploadFile;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.outwardextension.OutwardExtensionDto;
import com.glaway.foundation.fdk.dev.dto.outwardextension.OutwardExtensionUrlDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileTypeDto;
import com.glaway.foundation.fdk.dev.service.FeignOutwardExtensionService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.calendar.FeignCalendarService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.jackrabbit.util.JackrabbitUtil;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.foundation.system.serial.SerialNumberManager;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.*;
import com.glaway.ids.common.service.PluginValidateServiceI;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.*;
import com.glaway.ids.models.JsonRequery;
import com.glaway.ids.models.JsonResult;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.projectmanager.dto.*;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.rdtask.task.service.ApplyProcTemplateRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowWebRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.TaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.*;
import com.glaway.ids.util.CodeUtils;
import com.glaway.ids.util.CommonInitUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jodd.servlet.URLDecoder;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jasypt.commons.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * @Title: Controller
 * @Description: 任务流程分解
 * @author blcao
 * @date 2015-06-18 15:08:40
 * @version V1.0
 */
@Controller
@RequestMapping("/taskFlowResolveController")
public class TaskFlowResolveController extends BaseController {
    private static final OperationLog log = BaseLogFactory.getOperationLog(TaskFlowResolveController.class);

    /**
     * 用户管理接口
     */
    @Autowired
    private FeignUserService userService;

    /**
     * 用户部门接口
     */
    @Autowired
    private FeignDepartService deptService;


    /**
     * 项目计划管理接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;

    /**
     * 计划前置接口
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;

//    @Autowired
//    private OutwardExtensionServiceI outwardExtensionService;

    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    /**
     * 项目计划参数接口
     */
    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;

    /**
     * 流程模板接口
     */
    @Autowired
    private TaskFlowResolveRemoteFeignServiceI taskFlowResolveService;

    /**
     * 流程模板接口
     */
    @Autowired
    private RdFlowTaskFlowResolveRemoteFeignServiceI rdFlowTaskFlowResolveService;

    /**
     * 流程模板接口
     */
    @Autowired
    private RdFlowWebRemoteFeignServiceI rdFlowWebService;

    /**
     * 输入接口
     */
    @Autowired
    private InputsRemoteFeignServiceI inputsService;

    /**
     * 输出接口
     */
    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    /**
     * 任务资源接口
     */
    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoService;

    /**
     * 资源信息
     */
    @Autowired
    private NameStandardDeliveryRelationRemoteFeignServiceI nameStandardDeliveryRelationService;

    /**
     * 资源接口
     */
    @Autowired
    private ResourceRemoteFeignServiceI resourceService;

    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;

    /** 业务对象 */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;

    /**
     * 配置业务接口
     */
    @Autowired
    private FeignCalendarService calendarService;

    /**
     * EPS服务实现接口<br>
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;

    /**
     * 交付项与项目库关联
     */
    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    /**
     * 名称库<br>
     */
    @Autowired

    private NameStandardRemoteFeignServiceI nameStandardService;
    /**
     * 生命周期状态服务接口
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;

//    /**  */
//    @Autowired
//    private BaseUserService baseUserService;

    /**
     *
     */
    @Autowired
    private DeliveryStandardRemoteFeignServiceI deliveryStandardService;

//    /**
//     *
//     */
//    @Autowired
//    @Qualifier("businessObjectService")
//    private BusinessObjectServiceI<RepFileAttachment> repFileAttrService;

    /**
     *
     */
    @Autowired
    private PlanLogRemoteFeignServiceI planLogService;

//    /**
//     * 人员信息
//     */
//    @Autowired
//    private SelectUserServiceI selectUserService;

//    /**
//     * 文件类型查询业务层
//     */
//    @Autowired
//    private RepFileTypeQueryService repFileTypeQueryService;

    @Autowired
    private SessionFacade sessionFacade;

    /**
     * 插件验证接口服务
     */
    @Autowired
    public PluginValidateServiceI pluginValidateService;

    @Autowired
    private FeignSystemService feignSystemService;

    @Autowired
    private FeignRepService repFileService;

    @Value(value="${spring.application.name}")
    private String appKey;

    @Autowired
    private FeignOutwardExtensionService outwardExtensionService;

    /**
     *
     */
    @Autowired
    private FeignRepService repService;

    /**
     * 计划类型接口
     */
    @Autowired
    private ActivityTypeManageFeign activityTypeManageFeign;

    /**
     * 流程模板接口
     */
    @Autowired
    private ApplyProcTemplateRemoteFeignServiceI applyProcTemplateService;

    /**
     * 计划输入初始化时获取输入列表
     *
     * @param inputs
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    public void list(InputsDto inputs, HttpServletRequest request, HttpServletResponse response) {
        List<InputsDto> inputsList = new ArrayList<InputsDto>();
        if (inputs != null && StringUtils.isNotEmpty(inputs.getUseObjectId())
            && StringUtils.isNotEmpty(inputs.getUseObjectType())) {
            inputsList = inputsService.queryInputsDetailList(inputs);
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputsList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + inputsList.size() + "}";
        TagUtil.ajaxResponse(response, datagridStr);
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
//        GetFeignSystemServiceI systemService = (GetFeignSystemServiceI)ServiceDelegate.getService(
//            "getFeignSystemService");
        List<ActivityTypeManageDto> typeMapList = activityTypeManageFeign.getAllActivityTypeManage();
//        List<Map<String, String>> typeMapList = systemService.getDictData("activeCategory");
        List<JSONObject> list = new ArrayList<>();
        for(ActivityTypeManageDto cur : typeMapList) {
            JSONObject json = new JSONObject();
            json.put("typename", cur.getName());
            json.put("typecode", cur.getId());
            list.add(json);
        }
        try {
            TagUtil.ajaxResponse(response, JSON.toJSONString(list));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流程分解跳转判断
     *
     * @param planId
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "flowResolveJudge")
    @ResponseBody
    public AjaxJson flowResolveJudge(String planId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "";
        j.setSuccess(true);
        try {
            TSUserDto user = ResourceUtil.getCurrentUser();
            j.setMsg(user.getId());
            PlanDto parent = planService.getPlanEntity(planId);
            if (StringUtils.isNotEmpty(parent.getFlowResolveXml())) {
                String isEnableFlag = "2"; // "0":;"1":;"2":;"3":
                String status = PlanConstants.PLAN_ORDERED; // "0":;"1":;"2":;"3":
                FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.BACKCHANGEPLAN);
                String switchStr = switchStrJson.getObj().toString();
                if (!PlanConstants.PLAN_FINISH.equals(parent.getBizCurrent())) {
                    PlanDto p = new PlanDto();
                    p.setParentPlanId(planId);
                    // 是否已经进行反推计划设置：
                    FeignJson existsIdJson = planService.getPlanIdByLinkPlanId2(planId);
                    String existsId = existsIdJson.getObj().toString();
                    if (!CommonUtil.isEmpty(existsId) && CommonUtil.isEmpty(switchStr)) {
                        j.setObj("existsRdTask");
                        return j;
                    }
                    else {
                        List<PlanDto> childList = planService.queryPlanList(p, 1, 10, false);
                        if(childList.size()>0) {
                            for (PlanDto child : childList) {
                                if (PlanConstants.PLAN_EDITING.equals(child.getBizCurrent())) {
                                    status = PlanConstants.PLAN_EDITING;
                                    break;
                                }
                            }
                        }else {
                            status = PlanConstants.PLAN_EDITING;
                        }
                        if (PlanConstants.PLAN_ORDERED.equals(status)) {
                            for (PlanDto child : childList) {
                                if (StringUtils.isNotEmpty(child.getFlowStatus())
                                    && PlanConstants.PLAN_FLOWSTATUS_CHANGE.equals(child.getFlowStatus())
                                    && !PlanConstants.PLAN_INVALID.equals(child.getBizCurrent())) {
                                    isEnableFlag = "1";
                                    break;
                                }
                            }
                        }
                        else {
                            for (PlanDto child : childList) {
                                if (StringUtils.isNotEmpty(child.getFlowStatus())
                                    && !PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(child.getFlowStatus())
                                    && !PlanConstants.PLAN_INVALID.equals(child.getBizCurrent())) {
                                    if (!"true".equals(parent.getIsAssignSingleBack())) {
                                        isEnableFlag = "1";
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    isEnableFlag = "1";
                }
                j.setObj(isEnableFlag + "," + status);
            }
            else {
                j.setObj("prepare");
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.forwardfailure");
            j.setMsg(message);
            j.setSuccess(false);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids:" + planId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            return j;
        }
    }

    /**
     * 流程分解页面跳转
     *
     * @param plan
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "goPlanResolve")
    public ModelAndView goPlanResolve(PlanDto plan, HttpServletRequest request) {
        String parentId = "";
        if (plan != null && StringUtils.isNotEmpty(plan.getId())) {
            parentId = plan.getId();
        }

        // 获得流程模板的选择
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.FLOWTEMPLATE);
        String status = switchStrJson.getObj().toString();
        request.setAttribute("isFlowFlag", status);
        request.setAttribute("parentPlanId", parentId);
        TSUserDto user = ResourceUtil.getCurrentUser();
        request.setAttribute("userId", user.getId());
        return new ModelAndView("com/glaway/ids/pm/project/task/flowResolve");
    }

    /**
     * 研发流程模板查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "proTemplateList")
    public void proTemplateList(HttpServletRequest request, HttpServletResponse response) {
        // 查询条件组装器
        try {
            List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
            String nameKey = "TaskProcTemplate.procTmplName";
            String nameValue = "";
            String pageKey = "page";
            String pageValue = "";
            String rowsKey = "rows";
            String rowsValue = "";
            for (ConditionVO curVO : conditionList) {
                if (nameKey.equals(curVO.getKey())) {
                    nameValue = curVO.getValue();
                }
                if (pageKey.equals(curVO.getKey())) {
                    pageValue = curVO.getValue();
                }
                if (rowsKey.equals(curVO.getKey())) {
                    rowsValue = curVO.getValue();
                }
            }

            List<TaskProcTemplateVo> allResultList = new ArrayList<TaskProcTemplateVo>();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            int count = 1;
            allResultList = rdFlowTaskFlowResolveService.getProcTemplateAllList(ResourceUtil.getCurrentUserOrg().getId(),nameValue,pageValue,rowsValue);
            if(!CommonUtil.isEmpty(allResultList)) {
                for(TaskProcTemplateVo curTaskProcTemplateVo : allResultList) {
                   String countStr = curTaskProcTemplateVo.getCount();
                    if(!CommonUtil.isEmpty(countStr)) {
                        count = Integer.parseInt(countStr);
                        break;
                    }
                }
            }


//            try {
//                RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                String procTemplatelist = rdfConfigSupport.getProcTemplateAllList(nameValue,
//                    pageValue, rowsValue);
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//                AjaxJson ajaxJson = gson.fromJson(procTemplatelist, AjaxJson.class);
//                mapList = (List<Map<String, Object>>)ajaxJson.getObj();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//            // }
//            int count = 1;
//            if (!CommonUtil.isEmpty(mapList)) {
//                for (Map<String, Object> map : mapList) {
//                    TaskProcTemplateVO vo = new TaskProcTemplateVO();
//                    vo.setId(StringUtils.isEmpty((String)map.get("id")) ? "" : map.get("id").toString());
//                    String procTmplName = StringUtils.isEmpty((String)map.get("procTmplName")) ? "" : map.get(
//                        "procTmplName").toString();
//                    String procTmpSubName = StringUtils.isEmpty((String)map.get("procTmpSubName")) ? "" : map.get(
//                        "procTmpSubName").toString();
//                    if (!CommonUtil.isEmpty(procTmpSubName)) {
//                        vo.setProcTmplName(procTmplName + "(" + procTmpSubName + ")");
//                    }
//                    else {
//                        vo.setProcTmplName(procTmplName);
//                    }
//
//                    vo.setBizVersion(StringUtils.isEmpty((String)map.get("bizVersion")) ? "" : map.get(
//                        "bizVersion").toString());
//                    vo.setBizCurrent(StringUtils.isEmpty((String)map.get("status")) ? "" : map.get(
//                        "status").toString());
//                    vo.setFirstBy(StringUtils.isEmpty((String)map.get("firstBy")) ? "" : map.get(
//                        "firstBy").toString());
//                    vo.setFirstTimeStr(StringUtils.isEmpty((String)map.get("firstTimeStr")) ? "" : map.get(
//                        "firstTimeStr").toString());
//                    vo.setCreator(StringUtils.isEmpty((String)map.get("creator")) ? "" : map.get(
//                        "creator").toString());
//                    vo.setCreateTimeStr(StringUtils.isEmpty((String)map.get("createTimeStr")) ? "" : map.get(
//                        "createTimeStr").toString());
//                    String countStr = StringUtils.isEmpty((String)map.get("count")) ? "" : map.get(
//                        "count").toString();
//                    if (StringUtils.isNotEmpty(countStr)) {
//                        count = Integer.parseInt(countStr);
//                    }
//                    if (StringUtils.isNotEmpty(vo.getId())) {
//                        allResultList.add(vo);
//                    }
//                }
//            }

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            String json = gson.toJson(allResultList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
            TagUtil.ajaxResponse(response, datagridStr);

        }
        catch (Exception e) {
            Object[] arguments = new String[] {
                I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.taskFlowTemplate"),
                I18nUtil.getValue("com.glaway.ids.common.msg.search")};
            String message = I18nUtil.getValue("com.glaway.ids.common.search.failure", arguments);
            log.error(message, e);
        }
    }

    /**
     * 流程分解编辑器
     *
     * @param plan
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "flowResolveEditor")
    @ResponseBody
    public AjaxJson flowResolveEditor(PlanDto plan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String newdate = (String)request.getParameter("newDate");
        String rdflowWeb_Nginx = (String)request.getParameter("rdflowWeb_Nginx");
        if (StringUtils.isNotBlank(rdflowWeb_Nginx)) {
            rdflowWeb_Nginx = FeignConstants.ID_RDFLOW_SERVICE;
        }
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateSuccess");
        try {
            flowResolveEditorDetail(plan, newdate,rdflowWeb_Nginx);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateFailure");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    private void flowResolveEditorDetail(PlanDto plan, String newdate,String rdflowWeb_Nginx)
        throws IOException {
        TSUserDto user = ResourceUtil.getCurrentUser();
        String userId = user.getId();
        PlanDto parent = planService.getPlanEntity(plan.getId());
//        parent.setOpContent(PlanConstants.PLAN_LOGINFO_FLOW_SPLIT);
//        Map<String,Object> paramsMap = new HashMap<String,Object>();
//        paramsMap.put("plan", parent);
//        paramsMap.put("user", user);
        planService.updateOpContentByPlanId(user,plan.getId(),PlanConstants.PLAN_LOGINFO_FLOW_SPLIT);

        FeignJson aFeignJson = rdFlowWebService.getClassesPathFromRdflow();
        String voXml = parent.getFlowResolveXml();
        String temlXml = "";
        String path = "";
        path = aFeignJson.getObj().toString()
            + File.separator
            + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";

        if (StringUtils.isEmpty(voXml)) {
            InputStream is = this.getClass().getResourceAsStream("/flowtaskTemp/a.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            String s = "";
            String s1 = "";
            while ((s1 = br.readLine()) != null) {
                s = s + s1;
            }
            temlXml = s;
        }
        else {
            temlXml = voXml;
            // 是否有图例,没有则添加
            if (temlXml.split("<mxCell id=\"cutline\"").length <= 1) {
                // 在研发流程模版xml里拼接图例字符串
                String[] str = temlXml.split("</root>");
                File begin = new File(path + "/a.txt");
                 InputStream in;
                 try {
                 in = new FileInputStream(begin);
                 BufferedReader bf = new BufferedReader(new InputStreamReader(in, "GBK"));
                 StringBuffer buffer = new StringBuffer();
                 String line = "";
                 while ((line = bf.readLine()) != null) {
                 buffer.append(line);
                 }
                 String temp = buffer.toString();
                 String[] tuliStr = temp.split("<mxCell id=\"cutline\"");
                 temlXml = str[0] + "<mxCell id=\"cutline\" " + tuliStr[1];
                 }
                 catch (Exception e) {
                 e.printStackTrace();
                 }
            }

        }
        // 性能优化
        if (StringUtil.isNotEmpty(parent.getId())) {
            Map<String, String> cellMap;
            try {
                File file = new File(path);
                File[] list = null;
                list = file.listFiles();
                if (list != null) {
                    for (File file2 : list) {
                        if (file2 == null) {
                            continue;
                        }
                        else if (userId.equals(file2.getName().split("_")[0])) {
                            file2.delete();
                        }
                    }
                }

                cellMap = convertXMl(parent.getId());
                temlXml = parseXml(temlXml, cellMap);
                FileUtil fu = new FileUtil();
                fu.saveFile(path, userId + "_tt_" + newdate + ".txt", temlXml);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public List<FlowTaskPreposeVo> getChangeFlowTaskPreposeList(FlowTaskParentVo parent,
                                                                List<FlowTaskVo> changeFlowTaskList)
        throws GWException {
        List<FlowTaskPreposeVo> list = new ArrayList<FlowTaskPreposeVo>();
        PlanDto parentPlan = new PlanDto();
        parentPlan.setId(parent.getParentId());
        List<PreposePlanDto> proposeList = preposePlanService.getPreposePlansByParent(parentPlan);
        for (PreposePlanDto prepose : proposeList) {
            FlowTaskPreposeVo flowTaskPrepose = new FlowTaskPreposeVo();
            flowTaskPrepose.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
            for (FlowTaskVo t : changeFlowTaskList) {
                if (t.getPlanId().equals(prepose.getPlanId())) {
                    flowTaskPrepose.setFlowTaskId(t.getId());
                    flowTaskPrepose.setFlowTaskInfo(t);
                    break;
                }
            }
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getPlanId().equals(prepose.getPreposePlanId())) {
                    flowTaskPrepose.setPreposeId(f.getId());
                    flowTaskPrepose.setPreposePlanInfo(f);
                    break;
                }
            }
            list.add(flowTaskPrepose);
        }
        return list;
    }

    /**
     * 流程分解编辑器
     *
     * @param plan
     * @param request
     */
    @SuppressWarnings({"finally", "null", "unused"})
    @RequestMapping(params = "flowResolveEditorForOrdered")
    @ResponseBody
    public AjaxJson flowResolveEditorForOrdered(PlanDto plan, HttpServletRequest request) {
        String newdate = (String)request.getParameter("newDate");
        String rdflowWeb_Nginx = (String)request.getParameter("rdflowWeb_Nginx");
        TSUserDto user = ResourceUtil.getCurrentUser();
        String userId = user.getId();

        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateSuccess");
        FlowTaskParentVo flowTaskParent = new FlowTaskParentVo();
        List<FlowTaskVo> changeFlowTaskList = new ArrayList<FlowTaskVo>();
        List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = new ArrayList<ChangeFlowTaskCellConnectVo>();
        List<FlowTaskPreposeVo> flowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();
        try {
            // 调用Webservice，通过调用佩兰id找到对应的研发流程任务的id，取出相关的xml的文件
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String jsonXml = rdfConfigSupport.getXmlbyPlanId(plan.getId());
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") // 时间转化为特定格式
//            .setPrettyPrinting() // 对json结果格式化
//            .create();
//            RDTaskVO rdTaskVO = gson.fromJson(jsonXml, RDTaskVO.class);
            RDTaskVO rdTaskVO = rdFlowTaskFlowResolveService.getXmlbyPlanId(plan.getId());
            String voXml = rdTaskVO.getFlowResolveXml();

            PlanDto parent = planService.getPlanEntity(plan.getId());
            flowTaskParent.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
            flowTaskParent.setParentId(parent.getId());
            flowTaskParent.setFlowResolveXml(voXml);
            flowTaskParent.setParentPlan(parent.getParentPlan());
            changeFlowTaskList = taskFlowResolveService.getChangeFlowTaskList(flowTaskParent,userId);
            changeFlowTaskConnectList = taskFlowResolveService.getChangeFlowTaskConnectList(flowTaskParent);
            changeFlowTaskList = planService.taskSort(changeFlowTaskList);
            flowTaskPreposeList = getChangeFlowTaskPreposeList(
                flowTaskParent, changeFlowTaskList);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + plan.getId(), changeFlowTaskList);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + plan.getId(),
                changeFlowTaskConnectList);
            request.getSession().setAttribute(
                PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + plan.getId(), flowTaskPreposeList);
            request.getSession().setAttribute(PlanConstants.FLOWTASK_PARENT_KEY + plan.getId(),
                flowTaskParent);

            List<JSONObject> jsonList = new ArrayList<JSONObject>();
            Map<String, String> map = new HashMap<String, String>();
            String jsonResult = "";
            String initStr = "";
            map.put("templateId", plan.getId());
            List<Map> nodelist = new ArrayList<Map>();
            request.getSession().removeAttribute("nameStandardMapList");
            List<Map<String, String>> nameStandardMapList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> nameStandardMapByNameList = new ArrayList<Map<String, String>>();
            Map<String, String> nameStandardMap = null;
            //List<NameStandard> allNameStandardList = sessionFacade.getList(NameStandard.class);
            List<NameStandardDto> allNameStandardList = nameStandardService.searchNameStandards(new NameStandardDto());
            Map<String, String> tmpMap = new HashMap<String, String>();
            for (int i = 0; !CommonUtil.isEmpty(allNameStandardList)
                            && i < allNameStandardList.size(); i++ ) {
                tmpMap.put(allNameStandardList.get(i).getName(),
                    allNameStandardList.get(i).getId());
            }
            Map<String, String> newMap = null;
            for (FlowTaskVo f : changeFlowTaskList) {
                nameStandardMap = new HashMap<String, String>();
                nameStandardMap.put("nameStandardId", tmpMap.get(f.getPlanName()));
                nameStandardMap.put("nameStandardName", f.getPlanName());
                nameStandardMapList.add(nameStandardMap);
                newMap = new HashMap<String, String>();
                newMap.put(f.getPlanName(), tmpMap.get(f.getPlanName()));
                nameStandardMapByNameList.add(newMap);
            }
            request.getSession().setAttribute("nameStandardMapList", nameStandardMapList);
            request.getSession().setAttribute("nameStandardMapByNameList",
                nameStandardMapByNameList);
            jsonResult = JSON.toJSONString(nodelist);
            map.put("nodeList", jsonResult);
//            List<JsonResult> jsList = new ArrayList<JsonResult>();
//            Map<String, List<Map>> jsMap = null;
//            try {
//                JsonResult json = HttpClientUtil.httpClientPostByTest(initStr, map);
//                jsMap = (Map<String, List<Map>>)json.getObj();
//                String jsonRsp = gson.toJson(json);
//                jsList = com.alibaba.fastjson.JSONObject.parseArray("[" + jsonRsp + "]",
//                    JsonResult.class);
//            }
//            catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            if (!CommonUtil.isEmpty(jsMap)) {
//                List<Map> paramList = jsMap.get("paramList");
//                List<Map> nodeParamList = null;
//                if (!CommonUtil.isEmpty(paramList)) {
//                    for (Map paramMap : paramList) {
//                        if (!CommonUtil.isEmpty(paramMap.get("flowTempNodeId"))) {
//                            nodeParamList = new ArrayList<Map>();
//                            if (!CommonUtil.isEmpty(request.getSession().getAttribute(
//                                "flowTempChange_param_" + paramMap.get("flowTempNodeId")))) {
//                                nodeParamList = (List<Map>)request.getSession().getAttribute(
//                                    "flowTempChange_param_" + paramMap.get("flowTempNodeId"));
//                            }
//                            if (!CommonUtil.isEmpty(paramMap.get("sourceNameStandardId"))) {
//                                NameStandard p = deliverablesInfoService.getEntity(
//                                    NameStandard.class,
//                                    paramMap.get("sourceNameStandardId").toString());
//                                paramMap.put("sourceName", p.getName());
//                            }
//                            String targetNameNames = "";
//                            if (!CommonUtil.isEmpty(paramMap.get("targetNameStandardIdArr"))) {
//                                String targetNameStandardIdArr = paramMap.get(
//                                    "targetNameStandardIdArr").toString();
//                                for (String s : targetNameStandardIdArr.split(",")) {
//                                    NameStandard n = deliverablesInfoService.getEntity(
//                                        NameStandard.class, s);
//                                    if (!CommonUtil.isEmpty(n)) {
//                                        if (!CommonUtil.isEmpty(targetNameNames)) {
//                                            targetNameNames = n.getName();
//                                        }
//                                        else {
//                                            targetNameNames = targetNameNames + "," + n.getName();
//                                        }
//                                    }
//                                }
//                            }
//                            paramMap.put("targetNameArr", targetNameNames);
//                            nodeParamList.add(paramMap);
//                            request.getSession().setAttribute(
//                                "flowTempChange_param_" + paramMap.get("flowTempNodeId"),
//                                nodeParamList);
//                        }
//                    }
//                }
//                List<Map> docList = jsMap.get("docList");
//                List<Map> nodeDocList = null;
//                if (!CommonUtil.isEmpty(docList)) {
//                    for (Map docMap : docList) {
//                        if (!CommonUtil.isEmpty(docMap.get("flowTempNodeId"))) {
//                            nodeDocList = new ArrayList<Map>();
//                            if (!CommonUtil.isEmpty(request.getSession().getAttribute(
//                                "flowTempChange_doc_" + docMap.get("flowTempNodeId")))) {
//                                nodeDocList = (List<Map>)request.getSession().getAttribute(
//                                    "flowTempChange_doc_" + docMap.get("flowTempNodeId"));
//                            }
//                            if (!CommonUtil.isEmpty(docMap.get("docId"))) {
//                                DeliveryStandard d = deliverablesInfoService.getEntity(
//                                    DeliveryStandard.class, docMap.get("docId").toString());
//                                docMap.put("docName", d.getName());
//                            }
//                            if (!CommonUtil.isEmpty(docMap.get("sourceId"))) {
//                                Plan p = deliverablesInfoService.getEntity(Plan.class,
//                                    docMap.get("sourceId").toString());
//                                docMap.put("sourceName", p.getPlanName());
//                            }
//                            String targetNameNames = "";
//                            if (!CommonUtil.isEmpty(docMap.get("targetNameStandardIdArr"))) {
//                                String targetNameStandardIdArr = docMap.get(
//                                    "targetNameStandardIdArr").toString();
//                                for (String s : targetNameStandardIdArr.split(",")) {
//                                    NameStandard n = deliverablesInfoService.getEntity(
//                                        NameStandard.class, s);
//                                    if (!CommonUtil.isEmpty(n)) {
//                                        if (!CommonUtil.isEmpty(targetNameNames)) {
//                                            targetNameNames = n.getName();
//                                        }
//                                        else {
//                                            targetNameNames = targetNameNames + "," + n.getName();
//                                        }
//                                    }
//                                }
//                            }
//                            docMap.put("targetNameArr", targetNameNames);
//                            if (!CommonUtil.isEmpty(docMap.get("upDocId"))) {
//                                RepFile r = deliverablesInfoService.getEntity(RepFile.class,
//                                    docMap.get("upDocId").toString());
//                                if (!CommonUtil.isEmpty(r)) {
//                                    docMap.put("upDocName", r.getFileName());
//                                }
//                            }
//
//                            nodeDocList.add(docMap);
//                            request.getSession().setAttribute(
//                                "flowTempChange_doc_" + docMap.get("flowTempNodeId"), nodeDocList);
//                        }
//                    }
//                }
//            }

            FeignJson aFeignJson = rdFlowWebService.getClassesPathFromRdflow();
            String temlXml = "";
            String path = "";
            path = aFeignJson.getObj().toString()
                + File.separator
                + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";

//            String classesPath = this.getClass().getClassLoader().getResource("").getPath();
//
//            String path = "";
//            if (classesPath.contains("WEB-INF")) {
//                String[] classPath = classesPath.split("WEB-INF");
//                path = classPath[0]
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//
//            }
//            else {
//                path = classesPath
//                       + "META-INF"
//                       + File.separator
//                       + "resources"
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//            }

            FileUtil fu = new FileUtil();
            if (StringUtils.isEmpty(flowTaskParent.getFlowResolveXml())) {
                InputStream is = this.getClass().getResourceAsStream(
                    "/flowtaskTemp/a.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = "";
                String s1 = "";
                while ((s1 = br.readLine()) != null) {
                    s = s + s1;
                }
                temlXml = s;
            }
            else {
                temlXml = flowTaskParent.getFlowResolveXml();
            }
            File file = new File(path);
            File[] list = null;
            list = file.listFiles();
            if (list != null) {
                for (File file2 : list) {
                    if (file2 == null) {
                        continue;
                    }
                    else if (userId.equals(file2.getName().split("_")[0])) {
                        file2.delete();
                    }
                }
            }
            // 性能优化
            if (StringUtil.isNotEmpty(plan.getId())) {
                Map<String, String> cellMap = convertXMl(plan.getId());
                temlXml = parseXml(temlXml, cellMap);
            }

            fu.saveFile(path, userId + "_tt_" + newdate + ".txt", temlXml);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateFailure");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 变更审批时、流程分解编辑器查看
     *
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "flowResolveEditorForViewChange")
    @ResponseBody
    public AjaxJson flowResolveEditorForViewChange(HttpServletRequest request) {
        String newdate = (String)request.getParameter("newDate");
        // 此id为变更flowTaskParentId:
        String id = (String)request.getParameter("id");
        String formId = (String)request.getParameter("formId");
        PlanDto plan = new PlanDto();
        plan.setId(id);
        plan.setFormId(formId);
        TSUserDto user = ResourceUtil.getCurrentUser();
        String userId = user.getId();
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateSuccess");
        FlowTaskParentVo flowTaskParent = new FlowTaskParentVo();
        List<FlowTaskVo> changeFlowTaskList = new ArrayList<FlowTaskVo>();
        List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = new ArrayList<ChangeFlowTaskCellConnectVo>();
        List<FlowTaskPreposeVo> flowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();
        try {
            // 获取变更父计划id：
            flowTaskParent = rdFlowTaskFlowResolveService.getFlowTaskParent(plan.getId());
            PlanDto p = new PlanDto();
            p.setParentPlanId(plan.getId());
            p.setFormId(plan.getFormId());
            changeFlowTaskList = taskFlowResolveService.getChangeFlowTaskListForChange(p,
                UserUtil.getCurrentUser().getId());
            changeFlowTaskConnectList = taskFlowResolveService.getChangeFlowTaskConnectListForChange(p);
            flowTaskPreposeList = taskFlowResolveService.getChangeFlowTaskPreposeListForChange(p);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + plan.getId(), changeFlowTaskList);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + plan.getId(),
                changeFlowTaskConnectList);
            request.getSession().setAttribute(
                PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + plan.getId(), flowTaskPreposeList);
            request.getSession().setAttribute(PlanConstants.FLOWTASK_PARENT_KEY + plan.getId(),
                flowTaskParent);
            FeignJson aFeignJson = rdFlowWebService.getClassesPathFromRdflow();
            String path = "";
            path = aFeignJson.getObj().toString()
                + File.separator
                + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";

//            String classesPath = this.getClass().getClassLoader().getResource("").getPath();
//            String path = "";
//            if (classesPath.contains("WEB-INF")) {
//                String[] classPath = classesPath.split("WEB-INF");
//                path = classPath[0]
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//
//            }
//            else {
//                path = classesPath
//                       + "META-INF"
//                       + File.separator
//                       + "resources"
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//            }
            FileUtil fu = new FileUtil();
            String temlXml = "";
            if (StringUtils.isEmpty(flowTaskParent.getFlowResolveXml())) {
                InputStream is = this.getClass().getResourceAsStream(
                    "/flowtaskTemp/a.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = "";
                String s1 = "";
                while ((s1 = br.readLine()) != null) {
                    s = s + s1;
                }
                temlXml = s;
            }
            else {
                temlXml = flowTaskParent.getFlowResolveXml();

            }
            File file = new File(path);
            File[] list = null;
            list = file.listFiles();
            if (list != null) {
                for (File file2 : list) {
                    if (file2 == null) {
                        continue;
                    }
                    else if (userId.equals(file2.getName().split("_")[0])) {
                        file2.delete();
                    }
                }
            }
            // 性能优化
            if (StringUtil.isNotEmpty(plan.getId())) {
                /*
                 * Map<String, String> cellMap = convertXMlForChangeview(plan.getId(),
                 * changeFlowTaskList);
                 * temlXml = parseXml(temlXml, cellMap);
                 */
            }

            fu.saveFile(path, userId + "_tt_" + newdate + ".txt", temlXml);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateFailure");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 变更审批时、流程分解编辑器查看
     *
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "flowResolveEditorForViewChangeEditor")
    @ResponseBody
    public AjaxJson flowResolveEditorForViewChangeEditor(HttpServletRequest request) {
        String newdate = (String)request.getParameter("newDate");
        // 此id为变更flowTaskParentId:
        String id = (String)request.getParameter("id");
        String formId = (String)request.getParameter("formId");
        PlanDto plan = new PlanDto();
        plan.setId(id);
        plan.setFormId(formId);
        TSUserDto user = UserUtil.getInstance().getUser();
        String userId = user.getId();
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateSuccess");
        FlowTaskParentVo flowTaskParent = new FlowTaskParentVo();
        List<FlowTaskVo> changeFlowTaskList = new ArrayList<FlowTaskVo>();
        List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = new ArrayList<ChangeFlowTaskCellConnectVo>();
        List<FlowTaskPreposeVo> flowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();
        try {
            // 获取变更父计划id：
            flowTaskParent = rdFlowTaskFlowResolveService.getFlowTaskParent(plan.getId());
            PlanDto p = new PlanDto();
            p.setParentPlanId(plan.getId());
            p.setFormId(plan.getFormId());
            changeFlowTaskList = taskFlowResolveService.getChangeFlowTaskListForChange(p,
                UserUtil.getCurrentUser().getId());
            changeFlowTaskConnectList = taskFlowResolveService.getChangeFlowTaskConnectListForChange(p);
            flowTaskPreposeList = taskFlowResolveService.getChangeFlowTaskPreposeListForChange(p);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + flowTaskParent.getParentId(),
                changeFlowTaskList);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + flowTaskParent.getParentId(),
                changeFlowTaskConnectList);
            request.getSession().setAttribute(
                PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + flowTaskParent.getParentId(),
                flowTaskPreposeList);
            request.getSession().setAttribute(
                PlanConstants.FLOWTASK_PARENT_KEY + flowTaskParent.getParentId(), flowTaskParent);

            FeignJson aFeignJson = rdFlowWebService.getClassesPathFromRdflow();
            String path = "";
            path = aFeignJson.getObj().toString()
                + File.separator
                + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//            String classesPath = this.getClass().getClassLoader().getResource("").getPath();
//            String path = "";
//            if (classesPath.contains("WEB-INF")) {
//                String[] classPath = classesPath.split("WEB-INF");
//                path = classPath[0]
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//
//            }
//            else {
//                path = classesPath
//                       + "META-INF"
//                       + File.separator
//                       + "resources"
//                       + File.separator
//                       + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";
//            }
            FileUtil fu = new FileUtil();
            String temlXml = "";
            if (StringUtils.isEmpty(flowTaskParent.getFlowResolveXml())) {
                InputStream is = this.getClass().getResourceAsStream(
                    "/flowtaskTemp/a.txt");
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = "";
                String s1 = "";
                while ((s1 = br.readLine()) != null) {
                    s = s + s1;
                }
                temlXml = s;
            }
            else {
                temlXml = flowTaskParent.getFlowResolveXml();
            }
            File file = new File(path);
            File[] list = null;
            list = file.listFiles();
            if (list != null) {
                for (File file2 : list) {
                    if (file2 == null) {
                        continue;
                    }
                    else if (userId.equals(file2.getName().split("_")[0])) {
                        file2.delete();
                    }
                }
            }
            // 性能优化
            if (StringUtil.isNotEmpty(plan.getId())) {
                /*
                 * Map<String, String> cellMap = convertXMlForChangeview(plan.getId(),
                 * changeFlowTaskList);
                 * temlXml = parseXml(temlXml, cellMap);
                 */
            }

            fu.saveFile(path, userId + "_tt_" + newdate + ".txt", temlXml);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateFailure");
            j.setSuccess(false);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * @param parentId
     * @return
     * @throws Exception
     */
    private Map<String, String> convertXMl(String parentId)
        throws Exception {

        PlanDto plan = planService.getPlanEntity(parentId);
        List<TaskCellVO> cells = new ArrayList<TaskCellVO>();
        Map<String, String> cellMap = new HashMap<String, String>();

        try {
            if (plan != null) {
                List<PlanDto> planList = planService.getPlanAllChildren(plan);
                for (PlanDto plan2 : planList) {
                    if (plan.getId().equals(plan2.getParentPlanId())) {
                        Project project = projectService.getProjectEntity(plan2.getProjectId());
                        TaskCellVO vo = new TaskCellVO();
                        vo.setCellId(plan2.getCellId());
                        if ("FINISH".equalsIgnoreCase(plan2.getBizCurrent())) {
                            vo.setBizCurrent(plan2.getBizCurrent());
                        }
                        else if ("CLOSED".equalsIgnoreCase(project.getBizCurrent())
                                 || "PAUSED".equalsIgnoreCase(project.getBizCurrent())) {
                            vo.setBizCurrent(project.getBizCurrent());
                        }
                        else {
                            vo.setBizCurrent(plan2.getBizCurrent());
                        }
                        cells.add(vo);
                    }
                }
            }
            else {
                // 若为流程变更时
                /*
                 * List<FlowTask> planList1 = taskFlowResolveService.getChangeFlowTaskList(plan);
                 * for (FlowTask plan2 : planList1) {
                 * Project project = projectService.getEntity(plan2.getProjectId());
                 * TaskCellVO vo = new TaskCellVO();
                 * vo.setCellId(plan2.getCellId());
                 * if ("FINISH".equalsIgnoreCase(plan2.getBizCurrent())) {
                 * vo.setBizCurrent(plan2.getBizCurrent());
                 * }
                 * else if ("CLOSED".equalsIgnoreCase(project.getBizCurrent())
                 * || "PAUSED".equalsIgnoreCase(project.getBizCurrent())) {
                 * vo.setBizCurrent(project.getBizCurrent());
                 * }
                 * else {
                 * vo.setBizCurrent(plan2.getBizCurrent());
                 * }
                 * cells.add(vo);
                 * }
                 */
            }
            for (TaskCellVO cell : cells) {
                cellMap.put(cell.getCellId(), cell.getBizCurrent());
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            // message = "获取失败";
        }
        finally {}

        return cellMap;

    }

    /**
     * @param xml
     * @param cellMap
     * @return
     * @throws DocumentException
     */
    private String parseXml(String xml, Map<String, String> cellMap)
        throws DocumentException {
        String styleIndex = "wftask;";
        SAXReader reader = new SAXReader();
        InputStream in;
        try {
            in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Document document = reader.read(in);
            Element root = document.getRootElement();
            List<Element> nodeRoots = root.elements("root");
            if (nodeRoots != null && nodeRoots.size() >= 1) {
                Element nodeRoot = nodeRoots.get(0);
                List<Element> nodes = nodeRoot.elements("Task");
                for (Iterator it = nodes.iterator(); it.hasNext();) {
                    Element elm = (Element)it.next();
                    String cellId = elm.attributeValue("id");
                    Element mxCell = elm.element("mxCell");
                    Attribute style = mxCell.attribute("style");
                    String[] str = style.getValue().split(".jpg;");

                    String styleString = "";
                    if (cellMap == null) {
                        styleString = styleIndex + "image=images/editorImg/editing.jpg";
                    }
                    else {
                        if (StringUtils.isNotEmpty(cellMap.get(cellId))) {
                            switch (cellMap.get(cellId)) {
                                case "ORDERED":
                                    styleString = styleIndex
                                                  + "image=images/editorImg/ordered.jpg";
                                    break;
                                case "EDITING":
                                    styleString = styleIndex
                                                  + "image=images/editorImg/editing.jpg";
                                    break;
                                case "FINISH":
                                    styleString = styleIndex + "image=images/editorImg/finish.jpg";
                                    break;
                                case "FEEDBACKING":
                                    styleString = styleIndex
                                                  + "image=images/editorImg/feedbacking.jpg";
                                    break;
                                case "PAUSED":
                                    styleString = styleIndex + "image=images/editorImg/paused.jpg";
                                    break;
                                case "CLOSED":
                                    styleString = styleIndex + "image=images/editorImg/closed.jpg";
                                    break;
                                case "LAUNCHED":
                                    styleString = styleIndex + "image=images/editorImg/launched.jpg";
                                    break;
                                case "TOBERECEIVED":
                                    styleString = styleIndex + "image=images/editorImg/tobereceived.jpg";
                                    break;
                                default:
                                    styleString = styleIndex
                                                  + "image=images/editorImg/editing.jpg";
                            }
                        }
                        else {
                            styleString = styleIndex + "image=images/editorImg/editing.jpg";
                        }
                    }

                    str[0] = styleString;
                    StringBuffer endStr = new StringBuffer(str[0]);

                    if (str.length > 1) {
                        endStr.append(";" + str[1]);
                    }
                    style.setValue(endStr.toString());
                }

            }

            xml = document.asXML();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return xml;
    }

    /**
     * 流程任务信息页面跳转
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "flowTaskCellAdd")
    public ModelAndView flowTaskCellAdd(HttpServletRequest request) {
        String cellId = request.getParameter("cellId");
        request.setAttribute("cellId", cellId);
        String parentPlanId = request.getParameter("parentPlanId");
        request.setAttribute("parentPlanId", parentPlanId);
        String isEnableFlag = request.getParameter("isEnableFlag");
        request.setAttribute("isEnableFlag", isEnableFlag);
        String refDuration = request.getParameter("refDuration");
        request.setAttribute("refDuration", refDuration);
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCell-add");

        return mav;
    }

    /**
     * 流程任务信息页面跳转
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "changeFlowTaskCellAdd")
    public ModelAndView changeFlowTaskCellAdd(HttpServletRequest request) {
        String cellId = request.getParameter("cellId");
        request.setAttribute("cellId", cellId);
        String parentPlanId = request.getParameter("parentPlanId");
        request.setAttribute("parentPlanId", parentPlanId);
        String isEnableFlag = request.getParameter("isEnableFlag");
        request.setAttribute("isEnableFlag", isEnableFlag);
        String refDuration = request.getParameter("refDuration");
        request.setAttribute("refDuration", refDuration);
        //TODO..
//        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//        if (!CommonUtil.isEmpty(outwardExtensionList)) {
//            request.setAttribute("outwards", outwardExtensionList);
//        }
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/changeFlowTaskCell-add");
        return mav;
    }

    /**
     * 跳转到tab页
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goTab")
    private ModelAndView goTab(PlanDto plan, HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mav = null;
        String tabIndex = req.getParameter("tabIndex");
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        String isEnableFlag = req.getParameter("isEnableFlag");
        String refDuration = req.getParameter("refDuration");
        String change = req.getParameter("change");
        String isSave = req.getParameter("isSave");

        PlanDto condition = new PlanDto();
        condition.setCellId(cellId);
        condition.setParentPlanId(parentPlanId);
        List<PlanDto> list = planService.queryPlanList(condition, 1, 10, false);

        // 责任人对应的责任部门list取值
        JSONArray deptList = new JSONArray();
        JSONObject obj = null;
        List<TSUserDto> userList = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
            Project project = projectService.getProjectEntity(parentPlan.getProjectId());
            if (project != null) {
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
        if (!CommonUtil.isEmpty(userList)) {
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
        }
        PlanDto info = new PlanDto();
        if (list.size() > 0) {
            info = list.get(0);
            if (!CommonUtil.isEmpty(info.getOwner()) && !CommonUtil.isEmpty(info.getCreateOrgId())) {
                //初始化责任部门：
                TSDepartDto curTSDepartDto = deptService.getTSDepartById(appKey, info.getCreateOrgId());
                if(!CommonUtil.isEmpty(curTSDepartDto)) {
                    info.setOwnerDept(curTSDepartDto.getDepartname());
                }
            }
            List<PreposePlanDto> outplans = preposePlanService.getPreposePlansByPlanId(info);

            String preposeNames = "";
            String preposeIds = "";
            String allPreposeNames = "";
            String allPreposeIds = "";
            for (PreposePlanDto prepose : outplans) {

                PlanDto preposePlan = planService.getPlanEntity(prepose.getPreposePlanId());
                if (preposePlan != null) {
                    if (StringUtils.isNotEmpty(allPreposeNames)) {
                        allPreposeNames = allPreposeNames + "," + preposePlan.getPlanName();
                    }
                    else {
                        allPreposeNames = preposePlan.getPlanName();
                    }
                    if (StringUtils.isNotEmpty(allPreposeIds)) {
                        allPreposeIds = allPreposeIds + "," + prepose.getPreposePlanId();
                    }
                    else {
                        allPreposeIds = prepose.getPreposePlanId();
                    }

                    if (StringUtils.isNotEmpty(preposeNames)) {
                        if (StringUtils.isEmpty(preposePlan.getParentPlanId())
                            || !preposePlan.getParentPlanId().equals(info.getParentPlanId())) {
                            preposeNames = preposeNames + "," + preposePlan.getPlanName();
                        }
                    }
                    else {
                        if (StringUtils.isEmpty(preposePlan.getParentPlanId())
                            || !preposePlan.getParentPlanId().equals(info.getParentPlanId())) {
                            preposeNames = preposePlan.getPlanName();
                        }
                    }
                    if (StringUtils.isNotEmpty(preposeIds)) {
                        if (StringUtils.isEmpty(preposePlan.getParentPlanId())
                            || !preposePlan.getParentPlanId().equals(info.getParentPlanId())) {
                            preposeIds = preposeIds + "," + prepose.getPreposePlanId();
                        }
                    }
                    else {
                        if (StringUtils.isEmpty(preposePlan.getParentPlanId())
                            || !preposePlan.getParentPlanId().equals(info.getParentPlanId())) {
                            preposeIds = prepose.getPreposePlanId();
                        }
                    }
                }

            }
            req.setAttribute("allPreposeNames", allPreposeNames);
            req.setAttribute("allPreposeIds", allPreposeIds);
            info.setPreposeIds(preposeIds);
            info.setPreposePlans(preposeNames);
        }
        else {
            info.setWorkTime("1");
            if (StringUtils.isNotEmpty(refDuration)) {
                info.setWorkTime(refDuration);
            }
        }
        TSUserDto tuser = null;
        if(!CommonUtil.isEmpty(info.getOwner())) {
            tuser = userService.getUserByUserId(info.getOwner());
        }
        String switchStr = "";
        try {
              FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
              switchStr = switchStrJson.getObj().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String isStandard = "false";
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)) {// 非强制名称库
            isStandard = "ok";
        }
        else if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
                 || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            isStandard = "true";
        }
        else {
            isStandard = "false";// 不使用名称库
        }
        String updateFlag = "false";
        if (NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {// 关联交付项可以修改
            updateFlag = "true";
        }

//        String dictCode = "activeCategory";
//        List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
//        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
//        List<TSTypeDto> types = tsMap.get(dictCode);

        if (!"false".equals(isStandard)) {
            if (StringUtils.isEmpty(info.getTaskNameType())) {
                List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(false);
                if(activityTypeManageList.size()>0) {
                    for(ActivityTypeManageDto activityTypeManage : activityTypeManageList) {
                        if(!CommonUtil.isEmpty(activityTypeManage.getName()) && "研发类".equals(activityTypeManage.getName())) {
                            info.setTaskNameType(activityTypeManage.getId());
                            info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
                        }
                    }
                }
//                if (!CommonUtil.isEmpty(types)) {
//                    info.setTaskNameType(types.get(0).getTypecode());
//                    info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
//                }
            }
        }
        else {
            List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(false);
            if(activityTypeManageList.size()>0) {
                for(ActivityTypeManageDto activityTypeManage : activityTypeManageList) {
                    if(!CommonUtil.isEmpty(activityTypeManage.getName()) && "研发类".equals(activityTypeManage.getName())) {
                        info.setTaskNameType( activityTypeManage.getId());
                        info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
                    }
                }
            }
//            if (!CommonUtil.isEmpty(types)) {
//                info.setTaskNameType(types.get(0).getTypecode());
//                info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
//            }
        }

        if ("true".equals(info.getFromTemplate())) {
            if (StringUtils.isEmpty(info.getWorkTimeReference())) {
                info.setWorkTimeReference(info.getWorkTime());
            }
        }
        else {
            info.setWorkTimeReference("0");
        }
        if (PlanConstants.PLAN_FEEDBACKING.equals(info.getBizCurrent())
            || PlanConstants.PLAN_FINISH.equals(info.getBizCurrent())) {
            isEnableFlag = "1";
        }
        req.setAttribute("plan_", info);
        req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userList) {
            JSONObject obj1 = new JSONObject();
            obj1.put("id", t.getId());
            obj1.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj1);
        }

        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("\"", "'");
        req.setAttribute("userList2", jonStr);
        if (tuser != null) {
            if (tuser.getRealName().contains("-")) {
                req.setAttribute("ownerShow", tuser.getRealName());
            }
            else {
                req.setAttribute("ownerShow", tuser.getRealName() + "-" + tuser.getUserName());
            }
        }
        else {
            req.setAttribute("ownerShow", "");
        }
        if(!CommonUtil.isEmpty(info.getPlanLevel())) {
            BusinessConfig planLevelBase = new BusinessConfig();
            planLevelBase.setId(info.getPlanLevel());
            planLevelBase.setConfigType(ConfigTypeConstants.PLANLEVEL);
            String planLevelListStr2 = businessConfigService.searchBusinessConfigAccurate(planLevelBase);
            List<BusinessConfigDto> planLevelInfo2 = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr2),new TypeReference<List<BusinessConfigDto>>(){});
            info.setPlanLevelInfo(planLevelInfo2.get(0));
        }
        if (info.getPlanLevelInfo() != null && "1".equals(info.getPlanLevelInfo().getAvaliable())
            && ConfigStateConstants.START.equals(info.getPlanLevelInfo().getStopFlag())) {
            req.setAttribute("planLevelShow", info.getPlanLevelInfo().getName());
        }
        else {
            req.setAttribute("planLevelShow", "");
        }
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
//        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
//        planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
   //     List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});

        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr3);
        NameStandardDto nameStandard = new NameStandardDto();
        List<NameStandardDto> standList = null;
        switch (tabIndex) {
            // tab索引
            case "0":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-nodeInfo"); // 节点信息
                mav.addObject("isStandard", isStandard);
                nameStandard.setStopFlag("启用");
                nameStandard.setName(info.getPlanName());
                standList = nameStandardService.searchNameStandards(nameStandard);
                if (!CommonUtil.isEmpty(standList)) {
                    mav.addObject("nameStandardId", standList.get(0).getId());
                }
                else {
                    mav.addObject("nameStandardId", "");
                }
                mav.addObject("change", change);
                if (CommonUtil.isEmpty(isSave)) {
                    isSave = "false";
                }
                mav.addObject("isSave", isSave);
                break;
            // tab索引
            case "1":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-baseInfo"); // 基本信息
                mav.addObject("isStandard", isStandard);
                nameStandard.setStopFlag("启用");
                nameStandard.setName(info.getPlanName());
                standList = nameStandardService.searchNameStandards(nameStandard);
                if (!CommonUtil.isEmpty(standList)) {
                    mav.addObject("nameStandardId", standList.get(0).getId());
                }
                else {
                    mav.addObject("nameStandardId", "");
                }
                mav.addObject("change", change);
                if (CommonUtil.isEmpty(isSave)) {
                    isSave = "false";
                }
                mav.addObject("isSave", isSave);
                break;
            case "2":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-input");// 输入
                break;
            case "3":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-reference"); // 参考
                Properties prop = PropertiesUtil.getProperties(KnowledgeSupportConstants.KNOWLEDGE_ADDRESS);
                String openUrl = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
                                 + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_SEARCH_URL);
                String openUrlView = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
                                     + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_VIEW_URL);
                mav.addObject("openUrl", openUrl);
                mav.addObject("openUrlView", openUrlView);
                mav.addObject("taskId", info.getId());
                break;
            case "4":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-output"); // 输出
                if (!CommonUtil.isEmpty(info)) {
                    mav.addObject("planId", info.getId());
                }
                mav.addObject("updateFlag", updateFlag);
                break;
            case "5":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-resource"); // 资源
                if (!CommonUtil.isEmpty(info)) {
                    mav.addObject("planId", info.getId());
                }
                break;
            default:
                mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellTab-baseInfo"); // 基本信息
        }
        boolean isKlmPluginValid = pluginValidateService.isValidatePlugin(PluginConstants.KLM_PLUGIN_NAME);
        mav.addObject("cellId", cellId);
        mav.addObject("parentPlanId", parentPlanId);
        if (list.size() > 0) {
            mav.addObject("projectId", list.get(0).getProjectId());
        }
        mav.addObject("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        mav.addObject("isEnableFlag", isEnableFlag);
        mav.addObject("isKlmPluginValid", isKlmPluginValid);
        return mav;
    }

    /**
     * 跳转到tab页
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goChangeTab")
    private ModelAndView goChangeTab(PlanDto plan, HttpServletRequest req,
                                     HttpServletResponse response) {
        ModelAndView mav = null;
        String tabIndex = req.getParameter("tabIndex");
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        String isEnableFlag = req.getParameter("isEnableFlag");
        String refDuration = req.getParameter("refDuration");
        String isSave = req.getParameter("isSave");
        String projectId = "";
        // 责任人对应的责任部门list取值
        JSONArray deptList = new JSONArray();
        JSONObject obj = null;
        List<TSUserDto> userList = new ArrayList<TSUserDto>();
        Map<String, TSUserDto> usersMap = userService.getCommonUserAll();
        Map<String, TSUserDto> userIdsMap = new HashMap<String, TSUserDto>();
        for (TSUserDto user : usersMap.values()) {
            userIdsMap.put(user.getId(), user);
        }
        boolean isChangeProcessView = false;
        String templateId = "";
        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
            if(!CommonUtil.isEmpty(parentPlan)) {
                if (!CommonUtil.isEmpty(parentPlan)) {
                    // 获取变更父计划id：
                    FlowTaskParentVo flowTaskParent = new FlowTaskParentVo();
                    flowTaskParent = rdFlowTaskFlowResolveService.getFlowTaskParent(parentPlanId);
                    Map<String, Object> mapList = new HashMap<String, Object>();

                    if (flowTaskParent != null) {
                        parentPlan = planService.getPlanEntity(flowTaskParent.getParentId());
                        templateId = flowTaskParent.getParentId();
                    }
                    isChangeProcessView = true;
                }
                Project project = projectService.getProjectEntity(parentPlan.getProjectId());
                if (project != null) {
                    projectId = parentPlan.getProjectId();
                    List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                    for (TSUserDto u : users) {
                        if (!CommonUtil.isEmpty(userIdsMap.get(u.getId()))) {
                            userList.add(userIdsMap.get(u.getId()));
                        }
                    }
                }
            }
        }

        FlowTaskVo info = new FlowTaskVo();
        info.setWorkTime("1");
        if (StringUtils.isNotEmpty(refDuration)) {
            info.setWorkTime(refDuration);
        }

        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        if (!CommonUtil.isEmpty(changeFlowTaskList)) {
            for (FlowTaskVo task : changeFlowTaskList) {
                if (task.getCellId().equals(cellId)) {
                    info = task;
                    break;
                }
            }
        }

        if (!CommonUtil.isEmpty(userList)) {
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

                if (user.getId().equals(info.getOwner()) && user.getTSDepart() != null
                    && StringUtils.isNotEmpty(user.getTSDepart().getDepartname())) {
                    info.setOwnerDept(user.getTSDepart().getDepartname());
                }

                deptList.add(obj);
            }
        }

        if (info.getOwner() != null) {
            List<TSDepartDto> deptListInfo = deptService.getTSDepartByuserId(appKey, info.getOwner());
            if(deptListInfo.size()>0) {
                TSDepartDto curDeptInfo =  deptListInfo.get(0);
                if(!CommonUtil.isEmpty(curDeptInfo)) {
                    info.setOwnerDept(curDeptInfo.getDepartname());
                }
            }
        }

        String switchStr = "";
        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
            FeignJson  switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
            switchStr = switchStrJson.getObj().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String isStandard = "false";
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)) {// 非强制名称库
            isStandard = "ok";
        }
        else if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
                 || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            isStandard = "true";
        }
        else {
            isStandard = "false";// 不使用名称库
        }
        // 是否开启使用名称库
        // 任务类型设置
//        String dictCode = "activeCategory";
//        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
//        List<TSTypeDto> types = tsMap.get(dictCode);
//        List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
//        if (!"false".equals(isStandard)) {
//            if (StringUtils.isEmpty(info.getTaskNameType())) {
//                if (!CommonUtil.isEmpty(types)) {
//                    info.setTaskNameType(types.get(0).getTypecode());
//                    info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
//                }
//            }
//        }
//        else {
//            if (!CommonUtil.isEmpty(types)) {
//                info.setTaskNameType(types.get(0).getTypecode());
//                info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
//            }
//        }

        if ("true".equals(info.getFromTemplate())) {
            if (StringUtils.isEmpty(info.getWorkTimeReference())) {
                info.setWorkTimeReference(info.getWorkTime());
            }
        }
        else {
            info.setWorkTimeReference("0");
        }
        if (PlanConstants.PLAN_FEEDBACKING.equals(info.getBizCurrent())
            || PlanConstants.PLAN_FINISH.equals(info.getBizCurrent())) {
            isEnableFlag = "1";
        }

        req.setAttribute("plan_", info);
        req.setAttribute("departList", deptList.toString().replaceAll("\"", "'"));

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userList) {
            JSONObject obj1 = new JSONObject();
            obj1.put("id", t.getId());
            obj1.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj1);
        }

        String jsonResult = JSON.toJSONString(jsonList);
        String jonStr = jsonResult;
        jonStr = jonStr.replaceAll("\"", "'");
        req.setAttribute("userList2", jonStr);
        TSUserDto tuser = userIdsMap.get(info.getOwner());
        String ownerShow = "";
        if (tuser != null) {
            if (StringUtils.isNotEmpty(tuser.getRealName())) {
                if (tuser.getRealName().contains("-")) {
                    ownerShow = tuser.getRealName();
                }
                else {
                    ownerShow = tuser.getRealName() + "-" + tuser.getUserName();
                }
            }
        }
        req.setAttribute("ownerShow", ownerShow);

        req.setAttribute("planLevelShow", "");
        if (StringUtils.isNotEmpty(info.getPlanLevel())) {
            BusinessConfig planLevelInfo = businessConfigService.getBusinessConfig(info.getPlanLevel());
            if (planLevelInfo != null && "1".equals(planLevelInfo.getAvaliable())
                && ConfigStateConstants.START.equals(planLevelInfo.getStopFlag())) {
                req.setAttribute("planLevelShow", planLevelInfo.getName());
            }
        }
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig>  planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
     //   List<BusinessConfig>  planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr3);
        NameStandardDto nameStandard = new NameStandardDto();
        switch (tabIndex) {
            // tab索引
            case "0":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/changeFlowTaskCellTab-nodeInfo"); // 节点信息
                nameStandard.setStopFlag("启用");
                nameStandard.setName(info.getPlanName());
                List<NameStandardDto> standList = nameStandardService.searchNameStandards(nameStandard);
                if (!CommonUtil.isEmpty(standList)) {
                    mav.addObject("nameStandardId", standList.get(0).getId());
                }
                else {
                    mav.addObject("nameStandardId", "");
                }
                List<OutwardExtensionDto> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"activeCategory");
                if (!CommonUtil.isEmpty(outwardExtensionList)) {
                    mav.addObject("outwards", outwardExtensionList);
                }
                if (CommonUtil.isEmpty(isSave)) {
                    isSave = "false";
                }
                if (!CommonUtil.isEmpty(templateId)) {
                    mav.addObject("templateId", templateId);
                    mav.addObject("templateNodeId", info.getId());
                }
                mav.addObject("isSave", isSave);
                mav.addObject("isChangeProcessView", isChangeProcessView);
                break;
            // tab索引
            case "1":
                mav = new ModelAndView(
                    "com/glaway/ids/pm/project/task/changeFlowTaskCellTab-baseInfo"); // 基本信息
                mav.addObject("isStandard", isStandard);
                nameStandard.setStopFlag("启用");
                nameStandard.setName(info.getPlanName());
                List<NameStandardDto> standList1 = nameStandardService.searchNameStandards(nameStandard);
                if (!CommonUtil.isEmpty(standList1)) {
                    mav.addObject("nameStandardId", standList1.get(0).getId());
                }
                else {
                    mav.addObject("nameStandardId", "");
                }
                List<OutwardExtensionDto> outwardExtensionList1 = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"activeCategory");
                if (!CommonUtil.isEmpty(outwardExtensionList1)) {
                    mav.addObject("outwards", outwardExtensionList1);
                }
                if (CommonUtil.isEmpty(isSave)) {
                    isSave = "false";
                }
                if (!CommonUtil.isEmpty(templateId)) {
                    mav.addObject("templateId", templateId);
                    mav.addObject("templateNodeId", info.getId());
                }
                mav.addObject("isSave", isSave);
                mav.addObject("isChangeProcessView", isChangeProcessView);
                break;
            case "2":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/changeFlowTaskCellTab-input");// 输入
                break;
            case "3":
                mav = new ModelAndView(
                    "com/glaway/ids/pm/project/task/changeFlowTaskCellTab-reference"); // 参考
                Properties prop = PropertiesUtil.getProperties(KnowledgeSupportConstants.KNOWLEDGE_ADDRESS);
                String openUrl = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
                                 + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_SEARCH_URL);
                String openUrlView = SystemConfigUtil.getValue(IDSConfigConstants.IDS_KLM_HTTPURL_KEY)
                                     + prop.getProperty(KnowledgeSupportConstants.IDS_KNOWLEDGE_VIEW_URL);
                mav.addObject("openUrl", openUrl);
                mav.addObject("openUrlView", openUrlView);
                mav.addObject("planId", info.getPlanId());
                mav.addObject("taskId", info.getPlanId());
                // 参考
                break;
            case "4":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/changeFlowTaskCellTab-output"); // 输出
                break;
            case "5":
                mav = new ModelAndView(
                    "com/glaway/ids/pm/project/task/changeFlowTaskCellTab-resource"); // 资源
                if (!CommonUtil.isEmpty(info)) {
                    mav.addObject("planId", info.getPlanId());
                }
                break;
            default:
                mav = new ModelAndView(
                    "com/glaway/ids/pm/project/task/changeFlowTaskCellTab-baseInfo"); // 基本信息
        }

        boolean klmFlag = getKlmPluginValid();
        mav.addObject("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        mav.addObject("cellId", cellId);
        mav.addObject("parentPlanId", parentPlanId);
        mav.addObject("projectId", projectId);
        mav.addObject("isEnableFlag", isEnableFlag);
        mav.addObject("klmFlag", klmFlag);
        return mav;
    }

    private boolean getKlmPluginValid() {
        return pluginValidateService.isValidatePlugin(PluginConstants.KLM_PLUGIN_NAME);
    }

    /**
     * 查询活动输入信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "inputList")
    private void inputList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        int page = 1;
        int rows = 10;
        if(!CommonUtil.isEmpty(req.getParameter("page"))) {
            page = Integer.valueOf(req.getParameter("page"));
            rows = Integer.valueOf(req.getParameter("rows"));
        }
        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        List<InputsDto> inputList = new ArrayList<InputsDto>();
        if (list.size() > 0) {
            plan = list.get(0);
            String libId = "";
            if (!CommonUtil.isEmpty(plan)) {
                String projectId = plan.getProjectId();
                FeignJson fj = projRoleService.getLibIdByProjectId(projectId);
                libId = String.valueOf(fj.getObj());
            }
            else {
                String projectId = req.getParameter("projectId");
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

            InputsDto inputs = new InputsDto();
            inputs.setUseObjectId(plan.getId());
            inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            inputList = inputsService.queryInputsDetailList(inputs);
            for (InputsDto i : inputList) {
                if (StringUtils.isNotEmpty(i.getDocId())) {
                    RepFileDto r =  repFileService.getRepFileByRepFileId(appKey, i.getDocId());
                    if (!CommonUtil.isEmpty(r)) {
                        //TODO..
                        String havePower = "";
//                        String havePower = planService.getOutPower(i.getDocId(),i.getUseObjectId(), UserUtil.getCurrentUser().getId());
                        if ("downloadDetail".equals(havePower)) {
                            i.setDownload(true);
                            i.setDetail(true);
                            i.setHavePower(true);
                        }
                        else if ("detail".equals(havePower)) {
                            i.setDownload(false);
                            i.setDetail(true);
                            i.setHavePower(true);
                        }
                        else {
                            i.setDownload(false);
                            i.setDetail(false);
                            i.setHavePower(false);
                        }
                        i.setSecurityLeve(r.getSecurityLevel());
                    }
                }

                // 外部输入挂接项目库的数据获取：
                if (!CommonUtil.isEmpty(i.getOriginType())
                    && PlanConstants.PROJECTLIBDOC.equals(i.getOriginType())) {
                    if (!CommonUtil.isEmpty(fileNameMap.get(i.getDocId()))) {
                        i.setDocName(fileNameMap.get(i.getDocId()));
                    }
                    if (!CommonUtil.isEmpty(filePathMap.get(i.getDocId()))) {
                        i.setOriginObjectName(filePathMap.get(i.getDocId()));
                    }
                    if (!CommonUtil.isEmpty(fileIdMap.get(i.getDocId()))) {
                        i.setDocIdShow(fileIdMap.get(i.getDocId()));
                    }
                }
                else if ((!CommonUtil.isEmpty(i.getOriginType()) && i.getOriginType().equals(
                    "PLAN"))
                         && (!CommonUtil.isEmpty(i.getOriginTypeExt()) && i.getOriginTypeExt().equals(
                             PlanConstants.DELIEVER_EN))) {
                    PlanDto curPlan = planService.getPlanEntity(i.getOriginObjectId());
                    // 外部输入挂接计划的数据获取：
                    List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                    if (!CommonUtil.isEmpty(curPlan)) {
                        projDocRelationList = inputsService.getDocRelationList(curPlan,
                            UserUtil.getInstance().getUser().getId());
                    }
                    ProjDocVo projDoc = new ProjDocVo();
                    if (!CommonUtil.isEmpty(projDocRelationList)) {
                        for (ProjDocVo vo : projDocRelationList) {
                            if (vo.getDeliverableId().equals(i.getOriginDeliverablesInfoId())) {
                                projDoc = vo;
                                break;
                            }
                        }
                    }
                    i.setOriginObjectName(curPlan.getPlanNumber() + "." + curPlan.getPlanName());
                    i.setDocId(projDoc.getDocId());
                    i.setDocIdShow(projDoc.getDocId());
                    i.setDocName(projDoc.getDocName());
                    i.setExt1(String.valueOf(projDoc.isDownload()));
                    i.setExt2(String.valueOf(projDoc.isHavePower()));
                    i.setExt3(String.valueOf(projDoc.isDetail()));
                }
            }
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
        TagUtil.ajaxResponse(response, json);

    }

    /**
     * 变更输入删除数据返回：
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "delList")
    private void delList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");

        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        List<InputsDto> inputList = new ArrayList<InputsDto>();
        if (list.size() > 0) {
            plan = list.get(0);
            InputsDto inputs = new InputsDto();
            inputs.setUseObjectId(plan.getId());
            inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            inputList = inputsService.queryInputsDetailList(inputs);
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
        TagUtil.ajaxResponse(response, json);

    }

    /**
     * 查询活动输入信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeInputList")
    private void changeInputList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskInputsVo> changeInputList = new ArrayList<FlowTaskInputsVo>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for (FlowTaskVo flowtask : changeFlowTaskList) {
            if (flowtask.getCellId().equals(cellId)) {
                changeInputList = flowtask.getInputList();
                break;
            }
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(changeInputList);
        TagUtil.ajaxResponse(response, json);

    }

    /**
     * 查询活动输入信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "inputinlist")
    private void inputinlist(HttpServletRequest req, HttpServletResponse response) {
        String useObjectId = req.getParameter("useObjectId");
        String useObjectType = req.getParameter("useObjectType");
        InputsDto inputs = new InputsDto();
        inputs.setUseObjectId(useObjectId);
        inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<InputsDto> inputList = inputsService.queryInputsDetailList(inputs);
        String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 删除输入
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelInput")
    @ResponseBody
    public AjaxJson doDelInput(InputsDto input, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            input = inputsService.getInputEntity(input.getId());
            if (input != null) {
                inputsService.deleteInputs(input);
            }
            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            j.setSuccess(false);
            log.error(message, e, input.getId(), "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + input.getId()};// 异常原因：{0}；异常描述：{1}
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
     * 删除输入
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelChangeInputs")
    @ResponseBody
    public AjaxJson doDelChangeInputs(String ids, String cellId, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            List<FlowTaskInputsVo> list = new ArrayList<FlowTaskInputsVo>();
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            List<FlowTaskInputsVo> changeInputList = new ArrayList<FlowTaskInputsVo>();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(cellId)) {
                    changeInputList = f.getInputList();
                    break;
                }
            }
            String[] idsArr = ids.split(",");
            for (String id : idsArr) {

                for (FlowTaskInputsVo in : changeInputList) {
                    if (in.getId().equals(id)) {
                        list.add(in);
                        break;
                    }
                }

            }
            changeInputList.removeAll(list);
            j.setSuccess(true);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            j.setSuccess(false);
            log.error(message, e, cellId, "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + cellId};// 异常原因：{0}；异常描述：{1}
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
     * 查询活动输出信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "outputList")
    private void outputList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");

        Map<String, String> planIdAndPlanNameMap = new HashMap<String, String>();
        PlanDto conditionPlan = new PlanDto();
        conditionPlan.setParentPlanId(parentPlanId);
        FeignJson planIdAndplanNameJson = planService.queryPlanIdAndNameMap(parentPlanId);
        if (planIdAndplanNameJson.isSuccess()) {
            Map<String, Object> map = new HashMap<>();
            map = planIdAndplanNameJson.getAttributes();
            planIdAndPlanNameMap = (Map<String, String>)map.get("planIdAndplanNameMap");
        }

        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        List<DeliverablesInfoDto> outputList = new ArrayList<DeliverablesInfoDto>();
        if (!CommonUtil.isEmpty(list)) {
            plan = list.get(0);
            DeliverablesInfoDto outputs = new DeliverablesInfoDto();
            outputs.setUseObjectId(plan.getId());
            outputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            outputList = deliverablesInfoService.queryDeliverableList(outputs, 1, 10, false);

            PlanDto planNew = new PlanDto();
            planNew = planService.getPlanEntity(plan.getId());
            List<ProjDocVo> projDocRelationList = getDocRelationList(planNew);
            List<InputsDto> inputList = inputsService.queryInputsDetailListForString(parentPlanId);
            if (!CommonUtil.isEmpty(projDocRelationList)) {
                for (DeliverablesInfoDto deli : outputList) {
                    for (ProjDocVo p : projDocRelationList) {
                        if (deli.getName().equals(p.getDeliverableName())) {
                            if (StringUtils.isNotEmpty(p.getDocId())) {
                                deli.setDocId(p.getDocId());
                                deli.setDocName(p.getDocName());
                                String havePower = planService.getOutPower(p.getDocId(),
                                    plan.getId(), UserUtil.getCurrentUser().getId());
                                if ("downloadDetail".equals(havePower)) {
                                    deli.setDownload(true);
                                    deli.setDetail(true);
                                    deli.setHavePower(true);
                                }
                                else if ("detail".equals(havePower)) {
                                    deli.setDownload(false);
                                    deli.setDetail(true);
                                    deli.setHavePower(true);
                                }
                                else {
                                    deli.setDownload(false);
                                    deli.setDetail(false);
                                    deli.setHavePower(false);
                                }
                                deli.setSecurityLevel(p.getSecurityLevel());
                            }
                            break;
                        }
                    }
                    String result = "";
                    // 找出该计划的父计划，通过父计划查询出所有的输出，根据输出的来源id匹配，显示出去向：
                    if (!CommonUtil.isEmpty(inputList)) {
                        for (InputsDto curInputs : inputList) {
                            if (!CommonUtil.isEmpty(curInputs.getOriginDeliverablesInfoId())
                                && deli.getId().equals(curInputs.getOriginDeliverablesInfoId())) {
                                String planName = planIdAndPlanNameMap.get(curInputs.getUseObjectId());
                                if (StringUtil.isNotEmpty(result)) {
                                    result = result + "," + planName;
                                }
                                else {
                                    result = planName;
                                }
                            }
                        }
                    }

                    deli.setResult(result);
                }
            }
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(outputList);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 查询活动输出信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeOutputList")
    private void changeOutputList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskDeliverablesInfoVo> changeOutputList = new ArrayList<FlowTaskDeliverablesInfoVo>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        Map<String, String> planIdAndPlanNameMap = new HashMap<String, String>();
//        FeignJson flowTaskParentIdJson = planService.getPlanIdByFlowTaskParentId(parentPlanId);
//        String flowTaskParentId = flowTaskParentIdJson.getObj().toString();
        // if(CommonUtil.isEmpty(flowTaskParentId)){
        if (StringUtils.isEmpty(cellId)) {
            cellId = (String)req.getSession().getAttribute("cellIdForDocument");
        }

        List<FlowTaskInputsVo> inputVoList = new ArrayList<FlowTaskInputsVo>();
        for (FlowTaskVo f : changeFlowTaskList) {
            planIdAndPlanNameMap.put(f.getId(), f.getPlanName());
            inputVoList.addAll(f.getInputList());
        }
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getCellId().equals(cellId)) {
//                String flowTaskId = f.getId();
                List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                if (StringUtil.isNotEmpty(f.getPlanId())) {
                    PlanDto planNew = new PlanDto();
                    planNew = planService.getPlanEntity(f.getPlanId());
                    projDocRelationList = getDocRelationList(planNew);
                }
                changeOutputList = f.getOutputList();
                for (FlowTaskDeliverablesInfoVo deli : changeOutputList) {
                    for (ProjDocVo p : projDocRelationList) {
                        if (deli.getName().equals(p.getDeliverableName())) {
                            if (StringUtils.isNotEmpty(p.getDocId())) {
                                deli.setDocId(p.getDocId());
                                deli.setDocName(p.getDocName());
                                String havePower = planService.getOutPower(p.getDocId(),
                                    f.getPlanId(), UserUtil.getCurrentUser().getId());
                                if ("downloadDetail".equals(havePower)) {
                                    deli.setDownload(true);
                                    deli.setDetail(true);
                                    deli.setHavePower(true);
                                }
                                else if ("detail".equals(havePower)) {
                                    deli.setDownload(false);
                                    deli.setDetail(true);
                                    deli.setHavePower(true);
                                }
                                else {
                                    deli.setDownload(false);
                                    deli.setDetail(false);
                                    deli.setHavePower(false);
                                }
                                deli.setSecurityLevel(p.getSecurityLevel());
                            }
                            break;
                        }
                    }

                    String result = "";
                    // 找出该计划的父计划，通过父计划查询出所有的输出，根据输出的来源id匹配，显示出去向：
                    Map<String, String> toNameMap = new HashMap<String, String>();
                    if (!CommonUtil.isEmpty(inputVoList)) {
                        for (FlowTaskInputsVo curInputs : inputVoList) {
                            if (!CommonUtil.isEmpty(curInputs.getOriginDeliverablesInfoId())
                                && deli.getId().equals(curInputs.getOriginDeliverablesInfoId())) {
                                String planName = planIdAndPlanNameMap.get(curInputs.getUseObjectId());
                                if (CommonUtil.isEmpty(toNameMap.get(planName))) {
                                    if (StringUtil.isNotEmpty(result)) {
                                        result = result + "," + planName;
                                    }
                                    else {
                                        result = planName;
                                    }
                                    toNameMap.put(planName, planName);
                                }
                            }
                        }
                    }
                    deli.setResult(result);
                }
                break;
            }
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(changeOutputList.size(), changeOutputList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 查询活动输出信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "outputListRe")
    private void outputListRe(HttpServletRequest req, HttpServletResponse response) {
        String cellIdForDocument = (String)req.getSession().getAttribute("cellIdForDocument");
        String parentPlanIdForDocument = (String)req.getSession().getAttribute(
            "parentPlanIdForDocument");
        PlanDto plan = new PlanDto();
        plan.setCellId(cellIdForDocument);
        plan.setParentPlanId(parentPlanIdForDocument);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        List<DeliverablesInfoDto> outputList = new ArrayList<DeliverablesInfoDto>();
        if (list.size() > 0) {
            plan = list.get(0);
            DeliverablesInfoDto outputs = new DeliverablesInfoDto();
            outputs.setUseObjectId(plan.getId());
            outputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            outputList = deliverablesInfoService.queryDeliverableList(outputs, 1, 10, false);

            PlanDto planNew = new PlanDto();
            planNew = planService.getPlanEntity(plan.getId());
            List<ProjDocVo> projDocRelationList = getDocRelationList(planNew);

            for (DeliverablesInfoDto deli : outputList) {

                for (ProjDocVo p : projDocRelationList) {
                    if (deli.getName().equals(p.getDeliverableName())) {
                        if (StringUtils.isNotEmpty(p.getDocId())) {
                            deli.setDocId(p.getDocId());
                            deli.setDocName(p.getDocName());
                            String havePower = planService.getOutPower(deli.getDocId(),
                                parentPlanIdForDocument, UserUtil.getCurrentUser().getId());
                            if ("downloadDetail".equals(havePower)) {
                                deli.setDownload(true);
                                deli.setDetail(true);
                                deli.setHavePower(true);
                            }
                            else if ("detail".equals(havePower)) {
                                deli.setDownload(false);
                                deli.setDetail(true);
                                deli.setHavePower(true);
                            }
                            else {
                                deli.setDownload(false);
                                deli.setDetail(false);
                                deli.setHavePower(false);
                            }
                        }
                        break;
                    }
                }

                String result = "";
                PlanDto preposePlan = new PlanDto();
                preposePlan.setPreposeIds(deli.getUseObjectId());
                List<PreposePlanDto> preposeList = preposePlanService.getPostposesByPreposeId(preposePlan);
                for (PreposePlanDto prepose : preposeList) {
                    PlanDto postpose = planService.getPlanEntity(prepose.getPlanId());
                    if (postpose != null) {
                        InputsDto input = new InputsDto();
                        input.setUseObjectId(postpose.getId());
                        input.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                        input.setName(deli.getName());
                        input.setOrigin(prepose.getPreposePlanInfo().getPlanName());
                        List<InputsDto> inputList = inputsService.queryInputList(input, 1, 10, false);
                        if (!CommonUtil.isEmpty(inputList)) {
                            if (StringUtil.isNotEmpty(result)) {
                                result = result + "," + postpose.getPlanName();
                            }
                            else {
                                result = postpose.getPlanName();
                            }
                        }
                    }
                }
                deli.setResult(result);
            }
        }
        String json = com.alibaba.fastjson.JSONArray.toJSONString(outputList);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 删除输入
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDeloutput")
    @ResponseBody
    public AjaxJson doDeloutput(DeliverablesInfoDto output, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            output = deliverablesInfoService.getDeliverablesInfoEntity(output.getId());
            if (output != null) {
                PlanDto useObject = planService.getPlanEntity(output.getUseObjectId());
                DeliveryStandardDto standard = deliveryStandardService.getDeliveryStandardByName(output.getName());
                deliverablesInfoService.deleteDeliverablesById(output.getId());
                inputsService.deleteInputsByOriginDeliverables(output.getId(),PlanConstants.USEOBJECT_TYPE_PLAN);
                // 同步研发流程任务删除相关数据：
                rdFlowTaskFlowResolveService.doDeloutputByPlanOutputId(output.getId());

                List<String> httpUrls = new ArrayList<String>();
                //TODO..
                List<OutwardExtensionDto> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"flowResolveCategoryHttpServer");
                if (!CommonUtil.isEmpty(outwardExtensionList)) {
                    for (OutwardExtensionDto ext : outwardExtensionList) {
                        if (!CommonUtil.isEmpty(ext.getUrlList())) {
                            for (OutwardExtensionUrlDto out : ext.getUrlList()) {
                                if ("update".equals(out.getOperateCode())) {
                                    httpUrls.add(out.getOperateUrl());
                                }
                            }
                        }
                    }
                }
                if (!CommonUtil.isEmpty(httpUrls)) {
                    String parentPlanId = "";
                    String planId = "";
                    if (!CommonUtil.isEmpty(useObject)) {
                        parentPlanId = useObject.getParentPlanId();
                        planId = useObject.getId();
                    }
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("deleteType", "delivery");
                    map.put("parentPlanId", parentPlanId);
                    map.put("planId", planId);
                    if (!CommonUtil.isEmpty(standard)) {
                        map.put("deliveryIds", standard.getId());
                    }

//                    for (String url : httpUrls) {
//                        try {
//                            HttpClientUtil.httpClientPostByTest(url + "&deleteType=delivery", map);
//                        }
//                        catch (Exception e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
                }
            }
            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            j.setSuccess(false);
            log.error(message, e, output.getId(), "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + output.getId()};// 异常原因：{0}；异常描述：{1}
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
     * 删除输出
     *
     * @return
     */

    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelChangeOutput")
    @ResponseBody
    public AjaxJson doDelChangeOutput(FlowTaskDeliverablesInfoVo output, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            Map<String, String> delOutputs = new HashMap<String, String>();
            String flowTaskId = "";
            String deliverId = "";
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getId().equals(output.getUseObjectId())) {
                    flowTaskId = f.getId();
                    if (!CommonUtil.isEmpty(f.getPlanId())) {
                        flowTaskId = f.getPlanId();
                    }
                    for (FlowTaskDeliverablesInfoVo out : f.getOutputList()) {
                        if (out.getId().equals(output.getId())) {
                            deliverId = out.getDeliverId();
                            delOutputs.put(output.getId(), output.getId());
                            f.getOutputList().remove(out);
                            break;
                        }
                    }
                    break;
                }
            }
            if (!CommonUtil.isEmpty(delOutputs)) {
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (!CommonUtil.isEmpty(f.getInputList())) {
                        for (FlowTaskInputsVo in : f.getInputList()) {
                            if (StringUtils.isNotEmpty(delOutputs.get(in.getOriginDeliverablesInfoId()))) {
                                f.getInputList().remove(in);
                                break;
                            }
                        }
                    }
                }
            }
            StringBuffer resStrBuffer = new StringBuffer();
            List<OutwardExtensionDto> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(ResourceUtil.getApplicationInformation().getAppKey(),"activeCategory");
            if (!CommonUtil.isEmpty(outwardExtensionList)) {
                for (OutwardExtensionDto out : outwardExtensionList) {
                    if (!CommonUtil.isEmpty(resStrBuffer.toString())) {
                        resStrBuffer.append("," + out.getOptionValue());
                    }
                    else {
                        resStrBuffer.append(out.getOptionValue());
                    }
                }
            }
            j.setObj(flowTaskId + "," + deliverId + ":" + resStrBuffer.toString());
            j.setSuccess(true);

            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            j.setSuccess(false);
            log.error(message, e, output.getId(), "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + output.getId()};// 异常原因：{0}；异常描述：{1}
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
     * 查询活动资源信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "resourceList")
    private void resourceList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        List<ResourceLinkInfoDto> resourceList = new ArrayList<ResourceLinkInfoDto>();
        if (list.size() > 0) {
            plan = list.get(0);
            ResourceLinkInfoDto resource = new ResourceLinkInfoDto();
            resource.setUseObjectId(plan.getId());
            resource.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            resourceList = resourceLinkInfoService.queryResourceList(resource, 1, 10, false);
            Map<String, ResourceDto> resourceMap = new HashMap<String, ResourceDto>();
            List<ResourceDto> resourceListTemp = resourceService.getAllResourceInfos();
            for (ResourceDto r : resourceListTemp) {
                resourceMap.put(r.getId(), r);
            }
            for (ResourceLinkInfoDto info : resourceList) {
                if (resourceMap.get(info.getResourceId()) != null) {
                    info.setResourceName(resourceMap.get(info.getResourceId()).getName());
                    info.setResourceType(resourceMap.get(info.getResourceId()).getPath());
                }
            }
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(resourceList.size(), resourceList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 查询活动资源信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeResourceList")
    private void changeResourceList(HttpServletRequest req, HttpServletResponse response) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskResourceLinkInfoVo> changeResourceList = new ArrayList<FlowTaskResourceLinkInfoVo>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getCellId().equals(cellId)) {
                changeResourceList = f.getResourceLinkList();
                break;
            }
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(changeResourceList.size(), changeResourceList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceListfresh")
    public void resourceListfresh(HttpServletRequest request, HttpServletResponse response) {
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        PlanDto task = new PlanDto();
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
        if (!CommonUtil.isEmpty(childList)) {
            for (PlanDto child : childList) {
                if (cellId.equals(child.getCellId())) {
                    task = child;
                    break;
                }
            }
        }

        List<ResourceLinkInfoDto> resourceList = new ArrayList<ResourceLinkInfoDto>();
        if (task != null && StringUtils.isNotEmpty(task.getId())) {
            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setUseObjectId(task.getId());
            resourceList = resourceLinkInfoService.queryResourceList(resourceLinkInfo, 1, 10,
                false);
            Map<String, ResourceDto> resourceMap = new HashMap<String, ResourceDto>();
            List<ResourceDto> resourceListTemp = resourceService.getAllResourceInfos();
            for (ResourceDto r : resourceListTemp) {
                resourceMap.put(r.getId(), r);
            }
            for (ResourceLinkInfoDto info : resourceList) {
                if (resourceMap.get(info.getResourceId()) != null) {
                    info.setResourceName(resourceMap.get(info.getResourceId()).getName());
                    info.setResourceType(resourceMap.get(info.getResourceId()).getPath());
                }
            }
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(resourceList.size(), resourceList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 查询项目成员
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "projectMembers")
    @ResponseBody
    public void projectMembers(HttpServletRequest request, HttpServletResponse response) {
        String parentPlanId = request.getParameter("parentPlanId");
        List<TSUserDto> userList = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto plan = planService.getPlanEntity(parentPlanId);
            if (plan != null) {
                Project project = projectService.getProjectEntity(plan.getProjectId());
                if (project != null) {
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
        }

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userList) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj);
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
     * 流程任务加入编译器
     *
     * @param temp
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateXml")
    @ResponseBody
    public AjaxJson doUpdateXml(PlanDto temp, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            String cellIds = request.getParameter("cellIds");
            String cellContact = request.getParameter("cellContact");

            taskFlowResolveService.updateFlowTasks(temp, cellIds, parentPlanId, cellContact,UserUtil.getCurrentUser().getId());

            // 创建父任务:
            PlanDto parent = planService.getPlanEntity(parentPlanId);
            String outUserId = UserUtil.getCurrentUser().getId();
            TSUserDto currentUser = UserUtil.getCurrentUser();
            FeignJson linkPlanFlagJson = rdFlowTaskFlowResolveService.isHaveLinkPlanId(parentPlanId);
            String linkPlanFlag = linkPlanFlagJson.getObj().toString();
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String linkPlanFlagList = rdfConfigSupport.isHaveLinkPlanId(parentPlanId);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(linkPlanFlagList, AjaxJson.class);
//            String linkPlanFlag = (String)ajaxJson.getObj();

            if ("false".equals(linkPlanFlag)) {
                Project parentProject = projectService.getProjectEntity(parent.getProjectId());
                // 添加到任务流程中去：
                RDTaskVO rdTaskVO = new RDTaskVO();
                rdTaskVO.setCreateBy(currentUser.getId());
                rdTaskVO.setCreateFullName(currentUser.getRealName());
                rdTaskVO.setCreateName(currentUser.getUserName());
                rdTaskVO.setCreateTime(new Date());
                rdTaskVO.setParentPlanId(parent.getId());
                rdTaskVO.setPlanName(parent.getPlanName());
                rdTaskVO.setWorkTimeType(parentProject.getProjectTimeType());
                rdTaskVO.setOwner(parent.getOwner());
                rdTaskVO.setPlanStartTime(parent.getPlanStartTime());
                rdTaskVO.setPlanStartTimeStr(DateUtil.formatDate(parent.getPlanStartTime(),
                    DateUtil.FORMAT_ONE));
                rdTaskVO.setPlanEndTime(parent.getPlanEndTime());
                rdTaskVO.setPlanEndTimeStr(DateUtil.formatDate(parent.getPlanEndTime(),
                    DateUtil.FORMAT_ONE));
                rdTaskVO.setWorkTime(parent.getWorkTime());
                rdTaskVO.setRemark(parent.getRemark());
                rdTaskVO.setAssigner(parent.getAssigner());
                rdTaskVO.setAssignTime(parent.getAssignTime());
                rdTaskVO.setAssignTimeStr(DateUtil.formatDate(parent.getAssignTime(),
                    DateUtil.FORMAT_ONE));
                // 相关的下达信息也要添加：
                String approveType = "";
                String procInstId = "";
                if (!CommonUtil.isEmpty(parent.getFormId())) {
                    ApprovePlanFormDto approvePlanForm = planService.getApprovePlanFormEntity(parent.getFormId());
                    if (!CommonUtil.isEmpty(approvePlanForm)) {
                        approveType = approvePlanForm.getApproveType();
                        procInstId = approvePlanForm.getProcInstId();
                    }
                }
                rdFlowTaskFlowResolveService.getSaveRdTaskInfo(rdTaskVO, "", outUserId, approveType, procInstId,
                    parent.getFormId(), "", "", "");
            }
            // 编辑器xml同步保存到相关任务：
            String userId = UserUtil.getCurrentUser().getId();
            Map<String, String> in = new HashMap<>();
            in.put("xml",parent.getFlowResolveXml());
            rdFlowTaskFlowResolveService.getDoUpdateXml(in, userId, parentPlanId,
                cellIds, cellContact);

            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            throw new GWException(GWConstants.ERROR_2003, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 流程任务加入编译器
     *
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateXmlForChange")
    @ResponseBody
    public AjaxJson doUpdateXmlForChange(FlowTaskParentVo temp, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            String cellIds = request.getParameter("cellIds");
            String cellContact = request.getParameter("cellContact");
            FlowTaskParentVo flowTaskParent = (FlowTaskParentVo)request.getSession().getAttribute(
                PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId);
            flowTaskParent.setFlowResolveXml(temp.getFlowResolveXml());
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)request.getSession().getAttribute(
                PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);

            List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = (List<ChangeFlowTaskCellConnectVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + parentPlanId);

            ChangeFlowTaskInfoVO vo = doUpdateXmlForChange(flowTaskParent,
                changeFlowTaskList, flowTaskPreposeList, changeFlowTaskConnectList, cellIds,
                parentPlanId, cellContact);

            request.getSession().setAttribute(PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId,
                flowTaskParent);
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, vo.getChangeFlowTaskList());

            request.getSession().setAttribute(
                PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId,
                vo.getFlowTaskPreposeList());

            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + parentPlanId,
                vo.getChangeFlowTaskConnectList());

            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            throw new GWException(GWConstants.ERROR_2003, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 流程任务加入编译器
     * @param flowTaskParent
     * @param changeFlowTaskList
     * @param flowTaskPreposeList
     * @param changeFlowTaskConnectList
     * @param cellIds
     * @param parentPlanId
     * @param cellContact
     */
    @ResponseBody
    private ChangeFlowTaskInfoVO doUpdateXmlForChange(FlowTaskParentVo flowTaskParent,
                                                     List<FlowTaskVo> changeFlowTaskList,
                                                     List<FlowTaskPreposeVo> flowTaskPreposeList,
                                                     List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList,
                                                     String cellIds, String parentPlanId,
                                                     String cellContact) {
        changeFlowTaskList = saveChangeFlowTaskList(cellIds, changeFlowTaskList,
            flowTaskPreposeList);
        changeFlowTaskConnectList = saveChangeFlowTaskConnectList(parentPlanId, cellContact,
            changeFlowTaskList, changeFlowTaskConnectList);
        flowTaskPreposeList = saveChangeFlowTaskPreposeList(changeFlowTaskConnectList,
            changeFlowTaskList, flowTaskPreposeList);
        changeFlowTaskList = saveChangeFlowTasks(flowTaskParent, changeFlowTaskList,
            changeFlowTaskConnectList);
        ChangeFlowTaskInfoVO vo = new ChangeFlowTaskInfoVO();
        vo.setChangeFlowTaskList(changeFlowTaskList);
        vo.setFlowTaskPreposeList(flowTaskPreposeList);
        vo.setChangeFlowTaskConnectList(changeFlowTaskConnectList);
        return vo;
    }

    /**
     * Description: <br>
     *
     * @param cellIds
     * @param changeFlowTaskList
     * @param flowTaskPreposeList
     * @return
     * @see
     */

    private List<FlowTaskVo> saveChangeFlowTaskList(String cellIds,
                                                    List<FlowTaskVo> changeFlowTaskList,
                                                    List<FlowTaskPreposeVo> flowTaskPreposeList) {
        String[] cells = cellIds.split(",");
        boolean flag = true;
        List<FlowTaskVo> flowTaskList = new ArrayList<FlowTaskVo>();
        if (changeFlowTaskList.size() > 0) {
            for (FlowTaskVo child : changeFlowTaskList) {
                if (cells.length > 0) {
                    for (int i = 0; i < cells.length; i++ ) {
                        if (child.getCellId().equals(cells[i])) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        // 删除该节点输出的传递
                        for (FlowTaskPreposeVo preposePlan : flowTaskPreposeList) {
                            if (child.getId().equals(preposePlan.getPreposeId())) {
                                if (StringUtils.isNotEmpty(preposePlan.getFlowTaskId())) {
                                    FlowTaskVo prepose = new FlowTaskVo();
                                    for (FlowTaskVo flowTask : changeFlowTaskList) {
                                        if (preposePlan.getFlowTaskId().equals(flowTask.getId())) {
                                            prepose = flowTask;
                                            break;
                                        }
                                    }
                                    if (prepose != null) {
                                        InputsDto postposeIn = new InputsDto();
                                        postposeIn.setUseObjectId(prepose.getId());
                                        postposeIn.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                        postposeIn.setOrigin(child.getPlanName());
                                        List<FlowTaskInputsVo> postposeInputList = prepose.getInputList();
                                        List<FlowTaskInputsVo> postposeInputList2 = prepose.getInputList();
                                        for (int i = 0; i < postposeInputList.size(); i++ ) {
                                            if (postposeIn.getUseObjectId().equals(prepose.getId())
                                                && postposeIn.getUseObjectType().equals(
                                                    PlanConstants.USEOBJECT_TYPE_PLAN)
                                                && postposeIn.getOrigin().equals(
                                                    child.getPlanName())) {
                                                prepose.getInputList().remove(
                                                    postposeInputList2.get(i));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        List<FlowTaskPreposeVo> preposeList = new ArrayList<FlowTaskPreposeVo>();
                        // 删除与该节点相关的前后置关系
                        for (FlowTaskPreposeVo preposePlan : flowTaskPreposeList) {
                            if (!child.getId().equals(preposePlan.getPreposeId())
                                && !child.getId().equals(preposePlan.getFlowTaskId())) {
                                preposeList.add(preposePlan);
                            }
                        }
                        flowTaskPreposeList.clear();
                        flowTaskPreposeList.addAll(preposeList);
                    }
                    else {
                        // TODO
//                        child.setPlanStartTime(null);
//                        child.setPlanEndTime(null);
                        flowTaskList.add(child);
                    }
                }
                flag = true;
            }
        }
        return flowTaskList;
    }


    /**
     * Description: <br>
     *
     * @param parentPlanId
     * @param cellContact
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
     * @return
     * @see
     */

    private List<ChangeFlowTaskCellConnectVo> saveChangeFlowTaskConnectList(String parentPlanId,
                                                                            String cellContact,
                                                                            List<FlowTaskVo> changeFlowTaskList,
                                                                            List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList) {
        changeFlowTaskConnectList = new ArrayList<ChangeFlowTaskCellConnectVo>();
        if (StringUtils.isNotEmpty(cellContact)) {
            String[] cellContactSplit = cellContact.split(",");
            for (String cellContacts : cellContactSplit) {
                ChangeFlowTaskCellConnectVo conn = new ChangeFlowTaskCellConnectVo();
                conn.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
                conn.setParentPlanId(parentPlanId);
                String[] cell = cellContacts.split("-");
                conn.setCellId(cell[0]);
                conn.setTargetId(cell[1]);
                changeFlowTaskConnectList.add(conn);
            }
            for (ChangeFlowTaskCellConnectVo FlowTaskCellConnectVo : changeFlowTaskConnectList) {
                // 通过节点与模板ID获得基本信息ID
                if (StringUtils.isNotEmpty(FlowTaskCellConnectVo.getCellId())) {
                    for (FlowTaskVo f : changeFlowTaskList) {
                        if (FlowTaskCellConnectVo.getCellId().equals(f.getCellId())) {
                            FlowTaskCellConnectVo.setInfoId(f.getId());
                            break;
                        }
                    }
                }
                if (StringUtils.isNotEmpty(FlowTaskCellConnectVo.getTargetId())) {
                    for (FlowTaskVo f : changeFlowTaskList) {
                        if (FlowTaskCellConnectVo.getTargetId().equals(f.getCellId())) {
                            FlowTaskCellConnectVo.setTargetInfoId(f.getId());
                            break;
                        }
                    }
                }
            }
        }
        return changeFlowTaskConnectList;
    }

    /**
     * Description: <br>
     *
     * @param changeFlowTaskConnectList
     * @param changeFlowTaskList
     * @param flowTaskPreposeList
     * @return
     * @see
     */

    private List<FlowTaskPreposeVo> saveChangeFlowTaskPreposeList(List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList,
                                                                  List<FlowTaskVo> changeFlowTaskList,
                                                                  List<FlowTaskPreposeVo> flowTaskPreposeList) {
        flowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();
        // 更新节点前后置关系及开始结束时间和工期
        for (ChangeFlowTaskCellConnectVo cellConnect : changeFlowTaskConnectList) {
            if (!TaskProcConstants.TASK_CELL_START.equals(cellConnect.getCellId())
                && !TaskProcConstants.TASK_CELL_END.equals(cellConnect.getTargetId())) {
                FlowTaskVo p1 = new FlowTaskVo();
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (cellConnect.getCellId().equals(f.getCellId())) {
                        p1 = f;
                        break;
                    }
                }
                FlowTaskVo p2 = new FlowTaskVo();
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (cellConnect.getTargetId().equals(f.getCellId())) {
                        p2 = f;
                        break;
                    }
                }
                FlowTaskPreposeVo prepose = new FlowTaskPreposeVo();
                prepose.setPreposeId(p1.getId());
                prepose.setFlowTaskId(p2.getId());
                flowTaskPreposeList.add(prepose);
            }
        }
        return flowTaskPreposeList;
    }

    /**
     * Description: <br>
     *
     * @param flowTaskParent
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
     * @see
     */
    private List<FlowTaskVo> saveChangeFlowTasks(FlowTaskParentVo flowTaskParent,
                                                 List<FlowTaskVo> changeFlowTaskList,
                                                 List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList) {
        PlanDto parent = planService.getPlanEntity(flowTaskParent.getParentId());
        Project parentProject = projectService.getProjectEntity(parent.getProjectId());
        for (ChangeFlowTaskCellConnectVo cellConnect : changeFlowTaskConnectList) {
            if (cellConnect.getCellId().equals(TaskProcConstants.TASK_CELL_START)) {
                for (FlowTaskVo info : changeFlowTaskList) {
                    if (cellConnect.getTargetId().equals(info.getCellId())) {
                        info.setPlanStartTime(parent.getPlanStartTime());
                        Date date = (Date)info.getPlanStartTime().clone();
                        if (parentProject != null
                            && StringUtils.isNotEmpty(parentProject.getProjectTimeType())) {
                            if (ProjectConstants.WORKDAY.equals(parentProject.getProjectTimeType())) {
                                info.setPlanEndTime(DateUtil.nextWorkDay(
                                    date, Integer.valueOf(info.getWorkTime()) - 1));
                            }
                            else if (ProjectConstants.COMPANYDAY.equals(parentProject.getProjectTimeType())) {
                                Map<String, Object> params = new HashMap<String, Object>();
                                params.put("startDate",date);
                                params.put("days",Integer.valueOf(info.getWorkTime()) - 1);
                                info.setPlanEndTime(calendarService.getNextWorkingDay(appKey,params));
                            }
                            else {
                                info.setPlanEndTime(TimeUtil.getExtraDate(date,
                                    Integer.valueOf(info.getWorkTime()) - 1));
                            }
                        }
                        else {
                            info.setPlanEndTime(TimeUtil.getExtraDate(date,
                                Integer.valueOf(info.getWorkTime()) - 1));
                        }
                        List<ChangeFlowTaskCellConnectVo> backConns = new ArrayList<ChangeFlowTaskCellConnectVo>();
                        for (ChangeFlowTaskCellConnectVo conn : changeFlowTaskConnectList) {
                            if (cellConnect.getTargetId().equals(conn.getCellId())) {
                                backConns.add(conn);
                            }
                        }
                        flowResolveWithBackPlan1(info.getId(), backConns, changeFlowTaskList,
                            changeFlowTaskConnectList);
                    }
                }
            }
        }
        return changeFlowTaskList;
    }

    /**
     * 根据流程模板节点保存期后置计划及其相关信息
     *
     * @param preposeId
     * @param backPlans
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
     * @see
     */
    private void flowResolveWithBackPlan1(String preposeId,
                                          List<ChangeFlowTaskCellConnectVo> backPlans,
                                          List<FlowTaskVo> changeFlowTaskList,
                                          List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList) {
        FlowTaskVo prepose = new FlowTaskVo();
        for (FlowTaskVo info : changeFlowTaskList) {
            if (preposeId.equals(info.getId())) {
                prepose = info;
                break;
            }
        }
        Project project = projectService.getProjectEntity(prepose.getProjectId());
        for (ChangeFlowTaskCellConnectVo conn : backPlans) {
            if (!TaskProcConstants.TASK_CELL_END.equals(conn.getTargetId())) {
                FlowTaskVo info = null;
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (conn.getTargetId().equals(f.getCellId())) {
                        info = f;
                        break;
                    }
                }
                if (info != null) {
                    Date proposeEndTime = (Date)prepose.getPlanEndTime().clone();
                    Date startTime;
                    if (project != null && StringUtils.isNotEmpty(project.getProjectTimeType())) {
                        if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                            startTime = DateUtil.nextWorkDay(
                                proposeEndTime, 1);
                            if (info.getPlanStartTime() == null
                                || startTime.getTime() > info.getPlanStartTime().getTime()) {
                                info.setPlanStartTime(startTime);
                                Date date = (Date)info.getPlanStartTime().clone();
                                info.setPlanEndTime(DateUtil.nextWorkDay(
                                    date, Integer.valueOf(info.getWorkTime()) - 1));
                            }
                        }
                        else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("startDate",proposeEndTime);
                            params.put("days",1);
                            startTime = calendarService.getNextWorkingDay(appKey, params);
                            if (info.getPlanStartTime() == null
                                || startTime.getTime() > info.getPlanStartTime().getTime()) {
                                info.setPlanStartTime(startTime);
                                Date date = (Date)info.getPlanStartTime().clone();
                                Map<String, Object> params1 = new HashMap<String, Object>();
                                params1.put("startDate",date);
                                params1.put("days",Integer.valueOf(info.getWorkTime()) - 1);
                                info.setPlanEndTime(calendarService.getNextWorkingDay(appKey,params1));
                            }
                        }
                        else {
                            startTime = TimeUtil.getExtraDate(proposeEndTime, 1);
                            if (info.getPlanStartTime() == null
                                || startTime.getTime() > info.getPlanStartTime().getTime()) {
                                info.setPlanStartTime(startTime);
                                Date date = (Date)info.getPlanStartTime().clone();
                                info.setPlanEndTime(TimeUtil.getExtraDate(date,
                                    Integer.valueOf(info.getWorkTime()) - 1));
                            }
                        }
                    }
                    else {
                        startTime = TimeUtil.getExtraDate(proposeEndTime, 1);
                        if (info.getPlanStartTime() == null
                            || startTime.getTime() > info.getPlanStartTime().getTime()) {
                            info.setPlanStartTime(startTime);
                            Date date = (Date)info.getPlanStartTime().clone();
                            info.setPlanEndTime(TimeUtil.getExtraDate(date,
                                Integer.valueOf(info.getWorkTime()) - 1));
                        }
                    }
                    List<ChangeFlowTaskCellConnectVo> backConns = new ArrayList<ChangeFlowTaskCellConnectVo>();
                    for (ChangeFlowTaskCellConnectVo connenct : changeFlowTaskConnectList) {
                        if (conn.getTargetId().equals(connenct.getCellId())) {
                            backConns.add(connenct);
                        }
                    }
                    flowResolveWithBackPlan1(info.getId(), backConns, changeFlowTaskList,
                        changeFlowTaskConnectList);
                }
            }
        }
    }


    /**
     * 保存流程任务基本信息
     *
     * @param plan
     * @param req
     * @param response
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveFlowTask")
    @ResponseBody
    private AjaxJson saveFlowTask(PlanDto plan, HttpServletRequest req, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        try {
            String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            failMessage = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            failMessageCode = GWConstants.ERROR_2003;
            PlanDto parent = planService.getPlanEntity(plan.getParentPlanId());
            plan.setPreposeIds(req.getParameter("preposeIds"));

            // 创建父任务:
            String outUserId = UserUtil.getCurrentUser().getId();
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
            FeignJson linkPlanFlagJson = rdFlowTaskFlowResolveService.isHaveLinkPlanId(plan.getParentPlanId());
            String linkPlanFlag = linkPlanFlagJson.getObj().toString();
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(linkPlanFlagList, AjaxJson.class);
//            String linkPlanFlag = (String)ajaxJson.getObj();

            if ("false".equals(linkPlanFlag)) {
                Project parentProject = projectService.getProjectEntity(parent.getProjectId());
                // 添加到任务流程中去：
                RDTaskVO rdTaskVO = new RDTaskVO();
                rdTaskVO.setParentPlanId(parent.getId());
                rdTaskVO.setPlanName(parent.getPlanName());
                rdTaskVO.setWorkTimeType(parentProject.getProjectTimeType());
                rdTaskVO.setOwner(parent.getOwner());
                rdTaskVO.setPlanStartTimeStr(DateUtil.formatDate(parent.getPlanStartTime(),
                    DateUtil.FORMAT_ONE));
                rdTaskVO.setPlanEndTimeStr(DateUtil.formatDate(parent.getPlanEndTime(),
                    DateUtil.FORMAT_ONE));
                rdTaskVO.setWorkTime(parent.getWorkTime());
                rdTaskVO.setRemark(parent.getRemark());
                rdTaskVO.setAssigner(parent.getAssigner());
                rdTaskVO.setAssignTimeStr(DateUtil.formatDate(parent.getAssignTime(),
                    DateUtil.FORMAT_ONE));
                // 相关的下达信息也要添加：
                String approveType = "";
                String procInstId = "";
                if (!CommonUtil.isEmpty(parent.getFormId())) {
                    ApprovePlanFormDto approvePlanForm = planService.getApprovePlanFormEntity(parent.getFormId());
                    if (!CommonUtil.isEmpty(approvePlanForm)) {
                        approveType = approvePlanForm.getApproveType();
                        procInstId = approvePlanForm.getProcInstId();
                    }
                }

                rdFlowTaskFlowResolveService.getSaveRdTaskInfo(rdTaskVO, "", outUserId, approveType, procInstId,
                    parent.getFormId(), "", "", "");
            }

             FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
             String switchStr = switchStrJson.getObj().toString();
//            String switchStr = "";
//            try {
//                String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//                AjaxJson ajaxJsonStr = gson.fromJson(switchStrJson, AjaxJson.class);
//                switchStr = (String)ajaxJsonStr.getObj();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
            boolean mustUseStandard = false;
            if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                mustUseStandard = true;
            }
            if (mustUseStandard) {
                NameStandardDto nameStandard = new NameStandardDto();
                nameStandard.setStopFlag("启用");
                List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                Map<String, String> nameStdMap = new HashMap<String, String>();
                for (NameStandardDto std : list) {
                    nameStdMap.put(std.getName(), std.getName());
                }
                if (CommonUtil.isEmpty(nameStdMap)) {
                    j.setSuccess(false);
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return j;
                }
                if (StringUtils.isEmpty(nameStdMap.get(plan.getPlanName()))) {
                    j.setSuccess(false);
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return j;
                }
            }

            if (parent.getPlanName().equals(plan.getPlanName())) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameParentnameSameCheck"));
            }
            else if (checkFlowTaskName(plan)) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"));
            }
            else {
                if (StringUtils.isNotEmpty(plan.getId())) {
                    String oldNameStandardName = "";
                    String oldTaskNameType = "";
                    PlanDto oldInfo = planService.getPlanEntity(plan.getId());
                    boolean needHttp = false;
                    if (!CommonUtil.isEmpty(oldInfo)) {
                        oldNameStandardName = oldInfo.getPlanName();
                        oldTaskNameType = oldInfo.getTaskNameType();
                        if (!CommonUtil.isEmpty(oldTaskNameType)) {
                            if (!CommonUtil.isEmpty(oldTaskNameType)
                                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(oldTaskNameType)
                                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(oldTaskNameType)
                                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(oldTaskNameType)) {
                                needHttp = true;
                            }
                        }
                    }

                    // 1.删除外部前置计划表对应的数据：
                    List<PreposePlanDto> preposeList = preposePlanService.getPreposePlansByPlanId(plan);

                    String outpreposeIds = "";
                    for (PreposePlanDto prepose : preposeList) {
                        PlanDto preposePlan = planService.getPlanEntity(prepose.getPreposePlanId());
                        if (!plan.getParentPlanId().equals(preposePlan.getParentPlanId())) {
                            String id = prepose.getPreposePlanId();
                            if (outpreposeIds.equals("")) {
                                outpreposeIds = id;
                            }
                            else {
                                outpreposeIds = outpreposeIds + "," + id;
                            }
                            preposePlanService.deleteById(prepose.getId());
                        }
                    }

                    String createOrgId = ResourceUtil.getCurrentUserOrg().getId();

                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("plan", plan);
                    params.put("parent", parent);
                    params.put("user", UserUtil.getCurrentUser());
                    params.put("createOrgId", createOrgId);
                    taskFlowResolveService.saveFlowTask1(params);
                    // 同步修改关联的任务的基本信息:
                    String userId = UserUtil.getCurrentUser().getId();
                    String parentPlanId = plan.getParentPlanId();
                    String planName = plan.getPlanName();
                    String owner = plan.getOwner();
                    String remark = plan.getRemark();
                    String planId = plan.getId();
                    String workTime = plan.getWorkTime();
                    String cellId = plan.getCellId();
                    rdFlowTaskFlowResolveService.getSaveTaskInfoFromPlan1(userId, parentPlanId, planName,
                        owner, remark, planId, workTime, cellId);

                    j.setObj(plan.getId());

                    // a.循环选择出需要删除的id ：
                    String preposeIds = (String)req.getParameter("preposeIds");

                    // 找到数据库中的外部ids：

                    String delpreposeIds = "";
                    String[] currentpreposeIds = preposeIds.split(",");

                    for (String preposeid : outpreposeIds.split(",")) {
                        boolean isExit = false;
                        for (int i = 0; i < currentpreposeIds.length; i++ ) {
                            String current = currentpreposeIds[i];
                            if (current.equals(preposeid)) {
                                isExit = true;
                                break;
                            }
                        }
                        if (!isExit) {
                            if (delpreposeIds.equals("")) {
                                delpreposeIds = preposeid;
                            }
                            else {
                                delpreposeIds = delpreposeIds + "," + preposeid;
                            }
                        }
                    }
                    // b.通过id删除掉出入表中的数据：
                    if (StringUtils.isNotEmpty(delpreposeIds)) {
                        List<DeliverablesInfoDto> preposeDeliverables = deliverablesInfoService.getPreposePlanDeliverables(delpreposeIds);

                        InputsDto inputs = new InputsDto();
                        inputs.setUseObjectId(plan.getId());
                        inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                        List<InputsDto> currentinputList = inputsService.queryInputsDetailList(inputs);

                        for (DeliverablesInfoDto deliver : preposeDeliverables) {
                            for (InputsDto in : currentinputList) {
                                if (deliver.getId().equals(in.getOriginDeliverablesInfoId())) {
                                    inputsService.deleteInputs(in);
                                }
                            }
                        }
                    }

                    if (!needHttp && !CommonUtil.isEmpty(plan.getTaskNameType())) {
                        if (!CommonUtil.isEmpty(plan.getTaskNameType())
                            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(plan.getTaskNameType())
                            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(plan.getTaskNameType())
                            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(plan.getTaskNameType())) {
                            needHttp = true;
                        }
                    }

                    if (!plan.getPlanName().equals(oldNameStandardName)) {
//                        if (needHttp) {
//                            List<String> httpUrls = new ArrayList<String>();
//                            List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowResolveCategoryHttpServer");
//                            String deleteType = "node";
//                            if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                                for (OutwardExtension outwardExtension : outwardExtensionList) {
//                                    if (!CommonUtil.isEmpty(outwardExtension)
//                                        && !CommonUtil.isEmpty(outwardExtension.getUrlList())) {
//                                        for (OutwardExtensionUrl url : outwardExtension.getUrlList()) {
//                                            if ("addOrChangeNode".equals(url.getOperateCode())) {
//                                                httpUrls.add(url.getOperateUrl());
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            if (!CommonUtil.isEmpty(httpUrls)) {
//                                Map<String, String> map = new HashMap<String, String>();
//                                map.put("deleteType", deleteType);
//                                map.put("parentPlanId", plan.getParentPlanId());
//                                map.put("planId", plan.getId());
//                                NameStandard nameStandard = nameStandardService.getNameStandardByName(plan.getPlanName());
//                                if (!CommonUtil.isEmpty(nameStandard)) {
//                                    map.put("namestandardId", nameStandard.getId());
//                                }
//                                map.put("userId", UserUtil.getCurrentUser().getId());
//                                for (String operateUrl : httpUrls) {
//                                    if (!CommonUtil.isEmpty(operateUrl)) {
//                                        HttpClientUtil.httpClientPostByTest(operateUrl
//                                                                            + "&deleteType="
//                                                                            + deleteType, map);
//                                    }
//                                }
//                            }
//                        }
                    }
                }
                else {
                    String createOrgId = ResourceUtil.getCurrentUserOrg().getId();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("plan", plan);
                    params.put("parent", parent);
                    params.put("createOrgId", createOrgId);
                    FeignJson curId = taskFlowResolveService.saveFlowTask2(params, UserUtil.getCurrentUser().getId());
                    plan.setId(curId.getObj().toString());
                    // 任务同步保存新的节点信息
                    String userId = UserUtil.getCurrentUser().getId();
                    String parentPlanId = plan.getParentPlanId();
                    String planName = plan.getPlanName();
                    String owner = "";
                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        owner = plan.getOwner();
                    }
                    String remark = "";
                    if(!CommonUtil.isEmpty(plan.getRemark())) {
                        owner = plan.getRemark();
                    }
                    String planId = plan.getId();
                    String workTime = plan.getWorkTime();
                    String cellId = plan.getCellId();
                    rdFlowTaskFlowResolveService.getSaveTaskInfoFromPlan1(userId, parentPlanId, planName,
                        owner, remark, planId, workTime, cellId);

                    j.setObj(plan.getId());
                    boolean needHttp = false;
                    if (!CommonUtil.isEmpty(plan.getTaskNameType())
                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(plan.getTaskNameType())
                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(plan.getTaskNameType())
                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(plan.getTaskNameType())) {
                        needHttp = true;
                    }
                    if (needHttp) {
//TODO..
//                        List<String> httpUrls = new ArrayList<String>();
//                        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowResolveCategoryHttpServer");
//                        String deleteType = "node";
//                        if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                            for (OutwardExtension outwardExtension : outwardExtensionList) {
//                                if (!CommonUtil.isEmpty(outwardExtension)
//                                    && !CommonUtil.isEmpty(outwardExtension.getUrlList())) {
//                                    for (OutwardExtensionUrl url : outwardExtension.getUrlList()) {
//                                        if ("addOrChangeNode".equals(url.getOperateCode())) {
//                                            httpUrls.add(url.getOperateUrl());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (!CommonUtil.isEmpty(httpUrls)) {
//                            Map<String, String> map = new HashMap<String, String>();
//                            map.put("deleteType", deleteType);
//                            map.put("parentPlanId", plan.getParentPlanId());
//                            map.put("planId", plan.getId());
//                            NameStandard nameStandard = nameStandardService.getNameStandardByName(plan.getPlanName());
//                            if (!CommonUtil.isEmpty(nameStandard)) {
//                                map.put("namestandardId", nameStandard.getId());
//                            }
//                            map.put("userId", UserUtil.getCurrentUser().getId());
//                            for (String operateUrl : httpUrls) {
//                                if (!CommonUtil.isEmpty(operateUrl)) {
//                                    HttpClientUtil.httpClientPostByTest(operateUrl
//                                                                        + "&deleteType="
//                                                                        + deleteType, map);
//                                }
//                            }
//                        }
                    }

                }
                j.setSuccess(true);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(failMessage);
            log.error(failMessage, e, "", plan.getId().toString());
            Object[] params = new Object[] {failMessage, plan.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            return j;
        }
    }

    /**
     * 保存流程任务基本信息
     *
     * @param plan
     * @param req
     * @param response
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveFlowTaskForChange")
    @ResponseBody
    private AjaxJson saveFlowTaskForChange(FlowTaskVo plan, HttpServletRequest req,
                                           HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String cellId = req.getParameter("cellId");
        String preposeIds = req.getParameter("preposeIds");
        String allPreposeIds = req.getParameter("allPreposeIds");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            failMessage = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            failMessageCode = GWConstants.ERROR_2003;
            PlanDto parent = planService.getPlanEntity(parentPlanId);
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = (List<ChangeFlowTaskCellConnectVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + parentPlanId);

            FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
             String switchStr = switchStrJson.getObj().toString();
//            String switchStr = "";
//            try {
//                RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//                AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//                switchStr = (String)ajaxJson.getObj();
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
            boolean mustUseStandard = false;
            if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
                || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                mustUseStandard = true;
            }
            if (mustUseStandard) {
                NameStandardDto nameStandard = new NameStandardDto();
                nameStandard.setStopFlag("启用");
                List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
                Map<String, String> nameStdMap = new HashMap<String, String>();
                for (NameStandardDto std : list) {
                    nameStdMap.put(std.getName(), std.getId());
                }
                if (CommonUtil.isEmpty(nameStdMap)) {
                    j.setSuccess(false);
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return j;
                }
                if (StringUtils.isEmpty(nameStdMap.get(plan.getPlanName()))) {
                    j.setSuccess(false);
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return j;
                }
                else {
                    plan.setNameStandardId(nameStdMap.get(plan.getPlanName()));
                }
            }

            if (parent.getPlanName().equals(plan.getPlanName())) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameParentnameSameCheck"));
            }
            else if (checkFlowTaskName(plan, changeFlowTaskList)) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"));
            }
            else {
                if (StringUtils.isNotEmpty(plan.getId())) {
                    boolean nameChange = false;
                    for (FlowTaskVo task : changeFlowTaskList) {
                        if (task.getId().equals(plan.getId())) {
                            if (!task.getPlanName().equals(plan.getPlanName())) {
                                nameChange = true;
                            }
                            break;
                        }
                    }
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("plan", plan);
                    params.put("changeFlowTaskList", changeFlowTaskList);
                    params.put("changeFlowTaskConnectList", changeFlowTaskConnectList);
                    params.put("parent", parent);
                    changeFlowTaskList = taskFlowResolveService.saveFlowTaskForChange1(params);
                    if (nameChange) {

                        j.setObj(plan.getId());
                    }
                }
                else {
                    FlowTaskParentVo flowTaskParent = (FlowTaskParentVo)req.getSession().getAttribute(
                        PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId);

                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("plan", plan);
                    params.put("changeFlowTaskList", changeFlowTaskList);
                    params.put("changeFlowTaskConnectList", changeFlowTaskConnectList);
                    params.put("parent", parent);
                    params.put("flowTaskParent", flowTaskParent);
                    changeFlowTaskList = taskFlowResolveService.saveFlowTaskForChange2(params);
                    for(FlowTaskVo vo : changeFlowTaskList) {
                        if(vo.getPlanName().equals(plan.getPlanName())) {
                            j.setObj(vo.getId());
                            break;
                        }
                    }
                }

                req.getSession().setAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
                j.setSuccess(true);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            j.setMsg(failMessage);
            log.error(failMessage, e, "", plan.getId().toString());
            Object[] params = new Object[] {failMessage, plan.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            return j;
        }
    }

    /**
     * 判断流程任务名称是否重复
     *
     * @param task
     * @return
     * @see
     */
    private boolean checkFlowTaskName(PlanDto task) {
        boolean repeat = false;
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(task.getParentPlanId());
        List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);
        if (StringUtils.isNotEmpty(task.getId())) {
            for (PlanDto p : taskList) {
                if (!p.getId().equals(task.getId()) && p.getPlanName().equals(task.getPlanName())) {
                    repeat = true;
                    break;
                }
            }
        }
        else {
            for (PlanDto p : taskList) {
                if (p.getPlanName().equals(task.getPlanName())) {
                    repeat = true;
                    break;
                }
            }
        }

        return repeat;
    }

    /**
     * 判断流程任务名称是否重复
     *
     * @param task
     * @return
     * @see
     */
    private boolean checkFlowTaskName(FlowTaskVo task, List<FlowTaskVo> changeFlowTaskList) {
        boolean repeat = false;
        if (StringUtils.isNotEmpty(task.getId())) {
            for (FlowTaskVo p : changeFlowTaskList) {
                if (!p.getId().equals(task.getId()) && p.getPlanName().equals(task.getPlanName())) {
                    repeat = true;
                    break;
                }
            }
        }
        else {
            for (FlowTaskVo p : changeFlowTaskList) {
                if (p.getPlanName().equals(task.getPlanName())) {
                    repeat = true;
                    break;
                }
            }
        }
        return repeat;
    }

    /**
     * 流程任务提交审批
     *
     * @param parentPlanId
     * @param status
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "assignTaskPlan")
    public ModelAndView assignTaskPlan(String parentPlanId, String status,
                                       HttpServletRequest request) {
        PlanDto plan = planService.getPlanEntity(parentPlanId);
        request.setAttribute("parentPlanName", plan.getPlanName());
        request.setAttribute("parentPlanId", parentPlanId);
        request.setAttribute("status", status);
        return new ModelAndView("com/glaway/ids/pm/project/task/flowTaskPlanGrant");
    }

    /**
     * 流程任务提交审批
     * @param parentPlanId
     * @param status
     * @param changeType
     * @param changeRemark
     * @param request
     */
    @RequestMapping(params = "assignTaskPlanForChange")
    public ModelAndView assignTaskPlanForChange(String parentPlanId, String status,
                                                String changeType, String changeRemark,
                                                HttpServletRequest request) {
        request.setAttribute("parentPlanId", parentPlanId);
        request.setAttribute("status", status);
        request.setAttribute("changeType", changeType);
        request.setAttribute("changeRemark", changeRemark);
        return new ModelAndView("com/glaway/ids/pm/project/task/flowTaskPlanGrantForChange");
    }

    /**
     * 变更原因页面
     * @param parentPlanId
     * @param status
     * @param request
     */
    @RequestMapping(params = "goChange")
    public ModelAndView goChange(String parentPlanId, String status, HttpServletRequest request) {
        PlanDto plan = planService.getPlanEntity(parentPlanId);
        request.setAttribute("parentPlanName", plan.getPlanName());
        request.setAttribute("parentPlanId", parentPlanId);
        request.setAttribute("status", status);
        //TODO..
//        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//        if (!CommonUtil.isEmpty(outwardExtensionList)) {
//            request.setAttribute("outwards", outwardExtensionList);
//        }
        return new ModelAndView("com/glaway/ids/pm/project/task/flowTaskPlanGrantChange");
    }

    /**
     * 启动并提交流程任务计划下达审批流
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startAssignProcess")
    @ResponseBody
    public AjaxJson startAssignProcess(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowTaskAssignProcessSuccess");
        String leader = request.getParameter("leader");
        String deptLeader = request.getParameter("deptLeader");
        String parentPlanId = request.getParameter("parentPlanId");
        String type = request.getParameter("type");
        try {
            TSUserDto actor = new TSUserDto();
            actor.setId(UserUtil.getInstance().getUser().getId());
            actor.setUserName(UserUtil.getInstance().getUser().getUserName());
            actor.setRealName(UserUtil.getInstance().getUser().getRealName());
            RDTaskVO rdTaskVo = rdFlowTaskFlowResolveService.startAssignProcess(actor, leader, deptLeader, parentPlanId ,type);
            if(!CommonUtil.isEmpty(rdTaskVo)){
                j.setSuccess(true);
                log.info(message, parentPlanId, "");

            }
            else{
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowTaskAssignProcessFailure");
                j.setSuccess(false);
                log.error(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowTaskAssignProcessFailure");
            j.setSuccess(false);
            log.error(message, e, parentPlanId, "");
            Object[] params = new Object[] {message,
                PlanDto.class.getClass() + " oids:" + parentPlanId};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 流程分解任务下达审批查看详情页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignPlanFlow")
    public ModelAndView goAssignPlanFlow(PlanDto plan, HttpServletRequest req) {
        String parentPlanId = "";
        String formId = req.getParameter("taskNumber");
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);

        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        for (int i = 0; i < approve.size(); i++ ) {
            PlanDto p = planService.getPlanEntity(approve.get(i).getPlanId());
            if (p != null && StringUtils.isNotEmpty(p.getParentPlanId())) {
                parentPlanId = p.getParentPlanId();
                break;
            }
        }
        String userId = UserUtil.getInstance().getUser().getId();
        req.setAttribute("userId", userId);
        req.setAttribute("status", PlanConstants.PLAN_EDITING);
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("oldParentPlanId", parentPlanId);
        return new ModelAndView("com/glaway/ids/pm/project/task/goFlowTaskProcess");
    }

    /**
     * 流程分解任务下达审批查看详情页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAssignFlowView")
    public ModelAndView goAssignFlowView(PlanDto plan, HttpServletRequest req) {
        String parentPlanId = "";
        String formId = req.getParameter("taskNumber");
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);

        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        for (int i = 0; i < approve.size(); i++ ) {
            PlanDto p = planService.getPlanEntity(approve.get(i).getPlanId());
            if (p != null && StringUtils.isNotEmpty(p.getParentPlanId())) {
                parentPlanId = p.getParentPlanId();
                break;
            }
        }
        String userId = UserUtil.getInstance().getUser().getId();
        req.setAttribute("userId", userId);
        req.setAttribute("status", PlanConstants.PLAN_EDITING);
        req.setAttribute("parentPlanId", parentPlanId);
        return new ModelAndView("com/glaway/ids/pm/project/task/goFlowTaskProcessView");
    }

    /**
     * 流程分解任务变更审批查看详情页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeFlowView")
    public ModelAndView goChangeFlowView(PlanDto plan, HttpServletRequest req) {
        String parentPlanId = "";
        String formId = req.getParameter("taskNumber");
        String userId = UserUtil.getInstance().getUser().getId();
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        for (int i = 0; i < approve.size(); i++ ) {
            FeignJson  parentPlanIdJson = rdFlowTaskFlowResolveService.getParentPlanIdByFlowTaskId(approve.get(i).getPlanId());
            parentPlanId = parentPlanIdJson.getObj().toString();
            if (!CommonUtil.isEmpty(parentPlanId)) {
                break;
            }

        }
        req.setAttribute("status", PlanConstants.PLAN_ORDERED);
        req.setAttribute("userId", userId);
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("formId", formId);
        return new ModelAndView("com/glaway/ids/pm/project/task/goFlowTaskProcessView");
    }

    /**
     * 流程分解任务变更审批查看详情页面跳转
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeFlow")
    public ModelAndView goChangeFlow(PlanDto plan, HttpServletRequest req) {
        String parentPlanId = "";
        String oldParentPlanId = "";
        String formId = req.getParameter("taskNumber");
        String userId = UserUtil.getInstance().getUser().getId();
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        for (int i = 0; i < approve.size(); i++ ) {
            FeignJson parentPlanIdJson = rdFlowTaskFlowResolveService.getParentPlanIdByFlowTaskId(approve.get(i).getPlanId());
            parentPlanId = parentPlanIdJson.getObj().toString();
            if (!CommonUtil.isEmpty(parentPlanId)) {
                FeignJson oldParentPlanIdJson = rdFlowTaskFlowResolveService.getPlanIdByFlowTaskParentId(parentPlanId);
                oldParentPlanId = oldParentPlanIdJson.getObj().toString();
                break;
            }

        }
        req.setAttribute("status", PlanConstants.PLAN_ORDERED);
        req.setAttribute("userId", userId);
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("oldParentPlanId", oldParentPlanId);
        req.setAttribute("formId", formId);
        return new ModelAndView("com/glaway/ids/pm/project/task/goFlowTaskProcess");
    }

    /**
     * 启动并提交工作流
     * @param ids
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startPlanChange")
    @ResponseBody
    public AjaxJson startPlanChange(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.processStartSuccess");
        try {
            String leader = request.getParameter("leader");
            String deptLeader = request.getParameter("deptLeader");
            leader = URLDecoder.decode(leader, "UTF-8");
            deptLeader = URLDecoder.decode(deptLeader, "UTF-8");
            String changeType = request.getParameter("changeType");
            String changeRemark = request.getParameter("changeRemark");
            String parentPlanId = request.getParameter("parentPlanId");
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            FlowTaskParentVo flowTaskParent = (FlowTaskParentVo)request.getSession().getAttribute(
                PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId);
            List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = (List<ChangeFlowTaskCellConnectVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + parentPlanId);
            FlowTaskOutChangeVO flowTaskOutChangeVO = new FlowTaskOutChangeVO();
            if (!CommonUtil.isEmpty(flowTaskParent)
                && PlanConstants.PLAN_FLOWSTATUS_BACK.equals(flowTaskParent.getIsChangeSingleBack())) {
                boolean isSuccess = taskFlowResolveService.startPlanChangeforBackProcess(leader,
                    deptLeader, flowTaskParent,UserUtil.getCurrentUser().getId());
                if (isSuccess) {
                    Map<String,Object> params = new HashMap<>();
                    params.put("flowTaskParent", flowTaskParent);
                    params.put("changeFlowTaskList", changeFlowTaskList);
                    params.put("changeFlowTaskConnectList", changeFlowTaskConnectList);
                    flowTaskOutChangeVO = taskFlowResolveService.startPlanChange(UserUtil.getCurrentUser().getId(),leader,
                        deptLeader, changeType, changeRemark, params);
                }
            }
            else {
                Map<String,Object> params = new HashMap<>();
                params.put("flowTaskParent", flowTaskParent);
                params.put("changeFlowTaskList", changeFlowTaskList);
                params.put("changeFlowTaskConnectList", changeFlowTaskConnectList);
                flowTaskOutChangeVO = taskFlowResolveService.startPlanChange(UserUtil.getCurrentUser().getId(),leader,
                    deptLeader, changeType, changeRemark, params);
            }
            j.setObj(flowTaskOutChangeVO);
            j.setSuccess(true);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.processStartFailure");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 输入新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HttpServletRequest req) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        String type = req.getParameter("type");
        String preposeIds = req.getParameter("preposeIds");
        String allPreposeIds = req.getParameter("allPreposeIds");

        req.setAttribute("cellId", cellId);
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("type", type);
        req.setAttribute("preposeIds", preposeIds);
        req.setAttribute("allPreposeIds", allPreposeIds);

        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/deliverables-add");
        if (type.equals("OUTPUT")) {}
        else {
            InputsDto inputs = new InputsDto();
            PlanDto plan = new PlanDto();
            plan.setCellId(cellId);
            plan.setParentPlanId(parentPlanId);
            List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);

            // List<Inputs> inputList = new ArrayList<Inputs>();
            if (taskList.size() > 0) {
                plan = taskList.get(0);
                inputs.setUseObjectId(plan.getId());
                inputs.setUseObjectType("PLAN");
                req.setAttribute("useObjectId", plan.getId());
                req.setAttribute("useObjectType", "PLAN");
            }
            req.setAttribute("inputs_", inputs);
        }
        switch (type) {
        // 内部输入
            case "INNERTASK":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/innerInput-add");
                break;
            // 外部输入
            case "DELIEVER":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/outInput-add");
                break;
            // 本地文档
            case "LOCAL":
                mav = new ModelAndView("com/glaway/ids/pm/project/task/taskCellInput-doc");
                break;
            default:
                break;
        }
        return mav;
    }

    /**
     * Description: <br>
     * 上传文件附件<br>
     * Implement: <br>
     * <br>
     *
     * @param request
     * @param response
     * @return 返回
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addFileAttachments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addFileAttachments(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String useObjectId = request.getParameter("useObjectId");
        try {
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String attachmentURL = "";
            List<String> distinctNames = new ArrayList<String>();
            for (MultipartFile mf : fileMap.values()) {
                // 获取文件流
                InputStream fos = mf.getInputStream();
                String attachmentName = mf.getOriginalFilename();
                attachmentURL = JackrabbitUtil.handleFileUpload(fos, attachmentName,
                    JackrabbitConstants.PLAN_RESOLVE_FILE_PATH, false);
                // 交付项名称去掉文件名后缀
                String delieverName = attachmentName.substring(0, attachmentName.lastIndexOf("."));
                distinctNames.add(delieverName);
                InputsDto currentinputs = new InputsDto();
                currentinputs.setUseObjectId(useObjectId);
                currentinputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                currentinputs.setOriginType(PlanConstants.LOCAL_EN);
                currentinputs.setName(delieverName);
                currentinputs.setDocName(attachmentName);
                currentinputs.setDocId(attachmentURL);
                taskFlowResolveService.doAddInputsNew("", currentinputs,UserUtil.getCurrentUser().getId());
            }

            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.uploadSuccess"));
            j.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.uploadFailure"));
            j.setSuccess(false);
        }
        finally {
            return j;
        }
    }

    /**
     * Description: <br>
     * 上传文件附件<br>
     * Implement: <br>
     * <br>
     *
     * @param request
     * @param response
     * @return 返回
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addFileAttachmentsChange", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson addFileAttachmentsChange(HttpServletRequest request,
                                             HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String parentPlanId = request.getParameter("parentPlanId");
        String useObjectId = request.getParameter("useObjectId");
        TSUserDto currentUser = UserUtil.getCurrentUser();
        try {
            UploadFile uploadFile = new UploadFile(request);
            MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
            uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            String attachmentURL = "";
            List<String> distinctNames = new ArrayList<String>();
            InputsDto currentinputs = new InputsDto();
            for (MultipartFile mf : fileMap.values()) {
                // 获取文件流
                InputStream fos = mf.getInputStream();
                String attachmentName = mf.getOriginalFilename();
                attachmentURL = JackrabbitUtil.handleFileUpload(fos, attachmentName,
                    JackrabbitConstants.PLAN_RESOLVE_FILE_PATH, false);

                // 交付项名称去掉文件名后缀
                String delieverName = attachmentName.substring(0, attachmentName.lastIndexOf("."));
                distinctNames.add(delieverName);
                currentinputs.setUseObjectId(useObjectId);
                currentinputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                currentinputs.setOriginType(PlanConstants.LOCAL_EN);
                currentinputs.setName(delieverName);
                currentinputs.setDocName(attachmentName);
                currentinputs.setDocId(attachmentURL);
                break;
            }

            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            // 找到对应的变更节点，并添加输出：
            for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                if (curFlowTaskVo.getId().equals(useObjectId)) {
                    FlowTaskInputsVo input = new FlowTaskInputsVo();
                    String uuid = PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString();
                    input.setId(uuid);
                    input.setUseObjectId(useObjectId);
                    input.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                    input.setOriginType(PlanConstants.LOCAL_EN);
                    input.setOriginObjectName(PlanConstants.LOCAL);
                    input.setName(currentinputs.getName());
                    input.setDocName(currentinputs.getDocName());
                    input.setDocId(currentinputs.getDocId());
                    input.setCreateBy(currentUser.getId());
                    input.setCreateFullName(currentUser.getRealName());
                    input.setCreateName(currentUser.getUserName());
                    input.setCreateTime(new Date());
                    curFlowTaskVo.getInputList().add(input);
                    break;
                }

            }
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);

            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.uploadSuccess"));
            j.setSuccess(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.common.uploadFailure"));
            j.setSuccess(false);
        }
        finally {
            return j;
        }
    }

    /**
     * 输入新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goChangeAdd")
    public ModelAndView goChangeAdd(HttpServletRequest req) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        String type = req.getParameter("type");
        String preposeIds = req.getParameter("preposeIds");
        String allPreposeIds = req.getParameter("allPreposeIds");

        req.setAttribute("cellId", cellId);
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("type", type);
        req.setAttribute("preposeIds", preposeIds);
        req.setAttribute("allPreposeIds", allPreposeIds);
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/changeDeliverables-add");
        if (type.equals("OUTPUT")) {}
        else {
            FlowTaskInputsVo inputs = new FlowTaskInputsVo();
            FlowTaskVo plan = new FlowTaskVo();
            plan.setCellId(cellId);
            plan.setParentPlanId(parentPlanId);
            // List<Plan> taskList = planService.queryPlanList(plan, 1, 10, false);
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            List<FlowTaskInputsVo> inputList = new ArrayList<FlowTaskInputsVo>();
            if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (f.getCellId().equals(cellId)) {
                        inputList = f.getInputList();
                        plan.setId(f.getId());
                        break;
                    }
                }
            }
            req.setAttribute("parentPlanId", parentPlanId);
            inputs.setUseObjectId(plan.getId());
            inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            req.setAttribute("useObjectId", plan.getId());
            req.setAttribute("useObjectType", PlanConstants.USEOBJECT_TYPE_PLAN);
            req.setAttribute("inputs_", inputs);

            switch (type) {
            // 内部输入
                case "INNERTASK":
                    mav = new ModelAndView("com/glaway/ids/pm/project/task/innerChangeInput-add");
                    break;
                // 外部输入
                case "DELIEVER":
                    mav = new ModelAndView("com/glaway/ids/pm/project/task/outChangeInput-add");
                    break;
                // 本地文档
                case "LOCAL":
                    mav = new ModelAndView("com/glaway/ids/pm/project/task/taskCellChangeInput-doc");
                    break;
                default:
                    break;
            }
        }

        return mav;
    }

    /**
     * 输入资源页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddResource")
    public ModelAndView goAddResource(HttpServletRequest req) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");

        List<PlanDto> childList = new ArrayList<PlanDto>();
        PlanDto parent = new PlanDto();
        parent.setParentPlanId(parentPlanId);
        parent.setCellId(cellId);
        childList = planService.queryPlanList(parent, 1, 10, false);

        if (!CommonUtil.isEmpty(childList)) {
            req.setAttribute(
                "startTime",
                DateUtil.formatDate(childList.get(0).getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
            req.setAttribute("endTime",
                DateUtil.formatDate(childList.get(0).getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));

        }
        req.setAttribute("cellId", cellId);
        req.setAttribute("parentPlanId", parentPlanId);
        return new ModelAndView("com/glaway/ids/pm/project/task/resource-add");
    }

    /**
     * 输入资源页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goChangeAddResource")
    public ModelAndView goChangeAddResource(HttpServletRequest req) {
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        FlowTaskVo currentFlowTask = null;
        if (!CommonUtil.isEmpty(changeFlowTaskList)) {
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (cellId.equals(flowTask.getCellId())) {
                    currentFlowTask = flowTask;
                    break;
                }
            }
        }
        if (currentFlowTask != null) {
            if (currentFlowTask.getPlanStartTime() != null
                && currentFlowTask.getPlanEndTime() != null) {
                req.setAttribute("startTime", DateUtil.formatDate(
                    currentFlowTask.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                req.setAttribute("endTime", DateUtil.formatDate(currentFlowTask.getPlanEndTime(),
                    DateUtil.LONG_DATE_FORMAT));
            }
            else {
                PlanDto parent = planService.getPlanEntity(parentPlanId);
                if (parent != null) {
                    req.setAttribute("startTime",
                        DateUtil.formatDate(parent.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                    req.setAttribute("endTime",
                        DateUtil.formatDate(parent.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));
                }
            }
        }
        else {
            PlanDto parent = planService.getPlanEntity(parentPlanId);
            if (parent != null) {
                req.setAttribute("startTime",
                    DateUtil.formatDate(parent.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                req.setAttribute("endTime",
                    DateUtil.formatDate(parent.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));
            }
        }
        req.setAttribute("cellId", cellId);
        req.setAttribute("parentPlanId", parentPlanId);
        return new ModelAndView("com/glaway/ids/pm/project/task/changeResource-add");
    }

    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "deliverablesList")
    @ResponseBody
    public void deliverablesList(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String preposeIds = request.getParameter("preposeIds");
        String allPreposeIds = request.getParameter("allPreposeIds");
        DeliveryStandardDto condition = new DeliveryStandardDto();
        condition.setStopFlag(ConfigStateConstants.START);
        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);

        if (!"OUTPUT".equals(type)) {
            List<InputsDto> currentinputList = new ArrayList<InputsDto>();
            if (taskList.size() > 0) {
                plan = taskList.get(0);
                InputsDto inputs = new InputsDto();
                inputs.setUseObjectId(plan.getId());
                inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                currentinputList = inputsService.queryInputsDetailList(inputs);
            }

            // 增加内部前置：

            // 1.查询出后台数据库的前置计划：
            String dateBasepreposeIds = "";
            List<PreposePlanDto> preposeList = preposePlanService.getPreposePlansByPlanId(plan);

            for (PreposePlanDto prepose : preposeList) {
                PlanDto preposePlan = planService.getPlanEntity(prepose.getPreposePlanId());
                // if (!plan.getParentPlanId().equals(preposePlan.getParentPlanId())) {
                String id = prepose.getPreposePlanId();
                if (dateBasepreposeIds.equals("")) {
                    dateBasepreposeIds = id;
                }
                else {
                    dateBasepreposeIds = dateBasepreposeIds + "," + id;
                }

                // }

            }

            List<DeliverablesInfoDto> list = new ArrayList<DeliverablesInfoDto>();

            // 外部前置 ：
            if (StringUtils.isNotEmpty(dateBasepreposeIds)) {
                // List<DeliverablesInfo> preposeDeliverables =
                // deliverablesInfoService.getPreposePlanDeliverables(preposeIds);

                List<DeliverablesInfoDto> preposeDeliverables = deliverablesInfoService.getPreposePlanDeliverables(dateBasepreposeIds);

                for (DeliverablesInfoDto deliver : preposeDeliverables) {
                    boolean exist = false;
                    for (InputsDto in : currentinputList) {
                        if (deliver.getId().equals(in.getOriginDeliverablesInfoId())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        list.add(deliver);
                    }
                }
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(list);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + list.size() + "}";

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
        else {

            int rows = Integer.valueOf(request.getParameter("rows"));
            int page = Integer.valueOf(request.getParameter("page"));
            List<DeliveryStandardDto> list = deliveryStandardService.searchUseableDeliveryStandards(condition);

             List<DeliveryStandardDto> deliverablesList = new ArrayList<DeliveryStandardDto>();

             List<DeliverablesInfoDto> outputList = new ArrayList<DeliverablesInfoDto>();
             if (taskList.size() > 0) {
             plan = taskList.get(0);
             DeliverablesInfoDto outputs = new DeliverablesInfoDto();
             outputs.setUseObjectId(plan.getId());
             outputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
             outputList = deliverablesInfoService.queryDeliverableList(outputs, 1, 10, false);
             }
             if (CommonUtil.isEmpty(outputList)) {
             deliverablesList.addAll(list);
             }
             else {
             for (DeliveryStandardDto deliverables : list) {
             boolean isExist = false;
             for (DeliverablesInfoDto output : outputList) {
             if (deliverables.getName().equals(output.getName())) {
             isExist = true;
             break;
             }
             }
             if (!isExist) {
             deliverablesList.add(deliverables);
             }
             }
             }

            int count = deliverablesList.size();
            List<DeliveryStandardDto> resList = new ArrayList<DeliveryStandardDto>();
            if (count > page * rows) {
                resList = deliverablesList.subList((page - 1) * rows, page * rows);
            }
            else {
                resList = deliverablesList.subList((page - 1) * rows, deliverablesList.size());
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
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

    }

    /**
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "outInputList")
    @ResponseBody
    public void outInputList(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        DeliveryStandardDto condition = new DeliveryStandardDto();
        condition.setStopFlag(ConfigStateConstants.START);
        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);

        if (!"OUTPUT".equals(type)) {
            List<InputsDto> currentinputList = new ArrayList<InputsDto>();
            if (taskList.size() > 0) {
                plan = taskList.get(0);
                InputsDto inputs = new InputsDto();
                inputs.setUseObjectId(plan.getId());
                inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                inputs.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                currentinputList = inputsService.queryInputsDetailList(inputs);
            }

            List<DeliveryStandardDto> list = deliveryStandardService.searchUseableDeliveryStandards(condition);

            List<DeliveryStandardDto> inputsList = new ArrayList<DeliveryStandardDto>();

            if (CommonUtil.isEmpty(currentinputList)) {
                inputsList.addAll(list);
            }
            else {
                for (DeliveryStandardDto deliverables : list) {
                    boolean isExist = false;
                    for (InputsDto input : currentinputList) {
                        if (deliverables.getName().equals(input.getName())) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        inputsList.add(deliverables);
                    }
                }
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(inputsList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + inputsList.size() + "}";
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
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            taskFlowResolveService.doBatchDelInputsForWork(ids);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
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
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "changeDeliverablesList")
    @ResponseBody
    public void changeDeliverablesList(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String parentPlanId = request.getParameter("parentPlanId");
        String cellId = request.getParameter("cellId");

        DeliveryStandardDto deliveryStandard = new DeliveryStandardDto();
        deliveryStandard.setStopFlag(ConfigStateConstants.START);

        List<DeliveryStandardDto> list = deliveryStandardService.searchUseableDeliveryStandards(deliveryStandard);

        List<DeliveryStandardDto> deliverablesList = new ArrayList<DeliveryStandardDto>();

        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)request.getSession().getAttribute(
            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);

        if ("INPUT".equals(type)) {
            String preposeIds = request.getParameter("preposeIds");
            String currentId = "";
            List<DeliverablesInfoDto> currentlist = new ArrayList<DeliverablesInfoDto>();
            List<FlowTaskInputsVo> changeInputList = new ArrayList<FlowTaskInputsVo>();
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (flowTask.getCellId().equals(cellId)) {
                    changeInputList = flowTask.getInputList();
                    currentId = flowTask.getId();
                    break;
                }
            }

            for (FlowTaskPreposeVo current : flowTaskPreposeList) {
                if (CommonUtils.isNotEmpty(current.getFlowTaskId())) {
                    if (current.getFlowTaskId().equals(currentId)) {
                        for (FlowTaskVo flowTask : changeFlowTaskList) {
                            if (current.getPreposeId().equals(flowTask.getId())) {
                                for (FlowTaskDeliverablesInfoVo out : flowTask.getOutputList()) {
                                    DeliverablesInfoDto output = new DeliverablesInfoDto();
                                    output.setId(out.getId());
                                    output.setName(out.getName());
                                    output.setUseObjectName(flowTask.getPlanName());
                                    output.setUseObjectId(out.getUseObjectId());
                                    output.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                    boolean isExist = false;
                                    for (FlowTaskInputsVo input : changeInputList) {
                                        // 2.获取该计划的list中的FlowTaskInputs对应的输入，并比较出计划输入的值。
                                        if (output.getId().equals(
                                            input.getOriginDeliverablesInfoId())) {
                                            isExist = true;
                                            break;
                                        }
                                    }
                                    if (!isExist) {
                                        currentlist.add(output);
                                    }
                                }

                            }

                        }
                    }
                }

            }

            if (StringUtils.isNotEmpty(preposeIds)) {
                // 1.获取前置计划的输出：
                List<DeliverablesInfoDto> preposeDeliverables = deliverablesInfoService.getPreposePlanDeliverables(preposeIds);
                for (DeliverablesInfoDto deliver : preposeDeliverables) {

                    boolean isExist = false;
                    for (FlowTaskInputsVo input : changeInputList) {
                        // 2.获取该计划的list中的FlowTaskInputs对应的输入，并比较出计划输入的值。
                        if (deliver.getId().equals(input.getOriginDeliverablesInfoId())) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        currentlist.add(deliver);
                    }

                }
            }
            String json = com.alibaba.fastjson.JSONArray.toJSONString(currentlist);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + currentlist.size() + "}";

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
        else {
            int rows =10;
            int page =1;
            if(!CommonUtil.isEmpty(request.getParameter("rows")) && !CommonUtil.isEmpty(request.getParameter("page"))){
                rows = Integer.valueOf(request.getParameter("rows"));
                page = Integer.valueOf(request.getParameter("page"));
            }


            List<FlowTaskDeliverablesInfoVo> changeOutputList = new ArrayList<FlowTaskDeliverablesInfoVo>();
            for (FlowTaskVo task : changeFlowTaskList) {
                if (cellId.equals(task.getCellId())) {
                    changeOutputList = task.getOutputList();
                    break;
                }
            }
            if (CommonUtil.isEmpty(changeOutputList)) {
                deliverablesList.addAll(list);
            }
            else {
                for (DeliveryStandardDto deliverables : list) {
                    boolean isExist = false;
                    for (FlowTaskDeliverablesInfoVo output : changeOutputList) {
                        if (deliverables.getName().equals(output.getName())) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        deliverablesList.add(deliverables);
                    }
                }
            }

            int count = deliverablesList.size();
            List<DeliveryStandardDto> resList = new ArrayList<DeliveryStandardDto>();
            if (count > page * rows) {
                resList = deliverablesList.subList((page - 1) * rows, page * rows);
            }
            else {
                resList = deliverablesList.subList((page - 1) * rows, deliverablesList.size());
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(resList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count
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

    }

    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddInherit")
    public ModelAndView goAddInherit(DeliverablesInfoDto deliverablesInfo, HttpServletRequest req) {
        String cellIdForDocument = req.getParameter("cellId");
        String parentPlanIdForDocument = req.getParameter("parentPlanId");
        req.getSession().setAttribute("cellIdForDocument", cellIdForDocument);
        req.getSession().setAttribute("parentPlanIdForDocument", parentPlanIdForDocument);
        return new ModelAndView("com/glaway/ids/pm/project/task/inheritDocument-add");
    }

    /**
     * 交付物新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAddInheritForChange")
    public ModelAndView goAddInheritForChange(DeliverablesInfoDto deliverablesInfo,
                                              HttpServletRequest req) {
        String cellIdForDocument = req.getParameter("cellId");
        String parentPlanIdForDocument = req.getParameter("parentPlanId");
        req.getSession().setAttribute("cellIdForDocument", cellIdForDocument);
        req.getSession().setAttribute("parentPlanIdForDocument", parentPlanIdForDocument);
        req.setAttribute("parentPlanId", parentPlanIdForDocument);
        return new ModelAndView("com/glaway/ids/pm/project/task/changeInheritDocument-add");
    }

    /**
     * 新增交付物
     * @param name
     * @param no
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "search3")
    @ResponseBody
    public AjaxJson search3(String name, String no, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String message = "";
        try {
            List<DeliveryStandardDto> searchInfoListTemp = new ArrayList<DeliveryStandardDto>();
            PlanDto task = new PlanDto();
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                for (PlanDto child : childList) {
                    if (cellId.equals(child.getCellId())) {
                        task = child;
                        break;
                    }
                }
            }
            List<DeliverablesInfoDto> deliverablesInfoList = new ArrayList<DeliverablesInfoDto>();
            if (task != null && StringUtils.isNotEmpty(task.getId())) {
                DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(task.getId());
                deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                deliverablesInfoList = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);
            }

            DeliveryStandardDto condition = new DeliveryStandardDto();
            condition.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> deliverables = deliveryStandardService.searchUseableDeliveryStandards(condition);

            List<DeliveryStandardDto> searchRmList = new ArrayList<DeliveryStandardDto>();
            if (CommonUtil.isEmpty(deliverablesInfoList)) {
                searchRmList.addAll(deliverables);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (DeliverablesInfoDto flowTaskDeliverablesInfo : deliverablesInfoList) {
                    if (StringUtils.isNotEmpty(flowTaskDeliverablesInfo.getName())) {
                        resourceIdMap.put(flowTaskDeliverablesInfo.getName(),
                            flowTaskDeliverablesInfo.getName());
                    }
                }
                for (DeliveryStandardDto deliveryStandard : deliverables) {
                    if (StringUtils.isEmpty(resourceIdMap.get(deliveryStandard.getName()))) {
                        searchRmList.add(deliveryStandard);
                    }
                }
            }

            for (DeliveryStandardDto b : searchRmList) {
                if (b.getName().contains(name) && b.getNo().contains(no)) {
                    searchInfoListTemp.add(b);
                }
            }
            log.info(message, name, name);
        }
        catch (Exception e) {
            message = failMessage;
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
     * 新增交付物
     * @param name
     * @param no
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "search4")
    @ResponseBody
    public AjaxJson search4(String name, String no, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String message = "";
        try {
            List<DeliveryStandardDto> searchInfoListTemp = new ArrayList<DeliveryStandardDto>();

            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            List<FlowTaskDeliverablesInfoVo> changeOutputList = new ArrayList<FlowTaskDeliverablesInfoVo>();
            List<Resource> searchRmInfoList = new ArrayList<Resource>();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(cellId)) {
                    changeOutputList = f.getOutputList();
                    break;
                }
            }

            DeliveryStandardDto condition = new DeliveryStandardDto();
            condition.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> deliverables = deliveryStandardService.searchUseableDeliveryStandards(condition);

            List<DeliveryStandardDto> searchRmList = new ArrayList<DeliveryStandardDto>();
            if (CommonUtil.isEmpty(changeOutputList)) {
                searchRmList.addAll(deliverables);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (FlowTaskDeliverablesInfoVo flowTaskDeliverablesInfo : changeOutputList) {
                    if (StringUtils.isNotEmpty(flowTaskDeliverablesInfo.getName())) {
                        resourceIdMap.put(flowTaskDeliverablesInfo.getName(),
                            flowTaskDeliverablesInfo.getName());
                    }
                }
                for (DeliveryStandardDto deliveryStandard : deliverables) {
                    if (StringUtils.isEmpty(resourceIdMap.get(deliveryStandard.getName()))) {
                        searchRmList.add(deliveryStandard);
                    }
                }
            }

            for (DeliveryStandardDto b : searchRmList) {
                if (b.getName().contains(name) && b.getNo().contains(no)) {
                    searchInfoListTemp.add(b);
                }
            }
            log.info(message, name, name);
        }
        catch (Exception e) {
            message = failMessage;
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
    @RequestMapping(params = "datagridSearchlist3")
    @ResponseBody
    public void datagridSearchlist3(HttpServletRequest request, HttpServletResponse response) {
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        try {
            List<DeliveryStandardDto> searchList = new ArrayList<DeliveryStandardDto>();
            List<DeliveryStandardDto> searchInfoListTemp = new ArrayList<DeliveryStandardDto>();
            PlanDto task = new PlanDto();
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                for (PlanDto child : childList) {
                    if (cellId.equals(child.getCellId())) {
                        task = child;
                        break;
                    }
                }
            }
            List<DeliverablesInfoDto> deliverablesInfoList = new ArrayList<DeliverablesInfoDto>();
            if (task != null && StringUtils.isNotEmpty(task.getId())) {
                DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(task.getId());
                deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                deliverablesInfoList = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);
            }

            DeliveryStandardDto condition = new DeliveryStandardDto();
            condition.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> deliverables = deliveryStandardService.searchUseableDeliveryStandards(condition);

            if (CommonUtil.isEmpty(deliverablesInfoList)) {
                searchInfoListTemp.addAll(deliverables);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (DeliverablesInfoDto flowTaskDeliverablesInfo : deliverablesInfoList) {
                    if (StringUtils.isNotEmpty(flowTaskDeliverablesInfo.getName())) {
                        resourceIdMap.put(flowTaskDeliverablesInfo.getName(),
                            flowTaskDeliverablesInfo.getName());
                    }
                }
                for (DeliveryStandardDto deliveryStandard : deliverables) {
                    if (StringUtils.isEmpty(resourceIdMap.get(deliveryStandard.getName()))) {
                        searchInfoListTemp.add(deliveryStandard);
                    }
                }
            }

            for (DeliveryStandardDto b : searchInfoListTemp) {
                if (b.getName().contains(name) && b.getNo().contains(no)) {
                    searchList.add(b);
                }
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(searchList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + searchList.size() + "}";

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
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
    @RequestMapping(params = "datagridSearchlist4")
    @ResponseBody
    public void datagridSearchlist4(HttpServletRequest request, HttpServletResponse response) {
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        try {

            List<DeliveryStandardDto> searchList = new ArrayList<DeliveryStandardDto>();

            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            List<FlowTaskDeliverablesInfoVo> changeOutputList = new ArrayList<FlowTaskDeliverablesInfoVo>();
            List<Resource> searchRmInfoList = new ArrayList<Resource>();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(cellId)) {
                    changeOutputList = f.getOutputList();
                    break;
                }
            }

            DeliveryStandardDto condition = new DeliveryStandardDto();
            condition.setStopFlag(ConfigStateConstants.START);
            List<DeliveryStandardDto> deliverables = deliveryStandardService.searchUseableDeliveryStandards(condition);

            List<DeliveryStandardDto> searchRmList = new ArrayList<DeliveryStandardDto>();
            if (CommonUtil.isEmpty(changeOutputList)) {
                searchRmList.addAll(deliverables);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (FlowTaskDeliverablesInfoVo flowTaskDeliverablesInfo : changeOutputList) {
                    if (StringUtils.isNotEmpty(flowTaskDeliverablesInfo.getName())) {
                        resourceIdMap.put(flowTaskDeliverablesInfo.getName(),
                            flowTaskDeliverablesInfo.getName());
                    }
                }
                for (DeliveryStandardDto deliveryStandard : deliverables) {
                    if (StringUtils.isEmpty(resourceIdMap.get(deliveryStandard.getName()))) {
                        searchRmList.add(deliveryStandard);
                    }
                }
            }

            for (DeliveryStandardDto b : searchRmList) {
                if (b.getName().contains(name) && b.getNo().contains(no)) {
                    searchList.add(b);
                }
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(searchList);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + searchList.size() + "}";

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");

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
        String cellIdForDocument = (String)request.getSession().getAttribute("cellIdForDocument");
        String parentPlanIdForDocument = (String)request.getSession().getAttribute(
            "parentPlanIdForDocument");

        PlanDto parent = new PlanDto();
        parent.setParentPlanId(parentPlanIdForDocument);
        parent.setCellId(cellIdForDocument);
        PlanDto plan = planService.queryPlanList(parent, 1, 10, false).get(0);
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> deliverableslist = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);
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
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridInheritlistForChange")
    @ResponseBody
    public void datagridInheritlistForChange(HttpServletRequest request,
                                             HttpServletResponse response) {
        String parentPlanId = request.getParameter("parentPlanId");
        List<DeliverablesInfoDto> list = new ArrayList<DeliverablesInfoDto>();
        List<DeliverablesInfoDto> deliverablesParentlist = deliverablesInfoService.getDeliverablesByUseObeject(
            PlanConstants.USEOBJECT_TYPE_PLAN, parentPlanId);
        Map<String, String> map = new HashMap<String, String>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        for (FlowTaskVo flowTask : changeFlowTaskList) {
            if (!CommonUtil.isEmpty(flowTask.getOutputList())) {
                for (FlowTaskDeliverablesInfoVo output : flowTask.getOutputList()) {
                    map.put(output.getName(), output.getName());
                }
            }
        }

        for (DeliverablesInfoDto parentOutput : deliverablesParentlist) {
            if (StringUtils.isEmpty(map.get(parentOutput.getName()))) {
                list.add(parentOutput);
            }
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + list.size() + "}";

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
     * 新增交付物-继承父项目输出
     *
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInheritDocument")
    @ResponseBody
    public AjaxJson doAddInheritDocument(String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String cellIdForDocument = (String)request.getSession().getAttribute("cellIdForDocument");
        String parentPlanIdForDocument = (String)request.getSession().getAttribute(
            "parentPlanIdForDocument");
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            List<PlanDto> childList = new ArrayList<PlanDto>();
            PlanDto parent = new PlanDto();
            parent.setParentPlanId(parentPlanIdForDocument);
            parent.setCellId(cellIdForDocument);
            childList = planService.queryPlanList(parent, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                taskFlowResolveService.doAddInheritDocument(names, childList.get(0));
            }
            j.setSuccess(true);
            log.info(message, childList.get(0).getId(), childList.get(0).getId().toString());
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            log.error(failMessage, e, "", parentPlanIdForDocument.toString());
            Object[] params = new Object[] {failMessage, parentPlanIdForDocument.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 新增交付物-继承父项目输出
     *
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInheritForChange")
    @ResponseBody
    public AjaxJson doAddInheritForChange(String names, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String cellId = (String)request.getSession().getAttribute("cellIdForDocument");
        String parentPlanId = request.getParameter("parentPlanId");
        String parentPlanIdForDocument = (String)request.getSession().getAttribute(
            "parentPlanIdForDocument");
        String message = "";
        String failMessage = "";
        String failMessageCode = "";
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            doAddInheritForChange1(names, changeFlowTaskList, cellId);
            j.setSuccess(true);
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            log.error(failMessage, e, "", parentPlanIdForDocument.toString());
            Object[] params = new Object[] {failMessage, parentPlanIdForDocument.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(failMessageCode, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }


    private List<FlowTaskVo> doAddInheritForChange1(String names, List<FlowTaskVo> changeFlowTaskList,
                                      String cellId) {
        List<DeliveryStandardDto> deliveryStandards =   deliveryStandardService.searchDeliveryStandards(new DeliveryStandardDto());
        Map<String, String> deliveryStandardMap = new HashMap<String, String>();
        for (DeliveryStandardDto d : deliveryStandards) {
            deliveryStandardMap.put(d.getName(), d.getId());
        }
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getCellId().equals(cellId)) {
                for (String name : names.split(",")) {
                    FlowTaskDeliverablesInfoVo output = new FlowTaskDeliverablesInfoVo();
                    String uuid = PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString();
                    output.setId(uuid);
                    output.setName(name);
                    if (!CommonUtil.isEmpty(deliveryStandardMap)
                        && !CommonUtil.isEmpty(deliveryStandardMap.get(name))) {
                        output.setDeliverId(deliveryStandardMap.get(name));
                    }
                    output.setUseObjectId(f.getId());
                    output.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                    f.getOutputList().add(output);
                }
                break;
            }
        }

        return changeFlowTaskList;
    }


    /**
     * 获取新增资源列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceInfoList")
    @ResponseBody
    public void resourceInfoList(ResourceLinkInfoDto resourceLinkInfo, HttpServletRequest request,
                                 HttpServletResponse response) {
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        List<ResourceDto> resourceListTemp = resourceService.searchUsables(new ResourceDto());
        List<ResourceDto> resourceListTemp2 = new ArrayList<ResourceDto>();
        PlanDto task = new PlanDto();
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
        if (!CommonUtil.isEmpty(childList)) {
            for (PlanDto child : childList) {
                if (cellId.equals(child.getCellId())) {
                    task = child;
                    break;
                }
            }
        }
        List<ResourceLinkInfoDto> resourceList = new ArrayList<ResourceLinkInfoDto>();
        if (task != null && StringUtils.isNotEmpty(task.getId())) {
            ResourceLinkInfoDto info = new ResourceLinkInfoDto();
            info.setUseObjectId(task.getId());
            resourceList = resourceLinkInfoService.queryResourceList(info, 1, 10, false);
        }

        if (resourceListTemp != null) {
            if (CommonUtil.isEmpty(resourceList)) {
                resourceListTemp2.addAll(resourceListTemp);
            }
            else {
                Map<String, String> idMap = new HashMap<String, String>();
                for (ResourceLinkInfoDto resource : resourceList) {
                    if (StringUtils.isNotEmpty(resource.getResourceId())) {
                        idMap.put(resource.getResourceId(), resource.getResourceId());
                    }
                }
                for (ResourceDto resource : resourceListTemp) {
                    if (StringUtils.isEmpty(idMap.get(resource.getId()))) {
                        resourceListTemp2.add(resource);
                    }
                }
            }
        }
        if (resourceListTemp2 != null && resourceListTemp2.size() > 0) {
            String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceListTemp2);
            String datagridStr = "{\"rows\":" + json + ",\"total\":" + resourceListTemp2.size()
                                 + "}";

            TagUtil.ajaxResponse(response, datagridStr);
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
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        String path = request.getParameter("path");
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        List<ResourceDto> searchInfoList = new ArrayList<ResourceDto>();
        List<ResourceDto> searchInfoListTemp = new ArrayList<ResourceDto>();

        PlanDto task = new PlanDto();
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
        if (!CommonUtil.isEmpty(childList)) {
            for (PlanDto child : childList) {
                if (cellId.equals(child.getCellId())) {
                    task = child;
                    break;
                }
            }
        }
        List<ResourceLinkInfoDto> resourceList = new ArrayList<ResourceLinkInfoDto>();
        if (task != null && StringUtils.isNotEmpty(task.getId())) {
            ResourceLinkInfoDto info = new ResourceLinkInfoDto();
            info.setUseObjectId(task.getId());
            resourceList = resourceLinkInfoService.queryResourceList(info, 1, 10, false);
        }

        List<ResourceDto> resources = resourceService.searchUsables(new ResourceDto());

        if (CommonUtil.isEmpty(resourceList)) {
            searchInfoListTemp.addAll(resources);
        }
        else {
            Map<String, String> resourceIdMap = new HashMap<String, String>();
            for (ResourceLinkInfoDto flowTaskResource : resourceList) {
                if (StringUtils.isNotEmpty(flowTaskResource.getResourceId())) {
                    resourceIdMap.put(flowTaskResource.getResourceId(),
                        flowTaskResource.getResourceId());
                }
            }
            for (ResourceDto resource : resources) {
                if (StringUtils.isEmpty(resourceIdMap.get(resource.getId()))) {
                    searchInfoListTemp.add(resource);
                }
            }
        }

        for (ResourceDto r : searchInfoListTemp) {
            Boolean a = false;
            if (StringUtils.isNotEmpty(path)) {
                for (String pt : path.split(",")) {
                    if (r.getPath().contains(pt)) {
                        a = true;
                    }
                }
            }
            else {
                a = true;
            }
            if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                searchInfoList.add(r);
            }
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(searchInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + searchInfoList.size() + "}";

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
    @RequestMapping(params = "datagridSearchlist2")
    @ResponseBody
    public void datagridSearchlist2(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        String path = request.getParameter("path");
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        List<FlowTaskResourceLinkInfoVo> changeResourceList = new ArrayList<FlowTaskResourceLinkInfoVo>();
        List<ResourceDto> searchInfoList = new ArrayList<ResourceDto>();
        List<ResourceDto> searchInfoListTemp = new ArrayList<ResourceDto>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getCellId().equals(cellId)) {
                changeResourceList = f.getResourceLinkList();
                break;
            }
        }
        List<ResourceDto> resources = resourceService.searchUsables(new ResourceDto());

        if (CommonUtil.isEmpty(changeResourceList)) {
            searchInfoListTemp.addAll(resources);
        }
        else {
            Map<String, String> resourceIdMap = new HashMap<String, String>();
            for (FlowTaskResourceLinkInfoVo flowTaskResource : changeResourceList) {
                if (StringUtils.isNotEmpty(flowTaskResource.getResourceId())) {
                    resourceIdMap.put(flowTaskResource.getResourceId(),
                        flowTaskResource.getResourceId());
                }
            }
            for (ResourceDto resource : resources) {
                if (StringUtils.isEmpty(resourceIdMap.get(resource.getId()))) {
                    searchInfoListTemp.add(resource);
                }
            }
        }

        for (ResourceDto r : searchInfoListTemp) {
            Boolean a = false;
            if (StringUtils.isNotEmpty(path)) {
                for (String pt : path.split(",")) {
                    if (r.getPath().contains(pt)) {
                        a = true;
                    }
                }
            }
            else {
                a = true;
            }
            if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                searchInfoList.add(r);
            }
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(searchInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + searchInfoList.size() + "}";

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
     * @param name
     * @param no
     * @param path
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "search")
    @ResponseBody
    public AjaxJson search(String name, String no, String path, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String message = "";
        try {
            List<ResourceDto> searchInfoListTemp = new ArrayList<ResourceDto>();

            PlanDto task = new PlanDto();
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                for (PlanDto child : childList) {
                    if (cellId.equals(child.getCellId())) {
                        task = child;
                        break;
                    }
                }
            }
            List<ResourceLinkInfoDto> resourceList = new ArrayList<ResourceLinkInfoDto>();
            if (task != null && StringUtils.isNotEmpty(task.getId())) {
                ResourceLinkInfoDto info = new ResourceLinkInfoDto();
                info.setUseObjectId(task.getId());
                resourceList = resourceLinkInfoService.queryResourceList(info, 1, 10, false);
            }

            List<ResourceDto> resources = resourceService.searchUsables(new ResourceDto());
            List<ResourceDto> searchRmInfoList = new ArrayList<ResourceDto>();
            if (CommonUtil.isEmpty(resourceList)) {
                searchRmInfoList.addAll(resources);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (ResourceLinkInfoDto flowTaskResource : resourceList) {
                    if (StringUtils.isNotEmpty(flowTaskResource.getResourceId())) {
                        resourceIdMap.put(flowTaskResource.getResourceId(),
                            flowTaskResource.getResourceId());
                    }
                }
                for (ResourceDto resource : resources) {
                    if (StringUtils.isEmpty(resourceIdMap.get(resource.getId()))) {
                        searchRmInfoList.add(resource);
                    }
                }
            }
            for (ResourceDto r : searchRmInfoList) {
                Boolean a = false;
                if (StringUtils.isNotEmpty(path)) {
                    for (String pt : path.split(",")) {
                        if (r.getPath().contains(pt)) {
                            a = true;
                        }
                    }
                }
                else {
                    a = true;
                }
                if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                    searchInfoListTemp.add(r);
                }
            }

            log.info(message, name, name);
        }
        catch (Exception e) {
            message = failMessage;
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
     * 新增交付物
     * @param name
     * @param no
     * @param path
     * @param request
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "search2")
    @ResponseBody
    public AjaxJson search2(String name, String no, String path, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String message = "";
        try {
            List<ResourceDto> searchInfoListTemp = new ArrayList<ResourceDto>();
            List<FlowTaskResourceLinkInfoVo> changeResourceList = new ArrayList<FlowTaskResourceLinkInfoVo>();
            List<ResourceDto> searchRmInfoList = new ArrayList<ResourceDto>();
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(cellId)) {
                    changeResourceList = f.getResourceLinkList();
                    break;
                }
            }
            List<ResourceDto> resources = resourceService.searchUsables(new ResourceDto());

            if (CommonUtil.isEmpty(changeResourceList)) {
                searchRmInfoList.addAll(resources);
            }
            else {
                Map<String, String> resourceIdMap = new HashMap<String, String>();
                for (FlowTaskResourceLinkInfoVo flowTaskResource : changeResourceList) {
                    if (StringUtils.isNotEmpty(flowTaskResource.getResourceId())) {
                        resourceIdMap.put(flowTaskResource.getResourceId(),
                            flowTaskResource.getResourceId());
                    }
                }
                for (ResourceDto resource : resources) {
                    if (StringUtils.isEmpty(resourceIdMap.get(resource.getId()))) {
                        searchRmInfoList.add(resource);
                    }
                }
            }
            for (ResourceDto r : searchRmInfoList) {
                Boolean a = false;
                if (StringUtils.isNotEmpty(path)) {
                    for (String pt : path.split(",")) {
                        if (r.getPath().contains(pt)) {
                            a = true;
                        }
                    }
                }
                else {
                    a = true;
                }
                if (r.getName().contains(name) && r.getNo().contains(no) && a) {
                    searchInfoListTemp.add(r);
                }
            }
            log.info(message, name, name);
        }
        catch (Exception e) {
            message = failMessage;
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
     * 获取新增资源列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "changeResourceInfoList")
    @ResponseBody
    public void changeResourceInfoList(HttpServletRequest request, HttpServletResponse response) {
        List<ResourceDto> resourceListTemp = resourceService.searchUsables(new ResourceDto());
        List<ResourceDto> resourceListTemp2 = new ArrayList<ResourceDto>();

        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        List<FlowTaskResourceLinkInfoVo> changeResourceList = new ArrayList<FlowTaskResourceLinkInfoVo>();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getCellId().equals(cellId)) {
                changeResourceList = f.getResourceLinkList();
                break;
            }
        }

        if (!CommonUtil.isEmpty(resourceListTemp)) {
            if (CommonUtil.isEmpty(changeResourceList)) {
                resourceListTemp2.addAll(resourceListTemp);
            }
            else {
                for (ResourceDto resource : resourceListTemp) {
                    boolean isExist = false;
                    for (FlowTaskResourceLinkInfoVo flowTaskResource : changeResourceList) {
                        if (StringUtils.isNotEmpty(flowTaskResource.getResourceId())
                            && resource.getId().equals(flowTaskResource.getResourceId())) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        resourceListTemp2.add(resource);
                    }
                }
            }
        }

        String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceListTemp2);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + resourceListTemp2.size() + "}";
        TagUtil.ajaxResponse(response, datagridStr);

    }

    /**
     * 新增交付物
     *
     * @param cellId
     * @param parentPlanId
     * @param type
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(String cellId, String parentPlanId, String type, String names,
                          HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String  message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        try {
            List<PlanDto> childList = new ArrayList<PlanDto>();
            PlanDto parent = new PlanDto();
            parent.setParentPlanId(parentPlanId);
            parent.setCellId(cellId);
            childList = planService.queryPlanList(parent, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                taskFlowResolveService.doAdd(type, names, UserUtil.getCurrentUser().getId(),childList);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {
                I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure"),
                I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure")};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }

    }

    /**
     * 新增交付物
     * @param inputs
     * @param ids
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInputs")
    @ResponseBody
    public AjaxJson doAddInputs(InputsDto inputs, String ids, HttpServletRequest request) {
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        String allPreposeIds = request.getParameter("allPreposeIds").trim();
        String preposeIds = request.getParameter("preposeIds").trim();
        String parentPlanId = request.getParameter("parentPlanId").trim();
        String cellId = request.getParameter("cellId").trim();

        // //获取内部前置ids：
        String innerpreposeIds = "";
        String[] outpreposeid = preposeIds.split(",");
        String message = "";

        for (String preposeid : allPreposeIds.split(",")) {
            boolean isExit = false;
            for (int i = 0; i < outpreposeid.length; i++ ) {
                String current = outpreposeid[i];
                if (current.equals(preposeid)) {
                    isExit = true;
                    break;
                }
            }
            if (!isExit) {
                if (innerpreposeIds.equals("")) {
                    innerpreposeIds = preposeid;
                }
                else {
                    innerpreposeIds = innerpreposeIds + "," + preposeid;
                }
            }

        }

        InputsDto currentinputs = new InputsDto();
        currentinputs.setUseObjectId(useObjectId);
        currentinputs.setUseObjectType(useObjectType);

        AjaxJson j = new AjaxJson();
        try {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.task.input")
                + I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            taskFlowResolveService.doAddInputs(ids, currentinputs);
            // 同步研发流程任务输入
            // 通过前置计划的id，找到其cellId :
            Map<String, String> idAndCellIdMap = new HashMap<String, String>();
            if (!CommonUtil.isEmpty(allPreposeIds)) {
                for (String preposeid : allPreposeIds.split(",")) {
                    PlanDto preposePlan = planService.getPlanEntity(preposeid);
                    idAndCellIdMap.put(preposeid, preposePlan.getCellId());
                }
            }

            String cellAndNameInputStr = "";
            if (!CommonUtil.isEmpty(ids)) {
                List<DeliverablesInfoDto> selectedList = deliverablesInfoService.getSelectedPreposePlanDeliverables(ids);
                for (DeliverablesInfoDto deliverablesInfo : selectedList) {
                    // DeliverablesInfo deliverablesInfo = planService.getEntity(Plan.class,
                    // deliverablesInfoId);
                    if (!CommonUtil.isEmpty(idAndCellIdMap.get(deliverablesInfo.getUseObjectId()))) {
                        if (cellAndNameInputStr.equals("")) {
                            cellAndNameInputStr = idAndCellIdMap.get(deliverablesInfo.getUseObjectId())
                                                  + ";" + deliverablesInfo.getName();
                        }
                        else {
                            cellAndNameInputStr = cellAndNameInputStr
                                                  + ","
                                                  + idAndCellIdMap.get(deliverablesInfo.getUseObjectId())
                                                  + ";" + deliverablesInfo.getName();
                        }
                    }
                }
            }
            if (!CommonUtil.isEmpty(cellAndNameInputStr)) {
                // 数据同步：任务的输入
                String userId = UserUtil.getCurrentUser().getId();
                rdFlowTaskFlowResolveService.saveRdfInputByPlan(cellAndNameInputStr, parentPlanId, cellId, userId);
//                RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                rdfConfigSupport.saveRdfInputByPlan(cellAndNameInputStr, parentPlanId, cellId,
//                    userId);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {
                I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure"),
                I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure")};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }

    }

    /**
     * 新增交付物
     * @param inputs
     * @param ids
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "changdoAddInputs")
    @ResponseBody
    public AjaxJson changdoAddInputs(InputsDto inputs, String ids, HttpServletRequest request) {
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        /*
         * String allPreposeIds = request.getParameter("allPreposeIds").trim(); String preposeIds =
         * request.getParameter("preposeIds").trim();
         */
        String cellId = request.getParameter("cellId").trim();
        String originObjectIds = request.getParameter("originObjectIds").trim();
        String originObjectNames = request.getParameter("originObjectNames").trim();
        String names = request.getParameter("names").trim();
        String parentPlanId = request.getParameter("parentPlanId");
        List<FlowTaskInputsVo> changeInputList = new ArrayList<FlowTaskInputsVo>();
        AjaxJson j = new AjaxJson();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        if (!CommonUtil.isEmpty(changeFlowTaskList)) {
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(cellId)) {
                    changeInputList = f.getInputList();
                    break;
                }
            }
        }
        String[] idsArr = ids.split(",");
        String[] originObjectIdsArr = originObjectIds.split(",");
        String[] originObjectNamesArr = originObjectNames.split(",");
        String[] namesArr = names.split(",");
        List<DeliveryStandardDto> deliveryStandards = deliveryStandardService.searchDeliveryStandards(new DeliveryStandardDto());
//        List<DeliveryStandardDto> deliveryStandards = deliveryStandardService.findHql("from DeliveryStandard");
        Map<String, String> deliveryStandardMap = new HashMap<String, String>();
        for (DeliveryStandardDto deliveryStandard : deliveryStandards) {
            deliveryStandardMap.put(deliveryStandard.getName(), deliveryStandard.getId());
        }
        if (idsArr.length > 0) {
            for (int i = 0; i < idsArr.length; i++ ) {
                FlowTaskInputsVo temp = new FlowTaskInputsVo();
                String uuid = PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString();
                temp.setId(uuid);
                temp.setName(namesArr[i]);
                if (!CommonUtil.isEmpty(namesArr[i]) && !CommonUtil.isEmpty(deliveryStandardMap)
                    && !CommonUtil.isEmpty(deliveryStandardMap.get(namesArr[i]))) {
                    temp.setDeliverId(deliveryStandardMap.get(namesArr[i]));
                }
                temp.setUseObjectId(useObjectId);
                temp.setUseObjectType(useObjectType);
                temp.setOriginObjectId(originObjectIdsArr[i]);
                temp.setOriginObjectName(originObjectNamesArr[i]);
                temp.setOriginDeliverablesInfoId(idsArr[i]);
                changeInputList.add(temp);
            }
        }
        return j;
    }

    /**
     * 更新input
     * @param inputs
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "updateInput")
    @ResponseBody
    public AjaxJson updateInput(InputsDto inputs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
//            InputsDto i = inputsService.getInputEntity(inputs.getId());
//            i.setDocId(inputs.getDocId());
//            i.setDocName(inputs.getDocName());
            inputsService.updateInputsForDocInfoById(inputs.getId(),inputs.getDocId(),inputs.getDocName());
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.editfailure");
            j.setSuccess(false);
            Object[] params = new Object[] {message, message};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 更新input
     * @param inputs
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "updateChangeInput")
    @ResponseBody
    public AjaxJson updateChangeInput(InputsDto inputs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (!CommonUtil.isEmpty(f.getInputList())) {
                        for (FlowTaskInputsVo input : f.getInputList()) {
                            if (inputs.getId().equals(input.getId())) {
                                input.setDocId(inputs.getDocId());
                                input.setDocName(inputs.getDocName());
                                break;
                            }
                        }
                    }
                }
                j.setSuccess(true);
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {message, message};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 新建文件
     * @param document
     * @param request
     * @param session
     * @param repFileType
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddForChangeTask")
    @ResponseBody
    public AjaxJson doAddForChangeTask(ProjLibDocumentVo document, HttpServletRequest request,
                                       HttpSession session, RepFileTypeDto repFileType) {
        AjaxJson j = new AjaxJson();
        String sad = document.getProjectId();
        String inputId = request.getParameter("inputId");
        String parentPlanId = request.getParameter("parentPlanId");
        Project project = projectService.getProjectEntity(document.getProjectId());
        String projectNo = project.getProjectNumber();
        SerialNumberManager.getDataMap().put("projectNo", projectNo);
        String id = repFileType.getId();
        String type = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.document");
        /**
         * 文件类型由RepFileType
         */
        // document.setFileTypeId("4028ef2d504608ba0150462418bf0001");
        // 需求变更typeID更改为CODE
        /*String fileTypeId = repFileTypeQueryService.getFileTypeIdByCode(RepFileTypeConstants.REP_FILE_TYPE_PRO);*/
        String fileTypeId= "";
        List<RepFileTypeDto> fileTypeList = repService.getRepFileTypesByFileTypeCode(ResourceUtil.getApplicationInformation().getAppKey(), RepFileTypeConstants.REP_FILE_TYPE_PRO);
        if(!CommonUtil.isEmpty(fileTypeList)){
            fileTypeId = fileTypeList.get(0).getId();
        }

        document.setFileTypeId(fileTypeId);
        String docNameNew = document.getDocName();
        if (document.getType().equals(0)) {
            type = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.folder");
        }

        String message = type + I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.createsuccess");
        try {
            short securityLevel = Short.parseShort(StringUtils.defaultIfBlank(
                document.getSecurityLevel(), "1"));
            short creatorSecurityLevel = UserUtil.getCurrentUserSecurityLevel();
            if (securityLevel > creatorSecurityLevel) {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.unCreatHighSecurityLevelDocument");
                j.setSuccess(false);
                return j;
            }
            if (projLibService.validateReptDocNum(document.getDocNumber())) {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.documentCodeExist");
                j.setSuccess(false);
                return j;
            }
            String fileId = projLibService.createFile(document,UserUtil.getCurrentUser().getId());
            j.setObj(fileId);

            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (!CommonUtil.isEmpty(f.getInputList())) {
                        for (FlowTaskInputsVo input : f.getInputList()) {
                            if (inputId.equals(input.getId())) {
                                input.setDocId(fileId);
                                input.setDocName(docNameNew);
                                break;
                            }
                        }
                    }
                }
            }

            // 计划提交项操作记录
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.deliverablesConnectedSuccess");
            String planId = (String)session.getAttribute("planId");
            PlanLogDto planLog = new PlanLogDto();

            try {
                planLog.setPlanId(planId);
                planLog.setLogInfo("挂接交付物:" + docNameNew);
                PlanDto plan = new PlanDto();
                plan = planService.getPlanEntity(planId);
                if (plan != null && StringUtils.isNotEmpty(plan.getLauncher())) {
                    TSUserDto user = userService.getUserByUserId(plan.getLauncher());
                    planLog.setCreateBy(user.getId());
                    planLog.setCreateName(user.getUserName());
                    planLog.setCreateFullName(user.getRealName());
                    planLog.setCreateTime(new Date());

                }
                planLogService.savePlanLog(planLog);
            }
            catch (Exception e) {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.savePlanSubmitDeliverablesLogFailure");
                log.error(message, e, null, message);
                Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
                throw new GWException(GWConstants.ERROR_2001, params, e);
            }

        }
        catch (Exception e) {
            message = type + I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.createfailure");
            j.setSuccess(false);
            log.error(message, e, document.getId(), document.toString());
            Object[] params = new Object[] {message, document.toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 新增交付物
     *
     * @param cellId
     * @param parentPlanId
     * @param type
     * @param names
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doChangeAdd")
    @ResponseBody
    public AjaxJson doChangeAdd(String cellId, String parentPlanId, String type, String names,
                                HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "";
        TSUserDto currentUser = UserUtil.getCurrentUser();
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                if (!CommonUtil.isEmpty(names)) {
                    List<DeliveryStandardDto> deliveryStandards = deliveryStandardService.searchDeliveryStandards(new DeliveryStandardDto());
//                    List<DeliveryStandardDto> deliveryStandards = deliveryStandardService.findHql("from DeliveryStandard");
                    Map<String, String> deliveryStandardMap = new HashMap<String, String>();
                    for (DeliveryStandardDto d : deliveryStandards) {
                        deliveryStandardMap.put(d.getName(), d.getId());
                    }
                    if ("INPUT".equals(type)) {
                        for (FlowTaskVo f : changeFlowTaskList) {
                            if (f.getCellId().equals(cellId)) {
                                for (String name : names.split(",")) {
                                    FlowTaskInputsVo input = new FlowTaskInputsVo();
                                    String uuid = PlanConstants.PLAN_CREATE_UUID
                                                  + UUID.randomUUID().toString();
                                    input.setId(uuid);
                                    input.setName(name);
                                    input.setUseObjectId(f.getId());
                                    input.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                    input.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                    input.setCreateBy(currentUser.getId());
                                    input.setCreateFullName(currentUser.getRealName());
                                    input.setCreateName(currentUser.getUserName());
                                    input.setCreateTime(new Date());
                                    if (!CommonUtil.isEmpty(deliveryStandardMap)
                                        && !CommonUtil.isEmpty(deliveryStandardMap.get(name))) {
                                        input.setDeliverId(deliveryStandardMap.get(name));
                                    }
                                    f.getInputList().add(input);
                                }
                                break;
                            }
                        }
                        j.setSuccess(true);
                        message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
                    }
                    else {
                        for (FlowTaskVo f : changeFlowTaskList) {
                            if (f.getCellId().equals(cellId)) {
                                for (String name : names.split(",")) {
                                    FlowTaskDeliverablesInfoVo output = new FlowTaskDeliverablesInfoVo();
                                    String uuid = PlanConstants.PLAN_CREATE_UUID
                                                  + UUID.randomUUID().toString();
                                    output.setId(uuid);
                                    output.setName(name);
                                    output.setUseObjectId(f.getId());
                                    output.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                    if (!CommonUtil.isEmpty(deliveryStandardMap)
                                        && !CommonUtil.isEmpty(deliveryStandardMap.get(name))) {
                                        output.setDeliverId(deliveryStandardMap.get(name));
                                    }
                                    f.getOutputList().add(output);
                                }
                                break;
                            }
                        }
                        j.setSuccess(true);
                        message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
                    }
                }
                request.getSession().setAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {message, message};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 新增资源
     *
     * @param cellId
     * @param parentPlanId
     * @param ids
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddResource")
    @ResponseBody
    public AjaxJson doAddResource(String cellId, String parentPlanId, String ids,
                                  HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            List<PlanDto> childList = new ArrayList<PlanDto>();
            PlanDto parent = new PlanDto();
            parent.setParentPlanId(parentPlanId);
            parent.setCellId(cellId);
            childList = planService.queryPlanList(parent, 1, 10, false);
            if (!CommonUtil.isEmpty(childList)) {
                for (String id : ids.split(",")) {
                ResourceLinkInfoDto resource = new ResourceLinkInfoDto();
                resource.setResourceId(id);
                resource.setUseObjectId(childList.get(0).getId());
                resource.setStartTime(childList.get(0).getPlanStartTime());
                resource.setEndTime(childList.get(0).getPlanEndTime());
                resource.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                resource.setUseRate("100");
                resourceLinkInfoService.doAddResourceLinkInfo(resource);
                }
//                taskFlowResolveService.doAddResource(ids, childList);
                j.setSuccess(true);
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {message, message};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 新增资源
     *
     * @param cellId
     * @param parentPlanId
     * @param ids
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doChangeAddResource")
    @ResponseBody
    public AjaxJson doChangeAddResource(String cellId, String parentPlanId, String ids,
                                        HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                for (FlowTaskVo f : changeFlowTaskList) {
                    if (f.getCellId().equals(cellId)) {
                        for (String id : ids.split(",")) {
                            ResourceDto resource = resourceService.getEntity(id);
                            if (resource != null) {
                                FlowTaskResourceLinkInfoVo resourceLinkInfo = new FlowTaskResourceLinkInfoVo();
                                String uuid = PlanConstants.PLAN_CREATE_UUID
                                              + UUID.randomUUID().toString();

                                resourceLinkInfo.setId(uuid);
                                ResourceDto r = new ResourceDto();
                                r.setId(id);
                                List<ResourceDto> resourceListTemp = resourceService.searchUsables(r);
                                if (!CommonUtil.isEmpty(resourceListTemp)) {
                                    resourceLinkInfo.setResourceType(resourceListTemp.get(0).getPath());
                                }
                                resourceLinkInfo.setResourceId(id);
                                resourceLinkInfo.setResourceInfo(resource);
                                resourceLinkInfo.setResourceName(resource.getName());
                                resourceLinkInfo.setUseObjectId(f.getId());
                                resourceLinkInfo.setStartTime(f.getPlanStartTime());
                                resourceLinkInfo.setEndTime(f.getPlanEndTime());
                                resourceLinkInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                resourceLinkInfo.setUseRate("100");
                                f.getResourceLinkList().add(resourceLinkInfo);
                            }
                        }
                        break;
                    }
                }
                j.setSuccess(true);
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
                j.setSuccess(false);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            j.setSuccess(false);
            Object[] params = new Object[] {message, message};// 异常原因：{0}；详细信息：{1}
            throw new GWException(message, params, e);
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_INSERT,
            // Globals.Log_Leavel_INFO);
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
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            // taskFlowResolveService.doDelResource(ids, resourceList);
            resourceService.doBatchDel(ids);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            log.error(message, e, ids, "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + ids};// 异常原因：{0}；异常描述：{1}
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
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelChangeResource")
    @ResponseBody
    public AjaxJson doDelChangeResource(FlowTaskResourceLinkInfoVo resourceLinkInfo,
                                        HttpServletRequest request) {
        String parentPlanId = request.getParameter("parentPlanId");
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletesuccess");
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            doDelChangeResource(resourceLinkInfo, changeFlowTaskList);
            j.setSuccess(true);
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.deletefailure");
            j.setSuccess(false);
            log.error(message, e, resourceLinkInfo.getId(), "");
            Object[] params = new Object[] {message,
                DeliverablesInfoDto.class.getClass() + " oids:" + resourceLinkInfo.getId()};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

   private void doDelChangeResource(FlowTaskResourceLinkInfoVo resourceLinkInfo,
                                    List<FlowTaskVo> changeFlowTaskList) {
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getId().equals(resourceLinkInfo.getUseObjectId())) {
                for (FlowTaskResourceLinkInfoVo resource : f.getResourceLinkList()) {
                    if (resource.getId().equals(resourceLinkInfo.getId())) {
                        f.getResourceLinkList().remove(resource);
                        break;
                    }
                }
                break;
            }
        }

    }

    /**
     * 资源编辑页面跳转
     *
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(params = "goModify")
    public ModelAndView goModify(ResourceLinkInfoDto tempPlanResourceLinkInfo, HttpServletRequest req)
        throws ParseException, UnsupportedEncodingException {
        String id = req.getParameter("id");
        tempPlanResourceLinkInfo = resourceLinkInfoService.getResourceLinkInfoEntity(id);
        ResourceDto resourceDto = resourceService.getEntity(tempPlanResourceLinkInfo.getResourceId());
        if(!CommonUtil.isEmpty(resourceDto)) {
            tempPlanResourceLinkInfo.setResourceName(resourceDto.getName());
        }
        PlanDto plan = planService.getPlanEntity(tempPlanResourceLinkInfo.getUseObjectId());
        req.setAttribute("planStartTime", plan.getPlanStartTime());
        req.setAttribute("planEndTime", plan.getPlanEndTime());
        req.setAttribute("tempPlanResourceLinkInfo_", tempPlanResourceLinkInfo);
        return new ModelAndView("com/glaway/ids/pm/project/task/resource-modify");
    }

    /**
     * 资源编辑页面跳转
     *
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(params = "goModifyForChange")
    public ModelAndView goModifyForChange(FlowTaskResourceLinkInfoVo resourceLinkInfo,
                                          HttpServletRequest req)
        throws ParseException, UnsupportedEncodingException {
        String parentPlanId = req.getParameter("parentPlanId");
        FlowTaskResourceLinkInfoVo tempPlanResourceLinkInfo = new FlowTaskResourceLinkInfoVo();
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for (FlowTaskVo f : changeFlowTaskList) {
            if (f.getId().equals(resourceLinkInfo.getUseObjectId())) {
                for (FlowTaskResourceLinkInfoVo resource : f.getResourceLinkList()) {
                    if (resource.getId().equals(resourceLinkInfo.getId())) {
                        resource.setResourceName(resource.getResourceInfo().getName());
                        req.setAttribute("planStartTime",
                            DateUtil.dateToString(f.getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                        req.setAttribute("planEndTime",
                            DateUtil.dateToString(f.getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));
                        tempPlanResourceLinkInfo = resource;
                        break;
                    }
                }
                break;
            }
        }
        req.setAttribute("parentPlanId", parentPlanId);
        req.setAttribute("tempPlanResourceLinkInfo_", tempPlanResourceLinkInfo);
        return new ModelAndView("com/glaway/ids/pm/project/task/changeResource-modify");
    }

    /**
     * 更新资源
     *
     * @param tempPlanResourceLinkInfo
     * @param request
     * @return
     */
    @RequestMapping(params = "doUpdateResource")
    @ResponseBody
    public AjaxJson doUpdateResource(ResourceLinkInfoDto tempPlanResourceLinkInfo,
                                     HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        try {
            ResourceLinkInfoDto resourceLinkInfo = resourceLinkInfoService.getResourceLinkInfoEntity(tempPlanResourceLinkInfo.getId());
            resourceLinkInfo.setUseRate(tempPlanResourceLinkInfo.getUseRate());
            resourceLinkInfo.setStartTime(tempPlanResourceLinkInfo.getStartTime());
            resourceLinkInfo.setEndTime(tempPlanResourceLinkInfo.getEndTime());
            ResourceDto r = new ResourceDto();
            r.setId(resourceLinkInfo.getResourceId());
            List<ResourceDto> resourceListTemp = resourceService.searchUsables(r);
            if (!CommonUtil.isEmpty(resourceListTemp)) {
                resourceLinkInfo.setResourceType(resourceListTemp.get(0).getPath());
            }
            ResourceDto resourceDto = resourceService.getEntity(resourceLinkInfo.getResourceId());
            if(!CommonUtil.isEmpty(resourceDto)) {
                resourceLinkInfo.setResourceName(resourceDto.getName());
            }
            List<ResourceLinkInfoDto> res = new ArrayList<>();
            res.add(resourceLinkInfo);
            resourceLinkInfoService.updateResourceLinkInfoTimeByDto(res);
            j.setSuccess(true);
            // taskFlowResolveService.doUpdateResource(tempPlanResourceLinkInfo, resourceList);
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
            e.printStackTrace();
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 更新资源
     *
     * @param tempPlanResourceLinkInfo
     * @param request
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateResourceForChange")
    @ResponseBody
    public AjaxJson doUpdateResourceForChange(FlowTaskResourceLinkInfoVo tempPlanResourceLinkInfo,
                                              HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getId().equals(tempPlanResourceLinkInfo.getUseObjectId())) {
                    for (FlowTaskResourceLinkInfoVo resourceLinkInfo : f.getResourceLinkList()) {
                        if (tempPlanResourceLinkInfo.getId().equals(resourceLinkInfo.getId())) {
                            resourceLinkInfo.setUseRate(tempPlanResourceLinkInfo.getUseRate());
                            resourceLinkInfo.setStartTime(tempPlanResourceLinkInfo.getStartTime());
                            resourceLinkInfo.setEndTime(tempPlanResourceLinkInfo.getEndTime());
                            ResourceDto r = new ResourceDto();
                            r.setId(resourceLinkInfo.getResourceId());
                            List<ResourceDto> resourceListTemp = resourceService.searchUsables(r);
                            if (!CommonUtil.isEmpty(resourceListTemp)) {
                                resourceLinkInfo.setResourceType(resourceListTemp.get(0).getPath());
                            }
                            resourceLinkInfo.setResourceName(resourceLinkInfo.getResourceInfo().getName());
                            break;
                        }
                    }
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savefailure");
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 跳转到批量编辑的页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goBaseInfoBatchMod")
    private ModelAndView goBaseInfoBatchMod(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskCellBatchModify");
        String parentPlanId = req.getParameter("parentPlanId");
        mav.addObject("parentPlanId", parentPlanId);

        List<TSUserDto> userList = userService.getAllUsers();

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);

        List<BusinessConfig>  planLevelList1 = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig>  planLevelList1 = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});
        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        mav.addObject("planLevelList", jonStr);

        JSONArray jList = new JSONArray();
        JSONObject jObj = null;
        for (TSUserDto t : userList) {
            jObj = new JSONObject();
            jObj.put("id", t.getId());
            jObj.put("realName", t.getRealName() + "-" + t.getUserName());
            jList.add(jObj);
        }
        String jonStr2 = jList.toString().replaceAll("\"", "'");

        mav.addObject("userList", jonStr2);

        List<TSUserDto> userListNew = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto plan = planService.getPlanEntity(parentPlanId);
            if (plan != null) {
                Project project = projectService.getProjectEntity(plan.getProjectId());
                if (project != null) {
                    List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                    for (TSUserDto r : users) {
                        if (r != null) {
                            TSUserDto o = userService.getUserByUserId(r.getId());
                            if (o != null) {
                                userListNew.add(o);
                            }
                        }
                    }
                }
            }
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userListNew) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj);
        }

        // 判断计划流程变更中变更的计划名称是否启用活动名称库的参数处理
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();

//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
            || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
            || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            req.setAttribute("isStandards", "ok");
        }
        else {
            req.setAttribute("isStandards", "nook");
        }
        String aaa = JSON.toJSONString(jsonList);
        req.setAttribute("userList1", aaa);
        String b = JSON.toJSONString(planLevelList1);
        req.setAttribute("planLevelList1", b);
        return mav;
    }

    /**
     * 跳转到批量编辑的页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goChangeBaseInfoBatchMod")
    private ModelAndView goChangeBaseInfoBatchMod(HttpServletRequest req,
                                                  HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(
            "com/glaway/ids/pm/project/task/changeFlowTaskCellBatchModify");
        String parentPlanId = req.getParameter("parentPlanId");
        mav.addObject("parentPlanId", parentPlanId);
        List<TSUserDto> userList = userService.getAllUsers();

        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList1 = businessConfigService.searchUseableBusinessConfigs(planLevel);
       // List<BusinessConfig> planLevelList1 = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});
        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        mav.addObject("planLevelList", jonStr);
        String b = JSON.toJSONString(planLevelList1);
        req.setAttribute("planLevelList1", b);

        JSONArray jList = new JSONArray();
        JSONObject jObj = null;
        for (TSUserDto t : userList) {
            jObj = new JSONObject();
            jObj.put("id", t.getId());
            jObj.put("realName", t.getRealName() + "-" + t.getUserName());
            jList.add(jObj);
        }
        String jonStr2 = jList.toString().replaceAll("\"", "'");
        mav.addObject("userList", jonStr2);
        List<TSUserDto> userListNew = new ArrayList<TSUserDto>();
        if (StringUtils.isNotEmpty(parentPlanId)) {
            PlanDto plan = planService.getPlanEntity(parentPlanId);
            if (plan != null) {
                Project project = projectService.getProjectEntity(plan.getProjectId());
                if (project != null) {
                    List<TSUserDto> users = projRoleService.getUserInProject(project.getId());
                    for (TSUserDto r : users) {
                        if (r != null) {
                            TSUserDto o = userService.getUserByUserId(r.getId());
                            if (o != null) {
                                userListNew.add(o);
                            }
                        }
                    }
                }
            }
        }
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        for (TSUserDto t : userListNew) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("realName", t.getRealName() + "-" + t.getUserName());
            jsonList.add(obj);
        }

        // 判断计划流程变更中变更的计划名称是否启用活动名称库的参数处理
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();
//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
            || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
            || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            req.setAttribute("isStandards", "ok");
        }
        else {
            req.setAttribute("isStandards", "nook");
        }

        String aaa = JSON.toJSONString(jsonList);
        req.setAttribute("userList1", aaa);

        return mav;
    }

    /**
     * 跳转到匹配外部输入来源的页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goOutInputMatch")
    private ModelAndView goOutInputMatch(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/goBaveOutInputMatch");
        String parentPlanId = req.getParameter("parentPlanId");
        mav.addObject("parentPlanId", parentPlanId);
        String uuId = UUID.randomUUID().toString();
        mav.addObject("uuId", uuId);
        PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
        if (!CommonUtil.isEmpty(parentPlan)) {
            mav.addObject("projectId", parentPlan.getProjectId());
        }
        return mav;
    }

    /**
     * 跳转到变更匹配外部输入来源的页面
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "goChangeOutInputMatch")
    private ModelAndView goChangeOutInputMatch(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView(
            "com/glaway/ids/pm/project/task/goChangeBaveOutInputMatch");
        String parentPlanId = req.getParameter("parentPlanId");
        mav.addObject("parentPlanId", parentPlanId);
        String uuId = UUID.randomUUID().toString();
        mav.addObject("uuId", uuId);
        PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
        if (!CommonUtil.isEmpty(parentPlan)) {
            mav.addObject("projectId", parentPlan.getProjectId());
        }
        return mav;
    }

    /**
     * 查询外部输入匹配的活动输入信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "outInputMatchList")
    @ResponseBody
    private void outInputMatchList(HttpServletRequest req, HttpServletResponse response) {
        String parentPlanId = req.getParameter("parentPlanId");
        String uuId = req.getParameter("uuId");
        String type = req.getParameter("type");
        String json = "";
        if (!CommonUtil.isEmpty(type)) {
            List<FlowTaskInputsMatchVo> changeOutInputList = new ArrayList<FlowTaskInputsMatchVo>();
            if (!CommonUtil.isEmpty(req.getSession().getAttribute(
                PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId))) {
                changeOutInputList = (List<FlowTaskInputsMatchVo>)req.getSession().getAttribute(
                    PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId);
            }
            else {
                List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
                for (FlowTaskVo flowtask : changeFlowTaskList) {
                    List<FlowTaskInputsVo> curOutInputList1 = flowtask.getInputList();
                    if (!CommonUtil.isEmpty(curOutInputList1)) {
                        for (FlowTaskInputsVo curInputVo : curOutInputList1) {
                            if (!CommonUtil.isEmpty(curInputVo)
                                && !CommonUtil.isEmpty(curInputVo.getOriginTypeExt())
                                && PlanConstants.DELIEVER_EN.equals(curInputVo.getOriginTypeExt())) {
                                FlowTaskInputsMatchVo curFlowTaskInputsMatchVo = new FlowTaskInputsMatchVo();
                                curFlowTaskInputsMatchVo.setId(curInputVo.getId());
                                curFlowTaskInputsMatchVo.setName(curInputVo.getName());
                                curFlowTaskInputsMatchVo.setDocument(curInputVo.getDocument());
                                curFlowTaskInputsMatchVo.setOriginType(curInputVo.getOriginType());
                                curFlowTaskInputsMatchVo.setOriginTypeExt(curInputVo.getOriginTypeExt());
                                curFlowTaskInputsMatchVo.setFileId(curInputVo.getFileId());
                                curFlowTaskInputsMatchVo.setInputId(curInputVo.getInputId());
                                curFlowTaskInputsMatchVo.setOrigin(curInputVo.getOrigin());
                                curFlowTaskInputsMatchVo.setRequired(curInputVo.getRequired());
                                curFlowTaskInputsMatchVo.setUseObjectId(curInputVo.getUseObjectId());
                                curFlowTaskInputsMatchVo.setUseObjectType(curInputVo.getUseObjectType());
                                curFlowTaskInputsMatchVo.setDocId(curInputVo.getDocId());
                                curFlowTaskInputsMatchVo.setDocName(curInputVo.getDocName());
                                curFlowTaskInputsMatchVo.setOriginDeliverablesInfoId(curInputVo.getOriginDeliverablesInfoId());
                                curFlowTaskInputsMatchVo.setOriginDeliverablesInfoName(curInputVo.getOriginDeliverablesInfoName());
                                curFlowTaskInputsMatchVo.setOriginObjectId(curInputVo.getOriginObjectId());
                                curFlowTaskInputsMatchVo.setOriginObjectName(curInputVo.getOriginObjectName());
                                curFlowTaskInputsMatchVo.setCreateBy(curInputVo.getCreateBy());
                                curFlowTaskInputsMatchVo.setCreateFullName(curInputVo.getCreateFullName());
                                curFlowTaskInputsMatchVo.setCreateName(curInputVo.getCreateName());
                                curFlowTaskInputsMatchVo.setCreateTime(curInputVo.getCreateTime());
                                curFlowTaskInputsMatchVo.setDeliverId(curInputVo.getDeliverId());
                                curFlowTaskInputsMatchVo.setSecurityLevel(curInputVo.getSecurityLevel());
                                curFlowTaskInputsMatchVo.setDownload(curInputVo.getDownload());
                                curFlowTaskInputsMatchVo.setDetail(curInputVo.getDetail());
                                curFlowTaskInputsMatchVo.setHavePower(curInputVo.getHavePower());
                                curFlowTaskInputsMatchVo.setDocIdShow(curInputVo.getDocIdShow());
                                curFlowTaskInputsMatchVo.setUseObjectName(flowtask.getPlanName());
                                curFlowTaskInputsMatchVo.setOriginObjectNameShow(curFlowTaskInputsMatchVo.getOriginObjectName());
                                changeOutInputList.add(curFlowTaskInputsMatchVo);
                            }
                        }
                    }
                }
                req.getSession().setAttribute(PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId,
                    changeOutInputList);

            }
            json = com.alibaba.fastjson.JSONArray.toJSONString(changeOutInputList);
        }
        else {
            List<InputsDto> outInputsList = inputsService.queryOutInputsDetailList(parentPlanId);
            if (!CommonUtil.isEmpty(req.getSession().getAttribute(
                PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId))) {
                outInputsList = (List<InputsDto>)req.getSession().getAttribute(
                    PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId);
            }
            else {
                PlanDto parentPlan = planService.getPlanEntity(parentPlanId);
                PlanDto planContation = new PlanDto();
                planContation.setParentPlanId(parentPlanId);
                Map<String, String> planIdAndNameMap = new HashMap<String, String>();
                FeignJson planIdAndplanNameJson = planService.queryPlanIdAndNameMap(parentPlanId);
                if (planIdAndplanNameJson.isSuccess()) {
                    Map<String, Object> map = new HashMap<>();
                    map = planIdAndplanNameJson.getAttributes();
                    planIdAndNameMap = (Map<String, String>)map.get("planIdAndplanNameMap");
                }

                String libId = "";
                Map<String, String> fileNameMap = new HashMap<String, String>();

                Map<String, String> filePathMap = new HashMap<String, String>();

                Map<String, String> fileIdMap = new HashMap<String, String>();

                if (!CommonUtil.isEmpty(parentPlan)) {
                    String projectId = parentPlan.getProjectId();
                    FeignJson fj = projRoleService.getLibIdByProjectId(projectId);
                    libId = String.valueOf(fj.getObj());
                }

                if (!CommonUtil.isEmpty(libId)) {
                    fileNameMap = inputsService.getRepFileNameAndBizIdMap(libId);

                    filePathMap = inputsService.getRepFilePathAndBizIdMap(libId);

                    fileIdMap = inputsService.getRepFileIdAndBizIdMap(libId);
                }

                for (InputsDto i : outInputsList) {
                    i.setOriginObjectName(planIdAndNameMap.get(i.getUseObjectId()));
                    if (StringUtils.isNotEmpty(i.getDocId())) {
                        RepFileDto r =  repFileService.getRepFileByRepFileId(appKey, i.getDocId());
                        if (!CommonUtil.isEmpty(r)) {
                            String havePower = planService.getOutPower(i.getDocId(),
                                i.getUseObjectId(), UserUtil.getCurrentUser().getId());
                            if ("downloadDetail".equals(havePower)) {
                                i.setDownload(true);
                                i.setDetail(true);
                                i.setHavePower(true);
                            }
                            else if ("detail".equals(havePower)) {
                                i.setDownload(false);
                                i.setDetail(true);
                                i.setHavePower(true);
                            }
                            else {
                                i.setDownload(false);
                                i.setDetail(false);
                                i.setHavePower(false);
                            }
                            i.setSecurityLeve(r.getSecurityLevel());
                        }
                    }

                    // 外部输入挂接项目库的数据获取：
                    if (!CommonUtil.isEmpty(i.getOriginType())
                        && PlanConstants.PROJECTLIBDOC.equals(i.getOriginType())) {
                        if (!CommonUtil.isEmpty(fileNameMap.get(i.getDocId()))) {
                            i.setDocName(fileNameMap.get(i.getDocId()));
                        }
                        if (!CommonUtil.isEmpty(filePathMap.get(i.getDocId()))) {
                            i.setOriginObjectNameShow(filePathMap.get(i.getDocId()));
                        }
                        if (!CommonUtil.isEmpty(fileIdMap.get(i.getDocId()))) {
                            i.setDocIdShow(fileIdMap.get(i.getDocId()));
                        }
                    }
                    else if ((!CommonUtil.isEmpty(i.getOriginType()) && i.getOriginType().equals(
                        "PLAN"))
                             && (!CommonUtil.isEmpty(i.getOriginTypeExt()) && i.getOriginTypeExt().equals(
                                 PlanConstants.DELIEVER_EN))) {
                        PlanDto curPlan = planService.getPlanEntity(i.getOriginObjectId());
                        // 外部输入挂接计划的数据获取：
                        List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
                        if (!CommonUtil.isEmpty(curPlan)) {
                            projDocRelationList = inputsService.getDocRelationList(curPlan,
                                UserUtil.getInstance().getUser().getId());
                        }
                        ProjDocVo projDoc = new ProjDocVo();
                        if (!CommonUtil.isEmpty(projDocRelationList)) {
                            for (ProjDocVo vo : projDocRelationList) {
                                if (vo.getDeliverableId().equals(i.getOriginDeliverablesInfoId())) {
                                    projDoc = vo;
                                    break;
                                }
                            }
                        }
                        i.setOriginObjectNameShow(curPlan.getPlanNumber() + "."
                                                  + curPlan.getPlanName());
                        i.setDocId(projDoc.getDocId());
                        i.setDocName(projDoc.getDocName());
                        i.setExt1(String.valueOf(projDoc.isDownload()));
                        i.setExt2(String.valueOf(projDoc.isHavePower()));
                        i.setExt3(String.valueOf(projDoc.isDetail()));
                    }

                }
                req.getSession().setAttribute(PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId,
                    outInputsList);
            }
            json = com.alibaba.fastjson.JSONArray.toJSONString(outInputsList);
        }
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 保存匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "doBatchMatchSave")
    @ResponseBody
    public AjaxJson doBatchMatchSave(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            List<InputsDto> outInputsList = (List<InputsDto>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId);
            List<InputsDto> outInputsListOld = inputsService.queryOutInputsDetailList(parentPlanId);
            if(outInputsListOld.size()>0) {
                for (InputsDto curInputs : outInputsListOld) {
                    for (InputsDto cur : outInputsList) {
                        if (curInputs.getId().equals(cur.getId())) {
                            curInputs.setDocId(cur.getDocId());
                            curInputs.setDocName(cur.getDocName());
                            curInputs.setOriginType(cur.getOriginType());
                            curInputs.setOriginObjectId(cur.getOriginObjectId());
                            curInputs.setOriginDeliverablesInfoId(cur.getOriginDeliverablesInfoId());
                            curInputs.setOriginTypeExt(cur.getOriginTypeExt());
                            curInputs.setExt1(cur.getExt1());
                            curInputs.setExt2(cur.getExt2());
                            curInputs.setExt3(cur.getExt3());
                            break;
                        }
                    }
                }

                for (InputsDto curInputsOld : outInputsListOld) {
                    taskFlowResolveService.doAddInputsNew("", curInputsOld,UserUtil.getCurrentUser().getId());
                }
            }

            j.setMsg("保存成功");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存失败");
        }
        return j;
    }

    /**
     * (变更)保存匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "doBatchChangeMatchSave")
    @ResponseBody
    public AjaxJson doBatchChangeMatchSave(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            List<FlowTaskInputsMatchVo> outInputsList = (List<FlowTaskInputsMatchVo>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId);
            List<FlowTaskVo> flowTaskVoOld = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo curFlowTaskVo : flowTaskVoOld) {
                List<FlowTaskInputsVo> curInputs = curFlowTaskVo.getInputList();
                if (curInputs.size() > 0) {
                    for (FlowTaskInputsVo curOld : curInputs) {
                        for (FlowTaskInputsMatchVo cur : outInputsList) {
                            if (curOld.getId().equals(cur.getId())) {
                                curOld.setDocId(cur.getDocId());
                                curOld.setDocName(cur.getDocName());
                                curOld.setOriginType(cur.getOriginType());
                                curOld.setOriginObjectId(cur.getOriginObjectId());
                                curOld.setOriginDeliverablesInfoId(cur.getOriginDeliverablesInfoId());
                                curOld.setOriginTypeExt(cur.getOriginTypeExt());
                                curOld.setOriginObjectName(cur.getOriginObjectNameShow());
                                curOld.setExt1(cur.getExt1());
                                curOld.setExt2(cur.getExt2());
                                curOld.setExt3(cur.getExt3());
                                break;
                            }
                        }
                    }
                }
            }
            req.getSession().setAttribute(PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId,
                flowTaskVoOld);

            j.setMsg("保存成功");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存失败");
        }
        return j;
    }

    /**
     * 自动匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "autoInfoMatch")
    @ResponseBody
    public AjaxJson autoInfoMatch(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String projectId = req.getParameter("projectId");

        try {
            Map<String, String> fileNameMap = new HashMap<String, String>();

            Map<String, String> filePathMap = new HashMap<String, String>();

            Map<String, String> fileIdMap = new HashMap<String, String>();

            FeignJson fj = projRoleService.getLibIdByProjectId(projectId);
            String libId = String.valueOf(fj.getObj());

            if (!CommonUtil.isEmpty(libId)) {
                fileNameMap = inputsService.getRepFileNameAndBizIdMap(libId);

                filePathMap = inputsService.getRepFilePathAndBizIdMap(libId);

                fileIdMap = inputsService.getRepFileIdAndBizIdMap(libId);
            }

            PlanDto p = new PlanDto();
            p.setProjectId(projectId);
            List<PlanDto> planList = planService.queryPlanInputsList(p);

            List<InputsDto> outInputsList = (List<InputsDto>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId);
            for (InputsDto curInputs : outInputsList) {
                if (CommonUtil.isEmpty(curInputs.getOriginType())
                    && PlanConstants.DELIEVER_EN.equals(curInputs.getOriginTypeExt())) {
                    String inputsName = curInputs.getName();
                    List<PlanDto> tempList = new ArrayList<PlanDto>();

                    if (!CommonUtil.isEmpty(planList)) {
                        for (PlanDto temp : planList) {
                            List<ProjDocVo> projDocRelationList = inputsService.getDocRelationListMatch(
                                temp, UserUtil.getInstance().getUser().getId(), inputsName);
                            if (!CommonUtil.isEmpty(projDocRelationList)
                                && !temp.getId().equals(curInputs.getUseObjectId())) {
                                tempList.add(temp);
                                break;
                            }

                        }
                    }

                    // 如果为空，即没有计划的交付项与之匹配：
                    if (!CommonUtil.isEmpty(tempList)) {
                        InputsDto input = new InputsDto();
                        PlanDto plan = tempList.get(0);
                        List<ProjDocVo> projDocRelationList = getDocRelationListMatch(plan,
                            inputsName);
                        ProjDocVo projDoc = new ProjDocVo();
                        if (!CommonUtil.isEmpty(projDocRelationList)) {
                            for (ProjDocVo vo : projDocRelationList) {
                                if (vo.getDeliverableName().equals(inputsName)) {
                                    projDoc = vo;
                                    break;
                                }
                            }
                        }

                        if (!CommonUtil.isEmpty(projDoc)) {
                            input.setDocId(projDoc.getDocId());
                            input.setOriginObjectId(plan.getId());
                            input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                        }

                        if (StringUtils.isNotEmpty(input.getDocId())) {
                            RepFileDto r = repFileService.getRepFileByRepFileId(appKey, input.getDocId());
                            if (!CommonUtil.isEmpty(r)) {
                                String havePower = planService.getOutPower(input.getDocId(),
                                    curInputs.getUseObjectId(), UserUtil.getCurrentUser().getId());
                                if ("downloadDetail".equals(havePower)) {
                                    input.setDownload(true);
                                    input.setDetail(true);
                                    input.setHavePower(true);
                                }
                                else if ("detail".equals(havePower)) {
                                    input.setDownload(false);
                                    input.setDetail(true);
                                    input.setHavePower(true);
                                }
                                else {
                                    input.setDownload(false);
                                    input.setDetail(false);
                                    input.setHavePower(false);
                                }
                                input.setSecurityLeve(r.getSecurityLevel());
                            }
                        }

                        for (InputsDto curInputs2 : outInputsList) {
                            if (curInputs2.getId().equals(curInputs.getId())) {
                                curInputs2.setDownload(input.getDownload());
                                curInputs2.setDetail(input.getDetail());
                                curInputs2.setOriginDeliverablesInfoId(input.getOriginDeliverablesInfoId());
                                curInputs2.setOriginObjectId(input.getOriginObjectId());
                                curInputs2.setHavePower(input.getHavePower());
                                curInputs2.setSecurityLeve(input.getSecurityLeve());
                                curInputs2.setOriginObjectNameShow(plan.getPlanNumber() + "."
                                                                   + plan.getPlanName());
                                curInputs2.setDocId(projDoc.getDocId());
                                curInputs2.setDocName(projDoc.getDocName());
                                if (!CommonUtil.isEmpty(input.getDownload())) {
                                    curInputs2.setExt1(input.getDownload().toString());
                                    curInputs2.setExt2(input.getHavePower().toString());
                                    curInputs2.setExt3(input.getDetail().toString());
                                }
                                curInputs2.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                curInputs2.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                curInputs2.setMatchFlag("true");
                                break;
                            }
                        }
                    }
                    else {
                        String curDocId = "";
                        if (!CommonUtil.isEmpty(fileNameMap)) {
                            for (String curkey : fileNameMap.keySet()) {
                                if (fileNameMap.get(curkey).equals(curInputs.getName())) {
                                    curDocId = curkey;
                                    break;
                                }
                            }
                        }

                        for (InputsDto i : outInputsList) {
                            if (i.getId().equals(curInputs.getId())) {
                                // 外部输入挂接项目库的数据获取：
                                if (!CommonUtil.isEmpty(curDocId)) {
                                    i.setDocId(curDocId);
                                    i.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                    i.setOriginType(PlanConstants.PROJECTLIBDOC);
                                    if (!CommonUtil.isEmpty(fileNameMap.get(i.getDocId()))) {
                                        i.setDocName(fileNameMap.get(i.getDocId()));
                                    }
                                    if (!CommonUtil.isEmpty(filePathMap.get(i.getDocId()))) {
                                        i.setOriginObjectNameShow(filePathMap.get(i.getDocId()));
                                    }
                                    if (!CommonUtil.isEmpty(fileIdMap.get(i.getDocId()))) {
                                        i.setDocIdShow(fileIdMap.get(i.getDocId()));
                                    }
                                }
                                i.setMatchFlag("true");
                                break;
                            }
                        }

                    }
                }
            }
            req.getSession().setAttribute(PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId,
                outInputsList);
            j.setMsg("自动匹配完成");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("自动匹配失败");
        }
        return j;
    }

    /**
     * 变更自动匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "autoInfoChangeMatch")
    @ResponseBody
    public AjaxJson autoInfoChangeMatch(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String projectId = req.getParameter("projectId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            Map<String, String> fileNameMap = new HashMap<String, String>();

            Map<String, String> filePathMap = new HashMap<String, String>();

            Map<String, String> fileIdMap = new HashMap<String, String>();

            FeignJson fj= projRoleService.getLibIdByProjectId(projectId);
            String libId = String.valueOf(fj.getObj());

            if (!CommonUtil.isEmpty(libId)) {
                fileNameMap = inputsService.getRepFileNameAndBizIdMap(libId);

                filePathMap = inputsService.getRepFilePathAndBizIdMap(libId);

                fileIdMap = inputsService.getRepFileIdAndBizIdMap(libId);
            }

            Map<String, String> outputIdAndIdMap = new HashMap<String, String>();
            Map<String, String> idAndPlanId = new HashMap<String, String>();
            Map<String, String> planIdAndIdMap = new HashMap<String, String>();
            List<FlowTaskVo> flowTaskVoOld = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo curFlowTaskVoOld : flowTaskVoOld) {
                if (!CommonUtil.isEmpty(curFlowTaskVoOld.getOutputList())) {
                    for (FlowTaskDeliverablesInfoVo curFlowTaskDeliverablesInfoVo : curFlowTaskVoOld.getOutputList()) {
                        if (!CommonUtil.isEmpty(curFlowTaskDeliverablesInfoVo.getOutputId())) {
                            outputIdAndIdMap.put(curFlowTaskDeliverablesInfoVo.getOutputId(),
                                curFlowTaskDeliverablesInfoVo.getId());
                        }
                    }
                }
                if (!CommonUtil.isEmpty(curFlowTaskVoOld.getPlanId())) {
                    idAndPlanId.put(curFlowTaskVoOld.getId(), curFlowTaskVoOld.getPlanId());
                    planIdAndIdMap.put(curFlowTaskVoOld.getPlanId(), curFlowTaskVoOld.getId());
                }
            }

            PlanDto p = new PlanDto();
            p.setProjectId(projectId);
            List<PlanDto> planList = planService.queryPlanInputsList(p);
            List<FlowTaskInputsMatchVo> outInputsList = (List<FlowTaskInputsMatchVo>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId);
            for (FlowTaskInputsMatchVo curInputs : outInputsList) {
                if (CommonUtil.isEmpty(curInputs.getOriginType())
                    && PlanConstants.DELIEVER_EN.equals(curInputs.getOriginTypeExt())) {
                    String inputsName = curInputs.getName();
                    List<PlanDto> tempList = new ArrayList<PlanDto>();

                    if (!CommonUtil.isEmpty(planList)) {
                        for (PlanDto temp : planList) {
                            List<ProjDocVo> projDocRelationList = inputsService.getDocRelationListMatch(
                                temp, UserUtil.getInstance().getUser().getId(), inputsName);
                            if (!CommonUtil.isEmpty(projDocRelationList)
                                && !temp.getId().equals(
                                    idAndPlanId.get(curInputs.getUseObjectId()))) {
                                tempList.add(temp);
                                break;
                            }

                        }
                    }

                    // 如果为空，即没有计划的交付项与之匹配：
                    if (!CommonUtil.isEmpty(tempList)) {
                        InputsDto input = new InputsDto();
                        PlanDto plan = tempList.get(0);
                        List<ProjDocVo> projDocRelationList = getDocRelationListMatch(plan,
                            inputsName);
                        ProjDocVo projDoc = new ProjDocVo();
                        if (!CommonUtil.isEmpty(projDocRelationList)) {
                            for (ProjDocVo vo : projDocRelationList) {
                                if (vo.getDeliverableName().equals(inputsName)) {
                                    projDoc = vo;
                                    break;
                                }
                            }
                        }

                        if (!CommonUtil.isEmpty(projDoc)) {
                            input.setDocId(projDoc.getDocId());
                            if (CommonUtil.isEmpty(planIdAndIdMap.get(plan.getId()))) {
                                input.setOriginObjectId(plan.getId());
                            }
                            else {
                                input.setOriginObjectId(planIdAndIdMap.get(plan.getId()));
                            }
                            if (CommonUtil.isEmpty(outputIdAndIdMap.get(projDoc.getDeliverableId()))) {
                                input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                            }
                            else {
                                input.setOriginDeliverablesInfoId(outputIdAndIdMap.get(projDoc.getDeliverableId()));
                            }
                        }

                        if (StringUtils.isNotEmpty(input.getDocId())) {
                            RepFileDto r = repFileService.getRepFileByRepFileId(appKey, input.getDocId());
                            if (!CommonUtil.isEmpty(r)) {
                                String havePower = planService.getOutPower(input.getDocId(),
                                    curInputs.getUseObjectId(), UserUtil.getCurrentUser().getId());
                                if ("downloadDetail".equals(havePower)) {
                                    input.setDownload(true);
                                    input.setDetail(true);
                                    input.setHavePower(true);
                                }
                                else if ("detail".equals(havePower)) {
                                    input.setDownload(false);
                                    input.setDetail(true);
                                    input.setHavePower(true);
                                }
                                else {
                                    input.setDownload(false);
                                    input.setDetail(false);
                                    input.setHavePower(false);
                                }
                                input.setSecurityLeve(r.getSecurityLevel());
                            }
                        }

                        for (FlowTaskInputsMatchVo curInputs2 : outInputsList) {
                            if (curInputs2.getId().equals(curInputs.getId())) {
                                curInputs2.setDownload(input.getDownload());
                                curInputs2.setDetail(input.getDetail());
                                curInputs2.setOriginDeliverablesInfoId(input.getOriginDeliverablesInfoId());
                                curInputs2.setOriginObjectId(input.getOriginObjectId());
                                curInputs2.setHavePower(input.getHavePower());
                                curInputs2.setSecurityLeve(input.getSecurityLeve());
                                curInputs2.setOriginObjectNameShow(plan.getPlanNumber() + "."
                                                                   + plan.getPlanName());
                                curInputs2.setDocId(projDoc.getDocId());
                                curInputs2.setDocName(projDoc.getDocName());
                                if (!CommonUtil.isEmpty(input.getDownload())) {
                                    curInputs2.setExt1(input.getDownload().toString());
                                    curInputs2.setExt2(input.getHavePower().toString());
                                    curInputs2.setExt3(input.getDetail().toString());
                                }
                                curInputs2.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                curInputs2.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                curInputs2.setMatchFlag("true");
                                break;
                            }
                        }
                    }
                    else {
                        String curDocId = "";
                        if (!CommonUtil.isEmpty(fileNameMap)) {
                            for (String curkey : fileNameMap.keySet()) {
                                if (fileNameMap.get(curkey).equals(curInputs.getName())) {
                                    curDocId = curkey;
                                    break;
                                }
                            }
                        }

                        for (FlowTaskInputsMatchVo i : outInputsList) {
                            if (i.getId().equals(curInputs.getId())) {
                                i.setDocId(curDocId);
                                i.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                i.setOriginType(PlanConstants.PROJECTLIBDOC);
                                // 外部输入挂接项目库的数据获取：
                                if (!CommonUtil.isEmpty(curDocId)) {
                                    if (!CommonUtil.isEmpty(fileNameMap.get(i.getDocId()))) {
                                        i.setDocName(fileNameMap.get(i.getDocId()));
                                    }
                                    if (!CommonUtil.isEmpty(filePathMap.get(i.getDocId()))) {
                                        i.setOriginObjectNameShow(filePathMap.get(i.getDocId()));
                                    }
                                    if (!CommonUtil.isEmpty(fileIdMap.get(i.getDocId()))) {
                                        i.setDocIdShow(fileIdMap.get(i.getDocId()));
                                    }
                                }
                                i.setMatchFlag("true");
                                break;
                            }
                        }

                    }
                }
            }
            req.getSession().setAttribute(PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId,
                outInputsList);
            j.setMsg("自动匹配完成");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("自动匹配失败");
        }
        return j;
    }

    /**
     * 删除匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "deleteDocumentMatch")
    @ResponseBody
    public AjaxJson deleteDocumentMatch(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String ids = req.getParameter("ids");
        try {
            List<InputsDto> outInputsList = (List<InputsDto>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId);
            for (InputsDto curInputs : outInputsList) {
                if (ids.contains(curInputs.getId())) {
                    // 清除掉匹配的文档及其来源字段：
                    curInputs.setDocId("");
                    curInputs.setDocName("");
                    curInputs.setOriginObjectId("");
                    curInputs.setOriginDeliverablesInfoId("");
                    curInputs.setDocIdShow("");
                    curInputs.setOriginType("");
                    curInputs.setOriginObjectNameShow("");
                    curInputs.setMatchFlag("");
                    curInputs.setExt1("");
                    curInputs.setExt2("");
                    curInputs.setExt3("");
                }
            }
            req.getSession().setAttribute(PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId,
                outInputsList);
            j.setMsg("清除成功");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("清除失败");
        }
        return j;
    }

    /**
     * 删除匹配的文档和来源
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "deleteChangeDocumentMatch")
    @ResponseBody
    public AjaxJson deleteChangeDocumentMatch(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String uuId = req.getParameter("uuId");
        String ids = req.getParameter("ids");
        try {
            List<FlowTaskInputsMatchVo> outChangeInputsList = (List<FlowTaskInputsMatchVo>)req.getSession().getAttribute(
                PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId);
            for (FlowTaskInputsMatchVo curInputs : outChangeInputsList) {
                if (ids.contains(curInputs.getId())) {
                    // 清除掉匹配的文档及其来源字段：
                    curInputs.setDocId("");
                    curInputs.setDocName("");
                    curInputs.setOriginObjectId("");
                    curInputs.setOriginDeliverablesInfoId("");
                    curInputs.setDocIdShow("");
                    curInputs.setOriginType("");
                    curInputs.setOriginObjectNameShow("");
                    curInputs.setMatchFlag("");
                    curInputs.setExt1("");
                    curInputs.setExt2("");
                    curInputs.setExt3("");
                }
            }
            req.getSession().setAttribute(PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId,
                outChangeInputsList);
            j.setMsg("清除成功");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("清除失败");
        }
        return j;
    }

    /**
     * 匹配单行选择确认更新的文档和来源
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "updateMatchProjLibAndPlanLink")
    @ResponseBody
    public AjaxJson updateMatchProjLibAndPlanLink(HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String useObjectId = request.getParameter("useObjectId");
        String originType = request.getParameter("originType");
        String rowId = request.getParameter("rowId");
        String tempId = request.getParameter("tempId");
        String uuId = request.getParameter("uuId");
        try {
            List<InputsDto> outInputsList = (List<InputsDto>)request.getSession().getAttribute(
                PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId);
            String curId = "";
            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                curId = rowId;

            }
            else {
                curId = tempId;
            }

            InputsDto curInputsSelect = inputsService.getInputEntity(curId);
            if (!CommonUtil.isEmpty(curInputsSelect)) {
                useObjectId = curInputsSelect.getUseObjectId();
            }

            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                String fileId = request.getParameter("fileId");
                String folderId = request.getParameter("folderId");
                String projectId = request.getParameter("projectId");

                Map<String, String> fileNameMap = new HashMap<String, String>();

                Map<String, String> filePathMap = new HashMap<String, String>();

                Map<String, String> fileIdMap = new HashMap<String, String>();

                InputsDto input = new InputsDto();
                RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, fileId);
                input.setDocId(rep.getBizId());
                if (!CommonUtil.isEmpty(rep.getBizId())) {
                    fileNameMap = inputsService.getRepFileNameAndBizIdMap(rep.getLibId());

                    filePathMap = inputsService.getRepFilePathAndBizIdMap(rep.getLibId());

                    fileIdMap = inputsService.getRepFileIdAndBizIdMap(rep.getLibId());
                }

                if (!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))) {
                    input.setDocName(fileNameMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))) {
                    input.setOriginObjectNameShow(filePathMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))) {
                    input.setDocIdShow(fileIdMap.get(input.getDocId()));
                }

                for (InputsDto curInputs : outInputsList) {
                    if (curInputs.getId().equals(curId)) {
                        // curInputs.setDownload(input.getDownload());
                        // curInputs.setDetail(input.getDetail());
                        // curInputs.setHavePower(input.getHavePower());
                        curInputs.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                        curInputs.setOriginType(PlanConstants.PROJECTLIBDOC);
                        curInputs.setDocId(input.getDocId());
                        curInputs.setDocName(input.getDocName());
                        curInputs.setOriginObjectNameShow(input.getOriginObjectNameShow());
                        curInputs.setDocIdShow(input.getDocIdShow());
                        curInputs.setMatchFlag("");
                        break;
                    }
                }

            }
            else {
                String planId = request.getParameter("planId");
                String inputsName = request.getParameter("inputsName");
                InputsDto input = new InputsDto();
                PlanDto plan = planService.getPlanEntity(planId);
                List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
                ProjDocVo projDoc = new ProjDocVo();
                if (!CommonUtil.isEmpty(projDocRelationList)) {
                    for (ProjDocVo vo : projDocRelationList) {
                        if (vo.getDeliverableName().equals(inputsName)) {
                            projDoc = vo;
                            break;
                        }
                    }
                }

                if (!CommonUtil.isEmpty(projDoc)) {
                    input.setDocId(projDoc.getDocId());
                    input.setOriginObjectId(plan.getId());
                    input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                }

                if (StringUtils.isNotEmpty(input.getDocId())) {
                    RepFileDto r = repFileService.getRepFileByRepFileId(appKey, input.getDocId());
                    if (!CommonUtil.isEmpty(r)) {
                        String havePower = planService.getOutPower(input.getDocId(), useObjectId,
                            UserUtil.getCurrentUser().getId());
                        if ("downloadDetail".equals(havePower)) {
                            input.setDownload(true);
                            input.setDetail(true);
                            input.setHavePower(true);
                        }
                        else if ("detail".equals(havePower)) {
                            input.setDownload(false);
                            input.setDetail(true);
                            input.setHavePower(true);
                        }
                        else {
                            input.setDownload(false);
                            input.setDetail(false);
                            input.setHavePower(false);
                        }
                        input.setSecurityLeve(r.getSecurityLevel());
                    }
                }

                for (InputsDto curInputs : outInputsList) {
                    if (curInputs.getId().equals(curId)) {
                        curInputs.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                        curInputs.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                        curInputs.setDownload(input.getDownload());
                        curInputs.setDetail(input.getDetail());
                        curInputs.setHavePower(input.getHavePower());
                        curInputs.setSecurityLeve(input.getSecurityLeve());
                        curInputs.setOriginObjectNameShow(plan.getPlanNumber() + "."
                                                          + plan.getPlanName());
                        curInputs.setDocId(projDoc.getDocId());
                        curInputs.setDocName(projDoc.getDocName());
                        curInputs.setOriginObjectId(input.getOriginObjectId());
                        curInputs.setOriginDeliverablesInfoId(input.getOriginDeliverablesInfoId());
                        if (!CommonUtil.isEmpty(input.getDownload())) {
                            curInputs.setExt1(input.getDownload().toString());
                            curInputs.setExt2(input.getHavePower().toString());
                            curInputs.setExt3(input.getDetail().toString());
                        }
                        curInputs.setMatchFlag("");
                        break;
                    }
                }

            }
            request.getSession().setAttribute(PlanConstants.FLOWTASK_MATCHINPUTS_KEY + uuId,
                outInputsList);
        }
        catch (Exception e) {}
        finally {
            return j;
        }
    }

    /**
     * 匹配单行选择确认更新的文档和来源(变更)
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "updateChangeMatchProjLibAndPlanLink")
    @ResponseBody
    public AjaxJson updateChangeMatchProjLibAndPlanLink(HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String useObjectId = request.getParameter("useObjectId");
        String useObjectIdDataBase = "";
        String originType = request.getParameter("originType");
        String rowId = request.getParameter("rowId");
        String tempId = request.getParameter("tempId");
        String parentPlanId = request.getParameter("parentPlanId");
        String uuId = request.getParameter("uuId");
        try {
            List<FlowTaskInputsMatchVo> changeFlowTaskList = (List<FlowTaskInputsMatchVo>)request.getSession().getAttribute(
                PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId);
            String curId = "";
            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                curId = rowId;
            }
            else {
                curId = tempId;
            }

            List<FlowTaskVo> oldChangeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            Map<String, String> outputIdAndIdMap = new HashMap<String, String>();
            Map<String, String> planIdAndIdMap = new HashMap<String, String>();
            for (FlowTaskVo curFlowTaskVo : oldChangeFlowTaskList) {
                if (!CommonUtil.isEmpty(curFlowTaskVo.getOutputList())) {
                    for (FlowTaskDeliverablesInfoVo curFlowTaskDeliverablesInfoVo : curFlowTaskVo.getOutputList()) {
                        if (!CommonUtil.isEmpty(curFlowTaskDeliverablesInfoVo.getOutputId())) {
                            outputIdAndIdMap.put(curFlowTaskDeliverablesInfoVo.getOutputId(),
                                curFlowTaskDeliverablesInfoVo.getId());
                        }
                    }
                }
                if (!CommonUtil.isEmpty(curFlowTaskVo.getPlanId())) {
                    planIdAndIdMap.put(curFlowTaskVo.getPlanId(), curFlowTaskVo.getId());
                }
            }

            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                String fileId = request.getParameter("fileId");
                String folderId = request.getParameter("folderId");
                String projectId = request.getParameter("projectId");

                Map<String, String> fileNameMap = new HashMap<String, String>();

                Map<String, String> filePathMap = new HashMap<String, String>();

                Map<String, String> fileIdMap = new HashMap<String, String>();

                InputsDto input = new InputsDto();
                RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, fileId);
                input.setDocId(rep.getBizId());
                if (!CommonUtil.isEmpty(rep.getBizId())) {
                    fileNameMap = inputsService.getRepFileNameAndBizIdMap(rep.getLibId());

                    filePathMap = inputsService.getRepFilePathAndBizIdMap(rep.getLibId());

                    fileIdMap = inputsService.getRepFileIdAndBizIdMap(rep.getLibId());
                }

                if (!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))) {
                    input.setDocName(fileNameMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))) {
                    input.setOriginObjectNameShow(filePathMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))) {
                    input.setDocIdShow(fileIdMap.get(input.getDocId()));
                }

                if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                    for (FlowTaskInputsMatchVo curFlowTaskInputsVo : changeFlowTaskList) {
                        if (curFlowTaskInputsVo.getId().equals(curId)) {
                            curFlowTaskInputsVo.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                            curFlowTaskInputsVo.setOriginType(PlanConstants.PROJECTLIBDOC);
                            curFlowTaskInputsVo.setOriginObjectNameShow(input.getOriginObjectNameShow());
                            curFlowTaskInputsVo.setDocId(input.getDocId());
                            curFlowTaskInputsVo.setDocName(input.getDocName());
                            curFlowTaskInputsVo.setDocIdShow(input.getDocIdShow());
                            curFlowTaskInputsVo.setMatchFlag("");
                            break;
                        }
                    }
                }
            }
            else {
                String planId = request.getParameter("planId");
                String inputsName = request.getParameter("inputsName");
                InputsDto input = new InputsDto();
                PlanDto plan = planService.getPlanEntity(planId);
                List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
                ProjDocVo projDoc = new ProjDocVo();
                if (!CommonUtil.isEmpty(projDocRelationList)) {
                    for (ProjDocVo vo : projDocRelationList) {
                        if (vo.getDeliverableName().equals(inputsName)) {
                            projDoc = vo;
                            break;
                        }
                    }
                }

                if (!CommonUtil.isEmpty(projDoc)) {
                    input.setDocId(projDoc.getDocId());
                    if (CommonUtil.isEmpty(planIdAndIdMap.get(plan.getId()))) {
                        input.setOriginObjectId(plan.getId());
                    }
                    else {
                        input.setOriginObjectId(planIdAndIdMap.get(plan.getId()));
                    }
                    if (CommonUtil.isEmpty(outputIdAndIdMap.get(projDoc.getDeliverableId()))) {
                        input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                    }
                    else {
                        input.setOriginDeliverablesInfoId(outputIdAndIdMap.get(projDoc.getDeliverableId()));
                    }
                }

                if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                    for (FlowTaskInputsMatchVo curFlowTaskInputsVo : changeFlowTaskList) {
                        if (curFlowTaskInputsVo.getId().equals(curId)) {
                            curFlowTaskInputsVo.setOriginObjectNameShow(plan.getPlanNumber() + "."
                                                                        + plan.getPlanName());
                            curFlowTaskInputsVo.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                            curFlowTaskInputsVo.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                            curFlowTaskInputsVo.setDownload(input.getDownload());
                            curFlowTaskInputsVo.setDetail(input.getDetail());
                            curFlowTaskInputsVo.setHavePower(input.getHavePower());
                            // curFlowTaskInputsVo.setSecurityLeve(input.getSecurityLeve());
                            curFlowTaskInputsVo.setDocId(projDoc.getDocId());
                            curFlowTaskInputsVo.setDocName(projDoc.getDocName());
                            curFlowTaskInputsVo.setOriginObjectId(input.getOriginObjectId());
                            curFlowTaskInputsVo.setOriginDeliverablesInfoId(input.getOriginDeliverablesInfoId());
                            if (!CommonUtil.isEmpty(input.getDownload())) {
                                curFlowTaskInputsVo.setExt1(input.getDownload().toString());
                                curFlowTaskInputsVo.setExt2(input.getHavePower().toString());
                                curFlowTaskInputsVo.setExt3(input.getDetail().toString());
                            }
                            curFlowTaskInputsVo.setMatchFlag("");
                            break;
                        }
                    }
                }

            }
            request.getSession().setAttribute(PlanConstants.FLOWTASK_CHANGEMATCHINPUTS_KEY + uuId,
                changeFlowTaskList);
        }
        catch (Exception e) {}
        finally {
            return j;
        }
    }

    /**
     * 暂存外部输入选择项目库或者外部计划的数据
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "updateProjLibAndPlanLinkChange")
    @ResponseBody
    public AjaxJson updateProjLibAndPlanLinkChange(HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String useObjectId = request.getParameter("useObjectId");
        String useObjectIdDataBase = "";
        String originType = request.getParameter("originType");
        String rowId = request.getParameter("rowId");
        String tempId = request.getParameter("tempId");
        String parentPlanId = request.getParameter("parentPlanId");
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            Map<String, String> outputIdAndIdMap = new HashMap<String, String>();
            Map<String, String> planIdAndIdMap = new HashMap<String, String>();
            for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                if (!CommonUtil.isEmpty(curFlowTaskVo.getOutputList())) {
                    for (FlowTaskDeliverablesInfoVo curFlowTaskDeliverablesInfoVo : curFlowTaskVo.getOutputList()) {
                        if (!CommonUtil.isEmpty(curFlowTaskDeliverablesInfoVo.getOutputId())) {
                            outputIdAndIdMap.put(curFlowTaskDeliverablesInfoVo.getOutputId(),
                                curFlowTaskDeliverablesInfoVo.getId());
                        }
                    }
                }
                if (!CommonUtil.isEmpty(curFlowTaskVo.getPlanId())) {
                    planIdAndIdMap.put(curFlowTaskVo.getPlanId(), curFlowTaskVo.getId());
                }
            }

            String curId = "";
            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                curId = rowId;
            }
            else {
                curId = tempId;
            }

            if (originType.equals(PlanConstants.PROJECTLIBDOC)) {
                String fileId = request.getParameter("fileId");
                String folderId = request.getParameter("folderId");
                String projectId = request.getParameter("projectId");

                Map<String, String> fileNameMap = new HashMap<String, String>();

                Map<String, String> filePathMap = new HashMap<String, String>();

                Map<String, String> fileIdMap = new HashMap<String, String>();

                InputsDto input = new InputsDto();
                RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, fileId);
                input.setDocId(rep.getBizId());
                if (!CommonUtil.isEmpty(rep.getBizId())) {
                    fileNameMap = inputsService.getRepFileNameAndBizIdMap(rep.getLibId());

                    filePathMap = inputsService.getRepFilePathAndBizIdMap(rep.getLibId());

                    fileIdMap = inputsService.getRepFileIdAndBizIdMap(rep.getLibId());
                }

                if (!CommonUtil.isEmpty(fileNameMap.get(input.getDocId()))) {
                    input.setDocName(fileNameMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(filePathMap.get(input.getDocId()))) {
                    input.setOriginObjectNameShow(filePathMap.get(input.getDocId()));
                }
                if (!CommonUtil.isEmpty(fileIdMap.get(input.getDocId()))) {
                    input.setDocIdShow(fileIdMap.get(input.getDocId()));
                }

                for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                    if (curFlowTaskVo.getId().equals(useObjectId)) {
                        if (!CommonUtil.isEmpty(curFlowTaskVo.getInputList())) {
                            for (FlowTaskInputsVo curFlowTaskInputsVo : curFlowTaskVo.getInputList()) {
                                if (curFlowTaskInputsVo.getId().equals(curId)) {
                                    curFlowTaskInputsVo.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                    curFlowTaskInputsVo.setOriginType(PlanConstants.PROJECTLIBDOC);
                                    curFlowTaskInputsVo.setOriginObjectName(input.getOriginObjectNameShow());
                                    curFlowTaskInputsVo.setDocId(input.getDocId());
                                    curFlowTaskInputsVo.setDocName(input.getDocName());
                                    curFlowTaskInputsVo.setDocIdShow(input.getDocIdShow());
                                    break;
                                }
                            }
                        }

                    }
                }

            }
            else {
                String planId = request.getParameter("planId");
                String inputsName = request.getParameter("inputsName");
                InputsDto input = new InputsDto();
                PlanDto plan = planService.getPlanEntity(planId);
                List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
                ProjDocVo projDoc = new ProjDocVo();
                if (!CommonUtil.isEmpty(projDocRelationList)) {
                    for (ProjDocVo vo : projDocRelationList) {
                        if (vo.getDeliverableName().equals(inputsName)) {
                            projDoc = vo;
                            break;
                        }
                    }
                }

                if (!CommonUtil.isEmpty(projDoc)) {
                    input.setDocId(projDoc.getDocId());
                    if (CommonUtil.isEmpty(planIdAndIdMap.get(plan.getId()))) {
                        input.setOriginObjectId(plan.getId());
                    }
                    else {
                        input.setOriginObjectId(planIdAndIdMap.get(plan.getId()));
                    }
                    if (CommonUtil.isEmpty(outputIdAndIdMap.get(projDoc.getDeliverableId()))) {
                        input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                    }
                    else {
                        input.setOriginDeliverablesInfoId(outputIdAndIdMap.get(projDoc.getDeliverableId()));
                    }
                }

                for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                    if (curFlowTaskVo.getId().equals(useObjectId)) {
                        if (!CommonUtil.isEmpty(curFlowTaskVo.getInputList())) {
                            for (FlowTaskInputsVo curFlowTaskInputsVo : curFlowTaskVo.getInputList()) {
                                if (curFlowTaskInputsVo.getId().equals(curId)) {
                                    curFlowTaskInputsVo.setOriginObjectName(plan.getPlanNumber()
                                                                            + "."
                                                                            + plan.getPlanName());
                                    curFlowTaskInputsVo.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                                    curFlowTaskInputsVo.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                                    curFlowTaskInputsVo.setDownload(input.getDownload());
                                    curFlowTaskInputsVo.setDetail(input.getDetail());
                                    curFlowTaskInputsVo.setHavePower(input.getHavePower());
                                    // curFlowTaskInputsVo.setSecurityLeve(input.getSecurityLeve());
                                    curFlowTaskInputsVo.setDocId(projDoc.getDocId());
                                    curFlowTaskInputsVo.setDocName(projDoc.getDocName());
                                    curFlowTaskInputsVo.setOriginObjectId(input.getOriginObjectId());
                                    curFlowTaskInputsVo.setOriginDeliverablesInfoId(input.getOriginDeliverablesInfoId());
                                    if (!CommonUtil.isEmpty(input.getDownload())) {
                                        curFlowTaskInputsVo.setExt1(input.getDownload().toString());
                                        curFlowTaskInputsVo.setExt2(input.getHavePower().toString());
                                        curFlowTaskInputsVo.setExt3(input.getDetail().toString());
                                    }
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
        }
        catch (Exception e) {}
        finally {
            return j;
        }
    }

    /**
     * 查询流程任务信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "flowTaskList")
    private void flowTaskList(HttpServletRequest req, HttpServletResponse response) {
        String parentPlanId = req.getParameter("parentPlanId");
        if (StringUtil.isNotEmpty(parentPlanId)) {
            PlanDto parent = new PlanDto();
            parent.setParentPlanId(parentPlanId);
            List<PlanDto> flowTaskList = planService.queryPlanOrderByStarttime(parent, 1, 10, false);
            req.getSession().setAttribute(PlanConstants.FLOWTASK_LIST_KEY + parentPlanId,
                flowTaskList);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            for (PlanDto p : flowTaskList) {
                p.setParentPlanName(p.getPlanName());
//                if (p.getPlanLevelInfo() == null
//                    || !"1".equals(p.getPlanLevelInfo().getAvaliable())
//                    || ConfigStateConstants.STOP.equals(p.getPlanLevelInfo().getStopFlag())) {
//                    p.setPlanLevel("");
//                }
                if ("true".equals(p.getFromTemplate())) {
                    if (StringUtils.isEmpty(p.getWorkTimeReference())) {
                        p.setWorkTimeReference(p.getWorkTime());
                    }
                }
                else {
                    p.setWorkTimeReference("0");
                }
            }
            DataGridReturn data = new DataGridReturn(flowTaskList.size(), flowTaskList);
            String json = gson.toJson(data);
            TagUtil.ajaxResponse(response, json);
        }
    }

    /**
     * 查询流程任务信息
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "flowTaskListForChange")
    private void flowTaskListForChange(HttpServletRequest req, HttpServletResponse response) {
        String parentPlanId = req.getParameter("parentPlanId");
        if (StringUtil.isNotEmpty(parentPlanId)) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            changeFlowTaskList = planService.taskSort(changeFlowTaskList);
            DataGridReturn data = new DataGridReturn(changeFlowTaskList.size(), changeFlowTaskList);
            BusinessConfig condition = new BusinessConfig();
            condition.setConfigType(ConfigTypeConstants.PLANLEVEL);
            List<BusinessConfig> levelList = businessConfigService.searchUseableBusinessConfigs(condition);
            List<BusinessConfigDto> planLevelList = CodeUtils.JsonListToList(levelList,BusinessConfigDto.class);
       //     List<BusinessConfigDto> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfigDto>>(){});
            Map<String, BusinessConfigDto> planLevelMap = new HashMap<String, BusinessConfigDto>();
            for (BusinessConfigDto planlevel : planLevelList) {
                planLevelMap.put(planlevel.getId(), planlevel);
            }
            for (FlowTaskVo f : changeFlowTaskList) {
                f.setParentPlanName(f.getPlanName());
                if (StringUtils.isEmpty(f.getPlanLevel())
                    || CommonUtil.isEmpty(planLevelMap.get(f.getPlanLevel()))) {
                    f.setPlanLevel("");
                }
                else {
                    f.setPlanLevelInfo(planLevelMap.get(f.getPlanLevel()));
                }
                if ("true".equals(f.getFromTemplate())) {
                    if (StringUtils.isEmpty(f.getWorkTimeReference())) {
                        PlanDto plan = planService.getPlanEntity(f.getPlanId());
                        f.setWorkTimeReference(plan.getWorkTime());
                    }
                }
                else {
                    f.setWorkTimeReference("0");
                }
            }
            changeFlowTaskList = planService.taskSort(changeFlowTaskList);
            String json = gson.toJson(data);
            TagUtil.ajaxResponse(response, json);
        }
    }

    /**
     * 批量编辑时、递归更新其后置开始结束时间及工期
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeFlowTaskList")
    private void changeFlowTaskList(PlanDto flowTask, HttpServletRequest req,
                                    HttpServletResponse response) {
        Date planEndTime = getEndTimeByStartTimeAndWorkTime(flowTask);
        flowTask.setPlanEndTime(planEndTime);
        List<PlanDto> flowTaskList = (List<PlanDto>)req.getSession().getAttribute(
            PlanConstants.FLOWTASK_LIST_KEY + flowTask.getParentPlanId());
        for (PlanDto task : flowTaskList) {
            if (task.getId().equals(flowTask.getId())) {
                task.setOwner(flowTask.getOwner());
                task.setPlanLevel(flowTask.getPlanLevel());
                task.setWorkTime(flowTask.getWorkTime());
                task.setPlanEndTime(flowTask.getPlanEndTime());
                task.setParentPlanName(flowTask.getParentPlanName());
                task.setPlanName(flowTask.getPlanName());
                task.setFromTemplate(flowTask.getFromTemplate());
                break;
            }
        }
        PlanDto plan = new PlanDto();
        plan.setPreposeIds(flowTask.getId());
        List<PreposePlanDto> postposes = preposePlanService.getPostposesByPreposeId(plan);
        for (PreposePlanDto postpose : postposes) {
            for (PlanDto task : flowTaskList) {
                if (task.getId().equals(postpose.getPlanId())) {
                    Date maxPreposeEndTime = getPreposeMaxEndTime(postpose, flowTaskList);
                    task.setPlanStartTime(getStartTimeByPrepose(flowTask, maxPreposeEndTime));
                    task.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(task));
                    changePostposes(task, flowTaskList);
                }
            }
        }
        for (int j = 0; j < flowTaskList.size() - 1; j++ ) {
            PlanDto floTask = new PlanDto();
            for (int k = j; k < flowTaskList.size() - 1; k++ ) {
                Date s = flowTaskList.get(k).getPlanStartTime();
                Date s1 = flowTaskList.get(k + 1).getPlanStartTime();
                if (s.compareTo(s1) == 1) {
                    floTask = flowTaskList.get(k);
                    flowTaskList.set(k, flowTaskList.get(k + 1));
                    flowTaskList.set(k + 1, floTask);
                }
                else if (s.compareTo(s1) == 0) {
                    PlanDto floTask1 = new PlanDto();
                    Date endtime1 = flowTaskList.get(k).getPlanEndTime();
                    Date endtime2 = flowTaskList.get(k + 1).getPlanEndTime();
                    if (endtime1.compareTo(endtime2) == 1) {
                        floTask1 = flowTaskList.get(k);
                        flowTaskList.set(k, flowTaskList.get(k + 1));
                        flowTaskList.set(k + 1, floTask1);
                    }
                }
            }
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(flowTaskList.size(), flowTaskList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 流程任务变更 批量编辑时、递归更新其后置开始结束时间及工期
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeFlowTaskListForChange")
    private void changeFlowTaskListForChange(FlowTaskVo flowTask, HttpServletRequest req,
                                             HttpServletResponse response) {
        String flowTaskParentId = req.getParameter("flowTaskParentId");
        Date planEndTime = getEndTimeByStartTimeAndWorkTime(flowTask);
        flowTask.setPlanEndTime(planEndTime);
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + flowTaskParentId);

        List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)req.getSession().getAttribute(
            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + flowTaskParentId);

        for (FlowTaskVo task : changeFlowTaskList) {
            if (task.getId().equals(flowTask.getId())) {
                task.setOwner(flowTask.getOwner());
                task.setPlanLevel(flowTask.getPlanLevel());
                task.setWorkTime(flowTask.getWorkTime());
                task.setPlanEndTime(flowTask.getPlanEndTime());
                task.setParentPlanName(flowTask.getParentPlanName());
                task.setPlanName(flowTask.getPlanName());
                task.setFromTemplate(flowTask.getFromTemplate());
                break;
            }
        }

        List<FlowTaskPreposeVo> postposes = new ArrayList<FlowTaskPreposeVo>();
        for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
            if (prepose.getPreposeId().equals(flowTask.getId())) {
                postposes.add(prepose);
            }
        }
        changeFlowTaskList = planService.taskSort(changeFlowTaskList);
        for (FlowTaskPreposeVo postpose : postposes) {
            for (FlowTaskVo task : changeFlowTaskList) {
                if (task.getId().equals(postpose.getFlowTaskId())) {
                    Date maxPreposeEndTime = getPreposeMaxEndTime(postpose, changeFlowTaskList,
                        flowTaskPreposeList);
                    task.setPlanStartTime(getStartTimeByPrepose(flowTask, maxPreposeEndTime));
                    task.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(task));
                    changePostposes(task, changeFlowTaskList, flowTaskPreposeList);
                }
            }
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();

        DataGridReturn data = new DataGridReturn(changeFlowTaskList.size(), changeFlowTaskList);

        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 批量编辑时、递归更新其后置开始结束时间及工期
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeFlowTaskListAndSave")
    @ResponseBody
    private AjaxJson changeFlowTaskListAndSave(PlanDto flowTask, HttpServletRequest req,
                                               HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        List<PlanDto> flowTaskList = (List<PlanDto>)req.getSession().getAttribute(
            PlanConstants.FLOWTASK_LIST_KEY + flowTask.getParentPlanId());
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();
//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        boolean mustUseStandard = false;
        if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
            || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            mustUseStandard = true;
        }
        if (mustUseStandard) {
            NameStandardDto nameStandard = new NameStandardDto();
            nameStandard.setStopFlag("启用");
            List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
            Map<String, String> nameStdMap = new HashMap<String, String>();
            for (NameStandardDto std : list) {
                nameStdMap.put(std.getName(), std.getName());
            }
            if (CommonUtil.isEmpty(nameStdMap)) {
                aj.setSuccess(false);
                aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                return aj;
            }
            if (StringUtils.isEmpty(nameStdMap.get(flowTask.getPlanName()))) {
                aj.setSuccess(false);
                aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                return aj;
            }
            for (PlanDto pl : flowTaskList) {
                if (StringUtils.isEmpty(nameStdMap.get(pl.getPlanName()))) {
                    aj.setSuccess(false);
                    aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return aj;
                }
            }
        }
        Date planEndTime = getEndTimeByStartTimeAndWorkTime(flowTask);
        flowTask.setPlanEndTime(planEndTime);
        return changeFlowTaskListAndSave(flowTask, flowTaskList, aj,
            switchStr);
    }


    private AjaxJson changeFlowTaskListAndSave(PlanDto flowTask, List<PlanDto> flowTaskList, AjaxJson aj,
                                              String switchStr) {
        String parentPlanId = flowTask.getParentPlanId();
        PlanDto parent = planService.getPlanEntity(parentPlanId);

        if (parent.getPlanName().equals(flowTask.getPlanName())) {
            aj.setSuccess(false);
            aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameParentnameSameCheck"));
            return aj;
        }

        // 判断流程任务不能和父计划重名
        for (PlanDto task : flowTaskList) {
            if (parent.getPlanName().equals(task.getPlanName())) {
                aj.setSuccess(false);
                aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameParentnameSameCheck"));
                return aj;
            }
        }

        // 流程任务之间不能重名
        Map<String, String> childNameMap = new HashMap<String, String>();
        for (PlanDto task : flowTaskList) {
            if (StringUtils.isEmpty(childNameMap.get(task.getPlanName()))) {
                childNameMap.put(task.getPlanName(), task.getPlanName());
            }
            else {
                aj.setSuccess(false);
                aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"));
                return aj;
            }
        }

        for (PlanDto task : flowTaskList) {
            if (!checkNames(flowTask.getPlanName()) && !"true".equals(task.getFromTemplate())) {
                aj.setSuccess(false);
                Object[] arguments = new String[] {flowTask.getPlanName()};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.tasknameInexistenceNameStandard",
                    arguments));
                // message = "任务【"+task.getPlanName()+"】不在活动名称库中，请修改";
                return aj;
            }

            if (task.getId().equals(flowTask.getId())) {

                boolean nameChange = false;
                // 20160928 如果计划名称变更，则先删除该计划相关的输入及输入对应的输入
                if (!task.getPlanName().equals(flowTask.getPlanName())) {
                    nameChange = true;
                }

                task.setOwner(flowTask.getOwner());
                task.setPlanLevel(flowTask.getPlanLevel());
                task.setWorkTime(flowTask.getWorkTime());
                task.setPlanEndTime(flowTask.getPlanEndTime());

                task.setPlanName(flowTask.getPlanName());
                task.setFromTemplate(flowTask.getFromTemplate());

                if (!"true".equals(task.getFromTemplate()) && nameChange) {
                    List<DeliverablesInfoDto> list2 = deliverablesInfoService.getDeliverablesByUseObeject(
                        "PLAN", task.getId());

                    NameStandardDto condition2 = new NameStandardDto();
                    condition2.setStopFlag("启用");
                    condition2.setName(task.getPlanName());
                    List<NameStandardDto> nameStandardList2 = nameStandardService.searchNameStandards(condition2);
                    if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                        || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                        || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                        if (nameStandardList2.size() > 0) {
                            for (NameStandardDto n : nameStandardList2) {
                                if ("启用".equals(n.getStopFlag())) {
                                    NameStandardDeliveryRelationDto relation = new NameStandardDeliveryRelationDto();
                                    relation.setNameStandardId(n.getId());
                                    List<NameStandardDeliveryRelationDto> list = nameStandardDeliveryRelationService.searchForPage(
                                        relation, 1, 10);
                                    planService.deleteDeliverablesByPlanId(task.getId());

                                    if (list.size() > 0) {
                                        for (NameStandardDeliveryRelationDto r : list) {
                                            if(!CommonUtil.isEmpty(r.getDeliveryStandardId())) {
                                                r.setDeliveryStandard(deliveryStandardService.getDeliveryStandardEntity(r.getDeliveryStandardId()));
                                            }
                                            if (r.getDeliveryStandard() != null) {
                                                int a = 0;
                                                for (DeliverablesInfoDto d : list2) {
                                                    if (r.getDeliveryStandard().getName().equals(
                                                        d.getName())) {
                                                        a = 1;
                                                        break;
                                                    }
                                                }
                                                if (a == 0 && r.getDeliveryStandardId() != null) {
                                                    DeliverablesInfoDto deliverablesInfoTemp = new DeliverablesInfoDto();
                                                    deliverablesInfoService.initBusinessObject(deliverablesInfoTemp);
                                                    deliverablesInfoTemp.setId(null);
                                                    deliverablesInfoTemp.setName(r.getDeliveryStandard().getName());
                                                    deliverablesInfoTemp.setUseObjectId(task.getId());
                                                    deliverablesInfoTemp.setUseObjectType("PLAN");
                                                    deliverablesInfoTemp.setOrigin(PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD);
                                                    deliverablesInfoService.saveDeliverablesInfo(deliverablesInfoTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        PlanDto plan = new PlanDto();
        plan.setPreposeIds(flowTask.getId());
        List<PreposePlanDto> postposes = preposePlanService.getPostposesByPreposeId(plan);
        for (PreposePlanDto postpose : postposes) {
            for (PlanDto task : flowTaskList) {
                if (task.getId().equals(postpose.getPlanId())) {
                    Date maxPreposeEndTime = getPreposeMaxEndTime(postpose, flowTaskList);
                    task.setPlanStartTime(getStartTimeByPrepose(flowTask, maxPreposeEndTime));
                    task.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(task));
                    changePostposes(task, flowTaskList);
                }
            }
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        for (PlanDto task : flowTaskList) {
            if (task.getPlanStartTime().getTime() < parent.getPlanStartTime().getTime()) {
                aj.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanStartTime())};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentStartTimeCheck",
                    arguments));
                return aj;
            }
            if (task.getPlanStartTime().getTime() > parent.getPlanEndTime().getTime()) {
                aj.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanEndTime())};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentEndTimeCheck",
                    arguments));
                return aj;
            }

            if (task.getPlanEndTime().getTime() < parent.getPlanStartTime().getTime()) {
                aj.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanStartTime())};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentStartTimeCheck",
                    arguments));
                return aj;
            }
            if (task.getPlanEndTime().getTime() > parent.getPlanEndTime().getTime()) {
                aj.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanEndTime())};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentEndTimeCheck",
                    arguments));
                return aj;
            }
        }
        Map<String, String> cellWorkTimeMap = new HashMap<String, String>();
        for (PlanDto p : flowTaskList) {
            PlanDto task = planService.getPlanEntity(p.getId());
            if (StringUtil.isNotEmpty(p.getOwner())) {
                task.setOwner(p.getOwner());
            }
            if (StringUtil.isNotEmpty(p.getPlanLevel())) {
                task.setPlanLevel(p.getPlanLevel());
            }
            if ((p.getPlanEndTime().getTime() != task.getPlanEndTime().getTime())
                || (p.getPlanStartTime().getTime() != task.getPlanStartTime().getTime())) {
                ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
                resourceLinkInfo.setUseObjectId(task.getId());// 关连的外键id
                resourceLinkInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);// 关连的外键type
                List<ResourceLinkInfoDto> res = resourceLinkInfoService.queryResourceList(
                    resourceLinkInfo, 1, 10, false);// 一个任务对应的资源列表

                if (!CommonUtil.isEmpty(res)) {
                    for (ResourceLinkInfoDto linkInfo : res) {
                        linkInfo.setStartTime(p.getPlanStartTime());
                        linkInfo.setEndTime(p.getPlanEndTime());
                        resourceLinkInfoService.doAddResourceLinkInfo(linkInfo);
//                        planService.saveOrUpdate(linkInfo);
                    }
                }
            }
            task.setPlanStartTime(p.getPlanStartTime());
            task.setPlanEndTime(p.getPlanEndTime());
            task.setWorkTime(p.getWorkTime());
            task.setPlanName(p.getPlanName());
            planService.updatePlan(task);
//            planService.save(task);
            if (StringUtil.isEmpty(parentPlanId) && StringUtil.isNotEmpty(task.getParentPlanId())) {
                parentPlanId = task.getParentPlanId();
            }
            String label = task.getPlanName() + "," + task.getWorkTime() + "天";
            cellWorkTimeMap.put(task.getCellId(), label);
        }
        String flowResolveXml = parent.getFlowResolveXml();
        flowResolveXml = refreshFlowResolveXml(flowResolveXml, cellWorkTimeMap);
        parent.setFlowResolveXml(flowResolveXml);
        CommonInitUtil.initGLVDataForUpdate(parent);
        planService.updatePlan(parent);
////        saveOrUpdate(parent);
//        PlanDto conditionPlan = new PlanDto();
//        conditionPlan.setParentPlanId(parentPlanId);
//        List<PlanDto> list = planService.queryPlanOrderByStarttime(conditionPlan, 1, 10, false);
//        for (int i = 0; i < list.size(); i++ ) {
//            PlanDto task = list.get(i);
//            task.setStoreyNo(i + 1);
////            planService.save(task);
//            planService.savePlanByPlanDto(task);
//        }
        aj.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess"));
        aj.setSuccess(true);
        return aj;
    }

    /**
     * 判断计划名称是否在活动名称库中
     *
     * @param planName
     * @return
     * @see
     */
    private boolean checkNames(String planName) {
        boolean repeat = true;
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();
//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)) {
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
            if (nameStandardList2.size() == 0) {
                repeat = false;
            }
            else {
                repeat = true;
            }
        }

        return repeat;
    }

    /**
     * 批量编辑时、递归更新其后置开始结束时间及工期
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "changeFlowTaskListAndSaveForChange")
    @ResponseBody
    private AjaxJson changeFlowTaskListAndSaveForChange(FlowTaskVo flowTask,
                                                        HttpServletRequest req,
                                                        HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        String message = "";
        Date planEndTime = getEndTimeByStartTimeAndWorkTime(flowTask);
        flowTask.setPlanEndTime(planEndTime);
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + flowTask.getParentPlanId());

        List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = (List<ChangeFlowTaskCellConnectVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + flowTask.getParentPlanId());

        List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)req.getSession().getAttribute(
            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + flowTask.getParentPlanId());

        // 20160928 如果计划名称变更，则先删除该计划相关的输入及输入对应的输入
        boolean nameChange = false;
        Map<String, String> delOutputIds = new HashMap<String, String>();
        for (FlowTaskVo task : changeFlowTaskList) {
            if (task.getId().equals(flowTask.getId())) {
                if (!task.getPlanName().equals(flowTask.getPlanName())) {
                    task.getInputList().clear();
                    if (!CommonUtil.isEmpty(task.getOutputList())) {
                        for (FlowTaskDeliverablesInfoVo out : task.getOutputList()) {
                            delOutputIds.put(out.getId(), out.getId());
                        }
                    }
                    task.getOutputList().clear();
                    nameChange = true;
                }
                break;
            }
        }

        for (FlowTaskVo task : changeFlowTaskList) {

            // 20160928 去除删除的前置所对应的输入
            if (!CommonUtil.isEmpty(task.getInputList()) && !CommonUtil.isEmpty(delOutputIds)) {
                List<FlowTaskInputsVo> inputsList = new ArrayList<FlowTaskInputsVo>();
                for (FlowTaskInputsVo inputs : task.getInputList()) {
                    if (StringUtils.isEmpty(delOutputIds.get(inputs.getOriginDeliverablesInfoId()))) {
                        inputsList.add(inputs);
                    }
                }
                task.getInputList().clear();
                task.getInputList().addAll(inputsList);
            }

            if (task.getId().equals(flowTask.getId())) {
                task.setOwner(flowTask.getOwner());
                task.setPlanLevel(flowTask.getPlanLevel());
                task.setWorkTime(flowTask.getWorkTime());
                task.setPlanEndTime(flowTask.getPlanEndTime());
                task.setPlanName(flowTask.getPlanName());
                task.setFromTemplate(flowTask.getFromTemplate());

                if (!checkNames(task.getPlanName()) && !"true".equals(task.getFromTemplate())) {
                    aj.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName()};
                    aj.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.taskNameInexistenceNeedEdit",
                        arguments));
                    return aj;
                }

                if (!"true".equals(task.getFromTemplate()) && nameChange) {
                    FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
                    String switchStr = switchStrJson.getObj().toString();
//                    String switchStr = "";
//                    try {
//                        RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                        RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                        String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//                        AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//                        switchStr = (String)ajaxJson.getObj();
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    NameStandardDto condition2 = new NameStandardDto();
                    condition2.setStopFlag("启用");
                    condition2.setName(task.getPlanName());
                    List<NameStandardDto> nameStandardList2 = nameStandardService.searchNameStandards(condition2);
                    if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                        || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                        || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                        if (nameStandardList2.size() > 0) {
                            for (NameStandardDto n : nameStandardList2) {
                                if ("启用".equals(n.getStopFlag())) {
                                    NameStandardDeliveryRelationDto relation = new NameStandardDeliveryRelationDto();
                                    relation.setNameStandardId(n.getId());
                                    List<NameStandardDeliveryRelationDto> list = nameStandardDeliveryRelationService.searchForPage(
                                        relation, 1, 10);
                                    for (ChangeFlowTaskCellConnectVo c : changeFlowTaskConnectList) {
                                        if (c.getCellId().equals(task.getCellId())) {
                                            deleteLinkInputForChange(c.getCellId(),
                                                c.getTargetId(), changeFlowTaskList);
                                        }
                                    }
                                    if (list.size() > 0) {
                                        task.getOutputList().clear();
                                        for (NameStandardDeliveryRelationDto r : list) {
                                            if(!CommonUtil.isEmpty(r.getDeliveryStandardId())) {
                                                r.setDeliveryStandard(deliveryStandardService.getDeliveryStandardEntity(r.getDeliveryStandardId()));
                                            }
                                            if (r.getDeliveryStandardId() != null) {
                                                FlowTaskDeliverablesInfoVo deliverablesInfoTemp = new FlowTaskDeliverablesInfoVo();
                                                // deliverablesInfoService.initBusinessObject(deliverablesInfoTemp);
                                                // 获取FlowTaskDeliverablesInfo的BusinessObject
//                                                RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                                                RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                                                String deliverablesInfoInitBusinessObjectJson = rdfConfigSupport.getDeliverablesInfoInitBusinessObject();
//                                                Gson gson = new GsonBuilder().setDateFormat(
//                                                    "yyyy-MM-dd").setPrettyPrinting().create();
//                                                AjaxJson ajaxJson = gson.fromJson(
//                                                    deliverablesInfoInitBusinessObjectJson,
//                                                    AjaxJson.class);
                                               FeignJson ajaxJson = rdFlowTaskFlowResolveService.getFlowTaskDeliverablesInfoInitBusinessObject();
                                                String mapList = (String)ajaxJson.getObj();
                                                if (!CommonUtil.isEmpty(mapList)
                                                    && !CommonUtil.isEmpty(mapList.split(",")[0])
                                                    && !CommonUtil.isEmpty(mapList.split(",")[1])) {
                                                    deliverablesInfoTemp.setAvaliable("1");
                                                    deliverablesInfoTemp.setSecurityLevel((short)1);
                                                    deliverablesInfoTemp.setBizCurrent(mapList.split(",")[0].toString());
                                                    deliverablesInfoTemp.setPolicy_id(mapList.split(",")[1].toString());
                                                    deliverablesInfoTemp.setBizId(UUID.randomUUID().toString());
                                                }
                                                deliverablesInfoTemp.setId(null);
                                                deliverablesInfoTemp.setName(r.getDeliveryStandard().getName());
                                                deliverablesInfoTemp.setUseObjectId(task.getId());
                                                deliverablesInfoTemp.setUseObjectType("PLAN");
                                                deliverablesInfoTemp.setOrigin(PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD);
                                                task.getOutputList().add(deliverablesInfoTemp);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        List<FlowTaskPreposeVo> postposes = new ArrayList<FlowTaskPreposeVo>();
        for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
            if (prepose.getPreposeId().equals(flowTask.getId())) {
                postposes.add(prepose);
            }
        }
        for (FlowTaskPreposeVo postpose : postposes) {
            for (FlowTaskVo task : changeFlowTaskList) {
                if (task.getId().equals(postpose.getFlowTaskId())) {
                    Date maxPreposeEndTime = getPreposeMaxEndTime(postpose, changeFlowTaskList,
                        flowTaskPreposeList);
                    task.setPlanStartTime(getStartTimeByPrepose(flowTask, maxPreposeEndTime));
                    task.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(task));
                    changePostposes(task, changeFlowTaskList, flowTaskPreposeList);
                }
            }
        }
        for (FlowTaskVo task : changeFlowTaskList) {
            if ((flowTask.getPlanEndTime().getTime() != task.getPlanEndTime().getTime())
                || (flowTask.getPlanEndTime().getTime() != task.getPlanStartTime().getTime())) {
                FlowTaskResourceLinkInfoVo flowTaskRs = new FlowTaskResourceLinkInfoVo();
                flowTaskRs.setUseObjectId(task.getId());// 关连的外键id
                flowTaskRs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);// 关连的外键id
                List<FlowTaskResourceLinkInfoVo> resourceList = task.getResourceLinkList();// 一个任务对应的资源列表

                if (!CommonUtil.isEmpty(resourceList)) {
                    for (FlowTaskResourceLinkInfoVo ftInfo : resourceList) {
                        ftInfo.setStartTime(task.getPlanStartTime());
                        ftInfo.setEndTime(task.getPlanEndTime());
                    }
                }
            }
        }
        // changeFlowTaskList = procTemplateService.taskSort(changeFlowTaskList);

        aj.setMsg(message);
        aj.setSuccess(true);
        return aj;
    }

    /**
     * 将后置计划的开始结束时间递归变更
     *
     * @param task
     * @see
     */
    private void changePostposes(PlanDto task, List<PlanDto> flowTaskList) {
        PlanDto plan = new PlanDto();
        plan.setPreposeIds(task.getId());
        List<PreposePlanDto> postposes = preposePlanService.getPostposesByPreposeId(plan);
        if (!CommonUtil.isEmpty(postposes)) {
            for (PreposePlanDto postpose : postposes) {
                for (PlanDto t : flowTaskList) {
                    if (t.getId().equals(postpose.getPlanId())
                        && !PlanConstants.PLAN_FINISH.equals(t.getBizCurrent())
                        && !PlanConstants.PLAN_FEEDBACKING.equals(t.getBizCurrent())) {
                        Date maxPreposeEndTime = getPreposeMaxEndTime(postpose, flowTaskList);
                        t.setPlanStartTime(getStartTimeByPrepose(task, maxPreposeEndTime));
                        t.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(t));
                        changePostposes(t, flowTaskList);
                    }
                }
            }
        }
    }

    /**
     * 将后置计划的开始结束时间递归变更
     *
     * @param task
     * @see
     */
    private void changePostposes(FlowTaskVo task, List<FlowTaskVo> changeFlowTaskList,
                                 List<FlowTaskPreposeVo> flowTaskPreposeList) {
        PlanDto plan = new PlanDto();
        plan.setPreposeIds(task.getId());
        List<FlowTaskPreposeVo> postposes = new ArrayList<FlowTaskPreposeVo>();
        for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
            if (prepose.getPreposeId().equals(task.getId())) {
                postposes.add(prepose);
            }
        }
        if (!CommonUtil.isEmpty(postposes)) {
            for (FlowTaskPreposeVo postpose : postposes) {
                for (FlowTaskVo t : changeFlowTaskList) {
                    if (t.getId().equals(postpose.getFlowTaskId())
                        && !PlanConstants.PLAN_FINISH.equals(t.getBizCurrent())
                        && !PlanConstants.PLAN_FEEDBACKING.equals(t.getBizCurrent())) {
                        Date maxPreposeEndTime = getPreposeMaxEndTime(postpose,
                            changeFlowTaskList, flowTaskPreposeList);
                        t.setPlanStartTime(getStartTimeByPrepose(task, maxPreposeEndTime));
                        t.setPlanEndTime(getEndTimeByStartTimeAndWorkTime(t));
                        changePostposes(t, changeFlowTaskList, flowTaskPreposeList);
                    }
                }
            }
        }
    }

    /**
     * 获取其前置最大的结束时间
     *
     * @param postpose
     * @param flowTaskList
     * @return
     * @see
     */
    private Date getPreposeMaxEndTime(PreposePlanDto postpose, List<PlanDto> flowTaskList) {
        Date endTime = null;
        PlanDto p = new PlanDto();
        p.setId(postpose.getPlanId());
        List<PreposePlanDto> preposes = preposePlanService.getPreposePlansByPlanId(p);
        for (PreposePlanDto prepose : preposes) {
            for (PlanDto task : flowTaskList) {
                if (task.getId().equals(prepose.getPreposePlanId())) {
                    if (endTime == null) {
                        endTime = (Date)task.getPlanEndTime().clone();
                    }
                    else {
                        if (task.getPlanEndTime().getTime() > endTime.getTime()) {
                            endTime = (Date)task.getPlanEndTime().clone();
                        }
                    }
                }
            }
        }
        return endTime;
    }

    /**
     * 获取其前置最大的结束时间
     *
     * @param postpose
     * @param flowTaskList
     * @return
     * @see
     */
    private Date getPreposeMaxEndTime(FlowTaskPreposeVo postpose, List<FlowTaskVo> flowTaskList,
                                      List<FlowTaskPreposeVo> flowTaskPreposeList) {
        Date endTime = null;
        List<FlowTaskPreposeVo> preposes = new ArrayList<FlowTaskPreposeVo>();
        for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
            if (postpose.getFlowTaskId().equals(prepose.getFlowTaskId())) {
                preposes.add(prepose);
            }
        }
        for (FlowTaskPreposeVo prepose : preposes) {
            for (FlowTaskVo task : flowTaskList) {
                if (task.getId().equals(prepose.getPreposeId())) {
                    if (endTime == null) {
                        endTime = (Date)task.getPlanEndTime().clone();
                    }
                    else {
                        if (task.getPlanEndTime().getTime() > endTime.getTime()) {
                            endTime = (Date)task.getPlanEndTime().clone();
                        }
                    }
                }
            }
        }
        return endTime;
    }

    /**
     * 获取后置计划的开始时间
     * @param flowTask
     * @param date
     */
    private Date getStartTimeByPrepose(PlanDto flowTask, Date date) {
        Date startTime = null;
        Project project = projectService.getProjectEntity(flowTask.getProjectId());
        if (project != null && StringUtils.isNotEmpty(project.getProjectTimeType())) {
            if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                startTime = DateUtil.nextWorkDay(date, 1);
            }
            else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",date);
                params.put("days",1);
                startTime = calendarService.getNextWorkingDay(appKey,params);
            }
            else {
                startTime = TimeUtil.getExtraDate(date, 1);
            }
        }
        else {
            startTime = TimeUtil.getExtraDate(date, 1);
        }
        return startTime;
    }

    /**
     * 获取后置计划的开始时间
     *
     * @param flowTask
     * @param date
     * @return
     */
    private Date getStartTimeByPrepose(FlowTaskVo flowTask, Date date) {
        Date startTime = null;
        Project project = projectService.getProjectEntity(flowTask.getProjectId());
        if (project != null && StringUtils.isNotEmpty(project.getProjectTimeType())) {
            if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                startTime = DateUtil.nextWorkDay(date, 1);
            }
            else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",date);
                params.put("days",1);
                startTime = calendarService.getNextWorkingDay(appKey,params);
            }
            else {
                startTime = TimeUtil.getExtraDate(date, 1);
            }
        }
        else {
            startTime = TimeUtil.getExtraDate(date, 1);
        }
        return startTime;
    }

    /**
     * 获取计划的结束时间
     *
     * @param plan
     * @return
     */
    private Date getEndTimeByStartTimeAndWorkTime(PlanDto plan) {
        Date planEndTime = null;
        Project project = projectService.getProjectEntity(plan.getProjectId());
        if (project != null) {
            if (StringUtils.isNotEmpty(project.getProjectTimeType())) {
                plan.setStatus(project.getProjectTimeType());
            }
            else {
                plan.setStatus(ProjectConstants.NATURALDAY);
            }
            Date date;
            if (plan.getPlanStartTime() == null) {
                date = (Date)project.getStartProjectTime().clone();
            }
            else {
                date = (Date)plan.getPlanStartTime().clone();
            }
            if (ProjectConstants.WORKDAY.equals(plan.getStatus())) {
                planEndTime = DateUtil.nextWorkDay(date,
                    Integer.valueOf(plan.getWorkTime()) - 1);
            }
            else if (ProjectConstants.COMPANYDAY.equals(plan.getStatus())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",date);
                params.put("days",Integer.valueOf(plan.getWorkTime()) - 1);

                planEndTime = calendarService.getNextWorkingDay(appKey,params);
            }
            else {
                planEndTime = DateUtil.nextDay(date, Integer.valueOf(plan.getWorkTime()) - 1);
            }
        }
        return planEndTime;
    }

    /**
     * 获取计划的结束时间
     *
     * @param flowTask
     * @return
     */
    private Date getEndTimeByStartTimeAndWorkTime(FlowTaskVo flowTask) {
        Date planEndTime = null;
        Project project = projectService.getProjectEntity(flowTask.getProjectId());
        if (project != null) {
            if (StringUtils.isNotEmpty(project.getProjectTimeType())) {
                flowTask.setStatus(project.getProjectTimeType());
            }
            else {
                flowTask.setStatus(ProjectConstants.NATURALDAY);
            }
            Date date = (Date)flowTask.getPlanStartTime().clone();
            if (ProjectConstants.WORKDAY.equals(flowTask.getStatus())) {
                planEndTime = DateUtil.nextWorkDay(date,
                    Integer.valueOf(flowTask.getWorkTime()) - 1);
            }
            else if (ProjectConstants.COMPANYDAY.equals(flowTask.getStatus())) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("startDate",date);
                params.put("days",Integer.valueOf(flowTask.getWorkTime()) - 1);
                planEndTime = calendarService.getNextWorkingDay(appKey,params);
            }
            else {
                planEndTime = DateUtil.nextDay(date, Integer.valueOf(flowTask.getWorkTime()) - 1);
            }
        }
        return planEndTime;
    }

    /**
     * 批量修改基本信息保存
     *
     * @return
     */
    @RequestMapping(params = "doBatchSaveBasicInfo")
    @ResponseBody
    private AjaxJson doBatchSaveBasicInfo(HttpServletRequest req, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        String[] ids = req.getParameter("ids").split(",");
        String[] owners = req.getParameter("owners").split(",");
        String[] planLevels = req.getParameter("planLevels").split(",");
        String[] planStartTimes = req.getParameter("planStartTimes").split(",");
        String[] planEndTimes = req.getParameter("planEndTimes").split(",");
        String[] planNames = req.getParameter("planNames").split(",");
        String[] fromTemplates = req.getParameter("fromTemplates").split(",");
        String[] workTimes = req.getParameter("workTimes").split(",");
        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        Map<String, String> childNameMap = new HashMap<String, String>();
        for (int i = 0; i < planNames.length; i++ ) {
            if (StringUtils.isEmpty(childNameMap.get(planNames[i]))) {
                childNameMap.put(planNames[i], planNames[i]);
            }
            else {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameExistCheck"));
                return j;
            }
        }

        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();
//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        boolean mustUseStandard = false;
        if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
            || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            mustUseStandard = true;
        }
        if (mustUseStandard) {
            NameStandardDto nameStandard = new NameStandardDto();
            nameStandard.setStopFlag("启用");
            List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
            Map<String, String> nameStdMap = new HashMap<String, String>();
            for (NameStandardDto std : list) {
                nameStdMap.put(std.getName(), std.getName());
            }
            if (CommonUtil.isEmpty(nameStdMap)) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                return j;
            }
            for (int i = 0; i < ids.length; i++ ) {
                if (StringUtils.isEmpty(nameStdMap.get(planNames[i]))) {
                    j.setSuccess(false);
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.nameNotStandard"));
                    return j;
                }
            }
        }

        for (int i = 0; i < ids.length; i++ ) {
            if (parent.getPlanName().equals(planNames[i])) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtasknameParentnameSameCheck"));
                return j;
            }
            if (!checkNames(planNames[i]) && !"true".equals(fromTemplates[i])) {
                j.setSuccess(false);
                Object[] arguments1 = new String[] {planNames[i]};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.taskNameInexistenceNeedEdit", arguments1));
                return j;
            }
            PlanDto task = planService.getPlanEntity(ids[i]);
            task.setPlanStartTime(DateUtil.stringtoDate(planStartTimes[i],
                DateUtil.LONG_DATE_FORMAT));
            task.setPlanEndTime(DateUtil.stringtoDate(planEndTimes[i], DateUtil.LONG_DATE_FORMAT));
            task.setWorkTime(workTimes[i]);
            Object[] arguments2 = new String[] {task.getPlanName(),
                sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanStartTime())};
            if (task.getPlanStartTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentStartTimeCheck",
                    arguments2));
                return j;
            }
            if (task.getPlanStartTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentEndTimeCheck",
                    arguments2));
                return j;
            }
            if (task.getPlanEndTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(), sdf1.format(task.getPlanEndTime()),
                    sdf1.format(parent.getPlanStartTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentStartTimeCheck",
                    arguments2));
                return j;
            }
            if (task.getPlanEndTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(), sdf1.format(task.getPlanEndTime()),
                    sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentEndTimeCheck",
                    arguments2));
                return j;
            }
        }
        Map<String, String> cellWorkTimeMap = new HashMap<String, String>();
        for (int i = 0; i < ids.length; i++ ) {
            PlanDto task = planService.getPlanEntity(ids[i]);
            if (StringUtil.isNotEmpty(owners[i])) {
                task.setOwner(owners[i].trim());
            }
            if (StringUtil.isNotEmpty(planLevels[i])) {
                task.setPlanLevel(planLevels[i].trim());
            }
            boolean nameChange = false;
            if (StringUtil.isNotEmpty(planNames[i])) {
                // 20160928 如果计划名称变更，则先删除该计划相关的输入及输入对应的输入
                if (!task.getPlanName().equals(planNames[i])) {
                    nameChange = true;
                    deliverablesInfoService.deleteDeliverablesByPlanId(task.getId());
                }
                task.setPlanName(planNames[i].trim());
            }
            task.setStoreyNo(i + 1);
            taskFlowResolveService.doBatchSaveBasicInfo(fromTemplates[i], task, planStartTimes[i],
                planEndTimes[i], workTimes[i], nameChange);
            if (StringUtils.isEmpty(parentPlanId)) {
                parentPlanId = task.getParentPlanId();
            }
            String label = task.getPlanName() + "," + task.getWorkTime() + "天";
            cellWorkTimeMap.put(task.getCellId(), label);
        }
        String flowResolveXml = parent.getFlowResolveXml();
        flowResolveXml = refreshFlowResolveXml(flowResolveXml,
            cellWorkTimeMap);
        parent.setFlowResolveXml(flowResolveXml);
        CommonInitUtil.initGLVDataForUpdate(parent);
        planService.updatePlan(parent);
        j.setMsg(message);
        j.setSuccess(true);
        return j;
    }


    /**
     * 将flowResolveXml中的参考工期改为实际工期
     *
     * @param flowResolveXml
     * @param cellWorkTimeMap
     * @return
     * @return
     */
    private String refreshFlowResolveXml(String flowResolveXml, Map<String, String> cellWorkTimeMap) {
        try {
            SAXReader reader = new SAXReader();
            InputStream in = new ByteArrayInputStream(flowResolveXml.getBytes("UTF-8"));
            Document document = reader.read(in);
            Element root = document.getRootElement();
            List<Element> nodeRoots = root.elements("root");
            if (nodeRoots != null && nodeRoots.size() >= 1) {
                Element nodeRoot = nodeRoots.get(0);
                List<Element> nodes = nodeRoot.elements("Task");
                for (Iterator it = nodes.iterator(); it.hasNext();) {
                    Element elm = (Element)it.next();
                    String cellId = elm.attributeValue("id");
                    if (StringUtil.isNotEmpty(cellWorkTimeMap.get(cellId))) {
                        String cellNameInfo = cellWorkTimeMap.get(cellId);
                        elm.setAttributeValue("label", cellNameInfo);
                    }
                }

            }
            flowResolveXml = document.asXML();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flowResolveXml;
    }


    /**
     * 批量修改基本信息保存
     *
     * @return
     */
    @RequestMapping(params = "doBatchSaveBasicInfoForChange")
    @ResponseBody
    private AjaxJson doBatchSaveBasicInfoForChange(HttpServletRequest req,
                                                   HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.savesuccess");
        String[] ids = req.getParameter("ids").split(",");
        String[] planNames = req.getParameter("planNames").split(",");
        String[] fromTemplates = req.getParameter("fromTemplates").split(",");
        String[] owners = req.getParameter("owners").split(",");
        String[] planLevels = req.getParameter("planLevels").split(",");
        String[] planStartTimes = req.getParameter("planStartTimes").split(",");
        String[] planEndTimes = req.getParameter("planEndTimes").split(",");
        String[] workTimes = req.getParameter("workTimes").split(",");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = (List<ChangeFlowTaskCellConnectVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_CONNECT_LIST_KEY + parentPlanId);
        List<FlowTaskVo> changeFlowTaskList1 = new ArrayList<FlowTaskVo>();
        for (int i = 0; i < ids.length; i++ ) {
            FlowTaskVo task = new FlowTaskVo();

            if (!checkNames(planNames[i]) && "false".equals(fromTemplates[i])) {
                aj.setSuccess(false);
                Object[] arguments1 = new String[] {planNames[i]};
                aj.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.taskNameInexistenceNeedEdit", arguments1));
                return aj;
            }
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (flowTask.getId().equals(ids[i])) {
                    boolean nameChange = false;
                    if (!flowTask.getPlanName().equals(planNames[i])) {
                        nameChange = true;
                    }
                    if (StringUtil.isNotEmpty(planNames[i])) {
                        task.setPlanName(planNames[i].trim());
                    }
                    task = flowTask;
                    if ("false".equals(fromTemplates[i]) && nameChange) {
                         FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
                         String switchStr = switchStrJson.getObj().toString();
//                        String switchStr = "";
//                        try {
//                            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//                            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//                            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//                            switchStr = (String)ajaxJson.getObj();
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
                        NameStandardDto condition2 = new NameStandardDto();
                        condition2.setStopFlag("启用");
                        condition2.setName(task.getPlanName());
                        List<NameStandardDto> nameStandardList2 = nameStandardService.searchNameStandards(condition2);
                        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)
                            || NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)
                            || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
                            if (nameStandardList2.size() > 0) {
                                for (NameStandardDto n : nameStandardList2) {
                                    if ("启用".equals(n.getStopFlag())) {
                                        NameStandardDeliveryRelationDto relation = new NameStandardDeliveryRelationDto();
                                        relation.setNameStandardId(n.getId());
                                        List<NameStandardDeliveryRelationDto> list = nameStandardDeliveryRelationService.searchForPage(
                                            relation, 1, 10);

                                        doBatchSaveBasicInfoForChange1(
                                            changeFlowTaskList, changeFlowTaskConnectList,
                                            flowTask);
                                        if (list.size() > 0) {
                                            flowTask.getOutputList().clear();
                                            for (NameStandardDeliveryRelationDto r : list) {
                                                if(!CommonUtil.isEmpty(r.getDeliveryStandardId())) {
                                                    r.setDeliveryStandard(deliveryStandardService.getDeliveryStandardEntity(r.getDeliveryStandardId()));
                                                }
                                                if (r.getDeliveryStandardId() != null) {
                                                    FlowTaskDeliverablesInfoVo deliverablesInfoTemp = new FlowTaskDeliverablesInfoVo();
                                                    // deliverablesInfoService.initBusinessObject(deliverablesInfoTemp);
                                                    // 获取FlowTaskDeliverablesInfo的BusinessObject
//                                                    RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//                                                    RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
                                                    FeignJson ajaxJson = rdFlowTaskFlowResolveService.getFlowTaskDeliverablesInfoInitBusinessObject();
                                                    String mapList = (String)ajaxJson.getObj();
                                                    if (!CommonUtil.isEmpty(mapList)
                                                        && !CommonUtil.isEmpty(mapList.split(",")[0])
                                                        && !CommonUtil.isEmpty(mapList.split(",")[1])) {
                                                        deliverablesInfoTemp.setAvaliable("1");
                                                        deliverablesInfoTemp.setSecurityLevel((short)1);
                                                        deliverablesInfoTemp.setBizCurrent(mapList.split(",")[0].toString());
                                                        deliverablesInfoTemp.setPolicy_id(mapList.split(",")[1].toString());
                                                        deliverablesInfoTemp.setBizId(UUID.randomUUID().toString());
                                                    }

                                                    deliverablesInfoTemp.setId(null);
                                                    deliverablesInfoTemp.setName(r.getDeliveryStandard().getName());
                                                    deliverablesInfoTemp.setUseObjectId(ids[i]);
                                                    deliverablesInfoTemp.setUseObjectType("PLAN");
                                                    deliverablesInfoTemp.setOrigin(PlanConstants.DELIVERABLES_ORIGIN_NAMESTANDARD);
                                                    flowTask.getOutputList().add(
                                                        deliverablesInfoTemp);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if ((flowTask.getPlanEndTime().getTime() != task.getPlanEndTime().getTime())
                    || (flowTask.getPlanEndTime().getTime() != task.getPlanStartTime().getTime())) {
                    FlowTaskResourceLinkInfoVo flowTaskRs = new FlowTaskResourceLinkInfoVo();
                    flowTaskRs.setUseObjectId(task.getId());// 关连的外键id
                    flowTaskRs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);// 关连的外键id
                    List<FlowTaskResourceLinkInfoVo> resourceList = task.getResourceLinkList();// 一个任务对应的资源列表
                    if (!CommonUtil.isEmpty(resourceList)) {
                        for (FlowTaskResourceLinkInfoVo ftInfo : resourceList) {
                            ftInfo.setStartTime(task.getPlanStartTime());
                            ftInfo.setEndTime(task.getPlanEndTime());
                        }
                    }
                }
            }

            changeFlowTaskList = planService.taskSort(changeFlowTaskList);

            if (StringUtil.isNotEmpty(owners[i])) {
                task.setOwner(owners[i].trim());
            }
            if (StringUtil.isNotEmpty(planLevels[i])) {
                task.setPlanLevel(planLevels[i].trim());
            }
            task.setPlanStartTime(DateUtil.stringtoDate(planStartTimes[i],
                DateUtil.LONG_DATE_FORMAT));
            task.setPlanEndTime(DateUtil.stringtoDate(planEndTimes[i], DateUtil.LONG_DATE_FORMAT));
            task.setWorkTime(workTimes[i]);
            task.setStoreyNo(i + 1);
            changeFlowTaskList1.add(task);

        }
        req.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList1);
        aj.setMsg(message);
        aj.setSuccess(true);
        return aj;
    }

    private void doBatchSaveBasicInfoForChange1(List<FlowTaskVo> changeFlowTaskList,
                                              List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList,
                                              FlowTaskVo flowTask) {
        for (ChangeFlowTaskCellConnectVo c : changeFlowTaskConnectList) {
            if (c.getCellId().equals(flowTask.getCellId())) {
                deleteLinkInputForChange(c.getCellId(), c.getTargetId(), changeFlowTaskList);
            }
        }
    }


    /**获取父计划的状态
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "getParentBizCurrent")
    @ResponseBody
    public AjaxJson getParentBizCurrent(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        j.setObj(parent.getBizCurrent());
        return j;
    }

    /**
     * 判断流程任务是否可以提交
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkFlowTask")
    @ResponseBody
    public AjaxJson checkFlowTask(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        //父节点是拟制中的时候,校验父节点的相关信息,以便于父子节点满足条件一起提交
        if("EDITING".equals(parent.getBizCurrent())) {
            if (StringUtils.isEmpty(parent.getOwner())) {
                j.setSuccess(false);
                Object[] arguments = new String[] {parent.getPlanName()};
                j.setMsg("父"+I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskOwnerInexistenceCannotApprove",
                    arguments));
                return j;
            }
            if(("WBS计划".equals(parent.getPlanType()) || CommonUtil.isEmpty(parent.getPlanType())) && CommonUtil.isEmpty(parent.getParentPlanId())) {
            }else {
                j.setSuccess(false);
                j.setMsg("此研发流程父计划不是一级计划，不可提交");
                return j;
            }

            if(CommonUtil.isEmpty(parent.getPlanStartTime())) {
                j.setSuccess(false);
                j.setMsg("父计划的开始时间不能为空");
                return j;
            }

            if(CommonUtil.isEmpty(parent.getPlanEndTime())) {
                j.setSuccess(false);
                j.setMsg("父计划的结束时间不能为空");
                return j;
            }

            FeignJson isCheckInput = planService.checkOriginIsNullBeforeSub(parentPlanId,"PLAN");
            if(!isCheckInput.isSuccess()) {
                j.setSuccess(false);
                j.setMsg("父计划存在没有挂载的输入，不可发布");
                return j;
            }

            Project project = projectService.getProjectEntity(parent.getProjectId());
            if(!CommonUtil.isEmpty(project) && "EDITING".equals(project.getBizCurrent())) {
                j.setSuccess(false);
                j.setMsg("拟制中的项目不能发布流程");
                return j;
            }

            AjaxJson curInfo = checkResourceLinkInfoByPlanDto(parent);
            if(curInfo.isSuccess()) {
            }else {
                j.setSuccess(false);
                j.setObj(curInfo.getObj());
                return j;
            }
        }

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        plan.setAvaliable("1");;
        List<PlanDto> flowTaskList = planService.queryPlanList(plan, 1, 10, false);
        //获取计划id的Map集合：
        Map<String,String> childIdMap = new HashMap<String,String>();
        for (PlanDto taskPlan : flowTaskList) {
            childIdMap.put(taskPlan.getId(), taskPlan.getId());
        }

        if (!childrenPlanCoverParent(parent, flowTaskList)) {
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtaskMissParentDeliverablesCannotApprove"));
            return j;
        }
        PlanDto task = new PlanDto();
        for (PlanDto taskPlan : flowTaskList) {
            try {
                BeanUtils.copyProperties(task,taskPlan);
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            task = (PlanDto)taskPlan.clone();
            task.setId(taskPlan.getId());
            if (StringUtils.isNotEmpty(task.getFlowStatus())
                && !PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(task.getFlowStatus())
                && !PlanConstants.PLAN_FLOWSTATUS_BACK.equals(task.getIsAssignSingleBack())) {
                String flowStatusCHI = "";
                if (PlanConstants.PLAN_FLOWSTATUS_ORDERED.equals(task.getFlowStatus())) {
                    flowStatusCHI = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.orderedProcess");
                }
                else if (PlanConstants.PLAN_FLOWSTATUS_CHANGE.equals(task.getFlowStatus())) {
                    flowStatusCHI = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.changeProcess");
                }
                else if (PlanConstants.PLAN_FLOWSTATUS_FEEDBACKING.equals(task.getFlowStatus())) {
                    flowStatusCHI = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.feedbackProcess");
                }
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(), flowStatusCHI};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.existProcessCannotSubmit", arguments));
                return j;
            }
            if (StringUtils.isEmpty(task.getOwner())) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName()};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskOwnerInexistenceCannotApprove",
                    arguments));
                return j;
            }
            Object[] arguments1 = new String[] {task.getPlanName(),
                sdf1.format(task.getPlanStartTime()), sdf1.format(task.getPlanEndTime())};
            if (task.getPlanStartTime().getTime() > task.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeEndTimeCheck",
                    arguments1));
                return j;
            }
            Object[] arguments2 = new String[] {task.getPlanName(),
                sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanStartTime())};
            if (task.getPlanStartTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentStartTimeCheck",
                    arguments2));
                return j;
            }
            if (task.getPlanStartTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentEndTimeCheck",
                    arguments2));
            }

            if (task.getPlanEndTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(), sdf1.format(task.getPlanEndTime()),
                    sdf1.format(parent.getPlanStartTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentStartTimeCheck",
                    arguments2));
                return j;
            }
            if (task.getPlanEndTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                arguments2 = new String[] {task.getPlanName(), sdf1.format(task.getPlanEndTime()),
                    sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentEndTimeCheck",
                    arguments2));
                return j;
            }

            AjaxJson curInfo = checkResourceLinkInfoByPlanDto(task);
            if(curInfo.isSuccess()) {
            }else {
                j.setSuccess(false);
                j.setObj(curInfo.getObj());
                return j;
            }

            //内部前置计划的结束时间必须大于计划的开始时间：bug24529
            List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(task);
            for (PreposePlanDto preposePlan : preposePlanList) {
                if (!CommonUtil.isEmpty(preposePlan.getPreposePlanId()) && !CommonUtil.isEmpty(childIdMap.get(preposePlan.getPreposePlanId()))) {
                    PlanDto prepose = planService.getPlanEntity(preposePlan.getPreposePlanId());
                    if(prepose.getPlanEndTime().getTime() < task.getPlanStartTime().getTime()) {
                    }else {
                        arguments2 = new String[] {task.getPlanName(), sdf1.format(task.getPlanStartTime()),prepose.getPlanName(),
                            sdf1.format(prepose.getPlanEndTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.planStartTimePreposePlanEndTimeCheck",
                            arguments2));
                        j.setSuccess(false);
                        return j;
                    }
                }
            }

        }
        return j;
    }

    public AjaxJson checkResourceLinkInfoByPlanDto(PlanDto task) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
        resourceLinkInfo.setUseObjectId(task.getId());
        List<ResourceLinkInfoDto> resourceList = resourceLinkInfoService.queryResourceList(
            resourceLinkInfo, 1, 10, false);
        if (!CommonUtil.isEmpty(resourceList)) {
            for (int i = 0; i < resourceList.size(); i++ ) {
                if (StringUtils.isEmpty(resourceList.get(i).getUseRate())) {
                    if (resourceList.get(i).getResourceInfo() != null) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {
                            task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.userate")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                            arguments));
                    }
                    else {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.userate")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                            arguments));
                    }
                    return j;
                }
                if (resourceList.get(i).getStartTime() == null) {
                    if (resourceList.get(i).getResourceInfo() != null) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {
                            task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.starttime")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                            arguments));
                    }
                    else {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.starttime")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                            arguments));
                    }
                    return j;
                }
                if (resourceList.get(i).getEndTime() == null) {
                    if (resourceList.get(i).getResourceInfo() != null) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {
                            task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.endtime")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                            arguments));
                    }
                    else {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.endtime")};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                            arguments));
                    }
                    return j;
                }

                if (resourceList.get(i).getStartTime().getTime() > resourceList.get(i).getEndTime().getTime()) {
                    j.setSuccess(false);

                    if (resourceList.get(i).getResourceInfo() != null) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            sdf1.format(resourceList.get(i).getStartTime()),
                            sdf1.format(resourceList.get(i).getEndTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceStartTimeEndTimeCheck1",
                            arguments));
                    }
                    else {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {
                            sdf1.format(resourceList.get(i).getStartTime()),
                            sdf1.format(resourceList.get(i).getEndTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceStartTimeEndTimeCheck",
                            arguments));
                    }
                    return j;
                }
                if (resourceList.get(i).getStartTime().getTime() < task.getPlanStartTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        resourceList.get(i).getResourceInfo().getName(),
                        sdf1.format(resourceList.get(i).getStartTime()),
                        sdf1.format(task.getPlanStartTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.resourceStartTimeTaskStartTimeCheck",
                        arguments));
                    return j;
                }
                if (resourceList.get(i).getStartTime().getTime() > task.getPlanEndTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        resourceList.get(i).getResourceInfo().getName(),
                        sdf1.format(resourceList.get(i).getStartTime()),
                        sdf1.format(task.getPlanEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.resourceStartTimeTaskEndTimeCheck",
                        arguments));
                    return j;
                }

                if (resourceList.get(i).getEndTime().getTime() < task.getPlanStartTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        resourceList.get(i).getResourceInfo().getName(),
                        sdf1.format(resourceList.get(i).getEndTime()),
                        sdf1.format(task.getPlanStartTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.resourceEndTimeTaskStartTimeCheck",
                        arguments));
                    return j;
                }
                if (resourceList.get(i).getEndTime().getTime() > task.getPlanEndTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        resourceList.get(i).getResourceInfo().getName(),
                        sdf1.format(resourceList.get(i).getEndTime()),
                        sdf1.format(task.getPlanEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.resourceEndTimeTaskEndTimeCheck",
                        arguments));
                    return j;
                }

            }
        }
        return j;
    }


    /**
     * 判断流程任务是否超过上级结束时间
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkFlowTaskTime")
    @ResponseBody
    public AjaxJson checkFlowTaskTime(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> flowTaskList = planService.queryPlanList(plan, 1, 10, false);

        for (PlanDto task : flowTaskList) {
            if (task.getPlanStartTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanStartTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentStartTimeCheck",
                    arguments));
                return j;
            }
            if (task.getPlanStartTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentEndTimeCheck",
                    arguments));
                return j;
            }

            if (task.getPlanEndTime().getTime() < parent.getPlanStartTime().getTime()) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanStartTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentStartTimeCheck",
                    arguments));
                return j;
            }
            if (task.getPlanEndTime().getTime() > parent.getPlanEndTime().getTime()) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName(),
                    sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanEndTime())};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentEndTimeCheck",
                    arguments));
                return j;
            }
        }
        return j;
    }

    /**
     * 判断流程任务是否可以提交
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkFlowTaskForChange")
    @ResponseBody
    public AjaxJson checkFlowTaskForChange(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto parent = planService.getPlanEntity(parentPlanId);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)req.getSession().getAttribute(
            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);

        // if (!childrenPlanCoverParentForChange(parent, changeFlowTaskList)) {
        // j.setSuccess(false);
        // j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowtaskMissParentDeliverablesCannotApprove"));
        // return j;
        // }
        for (FlowTaskVo task : changeFlowTaskList) {
            if (StringUtils.isEmpty(task.getOwner())) {
                j.setSuccess(false);
                Object[] arguments = new String[] {task.getPlanName()};
                j.setMsg(I18nUtil.getValue(
                    "com.glaway.ids.pm.rdtask.flowResolve.flowtaskOwnerInexistenceCannotApprove",
                    arguments));
                return j;
            }
            if (task.getPlanStartTime() != null && task.getPlanEndTime() != null) {
                if (task.getPlanStartTime().getTime() > task.getPlanEndTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        sdf1.format(task.getPlanStartTime()), sdf1.format(task.getPlanEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeEndTimeCheck",
                        arguments));
                    return j;
                }
                if (task.getPlanStartTime().getTime() < parent.getPlanStartTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        sdf1.format(task.getPlanStartTime()),
                        sdf1.format(parent.getPlanStartTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentStartTimeCheck",
                        arguments));
                    return j;
                }
                if (task.getPlanStartTime().getTime() > parent.getPlanEndTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        sdf1.format(task.getPlanStartTime()), sdf1.format(parent.getPlanEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.flowtaskStartTimeParentEndTimeCheck",
                        arguments));
                    return j;
                }

                if (task.getPlanEndTime().getTime() < parent.getPlanStartTime().getTime()) {
                    j.setSuccess(false);
                    Object[] arguments = new String[] {task.getPlanName(),
                        sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanStartTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentStartTimeCheck",
                        arguments));
                    return j;
                }
                if (task.getPlanEndTime().getTime() > parent.getPlanEndTime().getTime()) {
                    j.setSuccess(false);

                    Object[] arguments = new String[] {task.getPlanName(),
                        sdf1.format(task.getPlanEndTime()), sdf1.format(parent.getPlanEndTime())};
                    j.setMsg(I18nUtil.getValue(
                        "com.glaway.ids.pm.rdtask.flowResolve.flowtaskEndTimeParentEndTimeCheck",
                        arguments));
                    return j;
                }
            }

            List<FlowTaskResourceLinkInfoVo> resourceList = task.getResourceLinkList();
            if (resourceList != null && resourceList.size() > 0) {
                for (int i = 0; i < resourceList.size(); i++ ) {
                    if (StringUtils.isEmpty(resourceList.get(i).getUseRate())) {
                        if (resourceList.get(i).getResourceInfo() != null) {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {
                                task.getPlanName(),
                                resourceList.get(i).getResourceInfo().getName(),
                                I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.userate")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                                arguments));
                        }
                        else {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.userate")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                                arguments));
                        }
                        return j;
                    }
                    if (resourceList.get(i).getStartTime() == null) {
                        if (resourceList.get(i).getResourceInfo() != null) {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {
                                task.getPlanName(),
                                resourceList.get(i).getResourceInfo().getName(),
                                I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.starttime")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                                arguments));
                        }
                        else {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.starttime")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                                arguments));
                        }
                        return j;
                    }
                    if (resourceList.get(i).getEndTime() == null) {
                        if (resourceList.get(i).getResourceInfo() != null) {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {
                                task.getPlanName(),
                                resourceList.get(i).getResourceInfo().getName(),
                                I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.endtime")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck1",
                                arguments));
                        }
                        else {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.resource.endtime")};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceEmptyCheck",
                                arguments));
                        }
                        return j;
                    }

                    if (resourceList.get(i).getStartTime().getTime() > resourceList.get(i).getEndTime().getTime()) {
                        j.setSuccess(false);

                        if (resourceList.get(i).getResourceInfo() != null) {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {task.getPlanName(),
                                resourceList.get(i).getResourceInfo().getName(),
                                sdf1.format(resourceList.get(i).getStartTime()),
                                sdf1.format(resourceList.get(i).getEndTime())};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceStartTimeEndTimeCheck1",
                                arguments));
                        }
                        else {
                            j.setSuccess(false);
                            Object[] arguments = new String[] {
                                sdf1.format(resourceList.get(i).getStartTime()),
                                sdf1.format(resourceList.get(i).getEndTime())};
                            j.setMsg(I18nUtil.getValue(
                                "com.glaway.ids.pm.rdtask.flowResolve.flowtaskResourceStartTimeEndTimeCheck",
                                arguments));
                        }
                        return j;
                    }
                    if (resourceList.get(i).getStartTime().getTime() < task.getPlanStartTime().getTime()) {
                        j.setSuccess(false);
                        ResourceDto resourceInfo = resourceService.getEntity(resourceList.get(i).getResourceId());
                        Object[] arguments = new String[] {task.getPlanName(),
                            resourceInfo.getName(),
                            sdf1.format(resourceList.get(i).getStartTime()),
                            sdf1.format(task.getPlanStartTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.resourceStartTimeTaskStartTimeCheck",
                            arguments));
                        return j;
                    }
                    if (resourceList.get(i).getStartTime().getTime() > task.getPlanEndTime().getTime()) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            sdf1.format(resourceList.get(i).getStartTime()),
                            sdf1.format(task.getPlanEndTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.resourceStartTimeTaskEndTimeCheck",
                            arguments));
                        return j;
                    }

                    if (resourceList.get(i).getEndTime().getTime() < task.getPlanStartTime().getTime()) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            sdf1.format(resourceList.get(i).getEndTime()),
                            sdf1.format(task.getPlanStartTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.resourceEndTimeTaskStartTimeCheck",
                            arguments));
                        return j;
                    }
                    if (resourceList.get(i).getEndTime().getTime() > task.getPlanEndTime().getTime()) {
                        j.setSuccess(false);
                        Object[] arguments = new String[] {task.getPlanName(),
                            resourceList.get(i).getResourceInfo().getName(),
                            sdf1.format(resourceList.get(i).getEndTime()),
                            sdf1.format(task.getPlanEndTime())};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.resourceEndTimeTaskEndTimeCheck",
                            arguments));
                        return j;
                    }

                }
            }

            List<FlowTaskPreposeVo> preposePlanList = new ArrayList<FlowTaskPreposeVo>();
            for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
                if (prepose.getFlowTaskId().equals(task.getId())) {
                    preposePlanList.add(prepose);
                }
            }
            for (FlowTaskPreposeVo preposePlan : preposePlanList) {
                if (StringUtils.isNotEmpty(preposePlan.getPreposeId())) {
                    FlowTaskVo prepose = null;
                    for (FlowTaskVo flowtask : changeFlowTaskList) {
                        if (preposePlan.getPreposeId().equals(flowtask.getId())) {
                            prepose = flowtask;
                            break;
                        }
                    }
                }
            }
        }
        StringBuffer resStrBuffer = new StringBuffer();
//        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//        if (!CommonUtil.isEmpty(outwardExtensionList)) {
//            for (OutwardExtension out : outwardExtensionList) {
//                if (!CommonUtil.isEmpty(resStrBuffer.toString())) {
//                    resStrBuffer.append("," + out.getOptionValue());
//                }
//                else {
//                    resStrBuffer.append(out.getOptionValue());
//                }
//            }
//        }
        j.setObj(resStrBuffer.toString());
        return j;
    }

    /**
     * 判断子计划的输出是否覆盖父计划
     *
     * @param children
     * @return
     * @see
     */
    private boolean childrenPlanCoverParent(PlanDto parent, List<PlanDto> children) {
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(parent.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> parentDeliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);
        if (!CommonUtil.isEmpty(parentDeliverablesList)) {
            Map<String, String> deliverablesMap = new HashMap<String, String>();
            for (PlanDto child : children) {
                deliverablesInfo = new DeliverablesInfoDto();
                deliverablesInfo.setUseObjectId(child.getId());
                deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                List<DeliverablesInfoDto> childDeliverables = deliverablesInfoService.queryDeliverableList(
                    deliverablesInfo, 1, 10, false);
                for (DeliverablesInfoDto childDeli : childDeliverables) {
                    deliverablesMap.put(childDeli.getName(), childDeli.getName());
                }
            }
            for (DeliverablesInfoDto parentDeli : parentDeliverablesList) {
                // 判断父级交付物书否被全部覆盖
                if (StringUtils.isEmpty(deliverablesMap.get(parentDeli.getName()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断子计划的输出是否覆盖父计划
     *
     * @param children
     * @return
     * @see
     */
    private boolean childrenPlanCoverParentForChange(PlanDto parent, List<FlowTaskVo> children) {
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(parent.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        List<DeliverablesInfoDto> parentDeliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);
        if (!CommonUtil.isEmpty(parentDeliverablesList)) {
            Map<String, String> deliverablesMap = new HashMap<String, String>();
            for (FlowTaskVo child : children) {
                List<FlowTaskDeliverablesInfoVo> childDeliverables = child.getOutputList();
                for (FlowTaskDeliverablesInfoVo childDeli : childDeliverables) {
                    deliverablesMap.put(childDeli.getName(), childDeli.getName());
                }
            }
            for (DeliverablesInfoDto parentDeli : parentDeliverablesList) {
                // 判断父级交付物书否被全部覆盖
                if (StringUtils.isEmpty(deliverablesMap.get(parentDeli.getName()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 前置交付项指向后置页面跳转
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "taskCellQueryDeliver")
    public ModelAndView taskCellQueryDeliver(HttpServletRequest request) {
        String fromCellId = request.getParameter("fromCellId");
        request.setAttribute("fromCellId", fromCellId);
        String toCellId = request.getParameter("toCellId");
        request.setAttribute("toCellId", toCellId);
        String parentPlanId = request.getParameter("parentPlanId");
        request.setAttribute("parentPlanId", parentPlanId);
        ModelAndView mav = new ModelAndView("com/glaway/ids/pm/project/task/flowTaskLine-select");
        return mav;
    }

    /**
     * 前置交付项指向后置页面跳转
     *
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "changeTaskCellQueryDeliver")
    public ModelAndView changeTaskCellQueryDeliver(HttpServletRequest request) {
        String fromCellId = request.getParameter("fromCellId");
        request.setAttribute("fromCellId", fromCellId);
        String toCellId = request.getParameter("toCellId");
        request.setAttribute("toCellId", toCellId);
        String parentPlanId = request.getParameter("parentPlanId");
        request.setAttribute("parentPlanId", parentPlanId);
        ModelAndView mav = new ModelAndView(
            "com/glaway/ids/pm/project/task/changeFlowTaskLine-select");
        return mav;
    }

    /**
     * 关系查询，左右边窗口数据初始化查询
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "doCellRelationSearch")
    @ResponseBody
    private void doCellRelationSearch(HttpServletRequest req, HttpServletResponse response) {

        if ("left".equals(req.getParameter("side"))) {
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(req.getParameter("parentPlanId"));
            plan.setCellId(req.getParameter("fromCellId"));
            List<PlanDto> left = planService.queryPlanList(plan, 1, 10, false);
            if (left != null) {
                plan = left.get(0);
            }
            DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
            deliverablesInfo.setUseObjectId(plan.getId());
            deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);

            List<DeliverablesInfoDto> outputList = deliverablesInfoService.queryDeliverableList(
                deliverablesInfo, 1, 10, false);

            for (DeliverablesInfoDto output : outputList) {
                output.setChecked("unchecked");
            }

            String json = com.alibaba.fastjson.JSONArray.toJSONString(outputList);
            TagUtil.ajaxResponse(response, json);
        }
        else {
            PlanDto task = new PlanDto();
            PlanDto fromTask = new PlanDto();

            PlanDto plan = new PlanDto();
            plan.setParentPlanId(req.getParameter("parentPlanId"));
            plan.setCellId(req.getParameter("toCellId"));

            List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);

            if (!CommonUtil.isEmpty(taskList)) {
                task = taskList.get(0);
            }

            plan = new PlanDto();
            plan.setParentPlanId(req.getParameter("parentPlanId"));
            plan.setCellId(req.getParameter("fromCellId"));

            List<PlanDto> fromTaskList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(fromTaskList)) {
                fromTask = fromTaskList.get(0);
            }

            InputsDto inputs = new InputsDto();
            inputs.setUseObjectId(task.getId());
            inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            inputs.setOriginObjectId(fromTask.getId());

            List<InputsDto> inputList = inputsService.queryInputList(inputs, 1, 10, false);

            String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
            TagUtil.ajaxResponse(response, json);
        }
    }

    /**
     * 关系查询，左右边窗口数据初始化查询
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "doChangeCellRelationSearch")
    @ResponseBody
    private void doChangeCellRelationSearch(HttpServletRequest req, HttpServletResponse response) {
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

        if ("left".equals(req.getParameter("side"))) {
            List<FlowTaskDeliverablesInfoVo> outputList = new ArrayList<FlowTaskDeliverablesInfoVo>();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(req.getParameter("fromCellId"))) {
                    outputList = f.getOutputList();
                    for (FlowTaskDeliverablesInfoVo d : outputList) {
                        d.setUseObjectName(f.getPlanName());
                    }
                }
            }
            for (FlowTaskDeliverablesInfoVo output : outputList) {
                output.setChecked("unchecked");
            }
            String json = com.alibaba.fastjson.JSONArray.toJSONString(outputList);
            TagUtil.ajaxResponse(response, json);
        }
        else {
            FlowTaskVo from = new FlowTaskVo();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(req.getParameter("fromCellId"))) {
                    from = f;
                    break;
                }
            }
            List<FlowTaskInputsVo> inputList = new ArrayList<FlowTaskInputsVo>();
            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(req.getParameter("toCellId"))) {
                    List<FlowTaskInputsVo> list = f.getInputList();
                    for (FlowTaskInputsVo in : list) {
                        if (from.getId().equals(in.getOriginObjectId())) {
                            for (FlowTaskDeliverablesInfoVo out : from.getOutputList()) {
                                if (in.getOriginDeliverablesInfoId().equals(out.getId())) {
                                    inputList.add(in);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
            }
            String json = com.alibaba.fastjson.JSONArray.toJSONString(inputList);
            TagUtil.ajaxResponse(response, json);
        }
    }

    /**
     * 前后置关系改变
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "doCellRelationChange")
    @ResponseBody
    private AjaxJson doCellRelationChange(HttpServletRequest req, HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.operatesuccess");
        String fromCellId = req.getParameter("fromCellId");
        String parentPlanId = req.getParameter("parentPlanId");
        String toCellId = req.getParameter("toCellId");
        String[] names = req.getParameter("names").split(",");
        String[] ids = req.getParameter("ids").split(",");
        try {
            PlanDto plan = new PlanDto();
            PlanDto fromTask = new PlanDto();
            PlanDto toTask = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            plan.setCellId(fromCellId);
            List<PlanDto> fromList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(fromList)) {
                fromTask = fromList.get(0);
            }

            plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            plan.setCellId(toCellId);
            List<PlanDto> toList = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(toList)) {
                toTask = toList.get(0);
            }
            InputsDto inputs = new InputsDto();
            inputs.setUseObjectId(toTask.getId());
            inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            inputs.setOriginObjectId(fromTask.getId());

            List<InputsDto> inputList = inputsService.queryInputList(inputs, 1, 10, false);
            Map<String, String> inputMap = new HashMap<String, String>();
            Map<String, String> namesMap = new HashMap<String, String>();
            for (InputsDto input : inputList) {
                inputMap.put(input.getName(), input.getName());
            }
            List<InputsDto> addInputList = new ArrayList<InputsDto>();
            if (!CommonUtil.isEmpty(names)) {
                for (int i = 0; i < names.length; i++ ) {
                    if (StringUtils.isNotEmpty(names[i])) {
                        namesMap.put(names[i], names[i]);
                        if (StringUtils.isEmpty(inputMap.get(names[i]))) {
                            InputsDto in = new InputsDto();
                            in.setName(names[i]);
                            in.setUseObjectId(toTask.getId());
                            in.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                            in.setOrigin(fromTask.getPlanName());
                            in.setOriginDeliverablesInfoId(ids[i]);
                            in.setOriginObjectId(fromTask.getId());
                            addInputList.add(in);
                        }
                    }
                }
            }
            List<InputsDto> delInputList = new ArrayList<InputsDto>();
            for (InputsDto input : inputList) {
                if (fromTask.getId().equals(input.getOriginObjectId())
                    && StringUtils.isEmpty(namesMap.get(input.getName()))) {
                    delInputList.add(input);
                }
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("addInputList", addInputList);
            params.put("delInputList", delInputList);
            inputsService.updateInputsByAddAndDel(params);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.operatefailure");
            aj.setMsg(message);
            aj.setSuccess(false);
            return aj;
        }

        aj.setMsg(message);
        aj.setSuccess(true);
        return aj;
    }

    /**
     * 前后置关系改变
     *
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(params = "doChangeCellRelationChange")
    @ResponseBody
    private AjaxJson doChangeCellRelationChange(HttpServletRequest req,
                                                HttpServletResponse response) {
        AjaxJson aj = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.operatesuccess");
        String fromCellId = req.getParameter("fromCellId");
        String toCellId = req.getParameter("toCellId");
        String[] names = req.getParameter("names").split(",");
        String[] ids = req.getParameter("ids").split(",");
        String[] originObjectIds = req.getParameter("originObjectIds").split(",");
        String[] originObjectNames = req.getParameter("originObjectNames").split(",");
        String[] originDeliverablesInfoIds = req.getParameter("originDeliverablesInfoIds").split(
            ",");
        String[] originDeliverablesInfoNames = req.getParameter("originDeliverablesInfoNames").split(
            ",");
        String parentPlanId = req.getParameter("parentPlanId");
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        try {
            FlowTaskVo fromTask = new FlowTaskVo();
            FlowTaskVo toTask = new FlowTaskVo();

            for (FlowTaskVo f : changeFlowTaskList) {
                if (f.getCellId().equals(fromCellId)) {
                    fromTask = f;
                    break;
                }
            }
            List<FlowTaskInputsVo> inputList = new ArrayList<FlowTaskInputsVo>();
            for (FlowTaskVo t : changeFlowTaskList) {
                if (t.getCellId().equals(toCellId)) {
                    toTask = t;
                    inputList = t.getInputList();
                    break;
                }
            }
            Map<String, String> inputMap = new HashMap<String, String>();
            Map<String, String> namesMap = new HashMap<String, String>();
            Map<String, String> originNamesMap = new HashMap<String, String>();
            for (FlowTaskInputsVo input : inputList) {
                inputMap.put(input.getName(), input.getName());
            }
            for (int i = 0; i < names.length; i++ ) {
                if (StringUtils.isNotEmpty(names[i])) {
                    namesMap.put(names[i], names[i]);
                    if (StringUtils.isEmpty(inputMap.get(names[i]))) {
                        FlowTaskInputsVo in = new FlowTaskInputsVo();
                        String uuid = PlanConstants.PLAN_CREATE_UUID
                                      + UUID.randomUUID().toString();
                        in.setId(uuid);
                        in.setName(names[i]);
                        in.setUseObjectId(toTask.getId());
                        in.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                        in.setOrigin(fromTask.getPlanName());
                        originNamesMap.put(names[i], fromTask.getPlanName());
                        in.setOriginObjectId(originObjectIds[i]);
                        in.setOriginObjectName(originObjectNames[i]);
                        in.setOriginDeliverablesInfoId(originDeliverablesInfoIds[i]);
                        in.setOriginDeliverablesInfoName(originDeliverablesInfoNames[i]);
                        inputList.add(in);
                    }
                }
            }

            for (int i = inputList.size(); i > 0; i-- ) {
                if (fromTask.getId().equals(inputList.get(i - 1).getOriginObjectId())
                    && StringUtils.isEmpty(namesMap.get(inputList.get(i - 1).getName()))
                    && StringUtils.isEmpty(originNamesMap.get(inputList.get(i - 1).getName()))) {
                    toTask.getInputList().remove(i - 1);
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.operatefailure");
            aj.setMsg(message);
            aj.setSuccess(false);
            return aj;
        }

        aj.setMsg(message);
        aj.setSuccess(true);
        return aj;
    }

    /**
     * 判断节点是否必要节点
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "isRequiredCell")
    @ResponseBody
    public AjaxJson isRequiredCell(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String parentPlanId = req.getParameter("parentPlanId");
        String cellId = req.getParameter("cellId");
        try {
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            plan.setCellId(cellId);
            List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(list)) {
                plan = list.get(0);
                if ("true".equals(plan.getRequired())) {
                    j.setObj("true");
                }
                else {
                    j.setObj("false");
                }
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.judgesuccess"));
                j.setSuccess(true);
            }
            else {
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNullNodeInfo"));
                j.setSuccess(false);
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNodeInfoFailure"));
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 判断节点是否必要节点
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "isRequiredCellAndHasChild")
    @ResponseBody
    public AjaxJson isRequiredCellAndHasChild(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String parentPlanId = req.getParameter("parentPlanId");
        String cellId = req.getParameter("cellId");
        try {
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            plan.setCellId(cellId);
            List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(list)) {
                plan = list.get(0);
                if ("true".equals(plan.getRequired())) {
                    j.setObj("true");
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.judgesuccess"));
                }
                else {
                    PlanDto parent = new PlanDto();
                    parent.setParentPlanId(plan.getId());
                    List<PlanDto> childList = planService.queryPlanList(parent, 1, 10, false);
                    if (!CommonUtil.isEmpty(childList)) {
                        j.setObj("hasChild");
                        Object[] arguments = new String[] {plan.getPlanName()};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.hasChild", arguments));
                    }
                    else {
                        j.setObj("false");
                        j.setMsg("【" + plan.getPlanName() + "】");
                    }
                }
                j.setSuccess(true);
            }
            else {
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNullNodeInfo"));
                j.setSuccess(true);
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNodeInfoFailure"));
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 判断节点是否必要节点
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "isRequiredCellAndHasChildForChange")
    @ResponseBody
    public AjaxJson isRequiredCellAndHasChildForChange(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            FlowTaskVo plan = null;
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (cellId.equals(flowTask.getCellId())) {
                    plan = flowTask;
                    break;
                }
            }
            if (plan == null || StringUtils.isEmpty(plan.getId())) {
                j.setObj("false");
            }
            else if ("true".equals(plan.getRequired())) {
                j.setObj("true");
            }
            else {
                if (StringUtils.isNotEmpty(plan.getPlanId())) {
                    PlanDto parent = new PlanDto();
                    parent.setParentPlanId(plan.getPlanId());
                    List<PlanDto> childList = planService.queryPlanList(parent, 1, 10, false);
                    if (!CommonUtil.isEmpty(childList)) {
                        j.setObj("hasChild");
                        Object[] arguments = new String[] {plan.getPlanName()};
                        j.setMsg(I18nUtil.getValue(
                            "com.glaway.ids.pm.rdtask.flowResolve.hasChild", arguments));
                    }
                    else {
                        j.setObj("false");
                        j.setMsg("【" + plan.getPlanName() + "】");
                    }
                }
            }
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.judgesuccess"));
            j.setSuccess(true);

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNodeInfoFailure"));
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 判断后置计划
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "pdAfter")
    @ResponseBody
    public AjaxJson pdAfter(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String cellId = req.getParameter("cellId");
        String status = req.getParameter("status");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            if (StringUtils.isNotEmpty(cellId) && !"end".equals(cellId)) {
                if (StringUtils.isNotEmpty(status) && "ORDERED".equals(status)) {
                    List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                        PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
                    FlowTaskVo plan = new FlowTaskVo();
                    if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                        for (FlowTaskVo flowTask : changeFlowTaskList) {
                            if (StringUtils.isNotEmpty(flowTask.getCellId())
                                && cellId.equals(flowTask.getCellId())) {
                                plan = flowTask;
                                break;
                            }
                        }
                    }
                    if (plan != null) {
                        if (StringUtils.isNotEmpty(plan.getBizCurrent())) {
                            if (PlanConstants.PLAN_FINISH.equals(plan.getBizCurrent())
                                || PlanConstants.PLAN_FEEDBACKING.equals(plan.getBizCurrent())) {
                                j.setSuccess(false);
                            }
                        }
                    }
                }
                else {
                    PlanDto plan = new PlanDto();
                    plan.setParentPlanId(parentPlanId);
                    plan.setCellId(cellId);
                    List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
                    PlanDto p = new PlanDto();
                    if (list != null && list.size() > 0) {
                        p = list.get(0);
                        if (StringUtils.isNotEmpty(p.getBizCurrent())) {
                            if (PlanConstants.PLAN_FINISH.equals(p.getBizCurrent())
                                || PlanConstants.PLAN_FEEDBACKING.equals(p.getBizCurrent())) {
                                j.setSuccess(false);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.getNodeInfoFailure"));
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 判断上是否有输入输出关系
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "isLineConnect")
    @ResponseBody
    public AjaxJson isLineConnect(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String parentPlanId = req.getParameter("parentPlanId");
        String fromCellId = req.getParameter("fromCellId");
        String toCellId = req.getParameter("toCellId");
        try {
            PlanDto from = new PlanDto();
            from.setParentPlanId(parentPlanId);
            from.setCellId(fromCellId);
            List<PlanDto> fromList = planService.queryPlanList(from, 1, 10, false);
            if (!CommonUtil.isEmpty(fromList)) {
                from = fromList.get(0);

                PlanDto to = new PlanDto();
                to.setParentPlanId(parentPlanId);
                to.setCellId(toCellId);
                List<PlanDto> toList = planService.queryPlanList(to, 1, 10, false);
                if (!CommonUtil.isEmpty(toList)) {
                    to = toList.get(0);
                    InputsDto inputs = new InputsDto();
                    inputs.setUseObjectId(to.getId());
                    inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                    inputs.setOrigin(from.getPlanName());

                    List<InputsDto> list = inputsService.queryInputList(inputs, 1, 10, false);
                    if (!CommonUtil.isEmpty(list)) {
                        j.setSuccess(true);
                    }

                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 判断上是否有输入输出关系
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "isLineConnectForChange")
    @ResponseBody
    public AjaxJson isLineConnectForChange(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String fromCellId = req.getParameter("fromCellId");
        String toCellId = req.getParameter("toCellId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            FlowTaskVo from = new FlowTaskVo();
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (fromCellId.equals(flowTask.getCellId())) {
                    from = flowTask;
                    break;
                }
            }
            FlowTaskVo to = new FlowTaskVo();
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (toCellId.equals(flowTask.getCellId())) {
                    to = flowTask;
                    break;
                }
            }
            List<FlowTaskInputsVo> list = to.getInputList();
            boolean flg = false;
            for (FlowTaskInputsVo inputs : list) {
                if (inputs.getUseObjectId().equals(to.getId())
                    && inputs.getUseObjectType().equals(PlanConstants.USEOBJECT_TYPE_PLAN)
                    && inputs.getOrigin().equals(from.getPlanName())) {
                    flg = true;
                }
            }
            j.setSuccess(flg);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 删除线上的输入输出关系
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "deleteLineConnect")
    @ResponseBody
    public AjaxJson deleteLineConnect(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String parentPlanId = req.getParameter("parentPlanId");
        String fromCellId = req.getParameter("fromCellId");
        String toCellId = req.getParameter("toCellId");
        try {
            PlanDto condition = new PlanDto();
            condition.setParentPlanId(parentPlanId);
            List<PlanDto> list = planService.queryPlanList(condition, 1, 10, false);
            if (!CommonUtil.isEmpty(list)) {
                PlanDto from = new PlanDto();
                PlanDto to = new PlanDto();
                for (PlanDto p : list) {
                    if (fromCellId.endsWith(p.getCellId())) {
                        from = p;
                    }
                    if (toCellId.endsWith(p.getCellId())) {
                        to = p;
                    }
                }
                if (!CommonUtil.isEmpty(from) && !CommonUtil.isEmpty(to)) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("to", to);
                    map.put("from", from);
                    taskFlowResolveService.deleteLineConnect(map);
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 删除关联的输入
     * @param parentPlanId
     * @param fromCellId
     * @param toCellId
     */
    public AjaxJson deleteLinkInput(String parentPlanId, String fromCellId, String toCellId) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        try {
            PlanDto from = new PlanDto();
            from.setParentPlanId(parentPlanId);
            from.setCellId(fromCellId);
            List<PlanDto> fromList = planService.queryPlanList(from, 1, 10, false);
            if (!CommonUtil.isEmpty(fromList)) {
                from = fromList.get(0);
                PlanDto to = new PlanDto();
                to.setParentPlanId(parentPlanId);
                to.setCellId(toCellId);
                List<PlanDto> toList = planService.queryPlanList(to, 1, 10, false);
                if (!CommonUtil.isEmpty(toList)) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("to", to);
                    map.put("toList", toList);
                    map.put("from", from);
                    taskFlowResolveService.deleteLinkInput(map);
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 删除所选节点
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "deleteSelectedCell")
    @ResponseBody
    public AjaxJson deleteSelectedCell(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String parentPlanId = req.getParameter("parentPlanId");
        String cellId = req.getParameter("cellId");
        String status = req.getParameter("status");
        try {
            if ("ORDERED".equals(status)) {
                List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
                List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)req.getSession().getAttribute(
                    PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);
                String flowTaskId = "";
                String nodeId = "";
                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (cellId.equals(flowTask.getCellId())) {
                        if (!CommonUtil.isEmpty(flowTask.getPlanId())) {
                            nodeId = flowTask.getPlanId();
                        }
                        else {
                            nodeId = flowTask.getId();
                        }
                        flowTaskId = flowTask.getId();
                        changeFlowTaskList.remove(flowTask);
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(flowTaskId)) {
                    for (FlowTaskVo flowTask : changeFlowTaskList) {
                        List<FlowTaskInputsVo> inputList = flowTask.getInputList();
                        List<FlowTaskInputsVo> newInputList = new ArrayList<FlowTaskInputsVo>();
                        if (!CommonUtil.isEmpty(inputList)) {
                            for (FlowTaskInputsVo in : inputList) {
                                if (!flowTaskId.equals(in.getOriginObjectId())) {
                                    newInputList.add(in);
                                }
                                else {
                                    if (!CommonUtil.isEmpty(flowTask.getTaskNameType())
                                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(flowTask.getTaskNameType())
                                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(flowTask.getTaskNameType())
                                        && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(flowTask.getTaskNameType())) {
                                        in.setOriginObjectId(null);
                                        in.setOriginObjectName(null);
                                        in.setOriginDeliverablesInfoId(null);
                                        in.setOriginDeliverablesInfoName(null);
                                        newInputList.add(in);
                                    }
                                }
                            }
                            flowTask.getInputList().clear();
                            flowTask.getInputList().addAll(newInputList);
                        }
                    }
                    req.getSession().setAttribute(
                        PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId, changeFlowTaskList);
                    if (!CommonUtil.isEmpty(flowTaskPreposeList)) {
                        List<FlowTaskPreposeVo> newflowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();
                        for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
                            if (!flowTaskId.equals(prepose.getFlowTaskId())
                                && !flowTaskId.equals(prepose.getPreposeId())) {
                                newflowTaskPreposeList.add(prepose);
                            }
                        }
                        req.getSession().setAttribute(
                            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId,
                            newflowTaskPreposeList);
                    }
                }
                StringBuffer resStrBuffer = new StringBuffer();
//                List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//                if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                    for (OutwardExtension out : outwardExtensionList) {
//                        if (!CommonUtil.isEmpty(resStrBuffer.toString())) {
//                            resStrBuffer.append("," + out.getOptionValue());
//                        }
//                        else {
//                            resStrBuffer.append(out.getOptionValue());
//                        }
//                    }
//                }
                j.setObj(nodeId + ":" + resStrBuffer.toString());
            }
            else {
                String planId = "";
                PlanDto plan = new PlanDto();
                plan.setParentPlanId(parentPlanId);
                plan.setCellId(cellId);
                List<PlanDto> childList = planService.queryPlanList(plan, 1, 10, false);
                if (!CommonUtil.isEmpty(childList)) {
                    planId = childList.get(0).getId();
                    taskFlowResolveService.deleteSelectedCell(parentPlanId, cellId);
                    //TODO..
//                    List<String> httpUrls = new ArrayList<String>();
//                    List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowResolveCategoryHttpServer");
//                    if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                        for (OutwardExtension ext : outwardExtensionList) {
//                            if (!CommonUtil.isEmpty(ext.getUrlList())) {
//                                for (OutwardExtensionUrl out : ext.getUrlList()) {
//                                    if ("update".equals(out.getOperateCode())) {
//                                        httpUrls.add(out.getOperateUrl());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    if (!CommonUtil.isEmpty(httpUrls)) {
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("deleteType", "node");
//                        map.put("parentPlanId", parentPlanId);
//                        map.put("planId", planId);
//                        for (String url : httpUrls) {
//                            try {
//                                HttpClientUtil.httpClientPostByTest(url + "&deleteType=node", map);
//                            }
//                            catch (Exception e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                }
            }
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.editsuccess"));
            j.setSuccess(true);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            j.setSuccess(false);
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.editfailure"));
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 删除线上的输入输出关系
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "deleteLineConnectForChange")
    @ResponseBody
    public AjaxJson deleteLineConnectForChange(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        String fromCellId = req.getParameter("fromCellId");
        String toCellId = req.getParameter("toCellId");
        String parentPlanId = req.getParameter("parentPlanId");
        try {
            if (StringUtils.isNotEmpty(fromCellId) && StringUtils.isNotEmpty(toCellId)) {
                List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)req.getSession().getAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

                List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)req.getSession().getAttribute(
                    PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);

                FlowTaskVo from = new FlowTaskVo();
                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (fromCellId.equals(flowTask.getCellId())) {
                        from = flowTask;
                        break;
                    }
                }

                FlowTaskVo to = new FlowTaskVo();
                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (toCellId.equals(flowTask.getCellId())) {
                        to = flowTask;
                        break;
                    }
                }

                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (to.getId().equals(flowTask.getId())) {
                        List<FlowTaskInputsVo> inputList = flowTask.getInputList();
                        List<FlowTaskInputsVo> newInputList = new ArrayList<FlowTaskInputsVo>();
                        if (!CommonUtil.isEmpty(inputList)) {
                            for (FlowTaskInputsVo in : inputList) {
                                if (!from.getId().equals(in.getOriginObjectId())) {
                                    newInputList.add(in);
                                }
                            }
                            flowTask.getInputList().clear();
                            flowTask.getInputList().addAll(newInputList);
                        }
                    }
                }

                req.getSession().setAttribute(
                    PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId, changeFlowTaskList);
                if (!CommonUtil.isEmpty(flowTaskPreposeList)) {
                    for (FlowTaskPreposeVo prepose : flowTaskPreposeList) {
                        if (from.getId().equals(prepose.getFlowTaskId())
                            && to.getId().equals(prepose.getPreposeId())) {
                            flowTaskPreposeList.remove(prepose);
                        }
                    }
                    req.getSession().setAttribute(
                        PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId,
                        flowTaskPreposeList);
                }
                if (!CommonUtil.isEmpty(from) && !CommonUtil.isEmpty(to)) {
                    StringBuffer resStrBuffer = new StringBuffer();
                    //TODO..
//                    List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("activeCategory");
//                    if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                        for (OutwardExtension out : outwardExtensionList) {
//                            if (!CommonUtil.isEmpty(resStrBuffer.toString())) {
//                                resStrBuffer.append("," + out.getOptionValue());
//                            }
//                            else {
//                                resStrBuffer.append(out.getOptionValue());
//                            }
//                        }
//                        j.setObj((!CommonUtil.isEmpty(from.getPlanId()) ? from.getPlanId() : from.getId())
//                                 + ","
//                                 + (!CommonUtil.isEmpty(to.getPlanId()) ? to.getPlanId() : to.getId())
//                                 + ":" + resStrBuffer.toString());
//                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 删除线上的输入输出关系
     * @param fromCellId
     * @param toCellId
     * @param changeFlowTaskList
     */
    public AjaxJson deleteLinkInputForChange(String fromCellId, String toCellId,
                                             List<FlowTaskVo> changeFlowTaskList) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        try {
            if (StringUtils.isNotEmpty(fromCellId) && StringUtils.isNotEmpty(toCellId)) {
                FlowTaskVo from = new FlowTaskVo();
                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (fromCellId.equals(flowTask.getCellId())) {
                        from = flowTask;
                        break;
                    }
                }

                FlowTaskVo to = new FlowTaskVo();
                for (FlowTaskVo flowTask : changeFlowTaskList) {
                    if (toCellId.equals(flowTask.getCellId())) {
                        to = flowTask;
                        break;
                    }
                }
                InputsDto inputs = new InputsDto();
                inputs.setUseObjectId(to.getId());
                inputs.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
                inputs.setOrigin(from.getPlanName());
                List<FlowTaskInputsVo> list = to.getInputList();
                if (!CommonUtil.isEmpty(to.getInputList())) {
                    for (FlowTaskInputsVo in : list) {
                        if (inputs.getUseObjectId().equals(to.getId())
                            && inputs.getUseObjectType().equals(PlanConstants.USEOBJECT_TYPE_PLAN)
                            && inputs.getOrigin().equals(from.getPlanName())) {
                            to.getInputList().remove(in);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * @param request
     * @param response
     */
    @RequestMapping(params = "getReferenceList")
    @ResponseBody
    public void getReferenceList(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
        // 查询条件
        TaskInfoReq taskInfo = new TaskInfoReq();
        List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();
        String planId = request.getParameter("planId");
        String parentPlanId = request.getParameter("parentPlanId");
        if (StringUtils.isNotEmpty(planId)) {
            taskInfo.setTaskId(planId);
            // 通过任务编号获得任务名称
            PlanDto plan = planService.getPlanEntity(planId);
            if (plan != null) {
                Project parentProject = projectService.getProjectEntity(plan.getProjectId());
                plan.setProject(parentProject);
                if (StringUtils.isNotEmpty(plan.getPlanName())) {
                    taskInfo.setTaskName(plan.getPlanName());
                }
                // 通过任务编号获得项目分类
                if (plan.getProject() != null) {
                    if (StringUtils.isNotEmpty(plan.getProject().getEps())) {
                        taskInfo.setEpsName(epsConfigService.getEpsNamePathById(plan.getProject().getEps()).getObj().toString());
                    }
                }
            }
            // 通过任务编号获得交付项
            PlanDto planNew = new PlanDto();
            planNew.setId(planId);
            projDocRelationList = getDocRelationList(planNew);
        }
        else {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            String taskId = request.getParameter("taskId");
            taskInfo.setTaskId(taskId);
            FlowTaskVo plan = null;
            for (FlowTaskVo flowTask : changeFlowTaskList) {
                if (flowTask.getId().equals(taskId)) {
                    plan = flowTask;
                    break;
                }
            }
            if (plan != null) {
                if (StringUtils.isNotEmpty(plan.getPlanName())) {
                    taskInfo.setTaskName(plan.getPlanName());
                }
                Project project = new Project();
                if (StringUtils.isNotEmpty(plan.getProjectId())) {
                    project = projectService.getProjectEntity(plan.getProjectId());
                }
                // 通过任务编号获得项目分类
                if (project != null && StringUtils.isNotEmpty(project.getEps())) {
                    taskInfo.setEpsName(epsConfigService.getEpsNamePathById(project.getEps()).getObj().toString());
                }
                for (FlowTaskDeliverablesInfoVo parentDeli : plan.getOutputList()) {
                    ProjDocVo projDocVo = new ProjDocVo();
                    projDocVo.setDeliverableId(parentDeli.getId());
                    projDocVo.setDeliverableName(parentDeli.getName());
                    projDocRelationList.add(projDocVo);
                }
            }

        }

        // 遍历交付项，组装成字符串
        StringBuffer sb = new StringBuffer();
        for (ProjDocVo projDocVo : projDocRelationList) {
            sb.append(" " + projDocVo.getDeliverableName());
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            taskInfo.setDeliverables(sb.substring(1));
        }
        // 获得当前用户的密级
        TSUserDto user = UserUtil.getInstance().getUser();
        if (user != null && user.getSecurityLevel() != null) {
            taskInfo.setUserSecretlevel(user.getSecurityLevel());
        }
        taskInfo.setPageSize(Integer.parseInt(request.getParameter("page")));
        taskInfo.setPageNum(Integer.parseInt(request.getParameter("rows")));
        JsonRequery jsonReq = new JsonRequery();
        jsonReq.setReqObj(gson.toJson(taskInfo));
        String jquery = gson.toJson(jsonReq);
        JsonResult jsonResult = new JsonResult();
        //TODO..
//        try {
//            KnowledgeSupportImplService supportService = new KnowledgeSupportImplService();
//            KnowledgeSupport support = supportService.getKnowledgeSupportImplPort();
//            String listStr = support.findKnowledgeInfo(jquery,
//                KnowledgeWebServiceConstants.KNOWLEDGE_SEARCE_LIST);
//            jsonResult = gson.fromJson(listStr, JsonResult.class);
//            if (!"0".equals(jsonResult.getRetCode())) {
//                log.warn(jsonResult.getRetMsg());
//                throw new GWException(jsonResult.getRetMsg());
//            }
//            jsonResult.setRetObj(gson.toJson(jsonResult.getRetObj()));
//        }
//        catch (GWException_Exception e) {
//            log.warn(e.getMessage());
//            throw new GWException(
//                I18nUtil.getValue("com.glaway.ids.pm.project.task.viewTaskKnowledegRecord")
//                    + I18nUtil.getValue("com.glaway.ids.pm.project.task.interfaceFailure"));
//        }
        TagUtil.ajaxResponse(response, jsonResult.getRetObj().toString());

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
//                FeignJson fj= planService.getDocRelation(childDeli.getId());
//                String projDocRelationDbListStr = String.valueOf(fj.getObj());
//                List<ProjDocRelationDto> projDocRelationList = null;
//                if(!CommonUtil.isEmpty(projDocRelationDbListStr.substring(1, projDocRelationDbListStr.length()-1))) {
//                    projDocRelationList = JSON.parseObject(JsonFromatUtil.formatJsonToList(projDocRelationDbListStr),new TypeReference<List<ProjDocRelationDto>>(){});
//                }
                if (projDocRelationList == null || projDocRelationList.size() < 1) {
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
                convertToVo(projDocVo, projDocRelation);
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                projDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
//                FeignJson fj = planService.getDocRelation(parentDeli.getId());
//                String projDocRelationDbListStr = String.valueOf(fj.getObj());
                List<ProjDocRelationDto> projDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
//                List<ProjDocRelationDto> projDocRelationDbList = null;
//                if(!CommonUtil.isEmpty(projDocRelationDbListStr) && !CommonUtil.isEmpty(projDocRelationDbListStr.substring(1, projDocRelationDbListStr.length()-1))) {
//                    projDocRelationDbList = JSON.parseObject(JsonFromatUtil.formatJsonToList(projDocRelationDbListStr),new TypeReference<List<ProjDocRelationDto>>(){});
//                }
//                List<ProjDocRelationDto> projDocRelationDbList = JSON.parseObject(JsonFromatUtil.formatJsonToList(projDocRelationDbListStr),new TypeReference<List<ProjDocRelationDto>>(){});

                if (projDocRelationDbList != null && projDocRelationDbList.size() > 0) {
                    convertToVo(projDocVo, projDocRelationDbList.get(0));
                }

                projDocVo.setOpFlag(true);
                projDocRelationList.add(projDocVo);
            }
        }

        return projDocRelationList;
    }

    /**
     * 项目计划页面初始化时获取项目库
     *
     * @param plan
     * @see
     */
    public List<ProjDocVo> getDocRelationListMatch(PlanDto plan, String deliverName) {
        // 通过交付项判断子计划是否包括项目库
        // 获取计划的输出
        DeliverablesInfoDto deliverablesInfo = new DeliverablesInfoDto();
        deliverablesInfo.setUseObjectId(plan.getId());
        deliverablesInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
        deliverablesInfo.setName(deliverName);
        List<DeliverablesInfoDto> deliverablesList = deliverablesInfoService.queryDeliverableList(
            deliverablesInfo, 1, 10, false);

        if (CommonUtil.isEmpty(deliverablesList)) {
            return new ArrayList<ProjDocVo>();
        }

        List<ProjDocVo> projDocRelationList = new ArrayList<ProjDocVo>();

        for (DeliverablesInfoDto parentDeli : deliverablesList) {
            ProjDocVo projDocVo = new ProjDocVo();
            projDocVo.setDeliverableId(parentDeli.getId());
            projDocVo.setDeliverableName(parentDeli.getName());
            List<ProjDocRelationDto> curProjDocRelationList = projLibService.getDocRelation(parentDeli.getId());
            ProjDocRelationDto projDocRelation = null;
            if (!CommonUtil.isEmpty(projDocRelationList)) {
                projDocRelation = curProjDocRelationList.get(0);
            }
            if (projDocRelation != null) { // 如果在子计划中包含交付项，需要使用子计划的交付项
                convertToVo(projDocVo, projDocRelation);
                projDocVo.setDeliverableName(parentDeli.getName());
                projDocVo.setOpFlag(false);
                projDocRelationList.add(projDocVo);
            }
            else { // 如果子计划中没有的，需要查询自己的交付项
                List<ProjDocRelationDto> projDocRelationDbList = projLibService.getDocRelation(parentDeli.getId());
                if (projDocRelationDbList != null && projDocRelationDbList.size() > 0) {
                    convertToVo(projDocVo, projDocRelationDbList.get(0));
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
    private ProjDocVo convertToVo(ProjDocVo projDocVo, ProjDocRelationDto projDocRelation) {
        if (StringUtils.isEmpty(projDocRelation.getDocId())) {
            return projDocVo;
        }
        projDocVo.setDocId(projDocRelation.getDocId());
        RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, projDocRelation.getDocId());
        if (rep == null) {
            return projDocVo;
        }
        projDocVo.setDocName(rep.getFileName());
        projDocVo.setVersion(rep.getBizVersion());
        String approveStatus = lifeCycleStatusService.getTitleByPolicyIdAndName(
            rep.getPolicy().getId(),
            rep.getBizCurrent());
        projDocVo.setSecurityLevel(rep.getSecurityLevel());
        projDocVo.setStatus(approveStatus);
        return projDocVo;
    }

    /**
     * 删除管参考知识
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "deleteReference")
    @ResponseBody
    public AjaxJson deleteReference(String code, String libId, String taskId, String planId,
                                    HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.clearsuccess");
        KnowledgeInfoReq req = new KnowledgeInfoReq();
        req.setCode(code);
        req.setLibId(libId);
        if (StringUtils.isNotEmpty(planId)) {
            req.setTaskId(planId);
        }
        else {
            req.setTaskId(taskId);
        }
        String userId = UserUtil.getInstance().getUser().getId();
        req.setUserId(userId);

        Gson gson = new Gson();
        JsonRequery jsonReq = new JsonRequery();
        jsonReq.setReqObj(gson.toJson(req));
        String jdelete = gson.toJson(jsonReq);
        JsonResult jsonResult = new JsonResult();
        try {
            //TODO..
//            KnowledgeSupportImplService supportService = new KnowledgeSupportImplService();
//            KnowledgeSupport support = supportService.getKnowledgeSupportImplPort();
//            String listStr = support.operationKnowledge(jdelete, "delete");
//            jsonResult = gson.fromJson(listStr, JsonResult.class);
//            if (!"0".equals(jsonResult.getRetCode())) {
//                j.setSuccess(false);
//                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.clearfailure");
//                log.warn(jsonResult.getRetMsg());
//            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.clearfailure");
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 根据父计划Id获取所有子计划的状态
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getCurrent")
    @ResponseBody
    public AjaxJson getCurrent(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.searchsuccess");
        String parentId = request.getParameter("parentId");
        PlanDto plan = planService.getPlanEntity(parentId);
        List<TaskCellVO> cells = new ArrayList<TaskCellVO>();

        try {
            if (plan != null) {
                List<PlanDto> planList = planService.getPlanAllChildren(plan);
                for (PlanDto plan2 : planList) {
                    if (plan.getId().equals(plan2.getParentPlanId())) {
                        Project project = projectService.getProjectEntity(plan2.getProjectId());
                        TaskCellVO vo = new TaskCellVO();
                        vo.setCellId(plan2.getCellId());
                        if ("FINISH".equalsIgnoreCase(plan2.getBizCurrent())) {
                            vo.setBizCurrent(plan2.getBizCurrent());
                        }
                        else if ("CLOSED".equalsIgnoreCase(project.getBizCurrent())
                                 || "PAUSED".equalsIgnoreCase(project.getBizCurrent())) {
                            vo.setBizCurrent(project.getBizCurrent());
                        }
                        else {
                            vo.setBizCurrent(plan2.getBizCurrent());
                        }
                        cells.add(vo);
                    }
                }
                j.setObj(cells);
            }
            else {
                // 若为流程变更时
                FlowTaskParentVo p = new FlowTaskParentVo();
                p.setParentId(parentId);
                List<FlowTaskVo> planList1 = taskFlowResolveService.getChangeFlowTaskList(p,UserUtil.getCurrentUser().getId());
                for (FlowTaskVo plan2 : planList1) {
                    Project project = projectService.getProjectEntity(plan2.getProjectId());
                    TaskCellVO vo = new TaskCellVO();
                    vo.setCellId(plan2.getCellId());
                    if ("FINISH".equalsIgnoreCase(plan2.getBizCurrent())) {
                        vo.setBizCurrent(plan2.getBizCurrent());
                    }
                    else if ("CLOSED".equalsIgnoreCase(project.getBizCurrent())
                             || "PAUSED".equalsIgnoreCase(project.getBizCurrent())) {
                        vo.setBizCurrent(project.getBizCurrent());
                    }
                    else {
                        vo.setBizCurrent(plan2.getBizCurrent());
                    }
                    cells.add(vo);

                }
                j.setObj(cells);
            }
        }
        catch (Exception e) {
            log.warn(e.getMessage());
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.task.searchfailure");
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 流程任务提交审批
     * @param parentPlanId
     * @param flowTaskStatus
     * @param request
     */
    @RequestMapping(params = "goToSaveAsTemplatePage")
    public ModelAndView goToSaveAsTemplatePage(String parentPlanId, String flowTaskStatus,
                                               HttpServletRequest request) {
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
        String switchStr = switchStrJson.getObj().toString();
//        String switchStr = "";
//        try {
//            RdfTaskSupportImplService support = new RdfTaskSupportImplService();
//            RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
//            String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
//            AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
//            switchStr = (String)ajaxJson.getObj();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
        String isStandard = "false";
        if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)) {// 非强制名称库
            isStandard = "ok";
        }
        else if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
                 || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
            isStandard = "true";
        }
        else {
            isStandard = "false";// 不使用名称库
        }
        request.setAttribute("isStandard", isStandard);
        request.setAttribute("parentPlanId", parentPlanId);
        request.setAttribute("flowTaskStatus", flowTaskStatus);
        return new ModelAndView("com/glaway/ids/pm/project/task/flowTaskSaveAsTemplate");
    }

    /**
     * 变更申请提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goChangeApplyEdit")
    public ModelAndView goChangeApplyEdit(HttpServletRequest req) {
        String taskNumber = req.getParameter("taskNumber");
        PlanownerApplychangeInfoDto p = planService.getPlanownerApplychangeInfo(taskNumber);
        req.setAttribute("planownerApplychangeInfo_", p);
        String formId = req.getParameter("taskNumber");
        String taskId = req.getParameter("taskId");
        req.getSession().setAttribute("taskIdSave", taskId);
        req.getSession().setAttribute("planId", p.getPlanId());
        PlanDto plan = planService.getPlanEntity(p.getPlanId());
        req.getSession().setAttribute("planName", plan.getPlanName());
        req.getSession().setAttribute("formId", formId);
        BusinessConfig b = businessConfigService.getBusinessConfig(p.getChangeType());
        req.setAttribute("changetypeName", b.getName());
        return new ModelAndView("com/glaway/ids/pm/project/task/applyChangeEdit");
    }

    /**
     * 变更申请提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goChangeApply")
    public ModelAndView goChangeApply(HttpServletRequest req) {
        String planId = req.getParameter("planId");
        PlanDto plan = planService.getPlanEntity(planId);
        req.setAttribute("planId", planId);
        req.setAttribute("planName", plan.getPlanName());
        return new ModelAndView("com/glaway/ids/pm/project/task/applyChange");
    }


    /**
     * 驳回到首节点再次提交工作流
     *
     * @return
     */
    @RequestMapping(params = "startChangeApplyForWorkFlow")
    @ResponseBody
    public FeignJson startChangeApplyForWorkFlow(PlanownerApplychangeInfoDto planownerApplychangeInfo,
                                                HttpServletRequest req) {
        String formId = (String)req.getSession().getAttribute("formId");
        String taskId = (String)req.getSession().getAttribute("taskIdSave");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("dto",planownerApplychangeInfo);
        map.put("formId",formId);
        map.put("taskId",taskId);
        map.put("userId",UserUtil.getCurrentUser().getId());
        FeignJson j = taskFlowResolveService.startChangeApplyForWorkFlow(map);
        return j;
    }

    /**
     * 终止流程
     *
     * @return
     */
    @RequestMapping(params = "cancelChangeApplyForWorkFlow")
    @ResponseBody
    public FeignJson cancelChangeApplyForWorkFlow(PlanownerApplychangeInfoDto planownerApplychangeInfo,
                                                 HttpServletRequest req) {
        String formId = (String)req.getSession().getAttribute("formId");
        String taskId = (String)req.getSession().getAttribute("taskIdSave");
        String userId = ResourceUtil.getCurrentUser().getId();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("dto",planownerApplychangeInfo);
        map.put("formId",formId);
        map.put("taskId",taskId);
        map.put("userId",userId);

        FeignJson j = taskFlowResolveService.cancelChangeApplyForWorkFlow(map);
        return j;
    }

    /**
     * 获根据任务名称获取任务类型 2016年4月8日 17:56:25 wqb
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTaskNameType")
    @ResponseBody
    public AjaxJson getTaskNameType(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String planName = (String)request.getParameter("planName");
        String message = "";
        try {
            String dictCode = "activeCategory";
//            List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
            Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
            List<TSTypeDto> types = tsMap.get(dictCode);
            if (StringUtils.isNotEmpty(planName.trim())) {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.getTaskTypeSuccess");
                NameStandardDto ns = new NameStandardDto();
                ns.setName(planName.trim());
                List<NameStandardDto> nameStandards = nameStandardService.searchNameStandardsAccurate(ns);
                if (!CommonUtil.isEmpty(nameStandards)) {
                    j.setObj(nameStandards.get(0).getActiveCategory());
                }
                else {
                    if (!CommonUtil.isEmpty(types)) {
                        j.setObj(types.get(0).getTypecode());
                    }
                    else {
                        if (!CommonUtil.isEmpty(types)) {
                            j.setObj(types.get(0).getTypecode());
                        }
                    }
                }
                j.setSuccess(true);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.getTaskTypeSuccess");
                if (!CommonUtil.isEmpty(types)) {
                    j.setObj(types.get(0).getTypecode());
                }
                else {
                    if (!CommonUtil.isEmpty(types)) {
                        j.setObj(types.get(0).getTypecode());
                    }
                }
                j.setSuccess(true);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.getTaskTypeFailure");
            j.setMsg(message);
            j.setSuccess(false);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        return j;
    }

    /**
     * 保存到前置计划表：
     */
    @RequestMapping(params = "saveOutPreposePlan")
    @ResponseBody
    public void saveOutPreposePlan(HttpServletRequest request, HttpServletResponse response) {

        String preposeIds = request.getParameter("preposeIds");
        String preposePlans = request.getParameter("preposePlans");
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        String message = "";

        String useObjectId = "";
        PlanDto plan = new PlanDto();
        plan.setCellId(cellId);
        plan.setParentPlanId(parentPlanId);
        List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);
        if (taskList.size() > 0) {
            plan = taskList.get(0);
            useObjectId = plan.getId();
        }
        try {
            taskFlowResolveService.saveOutPreposePlan(preposeIds, useObjectId);

            log.info(message, preposeIds, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.savePreposePlanFailure");
            log.error(message, e, "", "");
            Object[] params = new Object[] {message, ApprovePlanFormDto.class.getClass() + " oids:"};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);

        }
    }

    /**
     * 复制并保存计划信息
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "paste")
    @ResponseBody
    public AjaxJson paste(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            String parentId = request.getParameter("parentId");
            String flowStatus = request.getParameter("flowStatus");
            String fromCellIds = request.getParameter("fromCellIds");
            String toCellIds = request.getParameter("toCellIds");
            String dictCode = "activeCategory";
//            List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
            Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
            List<TSTypeDto> types = tsMap.get(dictCode);
            if (!"ORDERED".equals(flowStatus)) {
                // 流程分解
                PlanDto plan = new PlanDto();
                plan.setParentPlanId(parentId);
                List<PlanDto> taskList = planService.queryPlanList(plan, 1, 10, false);
                PlanDto parent = planService.getPlanEntity(parentId);
                Project project = projectService.getProjectEntity(parent.getProjectId());
                int maxPlanNumber = planService.getMaxPlanNumber(parent);
                int maxStoreyNo = 0;
                int nameIndex = 1;
                for (PlanDto pl : taskList) {
                    if (maxStoreyNo < pl.getStoreyNo()) {
                        maxStoreyNo = pl.getStoreyNo();
                    }
                    // .contains(PlanConstants.DEFAULT_TASKNAME)
                    String num = "";
                    if (pl.getPlanName().startsWith(PlanConstants.DEFAULT_TASKNAME)) {
                        if (!CommonUtil.isEmpty(pl.getPlanName().split(
                            PlanConstants.DEFAULT_TASKNAME))) {
                            num = pl.getPlanName().split(PlanConstants.DEFAULT_TASKNAME)[1];
                            // 判断是否为数字
                            Pattern pattern = Pattern.compile("[0-9]*");
                            if (pattern.matcher(num).matches()) {
                                if (Integer.parseInt(num) > nameIndex) {
                                    nameIndex = Integer.parseInt(num) + 1;
                                }
                                else {
                                    nameIndex++ ;
                                }
                            }
                        }
                    }

                }
                if (!CommonUtil.isEmpty(taskList)) {
                    String[] fromIdArr = fromCellIds.split(",");
                    String[] toIdArr = toCellIds.split(",");
                    for (int i = 0; i < fromIdArr.length; i++ ) {
                        for (PlanDto p : taskList) {
                            if (p.getCellId().equals(fromIdArr[i])) {
                                PlanDto toPlan = new PlanDto();
                                planService.initPlan(toPlan);
                                CommonInitUtil.initGLVDataForCreate(toPlan);
                                toPlan.setWorkTime("1");
                                toPlan.setCellId(toIdArr[i]);
                                toPlan.setPlanName(PlanConstants.DEFAULT_TASKNAME + nameIndex);
                                toPlan.setPlanLevel(p.getPlanLevel());
                                if (!CommonUtil.isEmpty(types)) {
                                    toPlan.setTaskNameType(types.get(0).getTypecode());
                                }
                                toPlan.setParentPlanId(parentId);
                                toPlan.setProjectId(p.getProjectId());
                                toPlan.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
                                toPlan.setOwner(p.getOwner());
                                toPlan.setPlanNumber(maxPlanNumber + 1);
                                maxPlanNumber++ ;
                                toPlan.setStoreyNo(maxStoreyNo + 1);
                                maxStoreyNo++ ;
                                toPlan.setPlanStartTime(parent.getPlanStartTime());
                                Date planEndTime = toPlan.getPlanStartTime();
                                if (ProjectConstants.WORKDAY.equals(project.getProjectTimeType())) {
                                    planEndTime = DateUtil.nextWorkDay(
                                        (Date)toPlan.getPlanStartTime().clone(), 0);
                                }
                                else if (ProjectConstants.COMPANYDAY.equals(project.getProjectTimeType())) {
                                    Map<String, Object> params = new HashMap<String, Object>();
                                    params.put("startDate",planEndTime);
                                    params.put("days",0);

                                    planEndTime = calendarService.getNextWorkingDay(appKey, params);
                                }
                                else {
                                    planEndTime = DateUtil.nextDay(
                                        (Date)toPlan.getPlanStartTime().clone(), 0);
                                }
                                toPlan.setPlanEndTime(planEndTime);
                                planService.savePlanByPlanDto(toPlan);
                                nameIndex++ ;
                            }
                        }
                    }
                }
            }
            else {
                // 流程变更
                List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                    PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentId);
                int nameIndex = 1;
                for (FlowTaskVo pl : changeFlowTaskList) {
                    String num = "";
                    if (pl.getPlanName().startsWith(PlanConstants.DEFAULT_TASKNAME)) {
                        if (!CommonUtil.isEmpty(pl.getPlanName().split(
                            PlanConstants.DEFAULT_TASKNAME))) {
                            num = pl.getPlanName().split(PlanConstants.DEFAULT_TASKNAME)[1];
                            // 判断是否为数字
                            Pattern pattern = Pattern.compile("[0-9]*");
                            if (pattern.matcher(num).matches()) {
                                if (Integer.parseInt(num) > nameIndex) {
                                    nameIndex = Integer.parseInt(num) + 1;
                                }
                                else {
                                    nameIndex++ ;
                                }
                            }
                        }
                    }
                }
                if (!CommonUtil.isEmpty(changeFlowTaskList)) {
                    String[] fromIdArr = fromCellIds.split(",");
                    String[] toIdArr = toCellIds.split(",");
                    List<FlowTaskVo> toList = new ArrayList<FlowTaskVo>();
                    for (int i = 0; i < fromIdArr.length; i++ ) {
                        for (FlowTaskVo p : changeFlowTaskList) {
                            if (p.getCellId().equals(fromIdArr[i])) {
                                FlowTaskVo toPlan = new FlowTaskVo();
                                toPlan.setId(PlanConstants.PLAN_CREATE_UUID
                                             + UUID.randomUUID().toString());
                                toPlan.setWorkTime("1");
                                PlanDto toPlanChange = new PlanDto();
                                planService.initPlan(toPlanChange);
                                toPlan.setBizCurrent(PlanConstants.PLAN_EDITING);
                                toPlan.setCellId(toIdArr[i]);
                                toPlan.setPlanName(PlanConstants.DEFAULT_TASKNAME + nameIndex);
                                toPlan.setPlanLevel(p.getPlanLevel());
                                if (!CommonUtil.isEmpty(types)) {
                                    toPlan.setTaskNameType(types.get(0).getTypecode());
                                }
                                toPlan.setParentPlanId(parentId);
                                toPlan.setOwner(p.getOwner());
                                toPlan.setProjectId(p.getProjectId());
                                toPlan.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
                                toList.add(toPlan);
                                nameIndex++ ;
                            }
                        }
                    }
                    if (!CommonUtil.isEmpty(toList)) {
                        changeFlowTaskList.addAll(toList);
                    }
                    request.getSession().setAttribute(
                        PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentId, changeFlowTaskList);

                }

            }

            log.info(message, parentId, "");
        }
        catch (Exception e) {
            log.error(message, e, "", "");
            Object[] params = new Object[] {message, ""};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 双击线的时候判断打开的页面
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "selectLineUrl")
    @ResponseBody
    public AjaxJson selectLineUrl(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            //TODO..
//            List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowTempActiveLineSet");
//            String fromCellId = request.getParameter("fromCellId");
//            String toCellId = request.getParameter("toCellId");
//            String templateId = request.getParameter("parentPlanId");
            boolean needHttp = false;
            if (needHttp) {
                j.setSuccess(false);
            }
            else {
                j.setSuccess(true);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveFailure");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        return j;
    }

    /**
     * 双击线的时候判断打开的页面
     * @param request
     * @param response
     */
    @RequestMapping(params = "selectLineUrlForFlow")
    @ResponseBody
    public AjaxJson selectLineUrlForFlow(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        try {
            //TODO..
//            List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowActiveLineSet");
            String fromCellId = request.getParameter("fromCellId");
            String toCellId = request.getParameter("toCellId");
            String parentPlanId = request.getParameter("parentPlanId");
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(parentPlanId);
            plan.setCellId(fromCellId);
            List<PlanDto> left = planService.queryPlanList(plan, 1, 10, false);
            if (!CommonUtil.isEmpty(left)) {
                plan = left.get(0);
            }

            PlanDto planAfter = new PlanDto();
            planAfter.setParentPlanId(parentPlanId);
            planAfter.setCellId(toCellId);
            List<PlanDto> right = planService.queryPlanList(planAfter, 1, 10, false);
            if (!CommonUtil.isEmpty(right)) {
                planAfter = right.get(0);
            }

            boolean needHttp = false;
            if (!CommonUtil.isEmpty(plan.getTaskNameType())
                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(plan.getTaskNameType())
                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(plan.getTaskNameType())
                && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(plan.getTaskNameType())) {
                if (!CommonUtil.isEmpty(planAfter.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(planAfter.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(planAfter.getTaskNameType())
                    && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(planAfter.getTaskNameType())) {
                    needHttp = true;
                }
            }
            if (needHttp) {
                //TODO..
//                j.setSuccess(false);
//                if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                    if (!CommonUtil.isEmpty(outwardExtensionList.get(0).getUrlList())) {
//                        OutwardExtensionUrl out = outwardExtensionList.get(0).getUrlList().get(0);
//                        out.setExt1(plan.getId());
//                        out.setExt2(planAfter.getId());
//                        NameStandard ns = new NameStandard();
//                        ns.setName(plan.getPlanName());
//                        ns.setStopFlag("启用");
//                        List<NameStandard> list = nameStandardService.searchNameStandards(ns);
//                        if (!CommonUtil.isEmpty(list)) {
//                            out.setExt3(list.get(0).getId());
//                        }
//
//                        NameStandard ns2 = new NameStandard();
//                        ns2.setName(planAfter.getPlanName());
//                        ns2.setStopFlag("启用");
//                        List<NameStandard> list2 = nameStandardService.searchNameStandards(ns2);
//                        if (!CommonUtil.isEmpty(list2)) {
//                            out.setDeleteFlag(list2.get(0).getId());
//                        }
//                        j.setObj(out);
//                    }
//                }
            }
            else {
                j.setSuccess(true);
            }

        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveFailure");
            j.setSuccess(false);
            j.setMsg(message);
            Object[] params = new Object[] {message, PlanDto.class.getClass() + " oids: "};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        return j;
    }

    /**
     * 根据活动名称主键id，获取tabs
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTabsByTaskNameType")
    @ResponseBody
    private AjaxJson getTabsByTaskNameType(HttpServletRequest request, HttpServletResponse response) {
        String useCode = request.getParameter("useCode");
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        List<Map<String, String>> tabsList = new ArrayList<Map<String, String>>();
        Map<String, String> tabMap = new HashMap<String, String>();
        //TODO..
//        List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList(useCode);
//        if (!CommonUtil.isEmpty(outwardExtensionList)) {
//            for (OutwardExtension ext : outwardExtensionList) {
//                for (int i = 0; !CommonUtil.isEmpty(ext.getUrlList())
//                                && i < ext.getUrlList().size(); i++ ) {
//                    tabMap = new HashMap<String, String>();
//                    tabMap.put("id", ext.getUrlList().get(i).getId());
//                    tabMap.put("title", ext.getUrlList().get(i).getDialogTitle());
//                    tabMap.put("href", ext.getUrlList().get(i).getOperateUrl());
//                    tabsList.add(tabMap);
//                }
//            }
//        }
        j.setObj(tabsList);
        return j;
    }

    /**
     * 根据活动名称主键id，获取tabs
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTabsByTaskNameTypeForFlow")
    @ResponseBody
    private AjaxJson getTabsByTaskNameTypeForFlow(HttpServletRequest request,
                                                  HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        List<Map<String, String>> tabsList = new ArrayList<Map<String, String>>();
        Map<String, String> tabMap = new HashMap<String, String>();
        String taskNameType = request.getParameter("taskNameType");
        boolean needHttp = false;
        if (!CommonUtil.isEmpty(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(taskNameType)) {
            needHttp = true;
        }
        if (needHttp) {
            //TODO..
//            List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("flowActiveCategory");
//            if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                for (OutwardExtension ext : outwardExtensionList) {
//                    for (int i = 0; !CommonUtil.isEmpty(ext.getUrlList())
//                                    && i < ext.getUrlList().size(); i++ ) {
//                        tabMap = new HashMap<String, String>();
//                        tabMap.put("id", ext.getUrlList().get(i).getId());
//                        tabMap.put("title", ext.getUrlList().get(i).getDialogTitle());
//                        tabMap.put("href", ext.getUrlList().get(i).getOperateUrl());
//                        tabsList.add(tabMap);
//                    }
//                }
//            }
        }
        j.setObj(tabsList);
        return j;
    }

    /**
     * 根据活动名称主键id，获取tabs
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTabsByTaskNameTypeForFlowChange")
    @ResponseBody
    private AjaxJson getTabsByTaskNameTypeForFlowChange(HttpServletRequest request,
                                                        HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        List<Map<String, String>> tabsList = new ArrayList<Map<String, String>>();
        Map<String, String> tabMap = new HashMap<String, String>();
        String taskNameType = request.getParameter("taskNameType");
        boolean needHttp = false;
        if (!CommonUtil.isEmpty(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RD.equals(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_REVIEW.equals(taskNameType)
            && !NameStandardConstants.NAMESTANDARD_ACTIVECATEGORY_RISK.equals(taskNameType)) {
            needHttp = true;
        }
        if (needHttp) {
            //TODO..
//            List<OutwardExtension> outwardExtensionList = outwardExtensionService.getOutwardExtensionList("changeFlowTempActiveCategory");
//            if (!CommonUtil.isEmpty(outwardExtensionList)) {
//                for (OutwardExtension ext : outwardExtensionList) {
//                    for (int i = 0; !CommonUtil.isEmpty(ext.getUrlList())
//                                    && i < ext.getUrlList().size(); i++ ) {
//                        tabMap = new HashMap<String, String>();
//                        tabMap.put("id", ext.getUrlList().get(i).getId());
//                        tabMap.put("title", ext.getUrlList().get(i).getDialogTitle());
//                        tabMap.put("href", ext.getUrlList().get(i).getOperateUrl());
//                        tabsList.add(tabMap);
//                    }
//                }
//            }
        }
        j.setObj(tabsList);
        return j;
    }

    /**
     * Description: <br>
     * 获取反推计划的标志
     *
     * @return
     * @see
     */
    @RequestMapping(params = "getBackChangePlanFlag")
    @ResponseBody
    private AjaxJson getBackChangePlanFlag(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        FeignJson switchStrJson = paramSwitchService.getSwitch(SwitchConstants.BACKCHANGEPLAN);
        String backChangePlanSwitchStr = switchStrJson.getObj().toString();
        if (CommonUtil.isEmpty(backChangePlanSwitchStr)) {
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 调用流程模板分解
     *
     * @param tptmpl
     * @param request
     * @param response
     */
    @RequestMapping(params = "templateResolve")
    @ResponseBody
    public AjaxJson templateResolve(TaskProcTemplateVo tptmpl, HttpServletRequest request,
                                    HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        String parentId = request.getParameter("parentId");
        TaskProcTemplateDto taskProcTemplate = rdFlowTaskFlowResolveService.getProcTemplateEntity(tptmpl.getId());
        try {
            if (!CommonUtil.isEmpty(parentId)) {
                boolean isSuccess = true;
                if(taskProcTemplate != null && StringUtils.isNotEmpty(taskProcTemplate.getId())) {
                    String templateId = taskProcTemplate.getId();
                    isSuccess = applyProcTemplateService.templateResolveForPlan(parentId, templateId, UserUtil.getCurrentUser().getId());
//                    isSuccess = taskFlowResolveService.templateResolveForPlan(parentId, templateId, UserUtil.getCurrentUser().getId());
                    j.setSuccess(isSuccess);
                }else {
                    isSuccess = applyProcTemplateService.templateResolveForPlan(parentId, "", UserUtil.getCurrentUser().getId());
//                    isSuccess = taskFlowResolveService.templateResolveForPlan(parentId, "", UserUtil.getCurrentUser().getId());
                    j.setSuccess(isSuccess);
                }

                if (!isSuccess) {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveFailure"));
                }
                else {
                    j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveSuccess"));
                }

            }else {
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveFailure");
                j.setSuccess(false);
                j.setMsg(message);
            }
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.flowResolveFailure");
            j.setSuccess(false);
            j.setMsg(message);
        }
        return j;
    }

    /**
     * Description: <br>
     * 1、输入：本地文档下载<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "fileDown")
    public void fileDown(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        ServletOutputStream out = null;
        try {
            String filePath = request.getParameter("filePath");
            String fileName = request.getParameter("fileName");
            is = JackrabbitUtil.downLoadFile(filePath);
            String contentType = "application/x-download";
            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("gb2312"), "iso8859-1"));
            out = response.getOutputStream();
            byte[] bytes = new byte[0xffff];
            int b = 0;
            while ((b = is.read(bytes, 0, 0xffff)) > 0) {
                out.write(bytes, 0, b);
            }
            is.close();
            out.flush();
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "updateInputsProjLibLink")
    @ResponseBody
    public AjaxJson updateInputsProjLibLink(HttpServletRequest request)
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
            InputsDto input = new InputsDto();
            List<RepFileDto> repList = new ArrayList<RepFileDto>();
            RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, fileId);
            repList.add(rep);
            List<ProjLibDocumentVo> voList = projLibService.getRepList(fileId, folderId, projectId,UserUtil.getCurrentUser().getId());
            if (!CommonUtil.isEmpty(voList)) {
                input.setId(rowId);
                input.setDocName(voList.get(0).getDocName());
                input.setOriginType("PROJECTLIBDOC");
                input.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                input.setDocId(voList.get(0).getBizId());
                input.setExt1(String.valueOf(voList.get(0).isDownload()));
                input.setExt2(String.valueOf(voList.get(0).isHistory()));
                input.setExt3(String.valueOf(voList.get(0).isDetail()));
                taskFlowResolveService.doAddInputsNew("", input,UserUtil.getCurrentUser().getId());
            }

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
    @RequestMapping(params = "setInputsPlanLink")
    @ResponseBody
    public AjaxJson setInputsPlanLink(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planId = request.getParameter("planId");
        String tempId = request.getParameter("tempId");
        String useObjectId = request.getParameter("useObjectId");
        String inputsName = request.getParameter("inputsName");
        try {
            InputsDto input = new InputsDto();
            PlanDto plan = planService.getPlanEntity(planId);
            List<ProjDocVo> projDocRelationList = getDocRelationList(plan);
            ProjDocVo projDoc = new ProjDocVo();
            if (!CommonUtil.isEmpty(projDocRelationList)) {
                for (ProjDocVo vo : projDocRelationList) {
                    if (vo.getDeliverableName().equals(inputsName)) {
                        projDoc = vo;
                        break;
                    }
                }
            }

            if (!CommonUtil.isEmpty(projDoc)) {
                input.setId(tempId);
                input.setDocName(projDoc.getDocName());
                input.setOriginType(PlanConstants.USEOBJECT_TYPE_PLAN);
                input.setOriginTypeExt(PlanConstants.DELIEVER_EN);
                input.setDocId(projDoc.getDocId());
                input.setOriginObjectId(plan.getId());
                input.setOriginDeliverablesInfoId(projDoc.getDeliverableId());
                input.setExt1(String.valueOf(projDoc.isDownload()));
                input.setExt2(String.valueOf(projDoc.isHavePower()));
                input.setExt3(String.valueOf(projDoc.isDetail()));
                taskFlowResolveService.doAddInputsNew("", input,UserUtil.getCurrentUser().getId());
            }

        }
        catch (Exception e) {

        }
        finally {
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
    @RequestMapping(params = "datagridlistForInnerInputs")
    @ResponseBody
    public void datagridlistForInnerInputs(HttpServletRequest request, HttpServletResponse response) {
        String parentPlanId = request.getParameter("parentPlanId");
        String cellId = request.getParameter("cellId");
        String useObjectId = request.getParameter("useObjectId");
        String ids = request.getParameter("ids");
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        // 最终的展示值：
        List<DeliveryStandardDto> deliveryStandardEndList = new ArrayList<DeliveryStandardDto>();
        // 查出所有的子节点的输出：
        List<DeliverablesInfoDto> allDeliverablesInfoList = deliverablesInfoService.getAllDeliverablesByUseObeject(parentPlanId);
        // 查出所有的启用的交付项名称库信息：
        Map<String, String> nameAndIdMap = new HashMap<String, String>();
        Map<String, String> nameAndNoMap = new HashMap<String, String>();
        Map<String, String> nameAndRemarkMap = new HashMap<String, String>();
        List<DeliveryStandardDto> allDeliveryStandardList = deliveryStandardService.searchUseableDeliveryStandards(new DeliveryStandardDto());
        if (!CommonUtil.isEmpty(allDeliveryStandardList)) {
            for (DeliveryStandardDto curDeliveryStandard : allDeliveryStandardList) {
                nameAndIdMap.put(curDeliveryStandard.getName(), curDeliveryStandard.getId());
                nameAndNoMap.put(curDeliveryStandard.getName(), curDeliveryStandard.getNo());
                nameAndRemarkMap.put(curDeliveryStandard.getName(),
                    curDeliveryStandard.getRemark());
            }
        }

        // 查出子计划：
        Map<String, String> planIdAndplanNameMap = new HashMap<String, String>();
        FeignJson planIdAndplanNameJson = planService.queryPlanIdAndNameMap(parentPlanId);
        if (planIdAndplanNameJson.isSuccess()) {
            Map<String, Object> map = new HashMap<>();
            map = planIdAndplanNameJson.getAttributes();
            planIdAndplanNameMap = (Map<String, String>)map.get("planIdAndplanNameMap");
        }

        // 找出前置计划：
        Map<String, String> preposePlanIdsMap = new HashMap<String, String>();
        PlanDto tempPlan = new PlanDto();
        tempPlan.setId(useObjectId);
        List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(tempPlan);
        for (PreposePlanDto curPreposePlan : preposePlanList) {
            preposePlanIdsMap.put(curPreposePlan.getPreposePlanId(),
                curPreposePlan.getPreposePlanId());
        }
        // 前置计划输出对应的交付项名称
        List<DeliveryStandardDto> deliveryStandardList1 = new ArrayList<DeliveryStandardDto>();
        // 非前置计划输出对应的交付项名称
        List<DeliveryStandardDto> deliveryStandardList2 = new ArrayList<DeliveryStandardDto>();

        for (DeliverablesInfoDto curDeliverablesInfo : allDeliverablesInfoList) {
            if (!curDeliverablesInfo.getUseObjectId().equals(useObjectId)) {
                if (!CommonUtil.isEmpty(name) || !CommonUtil.isEmpty(no)) {
                    if ((!CommonUtil.isEmpty(name) && !curDeliverablesInfo.getName().toUpperCase().contains(
                        name.toUpperCase()))
                        || (!CommonUtil.isEmpty(no) && !nameAndNoMap.get(
                            curDeliverablesInfo.getName()).toUpperCase().contains(no.toUpperCase()))) {
                        continue;
                    }
                }
                if (CommonUtil.isEmpty(ids)) {
                    DeliveryStandardDto curDeliveryStandard = new DeliveryStandardDto();
                    curDeliveryStandard.setId(UUIDGenerator.generate());
                    curDeliveryStandard.setDeliverId(curDeliverablesInfo.getId());
                    curDeliveryStandard.setNo(nameAndNoMap.get(curDeliverablesInfo.getName()));
                    curDeliveryStandard.setName(curDeliverablesInfo.getName());
                    curDeliveryStandard.setCellName(planIdAndplanNameMap.get(curDeliverablesInfo.getUseObjectId()));
                    curDeliveryStandard.setOriginObjectId(curDeliverablesInfo.getUseObjectId());
                    curDeliveryStandard.setRemark(nameAndRemarkMap.get(curDeliverablesInfo.getName()));
                    if (!CommonUtil.isEmpty(preposePlanIdsMap)
                        && !CommonUtil.isEmpty(preposePlanIdsMap.get(curDeliverablesInfo.getUseObjectId()))) {
                        curDeliveryStandard.setIsPreOutput("true");
                        deliveryStandardList1.add(curDeliveryStandard);
                    }
                    else {
                        curDeliveryStandard.setIsPreOutput("false");
                        deliveryStandardList2.add(curDeliveryStandard);
                    }
                }
                else if (!ids.contains(curDeliverablesInfo.getId())) {
                    DeliveryStandardDto curDeliveryStandard = new DeliveryStandardDto();
                    curDeliveryStandard.setId(UUIDGenerator.generate());
                    curDeliveryStandard.setDeliverId(curDeliverablesInfo.getId());
                    curDeliveryStandard.setNo(nameAndNoMap.get(curDeliverablesInfo.getName()));
                    curDeliveryStandard.setName(curDeliverablesInfo.getName());
                    curDeliveryStandard.setCellName(planIdAndplanNameMap.get(curDeliverablesInfo.getUseObjectId()));
                    curDeliveryStandard.setOriginObjectId(curDeliverablesInfo.getUseObjectId());
                    curDeliveryStandard.setRemark(nameAndRemarkMap.get(curDeliverablesInfo.getName()));
                    if (!CommonUtil.isEmpty(preposePlanIdsMap)
                        && !CommonUtil.isEmpty(preposePlanIdsMap.get(curDeliverablesInfo.getUseObjectId()))) {
                        curDeliveryStandard.setIsPreOutput("true");
                        deliveryStandardList1.add(curDeliveryStandard);
                    }
                    else {
                        curDeliveryStandard.setIsPreOutput("false");
                        deliveryStandardList2.add(curDeliveryStandard);
                    }
                }
            }

        }
        deliveryStandardList1.addAll(deliveryStandardList2);

        String json = com.alibaba.fastjson.JSONArray.toJSONString(deliveryStandardList1);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + deliveryStandardList1.size()
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
     * 获取交付物名称库列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "datagridlistForInnerChangeInputs")
    @ResponseBody
    public void datagridlistForInnerChangeInputs(HttpServletRequest request,
                                                 HttpServletResponse response) {
        String parentPlanId = request.getParameter("parentPlanId");
        String cellId = request.getParameter("cellId");
        String useObjectId = request.getParameter("useObjectId");
        String ids = request.getParameter("ids");
        String name = request.getParameter("name");
        String no = request.getParameter("no");
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        List<FlowTaskPreposeVo> flowTaskPreposeList = (List<FlowTaskPreposeVo>)request.getSession().getAttribute(
            PlanConstants.FLOWTASK_PREPOSE_LIST_KEY + parentPlanId);

        // 查出子计划：
        Map<String, String> planIdAndplanNameMap = new HashMap<String, String>();

        // 查出所有的子节点的输出：
        List<FlowTaskDeliverablesInfoVo> allDeliverablesInfoList = new ArrayList<FlowTaskDeliverablesInfoVo>();
        for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
            List<FlowTaskDeliverablesInfoVo> deliverablesInfoVoList = curFlowTaskVo.getOutputList();
            allDeliverablesInfoList.addAll(deliverablesInfoVoList);
            planIdAndplanNameMap.put(curFlowTaskVo.getId(), curFlowTaskVo.getPlanName());
        }
        // 查出所有的启用的交付项名称库信息：
        Map<String, String> nameAndIdMap = new HashMap<String, String>();
        Map<String, String> nameAndNoMap = new HashMap<String, String>();
        Map<String, String> nameAndRemarkMap = new HashMap<String, String>();
        List<DeliveryStandardDto> allDeliveryStandardList = deliveryStandardService.searchUseableDeliveryStandards(new DeliveryStandardDto());
        if (!CommonUtil.isEmpty(allDeliveryStandardList)) {
            for (DeliveryStandardDto curDeliveryStandard : allDeliveryStandardList) {
                nameAndIdMap.put(curDeliveryStandard.getName(), curDeliveryStandard.getId());
                nameAndNoMap.put(curDeliveryStandard.getName(), curDeliveryStandard.getNo());
                nameAndRemarkMap.put(curDeliveryStandard.getName(),
                    curDeliveryStandard.getRemark());
            }
        }

        // 找出前置计划：
        Map<String, String> preposePlanIdsMap = new HashMap<String, String>();
        if (!CommonUtil.isEmpty(allDeliveryStandardList)) {
            for (FlowTaskPreposeVo curFlowTaskPreposeVo : flowTaskPreposeList) {
                if (curFlowTaskPreposeVo.getFlowTaskId().equals(useObjectId)) {
                    preposePlanIdsMap.put(curFlowTaskPreposeVo.getPreposeId(),
                        curFlowTaskPreposeVo.getPreposeId());
                }
            }
        }
        // 前置计划输出对应的交付项名称
        List<DeliveryStandardDto> deliveryStandardList1 = new ArrayList<DeliveryStandardDto>();
        // 非前置计划输出对应的交付项名称
        List<DeliveryStandardDto> deliveryStandardList2 = new ArrayList<DeliveryStandardDto>();

        for (FlowTaskDeliverablesInfoVo curDeliverablesInfo : allDeliverablesInfoList) {
            if (!curDeliverablesInfo.getUseObjectId().equals(useObjectId)) {
                if (!CommonUtil.isEmpty(name) || !CommonUtil.isEmpty(no)) {
                    if ((!CommonUtil.isEmpty(name) && !curDeliverablesInfo.getName().toUpperCase().contains(
                        name.toUpperCase()))
                        || (!CommonUtil.isEmpty(no) && !nameAndNoMap.get(
                            curDeliverablesInfo.getName()).toUpperCase().contains(no.toUpperCase()))) {
                        continue;
                    }
                }
                if (CommonUtil.isEmpty(ids)) {
                    DeliveryStandardDto curDeliveryStandard = new DeliveryStandardDto();
                    curDeliveryStandard.setId(UUIDGenerator.generate());
                    curDeliveryStandard.setDeliverId(curDeliverablesInfo.getId());
                    curDeliveryStandard.setNo(nameAndNoMap.get(curDeliverablesInfo.getName()));
                    curDeliveryStandard.setName(curDeliverablesInfo.getName());
                    curDeliveryStandard.setCellName(planIdAndplanNameMap.get(curDeliverablesInfo.getUseObjectId()));
                    curDeliveryStandard.setOriginObjectId(curDeliverablesInfo.getUseObjectId());
                    curDeliveryStandard.setRemark(nameAndRemarkMap.get(curDeliverablesInfo.getName()));
                    if (!CommonUtil.isEmpty(preposePlanIdsMap)
                        && !CommonUtil.isEmpty(preposePlanIdsMap.get(curDeliverablesInfo.getUseObjectId()))) {
                        curDeliveryStandard.setIsPreOutput("true");
                        deliveryStandardList1.add(curDeliveryStandard);
                    }
                    else {
                        curDeliveryStandard.setIsPreOutput("false");
                        deliveryStandardList2.add(curDeliveryStandard);
                    }
                }
                else if (!ids.contains(curDeliverablesInfo.getId())) {
                    DeliveryStandardDto curDeliveryStandard = new DeliveryStandardDto();
                    curDeliveryStandard.setId(UUIDGenerator.generate());
                    curDeliveryStandard.setDeliverId(curDeliverablesInfo.getId());
                    curDeliveryStandard.setNo(nameAndNoMap.get(curDeliverablesInfo.getName()));
                    curDeliveryStandard.setName(curDeliverablesInfo.getName());
                    curDeliveryStandard.setCellName(planIdAndplanNameMap.get(curDeliverablesInfo.getUseObjectId()));
                    curDeliveryStandard.setOriginObjectId(curDeliverablesInfo.getUseObjectId());
                    curDeliveryStandard.setRemark(nameAndRemarkMap.get(curDeliverablesInfo.getName()));
                    if (!CommonUtil.isEmpty(preposePlanIdsMap)
                        && !CommonUtil.isEmpty(preposePlanIdsMap.get(curDeliverablesInfo.getUseObjectId()))) {
                        curDeliveryStandard.setIsPreOutput("true");
                        deliveryStandardList1.add(curDeliveryStandard);
                    }
                    else {
                        curDeliveryStandard.setIsPreOutput("false");
                        deliveryStandardList2.add(curDeliveryStandard);
                    }
                }
            }

        }
        deliveryStandardList1.addAll(deliveryStandardList2);

        String json = com.alibaba.fastjson.JSONArray.toJSONString(deliveryStandardList1);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + deliveryStandardList1.size()
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

    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInnerInputs")
    @ResponseBody
    public AjaxJson doAddInnerInputs(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        String originTypeExt = request.getParameter("type");
        String names = request.getParameter("deliverNames");
        String originDeliverablesInfoIds = request.getParameter("deliverIds");
        String originObjectIds = request.getParameter("originObjectIds");
        try {
            String[] nameArray = names.split(",");
            String[] originDeliverablesInfoIdArray = originDeliverablesInfoIds.split(",");
            String[] originObjectIdArray = originObjectIds.split(",");
            if (nameArray.length > 0) {
                for (int i = 0; i < nameArray.length; i++ ) {
                    InputsDto input = new InputsDto();
                    input.setOriginType("PLAN");
                    input.setOriginTypeExt(originTypeExt);
                    input.setOriginObjectId(originObjectIdArray[i]);
                    input.setOriginDeliverablesInfoId(originDeliverablesInfoIdArray[i]);
                    input.setName(nameArray[i]);
                    input.setUseObjectType(useObjectType);
                    input.setUseObjectId(useObjectId);
                    taskFlowResolveService.doAddInputsNew("", input,UserUtil.getCurrentUser().getId());
                }
            }

        }
        catch (Exception e) {

        }
        finally {
            return j;
        }
    }

    @SuppressWarnings("finally")
    @RequestMapping(params = "doAddInnerChangeInputs")
    @ResponseBody
    public AjaxJson doAddInnerChangeInputs(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSUserDto currentUser = UserUtil.getCurrentUser();
        String useObjectId = request.getParameter("useObjectId");
        String useObjectType = request.getParameter("useObjectType");
        String originTypeExt = request.getParameter("type");
        String names = request.getParameter("deliverNames");
        String originDeliverablesInfoIds = request.getParameter("deliverIds");
        String originObjectIds = request.getParameter("originObjectIds");
        String originObjectNames = request.getParameter("originObjectNames");
        String parentPlanId = request.getParameter("parentPlanId");
        try {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            // 找到对应的变更节点，并添加输出：
            for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                if (curFlowTaskVo.getId().equals(useObjectId)) {
                    String[] nameArray = names.split(",");
                    String[] originDeliverablesInfoIdArray = originDeliverablesInfoIds.split(",");
                    String[] originObjectIdArray = originObjectIds.split(",");
                    String[] originObjectNameArray = originObjectNames.split(",");
                    if (nameArray.length > 0) {
                        for (int i = 0; i < nameArray.length; i++ ) {
                            FlowTaskInputsVo input = new FlowTaskInputsVo();
                            String uuid = PlanConstants.PLAN_CREATE_UUID
                                          + UUID.randomUUID().toString();
                            input.setId(uuid);
                            input.setOriginType("PLAN");
                            input.setOriginTypeExt(originTypeExt);
                            input.setOriginObjectId(originObjectIdArray[i]);
                            input.setOriginObjectName(originObjectNameArray[i]);
                            input.setOriginDeliverablesInfoId(originDeliverablesInfoIdArray[i]);
                            input.setCreateBy(currentUser.getId());
                            input.setCreateFullName(currentUser.getRealName());
                            input.setCreateName(currentUser.getUserName());
                            input.setCreateTime(new Date());
                            input.setName(nameArray[i]);
                            input.setUseObjectType(useObjectType);
                            input.setUseObjectId(useObjectId);
                            curFlowTaskVo.getInputList().add(input);

                        }
                    }

                    break;
                }

            }
            request.getSession().setAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId, changeFlowTaskList);
        }
        catch (Exception e) {

        }
        finally {
            return j;
        }
    }

    /**
     * 父节点下是否含有输入没有来源
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "isHaveInputsNotFromInfo")
    @ResponseBody
    public AjaxJson isHaveInputsNotFromInfo(HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String parentPlanId = request.getParameter("parentPlanId");
        try {
            boolean flag = false;
            List<InputsDto> inputList = inputsService.queryInputsDetailListForString(parentPlanId);
            for (InputsDto i : inputList) {
                if (CommonUtil.isEmpty(i.getOriginType())
                    && PlanConstants.DELIEVER_EN.equals(i.getOriginTypeExt())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                j.setSuccess(false);
            }
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
     * 父节点下是否含有输入没有来源
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "isHaveChangeInputsNotFromInfo")
    @ResponseBody
    public AjaxJson isHaveChangeInputsNotFromInfo(HttpServletRequest request)
        throws ParseException {
        AjaxJson j = new AjaxJson();
        String failMessage = "";
        String failMessageCode = "";
        String parentPlanId = request.getParameter("parentPlanId");
        try {
            boolean flag = false;
            List<FlowTaskInputsVo> changeOutInputList = new ArrayList<FlowTaskInputsVo>();
            // List<Inputs> inputList = inputsService.queryInputsDetailList(parentPlanId);
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);

            for (FlowTaskVo flowtask : changeFlowTaskList) {
                List<FlowTaskInputsVo> curOutInputList = flowtask.getInputList();
                if (!CommonUtil.isEmpty(curOutInputList)) {
                    for (FlowTaskInputsVo curInputVo : curOutInputList) {
                        if (!CommonUtil.isEmpty(curInputVo)
                            && !CommonUtil.isEmpty(curInputVo.getOriginTypeExt())
                            && PlanConstants.DELIEVER_EN.equals(curInputVo.getOriginTypeExt())) {
                            curInputVo.setUseObjectName(flowtask.getPlanName());
                            changeOutInputList.add(curInputVo);
                        }
                    }
                }
            }
            for (FlowTaskInputsVo i : changeOutInputList) {
                if (CommonUtil.isEmpty(i.getOriginType())
                    && PlanConstants.DELIEVER_EN.equals(i.getOriginTypeExt())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                j.setSuccess(false);
            }
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
     * Description: <br>
     * 获取数据库中计划的id
     *
     * @return
     * @see
     */
    @RequestMapping(params = "getPlanIdByChangeInputUseObjectId")
    @ResponseBody
    private AjaxJson getPlanIdByChangeInputUseObjectId(HttpServletRequest request,
                                                       HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String useObjectId = request.getParameter("useObjectId");
        String parentPlanId = request.getParameter("parentPlanId");
        if (!CommonUtil.isEmpty(useObjectId) && !CommonUtil.isEmpty(parentPlanId)) {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                if (useObjectId.equals(curFlowTaskVo.getId())) {
                    j.setObj(curFlowTaskVo.getPlanId());
                    break;
                }
            }
        }
        return j;
    }

    /**
     * 查询名称库键值对
     *
     * @param request
     * @param response
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "standardValue")
    @ResponseBody
    public void standardValue(HttpServletRequest request, HttpServletResponse response) {
        String message = "没有名称库";
        try {
            NameStandardDto nameStandard = new NameStandardDto();
            nameStandard.setStopFlag("启用");
            List<NameStandardDto> list = nameStandardService.searchNameStandards(nameStandard);
            String jonStr = JsonUtil.getCodeTitleJson(list, "id", "name");
            request.getSession().setAttribute("standardValue", jonStr);
            jonStr = jonStr.replaceAll("'", "\"");
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动并提交变更申请流程
     *
     * @param planownerApplychangeInfo
     * @param leader
     *            审批人ID
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "startChangeApplyProcess")
    @ResponseBody
    public FeignJson startChangeApplyProcess(PlanownerApplychangeInfoDto planownerApplychangeInfo,
                                            String leader, HttpServletRequest request) {
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson j = taskFlowResolveService.startChangeApplyProcess(planownerApplychangeInfo,leader,userId);
        return j;
    }

    /**
     * 变更申请提交审批跳转
     *
     * @params
     */
    @RequestMapping(params = "goChangeApplyView")
    public ModelAndView goChangeApplyView(HttpServletRequest req) {
        String taskNumber = req.getParameter("taskNumber");
        PlanownerApplychangeInfoDto p = planService.getPlanownerApplychangeInfo(taskNumber);
        req.setAttribute("planownerApplychangeInfo_", p);
        BusinessConfigDto b = nameStandardService.getBusinessConfig(p.getChangeType());
        req.setAttribute("changetypeName", b.getName());
        req.getSession().setAttribute("planId", p.getPlanId());
        PlanDto plan = planService.getPlanEntity(p.getPlanId());
        req.getSession().setAttribute("planName", plan.getPlanName());
        return new ModelAndView("com/glaway/ids/pm/project/task/applyChangeView");
    }

    /**
     * 查询活动名称类型
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "getTaskNameTypeList")
    @ResponseBody
    private void getTaskNameTypeList(HttpServletRequest request, HttpServletResponse response) {
        // 给计划类型设置初始值
        List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageFeign.getAllActivityTypeManage(true);
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
     * 通过cellid 和 父计划id 判断当前用户是否是计划的负责人
     * @param req
     */
    @RequestMapping(params = "isPlanOwner")
    @ResponseBody
    public FeignJson isPlanOwner (HttpServletRequest req) {
        FeignJson j = new FeignJson();
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        TSUserDto curUser = ResourceUtil.getCurrentUser();
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        plan.setCellId(cellId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        PlanDto p = new PlanDto();
        if (list != null && list.size() > 0) {
            p = list.get(0);
            if (!p.getOwner().equals(curUser.getId())) {
                j.setSuccess(false);
            }
        }
        return j;
    }

    /**
     * 通过cellid 和 父计划id 判断当前用户是否是计划的负责人
     * @param req
     */
    @RequestMapping(params = "getPlanByCellIdAndParentId")
    @ResponseBody
    public FeignJson getPlanByCellIdAndParentId (HttpServletRequest req) {
        FeignJson j = new FeignJson();
        String cellId = req.getParameter("cellId");
        String parentPlanId = req.getParameter("parentPlanId");
        PlanDto plan = new PlanDto();
        plan.setParentPlanId(parentPlanId);
        plan.setCellId(cellId);
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
        PlanDto p = new PlanDto();
        if (list != null && list.size() > 0) {
            p = list.get(0);
            j.setObj(p);
        }
        return j;
    }

    /**
     * 通过责任人id获取其部门
     *
     * @param request
     * @return
     * @throws ParseException
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "setDeptNameByOwnerId")
    @ResponseBody
    public AjaxJson setDeptNameByOwnerId(HttpServletRequest request)
            throws ParseException {
        AjaxJson j = new AjaxJson();
        String ownerId = request.getParameter("ownerId");
        try {
            if (!CommonUtil.isEmpty(ownerId)) {
                List<TSDepartDto> deptListInfo = deptService.getTSDepartByuserId(appKey, ownerId);
                if(deptListInfo.size()>0) {
                    TSDepartDto curDeptInfo =  deptListInfo.get(0);
                    if(!CommonUtil.isEmpty(curDeptInfo)) {
                        j.setObj(curDeptInfo.getDepartname());
                    }
                }
            }
        }
        catch (Exception e) {
        }
        finally {
            return j;
        }
    }

    /**
     * 获取分类树
     * @param request
     * @param response
     */
    @RequestMapping(params = "getProcCategoryTree")
    @ResponseBody
    public void getProcCategoryTree(HttpServletRequest request, HttpServletResponse response){
        FeignJson j = rdFlowTaskFlowResolveService.getProcCategoryTree();
        String json = "";
        if (j.isSuccess()) {
            json = j.getObj() == null ? "" : j.getObj().toString();
        }
        TagUtil.ajaxResponse(response,json);
    }

    /**
     * 获取查询数据
     *
     * @param req
     * @param response
     */
    @RequestMapping(params = "conditionSearch")
    @ResponseBody
    public void conditionSearch(HttpServletRequest req, HttpServletResponse response) {
        String procCategoryId = req.getParameter("procCategoryId");
        //fromType来源数据（qiyong）:
        String fromType = req.getParameter("fromType");
        Map<String, Object> params = new HashMap<String, Object>();
        if (com.glaway.foundation.common.util.StringUtil.isNotEmpty(req.getParameter("createName"))) {
            String createName = req.getParameter("createName");
            params.put("createName", createName);
        }
        if (com.glaway.foundation.common.util.StringUtil.isNotEmpty(req.getParameter("creatorDept"))) {
            String creatorDept = req.getParameter("creatorDept");
            params.put("creatorDept", creatorDept);
        }
        if (com.glaway.foundation.common.util.StringUtil.isNotEmpty(req.getParameter("procTmplNames"))) {
            String procTmplNames = req.getParameter("procTmplNames");
            params.put("procTmplNames", procTmplNames);
        }

        List<ConditionVO> conditionLst = RequestMapUtil.getQueryCondition(req);

        for (ConditionVO vo : conditionLst) {
            if (vo.getKey().equals("procTmplNames")) {
                vo.setValue(null);
            }
        }
        ConditionVO conditionVO = new ConditionVO();
        conditionVO.setCondition("eq");
        conditionVO.setKey("createOrgId");
        conditionVO.setValue(ResourceUtil.getCurrentUserOrg().getId());
        conditionLst.add(conditionVO);
        ConditionVO conditionVO1 = new ConditionVO();
        conditionVO1.setCondition("eq");
        conditionVO1.setKey("procCategoryId");
        conditionVO1.setValue(procCategoryId);
        conditionLst.add(conditionVO1);

        if(!CommonUtil.isEmpty(fromType) && "qiyong".equals(fromType)) {
            ConditionVO conditionVO2 = new ConditionVO();
            conditionVO2.setCondition("in");
            conditionVO2.setKey("TaskProcTemplate.bizCurrent");
            conditionVO2.setValue(","+fromType);
            conditionLst.add(conditionVO2);
        }
        params.put("conditionLst", conditionLst);

        Map<String, Object> listMap = (Map<String, Object>)rdFlowTaskFlowResolveService.getConditionSearch(params);
        Object listCountObj =  listMap.get("listCount");
        String count = JSON.toJSONString(listCountObj).toString();
        Object taskProcTemplateDtoListObj =  listMap.get("taskProcTemplateDtoList");
        List<TaskProcTemplateDto> list = JSON.parseObject(JSON.toJSONString(taskProcTemplateDtoListObj), new TypeReference<List<TaskProcTemplateDto>>(){});
        for (TaskProcTemplateDto t : list) {
            MyStartedTaskDto myStartedTask = new MyStartedTaskDto();
            if(!CommonUtil.isEmpty(t.getCreateBy())) {
                TSUserDto user = userService.getUserByUserId(t.getCreateBy());
                t.setCreator(user);
                if(CommonUtil.isEmpty(t.getCreatorDept())){
                    if(!CommonUtil.isEmpty(user.getTSDepart())){
                        t.setCreatorDept(user.getTSDepart().getDepartname());
                    }
                }
            }
            if(CommonUtil.isEmpty(t.getActivityCount())) {
                t.setActivityCount("0");
            }
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        String json = gson.toJson(list);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }



}
