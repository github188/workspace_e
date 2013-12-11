package com.nari.qrystat.colldataanalyse;

/**
 * 用于描述考核单元中入口和出口
 * 
 * @author huangxuan
 * 
 */
public class Giomp {
	private Integer ioMpId;
	private Integer chkunitId;
	private Integer mpId;
	private Integer meterId;
	private Integer isIn;
	public Integer getIoMpId() {
		return ioMpId;
	}
	public void setIoMpId(Integer ioMpId) {
		this.ioMpId = ioMpId;
	}
	public Integer getChkunitId() {
		return chkunitId;
	}
	public void setChkunitId(Integer chkunitId) {
		this.chkunitId = chkunitId;
	}
	public Integer getMpId() {
		return mpId;
	}
	public void setMpId(Integer mpId) {
		this.mpId = mpId;
	}
	public Integer getMeterId() {
		return meterId;
	}
	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
	}
	public Integer getIsIn() {
		return isIn;
	}
	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}
	
}
