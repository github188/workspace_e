package com.nari.runman.archivesman;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.customer.CCons;

/**
 * Action 类 ArchiveManAction
 * 
 * @author zhangzhw
 * @describe 档案管理 用户管理 Action
 */
public class ArchiveManAction extends BaseAction {

	// 注入类
	IArchiveManManager archiveManManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 返回记录
	CCons ccons;

	// 查询参数
	public String nodeId;

	// Form参数
	private Long consId;
	private Long custId;
	private String orgNo;
	private String areaNo;
	private String bureauNo;
	private String consNo;
	private String consName;
	private String custNo;
	private Long subsId;
	private Long tgId;
	private Long lineId;
	private String custQueryNo;
	private String tmpPayRelaNo;
	private String orgnConsNo;
	private String consSortCode;
	private String elecAddr;
	private String tradeCode;
	private Byte consType;
	private String elecTypeCode;
	private Double contractCap;
	private Double runCap;
	private String capGradeNo;
	private String shiftNo;
	private String lodeAttrCode;
	private String voltCode;
	private String hecIndustryCode;
	private String holiday;
	private Date buildDate;
	private Date psDate;
	private Date cancelDate;
	private Date dueDate;
	private String notifyMode;
	private String settleMode;
	private String statusCode;
	private String rrioCode;
	private Integer chkCycle;
	private Date lastChkDate;
	private String checkerNo;
	private String poweroffCode;
	private String transferCode;
	private String mrSectNo;
	private String noteTypeCode;
	private String tmpFlag;
	private Date tmpDate;
	private String applyNo;
	private Date applyDate;

	/**
	 * action 方法 queryArchive
	 * 
	 * @return 通过用户Id查询档案信息
	 */
	public String queryArchive() throws Exception {
		// 查询要取得的信息
		// archiveManager.queryArchive(nodeId);
		ccons = archiveManManager.queryArchive(nodeId);
		return "success";
	}

	/**
	 * action 方法
	 * 
	 * @return 保存cons信息
	 */
	public String saveArchiveCons() throws Exception {

		CCons cons = this.genCCons();

		ccons = archiveManManager.saveArchive(cons);

		return SUCCESS;
	}

	// 组合前台参数为 CCons
	public CCons genCCons() {
		CCons cons = new CCons();
		cons.setApplyDate(this.getApplyDate());
		cons.setApplyNo(this.getApplyNo());
		cons.setAreaNo(this.getAreaNo());
		cons.setBuildDate(this.getBuildDate());
		cons.setCancelDate(this.getCancelDate());
		cons.setCapGradeNo(this.getCapGradeNo());
		cons.setConsId(this.getConsId());
		cons.setConsName(this.getConsName());
		cons.setConsNo(this.getConsNo());
		cons.setConsSortCode(this.getConsSortCode());
		cons.setConsType(this.getConsType());
		cons.setContractCap(this.getContractCap());
		cons.setCustId(this.getCustId());
		cons.setCustNo(this.getCustNo());
		cons.setCustQueryNo(this.getCustQueryNo());
		cons.setElecAddr(this.getElecAddr());
		cons.setElecTypeCode(this.getElecTypeCode());
		cons.setHecIndustryCode(this.getHecIndustryCode());
		cons.setHoliday(this.getHoliday());
		cons.setLineId(this.getLineId());
		cons.setLodeAttrCode(this.getLodeAttrCode());
		cons.setMrSectNo(this.getMrSectNo());
		cons.setOrgNo(this.getOrgNo());
		cons.setOrgnConsNo(this.getOrgnConsNo());
		cons.setPoweroffCode(this.getPoweroffCode());
		cons.setPsDate(this.getPsDate());
		cons.setRrioCode(this.getRrioCode());
		cons.setRunCap(this.getRunCap());
		cons.setShiftNo(this.getShiftNo());
		cons.setStatusCode(this.getStatusCode());
		cons.setSubsId(this.getSubsId());
		cons.setTgId(this.getTgId());
		cons.setTmpFlag(this.getTmpFlag());
		cons.setTradeCode(this.getTradeCode());
		cons.setVoltCode(this.getVoltCode());

		return cons;
	}

	// getters and setters

	@JSON(serialize = false)
	public IArchiveManManager getArchiveManManager() {
		return archiveManManager;
	}

