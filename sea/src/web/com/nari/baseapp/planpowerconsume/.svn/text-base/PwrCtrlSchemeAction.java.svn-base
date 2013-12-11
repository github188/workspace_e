package com.nari.baseapp.planpowerconsume;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.PwrctrlSchemeExecBean;
import com.nari.orderlypower.PwrctrlTempDetailBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.orderlypower.TmnlPwrctrlUserBean;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WFactctrlTemplate;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WPwrctrlTemplate;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.orderlypower.WTmnlCtrlStatus;
import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.orderlypower.WTmnlPwrctrlDetail;
import com.nari.orderlypower.WTmnlPwrctrlDetailId;
import com.nari.privilige.PSysUser;
import com.nari.util.CtrlSchemeTypeCode;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功率控制方案及参数设置Action
 * @author 杨传文
 */
public class PwrCtrlSchemeAction extends BaseAction {
	private static Logger logger = Logger.getLogger(PwrCtrlSchemeAction.class);
	
	private PowerCtrlQueryManager powerCtrlQueryManager;
	private WPwrctrlTemplateManager wPwrctrlTemplateManager;
	private RemoteCtrlManager remoteCtrlManager;//自动注入遥控业务层 
	private WPwrctrlSchemeManager wPwrctrlSchemeManager;
	private WFactctrlTemplateManager wFactctrlTemplateManager;
	private WtmnlCtrlStatusManger wtmnlCtrlStatusManger;
	
//	参数
	private WCtrlScheme scheme;
	private String nodeType;
	private Integer alertTime1;//告警时间
	private Integer alertTime2;
	private Integer alertTime3;
	private Integer alertTime4;
	private Boolean box1;//告警轮次
	private Boolean box2;
	private Boolean box3;
	private Boolean box4;
	private Short floatDownWtime;//定值滑差时间
	private Short powerFreezetime;//冻结延时时间
	private Short floatDownPercent;//浮动系数
	private Short ctrlTime;
	private WFloatDownCtrl floatDownCtrl;
	private String staffNo;//操作员编号
	private Long lineId;
	private String groupNo;
	private Long subsId;
	
	private int second = 45;
	
	private String templateName;
	private String templateId;
	private Byte curveNo;
	private String sectionNo;
	private String tmnlAssetNo; // 终端资产号
	private String orgNo; // 供电单位编号
	private String orgType;
	private String consNo; // 用户编号
	private String totalNo; // 总加组号

	private String consNos[]; // 用户编号数组
	private String orgNos[]; // 供电单位编号数组
	private String tmnlAssetNos[];// 终端编号数组
	private String totalNos[]; // 总加组号数组
	private List<String> pwrCtrlList;
	private Long schemeId;    //方案id
	private String ctrlSchemeName;//方案名称
	private long start;
	private int limit=Constans.DEFAULT_PAGE_SIZE;
	
//	返回值
	private List<WCtrlScheme> schemeList;//方案列表
	private boolean success = true;
	private List<PwrctrlSchemeExecBean> pwrctrlSchemeExecList;
	private long totalCount;
	private String schemeCurveNo;
	private String validSectionNo;
	private Integer secLength;
	private Boolean checkResult;//方案名称校验结果
	
	private List<PwrctrlTempDetailBean> reslutDetailList;
	private List<TmnlPwrctrlUserBean> pwrctrlResultList;//时段控召测返回结果
	private List<WTmnlFactctrl> factctrlResultList;//厂休控召测返回结果
	private List<TmnlExecStatusBean> execResultList;//
	private String cacheAndTmnlStatus="";
	
	//自动注入控制方案业务层
	private IWCtrlSchemeManager iWCtrlSchemeManager;
	public void setiWCtrlSchemeManager(IWCtrlSchemeManager iWCtrlSchemeManager) {
		this.iWCtrlSchemeManager = iWCtrlSchemeManager;
	}
/*-----------------------------------------------------------------时段控-------------------------------------------------------*/	
	/**
	 * 时段控参数下发 TODO
	 * @return
	 * @throws Exception
	 */
	public String sdkParamSend() throws Exception {
		logger.debug("时段控参数下发开始");
		TaskDeal taskDeal = new TaskDeal();//生成请求对象,针对p0
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
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}

//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
//			获取模板和模板明细信息
			WPwrctrlTemplate template = wPwrctrlTemplateManager.findWPwrctrlTemplateByName(templateName);
			if(template == null) {
				logger.error("-----功率时段控参数下发失败，该模板不存在-----");
				success = false;
				return SUCCESS;
			}
			List<WPwrctrlTemplateDetail> tmpDetailList = wPwrctrlTemplateManager.findWPwrctrlTemplateDetailByTemplateName(templateName);
			
			execResultList = new ArrayList<TmnlExecStatusBean>();//下发结果列表返回页面
			
			CtrlParamBean flagBean = tmnlList.get(0);
			
			
			List<Item> sdItems = null;
			List<Item> floatItems = null;
			List<Item> constItems = null;
			Map<String, List<Item>> sdItemsMap = new HashMap<String, List<Item>>(); 
			Map<String, List<Item>> floatItemsMap = new HashMap<String, List<Item>>(); 
			Map<String, List<Item>> constItemsMap = new HashMap<String, List<Item>>(); 
			List<String> protItemSnList = null;//参数块内序号列表
			List<String> blockIndexList = null;//参数块序号列表
			Map<String, List<String>> protItemSnMap = new HashMap<String, List<String>>();
			Map<String, List<String>> blockIndexMap = new HashMap<String, List<String>>();
			
//			终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			
//			循环总加组,分别下发参数
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
//				参数项定义
				Item item = null;
				ParamItem sdItem = null;
				ParamItem floatItem = null;
				String[] strSd = null;
				ParamItem constItem = null;
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					paramList = new ArrayList<ParamItem>();
					/*设置时段控时段*/
					sdItem = new ParamItem();
					sdItem.setFn((short) 18);//设置FN号
					sdItem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	//				编码对象集合
					sdItems = new ArrayList<Item>();
					strSd = new String[12];
					strSd = getSdksd(tmpDetailList);
					
					for (int i = 0; i < strSd.length; i++) {
						item = new Item(bean.getProtocolCode()+"04120"+String.format("%1$02X", i+1));//将16进制数字格式化成2位长度的10进制字符
						item.setValue(strSd[i]);
						sdItems.add(item);
					}
					
					sdItem.setItems((ArrayList<Item>) sdItems);
					paramList.add(sdItem);
					
					/*模板选中执行浮动系数时, 设置时段控浮动系数*/
					if(template.getIsExec()){
						floatItem = new ParamItem();
						floatItem.setFn((short)19);//设置FN号
						floatItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	//					编码对象集合
						floatItems = new ArrayList<Item>();
						item = new Item(bean.getProtocolCode()+"0413001");
						item.setValue(String.valueOf(template.getFloatValue()));
						floatItems.add(item);
						floatItem.setItems((ArrayList<Item>) floatItems);
						paramList.add(floatItem);
					}
						
					/*设置时段控定值*/
					constItem = new ParamItem();
					constItem.setFn((short) 41);//设置FN号
					constItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
//						编码对象集合
					constItems = new ArrayList<Item>();
					protItemSnList = new ArrayList<String>();//参数块内序号列表
					blockIndexList = new ArrayList<String>();//参数块序号列表
					
					String const1 = "0";
					String const2 = "0";
					String const3 = "0";
					int fanNum = 0;
//						设置方案标示
					for (int i = 0; i < tmpDetailList.size(); i++) {
						WPwrctrlTemplateDetail detail = tmpDetailList.get(i);
						if(detail.getId().getConst1()!= null && detail.getId().getConst1() != 0){
							const1 = "1";
						}
						if(detail.getId().getConst2()!= null && detail.getId().getConst2() != 0) {
							const2 = "1";
						}
						if(detail.getId().getConst3()!= null && detail.getId().getConst3() != 0){
							const3 = "1";
						}
					}
					
					if("1".equals(const1)){	fanNum++; }
					if("1".equals(const2)){	fanNum++; }
					if("1".equals(const3)){	fanNum++; }
					
					item = new Item(bean.getProtocolCode()+"0429001");
					item.setValue("00000"+const3+const2+const1);
					protItemSnList.add("1");//方案块内序号：1
					blockIndexList.add("0");//方案块序号：0
					constItems.add(item);
					
//						循环方案
					for(int k = 0; k<fanNum ; k++){
						String[] sdh = new String [8];
						for (int i = 0; i < tmpDetailList.size(); i++) {
								sdh[i] = "1";
						}
						for (int i = 0; i < sdh.length; i++) {
							if(sdh[i]==null||"".equals(sdh[i])) {
								sdh[i] = "0";
							}
						}
						StringBuffer sdhBuf = new StringBuffer();
						for (int i = 0; i < sdh.length; i++) {
							sdhBuf.append(sdh[sdh.length-i-1]);
						}
//							设置时段号
						item = new Item(bean.getProtocolCode()+"0429002");
						item.setValue(sdhBuf.toString());
						protItemSnList.add("2");//时段块内序号：2
						blockIndexList.add(Integer.toString(k+1));//时段块序号:方案序号，值为：1，2，3其一
						constItems.add(item);
						
						for (int i = 0; i < tmpDetailList.size(); i++) {
							WPwrctrlTemplateDetail detail = tmpDetailList.get(i);
//								设置时段定值
							item = new Item(bean.getProtocolCode()+"0429003");
							if(k==0)
								item.setValue(String.valueOf(detail.getId().getConst1()));//方案1设置const1
							if(k==1)
								item.setValue(String.valueOf(detail.getId().getConst2()));//方案2设置const2
							if(k==2)
								item.setValue(String.valueOf(detail.getId().getConst3()));//方案3设置const3
							
							protItemSnList.add("3");//时段定值块内序号：3
							blockIndexList.add(Integer.toString(k+1)+"."+Integer.toString(i+1));//时段定值块序号：时段块序号.时段号
							constItems.add(item);
						}
					}
					
					constItem.setItems((ArrayList<Item>) constItems);
					paramList.add(constItem);
					
