package com.glaway.ids.review.service;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.review.fallback.ReviewFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: ReviewFeignServiceI
 * @Date: 2019/8/13-16:02
 * @since
 */
@FeignClient(value = FeignConstants.IDS_REVIEW_SERVICE,fallbackFactory = ReviewFeignServiceFallback.class)
public interface ReviewFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_REVIEW_FEIGN_SERVICE+"/feign/reviewSupportRestController/getReciewTypeList.do")
    FeignJson getReciewTypeList();
}
