package com.glaway.ids.project.projectmanager.dto;

import com.glaway.foundation.common.vdata.GLVData;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: ResourceFolder
 * @Date: 2019/6/11-11:00
 * @since
 */
public class ResourceFolderDto extends GLVData {
    /**
     * <!-- begin-user-doc -->资源目录名称
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->父目录ID
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String parentId = null;

    /**
     * <!-- begin-user-doc -->路径
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String path = null;

    /**
     * <!-- begin-user-doc -->节点顺序
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String nodeOrder = null;

    /**
     * <!-- begin-user-doc -->排序
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private long orderNum = 0;

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 子孙节点
     */
    @Transient()
    private List<ResourceFolderDto> children = new ArrayList<ResourceFolderDto>();

    /**
     * Returns the value of '<em><b>children</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of '<em><b>children</b></em>' feature
     */
    public List<ResourceFolderDto> getChildren() {
        return children;
    }

    /**
     * Adds to the <em>children</em> feature.
     *
     * @param childrenValue
     *            the value to add
     * @return true if the value is added to the collection (it was not yet present in the
     *         collection), false otherwise
     */
    public boolean addToChildren(ResourceFolderDto childrenValue) {
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
     */
    public boolean removeFromChildren(ResourceFolderDto childrenValue) {
        if (children.contains(childrenValue)) {
            boolean result = children.remove(childrenValue);
            return result;
        }
        return false;
    }

    /**
     * Clears the <em>children</em> feature.
     */
    public void clearChildren() {
        while (!children.isEmpty()) {
            removeFromChildren(children.iterator().next());
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newChildren
     */
    public void setChildren(List<ResourceFolderDto> newChildren) {
        children = newChildren;
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
     * Returns the value of '<em><b>parentId</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of '<em><b>parentId</b></em>' feature
     * @generated
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newParentId
     * @generated
     */
    public void setParentId(String newParentId) {
        parentId = newParentId;
    }

    /**
     * Returns the value of '<em><b>path</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of '<em><b>path</b></em>' feature
     * @generated
     */
    public String getPath() {
        return path;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newPath
     * @generated
     */
    public void setPath(String newPath) {
        path = newPath;
    }

    /**
     * Returns the value of '<em><b>nodeOrder</b></em>' feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of '<em><b>nodeOrder</b></em>' feature
     * @generated
     */
    public String getNodeOrder() {
        return nodeOrder;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param newNodeOrder
     * @generated
     */
    public void setNodeOrder(String newNodeOrder) {
        nodeOrder = newNodeOrder;
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
        return "ResourceFolder " + " [name: " + getName() + "]" + " [parentId: " + getParentId()
                + "]" + " [path: " + getPath() + "]" + " [nodeOrder: " + getNodeOrder() + "]";
    }
}
