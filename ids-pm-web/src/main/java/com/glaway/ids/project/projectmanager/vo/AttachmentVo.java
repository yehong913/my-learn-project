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

import java.util.Date;




public class AttachmentVo {

    private String id;

    private String uuid;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件下载地址
     */
    private String dowmLoadUrl;

    /**
     * 附件vo的密级
     */
    private String docSecurityLevel;
    
    /**
     * 变更密级隐藏行字段
     */
    private String changeSecurityLevel;

    /**
     * 上传人
     */
    private String createName;
    
    /**
     * 附件显示名称
     */
    private String attachmentShowName;
    
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

    /**
     * 上传时间
     */
    private Date createTime;
    
    public String getChangeSecurityLevel() {
        return changeSecurityLevel;
    }

    public void setChangeSecurityLevel(String changeSecurityLevel) {
        this.changeSecurityLevel = changeSecurityLevel;
    }

    public String getDocSecurityLevel() {
        return docSecurityLevel;
    }

    public void setDocSecurityLevel(String docSecurityLevel) {
        this.docSecurityLevel = docSecurityLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDowmLoadUrl() {
        return dowmLoadUrl;
    }

    public void setDowmLoadUrl(String dowmLoadUrl) {
        this.dowmLoadUrl = dowmLoadUrl;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAttachmentShowName() {
        return attachmentShowName;
    }

    public void setAttachmentShowName(String attachmentShowName) {
        this.attachmentShowName = attachmentShowName;
    }
    

}
