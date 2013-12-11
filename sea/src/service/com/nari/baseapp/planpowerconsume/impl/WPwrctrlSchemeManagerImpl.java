package com.nari.baseapp.planpowerconsume.impl;

import java.util.List;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.PowerCtrlDao;
import com.nari.baseapp.planpowerconsume.WCtrlSchemeSectionDao;
import com.nari.baseapp.planpowerconsume.WFloatDownCtrlDao;
import com.nari.baseapp.planpowerconsume.WPwrctrlSchemeManager;
import com.nari.baseapp.planpowerconsume.WTmnlFactctrlDao;
import com.nari.baseapp.planpowerconsume.WTmnlPwrctrlDao;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WCtrlSchemeSection;
import com.nari.orderlypower.WCtrlSchemeSectionId;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

public class WPwrctrlSchemeManagerImpl implements WPwrctrlSchemeManager {
	private IWCtrlSchemeDao iWCtrlSchemeDao;
	private WCtrlSchemeSectionDao wCtrlSchemeSectionDao;
	private WTmnlPwrctrlDao wTmnlPwrctrlDao;
	private WTmnlFactctrlDao wTmnlFactctrlDao;
	private WFloatDownCtrlDao wFloatDownCtrlDao;
	private PowerCtrlDao powerCtrlDao;
	
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao ctrlSchemeDao) {
		iWCtrlSchemeDao = ctrlSchemeDao;
	}

	public void setwCtrlSchemeSectionDao(WCtrlSchemeSectionDao ctrlSchemeSectionDao) {
		wCtrlSchemeSectionDao = ctrlSchemeSectionDao;
	}

	public void setwTmnlPwrctrlDao(WTmnlPwrctrlDao tmnlPwrctrlDao) {
		wTmnlPwrctrlDao = tmnlPwrctrlDao;
	}

	public void setwTmnlFactctrlDao(WTmnlFactctrlDao tmnlFactctrlDao) {
		wTmnlFactctrlDao = tmnlFactctrlDao;
	}

	public void setwFloatDownCtrlDao(WFloatDownCtrlDao floatDownCtrlDao) {
		wFloatDownCtrlDao = floatDownCtrlDao;
	}

	public void setPowerCtrlDao(PowerCtrlDao powerCtrlDao) {
		this.powerCtrlDao = powerCtrlDao;
	}
	
	/**
	 * 保存时段控方案明细
	 * @param schemeId
	 * @param tmpId
	 * @param pwrctrlList
	 * @param detailList
	 * @throws DBAccessException
	 */
	public boolean savePeriodctrlScheme(Long schemeId, Long tmpId, List<WTmnlPwrctrl> pwrctrlList,
			List<WTmnlPwrctrlDetail[]> detailList) throws DBAccessException {
		//	若存在参数为空，则不做处理
		if(schemeId == null || pwrctrlList == null || pwrctrlList.size()==0 ||
				detailList == null || detailList.size() == 0) {
			return false;
		}

		//清除原有曲线和曲线明细
		wTmnlPwrctrlDao.deleteBySchemeId(schemeId);
		//清除原有模板方案关系
		powerCtrlDao.deleteBySchemeId(schemeId);
		
		//保存模板方案关系
		WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
		id.setCtrlSchemeId(schemeId);
		id.setSectionTemplateId(tmpId);
		wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
		
		//保存曲线和曲线明细
		for (int i = 0; i < pwrctrlList.size(); i++) {
			WTmnlPwrctrl pwrctrl = pwrctrlList.get(i);
			pwrctrl.setCtrlSchemeId(schemeId);
			savePwrctrl(pwrctrl,detailList.get(i));
		}
		return true;
	}
	
	@Override
	public void savePwrctrlScheme(WCtrlScheme scheme, Long tmpId, List<WTmnlPwrctrl> pwrctrlList,
			List<WTmnlPwrctrlDetail[]> detailList) throws DBAccessException {
//		若存在参数为空，则不做处理
		if(scheme == null || pwrctrlList == null || pwrctrlList.size()==0 ||
				detailList == null || detailList.size() == 0) {
			return;
		}
		if(scheme.getCtrlSchemeId()==null && scheme.getCtrlSchemeName() != null) {
//			保存方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
//			保存模板方案关系
			WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
			id.setCtrlSchemeId(scheme.getCtrlSchemeId());
			id.setSectionTemplateId(tmpId);
			wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
			
//			保存曲线和曲线明细
			for (int i = 0; i < pwrctrlList.size(); i++) {
				WTmnlPwrctrl pwrctrl = pwrctrlList.get(i);
				pwrctrl.setCtrlSchemeId(scheme.getCtrlSchemeId());
				savePwrctrl(pwrctrl,detailList.get(i));
			}
		} else if(scheme.getCtrlSchemeId()!=null) {
			//清除原有曲线和曲线明细
			wTmnlPwrctrlDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			//清除原有模板方案关系
			powerCtrlDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			//修改方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
			//保存模板方案关系
			WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
			id.setCtrlSchemeId(scheme.getCtrlSchemeId());
			id.setSectionTemplateId(tmpId);
			wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
			
			//保存曲线和曲线明细
			for (int i = 0; i < pwrctrlList.size(); i++) {
				WTmnlPwrctrl pwrctrl = pwrctrlList.get(i);
				pwrctrl.setCtrlSchemeId(scheme.getCtrlSchemeId());
				savePwrctrl(pwrctrl,detailList.get(i));
			}
		}else {
			return;
		}
	}

	@Override
	public void savePwrctrl(WTmnlPwrctrl pwrctrl,
			WTmnlPwrctrlDetail[] details) throws DBAccessException {
//		保存曲线和曲线明细
			wTmnlPwrctrlDao.save(pwrctrl,details);
	}
	
    /**
     * 保存厂休控方案明细
     * @param schemeId
     * @param templateId
     * @param tmnlFactctrlList
     * @return
     * @throws DBAccessException
     */
	public boolean saveFactoryCtrlScheme(Long schemeId, Long templateId,
			List<WTmnlFactctrl> tmnlFactctrlList) throws DBAccessException {
//		若存在参数为空，则不做处理
		if(schemeId == null || tmnlFactctrlList == null || tmnlFactctrlList.size()==0) {
			return false;
		}
		//清除方案明细
		wTmnlFactctrlDao.deleteBySchemeId(schemeId);
		//清除模板方案关系
		powerCtrlDao.deleteBySchemeId(schemeId);

		
		//保存模板方案关系
		WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
		id.setCtrlSchemeId(schemeId);
		id.setSectionTemplateId(templateId);
		wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
		
		//保存方案明细
		for (int i = 0; i < tmnlFactctrlList.size(); i++) {
			WTmnlFactctrl factctrl = tmnlFactctrlList.get(i);
			factctrl.setCtrlSchemeId(schemeId);
			wTmnlFactctrlDao.save(factctrl);
		}
		return true;
	}
	
	@Override
	public void saveFactctrlScheme(WCtrlScheme scheme, Long templateId,
			List<WTmnlFactctrl> tmnlFactctrlList) throws DBAccessException {
//		若存在参数为空，则不做处理
		if(scheme == null || tmnlFactctrlList == null || tmnlFactctrlList.size()==0) {
			return;
		}
		if(scheme.getCtrlSchemeId()==null && scheme.getCtrlSchemeName()!=null) {
			//保存方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
			//保存模板方案关系
			WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
			id.setCtrlSchemeId(scheme.getCtrlSchemeId());
			id.setSectionTemplateId(templateId);
			wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
			
			//保存曲线和曲线明细
			for (int i = 0; i < tmnlFactctrlList.size(); i++) {
				WTmnlFactctrl factctrl = tmnlFactctrlList.get(i);
				factctrl.setCtrlSchemeId(scheme.getCtrlSchemeId());
				wTmnlFactctrlDao.save(factctrl);
			}
		} else if(scheme.getCtrlSchemeId() !=null) {
			//清除方案明细
			wTmnlFactctrlDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			//清除模板方案关系
			powerCtrlDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			//修改方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
			//保存模板方案关系
			WCtrlSchemeSectionId id = new WCtrlSchemeSectionId();
			id.setCtrlSchemeId(scheme.getCtrlSchemeId());
			id.setSectionTemplateId(templateId);
			wCtrlSchemeSectionDao.save(new WCtrlSchemeSection(id));
			
			//保存方案明细
			for (int i = 0; i < tmnlFactctrlList.size(); i++) {
				WTmnlFactctrl factctrl = tmnlFactctrlList.get(i);
				factctrl.setCtrlSchemeId(scheme.getCtrlSchemeId());
				wTmnlFactctrlDao.save(factctrl);
			}
		}
	}

	@Override
	public void saveFactctrl(WTmnlFactctrl tmnlFactctrl) throws DBAccessException {
//		保存曲线和曲线明细
		wTmnlFactctrlDao.save(tmnlFactctrl);
	}
	
	/**
	 * 保存下浮控方案明细
	 * @param schemeId
	 * @param tmnlFloatList
	 * @return
	 * @throws DBAccessException
	 */
	public boolean saveFloatCtrlScheme(Long schemeId, List<WFloatDownCtrl> tmnlFloatList) throws DBAccessException {
        //若存在参数为空，则不做处理
		if(schemeId == null || tmnlFloatList == null || tmnlFloatList.size()==0) {
			return false;
		}
		
		//清除此方案原有明细
		wFloatDownCtrlDao.deleteBySchemeId(schemeId);
		
		//保存曲线和曲线明细
		for (int i = 0; i < tmnlFloatList.size(); i++) {
			WFloatDownCtrl tmnlFloat = tmnlFloatList.get(i);
			tmnlFloat.setCtrlSchemeId(schemeId);
			wFloatDownCtrlDao.save(tmnlFloat);
		}
		return true;
	}
	
	@Override
	public void saveFloatScheme(WCtrlScheme scheme, List<WFloatDownCtrl> tmnlFloatList) throws DBAccessException {
//		若存在参数为空，则不做处理
		if(scheme == null || tmnlFloatList == null || tmnlFloatList.size()==0) {
			return;
		}
		if(scheme.getCtrlSchemeId() ==null && scheme.getCtrlSchemeName() !=null){
//			保存方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
//			保存曲线和曲线明细
			for (int i = 0; i < tmnlFloatList.size(); i++) {
				WFloatDownCtrl tmnlFloat = tmnlFloatList.get(i);
				tmnlFloat.setCtrlSchemeId(scheme.getCtrlSchemeId());
				wFloatDownCtrlDao.save(tmnlFloat);
			}
		} else if(scheme.getCtrlSchemeId() != null){
			//清除此方案原有明细
			wFloatDownCtrlDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			
//			修改方案
			iWCtrlSchemeDao.saveOrUpdate(scheme);
			
//			保存曲线和曲线明细
			for (int i = 0; i < tmnlFloatList.size(); i++) {
				WFloatDownCtrl tmnlFloat = tmnlFloatList.get(i);
				tmnlFloat.setCtrlSchemeId(scheme.getCtrlSchemeId());
				wFloatDownCtrlDao.save(tmnlFloat);
			}
		}
	}

	@Override
	public List<WTmnlFactctrl> queryTmnlFactctrlList(List<PropertyFilter> filters) throws DBAccessException {
		List<WTmnlFactctrl> factList = null;
		if(null != filters && 0 < filters.size()){
			factList = wTmnlFactctrlDao.findBy(filters);
		}
		return factList;
	}

	@Override
	public List<WFloatDownCtrl> queryFloatDownCtrlList(List<PropertyFilter> filters) throws DBAccessException {
		List<WFloatDownCtrl> floatDownList =null;
		if(null != filters && 0 < filters.size()){
			floatDownList =wFloatDownCtrlDao.findBy(filters);
		}
		return floatDownList;
	}

	@Override
	public List<WTmnlPwrctrl> queryTmnlPwrctrlList(List<PropertyFilter> filters) throws DBAccessException {
		List<WTmnlPwrctrl> pwrList = null;
		if(null != filters && 0 < filters.size()){
			pwrList = wTmnlPwrctrlDao.findBy(filters);
		}
		return pwrList;
	}

	@Override
	public void updateTmnlFactctrl(WTmnlFactctrl factctrl) throws DBAccessException{
			powerCtrlDao.updateTmnlFactctrl(factctrl);
	}

	@Override
	public void updateTmnlFloat(WFloatDownCtrl floatDown) throws DBAccessException {
			powerCtrlDao.updateTmnlFloat(floatDown);
	}

	@Override
	public void updateTmnlPwrctrl(WTmnlPwrctrl pwrctrl) throws DBAccessException {
			powerCtrlDao.updateTmnlPwrctrl(pwrctrl);
	}
}
