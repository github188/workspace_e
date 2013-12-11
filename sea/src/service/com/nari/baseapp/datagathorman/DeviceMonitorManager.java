package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.DeviceExcepDto;
import com.nari.baseapp.datagatherman.DeviceMonitorDto;
import com.nari.basicdata.VwTmnlFactory;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.util.exception.DBAccessException;

public interface DeviceMonitorManager {

	/**
	 * 查询按严重级别来分类的设备监测情况
	 * @param monitorStartDate 开始时间
	 * @param monitorEndDate 结束时间
	 * @throws Exception
	 */
	public List<DeviceMonitorDto> querySerisLevelInfo(PSysUser pSysUser,String orgNo,String monitorStartDate,
										String monitorEndDate) throws Exception;
	
	/**
	 * 查询按生产厂商来分类的设备监测情况
	 * @param monitorStartDate 开始时间
	 * @param monitorEndDate 结束时间
	 * @throws Exception
	 */
	public List<DeviceMonitorDto> queryDeviceFactoryInfo(PSysUser pSysUser,String orgNo,String monitorStartDate,
											String monitorEndDate) throws Exception;
	/**
	 * 设备监测严重级别(每日统计)
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return page
	 * @throws DBAccessException
	 */
	public List<AEventStatDWindowDto>  queryDaySeriousLevel(PSysUser pSysUser,String orgNo,String levelNo,String startDate,String endDate,long start, int limit) throws Exception;
	
	/**
	 * 查询终端告警事件
	 * @param orgNo 单位编码
	 * @param excepStartDate 开始时间
	 * @param excepEndDate 结束时间
	 * @param factory 生成厂商
	 * @param dataType 数据类型
	 * @return
	 * @throws Exception
	 */
	public List<DeviceExcepDto> queryDeviceExcepInfo(String orgNo,String excepStartDate,
				String excepEndDate,String factory,String dataType) throws Exception;
	
	/**
	 * 查询全部生产厂家
	 * @return
	 * @throws Exception
	 */
	public List<VwTmnlFactory> getAllFactory() throws Exception;
}
