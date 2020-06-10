/*
 * 文件名：ProjectStatusConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：blcao
 * 修改时间：2015年4月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.constant;

/**
 * 项目
 * 
 * @author wangshen
 * @version 2015年4月7日
 * @see ProjectConstants
 * @since
 */
public class ProjectConstants {
    /** 自然日 */
    public static final String NATURALDAY = "naturalDay";

    /** 工作日 */
    public static final String WORKDAY = "workDay";

    /** 公司日 */
    public static final String COMPANYDAY = "companyDay";
    
    /** 项目工期不能修改*/
    public static final String NOTMODIFY = "1";
    
    /** 项目工期可以修改*/
    public static final String MODIFY = "0";

    /** 项目树展示最近N条记录 */
    public static final String RECENTLY_PROJECT_NUM = "10";
    
    /** 项目*/
    public static final String PROJECT = "PROJECT";
    
    /** 项目模板 */
    public static final String PROJECTTEMPLATE = "PROJECTTEMPLATE";
    
    /** 文档密级对应的code */
    public static final String PROJ_LIB_SEC_GROUPCODE = "secretLevel";
    
    /** 项目构造树-项目 */
    public static final String PROJ_MENU_PROJECT = "项目";
    
    /** 项目构造树-项目列表 */
    public static final String PROJ_MENU_PROJECT_LIST = "项目列表";
    
    /****************************项目生命周期********************************/
    /** 项目拟制中状态 */
    public static final String EDITING = "EDITING";

    /** 项目执行中状态 */
    public static final String STARTING = "STARTING";

    /** 项目暂停状态 */
    public static final String PAUSED = "PAUSED";
    
    /** 项目关闭状态 */
    public static final String CLOSED = "CLOSED";
    /****************************项目操作********************************/
    /** 恢复项目 */
    public static final String RESUME = "RESUME";
    /** 暂停项目 */
    public static final String PAUSE = "PAUSE";
    /************************END 项目生命周期********************************/
    
    /** 项目审批中状态 */
    public static final String APPROVING = "1";

    /** 项目审批结束状态 */
    public static final String APPROVED = "0";
    
    /** 项目已被驳回状态 */
    public static final String REFUSED = "1";

    /** 项目未被驳回状态 */
    public static final String NORMAL = "0";
    
    /****************计划模板状态 start*************************/
    /** 项目模板拟制中状态 */
    public static final String PROJTEMPLATE_NIZHL = "nizhi";
    
    /** 项目模板审核中状态 */
    public static final String PROJTEMPLATE_SHENHE = "shenhe";
    
    /** 项目模板禁用中状态 */
    public static final String PROJTEMPLATE_JINYONG = "jinyong";
    
    /** 项目模板启用状态 */
    public static final String PROJTEMPLATE_QIYONG= "qiyong";
    
    /** 项目模板修订中状态 */
    public static final String PROJTEMPLATE_XIUDING= "xiuding";
    
    /** 项目模板修订中状态 */
    public static final String PROJTMP_USED= "1";
    /****************计划模板状态 end*************************/
    
}
