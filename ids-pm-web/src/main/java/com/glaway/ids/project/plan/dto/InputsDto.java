package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.io.Serializable;

/**
 * 计划 输入
 * 
 * @generated
 */
public class InputsDto extends GLVData implements Serializable {

    /**
     * 输入项名称
     */
    private String name = null;

    /**
     * 输入关联对象类型，如："PLAN",表示该输入数据属于某条计划
     */
    private String useObjectType = null;

    /**
     * 输入关联对象ID
     */
    private String useObjectId = null;

    /**
     * 所属
     */
    private String fileId = null;

    /**
     * 来源
     */
    private String origin = null;

    /**
     * 是否必要 
     */
    private String required = null;

    /**
     * 项目库文档的bizId、本地上传文档对应的jackrabbit路径
     */
    private String docId = null;

    /**
     * 文档名称 ：本地上传的是文件的全名包括后缀；来源计划输出和项目库文档的都是项目库文档名称
     */
    private String docName = null;

    /**
     * 来源计划对象ID 
     */
    private String originObjectId = null;

    /**
     * 来源计划对象名称
     */
    private String originObjectName = null;
    
    /**
     * 来源计划对象名称显示
     */
    private String originObjectNameShow = null;

    /**
     * 来源输出对象ID 
     */
    private String originDeliverablesInfoId = null;

    /**
     * 来源输出对象名称
     */
    private String originDeliverablesInfoName = null;

    /**
     * 是否选中
     */
    private String checked = null;

    /**
     * 文件
     */
    private DocumentDto document = null;

    /**
     * 表单ID
     */
    private String formId = null;
    
    /**
     * 来源类型(LOCAL:本地上传；PLAN:计划(实际为计划的输出)；PROJECTLIBDOC:项目库文档)
     */
    private String originType = null;
    
    /**
     * 补充类型,记录流程分解时：研发流程模版带来的内部和外部输入的区别(INNERTASK,DELIEVER)
     */
    private String originTypeExt = null;
    
    /**
     * 临时Id
     */
    private String tempId = null;
    
    
    /**
     * 是否有权限
     */
    private Boolean havePower = null;
    
    /**
     * 是否有下载权限
     */
    private Boolean download = null;
    
    /**
     * 是否有查看权限
     */
    private Boolean detail = null;
    
    /**
     * 密级
     */
    private Short securityLeve = null;
    
    /**
     * 文档名称显示
     */
    private String docNameShow = null;
    
    /**
     * 来源显示
     */
    private String originPath = null;
    
    /**
     * 文档id显示
     */
    private String docIdShow = null;
    
    /**
     * 匹配标志 
     */
    private String matchFlag = null;

    /**
     * 文件类型
     */
    private String fileType = null;

    /**
     * 版本号
     */
    private String versionCode = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUseObjectType() {
        return useObjectType;
    }

    public void setUseObjectType(String useObjectType) {
        this.useObjectType = useObjectType;
    }

    public String getUseObjectId() {
        return useObjectId;
    }

    public void setUseObjectId(String useObjectId) {
        this.useObjectId = useObjectId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getOriginObjectId() {
        return originObjectId;
    }

    public void setOriginObjectId(String originObjectId) {
        this.originObjectId = originObjectId;
    }

    public String getOriginObjectName() {
        return originObjectName;
    }

    public void setOriginObjectName(String originObjectName) {
        this.originObjectName = originObjectName;
    }

    public String getOriginObjectNameShow() {
        return originObjectNameShow;
    }

    public void setOriginObjectNameShow(String originObjectNameShow) {
        this.originObjectNameShow = originObjectNameShow;
    }

    public String getOriginDeliverablesInfoId() {
        return originDeliverablesInfoId;
    }

    public void setOriginDeliverablesInfoId(String originDeliverablesInfoId) {
        this.originDeliverablesInfoId = originDeliverablesInfoId;
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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
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

    public Short getSecurityLeve() {
        return securityLeve;
    }

    public void setSecurityLeve(Short securityLeve) {
        this.securityLeve = securityLeve;
    }

    public String getDocNameShow() {
        return docNameShow;
    }

    public void setDocNameShow(String docNameShow) {
        this.docNameShow = docNameShow;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getDocIdShow() {
        return docIdShow;
    }

    public void setDocIdShow(String docIdShow) {
        this.docIdShow = docIdShow;
    }

    public String getMatchFlag() {
        return matchFlag;
    }

    public void setMatchFlag(String matchFlag) {
        this.matchFlag = matchFlag;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
}
