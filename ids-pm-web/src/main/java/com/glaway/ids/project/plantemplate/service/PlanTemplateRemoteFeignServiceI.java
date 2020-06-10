package com.glaway.ids.project.plantemplate.service;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.util.ResourceUtil;
import com.glaway.foundation.core.common.hibernate.qbc.PageList;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;

import com.glaway.ids.project.plan.dto.InputsDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plantemplate.dto.PlanTempOptLogDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDetailDto;
import com.glaway.ids.project.plantemplate.dto.PlanTemplateDto;
import com.glaway.ids.project.plantemplate.fallback.PlanTemplateRemoteFeignServiceCallBack;
import com.glaway.ids.project.plantemplate.support.planTemplate.vo.PlanTemplateReq;
import com.glaway.ids.util.mpputil.MppInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 〈计划模板接口〉
 * 〈功能详细描述〉
 * @author duanpengfei
 * @version 2015年4月22日
 * @see PlanTemplateRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanTemplateRemoteFeignServiceCallBack.class)
public interface PlanTemplateRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/queryEntity.do")
    PageList queryEntity(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/doStatusChange.do")
    FeignJson doStatusChange(@RequestBody PlanTemplateReq planTemplateReq,@RequestParam("planTemplateId") String planTemplateId,
                             @RequestParam("curUserId")String curUserId,@RequestParam("orgId")String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/getPlanTemplateEntity.do")
    PlanTemplateDto getPlanTemplateEntity(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/getTemplatePlanDetailById.do")
    List<PlanTemplateDetailDto> getTemplatePlanDetailById(@RequestParam("planTemplateId") String planTemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/queryInputsListMap.do")
    Map<String,List<InputsDto>> queryInputsListMap();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/checkTemplateNameBeforeSave.do")
    boolean checkTemplateNameBeforeSave(@RequestParam("templateName") String templateName,@RequestParam("planTemplateId") String planTemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/savePlanTemplate.do")
    FeignJson savePlanTemplate(@RequestParam("templateName") String templateName, @RequestParam("planTemplateId") String planTemplateId, @RequestParam("remark") String remark,@RequestParam("type") String type,
                               @RequestParam("curUserId") String curUserId,@RequestParam("orgId") String orgId,@RequestBody List<PlanDto> planList);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/deletePlanTemplate.do")
    FeignJson deletePlanTemplate(@RequestBody PlanTemplateReq planTemplateReq, @RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/updatePlanTemplate.do")
    FeignJson updatePlanTemplate(@RequestBody PlanTemplateReq planTemplateReq,@RequestParam("userId") String userId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/savePlanTemplateDetail.do")
    FeignJson savePlanTemplateDetail(@RequestBody Map<String,Object> paramMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/saveMppInfo.do")
    List<MppInfo> saveMppInfo(@RequestBody PlanTemplateReq planTemplateReq);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/checkData.do")
    FeignJson checkData(@RequestBody Map<String,Object> paramMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/checkData2.do")
    FeignJson checkData2(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/savePlanTemplateDetailByExcel.do")
    FeignJson savePlanTemplateDetailByExcel(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/copyTemplate.do")
    FeignJson copyTemplate(@RequestParam("oldTemplId") String oldTemplId, @RequestParam("newTemplName") String newTemplName, @RequestParam("remark") String remark,
                           @RequestBody TSUserDto userDto,@RequestParam("orgId")String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/findBusinessObjectById.do")
    PlanTemplateDto findBusinessObjectById(@RequestParam("planTemplateId")String planTemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/backVesion.do")
    FeignJson backVesion(@RequestBody PlanTemplateDto planTemplateDto,@RequestParam("userId") String userId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/revokeVesion.do")
    FeignJson revokeVesion(@RequestBody PlanTemplateDto planTemplateDto,@RequestParam("userId") String userId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/startPlanTemplateProcess.do")
    FeignJson startPlanTemplateProcess(@RequestBody Map<String,Object> paramMap);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/saveTemplateOptLog.do")
    FeignJson saveTemplateOptLog(@RequestParam("bizId") String bizId, @RequestParam("logInfo") String logInfo,@RequestBody TSUserDto userDto,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/completePlanTemplateProcess.do")
    FeignJson completePlanTemplateProcess(@RequestParam("taskId") String taskId, @RequestBody PlanTemplateDto planTemplateDto,@RequestParam("userId") String userId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/getVersionHistoryAndCount.do")
    FeignJson getVersionHistoryAndCount(@RequestParam("bizId") String bizId, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/findPlanTempOptLogById.do")
    List<PlanTempOptLogDto> findPlanTempOptLogById(@RequestParam("bizId") String bizId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/savePlanTemplateByPlanject.do")
    FeignJson savePlanTemplateByPlanject(@RequestParam(value = "projectId",required = false) String projectId,@RequestParam(value = "planId",required = false) String planId,
                                         @RequestBody PlanTemplateDto planTemplate,@RequestParam("userId") String userId,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/importPlanTemplateMpp.do")
    FeignJson importPlanTemplateMpp(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/doDeletePlanTemplate.do")
    FeignJson doDeletePlanTemplate(@RequestParam(value = "ids",required = false) String ids,
                                   @RequestParam(value = "planTemplateId",required = false) String planTemplateId, @RequestParam(value = "projTemplateId",required = false) String projTemplateId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planTemplateRestController/getImportDataList.do")
    FeignJson getImportDataList(@RequestBody List<Map<String,String>> map,@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "planTemplateId",required = false) String planTemplateId,@RequestParam(value = "orgId",required = false) String orgId);

}
