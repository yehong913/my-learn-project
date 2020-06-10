package com.glaway.ids.planGeneral.tabCombinationTemplate.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.feign.FeignIdsCommonServiceI;
import com.glaway.ids.planGeneral.tabCombinationTemplate.dto.TabCombinationTemplateDto;
import com.glaway.ids.planGeneral.tabCombinationTemplate.feign.TabCombinationTemplateFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TabCombinationTemplateFeignServiceCallback implements FallbackFactory<TabCombinationTemplateFeignServiceI> {

	@Override
	public TabCombinationTemplateFeignServiceI create(Throwable throwable) {
		return new  TabCombinationTemplateFeignServiceI(){
            @Override
            public FeignJson isActivityTypeManageUse(String id, String templateId) {
                return new FeignJson();
            }

            @Override
            public FeignJson saveTabCbTemplateInfo(Map<String, Object> param) {
                return null;
            }

            @Override
            public FeignJson updateTabCbTemplateInfo(Map<String, Object> param) {
                return null;
            }

            @Override
            public FeignJson queryEntity(Map<String, Object> params) {
                return null;
            }

            @Override
            public FeignJson findTabCbTempById(String id) {
                return null;
            }

            @Override
            public FeignJson getCombTemplateInfos(String tabCbTemplateId) {
                return null;
            }

            @Override
            public FeignJson getCombTemplateInfosByPlanId(String planId, String activityId, String displayAccess) {
                return null;
            }

            @Override
            public FeignJson doBatchDel(String ids) {
                return null;
            }

            @Override
            public FeignJson doStatusChange(String ids, String status, String userId) {
                return null;
            }

            @Override
            public List<TabCombinationTemplateDto> findTabCbTempByActivityId(String activityId) {
                return null;
            }

            @Override
            public List<String> queryAllName() {
                return null;
            }

            @Override
            public FeignJson doSubmitApprove(Map<String, String> map) {
                return new FeignJson();
            }

            @Override
            public FeignJson getVersionDatagridStr(String bizId, Integer pageSize, Integer pageNum) {
                return new FeignJson();
            }

            @Override
            public FeignJson backVersion(Map<String, String> params) {
                return new FeignJson();
            }

            @Override
            public FeignJson backSaveAndSubmit(Map<String, Object> param) {
                return new FeignJson();
            }

            @Override
            public TabCombinationTemplateDto findTabCbTempByPlanId(String planId) {
                return null;
            }

            @Override
            public FeignJson getLifeCycleStatusList() {
                return new FeignJson();
            }
        };
	}
}

