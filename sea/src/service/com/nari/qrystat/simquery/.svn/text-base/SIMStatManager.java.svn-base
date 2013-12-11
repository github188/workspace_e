package com.nari.qrystat.simquery;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * SIM卡运行情况统计信息服务
 * 
 * @author 杨传文
 */
public interface SIMStatManager {

	/**
	 * @return SIM卡安装情况统计列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<SimInstall> findSim(String psPart,String simNo, long start, int limit,PSysUser pSysUser)
			throws DBAccessException;

	/**
	 * @return SIM卡超流量统计列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<SIMOverTraffic> findSimOverTraffic(String psPart,
			String chargeDateFrom, String chargeDateTo, String runner,
			long start, int limit) throws DBAccessException;

	/**
	 * 查询SIM卡流量信息
	 * @param simNo
	 * @param chargeDateFrom
	 * @param chargeDateTo
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<SimDetailFlowInfoBean> findSimDetailFlowInfo(String simNo,
			Date chargeDateFrom, Date chargeDateTo, long start, int limit) throws DBAccessException ;

	/**
	 * SIM卡流量分析
	 * @param dateFrom
	 * @param dateTo
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<SimTrafficInfoBean> findSimTrafficInfo(PSysUser user, String dateFrom,
			String dateTo,String orgNo, long start, int limit) throws DBAccessException;
}
