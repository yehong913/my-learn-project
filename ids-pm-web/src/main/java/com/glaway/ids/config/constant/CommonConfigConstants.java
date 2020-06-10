/*
 * 文件名：ReviewConfigConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangyangzan
 * 修改时间：2015年12月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.constant;

/**
 * 通用配置常量
 * 
 * @author wangyangzan
 * @version 2015年12月11日
 * @see CommonConfigConstants
 * @since
 */
public class CommonConfigConstants {

    /** 外部专家 */
    public static final String EXPERT_OUTSIDE = "1";
    /** 内部专家 */
    public static final String EXPERT_INSIDE = "2";
    /** 外部*/
    public static final String EXPERT_OUTERTYPE = "外部";
    /** 内部 */
    public static final String EXPERT_INNERTYPE = "内部";
    /** 专家类型(导出列字段名)*/
    public static final String EXPERT_TYPE = "专家类型";
    /** 专家姓名*/
    public static final String EXPERT_NAME = "专家姓名";
    /** 专业*/
    public static final String EXPERT_SPECIALTY = "专业";
    /** 电话*/
    public static final String EXPERT_TEL = "电话";
    /** 邮箱*/
    public static final String EXPERT_MAIL = "邮箱";
    /** 启用*/
    public static final String STATUS_START = "启用";
    /** 禁用*/
    public static final String STATUS_STOP = "禁用";
    

    /** 专业-评审要素模板 */
    public static final String SPECIALTY_LINK_FACTOR_TEMPLATE = "1";
    /** 专业-专家 */
    public static final String SPECIALTY_LINK_EXPERT = "2";
    
    
    /***********************活动名称库类型******************************/
    /** 研发类 */
    public static final String NAMESTANDARD_TYPE_DEV = "0";
    
    /** 评审类 */
    public static final String NAMESTANDARD_TYPE_REVIEW = "1";
    
    /*********************资源每日使用信息更新-操作类型***********************/
    /**
     *  处理对象类型-项目
     */
    public static final String OBJECT_TYPE_PROJECT = "PROJECT";
    /**
     *  处理对象类型-计划
     */
    public static final String OBJECT_TYPE_PLAN = "PLAN";
    /** 
     *  处理对象类型-资源
     */
    public static final String OBJECT_TYPE_RESOURCE = "RESOURCE";
    
    /**
     *  资源新增
     */
    public static final String RESOURCEEVERYDAYUSE_OPERATIONTYPE_INSERT = "INSERT";
    /**
     *  资源更新
     */
    public static final String RESOURCEEVERYDAYUSE_OPERATIONTYPE_UPDATE = "UPDATE";
    /** 
     *  资源删除
     */
    public static final String RESOURCEEVERYDAYUSE_OPERATIONTYPE_DELETE = "DELETE";
    
    /** 
     *  项目关闭
     */
    public static final String RESOURCEEVERYDAYUSE_PROJECT_CLOSE = "CLOSE";
    /** 
     *  项目暂停
     */
    public static final String RESOURCEEVERYDAYUSE_PROJECT_PAUSE = "PAUSE";
    
    /** 
     *  计划删除
     */
    public static final String RESOURCEEVERYDAYUSE_PLAN_DELETE = "DELETE";
    /** 
     *  计划废弃
     */
    public static final String RESOURCEEVERYDAYUSE_PROJECT_DISCARD = "DISCARD";
    /** 
     *  计划提前完工
     */
    public static final String RESOURCEEVERYDAYUSE_PROJECT_BEFOREHAND = "BEFOREHAND";
    
    /** 
     *  业务数据为空
     */
    public static final String DATA_IS_NULL = "dataIsNull";
    
}
