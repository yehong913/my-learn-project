package com.glaway.ids.rdtask.task.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.rdtask.task.service.ApplyProcTemplateRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowWebRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RdFlowWebRemoteFeignServiceCallBack implements FallbackFactory<RdFlowWebRemoteFeignServiceI> {

    @Override
    public RdFlowWebRemoteFeignServiceI create(Throwable cause) {
        return new RdFlowWebRemoteFeignServiceI(){
            @Override
            public FeignJson getClassesPathFromRdflow() {
                return null;
            }
        };
    }

}

