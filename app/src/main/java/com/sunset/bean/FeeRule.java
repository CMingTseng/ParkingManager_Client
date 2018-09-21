package com.sunset.bean;

import java.io.Serializable;


 
public class FeeRule implements Serializable{
	/**
	 * 收费规则
	 */
	public static final long serialVersionUID = 1L;
	public String ruleId;
	public String type;//0按时收费，1按次
	public String carType;// 
	public String parkId;
	public String freeTime;//免费时长
	public String maxFee; //最大限额
	public String firstTime;//首段时长
	public String firstPrice;//首段价格
	public String spaceTime;//间隔时长
	public String spacePrice;//间隔价格
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}
	public String getMaxFee() {
		return maxFee;
	}
	public void setMaxFee(String maxFee) {
		this.maxFee = maxFee;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(String firstPrice) {
		this.firstPrice = firstPrice;
	}
	public String getSpaceTime() {
		return spaceTime;
	}
	public void setSpaceTime(String spaceTime) {
		this.spaceTime = spaceTime;
	}
	public String getSpacePrice() {
		return spacePrice;
	}
	public void setSpacePrice(String spacePrice) {
		this.spacePrice = spacePrice;
	}
	 
}
