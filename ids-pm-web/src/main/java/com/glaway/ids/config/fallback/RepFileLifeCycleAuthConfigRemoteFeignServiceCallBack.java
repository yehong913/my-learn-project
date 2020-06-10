package com.glaway.ids.config.fallback;

import com.glaway.ids.config.service.RepFileLifeCycleAuthConfigRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RepFileLifeCycleAuthConfigRemoteFeignServiceCallBack implements FallbackFactory<RepFileLifeCycleAuthConfigRemoteFeignServiceI> {


    @Override
    public RepFileLifeCycleAuthConfigRemoteFeignServiceI create(Throwable throwable) {
        return new RepFileLifeCycleAuthConfigRemoteFeignServiceI(){


            @Override
            public String queryLifeCyclePolicyList(String policyName) {
                return null;
            }

            @Override
            public String queryLifeCycleStatusEntityList(String policyId) {
                return null;
            }
        };
    }
}
