package com.nari.baseapp.planpowerconsume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.basicdata.VwLimitType;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.PaulPowerFetchResult;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.privilige.PSysUser;


/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-12 下午01:55:51 
 * 描述：终端保电功能
 */


public class TerminalProtectAction extends BaseAction{

	private static Logger logger = Logger.getLogger(TerminalProtectAction.class);
	
	//返回值
	public boolean success = true;
  
	//请求的参数	
	private String isProtect;     
	private String isFixed;
	private Integer FLAG;        //任务返回状态
	private String tmnlAssetNo;  //终端资产号
	private String orgNo;        //供电单位编号
	private String orgType;      //供电单位类型
    private String nodeType;     //节点类型
    private String lineId;       //线路Id
    private Long schemeId;       //方案ID
    private String schemeType;   //方案类别
    private String groupNo;      //群组编号
    private String subsId;       //变电站标识 
	private Integer autoPaulPower;    //自动保电参数
	private Float securityValue;    //保安定值
	private Float duration;         //保电投入持续时间
	//private Boolean isSendSms;    //是否发送短信
	private List<String>  protectTmnlList; //终端列表
	private String ctrlSchemeName;   //控制方案名
	private Date ctrlDateStart;      //控制开始日期
	private Date ctrlDateEnd;        //控制结束日期
	//private String limitType;       //限电类型
	private List<VwLimitType> limitTypeList;//限电类型列表  
	private List<PaulPowerFetchResult>fetchResultList;//召测结果列表  
	private List<WCtrlScheme> schemeList;//方案列表
	private List<TmnlExecStatusBean> tmnlExecStatusList;//终端执行状态列表
	private List<WTmnlPaulPower> wTmnlPaulPowerList;  //Grid数据列表
	private Integer taskSecond=30;            
	private String cacheAndTmnlStatus;         //提示信息	
	private String schemeRemark;               //方案备注
	
	private IWTmnlPaulPowerManager iWTmnlPaulPowerManager;
    public void setiWTmnlPaulPowerManager(
				IWTmnlPaulPowerManager iWTmnlPaulPowerManager) {
			this.iWTmnlPaulPowerManager = iWTmnlPaulPowerManager;
		}
	private ILOpTmnlLogManager iLOpTmnlLogManager;
	
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	
	private SuspensionCtrlManager suspensionCtrlManager;
	
