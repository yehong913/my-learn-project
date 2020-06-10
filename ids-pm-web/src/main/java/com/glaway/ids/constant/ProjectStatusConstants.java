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
 * 项目状态
 * 
 * @author blcao
 * @version 2015年4月7日
 * @see ProjectStatusConstants
 * @since
 */
public class ProjectStatusConstants {
    /** 正常 */
    public static final String NORMAL = "NORMAL";

    /** 暂停 */
    public static final String PAUSE = "PAUSE";

    /** 恢复 */
    public static final String RECOVER = "RECOVER";

    /** 关闭 */
    public static final String CLOSE = "CLOSE";

    /** 暂停 */
    public static final String PAUSE_CHI = "已暂停";

    /** 关闭 */
    public static final String CLOSE_CHI = "已关闭";
    
    /*************************************************************/
    /** 项目流程业务类型 **/
    /*************************************************************/
    /** 启动 */
    public static final String PROJECT_STATUS_CHANGE_START = "START";

    /** 暂停 */
    public static final String PROJECT_STATUS_CHANGE_PAUSE = "PAUSE";

    /** 恢复 */
    public static final String PROJECT_STATUS_CHANGE_RESUME = "RESUME";

    /** 关闭 */
    public static final String PROJECT_STATUS_CHANGE_CLOSE = "CLOSE";
    /*************************************************************/
    /** 项目流程业务执行成功后消息 **/
    /*************************************************************/
    /** 启动 */
    public static final String PROJECT_STATUS_CHANGE_START_CHI = "启动";

    /** 暂停 */
    public static final String PROJECT_STATUS_CHANGE_PAUSE_CHI = "暂停";

    /** 恢复 */
    public static final String PROJECT_STATUS_CHANGE_RESUME_CHI = "恢复";

    /** 关闭 */
    public static final String PROJECT_STATUS_CHANGE_CLOSE_CHI = "关闭";
}
