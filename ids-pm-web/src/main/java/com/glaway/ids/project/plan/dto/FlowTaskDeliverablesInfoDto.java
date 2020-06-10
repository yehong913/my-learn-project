package com.glaway.ids.project.plan.dto;


import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;


/**
 * 流程任务变更 输出表
 * 
 * @generated
 */
public class FlowTaskDeliverablesInfoDto extends GLVData {

    /**
     * <!-- begin-user-doc -->输出id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String outputId = null;

    /**
     * <!-- begin-user-doc -->名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String name = null;

    /**
     * <!-- begin-user-doc -->外键类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String useObjectId = null;
    
    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     */
    @Transient()
    private String useObjectCellId = null;

    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     */
    @Transient()
    private String useObjectName = null;

    
    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     */
    @Transient()
    private Boolean havePower = null;
    
    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     */
    @Transient()
    private Boolean download = null;
    
    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     */
    @Transient()
    private Boolean detail = null;
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String useObjectTempId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String fileId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String origin = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String required = null;

    /**
     * <!-- begin-user-doc -->项目库文档ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String docId = null;

    /**
     * <!-- begin-user-doc -->文档名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String docName = null;
    
    /**
     * 
     */
    @Transient()
    private String docUrl = null;

    /**
     * 去向
     */
    @Transient()
    private String result = null;

    /**
     * 
     */
    @Transient()
    private String checked = null;

//    /**
//     * 
//     */
//    @Transient()
//    private Document document = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    
    private String formId = null;
    
    /**
     * 名称对应的标交付项的ID
     */
    @Transient()
    private String deliverId = null;

    /**
     * Returns the value of '<em><b>outputId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>outputId</b></em>' feature
     * @generated
     */
    public String getOutputId() {
        return outputId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getOutputId() <em>outputId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOutputId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getOutputId() outputId}'
     *            feature.
     * @generated
     */
    public void setOutputId(String newOutputId) {
        outputId = newOutputId;
    }

    /**
     * Returns the value of '<em><b>name</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the value of '<em><b>useObjectType</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectType</b></em>' feature
     * @generated
     */
    public String getUseObjectType() {
        return useObjectType;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getUseObjectType() <em>useObjectType</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectType
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getUseObjectType()
     *            useObjectType}' feature.
     * @generated
     */
    public void setUseObjectType(String newUseObjectType) {
        useObjectType = newUseObjectType;
    }

    /**
     * Returns the value of '<em><b>useObjectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectId</b></em>' feature
     * @generated
     */
    public String getUseObjectId() {
        return useObjectId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getUseObjectId() <em>useObjectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getUseObjectId()
     *            useObjectId}' feature.
     * @generated
     */
    public void setUseObjectId(String newUseObjectId) {
        useObjectId = newUseObjectId;
    }

    /**
     * Returns the value of '<em><b>useObjectTempId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>useObjectTempId</b></em>' feature
     * @generated
     */
    public String getUseObjectTempId() {
        return useObjectTempId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getUseObjectTempId() <em>useObjectTempId</em>}'
     * feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectTempId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getUseObjectTempId()
     *            useObjectTempId}' feature.
     * @generated
     */
    public void setUseObjectTempId(String newUseObjectTempId) {
        useObjectTempId = newUseObjectTempId;
    }

    /**
     * Returns the value of '<em><b>fileId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>fileId</b></em>' feature
     * @generated
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getFileId() <em>fileId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFileId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getFileId() fileId}'
     *            feature.
     * @generated
     */
    public void setFileId(String newFileId) {
        fileId = newFileId;
    }

    /**
     * Returns the value of '<em><b>origin</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>origin</b></em>' feature
     * @generated
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getOrigin() <em>origin</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOrigin
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getOrigin() origin}'
     *            feature.
     * @generated
     */
    public void setOrigin(String newOrigin) {
        origin = newOrigin;
    }

    /**
     * Returns the value of '<em><b>required</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>required</b></em>' feature
     * @generated
     */
    public String getRequired() {
        return required;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getRequired() <em>required</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getRequired() required}'
     *            feature.
     * @generated
     */
    public void setRequired(String newRequired) {
        required = newRequired;
    }

    /**
     * Returns the value of '<em><b>docId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>docId</b></em>' feature
     * @generated
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getDocId() <em>docId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getDocId() docId}' feature.
     * @generated
     */
    public void setDocId(String newDocId) {
        docId = newDocId;
    }

    /**
     * Returns the value of '<em><b>docName</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>docName</b></em>' feature
     * @generated
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getDocName() <em>docName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocName
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getDocName() docName}'
     *            feature.
     * @generated
     */
    public void setDocName(String newDocName) {
        docName = newDocName;
    }

    /**
     * Returns the value of '<em><b>formId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>formId</b></em>' feature
     * @generated
     */
    public String getFormId() {
        return formId;
    }

    /**
     * Sets the '{@link FlowTaskDeliverablesInfo#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTaskDeliverablesInfo#getFormId() formId}'
     *            feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    public String getUseObjectName() {
        return useObjectName;
    }

    public void setUseObjectName(String useObjectName) {
        this.useObjectName = useObjectName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

//    public Document getDocument() {
//        return document;
//    }
//
//    public void setDocument(Document document) {
//        this.document = document;
//    }

    public Boolean getHavePower() {
        return havePower;
    }

    public void setHavePower(Boolean havePower) {
        this.havePower = havePower;
    }

    public Boolean getDownload() {
        return download;
    }

    public void setDownload(Boolean download) {
        this.download = download;
    }

    public Boolean getDetail() {
        return detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public String getDeliverId() {
		return deliverId;
	}

	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

    public String getUseObjectCellId() {
        return useObjectCellId;
    }

    public void setUseObjectCellId(String useObjectCellId) {
        this.useObjectCellId = useObjectCellId;
    }
}
