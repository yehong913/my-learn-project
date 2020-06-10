package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.project.projectmanager.dto.Project;
import lombok.Data;

import java.util.Date;


/**
 * 基线信息
 */
@Data
public class BasicLineDto extends GLVData {

    /**
     * 基线所属项目ID
     * 
     * @generated
     */
    private String projectId = null;

    /**
     * 基线所属项目
     */
    private Project project;

    /**
     * 名称
     * 
     * @generated
     */
    private String basicLineName = null;

    /**
     * 流程实例id
     * 
     * @generated
     */
    private String procInstId = null;

    /**
     * 备注
     * 
     * @generated
     */

    private String remark = null;

    /**
     * 创建人
     */
    private TSUserDto createByInfo;

    /**
     * 
     */
    private TSUserDto launcherInfo;

    /** 是否存在流程 0:不存在; 1:存在 */
    private String flowFlag = null;

    /**
     * <!-- begin-user-doc --> 发起人 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String launcher = "";

    /**
     * <!-- begin-user-doc --> 发起时间 <!-- end-user-doc -->
     * 
     * @generated
     */
    private Date launchTime = null;

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "BasicLine " + " [projectId: " + getProjectId() + "]" + " [basicLineName: "
               + getBasicLineName() + "]" + " [procInstId: " + getProcInstId() + "]"
               + " [remark: " + getRemark() + "]" + " [launcher: " + getLauncher() + "]"
               + " [launchTime: " + getLaunchTime() + "]";
    }
}
