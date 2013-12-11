package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.nari.baseapp.planpowerconsume.WFactctrlTemplateDao;
import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.orderlypower.WFactctrlTemplate;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

public class WFactctrlTemplateDaoImpl extends HibernateBaseDaoImpl<WFactctrlTemplate, Long>
		implements WFactctrlTemplateDao {

	@Override
	public boolean isExistByTemplateName(String templateName) {
		String hql = "from WFactctrlTemplate t where t.templateName=?";
		if(getHibernateTemplate().find(hql,templateName).size() == 0){
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(WFactctrlTemplate template) throws DBAccessException {
		String hql = "from WFactctrlTemplate t where t.templateId=?";
		List<WFactctrlTemplate> list = getHibernateTemplate().find(hql,template.getTemplateId());
		if(list == null || list.size()==0) {
			throw new DBAccessException(ExceptionConstants.DBE_UPDATEFAIL);
		}
		WFactctrlTemplate realTmp = list.get(0);
		BeanUtils.copyProperties(template, realTmp, new String[]{"templateId","createDate"});
		getHibernateTemplate().update(realTmp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WFactctrlTemplate> findAll(String staff)
			throws DBAccessException {
		String hql = "from WFactctrlTemplate t where t.staffNo = ?";
		return getHibernateTemplate().find(hql,staff);
	}
}
