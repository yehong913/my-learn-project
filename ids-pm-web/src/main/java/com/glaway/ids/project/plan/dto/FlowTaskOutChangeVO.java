package com.glaway.ids.project.plan.dto;

import java.util.List;
import java.util.Map;

/**
 * 流程变更与外部系统交互Vo
 * @author blcao
 */
public class FlowTaskOutChangeVO {
	
	private String parentPlanId;
	
	private List<FlowTaskNodeVO> nodes;
	
	private Map<String,String> planIdAndRdTaskId;

	public String getParentPlanId() {
		return parentPlanId;
	}

	public void setParentPlanId(String parentPlanId) {
		this.parentPlanId = parentPlanId;
	}

	public List<FlowTaskNodeVO> getNodes() {
		return nodes;
	}

	public void setNodes(List<FlowTaskNodeVO> nodes) {
		this.nodes = nodes;
	}

    public Map<String, String> getPlanIdAndRdTaskId() {
        return planIdAndRdTaskId;
    }

    public void setPlanIdAndRdTaskId(Map<String, String> planIdAndRdTaskId) {
        this.planIdAndRdTaskId = planIdAndRdTaskId;
    }

}
