package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.FeignPlanChangeServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FeignPlanChangeServiceCallback implements FallbackFactory<FeignPlanChangeServiceI> {

	@Override
	public FeignPlanChangeServiceI create(Throwable throwable) {
		return new FeignPlanChangeServiceI(){

            @Override
            public String initTemporaryPlanDto(TemporaryPlanDto temporaryPlan) {
                return null;
            }

            @Override
            public String saveOrUpdateTemporaryPlan(TemporaryPlanDto temporaryPlan, String curUserId, String orgId) {
                return null;
            }

            @Override
            public String getTemporaryPlanId(TemporaryPlanDto temporaryPlan, String uploadSuccessPath, String uploadSuccessFileName, String userId, String orgId) {
                return null;
            }

            @Override
            public void startPlanChangeForWork(FeignJson json) {

            }

            @Override
            public List<TempPlanResourceLinkInfoDto> queryTempPlanResourceLinkList(TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo, int page, int rows, boolean isPage) {
                return null;
            }

            @Override
            public TemporaryPlanDto getTemporaryPlan(String id) {
                return null;
            }

            @Override
            public List<PlanHistoryDto> getPlanHistoryListByPlanId(String useObjectId) {
                return null;
            }

            @Override
            public List<TemporaryPlanDto> getTemporaryPlanListByPlanId(String useObjectId) {
                return null;
            }

            @Override
            public List<TemporaryPlanDto> queryTemporaryPlanList(TemporaryPlanDto temporaryPlanDto, int page, int rows, boolean isPage) {
                return null;
            }
        };
	}
}

