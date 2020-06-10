package com.glaway.ids.rdtask.task.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.outwardextension.OutwardExtensionDto;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;
import com.glaway.foundation.fdk.dev.service.FeignOutwardExtensionService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.calendar.FeignCalendarService;
import com.glaway.foundation.fdk.dev.service.rep.FeignRepService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.service.LifeCycleStatusServiceI;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.common.constant.PluginConstants;
import com.glaway.ids.common.constant.TaskProcConstants;
import com.glaway.ids.common.service.PluginValidateServiceI;
import com.glaway.ids.config.constant.CommonConstants;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.NameStandardSwitchConstants;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.SwitchConstants;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import com.glaway.ids.planGeneral.plantabtemplate.dto.DataSourceObjectDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.dto.TabTemplateDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.DataSourceObjectFeign;
import com.glaway.ids.planGeneral.plantabtemplate.feign.TabTemplateFeign;
import com.glaway.ids.planGeneral.plantabtemplate.service.TabTemplateServiceI;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowWebRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.TaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jodd.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 任务流程变更（ V4.0微服务改造）
 * @author wqb
 * @date 2015-06-18 15:08:40
 * @version V4.0微服务改造
 */
@Controller
@RequestMapping("/applyFlowResolveForChangeController")
public class ApplyFlowResolveForChangeController extends BaseController {
    private static final OperationLog log = BaseLogFactory.getOperationLog(ApplyFlowResolveForChangeController.class);

    /**
     * 用户管理接口
     */
    @Autowired
    private FeignUserService userService;
    
    /**
     * 项目计划接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;
    
    /**
     * 项目参数接口
     */
    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;
    
    /**
     * 流程任务分解接口
     */
    @Autowired
    private RdFlowTaskFlowResolveRemoteFeignServiceI rdFlowTaskFlowResolveService;
    
    /**
     * 流程分解接口
     */
    @Autowired
    private TaskFlowResolveRemoteFeignServiceI taskFlowResolveService;
    
    /**
     * 计划前置接口
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;
    
    /**
     * rdFlowWeb接口
     */
    @Autowired
    private RdFlowWebRemoteFeignServiceI rdFlowWebService;
    
    /**
     * 名称库接口
     */
    @Autowired

    private NameStandardRemoteFeignServiceI nameStandardService;
    
    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;
    
    /** 业务对象 */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;
    
    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;
    
    /**
     * 用户部门接口
     */
    @Autowired
    private FeignDepartService deptService;
    
    @Value(value="${spring.application.name}")
    private String appKey;
    
    @Autowired
    private FeignSystemService feignSystemService;
    
    /**
     * 插件验证接口服务
     */
    @Autowired
    public PluginValidateServiceI pluginValidateService;
    
    @Autowired
    private FeignCalendarService calendarService;
    
    /**
     * 生命周期状态服务接口
     */
    @Autowired
    private LifeCycleStatusServiceI lifeCycleStatusService;
    
    /**
     * 输出接口
     */
    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;
    
    /**
     * repFile接口
     */
    @Autowired
    private FeignRepService repFileService;
    
    /**
     * 输入接口
     */
    @Autowired
    private InputsRemoteFeignServiceI inputsService;
    
    @Autowired
    private FeignOutwardExtensionService outwardExtensionService;
    
    @Autowired
    private TabTemplateFeign tabTemplateFeign;
    
    //元数据属性Feign
    @Autowired
    private DataSourceObjectFeign dataSourceObjectFeign;
    
    @Autowired
    private ActivityTypeManageFeign activityTypeManageEntityService;
    
    
    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;
    
