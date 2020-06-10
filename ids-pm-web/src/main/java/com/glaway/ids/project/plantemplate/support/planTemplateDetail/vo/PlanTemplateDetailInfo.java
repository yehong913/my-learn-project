/*
 * 文件名：PlanTemplateDetailItem.java
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
 * 〈计划模板WBS计划详细〉
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2015年3月30日
 * @see PlanTemplateDetailInfo
 * @since
 */

public class PlanTemplateDetailInfo {
    
    /**
     * 计划模板编号<br>
     */
    private String id;
    
    /**
     * 计划名称<br>
     */
    private String name;
    
    /**
     * 计划类型<br>
     */
    private String planLevel;
    
    /**
     * 工期<br>
     */
    private String workTime;
    
    /**
     * 里程碑<br>
     */
    private String milestone;
    
    /**
     * 上级计划编号<br>
     */
    private String parentPlanId;
    /**
     * 排序<br>
     */
    private int num;
    
    /**
     * 交付项的数量<br>
     */
    private long deliverablesCount;
    
    /**
     * 交付项名称<br>
     */
    private String deliverablesName;
    /**
     * 前置名称<br>
     */
    private String preposeName;
    /**
     * 模板计划节点<br>
     */
    private int planTmpNumber;
    /**
     * 可选参数,父节点的id,如果为空,则该节点为根节点<br>
     */
    private String _parentId;
    /**
     * 排序<br>
     */
    private String order;// 排序
    /**
     * 意义，目的和功能，以及被用到的地方 open closed<br>
     */
    private String state;
    
    /**
     * 输入项名称<br>
     */
    private String inputsName;
    
    /**
     * 来源<br>
     */
    private String origin;
    
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
     * @return Returns the planLevel.
     */
    public String getPlanLevel() {
        return planLevel;
    }
    /**
     * @param planLevel The planLevel to set.
     */
    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }
    /**
     * @return Returns the workTime.
     */
    public String getWorkTime() {
        return workTime;
    }
    /**
     * @param workTime The workTime to set.
     */
    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }
    /**
     * @return Returns the parentPlanId.
     */
    public String getParentPlanId() {
        return parentPlanId;
    }
    /**
     * @param parentPlanId The parentPlanId to set.
     */
    public void setParentPlanId(String parentPlanId) {
        this.parentPlanId = parentPlanId;
    }
    /**
     * @return Returns the deliverablesCount.
     */
    public long getDeliverablesCount() {
        return deliverablesCount;
    }
    /**
     * @param deliverablesCount The deliverablesCount to set.
     */
    public void setDeliverablesCount(long deliverablesCount) {
        this.deliverablesCount = deliverablesCount;
    }
    /**
     * @return Returns the num.
     */
    public int getNum() {
        return num;
    }
    /**
     * @param num The num to set.
     */
    public void setNum(int num) {
        this.num = num;
    }
    /**
     * @return Returns the milestone.
     */
    public String getMilestone() {
        return milestone;
    }
    /**
     * @param milestone The milestone to set.
     */
    public void setMilestone(String milestone) {
        this.milestone = milestone;
    }
    /**
     * @return Returns the deliverablesName.
     */
    public String getDeliverablesName() {
        return deliverablesName;
    }
    /**
     * @param deliverablesName The deliverablesName to set.
     */
    public void setDeliverablesName(String deliverablesName) {
        this.deliverablesName = deliverablesName;
    }
    /**
     * @return Returns the preposeName.
     */
    public String getPreposeName() {
        return preposeName;
    }
    /**
     * @param preposeName The preposeName to set.
     */
    public void setPreposeName(String preposeName) {
        this.preposeName = preposeName;
    }
    /**
     * @return Returns the planTmpNumber.
     */
    public int getPlanTmpNumber() {
        return planTmpNumber;
    }
    /**
     * @param planTmpNumber The planTmpNumber to set.
     */
    public void setPlanTmpNumber(int planTmpNumber) {
        this.planTmpNumber = planTmpNumber;
    }
    /**
     * @return Returns the _parentId.
     */
    public String get_parentId() {
        return _parentId;
    }
    /**
     * @param _parentId The _parentId to set.
     */
    public void set_parentId(String _parentId) {
        this._parentId = _parentId;
    }
    /**
     * @return Returns the order.
     */
    public String getOrder() {
        return order;
    }
    /**
     * @param order The order to set.
     */
    public void setOrder(String order) {
        this.order = order;
    }
    /**
     * @return Returns the state.
     */
    public String getState() {
        return state;
    }
    /**
     * @param state The state to set.
     */
    public void setState(String state) {
        this.state = state;
    }
    public String getInputsName() {
        return inputsName;
    }
    public void setInputsName(String inputsName) {
        this.inputsName = inputsName;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
