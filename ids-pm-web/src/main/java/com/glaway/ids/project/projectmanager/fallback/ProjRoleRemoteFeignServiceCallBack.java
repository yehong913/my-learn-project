package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.FdTeamRoleUserDto;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.menu.dto.ProjTeamLinkDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjRoleRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjRoleRemoteFeignServiceCallBack implements FallbackFactory<ProjRoleRemoteFeignServiceI> {

	@Override
	public ProjRoleRemoteFeignServiceI create(Throwable throwable) {
		return new ProjRoleRemoteFeignServiceI(){

			@Override
			public boolean isSysRoleByUserIdAndRoleCode(String userId, String PMO) {
				return false;
			}

			@Override
			public boolean isProjRoleByUserIdAndRoleCode(String projectId, String roleCode, String userId) {
				return false;
			}

			@Override
			public FeignJson getTeamIdByProjectId(String projectId) {
				return new FeignJson();
			}

			@Override
			public List<ProjTeamLinkDto> searchProjTeamLink(ProjTeamLinkDto projTeamLink) {
				return null;
			}

			@Override
			public List<TSUserDto> getUserInProjectByTeamId(String projectId, String teamId) {
				return null;
			}

			@Override
			public List<TSUserDto> getUserInProject(String projectId) {
				return null;
			}

			@Override
			public FeignJson getLibIdByProjectId(String projectId) {
				return null;
			}

			//			@Override
//			public List<FdTeamRoleUserDto> getRoleUsersByRoleCodeAndTeamId(String roldeCode, String teamId) {
//				return null;
//			}
		};
	}
}

