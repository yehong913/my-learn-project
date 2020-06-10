/*
 * 文件名：NameStandardSwitchConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.constant;

/**
 * 计划项目参数中名称库选择框列表
 * 
 * @author Administrator
 * @version 2015年4月20日
 * @see NameStandardSwitchConstants
 * @since
 */
public class NameStandardSwitchConstants {

    /** 启用活动名称库 */
    public static final String USENAMESTANDARDLIB = "启用活动名称库";

    /** 强制使用活动名称 */
    public static final String FORCEUSENAMESTANDARD = "强制使用活动名称";

    /** 关联交付项可以修改 */
    public static final String DELIVERABLEUPATEABLE = "关联交付项可以修改";

    /**
     * 根据编号获取名称
     * 
     * @param id
     * @return
     * @see
     */
    public static String getSwitch(String id) {
        String switchName = null;
        switch (id) {
            case "1":
                switchName = USENAMESTANDARDLIB;
                break;
            case "2":
                switchName = FORCEUSENAMESTANDARD;
                break;
            case "3":
                switchName = DELIVERABLEUPATEABLE;
                break;
            default:
                break;
        }
        return switchName;
    }

    /**
     * 获取最后一个配置
     * 
     * @param names
     * @return
     * @see
     */
    public static String getLastSwitch(String names) {
        if (names == null) {
            return null;
        }
        String last = null;
        if (names.contains(DELIVERABLEUPATEABLE.trim())) {
            last = DELIVERABLEUPATEABLE;
        }
        else if (names.contains(FORCEUSENAMESTANDARD.trim())) {
            last = FORCEUSENAMESTANDARD;
        }
        else if (names.contains(USENAMESTANDARDLIB.trim())) {
            last = USENAMESTANDARDLIB;
        }
        return last;
    }

    public static String getList() {
        return DELIVERABLEUPATEABLE + "," + FORCEUSENAMESTANDARD + "," + DELIVERABLEUPATEABLE;
    }
}
