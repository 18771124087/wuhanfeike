package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.DoctorRowDataObj;

/**
 * @author wok
 * 
 * 类说明
 **/
public class DoctorRowDataModel extends BaseModel {

	private List<DoctorRowDataObj> patientData;

	public List<DoctorRowDataObj> getPatientData() {
		return patientData;
	}

	public void setPatientData(List<DoctorRowDataObj> patientData) {
		this.patientData = patientData;
	}
	
	
}
