package com.glaway.ids.project.plan.fallback;

import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.NameStandardDeliveryRelationDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.NameStandardDeliveryRelationRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NameStandardDeliveryRelationRemoteFeignCallBack implements FallbackFactory<NameStandardDeliveryRelationRemoteFeignServiceI> {

	@Override
	public NameStandardDeliveryRelationRemoteFeignServiceI create(Throwable throwable) {
		return new NameStandardDeliveryRelationRemoteFeignServiceI(){


			@Override
			public List<NameStandardDeliveryRelationDto> searchForPage(NameStandardDeliveryRelationDto relation, int page, int rows) {
				return null;
			}
		};
	}
}

