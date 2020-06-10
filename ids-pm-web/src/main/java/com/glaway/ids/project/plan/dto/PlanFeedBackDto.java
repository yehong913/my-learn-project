package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: PlanFeedBackDto
 * @Date: 2019/10/16-14:08
 * @since
 */
@Data
public class PlanFeedBackDto extends GLVData {
    //生命周期状态
    private String lifeCycleStatus;

    //权重占比
    private String weightPercent;
}
