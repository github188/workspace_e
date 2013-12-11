package com.nari.runman.runstatusman;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.orderlypower.EnergyControlDto;
import com.nari.statreport.ATmnlFlowMBeanH;
import com.nari.statreport.SimFee;
import com.nari.statreport.TmnlFactory;
import com.nari.support.Page;


public class ATmnlFlowMBeanHAction extends BaseAction {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private ATmnlFlowMBeanManager aTmnlFlowMBeanManager;
	

	//请求返回列表
	private List<ATmnlFlowMBeanH> aTmnlFlowMBeanHList;
	private List<TmnlFactory> tmnlFactoryList;
	private boolean success = true;
	private long totalCount;
	
	private SimFee simFee;
	private TmnlFactory tmnlFactory;

	//前台参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	
	//得到流量超用明细表参数
	public Date sDay;
	public String orgName;
	public String orgType;
	public String over;
	public String manufacture;
	private String nodeType;    //节点类型
	private String groupNo;//组号
	private String subsId;//变电站标识
	private String lineId;//线路编号

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Date getsDay() {
		return sDay;
	}

	public void setsDay(Date sDay) {
		this.sDay = sDay;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOver() {
		return over;
	}

	public void setOver(String over) {
		this.over = over;
	}

	public String getManufacture() {
		return manufacture;
	}

	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public ATmnlFlowMBeanManager getaTmnlFlowMBeanManager() {
		return aTmnlFlowMBeanManager;
	}

	public void setaTmnlFlowMBeanManager(ATmnlFlowMBeanManager aTmnlFlowMBeanManager) {
		this.aTmnlFlowMBeanManager = aTmnlFlowMBeanManager;
	}

	public List<ATmnlFlowMBeanH> getaTmnlFlowMBeanHList() {
		return aTmnlFlowMBeanHList;
	}

	public void setaTmnlFlowMBeanHList(List<ATmnlFlowMBeanH> aTmnlFlowMBeanHList) {
		this.aTmnlFlowMBeanHList = aTmnlFlowMBeanHList;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public SimFee getSimFee() {
		return simFee;
	}

	public void setSimFee(SimFee simFee) {
		this.simFee = simFee;
	}
	
	public void setTmnlFactory(TmnlFactory tmnlFactory) {
		this.tmnlFactory = tmnlFactory;
	}

	public TmnlFactory getTmnlFactory() {
		return tmnlFactory;
	}
	
	public void setTmnlFactoryList(List<TmnlFactory> tmnlFactoryList) {
		this.tmnlFactoryList = tmnlFactoryList;
	}

	public List<TmnlFactory> getTmnlFactoryList() {
		return tmnlFactoryList;
	}
	
	
	/**
	 * 得到流量超用明细表-超用or全部
	 * @return 
	 * @throws Exception
	 */
	public String queryChannelH() throws Exception {
		
		if("".equals(sDay)||sDay==null){
			sDay=new Date();
		}
		
		try{
			String sDate = new SimpleDateFormat("yyyy-MM").format(sDay);
			System.out.println("orgtype====:"+orgType);
			Page<ATmnlFlowMBeanH> channelPage = aTmnlFlowMBeanManager.findChannelH(sDate,orgName,orgType,over,manufacture,start,limit);
			aTmnlFlowMBeanHList = channelPage.getResult();
			totalCount  = channelPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "success";
	}
	
	/**
	 * 得到资费标准
	 * @return 
	 * @throws Exception
	 */
	public String querySimFee() throws Exception{
		
		try{
			List<SimFee> simFeeList = aTmnlFlowMBeanManager.findSimFee();
			if(simFeeList.size()>0){
				simFee = simFeeList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "success";
	}

	/**
	 * 得到生产厂家
	 * @return 
	 * @throws Exception
	 */
	public String queryTmnlFactory() throws Exception { 	//添加"全部,00"

		try {
			tmnlFactoryList = new ArrayList<TmnlFactory>();
			TmnlFactory factory = new TmnlFactory();
			factory.setFactoryCode("00");
			factory.setFactoryName("全部");
			tmnlFactoryList.add(factory);
			tmnlFactoryList.addAll(aTmnlFlowMBeanManager.findTmnlFactory());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "success";
	}
	
	/**
	 * 左边树-用户????????
	 * @return String 
	 * @throws Exception
	 */
//    public String loadGridData() throws Exception{
//    	logger.debug("??????");
//    	if(null == nodeType || "".equals(nodeType)){
//    		return ERROR;
//    	}
//    	
//        Page<ATmnlFlowMBeanH> psc = null;
//    	
//    	if("org".equals(nodeType)){
//    		psc = aTmnlFlowMBeanManager.findChannelH(sDate, orgName, orgType, over, manufacture, start, limit);
//    	}
//    	else if("usr".equals(nodeType)){
//    		psc = aTmnlFlowMBeanManager.queryEnergyCtrlGridByConsNo(this.consNo,this.start,this.limit);
//    	}
//        else if("line".equals(nodeType)){
//        	psc = aTmnlFlowMBeanManager.queryEnergyCtrlGridByLineId(this.lineId,this.start,this.limit);
//    	}
//        else if("cgp".equals(nodeType) || "ugp".equals(nodeType)){
//        	psc = aTmnlFlowMBeanManager.queryEnergyCtrlGridByGroupNo(this.nodeType,this.groupNo,this.start,this.limit);
//    	}
//        else if("sub".equals(nodeType)){
//        	psc = aTmnlFlowMBeanManager.queryEnergyCtrlGridBySubsId(this.subsId,this.start,this.limit);
//    	}
//        else if("schemeId".equals(nodeType)){
//        	Long schemeNo = 0L;
//        	if(null != this.schemeId && !"".equals(this.schemeId)){
//        		schemeNo = Long.valueOf(this.schemeId);
//        	}
//        	psc = aTmnlFlowMBeanManager.queryEnergyCtrlGridBySchemeNo(schemeNo,this.start,this.limit);
//    	}
//        else {
//        	return SUCCESS;
//        }
//    	if(null!=psc){
//	    	userGridList = psc.getResult();
//	    	totalCount = psc.getTotalCount();
//    	}
//    	logger.debug("XXXXXXXXXXXXX");
//    	return SUCCESS;
//    }
	
}