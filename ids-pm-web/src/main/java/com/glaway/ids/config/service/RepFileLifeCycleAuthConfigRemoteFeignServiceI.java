package com.glaway.ids.config.service;


import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.config.fallback.RepFileLifeCycleAuthConfigRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文档类型参数设置feign接口
 * 
 * @author zhousuxia
 * @version 2019年6月13日
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = RepFileLifeCycleAuthConfigRemoteFeignServiceCallBack.class)
public interface RepFileLifeCycleAuthConfigRemoteFeignServiceI {


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileLifeCycleAuthConfigRestController/queryLifeCyclePolicyList.do")
    String queryLifeCyclePolicyList(@RequestParam("policyName") String policyName);


    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/repFileLifeCycleAuthConfigRestController/queryLifeCycleStatusEntityList.do")
    String queryLifeCycleStatusEntityList(@RequestParam("policyId") String policyId);
}
