package com.nari.qrystat.taskQuery;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-5-13
 *
 */
public interface TaskDetailsManager {
	/**
	 * 检索工单信息
	 * 
	 * @param eventInfo 检索条件
	 * @param start     分页开始条数
	 * @param limit     分页每页条数
	 * @return  分页工单信息list
	 */
	public Page<TaskDetailsDto> getEventInfo(TaskStatSearchInfoBean eventInfo, long start, int limit) throws DBAccessException;

}
