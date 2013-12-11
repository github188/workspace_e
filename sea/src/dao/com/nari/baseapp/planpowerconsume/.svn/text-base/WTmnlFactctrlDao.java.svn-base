package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

public interface WTmnlFactctrlDao {
	/**
	 * 保存终端厂休控
	 * @param factctrl
	 * @throws DBAccessException
	 */
	public void save(WTmnlFactctrl factctrl) throws DBAccessException;
	
	/**
	 * 查询厂休控执行列表
	 * @param filters
	 * @return
	 */
	public List<WTmnlFactctrl> findBy(List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 按方案ID删除方案明细
	 * @param ctrlSchemeId
	 */
	public void deleteBySchemeId(Long ctrlSchemeId);
}
