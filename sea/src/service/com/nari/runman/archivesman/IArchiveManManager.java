package com.nari.runman.archivesman;

import com.nari.customer.CCons;

/**
 * 接口 IArchiveManManager
 * 
 * @author zhangzhw
 * @describe 档案管理服务接口
 */
public interface IArchiveManManager {

	/**
	 * 方法 queryArchive
	 * 
	 * @param nodeId
	 * @return 通过用户节点名字查询用户
	 */
	public CCons queryArchive(String nodeId) throws Exception;

	/**
	 * 方法 saveArchive
	 * 
	 * @param ccons
	 * @return 保存用户信息
	 * @throws Exception
	 */
	public CCons saveArchive(CCons ccons) throws Exception;

}
