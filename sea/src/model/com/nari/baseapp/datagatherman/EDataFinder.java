package com.nari.baseapp.datagatherman;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;

/**
 * 用于查询e_data_mp 以及相关的表中数据的一个finder
 * 
 * @author huangxuan
 * 
 */

public class EDataFinder {
	@SelectConfig(column = "e.tmnl_asset_no")
	private String tmnlAssetNo;
	// 电能表资产号
	@SelectConfig(column = "dlc.asset_no", dealNull = DealValueType.PARSEEMPTY)
	private String fmrAssetNo;
	// 采集点资产号
	@SelectConfig(column = "e.asset_no", dealNull = DealValueType.PARSEEMPTY)
	private String assetNo;
	private int isTmnl = 0;

	public int getIsTmnl() {
		return isTmnl;
	}

	public void setIsTmnl(int isTmnl) {
		this.isTmnl = isTmnl;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getFmrAssetNo() {
		return fmrAssetNo;
	}

	public void setFmrAssetNo(String fmrAssetNo) {
		if (null != fmrAssetNo&&fmrAssetNo.equals("终端")) {
			this.fmrAssetNo = "";
			return;
		}
		this.fmrAssetNo = fmrAssetNo;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

}
