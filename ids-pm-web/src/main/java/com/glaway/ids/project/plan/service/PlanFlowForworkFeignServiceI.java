package com.glaway.ids.project.plan.service;

import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.foundation.fdk.dev.service.threemember.FeignUserService;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.*;
import com.glaway.ids.project.plan.fallback.PlanFlowForworkFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.dto.ResourceLinkInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: PlanFlowForworkFeignServiceI
 * @Date: 2019/8/6-20:03
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = PlanFlowForworkFeignServiceCallBack.class)
public interface PlanFlowForworkFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/modifyResourceMassForWork.do")
    FeignJson modifyResourceMassForWork(@RequestBody List<ResourceLinkInfoDto> resourceLst, @RequestParam(value = "useRates",required = false) String useRates,
                                        @RequestParam(value = "endTimes",required = false) String endTimes,@RequestParam(value = "startTimes",required = false) String startTimes);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/doBatchDel.do")
    FeignJson doBatchDel(@RequestParam(value = "ids",required = false) String ids);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/deleteChildPlan.do")
    void deleteChildPlan(@RequestBody PlanDto plan ,@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/saveModifyListForWork.do")
    FeignJson saveModifyListForWork(@RequestBody FeignJson feignJson);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/getTempPlanListForWork.do")
    List<TemporaryPlanDto> getTempPlanListForWork(@RequestBody FeignJson feignJson);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/startPlanChangeMassForWork.do")
    void startPlanChangeMassForWork(@RequestBody FeignJson feignJson);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/copyBasicLineForWork.do")
    void copyBasicLineForWork(@RequestBody BasicLineDto basicLine, @RequestParam(value = "ids") String ids, @RequestParam(value = "projectId")String projectId, @RequestParam(value = "basicLineIdForCopy")String basicLineIdForCopy);


    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/doAddDelDeliverForWork.do")
    FeignJson doAddDelDeliverForWork(@RequestParam("names") String names,@RequestBody DeliverablesInfoDto deliverablesInfo);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/doAddInheritlistForTemplate.do")
    FeignJson doAddInheritlistForTemplate(@RequestParam("names") String names, @RequestParam("planIdForInherit") String planIdForInherit,
                                          @RequestParam("useObjectType") String useObjectType, @RequestBody TSUserDto userDto,@RequestParam("orgId") String orgId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/getChangeTaskIdByFormId.do")
    String getChangeTaskIdByFormId(@RequestParam(value = "formId") String formId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/getResourceLinkInfosByProject.do")
    List<ResourceLinkInfoDto> getResourceLinkInfosByProject(@RequestParam(value = "projectId") String projectId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/startPlanChangeFlowForWork.do")
    void startPlanChangeFlowForWork(@RequestBody FeignJson feignJson);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/planFlowForworkRestController/doAddInheritlist.do")
    void doAddInheritlist(@RequestParam(value = "names")String names,@RequestParam(value = "planIdForInherit")String planIdForInherit,
                          @RequestBody TSUserDto curUser,@RequestParam(value = "orgId")String orgId);

}
