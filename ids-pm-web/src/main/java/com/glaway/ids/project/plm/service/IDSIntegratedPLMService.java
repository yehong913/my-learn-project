package com.glaway.ids.project.plm.service;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plm.dto.CheckOutInfoVO;
import com.glaway.ids.project.plm.dto.PlmTypeDefinitionDto;
import com.glaway.ids.project.plm.fallback.IDSIntegratedPLMServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * IDS集成service的接口
 * plm-basic-service服务
 *
 * @date 2019年10月17日
 * @see IDSIntegratedPLMService
 * @since
 */
@FeignClient(value = FeignConstants.ID_PLM_SERVICE,fallbackFactory = IDSIntegratedPLMServiceCallBack.class)
public interface IDSIntegratedPLMService {
    /**
     * 受影响对象所有容器
     * @param jsonList
     * @param userId
     */
    @RequestMapping(value="/plm-service/feign/IDSIntegratedPLMController/getPrimaryObjectContext.do")
    List<JSONObject> getPrimaryObjectContext(@RequestBody List<JSONObject> jsonList, @RequestParam(value = "userId",required = false) String userId);

    /**
     * 获取所有生命周期状态
     * @return
     */
    @RequestMapping(value="/plm-service/feign/IDSIntegratedPLMController/getAllLifeCycleStatusListJson.do")
    FeignJson getAllLifeCycleStatusListJson();

    /**
     *
     * 选择对象(文档，图档，部件)查询
     *
     * @param conditionList
     * @return
     * @see
     */
    @RequestMapping(value ="/plm-service/feign/IDSIntegratedPLMController/findObjectsForChangeExceptBaselineByCondition.do")
    List<CheckOutInfoVO> findObjectsForChangeExceptBaselineByCondition(@RequestBody List<ConditionVO> conditionList,@RequestParam(value = "userId",required = false) String userId);

    /**
     *
     * 获取变更时选择对象(除基线外)数量
     *
     * @param conditionList
     * @return
     * @see
     */
    @RequestMapping(value ="/plm-service/feign/IDSIntegratedPLMController/getObjectForChangeExceptBaselineSizeByCondition.do")
    int getObjectForChangeExceptBaselineSizeByCondition(@RequestBody List<ConditionVO> conditionList, @RequestParam(value = "userId",required = false) String userId);

    /**
     * 获取子孙类型
     * @param typelist
     * List<PlmTypeDefinitionDto> typelist,String pid
     * @return
     */
    @RequestMapping(value="/plm-service/feign/IDSIntegratedPLMController/getChildTypeList.do")
    List<PlmTypeDefinitionDto> getChildTypeList(@RequestBody List<PlmTypeDefinitionDto> typelist, @RequestParam("typeId") String typeId);
}
