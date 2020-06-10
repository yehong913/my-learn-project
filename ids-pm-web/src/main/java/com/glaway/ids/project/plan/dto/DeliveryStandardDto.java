package com.glaway.ids.project.plan.dto;

import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;

/**
 * @Description: 〈一句话功能简述〉
 * @author: sunmeng
 * @ClassName: DeliveryStandardDto
 * @Date: 2019/6/10-19:20
 * @since
 */
@Data
public class DeliveryStandardDto extends GLVData {

    
    private String useObjectId = null;
    /**
     * <!-- begin-user-doc -->编号
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String no = null;

    /**
     * <!-- begin-user-doc -->交付项编号
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String name = null;

    /**
     * <!-- begin-user-doc -->备注
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String remark = null;

    /**
     * <!-- begin-user-doc -->状态
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private String stopFlag = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private String avaliable = "1";

    /**
     * <!-- begin-user-doc -->文档类型ID<!-- end-user-doc -->
     *
     * @generated
     */
    private String docTypeId = null;

    /**
     * <!-- begin-user-doc -->文档类型名称<!-- end-user-doc -->
     *
     * @generated
     */
    private String docTypeName = null;
    
    /**
     * <!-- begin-user-doc -->交付项ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String deliverId = null;
    
    private String cellName = null;
    
    /**
     * <!-- begin-user-doc -->来源节点ID
     * <!-- end-user-doc -->
     * 
     * @generated
     */
    private String originObjectId = null;
    
    private String isPreOutput = null;


}
