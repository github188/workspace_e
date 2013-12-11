package com.nari.runman.runstatusman;

import java.util.List;

import com.nari.statreport.ATmnlFlowMBean;
import com.nari.statreport.ATmnlFlowMBeanH;
import com.nari.statreport.SimFee;
import com.nari.statreport.TmnlFactory;
import com.nari.support.Page;

public interface ATmnlFlowMBeanDao {
	/**
	 * @param sDate年月参数
	 * @return 终端月通信流量列表
	 */
	public Page<ATmnlFlowMBean> findChannelMonitor(String sDate, long start,
			int limit);

	/**
	 * @param sDate 查询时间
	 * @param orgName 节点名称
	 * @param over 超用
	 * @param makeFactory 制造厂商
	 * @return 流量超用明细表列表-------超用
	 */
	public Page<ATmnlFlowMBeanH> findChannelH(String sDate, String orgName,String orgType,
			String manufacture, long start, int limit);

	/**
	 * @param sDate 查询时间
	 * @param orgName 节点名称
	 * @param over 超用
	 * @param makeFactory 制造厂商
	 * @return 流量超用明细表列表-------全部
	 */
	public Page<ATmnlFlowMBeanH> findChannelHH(String sDate, String orgName,String orgType,
			String manufacture, long start, int limit);
	
	
	/**
	 * @param
	 * @return 资费标准
	 */
	public List<SimFee> findSimFee();
	
	/**
	 * @param
	 * @return 制造厂商
	 */
	public List<TmnlFactory> findTmnlFactory();
	
}
