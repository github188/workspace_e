package com.nari.qrystat.colldataanalyse.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineLosePowerBean;
import com.nari.qrystat.colldataanalyse.LineLosePowerStatJdbcDao;
import com.nari.qrystat.colldataanalyse.LineLosePowerStatManager;
import com.nari.qrystat.colldataanalyse.LosePowerStatDto;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 线路用电损耗统计接口实现类
 * @author 姜炜超
 */
public class LineLosePowerStatManagerImpl implements LineLosePowerStatManager{
	
	private LineLosePowerStatJdbcDao lineLosePowerStatJdbcDao; 
	
	public void setLineLosePowerStatJdbcDao(
			LineLosePowerStatJdbcDao lineLosePowerStatJdbcDao) {
		this.lineLosePowerStatJdbcDao = lineLosePowerStatJdbcDao;
	}

	/**
	 * 根据条件查询时间段内线路用电损耗统计信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param statFlag 02表示月，03表示季度，04表示年
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerStat(String nodeId, String nodeType, String orgType, 
			String statFlag, Date startDate, Date endDate,long start, int limit, PSysUser pSysUser)throws Exception{
		if(null == statFlag || null == startDate || null == endDate || null == nodeId){
			return new Page<LineLosePowerBean>();
		}
		Page<LineLosePowerBean> psc = null;//定义返回分页对象
		Integer startD = null;
		Integer endD = null;
		if("02".equals(statFlag)){//月份
			startD = Integer.valueOf(DateUtil.dateToStringNoV(startDate).substring(0,6));
			endD = Integer.valueOf(DateUtil.dateToStringNoV(endDate).substring(0,6));
		}else if("03".equals(statFlag)){//年份
			String s1 = DateUtil.dateToStringNoV(startDate).substring(0,4);
			String e1 = DateUtil.dateToStringNoV(endDate).substring(0,4);
			Integer s2 = Integer.valueOf(DateUtil.dateToStringNoV(startDate).substring(4,6));
			Integer e2 = Integer.valueOf(DateUtil.dateToStringNoV(endDate).substring(4,6));
			startD = Integer.valueOf(s1+getSeason(s2));
			endD = Integer.valueOf(e1+getSeason(e2));
		}else{
			startD = Integer.valueOf(DateUtil.dateToString(startDate).substring(0,4));
			endD = Integer.valueOf(DateUtil.dateToString(endDate).substring(0,4));
		}		
		try{
			psc = lineLosePowerStatJdbcDao.findLineLosePowerStat(nodeId, nodeType, orgType, statFlag, 
					startD, endD, start, limit, pSysUser);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_LINELOSEPOWERSTATINFO);
		}
		
		return psc;
	}
	
	/**
	 * 通过月份获取月份所在的季度
	 * @param month
	 * @return
	 */
	private String getSeason(int month){
		if(month <= 3){
			return "01";
		}else if(month <= 6){
			return "02";
		}else if(month <= 9){
			return "03";
		}else if(month <= 12){
			return "04";
		}
		return "01";
	}
}
