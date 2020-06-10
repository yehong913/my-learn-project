package com.glaway.ids.rdtask.task.service;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.models.JsonResult;
import com.glaway.ids.rdtask.task.fallback.PlanKnowledgeRefrenceFeignServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description: 〈任务反馈信息〉
 * @author: sunmeng
 * @ClassName: TaskFeedbackFeignServiceI
 * @Date: 2019/8/8-13:47
 * @since
 */
@FeignClient(value = FeignConstants.KES_KLM_SERVICE,fallbackFactory = PlanKnowledgeRefrenceFeignServiceCallBack.class)
public interface PlanKnowledgeReferenceFeignServiceI {

    @RequestMapping(FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/knowledgeOperationRestController/findKnowledgeInfo.do")
    JsonResult findKnowledgeInfo(@RequestBody Map<String, Object> paramsMap);

    @RequestMapping(FeignConstants.KES_KLM_FEIGN_SERVICE+"/feign/knowledgeOperationRestController/deleteKnowledgeInfo.do")
    JsonResult deleteKnowledgeInfo(@RequestBody Map<String, Object> paramsMap);

}
