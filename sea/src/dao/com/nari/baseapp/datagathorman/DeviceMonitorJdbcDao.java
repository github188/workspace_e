package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.DeviceMonitorDto;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.util.exception.DBAccessException;

public interface DeviceMonitorJdbcDao {

	/**
	 * 查询设备监测情况
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	public List<DeviceMonitorDto> findAllByOrg(PSysUser pSysUser,String orgType,String orgNo,String startDate,String endDate) throws DBAccessException;

	/**
	 * 查询设备监测（分生产厂家）
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findMonitorByFactory(PSysUser pSysUser,String orgType,String orgNo,String startDate,String endDate)throws DBAccessException;
	
	/**
	 * 设备监测严重级别(每日统计)
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return page
	 * @throws DBAccessException
	 */
	public List<AEventStatDWindowDto>  findDaySeriousLevel(PSysUser pSysUser,String orgNo,String levelNo,String startDate,String endDate,long start, int limit) throws DBAccessException;
}
