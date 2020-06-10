/*
 * 文件名：SearchCatgoryNode.java 版权：Copyright by www.glaway.com 描述： 修改人：Administrator 修改时间：2016年1月27日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.glaway.ids.common.vo;


import lombok.Data;

import java.util.List;


/**
 * 〈知识分类查询节点〉 〈功能详细描述〉
 * 
 * @author 王广铭
 * @version 2015年7月27日
 * @see SearchCategoryNode
 * @since
 */
@Data
public class SearchCategoryNode
{
    /** 知识分类根节点 */
    private List<KnowledgeCatgoryDto> roots;

    /** 知识分类数据 */
    private List<KnowledgeCatgoryDto> categories;

}
