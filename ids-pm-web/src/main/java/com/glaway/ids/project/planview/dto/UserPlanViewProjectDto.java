package com.glaway.ids.project.planview.dto;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.glaway.foundation.common.entity.GLObject;
import com.glaway.foundation.common.vdata.GLVData;

/**
 * 用户项目视图
 * @author likaiyong
 * @version 2018年5月30日
 */
public class UserPlanViewProjectDto extends GLVData {

    /**
     * 视图信息ID
     */
    private String planViewInfoId = null;
    
    /**
     * 用户ID 
     */
    private String userId = null;
    
    /**
     * 项目ID
     */
    private String projectId = null;

    public String getPlanViewInfoId() {
        return planViewInfoId;
    }

    public void setPlanViewInfoId(String planViewInfoId) {
        this.planViewInfoId = planViewInfoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "UserPlanViewProjectDto [planViewInfoId=" + planViewInfoId
                + ", userId=" + userId + ", projectId=" + projectId + "]";
    }
    
}