					//如果下发终端只有一个,则在此处下发
					if(tmnlList.size() ==1) {
						//将参数信息保存，用于记录日志
						sdItemsMap.put(bean.getTmnlAssetNo(), sdItems);
						floatItemsMap.put(bean.getTmnlAssetNo(), floatItems);
						constItemsMap.put(bean.getTmnlAssetNo(), constItems);
						protItemSnMap.put(bean.getTmnlAssetNo(), protItemSnList);
						blockIndexMap.put(bean.getTmnlAssetNo(), blockIndexList);
						
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//将参数信息保存，用于记录日志
					sdItemsMap.put(bean.getTmnlAssetNo(), sdItems);
					floatItemsMap.put(bean.getTmnlAssetNo(), floatItems);
					constItemsMap.put(bean.getTmnlAssetNo(), constItems);
					protItemSnMap.put(bean.getTmnlAssetNo(), protItemSnList);
					blockIndexMap.put(bean.getTmnlAssetNo(), blockIndexList);
					//找到新的终端，先下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					logger.debug("--调用接口--");
					
					//终端list重置
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					//参数list重置
					paramList = new ArrayList<ParamItem>();
					/*设置时段控时段*/
					sdItem = new ParamItem();
					sdItem.setFn((short) 18);//设置FN号
					sdItem.setPoint((short) 0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	//				编码对象集合
					sdItems = new ArrayList<Item>();
					strSd = new String[12];
					strSd = getSdksd(tmpDetailList);
					
					for (int i = 0; i < strSd.length; i++) {
						item = new Item(bean.getProtocolCode()+"04120"+String.format("%1$02X", i+1));
						item.setValue(strSd[i]);
						sdItems.add(item);
					}
					sdItem.setItems((ArrayList<Item>) sdItems);
					paramList.add(sdItem);
					
					/*模板选中执行浮动系数时, 设置时段控浮动系数*/
					if(template.getIsExec()){
						floatItem = new ParamItem();
						floatItem.setFn((short)19);//设置FN号
						floatItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	//					编码对象集合
						floatItems = new ArrayList<Item>();
						item = new Item(bean.getProtocolCode()+"0413001");
						item.setValue(String.valueOf(template.getFloatValue()));
						floatItems.add(item);
						floatItem.setItems((ArrayList<Item>) floatItems);
						paramList.add(floatItem);
					}
					flagBean = bean;
				}
				
				/*设置时段控定值*/
				constItem = new ParamItem();
				constItem.setFn((short) 41);//设置FN号
				constItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
//				编码对象集合
				constItems = new ArrayList<Item>();
				protItemSnList = new ArrayList<String>();//参数块内序号列表
				blockIndexList = new ArrayList<String>();//参数块序号列表
				
				String const1 = "0";
				String const2 = "0";
				String const3 = "0";
				int fanNum = 0;//记录有效方案数量
//				设置方案标示
				for (int i = 0; i < tmpDetailList.size(); i++) {
					WPwrctrlTemplateDetail detail = tmpDetailList.get(i);
					if(detail.getId().getConst1()!= null && detail.getId().getConst1() != 0){
						const1 = "1";
					}
					if(detail.getId().getConst2()!= null && detail.getId().getConst2() != 0) {
						const2 = "1";
					}
					if(detail.getId().getConst3()!= null && detail.getId().getConst3() != 0){
						const3 = "1";
					}
				}
				
				if("1".equals(const1)){	fanNum++; }
				if("1".equals(const2)){	fanNum++; }
				if("1".equals(const3)){	fanNum++; }
				
				item = new Item(bean.getProtocolCode()+"0429001");
				item.setValue("00000"+const3+const2+const1);
				protItemSnList.add("1");//方案块内序号：1
				blockIndexList.add("0");//方案块序号：0
				constItems.add(item);
				
//				循环方案
				for(int k = 0; k<fanNum ; k++){
					String[] sdh = new String [8];
					for (int i = 0; i < tmpDetailList.size(); i++) {
							sdh[i] = "1";
					
					}
					for (int i = 0; i < sdh.length; i++) {
						if(sdh[i]==null||"".equals(sdh[i])) {
							sdh[i] = "0";
						}
					}
					StringBuffer sdhBuf = new StringBuffer();
					for (int i = 0; i < sdh.length; i++) {
						sdhBuf.append(sdh[sdh.length-i-1]);
					}
//					设置时段号
					item = new Item(bean.getProtocolCode()+"0429002");
					item.setValue(sdhBuf.toString());
					protItemSnList.add("2");//时段块内序号：2
					blockIndexList.add(Integer.toString(k+1));//时段块序号:方案序号，值为：1，2，3其一
					constItems.add(item);
					
					for (int i = 0; i < tmpDetailList.size(); i++) {
						WPwrctrlTemplateDetail detail = tmpDetailList.get(i);
//						设置时段定值
						item = new Item(bean.getProtocolCode()+"0429003");
						if(k==0)
							item.setValue(String.valueOf(detail.getId().getConst1()));//方案1设置const1
						if(k==1)
							item.setValue(String.valueOf(detail.getId().getConst2()));//方案2设置const2
						if(k==2)
							item.setValue(String.valueOf(detail.getId().getConst3()));//方案3设置const3
						
						protItemSnList.add("3");//时段定值块内序号：3
						blockIndexList.add(Integer.toString(k+1)+"."+Integer.toString(i+1));//时段定值块序号：时段块序号.时段号
						constItems.add(item);
					}
				}
				
				constItem.setItems((ArrayList<Item>) constItems);
				paramList.add(constItem);
				
				if(n== tmnlList.size() - 1) {
					//将参数信息保存，用于记录日志
					sdItemsMap.put(bean.getTmnlAssetNo(), sdItems);
					floatItemsMap.put(bean.getTmnlAssetNo(), floatItems);
					constItemsMap.put(bean.getTmnlAssetNo(), constItems);
					protItemSnMap.put(bean.getTmnlAssetNo(), protItemSnList);
					blockIndexMap.put(bean.getTmnlAssetNo(), blockIndexList);
					//将最后一个终端下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					logger.debug("--调用接口--");
				}
			}

			logger.debug("等待结果...");
			Response res = null;
			List<Response> list = taskDeal.getAllResponse(second);
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();
					List<ResponseItem> resItems = res.getResponseItems();
					
//					记录终端操作日志
					LOpTmnlLog l = new LOpTmnlLog();
					
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率时段控");
					l.setOpButton("参数下发");
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setOpType((short)1);//参数下发
					l.setOpObj((short)4);
					l.setCurStatus(Status.toString());
					
					List<String> innerBlockSn = protItemSnMap.get(res.getTmnlAssetNo());
					List<String> blockSn = blockIndexMap.get(res.getTmnlAssetNo());
					
					//记录时段定值日志
					List<Item> sdItemList = sdItemsMap.get(res.getTmnlAssetNo());
					if(sdItemList!= null && sdItemList.size()>0){
						for (int j = 0; j < sdItemList.size(); j++) {
							Short totalNo= resItems.get(j).getPn();
							l.setOpObjNo((long)totalNo);
							
							Item it = sdItemList.get(j);
							l.setInnerBlockSn(Integer.parseInt(innerBlockSn.get(j)));
							l.setBlockSn(blockSn.get(j));
							l.setCurValue(it.getValue());
							l.setProtItemNo(it.getCode());
							
							iLOpTmnlLogManager.saveTmnlLog(l);
						}
					}
					
					//记录时段定值日志
					List<Item> constItemList = constItemsMap.get(res.getTmnlAssetNo());
					if(constItemList!= null && constItemList.size()>0){
						for (int j = 0; j < constItemList.size(); j++) {
							Item it = constItemList.get(j);
							l.setInnerBlockSn(Integer.parseInt(innerBlockSn.get(j)));
							l.setBlockSn(blockSn.get(j));
							l.setCurValue(it.getValue());
							l.setProtItemNo(it.getCode());
							
							iLOpTmnlLogManager.saveTmnlLog(l);
						}
					}
					
					//记录时段浮动系数日志
					List<Item> floatItemList = floatItemsMap.get(res.getTmnlAssetNo());
					if(floatItemList!= null && floatItemList.size()>0){
						for (int j = 0; j < floatItemList.size(); j++) {
							Short totalNo= resItems.get(j).getPn();
							l.setOpObjNo((long)totalNo);
							
							l.setOpObj((short)1);
							Item it = floatItemList.get(j);
							l.setCurValue(it.getValue());
							l.setProtItemNo(it.getCode());
							
							iLOpTmnlLogManager.saveTmnlLog(l);
						}
					}
					
