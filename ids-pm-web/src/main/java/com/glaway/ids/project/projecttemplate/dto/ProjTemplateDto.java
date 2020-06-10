package com.glaway.ids.project.projecttemplate.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.dto.TSUserDto;
import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * A representation of the model object '<em><b>ProjTemplate</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjTemplateDto extends GLVData implements Serializable {

    /**
     * <!-- begin-user-doc -->项目模板编号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projTmplNumber = null;

    /**
     * <!-- begin-user-doc -->项目模板名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String projTmplName = null;

    /**
     * <!-- begin-user-doc -->备注
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = "";

    /**
     * 保存计划
     */
    private List<String> savePlans = null;

    /**
     * 保存团队
     */
    private List<String> saveTeams = null;

    /**
     * 保存知识库
     */
    private List<String> saveLibs = null;
    
    /**
     * 保存项目库目录权限
     */
    private List<String> saveLibPower = null;
    
    /**
     *  暂存标示Id，用于页面显示
     */
    private String persientId = null;

    /**
     * <!-- begin-user-doc -->创建人
     * <!-- end-user-doc -->
     */

    private TSUserDto creator;

    /**
     * <!-- begin-user-doc -->状态: 换成bizCurrent判断模板状态
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String status = "";
    
    /**
     * <!-- begin-user-doc -->流程实例Id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String processInstanceId = null;

    /**
     * Returns the value of '<em><b>projTmplNumber</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projTmplNumber</b></em>' feature
     * @generated
     */
    public String getProjTmplNumber() {
        return projTmplNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjTmplNumber
     *            feature.
     * @generated
     */
    public void setProjTmplNumber(String newProjTmplNumber) {
        projTmplNumber = newProjTmplNumber;
    }

    /**
     * Returns the value of '<em><b>projTmplName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>projTmplName</b></em>' feature
     * @generated
     */
    public String getProjTmplName() {
        return projTmplName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newProjTmplName
     *            feature.
     * @generated
     */
    public void setProjTmplName(String newProjTmplName) {
        projTmplName = newProjTmplName;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRemark
     * @generated
     */
    public void setRemark(String newRemark) {
        remark = newRemark;
    }

    /**
     * Returns the value of '<em><b>status</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>status</b></em>' feature
     * @generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStatus
     * @generated
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public List<String> getSavePlans() {
        return savePlans;
    }

    public void setSavePlans(List<String> savePlans) {
        this.savePlans = savePlans;
    }

    public List<String> getSaveTeams() {
        return saveTeams;
    }

    public void setSaveTeams(List<String> saveTeams) {
        this.saveTeams = saveTeams;
    }

    public List<String> getSaveLibs() {
        return saveLibs;
    }

    public void setSaveLibs(List<String> saveLibs) {
        this.saveLibs = saveLibs;
    }

    public TSUserDto getCreator() {
        return creator;
    }

    public void setCreator(TSUserDto creator) {
        this.creator = creator;
    }
    
    public List<String> getSaveLibPower() {
        return saveLibPower;
    }

    public void setSaveLibPower(List<String> saveLibPower) {
        this.saveLibPower = saveLibPower;
    }
    
    

    public String getPersientId() {
        return persientId;
    }

    public void setPersientId(String persientId) {
        this.persientId = persientId;
    }

    
    
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "ProjTemplate " + " [projTmplNumber: " + getProjTmplNumber() + "]"
               + " [projTmplName: " + getProjTmplName() + "]" + " [remark: " + getRemark() + "]"
               + " [status: " + getStatus() + "]";
    }

}
