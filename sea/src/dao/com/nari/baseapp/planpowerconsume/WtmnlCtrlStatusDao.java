package com.nari.baseapp.planpowerconsume;

import com.nari.orderlypower.WTmnlCtrlStatus;

public interface WtmnlCtrlStatusDao {
	/**
	 * 插入或更新实时终端当前功率下浮控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusDownCtrlFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端营业报停控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusBusinessFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端厂休控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusFactctrlFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端时段控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusPwrctrlFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端遥控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusTurnFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端月电控状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusMonPctrlFlag(WTmnlCtrlStatus ctrlStatus);
	
	/**
	 * 插入或更新实时终端XXX状态
	 * @param ctrlStatus
	 */
	public void saveOrUpdateTmnlCtrlStatusFeectrlFlag(WTmnlCtrlStatus ctrlStatus);
}
