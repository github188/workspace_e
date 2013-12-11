package com.nari.sysman.securityman;

import java.util.List;

import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * DAO接口
 * 
 * @author zhangzhw 左边树用户列表高级查询DAO接口
 */
public interface IUserAdvanceQueryDao {

	/**
	 * 方法
	 * 
	 * @param con
	 * @param start
	 * @param limit
	 * @param username
	 * @return 通过条件查询到的用户列表
	 */
	public Page<CConsRtmnl> findCustmerByCon(String con, long start, int limit,
			PSysUser username);

	/**
	 * 方法 findBureauList
	 * 
	 * @return 供电所列表
	 */
	public List<OOrg> findBureauList(String username);

	/**
	 * 方法 findOrgNoList
	 * 
	 * @return 供电单位列表
	 */
	public List<OOrg> findOrgNoList(String username);
	
	
	/**
	 * action 方法 　 subIdList
	 * 
	 * @return 字典列表　变电站
	 * @throws Exception
	 */
	public List subIdList(PSysUser pSysUser) throws Exception ;

	/**
	 * action 方法 　 lineIdList
	 * 
	 * @return 字典列表　线路
	 * @throws Exception
	 */
	public List lineIdList(PSysUser pSysUser) throws Exception;

	/**
	 * action 方法 　 tradeCodeList
	 * 
	 * @return 字典列表　行业
	 * @throws Exception
	 */
	public List tradeCodeList() throws Exception ;

	/**
	 * action 方法 　 consTypeList
	 * 
	 * @return 字典列表　用户类型
	 * @throws Exception
	 */
	public List consTypeList() throws Exception ;

	/**
	 * action 方法 　 elecTypeList
	 * 
	 * @return 字典列表　用电类别
	 * @throws Exception
	 */
	public List elecTypeList() throws Exception ;

	/**
	 * action 方法 　 capGradeList
	 * 
	 * @return 字典列表　用电容量等级
	 * @throws Exception
	 */
	public List capGradeList() throws Exception ;

	/**
	 * action 方法 　 shiftNoList
	 * 
	 * @return 字典列表　班次
	 * @throws Exception
	 */
	public List shiftNoList() throws Exception ;

	/**
	 * action 方法 　 lodeAttrList
	 * 
	 * @return 字典列表　负荷性质
	 * @throws Exception
	 */
	public List lodeAttrList() throws Exception ;

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　终端类型
	 * @throws Exception
	 */
	public List tmnlTypeList() throws Exception ;

	/**
	 * action 方法 　 tmnlTypeList
	 * 
	 * @return 字典列表　采集方式
	 * @throws Exception
	 */
	public List collTypeList() throws Exception ;

}
