package com.glaway.ids.rdtask.task.service;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.rdtask.task.fallback.RdFlowTaskFlowResolveWebRemoteFeignServiceCallBack;
import com.glaway.ids.rdtask.task.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 分解
 * 
 * @author wqb
 * @version 2019年7月29日 21:43:16
 * @see RdFlowTaskFlowResolveRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_RDFLOW_SERVICE,fallbackFactory = RdFlowTaskFlowResolveWebRemoteFeignServiceCallBack.class)
public interface RdFlowTaskFlowResolveRemoteFeignServiceI {
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/procTemplateRestController/getProcTemplateAllList.do")
    List<TaskProcTemplateVo> getProcTemplateAllList(@RequestParam(value = "createOrgId",required = false) String createOrgId , @RequestParam(value = "nameValue",required = false) String nameValue
        ,@RequestParam(value = "pageValue",required = false) String pageValue,@RequestParam(value = "rowsKey",required = false) String rowsKey);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getXmlbyPlanId.do")
    RDTaskVO getXmlbyPlanId(@RequestParam(value = "planId",required = false) String planId);
        
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/doDeloutputByPlanOutputId.do")
    void doDeloutputByPlanOutputId(@RequestParam(value = "id",required = false) String id);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/isHaveLinkPlanId.do")
    FeignJson isHaveLinkPlanId(@RequestParam(value = "parentPlanId",required = false) String parentPlanId);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getSaveRdTaskInfo.do")
    void getSaveRdTaskInfo(@RequestBody RDTaskVO vo,@RequestParam(value = "templateId",required = false) String templateId,@RequestParam(value = "outUserId",required = false) String outUserId,@RequestParam(value = "approveType",required = false) String approveType,
                           @RequestParam(value = "procInstId",required = false) String procInstId , @RequestParam(value = "formId",required = false) String formId,@RequestParam(value = "planMapStr",required = false) String planMapStr
                           ,@RequestParam(value = "inputMapStr",required = false) String inputMapStr,@RequestParam(value = "outputMapStr",required = false) String outputMapStr);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getDoUpdateXml.do")
    void getDoUpdateXml(@RequestBody Map<String, String> in,@RequestParam(value = "userId") String userId, @RequestParam(value = "parentPlanId") String parentPlanId
                        , @RequestParam(value = "cellIds") String cellIds,@RequestParam(value = "cellContact") String cellContact);
    
    /**
     * 保存流程任务基本信息
     * 事物重构
     * @param paramMap    
     */
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/saveFlowTask1.do")
    String saveFlowTask1(@RequestBody Map<String, Object> paramMap);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getSaveTaskInfoFromPlan1.do")
    void getSaveTaskInfoFromPlan1(@RequestParam(value = "userId") String userId,@RequestParam(value = "parentPlanId") String parentPlanId,@RequestParam(value = "planName") String planName,
                                  @RequestParam(value = "owner") String owner,@RequestParam(value = "remark") String remark,
                                  @RequestParam(value = "planId") String planId,@RequestParam(value = "workTime") String workTime,@RequestParam(value = "cellId") String cellId);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getParentPlanIdByFlowTaskId.do")
    FeignJson getParentPlanIdByFlowTaskId(@RequestParam(value = "flowTaskId") String flowTaskId);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getPlanIdByFlowTaskParentId.do")
    FeignJson getPlanIdByFlowTaskParentId(@RequestParam(value = "flowTaskParentId") String flowTaskParentId);
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/saveRdfInputByPlan.do")
    void saveRdfInputByPlan(@RequestParam(value = "cellAndNameInputStr") String cellAndNameInputStr,@RequestParam(value = "parentPlanId") String parentPlanId,
                            @RequestParam(value = "cellId") String cellId, @RequestParam(value = "userId")String userId);

    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getFlowTaskDeliverablesInfoInitBusinessObject.do")
    FeignJson getFlowTaskDeliverablesInfoInitBusinessObject();

    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getProcTemplateEntity.do")
    TaskProcTemplateDto getProcTemplateEntity(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/startAssignProcess.do")
    RDTaskVO startAssignProcess(@RequestBody TSUserDto actor, @RequestParam(value = "leader") String leader, @RequestParam(value = "deptLeader")String deptLeader
                                , @RequestParam(value = "parentPlanId") String parentPlanId, @RequestParam(value = "type") String type);    
    
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/getFlowTaskParent.do")
    FlowTaskParentVo getFlowTaskParent(@RequestParam(value = "parentPlanId")  String parentPlanId);

    @RequestMapping(FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/procCategoryRestController/getProcCategoryTree.do")
    FeignJson getProcCategoryTree();

    /**
     * 获取查询数据
     * wqb 2019年6月3日 15:58:26
     */
    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/procTemplateRestController/getConditionSearch.do")
    Map<String, Object> getConditionSearch(@RequestBody Map<String, Object> params);

    @RequestMapping(value = FeignConstants.IDS_RDFLOW_FEIGN_SERVICE+"/feign/rdfTaskFlowResolveRestController/queryFlowTaskCellConnectVoListForTargetId.do")
    List<FlowTaskCellConnectVo> queryFlowTaskCellConnectVoListForTargetId(@RequestParam(value = "parentPlanId") String parentPlanId , @RequestParam(value = "cellId") String cellId);

}
