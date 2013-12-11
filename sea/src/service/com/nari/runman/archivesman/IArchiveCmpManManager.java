package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.CmpJdbc;

/**
 * 服务接口 iArchiveRcpManManager
 * 
 * @author zhangzhw
 * @describe 档案管理采集点管理接口
 * 
 */
public interface IArchiveCmpManManager {

	/**
	 * 方法 queryCmpByConsNo
	 * 
	 * @param consNo
	 * @return 通过 consNo查询Cmp列表
	 * @throws Exception
	 */
	public List<CmpJdbc> queryCmpByConsNo(String consNo) throws Exception;

	/**
	 * 方法 saveCmp
	 * 
	 * @param cmpJdbc
	 * @return 保存Cmp列表
	 * @throws Exception
	 */
	public boolean saveCmp(CmpJdbc[] cmpJdbc) throws Exception;

}
