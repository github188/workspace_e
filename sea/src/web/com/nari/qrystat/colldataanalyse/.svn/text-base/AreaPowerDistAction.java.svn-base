package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DateUtil;

/**
 * 地区电量分布Action
 * @author 姜炜超
 */
public class AreaPowerDistAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(AreaPowerDistAction.class);
	
	//自动注入地区电量分布业务层 
	private AreaPowerDistManager areaPowerDistManager;
	
	public void setAreaPowerDistManager(AreaPowerDistManager areaPowerDistManager) {
		this.areaPowerDistManager = areaPowerDistManager;
	}

	//成功返回标记
	public boolean success = true;

	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private Date compareDate;//对比起始日期
	private List<AreaPowerDistDto> apdList;//地区电量分布信息列表
	

	/**
	 * 查询地区电量分布
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("查询地区电量分布开始");
    	
    	PSysUser pSysUser = getPSysUser();//从session中获取操作员信息
    	//如果参数为空，返回页面
    	if(null == pSysUser || null == startDate || null == endDate || null == compareDate){
    		return SUCCESS;
    	}
    	
    	Page<AreaPowerDistDto> psc = null;
    	
    	psc = areaPowerDistManager.findAPDInfoByCond(pSysUser, startDate, endDate, 
    			compareDate, start, limit);

    	apdList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询地区电量分布结束");
    	return SUCCESS;
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


	public List<AreaPowerDistDto> getApdList() {
		return apdList;
	}


	public void setApdList(List<AreaPowerDistDto> apdList) {
		this.apdList = apdList;
	}

	public Date getCompareDate() {
		return compareDate;
	}

	public void setCompareDate(Date compareDate) {
		this.compareDate = compareDate;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
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
}
