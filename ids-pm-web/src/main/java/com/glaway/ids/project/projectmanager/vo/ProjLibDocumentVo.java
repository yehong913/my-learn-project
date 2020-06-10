/*
 * 文件名：ProjLibDocumentVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：wangshen
 * 修改时间：2015年5月11日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.projectmanager.vo;


import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Basic;

import java.util.List;

public class ProjLibDocumentVo {

    
    private String id;

    private String docNumber;

    private String docName;

    private String version;

    private String path;

    private String createName;

    private Date createTime;

    private String updateName;

    private Date updateTime;

    private String securityLevel;

    private String status;

    private String remark;

    private String projectId;

    private String pathName;

    private String bizId;

    private String parentId;
    
    /**
     * 流程ID
     */
    private String procInstId;
    
    /**
     * 流程名称
     */
    private String title;

    
    private long orderNum;
    
    private boolean detail;
    
    private boolean remove;
    
    private boolean update;
    
    private boolean download;
    
    private boolean upload;
    
    private boolean history;
    
    private boolean revise;
    
    private boolean rollback;
    
    private boolean approve;
    
    /**
     * 文件类型
     */
    private String fileTypeId;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件下载地址
     */
    private String dowmLoadUrl;

    /**
     * 文件类型，0：目录，1：文件
     */
    private Integer type;
    /**
     * 操作状态
     */
    @Basic()
    private String operStatus;

    private String createTimeStr;

    private String updateTimeStr;
    /**
     * 密级id
     * */
    private String securityLevelId;
    
    /**
     *文档类型
     * */
    private String docTypeId;
    
    /**
     *附件的vo 
     */
    private List<AttachmentVo> attachmentVo = new ArrayList<AttachmentVo>();
    
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public List<AttachmentVo> getAttachmentVo() {
        return attachmentVo;
    }

    public void setAttachmentVo(List<AttachmentVo> attachmentVo) {
        this.attachmentVo = attachmentVo;
    }

    public String getSecurityLevelId() {
        return securityLevelId;
    }

    public void setSecurityLevelId(String securityLevelId) {
        this.securityLevelId = securityLevelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getDowmLoadUrl() {
        return dowmLoadUrl;
    }

    public void setDowmLoadUrl(String dowmLoadUrl) {
        this.dowmLoadUrl = dowmLoadUrl;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }
    public String getFileTypeId() {
        return fileTypeId;
    }
    public void setFileTypeId(String fileTypeId){
    	this.fileTypeId = fileTypeId;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isRevise() {
        return revise;
    }

    public void setRevise(boolean revise) {
        this.revise = revise;
    }

    public boolean isRollback() {
        return rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public String getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(String docTypeId) {
        this.docTypeId = docTypeId;
    }
}
