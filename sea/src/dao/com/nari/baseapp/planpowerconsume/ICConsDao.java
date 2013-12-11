package com.nari.baseapp.planpowerconsume;

import com.nari.customer.CCons;
import com.nari.support.Page;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-28 下午05:37:56
 *
 * @类描述: 用电客户管理
 *	
 */
public interface ICConsDao {

	/**
	 * 
	 * @param page
	 * @return
	 * @描述 分页查询用电客户 
	 */
	public Page<CCons> findAllCCons(Page<CCons> page);
}
