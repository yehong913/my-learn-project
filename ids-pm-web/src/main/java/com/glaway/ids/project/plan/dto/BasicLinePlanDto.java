package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.ids.project.projectmanager.dto.BusinessConfigDto;
import com.glaway.ids.project.projectmanager.dto.Project;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 基线计划信息
 */
@Data
public class BasicLinePlanDto extends GLVData {

    /**
     * 所属基线ID
     * 
     * @generated
     */
    private String basicLineId = null;

    /**
     * 所属基线
     */
    private BasicLineDto basicLine;

    /**
     * 计划ID
     * 
     * @generated
     */
    private String planId = null;

    /**
     * 计划名称
     * 
     * @generated
     */
    private String planName = null;

    /**
     * 所属项目ID
     * 
     * @generated
     */
    private String projectId = null;

    /**
     * 所属项目
     */
    private Project project;

    /**
     * 父计划ID
     * 
     * @generated
     */
    private String parentPlanId = null;

    /**
     * 计划等级
     * 
     * @generated
     */
    private String planLevel = null;

    /**
     * 计划等级
     */
    private BusinessConfigDto planLevelInfo;

    /**
     * 生命周期
     * 
     * @generated
     */
    private String bizCurrent = null;

    /**
     * 生命周期
     * 
     * @generated
     */
    private String status = null;

    /**
     * 负责人
     * 
     * @generated
     */
    private String owner = null;

    /**
     * 负责人
     */
    private TSUserDto ownerInfo;

    /**
     * 开始时间
     * 
     * @generated
     */
    private Date planStartTime = null;

    /**
     * 结束时间
     * 
     * @generated
     */
    private Date planEndTime = null;

    /**
     * 工期
     * 
     * @generated
     */
    private String workTime = null;

    /**
     * 输出
     * 
     * @generated
     */
    private String deliverables = null;

    /**
     * 计划创建人
     * 
     * @generated
     */
    private String creator = null;

    /**
     * 计划创建人
     */
    private TSUserDto creatorInfo;

    /**
     * 责任人部门
     */
    private String ownerDept = null;

    /**
     * 下达人
     * 
     * @generated
     */
    private String assigner = null;

    /**
     * 下达人
     */
    private TSUserDto assignerInfo;

    /**
     * 下达时间
     * 
     * @generated
     */
    private Date assignTime = null;

    /**
     * 备注
     * 
     * @generated
     */
    private String remark = null;

    /**
     * 是否里程碑
     * 
     * @generated
     */
    private String milestone = "false";

    /**
     * 是否里程碑
     */
    private String milestoneName = null;

    /**
     * 风险
     * 
     * @generated
     */
    @Basic()
    private String risk = null;

    /**
     * 前置计划ID
     */
    @Transient()
    private String preposeIds = null;

    /**
     * 
     */
    @Transient()
    private String parentStorey = null;

    /**
     * 同级排序序号
     * 
     * @generated
     */
    @Basic()
    private int storeyNo = 0;

    /**
     * 应用情况
     * 
     * @generated
     */
    private String implementation = null;

    /**
     * 进度
     * 
     * @generated
     */
    private String progressRate = null;

    /**
     * 临时父节点id
     */
    private String _parentId = null;

    /**
     * 顺序
     */
    private String order = null;

    /**
     * flg
     */
    private String compareFlag = null;

    /**
     * 子计划
     */
    private List<BasicLinePlanDto> children = new ArrayList<BasicLinePlanDto>();

    /**
     * 计划类型
     * 
     * @generated
     */
    private String planType = null;

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "BasicLinePlan " + " [basicLineId: " + getBasicLineId() + "]" + " [planId: "
               + getPlanId() + "]" + " [planName: " + getPlanName() + "]" + " [projectId: "
               + getProjectId() + "]" + " [parentPlanId: " + getParentPlanId() + "]"
               + " [planLevel: " + getPlanLevel() + "]" + " [bizCurrent: " + getBizCurrent() + "]"
               + " [status: " + getStatus() + "]" + " [owner: " + getOwner() + "]"
               + " [planStartTime: " + getPlanStartTime() + "]" + " [planEndTime: "
               + getPlanEndTime() + "]" + " [workTime: " + getWorkTime() + "]"
               + " [deliverables: " + getDeliverables() + "]" + " [creator: " + getCreator() + "]"
               + " [assigner: " + getAssigner() + "]" + " [assignTime: " + getAssignTime() + "]"
               + " [remark: " + getRemark() + "]" + " [milestone: " + getMilestone() + "]"
               + " [risk: " + getRisk() + "]" + " [storeyNo: " + getStoreyNo() + "]"
               + " [implementation: " + getImplementation() + "]" + " [progressRate: "
               + getProgressRate() + "]" + " [planType: " + getPlanType() + "]";
    }
}
