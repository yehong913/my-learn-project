package com.glaway.ids.project.projectmanager.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.ids.project.projectmanager.dto.Project;
import com.glaway.ids.project.projectmanager.service.ProjLogRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.service.ProjectRemoteFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjLogRemoteFeignServiceCallBack implements FallbackFactory<ProjLogRemoteFeignServiceI> {

	@Override
	public ProjLogRemoteFeignServiceI create(Throwable throwable) {
		return new ProjLogRemoteFeignServiceI(){


			@Override
			public void saveProjLog(String projectNumber, String projectId, String logInfo, String remark, TSUserDto userDto) {

			}
		};
	}
}

