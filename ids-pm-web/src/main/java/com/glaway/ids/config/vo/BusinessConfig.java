package com.glaway.ids.config.vo;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * A representation of the model object '<em><b>BusinessConfig</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class BusinessConfig extends GLVData {

    /**
     * <!-- begin-user-doc -->配置编号 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String no = "";

    /**
     * <!-- begin-user-doc -->配置名称 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc --> 配置类型<!-- end-user-doc -->
     * 
     * @generated
     */
    private String configType = null;

    /**
     * <!-- begin-user-doc -->配置信息 <!-- end-user-doc -->
     * 
     * @generated
     */

    private String configComment = null;

    /**
     * <!-- begin-user-doc -->状态
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String stopFlag = null;
    
    /**
     * <!-- begin-user-doc -->父节点ID <!-- end-user-doc -->
     * 
     * @generated
     */
    private String parentId = "";

    /**
     * <!-- begin-user-doc -->路径 <!-- end-user-doc -->
     * 
     * @generated
     */

    private String path = null;
    
    

    private String rankQuality=null;
    /**
     * <!-- begin-user-doc -->color <!-- end-user-doc -->
     * 
     * @generated
     */

    private String result;
    
    /**
     * <!-- begin-user-doc -->子孙节点 <!-- end-user-doc -->
     * 
     * @generated
     */
    private List<BusinessConfig> children = new ArrayList<BusinessConfig>();

    /**
     * Clears the <em>children</em> feature.
     * 
     * @generated
     */
    public void clearChildren() {
        while (!children.isEmpty()) {
            removeFromChildren(children.iterator().next());
        }
    }

    public boolean removeFromChildren(BusinessConfig childrenValue) {
        if (children.contains(childrenValue)) {
            boolean result = children.remove(childrenValue);
            return result;
        }
        return false;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigComment() {
        return configComment;
    }

    public void setConfigComment(String configComment) {
        this.configComment = configComment;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRankQuality() {
        return rankQuality;
    }

    public void setRankQuality(String rankQuality) {
        this.rankQuality = rankQuality;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BusinessConfig> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessConfig> children) {
        this.children = children;
    }
}
