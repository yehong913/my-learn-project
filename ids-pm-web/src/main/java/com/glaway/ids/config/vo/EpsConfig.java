package com.glaway.ids.config.vo;


import java.util.ArrayList;
import java.util.List;

import com.glaway.foundation.businessobject.entity.BusinessObject;
import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * A representation of the model object '<em><b>EpsConfig</b></em>'. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
@SuppressWarnings("unused")
@Data
public class EpsConfig  extends GLVData {

    /**
     * <!-- begin-user-doc --> 项目分类名称<!-- end-user-doc -->
     * 
     * @generated
     */

    private String name = null;

    /**
     * <!-- begin-user-doc -->编号 <!-- end-user-doc -->
     * 
     * @generated
     */

    private String no = null;

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

    /**
     * <!-- begin-user-doc -->状态 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String stopFlag = null;

    /**
     * <!-- begin-user-doc -->配置描述 <!-- end-user-doc -->
     * 
     * @generated
     */
    private String configComment = "";
    /**
     * 项目分类排序属性
     */
    private String rankQuality= null;

    /**
     * <!-- begin-user-doc -->子孙节点 <!-- end-user-doc -->
     *
     * @generated
     */
    private List<EpsConfig> children = new ArrayList<EpsConfig>();

    
    private String result = null;
    

    /**
     * Adds to the <em>children</em> feature.
     *
     * @param childrenValue
     *            the value to add
     * @return true if the value is added to the collection (it was not yet
     *         present in the collection), false otherwise
     * @generated
     */
    public boolean addToChildren(EpsConfig childrenValue) {
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
    public boolean removeFromChildren(EpsConfig childrenValue) {
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

}
