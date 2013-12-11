package com.nari.runman.archivesman;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.GLineJdbc;

/**
 * @author yut
 * @describe 档案管理 组织机构 线路维护 Action
 */
public class OrganizeManLineAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	// 注入类
	IOrganizeManager iOrganizeManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;
	public String msg;

	// 返回记录
	public List<GLineJdbc> lineList;

	// 查询参数
	public String nodeValue;

	
	private String lineSubsId;
	// Form参数
	private   Long lineId;
	private   String lineNo;
	private   String lineName;
	private   String orgNo;
	private   String voltCode;
	private   String wireSpecCode;
	private   Double wireLen;
	private   String sublineFlag;
	private   String lnFlag;
	private   String ruralGridFlag;
	private   String runStatusCode;
	private   String llCalcMode;
	private   Double apLlValue;
	private   Double rpLlValue;
	private   Double unitResi;
	private   Double unitReac;
	
	/**
	 * 查询线路
	 */
	public String queryLine() throws Exception {
		this.logger.debug("查询变电站");
		this.lineList = this.iOrganizeManager.findLine(nodeValue);
		return SUCCESS;
	}
	
	/**
	 * 保存线路
	 * @return
	 * @throws Exception
	 */
	public String saveLine()throws Exception{
		GLineJdbc lineJdbc = this.genGLine();
		this.msg = this.iOrganizeManager.saveOrUpdateLine(lineSubsId,lineJdbc);
		this.logger.debug("保存线路"+this.msg);
		return SUCCESS;
	}

	/**
	 * 删除线路
	 * @return
	 * @throws Exception
	 */
	public String deleteLine() throws Exception{
		this.iOrganizeManager.deleteLine(lineId.toString());
		return SUCCESS;
	}

	private GLineJdbc genGLine(){
		GLineJdbc lineJdbc = new GLineJdbc();
		lineJdbc.setApLlValue(apLlValue);
		lineJdbc.setLineId(lineId);
		lineJdbc.setLineName(lineName);
		lineJdbc.setLineNo(lineNo);
		lineJdbc.setLlCalcMode(llCalcMode);
		lineJdbc.setLnFlag(lnFlag);
		lineJdbc.setOrgNo(orgNo);
		lineJdbc.setRpLlValue(rpLlValue);
		lineJdbc.setRunStatusCode(runStatusCode);
		lineJdbc.setRuralGridFlag(ruralGridFlag);
		lineJdbc.setSublineFlag(sublineFlag);
		lineJdbc.setUnitReac(unitReac);
		lineJdbc.setUnitResi(unitResi);
		lineJdbc.setVoltCode(voltCode);
		lineJdbc.setWireLen(wireLen);
		lineJdbc.setWireSpecCode(wireSpecCode);
		lineJdbc.setChgDate(new Timestamp(new Date().getTime()));
		return lineJdbc;
	}
	
	@JSON(serialize = false)
	public void setiOrganizeManager(IOrganizeManager iOrganizeManager) {
		this.iOrganizeManager = iOrganizeManager;
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

	public List<GLineJdbc> getLineList() {
		return lineList;
	}

	public void setLineList(List<GLineJdbc> lineList) {
		this.lineList = lineList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getVoltCode() {
		return voltCode;
	}

	public void setVoltCode(String voltCode) {
		this.voltCode = voltCode;
	}

	public String getWireSpecCode() {
		return wireSpecCode;
	}

	public void setWireSpecCode(String wireSpecCode) {
		this.wireSpecCode = wireSpecCode;
	}

	public Double getWireLen() {
		return wireLen;
	}

	public void setWireLen(Double wireLen) {
		this.wireLen = wireLen;
	}

	public String getSublineFlag() {
		return sublineFlag;
	}

	public void setSublineFlag(String sublineFlag) {
		this.sublineFlag = sublineFlag;
	}

	public String getLnFlag() {
		return lnFlag;
	}

	public void setLnFlag(String lnFlag) {
		this.lnFlag = lnFlag;
	}

	public String getRuralGridFlag() {
		return ruralGridFlag;
	}

	public void setRuralGridFlag(String ruralGridFlag) {
		this.ruralGridFlag = ruralGridFlag;
	}

	public String getRunStatusCode() {
		return runStatusCode;
	}

	public void setRunStatusCode(String runStatusCode) {
		this.runStatusCode = runStatusCode;
	}

	public String getLlCalcMode() {
		return llCalcMode;
	}

	public void setLlCalcMode(String llCalcMode) {
		this.llCalcMode = llCalcMode;
	}

	public Double getApLlValue() {
		return apLlValue;
	}

	public void setApLlValue(Double apLlValue) {
		this.apLlValue = apLlValue;
	}

	public Double getRpLlValue() {
		return rpLlValue;
	}

	public void setRpLlValue(Double rpLlValue) {
		this.rpLlValue = rpLlValue;
	}

	public Double getUnitResi() {
		return unitResi;
	}

	public void setUnitResi(Double unitResi) {
		this.unitResi = unitResi;
	}

	public Double getUnitReac() {
		return unitReac;
	}

	public void setUnitReac(Double unitReac) {
		this.unitReac = unitReac;
	}

	public String getLineSubsId() {
		return lineSubsId;
	}

	public void setLineSubsId(String lineSubsId) {
		this.lineSubsId = lineSubsId;
	}

}
