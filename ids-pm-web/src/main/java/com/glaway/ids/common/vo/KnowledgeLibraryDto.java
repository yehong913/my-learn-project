package com.glaway.ids.common.vo;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;


/**
 * A representation of the model object '<em><b>KnowledgeLibrary</b></em>'. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
@Data
public class KnowledgeLibraryDto extends GLVData
{

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String name = null;

    private String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 名称提示
     */
    @Transient()
    private String tipName = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String dataSourceType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String dataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String status = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    private String remark = null;

    /**
     * 流程id
     */
    private String procInstId = null;

    /**
     * 当前用户是否是管理员
     */
    @Transient()
    private String isManager = null;

    /**
     * 分类list
     */
    @Transient()
    private List<CategoryNodeVo> categoryNodeVoList = null;

    /**
     * Returns the value of '<em><b>name</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>name</b></em>' feature
     * @generated
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the '{@link KnowledgeLibraryDto#getName() <em>name</em>}' feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param newName
     *            the new value of the '{@link KnowledgeLibraryDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName)
    {
        name = newName;
    }

    /**
     * Returns the value of '<em><b>dataSourceType</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>dataSourceType</b></em>' feature
     * @generated
     */
    public String getDataSourceType()
    {
        return dataSourceType;
    }

    /**
     * Sets the '{@link KnowledgeLibraryDto#getDataSourceType() <em>dataSourceType</em>}' feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDataSourceType
     *            the new value of the '{@link KnowledgeLibraryDto#getDataSourceType()
     *            dataSourceType} ' feature.
     * @generated
     */
    public void setDataSourceType(String newDataSourceType)
    {
        dataSourceType = newDataSourceType;
    }

    /**
     * Returns the value of '<em><b>dataType</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>dataType</b></em>' feature
     * @generated
     */
    public String getDataType()
    {
        return dataType;
    }

    /**
     * Sets the '{@link KnowledgeLibraryDto#getDataType() <em>dataType</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newDataType
     *            the new value of the '{@link KnowledgeLibraryDto#getDataType() dataType}'
     *            feature.
     * @generated
     */
    public void setDataType(String newDataType)
    {
        dataType = newDataType;
    }

    /**
     * Returns the value of '<em><b>remark</b></em>' feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @return the value of '<em><b>remark</b></em>' feature
     * @generated
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * Sets the '{@link KnowledgeLibraryDto#getRemark() <em>remark</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param newRemark
     *            the new value of the '{@link KnowledgeLibraryDto#getRemark() remark}' feature.
     * @generated
     */
    public void setRemark(String newRemark)
    {
        remark = newRemark;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIsManager()
    {
        return isManager;
    }

    public void setIsManager(String isManager)
    {
        this.isManager = isManager;
    }

    public List<CategoryNodeVo> getCategoryNodeVoList()
    {
        return categoryNodeVoList;
    }

    public void setCategoryNodeVoList(List<CategoryNodeVo> categoryNodeVoList)
    {
        this.categoryNodeVoList = categoryNodeVoList;
    }

    public String getTipName()
    {
        return tipName;
    }

    public void setTipName(String tipName)
    {
        this.tipName = tipName;
    }

    public String getProcInstId()
    {
        return procInstId;
    }

    public void setProcInstId(String procInstId)
    {
        this.procInstId = procInstId;
    }

}
