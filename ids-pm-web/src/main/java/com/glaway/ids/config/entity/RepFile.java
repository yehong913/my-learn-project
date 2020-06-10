/*
 * 文件名：RepFile.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：lzhang
 * 修改时间：2015年3月30日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.entity;


import com.glaway.foundation.document.entity.DocumentObject;


/**
 * 文件
 * 〈功能详细描述〉
 * 
 * @author lzhang
 * @version 2015年3月30日
 * @see RepFile
 * @since
 */

public class RepFile extends DocumentObject {

    /**
     * 存储库Id
     */
    private String libId;

    /**
     * 文件类型Id
     */
    private String fileTypeId;

    /**
     * 文档编号
     */
    private String fileNumber;

    /**
     * 文档备注
     */
    private String fileRemark;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件编码
     */
    private String fileCode;

    /**
     * 操作状态
     */
    private String operStatus;

    /**
     * 父文件Id
     */
    private String parentId;

    /**
     * 排序算法
     */
    private long orderNum;

    /**
     * 文件类型，0：目录，1：文件
     */
    private int fileType;

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(String fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "RepFile [libId=" + libId + ", fileTypeId=" + fileTypeId + ", fileNumber="
               + fileNumber + ", fileRemark=" + fileRemark + ", fileName=" + fileName
               + ", fileCode=" + fileCode + ", operStatus=" + operStatus + ", parentId="
               + parentId + ", orderNum=" + orderNum + ", fileType=" + fileType + "]";
    }

}
