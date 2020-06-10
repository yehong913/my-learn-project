package com.glaway.ids.project.task.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaway.foundation.core.common.controller.BaseController;


/**
 * @Title: Controller
 * @Description: 项目计划
 * @author blcao
 * @date 2015-03-23 16:32:26
 * @version V1.0
 */
@Controller
@RequestMapping("/taskController")
public class TaskController extends BaseController {
//
//    /**
//     * 
//     */
//    private static final OperationLog log = BaseLogFactory.getOperationLog(TaskController.class);
//
//    /**
//     * 项目计划管理接口
//     */
//    @Autowired
//    private PlanServiceI planService;
//
//    /**
//     * 计划资源
//     */
//    @Autowired
//    private ResourceLinkInfoServiceI resourceLinkInfoService;
//
//    /**
//     * 项目角色人员服务实现接口<br>
//     */
//    @Autowired
//    private ProjRoleServiceI projRoleService;
//
//    /**
//     * 名称库<br>
//     */
//    @Autowired
//    private NameStandardServiceI nameStandardService;
//
//    /**
//     * 项目角色人员服务实现接口<br>
//     */
//    @Autowired
//    private ProjectServiceI projectService;
//
//    /**
//     * 项目计划参数接口
//     */
//    @Autowired
//    private ParamSwitchServiceI paramSwitchService;
//
//    /**
//     * 消息信息
//     */
//    private String message;
//
//    /**
//     * 查询名称库是否开启 是 true 否 false
//     * 
//     * @param ids
//     * @return
//     */
//    @SuppressWarnings("finally")
//    @RequestMapping(params = "isStandard")
//    @ResponseBody
//    public AjaxJson isStandard(HttpServletRequest request) {
//        AjaxJson j = new AjaxJson();
//        String message = I18nUtil.getValue("com.glaway.ids.pm.project.task.nameStandardInexistence");
//        try {
//            String switchStr = paramSwitchService.getSwitch(SwitchConstants.NAMESTANDARDSWITCH);
////            String switchStr = "";
////            try {
////                RdfTaskSupportImplService support = new RdfTaskSupportImplService();
////                RdfTaskSupport rdfConfigSupport = support.getRdfTaskSupportImplPort();
////                String switchStrJson = rdfConfigSupport.getSwitchfromRdFlow(SwitchConstants.NAMESTANDARDSWITCH);
////                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
////                AjaxJson ajaxJson = gson.fromJson(switchStrJson, AjaxJson.class);
////                switchStr = (String)ajaxJson.getObj();
////            }
////            catch (Exception e) {
////                e.printStackTrace();
////            }
//            if (NameStandardSwitchConstants.USENAMESTANDARDLIB.equals(switchStr)) {// 非强制名称库
//                request.getSession().setAttribute("isStandard", "ok");
//            }
//            else if (NameStandardSwitchConstants.FORCEUSENAMESTANDARD.equals(switchStr)// 强制名称库
//                     || NameStandardSwitchConstants.DELIVERABLEUPATEABLE.equals(switchStr)) {
//                j.setObj(true);
//                message = I18nUtil.getValue("com.glaway.ids.pm.project.task.nameStandardExist");
//                request.getSession().setAttribute("isStandard", "true");
//            }
//            else {
//                request.getSession().setAttribute("isStandard", "false");// 不使用名称库
//            }
//        }
//        catch (Exception e) {
//            Object[] arguments = new String[] {
//                I18nUtil.getValue("com.glaway.ids.common.msg.search"), ""};
//            message = I18nUtil.getValue("com.glaway.ids.common.search.failure", arguments);
//            throw new GWException(GWConstants.ERROR_2002, e);
//        }
//        finally {
//            // systemService.addLog(message, Globals.Log_Type_INSERT,
//            // Globals.Log_Leavel_INFO);
//            j.setMsg(message);
//            return j;
//        }
//    }
//
//    /**
//     * 计划前置选择页面跳转
//     * 
//     * @param plan
//     * @param req
//     * @return
//     * @see
//     */
//    @RequestMapping(params = "taskData")
//    public ModelAndView goTaskList(HttpServletRequest request) {
//        // 获取所属项目ID
//        String projectId = request.getParameter("id");
//        boolean isModify = projectService.isModify(projectId);
//        String userId = UserUtil.getInstance().getUser().getId();
//        boolean isProjectManger = projRoleService.isProjRoleByUserIdAndRoleCode(projectId,
//            ProjectRoleConstants.PROJ_MANAGER, userId);
//        boolean isPmo = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
//            ProjectRoleConstants.PMO);
//        Plan p = new Plan();
//        planService.initBusinessObject(p);
//        List<LifeCycleStatus> statusList = p.getPolicy().getLifeCycleStatusList();
//
//        String warningDay = paramSwitchService.getSwitch(SwitchConstants.PLANWARNINGDAYS);
//        String warningDayFlag = "before";
//        if (StringUtils.isNotEmpty(warningDay)) {
//            if (warningDay.trim().startsWith("-")) {
//                warningDayFlag = "after";
//                warningDay = warningDay.trim().replace("-", "");
//            }
//        }
//        else {
//            warningDay = "0";
//        }
//
//        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
//        request.setAttribute("isModify", isModify);
//        request.setAttribute("currentUserId", userId);
//        request.setAttribute("isProjectManger", isProjectManger);
//        request.setAttribute("isPmo", isPmo);
//        request.setAttribute("warningDay", warningDay);
//        request.setAttribute("warningDayFlag", warningDayFlag);
//        request.setAttribute("statusList", jonStr);
//        String currentDate = DateUtil.getStringFromDate(new Date(), DateUtil.YYYY_MM_DD);
//        request.setAttribute("currentDate", currentDate);
//        return new ModelAndView("com/glaway/ids/project/task/taskList");
//    }
//
//    /**
//     * easyui AJAX请求数据
//     * 
//     * @param request
//     * @param response
//     * @param dataGrid
//     * @param user
//     */
//    @RequestMapping(params = "datagrid")
//    public void datagrid(Plan plan, HttpServletRequest request, HttpServletResponse response) {
//        Object[] arguments = new String[] {
//            I18nUtil.getValue("com.glaway.ids.pm.project.task.tasklist"),
//            I18nUtil.getValue("com.glaway.ids.common.msg.search")};
//        String message = I18nUtil.getValue("com.glaway.ids.common.search.success", arguments);
//        try {
//            int page = Integer.valueOf(request.getParameter("page"));
//            int row = Integer.valueOf(request.getParameter("rows"));
//            // plan.setProjectId(projectId);
//            plan.setOwner(UserUtil.getInstance().getUser().getId());
//            List<Plan> planList = planService.queryPlanListForTask(plan, page, row, true);
//            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
//                1.0).create();
//            String json = gson.toJson(planList);
//            long count = planService.getCountForTask(plan);
//            String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
//            TagUtil.ajaxResponse(response, datagridStr);
//        }
//        catch (Exception e) {
//            message = I18nUtil.getValue("com.glaway.ids.common.search.failure", arguments);
//            log.error(message, e, null, message);
//            Object[] params = new Object[] {message};// 异常原因：{0}；异常描述：{1}
//            throw new GWException(GWConstants.ERROR_2001, params, e);
//        }
//    }
//
//    /**
//     * 计划前置选择页面跳转
//     * 
//     * @param plan
//     * @param req
//     * @return
//     * @see
//     */
//    @RequestMapping(params = "taskCellAdd")
//    public ModelAndView taskCellAdd(HttpServletRequest request) {
//        String cellId = request.getParameter("cellId");
//        request.setAttribute("cellId", cellId);
//        String templateId = request.getParameter("templateId");
//        request.setAttribute("templateId", templateId);
//        String refDuration = request.getParameter("refDuration");
//        request.setAttribute("refDuration", refDuration);
//        String isEnableFlag = request.getParameter("isEnableFlag");
//        request.setAttribute("isEnableFlag", isEnableFlag);
//        ModelAndView mav = new ModelAndView("com/glaway/ids/project/task/taskCell-add");
//        mav.addObject("isStandard", (Boolean)this.isStandard(request).getObj());
//        return mav;
//    }
//
//    /**
//     * 前置交付项指向后置页面跳转
//     * 
//     * @param plan
//     * @param req
//     * @return
//     * @see
//     */
//    @RequestMapping(params = "taskCellQueryDeliver")
//    public ModelAndView taskCellQueryDeliver(HttpServletRequest request) {
//        String fromCellId = request.getParameter("fromCellId");
//        request.setAttribute("fromCellId", fromCellId);
//        String toCellId = request.getParameter("toCellId");
//        request.setAttribute("toCellId", toCellId);
//        String templateId = request.getParameter("templateId");
//        request.setAttribute("templateId", templateId);
//        ModelAndView mav = new ModelAndView(
//            "com/glaway/ids/project/task/taskCellTargetDeliver-select");
//        return mav;
//    }
}
