package com.nari.advapp.vipconsman;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DateUtil;

/**
 * 重点户排名分析查询Action
 * @author 姜炜超
 */
public class VipConsSortAnalyseAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(VipConsSortAnalyseAction.class);
	
	//自动注入重点户排名分析业务层 
	private VipConsSortAnalyseManager vipConsSortAnalyseManager;
	
	//成功返回标记
	public boolean success = true;

	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private int flag;//1最大负荷，2最小负荷，3最大电能量，4最小电能量
	private List<VipConsSortAnalyseBean> sortList;//用电排名
	private int MAX = 1;
	private int MIN = 2;
	
	/**
	 * 查询重点户排名分析
	 * @return String 
	 * @throws Exception
	 */
    public String queryGridData() throws Exception{
    	logger.debug("查询重点户排名分析开始");
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
		String startDate = DateUtil.dateToString(this.startDate);
		String endDate = DateUtil.dateToString(this.endDate);
    	
    	Page<VipConsSortAnalyseBean> psc = null;
    	    	
    	if(1 == flag){
    	    psc = vipConsSortAnalyseManager.findVipConsLoadSort(pSysUser, startDate, endDate, MAX, start, limit);
    	}else if(2 == flag){
    		psc = vipConsSortAnalyseManager.findVipConsLoadSort(pSysUser, startDate, endDate, MIN, start, limit);
    	}else if(3 == flag){
    		psc = vipConsSortAnalyseManager.findVipConsPowerSort(pSysUser, startDate, endDate, MAX, start, limit);
        }else if(4 == flag){
    		psc = vipConsSortAnalyseManager.findVipConsPowerSort(pSysUser, startDate, endDate, MIN, start, limit);
    	}else{
    		return SUCCESS;
    	}
    	sortList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询重点户排名分析结束");
    	return SUCCESS;
    }
    
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public void setVipConsSortAnalyseManager(
			VipConsSortAnalyseManager vipConsSortAnalyseManager) {
		this.vipConsSortAnalyseManager = vipConsSortAnalyseManager;
	}

	public List<VipConsSortAnalyseBean> getSortList() {
		return sortList;
	}

	public void setSortList(List<VipConsSortAnalyseBean> sortList) {
		this.sortList = sortList;
	}	
}
