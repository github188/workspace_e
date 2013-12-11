package com.nari.baseapp.planpowerconsume;

import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.util.exception.DBAccessException;

public interface PowerCtrlDao {
	/**
	 * 修改终端厂休控发送时间和控制状态
	 * @param factctrlList
	 */
	public void updateTmnlFactctrl(WTmnlFactctrl tmnlFactctrl) throws DBAccessException;

	/**
	 * 修改下浮控发送时间和控制状态
	 * @param floatList
	 */
	public void updateTmnlFloat(WFloatDownCtrl floatDownCtrl) throws DBAccessException;

	/**
	 * 修改终端时段控控发送时间和控制状态
	 * @param pwrctrllList
	 */
	public void updateTmnlPwrctrl(WTmnlPwrctrl tmnlPwrctrl) throws DBAccessException;

	/**
	 * 根据方案ID删除方案模板关系表
	 * @param ctrlSchemeId
	 */
	public void deleteBySchemeId(Long ctrlSchemeId);
}
