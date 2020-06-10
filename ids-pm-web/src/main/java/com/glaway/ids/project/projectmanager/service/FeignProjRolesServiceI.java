package com.glaway.ids.project.projectmanager.service;

import com.glaway.foundation.common.dto.FdTeamRoleGroupDto;
import com.glaway.foundation.common.dto.FdTeamRoleUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.fallback.FallBackProjRolesServiceI;
import com.glaway.ids.project.projectmanager.fallback.ProjectRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.vo.GroupUserDetailVo;
import com.glaway.ids.project.projectmanager.vo.TeamVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by LHR on 2019/7/26.
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE, fallbackFactory = FallBackProjRolesServiceI.class)
public interface FeignProjRolesServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getSysUserListByTeamId.do")
    List<TeamVo> getSysUserListByTeamId(@RequestParam(value = "teamId") String teamId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getSysGroupListByTeamId.do")
    List<TeamVo> getSysGroupListByTeamId(@RequestParam(value = "teamId") String teamId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/addTeamRoleByCode.do")
    void addTeamRoleByCode(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "code") String code);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getTeamIdByProjectId.do")
    FeignJson getTeamIdByProjectId(@RequestParam(value = "projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/BatchDelRoleAndUsers.do")
    void BatchDelRoleAndUsers(@RequestBody FeignJson json);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getGroupUserDetailVoList.do")
    List<GroupUserDetailVo> getGroupUserDetailVoList(@RequestParam(value = "groupId") String groupId);

//    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getRoleUsersByRoleCodeAndTeamId.do")
//    List<FdTeamRoleUserDto> getRoleUsersByRoleCodeAndTeamId(@RequestParam(value = "roleCode") String roleCode, @RequestParam(value = "teamId")String teamId);
//
//    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getRoleGroupsByRoleCodeAndTeamId.do")
//    List<FdTeamRoleGroupDto> getRoleGroupsByRoleCodeAndTeamId(@RequestParam(value = "roleCode") String roleCode, @RequestParam(value = "teamId")String teamId);

//    FeignJson doBatchDelRoleAndUsers(@RequestParam(value = "projectId")String projectId,@RequestParam(value = "teamId") String teamId, @RequestParam(value = "userStr")String userStr,@RequestParam(value = "roleStr") String roleStr, @RequestParam(value = "groupStr")String groupStr);
}
