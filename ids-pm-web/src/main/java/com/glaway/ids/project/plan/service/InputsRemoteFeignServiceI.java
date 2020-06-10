package com.glaway.ids.project.plan.service;


import java.util.List;
import java.util.Map;

import com.glaway.foundation.common.service.CommonService;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.fallback.InputsRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.fallback.PlanRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.vo.ProjDocVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 任务输入
 * 
 * @author blcao
 * @version 2015年7月6日
 * @see InputsRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = InputsRemoteFeignServiceCallBack.class)
public interface InputsRemoteFeignServiceI{


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/queryNewInputsList.do")
    List<InputsDto> queryNewInputsList(@RequestBody InputsDto inputs);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getRepFileNameAndBizIdMap.do")
    Map<String, String> getRepFileNameAndBizIdMap(@RequestParam("libId") String libId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getRepFilePathAndBizIdMap.do")
    Map<String, String> getRepFilePathAndBizIdMap(@RequestParam("libId") String libId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getRepFileIdAndBizIdMap.do")
    Map<String, String> getRepFileIdAndBizIdMap(@RequestParam("libId") String libId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getDocRelationList.do")
    List<ProjDocVo> getDocRelationList(@RequestBody PlanDto planDto,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getInputEntity.do")
    InputsDto getInputEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/deleteInputs.do")
    void deleteInputs(@RequestBody InputsDto inputsDto);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/queryInputsDetailList.do")
    List<InputsDto> queryInputsDetailList(@RequestBody InputsDto inputs);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/queryInputsDetailListForString.do")
    List<InputsDto> queryInputsDetailListForString(@RequestParam("planParentId") String planParentId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/queryInputList.do")
    List<InputsDto> queryInputList(@RequestBody InputsDto inputs,@RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("isPage") boolean isPage);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/deleteInputsByOriginDeliverables.do")
    void deleteInputsByOriginDeliverables(@RequestParam("originDeliverablesInfoId") String originDeliverablesInfoId, @RequestParam("useObjectType") String useObjectType);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/deleteInputsById.do")
    void deleteInputsById(@RequestParam("id") String id);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/updateInputsForDocInfoById.do")
    void updateInputsForDocInfoById(@RequestParam("id") String id, @RequestParam("docId") String docId, @RequestParam("docName") String docName);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/queryOutInputsDetailList.do")
    List<InputsDto> queryOutInputsDetailList(@RequestParam("planParentId") String planParentId);
    
    /**
     * 获取项目库信息(自动匹配使用)
     * 
     * @author wqb
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getDocRelationListMatch.do")
    List<ProjDocVo> getDocRelationListMatch(@RequestBody PlanDto planDto,@RequestParam("userId") String userId,@RequestParam("deliverName") String deliverName);

    /**
     * 更新输入
     * @param params
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/updateInputsByAddAndDel.do")
    void updateInputsByAddAndDel(@RequestBody Map<String, Object> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/listView.do")
    FeignJson listView(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getInputsRelationList.do")
    FeignJson getInputsRelationList(@RequestBody PlanDto dto,@RequestParam("page") int page, @RequestParam("rows") int rows,
                                    @RequestParam(value = "projectId",required = false) String projectId, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/inputsRestController/getInputsInfoByPlanTemplateId.do")
    List<InputsDto> getInputsInfoByPlanTemplateId(@RequestParam(value = "templId",required = false) String templId);
}
