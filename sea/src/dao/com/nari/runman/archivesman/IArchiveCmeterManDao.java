package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.CmeterJdbc;

/**
 * DAO 接口 IArchiveRcpManDao
 * 
 * @author zhangzhw
 * @describe 采集点DAO接口
 */
public interface IArchiveCmeterManDao {

	/**
	 * 方法　queryCmeterByConsNo
	 * 
	 * @param consNo
	 * @return　通过　consNo 查询　Cmeter 列表
	 */
	public List<CmeterJdbc> queryCmeterByConsNo(String consNo);

	/**
	 * 方法　saveCmeter
	 * 
	 * @param cmeterJdbc
	 * @return　保存　Cmeter 列表
	 */
	public boolean saveCmeter(CmeterJdbc[] cmeterJdbc);

}
