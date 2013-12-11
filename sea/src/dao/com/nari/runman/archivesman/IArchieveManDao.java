package com.nari.runman.archivesman;

import com.nari.customer.CCons;

/**
 * 接口 IArchieveManDao
 * 
 * @author zhangzhw
 * @describe 档案管理接口
 */
public interface IArchieveManDao {

	/**
	 * 方法 findCConsById
	 * 
	 * @param nodeId
	 * @return 通过consId查询CCons信息
	 */
	public CCons findCConsById(String nodeId);
	
	/**
	 * 方法 findCConsByNo
	 * 
	 * @param consNo
	 * @return 通过consNo查询CCons信息
	 */
	public CCons findCConsByNo(String consNo);

	/**
	 * 方法 saveOrUpdate
	 * 
	 * @param ccons
	 * @return 保存Ccons信息并返回
	 */
	public CCons saveOrUpdate(CCons ccons);
	
	public long querySequece(String seqName);
	
}
