package com.nari.baseapp.datagathorman.impl;

import java.util.ArrayList;
import java.util.List;

import com.nari.baseapp.datagatherman.DataPublishManDto;
import com.nari.baseapp.datagatherman.TblDataDto;
import com.nari.baseapp.datagathorman.DataPublishJdbcDao;
import com.nari.baseapp.datagathorman.DataPublishManager;

public class DataPublishManagerImpl implements DataPublishManager {

	private DataPublishJdbcDao dataPublishJdbcDao;
	public void setDataPublishJdbcDao(DataPublishJdbcDao dataPublishJdbcDao) {
		this.dataPublishJdbcDao = dataPublishJdbcDao;
	}


	public String test(String s) throws Exception{
		return this.dataPublishJdbcDao.test(s);
	}

	/**
	 * 查询单位的突变数据和零数据
	 * @param orgNo 单位编码
	 * @param queryType 数据类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 */
	public List<DataPublishManDto> queryDataPublishInfo(String orgNo,String queryType,String startDate,String endDate) throws Exception{
		List<DataPublishManDto> list = new ArrayList<DataPublishManDto>();
		DataPublishManDto d1 = new DataPublishManDto();DataPublishManDto d2 = new DataPublishManDto();
		DataPublishManDto d3 = new DataPublishManDto();DataPublishManDto d4 = new DataPublishManDto();
		d1.setOrgName("西宁");d1.setTbData("256");d1.setLiData("574");
		d2.setOrgName("海南");d2.setTbData("201");d2.setLiData("419");
		d3.setOrgName("黄化");d3.setTbData("173");d3.setLiData("452");
		d4.setOrgName("海东");d4.setTbData("354");d4.setLiData("117");
		list.add(d1);list.add(d2);list.add(d3);list.add(d4);
		return list;
	}
	
	/**
	 * 查询突变数据、零数据
	 * @param orgNo 单位编码
	 * @param queryType 数据类型
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 * @throws Exception
	 */
	public List<TblDataDto> queryTblDataInfo(String orgNo,String queryType, String startDate,String endDate) throws Exception{
		TblDataDto t1 = new TblDataDto();TblDataDto t2 = new TblDataDto();
		t1.setConsName("用户1");t1.setOrgName("西宁");t1.setDataValue("11");
		t1.setTerminalAddr("111111");t1.setConsNo("111111");
		t1.setDataTime("2009-10-21");t1.setPublishTime("2009-11-11");
		t1.setRepairTime("2009-12-22");
		t2.setConsName("用户2");t2.setOrgName("西宁");t2.setDataValue("22");
		t2.setTerminalAddr("222222");t2.setConsNo("222222");
		t2.setDataTime("2009-11-21");t2.setPublishTime("2009-12-11");
		t2.setRepairTime("2010-01-02");
		List<TblDataDto> list = new ArrayList<TblDataDto>();
		list.add(t1);list.add(t1);list.add(t2);list.add(t2);
		return list;
	}

}
