/*
 * 文件名：KnowledgeInfoReq.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年8月13日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.rdtask.task.vo;

public class KnowledgeInfoReq {
    
    /** 知识编号*/
    private String code;
    
    /** 来源库编号*/
    private String libId;
    
    /** 知识唯一值*/
    private String workId;
    
    /** 任务ID*/
    private String taskId;
    
    /** 用户编号*/
    private String userId;
    
    /** 类型*/
    private String type;
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
