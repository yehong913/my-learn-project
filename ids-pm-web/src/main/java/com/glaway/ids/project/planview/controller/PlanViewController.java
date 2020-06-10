package com.glaway.ids.project.planview.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.dto.TSDepartDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.core.common.constant.Globals;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignDepartService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.foundation.tag.vo.ComboTreeNode;
import com.glaway.ids.config.constant.ConfigTypeConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.config.vo.EpsConfig;
import com.glaway.ids.constant.PlanviewConstant;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.PlanViewSetConditionDto;
import com.glaway.ids.project.planview.service.PlanViewRemoteFeignServiceI;
import com.glaway.ids.project.planview.util.TimeConditionUtil;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.util.CommonInitUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.glaway.foundation.cache.service.RedisService;
import com.glaway.foundation.common.dao.SessionFacade;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;

/**
 * 计划视图实现方法
 * 〈功能详细描述〉
 * 
 * @author likaiyong
 * @version 2018年5月30日
 * @see PlanViewController
 * @since
 */

@Controller
@RequestMapping("/planViewController")
public class PlanViewController extends BaseController {

    private static final OperationLog log = BaseLogFactory.getOperationLog(PlanViewController.class);

    /**
     * 视图服务实现接口
     */
/*    @Autowired
    private PlanViewServiceI planViewService;*/
    
    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;
    
    /**
     * 项目服务实现接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    /**
     * 配置业务接口
     */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;
    
    /**
     * 项目计划管理接口
     */
    @Autowired
    private PlanRemoteFeignServiceI planService;
    

    @Autowired
    private FeignSystemService feignSystemService;
    
    /**
     * redis缓存服务
     */
    @Autowired
    private RedisService redisService;


    @Autowired
    private PlanViewRemoteFeignServiceI planViewService;


    @Autowired
    private FeignDepartService departService;



