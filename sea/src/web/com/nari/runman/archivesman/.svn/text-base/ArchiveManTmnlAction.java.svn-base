package com.nari.runman.archivesman;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;

/**
 * Action 类　ArchiveManTmnlAction
 * 
 * @author zhangzhw 档案管理　终端管理　Action
 */
public class ArchiveManTmnlAction extends BaseAction {

	// 注入类
	IArchiveTmnlManManager iArchiveTmnlManManager;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 前台参数
	public String consNo;
	public String tmnlStore;

	// 返回值
	public List<RtmnlRunJdbc> list;
	public List<RcpParaJdbc> paramlist;

	

	/**
	 * 方法 queryTmnls
	 * 
	 * @return 查询终端
	 */
	public String queryTmnls() throws Exception {

		setList(iArchiveTmnlManManager.queryTmnlByConsNo(getConsNo()));
		return SUCCESS;
	}

	/**
	 * Action 方法 saveTmnls
	 * 
	 * @return 保存tmnl列表
	 * @throws Exception
	 */
	public String saveTmnls() throws Exception {
		
		//--------------以下为终端信息---------------//
		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(RtmnlRunJdbc.class);

		JSONArray jsonArray = JSONArray.fromObject(this.tmnlStore);
		RtmnlRunJdbc[] rtmnlRunJdbc = (RtmnlRunJdbc[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);

		
		//------------以下为终端参数------------------//
		String tmnlParams = super.getRequest().getParameter("tmnlParams");
		
		if(null!=tmnlParams && !tmnlParams.trim().equals("")){
			tmnlParams="["+tmnlParams+"]";
			jsonConfig.setRootClass(RcpParaJdbc.class);
			JSONArray jsonArray1 = JSONArray.fromObject(tmnlParams);
			RcpParaJdbc[] rcpParaJdbc = (RcpParaJdbc[]) JSONSerializer.toJava(
					jsonArray1, jsonConfig);
			
			iArchiveTmnlManManager.saveTmnls(this.consNo,rtmnlRunJdbc,rcpParaJdbc);
		}else{
			iArchiveTmnlManManager.saveTmnls(this.consNo,rtmnlRunJdbc,null);
		}
		return SUCCESS;
	}

	/**
	 * 根据终端地址信息查询终端参数信息
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlParams() throws Exception{
		String tmnladdr = super.getRequest().getParameter("taddr");
		paramlist= iArchiveTmnlManManager.queryTmnlParams(tmnladdr);
		return SUCCESS;
	}
	
	/**
	 * 删除终端信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public String deleteTmnl() throws Exception{
		String tmnladdr = super.getRequest().getParameter("tmnladdr");
		if(null==tmnladdr && tmnladdr.trim().equals("")){
			this.setSuccess(false);
			Errors er = new Errors();
			er.setMsg("删除终端["+tmnladdr+"]失败！");
			er.setTitle("警告");
		}else{
			if(iArchiveTmnlManManager.deleteTmnl(tmnladdr)){
				this.setSuccess(true);
			}else{
				this.setSuccess(false);
				Errors er = new Errors();
				er.setMsg("删除终端["+tmnladdr+"]失败！");
				er.setTitle("警告");
			}
		}
		return SUCCESS;
	}
	
	
	// getters and setters
	@JSON(serialize = false)
	public IArchiveTmnlManManager getiArchiveTmnlManManager() {
		return iArchiveTmnlManManager;
	}

	public void setiArchiveTmnlManManager(
			IArchiveTmnlManManager iArchiveTmnlManManager) {
		this.iArchiveTmnlManManager = iArchiveTmnlManManager;
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

	public List<RtmnlRunJdbc> getList() {
		return list;
	}

	public void setList(List<RtmnlRunJdbc> list) {
		this.list = list;
	}

	public String getTmnlStore() {
		return tmnlStore;
	}

	public void setTmnlStore(String tmnlStore) {
		this.tmnlStore = tmnlStore;
	}

	public List<RcpParaJdbc> getParamlist() {
		return paramlist;
	}

	public void setParamlist(List<RcpParaJdbc> paramlist) {
		this.paramlist = paramlist;
	}
}
