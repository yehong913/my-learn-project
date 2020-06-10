package com.glaway.ids.config.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.config.dto.ParamSwitchDto;
import com.glaway.ids.config.service.ParamSwitchRemoteFeignServiceI;
import com.glaway.ids.project.projecttemplate.dto.ProjTemplateDto;
import com.glaway.ids.project.projecttemplate.service.ProjTemplateRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ParamSwitchRemoteFeignServiceCallBack implements FallbackFactory<ParamSwitchRemoteFeignServiceI> {

	@Override
	public ParamSwitchRemoteFeignServiceI create(Throwable throwable) {
		return new  ParamSwitchRemoteFeignServiceI(){


			@Override
			public FeignJson getSwitch(String switchName) {
				return null;
			}

			@Override
            public List<ParamSwitchDto> search(ParamSwitchDto paramSwitch) {
                return null;
            }

            @Override
            public void updateStatusById(String status, String id) {

            }
		};
	}
}

