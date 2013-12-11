package com.nari.baseapp.datagathorman;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.ATmnlCollQuality;
import com.nari.baseapp.datagatherman.CommMode;
import com.nari.baseapp.datagatherman.ConsType;
import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.baseapp.datagatherman.MissingTerminalDTO;
import com.nari.baseapp.datagatherman.TerminalTypeCode;
import com.nari.baseapp.datagatherman.TmnlModeCode;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.opensymphony.xwork2.ActionContext;

/**
 * 采集质量检查 Action类
 * 
 * @author 余涛
 * 
 */
public class GatherQualityEvaluateAction extends BaseAction {

	private Logger logger = Logger.getLogger(this.getClass());

	private GatherQualityEvaluateManager gatherQualityEvaluateManager;

	public void setGatherQualityEvaluateManager(
			GatherQualityEvaluateManager gatherQualityEvaluateManager) {
		this.gatherQualityEvaluateManager = gatherQualityEvaluateManager;
	}

	/* 需要返回的Json */
	private boolean success = true;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	public long totalCount;

	private String startDate;
	private String endDate;
	private List<ATmnlCollQuality> aTmnlQualityList;

	private String handStartDate;
	private String handEndDate;
	private String nodeType;
	private String nodeValue;
	private String orgType;
	private String queryType;
	private List<GatherByHandDto> gatherByHandList;
	
	private List<ConsType> consTypeList;//用户类型列表
	private List<TmnlModeCode> tmnlModeCodeList;//终端型号列表
	private List<CommMode> commModeList; //通信方式列表
	private List <MissingTerminalDTO> missingTerminalList;//采集失败终端
	private String consType;//用户类型
	private String tmnlModeCode;//终端型号
	private List<TerminalTypeCode> tmnlTypeCodeList;//终端类型
	private String tmnlTypeCode; //终端类型
	private String commMode;//通信方式
	private String orgNo;

	// json格式数据
	private String jsonData;

	/**
	 * 查询采集质量情况
	 */
	public String findGatherQualityInfo() throws Exception {
		this.logger.debug("查询采集质量情况");
		PSysUser pSysUser = new PSysUser();
		pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
		if (pSysUser == null) {
			this.aTmnlQualityList = new ArrayList<ATmnlCollQuality>();
			return SUCCESS;
		}
		
		//处理首页昨日采集成功率
		if(null ==orgType || "".equals(orgType)){
			orgType = pSysUser.getAccessLevel();
		}
		if(null == orgNo || "".equals(orgNo)){
			orgNo = pSysUser.getOrgNo();
		}
		
//		this.aTmnlQualityList = this.gatherQualityEvaluateManager
//				.queryGatherQuality(pSysUser, pSysUser.getOrgNo(), startDate,
//						endDate);
		String staffNo = pSysUser.getStaffNo();
		aTmnlQualityList = gatherQualityEvaluateManager.queryGatherQuality(staffNo, orgType, orgNo, consType, tmnlModeCode, commMode, startDate, endDate);
		return SUCCESS;
	}

	/**
	 * 查询漏点补召的信息
	 */
	public String findUnGatherInfo() throws Exception {
		this.logger.debug("查询漏点补召信息开始");
		
		PSysUser pSysUser = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		if (pSysUser == null) {
			return SUCCESS;
		}
		Page<GatherByHandDto> pgb = this.gatherQualityEvaluateManager
				.queryUnGatherInfo(pSysUser, start, limit, nodeValue, nodeType,
						queryType, handStartDate, handEndDate, orgType);
		if (pgb == null) {
			return SUCCESS;
		}
		this.gatherByHandList = pgb.getResult();
		this.totalCount = pgb.getTotalCount();

		this.logger.debug("查询漏点补召信息结束");
		return SUCCESS;
	}

