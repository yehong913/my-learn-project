package com.glaway.ids.project.plan.fallback;

import com.glaway.ids.common.dto.DeliveryStandardDocTypeDto;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.DeliveryStandardRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryStandardRemoteFeignServiceCallBack implements FallbackFactory<DeliveryStandardRemoteFeignServiceI> {

	@Override
	public DeliveryStandardRemoteFeignServiceI create(Throwable throwable) {
		return new DeliveryStandardRemoteFeignServiceI(){

			@Override
			public List<DeliveryStandardDto> searchDeliveryStandardAccurate(DeliveryStandardDto condition) {
				return null;
			}

			@Override
			public List<DeliveryStandardDto> searchUseableDeliveryStandards(DeliveryStandardDto condition) {
				return null;
			}

            @Override
            public DeliveryStandardDto getDeliveryStandardByName(String name) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<DeliveryStandardDto> searchDeliveryStandards(DeliveryStandardDto ds) {
                // TODO Auto-generated method stub
                return null;
            }

			@Override
			public DeliveryStandardDto getDeliveryStandardEntity(String id) {
				return null;
			}

			@Override
			public DeliveryStandardDocTypeDto getDeliveryStandardDocTypeById(String id) {
				return null;
			}
		};
	}
}

