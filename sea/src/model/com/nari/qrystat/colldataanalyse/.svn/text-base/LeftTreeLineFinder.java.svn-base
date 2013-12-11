package com.nari.qrystat.colldataanalyse;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

/**
 * 通过此finder来查找左边树里面的线路
 * @author huangxuan
 *
 */
public class LeftTreeLineFinder {
	@SelectConfig(column="line_name",limit=LimitType.LIKE,dealNull=DealValueType.PARSEEMPTY,likeMode=LikeMode.CONTAINS)
	private String lineName;
	@SelectConfig(column="is_reg",limit=LimitType.EQ,dealNull=DealValueType.PARSEEMPTY)
	private String isReg;
	@SelectConfig(column="volt_code",limit=LimitType.EQ,dealNull=DealValueType.PARSEEMPTY)
	private String voltCode;
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getIsReg() {
		return isReg;
	}
	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}
	public String getVoltCode() {
		return voltCode;
	}
	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}
	
}
