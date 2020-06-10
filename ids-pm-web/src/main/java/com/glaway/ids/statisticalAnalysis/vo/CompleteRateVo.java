/*
 * 文件名：CompleteRateVo.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：syc
 * 修改时间：2016年4月7日
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
public class CompleteRateVo implements Serializable {

    /** 任务类型（统计方式） */
    private String type;

    /** 达成率 */
    private String rate;

    /** 总计划数 */
    private String total;

    /** 实际达成树数 */
    private String complete;

    /** 正常未完成 */
    private String uncomplete;

    /** 延期未完成 */
    private String delay;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getUncomplete() {
        return uncomplete;
    }

    public void setUncomplete(String uncomplete) {
        this.uncomplete = uncomplete;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

}
