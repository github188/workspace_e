package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 功率时段控方案Service
 * @author 杨传文
 */
public interface WPwrctrlSchemeManager {
	
	/**
	 * 保存时段控方案明细
	 * @param schemeId
	 * @param tmpId
	 * @param pwrctrlList
	 * @param detailList
	 * @throws DBAccessException
	 */
	public boolean savePeriodctrlScheme(Long schemeId, Long tmpId, List<WTmnlPwrctrl> pwrctrlList,
			List<WTmnlPwrctrlDetail[]> detailList) throws DBAccessException; 
	  /**
     * 保存厂休控方案明细
     * @param schemeId
     * @param templateId
     * @param tmnlFactctrlList
     * @return
     * @throws DBAccessException
     */
	public boolean saveFactoryCtrlScheme(Long schemeId, Long templateId,
			List<WTmnlFactctrl> tmnlFactctrlList) throws DBAccessException;
	
	/**
	 * 保存下浮控方案明细
	 * @param schemeId
	 * @param tmnlFloatList
	 * @return
	 * @throws DBAccessException
	 */
	public boolean saveFloatCtrlScheme(Long schemeId,
			List<WFloatDownCtrl> tmnlFloatList) throws DBAccessException;
	
	/**
	 * 保存一个功率时段控方案
	 * @param pwrctrl
	 * @param detailList
	 * @throws DBAccessException
	 */
	public void savePwrctrlScheme(WCtrlScheme scheme, Long tmpId, List<WTmnlPwrctrl> pwrctrlList, List<WTmnlPwrctrlDetail[]> detailList) throws DBAccessException;
	
	/**
	 * 保存一个功率厂休控方案
	 * @param scheme 厂休控方案
	 * @param templateName 厂休控模板名称
	 * @param tmnlFactctrlList 
	 */
	public void saveFactctrlScheme(WCtrlScheme scheme, Long templateId, List<WTmnlFactctrl> tmnlFactctrlList) throws DBAccessException;
	
	/**
	 * 保存一个功率下浮控方案
	 * @param scheme 下浮控方案
	 * @param tmnlFactctrlList 
	 */
	public void saveFloatScheme(WCtrlScheme scheme, List<WFloatDownCtrl> tmnlFloatList) throws DBAccessException ;
	
	/**
	 * 对没有方案直接下发参数的情况，直接保存终端时段控
	 * @param pwrctrl
	 * @param detailList
	 * @throws DBAccessException
	 */
	public void savePwrctrl(WTmnlPwrctrl pwrctrl, WTmnlPwrctrlDetail[] details) throws DBAccessException;
	
	/**
	 * 对没有方案直接下发参数的情况，直接保存终端厂休控
	 * @param tmnlFactctrlList 
	 */
	public void saveFactctrl(WTmnlFactctrl tmnlFactctrl) throws DBAccessException;

	/**
	 * 查询时段控执行列表
	 * @param filters
	 * @return
	 */
	public List<WTmnlPwrctrl> queryTmnlPwrctrlList(
			List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 查询厂休控执行列表
	 * @param filters
	 * @return
	 */
	public List<WTmnlFactctrl> queryTmnlFactctrlList(
			List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 查询当前功率下浮控执行列表
	 * @param filters
	 * @return
	 */
	public List<WFloatDownCtrl> queryFloatDownCtrlList(
			List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 修改终端厂休控发送时间和控制状态
	 * @param factctrlList
	 */
	public void updateTmnlFactctrl(WTmnlFactctrl factctrl) throws DBAccessException;
	
	/**
	 * 修改终端时段控控发送时间和控制状态
	 * @param pwrctrllList
	 */
	public void updateTmnlPwrctrl(WTmnlPwrctrl pwrctrl) throws DBAccessException;
	
	/**
	 * 修改下浮控发送时间和控制状态
	 * @param floatList
	 */
	public void updateTmnlFloat(WFloatDownCtrl floatDown) throws DBAccessException;
}
