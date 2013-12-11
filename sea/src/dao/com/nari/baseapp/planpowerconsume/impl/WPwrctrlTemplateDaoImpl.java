package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nari.baseapp.planpowerconsume.PowerCtrlQueryDao;
import com.nari.baseapp.planpowerconsume.WPwrctrlTemplateDao;
import com.nari.orderlypower.WPwrctrlTemplate;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.util.exception.DBAccessException;

public class WPwrctrlTemplateDaoImpl extends HibernateDaoSupport implements
		WPwrctrlTemplateDao {

	private PowerCtrlQueryDao powerCtrlQueryDao;
	
	public PowerCtrlQueryDao getPowerCtrlQueryDao() {
		return powerCtrlQueryDao;
	}

	public void setPowerCtrlQueryDao(PowerCtrlQueryDao powerCtrlQueryDao) {
		this.powerCtrlQueryDao = powerCtrlQueryDao;
	}

	@Override
	public void save(WPwrctrlTemplate pwrctrlTemplate,
			WPwrctrlTemplateDetail[] pwrctrlTemplateDetail) {
		if(pwrctrlTemplate == null || pwrctrlTemplateDetail == null || pwrctrlTemplateDetail.length==0){
			return;
		}
//		时段控模板父表
		getHibernateTemplate().save(pwrctrlTemplate);
		Long templateId = pwrctrlTemplate.getTemplateId();
//		时段控模板明细
		for (int i = 0; i < pwrctrlTemplateDetail.length; i++) {
			if(pwrctrlTemplateDetail[i] == null){
				continue;
			}
			pwrctrlTemplateDetail[i].getId().setTemplateId(templateId);
			if(pwrctrlTemplateDetail[i].getId().getConst1()==null){
				pwrctrlTemplateDetail[i].getId().setConst1(0F);
			}
			if(pwrctrlTemplateDetail[i].getId().getConst2()==null){
				pwrctrlTemplateDetail[i].getId().setConst2(0F);
			}
			if(pwrctrlTemplateDetail[i].getId().getConst3()==null){
				pwrctrlTemplateDetail[i].getId().setConst3(0F);
			}
			getHibernateTemplate().save(pwrctrlTemplateDetail[i]);
		}
	}

	@Override
	public void update(WPwrctrlTemplate pwrctrlTemplate,
			WPwrctrlTemplateDetail[] pwrctrlTemplateDetail)throws DBAccessException{
		if(pwrctrlTemplate == null || pwrctrlTemplateDetail == null || pwrctrlTemplateDetail.length==0){
			return;
		}
		try {
//			修改时段控模板
			WPwrctrlTemplate tmp = findWPwrctrlTemplateById(pwrctrlTemplate.getTemplateId());
			if(null==tmp)
				return;
			tmp.setTemplateName(pwrctrlTemplate.getTemplateName());
			tmp.setIsExec(pwrctrlTemplate.getIsExec());
			tmp.setFloatValue(pwrctrlTemplate.getFloatValue());
			
	//		修改时段控模板明细
			List<WPwrctrlTemplateDetail> detailList = null;
		
			detailList = powerCtrlQueryDao.findWPwrctrlTemplateDetailBytemplateId(tmp.getTemplateId());
			System.out.println("detailList.size====="+detailList.size());
			if(detailList!=null && detailList.size()>0){
				for (int i = 0; i < detailList.size(); i++) {
					getHibernateTemplate().delete(detailList.get(i));
					getHibernateTemplate().flush();
				}
			}
		
			for (int i = 0 ; i< pwrctrlTemplateDetail.length; i++) {
				WPwrctrlTemplateDetailId realId = pwrctrlTemplateDetail[i]==null?null:pwrctrlTemplateDetail[i].getId();
				if(realId == null) {
					continue;
				} else {
					realId.setTemplateId(tmp.getTemplateId());
					if(realId.getConst1()==null){
						realId.setConst1(0F);
					}
					if(realId.getConst2()==null){
						realId.setConst2(0F);
					}
					if(realId.getConst3()==null){
						realId.setConst3(0F);
					}
					getHibernateTemplate().save(pwrctrlTemplateDetail[i]);
				}
			}
			getHibernateTemplate().update(tmp);
		} catch (DBAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 按模板Id查询模板
	 * @param templateId模板Id
	 * @return 功率时段控模板
	 */
	@SuppressWarnings("unchecked")
	public WPwrctrlTemplate findWPwrctrlTemplateById(Long templateId)throws DBAccessException{
		try{
			String hql = "from WPwrctrlTemplate t where t.templateId =?";
			List<WPwrctrlTemplate> list = getHibernateTemplate().find(hql,templateId);
			if(list == null || list.size() == 0) {
				return null;
			}
			return list.get(0);
		}catch(RuntimeException e){
			this.logger.debug("按Id查询模板出错！");
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public WPwrctrlTemplate findWPwrctrlTemplateByName(String templateName) {
		String hql = "from WPwrctrlTemplate t where t.templateName =?";
		List<WPwrctrlTemplate> list = getHibernateTemplate().find(hql,templateName);
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailBytemplateName(
			String templateName) {
		WPwrctrlTemplate tmp = findWPwrctrlTemplateByName(templateName);
		if(tmp == null) {
			return null;
		}
		try {
			return powerCtrlQueryDao.findWPwrctrlTemplateDetailBytemplateId(tmp.getTemplateId());
		} catch (DBAccessException e) {
			e.printStackTrace();
			return new ArrayList<WPwrctrlTemplateDetail>();
		}
	}

	@Override
	public boolean isExistByTemplateName(String templateName) {
		String hql = "from WPwrctrlTemplate t where t.templateName=?";
		if(getHibernateTemplate().find(hql,templateName).size() == 0){
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WPwrctrlTemplate> findAllTemplate(String staffNo) {
		String hql = "from WPwrctrlTemplate t where t.staffNo = ?";
		return getHibernateTemplate().find(hql,staffNo);
	}
}
