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

package com.glaway.ids.project.plantemplate.support.planTemplate.vo;

/**
 * 〈请求计划模板〉
 * 〈功能详细描述〉
 * @author duanpengfei
 * @version 2015年3月30日
 * @see PlanTemplateReq
 * @since
 */

public class PlanTemplateReq {
    
    
    
    private String procInstId;
    /**
     * 页号<br>
     */
    private Integer pageNum;
    /**
     * 页数<br>
     */
    private Integer pageSize;
    
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
     * 备注<br>
     */
    private String remark;
    /**
     * 室主任<br>
     */
    private String leader;
    /**
     * 部领导<br>
     */
    private String deptLeader;
    /**
     * 流程ID<br>
     */
    private String approveTaskId;
    /**
     * 接口类型
     * SupportFlagConstants类<br>
     */
    private String supportFlag;
    
    /**
     * 创建者Id
     */
    private String firstBy = null;
    
    /**
     * 创建者中文名
     */
    private String firstFullName = null;
    
    /**
     * 创建者用户名
     */
    private String firstName = null;
    
    /**
     * 导出类型（计划模板或者项目模板）
     */
    private String exportType = null;
    
    public String getProcInstId() {
        return procInstId;
    }
    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return Returns the pageNum.
     */
    public Integer getPageNum() {
        return pageNum;
    }
    /**
     * @param pageNum The pageNum to set.
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
    /**
     * @return Returns the pageSize.
     */
    public Integer getPageSize() {
        return pageSize;
    }
    /**
     * @param pageSize The pageSize to set.
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
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
     * @param bizCurrent The bizCurrent to set.
     */
    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }
    /**
     * @return Returns the creator.
     */
    public String getCreator() {
        return creator;
    }
    /**
     * @param creator The creator to set.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the supportFlag.
     */
    public String getSupportFlag() {
        return supportFlag;
    }
    /**
     * @param supportFlag The supportFlag to set.
     */
    public void setSupportFlag(String supportFlag) {
        this.supportFlag = supportFlag;
    }
    /**
     * @return Returns the leader.
     */
    public String getLeader() {
        return leader;
    }
    /**
     * @param leader The leader to set.
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
     * @param approveTaskId The approveTaskId to set.
     */
    public void setApproveTaskId(String approveTaskId) {
        this.approveTaskId = approveTaskId;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return Returns the deptLeader.
     */
    public String getDeptLeader() {
        return deptLeader;
    }
    /**
     * @param deptLeader The deptLeader to set.
     */
    public void setDeptLeader(String deptLeader) {
        this.deptLeader = deptLeader;
    }
    public String getFirstBy() {
        return firstBy;
    }
    public void setFirstBy(String firstBy) {
        this.firstBy = firstBy;
    }
    public String getFirstFullName() {
        return firstFullName;
    }
    public void setFirstFullName(String firstFullName) {
        this.firstFullName = firstFullName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getExportType() {
        return exportType;
    }
    public void setExportType(String exportType) {
        this.exportType = exportType;
    }
    
    
}
