package com.glaway.ids.common.vo;

import java.util.List;

/**
 * 分类的Vo
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wqb
 * @version 2018年9月18日
 * @see CategoryNodeVo
 * @since
 */
public class CategoryNodeVo {

    /**
     * Id<br>
     */
    private String id;

    /**
     * 父节点Id<br>
     */
    private String pid;

    /**
     * 显示名<br>
     */
    private String name;
    
    /**
     * tip显示名<br>
     */
    private String tipName;
    
    /**
     * 
     */
    private List<CategoryNodeVo> categoryNodeVoList = null ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public List<CategoryNodeVo> getCategoryNodeVoList() {
        return categoryNodeVoList;
    }

    public void setCategoryNodeVoList(List<CategoryNodeVo> categoryNodeVoList) {
        this.categoryNodeVoList = categoryNodeVoList;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipName() {
        return tipName;
    }

    public void setTipName(String tipName) {
        this.tipName = tipName;
    }
}
