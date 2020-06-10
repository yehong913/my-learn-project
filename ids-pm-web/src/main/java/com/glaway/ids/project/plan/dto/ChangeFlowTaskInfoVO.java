/*
 * 文件名：ChangeFlowTaskInfoVO.java
 * 版权：Copyright by www.glaway.com
 * 描述：
 * 修改人：blcao
 * 修改时间：2016年7月28日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.glaway.ids.project.plan.dto;

import java.util.ArrayList;
import java.util.List;

import com.glaway.ids.rdtask.task.vo.ChangeFlowTaskCellConnectVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskPreposeVo;
import com.glaway.ids.rdtask.task.vo.FlowTaskVo;

/**
 * @author wqb
 */
public class ChangeFlowTaskInfoVO {

    /**
     * 流程变更-节点信息
     */
    private List<FlowTaskVo> changeFlowTaskList = new ArrayList<FlowTaskVo>();

    /**
     * 流程变更-前后置关系信息
     */
    private List<FlowTaskPreposeVo> flowTaskPreposeList = new ArrayList<FlowTaskPreposeVo>();

    /**
     * 流程变更-前后指向关系信息
     */
    private List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList = new ArrayList<ChangeFlowTaskCellConnectVo>();

    public List<FlowTaskVo> getChangeFlowTaskList() {
        return changeFlowTaskList;
    }

    public void setChangeFlowTaskList(List<FlowTaskVo> changeFlowTaskList) {
        this.changeFlowTaskList = changeFlowTaskList;
    }

    public List<FlowTaskPreposeVo> getFlowTaskPreposeList() {
        return flowTaskPreposeList;
    }

    public void setFlowTaskPreposeList(List<FlowTaskPreposeVo> flowTaskPreposeList) {
        this.flowTaskPreposeList = flowTaskPreposeList;
    }

    public List<ChangeFlowTaskCellConnectVo> getChangeFlowTaskConnectList() {
        return changeFlowTaskConnectList;
    }

    public void setChangeFlowTaskConnectList(List<ChangeFlowTaskCellConnectVo> changeFlowTaskConnectList) {
        this.changeFlowTaskConnectList = changeFlowTaskConnectList;
    }
}
