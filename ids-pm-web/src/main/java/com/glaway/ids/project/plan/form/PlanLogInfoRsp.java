/*
 * 文件名：PlanLogRsp.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：duanpengfei
 * 修改时间：2015年5月4日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.form;

import java.util.List;

/**
 * 〈返回的计划日志〉
 * 〈功能详细描述〉
 * @author duanpengfei
 * @version 2015年5月4日
 * @see PlanLogRsp
 * @since
 */

public class PlanLogInfoRsp {
    private Long total;
    private List<PlanLogInfo> rows;
    /**
     * @return Returns the total.
     */
    public Long getTotal() {
        return total;
    }
    /**
     * @param total The total to set.
     */
    public void setTotal(Long total) {
        this.total = total;
    }
    /**
     * @return Returns the rows.
     */
    public List<PlanLogInfo> getRows() {
        return rows;
    }
    /**
     * @param rows The rows to set.
     */
    public void setRows(List<PlanLogInfo> rows) {
        this.rows = rows;
    }
}
