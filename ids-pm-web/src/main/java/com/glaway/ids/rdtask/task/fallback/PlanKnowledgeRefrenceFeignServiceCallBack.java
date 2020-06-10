package com.glaway.ids.rdtask.task.fallback;

import com.glaway.ids.models.JsonResult;
import com.glaway.ids.rdtask.task.service.PlanKnowledgeReferenceFeignServiceI;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 〈一句话功能简述〉
 * @author: wqb
 * @ClassName: PlanKnowledgeRefrenceFeignServiceCallBack
 * @Date: 2019年11月29日 13:30:13
 * @since
 */
@Component
public class PlanKnowledgeRefrenceFeignServiceCallBack implements FallbackFactory<PlanKnowledgeReferenceFeignServiceI> {
    @Override
    public PlanKnowledgeReferenceFeignServiceI create(Throwable throwable) {
        return new PlanKnowledgeReferenceFeignServiceI() {

            @Override
            public JsonResult findKnowledgeInfo(Map<String, Object> paramsMap) {
                return null;
            }

            @Override
            public JsonResult deleteKnowledgeInfo(Map<String, Object> paramsMap) {
                return null;
            }
        };
    }
}
