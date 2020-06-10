package com.glaway.ids.qualityTest.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.qualityTest.dto.CofigFormTestDto;
import com.glaway.ids.qualityTest.feign.QualityTestFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 熔断
 * @author: sunmeng
 * @ClassName: QualityTestFeignServiceFallBack
 * @Date: 2019/10/30-15:30
 * @since
 */
@Component
public class QualityTestFeignServiceFallBack implements FallbackFactory<QualityTestFeignServiceI> {
    @Override
    public QualityTestFeignServiceI create(Throwable throwable) {
        return new QualityTestFeignServiceI() {
            @Override
            public FeignJson addQualityDataGrid(String useObjectId) {
                return new FeignJson();
            }

            @Override
            public FeignJson searchDataGrid(String useObjectId) {
                return new FeignJson();
            }

            @Override
            public FeignJson saveQualityDataGrid(String planId, String useObjectId) {
                return new FeignJson();
            }

            @Override
            public FeignJson saveFormTest(CofigFormTestDto dto) {
                return new FeignJson();
            }

            @Override
            public FeignJson getFormTestByPlanId(String planId) {
                return new FeignJson();
            }

            @Override
            public FeignJson searchList(String planId) {
                return new FeignJson();
            }

            @Override
            public FeignJson updateFormTest(CofigFormTestDto dto) {
                return new FeignJson();
            }
        };
    }
}