    //页签模版管理Service
    @Autowired
    private TabTemplateServiceI tabTemplateServiceImpl;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;
    
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
                if (!PlanConstants.PLAN_FINISH.equals(parent.getBizCurrent())) {
                    PlanDto p = new PlanDto();
                    p.setParentPlanId(planId);
                    List<PlanDto> childList = planService.queryPlanList(p, 1, 10, false);
                    if(childList.size()>0) {
                        Boolean isNotEditing = false;
                        for (PlanDto child : childList) {
                            if (!PlanConstants.PLAN_EDITING.equals(child.getBizCurrent())) {
                                isNotEditing = true;
                                break;
                            }
                        }
                        if(!isNotEditing) {
                            status = PlanConstants.PLAN_EDITING; 
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
     * 流程分解编辑器 
     * @param plan
     * @param request
     * @return 
     * @see
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

//            RDTaskVO rdTaskVO = rdFlowTaskFlowResolveService.getXmlbyPlanId(plan.getId());
//            String voXml = rdTaskVO.getFlowResolveXml();

            PlanDto parent = planService.getPlanEntity(plan.getId());
            flowTaskParent.setId(PlanConstants.PLAN_CREATE_UUID + UUID.randomUUID().toString());
            flowTaskParent.setParentId(parent.getId());
            flowTaskParent.setFlowResolveXml(parent.getFlowResolveXml());
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
           
            FeignJson aFeignJson = rdFlowWebService.getClassesPathFromRdflow();        
            String temlXml = "";
            String path = "";
            path = aFeignJson.getObj().toString()
                + File.separator
                + "webpage/com/glaway/ids/rdtask/workFlow/examples/editors/diagrams/flowtaskTemp";

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
            
            try {
                fu.saveFile(path, userId + "_tt_" + newdate + ".txt", temlXml);
            }
            catch (Exception e) {
                e.printStackTrace();
                message = I18nUtil.getValue("com.glaway.ids.pm.rdtask.flowResolve.fileCreateFailure");
                j.setSuccess(false);
            }
            
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
    
    /**获取变更前置计划的list
     * @param parent
     * @param changeFlowTaskList
     * @return
     */
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
                    org.dom4j.Element elm = (org.dom4j.Element)it.next();
                    String cellId = elm.attributeValue("id");
                    org.dom4j.Element mxCell = elm.element("mxCell");
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
        String enterType = request.getParameter("enterType");
        request.setAttribute("enterType", enterType);
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
    @RequestMapping(params = "goChangeTab")
    private ModelAndView goChangeTab(PlanDto plan, HttpServletRequest req,
                                     HttpServletResponse response) {
        ModelAndView mav = null;
        String enterType = req.getParameter("enterType");
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
        String dictCode = "activeCategory";
        Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
        List<TSTypeDto> types = tsMap.get(dictCode);       
//        List<TSType> types = TSTypegroup.allTypes.get(dictCode.toLowerCase());
        if (!"false".equals(isStandard)) {
            if (StringUtils.isEmpty(info.getTaskNameType())) {
                if (!CommonUtil.isEmpty(types)) {
//                    info.setTaskNameType(types.get(0).getTypecode());
                    info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
                }
            }
        }
        else {
            if (!CommonUtil.isEmpty(types)) {
//                info.setTaskNameType(types.get(0).getTypecode());
                info.setTaskType(PlanConstants.PLAN_TYPE_FLOW);
            }
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
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr3);
        mav = new ModelAndView("com/glaway/ids/pm/project/task/changeFlowTaskCellTab-nodeInfo"); // 节点信息
        mav.addObject("isStandard", isStandard);
        NameStandardDto nameStandard = new NameStandardDto();
        nameStandard.setStopFlag("启用");
        nameStandard.setName(info.getPlanName());
        List<NameStandardDto> standList = nameStandardService.searchNameStandards(nameStandard);
        if (!CommonUtil.isEmpty(standList)) {
            mav.addObject("nameStandardId", standList.get(0).getId());
        }
        else {
            mav.addObject("nameStandardId", "");
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

        boolean klmFlag = getKlmPluginValid();
        mav.addObject("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
        mav.addObject("cellId", cellId);
        mav.addObject("parentPlanId", parentPlanId);
        mav.addObject("projectId", projectId);
        mav.addObject("isEnableFlag", isEnableFlag);
        mav.addObject("klmFlag", klmFlag);
        mav.addObject("enterType", enterType);
        return mav;
    }

    private boolean getKlmPluginValid() {
        return pluginValidateService.isValidatePlugin(PluginConstants.KLM_PLUGIN_NAME);
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
     * 流程任务加入编译器
     * @param temp
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
     *
     * @param cellIds
     * @param changeFlowTaskList
     * @param flowTaskPreposeList
     * @return
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
                        child.setPlanStartTime(null);
                        child.setPlanEndTime(null);
                        flowTaskList.add(child);
                    }
                }
                flag = true;
            }
        }
        return flowTaskList;
    }


    /**
     *
     * @param parentPlanId
     * @param cellContact
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
     * @return
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
     *
     * @param changeFlowTaskConnectList
     * @param changeFlowTaskList
     * @param flowTaskPreposeList
     * @return
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
     *
     * @param flowTaskParent
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
     * @return
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
                        if (StringUtils.isNotEmpty(parentProject.getProjectTimeType())) {
                            if (ProjectConstants.WORKDAY.equals(parentProject.getProjectTimeType())) {
                                info.setPlanEndTime(com.glaway.foundation.common.util.DateUtil.nextWorkDay(
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
     * @param preposeId
     * @param backPlans
     * @param changeFlowTaskList
     * @param changeFlowTaskConnectList
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
        Project project = null;
        if(!CommonUtil.isEmpty(prepose.getProjectId())) {
            project = projectService.getProjectEntity(prepose.getProjectId());
        }
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
                            startTime = com.glaway.foundation.common.util.DateUtil.nextWorkDay(
                                proposeEndTime, 1);
                            if (info.getPlanStartTime() == null
                                || startTime.getTime() > info.getPlanStartTime().getTime()) {
                                info.setPlanStartTime(startTime);
                                Date date = (Date)info.getPlanStartTime().clone();
                                info.setPlanEndTime(com.glaway.foundation.common.util.DateUtil.nextWorkDay(
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
            1.0).create();
        DataGridReturn data = new DataGridReturn(changeInputList.size(), changeInputList);
        String json = gson.toJson(data);
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
        if (projDocRelation.getRepFile() == null && !CommonUtil.isEmpty(projDocRelation.getDocId())) {
            RepFileDto rep = repFileService.getRepFileByRepFileId(appKey, projDocRelation.getDocId());  
            projDocRelation.setRepFile(rep);
        }
        projDocVo.setDocName(projDocRelation.getRepFile().getFileName());
        projDocVo.setVersion(projDocRelation.getRepFile().getBizVersion());
        String approveStatus = lifeCycleStatusService.getTitleByPolicyIdAndName(
            projDocRelation.getRepFile().getPolicy().getId(),
            projDocRelation.getRepFile().getBizCurrent());
        projDocVo.setSecurityLevel(projDocRelation.getRepFile().getSecurityLevel());
        projDocVo.setStatus(approveStatus);
        return projDocVo;
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
        String cellId = request.getParameter("cellId");
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
                    if (curFlowTaskVo.getCellId().equals(cellId)) {
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
                    if (curFlowTaskVo.getCellId().equals(cellId)) {
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
        String cellId = request.getParameter("cellId");
        String parentPlanId = request.getParameter("parentPlanId");
        if (!CommonUtil.isEmpty(cellId) && !CommonUtil.isEmpty(parentPlanId)) {
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            for (FlowTaskVo curFlowTaskVo : changeFlowTaskList) {
                if (cellId.equals(curFlowTaskVo.getCellId())) {
                    j.setObj(curFlowTaskVo.getPlanId());
                    break;
                }
            }
        }
        return j;
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
                if (f.getCellId().equals(output.getCellId())) {
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
     * 获取
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "getRealPlanId")
    @ResponseBody
    public AjaxJson getRealPlanId(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        try {
            String parentPlanId = request.getParameter("parentPlanId");
            String cellId = request.getParameter("cellId");
            List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
                PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
            if(changeFlowTaskList.size()>0) {
                for(FlowTaskVo vo : changeFlowTaskList) {
                    if(cellId.equals(vo.getCellId())) {
                        j.setObj(vo.getPlanId());
                    }
                }
            }
            j.setSuccess(true);
        }
        catch (Exception e) {         
            throw new GWException(GWConstants.ERROR_2003, e);
        }
        finally {
            return j;
        }
    }
      
    /**
     * 功能描述：待办或其他查看流程变更编辑器节点信息
     * @return ModelAndView
     */
    @RequestMapping(params = "goTabCommonDetailChange")
    public ModelAndView goTabCommonDetailChange(HttpServletRequest request) {
        String projectId = request.getParameter("id");
        String tabId = request.getParameter("tabId");
        String title = request.getParameter("title"); 
        String parentPlanId = request.getParameter("parentPlanId");
        String isEnableFlag = request.getParameter("isEnableFlag");
        String fromDetailType = request.getParameter("fromDetailType");
        String onlyReadonly = request.getParameter("onlyReadonly");
        String showProjectInfo = request.getParameter("showProjectInfo");     
        String showBasicButtom = request.getParameter("showBasicButtom");
        String isStandard = request.getParameter("isStandard");
        String cellId = request.getParameter("cellId");
        
        
        request.setAttribute("showBasicButtom",showBasicButtom);
        if(!CommonUtil.isEmpty(onlyReadonly)) {
            request.setAttribute("onlyReadonly",onlyReadonly);
        }
        String tabTemplateCode = "";
        FlowTaskParentVo flowTaskParentVo = (FlowTaskParentVo)request.getSession().getAttribute(
            PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId);
        
        FlowTaskVo plan = null;
        List<FlowTaskVo> changeFlowTaskList = (List<FlowTaskVo>)request.getSession().getAttribute(
            PlanConstants.CHANGE_FLOWTASK_LIST_KEY + parentPlanId);
        for(FlowTaskVo flowTaskVo :changeFlowTaskList) {
            if(flowTaskVo.getCellId().equals(cellId)) {
                plan = flowTaskVo;
                break;
            }
        }
        
//        PlanDto plan = planService.getPlanEntity(planId); 
        if(!CommonUtil.isEmpty(plan)) {
            String index = request.getParameter("index");  
            request.setAttribute("index",index);
            //进入方式：0：编辑，2：查看
            String enterType = request.getParameter("enterType");
            request.setAttribute("enterType",enterType);
            
            String showLog = request.getParameter("showLog");
            if (!CommonUtil.isEmpty(showLog)) {
                request.setAttribute("showLog",showLog);
            }
            //页面input框数据集合
            Map<String,Object> results = new HashMap<>();
            results.put("planId",plan.getId());
            results.put("projectId",projectId);
            results.put("planBizCurrent",plan.getBizCurrent());
            results.put("showProjectInfo",showProjectInfo);
            results.put("enterType",enterType);
            results.put("onlyReadonly",onlyReadonly);
            results.put("fromType",plan.getTaskNameType());
            results.put("isEnableFlag",isEnableFlag);
            request.setAttribute("isEnableFlag",isEnableFlag);         
            results.put("fromDetailType",fromDetailType);
            results.put("preposeIds",plan.getPreposeIds());
            results.put("preposeEndTime","");


            //通过页签模板id获取页签模板信息
            TabTemplateDto tabTemplateDto = tabTemplateFeign.queryTabTemplate(tabId);
            if (!CommonUtil.isEmpty(tabTemplateDto)) {
                tabTemplateCode = tabTemplateDto.getCode();
            }

            if (StringUtils.isNotBlank(tabTemplateCode)) {
                //判断js文件是否存在
                String filePath = request.getSession().getServletContext().getRealPath(CommonConstants.jsPath+tabTemplateCode+".js");
                File file = new File(filePath);
                if (file.exists()) {
                    request.setAttribute("code",tabTemplateCode);
                }
            }

            List<DataSourceObjectDto> dtoList = dataSourceObjectFeign.getAllDataByTabId(tabId);
            String entityPath ="";
            if(dtoList.size()>0) {
                entityPath = dtoList.get(0).getObjectPath();
            }
            
            Map<String, Object> map = new HashMap<>();
            Map<String, TSUserDto> allUserMap= null;          
            Map<String, String> planLevelIdAndNameMap= new HashMap<>();
            Map<String, String> planLevelIdAndNameAllMap= new HashMap<>();
            Map<String, String> activityTypeManageIdAndNameMap= new HashMap<>();
            Map<String, String> statusMap= new HashMap<>();
            Map<String, String> taskTypeMap= new HashMap<>();
            

            if("TC001".equals(tabTemplateCode)) {
                results.put("isStandard",isStandard);                
                allUserMap= userService.getAllUserIdsMap();
                List<JSONObject> jsonList = new ArrayList<JSONObject>();
                for (String  t : allUserMap.keySet()) {
                    TSUserDto cuTsUserDto  = allUserMap.get(t);
                    JSONObject obj1 = new JSONObject();
                    obj1.put("id", cuTsUserDto.getId());
                    obj1.put("realName", cuTsUserDto.getRealName() + "-" + cuTsUserDto.getUserName());
                    jsonList.add(obj1);
                }
                String jonStrUser = JSON.toJSONString(jsonList);
                String jonStr1 = jonStrUser;
                jonStr1 = jonStr1.replaceAll("\"", "'");
                results.put("userList2",jonStr1);                
                try {
                    map = objectToMap(plan);
                    map.put("bizCurrent", plan.getBizCurrent());
                    map.put("createFullName", plan.getCreateFullName()+"-"+plan.getCreateName());
                    if(!CommonUtil.isEmpty(plan.getCreateTime())) {
                        map.put("createTime",  DateUtil.getStringFromDate(plan.getCreateTime(), DateUtil.YYYY_MM_DD));
                    }
                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        //初始化责任部门：
                        List<TSDepartDto> TSDepartDtoList = deptService.getTSDepartByuserId(appKey,plan.getOwner());
                        if(TSDepartDtoList.size()>0) {
                            map.put("ownerDept", TSDepartDtoList.get(0).getDepartname());
                        }
                      }
                    
                    if(!CommonUtil.isEmpty(plan.getOwner())) {
                        map.put("owner", plan.getOwner());
                      }else {
                          map.put("owner", "");
                      }
                   
                    //开始时间：
                    if(!CommonUtil.isEmpty(map.get("planStartTime"))) {
                        map.put("planStartTime",  DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                    }    
                    
                    //结束时间：
                    if(!CommonUtil.isEmpty(map.get("planEndTime"))) {
                        map.put("planEndTime",  DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD));
                    } 
                    
                    //下达时间：
                    if(!CommonUtil.isEmpty(map.get("assignTime"))) {
                        map.put("assignTime",  DateUtil.getStringFromDate(plan.getAssignTime(), DateUtil.YYYY_MM_DD));
                    }
                    //计划进度：
                    String progressRate = "0.00%";
                    if(!CommonUtil.isEmpty(map.get("progressRate"))) {
                        progressRate = map.get("progressRate")+"%";
                        map.put("progressRate", progressRate);
                    }
                    //上级计划：
                    if(!CommonUtil.isEmpty(map.get("parentPlanId"))) {
//                        PlanDto parentPlan = planService.getPlanEntity(map.get("parentPlanId").toString()); 
//                        FlowTaskParentVo flowTaskParent = (FlowTaskParentVo)request.getSession().getAttribute(
//                            PlanConstants.FLOWTASK_PARENT_KEY + parentPlanId);
                        if(!CommonUtil.isEmpty(flowTaskParentVo)) {
                            PlanDto parentPlan = planService.getPlanEntity(flowTaskParentVo.getParentId()); 
                            if(!CommonUtil.isEmpty(parentPlan)) {
                                map.put("parentPlanId", parentPlan.getPlanName());
                            }
                        }
                    }
                    
//                    List<PreposePlanDto> preposePlanList = preposePlanService.getPreposePlansByPlanId(plan);
                   String preposePlanList = plan.getPreposeIds();
                    String preposePlanNames = "";
                    String preposeIdsList = "";
                    if(!CommonUtil.isEmpty(preposePlanList)) {
                        for (String preposePlanId : preposePlanList.split(",")) {                            
                            PlanDto curPreposePlan = planService.getPlanEntity(preposePlanId);
                            if(!CommonUtil.isEmpty(curPreposePlan)) {
                                if(CommonUtil.isEmpty(preposePlanNames)) {
                                    preposePlanNames = curPreposePlan.getPlanName();
                                    preposeIdsList = curPreposePlan.getId();
                                }else {
                                    preposePlanNames = preposePlanNames +","+curPreposePlan.getPlanName();
                                    preposeIdsList = preposeIdsList + "," + curPreposePlan.getId();
                                }
                            }
                        }
                        map.put("preposeNames", preposePlanNames);
                        results.put("preposeIds",preposeIdsList);
                        results.put("allPreposeIds","");
                        
                    }
                    
                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // 计划等级
                BusinessConfig planLevel = new BusinessConfig();
                planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                planLevel.setAvaliable("");
                //获取所有的计划等级：
                List<BusinessConfig> planLevelListStrAll = businessConfigService.searchBusinessConfigs(planLevel);
                if(planLevelListStrAll.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelListStrAll) {
                        planLevelIdAndNameAllMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }

                    String jonStr = JsonUtil.getCodeTitleJson(planLevelListStrAll, "id", "name");
                    results.put("planLevelList",jonStr);
                }

                //获取启用的计划等级：
                List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
         //       List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
                if(planLevelList.size()>0) {
                    for(BusinessConfig curBusinessConfig : planLevelList) {
                        planLevelIdAndNameMap.put(curBusinessConfig.getId(), curBusinessConfig.getName());
                    }
                }

                //获取计划类型 ：
                List<ActivityTypeManageDto> activityTypeManageList = activityTypeManageEntityService.getAllActivityTypeManage(false);
                if(activityTypeManageList.size()>0) {
                    for(ActivityTypeManageDto activityTypeManageDto : activityTypeManageList) {
                        activityTypeManageIdAndNameMap.put(activityTypeManageDto.getId(), activityTypeManageDto.getName());
                    }
                }
                
                //状态：
                statusMap.put("EDITING", "拟制中");
                statusMap.put("LAUNCHED", "已发布");
                statusMap.put("TOBERECEIVED", "待接收");
                statusMap.put("ORDERED", "执行中");
                statusMap.put("FEEDBACKING", "完工确认");
                statusMap.put("FINISH", "已完工");
                statusMap.put("INVALID", "已废弃");
                
                //计划类别：
                if(!CommonUtil.isEmpty(plan.getTaskType())) {
                    if("1".equals(plan.getTaskType())) {
                        map.put("taskType", PlanConstants.PLAN_TYPE_WBS);
                    }else if("2".equals(plan.getTaskType())) {
                        map.put("taskType", PlanConstants.PLAN_TYPE_TASK);
                    }else {
                        map.put("taskType", PlanConstants.PLAN_TYPE_FLOW);
                    }
                }

                //获取项目相关数据：
                if(!CommonUtil.isEmpty(plan.getProjectId())) {
                    Project project = projectService.getProjectEntity(plan.getProjectId());
                    if(!CommonUtil.isEmpty(project)) {
                        map.put("projectName", project.getName());
                        
                        String projectManagerNames = "";
                        if(!CommonUtil.isEmpty(project.getProjectManagers())){
                            for(String managerId :project.getProjectManagers().split(",") ){
                                TSUserDto userDto = allUserMap.get(managerId);
                                if(CommonUtil.isEmpty(projectManagerNames)){
                                    projectManagerNames = userDto.getRealName()+"-"+userDto.getUserName();
                                }else{
                                    projectManagerNames = projectManagerNames + "," +userDto.getRealName()+"-"+userDto.getUserName();
                                }
                            }
                            map.put("projectManagerNames", projectManagerNames);
                        }
                        if(!CommonUtil.isEmpty(project.getStartProjectTime())) {
                            map.put("startProjectTime", DateUtil.getStringFromDate(project.getStartProjectTime(), DateUtil.YYYY_MM_DD)); 
                        }
                        if(!CommonUtil.isEmpty(project.getEndProjectTime())) {
                            map.put("endProjectTime", DateUtil.getStringFromDate(project.getEndProjectTime(), DateUtil.YYYY_MM_DD));
                        }
                        
                        if (!CommonUtil.isEmpty(project.getEps())) {
                            FeignJson fj = epsConfigService.getEpsNamePathById(project.getEps());
                            String epsName = "";
                            if (fj.isSuccess()) {
                                epsName = fj.getObj() == null ? "" : fj.getObj().toString();
                            }
                            map.put("eps", epsName);
                        }
                        String rocess = "0.00%";
                        if(!CommonUtil.isEmpty(project.getProcess())) {
                            rocess = project.getProcess()+"%";                            
                        }
                        map.put("process", rocess);                   
                    }
                }
                
            }
            else if("TC002".equals(tabTemplateCode)) {            
      
            }
            else if("TC003".equals(tabTemplateCode)) {            
                //request.setAttribute("userLevel", UserUtil.getInstance().getUser().getSecurityLevel());
                results.put("userLevel",UserUtil.getInstance().getUser().getSecurityLevel());
            }
            else if("TC004".equals(tabTemplateCode)) {                       
                String startTime = DateUtil.getStringFromDate(plan.getPlanStartTime(), DateUtil.YYYY_MM_DD);
                String endTime = DateUtil.getStringFromDate(plan.getPlanEndTime(), DateUtil.YYYY_MM_DD);
                //request.setAttribute("startTime", startTime);
                //request.setAttribute("endTime", endTime);
                results.put("startTime"+index,startTime);
                results.put("endTime"+index,endTime);
            }else if("TC006".equals(tabTemplateCode) || "TC005".equals(tabTemplateCode)) {//风险清单&问题页签获取teamId
                FeignJson teamFj = projRoleService.getTeamIdByProjectId(projectId);
                String teamId = "";
                if (teamFj.isSuccess()) {
                    teamId = teamFj.getObj() == null ? "" : teamFj.getObj().toString();
                }
                //request.setAttribute("teamId", teamId);
                results.put("teamId",teamId);
            }
            
            request.setAttribute("fromType","Plan");
            //数据查询
            List<List<List<ObjectPropertyInfoDto>>> lists = tabTemplateServiceImpl.goTabView(request, tabId);
            for(List<List<ObjectPropertyInfoDto>> cur2 : lists) {
                for(List<ObjectPropertyInfoDto> cur3 : cur2) {
/*                    for(ObjectPropertyInfoDto cur4 : cur3) {*/
                    for(int i=cur3.size()-1;i>=0; i--) {
                        ObjectPropertyInfoDto cur4 = new ObjectPropertyInfoDto();
                        cur4 = cur3.get(i);
                        if(cur3.get(i).getControl().equals("7") || cur3.get(i).getControl().equals("6")) {                            
                            if(!CommonUtil.isEmpty(cur4.getObjectPath())) {    
                                if(cur4.getObjectPath().contains("TaskCellDeliverItem")) {
                                    cur3.remove(i);
                                    continue;
                                }
                            }
                        }
                        if(!CommonUtil.isEmpty(entityPath) && cur4.getObjectPath().equals(entityPath) && cur4.getControl().equals("6")) {
                            String curType =  entityPath.substring(entityPath.lastIndexOf(".")+1, entityPath.length());
                            cur4.setId(curType);
                            request.setAttribute("fromType",curType);
                            request.setAttribute("dataGrideId",curType);
                           
                        }
                        String propertyvalue = cur4.getPropertyValue();
                        
                        if(!CommonUtil.isEmpty(propertyvalue) && !CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get(propertyvalue))) {
                            cur4.setValueInfo(map.get(propertyvalue).toString());
                        }
                        
                        //按钮样式绑定 默认新增
                        if (cur4.getControl().equals("8")) {
                            //如果是按钮时，替换id：
                            cur4.setButtonDivId(tabTemplateCode+cur4.getPropertyName()+"Div");
                            //输出按钮隐藏：
                            if("TC003".equals(tabTemplateCode) && (!"ORDERED".equals(plan.getBizCurrent()))) {
                                cur4.setDisplay("3");
                            }
                            if(CommonUtil.isEmpty(cur4.getFormat())) {
                                cur4.setFormat("basis ui-icon-plus");
                            }
                        }
                        if(!CommonUtil.isEmpty(propertyvalue)) {
                            //说明：
                            if("remark".equals(propertyvalue)) {
                                cur4.setId("Plan-remark");
                            }
                            
                            if("planName".equals(propertyvalue)){
                                cur4.setId("Plan-planName");
                            }
                            
                            if("owner".equals(propertyvalue)){
                                cur4.setValueField("id");
                                cur4.setTextField("realName");
                                cur4.setId("Plan-owner");
                                if(!CommonUtil.isEmpty(flowTaskParentVo)) {
                                    cur4.setLoadUrl("taskFlowResolveController.do?projectMembers&parentPlanId="+flowTaskParentVo.getParentId()+"");
                                }
                            }
                            
                            if("planLevel".equals(propertyvalue)){
                                cur4.setValueField("id");
                                cur4.setTextField("name");
                                cur4.setId("Plan-planLevel");
                                cur4.setLoadUrl("planController.do?useablePlanLevelList");
                            }
                            
                            if("preposeIds".equals(propertyvalue)){
                                cur4.setId("Plan-preposeIds");
                                if(!CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get("preposeNames"))) {
                                    cur4.setValueInfo((String)map.get("preposeNames"));
                                }
                            }

                            if("ownerDept".equals(propertyvalue)){
                                cur4.setId("Plan-ownerDept");
                            }

                            if("workTime".equals(propertyvalue)){
                                cur4.setId("Plan-workTime");                               
                            }
                            if("planStartTime".equals(propertyvalue)){
                                cur4.setId("planStartTime");                               
                            }
                            
                            if("planEndTime".equals(propertyvalue)){
                                cur4.setId("planEndTime");                               
                            }
                            
                            if(!CommonUtil.isEmpty(map) && !CommonUtil.isEmpty(map.get(propertyvalue))) {
                                //初始化责任人：
                                if("owner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(map.get(propertyvalue)))) {
                                    cur4.setValueInfo(allUserMap.get(map.get(propertyvalue)).getRealName()+"-"+allUserMap.get(map.get(propertyvalue)).getUserName());                                      
                                }
                                
                                //下达人：
                                if("assigner".equals(propertyvalue) && !CommonUtil.isEmpty(allUserMap) && !CommonUtil.isEmpty(allUserMap.get(map.get(propertyvalue)))) {
                                    cur4.setValueInfo(allUserMap.get(map.get(propertyvalue)).getRealName()+"-"+allUserMap.get(map.get(propertyvalue)).getUserName());
                                }

                                //初始化责任人：
                                if("planLevel".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(planLevelIdAndNameMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(planLevelIdAndNameMap.get(map.get(propertyvalue)));
                                    }else if(!CommonUtil.isEmpty(planLevelIdAndNameAllMap.get(map.get(propertyvalue)))){
                                        cur4.setValueInfo(planLevelIdAndNameAllMap.get(map.get(propertyvalue)));
                                    }
                                }
                                //里程碑：
                                if("milestone".equals(propertyvalue)) {
                                    String milestone = map.get(propertyvalue).toString();
                                    if("true".equals(milestone)) {
                                        cur4.setValueInfo("是");
                                    }else {
                                        cur4.setValueInfo("否");
                                    }
                                    
                                }
                                
                                //获取计划类型 ：
                                if("taskNameType".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(activityTypeManageIdAndNameMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(activityTypeManageIdAndNameMap.get(map.get(propertyvalue)));
                                    }else {
                                        cur4.setValueInfo("研发类");
                                    }
                                   
                                }
                                
                                //状态：
                                if("bizCurrent".equals(propertyvalue)) {
                                    if(!CommonUtil.isEmpty(statusMap.get(map.get(propertyvalue)))) {
                                        cur4.setValueInfo(statusMap.get(map.get(propertyvalue)));
                                    }else {
                                        cur4.setValueInfo("拟制中");
                                    }   
                                }

                                //开始时间：
                                if("planStartTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getPlanStartTime());
                                }
                                //结束时间：
                                if("planEndTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getPlanEndTime());
                                }
                                //创建时间：
                                if("createTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getCreateTime());
                                }
                                //发布时间：
                                if("assignTime".equals(propertyvalue)) {
                                    cur4.setDefaultValue(plan.getAssignTime());
                                }
                            }
                        }
                    }
                }
            }
            request.setAttribute("results",results);
            request.setAttribute("lists",lists);
            
        }
        return new ModelAndView("com/glaway/ids/planGeneral/planGeneral");
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
}
