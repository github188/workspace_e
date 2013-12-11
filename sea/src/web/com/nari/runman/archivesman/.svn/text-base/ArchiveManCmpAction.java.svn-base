package com.nari.runman.archivesman;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.CmpJdbc;

/**
 * Action 方法 ArchiveManCmpAction
 * 
 * @author zhangzhw
 * @describe 档案管理采计量点管理
 */
public class ArchiveManCmpAction extends BaseAction {

	// 注入类
	IArchiveCmpManManager iArchiveCmpManManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 前台参数
	public String consNo;
	public String cmpStore;

	// 返回结果
	List<CmpJdbc> cmpList;

	/**
	 * ACTION 方法
	 * 
	 * @return 通过consNo查询Cmp列表
	 * @throws Exception
	 */
	public String queryCmp() throws Exception {
		setCmpList(iArchiveCmpManManager.queryCmpByConsNo(getConsNo()));
		return SUCCESS;
	}

	/**
	 * ACTION 方法 saveCmp
	 * 
	 * @return 保存计量点列表
	 * @throws Exception
	 */
	public String saveCmp() throws Exception {
		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(CmpJdbc.class);

		JSONArray jsonArray = JSONArray.fromObject(this.cmpStore);
		CmpJdbc[] cmpJdbc = (CmpJdbc[]) JSONSerializer.toJava(jsonArray,
				jsonConfig);

		iArchiveCmpManManager.saveCmp(cmpJdbc);

		return SUCCESS;
	}

	// getters and setters
	@JSON(serialize = false)
	public IArchiveCmpManManager getiArchiveCmpManManager() {
		return iArchiveCmpManManager;
	}

	public void setiArchiveCmpManManager(
			IArchiveCmpManManager iArchiveCmpManManager) {
		this.iArchiveCmpManManager = iArchiveCmpManManager;
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

	public String getCmpStore() {
		return cmpStore;
	}

	public void setCmpStore(String cmpStore) {
		this.cmpStore = cmpStore;
	}

	public List<CmpJdbc> getCmpList() {
		return cmpList;
	}

	public void setCmpList(List<CmpJdbc> cmpList) {
		this.cmpList = cmpList;
	}

}
