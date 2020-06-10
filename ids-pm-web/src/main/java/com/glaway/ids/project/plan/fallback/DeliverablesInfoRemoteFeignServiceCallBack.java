package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.DeliverablesInfoRemoteFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DeliverablesInfoRemoteFeignServiceCallBack implements FallbackFactory<DeliverablesInfoRemoteFeignServiceI> {

	@Override
	public DeliverablesInfoRemoteFeignServiceI create(Throwable throwable) {
		return new DeliverablesInfoRemoteFeignServiceI(){


			@Override
			public List<DeliverablesInfoDto> queryDeliverableList(DeliverablesInfoDto deliverablesInfo, int page, int rows, boolean isPage) {
				return null;
			}

			@Override
			public void doBatchDelDeliverForWork(String ids) {

			}

			@Override
			public long getCount(DeliverablesInfoDto deliverablesInfo) {
				return 0;
			}

			@Override
			public void deleteDeliverablesById(String id) {

			}

			@Override
			public void doAddDelDeliverForWork(String names, DeliverablesInfoDto deliverablesInfo) {

			}

			@Override
			public String initDeliverablesInfo(DeliverablesInfoDto deliverablesInfoDto) {
				return null;
			}

			@Override
			public void saveDeliverablesInfo(DeliverablesInfoDto deliverablesInfoDto) {

			}

            @Override
            public FeignJson getLifeCycleStatusList() {
                return null;
            }

            @Override
            public DeliverablesInfoDto getDeliverablesInfoEntity(String id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<DeliverablesInfoDto> getPreposePlanDeliverables(String preposeIds) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<DeliverablesInfoDto> getDeliverablesByUseObeject(String useObjectType,
                                                                         String useObjectId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<DeliverablesInfoDto> getSelectedPreposePlanDeliverables(String ids) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void deleteDeliverablesByPlanId(String planId) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public List<DeliverablesInfoDto> getAllDeliverablesByUseObeject(String parentPlanId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Integer getJudgePlanAllDocumantWithStatus(PlanDto plan, String isOut) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson listView(Map<String, Object> map) {
                return new FeignJson();
            }

            @Override
            public void initBusinessObject(DeliverablesInfoDto document) {

            }

            @Override
            public Map<String, ProjDocRelationDto> queryFinishDeliverable(String planId) {
                return null;
            }

            @Override
            public FeignJson updateDeliverablesInfo(DeliverablesInfoDto deliverablesInfo) {
                return new FeignJson();
            }

            @Override
            public FeignJson updateDeliverablesInfoByPlm(DeliverablesInfoDto deliverablesInfo) {
                return null;
            }

            @Override
            public List<DeliverablesInfoDto> getDeliverablesByProject(String projectId) {
                return null;
            }
        };
	}
}

