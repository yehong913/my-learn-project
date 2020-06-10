/*
 * 文件名：TaskInfoReq.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年8月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.rdtask.task.vo;

public class TaskInfoReq {

    /** 任务名称*/
    private String taskName;

    /** 交付项名称*/
    private String deliverables;

    /** 项目分类*/
    private String epsName;

    /** 过滤任务ID*/
    private String taskId;

    /** type*/
    private String type;

    /** 计划类型*/
    private String planType;

    /** 计划类别名称*/
    private String taskNameTypeName;

    /** 项目名称*/
    private String projectName;

    /**当前用户密级*/
    private Short userSecretlevel;

    /** 搜索词*/
    private String searchWords;

    /**
     * 页号<br>
     */
    private Integer pageNum;
    /**
     * 页数<br>
     */
    private Integer pageSize;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public String getEpsName() {
        return epsName;
    }

    public void setEpsName(String epsName) {
        this.epsName = epsName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getTaskNameTypeName() {
        return taskNameTypeName;
    }

    public void setTaskNameTypeName(String taskNameTypeName) {
        this.taskNameTypeName = taskNameTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Short getUserSecretlevel() {
        return userSecretlevel;
    }

    public void setUserSecretlevel(Short userSecretlevel) {
        this.userSecretlevel = userSecretlevel;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }
}
