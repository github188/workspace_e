/**
 * 
 */
package com.nari.mydesk;

import java.util.List;

import com.nari.privilige.PSysUser;

/**
 * 
 * 线损统计DAO
 * @author ChunMingLi
 * @since 2010-8-18
 *
 */
public interface LosePowerStatAnalyseDao {
	/**
	 * 线损统计
	 * @param queryDate 查询时间
	 * @param orgNo 供电单位
	 * @param orgType 单位类别
	 * @param pSysUser 操作员信息
	 * @return list
	 * @throws Exception
	 */
	public List<LosePowerStatAnalyseDto> queryTGLosePowerStatA(String queryDate,String orgNo, String orgType, PSysUser pSysUser)throws Exception;
	
	/**
	 * 线损统计
	 * @param queryDate 查询时间
	 * @param orgNo 供电单位
	 * @param orgType 单位类别
	 * @param pSysUser 操作员信息
	 * @return list
	 * @throws Exception
	 */
	public List<LosePowerStatAnalyseDto> queryLineLosePowerStatA(String queryDate,String orgNo, String orgType, PSysUser pSysUser)throws Exception;
}
