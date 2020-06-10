package com.glaway.ids.rdtask.task.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.rdtask.task.service.ApplyProcTemplateRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.FlowTaskParentVo;
import com.glaway.ids.rdtask.task.vo.RDTaskVO;
import com.glaway.ids.rdtask.task.vo.TaskProcTemplateDto;
import com.glaway.ids.rdtask.task.vo.TaskProcTemplateVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ApplyProcTemplateRemoteFeignServiceCallback implements FallbackFactory<ApplyProcTemplateRemoteFeignServiceI> {

    @Override
    public ApplyProcTemplateRemoteFeignServiceI create(Throwable cause) {
        return new ApplyProcTemplateRemoteFeignServiceI(){

            @Override
            public boolean templateResolveForPlan(String parentId, String templateId, String currentId) {
                return false;
            }
        };
    }

}

