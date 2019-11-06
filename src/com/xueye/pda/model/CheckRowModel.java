package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.CheckRowObj;
import com.xueye.pda.obj.DoctorRowDataObj;

/**
 * @author wok
 * 
 * 类说明
 **/
public class CheckRowModel extends BaseModel {

	private List<CheckRowObj> patientData;

	public List<CheckRowObj> getPatientData() {
		return patientData;
	}

	public void setPatientData(List<CheckRowObj> patientData) {
		this.patientData = patientData;
	}

	
	
}
