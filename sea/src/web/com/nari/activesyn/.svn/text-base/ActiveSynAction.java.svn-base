package com.nari.activesyn;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;

/**
 * 同步档案Action
 * @author Taoshide
 *
 */
public class ActiveSynAction extends BaseAction {
	private DataManager dataManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;
	/**
	 * 同步
	 * @return
	 * @throws Exception
	 */
	public String Syn() throws Exception{
		String rquestType = super.getRequest().getParameter("consType");
		String requestValue = super.getRequest().getParameter("consValue");
		if(dataManager.synData(rquestType, requestValue)){
			this.setSuccess(true);
			Errors er = new Errors();
			er.setMsg("同步档案成功！");
			er.setTitle("提示");
		}else{
			this.setSuccess(false);
			Errors er = new Errors();
			er.setMsg("同步档案失败！");
			er.setTitle("提示");
		}
		return SUCCESS;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	
	@JSON(serialize = false)
	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}
}
