package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ExcptionReportManager {
	public Page<ExcptionReport> findExcptionCount(String treeType,String orgNo, String orgType,
			PSysUser pSysUser, String dateStart, String dateEnd, long start, int limit) throws DBAccessException;
	public List<VwConsType> findConsType() throws DBAccessException;
}