	public void setSuspensionCtrlManager(
			SuspensionCtrlManager suspensionCtrlManager) {
		this.suspensionCtrlManager = suspensionCtrlManager;
	}
	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	
    public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}

	//--------------下发
	public String sendout() throws Exception {
		try {
			TaskDeal taskDeal=new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			//终端列表
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;	
			}
			if(null==this.autoPaulPower && null==this.securityValue){
				this.success=false;
				return SUCCESS;	
			}
			//操作终端时间
			Date OpTime=new Date();
			
			//终端资产号集合
			List<String> tmnlAssetNoList = null;
			// 参数项设置
			List<ParamItem> paramList=null;
			ParamItem pitem=null;
			List<Item> items=null; 
			Item item=null;
			for (int i = 0; i < tmnlList.size(); i++) {
				//终端规约码
				String tProtocolCode=tmnlList.get(i).getProtocolCode();
				
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo());

				paramList = new ArrayList<ParamItem>();
				if (null!=this.autoPaulPower){
					pitem = new ParamItem();
					pitem.setFn((short) 58);// 设置FN号
					pitem.setPoint((short) 0);// 设置point(为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					// 编码对象集合
					items = new ArrayList<Item>();
					item = new Item(tProtocolCode+"043A001");
					item.setValue(String.valueOf(this.autoPaulPower));
					items.add(item);		
					pitem.setItems((ArrayList<Item>) items);
					paramList.add(pitem);
				}
				if (null!=this.securityValue){
					pitem = new ParamItem();
					pitem.setFn((short) 17);// 设置FN号
					pitem.setPoint((short) 0);// 设置point(为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					// 编码对象集合
					items = new ArrayList<Item>();
					item = new Item(tProtocolCode+"0411001");
					item.setValue(String.valueOf(this.securityValue));
					items.add(item);
					pitem.setItems((ArrayList<Item>) items);
					paramList.add(pitem);
				}
				//调用前置机接口下发任务
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.SET_PARAMETERS, paramList);
			}	
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			//前置机返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
			
			TmnlExecStatusBean tmnlexecStatus=null;
			if(null != respList && respList.size()>0){
				Response res=null;
				LOpTmnlLog log=null;
				for(int i = 0;i < respList.size();i++){
					res=(Response)respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
					//终端返回状态
					short Status = res.getTaskStatus();
					String tProtocolCode=getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo);
					// 写日志
					log = getLOpTmnlLog();
					log.setOpModule("终端保电");
					log.setOpButton("参数下发");
					log.setOpType((short)1);
					log.setOpObj((short)1);
					log.setTmnlAssetNo(tAssetNo);
					log.setOpTime(OpTime);
					log.setCurStatus(String.valueOf(Status));
					if(null!=this.autoPaulPower){
						log.setProtItemNo(tProtocolCode+"043A001");
						log.setCurValue(String.valueOf(this.autoPaulPower));
						this.iLOpTmnlLogManager.saveTmnlLog(log);
					}
					if(null!=this.securityValue){
						log.setProtItemNo(tProtocolCode+"0411001");
						log.setCurValue(String.valueOf(this.securityValue));
						this.iLOpTmnlLogManager.saveTmnlLog(log);
					}
					tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tAssetNo);
					if (Status == 3)
						tmnlexecStatus.setExecFlag("1");
					else
						tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			else{
				for (int i = 0; i < tmnlList.size(); i++) {
					tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tmnlList.get(i).getTmnlAssetNo());
					tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//--------------召测
	public String fetch() throws Exception {
		try {
			TaskDeal taskDeal = new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			//终端列表
			List<String> tmnlList = parseFetchTmnlList();
			if(null==tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;	
			}
			if((null==this.isFixed||("").equals(this.isFixed))&&(null==this.isProtect||("").equals(this.isProtect))){
				this.success=false;
				return SUCCESS;
			}
			// 参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			if (this.isFixed.equals("1")) {
				ParamItem pitem = new ParamItem();
				pitem.setFn((short) 17);// 设置FN号
				pitem.setPoint((short) 0);// 设置point(为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)		
				// 编码对象集合
				pitem.setItems(null);
				paramList.add(pitem);	
			}
			if (this.isProtect.equals("1")) {
				ParamItem pitem = new ParamItem();
				pitem.setFn((short) 58);// 设置FN号
				pitem.setPoint((short) 0);// 设置point// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				// 编码对象集合
				pitem.setItems(null);
				paramList.add(pitem);
			}
			// 终端资产号集合
			List<String> tmnlAssetNoList =null;
			for (int i = 0; i < tmnlList.size(); i++){
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i));	
				// 召测
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.QUERY_PARAMS, paramList);
			}
			List<Response> respList  = taskDeal.getAllResponse(this.taskSecond);
			
			//System.out.println("召测结果数："+respList.size());
			this.fetchResultList = new ArrayList<PaulPowerFetchResult>();
			if(null != respList && respList.size()>0){
				Response  res = null;
				PaulPowerFetchResult  fetchResult = null;
				for (int i = 0; i < respList.size(); i++) {
					res=(Response)respList.get(i);
					if(null!=res.getResponseItems()&& 0 < res.getResponseItems().size() && res.getTaskStatus()==3){
						fetchResult=new  PaulPowerFetchResult();
						fetchResult.setTmnlAssetNo(res.getTmnlAssetNo());
						for(int j=0 ;j < res.getResponseItems().size();j++){
							 if(res.getResponseItems().get(j).getCode().endsWith("043A001"))
								 fetchResult.setAutoPaulPower(Integer.valueOf(res.getResponseItems().get(j).getValue()));
							 else if(res.getResponseItems().get(j).getCode().endsWith("0411001"))
								 fetchResult.setSecurityValue(Float.valueOf(res.getResponseItems().get(j).getValue()));
						}
						this.fetchResultList.add(fetchResult);	
					}
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// --------------保电投入
	public String devote() throws Exception {
		/*this.tmnlAssetNoArray = this.parse(this.tmnlAssetNos);
		this.consNoArray = this.parse(this.consNos);
		this.orgNoArray = this.parse(this.orgNos);
		if (this.tmnlAssetNoArray == null||this.tmnlAssetNoArray.length==0)
			return SUCCESS;*/
		try {
			TaskDeal taskDeal = new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()||null==this.duration){
				this.success=false;
				return SUCCESS;	
			}
			//操作终端时间
			Date OpTime=new Date();
			//前置机返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
			// 终端资产号集合
			List<String> tmnlAssetNoList =null;
			// 参数项设置
			List<ParamItem> paramList = null;
			ParamItem pitem=null;
			List<Item> items=null; 
			Item item=null; 
			for (int i = 0; i < tmnlList.size(); i++){
				
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo());
				
				paramList = new ArrayList<ParamItem>();
				pitem = new ParamItem();
				pitem.setFn((short) 25);// 设置FN号
				pitem.setPoint((short) 0);// 设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				
				// 编码对象集合
				items = new ArrayList<Item>();
				item = new Item(tmnlList.get(i).getProtocolCode()+"0519001");
				item.setValue(String.valueOf((short)(this.duration * 2)));
				items.add(item);
				pitem.setItems((ArrayList<Item>)items);
				paramList.add(pitem);
				
				//投入
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.CONTROL_COMMAND, paramList);
			}
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			TmnlExecStatusBean tmnlexecStatus=null;
			if (null != respList && respList.size()>0){
				Response res=null;
				LOpTmnlLog log=null;
				for(int i=0;i<respList.size();i++){
					res=(Response)respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
					//终端返回状态
					short Status = res.getTaskStatus();
					// 写日志
					log= getLOpTmnlLog();
					log.setOpModule("终端保电");
					log.setOpButton("保电投入");
					log.setOpType((short)2);
					log.setOpObj((short)1);
					log.setTmnlAssetNo(tAssetNo);
					log.setProtItemNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"0519001");
					log.setCurValue(String.valueOf((short)(this.duration * 2)));
					log.setOpTime(OpTime);
					log.setCurStatus(String.valueOf(Status));
					this.iLOpTmnlLogManager.saveTmnlLog(log);
					//返回执行状态
					tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tAssetNo);
					if (Status == 3){
						this.iWTmnlPaulPowerManager.updateProtectFlag(tAssetNo,1);
						tmnlexecStatus.setExecFlag("1");
					}
					else
						tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}		
			else{
				for (int i = 0; i < tmnlList.size(); i++){
					tmnlexecStatus=new TmnlExecStatusBean();
					tmnlexecStatus.setKeyId(tmnlList.get(i).getTmnlAssetNo());
					tmnlexecStatus.setExecFlag("0");
					this.tmnlExecStatusList.add(tmnlexecStatus);
				}
			}
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// --------------保电解除
	public String release() throws Exception {
		try {
			TaskDeal taskDeal = new TaskDeal();
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;
			}
			//操作终端时间
			Date OpTime=new Date();
			
			// 参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = new ParamItem();
			pitem.setFn((short) 33);// 设置FN号
			pitem.setPoint((short) 0);// 设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			pitem.setItems(null);
			paramList.add(pitem);
			// 终端资产号集合
			List<String> tmnlAssetNoList = null;
			for (int i = 0; i < tmnlList.size(); i++){
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo());	
				// 解除
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.CONTROL_COMMAND, paramList);
			}
			
			List<Response> respList = taskDeal.getAllResponse(this.taskSecond);
			//前置机返回结果
			this.tmnlExecStatusList = new ArrayList<TmnlExecStatusBean>();
			TmnlExecStatusBean tmnlExecStatusBean =null;
			if (null != respList && respList.size()>0){
				Response res=null;
				LOpTmnlLog log =null;
				for(int i=0;i < respList.size();i++){
					res=(Response)respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
					//终端返回状态
					short Status = res.getTaskStatus();
					// 写日志
					log = getLOpTmnlLog();
					log.setOpModule("终端保电");
					log.setOpButton("保电解除");
					log.setOpType((short)2);
					log.setOpObj((short)1);
					log.setTmnlAssetNo(tAssetNo);
					log.setProtocolNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"0521");
					log.setCurStatus(String.valueOf(Status));
					log.setOpTime(OpTime);
					this.iLOpTmnlLogManager.saveTmnlLog(log);
					//返回执行状态
					tmnlExecStatusBean = new TmnlExecStatusBean();
					tmnlExecStatusBean.setKeyId(tAssetNo);
					if (Status == 3){
						tmnlExecStatusBean.setExecFlag("1");
						this.iWTmnlPaulPowerManager.updateProtectFlag(tAssetNo,0);
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
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 另存为方案界面，点击确定时的事件处理，orgNo是操作员单位
	 * 
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String saveProtectScheme() throws Exception {
		try{	
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			//工号
			String staffNo = pSysUser.getStaffNo();
			//操作员单位
			String userOrgNo=pSysUser.getOrgNo();
			/*if (null == this.consNos || null == this.orgNos
					|| null == this.tmnlAssetNos) {
				return SUCCESS;
			}
			String[] orgNoArr = this.parse(this.orgNos);
			String[] consNoArr = this.parse(this.consNos);
			String[] tmnlAssetNoArr = this.parse(this.tmnlAssetNos);
			if (null == orgNoArr || 0 >= orgNoArr.length || null == consNoArr
					|| 0 >= consNoArr.length || null == tmnlAssetNoArr
					|| 0 >= tmnlAssetNoArr.length) {
				return SUCCESS;
			}
			// 对终端编号为空的用户记录进行剔除
			this.filterData(orgNoArr, consNoArr, tmnlAssetNoArr);*/
			//终端列表
			List<CtrlParamBean> tmnlList = parseSchemeTmnlList();
			if(null==tmnlList||0==tmnlList.size()
					||null==staffNo||"".equals(staffNo)
					||null==this.ctrlSchemeName||"".equals(this.ctrlSchemeName)){
	            this.success=false;
				return SUCCESS;
			}
			//先查询该操作员是否有重名方案
			List<WCtrlScheme> schemeList =this.iWCtrlSchemeManager.checkSchemeName(staffNo,"08",this.ctrlSchemeName);
			if(null != schemeList && 0 < schemeList.size()){
				this.FLAG = 0;
			}
			else{
				Date saveTime=new Date();
				WCtrlScheme scheme = new WCtrlScheme();
				scheme.setCtrlSchemeName(this.ctrlSchemeName);
				scheme.setCtrlSchemeType("08");
				scheme.setCreateDate(saveTime);
				scheme.setStaffNo(staffNo);
				scheme.setOrgNo(userOrgNo);
				scheme.setCtrlDateStart(dateToSqlDate(this.ctrlDateStart));
				scheme.setCtrlDateEnd(dateToSqlDate(this.ctrlDateEnd));
				scheme.setIsValid(1L);
				scheme.setSchemeRemark(this.schemeRemark);
				List<WTmnlPaulPower> PaulPowerList = new ArrayList<WTmnlPaulPower>();
				for (int i = 0; i < tmnlList.size(); i++) {
					String orgNo=tmnlList.get(i).getOrgNo();
					String consNo=tmnlList.get(i).getConsNo();
					String tmnlAssetNo=tmnlList.get(i).getTmnlAssetNo(); 
					WTmnlPaulPower wt = new WTmnlPaulPower();
					wt.setOrgNo(orgNo);
					wt.setConsNo(consNo);
					wt.setTmnlAssetNo(tmnlAssetNo);
					if (null!=this.autoPaulPower)
						wt.setAutoPaulPower(this.autoPaulPower);
					if (null!=this.securityValue)	
						wt.setSecurityValue(this.securityValue);
					if (null!=this.duration)	
						wt.setDuration((short)(this.duration * 2));
					PaulPowerList.add(wt);
				}
				this.success=this.iWTmnlPaulPowerManager.saveScheme(scheme, PaulPowerList);
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
     * 保存保电方案
     * @return
     * @throws Exception
     */
	public String updateProtectScheme() throws Exception {
		try{
			List<WCtrlScheme> schemeList = this.iWCtrlSchemeManager.querySchemesById(this.schemeId);
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
			scheme.setSchemeRemark(this.schemeRemark);
			//scheme.setIsValid(0L);
	
			List<WTmnlPaulPower> PaulPowerList = new ArrayList<WTmnlPaulPower>();
			for (int i = 0; i < tmnlList.size(); i++) {
				String orgNo=tmnlList.get(i).getOrgNo();
				String consNo=tmnlList.get(i).getConsNo();
				String tmnlAssetNo=tmnlList.get(i).getTmnlAssetNo(); 
				WTmnlPaulPower wt = new WTmnlPaulPower();
				wt.setOrgNo(orgNo);
				wt.setConsNo(consNo);
				wt.setTmnlAssetNo(tmnlAssetNo);
				wt.setCtrlSchemeId(this.schemeId);
				if (null!=this.autoPaulPower)
					wt.setAutoPaulPower(autoPaulPower);
				if (null!=this.securityValue)	
					wt.setSecurityValue(this.securityValue);
				if (null!=this.duration)	
					wt.setDuration((short)(this.duration* 2));
				PaulPowerList.add(wt);
			}
			this.success= this.iWTmnlPaulPowerManager.updateScheme(scheme, PaulPowerList);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 从前台返回的数组进行校验，暂时只考虑如果终端号为空，则跳过该组数据。如果用户号和供电单位也不能为空，需要加入校验逻辑
	 * 
	 * @param date
	 * @return java.sql.Date
	 *//*
	private void filterData(String[] orgNoArr, String[] consNoArr,String[] tmnlAssetNoArr) {
		int j = 0;

		this.orgNoArray = null;
		this.consNoArray = null;
		this.tmnlAssetNoArray = null;

		this.orgNoArray = new String[tmnlAssetNoArr.length];
		this.consNoArray = new String[tmnlAssetNoArr.length];
		this.tmnlAssetNoArray = new String[tmnlAssetNoArr.length];

		for (int i = 0; i < tmnlAssetNoArr.length; i++) {
			if (null == tmnlAssetNoArr[i] || "".equals(tmnlAssetNoArr[i])
					|| null == consNoArr[i] || "".equals(consNoArr[i])
					|| null == orgNoArr[i] || "".equals(orgNoArr[i])) {
				continue;
			}
			this.orgNoArray[j] = orgNoArr[i];
			this.consNoArray[j] = consNoArr[i];
			this.tmnlAssetNoArray[j] = tmnlAssetNoArr[i];
			j++;
		}

		System.out.println("过滤完毕");
		System.out.println("用户编号" + this.consNoArray.length);
		System.out.println("终端号" + this.tmnlAssetNoArray.length);
		System.out.println("供电单位" + this.orgNoArray.length);
	}*/

	/**
	 * date转化为数据库保存格式的date，如果有错返回当日信息
	 * 
	 * @param date
	 * @return java.sql.Date
	 */
	private java.sql.Date dateToSqlDate(Date date) {
		if (null == date) {
			return new java.sql.Date(new Date().getTime());
		}
		try {
			return (new java.sql.Date(date.getTime()));
		} catch (Exception ex) {
			return new java.sql.Date(new Date().getTime());
		}
	}

	/**
	 * 加载方案，在点击方案加载之后的事件处理，主要是把方案显示在页面上，供选择
	 * 
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String loadScheme() throws Exception {
		try{
			PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
	    	if(null==pSysUser)
	    		return SUCCESS;
			String staffNo = pSysUser.getStaffNo();
			this.schemeList = this.iWCtrlSchemeManager.querySchemeListByType(staffNo,"08");
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
	 * 提取页面选择的终端（下发、投入、解除）
	 */
	public List<CtrlParamBean> parseTmnlList() {
		if(null==this.protectTmnlList||0==this.protectTmnlList.size())
			return null;
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.protectTmnlList.size(); i++) {
			String[] tmnl = this.protectTmnlList.get(i).split("`");
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
	 * 提取页面选择的终端（用于召测）
	 */
	public List<String> parseFetchTmnlList() {
		if(null==this.protectTmnlList||0==this.protectTmnlList.size())
			return null;
		List<String> tmnlList=new ArrayList<String>();
		for (int i = 0; i < this.protectTmnlList.size(); i++) {
			String tmnl = this.protectTmnlList.get(i);
			if("".equals(tmnl)){
				continue;
			}
			tmnlList.add(tmnl);
		}
		return  tmnlList;
	}
	
	/**
	 * 提取页面选择的终端（用于保存方案）
	 */
	public List<CtrlParamBean> parseSchemeTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < this.protectTmnlList.size(); i++) {
			String[] tmnl = this.protectTmnlList.get(i).split("`");
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
	 * 从字符串提取信息
	 * @param s
	 * @return
	 */
	public String[] parse(String s){
    	if(s!=null){
    	  String[] s1 = s.split("`");
    	  return s1;
    	}
    	else
    	 return null;
    }
	
	
 /*   *//**
     * 添加终端保电信息
     * @param orgNo
     * @param consNo
     * @param tmnlAssetNo
     * @param ctrlFlag
     * @param staffNo
     * @return
     *//*
	public void addWTmnlPaulPower(String orgNo, String consNo,String tmnlAssetNo, Boolean ctrlFlag, String staffNo) {
		try {
			WTmnlPaulPower w = new WTmnlPaulPower();
			w.setOrgNo(orgNo);
			w.setConsNo(consNo);
			w.setTmnlAssetNo(tmnlAssetNo);
			w.setCtrlFlag(ctrlFlag);
			if (this.autoPaulPower == null || this.autoPaulPower.equals(""))
				w.setAutoPaulPower(null);
			else
				w.setAutoPaulPower(Integer.valueOf(autoPaulPower));
			if (this.securityValue == null || this.securityValue.equals(""))
				w.setSecurityValue(null);
			else
				w.setSecurityValue(Integer.valueOf(this.securityValue));
			if (this.duration == null || this.duration.equals(""))
				w.setDuration(null);
			else
				w.setDuration((short) (Float.valueOf(this.duration) * 2));
			//w.setIsSendSms(false);
			w.setStaffNo(staffNo);
			w.setSaveTime(new Date());
			w.setSendTime(new Date());
			this.iWTmnlPaulPowerManager.savePaulPower(w);
			return;
		} catch (Exception e) {
			logger.error(e);
		}
	}*/
    
    /**
	 * 取终端信息
	 */
	public String generalGridByTree() throws Exception {
		if(null == this.nodeType || "".equals(this.nodeType)){
			return SUCCESS;
   	    }
		PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
		if("usr".equals(this.nodeType)){
			this.wTmnlPaulPowerList =this.iWTmnlPaulPowerManager.queryTmnlByTmnlAssetNo(this.tmnlAssetNo);
    	}
		else if("org".equals(this.nodeType)){
			this.wTmnlPaulPowerList =this.iWTmnlPaulPowerManager.queryTmnlByOrgNo(this.orgNo,this.orgType,pSysUser);
		}
		else if("line".equals(this.nodeType)){
			this.wTmnlPaulPowerList =this.iWTmnlPaulPowerManager.queryTmnlByLineId(this.lineId,pSysUser);
		}
		else if("cgp".equals(this.nodeType) || "ugp".equals(this.nodeType)){
			this.wTmnlPaulPowerList =this.iWTmnlPaulPowerManager.queryTmnlByGroupNo(this.nodeType,this.groupNo);
		}
		else if("sub".equals(this.nodeType)){
			this.wTmnlPaulPowerList =this.iWTmnlPaulPowerManager.queryTmnlBySubsId(this.subsId,pSysUser);
		}
		else if("ctrlScheme".equals(this.nodeType)){
			this.wTmnlPaulPowerList = this.iWTmnlPaulPowerManager.queryTmnlBySchemeId(this.schemeId);
		}
		return SUCCESS;
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

	public String getIsProtect() {
		return isProtect;
	}

	public void setIsProtect(String isProtect) {
		this.isProtect = isProtect;
	}

	public String getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed;
	}
    
	

	/*
	 * public Date getSaveTime() { return saveTime; }
	 * 
	 * public void setSaveTime(Date saveTime) { this.saveTime = saveTime; }
	 * 
	 * public Date getSendTime() { return sendTime; }
	 * 
	 * public void setSendTime(Date sendTime) { this.sendTime = sendTime; }
	 * public Long getCtrlSchemeId() { return ctrlSchemeId; }
	 * 
	 * public void setCtrlSchemeId(Long ctrlSchemeId) { this.ctrlSchemeId =
	 * ctrlSchemeId; }
	 * 
	 * public Boolean getCtrlFlag() { return ctrlFlag; }
	 * 
	 * public void setCtrlFlag(Boolean ctrlFlag) { this.ctrlFlag = ctrlFlag; }
	 * 
	 * public Boolean getIsSendSms() { return isSendSms; }
	 * 
	 * public void setIsSendSms(Boolean isSendSms) { this.isSendSms = isSendSms;
	 * }
	 */
   

	public Integer getAutoPaulPower() {
		return autoPaulPower;
	}

	public void setAutoPaulPower(Integer autoPaulPower) {
		this.autoPaulPower = autoPaulPower;
	}

	public Float getSecurityValue() {
		return securityValue;
	}

	public void setSecurityValue(Float securityValue) {
		this.securityValue = securityValue;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public String getCtrlSchemeName() {
		return ctrlSchemeName;
	}

	public void setCtrlSchemeName(String ctrlSchemeName) {
		this.ctrlSchemeName = ctrlSchemeName;
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

	/*public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}*/

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

	public List<PaulPowerFetchResult> getFetchResultList() {
		return fetchResultList;
	}

	public void setFetchResultList(List<PaulPowerFetchResult> fetchResultList) {
		this.fetchResultList = fetchResultList;
	}

	public List<String> getProtectTmnlList() {
		return protectTmnlList;
	}

	public void setProtectTmnlList(List<String> protectTmnlList) {
		this.protectTmnlList = protectTmnlList;
	}

	public Integer getFLAG() {
		return FLAG;
	}

	public void setFLAG(Integer fLAG) {
		FLAG = fLAG;
	}

	public List<TmnlExecStatusBean> getTmnlExecStatusList() {
		return tmnlExecStatusList;
	}

	public void setTmnlExecStatusList(List<TmnlExecStatusBean> tmnlExecStatusList) {
		this.tmnlExecStatusList = tmnlExecStatusList;
	}

	public List<WTmnlPaulPower> getwTmnlPaulPowerList() {
		return wTmnlPaulPowerList;
	}

	public void setwTmnlPaulPowerList(List<WTmnlPaulPower> wTmnlPaulPowerList) {
		this.wTmnlPaulPowerList = wTmnlPaulPowerList;
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

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
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
