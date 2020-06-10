package com.glaway.ids.project.plan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.cache.service.RedisService;
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
import com.glaway.foundation.fdk.dev.service.FeignOutwardExtensionService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.calendar.FeignCalendarService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignGroupService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.PlanConstants;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.plan.vo.PlanChangeVo;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import com.glaway.ids.project.projectmanager.service.ProjLibRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.jasypt.commons.CommonUtils;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LHR on 2019/8/6.
 */
@Controller
@RequestMapping("/planChangeMassController")
public class PlanChangeMassController extends BaseController {

    /**
     *
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanChangeMassController.class);

    /**
     * 项目服务实现接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;


    /**
     * 项目团队服务实现接口
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    @Autowired
    private PlanRemoteFeignServiceI planService;


    @Autowired
    private FeignSystemService feignSystemService;


    @Autowired
    private FeignGroupService groupService;


    @Autowired
    private FeignUserService userService;


    @Autowired
    private FeignCalendarService calendarService;


    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;


    @Autowired
    private ParamSwitchRemoteFeignServiceI paramSwitchService;


    @Autowired
    private RedisService redisService;


    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;


    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliveryStandardService;

    @Autowired
    PreposePlanRemoteFeignServiceI planRemoteFeignServiceI;

    @Autowired
    ParamSwitchRemoteFeignServiceI paramSwitchRemoteFeignServiceI;

    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    @Autowired
    private ProjLibRemoteFeignServiceI projLibService;

    @Autowired
    private FeignOutwardExtensionService outwardExtensionService;

    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanRemoteFeignServiceI;

    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoRemoteFeignServiceI;

    @Autowired
    private ResourceRemoteFeignServiceI resourceRemoteFeignServiceI;

    /**
     * 接口
     */
    @Value(value="${spring.application.name}")
    private String appKey;

    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkService;

    @Autowired
    private PlanBusinessConfigServiceI planBusinessConfigServiceI;

    @Autowired
    private FeignPlanChangeServiceI feignPlanChangeServiceI;

    @Autowired
    private ResourceRemoteFeignServiceI resourceService;

