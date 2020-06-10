/*
 * 文件名：ReferenceTypeConstants.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年7月10日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.constant;

/**
 * 〈知识管理常量〉
 * 
 * @author duanpengfei
 * @version 2015年11月13日
 * @see KnowledgeConstants
 * @since
 */
public class KnowledgeConstants {

    /** 知识库 */
    public static final String KNOWLEDGE_LIBRARY_CHI = "知识库";

    public static final String KNOWLEDGE_LIBRARY_ROOT_ID = "ROOT";

    public static final String IN_APPROVE = "1";

    public static final String NOT_IN_APPROVE = "0";

    /** 知识分类 */
    public static final String KNOWLEDGE_CATEGORY_CHI = "知识分类";

    public static final String KNOWLEDGE_CATEGORY_ROOT_ID = "ROOT";

    /** 高级搜索 */
    // 并且
    public static final String LOGICAL_OPERATOR_AND = "AND";

    // 或者
    public static final String LOGICAL_OPERATOR_OR = "OR";

    // 包含
    public static final String SEARCH_RESULT_IN = "IN";

    // 不包含
    public static final String SEARCH_RESULT_NOTIN = "NOTIN";

    // 标题
    public static final String SEARCH_CONDITION_TITLE = "title";

    // 摘要
    public static final String SEARCH_CONDITION_ABSTRUCT = "abstruct";

    // 更新时间
    public static final String SEARCH_CONDITION_UPDATETIME = "updatetime";

    // 摘要
    public static final String SEARCH_CONDITION_AUTHORNAME = "authorName";

    // 默认
    public static final String SEARCH_CONDITION_DEFAULT = "default";

    // 发布
    public static final String SEARCH_CONDITION_ISSUE = "issue";

    // Comment 评论
    public static final String SEARCH_CONDITION_COMMENT = "comment";

    // 收藏 favorite
    public static final String SEARCH_CONDITION_FAVORITE = "favorite";

    // 清除部门积分
    public static final String DELETE_DEPT_INTEGREL = "dept";

    // 清除个人积分
    public static final String DELETE_PERSONAGE_INTEGREL = "personage";

    // 升序
    public static final String SORT_TYPE_ASC = "ASC";

    // 降序
    public static final String SORT_TYPE_DESC = "DESC";

    // 发表帖子
    public static final String ARTICLE_TYPE_ISSUE = "publish";

    // 回复帖子
    public static final String ARTICLE_TYPE_REPLY = "reply";

    // 知识板块管理员roleCode
    public static final String ARTICLE_KNOWMANAGER_ROLECODE = "knowledgeManager";
    
    // 知识分类结束标志
    public static final String KNOWLEDGE_CLASSIFY_END_FLAG = "ROOT_";
    
    /**
     * 知识管理的session;
     */
    public static final String IMPORT_KNOWLEDGE_SESSION = "import_knowledge_session_";  
    
    /** 上传附件 */
    public static final String KCY_KNOWLEDGE_ITEM= "KnowledgeItemAttachment";

    /**
     * 知识库外部内部区分
     */
    // 知识库-内部
    public static final String KNOWLEDGE_LIBRARY_IN = "内部";  

    // 知识库-外部
    public static final String KNOWLEDGE_LIBRARY_OUT = "外部";  
    
    // 条目code标志
    public static final String KNOWLEDGE_ITEM_CODE = "knowledgeItem";
    
    /**
     *  知识条目审批流程
     */
    public static final String KNOWLEDGEITEM_START_PROCESS = "knowledgeItemApproveProcess";
    
    /** 知识条目审批流程*/
    public static final String KNOWLEDGEITEM_START_DISPLAYNAME = "知识条目审批流程";
    
    /**
     *  知识借阅审批流程
     */
    public static final String KNOWLEDGEBORROW_START_PROCESS = "knowledgeBorrowProcess";
    
    /** 知识借阅审批流程*/
    public static final String KNOWLEDGEBORROW_START_DISPLAYNAME = "知识借阅审批流程";
    
    /** 知识库模版存放路径 */
    public static final String KCY_KNOWLEDGELIBTEMP_ATTACHMENT_PATH = "knowledgeItem/attachment";
    
    /** 知识库模版附件 */
    public static final String KCY_KNOWLEDGELIBTEMP_ATTACHMENT= "knowledgeLibTempAttachment";
    
    
    /** 积分操作对象类型*/
    public static final String SCORE_OPERATE_TARGET="knowledgeLibrary";
    
    /** 积分操作对象 */
    public static final String SCORE_OBJECT__KNOWLEDGEITEM="知识文档";
    
    /** 积分操作对象 */
    public static final String SCORE_OBJECT_PUBLISHER="发布者";
    
    /** 积分操作对象 */
    public static final String SCORE_OBJECT_COMMENTER="评论者";
    
    /** 积分操作对象 */
    public static final String SCORE_OBJECT_SHARER="分享者";
    
    /** 积分操作对象 */
    public static final String SCORE_OBJECT_COLLECTOR="收藏者";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_GOOD="点赞文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_PUBLISH="发布文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_ABANDON="废弃文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_SHARE="分享文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_EVALUATE="评价文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_COLLECT="收藏文档";
    
    /** 操作 */
    public static final String KCY_KNOWLEDGE_OPERATION_DOWNLOAD="下载文档";
    
    
}
