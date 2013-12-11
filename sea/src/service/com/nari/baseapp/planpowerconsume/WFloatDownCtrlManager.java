package com.nari.baseapp.planpowerconsume;

import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.util.exception.DBAccessException;

/**
 * 当前功率下浮控Service
 * @author 杨传文
 */
public interface WFloatDownCtrlManager {
	/**
	 * 保存一个当前功率下浮控模板
	 * @param downCtrl
	 */
	public void save(WFloatDownCtrl downCtrl) throws DBAccessException;
}
