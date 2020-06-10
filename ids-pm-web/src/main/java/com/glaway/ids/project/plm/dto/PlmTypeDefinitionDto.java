package com.glaway.ids.project.plm.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.common.vdata.GLVData;
/**
 * 基础类型管理
 * @author fsk
 */
public class PlmTypeDefinitionDto extends GLVData{
    /**
     * 显示名称
     */
    private String typeName;
    /**
     * 内部名称
     */
    private String typeCode;
    /**
     * 父id
     */
    @JSONField(name = "_parentId")
    private String parentId;
    /**
     * 分组
     */
    private String groupCode;
    /**
     * 父类型名称
     */
    private String parentName;
    /**
     * 关联生命周期ID
     */
    private String lifecyclePolicyId;
    /**
     * 关联生命周期名称
     */
    private String lifecyclePolicyName;
    /**
     * 图标ID
     */
    private String iconId;
    /**
     * 图标名称
     */
    private String iconName;
    /**
     * 编码生成规则Id
     */
    private String generateRuleId;
    /**
     * 编码生成规则名称
     */
    private String generateRuleName;
    /**
     * 状态:0:禁用 ,1:启用
     */
    private String status;

    /**
     * 可实例化:0：可以，1：不可以
     */
    private String instantiation;

    /**
     * 可有子类型:0：可以，1：不可以
     */
    private String hasChildType;

    /**
     * 工作流定义Id
     */
    private String workflowDefId;

    /**
     * 是否被使用：0：否，1：是
     */
	/*@Basic
	private String isUse;*/

    /**
     * 是否构型项:0否，1：是
     */
    private String isConfigurationItems;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getLifecyclePolicyId() {
        return lifecyclePolicyId;
    }

    public void setLifecyclePolicyId(String lifecyclePolicyId) {
        this.lifecyclePolicyId = lifecyclePolicyId;
    }

    public String getLifecyclePolicyName() {
        return lifecyclePolicyName;
    }

    public void setLifecyclePolicyName(String lifecyclePolicyName) {
        this.lifecyclePolicyName = lifecyclePolicyName;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getGenerateRuleId() {
        return generateRuleId;
    }

    public void setGenerateRuleId(String generateRuleId) {
        this.generateRuleId = generateRuleId;
    }

    public String getGenerateRuleName() {
        return generateRuleName;
    }

    public void setGenerateRuleName(String generateRuleName) {
        this.generateRuleName = generateRuleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstantiation() {
        return instantiation;
    }

    public void setInstantiation(String instantiation) {
        this.instantiation = instantiation;
    }

    public String getHasChildType() {
        return hasChildType;
    }

    public void setHasChildType(String hasChildType) {
        this.hasChildType = hasChildType;
    }

    public String getWorkflowDefId() {
        return workflowDefId;
    }

    public void setWorkflowDefId(String workflowDefId) {
        this.workflowDefId = workflowDefId;
    }

    public String getIsConfigurationItems() {
        return isConfigurationItems;
    }

    public void setIsConfigurationItems(String isConfigurationItems) {
        this.isConfigurationItems = isConfigurationItems;
    }

}
