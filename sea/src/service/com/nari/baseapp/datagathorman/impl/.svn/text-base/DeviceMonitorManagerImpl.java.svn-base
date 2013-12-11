package com.nari.baseapp.datagathorman.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagatherman.DeviceExcepDto;
import com.nari.baseapp.datagatherman.DeviceMonitorDto;
import com.nari.baseapp.datagathorman.DeviceMonitorJdbcDao;
import com.nari.baseapp.datagathorman.DeviceMonitorManager;
import com.nari.basicdata.VwTmnlFactory;
import com.nari.basicdata.VwTmnlFactoryJdbcDao;
import com.nari.oranization.OOrgDao;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class DeviceMonitorManagerImpl implements DeviceMonitorManager {

	private DeviceMonitorJdbcDao deviceMonitorJdbcDao;
	private OOrgDao oOrgDao;
	private VwTmnlFactoryJdbcDao vwTmnlFactoryJdbcDao;
	public void setDeviceMonitorJdbcDao(DeviceMonitorJdbcDao deviceMonitorJdbcDao) {
		this.deviceMonitorJdbcDao = deviceMonitorJdbcDao;
	}
	public void setoOrgDao(OOrgDao oOrgDao) {
		this.oOrgDao = oOrgDao;
	}
	public void setVwTmnlFactoryJdbcDao(VwTmnlFactoryJdbcDao vwTmnlFactoryJdbcDao) {
		this.vwTmnlFactoryJdbcDao = vwTmnlFactoryJdbcDao;
	}
	/**
	 * 查询设备监测具体时间内的情况
	 * @param monitorStartDate 开始时间
	 * @param monitorEndDate 结束时间
	 * @throws Exception
	 */
	public List<DeviceMonitorDto> querySerisLevelInfo(PSysUser pSysUser,String orgNo,String monitorStartDate,
										String monitorEndDate) throws Exception{
		List<DeviceMonitorDto> serisLevelList = new ArrayList<DeviceMonitorDto>();
		try{
			OOrg oOrg = this.oOrgDao.findById(orgNo);
			String orgType = oOrg==null?"":oOrg.getOrgType();
			serisLevelList = this.deviceMonitorJdbcDao.findAllByOrg(pSysUser, orgType, orgNo, monitorStartDate, monitorEndDate);
			return serisLevelList;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询设备监测具体时间内的情况出错");
		}
	}
	@Override
	public List<AEventStatDWindowDto> queryDaySeriousLevel(PSysUser pSysUser,String orgNo,String levelNo,String startDate,String endDate,long start, int limit) throws Exception{
		try {
			return this.deviceMonitorJdbcDao.findDaySeriousLevel(pSysUser, orgNo, levelNo,startDate, endDate, start, limit);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	/**
	 * 查询按生产厂商来分类的设备监测情况
	 * @param monitorStartDate 开始时间
	 * @param monitorEndDate 结束时间
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DeviceMonitorDto> queryDeviceFactoryInfo(PSysUser pSysUser,String orgNo,String monitorStartDate,
											String monitorEndDate) throws Exception{
		List<DeviceMonitorDto> deviceFactoryList = new ArrayList<DeviceMonitorDto>();
		try{
			OOrg oOrg = this.oOrgDao.findById(orgNo);
			String orgType = oOrg==null?"":oOrg.getOrgType();
			deviceFactoryList = this.deviceMonitorJdbcDao.findMonitorByFactory(pSysUser,orgType, orgNo, monitorStartDate, monitorEndDate);
			return deviceFactoryList;
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询按生产厂商来分类的设备监测情况出错");
		}
	}
	
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
				String excepEndDate,String factory,String dataType) throws Exception{
		List<DeviceExcepDto> list = new ArrayList<DeviceExcepDto>();
		try{
			DeviceExcepDto d1 = new DeviceExcepDto();DeviceExcepDto d2 = new DeviceExcepDto();
			d1.setConsName("用户1");d1.setConsNo("111");d1.setEventName("1");d1.setHappTime("22:33:33");
			d1.setOrgName("合肥");d1.setPsData("1");d1.setRepairTime("23:33:33");d1.setTerminalAddr("111111");
			d1.setStatus("1234");d2.setStatus("2234");
			d2.setConsName("用户2");d2.setConsNo("222");d2.setEventName("2");d2.setHappTime("11:33:33");
			d2.setOrgName("合肥");d2.setPsData("2");d2.setRepairTime("21:33:33");d2.setTerminalAddr("222222");
			list.add(d1);list.add(d2);
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询终端告警事件出错");
		}
	}
	
	/**
	 * 查询全部生产厂家
	 * @return
	 * @throws Exception
	 */
	public List<VwTmnlFactory> getAllFactory() throws Exception{
		List<VwTmnlFactory> list = new ArrayList<VwTmnlFactory>();
		try{
			list = this.vwTmnlFactoryJdbcDao.findAll();
			return list;
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询全部生产厂家出错");
		}
	}

}
