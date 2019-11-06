package com.xueye.pda.model;

public class BaseModel {

	private String state;
	private String msg;
	private int infCord; 
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getInfCord() {
		return infCord;
	}
	public void setInfCord(int infCord) {
		this.infCord = infCord;
	}
	@Override
	public String toString() {
		return "BaseModel [state=" + state + ", msg=" + msg + ", infCord="
				+ infCord + "]";
	}

	
	

}
