package com.glaway.ids.project.projecttemplate.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRoleRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjTemplateRoleRemoteFeignServiceCallBack implements FallbackFactory<ProjTemplateRoleRemoteFeignServiceI> {

	@Override
	public ProjTemplateRoleRemoteFeignServiceI create(Throwable throwable) {
		return new ProjTemplateRoleRemoteFeignServiceI(){
			@Override
			public FeignJson getTeamIdByTemplateId(String projTemplateId) {
				return null;
			}
		};
	}

}



