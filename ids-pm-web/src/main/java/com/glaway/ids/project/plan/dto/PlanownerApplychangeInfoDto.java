package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Description: 负责人申请变更信息
 * @author: sunmeng
 * @ClassName: PlanownerApplychangeInfoDto
 * @Date: 2019/8/8-16:47
 * @since
 */
@Getter
@Setter
public class PlanownerApplychangeInfoDto extends GLVData {

    private String changeType = null;

    private String changeRemark = null;

    private String planId = null;

    private String launcher = null;

    private Date launchTime = null;
}
