/**
 * 创建人：姜海辉
 * 类描述：用户余额查询Action层
 */
package com.nari.baseapp.prepaidman;

import java.util.ArrayList;
import java.util.List;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.support.Page;
import com.nari.coherence.TaskDeal;

public class UseValueQueryAction extends BaseAction{
	
	//返回值
	private boolean success = true;
	private String msg;
    private Integer taskSecond=30;      //任务执行时间
    private String orgNo;       		//供电单位编号
	private String orgName;       		//供电单位名称
	private String consNo;        		//用户编号
	private String appNo;         		//购电单号
	private String terminalAddr;  		 //终端地址
	private String startDate;       	//开始日期
	private String endDate;       		 //结束日期
	private String tmnlAssetNo;   		//终端资产号
	private Short totalNo;				//总加组号
	private List<String> fetchTmnlTotalList;  //召测终端列表
	private List<UseValueResult> fetchResultList; //召测结果
	private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private List<UseValueResult> useValueResultList; 
	
	private IUseValueQueryManager iUseValueQueryManager;
	
	/**
	 * 查询剩余值（按照总加组）
	 * @return
	 * @throws Exception
	 */
	public String useValueQuery()throws Exception{
		if((null==this.startDate||"".equals(this.startDate))
			&&(null==this.endDate||"".equals(this.endDate))){
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<UseValueResult> useValueResultPage = this.iUseValueQueryManager
					.valueQuery(this.orgNo, this.consNo, this.appNo,
							this.terminalAddr, this.startDate, this.endDate,
							this.start, this.limit);
			this.useValueResultList=useValueResultPage.getResult();
			this.totalCount=useValueResultPage.getTotalCount();
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
		
	/**
	 * 查询剩余值明细
	 * @return
	 * @throws Exception
	 */
	public String useValueDetailQuery()throws Exception{
		if( null==this.tmnlAssetNo||"".equals(this.tmnlAssetNo)
		    ||null==this.totalNo||
			null==this.startDate||"".equals(this.startDate)
			||null==this.endDate||"".equals(this.endDate)){
			this.success=false;
			return SUCCESS;
		}
		try{
			Page<UseValueResult> useValueResultPage = this.iUseValueQueryManager
					.valueDetailQuery(this.tmnlAssetNo, this.totalNo,this.orgNo, this.consNo, this.appNo,
							this.terminalAddr, this.startDate, this.endDate,
							this.start, this.limit);
			this.useValueResultList=useValueResultPage.getResult();
			this.totalCount=useValueResultPage.getTotalCount();
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 召测剩余值，下发前置，一类数据召测
	 * @return String
	 * @throws Exception 
	 */
	public String fetchUseValue() throws Exception {
		List<UseValueResult> tmnlList= parseFetchTmnlList();
		if(null==tmnlList||0==tmnlList.size()){
			this.success=false;
			return SUCCESS;
		}	
		try{
			if(!TaskDeal.isCacheRunning()){
				this.msg="前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				this.msg="前置集群服务中断";
				return SUCCESS;
			}
			this.fetchResultList=this.iUseValueQueryManager.fetchUseValue(tmnlList, this.taskSecond);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}
	
	/**
	 * 召测剩余值（全选），下发前置，一类数据召测
	 * @return String
	 * @throws Exception 
	 *//*
	public String fetchAllUseValue() throws Exception {
		if((null==this.orgName||"".equals(this.orgName))
			&&(null==this.consNo||"".equals(this.consNo))
			&&(null==this.appNo||"".equals(this.appNo))
			&&(null==this.terminalAddr||"".equals(this.terminalAddr))
			&&(null==this.startDate||"".equals(this.startDate))
			&&(null==this.endDate||"".equals(this.endDate))){
			this.success=false;
			return SUCCESS;
		}
		try{
			if(!TaskDeal.isCacheRunning()){
				this.msg="前置集群通信中断";
				return SUCCESS;
			}
			if(!TaskDeal.isFrontAlive()){
				this.msg="前置集群服务中断";
				return SUCCESS;
			}
			this.iUseValueQueryManager.fetchAllUseValue(this.orgName, this.consNo, this.appNo,
					this.terminalAddr, this.startDate, this.endDate, this.taskSecond);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
	/**
	 * 提取页面选择的终端（用于召测）
	 */
	public List<UseValueResult> parseFetchTmnlList() {
		if(null==this.fetchTmnlTotalList||0==this.fetchTmnlTotalList.size())
			return null;
		List<UseValueResult> tmnlList = new ArrayList<UseValueResult>();
		for (int i = 0; i < this.fetchTmnlTotalList.size(); i++) {
			String[] tmnl = this.fetchTmnlTotalList.get(i).split("`");
			if(null==tmnl[5]||"".equals(tmnl[5])||"null".equals(tmnl[5])
					||null==tmnl[5]||"".equals(tmnl[6])||"null".equals(tmnl[6])){
				continue;
			}
			UseValueResult u =new UseValueResult();
			u.setOrgNo(tmnl[0]);
			u.setConsNo(tmnl[1]);
			u.setAppNo(tmnl[2]);
			u.setTerminalId(tmnl[3]);
			if(null!=tmnl[4]&&!"null".equals(tmnl[4])&&!"".equals(tmnl[4]))
				u.setMeterId(tmnl[4]);
			u.setTmnlAssetNo(tmnl[5]);
			u.setTotalNo(Short.valueOf(tmnl[6]));
			tmnlList.add(u);
		}
		return tmnlList;
	}
	
	public void setiUseValueQueryManager(IUseValueQueryManager iUseValueQueryManager) {
		this.iUseValueQueryManager = iUseValueQueryManager;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getTerminalAddr() {
		return terminalAddr;
	}
	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<UseValueResult> getUseValueResultList() {
		return useValueResultList;
	}
	public void setUseValueResultList(List<UseValueResult> useValueResultList) {
		this.useValueResultList = useValueResultList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<String> getFetchTmnlTotalList() {
		return fetchTmnlTotalList;
	}

	public void setFetchTmnlTotalList(List<String> fetchTmnlTotalList) {
		this.fetchTmnlTotalList = fetchTmnlTotalList;
	}

	public List<UseValueResult> getFetchResultList() {
		return fetchResultList;
	}
	public void setFetchResultList(List<UseValueResult> fetchResultList) {
		this.fetchResultList = fetchResultList;
	}
	public Integer getTaskSecond() {
		return taskSecond;
	}
	public void setTaskSecond(Integer taskSecond) {
		this.taskSecond = taskSecond;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public Short getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(Short totalNo) {
		this.totalNo = totalNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
}
