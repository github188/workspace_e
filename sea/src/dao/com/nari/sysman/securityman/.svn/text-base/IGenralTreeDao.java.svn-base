package com.nari.sysman.securityman;

import java.util.List;

import com.nari.basicdata.BTrade;
import com.nari.customer.CConsRtmnl;
import com.nari.grid.GLine;
import com.nari.grid.GSubs;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.CpTypeCode;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.RUserGroup;
import com.nari.terminalparam.RUserGroupDetailId;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.terminalparam.TTmnlGroupDetailId;

/**
 * 接口 IGenralTreeDao
 * 
 * @author zhangzhw
 * @describe 主页面左边树节点处理
 */
public interface IGenralTreeDao {

	/**
	 * 方法 findOrgByUpNode
	 * 
	 * @param username
	 *            登录用户
	 * @param upnodeid
	 * @param start
	 * @param limit
	 * @return 通过用户权限查询到的下级单位分页信息
	 */
	public Page<OOrg> findOrgByUsername(PSysUser username, long start, int limit);

	/**
	 * 方法 findOrgByUpNode
	 * 
	 * @param username
	 * @param unitType
	 *            单位类别
	 * @param upnodeid
	 * @param start
	 * @param limit
	 * @return 通过上级单位代码查询到的下级单位分页信息
	 */
	public Page<OOrg> findOrgByUpNode(PSysUser username, String unitType,
			String upnodeid, long start, int limit);

	/**
	 * 方法 findSubByOrg
	 * 
	 * @param orgNo
	 * @param start
	 * @param limit
	 * @return 通过上级单位查询到的变电站分页信息
	 */
	public Page<GSubs> findSubByOrg(String orgNo, long start, long limit);

	/**
	 * 方法 findLineBySub
	 * 
	 * @param subNo
	 * @param start
	 * @param limit
	 * @return 通过变电站编码取得的 线路分页信息
	 */
	public Page<GLine> findLineBySub(String subNo, long start, int limit);

	/**
	 * 方法　findCustmerByLine
	 * 
	 * @param lineNo
	 * @param start
	 * @param limit
	 * @return 通过线路查询到的　专变和台区用户
	 */
	public Page<CConsRtmnl> findCustmerByLine(String lineNo, long start,
			int limit, PSysUser pSysUser);

	/**
	 * 方法 findCustmerByTrade
	 * 
	 * @param tradeNo
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return 通过行业查询用户
	 */
	public Page<CConsRtmnl> findCustmerByTrade(String tradeNo, long start,
			int limit, PSysUser pSysUser);

	/**
	 * 方法 findTradeByUpTrade
	 * 
	 * @param uptrade
	 * @param start
	 * @param limit
	 * @return 通过上级行业查询到的 行业
	 */
	public Page<BTrade> findTradeByUpTrade(String uptrade, long start, int limit);

	/**
	 * 方法　findCustmerByOrg
	 * 
	 * @param lineNo
	 * @param start
	 * @param limit
	 * @return 通过供电所查询到的　专变和台区用户
	 */
	public Page<CConsRtmnl> findCustmerByOrg(String orgNo, long start,
			int limit, PSysUser pSysUser);

	/**
	 * findCustmerBycitizen
	 * 
	 * @param ugb
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return 查询居民户信息
	 */
	public Page<CConsRtmnl> findCustmerBycitizen(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser);

	/**
	 * findCustmerByTg
	 * 
	 * @param ugb
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return 查询台区信息
	 */
	public Page<CConsRtmnl> findCustmerByTg(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser);
	/**
	 * 方法　findCustmerByOrg
	 * 
	 * @param lineNo
	 * @param start
	 * @param limit
	 * @return 通过供电所查询到的　专变和台区用户
	 */
	public Page<CConsRtmnl> findCustmerByOrg(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser);

	/**
	 * 方法 findBackUserByName
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return 通过用户名查询备选用户群组
	 */
	public Page<RUserGroup> findBackUserByName(String username, long start,
			int limit);

	/**
	 * 方法 findBackUserDetail
	 * 
	 * @param groupNo
	 * @param start
	 * @param limit
	 * @return 通过群组名查询群组明细信息
	 */
	public Page<CConsRtmnl> findBackUserDetail(String groupNo, long start,
			int limit, PSysUser username);

	/**
	 * 方法 findControlGroupByName
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return 通过用户名查询控制用户群组
	 */
	public Page<TTmnlGroup> findControlGroupByName(String username, long start,
			int limit);

	/**
	 * 方法 findControlGroupDetail
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return 通过群组名查询控制群组详细信息
	 */
	public Page<CConsRtmnl> findControlGroupDetail(String groupNo, long start,
			int limit, PSysUser username);

	/**
	 * 方法 findBureauList
	 * 
	 * @return 供电所列表
	 */
	public List<OOrg> findBureauList(PSysUser username);

	/**
	 * 方法 findProtocolList
	 * 
	 * @return 规约列表
	 */
	public List<VwProtocolCode> findProtocolList();
	
	/**
	 * 查询采集点类型
	 * chenjg
	 * @return
	 */
	public List <CpTypeCode> findAllCpTypeCode();
	
	/**
	 * chenjg
	 * 变电站查询用户
	 * @param ugb
	 * @param start
	 * @param limit
	 * @param pSysUser
	 * @return
	 */
	public Page<CConsRtmnl> findCustmerBySubs(UserGridBean ugb, long start,
			int limit, PSysUser pSysUser);
}
