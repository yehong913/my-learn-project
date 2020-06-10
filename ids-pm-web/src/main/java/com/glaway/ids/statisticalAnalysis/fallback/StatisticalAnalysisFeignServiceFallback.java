package com.glaway.ids.statisticalAnalysis.fallback;

import com.glaway.foundation.fdk.dev.common.FeignJson;
import com.glaway.ids.statisticalAnalysis.service.StatisticalAnalysisFeignServiceI;
import com.glaway.ids.statisticalAnalysis.vo.DelayTaskVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectAnalysisVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectBoardReportDataVo;
import com.glaway.ids.statisticalAnalysis.vo.ProjectBoardVo;
import com.glaway.ids.statisticalAnalysis.vo.planChangeAnalysisVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: StatisticalAnalysisFeignServiceFallback
 * @Date: 2019/8/16-16:32
 * @since
 */
@Component
public class StatisticalAnalysisFeignServiceFallback implements FallbackFactory<StatisticalAnalysisFeignServiceI> {
    @Override
    public StatisticalAnalysisFeignServiceI create(Throwable throwable) {
        return new StatisticalAnalysisFeignServiceI() {
            @Override
            public List<ProjectBoardVo> getProjectBoardVoList(String userId, int page, int rows, boolean isPage) {
                return null;
            }

            @Override
            public int getProjectBoardVoListSize(String userId, int page, int rows, boolean isPage) {
                return 0;
            }

            @Override
            public List<ProjectAnalysisVo> getMilestoneVoList(String projectId) {
                return null;
            }

            @Override
            public FeignJson getWBSCompleteRateVo(String pid, String type, String year, String month) {
                return new FeignJson();
            }

            @Override
            public FeignJson getCompleteRateVo(String pid, String type, String year, String month) {
                return new FeignJson();
            }

            @Override
            public List<ProjectBoardReportDataVo> getProjectBoardReportData(String projectId, String planLevel) {
                return null;
            }

            @Override
            public FeignJson getMonthRateVoList(String projectId, String year) {
                return new FeignJson();
            }

            @Override
            public List<DelayTaskVo> getDelayTaskVoList(String pid, int page, int rows, boolean isPage) {
                return null;
            }

            @Override
            public FeignJson getYearCombobox() {
                return new FeignJson();
            }

            @Override
            public FeignJson getProjectCombobox(String orgId) {
                return new FeignJson();
            }

            @Override
            public FeignJson searchlaborLoadList(Map<String, Object> params) {
                return new FeignJson();
            }

            @Override
            public FeignJson getLaborLoadListCharts(Map<String, Object> params) {
                return new FeignJson();
            }

            @Override
            public FeignJson getPlanChangeHighchartsInfo(String condition, String conditionForManager ,String type) {
                return new FeignJson();
            }

            @Override
            public List<planChangeAnalysisVo> conditionSearch(String condition, String conditionForManager, int page, int rows) {
                return null;
            }
        };
    }
}