					for (int j = 0; j < resItems.size(); j++) {
						if (Status == 3&& res.getErrorCode()==null) {
							execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+resItems.get(i).getPn(),"2"));
						} else {
							execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+resItems.get(i).getPn(),"-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()+bean.getTotalNo(), "-1"));
				}
			}
			
			logger.debug("execResultList: "+execResultList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("时段控参数下发结束");
		return SUCCESS;
	}
	
	/**
	 * 时段控参数召测 TODO
	 * @return
	 * @throws Exception
	 */
	public String sdkParamFecth() throws Exception {
		logger.debug("时段控参数召测开始");
		TaskDeal taskDeal = new TaskDeal();//生成请求对象,针对时段
		try{
			if(!TaskDeal.isCacheRunning()){
				cacheAndTmnlStatus = "前置集群通信中断";
				return SUCCESS;
			}
			
			if(!TaskDeal.isFrontAlive()){
				cacheAndTmnlStatus = "前置集群服务中断";
				return SUCCESS;
			}
			List<CtrlParamBean> tmnlList = queryCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			
			pwrctrlResultList = new ArrayList<TmnlPwrctrlUserBean>();
			
			CtrlParamBean flagBean = tmnlList.get(0);
			List<String> tmnlAssetNoList = new ArrayList<String>();
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			
			for (int m = 0; m < tmnlList.size(); m++) {
				CtrlParamBean bean = tmnlList.get(m);
			
//				参数项设置
				List<Item> items = null;
				if(m==0){
					//终端资产号集合
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					/*设置时段控时段*/
					ParamItem constItem = new ParamItem();
					constItem.setFn((short)18);//设置FN号
					constItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	
					items = new ArrayList<Item>();
					constItem.setItems((ArrayList<Item>) items);
					paramList.add(constItem);

					/*设置时段控浮动系数*/
					ParamItem floatItem = new ParamItem();
					floatItem.setFn((short)19);//设置FN号
					floatItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	
					items = new ArrayList<Item>();
					floatItem.setItems((ArrayList<Item>) items);
					paramList.add(floatItem);
					
					/*设置时段控定值*/
					ParamItem sdItem = new ParamItem();
					sdItem.setFn((short)41);//设置FN号
					sdItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					
					items = new ArrayList<Item>();
					sdItem.setItems((ArrayList<Item>) items);
					paramList.add(sdItem);
					
					if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行参数召测
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
					logger.debug("--调用接口--");
					
					paramList = new ArrayList<ParamItem>();
					/*设置时段控时段*/
					ParamItem constItem = new ParamItem();
					constItem.setFn((short)18);//设置FN号
					constItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	
					items = new ArrayList<Item>();
					constItem.setItems((ArrayList<Item>) items);
					paramList.add(constItem);

					/*设置时段控浮动系数*/
					ParamItem floatItem = new ParamItem();
					floatItem.setFn((short)19);//设置FN号
					floatItem.setPoint((short)0);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
	
					items = new ArrayList<Item>();
					floatItem.setItems((ArrayList<Item>) items);
					paramList.add(floatItem);
					
					flagBean = bean;
				}
				
				/*设置时段控定值*/
				ParamItem sdItem = new ParamItem();
				sdItem.setFn((short)41);//设置FN号
				sdItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				
				items = new ArrayList<Item>();
				sdItem.setItems((ArrayList<Item>) items);
				paramList.add(sdItem);
				
				if(m== tmnlList.size()-1) {
					//参数召测
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
					logger.debug("--调用接口--");
				}
			}	
		  //获取pn的所有结果
			logger.debug("等待结果...");
			List<Response> pnList =  taskDeal.getAllResponse(second);
			Response res = null;
		    if(null != pnList && 0 < pnList.size()){
		    	for(int i = 0; i < pnList.size(); i++){
		    	    res = (Response)pnList.get(i);
		    	    //TmnlPwrctrlUserBean tp = new TmnlPwrctrlUserBean();
		    	    Short status =res.getTaskStatus();
		    	   System.out.println("response==" +res);
		    	   if (status ==3 ||status==4) {
		    		   List <ResponseItem> itemList = res.getResponseItems();
		    		   //将终端返回的responseItems list按pn值排序，使p0（浮动系数，时段）在前，pn（定值）在后
		    		   Collections.sort(itemList, new Comparator<ResponseItem>(){
							@Override
							public int compare(ResponseItem o1, ResponseItem o2) {
								if(o1.getPn() == o2.getPn()) {
									return o1.getCode().compareTo(o2.getCode());
								}
								return o1.getPn()-o2.getPn();
							}
		    		   });
		    		   System.out.println("itemList==="+itemList);
					}
		    	}
		    } 
			System.out.println("pwrctrlResultList==="+pwrctrlResultList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("时段控参数召测结束");
		return SUCCESS;
	}
	
	/**
	 * 保存时段控方案明细
	 * @return
	 * @throws Exception
	 */
	public String addPeriodCtrlScheme() throws Exception {
		logger.debug("-----功率时段控方案保存开始-----");

		List<CtrlParamBean> tmnlList = setCtrlTmnlList();

//		获取模板和模板明细信息
		WPwrctrlTemplate template = wPwrctrlTemplateManager.findWPwrctrlTemplateByName(templateName);
		if(template == null) {
			logger.error("-----功率时段控方案保存失败，该方案模板不存在-----");
			success = false;
			return SUCCESS;
		}
		List<WPwrctrlTemplateDetail> tmpDetailList = wPwrctrlTemplateManager.findWPwrctrlTemplateDetailByTemplateName(templateName);
		
//		申明时段空曲线对象
		List<WTmnlPwrctrl> tmnlPwrctrlList = new ArrayList<WTmnlPwrctrl>();
		List<WTmnlPwrctrlDetail[]> detailList = new ArrayList<WTmnlPwrctrlDetail[]>();
		
		//获取方案信息
		List<WCtrlScheme> schemeList= this.iWCtrlSchemeManager.querySchemesById(this.schemeId);
		if(null==schemeList||schemeList.size()!=1){
			logger.error("-----功率时段控方案保存失败，方案信息出错-----");
			success = false;
			return SUCCESS;
		}
		
		for (int m = 0; m < tmnlList.size(); m++) {
			CtrlParamBean bean = tmnlList.get(m);
			
//			设置申明时段空曲线对象属性值
			WTmnlPwrctrl tmnlPwrctrl = new WTmnlPwrctrl();
			tmnlPwrctrl.setConsNo(bean.getConsNo());
			tmnlPwrctrl.setCtrlDateEnd(schemeList.get(0).getCtrlDateEnd());
			tmnlPwrctrl.setCtrlDateStart(schemeList.get(0).getCtrlDateStart());
			tmnlPwrctrl.setCurveNo(curveNo);
			tmnlPwrctrl.setFloatValue(template.getFloatValue());
			tmnlPwrctrl.setIsExec(template.getIsExec());
			tmnlPwrctrl.setIsSendSms(false);
			tmnlPwrctrl.setOrgNo(bean.getOrgNo());
			tmnlPwrctrl.setSaveTime(new Date());
			tmnlPwrctrl.setStaffNo(schemeList.get(0).getStaffNo());
			tmnlPwrctrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			tmnlPwrctrl.setTotalNo((int)bean.getTotalNo());
			
//			设置时段控曲线明细信息
			WTmnlPwrctrlDetail[] details = new WTmnlPwrctrlDetail[8];
			for (int j = 0; j < tmpDetailList.size(); j++) {
				if(tmpDetailList.get(j) != null){
					WPwrctrlTemplateDetailId tmpDetail = tmpDetailList.get(j).getId();
					
					WTmnlPwrctrlDetailId detailId = new WTmnlPwrctrlDetailId();
					detailId.setConst1(tmpDetail.getConst1());
					detailId.setConst2(tmpDetail.getConst2());
					detailId.setConst3(tmpDetail.getConst3());
					for (int k = 0; k < sectionNo.length(); k++) {
						if(sectionNo.indexOf(new Integer(j+1).toString())>=0){
							detailId.setIsValid(true);
						}
					}
					detailId.setSectionEnd(tmpDetail.getSectionEnd());
					detailId.setSectionNo(tmpDetail.getSectionNo());
					detailId.setSectionStart(tmpDetail.getSectionStart());
					
					if(detailId.getIsValid() == null) {
						detailId.setIsValid(false);
					}
					
					details[j] = new WTmnlPwrctrlDetail();
					details[j].setId(detailId);
				}
			} 
			
//			将对象放入list中
			detailList.add(details);
			tmnlPwrctrlList.add(tmnlPwrctrl);
		}
		
//		调用service，将对象保存到数据库
		this.success=wPwrctrlSchemeManager.savePeriodctrlScheme(this.schemeId, template.getTemplateId(), tmnlPwrctrlList, detailList);
		logger.debug("-----功率时段控方案保存结束-----");
		return SUCCESS;
	}
	
	/**
	 * 功率时段控方案保存
	 * @return
	 * @throws Exception
	 */
	public String addSdkPwrCtrlScheme() throws Exception {
		logger.debug("-----功率时段控方案保存开始-----");
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();

		List<CtrlParamBean> tmnlList = setCtrlTmnlList();

//		设置方案其他属性值
		scheme.setOrgNo(user.getOrgNo());
		scheme.setCtrlSchemeType(CtrlSchemeTypeCode.POWER_TIME_CTRL);
		scheme.setIsValid(1L);
		scheme.setCreateDate(new Date());
		scheme.setStaffNo(staffNo);

//		获取模板和模板明细信息
		WPwrctrlTemplate template = wPwrctrlTemplateManager.findWPwrctrlTemplateByName(templateName);
		if(template == null) {
			logger.error("-----功率时段控方案保存失败，该方案模板不存在-----");
			success = false;
			return SUCCESS;
		}
		List<WPwrctrlTemplateDetail> tmpDetailList = wPwrctrlTemplateManager.findWPwrctrlTemplateDetailByTemplateName(templateName);
		
//		申明时段空曲线对象
		List<WTmnlPwrctrl> tmnlPwrctrlList = new ArrayList<WTmnlPwrctrl>();
		List<WTmnlPwrctrlDetail[]> detailList = new ArrayList<WTmnlPwrctrlDetail[]>();
		
		for (int m = 0; m < tmnlList.size(); m++) {
			CtrlParamBean bean = tmnlList.get(m);
			
//			设置申明时段空曲线对象属性值
			WTmnlPwrctrl tmnlPwrctrl = new WTmnlPwrctrl();
			tmnlPwrctrl.setConsNo(bean.getConsNo());
			tmnlPwrctrl.setCtrlDateEnd(scheme.getCtrlDateEnd());
			tmnlPwrctrl.setCtrlDateStart(scheme.getCtrlDateStart());
			tmnlPwrctrl.setCurveNo(curveNo);
			tmnlPwrctrl.setFloatValue(template.getFloatValue());
			tmnlPwrctrl.setIsExec(template.getIsExec());
			tmnlPwrctrl.setIsSendSms(false);
			tmnlPwrctrl.setOrgNo(bean.getOrgNo());
			tmnlPwrctrl.setSaveTime(new Date());
			tmnlPwrctrl.setStaffNo(scheme.getStaffNo());
			tmnlPwrctrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			tmnlPwrctrl.setTotalNo((int)bean.getTotalNo());
			
//			设置时段控曲线明细信息
			WTmnlPwrctrlDetail[] details = new WTmnlPwrctrlDetail[8];
			for (int j = 0; j < tmpDetailList.size(); j++) {
				if(tmpDetailList.get(j) != null){
					WPwrctrlTemplateDetailId tmpDetail = tmpDetailList.get(j).getId();
					
					WTmnlPwrctrlDetailId detailId = new WTmnlPwrctrlDetailId();
					detailId.setConst1(tmpDetail.getConst1());
					detailId.setConst2(tmpDetail.getConst2());
					detailId.setConst3(tmpDetail.getConst3());
					for (int k = 0; k < sectionNo.length(); k++) {
						if(sectionNo.indexOf(new Integer(j+1).toString())>=0){
							detailId.setIsValid(true);
						}
					}
					detailId.setSectionEnd(tmpDetail.getSectionEnd());
					detailId.setSectionNo(tmpDetail.getSectionNo());
					detailId.setSectionStart(tmpDetail.getSectionStart());
					
					if(detailId.getIsValid() == null) {
						detailId.setIsValid(false);
					}
					
					details[j] = new WTmnlPwrctrlDetail();
					details[j].setId(detailId);
				}
			} 
			
//			将对象放入list中
			detailList.add(details);
			tmnlPwrctrlList.add(tmnlPwrctrl);
		}
		
//		调用service，将对象保存到数据库
		wPwrctrlSchemeManager.savePwrctrlScheme(scheme, template.getTemplateId(), tmnlPwrctrlList, detailList);
		
		logger.debug("-----功率时段控方案保存结束-----");
		return SUCCESS;
	}

	/**
	 * 查询时段控用户列表
	 * @return
	 * @throws Exception
	 */
	public String queryPwrctrlSchemeExec() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		if ("usr".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} else if ("org".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecByOrgNo(orgNo,orgType,user);
		} else if ("schemeId".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecBySchemeId(schemeId);
		} else  if("line".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecByLineId(lineId,user);
		} else if ("cgp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecByCgroupNo(groupNo);
		} else if ("ugp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecByUgroupNo(groupNo);
		} else if ("sub".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findPwrctrlSchemeExecBySubsId(subsId,user);
		} else {
			pwrctrlSchemeExecList = new ArrayList<PwrctrlSchemeExecBean>();
		}
		return SUCCESS;
	}
	
	/**
	 * 加载时段控方案
	 * @return
	 * @throws Exception
	 */
	public String pwrctrlLoadScheme() throws Exception {
		schemeCurveNo = powerCtrlQueryManager.findSchemeCurveNo(schemeId);
		validSectionNo = powerCtrlQueryManager.findSchemeValidSectionNo(schemeId);
		secLength = powerCtrlQueryManager.findSchemeSecLength(schemeId);
		templateId = powerCtrlQueryManager.findSchemeTemplateId(schemeId);
		return SUCCESS;
	}
	
	/**
	 * 加载时段方案列表
	 * @return
	 * @throws Exception
	 */
	public String sdkLoadScheme() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		schemeList = remoteCtrlManager.querySchemeListByType(CtrlSchemeTypeCode.POWER_TIME_CTRL, staffNo);
		return SUCCESS;
	}

	/**
	 * 时段控方案执行
	 * @return
	 * @throws Exception
	 */
	public String sdkExec() throws Exception {
		logger.debug("时段控方案执行开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();

			execResultList = new ArrayList<TmnlExecStatusBean>();//投入成功失败标示列表 返回页面
			
//			对每个终端循环投入
			CtrlParamBean flagBean = tmnlList.get(0);
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
				
				if(n==0) {//循环第一次，直接设置参数，然后跳出循环
					paramList.add(setPwrExecParam(bean));
					if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行下发
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
//				参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
					
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					flagBean = bean;
				}
				//循环中正常设置参数
				paramList.add(setPwrExecParam(bean));
				
				if(n==tmnlList.size()-1) {//执行
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
				}
			}
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
		    Response res = null;
		    if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();// 记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率时段控");
					l.setOpButton("投入");
					l.setOpType((short) 2);// 控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);//pn为总加组
					
					if(Status==3 ||Status==4){
						String protocalCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);

							// 对于控制投入到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							if( j%2 == 0) {
								l.setProtItemNo(protocalCode +"0509001");
								l.setCurValue(spellValidSectionNo());
							} else {
								l.setProtItemNo(protocalCode +"0509002");
								l.setCurValue(schemeCurveNo);
							}
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							// 向前台页面返回参数下发结果,修改终端实时状态
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setPwrctrlFlag((short) 1);// 投入设置成1
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusPwrctrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "1"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) continue;
							
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							//对未返回的终端也要记录日志
							// 对于控制投入到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							l.setProtItemNo(bean.getTotalNo() +"0509001");
							l.setCurValue(spellValidSectionNo());
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setProtItemNo(bean.getTotalNo() +"0509002");
							l.setCurValue(schemeCurveNo);
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			}else {
				for (int n = 0; n < tmnlList.size(); n++) {//若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()+bean.getTotalNo(),"-1"));
				}
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("时段控方案执行结束...");
		return SUCCESS;
	}

	/**
	 * 设置时段控投入参数
	 * @return
	 */
	protected ParamItem setPwrExecParam(CtrlParamBean bean) {
		ParamItem pitem = new ParamItem();
		pitem.setFn((short) 9);//设置FN号
		pitem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		
		//编码对象集合
		List<Item> items = new ArrayList<Item>();
		Item item = null;
		
		//设置有效时段
		item = new Item(bean.getProtocolCode() + "0509001");
		item.setValue(spellValidSectionNo());
		items.add(item);
		
		//设置方案号
		item = new Item(bean.getProtocolCode() + "0509002");
		item.setValue(schemeCurveNo);
		items.add(item);
		
		pitem.setItems((ArrayList<Item>) items);
		return pitem;
	}
	
	/**
	 * 时段控方案解除
	 * @return
	 * @throws Exception
	 */
	public String sdkRelease() throws Exception {
		logger.debug("时段控方案解除开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			execResultList = new ArrayList<TmnlExecStatusBean>();//解除成功失败标示列表 返回页面
			
			/**
			 * 终端循环解除，时段控解除F17
			 */
			schemeRelease(tmnlList, taskDeal, (short)17);
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();// 记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率时段控");
					l.setOpButton("解除");
					l.setOpType((short) 2);// 控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);//pn为总加组
					
					if(Status==3 ||Status==4){
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);

							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							l.setProtocolNo(protocolCode +"0511");
							iLOpTmnlLogManager.saveTmnlLog(l);

							// 向前台页面返回参数下发结果
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setPwrctrlFlag((short) 0);// 解除设置成0
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusPwrctrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "0"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) continue;
							
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							//对未返回的终端也要记录日志
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							l.setProtocolNo(bean.getProtocolCode() +"0511");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("时段控方案解除结束...");
		return SUCCESS;
	}
	
