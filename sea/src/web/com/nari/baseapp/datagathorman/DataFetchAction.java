package com.nari.baseapp.datagathorman;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.engine.query.QueryMetadata;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.datagatherman.DataFetchTerminal;
import com.nari.baseapp.datagatherman.EDataFinder;
import com.nari.baseapp.datagatherman.ProtocolGroupDTO;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.DataCode;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.EventItem;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.terminalparam.TDataCombi;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;
import com.opensymphony.xwork2.ActionContext;

/**
 * 数据召测 Action
 * 
 * @author 鲁兆淞
 */
public class DataFetchAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private String startDate;
	private String endDate;
	private Date startTime;
	private Date endTime;
	private int timeOut = 300;
	private String prefix = "dataFetch";
	private Short point[];
	private List<String> fatherClearNos;
	private Integer start = 0;
	private String protocolCode;

	public Short[] getPoint() {
		return point;
	}

	public void setPoint(Short[] point) {
		this.point = point;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private String combiId;
	private static long keyIndex = 0;
	private String key;
	// 查找edatamp表中的内容
	private EDataFinder edataFinder = new EDataFinder();

	private Map<String, List> comboData;
	// 记录起止码
	private Integer startText;
	private Integer endText;
	private List<String> bigCodes;
	// 根据一个大项的名称找到对应的名称
	private Map bigCodeToName;
	// 记录数据的类型
	private Map errorMap = new HashMap();
	Map<String, Map<String, String>> meterName;
	private boolean allPns;

	// 终端不存在pn的列表
	private List errorPn;
	// 某一个终端地址
	private String tmnlNo;
	// 测量点或者总加组的列表
	private List<String> pns;
	// codes用于接收发过来的规约项来进行召测
	private List<String> userCodes;
	// private
	private Short eventLevel;

	private Integer limit;
	List<String> tmnlAssetNos;

	private List<String> cmds;// 记录编辑召测组合的时候要执行的命令
	private String frmNo;

	private String node;
	private Map combiMap;

	// 记录数查询到的结果的总数
	private Long usrCount;

	/*-----------------以下是四川盘龙电能表通断控制所用到的成员变量--------------------------*/
	// 召测返回状态，包括费控结果状态和开关状态
	private String[] status;
	// 召测返回电能表的测量点号
	private String[] meterAssetNoArr;

	// 电能表ID、是否欠费、原始开关状态、任务的超时时间
	private String origMeterId;
	private String oriDueStatus;
	private String onoffStatus;
	private int overTime;

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getMeterAssetNoArr() {
		return meterAssetNoArr;
	}

	public void setMeterAssetNoArr(String[] meterAssetNoArr) {
		this.meterAssetNoArr = meterAssetNoArr;
	}

	public String getOrigMeterId() {
		return origMeterId;
	}

	public void setOrigMeterId(String origMeterId) {
		this.origMeterId = origMeterId;
	}

	public String getOriDueStatus() {
		return oriDueStatus;
	}

	public void setOriDueStatus(String oriDueStatus) {
		this.oriDueStatus = oriDueStatus;
	}

	public String getOnoffStatus() {
		return onoffStatus;
	}

	public void setOnoffStatus(String onoffStatus) {
		this.onoffStatus = onoffStatus;
	}

	public int getOverTime() {
		return overTime;
	}

	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}

	// 验证struts2的jsonplugin的处理能力
	/****
	 * 
	 * 经测试 jsonplugin无比的强大 但是对key的操作不大行,运行结果为
	 * "test":{"{test1=aaaa, test11=abc}":[{"sex":"box","name":"hx"},<br>
	 * {"sex":"box","name":"hx"},<br>
	 * {"sex":"box","name":"hx"},{"sex":"box","name":"hx"}]} *
	 ****/
	public Map getTest() {
		// List<Map> l=new ArrayList<Map>();

		return test;
	}

	private Map test = new HashMap();

	private String clearNo;
	private List<Map> resultMap = new ArrayList<Map>();

	// 在后台处理的字符串的格式
	String formatStr = "yy年MM月dd日";

	/* 自动注入业务层 */
	private DataFetchManager dataFetchManager;

	public void setDataFetchManager(DataFetchManager dataFetchManager) {
		this.dataFetchManager = dataFetchManager;
	}

	/* 需要返回的Json */
	private boolean success = true; // 是否成功

	public boolean isSuccess() {
		return this.success;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	private List<TreeNode> treeNodeList; // 树节点列表

	public List<TreeNode> getTreeNodeList() {
		return this.treeNodeList;
	}

	private List<TDataCombi> tDataCombiList; // 透明规约组合列表

	public List<TDataCombi> getTDataCombiList() {
		return this.tDataCombiList;
	}

	private List<ProtocolGroupDTO> protocolGroupDTOList; // 透明规约组合列表

	public List<ProtocolGroupDTO> getProtocolGroupDTOList() {
		return this.protocolGroupDTOList;
	}

	private DataFetchTerminal dataFetchTerminal; // 终端

	public DataFetchTerminal getDataFetchTerminal() {
		return this.dataFetchTerminal;
	}

	/* 页面表单项 */
	private Integer queryType; // 查询类型

	private String groupName; // 透明规约组合名称

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	private String shareFlag; // 透明规约组合是否共享

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}

	private Integer lifeDay; // 透明规约组合有效天数

	public void setLifeDay(Integer lifeDay) {
		this.lifeDay = lifeDay;
	}

	private String[] protocolArray; // 透明规约数据项数组

	public void setProtocolArray(String[] protocolArray) {
		this.protocolArray = protocolArray;
	}

	private String terminalId; // 终端ID

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 查询透明规约类型树节点列表
	 * 
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	public String queryTreeNode() throws Exception {
		this.logger.debug("查询透明规约类型树节点列表开始");
		long timeStart = new Date().getTime();

		this.treeNodeList = this.dataFetchManager.queryTreeNode(queryType);

		long timeEnd = new Date().getTime();
		this.logger.debug("查询透明规约类型树节点列表结束，所用时间为：" + (timeEnd - timeStart)
				+ "毫秒。");
		return SUCCESS;
	}

	/**
	 * 查询透明规约组合
	 * 
	 * @return 跳转字符串
	 * @throws Exception
	 */
	public String queryProtocolGroup() throws Exception {
		this.logger.debug("查询透明规约组合开始");
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser staff = (PSysUser) session.get("pSysUser");
		this.protocolGroupDTOList = this.dataFetchManager
				.queryProtocolGroup(staff.getStaffNo());
		this.logger.debug("查询透明规约组合结束");
		return SUCCESS;
	}

	/**
	 * 保存透明规约组合
	 * 
	 * @return 跳转字符串
	 * @throws Exception
	 */
	public String saveProtocolGroup() throws Exception {
		this.logger.debug("保存透明规约组合开始");

		ProtocolGroupDTO protocolGroupDTO = new ProtocolGroupDTO();
		protocolGroupDTO.setCombiName(this.groupName);
		protocolGroupDTO.setCreateDate(new Date());
		protocolGroupDTO.setIsShare(true);
		protocolGroupDTO.setStaffNo(this.getPSysUser().getStaffNo());
		protocolGroupDTO.setValidDays(this.lifeDay);
		if (this.shareFlag == null)
			protocolGroupDTO.setIsShare(false);
		for (String protocol : this.protocolArray)
			protocolGroupDTO.getCombiMap().put(protocol, "");

		this.dataFetchManager.insertProtocolGroup(protocolGroupDTO);

		this.logger.debug("保存透明规约组合结束");
		return SUCCESS;
	}

	/**
	 * 查询终端
	 * 
	 * @return 跳转字符串
	 * @throws Exception
	 */
	public String queryTerminal() throws Exception {
		this.logger.debug("查询终端开始");

		// System.out.println(this.terminalId);
		// this.dataFetchTerminal = this.dataFetchManager
		// .queryTerminalById(this.terminalId);

		this.logger.debug("查询终端结束");
		return SUCCESS;
	}

	/**
	 * 四川盘龙电表通断控制 add by sungang 2010/11/06
	 * 
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String meterDataFetch() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Map mc = (Map) session.get("meterCtrolData");
		if (null == mc) {
			mc = new Hashtable();
			session.put("meterCtrolData", mc);
		}
		this.logger.debug("电表控制开始");

		// 获取操作人员信息，Ip地址用于生成操作日志
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		String ipAddr = getRequest().getRemoteAddr();

		// 更新费控表返回状态
		boolean flag = false;

		List<List> temp = new ArrayList<List>();

		// 实时通断任务返回response
		List<Response> responseList = new ArrayList<Response>();

		try {
			if (!TaskDeal.isCacheRunning()) {
				throw new RuntimeException("前置集群通信中断");
			}
			if (!TaskDeal.isFrontAlive()) {
				throw new RuntimeException("前置集群服务中断");
			}
			if (tmnlAssetNos == null || tmnlAssetNos.size() == 0) {
				throw new RuntimeException("终端不能为空");
			}
			// 只能对单只表进行通断操作，但是需返回两个状态，因此meterStatus[]大小初始化为2
			String[] meterStatus = new String[2];
			String[] meterAssetNoRet = new String[point.length];

			tmnlAssetNos = removeEcho(tmnlAssetNos);

			List<String> clearNos = dataFetchManager.findCodesByName(combiId);
			ArrayList<Item> codes = convertToItem(clearNos);

			ArrayList<Item> notZeros = new ArrayList<Item>();

			for (Item it : codes) {
				notZeros.add(it);
			}

			if (null == queryType) {
				throw new RuntimeException("请选择查询类型");
			}

			key = key == null ? (prefix) : key;
			Object obj = mc.get(key);
			if (null != obj) {
				throw new RuntimeException("当前的key有重复，请重新下发通断命令");
			}
			if (queryType.equals(1)) {
				logger.debug("电表实时通断开始");
				if (!notZeros.isEmpty()) {
					dealRealPns(temp, point, notZeros);
					// 区别一类数据召测,直接返回response
					responseList = driveMeterRealData(tmnlAssetNos, temp);
				}
				logger.debug("电表实时通断结束");
			}

			// 对response进行处理
			Response r = null;
			if (null != responseList && 0 < responseList.size()) {
				for (int i = 0; i < responseList.size(); i++) {
					r = (Response) responseList.get(i);
					String tmnlAssetNo = r.getTmnlAssetNo();
					// 增加终端操作日志
					LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
					lOpTmnlLog.setEmpNo(userInfo.getStaffNo());
					lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
					lOpTmnlLog.setIpAddr(ipAddr);
					lOpTmnlLog.setOpModule("电能表通断控制");
					lOpTmnlLog.setTmnlAssetNo(tmnlAssetNo);
					lOpTmnlLog.setOpTime(new Date());
					lOpTmnlLog.setOpObj((short) 7);

					if (r.getTaskStatus() == 3) {
						List<ResponseItem> resItems = r.getResponseItems();
						// 晓程规约对于拉合闸成功的response，没法区分
						ResponseItem item16 = findResItemByFn("16", resItems);
						ResponseItem item15 = findResItemByFn("15", resItems);
						if (item16 != null) {
							if (item16.getValue().equals("AA,拉闸")) {
								// 更新费控表状态
								flag = dataFetchManager.updateOnOffStatus(
										origMeterId, oriDueStatus, "OF");
								if (flag) {
									short errorCode = (short) 11;
									meterStatus[0] = Short.toString(errorCode);
									meterStatus[1] = "OF";
									meterAssetNoRet[i] = Short
											.toString(point[i]);
									
									
								} else {
									short errorCode = (short) 10;
									meterStatus[0] = Short.toString(errorCode);
									meterStatus[1] = onoffStatus;
									meterAssetNoRet[i] = Short
											.toString(point[i]);
								
								}
								//
							
								lOpTmnlLog.setOpButton("拉闸");
								lOpTmnlLog.setOpType((short) 5);
								lOpTmnlLog.setCurStatus("成功");
								lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
								lOpTmnlLog.setProtocolNo(item16.getCode());
								lOpTmnlLog.setCurValue(item16.getValue());
								//lOpTmnlLog.setOpObjNo(point[i].longValue());
								//lOpTmnlLog.setProtItemNo(item16.getCode());
							} else {
								short errorCode = (short) -27;
								meterStatus[0] = Short.toString(errorCode);
								meterStatus[1] = onoffStatus;
								meterAssetNoRet[i] = Short.toString(point[i]);
								//
								lOpTmnlLog.setOpButton("拉闸");
								lOpTmnlLog.setOpType((short) 5);
								lOpTmnlLog.setCurStatus("失败");								
								lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
								lOpTmnlLog.setProtocolNo(item16.getCode());
								lOpTmnlLog.setCurValue(item16.getValue());
								//lOpTmnlLog.setOpObjNo(point[i].longValue());
								//lOpTmnlLog.setProtItemNo(item16.getCode());
							}

						} else if (item15 != null) {
							if (item15.getValue().equals("55,合闸")) {
								// 更新费控表状态，如果该用户当前是交费状态，那么合闸后，该用户变成正常状态
								flag = dataFetchManager.updateOnOffStatus(
										origMeterId, oriDueStatus, "ON");
								if (flag) {
									short errorCode = (short) 21;
									meterStatus[0] = Short.toString(errorCode);
									meterStatus[1] = "ON";
									meterAssetNoRet[i] = Short
											.toString(point[i]);
									
								} else {
									short errorCode = (short) 20;
									meterStatus[0] = Short.toString(errorCode);
									meterStatus[1] = onoffStatus;
									meterAssetNoRet[i] = Short
											.toString(point[i]);
									
								//lOpTmnlLog.setCurStatus("合闸成功,更新开关状态失败!");
								}
								//
								lOpTmnlLog.setOpButton("合闸");
								lOpTmnlLog.setOpType((short) 6);	
								lOpTmnlLog.setCurStatus("成功");
								lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
								lOpTmnlLog.setProtocolNo(item15.getCode());
								lOpTmnlLog.setCurValue(item15.getValue());
							} else {
								short errorCode = (short) -27;
								meterStatus[0] = Short.toString(errorCode);
								meterStatus[1] = onoffStatus;
								meterAssetNoRet[i] = Short.toString(point[i]);
								//
								lOpTmnlLog.setOpButton("合闸");
								lOpTmnlLog.setOpType((short) 6);
								lOpTmnlLog.setCurStatus("失败");
						   		lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
								lOpTmnlLog.setProtocolNo(item15.getCode());
								lOpTmnlLog.setCurValue(item15.getValue());
							}

						} else {

							short errorCode = (short) -27;
							meterStatus[0] = Short.toString(errorCode);
							meterStatus[1] = onoffStatus;
							meterAssetNoRet[i] = Short.toString(point[i]);
							//
							lOpTmnlLog.setCurStatus("失败");
							if (combiId.equals("284")) {
								lOpTmnlLog.setOpType((short) 6);
								lOpTmnlLog.setOpButton("合闸");
							} else if (combiId.equals("283")) {
								lOpTmnlLog.setOpType((short) 5);
								lOpTmnlLog.setOpButton("拉闸");
							}
							lOpTmnlLog.setProtocolNo(clearNos.get(0));
							lOpTmnlLog.setCurValue("空值");
							lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
						
						}

					} else {

						short errorCode = (short) -1;
						meterStatus[0] = Short.toString(errorCode);
						meterStatus[1] = onoffStatus;
						meterAssetNoRet[i] = Short.toString(point[i]);
						//
						lOpTmnlLog.setCurStatus("失败");
						if (combiId.equals("284")) {
							lOpTmnlLog.setOpType((short) 6);
							lOpTmnlLog.setOpButton("合闸");
						} else if (combiId.equals("283")) {
							lOpTmnlLog.setOpType((short) 5);
							lOpTmnlLog.setOpButton("拉闸");
						}
						lOpTmnlLog.setProtocolNo(clearNos.get(0));
						lOpTmnlLog.setCurValue("空值");
						lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));

					}
					this.addOpTmnlLog(lOpTmnlLog);
				}// for

				this.setStatus(meterStatus);
				this.setMeterAssetNoArr(meterAssetNoRet);
			} else {
				// 增加终端操作失败日志
				LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
				lOpTmnlLog.setEmpNo(userInfo.getStaffNo());
				lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
				lOpTmnlLog.setIpAddr(ipAddr);
				lOpTmnlLog.setOpModule("电能表通断控制");
				lOpTmnlLog.setTmnlAssetNo(tmnlAssetNos.get(0));
				lOpTmnlLog.setOpTime(new Date());
				lOpTmnlLog.setOpObj((short) 7);
				lOpTmnlLog.setCurStatus("失败");
				if (combiId.equals("284")) {
					lOpTmnlLog.setOpType((short) 6);
					lOpTmnlLog.setOpButton("合闸");
				} else if (combiId.equals("283")) {
					lOpTmnlLog.setOpType((short) 5);
					lOpTmnlLog.setOpButton("拉闸");
				}
				lOpTmnlLog.setProtocolNo(clearNos.get(0));
	
				lOpTmnlLog.setCurValue("空值");
				for (int i = 0; i < point.length; i++) {
					short errorCode = (short) 3;
					meterStatus[0] = Short.toString(errorCode);
					meterStatus[1] = onoffStatus;
					meterAssetNoRet[i] = point[i].toString();
					lOpTmnlLog.setOpObjNo(Long.valueOf(origMeterId));
					this.addOpTmnlLog(lOpTmnlLog);
				}

				this.setStatus(meterStatus);
				this.setMeterAssetNoArr(meterAssetNoRet);

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		this.logger.debug("电表控制结束");
		return SUCCESS;
	}

	/**
	 * 根据fn遍历ResponseItemList，找到则返回该ResponseItem，否则返回null add by sungang
	 * 2010/11/06
	 * 
	 * @param string
	 * @param resItems
	 * @return
	 */
	private ResponseItem findResItemByFn(String fn, List<ResponseItem> resItems) {
		if (resItems == null || resItems.size() <= 0 || fn == null) {
			return null;
		}
		for (int j = 0; j < resItems.size(); j++) {
			ResponseItem item = resItems.get(j);
			String Fn = Short.toString(item.getFn());
			if (fn.equals(Fn)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 数据召测 modified by sungang 2010-10-16,在不影响原有程序的基础上，增加盘龙晓程规约处理分支，
	 * 主要针对二类数据的召测
	 * 
	 * @return Struts 跳转字符串
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String dataFetch() throws Exception {
		Map session = ActionContext.getContext().getSession();

		Map mm = (Map) session.get("responseData");
		if (null == mm) {
			mm = new Hashtable();
			session.put("responseData", mm);
		}
		if (allPns) {
			if (protocolCode.equals("C")) {
				List list = dataFetchManager.findAllPns(tmnlNo);
				// 针对档案导入可能出现问题的补救措施
				if (list.size() == 1) {
					point = new Short[list.size()];
				} else {
					point = new Short[list.size() - 1];
				}

				int index = 0;
				for (Object o : list) {
					Short temp = (Short) o;
					if (!temp.equals((short) 0)) {
						point[index++] = temp;
					}

				}
			} else {
				List list = dataFetchManager.findAllPns(tmnlNo);
				point = new Short[list.size()];
				int index = 0;
				for (Object o : list) {
					point[index++] = (Short) o;
				}
			}
		}
		this.logger.debug("数据召测开始");
		int flat = 0;
		List<List> temp = new ArrayList<List>();

		List<Response> responseList = new ArrayList<Response>();
		try {
			if (!TaskDeal.isCacheRunning()) {
				throw new RuntimeException("前置集群通信中断");
			}
			if (!TaskDeal.isFrontAlive()) {
				throw new RuntimeException("前置集群服务中断");
			}
			if (tmnlAssetNos == null || tmnlAssetNos.size() == 0) {
				throw new RuntimeException("终端不能为空");
			}
			tmnlAssetNos = removeEcho(tmnlAssetNos);

			List<String> clearNos = new ArrayList<String>();
			// 如果用户自定义的userCodes不为空，使用用户定义的规约项进行召测
			if (!queryType.equals(3) && null != userCodes
					&& !userCodes.isEmpty()) {
				clearNos = userCodes;
			} else {
				clearNos = dataFetchManager.findCodesByName(combiId);
			}
			this.logger.debug("初始化透明编码无错误");
			this.fatherClearNos = clearNos;
			// 1E1110
			ArrayList<Item> codes = convertToItem(clearNos);
			ArrayList<Item> zeros = new ArrayList<Item>();
			ArrayList<Item> notZeros = new ArrayList<Item>();
			for (Item it : codes) {
				if (it.getCode().startsWith("4")) {
					zeros.add(it);
				} else {
					notZeros.add(it);
				}
			}
			if (null == queryType) {
				throw new RuntimeException("请选择查询类型");
			}
			key = key == null ? (prefix) : key;
			Object obj = mm.get(key);
			if (null != obj) {
				throw new RuntimeException("当前的key有重复，请重新召测");
			}
			if (queryType.equals(1)) {
				logger.debug("数据的实时召测开始");
				if (!notZeros.isEmpty()) {
					dealRealPns(temp, point, notZeros);
					// RealParamsOnlyZero(temp);
					responseList = driveRealData(tmnlAssetNos, temp);
				}
				if (zeros.size() > 0) {
					TaskDeal td = new TaskDeal();
					List<RealDataItem> lrs = new ArrayList<RealDataItem>();
					RealDataItem r = new RealDataItem();
					r.setCodes(zeros);
					r.setPoint((short) 0);
					lrs.add(r);
					td.callRealData(tmnlAssetNos, lrs);
					if (key.startsWith(prefix)) {
						responseList.addAll(td.getAllResponse(timeOut));
					} else {
						DataFetchProxy df = new DataFetchProxy(td, key, prefix,
								this);
						df.getAllResponse(timeOut);
					}
				}
				logger.debug("数据的实时召测结束");
			} else if (queryType.equals(2)) {
				logger.debug("数据的历史召测开始");

				if (notZeros.size() > 0) {
					dealHisPns(temp, point, notZeros);
					// HistoryParamsOnlyZero(hisDataItems);
					// 内置将没有pn的设置成0
					if (protocolCode.equals("C")) {
						responseList = driveHisDataOfPL(tmnlAssetNos, temp,
								startTime, endTime, protocolCode, point.length);
					} else {
						responseList = driveHisData(tmnlAssetNos, temp,
								startTime, endTime);
					}
				}
				if (zeros.size() > 0) {
					TaskDeal td = new TaskDeal();
					List<HisDataItem> hisDataItems = new ArrayList<HisDataItem>();
					HisDataItem h = new HisDataItem();
					h.setCodes(zeros);
					h.setPoint((short) 0);
					h.setStartTime(startTime);
					h.setEndTime(endTime);
					hisDataItems.add(h);
					td.callHisData(tmnlAssetNos, hisDataItems);
					if (key.startsWith(prefix)) {
						responseList.addAll(td.getAllResponse(timeOut));
					} else {
						DataFetchProxy df = new DataFetchProxy(td, key, prefix,
								this);
						df.getAllResponse(timeOut);
					}
				}
				logger.debug("数据的历史召测结束");
			} else if (queryType.equals(3)) {
				logger.debug("数据的事件召测开始");
				TaskDeal td = new TaskDeal();
				List<EventItem> events = new ArrayList<EventItem>();

				// for (int i = 0; i < point.length; i++) {
				EventItem e = new EventItem();
				// e.setCode(codes);
				// e.setFn(fn);
				ArrayList<Item> pmpn = new ArrayList<Item>();
				pmpn.add(new Item("pm", startText + ""));
				pmpn.add(new Item("pn", endText + ""));
				if (eventLevel == null) {
					throw new RuntimeException("请选择事件级别");
				}
				e.setCode(pmpn);
				e.setFn(eventLevel);
				// e.setPoint(point==);
				e.setPoint((short) 0);
				// e.setEndTime(endTime);
				// e.setStartTime(startTime);
				events.add(e);
				// }
				td.callEvent(tmnlAssetNos, events);
				if (key.startsWith(prefix)) {
					responseList = td.getAllResponse(timeOut);
					// addResponseData(responseList);
				} else {
					DataFetchProxy df = new DataFetchProxy(td, key, prefix,
							this);
					df.getAllResponse(timeOut);
				}
				logger.debug("数据的事件召测结束");
			}
			// 如果是采用异步的方式得到的结果，那么后面的代码就不用支持了
			if (!key.startsWith(prefix)) {
				addMessage("over");
				this.logger.debug("数据召测结束");
				return SUCCESS;
			}
			// if (responseList != null && responseList.size() != 0) {
			// bigCodeToName = dataFetchManager.findBigCodeToName(bigCodes);
			// }
			// 使用map一次查询对应可以提交执行效率
			// 对得到的数据进行处理成适合ext 作为输出的格式
			// 记录查到的编码的名称
			List<String> names = new ArrayList<String>();
			List<String> eventNos = new ArrayList<String>();
			Map<String, String> dataTypeNameMp = dataFetchManager.mapTypeName();
			HashSet<String> regData = new HashSet<String>();
			// 记录查到的dataid的列表
			List dataIds = new ArrayList();
			for (Response r : responseList) {
				List<DbData> dbs = r.getDbDatas();
				if (dbs == null) {
					continue;
				}
				if (null != r && r.getErrorCodeId() < 0) {
					errorMap.put(r.getTmnlAssetNo(), r.getTaskStatus());
				}
				for (DbData db : dbs) {
					// 填充在前台需要的数据需要的
					ArrayList<DataCode> dcs = db.getDataCodes();
					dataIds.add(db.getDataId());
					for (DataCode dc : dcs) {
						// 放行事件召测
						if (queryType.equals(3)) {
							eventNos.add(dc.getName());
							// Format format = new SimpleDateFormat(formatStr);
							// Date startTime = null;
							// Date endTime = null;
							// try {
							// startTime = (Date) format
							// .parseObject(startDate);
							// } catch (Exception e) {
							// }
							// try {
							// endTime = (Date) format.parseObject(endDate);
							// } catch (Exception e) {
							// }
							boolean b = true;
							if (startTime != null) {
								b = startTime.before(db.getTime());
							}
							if (!b) {
								continue;
							}
							if (endTime != null) {
								b = endTime.after(db.getTime());
							}
							if (!b) {
								continue;
							}
						}
						Map m = new HashMap();
						// 对结果集进行过滤
						String name = dc.getName();
						if (!queryType.equals(3) && !isOk(clearNos, name)) {
							continue;
						}
						// 到这一步说明有数据返回
						// regData.add(r.getTmnlAssetNo());
						names.add(name);
						// if (dc.getValue().getClass().isArray()) {
						// double[] objs = (double[]) dc.getValue();
						// int f = 0;
						// for (double ob : objs) {
						// if (!(ob + "").startsWith("-9999")) {
						// f = 1;
						// break;
						// }
						// }
						// if (f == 0) {
						// continue;
						// }
						// } else {
						// String v = dc.getValue() + "";
						// if (v.startsWith("-9999")) {
						// continue;
						// }
						// }
						// regData.remove(r.getTmnlAssetNo());
						// m.put("time", db.getTime());
						m.put("taskStatus", r.getTaskStatus() + "");
						// 错误编码
						m.put("errorCode", r.getErrorCode());
						m.put("name", dc.getName());
						m.put("errorCodeStr", r.getErrorCodeStr());
						m.put("tmnlAssetNo", r.getTmnlAssetNo());
						// m.put(key, value)
						m.put("errorCode", r.getErrorCode());
						m.put("funCode", r.getFuncCode());
						m.put("areaCode", db.getAreaCode());
						m.put("ct", db.getCT());
						m.put("dataId", db.getDataId());
						// m.put("dataType", db.getDataType());
						m.put("dataTypeName", dataTypeNameMp.get(db
								.getDataType()
								+ ""));
						m.put("pt", db.getPT());
						m.put("time", db.getTime());
						m.put("valueType", db.getValueType());
						m.put("interval", db.getInterval());
						m.put("mark", db.isMark());
						m.put("value", dc.getValue());
						m.put("pn", db.getPn());
						// // 前台用于分组的字段
						// m.put("group", dc.getName() + "_" + db.getPn());
						resultMap.add(m);
					}
					// 得到一个透明编码到透明编码名称的map

				}
			}
			if (null == resultMap || resultMap.size() == 0) {
				return SUCCESS;
			}
			// bigCodeToName = dataFetchManager.findBigCodeToName(bigCodes);
			Map<String, String> map = null;
			// Map<String,String> mapPn=dataFetchManager.findDataIdPn(dataIds);
			Map<String, String> mapDataType = null;
			if (queryType.equals(3)) {
				map = dataFetchManager.mapEventName(eventNos);
			} else {
				map = dataFetchManager.findCodeName(names);
				mapDataType = dataFetchManager.findCodeDataType(names);
			}
			if (map != null) {
				for (Map m : resultMap) {
					m.put("showName", map.get(m.get("name")));
					if (mapDataType == null) {
						m.put("unit", "");
					} else {
						m.put("unit", mapDataType.get(m.get("name")));
					}
					// m.put("pn", mapPn.get(m.get("dataId").toString()));
				}
			}
			// 遍历这个注册数据
			// 此时regData有的数据为数据为-9999的数据,并且在后台全部被去掉，在
			// 前台不应该显示为失败
			for (String str : regData) {
				errorMap.put(str, "8");
			}
			// System.out.println(responseList.get(0).getResponseItems());
		} catch (Exception e) {
			logger.error("召测出错:" + e.getMessage());
			e.printStackTrace();
			message = e.getMessage();

		}
		addMessage("over");
		this.logger.debug("数据召测结束");
		return SUCCESS;
	}

	/**
	 * 
	 * @param lrs
	 *            召测结果的数组
	 * @param fatherCodes
	 *            透明规约的项
	 */
	@SuppressWarnings("unchecked")
	public List<Map> parseDataFetch(List<Response> lrs) {
		try {
			List<Map> rs = new ArrayList<Map>();
			// if (responseList != null && responseList.size() != 0) {
			// bigCodeToName = dataFetchManager.findBigCodeToName(bigCodes);
			// }
			// 使用map一次查询对应可以提交执行效率
			// 对得到的数据进行处理成适合ext 作为输出的格式
			// 记录查到的编码的名称
			List<String> names = new ArrayList<String>();
			List<String> eventNos = new ArrayList<String>();
			Map<String, String> dataTypeNameMp = dataFetchManager.mapTypeName();
			HashSet<String> regData = new HashSet<String>();
			// 记录查到的dataid的列表
			List dataIds = new ArrayList();
			for (Response r : lrs) {
				List<DbData> dbs = r.getDbDatas();
				if (dbs == null) {
					continue;
				}
				if (null != r && (r.getErrorCodeId() < 0)) {
					errorMap.put(r.getTmnlAssetNo(), r.getErrorCodeStr());
				}
				for (DbData db : dbs) {
					// 填充在前台需要的数据需要的
					ArrayList<DataCode> dcs = db.getDataCodes();
					dataIds.add(db.getDataId());
					for (DataCode dc : dcs) {
						// 放行事件召测
						if (queryType.equals(3)) {
							eventNos.add(dc.getName());
							boolean b = true;
							if (startTime != null) {
								b = startTime.before(db.getTime());
							}
							if (!b) {
								continue;
							}
							if (endTime != null) {
								b = endTime.after(db.getTime());
							}
							if (!b) {
								continue;
							}
						}
						Map m = new HashMap();
						// 对结果集进行过滤
						String name = dc.getName();
						if (!queryType.equals(3)
								&& !isOk(this.fatherClearNos, name)) {
							continue;
						}
						// 到这一步说明有数据返回
						// regData.add(r.getTmnlAssetNo());
						names.add(name);
						// if (dc.getValue().getClass().isArray()) {
						// double[] objs = (double[]) dc.getValue();
						// int f = 0;
						// for (double ob : objs) {
						// if (!(ob + "").startsWith("-9999")) {
						// f = 1;
						// break;
						// }
						// }
						// if (f == 0) {
						// continue;
						// }
						// } else {
						// String v = dc.getValue() + "";
						// if (v.startsWith("-9999")) {
						// continue;
						// }
						// }
						// regData.remove(r.getTmnlAssetNo());
						m.put("time", db.getTime());
						m.put("taskStatus", r.getTaskStatus() + "");
						// 错误编码
						m.put("errorCode", r.getErrorCode());
						m.put("name", dc.getName());
						m.put("errorCodeStr", r.getErrorCodeStr());
						m.put("tmnlAssetNo", r.getTmnlAssetNo());
						// m.put(key, value)
						m.put("errorCode", r.getErrorCode());
						m.put("funCode", r.getFuncCode());
						m.put("areaCode", db.getAreaCode());
						m.put("ct", db.getCT());
						m.put("dataId", db.getDataId());
						// m.put("dataType", db.getDataType());
						m.put("dataTypeName", dataTypeNameMp.get(db
								.getDataType()
								+ ""));
						m.put("pt", db.getPT());
						m.put("time", db.getTime());
						m.put("valueType", db.getValueType());
						m.put("interval", db.getInterval());
						m.put("mark", db.isMark());
						m.put("value", dc.getValue());
						m.put("pn", db.getPn());
						// // 前台用于分组的字段
						// m.put("group", dc.getName() + "_" + db.getPn());
						rs.add(m);
					}
					// 得到一个透明编码到透明编码名称的map

				}
			}
			if (null == rs || rs.size() == 0) {
				return new ArrayList<Map>();
			}
			// bigCodeToName = dataFetchManager.findBigCodeToName(bigCodes);
			Map<String, String> map = null;
			// Map<String,String> mapPn=dataFetchManager.findDataIdPn(dataIds);
			Map<String, String> mapDataType = null;
			if (queryType.equals(3)) {
				map = dataFetchManager.mapEventName(eventNos);
			} else {
				map = dataFetchManager.findCodeName(names);
				mapDataType = dataFetchManager.findCodeDataType(names);
			}
			if (map != null) {
				for (Map m : rs) {
					m.put("showName", map.get(m.get("name")));
					if (mapDataType == null) {
						m.put("unit", "");
					} else {
						m.put("unit", mapDataType.get(m.get("name")));
					}
					// m.put("pn", mapPn.get(m.get("dataId").toString()));
				}
			}
			// 遍历这个注册数据
			// 此时regData有的数据为数据为-9999的数据,并且在后台全部被去掉，在
			// 前台不应该显示为失败
			for (String str : regData) {
				errorMap.put(str, "8");
			}
			return rs;
			// System.out.println(responseList.get(0).getResponseItems());
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return new ArrayList<Map>();
	}

	/****
	 * 将组合和召测数据显示为树形结构 *
	 ***/
	public String findForTree() {
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser staff = (PSysUser) session.get("pSysUser");
		try {
			this.dataFetchManager.findGroupForTree(staff.getStaffNo());
		} catch (Exception e) {
			message = e.getMessage();
		}
		return SUCCESS;
	}

	/****
	 * 后台分页版本的查询
	 * 
	 * ****/
	@SuppressWarnings("unchecked")
	public String dealPageTree() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page page = this.dataFetchManager.findClick(user, node, start,
					limit);
			resultMap = page.getResult();
			usrCount = page.getTotalCount();
		} catch (Exception e) {
			message = e.getMessage();
		}
		return SUCCESS;
	}

	/****
	 * 左边树相关的操作 *
	 **/
	public String dealTree() {
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser user = (PSysUser) session.get("pSysUser");
		try {
			resultMap = dataFetchManager.findClickDirect(user, node);
		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 通过终端地址找到相应的表计的信息 *****/
	@SuppressWarnings("unchecked")
	public String findTmnlCmp() {
		try {
			Page page = new Page(start, limit);
			page = dataFetchManager.findCmpByFinder(edataFinder, start, limit);
			resultMap = page.getResult();
			usrCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public static void main(String[] args) {
		Object a = new double[] { 1.2, 3.4, 5.6 };
		double[] d = (double[]) a;
		System.out.println(a.getClass().isArray());
		// RealDataItem ri = new RealDataItem();
		// ParamItem pi = new ParamItem();
		// HisDataItem d = new HisDataItem();
		// Item i = new Item("aa");
		// pi.getItems();
		// Response rs = new Response();
		// EventItem ei = new EventItem();
		// ResponseItem rpi = new ResponseItem();
		// // System.out.println(Short.valueOf(null)); error
		// // System.out.println(Short.parseShort(null)); // error
		// List<String> str = new ArrayList<String>();
		// str.add("1");
		// str.add("2");
		// List<String> l1 = new ArrayList<String>();
		// l1.add("1");
		// l1.add("2");
		// List list = new ArrayList();
		// list.add(l1);
		// l1 = new ArrayList<String>();
		// l1.add("a");
		// l1.add("b");
		// list.add(l1);
		// System.out.println(list.get(1));
	}

	/***
	 * 
	 * 查找某个用户的所有可见得召测组合数据结构 *
	 **/
	public String findStructure() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser staff = (PSysUser) session.get("pSysUser");
			combiMap = dataFetchManager.findAllCombi(staff.getStaffNo());
		} catch (Exception e) {
			message = e.getMessage();
		}
		return SUCCESS;
	}

	/****
	 * 
	 * 前端上传需要改变的数据，通过此方法进行执行 *
	 **/
	public String doEdit() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser staff = (PSysUser) session.get("pSysUser");
			dataFetchManager.updateMain(cmds, staff);
			// 对当前的操作进行日志的记录

		} catch (Exception e) {
			message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 找到一个终端对应的采集器
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findFrmAsset() {
		try {
			resultMap = dataFetchManager.findFrmAsset(tmnlNo);
		} catch (Exception e) {
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/**
	 * 找到一个采集器对应的终端列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findPns() {
		try {
			resultMap = dataFetchManager.findPns(frmNo);
		} catch (Exception e) {
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/****
	 * 根据终端资产号和pn列表找到标记对应的用户名 *
	 ****/
	public String findTmnlPnCons() {
		try {
			meterName = dataFetchManager.findMeterCons(tmnlNo, pns);
			comboData = dataFetchManager.findFrmToAsset(tmnlNo);
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	// 找到comboData的结构
	public String findComboData() {
		try {
			comboData = dataFetchManager.findFrmToAsset(tmnlNo);
		} catch (Exception e) {
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	/****
	 *通过一个key在session的对应的hashtable里面取到值 约定response 列表放置在session中的responseData里面
	 * *
	 *****/
	@SuppressWarnings("unchecked")
	public String querySessionData() {
		try {
			Map session = ActionContext.getContext().getSession();
			Map m = (Map) session.get("responseData");
			// 通过message来传递消息
			resultMap = (List) m.get(key);
			if (null == resultMap) {
				resultMap = new ArrayList<Map>();
			}
			String msg = this.queryResponseMessage();
			if (null != msg && msg.equals("over")) {
				this.message = "over";
				m.clear();
				return SUCCESS;
			}
			m.clear();
		} catch (Exception e) {
			e.printStackTrace();
			// this.message=e.getMessage();
		}
		return SUCCESS;
	}

	/**
	 * 增加一些数据到session中
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void addResponseData(List<Response> list) {
		if (key.startsWith(prefix)) {
			return;
		}
		Map session = ActionContext.getContext().getSession();
		Map m = (Map) session.get("responseData");
		if (null == m) {
			m = new Hashtable();
			session.put("responseData", m);
		}
		Object obj = m.get(key);
		if (null == obj) {
			m.put(key, new ArrayList());
		}
		List get = (List) m.get(key);
		parseDataFetch(list);
		get.addAll(list);
	}

	/**
	 * 增加一个key的message
	 * 
	 * @param message
	 */
	@SuppressWarnings("unchecked")
	private void addMessage(String message) {
		if (null == key || key.startsWith(prefix)) {
			return;
		}
		Map session = ActionContext.getContext().getSession();
		Map m = (Map) session.get("responseMessage");
		if (null == m) {
			m = new Hashtable();
			session.put("responseMessage", m);
		}
		m.put(key, message);
	}

	/**
	 * 得到当前线程下面的key的message
	 * 
	 * @return
	 */
	private String queryResponseMessage() {
		Map session = ActionContext.getContext().getSession();
		Map m = (Map) session.get("responseMessage");
		if (null == m) {
			return null;
		}
		String message = (String) m.get(key);
		return message;
	}

	/**
	 * 清空某个key中的session中的所以的数据， 防止数据堆积，在监视完成时由前台调用
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String clearAllSesssionData() {
		try {
			Map session = ActionContext.getContext().getSession();
			Map m = (Map) session.get("responseMessage");
			Map mm = (Map) session.get("responseData");
			if (null != m) {
				m.remove(key);
			}
			if (null != mm) {
				mm.remove(key);
			}
		} catch (Exception e) {
		}
		return SUCCESS;
	}

	public Map<String, List> getComboData() {
		return comboData;
	}

	/****
	 * 工具方法 得到召测项列表的父节点 的item 列表 *
	 * 
	 * @throws DBAccessException
	 *             *
	 **/
	private ArrayList<Item> convertToItem(List<String> clearNos)
			throws DBAccessException {
		ArrayList<Item> rs = new ArrayList<Item>();
		bigCodes = new ArrayList<String>();
		Set<String> st = new HashSet<String>();
		for (String no : clearNos) {
			String f = dataFetchManager.getFather(no);
			if (f == null) {
				f = no;
			}
			if (!st.contains(f)) {
				st.add(f);
				bigCodes.add(f);
				rs.add(new Item(f));
			}
		}
		return rs;
	}

	/***
	 * 对得到的结果进行过滤是，判断的依据方法 *
	 **/
	private boolean isOk(List<String> clearNos, String name) {
		for (String no : clearNos) {
			String father = no.replace("F", "");
			if (name.indexOf(father) == 0) {
				return true;
			}
		}
		return false;
	}

	/****
	 * 对终端资产号进行过滤，去掉重复的 *
	 ***/
	private List<String> removeEcho(List list) {
		Set<String> st = new HashSet(list);
		return new ArrayList<String>(st);
	}

	/***** 对实时数据召测的参数进行中pn只能为零的部分进行过滤 ****/
	private void RealParamsOnlyZero(List<List> temp) {
		for (List<RealDataItem> l : temp) {
			for (RealDataItem r : l) {
				ArrayList<Item> codes = r.getCodes();
				for (Item i : codes) {
					if (i.getCode().startsWith("4")) {
						r.setPoint((short) 0);
					}
				}
			}
		}

	}

	/******
	 * 
	 * *
	 ****/

	/***
	 * 一类数据生成任务得到结果
	 * 
	 * @param clearNos
	 *            *
	 ***/
	@SuppressWarnings("unchecked")
	private List<Response> driveRealData(List<String> tmnlNos, List<List> temp) {
		List<Response> result = new ArrayList<Response>();
		int i = 0;
		for (List l : temp) {
			TaskDeal td = new TaskDeal();
			// List<RealDataItem> lr=l;
			i += l.size();
			td.callRealData(tmnlNos, l);
			if (key.startsWith(prefix)) {
				List rList = td.getAllResponse(timeOut);
				result.addAll(rList);
			} else {
				DataFetchProxy df = new DataFetchProxy(td, key, prefix, this);
				df.getAllResponse(timeOut);
			}
		}
		logger.debug(i);
		return result;
	}

	// ------------------add bu sungang---2010-10-12----------start--------
	@SuppressWarnings("unchecked")
	private List<Response> driveMeterRealData(List<String> tmnlNos,
			List<List> temp) {
		List<Response> result = new ArrayList<Response>();
		for (List l : temp) {
			TaskDeal td = new TaskDeal();
			td.callRealData(tmnlNos, l);
			if (key.startsWith(prefix)) {
				List rList = td.getAllResponse(45);
				result.addAll(rList);
			} else {
				DataFetchProxy df = new DataFetchProxy(td, key, prefix, this);
				result = df.getMeterAllResponse(overTime);
			}
		}
		return result;
	}

	// ------------add bu sungang---2010-10-12------end------------------

	/***** 处理一类数据的point，来生成一个list套一个list的数据 ****/
	@SuppressWarnings("unchecked")
	private void dealRealPns(List temp, Short pn[], ArrayList<Item> codes) {
		List<RealDataItem> reals = new ArrayList<RealDataItem>();
		int flat = 0;
		for (int i = 0; i < pn.length; i++) {
			flat++;
			RealDataItem real = new RealDataItem();
			real.setCodes(codes);
			real.setPoint(point[i] == null ? 1 : point[i]);
			reals.add(real);
			if (flat == 10 || i == pn.length - 1) {
				flat = 0;
				temp.add(reals);
				reals = new ArrayList<RealDataItem>();
			}
		}
	}

	/**** 处理二类数据的pn ****/

	@SuppressWarnings("unchecked")
	private void dealHisPns(List temp, Short pn[], ArrayList<Item> codes) {
		List<HisDataItem> reals = new ArrayList<HisDataItem>();
		int flat = 0;
		for (int i = 0; i < pn.length; i++) {
			flat++;
			HisDataItem real = new HisDataItem();
			real.setCodes(codes);
			real.setPoint(point[i] == null ? 1 : point[i]);
			reals.add(real);
			if (flat == 1000 || i == pn.length - 1) {
				flat = 0;
				temp.add(reals);
				reals = new ArrayList<HisDataItem>();
			}
		}

	}

	/************** 对历史数据召测的参数中pn只能为零的部分进行过滤 **************************/
	private void HistoryParamsOnlyZero(List<HisDataItem> hs) {
		for (HisDataItem h : hs) {
			ArrayList<Item> codes = h.getCodes();
			for (Item i : codes) {
				if (i.getCode().startsWith("4")) {
					h.setPoint((short) 0);
				}
			}
		}
	}

	private Date addDays(Date date, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, i);
		return c.getTime();
	}

	/*****************
	 * hisDataItems***tmnlAssetNos 对开始时间和结束时间进行处理来的到召测的结果
	 * 
	 * @param clearNos
	 *            *
	 ***********/
	@SuppressWarnings("unchecked")
	private List driveHisData(List assetNos, List<List> temp, Date start,
			Date end) {
		List list = new ArrayList();
		// 开始时间和结束时间之间相差的天数
		int i = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
		for (List<HisDataItem> lh : temp) {
			for (int j = 0; j < i; j++) {
				TaskDeal td = new TaskDeal();
				for (HisDataItem h : lh) {
					ArrayList<Item> codes = h.getCodes();
					h.setStartTime(addDays(start, j));
					h.setEndTime(addDays(start, j + 1));
				}
				td.callHisData(assetNos, lh);
				if (!key.startsWith(prefix)) {
					DataFetchProxy df = new DataFetchProxy(td, key, prefix,
							this);
					df.getAllResponse(timeOut);
				} else {
					List rList = td.getAllResponse(timeOut);
					list.addAll(rList);
				}
			}
		}
		return list;
	}

	// ----------------------盘龙晓程规约处理分支----------------------------------------
	@SuppressWarnings("unchecked")
	private List driveHisDataOfPL(List assetNos, List<List> temp, Date start,
			Date end, String protocolCode, int len) {
		List list = new ArrayList();
		// 开始时间和结束时间之间相差的天数
		int i = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
		for (List<HisDataItem> lh : temp) {
			for (int j = 0; j < i; j++) {
				TaskDeal td = new TaskDeal();
				for (HisDataItem h : lh) {
					ArrayList<Item> codes = h.getCodes();
					h.setStartTime(addDays(start, j));
					h.setEndTime(addDays(start, j + 1));
				}
				td.callHisData(assetNos, lh);
				if (!key.startsWith(prefix)) {
					DataFetchProxy df = new DataFetchProxy(td, key, prefix,
							this);
					df.getAllResponseOfPL(timeOut, len);

				} else {
					List rList = td.getAllResponse(timeOut);
					list.addAll(rList);
				}
			}
		}
		return list;
	}

	public Map getErrorMap() {
		return errorMap;
	}

	public Map getBigCodeToName() {
		return bigCodeToName;
	}

	public List getErrorPn() {
		return errorPn;
	}

	public void setErrorPn(List errorPn) {
		this.errorPn = errorPn;
	}

	public void setCombiId(String combiId) {
		this.combiId = combiId;
	}

	public void setEventLevel(Short eventLevel) {
		this.eventLevel = eventLevel;
	}

	public List<String> getTmnlAssetNos() {
		return tmnlAssetNos;
	}

	public void setTmnlAssetNos(List<String> tmnlAssetNos) {
		this.tmnlAssetNos = tmnlAssetNos;
	}

	public void setCmds(List<String> cmds) {
		this.cmds = cmds;
	}

	public Map getCombiMap() {
		return combiMap;
	}

	public Long getUsrCount() {
		return usrCount;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Map> getResultMap() {
		return resultMap;
	}

	public void setResultMap(List<Map> resultMap) {
		this.resultMap = resultMap;
	}

	public void setClearNo(String clearNo) {
		this.clearNo = clearNo;
	}

	public void setFormatStr(String formatStr) {
		this.formatStr = formatStr;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public Map<String, Map<String, String>> getMeterName() {
		return meterName;
	}

	public void setMeterName(Map<String, Map<String, String>> meterName) {
		this.meterName = meterName;
	}

	public void setTmnlNo(String tmnlNo) {
		this.tmnlNo = tmnlNo;
	}

	public void setPns(List<String> pns) {
		this.pns = pns;
	}

	public void setAllPns(boolean allPns) {
		this.allPns = allPns;
	}

	public void setFrmNo(String frmNo) {
		this.frmNo = frmNo;
	}

	public void setEdataFinder(EDataFinder edataFinder) {
		this.edataFinder = edataFinder;
	}

	public void setComboData(Map<String, List> comboData) {
		this.comboData = comboData;
	}

	public EDataFinder getEdataFinder() {
		return edataFinder;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setStartText(Integer startText) {
		this.startText = startText;
	}

	public void setEndText(Integer endText) {
		this.endText = endText;
	}

	public void setUserCodes(List<String> userCodes) {
		this.userCodes = userCodes;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
}
