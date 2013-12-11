package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.EnergyControlDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 月电量定值控业务层接口
 * @author 姜炜超
 */
public interface EnergyCtrlManager {
	/**
	 * 保存月电控方案明细
	 * @param schemeId
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public void saveSchemeDeal(Long schemeId, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException ;
	
	/**
	 * 新增方案，针对新增加方案的业务处理，主要是写入方案主表和月电量定值控表
	 * @param scheme
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean saveScheme(WCtrlScheme scheme, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException ;
	
	/**
	 * 修改方案，针对新增加方案的业务处理，主要是写入方案主表和月电量定值控表
	 * @param scheme
	 * @param monPctrlList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme scheme, List<WTmnlMonPctrl> monPctrlList) throws DBAccessException ;

	
	/**
	 * 根据供电单位编号查询月电量定值控Grid数据分页列表
	 * @param orgNo
	 * @param orgType
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByOrgNo(String orgNo, String orgType,PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据终端资产号查询月电量定值控Grid数据
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 根据客户编号查询月电量定值控Grid数据分页列表
	 * @param consNo
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByConsNo(String consNo)throws DBAccessException;
		
	/**
	 * 根据线路Id查询月电量定值控Grid数据分页列表
	 * @param lineId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByLineId(String lineId, PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据组号查询月电量定值控Grid数据分页列表
	 * @param groupNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridByGroupNo(String nodeType,String groupNo)throws DBAccessException;
	
	/**
	 * 根据变电站标识查询月电量定值控Grid数据分页列表
	 * @param subsId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridBySubsId(String subsId,PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据方案号查询月电量定值控Grid数据分页列表
	 * @param schemeNo
	 * @return Page<EnergyControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<EnergyControlDto> queryEnergyCtrlGridBySchemeId(Long schemeId)throws DBAccessException;
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void updateMonPctrlStatus(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException;
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void updateMonPctrl(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException;
	
	
	/**
	 * 查询月电量定值控方案，用于校验状态编码是否可以进行投入和解除操作
	 * @param filters
	 * @return List<WTmnlMonPctrl> 
	 * @throws DBAccessException 数据库异常
	 */
	//public List<WTmnlMonPctrl> queryMonPctrlList(List<PropertyFilter> filters)throws DBAccessException; 
	
	
	/**
	 * 新增或修改月电量定值控方案，针对参数下发前用户的操作，只修改月电量定值控明细表
	 * @param monPctrlList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void saveOrUpdateMonPctrl(List<WTmnlMonPctrl> monPctrlList) throws DBAccessException;
	

	/**
	 * 新增或修改月电量定值控方案，针对参数下发前用户的操作，只修改月电量定值控明细表
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void saveOrUpdateMonPctrl(WTmnlMonPctrl monPctrl) throws DBAccessException;
	
	/**
	 * 更新月电量定值控方案明细
	 */
	public void updateSchemeDetail(WTmnlMonPctrl wTmnlMonPctrl,short status) throws DBAccessException;
	
	
}
