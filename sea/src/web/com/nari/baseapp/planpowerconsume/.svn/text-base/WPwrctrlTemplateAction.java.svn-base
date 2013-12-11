package com.nari.baseapp.planpowerconsume;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.orderlypower.WPwrctrlTemplate;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.privilige.PSysUser;
import com.opensymphony.xwork2.ActionContext;

public class WPwrctrlTemplateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(WPwrctrlTemplateAction.class);

	private WPwrctrlTemplateManager wPwrctrlTemplateManager;

	// 返回值
	private boolean success = true;

	private boolean exist;
	private String templateName;
	private Long templateId;
	private List<WPwrctrlTemplate> templateList;
	private List<WPwrctrlTemplateDetail> detailList;
	private WPwrctrlTemplate template;
	private WPwrctrlTemplateDetailId id1;
	private WPwrctrlTemplateDetailId id2;
	private WPwrctrlTemplateDetailId id3;
	private WPwrctrlTemplateDetailId id4;
	private WPwrctrlTemplateDetailId id5;
	private WPwrctrlTemplateDetailId id6;
	private WPwrctrlTemplateDetailId id7;
	private WPwrctrlTemplateDetailId id8;

	/**
	 * 保存一个功率控制模板及其明细信息
	 * 
	 * @throws Exception
	 */
	public String saveWPwrctrlTemplate() throws Exception {
		logger.debug("功率时段控参数模板保存开始");
		WPwrctrlTemplateDetailId[] detailId = { id1, id2, id3, id4, id5, id6,
				id7, id8 };
		for (int i = 0; i < detailId.length; i++) {
			if("0:0".equals(detailId[i].getSectionEnd())){
				detailId[i]=null;
			}
		}
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		template.setStaffNo(staff.getStaffNo());
		
		wPwrctrlTemplateManager.save(template, detailId);
		logger.debug("功率时段控参数模板保存结束");
		return SUCCESS;
	}
	
	/**
	 * 修改一个功率控制模板及其明细信息
	 * 
	 * @throws Exception
	 */
	public String updateWPwrctrlTemplate() throws Exception {
		logger.debug("功率时段控参数模板修改开始");
		WPwrctrlTemplateDetailId[] detailId = { id1, id2, id3, id4, id5, id6,
				id7, id8 };
		for (int i = 0; i < detailId.length; i++) {
			if("0:0".equals(detailId[i].getSectionEnd())){
				detailId[i]=null;
			}
		}
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		template.setStaffNo(staff.getStaffNo());
		wPwrctrlTemplateManager.update(template, detailId);
		logger.debug("功率时段控参数模板修改结束");
		return SUCCESS;
	}
	
	/**
	 * 校验模板名称是否已存在
	 * 
	 * @throws Exception
	 */
	public String queryTemplateName() throws Exception {
		exist = wPwrctrlTemplateManager.isExistByTemplateName(templateName);
		return SUCCESS;
	}

	/**
	 * 查询当前操作员所有模板，填充模板名称下拉框
	 * @return
	 * @throws Exception
	 */
	public String queryTemplateList() throws Exception {
		PSysUser staff = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(staff == null ) {
			return ERROR;
		}
		templateList = wPwrctrlTemplateManager.findAllTemplate(staff.getStaffNo());
		return SUCCESS;
	}

	/**
	 * 修改模板时带出原模板信息
	 * @return
	 * @throws Exception
	 */
	public String queryTemplateDetails() throws Exception {
		detailList = wPwrctrlTemplateManager.findWPwrctrlTemplateDetailByTemplateId(templateId);
		return SUCCESS;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public WPwrctrlTemplate getTemplate() {
		return template;
	}

	public void setTemplate(WPwrctrlTemplate template) {
		this.template = template;
	}

	public WPwrctrlTemplateDetailId getId1() {
		return id1;
	}

	public void setId1(WPwrctrlTemplateDetailId id1) {
		this.id1 = id1;
	}

	public WPwrctrlTemplateDetailId getId2() {
		return id2;
	}

	public void setId2(WPwrctrlTemplateDetailId id2) {
		this.id2 = id2;
	}

	public WPwrctrlTemplateDetailId getId3() {
		return id3;
	}

	public void setId3(WPwrctrlTemplateDetailId id3) {
		this.id3 = id3;
	}

	public WPwrctrlTemplateDetailId getId4() {
		return id4;
	}

	public void setId4(WPwrctrlTemplateDetailId id4) {
		this.id4 = id4;
	}

	public WPwrctrlTemplateDetailId getId5() {
		return id5;
	}

	public void setId5(WPwrctrlTemplateDetailId id5) {
		this.id5 = id5;
	}

	public WPwrctrlTemplateDetailId getId6() {
		return id6;
	}

	public void setId6(WPwrctrlTemplateDetailId id6) {
		this.id6 = id6;
	}

	public WPwrctrlTemplateDetailId getId7() {
		return id7;
	}

	public void setId7(WPwrctrlTemplateDetailId id7) {
		this.id7 = id7;
	}

	public WPwrctrlTemplateDetailId getId8() {
		return id8;
	}

	public void setId8(WPwrctrlTemplateDetailId id8) {
		this.id8 = id8;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	@JSON(serialize = false)
	public WPwrctrlTemplateManager getwPwrctrlTemplateManager() {
		return wPwrctrlTemplateManager;
	}

	public void setwPwrctrlTemplateManager(
			WPwrctrlTemplateManager pwrctrlTemplateManager) {
		wPwrctrlTemplateManager = pwrctrlTemplateManager;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<WPwrctrlTemplate> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<WPwrctrlTemplate> templateList) {
		this.templateList = templateList;
	}

	public List<WPwrctrlTemplateDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<WPwrctrlTemplateDetail> detailList) {
		this.detailList = detailList;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
}
