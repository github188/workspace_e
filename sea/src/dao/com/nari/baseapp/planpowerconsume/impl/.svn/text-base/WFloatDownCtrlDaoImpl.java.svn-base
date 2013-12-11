package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.WFloatDownCtrlDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.util.exception.DBAccessException;

public class WFloatDownCtrlDaoImpl extends HibernateBaseDaoImpl<WFloatDownCtrl, Long>
		implements WFloatDownCtrlDao {

	@Override
	public void deleteBySchemeId(Long ctrlSchemeId) throws DBAccessException {
		String hql = "from WFloatDownCtrl w where w.ctrlSchemeId =?";
		getHibernateTemplate().deleteAll(getHibernateTemplate().find(hql, ctrlSchemeId));
	}
}
