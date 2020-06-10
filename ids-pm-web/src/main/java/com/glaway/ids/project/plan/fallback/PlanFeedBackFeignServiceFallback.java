package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.PlanFeedBackDto;
import com.glaway.ids.project.plan.service.PlanFeedBackFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: PlanFeedBackFeignServiceFallback
 * @Date: 2019/10/16-14:18
 * @since
 */
@Component
public class PlanFeedBackFeignServiceFallback implements FallbackFactory<PlanFeedBackFeignServiceI> {
    @Override
    public PlanFeedBackFeignServiceI create(Throwable throwable) {
        return new PlanFeedBackFeignServiceI() {
            @Override
            public FeignJson queryData() {
                return new FeignJson();
            }

            @Override
            public FeignJson saveFeedBack(Map<String, String> params, String userId) {
                return new FeignJson();
            }

            @Override
            public FeignJson calculateWeight(Map<String, Object> map) {
                return new FeignJson();
            }

            @Override
            public FeignJson calculateWeightByStatus(String status) {
                return new FeignJson();
            }
        };
    }
}
