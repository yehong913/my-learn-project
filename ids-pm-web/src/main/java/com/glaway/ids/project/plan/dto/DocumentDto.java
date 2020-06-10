package com.glaway.ids.project.plan.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * A representation of the model object '<em><b>Document</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @author wangyanzan
 * @generated
 */
public class DocumentDto extends GLVData {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

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
     * A toString method which prints the values of all EAttributes of this instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        return "Document " + " [name: " + getName() + "]";
    }
}