	public String callUnGatherInfo() throws Exception {
		this.logger.debug("手工补招开始");
		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(GatherByHandDto.class);

		GatherByHandDto[] gatherByHandDto = (GatherByHandDto[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);
		this.gatherQualityEvaluateManager.recallByHand(gatherByHandDto);
		this.logger.debug("手工补招结束");
		return SUCCESS;
	}
	
	/**
	 * chenjg
	 * 查询所有用户类型
	 * @return
	 */
	public String queryConsType(){
		try {
			consTypeList = gatherQualityEvaluateManager.getAllConsType();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("查询所有用户类型失败!");
			return "failure";
		}
		return SUCCESS;
	}

	/**
	 * chenjg
	 * 查询所有终端型号
	 * @return
	 */
	public String queryTmnlModeCode(){
		try {
			tmnlModeCodeList = gatherQualityEvaluateManager.getAllTmnlModeCode();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("查询所有终端型号失败!");
			return "failure";
		}
		return SUCCESS;
	}
	
	/**
	 * chenjg
	 * 查询所有终端型号
	 * @return
	 */
	public String queryTmnlType(){
		try {
			tmnlTypeCodeList = gatherQualityEvaluateManager.getAllTmnlType();
		} catch (Exception e) {
			logger.debug("查询所有终端类型失败！");
			e.printStackTrace();
			return "failure";
		}
		return SUCCESS;
	}
	
	/**
	 * chenjg
	 * 查询所有通信方式
	 * @return
	 */
	public String queryCommMode(){
		try {
			commModeList = gatherQualityEvaluateManager.getAllCommMode();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("查询所有通信方式失败!");
			return "failure";
		}
		return SUCCESS;
	}
	
	/**
	 * chenjg
	 * 查询采集失败终端
	 * @return
	 */
	public String queryMissingTerminal(){
		PSysUser pSysUser = (PSysUser) this.getSession().getAttribute("pSysUser");
		String staffNo =  pSysUser.getStaffNo();
		try {
			Page page = gatherQualityEvaluateManager.getMissingTerminal(start, limit, staffNo, consType, orgNo, tmnlModeCode, commMode, startDate, endDate);
			missingTerminalList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			logger.debug("查询采集失败终端失败!");
			e.printStackTrace();
			return "failure";
		}
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<ATmnlCollQuality> getaTmnlQualityList() {
		return aTmnlQualityList;
	}

	public void setaTmnlQualityList(List<ATmnlCollQuality> aTmnlQualityList) {
		this.aTmnlQualityList = aTmnlQualityList;
	}

	public String getHandStartDate() {
		return handStartDate;
	}

	public void setHandStartDate(String handStartDate) {
		this.handStartDate = handStartDate;
	}

	public String getHandEndDate() {
		return handEndDate;
	}

	public void setHandEndDate(String handEndDate) {
		this.handEndDate = handEndDate;
	}

	public List<GatherByHandDto> getGatherByHandList() {
		return gatherByHandList;
	}

	public void setGatherByHandList(List<GatherByHandDto> gatherByHandList) {
		this.gatherByHandList = gatherByHandList;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<ConsType> getConsTypeList() {
		return consTypeList;
	}

	public void setConsTypeList(List<ConsType> consTypeList) {
		this.consTypeList = consTypeList;
	}

	public List<TmnlModeCode> getTmnlModeCodeList() {
		return tmnlModeCodeList;
	}

	public void setTmnlModeCodeList(List<TmnlModeCode> tmnlModeCodeList) {
		this.tmnlModeCodeList = tmnlModeCodeList;
	}

	public List<CommMode> getCommModeList() {
		return commModeList;
	}

	public void setCommModeList(List<CommMode> commModeList) {
		this.commModeList = commModeList;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public String getTmnlModeCode() {
		return tmnlModeCode;
	}

	public void setTmnlModeCode(String tmnlModeCode) {
		this.tmnlModeCode = tmnlModeCode;
	}

	public String getCommMode() {
		return commMode;
	}

	public void setCommMode(String commMode) {
		this.commMode = commMode;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public List<TerminalTypeCode> getTmnlTypeCodeList() {
		return tmnlTypeCodeList;
	}

	public void setTmnlTypeCodeList(List<TerminalTypeCode> tmnlTypeCodeList) {
		this.tmnlTypeCodeList = tmnlTypeCodeList;
	}

	public String getTmnlTypeCode() {
		return tmnlTypeCode;
	}

	public void setTmnlTypeCode(String tmnlTypeCode) {
		this.tmnlTypeCode = tmnlTypeCode;
	}

	public List<MissingTerminalDTO> getMissingTerminalList() {
		return missingTerminalList;
	}

	public void setMissingTerminalList(List<MissingTerminalDTO> missingTerminalList) {
		this.missingTerminalList = missingTerminalList;
	}

}
