package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.PwrctrlSchemeExecBean;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * 功率控制查询Service
 * @author 杨传文
 */
public interface PowerCtrlQueryManager {
	/**
	 * 按方案ID查询时段控方案执行列表信息
	 * @param schemeId
	 * @return
	 * @throws DBAccessException
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySchemeId(Long schemeId) throws DBAccessException;
	
	/**
	 * 按资产编号查询时段控方案执行列表信息
	 * @param schemeId
	 * @return
	 * @throws DBAccessException
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;

	/**
	 * 按供电单位查询时段控方案执行列表信息
	 * @param schemeId
	 * @return
	 * @throws DBAccessException
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByOrgNo(String orgNo,String orgType, PSysUser user) throws DBAccessException;
	
	/**
	 * 按行业ID查询时段控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException;

	/**
	 * 按控制群组编号查询时段控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByCgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按普通群组编号查询时段控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByUgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按供电所ID查询时段控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySubsId(Long subsId, PSysUser user) throws DBAccessException;

	/**
	 * 查询时段控方案有效时段
	 * @param schemeId
	 * @return
	 */
	public String findSchemeValidSectionNo(Long schemeId) throws DBAccessException;

	/**
	 * 查询时段控方案对应模板名称
	 * @param schemeId
	 * @return
	 */
	public String findSchemeTemplateName(Long schemeId) throws DBAccessException;

	/**
	 * 查询时段控方案功率曲线
	 * @param schemeId
	 * @return
	 */
	public String findSchemeCurveNo(Long schemeId) throws DBAccessException;

	/**
	 * 按用户编号查询终端厂休控
	 * @param consNo 用户编号
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByTmnlAssetNo(String mnlAssetNo) throws DBAccessException;

	/**
	 * 按供电单位编号查询终端厂休控
	 * @param orgNo 供电单位编号
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByOrgNo(String orgNo,String orgType, PSysUser user) throws DBAccessException;

	/**
	 * 按方案ID查询终端厂休控
	 * @param schemeId 方案ID
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySchemeId(Long schemeId) throws DBAccessException;
	
	/**
	 * 按行业ID查询厂休控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException;

	/**
	 * 按控制群组编号查询厂休控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByCgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按普通群组编号查询时厂休用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByUgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按供电所ID查询厂休控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySubsId(Long subsId, PSysUser user) throws DBAccessException;
	
	/**
	 * 按资产编号编号查询终端下浮控
	 * @param consNo 用户编号
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException;

	/**
	 * 按供电单位编号查询终端下浮控
	 * @param orgNo 供电单位编号
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByOrgNo(String orgNo,String orgType, PSysUser user) throws DBAccessException;

	/**
	 * 按方案ID查询终端下浮控
	 * @param schemeId 方案ID
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySchemeId(	Long schemeId) throws DBAccessException;
	
	/**
	 * 按行业ID查询下浮控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException;

	/**
	 * 按控制群组编号查询下浮控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByCgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按普通群组编号查询下浮控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByUgroupNo(String groupNo) throws DBAccessException;

	/**
	 * 按供电所ID查询下浮控用户列表
	 * @param lineId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySubsId(Long subsId, PSysUser user) throws DBAccessException;

	/**
	 * 加载当前功率下浮控信息
	 * @param schemeId 方案ID
	 * @return
	 */
	public WFloatDownCtrl findfloatDownCtrl(Long schemeId) throws DBAccessException;

	/**
	 * 按方案ID查询模板名称
	 * @param schemeId
	 * @return
	 */
	public String findCxkSchemeTemplateName(Long schemeId) throws DBAccessException;

	/**
	 * 校验方案名称是否已存在
	 * @param ctrlSchemeName
	 * @param staffNo
	 * @return
	 */
	public Boolean queryCtrlSchemeName(String ctrlSchemeName, String staffNo)  throws DBAccessException;

	/**
	 * 根据方案ID查询时段控模板参数明细的时段个数
	 * @param schemeId
	 * @return
	 */
	public Integer findSchemeSecLength(Long schemeId)  throws DBAccessException;
	
	/**
	 * 据方案ID查询时段控模板ID
	 * @param schemeId
	 * @return
	 * @throws DBAccessException
	 */
	public String findSchemeTemplateId(Long schemeId) throws DBAccessException ;
}
