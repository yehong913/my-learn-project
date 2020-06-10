package com.glaway.ids.project.planview.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.PlanViewSetConditionDto;
import com.glaway.ids.project.planview.service.PlanViewRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PlanViewRemoteFeignServiceCallBack implements FallbackFactory<PlanViewRemoteFeignServiceI> {

	@Override
	public PlanViewRemoteFeignServiceI create(Throwable throwable) {
		return new PlanViewRemoteFeignServiceI(){
			@Override
			public boolean isUpdateCondition(String planViewInfoId, String userId) {
				return false;
			}

			@Override
			public FeignJson saveSearchCondition(Map<String, Object> viewMap, String planViewInfoId, String orgId) {
				return null;
			}

			@Override
			public FeignJson publishView(String planViewInfoId, String projectIds, String userIds, String name, TSUserDto curUser, String orgId) {
				return null;
			}

			@Override
			public FeignJson saveSetConditionByDeaprtment(String departmentId, String name, TSUserDto curUser, String orgId) {
				return null;
			}

			@Override
			public FeignJson saveColumnInfo(String planViewInfoId, TSUserDto curUser, String orgId) {
				return null;
			}

			@Override
			public FeignJson updatePlanViewColumn(String planViewInfoId, String showColumnIds, TSUserDto curUser, String orgId) {
				return null;
			}

			@Override
			public FeignJson cancelPublishView(String planViewInfoId, String status) {
				return null;
			}

			@Override
			public FeignJson saveOrUpdatePlanViewInfo(PlanViewInfoDto planViewInfoDto) {
				return null;
			}

			@Override
			public List<PlanViewSetConditionDto> getSetConditionByPlanView(String planViewInfoId) {
				return null;
			}

			@Override
			public PlanViewSetConditionDto getBeanByPlanViewInfoId(String planViewInfoId) {
				return null;
			}

			@Override
			public PlanViewInfoDto getPlanViewInfoEntity(String id) {
				return null;
			}

			@Override
			public FeignJson saveAsNewView(String planViewInfoId, String planViewInfoName, TSUserDto curUser, String orgId) {
				return null;
			}

			@Override
			public List<PlanViewInfoDto> getViewList(String projectId, TSUserDto userDto) {
				return null;
			}

			@Override
			public boolean getViewCountByStatusAndName(String name, String status, String id) {
				return false;
			}

			@Override
			public List<PlanViewInfoDto> getPlanViewInfoByViewNameAndStatusAndCreateName(String planViewInfoName, String status, String createName) {
				return null;
			}

			@Override
			public FeignJson deletePlanViewInfo(String planViewInfoId) {
				return null;
			}

			@Override
			public FeignJson saveCustomView(Map<String, Object> map, String projectId, String orgId) {
				return null;
			}

			@Override
			public FeignJson saveOrUpdatePlanViewSetCondition(PlanViewSetConditionDto planViewSetConditionDto) {
				return null;
			}

			@Override
			public List<PlanViewSearchConditionDto> getSearchConditionByPlanView(String planViewInfoId) {
				return null;
			}
		};
	}
}

