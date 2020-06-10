/*
 * 文件名：CommonConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：xshen
 * 修改时间：2015年3月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.constant;

/**
 * 通用常量
 * 
 * @author duanpengfei
 * @version 2015年4月8日
 * @see CommonConstants
 * @since
 */
public class CommonConstants {

    /**
     * 计划模板交付项类型
     */
    public static final String DELIVERABLE_TYPE_PLANTEMPLATE = "PLANTEMPLATE";

    /**
     * 项目模板交付项类型
     */
    public static final String DELIVERABLE_TYPE_PROJECTTEMPLATE = "PROJECTTEMPLATE";

    /**
     * 计划交付项类型
     */
    public static final String DELIVERABLE_TYPE_PLAN = "PLAN";

    /**
     * 判断是否
     */
    public static final String FALSE = "false";

    /**
     * 判断是否
     */
    public static final String TRUE = "true";

    /**
     * 中文是否
     */
    public static final String CTRUE = "是";

    /**
     * 中文是否
     */
    public static final String CFALSE = "否";

    
    /*************************************************************/
    /** 是否存在流程 **/
    /*************************************************************/
    /**
     * 存在
     */
    public static final String FLOW_FLAG_EXIST = "1";

    /**
     * 不存在
     */
    public static final String FLOW_FLAG_NO_EXIST = "0";

    /**
     * 流程计划
     */
    public static final String FLOW_TYPE_PLAN = "1";

    /**
     * 流程研发流程
     */
    public static final String FLOW_TYPE_PROTMP = "2";

    /**
     * 流程项目
     */
    public static final String FLOW_TYPE_PROJECT = "3";

    /**
     * 流程项目库
     */
    public static final String FLOW_TYPE_PROJECT_LIB = "4";

    
    /*********************** 活动名称库类型 ******************************/
    /**
     * 研发类
     */
    public static final String NAMESTANDARD_TYPE_DEV = "0";

    /**
     * 评审类
     */
    public static final String NAMESTANDARD_TYPE_REVIEW = "1";

    
    /*********************** 项目库权限模板 ******************************/
    /**
     * 项目库权限模板目录根目录的父ID
     */
    public static final String PROJECT_AUTH_TEMPLATE_CATEGORY_ROOT = "ROOT";

    /**
     * 项目库权限模板目录根目录名称
     */
    public static final String PROJECT_AUTH_TEMPLATE_CATEGORY_ROOT_CHI = "项目库";
    /***********************END 项目库权限模板 ******************************/

    
    /**
     * 项目库文档生命周期策略名称
     */
    public static final String REPFILE_LIFECYCLE_POLICY_NAME = "PROJECT_DOCUMENT";
    
    /**
     * 基本信息
     */
    public static final String BASIC_CN = "基本信息";
    
    /**
     * 输入
     */
    public static final String INPUT_CN = "输入";
    
    /**
     * 输出
     */
    public static final String OUTPUT_CN = "输出";
    
    /**
     * 资源
     */
    public static final String RESOURCE_CN = "资源";
    
    /**
     * 参考
     */
    public static final String REFERENCE_CN = "参考";
    
    /**
     * 问题
     */
    public static final String PROBLEM_CN = "问题";
    
    /**
     * 风险清单
     */
    public static final String RISK_CN = "风险清单";

    /**
     * js文件路径
     */
    public static final String jsPath = "/webpage/com/glaway/ids/planGeneral/js/";

}
