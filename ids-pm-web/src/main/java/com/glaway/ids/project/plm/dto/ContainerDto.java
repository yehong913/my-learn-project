package com.glaway.ids.project.plm.dto;

import java.util.List;
import javax.persistence.Column;
import com.alibaba.fastjson.annotation.JSONField;
import com.glaway.foundation.common.vdata.GLVData;

/**
 * 容器基类
 * @author fsk
 *
 */
public class ContainerDto extends GLVData  {
    /**
     * 容器名称
     */
    private String name;
    /**
     * 容器代码
     */
    private String code;
    /**
     * 容器描述
     */
    private String description;
    /**
     * 容器对象类型
     */
    private String className;
    /**
     * 容器软类型
     */
    private String typeName;
    /**
     * 容器类型ID
     */
    private String typeId;

    /**
     * 根容器ID
     */
    private String rootId;

    /**
     * 子容器列表
     */
    private List<ContainerDto> childrenContainer;
    /**
     * 父容器
     */
    private ContainerDto parentContainer;
    /**
     * 根容器
     */
/*	@Transient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rootId")
	private Container rootContainer;*/


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

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRootId() {
        return rootId;
    }
    public void setRootId(String rootId) {
        this.rootId = rootId;
    }


    @JSONField(serialize=false)
    public List<ContainerDto> getChildrenContainer() {
        return childrenContainer;
    }
    public void setChildrenContainer(List<ContainerDto> childrenContainer) {
        this.childrenContainer = childrenContainer;
    }

    @JSONField(serialize=false)
    public ContainerDto getParentContainer() {
        return parentContainer;
    }
    public void setParentContainer(ContainerDto parentContainer) {
        this.parentContainer = parentContainer;
    }
}
