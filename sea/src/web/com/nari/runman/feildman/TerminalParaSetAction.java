package com.nari.runman.feildman;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.basicdata.BCommProtItemListId;
import com.nari.basicdata.BCommProtocol;
import com.nari.basicdata.BCommProtocolItem;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.bg.TBgTask;
import com.nari.bg.TBgTaskDetail;
import com.nari.bg.TBgTaskDetailId;
import com.nari.bg.TBgTaskId;
import com.nari.bg.impl.BackGroundTaskManagerImpl;
import com.nari.coherence.TaskDeal;
import com.nari.elementsdata.EDataMp;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.AutoSendQuery;
import com.nari.support.Page;
import com.nari.terminalparam.CallValueBean;
import com.nari.terminalparam.MpMeterInfoBean;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.terminalparam.TTmnlMpParamId;
import com.nari.terminalparam.TTmnlParam;
import com.nari.terminalparam.TTmnlParamId;
import com.nari.util.StatusCodeUtils;

/**
 * 终端参数设定Action
 * 
 * @author longkc
 */
public class TerminalParaSetAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	// 要返回的值
	private List<VwDataTypePBean> dt;

	private List<VwProtocolCodeBean> pc;

	private List<BCommProtocol> bcp;

	private List<BCommProtocolItem> bcpi;

	private List<BCommProtItemListId> bpil;

	private List<TTmnlParam> ttp;

	private List<List<TTmnlParam>> ttpList;

	private List<List<FetchInfoBean>> fiBeanList;

	private List<FetchInfoBean> tmnlInfo;

	private Map<String, String> tmnlStatusMap;

	private List<TerminalInfoBean> tmnlList;

	private boolean success = true;

	private List<TBgTask> tBgTaskList = new ArrayList<TBgTask>();

	private List<TBgTaskDetail> tBgTaskDetailList = new ArrayList<TBgTaskDetail>();

	private Map<String, List<FetchInfoBean>> tmnlTermDataList;

	private Map<String, MpMeterInfoBean> tmnlTermMap;

	private long taskId;

	private int taskSecond;

	// 浏览器端请求的参数
	// 规约类型编码
	private String protocolCode;
	// 规约编码数据类型
	private String dataType;
	// 规约项数据编码
	private String proItemNo;
	// 规约项编码
	private String proNO;
	// 终端资产编号
	private String terminalNo;
	// 召测状态
	private String[] status;
	// FN号
	private short fn;
	// PN号
	private short[] pn;
	// flg
	private int mpflg;
	// 终端存在flg
	private int tmnlflg;
	// json格式数据
	private String jsonData;
	// 终端资产编号集合
	private String[] tmnlAssetNoArr;
	// 节点类型
	private String nodeType;
	// 节点值
	private String nodeValue;
	// 是否为数据库参数下发flg
	private boolean dataFlag;
	// 单个终端召测下发状态
	private String statusCode;
	//终端资产号
	private String tmnlAssetNo;
	//规约类型
	private String protNO;
	//测量点
	private String[] mpSn;
	//块序号
	private String[] blockSn;
	private long totalCount;
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	public List<CallValueBean> CallValueBeanList;

	// 动态注入服务层
	private TerminalParaSetManager terminalParaSetManager;

	private BackGroundTaskManagerImpl backGroundTaskManager;

	
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getProtNO() {
		return protNO;
	}

	public void setProtNO(String protNO) {
		this.protNO = protNO;
	}

	public String[] getMpSn() {
		return mpSn;
	}

	public void setMpSn(String[] mpSn) {
		this.mpSn = mpSn;
	}

	public String[] getBlockSn() {
		return blockSn;
	}

	public void setBlockSn(String[] blockSn) {
		this.blockSn = blockSn;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	public List<CallValueBean> getCallValueBeanList() {
		return CallValueBeanList;
	}

	public void setCallValueBeanList(List<CallValueBean> callValueBeanList) {
		CallValueBeanList = callValueBeanList;
	}
	public void setBackGroundTaskManager(
			BackGroundTaskManagerImpl backGroundTaskManager) {
		this.backGroundTaskManager = backGroundTaskManager;
	}

	public void setTerminalParaSetManager(
			TerminalParaSetManager terminalParaSetManager) {
		this.terminalParaSetManager = terminalParaSetManager;
	}

	/**
	 * 查询规约类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPortocolCode() throws Exception {
		this.logger.debug("规约类型查询开始");
		this.setPc(this.terminalParaSetManager.getProCodeList());
		this.logger.debug("规约类型查询结束");
		return SUCCESS;
	}

	/**
	 * 终端规约对应数据类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDataType() throws Exception {
		this.logger.debug("终端规约对应数据类型查询开始");
		this.setDt(this.terminalParaSetManager.getDataTypeList(
				this.protocolCode, this.proNO));
		this.logger.debug("终端规约对应数据类型查询结束");
		return SUCCESS;
	}

	/**
	 * 查询终端规约
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPortocol() throws Exception {
		this.logger.debug("终端规约查询开始");
		this.setBcp(this.terminalParaSetManager.getProtocolList(this.dataType,
				this.protocolCode));
		this.logger.debug("终端规约查询结束");
		return SUCCESS;
	}

	/**
	 * 查询终端规约明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPortocolItem() throws Exception {
		this.logger.debug("终端规约明细查询开始");
		this.setBcpi(this.terminalParaSetManager.getProtocolItemList(proNO));
		this.logger.debug("终端规约明细查询结束");
		return SUCCESS;
	}

	/**
	 * 查询规约项数据编码明细。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPorItemList() throws Exception {
		this.logger.debug("约项数据编码明细查询开始");
		this.setBpil(this.terminalParaSetManager
				.getProtlItemListId(this.proItemNo));
		this.logger.debug("约项数据编码明细查询结束");
		return SUCCESS;
	}

	/**
	 * 查询终端信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlList() throws Exception {
		this.logger.debug("终端查询开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		this.setTmnlList(this.terminalParaSetManager.getTermList(this.nodeType,
				this.nodeValue, userInfo, this.protocolCode));
		this.logger.debug("终端查询结束");
		return SUCCESS;
	}
	
	/**
	 * 查询终端信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlArrList() throws Exception {
		this.logger.debug("批量终端查询开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		this.setTmnlList(this.terminalParaSetManager.getTmnlArrList(this.tmnlAssetNoArr, userInfo, this.protocolCode));
		this.logger.debug("批量终端查询结束");
		return SUCCESS;
	}

	/**
	 * 查询终端详细信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlInfoList() throws Exception {
		this.logger.debug("终端详细信息查询开始");
		if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
			// 测量点数据
			this.setTmnlInfo(this.terminalParaSetManager.getTmnlMpList(
					this.proNO, this.terminalNo, this.pn));
		} else {
			// 终端数据
			this.setTmnlInfo(this.terminalParaSetManager.getTerminalList(
					this.proNO, this.terminalNo));
		}

		this.logger.debug("终端详细信息查询结束");
		return SUCCESS;
	}

	/**
	 * 查询终端用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlUserInfo() throws Exception {
		this.logger.debug("查询终端用户信息开始");
		this.setTmnlList(this.terminalParaSetManager
				.getTermInfo(this.terminalNo));
		this.logger.debug("查询终端用户信息结束");
		return SUCCESS;
	}

	/**
	 * 查询终端表计详细信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlTermInfoList() throws Exception {
		this.logger.debug("终端表计详细信息查询开始");
		// 终端表计信息
		List<MpMeterInfoBean> meterRs = this.terminalParaSetManager
				.getTermMeterInfo(this.terminalNo);

		// 构造测量点号和测量点信息对应的map
		Map<String, MpMeterInfoBean> meterMap = new HashMap<String, MpMeterInfoBean>();
		for (MpMeterInfoBean mmInfo : meterRs) {
			meterMap.put(String.valueOf(mmInfo.getMpSn()), mmInfo);
		}
		this.setTmnlTermMap(meterMap);
		// 终端表计数据
		this.setTmnlTermDataList(this.terminalParaSetManager.getTermMeterInfo(
				this.terminalNo, this.proNO));
		this.logger.debug("终端表计详细信息查询结束");
		return SUCCESS;
	}

	/**
	 * 保存召测信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveTerminalInfo() throws Exception {
		this.logger.debug("召测信息保存开始");

		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(FetchInfoBean.class);

		FetchInfoBean[] fetchBean = (FetchInfoBean[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);
		List<FetchInfoBean> fetchInfo = new ArrayList<FetchInfoBean>();

		for (int i = 0; i < fetchBean.length; i++) {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				String tmnlNo = fetchBean[i].getTmnlAssetNo();
				short mpSn = (short) Integer.parseInt(fetchBean[i].getMpSn());
				if (this.judgeMpExist(tmnlNo, mpSn) == false) {
					this.setMpflg(1);
				} else {
					fetchInfo.add(fetchBean[i]);
				}
			} else {
				fetchInfo.add(fetchBean[i]);
			}

		}

		this.saveFetchdata(fetchInfo);
		this.logger.debug("召测信息保存结束");
		return SUCCESS;
	}

	
	/**
	 * 保存callValue。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveCallValue() throws Exception {
		this.logger.debug("召测信息保存开始");

		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(FetchInfoBean.class);

		FetchInfoBean[] fetchBean = (FetchInfoBean[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);
		List<FetchInfoBean> fetchInfo = new ArrayList<FetchInfoBean>();

		for (int i = 0; i < fetchBean.length; i++) {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				String tmnlNo = fetchBean[i].getTmnlAssetNo();
				short mpSn = (short) Integer.parseInt(fetchBean[i].getMpSn());
				if (this.judgeMpExist(tmnlNo, mpSn) == false) {
					this.setMpflg(1);
				} else {
					fetchInfo.add(fetchBean[i]);
				}
			} else {
				fetchInfo.add(fetchBean[i]);
			}

		}

		this.saveFetchdataByCallValue(fetchInfo);
		this.logger.debug("召测信息保存结束");
		return SUCCESS;
	}
	
	/**保存callValue
	 * 下发后更新下发参数信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	private void saveFetchdataByCallValue(List<FetchInfoBean> fetchInfo) {
		try {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				this.terminalParaSetManager.saveOrUpdateFetchInfoBeanForMP(fetchInfo);
			} else {
				this.terminalParaSetManager.saveOrUpdateFetchInfoBeanForTP(fetchInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * 	查询明细
 * @return
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public String queryCallValueBean() throws Exception{
		try {	
			List blockSnList = new ArrayList();
			for(int i = 0;i<blockSn.length;i++){
				blockSnList.add(blockSn[i]);
			}
			List mpSnList = new ArrayList();
			for(int i = 0;i<mpSn.length;i++){
				mpSnList.add(mpSn[i]);
			}

			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				Page<CallValueBean> callBeanMpSnPage = this.terminalParaSetManager.getCallValueBeanMpSn(tmnlAssetNo, protNO, mpSnList, blockSnList, start, limit);
				CallValueBeanList = callBeanMpSnPage.getResult();
				totalCount = callBeanMpSnPage.getTotalCount();
			}else{
				// 晓程规约查询和设置的规约项不一样
				if(protNO.equals("C0A01")||protNO.equals("C0A0B")||protNO.equals("C0A19"))
				{
					protNO = protNO.replace("0A", "04");
				}
				Page<CallValueBean> callBeanPage = this.terminalParaSetManager.getCallValueBean(tmnlAssetNo, protNO, blockSnList, start, limit);
				CallValueBeanList = callBeanPage.getResult();
				totalCount = callBeanPage.getTotalCount();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	
	
	/**
	 * 保存页面参数。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String savePageInfo() {
		this.logger.debug("页面参数保存开始");
		// 是否选中下发终端判断
		if (this.tmnlAssetNoArr[0] == null || "".equals(this.tmnlAssetNoArr[0])) {
			this.setTmnlflg(1);
			return SUCCESS;
		}

		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);
		JsonConfig jsonConfig = new JsonConfig();

		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);

		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(FetchInfoBean.class);

		// 参数是否保存FLAG
		boolean saveFlag = true;
		// 下发参数
		for (int m = 0; m < this.pn.length; m++) {
			for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
				saveFlag = true;
				if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
					if (this.judgeMpExist(this.tmnlAssetNoArr[i], this.pn[m]) == false) {
						saveFlag = false;
					}
				}
				if (saveFlag == true) {
					List<String> tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(this.tmnlAssetNoArr[i]);

					List<FetchInfoBean> ttpara = new ArrayList<FetchInfoBean>();
					// 取出终端对应的参数
					FetchInfoBean[] bpItem = (FetchInfoBean[]) JSONSerializer
							.toJava(jsonArray.getJSONArray(i), jsonConfig);
					// 编码对象集合
					for (int j = 0; j < bpItem.length; j++) {
						// 将状态设置为下发参数中
						bpItem[j].setStatusCode(StatusCodeUtils.PARAM_SAVE_ONLY);
						bpItem[j].setStaffNo(userInfo.getStaffNo());
						ttpara.add(bpItem[j]);
					}
					// 保存参数
					this.savePara(ttpara, pn[m]);
				}
			}
		}

		this.setMpflg(0);
		this.logger.debug("页面参数保存结束");
		return SUCCESS;
	}

	/**
	 * 保存后台下发参数。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveTBgTaskInfo() {
		this.logger.debug("后台下发参数保存开始");
		// 是否选中下发终端判断
		if (this.tmnlAssetNoArr[0] == null || "".equals(this.tmnlAssetNoArr[0])) {
			this.setTmnlflg(1);
			return SUCCESS;
		}
		try {
			this.taskId = this.backGroundTaskManager.getTaskId();
			// 获取操作员信息
			PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
					"pSysUser");
			if (this.dataFlag) {
				// 下发参数
				for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
					StringBuffer pnsb = new StringBuffer();
					for (int m = 0; m < this.pn.length; m++) {
						if ("22".equals(this.dataType)
								|| "24".equals(this.dataType)) {
							if (this.judgeMpExist(this.tmnlAssetNoArr[i],
									this.pn[m]) == true) {
								pnsb = this.translatePn(pnsb, pn[m]);
							}
						}
					}
					this.saveTaskInfo(tmnlAssetNoArr[i], pnsb.toString());
				}
			} else {
				JSONArray jsonArray = JSONArray.fromObject(this.jsonData);
				JsonConfig jsonConfig = new JsonConfig();

				// json为数组的解析模式
				jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);

				// 指定Json数据对应的javaBean对象class名
				jsonConfig.setRootClass(FetchInfoBean.class);

				// 参数是否保存FLAG
				boolean saveFlag = true;
				// 下发参数
				for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
					StringBuffer pnsb = new StringBuffer();
					for (int m = 0; m < this.pn.length; m++) {
						saveFlag = true;
						if ("22".equals(this.dataType)
								|| "24".equals(this.dataType)) {
							if (this.judgeMpExist(this.tmnlAssetNoArr[i],
									this.pn[m]) == false) {
								saveFlag = false;
							} else {
								pnsb = this.translatePn(pnsb, pn[m]);
							}
						}
						if (saveFlag == true) {
							List<FetchInfoBean> ttpara = new ArrayList<FetchInfoBean>();
							// 取出终端对应的参数
							FetchInfoBean[] bpItem = (FetchInfoBean[]) JSONSerializer
									.toJava(jsonArray.getJSONArray(i),
											jsonConfig);
							// 编码对象集合
							for (int j = 0; j < bpItem.length; j++) {
								// 将状态设置为下发参数中
								bpItem[j].setStatusCode(StatusCodeUtils.PARAM_SAVE_ONLY);
								bpItem[j].setStaffNo(userInfo.getStaffNo());
								ttpara.add(bpItem[j]);
							}
							// 保存参数
							this.savePara(ttpara, pn[m]);
						}
					}
					this.saveTaskInfo(tmnlAssetNoArr[i], pnsb.toString());
				}
			}
			this.backGroundTaskManager.saveTBgTaskInfo(this.tBgTaskList);
			this.backGroundTaskManager
					.saveTBgTaskDetail(this.tBgTaskDetailList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setMpflg(0);
		this.logger.debug("后台下发参数保存结束");
		return SUCCESS;
	}

	/**
	 * 页面参数下发。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendPagePara() {
		this.logger.debug("参数下发开始");
//		if (!this.isConnet()) {
//			return SUCCESS;
//		}
		// 是否选中下发终端判断
		if (this.tmnlAssetNoArr[0] == null || "".equals(this.tmnlAssetNoArr[0])) {
			this.setTmnlflg(1);
			return SUCCESS;
		}

		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 获取操作员IP地址
		String ipAddr = getRequest().getRemoteAddr();

		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);
		JsonConfig jsonConfig = new JsonConfig();

		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);

		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(FetchInfoBean.class);

		String[] statusArr = new String[this.tmnlAssetNoArr.length];
		String[] tmnlAssetNoRet = new String[this.tmnlAssetNoArr.length];

		// 终端状态
		Map<String, String> tmnlStatusMap = new HashMap<String, String>();

		List<String> keyList = null;
		Map<List<String>, List<FetchInfoBean>> tmnlInfo = new HashMap<List<String>, List<FetchInfoBean>>();

		TaskDeal taskDeal = new TaskDeal();
		// 下发FLAG
		boolean sendFlag = true;
		// 下发参数
		for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
			// 参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(this.tmnlAssetNoArr[i]);
			for (int m = 0; m < this.pn.length; m++) {
				sendFlag = true;
				if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
					if (this.judgeMpExist(this.tmnlAssetNoArr[i], this.pn[m]) == false) {
						tmnlStatusMap.put(this.tmnlAssetNoArr[i],
								StatusCodeUtils.TMNL_MP_FLAG);
						this.setMpflg(2);
						sendFlag = false;
					}
				}
				if (sendFlag == true) {
					List<FetchInfoBean> ttpara = new ArrayList<FetchInfoBean>();
					// 取出终端对应的参数
					FetchInfoBean[] bpItem = (FetchInfoBean[]) JSONSerializer
							.toJava(jsonArray.getJSONArray(i), jsonConfig);
					List<FetchInfoBean> bpList = new ArrayList<FetchInfoBean>();
					ParamItem pitem = new ParamItem();
					pitem.setFn((short) fn);// 设置FN号
					pitem.setPoint((short) pn[m]); // 设置PN号
					// 编码对象集合
					List<Item> items = new ArrayList<Item>();
					for (int j = 0; j < bpItem.length; j++) {
						bpList.add(bpItem[j]);
						Item item = new Item(bpItem[j].getProtItemNo());
						item.setValue(bpItem[j].getCurrentValue().trim());
						items.add(item);
						// 将状态设置为下发参数中
						bpItem[j].setStatusCode(StatusCodeUtils.PARAM_SEND_DOING);
						bpItem[j].setStaffNo(userInfo.getStaffNo());
						ttpara.add(bpItem[j]);
						keyList = new ArrayList<String>();
						keyList.add(this.tmnlAssetNoArr[i]);
						keyList.add(String.valueOf(this.pn[m]));
						tmnlInfo.put(keyList, ttpara);
					}

					pitem.setItems((ArrayList<Item>) items);
					paramList.add(pitem);
					this.savePara(ttpara, this.pn[m]);
				}
			}
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.SET_PARAMETERS, paramList);
		}

		List<Response> rs = taskDeal.getAllResponse(this.taskSecond);
		for (int i = 0; i < rs.size(); i++) {
			Response response = rs.get(i);
			// 获取下发状态code
			String sendStatus = String.valueOf(response.getTaskStatus());
			List<ResponseItem> rsItem = response.getResponseItems();
			String tmnlAsserNo = response.getTmnlAssetNo();
			for (int j = 0; j < rsItem.size(); j++) {
				short pn = response.getResponseItems().get(j).getPn();
				// 获取数据库数据查询
				List<FetchInfoBean> retRs = this.getTerminalInfo(tmnlAsserNo,
						pn);
				keyList = new ArrayList<String>();
				keyList.add(tmnlAsserNo);
				//针对盘龙晓程规约作的改动
				if(proNO.equals("C0401")){
					pn = (short) 0;
				}
				keyList.add(String.valueOf(pn));
				List<FetchInfoBean> ttpara = tmnlInfo.get(keyList);
				// 参数下发成功将终端下发状体置为成功，否则置为失败
				if ("3".equals(sendStatus)) {
					for (int k = 0; k < ttpara.size(); k++) {
						ttpara.get(k).setHistoryValue(
								retRs.get(k).getCurrentValue());
						ttpara.get(k).setStatusCode(
								StatusCodeUtils.PARAM_SEND_SUCCESS);
						LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
						lOpTmnlLog.setEmpNo(userInfo.getStaffNo());
						lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
						lOpTmnlLog.setIpAddr(ipAddr);
						lOpTmnlLog.setOpModule("参数设置");
						lOpTmnlLog.setOpButton("下发");
						lOpTmnlLog.setTmnlAssetNo(tmnlAsserNo);
						lOpTmnlLog.setProtItemNo(ttpara.get(k).getProtItemNo());
						lOpTmnlLog.setCurValue(ttpara.get(k).getCurrentValue());
						lOpTmnlLog.setOpTime(new Date());
						this.addOpTmnlLog(lOpTmnlLog);
					}
					tmnlStatusMap.put(tmnlAsserNo,
							StatusCodeUtils.PARAM_SEND_SUCCESS);
					this.savePara(ttpara, pn);
				} else if ("4".equals(sendStatus)) {
					// 下发失败
					String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
							.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.PARAM_SEND_FAILE
							: tmnlStatusMap.get(tmnlAsserNo);
					tmnlStatusMap.put(tmnlAsserNo, status);
				} else if ("1".equals(sendStatus)) {
					// 终端不在线
					String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
							.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.TMNL_OFF_LINE
							: tmnlStatusMap.get(tmnlAsserNo);
					tmnlStatusMap.put(tmnlAsserNo, status);
				} else if ("0".equals(sendStatus)) {
					// 在规定的时间内缓存没有返回
					String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
							.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.COR_UNBACK
							: tmnlStatusMap.get(tmnlAsserNo);
					tmnlStatusMap.put(tmnlAsserNo, status);
				} else {
					String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
							.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.TMNL_NO_REPLAY
							: tmnlStatusMap.get(tmnlAsserNo);
					tmnlStatusMap.put(tmnlAsserNo, status);
				}
				//下发不成功时将状态更新为失败
				if (!"3".equals(sendStatus)) {
					for (int k = 0; k < ttpara.size(); k++) {
						ttpara.get(k).setHistoryValue(
								retRs.get(k).getCurrentValue());
						ttpara.get(k).setStatusCode(
								StatusCodeUtils.PARAM_SEND_FAILE);
					}
					this.savePara(ttpara, pn);
				}
			}
		}
		
		Set<String> tmnlSet = tmnlStatusMap.keySet();
		if (tmnlSet != null) {
			Iterator<String> tmnlIt = tmnlSet.iterator();
			int i = 0;
			while (tmnlIt.hasNext()) {
				String key = (String) tmnlIt.next();
				String value = tmnlStatusMap.get(key);
				statusArr[i] = value;
				tmnlAssetNoRet[i] = key;
				i++;
			}
		}

		this.setStatus(statusArr);
		this.setMpflg(0);
		this.setTmnlAssetNoArr(tmnlAssetNoRet);
		this.logger.debug("参数下发结束");
		return SUCCESS;
	}

	/**
	 * 终端表计参数下发
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendMeterPara() throws Exception {
		this.logger.debug("表计参数下发开始");
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 获取操作员IP地址
		String ipAddr = getRequest().getRemoteAddr();

		// 下发终端信息
		List<String> tmnlAssetNoList = new ArrayList<String>();
		tmnlAssetNoList.add(this.terminalNo);

		// 下发通信接口
		TaskDeal taskDeal = new TaskDeal();

		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);
		JsonConfig jsonConfig = new JsonConfig();

		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);

		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(FetchInfoBean.class);

		// 取出终端对应的参数
		FetchInfoBean[] bpItem = (FetchInfoBean[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);

		// 获取数据库数据
//		List<FetchInfoBean> retRs = this.terminalParaSetManager
//				.getTerminalList(this.proNO, this.terminalNo);
		// 构造下发参数
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		// 构造保存参数
		List<FetchInfoBean> ttpara = new ArrayList<FetchInfoBean>();
		ParamItem pitem = new ParamItem();
		pitem.setFn((short) fn);// 设置FN号
		pitem.setPoint((short) pn[0]); // 设置PN号
		// 编码对象集合
		List<Item> items = new ArrayList<Item>();
		for (int j = 0; j < bpItem.length; j++) {
			Item item = new Item(bpItem[j].getProtItemNo());
			item.setValue(bpItem[j].getCurrentValue().trim());
			items.add(item);
			// 将状态设置为下发参数中
			bpItem[j].setStatusCode(StatusCodeUtils.PARAM_SEND_DOING);
			bpItem[j].setStaffNo(userInfo.getStaffNo());
			ttpara.add(bpItem[j]);
			//每17个参数下发一次
			if (j > 0 && j % 17 == 0) {
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
				// 下发参数
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.SET_PARAMETERS, paramList);
				items = new ArrayList<Item>();
				paramList = new ArrayList<ParamItem>();
			}
		}
		//下发（总数/17）余数个数的参数
		pitem.setItems((ArrayList<Item>) items);
		paramList.add(pitem);
		// 下发参数
		taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
				FrontConstant.SET_PARAMETERS, paramList);
		// 更新参数
		this.terminalParaSetManager.updateMertInfo(ttpara);

		// 取得下发返回结果
		List<Response> rs = taskDeal.getAllResponse(this.taskSecond);
		for (int i = 0; i < rs.size(); i++) {
			Response response = rs.get(i);
			// 获取下发状态code
			String sendStatus = String.valueOf(response.getTaskStatus());
			List<ResponseItem> rsItem = response.getResponseItems();
			String tmnlAsserNo = response.getTmnlAssetNo();
			for (int j = 0; j < rsItem.size(); j++) {
				// 参数下发成功将终端下发状体置为成功，否则置为失败
				if ("3".equals(sendStatus)) {
					for (int k = 0; k < ttpara.size(); k++) {
						ttpara.get(k).setStatusCode(
								StatusCodeUtils.PARAM_SEND_SUCCESS);
						LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
						lOpTmnlLog.setEmpNo(userInfo.getStaffNo());
						lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
						lOpTmnlLog.setIpAddr(ipAddr);
						lOpTmnlLog.setOpModule("终端表计参数设置");
						lOpTmnlLog.setOpButton("下发");
						lOpTmnlLog.setTmnlAssetNo(tmnlAsserNo);
						lOpTmnlLog.setProtItemNo(ttpara.get(k).getProtItemNo());
						lOpTmnlLog.setCurValue(ttpara.get(k).getCurrentValue());
						lOpTmnlLog.setOpTime(new Date());
						this.addOpTmnlLog(lOpTmnlLog);
					}
					this.statusCode = StatusCodeUtils.PARAM_SEND_SUCCESS;
					// 更新下发状态
					this.terminalParaSetManager.updateMertInfo(ttpara);
				} else if ("4".equals(sendStatus)) {
					// 下发失败
					this.statusCode = StatusCodeUtils.PARAM_SEND_FAILE;
				} else if ("1".equals(sendStatus)) {
					// 终端不在线
					this.statusCode = StatusCodeUtils.TMNL_OFF_LINE;
				} else if ("0".equals(sendStatus)) {
					// 在规定的时间内缓存没有返回
					this.statusCode = StatusCodeUtils.COR_UNBACK;
				} else {
					this.statusCode = StatusCodeUtils.TMNL_NO_REPLAY;
				}
				//下发不成功时将状态更新为失败
				if (!"3".equals(sendStatus)) {
					for (int k = 0; k < ttpara.size(); k++) {
						ttpara.get(k).setStatusCode(StatusCodeUtils.PARAM_SEND_FAILE);
					}
					this.terminalParaSetManager.updateMertInfo(ttpara);
				}
			}
		}

		this.logger.debug("表计参数下发结束");
		return SUCCESS;
	}

	/**
	 * 参数下发。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sendDBPara() throws Exception {
		this.logger.debug("参数下发开始");
		if (!this.isConnet()) {
			return SUCCESS;
		}
		// 是否选中下发终端判断
		if (this.tmnlAssetNoArr[0] == null || "".equals(this.tmnlAssetNoArr[0])) {
			this.setTmnlflg(1);
			return SUCCESS;
		}
		// 获取操作员信息
		PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		// 获取操作员IP地址
		String ipAddr = getRequest().getRemoteAddr();
		// 终端状态
		Map<String, String> tmnlStatusMap = new HashMap<String, String>();

		List<String> keyList = null;
		Map<List<String>, List<FetchInfoBean>> tmnlInfo = new HashMap<List<String>, List<FetchInfoBean>>();

		TaskDeal taskDeal = new TaskDeal();
		boolean sendFlag = false;
		// 下发参数
		for (int j = 0; j < this.tmnlAssetNoArr.length; j++) {
			// 参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			// 组装下发终端
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(this.tmnlAssetNoArr[j]);
			for (int i = 0; i < this.pn.length; i++) {
				short[] pn = { this.pn[i] };
				// 获取数据库数据
				if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
					// 测量点数据
					this.setTmnlInfo(this.terminalParaSetManager.getTmnlMpList(
							this.proNO, this.tmnlAssetNoArr[j], pn));
				} else {
					// 终端数据
					this
							.setTmnlInfo(this.terminalParaSetManager
									.getTerminalList(this.proNO,
											this.tmnlAssetNoArr[j]));
				}
				// 终端下发状态不存在时将其下发状态置为数据
				if (tmnlStatusMap.get(this.tmnlAssetNoArr[j]) == null) {
					tmnlStatusMap.put(this.tmnlAssetNoArr[j],
							StatusCodeUtils.TMNL_DATA_FLAG);
				}

				if (this.tmnlInfo.size() == 0) {
					String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
							.equals(tmnlStatusMap.get(this.tmnlAssetNoArr[j])) ? StatusCodeUtils.TMNL_DATA_FLAG
							: tmnlStatusMap.get(this.tmnlAssetNoArr[j]);
					tmnlStatusMap.put(this.tmnlAssetNoArr[j], status);
					sendFlag = false;
					continue;
				}

				// 组装更新数据库使用的list
				List<FetchInfoBean> ttpara = new ArrayList<FetchInfoBean>();
				ParamItem pitem = new ParamItem();
				pitem.setFn((short) fn);// 设置FN号
				pitem.setPoint((short) this.pn[i]); // 设置PN号
				List<Item> items = new ArrayList<Item>();
				sendFlag = true;
				for (int m = 0; m < this.getTmnlInfo().size(); m++) {
					// 数据库中数据的下发状态如果为 ”下发成功状态“数据不在下发
					if (StatusCodeUtils.PARAM_SEND_SUCCESS.equals(this
							.getTmnlInfo().get(m).getStatusCode())) {
						tmnlStatusMap.put(this.tmnlAssetNoArr[j],
								StatusCodeUtils.PARAM_SEND_SUCCESS);
						sendFlag = false;
						break;
					} else {
						Item item = new Item(this.getTmnlInfo().get(m)
								.getProtItemNo());
						item.setValue(this.getTmnlInfo().get(m)
								.getCurrentValue().trim());
						items.add(item);
						// 将状态设置为下发参数中
						this.getTmnlInfo().get(m).setStatusCode(
								StatusCodeUtils.PARAM_SEND_DOING);
						this.getTmnlInfo().get(m).setSaveTime(new Date());
						this.getTmnlInfo().get(m).setStaffNo(
								userInfo.getStaffNo());
						ttpara.add(this.getTmnlInfo().get(m));
						keyList = new ArrayList<String>();
						keyList.add(this.tmnlAssetNoArr[j]);
						keyList.add(String.valueOf(this.pn[i]));
						tmnlInfo.put(keyList, ttpara);
					}

				}
				if (sendFlag) {
					pitem.setItems((ArrayList<Item>) items);
					paramList.add(pitem);
					this.savePara(ttpara, this.pn[i]);
				}
			}
			if (sendFlag) {
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.SET_PARAMETERS, paramList);
			}
		}

		if (sendFlag) {
			List<Response> rs = taskDeal.getAllResponse(this.taskSecond);
			for (int i = 0; i < rs.size(); i++) {
				Response response = rs.get(i);
				// 获取下发状态code
				String sendStatus = String.valueOf(response.getTaskStatus());
				// short pn = 0;
				List<ResponseItem> rsItem = response.getResponseItems();
				for (int j = 0; j < rsItem.size(); j++) {
					short pn = response.getResponseItems().get(j).getPn();
					String tmnlAsserNo = response.getTmnlAssetNo();
					// 获取数据库数据查询
					List<FetchInfoBean> retRs = this.getTerminalInfo(
							tmnlAsserNo, pn);
					keyList = new ArrayList<String>();
					keyList.add(tmnlAsserNo);
					keyList.add(String.valueOf(pn));
					List<FetchInfoBean> ttpara = tmnlInfo.get(keyList);
					// 参数下发成功将终端下发状体置为成功，否则置为失败
					if ("3".equals(sendStatus)) {

						for (int k = 0; k < ttpara.size(); k++) {
							ttpara.get(k).setHistoryValue(
									retRs.get(k).getCurrentValue());
							ttpara.get(k).setStatusCode(
									StatusCodeUtils.PARAM_SEND_SUCCESS);
							LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
							lOpTmnlLog.setEmpNo(userInfo.getStaffNo());
							lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
							lOpTmnlLog.setIpAddr(ipAddr);
							lOpTmnlLog.setOpModule("参数设置");
							lOpTmnlLog.setOpButton("下发");
							lOpTmnlLog.setTmnlAssetNo(tmnlAsserNo);
							lOpTmnlLog.setProtItemNo(ttpara.get(k)
									.getProtItemNo());
							lOpTmnlLog.setCurValue(ttpara.get(k)
									.getCurrentValue());
							lOpTmnlLog.setOpTime(new Date());
							this.addOpTmnlLog(lOpTmnlLog);
						}
						tmnlStatusMap.put(tmnlAsserNo,
								StatusCodeUtils.PARAM_SEND_SUCCESS);
						this.savePara(ttpara, pn);
					} else if ("4".equals(sendStatus)) {
						// 下发失败
						String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
								.equals(tmnlStatusMap.get(tmnlAsserNo)) ? response
								.getErrorCodeStr()
								: tmnlStatusMap.get(tmnlAsserNo);
						tmnlStatusMap.put(tmnlAsserNo, status);
					} else if ("1".equals(sendStatus)) {
						// 终端不在线
						String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
								.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.TMNL_OFF_LINE
								: tmnlStatusMap.get(tmnlAsserNo);
						tmnlStatusMap.put(tmnlAsserNo, status);
					} else if ("0".equals(sendStatus)) {
						// 在规定的时间内缓存没有返回
						String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
								.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.COR_UNBACK
								: tmnlStatusMap.get(tmnlAsserNo);
						tmnlStatusMap.put(tmnlAsserNo, status);
					} else {
						String status = !StatusCodeUtils.PARAM_SEND_SUCCESS
								.equals(tmnlStatusMap.get(tmnlAsserNo)) ? StatusCodeUtils.TMNL_NO_REPLAY
								: tmnlStatusMap.get(tmnlAsserNo);
						tmnlStatusMap.put(tmnlAsserNo, status);
					}
					//下发不成功时将状态更新为失败
					if (!"3".equals(sendStatus)) {
						for (int k = 0; k < ttpara.size(); k++) {
							ttpara.get(k).setHistoryValue(
									retRs.get(k).getCurrentValue());
							ttpara.get(k).setStatusCode(
									StatusCodeUtils.PARAM_SEND_FAILE);
						}
						this.savePara(ttpara, pn);
					}
				}
			}
		}

		// 设置返回终端对应的下发状态
		String[] statusArr = new String[this.tmnlAssetNoArr.length];
		String[] tmnlAssetNoRet = new String[this.tmnlAssetNoArr.length];
		Set<String> tmnlSet = tmnlStatusMap.keySet();
		if (tmnlSet != null) {
			Iterator<String> tmnlIt = tmnlSet.iterator();
			int i = 0;
			while (tmnlIt.hasNext()) {
				String key = (String) tmnlIt.next();
				String value = tmnlStatusMap.get(key);
				statusArr[i] = value;
				tmnlAssetNoRet[i] = key;
				i++;
			}
		}

		this.setStatus(statusArr);
		this.setMpflg(0);
		this.setTmnlAssetNoArr(tmnlAssetNoRet);
		this.logger.debug("参数下发结束");
		return SUCCESS;
	}

	/**
	 * 参数召测。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fetchPara() {
		this.logger.debug("fetchPara start");
		if (!this.isConnet()) {
			return SUCCESS;
		}
		// 是否选中召测终端判断
		if (this.tmnlAssetNoArr[0] == null || "".equals(this.tmnlAssetNoArr[0])) {
			this.setTmnlflg(1);
			return SUCCESS;
		}
		// 终端资产号集合
		List<String> tmnlAssetNoList = new ArrayList<String>();
		for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
			tmnlAssetNoList.add(this.tmnlAssetNoArr[i]);
		}

		TaskDeal taskDeal = new TaskDeal();
		// 参数项设置
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		for (int m = 0; m < this.pn.length; m++) {
			ParamItem pitem = new ParamItem();
			pitem.setFn(this.fn);// 设置FN号

			pitem.setPoint((short) pn[m]); // 设置PN号

			// 编码对象集合
			List<Item> items = new ArrayList<Item>();
			if (this.jsonData != null && !"[]".equals(this.jsonData)) {
				JSONArray jsonArray = JSONArray.fromObject(this.jsonData);

				JsonConfig jsonConfig = new JsonConfig();
				// json为数组的解析模式
				jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
				// 指定Json数据对应的javaBean对象class名
				jsonConfig.setRootClass(BCommProtocolItem.class);

				BCommProtocolItem[] bpItem = (BCommProtocolItem[]) JSONSerializer
						.toJava(jsonArray, jsonConfig);
				List<BCommProtocolItem> bpList = new ArrayList<BCommProtocolItem>();
				for (int i = 0; i < bpItem.length; i++) {
					bpList.add(bpItem[i]);
				}
				for (int i = 0; i < bpList.size(); i++) {
					Item item = new Item(bpList.get(i).getProtItemNo());
					item.setValue(bpList.get(i).getDefaultVaule().trim());
					items.add(item);
				}
			}

			pitem.setItems((ArrayList<Item>) items);
			paramList.add(pitem);
		}
		List<List<FetchInfoBean>> rs = new ArrayList<List<FetchInfoBean>>();
		List<Response> ttr = null;
		try {
			// 召测
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.QUERY_PARAMS, paramList);
			ttr = taskDeal.getAllResponse(this.taskSecond);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] statusArr = new String[ttr.size()];
		String[] addArr = new String[ttr.size()];
		List<FetchInfoBean> resList = new ArrayList<FetchInfoBean>();
		for (int i = 0; i < ttr.size(); i++) {

			statusArr[i] = String.valueOf(ttr.get(i).getTaskStatus());
			addArr[i] = ttr.get(i).getTmnlAssetNo();
			if ("3".equals(statusArr[i])) {
				resList = new ArrayList<FetchInfoBean>();
				statusArr[i] = StatusCodeUtils.PARAM_FETCH_SUCCESS;

				// 获取终端召测状态
				List<ResponseItem> ri = ttr.get(i).getResponseItems();
				String blockSN ="";
				boolean needSkip = false;
				for (int j = 0; j < ri.size(); j++) {
					//RandySuh@xining 2010.09.15 F10 
					String item = ri.get(j).getCode();
					if(item.substring(1, 5).equals("040A")){
						if(item.endsWith("002")){
							blockSN = ri.get(j).getValue();
							needSkip = true;
						}
					}else{
						//blockSN = ri.get(j).getBlockSN();
						needSkip = false;
					}
					if(!needSkip)
						blockSN = ri.get(j).getBlockSN();
					FetchInfoBean tp = new FetchInfoBean();
					// 数据值
					tp.setCurrentValue(ri.get(j).getValue());
					// 规约项编码
					tp.setProtItemNo(item);
					// 终端资产号
					tp.setTmnlAssetNo(ttr.get(i).getTmnlAssetNo());
					// 块序号
					//tp.setBlockSn(ri.get(j).getBlockSN());
					tp.setBlockSn(blockSN);
					// 块内序号
					tp
							.setInnerBlockSn(Integer.valueOf(ri.get(j)
									.getBlockDef()));
					// pn
					tp.setMpSn(String.valueOf(ri.get(j).getPn()));
					// 召测状态
					tp.setStatusCode("04");
					resList.add(tp);
				}
				rs.add(resList);
			} else if ("4".equals(statusArr[i])) {
				resList = new ArrayList<FetchInfoBean>();
				statusArr[i] = StatusCodeUtils.PARAM_FETCH_FAILE;
				rs.add(resList);
			} else if ("1".equals(statusArr[i])) {
				resList = new ArrayList<FetchInfoBean>();
				statusArr[i] = StatusCodeUtils.TMNL_OFF_LINE;
				rs.add(resList);
			} else if ("0".equals(statusArr[i])) {
				resList = new ArrayList<FetchInfoBean>();
				statusArr[i] = StatusCodeUtils.COR_UNBACK;
				rs.add(resList);
			}
		}
		if (ttr.size() == 0) {
			statusArr = new String[this.tmnlAssetNoArr.length];
			addArr = new String[this.tmnlAssetNoArr.length];
			for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
				statusArr[i] = StatusCodeUtils.TMNL_NO_REPLAY;
				addArr[i] = this.tmnlAssetNoArr[i];
				rs.add(resList);
			}
		}
		this.setStatus(statusArr);
		this.setTmnlAssetNoArr(addArr);
		this.setMpflg(0);
		this.setFiBeanList(rs);
		this.logger.debug("fetchPara end");
		return SUCCESS;
	}

	/**
	 * 判断前置机和缓存通信状态
	 * 
	 * @return true:正常,false：中断
	 */
	private boolean isConnet() {
		boolean retCode = true;
		// Web应用服务器与缓存服务器通信状态
		if (!TaskDeal.isCacheRunning()) {
			this.setMpflg(4);
			retCode = false;
		}
		// 前置集群服务通信状态
		if (!TaskDeal.isFrontAlive()) {
			this.setMpflg(5);
			retCode = false;
		}
		return retCode;
	}

	/**
	 * 下发前保存下发参数信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	private void savePara(List<FetchInfoBean> ttmInfo, short pn) {
		try {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				List<TTmnlMpParam> tmInfoList = new ArrayList<TTmnlMpParam>();
				for (FetchInfoBean tt : ttmInfo) {
					TTmnlMpParamId ttmp = new TTmnlMpParamId();
					TTmnlMpParam tmInfo = new TTmnlMpParam();
					List<EDataMp> rs = this.terminalParaSetManager
							.getMpInfoList(tt.getTmnlAssetNo(), pn);
					ttmp.setBlockSn(tt.getBlockSn());
					ttmp.setCurrentValue(tt.getCurrentValue());
					ttmp.setDataId(rs.get(0).getDataId());
					ttmp.setFailureCode(tt.getFailureCode());
					ttmp.setHistoryValue(tt.getHistoryValue());
					ttmp.setInnerBlockSn(tt.getInnerBlockSn());
					ttmp.setLastSendTime(tt.getLastSendTime());
					ttmp.setNextSendTime(tt.getLastSendTime());
					ttmp.setProtItemNo(tt.getProtItemNo());
					ttmp.setResendCount(tt.getResendCount());
					ttmp.setSaveTime(tt.getSaveTime());
					ttmp.setSendTime(tt.getSendTime());
					ttmp.setStaffNo(tt.getStaffNo());
					ttmp.setStatusCode(tt.getStatusCode());
					ttmp.setSuccessTime(tt.getSuccessTime());
					tmInfo.setId(ttmp);
					tmInfoList.add(tmInfo);
				}
				this.terminalParaSetManager.saveMpInfo(tmInfoList);
			} else {
				List<TTmnlParam> tmInfoList = new ArrayList<TTmnlParam>();
				for (FetchInfoBean tt : ttmInfo) {
					TTmnlParam ttmp = new TTmnlParam();
					TTmnlParamId ttmpId = new TTmnlParamId();
					ttmpId.setBlockSn(tt.getBlockSn());
					ttmpId.setTmnlAssetNo(tt.getTmnlAssetNo());
					ttmpId.setInnerBlockSn(tt.getInnerBlockSn());
					ttmpId.setProtItemNo(tt.getProtItemNo());
					ttmp.setId(ttmpId);
					ttmp.setCurrentValue(tt.getCurrentValue());
					ttmp.setFailureCode(tt.getFailureCode());
					ttmp.setHistoryValue(tt.getHistoryValue());
					ttmp.setLastSendTime(tt.getLastSendTime());
					ttmp.setNextSendTime(tt.getNextSendTime());
					ttmp.setResendCount(tt.getResendCount());
					ttmp.setSaveTime(tt.getSaveTime());
					ttmp.setSendTime(tt.getSendTime());
					ttmp.setStaffNo(tt.getStaffNo());
					ttmp.setStatusCode(tt.getStatusCode());
					ttmp.setSuccessTime(tt.getSuccessTime());
					tmInfoList.add(ttmp);
				}
				this.terminalParaSetManager.saveTerminal(tmInfoList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下发后更新下发参数信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	// private void updatePara(List<FetchInfoBean> ttmInfo, short pn) {
	// try {
	// // 22表示测量点参数，24表示直流模拟量参数
	// if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
	// List<TTmnlMpParam> tmInfoList = new ArrayList<TTmnlMpParam>();
	// for (FetchInfoBean tt : ttmInfo) {
	// TTmnlMpParamId ttmp = new TTmnlMpParamId();
	// TTmnlMpParam tmInfo = new TTmnlMpParam();
	// List<EDataMp> rs = this.terminalParaSetManager
	// .getMpInfoList(tt.getTmnlAssetNo(), pn);
	// ttmp.setBlockSn(tt.getBlockSn());
	// ttmp.setCurrentValue(tt.getCurrentValue());
	// ttmp.setDataId(rs.get(0).getDataId());
	// ttmp.setFailureCode(tt.getFailureCode());
	// ttmp.setHistoryValue(tt.getHistoryValue());
	// ttmp.setInnerBlockSn(tt.getInnerBlockSn());
	// ttmp.setLastSendTime(tt.getLastSendTime());
	// ttmp.setNextSendTime(tt.getLastSendTime());
	// ttmp.setProtItemNo(tt.getProtItemNo());
	// ttmp.setResendCount(tt.getResendCount());
	// ttmp.setSaveTime(tt.getSaveTime());
	// ttmp.setSendTime(tt.getSendTime());
	// ttmp.setStaffNo(tt.getStaffNo());
	// ttmp.setStatusCode(tt.getStatusCode());
	// ttmp.setSuccessTime(tt.getSuccessTime());
	// tmInfo.setId(ttmp);
	// tmInfoList.add(tmInfo);
	// }
	// this.terminalParaSetManager.updateMpInfo(tmInfoList);
	// } else {
	// List<TTmnlParam> tmInfoList = new ArrayList<TTmnlParam>();
	// for (FetchInfoBean tt : ttmInfo) {
	// TTmnlParam ttmp = new TTmnlParam();
	// TTmnlParamId ttmpId = new TTmnlParamId();
	// ttmpId.setBlockSn(tt.getBlockSn());
	// ttmpId.setTmnlAssetNo(tt.getTmnlAssetNo());
	// ttmpId.setInnerBlockSn(tt.getInnerBlockSn());
	// ttmpId.setProtItemNo(tt.getProtItemNo());
	// ttmp.setId(ttmpId);
	// ttmp.setCurrentValue(tt.getCurrentValue());
	// ttmp.setFailureCode(tt.getFailureCode());
	// ttmp.setHistoryValue(tt.getHistoryValue());
	// ttmp.setLastSendTime(tt.getLastSendTime());
	// ttmp.setNextSendTime(tt.getNextSendTime());
	// ttmp.setResendCount(tt.getResendCount());
	// ttmp.setSaveTime(tt.getSaveTime());
	// ttmp.setSendTime(tt.getSendTime());
	// ttmp.setStaffNo(tt.getStaffNo());
	// ttmp.setStatusCode(tt.getStatusCode());
	// ttmp.setSuccessTime(tt.getSuccessTime());
	// tmInfoList.add(ttmp);
	// }
	// this.terminalParaSetManager.updateTerminal(tmInfoList);
	// }
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 下发后更新下发参数信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	private void saveFetchdata(List<FetchInfoBean> fetchInfo) {
		try {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				List<TTmnlMpParam> tmInfoList = new ArrayList<TTmnlMpParam>();
				for (FetchInfoBean tt : fetchInfo) {
					TTmnlMpParamId ttmp = new TTmnlMpParamId();
					TTmnlMpParam tmInfo = new TTmnlMpParam();
					List<EDataMp> rs = this.terminalParaSetManager
							.getMpInfoList(tt.getTmnlAssetNo(), (short) Integer
									.parseInt(tt.getMpSn()));
					ttmp.setBlockSn(tt.getBlockSn());
					ttmp.setCurrentValue(tt.getCurrentValue());
					ttmp.setDataId(rs.get(0).getDataId());
					ttmp.setFailureCode(tt.getFailureCode());
					ttmp.setHistoryValue(tt.getHistoryValue());
					ttmp.setInnerBlockSn(tt.getInnerBlockSn());
					ttmp.setLastSendTime(tt.getLastSendTime());
					ttmp.setNextSendTime(tt.getLastSendTime());
					ttmp.setProtItemNo(tt.getProtItemNo());
					ttmp.setResendCount(tt.getResendCount());
					ttmp.setSaveTime(tt.getSaveTime());
					ttmp.setSendTime(tt.getSendTime());
					ttmp.setStaffNo(tt.getStaffNo());
					ttmp.setStatusCode(tt.getStatusCode());
					ttmp.setSuccessTime(tt.getSuccessTime());
					tmInfo.setId(ttmp);
					tmInfoList.add(tmInfo);
				}
				this.terminalParaSetManager.saveMpInfo(tmInfoList);
			} else {
				List<TTmnlParam> ttmInfo = new ArrayList<TTmnlParam>();
				for (FetchInfoBean tt : fetchInfo) {
					TTmnlParam ttmp = new TTmnlParam();
					TTmnlParamId tp = new TTmnlParamId();
					tp.setBlockSn(tt.getBlockSn());
					tp.setProtItemNo(tt.getProtItemNo());
					tp.setInnerBlockSn(tt.getInnerBlockSn());
					tp.setTmnlAssetNo(tt.getTmnlAssetNo());
					ttmp.setId(tp);
					ttmp.setCurrentValue(tt.getCurrentValue());
					ttmp.setFailureCode(tt.getFailureCode());
					ttmp.setHistoryValue(tt.getHistoryValue());
					ttmp.setLastSendTime(tt.getLastSendTime());
					ttmp.setNextSendTime(tt.getLastSendTime());
					ttmp.setResendCount(tt.getResendCount());
					ttmp.setSaveTime(tt.getSaveTime());
					ttmp.setSendTime(tt.getSendTime());
					ttmp.setStaffNo(tt.getStaffNo());
					ttmp.setStatusCode(tt.getStatusCode());
					ttmp.setSuccessTime(tt.getSuccessTime());
					ttmInfo.add(ttmp);
				}
				this.terminalParaSetManager.saveTerminal(ttmInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据库当前值
	 * 
	 * @throws Exception
	 */
	private List<FetchInfoBean> getTerminalInfo(String tmnlAssetNo, short pn) {
		List<FetchInfoBean> rs = new ArrayList<FetchInfoBean>();
		try {
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				short[] pnTotal = { pn };
				rs = this.terminalParaSetManager.getTmnlMpList(this.proNO,
						tmnlAssetNo, pnTotal);
			} else {
				rs = this.terminalParaSetManager.getTerminalList(this.proNO,
						tmnlAssetNo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 测量点参数下发召测判断测量点是否存在。
	 * 
	 * @param tmnlAssetNo
	 *            终端资产号
	 * @param pn
	 *            测量点号
	 * @return
	 * @throws Exception
	 */
	private boolean judgeMpExist(String tmnlAssetNo, short pn) {
		List<EDataMp> rs = null;
		try {
			rs = this.terminalParaSetManager.getMpInfoList(tmnlAssetNo, pn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (rs != null && rs.size() > 0 && rs.get(0).getIsValid() == false) {
			// 存在有效测点返回true
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存后台下发任务相关表信息
	 */
	private void saveTaskInfo(String tmnlAssetNo, String pn) {
		try {
			// 获取操作员信息
			PSysUser userInfo = (PSysUser) this.getSession().getAttribute(
					"pSysUser");
			TBgTaskId tBgTaskId = new TBgTaskId();
			TBgTask tBgTask = new TBgTask();
			tBgTaskId.setTaskId(this.taskId);
			tBgTaskId.setTaskTime(new Date());
			tBgTaskId.setStaffNo(userInfo.getStaffNo());
			tBgTaskId.setTaskName("终端参数设置");
			tBgTaskId.setTmnlAssetNo(tmnlAssetNo);
			tBgTaskId.setObjList(pn);
			if ("22".equals(this.dataType) || "24".equals(this.dataType)) {
				tBgTaskId.setObjType(new Byte("1"));
				tBgTaskId.setTaskType(new Byte("1"));
			} else {
				tBgTaskId.setObjType(new Byte("0"));
				tBgTaskId.setTaskType(new Byte("0"));
			}
			// 设置任务状态为待执行
			tBgTaskId.setTaskStatus("0");
			tBgTask.setId(tBgTaskId);
			this.tBgTaskList.add(tBgTask);
			// this.backGroundTaskManager.saveTBgTaskInfo(tBgTask);

			TBgTaskDetailId tBgTaskDetailId = new TBgTaskDetailId();
			TBgTaskDetail tBgTaskDetail = new TBgTaskDetail();
			tBgTaskDetailId.setTaskId(this.taskId);
			String dataItemNo = Integer.toHexString(this.fn);
			dataItemNo = dataItemNo.length() == 1 ? ("0" + dataItemNo
					.toUpperCase()) : dataItemNo.toUpperCase();
			tBgTaskDetailId.setDataItemNo(dataItemNo);
			tBgTaskDetailId.setDataType(new Byte("4"));
			tBgTaskDetail.setId(tBgTaskDetailId);
			this.tBgTaskDetailList.add(tBgTaskDetail);
			// this.backGroundTaskManager.saveTBgTaskDetail(tBgTaskDetail);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private StringBuffer translatePn(StringBuffer sb, short pn) {
		if (sb.length() < pn) {
			for (int i = sb.length(); i < pn; i++) {
				if (i == pn - 1) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
		} else {
			sb.replace(pn - 1, pn, "1");
		}

		return sb;
	}

	public List<VwDataTypePBean> getDt() {
		return dt;
	}

	public void setDt(List<VwDataTypePBean> dt) {
		this.dt = dt;
	}

	public List<VwProtocolCodeBean> getPc() {
		return pc;
	}

	public void setPc(List<VwProtocolCodeBean> pc) {
		this.pc = pc;
	}

	public List<BCommProtocol> getBcp() {
		return bcp;
	}

	public void setBcp(List<BCommProtocol> bcp) {
		this.bcp = bcp;
	}

	public List<BCommProtocolItem> getBcpi() {
		return bcpi;
	}

	public void setBcpi(List<BCommProtocolItem> bcpi) {
		this.bcpi = bcpi;
	}

	public List<BCommProtItemListId> getBpil() {
		return bpil;
	}

	public void setBpil(List<BCommProtItemListId> bpil) {
		this.bpil = bpil;
	}

	public List<TTmnlParam> getTtp() {
		return ttp;
	}

	public void setTtp(List<TTmnlParam> ttp) {
		this.ttp = ttp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getProItemNo() {
		return proItemNo;
	}

	public void setProItemNo(String proItemNo) {
		this.proItemNo = proItemNo;
	}

	public String getProNO() {
		return proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public short getFn() {
		return fn;
	}

	public void setFn(short fn) {
		this.fn = fn;
	}

	public short[] getPn() {
		return pn;
	}

	public void setPn(short[] pn) {
		this.pn = pn;
	}

	public List<List<TTmnlParam>> getTtpList() {
		return ttpList;
	}

	public void setTtpList(List<List<TTmnlParam>> ttpList) {
		this.ttpList = ttpList;
	}

	public List<FetchInfoBean> getTmnlInfo() {
		return tmnlInfo;
	}

	public void setTmnlInfo(List<FetchInfoBean> tmnlInfo) {
		this.tmnlInfo = tmnlInfo;
	}

	public List<List<FetchInfoBean>> getFiBeanList() {
		return fiBeanList;
	}

	public void setFiBeanList(List<List<FetchInfoBean>> fiBeanList) {
		this.fiBeanList = fiBeanList;
	}

	public int getTmnlflg() {
		return tmnlflg;
	}

	public void setTmnlflg(int tmnlflg) {
		this.tmnlflg = tmnlflg;
	}

	public int getMpflg() {
		return mpflg;
	}

	public void setMpflg(int mpflg) {
		this.mpflg = mpflg;
	}

	public String[] getTmnlAssetNoArr() {
		return tmnlAssetNoArr;
	}

	public void setTmnlAssetNoArr(String[] tmnlAssetNoArr) {
		this.tmnlAssetNoArr = tmnlAssetNoArr;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Map<String, String> getTmnlStatusMap() {
		return tmnlStatusMap;
	}

	public void setTmnlStatusMap(Map<String, String> tmnlStatusMap) {
		this.tmnlStatusMap = tmnlStatusMap;
	}

	public List<TerminalInfoBean> getTmnlList() {
		return tmnlList;
	}

	public void setTmnlList(List<TerminalInfoBean> tmnlList) {
		this.tmnlList = tmnlList;
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

	public boolean isDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(boolean dataFlag) {
		this.dataFlag = dataFlag;
	}

	public int getTaskSecond() {
		return taskSecond;
	}

	public void setTaskSecond(int taskSecond) {
		this.taskSecond = taskSecond;
	}

	public Map<String, List<FetchInfoBean>> getTmnlTermDataList() {
		return tmnlTermDataList;
	}

	public void setTmnlTermDataList(
			Map<String, List<FetchInfoBean>> tmnlTermDataList) {
		this.tmnlTermDataList = tmnlTermDataList;
	}

	public Map<String, MpMeterInfoBean> getTmnlTermMap() {
		return tmnlTermMap;
	}

	public void setTmnlTermMap(Map<String, MpMeterInfoBean> tmnlTermMap) {
		this.tmnlTermMap = tmnlTermMap;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
