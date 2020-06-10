package com.glaway.ids.project.projectmanager.controller;

import com.glaway.foundation.common.dto.FdTeamRoleGroupDto;
import com.glaway.foundation.common.dto.FdTeamRoleUserDto;
import com.glaway.foundation.common.dto.TSGroupDto;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.log.BaseLogFactory;
import com.glaway.foundation.common.log.OperationLog;
import com.glaway.foundation.common.util.CommonUtil;
import com.glaway.foundation.common.util.I18nUtil;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.common.util.UserUtil;
import com.glaway.foundation.core.common.controller.BaseController;
import com.glaway.foundation.core.common.model.json.AjaxJson;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignRoleService;
import com.glaway.foundation.fdk.dev.service.threemember.FeignTeamService;
import com.glaway.foundation.tag.core.easyui.TagUtil;
import com.glaway.ids.constant.ProjectConstants;
import com.glaway.ids.constant.ProjectRoleConstants;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.FeignProjGroupServiceI;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LHR on 2019/7/24.
 */
@Controller
@RequestMapping("/projRoleUsersController")
public class ProjRoleUsersController extends BaseController {


    @Autowired
    private ProjectRemoteFeignServiceI projectService;

    /**
     * 项目团队服务实现接口
     */
    @Autowired
    private ProjRoleRemoteFeignServiceI projRoleService;

    /**
     * message
     */
    private String message;

    @Autowired
    private FeignProjGroupServiceI feignProjGroupServiceI;

    @Autowired
    private FeignTeamService feignTeamService;

    @Autowired
    private FeignRoleService feignRoleService;

    @Autowired
    private ProjectRemoteFeignServiceI projectRemoteFeignServiceI;

    private static final OperationLog log = BaseLogFactory.getOperationLog(ProjRoleUsersController.class);


