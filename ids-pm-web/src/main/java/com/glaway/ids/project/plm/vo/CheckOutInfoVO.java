package com.glaway.ids.project.plm.vo;

import java.util.Date;


/**
 * 检出对象信息VO
 *
 * @author blcao
 * @version 2018年10月16日
 * @see CheckOutInfoVO
 * @since
 */
public class CheckOutInfoVO {

    /**
     * 对象Id
     */
    private String id = null;

    /**
     * 对象ClassName
     */
    private String objectClass = null;

    /**
     * 对象Type
     */
    private String objectType = null;

    /**
     * 对象名称
     */
    private String name = null;

    /**
     * 对象编号
     */
    private String code = null;

    /**
     * 存放位置ID
     */
    private String locationId = null;

    /**
     * 存放位置ClassName
     */
    private String locationClass = null;

    /**
     * 生命周期版本
     */
    private String bizVersion = null;

    /**
     * 生命周期状态
     */
    private String bizCurrent = null;

    /**
     * 状态展示
     */
    private String status = null;

    /**
     * 关联Master对象Id
     */
    private String bizId = null;

    /**
     * 是否有视图(true:是, false:否)
     */
    private String viewIsNull = "false";

    /**
     * 视图id
     */
    private String viewId = null;

    /**
     * 视图名称
     */
    private String viewName = null;

    /**
     * 创建人ID
     */
    private String createBy = null;

    /**
     * 创建人名称
     */
    private String createName = null;

    /**
     * 创建时间
     */
    private Date createTime = null;

    /**
     * 创建人全名
     */
    private String createFullName = null;

    /**
     * 修改人ID
     */
    private String updateBy = null;

    /**
     * 修改人名称
     */
    private String updateName = null;

    /**
     * 修改人全名
     */
    private Date updateTime = null;

    /**
     * 修改时间
     */
    private String updateFullName = null;

    /**
     * 上下文ClassName
     */
    private String contextClass = null;

    /**
     * 上下文ID
     */
    private String contextId = null;

    /**
     * 上下文名称
     */
    private String contextName = null;

    /**
     * 权限控制容器ID
     */
    private String roleContainerId = null;

    /**
     * 可用状态
     */
    private String statusList = null;

    /**
     * 备注
     */
    private String remark = null;

    private String actiRecordId = null;

    private String checkOutInfo = null;

    /**
     * 变更类型
     */
    private String thecategory = null;

    /**
     * 优先级
     */
    private String prioritylevel = null;

    /**
     * 复杂性
     */
    private String complexity = null;

    /**
     * 需要时间
     */
    private Date needDate = null;

    /**
     * 受影响部件来源id
     */
    private String partSourceId = null;

    /**
     * 产生对象来源：换号：number、升版 :version
     */
    private String productSource = null;

    /**
     * 对象显示名称
     */

    private String typename;
    /**
     * 对象显示图标
     */
    private String typeicon;

    /**
     * 生命周期策略id
     */
    private String policyId;

    /**
     * 是否是副本
     */
    private String isTranscript;

    /**
     * 追踪编号
     */
    private String traceCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationClass() {
        return locationClass;
    }

    public void setLocationClass(String locationClass) {
        this.locationClass = locationClass;
    }

    public String getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(String bizVersion) {
        this.bizVersion = bizVersion;
    }

    public String getBizCurrent() {
        return bizCurrent;
    }

    public void setBizCurrent(String bizCurrent) {
        this.bizCurrent = bizCurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getViewIsNull() {
        return viewIsNull;
    }

    public void setViewIsNull(String viewIsNull) {
        this.viewIsNull = viewIsNull;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateFullName() {
        return createFullName;
    }

    public void setCreateFullName(String createFullName) {
        this.createFullName = createFullName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateFullName() {
        return updateFullName;
    }

    public void setUpdateFullName(String updateFullName) {
        this.updateFullName = updateFullName;
    }

    public String getContextClass() {
        return contextClass;
    }

    public void setContextClass(String contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getRoleContainerId() {
        return roleContainerId;
    }

    public void setRoleContainerId(String roleContainerId) {
        this.roleContainerId = roleContainerId;
    }

    public String getStatusList() {
        return statusList;
    }

    public void setStatusList(String statusList) {
        this.statusList = statusList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActiRecordId() {
        return actiRecordId;
    }

    public void setActiRecordId(String actiRecordId) {
        this.actiRecordId = actiRecordId;
    }

    public String getCheckOutInfo() {
        return checkOutInfo;
    }

    public void setCheckOutInfo(String checkOutInfo) {
        this.checkOutInfo = checkOutInfo;
    }

    public String getThecategory() {
        return thecategory;
    }

    public void setThecategory(String thecategory) {
        this.thecategory = thecategory;
    }

    public String getPrioritylevel() {
        return prioritylevel;
    }

    public void setPrioritylevel(String prioritylevel) {
        this.prioritylevel = prioritylevel;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public Date getNeedDate() {
        return needDate;
    }

    public void setNeedDate(Date needDate) {
        this.needDate = needDate;
    }

    public String getPartSourceId() {
        return partSourceId;
    }

    public void setPartSourceId(String partSourceId) {
        this.partSourceId = partSourceId;
    }

    public String getProductSource() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource = productSource;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypeicon() {
        return typeicon;
    }

    public void setTypeicon(String typeicon) {
        this.typeicon = typeicon;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getIsTranscript() {
        return isTranscript;
    }

    public void setIsTranscript(String isTranscript) {
        this.isTranscript = isTranscript;
    }

    public String getTraceCode() {
        return traceCode;
    }

    public void setTraceCode(String traceCode) {
        this.traceCode = traceCode;
    }

}
