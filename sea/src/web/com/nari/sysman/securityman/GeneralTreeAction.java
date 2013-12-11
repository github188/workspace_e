package com.nari.sysman.securityman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.customer.CConsRtmnl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.CpTypeCode;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.util.TreeNode;

/**
 * 类 GeneralTreeAction
 * 
 * @author zhangzhw
 * @describe 通用左边树构造
 */
public class GeneralTreeAction extends BaseAction {
	// 前台参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	public String node;

	// 用户查询条件
	public String consName;
	public String consNo;
	public String terminalId;
	public String protocolCode;
	public String bureauNo;
	public String tmnlAssetNo;
	public String consType;
	public String tgId;
	public String tgNo;
	public String tgName;
	public String mrSectNo;
	public String meterAssetNo;
	public String terminalAddr;
	public String cisAssetNo;   //采集器资产号
	private String cpNo;
	private String cpName;
	private String cpAddr;
	private String cpTypeCode;
	private String subName;//台区名称
	private String lineName;
	

	// 返回的结果
	public boolean success = true;
	public Errors errors;
	public long totalCount;
	public List<CConsRtmnl> userGrid;
	public Page<TreeNode> pageInfo;
	public List<OOrg> bureauList;
	public List<VwProtocolCode> protocolList;
	private List<CpTypeCode> cpTypeCodeList;

	// spring 注入对象
	public IGeneralTreeManager iGeneralTreeManager;

	/**
	 * 
	 * @return 用户树数据
	 * @throws Exception
	 * 
	 */
	public String userTree() throws Exception {

		pageInfo = new Page<TreeNode>();
		List<TreeNode> list = new ArrayList<TreeNode>();

		for (long i = start; i < start + limit; i++) {
			TreeNode tn = new TreeNode();
			tn.setId(node + "" + i);
			tn.setName(node + "" + i);
			tn.setText(node + "" + i);
			if (node.length() > 5)
				tn.setLeaf(true);
			else
				tn.setLeaf(false);
			list.add(tn);

		}
		pageInfo.setResult(list);

		pageInfo.setTotalCount(100);

		setSuccess(true);
		return "success";
	}

	/**
	 * 
	 * @return 电网树数据
	 * @throws Exception
	 */
	public String netTree() throws Exception {
		PSysUser username = super.getPSysUser();
		pageInfo = iGeneralTreeManager.findGridTree(username, getNode(),
				getStart(), getLimit());
//		System.out.println("-----------电网树节点------------------");
//		for(TreeNode tn : pageInfo.getResult())
//		{
//			System.out.println(tn.getName());
//		}
		return "success";
		// return userTree();
	}

	/**
	 * 
	 * @return 行业树数据
	 * @throws Exception
	 */
	public String tradeTree() throws Exception {

		pageInfo = iGeneralTreeManager.findTradeTree(super.getPSysUser(),
				getNode(), getStart(), getLimit());
		setSuccess(true);
		return "success";
		// return userTree();
	}

	/**
	 * 
	 * @return 区域树数据
	 * @throws Exception
	 */
	public String areaTree() throws Exception {
		PSysUser username = (PSysUser) super.getSession().getAttribute(
				"pSysUser");
		System.out.println("---------------printNode----------");
		System.out.println(node);
		pageInfo = iGeneralTreeManager.findAreaTree(username, getNode(),
				getStart(), getLimit());
		setSuccess(true);
		return "success";
	}

	/**
	 * 
	 * @return 备选用户树数据
	 * @throws Exception
	 */
	public String backTree() throws Exception {
		PSysUser username = (PSysUser) super.getSession().getAttribute(
				"pSysUser");
		pageInfo = iGeneralTreeManager.findBackTree(username, getNode(),
				getStart(), getLimit());
		setSuccess(true);
		return "success";
	}

	/**
	 * 
	 * @return 控制用户树数据
	 * @throws Exception
	 */
	public String controlTree() throws Exception {
		PSysUser username = (PSysUser) super.getSession().getAttribute(
				"pSysUser");
		pageInfo = iGeneralTreeManager.findControlTree(username, getNode(),
				getStart(), getLimit());
		setSuccess(true);
		return "success";
	}

	/**
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public String userGrid() throws Exception {
		PSysUser pSysUser = (PSysUser) super.getSession().getAttribute(
				"pSysUser");

		Page<CConsRtmnl> pcr = iGeneralTreeManager.findUserGrid(pSysUser,
				start, limit, genUserGridBean());
		userGrid = pcr.getResult();
		totalCount = pcr.getTotalCount();
		return "success";
	}

	/**
	 * 
	 * @return 备选群组用户列表
	 * @throws Exception
	 */
	public String backUserGrid() throws Exception {
		PSysUser pSysUser = (PSysUser) super.getSession().getAttribute(
				"pSysUser");

		Page<CConsRtmnl> pcr = iGeneralTreeManager.findUserGrid(pSysUser,
				start, limit, genUserGridBean());
		userGrid = pcr.getResult();
		totalCount = pcr.getTotalCount();
		return "success";
	}

