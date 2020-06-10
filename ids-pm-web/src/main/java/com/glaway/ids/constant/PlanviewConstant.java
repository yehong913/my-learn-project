package com.glaway.ids.constant;

/**
 * 计划视图
 * @author likaiyong
 *
 */
public class PlanviewConstant {

    /** 公共视图 */
    public static final String PUBLIC = "公共视图";
    
    /** 私有视图 */
    public static final String PRIVATE = "私有视图";
    
    /** 更新视图*/
    public static final String UPDATE = "更新视图";
    
    /** 另存为视图*/
    public static final String SAVEAS = "另存为视图";
    
    /** 按部门设置*/
    public static final String SETBY_DEPARTMENT = "按部门设置";
    
    /** 按时间设置*/
    public static final String SETBY_TIME = "按时间设置";
    
    /** 自定义设置*/
    public static final String SELFDEFINE = "自定义设置";
    
    /** 管理视图*/
    public static final String MANAGEMENT = "管理视图";
    
    /** 视图状态-公共*/
    public static final String PUBLIC_STATUS = "PUBLIC";
    
    /** 视图状态-私有*/
    public static final String PRIVATE_STATUS = "PRIVATE";
    
    /** 当前用户 */
    public static final String SYSTEM_USER = "systemUser";
    
    /** 当前年 */
    public static final String SYSTEM_YEAR = "systemYear";
    
    /** 当前月份*/
    public static final String SYSTEM_MONTH = "systemMonth";
    
    /** 当前季度 */
    public static final String SYSTEM_QUARTER = "systemQuarter";
    
    /** 计划视图页面入口*/
    public static final String PAGE_NAME = "PLANVIEW";
    
    /** 计划视图关联项目*/
    public static final String APPLY_PROJECT = "applyProject";
    
    /** 计划视图关联用户*/
    public static final String APPLY_USER = "applyProject";
    
    /** 计划视图关联展示列*/
    public static final String LINK_COLUMN = "planNo,planLevel,planType,planTaskType,status,owner,"
                                             + "planStartTime,planEndTime,assigner,assignTime,"
                                             + "workTime,preposePlan,mileStone,creator,createTime";
    /** 计划展示列*/
    public static final String LINK_COLUMN_SHOW = "planNumber,progressRate,optBtn,planName,planLevelInfo,taskNameTypeDisplay,taskType,bizCurrentInfo,ownerInfo,"
        + "planStartTime,planEndTime,assignerInfo,assignTime,"
        + "workTime,preposePlans,milestone,creator,createTime";
    
    /** 计划视图关联展示列*/
    public static final String SHOW_HEADER = "编号,进度,操作,计划名称,计划等级,计划类型,计划类别,状态,负责人,开始时间,结束时间,发布人,发布时间,工期(天),前置计划,里程碑,创建者,创建时间";
    
    public static final String SHOW_HEADERS = "编号,计划等级,计划类型,计划类别,状态,负责人,开始时间,结束时间,发布人,发布时间,工期(天),前置计划,里程碑,创建者,创建时间";
    
}
