package com.glaway.ids.project.plantemplate.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plantemplate.dto.PlanTempOptLogDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.service.PlanTemplateRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.PlanViewSetConditionDto;
import com.glaway.ids.project.planview.service.PlanViewRemoteFeignServiceI;
import com.glaway.ids.util.mpputil.MppInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PlanTemplateRemoteFeignServiceCallBack implements FallbackFactory<PlanTemplateRemoteFeignServiceI> {

	@Override
	public PlanTemplateRemoteFeignServiceI create(Throwable throwable) {
		return new PlanTemplateRemoteFeignServiceI(){

			@Override
			public FeignJson getLifeCycleStatusList() {
				return null;
			}

			@Override
			public FeignJson doStatusChange(PlanTemplateReq planTemplateReq, String planTemplateId, String curUserId, String orgId) {
				return null;
			}

			@Override
			public boolean checkTemplateNameBeforeSave(String templateName, String planTemplateId) {
				return false;
			}

			@Override
			public FeignJson savePlanTemplate(String templateName, String planTemplateId, String remark, String type, String curUserId, String orgId, List<PlanDto> planList) {
				return null;
			}

			@Override
			public FeignJson completePlanTemplateProcess(String taskId, PlanTemplateDto planTemplateDto, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson doDeletePlanTemplate(String ids, String planTemplateId, String projTemplateId) {
				return null;
			}

			@Override
			public FeignJson deletePlanTemplate(PlanTemplateReq planTemplateReq, String userId) {
				return null;
			}

			@Override
			public FeignJson updatePlanTemplate(PlanTemplateReq planTemplateReq, String userId) {
				return null;
			}

			@Override
			public FeignJson savePlanTemplateDetail(Map<String, Object> paramMap) {
				return null;
			}

			@Override
			public List<MppInfo> saveMppInfo(PlanTemplateReq planTemplateReq) {
				return null;
			}

			@Override
			public FeignJson checkData(Map<String, Object> paramMap) {
				return null;
			}

			@Override
			public FeignJson checkData2(Map<String, Object> map) {
				return null;
			}

			@Override
			public FeignJson copyTemplate(String oldTemplId, String newTemplName, String remark, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public FeignJson getImportDataList(List<Map<String, String>> map, String userId, String planTemplateId, String orgId) {
				return null;
			}

			@Override
			public FeignJson getVersionHistoryAndCount(String bizId, Integer pageSize, Integer pageNum) {
				return null;
			}

			@Override
			public List<PlanTempOptLogDto> findPlanTempOptLogById(String bizId) {
				return null;
			}

			@Override
			public FeignJson savePlanTemplateByPlanject(String projectId, String planId, PlanTemplateDto planTemplate, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson importPlanTemplateMpp(Map<String, Object> map) {
				return null;
			}

			@Override
			public PlanTemplateDto findBusinessObjectById(String planTemplateId) {
				return null;
			}

			@Override
			public FeignJson backVesion(PlanTemplateDto planTemplateDto, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson revokeVesion(PlanTemplateDto planTemplateDto, String userId, String orgId) {
				return null;
			}

			@Override
			public FeignJson saveTemplateOptLog(String bizId, String logInfo, TSUserDto userDto, String orgId) {
				return null;
			}

			@Override
			public FeignJson startPlanTemplateProcess(Map<String, Object> paramMap) {
				return null;
			}

			@Override
			public FeignJson savePlanTemplateDetailByExcel(Map<String, Object> map) {
				return null;
			}

			@Override
			public PlanTemplateDto getPlanTemplateEntity(String id) {
				return null;
			}

			@Override
			public List<PlanTemplateDetailDto> getTemplatePlanDetailById(String planTemplateId) {
				return null;
			}

			@Override
			public Map<String, List<InputsDto>> queryInputsListMap() {
				return null;
			}

			@Override
			public PageList queryEntity(Map<String, Object> map) {
				return null;
			}
		};
	}
}

