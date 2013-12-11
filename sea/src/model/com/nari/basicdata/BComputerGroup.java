package com.nari.basicdata;

/**
 * BComputerGroup entity. @author MyEclipse Persistence Tools
 */

public class BComputerGroup implements java.io.Serializable {

	// Fields

	private Integer computerId;
	private String ipAddr;
	private String computerName;
	private Byte groupType;
	private Byte computerStatus;
	private Boolean isMonitor;
	private Integer listernNo;
	private Byte pri;
	private Integer maxThread;

	// Constructors

	/** default constructor */
	public BComputerGroup() {
	}

	/** full constructor */
	public BComputerGroup(String ipAddr, String computerName, Byte groupType,
			Byte computerStatus, Boolean isMonitor, Integer listernNo,
			Byte pri, Integer maxThread) {
		this.ipAddr = ipAddr;
		this.computerName = computerName;
		this.groupType = groupType;
		this.computerStatus = computerStatus;
		this.isMonitor = isMonitor;
		this.listernNo = listernNo;
		this.pri = pri;
		this.maxThread = maxThread;
	}

	// Property accessors

	public Integer getComputerId() {
		return this.computerId;
	}

	public void setComputerId(Integer computerId) {
		this.computerId = computerId;
	}

	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getComputerName() {
		return this.computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public Byte getGroupType() {
		return this.groupType;
	}

	public void setGroupType(Byte groupType) {
		this.groupType = groupType;
	}

	public Byte getComputerStatus() {
		return this.computerStatus;
	}

	public void setComputerStatus(Byte computerStatus) {
		this.computerStatus = computerStatus;
	}

	public Boolean getIsMonitor() {
		return this.isMonitor;
	}

	public void setIsMonitor(Boolean isMonitor) {
		this.isMonitor = isMonitor;
	}

	public Integer getListernNo() {
		return this.listernNo;
	}

	public void setListernNo(Integer listernNo) {
		this.listernNo = listernNo;
	}

	public Byte getPri() {
		return this.pri;
	}

	public void setPri(Byte pri) {
		this.pri = pri;
	}

	public Integer getMaxThread() {
		return this.maxThread;
	}

	public void setMaxThread(Integer maxThread) {
		this.maxThread = maxThread;
	}

}