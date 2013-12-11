package com.nari.advapp.statanalyse;

import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.advapp.statanalyse.impl.ElecContrastAnalysisManagerImpl;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.support.Page;

public class ElecContrastAnalysisAction extends BaseAction {


	private  Logger logger = Logger.getLogger(ElecContrastAnalysisAction.class);
	private List<ElecContrastAnalysisOrgNo> ElecContrastAnalysisOrgNoList;
	private List<ElecContrastAnalysisType> ElecContrastAnalysisTypeList;
	private List<ChargeContrastAnalysisBean> chargeContrastAnalysisList;
	public long start;
	public long limit;
	public long totalCount;
	public String capGradeNo;
	public String capGradeName;
	

	private boolean success = true;
	private String[] ValueArray;
	private String DateStart;
	private String DateEnd;
	private String ValueCombo;
	private String OrgNo;
	private String OrgType;
	
	private ElecContrastAnalysisManagerImpl elecContrastAnalysisManager;
	
	


	public List<ElecContrastAnalysisType> getElecContrastAnalysisTypeList() {
		return ElecContrastAnalysisTypeList;
	}



	public void setElecContrastAnalysisTypeList(
			List<ElecContrastAnalysisType> elecContrastAnalysisTypeList) {
		ElecContrastAnalysisTypeList = elecContrastAnalysisTypeList;
	}



	public void setElecContrastAnalysisManager(
			ElecContrastAnalysisManagerImpl elecContrastAnalysisManager) {
		this.elecContrastAnalysisManager = elecContrastAnalysisManager;
	}



	public List<ElecContrastAnalysisOrgNo> getElecContrastAnalysisOrgNoList() {
		return ElecContrastAnalysisOrgNoList;
	}



	public void setElecContrastAnalysisOrgNoList(
			List<ElecContrastAnalysisOrgNo> elecContrastAnalysisOrgNoList) {
		ElecContrastAnalysisOrgNoList = elecContrastAnalysisOrgNoList;
	}

	public List<ChargeContrastAnalysisBean> getChargeContrastAnalysisList() {
		return chargeContrastAnalysisList;
	}



	public void setChargeContrastAnalysisList(
			List<ChargeContrastAnalysisBean> chargeContrastAnalysisList) {
		this.chargeContrastAnalysisList = chargeContrastAnalysisList;
	}



	public long getStart() {
		return start;
	}



	public void setStart(long start) {
		this.start = start;
	}



	public long getLimit() {
		return limit;
	}



	public void setLimit(long limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}



	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}



	public boolean isSuccess() {
		return success;
	}



	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String[] getValueArray() {
		return ValueArray;
	}



	public void setValueArray(String[] valueArray) {
		ValueArray = valueArray;
	}

	public String getValueCombo() {
		return ValueCombo;
	}



	public void setValueCombo(String valueCombo) {
		ValueCombo = valueCombo;
	}



	public String getDateStart() {
		return DateStart;
	}



	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}



	public String getDateEnd() {
		return DateEnd;
	}



	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}

	public String getOrgNo() {
		return OrgNo;
	}



	public void setOrgNo(String orgNo) {
		OrgNo = orgNo;
	}



	public String getOrgType() {
		return OrgType;
	}



	public void setOrgType(String orgType) {
		OrgType = orgType;
	}

	public String queryElecContrastAnalysisOrgNo() throws Exception{
		try {
			
			logger.debug("电量对比分析");
			this.setElecContrastAnalysisOrgNoList(this.elecContrastAnalysisManager.getElecContrastAnalysisOrgNo(DateStart, DateEnd, ValueCombo, ValueArray,OrgNo,OrgType));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	public String queryChargeContrastAnalysisOrgNo() throws Exception{
		try {
			
			logger.debug("负荷对比分析");
            this.setChargeContrastAnalysisList(this.elecContrastAnalysisManager.getChargeContrastAnalysisOrgNo(DateStart, DateEnd, ValueCombo, ValueArray,OrgNo,OrgType));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	public String queryElecContrastAnalysiscapGrade() throws Exception{
		try {
			logger.debug("电量对比分析");
			this.setElecContrastAnalysisTypeList(this.elecContrastAnalysisManager.getElecContrastAnalysiscapGrade());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	public String queryElecContrastAnalysisvoltageGrade() throws Exception{
	try{
		logger.debug("电量对比分析");
		this.setElecContrastAnalysisTypeList(this.elecContrastAnalysisManager.getElecContrastAnalysiscapGradeVoltage());
	}catch(Exception e){
		e.printStackTrace();
	}
	return SUCCESS;
		
	}
	
	public String queryElecContrastAnalysisbussCategory() throws Exception{
		Page<ElecContrastAnalysisType> pi = this.elecContrastAnalysisManager.findPage(this.getElecContrastAnalysisType(),getStart(),getLimit());
		this.setElecContrastAnalysisTypeList(pi.getResult());
		this.setTotalCount(pi.getTotalCount());
		return SUCCESS;
    }
	
	// form 构造
	public ElecContrastAnalysisType getElecContrastAnalysisType() {
		ElecContrastAnalysisType elecContrastAnalysisType = new ElecContrastAnalysisType();
		elecContrastAnalysisType.setCapGradeName(this.capGradeName);
		elecContrastAnalysisType.setCapGradeNo(this.capGradeNo);

		return elecContrastAnalysisType;
	}
}