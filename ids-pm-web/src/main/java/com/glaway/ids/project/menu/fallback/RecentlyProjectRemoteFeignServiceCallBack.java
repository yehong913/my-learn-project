package com.glaway.ids.project.menu.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.ids.project.menu.service.RecentlyProjectRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecentlyProjectRemoteFeignServiceCallBack implements FallbackFactory<RecentlyProjectRemoteFeignServiceI> {

	@Override
	public RecentlyProjectRemoteFeignServiceI create(Throwable throwable) {
		return new  RecentlyProjectRemoteFeignServiceI(){


			@Override
			public void updateRecentlyByProjectId(String projectId, TSUserDto userDto) {

			}
		};
	}
}

