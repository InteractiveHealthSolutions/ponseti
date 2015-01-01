package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hospital implements Serializable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -605228208776514002L;
	
	public static String COLUMN_HOSPITAL_NAME = "name";
	public static String COLUMN_HOSPITAL_ID = "hospital_id";
	
	int hospitalId;
	String hospitalName;
	
	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Hospital() {
		
	}
	
	public Hospital(String hospitalName) {
		this.hospitalName = hospitalName;	
	}
	
	public static List<Hospital> parseHospitalsFromArray(Object[] hospitalsArray) {
		List<Hospital> hospitals = new ArrayList<Hospital>();
		for(int i=0; i<hospitalsArray.length; i++) {
			hospitals.add(new Hospital(hospitalsArray[i].toString()));
		}
		return hospitals;
	}
	
	
}
