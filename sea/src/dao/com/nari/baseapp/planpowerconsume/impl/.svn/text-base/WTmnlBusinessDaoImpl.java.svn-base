package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.nari.baseapp.planpowerconsume.IWTmnlBusinessDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.util.exception.DBAccessException;

/** 
 * 营业报停控明细表Dao处理类
 * @author jiangweichao
 */
public class WTmnlBusinessDaoImpl extends HibernateBaseDaoImpl<WTmnlBusiness, String> implements IWTmnlBusinessDao{
	/**
	 * 添加或更新营业报停控信息
	 * @param wTmnlBusiness
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(WTmnlBusiness busi)throws DBAccessException{
		try{	
			String hql = "from WTmnlBusiness w where w.tmnlAssetNo = ? and w.totalNo=?";
			List<WTmnlBusiness> wTmnlBusinessList = getHibernateTemplate().find(hql, new Object[]{busi.getTmnlAssetNo(),busi.getTotalNo()});
			if(null!=wTmnlBusinessList && 0 <wTmnlBusinessList.size()){
				WTmnlBusiness w = wTmnlBusinessList.get(0);
				if(null!=busi.getStopConst()){
					BeanUtils.copyProperties(busi, w,new String[]{"tmnlBusinessId","ctrlSchemeId","ctrlFlag"});
					getHibernateTemplate().update(w);
				}
				else if(null!= busi.getCtrlFlag()){
					BeanUtils.copyProperties(busi, w,new String[]{"tmnlBusinessId","ctrlSchemeId","stopStart","stopEnd","stopConst","saveTime"});
					getHibernateTemplate().update(w);
				}
			}
			else {
				super.save(busi);
			}
		} catch (Exception e) {
			throw new DBAccessException();
		}
	}
	
	
	/**
	 * 添加或更新营业报停控信息（用于添加更新方案）
	 * @param wTmnlBusiness
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateByScheme(WTmnlBusiness busi)throws DBAccessException{
		try{
			String hql = "from WTmnlBusiness w where w.tmnlAssetNo = ? and w.totalNo=?";
			List<WTmnlBusiness> wTmnlBusinessList = getHibernateTemplate().find(hql, new Object[]{busi.getTmnlAssetNo(),busi.getTotalNo()});
			if(null!=wTmnlBusinessList && 0 <wTmnlBusinessList.size()){
				WTmnlBusiness w = wTmnlBusinessList.get(0);
				BeanUtils.copyProperties(busi, w,new String[]{"tmnlBusinessId","ctrlFlag"});
				getHibernateTemplate().update(w);	
			}
			else {
				super.save(busi);
			}
		}catch (Exception e) {
			throw new DBAccessException();
		}
		
		
	}

}
