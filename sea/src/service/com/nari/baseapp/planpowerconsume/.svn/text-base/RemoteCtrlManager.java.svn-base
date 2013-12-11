package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.basicdata.VwLimitType;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.RmtCtrlSwitchDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlTurn;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 遥控业务层接口
 * @author 姜炜超
 */
public interface RemoteCtrlManager {
	/**
	 * 查询方案类型
	 * @param schemeName
	 * @return String
	 * @throws DBAccessException 数据库异常
	 */
	public String querySchemeType(String schemeName)throws DBAccessException;
	
	/**
	 * 主要用来查询某种类型的所有方案
	 * @param schemeType
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByType(String schemeType, String staffNo)throws DBAccessException;
	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，主要是写入方案主表和遥控表
	 * @param scheme
	 * @param turnList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdateScheme(WCtrlScheme scheme, List<WTmnlTurn> turnList) throws DBAccessException ;
	
	/**
	 * 查询限电类型
	 * @param
	 * @return List<VwLimitType>
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwLimitType> queryLimitTypeList()throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询遥控Grid数据分页列表
	 * @param orgNo
	 * @return Page<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridByOrgNo(String orgNo,String orgType, PSysUser user, String flag)throws DBAccessException;
	
	/**
	 * 根据终端资产编号查询遥控Grid数据分页列表
	 * @param consNo
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridByTmnlAssetNo(String tmnlAssetNo, String flag)throws DBAccessException;
	
	/**
	 * 根据线路Id查询遥控Grid数据分页列表
	 * @param lineId
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridByLineId(String lineId, PSysUser user, String flag)throws DBAccessException;
	
	/**
	 * 根据组号查询遥控Grid数据分页列表
	 * @param groupNo
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridByGroupNo(String groupNo, String flag)throws DBAccessException;
	
	/**
	 * 根据变电站标识查询遥控Grid数据分页列表
	 * @param subsId
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridBySubsId(String subsId, PSysUser user, String flag)throws DBAccessException;
	
	/**
	 * 根据方案号查询遥控Grid数据列表
	 * @param schemeNo
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridBySchemeNo(Long schemeNo, String flag)throws DBAccessException;
	
	/**
	 * 根据组号查询遥控Grid数据列表
	 * @param groupNo
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<RemoteControlDto> queryRemoteCtrlGridByUgpGroupNo(String groupNo, String flag);
	
	/**
	 * 更新遥控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param turnList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateTurn(WTmnlTurn turn) throws DBAccessException;
	
	/**
	 * 根据过滤条件查询方案
	 * @filters 
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByCond(List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 根据工号查询其供电单位
	 * @param staffNo
	 * @return String
	 */
    public String findOrgNoByStaffNo(String staffNo)throws DBAccessException;

	/**
	 * 按终端规约和FN号查询规约项数据编码列表
	 * @param turnType 遥测类型
	 * @return
	 */
	public List<String> findProItemNo(String trunType) throws DBAccessException;
	
	/**
	 * 遥测开关状态
	 * @param tmnlList 终端信息列表
	 * @param second 超时时间
	 * @return 召测结果列表
	 * @throws DBAccessException
	 */
	public List<RmtCtrlSwitchDto> fetchSwitchStatus(List<CtrlParamBean> tmnlList, int second) throws DBAccessException;
	

	/**
	 * 遥控拉闸
	 * @param tmnlList 终端信息列表
	 * @param second 超时时间
	 * @param user 操作员
	 * @return
	 * @throws DBAccessException
	 */
	public List<RemoteControlDto> remoteCtrlClose(List<CtrlParamBean> tmnlList, int second, PSysUser user, 
			String turnFlag, short alertDelayHours, short limitMins, String localIp) throws DBAccessException;
	
	/**
	 * 遥控合闸
	 * @param tmnlList 终端信息列表
	 * @param second 超时时间
	 * @param user 操作员
	 * @return
	 * @throws DBAccessException
	 */
	public List<RemoteControlDto> remoteCtrlOpen(List<CtrlParamBean> tmnlList, int second, PSysUser user, 
			String turnFlag, String localIp) throws DBAccessException;
}
