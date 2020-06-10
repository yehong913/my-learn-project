/*
 * 文件名：PlanLogInfo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年5月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.form;

/**
 * 〈计划日志〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年5月4日
 * @see PlanLogInfo
 * @since
 */

public class PlanLogInfo {
    
    private String id;
    
    /**
     * 计划编号<br>
     */
    private String planId;

    /**
     * 内容<br>
     */
    private String logInfo;

    /**
     * 用户名<br>
     */
    private String createName;

    /**
     * 用户时间<br>
     */
    private String createTimeStr;
    
    /**
     * 备注<br>
     */
    private String remark;
    
    /**
     * 附件<br>
     */
    private String filePath;

    /**
     * @return Returns the planId.
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * @param planId The planId to set.
     */
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    /**
     * @return Returns the logInfo.
     */
    public String getLogInfo() {
        return logInfo;
    }

    /**
     * @param logInfo The logInfo to set.
     */
    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    /**
     * @return Returns the createName.
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * @param createName The createName to set.
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * @return Returns the createTimeStr.
     */
    public String getCreateTimeStr() {
        return createTimeStr;
    }

    /**
     * @param createTimeStr The createTimeStr to set.
     */
    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    /**
     * @return Returns the filePath.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath The filePath to set.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
