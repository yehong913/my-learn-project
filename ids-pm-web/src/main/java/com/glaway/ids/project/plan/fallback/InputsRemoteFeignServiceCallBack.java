package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.service.InputsRemoteFeignServiceI;
import com.glaway.ids.project.plan.service.PlanRemoteFeignServiceI;
import com.glaway.ids.project.planview.dto.PlanViewColumnInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewInfoDto;
import com.glaway.ids.project.planview.dto.PlanViewSearchConditionDto;
import com.glaway.ids.project.planview.dto.UserPlanViewProjectDto;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import com.glaway.ids.project.projectmanager.vo.ProjLibDocumentVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InputsRemoteFeignServiceCallBack implements FallbackFactory<InputsRemoteFeignServiceI> {

	@Override
	public InputsRemoteFeignServiceI create(Throwable throwable) {
		return new InputsRemoteFeignServiceI(){


			@Override
			public List<InputsDto> queryNewInputsList(InputsDto inputs) {
				return null;
			}

			@Override
			public Map<String, String> getRepFileNameAndBizIdMap(String libId) {
				return null;
			}

			@Override
			public Map<String, String> getRepFilePathAndBizIdMap(String libId) {
				return null;
			}

			@Override
			public void deleteInputs(InputsDto inputsDto) {

			}

            @Override
            public List<InputsDto> getInputsInfoByPlanTemplateId(String templId) {
                return null;
            }

            @Override
			public InputsDto getInputEntity(String id) {
				return null;
			}

			@Override
			public Map<String, String> getRepFileIdAndBizIdMap(String libId) {
				return null;
			}

			@Override
			public List<ProjDocVo> getDocRelationList(PlanDto planDto, String userId) {
				return null;
			}

            @Override
            public List<InputsDto> queryInputsDetailList(InputsDto inputs) {

                return null;
            }

            @Override
            public List<InputsDto> queryInputsDetailListForString(String planParentId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<InputsDto> queryInputList(InputsDto inputs, int page, int rows,
                                                  boolean isPage) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void deleteInputsByOriginDeliverables(String originDeliverablesInfoId,
                                                         String useObjectType) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void deleteInputsById(String id) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void updateInputsForDocInfoById(String id, String docId, String docName) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public List<InputsDto> queryOutInputsDetailList(String planParentId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<ProjDocVo> getDocRelationListMatch(PlanDto planDto, String userId,
                                                           String deliverName) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void updateInputsByAddAndDel(Map<String, Object> params) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public FeignJson listView(Map<String, Object> map) {
                return new FeignJson();
            }

            @Override
            public FeignJson getInputsRelationList(PlanDto dto, int page, int rows, String projectId, String userId) {
                return new FeignJson();
            }
        };
	}
}

