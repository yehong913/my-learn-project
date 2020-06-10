package com.glaway.ids.project.plan.service;


import java.util.List;

import com.glaway.ids.common.constant.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.ids.project.plan.dto.PlanLogDto;
import com.glaway.ids.project.plan.fallback.PlanLogRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.form.PlanLogInfo;


/**
 * 项目计划日志
 * 
 * @author wqb
 * @version 2019年8月2日 14:22:07
 * @see PlanLogRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanLogRemoteFeignServiceCallBack.class)
public interface PlanLogRemoteFeignServiceI  {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planLogRestController/findPlanLogByPlanId.do")
    List<PlanLogDto> findPlanLogByPlanId(@RequestBody PlanLogInfo planLogInfo,@RequestParam(value = "page",required = false) int page
                                         ,@RequestParam(value = "rows",required = false) int rows, @RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planLogRestController/savePlanLog.do")
    void savePlanLog(@RequestBody PlanLogDto planLog);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planLogRestController/getPlanLogEntity.do")
    PlanLogDto getPlanLogEntity(@RequestParam(value = "id",required = false) String id);
}
