/*
 * 文件名：TeamVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年5月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.vo;




public class RepFileAuthVo {
    
    /**
     * id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;
    
    /**
     * 角色名称
     */
    private String roleName; 

    /**
     * 文件id
     */
    private String fileId;
    
    /**
     * 枚举出的权限名称
     */
    private String checkName;
    
    
    
    /**
     * 枚举出的权限code
     */
    private String checkValue;
    
    
    /**
     * 是否选中
     */
    private Boolean isChecked;

    /**
     * 角色编号
     */
    private String roleCode;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
