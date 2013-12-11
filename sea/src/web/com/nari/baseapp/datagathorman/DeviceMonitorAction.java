package com.nari.baseapp.datagathorman;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.DeviceExcepDto;
import com.nari.baseapp.datagatherman.DeviceMonitorDto;
import com.nari.basicdata.VwTmnlFactory;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDWindowDto;

/**
 * 设备监测Action
 * @author 余涛
 *
 */
public class DeviceMonitorAction extends BaseAction {

	private Logger logger = Logger.getLogger(this.getClass());
	
	/* 自动注入业务层 */
	private DeviceMonitorManager deviceMonitorManager;
	public void setDeviceMonitorManager(DeviceMonitorManager deviceMonitorManager) {
		this.deviceMonitorManager = deviceMonitorManager;
	}

	private boolean success = true;
	
	private List<VwTmnlFactory> vwFactoryList;
	
	private String monitorStartDate;
	private String monitorEndDate;
	private List<DeviceMonitorDto> serisLevelList;
	private List<DeviceMonitorDto> deviceFactoryList;
	private List<DeviceMonitorDto> serisJsonList;
	private List<DeviceMonitorDto> factoryJsonList;
	//严重级别(每日统计)
	private List<AEventStatDWindowDto> dmWindowList;
	
	private String levelNo;
	private String orgName;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;

	private String orgNo;
	private String queryType;
	private String factoryCode;
	private String excepStartDate;
	private String excepEndDate;
	private List<DeviceExcepDto> deviceExcepList;
	
	/**
	 * 查询设备监测情况（分严重级别和生成厂商两种情况）
	 * @throws Exception
	 */
	public String queryMonitorInfo() throws Exception {
		this.logger.debug("获得设备监测情况");
		PSysUser pSysUser = new PSysUser();
		pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			this.serisLevelList = new ArrayList<DeviceMonitorDto>();
			this.deviceFactoryList = new ArrayList<DeviceMonitorDto>();
			this.serisJsonList = new ArrayList<DeviceMonitorDto>();
			this.factoryJsonList = new ArrayList<DeviceMonitorDto>();
			return SUCCESS;
		}
		this.serisLevelList = this.deviceMonitorManager.querySerisLevelInfo(pSysUser,pSysUser.getOrgNo(),monitorStartDate, monitorEndDate);
		this.deviceFactoryList = this.deviceMonitorManager.queryDeviceFactoryInfo(pSysUser,pSysUser.getOrgNo(),monitorStartDate, monitorEndDate);
		if(serisLevelList==null){
			serisLevelList = new ArrayList<DeviceMonitorDto>();
		}
		if(deviceFactoryList==null){
			deviceFactoryList = new ArrayList<DeviceMonitorDto>();
		}
		
		this.serisJsonList = new ArrayList<DeviceMonitorDto>();
		DeviceMonitorDto serisDto1 = new DeviceMonitorDto();serisDto1.setTypes("严重");
		DeviceMonitorDto serisDto2 = new DeviceMonitorDto();serisDto2.setTypes("次要");
		DeviceMonitorDto serisDto3 = new DeviceMonitorDto();serisDto3.setTypes("一般");
		int serisVaule1 = 0,serisVaule2 = 0,serisVaule3 = 0;
		for(int i = 0; i<serisLevelList.size(); i++){
			serisVaule1 += serisLevelList.get(i).getSerisEvents();
			serisVaule2 += serisLevelList.get(i).getMinorEvents();
			serisVaule3 += serisLevelList.get(i).getGenerEvents();
		}
		serisDto1.setValue(serisVaule1);
		serisDto2.setValue(serisVaule2);
		serisDto3.setValue(serisVaule3);
		this.serisJsonList.add(serisDto1);
		this.serisJsonList.add(serisDto2);
		this.serisJsonList.add(serisDto3);
		
		this.factoryJsonList = new ArrayList<DeviceMonitorDto>();
		this.vwFactoryList = this.deviceMonitorManager.getAllFactory();
		if(vwFactoryList==null) return SUCCESS;
		int[] values = new int[vwFactoryList.size()];
		for(int i=0;i<vwFactoryList.size();i++){
			DeviceMonitorDto factoryDto = new DeviceMonitorDto();
			factoryDto.setTypes(vwFactoryList.get(i).getFactoryName());
			for(int j=0;j<deviceFactoryList.size();j++){
				values[i] += deviceFactoryList.get(j).getFactories()[i];
			}
			factoryDto.setValue(values[i]);
			if(values[i]!=0)
				this.factoryJsonList.add(factoryDto);
		}
		
