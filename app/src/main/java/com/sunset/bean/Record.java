package com.sunset.bean;

import java.io.Serializable;

public class Record implements Serializable{
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	public String recordId;
	public String parkId;
	public String departmentId;
	public String carId;
	public String entry;
	public String entryTime;
	public String export;
	public String exportTime;
	public String shouldAmt;//应收金额   入库时 *10
	public String realAmt;//实际金额   入库时 *10
	public String freeTime;
	public String userId;
	public String name;
	public String type;
	public String description;
	public String entryImg;
	public String exportImg;
	public String state;
	public String getCarId() {
		return carId;
	}
	
	 

	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getRecordId() {
		return recordId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}


	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

 

	public String getEntryImg() {
		return entryImg;
	}

	public void setEntryImg(String entryImg) {
		this.entryImg = entryImg;
	}

	public String getExportImg() {
		return exportImg;
	}

	public void setExportImg(String exportImg) {
		this.exportImg = exportImg;
	}

	 
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getEntry() {
		return entry;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	 
	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getExport() {
		return export;
	}

	public void setExport(String export) {
		this.export = export;
	}

	public String getExportTime() {
		return exportTime;
	}

	public void setExportTime(String exportTime) {
		this.exportTime = exportTime;
	}

	 
	public String getShouldAmt() {
		return shouldAmt;
	}

	public void setShouldAmt(String shouldAmt) {
		this.shouldAmt = shouldAmt;
	}

	public String getRealAmt() {
		return realAmt;
	}

	public void setRealAmt(String realAmt) {
		this.realAmt = realAmt;
	}

	public String getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
