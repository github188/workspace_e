package com.nari.advapp.vipconsman;

/**
 * 
 * 重点用户定义模型
 * 
 * @author ChunMingLi
 * @since 2010-6-2
 * 
 */
public class VipConsManager {
	
	
	/**
	 * 默认构造函数
	 */
	public VipConsManager() {
		super();
	}
	
	/**
	 * full constructor
	 */
	public VipConsManager(VipConsManagerId id, String consNo, int consSort,
			int monitorFlag, int minitorFreq, String monitorLevel,
			int monitorErc, int monitorEc, int monitorExce, int monitorMeter,
			int monitorVol, int monitorElec, int monitorTran, String staffNo,
			int dataSrc) {
		super();
		this.id = id;
		this.consNo = consNo;
		this.consSort = consSort;
		this.monitorFlag = monitorFlag;
		this.minitorFreq = minitorFreq;
		this.monitorLevel = monitorLevel;
		this.monitorErc = monitorErc;
		this.monitorEc = monitorEc;
		this.monitorExce = monitorExce;
		this.monitorMeter = monitorMeter;
		this.monitorVol = monitorVol;
		this.monitorElec = monitorElec;
		this.monitorTran = monitorTran;
		this.staffNo = staffNo;
		this.dataSrc = dataSrc;
	}


	//主键
	private VipConsManagerId id;

//	// 供电单位
//	private String orgNo;
//
//	// 用户标识
//	private long consId;

	// 重点用户编号
	private String consNo;

	// 用户类别
	private int consSort;

	// 后台监视
	private int monitorFlag;

	// 监视频度
	private int minitorFreq;

	// 监视级别
	private String monitorLevel;

	// 监视终端事件 
	private int monitorErc;

	// 监视用电异常
	private int monitorEc;

	// 监视工况
	private int monitorExce;

	// 计量设备监测
	private int monitorMeter;

	// 监视电压
	private int monitorVol;

	// 监视电流 
	private int monitorElec;

	// 变压器运行监测 
	private int monitorTran;

	// 工号 
	private String staffNo;

	// 数据来源
	private int dataSrc;



	/**
	 *  the id
	 * @return the id
	 */
	public VipConsManagerId getId() {
		return id;
	}

	/**
	 *  the id to set
	 * @param id the id to set
	 */
	public void setId(VipConsManagerId id) {
		this.id = id;
	}

	/**
	 *  重点用户编号
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}

	/**
	 *  重点用户编号 to set
	 * @param consNo the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	/**
	 *  用户类别
	 * @return the consSort
	 */
	public int getConsSort() {
		return consSort;
	}

	/**
	 *  用户类别 to set
	 * @param consSort the consSort to set
	 */
	public void setConsSort(int consSort) {
		this.consSort = consSort;
	}

	/**
	 *  后台监视
	 * @return the monitorFlag
	 */
	public int getMonitorFlag() {
		return monitorFlag;
	}

	/**
	 *  后台监视 to set
	 * @param monitorFlag the monitorFlag to set
	 */
	public void setMonitorFlag(int monitorFlag) {
		this.monitorFlag = monitorFlag;
	}

	/**
	 *  监视频度
	 * @return the minitorFreq
	 */
	public int getMinitorFreq() {
		return minitorFreq;
	}

	/**
	 *  监视频度 to set
	 * @param minitorFreq the minitorFreq to set
	 */
	public void setMinitorFreq(int minitorFreq) {
		this.minitorFreq = minitorFreq;
	}

	/**
	 *  监视级别
	 * @return the monitorLevel
	 */
	public String getMonitorLevel() {
		return monitorLevel;
	}

	/**
	 * 监视级别 to set
	 * @param monitorLevel the monitorLevel to set
	 */
	public void setMonitorLevel(String monitorLevel) {
		this.monitorLevel = monitorLevel;
	}

	/**
	 *  监视终端事件 
	 * @return the monitorErc
	 */
	public int getMonitorErc() {
		return monitorErc;
	}

	/**
	 *  监视终端事件  to set
	 * @param monitorErc the monitorErc to set
	 */
	public void setMonitorErc(int monitorErc) {
		this.monitorErc = monitorErc;
	}

	/**
	 *  the 监视用电异常
	 * @return the monitorEc
	 */
	public int getMonitorEc() {
		return monitorEc;
	}

	/**
	 * 监视用电异常 to set
	 * @param monitorEc the monitorEc to set
	 */
	public void setMonitorEc(int monitorEc) {
		this.monitorEc = monitorEc;
	}

	/**
	 *  the 监视工况
	 * @return the monitorExce
	 */
	public int getMonitorExce() {
		return monitorExce;
	}

	/**
	 *  监视工况 to set
	 * @param monitorExce the monitorExce to set
	 */
	public void setMonitorExce(int monitorExce) {
		this.monitorExce = monitorExce;
	}

	/**
	 *  the 计量设备监测
	 * @return the monitorMeter
	 */
	public int getMonitorMeter() {
		return monitorMeter;
	}

	/**
	 *  计量设备监测 to set
	 * @param monitorMeter the monitorMeter to set
	 */
	public void setMonitorMeter(int monitorMeter) {
		this.monitorMeter = monitorMeter;
	}

	/**
	 *  the 监视电压
	 * @return the monitorVol
	 */
	public int getMonitorVol() {
		return monitorVol;
	}

	/**
	 *  监视电压 to set
	 * @param monitorVol the monitorVol to set
	 */
	public void setMonitorVol(int monitorVol) {
		this.monitorVol = monitorVol;
	}

	/**
	 *  the 监视电流 
	 * @return the monitorElec
	 */
	public int getMonitorElec() {
		return monitorElec;
	}

	/**
	 *  监视电流  to set
	 * @param monitorElec the monitorElec to set
	 */
	public void setMonitorElec(int monitorElec) {
		this.monitorElec = monitorElec;
	}

	/**
	 *  the 变压器运行监测 
	 * @return the monitorTran
	 */
	public int getMonitorTran() {
		return monitorTran;
	}

	/**
	 *  变压器运行监测  to set
	 * @param monitorTran the monitorTran to set
	 */
	public void setMonitorTran(int monitorTran) {
		this.monitorTran = monitorTran;
	}

	/**
	 *  the 工号 
	 * @return the staffNo
	 */
	public String getStaffNo() {
		return staffNo;
	}

	/**
	 *  工号  to set
	 * @param staffNo the staffNo to set
	 */
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	/**
	 *  数据来源
	 * @return the dataSrc
	 */
	public int getDataSrc() {
		return dataSrc;
	}

	/**
	 *  数据来源 to set
	 * @param dataSrc the dataSrc to set
	 */
	public void setDataSrc(int dataSrc) {
		this.dataSrc = dataSrc;
	}

}