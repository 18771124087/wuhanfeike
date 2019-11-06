package com.xueye.pda.model;

import java.util.List;

import com.xueye.pda.obj.CollectObj;

public class CollectModel extends BaseModel{
List<CollectObj> patientData;

public List<CollectObj> getPatientData() {
	return patientData;
}

public void setPatientData(List<CollectObj> patientData) {
	this.patientData = patientData;
}

}
