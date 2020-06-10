/*
 * 文件名：PlanConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：blcao
 * 修改时间：2015年4月15日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.constant;

/**
 * 计划相关常量定义
 * 
 * @author blcao
 * @version 2015年4月21日
 * @see PlanConstants
 * @since
 */
public class PlanConstants {
    /** 计划新增时、计划ID的临时值前缀 */
    public static final String PLAN_CREATE_UUID = "BLCAO_";

    /** useObjectType WITH PLAN */
    public static final String USEOBJECT_TYPE_PLAN = "PLAN";
    
    /** 判断是否可以下发评审任务的字符串 */
    public static final String TASKNAMETYPE_REVIEW_CHI = "评审";
    
    /** 判断是否可以编制风险清单的字符串 */
    public static final String TASKNAMETYPE_RISK_CHI = "风险";
    
    /** 导出计划mpp表头 */
    public static final String EXPORT_PLAN_NAME = "项目计划信息";

    /** 导出计划模板mpp表头 */
    public static final String EXPORT_PLANTEMPLATE_NAME = "计划模板";

    /** 导出计划mpp表头 */
    public static final String EXPORT_PLAN_NAME_MODEL = "项目计划mpp导入模版";
    
    /** 资源折线图 使用区间 前后天数配置  */
    public static final int RESOURCE_TIMEAREA_ADDDAY = 5;

    
    /*************************************************************/
    /** 下达流程审批时相关URL start **/
    /*************************************************************/
    /** 下达审批时查看 */
    public static final String URL_ASSIGNPROCESS_VIEW = "/ids-pm-web/planController.do?goAssignPlanView&dataHeight="+400+"&dataWidth="+800+"&formId=";
    public static final String URL_FLOWTASK_ASSIGN_VIEW = "/ids-pm-web/taskFlowResolveController.do?goAssignFlowView&formId=";
    
    /** 下达审批时修改 */
    public static final String URL_ASSIGNPROCESS_EDIT = "/ids-pm-web/planController.do?goAssignPlanFlow&dataHeight="+400+"&dataWidth="+800+"&formId=";
    
    /** 单条变更审批时查看 */
    public static final String URL_CHANGEROCESS_VIEW = "/ids-pm-web/planChangeController.do?goChangePlanView&dataHeight="+550+"&dataWidth="+800+"&formId=";

    /** 单条变更审批时修改 */
    public static final String URL_CHANGEPROCESS_EDIT = "/ids-pm-web/planChangeController.do?goChangePlanFlow&dataHeight="+620+"&dataWidth="+800+"&formId=";
    
    
    /** 批量变更审批时查看 */
    public static final String URL_CHANGEMASSPROCESS_VIEW = "/ids-pm-web/planChangeMassController.do?goChangePlanView&dataHeight="+600+"&dataWidth="+800+"&formId=";
    public static final String URL_FLOWTASK_CHANGE_VIEW = "/ids-pm-web/taskFlowResolveController.do?goChangeFlowView&formId=";
    
//    public static final String URL_FLOWTASK_CHANGE_VIEWNEW = "rdfTaskFlowResolveController.do?goChangeFlowView&formId=";
    public static final String URL_FLOWTASK_CHANGE_VIEWNEW = "/ids-pm-web/taskFlowResolveController.do?goChangeFlowView&formId=";
    public static final String URL_FLOWTASK_CHANGE_VIEWPLAN = "/ids-pm-web/taskFlowResolveController.do?goChangeFlow&formId=";
    
    /** 批量变更审批时修改 */
    public static final String URL_CHANGEMASSPROCESS_EDIT = "/ids-pm-web/planChangeMassController.do?goChangePlanMassFlow&dataHeight="+600+"&dataWidth="+800+"&formId=";
    
    
    /** 基线审批时查看 */
    public static final String URL_BASICLINE_VIEW = "/ids-pm-web/basicLineController.do?goBasicLineView&dataHeight="+500+"&dataWidth="+750+"&basicLineId=";

    /** 基线审批时修改 */
    public static final String URL_BASICLINE_EDIT = "/ids-pm-web/basicLineController.do?goBasicLineFlow&dataHeight="+500+"&dataWidth="+750+"&basicLineId=";
    
    /** 变更申请查看 */
    public static final String URL_CHANGE_APPLY_VIEW = "/ids-pm-web/taskFlowResolveController.do?goChangeApplyView&dataHeight="+300+"&dataWidth="+800+"&formId=";
    
    /** 变更申请修改 */
    public static final String URL_CHANGE_APPLY_EDIT = "/ids-pm-web/taskFlowResolveController.do?goChangeApplyEdit&dataHeight="+300+"&dataWidth="+800+"&formId=";

    
    /*************************************************************/
    /** 计划生命周期状态 **/
    /*************************************************************/
    /** 拟制中 */
    public static final String PLAN_EDITING = "EDITING";

