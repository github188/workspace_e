package com.nari.qrystat.colldataanalyse;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LimitType;

/**
 * 
 * @author huangxuan
 * 
 */
public class MeterMpFinder {
	@SelectConfig(column="type_code",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String typeCode;
	@SelectConfig(column="mp_attr_code",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String mpAttrCode;
	@SelectConfig(column="fmr_asset_no",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String fmrAssetNo;
	@SelectConfig(column="terminal_addr",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String tmnlAddr;
	//如果为-1说明没有定义
	@SelectConfig(column="is_in",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String inOut;
	//如果为-1说明没有定义
	@SelectConfig(column="io_valid",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String isVaild;
	@SelectConfig(column="cons_type",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String consType;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getMpAttrCode() {
		return mpAttrCode;
	}
	public void setMpAttrCode(String mpAttrCode) {
		this.mpAttrCode = mpAttrCode;
	}
	public String getFmrAssetNo() {
		return fmrAssetNo;
	}
	public void setFmrAssetNo(String fmrAssetNo) {
		this.fmrAssetNo = fmrAssetNo;
	}
	public String getTmnlAddr() {
		return tmnlAddr;
	}
	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}
	public String getInOut() {
		return inOut;
	}
	public void setInOut(String inOut) {
		this.inOut = inOut;
	}
	public String getIsVaild() {
		return isVaild;
	}
	public void setIsVaild(String isVaild) {
		this.isVaild = isVaild;
	}
	public String getConsType() {
		return consType;
	}
	public void setConsType(String consType) {
		this.consType = consType;
	}
	
}
