package com.nari.sysman.securityman;

import java.util.List;

import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.CpTypeCode;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.util.TreeNode;

/**
 * 接口 IGenralTreeManager
 * 
 * @author zhangzhw
 * @describe 左边树DAO
 */
public interface IGeneralTreeManager {

	/**
	 * 方法 findGridTree
	 * 
	 * @param username
	 *            登录用户信息
	 * @param node
	 * @param start
	 * @param limit
	 * @return 电网树分页节点信息
	 * @throws Exception
	 */
	public Page<TreeNode> findGridTree(PSysUser username, String node,
			long start, int limit) throws Exception;

	/**
	 * 方法 findTradeTree
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 行业树分页节点信息
	 * @throws Exception
	 */
	public Page<TreeNode> findTradeTree(PSysUser username, String node,
			long start, int limit) throws Exception;

	/**
	 * 方法 findAreaTree
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 区域树分页节点信息
	 * @throws Exception
	 */
	public Page<TreeNode> findAreaTree(PSysUser username, String node,
			long start, int limit) throws Exception;

	/**
	 * 方法 findBackTree
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 备选用户群组树分页节点信息
	 * @throws Exception
	 */
	public Page<TreeNode> findBackTree(PSysUser username, String node, long start,
			int limit) throws Exception;

	/**
	 * 方法 findControlTree
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 控制群组分页节点信息
	 * @throws Exception
	 */
	public Page<TreeNode> findControlTree(PSysUser username, String node,
			long start, int limit) throws Exception;

	/**
	 * 方法 findUserGrid
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return 用户列表
	 * @throws Exception
	 */
	public Page<CConsRtmnl> findUserGrid(PSysUser pSysUser, long start,
			int limit, UserGridBean ugb) throws Exception;

	/**
	 * 方法 findBureauList
	 * 
	 * @return 供电所列表
	 * @throws Exception
	 */
	public List<OOrg> findBureauList(PSysUser username) throws Exception;

	/**
	 * 方法 findProtocolList
	 * 
	 * @return 规约列表
	 * @throws Exception
	 */
	public List<VwProtocolCode> findProtocolList() throws Exception;
	
	/**
	 * 查询所有采集点类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List <CpTypeCode> getAllCpTypeCode() throws Exception;
}
