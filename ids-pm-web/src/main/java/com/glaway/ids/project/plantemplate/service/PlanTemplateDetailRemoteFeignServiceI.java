package com.glaway.ids.project.plantemplate.service;


import java.util.List;
import java.util.Map;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.fallback.PlanTemplateDetailRemoteFeignServiceCallBack;
import com.glaway.ids.project.plantemplate.fallback.PlanTemplateRemoteFeignServiceCallBack;
import org.apache.poi.ss.formula.functions.T;

import com.glaway.foundation.businessobject.service.BusinessObjectServiceI;
import com.glaway.foundation.common.exception.GWException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 〈计划模板WBS计划接口〉
 * 〈功能详细描述〉
 * 
 * @author zhousuxia
 * @version 2019年8月13日
 * @see PlanTemplateDetailRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanTemplateDetailRemoteFeignServiceCallBack.class)
public interface PlanTemplateDetailRemoteFeignServiceI{

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateOrProjTemplateDetailPreposes.do")
    FeignJson getPlanTemplateOrProjTemplateDetailPreposes(@RequestParam(value = "plantemplateId",required = false) String plantemplateId, @RequestParam(value = "type",required = false) String type);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateDetailPreposesId.do")
    FeignJson getPlanTemplateDetailPreposesId(@RequestParam("plantemplateId") String plantemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateDetailList.do")
    FeignJson getPlanTemplateDetailList(@RequestParam("planTemplateId") String planTemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateOrProjTemplateDetailDeliverables.do")
    Map<String, String> getPlanTemplateOrProjTemplateDetailDeliverables(@RequestParam("plantemplateId") String plantemplateId, @RequestParam("type") String type);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateDetailInputsName.do")
    Map<String, String> getPlanTemplateDetailInputsName(@RequestBody List<InputsDto> list);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateDetailRestController/getPlanTemplateDetailInputsOrigin.do")
    Map<String, String> getPlanTemplateDetailInputsOrigin(@RequestBody Map<String,Object> paramsMap);

}
