package com.glaway.ids.project.projecttemplate.fallback;

import com.glaway.ids.config.dto.ProjectLibTemplateDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.projectmanager.service.ProjectLibTemplateRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateDetailRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjTemplateDetailRemoteFeignServiceCallBack implements FallbackFactory<ProjTemplateDetailRemoteFeignServiceI> {

	@Override
	public ProjTemplateDetailRemoteFeignServiceI create(Throwable throwable) {
		return new ProjTemplateDetailRemoteFeignServiceI(){
			@Override
			public List<PlanDto> convertPlanjTemplateDetail2Plan(String projTemplateId) {
				return null;
			}
		};
	}

}



