package com.glaway.ids.project.plan.fallback;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.glaway.ids.project.plan.dto.*;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo.PlanTemplateDetailReq;
import com.glaway.ids.project.planview.dto.PlanViewColumnInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.UserPlanViewProjectDto;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskVo;
import com.glaway.ids.util.mpputil.MppInfo;

import feign.hystrix.FallbackFactory;

@Component
public class PlanRemoteFeignServiceCallBack implements FallbackFactory<PlanRemoteFeignServiceI> {

	@Override
	public PlanRemoteFeignServiceI create(Throwable throwable) {
		return new PlanRemoteFeignServiceI(){

			@Override
			public long getCount(PlanDto plan) {
				return 0;
			}

			@Override
			public UserPlanViewProjectDto getUserViewProjectLinkByProjectId(String projectId, String userId) {
				return null;
			}

			@Override
			public List<PlanViewInfoDto> constructionPlanViewTree(String projectId, String type, String userId, String orgId) {
				return null;
			}

			@Override
			public List<PlanViewColumnInfoDto> getColumnInfoListByPlanViewInfoId(String planViewInfoId) {
				return null;
			}

			@Override
			public PlanViewInfoDto getPlanViewEntity(String id) {
				return null;
			}

			@Override
			public List<PlanViewSearchConditionDto> getSearchConditionListByViewId(String planViewInfoId) {
				return null;
			}

			@Override
			public void saveUserViewProjectLink(String projectId, String planViewId, TSUserDto curUser, String orgId) {

			}

			@Override
			public PlanRefuseInfoDto getPlanRefuseInfoEntity(String refuseId) {
				return null;
			}

			@Override
			public FeignJson savePlanByPlanTemplateId(PlanTemplateDetailReq planTemplateDetailReq, String planId, String type, String userId, String orgId) {
				return null;
			}

			@Override
			public List<PlanDto> getPlanAllChildrenByProjectId(String projectId) {
				return null;
			}

			@Override
			public List<MppInfo> saveMppInfo(List<PlanDto> plans) {
				return null;
			}

			@Override
			public BusinessConfigDto getBusinessConfigEntity(String id) {
				return null;
			}

			@Override public FeignJson doImportExcel(String userId, String projectId, String type, String planId,
													 List<Map<String,String>> map) {
				return null;
			}

			@Override
            public List<TempPlanInputsDto> queryInputChangeList(TempPlanInputsDto deliverablesInfo, int page, int rows, boolean isPage) {
                return null;
            }

			@Override
			public List<TempPlanDeliverablesInfoDto> queryDeliverableChangeList(TempPlanDeliverablesInfoDto tempPlanInputsDto, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public List<TempPlanResourceLinkInfoDto> queryResourceChangeList(TempPlanResourceLinkInfoDto tempPlanInputsDto, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public FeignJson continuePlanReceiveProcess(String planId, String flag, TSUserDto curUser) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListForTemplateTreegrid(PlanDto planDto) {
				return null;
			}

			@Override
			public Map<String, List<InputsDto>> getDetailInputsList(PlanDto planDto) {
				return null;
			}

			@Override
			public Map<String, List<DeliverablesInfoDto>> getDeliverableListMap() {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListForTreegridView(Map<String, Object> mapList) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListForTreegridWithView(List<PlanDto> planList, String planViewInfoId, String userId) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListForTreegrid(PlanDto planDto) {
				return null;
			}

			@Override
			public String initPlan(PlanDto planDto) {
				return null;
			}

			@Override
			public FeignJson getLifeCycleStatusList() {
				return null;
			}

			@Override
			public PlanDto getPlanEntity(String id) {
				return null;
			}

			@Override
			public FeignJson getDocRelation(String quoteId) {
				return null;
			}

			@Override
			public ProjLibDocumentVo getProjDocmentVoById(String docId) {
				return null;
			}

			@Override
			public String getFoldIdByProjectId(String projectId) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlansExceptInvalid(PlanDto planDto) {
				return null;
			}

			@Override
			public FeignJson saveAsCreate(String planStr, Map<String, Object> map) {
				return null;
			}

			@Override
			public void saveAsUpdate(Map<String, Object> map) {

			}

			@Override
			public List<PlanDto> queryPlanListByMap(Map<String, Object> map) {
				return null;
			}

			@Override
			public FeignJson delegatePlanReceiveProcess(String planId, TSUserDto curUser, String delegateUserId, String changeType, String leaderId, String departLeaderId, String changeRemark, String flag, String orgId) {
				return null;
			}

			@Override
			public FeignJson startPlanDelegateProcess(String planId, TSUserDto curUser, String delegateUserId, String changeType, String leaderId, String departLeaderId, String changeRemark, String orgId) {
				return null;
			}

			@Override
			public FeignJson getAllPlanLifeCycleArrayByProjId(String projId) {
				return null;
			}

			@Override
			public FeignJson getAllPlanCompletionByProjId(String projId) {
				return null;
			}

			@Override
			public FeignJson getAllPlanExecutByProjId(String projId) {
				return null;
			}

			@Override public List<PreposePlanDto> allPreposePlanList(String projId) {
				return null;
			}

			@Override
			public FeignJson doSaveBom(String planId, TSUserDto curUser, String bomId,
												 String projectId, String code, String name,
												 String orgId) {
				return null;
			}

			@Override
			public PageList queryEntityForPrepose(List<ConditionVO> conditionList, String projectId, String userName, String progressRate, String workTime) {
				return null;
			}

			@Override
			public FeignJson refusePlanReceiveProcess(String planId, TSUserDto curUser, String refuseReason, String refuseRemark, String orgId) {
				return null;
			}

			@Override
			public List<PlanDto> getPlanAllChildren(PlanDto planDto) {
				return null;
			}

			@Override
			public FeignJson queryProjectList(List<ConditionVO> conditionList) {
				return null;
			}

			@Override
			public FeignJson checkOriginIsNullBeforeSub(String ids, String useObjectType) {
				return null;
			}

			@Override
			public FeignJson pdAssign(PlanDto plan, String id) {
				return null;
			}

			@Override
			public Date getMinPlanStartTimeByProject(Project t) {
				return null;
			}

			@Override
			public Date getMaxPlanEndTimeByProject(Project t) {
				return null;
			}

			@Override
			public void deletePlans(List<PlanDto> list) {

			}

			@Override
			public long judgePlanStatusByPlan(PlanDto planDto) {
				return 0;
			}

			@Override
			public long getEditingPlanCount(PlanDto planDto) {
				return 0;
			}

			@Override
			public FeignJson updatePlan(PlanDto planDto) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanList(PlanDto plan, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public FeignJson inputForWork(Map<String, Object> map, String projectIdForPlan, String type, String userId) {
				return null;
			}

			@Override
			public FeignJson goAssignPlanOne(String assignId, String userId) {
				return null;
			}

			@Override
			public FeignJson getPlanIdByLinkPlanId2(String planId) {
				return null;
			}

			@Override
			public List<FlowTaskVo> taskSort(List<FlowTaskVo> changeFlowTaskList) {
				return null;
			}

			@Override
			public String getOutPower(String folderId, String planId, String userId) {
				return null;
			}

			@Override
			public FeignJson getOutPowerFj(String folderId, String planId, String userId) {
				return null;
			}

			@Override
			public ApprovePlanFormDto getApprovePlanFormEntity(String id) {
				return null;
			}

			@Override
			public List<ApprovePlanInfoDto> queryAssignList(ApprovePlanInfoDto approvePlanInfo, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanInputsList(PlanDto plan) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanOrderByStarttime(PlanDto plan, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public void savePlanByPlanDto(PlanDto toPlan) {

			}

			@Override
			public int getMaxPlanNumber(PlanDto parent) {
				return 0;
			}

			@Override
			public List<DeliveryStandardDto> searchDeliveryStandards(DeliveryStandardDto ds) {
				return null;
			}

			@Override
			public List<NameStandardDto> searchNameStandardsAccurate(NameStandardDto ns) {
				return null;
			}

			@Override
			public FeignJson startAssignProcess(Map<String, String> params) {
				return null;
			}

			@Override
			public FeignJson searchDatagrid(FeignJson json) {
				return null;
			}

			@Override
			public PageList queryEntity(Map<String, Object> map) {
				return null;
			}

			@Override
			public PageList queryEntityForSelectedPlan(Map<String, Object> map) {
				return null;
			}

			@Override
			public void updateOpContentByPlanId(TSUserDto usetDto, String planId, String opContent) {

			}

			@Override
			public void discardPlans(String id, String userId) {

			}

			@Override
			public FeignJson assignListViewTree(PlanDto dto, String formId, String userId) {
				return null;
			}

			@Override
			public FeignJson startPlanFlow(Map<String, String> map) {
				return null;
			}

			@Override
			public Map<String, Object> goBasicCheck(PlanDto dto) {
				return null;
			}

			@Override
			public FeignJson getPlanLifeStatus() {
				return null;
			}

			@Override
			public String getOutPowerForPlanList(String folderId, String planId, String userId) {
				return null;
			}

			@Override
			public FeignJson getTaskNameType(String planName) {
				return null;
			}

			@Override
			public List<JSONObject> changePlansToJSONObjects(List<PlanDto> planList) {
				return null;
			}

			@Override
			public FeignJson queryPlanIdAndNameMap(String parentPlanId) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListForCustomViewTreegrid(PlanDto planDto, String planIds) {
				return null;
			}

			@Override
			public PlanownerApplychangeInfoDto getPlanownerApplychangeInfo(String id) {
				return null;
			}

			@Override
			public List<PlanDto> getOutplanPreposesForSearch(List<ConditionVO> conditionList, String projectId, String userName, String progressRate, String workTime) {
				return null;
			}

			@Override
			public List<PlanDto> queryPlanListExceptEditing(PlanDto plan, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public FeignJson orderReviewTask(PlanDto dto, String orderReviewTaskType, String userId) {
				return new FeignJson();
			}

            @Override
            public List<PlanDto> getPlanInfoList(PlanDto p) {
                return null;
            }
            
            @Override
            public FeignJson getPlanIdByFlowTaskParentId(String parentPlanId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void deleteDeliverablesByPlanId(String planId) {
                // TODO Auto-generated method stub
                
            }

			@Override
			public TemporaryPlanDto getTemporaryPlanEntity(String id) {
				return null;
			}

            @Override
            public List<PlanDto> exportXls(PlanDto plan, String projectId) {
                return null;
            }

            @Override
            public List<PlanDto> exportXlsSingle(PlanDto plan) {
                return null;
            }

			@Override
			public FeignJson getTaskNumberByPlan(String taskNumber, String type) {
				return new FeignJson();
			}

			@Override public FeignJson concernPlan(String id, String concernCode,String userId) {
				return null;
			}

            @Override
            public FeignJson checkisParentChildAllPublish(String id) {
                // TODO Auto-generated method stub
                return null;
            }

			@Override
			public FeignJson planSeqApproveBtnCheck(String planId) {
				return new FeignJson();
			}

			@Override
			public FeignJson getParentProcess(String planId) {
				return new FeignJson();
			}
		};

	}
}

