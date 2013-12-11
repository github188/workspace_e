package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.PowerCtrlQueryDao;
import com.nari.baseapp.planpowerconsume.WPwrctrlTemplateDao;
import com.nari.baseapp.planpowerconsume.WPwrctrlTemplateManager;
import com.nari.orderlypower.WPwrctrlTemplate;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

public class WPwrctrlTemplateManagerImpl implements WPwrctrlTemplateManager{
	
	private WPwrctrlTemplateDao wPwrctrlTemplateDao;
	private PowerCtrlQueryDao powerCtrlQueryDao;
	
	public void setwPwrctrlTemplateDao(WPwrctrlTemplateDao pwrctrlTemplateDao) {
		wPwrctrlTemplateDao = pwrctrlTemplateDao;
	}

	public void setPowerCtrlQueryDao(PowerCtrlQueryDao powerCtrlQueryDao) {
		this.powerCtrlQueryDao = powerCtrlQueryDao;
	}

	@Override
	public void save(WPwrctrlTemplate template, WPwrctrlTemplateDetailId[] detailId)
			throws DBAccessException {
		try {
			wPwrctrlTemplateDao.save(setTemplateParam(template), setTemplateDetailParam(detailId));
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public void update(WPwrctrlTemplate template, WPwrctrlTemplateDetailId[] detailId)
			throws DBAccessException {
		try {
			wPwrctrlTemplateDao.update(setTemplateParam(template), setTemplateDetailParam(detailId));
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public WPwrctrlTemplate findWPwrctrlTemplateByName(String templateName)
			throws DBAccessException {
		try{
			return wPwrctrlTemplateDao.findWPwrctrlTemplateByName(templateName);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailByTemplateName(
			String templateName) throws DBAccessException {
		try{
			return wPwrctrlTemplateDao.findWPwrctrlTemplateDetailBytemplateName(templateName);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailByTemplateId(
			Long templateId) throws DBAccessException{
		try{
			return powerCtrlQueryDao.findWPwrctrlTemplateDetailBytemplateId(templateId);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public boolean isExistByTemplateName(String templateName) throws DBAccessException{
		try{
			return wPwrctrlTemplateDao.isExistByTemplateName(templateName);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<WPwrctrlTemplate> findAllTemplate(String staffNo) throws DBAccessException {
		try{
			return wPwrctrlTemplateDao.findAllTemplate(staffNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	/**
	 * 时段控模板参数设置
	 * @param template
	 * @return 时段控模板
	 */
	public WPwrctrlTemplate setTemplateParam(WPwrctrlTemplate template){
		WPwrctrlTemplate pwrctrlTemplate = template;
		pwrctrlTemplate.setCreateDate(new Date());
		if(pwrctrlTemplate.getIsExec() == null){
			pwrctrlTemplate.setIsExec(false);
		} else {
			pwrctrlTemplate.setIsExec(true);
		}
		return pwrctrlTemplate;
	}
	
	/**
	 * 时段控模板明细参数设置
	 * @param detailId
	 * @return 时段控模板明细列表
	 * @throws DBAccessException 
	 */
	public WPwrctrlTemplateDetail[] setTemplateDetailParam(WPwrctrlTemplateDetailId[] detailId) throws DBAccessException {
		WPwrctrlTemplateDetail[] detailArray= new WPwrctrlTemplateDetail[8];
		for (int i = 0; i < detailId.length; i++) {
			WPwrctrlTemplateDetailId id = detailId[i];
			if(id==null) {
				continue;
			}
			if(id.getIsValid() == null) {
				id.setIsValid(false);
			} else {
				id.setIsValid(true);
			}
			WPwrctrlTemplateDetail detail = new WPwrctrlTemplateDetail();
			detail.setId(id);
			detailArray[i] = detail;
		}
		return detailArray;
	}
	
	/**
	 * 给时段排序
	 * @param ids
	 * @return
	 */
	public WPwrctrlTemplateDetailId[] sortPwrctrlTemplateDetailId(WPwrctrlTemplateDetailId[] ids) {
		for (int i = 0; i < ids.length; i++) {
			for (int j = i; j < ids.length; j++) {
				if(new Integer(ids[i].getSectionStart().substring(0, 2))>=new Integer(ids[j].getSectionStart().substring(0, 2))) {
					WPwrctrlTemplateDetailId id = ids[i];
					ids[i] = ids[j];
					ids[j] = id;
				}
			}
		}
		return ids;
	}
	
	/**
	 * 检查时段控时段，未将全天划分的自动补齐，若补齐后时段超过8段 则抛出异常
	 * @param ids
	 * @return
	 */
	public List<WPwrctrlTemplateDetailId> checkTemplateTime(WPwrctrlTemplateDetailId[] ids) throws DBAccessException{
		WPwrctrlTemplateDetailId[] res =  sortPwrctrlTemplateDetailId(ids);
		List<WPwrctrlTemplateDetailId> list = new ArrayList<WPwrctrlTemplateDetailId>();
		List<WPwrctrlTemplateDetailId> resList = new ArrayList<WPwrctrlTemplateDetailId>();
		for (int i = 0; i < res.length; i++) {
			if(!"00:00".equals(res[i].getSectionEnd())){
				list.add(res[i]);
			}
		}
		int size = list.size();
		for (int i = 0; i < list.size(); i++) {
			WPwrctrlTemplateDetailId id = list.get(i);
			resList.add(id);
			if(i ==0 ){
				if(!"00:00".equals(id.getSectionStart())){
					WPwrctrlTemplateDetailId idNew= new WPwrctrlTemplateDetailId();
					BeanUtils.copyProperties(id, idNew);
					idNew.setSectionStart("00:00");
					idNew.setIsValid(null);
					idNew.setSectionEnd(id.getSectionStart());
					idNew.setSectionNo(++size);
					resList.add(idNew);
				}else if(id.getSectionEnd().equals(list.get(i+1).getSectionStart()) || list.size()==1){
					continue;
				}else{
					WPwrctrlTemplateDetailId idNew= new WPwrctrlTemplateDetailId();
					BeanUtils.copyProperties(id, idNew);
					idNew.setSectionStart(id.getSectionEnd());
					idNew.setIsValid(null);
					idNew.setSectionEnd(list.get(i+1).getSectionStart());
					idNew.setSectionNo(++size);
					resList.add(idNew);
				}
			} else if (i== list.size()-1 && !"23:30".equals(id.getSectionEnd())){
				WPwrctrlTemplateDetailId idNew= new WPwrctrlTemplateDetailId();
				BeanUtils.copyProperties(id, idNew);
				idNew.setSectionStart(id.getSectionEnd());
				idNew.setIsValid(null);
				idNew.setSectionEnd("23:30");
				idNew.setSectionNo(++size);
				resList.add(idNew);
				break;
			} 
			if(id.getSectionEnd().equals(list.get(i+1).getSectionStart())){
				continue;
			}else{
				WPwrctrlTemplateDetailId idNew= new WPwrctrlTemplateDetailId();
				BeanUtils.copyProperties(id, idNew);
				idNew.setSectionStart(id.getSectionEnd());
				idNew.setIsValid(null);
				idNew.setSectionEnd(list.get(i+1).getSectionStart());
				idNew.setSectionNo(++size);
				resList.add(idNew);
			}
		}
//		排序
		Collections.sort(resList, new Comparator<WPwrctrlTemplateDetailId>(){
			@Override
			public int compare(WPwrctrlTemplateDetailId o1, WPwrctrlTemplateDetailId o2) {
				if(!o1.getSectionStart().substring(0, 2).equals(o2.getSectionStart().substring(0, 2))){
					return new Integer(o1.getSectionStart().substring(0, 2))-new Integer(o2.getSectionStart().substring(0, 2));
				}else{
					return new Integer(o1.getSectionStart().substring(3, 5))-new Integer(o2.getSectionStart().substring(3, 5));
				}
			}
		});
		if(resList.size()>8) {
			throw new DBAccessException(ExceptionConstants.POWER_SDK_OVERLENGTH);
		}
		return resList;
	}
}
