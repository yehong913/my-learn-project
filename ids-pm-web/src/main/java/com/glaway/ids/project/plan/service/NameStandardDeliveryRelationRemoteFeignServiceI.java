package com.glaway.ids.project.plan.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.NameStandardDeliveryRelationDto;
import com.glaway.ids.project.plan.fallback.DeliverablesInfoRemoteFeignServiceCallBack;
import com.glaway.ids.project.plan.fallback.NameStandardDeliveryRelationRemoteFeignCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 任务输入
 * 
 * @author blcao
 * @version 2015年7月6日
 * @see NameStandardDeliveryRelationRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = NameStandardDeliveryRelationRemoteFeignCallBack.class)
public interface NameStandardDeliveryRelationRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/nameStandardRestController/searchForPage.do")
    List<NameStandardDeliveryRelationDto> searchForPage(@RequestBody NameStandardDeliveryRelationDto relation,
                                                       @RequestParam("page") int page, @RequestParam("rows") int rows);

}
