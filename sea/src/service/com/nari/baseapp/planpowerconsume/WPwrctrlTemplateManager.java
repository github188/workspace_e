package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WPwrctrlTemplate;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.util.exception.DBAccessException;

/**
 * 功率时段控模板信息Service
 * @author 杨传文
 */
public interface WPwrctrlTemplateManager {
	/**
	 * 保存率时段控模板信息及其明细
	 * @param pwrctrlTemplate 功率时段控模板
	 * @param pwrctrlTemplateDetail 功率时段控模板明细
	 */
	public void save(WPwrctrlTemplate template,
			WPwrctrlTemplateDetailId[] detailId)
			throws DBAccessException;
	/**
	 * 修改功率时段控模板信息及其明细
	 * @param pwrctrlTemplate 功率时段控模板
	 * @param pwrctrlTemplateDetail 功率时段控模板明细
	 */
	public void update(WPwrctrlTemplate template,
			WPwrctrlTemplateDetailId[] detailId)
			throws DBAccessException;
	
	/**
	 * 按模板名称查询模板
	 * @param templateName 模板名称
	 * @return 功率时段控模板
	 */
	public WPwrctrlTemplate findWPwrctrlTemplateByName(String templateName)
			throws DBAccessException;
	
	/**
	 * 按模板名称查询模板明细列表
	 * @param templateName 模板名称
	 * @return  模板明细列表
	 */
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailByTemplateName(String templateName)
			throws DBAccessException;
	
	/**
	 * 按模板名称检查是否存在
	 * @param templateName
	 * @return true 存在，false 不存在
	 */
	public boolean isExistByTemplateName(String templateName) throws DBAccessException;
	
	/**
	 * 查询所有模板
	 * @param staffNo 
	 * @return 所有模板
	 * @throws DBAccessException
	 */
	public List<WPwrctrlTemplate> findAllTemplate(String staffNo) throws DBAccessException;
	
	/**
	 * 按模板ID查询模板明细列表
	 * @param templateName 模板名称
	 * @return  模板明细列表
	 */
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailByTemplateId(
			Long templateId) throws DBAccessException;
}
