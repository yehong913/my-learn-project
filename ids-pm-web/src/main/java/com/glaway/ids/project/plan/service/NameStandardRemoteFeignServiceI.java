package com.glaway.ids.project.plan.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.dto.NameStandardDto;
import com.glaway.ids.project.plan.fallback.NameStandardRemoteServiceCallBack;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 项目计划
 * 
 * @author blcao
 * @version 2015年3月27日
 * @see NameStandardRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = NameStandardRemoteServiceCallBack.class)
public interface NameStandardRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/nameStandardRestController/searchNameStandardsExceptDesign.do")
    List<NameStandardDto> searchNameStandardsExceptDesign(@RequestBody NameStandardDto nameStandard);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/nameStandardRestController/searchNameStandards.do")
    List<NameStandardDto> searchNameStandards(@RequestBody NameStandardDto nameStandardDto);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchDeliverablesForPage.do")
    List<DeliveryStandardDto> searchDeliverablesForPage(@RequestBody DeliveryStandardDto ds, @RequestParam("page") int page, @RequestParam("rows") int rows,
                                                        @RequestParam(value = "ids",required = false) String ids);


    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchDeliveryStandardsForPage.do")
    List<DeliveryStandardDto> searchDeliveryStandardsForPage(@RequestBody DeliveryStandardDto ds, @RequestParam("page") int page, @RequestParam("rows") int rows);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getDeliverablesCount.do")
    long getDeliverablesCount(@RequestBody DeliveryStandardDto ds, @RequestParam("ids") String notIn);


    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getSearchCount.do")
    long getSearchCount(@RequestBody DeliveryStandardDto ds);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getDeliveryStandardEntity.do")
    DeliveryStandardDto getDeliveryStandardEntity(@RequestParam(value = "id",required = false) String id);
    
    /**
     * 精确查找
     * @param ns
     * wqb 2019年7月5日 13:43:13
     */
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/nameStandardRestController/searchNameStandardsAccurate.do")
    List<NameStandardDto> searchNameStandardsAccurate(@RequestBody NameStandardDto ns);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/BusinessConfigController/getEntity.do")
    BusinessConfigDto getBusinessConfig(@RequestParam("id") String id);
}
