package com.glaway.ids.project.projectmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.glaway.foundation.common.dto.*;
import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.*;
import com.glaway.foundation.common.util.param.GWConstants;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignGroupService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignTeamService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.ids.config.constant.ConfigStateConstants;
import com.glaway.ids.config.util.OpeartionUtils;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.FeignProjGroupServiceI;
import com.glaway.ids.project.projectmanager.service.FeignProjRolesServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.GroupUserDetailVo;
import com.glaway.ids.project.projectmanager.vo.TeamVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LHR on 2019/7/26.
 */
@Controller
@RequestMapping("/projRolesController")
public class ProjRolesController {

    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleRemoteFeignServiceI;

    @Autowired
    private FeignTeamService feignTeamService;

    @Autowired
    private FeignRoleService feignRoleService;

    @Autowired
    private FeignUserService feignUserService;

    @Autowired
    private FeignProjRolesServiceI feignProjRolesServiceI;

    @Autowired
    private FeignProjGroupServiceI feignProjGroupServiceI;

    @Autowired
    private ProjectRemoteFeignServiceI projectRemoteFeignServiceI;

    /**
     * Logger for this class
     */
    // private static final Logger logger = Logger.getLogger(ProjRolesController.class);
    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjRolesController.class);

    /**
     * message
     */
    private String message;

    /**
     * Description: <br>获得项目下的角色，组，用户
     * 1、…<br>
     * 2、…<br>
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     *
     * @param req
     * @return
     * @see
     */
    @RequestMapping(params = "getProjRolesAndUsers")
    public ModelAndView getProjRolesAndUsers(HttpServletRequest req) {
        boolean isViewPage = false;
        String id = req.getParameter("id");
        String isViewPageStr = req.getParameter("isViewPage");
        isViewPage = BooleanUtils.toBoolean(isViewPageStr);
        Object operationCodes = OpeartionUtils.getOperationCodes(req);

        boolean addProjRole = false;
        boolean addProjMember = false;
        boolean addProjGroup = false;
        boolean removeProjRoleOrMember = false;

        for (String operationCode : operationCodes.toString().split(",")) {
            if (operationCode.contains("addProjRole")) {
                addProjRole = true;
            }
            if (operationCode.contains("addProjMember")) {
                addProjMember = true;
            }
            if (operationCode.contains("addProjGroup")) {
                addProjGroup = true;
            }
            if (operationCode.contains("removeProjRoleOrMember")) {
                removeProjRoleOrMember = true;
            }
        }
        req.setAttribute("addProjRole", addProjRole);
        req.setAttribute("addProjMember", addProjMember);
        req.setAttribute("addProjGroup", addProjGroup);
        req.setAttribute("removeProjRoleOrMember", removeProjRoleOrMember);

        if (StringUtils.isNotBlank(id)) {
            if (isViewPage) {
                req.setAttribute("isViewPage", 1);
            } else {
                Project project = projectService.getProjectEntity(id);
                String isReject = project.getIsRefuse();
                isViewPage = isHidbar(id, isReject);
                if (isViewPage) {
                    req.setAttribute("isViewPage", 1);
                } else {
                    req.setAttribute("isViewPage", 0);
                }
            }
        }

        req.setAttribute("projectId", id);
        FeignJson feignJson = projRoleRemoteFeignServiceI.getTeamIdByProjectId(id);
        String teamIdByProjectId = (String) feignJson.getObj();
        req.setAttribute("teamId", teamIdByProjectId);
        // 当kdd调用IDS团队菜单时候  增加权限控制  当当前用户不是产品经理 即IDS中的项目经理时候 页面取消团队操作条件
        if (StringUtil.isNotEmpty(id)) {
            //获取当前用户
            TSUserDto user = UserUtil.getInstance().getUser();
            //获取项目实例信息
            Project project = projectService.getProjectEntity(id);
            //判断当前用户是否是总体设计师
            if (StringUtil.isNotEmpty(project.getProjectManagers()) && project.getProjectManagers().contains(user.getId())) {
                req.setAttribute("kddTeamOptPower", "kddTeamOptPower");
            }

        }
        // 是否刷新右侧树区域
        String refreshTree = req.getParameter("refreshTree");
        if (StringUtil.isNotEmpty(refreshTree)) {
            req.setAttribute("refreshTree", refreshTree);
        }
        return new ModelAndView("com/glaway/ids/project/projectmanager/projRolesList");
    }

    /**
     * Description: <br> 是否隐藏角色工具栏
     *
     * @param projectId
     * @param isReject
     * @return
     * @see
     */
    public boolean isHidbar(String projectId, String isReject) {
        Project p = projectService.getProjectEntity(projectId);
        if (null == p) {
            return true;
        }
        // 项目暂停关闭角色不可以修改
        if (p.getBizCurrent().equalsIgnoreCase(ProjectConstants.PAUSED)
                || p.getBizCurrent().equalsIgnoreCase(ProjectConstants.CLOSED)) {
            return true;
        }

        if (StringUtils.equals(ProjectConstants.APPROVING, p.getStatus())
                && !StringUtils.equals(ProjectConstants.REFUSED, isReject)
                && p.getBizCurrent().equalsIgnoreCase(ProjectConstants.EDITING)) {
            return true;

        }
        return false;
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
    @RequestMapping(params = "refreshTeamList")
    @ResponseBody
    public AjaxJson refreshTeamList(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        FeignJson feignJson = projRoleRemoteFeignServiceI.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        String appKey = ResourceUtil.getApplicationInformation().getAppKey();
        List<TSRoleDto> list = feignRoleService.getSysRoleListByTeamId(appKey, teamId);// 获得团队先角色
        List<TeamVo> teamRoles = new ArrayList<TeamVo>();
        for (TSRoleDto role : list) {
            TeamVo teamRole = new TeamVo();
            teamRole.setId(role.getId());
            teamRole.setRoleId(role.getId());
            teamRole.setRole(role);
            teamRole.setType("ROLE");
            teamRole.setUser(null);
            teamRole.setUserId("");
            teamRole.set_parentId(null);
            teamRole.setIconCls("basis ui-icon-person");
            teamRoles.add(teamRole);
        }
        List<TeamVo> teamUsers = feignProjRolesServiceI.getSysUserListByTeamId(teamId);
        List<TeamVo> teamGroups = feignProjRolesServiceI.getSysGroupListByTeamId(teamId);
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        for (TeamVo teamRole : teamRoles) {
            JSONObject root = this.getJSONObject(teamRole);
            List<JSONObject> rows = new ArrayList<JSONObject>();
            for (TeamVo teamUser : teamUsers) {
                if (teamRole.getId().equals(teamUser.getRoleId())) {
                    JSONObject teamUserNode = this.getJSONObject(teamUser);
                    rows.add(teamUserNode);
                }

            }
            for (TeamVo teamGroup : teamGroups) {
                if (teamRole.getId().equals(teamGroup.getRoleId())) {
                    JSONObject teamGroupNode = this.getJSONObject(teamGroup);
                    rows.add(teamGroupNode);
                }
            }
            root.put("rows", rows);
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }

    /**
     * 将TeamVo对象根据其type转换为JSONObject
     *
     * @param teamVo
     * @return
     */
    private JSONObject getJSONObject(TeamVo teamVo) {
        JSONObject jSONObject = new JSONObject();
        if (teamVo != null) {
            if ("USER".equals(teamVo.getType())) {
                jSONObject.put("id", StringUtils.isNotEmpty(teamVo.getId()) ? teamVo.getId() : "");
                jSONObject.put("roleId",
                        StringUtils.isNotEmpty(teamVo.getRoleId()) ? teamVo.getRoleId() : "");
                jSONObject.put("roleCode",
                        teamVo.getRole() != null ? teamVo.getRole().getRoleCode() : "");

                JSONObject node = new JSONObject();
                node.put("value", "");
                node.put("image", "space.png");
                jSONObject.put("roleName", node);

                jSONObject.put("name", teamVo.getUser().getRealName() + "-"
                        + teamVo.getUser().getUserName());
                jSONObject.put("memberDisplayName", this.getMemberDisplayName(teamVo));
                jSONObject.put("dept",
                        StringUtils.isNotEmpty(teamVo.getDept()) ? teamVo.getDept() : "");
                jSONObject.put("email",
                        StringUtils.isNotEmpty(teamVo.getEmail()) ? teamVo.getEmail() : "");
                jSONObject.put("role", teamVo.getRole() != null ? teamVo.getRole() : "");
                jSONObject.put("type",
                        StringUtils.isNotEmpty(teamVo.getType()) ? teamVo.getType() : "");
                jSONObject.put("userId",
                        StringUtils.isNotEmpty(teamVo.getUserId()) ? teamVo.getUserId() : "");
                jSONObject.put("user", "");
                jSONObject.put("_parentId",
                        StringUtils.isNotEmpty(teamVo.get_parentId()) ? teamVo.get_parentId() : "");
                jSONObject.put("iconCls",
                        StringUtils.isNotEmpty(teamVo.getIconCls()) ? teamVo.getIconCls() : "");

                if (StringUtils.isNotEmpty(teamVo.getUser().getStatus() + "")) {
                    jSONObject.put("status", teamVo.getUser().getStatus());
                    if (ConfigStateConstants.START_KEY.equals(teamVo.getUser().getStatus() + "")) {
                        jSONObject.put("showStatus", "有效");
                    } else {
                        jSONObject.put("showStatus", "失效");
                    }
                }

                jSONObject.put("rows", new ArrayList<JSONObject>());
            }
            if ("GROUP".equals(teamVo.getType())) {
                jSONObject.put("id", StringUtils.isNotEmpty(teamVo.getId()) ? teamVo.getId() : "");
                jSONObject.put("roleId",
                        StringUtils.isNotEmpty(teamVo.getRoleId()) ? teamVo.getRoleId() : "");
                jSONObject.put("roleCode",
                        teamVo.getRole() != null ? teamVo.getRole().getRoleCode() : "");

                JSONObject node = new JSONObject();
                node.put("value", "");
                node.put("image", "space.png");
                jSONObject.put("roleName", node);

                jSONObject.put(
                        "name",
                        StringUtils.isNotEmpty(teamVo.getGroup().getGroupName()) ? teamVo.getGroup().getGroupName() : "");
                jSONObject.put("memberDisplayName", this.getMemberDisplayName(teamVo));
                jSONObject.put("dept",
                        StringUtils.isNotEmpty(teamVo.getDept()) ? teamVo.getDept() : "");
                jSONObject.put("email",
                        StringUtils.isNotEmpty(teamVo.getEmail()) ? teamVo.getEmail() : "");
                jSONObject.put("role", teamVo.getRole() != null ? teamVo.getRole() : "");
                jSONObject.put("type",
                        StringUtils.isNotEmpty(teamVo.getType()) ? teamVo.getType() : "");
                jSONObject.put("userId",
                        StringUtils.isNotEmpty(teamVo.getUserId()) ? teamVo.getUserId() : "");
                jSONObject.put("user", "");
                jSONObject.put("_parentId",
                        StringUtils.isNotEmpty(teamVo.get_parentId()) ? teamVo.get_parentId() : "");
                jSONObject.put("iconCls",
                        StringUtils.isNotEmpty(teamVo.getIconCls()) ? teamVo.getIconCls() : "");
                jSONObject.put("rows", new ArrayList<JSONObject>());
            }
            if ("ROLE".equals(teamVo.getType())) {
                jSONObject.put("id", teamVo.getId());
                jSONObject.put("roleId", teamVo.getRoleId());

                JSONObject treeNode = new JSONObject();
                treeNode.put("value", teamVo.getRole().getRoleName());
                treeNode.put("image", "person.png");
                jSONObject.put("roleName", treeNode);

                jSONObject.put("roleCode",
                        teamVo.getRole() != null ? teamVo.getRole().getRoleCode() : "");

                jSONObject.put("name", "");
                jSONObject.put("memberDisplayName", "");
                jSONObject.put("dept",
                        StringUtils.isNotEmpty(teamVo.getDept()) ? teamVo.getDept() : "");
                jSONObject.put("email",
                        StringUtils.isNotEmpty(teamVo.getEmail()) ? teamVo.getEmail() : "");
                jSONObject.put("role", teamVo.getRole());
                jSONObject.put("type", teamVo.getType());
                jSONObject.put("userId", teamVo.getUserId());
                jSONObject.put("user", teamVo.getUser() != null ? teamVo.getUser() : "");

                jSONObject.put("_parentId",
                        StringUtils.isNotEmpty(teamVo.get_parentId()) ? teamVo.get_parentId() : "");
            }
        }
        return jSONObject;
    }

    /**
     * 获取成员显示名，包括图标及内容
     *
     * @param teamVo
     * @return
     */
    private String getMemberDisplayName(TeamVo teamVo) {
        String displayName = "";
        if (teamVo != null) {
            if ("USER".equals(teamVo.getType())) {
                displayName = displayName + teamVo.getUser().getRealName() + "-" + teamVo.getUser().getUserName();
            }
            if ("GROUP".equals(teamVo.getType())) {
                displayName = displayName
                        + "<a href='#' onclick=\"showGroup(\'" + teamVo.getGroupId()
                        + "\')\" style='color:blue'>" + teamVo.getGroup().getGroupName() + "</a>";
            }
            if ("ROLE".equals(teamVo.getType())) {
                displayName = "";
            }
        }
        return displayName;
    }

    /**
     * 添加项目角色
     *
     * @param
     * @return
     */
    @SuppressWarnings("finally")
    @PostMapping(params = "doBatchAdd")
    @ResponseBody
    public AjaxJson doBatchAdd(HttpServletRequest request) {
        String teamId = request.getParameter("teamId");
        String codes = request.getParameter("roles");
        List<String> roleIds = JSONArray.parseArray(codes, String.class);
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addSuccess");
        try {
            log.info(message, codes, "");
            for (String code : roleIds) {
                feignProjRolesServiceI.addTeamRoleByCode(teamId, code);
            }
            //新增角色  如果没有选中的角色 除了项目经理之外  删除没有选择的角色
            String appKey = ResourceUtil.getApplicationInformation().getAppKey();
            List<TSRoleDto> list = feignRoleService.getSysRoleListByTeamId(appKey, teamId);

            if (list != null && list.size() > 0) {
                for (TSRoleDto tsRole : list) {
                    //判断新增  团队的角色除了项目经理之外  有无没不需要添加的角色  有删除团队中的角色
                    if (!codes.contains(tsRole.getRoleCode()) && !"manager".equals(tsRole.getRoleCode())) {
                        feignTeamService.deleteFdTeamRoleUserByTeamIdAndRoleId(teamId, tsRole.getId());
                    }
                }
            }

        } catch (Exception e) {
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
            log.error(message, e, "", codes);
            Object[] params = new Object[]{message, codes};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2002, params, e);
        } finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 获取已被选中的用户
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getUserList")
    @ResponseBody
    public AjaxJson getUserList(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        String projectId = req.getParameter("projectId");
        String roleCode = req.getParameter("roleCode");
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateSuccess");

        try {
            FeignJson feignJson = projRoleRemoteFeignServiceI.getTeamIdByProjectId(projectId);
            String teamId = (String) feignJson.getObj();
            List<TSUserDto> users = feignTeamService.getSysUserListByTeamIdAndRoleCode(teamId, roleCode);// 获得角色下用户
            String obj = "";
            for (TSUserDto user : users) {
                obj = obj + user.getId() + ",";
            }
            if (null != obj && !"".equals(obj)) {
                obj = obj.substring(0, obj.length() - 1);
            }

            j.setObj(obj);
            j.setSuccess(true);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateFailure");
            log.error(message, e);
            Object[] params = new Object[]{message, projectId};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        } finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 获取已被选中的用户
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getGroupList")
    @ResponseBody
    public AjaxJson getGroupList(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        String projectId = req.getParameter("projectId");
        String roleCode = req.getParameter("roleCode");
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateTeamSuccess");
        try {
            FeignJson feignJson = projRoleRemoteFeignServiceI.getTeamIdByProjectId(projectId);
            String teamId = (String) feignJson.getObj();
            List<TSGroupDto> groups = feignProjGroupServiceI.getSysGroupListByTeamIdAndRoleCode(teamId, roleCode);
            String obj = "";
            for (TSGroupDto group : groups) {
                obj = obj + group.getId() + ",";
            }
            if (null != obj && !"".equals(obj)) {
                obj = obj.substring(0, obj.length() - 1);
            }

            j.setObj(obj);
            j.setSuccess(true);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateTeamFailure");
            log.error(message, e);
            Object[] params = new Object[]{message, projectId};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        } finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 获取已被选中的角色
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "getUsedRolesList")
    @ResponseBody
    public AjaxJson getUsedRolesList(HttpServletRequest req, HttpServletResponse rep) {
        AjaxJson j = new AjaxJson();
        String projectId = req.getParameter("projectId");
        String teamId = req.getParameter("teamId");
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateSuccess");
        try {
            String appKey = ResourceUtil.getApplicationInformation().getAppKey();
            List<TSRoleDto> roles = feignRoleService.getRoleListByTeamId(appKey, teamId);

            String obj = "";
            for (TSRoleDto role : roles) {
                obj = obj + role.getId() + ",";
            }
            if (null != obj && !"".equals(obj)) {
                obj = obj.substring(0, obj.length() - 1);
            }

            j.setObj(obj);
            j.setSuccess(true);
        } catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.operateFailure");
            log.error(message, e);
            Object[] params = new Object[]{message, projectId};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        } finally {
            j.setMsg(message);
            return j;
        }
    }

    /**
     * 团队详情查看
     *
     * @return
     */
    @RequestMapping(params = "goGroupUserList")
    public ModelAndView goGroupUserList(HttpServletRequest request) {
        String groupId = request.getParameter("groupId");
        request.setAttribute("groupId", groupId);
        return new ModelAndView("com/glaway/ids/project/projectmanager/projRoles-group");
    }
    /**
     * 获得项目下的团队
     *
     * @param
     * @return
     * @see
     */
    @RequestMapping(params = "getTeamList")
    public void getTeamList(HttpServletRequest request, HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        FeignJson feignJson = feignProjRolesServiceI.getTeamIdByProjectId(projectId);
        String teamId = String.valueOf(feignJson.getObj());
        String appKey = ResourceUtil.getApplicationInformation().getAppKey();
        List<TSRoleDto> list = feignRoleService.getSysRoleListByTeamId(appKey, teamId);// 获得团队先角色
        List<TeamVo> teamRoles = new ArrayList<TeamVo>();
        for (TSRoleDto role : list) {
            TeamVo teamRole = new TeamVo();
            teamRole.setId(role.getId());
            teamRole.setRoleId(role.getId());
            teamRole.setRole(role);
            teamRole.setType("ROLE");
            teamRole.setUser(null);
            teamRole.setUserId("");
            teamRole.set_parentId(null);
            teamRole.setIconCls("basis ui-icon-person");
            teamRoles.add(teamRole);
        }
        List<TeamVo> teamUsers = feignProjRolesServiceI.getSysUserListByTeamId(teamId);
        List<TeamVo> teamGroups = feignProjRolesServiceI.getSysGroupListByTeamId(teamId);
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        for (TeamVo teamRole : teamRoles) {
            JSONObject root = new JSONObject();
            root.put("id", teamRole.getId());
            root.put("roleId", teamRole.getRoleId());

            JSONObject treeNode = new JSONObject();
            treeNode.put("value", teamRole.getRole().getRoleName());
            treeNode.put("image", "person.png");
            root.put("roleName", treeNode);
            root.put("roleCode",
                    teamRole.getRole() != null ? teamRole.getRole().getRoleCode() : "");

            root.put("name", "");
            root.put("memberDisplayName", "");
            root.put("dept", StringUtils.isNotEmpty(teamRole.getDept()) ? teamRole.getDept() : "");
            root.put("email",
                    StringUtils.isNotEmpty(teamRole.getEmail()) ? teamRole.getEmail() : "");
            root.put("role", teamRole.getRole());
            root.put("type", teamRole.getType());
            root.put("userId", teamRole.getUserId());
            root.put("user", teamRole.getUser() != null ? teamRole.getUser() : "");
            root.put("_parentId",
                    StringUtils.isNotEmpty(teamRole.get_parentId()) ? teamRole.get_parentId() : "");
            root.put("iconCls", teamRole.getIconCls());
            List<JSONObject> rows = new ArrayList<JSONObject>();
            for (TeamVo teamUser : teamUsers) {
                if (teamRole.getId().equals(teamUser.getRoleId())) {
                    JSONObject teamUserNode = new JSONObject();
                    teamUserNode.put("id",
                            StringUtils.isNotEmpty(teamUser.getId()) ? teamUser.getId() : "");
                    teamUserNode.put("roleId",
                            StringUtils.isNotEmpty(teamUser.getRoleId()) ? teamUser.getRoleId() : "");
                    teamUserNode.put("roleCode",
                            teamUser.getRole() != null ? teamUser.getRole().getRoleCode() : "");

                    JSONObject node = new JSONObject();
                    node.put("value", "");
                    node.put("image", "space.png");
                    teamUserNode.put("roleName", node);
                    teamUserNode.put("name", teamUser.getUser().getRealName() + "-"
                            + teamUser.getUser().getUserName());
                    teamUserNode.put("memberDisplayName", this.getMemberDisplayName(teamUser));
                    teamUserNode.put("dept",
                            StringUtils.isNotEmpty(teamUser.getDept()) ? teamUser.getDept() : "");
                    teamUserNode.put("email",
                            StringUtils.isNotEmpty(teamUser.getEmail()) ? teamUser.getEmail() : "");
                    if (StringUtils.isNotEmpty(teamUser.getUser().getStatus() + "")) {
                        teamUserNode.put("status", teamUser.getUser().getStatus());
                        if (ConfigStateConstants.START_KEY.equals(teamUser.getUser().getStatus()
                                + "")) {
                            teamUserNode.put("showStatus", "有效");
                        } else {
                            teamUserNode.put("showStatus", "失效");
                        }
                    }
                    teamUserNode.put("role", teamUser.getRole() != null ? teamUser.getRole() : "");
                    teamUserNode.put("type",
                            StringUtils.isNotEmpty(teamUser.getType()) ? teamUser.getType() : "");
                    teamUserNode.put("userId",
                            StringUtils.isNotEmpty(teamUser.getUserId()) ? teamUser.getUserId() : "");
                    teamUserNode.put("user", "");
                    teamUserNode.put(
                            "_parentId",
                            StringUtils.isNotEmpty(teamUser.get_parentId()) ? teamUser.get_parentId() : "");
                    teamUserNode.put("iconCls",
                            StringUtils.isNotEmpty(teamUser.getIconCls()) ? teamUser.getIconCls() : "");
                    teamUserNode.put("rows", new ArrayList<JSONObject>());
                    rows.add(teamUserNode);
                }

            }
            for (TeamVo teamGroup : teamGroups) {
                if (teamRole.getId().equals(teamGroup.getRoleId())) {
                    JSONObject teamGroupNode = new JSONObject();
                    teamGroupNode.put("id",
                            StringUtils.isNotEmpty(teamGroup.getId()) ? teamGroup.getId() : "");
                    teamGroupNode.put("roleId",
                            StringUtils.isNotEmpty(teamGroup.getRoleId()) ? teamGroup.getRoleId() : "");
                    teamGroupNode.put("roleCode",
                            teamGroup.getRole() != null ? teamGroup.getRole().getRoleCode() : "");

                    JSONObject node = new JSONObject();
                    node.put("value", "");
                    node.put("image", "space.png");
                    teamGroupNode.put("roleName", node);

                    teamGroupNode.put(
                            "name",
                            StringUtils.isNotEmpty(teamGroup.getGroup().getGroupName()) ? teamGroup.getGroup().getGroupName() : "");
                    teamGroupNode.put("memberDisplayName", this.getMemberDisplayName(teamGroup));
                    teamGroupNode.put("dept",
                            StringUtils.isNotEmpty(teamGroup.getDept()) ? teamGroup.getDept() : "");
                    teamGroupNode.put("email",
                            StringUtils.isNotEmpty(teamGroup.getEmail()) ? teamGroup.getEmail() : "");
                    teamGroupNode.put("role",
                            teamGroup.getRole() != null ? teamGroup.getRole() : "");
                    teamGroupNode.put("type",
                            StringUtils.isNotEmpty(teamGroup.getType()) ? teamGroup.getType() : "");
                    teamGroupNode.put("userId",
                            StringUtils.isNotEmpty(teamGroup.getUserId()) ? teamGroup.getUserId() : "");
                    teamGroupNode.put("user", "");
                    teamGroupNode.put(
                            "_parentId",
                            StringUtils.isNotEmpty(teamGroup.get_parentId()) ? teamGroup.get_parentId() : "");
                    teamGroupNode.put(
                            "iconCls",
                            StringUtils.isNotEmpty(teamGroup.getIconCls()) ? teamGroup.getIconCls() : "");
                    teamGroupNode.put("rows", new ArrayList<JSONObject>());
                    rows.add(teamGroupNode);
                }
            }
            root.put("rows", rows);
            rootList.add(root);
        }

        String resultJSON = JSON.toJSONString(rootList);
        try {
            PrintWriter out = response.getWriter();
            out.print(resultJSON);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量删除项目角色和用户
     *
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchDelRoleAndUsers")
    @ResponseBody
    public AjaxJson doBatchDelRoleAndUsers(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.batchDeleteSuccess");

        String projectId = request.getParameter("projectId");
        FeignJson feignJson = feignProjRolesServiceI.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        String userStr = request.getParameter("users");
        String roleStr = request.getParameter("roles");
        String groupStr = request.getParameter("groups");

        String[] userArr = userStr.split(",");
        String[] roleArr = roleStr.split(",");
        String[] groupArr = groupStr.split(",");

        Project p = projectService.getProjectEntity(projectId);


        String appKey = ResourceUtil.getApplicationInformation().getAppKey();
        FdTeamRoleUserDto roleUserDto = new FdTeamRoleUserDto();
        roleUserDto.setTeamId(teamId);
        TSRoleDto roleDto = feignRoleService.getRoleByRoleCode(ProjectRoleConstants.PROJ_MANAGER);
        roleUserDto.setRoleId(roleDto.getId());
//        List<FdTeamRoleUserDto> managerList = feignTeamService.getFdTeamRoleUser(appKey,roleUserDto);

//        List<FdTeamRoleGroupDto> managerList2 = feignTeamService.getFdTeamRoleGroupListByTeamIdAndRoleId(appKey,teamId,"");

        List<FdTeamRoleUserDto> managerList = feignTeamService.getRoleUsersByRoleCodeAndTeamId(appKey,
                ProjectRoleConstants.PROJ_MANAGER, teamId);

        List<FdTeamRoleGroupDto> managerList2 = feignTeamService.getRoleGroupsByRoleCodeAndTeamId(appKey,teamId,
                ProjectRoleConstants.PROJ_MANAGER);

        List<TeamVo> delManagerList = new ArrayList<TeamVo>();

        List<TeamVo> users = new ArrayList<TeamVo>();
        List<TeamVo> roles = new ArrayList<TeamVo>();
        List<TeamVo> groups = new ArrayList<TeamVo>();

        List<TeamVo> teamUsers = feignProjRolesServiceI.getSysUserListByTeamId(teamId);
        List<TeamVo> teamGroups = feignProjRolesServiceI.getSysGroupListByTeamId(teamId);
        List<TSRoleDto> list = feignRoleService.getSysRoleListByTeamId(appKey, teamId);// 获得团队先角色
        List<TeamVo> teamRoles = new ArrayList<TeamVo>();
        for (TSRoleDto role : list) {
            TeamVo teamrole = new TeamVo();
            teamrole.setId(role.getId());
            teamrole.setRoleId(role.getId());
            teamrole.setRole(role);
            teamrole.setType("ROLE");
            teamrole.setUser(null);
            teamrole.setUserId("");
            teamrole.set_parentId(null);
            teamrole.setIconCls("basis ui-icon-person");
            teamRoles.add(teamrole);
        }
        for (int i = 0; i < userArr.length; i++ ) {
            for (TeamVo user : teamUsers) {
                if (userArr[i].equals(user.getId())) {
                    users.add(user);
                }
            }
        }

        for (int i = 0; i < roleArr.length; i++ ) {
            for (TeamVo role : teamRoles) {
                if (roleArr[i].equals(role.getId())) {
                    roles.add(role);
                }
            }
        }

        for (int i = 0; i < roleArr.length; i++ ) {
            for (TeamVo group : teamGroups) {
                if (roleArr[i].equals(group.getRoleId())) {
                    groups.add(group);
                }
            }
        }

        try {

            for (TeamVo userVo : users) {
                if (userVo.getRole().getRoleCode().equals(ProjectRoleConstants.PROJ_MANAGER)) {
                    delManagerList.add(userVo);
                }
            }

            for (TeamVo userVo : groups) {
                if (userVo.getRole().getRoleCode().equals(ProjectRoleConstants.PROJ_MANAGER)) {
                    delManagerList.add(userVo);
                }
            }
            String kddProductTeamType=request.getParameter("kddProductTeamType");
            if (delManagerList.size() >= managerList.size() + managerList2.size()) {
                //判断条件变跟根据页面传入的参数进行判断
                if(StringUtil.isNotEmpty(kddProductTeamType)&&"kddProduct".equals(kddProductTeamType)){
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.cannotBatchDeleteKdd");
                }else{
                    message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.cannotBatchDelete");
                    j.setSuccess(false);
                }
                return j;
            }
            String userId = UserUtil.getCurrentUser().getUserId();
            FeignJson json = new FeignJson();
            HashMap<String, Object> map = new HashMap<>();
            map.put("users",users);
            map.put("roles",roles);
            map.put("groups",groups);
            map.put("teamId",teamId);
            map.put("p",p);
            map.put("delManagerList",delManagerList);
            map.put("kddProductTeamType",kddProductTeamType);
            map.put("projectId",projectId);
            map.put("userId",userId);
            json.setAttributes(map);
            feignProjRolesServiceI.BatchDelRoleAndUsers(json);
        }
        catch (Exception e) {
            e.printStackTrace();
            message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.batchDeleteFaliure");
            log.error(message, e);
            Object[] params = new Object[] {message, projectId};// 异常原因：{0}；详细信息：{1}
            throw new GWException(GWConstants.ERROR_2003, params, e);
        }
        finally {
            j.setMsg(message);
            return j;
        }

    }


    /**
     * 角色团队查询接口
     *
     * @param
     * @param
     * @return
     * @throws UnsupportedEncodingException
     * @see
     */
    @RequestMapping(params = "searchTreegrid")
    @ResponseBody
    public AjaxJson searchTreegrid(HttpServletRequest req, HttpServletResponse rep)
            throws UnsupportedEncodingException {
        String appKey = ResourceUtil.getApplicationInformation().getAppKey();
        AjaxJson j = new AjaxJson();
        String projectId = req.getParameter("projectId");
        String searchRole = req.getParameter("searchRole");
        String memberType = req.getParameter("memberType");
        String searchMember = req.getParameter("searchMember");
        String searchDept = req.getParameter("searchDept");
        String searchGroup = req.getParameter("searchGroup");
        String teamId = req.getParameter("teamId");
        if (StringUtils.isEmpty(teamId)) {
            FeignJson feignJson = feignProjRolesServiceI.getTeamIdByProjectId(projectId);
            String str = (String) feignJson.getObj();
            teamId = str;
        }


        List<TSRoleDto> list = feignRoleService.getSysRoleListByTeamId(appKey, teamId);// 获得团队先角色
        List<TeamVo> teamRoles = new ArrayList<TeamVo>();
        for (TSRoleDto role : list) {
            TeamVo teamrole = new TeamVo();
            teamrole.setId(role.getId());
            teamrole.setRoleId(role.getId());
            teamrole.setRole(role);
            teamrole.setType("ROLE");
            teamrole.setUser(null);
            teamrole.setUserId("");
            teamrole.set_parentId(null);
            teamrole.setIconCls("basis ui-icon-person");
            teamRoles.add(teamrole);
        }
        List<TeamVo> teamUsers = feignProjRolesServiceI.getSysUserListByTeamId(teamId);
        List<TeamVo> teamGroups = feignProjRolesServiceI.getSysGroupListByTeamId(teamId);
        List<JSONObject> rootList = new ArrayList<JSONObject>();
        for (TeamVo teamRole : teamRoles) {
            if (searchRole != null && !searchRole.equals("")) {
                if (teamRole.getRole().getRoleName().indexOf(searchRole) == -1) {
                    continue;
                }
            }
            List<JSONObject> rows = new ArrayList<JSONObject>();
            if (!"3".equals(memberType)) {
                for (TeamVo teamUser : teamUsers) {
                    if (searchMember != null && !searchMember.equals("") && memberType.equals("2")) {
                        if (teamUser.getUser().getRealName().indexOf(searchMember) == -1
                                && teamUser.getUser().getUserName().indexOf(searchMember) == -1) {
                            continue;
                        }
                    }
                    if (searchDept != null && !searchDept.equals("") && memberType.equals("2")) {
                        if (teamUser.getDept().indexOf(searchDept) == -1) {
                            continue;
                        }
                    }
                    if (teamRole.getId().equals(teamUser.getRoleId())) {
                        JSONObject teamUserNode = this.getJSONObject(teamUser);
                        rows.add(teamUserNode);
                    }
                }
            }
            if (!"2".equals(memberType)) {
                for (TeamVo teamGroup : teamGroups) {

                    if (searchGroup != null && !searchGroup.equals("") && memberType.equals("3")) {
                        if (teamGroup.getGroup().getGroupName().indexOf(searchGroup) == -1) {
                            continue;
                        }
                    }
                    if (teamRole.getId().equals(teamGroup.getRoleId())) {
                        JSONObject teamGroupNode = this.getJSONObject(teamGroup);
                        rows.add(teamGroupNode);
                    }

                }
            }
            if ("1".equals(memberType)) {
                JSONObject root = this.getJSONObject(teamRole);
                root.put("rows", rows);
                rootList.add(root);
            }
            else {
                if (!CommonUtil.isEmpty(rows)) {
                    JSONObject root = this.getJSONObject(teamRole);
                    root.put("rows", rows);
                    rootList.add(root);
                }
            }
        }
        String resultJSON = JSON.toJSONString(rootList);
        j.setSuccess(true);
        j.setObj(resultJSON);
        return j;
    }

    /**
     * 获取列表
     *
     * @param request
     * @param response
     * @see
     */
    @RequestMapping(params = "groupDatagrid")
    @ResponseBody
    public void datagridlist(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");
        List<GroupUserDetailVo> voList = feignProjRolesServiceI.getGroupUserDetailVoList(groupId);
        String datagridStr = "{\"rows\":" + JSON.toJSONString(voList) + ",\"total\":"
                + voList.size() + "}";

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
