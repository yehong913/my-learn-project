package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.FdTeamRoleGroupDto;
import com.glaway.foundation.common.dto.TSGroupDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.projectmanager.service.FeignProjGroupServiceI;
import com.glaway.ids.project.projectmanager.service.FeignProjRolesServiceI;
import com.glaway.ids.project.projectmanager.vo.GroupUserDetailVo;
import com.glaway.ids.project.projectmanager.vo.TeamVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LHR on 2019/7/26.
 */
@Component
public class FeignProjGroupServiceCallback implements FallbackFactory<FeignProjGroupServiceI> {
    @Override
    public FeignProjGroupServiceI create(Throwable throwable) {
        return new FeignProjGroupServiceI(){
            @Override
            public List<TSGroupDto> getSysGroupListByTeamIdAndRoleCode(String teamId, String roleCode) {
                return null;
            }

            @Override
            public List<FdTeamRoleGroupDto> getFdTeamRoleGroupListByTeamIdAndRoleCode(String teamId, String roleCode) {
                return null;
            }

            @Override
            public void addRoleGroup(String teamId, String roleCode, String id) {

            }
        };
    }
}
