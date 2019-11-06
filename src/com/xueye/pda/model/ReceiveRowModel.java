package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.CheckRowObj;
import com.xueye.pda.obj.DoctorRowDataObj;
import com.xueye.pda.obj.ReceiveRowObj;

/**
 * @author wok
 * 
 * 类说明
 **/
public class ReceiveRowModel extends BaseModel {

	private List<ReceiveRowObj> patientData;

	public List<ReceiveRowObj> getPatientData() {
		return patientData;
	}

	public void setPatientData(List<ReceiveRowObj> patientData) {
		this.patientData = patientData;
	}


	
	
}
