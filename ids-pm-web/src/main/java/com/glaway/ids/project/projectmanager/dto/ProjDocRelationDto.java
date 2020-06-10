package com.glaway.ids.project.projectmanager.dto;


import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;
import com.glaway.foundation.fdk.dev.dto.rep.RepFileDto;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 项目文档关系对象
 * @author wyz
 * @version 2018年8月6日
 * @see ProjDocRelationDto
 * @since
 */
public class ProjDocRelationDto extends GLVData {

    /**
     * <!-- begin-user-doc -->引用该文档的ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String quoteId = null;

    /**
     * 文档实例类
     */

    private RepFileDto repFile;

    /**
     * <!-- begin-user-doc -->文档ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String docId = null;

    /**
     * Returns the value of '<em><b>quoteId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>quoteId</b></em>' feature
     * @generated
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newQuoteId
     * @generated
     */
    public void setQuoteId(String newQuoteId) {
        quoteId = newQuoteId;
    }

    /**
     * Returns the value of '<em><b>docId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>docId</b></em>' feature
     * @generated
     */
    public String getDocId() {
        return docId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDocId
     * @generated
     */
    public void setDocId(String newDocId) {
        docId = newDocId;
    }

    public RepFileDto getRepFile() {
        return repFile;
    }

    public void setRepFile(RepFileDto repFile) {
        this.repFile = repFile;
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
        return "ProjDocRelation " + " [quoteId: " + getQuoteId() + "]" + " [docId: " + getDocId()
               + "]";
    }
}
