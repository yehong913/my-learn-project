package com.glaway.ids.planGeneral.activityTypeManage.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.feign.FeignIdsCommonServiceI;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.feign.ActivityTypeManageFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActivityTypeManageFeignCallback implements FallbackFactory<ActivityTypeManageFeign> {

	@Override
	public ActivityTypeManageFeign create(Throwable throwable) {
		return new  ActivityTypeManageFeign(){
            @Override
            public FeignJson queryEntity(FeignJson feignJson) {
                return null;
            }

            @Override
            public FeignJson doAddActivityTypeManage(String userId, String name, String remark, String id) {
                return null;
            }

            @Override
            public FeignJson doDeleteBatch(String userId, String ids) {
                return null;
            }

            @Override
            public FeignJson deleteBatchBeforeCheckDate(String ids) {
                return null;
            }

            @Override
            public FeignJson doStartOrStop(String userId, String ids, String status) {
                return null;
            }

            @Override
            public ActivityTypeManageDto queryActivityTypeManageById(String id) {
                return null;
            }

            @Override
            public List<ActivityTypeManageDto> getAllActivityTypeManage(Boolean flag) {
                return null;
            }

            @Override
            public List<ActivityTypeManageDto> getAllActivityTypeManage() {
                return null;
            }

            @Override
            public Map<String, String> getAllActivityTypeManageMap() {
                return new HashMap<>();
            }
        };
	}
}