/*--------------------------------------------------------------------厂休控-----------------------------------------------------------------*/
	/**
	 * 厂休控参数下发
	 * @throws Exception
	 */
	public String cxkParamSend() throws Exception {
		logger.debug("厂休控参数下发开始");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}

//			获取模板和模板明细信息
			WFactctrlTemplate template = wFactctrlTemplateManager.findBy(templateName);
			if(template == null) {
				logger.error("-----厂休控参数下发失败，选择模板不存在-----");
				success = false;
				return SUCCESS;
			}

			execResultList = new ArrayList<TmnlExecStatusBean>();
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
			/**
			 * 终端循环参数下发
			 */
			CtrlParamBean flagBean = tmnlList.get(0);
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
//				终端资产号集合
				if(n==0){
					tmnlAssetNoList.add(bean.getTmnlAssetNo());//加入终端资产编号列表
					
					ParamItem factItem =setParamItem(template,bean);
					paramList.add(factItem);
					
					if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行下发
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
//				参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){//参数下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					logger.debug("--调用接口--");
					
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					flagBean = bean;
				}
				
				ParamItem factItem =setParamItem(template,bean);
				paramList.add(factItem);
				
				if(n==tmnlList.size()-1) {//list结尾调用接口，循环结束
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.SET_PARAMETERS, paramList);
					logger.debug("--调用接口--");
				}
			}
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();//记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率厂休控");
					l.setOpButton("参数下发");
					l.setOpType((short) 1);// 参数下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);

					List<ResponseItem> resItems = res.getResponseItems();
					if(Status ==3 ||Status ==4) {
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < resItems.size(); j++) {
							ResponseItem	resItem = resItems.get(j);
							l.setOpObjNo((long)resItems.get(j).getPn());
							// 对各规约项分别设置，并保存
							l.setCurValue(String.valueOf(template.getFactoryConst()));
							l.setProtItemNo(protocolCode+"042A001");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(template.getCtrlTime());
							l.setProtItemNo(protocolCode+"042A002");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(String.valueOf(template.getContinueHours()));
							l.setProtItemNo(protocolCode+"042A003");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(getWeekDaysByte(template.getWeekDays()));
							l.setProtItemNo(protocolCode+"042A004");
							iLOpTmnlLogManager.saveTmnlLog(l);
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							// 向前台页面返回参数下发结果
							if (Status == 3)
								execResultList.add(new TmnlExecStatusBean(
										res.getTmnlAssetNo()+ resItem.getPn(), "2"));
							else
								execResultList.add(new TmnlExecStatusBean(
										res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) {
							continue;
						}
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							l.setOpObjNo((long)bean.getTotalNo());
							// 对各规约项分别设置，并保存
							l.setCurValue(String.valueOf(template.getFactoryConst()));
							l.setProtItemNo(bean.getProtocolCode()+"042A001");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(template.getCtrlTime());
							l.setProtItemNo(bean.getProtocolCode()+"042A002");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(String.valueOf(template.getContinueHours()));
							l.setProtItemNo(bean.getProtocolCode()+"042A003");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							l.setCurValue(getWeekDaysByte(template.getWeekDays()));
							l.setProtItemNo(bean.getProtocolCode()+"042A004");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("厂休控参数下发结束");
		return SUCCESS;
	}
	
	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<CtrlParamBean> noResponseTmnl(List<CtrlParamBean> tmnlList,
			String tmnlAssetNo) {
		if(tmnlList==null || tmnlList.size()<=0 ||tmnlAssetNo==null) {
			return null;
		}
		List<CtrlParamBean> resList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}

	/**
	 * 设置厂休控下发参数
	 * @param template
	 * @param bean
	 * @return
	 */
	public ParamItem setParamItem(WFactctrlTemplate template, CtrlParamBean bean) {
		ParamItem factItem = new ParamItem();
		List<Item> items = null;
		Item item = null;
		factItem.setFn((short) 42);//设置FN号
		factItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		//编码对象集合
		items = new ArrayList<Item>();
		//设置厂休控定值
		item = new Item(bean.getProtocolCode()+"042A001");
		item.setValue(String.valueOf(template.getFactoryConst()));
		items.add(item);
		//设置厂休控起始时间
		item = new Item(bean.getProtocolCode()+"042A002");
		item.setValue(template.getCtrlTime());
		items.add(item);
		//设置厂休控延续时间
		item = new Item(bean.getProtocolCode()+"042A003");
		item.setValue(String.valueOf(template.getContinueHours()));
		items.add(item);
		item = new Item(bean.getProtocolCode()+"042A004");
		item.setValue(getWeekDaysByte(template.getWeekDays()));
		items.add(item);
		
		factItem.setItems((ArrayList<Item>) items);
		return factItem;
	}
	
	/**
	 * 厂休控参数召测
	 * @return
	 * @throws Exception
	 */
	public String cxkParamFecth() throws Exception {
		logger.debug("厂休控参数召测开始");
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
			List<CtrlParamBean> tmnlList = queryCtrlTmnlList();
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}
			factctrlResultList = new ArrayList<WTmnlFactctrl>();
			
			CtrlParamBean flagBean = tmnlList.get(0);
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
				if(n==0){
					tmnlAssetNoList.add(bean.getTmnlAssetNo());//加入终端资产编号列表
					
					ParamItem coloumItem = new ParamItem();
					coloumItem.setFn((short) 42);//设置FN号
					coloumItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					
					List<Item> items = new ArrayList<Item>();
					coloumItem.setItems((ArrayList<Item>) items);
					paramList.add(coloumItem);
					
					if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行下发
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
//				参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					//参数下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
					logger.debug("--调用接口--");
					
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					flagBean = bean;
				}
				
				ParamItem coloumItem = new ParamItem();
				coloumItem.setFn((short) 42);//设置FN号
				coloumItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				
				List<Item> items = new ArrayList<Item>();
				coloumItem.setItems((ArrayList<Item>) items);
				paramList.add(coloumItem);
				
				if(n==tmnlList.size()-1) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.QUERY_PARAMS, paramList);
					logger.debug("--调用接口--");
				}
			}
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
		    Response res = null;
		    if(null != list && 0 < list.size()){
		    	for(int i = 0; i < list.size(); i++){
		    		WTmnlFactctrl tf = new WTmnlFactctrl();
		    	    res = (Response)list.get(i);
		    	    Short status = res.getTaskStatus();
					if (status ==3 ||status==4) {
						//循环responseItems
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem items = res.getResponseItems().get(j);
							if(j%4==0) {
								tf.setKeyId(res.getTmnlAssetNo() + items.getPn());
								tf.setFactoryConst(Float.valueOf(items.getValue()));
							} else if(j%4==1) {
								tf.setCtrlTime(items.getValue());
							} else if(j%4==2) {
								tf.setContinueHours(Byte.valueOf(items.getValue()));
							}else {	
								String weeks = items.getValue();
								tf.setWeekDays(transWeeks(weeks));
								factctrlResultList.add(tf);
							}
						}
					}
		    	}
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("厂休控参数召测结束");
		return SUCCESS;
	}
	
	/**
	 * 保存厂休控方案
	 * @return
	 * @throws Exception
	 */
	public String addFactoryCtrlScheme() throws Exception {
		logger.debug("-----功率厂休控方案保存开始-----");

//		取得当前操作员编号
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		List<CtrlParamBean> tmnlList = setCtrlTmnlList();

//		获取模板和模板明细信息
		WFactctrlTemplate template = wFactctrlTemplateManager.findBy(templateName);
		if(template == null) {
			logger.error("-----功率厂休控方案保存失败，该方案模板不存在-----");
			success = false;
			return SUCCESS;
		}
		
//		申明终端厂休控对象
		List<WTmnlFactctrl> tmnlFactctrlList = new ArrayList<WTmnlFactctrl>();
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			设置申明时段空曲线对象属性值
			WTmnlFactctrl tmnlFactctrl = new WTmnlFactctrl();
			tmnlFactctrl.setConsNo(bean.getConsNo());
			tmnlFactctrl.setContinueHours(template.getContinueHours());
			tmnlFactctrl.setCtrlTime(template.getCtrlTime());
			tmnlFactctrl.setFactoryConst(template.getFactoryConst());
			tmnlFactctrl.setWeekDays(template.getWeekDays());
			tmnlFactctrl.setIsSendSms(false);
			tmnlFactctrl.setOrgNo(bean.getOrgNo());
			tmnlFactctrl.setSaveTime(new Date());
			tmnlFactctrl.setStaffNo(user.getStaffNo());
			tmnlFactctrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			tmnlFactctrl.setTotalNo(bean.getTotalNo());
			
//			将对象放入list中
			tmnlFactctrlList.add(tmnlFactctrl);
		}
		
