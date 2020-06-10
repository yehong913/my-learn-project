package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.util.ArrayList;
import java.util.List;


/**
 * A representation of the model object '<em><b>NameStandard</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @author wangyangzan
 * @generated
 */

public class NameStandardDto extends GLVData {

    /**
     * <!-- begin-user-doc -->编号
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String no = null;

    /**
     * <!-- begin-user-doc -->活动名称库
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->活动分类
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String activeCategory = null;

    /**
     * <!-- begin-user-doc -->状态
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String stopFlag = null;

    /**
     * <!-- begin-user-doc -->有效性
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String avaliable = "1";

    /**
     * 交付项
     */
    private List<NameStandardDto> deliverableList = new ArrayList<NameStandardDto>();

    public List<NameStandardDto> getDeliverableList() {
        return deliverableList;
    }

    public void setDeliverableList(List<NameStandardDto> deliverableList) {
        this.deliverableList = deliverableList;
    }

    /**
     * Returns the value of '<em><b>no</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>no</b></em>' feature
     * @generated
     */
    public String getNo() {
        return no;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newNo
     * @generated
     */
    public void setNo(String newNo) {
        no = newNo;
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
     * Returns the value of '<em><b>activeCategory</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>activeCategory</b></em>' feature
     * @generated
     */
    public String getActiveCategory() {
        return activeCategory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newActiveCategory
     *            feature.
     * @generated
     */
    public void setActiveCategory(String newActiveCategory) {
        activeCategory = newActiveCategory;
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newStopFlag
     * @generated
     */
    public void setStopFlag(String newStopFlag) {
        stopFlag = newStopFlag;
    }

    /**
     * Returns the value of '<em><b>avaliable</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>avaliable</b></em>' feature
     * @generated
     */
    public String getAvaliable() {
        return avaliable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newAvaliable
     * @generated
     */
    public void setAvaliable(String newAvaliable) {
        avaliable = newAvaliable;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "NameStandard " + " [no: " + getNo() + "]" + " [name: " + getName() + "]"
               + " [activeCategory: " + getActiveCategory() + "]" + " [stopFlag: " + getStopFlag()
               + "]" + " [avaliable: " + getAvaliable() + "]";
    }
}
