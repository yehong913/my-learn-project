package com.glaway.ids.project.projectmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.BeanUtil;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.StringUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.foundation.system.lifecycle.entity.LifeCycleStatus;
import com.glaway.ids.config.service.EpsConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.util.JsonFromatUtil;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.config.vo.BusinessConfig;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjLogRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: ProjectRestController
 * @Date: 2019/7/26-16:38
 * @since
 */
@Controller
@RequestMapping("/feign/projectControllerForFeign")
public class ProjectControllerForFeign {

    /**
     * 操作日志
     */
    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjectController.class);

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

    /**
     * EPS服务实现接口
     */
    @Autowired
    private EpsConfigRemoteFeignServiceI epsConfigService;

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
                if (null != user) {
                    newProject.setCreateName(user.getRealName() + "-" + user.getUserName());
                }
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
                String substring = newProject.getEpsName().substring(1, newProject.getEpsName().length() - 1);
                newProject.setEpsName(substring);
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
                    boolean isPMO = projRoleService.isSysRoleByUserIdAndRoleCode(
                            UserUtil.getInstance().getUser().getId(), ProjectRoleConstants.PMO);
                    boolean isProjectManager = projRoleService.isProjRoleByUserIdAndRoleCode(
                            project.getId(), ProjectRoleConstants.PROJ_MANAGER,
                            UserUtil.getInstance().getUser().getId());
                    if(ProjectConstants.EDITING.equals(project.getBizCurrent())){
                        if(!isPMO){
                            isViewPage = "true";
                        }
                    }else{
                        if (!isPMO && !isProjectManager) {
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
}
