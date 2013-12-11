package com.nari.runman.runstatusman;

import java.util.List;
import com.nari.statreport.ATmnlFlowMBean;
import com.nari.statreport.ATmnlFlowMBeanH;
import com.nari.statreport.SimFee;
import com.nari.statreport.TmnlFactory;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ATmnlFlowMBeanManager {

	/**
	 * 查询终端月通信流量
	 * 
	 * @return 终端月通信流量列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<ATmnlFlowMBean> findChannelMonitor(String sDate, long start,
			int limit) throws DBAccessException;

	/**
	 * 查询流量超用明细表
	 * 
	 * @return 终端流量超用明细表-超用
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public Page<ATmnlFlowMBeanH> findChannelH(String sDate, String orgName,String orgType,
			String over, String manufacture, long start, int limit)
			throws DBAccessException;
	
	/**
	 * 查询流量超用明细表
	 * 
	 * @return 资费标准
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<SimFee> findSimFee() throws DBAccessException;
	
	/**
	 * 查询流量超用明细表
	 * 
	 * @return 查询生产厂家
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<TmnlFactory> findTmnlFactory()throws DBAccessException;
}
