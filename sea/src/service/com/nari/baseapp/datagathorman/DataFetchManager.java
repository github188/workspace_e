package com.nari.baseapp.datagathorman;

import java.util.List;
import java.util.Map;

import com.nari.baseapp.datagatherman.DataFetchTerminal;
import com.nari.baseapp.datagatherman.EDataFinder;
import com.nari.baseapp.datagatherman.ProtocolGroupDTO;
import com.nari.privilige.PSysUser;
import com.nari.runman.dutylog.SaveLUserOpLog;
import com.nari.support.Page;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * 数据召唤业务层接口
 * 
 * @author 鲁兆淞
 */
public interface DataFetchManager {

	/**
	 * 查询透明规约类型树节点列表
	 * 
	 * @return 透明规约类型树节点列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<TreeNode> queryTreeNode(Integer queryType)
			throws DBAccessException;

	/**
	 * 查询召测组合
	 * 
	 * @return 召测组合列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<ProtocolGroupDTO> queryProtocolGroup(String staffNo)
			throws DBAccessException;

	/**
	 * 新增召测组合
	 * 
	 * @param protocolGroupDTO
	 *            透明规约组合DTO
	 * @throws DBAccessException
	 *             数据库异常
	 * @throws ServiceException
	 *             服务层异常
	 */
	public void insertProtocolGroup(ProtocolGroupDTO protocolGroupDTO)
			throws DBAccessException, ServiceException;

	/**
	 * 按终端ID查询终端
	 * 
	 * @param terminalId
	 *            终端ID
	 * @return 终端
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public DataFetchTerminal queryTerminalById(String terminalId)
			throws DBAccessException;

	/****
	 * 召测组合转化为树 *
	 **/
	public List<TreeNode> findGroupForTree(String staffNo)
			throws DBAccessException;

	/***
	 * 
	 * 通过插入的字符串来编辑召测的组合
	 * 
	 * @throws DBAccessException
	 * **/
	public void editProtocolGroup(List<String> list, String staffNo)
			throws DBAccessException;

	/***
	 * 得到一个透明规约编码的父项是多少
	 * 
	 * @return 如果没有父项 返回null
	 * @throws DBAccessException
	 * **/
	public String getFather(String clearNo) throws DBAccessException;

	/***
	 * 相应左边树的点击事件返回数据
	 * 
	 * @param staffNo
	 *            操作人员的工号
	 * @param postData
	 *            点击后都通post传到后台的数据 格式 org_值_...
	 * @param start
	 *            分页的开始
	 * @param 分页的结束
	 * 
	 *            return {@link RETURN} page对象 结果集里面放置的map *
	 ***/
	public Page findClick(PSysUser user, String postData, long start, int limit);

	/**
	 * 查找一个用户所有的组合和其中的项生成后的格式 格式如下,默认包含共享的项目<br>
	 * {组合的名称,用户编号,有效天数，创建时间，是否共享,是不是自己的组合}:{[<br>
	 * clearNo，<br>
	 * 透明规约的名称,<br>
	 * 规约的类型，//（一类，二类）<br>
	 * 标示,<br>
	 * 是否可能修改<br>
	 * ]<br>
	 * }<br>
	 * 经查询透明公约的长度全部是6 <br>
	 * <br>
	 * 此方法主要是直接生成便于前台处理的数据结构
	 * 
	 * @author huangxuan
	 * @param staffNo
	 *            工作人员编号
	 * @return 返回的格式中key采用驼峰命名法 *
	 **/
	@SuppressWarnings("unchecked")
	public Map<Map, List> findAllCombi(final String staffNo)
			throws DBAccessException;

	/***
	 * 通过一定的约定格式来进行更新 发生任何错误都会回滚 <br>
	 * combiEdit <br>
	 * combiDel <br>
	 * itemDel <br>
	 * itemAdd <br>
	 * 
	 * @param list
	 *            字符串标示的命名列表<br>
	 *            可能能发生效果的命令名称为 combiName newName staffNo isShare status
	 *            clearProtNo combiId validDays
	 * @param staffNo
	 *            工作人员的列表
	 ****/
	public void updateMain(List<String> list, PSysUser user)
			throws DBAccessException;

	/*****
	 *通过透明编码的列表得到透明编码所对应的名称
	 * 
	 * *
	 ****/
	public Map<String, String> findCodeName(List<String> list)
			throws DBAccessException;