	public void setArchiveManManager(IArchiveManManager archiveManManager) {
		this.archiveManManager = archiveManManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public CCons getCcons() {
		return ccons;
	}

	public void setCcons(CCons ccons) {
		this.ccons = ccons;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Long getConsId() {
		return consId;
	}

	public void setConsId(Long consId) {
		this.consId = consId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getBureauNo() {
		return bureauNo;
	}

	public void setBureauNo(String bureauNo) {
		this.bureauNo = bureauNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public Long getTgId() {
		return tgId;
	}

	public void setTgId(Long tgId) {
		this.tgId = tgId;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getCustQueryNo() {
		return custQueryNo;
	}

	public void setCustQueryNo(String custQueryNo) {
		this.custQueryNo = custQueryNo;
	}

	public String getTmpPayRelaNo() {
		return tmpPayRelaNo;
	}

	public void setTmpPayRelaNo(String tmpPayRelaNo) {
		this.tmpPayRelaNo = tmpPayRelaNo;
	}

	public String getOrgnConsNo() {
		return orgnConsNo;
	}

	public void setOrgnConsNo(String orgnConsNo) {
		this.orgnConsNo = orgnConsNo;
	}

	public String getConsSortCode() {
		return consSortCode;
	}

	public void setConsSortCode(String consSortCode) {
		this.consSortCode = consSortCode;
	}

	public String getElecAddr() {
		return elecAddr;
	}

	public void setElecAddr(String elecAddr) {
		this.elecAddr = elecAddr;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public Byte getConsType() {
		return consType;
	}

	public void setConsType(Byte consType) {
		this.consType = consType;
	}

	public String getElecTypeCode() {
		return elecTypeCode;
	}

	public void setElecTypeCode(String elecTypeCode) {
		this.elecTypeCode = elecTypeCode;
	}

	public Double getContractCap() {
		return contractCap;
	}

	public void setContractCap(Double contractCap) {
		this.contractCap = contractCap;
	}

	public Double getRunCap() {
		return runCap;
	}

	public void setRunCap(Double runCap) {
		this.runCap = runCap;
	}

	public String getCapGradeNo() {
		return capGradeNo;
	}

	public void setCapGradeNo(String capGradeNo) {
		this.capGradeNo = capGradeNo;
	}

	public String getShiftNo() {
		return shiftNo;
	}

	public void setShiftNo(String shiftNo) {
		this.shiftNo = shiftNo;
	}

	public String getLodeAttrCode() {
		return lodeAttrCode;
	}

	public void setLodeAttrCode(String lodeAttrCode) {
		this.lodeAttrCode = lodeAttrCode;
	}

	public String getVoltCode() {
		return voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getHecIndustryCode() {
		return hecIndustryCode;
	}

	public void setHecIndustryCode(String hecIndustryCode) {
		this.hecIndustryCode = hecIndustryCode;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public Date getPsDate() {
		return psDate;
	}

	public void setPsDate(Date psDate) {
		this.psDate = psDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getNotifyMode() {
		return notifyMode;
	}

	public void setNotifyMode(String notifyMode) {
		this.notifyMode = notifyMode;
	}

	public String getSettleMode() {
		return settleMode;
	}

	public void setSettleMode(String settleMode) {
		this.settleMode = settleMode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getRrioCode() {
		return rrioCode;
	}

	public void setRrioCode(String rrioCode) {
		this.rrioCode = rrioCode;
	}

	public Integer getChkCycle() {
		return chkCycle;
	}

	public void setChkCycle(Integer chkCycle) {
		this.chkCycle = chkCycle;
	}

	public Date getLastChkDate() {
		return lastChkDate;
	}

	public void setLastChkDate(Date lastChkDate) {
		this.lastChkDate = lastChkDate;
	}

	public String getCheckerNo() {
		return checkerNo;
	}

	public void setCheckerNo(String checkerNo) {
		this.checkerNo = checkerNo;
	}

	public String getPoweroffCode() {
		return poweroffCode;
	}

	public void setPoweroffCode(String poweroffCode) {
		this.poweroffCode = poweroffCode;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	public String getMrSectNo() {
		return mrSectNo;
	}

	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	public String getNoteTypeCode() {
		return noteTypeCode;
	}

	public void setNoteTypeCode(String noteTypeCode) {
		this.noteTypeCode = noteTypeCode;
	}

	public String getTmpFlag() {
		return tmpFlag;
	}

	public void setTmpFlag(String tmpFlag) {
		this.tmpFlag = tmpFlag;
	}

	public Date getTmpDate() {
		return tmpDate;
	}

	public void setTmpDate(Date tmpDate) {
		this.tmpDate = tmpDate;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

}
