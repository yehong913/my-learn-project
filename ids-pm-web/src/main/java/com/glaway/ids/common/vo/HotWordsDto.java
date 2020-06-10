package com.glaway.ids.common.vo;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * A representation of the model object '<em><b>HotWords</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class HotWordsDto extends GLVData {

    /**
     * <!-- begin-user-doc -->热词名称
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String content = null;

    /**
     * <!-- begin-user-doc -->热词类型
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String type = null;

    /**
     * <!-- begin-user-doc -->热词出现的次数
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private int frequency = 0;

    /**
     * Returns the value of '<em><b>content</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>content</b></em>' feature
     * @generated
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the '{@link HotWords#getContent() <em>content</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newContent
     *            the new value of the '{@link HotWords#getContent() content}' feature.
     * @generated
     */
    public void setContent(String newContent) {
        content = newContent;
    }

    /**
     * Returns the value of '<em><b>type</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>type</b></em>' feature
     * @generated
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the '{@link HotWords#getType() <em>type</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newType
     *            the new value of the '{@link HotWords#getType() type}' feature.
     * @generated
     */
    public void setType(String newType) {
        type = newType;
    }

    /**
     * Returns the value of '<em><b>frequency</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>frequency</b></em>' feature
     * @generated
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the '{@link HotWords#getFrequency() <em>frequency</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newFrequency
     *            the new value of the '{@link HotWords#getFrequency() frequency}' feature.
     * @generated
     */
    public void setFrequency(int newFrequency) {
        frequency = newFrequency;
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
        return "HotWords " + " [content: " + getContent() + "]" + " [type: " + getType() + "]"
               + " [frequency: " + getFrequency() + "]";
    }
}
