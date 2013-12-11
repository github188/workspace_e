package com.nari.baseapp.planpowerconsume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.basicdata.VwLimitType;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlReject;
import com.nari.privilige.PSysUser;
/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-14 上午08:58:21 
 * 描述：
 */

public class TerminalEliminateAction extends BaseAction{
	
	private static Logger logger = Logger.getLogger(TerminalProtectAction.class);

	//返回值
	public boolean success = true;
	
	//请求的参数
	private String  release;     //解除or投入
	//private String totalNos;   //总加组号
	private String tmnlAssetNo;  //终端资产号
	private String orgNo;        //供电单位编号
	private String orgType;      //供电单位类型
    private String nodeType;     //节点类型
    private String lineId;       //线路Id
    private Long schemeId;       //方案ID
    private String groupNo;      //群组编号
    private String subsId;       //变电站标识 
    private String schemeRemark;               //方案备注
	List<String>  eliminateTmnlList;     //终端列表
	private Date ctrlDateStart;         //控制开始日期
	private Date ctrlDateEnd;           //控制结束日期
	private String   limitType;         //限电类型
	private String ctrlSchemeName;      //控制方案名
	private Integer taskSecond=30;      //任务执行时间
	private Integer FLAG;               //任务执行状态
	List<WTmnlReject>  wTmnlRejectList;  //Grid数据列表
	private List<WCtrlScheme> schemeList;//方案列表
	private List<VwLimitType> limitTypeList;//限电类型列表

	private List<TmnlExecStatusBean> tmnlExecStatusList;//终端执行状态列表
	private String cacheAndTmnlStatus;
	private  IWTmnlRejectManager  iWTmnlRejectManager;
	public void setiWTmnlRejectManager(IWTmnlRejectManager iWTmnlRejectManager) {
		this.iWTmnlRejectManager = iWTmnlRejectManager;
	}
	
