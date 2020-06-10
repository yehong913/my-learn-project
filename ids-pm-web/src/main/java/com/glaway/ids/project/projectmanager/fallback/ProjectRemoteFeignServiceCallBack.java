package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileAttachmentDto;
import com.glaway.ids.project.menu.service.RecentlyProjectRemoteFeignServiceI;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projectmanager.dto.ProjWarnDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.ProjWarnForGridVo;
import com.glaway.ids.project.projectmanager.vo.ProjWarnVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProjectRemoteFeignServiceCallBack implements FallbackFactory<ProjectRemoteFeignServiceI> {

	@Override
	public ProjectRemoteFeignServiceI create(Throwable throwable) {
		return new ProjectRemoteFeignServiceI(){
			@Override
			public TSRoleDto getRole(String roleCode) {
				return null;
			}

			@Override
			public void updateProject(String[] managerIds, String teamId, Project project, String userId) {

			}

			@Override
			public String initProject(Project project) {
				return null;
			}

			@Override
			public FeignJson doProjectUpdate(Project project, String pagetype, String isUpdate, String userId) {
				return null;
			}


            @Override
			public void batchDeleteProject(List<Project> project, String ids, String userId) {

			}

			@Override
			public FeignJson projectBatchDelete(String ids, String userId) {
				return new FeignJson();
			}

			@Override
			public List<ProjWarnForGridVo> queryProjectwarnGrid(String projectId) {
				return null;
			}

			@Override
			public FeignJson getProj(String userId) {
				return null;
			}

			@Override
			public List<Project> getProjectListForPortlet(TSUserDto userDto) {
				return null;
			}

			@Override
			public FeignJson getProjWarm(String userId) {
				return null;
			}

			@Override
			public FeignJson addProjWarn(ProjWarnDto projWarn, String userId, String orgId) {
				return null;
			}

			@Override
			public List<ProjWarnVo> getProjWarnReportData(String projectIds) {
				return null;
			}

			@Override
			public List<Project> getStartingProjByUserId(String userId) {
				return null;
			}

			@Override
			public PageList queryEntityBySql(List<ConditionVO> conditionList, String projectManager, String createName, String entryPage, String isTeamMember, String currentUserId, String orgId) {
				return null;
			}

			@Override
			public String getProjectbyNumber(String projectNumber) {
				return null;
			}

			@Override
			public FeignJson createProject(Project project, String currentUserStr, String orgId) {
				return null;
			}

			@Override
			public Project getProjectEntity(String projectId) {
				return null;
			}


			@Override
			public String getProjLogByProjectId(String projectId, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public long getProjLogListCount(String projectNumber) {
				return 0;
			}

			@Override
			public boolean isModifyForPlan(String projectId) {
				return false;
			}

			@Override
			public FeignJson getLifeCycleStatusList() {
				return new FeignJson();
			}

			@Override
			public boolean submitProjectFlowAgain(Project project, String approvePerson, String deptApprovePerson, String remark,String userId) {
				return false;
			}

			@Override
			public void submitProjectFlow(Project project, String approvePerson, String deptApprovePerson, String remark, String processDefinitionKey, String userId) {

			}

			@Override
			public List<Map<String, Object>> getProjWarnDataForProjStatistics(String projectId) {
				return null;
			}


			@Override
			public FeignJson doStartProject(Map<String, Object> params) {
				return new FeignJson();
			}

			@Override
			public FeignJson doPauseOrResumeProject(Map<String, Object> params) {
				return new FeignJson();
			}

			@Override
			public FeignJson doCloseProject(Map<String, Object> params) {
				return new FeignJson();
			}

			@Override
			public FeignJson judgeIsTeamManager(String projectId, String userId) {
				return new FeignJson();
			}

			@Override
			public List<PlanDto> getOneLevelPlanByProject(String projectId) {
				return new ArrayList<PlanDto>();
			}

			@Override
			public void addRoleUser(String teamId, String roleCode, String userId) {

			}

			@Override
			public void upadteProjectManagerNames(String projectId, String type) {

			}

			@Override
			public FeignJson resubmitProjectFlow(Project project, String oper, String id, String userId) {
				return new FeignJson();
			}

			@Override
			public List<Project> getProjectPortletList(String userId) {
				return null;
			}
		};
	}
}

