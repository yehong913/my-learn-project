/*
 * 文件名：TaskFeedbackInfo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年5月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.rdtask.task.form;

import java.io.Serializable;

/**
 * 〈计划反馈信息〉
 * 〈功能详细描述〉
 * 
 * @author duanpengfei
 * @version 2015年5月4日
 * @see TaskFeedbackInfo
 * @since
 */

public class TaskFeedbackInfo implements Serializable {
    
    /**
     * 计划反馈编号<br>
     */
    public String id;
    /**
     * 任务编号<br>
     */
    private String taskId;

    /**
     * 进度<br>
     */
    private String progressRate = "";

    /**
     * 进度描述<br>
     */
    private String progressRateRemark = "";

    /**
     * 风险等级<br>
     */
    private String risk = "";

    /**
     * 风险描述<br>
     */
    private String riskRemark = "";
    
    /**
     * 进度附件地址<br>
     */
    private String filePathP = "";
    
    /**
     * 风险附件地址<br>
     */
    private String filePath = "";

    /**
     * @return Returns the taskId.
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId The taskId to set.
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return Returns the progressRate.
     */
    public String getProgressRate() {
        return progressRate;
    }

    /**
     * @param progressRate The progressRate to set.
     */
    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }

    /**
     * @return Returns the progressRateRemark.
     */
    public String getProgressRateRemark() {
        return progressRateRemark;
    }

    /**
     * @param progressRateRemark The progressRateRemark to set.
     */
    public void setProgressRateRemark(String progressRateRemark) {
        this.progressRateRemark = progressRateRemark;
    }

    public String getFilePathP() {
        return filePathP;
    }

    public void setFilePathP(String filePathP) {
        this.filePathP = filePathP;
    }

    /**
     * @return Returns the risk.
     */
    public String getRisk() {
        return risk;
    }

    /**
     * @param risk The risk to set.
     */
    public void setRisk(String risk) {
        this.risk = risk;
    }

    /**
     * @return Returns the riskRemark.
     */
    public String getRiskRemark() {
        return riskRemark;
    }

    /**
     * @param riskRemark The riskRemark to set.
     */
    public void setRiskRemark(String riskRemark) {
        this.riskRemark = riskRemark;
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
