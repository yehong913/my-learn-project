package com.glaway.ids.project.plan.service;


import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.DeliverablesInfoDto;
import com.glaway.ids.project.plan.dto.PlanDto;
import com.glaway.ids.project.plan.fallback.DeliverablesInfoRemoteFeignServiceCallBack;
import com.glaway.ids.project.projectmanager.dto.ProjDocRelationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


/**
 * 任务输入
 * 
 * @author blcao
 * @version 2015年7月6日
 * @see DeliverablesInfoRemoteFeignServiceI
 * @since
 */

@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = DeliverablesInfoRemoteFeignServiceCallBack.class)
public interface DeliverablesInfoRemoteFeignServiceI {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/queryDeliverableList.do")
    List<DeliverablesInfoDto> queryDeliverableList(@RequestBody DeliverablesInfoDto deliverablesInfo, @RequestParam("page") int page,
                                                   @RequestParam("rows") int rows,@RequestParam("isPage") boolean isPage);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getCount.do")
    long getCount(@RequestBody DeliverablesInfoDto deliverablesInfo);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/deleteDeliverablesById.do")
    void deleteDeliverablesById(@RequestParam("id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/initDeliverablesInfo.do")
    String initDeliverablesInfo(@RequestBody DeliverablesInfoDto deliverablesInfoDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/saveDeliverablesInfo.do")
    void saveDeliverablesInfo(@RequestBody DeliverablesInfoDto deliverablesInfoDto);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/doAddDelDeliverForWork.do")
    void doAddDelDeliverForWork(@RequestParam("names") String names,@RequestBody DeliverablesInfoDto deliverablesInfo);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/doBatchDelDeliverForWork.do")
    void doBatchDelDeliverForWork(@RequestParam("ids") String ids);
    
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getDeliverablesInfoEntity.do")
    DeliverablesInfoDto getDeliverablesInfoEntity(@RequestParam("ids") String id);

    /**
     * 获取前置计划的输出
     * 
     * @param preposeIds
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getPreposePlanDeliverables.do")
    List<DeliverablesInfoDto> getPreposePlanDeliverables(@RequestParam("preposeIds") String preposeIds);
    
    /**
     * 根据useObjectType、useObjectId查找相关交付物
     * 
     * @param useObjectType
     * @param useObjectId
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getDeliverablesByUseObeject.do")
    List<DeliverablesInfoDto> getDeliverablesByUseObeject(@RequestParam("useObjectType") String useObjectType, @RequestParam("useObjectId") String useObjectId);
    
    /**
     * 获取输入增加时所选择的deliverablesInfo的交付物信息
     * @param ids
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getSelectedPreposePlanDeliverables.do")
    List<DeliverablesInfoDto> getSelectedPreposePlanDeliverables(@RequestParam("ids") String ids);

    /**
     * 删除计划的输入、输出、删除计划输入相关的输入
     * @param planId
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/deleteDeliverablesByPlanId.do")
    void deleteDeliverablesByPlanId(@RequestParam("planId")String planId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getAllDeliverablesByUseObeject.do")
    List<DeliverablesInfoDto> getAllDeliverablesByUseObeject(@RequestParam("parentPlanId") String parentPlanId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getJudgePlanAllDocumantWithStatus.do")
    Integer getJudgePlanAllDocumantWithStatus(@RequestBody PlanDto plan, @RequestParam("isOut")  String isOut);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/listView.do")
    FeignJson listView(@RequestBody Map<String,Object> map);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/initBusinessObject.do")
    void initBusinessObject(@RequestBody DeliverablesInfoDto document);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/queryFinishDeliverable.do")
    Map<String, ProjDocRelationDto> queryFinishDeliverable(@RequestParam(value = "planId",required = false) String planId);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/updateDeliverablesInfo.do")
    FeignJson updateDeliverablesInfo(@RequestBody DeliverablesInfoDto deliverablesInfo);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/updateDeliverablesInfoByPlm.do")
    FeignJson updateDeliverablesInfoByPlm(@RequestBody DeliverablesInfoDto deliverablesInfo);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/deliverablesInfoRestController/getDeliverablesByProject.do")
    List<DeliverablesInfoDto> getDeliverablesByProject(@RequestParam(value = "projectId") String projectId);
}
