/*
 * 文件名：PlanTemplateReq.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.pbmn.activity.entity;

/**
 * 〈流程请求模板〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年3月30日
 * @see ActivityInfo
 * @since
 */

public class ActivityInfo {
    /**
     * 模板编号<br>
     */
    private String id;

    /**
     * 模板名称<br>
     */
    private String name;

    /**
     * 流程状态<br>
     */
    private String bizCurrent;

    /**
     * 启动状态<br>
     */
    private String status;

    /**
     * 创建者<br>
     */
    private String creator;

    /**
     * 审批领导<br>
     */
    private String leader;

    /**
     * 流程ID<br>
     */
    private String approveTaskId;

    /**
     * 流程实例ID<br>
     */
    private String procInstId;

    /**
     * 反馈时的编辑页面<br>
     */
    private String editUrl;

    /**
     * 审批时的查看页面<br>
     */
    private String viewUrl;

    /**
     * 终止监听类<br>
     */
    private String cancelEventListener;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the bizCurrent.
     */
    public String getBizCurrent() {
        return bizCurrent;
    }

    /**
     * @param bizCurrent
     *            The bizCurrent to set.
     */
    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     *            The creator to set.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return Returns the leader.
     */
    public String getLeader() {
        return leader;
    }

    /**
     * @param leader
     *            The leader to set.
     */
    public void setLeader(String leader) {
        this.leader = leader;
    }

    /**
     * @return Returns the approveTaskId.
     */
    public String getApproveTaskId() {
        return approveTaskId;
    }

    /**
     * @param approveTaskId
     *            The approveTaskId to set.
     */
    public void setApproveTaskId(String approveTaskId) {
        this.approveTaskId = approveTaskId;
    }

    /**
     * @return Returns the editUrl.
     */
    public String getEditUrl() {
        return editUrl;
    }

    /**
     * @param editUrl
     *            The editUrl to set.
     */
    public void setEditUrl(String editUrl) {
        this.editUrl = editUrl;
    }

    /**
     * @return Returns the viewUrl.
     */
    public String getViewUrl() {
        return viewUrl;
    }

    /**
     * @param viewUrl
     *            The viewUrl to set.
     */
    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    /**
     * @return Returns the procInstId.
     */
    public String getProcInstId() {
        return procInstId;
    }

    /**
     * @param procInstId
     *            The procInstId to set.
     */
    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getCancelEventListener() {
        return cancelEventListener;
    }

    public void setCancelEventListener(String cancelEventListener) {
        this.cancelEventListener = cancelEventListener;
    }
}
