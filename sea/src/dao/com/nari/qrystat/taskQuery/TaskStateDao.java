package com.nari.qrystat.taskQuery;

import java.util.Date;
import java.util.List;



/**
 * 
 * 
 * 工单查询统计DAO
 * 
 * @author ChunMingLi
 * 2010-4-28
 *
 */
public interface TaskStateDao {
	
	/**
	 * 
	 * 查询工单统计集合
	 * 
	 * @param dateFrom 查询开始时间
	 * @param dateTo 查询结束时间
	 * @return 返回工单集合
	 */
	public List<TaskStatTypeDto> findTaskType(Date dateFrom, Date dateTo);
	
	/**
	 * 
	 * 查询工单统计供电单位集合
	 * 
	 * @param dateFrom 查询开始时间
	 * @param dateTo 查询结束时间
	 * @return 返回工单供电单位集合
	 */
	public List<TaskStateArea> findTaskArea(Date dateFrom, Date dateTo);
	
	/**
	 * 
	 * 返回 查询范围的供电单位集合
	 * 
	 * @return 返回供电单位集合
	 */
	public List<String> findTaskOrgNo(Date dateFrom, Date dateTo);
	
	/**
	 * 
	 * 返回所有供电单位集合
	 * 
	 * @return 返回供电单位集合
	 */
	public List<String> findTaskOrgNo();
}
