package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import com.nari.baseapp.planpowerconsume.WTmnlPwrctrlDao;
import com.nari.baseapp.planpowerconsume.WTmnlPwrctrlDetailDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.util.exception.DBAccessException;

public class WTmnlPwrctrlDaoImpl extends HibernateBaseDaoImpl<WTmnlPwrctrl, Long> implements
		WTmnlPwrctrlDao {

	private WTmnlPwrctrlDetailDao wTmnlPwrctrlDetailDao;
	
	public void setwTmnlPwrctrlDetailDao(WTmnlPwrctrlDetailDao tmnlPwrctrlDetailDao) {
		wTmnlPwrctrlDetailDao = tmnlPwrctrlDetailDao;
	}

	@Override
	public void save(WTmnlPwrctrl pwrctrl,WTmnlPwrctrlDetail[] detail) throws DBAccessException {
		if(getSession()!=null){
			getSession().clear();
		}
//		String hql = "from WTmnlPwrctrl w where w.consNo = ? and w.tmnlAssetNo = ? and w.totalNo = ?";
//		List<WTmnlPwrctrl> pwrctrlList = getHibernateTemplate().find(hql, new Object[]{pwrctrl.getConsNo(), 
//				pwrctrl.getTmnlAssetNo(),pwrctrl.getTotalNo()});
//		if(pwrctrlList.size()>0){
//			WTmnlPwrctrl persistPwrctrl = pwrctrlList.get(0);
//			if(pwrctrl.getIsExec()){//判断是否执行浮动系数，对不执行浮动系数的，不跟新原来的浮动系数值
//				BeanUtils.copyProperties(pwrctrl, persistPwrctrl,new String[]{"tmnlPwrctrlId","ctrlSchemeId"});
//			} else {
//				BeanUtils.copyProperties(pwrctrl, persistPwrctrl,new String[]{"tmnlPwrctrlId","ctrlSchemeId", "floatValue"});
//			}
//			getHibernateTemplate().update(persistPwrctrl);
//			
//			if(detail!=null && detail.length>0){//时段控投入时，对明细没有修改，此参数有可能为空
//				String updateHql  = "from WTmnlPwrctrlDetail d where d.id.tmnlPwrctrlId = ?";
//				List<WTmnlPwrctrlDetail> detailList = getHibernateTemplate().find(updateHql, persistPwrctrl.getTmnlPwrctrlId());
//				if(detailList!=null && detailList.size()>0){
//					for (int i = 0; i < detailList.size(); i++) {
//						WTmnlPwrctrlDetail d = detailList.get(i);
//						getHibernateTemplate().delete(d);
//					}
//				}
//				
//				for (int i = 0; i < detail.length; i++) {
//					if(detail[i]!=null && detail[i].getId() != null){
//						detail[i].getId().setTmnlPwrctrlId(persistPwrctrl.getTmnlPwrctrlId());
//						wTmnlPwrctrlDetailDao.save(detail[i]);
//					}
//				}
//			}
//		} else {//未查到记录，则插入记录
		getHibernateTemplate().save(pwrctrl);
		getHibernateTemplate().flush();
		Long pwrctrlId = pwrctrl.getTmnlPwrctrlId();
		if(pwrctrlId!=null && detail!=null && detail.length>0) {
			for (int i = 0; i < detail.length; i++) {
				if(detail[i]!=null && detail[i].getId() != null){
					detail[i].getId().setTmnlPwrctrlId(pwrctrlId);
					wTmnlPwrctrlDetailDao.save(detail[i]);
				}
			}
		}
//			}
//		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteBySchemeId(Long ctrlSchemeId) {
		String hql = "from WTmnlPwrctrl w where w.ctrlSchemeId =?";
		//按方案ID查询方案明细列表
		List<WTmnlPwrctrl> pwrList = getHibernateTemplate().find(hql,ctrlSchemeId);
		
		if(pwrList==null || pwrList.size()<=0) {
			return;
		}
		for (int i = 0; i < pwrList.size(); i++) {//循环时段控曲线，分别删除曲线明细
			WTmnlPwrctrl pwr = pwrList.get(i);
			String dhql = "from WTmnlPwrctrlDetail w where w.id.tmnlPwrctrlId =?";
			List<WTmnlPwrctrl> pwrDetailList = getHibernateTemplate().find(dhql,pwr.getTmnlPwrctrlId());
			getHibernateTemplate().deleteAll(pwrDetailList);
		}
		getHibernateTemplate().deleteAll(pwrList);//删除明细曲线
	}
}
