package com.nari.oranization.impl;

import java.util.List;

import com.nari.action.baseaction.Constans;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.oranization.OOrgDao;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.DBAccessException;

public class OOrgDaoImpl extends HibernateBaseDaoImpl<OOrg, String> implements
		OOrgDao {

	/**
	 * 根据上级orgNO查询
	 * 
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<OOrg> findByPId(String orgNo) throws DBAccessException {
		if (orgNo == null || orgNo.isEmpty())
			return super.findBy("POrgNo", Constans.STAGEGRID_ORGNO, "sortNo",
					"asc");

		// 处理访问权限问题
		// if (orgNo.equals("orgtree_root")) {
		// List<OOrg> list = this.findBy("OrgNo", pSysUser.getOrgNo(),
		// "sortNo", "asc");
		//
		// OOrg oorg = list.get(0);
		// if (!oorg.getOrgType().equals(pSysUser.getAccessLevel()))
		// list = this.findBy("OrgNo", oorg.getPOrgNo(), "sortNo", "asc");
		// return list;
		// // return this.findBy("POrgNo", Constans.STAGEGRID_ORGNO, "sortNo",
		// // "asc");
		// }

		return this.findBy("POrgNo", orgNo, "sortNo", "asc");
	}

//	public List<OOrg> findBy(String property, String value, String sorPro,
//			String sort) throws DBAccessException {
//		return super.findBy(property, value, sorPro, sort);
//	}

}
