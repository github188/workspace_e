package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.IWTmnlPaulPowerDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者:姜海辉
 * 创建时间：2009-12-15 上午09:26:31 
 * 描述：
 */

public class WTmnlPaulPowerDaoImpl extends HibernateBaseDaoImpl<WTmnlPaulPower, Long> implements IWTmnlPaulPowerDao{

	/**
	 * 新增或修改终端保电信息
	 * @param wTmnlPaulPower 终端保电信息
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException{
		try{
			String hql = "from WTmnlPaulPower w where w.tmnlAssetNo = ? ";
			List<WTmnlPaulPower> wTmnlPaulPowerList = getHibernateTemplate().find(hql, new Object[]{wTmnlPaulPower.getTmnlAssetNo()});
			if(null!=wTmnlPaulPowerList && 0 <wTmnlPaulPowerList.size()){
				WTmnlPaulPower w = wTmnlPaulPowerList.get(0);
				if(null!=wTmnlPaulPower.getAutoPaulPower()||null!=wTmnlPaulPower.getSecurityValue()){
					if(null==wTmnlPaulPower.getAutoPaulPower())
						BeanUtils.copyProperties(wTmnlPaulPower, w,new String[]{"tmnlPaulPowerId"," ctrlSchemeId","duration","ctrlFlag","autoPaulPower"});
					else if(null==wTmnlPaulPower.getSecurityValue())
						BeanUtils.copyProperties(wTmnlPaulPower, w,new String[]{"tmnlPaulPowerId"," ctrlSchemeId","duration","ctrlFlag","securityValue"});
					else
						BeanUtils.copyProperties(wTmnlPaulPower, w,new String[]{"tmnlPaulPowerId"," ctrlSchemeId","duration","ctrlFlag"});
					getHibernateTemplate().update(w);
				}
				else if(null!=wTmnlPaulPower.getCtrlFlag()){
					BeanUtils.copyProperties(wTmnlPaulPower, w,new String[]{"tmnlPaulPowerId"," ctrlSchemeId","autoPaulPower","securityValue","saveTime"});
					getHibernateTemplate().update(w);
				}
			} else {
				super.save(wTmnlPaulPower);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 新增或修改终端保电信息
	 * @param wTmnlPaulPower 终端保电信息
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateByScheme(WTmnlPaulPower wTmnlPaulPower) throws DBAccessException{
		try{
			String hql = "from WTmnlPaulPower w where w.tmnlAssetNo = ? ";
			List<WTmnlPaulPower> wTmnlPaulPowerList = getHibernateTemplate().find(hql, new Object[]{wTmnlPaulPower.getTmnlAssetNo()});
			if(null!=wTmnlPaulPowerList && 0 <wTmnlPaulPowerList.size()){
				WTmnlPaulPower w = wTmnlPaulPowerList.get(0);
				BeanUtils.copyProperties(wTmnlPaulPower, w,new String[]{"tmnlPaulPowerId"});
				getHibernateTemplate().update(w);
			} else {
				super.save(wTmnlPaulPower);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
