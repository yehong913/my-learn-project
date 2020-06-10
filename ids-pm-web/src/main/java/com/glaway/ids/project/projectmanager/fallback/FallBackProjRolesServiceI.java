package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.FeignProjRolesServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.vo.GroupUserDetailVo;
import com.glaway.ids.project.projectmanager.vo.TeamVo;
import feign.Feign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LHR on 2019/7/26.
 */
@Component
public class FallBackProjRolesServiceI implements FallbackFactory<FeignProjRolesServiceI> {
    @Override
    public FeignProjRolesServiceI create(Throwable throwable) {
        return new FeignProjRolesServiceI(){
            @Override
            public List<TeamVo> getSysUserListByTeamId(String teamId) {
                return new ArrayList<>();
            }

            @Override
            public List<TeamVo> getSysGroupListByTeamId(String teamId) {
                return new ArrayList<>();
            }

            @Override
            public void addTeamRoleByCode(String teamId, String code) {

            }

            @Override
            public FeignJson getTeamIdByProjectId(String projectId) {
                return null;
            }

            @Override
            public void BatchDelRoleAndUsers(FeignJson json) {

            }

            @Override
            public List<GroupUserDetailVo> getGroupUserDetailVoList(String groupId) {
                return null;
            }

//            @Override
//            public FeignJson doBatchDelRoleAndUsers(String projectId, String teamId, String userStr, String roleStr, String groupStr) {
//                return null;
//            }
        };
    }
}
