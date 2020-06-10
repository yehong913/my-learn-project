package com.glaway.ids.config.vo;

import java.util.List;


/**
 * 文档类型设置VO对象（用于文档类型导出）
 * @author likaiyong
 * @version 2018年8月9日16:45:18
 * @see RepFileTypeConfigVo
 *
 */
public class RepFileTypeConfigVo {

    /**
     * 编号
     */
    private String code;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 编号规则
     */
    private String generateRuleName;
    
    /**
     * 规则说明
     */
    private String generateRuleDesc;
    
    /**
     * 状态
     */
    private String status;
    
   /**
    * 备注
    */
    private String remark;
    
    /**
     * 审批环节
     */
    private List<BpmnTaskVo> list;
    
    /**
     * 自定义属性（多个用分号隔开）
     */
    private String customAttr;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenerateRuleName() {
        return generateRuleName;
    }

    public void setGenerateRuleName(String generateRuleName) {
        this.generateRuleName = generateRuleName;
    }

    public String getGenerateRuleDesc() {
        return generateRuleDesc;
    }

    public void setGenerateRuleDesc(String generateRuleDesc) {
        this.generateRuleDesc = generateRuleDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BpmnTaskVo> getList() {
        return list;
    }

    public void setList(List<BpmnTaskVo> list) {
        this.list = list;
    }

    public String getCustomAttr() {
        return customAttr;
    }

    public void setCustomAttr(String customAttr) {
        this.customAttr = customAttr;
    }
    
}
