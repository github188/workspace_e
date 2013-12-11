package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ISendDataQueryManager {
	public Page<SendDataQuery> findSDQuery(String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,String DateStart,String DateEnd,String CONS_NO, String TG_ID,
			long start, int limit,PSysUser pSysUser) throws DBAccessException;
	public Page<SendDataQueryB> findSendDataQueryB(String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException;
	public List<SendDataQueryC> findSendDataQueryC(String DateStart,
			String DateEnd, String CONS_NO,PSysUser pSysUser)
			throws DBAccessException;
	public Page<SendDataQueryD> findSendDataQueryD(String CurDate,String TG_ID,long start, int limit,PSysUser pSysUser) throws DBAccessException;
	public List<GeneralDataBean> findSendDataQueryE(String consNo,String asserNo,String dataDate,PSysUser pSysUser) throws DBAccessException;

	public List<SendDataQueryF> findSendDataQueryF(String DateStart,
			String DateEnd, String CONS_NO,String TG_ID,PSysUser pSysUser)
			throws DBAccessException;
	public Page<SendDataQueryBFail> findSendDataQueryBFail(String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException;
	public Page<SendDataQueryDay> findSendDataQueryDay(String fieldConsNo,String fieldassetNo,String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException;

}
