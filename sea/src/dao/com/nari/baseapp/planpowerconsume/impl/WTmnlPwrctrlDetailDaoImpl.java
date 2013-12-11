package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.nari.baseapp.planpowerconsume.WTmnlPwrctrlDetailDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.orderlypower.WTmnlPwrctrlDetailId;
import com.nari.util.exception.DBAccessException;

public class WTmnlPwrctrlDetailDaoImpl extends HibernateBaseDaoImpl<WTmnlPwrctrlDetail, WTmnlPwrctrlDetailId>
		implements WTmnlPwrctrlDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<WTmnlPwrctrlDetail> findList(Long pwrctrlId)
			throws DBAccessException {
		String hql = "from WTmnlPwrctrlDetail d where d.id.tmnlPwrctrlId=? order by d.id.sectionNo";
		return getHibernateTemplate().find(hql,pwrctrlId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(WTmnlPwrctrlDetail detail) throws DBAccessException {
		String hql = "from WTmnlPwrctrlDetail w where w.id.tmnlPwrctrlId = ? and w.id.sectionStart = ?";
		List<WTmnlPwrctrlDetail> detailList = getHibernateTemplate().find(hql, new Object[]{detail.getId().getTmnlPwrctrlId(), 
				detail.getId().getSectionStart()});
		if(detailList.size()>0){
			WTmnlPwrctrlDetail det = detailList.get(0);
			
			WTmnlPwrctrlDetailId detailId = detail.getId(); 
			BeanUtils.copyProperties(detailId, det.getId(),new String[]{"tmnlPwrctrlId"});
			det.setId(det.getId());
			getHibernateTemplate().update(det);
		} else {
			super.save(detail);
		}
	}
}
