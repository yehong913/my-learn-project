package com.glaway.ids.common.vo;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * A representation of the model object '<em><b>AttentionWords</b></em>'.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class AttentionWordsDto extends GLVData {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String searchWord = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String userId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String deptId = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private long searchCount = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String type = null;

    /**
     * Returns the value of '<em><b>searchWord</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>searchWord</b></em>' feature
     * @generated
     */
    public String getSearchWord() {
        return searchWord;
    }

    /**
     * Sets the '{@link AttentionWordsDto#getSearchWord() <em>searchWord</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newSearchWord
     *            the new value of the '{@link AttentionWordsDto#getSearchWord() searchWord}' feature.
     * @generated
     */
    public void setSearchWord(String newSearchWord) {
        searchWord = newSearchWord;
    }

    /**
     * Returns the value of '<em><b>userId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>userId</b></em>' feature
     * @generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the '{@link AttentionWordsDto#getUserId() <em>userId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newUserId
     *            the new value of the '{@link AttentionWordsDto#getUserId() userId}' feature.
     * @generated
     */
    public void setUserId(String newUserId) {
        userId = newUserId;
    }

    /**
     * Returns the value of '<em><b>deptId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>deptId</b></em>' feature
     * @generated
     */
    public String getDeptId() {
        return deptId;
    }

    /**
     * Sets the '{@link AttentionWordsDto#getDeptId() <em>deptId</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newDeptId
     *            the new value of the '{@link AttentionWordsDto#getDeptId() deptId}' feature.
     * @generated
     */
    public void setDeptId(String newDeptId) {
        deptId = newDeptId;
    }

    /**
     * Returns the value of '<em><b>searchCount</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @return the value of '<em><b>searchCount</b></em>' feature
     * @generated
     */
    public long getSearchCount() {
        return searchCount;
    }

    /**
     * Sets the '{@link AttentionWordsDto#getSearchCount() <em>searchCount</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newSearchCount
     *            the new value of the '{@link AttentionWordsDto#getSearchCount() searchCount}'
     *            feature.
     * @generated
     */
    public void setSearchCount(long newSearchCount) {
        searchCount = newSearchCount;
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
     * Sets the '{@link AttentionWordsDto#getType() <em>type</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @param newType
     *            the new value of the '{@link AttentionWordsDto#getType() type}' feature.
     * @generated
     */
    public void setType(String newType) {
        type = newType;
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
        return "AttentionWords " + " [searchWord: " + getSearchWord() + "]" + " [userId: "
               + getUserId() + "]" + " [deptId: " + getDeptId() + "]" + " [searchCount: "
               + getSearchCount() + "]" + " [type: " + getType() + "]";
    }
}
