package com.nari.runman.runstatusman;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface OnlineMonitorDao {
	
	
	public Page<OnOffLineMonitorDto> findPSysUsers(long start,long limit)throws Exception ;
	

}
