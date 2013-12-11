package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

/**
 * @author 杨传文
 */
public interface DunningControlManager {
	/**
	 * 查询催费用户终端列表
	 * @param nodeId
	 * @param user
	 * @return
	 */
	public List<DunningControlBean> queryDunningControlUser(String nodeId,PSysUser user) throws DBAccessException;
}
