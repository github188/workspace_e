package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 台区用电损耗查询Action
 * @author 姜炜超
 */
public class LosePowerStatAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(LosePowerStatAction.class);
	
	//自动注入营业报停控业务层 
	private LosePowerStatManager losePowerStatManager;
	
	public void setLosePowerStatManager(LosePowerStatManager losePowerStatManager) {
		this.losePowerStatManager = losePowerStatManager;
	}
	//成功返回标记
	public boolean success = true;
	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	private String nodeId;//节点id
	private String nodeType;//节点类型
	private String orgType;//单位类型
	private String orgNo; //单位编码
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String tgId;//台区号
	private Boolean flag;//成功or失败
	private String ioValue;//入口or出口
	private String succFalg;//成功or失败
	
	private List<LosePowerStatDto> lpsList;//台区用电损耗查询数据列表
	private List<LosePowerStatReadBean> lpsReadList;//时间段内台区用户抄表信息列表
	private Boolean checkAll;//是否全选
	private Boolean checkRecall;//是否补召
	private Boolean checkRecalc;//是否重新计算
	private List<String> tmnlAssetNoList;//资产编号列表

	private List<Map> resultList;

	private Integer intTgId;
	
	
	 /**
	 * 加载台区用电损耗查询数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("查询台区用电损耗查询开始");
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	Page<LosePowerStatDto> page = losePowerStatManager.findLPSInfoByCond(nodeId, nodeType, orgType, 
    			startDate, endDate, start, limit, pSysUser);
    	lpsList = page.getResult();
    	totalCount = page.getTotalCount();
    	
    	logger.debug("查询台区用电损耗查询结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载时间段内台区用户抄表信息
	 * @return String 
	 * @throws Exception
	 */
    public String loadReadData() throws Exception{
    	logger.debug("查询时间段内台区用户抄表信息开始");
    	    	    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
		
    	Page<LosePowerStatReadBean> psc = losePowerStatManager.findLPSReadInfo(tgId, startDate, succFalg, 
    			ioValue, endDate, start, limit, pSysUser);

    	lpsReadList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	
    	logger.debug("查询时间段内台区用户抄表信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 执行操作，补召
	 * @return String 
	 * @throws Exception
	 */
    public String exec() throws Exception{
    	if(null == tmnlAssetNoList || 0 == tmnlAssetNoList.size()){
    		return SUCCESS;
    	}
    	logger.debug("补召执行开始");
    	if(checkRecall){
    	    losePowerStatManager.recallData(checkAll, tgId, startDate, flag, 
    			ioValue, endDate, tmnlAssetNoList);//补召
    	}
    	logger.debug("补召执行结束");
    	return SUCCESS;
    }
    
    /**
	 * 执行操作，重新计算
	 * @return String 
	 * @throws Exception
	 */
    public String recalcData() throws Exception {
    	logger.debug("重新计算开始");
    	if(checkRecalc){
    	    this.losePowerStatManager.recalcData(orgNo, startDate, endDate);//重新计算
    	}
    	logger.debug("重新计算结束");
    	return SUCCESS;
    }
    
    /*** 生成chart所要用到的数据 **/
	public String findChartData() {
		try {
			resultList = losePowerStatManager.findChartData(intTgId, startDate,
					endDate);
		} catch (Exception e) {
			logger.error(e.getMessage());

		}
		return SUCCESS;
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

	public List<LosePowerStatDto> getLpsList() {
		return lpsList;
	}

	public void setLpsList(List<LosePowerStatDto> lpsList) {
		this.lpsList = lpsList;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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

	public List<LosePowerStatReadBean> getLpsReadList() {
		return lpsReadList;
	}

	public void setLpsReadList(List<LosePowerStatReadBean> lpsReadList) {
		this.lpsReadList = lpsReadList;
	}

	public String getTgId() {
		return tgId;
	}

	public void setTgId(String tgId) {
		this.tgId = tgId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getIoValue() {
		return ioValue;
	}

	public void setIoValue(String ioValue) {
		this.ioValue = ioValue;
	}

	public Boolean getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(Boolean checkAll) {
		this.checkAll = checkAll;
	}

	public List<String> getTmnlAssetNoList() {
		return tmnlAssetNoList;
	}

	public void setTmnlAssetNoList(List<String> tmnlAssetNoList) {
		this.tmnlAssetNoList = tmnlAssetNoList;
	}

	public Boolean getCheckRecall() {
		return checkRecall;
	}

	public void setCheckRecall(Boolean checkRecall) {
		this.checkRecall = checkRecall;
	}

	public Boolean getCheckRecalc() {
		return checkRecalc;
	}

	public void setCheckRecalc(Boolean checkRecalc) {
		this.checkRecalc = checkRecalc;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public void setIntTgId(Integer intTgId) {
		this.intTgId = intTgId;
	}
	
	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 *  the succFalg
	 * @return the succFalg
	 */
	public String getSuccFalg() {
		return succFalg;
	}

	/**
	 *  the succFalg to set
	 * @param succFalg the succFalg to set
	 */
	public void setSuccFalg(String succFalg) {
		this.succFalg = succFalg;
	}
	
}
