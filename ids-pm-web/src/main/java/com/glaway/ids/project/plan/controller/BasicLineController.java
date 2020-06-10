package com.glaway.ids.project.plan.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.project.plan.dto.BasicLineDto;
import com.glaway.ids.project.plan.dto.BasicLinePlanDto;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.*;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 基线管理
 * @author sji
 * @date 2015-03-23 16:32:26
 * @version V1.0
 */
@Controller
@RequestMapping("/basicLineController")
public class BasicLineController extends BaseController {

    /**
     * 
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(BasicLineController.class);

    /**
     * 用户管理接口
     */
    @Autowired
    private FeignUserService userService;

    /**
     * 项目计划管理接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;

    /**
     * 基线管理接口
     */
    @Autowired
    private FeignBasicLineServiceI basicLineService;

    /**
     * 计划资源
     */
    @Autowired
    private ResourceLinkInfoRemoteFeignServiceI resourceLinkInfoService;

    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    /**
     * 名称库<br>
     */
    @Autowired
    private NameStandardRemoteFeignServiceI nameStandardService;

    /**
     * 项目角色人员服务实现接口<br>
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;


    /**
     * 
     */
    @Autowired
    private PlanFlowForworkFeignServiceI planFlowForworkService;

    /**
     * 
     */
    @Autowired
    private RdFlowTaskFlowResolveRemoteFeignServiceI flowTaskService;

    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;

    /**
     * 计划前置表
     */
    @Autowired
    private PreposePlanRemoteFeignServiceI preposePlanService;


    /**
     * 交付项接口
     */
    @Autowired
    private DeliverablesInfoRemoteFeignServiceI deliverablesInfoService;

    /**
     * 计划列表list
     */
    private List<BasicLineDto> basicLineList = new ArrayList<>();

    /**
     * 意义，目的和功能，以及被用到的地方<br>
     */
    private String message;

