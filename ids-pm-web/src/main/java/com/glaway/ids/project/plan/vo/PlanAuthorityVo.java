/*
 * 文件名：PlanAuthorityVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：blcao
 * 修改时间：2016年7月22日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.glaway.ids.project.plan.vo;

/**
 * PlanAuthorityVo
 * 
 * @author blcao
 */
public class PlanAuthorityVo {

    /**
     * 是否可修改
     */
    private String isModify;

    /**
     * 修改权限
     */
    private String planModifyOperationCode;

    /**
     * 下达权限
     */
    private String planAssignOperationCode;

    /**
     * 删除权限
     */
    private String planDeleteOperationCode;

    /**
     * 变更权限
     */
    private String planChangeOperationCode;
    
    /**
     * 撤销变更权限
     */
    private String planRevocationOperationCode;

    /**
     * 废弃权限
     */
    private String planDiscardOperationCode;

    /**
     * 是否项目管理员
     */
    private boolean isPmo;

    /**
     * 是否项目经理
     */
    private boolean isProjectManger;

    /**
     * 关注权限
     */
    private String planConcernOperationCode;

    /**
     * 取消关注权限
     */
    private String planUnconcernOperationCode;

    public String getIsModify() {
        return isModify;
    }

    public void setIsModify(String isModify) {
        this.isModify = isModify;
    }

    public String getPlanModifyOperationCode() {
        return planModifyOperationCode;
    }

    public void setPlanModifyOperationCode(String planModifyOperationCode) {
        this.planModifyOperationCode = planModifyOperationCode;
    }

    public String getPlanAssignOperationCode() {
        return planAssignOperationCode;
    }

    public void setPlanAssignOperationCode(String planAssignOperationCode) {
        this.planAssignOperationCode = planAssignOperationCode;
    }

    public String getPlanDeleteOperationCode() {
        return planDeleteOperationCode;
    }

    public void setPlanDeleteOperationCode(String planDeleteOperationCode) {
        this.planDeleteOperationCode = planDeleteOperationCode;
    }

    public String getPlanChangeOperationCode() {
        return planChangeOperationCode;
    }

    public void setPlanChangeOperationCode(String planChangeOperationCode) {
        this.planChangeOperationCode = planChangeOperationCode;
    }

    public String getPlanRevocationOperationCode() {
        return planRevocationOperationCode;
    }

    public void setPlanRevocationOperationCode(String planRevocationOperationCode) {
        this.planRevocationOperationCode = planRevocationOperationCode;
    }

    public String getPlanDiscardOperationCode() {
        return planDiscardOperationCode;
    }

    public void setPlanDiscardOperationCode(String planDiscardOperationCode) {
        this.planDiscardOperationCode = planDiscardOperationCode;
    }

    public boolean isPmo() {
        return isPmo;
    }

    public void setPmo(boolean isPmo) {
        this.isPmo = isPmo;
    }

    public boolean isProjectManger() {
        return isProjectManger;
    }

    public void setProjectManger(boolean isProjectManger) {
        this.isProjectManger = isProjectManger;
    }

    public String getPlanConcernOperationCode() {
        return planConcernOperationCode;
    }

    public void setPlanConcernOperationCode(String planConcernOperationCode) {
        this.planConcernOperationCode = planConcernOperationCode;
    }

    public String getPlanUnconcernOperationCode() {
        return planUnconcernOperationCode;
    }

    public void setPlanUnconcernOperationCode(String planUnconcernOperationCode) {
        this.planUnconcernOperationCode = planUnconcernOperationCode;
    }
}
