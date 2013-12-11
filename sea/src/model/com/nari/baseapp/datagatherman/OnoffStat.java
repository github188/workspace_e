package com.nari.baseapp.datagatherman;

import java.io.Serializable;

public class OnoffStat implements Serializable{
	private int onNum;
	private int offNum;
	public int getOnNum() {
		return onNum;
	}
	public void setOnNum(int onNum) {
		this.onNum = onNum;
	}
	public int getOffNum() {
		return offNum;
	}
	public void setOffNum(int offNum) {
		this.offNum = offNum;
	}
	

}
