package com.glaway.ids.rdtask.task.service;


import com.glaway.ids.rdtask.task.fallback.RdFlowWebRemoteFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;


/**
 * 接口
 * 
 * @author wqb
 * @version 2019年8月15日 16:21:58
 * @see RdFlowWebRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_RDFLOW_WEB,fallbackFactory = RdFlowWebRemoteFeignServiceCallBack.class )
public interface RdFlowWebRemoteFeignServiceI {
    @RequestMapping(value = "/ids-rdflow-web/feign/rdflowRestController/getClassesPathFromRdflow.do")
    FeignJson getClassesPathFromRdflow();
}
