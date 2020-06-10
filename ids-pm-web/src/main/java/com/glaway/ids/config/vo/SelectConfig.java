/*
 * 文件名：SelectConfig.java 版权：Copyright by www.glaway.com 描述： 修改人：xshen 修改时间：2015年4月7日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.glaway.ids.config.vo;


/**
 * 业务配置
 * 
 * @author wangyangzan
 * @version 2018年4月26日
 * @see SelectConfig
 * @since
 */
public class SelectConfig
{

    /** 业务配置选择ID */
    private String id = null;

    /** 业务配置编号 */
    private String no = null;

    /** 业务配置名称 */
    private String name = null;

    /** 业务配置类型 */
    private String configType = null;

    /** 业务配置描述 */
    private String configComment = null;

    /** 业务配置状态 */
    private String stopFlag = null;

    /** 业务配置是否选中 */
    private Boolean selected = false;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getNo()
    {
        return no;
    }

    public void setNo(String no)
    {
        this.no = no;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getConfigType()
    {
        return configType;
    }

    public void setConfigType(String configType)
    {
        this.configType = configType;
    }

    public String getConfigComment()
    {
        return configComment;
    }

    public void setConfigComment(String configComment)
    {
        this.configComment = configComment;
    }

    public String getStopFlag()
    {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag)
    {
        this.stopFlag = stopFlag;
    }

    public Boolean getSelected()
    {
        return selected;
    }

    public void setSelected(Boolean selected)
    {
        this.selected = selected;
    }

    public static SelectConfig transfer(BusinessConfig config)
    {
        SelectConfig s = new SelectConfig();
        s.setId(config.getId());
        s.setName(config.getName());
        s.setNo(config.getNo());
        return s;
    }
}
