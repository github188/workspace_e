package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.IRTmnlRunJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.util.exception.DBAccessException;

/**
 * 运行终端JdbcDao接口实现类
 * @author haihui
 *
 */
public class RTmnlRunJdbcDaoImpl extends JdbcBaseDAOImpl implements IRTmnlRunJdbcDao{

	/**
	 * 更新终端保电信息
	 * @throws DBAccessException
	 */
	public void updateProtect(String tmnlAssetNo,Integer flag)throws DBAccessException{
		String sql ="update r_tmnl_run r set r.ps_ensure_flag = ? where r.tmnl_asset_no = ?";
		try{
			getJdbcTemplate().update(sql, new Object[]{flag,tmnlAssetNo});
		}catch(RuntimeException e){
			this.logger.debug("更新终端保电信息出错！");
			throw e;
		}
	}
}