    /**
     * Description: <br>获得视图树
     * 
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "list")
    @ResponseBody
    public void list(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        List<TreeNode> list = new ArrayList<TreeNode>();
        TSUserDto userDto = UserUtil.getCurrentUser();
        List<PlanViewInfoDto> planViewList = planService.constructionPlanViewTree(projectId, PlanviewConstant.PUBLIC,userDto.getId(),ResourceUtil.getCurrentUserOrg().getId());
        //公共视图
        TreeNode publicView = new TreeNode("0", null, PlanviewConstant.PUBLIC, PlanviewConstant.PUBLIC, true);
        publicView.setIcon("webpage/com/glaway/ids/common/tree-point.png");
        //私有视图
        TreeNode privateView = new TreeNode("1", null, PlanviewConstant.PRIVATE, PlanviewConstant.PRIVATE, true);
        privateView.setIcon("webpage/com/glaway/ids/common/tree-point.png");
        list.add(publicView);
        list.add(privateView);
        for (PlanViewInfoDto planView : planViewList) {
            TreeNode view = null;
            if(PlanviewConstant.PUBLIC_STATUS.equals(planView.getStatus())) {
                view = new TreeNode(planView.getId(), publicView.getId(), planView.getName(), planView.getName(),
                    true);
            }
            else {
                view = new TreeNode(planView.getId(), privateView.getId(), planView.getName(), planView.getName(),
                    true);
            }
           /* menu.setDataObject("qualitySystemFileDetailController.do?goSystemFileListForAdmin"
                               + "&folderId=" + menu.getId());*/
            view.setIconClose("webpage/com/glaway/ids/common/zTreeIcon_close.png");
            view.setIconOpen("webpage/com/glaway/ids/common/zTreeIcon_open.png");
            view.setIcon("webpage/com/glaway/ids/common/tree-point.png");
            list.add(view);

        }
        String json = JSONArray.toJSONString(list);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 查询设置视图
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "setPlanViewList")
    @ResponseBody
    public void setPlanViewList(HttpServletRequest request, HttpServletResponse response) {

        List<JSONObject> jsonList = new ArrayList<JSONObject>();
        JSONObject obj = new JSONObject();
        obj.put("id", "1");
        obj.put("name", PlanviewConstant.UPDATE);
        jsonList.add(obj);
        JSONObject obj2 = new JSONObject();
        obj2.put("id", "2");
        obj2.put("name", PlanviewConstant.SAVEAS);
        jsonList.add(obj2);
        JSONObject obj3 = new JSONObject();
        obj3.put("id", "3");
        obj3.put("name", PlanviewConstant.SETBY_DEPARTMENT);
        jsonList.add(obj3);
        JSONObject obj4 = new JSONObject();
        obj4.put("id", "4");
        obj4.put("name", PlanviewConstant.SETBY_TIME);
        jsonList.add(obj4);
        JSONObject obj5 = new JSONObject();
        obj5.put("id", "5");
        obj5.put("name", PlanviewConstant.SELFDEFINE);
        jsonList.add(obj5);
        JSONObject obj6 = new JSONObject();
        obj6.put("id", "6");
        obj6.put("name", PlanviewConstant.MANAGEMENT);
        jsonList.add(obj6);
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
     * 更新视图
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdateView")
    @ResponseBody
    public AjaxJson doUpdateView(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.updateSuccess");
        j.setSuccess(true);
        try {
            String planViewInfoId = request.getParameter("planViewInfoId");
            String showColumnIds = request.getParameter("showColumnIds");
            boolean flag = planViewService.isUpdateCondition(planViewInfoId, ResourceUtil.getCurrentUser().getId());
            if(!flag) {
                j.setSuccess(false);
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.saveAsView");
            }
            else {
                List<PlanViewSearchConditionDto> searchList = planViewService.getSearchConditionByPlanView(planViewInfoId);
                //视图已存在的条件list
                List<String> existConditionList = new ArrayList<String>();
                for(PlanViewSearchConditionDto c :  searchList) {
                    String attributeName = c.getAttributeName();
                    if(!CommonUtil.isEmpty(attributeName)) {
                        existConditionList.add(attributeName);
                    }
                }

                String planNumber = request.getParameter("planNumber");
                String isDelay = request.getParameter("isDelay");
                String planName = request.getParameter("planName");
                String planLevel = request.getParameter("planLevel");
                String bizCurrent = request.getParameter("bizCurrent");
                String userName = request.getParameter("userName");
                String planStartTime = request.getParameter("planStartTime");
                String planEndTime = request.getParameter("planEndTime");
                String workTime = request.getParameter("workTime");
                String workTimeCondition = request.getParameter("workTime_condition");
                String progressRate = request.getParameter("progressRate");
                String progressRateCondition = request.getParameter("progressRate_condition");
                String taskNameType = request.getParameter("taskNameType");
                String taskType = request.getParameter("taskType");
                List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
                if (StringUtils.isNotEmpty(planNumber)) {
                    ConditionVO conditionVO = new ConditionVO();
                    conditionVO.setKey("Plan.planNumber");
                    conditionVO.setValue(planNumber);
                    conditionList.add(conditionVO);
                }
                if (StringUtils.isNotEmpty(planName)) {
                    ConditionVO conditionVO = new ConditionVO();
                    conditionVO.setKey("Plan.planName");
                    conditionVO.setValue(planName);
                    conditionList.add(conditionVO);
                }

                if (StringUtils.isNotEmpty(planLevel)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValueArr(planLevel.split(","));
                    vo.setCondition("in");
                    vo.setValue(planLevel);
                    vo.setKey("Plan.planLevelInfo.id");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(isDelay)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValueArr(isDelay.split(","));
                    vo.setCondition("in");
                    vo.setValue(isDelay);
                    vo.setKey("Plan.isDelay");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(bizCurrent)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValueArr(bizCurrent.split(","));
                    vo.setCondition("in");
                    vo.setValue(bizCurrent);
                    vo.setKey("Plan.bizCurrent");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(planStartTime)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValue(planStartTime);
                    vo.setKey("Plan.planStartTime");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(planEndTime)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValue(planEndTime);
                    vo.setKey("Plan.planEndTime");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(workTime)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValue(workTime);
                    vo.setKey("Plan.workTime");
                    vo.setCondition(workTimeCondition);
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(progressRate)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValue(progressRate);
                    vo.setKey("Plan.progressRate");
                    vo.setCondition(progressRateCondition);
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(taskNameType)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValueArr(taskNameType.split(","));
                    vo.setCondition("in");
                    vo.setValue(taskNameType);
                    vo.setKey("Plan.taskNameType");
                    conditionList.add(vo);
                }

                if (StringUtils.isNotEmpty(taskType)) {
                    ConditionVO vo = new ConditionVO();
                    vo.setValueArr(taskType.split(","));
                    vo.setCondition("in");
                    vo.setValue(taskType);
                    vo.setKey("Plan.taskType");
                    conditionList.add(vo);
                }

                if(StringUtils.isNotEmpty(userName))
                {
                    ConditionVO vo = new ConditionVO();
                    vo.setValue(userName);
                    vo.setKey("Plan.owner");
                    conditionList.add(vo);
                }

                Map<String,Object> viewMap = new HashMap<String,Object>();
                viewMap.put("conditionList",conditionList);
                viewMap.put("existConditionList",existConditionList);
                viewMap.put("currentUser",ResourceUtil.getCurrentUser());
                planViewService.saveSearchCondition(viewMap, planViewInfoId,ResourceUtil.getCurrentUserOrg().getId());
                planViewService.updatePlanViewColumn(planViewInfoId,showColumnIds,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.updateFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 跳转到另存视图页面
     *
     * @return
     * @see
     */
    @RequestMapping(params = "goViewSaveAs")
    public ModelAndView goViewSaveAs(HttpServletRequest request, HttpServletRequest response) {
        String viewId = response.getParameter("viewId");
        String showColumnIds = request.getParameter("showColumnIds");
        if (!CommonUtil.isEmpty(viewId)) {
            request.setAttribute("viewId", viewId);
            request.setAttribute("showColumnIds", showColumnIds);

            String planNumber = request.getParameter("planNumber");
            String isDelay = request.getParameter("isDelay");
            String planName = request.getParameter("planName");
            String planLevel = request.getParameter("planLevel");
            String bizCurrent = request.getParameter("bizCurrent");
            String userName = request.getParameter("userName");
            String planStartTime = request.getParameter("planStartTime");
            String planEndTime = request.getParameter("planEndTime");
            String workTime = request.getParameter("workTime");
            String workTimeCondition = request.getParameter("workTime_condition");
            String progressRate = request.getParameter("progressRate");
            String progressRateCondition = request.getParameter("progressRate_condition");
            String taskNameType = request.getParameter("taskNameType");
            String taskType = request.getParameter("taskType");

            request.setAttribute("planNumber",planNumber);
            request.setAttribute("isDelay",isDelay);
            request.setAttribute("planName",planName);
            request.setAttribute("planLevel",planLevel);
            request.setAttribute("bizCurrent",bizCurrent);
            request.setAttribute("userName",userName);
            request.setAttribute("planStartTime",planStartTime);
            request.setAttribute("planEndTime",planEndTime);
            request.setAttribute("workTime",workTime);
            request.setAttribute("workTimeCondition",workTimeCondition);
            request.setAttribute("progressRate",progressRate);
            request.setAttribute("progressRateCondition",progressRateCondition);
            request.setAttribute("taskNameType",taskNameType);
            request.setAttribute("taskType",taskType);
        }

        return new ModelAndView("com/glaway/ids/project/planview/planView-save");
    }


    /**
     * 另存视图
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @ResponseBody
    @RequestMapping(params = "doViewSaveAs")
    public AjaxJson doViewSaveAs(HttpServletRequest request, HttpServletRequest response) {
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveSuccess");
        AjaxJson j = new AjaxJson();
        try {
            String viewId = request.getParameter("viewId");
            String showColumnIds = request.getParameter("showColumnIds");
            String name = request.getParameter("name");
            if (CommonUtil.isEmpty(viewId)) {
                j.setSuccess(false);
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveFailure");
            }
            else {
                // 校验视图名称是否重复doViewSaveAs
                List<PlanViewInfoDto> sameNames = planViewService.getPlanViewInfoByViewNameAndStatusAndCreateName(name,PlanviewConstant.PRIVATE_STATUS, UserUtil.getInstance().getUser().getUserName());

                if (!CommonUtil.isEmpty(sameNames)) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                    j.setSuccess(false);
                }
                else {
                    PlanViewInfoDto view = new PlanViewInfoDto();
                    view.setName(name);
                    FeignJson feignJson = planViewService.saveAsNewView(viewId, name,
                            ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());

                    String newViewId = String.valueOf(feignJson.getObj());

                    String planNumber = request.getParameter("planNumber");
                    String isDelay = request.getParameter("isDelay");
                    String planName = request.getParameter("planName");
                    String planLevel = request.getParameter("planLevel");
                    String bizCurrent = request.getParameter("bizCurrent");
                    String userName = request.getParameter("userName");
                    String planStartTime = request.getParameter("planStartTime");
                    String planEndTime = request.getParameter("planEndTime");
                    String workTime = request.getParameter("workTime");
                    String workTimeCondition = request.getParameter("workTime_condition");
                    String progressRate = request.getParameter("progressRate");
                    String progressRateCondition = request.getParameter("progressRate_condition");
                    String taskNameType = request.getParameter("taskNameType");
                    String taskType = request.getParameter("taskType");
                    List<ConditionVO> conditionList = new ArrayList<ConditionVO>();
                    if (StringUtils.isNotEmpty(planNumber)) {
                        ConditionVO conditionVO = new ConditionVO();
                        conditionVO.setKey("Plan.planNumber");
                        conditionVO.setValue(planNumber);
                        conditionList.add(conditionVO);
                    }
                    if (StringUtils.isNotEmpty(planName)) {
                        ConditionVO conditionVO = new ConditionVO();
                        conditionVO.setKey("Plan.planName");
                        conditionVO.setValue(planName);
                        conditionList.add(conditionVO);
                    }

                    if (StringUtils.isNotEmpty(planLevel)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValueArr(planLevel.split(","));
                        vo.setCondition("in");
                        vo.setValue(planLevel);
                        vo.setKey("Plan.planLevelInfo.id");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(isDelay)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValueArr(isDelay.split(","));
                        vo.setCondition("in");
                        vo.setValue(isDelay);
                        vo.setKey("Plan.isDelay");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(bizCurrent)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValueArr(bizCurrent.split(","));
                        vo.setCondition("in");
                        vo.setValue(bizCurrent);
                        vo.setKey("Plan.bizCurrent");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(planStartTime)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValue(planStartTime);
                        vo.setKey("Plan.planStartTime");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(planEndTime)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValue(planEndTime);
                        vo.setKey("Plan.planEndTime");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(workTime)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValue(workTime);
                        vo.setKey("Plan.workTime");
                        vo.setCondition(workTimeCondition);
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(progressRate)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValue(progressRate);
                        vo.setKey("Plan.progressRate");
                        vo.setCondition(progressRateCondition);
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(taskNameType)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValueArr(taskNameType.split(","));
                        vo.setCondition("in");
                        vo.setValue(taskNameType);
                        vo.setKey("Plan.taskNameType");
                        conditionList.add(vo);
                    }

                    if (StringUtils.isNotEmpty(taskType)) {
                        ConditionVO vo = new ConditionVO();
                        vo.setValueArr(taskType.split(","));
                        vo.setCondition("in");
                        vo.setValue(taskType);
                        vo.setKey("Plan.taskType");
                        conditionList.add(vo);
                    }

                    if(StringUtils.isNotEmpty(userName))
                    {
                        ConditionVO vo = new ConditionVO();
                        vo.setValue(userName);
                        vo.setKey("Plan.owner");
                        conditionList.add(vo);
                    }

                    List<String> existConditionList = new ArrayList<String>();

                    Map<String,Object> viewMap = new HashMap<String,Object>();
                    viewMap.put("conditionList",conditionList);
                    viewMap.put("existConditionList",existConditionList);
                    viewMap.put("currentUser",ResourceUtil.getCurrentUser());
                    planViewService.saveSearchCondition(viewMap, newViewId,ResourceUtil.getCurrentUserOrg().getId());
                    planViewService.updatePlanViewColumn(newViewId,showColumnIds,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());

                }
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 视图按部门设置页面跳转
     *
     * @return
     * @see
     */
    @RequestMapping(params = "goDepartment")
    public ModelAndView goDepartment(HttpServletRequest request, HttpServletRequest response) {
        String planViewInfoId = response.getParameter("planViewInfoId");
        if (!CommonUtil.isEmpty(planViewInfoId)) {
            response.setAttribute("planViewInfoId", planViewInfoId);
        }
        return new ModelAndView("com/glaway/ids/project/planview/view-setByDapartment");
    }


    /**
     * 保存按部门设置的条件
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSaveSetByDept")
    @ResponseBody
    public AjaxJson doSaveSetByDept(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveSuccess");
        try {
            String name = request.getParameter("name");
            String deptId = request.getParameter("deptId");
            //校验视图名称是否重复
            boolean flag = planViewService.getViewCountByStatusAndName(name, PlanviewConstant.PRIVATE_STATUS, "");
            if(flag) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
            }
            else {
                FeignJson feignJson = planViewService.saveSetConditionByDeaprtment(deptId, name,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
                String planViewInfoId = String.valueOf(feignJson.getObj());
                //保存展示列信息
                planViewService.saveColumnInfo(planViewInfoId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
                j.setObj(planViewInfoId);
                j.setSuccess(true);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    @RequestMapping(params = "dpeartCombotree")
    public void combotree(ComboTreeNode demo, HttpServletRequest request,
                          HttpServletResponse response) {
        List<ComboTreeNode> list = new ArrayList<ComboTreeNode>();

        List<TSDepartDto> departsList = departService.getAllDepart();
        for (TSDepartDto item : departsList) {
            if (Globals.User_Normal.equals(item.getStatus())) {
                ComboTreeNode node = new ComboTreeNode();
                node.setId(item.getId());
                node.setIsParent(item.getTSPDepart() == null);
                node.setOpen(false);
                node.setTitle(item.getDepartname());
                node.setName(item.getDepartname());
                node.setPid(item.getTSPDepart() != null ? item.getTSPDepart().getId() : "");
                list.add(node);
            }
        }

        String json = JSONArray.toJSONString(list);
        TagUtil.ajaxResponse(response, json);
    }


    /**
     * 项目计划新增页面跳转
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "goPlanViewTime")
    public ModelAndView goPlanViewTime(HttpServletRequest req) {
        req.getSession().setAttribute("viewId", req.getParameter("viewId"));

        return new ModelAndView("com/glaway/ids/project/planview/planViewTime-add");
    }


    /**
     * 设置时间条件 年，季，月
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "timeDesign")
    @ResponseBody
    public void timeDesign(HttpServletRequest request, HttpServletResponse response) {

        String jonStr = TimeConditionUtil.createYSM(null);
        // request.getSession().setAttribute("ysmValue", jonStr);
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置年时间条件
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "yearDesign")
    @ResponseBody
    public void yearDesign(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("tempYear",
                DateUtil.getStringFromDate(new Date(), DateUtil.YYYY));
        String jonStr = TimeConditionUtil.createYearRange(null, 50);
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置季或者月时间条件
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "seasonDesign")
    @ResponseBody
    public void seasonDesign(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("curIdString");
        request.getSession().removeAttribute("curMonthIdString");
        String type = request.getParameter("type");
        String jonStr = null;
        System.out.println(DateUtil.getMonth(new Date()));
        if (null == type) {
            type = "season";
        }
        if (type.contains("month")) {
            jonStr = TimeConditionUtil.createMonthRange(null);
        }
        else {

            jonStr = TimeConditionUtil.createSeaonRange(null);
        }
        try {
            request.getSession().setAttribute("curMonthIdString", DateUtil.getMonth(new Date()));
            request.getSession().setAttribute("curIdString", (DateUtil.getMonth(new Date())-1)/3+1);
            request.getSession().setAttribute("curSM", 0);

        }
        catch (NumberFormatException e1) {
            e1.printStackTrace();
        }

        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @SuppressWarnings({"finally", "unchecked"})
    @RequestMapping(params = "doViewSetByTime")
    @ResponseBody
    public AjaxJson doViewSetByTime(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveSuccess");
        PlanViewInfoDto view = new PlanViewInfoDto();
        PlanViewSetConditionDto condition = new PlanViewSetConditionDto();
        // 保存视图查询条件
        String viewId = request.getParameter("viewId");
        String viewName = request.getParameter("viewName");
        String timeC = request.getParameter("timeC");
        String yearC = request.getParameter("yearC");
        String seasonmonthC = request.getParameter("seasonmonthC");
        String planRangeC = request.getParameter("planRangeC");
        List<PlanViewInfoDto> planViewInfos = planViewService.getPlanViewInfoByViewNameAndStatusAndCreateName(viewName,PlanviewConstant.PRIVATE_STATUS,"");
        try {
            if(!CommonUtil.isEmpty(planViewInfos))
            {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
            }
            else
            {
                if (!CommonUtil.isEmpty(viewId)) {
                    String vId = null;
                    view = planViewService.getPlanViewInfoEntity(viewId);
                    condition = planViewService.getBeanByPlanViewInfoId(viewId);
                    if (CommonUtil.isEmpty(view)) {
                        view = new PlanViewInfoDto();
                        view.setCreateBy(UserUtil.getInstance().getUser().getId());
                        view.setName(viewName);
                        view.setIsDefault("false");
                        view.setStatus("PRIVATE");
                        CommonInitUtil.initGLVDataForCreate(view);
                        FeignJson fJson = planViewService.saveOrUpdatePlanViewInfo(view);
                        vId = String.valueOf(fJson.getObj());
                        // sessionFacade.saveOrUpdate(view);
                    }
                    else {
                        view = new PlanViewInfoDto();
                        view.setName(viewName);
                        view.setIsDefault("false");
                        view.setStatus("PRIVATE");
                        CommonInitUtil.initGLVDataForUpdate(view);
                        FeignJson fJson = planViewService.saveOrUpdatePlanViewInfo(view);
                        vId = String.valueOf(fJson.getObj());
                    }

                    if (CommonUtil.isEmpty(condition)) {
                        condition = new PlanViewSetConditionDto();
                        condition.setTimeRange(createTimeContition(timeC, yearC, seasonmonthC));
                        condition.setShowRange(planRangeC);
                        condition.setPlanViewInfoId(vId);
                        CommonInitUtil.initGLVDataForCreate(condition);
                        planViewService.saveOrUpdatePlanViewSetCondition(condition);

                    }
                    else {
                        condition = new PlanViewSetConditionDto();
                        condition.setTimeRange(createTimeContition(timeC, yearC, seasonmonthC));
                        condition.setShowRange(planRangeC);
                        condition.setPlanViewInfoId(vId);
                        CommonInitUtil.initGLVDataForUpdate(condition);
                        planViewService.saveOrUpdatePlanViewSetCondition(condition);
                    }
                    //保存展示列信息
                    planViewService.saveColumnInfo(vId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
                    j.setObj(vId);
                }
                else {
                    String vId = null;
                    view = new PlanViewInfoDto();
                    view.setCreateBy(UserUtil.getInstance().getUser().getId());
                    view.setName(viewName);
                    view.setIsDefault("false");
                    view.setStatus("PRIVATE");
                    CommonInitUtil.initGLVDataForCreate(view);
                    FeignJson fJson = planViewService.saveOrUpdatePlanViewInfo(view);
                    vId = String.valueOf(fJson.getObj());

                    condition = new PlanViewSetConditionDto();
                    condition.setTimeRange(createTimeContition(timeC, yearC, seasonmonthC));
                    condition.setShowRange(planRangeC);
                    condition.setPlanViewInfoId(view.getId());
                    CommonInitUtil.initGLVDataForCreate(condition);
                    planViewService.saveOrUpdatePlanViewSetCondition(condition);
                    //保存展示列信息
                    planViewService.saveColumnInfo(vId,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
                    j.setObj(vId);
                }
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveFailure");
            e.printStackTrace();
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 转换时间条件，保存到库<br>
     * 格式： year:2012,2015<br>
     * season:2018:1,2...4<br>
     * month:2099:1,2,3...12<br>
     *
     * @param time
     * @param year
     * @param season
     * @return
     * @see
     */
    private String createTimeContition(String time, String year, String season) {
        String result = "";
        if (time.equalsIgnoreCase("year")) {
            result += "year:" + year;
        }
        else if (time.equalsIgnoreCase("season")) {
            result += "season:" + year + ":" + TimeConditionUtil.convertText2Value(season);
        }
        else if (time.equalsIgnoreCase("month")) {
            result += "month:" + year + ":" + TimeConditionUtil.convertText2Value(season);
        }
        return result;
    }



    /**
     * 跳转自定义视图页面
     *
     * @author zhousuxia
     * @version 2018年6月14日
     * @see PlanViewController
     * @since
     */
    @RequestMapping(params = "goCustomPlanView")
    public ModelAndView goCustomPlanView(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String isModify = request.getParameter("isModify");
        String planModifyOperationCode = request.getParameter("planModifyOperationCode");
        String planAssignOperationCode = request.getParameter("planAssignOperationCode");
        String planDeleteOperationCode = request.getParameter("planDeleteOperationCode");
        String planChangeOperationCode = request.getParameter("planChangeOperationCode");
        String planDiscardOperationCode = request.getParameter("planDiscardOperationCode");
        String planRevocationOperationCode = request.getParameter("planRevocationOperationCode");
        String isPmo = request.getParameter("isPmo");
        String isProjectManger = request.getParameter("isProjectManger");
        String tempViewId = UUID.randomUUID().toString();
        request.setAttribute("projectId", projectId);
        request.setAttribute("isModify", isModify);
        request.setAttribute("planModifyOperationCode", planModifyOperationCode);
        request.setAttribute("planAssignOperationCode", planAssignOperationCode);
        request.setAttribute("planDeleteOperationCode", planDeleteOperationCode);
        request.setAttribute("planChangeOperationCode", planChangeOperationCode);
        request.setAttribute("planDiscardOperationCode", planDiscardOperationCode);
        request.setAttribute("planRevocationOperationCode", planRevocationOperationCode);
        request.setAttribute("isPmo", isPmo);
        request.setAttribute("isProjectManger", isProjectManger);
        request.setAttribute("tempViewId", tempViewId);
        return new ModelAndView("com/glaway/ids/project/planview/customView");
    }


    /**
     * 将选中的计划加到缓存中
     *
     * @author zhousuxia
     * @version 2018年6月14日
     * @see PlanViewController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "addMySelectPlan")
    @ResponseBody
    public AjaxJson addMySelectPlan(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planIds = request.getParameter("planId");
        String tempViewId = request.getParameter("tempViewId");
        try{
            List<PlanViewSetConditionDto> conditionList = new ArrayList<>();
            String conditionStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
            if(!CommonUtil.isEmpty(conditionStr)){
                conditionList = JSON.parseArray(conditionStr,PlanViewSetConditionDto.class);
            }

            Map<String,String> map = new HashMap<String, String>();
            if(CommonUtil.isEmpty(conditionList)){
                conditionList = new ArrayList<PlanViewSetConditionDto>();
            }else{
                for(PlanViewSetConditionDto con : conditionList){
                    map.put(con.getPlanId(), con.getPlanViewInfoId());
                }
            }
            if(!CommonUtil.isEmpty(planIds)){
               /* String resPla = "";
                if(CommonUtil.isEmpty(conditionList)){
                    conditionList = new ArrayList<PlanViewSetCondition>();
                }else{
                    for(PlanViewSetCondition setCon : conditionList){
                        if(!planIds.contains(setCon.getPlanId())){
                            if(CommonUtil.isEmpty(resPla)){
                                planIds.
                            }
                        }
                    }
                }*/

                for(String planId : planIds.split(",")){
                    if(CommonUtil.isEmpty(map.get(planId))){
                        PlanViewSetConditionDto condition = new PlanViewSetConditionDto();
                        condition.setPlanId(planId);
                        condition.setPlanViewInfoId(tempViewId);
                        condition.setCreateBy(UserUtil.getInstance().getUser().getId());
                        condition.setCreateFullName(UserUtil.getInstance().getUser().getRealName());
                        condition.setCreateName(UserUtil.getInstance().getUser().getUserName());
                        condition.setCreateTime(new Date());
                        conditionList.add(condition);
                    }
                }
            }
            String conStr = JSON.toJSONString(conditionList);
            redisService.setToRedis("MYSELECTPLANLIST", tempViewId, conStr);
            j.setSuccess(true);
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }


    /**
     * 将选中的计划从缓存中移除
     *
     * @author zhousuxia
     * @version 2018年6月14日
     * @see PlanViewController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "removeSelectedPlan")
    @ResponseBody
    public AjaxJson removeSelectedPlan(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String planIds = request.getParameter("planId");
        String tempViewId = request.getParameter("tempViewId");
        try{
            List<PlanViewSetConditionDto> conditionList = new ArrayList<>();
            String conditionStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
            if(!CommonUtil.isEmpty(conditionStr)){
                conditionList = JSON.parseArray(conditionStr,PlanViewSetConditionDto.class);
            }
            List<PlanViewSetConditionDto> resConditionList = new ArrayList<PlanViewSetConditionDto>();
            if(!CommonUtil.isEmpty(conditionList)){
                for(PlanViewSetConditionDto condition : conditionList){
                    if(!CommonUtil.isEmpty(planIds) && !planIds.contains(condition.getPlanId())){
                        resConditionList.add(condition);
                    }
                }
            }

            String conStr = JSON.toJSONString(resConditionList);
            redisService.setToRedis("MYSELECTPLANLIST", tempViewId, conStr);
            j.setSuccess(true);
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            return j;
        }
    }



    /**
     * 校验视图名称是否重复
     *
     * @author zhousuxia
     * @version 2018年6月14日
     * @see PlanViewController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "checkViewNameBeforeSave")
    @ResponseBody
    public AjaxJson checkViewNameBeforeSave(HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String name = request.getParameter("name");
        String message = "";
        try{
            //校验视图名称是否重复
            boolean flag = planViewService.getViewCountByStatusAndName(name, PlanviewConstant.PRIVATE_STATUS, "");
            if(flag) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
            }
        }catch(Exception e){
            j.setSuccess(false);
        }finally{
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 保存自定义视图
     *
     * @author zhousuxia
     * @version 2018年6月14日
     * @see PlanViewController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doSaveCustomView")
    @ResponseBody
    public AjaxJson doSaveCustomView(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveSuccess");
        try{
            String name = request.getParameter("name");
            String tempViewId = request.getParameter("tempViewId");
            String projectId = request.getParameter("projectId");
            List<PlanViewSetConditionDto> conditionList = new ArrayList<>();
            String conditionStr = (String)redisService.getFromRedis("MYSELECTPLANLIST", tempViewId);
            if(!CommonUtil.isEmpty(conditionStr)){
                conditionList = JSON.parseArray(conditionStr,PlanViewSetConditionDto.class);
            }
            PlanViewInfoDto info = new PlanViewInfoDto();
            info.setName(name);
            info.setId(tempViewId);
            info.setIsDefault("false");
            info.setStatus("PRIVATE");
            CommonInitUtil.initGLVDataForCreate(info);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("info",info);
            map.put("conditionList",conditionList);
            map.put("curUser",ResourceUtil.getCurrentUser());
            FeignJson fJson = planViewService.saveCustomView(map,projectId,ResourceUtil.getCurrentUserOrg().getId());
            //保存展示列信息
            planViewService.saveColumnInfo(String.valueOf(fJson.getObj()),ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            j.setObj(info.getId());
            j.setSuccess(true);
        }catch(Exception e){
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.setting.saveFailure");
            j.setSuccess(false);
        }finally{
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 管理视图页面跳转
     *
     * @return
     * @see
     */
    @RequestMapping(params = "goViewManagement")
    public ModelAndView goViewManagement(HttpServletRequest request, HttpServletRequest response) {
        String currentUserId = UserUtil.getCurrentUser().getId();
        response.setAttribute("currentUserId", currentUserId);
        return new ModelAndView("com/glaway/ids/project/planview/view-management");
    }


    /**
     * 视图列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    @ResponseBody
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        List<PlanViewInfoDto> result = planViewService.getViewList(projectId,ResourceUtil.getCurrentUser());

        String datagridStr = JSON.toJSONString(result);

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
     * 保存视图名称
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "unchecked", "finally" })
    @RequestMapping(params = "doSaveName")
    @ResponseBody
    public AjaxJson doSaveName(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.name.modifySuccess");
        j.setSuccess(true);
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String status = request.getParameter("status");
            //校验视图名称是否重复
            boolean flag = planViewService.getViewCountByStatusAndName(name, status, id);
            if(flag) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
            }
            else {
                if(name.length() > 30) {
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameLengthBeyond");
                    j.setSuccess(false);
                } else {
                    PlanViewInfoDto v = new PlanViewInfoDto();
                    v = planViewService.getPlanViewInfoEntity(id);
                    v.setName(name);
                    CommonInitUtil.initGLVDataForCreate(v);
                    planViewService.saveOrUpdatePlanViewInfo(v);
                }
            }

        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.name.modifyFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }



    /**
     * 视图展示条件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goViewSearchInfo")
    public ModelAndView goViewSearchInfo(HttpServletRequest request, HttpServletRequest response) {
        String planViewInfoId = response.getParameter("planViewInfoId");
        StringBuffer sb = new StringBuffer();
        List<PlanViewSearchConditionDto> searchList = planViewService.getSearchConditionByPlanView(planViewInfoId);
        List<PlanViewSetConditionDto> setList = planViewService.getSetConditionByPlanView(planViewInfoId);
        if(!CommonUtil.isEmpty(searchList)) {
            // 计划等级
            BusinessConfig planLevel = new BusinessConfig();
            planLevel.setConfigType(ConfigTypeConstants.PLANLEVEL);
            List<BusinessConfig> planLevelList = businessConfigService.searchUseableBusinessConfigs(planLevel);
     //       List<BusinessConfig> planLevelList = JSON.parseObject(JsonFromatUtil.formatJsonToList(planLevelListStr),new TypeReference<List<BusinessConfig>>(){});
            Map<String, String> levelMap = new HashMap<String, String>();
            for(BusinessConfig level : planLevelList) {
                levelMap.put(level.getId(), level.getName());
            }
            //计划状态
            Map<String, String> statusMap = new HashMap<String, String>();
            FeignJson fj = planService.getLifeCycleStatusList();
            String lifeCycleStatusStr = String.valueOf(fj.getObj());
            List<LifeCycleStatus> lifeCycleStatus = JSON.parseArray(lifeCycleStatusStr,LifeCycleStatus.class);
            for(LifeCycleStatus s : lifeCycleStatus) {
                statusMap.put(s.getName(), s.getTitle());
            }
            //计划类型
            Map<String, String> taskTypeMap = new HashMap<String, String>();
            // 获取活动名称字典表list
            String dictCode = "activeCategory";
            Map<String,List<TSTypeDto>> tsMap = feignSystemService.getAllTypesOfTypeGroup(ResourceUtil.getApplicationInformation().getAppKey());
            List<TSTypeDto> types = tsMap.get(dictCode);

            for(TSTypeDto t : types) {
                taskTypeMap.put(t.getTypecode(), t.getTypename());
            }
            sb.append("<p><b style='margin-left:10px'>查询条件："+ "</b></p>");
            for(PlanViewSearchConditionDto sc : searchList) {
                String attributeName = sc.getAttributeName();
                String attributeCondition = sc.getAttributeCondition();
                String attributeValue = sc.getAttributeValue();
                if("Plan.planName".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>计划名称：" + attributeValue);
                    sb.append("；<br></div>");
                }
                else if("Plan.isDelay".equals(attributeName)) {
                    if("Delay".equalsIgnoreCase(attributeValue)) {
                        sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>是否延期：延期未完成");
                        sb.append("；<br></div>");
                    }
                }
                else if("Plan.planLevelInfo.id".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>计划等级：" + doTranslateAttributeName(attributeValue, levelMap));
                    sb.append("；<br></div>");
                }
                else if("Plan.bizCurrent".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>状态：" + doTranslateAttributeName(attributeValue, statusMap));
                    sb.append("；<br></div>");
                }
                else if("Plan.planNumber".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>计划序号：" + attributeValue);
                    sb.append("；<br></div>");
                }
                else if("Plan.owner".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>负责人：" + attributeValue);
                    sb.append("；<br></div>");
                }
                else if("Plan.planStartTime".equals(attributeName)) {
                    if(!CommonUtil.isEmpty(attributeValue)) {
                        sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>开始时间：" + attributeValue.replace(",", "-"));
                        sb.append("；<br></div>");
                    }
                }
                else if("Plan.planEndTime".equals(attributeName)) {
                    if(!CommonUtil.isEmpty(attributeValue)) {
                        sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>结束时间：" + attributeValue.replace(",", "-"));
                        sb.append("；<br></div>");
                    }
                }
                else if("Plan.workTime".equals(attributeName)) {
                    if("le".equals(attributeCondition)) {
                        attributeValue = "≤"+ attributeValue;
                    }
                    else if("ge".equals(attributeCondition)) {
                        attributeValue = "≥"+ attributeValue;
                    }
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>工期：" + attributeValue);
                    sb.append("；<br></div>");
                }
                else if("Plan.progressRate".equals(attributeName)) {
                    if("le".equals(attributeCondition)) {
                        attributeValue = "≤"+ attributeValue;
                    }
                    else if("ge".equals(attributeCondition)) {
                        attributeValue = "≥"+ attributeValue;
                    }
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>进度：" + attributeValue);
                    sb.append("；<br></div>");
                }
                else if("Plan.taskNameType".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>计划类型：" + doTranslateAttributeName(attributeValue, taskTypeMap));
                    sb.append("；<br></div>");
                }
                else if("Plan.taskType".equals(attributeName)) {
                    sb.append("<div style ='font-size:12px; height:16px; margin:5px 5px;'>计划类别：" + attributeValue);
                    sb.append("；<br></div>");
                }
            }
        }
        String departStr = "";
        String timeRangeStr = "";
        String showRangeString ="";
        if(!CommonUtil.isEmpty(setList)) {
            Map<String, String> departMap = new HashMap<String, String>();
            List<TSDepartDto> departList = departService.getAllDepart();
            for(TSDepartDto d : departList) {
                departMap.put(d.getId(), d.getDepartname());
            }

            for(PlanViewSetConditionDto set : setList) {
                String departId = set.getDepartmentId();
                String timeRange = set.getTimeRange();
                String showRange =set.getShowRange();

                if(!CommonUtil.isEmpty(departId)) {
                    departStr = "<br><b style='margin-left:10px'>部门设置条件：</b><br><br>&nbsp;&nbsp;&nbsp;&nbsp;" + doTranslateAttributeName(departId, departMap) +"<br>";
                }
                if(!CommonUtil.isEmpty(timeRange)) {
                    timeRangeStr = "<div style ='font-size:12px; height:16px; margin:5px 15px;'><br>时间限制：" + TimeConditionUtil.toText(timeRange, ",") + "<br></div>";
                }
                if(!CommonUtil.isEmpty(showRange)) {
                    showRangeString = "<div style ='font-size:12px; height:16px; margin:5px 15px;'><br>展开范围：" + parseShowRange(showRange)+"</div>";
                }
            }
        }
        if(!CommonUtil.isEmpty(departStr)) {
            sb.append(departStr);
        }
        if(!CommonUtil.isEmpty(timeRangeStr)||CommonUtil.isEmpty(showRangeString))
        {
            sb.append("<br><b style='margin-left:10px'>时间条件：</b><br>");
            if(!CommonUtil.isEmpty(timeRangeStr)) {
                sb.append(timeRangeStr);
            }
            if(!CommonUtil.isEmpty(showRangeString)) {
                sb.append(showRangeString);
            }
        }
        response.setAttribute("viewSearchInfo", sb.toString());
        return new ModelAndView("com/glaway/ids/project/planview/view-searchInfo");
    }



    private String doTranslateAttributeName(String attributeValue, Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        if(!CommonUtil.isEmpty(attributeValue)) {
            String[] array = attributeValue.split(",");
            for(int i = 0; i < array.length; i++) {
                if(i == 0) {
                    sb.append(map.get(array[i]));
                }
                else {
                    sb.append("," + map.get(array[i]));
                }
            }
        }
        return sb.toString();
    }


    private String parseShowRange(String range)
    {
        String rs=null;
        if("toComplete".equalsIgnoreCase(range))
        {
            rs="需完成的计划";
        }
        else if("execPlan".equalsIgnoreCase(range))
        {
            rs="执行的计划";
        }else if("toStart".equalsIgnoreCase(range))
        {
            rs="需开始的计划";
        }
        return  rs;
    }


    /**
     * 发布视图页面跳转
     *
     * @return
     * @see
     */
    @RequestMapping(params = "goPublishView")
    public ModelAndView goPublishView(HttpServletRequest request, HttpServletRequest response) {
        String planViewInfoId = request.getParameter("planViewInfoId");
        String viewName = request.getParameter("viewName");
        String status = request.getParameter("status");
        String projectId = request.getParameter("projectId");
        String switchViewId = request.getParameter("switchViewId");
        String currentUserId = UserUtil.getCurrentUser().getId();
        response.setAttribute("planViewInfoId", planViewInfoId);
        response.setAttribute("projectId", projectId);
        response.setAttribute("viewName", viewName);
        response.setAttribute("status", status);
        response.setAttribute("currentUserId", currentUserId);
        response.setAttribute("switchViewId", switchViewId);
        return new ModelAndView("com/glaway/ids/project/planview/view-publish");
    }


    /**
     * 发布视图页面跳转
     *
     * @return
     * @see
     */
    @RequestMapping(params = "goSelectProject")
    public ModelAndView goSelectProject(HttpServletRequest request, HttpServletRequest response) {
        FeignJson fj = projectService.getLifeCycleStatusList();
        String lifeCycStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> lifeCycleList = JSON.parseObject(lifeCycStr,new TypeReference<List<LifeCycleStatus>>(){});
        String epsStr = epsConfigService.getList();
        List<EpsConfig> epsList = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsStr),new TypeReference<List<EpsConfig>>(){});

        JSONArray jsonLifeCycleList = new JSONArray();
        for (LifeCycleStatus status : lifeCycleList) {
            JSONObject obj = new JSONObject();
            obj.put("name", status.getName());
            obj.put("title", status.getTitle());
            jsonLifeCycleList.add(obj);
        }

        JSONArray jsonEpsList = new JSONArray();
        for (EpsConfig config : epsList) {
            JSONObject obj = new JSONObject();
            obj.put("id", config.getId());
            obj.put("name", String.valueOf(epsConfigService.getEpsNamePathById(config.getId()).getObj())); // 性能待优化，需先一次性查询出所有epsPath的Map，再从map中取对应的值
            jsonEpsList.add(obj);
        }

        String lifeCycleListStr = jsonLifeCycleList.toString().replaceAll("\"", "'");
        String epsListStr = jsonEpsList.toString().replaceAll("\"", "'");
        request.setAttribute("lifeCycleList", lifeCycleListStr);
        request.setAttribute("epsList", epsListStr);
        return new ModelAndView("com/glaway/ids/project/planview/select-project");
    }


    /**
     * 发布视图
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doPublishView")
    @ResponseBody
    public AjaxJson doPublishView(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.publishSuccess");
        j.setSuccess(true);
        try {
            String planViewInfoId = request.getParameter("planViewInfoId");
            String projectIds = request.getParameter("projectIds");
            String userIds = request.getParameter("userIds");
            String name = request.getParameter("name");
            //校验视图名称是否重复
            boolean flag = planViewService.getViewCountByStatusAndName(name, PlanviewConstant.PUBLIC_STATUS, planViewInfoId);
            if(flag) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
            }
            else {
                planViewService.publishView(planViewInfoId, projectIds, userIds, name,ResourceUtil.getCurrentUser(),ResourceUtil.getCurrentUserOrg().getId());
            }

        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.publishFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }


    /**
     * 取消发布视图
     *
     * @return
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doCancelPublishView")
    @ResponseBody
    public AjaxJson doCancelPublishView(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        String message = "";
        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            //校验视图名称是否重复
            boolean flag = planViewService.getViewCountByStatusAndName(name, PlanviewConstant.PRIVATE_STATUS, id);
            if(flag) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.nameRepeated");
                j.setSuccess(false);
                j.setMsg(message);
            }
            else {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.cancelPublishSuccess");
                j.setMsg(message);
                planViewService.cancelPublishView(id, PlanviewConstant.PRIVATE_STATUS);
            }
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.cancelPublishFailure");
            j.setMsg(message);
        }
        finally {
            log.info(message);
            return j;
        }
    }


    /**
     * 删除视图
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "unchecked", "finally" })
    @RequestMapping(params = "doDeleteView")
    @ResponseBody
    public AjaxJson doDeleteView(HttpServletRequest request, HttpServletRequest response) {
        AjaxJson j = new AjaxJson();
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.deleteSuccess");
        j.setSuccess(true);
        try {
            String id = request.getParameter("id");
            planViewService.deletePlanViewInfo(id);
        }
        catch (Exception e) {
            j.setSuccess(false);
            message = I18nUtil.getValue("com.glaway.ids.pm.project.planview.deleteFailure");
        }
        finally {
            log.info(message);
            j.setMsg(message);
            return j;
        }
    }



}
