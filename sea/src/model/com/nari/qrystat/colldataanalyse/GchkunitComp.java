package com.nari.qrystat.colldataanalyse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 考核单元从表g_chkunit_comp的插入操作bean
 * @author huangxuan
 *
 */
@Table(name="g_chkunit_comp")
public class GchkunitComp {
	@Id
	@Column(name="comp_id")
	@GeneratedValue(generator="s_g_chkunit_comp")
	private Integer compId;
	@Column(name="chkunit_id")
	private Integer chkunitId;
	@Column(name="obj_id")
	private Integer objId;
	@Column(name="obj_type_code")
	private String objTypeCode;
	
	public Integer getCompId() {
		return compId;
	}
	public void setCompId(Integer compId) {
		this.compId = compId;
	}
	public Integer getChkunitId() {
		return chkunitId;
	}
	public void setChkunitId(Integer chkunitId) {
		this.chkunitId = chkunitId;
	}
	public Integer getObjId() {
		return objId;
	}
	public void setObjId(Integer objId) {
		this.objId = objId;
	}
	public String getObjTypeCode() {
		return objTypeCode;
	}
	public void setObjTypeCode(String objTypeCode) {
		this.objTypeCode = objTypeCode;
	}
	
}
