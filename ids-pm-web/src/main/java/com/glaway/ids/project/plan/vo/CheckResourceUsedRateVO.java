/*
 * 文件名：ResourceUsedRate.java 版权：Copyright by www.glaway.com 描述： 修改人：syc 修改时间：2016年3月30日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;

/**
 * CheckResourceUsedRateVO
 * 
 * @author wqb
 * @version 2019年12月6日 15:02:11
 * @see CheckResourceUsedRateVO
 * @since
 */
public class CheckResourceUsedRateVO
{

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目
     */
    private String projectName;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划开始时间
     */
    private String planStartTime;

    /**
     * 计划开始时间
     */
    private String planEndTime;

    /**
     * 使用百分比（%）
     */
    private double usedRate;

    /**
     * 计划进度（%）
     */
    private double progressRate;

    /**
     * 计划负责人
     */
    private String launcher;

    /**
     * 项目经理
     */
    private String managerName;

    private  int allNumber;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public double getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(double usedRate) {
        this.usedRate = usedRate;
    }

    public double getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(double progressRate) {
        this.progressRate = progressRate;
    }

    public String getLauncher() {
        return launcher;
    }

    public void setLauncher(String launcher) {
        this.launcher = launcher;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }
}
