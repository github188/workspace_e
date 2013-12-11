package com.nari.baseapp.datagathorman;

import java.util.List;
import java.util.Map;

import com.nari.support.Page;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;

public interface DataFetchLoneDao {
	/**
	 * 通过终端资产号内码找到底下直接连接的用户
	 * @param tmnlAssetNo
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	Page<Map> findUnderUser(String tmnlAssetNo, long start, int limit)
			throws DBAccessException;
	/**
	 * 通过终端资产号内码找到底下挂的采集器
	 * @param tmnlAssetNo
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	Page<Map> findFrmUnderTmnl(String tmnlAssetNo, long start, long limit)
			throws DBAccessException;
	/**
	 * 通过终端资产号内码和采集器的id找到底下的测量点
	 * @param tmnlAssetNo
	 * @param frmId
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	Page<Map> findMeterUnderFrm(String tmnlAssetNo, String frmId, long start,
			long limit) throws DBAccessException;
	/**
	 * 通过不同的节点类型和不同的值来查询分页树所需要的数据
	 * @param nodeStr
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	Page<TreeNode> generalPointTree(String nodeStr, long start, int limit)
			throws DBAccessException;
	
	/**
	 * 通过e_data_mp中的id来找到相关的数据
	 * @param id
	 * @return
	 * @throws DBAccessException
	 */
	Map findDataById(String id) throws DBAccessException;
	/**
	 * 统一处理不同的类型的规约项的节点。来生成树
	 * @param nodeStr 节点字符串,通过_连接
	 * @return
	 * @throws DBAccessException
	 */
	List<TreeNode> dealClearTree(String nodeStr) throws DBAccessException;
	/**
	 * 通过不同的节点来生成不同的下级节点,向外部暴露的处理不同类型树的方法 
	 * @param nodeStr 传入的节点id
	 * @return
	 * @throws DBAccessException
	 */
	List<TreeNode> createTypeTree(String nodeStr) throws DBAccessException;
	/**
	 * 查找一个终端下用户的总数
	 * @param tmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	Long findCoutUser(String tmnlAssetNo) throws DBAccessException;
	
}
