package com.glaway.ids.rdtask.task.vo;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Transient;

import com.glaway.ids.project.plan.dto.DocumentDto;


/**
 * 流程任务变更 输入表Vo
 * 
 * @generated
 */
public class FlowTaskInputsMatchVo {
    /**
     * 密级
     */
    @Transient()
    private Short securityLeve = null;
    
    /**
     * 匹配标志 
     */
    @Transient()
    private String matchFlag = null;
    
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
    
    /**
     * 
     */
    private String ext1 = null;
    /**
     * 
     */
    private String ext2 = null;
    /**
     * 
     */
    private String ext3 = null;
    
    /**
     * 
     */
    private String docIdShow = null;
    
    /**
     * 
     */
    private String originType = null;
    
    /**
     * 
     */
    private String originTypeExt = null;
    
    /**
     * 
     */
    private String id = null;
    
    /**
     * <!-- begin-user-doc -->输入id
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String inputId = null;

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
    
    /**useObjectName
     */
    private String useObjectName = null;

    /**
     * <!-- begin-user-doc -->所属
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String fileId = null;

    /**
     * <!-- begin-user-doc -->是否来源活动名称库
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String origin = null;

    /**
     * <!-- begin-user-doc -->是否必要
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
     * <!-- begin-user-doc -->来源计划对象ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String originObjectId = null;

    /**
     * 来源计划对象名称
     */
    @Transient()
    private String originObjectName = null;
    
    /**
     * 来源计划对象名称显示
     */
    @Transient()
    private String originObjectNameShow = null;

    /**
     * <!-- begin-user-doc -->来源输出对象ID <!-- end-user-doc -->
     * 
     * @generated
     */
    @Basic()
    private String originDeliverablesInfoId = null;

    /**
     * 来源输出对象名称
     */
    @Transient()
    private String originDeliverablesInfoName = null;

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
     * 
     */
    @Transient()
    private Boolean havePower = null;
    
    /**
     * 
     */
    @Transient()
    private Boolean download = null;
    
    /**
     * 
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
    private String formId = null;
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Transient()
    private Short securityLevel = null;
    
    /**
     * 名称对应的标交付项的ID
     */
    @Transient()
    private String deliverId = null;

    /**
     * Returns the value of '<em><b>inputId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>inputId</b></em>' feature
     * @generated
     */
    public String getInputId() {
        return inputId;
    }

    /**
     * Sets the '{@link FlowTaskInputs#getInputId() <em>inputId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newInputId
     *            the new value of the '{@link FlowTaskInputs#getInputId() inputId}' feature.
     * @generated
     */
    public void setInputId(String newInputId) {
        inputId = newInputId;
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
     * Sets the '{@link FlowTaskInputs#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link FlowTaskInputs#getName() name}' feature.
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
     * Sets the '{@link FlowTaskInputs#getUseObjectType() <em>useObjectType</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectType
     *            the new value of the '{@link FlowTaskInputs#getUseObjectType() useObjectType}'
     *            feature.
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
     * Sets the '{@link FlowTaskInputs#getUseObjectId() <em>useObjectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectId
     *            the new value of the '{@link FlowTaskInputs#getUseObjectId() useObjectId}'
     *            feature.
     * @generated
     */
    public void setUseObjectId(String newUseObjectId) {
        useObjectId = newUseObjectId;
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
     * Sets the '{@link FlowTaskInputs#getFileId() <em>fileId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFileId
     *            the new value of the '{@link FlowTaskInputs#getFileId() fileId}' feature.
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
     * Sets the '{@link FlowTaskInputs#getOrigin() <em>origin</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOrigin
     *            the new value of the '{@link FlowTaskInputs#getOrigin() origin}' feature.
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
     * Sets the '{@link FlowTaskInputs#getRequired() <em>required</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
     *            the new value of the '{@link FlowTaskInputs#getRequired() required}' feature.
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
     * Sets the '{@link FlowTaskInputs#getDocId() <em>docId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocId
     *            the new value of the '{@link FlowTaskInputs#getDocId() docId}' feature.
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
     * Sets the '{@link FlowTaskInputs#getDocName() <em>docName</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocName
     *            the new value of the '{@link FlowTaskInputs#getDocName() docName}' feature.
     * @generated
     */
    public void setDocName(String newDocName) {
        docName = newDocName;
    }

    /**
     * Returns the value of '<em><b>originObjectId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>originObjectId</b></em>' feature
     * @generated
     */
    public String getOriginObjectId() {
        return originObjectId;
    }

    /**
     * Sets the '{@link FlowTaskInputs#getOriginObjectId() <em>originObjectId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOriginObjectId
     *            the new value of the '{@link FlowTaskInputs#getOriginObjectId() originObjectId}'
     *            feature.
     * @generated
     */
    public void setOriginObjectId(String newOriginObjectId) {
        originObjectId = newOriginObjectId;
    }

    /**
     * Returns the value of '<em><b>originDeliverablesInfoId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>originDeliverablesInfoId</b></em>' feature
     * @generated
     */
    public String getOriginDeliverablesInfoId() {
        return originDeliverablesInfoId;
    }

    /**
     * Sets the '{@link FlowTaskInputs#getOriginDeliverablesInfoId()
     * <em>originDeliverablesInfoId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOriginDeliverablesInfoId
     *            the new value of the '{@link FlowTaskInputs#getOriginDeliverablesInfoId()
     *            originDeliverablesInfoId}' feature.
     * @generated
     */
    public void setOriginDeliverablesInfoId(String newOriginDeliverablesInfoId) {
        originDeliverablesInfoId = newOriginDeliverablesInfoId;
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
     * Sets the '{@link FlowTaskInputs#getFormId() <em>formId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
     *            the new value of the '{@link FlowTaskInputs#getFormId() formId}' feature.
     * @generated
     */
    public void setFormId(String newFormId) {
        formId = newFormId;
    }

    public String getOriginObjectName() {
        return originObjectName;
    }

    public void setOriginObjectName(String originObjectName) {
        this.originObjectName = originObjectName;
    }

    public String getOriginDeliverablesInfoName() {
        return originDeliverablesInfoName;
    }

    public void setOriginDeliverablesInfoName(String originDeliverablesInfoName) {
        this.originDeliverablesInfoName = originDeliverablesInfoName;
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

    public Short getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Short securityLevel) {
        this.securityLevel = securityLevel;
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

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    public String getOriginTypeExt() {
        return originTypeExt;
    }

    public void setOriginTypeExt(String originTypeExt) {
        this.originTypeExt = originTypeExt;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }

    public String getDocIdShow() {
        return docIdShow;
    }

    public void setDocIdShow(String docIdShow) {
        this.docIdShow = docIdShow;
    }

    public String getOriginObjectNameShow() {
        return originObjectNameShow;
    }

    public void setOriginObjectNameShow(String originObjectNameShow) {
        this.originObjectNameShow = originObjectNameShow;
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

    public String getMatchFlag() {
        return matchFlag;
    }

    public void setMatchFlag(String matchFlag) {
        this.matchFlag = matchFlag;
    }

    public Short getSecurityLeve() {
        return securityLeve;
    }

    public void setSecurityLeve(Short securityLeve) {
        this.securityLeve = securityLeve;
    }

    public String getUseObjectName() {
        return useObjectName;
    }

    public void setUseObjectName(String useObjectName) {
        this.useObjectName = useObjectName;
    }
}
