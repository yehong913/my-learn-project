/*
 * 文件名：ProjectBoardVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2016年4月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.statisticalAnalysis.vo;

import java.io.Serializable;

/**
 * 计划达成率vo
 *
 * @author bxu
 */
public class ProjectBoardReportDataVo implements Serializable {

    private String number;

    private String name;

    private String action;

    private String order;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
