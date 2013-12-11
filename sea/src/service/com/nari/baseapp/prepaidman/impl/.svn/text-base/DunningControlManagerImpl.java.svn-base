package com.nari.baseapp.prepaidman.impl;

import java.util.List;

import com.nari.baseapp.prepaidman.DunningControlBean;
import com.nari.baseapp.prepaidman.DunningControlDao;
import com.nari.baseapp.prepaidman.DunningControlManager;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

public class DunningControlManagerImpl implements DunningControlManager {
	private DunningControlDao dunningControlDao;
	
	public void setDunningControlDao(DunningControlDao dunningControlDao) {
		this.dunningControlDao = dunningControlDao;
	}
	@Override
	public List<DunningControlBean> queryDunningControlUser(String nodeId,
			PSysUser user)  throws DBAccessException{
		try{
			return dunningControlDao.queryDunningControlUser(nodeId, user);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DBAccessException("查询催费控制用户出错");
		}
	}
}
