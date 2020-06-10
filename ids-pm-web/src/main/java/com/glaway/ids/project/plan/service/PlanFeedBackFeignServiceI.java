package com.glaway.ids.project.plan.service;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.PlanFeedBackDto;
import com.glaway.ids.project.plan.fallback.PlanFeedBackFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: sunmeng
 * @ClassName: PlanFeedBackFeignServiceI
 * @Date: 2019/10/16-14:16
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanFeedBackFeignServiceFallback.class)
public interface PlanFeedBackFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFeedBackRestController/queryData.do")
    FeignJson queryData();

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFeedBackRestController/saveFeedBack.do")
    FeignJson saveFeedBack(@RequestBody Map<String,String> params, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFeedBackRestController/calculateWeight.do")
    FeignJson calculateWeight(@RequestBody Map<String,Object> map);

    /**
     * 根据相应的生命周期状态获取权重比
     * @param status
     * @return
     */
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFeedBackRestController/calculateWeightByStatus.do")
    FeignJson calculateWeightByStatus(@RequestParam(value = "status",required = false) String status);

}
