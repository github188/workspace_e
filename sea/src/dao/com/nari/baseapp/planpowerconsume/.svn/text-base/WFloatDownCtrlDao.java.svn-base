package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 当前功率下浮控DAO
 * @author 杨传文
 */
public interface WFloatDownCtrlDao {
	/**
	 * 保存一个当前功率下浮控模板
	 * @param downCtrl
	 */
	public void save(WFloatDownCtrl downCtrl) throws DBAccessException;
	
	/**
	 * 查询当前功率下浮控执行列表
	 * @param filters
	 * @return
	 */
	public List<WFloatDownCtrl> findBy(List<PropertyFilter> filters) throws DBAccessException;

	/**
	 * 按方案ID删除方案明细
	 * @param ctrlSchemeId
	 * @throws DBAccessException
	 */
	public void deleteBySchemeId(Long ctrlSchemeId) throws DBAccessException;
}
