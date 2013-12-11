package com.nari.baseapp.planpowerconsume;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.basicdata.VwLimitType;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.DataCode;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.RmtCtrlPCDto;
import com.nari.orderlypower.RmtCtrlPowerDto;
import com.nari.orderlypower.RmtCtrlSwitchDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlTurn;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.util.CtrlSchemeTypeCode;
import com.opensymphony.xwork2.ActionContext;

/**
 * 遥控Action处理类
 * @author 姜炜超
 */
public class RemoteControlAction extends BaseAction{
	//日志对象
	private static Logger logger = Logger.getLogger(RemoteControlAction.class);
	
	public final static String LOAD_UI_GRID="ui";//遥测电压电流标记
	public final static String LOAD_POWER_GRID="power";//遥测功率标记
	public final static String LOAD_CTRL_GRID="ctrl";//遥测开关拉合闸标记
		
	//返回值
	public boolean success = true;
	
	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private int second = 45;
	
	private String tmnlAssetNo;  //终端资产号
	private String orgNo;        //供电单位编号
	private String consNo;       //用户编号
	private String totalNo;      //总加组号
	private String orgType;		//供电单位类型
	
	private Long schemeId;    //方案id
	private Date newStartDate;  //新添加方案开始时间
	private Date newEndDate;    //新添加结束时间
	private String schemeRemark;//方案备注
	private String limitType;   //限电类型
	private String schemeLabel; //遥控固定值
	private String schemeName;  //方案名
	private String nodeType;    //节点类型
	private String staffNo;     //操作员编号
	private short alertDelayHours;//延迟告警时间
	private short limitMins;//限电时间
	private String turnFlag;//控制开关
	private String operType;//方案操作类型
	private Boolean checkSchemeName;//方案名校验结果，true表示存在，false表示不存在
	private String groupNo;//组号
	private String subsId;//变电站标识
	private String lineId;//线路编号
	private List<String> remoteCtrlList;
	
	private List<WCtrlScheme> schemeList;//方案列表
	private List<VwLimitType> limitTypeList;//限电类型列表
	private List<RemoteControlDto> uiUserGridList;//Grid数据列表
	private List<RemoteControlDto> pwrUserGridList;//Grid数据列表
	private List<RemoteControlDto> userGridList;//Grid数据列表
	private WCtrlScheme schemeByNo;//方案信息，根据方案id查询
	private List<RemoteControlDto> resultList;//拉闸、合闸执行命令后的返回结果
	private List<RmtCtrlPCDto> rtPCList;//遥测电压电流执行命令后的返回结果
	private List<RmtCtrlPowerDto> rtPowerList;//遥测功率执行命令后的返回结果
	private List<RmtCtrlSwitchDto> rtSwitchList;//遥测开关执行命令后的返回结果
	
	private RemoteCtrlManager remoteCtrlManager;//自动注入遥控业务层 

	private String cacheAndTmnlStatus="";

	/**
	 * 遥测电压电流
	 * @return String 
	 * @throws Exception
	 */
    public String remoteTestPC() throws Exception{
    	logger.debug("遥测电压电流开始");
    	TaskDeal taskDeal = new TaskDeal();//生成请求对象
		try{		
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = uiCtrlTmnlList();//对前台传入的特殊格式的数据进行转换赋值
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			
			rtPCList = new ArrayList<RmtCtrlPCDto>();//定义返回前台的列表对象
			
			CtrlParamBean flagBean = tmnlList.get(0);
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//设置参数
					realDataItems.add(setUiRealDataItem(bean,LOAD_UI_GRID));
					if(tmnlList.size() ==1) {
						taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
					}
					continue;
				}
				
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())) {
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
					
					realDataItems = new ArrayList<RealDataItem>();
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
				}
				
				realDataItems.add(setUiRealDataItem(bean, LOAD_UI_GRID));
				//召测最后一个终端
				if(n== tmnlList.size()-1) {
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
				}
			}
			
