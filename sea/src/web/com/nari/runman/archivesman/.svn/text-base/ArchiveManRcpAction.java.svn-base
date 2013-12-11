package com.nari.runman.archivesman;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.util.StrutsUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.morph.integration.commons.beanutils.BeanUtilsConverter;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.cp.REODev;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runman.runstatusman.CMeterDto;
import com.nari.runman.runstatusman.RCollObj;
import com.nari.support.Page;

/**
 * Action 方法 ArchiveManRcpAction
 * 
 * @author zhangzhw
 * @describe 档案管理采集点管理
 */
public class ArchiveManRcpAction extends BaseAction {

	// 注入类
	IArchiveRcpManManager iArchiveRcpManManager;

	// 前台参数

	public String consNo;
	public String consId;

	public String rcpStore;
	public String rcpParaStore;

	// 返回确认
	public boolean success = true;
	public Errors errors;

	// 返回的RcpList和RcpCommParaList
	public List<RcpRunJdbc> rcpList;
	public List<RcpParaJdbc> rcpParaList;
	
	//分页控制
	//分类处理参数
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	
	
	
	public List<REODev> reoDevlist;
	public REODev reodev;
	public List<CMeterDto> cmeterlist;
	public String rcoid;//返回的采集对象标识
	

	

	/**
	 * Action 方法 queryRcp
	 * 
	 * @return 查询采集点的Action
	 */
	public String queryRcp() throws Exception {
		// 后台使用consId查询
		setRcpList(iArchiveRcpManManager.queryRcpByConsId(getConsId()));
		setRcpParaList(iArchiveRcpManManager.queryRcpParaByConsId(getConsId()));

		return SUCCESS;
	}
	
