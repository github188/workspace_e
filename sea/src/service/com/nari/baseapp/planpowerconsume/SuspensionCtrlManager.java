package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.basicdata.VwLimitType;
import com.nari.orderlypower.SuspensionControlDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 营业报停控业务层接口
 * @author 姜炜超
 */
public interface SuspensionCtrlManager {
	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，主要是写入方案主表和营业报停控表
	 * @param scheme
	 * @param busiList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean saveScheme(WCtrlScheme scheme, List<WTmnlBusiness> busiList) throws DBAccessException ;
	
	/**
	 * 查询限电类型
	 * @param
	 * @return List<VwLimitType>
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwLimitType> queryLimitTypeList()throws DBAccessException;
	
	/**
	 * 根据供电单位编号查询营业报停控Grid数据分页列表
	 * @param orgNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByOrgNo(String orgNo,String orgType,PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据客户编号查询营业报停控Grid数据分页列表
	 * @param consNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 根据线路Id查询营业报停控Grid数据分页列表
	 * @param lineId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByLineId(String lineId,PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据组号查询营业报停控Grid数据分页列表
	 * @param nodeType
	 * @param groupNo
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridByGroupNo(String nodeType,String groupNo)throws DBAccessException;
	
	/**
	 * 根据变电站标识查询营业报停控Grid数据分页列表
	 * @param subsId
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridBySubsId(String subsId,PSysUser pSysUser)throws DBAccessException;
	
	/**
	 * 根据方案号查询营业报停控Grid数据分页列表
	 * @param schemeNo
	 * @return Page<SuspensionControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	public List<SuspensionControlDto> querySuspCtrlGridBySchemeId(Long schemeId)throws DBAccessException;
	
	/**
	 * 更新营业报停控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void updateBusiStatus(List<WTmnlBusiness> busiList) throws DBAccessException;
	
	/**
	 * 更新营业报停控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void updateBusiness(List<WTmnlBusiness> busiList) throws DBAccessException;
	
	/**
	 * 查询营业报停控方案，用于校验状态编码是否可以进行投入和解除操作
	 * @param filters
	 * @return List<WTmnlBusiness> 
	 * @throws DBAccessException 数据库异常
	 */
	//public List<WTmnlBusiness> queryBusiList(List<PropertyFilter> filters)throws DBAccessException; 
	
	
	/**
	 * 新增或修改营业报停控方案，针对参数下发前用户的操作
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	//public void saveOrUpdateBusiness(WTmnlBusiness busi) throws DBAccessException;
	
    
    /**
	 * 修改方案，针对保存方案的业务处理，写入方案主表和营业报停控表
	 * @param wCtrlScheme
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public Boolean updateScheme(WCtrlScheme wCtrlScheme,List<WTmnlBusiness> busiList)throws DBAccessException;
}
