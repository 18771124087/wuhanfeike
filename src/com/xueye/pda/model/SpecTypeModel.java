package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.SpecTypeObj;

/**
 * @author wok
 * 
 * 类说明
 **/
public class SpecTypeModel extends BaseModel {

	private List<SpecTypeObj> patientDataRow;

	public List<SpecTypeObj> getPatientDataRow() {
		return patientDataRow;
	}

	public void setPatientDataRow(List<SpecTypeObj> patientDataRow) {
		this.patientDataRow = patientDataRow;
	}
	
	
	
}
