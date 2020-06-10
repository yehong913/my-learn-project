package com.glaway.ids.project.plan.service;


import java.util.List;

import com.glaway.ids.project.plan.dto.TempPlanResourceLinkInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.PlanHistoryDto;
import com.glaway.ids.project.plan.dto.TemporaryPlanDto;
import com.glaway.ids.project.plan.fallback.FeignPlanChangeServiceCallback;


@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = FeignPlanChangeServiceCallback.class)
public interface FeignPlanChangeServiceI {
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/initTemporaryPlanDto.do")
    String initTemporaryPlanDto(@RequestBody TemporaryPlanDto temporaryPlan);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/saveOrUpdateTemporaryPlan.do")
    String saveOrUpdateTemporaryPlan(@RequestBody TemporaryPlanDto temporaryPlan, @RequestParam(value = "curUserId") String curUserId,@RequestParam(value = "orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/getTemporaryPlanId.do")
    String getTemporaryPlanId(@RequestBody TemporaryPlanDto temporaryPlan, @RequestParam(value = "uploadSuccessPath")String uploadSuccessPath, @RequestParam(value = "uploadSuccessFileName")String uploadSuccessFileName,
                              @RequestParam(value = "userId") String userId,@RequestParam(value = "orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/startPlanChangeForWork.do")
    void startPlanChangeForWork(@RequestBody FeignJson json);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/getTemporaryPlan.do")
    TemporaryPlanDto getTemporaryPlan(@RequestParam(value = "id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/getPlanHistoryListByPlanId.do")
    List<PlanHistoryDto> getPlanHistoryListByPlanId(@RequestParam("useObjectId") String useObjectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/getTemporaryPlanListByPlanId.do")
    List<TemporaryPlanDto> getTemporaryPlanListByPlanId(@RequestParam("useObjectId") String useObjectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/queryTemporaryPlanList.do")
    List<TemporaryPlanDto> queryTemporaryPlanList(@RequestBody TemporaryPlanDto temporaryPlanDto, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows, @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planChangeRestController/queryTempPlanResourceLinkList.do")
    List<TempPlanResourceLinkInfoDto> queryTempPlanResourceLinkList(@RequestBody TempPlanResourceLinkInfoDto tempPlanResourceLinkInfo, @RequestParam(value = "page") int page,  @RequestParam(value = "rows") int rows,@RequestParam(value = "isPage") boolean isPage);
}
