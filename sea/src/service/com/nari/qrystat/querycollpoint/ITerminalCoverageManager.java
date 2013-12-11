package com.nari.qrystat.querycollpoint;

import java.util.List;
import java.util.Map;

import com.nari.privilige.PSysUser;
import com.nari.util.exception.ServiceException;

public interface ITerminalCoverageManager {
	/*****按容量统计，未分页版
	 * @param user 登陆用户
	 * @param node 点击的节点
	 * @return 按容量查询
	 * @throws ServiceException *****/
	@SuppressWarnings("unchecked")
public	List<Map> findByCapacity(PSysUser user, String node)
			throws ServiceException;
	/********按地区统计未分页版*****/
	/*****
	 * @param user 登陆用户
	 * @param node 点击的节点
	 * @return 按地区查询
	 * @throws ServiceException *****/
	List<Map> findByArea(PSysUser user, String node) throws ServiceException;

}
