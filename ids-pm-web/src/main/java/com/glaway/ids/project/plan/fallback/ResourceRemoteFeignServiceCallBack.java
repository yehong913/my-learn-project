package com.glaway.ids.project.plan.fallback;

import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.NameStandardDto;
import com.glaway.ids.project.plan.service.NameStandardRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.ResourceRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceRemoteFeignServiceCallBack implements FallbackFactory<ResourceRemoteFeignServiceI> {

	@Override
	public ResourceRemoteFeignServiceI create(Throwable throwable) {
		return new ResourceRemoteFeignServiceI(){

			@Override
			public List<ResourceDto> searchUsables(ResourceDto resourceDto) {
				return null;
			}

			@Override
			public List<ResourceDto> getAllResourceInfos() {
				return null;
			}

            @Override
            public ResourceDto getEntity(String id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void doBatchDel(String ids) {
                // TODO Auto-generated method stub
                
            }
		};
	}
}

