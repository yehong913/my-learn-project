package com.glaway.ids.project.plan.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.glaway.foundation.common.vo.ConditionVO;
import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.project.plan.dto.BasicLineDto;
import com.glaway.ids.project.plan.dto.BasicLinePlanDto;
import com.glaway.ids.project.plan.fallback.FeignBasicLineServiceCallback;

/**
 * Created by LHR on 2019/8/12.
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = FeignBasicLineServiceCallback.class)
public interface FeignBasicLineServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/getLifeCycleStatusList.do")
    String getLifeCycleStatusList();


    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/getBasicLineEntity.do")
    BasicLineDto getBasicLineEntity(@RequestParam(value = "id") String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/getBasicLinePlanEntity.do")
    BasicLinePlanDto getBasicLinePlanEntity(@RequestParam(value = "id") String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/queryBasicLinePlanList.do")
    List<BasicLinePlanDto> queryBasicLinePlanList(@RequestBody BasicLinePlanDto basicLinePlan, @RequestParam(value = "page") int page, @RequestParam(value = "rows")int rows, @RequestParam(value = "isPage")boolean isPage);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/changePlansToJSONObjects.do")
    List<JSONObject> changePlansToJSONObjects(@RequestBody List<BasicLinePlanDto> validList);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/saveBasicLine.do")
    FeignJson saveBasicLine(@RequestBody BasicLineDto basicLine, @RequestParam(value = "ids",required = false)String ids, @RequestParam(value = "basicLineName",required = false)String basicLineName, @RequestParam(value = "remark",required = false)String remark, @RequestParam(value = "type",required = false)String type, @RequestParam(value = "projectId",required = false)String projectId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/deleteBasicLine.do")
    void deleteBasicLine(@RequestBody BasicLineDto basicLine);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/doFrozeBasicLine.do")
    FeignJson doFrozeBasicLine(@RequestParam(value = "id") String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/doUseBasicLine.do")
    FeignJson doUseBasicLine(@RequestParam(value = "id") String id);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/startBasicLine.do")
    FeignJson startBasicLine(@RequestParam(value = "basicLineId") String basicLineId,@RequestParam(value = "leader") String leader, @RequestParam(value = "deptLeader")String deptLeader,@RequestParam(value = "userId")String userId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/startBasicLineFlow.do")
    FeignJson startBasicLineFlow(@RequestBody BasicLineDto basicLine,@RequestParam(value = "basicLineId") String basicLineId, @RequestParam(value = "taskId")String taskId,@RequestParam(value = "basicLineName")String basicLineName,@RequestParam(value = "remark")String remark);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/basicLineRestController/searchDatagrid.do")
    FeignJson searchDatagrid(@RequestBody List<ConditionVO> conditionList, @RequestParam(value = "projectId",required = false) String projectId, @RequestParam(value = "userName",required = false)String userName);


//    void initBusinessBasicLine(BasicLineDto b);
}
