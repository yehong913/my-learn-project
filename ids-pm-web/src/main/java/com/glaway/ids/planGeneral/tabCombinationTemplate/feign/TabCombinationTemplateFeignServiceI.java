package com.glaway.ids.planGeneral.tabCombinationTemplate.feign;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.planGeneral.tabCombinationTemplate.dto.TabCombinationTemplateDto;
import com.glaway.ids.planGeneral.tabCombinationTemplate.fallback.TabCombinationTemplateFeignServiceCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 页签组合模板Feign
 * @author: sunmeng
 * @ClassName: TabCombinationTemplateFeignServiceI
 * @Date: 2019/8/29-17:56
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = TabCombinationTemplateFeignServiceCallback.class)
public interface TabCombinationTemplateFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/isActivityTypeManageUse.do")
    FeignJson isActivityTypeManageUse(@RequestParam(value = "id",required = false) String id, @RequestParam(value = "templateId",required = false) String templateId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/saveTabCbTemplateInfo.do")
    FeignJson saveTabCbTemplateInfo(@RequestBody Map<String,Object> param);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/updateTabCbTemplateInfo.do")
    FeignJson updateTabCbTemplateInfo(@RequestBody Map<String,Object> param);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/backSaveAndSubmit.do")
    FeignJson backSaveAndSubmit(@RequestBody Map<String,Object> param);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/queryEntity.do")
    FeignJson queryEntity(@RequestBody Map<String,Object> params);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/findTabCbTempById.do")
    FeignJson findTabCbTempById(@RequestParam(value = "id",required = false) String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/getCombTemplateInfos.do")
    FeignJson getCombTemplateInfos(@RequestParam(value = "tabCbTemplateId",required = false) String tabCbTemplateId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/getCombTemplateInfosByPlanId.do")
    FeignJson getCombTemplateInfosByPlanId(@RequestParam(value = "planId",required = false) String planId,@RequestParam(value = "activityId",required = false) String activityId,@RequestParam(value = "displayAccess",required = false) String displayAccess);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/doBatchDel.do")
    FeignJson doBatchDel(@RequestParam(value = "ids",required = false) String ids);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/doStatusChange.do")
    FeignJson doStatusChange(@RequestParam(value = "ids",required = false) String ids,
                             @RequestParam(value = "status",required = false) String status,@RequestParam(value = "userId",required = false) String userId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/findTabCbTempByActivityId.do")
    List<TabCombinationTemplateDto> findTabCbTempByActivityId(@RequestParam(value = "activityId",required = false) String activityId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/findTabCbTempByPlanId.do")
    TabCombinationTemplateDto findTabCbTempByPlanId(@RequestParam(value = "planId",required = false) String planId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/queryAllName.do")
    List<String> queryAllName();

    /**
     * 页签组合模板提交审批
     * @param map
     * @return
     */
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/doSubmitApprove.do")
    FeignJson doSubmitApprove(@RequestBody Map<String,String> map);

    /**
     * 获取历史信息
     * @param bizId    版本id
     * @param pageSize 页码
     * @param pageNum  每页数量
     * @return
     */
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/getVersionDatagridStr.do")
    FeignJson getVersionDatagridStr(@RequestParam(value = "bizId",required = false) String bizId, @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("pageNum") Integer pageNum);

    /**
     * 版本回退
     * @param params templateId,版本id,版本号，回退方式(回退&撤消)
     * @return
     */
    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/backVersion.do")
    FeignJson backVersion(@RequestBody Map<String,String> params);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/tabCombinationTemplateRestController/getLifeCycleStatusList.do")
    FeignJson getLifeCycleStatusList();
}
