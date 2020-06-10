package com.glaway.ids.config.auth;


import java.util.HashMap;
import java.util.Map;


/**
 * 项目库权限枚举值
 * 
 * @author blcao
 */
public enum ProjectLibraryAuthorityEnum {
    /**
     * 
     */
    LIST("列表", 1, "list"),
    /**
     * 
     */
    DETAIL("查看", 2, "detail"),
    /**
     * 
     */
    CREATE("新增", 3, "create"),
    /**
     * 
     */
    REMOVE("删除", 4, "remove"),
    /**
     * 
     */
    UPDATE("修改", 5, "update"),
    /**
     * 
     */
    DOWNLOAD("下载", 6, "download"),
    /**
     * 
     */
    UPLOAD("上传", 7, "upload"),
    /**
     * 
     */
    HISTORY("历史", 8, "history"),
    /**
     * 
     */
    REVISE("修订", 9, "revise"),
    /**
     * 
     */
    ROLLBACK("回退", 10, "rollback"),
    /**
     * 
     */
    APPROVE("提交审批", 11, "approve");

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private int order;

    /**
     * 
     */
    private String actionCode;

    private ProjectLibraryAuthorityEnum(String name, int order, String actionCode) {
        this.name = name;
        this.order = order;
        this.actionCode = actionCode;
    }

    public static ProjectLibraryAuthorityEnum valueOfActionCode(String actionCode) {
        ProjectLibraryAuthorityEnum[] actions = values();
        for (int i = 0; i < actions.length; i++ ) {
            ProjectLibraryAuthorityEnum action = actions[i];
            String code = action.getActionCode();
            if (code != null && code.equals(actionCode)) {
                return action;
            }
        }
        return null;
    }

    public static Map<String, String> getAuthNameCodeMap() {
        Map<String, String> authNameCodeMap = new HashMap<String, String>();
        ProjectLibraryAuthorityEnum[] actions = values();
        for (int i = 0; i < actions.length; i++ ) {
            ProjectLibraryAuthorityEnum action = actions[i];
            authNameCodeMap.put(action.getActionCode(), action.getName());
        }
        return authNameCodeMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

}
