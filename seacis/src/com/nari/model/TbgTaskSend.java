package com.nari.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "t_bg_task_send")
public class TbgTaskSend {
	@Column(name = "tmnl_asset_no")
	private String tmnlAssetNo;
	@Column(name = "task_no")
	private String taskNo;
	@Column(name = "data_item_no")
	private String dataItemNo;
	@Column(name = "data_value")
	private String dataValue;

	public TbgTaskSend() {
	}

	public TbgTaskSend(String dataItemNo) {
		this.dataItemNo=dataItemNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getDataItemNo() {
		return dataItemNo;
	}

	public void setDataItemNo(String dataItemNo) {
		this.dataItemNo = dataItemNo;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
