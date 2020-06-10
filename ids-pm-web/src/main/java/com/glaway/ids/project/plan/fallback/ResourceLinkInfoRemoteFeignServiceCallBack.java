package com.glaway.ids.project.plan.fallback;

import com.glaway.ids.project.plan.service.ResourceLinkInfoRemoteFeignServiceI;
import com.glaway.ids.project.plan.vo.CheckResourceUsedRateVO;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ResourceLinkInfoRemoteFeignServiceCallBack implements FallbackFactory<ResourceLinkInfoRemoteFeignServiceI> {

	@Override
	public ResourceLinkInfoRemoteFeignServiceI create(Throwable throwable) {
		return new ResourceLinkInfoRemoteFeignServiceI(){
			@Override
			public void doAddResourceForWork(String ids, String useObjectId, String planStartTime2, String planEndTime2, String useObjectType) {

			}

			@Override
			public List<ResourceLinkInfoDto> queryResourceList(ResourceLinkInfoDto resourceLinkInfo, int page, int rows, boolean isPage) {
				return null;
			}

            @Override
            public Map<String, Object> doAdd(ResourceDto resource) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void doAddResourceLinkInfo(ResourceLinkInfoDto dto) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public ResourceLinkInfoDto getResourceLinkInfoEntity(String id) {
                // TODO Auto-generated method stub
                return null;
            }

			@Override
			public List<Map<String, Object>> getAllLinkInfo(ResourceLinkInfoDto resourceLinkInfoDto) {
				return null;
			}

			@Override
			public List<CheckResourceUsedRateVO> conditionSearchForCheckResource(String resourceId, String startTime, String endTime, int page, int rows, String useobjectid) {
				return null;
			}

			@Override
			public void updateResourceLinkInfoTimeByDto(List<ResourceLinkInfoDto> res) {

			}

			@Override
			public long getCount(ResourceLinkInfoDto dto) {
				return 0;
			}
		};
	}
}


