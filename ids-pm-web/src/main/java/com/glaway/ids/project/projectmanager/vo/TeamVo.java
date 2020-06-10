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


import com.glaway.foundation.common.dto.TSGroupDto;
import com.glaway.foundation.common.dto.TSRoleDto;
import com.glaway.foundation.common.dto.TSUserDto;
import lombok.Data;

public class TeamVo {

    private String id;

    private String userId;

    private String roleId;

    private String groupId;

    private TSRoleDto role;

    private TSUserDto user;

    private TSGroupDto group;

    private String type;

    private String _parentId;
    
    private String dept;
    
    private String email;
    
    private String iconCls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public TSRoleDto getRole() {
        return role;
    }

    public void setRole(TSRoleDto role) {
        this.role = role;
    }

    public TSUserDto getUser() {
        return user;
    }

    public void setUser(TSUserDto user) {
        this.user = user;
    }

    public TSGroupDto getGroup() {
        return group;
    }

    public void setGroup(TSGroupDto group) {
        this.group = group;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_parentId() {
        return _parentId;
    }

    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
}