    /** 已下达 */
    public static final String PLAN_ORDERED = "ORDERED";

    /** 完工反馈中 */
    public static final String PLAN_FEEDBACKING = "FEEDBACKING";

    /** 已完工 */
    public static final String PLAN_FINISH = "FINISH";
    
    /** 已废弃 */
    public static final String PLAN_INVALID = "INVALID";

    
    /*************************************************************/
    /** 计划流程状态 **/
    /*************************************************************/
    /** 无流程 */
    public static final String PLAN_FLOWSTATUS_NORMAL = "NORMAL";

    /** 下达流程中--已发布 */
    public static final String PLAN_FLOWSTATUS_LAUNCHED = "LAUNCHED";

    /** 下达流程中--待接收 */
    public static final String PLAN_FLOWSTATUS_TOBERECEIVED = "TOBERECEIVED";

    /** 下达流程中 */
    public static final String PLAN_FLOWSTATUS_ORDERED = "ORDERED";

    /** 变更流程中 */
    public static final String PLAN_FLOWSTATUS_CHANGE = "CHANGE";

    /** 完工反馈流程中 */
    public static final String PLAN_FLOWSTATUS_FEEDBACKING = "FEEDBACKING";
    
    /** 冻结 */
    public static final String BASICLINE_FLOWSTATUS_FREEZING = "FREEZING";
    
    /** 启用 */
    public static final String BASICLINE_FLOWSTATUS_USEING = "ENABLE";
    
    /** 审批中 */
    public static final String BASICLINE_FLOWSTATUS_VERFYING = "APPROVING";

    
    /*************************************************************/
    /** 计划日志信息 **/
    /*************************************************************/
    /** 新增计划 */
    public static final String PLAN_LOGINFO_CREATE = "新增计划";

    /** 下达计划 */
    public static final String PLAN_LOGINFO_ORDERED = "发布计划";
    
    /** 子计划下达 */
    public static final String PLAN_LOGINFO_SON_ORDERED = "子计划下达";

    /** 变更计划 */
    public static final String PLAN_LOGINFO_CHANGE = "变更计划";
    
    /** 计划分解 */
    public static final String PLAN_LOGINFO_SPLIT = "计划分解";     // 计划类型为"计划任务"
    
    /** 计划废弃*/
    public static final String PLAN_LOGINFO_DISCARD = "计划废弃";  
    
    /** 流程分解 */
    public static final String PLAN_LOGINFO_FLOW_SPLIT = "流程分解";    // 计划类型为"流程任务" 
    
    /** 撤消分解*/
    public static final String PLAN_DELETE_SPLIT = "撤消流程分解"; 
    
    /** 撤消分解*/
    public static final String PLAN_LOGINFO_DELETE_SPLIT = "撤消分解"; 
    
    /** 下发评审任务*/
    public static final String PLAN_LOGINFO_ORDER_REVIEW_TASK = "下发评审任务"; 

    /**
     * 提交完工反馈<br>
     */
    public static final String PLAN_LOGINFO_FEEDBACK = "提交完工确认";

    /**
     * 完工反馈被驳回<br>
     */
    public static final String PLAN_LOGINFO_FEEDBACK_GOBACK = "完工确认被驳回";
    
    /**
     * 完工反馈被终止<br>
     */
    public static final String PLAN_LOGINFO_FEEDBACK_CANCEL = "完工确认被终止";
    
    /**
     *  根据项目的暂停/恢复设置该项目下计划的暂停/恢复<br>
     */
    public static final String PROJECTATTENTION_CLOSE = "项目关闭，项目下所有计划相关流程终止";
    
    /**
     *  根据项目的暂停/恢复设置该项目下计划的暂停/恢复<br>
     */
    public static final String PROJECTATTENTION_STOP = "项目暂停，项目下所有计划相关流程挂起";
    

    /**
     * 任务完工<br>
     */
    public static final String PLAN_LOGINFO_FINISH = "任务完工";
    
    
    /*************************************************************/
    /** 计划来源 **/
    /*************************************************************/
    
    /** 任务计划分解<br>*/
    public static final String PLAN_SOURCE_TASK_SPLIT = "task_split";

    
    /*************************************************************/
    /** 计划类型 **/
    /*************************************************************/
    /**
     *  任务计划
     */
    public static final String PLAN_TYPE_TASK = "任务计划";
    
    /**
     *  流程计划
     */
    public static final String PLAN_TYPE_FLOW = "流程计划";
    
