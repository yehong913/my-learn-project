package com.glaway.ids.project.menu.fallback;

import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.menu.service.ProjectMenuRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProjectMenuRemoteFeignServiceCallBack implements FallbackFactory<ProjectMenuRemoteFeignServiceI> {

	@Override
	public ProjectMenuRemoteFeignServiceI create(Throwable throwable) {
		return new  ProjectMenuRemoteFeignServiceI(){

			@Override
			public FeignJson constructionProjectMenuTree(String projectId, String currentUserId) {
				return new FeignJson();
			}
		};
	}
}

