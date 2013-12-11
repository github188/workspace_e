package com.nari.runman.runstatusman;

import java.util.Date;

import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDBean;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.statreport.AmmeterHDto;
import com.nari.statreport.AmmeterHWindowDto;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface AEventStatDBeanDao {
	/**
	 * 电能表运行状态
	 * @param startDate	开始日期
	 * @param endDate	结束日期	
	 * @return
	 */
	public Page<AEventStatDBean>findAEventStatDBean(PSysUser user, Date startDate, Date endDate, long start, int limit);
	
	/**
	 * 电能表超差
	 * 
	 * @param orgNo  
	 * 			 供电单位编号
	 * @param eventNo  
	 * 			异常事件编码
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @param startDate
	 *            查询开始时间
	 * @param endDate
	 *            查询结束时间
	 * @return page
	 * @throws DBAccessException
	 */
	public Page<AmmeterHDto> findAmmeterHDto(PSysUser user,String orgNo, String eventNo, Date startDate, Date endDate, long start, int limit) throws DBAccessException;
	
	/**
	 * 电能表异常窗口
	 * 
	 * @param eventId  
	 * 			数据ID
	 * @param areaCode
	 * 			存放区域
	 * @param eventNo  
	 * 			异常事件编码 
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @return	  page
	 * 
	 * @throws DBAccessException
	 */
	public Page<AmmeterHWindowDto> findAmmeterHWindowList(String eventId,String areaCode,String eventNo,long start, int limit)throws DBAccessException;
	
	/**
	 * 电能表异常每日统计列表
	 * 
	 * @param orgNo
	 * @param eventNo
	 * @return  page
	 * @throws DBAccessException
	 */
	public Page<AEventStatDWindowDto> findAEventStatDWindow(String orgNo,String eventNo,Date startDate, Date endDate,long start, int limit)throws DBAccessException;
}
