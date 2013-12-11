package com.nari.baseapp.planpowerconsume;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.basicdata.VwLimitType;
import com.nari.customer.CCons;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author 陈建国
 * 
 * @创建时间:2010-1-7 上午10:29:37
 * 
 * @类描述: 有序用电任务编制
 * @author 修改  huangxuan
 * 说明<br>1在此层中涉及到得需要对控制人员的编号进行处理的方面全部
 * 交给dao层中的代码	Map session = ActionContext.getContext().getSession();<br>
			// 得到操作人员
			<br> PSysUser staff = (PSysUser) session.get("pSysUser");<br>
			因为Map session = ActionContext.getContext().getSession();并不依赖<br>
			httpservletrequest 或者httpservletresponse对象.<br>
			
 */
public class PlanCompileAction extends BaseAction {
	
	//日志对象
	private static Logger logger = Logger.getLogger(EnergyControlAction.class);
	
	private List<String> userNos = new ArrayList<String>();
	private String message;

	public String getMessage() {
		return message;
	}

	public void setUserNos(List<String> userNos) {
		this.userNos = userNos;
	}

	private List<Long> ctrlSchemeIds = new ArrayList<Long>(5);

	public void setCtrlSchemeIds(List<Long> ctrlSchemeIds) {
		this.ctrlSchemeIds = ctrlSchemeIds;
	}

	private boolean success = true;
	private long start;
	private int limit=Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
	private List<WCtrlScheme> root;
	// 用户保存机构的编号和机构的名称之间的键值对关系
	private Map<String, String> noToOrgName;

	public Map<String, String> getNoToOrgName() {
		return noToOrgName;
	}

	public void setNoToOrgName(Map<String, String> noToOrgName) {
		this.noToOrgName = noToOrgName;
	}

	private int cconStart;
	private int cconLimit;
	private long cconTotalCount;
	private Long ctrlSchemeId;    //方案Id
	private String ctrlSchemeName;  //方案名
	private String ctrlType;     //功控类型
	private Integer FLAG;        //任务返回状态
	private Date newStartDate;  //新添加方案开始时间
	private Date newEndDate;    //新添加结束时间
	private String limitType;   //限电类型
	private String schemeRemark;//方案备注
	private Double ctrlLoad; //调控负荷
	private Integer schemeNameFlag;//方案名是否被修改的标志
	@SuppressWarnings("unchecked")
	private List cconList;
	private List<VwCtrlSchemeType> schemeTypes;
	// 调用这个接口可以对多种不同的工控类型进行自动的统一的操作
	private IWTmnlSchemePublicManager iWTmnlSchemePublicManager;
	private IPlanCompileManager iPlanCompileManager;

	public void setiWTmnlSchemePublicManager(
			IWTmnlSchemePublicManager iWTmnlSchemePublicManager) {
		this.iWTmnlSchemePublicManager = iWTmnlSchemePublicManager;
	}

	// 记录所有的限制类型
	private List<VwLimitType> limitTypes;

	public List<VwLimitType> getLimitTypes() {
		return limitTypes;
	}

	// 用来记录查询中要用到的参数 不转化为json
	private WCtrlScheme wctrlScheme = new WCtrlScheme();

	public WCtrlScheme getWctrlScheme() {
		return wctrlScheme;
	}

