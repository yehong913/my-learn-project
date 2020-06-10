package com.glaway.ids.rdtask.task.service;


import com.glaway.ids.rdtask.task.fallback.ApplyProcTemplateRemoteFeignServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.ids.common.constant.FeignConstants;


/**
 * 分解
 * 
 * @author wqb
 * @version 2019年10月20日 17:36:16
 * @see ApplyProcTemplateRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ApplyProcTemplateRemoteFeignServiceCallback.class)
public interface ApplyProcTemplateRemoteFeignServiceI {
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/applyProcTemplateRestController/templateResolveForPlan.do")
    boolean templateResolveForPlan(@RequestParam(value = "parentId",required = false) String parentId, @RequestParam(value = "templateId",required = false) String templateId, @RequestParam(value = "currentId",required = false)  String currentId);
 
}
