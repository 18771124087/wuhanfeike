package com.xueye.pda.obj;


import java.io.Serializable;

import com.xueye.pda.model.BaseModel;

/**
 * 
 * 系统用户
 * 
 * @author wok
 * 
 */
@SuppressWarnings("serial")
public class UserObj implements Serializable {
	private String oprId; //登录用户编号
	private String oprName; //登录用户姓名
	
	private String role_no; //角色编号
	private String role_name; //角色名称
	private String department;//部门
	
	public String getOprId() {
		return oprId;
	}
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	public String getOprName() {
		return oprName;
	}
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}
	public String getRole_no() {
		return role_no;
	}
	public void setRole_no(String role_no) {
		this.role_no = role_no;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
}
