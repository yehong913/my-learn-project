/*
 * 文件名：MppInfo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年3月31日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.util.mpputil;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 〈mpp数据〉
 * 〈获得的Mpp数据〉
 * 
 * @author Administrator
 * @version 2015年3月31日
 * @see MppInfo
 * @since
 */

public class MppInfo {
    /**
     * 唯一编号<br>
     */
    private String uniqueId;

    /**
     * 编号<br>
     */
    private Integer id;

    /**
     * 上级编号<br>
     */
    private String parentId;

    /**
     * 名称<br>
     */
    private String name;

    /**
     * 工期<br>
     */
    private String duration;

    /**
     * 级别<br>
     */
    private String planLevel;

    /**
     * 里程碑<br>
     */
    private Boolean milestone;

    /**
     * 交付项<br>
     */
    private String documentName;
    
    /**
     * 前置名称<br>
     */
    private String preposeName;

    /**
     * 任务类型<br>
     */
    private String taskNameType;
    
    
    /**
     * 开始时间
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date startTime = null;

    /**
     * 结束时间
     */
    @Basic()
    @Temporal(TemporalType.DATE)
    private Date endTime = null;

    /**
     * @return Returns the uniqueId.
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId
     *            The uniqueId to set.
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return Returns the id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the parentId.
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     *            The parentId to set.
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
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
     * @return Returns the duration.
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration
     *            The duration to set.
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return Returns the planLevel.
     */
    public String getPlanLevel() {
        return planLevel;
    }

    /**
     * @param planLevel
     *            The planLevel to set.
     */
    public void setPlanLevel(String planLevel) {
        this.planLevel = planLevel;
    }

    /**
     * @return Returns the documentName.
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * @param documentName
     *            The documentName to set.
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * @return Returns the milestone.
     */
    public Boolean getMilestone() {
        return milestone;
    }

    /**
     * @param milestone The milestone to set.
     */
    public void setMilestone(Boolean milestone) {
        this.milestone = milestone;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTaskNameType() {
        return taskNameType;
    }

    public void setTaskNameType(String taskNameType) {
        this.taskNameType = taskNameType;
    }
}
