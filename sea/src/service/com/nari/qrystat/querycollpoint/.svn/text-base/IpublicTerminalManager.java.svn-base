package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IpublicTerminalManager {
	public Page<PublicTerminal> findPBTerminal(String consType,String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser) throws DBAccessException;

	public Page<PublicTerminal> findPRTerminal(String consType,String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser) throws DBAccessException;
	
	
	/**
	 * @return 查询生产厂家
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<PublicTerminal> findTmnlStatus()throws DBAccessException;
}
