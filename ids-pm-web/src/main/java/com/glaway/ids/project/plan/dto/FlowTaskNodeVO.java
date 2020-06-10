package com.glaway.ids.project.plan.dto;

import java.util.List;

/**
 * 流程变更与外部系统交互节点信息
 * @author blcao
 */
public class FlowTaskNodeVO {
	
	private String oldTaskId;
	
	private String newTaskId;
	
	private String nameStandardId;
	
	private List<FlowTaskDeliverVO> delivers;

	public String getOldTaskId() {
		return oldTaskId;
	}

	public void setOldTaskId(String oldTaskId) {
		this.oldTaskId = oldTaskId;
	}

	public String getNewTaskId() {
		return newTaskId;
	}

	public void setNewTaskId(String newTaskId) {
		this.newTaskId = newTaskId;
	}

	public String getNameStandardId() {
		return nameStandardId;
	}

	public void setNameStandardId(String nameStandardId) {
		this.nameStandardId = nameStandardId;
	}

	public List<FlowTaskDeliverVO> getDelivers() {
		return delivers;
	}

	public void setDelivers(List<FlowTaskDeliverVO> delivers) {
		this.delivers = delivers;
	}
	
}
