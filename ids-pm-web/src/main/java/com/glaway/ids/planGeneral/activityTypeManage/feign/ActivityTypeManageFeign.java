package com.glaway.ids.planGeneral.activityTypeManage.feign;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.planGeneral.activityTypeManage.dto.ActivityTypeManageDto;
import com.glaway.ids.planGeneral.activityTypeManage.fallback.ActivityTypeManageFeignCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by LHR on 2019/8/26.
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = ActivityTypeManageFeignCallback.class)
public interface ActivityTypeManageFeign {

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/queryEntity.do")
    FeignJson queryEntity(@RequestBody FeignJson feignJson);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/doAddActivityTypeManage.do")
    FeignJson doAddActivityTypeManage(@RequestParam(value = "userId") String userId, @RequestParam(value = "name") String name, @RequestParam(value = "remark") String remark, @RequestParam(value = "id", required = false) String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/doDeleteBatch.do")
    FeignJson doDeleteBatch(@RequestParam(value = "userId") String userId, @RequestParam(value = "ids") String ids);

    /**活动类型批量删除前的校验
     * @param ids
     * wqb 2019年9月5日 17:59:12
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/deleteBatchBeforeCheckDate.do")
    FeignJson deleteBatchBeforeCheckDate(@RequestParam(value = "ids") String ids);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/doStartOrStop.do")
    FeignJson doStartOrStop(@RequestParam(value = "userId") String userId, @RequestParam(value = "ids") String ids, @RequestParam(value = "status") String status);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/queryActivityTypeManageById.do")
    ActivityTypeManageDto queryActivityTypeManageById(@RequestParam(value = "id") String id);

    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/getAllActivityTypeManage.do")
    List<ActivityTypeManageDto> getAllActivityTypeManage(@RequestParam(value = "flag",required = false) Boolean flag);

    /**
     * 获取所有活动类型
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/getAllActivityTypeManages.do")
    List<ActivityTypeManageDto> getAllActivityTypeManage();

    /**
     * 获取所有未删除的活动类型以<id,name>形式返回
     * @return
     */
    @RequestMapping(value = FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/activityTypeManageRestController/getAllActivityTypeManageMap.do")
    Map<String, String> getAllActivityTypeManageMap();
}
