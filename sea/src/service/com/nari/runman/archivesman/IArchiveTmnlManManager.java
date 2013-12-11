package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;

/**
 * 接口
 * 
 * @author zhangzhw
 * 
 */
public interface IArchiveTmnlManManager {

	/**
	 * 方法 queryTmnlByConsNo
	 * 
	 * @param consNo
	 * @return 通过consNo查询关联终端列表
	 * @throws Exception
	 */
	public List<RtmnlRunJdbc> queryTmnlByConsNo(String consNo) throws Exception;

	/**
	 * 方法 saveTmnls
	 * 
	 * @param rtmnlRunJdbc
	 * @return 保存终端列表
	 * @throws Exception
	 */
	public boolean saveTmnls(String consNo,RtmnlRunJdbc[] rtmnlRunJdbc,RcpParaJdbc[] rcpParaJdbc) throws Exception;

	/**
	 * 根据终端地址信息查询终端详细参数信息
	 * @param tmnladdrs 终端地址
	 * @return
	 */
	public List<RcpParaJdbc> queryTmnlParams(String tmnladdrs);
	
	/**
	 * 删除终端信息
	 * @param tmnladdr 要进行删除的终端地址
	 * @return
	 * @throws Exception
	 */
	public boolean deleteTmnl(String tmnladdr) throws Exception;
}
