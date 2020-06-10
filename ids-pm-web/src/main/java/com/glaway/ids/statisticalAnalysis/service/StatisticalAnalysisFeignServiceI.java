package com.glaway.ids.statisticalAnalysis.service;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.common.constant.FeignConstants;
import com.glaway.ids.statisticalAnalysis.fallback.StatisticalAnalysisFeignServiceFallback;
import com.glaway.ids.statisticalAnalysis.vo.DelayTaskVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectAnalysisVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectBoardReportDataVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectBoardVo;
import com.glaway.ids.statisticalAnalysis.vo.planChangeAnalysisVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Description: 统计分析接口
 * @author: sunmeng
 * @ClassName: StatisticalAnalysisFeignServiceI
 * @Date: 2019/8/16-16:31
 * @since
 */
@FeignClient(value = FeignConstants.ID_PM_SERVICE,fallbackFactory = StatisticalAnalysisFeignServiceFallback.class)
public interface StatisticalAnalysisFeignServiceI {

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getProjectBoardVoList.do")
    List<ProjectBoardVo> getProjectBoardVoList(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "page",required = false) int page,
                                                     @RequestParam(value = "rows",required = false) int rows,@RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getProjectBoardVoListSize.do")
    int getProjectBoardVoListSize(@RequestParam(value = "userId",required = false) String userId,@RequestParam(value = "page",required = false) int page,
                                                     @RequestParam(value = "rows",required = false) int rows,@RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getMilestoneVoList.do")
    List<ProjectAnalysisVo> getMilestoneVoList(@RequestParam(value = "projectId",required = false) String projectId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getWBSCompleteRateVo.do")
    FeignJson getWBSCompleteRateVo(@RequestParam(value = "pid",required = false) String pid, @RequestParam(value = "type",required = false) String type,
                                   @RequestParam(value = "year",required = false) String year, @RequestParam(value = "month",required = false) String month);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getCompleteRateVo.do")
    FeignJson getCompleteRateVo(@RequestParam(value = "pid",required = false) String pid, @RequestParam(value = "type",required = false) String type,
                                @RequestParam(value = "year",required = false) String year, @RequestParam(value = "month",required = false) String month);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getMonthRateVoList.do")
    FeignJson getMonthRateVoList(@RequestParam(value = "projectId",required = false) String projectId,@RequestParam(value = "year",required = false) String year);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getDelayTaskVoList.do")
    List<DelayTaskVo> getDelayTaskVoList(@RequestParam(value = "pid",required = false) String pid, @RequestParam(value = "page",required = false) int page,
                                         @RequestParam(value = "rows",required = false) int rows, @RequestParam(value = "isPage",required = false) boolean isPage);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getYearCombobox.do")
    FeignJson getYearCombobox();

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getProjectCombobox.do")
    FeignJson getProjectCombobox(@RequestParam(value = "orgId",required = false) String orgId);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getProjectBoardReportData.do")
    List<ProjectBoardReportDataVo> getProjectBoardReportData(@RequestParam(value = "projectId",required = false) String projectId,@RequestParam(value = "planLevel",required = false) String planLevel);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/searchlaborLoadList.do")
    FeignJson searchlaborLoadList(@RequestBody Map<String, Object> params);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getLaborLoadListCharts.do")
    FeignJson getLaborLoadListCharts(@RequestBody Map<String, Object> params);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/getPlanChangeHighchartsInfo.do")
    FeignJson getPlanChangeHighchartsInfo(@RequestParam(value = "condition",required = false) String condition,
                                          @RequestParam(value = "conditionForManager",required = false) String conditionForManager,
                                          @RequestParam(value = "type",required = false) String type);

    @RequestMapping(FeignConstants.IDS_PM_FEIGN_SERVICE+"/feign/statisticalAnalysisRestController/conditionSearch.do")
    List<planChangeAnalysisVo> conditionSearch(@RequestParam(value = "condition",required = false) String condition,
                                               @RequestParam(value = "conditionForManager",required = false) String conditionForManager,
                                               @RequestParam(value = "page", required = false) int page,
                                               @RequestParam(value = "rows", required = false) int rows);
}
