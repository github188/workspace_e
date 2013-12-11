package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;

/**
 * DAO接口 IArchiveTmnlManDao
 * 
 * @author zhangzhw
 * @describe 档案管理终端管理DAO接口
 */
public interface IArchiveTmnlManDao {

	/**
	 * 方法 queryTmnlByConsNo
	 * 
	 * @param consNo
	 * @return 通过consNo查询终端列表
	 */
	public List<RtmnlRunJdbc> queryTmnlByConsNo(String consNo);

	/**
	 * 方法 saveTmnls
	 * 
	 * @param rtmnlRunJdbc
	 * @return 保存终端列表
	 */
	public boolean saveTmnls(String consNo,RtmnlRunJdbc[] rtmnlRunJdbc,RcpParaJdbc[] rcpParaJdbc);

	/**
	 * 根据终端地址查询终端详细参数信息
	 * @param tmnladdrs
	 * @return
	 */
	public List<RcpParaJdbc> queryTmnlParams(String tmnladdrs);
	
	/**
	 * 删除终端信息 
	 * @param tmnladdr 要进行删除的终端地址
	 * @return 成功:TRUE 失败:FALSE
	 * @throws Exception
	 */
	public int deleteTmnl(String tmnladdr) throws Exception;
}
