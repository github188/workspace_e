package com.nari.qrystat.querycollpoint;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public interface ExcptionReportDao{
	public Page<ExcptionReport> findExcptionCount(String treeType,String orgNo,String orgType,PSysUser pSysUser,String dateStart,String dateEnd, long start, int limit);
	public List<VwConsType> findConsType();
}
