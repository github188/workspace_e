package com.nari.sysman.securityman.impl;

/**
 *@创建人：陈建国
 *@创建时间：20092009-11-15下午01:30:38
 *@类描述：系统用户dao
 */
import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.IPSysUserDao;
import com.nari.util.exception.DBAccessException;

public class PSysUserDaoImpl extends HibernateBaseDaoImpl<PSysUser, String>
		implements IPSysUserDao {

	/**
	 * 根据用户名模糊查询所有的用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 用户信息的List
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<PSysUser> findAllByName(String name) throws DBAccessException {
		return this.findBy("name", name, MatchType.LIKE);
	}

	/**
	 * 查询所有的用户信息
	 * 
	 * @return 用户信息的List
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<PSysUser> findAll() throws DBAccessException {
		return this.findBy("name", "", MatchType.LIKE);
	}

	/**
	 * @描述 根据工号和密码查找系统用户
	 * @param staffNo
	 *            工号
	 * @param password
	 *            密码
	 * @return
	 * @throws DBAccessException
	 */
	public PSysUser findByStaffNoAndPWD(String staffNo, String password)
			throws DBAccessException {
		String[] args = { staffNo };
		List list = this.getHibernateTemplate().find(
				"from PSysUser p where p.staffNo = ? ", args);
		if (list.size() > 0) {
			return (PSysUser) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据工号查询所有的用户信息
	 * 
	 * @param staffNo
	 *            工号
	 * @return 用户信息
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public PSysUser findByStaffNo(String staffNo) throws DBAccessException {
		String[] args = { staffNo };
		List list = this.getHibernateTemplate().find(
				"from PSysUser p where p.staffNo = ?", args);
		if (list.size() > 0) {
			return (PSysUser) list.get(0);
		} else {
			return null;
		}
	}
}
