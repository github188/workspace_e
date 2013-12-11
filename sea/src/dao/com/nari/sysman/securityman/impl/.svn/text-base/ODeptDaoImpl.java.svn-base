package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.action.baseaction.Constans;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orgnization.ODept;
import com.nari.sysman.securityman.IODeptDao;
import com.nari.util.exception.DBAccessException;

/**
 * 作者: 姜海辉
 * 创建时间：2009-11-17 下午08:22:24
 * 描述：
 */
public class ODeptDaoImpl extends HibernateBaseDaoImpl<ODept, String> implements IODeptDao {

	@Override
	public List<ODept> findByPId(String node) throws DBAccessException {

		if(node.equals("deptree_root"))
			return this.findBy("PDeptNo", Constans.STAGEGRID_DEPNO, "dispSn", "asc");
		return this.findBy("PDeptNo", node, "dispSn", "asc");
	}

}
