package com.nari.runman.runstatusman;

/**
 * 
 * 供电单位离线终端统计DTO
 * 
 * @author ChunMingLi
 * 2010-5-1
 *
 */
public class ChannelMonitorOrgNoDto {

	//供电单位编号
	private String orgNo;
	
	//供电单位编号
	private String orgName;
	
	//上线终端数
	private int upTerminalNum;
	
	//离线终端数
	private int offTerminalNum;
	
	//离线率
	private double offLineScale;
	
	//单位类别
	private String orgType;
	
	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public int getUpTerminalNum() {
		return upTerminalNum;
	}

	public void setUpTerminalNum(int upTerminalNum) {
		this.upTerminalNum = upTerminalNum;
	}

	public int getOffTerminalNum() {
		return offTerminalNum;
	}

	public void setOffTerminalNum(int offTerminalNum) {
		this.offTerminalNum = offTerminalNum;
	}

	public double getOffLineScale() {
		return offLineScale;
	}

	public void setOffLineScale(double offLineScale) {
		this.offLineScale = offLineScale;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
