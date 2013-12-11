package com.nari.sysman.securityman.impl;
import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PRole;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.IPRoleDao;
import com.nari.util.exception.DBAccessException;

/**
 * 作者:姜海辉
 * 创建时间：2009-11-16 上午10:52:36
 * 描述：
 */
public class PRoleDaoImpl extends HibernateBaseDaoImpl<PRole, Long> implements IPRoleDao {

	/**
	 * 根据角色描述查询用户信息
	 * @param roleDesc
	 * @return
	 * @throws DBAccessException 数据库异常
	 */
	public List<PRole> findByRoleDesc(String roleDesc) throws DBAccessException {
		return this.findBy("roleDesc", roleDesc, MatchType.LIKE);
	}

}
