package com.glaway.ids.config.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.BusinessConfigRemoteFeignServiceCallback;
import com.glaway.ids.config.vo.BusinessConfig;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: BusineessRemoteFeignProductServiceI
 * @Date: 2019/6/3-11:07
 * @since
 */
@FeignClient(value = FeignConstants.ID_COMMON_SERVICE,fallbackFactory = BusinessConfigRemoteFeignServiceCallback.class)
public interface BusinessConfigRemoteFeignServiceI {

    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/BusinessConfigController/doAdd.do")
    FeignJson doAdd(@RequestBody BusinessConfig businessConfig, @RequestParam(value = "type", required = false) String type);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/BusinessConfigController/doBatchDel.do")
    FeignJson doBatchDel(@RequestParam("ids") String ids, @RequestParam("type")String type);


    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/BusinessConfigController/doUpdate.do")
    Map<String,Object> doUpdate(@RequestBody Map<String,Object> map);


    @RequestMapping(FeignConstants.IDS_COMMON_FEIGN_SERVICE+"/feign/BusinessConfigController/doBatchStartOrStop.do")
    void doBatchStartOrStop(@RequestParam(value = "id",required = false) String id,@RequestParam(value = "state",required = false) String state);


}