	/**
	 * 删除采集点信息
	 * @return
	 * @throws Exception
	 */
	public String deleteRcp() throws Exception{
		String cpno = super.getRequest().getParameter("cpNo");
		if(null!=cpno && !cpno.equals("")){
			if(!iArchiveRcpManManager.deleteRcp(cpno)){
				this.setSuccess(false);
				Errors er = new Errors();
				er.setMsg("删除采集点["+cpno+"]失败！请检查此采集点下是否含有关联数据！");
				er.setTitle("警告");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 删除采集器信息
	 * @return
	 * @throws Exception
	 */
	public String deleteREODev() throws Exception{
		String collId = super.getRequest().getParameter("collId");
		if(null!=collId && !collId.equals("")){
			if(!iArchiveRcpManManager.deleteREODev(collId)){
				this.setSuccess(false);
				Errors er = new Errors();
				er.setMsg("删除采集点["+collId+"]失败！");
				er.setTitle("警告");
			}
		}
		return SUCCESS;
	}
	
	
	/**
	 * ACTION 方法 saveRcps
	 * 
	 * @return 保存Rcp 和Rcp 参数
	 */
	public String saveRcps() throws Exception {

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(RcpRunJdbc.class);

		JSONArray jsonArray = JSONArray.fromObject(this.rcpStore);
		RcpRunJdbc[] rcpRunJdbc = (RcpRunJdbc[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);

		jsonConfig.setRootClass(RcpParaJdbc.class);
		JSONArray jsonArray1 = JSONArray.fromObject(this.rcpParaStore);
		RcpParaJdbc[] rcpParaJdbc = (RcpParaJdbc[]) JSONSerializer.toJava(
				jsonArray1, jsonConfig);

		//Boolean b = iArchiveRcpManManager.saveRcps(rcpRunJdbc, rcpParaJdbc,consId);
		Boolean b = iArchiveRcpManManager.saveRcps(rcpRunJdbc, consId);
		if (b) {// 保存成功后将新的查询结果返回前台
			setRcpList(iArchiveRcpManManager.queryRcpByConsId(getConsId()));
			setRcpParaList(iArchiveRcpManManager
					.queryRcpParaByConsId(getConsId()));
		} else {
			this.setSuccess(false);
			Errors er = new Errors();
			er.setMsg("保存采集点信息失败");
			er.setTitle("警告");
		}
		return SUCCESS;
	}

	// getters and setters
	// @JSON(serialize = false)
	// public IArchiveRcpManManager getiArchiveRcpManManager() {
	// return iArchiveRcpManManager;
	// }

	/**
	 * 根据采集点标识查询采集器信息
	 */
	public String queryREODev() throws Exception {
		String cpNo=super.getRequest().getParameter("cpNo");
		reoDevlist = iArchiveRcpManManager.queryREODev(cpNo);
		return SUCCESS;
	}
	
	/**
	 * 保存采集器信息
	 * @return
	 * @throws Exception
	 */
	public String saveREODev() throws Exception{
		if(null!=reodev ){
			
			if(!iArchiveRcpManManager.saveREODev(reodev)){
				this.setSuccess(false);
				Errors er = new Errors();
				er.setMsg("增加采集器失败！");
				er.setTitle("警告");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 修改采集器信息
	 * @return
	 * @throws Exception
	 */
	public String updateREODev() throws Exception{
		String reodevstr = super.getRequest().getParameter("reodevstr");
		
		if(null!=reodevstr && reodevstr.trim().length() > 0){
			JsonConfig jsonConfig = new JsonConfig();
			// json为数组的解析模式
			jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
			// 指定Json数据对应的javaBean对象class名
			jsonConfig.setRootClass(REODev.class);
	
			JSONArray jsonArray = JSONArray.fromObject("["+reodevstr+"]");
			REODev[] rEODev = (REODev[]) JSONSerializer.toJava(
					jsonArray, jsonConfig);
			
			iArchiveRcpManManager.updateREODev(rEODev);
		}else{
			this.setSuccess(false);
			Errors er = new Errors();
			er.setMsg("增加采集器失败！");
			er.setTitle("警告");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 查询已挂电能表信息
	 * @return
	 */
	public String queryCmeterIsHang() throws Exception {
		
		String cpNo = super.getRequest().getParameter("cpNo");
		//
		// = new 
		//cmeterlist
		Page<CMeterDto> pages= iArchiveRcpManManager.queryCmeterIsHang(cpNo,start,limit);
		if(null!=pages && pages.getTotalCount() > 0){
			cmeterlist = pages.getResult();
			totalCount = pages.getTotalCount();
		}
		return SUCCESS;
	}
	
	/**
	 * 新增电能表挂载信息
	 * @return
	 * @throws Exception
	 */
	public String saveCmeterHangInfo() throws Exception{
		String cpNo = super.getRequest().getParameter("cpNo");
		String meterid = super.getRequest().getParameter("meterid");
		if(null != cpNo && null != meterid){
			RCollObj rco = new RCollObj();
			rco.setCpNo(cpNo);
			rco.setMeterId(Long.parseLong((meterid)));
			this.rcoid = iArchiveRcpManManager.saveCmeterHangInfo(rco);
			if(null!=this.rcoid){
				this.setSuccess(true);
				return SUCCESS;
			}
		}
		this.setSuccess(false);
		Errors er = new Errors();
		er.setMsg("增加采集器失败！");
		er.setTitle("警告");
		return SUCCESS;
	}
	
	/**
	 * 查询待挂表信息
	 * @return
	 * @throws Exception
	 */
	public String queryCmeterNotHang() throws Exception{
		String cpNo = super.getRequest().getParameter("cpNo");
		Page<CMeterDto> pages = iArchiveRcpManManager.queryCmeterIsNotHang(cpNo,start,limit);
		if(null!=pages && pages.getTotalCount() > 0){
			cmeterlist = pages.getResult();
			this.totalCount = pages.getTotalCount();
		}
		return SUCCESS;
	}
	
	public String deleteCmeterHang() throws Exception{
		String collobjid = super.getRequest().getParameter("collobjid");
		if(null==collobjid || "".equals(collobjid.trim())){
			this.setSuccess(false);
		}else{
			iArchiveRcpManManager.deleteCmeterHang(collobjid);
			this.setSuccess(true);
		}
		return SUCCESS;
	}
	
	public List<CMeterDto> getCmeterlist() {
		return cmeterlist;
	}

	public void setCmeterlist(List<CMeterDto> cmeterlist) {
		this.cmeterlist = cmeterlist;
	}

	public void setiArchiveRcpManManager(
			IArchiveRcpManManager iArchiveRcpManManager) {
		this.iArchiveRcpManManager = iArchiveRcpManManager;
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

	public List<RcpRunJdbc> getRcpList() {
		return rcpList;
	}

	public void setRcpList(List<RcpRunJdbc> rcpList) {
		this.rcpList = rcpList;
	}

	public List<RcpParaJdbc> getRcpParaList() {
		return rcpParaList;
	}

	public void setRcpParaList(List<RcpParaJdbc> rcpParaList) {
		this.rcpParaList = rcpParaList;
	}

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId;
	}

	public String getRcpStore() {
		return rcpStore;
	}

	public void setRcpStore(String rcpStore) {
		this.rcpStore = rcpStore;
	}

	public String getRcpParaStore() {
		return rcpParaStore;
	}

	public void setRcpParaStore(String rcpParaStore) {
		this.rcpParaStore = rcpParaStore;
	}

	public List<REODev> getReoDevlist() {
		return reoDevlist;
	}
	public void setReoDevlist(List<REODev> reoDevlist) {
		this.reoDevlist = reoDevlist;
	}
	public REODev getReodev() {
		return reodev;
	}

	public void setReodev(REODev reodev) {
		this.reodev = reodev;
	}
	public String getRcoid() {
		return rcoid;
	}

	public void setRcoid(String rcoid) {
		this.rcoid = rcoid;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	
}
