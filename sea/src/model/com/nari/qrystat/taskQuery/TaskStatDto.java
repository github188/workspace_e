package com.nari.qrystat.taskQuery;

/**
 * 
 * 工单查询统计DTO
 * 工单状态类别   00新异常、02营销处理中、03正常归档、
 * 				04误报确认、06挂起、07本地处理中、08强制归档
 * 
 * @author ChunMingLi 2010-4-23
 * 
 */
public class TaskStatDto {
	
	//工单号
	private int eventID;
	
	// 供电单位编号
	private String orgNo;
	
	//供电单位名称
	private String orgName;

	// 00新异常 待办工单
	private int opTypeStateDBGD;

	// 02营销处理中
	private int opTypeStateYXCLZ;

	// 03正常归档
	private int opTypeStateZCGD;

	// 04误报确认
	private int opTypeStateWBQR;

	// 06挂起
	private int opTypeStateGQ;

	// 07本地处理中
	private int opTypeStateBDCLZ;

	// 08强制归档
	private int opTypeStateQZGD;
	
	// 合计
	private int total;

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getOpTypeStateDBGD() {
		return opTypeStateDBGD;
	}

	public void setOpTypeStateDBGD(int opTypeStateDBGD) {
		this.opTypeStateDBGD = opTypeStateDBGD;
	}

	public int getOpTypeStateYXCLZ() {
		return opTypeStateYXCLZ;
	}

	public void setOpTypeStateYXCLZ(int opTypeStateYXCLZ) {
		this.opTypeStateYXCLZ = opTypeStateYXCLZ;
	}

	public int getOpTypeStateZCGD() {
		return opTypeStateZCGD;
	}

	public void setOpTypeStateZCGD(int opTypeStateZCGD) {
		this.opTypeStateZCGD = opTypeStateZCGD;
	}

	public int getOpTypeStateWBQR() {
		return opTypeStateWBQR;
	}

	public void setOpTypeStateWBQR(int opTypeStateWBQR) {
		this.opTypeStateWBQR = opTypeStateWBQR;
	}

	public int getOpTypeStateGQ() {
		return opTypeStateGQ;
	}

	public void setOpTypeStateGQ(int opTypeStateGQ) {
		this.opTypeStateGQ = opTypeStateGQ;
	}

	public int getOpTypeStateBDCLZ() {
		return opTypeStateBDCLZ;
	}

	public void setOpTypeStateBDCLZ(int opTypeStateBDCLZ) {
		this.opTypeStateBDCLZ = opTypeStateBDCLZ;
	}

	public int getOpTypeStateQZGD() {
		return opTypeStateQZGD;
	}

	public void setOpTypeStateQZGD(int opTypeStateQZGD) {
		this.opTypeStateQZGD = opTypeStateQZGD;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	
}
