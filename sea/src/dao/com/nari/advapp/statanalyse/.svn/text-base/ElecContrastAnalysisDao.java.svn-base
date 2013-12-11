package com.nari.advapp.statanalyse;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ElecContrastAnalysisDao {
	/**
	 * 按照供电单位进行电量对比分析
	 * @param orgNo 选择的供电单位数组
	 * @param DateStart
	 * @param DateEnd
	 * @return 供电单位电量曲线信息
	 * @throws DBAccessException
	 */
	public List<ElecContrastAnalysisOrgNo> getElecContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType) throws DBAccessException;
	public List<ChargeContrastAnalysisBean> getChargeContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType) throws DBAccessException;
/**
 * 
 * @return 合同容量
 * @throws DBAccessException
 */
	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGrade() throws DBAccessException;
	public Page<ElecContrastAnalysisType> findPage(ElecContrastAnalysisType elecContrastAnalysisType,long start, long limit) throws DBAccessException;
}
