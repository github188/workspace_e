package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IConsumerInfoManager {
	public List<ConsumerInfo> findConsumerInfo(String CONS_NO)  throws DBAccessException;
	public List<Tmnlrun> findTmnlrun(String CONS_NO) throws DBAccessException;
	public Page<Cmeter> findCmeter(String CONS_NO,String TG_ID,long start, int limit) throws DBAccessException;
	public Page<NewMpDayPower> findNewMpDayPower(String assetNo,String flagValue,String CONS_NO,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException;
	public Page<CmeterInfo> findCmeterInfo(String CONS_NO,
			long start, int limit) throws DBAccessException;
}
