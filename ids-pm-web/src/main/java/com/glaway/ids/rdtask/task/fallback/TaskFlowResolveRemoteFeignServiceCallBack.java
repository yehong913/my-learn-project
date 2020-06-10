package com.glaway.ids.rdtask.task.fallback;

import java.util.List;
import java.util.Map;

import com.glaway.ids.project.plan.dto.PlanownerApplychangeInfoDto;
import org.springframework.stereotype.Component;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.FlowTaskOutChangeVO;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.rdtask.task.service.TaskFlowResolveRemoteFeignServiceI;
import com.glaway.ids.rdtask.task.vo.ChangeFlowTaskCellConnectVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskParentVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskPreposeVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskVo;

import feign.hystrix.FallbackFactory;

@Component
public class TaskFlowResolveRemoteFeignServiceCallBack implements FallbackFactory<TaskFlowResolveRemoteFeignServiceI> {

    @Override
    public TaskFlowResolveRemoteFeignServiceI create(Throwable cause) {
        return new TaskFlowResolveRemoteFeignServiceI(){

            @Override
            public List<FlowTaskVo> getChangeFlowTaskList(FlowTaskParentVo flowTaskParent,
                                                          String userId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<ChangeFlowTaskCellConnectVo> getChangeFlowTaskConnectList(FlowTaskParentVo parent) {
                // TODO Auto-generated method stub
                return null;
            }


            @Override
            public void doAddInputsNew(String ids, InputsDto dto, String currentUserId) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doBatchDelInputsForWork(String ids) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doAddInheritDocument(String names, PlanDto temp) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doAdd(String type, String names, String userId, List<PlanDto> dtoList) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doAddInputs(String ids, InputsDto temp) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void doBatchSaveBasicInfo(String fromTemplate, PlanDto task,
                                             String planStartTime, String planEndTime,
                                             String workTime, boolean nameChange) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void deleteLineConnect(Map<String, Object> map) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void deleteLinkInput(Map<String, Object> map) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void deleteSelectedCell(String parentPlanId, String cellId) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void saveOutPreposePlan(String preposeIds, String useObjectId) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void updateFlowTasks(PlanDto temp, String cellIds, String parentPlanId,
                                        String cellContact, String userId) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public boolean templateResolveForPlan(String parentId, String templateId,
                                                  String currentId) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void saveFlowTask1(Map<String, Object> paramMap) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public FeignJson saveFlowTask2(Map<String, Object> params, String currentUserId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public FeignJson startChangeApplyProcess(PlanownerApplychangeInfoDto dto, String leader, String userId) {
                return new FeignJson();
            }

            @Override
            public FeignJson startChangeApplyForWorkFlow(Map<String, Object> map) {
                return new FeignJson();
            }

            @Override
            public FeignJson cancelChangeApplyForWorkFlow(Map<String, Object> map) {
                return new FeignJson();
            }

            @Override
            public List<FlowTaskVo> getChangeFlowTaskListForChange(PlanDto plan, String userId) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<ChangeFlowTaskCellConnectVo> getChangeFlowTaskConnectListForChange(PlanDto plan) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<FlowTaskPreposeVo> getChangeFlowTaskPreposeListForChange(PlanDto plan) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<FlowTaskVo> saveFlowTaskForChange1(Map<String, Object> params) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<FlowTaskVo> saveFlowTaskForChange2(Map<String, Object> params) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public boolean startPlanChangeforBackProcess(String leader, String deptLeader,
                                                         FlowTaskParentVo flowTaskParent,
                                                         String userId) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public FlowTaskOutChangeVO startPlanChange(String userId, String leader,
                                                       String deptLeader, String changeType,
                                                       String changeRemark,
                                                       Map<String, Object> params) {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

}

