package com.glaway.ids.project.projectmanager.dto;

import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Common业务配置
 * @author: sunmeng
 * @ClassName: BusinessConfigDTO
 * @Date: 2019/6/3-13:58
 * @since
 */
public class BusinessConfigDto extends GLVData {

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
    @Transient()
    private List<BusinessConfigDto> children = new ArrayList<BusinessConfigDto>();


    public String getRankQuality() {
        return rankQuality;
    }

    public void setRankQuality(String rankQuality) {
        this.rankQuality = rankQuality;
    }

    /**
     * Returns the value of '<em><b>no</b></em>' feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of '<em><b>no</b></em>' feature
     * @generated
     */
    public String getNo() {
        return no;
    }

    /**
     * Sets the '{@link BusinessConfigDto#getNo() <em>no</em>}' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param newNo
     *            the new value of the '{@link BusinessConfigDto#getNo() no}' feature.
     * @generated
     */
    public void setNo(String newNo) {
        no = newNo;
    }

    /**
     * Returns the value of '<em><b>name</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the '{@link BusinessConfigDto#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newName
     *            the new value of the '{@link BusinessConfigDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the value of '<em><b>configType</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>configType</b></em>' feature
     * @generated
     */
    public String getConfigType() {
        return configType;
    }

    /**
     * Sets the '{@link BusinessConfigDto#getConfigType() <em>configType</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newConfigType
     *            the new value of the '{@link BusinessConfigDto#getConfigType() configType}' feature.
     * @generated
     */
    public void setConfigType(String newConfigType) {
        configType = newConfigType;
    }

    /**
     * Returns the value of '<em><b>configComment</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>configComment</b></em>' feature
     * @generated
     */
    public String getConfigComment() {
        return configComment;
    }

    /**
     * Sets the '{@link BusinessConfigDto#getConfigComment() <em>configComment</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newConfigComment
     *            the new value of the '{@link BusinessConfigDto#getConfigComment() configComment}'
     *            feature.
     * @generated
     */
    public void setConfigComment(String newConfigComment) {
        configComment = newConfigComment;
    }

    /**
     * Returns the value of '<em><b>stopFlag</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of '<em><b>stopFlag</b></em>' feature
     * @generated
     */
    public String getStopFlag() {
        return stopFlag;
    }

    /**
     * Sets the '{@link BusinessConfigDto#getStopFlag() <em>stopFlag</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newStopFlag
     *            the new value of the '{@link BusinessConfigDto#getStopFlag() stopFlag}' feature.
     * @generated
     */
    public void setStopFlag(String newStopFlag) {
        stopFlag = newStopFlag;
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

    public List<BusinessConfigDto> getChildren() {
        return children;
    }

    public void setChildren(List<BusinessConfigDto> children) {
        this.children = children;
    }


    /**
     * Adds to the <em>children</em> feature.
     *
     * @param childrenValue
     *            the value to add
     * @return true if the value is added to the collection (it was not yet
     *         present in the collection), false otherwise
     * @generated
     */
    public boolean addToChildren(BusinessConfigDto childrenValue) {
        if (!children.contains(childrenValue)) {
            boolean result = children.add(childrenValue);
            return result;
        }
        return false;
    }

    /**
     * Removes from the <em>children</em> feature.
     *
     * @param childrenValue
     *            the value to remove
     * @return true if the value is removed from the collection (it existed in
     *         the collection before removing), false otherwise
     * @generated
     */
    public boolean removeFromChildren(BusinessConfigDto childrenValue) {
        if (children.contains(childrenValue)) {
            boolean result = children.remove(childrenValue);
            return result;
        }
        return false;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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

}

