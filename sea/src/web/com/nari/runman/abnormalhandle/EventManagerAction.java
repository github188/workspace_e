package com.nari.runman.abnormalhandle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.FetchInfoBean;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.StatusCodeUtils;

/**
 * 事件管理设定Action
 * 
 * @author longkc
 */
public class EventManagerAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	// 要返回的值
	private List<VwDataTypePBean> dt;
	
	TaskDeal taskDeal = new TaskDeal();

	private List<VwProtocolCodeBean> pc;

	private List<BProtocolEvent> pe;

	// 规约类型编码
	private String protocolCode;

	// 规约项编码
	private String proNO;

	// 终端资产编号集合
	private String[] tmnlAssetNoArr;
	// 终端存在flg
	private int tmnlflg;
	//状态flg
	private int mpflg;
	// FN号
	private short fn;
	// json格式数据
	private String jsonData;
	// 召测状态
	private String[] status;

	private boolean success = true;

	// 召测结果
	private List<List<FetchInfoBean>> fiBeanList;

	// 动态注入服务层
	private EventManageManager eventManageManager;

	public void setEventManageManager(EventManageManager eventManageManager) {
		this.eventManageManager = eventManageManager;
	}

	/**
	 * 查询规约类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryPortocolCode() throws Exception {
		this.logger.debug("规约类型查询开始");
		this.setPc(this.eventManageManager.getProCodeList());
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
		this.setDt(this.eventManageManager.getDataTypeList(this.protocolCode,
				this.proNO));
		this.logger.debug("终端规约对应数据类型查询结束");
		return SUCCESS;
	}
	
	/**
	 * 更新规约事件级别
	 * @return
	 * @throws Exception
	 */
	public String saveEventInfo() throws Exception {
		this.logger.debug("更新规约事件级别开始");
		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);

		JsonConfig jsonConfig = new JsonConfig();
		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(BProtocolEvent.class);

		BProtocolEvent[] eventifnoBean = (BProtocolEvent[]) JSONSerializer.toJava(
				jsonArray, jsonConfig);
		List<BProtocolEvent> eventInfo = new ArrayList<BProtocolEvent>();
		for (int i = 0; i < eventifnoBean.length; i ++) {
			eventInfo.add(eventifnoBean[i]);
		}
		this.eventManageManager.updateEventInfo(eventInfo);
		this.logger.debug("更新规约事件级别结束");
		return SUCCESS;
	}

	/**
	 * 查询规约事件信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryEventInfo() throws Exception {
		this.logger.debug("查询规约事件信息开始");
		this.setPe(this.eventManageManager.getEventInfo(this.protocolCode));
		this.logger.debug("查询规约事件信息结束");
		return SUCCESS;
	}

	/**
	 * 参数召测。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String fetchTerminalPara() {

		this.logger.debug("召测开始");
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

		// 参数项设置
		List<ParamItem> paramList = new ArrayList<ParamItem>();

		ParamItem pitem = new ParamItem();
		pitem.setFn(this.fn);// 设置FN号

		pitem.setPoint((short) 0); // 设置PN号

		// 编码对象集合
		List<Item> items = new ArrayList<Item>();

		pitem.setItems((ArrayList<Item>) items);
		paramList.add(pitem);

		List<List<FetchInfoBean>> rs = new ArrayList<List<FetchInfoBean>>();
		List<Response> ttr = null;
		try {
			// 召测
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.QUERY_PARAMS, paramList);
			ttr = taskDeal.getAllResponse(20);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] statusArr = new String[this.tmnlAssetNoArr.length];
		String[] tmnlAssetNoRet = new String[this.tmnlAssetNoArr.length];
		List<FetchInfoBean> resList = new ArrayList<FetchInfoBean>();
		for (int i = 0; i < ttr.size(); i++) {
			tmnlAssetNoRet[i] = ttr.get(i).getTmnlAssetNo();
			statusArr[i] = String.valueOf(ttr.get(i).getTaskStatus());
			if (statusArr[i].equals("3")) {
				resList = new ArrayList<FetchInfoBean>();
				statusArr[i] = StatusCodeUtils.PARAM_FETCH_SUCCESS;
				// 获取终端召测状态
				List<ResponseItem> ri = ttr.get(i).getResponseItems();
				for (int j = 0; j < ri.size(); j++) {
					FetchInfoBean tp = new FetchInfoBean();
					// 数据值
					tp.setCurrentValue(ri.get(j).getValue());
					// 规约项编码
					tp.setProtItemNo(ri.get(j).getCode());
					// 终端资产号
					tp.setTmnlAssetNo(ttr.get(i).getTmnlAssetNo());
					// 块序号
					tp.setBlockSn(ri.get(j).getBlockSN());
					tp.setInnerBlockSn(j);
					// 块内序号
					tp.setInnerBlockSn(Integer.valueOf(ri.get(j).getBlockDef()));
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
			for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
				statusArr[i] = StatusCodeUtils.TMNL_NO_REPLAY;
				tmnlAssetNoRet[i] = this.tmnlAssetNoArr[i];
				rs.add(resList);
			}
		}
		this.setStatus(statusArr);
		this.setTmnlAssetNoArr(tmnlAssetNoRet);
		this.setFiBeanList(rs);
		this.logger.debug("召测结束");
		return SUCCESS;
	}

	/**
	 * 参数下发
	 * 
	 * @return void
	 */
	public String sendPara() {
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

		// 参数项设置
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		JSONArray jsonArray = JSONArray.fromObject(this.jsonData);
		JsonConfig jsonConfig = new JsonConfig();

		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);
		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(TTmnlParam.class);
		List<TTmnlParam> ttpara = new ArrayList<TTmnlParam>();
		// 取出终端对应的参数、只有一个数组，只取第一位即可
		TTmnlParam[] bpItem = (TTmnlParam[]) JSONSerializer.toJava(jsonArray
				.getJSONArray(0), jsonConfig);

		String[] statusArr = new String[this.tmnlAssetNoArr.length];
		String[] tmnlAssetNoRet = null;

		List<String> tmnlAssetNoList = new ArrayList<String>();
		// 下发参数
		for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
			tmnlAssetNoRet = new String[this.tmnlAssetNoArr.length];

			tmnlAssetNoList.add(this.tmnlAssetNoArr[i]);
		}

		List<TTmnlParam> bpList = new ArrayList<TTmnlParam>();
		ParamItem pitem = new ParamItem();
		pitem.setFn((short) fn);// 设置FN号
		pitem.setPoint((short) 0); // 设置PN号
		// 编码对象集合
		List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < bpItem.length; i++) {
			// 下发参数
			bpList.add(bpItem[i]);
			Item item = new Item(bpItem[i].getId().getProtItemNo());
			item.setValue(bpItem[i].getCurrentValue().trim());
			items.add(item);
			// 将状态设置为下发参数中
			bpItem[i].setStatusCode(StatusCodeUtils.PARAM_SENDING);
			for (int j = 0; j < this.tmnlAssetNoArr.length; j++) {
				bpItem[i].getId().setTmnlAssetNo(tmnlAssetNoArr[j]);
				ttpara.add(bpItem[i]);
			}
		}
		pitem.setItems((ArrayList<Item>) items);
		paramList.add(pitem);
		// 保存下发参数
		// this.savePara(ttpara);
		this.sendOnePara(tmnlAssetNoList, paramList);
		List<Response> ttr = this.taskDeal.getAllResponse(20);
		for (int i = 0; i < ttr.size(); i++) {
			// 获取下发状态code
			statusArr[i] = String.valueOf(ttr.get(i).getTaskStatus());
			tmnlAssetNoRet[i] = ttr.get(i).getTmnlAssetNo();
			if ("3".equals(statusArr[i])) {
				// 下发成功
				for (int j = 0; j < ttpara.size(); j++) {
					ttpara.get(j).setStatusCode(
							StatusCodeUtils.PARAM_SEND_SUCCESS);
					LOpTmnlLog lOpTmnlLog = new LOpTmnlLog();
					lOpTmnlLog.setEmpNo(userInfo.getName());
					lOpTmnlLog.setOrgNo(userInfo.getOrgNo());
					lOpTmnlLog.setIpAddr(ipAddr);
					lOpTmnlLog.setOpModule("事件屏蔽与分级");
					lOpTmnlLog.setOpButton("下发");
					lOpTmnlLog.setTmnlAssetNo(this.tmnlAssetNoArr[i]);
					lOpTmnlLog.setProtItemNo(ttpara.get(j).getId()
							.getProtItemNo());
					lOpTmnlLog.setCurValue(ttpara.get(j).getCurrentValue());
					lOpTmnlLog.setOpTime(new Date());
					this.addOpTmnlLog(lOpTmnlLog);
				}
				statusArr[i] = StatusCodeUtils.PARAM_SEND_SUCCESS;
				this.updatePara(ttpara);
			} else if ("4".equals(statusArr[i])) {
				// 下发失败
				if (!StatusCodeUtils.PARAM_SEND_SUCCESS.equals(statusArr[i])) {
					statusArr[i] = StatusCodeUtils.PARAM_SEND_FAILE;
				}
			} else if ("1".equals(statusArr[i])) {
				// 终端不在线
				if (!StatusCodeUtils.PARAM_SEND_SUCCESS.equals(statusArr[i])) {
					statusArr[i] = StatusCodeUtils.TMNL_OFF_LINE;
				}
			} else if ("0".equals(statusArr[i])) {
				// 在规定的时间内缓存没有返回
				if (!StatusCodeUtils.PARAM_SEND_SUCCESS.equals(statusArr[i])) {
					statusArr[i] = StatusCodeUtils.COR_UNBACK;
				}
			} 
			else {
				// 终端无应答
				if (!StatusCodeUtils.PARAM_SEND_SUCCESS.equals(statusArr[i])) {
					statusArr[i] = StatusCodeUtils.TMNL_NO_REPLAY;
				}

			}
		}
		if (ttr.size() == 0) {
			for (int i = 0; i < this.tmnlAssetNoArr.length; i++) {
				statusArr[i] = StatusCodeUtils.TMNL_NO_REPLAY;
				tmnlAssetNoRet[i] = this.tmnlAssetNoArr[i];
			}
		}
		// 更新下发状态
		// this.updatePara(ttpara);
		this.setStatus(statusArr);
		this.setTmnlAssetNoArr(tmnlAssetNoRet);
		this.logger.debug("参数下发结束");
		return SUCCESS;
	}

	/**
	 * 判断前置机和缓存通信状态
	 * 
	 * @return true:正常,false：中断
	 */
	private boolean isConnet() {
		boolean retCode = true;
		//Web应用服务器与缓存服务器通信状态
		if (!TaskDeal.isCacheRunning()) {
			this.setMpflg(4);
			retCode = false;
		}
		//前置集群服务通信状态
		if (!TaskDeal.isFrontAlive()) {
			this.setMpflg(5);
			retCode = false;
		}
		return retCode;
	}
	
	/**
	 * 参数下发。
	 * 
	 * @return
	 * @throws Exception
	 */
	private void sendOnePara(List<String> tmnlAssetNoList,
			List<ParamItem> paramList) {
		try {
			this.taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.SET_PARAMETERS, paramList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 下发前保存下发参数信息。
	 * 
	 * @return
	 * @throws Exception
	 */
	// private void savePara(List<TTmnlParam> ttmInfo) {
	// try {
	// this.eventManageManager.saveORUpdateTerminal(ttmInfo);
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
	private void updatePara(List<TTmnlParam> ttmInfo) {
		try {
			this.eventManageManager.saveORUpdateTerminal(ttmInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getMpflg() {
		return mpflg;
	}

	public void setMpflg(int mpflg) {
		this.mpflg = mpflg;
	}
	
	public void setDt(List<VwDataTypePBean> dt) {
		this.dt = dt;
	}

	public void setPc(List<VwProtocolCodeBean> pc) {
		this.pc = pc;
	}

	public List<VwDataTypePBean> getDt() {
		return dt;
	}

	public List<VwProtocolCodeBean> getPc() {
		return pc;
	}

	public String getProtocolCode() {
		return protocolCode;
	}

	public void setProtocolCode(String protocolCode) {
		this.protocolCode = protocolCode;
	}

	public String getProNO() {
		return proNO;
	}

	public void setProNO(String proNO) {
		this.proNO = proNO;
	}

	public String[] getTmnlAssetNoArr() {
		return tmnlAssetNoArr;
	}

	public void setTmnlAssetNoArr(String[] tmnlAssetNoArr) {
		this.tmnlAssetNoArr = tmnlAssetNoArr;
	}

	public int getTmnlflg() {
		return tmnlflg;
	}

	public void setTmnlflg(int tmnlflg) {
		this.tmnlflg = tmnlflg;
	}

	public short getFn() {
		return fn;
	}

	public void setFn(short fn) {
		this.fn = fn;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<List<FetchInfoBean>> getFiBeanList() {
		return fiBeanList;
	}

	public void setFiBeanList(List<List<FetchInfoBean>> fiBeanList) {
		this.fiBeanList = fiBeanList;
	}

	public List<BProtocolEvent> getPe() {
		return pe;
	}

	public void setPe(List<BProtocolEvent> pe) {
		this.pe = pe;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
