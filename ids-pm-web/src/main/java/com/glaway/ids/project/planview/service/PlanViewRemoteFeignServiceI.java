package com.glaway.ids.project.planview.service;

import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.PlanViewSetConditionDto;
import com.glaway.ids.project.planview.fallback.PlanViewRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 视图服务接口
 * @author likaiyong
 * @version 2018年5月31日
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanViewRemoteFeignServiceCallBack.class)
public interface PlanViewRemoteFeignServiceI{


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/isUpdateCondition.do")
    boolean isUpdateCondition(@RequestParam("planViewInfoId") String planViewInfoId,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getSearchConditionByPlanView.do")
    List<PlanViewSearchConditionDto> getSearchConditionByPlanView(@RequestParam("planViewInfoId") String planViewInfoId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveSearchCondition.do")
    FeignJson saveSearchCondition(@RequestBody Map<String,Object> viewMap, @RequestParam("planViewInfoId") String planViewInfoId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/updatePlanViewColumn.do")
    FeignJson updatePlanViewColumn(@RequestParam("planViewInfoId") String planViewInfoId, @RequestParam("showColumnIds") String showColumnIds, @RequestBody TSUserDto curUser, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getPlanViewInfoByViewNameAndStatusAndCreateName.do")
    List<PlanViewInfoDto> getPlanViewInfoByViewNameAndStatusAndCreateName(@RequestParam("planViewInfoName") String planViewInfoName,@RequestParam("status") String status,@RequestParam("createName") String createName);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveAsNewView.do")
    FeignJson saveAsNewView(@RequestParam("planViewInfoId") String planViewInfoId,@RequestParam("planViewInfoName") String planViewInfoName,@RequestBody TSUserDto curUser, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getViewCountByStatusAndName.do")
    boolean getViewCountByStatusAndName(@RequestParam("name") String name, @RequestParam("status") String status, @RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveSetConditionByDeaprtment.do")
    FeignJson saveSetConditionByDeaprtment(@RequestParam("departmentId") String departmentId, @RequestParam("name") String name,@RequestBody TSUserDto curUser, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveColumnInfo.do")
    FeignJson saveColumnInfo(@RequestParam("planViewInfoId") String planViewInfoId,@RequestBody TSUserDto curUser, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getPlanViewInfoEntity.do")
    PlanViewInfoDto getPlanViewInfoEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getBeanByPlanViewInfoId.do")
    PlanViewSetConditionDto getBeanByPlanViewInfoId(@RequestParam("planViewInfoId") String planViewInfoId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveOrUpdatePlanViewInfo.do")
    FeignJson saveOrUpdatePlanViewInfo(@RequestBody PlanViewInfoDto planViewInfoDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveOrUpdatePlanViewSetCondition.do")
    FeignJson saveOrUpdatePlanViewSetCondition(@RequestBody PlanViewSetConditionDto planViewSetConditionDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/saveCustomView.do")
    FeignJson saveCustomView(@RequestBody Map<String,Object> map,@RequestParam("projectId") String projectId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getViewList.do")
    List<PlanViewInfoDto> getViewList(@RequestParam("projectId") String projectId,@RequestBody TSUserDto userDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/getSetConditionByPlanView.do")
    List<PlanViewSetConditionDto> getSetConditionByPlanView(@RequestParam("planViewInfoId") String planViewInfoId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/publishView.do")
    FeignJson publishView(@RequestParam("planViewInfoId") String planViewInfoId, @RequestParam("projectIds") String projectIds,
                          @RequestParam("userIds") String userIds, @RequestParam("name") String name,@RequestBody TSUserDto curUser, @RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/cancelPublishView.do")
    FeignJson cancelPublishView(@RequestParam("planViewInfoId") String planViewInfoId, @RequestParam("status") String status);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planViewRestController/deletePlanViewInfo.do")
    FeignJson deletePlanViewInfo(@RequestParam("planViewInfoId") String planViewInfoId);

}


