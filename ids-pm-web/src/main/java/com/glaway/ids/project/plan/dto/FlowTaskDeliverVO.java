package com.glaway.ids.project.plan.dto;

/**
 * 流程變更外部系統交互输入输出VO
 * @author Administrator
 *
 */
public class FlowTaskDeliverVO {
	
	private String inOrOut;
	
	private String deliverId;

	private String newId;
	
	private String sourceId;
	
	private String sourceDeliverSeq;

	public String getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}

	public String getDeliverId() {
		return deliverId;
	}

	public void setDeliverId(String deliverId) {
		this.deliverId = deliverId;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceDeliverSeq() {
		return sourceDeliverSeq;
	}

	public void setSourceDeliverSeq(String sourceDeliverSeq) {
		this.sourceDeliverSeq = sourceDeliverSeq;
	}
	
}
