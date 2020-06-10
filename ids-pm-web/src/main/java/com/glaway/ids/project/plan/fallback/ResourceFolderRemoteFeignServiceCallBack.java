package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.common.vo.TreeNode;
import com.glaway.ids.project.plan.service.ResourceFolderRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.ResourceRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ResourceDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceFolderRemoteFeignServiceCallBack implements FallbackFactory<ResourceFolderRemoteFeignServiceI> {

	@Override
	public ResourceFolderRemoteFeignServiceI create(Throwable throwable) {
		return new ResourceFolderRemoteFeignServiceI(){
			@Override
			public List<TreeNode> getTreeNodesForPm() {
				return null;
			}
		};
	}
}

