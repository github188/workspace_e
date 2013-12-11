package com.nari.baseapp.datagathorman;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.datagatherman.DataPublishManDto;
import com.nari.baseapp.datagatherman.TblDataDto;

/**
 * 数据发布管理 Action
 * @author 余涛
 *
 */
public class DataPublishAction extends BaseAction{

	private Logger logger = Logger.getLogger(this.getClass());
	
	/* 自动注入业务层 */
	private DataPublishManager dataPublishManager;
	public void setDataPublishManager(DataPublishManager dataPublishManager) {
		this.dataPublishManager = dataPublishManager;
	}

	private boolean success = true;
	
	private String test;
	private String queryType;
	private String dataPublishStartDate;
	private String dataPublishEndDate;
	private List<DataPublishManDto> dataPublishInfoList;
	
	private String orgNo;
	private String qtQueryType;
	private String qtStartDate;
	private String qtEndDate;
	private List<TblDataDto> tblDataList;
	
	public String test() throws Exception {
		this.logger.debug("test-----start");
		this.test = this.dataPublishManager.test("success");
		this.logger.debug("test-----"+this.test);
		return SUCCESS;
	}
	
	/**
	 * 获取数据发布的突变数据和零数据
	 * @return
	 * @throws Exception
	 */
	public String getDataPublishInfo() throws Exception {
		this.logger.debug("获取数据发布的突变数据和零数据");
		this.dataPublishInfoList = new ArrayList<DataPublishManDto>();
		dataPublishInfoList = this.dataPublishManager.queryDataPublishInfo("", queryType, dataPublishStartDate, dataPublishEndDate);
		return SUCCESS;
	}
	
	/**
	 * 查询具体突变数据、零数据
	 * @return
	 * @throws Exception
	 */
	public String getTblDataInfo() throws Exception {
		this.logger.debug("查询具体突变数据、零数据");
		this.tblDataList = new ArrayList<TblDataDto>();
		this.tblDataList = this.dataPublishManager.queryTblDataInfo("", qtQueryType, qtStartDate, qtEndDate);
		return SUCCESS;
	}
	
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getDataPublishStartDate() {
		return dataPublishStartDate;
	}
	public void setDataPublishStartDate(String dataPublishStartDate) {
		this.dataPublishStartDate = dataPublishStartDate;
	}
	public String getDataPublishEndDate() {
		return dataPublishEndDate;
	}
	public void setDataPublishEndDate(String dataPublishEndDate) {
		this.dataPublishEndDate = dataPublishEndDate;
	}
	public List<DataPublishManDto> getDataPublishInfoList() {
		return dataPublishInfoList;
	}
	public void setDataPublishInfoList(List<DataPublishManDto> dataPublishInfoList) {
		this.dataPublishInfoList = dataPublishInfoList;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getQtQueryType() {
		return qtQueryType;
	}
	public void setQtQueryType(String qtQueryType) {
		this.qtQueryType = qtQueryType;
	}
	public String getQtStartDate() {
		return qtStartDate;
	}
	public void setQtStartDate(String qtStartDate) {
		this.qtStartDate = qtStartDate;
	}
	public String getQtEndDate() {
		return qtEndDate;
	}
	public void setQtEndDate(String qtEndDate) {
		this.qtEndDate = qtEndDate;
	}
	public List<TblDataDto> getTblDataList() {
		return tblDataList;
	}
	public void setTblDataList(List<TblDataDto> tblDataList) {
		this.tblDataList = tblDataList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