		return SUCCESS;
	}
	/**
	 * 查询设备监测情况（按照每日统计严重级别）
	 * @return success
	 * @throws Exception
	 */
	public String queryDaySeriousLevel(){
		this.logger.debug("查询设备监测情况（按照每日统计严重级别）: getDaySeriousLevel() 开始" );
		PSysUser pSysUser = new PSysUser();
		pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		try {
			this.setDmWindowList(this.deviceMonitorManager.queryDaySeriousLevel(pSysUser, orgNo, levelNo,monitorStartDate, monitorEndDate, start, limit));
			List<AEventStatDWindowDto> windowList = new ArrayList<AEventStatDWindowDto>();
			for(AEventStatDWindowDto windowDto :dmWindowList){
				windowDto.setLevelNo(levelNo);
				windowDto.setOrgNo(orgNo);
				windowDto.setOrgName(orgName);
				windowList.add(windowDto);
			}
			this.setDmWindowList(windowList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.logger.debug("查询设备监测情况（按照每日统计严重级别）: getDaySeriousLevel() 结束 ");
		return SUCCESS;
	}
	
	/**
	 * 查询所有生产厂家
	 * @return
	 * @throws Exception
	 */
	public String queryDeviceFactory() throws Exception{
		this.vwFactoryList = this.deviceMonitorManager.getAllFactory();
		if(vwFactoryList!=null) this.logger.debug("vwFactory-------"+vwFactoryList.size());
		return SUCCESS;
	}
	
	/**
	 * 查询终端告警事件
	 * @return
	 * @throws Exception
	 */
	public String getDeviceExcepInfo() throws Exception {
		this.deviceExcepList = new ArrayList<DeviceExcepDto>();
		this.deviceExcepList = this.deviceMonitorManager.queryDeviceExcepInfo(orgNo, excepStartDate, excepEndDate, factoryCode, queryType);
		return SUCCESS;
	}
	
	public String getMonitorStartDate() {
		return monitorStartDate;
	}
	public void setMonitorStartDate(String monitorStartDate) {
		this.monitorStartDate = monitorStartDate;
	}
	public String getMonitorEndDate() {
		return monitorEndDate;
	}
	public void setMonitorEndDate(String monitorEndDate) {
		this.monitorEndDate = monitorEndDate;
	}
	public List<DeviceMonitorDto> getSerisLevelList() {
		return serisLevelList;
	}
	public void setSerisLevelList(List<DeviceMonitorDto> serisLevelList) {
		this.serisLevelList = serisLevelList;
	}
	public List<DeviceMonitorDto> getDeviceFactoryList() {
		return deviceFactoryList;
	}
	public void setDeviceFactoryList(List<DeviceMonitorDto> deviceFactoryList) {
		this.deviceFactoryList = deviceFactoryList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<DeviceMonitorDto> getSerisJsonList() {
		return serisJsonList;
	}
	public void setSerisJsonList(List<DeviceMonitorDto> serisJsonList) {
		this.serisJsonList = serisJsonList;
	}
	public List<DeviceExcepDto> getDeviceExcepList() {
		return deviceExcepList;
	}
	public void setDeviceExcepList(List<DeviceExcepDto> deviceExcepList) {
		this.deviceExcepList = deviceExcepList;
	}
	public List<DeviceMonitorDto> getFactoryJsonList() {
		return factoryJsonList;
	}
	public void setFactoryJsonList(List<DeviceMonitorDto> factoryJsonList) {
		this.factoryJsonList = factoryJsonList;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getFactoryCode() {
		return factoryCode;
	}
	public void setFactoryCode(String factoryCode) {
		this.factoryCode = factoryCode;
	}
	public List<VwTmnlFactory> getVwFactoryList() {
		return vwFactoryList;
	}
	public void setVwFactoryList(List<VwTmnlFactory> vwFactoryList) {
		this.vwFactoryList = vwFactoryList;
	}
	public String getExcepStartDate() {
		return excepStartDate;
	}
	public void setExcepStartDate(String excepStartDate) {
		this.excepStartDate = excepStartDate;
	}
	public String getExcepEndDate() {
		return excepEndDate;
	}
	public void setExcepEndDate(String excepEndDate) {
		this.excepEndDate = excepEndDate;
	}
	/**
	 *  the dmWindowList
	 * @return the dmWindowList
	 */
	public List<AEventStatDWindowDto> getDmWindowList() {
		return dmWindowList;
	}
	/**
	 *  the dmWindowList to set
	 * @param dmWindowList the dmWindowList to set
	 */
	public void setDmWindowList(List<AEventStatDWindowDto> dmWindowList) {
		this.dmWindowList = dmWindowList;
	}
	/**
	 *  the start
	 * @return the start
	 */
	public long getStart() {
		return start;
	}
	/**
	 *  the start to set
	 * @param start the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}
	/**
	 *  the limit
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 *  the limit to set
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	/**
	 *  the levelNo
	 * @return the levelNo
	 */
	public String getLevelNo() {
		return levelNo;
	}
	/**
	 *  the levelNo to set
	 * @param levelNo the levelNo to set
	 */
	public void setLevelNo(String levelNo) {
		this.levelNo = levelNo;
	}
	/**
	 *  the orgName
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 *  the orgName to set
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
