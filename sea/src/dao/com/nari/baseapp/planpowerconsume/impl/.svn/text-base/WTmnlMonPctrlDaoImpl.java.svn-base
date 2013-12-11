package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.nari.baseapp.planpowerconsume.IWTmnlMonPctrlDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.util.exception.DBAccessException;

/** 
 * 月电量定值控明细表Dao处理类
 * @author jiangweichao
 */
public class WTmnlMonPctrlDaoImpl extends HibernateBaseDaoImpl<WTmnlMonPctrl, String> implements IWTmnlMonPctrlDao{

	/**
	 * 添加或更新月电量定值控信息
	 * @param wTmnlMonPctrl
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(WTmnlMonPctrl wTmnlMonPctrl)throws DBAccessException{
		try{	
			String hql = "from WTmnlMonPctrl w where w.tmnlAssetNo = ? and w.totalNo=?";
			List<WTmnlMonPctrl> wTmnlMonPctrlList = getHibernateTemplate().find(hql, new Object[]{wTmnlMonPctrl.getTmnlAssetNo(),wTmnlMonPctrl.getTotalNo()});
			if(null!=wTmnlMonPctrlList && 0 <wTmnlMonPctrlList.size()){
				WTmnlMonPctrl w = wTmnlMonPctrlList.get(0);
				if(null!=wTmnlMonPctrl.getPowerConst()){
					BeanUtils.copyProperties(wTmnlMonPctrl,w,new String[]{"monPctrlId","ctrlSchemeId","ctrlFlag"});
					getHibernateTemplate().update(w);
				}
				else if(null!=wTmnlMonPctrl.getCtrlFlag()){
					BeanUtils.copyProperties(wTmnlMonPctrl,w,new String[]{"monPctrlId","ctrlSchemeId","isExec","floatValue","powerConst","saveTime"});
					getHibernateTemplate().update(w);
				}
			}
			else {
				super.save(wTmnlMonPctrl);
			}
		} catch (Exception e) {
			throw new DBAccessException();
		}
	}
	
	/**
	 * 保存或修改月电量定值控明细表，主要是添加方案适用
	 * @param monPctrl
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateByScheme(WTmnlMonPctrl wTmnlMonPctrl) throws DBAccessException{
		try{	
			String hql = "from WTmnlMonPctrl w where w.tmnlAssetNo = ? and w.totalNo=?";
			List<WTmnlMonPctrl> wTmnlMonPctrlList = getHibernateTemplate().find(hql, new Object[]{wTmnlMonPctrl.getTmnlAssetNo(),wTmnlMonPctrl.getTotalNo()});
			if(null!=wTmnlMonPctrlList && 0 <wTmnlMonPctrlList.size()){
				WTmnlMonPctrl w = wTmnlMonPctrlList.get(0);
				BeanUtils.copyProperties(wTmnlMonPctrl,w,new String[]{"monPctrlId","ctrlFlag"});
				getHibernateTemplate().update(w);
			}
			else {
				super.save(wTmnlMonPctrl);
			}
		} catch (Exception e) {
			throw new DBAccessException();
		}
	
	}
}
