/*
 * 文件名：PlanTemplateDetailRep.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plantemplate.support.planTemplateDetail.vo;

/**
 * 〈计划模板WBS计划详细查询〉
 * 〈功能详细描述〉
 * @author duanpengfei
 * @version 2015年3月30日
 * @see PlanTemplateDetailReq
 * @since
 */

public class PlanTemplateDetailReq {
    /**
     * 计划模板编号<br>
     */
    private String planTemplateId;
    
    /**
     * 项目模板id<br>
     */
    private String projectTemplateId;
    
    /**
     * 计划ID<br>
     */
    private String planId;
    /**
     * 上方计划ID（在下方计划插入时）<br>
     */
    private String upPlanId;
    /**
     * 项目唯一编号<br>
     */
    private String projectNumber;
    
    /**
     * 创建人编号<br>
     */
    private String createBy;
    
    /**
     *  计划来源
     */
    private String planSource;

    /**
     * @return Returns the planTemplateId.
     */
    public String getPlanTemplateId() {
        return planTemplateId;
    }

    /**
     * @param planTemplateId The planTemplateId to set.
     */
    public void setPlanTemplateId(String planTemplateId) {
        this.planTemplateId = planTemplateId;
    }

    /**
     * @return Returns the projectNumber.
     */
    public String getProjectNumber() {
        return projectNumber;
    }

    /**
     * @param projectNumber The projectNumber to set.
     */
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

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
     * @return Returns the createBy.
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy The createBy to set.
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return Returns the upPlanId.
     */
    public String getUpPlanId() {
        return upPlanId;
    }

    /**
     * @param upPlanId The upPlanId to set.
     */
    public void setUpPlanId(String upPlanId) {
        this.upPlanId = upPlanId;
    }

    /**
     * @return Returns the planSource.
     */
    public String getPlanSource() {
        return planSource;
    }

    /**
     * @param planSource The planSource to set.
     */
    public void setPlanSource(String planSource) {
        this.planSource = planSource;
    }

    public String getProjectTemplateId() {
        return projectTemplateId;
    }

    public void setProjectTemplateId(String projectTemplateId) {
        this.projectTemplateId = projectTemplateId;
    }
    
    
}