    /**
     * 基线列表 页面跳转
     * 
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "basicLine")
    public ModelAndView basicLine(HttpServletRequest request) {
        // 获取所属项目ID
        String projectId = request.getParameter("projectId");
        // String teamId = request.getParameter("teamId");
        BasicLineDto b = new BasicLineDto();
//        basicLineService.initBusinessBasicLine(b);
        String lifeStaStr = basicLineService.getLifeCycleStatusList();
        List<LifeCycleStatus> statusList = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeStaStr),new TypeReference<List<LifeCycleStatus> >(){});

        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        request.setAttribute("basicLineStatusList", jonStr);
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineList");
    }
    /**
     * 基线列表 页面跳转
     * 
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "basicLineContrast")
    public ModelAndView basicLineContrast(HttpServletRequest request) {
        // 获取所属项目ID
        request.setAttribute("id1", request.getParameter("id1"));
        request.setAttribute("id2", request.getParameter("id2"));
        return new ModelAndView("com/glaway/ids/project/plan/basicLineContrast");
    }
    

    /**
     * 基线新增页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goAddBasicLine")
    public ModelAndView goAddBasicLine(BasicLineDto basicLine, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        req.setAttribute("basicLine_", basicLine);
        req.setAttribute("projectId", projectId);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineAdd");
    }
    
    /**
     * 基线对比现有计划页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goContrastCurrentPlan")
    public ModelAndView goContrastCurrentPlan(BasicLineDto basicLine, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        String selectedId = req.getParameter("selectedId");
        req.setAttribute("basicLine_", basicLine);
        req.setAttribute("projectId", projectId);
        req.setAttribute("selectedId", selectedId);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineContrastCurrentPlan");
    }

    /**
     * 基线复制页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goCopyBasicLine")
    public ModelAndView goCopyBasicLine(BasicLineDto basicLine, HttpServletRequest req) {
        String basicLineIdForCopy = req.getParameter("id");
        String projectId = req.getParameter("projectId");
        basicLine = basicLineService.getBasicLineEntity(basicLineIdForCopy);
        req.setAttribute("copyBasicLineName", basicLine.getBasicLineName());
        req.setAttribute("basicLine_", basicLine);
        req.setAttribute("projectId", projectId);
        req.setAttribute("basicLineIdForCopy", basicLineIdForCopy);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineCopy");
    }

    /**
     * 基线新增页面初始化
     * 
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "basicLineCopyList")
    public void basicLineCopyList(BasicLinePlanDto basicLinePlan, HttpServletRequest request,
                                  HttpServletResponse response) {
        String basicLineIdForCopy = request.getParameter("basicLineIdForCopy");
        basicLinePlan.setBasicLineId(basicLineIdForCopy);
        List<BasicLinePlanDto> basicLinePlanList = basicLineService.queryBasicLinePlanList(
            basicLinePlan, 1, 10, false);
        List<BasicLinePlanDto> validList = new ArrayList<>();
        String projectId = "";
        for (BasicLinePlanDto basicPlan : basicLinePlanList) {
            if (StringUtils.isNotEmpty(basicPlan.getProjectId())) {
                projectId = basicPlan.getProjectId();
            }
        }
        PlanDto plan = new PlanDto();
        plan.setProjectId(projectId);
        List<PlanDto> validPlanList = planService.queryPlanList(plan, 1, 10, false);
        Map<String, String> validPlanMap = new HashMap<String, String>();
        for (PlanDto p : validPlanList) {
            validPlanMap.put(p.getId(), p.getId());
        }
        for (BasicLinePlanDto b : basicLinePlanList) {
            if (StringUtils.isNotEmpty(validPlanMap.get(b.getPlanId()))) {
                b.set_parentId(b.getParentPlanId());
                b.setOrder(String.valueOf(b.getStoreyNo()));
                validList.add(b);
            }
        }

        List<JSONObject> rootList = basicLineService.changePlansToJSONObjects(validList);

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
     * 项目计划查看页面跳转
     * 
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goCheck")
    public ModelAndView goCheck(PlanDto plan, HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            plan = planService.getPlanEntity(id);
            req.setAttribute("plan_", plan);
        }
        return new ModelAndView("com/glaway/ids/project/plan/plan-check");
    }

    /**
     * 项目计划查看页面跳转
     * 
     * @param plan
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goCheckFlow")
    public ModelAndView goCheckFlow(PlanDto plan, HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            BasicLinePlanDto basicLinePlan = basicLineService.getBasicLinePlanEntity(id);
            plan = planService.getPlanEntity(basicLinePlan.getPlanId());
            req.setAttribute("plan_", plan);
        }
        return new ModelAndView("com/glaway/ids/project/plan/plan-check");
    }

    /**
     * 基线新增页面初始化
     * 
     * @param plan
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "basicLineAddList")
    public void basicLineAddList(PlanDto plan, HttpServletRequest request,
                                 HttpServletResponse response) {
        // 获取所属项目
        String projectId = request.getParameter("projectId");
        plan.setProjectId(projectId);
        List<PlanDto> planList = planService.queryPlanListExceptEditing(plan, 1, 10, false);

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
     * 基线新增页面初始化
     * 
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "basicLineViewList")
    public void basicLineViewList(BasicLinePlanDto basicLinePlan, HttpServletRequest request,
                                  HttpServletResponse response) {
        String basicLineId = request.getParameter("basicLineId");
        basicLinePlan.setBasicLineId(basicLineId);
        List<BasicLinePlanDto> basicLinePlanList = basicLineService.queryBasicLinePlanList(
            basicLinePlan, 1, 10, false);

        List<JSONObject> rootList = basicLineService.changePlansToJSONObjects(basicLinePlanList);

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
     * 基线驳回到首节点初始化
     * 
     * @param
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "basicLineEditList")
    public void basicLineEditList(BasicLinePlanDto basicLinePlan, HttpServletRequest request,
                                  HttpServletResponse response) {
        String basicLineId = request.getParameter("basicLineId");
        basicLinePlan.setBasicLineId(basicLineId);
        List<BasicLinePlanDto> basicLinePlanList = basicLineService.queryBasicLinePlanList(
            basicLinePlan, 1, 10, false);

        List<BasicLinePlanDto> basicLinePlanListTemp = new ArrayList<>();
        basicLinePlanListTemp.addAll(basicLinePlanList);
        basicLinePlanList.clear();
        for (BasicLinePlanDto b : basicLinePlanListTemp) {
            PlanDto p = planService.getPlanEntity(b.getPlanId());
            if (p != null && !"0".equals(p.getAvaliable())) {
                basicLinePlanList.add(b);
            }
        }

        for (BasicLinePlanDto node : basicLinePlanList) {
            node.set_parentId(node.getParentPlanId());
            node.setOrder(String.valueOf(node.getStoreyNo()));
        }

        List<JSONObject> rootList = basicLineService.changePlansToJSONObjects(basicLinePlanList);

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
     * 项目计划新增或更新后保存
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveBasicLine")
    @ResponseBody
    public AjaxJson saveBasicLine(BasicLineDto basicLine, String ids, String basicLineName,
                                  String remark, HttpServletRequest request) {
        String userId = UserUtil.getCurrentUser().getId();
        String userName = UserUtil.getCurrentUser().getUserName();
        String type = request.getParameter("type");
        String projectId = request.getParameter("projectId");
        basicLine.setCreateTime(new Date());
        basicLine.setCreateBy(userId);
        basicLine.setCreateName(userName);
        FeignJson feignJson = basicLineService.saveBasicLine(basicLine,ids,basicLineName,remark,type,projectId);

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(feignJson.isSuccess());
        ajaxJson.setMsg(feignJson.getMsg());
        ajaxJson.setAttributes(feignJson.getAttributes());
        ajaxJson.setObj(feignJson.getObj());
        return ajaxJson;
    }
    
    
    
    
    /**
     * 项目计划新增或更新后保存
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "saveBasicLineTemp")
    @ResponseBody
    public AjaxJson saveBasicLineTemp(BasicLineDto basicLine, String ids, String basicLineName,
                                  String remark, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.saveBasicLineSuccess");
        try {
            String type = request.getParameter("type");
            String projectId = request.getParameter("projectId");
            basicLine.setAvaliable("1");
            if (StringUtils.isNotEmpty(type)) { // 当前计划与基线对比，需优化，不要存在数据库中
                basicLine.setAvaliable("0");  
            }
            basicLine.setProjectId(projectId);
            basicLine.setBasicLineName(basicLineName);
            basicLine.setRemark(remark);
            basicLine.setCreateTime(new Date());
            basicLine.setId(projectId);
            request.getSession().setAttribute("basicline-contrast",basicLine);
            
            //basicLineService.initBusinessObject(basicLine);
            //basicLineService.saveOrUpdate(basicLine);

            //planFlowForworkService.saveBasicLineForWork(ids, basicLine, projectId);
            List<BasicLinePlanDto> basicLinePlanListTemp = new ArrayList<>();
            PlanDto condition = new PlanDto();
            condition.setProjectId(projectId);
            List<PlanDto> planList = planService.queryPlanListForTreegrid(condition);
            Map<String, PlanDto> plansMap = new HashMap<String, PlanDto>();
            for (PlanDto plan : planList) {
                plansMap.put(plan.getId(), plan);
            }
            // 将计划的输出按照key为计划ID,value为计划所对应的输出list 组成map
            List<DeliverablesInfoDto> deliverablesAllList = deliverablesInfoService.getDeliverablesByProject(projectId);
            Map<String, List<DeliverablesInfoDto>> deliverablesMap = new HashMap<>();
            for (DeliverablesInfoDto deliverablesInfo : deliverablesAllList) {
                List<DeliverablesInfoDto> list = new ArrayList<>();
                if (!CommonUtil.isEmpty(deliverablesMap.get(deliverablesInfo.getUseObjectId()))) {
                    list = deliverablesMap.get(deliverablesInfo.getUseObjectId());
                }
                list.add(deliverablesInfo);
                deliverablesMap.put(deliverablesInfo.getUseObjectId(), list);
            }
            for (String id : ids.split(",")) {
                BasicLinePlanDto basicLinePlan = new BasicLinePlanDto();
                PlanDto plan = plansMap.get(id);
                basicLinePlan.setPlanId(plan.getId());
                basicLinePlan.setBizCurrent(plan.getBizCurrent());
                basicLinePlan.setAssigner(plan.getAssigner());
                basicLinePlan.setAssignerInfo(plan.getAssignerInfo());
                basicLinePlan.setAssignTime(plan.getAssignTime());
                basicLinePlan.setBasicLine(basicLine);
                //basicLinePlan.setBasicLineId(basicLine.getId());
                basicLinePlan.setPlanType(plan.getPlanType());
                basicLinePlan.setCreateBy(plan.getCreateBy());
                basicLinePlan.setCreateName(plan.getCreateName());
                basicLinePlan.setCreateTime(plan.getCreateTime());
                basicLinePlan.setCreator(plan.getCreateBy());
                basicLinePlan.setCreatorInfo(plan.getCreator());
                List<DeliverablesInfoDto> deliverablesList = deliverablesMap.get(plan.getId());
                if (!CommonUtil.isEmpty(deliverablesList)) {
                    String deliverables = "";
                    for (DeliverablesInfoDto deliverablesInfo : deliverablesList) {
                        if (StringUtils.isNotEmpty(deliverables)) {
                            deliverables = deliverables + "," + deliverablesInfo.getName();
                        }
                        else {
                            deliverables = deliverablesInfo.getName();
                        }
                    }
                    basicLinePlan.setDeliverables(deliverables);
                }

                basicLinePlan.setImplementation(plan.getImplementation());
                basicLinePlan.setMilestone(plan.getMilestone());
                basicLinePlan.setMilestoneName(plan.getMilestoneName());
                basicLinePlan.setOwner(plan.getOwner());
                basicLinePlan.setOwnerDept(plan.getOwnerDept());
                basicLinePlan.setOwnerInfo(plan.getOwnerInfo());
                basicLinePlan.setParentPlanId(plan.getParentPlanId());
                basicLinePlan.setParentStorey(plan.getParentStorey());
                basicLinePlan.setPlanEndTime(plan.getPlanEndTime());
                basicLinePlan.setPlanLevel(plan.getPlanLevel());
                basicLinePlan.setPlanLevelInfo(plan.getPlanLevelInfo());
                basicLinePlan.setPlanName(plan.getPlanName());
                basicLinePlan.setPlanStartTime(plan.getPlanStartTime());
                basicLinePlan.setPreposeIds(plan.getPreposeIds());
                basicLinePlan.setProgressRate(plan.getProgressRate());
                basicLinePlan.setProject(plan.getProject());
                basicLinePlan.setProjectId(plan.getProjectId());
                basicLinePlan.setRemark(plan.getRemark());
                basicLinePlan.setStatus(plan.getStatus());
                basicLinePlan.setStoreyNo(plan.getStoreyNo());
                basicLinePlan.setUpdateBy(plan.getUpdateBy());
                basicLinePlan.setUpdateName(plan.getUpdateName());
                basicLinePlan.setUpdateTime(plan.getUpdateTime());
                basicLinePlan.setWorkTime(plan.getWorkTime());
                //basicLineService.saveOrUpdate(basicLinePlan);
                basicLinePlan.setId(plan.getId());
                basicLinePlanListTemp.add(basicLinePlan);
            }

            String planIds = "";
            for (BasicLinePlanDto s : basicLinePlanListTemp) {
                planIds = planIds + s.getPlanId() + ",";
            }
            if (planIds.endsWith(",")) {
                planIds = planIds.substring(0, planIds.length() - 1);
            }

            for (BasicLinePlanDto b : basicLinePlanListTemp) {
                if (StringUtils.isNotEmpty(b.getParentPlanId())) {
                    PlanDto p = plansMap.get(b.getPlanId());
                    if (StringUtils.isNotEmpty(p.getParentPlanId())) {
                        if (planIds.contains(p.getParentPlanId())) {
                            for (BasicLinePlanDto basicLinePlan : basicLinePlanListTemp) {
                                if (p.getParentPlanId().equals(basicLinePlan.getPlanId())) {
                                    b.setParentPlanId(basicLinePlan.getId());
                                }
                            }
                        }
                        else {
                            b.setParentPlanId(null);
                        }
                    }
                    //basicLineService.saveOrUpdate(b);
                }
            }
            request.getSession().setAttribute("basicline-contrastList",basicLinePlanListTemp);
            log.info(message, basicLine.getId(), basicLine.getId().toString());
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.saveBasicLineFail");
            log.error(message, e, "", basicLine.getId().toString());
            Object[] params = new Object[] {message, basicLine.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            j.setMsg(message);;
            j.setObj(basicLine.getId());
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            return j;
        }
    }
    

    /**
     * 项目计划新增或更新后保存
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "copyBasicLine")
    @ResponseBody
    public AjaxJson copyBasicLine(BasicLineDto basicLine, String ids, String basicLineName,
                                  String remark, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.copyBasicLineSuccess");
        try {
            String projectId = request.getParameter("projectId");
            String basicLineIdForCopy = request.getParameter("basicLineIdForCopy");
            basicLine.setId(null);
            basicLine.setProjectId(projectId);
            basicLine.setAvaliable("1");
            basicLine.setBasicLineName(basicLineName);
            basicLine.setRemark(remark);
            basicLine.setCreateTime(new Date());
            basicLine.setCreateBy(UserUtil.getCurrentUser().getId());
            basicLine.setCreateName(UserUtil.getCurrentUser().getUserName());
            planFlowForworkService.copyBasicLineForWork(basicLine, ids, projectId, basicLineIdForCopy);
//            log.info(message, basicLine.getId(), basicLine.getId().toString());
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.copyBasicLineFail");
            log.error(message, e, "", basicLine.getId().toString());
            Object[] params = new Object[] {message, basicLine.getId().toString()}; // 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            j.setMsg(message);
            // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            return j;
        }
    }

    /**
     * 删除基线
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doDelBasicLine")
    @ResponseBody
    public AjaxJson doDelBasicLine(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.doDelBasicLineSuccess");
        try {
            String id = request.getParameter("id");
            BasicLineDto basicLine = basicLineService.getBasicLineEntity(id);
            if (StringUtils.isNotEmpty(basicLine.getProcInstId())) {
                workFlowFacade.getWorkFlowMonitorService().terminateProcessInstance(id,
                    basicLine.getProcInstId(), "");
                basicLineService.deleteBasicLine(basicLine);
            }
            else {
                basicLineService.deleteBasicLine(basicLine);
            }
            log.info(message);
        }
        catch (Exception e) {
            String id = request.getParameter("id");
            message = I18nUtil.getValue("com.glaway.ids.pm.project.plan.basicLine.doDelBasicLineFail");
            log.error(message, e);
            Object[] params = new Object[] {message, BasicLineDto.class.getClass() + " oids:" + id};// 异常原因：{0}；异常描述：{1}
            throw new GWException(GWConstants.ERROR_2005, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 冻结基线
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doFrozeBasicLine")
    @ResponseBody
    public AjaxJson doFrozeBasicLine(HttpServletRequest request) {
        String id = request.getParameter("id");
        FeignJson feignJson = basicLineService.doFrozeBasicLine(id);

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(feignJson.getMsg());
        return ajaxJson;

    }

    /**
     * 解冻基线
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUseBasicLine")
    @ResponseBody
    public AjaxJson doUseBasicLine(HttpServletRequest request) {
        String id = request.getParameter("id");
        FeignJson feignJson = basicLineService.doUseBasicLine(id);

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(feignJson.getMsg());
        return ajaxJson;

    }

    /**
     * 项目计划下达页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goSubmitBasicLine")
    public ModelAndView goSubmitBasicLine(BasicLineDto basicLine, HttpServletRequest req) {
        String basicLineId = req.getParameter("id");
        req.setAttribute("basicLineId", basicLineId);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineSubmit");
    }

    /**
     * 启动并提交基线审批流
     * 
     * @param
     * @param
     *
     * @param
     *
     * @param request
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startBasicLine")
    @ResponseBody
    public AjaxJson startBasicLine(HttpServletRequest request) {
        AjaxJson ajaxJson = null;
        try {
            String basicLineId = request.getParameter("basicLineId");
            String leader = request.getParameter("leader");
            String deptLeader = request.getParameter("deptLeader");
            leader = URLDecoder.decode(leader, "UTF-8");
            deptLeader = URLDecoder.decode(deptLeader, "UTF-8");
            String id = UserUtil.getCurrentUser().getId();
            FeignJson feignJson = basicLineService.startBasicLine(basicLineId,leader,deptLeader,id);

            ajaxJson = new AjaxJson();
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg(feignJson.getMsg());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ajaxJson;

    }

    /**
     * 驳回到首节点再次提交工作流
     * 
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "startBasicLineFlow")
    @ResponseBody
    public AjaxJson startBasicLineFlow(BasicLineDto basicLine, String basicLineName, String remark, HttpServletRequest req) {
        String basicLineId = req.getParameter("basicLineId");
        String taskId = req.getParameter("taskId");

        FeignJson feignJson = basicLineService.startBasicLineFlow(basicLine,basicLineId,taskId,basicLineName,remark);

        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg(feignJson.getMsg());
        return ajaxJson;
    }

    /**
     * 项目计划下达页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goBasicLineView")
    public ModelAndView goBasicLineView(HttpServletRequest req) {
        String basicLineForFlow = req.getParameter("taskNumber");
        BasicLineDto basicLine = basicLineService.getBasicLineEntity(basicLineForFlow);
        req.setAttribute("basicLine_", basicLine);
        req.setAttribute("basicLineId", basicLineForFlow);
        return new ModelAndView("com/glaway/ids/project/plan/basicLineView");
    }

    /**
     * 项目计划下达首节点页面跳转
     * 
     * @param
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goBasicLineFlow")
    public ModelAndView goBasicLineFlow(HttpServletRequest req) {
        String basicLineIdForFlow = req.getParameter("taskNumber");
        String taskId = req.getParameter("taskId");
        BasicLineDto basicLine = basicLineService.getBasicLineEntity(basicLineIdForFlow);
        req.setAttribute("basicLine_", basicLine);
        req.setAttribute("taskId", taskId);
        req.setAttribute("basicLineId", basicLineIdForFlow);
        req.setAttribute("basicLineProjectIdForFlow", basicLine.getProjectId());
        return new ModelAndView("com/glaway/ids/project/plan/basicLineEdit");
    }

    /**
     * 是否在名称库中
     * 
     * @param
     * @param request
     * @return
     * @see
     */
    @RequestMapping(params = "select")
    @ResponseBody
    public AjaxJson select(String id, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        PlanDto plan = planService.getPlanEntity(id);
        List<PlanDto> allplan = planService.getPlanAllChildren(plan);
        String ids = "";
        for (PlanDto s : allplan) {
            ids = ids + s.getId() + ",";
        }
        if (ids.endsWith(",")) {
            ids = ids.substring(0, ids.length() - 1);
        }
        j.setMsg(ids);
        return j;
    }

    /**
     * 查询计划等级
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusList")
    @ResponseBody
    public void statusList(HttpServletRequest request, HttpServletResponse response) {

        BasicLineDto b = new BasicLineDto();
        String lifeStaStr = basicLineService.getLifeCycleStatusList();
        List<LifeCycleStatus> statusList = JSON.parseObject(JsonFromatUtil.formatJsonToList(lifeStaStr),new TypeReference<List<LifeCycleStatus> >(){});

        String jonStr = JsonUtil.getCodeTitleJson(statusList, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认进入的首页面
     * 
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response)
        throws UnsupportedEncodingException {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        String userName = request.getParameter("userName");
        String projectId = request.getParameter("projectId");
        String bizCurrent = request.getParameter("bizCurrent");
        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("BasicLine.bizCurrent");
            conditionList.add(vo);
        }
        FeignJson feignJson = basicLineService.searchDatagrid(conditionList, projectId, userName);
        String datagridStr = (String) feignJson.getObj();
        TagUtil.ajaxResponse(response, datagridStr);
    }

}