	/**
	 * 
	 * @return 控制群组用户列表
	 * @throws Exception
	 */
	public String controlUserGrid() throws Exception {
		PSysUser pSysUser = (PSysUser) super.getSession().getAttribute(
				"pSysUser");
		Page<CConsRtmnl> pcr = iGeneralTreeManager.findUserGrid(pSysUser,
				start, limit, genUserGridBean());
		userGrid = pcr.getResult();
		totalCount = pcr.getTotalCount();
		return "success";
	}

	/**
	 * 方法 bureauList
	 * 
	 * @return 该用户有权限的供电所列表
	 * @throws Exception
	 */
	public String bureauList() throws Exception {
		PSysUser username = super.getPSysUser();
		bureauList = iGeneralTreeManager.findBureauList(username);
		return "success";
	}

	/**
	 * 方法 protocolList
	 * 
	 * @return 所有规约列表
	 * @throws Exception
	 */
	public String protocolList() throws Exception {
		// String username = (String)
		// super.getSession().getAttribute("username");
		protocolList = iGeneralTreeManager.findProtocolList();
		return "success";
	}

	public String queryCpTypeCode() throws Exception{
		cpTypeCodeList = iGeneralTreeManager.getAllCpTypeCode();
		return "success";
	}
	
	/**
	 * 
	 * @return UserGridBean
	 */
	public UserGridBean genUserGridBean() {
		UserGridBean ugb = new UserGridBean();
		ugb.consName = consName;
		ugb.consNo = consNo;
		ugb.terminalId = terminalId;
		ugb.potocolCode = protocolCode;
		ugb.bureauNo = bureauNo;
		ugb.tmnlAssetNo = tmnlAssetNo;
		ugb.consType=consType;
		ugb.tgId=tgId;
		ugb.tgName=tgName;
		ugb.mrSectNo=mrSectNo;
		ugb.meterAssetNo=meterAssetNo;
		ugb.tgNo = tgNo;
		ugb.terminalAddr = terminalAddr;
		ugb.setCpNo(cpNo);
		ugb.setCpName(cpName);
		ugb.setCpAddr(cpAddr);
		ugb.setCpTypeCode(cpTypeCode);
		ugb.setSubName(subName);
		ugb.setCisAssetNo(cisAssetNo);
		ugb.setLineName(lineName);
		return ugb;

	}

	// getters and setters
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

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
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

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Page<TreeNode> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(Page<TreeNode> pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * @return the userGrid
	 */
	public List<CConsRtmnl> getUserGrid() {
		return userGrid;
	}

	/**
	 * @param userGrid
	 *            the userGrid to set
	 */
	public void setUserGrid(List<CConsRtmnl> userGrid) {
		this.userGrid = userGrid;
	}

	@JSON(serialize = false)
	public IGeneralTreeManager getiGeneralTreeManager() {
		return iGeneralTreeManager;
	}

	public void setiGeneralTreeManager(IGeneralTreeManager iGeneralTreeManager) {
		this.iGeneralTreeManager = iGeneralTreeManager;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getBureauNo() {
		return bureauNo;
	}

	public void setBureauNo(String bureauNo) {
		this.bureauNo = bureauNo;
	}

	public List<OOrg> getBureauList() {
		return bureauList;
	}

	public void setBureauList(List<OOrg> bureauList) {
		this.bureauList = bureauList;
	}

	public List<VwProtocolCode> getProtocolList() {
		return protocolList;
	}

	public void setProtocolList(List<VwProtocolCode> protocolList) {
		this.protocolList = protocolList;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public String getTgId() {
		return tgId;
	}

	public void setTgId(String tgId) {
		this.tgId = tgId;
	}

	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	public String getMrSectNo() {
		return mrSectNo;
	}

	public void setMrSectNo(String mrSectNo) {
		this.mrSectNo = mrSectNo;
	}

	public String getMeterAssetNo() {
		return meterAssetNo;
	}

	public void setMeterAssetNo(String meterAssetNo) {
		this.meterAssetNo = meterAssetNo;
	}

	public String getTerminalAddr() {
		return terminalAddr;
	}

	public void setTerminalAddr(String terminalAddr) {
		this.terminalAddr = terminalAddr;
	}

	public String getCisAssetNo() {
		return cisAssetNo;
	}

	public void setCisAssetNo(String cisAssetNo) {
		this.cisAssetNo = cisAssetNo;
	}

	public String getTgNo() {
		return tgNo;
	}

	public void setTgNo(String tgNo) {
		this.tgNo = tgNo;
	}

	public List<CpTypeCode> getCpTypeCodeList() {
		return cpTypeCodeList;
	}

	public void setCpTypeCodeList(List<CpTypeCode> cpTypeCodeList) {
		this.cpTypeCodeList = cpTypeCodeList;
	}

	public String getCpNo() {
		return cpNo;
	}

	public void setCpNo(String cpNo) {
		this.cpNo = cpNo;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpAddr() {
		return cpAddr;
	}

	public void setCpAddr(String cpAddr) {
		this.cpAddr = cpAddr;
	}

	public String getCpTypeCode() {
		return cpTypeCode;
	}

	public void setCpTypeCode(String cpTypeCode) {
		this.cpTypeCode = cpTypeCode;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
}
