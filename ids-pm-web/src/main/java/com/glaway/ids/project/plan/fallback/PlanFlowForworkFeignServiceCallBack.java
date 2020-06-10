package com.glaway.ids.project.plan.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.BasicLineDto;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.dto.TemporaryPlanDto;
import com.glaway.ids.project.plan.service.PlanFlowForworkFeignServiceI;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: PlanFlowForworkFeignServiceCallBack
 * @Date: 2019/8/6-20:04
 * @since
 */
@Component
public class PlanFlowForworkFeignServiceCallBack implements FallbackFactory<PlanFlowForworkFeignServiceI> {
    @Override
    public PlanFlowForworkFeignServiceI create(Throwable throwable) {
        return new PlanFlowForworkFeignServiceI() {
            @Override
            public FeignJson modifyResourceMassForWork(List<ResourceLinkInfoDto> resourceLst, String useRates, String endTimes, String startTimes) {
                return new FeignJson();
            }

            @Override
            public FeignJson saveModifyListForWork(FeignJson feignJson) {
                return null;
            }

            @Override
            public FeignJson doBatchDel(String ids) {
                return new FeignJson();
            }

            @Override
            public void deleteChildPlan(PlanDto plan, String userId) {
                
            }

            @Override
            public FeignJson doAddDelDeliverForWork(String names, DeliverablesInfoDto deliverablesInfo) {
                return null;
            }

            @Override
            public FeignJson doAddInheritlistForTemplate(String names, String planIdForInherit, String useObjectType, TSUserDto userDto, String orgId) {
                return null;
            }

            @Override
            public String getChangeTaskIdByFormId(String formId) {
                return null;
            }

            @Override
            public List<ResourceLinkInfoDto> getResourceLinkInfosByProject(String projectId) {
                return null;
            }

            @Override
            public void startPlanChangeFlowForWork(FeignJson feignJson) {

            }

            @Override
            public List<TemporaryPlanDto> getTempPlanListForWork(FeignJson feignJson) {
                return null;
            }

            @Override
            public void startPlanChangeMassForWork(FeignJson feignJson) {

            }

            @Override
            public void doAddInheritlist(String names, String planIdForInherit, TSUserDto curUser, String orgId) {

            }

            @Override
            public void copyBasicLineForWork(BasicLineDto basicLine, String ids, String projectId, String basicLineIdForCopy) {

            }
        };
    }
}
