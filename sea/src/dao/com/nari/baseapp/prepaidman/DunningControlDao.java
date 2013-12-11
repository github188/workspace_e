package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.privilige.PSysUser;

public interface DunningControlDao {
	/**
	 * 查询催费控制用户
	 * @param nodeId
	 * @param user
	 * @return
	 */
	public List<DunningControlBean> queryDunningControlUser(String nodeId, PSysUser user);
}
