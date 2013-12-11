package com.nari.qrystat.simquery;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * SIM卡运行情况统计信息DAO
 * 
 * @author 杨传文
 */
public interface SIMStatDao {

	/**
	 * @param psPart
	 *            供电单位
	 * @param runner
	 *            运营商
	 * @param statusCode
	 *            运行状态
	 * @param start
	 *            分页参数
	 * @param limit
	 *            每页记录条数
	 * @return SIM卡安装情况统计列表
	 */
	public Page<SimInstall> findSim(String psPart,String simNo, long start, int limit,
			PSysUser pSysUser);

	/**
	 * @param psPart
	 *            供电单位
	 * @param chargeDateFrom
	 *            统计日起
	 * @param chargeDateTo
	 *            统计日止
	 * @param runner
	 *            运营商
	 * @param start
	 *            分页参数
	 * @param limit
	 *            每页记录条数
	 * @return SIM卡超流量统计列表
	 */
	public Page<SIMOverTraffic> findSimOverTraffic(String psPart,
			String chargeDateFrom, String chargeDateTo, String runner,
			long start, int limit);

	/**
	 *  查询SIM卡流量信息
	 * @param simNo
	 * @param chargeDateFrom
	 * @param chargeDateTo
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<SimDetailFlowInfoBean> findSimDetailFlowInfo(String simNo,
			Date chargeDateFrom, Date chargeDateTo, long start, int limit) throws DBAccessException;

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