    /**
     * 判断某用户是否是某项目下的项目经理或者是PMO
     *
     * @return
     */
    @RequestMapping(params = "isProjectManagerOrPMO")
    @ResponseBody
    public void isProjectManagerOrPMO(HttpServletRequest requset, HttpServletResponse response) {
        String userId = requset.getParameter("userId");
        String id = requset.getParameter("id");
        Project p = projectService.getProjectEntity(id);
        // 为空则取当前用户
        if (StringUtils.isBlank(userId)) {
            userId = UserUtil.getInstance().getUser().getId();
        }
        boolean isProjectManger = projRoleService.isProjRoleByUserIdAndRoleCode(p.getId(),
                ProjectRoleConstants.PROJ_MANAGER, userId);
        boolean isPMO = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
                ProjectRoleConstants.PMO);
        boolean result = false;
        if (isProjectManger || isPMO) {
            result = true;
        }
        TagUtil.ajaxResponse(response, String.valueOf(result));
    }

    /**
     * 添加项目角色
     *
     * @param
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchAddGroup01")
    @ResponseBody
    public AjaxJson doBatchAddGroup01(HttpServletRequest request) {
        message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addSuccess");
        String[] ids = request.getParameter("ids").split(",");
        String[] groupCodes = request.getParameter("groupCodes").split(",");
        String[] groupNames = request.getParameter("groupNames").split(",");
        String projectId = request.getParameter("projectId");
        String roleCode = request.getParameter("roleCode");

        FeignJson feignJson= projRoleService.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        List<TSGroupDto> tsGroups = new ArrayList<>();
        //修改BUG如果选中为空的处理
        if(StringUtils.isNotEmpty(request.getParameter("ids"))){
            for (int i = 0; i < ids.length; i++ ) {
                TSGroupDto t = new TSGroupDto();
                t.setId(ids[i]);
                t.setGroupCode(groupCodes[i]);
                t.setGroupName(groupNames[i]);
                tsGroups.add(t);
            }
        }

        AjaxJson j = new AjaxJson();
        if (StringUtils.isNotBlank(roleCode) && StringUtils.isNotBlank(projectId)
                && !CommonUtil.isEmpty(tsGroups)) {
            try {
                List<FdTeamRoleGroupDto> roleGroups = feignProjGroupServiceI.getFdTeamRoleGroupListByTeamIdAndRoleCode(teamId, roleCode);
                List<String> temp = new ArrayList<String>();
                for (TSGroupDto g : tsGroups) {
                    for (FdTeamRoleGroupDto rg : roleGroups) {
                        String groupId = rg.getGroupId();
                        if (groupId != null) {
                            if (rg.getGroupId().equals(g.getId())) {
                                temp.add(g.getId());
                            }
                        }
                    }
                }

                for (TSGroupDto g : tsGroups) {
                    if (temp.contains(g.getId())) {
                        continue;
                    }
                    feignProjGroupServiceI.addRoleGroup(teamId, roleCode, g.getId());
                }

                //修改BUG 项目团队中  团队新增时候  如果取消勾选了已选的成员组  应当做相应的删除
                //目前群组选择暂无回显，此段先注释，有回显之后需要放开
               /* List<FdTeamRoleGroupDto> roleGroupsNew = feignProjGroupServiceI.getFdTeamRoleGroupListByTeamIdAndRoleCode(
                        teamId, roleCode);
                if(roleGroupsNew!=null&&roleGroupsNew.size()>0)
                {
                    for (FdTeamRoleGroupDto fdTeamRoleGroup : roleGroupsNew)
                    {
                        //判断团队中的成员组是否已经被移除 参数
                        boolean flag=true;
                        for (TSGroupDto g : tsGroups)
                        {
                            //判断团队中的成员组是否已经被移除
                            if(fdTeamRoleGroup.getGroupId().equals(g.getId()))
                            {
                                flag=false;
                                break;
                            }

                        }
                        if(flag)
                        {
                            //删除没有选择的用户组
                            feignTeamService.deleteFdTeamRoleGroupByTeamIdAndRoleIdAndGroupId(ResourceUtil.getApplicationInformation().getAppKey(),teamId, fdTeamRoleGroup.getRoleId(), fdTeamRoleGroup.getGroupId());

                        }

                    }
                }*/

                if (StringUtils.equals(roleCode, ProjectRoleConstants.PROJ_MANAGER)) {
                    projectService.upadteProjectManagerNames(projectId,null);
                }

            }
            catch (Exception e) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
                log.error(message, e);
            }
            finally {
                j.setMsg(message);
                return j;
            }
        }else{
            //修改BUG 项目团队中  团队新增时候  如果取消勾选了已选的成员组  应当做相应的删除
            List<FdTeamRoleGroupDto> roleGroupsNew = feignProjGroupServiceI.getFdTeamRoleGroupListByTeamIdAndRoleCode(
                    teamId, roleCode);
            if(roleGroupsNew!=null&&roleGroupsNew.size()>0)
            {
                for (FdTeamRoleGroupDto fdTeamRoleGroup : roleGroupsNew)
                {

                    feignTeamService.deleteFdTeamRoleGroupByTeamIdAndRoleIdAndGroupId(ResourceUtil.getApplicationInformation().getAppKey(),teamId, fdTeamRoleGroup.getRoleId(), fdTeamRoleGroup.getGroupId());
                }
            }
        }
        return j;
    }

    /**
     * 添加项目角色
     *
     * @param
     * @return
     */
    @SuppressWarnings("finally")
    @RequestMapping(params = "doBatchAdd")
    @ResponseBody
    public AjaxJson doBatchAdd(HttpServletRequest request) {
        String selectUserStr = request.getParameter("selectUser");
        String projectId = request.getParameter("projectId");
        String roleCode = request.getParameter("roleCode");
        // Project project = projectService.getEntity(projectId);
        FeignJson feignJson = projRoleService.getTeamIdByProjectId(projectId);
        String teamId = (String) feignJson.getObj();
        // List<TSUser> tsUsers = JSONArray.parseArray(selectUserStr, TSUser.class);
        String[] tsUsers = selectUserStr.split(",");
        AjaxJson j = new AjaxJson();
        if (StringUtils.isNotBlank(roleCode) && StringUtils.isNotBlank(projectId)
                && StringUtils.isNotBlank(selectUserStr)) {
            try {
                String appKey = ResourceUtil.getApplicationInformation().getAppKey();
                FdTeamRoleUserDto roleUserDto = new FdTeamRoleUserDto();
                roleUserDto.setTeamId(teamId);
                TSRoleDto roleDto = feignRoleService.getRoleByRoleCode(ProjectRoleConstants.PROJ_MANAGER);
                roleUserDto.setRoleId(roleDto.getId());
                List<FdTeamRoleUserDto> roleUsers = feignTeamService.getFdTeamRoleUser(appKey,roleUserDto);
                List<String> temp = new ArrayList<String>();
                for (int i = 0; i < tsUsers.length; i++ ) {
                    for (FdTeamRoleUserDto pu : roleUsers) {
                        if (pu.getUserId().equals(tsUsers[i])) {
                            temp.add(pu.getUserId());
                        }
                    }
                }

                for (int c = 0; c < tsUsers.length; c++ ) {
                    if(StringUtils.equals(roleCode, ProjectRoleConstants.PROJ_MANAGER)){
                        if (temp.contains(tsUsers[c])) {
                            continue;
                        }
                        projectRemoteFeignServiceI.addRoleUser(teamId, roleCode, tsUsers[c]);
                    }else{
                        projectRemoteFeignServiceI.addRoleUser(teamId, roleCode, tsUsers[c]);
                    }

                }
                //修改BUG如果选择的团队成员已经不再原来团队中  已经取消选择 删除团队角色中不存在的成员
//                List<FdTeamRoleUserDto> roleUsersNew = feignTeamService.getFdTeamRoleUser(appKey,roleUserDto);
                List<FdTeamRoleUserDto> roleUsersNew = feignTeamService.getRoleUsersByRoleCodeAndTeamId(appKey, roleCode, teamId);
                if(roleUsersNew!=null&&roleUsersNew.size()>0)
                {
                    for (FdTeamRoleUserDto fdTeamRoleUser : roleUsersNew)
                    {
                        //判断原先团队中该角色下的成员 是否是被选中的   未被选中删除 原先团中没选中的成员
                        if(!selectUserStr.contains(fdTeamRoleUser.getUserId())) {
                            feignTeamService.deleteFdTeamRoleUserByTeamIdAndRoleIdAndUserId(teamId, fdTeamRoleUser.getRoleId(),fdTeamRoleUser.getUserId());
                        }

                    }
                }

                if (StringUtils.equals(roleCode, ProjectRoleConstants.PROJ_MANAGER)) {
                    projectService.upadteProjectManagerNames(projectId,null);
                }

            }
            catch (Exception e) {
                message = I18nUtil.getValue("com.glaway.ids.pm.project.projectmanager.role.addFailure");
                log.error(message, e);
            }
            finally {
                j.setMsg(message);
                return j;
            }
        }else{
            if (!StringUtils.equals(roleCode, ProjectRoleConstants.PROJ_MANAGER))
            {
                //修改BUG如果选择的团队成员已经不再原来团队中  已经取消选择 删除团队角色中不存在的成员
                String appKey = ResourceUtil.getApplicationInformation().getAppKey();
                FdTeamRoleUserDto roleUserDto = new FdTeamRoleUserDto();
                roleUserDto.setTeamId(teamId);
                TSRoleDto roleDto = feignRoleService.getRoleByRoleCode(ProjectRoleConstants.PROJ_MANAGER);
                roleUserDto.setRoleId(roleDto.getId());
//                List<FdTeamRoleUserDto> roleUsersNew = feignTeamService.getFdTeamRoleUser(appKey,roleUserDto);
                List<FdTeamRoleUserDto> roleUsersNew = feignTeamService.getRoleUsersByRoleCodeAndTeamId(appKey,roleCode, teamId);
                if(roleUsersNew!=null&&roleUsersNew.size()>0)
                {
                    for (FdTeamRoleUserDto fdTeamRoleUser : roleUsersNew)
                    {
                        //判断原先团队中该角色下的成员 是否是被选中的   未被选中删除 原先团中没选中的成员
                        if(!selectUserStr.contains(fdTeamRoleUser.getUserId()))
                        {
                            feignTeamService.deleteFdTeamRoleUserByTeamIdAndRoleIdAndUserId(teamId, fdTeamRoleUser.getRoleId(),fdTeamRoleUser.getUserId());
                        }

                    }
                }
            }
        }
        return j;
    }


    /**
     * 判断某用户是否具有修改的权限
     *
     * @return
     */
    @RequestMapping(params = "isHasModifyAuth")
    public void isHasModifyAuth(HttpServletRequest requset, HttpServletResponse response) {
        String userId = requset.getParameter("userId");
        String id = requset.getParameter("id");
        Project p = projectService.getProjectEntity(id);
        boolean isHasModifyAuth = false;
        // 为空则取当前用户
        if (StringUtils.isBlank(userId)) {
            userId = UserUtil.getInstance().getUser().getId();
        }
        if (ProjectConstants.EDITING.equals(p.getBizCurrent())) {
            boolean isPMO = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
                    ProjectRoleConstants.PMO);
            if (isPMO) {
                isHasModifyAuth = true;
            }
        }
        else {
            boolean isPMO = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
                    ProjectRoleConstants.PMO);
            boolean isProjectManger = projRoleService.isProjRoleByUserIdAndRoleCode(p.getId(),
                    ProjectRoleConstants.PROJ_MANAGER, userId);
            if (isProjectManger || isPMO) {
                isHasModifyAuth = true;
            }
        }

        TagUtil.ajaxResponse(response, String.valueOf(isHasModifyAuth));
    }

    /**
     * 判断某用户是否是项目管理员
     *
     * @return
     */
    @RequestMapping(params = "isPMO")
    public void isPMO(HttpServletRequest requset, HttpServletResponse response) {
        String userId = requset.getParameter("userId");
        // 为空则取当前用户
        if (StringUtils.isBlank(userId)) {
            userId = UserUtil.getInstance().getUser().getId();
        }
        boolean isPMO = projRoleService.isSysRoleByUserIdAndRoleCode(userId,
                ProjectRoleConstants.PMO);
        TagUtil.ajaxResponse(response, String.valueOf(isPMO));
    }
}
