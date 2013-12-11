package com.nari.baseapp.planpowerconsume;

import com.nari.orderlypower.WCtrlSchemeSection;
import com.nari.util.exception.DBAccessException;

/**
 * 控制方案时段控
 * @author 杨传文
 */
public interface WCtrlSchemeSectionDao {
	/**
	 * 保存一个控制方案时段控
	 * @param schemeSection
	 * @throws DBAccessException
	 */
	public void save(WCtrlSchemeSection schemeSection) throws DBAccessException;
}
