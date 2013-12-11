package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.WFactctrlTemplateDao;
import com.nari.baseapp.planpowerconsume.WFactctrlTemplateManager;
import com.nari.orderlypower.WFactctrlTemplate;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class WFactctrlTemplateManagerImpl implements WFactctrlTemplateManager {

	private WFactctrlTemplateDao wFactctrlTemplateDao;
	
	public void setwFactctrlTemplateDao(WFactctrlTemplateDao factctrlTemplateDao) {
		wFactctrlTemplateDao = factctrlTemplateDao;
	}

	@Override
	public List<WFactctrlTemplate> findAll(String staff) throws DBAccessException {
		try{
			return wFactctrlTemplateDao.findAll(staff);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public WFactctrlTemplate findBy(String templateName)
			throws DBAccessException {
		try{
			List<WFactctrlTemplate> tmp = wFactctrlTemplateDao.findBy("templateName", templateName);
			if(tmp == null || tmp.size() == 0) {
				return null;
			}
			return tmp.get(0);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public boolean isExistByTemplateName(String templateName)
			throws DBAccessException {
		try{
			return wFactctrlTemplateDao.isExistByTemplateName(templateName);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public void save(WFactctrlTemplate template) throws DBAccessException {
		try{
			wFactctrlTemplateDao.save(template);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public void update(WFactctrlTemplate template) throws DBAccessException {
		try{
			wFactctrlTemplateDao.update(template);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
