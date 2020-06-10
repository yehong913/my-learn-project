package com.glaway.ids.review.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.review.service.ReviewFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: ReviewFeignServiceFallback
 * @Date: 2019/8/13-16:02
 * @since
 */
@Component
public class ReviewFeignServiceFallback implements FallbackFactory<ReviewFeignServiceI> {
    @Override
    public ReviewFeignServiceI create(Throwable throwable) {
        return new ReviewFeignServiceI() {
            @Override
            public FeignJson getReciewTypeList() {
                return new FeignJson();
            }
        };
    }
}
