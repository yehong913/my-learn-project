package com.glaway.ids.common.constant;

/**
 * 〈知识接口常量〉
 *
 * @author duanpengfei
 * @version 2015年8月13日
 * @see KnowledgeWebServiceConstants
 * @since
 */
public class KnowledgeWebServiceConstants {

    /**
     * 删除研发流程模板节点、流程分解的节点应的关联知识记录
     */
    public static final String KNOWLEDGE_OPERATION_DELETE = "delete";

    /**
     * 过滤掉研发任务对应的推荐的知识
     */
    public static final String KNOWLEDGE_OPERATION_FILTER = "filter";

    /**
     * 调用研发流程模板分解产生研发任务时，给研发任务参考初始化
     */
    public static final String KNOWLEDGE_OPERATION_RESOLVE = "resolve";

    /**
     * 流程变更或计划变更生效时，给研发任务参考初始化
     */
    public static final String KNOWLEDGE_OPERATION_CHANGED = "changed";

    /**
     * 知识参考类型-流程模板
     */
    public static final String KNOWLEDGE_REFERENCE_TYPE_FLOWTEMPLATE = "flowTemplate";

    /**
     * 知识参考类型-流程分解
     */
    public static final String KNOWLEDGE_REFERENCE_TYPE_FLOWRESOLVE = "flowResolve";

    /**
     * 知识参考类型-变更
     */
    public static final String KNOWLEDGE_REFERENCE_TYPE_CHANGE = "change";

    /**
     * 知识参考类型-研发任务
     */
    public static final String KNOWLEDGE_REFERENCE_TYPE_PLANTASK = "planTask";


}
