package com.nari.qrystat.colldataanalyse.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineEcelUserDto;
import com.nari.qrystat.colldataanalyse.LineEceltricQuantityDao;
import com.nari.qrystat.colldataanalyse.LineEceltricQuantityDto;
import com.nari.qrystat.colldataanalyse.LineEceltricQuantityManager;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 线路电量查询service类
 * @author ChunMingLi
 * @since  2010-6-23
 *
 */
public class LineEceltricQuantityManagerImpl implements LineEceltricQuantityManager {
	private LineEceltricQuantityDao lineEceltricQuantityDao;

	@Override
	public Page<LineEceltricQuantityDto> queryPageLineEcel(
			String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		
		try {
			return this.lineEceltricQuantityDao.queryPageLineEcel(nodeType, nodeValue, userInfo, start, limit, startDate, endDate);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("根据左边树节点查询主站分析异常出错");
		}
	}

	
	@Override
	public Page<LineEcelUserDto> queryPageLineEcelUser(String lineId,
			PSysUser userInfo, long start, long limit, Date startDate,Date endDate) throws Exception {
		try {
			return this.lineEceltricQuantityDao.queryPageLineEcelUser(lineId, userInfo, start, limit, startDate, endDate);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("线路电量用户明细查询出错");
		}
	}
	
	/**
	 *  the lineEceltricQuantityDao to set
	 * @param lineEceltricQuantityDao the lineEceltricQuantityDao to set
	 */
	public void setLineEceltricQuantityDao(
			LineEceltricQuantityDao lineEceltricQuantityDao) {
		this.lineEceltricQuantityDao = lineEceltricQuantityDao;
	}

}
