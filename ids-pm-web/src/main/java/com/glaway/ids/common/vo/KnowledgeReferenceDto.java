package com.glaway.ids.common.vo;


import com.glaway.foundation.common.vdata.GLVData;
import lombok.Data;


/**
 * 知识参考表
 * 
 * @author blcao
 * @version 2017年10月30日
 * @see KnowledgeReferenceDto
 * @since
 */
@Data
public class KnowledgeReferenceDto extends GLVData
{

    /**
     * 关联业务对象Id
     */
    private String taskId = null;

    /**
     * 知识code
     */
    private String code = null;

    /**
     * 知识库Id
     */
    private String libId = null;

    /**
     * 知识库
     */
    private KnowledgeLibraryDto library;

    /**
     * 类型 研发流程模板参考：flowTemplate 研发流程分解：flowResolve 研发任务：planTask
     */
    private String type = null;

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getLibId()
    {
        return libId;
    }

    public void setLibId(String libId)
    {
        this.libId = libId;
    }

    public KnowledgeLibraryDto getLibrary()
    {
        return library;
    }

    public void setLibrary(KnowledgeLibraryDto library)
    {
        this.library = library;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
