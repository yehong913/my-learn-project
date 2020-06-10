package com.glaway.ids.project.projectmanager.vo;


import java.util.HashMap;
import java.util.Map;



/**
 * 角色权限VO
 * 
 * @author blcao
 */
public class ProjLibRoleFileAuthVo {

    /** ProjectLibRoleFileAuth的ID */
    private String id;

    /** 目录ID */
    private String fileId;

    /** 角色ID */
    private String roleId;

    /** 角色名称 */
    private String roleName;

    /** 权限值Map */
    private Map<String, Boolean> authMap = new HashMap<String, Boolean>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map<String, Boolean> getAuthMap() {
        return authMap;
    }

    public void setAuthMap(Map<String, Boolean> authMap) {
        this.authMap = authMap;
    }
}
