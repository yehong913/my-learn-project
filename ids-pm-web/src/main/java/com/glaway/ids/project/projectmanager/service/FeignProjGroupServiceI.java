package com.glaway.ids.project.projectmanager.service;

import com.glaway.foundation.common.dto.FdTeamRoleGroupDto;
import com.glaway.foundation.common.dto.TSGroupDto;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.projectmanager.fallback.FallBackProjRolesServiceI;
import com.glaway.ids.project.projectmanager.fallback.FeignProjGroupServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by LHR on 2019/7/26.
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = FeignProjGroupServiceCallback.class)
public interface FeignProjGroupServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getSysGroupListByTeamIdAndRoleCode.do")
    List<TSGroupDto> getSysGroupListByTeamIdAndRoleCode(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "roleCode")String roleCode);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/getFdTeamRoleGroupListByTeamIdAndRoleCode.do")
    List<FdTeamRoleGroupDto> getFdTeamRoleGroupListByTeamIdAndRoleCode(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "roleCode") String roleCode);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projRolesRestController/addRoleGroup.do")
    void addRoleGroup(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "roleCode")String roleCode, @RequestParam(value = "id")String id);
}
