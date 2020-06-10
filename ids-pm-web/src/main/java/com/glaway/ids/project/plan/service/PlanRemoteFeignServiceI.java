package com.glaway.ids.project.plan.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.fallback.PlanRemoteFeignServiceCallBack;
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


/**
 * 项目计划
 *
 * @author blcao
 * @version 2015年3月27日
 * @see PlanRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanRemoteFeignServiceCallBack.class)
public interface PlanRemoteFeignServiceI  {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/projectRestController/getCount.do")
    long getCount(@RequestBody PlanDto plan);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getUserViewProjectLinkByProjectId.do")
    UserPlanViewProjectDto getUserViewProjectLinkByProjectId(@RequestParam("projectId") String projectId, @RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/constructionPlanViewTree.do")
    List<PlanViewInfoDto> constructionPlanViewTree(@RequestParam("projectId") String projectId, @RequestParam("type") String type,@RequestParam(value = "userId")String userId,@RequestParam(value = "orgId")String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getColumnInfoListByPlanViewInfoId.do")
    List<PlanViewColumnInfoDto> getColumnInfoListByPlanViewInfoId(@RequestParam("planViewInfoId") String planViewInfoId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanViewEntity.do")
    PlanViewInfoDto getPlanViewEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getSearchConditionListByViewId.do")
    List<PlanViewSearchConditionDto> getSearchConditionListByViewId(@RequestParam("planViewInfoId") String planViewInfoId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/saveUserViewProjectLink.do")
    void saveUserViewProjectLink(@RequestParam("projectId") String projectId,@RequestParam("planViewId") String planViewId,@RequestBody TSUserDto curUser,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListForTreegridView.do")
    List<PlanDto> queryPlanListForTreegridView(@RequestBody Map<String,Object> mapList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListForTreegridWithView.do")
    List<PlanDto> queryPlanListForTreegridWithView(@RequestBody List<PlanDto> planList,@RequestParam("planViewInfoId") String planViewInfoId,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListForTreegrid.do")
    List<PlanDto> queryPlanListForTreegrid(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/initPlan.do")
    String initPlan(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanEntity.do")
    PlanDto getPlanEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getDocRelation.do")
    FeignJson getDocRelation(@RequestParam("quoteId") String quoteId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getProjDocmentVoById.do")
    ProjLibDocumentVo getProjDocmentVoById(@RequestParam("docId") String docId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getFoldIdByProjectId.do")
    String getFoldIdByProjectId(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlansExceptInvalid.do")
    List<PlanDto> queryPlansExceptInvalid(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/saveAsCreate.do")
    FeignJson saveAsCreate(@RequestParam("planStr") String planStr,@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/saveAsUpdate.do")
    void saveAsUpdate(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanAllChildren.do")
    List<PlanDto> getPlanAllChildren(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryProjectList.do")
    FeignJson queryProjectList(@RequestBody List<ConditionVO> conditionList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/checkOriginIsNullBeforeSub.do")
    FeignJson checkOriginIsNullBeforeSub(@RequestParam(value = "ids",required = false) String ids,@RequestParam(value = "useObjectType",required = false) String useObjectType);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/pdAssign.do")
    FeignJson pdAssign(@RequestBody PlanDto plan, @RequestParam(value = "id",required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getMinPlanStartTimeByProject.do")
    Date getMinPlanStartTimeByProject(@RequestBody Project t);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getMaxPlanEndTimeByProject.do")
    Date getMaxPlanEndTimeByProject(@RequestBody Project t);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/deletePlans.do")
    void deletePlans(@RequestBody List<PlanDto> list);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/judgePlanStatusByPlan.do")
    long judgePlanStatusByPlan(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getEditingPlanCount.do")
    long getEditingPlanCount(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/updatePlan.do")
    FeignJson updatePlan(@RequestBody PlanDto planDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanList.do")
    List<PlanDto> queryPlanList(@RequestBody PlanDto plan, @RequestParam(value = "page") int page, @RequestParam(value = "rows") int rows,@RequestParam(value = "isPage") boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/inputForWork.do")
    FeignJson inputForWork(@RequestBody Map<String,Object> map,@RequestParam(value = "projectIdForPlan",required = false) String projectIdForPlan,@RequestParam(value = "type",required = false) String type,
                           @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/goAssignPlanOne.do")
    FeignJson goAssignPlanOne(@RequestParam(value = "assingnId",required = false) String assignId, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanIdByLinkPlanId2.do")
    FeignJson getPlanIdByLinkPlanId2( @RequestParam(value = "planId",required = false)String planId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/taskSort.do")
    List<FlowTaskVo> taskSort(@RequestBody List<FlowTaskVo> changeFlowTaskList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getOutPower.do")
    String getOutPower(@RequestParam(value = "folderId",required = false) String folderId
            , @RequestParam(value = "planId",required = false) String planId, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getOutPowerFj.do")
    FeignJson getOutPowerFj(@RequestParam(value = "folderId",required = false) String folderId,
                            @RequestParam(value = "planId",required = false) String planId, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getApprovePlanFormEntity.do")
    ApprovePlanFormDto getApprovePlanFormEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryAssignList.do")
    List<ApprovePlanInfoDto> queryAssignList(@RequestBody ApprovePlanInfoDto approvePlanInfo, @RequestParam(value = "page",required = false) int page, @RequestParam(value = "rows",required = false) int rows,
                                             @RequestParam(value = "isPage") boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanInputsList.do")
    List<PlanDto> queryPlanInputsList(@RequestBody PlanDto plan);

    /**
     * 根据plan条件检索计划
     *
     * @param plan
     * @param page
     * @param rows
     * @param isPage
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanOrderByStarttime.do")
    List<PlanDto> queryPlanOrderByStarttime(@RequestBody PlanDto plan, @RequestParam(value = "page",required = false) int page
            , @RequestParam(value = "rows",required = false) int rows, @RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/savePlanByPlanDto.do")
    void savePlanByPlanDto(@RequestBody PlanDto toPlan);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getMaxPlanNumber.do")
    int getMaxPlanNumber(@RequestBody PlanDto parent);

    /**查询交付项名称库
     * @param ds
     * wqb 2019年6月19日 16:06:08
     */
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchDeliveryStandards.do")
    List<DeliveryStandardDto> searchDeliveryStandards(@RequestBody DeliveryStandardDto ds);

    /**
     * 精确查找
     * @param ns
     * wqb 2019年7月5日 13:43:13
     */
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/nameStandardRestController/searchNameStandardsAccurate.do")
    List<NameStandardDto> searchNameStandardsAccurate(@RequestBody NameStandardDto ns);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/startAssignProcess.do")
    FeignJson startAssignProcess(@RequestBody Map<String,String> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/startAssignProcess.do")
    FeignJson searchDatagrid(@RequestBody FeignJson json);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryEntity.do")
    PageList queryEntity(@RequestBody Map<String, Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryEntityForSelectedPlan.do")
    PageList queryEntityForSelectedPlan(@RequestBody Map<String, Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/updateOpContentByPlanId.do")
    void updateOpContentByPlanId(@RequestBody TSUserDto usetDto,@RequestParam(value = "planId",required = false) String planId, @RequestParam(value = "opContent",required = false) String opContent);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/discardPlans.do")
    void discardPlans(@RequestParam(value = "id") String id,@RequestParam(value = "userId")String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/assignListViewTree.do")
    FeignJson assignListViewTree(@RequestBody PlanDto dto, @RequestParam(value = "formId",required = false) String formId,
                                 @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/startPlanFlow.do")
    FeignJson startPlanFlow(@RequestBody Map<String,String> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/goBasicCheck.do")
    Map<String,Object> goBasicCheck(@RequestBody PlanDto dto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanLifeStatus.do")
    FeignJson getPlanLifeStatus();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getOutPowerForPlanList.do")
    String getOutPowerForPlanList(@RequestParam(value = "folderId") String folderId, @RequestParam(value = "planId") String planId,@RequestParam(value = "userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getTaskNameType.do")
    FeignJson getTaskNameType(@RequestParam(value = "planName",required = false) String planName);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/changePlansToJSONObjects.do")
    List<JSONObject> changePlansToJSONObjects(@RequestBody List<PlanDto> planList);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanIdAndNameMap.do")
    FeignJson queryPlanIdAndNameMap(@RequestParam(value = "parentPlanId",required = false) String parentPlanId);


    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListForCustomViewTreegrid.do")
    List<PlanDto> queryPlanListForCustomViewTreegrid(@RequestBody PlanDto planDto,@RequestParam("planIds") String planIds);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanownerApplychangeInfo.do")
    PlanownerApplychangeInfoDto getPlanownerApplychangeInfo(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getOutplanPreposesForSearch.do")
    List<PlanDto> getOutplanPreposesForSearch(@RequestBody  List<ConditionVO> conditionList, @RequestParam(value = "projectId") String projectId, @RequestParam(value = "userName") String userName,
                                              @RequestParam(value = "progressRate") String progressRate, @RequestParam(value = "workTime") String workTime);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListExceptEditing.do")
    List<PlanDto> queryPlanListExceptEditing(@RequestBody PlanDto plan, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows, @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/orderReviewTask.do")
    FeignJson orderReviewTask(@RequestBody PlanDto dto,@RequestParam(value = "orderReviewTaskType",required = false) String orderReviewTaskType,
                              @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListForTemplateTreegrid.do")
    List<PlanDto> queryPlanListForTemplateTreegrid(@RequestBody PlanDto planDto);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getDetailInputsList.do")
    Map<String, List<InputsDto>> getDetailInputsList(@RequestBody PlanDto planDto);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getDeliverableListMap.do")
    Map<String,List<DeliverablesInfoDto>> getDeliverableListMap();

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanInfoList.do")
    List<PlanDto> getPlanInfoList(@RequestBody PlanDto p);
    
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanIdByFlowTaskParentId.do")
    FeignJson getPlanIdByFlowTaskParentId(@RequestParam(value = "parentPlanId") String parentPlanId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/deleteDeliverablesByPlanId.do")
    void deleteDeliverablesByPlanId(@RequestParam(value = "planId",required = false) String planId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getTemporaryPlanEntity.do")
    TemporaryPlanDto getTemporaryPlanEntity(@RequestParam(value = "id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/exportXls.do")
    List<PlanDto> exportXls(@RequestBody PlanDto plan, @RequestParam(value = "projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/exportXlsSingle.do")
    List<PlanDto> exportXlsSingle(@RequestBody PlanDto plan);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/savePlanByPlanTemplateId.do")
    FeignJson savePlanByPlanTemplateId(@RequestBody PlanTemplateDetailReq planTemplateDetailReq,
                                       @RequestParam(value = "planId",required = false) String planId,@RequestParam(value = "type",required = false) String type,
                                       @RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanAllChildrenByProjectId.do")
    List<PlanDto> getPlanAllChildrenByProjectId(@RequestParam(value = "projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/saveMppInfo.do")
    List<MppInfo> saveMppInfo(@RequestBody List<PlanDto> plans);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getBusinessConfigEntity.do")
    BusinessConfigDto getBusinessConfigEntity(@RequestParam(value = "id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/doImportExcel.do")
    FeignJson doImportExcel(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "projectId",required = false) String projectId,@RequestParam(value = "type",required = false) String type,
                         @RequestParam(value = "planId",required = false) String planId,@RequestBody List<Map<String,String>> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryInputChangeList.do")
    List<TempPlanInputsDto> queryInputChangeList(@RequestBody TempPlanInputsDto deliverablesInfo, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows,
                                                 @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryDeliverableChangeList.do")
    List<TempPlanDeliverablesInfoDto> queryDeliverableChangeList(@RequestBody TempPlanDeliverablesInfoDto tempPlanInputsDto, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows,
                                                                 @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryResourceChangeList.do")
    List<TempPlanResourceLinkInfoDto> queryResourceChangeList(@RequestBody TempPlanResourceLinkInfoDto tempPlanInputsDto, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows,
                                                              @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getTaskNumberByPlan.do")
    FeignJson getTaskNumberByPlan(@RequestParam(value = "taskNumber",required = false) String taskNumber,
                                  @RequestParam(value = "type",required = false) String type);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/concernPlan.do")
    FeignJson concernPlan(@RequestParam(value = "planId",required = false) String planId, @RequestParam(value = "concernCode",required = false) String concernCode,
                          @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/continuePlanReceiveProcess.do")
    FeignJson continuePlanReceiveProcess(@RequestParam(value = "planId",required = false) String planId,@RequestParam(value = "flag",required = false) String flag,@RequestBody TSUserDto curUser);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/refusePlanReceiveProcess.do")
    FeignJson refusePlanReceiveProcess(@RequestParam(value = "planId",required = false) String planId,@RequestBody TSUserDto curUser,
                                       @RequestParam(value = "refuseReason",required = false) String refuseReason,@RequestParam(value = "refuseRemark",required = false) String refuseRemark,
                                       @RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getPlanRefuseInfoEntity.do")
    PlanRefuseInfoDto getPlanRefuseInfoEntity(@RequestParam(value = "refuseId",required = false) String refuseId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/delegatePlanReceiveProcess.do")
    FeignJson delegatePlanReceiveProcess(@RequestParam(value = "planId",required = false) String planId,@RequestBody TSUserDto curUser,
                                         @RequestParam(value = "delegateUserId",required = false) String delegateUserId,@RequestParam(value = "changeType",required = false) String changeType,
                                         @RequestParam(value = "leaderId",required = false) String leaderId,@RequestParam(value = "departLeaderId",required = false) String departLeaderId,
                                         @RequestParam(value = "changeRemark",required = false) String changeRemark,@RequestParam(value = "flag",required = false) String flag,
                                         @RequestParam(value = "orgId",required = false) String orgId);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/doSaveBom.do")
    FeignJson doSaveBom(@RequestParam(value = "planId",required = false) String planId,@RequestBody TSUserDto curUser,
                                         @RequestParam(value = "bomId",required = false) String bomId,@RequestParam(value = "projectId",required = false) String projectId,
                                         @RequestParam(value = "code",required = false) String code,@RequestParam(value = "name",required = false) String name,
                                         @RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryEntityForPrepose.do")
    PageList queryEntityForPrepose(@RequestBody List<ConditionVO> conditionList, @RequestParam(value = "projectId",required = false) String projectId,
                                   @RequestParam(value = "userName",required = false)  String userName,
                                   @RequestParam(value = "progressRate",required = false) String progressRate, @RequestParam(value = "workTime",required = false)  String workTime);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/checkisParentChildAllPublish.do")
    FeignJson checkisParentChildAllPublish(@RequestParam(value = "id", required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/startPlanDelegateProcess.do")
    FeignJson startPlanDelegateProcess(@RequestParam(value = "planId",required = false) String planId,@RequestBody TSUserDto curUser,
                                       @RequestParam(value = "delegateUserId",required = false) String delegateUserId,@RequestParam(value = "changeType",required = false) String changeType,
                                       @RequestParam(value = "leaderId",required = false) String leaderId,@RequestParam(value = "departLeaderId",required = false) String departLeaderId,
                                       @RequestParam(value = "changeRemark",required = false) String changeRemark, @RequestParam(value = "orgId",required = false) String orgId);

    /**获取当前项目下所有计划的生命周期状态数量
     * @param projId 项目ID
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getAllPlanLifeCycleArrayByProjId.do")
    FeignJson getAllPlanLifeCycleArrayByProjId(@RequestParam(value = "projId",required = false) String projId);

    /**获取当前项目下所有计划完成情况
     * @param projId 项目ID
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getAllPlanCompletionByProjId.do")
    FeignJson getAllPlanCompletionByProjId(@RequestParam(value = "projId", required = false) String projId);

    /**获取当前项目下所有计划执行情况
     * @param projId 项目ID
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getAllPlanExecutByProjId.do")
    FeignJson getAllPlanExecutByProjId(@RequestParam(value = "projId", required = false) String projId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/allPreposePlanList.do")
    List<PreposePlanDto> allPreposePlanList(@RequestParam(value = "projId", required = false) String projId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/queryPlanListByMap.do")
    List<PlanDto> queryPlanListByMap(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/planSeqApproveBtnCheck.do")
    FeignJson planSeqApproveBtnCheck(@RequestParam(value = "planId", required = false) String planId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getParentProcess.do")
    FeignJson getParentProcess(@RequestParam(value = "planId", required = false) String planId);
}
