package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ICCustManager {
	public List<CCust> findCCust(String custNo) throws DBAccessException;
	public Page<Ccontact> findCcontact(String custNo,long start,int limit)
	throws DBAccessException;
	public List<CSP> findCSP(String consNo)
	throws DBAccessException;
	public Page<CPS> findCPS(String consNo,long start,int limit)
	throws DBAccessException;
	public Page<RSIMCharge> findSIM(String SIM_NO, long start, int limit)
	throws DBAccessException;
	public List<DMeter> findDMeter(String consNo) throws DBAccessException;
	public Page<CITRun> findCITRun(String tFactor,long start,int limit)
	throws DBAccessException;
	public List<Gtran> findGtran(String CONS_ID,String consType) throws DBAccessException;
	public Page<Gtran> findGtran(String CONS_ID, long start, int limit)
	throws DBAccessException;
}
