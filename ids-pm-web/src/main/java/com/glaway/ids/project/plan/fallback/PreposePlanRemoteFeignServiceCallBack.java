package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.FeignPlanChangeServiceI;
import com.glaway.ids.project.plan.service.PreposePlanRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PreposePlanRemoteFeignServiceCallBack implements FallbackFactory<PreposePlanRemoteFeignServiceI> {

	@Override
	public PreposePlanRemoteFeignServiceI create(Throwable throwable) {
		return new PreposePlanRemoteFeignServiceI(){

            @Override
            public List<PreposePlanDto> getPreposePlansByPlanId(PlanDto dto) {
                return null;
            }

            @Override
            public List<PreposePlanDto> getPreposePlansByParent(PlanDto parent) {
                return null;
            }

            @Override
            public PreposePlanDto getPreposePlanEntity(String id) {
                return null;
            }

            @Override
            public List<PreposePlanDto> getPostposesByPreposeId(PlanDto plan) {
                return null;
            }

            @Override
            public void deleteById(String id) {

            }

            @Override
            public FeignJson getPostPlanListByPlanId(String planId, Map<String, String> preposeMap) {
                return null;
            }

            @Override
            public List<TempPlanResourceLinkInfoDto> queryResourceChangeListOrderBy(TempPlanResourceLinkInfoDto tempPlanResourceLinkInfoDto, int page, int rows, boolean isPage) {
                return null;
            }
        };
	}
}

