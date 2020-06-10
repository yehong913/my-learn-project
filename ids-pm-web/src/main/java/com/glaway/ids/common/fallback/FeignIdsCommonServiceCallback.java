package com.glaway.ids.common.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.feign.FeignIdsCommonServiceI;
import com.glaway.ids.config.service.BusinessConfigRemoteFeignServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FeignIdsCommonServiceCallback implements FallbackFactory<FeignIdsCommonServiceI> {

	@Override
	public FeignIdsCommonServiceI create(Throwable throwable) {
		return new  FeignIdsCommonServiceI(){
            @Override
            public String getFileTypeIdByCode(String fileTypeCode) {
                return null;
            }
        };
	}
}

