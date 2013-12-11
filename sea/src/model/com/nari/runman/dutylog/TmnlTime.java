package com.nari.runman.dutylog;

public class TmnlTime {
	private String tmnlAssetNo;
	private String time;
	
	public TmnlTime(){
		
	}
	public TmnlTime(String tmnlAssetNo, String time) {
		super();
		this.tmnlAssetNo = tmnlAssetNo;
		this.time = time;
	}
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