//		调用service，将对象保存到数据库
		this.success=this.wPwrctrlSchemeManager.saveFactoryCtrlScheme(this.schemeId, template.getTemplateId(), tmnlFactctrlList);
		
		logger.debug("-----功率厂休控方案保存结束-----");
		return SUCCESS;
	}
	
	/**
	 * 功率厂休控方案保存
	 * @return
	 * @throws Exception
	 */
	public String addCxkPwrCtrlScheme() throws Exception {
		logger.debug("-----功率厂休控方案保存开始-----");

//		取得当前操作员编号
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		
//		设置方案其他属性值
		scheme.setOrgNo(user.getOrgNo());
		scheme.setCtrlSchemeType(CtrlSchemeTypeCode.POWER_FACOTRY_CTRL);
		scheme.setIsValid(1L);
		scheme.setCreateDate(new Date());
		scheme.setStaffNo(staffNo);
		
		List<CtrlParamBean> tmnlList = setCtrlTmnlList();

//		获取模板和模板明细信息
		WFactctrlTemplate template = wFactctrlTemplateManager.findBy(templateName);
		if(template == null) {
			logger.error("-----功率厂休控方案保存失败，该方案模板不存在-----");
			success = false;
			return SUCCESS;
		}
		
//		申明终端厂休控对象
		List<WTmnlFactctrl> tmnlFactctrlList = new ArrayList<WTmnlFactctrl>();
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			设置申明时段空曲线对象属性值
			WTmnlFactctrl tmnlFactctrl = new WTmnlFactctrl();
			tmnlFactctrl.setConsNo(bean.getConsNo());
			tmnlFactctrl.setContinueHours(template.getContinueHours());
			tmnlFactctrl.setCtrlTime(template.getCtrlTime());
			tmnlFactctrl.setFactoryConst(template.getFactoryConst());
			tmnlFactctrl.setWeekDays(template.getWeekDays());
			tmnlFactctrl.setIsSendSms(false);
			tmnlFactctrl.setOrgNo(bean.getOrgNo());
			tmnlFactctrl.setSaveTime(new Date());
			tmnlFactctrl.setStaffNo(scheme.getStaffNo());
			tmnlFactctrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			tmnlFactctrl.setTotalNo(bean.getTotalNo());
			
//			将对象放入list中
			tmnlFactctrlList.add(tmnlFactctrl);
		}
		
//		调用service，将对象保存到数据库
		wPwrctrlSchemeManager.saveFactctrlScheme(scheme, template.getTemplateId(), tmnlFactctrlList);
		
		logger.debug("-----功率厂休控方案保存结束-----");
		return SUCCESS;
	}

	/**
	 * 查询厂休控用户列表
	 * @return
	 * @throws Exception
	 */
	public String queryFactctrlSchemeExec() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		if ("usr".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} else if ("org".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecByOrgNo(orgNo,orgType,user);
		} else if ("schemeId".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecBySchemeId(schemeId);
		} else  if("line".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecByLineId(lineId,user);
		} else if ("cgp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecByCgroupNo(groupNo);
		} else if ("ugp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecByUgroupNo(groupNo);
		} else if ("sub".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFactctrlSchemeExecBySubsId(subsId,user);
		} else {
			pwrctrlSchemeExecList = new ArrayList<PwrctrlSchemeExecBean>();
		}
		return SUCCESS;
	}

	/**
	 * 加载厂休控方案
	 * @return
	 * @throws Exception
	 */
	public String factctrlLoadScheme() throws Exception {
		templateId = powerCtrlQueryManager.findCxkSchemeTemplateName(schemeId);
		return SUCCESS;
	}
	
	/**
	 * 加载方案列表
	 * @return
	 * @throws Exception
	 */
	public String cxkLoadScheme() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		schemeList = remoteCtrlManager.querySchemeListByType(CtrlSchemeTypeCode.POWER_FACOTRY_CTRL, staffNo);
		return SUCCESS;
	}
	
	/**
	 * 厂休控方案执行
	 * @return
	 * @throws Exception
	 */
	public String cxkExec() throws Exception {
		logger.debug("厂休控方案执行开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}

			execResultList = new ArrayList<TmnlExecStatusBean>();
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
			/**
			 * 终端循环投入,厂休控投入-F10
			 */
			schemeRelease(tmnlList, taskDeal, (short)10);
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();// 记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志
					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率厂休控");
					l.setOpButton("投入");
					l.setOpType((short) 2);// 控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);
					
					if(Status==3 ||Status==4){
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);
							
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							l.setProtocolNo(protocolCode +"050A");
							iLOpTmnlLogManager.saveTmnlLog(l);

							// 向前台页面返回参数下发结果
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setFactctrlFlag((short) 1);// 投入设置成1
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusFactctrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "1"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) {
							continue;
						}
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							l.setProtocolNo(bean.getProtocolCode() +"050A");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("厂休控方案执行结束...");
		return SUCCESS;
	}
	
	/**
	 * 厂休控方案解除
	 * @return
	 * @throws Exception
	 */
	public String cxkRelease() throws Exception {
		logger.debug("厂休控方案解除开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				return SUCCESS;
			}

			execResultList = new ArrayList<TmnlExecStatusBean>();
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
			/**
			 * 终端循环解除,厂休控解除F18
			 */
			schemeRelease(tmnlList, taskDeal, (short)18);
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();// 记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率厂休控");
					l.setOpButton("解除");
					l.setOpType((short) 2);// 控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);
					
					if(Status==3 ||Status==4) {
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);

							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							l.setProtocolNo(protocolCode +"0512");
							iLOpTmnlLogManager.saveTmnlLog(l);

							// 向前台页面返回参数下发结果
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setFactctrlFlag((short) 0);// 解除设置成0
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusFactctrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "0"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) continue;
							
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							//对未返回的终端也要记录日志
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							l.setProtocolNo(bean.getProtocolCode() +"0512");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("厂休控方案解除结束...");
		return SUCCESS;
	}
/*--------------------------------------------------------------------下浮控----------------------------------------------------------*/	
	/**
	 * 功率下浮控方案明细保存
	 */
	public String addFloatCtrlScheme() throws Exception {
		logger.debug("-----功率下浮控方案保存开始-----");
//		取得当前操作员编号
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		
		List<CtrlParamBean> tmnlList = setCtrlTmnlList();
		
//		申明终端下浮控对象
		List<WFloatDownCtrl> tmnlFloatList = new ArrayList<WFloatDownCtrl>();
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			设置申明时段空曲线对象属性值
			WFloatDownCtrl floatDownCtrl = new WFloatDownCtrl();
			floatDownCtrl.setConsNo(bean.getConsNo());
			floatDownCtrl.setCtrlFlag(false);
			floatDownCtrl.setIsSendSms(false);
			floatDownCtrl.setOrgNo(bean.getOrgNo());
			floatDownCtrl.setSaveTime(new Date());
			floatDownCtrl.setSendTime(new Date());
			floatDownCtrl.setStaffNo(user.getStaffNo());
			floatDownCtrl.setStatusCode("01");
			floatDownCtrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			floatDownCtrl.setTotalNo(bean.getTotalNo());
			floatDownCtrl.setAlertTime1(alertTime1);
			floatDownCtrl.setAlertTime2(alertTime2);
			floatDownCtrl.setAlertTime3(alertTime3);
			floatDownCtrl.setAlertTime4(alertTime4);
			floatDownCtrl.setFloatDownPercent(floatDownPercent);
			floatDownCtrl.setFloatDownWtime(floatDownWtime);
			floatDownCtrl.setPowerFreezetime(powerFreezetime);
			floatDownCtrl.setCtrlTime(ctrlTime);
			floatDownCtrl.setCurrentPower(0.0);
			
//			将对象放入list中
			tmnlFloatList.add(floatDownCtrl);
		}
		
//		调用service，将对象保存到数据库
		wPwrctrlSchemeManager.saveFloatCtrlScheme(this.schemeId, tmnlFloatList);
		logger.debug("-----功率下浮控方案保存结束-----");
		return SUCCESS;
	}
	
	/**
	 * 功率下浮控方案保存
	 * @return
	 * @throws Exception
	 */
	public String addFloatPwrCtrlScheme() throws Exception {
		logger.debug("-----功率下浮控方案保存开始-----");
//		取得当前操作员编号
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		
//		设置方案其他属性值
		scheme.setOrgNo(user.getOrgNo());
		scheme.setCtrlSchemeType(CtrlSchemeTypeCode.FLOAT_DOWN_CTRL);
		scheme.setIsValid(1L);
		scheme.setCreateDate(new Date());
		scheme.setStaffNo(staffNo);
		
		List<CtrlParamBean> tmnlList = setCtrlTmnlList();
		
//		申明终端下浮控对象
		List<WFloatDownCtrl> tmnlFloatList = new ArrayList<WFloatDownCtrl>();
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			设置申明时段空曲线对象属性值
			WFloatDownCtrl floatDownCtrl = new WFloatDownCtrl();
			floatDownCtrl.setConsNo(bean.getConsNo());
			floatDownCtrl.setCtrlFlag(false);
			floatDownCtrl.setIsSendSms(false);
			floatDownCtrl.setOrgNo(bean.getOrgNo());
			floatDownCtrl.setSaveTime(new Date());
			floatDownCtrl.setSendTime(new Date());
			floatDownCtrl.setStaffNo(staffNo);
			floatDownCtrl.setStatusCode("01");
			floatDownCtrl.setTmnlAssetNo(bean.getTmnlAssetNo());
			floatDownCtrl.setTotalNo(bean.getTotalNo());
			floatDownCtrl.setAlertTime1(alertTime1);
			floatDownCtrl.setAlertTime2(alertTime2);
			floatDownCtrl.setAlertTime3(alertTime3);
			floatDownCtrl.setAlertTime4(alertTime4);
			floatDownCtrl.setFloatDownPercent(floatDownPercent);
			floatDownCtrl.setFloatDownWtime(floatDownWtime);
			floatDownCtrl.setPowerFreezetime(powerFreezetime);
			floatDownCtrl.setCtrlTime(ctrlTime);
			floatDownCtrl.setCurrentPower(0.0);
			
//			将对象放入list中
			tmnlFloatList.add(floatDownCtrl);
		}
		
