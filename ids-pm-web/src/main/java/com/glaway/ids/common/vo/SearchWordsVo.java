/*
 * 文件名：SearchWordsVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2016年2月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.common.vo;

public class SearchWordsVo {
    
    private String searchWords;
    
    private String userId;
    
    private String deptId;
    
    private String type;
    
    /**
     * 查询状态
     */
    private String searchQuery;

    public String getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(String searchWords) {
        this.searchWords = searchWords;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSearchQuery()
    {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery)
    {
        this.searchQuery = searchQuery;
    }
}
