package com.glaway.ids.project.projectmanager.vo;

public class ProjDocVo {

    /** id*/
    private String id;
    
    /** 交付项ID*/
    private String deliverableId;
    
    /** 交付项名称*/
    private String deliverableName;
    
    /** 项目库文档ID*/
    private String docId;
    
    /** 文档名称*/
    private String docName;
    
    /** 文档创建人*/
    private String docCreateBy;
    
    /** 文档名称*/
    private String fileTypeId;
   
    /** 文档版本*/
    private String version;
    
    /** 文档状态*/
    private String status;
    
    /** 是否可以操作*/
    private boolean opFlag;
    
    /** 是否有权限*/
    private boolean havePower;
    
    /** 是否有权限*/
    private boolean download;
    
    /** 是否有权限*/
    private boolean detail;
    
    /** 是否有查看权限（出输入挂接交付项）*/
    private String inputViewFlog;
    
    /** 密级*/
    private short securityLevel;

    /** plm 类型*/
    private String fileType;

    /** 来源：plm*/
    private String orginType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getInputViewFlog() {
        return inputViewFlog;
    }

    public void setInputViewFlog(String inputViewFlog) {
        this.inputViewFlog = inputViewFlog;
    }

    public String getDeliverableId() {
        return deliverableId;
    }

    public void setDeliverableId(String deliverableId) {
        this.deliverableId = deliverableId;
    }

    public String getDeliverableName() {
        return deliverableName;
    }

    public void setDeliverableName(String deliverableName) {
        this.deliverableName = deliverableName;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOpFlag() {
        return opFlag;
    }

    public void setOpFlag(boolean opFlag) {
        this.opFlag = opFlag;
    }




    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isHavePower() {
        return havePower;
    }

    public void setHavePower(boolean havePower) {
        this.havePower = havePower;
    }


    public short getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(short securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getDocCreateBy() {
        return docCreateBy;
    }

    public void setDocCreateBy(String docCreateBy) {
        this.docCreateBy = docCreateBy;
    }
    
}
