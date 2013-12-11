package com.nari.advapp.vipconsman;

import java.util.List;

import com.nari.util.exception.DBAccessException;

/**
 * 
 * 重点用户增删 service
 * @author ChunMingLi
 * @since  2010-6-5
 *
 */
public interface VipConsUserManager {
	/**
	 * 添加重点用户
	 * @param  vipConsUserList       重点用户列表
	 * @throws DBAccessException 数据库异常
	 */
	public int saveVipUser(List<VipConsManager> vipConsUserList) throws DBAccessException;
	
	/**
	 * 删除重点用户
	 * @param vipConsUserList   重点用户列表
	 * @return
	 * @throws DBAccessException 数据库异常
	 */
	public int deleteVipUser(List<VipConsManager> delUserList) throws DBAccessException;
}
