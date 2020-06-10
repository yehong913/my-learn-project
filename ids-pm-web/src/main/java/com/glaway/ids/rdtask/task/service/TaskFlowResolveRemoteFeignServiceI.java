package com.glaway.ids.rdtask.task.service;


import java.util.List;
import java.util.Map;

import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.PlanownerApplychangeInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glaway.foundation.common.exception.GWException;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.project.plan.dto.FlowTaskOutChangeVO;
import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.rdtask.task.fallback.TaskFlowResolveRemoteFeignServiceCallBack;
import com.glaway.ids.rdtask.task.vo.ChangeFlowTaskCellConnectVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskParentVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskPreposeVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskVo;


/**
 * 分解
 * 
 * @author wqb
 * @version 2019年7月29日 21:43:16
 * @see TaskFlowResolveRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = TaskFlowResolveRemoteFeignServiceCallBack.class)
public interface TaskFlowResolveRemoteFeignServiceI {
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getChangeFlowTaskList.do")
    List<FlowTaskVo> getChangeFlowTaskList(@RequestBody FlowTaskParentVo flowTaskParent, @RequestParam(value = "userId",required = false)String userId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getChangeFlowTaskListForChange.do")
    List<FlowTaskVo> getChangeFlowTaskListForChange(@RequestBody PlanDto plan,@RequestParam(value = "userId",required = false)String userId);
    
    /**
     * 获取parent计划的子流程任务节点的前后置关系，并将其转换为ChangeFlowTaskCellConnect
     * 
     * @param parent
     * @return
     * @throws GWException
     * @see
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getChangeFlowTaskConnectList.do")
    List<ChangeFlowTaskCellConnectVo> getChangeFlowTaskConnectList(@RequestBody FlowTaskParentVo parent);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getChangeFlowTaskConnectListForChange.do")
    List<ChangeFlowTaskCellConnectVo> getChangeFlowTaskConnectListForChange(PlanDto plan);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getChangeFlowTaskPreposeListForChange.do")
    List<FlowTaskPreposeVo> getChangeFlowTaskPreposeListForChange(PlanDto plan);
    
    /**
     * 更新编辑后的流程任务
     * 
     * @param temp
     * @param cellIds
     * @param parentPlanId
     * @param cellContact
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/updateFlowTasks.do")
    void updateFlowTasks(@RequestBody PlanDto temp, @RequestParam(value = "cellIds",required = false) String cellIds, @RequestParam(value = "parentPlanId",required = false) String parentPlanId
                         , @RequestParam(value = "cellContact",required = false) String cellContact, @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/saveFlowTask2.do")
    FeignJson saveFlowTask2(@RequestBody Map<String, Object> params, @RequestParam(value = "currentUserId",required = false) String currentUserId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doAddInputsNew.do")
    void doAddInputsNew( @RequestParam(value = "ids",required = false) String ids, @RequestBody InputsDto dto ,  @RequestParam(value = "currentUserId",required = false) String currentUserId);
    
    /**
     * 删除输入事务
     * 
     * @param ids
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doBatchDelInputsForWork.do")
    void doBatchDelInputsForWork(@RequestParam(value = "ids",required = false) String ids);
    
    /**
     * 新增交付物-继承父项目输出
     * 事物重构
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doAddInheritDocument.do")
    void doAddInheritDocument(@RequestParam(value = "names",required = false) String names,@RequestBody PlanDto temp);
    
    /**
     * 新增交付物
     * 事物重构
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doAdd.do")
    void doAdd(@RequestParam(value = "type",required = false) String type, @RequestParam(value = "names",required = false)String names
               , @RequestParam(value = "userId",required = false)String userId, @RequestBody List<PlanDto> dtoList);
    
    /**
     * @param ids
     * @param inputs
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doBatchSaveBasicInfo.do")
    void doAddInputs(@RequestParam(value = "ids",required = false) String ids, @RequestBody InputsDto temp);

    /**
     * 批量修改基本信息保存
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/doBatchSaveBasicInfo.do")
    void doBatchSaveBasicInfo(@RequestParam(value = "fromTemplate",required = false) String fromTemplate,@RequestBody PlanDto task, @RequestParam(value = "planStartTime",required = false) String planStartTime,
                              @RequestParam(value = "planEndTime",required = false) String planEndTime,  @RequestParam(value = "workTime",required = false) String workTime
                              ,  @RequestParam(value = "nameChange",required = false) boolean nameChange);

    /**
     * 删除线上的输入输出关系
     * 事务重构
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/deleteLineConnect.do")
    void deleteLineConnect(@RequestBody Map<String,Object> map);

    /**
     * 删除线上的输入输出关系
     * 事务重构
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/deleteLinkInput.do")
    void deleteLinkInput(@RequestBody Map<String, Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/deleteSelectedCell.do")
    void deleteSelectedCell(@RequestParam(value = "parentPlanId",required = false) String parentPlanId,@RequestParam(value = "cellId",required = false) String cellId);

//    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/getProcTemplateEntity.do")
//    TaskProcTemplateDto getProcTemplateEntity(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/saveOutPreposePlan.do")
    void saveOutPreposePlan(@RequestParam(value = "preposeIds",required = false) String preposeIds, @RequestParam(value = "useObjectId",required = false) String useObjectId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/templateResolveForPlan.do")
    boolean templateResolveForPlan(@RequestParam(value = "parentId",required = false) String parentId, @RequestParam(value = "templateId",required = false) String templateId, @RequestParam(value = "currentId",required = false)  String currentId);

    /**
     * 保存流程任务基本信息
     * 事物重构
     * @param paramMap    
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/saveFlowTask1.do")
    void saveFlowTask1(@RequestBody Map<String, Object> paramMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/startChangeApplyProcess.do")
    FeignJson startChangeApplyProcess(@RequestBody PlanownerApplychangeInfoDto dto,@RequestParam(value = "leader",required = false) String leader,
                                      @RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/startChangeApplyForWorkFlow.do")
    FeignJson startChangeApplyForWorkFlow(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/cancelChangeApplyForWorkFlow.do")
    FeignJson cancelChangeApplyForWorkFlow(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/saveFlowTaskForChange1.do")
    List<FlowTaskVo> saveFlowTaskForChange1(@RequestBody Map<String, Object> params);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/saveFlowTaskForChange2.do")
    List<FlowTaskVo> saveFlowTaskForChange2(@RequestBody Map<String, Object> params);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/startPlanChangeforBackProcess.do")
    boolean startPlanChangeforBackProcess(@RequestParam(value = "leader",required = false) String leader, @RequestParam(value = "deptLeader",required = false) String deptLeader,
                                          @RequestBody FlowTaskParentVo flowTaskParent, @RequestParam(value = "userId",required = false) String userId);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/taskFlowResolveRestController/startPlanChange.do")
    FlowTaskOutChangeVO startPlanChange(@RequestParam(value = "userId",required = false) String userId, @RequestParam(value = "leader",required = false) String leader, @RequestParam(value = "deptLeader",required = false) String deptLeader,
                                        @RequestParam(value = "changeType",required = false) String changeType, @RequestParam(value = "changeRemark",required = false) String changeRemark,
                                        @RequestBody Map<String, Object> params);


    
    

}