	/***
	 * 通过combiId查找所有的某一个名字组合下的透明公约编码，内部进行去重名
	 * 
	 * @param combiId
	 *            *
	 ***/
	public List<String> findCodesByName(String combiId)
			throws DBAccessException;

	/***
	 * 通过dataid得到dataId和测量点之间对应关系的map 如果没有找到，返回null
	 * 
	 * @param dataIds
	 *            数据的id列表
	 * @return dataid和相互关联的pt的列表 *
	 ****/
	public Map<String, String> findDataIdPn(List dataIds)
			throws DBAccessException;

	/***
	 * 找到数据类型与数据类型名称的关系
	 * 
	 * @return 数据类型和值的对应关系 *
	 ***/
	public Map<String, String> mapTypeName() throws DBAccessException;

	/******
	 * 通过事件编码号找到时间的名称 *
	 *****/
	@SuppressWarnings("unchecked")
	public Map<String, String> mapEventName(List nos) throws DBAccessException;

	/******
	 * 通过规约得到数据的类型
	 * 
	 * @list 规约名称的list
	 * @return map 规约名称和数据类型之间的关联 *
	 *****/
	public Map<String, String> findCodeDataType(List<String> list)
			throws DBAccessException;

	/****
	 * @param log
	 *            填入必要的数据.
	 * 
	 *            对操作进行日志的保存 *
	 **/
	public void saveUserOpLog(SaveLUserOpLog log) throws DBAccessException;

	/*** 不带分页版本点击事件处理 ****/
	/***
	 * 相应左边树的点击事件返回数据
	 * 
	 * @param staffNo
	 *            操作人员的工号
	 * @param postData
	 *            点击后都通post传到后台的数据 格式 org_值_...
	 * 
	 *            return {@link RETURN} List对象 结果集里面放置的map *
	 ***/
	List<Map> findClickDirect(PSysUser user, String postData);
	/***
	 * 通过大项的列表来找到大项和大项所对应的名称
	 *请确保codes中的项都为大项
	 *@return 一个map key为大项的code，名称为大项的名称
	 * @throws ServiceException
	 *             *
	 **/
	Map<String, String> findBigCodeToName(List<String> codes)
			throws ServiceException;
	/*******
	 * 通过终端资产号和pns来找到对应的表下面的用户名和用户编号
	 * @param tmnlNo 终端资产号
	 * @param pns 所对应的pn的列表
	 * @return 返回一个key格式为终端资产号_pn
	 * 值为一个map 其中的key为consNo 和consName
	 * *
	 * @throws ServiceException ****/
	Map<String, Map<String, String>> findMeterCons(String tmnlNo,
			List<String> pns) throws ServiceException;
	/*****
	 * 通过tmnl_asset_no来显示对应的表计的信息
	 *@param tmnlAssetNo 要查询的终端资产号内码
	 *@param start 开始
	 *@param limit 限制
	 *@return 分页后的结果
	 * *
	 * @throws ServiceException ****/
	Page<Map> findCmpInto(String tmnlAssetNo, int start, long limit)
			throws ServiceException;
	/****查找一个终端下面所有的测量点
	 * @param tmnlAssetNo 终端资产号的内码
	 * @return pn的列表
	 * @throws ServiceException ****/
	@SuppressWarnings("unchecked")
	List findAllPns(String tmnlAssetNo) throws ServiceException;
	/****找到一个终端对应的采集器
	 * @param tmnlNo 终端资产号的内码
	 * @throws ServiceException *****/
	@SuppressWarnings("unchecked")
	List findFrmAsset(String tmnlNo) throws ServiceException;
	/**
	 * 通过一个采集器的资产号<br />
	 * 来找对应的表计资产号
	 * @param frmAssetNo
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	List findPns(String frmAssetNo) throws ServiceException;
	/**
	 * 请保证终端资产号不为空
	 * @param finder
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findCmpByFinder(EDataFinder finder, int start, long limit)
			throws ServiceException;
	/**
	 * 通过资产号得到其下的采集器，以及采集器下面对应的表
	 * @param tmnlAssetNo
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	Map<String, List> findFrmToAsset(String tmnlAssetNo)
			throws ServiceException;
	
	/**
	 * 四川盘龙通过表计编号更新表计状态(service)
	 * @param 
	 * @throws ServiceException 
	 */
	
	public boolean updateOnOffStatus(String meterID,String dueStatus,String switchStatus)
	throws DBAccessException;
	
	
}