	public void setWctrlScheme(WCtrlScheme wctrlScheme) {
		this.wctrlScheme = wctrlScheme;
	}

	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}

	/**
	 * @ 得到当前所有的工控类型
	 * **/
	public String findCtrlSchemeType() {
		try {
			this.schemeTypes = iPlanCompileManager.getAllCtrlSchemeType();
			this.schemeTypes.remove(this.schemeTypes.size()-1);
			// realSchemeTypes=new ArrayList<VwCtrlSchemeTypeId>();
			// for(VwCtrlSchemeType v : schemeTypes){
			// realSchemeTypes.add(v.getId());
			// }
			// schemeTypes=null;
		} catch (DBAccessException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * @ *
	 * 
	 * 查找一个控制方案下的所有的用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findCConsByCtrlName() {
		Page page = new Page();
		page.setStartNo(start);
		page.setPageSize(limit);
		try {
			// page = iGroupSetManager.getAllCCons(page);
			setCconList(page.getResult());
//			WebApplicationContext wac = WebApplicationContextUtils
//					.getWebApplicationContext(getServletContext());
			page = iWTmnlSchemePublicManager.findCtrlSchemeUser(wctrlScheme
					.getCtrlSchemeId(), start, limit);
			// setTotalCount( page.getTotalCount());
			setCconTotalCount(page.getTotalCount());
			setCconList(page.getResult());
			//cconList.remove(cconList.size()-1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

	/****
	 * 得到所有的限电类型 *
	 **/
	public String findLimitTypes() {
		try {
			this.limitTypes = this.iPlanCompileManager.findAllLimitType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 通过不定的条件来对控制方案进行查询 *
	 **/
	@SuppressWarnings("unchecked")
	public String findWCtrlScheme() {
		Page<WCtrlScheme> page = new Page<WCtrlScheme>();
		page.setStartNo(start);
		page.setOrderBy("ctrlSchemeId");
		page.setOrder(Page.DESC);
		page.setPageSize(limit);
		// 放置过滤条件
		//List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser staff = (PSysUser) session.get("pSysUser");
			
			this.root = this.iPlanCompileManager.findWCtrlSheme(staff,wctrlScheme,start,limit)
					.getResult();
			// 组装出来一个列表，用于查找机构编号到机构名称的键值对
			List<String> list = new ArrayList<String>();
			for (WCtrlScheme w : root) {
				list.add(w.getOrgNo());
			}
			noToOrgName = iPlanCompileManager.findOrgNoToName(list);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		this.totalCount = page.getTotalCount();
		return SUCCESS;
	}

	/**
	 * 新建一个控制方案 
	 */
	public String newCtrlScheme() throws Exception{
	   logger.debug("新建控制方案开始");
	   try{	
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			String staffNo = pSysUser.getStaffNo();
			String userOrgNo=pSysUser.getOrgNo();
			if(null==staffNo||"".equals(staffNo)
				||null==this.ctrlType||"".equals(this.ctrlType)
				||null==this.ctrlSchemeName||"".equals(this.ctrlSchemeName)){
				this.success=false;
				return SUCCESS;
			}
			//先查询该操作员是否有此方案
			List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.checkSchemeName(staffNo,this.ctrlType,this.ctrlSchemeName);
			if(null != schemeList && 0 < schemeList.size()){
				this.FLAG = 0;
			}
			else{
				this.iWCtrlSchemeManager.newScheme(this.ctrlSchemeName, this.ctrlType, 
						staffNo, userOrgNo, dateToSqlDate(this.newStartDate),
						dateToSqlDate(this.newEndDate), this.limitType, this.ctrlLoad, this.schemeRemark);
				this.FLAG = 1;
			}
			logger.debug("新建控制方案结束");
	    	return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 修改控制方案信息
	 * @return
	 * @throws Exception
	 */
	public String updateCtrlScheme() throws Exception{
		 logger.debug("修改控制方案信息开始");
		 try{
			 if(null==this.ctrlSchemeId||null==this.ctrlSchemeName
			 	||"".equals(this.ctrlSchemeName)||null==this.schemeNameFlag){
					this.success=false;
					return SUCCESS;
			 }
			 PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			 if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			 }
			 String staffNo = pSysUser.getStaffNo();
			 if(null==staffNo||"".equals(staffNo)){
				this.success=false;
				return SUCCESS;
			}
			this.FLAG=this.iWCtrlSchemeManager.updateScheme(this.ctrlSchemeId,this.ctrlSchemeName, this.schemeNameFlag, this.newStartDate, this.newEndDate, 
					 this.limitType, this.ctrlLoad, this.schemeRemark, staffNo);
			 
			logger.debug("修改控制方案信息结束");
		    return SUCCESS;
		 }catch (Exception e) {
				e.printStackTrace();
				throw e;
		 }
	}
	
	/***
	 *删除控制方案 满足删除条件和不满足的条件都代人了进去，如果没有满足删除条件，会发生异常， 通知没有发现可以删除的
	 * **/
	public String deleteCtrlScheme() {
		try {
			iWTmnlSchemePublicManager.updateAboutCtrlScheme(ctrlSchemeIds);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return SUCCESS;
	}

	/***
	 * 删除一个控制方案下的一个或者多个用电用户 *
	 **/
	public String deleteCCons() {
		try {
			iWTmnlSchemePublicManager.deleteCtrlSchemeUser(wctrlScheme
					.getCtrlSchemeId(), userNos);
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return SUCCESS;
	}

	  
    /**
	 * date转化为数据库保存格式的date，如果有错返回当日信息
	 * @param date
	 * @return java.sql.Date
	 */
    private java.sql.Date dateToSqlDate(Date date){
    	if(null == date){
    		return new java.sql.Date(new Date().getTime());
    	}
    	try{
    	    return (new java.sql.Date(date.getTime()));
    	}catch(Exception ex){
    		return new java.sql.Date(new Date().getTime());
    	}
    }
    
	public void setiPlanCompileManager(IPlanCompileManager iPlanCompileManager) {
		this.iPlanCompileManager = iPlanCompileManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public List<WCtrlScheme> getRoot() {
		return root;
	}

	public void setRoot(List<WCtrlScheme> root) {
		this.root = root;
	}

	@SuppressWarnings("unchecked")
	public List<CCons> getCconList() {
		return cconList;
	}

	public void setCconList(List<CCons> cconList) {
		this.cconList = cconList;
	}

	public int getCconStart() {
		return cconStart;
	}

	public void setCconStart(int cconStart) {
		this.cconStart = cconStart;
	}

	public int getCconLimit() {
		return cconLimit;
	}

	public void setCconLimit(int cconLimit) {
		this.cconLimit = cconLimit;
	}

	public long getCconTotalCount() {
		return cconTotalCount;
	}

	public void setCconTotalCount(long cconTotalCount) {
		this.cconTotalCount = cconTotalCount;
	}

	public List<VwCtrlSchemeType> getSchemeTypes() {
		return schemeTypes;
	}

	public void setSchemeTypes(List<VwCtrlSchemeType> schemeTypes) {
		this.schemeTypes = schemeTypes;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		PlanCompileAction.logger = logger;
	}

	public String getCtrlSchemeName() {
		return ctrlSchemeName;
	}

	public void setCtrlSchemeName(String ctrlSchemeName) {
		this.ctrlSchemeName = ctrlSchemeName;
	}

	public String getCtrlType() {
		return ctrlType;
	}

	public void setCtrlType(String ctrlType) {
		this.ctrlType = ctrlType;
	}

	public Integer getFLAG() {
		return FLAG;
	}

	public void setFLAG(Integer fLAG) {
		FLAG = fLAG;
	}

	public Date getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	public Date getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public String getSchemeRemark() {
		return schemeRemark;
	}

	public void setSchemeRemark(String schemeRemark) {
		this.schemeRemark = schemeRemark;
	}

	public Double getCtrlLoad() {
		return ctrlLoad;
	}

	public void setCtrlLoad(Double ctrlLoad) {
		this.ctrlLoad = ctrlLoad;
	}

	public Integer getSchemeNameFlag() {
		return schemeNameFlag;
	}

	public void setSchemeNameFlag(Integer schemeNameFlag) {
		this.schemeNameFlag = schemeNameFlag;
	}

	public Long getCtrlSchemeId() {
		return ctrlSchemeId;
	}

	public void setCtrlSchemeId(Long ctrlSchemeId) {
		this.ctrlSchemeId = ctrlSchemeId;
	}

}
