package com.glaway.ids.project.plan.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaway.ids.project.plan.dto.PlanLogDto;
import com.glaway.ids.project.plan.form.PlanLogInfo;
import com.glaway.ids.project.plan.service.PlanLogRemoteFeignServiceI;

import feign.hystrix.FallbackFactory;

@Component
public class PlanLogRemoteFeignServiceCallBack implements FallbackFactory<PlanLogRemoteFeignServiceI> {

	@Override
	public PlanLogRemoteFeignServiceI create(Throwable throwable) {
		return new PlanLogRemoteFeignServiceI(){

            @Override
            public List<PlanLogDto> findPlanLogByPlanId(PlanLogInfo planLogInfo, int page,
                                                        int rows, boolean isPage) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void savePlanLog(PlanLogDto planLog) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public PlanLogDto getPlanLogEntity(String id) {
                // TODO Auto-generated method stub
                return null;
            }

		};
	}
}

