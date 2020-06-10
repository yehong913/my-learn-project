/*
 * 文件名：ResourceUsedRate.java 版权：Copyright by www.glaway.com 描述： 修改人：syc 修改时间：2016年3月30日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.vo;

/**
 * ResourceUsedRateVO
 * 
 * @author xiongshitian
 * @version 2018年4月26日
 * @see ResourceUsedRateVO
 * @since
 */
public class ResourceUsedRateVO
{

    /**
     * 日期,x坐标显示
     */
    private String date;

    /**
     * 已使用率,y坐标显示
     */
    private String usedRate;

    /**
     * 预估使用率,y坐标显示
     */
    private String willBeUsed;

    /**
     * 预警使用率,y坐标显示
     */
    private String occupationWarn;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getUsedRate()
    {
        return usedRate;
    }

    public void setUsedRate(String usedRate)
    {
        this.usedRate = usedRate;
    }

    public String getWillBeUsed()
    {
        return willBeUsed;
    }

    public void setWillBeUsed(String willBeUsed)
    {
        this.willBeUsed = willBeUsed;
    }

    public String getOccupationWarn()
    {
        return occupationWarn;
    }

    public void setOccupationWarn(String occupationWarn)
    {
        this.occupationWarn = occupationWarn;
    }

    @Override
    public String toString()
    {
        return "ResourceUsedRateVO[date=" + this.getDate() + ", occupationWarn="
               + this.getOccupationWarn() + ", usedRate=" + this.getUsedRate() + ", willBeUsed="
               + this.getWillBeUsed() + "]";
    }
}
