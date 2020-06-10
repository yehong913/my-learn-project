package com.glaway.ids.statisticalAnalysis.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: 人员负载分析vo
 * @author: sunmeng
 * @ClassName: LaborLoadVo
 * @Date: 2019/12/5-10:27
 * @since
 */
@Getter
@Setter
public class LaborLoadVo implements Serializable {
    /**
     * 负责人
     */
    private String ownerName;

    /**
     * 项目编号
     */
    private String projectNumber;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划进度
     */
    private String progressRate;

    /**
     * 计划状态
     */
    private String status;

    /**
     * 计划等级
     */
    private String planlevel;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 下达人
     */
    private String assignerName;

    /**
     * 下达时间
     */
    private String assignTime;

    /**
     * 计划类别
     */
    private String planType;
}
