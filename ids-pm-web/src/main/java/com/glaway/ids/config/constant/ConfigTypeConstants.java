/*
 * 文件名：ConfigTypeConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年4月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.constant;

/**
 * 配置类型常量
 * 
 * @author xshen
 * @version 2015年3月25日
 * @see ConfigTypeConstants
 * @since
 */
public class ConfigTypeConstants {
    /** 项目阶段 */
    public static final String PHARSE = "PHARSE";

    /** 计划等级 */
    public static final String PLANLEVEL = "PLANLEVEL";
    
    /** 计划变更类别 */
    public static final String PLANCHANGECATEGORY = "PLANCHANGECATEGORY";
    
    /** 交付项名称 */
    public static final String DELIVER_STANDARDNAME = "DELIVERSTANDARDNAME";
    
    /** 负责人名称*/
    public static final String OWNERNAME = "OWNERNAME";
    
    /** 前置缓存 */
    public static final String PREPOSECACHE = "PREPOSECACHE";

    /** 任务类型 */
    public static final String TASK_NAME_TYPE= "TASKNAMETYPE";

    /**
     * 根据类型获取显示名称
     * 
     * @param type
     * @return
     * @see
     */
    public static String getName(String type) {
        String name = "";
        switch (type) {
            case PHARSE:
                name = "项目阶段";
                break;
            case PLANLEVEL:
                name = "计划等级";
                break;
            case PLANCHANGECATEGORY:
                name = "计划变更类别";
                break;
            default:
                break;
        }
        return name;
    }

}
