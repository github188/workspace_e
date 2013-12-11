package com.nari.qrystat.colldataanalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.util.DateUtil;

/**
 * 行业用电趋势分析Action
 * @author 姜炜超
 */
public class TradeTrendlineAction extends BaseAction {
	//日志
	private static final Logger logger = Logger.getLogger(TradeTrendlineAction.class);

	//行业趋势业务对象
	private TradeTrendlineManager tradeTrendlineManager;
	
	// 请求返回列表
	private List<TradeTrendlineDto> tradeTrendlineList;//行业趋势列表
	private List<TradeTrendlineBean> tradeTrendlineChartList;//行业趋势曲线图数据列表
	private List<TradeTrendlineDataBean> tradeTimeModelList = new ArrayList<TradeTrendlineDataBean>();//时间列表
	
	// 前台参数
	private boolean success = true;
	
	private Date startDate;//查询日期
	private Date endDate;//对比日期
	private int rg;//判断，1表示分日，2表示分月
	private int interval;//间隔
	private List<String>  tradeList; //行业查询列表

	/**
	 * 查询行业用电趋势分析
	 * @return
	 * @throws Exception
	 */
	public String queryTradeTrendline() throws Exception {
		logger.debug("行业用电趋势分析开始");
		
		PSysUser pSysUser = getPSysUser();//从session中获取操作员信息
		
		//如果参数为空，返回页面
		if(null == pSysUser || null == tradeList || 0 == tradeList.size()){
			return SUCCESS;
		}
		
		//根据页面返回数据进行解析，然后放入列表中，传递给业务层
		List<TradeTrendlineDto> list = new ArrayList<TradeTrendlineDto>();
		for (int i = 0; i < this.tradeList.size(); i++) {
			String[] trades = this.tradeList.get(i).split("!");
			if(null == trades || trades.length < 2 || null == trades[0] ||null == trades[1] || "".equals(trades[0])||"".equals(trades[1])){
				continue;
			}
			TradeTrendlineDto dto = new TradeTrendlineDto();
			dto.setTradeNo(trades[0]);
			dto.setTradeName(trades[1]);
			list.add(dto);
		}
		try{
			tradeTrendlineList = tradeTrendlineManager.findTradeTrendline4Grid(pSysUser, list, rg, interval, startDate, endDate);
			tradeTrendlineChartList = tradeTrendlineManager.findTradeTrendline(pSysUser, list, rg, interval, startDate, endDate);
			getTimeModel(rg,interval,startDate, endDate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.debug("行业用电趋势分析结束");
		return SUCCESS;
	}

	/**
	 * 生成时间模板
	 * @return
	 * @throws Exception
	 */
	private void getTimeModel(int rg, int interval, Date startDate, Date endDate){
		TradeTrendlineDataBean bean  = null;
		for(int i = 0; i <= interval; i++){
			bean = new TradeTrendlineDataBean();
			if(1 == rg){
		        bean.setTime(DateUtil.afterDate(startDate, i).substring(5));
			}else{
				bean.setTime(DateUtil.afterMonth(startDate, i));
			}
		    tradeTimeModelList.add(bean);
		}
	}
	
	public void setTradeTrendlineManager(
			TradeTrendlineManager tradeTrendlineManager) {
		this.tradeTrendlineManager = tradeTrendlineManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<TradeTrendlineDto> getTradeTrendlineList() {
		return tradeTrendlineList;
	}

	public void setTradeTrendlineList(List<TradeTrendlineDto> tradeTrendlineList) {
		this.tradeTrendlineList = tradeTrendlineList;
	}

	public List<TradeTrendlineBean> getTradeTrendlineChartList() {
		return tradeTrendlineChartList;
	}
	
	public void setTradeTrendlineChartList(
			List<TradeTrendlineBean> tradeTrendlineChartList) {
		this.tradeTrendlineChartList = tradeTrendlineChartList;
	}

	public List<String> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<String> tradeList) {
		this.tradeList = tradeList;
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

	public List<TradeTrendlineDataBean> getTradeTimeModelList() {
		return tradeTimeModelList;
	}

	public void setTradeTimeModelList(
			List<TradeTrendlineDataBean> tradeTimeModelList) {
		this.tradeTimeModelList = tradeTimeModelList;
	}

	public int getRg() {
		return rg;
	}

	public void setRg(int rg) {
		this.rg = rg;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
}
