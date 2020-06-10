/*
 * 文件名：GanttResponse.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年5月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.gantt.vo;


import java.util.ArrayList;
import java.util.List;


public class GanttResponse {

    private List<GanttData> data = new ArrayList<GanttData>();

    private GanttRelates collections;

    public List<GanttData> getData() {
        return data;
    }

    public void setData(List<GanttData> data) {
        this.data = data;
    }

    public GanttRelates getCollections() {
        return collections;
    }

    public void setCollections(GanttRelates collections) {
        this.collections = collections;
    }

}
