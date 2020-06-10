package com.glaway.ids.config.service;


import java.util.List;

import com.glaway.foundation.businessobject.service.BusinessObjectServiceI;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.dto.ParamSwitchDto;
import com.glaway.ids.config.fallback.BusinessConfigServiceCallBack;
import com.glaway.ids.config.fallback.ParamSwitchRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 项目计划参数接口
 * @author xshen
 * @version 2015年4月16日
 * @see ParamSwitchRemoteFeignServiceI
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ParamSwitchRemoteFeignServiceCallBack.class)
public interface ParamSwitchRemoteFeignServiceI  {

    /**
     * 根据开关名称获取开关状态
     * 
     * @param switchName
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planRestController/getSwitch.do")
    FeignJson getSwitch(@RequestParam("switchName") String switchName);

    /**
     * 搜索参数列表
     * 
     * @param paramSwitch
     * wqb 2019年7月29日 13:47:35
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/paramSwitchRestController/getSwitch.do")
    List<ParamSwitchDto> search(@RequestBody ParamSwitchDto paramSwitch);
    
    /**设置状态
     * @param status
     * @param id 
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/paramSwitchRestController/updateStatusById.do")
    void updateStatusById(@RequestParam("status") String status,@RequestParam("id") String id);
}
