package com.glaway.ids.project.plantemplate.fallback;

import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateDetailRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PlanTemplateDetailRemoteFeignServiceCallBack implements FallbackFactory<PlanTemplateDetailRemoteFeignServiceI> {

	@Override
	public PlanTemplateDetailRemoteFeignServiceI create(Throwable throwable) {
		return new PlanTemplateDetailRemoteFeignServiceI(){
			@Override
			public FeignJson getPlanTemplateOrProjTemplateDetailPreposes(String plantemplateId, String type) {
				return null;
			}

			@Override
			public FeignJson getPlanTemplateDetailPreposesId(String plantemplateId) {
				return null;
			}

			@Override
			public FeignJson getPlanTemplateDetailList(String planTemplateId) {
				return null;
			}

			@Override
			public Map<String, String> getPlanTemplateOrProjTemplateDetailDeliverables(String plantemplateId, String type) {
				return null;
			}

			@Override
			public Map<String, String> getPlanTemplateDetailInputsName(List<InputsDto> list) {
				return null;
			}

			@Override
			public Map<String, String> getPlanTemplateDetailInputsOrigin(Map<String, Object> paramsMap) {
				return null;
			}

		};
	}
}

