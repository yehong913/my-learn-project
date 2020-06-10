package com.glaway.ids.config.fallback;

import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.CriteriaQuery;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.config.service.BusinessConfigRemoteFeignServiceI;
import com.glaway.ids.config.service.PlanBusinessConfigServiceI;
import com.glaway.ids.config.vo.BusinessConfig;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BusinessConfigRemoteFeignServiceCallback implements FallbackFactory<BusinessConfigRemoteFeignServiceI> {

	@Override
	public BusinessConfigRemoteFeignServiceI create(Throwable throwable) {
		return new  BusinessConfigRemoteFeignServiceI(){


			@Override
			public FeignJson doAdd(BusinessConfig businessConfig, String type) {
				return null;
			}

			@Override
			public FeignJson doBatchDel(String ids, String msg) {
				return null;
			}

			@Override
			public Map<String, Object> doUpdate(Map<String, Object> map) {
				return null;
			}

			@Override
			public void doBatchStartOrStop(String id, String state) {

			}
		};
	}
}

