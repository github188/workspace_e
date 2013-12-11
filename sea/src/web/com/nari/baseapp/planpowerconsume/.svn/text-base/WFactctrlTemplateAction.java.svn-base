package com.nari.baseapp.planpowerconsume;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.orderlypower.WFactctrlTemplate;
import com.nari.privilige.PSysUser;
import com.opensymphony.xwork2.ActionContext;

public class WFactctrlTemplateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(WFactctrlTemplateAction.class);

	private WFactctrlTemplateManager wFactctrlTemplateManager;

	// 返回值
	private boolean success = true;

	private boolean exist;
	private String templateName;
	private WFactctrlTemplate template;
	private List<WFactctrlTemplate> templateList;
	
	/**
	 * 保存厂休控模板
	 * @return
	 * @throws Exception
	 */
	public String saveWFactctrlTemplate() throws Exception{
		logger.debug("保存厂休控模板开始");
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		template.setStaffNo(staff.getStaffNo());
		wFactctrlTemplateManager.save(template);
		logger.debug("保存厂休控模板结束");
		return SUCCESS;
	}

	/**
	 * 查询所有厂休控模板
	 * @return
	 * @throws Exception
	 */
	public String queryTemplateList() throws Exception {
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		templateList = wFactctrlTemplateManager.findAll(staff.getStaffNo());
		return SUCCESS;
	}
	
	/**
	 * 校验模板名称是否已存在
	 * 
	 * @throws Exception
	 */
	public String checkTemplateName() throws Exception {
		this.exist = this.wFactctrlTemplateManager.isExistByTemplateName(templateName);
		return SUCCESS;
	}
	
	/**
	 * 修改厂休控模板
	 * @return
	 * @throws Exception
	 */
	public String updateWFactctrlTemplate() throws Exception {
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		template.setStaffNo(staff.getStaffNo());
		wFactctrlTemplateManager.update(template);
		return SUCCESS;
	}
	//	getter and setter
	@JSON(serialize = false)
	public WFactctrlTemplateManager getwFactctrlTemplateManager() {
		return wFactctrlTemplateManager;
	}
	public void setwFactctrlTemplateManager(
			WFactctrlTemplateManager factctrlTemplateManager) {
		wFactctrlTemplateManager = factctrlTemplateManager;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isExist() {
		return exist;
	}
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public WFactctrlTemplate getTemplate() {
		return template;
	}

	public void setTemplate(WFactctrlTemplate template) {
		this.template = template;
	}

	public List<WFactctrlTemplate> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<WFactctrlTemplate> templateList) {
		this.templateList = templateList;
	}
}