//		调用service，将对象保存到数据库
		wPwrctrlSchemeManager.saveFloatScheme(scheme, tmnlFloatList);
		logger.debug("-----功率下浮控方案保存结束-----");
		return SUCCESS;
	}

	/**
	 * 查询下浮控用户列表
	 * @return
	 * @throws Exception
	 */
	public String queryFloatSchemeExec() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		if ("usr".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecByTmnlAssetNo(tmnlAssetNo);
		} else if ("org".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecByOrgNo(orgNo,orgType,user);
		} else if ("schemeId".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecBySchemeId(schemeId);
		} else  if ("line".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecByLineId(lineId,user);
		} else if ("cgp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecByCgroupNo(groupNo);
		} else if ("ugp".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecByUgroupNo(groupNo);
		} else if ("sub".equals(nodeType)) {
			pwrctrlSchemeExecList = powerCtrlQueryManager.findFloatSchemeExecBySubsId(subsId,user);	
		} else {
			pwrctrlSchemeExecList = new ArrayList<PwrctrlSchemeExecBean>();
		}
		return SUCCESS;
	}

	/**
	 * 加载下浮控方案
	 * @return
	 * @throws Exception
	 */
	public String floatLoadScheme() throws Exception {
		floatDownCtrl = powerCtrlQueryManager.findfloatDownCtrl(schemeId);
		return SUCCESS;
	}
	
	/**
	 * 加载方案列表
	 * @return
	 * @throws Exception
	 */
	public String floatSchemeList() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		staffNo = user.getStaffNo();
		schemeList = remoteCtrlManager.querySchemeListByType(CtrlSchemeTypeCode.FLOAT_DOWN_CTRL, staffNo);
		return SUCCESS;
	}
	
	/**
	 * 当前功率下浮控方案执行 TODO
	 * @return
	 * @throws Exception
	 */
	public String floatExec() throws Exception {
		logger.debug("当前功率下浮控方案执行开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			Map<String, List<CtrlParamBean>> tmnlMap = createtrlTmnlMap();
			if(tmnlMap==null || tmnlMap.size()<=0){
				return SUCCESS;
			}

			execResultList = new ArrayList<TmnlExecStatusBean>();
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
			/**
			 * 终端循环投入
			 */
//			终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			
//			参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			//取得698规约终端
			List<CtrlParamBean>  tmnlList = tmnlMap.get("698");
			CtrlParamBean flagBean = null;
			if(tmnlList.size()>0) {
				flagBean = tmnlList.get(0);
			}
			//循环投入698规约终端
			ParamItem factItem = null;
			for (int n = 0; n < tmnlList.size(); n++) {
				CtrlParamBean bean = tmnlList.get(n);
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					factItem = setTmnlParamItemFor698();
					factItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					
					paramList.add(factItem);
					if(tmnlList.size() ==1) {
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
//				参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
				    //解除
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
					
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					flagBean = bean;
				}
				
				factItem = setTmnlParamItemFor698();
				factItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				
				paramList.add(factItem);
				
				if(n==tmnlList.size()-1) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
				}
			}
			
			//取得其他规约终端
			List<CtrlParamBean>  tmnlOtherList = tmnlMap.get("other");
			if(tmnlOtherList.size()>0) {
				//重新设置标记终端
				flagBean = tmnlOtherList.get(0);
			}
			//重新生成参数列表和终端号列表
			paramList = new ArrayList<ParamItem>();
			tmnlAssetNoList = new ArrayList<String>();
			ParamItem otherFactItem = null;
			
			for (int n = 0; n < tmnlOtherList.size(); n++) {
				CtrlParamBean bean = tmnlOtherList.get(n);
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					otherFactItem = setTmnlParamItemForOther(bean.getProtocolCode());
					otherFactItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					
					paramList.add(otherFactItem);
					if(tmnlList.size() ==1) {
						taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
						logger.debug("--调用接口--");
					}
					continue;
				}
				
//				参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
				    //解除
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
					
					//一个终端组一个包，遇到终端不同的要新建paramList对象
					paramList = new ArrayList<ParamItem>();
					
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					
					flagBean = bean;
				}
				
				otherFactItem = setTmnlParamItemForOther(bean.getProtocolCode());
				otherFactItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				
				paramList.add(otherFactItem);
				
				if(n==tmnlList.size()-1) {
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
				}
			}
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();//记录终端返回状态
					
					/* 记录日志，修改终端实时状态 */
					LOpTmnlLog l = new LOpTmnlLog();//写终端操作日志
					
//						设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率当前功率下浮控");
					l.setOpButton("投入");
					l.setOpType((short)2);//控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short)4);
					
					if(Status==3 ||Status==4){
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);
							
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							if("5".equals(protocolCode)) {
								List<Item> itemList = factItem.getItems();
								for (Item item : itemList) {
									l.setProtItemNo(item.getCode());
									l.setCurValue(item.getValue());
									iLOpTmnlLogManager.saveTmnlLog(l);
								}
							} else {
								List<Item> itemList = otherFactItem.getItems();
								for (Item item : itemList) {
									l.setProtItemNo(item.getCode());
									l.setCurValue(item.getValue());
									iLOpTmnlLogManager.saveTmnlLog(l);
								}
							}

							// 向前台页面返回参数下发结果
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setDownCtrlFlag((short) 1);// 投入设置成1
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusDownCtrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "1"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) {
							continue;
						}
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							if("5".equals(bean.getProtocolCode())) {
								List<Item> itemList = factItem.getItems();
								for (Item item : itemList) {
									l.setProtItemNo(item.getCode());
									l.setCurValue(item.getValue());
									iLOpTmnlLogManager.saveTmnlLog(l);
								}
							} else {
								List<Item> itemList = otherFactItem.getItems();
								for (Item item : itemList) {
									l.setProtItemNo(item.getCode());
									l.setCurValue(item.getValue());
									iLOpTmnlLogManager.saveTmnlLog(l);
								}
							}
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("当前功率下浮控方案执行结束...");
		return SUCCESS;
	}
	
	/**
	 * 设置698规约的当前功率下浮参数
	 * @return
	 */
	protected ParamItem setTmnlParamItemFor698() {
		ParamItem factItem = new ParamItem();
		factItem.setFn((short) 12);//设置FN号-当前功率下浮控投入
		List<Item> items = new ArrayList<Item>();
		Item item =null;
		
		item = new Item("5050C001");
		item.setValue(floatDownWtime.toString());//定值滑差时间
		items.add(item);
		
		item = new Item("5050C002");
		item.setValue(floatDownPercent.toString());//浮动系数
		items.add(item);
		
		item = new Item("5050C003");
		item.setValue(powerFreezetime.toString());//冻结延时时间
		items.add(item);
		
		item = new Item("5050C004");
		item.setValue(ctrlTime.toString());//控制时间
		items.add(item);
		
		item = new Item("5050C005");
		if(box1){
			item.setValue(alertTime1.toString());//第一次告警时间
		} else {
			item.setValue("0");
		}
		items.add(item);
		
		item = new Item("5050C006");
		if(box2){
			item.setValue(alertTime2.toString());//第二次告警时间
		} else {
			item.setValue("0");
		}
		items.add(item);

		item = new Item("5050C007");
		if(box3){
			item.setValue(alertTime3.toString());//第三次告警时间
		} else {
			item.setValue("0");
		}
		items.add(item);
		
		item = new Item("5050C008");
		if(box4){
			item.setValue(alertTime4.toString());//第四次告警时间
		}
		items.add(item);
		
		factItem.setItems((ArrayList<Item>) items);
		return factItem;
	}
	
	/**
	 * 设置其他规约的当前功率下浮参数
	 * @return
	 */
	protected ParamItem setTmnlParamItemForOther(String protocolCode) {
		ParamItem factItem = new ParamItem();
		factItem.setFn((short) 12);//设置FN号-当前功率下浮控投入
		List<Item> items = new ArrayList<Item>();
		Item item =null;
		
		item = new Item(protocolCode +"050C001");
		item.setValue(floatDownWtime.toString());//定值滑差时间
		items.add(item);
		
		item = new Item(protocolCode +"050C002");
		item.setValue(floatDownPercent.toString());//浮动系数
		items.add(item);
		
		item = new Item(protocolCode +"050C003");
		item.setValue(powerFreezetime.toString());//冻结延时时间
		items.add(item);
		
		factItem.setItems((ArrayList<Item>) items);
		return factItem;
	}
	
	/**
	 * 当前功率下浮控方案解除
	 * @return
	 * @throws Exception
	 */
	public String floatRelease() throws Exception {
		logger.debug("当前功率下浮控方案解除开始...");
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
			PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
			if(user==null){
				logger.error("-----会话超时,不能操作-----");
				success = false;
				return SUCCESS;
			}
			staffNo = user.getStaffNo();
			
			List<CtrlParamBean> tmnlList = setCtrlTmnlList();
			
			if(tmnlList==null || tmnlList.size()<=0){
				success = false;
				return SUCCESS;
			}

			execResultList = new ArrayList<TmnlExecStatusBean>();
			
//			取得本机IP，记录操作日志
			String localIp = getLocalIp();
			
			/**
			 * 终端循环解除,下浮控解除F20
			 */
			schemeRelease(tmnlList, taskDeal, (short)20);
			
			logger.debug("等待结果...");
			List<Response> list = taskDeal.getAllResponse(second);
			Response res = null;
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					res = (Response) list.get(i);
					Short Status = res.getTaskStatus();// 记录终端返回状态

					LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

					// 设置共有属性值
					l.setOrgNo(user.getOrgNo());
					l.setEmpNo(staffNo);
					l.setIpAddr(localIp);
					l.setOpModule("终端功率下浮控");
					l.setOpButton("解除");
					l.setOpType((short) 2);// 控制下发
					l.setTmnlAssetNo(res.getTmnlAssetNo());
					l.setOpTime(new Date());
					l.setCurStatus(Status.toString());
					l.setOpObj((short) 4);
					
					if(Status==3 ||Status==4){
						String protocolCode = getProtocolCodeByTmnlAssetNo(tmnlList,res.getTmnlAssetNo());
						for (int j = 0; j < res.getResponseItems().size(); j++) {
							ResponseItem resItem = res.getResponseItems().get(j);

							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) resItem.getPn());
							l.setProtocolNo(protocolCode +"0514");
							iLOpTmnlLogManager.saveTmnlLog(l);

							// 向前台页面返回参数下发结果
							if (Status == 3&& res.getErrorCode()==null) {
								// 创建终端实时状态Bean,并对各控制状态共属性赋值
								WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

								ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
								ctrlStatus.setTotalNo(resItem.getPn());
								ctrlStatus.setDownCtrlFlag((short) 0);// 解除设置成0
								wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusDownCtrlFlag(ctrlStatus);// 修改终端实时状态

								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "0"));
							} else {
								execResultList.add(new TmnlExecStatusBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1"));
							}
						}
					} else {
						//对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
						List<CtrlParamBean> noResponseList = noResponseTmnl(tmnlList, res.getTmnlAssetNo());
						if(noResponseList ==null) continue;
							
						for (int j = 0; j < noResponseList.size(); j++) {
							CtrlParamBean bean = noResponseList.get(j);
							
							//对未返回的终端也要记录日志
							// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
							l.setOpObjNo((long) bean.getTotalNo());
							l.setProtocolNo(bean.getProtocolCode() +"0514");
							iLOpTmnlLogManager.saveTmnlLog(l);
							
							execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
									+ bean.getTotalNo(), "-1"));
						}
					}
				}
			} else {
				for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
					CtrlParamBean bean = tmnlList.get(n);
					execResultList.add(new TmnlExecStatusBean(bean	.getTmnlAssetNo()
							+ bean.getTotalNo(), "-1"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("当前功率下浮控方案解除结束...");
		return SUCCESS;
	}
	
	/**
	 * 校验方案名是否已存在
	 * @return
	 * @throws Exception
	 */
	public String ckeckCtrlSchemeName() throws Exception {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			logger.error("-----会话超时,不能操作-----");
			success = false;
			return SUCCESS;
		}
		checkResult = powerCtrlQueryManager.queryCtrlSchemeName(ctrlSchemeName, user.getStaffNo());
		return SUCCESS;
	}
	
	/*-----------------------------------------------------------------------------公用方法------------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------------公用方法------------------------------------------------------------------------*/
	
	/**
	 * 根据终端资产编号获取终端规约编码
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	protected String getProtocolCodeByTmnlAssetNo(List<CtrlParamBean> tmnlList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) return "";
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}
	
	/**
	 * 无参数方案公用方法
	 * 将前台传入的终端list信息组包，发送给前置接口
	 */
	protected void schemeRelease(List<CtrlParamBean> tmnlList, TaskDeal taskDeal, short ctrlType) {
		CtrlParamBean flagBean = tmnlList.get(0);
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		List<String> tmnlAssetNoList = new ArrayList<String>();
		
		logger.debug("tmnlList===" +tmnlList);
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			if(n==0) {
				ParamItem factItem = new ParamItem();
				factItem.setFn(ctrlType);//设置FN号-F17时段控解除, F18厂休控解除, F20当前功率下浮控解除
				factItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
//				编码对象集合
				List<Item> items  = new ArrayList<Item>();
				
				factItem.setItems((ArrayList<Item>) items);
				paramList.add(factItem);
				
				if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
					logger.debug("--调用接口--");
				}
				continue;
			}

//			参数项设置
			if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				logger.debug("--调用接口--");
				
				//一个终端组一个包，遇到终端不同的要新建paramList对象
				paramList = new ArrayList<ParamItem>();
				
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(bean.getTmnlAssetNo());
				
				flagBean = bean;
			}
			
			ParamItem factItem = new ParamItem();
			factItem.setFn(ctrlType);//设置FN号-F17时段控解除, F18厂休控解除, F20当前功率下浮控解除
			factItem.setPoint(bean.getTotalNo());//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
