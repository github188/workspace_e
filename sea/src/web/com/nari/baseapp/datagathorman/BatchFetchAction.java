package com.nari.baseapp.datagathorman;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.datagatherman.TbgTask;
import com.nari.baseapp.datagatherman.TbgTaskDetail;
import com.nari.baseapp.datagatherman.TbgTaskFinder;
import com.nari.baseapp.datagatherman.TbgTaskStatisticsFinder;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;

public class BatchFetchAction extends BaseAction {
	private boolean success = true;
	private String message;// 记录错误的信息
	private String taskId;
	private TbgTaskStatisticsFinder statistics;

	// 通过前端的条件来查找
	private TbgTaskFinder finder;
	// 前端的动态的headers
	private List<Map> headers = new ArrayList<Map>(15);
	// 键值的映射
	private Map codeAndValue = new HashMap<String, String>();
	// 自动注入
	IBatchFetchManager iBatchFetchManager;
	// 第一次执行时间
	private Date sendTime;
	// 下一次执行时间
	private Date nextSendTime;
	private long start;
	private int limit;
	// 组合id
	private String combiId;
	// 记录传入的pns的字符串, 与规则有些不同。第一位表示p0
	private String pns;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 到底数据的类型
	private Integer queryType;
	// 结果列表
	private List<Map> resultList;
	private long resultCount;
	// 终端资产号列表
	private List<String> tmnlAssetNos;

	public long getResultCount() {
		return resultCount;
	}

	public Map getCodeAndValue() {
		return codeAndValue;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}

	public long getStart() {
		return start;
	}

	public void setStatistics(TbgTaskStatisticsFinder statistics) {
		this.statistics = statistics;
	}

	public TbgTaskFinder getFinder() {
		return finder;
	}

