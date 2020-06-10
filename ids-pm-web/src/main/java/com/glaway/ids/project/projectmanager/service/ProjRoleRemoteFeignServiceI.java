/*
 * 文件名：ProjRoleRemoteFeignServiceI.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年5月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.service;


import com.glaway.foundation.common.dto.FdTeamRoleUserDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.menu.dto.ProjTeamLinkDto;
import com.glaway.ids.project.projectmanager.fallback.ProjRoleRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjRoleRemoteFeignServiceCallBack.class)
public interface ProjRoleRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/isSysRoleByUserIdAndRoleCode.do")
    boolean isSysRoleByUserIdAndRoleCode(@RequestParam(value = "userId",required = false)String userId,@RequestParam(value = "PMO",required = false)String PMO);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/isProjRoleByUserIdAndRoleCode.do")
    boolean isProjRoleByUserIdAndRoleCode(@RequestParam(value = "projectId",required = false) String projectId, @RequestParam(value = "roleCode",required = false) String roleCode, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getTeamIdByProjectId.do")
    FeignJson getTeamIdByProjectId(@RequestParam(value = "projectId",required = false)String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getUserInProjectByTeamId.do")
    List<TSUserDto> getUserInProjectByTeamId(@RequestParam("projectId") String projectId,@RequestParam("teamId") String teamId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getUserInProject.do")
    List<TSUserDto> getUserInProject(@RequestParam("projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getLibIdByProjectId.do")
    FeignJson getLibIdByProjectId(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/searchProjTeamLink.do")
    List<ProjTeamLinkDto> searchProjTeamLink(@RequestBody ProjTeamLinkDto projTeamLink);

//    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getRoleUsersByRoleCodeAndTeamId.do")
//    List<FdTeamRoleUserDto> getRoleUsersByRoleCodeAndTeamId(@RequestParam(value = "roldeCode") String roldeCode, @RequestParam(value = "teamId")String teamId);
}
