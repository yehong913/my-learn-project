package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Transient;


/**
 * 变更计划 输入
 * 
 * @generated
 */
@Data
public class TempPlanInputsDto extends GLVData {

    /**
     * <!-- begin-user-doc -->输入id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String inputId = null;

    /**
     * <!-- begin-user-doc -->输入名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String name = null;

    /**
     * <!-- begin-user-doc -->外键类型 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc -->外键id<!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectId = null;

    /**
     * <!-- begin-user-doc -->所属<!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String fileId = null;

    /**
     * <!-- begin-user-doc -->来源<!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String origin = null;

    /**
     * <!-- begin-user-doc -->是否必要 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String required = null;

    /**
     * <!-- begin-user-doc -->项目库文档ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String docId = null;

    /**
     * <!-- begin-user-doc -->文档名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String docName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String formId = null;

    /**
     * <!-- begin-user-doc -->是否选中 <!-- end-user-doc -->
     */
    @Transient()
    private String checked = null;

    /**
     * <!-- begin-user-doc -->文件 <!-- end-user-doc -->
     */
    @Transient
    private DocumentDto document = null;

    /**
     * <!-- begin-user-doc -->来源计划对象ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String originObjectId = null;

    /**
     * <!-- begin-user-doc -->来源输出对象ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String originDeliverablesInfoId = null;
    
    /**
     * 输入类型
     */
    @Basic()
    private String originType = null;
    
    /**
     * 文档名称
     */
    @Transient()
    private String docNameShow = null;
    
    /**
     * 来源路径显示
     */
    @Transient()
    private String originPath = null;
    
    @Transient()
    private String docIdShow = null;



    @Override
    public String toString() {
        return "TempPlanInputs [inputId=" + inputId + ", name=" + name + ", useObjectType="
               + useObjectType + ", useObjectId=" + useObjectId + ", fileId=" + fileId
               + ", origin=" + origin + ", required=" + required + ", docId=" + docId
               + ", docName=" + docName + ", formId=" + formId + ", checked=" + checked
               + ", document=" + document + ", originObjectId=" + originObjectId
               + ", originDeliverablesInfoId=" + originDeliverablesInfoId + ", originType="
               + originType + ", docNameShow=" + docNameShow + ", originPath=" + originPath
               + ", docIdShow=" + docIdShow + "]";
    }
}
