package com.nari.mydesk;

import java.util.List;

public interface IMyDeskManager {

	/**
	 * @desc 地区用电大户排名
	 * @param staffNo
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<ElecOrderDTO> getElecOrder(String staff_no,String date) throws Exception;
	
	/**
	 * @desc 通信流量
	 * @return
	 * @throws Exception
	 */
	public List<CommFlowDTO> getCommFlow(String staffNo) throws Exception;
	
	/**
	 * @desc 读取配置文件，获取 portlet
	 * @return
	 * @throws Exception
	 */
	public List<Model> getAllModel() throws Exception;
}
