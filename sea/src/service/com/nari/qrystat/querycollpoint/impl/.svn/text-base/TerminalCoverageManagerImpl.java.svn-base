package com.nari.qrystat.querycollpoint.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.ITerminalCoverageManager;
import com.nari.support.Page;
import com.nari.util.exception.ServiceException;

public class TerminalCoverageManagerImpl implements ITerminalCoverageManager {
	private Logger logger = Logger.getLogger(this.getClass());
	/*** 统计容量情况,分页版 ***/
	@SuppressWarnings("unchecked")
	public Page<Map> findPageByCapacity(PSysUser user, int start, int limit) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		logger.debug("按容量统计未分页版开始");
		MapResultMapper mrp = new MapResultMapper("[*,c]");
		SqlData sd = SqlData.getOne();
		logger.debug(String.format(sd.coverageQuery, ""));
		logger.debug("按容量统计未分页版结束");
		return tree.pagingFind(String.format(sd.coverageQuery, ""), start,
				limit, mrp, new Object[] {});
	}

	/*****
	 * 按容量统计，未分页版
	 * 
	 * @param user
	 *            登陆用户
	 * @param node
	 *            点击的节点
	 * @return 按容量查询
	 * @throws ServiceException
	 *             *
	 ****/
	@SuppressWarnings("unchecked")
	public List<Map> findByCapacity(PSysUser user, String node)
			throws ServiceException {
		try {
			logger.debug("按容量统计开始");
			String[] values = node.split("_");
			String type = values[0];
			if (!type.equals("org")) {
				throw new RuntimeException("必须是区域节点");
			}
			String value = values[1];
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			MapResultMapper mrp = new MapResultMapper("[*,c]");
			SqlData sd = SqlData.getOne();
			
			logger.debug(String.format(getPowerSql(sd.coverageQuery,user), ""));
			logger.debug("按容量统计结束");
			return tree.getJdbcTemplate().query(
					String.format(getPowerSql(sd.coverageQuery,user), ""),
					new Object[] { value, user.getStaffNo() }, mrp);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}

	/******** 按地区统计未分页版 *****/
	/*****
	 * @param user
	 *            登陆用户
	 * @param node
	 *            点击的节点
	 * @return 按地区查询
	 * @throws ServiceException
	 *             *
	 ****/
	@SuppressWarnings("unchecked")
	public List<Map> findByArea(PSysUser user, String node)
			throws ServiceException {
		try {
			logger.debug("按地区统计开始");
			String[] values = node.split("_");
			String type = values[0];
			if (!type.equals("org")) {
				throw new RuntimeException("必须是区域节点");
			}
			String value = values[1];
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			MapResultMapper mrp = new MapResultMapper("[*,c]");
			SqlData sd = SqlData.getOne();
			
			logger.debug(String.format(getPowerSql(sd.coverageQueryByArea,user), ""));
			logger.debug("按地区统计结束");
			return tree.getJdbcTemplate().query(
					String.format(getPowerSql(sd.coverageQueryByArea,user), ""),
					new Object[] { value, user.getStaffNo() }, mrp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	/**
	 * 此方法用于对不同级别的用户得到不同的区县访问的权限
	 * 
	 * @param input
	 * @param user
	 * @return
	 */
	private String getPowerSql(String input, PSysUser user) {
		StringBuilder sb = new StringBuilder(input);
		int len = sb.indexOf("#ORG");
		if (Integer.valueOf(user.getAccessLevel()) <= 4) {
			sb.replace(len, len + 4, "p_org_no");
		} else {
			sb.replace(len, len + 4, "org_no");
		}
		input=null;
		return sb.toString();
	}
}
