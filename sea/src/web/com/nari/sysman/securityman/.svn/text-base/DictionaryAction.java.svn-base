package com.nari.sysman.securityman;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;

public class DictionaryAction extends BaseAction {

	// 返回结果
	public boolean success = true;
	public Errors errors;

	// 前台参数
	public String sql;

	// 返回结果
	@SuppressWarnings("unchecked")
	public List dicList;

	// 注入参数
	public IDictionaryManager dictionaryManager;

	/**
	 * Action 方法 publicDictionary
	 * @return 取得字典表列表
	 */
	public String publicDictionary() {
		this.setDicList(dictionaryManager.getDictionary(getSql()));
		return SUCCESS;
	}

	// Getters and Setters
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

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@SuppressWarnings("unchecked")
	public List getDicList() {
		return dicList;
	}

	@SuppressWarnings("unchecked")
	public void setDicList(List dicList) {
		this.dicList = dicList;
	}

	@JSON(serialize = false)
	public IDictionaryManager getDictionaryManager() {
		return dictionaryManager;
	}

	public void setDictionaryManager(IDictionaryManager dictionaryManager) {
		this.dictionaryManager = dictionaryManager;
	}

}
