package com.glaway.ids.planGeneral.tabCombinationTemplate.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: 组合模板信息vo
 * @author: sunmeng
 * @ClassName: CombinationTemplateVo
 * @Date: 2019/8/29-20:34
 * @since
 */
@Getter
@Setter
public class CombinationTemplateVo implements Serializable {

    private String id;

    //名称
    private String name;

    //显示权限
    private String displayAccess;

    //页签模板Id
    private String typeId;

    //页签模板
    private String tabName;

    //页签类型
    private String tabType;

    //页签顺序
    private int orderNum;
    
    //url
    private String url;

    //所属模块
    private String projectModel;

    //页面显示方式(sql编辑--0，URL接口--1)
    private String displayUsage;

    //版本信息
    private String bizVersion;
}
