package com.nari.advapp.statanalyse.impl;

import java.util.List;
import org.springframework.dao.DataAccessException;

import com.nari.advapp.statanalyse.ChargeContrastAnalysisBean;
import com.nari.advapp.statanalyse.ElecContrastAnalysisManager;
import com.nari.advapp.statanalyse.ElecContrastAnalysisOrgNo;
import com.nari.advapp.statanalyse.ElecContrastAnalysisType;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;
public class ElecContrastAnalysisManagerImpl implements
		ElecContrastAnalysisManager {
private ElecContrastAnalysisDaoImpl elecContrastAnalysisDaoImpl;

	public void setElecContrastAnalysisDaoImpl(
		ElecContrastAnalysisDaoImpl elecContrastAnalysisDaoImpl) {
	this.elecContrastAnalysisDaoImpl = elecContrastAnalysisDaoImpl;
}

	@Override
	public List<ElecContrastAnalysisOrgNo> getElecContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType)
			throws DBAccessException {
try{
		return this.elecContrastAnalysisDaoImpl.getElecContrastAnalysisOrgNo(DateStart,DateEnd,ValueCombo,ValueArray,OrgNo,OrgType);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} 
	}

	public List<ChargeContrastAnalysisBean> getChargeContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType)
	throws DBAccessException {
try{
return this.elecContrastAnalysisDaoImpl.getChargeContrastAnalysisOrgNo(DateStart,DateEnd,ValueCombo,ValueArray,OrgNo,OrgType);
}catch(DataAccessException e) {
	throw new DBAccessException(BaseException.processDBException(e));
} 
}
	
	@Override
	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGrade()
			throws DBAccessException {
		try{
			return this.elecContrastAnalysisDaoImpl.getElecContrastAnalysiscapGrade();
			}catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			} 
	}
	
	
	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGradeVoltage()
	throws DBAccessException {
    try{
	return this.elecContrastAnalysisDaoImpl.getElecContrastAnalysiscapGradeVoltage();
	}catch(DataAccessException e) {
		throw new DBAccessException(BaseException.processDBException(e));
	} 
    }
	
//	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGradeBuss()
//	throws DBAccessException {
//    try{
//	return this.elecContrastAnalysisDaoImpl.getElecContrastAnalysiscapGradeBuss();
//	}catch(DataAccessException e) {
//		throw new DBAccessException(BaseException.processDBException(e));
//	} 
//    }
	
	public Page<ElecContrastAnalysisType> findPage(ElecContrastAnalysisType elecContrastAnalysisType,long start, long limit) throws Exception{
		try {
			return this.elecContrastAnalysisDaoImpl.findPage(elecContrastAnalysisType, start, limit);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("电量对比分析查询出错");
		}
	
	}
	
}
