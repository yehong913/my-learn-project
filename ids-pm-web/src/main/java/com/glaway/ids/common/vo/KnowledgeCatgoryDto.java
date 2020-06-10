package com.glaway.ids.common.vo;


import com.glaway.foundation.businessobject.entity.BusinessObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A representation of the model object '<em><b>KnowledgeCatgoryDto</b></em>'.
 * <!-- begin-user-doc
 * -->知识--分类管理 <!-- end-user-doc -->
 *
 * @generated
 */
public class KnowledgeCatgoryDto extends BusinessObject {

    /**
     * <!-- begin-user-doc --> 名称<!-- end-user-doc -->
     *
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc --> 标签<!-- end-user-doc -->
     *
     * @generated
     */
    private String labels = null;

    /**
     * <!-- begin-user-doc --> 父节点<!-- end-user-doc -->
     *
     * @generated
     */
    private String parentId = null;

    /**
     * <!-- begin-user-doc --> 路径<!-- end-user-doc -->
     *
     * @generated
     */
    private String path = null;

    /**
     * <!-- begin-user-doc -->排序字段 <!-- end-user-doc -->
     *
     * @generated
     */
    private String sortOrder = null;


    /**
     * 知识库Id
     */
    private String libraryId = null;

    /**
     * color
     */
    private String result;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private List<KnowledgeCatgoryDto> children = new ArrayList<KnowledgeCatgoryDto>();

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
     * Sets the '{@link KnowledgeCatgoryDto#getName() <em>name</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newName
     *            the new value of the '{@link KnowledgeCatgoryDto#getName() name}' feature.
     * @generated
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Returns the value of '<em><b>labels</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>labels</b></em>' feature
     * @generated
     */
    public String getLabels() {
        return labels;
    }

    /**
     * Sets the '{@link KnowledgeCatgoryDto#getLabels() <em>labels</em>}' feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param newLabels
     *            the new value of the '{@link KnowledgeCatgoryDto#getLabels() labels}' feature.
     * @generated
     */
    public void setLabels(String newLabels) {
        labels = newLabels;
    }

    /**
     * Returns the value of '<em><b>parentId</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>parentId</b></em>' feature
     * @generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Sets the '{@link KnowledgeCatgoryDto#getParentId() <em>parentId</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newParentId
     *            the new value of the '{@link KnowledgeCatgoryDto#getParentId() parentId}' feature.
     * @generated
     */
    public void setParentId(String newParentId) {
        parentId = newParentId;
    }

    /**
     * Returns the value of '<em><b>path</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>path</b></em>' feature
     * @generated
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the '{@link KnowledgeCatgoryDto#getPath() <em>path</em>}' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newPath
     *            the new value of the '{@link KnowledgeCatgoryDto#getPath() path}' feature.
     * @generated
     */
    public void setPath(String newPath) {
        path = newPath;
    }

    /**
     * Returns the value of '<em><b>sortOrder</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>sortOrder</b></em>' feature
     * @generated
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the '{@link KnowledgeCatgoryDto#getSortOrder() <em>sortOrder</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newSortOrder
     *            the new value of the '{@link KnowledgeCatgoryDto#getSortOrder() sortOrder}' feature.
     * @generated
     */
    public void setSortOrder(String newSortOrder) {
        sortOrder = newSortOrder;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Returns the value of '<em><b>children</b></em>' feature.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of '<em><b>children</b></em>' feature
     * @generated
     */
    public List<KnowledgeCatgoryDto> getChildren() {
        return children;
    }

    /**
     * Adds to the <em>children</em> feature.
     *
     * @param childrenValue
     *            the value to add
     * @return true if the value is added to the collection (it was not yet present in the
     *         collection), false otherwise
     * @generated
     */
    public boolean addToChildren(KnowledgeCatgoryDto childrenValue) {
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
     * @return true if the value is removed from the collection (it existed in the collection
     *         before removing), false otherwise
     * @generated
     */
    public boolean removeFromChildren(KnowledgeCatgoryDto childrenValue) {
        if (children.contains(childrenValue)) {
            boolean result = children.remove(childrenValue);
            return result;
        }
        return false;
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

    /**
     * Sets the '{@link KnowledgeCatgoryDto#getChildren() <em>children</em>}' feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param newChildren
     *            the new value of the '{@link KnowledgeCatgoryDto#getChildren() children}' feature.
     * @generated
     */
    public void setChildren(List<KnowledgeCatgoryDto> newChildren) {
        children = newChildren;
    }

    /**
     * A toString method which prints the values of all EAttributes of this instance. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return "KnowledgeCatgoryDto [name=" + name + ", labels=" + labels + ", parentId=" + parentId
                + ", path=" + path + ", sortOrder=" + sortOrder + ", libraryId=" + libraryId
                + ", result=" + result + ", children=" + children + ", namePath=" + namePath + "]";
    }

    private String namePath = "";


    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }



}
