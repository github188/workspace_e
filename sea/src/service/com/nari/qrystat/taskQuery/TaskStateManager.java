package com.nari.qrystat.taskQuery;

import java.util.Date;
import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 
 * 工单查询统计service
 * 
 * @author ChunMingLi
 * 2010-4-28
 *
 */
public interface TaskStateManager {
	/**
	 * 
	 * 按照地区统计工单
	 * 
	 * @param dateFrom  查询开始时间
	 * @param dateTo    查询结束时间
	 * @param start		分页开始页
	 * @param limit		每页显示数
	 * @return			返回查询类型的Page
	 * @throws DBAccessException  数据库异常
	 */
	public List<TaskStateArea> findTaskArea(Date dateFrom, Date dateTo)throws DBAccessException ;
	
	/**
	 * 
	 * 查询工单统计集合 按照类型
	 * 
	 * @param dateFrom  查询开始时间
	 * @param dateTo    查询结束时间
	 * @param start		分页开始页
	 * @param limit		每页显示数
	 * @return			返回查询类型的Page
	 * @throws DBAccessException  数据库异常
	 */
	public List<TaskStatTypeDto> findTaskType(Date dateFrom, Date dateTo)throws DBAccessException ;
	
	/**
	 * 
	 *  返回 查询范围的供电单位集合
	 * 
	 * @return  返回供电单位集合
	 * @throws DBAccessException 数据库异常
	 */
	public List<String> findTaskOrgNo(Date dateFrom, Date dateTo) throws DBAccessException ;
	
	/**
	 * 
	 *  返回所有供电单位集合
	 * 
	 * @return  返回供电单位集合
	 * @throws DBAccessException 数据库异常
	 */
	public List<String> findTaskOrgNo() throws DBAccessException ;
	
	/**
	 * 
	 * 获得展现DTO
	 * 
	 * @param orgNoList  确定的供电单位工单集合
	 * @return TaskStatDto 展现DTO
	 */
	public TaskStatDto getTaskStatDto(List<TaskStateArea> orgNoList);
	
	/**
	 * 
	 * 分析工单查询按照供电单位分类
	 * 
	 * @param orgNoNameList 供电单位集合
	 * @param taskList      工单集合
	 * @return  按照供电单位分类的集合
	 */
	public List<TaskStatDto> processTaskStateList(List<String> orgNoNameList,List<TaskStateArea> taskList);
	
	//待办工单 00新异常
	public static final String OP_TYPE_STATE_DBGD = "00";
	
	// 02营销处理中
	public static final String OP_TYPE_STATE_YXCLZ = "02";

	// 03正常归档
	public static final String OP_TYPE_STATE_ZCGD = "03";

	// 04误报确认
	public static final String OP_TYPE_STATE_WBQR = "04";

	// 06挂起
	public static final String OP_TYPE_STATE_GQ = "06";

	// 07本地处理中
	public static final String OP_TYPE_STATE_BDCLZ = "07";

	// 08强制归档
	public static final String OP_TYPE_STATE_QZGD = "08";
	
	//系统异常
	public static final int EVENT_TYPE_EXCEPTION = 1;
	
	//终端上报事件
	public static final int EVENT_TYPE_TERMINAL_UP_MSG = 2;
	
	//主站分析终端故障
	public static final int EVENT_TYPE_TERMINAL_BUG = 3;
	
	//主站分析用电异常
	public static final int EVENT_TYPE_ELEC_EXCEPTION = 4;
	
	//主站分析数据异常
	public static final int EVENT_TYPE_DATA_EXCEPTION = 5;
}
