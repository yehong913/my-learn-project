package com.glaway.ids.project.plm.fallback;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.PlmTypeDefinitionDto;
import com.glaway.ids.project.plm.service.IDSIntegratedPLMService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class IDSIntegratedPLMServiceCallBack implements FallbackFactory<IDSIntegratedPLMService> {

    @Override
    public IDSIntegratedPLMService create(Throwable cause) {
        return new IDSIntegratedPLMService() {

            @Override
            public List<JSONObject> getPrimaryObjectContext(List<JSONObject> jsonList, String userId) {
                return null;
            }

            @Override
            public FeignJson getAllLifeCycleStatusListJson() {
                return null;
            }

            @Override
            public List<CheckOutInfoVO> findObjectsForChangeExceptBaselineByCondition(List<ConditionVO> conditionList, String userId) {
                return null;
            }

            @Override
            public int getObjectForChangeExceptBaselineSizeByCondition(List<ConditionVO> conditionList, String userId) {
                return 0;
            }

            @Override public List<PlmTypeDefinitionDto> getChildTypeList(
                List<PlmTypeDefinitionDto> typelist, String typeId) {
                return null;
            }

        };
    }
}
