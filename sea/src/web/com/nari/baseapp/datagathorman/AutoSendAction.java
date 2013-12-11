package com.nari.baseapp.datagathorman;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.baseapp.datagatherman.AutoSendDto;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.support.Page;

public class AutoSendAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private AutoSendManager autoSendManager;

	public void setAutoSendManager(AutoSendManager autoSendManager) {
		this.autoSendManager = autoSendManager;
	}

	private boolean success = true;

	private String consId;
	private String queryTmnlNo;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;

	private List<AutoSendDto> asQueryList;
	private List<AutoSendDto> meterInfoList;

	private String regSn; // 注册序号
	private String tmnlAssetNo; // 集中器终端资产号

	private String msg = "";

	/**
	 * 查询集抄户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAutoSendInfo() throws Exception {
		try {
			this.logger.debug("查询集抄户信息");
			Page<AutoSendDto> AsInfoPage = this.autoSendManager
					.findAsQueryInfo(consId, queryTmnlNo, start, limit);
			if (AsInfoPage != null) {
				asQueryList = AsInfoPage.getResult();
				totalCount = AsInfoPage.getTotalCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String queryMeterData() throws Exception {
		// 是否选中召测终端判断
		if (this.regSn == null || "".equals(this.regSn)) {
			this.msg = "请选择用户";
			return SUCCESS;
		}
		// 前置集群服务通信状态
		if (!TaskDeal.isFrontAlive()) {
			this.msg = "前置集群服务中断";
			return SUCCESS;
		}
		try {
			TaskDeal taskDeal = new TaskDeal();

			// List<ParamItem> paramList = new ArrayList<ParamItem>();
			// ParamItem pitem = new ParamItem();
			// pitem.setFn((short) 1);// 设置FN号
			// List<Item> items = new ArrayList<Item>();
			// pitem.setItems((ArrayList<Item>) items);
			//			
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(tmnlAssetNo);
			//			
			// pitem.setPoint(Short.parseShort(regSn));
			// paramList.add(pitem);
			//			
			// taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
			// FrontConstant.REQUEST_REAL_DATA, paramList);

			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			RealDataItem rdi = new RealDataItem();
			rdi.setPoint(Short.parseShort(regSn));

			List<Item> items = new ArrayList<Item>();
			Item it = new Item("11140F");
			items.add(it);
			rdi.setCodes((ArrayList<Item>) items);
			rdi.setFreezingType((short) 4);
			realDataItems.add(rdi);

			taskDeal.callRealData(tmnlAssetNoList, realDataItems);
			List<Response> ttr = taskDeal.getAllResponse(45);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			Date curretDate = new Date();
			// String currTime = "";
			String currTime = sdf.format(curretDate);
			Map<Short, String> statusMaps = getStatusMaps();
			meterInfoList = new ArrayList<AutoSendDto>();

			if (ttr != null && ttr.size() > 0) {
				for (Response re : ttr) {
					if (re == null)
						continue;
					List<ResponseItem> list = re.getResponseItems();
					if (re.getTaskStatus() == 3 && list != null) {
						String value = list.get(0).getValue();
						String[] values = value.split(",");
						if (values == null)
							return null;

						AutoSendDto autoSendDto = new AutoSendDto();
						autoSendDto.setRegSn(Integer.valueOf(regSn));

						// autoSendDto.setCommAddr1(values[0]);
						// 2010-10-18针对四川盘龙晓程规约进行修改
						autoSendDto.setCommAddr1(values[1]);
						if (values.length > 1) {
							autoSendDto.setCurrentValue(values[2]);
						}

						// if(values.length>2){
						// String date1 = "20"+values[2];
						// currTime = sdf1.format(sdf.parse(date1));
						// }
						autoSendDto.setMrSectDate(currTime);
						meterInfoList.add(autoSendDto);
					} else {
						AutoSendDto autoSendDto = new AutoSendDto();
						autoSendDto.setRegSn(Integer.valueOf(regSn));
						// autoSendDto.setCurrentValue(statusMaps.get(re.getTaskStatus()));
						autoSendDto.setCurrentValue(statusMaps.get((short) 4));
						autoSendDto.setMrSectDate(currTime);
						meterInfoList.add(autoSendDto);
					}
				}// for
			} else {
				AutoSendDto autoSendDto = new AutoSendDto();
				autoSendDto.setRegSn(Integer.valueOf(regSn));
				autoSendDto.setCurrentValue(statusMaps.get((short) 5));
				autoSendDto.setMrSectDate(currTime);
				meterInfoList.add(autoSendDto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获取任务执行后返回的状态码表
	 * 
	 * @return
	 */
	private Map<Short, String> getStatusMaps() {
		Map<Short, String> statusMaps = new HashMap<Short, String>();
		statusMaps.put((short) 0, "终端响应超时");
		statusMaps.put((short) 1, "终端不在线");
		statusMaps.put((short) 2, "终端无应答");
		statusMaps.put((short) 3, "成功");
		statusMaps.put((short) 4, "失败");
		statusMaps.put((short) 5, "前置集群服务中断");

		return statusMaps;
	}

	public String getMeterData() {
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId;
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

	public List<AutoSendDto> getAsQueryList() {
		return asQueryList;
	}

	public void setAsQueryList(List<AutoSendDto> asQueryList) {
		this.asQueryList = asQueryList;
	}

	public List<AutoSendDto> getMeterInfoList() {
		return meterInfoList;
	}

	public void setMeterInfoList(List<AutoSendDto> meterInfoList) {
		this.meterInfoList = meterInfoList;
	}

	public String getRegSn() {
		return regSn;
	}

	public void setRegSn(String regSn) {
		this.regSn = regSn;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getQueryTmnlNo() {
		return queryTmnlNo;
	}

	public void setQueryTmnlNo(String queryTmnlNo) {
		this.queryTmnlNo = queryTmnlNo;
	}
}
