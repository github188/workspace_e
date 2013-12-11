package com.nari.baseapp.datagathorman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import utils.system;
import com.nari.coherence.Dispatcher;
import com.nari.coherence.IFuture;
import com.nari.coherence.TaskDeal;
import com.nari.coherence.TaskTmnlPnRelation;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.Response;
import com.nari.util.DataFetchResponse;
import com.opensymphony.xwork2.ActionContext;

/***
 * 代理TaskDeal，实现特定的功能
 * 
 * @author huangxuan *
 **/
public class DataFetchProxy {
	private TaskDeal td;
	private IFuture ifuture;
	private String key;
	private String queryType;
	private List<String> fatherCodes;
	private String prefix = "dataFetch";
	private List resultMap;
	private Map errorMap;
	private DataFetchAction dataFetchAction;
	private static final Logger log = Logger.getLogger(TaskDeal.class);
	private HashSet<String> tmnlSet = new HashSet<String>();
	private Logger logger = Logger.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private DataFetchProxy() {
	}

	public DataFetchProxy(TaskDeal td, String key) {
		this.td = td;
		ifuture = td.getiFuture();
		this.key = key;
	}

	public DataFetchProxy(TaskDeal td, String key, String prefix,
			DataFetchAction dataFetchAction) {
		this.td = td;
		ifuture = td.getiFuture();
		logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$生成的任务号列表:"
				+ ifuture.getTaskIDs());
		this.key = key;
		this.dataFetchAction = dataFetchAction;
		this.prefix = prefix;
		List<Response> ors = this.td.getOfflineResponse();
		List<DataFetchResponse> rs = new ArrayList<DataFetchResponse>();
		for (Response r : ors) {
			DataFetchResponse d = new DataFetchResponse("offline", -99, "终端不在线");
			d.setTmnlAssetNo(r.getTmnlAssetNo());
			rs.add(d);
		}
		this.td.setDataFetchOfflineResponse(rs);
		List<TaskTmnlPnRelation> totalTtr = this.td.getTotalttr();
		for (TaskTmnlPnRelation tp : totalTtr) {
			tmnlSet.add(tp.getTtr().getTmnlAssetNo());
		}
	}

	/**
	 * @desc 此方法的如原始的getAllResponse类似， 但是忽略了从在一定的时间类没有在缓存得到结果<br>
	 *       这个错误。他会直接将得到的结果放在session里面
	 * @param iFuture
	 * @param second
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void getAllResponse(int second) {
		second = second - 3;
		HashSet<String> pns = new HashSet<String>();
		addResponseList(dataFetchAction.parseDataFetch(td.getOfflineResponse()));
		List ls = ifuture.getTaskIDs();
		logger.debug("数据召测等待时间为" + second);
		HashSet<Integer> nowTaskId = new HashSet<Integer>(ls);
		int len = ls.size() * td.getCount();
		try {
			for (int i = 0; i < len; i++) {
				// int waitTime = second / ls.size();
				if (nowTaskId.size() == 0) {
					// logger
					logger.debug("召测数据等待结束");
					break;
				}
				Response r = ifuture.getResponse(second * 1000 / len);
				// Response r = ifuture.getResponse(10);
				if (null != r) {
					if (r.isContinue()) {
						Dispatcher.removeId(r.getTaskId());
						nowTaskId.remove(r.getTaskId());
					}
					logger.debug("任务号" + r.getTaskId() + "对应终端"
							+ r.getTerminalAddr() + "已经返回结果");
					List<Response> rs = new ArrayList<Response>();
					rs.add(r);
					List<DbData> dbs = r.getDbDatas();
					for (DbData d : dbs) {
						// if(d.getPn()==108){
						// System.out.println("返回结果108");
						// }
						pns.add(d.getPn() + "");
					}
					// System.out.println("*******************"+(i+1)+"*******************次");
					tmnlSet.remove(r.getTmnlAssetNo());
					// System.out.println(r);
					// log.debug("返回的response对象" + r.getDbDatas());
					addResponseList(dataFetchAction.parseDataFetch(rs));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			Dispatcher.removeTaskIds(ls);
		}
		System.out.println("返回的pn,size:" + pns.size() + ",值为" + pns.toString());
		// 对最后都没有返回结果的response设定状态
		List<Response> rs = new ArrayList();
		logger.debug("召测结束：没有返回结果的终端为:" + tmnlSet);
		for (String tmnlNo : tmnlSet) {
			DataFetchResponse d = new DataFetchResponse("out", -100,
					"在规定的时间内缓存没有返回");
			d.setTmnlAssetNo(tmnlNo);
			rs.add(d);
			log.debug("****************************终端地址"+ tmnlNo+ "未能在规定的时间内返回结果，或者终端无应答******************************************");
		}
		addResponseList(dataFetchAction.parseDataFetch(rs));
	}

	// ------------------晓程规约处理分支by sungang---2010-10-16----------start--------

	@SuppressWarnings("unchecked")
	public void getAllResponseOfPL(int second, int len) {
		second = second - 3;
		HashSet<String> pns = new HashSet<String>();
		addResponseList(dataFetchAction.parseDataFetch(td.getOfflineResponse()));
		List ls = ifuture.getTaskIDs();
		logger.debug("数据召测等待时间为" + second);
		HashSet<Integer> nowTaskId = new HashSet<Integer>(ls);
		try {
			for (int i = 0; i < len; i++) {
				// int waitTime = second / ls.size();
				if (nowTaskId.size() == 0) {
					logger.debug("召测数据等待结束");
					break;
				}
				Response r = null;
				if (len < 300) {
					r = ifuture.getResponse(second * 1000 / len);
				} else {
					r = ifuture.getResponse(second * 1000 *3 / len);
				}
				if (null != r) {
					// if (r.isContinue()) {
					// Dispatcher.removeId(r.getTaskId());
					// nowTaskId.remove(r.getTaskId());
					// }
					logger.debug("任务号" + r.getTaskId() + "对应终端"
							+ r.getTerminalAddr() + "已经返回结果");
					List<Response> rs = new ArrayList<Response>();
					rs.add(r);
					List<DbData> dbs = r.getDbDatas();
					for (DbData d : dbs) {
						// if(d.getPn()==108){
						// System.out.println("返回结果108");
						// }
						pns.add(d.getPn() + "");
					}
					// System.out.println("*******************"+(i+1)+"*******************次");
					tmnlSet.remove(r.getTmnlAssetNo());
					// System.out.println(r);
					// log.debug("返回的response对象" + r.getDbDatas());
					addResponseList(dataFetchAction.parseDataFetch(rs));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			Dispatcher.removeTaskIds(ls);
		}
		System.out.println("返回的pn,size:" + pns.size() + ",值为" + pns.toString());
		// 对最后都没有返回结果的response设定状态
		List<Response> rs = new ArrayList();
		logger.debug("召测结束：没有返回结果的终端为:" + tmnlSet);
		for (String tmnlNo : tmnlSet) {
			DataFetchResponse d = new DataFetchResponse("out", -100,
					"在规定的时间内缓存没有返回");
			d.setTmnlAssetNo(tmnlNo);
			rs.add(d);
			log
					.debug("****************************终端地址"
							+ tmnlNo
							+ "未能在规定的时间内返回结果，或者终端无应答******************************************");
		}
		addResponseList(dataFetchAction.parseDataFetch(rs));
	}

	// ----------盘龙晓程规约电表通断by sungang 2010-11-07-----start------------
	public List<Response> getMeterAllResponse(int second) {
		second = second - 3;
		List<Response> result = new ArrayList<Response>();
		List ls = ifuture.getTaskIDs();
		logger.debug("电表通断等待时间为" + second);
		for (int i = 0; i < ls.size(); i++) {
			int waitTime = second / ls.size();
			Response r = ifuture.getResponse(waitTime * 1000);
			if (null != r) {
				result.add(r);
				log.debug("返回的response对象" + r);
			}
		}
		return result;
	}

	// ------------add by sungang---2010-11-07------end------------------
	/**
	 * 填充原始的response对象到session
	 * 
	 * @param resp
	 */
	@SuppressWarnings("unchecked")
	public void addResponseData(Response resp) {
		if (key.startsWith(prefix)) {
			return;
		}
		if (null == resp) {
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
			m.put(key, new ArrayList(30));
		}
		List get = (List) m.get(key);
		get.add(resp);
	}

	/**
	 * 填充原始的response列表对象到session
	 * 
	 * @param resp
	 */
	@SuppressWarnings("unchecked")
	public void addResponseList(List rs) {
		if (key.startsWith(prefix)) {
			return;
		}
		if (null == rs || rs.isEmpty()) {
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
			m.put(key, new ArrayList(30));
		}
		List get = (List) m.get(key);
		get.addAll(rs);
	}

}
