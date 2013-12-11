package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.CmeterJdbc;

/**
 * 服务接口 iArchiveRcpManManager
 * 
 * @author zhangzhw
 * @describe 档案管理采集点管理接口
 * 
 */
public interface IArchiveCmeterManManager {

	/**
	 * 方法　queryCmeterByConsNo
	 * 
	 * @param consNo
	 * @return　通过　consNo 查询　Cmeter 列表
	 * @throws Exception
	 */
	public List<CmeterJdbc> queryCmeterByConsNo(String consNo) throws Exception;

	/**
	 * 方法　saveCmeter
	 * 
	 * @param cmeterJdbc
	 * @return　保存　Cmeter 列表
	 * @throws Exception
	 */
	public boolean saveCmeter(CmeterJdbc[] cmeterJdbc) throws Exception;

}