	public void setFinder(TbgTaskFinder finder) {
		this.finder = finder;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public List<Map> getHeaders() {
		return headers;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setNextSendTime(Date nextSendTime) {
		this.nextSendTime = nextSendTime;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setPns(String pns) {
		this.pns = pns;
	}

	public List<String> getTmnlAssetNos() {
		return tmnlAssetNos;
	}

	public void setTmnlAssetNos(List<String> tmnlAssetNos) {
		this.tmnlAssetNos = tmnlAssetNos;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public void setCombiId(String combiId) {
		this.combiId = combiId;
	}

	public void setType(String type) {
		this.type = type;
	}

	// 组合的类型
	private String type;

	public void setiBatchFetchManager(IBatchFetchManager iBatchFetchManager) {
		this.iBatchFetchManager = iBatchFetchManager;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	/***** 查找一个召测组合的项 ***/
	public String findCombi() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			resultList = iBatchFetchManager.findAllCombi(user, type);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 查找召测组合的小项 ***/
	public String findCombiItem() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			resultList = iBatchFetchManager.findComItem(user, combiId);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 定义一个巡测Task ****/
	@SuppressWarnings("unchecked")
	public String doTask() {
		try {

			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			// iBatchFetchManager.addBgTask(tt, ttds);
			// tmnlAssetNos = new ArrayList<String>();
			// tmnlAssetNos.add("TMNL00435");
			// tmnlAssetNos.add("TMNL00436");
			// tmnlAssetNos.add("TMNL00436");
			// tmnlAssetNos.add("TMNL00996");
			List<TbgTask> tts = initParams();
			List<TbgTaskDetail> ttds = initParamItem();
			iBatchFetchManager.addBgTask(tts, ttds);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/***
	 ****通过必要的参数来完成对private List<TbgTask> tts 的初步设值 *
	 **/
	private List<TbgTask> initParams() {
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser user = (PSysUser) session.get("pSysUser");
		if (pns == null) {
			pns = "1";
		}
		String combiName = iBatchFetchManager.findCombiNameById(user, combiId);
		List<TbgTask> list = new ArrayList<TbgTask>();
		tmnlAssetNos = new ArrayList<String>(new HashSet<String>(tmnlAssetNos));
		Date now = new Date();
		Date sendTimeNow = sendTime == null ? now : sendTime;
		// 让task_time的时间和任务的执行时间一样
		now = sendTimeNow;
		for (String asset : tmnlAssetNos) {
			if (pns.startsWith("1")) {
				// 如果要召测的pn里面有pn=0的项，需要另外加一个任务
				TbgTask tt = new TbgTask();
				tt.setTaskTime(now);
				tt.setStaffNo(user.getStaffNo());
				tt.setOrgNo(user.getOrgNo());
				// 最大重试数，填5
				tt.setMaxTry(5);
				// 开始执行时间
				tt.setSendTime(sendTimeNow);
				// 下次执行时间
				tt.setNextSendTime(addMin(sendTimeNow, 5));
				tt.setTmnlAssetNo(asset);
				tt.setObjType(0);
				tt.setTaskName(combiName);
				// 原始在线等待状态
				tt.setTaskStatus("02");
				tt.setTaskType(4);
				tt.setRwFlag(1);
				// 时间初始化
				if (!queryType.equals(1)) {
					tt.setStartTime(startTime);
					tt.setEndTime(endTime);
				}
				list.add(tt);
				// 如果只有0这个测量点
				// 是默认的处理方式.前台不选择测量点时的处理
				if (pns.equals("1")) {
					continue;
				}
			}
			TbgTask t = new TbgTask();
			t.setTaskTime(now);
			t.setStaffNo(user.getStaffNo());
			t.setOrgNo(user.getOrgNo());
			t.setTmnlAssetNo(asset);
			t.setSendTime(new Date());
			// 最大重试数
			t.setMaxTry(5);
			// 开始执行时间
			t.setSendTime(sendTimeNow);
			// 下次执行时间
			t.setNextSendTime(addMin(sendTimeNow, 5));
			t.setTaskName(combiName);
			// 原始在线等待状态
			t.setTaskStatus("02");
			t.setTaskType(4);
			// 时间初始化
			if (!queryType.equals(1)) {
				t.setStartTime(startTime);
				t.setEndTime(endTime);
			}
			// 召测
			t.setRwFlag(1);
			t.setObjType(1);
			t.setObjList(pns.substring(1));
			list.add(t);
		}
		return list;
	}

	/*******
	 * 此方法用于将传入的数据<br>
	 * 转化为透明规约的大项列表，<br>
	 * 并且组装成合乎要求的TbgTaskDetail列表 *
	 * 
	 * @throws ServiceException
	 *             *
	 ****/
	private List<TbgTaskDetail> initParamItem() throws ServiceException {
		List<TbgTaskDetail> list = new ArrayList<TbgTaskDetail>();
		// 通过规约项的id，来找到所有的大项而且不重复的字符串列表
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser user = (PSysUser) session.get("pSysUser");

		// 得到大项的code，并且已经去掉了id
		List<String> fathers = iBatchFetchManager.findAndGetFather(user,
				combiId);
		for (String str : fathers) {
			TbgTaskDetail ttd = new TbgTaskDetail();
			ttd.setDataItemNo(str);
			ttd.setDataType(queryType);
			list.add(ttd);
		}
		return list;
	}

	/*** 找到所有的任务 *****/
	public String findTask() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");

			Page page = iBatchFetchManager.findTbgTask(user, start, limit,
					finder);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 查找批量巡测的统计结果
	 * 
	 * @return
	 */
	public String findStatistics() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = iBatchFetchManager.findStatistics(user, start,
					limit, statistics);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 对后台查到的任务结果进行组装，使其适合前台显示 ****/
	@SuppressWarnings("unchecked")
	public String findResult() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			List list = iBatchFetchManager.findTbgTaskResult(user, taskId);
			int dataKey = 1;
			// 数据的显示，在不同的维度上面对结果集进行过滤处理
			// tmnl_asset_no 维度 测量点维度 ,虽然此过程使用group by可以完成，但是效率不够高
			HashSet hs = new HashSet();
			resultList = new ArrayList<Map>(30);
			// 组装Ext js 中的headers
			for (Object o : list) {
				HashMap hm = (HashMap) o;

				// 组装key
				String key = hm.get("tmnlAssetNo") + "_" + hm.get("mpSn");
				if (!hs.contains(key)) {
					hm.put("dataId", dataKey++);
					hs.add(key);
					resultList.add(hm);
				}
				HashMap m = new HashMap();
				m.put("header", hm.get("protocolName"));
				m.put("dataIndex", hm.get("dataItemNo"));
				headers.add(m);
				// 填充code and value code的格式为dataItemNo_测量点
				codeAndValue.put(hm.get("tmnlAssetNo") + "_"
						+ hm.get("dataItemNo") + "_" + hm.get("mpSn"), hm
						.get("dataValue"));
			}
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**** 启用 ***/
	public String enableTask() {
		try {
			iBatchFetchManager.enableTask(taskId);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 停用任务 ****/
	public String disableTask() {
		try {
			iBatchFetchManager.disableTask(taskId);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 将一个时间加减相应的分钟
	private Date addMin(Date now, int mins) {
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.MINUTE, mins);
		return c.getTime();
	}

	public static void main(String[] args) {

	}
}
