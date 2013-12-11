package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WFactctrlTemplate;
import com.nari.util.exception.DBAccessException;

/**
 * 厂休控模板Service
 * @author 杨传文
 */
public interface WFactctrlTemplateManager {
	
	/**
	 * 保存功率厂休控模板信息
	 * @param template 功率厂休控模板
	 */
	public void save(WFactctrlTemplate template) throws DBAccessException ;
	
	/**
	 * 修改功率厂休控模板信息
	 * @param template 功率厂休控模板
	 * @param pwrctrlTemplateDetail 功率时段控模板明细
	 */
	public void update(WFactctrlTemplate template) throws DBAccessException ;
	
	/**
	 * 按模板名称查询模板
	 * @param templateName 模板名称
	 * @return 功率厂休控模板
	 */
	public WFactctrlTemplate findBy(String templateName) throws DBAccessException ;
	
	/**
	 * 按模板名称检查是否存在
	 * @param templateName
	 * @return true 存在，false 不存在
	 */
	public boolean isExistByTemplateName(String templateName) throws DBAccessException ;

	/**
	 * 查询当前操作员的所有模板
	 * @param staffNo 
	 * @return 所有模板
	 * @throws DBAccessException
	 */
	public List<WFactctrlTemplate> findAll(String staffNo) throws DBAccessException ;
}
