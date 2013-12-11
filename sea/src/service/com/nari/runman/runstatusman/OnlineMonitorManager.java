package com.nari.runman.runstatusman;


import com.nari.support.Page;
import com.nari.util.exception.ServiceException;

public interface OnlineMonitorManager {
	
	
	
	public Page<OnOffLineMonitorDto> findPSysUsers(long start,long limit)throws Exception ;
	
	

}
