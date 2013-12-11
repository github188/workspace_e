package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.WTmnlFactctrlDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlFactctrl;

public class WTmnlFactctrlDaoImpl extends HibernateBaseDaoImpl<WTmnlFactctrl, Long> implements
		WTmnlFactctrlDao {
	@Override
	public void deleteBySchemeId(Long ctrlSchemeId) {
		String hql = "from WTmnlFactctrl w where w.ctrlSchemeId = ?";
		getHibernateTemplate().deleteAll(getHibernateTemplate().find(hql,ctrlSchemeId));
	}
}
