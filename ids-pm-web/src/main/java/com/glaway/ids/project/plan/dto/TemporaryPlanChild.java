package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.dto.TSUserDto;
import lombok.Data;


/**
 * 
 * 计划变更-子计划
 * 
 * @author blcao
 * @version 2015年5月7日
 * @see
 * @since
 */
@Data
public class TemporaryPlanChild {

    /**
     * 父计划ID
     */
    private String parentPlanId = null;
    
    /**
     * 父计划名称
     */
    private String parentPlanName = null;
    
    /**
     * 计划ID
     */
    private String planId = null;
    
    /**
     * 计划名称
     */
    private String planName = null;
    
    /**
     * 创建人ID
     */
    private String createBy = null;
    
    /**
     * 创建人名称
     */
    private String createByName = null;
    
    /**
     * 创建人
     */
    private TSUserDto creator;
    
    /**
     * 负责人ID
     */
    private String owner = null;
    
    /**
     * 负责人
     */
    private TSUserDto ownerInfo;
    
    
    /**
     * 负责人名称
     */
    private String ownerName = null;
    
    /**
     * 下达人ID
     */
    private String assigner = null;
    
    
    /**
     * 下达人
     */
    private TSUserDto assignerInfo;
    
    
    
    /**
     * 下达人名称
     */
    private String assignerName = null;
    
    
    /**
     * 开始时间
     */
    private String planStartTime = null;
    
    /**
     * 开始时间是否逾界
     */
    private boolean startTimeOverflow = false;
    
    /**
     * 结束时间
     */
    private String planEndTime = null;
    
    
    /**
     * 结束时间是否逾界
     */
    private boolean endTimeOverflow = false;
    
    /**
     * 允许时间范围
     */
    private String timeAreaTip = null;
}
