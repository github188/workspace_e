package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.nari.baseapp.planpowerconsume.IWTmnlRejectDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlReject;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 
 * 创建时间：2009-12-23 下午05:28:43 
 * 描述：
 */

public class WTmnlRejectDaoImpl extends HibernateBaseDaoImpl<WTmnlReject , Long>implements IWTmnlRejectDao{
	

	/**
	 * 添加修改终端剔除信息
	 * @param wTmnlReject
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(WTmnlReject  wTmnlReject) throws DBAccessException{
		String hql = "from WTmnlReject w where w.tmnlAssetNo = ? ";
		List<WTmnlReject> wTmnlRejectList = getHibernateTemplate().find(hql, new Object[]{wTmnlReject.getTmnlAssetNo()});
		if(null!=wTmnlRejectList && 0 <wTmnlRejectList.size()){
			WTmnlReject w = wTmnlRejectList.get(0);
			BeanUtils.copyProperties(wTmnlReject,w,new String[]{"tmnlRejectId","ctrlSchemeId"});
			getHibernateTemplate().update(w);
		} else {
			super.save(wTmnlReject);
		}
	}
	
	/**
	 * 添加修改终端剔除信息
	 * @param wTmnlReject
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateByScheme(WTmnlReject  wTmnlReject) throws DBAccessException{
		String hql = "from WTmnlReject w where w.tmnlAssetNo = ? ";
		List<WTmnlReject> wTmnlRejectList = getHibernateTemplate().find(hql, new Object[]{wTmnlReject.getTmnlAssetNo()});
		if(null!=wTmnlRejectList && 0 <wTmnlRejectList.size()){
			WTmnlReject w = wTmnlRejectList.get(0);
			BeanUtils.copyProperties(wTmnlReject,w,new String[]{"tmnlRejectId"});
			getHibernateTemplate().update(w);
		} else {
			super.save(wTmnlReject);
		}
	}
	
}
