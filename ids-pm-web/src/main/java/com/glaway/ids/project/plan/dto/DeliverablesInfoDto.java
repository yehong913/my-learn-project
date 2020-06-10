package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.io.Serializable;


/**
 * A representation of the model object '<em><b>DeliverablesInfo</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @author wangyangzan
 * @generated
 */
public class DeliverablesInfoDto extends GLVData implements Serializable {

    /**
     * <!-- begin-user-doc --> 交付物名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc --> 关联的外键类型<!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectType = null;

    /**
     * <!-- begin-user-doc --> 关联的外键id <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectId = null;
    
    /**
     * <!-- begin-user-doc --><!-- end-user-doc -->
     */
    private String useObjectCellId = null;

    /**
     * <!-- begin-user-doc --> 关联的对象的名称 <!-- end-user-doc -->
     */
    private String useObjectName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String useObjectTempId = null;

    /**
     * 文件
     */
    private DocumentDto document = null;

    /**
     * <!-- begin-user-doc --> 所属 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String fileId = null;

    /**
     * <!-- begin-user-doc --> 是否来源活动名称库 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String origin = null;

    /**
     * <!-- begin-user-doc --> 是否必要 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String required = null;

    /**
     * 去向
     */
    private String result = null;

    /**
     * 是否选中
     */
    private String checked = null;

    /**
     * <!-- begin-user-doc --> 项目库文档ID <!-- end-user-doc -->
     * 
     * @generated
     */
    private String docId = null;

    /**
     * <!-- begin-user-doc --> 项目库文档名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String docName = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String formId = null;
    
    private Boolean havePower = null;
    
    private Boolean download = null;
    
    private Boolean detail = null;

    /**
     * PLM 文件类型
     */
    private String fileType;

    /**
     * 来源：PLM
     */
    private String orginType;

    /**
     * PLM版本
     */
    private String versionCode;

    /**
     * PLM状态
     */
    private String statusCode;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOrginType() {
        return orginType;
    }

    public void setOrginType(String orginType) {
        this.orginType = orginType;
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newName
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectType
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectId
     *            feature.
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUseObjectTempId
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFileId
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newOrigin
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newRequired
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocId
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocName
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFormId
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

    public DocumentDto getDocument() {
        return document;
    }

    public void setDocument(DocumentDto document) {
        this.document = document;
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

    public String getUseObjectCellId() {
        return useObjectCellId;
    }

    public void setUseObjectCellId(String useObjectCellId) {
        this.useObjectCellId = useObjectCellId;
    }

}
