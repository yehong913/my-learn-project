package com.glaway.ids.rdtask.task.fallback;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.rdtask.task.service.RdFlowTaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Component
public class RdFlowTaskFlowResolveWebRemoteFeignServiceCallBack implements FallbackFactory<RdFlowTaskFlowResolveRemoteFeignServiceI> {

    @Override
    public RdFlowTaskFlowResolveRemoteFeignServiceI create(Throwable cause) {
        return new RdFlowTaskFlowResolveRemoteFeignServiceI(){

            @Override
            public RDTaskVO getXmlbyPlanId(String planId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void doDeloutputByPlanOutputId(String id) {
                // TODO Auto-generated method stub

            }

            @Override
            public FeignJson isHaveLinkPlanId(String parentPlanId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void getSaveRdTaskInfo(RDTaskVO vo, String templateId, String outUserId,
                                          String approveType, String procInstId, String formId,
                                          String planMapStr, String inputMapStr,
                                          String outputMapStr) {
                // TODO Auto-generated method stub

            }

            @Override
            public void getDoUpdateXml(Map<String, String> in, String userId, String parentPlanId,
                                       String cellIds, String cellContact) {
                // TODO Auto-generated method stub

            }

            @Override
            public String saveFlowTask1(Map<String, Object> paramMap) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void getSaveTaskInfoFromPlan1(String userId, String parentPlanId,
                                                 String planName, String owner, String remark,
                                                 String planId, String workTime, String cellId) {
                // TODO Auto-generated method stub

            }

            @Override
            public FeignJson getParentPlanIdByFlowTaskId(String flowTaskId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson getPlanIdByFlowTaskParentId(String flowTaskParentId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public void saveRdfInputByPlan(String cellAndNameInputStr, String parentPlanId,
                                           String cellId, String userId) {
                // TODO Auto-generated method stub

            }

            @Override
            public FeignJson getFlowTaskDeliverablesInfoInitBusinessObject() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public TaskProcTemplateDto getProcTemplateEntity(String id) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public RDTaskVO startAssignProcess(TSUserDto actor, String leader, String deptLeader,
                                               String parentPlanId, String type) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FlowTaskParentVo getFlowTaskParent(String parentPlanId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson getProcCategoryTree() {
                return null;
            }

            @Override
            public Map<String, Object> getConditionSearch(Map<String, Object> params) {
                return null;
            }

            @Override
            public List<FlowTaskCellConnectVo> queryFlowTaskCellConnectVoListForTargetId(String parentPlanId, String cellId) {
                return null;
            }

            @Override
            public List<TaskProcTemplateVo> getProcTemplateAllList(String createOrgId,
                                                                   String nameValue,
                                                                   String pageValue,
                                                                   String rowsKey) {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

}

