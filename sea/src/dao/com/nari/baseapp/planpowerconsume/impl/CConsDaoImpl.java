package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.ICConsDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.customer.CCons;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-28 下午05:39:40
 *
 * @类描述: 用电客户管理
 *	
 */
public class CConsDaoImpl extends HibernateBaseDaoImpl<CCons, Long> implements ICConsDao {

	/**
	 * @描述 分页查询用电客户 
	 */
	public Page<CCons> findAllCCons(Page<CCons> page){
		try {
			return findPage(page);
		} catch (DBAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