//			请求一类数据召测
			List<Response> list = taskDeal.getAllResponse(second);
			
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					Response res  = (Response) list.get(i);
					Short Status = res.getTaskStatus();
					List<DbData> datas = res.getDbDatas();
					if(datas!= null && datas.size()>0){
						if (Status == 3&& res.getErrorCode()==null) {//返回状态为成功时，设置值返回前台
							for (int j = 0; j < datas.size(); j++) {
								List<DataCode> dataCodes = datas.get(j).getDataCodes();
								if(dataCodes!= null && dataCodes.size()>0) {
									RmtCtrlPCDto	rtPc =	new RmtCtrlPCDto();
									rtPc.setKeyId(res.getTmnlAssetNo()+datas.get(j).getPn());
									for (int k = 0; k < dataCodes.size();k++) {
										DataCode dataCode = dataCodes.get(k);
										if("1E1141".equals(dataCode.getName())){//1E1141是当前A相电压透明规约编码
											rtPc.setUa("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("1E1142".equals(dataCode.getName())){//1E1142是当前B相电压透明规约编码
											rtPc.setUb("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("1E1143".equals(dataCode.getName())){//1E1143是当前C相电压透明规约编码
											rtPc.setUc("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("1E1151".equals(dataCode.getName())){//1E1151是当前A相电流透明规约编码
											rtPc.setIa("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("1E1152".equals(dataCode.getName())){//1E1152是当前B相电流透明规约编码
											rtPc.setIb("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("1E1153".equals(dataCode.getName())){//1E1153是当前C相电流透明规约编码
											rtPc.setIc("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
									}
									rtPCList.add(rtPc);
								}
							}
						}
					}
				}
			}
			System.out.println(rtPCList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("遥测电压电流结束");
		return SUCCESS;
    }
    
    protected RealDataItem setUiRealDataItem(CtrlParamBean bean, String type)  throws Exception{
    	try{
	    	RealDataItem ritem = new RealDataItem();
			ArrayList<Item> codes = new ArrayList<Item>();
			
			Item item = null;
			List<String> proItemNo = remoteCtrlManager.findProItemNo(type);
			for (int i = 0; i < proItemNo.size(); i++) {
				item = new Item(proItemNo.get(i));
				codes.add(item);
			}
			
			ritem.setCodes(codes);
			if(LOAD_UI_GRID.equals(type)){//遥测电压电流pn为测量点号
				ritem.setPoint(bean.getMpSn());
			} else {//遥测功率pn为总加组号
				ritem.setPoint(bean.getTotalNo());
			}
			return ritem;
    	}catch (Exception e) {
			e.printStackTrace();
		}	
    	return null;
    }
    
    /**
	 * 遥测功率
	 * @return String 
	 * @throws Exception
	 */
    public String remoteTestPower() throws Exception{
    	logger.debug("遥测功率开始");				
    	TaskDeal taskDeal = new TaskDeal();//生成请求对象
		try{			
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = powerCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			
			rtPowerList = new ArrayList<RmtCtrlPowerDto>();
			
			CtrlParamBean flagBean = tmnlList.get(0);
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//设置参数
					realDataItems.add(setUiRealDataItem(bean,LOAD_POWER_GRID));
					continue;
				}
				
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
					
					realDataItems = new ArrayList<RealDataItem>();
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
				}
				
				realDataItems.add(setUiRealDataItem(bean, LOAD_POWER_GRID));
				//召测最后一个终端
				if(n== tmnlList.size()-1) {
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
				}
			}		
			
			//参数下发
			List<Response> list = taskDeal.getAllResponse(second);
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {//循环respnose列表(列表中其实只有一个对象)
					Response res = (Response) list.get(i);//得到Response对象
					List<DbData> datas = res.getDbDatas();//获取数据对象列表(今儿Fn对应几条记录)
					if(datas!= null && datas.size()>0){//循环数据对象列表
						Short Status = res.getTaskStatus();
						if (Status == 3 && res.getErrorCode()==null) {//返回状态为成功时，设置值返回页面
							RmtCtrlPowerDto rtPower = new RmtCtrlPowerDto();
							for (int j = 0; j < datas.size(); j++) {
								List<DataCode> dataCodes = datas.get(j).getDataCodes();//取得小项数据列表
								if(dataCodes!= null && dataCodes.size()>0) {
									rtPower.setKeyId(res.getTmnlAssetNo() + datas.get(j).getPn());
									for (int k = 0; k < dataCodes.size(); k++) {
										DataCode dataCode = dataCodes.get(k);
										if("351111".equals(dataCode.getName())){//351111是当前当前总加有功功率透明规约编码
											rtPower.setTotalP("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
										if("351121".equals(dataCode.getName())){//351121是当前当前总加无功功率透明规约编码
											rtPower.setTotalQ("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
										}
									}
								}
							}
							//将所有终端总加组返回页面, 遥测功率中有用功率为F17,无用功率为F18属于不同FN
							//必须将所有项设置完成才能形成返回对象
							rtPowerList.add(rtPower);
						}
					}
				}
			}
			System.out.println(rtPowerList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("遥测功率结束");
		return SUCCESS;
    }
    
    /**
	 * 遥测开关
	 * @return String 
	 * @throws Exception
	 */
    public String remoteTestSwitch() throws Exception{
    	logger.debug("遥测开关开始");				
		try{
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = switchCtrlTmnlList();
			
			rtSwitchList = remoteCtrlManager.fetchSwitchStatus(tmnlList, second);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("遥测开关结束");
		return SUCCESS;
    }
    
    /**
	 * 拉闸
	 * @return String 
	 * @throws Exception
	 */
    public String close() throws Exception{
    	logger.debug("拉闸开始");				
		try{
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = getPSysUser();
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			//对前台传入的终端列表进行格式处理，以便下发控制
			List<CtrlParamBean> tmnlList = sortCtrlTmnlList();
			
			resultList = remoteCtrlManager.remoteCtrlClose(tmnlList, second, user, turnFlag, alertDelayHours, limitMins, getLocalIp());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("拉闸结束");
		return SUCCESS;
    }
    
    /**
	 * 合闸
	 * @return String 
	 * @throws Exception
	 */
    public String open() throws Exception{
    	logger.debug("合闸开始");				
		try{
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			
			List<CtrlParamBean> tmnlList = sortCtrlTmnlList();
			
			resultList = remoteCtrlManager.remoteCtrlOpen(tmnlList, second, user, turnFlag, getLocalIp());

		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("合闸结束");
		return SUCCESS;
    }
    
	/**
	 * 加载遥测电压电流Grid数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadUiGridData() throws Exception{
    	logger.debug("遥控加载用户信息开始");
    	if(null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	//获取操作员编号
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
    	
    	if ("org".equals(nodeType)) {
    		uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridByOrgNo(orgNo,orgType, user, LOAD_UI_GRID);
		} else if ("usr".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridByTmnlAssetNo(tmnlAssetNo, LOAD_UI_GRID);
		} else if ("line".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridByLineId(lineId, user, LOAD_UI_GRID);
		} else if ("cgp".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridByGroupNo(groupNo, LOAD_UI_GRID);
		} else if ("ugp".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridByUgpGroupNo(groupNo, LOAD_UI_GRID);
		} else if ("sub".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridBySubsId(subsId, user, LOAD_UI_GRID);
		} else if ("schemeId".equals(nodeType)) {
			uiUserGridList = remoteCtrlManager.queryRemoteCtrlGridBySchemeNo(schemeId, LOAD_UI_GRID);
		} else {
			return SUCCESS;
		}
    	logger.debug("遥控加载用户信息结束");
    	return SUCCESS;
    }
    
	/**
	 * 加载遥测功率Grid数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadPowerGridData() throws Exception{
    	logger.debug("遥控加载用户信息开始");
    	if(null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
    	
    	if ("org".equals(nodeType)) {
    		pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridByOrgNo(orgNo,orgType, user, LOAD_POWER_GRID);
		} else if ("usr".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridByTmnlAssetNo(tmnlAssetNo, LOAD_POWER_GRID);
		} else if ("line".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridByLineId(lineId, user, LOAD_POWER_GRID);
		} else if ("cgp".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridByGroupNo(groupNo, LOAD_POWER_GRID);
		} else if ("ugp".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridByUgpGroupNo(groupNo, LOAD_POWER_GRID);
		} else if ("sub".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridBySubsId(subsId, user, LOAD_POWER_GRID);
		} else if ("schemeId".equals(nodeType)) {
			pwrUserGridList = remoteCtrlManager.queryRemoteCtrlGridBySchemeNo(schemeId, LOAD_POWER_GRID);
		} else {
			return SUCCESS;
		}
    	logger.debug("遥控加载用户信息结束");
    	return SUCCESS;
    }
    
	/**
	 * 加载遥测开关拉合闸Grid数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("遥控加载用户信息开始");
    	if(null == nodeType || "".equals(nodeType)){
    		return SUCCESS;
    	}
    	
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
    	
    	if ("org".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridByOrgNo(orgNo,orgType, user, LOAD_CTRL_GRID);
		} else if ("usr".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridByTmnlAssetNo(tmnlAssetNo, LOAD_CTRL_GRID);
		} else if ("line".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridByLineId(lineId, user, LOAD_CTRL_GRID);
		} else if ("cgp".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridByGroupNo(groupNo, LOAD_CTRL_GRID);
		} else if ("ugp".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridByUgpGroupNo(groupNo, LOAD_CTRL_GRID);
		} else if ("sub".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridBySubsId(subsId, user, LOAD_CTRL_GRID);
		} else if ("schemeId".equals(nodeType)) {
			userGridList = remoteCtrlManager.queryRemoteCtrlGridBySchemeNo(schemeId, LOAD_CTRL_GRID);
		} else {
			return SUCCESS;
		}
    	logger.debug("遥控加载用户信息结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载方案，在点击方案加载之后的事件处理，主要是把方案显示在页面上，供选择
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadScheme()throws Exception{
    	logger.debug("遥控加载方案开始");
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		
    	schemeList = remoteCtrlManager.querySchemeListByType(CtrlSchemeTypeCode.REMOTE_CTRL, staffNo);
    	logger.debug("遥控加载方案结束");
    	return SUCCESS;
    }
    
    /**
	 * 当操作员选择新增方案的时候，校验该是否存在
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String checkName() throws Exception{
    	PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
    	String schemeType = remoteCtrlManager.querySchemeType("遥控");
		//先查询该操作员是否有方案，如果有就填入id（orgNo是操作员的）
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		PropertyFilter s = new PropertyFilter("staffNo", this.staffNo);
		filters.add(s);
		PropertyFilter c = new PropertyFilter("ctrlSchemeType", schemeType);
		filters.add(c);
		PropertyFilter n = new PropertyFilter("ctrlSchemeName", schemeName);
		filters.add(n);
		List<WCtrlScheme> schemeList = this.remoteCtrlManager.querySchemeListByCond(filters);
		this.checkSchemeName = false;
		if(null != schemeList && 0 < schemeList.size()){
    		if(null != operType && "1".equals(operType)){
    			this.checkSchemeName = true;
    		}
    	}	
		return SUCCESS;
    }
    
    /**
	 * 保存或修改方案，保存为方案弹出界面，点击确定时的事件处理
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String addSolution() throws Exception{
    	logger.debug("遥控保存方案开始");
    	
    	PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		
		//对前台传入的终端列表进行格式处理，以便下发控制
		List<CtrlParamBean> tmnlList = sortCtrlTmnlList();

		//新建方案对象
        WCtrlScheme scheme = new WCtrlScheme();
		
		scheme = new WCtrlScheme();
		if(schemeId != null && !"".equals(schemeId)){
			scheme.setCtrlSchemeId(schemeId);
		}
		scheme.setCtrlSchemeName(schemeName);
		scheme.setCtrlSchemeType(CtrlSchemeTypeCode.REMOTE_CTRL);
		scheme.setStaffNo(staffNo);
		scheme.setOrgNo(user.getOrgNo());
		scheme.setCreateDate(new Date());

		scheme.setCtrlDateStart(dateToSqlDate(newStartDate));
		scheme.setCtrlDateEnd(dateToSqlDate(newEndDate));
		scheme.setSchemeRemark(schemeRemark);
		scheme.setIsValid(1L);
		
		//建立方案用户
		List<WTmnlTurn> turnList = new ArrayList<WTmnlTurn>();
		WTmnlTurn turn = null;
		for(int i = 0; i < tmnlList.size(); i++){
			CtrlParamBean bean = tmnlList.get(i);
			
			turn = new WTmnlTurn();
			turn.setOrgNo(user.getOrgNo());
			turn.setConsNo(user.getEmpNo());
			turn.setTmnlAssetNo(bean.getTmnlAssetNo());
			turn.setAlertDelayHours(alertDelayHours);
			turn.setLimitMins(limitMins);
			turn.setIsSendSms(false);
			turn.setStatusCode("01");
			turn.setStaffNo(staffNo);
			turn.setSaveTime(dateToSqlDate(new Date()));
			turn.setTurnFlag(turnFlag);
			
			turnList.add(turn);
		}
		remoteCtrlManager.saveOrUpdateScheme(scheme, turnList);
		
		logger.debug("遥控保存方案结束");
    	return SUCCESS;
    }
    
    /**
	 * 加载限电类型，在点击保存为方案后的事件处理，主要是把限电类型显示在combx中，供选择
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
    public String loadSchemeData() throws Exception{
    	logger.debug("遥控加载限电类型开始");
    	limitTypeList = remoteCtrlManager.queryLimitTypeList();
    	
    	Long schemeNo = 0L;
    	if(null != this.schemeId && !"".equals(this.schemeId)){
    		schemeNo = Long.valueOf(this.schemeId);
    	}
    	
    	//先查询该操作员是否有方案，如果有就填入id（orgNo是操作员的）
		List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
		PropertyFilter s = new PropertyFilter("ctrlSchemeId", schemeNo);
		filters.add(s);
		PropertyFilter c = new PropertyFilter("ctrlSchemeType", "05");
		filters.add(c);
		List<WCtrlScheme> schemeList = this.remoteCtrlManager.querySchemeListByCond(filters);
		if(null != schemeList && 0 < schemeList.size()){
			schemeByNo = (WCtrlScheme)schemeList.get(0);
			if(null != schemeByNo && null != schemeByNo.getCtrlSchemeName()){
				String schemeName = schemeByNo.getCtrlSchemeName().substring(4);
				schemeByNo.setCtrlSchemeName(schemeName);
			}
		}
		if(null == schemeByNo){
			schemeByNo = new WCtrlScheme();
		}
    	logger.debug("遥控加载限电类型结束");
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
    
    /**
	 * 获取本机ip
	 * @return String 本地ip
	 * @throws Exception
	 */
    private String getLocalIp() throws Exception{
    	InetAddress addr = InetAddress.getLocalHost();
	    String ipAddr=addr.getHostAddress().toString();//获得本机IP
	    return ipAddr;
    }

	/**
	 * 将页面选择的总加组按所属终端分类排序
	 */
	public List<CtrlParamBean> powerCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < remoteCtrlList.size(); i++) {
			String[] remoteCtrl = remoteCtrlList.get(i).split("`");
			//前台传入的终端和总加组号，可能为空字符串或 undefined 应剔除
			if("".equals(remoteCtrl[0])||"".equals(remoteCtrl[1])
					|| "undefined".equals(remoteCtrl[0])
					|| "undefined".equals(remoteCtrl[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(remoteCtrl[0]);
			if(!"".equals(remoteCtrl[1]) && remoteCtrl[1] != null && !"undefined".equals(remoteCtrl[1])){
				bean.setTotalNo(Short.parseShort(remoteCtrl[1]));
			}
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的遥测开关终端列表
	 */
	protected List<CtrlParamBean> switchCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < remoteCtrlList.size(); i++) {
			String[] remoteCtrl = remoteCtrlList.get(i).split("`");
			//前台传入的终端和总加组号，可能为空字符串或 undefined 应剔除
			if("".equals(remoteCtrl[0])|| "undefined".equals(remoteCtrl[0])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(remoteCtrl[0]);
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的总加组按所属终端分类排序--测量点
	 */
	protected List<CtrlParamBean> uiCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < remoteCtrlList.size(); i++) {
			String[] remoteCtrl = remoteCtrlList.get(i).split("`");
			//前台传入的终端和测量点号，可能为空字符串或 undefined 应剔除
			if ("".equals(remoteCtrl[0]) || "".equals(remoteCtrl[1])
					|| "undefined".equals(remoteCtrl[0])
					|| "undefined".equals(remoteCtrl[1])) {
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setTmnlAssetNo(remoteCtrl[0]);
			if(!"".equals(remoteCtrl[1]) && remoteCtrl[1] != null && !"undefined".equals(remoteCtrl[1])){
				bean.setMpSn(Short.parseShort(remoteCtrl[1]));
			}
			
			tmnlList.add(bean);
		}
		
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的总加组按所属终端分类排序
	 */
	protected List<CtrlParamBean> sortCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < remoteCtrlList.size(); i++) {
			String[] remoteCtrl = remoteCtrlList.get(i).split("`");
			//前台传入的终端和规约编码，可能为空字符串或 undefined 应剔除
			if("".equals(remoteCtrl[2])||"".equals(remoteCtrl[3])
					|| "undefined".equals(remoteCtrl[2])
					|| "undefined".equals(remoteCtrl[3])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(remoteCtrl[0]);
			bean.setConsNo(remoteCtrl[1]);
			bean.setTmnlAssetNo(remoteCtrl[2]);
			bean.setProtocolCode(remoteCtrl[3]);
			
			tmnlList.add(bean);
		}
		//按终端资产编号排序
		Collections.sort(tmnlList, new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
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

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
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

	public String getSchemeLabel() {
		return schemeLabel;
	}

	public void setSchemeLabel(String schemeLabel) {
		this.schemeLabel = schemeLabel;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public short getAlertDelayHours() {
		return alertDelayHours;
	}

	public void setAlertDelayHours(short alertDelayHours) {
		this.alertDelayHours = alertDelayHours;
	}

	public short getLimitMins() {
		return limitMins;
	}

	public void setLimitMins(short limitMins) {
		this.limitMins = limitMins;
	}

	public String getTurnFlag() {
		return turnFlag;
	}

	public void setTurnFlag(String turnFlag) {
		this.turnFlag = turnFlag;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public Boolean getCheckSchemeName() {
		return checkSchemeName;
	}

	public void setCheckSchemeName(Boolean checkSchemeName) {
		this.checkSchemeName = checkSchemeName;
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

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public List<String> getRemoteCtrlList() {
		return remoteCtrlList;
	}

	public void setRemoteCtrlList(List<String> remoteCtrlList) {
		this.remoteCtrlList = remoteCtrlList;
	}
	
	public void setRemoteCtrlManager(RemoteCtrlManager remoteCtrlManager) {
		this.remoteCtrlManager = remoteCtrlManager;
	}
	
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
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
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<RemoteControlDto> getUserGridList() {
		return userGridList;
	}

	public void setUserGridList(List<RemoteControlDto> userGridList) {
		this.userGridList = userGridList;
	}

	public void setResultList(List<RemoteControlDto> resultList) {
		this.resultList = resultList;
	}

	public void setRtPCList(List<RmtCtrlPCDto> rtPCList) {
		this.rtPCList = rtPCList;
	}

	public void setRtPowerList(List<RmtCtrlPowerDto> rtPowerList) {
		this.rtPowerList = rtPowerList;
	}

	public void setRtSwitchList(List<RmtCtrlSwitchDto> rtSwitchList) {
		this.rtSwitchList = rtSwitchList;
	}

	public WCtrlScheme getSchemeByNo() {
		return schemeByNo;
	}
	
	public List<RemoteControlDto> getResultList() {
		return resultList;
	}
	
	public List<RmtCtrlPCDto> getRtPCList() {
		return rtPCList;
	}

	public List<RmtCtrlPowerDto> getRtPowerList() {
		return rtPowerList;
	}

	public List<RmtCtrlSwitchDto> getRtSwitchList() {
		return rtSwitchList;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public List<RemoteControlDto> getUiUserGridList() {
		return uiUserGridList;
	}

	public void setUiUserGridList(List<RemoteControlDto> uiUserGridList) {
		this.uiUserGridList = uiUserGridList;
	}

	public List<RemoteControlDto> getPwrUserGridList() {
		return pwrUserGridList;
	}

	public void setPwrUserGridList(List<RemoteControlDto> pwrUserGridList) {
		this.pwrUserGridList = pwrUserGridList;
	}

	public void setSchemeByNo(WCtrlScheme schemeByNo) {
		this.schemeByNo = schemeByNo;
	}

	public String getCacheAndTmnlStatus() {
		return cacheAndTmnlStatus;
	}

	public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
		this.cacheAndTmnlStatus = cacheAndTmnlStatus;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getSchemeRemark() {
		return schemeRemark;
	}

	public void setSchemeRemark(String schemeRemark) {
		this.schemeRemark = schemeRemark;
	}
}
