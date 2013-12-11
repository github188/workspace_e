package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IRCPManager {
	public List<RCP> findRCP(String consNo) throws DBAccessException;
	public Page<RCollObj> findRcpCharge(String consNo,long start, int limit)
	throws DBAccessException;
	public List<RcpCommPara> findRcpCommPara(String consNo)
	throws DBAccessException;
}
