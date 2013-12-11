package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public interface IpublicTerminalDao {
	/**
	 * 查找相关的公变采集点信息
	 * 
	 * @author zhaoliang
	 * @param pbid传入的查询id
	 * @param pbtype
	 * @param start
	 * @param limit
	 * @return公变信息集合
	 */
	public Page<PublicTerminal> findPBTerminal(String consType,String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser);

	public Page<PublicTerminal> findPRTerminal(String consType,String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser);
	
	/**
	 * @param
	 * @return 终端状态
	 */
	public List<PublicTerminal> findTmnlStatus();
	
	
}