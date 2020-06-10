package com.glaway.ids.project.plan.service;


import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.service.CommonService;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.PreposePlanDto;
import com.glaway.ids.project.plan.dto.TempPlanResourceLinkInfoDto;
import com.glaway.ids.project.plan.fallback.InputsRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.fallback.PlanRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.fallback.PreposePlanRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 任务输入
 * 
 * @author blcao
 * @version 2015年7月6日
 * @see PreposePlanRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PreposePlanRemoteFeignServiceCallBack.class)
public interface PreposePlanRemoteFeignServiceI{


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/getPreposePlansByPlanId.do")
    List<PreposePlanDto> getPreposePlansByPlanId(@RequestBody PlanDto dto);
    
    /**
     * 根据计划ID查询其前置计划
     * 
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/getPreposePlansByParent.do")
    List<PreposePlanDto> getPreposePlansByParent(@RequestBody PlanDto parent);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/getPreposePlanEntity.do")
    PreposePlanDto getPreposePlanEntity(@RequestParam("id") String id);
    
    /**
     * 根据计划ID查询其后置计划
     * 
     * @param plan
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/getPostposesByPreposeId.do")
    List<PreposePlanDto> getPostposesByPreposeId(PlanDto plan);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/deleteById.do")
    void deleteById(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/getPostPlanListByPlanId.do")
    FeignJson getPostPlanListByPlanId(@RequestParam(value = "planId",required = false) String planId, @RequestBody Map<String,String> preposeMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/preposePlanRestController/queryResourceChangeListOrderBy.do")
    List<TempPlanResourceLinkInfoDto> queryResourceChangeListOrderBy(@RequestBody TempPlanResourceLinkInfoDto tempPlanResourceLinkInfoDto,
                                                                     @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows,
                                                                     @RequestParam(value = "isPage")boolean isPage);
}
