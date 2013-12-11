package com.nari.qrystat.querycollpoint;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ISendDataQueryDao {
	/**
	 * 查询日电能量
	 * @param sendDataQueryFlag 是否逐日显示
	 * @param CONS_NO
	 * @param TG_ID
	 * @return 抄表数据
	 * @throws DBAccessException
	 */
	public Page<SendDataQuery> findSendDataQuery(String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,String DateStart,String DateEnd,String CONS_NO,String TG_ID,long start, int limit,PSysUser pSysUser) throws DBAccessException;
	/**
	 * 查询冻结电能示值
	 * @param CONS_NO
	 * @param sendDataQueryFlag 是否逐日显示
	 * @param TG_ID
	 * @return 抄表数据
	 * @throws DBAccessException
	 */
	public Page<SendDataQueryB> findSendDataQueryB(String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException;

	/**
	 * 
	 * @param CONS_NO
	 * @return 电能量分析数据
	 * @throws DBAccessException
	 */
	public List<SendDataQueryC> findSendDataQueryC(String DateStart,String DateEnd,String CONS_NO,PSysUser pSysUser) throws DBAccessException;

	/**
	 * 
	 * @param TG_ID
	 * @return 居民未抄户数据
	 * @throws DBAccessException
	 */
	public Page<SendDataQueryD> findSendDataQueryD(String CurDate,String TG_ID,long start, int limit,PSysUser pSysUser) throws DBAccessException;
	
	/**
	 * 
	 * @param asserNo 资产号
	 * @param dataDate 日期
	 * @return 总加组曲线数据
	 * @throws DBAccessException
	 */
	public EMpEnergyCurveBean findSendDataQueryE(String consNo,String asserNo,String dataDate,PSysUser pSysUser) throws DBAccessException;

	public List<SendDataQueryF> findSendDataQueryF(String DateStart,String DateEnd,String CONS_NO,String TG_ID,PSysUser pSysUser) throws DBAccessException;


/**
 * 查询冻结电能示值
 * @param CONS_NO
 * @param sendDataQueryFlag 是否逐日显示
 * @param TG_ID
 * @return 抄表数据
 * @throws DBAccessException
 */
public Page<SendDataQueryBFail> findSendDataQueryBFail(String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,
		String DateStart, String DateEnd, String CONS_NO, String TG_ID,
		int start, int limit, PSysUser pSysUser) throws DBAccessException;

/**
 * 查询实时抄表数据
 * @param CONS_NO
 * @param sendDataQueryFlag 是否逐日显示
 * @param TG_ID
 * @return 抄表数据
 * @throws DBAccessException
 */
public Page<SendDataQueryDay> findSendDataQueryDay(String fieldConsNo,String fieldassetNo,String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,
		String DateStart, String DateEnd, String CONS_NO, String TG_ID,
		int start, int limit, PSysUser pSysUser) throws DBAccessException;
}