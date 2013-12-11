package com.nari.baseapp.planpowerconsume;

import java.util.Date;
import java.util.List;

import com.nari.orderlypower.WCtrlScheme;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 控制方案接口
 * 
 * @author 姜海辉
 * 
 */
public interface IWCtrlSchemeManager {

	/**
	 * 根据方案Id查询某种类型方案
	 * 
	 * @param schemeId
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> querySchemesById(Long schemeId)
			throws DBAccessException;

	/**
	 * 根据方案名称查询方案
	 * 
	 * @param staffNo
	 * @param schemeName
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> querySchemesByName(String staffNo,
			String schemeName) throws DBAccessException;
	
	/**
	 * 检查方案名称
	 * @param staffNo
	 * @param schemeType
	 * @param schemeName
	 * @return
	 * @throws DBAccessException
	 */
	public List<WCtrlScheme> checkSchemeName(String staffNo,
			String schemeType,String schemeName) throws DBAccessException;

	/**
	 * 主要用来查询某种类型的所有方案
	 * 
	 * @param schemeType
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByType(String staffNo,
			String schemeType) throws DBAccessException;

	/**
	 * 根据过滤条件查询方案
	 * 
	 * @filters
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByCond(List<PropertyFilter> filters)
			throws DBAccessException;

	/**
	 * 新建控制方案
	 * @param ctrlSchemeName
	 * @param ctrlType
	 * @param staffNo
	 * @param userOrgNo
	 * @param newStartDate
	 * @param newEndDate
	 * @param limitType
	 * @param ctrlLoad
	 * @param schemeRemark
	 */
	public void newScheme(String ctrlSchemeName, String ctrlType,
			String staffNo,String userOrgNo,Date newStartDate, Date newEndDate,
			String limitType,Double ctrlLoad,String schemeRemark)throws DBAccessException;
	
    /**
     * 修改控制方案信息
     * @param ctrlSchemeId
     * @param ctrlSchemeName
     * @param schemeNameFlag
     * @param ctrlType
     * @param newStartDate
     * @param newEndDate
     * @param limitType
     * @param ctrlLoad
     * @param schemeRemark
     * @param staffNo
     * @return
     * @throws DBAccessException
     */
	public int updateScheme(Long ctrlSchemeId,String ctrlSchemeName, Integer schemeNameFlag,
			Date newStartDate, Date newEndDate,String limitType,Double ctrlLoad,
			String schemeRemark,String staffNo)throws DBAccessException;

}
