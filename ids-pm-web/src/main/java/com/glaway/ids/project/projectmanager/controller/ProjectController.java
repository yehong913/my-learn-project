package com.glaway.ids.project.projectmanager.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.activiti.facade.WorkFlowFacade;
import com.glaway.foundation.businessobject.attribute.AdditionalAttributeManager;
import com.glaway.foundation.businessobject.attribute.dto.AdditionalAttributeDto;
import com.glaway.foundation.businessobject.attribute.dto.EntityAttributeAdditionalAttributeDto;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSTypeDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.core.common.model.json.DataGrid;
import com.glaway.foundation.core.common.model.json.DataGridReturn;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.FeignAttributeService;
import com.glaway.foundation.fdk.dev.service.FeignSystemService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignTeamService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.foundation.system.serial.SerialNumberManager;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.config.constant.RepFileTypeConfigConstants;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.config.vo.EpsConfig;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.project.menu.dto.ProjTeamLinkDto;
import com.glaway.ids.project.menu.service.RecentlyProjectRemoteFeignServiceI;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.dto.ProjectDto;
import com.glaway.ids.project.projectmanager.service.ProjLogRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.*;


/**
 * @Title: Controller
 * @Description: 项目维护
 * @author Songjie
 * @version V1.0
 */

@Controller
@RequestMapping("/projectController")
public class ProjectController extends BaseController {

    /**
     * 操作日志
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjectController.class);

    /**
     * 注入WorkFlowFacade
     */
    @Autowired
    private WorkFlowFacade workFlowFacade;


    /**
     * 最近访问项目服务实现接口
     */
    @Autowired
    private RecentlyProjectRemoteFeignServiceI recentlyProjectService;


    /**
     * 项目属性服务实现接口
     */
    @Autowired
    private PlanBusinessConfigServiceI businessConfigService;

    /**
     * 项目服务实现接口
     */
    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    @Autowired
    private FeignTeamService feignTeamService;
    
    @Autowired
    private FeignSystemService feignSystemService;
    
    /**
     * 项目日志服务实现接口
     */
    @Autowired
    private ProjLogRemoteFeignServiceI projLogService;


    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;

    @Autowired
    private FeignUserService userService;

    @Autowired
    private FeignRoleService roleService;


