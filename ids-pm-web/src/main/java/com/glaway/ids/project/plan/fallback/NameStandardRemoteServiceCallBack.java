package com.glaway.ids.project.plan.fallback;

import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.NameStandardDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.NameStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.planview.dto.PlanViewColumnInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.UserPlanViewProjectDto;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NameStandardRemoteServiceCallBack implements FallbackFactory<NameStandardRemoteFeignServiceI> {

	@Override
	public NameStandardRemoteFeignServiceI create(Throwable throwable) {
		return new NameStandardRemoteFeignServiceI(){


			@Override
			public List<NameStandardDto> searchNameStandardsExceptDesign(NameStandardDto nameStandard) {
				return null;
			}

			@Override
			public List<NameStandardDto> searchNameStandards(NameStandardDto nameStandardDto) {
				return null;
			}

			@Override
			public List<DeliveryStandardDto> searchDeliverablesForPage(DeliveryStandardDto ds, int page, int rows, String notIn) {
				return null;
			}

			@Override
			public List<DeliveryStandardDto> searchDeliveryStandardsForPage(DeliveryStandardDto ds, int page, int rows) {
				return null;
			}

			@Override
			public long getDeliverablesCount(DeliveryStandardDto ds, String notIn) {
				return 0;
			}

			@Override
			public long getSearchCount(DeliveryStandardDto ds) {
				return 0;
			}

			@Override
			public DeliveryStandardDto getDeliveryStandardEntity(String id) {
				return null;
			}

            @Override
            public List<NameStandardDto> searchNameStandardsAccurate(NameStandardDto ns) {
                // TODO Auto-generated method stub
                return null;
            }

			@Override
			public BusinessConfigDto getBusinessConfig(String id) {
				return new BusinessConfigDto();
			}
		};
	}
}

