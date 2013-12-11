package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.util.exception.DBAccessException;

/**
 * 终端功率时段控曲线明细DAO
 * @author Administrator
 */
public interface WTmnlPwrctrlDetailDao {
	/**
	 * 保存一个终端功率时段控曲线明细记录
	 * @param detail
	 * @throws DBAccessException
	 */
	public void save(WTmnlPwrctrlDetail detail) throws DBAccessException;
	
	/**
	 * 按父表ID查询终端功率时段控曲线明细列表
	 * @param pwrctrlId
	 * @return
	 * @throws DBAccessException
	 */
	public List<WTmnlPwrctrlDetail> findList(Long pwrctrlId) throws DBAccessException;
}	
