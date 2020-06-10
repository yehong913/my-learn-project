package com.glaway.ids.planGeneral.plantabtemplate.callback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.planGeneral.plantabtemplate.dto.ObjectPropertyInfoDto;
import com.glaway.ids.planGeneral.plantabtemplate.feign.ObjectPropertyFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ObjectPropertyFeignCallBack implements FallbackFactory<ObjectPropertyFeign> {

    @Override
    public ObjectPropertyFeign create(Throwable cause) {

        return new ObjectPropertyFeign() {

            @Override
            public List<ObjectPropertyInfoDto> getAllPropertyByTabId(String tabId) {
                return null;
            }

            @Override
            public FeignJson doDelete(String id) {
                return null;
            }

        };
    }
}
