package com.nari.mydesk;

import java.util.List;

import com.nari.util.exception.DBAccessException;

public interface AOrgStatDDao {
	/**
	 * 
	 * @param staffNo 用户标识
	 * @return 用户和电量分布model
	 */
	public List<AOrgStatD> getAOrgStatDBystaffNo(String staffNo,String orgType);
	
	/**
	 * 
	 * @param orgNo 供电单位编号
	 * @param orgType 供电单位类别
	 * @return
	 */
	public List<AOrgStatD> getAOrgStatDByOrgNo(String orgNo,String orgType);
	
	/**
	 * 
	 * @param orgNo 供电单位编号
	 * @param orgType 供电单位类别
	 * @return 首页曲线model
	 */
	public List<MainCurve> getMainCurve(String orgNo,String orgType);
	public List<AOrgStatD> getAOrgStatDOrgType(String orgNo);
}
