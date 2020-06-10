package com.glaway.ids.project.plan.service;


import java.util.List;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.common.dto.DeliveryStandardDocTypeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.ids.project.plan.dto.DeliveryStandardDto;
import com.glaway.ids.project.plan.fallback.DeliveryStandardRemoteFeignServiceCallBack;



/**
 * 配置业务接口
 * 
 * @author Administrator
 * @version 2015年3月26日
 * @see DeliveryStandardRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = DeliveryStandardRemoteFeignServiceCallBack.class)
public interface DeliveryStandardRemoteFeignServiceI
{

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getDeliveryStandardEntity.do")
    DeliveryStandardDto getDeliveryStandardEntity(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchUseableDeliveryStandards.do")
    List<DeliveryStandardDto> searchUseableDeliveryStandards(@RequestBody DeliveryStandardDto condition);

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchDeliveryStandardAccurate.do")
    List<DeliveryStandardDto> searchDeliveryStandardAccurate(@RequestBody DeliveryStandardDto condition );

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getDeliveryStandardByName.do")
    DeliveryStandardDto getDeliveryStandardByName(@RequestParam(value = "name",required = false) String name);
    
    /**查询交付项名称库
     * @param ds
     * wqb 2019年6月19日 16:06:08
     */
    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/searchDeliveryStandards.do")
    List<DeliveryStandardDto> searchDeliveryStandards(@RequestBody DeliveryStandardDto ds);

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/deliveryStandardRestController/getDeliveryStandardDocTypeById.do")
    DeliveryStandardDocTypeDto getDeliveryStandardDocTypeById(@RequestParam(value = "id",required = false) String id);
}