    /**
     * 查询计划方式
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "planChangeCategorylList")
    @ResponseBody
    public void planChangeCategorylList(HttpServletRequest request, HttpServletResponse response) {

        BusinessConfig planChangeCategory = new BusinessConfig();
        planChangeCategory.setConfigType(ConfigTypeConstants.PLANCHANGECATEGORY);
        List<BusinessConfig> planLevelList = planBusinessConfigServiceI.searchUseableBusinessConfigs(planChangeCategory);
  //      List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});

     /*   String jonStr = JsonUtil.getCodeTitleJson(planChangeCategoryList, "id", "name");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/

        String json = com.alibaba.fastjson.JSONArray.toJSONString(planLevelList);
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 项目计划批量变更页面跳转
     *
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangePlanMass")
    public ModelAndView goChangePlanMass(TemporaryPlanDto temporaryPlan, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        req.setAttribute("projectIdForPlan", projectId);
        Project project = projectService.getProjectEntity(projectId);
        SimpleDateFormat sd= new SimpleDateFormat("YYYY-MM-dd");
        req.setAttribute("startProjectTime", sd.format(project.getStartProjectTime()));
        req.setAttribute("endProjectTime", sd.format(project.getEndProjectTime()));
        // 计划变更类型
        BusinessConfig planChangeCategory = new BusinessConfig();
        planChangeCategory.setConfigType(ConfigTypeConstants.PLANCHANGECATEGORY);
        List<BusinessConfig> planChangeCategoryList = businessConfigService.searchUseableBusinessConfigs(planChangeCategory);
      //  List<BusinessConfig> planChangeCategoryList = JSON.parseObject(JsonFromatUtil.formatJsonToList(str1),new TypeReference<List<BusinessConfig>>(){});
        req.setAttribute("planChangeCategoryList", planChangeCategoryList);
        String jonStr = JsonUtil.getCodeTitleJson(planChangeCategoryList, "id", "name");
        req.setAttribute("planChangeCategoryListStr", jonStr);

        List<TSUserDto> userList = new ArrayList<>();

        // userList = userService.queryUserList();
        userList = projRoleService.getUserInProject(projectId);

        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfigDto> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(str2),new TypeReference<List<BusinessConfigDto>>(){});
        for (TSUserDto t : userList) {
            t.setRealName(t.getRealName() + "-" + t.getUserName());
        }
        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        String jonStr3 = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        String b = JSON.toJSONString(planLevelList);
        req.setAttribute("planLevelList1", b);
        String aaa = JSON.toJSONString(userList);
        req.setAttribute("userList1", aaa);
        req.setAttribute("temporaryPlan_", temporaryPlan);
        req.setAttribute("planLevelList", jonStr3);
        req.setAttribute("userList", jonStr2);

        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMass");
    }

    /**
     * 计划下达页面初始化
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planChangeList")
    public void planChangeList(PlanDto plan, HttpServletRequest request, HttpServletResponse response) {
        String appKey = ResourceUtil.getApplicationInformation().getAppKey();
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        plan.setProjectId(projectId);
        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson feignJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        boolean isProjectManger = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PROJ_MANAGER,
                userId, teamId);

        List<PlanDto> planList = new ArrayList<>();
        List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);

        Map<String, TSUserDto> allUserIdsMap = userService.getAllUserIdsMap();

        for (PlanDto p : list) {
            if (StringUtils.isNotEmpty(p.getPlanLevel())){
                BusinessConfigDto businessConfig = planService.getBusinessConfigEntity(p.getPlanLevel());
                if (!CommonUtil.isEmpty(businessConfig)){
                    p.setPlanLevelInfo(businessConfig);
                }
            }

            if (StringUtils.isNotEmpty(p.getOwner())){
                TSUserDto userDto = allUserIdsMap.get(p.getOwner());
                p.setOwnerInfo(userDto);
            }


            PlanDto node = null;
            try {
                node = (PlanDto) BeanUtil.cloneBean(p);
                if (CommonUtils.isNotEmpty(node.getParentPlanId())){
                    PlanDto praent = planService.getPlanEntity(node.getParentPlanId());
                    node.setParentPlan(praent);
                }
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            node.setId(p.getId());
            if (isProjectManger) {
                boolean isPmoPd = groupService.judgeHasLimit(appKey,ProjectRoleConstants.PMO,
                        node.getCreateBy(), null);
                if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getPlanType())) {
                    if (StringUtils.isEmpty(node.getParentPlanId())) {
                        if (userId.equals(node.getCreateBy()) || isPmoPd) {
                            if ((PlanConstants.PLAN_ORDERED.equals(node.getBizCurrent()) || PlanConstants.PLAN_FLOWSTATUS_LAUNCHED.equals(node.getBizCurrent()) || PlanConstants.PLAN_FLOWSTATUS_TOBERECEIVED.equals(node.getBizCurrent()))
                                    && (StringUtils.isEmpty(node.getFlowStatus())|| "NORMAL".equals(node.getFlowStatus()) || "true".equals(node.getIsChangeSingleBack())))
                            {
                                node.set_parentId(node.getParentPlanId());
                                node.setOrder(String.valueOf(node.getStoreyNo()));
                                planList.add(node);
                            }
                        }
                    }
                    else {
                        if (StringUtils.isNotEmpty(node.getParentPlan().getOwner())) {
                            boolean isPmoPd1 = groupService.judgeHasLimit(appKey,
                                    ProjectRoleConstants.PMO, node.getParentPlan().getOwner(), null);
                            if (userId.equals(node.getParentPlan().getOwner()) || isPmoPd1) {
                                if ((PlanConstants.PLAN_ORDERED.equals(node.getBizCurrent()) || PlanConstants.PLAN_FLOWSTATUS_LAUNCHED.equals(node.getBizCurrent()) || PlanConstants.PLAN_FLOWSTATUS_TOBERECEIVED.equals(node.getBizCurrent()))
                                        && (StringUtils.isEmpty(node.getFlowStatus())|| "NORMAL".equals(node.getFlowStatus()) || "true".equals(node.getIsChangeSingleBack()))) {
                                    if (!PlanConstants.PLAN_ORDERED.equals(node.getParentPlan().getBizCurrent())
                                            || PlanConstants.PLAN_FLOWSTATUS_CHANGE.equals(node.getParentPlan().getFlowStatus())) {
                                        node.setParentPlanId(null);
                                    }
                                    node.set_parentId(node.getParentPlanId());
                                    node.setOrder(String.valueOf(node.getStoreyNo()));
                                    planList.add(node);
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (!PlanConstants.PLAN_TYPE_FLOW.equals(node.getPlanType())) {
                    if (StringUtils.isEmpty(node.getParentPlanId())) {
                        if (userId.equals(node.getCreateBy())) {
                            if (PlanConstants.PLAN_ORDERED.equals(node.getBizCurrent())&& (StringUtils.isEmpty(node.getFlowStatus())|| "NORMAL".equals(node.getFlowStatus()) || "true".equals(node.getIsChangeSingleBack()))) {
                                node.set_parentId(node.getParentPlanId());
                                node.setOrder(String.valueOf(node.getStoreyNo()));
                                planList.add(node);
                            }
                        }
                    }
                    else {
                        if (node.getParentPlan() != null
                                && StringUtils.isNotEmpty(node.getParentPlan().getOwner())) {
                            if (userId.equals(node.getParentPlanId())) {
                                if (PlanConstants.PLAN_ORDERED.equals(node.getBizCurrent())&& (StringUtils.isEmpty(node.getFlowStatus())|| "NORMAL".equals(node.getFlowStatus()) || "true".equals(node.getIsChangeSingleBack()))) {
                                    if (!PlanConstants.PLAN_ORDERED.equals(node.getParentPlan().getBizCurrent())
                                            || PlanConstants.PLAN_FLOWSTATUS_CHANGE.equals(node.getParentPlan().getFlowStatus())) {
                                        node.setParentPlanId(null);
                                    }
                                    if (!userId.equals(node.getParentPlan().getCreateBy())) {
                                        node.setParentPlanId(null);
                                    }
                                    node.set_parentId(node.getParentPlanId());
                                    node.setOrder(String.valueOf(node.getStoreyNo()));
                                    planList.add(node);
                                }
                            }

                        }
                    }
                }
            }
        }

        for (PlanDto p1 : planList) {
            int a = 0;
            if (StringUtils.isNotEmpty(p1.getParentPlanId())) {
                for (PlanDto p2 : planList) {
                    if (p1.getParentPlanId().equals(p2.getId())) {
                        a = 1;
                        break;
                    }
                }
            }
            if (a == 0) {
                p1.set_parentId(null);
            }
        }
        request.getSession().setAttribute("planChangeMass-planList", planList);
        List<JSONObject> rootList = planService.changePlansToJSONObjects(planList);

        String resultJSON = JSON.toJSONString(rootList);

        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断父节点是否是拟制中
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "pdChangeAll")
    @ResponseBody
    public AjaxJson pdChangeAll(String ids, PlanDto plan, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        // String id = req.getParameter("id");
        String projectId = req.getParameter("projectId");
        plan.setProjectId(projectId);
        List<PlanDto> planList = new ArrayList<>();
        for (String id : ids.split(",")) {
            PlanDto pTemple = planService.getPlanEntity(id);
            planList.add(pTemple);
        }
        req.getSession().setAttribute("planChangeMass-planList", planList);

        for (PlanDto p : planList) {
            if (!PlanConstants.PLAN_ORDERED.equals(p.getBizCurrent())) {
                Object[] arguments = new String[] {p.getPlanName()};
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.pdChangeAllOne", arguments));
                break;
            }
            else if (StringUtils.isNotEmpty(p.getFlowStatus())
                    && !PlanConstants.PLAN_FLOWSTATUS_NORMAL.equals(p.getFlowStatus())) {
                Object[] arguments = new String[] {p.getPlanName()};
                j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.plan.pdChangeAllTwo", arguments));
                break;
            }
        }
        return j;
    }

    /**
     * 项目计划页面初始化时获取计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planChangeSelectList")
    public void planChangeSelectList(PlanDto plan, HttpServletRequest request,
                                     HttpServletResponse response) {
        // this.planSaveChangeList.clear();
        // this.planSaveChangeList.addAll(this.planList);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanDto> list = (List<PlanDto>)request.getSession().getAttribute("planChangeMass-planList");
        List<PlanDto> planList = new ArrayList<>();
        for (PlanDto pp : list) {
            PlanDto node = null;
            try {
                node = (PlanDto) BeanUtil.cloneBean(pp);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            node.setId(pp.getId());
            if (!StringUtils.isEmpty(node.getParentPlanId())) {
                boolean flg = false;
                for (PlanDto p : planList) {
                    if (node.getParentPlanId().equals(p.getId())) {
                        flg = true;
                        break;
                    }
                }
                if (!flg) {
                    node.setParentPlanId(null);
                }
            }
            node.set_parentId(node.getParentPlanId());
            node.setOrder(String.valueOf(node.getStoreyNo()));
            planList.add(node);
        }
        DataGridReturn data = new DataGridReturn(planList.size(), planList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);
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
    @RequestMapping(params = "changePlanList")
    public void changePlanList(String ids, String planLeveIds, String ownerIds,
                               String planStartTimes, String planEndTimes, String workTimes,
                               String milestones, HttpServletRequest request,
                               HttpServletResponse response)
            throws ParseException {
        String[] id = ids.split(",");
        String[] ownerId = ownerIds.split(",");
        String[] planLeveId = planLeveIds.split(",");
        String[] planStartTime = planStartTimes.split(",");
        String[] planEndTime = planEndTimes.split(",");
        String[] milestone = milestones.split(",");
        List<PlanDto> planChangeList = new ArrayList<PlanDto>();

        List<PlanDto> planLst = new ArrayList<PlanDto>();
        for (int i = 0; i < id.length; i++ ) {
            PlanDto p = planService.getPlanEntity(id[i]);
            planLst.add(p);
        }

        for (int i = 0; i < planLst.size(); i++ ) {
            PlanDto p = planLst.get(i);

            if (ownerId != null && !"".equals(ownerIds) && p.getOwner() != null) {
                if (ownerId[i].equals(p.getOwner())) {}
                else {
                    if (p.getOwner().equals(ownerId[i])) {

                    }
                    else {
                        p.setOwner(ownerId[i]);
                    }
                }
            }

            if (planLeveId != null && !"".equals(planLeveIds)) {
                if (p.getPlanLevel() != null) {
                    if (planLeveId.length > 0) {
                        if (planLeveId[i].equals(p.getPlanLevel())) {}
                        else {
                            if (p.getPlanLevel().equals(planLeveId[i])) {

                            }
                            else {
                                p.setPlanLevel(planLeveId[i]);
                            }
                        }
                    }

                }
                else {
                    p.setPlanLevel(planLeveId[i]);
                }
            }
            Date planStartTime2 = DateUtil.getDateFromString(planStartTime[i], DateUtil.YYYY_MM_DD);
            if (planStartTime2.getTime() == p.getPlanStartTime().getTime()) {}
            else {
                p.setPlanStartTime(planStartTime2);
            }

            Date planEndTime2 = DateUtil.getDateFromString(planEndTime[i], DateUtil.YYYY_MM_DD);
            if (planEndTime2.getTime() == p.getPlanEndTime().getTime()) {}
            else {
                p.setPlanEndTime(planEndTime2);
            }

            long workTime1 = 0;
            Date start = (Date)planStartTime2.clone();
            Date end = (Date)planEndTime2.clone();
            Project project = projectService.getProjectEntity(p.getProjectId());
            p.setProject(project);
            if (ProjectConstants.WORKDAY.equals(p.getProject().getProjectTimeType())) {
                workTime1 = TimeUtil.getWorkDayNumber(start, end);
            }
            else if (ProjectConstants.COMPANYDAY.equals(p.getProject().getProjectTimeType())) {
                Map<String,Object> params = new HashMap<String, Object>();
                params.put("startDate",start);
                params.put("endDate",end);
                workTime1 = calendarService.getAllWorkingDay(appKey,params).size();
            }
            else {

                workTime1 = DateUtil.dayDiff(start, end) + 1;
            }
            p.setWorkTime(String.valueOf(workTime1));

            if (milestone != null && !"".equals(milestones)) {
                if (milestone[i].equals(p.getMilestoneName())) {}
                else {
                    if ("false".equals(milestone[i]) || "true".equals(milestone[i])) {

                    }
                    else {
                        if ("是".equals(milestone[i])) {
                            p.setMilestone("true");
                        }
                        else if ("否".equals(milestone[i])) {
                            p.setMilestone("false");
                        }
                    }
                }
            }

            // if(a !=""){
            planChangeList.add(p);
            // }
        }
        request.getSession().setAttribute("planChangeMass-planChangeList", planChangeList);

    }

    /**
     * 判断批量变更的计划的父子、前后置时间是否收敛
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "checkChangePlanList")
    @ResponseBody
    public AjaxJson checkChangePlanList(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String msg = "";
        List<PlanDto> planChangeList = (List<PlanDto>)req.getSession().getAttribute(
                "planChangeMass-planChangeList");
        for (PlanDto planChange : planChangeList) {
            PlanDto plan = planService.getPlanEntity(planChange.getId());
            if (StringUtils.isNotEmpty(plan.getParentPlanId())) {
                PlanDto parent = null;
                boolean flg = false;
                for (PlanDto p : planChangeList) {
                    if (p.getId().equals(plan.getParentPlanId())) {
                        flg = true;
                        parent = p;
                        break;
                    }
                }
                if (!flg) {
                    parent = planService.getPlanEntity(plan.getParentPlanId());
                }
                if (parent != null) {
                    if ((parent.getPlanStartTime().getTime() > planChange.getPlanStartTime().getTime())
                            || (parent.getPlanEndTime().getTime() < planChange.getPlanEndTime().getTime())) {
                        String time1 = DateUtil.getStringFromDate(parent.getPlanStartTime(),
                                DateUtil.YYYY_MM_DD);
                        String time2 = DateUtil.getStringFromDate(parent.getPlanEndTime(),
                                DateUtil.YYYY_MM_DD);
                        Object[] arguments = new String[] {planChange.getPlanName(),
                                parent.getPlanName(), time1, time2};
                        msg = I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChangePlanListOne", arguments);
                        break;
                    }
                }
                else {
                    Object[] arguments = new String[] {planChange.getPlanName()};
                    msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChangePlanListTwo",
                            arguments);
                    break;
                }
            }
            else {
                List<PreposePlanDto> preposePlanList = preposePlanRemoteFeignServiceI.getPreposePlansByPlanId(planChange);
                for (PreposePlanDto preposePlan : preposePlanList) {
                    if (StringUtils.isNotEmpty(preposePlan.getPreposePlanId())) {
                        PlanDto prepose = null;
                        boolean flg = false;
                        for (PlanDto p : planChangeList) {
                            if (p.getId().equals(preposePlan.getPreposePlanId())) {
                                flg = true;
                                prepose = p;
                                break;
                            }
                        }
                        if (!flg) {
                            prepose = planService.getPlanEntity(
                                    preposePlan.getPreposePlanId());
                        }
                        if (prepose != null) {
                            if (planChange.getPlanStartTime().getTime() <= prepose.getPlanEndTime().getTime()) {
                                String time1 = DateUtil.getStringFromDate(
                                        preposePlan.getPreposePlanInfo().getPlanEndTime(),
                                        DateUtil.YYYY_MM_DD);
                                Object[] arguments = new String[] {planChange.getPlanName(),
                                        preposePlan.getPreposePlanInfo().getPlanName(), time1};
                                msg = I18nUtil.getValue(
                                        "com.glaway.ids.pm.project.plan.checkChangePlanListThree",
                                        arguments);
                                break;
                            }
                        }
                        else {
                            Object[] arguments = new String[] {planChange.getPlanName()};
                            msg = I18nUtil.getValue(
                                    "com.glaway.ids.pm.project.plan.checkChangePlanListFour", arguments);
                            break;
                        }
                    }
                }
            }
        }
        j.setMsg(msg);
        return j;
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
    public void resourceList(HttpServletRequest request, HttpServletResponse response) {
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = new ArrayList<>();
        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");
        List<TempPlanResourceLinkInfoDto> removeList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
            "removeList");
        for (int i = 0; i < planChangeList.size(); i++ ) {
            ResourceLinkInfoDto resourceLinkInfo = new ResourceLinkInfoDto();
            resourceLinkInfo.setUseObjectId(planChangeList.get(i).getId());
            resourceLinkInfo.setUseObjectType(PlanConstants.USEOBJECT_TYPE_PLAN);
            List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                    resourceLinkInfo, 1, 10, false);
            List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
            Map<String, String> resourcePathMap = new HashMap<String, String>();
            Map<String, String> resourceNameMap = new HashMap<String, String>();
            for (ResourceDto resource : resourceListTemp) {
                resourcePathMap.put(resource.getId(), resource.getPath());
                resourceNameMap.put(resource.getId(), resource.getName());
            }
            for (ResourceLinkInfoDto resourceLink : resourceLinkList) {
                TempPlanResourceLinkInfoDto tempResourceLink = new TempPlanResourceLinkInfoDto();

                tempResourceLink.setLinkId(resourceLink.getId());

                tempResourceLink.setPlanName(planChangeList.get(i).getPlanName());
                tempResourceLink.setResourceLinkId(planChangeList.get(i).getId());
                tempResourceLink.setId(resourceLink.getResourceId());
                tempResourceLink.setAvaliable(resourceLink.getAvaliable());
                tempResourceLink.setBizCurrent(resourceLink.getBizCurrent());
                tempResourceLink.setBizId(resourceLink.getBizId());
                tempResourceLink.setBizVersion(resourceLink.getBizVersion());
                tempResourceLink.setResourceId(resourceLink.getResourceId());
                tempResourceLink.setResourceInfo(resourceLink.getResourceInfo());
                tempResourceLink.setResourceName(resourceLink.getResourceInfo().getName());
                tempResourceLink.setResourceType(resourcePathMap.get(resourceLink.getResourceId()));
                tempResourceLink.setSecurityLevel(resourceLink.getSecurityLevel());
                tempResourceLink.setUseObjectId(resourceLink.getUseObjectId());
                tempResourceLink.setUseObjectType(resourceLink.getUseObjectType());
                tempResourceLink.setUseRate(resourceLink.getUseRate());
                tempResourceLink.setStartTime(resourceLink.getStartTime());
                tempResourceLink.setStartTimeOverflow(false);
                // Date a = planChangeList.get(i).getPlanStartTime();
                // DateUtil.dateToString(a,DateUtil.LONG_DATE_FORMAT);
                // Date b = planChangeList.get(i).getPlanEndTime();
                // DateUtil.dateToString(b,DateUtil.LONG_DATE_FORMAT);
                // tempResourceLink.setPlanStartTime(a.toString());
                tempResourceLink.setPlanStartTime(DateUtil.dateToString(
                        planChangeList.get(i).getPlanStartTime(), DateUtil.LONG_DATE_FORMAT));
                tempResourceLink.setPlanEndTime(DateUtil.dateToString(
                        planChangeList.get(i).getPlanEndTime(), DateUtil.LONG_DATE_FORMAT));

                if ((tempResourceLink.getStartTime().getTime() < planChangeList.get(i).getPlanStartTime().getTime())
                        || (tempResourceLink.getStartTime().getTime() > planChangeList.get(i).getPlanEndTime().getTime())) {
                    tempResourceLink.setStartTimeOverflow(true);
                }
                tempResourceLink.setEndTime(resourceLink.getEndTime());
                tempResourceLink.setEndTimeOverflow(false);
                if ((tempResourceLink.getEndTime().getTime() > planChangeList.get(i).getPlanEndTime().getTime())
                        || (tempResourceLink.getEndTime().getTime() < planChangeList.get(i).getPlanStartTime().getTime())) {
                    tempResourceLink.setEndTimeOverflow(true);
                }
                resourceLinkInfoList.add(tempResourceLink);
            }
        }

        List<TempPlanResourceLinkInfoDto> newList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(removeList)){
            for (TempPlanResourceLinkInfoDto dto:removeList){
               for (TempPlanResourceLinkInfoDto info:resourceLinkInfoList){
                    if (StringUtils.isNotEmpty(info.getId())&&dto.getId().equals(info.getId())){
                        newList.add(info);
                    }
               }
            }
        }

        if (!CollectionUtils.isEmpty(newList)){
            for (TempPlanResourceLinkInfoDto info:newList){
                resourceLinkInfoList.remove(info);
            }
        }

        request.getSession().setAttribute("planChangeMass-resourceLinkInfoList",
                resourceLinkInfoList);
        long count = resourceLinkInfoList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceLinkInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);

    }

    @RequestMapping(params = "closePlan")
    public void closePlan(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("removeList", null);
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
    public AjaxJson checkChildPostposeInfluenced(HttpServletRequest request,
                                                 HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String msg = "";
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");
        if (!CommonUtil.isEmpty(resourceLinkInfoList)) {
            for (int i = 0; i < resourceLinkInfoList.size(); i++ ) {
                if (StringUtils.isEmpty(resourceLinkInfoList.get(i).getUseRate())) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedOne",
                                arguments);

                    }
                    else {
                        msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedTwo");
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).getStartTime() == null) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedThree",
                                arguments);
                    }
                    else {
                        msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedFour");
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).getEndTime() == null) {
                    if (resourceLinkInfoList.get(i).getResourceInfo() != null) {
                        Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                        I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.checkChildPostposeInfluencedFive",
                                arguments);
                    }
                    else {
                        msg = I18nUtil.getValue("com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedSix");
                    }
                    break;
                }
                if (resourceLinkInfoList.get(i).isStartTimeOverflow()
                        || resourceLinkInfoList.get(i).isEndTimeOverflow()) {
                    Object[] arguments = new String[] {resourceLinkInfoList.get(i).getResourceInfo().getName()};
                    I18nUtil.getValue(
                            "com.glaway.ids.pm.project.plan.checkChildPostposeMassInfluencedSeven",
                            arguments);
                    break;
                }

            }
        }
        j.setMsg(msg);
        return j;
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
    public void temporaryPlanChildList(HttpServletRequest request, HttpServletResponse response) {
        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");
        List<TemporaryPlanChild> temporaryPlanChildList = getChildList(planChangeList);
        long count = temporaryPlanChildList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanChildList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 获取子计划
     *
     * @param
     * @param
     * @param
     * @see
     */
    private List<TemporaryPlanChild> getChildList(List<PlanDto> planChangeList) {
        List<TemporaryPlanChild> childList = new ArrayList<TemporaryPlanChild>();
        for (PlanDto changePlan : planChangeList) {
            PlanDto plan = new PlanDto();
            plan.setParentPlanId(changePlan.getId());
            List<PlanDto> list = planService.queryPlanList(plan, 1, 10, false);
            for (PlanDto p : list) {
                boolean flg = false;
                for (PlanDto change : planChangeList) {
                    if (change.getId().equals(p.getId())) {
                        flg = true;
                        break;
                    }
                }
                if (!flg) {
                    TemporaryPlanChild planChild = new TemporaryPlanChild();
                    planChild.setPlanId(p.getId());
                    planChild.setPlanName(p.getPlanName());
                    planChild.setParentPlanId(p.getParentPlanId());
                    if (!CommonUtil.isEmpty(p.getParentPlanId())){
                        PlanDto parentPlan = planService.getPlanEntity(p.getParentPlanId());
                        planChild.setParentPlanName(parentPlan.getPlanName());
                    }
                    if (StringUtils.isNotEmpty(p.getOwner())) {
                        planChild.setOwner(p.getOwner());
                        if (p.getOwnerInfo() != null) {
                            planChild.setOwnerName(p.getOwnerInfo().getRealName() + "-"
                                    + p.getOwnerInfo().getUserName());
                            planChild.setOwnerInfo(p.getOwnerInfo());
                        }
                    }

                    if(StringUtils.isNotEmpty(p.getAssigner())){
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
                    if (p.getPlanStartTime() != null) {
                        if ((p.getPlanStartTime().getTime() < changePlan.getPlanStartTime().getTime())
                                || (p.getPlanStartTime().getTime() > changePlan.getPlanEndTime().getTime())) {
                            planChild.setStartTimeOverflow(true);
                        }
                    }

                    planChild.setPlanStartTime(DateUtil.dateToString(p.getPlanStartTime(),
                            DateUtil.LONG_DATE_FORMAT));

                    planChild.setEndTimeOverflow(false);
                    if (p.getPlanEndTime() != null) {
                        if ((p.getPlanEndTime().getTime() > changePlan.getPlanEndTime().getTime())
                                || (p.getPlanEndTime().getTime() < changePlan.getPlanStartTime().getTime())) {
                            planChild.setEndTimeOverflow(true);
                        }
                    }

                    planChild.setPlanEndTime(DateUtil.dateToString(p.getPlanEndTime(),
                            DateUtil.LONG_DATE_FORMAT));
                    Object[] arguments = new String[] {
                            DateUtil.dateToString(changePlan.getPlanStartTime(),
                                    DateUtil.LONG_DATE_FORMAT),
                            DateUtil.dateToString(changePlan.getPlanEndTime(),
                                    DateUtil.LONG_DATE_FORMAT)};
                    I18nUtil.getValue("com.glaway.ids.pm.project.plan.getChildList", arguments);
                    childList.add(planChild);
                }
            }
        }
        return childList;
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
        String failMessage = "";
        String failMessageCode = "";
        String message = "";
        try {
            List<TemporaryPlanDto> tempPlanList = new ArrayList<>();
            List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                    "planChangeMass-planChangeList");
            if (StringUtils.isNotEmpty(temporaryPlan.getId())) {
                if (temporaryPlan.getId().startsWith(PlanConstants.PLAN_CREATE_UUID)) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanSuccess");
                    failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.addPlanFail");
                    failMessageCode = GWConstants.ERROR_2002;
                    // plan.setId(null);
                    feignPlanChangeServiceI.initTemporaryPlanDto(temporaryPlan);
                    feignPlanChangeServiceI.saveOrUpdateTemporaryPlan(temporaryPlan,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());
                }
                else {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.updatePlanSuccess");
                    failMessage = I18nUtil.getValue("com.glaway.ids.pm.project.plan.updatePlanFail");
                    failMessageCode = GWConstants.ERROR_2003;
                    TemporaryPlanDto t = feignPlanChangeServiceI.getTemporaryPlan(
                            temporaryPlan.getId());
                    temporaryPlan.setPlanNumber(t.getPlanNumber());
                    BeanUtil.copyBeanNotNull2Bean(temporaryPlan, t);
                    feignPlanChangeServiceI.saveOrUpdateTemporaryPlan(t,ResourceUtil.getCurrentUser().getId(),ResourceUtil.getCurrentUserOrg().getId());

                }
                request.getSession().setAttribute("planchangeMass-temporaryId",
                        temporaryPlan.getId());
                request.getSession().setAttribute("planchangemass-useObjectType", "PLAN");
                request.getSession().setAttribute("planchangemass-useObjectId",
                        temporaryPlan.getId());
                log.info(message, temporaryPlan.getId(), temporaryPlan.getId().toString());
                request.getSession().setAttribute("planChangeMass-tempPlanList", tempPlanList);
            }
            else {
                FeignJson feignJson = new FeignJson();
                Map<String, Object> map = new HashMap<>();
                map.put("temporaryPlan",temporaryPlan);
                map.put("planChangeList",planChangeList);
                map.put("userId",UserUtil.getCurrentUser().getId());
                feignJson.setAttributes(map);
                tempPlanList = planFlowForworkService.getTempPlanListForWork(feignJson);
                request.getSession().setAttribute("planChangeMass-tempPlanList", tempPlanList);
            }
            request.getSession().setAttribute("planChangeMass-tempPlanList", tempPlanList);
        }
        catch (Exception e) {
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
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChangeMass-resourceLinkInfoList");
            List<TemporaryPlanDto> tempPlanList = (List<TemporaryPlanDto>)request.getSession().getAttribute(
                    "planChangeMass-tempPlanList");
            leader = URLDecoder.decode(leader, "UTF-8");
            deptLeader = URLDecoder.decode(deptLeader, "UTF-8");

            String changeType = request.getParameter("changeType");

            FeignJson feignJson = new FeignJson();
            Map<String, Object> map = new HashMap<>();
            map.put("tempPlanList",tempPlanList);
            map.put("resourceLinkInfoList",resourceLinkInfoList);
            map.put("actor",actor);
            map.put("leader",leader);
            map.put("deptLeader",deptLeader);
            map.put("changeType",changeType);
            map.put("userId",UserUtil.getCurrentUser().getId());
            feignJson.setAttributes(map);
            planFlowForworkService.startPlanChangeMassForWork(feignJson);

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
     * 计划变更-后置
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "temporaryPlanPostposeList")
    public void temporaryPlanPostposeList(HttpServletRequest request, HttpServletResponse response) {
        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");
        List<TemporaryPlanPostpose> temporaryPlanPostposeList = getTemporaryPlanPostposeList(planChangeList);
        long count = temporaryPlanPostposeList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(temporaryPlanPostposeList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }

    /**
     * 获取后置计划
     *
     * @param
     * @param
     * @return
     * @see
     */
    private List<TemporaryPlanPostpose> getTemporaryPlanPostposeList(List<PlanDto> planChangeList) {
        List<TemporaryPlanPostpose> postposeList = new ArrayList<TemporaryPlanPostpose>();
        for (PlanDto planChange : planChangeList) {
            PlanDto plan = new PlanDto();
            plan.setPreposeIds(planChange.getId());
            List<PreposePlanDto> list = preposePlanRemoteFeignServiceI.getPostposesByPreposeId(plan);
            for (PreposePlanDto prepose : list) {
                boolean flg = false;
                for (PlanDto p : planChangeList) {
                    if (prepose.getPlanId().equals(p.getId())) {
                        flg = true;
                        break;
                    }
                }
                if (!flg) {
                    TemporaryPlanPostpose planPostpose = new TemporaryPlanPostpose();
                    PlanDto p = planService.getPlanEntity(prepose.getPlanId());
                    if (p != null) {
                        planPostpose.setPlanId(p.getId());
                        planPostpose.setPlanName(p.getPlanName());
                        planPostpose.setPreposePlanId(plan.getPreposeIds());
                        PlanDto preposePlan = planService.getPlanEntity(
                                prepose.getPreposePlanId());
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

                        if (StringUtils.isNotEmpty(p.getCreateBy())) {
                            planPostpose.setCreateBy(p.getCreateBy());
                            if (p.getCreator() != null) {
                                planPostpose.setCreateByName(p.getCreator().getRealName() + "-"
                                        + p.getCreator().getUserName());
                                planPostpose.setCreator(p.getCreator());
                            }
                        }

                        if(StringUtils.isNotEmpty(p.getAssigner())){
                            planPostpose.setAssigner(p.getAssigner());
                            if (p.getAssignerInfo() != null) {
                                planPostpose.setAssignerName(p.getAssignerInfo().getRealName() + "-"
                                        + p.getAssignerInfo().getUserName());
                                planPostpose.setAssignerInfo(p.getAssignerInfo());
                            }
                        }

                        planPostpose.setStartTimeOverflow(false);
                        Date preposeEndTimeNext = TimeUtil.getExtraDate(
                                planChange.getPlanEndTime(), 1);
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
                        Object[] arguments = new String[] {DateUtil.dateToString(
                                preposeEndTimeNext, DateUtil.LONG_DATE_FORMAT)};
                        I18nUtil.getValue(
                                "com.glaway.ids.pm.project.plan.getTemporaryPlanPostposeList", arguments);
                        postposeList.add(planPostpose);
                    }
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
    public void planChangeVo(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");

        String formId = (String)request.getSession().getAttribute("planchangeMass-formId");
        List<PlanDto> planChangeList = new ArrayList<PlanDto>();
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);

        Map<String,TSUserDto> userDtoMap = userService.getAllUserIdsMap();
        for (ApprovePlanInfoDto a : approve) {
            TemporaryPlanDto t = feignPlanChangeServiceI.getTemporaryPlan(
                    a.getPlanId());
            PlanDto p = planService.getPlanEntity(t.getPlanId());
            PlanDto planTemp = new PlanDto();
            planTemp.setId(p.getId());
            planTemp.setPlanName(t.getPlanName());
            if (StringUtils.isNotEmpty(t.getPlanLevel())) {
                BusinessConfigDto planlevel = planService.getBusinessConfigEntity(
                        t.getPlanLevel());
                planTemp.setPlanLevel(t.getPlanLevel());
                planTemp.setPlanLevelInfo(planlevel);
            }
            if (StringUtils.isNotEmpty(t.getOwner())) {
                planTemp.setOwner(t.getOwner());
                TSUserDto tSUser = userDtoMap.get(t.getOwner());
                planTemp.setOwnerInfo(tSUser);
            }
            planTemp.setPlanStartTime(t.getPlanStartTime());
            planTemp.setPlanEndTime(t.getPlanEndTime());
            planTemp.setMilestone(t.getMilestone());
            planTemp.setWorkTime(t.getWorkTime());
            planChangeList.add(planTemp);
        }

        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
//        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig> planLevelList2 = JSON.parseObject(JsonFromatUtil.formatJsonToList(str1),new TypeReference<List<BusinessConfig>>(){});
        for (PlanDto temporaryPlan : planChangeList) {
            PlanDto plan = planService.getPlanEntity(temporaryPlan.getId());

            if (StringUtils.isNotEmpty(plan.getOwner())
                    && StringUtils.isNotEmpty(temporaryPlan.getOwner())) {
                // Boolean a = false;
                List<TSUserDto> uList = userService.getAllUsers();
                for (TSUserDto t : uList) {
                    String ownerRm = t.getRealName() + "-" + t.getUserName();
                    if (ownerRm.equals(temporaryPlan.getOwner())) {
                        // a = true;
                        temporaryPlan.setOwner(t.getId());
                        break;
                    }
                }
                if (!plan.getOwner().equals(temporaryPlan.getOwner())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("负责人");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getOwnerInfo().getRealName() + "-"
                            + plan.getOwnerInfo().getUserName());
                    TSUserDto t = userService.getUserByUserId(temporaryPlan.getOwner());
                    if (t != null) {
                        planChangeVo.setChangeAfter(t.getRealName() + "-" + t.getUserName());
                    }
                    else {
                        planChangeVo.setChangeAfter(temporaryPlan.getOwner());
                    }

                    planChangeVoList.add(planChangeVo);
                }
            }
            String a = "0";
            for (BusinessConfig b : planLevelList2) {
                if (b.getId().equals(plan.getPlanLevel())) {
                    a = "1";
                    break;
                }
            }

            if (StringUtils.isNotEmpty(plan.getPlanLevel()) && "1".equals(a)) {
                if (StringUtils.isEmpty(temporaryPlan.getPlanLevel())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("计划等级");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getPlanLevelInfo().getName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    for (BusinessConfig b : planLevelList) {
                        if (b.getName().equals(temporaryPlan.getPlanLevelInfo().getName())) {
                            temporaryPlan.setPlanLevel(b.getId());
                            break;
                        }
                    }
                    if (!plan.getPlanLevel().equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        BusinessConfig bc = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                        planChangeVo.setChangeBefore(bc.getName());
                        // BusinessConfig planLevel = new BusinessConfig();
                        // planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                        // List<BusinessConfig> planLevelList =
                        // businessConfigService.searchBusinessConfigs(planLevel);
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }
            else {
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    if (!"===".equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore("-");
                        /*
                         * BusinessConfig planLevel = new BusinessConfig();
                         * planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                         * List<BusinessConfig> planLevelList =
                         * businessConfigService.searchBusinessConfigs(planLevel);
                         */
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        for (BusinessConfig b : planLevelList) {
                            if (b.getName().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            if (plan.getPlanStartTime() != null && temporaryPlan.getPlanStartTime() != null) {
                if (!plan.getPlanStartTime().equals(temporaryPlan.getPlanStartTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("开始时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanStartTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanStartTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getPlanEndTime() != null && temporaryPlan.getPlanEndTime() != null) {
                if (!plan.getPlanEndTime().equals(temporaryPlan.getPlanEndTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("结束时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanEndTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanEndTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getWorkTime() != null && temporaryPlan.getWorkTime() != null) {
                if (!plan.getWorkTime().equals(temporaryPlan.getWorkTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("工期");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getWorkTime());
                    planChangeVo.setChangeAfter(temporaryPlan.getWorkTime());
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (StringUtils.isNotEmpty(plan.getMilestone())
                    && StringUtils.isNotEmpty(temporaryPlan.getMilestone())) {
                if("是".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("true");
                }else if("否".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("false");
                }
                if("是".equals(plan.getMilestone())){
                    plan.setMilestone("true");
                }else if("否".equals(plan.getMilestone())){
                    plan.setMilestone("false");
                }
                if (!plan.getMilestone().equals(temporaryPlan.getMilestone())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
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

        }

        for (PlanDto temporaryPlan : planChangeList) {
            ResourceLinkInfoDto resourceLink = new ResourceLinkInfoDto();
            resourceLink.setUseObjectId(temporaryPlan.getId());
            List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                    resourceLink, 1, 10, false);
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
                    if(CommonUtil.isEmpty(dev.getResourceInfo()) && !CommonUtil.isEmpty(dev.getResourceId())){
                        ResourceDto resource = resourceRemoteFeignServiceI.getEntity(dev.getResourceId());
                        dev.setResourceName(resource.getName());
                    }
                    PlanDto plan = planService.getPlanEntity(d.getUseObjectId());
                    if (d.getResourceName().equals(dev.getResourceName())
                            && plan.getPlanName().equals(dev.getPlanName())) {
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
                            planChangeVo.setChangeBefore(DateUtil.getStringFromDate(d.getStartTime(),DateUtil.YYYY_MM_DD));
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
                            planChangeVo.setChangeBefore(DateUtil.getStringFromDate(d.getEndTime(),DateUtil.YYYY_MM_DD));
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
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("(资源)" + d.getResourceName());
                    planChangeVo.setType("删除");
                    planChangeVo.setChangeBefore(d.getResourceName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
            }

        }
        DataGridReturn data = new DataGridReturn(planChangeVoList.size(), planChangeVoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);

    }
    /**
     * 变更对比
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planChangeSingleVo")
    public void planChangeSingleVo(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");

        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");

        Map<String,TSUserDto> userDtoMap = userService.getAllUserIdsMap();

        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
//        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
    //    List<BusinessConfig> planLevelList2 = JSON.parseObject(JsonFromatUtil.formatJsonToList(str1),new TypeReference<List<BusinessConfig>>(){});
        for (PlanDto temporaryPlan : planChangeList) {
            PlanDto plan = planService.getPlanEntity(temporaryPlan.getId());

            if (StringUtils.isNotEmpty(plan.getOwner())
                    && StringUtils.isNotEmpty(temporaryPlan.getOwner())) {
                // Boolean a = false;
                List<TSUserDto> uList = userService.getAllUsers();
                for (TSUserDto t : uList) {
                    String ownerRm = t.getRealName() + "-" + t.getUserName();
                    if (ownerRm.equals(temporaryPlan.getOwner())) {
                        // a = true;
                        temporaryPlan.setOwner(t.getId());
                        break;
                    }
                }
                if (!plan.getOwner().equals(temporaryPlan.getOwner())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("负责人");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(userDtoMap.get(plan.getOwner()).getRealName() + "-"
                            + userDtoMap.get(plan.getOwner()).getUserName());
                    if (userDtoMap.get(temporaryPlan.getOwner()) != null) {
                        planChangeVo.setChangeAfter(userDtoMap.get(temporaryPlan.getOwner()).getRealName() + "-" + userDtoMap.get(temporaryPlan.getOwner()).getUserName());
                    }
                    else {
                        planChangeVo.setChangeAfter(temporaryPlan.getOwner());
                    }

                    planChangeVoList.add(planChangeVo);
                }
            }
            String a = "0";
            for (BusinessConfig b : planLevelList2) {
                if (b.getId().equals(plan.getPlanLevel())) {
                    a = "1";
                    break;
                }
            }

            if (StringUtils.isNotEmpty(plan.getPlanLevel()) && "1".equals(a)) {
                if (StringUtils.isEmpty(temporaryPlan.getPlanLevel())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("计划等级");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getPlanLevelInfo().getName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    for (BusinessConfig b : planLevelList) {
                        if (b.getName().equals(temporaryPlan.getPlanLevelInfo().getName())) {
                            temporaryPlan.setPlanLevel(b.getId());
                            break;
                        }
                    }
                    if (!plan.getPlanLevel().equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        BusinessConfig bc = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                        planChangeVo.setChangeBefore(bc.getName());
                        // BusinessConfig planLevel = new BusinessConfig();
                        // planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                        // List<BusinessConfig> planLevelList =
                        // businessConfigService.searchBusinessConfigs(planLevel);
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }
            else {
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    if (!"===".equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore("-");
                        /*
                         * BusinessConfig planLevel = new BusinessConfig();
                         * planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                         * List<BusinessConfig> planLevelList =
                         * businessConfigService.searchBusinessConfigs(planLevel);
                         */
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        for (BusinessConfig b : planLevelList) {
                            if (b.getName().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            if (plan.getPlanStartTime() != null && temporaryPlan.getPlanStartTime() != null) {
                if (!plan.getPlanStartTime().equals(temporaryPlan.getPlanStartTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("开始时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanStartTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanStartTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getPlanEndTime() != null && temporaryPlan.getPlanEndTime() != null) {
                if (!plan.getPlanEndTime().equals(temporaryPlan.getPlanEndTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("结束时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanEndTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanEndTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getWorkTime() != null && temporaryPlan.getWorkTime() != null) {
                if (!plan.getWorkTime().equals(temporaryPlan.getWorkTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("工期");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getWorkTime());
                    planChangeVo.setChangeAfter(temporaryPlan.getWorkTime());
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (StringUtils.isNotEmpty(plan.getMilestone())
                    && StringUtils.isNotEmpty(temporaryPlan.getMilestone())) {
                if("是".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("true");
                }else if("否".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("false");
                }
                if("是".equals(plan.getMilestone())){
                    plan.setMilestone("true");
                }else if("否".equals(plan.getMilestone())){
                    plan.setMilestone("false");
                }
                if (!plan.getMilestone().equals(temporaryPlan.getMilestone())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
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

        }

        for (PlanDto temporaryPlan : planChangeList) {
            ResourceLinkInfoDto resourceLink = new ResourceLinkInfoDto();
            resourceLink.setUseObjectId(temporaryPlan.getId());
            List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                    resourceLink, 1, 10, false);
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
                    PlanDto plan = planService.getPlanEntity(d.getUseObjectId());
                    if (d.getResourceName().equals(dev.getResourceName())
                            && plan.getPlanName().equals(dev.getPlanName())) {
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
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("(资源)" + d.getResourceName());
                    planChangeVo.setType("删除");
                    planChangeVo.setChangeBefore(d.getResourceName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
            }

        }
        DataGridReturn data = new DataGridReturn(planChangeVoList.size(), planChangeVoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);

    }

    @RequestMapping(params = "planChangeMassVo")
    public void planChangeMassVo(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();
        List<PlanChangeVo> planChangeVoList = new ArrayList<PlanChangeVo>();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");

        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");
        Map<String,TSUserDto> userDtoMap = userService.getAllUserIdsMap();

        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchBusinessConfigs(planLevel);
//        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
        List<BusinessConfig> planLevelList2 = businessConfigService.searchUseableBusinessConfigs(planLevel);
  //      List<BusinessConfig> planLevelList2 = JSON.parseObject(JsonFromatUtil.formatJsonToList(str1),new TypeReference<List<BusinessConfig>>(){});
        for (PlanDto temporaryPlan : planChangeList) {
            PlanDto plan = planService.getPlanEntity(temporaryPlan.getId());

            if (StringUtils.isNotEmpty(plan.getOwner())
                    && StringUtils.isNotEmpty(temporaryPlan.getOwner())) {
                // Boolean a = false;
                List<TSUserDto> uList = userService.getAllUsers();
                for (TSUserDto t : uList) {
                    String ownerRm = t.getRealName() + "-" + t.getUserName();
                    if (ownerRm.equals(temporaryPlan.getOwner())) {
                        // a = true;
                        temporaryPlan.setOwner(t.getId());
                        break;
                    }
                }
                if (!plan.getOwner().equals(temporaryPlan.getOwner())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("负责人");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(userDtoMap.get(plan.getOwner()).getRealName() + "-"
                            + userDtoMap.get(plan.getOwner()).getUserName());
                    if (userDtoMap.get(temporaryPlan.getOwner()) != null) {
                        planChangeVo.setChangeAfter(userDtoMap.get(temporaryPlan.getOwner()).getRealName() + "-" + userDtoMap.get(temporaryPlan.getOwner()).getUserName());
                    }
                    else {
                        planChangeVo.setChangeAfter(temporaryPlan.getOwner());
                    }

                    planChangeVoList.add(planChangeVo);
                }
            }
            String a = "0";
            for (BusinessConfig b : planLevelList2) {
                if (b.getId().equals(plan.getPlanLevel())) {
                    a = "1";
                    break;
                }
            }

            if (StringUtils.isNotEmpty(plan.getPlanLevel()) && "1".equals(a)) {
                if (StringUtils.isEmpty(temporaryPlan.getPlanLevel())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("计划等级");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getPlanLevelInfo().getName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    BusinessConfig bconfig = businessConfigService.getBusinessConfig(temporaryPlan.getPlanLevel());
                    for (BusinessConfig b : planLevelList) {
                        if (b.getName().equals(bconfig.getName())) {
                            temporaryPlan.setPlanLevel(b.getId());
                            break;
                        }
                    }
                    if (!plan.getPlanLevel().equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        BusinessConfig bc = businessConfigService.getBusinessConfig(plan.getPlanLevel());
                        planChangeVo.setChangeBefore(bc.getName());
                        // BusinessConfig planLevel = new BusinessConfig();
                        // planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                        // List<BusinessConfig> planLevelList =
                        // businessConfigService.searchBusinessConfigs(planLevel);
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }
            else {
                if (StringUtils.isNotEmpty(temporaryPlan.getPlanLevel())) {
                    if (!"===".equals(temporaryPlan.getPlanLevel())) {
                        PlanChangeVo planChangeVo = new PlanChangeVo();
                        planChangeVo.setPlanName(temporaryPlan.getPlanName());
                        planChangeVo.setField("计划等级");
                        planChangeVo.setType("修改");
                        planChangeVo.setChangeBefore("-");
                        /*
                         * BusinessConfig planLevel = new BusinessConfig();
                         * planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
                         * List<BusinessConfig> planLevelList =
                         * businessConfigService.searchBusinessConfigs(planLevel);
                         */
                        for (BusinessConfig b : planLevelList) {
                            if (b.getId().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        for (BusinessConfig b : planLevelList) {
                            if (b.getName().equals(temporaryPlan.getPlanLevel())) {
                                planChangeVo.setChangeAfter(b.getName());
                                break;
                            }
                        }
                        planChangeVoList.add(planChangeVo);
                    }
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            if (plan.getPlanStartTime() != null && temporaryPlan.getPlanStartTime() != null) {
                if (!plan.getPlanStartTime().equals(temporaryPlan.getPlanStartTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("开始时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanStartTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanStartTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getPlanEndTime() != null && temporaryPlan.getPlanEndTime() != null) {
                if (!plan.getPlanEndTime().equals(temporaryPlan.getPlanEndTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("结束时间");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(simpleDateFormat.format(plan.getPlanEndTime()).toString());
                    String time = DateUtil.getStringFromDate(temporaryPlan.getPlanEndTime(),
                            DateUtil.YYYY_MM_DD);
                    planChangeVo.setChangeAfter(time);
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (plan.getWorkTime() != null && temporaryPlan.getWorkTime() != null) {
                if (!plan.getWorkTime().equals(temporaryPlan.getWorkTime())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("工期");
                    planChangeVo.setType("修改");
                    planChangeVo.setChangeBefore(plan.getWorkTime());
                    planChangeVo.setChangeAfter(temporaryPlan.getWorkTime());
                    planChangeVoList.add(planChangeVo);
                }
            }

            if (StringUtils.isNotEmpty(plan.getMilestone())
                    && StringUtils.isNotEmpty(temporaryPlan.getMilestone())) {
                if("是".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("true");
                }else if("否".equals(temporaryPlan.getMilestone())){
                    temporaryPlan.setMilestone("false");
                }
                if("是".equals(plan.getMilestone())){
                    plan.setMilestone("true");
                }else if("否".equals(plan.getMilestone())){
                    plan.setMilestone("false");
                }
                if (!plan.getMilestone().equals(temporaryPlan.getMilestone())) {
                    PlanChangeVo planChangeVo = new PlanChangeVo();
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
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

        }

        for (PlanDto temporaryPlan : planChangeList) {
            ResourceLinkInfoDto resourceLink = new ResourceLinkInfoDto();
            resourceLink.setUseObjectId(temporaryPlan.getId());
            List<ResourceLinkInfoDto> resourceLinkList = resourceLinkInfoRemoteFeignServiceI.queryResourceList(
                    resourceLink, 1, 10, false);
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
                    PlanDto plan = planService.getPlanEntity(d.getUseObjectId());
                    if (d.getResourceName().equals(dev.getResourceName())
                            && plan.getPlanName().equals(dev.getPlanName())) {
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
                            String changeBeforeTime = DateUtil.getStringFromDate(d.getStartTime(),
                                    DateUtil.YYYY_MM_DD);
                            planChangeVo.setChangeBefore(changeBeforeTime);
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
                            String changeBeforeTime = DateUtil.getStringFromDate(d.getEndTime(),
                                    DateUtil.YYYY_MM_DD);
                            planChangeVo.setChangeBefore(changeBeforeTime);
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
                    planChangeVo.setPlanName(temporaryPlan.getPlanName());
                    planChangeVo.setField("(资源)" + d.getResourceName());
                    planChangeVo.setType("删除");
                    planChangeVo.setChangeBefore(d.getResourceName());
                    planChangeVo.setChangeAfter("-");
                    planChangeVoList.add(planChangeVo);
                }
            }

        }
        DataGridReturn data = new DataGridReturn(planChangeVoList.size(), planChangeVoList);
        String json = gson.toJson(data);
        TagUtil.ajaxResponse(response, json);

    }


    /**
     * Description: <br>
     * 负责人
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "queryUserListForModify")
    public void queryUserListForModify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String projectId = request.getParameter("projectId");
        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        try {
            List<TSUserDto> users = projRoleService.getUserInProject(projectId);
            for (int i = 0; i < users.size(); i++ ) {
                JSONObject obj = new JSONObject();
                obj.put("userKey", users.get(i).getId());
                obj.put("userName", users.get(i).getRealName() + "-" + users.get(i).getUserName());
                jsonList.add(obj);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String jsonResult = JSON.toJSONString(jsonList);
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
        out.close();
    }

    /**
     * Description: <br>
     * 里程碑
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param request
     * @param response
     * @throws IOException
     * @see
     */
    @RequestMapping(params = "queryMilestone")
    public void queryMilestone(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("formKey", "是");
        obj.put("formName", "是");
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("formKey", "否");
        obj2.put("formName", "否");
        jsonList.add(obj2);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String jsonResult = JSON.toJSONString(jsonList);
        PrintWriter out = response.getWriter();
        out.print(jsonResult);
        out.flush();
        out.close();
    }


    /**
     * 资源编辑页面跳转
     *
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(params = "goModify")
    public ModelAndView goModify(TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo,
                                 HttpServletRequest req)
            throws ParseException, UnsupportedEncodingException {
        String resourceId = req.getParameter("resourceId");
        // String useObjectId = req.getParameter("useObjectId");
        req.getSession().setAttribute("planchangemass-resourceId", resourceId);
        String planStartTime = req.getParameter("planStartTime");
        String planEndTime = req.getParameter("planEndTime");
        String useRate = req.getParameter("useRate");
        String planNameForResource = req.getParameter("planNameForResource");
        planNameForResource = URLDecoder.decode(planNameForResource, "UTF-8");
        req.getSession().setAttribute("planchangemass-planNameForResource", planNameForResource);
        if ("undefined".equals(useRate)) {
            useRate = "0";
        }
        String start = req.getParameter("start");
        String finish = req.getParameter("finish");

        // Plan p = resourceLinkInfoService.getEntity(Plan.class, useObjectId);
        req.setAttribute("planStartTime", planStartTime);
        req.setAttribute("planEndTime", planEndTime);
        List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
        Map<String, String> resourcePathMap = new HashMap<String, String>();
        Map<String, String> resourceNameMap = new HashMap<String, String>();
        for (ResourceDto resource : resourceListTemp) {
            resourcePathMap.put(resource.getId(), resource.getPath());
            resourceNameMap.put(resource.getId(), resource.getName());
        }
        tempPlanResourceLinkInfo.setResourceName(resourceNameMap.get(resourceId));
        tempPlanResourceLinkInfo.setResourceType(resourcePathMap.get(resourceId));

        tempPlanResourceLinkInfo.setUseRate(useRate);
        if (!"undefined".equals(start)) {
            Date startTime = DateUtil.getDateFromString(start, DateUtil.YYYY_MM_DD);
            tempPlanResourceLinkInfo.setStartTime(startTime);
        }
        if (!"undefined".equals(finish)) {
            Date finishTime = DateUtil.getDateFromString(finish, DateUtil.YYYY_MM_DD);
            tempPlanResourceLinkInfo.setEndTime(finishTime);
        }

        tempPlanResourceLinkInfo.setId(resourceId);
        tempPlanResourceLinkInfo.setResourceId(resourceId);
        req.setAttribute("tempPlanResourceLinkInfo_", tempPlanResourceLinkInfo);
        return new ModelAndView("com/glaway/ids/project/plan/resourceLinkInfoTempMass-modify");
    }



    /**
     * 删除交付物
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelResource")
    @ResponseBody
    public AjaxJson doDelResource(String ids, String planName, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteResourceSuccess");
        try {
            List<TempPlanResourceLinkInfoDto> list = new ArrayList<>();
            List<TempPlanResourceLinkInfoDto> removeList = new ArrayList<>();
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                    "planChangeMass-resourceLinkInfoList");
            for (int i = 0; i < resourceLinkInfoList.size(); i++ ) {
                if (ids.contains(resourceLinkInfoList.get(i).getResourceId()) && planName.contains(resourceLinkInfoList.get(i).getPlanName())) {
                    removeList.add(resourceLinkInfoList.get(i));
                }
                else {
                    list.add(resourceLinkInfoList.get(i));
                }
            }
            resourceLinkInfoList.clear();
            resourceLinkInfoList.addAll(list);
            request.getSession().setAttribute("removeList", removeList);
            request.getSession().setAttribute("planChangeMass-resourceLinkInfoList", resourceLinkInfoList);
            log.info(message, ids, "");
        }
        catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.deleteResourceFail");
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
     * 更新资源
     *
     * @param
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateResource")
    @ResponseBody
    public AjaxJson doUpdateResource(TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo,
                                     HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.resource.doUpdateResourceSuccess");
        String resourceId = (String)request.getSession().getAttribute("planchangemass-resourceId");
        String planNameForResource = (String)request.getSession().getAttribute(
                "planchangemass-planNameForResource");
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");
        try {
            List<TempPlanResourceLinkInfoDto> resourceLinkInfoListTemp = new ArrayList<TempPlanResourceLinkInfoDto>();
            // List<TempPlanResourceLinkInfo> resourceLinkInfoListTemp2 = new
            // ArrayList<TempPlanResourceLinkInfo>();
            resourceLinkInfoListTemp.addAll(resourceLinkInfoList);
            resourceLinkInfoList.clear();
            for (TempPlanResourceLinkInfoDto t : resourceLinkInfoListTemp) {
                if (resourceId.equals(t.getResourceId())
                        && planNameForResource.equals(t.getPlanName())) {
                    tempPlanResourceLinkInfo.setUseObjectId(t.getUseObjectId());
                    tempPlanResourceLinkInfo.setPlanName(t.getPlanName());
                    tempPlanResourceLinkInfo.setResourceLinkId(t.getResourceLinkId());
                    tempPlanResourceLinkInfo.setResourceInfo(t.getResourceInfo());
                    tempPlanResourceLinkInfo.setResourceType(t.getResourceType());
                    resourceLinkInfoList.add(tempPlanResourceLinkInfo);
                }
                else {
                    resourceLinkInfoList.add(t);
                }
            }
            request.getSession().setAttribute("planChangeMass-resourceLinkInfoList",
                    resourceLinkInfoList);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.resource.doUpdateResourceFail");
        }
        finally {
            // systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceListfresh")
    public void resourceListfresh(HttpServletRequest request, HttpServletResponse response) {

        List<TempPlanResourceLinkInfoDto> resourceLinkInfoListTemp = new ArrayList<TempPlanResourceLinkInfoDto>();
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = (List<TempPlanResourceLinkInfoDto>)request.getSession().getAttribute(
                "planChangeMass-resourceLinkInfoList");
        List<PlanDto> planChangeList = (List<PlanDto>)request.getSession().getAttribute(
                "planChangeMass-planChangeList");
        for (TempPlanResourceLinkInfoDto resourceLink : resourceLinkInfoList) {
            TempPlanResourceLinkInfoDto tempResourceLink = new TempPlanResourceLinkInfoDto();
            tempResourceLink.setPlanName(resourceLink.getPlanName());
            tempResourceLink.setResourceLinkId(resourceLink.getResourceLinkId());
            tempResourceLink.setId(resourceLink.getResourceId());
            tempResourceLink.setAvaliable(resourceLink.getAvaliable());
            tempResourceLink.setBizCurrent(resourceLink.getBizCurrent());
            tempResourceLink.setBizId(resourceLink.getBizId());
            tempResourceLink.setBizVersion(resourceLink.getBizVersion());
//            tempResourceLink.setPolicy(resourceLink.getPolicy());
            tempResourceLink.setResourceId(resourceLink.getResourceId());
            ResourceDto resource = resourceRemoteFeignServiceI.getEntity(resourceLink.getResourceId());
            tempResourceLink.setResourceInfo(resource);
            tempResourceLink.setResourceName(resourceLink.getResourceName());
            List<ResourceDto> resourceListTemp = resourceRemoteFeignServiceI.getAllResourceInfos();
            Map<String, String> resourcePathMap = new HashMap<String, String>();
            Map<String, String> resourceNameMap = new HashMap<String, String>();
            for (ResourceDto r : resourceListTemp) {
                resourcePathMap.put(r.getId(), r.getPath());
                resourceNameMap.put(r.getId(), r.getName());
            }
            tempResourceLink.setResourceType(resourcePathMap.get(resourceLink.getResourceId()));
            tempResourceLink.setSecurityLevel(resourceLink.getSecurityLevel());
            tempResourceLink.setUseObjectId(resourceLink.getUseObjectId());
            tempResourceLink.setUseObjectType(resourceLink.getUseObjectType());
            tempResourceLink.setUseRate(resourceLink.getUseRate());
            tempResourceLink.setStartTime(resourceLink.getStartTime());
            tempResourceLink.setEndTime(resourceLink.getEndTime());

            tempResourceLink.setStartTimeOverflow(false);
            for (PlanDto p : planChangeList) {
                // Date a = p.getPlanStartTime();
                // DateUtil.dateToString(a,DateUtil.LONG_DATE_FORMAT);
                // Date b = p.getPlanEndTime();
                // DateUtil.dateToString(b,DateUtil.LONG_DATE_FORMAT);
                // tempResourceLink.setPlanStartTime(a.toString());
                // tempResourceLink.setPlanEndTime(b.toString());
                tempResourceLink.setPlanStartTime(DateUtil.dateToString(p.getPlanStartTime(),
                        DateUtil.LONG_DATE_FORMAT));
                tempResourceLink.setPlanEndTime(DateUtil.dateToString(p.getPlanEndTime(),
                        DateUtil.LONG_DATE_FORMAT));
                // tempResourceLink.setPlanStartTime(p.getPlanStartTime().toString());
                // tempResourceLink.setPlanEndTime(p.getPlanEndTime().toString());
                if (p.getId().equals(resourceLink.getResourceLinkId())) {
                    tempResourceLink.setPlanName(p.getPlanName());
                    if ((tempResourceLink.getStartTime().getTime() < p.getPlanStartTime().getTime())
                            || (tempResourceLink.getStartTime().getTime() > p.getPlanEndTime().getTime())) {
                        tempResourceLink.setStartTimeOverflow(true);
                    }
                    tempResourceLink.setEndTime(resourceLink.getEndTime());
                    tempResourceLink.setEndTimeOverflow(false);
                    if ((tempResourceLink.getEndTime().getTime() > p.getPlanEndTime().getTime())
                            || (tempResourceLink.getEndTime().getTime() < p.getPlanStartTime().getTime())) {
                        tempResourceLink.setEndTimeOverflow(true);
                    }
                    break;
                }
            }
            /*
             * Plan p = planService.getEntity(Plan.class, resourceLink.getUseObjectId());
             * if ((tempResourceLink.getStartTime().getTime() < p.getPlanStartTime().getTime())
             * || (tempResourceLink.getStartTime().getTime() > p.getPlanEndTime().getTime())) {
             * tempResourceLink.setStartTimeOverflow(true);
             * }
             * tempResourceLink.setEndTime(resourceLink.getEndTime());
             * tempResourceLink.setEndTimeOverflow(false);
             * if ((tempResourceLink.getEndTime().getTime() > p.getPlanEndTime().getTime())
             * || (tempResourceLink.getEndTime().getTime() < p.getPlanStartTime().getTime())) {
             * tempResourceLink.setEndTimeOverflow(true);
             * }
             */

            resourceLinkInfoListTemp.add(tempResourceLink);
        }
        resourceLinkInfoList.clear();
        resourceLinkInfoList.addAll(resourceLinkInfoListTemp);
        request.getSession().setAttribute("planChangeMass-resourceLinkInfoList",
                resourceLinkInfoList);
        long count = resourceLinkInfoList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceLinkInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);

    }

    /**
     * 项目计划变更查看页面跳转
     *
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangePlanView")
    public ModelAndView goChangePlanView(TemporaryPlanDto temporaryPlan, HttpServletRequest req) {
        String formId = req.getParameter("taskNumber");
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);

        temporaryPlan = planService.getTemporaryPlanEntity(approve.get(0).getPlanId());
        if (temporaryPlan == null) {
            TemporaryPlanDto t = new TemporaryPlanDto();
            t.setPlanId(approve.get(0).getPlanId());
            t.setFormId(formId);
            List<TemporaryPlanDto> list = feignPlanChangeServiceI.queryTemporaryPlanList(t, 1, 10, false);
            temporaryPlan = list.get(0);
        }
        if (temporaryPlan != null) {
            if (temporaryPlan.getOwnerInfo() != null) {
                temporaryPlan.setOwnerDept(temporaryPlan.getOwnerInfo().getTSDepart().getDepartname());
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
                    PlanDto p = planService.getPlanEntity(id);
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
            req.getSession().setAttribute("planchangeMass-planId", temporaryPlan.getPlanId());
            req.getSession().setAttribute("planchangeMass-uploadSuccessFileName",
                    temporaryPlan.getChangeInfoDocName());
        }
        req.getSession().setAttribute("planchangeMass-formId", formId);
        req.setAttribute("projectId", temporaryPlan.getProjectId());

        List<TSUserDto> userList = new ArrayList<>();

        userList = projRoleService.getUserInProject(temporaryPlan.getProjectId());

        FeignJson fj = planService.getLifeCycleStatusList();
        String lifeStaStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> statusList = JSON.parseArray(lifeStaStr,LifeCycleStatus.class);
        String jonStr3 = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        req.setAttribute("statusList", jonStr3);
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = planBusinessConfigServiceI.searchUseableBusinessConfigs(planLevel);
   //     List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListString),new TypeReference<List<BusinessConfig>>(){});

        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        String b = JSON.toJSONString(planLevelList);
        req.setAttribute("planLevelList1", b);
        req.setAttribute("planLevelList", jonStr);

        for (TSUserDto t : userList) {
            t.setRealName(t.getRealName() + "-" + t.getUserName());
        }


        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        String aaa = JSON.toJSONString(userList);

        req.setAttribute("userList1", aaa);
        req.setAttribute("userList", jonStr2);
        req.setAttribute("temporaryPlan_", temporaryPlan);
        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassOnTreeViewNew");
    }

    /**
     * 批量变更查看第一个页签
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeMassFour")
    public ModelAndView goChangeMassFour(PlanDto plan, HttpServletRequest req) {

        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassTabFour");
    }


    /**
     * 资源页面初始化时获取交付物列表
     *
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "resourceFlowList")
    public void resourceFlowList(HttpServletRequest request, HttpServletResponse response) {
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = new ArrayList<>();
        String formId = (String)request.getSession().getAttribute("planchangeMass-formId");

        TempPlanResourceLinkInfoDto resourceLinkInfo = new TempPlanResourceLinkInfoDto();
        resourceLinkInfo.setUseObjectId(formId);
        resourceLinkInfoList = planRemoteFeignServiceI.queryResourceChangeListOrderBy(
                resourceLinkInfo, 1, 10, false);
        for (TempPlanResourceLinkInfoDto t : resourceLinkInfoList) {
            TemporaryPlanDto temporaryPlan = new TemporaryPlanDto();
            temporaryPlan.setPlanId(t.getResourceLinkId());
            List<TemporaryPlanDto> temporaryPlanListTemp = feignPlanChangeServiceI.queryTemporaryPlanList(
                    temporaryPlan, 1, 10, false);
            for(TemporaryPlanDto curTemporaryPlanDto: temporaryPlanListTemp) {
                if (!CommonUtil.isEmpty(t.getResourceId())) {
                    ResourceDto resourceDto = resourceService.getEntity(t.getResourceId());
                    t.setResourceName(resourceDto.getName());
                }
                t.setPlanName(curTemporaryPlanDto.getPlanName());
                t.setPlanStartTime(DateUtil.dateToString(
                        curTemporaryPlanDto.getPlanStartTime(), DateUtil.YYYY_MM_DD));
                t.setPlanEndTime(DateUtil.dateToString(curTemporaryPlanDto.getPlanEndTime(),
                        DateUtil.YYYY_MM_DD));
            }

        }
        request.getSession().setAttribute("planChangeMass-resourceLinkInfoList",
                resourceLinkInfoList);
        long count = resourceLinkInfoList.size();
        String json = com.alibaba.fastjson.JSONArray.toJSONString(resourceLinkInfoList);
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);

    }

    /**
     * 批量变更查看第一个页签
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeMassTwo")
    public ModelAndView goChangeMassTwo(PlanDto plan, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        req.setAttribute("projectId", projectId);
        // 计划等级
        BusinessConfig planLevel = new BusinessConfig();
        planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
        List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
  //      List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(str1),new TypeReference<List<BusinessConfig>>(){});
        List<TSUserDto> userList = new ArrayList<TSUserDto>();

        userList = projRoleService.getUserInProject(projectId);
        String jonStr = JsonUtil.getCodeTitleJson(planLevelList, "id", "name");
        req.setAttribute("planLevelList", jonStr);

        for (TSUserDto t : userList) {
            t.setRealName(t.getRealName() + "-" + t.getUserName());
        }


        String jonStr2 = JsonUtil.getCodeTitleJson(userList, "id", "realName");
        req.setAttribute("userList", jonStr2);
        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassTabTwo");
    }


    /**
     * 批量变更查看第一个页签
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeMassOne")
    public ModelAndView goChangeMassOne(PlanDto plan, HttpServletRequest req) {
        String id = req.getParameter("id");
        TemporaryPlanDto t = planService.getTemporaryPlanEntity(id);
        if(!CommonUtil.isEmpty(t.getChangeType())){
            BusinessConfig bc = businessConfigService.getBusinessConfig(t.getChangeType());
            t.setChangeTypeInfo(bc);
        }
        req.setAttribute("temporaryPlan_", t);
        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassTabOne");
    }

    /**
     * 批量变更查看第一个页签
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeMassThree")
    public ModelAndView goChangeMassThree(PlanDto plan, HttpServletRequest req) {

        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassTabThree");
    }

    /**
     * 批量变更查看第一个页签
     *
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goChangeMassFive")
    public ModelAndView goChangeMassFive(PlanDto plan, HttpServletRequest req) {

        return new ModelAndView("com/glaway/ids/project/plan/plan-changeMassTabFive");
    }

    /**
     * 项目计划页面初始化时获取计划列表
     *
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "planChangeSelectListView")
    public void planChangeSelectListView(PlanDto plan, HttpServletRequest request,
                                         HttpServletResponse response) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().setVersion(
                1.0).create();

        String formId = (String)request.getSession().getAttribute("planchangeMass-formId");
        List<PlanDto> planList = new ArrayList<PlanDto>();
        ApprovePlanInfoDto approvePlanInfo = new ApprovePlanInfoDto();
        approvePlanInfo.setFormId(formId);
        List<ApprovePlanInfoDto> approve = planService.queryAssignList(approvePlanInfo, 1, 10, false);
        List<TempPlanResourceLinkInfoDto> resourceLinkInfoList = new ArrayList<TempPlanResourceLinkInfoDto>();
        List<PlanDto> planChangeList = new ArrayList<PlanDto>();
        Map<String,TSUserDto> userDtoMap = userService.getAllUserIdsMap();
        for (ApprovePlanInfoDto a : approve) {
            TemporaryPlanDto t = feignPlanChangeServiceI.getTemporaryPlan(
                    a.getPlanId());
            PlanDto p = planService.getPlanEntity(t.getPlanId());
            PlanDto planTemp = new PlanDto();
            planTemp.setId(p.getId());
            planTemp.setPlanName(p.getPlanName());
            if (StringUtils.isNotEmpty(p.getPlanLevel())) {
                BusinessConfigDto planlevel = planService.getBusinessConfigEntity(
                        p.getPlanLevel());
                planTemp.setPlanLevel(p.getPlanLevel());
                planTemp.setPlanLevelInfo(planlevel);
            }
            if (StringUtils.isNotEmpty(p.getOwner())) {
                planTemp.setOwner(p.getOwner());
                TSUserDto tSUser = userDtoMap.get(p.getOwner());
                planTemp.setOwnerInfo(tSUser);
            }
            planTemp.setPlanStartTime(p.getPlanStartTime());
            planTemp.setPlanEndTime(p.getPlanEndTime());
            planTemp.setMilestone(p.getMilestone());
            planTemp.setWorkTime(p.getWorkTime());
            planList.add(planTemp);
        }

        planChangeList.clear();
        planChangeList.addAll(planList);
        request.getSession().setAttribute("planChangeMass-planChangeList", planChangeList);
        List<PlanDto> planList2 = new ArrayList<PlanDto>();
        for (PlanDto node : planList) {
            PlanDto p = new PlanDto();
            p.setId(node.getId());
            p.setParentPlan(node.getParentPlan());
            p.setParentPlanId(node.getParentPlanId());
            p.setTaskNameType(node.getTaskNameType());
            p.setAssigner(node.getAssigner());
            p.setMilestone(node.getMilestone());
            p.setPlanName(node.getPlanName());
            p.setPlanNumber(node.getPlanNumber());
            p.setPlanEndTime(node.getPlanEndTime());
            p.setPlanStartTime(node.getPlanStartTime());
            int a = 0;
            for (PlanDto q : planList) {
                if (q.getId().equals(p.getParentPlanId())) {
                    a = 1;
                    break;
                }
            }
            if (a == 0) {
                p.set_parentId(null);
            }
            else {
                p.set_parentId(p.getParentPlanId());
            }
            p.setOrder(String.valueOf(p.getStoreyNo()));
            if(!CommonUtil.isEmpty(node.getPlanLevelInfo())){
                node.setPlanLevelName(node.getPlanLevelInfo().getName());
            }
            if (!CommonUtil.isEmpty(node.getOwnerInfo())) {
                node.setOwnerRealName(node.getOwnerInfo().getRealName()+"-"+node.getOwnerInfo().getUserName());
            }
            if("true".equals(node.getMilestone())){
                node.setMilestone("是");
            }else if("false".equals(node.getMilestone())){
                node.setMilestone("否");
            }
            String planName =  "<a href='javascript:void(0);' onclick=\"viewPlanTwo(\'" + node.getId()
                    + "\')\" style='color:blue'>" + node.getPlanName() + "</a>";
            node.setPlanName(planName);
            planList2.add(node);
        }

//bug27431发现此处打开资源详情，对变更对比页签的资源数据产生了影响，后续有问题自行处理。
//        resourceLinkInfoList.clear();
//        TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo = new TempPlanResourceLinkInfoDto();
//        tempPlanResourceLinkInfo.setUseObjectId(formId);
//        List<TempPlanResourceLinkInfoDto> tempPlanResourceLinkInfoList = feignPlanChangeServiceI.queryTempPlanResourceLinkList(
//                tempPlanResourceLinkInfo, 1, 10, false);
//
//        resourceLinkInfoList.addAll(tempPlanResourceLinkInfoList);
//        request.getSession().setAttribute("planChangeMass-resourceLinkInfoList",
//                resourceLinkInfoList);
        request.getSession().setAttribute("planChangeMass-planList", planList2);
     /*   List<net.sf.json.JSONObject> rootList = planService.changePlansToJSONObjects(planList2);

        String resultJSON = JSON.toJSONString(rootList);*/
        for(PlanDto node : planList2){
            node.set_parentId(node.getParentPlanId());
        }
        DataGridReturn data = new DataGridReturn(planList2.size(), planList2);

        String json = gson.toJson(data);

        TagUtil.ajaxResponse(response, json);

    /*    try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
