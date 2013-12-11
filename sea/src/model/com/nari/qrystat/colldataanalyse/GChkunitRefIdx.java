package com.nari.qrystat.colldataanalyse;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

/**
 * 参考指标
 * @author huangxuan
 *
 */
@Table(name="g_chkunit_ref_idx")
public class GChkunitRefIdx {
	@Id
	@Column(name="LL_IDX_ID")
	@GeneratedValue(generator="s_g_chkunit_ref_idx.nextval")
	private Integer llIdxId;
	@Column(name="CHKUNIT_ID")
	private Integer chkunitId;
	@Column(name="LL_IDX_NAME")
	private String llIdxName;
	@Column(name="LL_IDX_VALUE")
	private Double llIdxValue;
	@Column(name="CHK_CYCLE")
	private String chkCycle;
	private String msg="db";
	private String chkunitName;
	private String dataSrc;
	public Integer getLlIdxId() {
		return llIdxId;
	}
	public void setLlIdxId(Integer llIdxId) {
		BeanPropertyRowMapper b=new BeanPropertyRowMapper(this.getClass());
		this.llIdxId = llIdxId;
	}
	public Integer getChkunitId() {
		return chkunitId;
	}
	public void setChkunitId(Integer chkunitId) {
		this.chkunitId = chkunitId;
	}
	public String getLlIdxName() {
		return llIdxName;
	}
	public void setLlIdxName(String llIdxName) {
		this.llIdxName = llIdxName;
	}
	
	public Double getLlIdxValue() {
		return llIdxValue;
	}
	public void setLlIdxValue(Double llIdxValue) {
		this.llIdxValue = llIdxValue;
	}
	public String getChkCycle() {
		return chkCycle;
	}
	public void setChkCycle(String chkCycle) {
		this.chkCycle = chkCycle;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getChkunitName() {
		return chkunitName;
	}
	public void setChkunitName(String chkunitName) {
		this.chkunitName = chkunitName;
	}
	public String getDataSrc() {
		return dataSrc;
	}
	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
}
