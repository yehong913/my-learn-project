package com.glaway.ids.rdtask.task.service;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.rdtask.task.fallback.TaskFeedbackFeignServiceCallBack;
import com.glaway.ids.rdtask.task.form.TaskFeedbackInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Description: 〈任务反馈信息〉
 * @author: sunmeng
 * @ClassName: TaskFeedbackFeignServiceI
 * @Date: 2019/8/8-13:47
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = TaskFeedbackFeignServiceCallBack.class)
public interface TaskFeedbackFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFeedbackRestController/saveTaskFeedback.do")
    FeignJson saveTaskFeedback(@RequestBody Map<String,Object> map);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFeedbackRestController/doSubmitApprove.do")
    FeignJson doSubmitApprove(@RequestParam(value = "leader",required = false) String leader,
                              @RequestParam(value = "planId",required = false) String planId,@RequestParam(value = "userId",required = false) String userId);
}
