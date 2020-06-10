package com.glaway.ids.models;

public class JsonResult {

	/** 返回编码*/
	private String retCode = "0";

	/** 返回提示信息*/
    private boolean success = true;// 是否成功

	   /** 返回提示信息*/
    private String retMsg = "成功";

    private Object obj = null;// 其他信息

	/** 响应对象 */
	private Object retObj;

	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public Object getRetObj() {
		return retObj;
	}
	public void setRetObj(Object retObj) {
		this.retObj = retObj;
	}
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Object getObj() {
        return obj;
    }
    public void setObj(Object obj) {
        this.obj = obj;
    }
    
	
}