//			编码对象集合
			List<Item> items  = new ArrayList<Item>();
			
			factItem.setItems((ArrayList<Item>) items);
			paramList.add(factItem);
			
			if(n==tmnlList.size()-1) {
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
				logger.debug("--调用接口--");
			}
		}
	}
	
	//将时段数字码转化成字符串
	protected String sdIntegerTosdString(Integer sdStart, Integer sdEnd) {
//		拼写时段开始时间
		Integer sdStartH = sdStart/2;
		String startTime =(sdStartH<10)?("0"+sdStartH.toString()):(sdStartH.toString());
		if(sdStart%2==0){
			startTime += ":00";
		} else {
			startTime += ":30";
		}
		
//		拼写时段结束时间
		Integer sdEndH = (sdEnd+1)/2;
		String endTime =(sdEndH<10)?("0"+sdEndH.toString()):(sdEndH.toString());
		if(sdEnd%2==0){
			endTime += ":30";
		} else {
			endTime += ":00";
		}
		return startTime+endTime;
	}
	
	/**
	 * 将时段号数组拼成8位01码格式字符串
	 * @return
	 */
	protected String spellValidSectionNo(){
		StringBuffer secNo = new StringBuffer();
		String[] secNos = validSectionNo.split(",");
		for (int i = 0; i < 8; i++){//时段号一共8位
			int secNum = Integer.parseInt(secNos[i]);
			if(secNum==i){
				secNo.append("1");//该位置时段有效，设置成1
			} else {
				secNo.append("0");//未找到匹配，设置成0
			}
		}
		return secNo.toString();
	}
	
	/**
	 * 根据返回报文还原时段控时段及定值列表信息
	 * @param sdArray 时段数组
	 * @param responseItems
	 */
	protected void pwrFecthDataSet(String[] sdArray, List<ResponseItem> responseItems) {
		if(responseItems==null|| responseItems.size()<=0 ||sdArray ==null || sdArray.length<=0){
			return;
		}
//		组织时段
		String[] sdByteArray = new String[48];
		List<Integer> sdInteger = new ArrayList<Integer>();
		List<String> sdString = new ArrayList<String>();
		
		for (int i = 0; i < sdArray.length; i++) {
			for (int j = 0; j < 4; j++) {
				sdByteArray[i*4 +j] =  sdArray[i].substring(6-2*j, 6-2*j+2);
				if("01".equals(sdByteArray[i*4 +j])){
					sdInteger.add(i*4 +j);
				}
			}
		}
		
//		如果时段值全部为0，既终端当前没有时段时，直接返回
		if(sdInteger.size()<=0){
			return;
		}
		
//		标记开始时间
		Integer sdStart = sdInteger.get(0);
		for (int i = 1; i < sdInteger.size(); i++) {
			Integer curSd = sdInteger.get(i);
			Integer preSd = sdInteger.get(i-1);
			if(curSd != preSd+1 && i != sdInteger.size()-1){
				
				sdString.add(sdIntegerTosdString(sdStart, preSd));
				
//				重新定位开始时段
				sdStart = curSd;
			} else if(i == sdInteger.size()-1) {
				if(curSd == preSd+1){
					sdString.add(sdIntegerTosdString(sdStart, curSd));
				} else {
					sdString.add(sdIntegerTosdString(sdStart, preSd));
					sdString.add(sdIntegerTosdString(curSd, curSd));
				}
			}
		}
		
		int curP = 0;
		ResponseItem item = null;
		String value = null;
		item = responseItems.get(curP);
		value = item.getValue();
		curP++;
		int fanNum = 0;
		//循环方案号，确定有几个方案
		for (int i = value.length()-1; i >= 0; i--) {
			char c = value.charAt(i);
			if(c=='1'){
				fanNum++;
			}
		}
		reslutDetailList = new ArrayList<PwrctrlTempDetailBean>();
		//循环方案
		for (int i = 0; i < fanNum; i++) {
			int sdNum = 0;
			item = responseItems.get(curP);
			curP++;
			
			value = item.getValue();
//			循环时段号，确定有几个有效时段
			for (int j = value.length()-1; j >= 0;j--) {
				char c = value.charAt(j);
				if(c=='1'){
					sdNum++;
				}
			}
			
			if(sdNum!= sdString.size()){
				return;
			}
			
//			循环时段，取得召测值
			for (int j = 0; j < sdNum ; j++) {
				item = responseItems.get(curP);
				curP++;
				value = item.getValue();
				if(i ==0){//方案1
					PwrctrlTempDetailBean bean = new PwrctrlTempDetailBean();
					
					bean.setConst1(Float.parseFloat(item.getValue()));
					bean.setSectionNo((byte)(j+1));
					bean.setSectionStart(sdString.get(j).substring(0, 5));
					bean.setSectionEnd(sdString.get(j).substring(5));
					
					reslutDetailList.add(bean);
				} else if( i ==1) {//方案2
					PwrctrlTempDetailBean bean = reslutDetailList.get(j);
					bean.setConst2(Float.parseFloat(item.getValue()));
				} else if(i ==2) {//方案3
					PwrctrlTempDetailBean bean = reslutDetailList.get(j);
					bean.setConst3(Float.parseFloat(item.getValue()));
				}
			}
		}
	}
	
	/**
	 * 将页面选择的总加组按所属终端,总加组分类排序
	 */
	protected List<CtrlParamBean> queryCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < pwrCtrlList.size(); i++) {
			/*对应前台传入参数格式：'orgNo'+'`'+'tmnlAssetNo'+'`'+'totalNo'
			 * 用于参数召测*/
			String[] pwrCtrls = pwrCtrlList.get(i).split("`");
			if("".equals(pwrCtrls[1])||"".equals(pwrCtrls[2])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(pwrCtrls[0]);
			bean.setTmnlAssetNo(pwrCtrls[1]);
			bean.setTotalNo(Short.parseShort(pwrCtrls[2]));
			
			tmnlList.add(bean);
		}
		
		Collections.sort(tmnlList,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				if(o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo())==0){
					return o1.getTotalNo().compareTo(o2.getTotalNo());
				}
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的总加组按所属终端,总加组分类排序
	 */
	protected List<CtrlParamBean> setCtrlTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < pwrCtrlList.size(); i++) {
			/*对应前台传入参数格式：'orgNo'+'`'+'consNo'+'`'+'tmnlAssetNo'+'`'+'totalNo'+'`'+'protocolCode'
			 * 用于参数下发，控制下发，方案保存*/
			String[] pwrCtrls = pwrCtrlList.get(i).split("`");
			if("".equals(pwrCtrls[2])||"".equals(pwrCtrls[3])||"".equals(pwrCtrls[4])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(pwrCtrls[0]);
			bean.setConsNo(pwrCtrls[1]);
			bean.setTmnlAssetNo(pwrCtrls[2]);
			bean.setTotalNo(Short.parseShort(pwrCtrls[3]));
			bean.setProtocolCode(pwrCtrls[4]);
			
			tmnlList.add(bean);
		}
		
		Collections.sort(tmnlList,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				if(o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo())==0){
					return o1.getTotalNo().compareTo(o2.getTotalNo());
				}
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		return tmnlList;
	}
	
	/**
	 * 将页面选择的总加组按规约编码，终端分类排序
	 */
	protected Map<String, List<CtrlParamBean>> createtrlTmnlMap() {
		List<CtrlParamBean> tmnl698List = new ArrayList<CtrlParamBean>();
		List<CtrlParamBean> tmnlOtherList = new ArrayList<CtrlParamBean>();
		for (int i = 0; i < pwrCtrlList.size(); i++) {
			/*对应前台传入参数格式：'orgNo'+'`'+'consNo'+'`'+'tmnlAssetNo'+'`'+'totalNo'+'`'+'protocolCode'
			 * 用于参数下发，控制下发，方案保存*/
			String[] pwrCtrls = pwrCtrlList.get(i).split("`");
			if("".equals(pwrCtrls[2])||"".equals(pwrCtrls[3])||"".equals(pwrCtrls[4])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setOrgNo(pwrCtrls[0]);
			bean.setConsNo(pwrCtrls[1]);
			bean.setTmnlAssetNo(pwrCtrls[2]);
			bean.setTotalNo(Short.parseShort(pwrCtrls[3]));
			bean.setProtocolCode(pwrCtrls[4]);
			
			if("5".equals(pwrCtrls[4])) 
				tmnl698List.add(bean);
			else 
				tmnlOtherList.add(bean);
		}
		
		//对698规约的终端按终端资产编号排序
		Collections.sort(tmnl698List,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				if(o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo())==0){
					return o1.getTotalNo().compareTo(o2.getTotalNo());
				}
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		//对其他规约的终端按终端资产编号排序
		Collections.sort(tmnlOtherList,new Comparator<CtrlParamBean>(){
			@Override
			public int compare(CtrlParamBean o1, CtrlParamBean o2) {
				if(o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo())==0){
					return o1.getTotalNo().compareTo(o2.getTotalNo());
				}
				return o1.getTmnlAssetNo().compareTo(o2.getTmnlAssetNo());
			}
		});
		Map<String, List<CtrlParamBean>> map = new HashMap<String, List<CtrlParamBean>>();
		map.put("698", tmnl698List);
		map.put("other", tmnlOtherList);
		return map;
	}
	
    /**
	 * 获取本机IP
	 * @return String 本地IP
	 * @throws Exception
	 */
	protected String getLocalIp() throws Exception{
    	InetAddress addr = InetAddress.getLocalHost();
	    String ipAddr=addr.getHostAddress().toString();//获得本机IP
	    return ipAddr;
    }
    
    protected String getWeekDaysByte(String week){
    	String[] weekDays = new String[7];
    	for (int i = 1; i < 8; i++) {
    		int f = 0;
    		for (int j = 0; j < week.length(); j++) {
    			if(week.charAt(j)-48 == i){
    				f = 1;
    			}
    		}
    		if(f == 0){
    			weekDays[i-1] = "0";
    		}else {
    			weekDays[i-1] = "1";
    		}
    	}
    	StringBuffer weekDaysByte = new StringBuffer();
    	for (int i = weekDays.length-1; i>=0; i--) {
    		weekDaysByte.append(weekDays[i]);
    	}
    	weekDaysByte.append("0");
    	return weekDaysByte.toString();
    }
    
	/**
	 * 根据时段控模板明细列表算出时段字节字符数组
	 * @param detailList
	 * @return
	 */
    protected String[] getSdksd(List<WPwrctrlTemplateDetail> detailList) {
		String [] str = new String[96];
		for (int i = 0; i < str.length; i+=2) {
			str[i] = "0";
		}
		for (int i = 0; i < detailList.size(); i++) {
			WPwrctrlTemplateDetail detail = detailList.get(i);
			Integer timeS = getTimeInteger(detail.getId().getSectionStart());
			Integer timeE =getTimeInteger(detail.getId().getSectionEnd());
			for (int j = timeS*2+1; j <= timeE*2; j+=2) {
				str[j] = "1";
			}
		}
		
		for (int i = 0; i < str.length; i++) {
			if(str[i]==null || "".equals(str[i])) {
				str[i]="0";
			}
		}
		
		StringBuffer resStr = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			resStr.append(str[i]);
		}
		
		return spliteTimeStrToArray(resStr.toString());
	}
	
	/**
	 * 将长度为96的字节字符串分成12段
	 * @param timeStr
	 * @return
	 */
    protected String[] spliteTimeStrToArray(String timeStr) {
		String[] resArray = new String[12];
		if(timeStr.length()!=96) {
			return null;
		}
		for (int i = 0; i < resArray.length; i ++) {
			resArray[i] = timeStr.substring(i*8,i*8+8);
			
			String[] subArray = new String[4];
			for (int j = 0; j < subArray.length; j++) {
				subArray[j] = resArray[i].substring(j*2,j*2+2);
			}
			StringBuffer resArrayBuffer = new StringBuffer();
			
			for (int j = 0; j < subArray.length; j++) {
				resArrayBuffer.append(subArray[subArray.length-j-1]);
			}
			
			resArray[i] = resArrayBuffer.toString();
		}
		return resArray;
	}
	
	/**
	 * 将时间段转化为对应的数字
	 * @param time
	 * @return
	 */
    protected Integer getTimeInteger(String time) {
		Integer timeInt =  Integer.parseInt(time.substring(0,2))*2;
		timeInt +="30".equals(time.substring(3,5))?1:0;
		return timeInt;
	}
	
	/**
	 * 将二进制码字符转化为中文星期数
	 * @param weeks
	 * @return
	 */
    protected String transWeeks(String weeks) {
		StringBuffer week = new StringBuffer(); 
		for (int i = weeks.length()-2; i >=0; i--) {
			if('1'==weeks.charAt(i)){
				week.append(getChineseNum(7-i));
			}
		}
		return week.toString();
	}
	
    protected String getChineseNum(Integer num){
		if(num ==1) return "一";
		if(num ==2) return "二";
		if(num ==3) return "三";
		if(num ==4) return "四";
		if(num ==5) return "五";
		if(num ==6) return "六";
		if(num ==7) return "日";
		return "";
	}
	
