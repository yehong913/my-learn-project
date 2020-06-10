package com.glaway.ids.project.menu.dto;


import com.glaway.foundation.common.vdata.GLVData;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目模板树
 * 
 * @author wangshen
 */
@SuppressWarnings("serial")
public class ProjTemplateMenuDto extends GLVData {

    /**
     * 显示名称
     */
    private String text;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 文件夹开关
     */
    private String state;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 是否开启
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子目录结构
     */
    private List<ProjTemplateMenuDto> children = new ArrayList<ProjTemplateMenuDto>();

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public List<ProjTemplateMenuDto> getChildren() {
        return children;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setChildren(List<ProjTemplateMenuDto> children) {
        this.children = children;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