	private ILOpTmnlLogManager iLOpTmnlLogManager;
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	
	private SuspensionCtrlManager suspensionCtrlManager;
	public void setSuspensionCtrlManager(SuspensionCtrlManager suspensionCtrlManager) {
		this.suspensionCtrlManager = suspensionCtrlManager;
	}
	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	
    public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}
    /**
     * 下发
     * @return
     * @throws Exception
     */
	public String sendout() throws Exception{
		try{
			TaskDeal taskDeal = new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList= parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;
			}		
			if(("0").equals(this.release)||("1").equals(this.release)){
				//操作终端时间
				Date OpTime=new Date();
				
				//参数项设置
				List<ParamItem> paramList = new ArrayList<ParamItem>();			
				if("0".equals(this.release))
				{
					  ParamItem pitem = new ParamItem();
					  pitem.setFn((short) 36);//设置FN号
					  pitem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					  //编码对象集合
					  pitem.setItems(null);
					  paramList.add(pitem);
				}
				else if("1".equals(this.release))
				{    
					  ParamItem pitem = new ParamItem();
					  pitem.setFn((short) 28);//设置FN号
					  pitem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					  //编码对象集合
					  pitem.setItems(null);
					  paramList.add(pitem);
				} 
				
				// 终端资产号集合
				List<String> tmnlAssetNoList=null;
				for(int i=0;i<tmnlList.size();i++){
					tmnlAssetNoList = new ArrayList<String>();
				    tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo()); 
					//下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				}
				List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
				//前置机返回结果
				this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
				TmnlExecStatusBean tmnlExecStatusBean =null;
				if (null != respList && respList.size()>0){
					Response res=null;
					LOpTmnlLog log=null;
					for(int i=0;i < respList.size();i++){
						res=(Response)respList.get(i);
						String tAssetNo=res.getTmnlAssetNo();
						//终端返回状态
						short Status = res.getTaskStatus();
						//写日志
						log=getLOpTmnlLog();
						log.setOpModule("终端剔除");
						log.setOpButton("下发");
						log.setOpType((short)2);
						log.setOpObj((short)1);
						log.setTmnlAssetNo(tAssetNo);
						log.setCurStatus(String.valueOf(Status));
		    			log.setOpTime(OpTime);
						if(("0").equals(this.release))
							log.setProtocolNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"0524");
						else if(("1").equals(this.release))
							log.setProtocolNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"051C");
						this.iLOpTmnlLogManager.saveTmnlLog(log);
						 //返回执行状态
						tmnlExecStatusBean = new TmnlExecStatusBean();
						tmnlExecStatusBean.setKeyId(tAssetNo);
						if (Status == 3){
							tmnlExecStatusBean.setExecFlag("1");
							this.iWTmnlRejectManager.updateEliminateFlag(tAssetNo, Integer.valueOf(this.release));
						}
						else
							tmnlExecStatusBean.setExecFlag("0");
						this.tmnlExecStatusList.add(tmnlExecStatusBean);
					}
				}
				else{
					for(int i=0;i < tmnlList.size();i++){
						tmnlExecStatusBean = new TmnlExecStatusBean();
						tmnlExecStatusBean.setKeyId(tmnlList.get(i).getTmnlAssetNo());
						tmnlExecStatusBean.setExecFlag("0");
						this.tmnlExecStatusList.add(tmnlExecStatusBean);
					}
				}
				return SUCCESS;
			}
			else{
				this.success=false;
				return SUCCESS;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	 /**
	 * 另存为方案界面，点击确定时的事件处理，orgNo是操作员单位
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	 public String addEliminateScheme() throws Exception{
		try{
			PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			String staffNo=pSysUser.getStaffNo();
			String userOrgNo=pSysUser.getOrgNo();
			//终端列表
			List<CtrlParamBean> tmnlList = parseSchemeTmnlList();	
			
			if(null==tmnlList||0==tmnlList.size()
				||null==staffNo||"".equals(staffNo)
				||null==this.ctrlSchemeName||"".equals(this.ctrlSchemeName)){
					this.success=false;
					return SUCCESS;
			}
			//先查询该操作员是否有该方案
			List<WCtrlScheme> schemeList = this.iWCtrlSchemeManager.checkSchemeName(staffNo,"09",this.ctrlSchemeName);
			if(null != schemeList && 0 < schemeList.size()){
				this.FLAG = 0;
			}
			else{
				WCtrlScheme scheme = new WCtrlScheme();
				scheme.setCtrlSchemeName(this.ctrlSchemeName);
				scheme.setCtrlSchemeType("09");
				scheme.setCreateDate(new Date());
				scheme.setStaffNo(staffNo);
				scheme.setOrgNo(userOrgNo);
				scheme.setCtrlDateStart(dateToSqlDate(this.ctrlDateStart));
				scheme.setCtrlDateEnd(dateToSqlDate(this.ctrlDateEnd));
				scheme.setLimitType(this.limitType);
				scheme.setIsValid((long)1);
				scheme.setSchemeRemark(this.schemeRemark);
				List<WTmnlReject> rejectList=new ArrayList<WTmnlReject>();
				for(int i = 0; i < tmnlList.size(); i++){
					String tOrgNo=tmnlList.get(i).getOrgNo();
					String tConsNo=tmnlList.get(i).getConsNo();
					String tTmnlAssetNo=tmnlList.get(i).getTmnlAssetNo(); 
					WTmnlReject wt=new WTmnlReject();
					wt.setOrgNo(tOrgNo);
					wt.setConsNo(tConsNo);
					wt.setTmnlAssetNo(tTmnlAssetNo);
					if(release.equals("0"))
						wt.setIsReject(false);
					else if(release.equals("1"))
						wt.setIsReject(true);
					rejectList.add(wt);
				}
				this.success=this.iWTmnlRejectManager.saveScheme(scheme, rejectList);
				if(this.success)
					this.FLAG = 1;
			 }
			 return SUCCESS;
	     }catch (Exception e) {
			e.printStackTrace();
			throw e;
		 }
	 }
	 
		 
	 /**
	  * 保存剔除方案
	  * @return
	  * @throws Exception
	  */
	 public String updateEliminateScheme() throws Exception{
		 try{
			 List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.querySchemesById(this.schemeId);
			 if(null==schemeList||0==schemeList.size()){
	    		 this.success=false;
				 return SUCCESS;
	    	 }
			//终端列表
			List<CtrlParamBean> tmnlList = parseSchemeTmnlList();
			if(null==tmnlList||0==tmnlList.size()){
	    		 this.success=false;
				 return SUCCESS;
	    	 }
			 WCtrlScheme scheme = schemeList.get(0);
			 scheme.setCtrlDateStart(dateToSqlDate(this.ctrlDateStart));
			 scheme.setCtrlDateEnd(dateToSqlDate(this.ctrlDateEnd));
			 scheme.setLimitType(this.limitType);
			//scheme.setIsValid(1L);
			 scheme.setSchemeRemark(this.schemeRemark);
			
			List<WTmnlReject> rejectList=new ArrayList<WTmnlReject>();
			for (int i = 0; i < tmnlList.size(); i++) {
				String tOrgNo=tmnlList.get(i).getOrgNo();
				String tConsNo=tmnlList.get(i).getConsNo();
				String tTmnlAssetNo=tmnlList.get(i).getTmnlAssetNo(); 
				WTmnlReject wt=new WTmnlReject();
				wt.setOrgNo(tOrgNo);
				wt.setConsNo(tConsNo);
				wt.setTmnlAssetNo(tTmnlAssetNo);
				wt.setCtrlSchemeId(this.schemeId);
				if(release.equals("0"))
					wt.setIsReject(false);
				else if(release.equals("1"))
					wt.setIsReject(true);
				rejectList.add(wt);
			}
			this.success=this.iWTmnlRejectManager.updateScheme(scheme, rejectList);
			return SUCCESS;
		 }catch (Exception e) {
			e.printStackTrace();
			throw e;
		 }
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
    /**
	 * 加载方案，在点击方案加载之后的事件处理，主要是把方案显示在页面上，供选择
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadScheme()throws Exception{
    	try{
	    	PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
	    	if(null==pSysUser)
	    		return SUCCESS;
			String staffNo=pSysUser.getStaffNo();
	    	this.schemeList =this.iWCtrlSchemeManager.querySchemeListByType(staffNo,"09");
	    	return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    /**
	 * 加载限电类型
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadLimitType() throws Exception{
    	logger.debug("加载限电类型开始");
    	try{
	    	this.limitTypeList = this.suspensionCtrlManager.queryLimitTypeList();
	    	logger.debug("加载限电类型结束");
	    	return SUCCESS;
    	}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }	
	
	/**
	 * 提取页面选择的终端
	 */
	public List<CtrlParamBean> parseTmnlList() {
		if(null==this.eliminateTmnlList||0==this.eliminateTmnlList.size())
			return null;
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.eliminateTmnlList.size(); i++) {
			String[] tmnl = this.eliminateTmnlList.get(i).split("`");
			if("".equals(tmnl[0])||"".equals(tmnl[1])||("null").equals(tmnl[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(tmnl[0]);
			bean.setProtocolCode(tmnl[1]);
			tmnlList.add(bean);
		}
		return tmnlList;
	}
	
	/**
	 * 提取页面选择的终端（用于保存方案）
	 */
	public List<CtrlParamBean> parseSchemeTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.eliminateTmnlList.size(); i++) {
			String[] tmnl = this.eliminateTmnlList.get(i).split("`");
			if("".equals(tmnl[2])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(tmnl[0]);
			bean.setConsNo(tmnl[1]);
			bean.setTmnlAssetNo(tmnl[2]);
			tmnlList.add(bean);
		}
		return tmnlList;
	}
	
	/**
	 * 取终端信息
	 */
	public String generalGridByTree() throws Exception {
		if(null == nodeType || "".equals(nodeType)){
			return SUCCESS;
   	    }
		try{
			PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
			if("usr".equals(this.nodeType)){
				this.wTmnlRejectList =this.iWTmnlRejectManager.queryTmnlByTmnlAssetNo(this.tmnlAssetNo);
	    	}
			else if("org".equals(this.nodeType)){
				this.wTmnlRejectList =this.iWTmnlRejectManager.queryTmnlByOrgNo(this.orgNo,this.orgType,pSysUser);
			}
			else if("line".equals(this.nodeType)){
				this.wTmnlRejectList =this.iWTmnlRejectManager.queryTmnlByLineId(this.lineId,pSysUser);
			}
			else if("cgp".equals(this.nodeType) || "ugp".equals(this.nodeType)){
				this.wTmnlRejectList =this.iWTmnlRejectManager.queryTmnlByGroupNo(this.nodeType,this.groupNo);
			}
			else if("sub".equals(this.nodeType)){
				this.wTmnlRejectList =this.iWTmnlRejectManager.queryTmnlBySubsId(this.subsId,pSysUser);
			}
			else if("ctrlScheme".equals(this.nodeType)){
			    this.wTmnlRejectList = this.iWTmnlRejectManager.queryTmnlBySchemeId(this.schemeId);
			}
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public String[] parse(String s){
    	if(s!=null){
      	  String[] s1 = s.split(",");
	      	  return s1;
	      	}
	      	else
	      	  return null;
    }
	/**
	 * 根据终端资产编号获取终端规约编码
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getProtocolCodeByTmnlAssetNo(List<CtrlParamBean> tmnlList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) return "";
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}
		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String isRelease() {
			return release;
		}

		public void setRelease(String release) {
			this.release = release;
		}
		
		public String getRelease() {
			return release;
		}

		public String getCtrlSchemeName() {
			return ctrlSchemeName;
		}

		public void setCtrlSchemeName(String ctrlSchemeName) {
			this.ctrlSchemeName = ctrlSchemeName;
		}
		public String getLimitType() {
			return limitType;
		}
		public void setLimitType(String limitType) {
			this.limitType = limitType;
		}
		public Date getCtrlDateStart() {
			return ctrlDateStart;
		}
		public void setCtrlDateStart(Date ctrlDateStart) {
			this.ctrlDateStart = ctrlDateStart;
		}
		public Date getCtrlDateEnd() {
			return ctrlDateEnd;
		}
		public void setCtrlDateEnd(Date ctrlDateEnd) {
			this.ctrlDateEnd = ctrlDateEnd;
		}
		public List<WCtrlScheme> getSchemeList() {
			return schemeList;
		}
		public void setSchemeList(List<WCtrlScheme> schemeList) {
			this.schemeList = schemeList;
		}
		public List<VwLimitType> getLimitTypeList() {
			return limitTypeList;
		}
		public void setLimitTypeList(List<VwLimitType> limitTypeList) {
			this.limitTypeList = limitTypeList;
		}
		public List<String> getEliminateTmnlList() {
			return eliminateTmnlList;
		}
		public void setEliminateTmnlList(List<String> eliminateTmnlList) {
			this.eliminateTmnlList = eliminateTmnlList;
		}

		public Integer getFLAG() {
			return FLAG;
		}

		public void setFLAG(Integer fLAG) {
			FLAG = fLAG;
		}

		public String getTmnlAssetNo() {
			return tmnlAssetNo;
		}

		public void setTmnlAssetNo(String tmnlAssetNo) {
			this.tmnlAssetNo = tmnlAssetNo;
		}

		public String getOrgNo() {
			return orgNo;
		}

		public void setOrgNo(String orgNo) {
			this.orgNo = orgNo;
		}

		public String getOrgType() {
			return orgType;
		}

		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}

		public String getNodeType() {
			return nodeType;
		}

		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}

		public String getLineId() {
			return lineId;
		}

		public void setLineId(String lineId) {
			this.lineId = lineId;
		}

		public Long getSchemeId() {
			return schemeId;
		}

		public void setSchemeId(Long schemeId) {
			this.schemeId = schemeId;
		}

		public String getSubsId() {
			return subsId;
		}

		public void setSubsId(String subsId) {
			this.subsId = subsId;
		}

		public List<WTmnlReject> getwTmnlRejectList() {
			return wTmnlRejectList;
		}

		public void setwTmnlRejectList(List<WTmnlReject> wTmnlRejectList) {
			this.wTmnlRejectList = wTmnlRejectList;
		}

		public String getGroupNo() {
			return groupNo;
		}

		public void setGroupNo(String groupNo) {
			this.groupNo = groupNo;
		}

		public List<TmnlExecStatusBean> getTmnlExecStatusList() {
			return tmnlExecStatusList;
		}

		public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
			this.tmnlExecStatusList = tmnlExecStatusList;
		}
		public String getCacheAndTmnlStatus() {
			return cacheAndTmnlStatus;
		}
		public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
			this.cacheAndTmnlStatus = cacheAndTmnlStatus;
		}
		public Integer getTaskSecond() {
			return taskSecond;
		}
		public void setTaskSecond(Integer taskSecond) {
			this.taskSecond = taskSecond;
		}
		public String getSchemeRemark() {
			return schemeRemark;
		}
		public void setSchemeRemark(String schemeRemark) {
			this.schemeRemark = schemeRemark;
		}
		
}
