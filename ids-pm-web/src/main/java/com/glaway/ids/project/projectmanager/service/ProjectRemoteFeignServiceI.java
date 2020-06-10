package com.glaway.ids.project.projectmanager.service;


import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projectmanager.dto.ProjLogDto;
import com.glaway.ids.project.projectmanager.dto.ProjWarnDto;
import com.glaway.ids.project.projectmanager.dto.Project;

import com.glaway.ids.project.projectmanager.fallback.ProjectRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.vo.ProjWarnForGridVo;
import com.glaway.ids.project.projectmanager.vo.ProjWarnVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 〈项目管理接口〉
 * 〈功能详细描述〉
 *
 * @author songjie
 * @version 2015年3月24日
 * @see ProjectRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ProjectRemoteFeignServiceCallBack.class)
public interface ProjectRemoteFeignServiceI{

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/initProject.do")
    String initProject(@RequestBody Project project);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/queryEntityBySql.do")
    PageList queryEntityBySql(@RequestBody List<ConditionVO> conditionList, @RequestParam(value="projectManager",required = false) String projectManager,
                              @RequestParam(value="createName",required = false) String createName,@RequestParam(value="entryPage",required = false) String entryPage,
                              @RequestParam(value="isTeamMember",required = false) String isTeamMember,@RequestParam(value="currentUserId",required = false) String currentUserId,@RequestParam(value="orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjectbyNumber.do")
    String getProjectbyNumber(@RequestParam("projectNumber") String projectNumber);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/createProject.do")
    FeignJson createProject(@RequestBody Project project,@RequestParam("currentUserStr") String currentUserStr,@RequestParam("orgId") String orgId);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjectEntity.do")
    Project getProjectEntity(@RequestParam("projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjLogByProjectId.do")
    String getProjLogByProjectId(@RequestParam("projectId") String projectId, @RequestParam("page") int page,
                                 @RequestParam("rows") int rows,@RequestParam("isPage")  boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjLogListCount.do")
    long getProjLogListCount(@RequestParam("projectNumber") String projectNumber);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/isModifyForPlan.do")
    boolean isModifyForPlan(@RequestParam(value="projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/submitProjectFlowAgain.do")
    boolean submitProjectFlowAgain(@RequestBody Project project, @RequestParam(value = "approvePerson") String approvePerson, @RequestParam(value = "deptApprovePerson")String deptApprovePerson,
                                   @RequestParam(value = "remark")String remark,@RequestParam(value = "userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/submitProjectFlow.do")
    void submitProjectFlow(@RequestBody Project project, @RequestParam(value = "approvePerson") String approvePerson, @RequestParam(value = "deptApprovePerson")String deptApprovePerson,
                           @RequestParam(value = "remark")String remark, @RequestParam(value = "processDefinitionKey")String processDefinitionKey,@RequestParam(value = "userId") String userId);

    //    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getUsedRolesList.do")
//    List<TSRoleDto> getUsedRolesList(@RequestParam(value = "teamId") String teamId);
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/doStartProject.do")
    FeignJson doStartProject(@RequestBody Map<String,Object> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/doPauseOrResumeProject.do")
    FeignJson doPauseOrResumeProject(@RequestBody Map<String,Object> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/doCloseProject.do")
    FeignJson doCloseProject(@RequestBody Map<String,Object> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/judgeIsTeamManager.do")
    FeignJson judgeIsTeamManager(@RequestParam(value = "projectId",required = false) String projectId, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getOneLevelPlanByProject.do")
    List<PlanDto> getOneLevelPlanByProject(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/addRoleUser.do")
    void addRoleUser(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "roleCode")String roleCode, @RequestParam(value = "userId")String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/upadteProjectManagerNames.do")
    void upadteProjectManagerNames(@RequestParam(value = "projectId") String projectId,@RequestParam(value = "type")String type);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getRole.do")
    TSRoleDto getRole(@RequestParam("roleCode") String roleCode);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/updateProject.do")
    void updateProject(@RequestParam("managerIds") String[] managerIds, @RequestParam("teamId") String teamId,@RequestBody Project project,@RequestParam("userId")String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/batchDeleteProject.do")
    void batchDeleteProject(@RequestBody List<Project> project,@RequestParam(value = "ids", required = false) String ids,@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/projectBatchDelete.do")
    FeignJson projectBatchDelete(@RequestParam(value = "ids", required = false) String ids, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/resubmitProjectFlow.do")
    FeignJson resubmitProjectFlow(@RequestBody Project project,@RequestParam(value = "oper",required = false) String oper,
                                  @RequestParam(value = "id",required = false) String id, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/doProjectUpdate.do")
    FeignJson doProjectUpdate(@RequestBody Project project,@RequestParam(value = "pagetype",required = false) String pagetype,
                              @RequestParam(value = "isUpdate",required = false) String isUpdate,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProj.do")
    FeignJson getProj(@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjectListForPortlet.do")
    List<Project> getProjectListForPortlet(@RequestBody TSUserDto userDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjWarm.do")
    FeignJson getProjWarm(@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/addProjWarn.do")
    FeignJson addProjWarn(@RequestBody ProjWarnDto projWarn,@RequestParam(value = "userId",required = false) String userId, @RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjWarnReportData.do")
    List<ProjWarnVo> getProjWarnReportData(@RequestParam(value = "projectIds",required = false) String projectIds);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjWarnDataForProjStatistics.do")
    List<Map<String,Object>> getProjWarnDataForProjStatistics(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/queryProjectwarnGrid.do")
    List<ProjWarnForGridVo> queryProjectwarnGrid(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getStartingProjByUserId.do")
    List<Project> getStartingProjByUserId(@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getProjectPortletList.do")
    List<Project> getProjectPortletList(@RequestParam(value = "userId",required = false) String userId);
}
