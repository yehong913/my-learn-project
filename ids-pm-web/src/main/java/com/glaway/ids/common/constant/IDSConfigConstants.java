/*
 * 文件名：IDSConfigConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：blcao
 * 修改时间：2015年10月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.constant;

/**
 * IDS系统配置项目KEY值
 * 
 * @author blcao
 * @version 2015年10月20日
 * @see IDSConfigConstants
 * @since
 */
public class IDSConfigConstants {

    /** 评审管理提供给项目及流程管理的webservice服务URL */
    public static final String IDS_REVIEW_TASK_SUPPORT_URL = "IDS_REVIEW_Task_Support";

    /** 查看评审任务请求地址 */
    public static final String IDS_REVIEWTASK_VIEWTASK_URL = "IDS_REVIEW_ViewTask_HttpUrl";
    
    /** 知识库提供的webservice接口地址在系统参数配置中的key */
    public static final String IDS_KLM_KNOWLEDGE_SUPPORT_KEY = "IDS_KLM_Knowledge_Support";
    
    /**  知识库访问地址在系统参数配置中的Key */
    public static final String IDS_KLM_HTTPURL_KEY = "IDS_KLM_HttpUrl";
    

    /** 导入风险第一列 */
    public static final String RISK_ONE = "风险名称";

    /** 导入风险第二列 */
    public static final String RISK_TWO = "负责人";

    /** 导入风险第三列 */
    public static final String RISK_THREE = "发生概率";

    /** 导入风险第四列 */
    public static final String RISK_FOUR = "影响程度";

    /** 导入风险第五列 */
    public static final String RISK_FIVE = "备注";

    /** 导入风险措施 */
    public static final String MEASURE = "措施名称";
    
}
