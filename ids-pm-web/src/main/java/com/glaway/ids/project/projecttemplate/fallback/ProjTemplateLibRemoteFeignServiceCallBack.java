package com.glaway.ids.project.projecttemplate.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.dto.rep.RepRoleFileAuthDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projectmanager.vo.ProjLibRoleFileAuthVo;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateLibRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjTemplateLibRemoteFeignServiceCallBack implements FallbackFactory<ProjTemplateLibRemoteFeignServiceI> {

	@Override
	public ProjTemplateLibRemoteFeignServiceI create(Throwable throwable) {
		return new ProjTemplateLibRemoteFeignServiceI(){
			@Override
			public boolean judgeAndAddValidRoleAuthSize(List<RepRoleFileAuthDto> roleFileAuthsList) {
				return false;
			}

			@Override
			public List<ProjLibRoleFileAuthVo> convertProjTemplateRoleFileAuthsVO(List<RepRoleFileAuthDto> roleFileAuthsList) {
				return null;
			}

			@Override
			public FeignJson applyTemplete(String templateId, String libId, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public boolean isFileNameRepeat(String templateId, String fileName) {
				return false;
			}
		};
	}

}