    /**
     * 项目团队服务实现接口
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;


    @Autowired
    private ProjTemplateRemoteFeignServiceI projTemplateService;


    @Autowired
    private PlanRemoteFeignServiceI planService;
    
    @Autowired
    private FeignAttributeService attributeService;




    /**
     * 项目列表 页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "project")
    public ModelAndView project(HttpServletRequest request) {
       /* Project p = new Project();
        String pro = projectService.initProject(p);
        p = JSON.parseObject(JsonFromatUtil.formatJsonToList(pro),new TypeReference<Project>(){});*/
        String fromType = request.getParameter("fromType");
        request.setAttribute("fromType",fromType);
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
            FeignJson epsFj = epsConfigService.getEpsNamePathById(config.getId());
            String epsName = "";
            if (epsFj.isSuccess()) {
                epsName = epsFj.getObj() == null ? "" : epsFj.getObj().toString();
            }
            obj.put("name", epsName); // 性能待优化，需先一次性查询出所有epsPath的Map，再从map中取对应的值
            jsonEpsList.add(obj);
        }

        String lifeCycleListStr = jsonLifeCycleList.toString().replaceAll("\"", "'");
        String epsListStr = jsonEpsList.toString().replaceAll("\"", "'");
        request.setAttribute("lifeCycleList", lifeCycleListStr);
        request.setAttribute("epsList", epsListStr);

        String userId = UserUtil.getInstance().getUser().getId();
        TSUserDto tuser = userService.getUserByUserId(userId);
        //是否是系统的项目管理员
        boolean isPmo = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
            ProjectRoleConstants.PMO);
        //是否是系统的项目经理
        boolean isManager = projRoleService.isSysRoleByUserIdAndRoleCode(userId, ProjectRoleConstants.PROJ_MANAGER);

        request.setAttribute("isPmo", isPmo);
        request.setAttribute("isManager", isManager);
        request.setAttribute("currentUserName", tuser.getRealName() + "-" + tuser.getUserName());
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projectList");
    }


    /**
     * 项目列表 查询接口
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "searchDatagrid")
    public void searchDatagrid(HttpServletRequest request, HttpServletResponse response) {
        List<ConditionVO> conditionList = RequestMapUtil.getQueryCondition(request);
        String searchProjectManager = request.getParameter("searchProjectManager");
        String createName = request.getParameter("createName");
        String bizCurrent = request.getParameter("bizCurrent"); // 生命周期状态
        String eps = request.getParameter("eps"); // 项目分类
        //计划视图查询条件
        String entryPage = request.getParameter("entryPage"); //入口页面
        String isTeamMember = request.getParameter("isTeamMember");

        if (StringUtils.isNotEmpty(bizCurrent)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(bizCurrent.split(","));
            vo.setCondition("in");
            vo.setValue(bizCurrent);
            vo.setKey("Project.bizCurrent");
            conditionList.add(vo);
        }

        /*
         * if (StringUtils.isNotEmpty(phaseInfo)) {
         * ConditionVO vo = new ConditionVO();
         * vo.setValueArr(phaseInfo.split(","));
         * vo.setCondition("in");
         * vo.setValue(phaseInfo);
         * vo.setKey("Project.phaseInfo.name");
         * conditionList.add(vo);
         * }
         */

      /*  if (StringUtils.isNotEmpty(eps)) {
            ConditionVO vo = new ConditionVO();
            vo.setValueArr(eps.split(","));
            vo.setCondition("in");
            vo.setValue(eps);
            vo.setKey("Project.eps");
            conditionList.add(vo);
        }*/
      String currentUserId = UserUtil.getInstance().getUser().getId();

        PageList pageList = projectService.queryEntityBySql(conditionList, searchProjectManager,
                createName, entryPage, isTeamMember,currentUserId,ResourceUtil.getCurrentUserOrg().getId());
        long count = pageList.getCount();
        String json = JsonUtil.getListJsonWithoutQuote(pageList.getResultList());
        String datagridStr = "{\"rows\":" + json + ",\"total\":" + count + "}";
        TagUtil.ajaxResponse(response, datagridStr);
    }



    /**
     * 获取启用状态的项目阶段
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "phaseList")
    @ResponseBody
    public void phaseList(HttpServletRequest request, HttpServletResponse response) {
        String phaseStr = businessConfigService.getProjectPhaseList();
        List<BusinessConfig> phaseList = JSON.parseObject(JsonFromatUtil.formatJsonToList(phaseStr),new TypeReference<List<BusinessConfig>>(){});
        String jonStr = JsonUtil.getCodeTitleJson(phaseList, "id", "name");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取项目生命周期状态列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "statusList")
    @ResponseBody
    public void statusList(HttpServletRequest request, HttpServletResponse response) {
        FeignJson fj = projectService.getLifeCycleStatusList();
        String lifeCycStr = String.valueOf(fj.getObj());
        List<LifeCycleStatus> lifeCycleList = JSON.parseObject(lifeCycStr,new TypeReference<List<LifeCycleStatus>>(){});
        String jonStr = JsonUtil.getCodeTitleJson(lifeCycleList, "name", "title");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取EPS项目分类树
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "epsCombotree")
    public void epsCombotree(HttpServletRequest request, HttpServletResponse response,
                             DataGrid dataGrid) {
        String epsTreeStr = epsConfigService.getEpsTreeNodes();
        List<TreeNode> epsTree = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsTreeStr),new TypeReference<List<TreeNode>>(){});
        String json = com.alibaba.fastjson.JSONArray.toJSONString(epsTree);

        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 项目流程驳回到首节点再次提交工作流：启动、暂停、恢复、关闭
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "resubmitProjectFlow")
    @ResponseBody
    public FeignJson resubmitProjectFlow(Project project, HttpServletRequest request) {
        String oper = request.getParameter("oper");
        String id = request.getParameter("id");
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson j = projectService.resubmitProjectFlow(project,oper,id,userId);
        return j;
    }

    /**
     * 项目新增页面跳转
     *
     * @param project
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(Project project, HttpServletRequest request) {
        TSUserDto tsUser = UserUtil.getInstance().getUser();
        project.setCreateName(tsUser.getRealName() + "-" + tsUser.getUserName());
        project.setCreateTime(new Date());
        request.setAttribute("project", project);
      
       /* TSRoleDto tsRole = roleService.getRoleByRoleCode("manager");
        *//*List<TSRole> roleList = projectService.getRole("manager");*//*
        if (!CommonUtil.isEmpty(tsRole)) {
            String id = tsRole.getId();
            request.setAttribute("codeId", id);
        }*/

        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/projectBasic-add");
    }


    /**
     * 获取启用状态的项目阶段+已选中的禁用的项目阶段
     * 项目修改和项目详情页面使用
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "phaseListForDetail")
    @ResponseBody
    public void phaseListForDetail(HttpServletRequest request, HttpServletResponse response) {
        String phaseStr = businessConfigService.getProjectPhaseList();
        List<BusinessConfig> phaseList = JSON.parseObject(JsonFromatUtil.formatJsonToList(phaseStr),new TypeReference<List<BusinessConfig>>(){});
        // String projectId = request.getParameter("projectId");
        // Project project = projectService.getEntity(Project.class, projectId);
        // BusinessConfig businessConfig = projectService.phaseIsForbidden(project.getPhase());
        // if (businessConfig != null
        // && businessConfig.getStopFlag().equals(ConfigStateConstants.STOP)) {
        // phaseList.add(businessConfig);
        // }
        String jonStr = JsonUtil.getCodeTitleJson(phaseList, "id", "name");
        jonStr = jonStr.replaceAll("'", "\"");
        try {
            TagUtil.ajaxResponse(response, jonStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * EPS树
     *
     * @return
     */
    @RequestMapping(params = "getEPSTree")
    public void getEPSTree(HttpServletRequest req, HttpServletResponse resp) {
        //  List<EpsConfig> configs = epsConfigService.getUsableEpsTree(new EpsConfig());
        String id=req.getParameter("id");
        EpsConfig config =null;
        //判断是否为根节点
        if(StringUtils.isBlank(id)){
            config = new EpsConfig();
        }else{
            String configStr = epsConfigService.getEpsConfig(id);
            config = JSON.parseObject(JsonFromatUtil.formatJsonToList(configStr),new TypeReference<EpsConfig>(){});
        }

        //孩子list
        //转换成符合easyui显示的格式
        String epsStr = epsConfigService.getTreeNodes(config);
        List<TreeNode> node = JSON.parseObject(JsonFromatUtil.formatJsonToList(epsStr),new TypeReference<List<TreeNode>>(){});

        String json = JsonUtil.toJsonString(node);
        TagUtil.ajaxResponse(resp, json);

    }


    /**
     * 校验项目编号是否重复
     *
     * @param project
     * @param req
     * @param rep
     * @return
     * @see
     */
    @RequestMapping(params = "vidateRepeat")
    @ResponseBody
    public AjaxJson vidateRepeat(Project project, HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        boolean isPass = false;
        Project p = null;
        if (null != project) {
            String pro  = projectService.getProjectbyNumber(project.getProjectNumber());
            p = JSON.parseObject(JsonFromatUtil.formatJsonToList(pro),new TypeReference<Project>(){});
            // 新增
            if (StringUtils.isEmpty(project.getId())) {
                if (null == p) {
                    isPass = true;
                }
            }
            else { // 修改
                if (null == p || p.getId().equals(project.getId())) {
                    isPass = true;
                }
            }
        }
        j.setSuccess(isPass);
        if (!isPass) {
            j.setMsg(I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.projectNumberExist"));
        }
        return j;
    }


    /**
     * 添加项目
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doAdd")
    @Transactional
    @ResponseBody
    public AjaxJson doAdd(Project project, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(false);
        String message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.addSuccess");
        try {
            CommonUtil.getInputSearchUserIds(project, request);
            String pro = projectService.initProject(project);
            project = JSON.parseObject(JsonFromatUtil.formatJsonToList(pro),new TypeReference<Project>(){});
            project.setEps(project.getEpsName());
            project.setProcess(0.00);
            project.setCreateBy(UserUtil.getInstance().getUser().getId());
            project.setCreateTime(new Date());
    
            Map<String,TSUserDto> userDtoMap = userService.getCommonPrepUserAllByUUid();
            
            String projectManagerNames = "";
            if(!CommonUtil.isEmpty(project.getProjectManagers())){
                for(String managerId :project.getProjectManagers().split(",") ){
                    TSUserDto userDto = userDtoMap.get(managerId);
                    if(CommonUtil.isEmpty(projectManagerNames)){
                        projectManagerNames = userDto.getRealName()+"-"+userDto.getUserName();
                    }else{
                        projectManagerNames = projectManagerNames + "," +userDto.getRealName()+"-"+userDto.getUserName();
                    }
                }
            }
            
            project.setProjectManagerNames(projectManagerNames);
            String currentUserStr = JSON.toJSONString(UserUtil.getInstance().getUser());
            String orgId=ResourceUtil.getCurrentUserOrg().getId();
            FeignJson fJson = projectService.createProject(project,currentUserStr,orgId);
            ProjectDto projectDto = new ProjectDto();
            BeanUtils.copyProperties(project, projectDto);
            Map<String,String[]> reqMap = request.getParameterMap();
            Map<String, Object> parmMap = new HashMap<>();
            
            parmMap.put("glObjId",project.getProjectNumber());
            parmMap.put("glObjClassName","com.glaway.ids.project.projectmanager.entity.Project");
            parmMap.put("addAttrNameMap",reqMap);
            AdditionalAttributeManager.addOrUpdateAdditionalAttribute(projectDto, parmMap);
            
            request.getSession().setAttribute("projectId",project.getProjectNumber());
            request.getSession().setAttribute("projectNumber", project.getProjectNumber());
            projLogService.saveProjLog(project.getProjectNumber(),String.valueOf(fJson.getObj()), message, "",UserUtil.getInstance().getUser());

            /*log.info(message, project.getId(), project.getId().toString());*/
            j.setSuccess(true);
            j.setObj("true");
        }
        catch (Exception e) {
//            System.out.println(e.getMessage());
//            message = I18nUtil.getValue("com.glaway.ids.project.projectmanager.addFailure");
            message = e.getMessage();
            j.setSuccess(false);
            log.error(message, e, "", project.getId().toString());
            Object[] params = new Object[] {message, project.getId().toString()};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }
    }



    /**
     * 项目详情查看基本信息页面跳转
     *
     * @param project
     * @param req
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "viewDetailBaseInfo")
    public ModelAndView viewDetailBaseInfo(Project project, HttpServletRequest req) {
        try {
            Object operationCodes = OpeartionUtils.getOperationCodes(req);
            boolean saveProject = false;
            boolean pauseResumeProjDetail = false;
            boolean resumeProjDetail = false;
            //boolean deleteProjDetail = false;
            boolean startProjDetail = false;
            boolean closeProjDetail = false;
            if(!CommonUtil.isEmpty(operationCodes)){
                for (String operationCode : operationCodes.toString().split(",")) {
                    if (operationCode.contains("saveProject")) {
                        saveProject = true;
                    }

                    if (operationCode.contains("pauseResumeProjDetail")) {
                        pauseResumeProjDetail = true;
                    }

                    if (operationCode.contains("resumeProjDetail")) {
                        resumeProjDetail = true;
                    }

/*                if (operationCode.contains("deleteProjDetail")) {
                    deleteProjDetail = true;
                }*/

                    if (operationCode.contains("startProjDetail")) {
                        startProjDetail = true;
                    }

                    if (operationCode.contains("closeProjDetail")) {
                        closeProjDetail = true;
                    }

                }
            }


            req.setAttribute("saveProject", saveProject);
            req.setAttribute("pauseResumeProjDetail", pauseResumeProjDetail);
            req.setAttribute("resumeProjDetail", resumeProjDetail);
            //req.setAttribute("deleteProjDetail", deleteProjDetail);
            req.setAttribute("startProjDetail", startProjDetail);
            req.setAttribute("closeProjDetail", closeProjDetail);

            if (StringUtils.isNotEmpty(project.getId())) {
                project = projectService.getProjectEntity(project.getId());
                /*project = JSON.parseObject(JsonFromatUtil.formatJsonToList(projectStr),new TypeReference<Project>(){});*/
                if (project == null) {
                    return new ModelAndView(
                            "com/glaway/ids/pm/project/projectmanager/project-deleteblank");
                }


                Project newProject = new Project();
                BeanUtil.copyBeanNotNull2Bean(project, newProject);

                if (StringUtils.isNotBlank(newProject.getEps())) {
                    FeignJson fj = epsConfigService.getEpsNamePathById(newProject.getEps());
                    String epsName = "";
                    if (fj.isSuccess()) {
                        epsName = fj.getObj() == null ? "" : fj.getObj().toString();
                    }
                    newProject.setEpsName(epsName);
                }
                TSUserDto user = newProject.getCreator();
                String createName = "";
                if (null != user) {
                    createName = user.getRealName() + "-" + user.getUserName();
                } else {
                    String createBy = project.getCreateBy();
                    if (StringUtils.isNotBlank(createBy)) {
                        user = userService.getUserByUserId(createBy);
                        createName = user.getRealName() + "-" + user.getUserName();
                    }
                }
                newProject.setCreateName(createName);
                if (StringUtils.isNotEmpty(newProject.getPhase())) {
                    BusinessConfig bc = new BusinessConfig();
                    bc.setId(newProject.getPhase());
                    bc.setAvaliable("");
//                    bc.setStopFlag(ConfigStateConstants.START);
                    String bcStr = businessConfigService.searchBusinessConfigAccurate(bc);
                    List<BusinessConfig> bcList = JSON.parseObject(JsonFromatUtil.formatJsonToList(bcStr),new TypeReference<List<BusinessConfig>>(){});
                    if (bcList.size() == 0) {
                        newProject.setPhaseInfo(null);
                        newProject.setPhase("");
                    }else{
                        newProject.setPhaseInfo(bcList.get(0));
                        newProject.setPhase(bcList.get(0).getName());
                    }
                }
                // 设置项目状态描述
                FeignJson fj = projectService.getLifeCycleStatusList();
                String lifeCycStr = String.valueOf(fj.getObj());
                List<LifeCycleStatus> lifeCycleList = JSON.parseObject(lifeCycStr,new TypeReference<List<LifeCycleStatus>>(){});
                if (!CommonUtil.isEmpty(lifeCycleList)) {
                    for (LifeCycleStatus lifeCycle : lifeCycleList) {
                        if (newProject.getBizCurrent().equals(lifeCycle.getName())) {
                            newProject.setBizCurrent(lifeCycle.getTitle());
                            break;
                        }
                    }
                }

                if (newProject.getProjectManagers() != null) {
                    newProject.setProjectManagers(newProject.getProjectManagers().replace(";", ","));
                }
                req.setAttribute("project_", newProject);

                TSRoleDto role = roleService.getRoleByRoleCode("manager");
                if (!CommonUtil.isEmpty(role)) {
                    String id = role.getId();
                    req.setAttribute("codeId", id);
                }

                if (StringUtil.isNotEmpty(newProject.getProjectTemplate()) && !newProject.getProjectTemplate().equals("不使用模板")) {
                    ProjTemplateDto projTemplate = projTemplateService.getProjTemplateEntity(
                            newProject.getProjectTemplate());
                    req.setAttribute("projTemplate", projTemplate);
                }
                else {
                    req.setAttribute("projTemplate", "1");
                }
                req.setAttribute("isView", req.getParameter("isView"));
                // 项目下存在计划的情况下，不允许修改工期设置
                PlanDto plan = new PlanDto();
                plan.setProjectId(project.getId());
                long planNumber = planService.getCount(plan);
                if (planNumber > 0) {
                    req.setAttribute("projectTimeTypeIsModify", ProjectConstants.NOTMODIFY);
                }

                // 是否只读（审批页面只读，非项目管理员和非项目经理只读）
                String isViewPage = req.getParameter("isViewPage");
                String isViewProjectPage = req.getParameter("isViewProjectPage");
                if (StringUtil.isEmpty(isViewPage) || !"true".equals(isViewPage)) {
                    boolean isProjectManager = projRoleService.isProjRoleByUserIdAndRoleCode(
                            project.getId(), ProjectRoleConstants.PROJ_MANAGER,
                            UserUtil.getInstance().getUser().getId());
                    if(ProjectConstants.EDITING.equals(project.getBizCurrent())){
                        if(!isProjectManager){
                            isViewPage = "true";
                        }
                    }else{
                        if (!isProjectManager) {
                            isViewPage = "true";
                        }
                    }
                }
                req.setAttribute("isViewPage", isViewPage);
                if("true".equals(isViewProjectPage)){
                    return new ModelAndView("com/glaway/ids/pm/project/projectmanager/project-viewBaseInfo");
                }

                // 是否刷新右侧树区域
                String refreshTree = req.getParameter("refreshTree");
                if (StringUtil.isNotEmpty(refreshTree)) {
                    req.setAttribute("refreshTree", refreshTree);
                }
            }
        }
        catch (Exception e) {
            log.error("项目修改异常", e, project.getId(), "");
        }
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/project-viewdetailbaseinfo");
    }


    /**
     * 项目提交审批页面跳转：
     * 启动、暂停、恢复、关闭
     *
     * @param project
     * @param
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "startProcess")
    public ModelAndView startProcess(Project project, HttpServletRequest req) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(project.getId())) {
            project = projectService.getProjectEntity(project.getId());
            req.setAttribute("project", project);
            String detail = req.getParameter("detail");
            if (detail != null && !"".equals(detail)) { // 来源于项目详情页面
                req.setAttribute("detail", detail);
            }
        }
        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/project-startsubmit");
    }

    /**
     * 项目启动审批流程
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doStartProject")
    @ResponseBody
    public FeignJson doStartProject(Project project, HttpServletRequest request) {
        String approvePerson = request.getParameter("approvePerson");
        String deptApprovePerson = request.getParameter("deptApprovePerson");
        String remark = request.getParameter("remark");

        String userId = ResourceUtil.getCurrentUser().getId();
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("dto",project);
        params.put("approvePerson",approvePerson);
        params.put("deptApprovePerson",deptApprovePerson);
        params.put("remark",remark);
        params.put("userId",userId);
        FeignJson j = projectService.doStartProject(params);
        return j;
    }



    /**
     * 项目暂停/恢复审批流程
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doPauseOrResumeProject")
    @ResponseBody
    public FeignJson doPauseOrResumeProject(Project project, HttpServletRequest request) {
        String operation = request.getParameter("operation");
        String approvePerson = request.getParameter("approvePerson");
        String deptApprovePerson = request.getParameter("deptApprovePerson");
        String remark = request.getParameter("remark");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("project",project);
        params.put("operation",operation);
        params.put("approvePerson",approvePerson);
        params.put("deptApprovePerson",deptApprovePerson);
        params.put("remark",remark);
        params.put("userId",ResourceUtil.getCurrentUser().getId());
        FeignJson j = projectService.doPauseOrResumeProject(params);
        return j;
    }



    /**
     * 项目关闭审批流程
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doCloseProject")
    @ResponseBody
    public FeignJson doCloseProject(Project project, HttpServletRequest request) {
        String approvePerson = request.getParameter("approvePerson");
        String deptApprovePerson = request.getParameter("deptApprovePerson");
        String remark = request.getParameter("remark");

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("dto",project);
        params.put("approvePerson",approvePerson);
        params.put("deptApprovePerson",deptApprovePerson);
        params.put("remark",remark);
        params.put("userId",ResourceUtil.getCurrentUser().getId());

        FeignJson json = projectService.doCloseProject(params);
        return json;
    }

    /**
     * 判断是否是项目团队的项目经理
     *
     * @author zhousuxia
     * @version 2019年1月4日
     * @see ProjectController
     * @since
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "judgeIsTeamManager")
    @ResponseBody
    public FeignJson judgeIsTeamManager(HttpServletRequest request){
        String projectId = request.getParameter("projectId");
        String userId = UserUtil.getInstance().getUser().getId();
        FeignJson j = projectService.judgeIsTeamManager(projectId,userId);
        return j;
    }

    /**
     * 检查项目下的所有计划是否已完工：
     * 全部已完工，返回false；否则返回true
     *
     * @param request
     * @param response
     * @return
     * @see
     */
    @RequestMapping(params = "checkPlan")
    public void checkPlan(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("id");
        String returnStr = "";
        //Project project = projectService.getEntity(Project.class, projectId);
        List<PlanDto> planList = projectService.getOneLevelPlanByProject(projectId);
        if (planList.size() > 0) {
            returnStr = "true";
        }
        else {
            returnStr = "false";
        }
        TagUtil.ajaxResponse(response, returnStr);
    }


    /**
     * 项目编辑页面跳转
     *
     * @param project
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(Project project, HttpServletRequest req) {
        try {
            String insertRecent = req.getParameter("insertRecent");
            req.setAttribute("insertRecent", insertRecent);
            if (StringUtil.isNotEmpty(project.getId())) {
                project = projectService.getProjectEntity(project.getId());
                Map<String,Object> additionalMap = AdditionalAttributeManager.findAttrMapByOid( project.getId());
                req.setAttribute("additionalMap",additionalMap);
                Project newProject = new Project();
                BeanUtil.copyBeanNotNull2Bean(project, newProject);

                if (StringUtils.isNotBlank(newProject.getEps())) {
                    FeignJson fJson = epsConfigService.getEpsNamePathById(newProject.getEps());
                    newProject.setEpsName(String.valueOf(fJson.getObj()));
                }

                TSUserDto user = newProject.getCreator();
                String createName = "";
                if (null != user) {
                    createName = user.getRealName() + "-" + user.getUserName();
                } else {
                    String createBy = project.getCreateBy();
                    if (StringUtils.isNotBlank(createBy)) {
                        user = userService.getUserByUserId(createBy);
                        createName = user.getRealName() + "-" + user.getUserName();
                    }
                }
                newProject.setCreateName(createName);
                if (StringUtils.isNotEmpty(newProject.getPhase())) {
                    BusinessConfig bc = new BusinessConfig();
                    bc.setId(newProject.getPhase());
                    bc.setAvaliable("");
//                    bc.setStopFlag(ConfigStateConstants.START);
                    String bcListStr = businessConfigService.searchBusinessConfigAccurate(bc);
                    List<BusinessConfig> bcList = JSON.parseObject(JsonFromatUtil.formatJsonToList(bcListStr),new TypeReference<List<BusinessConfig>>(){});
                    if (bcList.size() == 0) {
                        newProject.setPhaseInfo(null);
                        newProject.setPhase("");
                    }else{
                        newProject.setPhaseInfo(bcList.get(0));
                        newProject.setPhase(bcList.get(0).getName());
                    }
                }
                if (newProject.getProjectManagers() != null) {
                    newProject.setProjectManagers(newProject.getProjectManagers().replace(";", ","));
                }
                req.setAttribute("project_", newProject);

                // 项目下存在计划的情况下，不允许修改工期设置
                PlanDto plan = new PlanDto();
                plan.setProjectId(project.getId());
                long planNumber = planService.getCount(plan);
                if (planNumber > 0) {
                    req.setAttribute("projectTimeTypeIsModify", ProjectConstants.NOTMODIFY);
                }
            }

            TSRoleDto roleList = projectService.getRole("manager");
            if (!CommonUtil.isEmpty(roleList)) {
                String id = roleList.getId();
                req.setAttribute("codeId", id);
            }


            req.setAttribute("projTemplateList",
                    projTemplateService.searchAllProjTemplate());
        }
        catch (Exception e) {
            log.error("项目修改异常", e, project.getId(), "");
        }

        String oper = req.getParameter("oper");
        if (StringUtil.isNotEmpty(oper)) {
            if (oper.equals("startProject") || oper.equals("pauseProject")
                    || oper.equals("resumeProject") || oper.equals("closeProject")) {
                String isView = req.getParameter("isView");
                req.setAttribute("isView", isView);
                req.setAttribute("oper", oper);
                return new ModelAndView(
                        "com/glaway/ids/pm/project/projectmanager/project-resubmitFlow");
            }
        }

        return new ModelAndView("com/glaway/ids/pm/project/projectmanager/project-updatebaseinfo");
    }


    /**
     * 更新项目
     *
     * @param project
     * @param request
     * @return AjaxJson
     * @see
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public FeignJson doUpdate(Project project, HttpServletRequest request) {
        FeignJson j = new FeignJson();
        String pagetype = request.getParameter("pagetype");
        String isUpdate = request.getParameter("isUpdate");
        CommonUtil.getInputSearchUserIds(project, request);
        j = projectService.doProjectUpdate(project,pagetype,isUpdate,ResourceUtil.getCurrentUser().getId());
        return j;
    }


    /**
     * 批量删除项目
     *
     * @param ids
     * @param request
     * @return AjaxJson
     * @see
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public FeignJson doBatchDel(String ids, HttpServletRequest request) {
        String userId = ResourceUtil.getCurrentUser().getId();
        FeignJson fj = projectService.projectBatchDelete(ids, userId);
        return fj;
    }

    /**
     * Description: <br>
     * 获取启用状态的项目模板列表
     *
     * @param request
     * @param response
     * @param dataGrid
     * @see
     */
    @RequestMapping(params = "datagridTemplate")
    public void datagridTemplate(HttpServletRequest request,
                                 HttpServletResponse response, DataGrid dataGrid) {
        ProjTemplateDto projTemplate = new ProjTemplateDto();
        projTemplate.setBizCurrent(ProjectConstants.PROJTEMPLATE_QIYONG);
        List<ProjTemplateDto> listProjTemplate = projTemplateService.searchProjTemplate(projTemplate);

        String json = "";
        if (!CommonUtil.isEmpty(listProjTemplate)) {
            DataGridReturn demo = new DataGridReturn(listProjTemplate.size(), listProjTemplate);
            json = com.alibaba.fastjson.JSONObject.toJSONString(demo);
        }
        TagUtil.ajaxResponse(response, json);
    }

    /**
     * 门户项目列表 页面跳转
     *
     * @param request
     * @return ModelAndView
     * @see
     */
    @RequestMapping(params = "projectPortlet")
    public ModelAndView projectPortlet(HttpServletRequest request) {
        //查询五条项目数据
        String currentUserId = UserUtil.getInstance().getUser().getId();
        List<Project> projectList = projectService.getProjectPortletList(currentUserId);
        request.setAttribute("projectList",projectList);
        return new ModelAndView("com/glaway/ids/project/projectmanager/projectPortletList");
    }
    

    
    
    
}