//	--------------------getter and setter--------------------------------------------------------------------------

	public String getTemplateName() {
		return templateName;
	}

	public WCtrlScheme getScheme() {
		return scheme;
	}

	public void setScheme(WCtrlScheme scheme) {
		this.scheme = scheme;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Byte getCurveNo() {
		return curveNo;
	}

	public void setCurveNo(Byte curveNo) {
		this.curveNo = curveNo;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<PwrctrlSchemeExecBean> getPwrctrlSchemeExecList() {
		return pwrctrlSchemeExecList;
	}

	public void setPwrctrlSchemeExecList(
			List<PwrctrlSchemeExecBean> pwrctrlSchemeExecList) {
		this.pwrctrlSchemeExecList = pwrctrlSchemeExecList;
	}

	public PowerCtrlQueryManager getPowerCtrlQueryManager() {
		return powerCtrlQueryManager;
	}

	public void setPowerCtrlQueryManager(
			PowerCtrlQueryManager powerCtrlQueryManager) {
		this.powerCtrlQueryManager = powerCtrlQueryManager;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
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

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}

	public String[] getConsNos() {
		return consNos;
	}

	public void setConsNos(String[] consNos) {
		this.consNos = consNos;
	}

	public String[] getOrgNos() {
		return orgNos;
	}

	public void setOrgNos(String[] orgNos) {
		this.orgNos = orgNos;
	}

	public String[] getTmnlAssetNos() {
		return tmnlAssetNos;
	}

	public void setTmnlAssetNos(String[] tmnlAssetNos) {
		this.tmnlAssetNos = tmnlAssetNos;
	}

	public String[] getTotalNos() {
		return totalNos;
	}

	public void setTotalNos(String[] totalNos) {
		this.totalNos = totalNos;
	}

	public void setRemoteCtrlManager(RemoteCtrlManager remoteCtrlManager) {
		this.remoteCtrlManager = remoteCtrlManager;
	}

	public void setwPwrctrlSchemeManager(WPwrctrlSchemeManager pwrctrlSchemeManager) {
		wPwrctrlSchemeManager = pwrctrlSchemeManager;
	}

	public List<WCtrlScheme> getSchemeList() {
		return schemeList;
	}

	public void setSchemeList(List<WCtrlScheme> schemeList) {
		this.schemeList = schemeList;
	}

	public String getValidSectionNo() {
		return validSectionNo;
	}

	public void setValidSectionNo(String validSectionNo) {
		this.validSectionNo = validSectionNo;
	}

	public String getSchemeCurveNo() {
		return schemeCurveNo;
	}

	public void setSchemeCurveNo(String schemeCurveNo) {
		this.schemeCurveNo = schemeCurveNo;
	}

	public void setwFactctrlTemplateManager(
			WFactctrlTemplateManager factctrlTemplateManager) {
		wFactctrlTemplateManager = factctrlTemplateManager;
	}

	public Integer getAlertTime1() {
		return alertTime1;
	}

	public void setAlertTime1(Integer alertTime1) {
		this.alertTime1 = alertTime1;
	}

	public Integer getAlertTime2() {
		return alertTime2;
	}

	public void setAlertTime2(Integer alertTime2) {
		this.alertTime2 = alertTime2;
	}

	public Integer getAlertTime3() {
		return alertTime3;
	}

	public void setAlertTime3(Integer alertTime3) {
		this.alertTime3 = alertTime3;
	}

	public Integer getAlertTime4() {
		return alertTime4;
	}

	public void setAlertTime4(Integer alertTime4) {
		this.alertTime4 = alertTime4;
	}

	public Short getFloatDownWtime() {
		return floatDownWtime;
	}

	public void setFloatDownWtime(Short floatDownWtime) {
		this.floatDownWtime = floatDownWtime;
	}

	public Short getPowerFreezetime() {
		return powerFreezetime;
	}

	public void setPowerFreezetime(Short powerFreezetime) {
		this.powerFreezetime = powerFreezetime;
	}

	public Short getFloatDownPercent() {
		return floatDownPercent;
	}

	public void setFloatDownPercent(Short floatDownPercent) {
		this.floatDownPercent = floatDownPercent;
	}

	public Short getCtrlTime() {
		return ctrlTime;
	}

	public void setCrtlTime(Short ctrlTime) {
		this.ctrlTime = ctrlTime;
	}

	public WFloatDownCtrl getFloatDownCtrl() {
		return floatDownCtrl;
	}

	public void setFloatDownCtrl(WFloatDownCtrl floatDownCtrl) {
		this.floatDownCtrl = floatDownCtrl;
	}

	public List<WTmnlFactctrl> getFactctrlResultList() {
		return factctrlResultList;
	}

	public void setFactctrlResultList(List<WTmnlFactctrl> factctrlResultList) {
		this.factctrlResultList = factctrlResultList;
	}

	public String getStaffNo() {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			return "";
		}
		return user.getStaffNo();
	}

	public void setStaffNo(String staffNo) {
		PSysUser user = (PSysUser)ActionContext.getContext().getSession().get("pSysUser");
		if(user==null){
			return;
		}
		this.staffNo = user.getStaffNo();
	}

	public Long getLineId() {
		return lineId;
	}

	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Long getSubsId() {
		return subsId;
	}

	public void setSubsId(Long subsId) {
		this.subsId = subsId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public void setCtrlTime(Short ctrlTime) {
		this.ctrlTime = ctrlTime;
	}
	
	public List<String> getPwrCtrlList() {
		return pwrCtrlList;
	}
	
	public void setPwrCtrlList(List<String> pwrCtrlList) {
		this.pwrCtrlList = pwrCtrlList;
	}

	public List<TmnlPwrctrlUserBean> getPwrctrlResultList() {
		return pwrctrlResultList;
	}

	public void setPwrctrlResultList(List<TmnlPwrctrlUserBean> pwrctrlResultList) {
		this.pwrctrlResultList = pwrctrlResultList;
	}

	public List<PwrctrlTempDetailBean> getReslutDetailList() {
		return reslutDetailList;
	}

	public void setReslutDetailList(List<PwrctrlTempDetailBean> reslutDetailList) {
		this.reslutDetailList = reslutDetailList;
	}

	public String getCtrlSchemeName() {
		return ctrlSchemeName;
	}

	public void setCtrlSchemeName(String ctrlSchemeName) {
		this.ctrlSchemeName = ctrlSchemeName;
	}

	public Boolean getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Boolean checkResult) {
		this.checkResult = checkResult;
	}

	public Boolean getBox1() {
		return box1;
	}

	public void setBox1(Boolean box1) {
		this.box1 = box1;
	}

	public Boolean getBox2() {
		return box2;
	}

	public void setBox2(Boolean box2) {
		this.box2 = box2;
	}

	public Boolean getBox3() {
		return box3;
	}

	public void setBox3(Boolean box3) {
		this.box3 = box3;
	}

	public Boolean getBox4() {
		return box4;
	}

	public void setBox4(Boolean box4) {
		this.box4 = box4;
	}

	public Integer getSecLength() {
		return secLength;
	}

	public void setSecLength(Integer secLength) {
		this.secLength = secLength;
	}

	public List<TmnlExecStatusBean> getExecResultList() {
		return execResultList;
	}

	public void setExecResultList(List<TmnlExecStatusBean> execResultList) {
		this.execResultList = execResultList;
	}

	public void setWtmnlCtrlStatusManger(WtmnlCtrlStatusManger wtmnlCtrlStatusManger) {
		this.wtmnlCtrlStatusManger = wtmnlCtrlStatusManger;
	}

	public String getCacheAndTmnlStatus() {
		return cacheAndTmnlStatus;
	}

	public void setCacheAndTmnlStatus(String cacheAndTmnlStatus) {
		this.cacheAndTmnlStatus = cacheAndTmnlStatus;
	}

	public void setwPwrctrlTemplateManager(WPwrctrlTemplateManager pwrctrlTemplateManager) {
		wPwrctrlTemplateManager = pwrctrlTemplateManager;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
