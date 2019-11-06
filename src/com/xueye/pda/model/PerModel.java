package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.PerObj;

public class PerModel extends BaseModel{
List<PerObj> patientData;

public List<PerObj> getPatientData() {
	return patientData;
}

public void setPatientData(List<PerObj> patientData) {
	this.patientData = patientData;
}

}
