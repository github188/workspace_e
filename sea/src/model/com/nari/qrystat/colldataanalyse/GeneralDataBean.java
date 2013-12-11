package com.nari.qrystat.colldataanalyse;

import java.io.Serializable;

public class GeneralDataBean implements Serializable {
	private String time;
	private Double data;
	private Boolean flag;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getData() {
		return data;
	}

	public void setData(Double data) {
		this.data = data;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
