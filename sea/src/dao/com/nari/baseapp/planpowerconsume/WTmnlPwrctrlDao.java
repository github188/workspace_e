package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 终端功率时段控曲线DAO
 * @author 杨传文
 */
public interface WTmnlPwrctrlDao {
	/**
	 * 保存一个终端功率时段控曲线
	 * @param pwrctrl
	 */
	public void save(WTmnlPwrctrl pwrctrl,WTmnlPwrctrlDetail[] detail) throws DBAccessException;
	
	/**
	 * 查询时段控执行列表
	 * @param filters
	 * @return
	 */
	public List<WTmnlPwrctrl> findBy(List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 清除指定方案明细列表
	 * @param ctrlSchemeId
	 */
	public void deleteBySchemeId(Long ctrlSchemeId);
}
