package com.glaway.ids.project.plan.fallback;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.BasicLineDto;
import com.glaway.ids.project.plan.dto.BasicLinePlanDto;
import com.glaway.ids.project.plan.dto.PlanHistoryDto;
import com.glaway.ids.project.plan.dto.TemporaryPlanDto;
import com.glaway.ids.project.plan.service.FeignBasicLineServiceI;
import com.glaway.ids.project.plan.service.FeignPlanChangeServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeignBasicLineServiceCallback implements FallbackFactory<FeignBasicLineServiceI> {

	@Override
	public FeignBasicLineServiceI create(Throwable throwable) {
		return new FeignBasicLineServiceI(){

            @Override
            public String getLifeCycleStatusList() {
                return null;
            }

            @Override
            public BasicLineDto getBasicLineEntity(String id) {
                return null;
            }

            @Override
            public BasicLinePlanDto getBasicLinePlanEntity(String id) {
                return null;
            }

            @Override
            public List<BasicLinePlanDto> queryBasicLinePlanList(BasicLinePlanDto basicLinePlan, int page, int rows, boolean isPage) {
                return null;
            }

            @Override
            public List<JSONObject> changePlansToJSONObjects(List<BasicLinePlanDto> validList) {
                return null;
            }

            @Override
            public FeignJson saveBasicLine(BasicLineDto basicLine, String ids, String basicLineName, String remark, String type, String projectId) {
                return null;
            }

            @Override
            public void deleteBasicLine(BasicLineDto basicLine) {

            }

            @Override
            public FeignJson doFrozeBasicLine(String id) {
                return null;
            }

            @Override
            public FeignJson doUseBasicLine(String id) {
                return null;
            }

            @Override
            public FeignJson startBasicLine(String basicLineId, String leader, String deptLeader, String userId) {
                return null;
            }

            @Override
            public FeignJson startBasicLineFlow(BasicLineDto basicLine, String basicLineId, String taskId, String basicLineName, String remark) {
                return null;
            }

            @Override
            public FeignJson searchDatagrid(List<ConditionVO> conditionList, String projectId, String userName) {
                return null;
            }
        };
	}
}

