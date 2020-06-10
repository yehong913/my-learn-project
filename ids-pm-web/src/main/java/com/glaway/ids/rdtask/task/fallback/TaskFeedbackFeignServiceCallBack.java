package com.glaway.ids.rdtask.task.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.rdtask.task.form.TaskFeedbackInfo;
import com.glaway.ids.rdtask.task.service.TaskFeedbackFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: TaskFeedbackFeignServiceCallBack
 * @Date: 2019/8/8-13:48
 * @since
 */
@Component
public class TaskFeedbackFeignServiceCallBack implements FallbackFactory<TaskFeedbackFeignServiceI> {
    @Override
    public TaskFeedbackFeignServiceI create(Throwable throwable) {
        return new TaskFeedbackFeignServiceI() {
            @Override
            public FeignJson saveTaskFeedback(Map<String,Object> map) {
                return new FeignJson();
            }

            @Override
            public FeignJson doSubmitApprove(String leader, String planId, String userId) {
                return new FeignJson();
            }
        };
    }
}
