package com.glaway.ids.rdtask.task.vo;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Transient;

import com.glaway.ids.project.plan.dto.DocumentDto;


/**
 * 流程任务变更 输出表
 * 
 * @generated
 */
public class FlowTaskDeliverablesInfoVo {
    
    /**
     * 显示创建者
     */
    private String createBy ;
    
    /**
     * 显示创建者名称
     */
    private String createName ;
    
    /**
     * 创建时间
     */
    private Date createTime ;
    
    /**
     * 创建真实名称
     */
    private String createFullName = null;
    
    
    private String cellId = null;
    
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String firstBy = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String firstName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String firstFullName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    private Date firstTime = null;
    
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private Short securityLevel = new Short((short)1);
    
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String avaliable = "1";
    
    
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizId = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizVersion = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    private String policy_id = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String bizCurrent = null;
    
    /**
     * 
     */
    private String id = null;
    
    /**
     * <!-- begin-user-doc -->输出id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String outputId = null;

    /**
     * <!-- begin-user-doc -->名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String name = null;

    /**
     * <!-- begin-user-doc -->外键类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc -->外键id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String useObjectId = null;

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
    @Basic()
    private String useObjectTempId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String fileId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String origin = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String required = null;

    /**
     * <!-- begin-user-doc -->项目库文档ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String docId = null;

    /**
     * <!-- begin-user-doc -->文档名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
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

    /**
     * 
     */
    @Transient()
    private DocumentDto document = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
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

    public DocumentDto getDocument() {
        return document;
    }

    public void setDocument(DocumentDto document) {
        this.document = document;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Short securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getAvaliable() {
        return avaliable;
    }

    public void setAvaliable(String avaliable) {
        this.avaliable = avaliable;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(String bizVersion) {
        this.bizVersion = bizVersion;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getFirstBy() {
        return firstBy;
    }

    public void setFirstBy(String firstBy) {
        this.firstBy = firstBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstFullName() {
        return firstFullName;
    }

    public void setFirstFullName(String firstFullName) {
        this.firstFullName = firstFullName;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

}
