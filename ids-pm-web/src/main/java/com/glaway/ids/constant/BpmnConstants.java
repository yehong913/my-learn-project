/*
 * 文件名：pbmnConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年4月1日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.constant;

/**
 * 流程属性
 * 描述：记录流程相关的属性常量
 *  
 * @author duanpengfei
 * @version 2015年4月1日
 * @see BpmnConstants
 * @since
 */
public class BpmnConstants {
    /** 工作流属性文件*/
    public static final String IDS_PBMN_ADDRESS = "/properties/pbmn.properties";
    
    /*******************processDefinitionKey**************************/
    /** IDS通用流程*/
    public static final String BPMN_IDS = "idsProcess";
    
    /** 启动项目流程*/
    public static final String BPMN_START_PROJECT = "startProjectProcess";
    
    /** 暂停项目流程*/
    public static final String BPMN_PAUSE_PROJECT = "pauseProjectProcess";
    
    /** 恢复项目流程*/
    public static final String BPMN_RESUME_PROJECT = "resumeProjectProcess";
    
    /** 关闭项目流程*/
    public static final String BPMN_CLOSE_PROJECT = "closeProjectProcess";
    
    /** 文档审批流程*/
    public static final String BPMN_SUBMIT_DOCUMENT = "submitDocProcess";
    
    /** 计划模板流程*/
    public static final String BPMN_START_PLANTEMPLATE = "startPlanTemplateProcess";
    
    /** 项目模板流程*/
    public static final String BPMN_START_PROJTEMPLATE = "startProjTemplateProcess";
    
    /** 研发流程模板流程*/
    public static final String BPMN_START_TASKPROCTEMPLATE = "taskProcTemplateProcess";
    
    /** 计划下达流程*/
    public static final String BPMN_START_PLAN = "planAssignProcess";
    
    /** 计划批量下达流程*/
    public static final String BPMN_START_MASS_PLAN = "planAssignMassProcess";
    
    /** 流程任务批量下达流程*/
    public static final String BPMN_START_MASS_FLOWTASK = "flowtaskAssignMassProcess";
    
    /** 计划变更流程*/
    public static final String BPMN_CHANGE_PLAN = "planChangeProcess";
    
    /** 计划批量变更流程*/
    public static final String BPMN_CHANGE_MASS_PLAN = "planChangeMassProcess";
    
    /** 流程任务批量变更流程*/
    public static final String BPMN_CHANGE_MASS_FLOWTASK = "flowtaskChangeMassProcess";
    
    /** 流程任务批量变更流程*/
    public static final String BPMN_CHANGE_MASS_RDTASKFLOWTASK = "rdTaskFlowtaskChangeMassProcess";
    
    /** 任务完成反馈流程*/
    public static final String BPMN_START_TASKFEEDBACK = "taskFeedbackProcess";
    
    /** 基线流程*/
    public static final String BPMN_START_BASICLINE = "basicLineProcess";
    
    /** 计划变更申请流程*/
    public static final String BPMN_CHANGE_APLY = "changeApplyProcess";
    
    /** 风险问题上报流程*/
    public static final String BPMN_RISK_PROBLEM = "riskProblemProcess";
    
    /** 计划模板流程*/
    public static final String BPMN_PLAN_TEMPLATE_ENTITYNAME = "PlanTemplate";
    
    /** 项目模板流程*/
    public static final String BPMN_PROJ_TEMPLATE_ENTITYNAME = "ProjTemplate";
    
    
    public static final String FLOW_PLAN_TEMPLATE_DEFAULT_BUSINESSTYPE = "default";
    
    /*******************processDefinitionKey**************************/
    
    
    /*******************processDefinitionDisplayname**************************/
    /** 启动项目流程*/
    public static final String BPMN_START_PROJECT_DISPLAYNAME = "启动项目流程";
    
    /** 暂停项目流程*/
    public static final String BPMN_PAUSE_PROJECT_DISPLAYNAME = "暂停项目审批";
    
    /** 恢复项目流程*/
    public static final String BPMN_RESUME_PROJECT_DISPLAYNAME = "恢复项目审批";
    
    /** 关闭项目流程*/
    public static final String BPMN_CLOSE_PROJECT_DISPLAYNAME = "关闭项目流程";
    
    /** 文档审批流程*/
    public static final String BPMN_SUBMIT_DOCUMENT_DISPLAYNAME = "文档审批流程";
    
    /** 计划下达流程*/
    public static final String BPMN_START_PLAN_DISPLAYNAME = "计划发布流程";
    
    /** 计划变更流程*/
    public static final String BPMN_CHANGE_PLAN_DISPLAYNAME = "计划变更流程"; 
    
    /** 任务完成反馈流程*/
    public static final String BPMN_FEEDBACK = "任务完工确认流程";
    
    /** 基线流程*/
    public static final String BPMN_START_BASICLINE_DISPLAYNAME = "基线提交";
    
    /** 计划变更申请流程*/
    public static final String BPMN_START_APPLY_CHANGENAME = "变更申请流程";
    
    /** 风险问题上报流程*/
    public static final String BPMN_RISK_PROBLEM_DISPLAYNAME = "问题处理流程";
    
    /*******************processDefinitionDisplayname**************************/
    
    
    /********************OID****************************/
    /** 项目对象的OID*/
    public static final String OID_PROJECT = "com.glaway.ids.project.projectmanager.entity.Project:";
    
    /** 计划对象的OID*/
    public static final String OID_PLAN = "com.glaway.ids.project.plan.entity.Plan:";
    
    /** 批量申请单对象的OID*/
    public static final String OID_APPROVEPLANFORM = "com.glaway.ids.project.plan.entity.ApprovePlanForm:";
    
    /** 申请单对象的OID*/
    public static final String OID_APPROVERDTASKFORM = "com.glaway.ids.rdflow.plan.entity.RDTaskApproveForm:";
    
    /** TaskActityInfo对象的OID*/
    public static final String OID_TASKACTITYINFO = "com.glaway.ids.rdtask.task.pbmn.activity.entity.TaskActityInfo:";
    
    /** 计划模板的OID*/
    public static final String OID_PLANTEMPLATE = "com.glaway.ids.project.plantemplate.entity.PlanTemplate:";
   
    /** 项目模板的OID*/
    public static final String OID_PROJECTTEMPLATE = "com.glaway.ids.project.projecttemplate.entity.ProjTemplate:";
    
    /** 研发流程模板对象的OID*/
    public static final String OID_TASKPROCTEMPLATE = "com.glaway.ids.rdtask.proctemplate.entity.TaskProcTemplate:";
    
    /** 计划基线对象的OID*/
    public static final String OID_BASICLINE = "com.glaway.ids.project.plan.entity.BasicLine:";
    
    /** 变更申请对象的OID*/
    public static final String OID_CHANGEAPPLY = "com.glaway.ids.project.plan.entity.PlanownerApplychangeInfo:";
    
    /********************OID****************************/



    /** 象业务类型流程对应信息业务类型前缀*/
    public static final String OBJECT_BUSINESS_BPMN_LINK_BUSINESSTYPE = "fileType:";


    /** objectBusinessBpmnLink中实体名称*/
    public static final String BPMN_ENTITIY_NAME_PROJECT_FILE = "ProjectFile";

    /** 项目对象的OID*/
    public static final String TASKVO_REPFILETYPE = "REPFILETYPE";


    /** 任务节点选办理人模式（多人）*/
    public static final String TASK_ASSIGN_MODEL_MULTI = "多人";
    
}
