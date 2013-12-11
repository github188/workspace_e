package com.nari.qrystat.colldataanalyse;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 日用电负荷监测接口
 * @author 姜炜超
 */
public interface CurrLoadMonitorManager {
	/**
	 * 根据条件查询日用电负荷监测信息，针对grid显示
	 * @param list
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findCLMGridInfoByCond(List<CurrLoadMonitorDto> list, String queryDate, PSysUser pSysUser) throws Exception;
	
	/**
	 * 根据条件查询实时日用电负荷监测信息，针对曲线
	 * @param bean
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMRealCurInfoByCond(CurrLoadMonitorDto bean, String queryDate, PSysUser pSysUser) throws Exception;
	
	
	/**
	 * 根据条件查询冻结日用电负荷监测信息，针对曲线
	 * @param bean
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMVSTATCurInfoByCond(CurrLoadMonitorDto bean, String queryDate, PSysUser pSysUser) throws Exception;
	
	/**
	 * 根据供电单位查询该单位及子单位信息，点击左边树响应
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @return List<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findOrgInfo(String orgNo, String orgType, PSysUser pSysUser) throws Exception;
	
    /**
	 * 查询时间模板，为生成曲线而用
	 * @return 
	 */
	public List<CurrLoadMonitorDto> getTimeModelList() ;
}
