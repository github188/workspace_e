package com.nari.qrystat.colldataanalyse;

import java.util.HashMap;

import javax.persistence.Table;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

/**
 * 考核单元的实体 此类用于对记录考核单元的条件的值
 * 
 * @author huangxuan
 * 
 */
@Table(name = "G_CHKUNIT")
public class GChkUnitFinder {
	@SelectConfig(column = "chkunit_type_code", dealNull = DealValueType.PARSEEMPTY, limit = LimitType.EQ)
	private String chkUnitType;
	@SelectConfig(column = "CHKUNIT_NAME", dealNull = DealValueType.PARSEEMPTY, limit = LimitType.LIKE, likeMode = LikeMode.CONTAINS)
	private String chkUnitName;
	@SelectConfig(column = "link_flag", dealNull = DealValueType.PARSEEMPTY, limit = LimitType.EQ)
	private String linkFlag;
	@SelectConfig(column = "cycle", dealNull = DealValueType.PARSEEMPTY, limit = LimitType.LIKE,likeMode=LikeMode.CONTAINS)
	private String chkCycle;
	
	private HashMap<String,String> mapCycle=new HashMap<String,String>();
	{
		mapCycle.put("1","日,");
		mapCycle.put("2","月,");
		mapCycle.put("3","季,");
		mapCycle.put("4","半年,");
		mapCycle.put("5","年,");
	}
	private String orgName;
	
	private int chkUnitId;
	@SelectConfig(column = "org_no", dealNull = DealValueType.PARSEEMPTY, limit = LimitType.EQ)
	private String orgNo;
	private String examFlag;
	private String detSortCode;
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getChkUnitId() {
		return chkUnitId;
	}

	public void setChkUnitId(int chkUnitId) {
		this.chkUnitId = chkUnitId;
	}

	public String getChkUnitType() {
		return chkUnitType;
	}

	public void setChkUnitType(String chkUnitType) {
		this.chkUnitType = chkUnitType;
	}

	public String getChkUnitName() {
		return chkUnitName;
	}

	public void setChkUnitName(String chkUnitName) {
		this.chkUnitName = chkUnitName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getExamFlag() {
		return examFlag;
	}

	public void setExamFlag(String examFlag) {
		this.examFlag = examFlag;
	}

	public String getLinkFlag() {
		return linkFlag;
	}

	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}

	public String getChkCycle() {
		return chkCycle;
	}

	public void setChkCycle(String chkCycle) {
		this.chkCycle = mapCycle.get(chkCycle);
	}

	public String getDetSortCode() {
		return detSortCode;
	}

	public void setDetSortCode(String detSortCode) {
		this.detSortCode = detSortCode;
	}
}
