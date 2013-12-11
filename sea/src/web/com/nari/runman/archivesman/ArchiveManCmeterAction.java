package com.nari.runman.archivesman;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.CmeterJdbc;
import com.nari.runcontrol.RcpRunJdbc;

/**
 * Action 方法 ArchiveManCmeterAction
 * 
 * @author zhangzhw
 * @describe 档案管理电能表管理
 */
public class ArchiveManCmeterAction extends BaseAction {

	// 注入类
	IArchiveCmeterManManager iArchiveCmeterManManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	public String consNo;
	public String cmeterStore;

	public List<CmeterJdbc> cmeterList;

	/**
	 * ACTION 方法　queryCmeter
	 * 
	 * @return　通过consNo查询的Cmeter 列表
	 * @throws Exception
	 */
	public String queryCmeter() throws Exception {

		setCmeterList(iArchiveCmeterManManager.queryCmeterByConsNo(getConsNo()));
		return SUCCESS;
	}

	public String saveCmeter() throws Exception {

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(CmeterJdbc.class);

		JSONArray jsonArray = JSONArray.fromObject(this.cmeterStore);
		CmeterJdbc[] cmeterJdbc = (CmeterJdbc[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);

		iArchiveCmeterManManager.saveCmeter(cmeterJdbc);

		return SUCCESS;
	}

	// getters and setters
	@JSON(serialize = false)
	public IArchiveCmeterManManager getiArchiveCmeterManManager() {
		return iArchiveCmeterManManager;
	}

	public void setiArchiveCmeterManManager(
			IArchiveCmeterManManager iArchiveCmeterManManager) {
		this.iArchiveCmeterManManager = iArchiveCmeterManManager;
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

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getCmeterStore() {
		return cmeterStore;
	}

	public void setCmeterStore(String cmeterStore) {
		this.cmeterStore = cmeterStore;
	}

	public List<CmeterJdbc> getCmeterList() {
		return cmeterList;
	}

	public void setCmeterList(List<CmeterJdbc> cmeterList) {
		this.cmeterList = cmeterList;
	}

}
