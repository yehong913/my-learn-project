package com.glaway.ids.qualityTest.feign;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.qualityTest.dto.CofigFormTestDto;
import com.glaway.ids.qualityTest.fallback.QualityTestFeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: QualityTestFeignServiceI
 * @Date: 2019/10/30-15:30
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = QualityTestFeignServiceFallBack.class)
public interface QualityTestFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/addQualityDataGrid.do")
    FeignJson addQualityDataGrid(@RequestParam(value = "useObjectId",required = false) String useObjectId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/searchDataGrid.do")
    FeignJson searchDataGrid(@RequestParam(value = "useObjectId",required = false) String useObjectId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/saveQualityDataGrid.do")
    FeignJson saveQualityDataGrid(@RequestParam(value = "planId",required = false) String planId,
                                  @RequestParam(value = "useObjectId",required = false) String useObjectId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/saveFormTest.do")
    FeignJson saveFormTest(@RequestBody CofigFormTestDto dto);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/updateFormTest.do")
    FeignJson updateFormTest(@RequestBody CofigFormTestDto dto);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/getFormTestByPlanId.do")
    FeignJson getFormTestByPlanId(@RequestParam(value = "planId",required = false) String planId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/qualityTestRestController/searchList.do")
    FeignJson searchList(@RequestParam(value = "planId",required = false) String planId);

}