    /**
     *  WBS计划
     */
    public static final String PLAN_TYPE_WBS = "WBS计划";
    
    
    /*************************************************************/
    /** 流程分解类型 **/
    /*************************************************************/
    /**
     *  手工分解
     */
    public static final String FLOWRESOLVE_TYPE_MANUAL = "manualResolve";
    
    /**
     * 选择研发流程模板
     */
    public static final String FLOWRESOLVE_TYPE_TEMPLATE = "templateResolve";
    
    
    /*************************************************************/
    /** 研发流程模板生命周期 **/
    /*************************************************************/
    /**
     * 已审批
     */
    public static final String TASKPROCTEMPLATE_BIZCURRENT_YISHENPI = "yishenpi";

    /**
     * 启用
     */
    public static final String TASKPROCTEMPLATE_BIZCURRENT_QIYONG = "qiyong";

    /**
     * 禁用
     */
    public static final String TASKPROCTEMPLATE_BIZCURRENT_JINYONG = "jinyong";
    
    
    /*************************************************************/
    /** 计划输出来源 **/
    /*************************************************************/
    /**
     *  标准名称
     */
    public static final String DELIVERABLES_ORIGIN_NAMESTANDARD = "NAMESTANDARD";
    
    
    /*************************************************************/
    /** 流程模板节点输入输出类型 **/
    /*************************************************************/
    /**
     *  输入
     */
    public static final String DELIVERABLES_TYPE_INPUT = "INPUT"; 
    /**
     *  输出
     */
    public static final String DELIVERABLES_TYPE_OUTPUT = "OUTPUT";
    
    
    /*************************************************************/
    /** 计划流程类型 **/
    /*************************************************************/
    /**
     *  流程任务变更
     */
    public static final String PLAN_APPROVE_TYPE_FLOWTASKCHANGE = "流程任务变更";
    
    
    /*************************************************************/
    /** 计划里程碑 **/
    /*************************************************************/
    /**
     * 是
     */
    public static final String PLAN_MILESTONE_TRUE = "是";
    /**
     * 否
     */
    public static final String PLAN_MILESTONE_FALSE = "否";
    

    /****************计划模板状态 start*************************/
    /** 计划模板拟制中状态 */
    public static final String PLANTEMPLATE_NIZHL = "nizhi";
    
    /** 计划模板审批中状态 */
    public static final String PLANTEMPLATE_SHENHE = "shenhe";
    
    /** 计划模板已审批状态 */
    public static final String PLANTEMPLATE_YISHENPI = "yishenpi";
    /****************计划模板状态 end*************************/
    
    /**
     * 流程分解时，session中存储的List<Plan>的Key的前缀
     */
    public static final String FLOWTASK_LIST_KEY = "flowTaskList_";

    /**
     * 流程变更时，session中存储的List<FlowTask>的Key的前缀
     */
    public static final String CHANGE_FLOWTASK_LIST_KEY = "changeFlowTaskList_";

    /**
     * 流程变更时，session中存储的List<ChangeFlowTaskCellConnect>的Key的前缀
     */
    public static final String CHANGE_FLOWTASK_CONNECT_LIST_KEY = "changeFlowTaskConnectList_";

    /**
     * 流程变更时，session中存储的List<FlowTaskPrepose>的Key的前缀
     */
    public static final String FLOWTASK_PREPOSE_LIST_KEY = "flowTaskPreposeList_";

    /**
     * 流程变更时，session中存储的flowTaskParent的Key的前缀
     */
    public static final String FLOWTASK_PARENT_KEY = "flowTaskParent_";
    
    /**
     * session中存储的flowTaskMatchInputs的Key的前缀
     */
    public static final String FLOWTASK_MATCHINPUTS_KEY = "flowTaskMatchInputs_";
    
    /**
     * session中存储的flowTaskChangeMatchInputs的Key的前缀
     */
    public static final String FLOWTASK_CHANGEMATCHINPUTS_KEY = "flowTaskChangeMatchInputs_";
    
    /**
     * 任务名称的常量
     */
    public static final String DEFAULT_TASKNAME = "任务名称";
    
    /** 本地文档 的常量*/
    public static final String LOCAL = "本地文档";
    
    /** 本地文档 的英文常量*/
    public static final String LOCAL_EN = "LOCAL";
    
    /** 外部输入 的英文常量*/
    public static final String DELIEVER_EN = "DELIEVER";
    
    /** 内部输入 的英文常量*/
    public static final String INNERTASK_EN = "INNERTASK";
    
    /** 项目库文档 的常量*/
    public static final String PROJECTLIBDOC = "PROJECTLIBDOC";
    
    /** 变更的英文常量*/
    public static final String CHANGE_EN = "CHANGE";
    
    /** 在驳回的状态 */
    public static final String PLAN_FLOWSTATUS_BACK = "true";
    
}
