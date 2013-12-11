package com.nari.coherence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.basicdata.ATmnlRealTime;
import com.nari.ami.front.taskschedule.TaskHandle;
import com.nari.fe.commdefine.param.TaskTermRelations;
import com.nari.fe.commdefine.task.EventItem;
import com.nari.fe.commdefine.task.FuncAggregationImpl;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.IFuncAggregation;
import com.nari.fe.commdefine.task.MeterInfo;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.util.DataFetchResponse;

/**
 * @author 陈建国
 * 
 * @创建时间:2010-1-20 下午02:27:55
 * 
 * @类描述:
 * 
 *       前置返回的Response状态有5种： 0: 在规定的时间内缓存没有返回 1：终端不在线 3：成功 4：失败
 * 
 * 
 *       还有就是Response 为 null 终端无应答
 * 
 */
public class TaskDeal {

	private static final Logger log = Logger.getLogger(TaskDeal.class);
	private List<Response> offlineResponse = new ArrayList<Response>();
	private List<String> onlineAssetNo;
	private List<TaskTmnlPnRelation> totalttr = new ArrayList<TaskTmnlPnRelation>();
	private IFuture iFuture = new IFuture();
	private int count=0;
	public static TaskHandle getTaskHandle() {
//		System.out.println("----front----------");
//		System.out.println(TaskHandle.getSharedInstance(""));
		return TaskHandle.getSharedInstance("");
		
	}

	public int getCount() {
		if(count==0){
			return 1;
		}
		return count;
	}

	public List<Response> getOfflineResponse() {
		return offlineResponse;
	}

	public List<String> getOnlineAssetNo() {
		return onlineAssetNo;
	}

	public List<TaskTmnlPnRelation> getTotalttr() {
		return totalttr;
	}

	public IFuture getiFuture() {
		return iFuture;
	}

	public static Response getResponse(long taskId) {
		Response res = null;
		try {
			res = TaskDeal.getTaskHandle().getResponse(taskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @desc Web应用服务器与缓存服务器通信状态
	 * @return true:正常,false：中断
	 */
	public static boolean isCacheRunning() {
		try {
			StartListern.isRequireConnectCoherence();
			return getTaskHandle().isCacheRunning();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @desc 前置集群服务通信状态
	 * @return true:正常,false：中断
	 */
	public static boolean isFrontAlive() {
		try {
			StartListern.isRequireConnectCoherence();
//			System.out.println(getTaskHandle().isFrontAlive());
			return getTaskHandle().isFrontAlive();
//			return true;
//			if(flat){
//				return true;
//			}
//			StartListern.init();
//			return getTaskHandle().isFrontAlive();
		} catch (Exception e) {
			StartListern.init();
			e.printStackTrace();
//			try {
//				return getTaskHandle().isFrontAlive();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
		}
		return false;
	}

	/**
	 * @desc 剔除不在线终端 ,参数下发和控制命令的时候需要返回pn
	 * @param tmnlAssetNos
	 * 
	 */
	public void getOnlineAssetNo(List<String> tmnlAssetNos, short pn) {
		onlineAssetNo = new ArrayList<String>();
		for (int i = 0; i < tmnlAssetNos.size(); i++) {
			ATmnlRealTime tmnlStatus = getTaskHandle().getTmnlStatus(
					tmnlAssetNos.get(i));
			if (null == tmnlStatus || !tmnlStatus.getIsOnline()) {
				Response response = new Response();

				response.setTmnlAssetNo(tmnlAssetNos.get(i));
				response.setTaskStatus((short) 1);

				ResponseItem reItem = new ResponseItem();
				reItem.setPn(pn);
				List<ResponseItem> ResponseItems = new ArrayList<ResponseItem>();
				ResponseItems.add(reItem);
				response.setResponseItems(ResponseItems);

				this.offlineResponse.add(response);
			} else {
				this.onlineAssetNo.add(tmnlAssetNos.get(i));
			}
		}
	}

	public void setDataFetchOfflineResponse(List<DataFetchResponse> offlineResponse) {
		this.offlineResponse.clear() ;
		this.offlineResponse.addAll(offlineResponse);
	}

	/**
	 * @desc 剔除不在线终端 不需要返回 pn
	 * @param tmnlAssetNos
	 * 
	 */
	// public void getOnlineAssetNo(List<String> tmnlAssetNos){
	// onlineAssetNo = new ArrayList<String>();
	// for(int i = 0 ; i < tmnlAssetNos.size(); i++){
	// ATmnlRealTime tmnlStatus =
	// getTaskHandle().getTmnlStatus(tmnlAssetNos.get(i));
	// if(null == tmnlStatus || !tmnlStatus.getIsOnline()){
	// Response response = new Response();
	// response.setTmnlAssetNo(tmnlAssetNos.get(i));
	// response.setTaskStatus((short) 1);
	//				
	// this.offlineResponse.add(response);
	// }else{
	// this.onlineAssetNo.add(tmnlAssetNos.get(i));
	// }
	// }
	// }
	/**
	 * @desc 获取前置机机器名
	 * @return
	 */
	public static String getName() {
		FrontScheme frontScheme = new FrontScheme();
		try {
			log.debug("frontScheme.getName():" + frontScheme.getName());
			return frontScheme.getName();
		} catch (IOException e) {

			log.info("没有找到文件！！");
		}
		return "";
	}

	/**
	 * @desc 实时数据召测
	 * @param tmnlAssetNos
	 * @param realDataItems
	 * @param second
	 * @return
	 */
	public List<Response> callRealData(List<String> tmnlAssetNos,
			List<RealDataItem> realDataItems, int second) {
		List<Response> rtnList = new ArrayList<Response>();

		StartListern.isRequireConnectCoherence();

		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		this.getOnlineAssetNo(tmnlAssetNos, realDataItems.get(0).getPoint());
		rtnList.addAll(offlineResponse);
		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callRealData(onlineAssetNo, realDataItems);
				} else {
					ttr = aggreg.callRealData(onlineAssetNo, realDataItems,
							TaskDeal.getName());
				}

				long retTaskId = -1;
				IFuture iFuture = new IFuture();
				int i = 0;
				for (TaskTermRelations rel : ttr) {
					retTaskId = rel.getTaskId();
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
					}
				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
					// rtnList = iFuture.getRtnList(second,ttr);
					rtnList.addAll(iFuture.getRtnList(second, ttr));
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

		return rtnList;
	}

	/**
	 * @desc 实时数据召测
	 * @param tmnlAssetNos
	 * @param realDataItems
	 * @return
	 */
	public void callRealData(List<String> tmnlAssetNos,
			List<RealDataItem> realDataItems) {
		count=realDataItems.size();
		int jj=count/20;
		count=count%20==0?jj:(jj+1);
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		this.getOnlineAssetNo(tmnlAssetNos, realDataItems.get(0).getPoint());
		// StartListern.isRequireConnectCoherence();

		System.out.println("realDataItems:=============="
				+ realDataItems.toString());
		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callRealData(onlineAssetNo, realDataItems);
				} else {
					ttr = aggreg.callRealData(onlineAssetNo, realDataItems,
							TaskDeal.getName());
				}
				System.out.println("调用完成！");
				long retTaskId = -1;
				int i = 0;
				for (TaskTermRelations rel : ttr) {
					TaskTmnlPnRelation taskTmnlPnRelation = new TaskTmnlPnRelation();
					retTaskId = rel.getTaskId();
//					System.out.println("taskId:-------------------------------"
//							+ rel.getTaskId());
//					System.out.println("终端地址：----------------------------"
//							+ rel.getTmnlAssetNo());
					log.debug("实时数据召测:任务号taskId:-------------------------------" + rel.getTaskId()+
							",对应终端资产号内码：----------------------------"+ rel.getTmnlAssetNo());
					//log.debug("终端地址：----------------------------"+ rel.getTmnlAssetNo());
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
						taskTmnlPnRelation.setTtr(rel);
						taskTmnlPnRelation.setPn(realDataItems.get(0)
								.getPoint());
						totalttr.add(taskTmnlPnRelation);
					}
				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

	}

	/**
	 * @desc 历史数据召测
	 * @param tmnlAssetNos
	 * @param realDataItems
	 * @param second
	 * @return
	 */
	public List<Response> callHisData(List<String> tmnlAssetNos,
			List<HisDataItem> hisDataItems, int second) {
		List<Response> rtnList = new ArrayList<Response>();
		StartListern.isRequireConnectCoherence();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, hisDataItems.get(0).getPoint());
		rtnList.addAll(offlineResponse);
		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callHisData(onlineAssetNo, hisDataItems);
				} else {
					ttr = aggreg.callHisData(onlineAssetNo, hisDataItems,
							TaskDeal.getName());
				}

				long retTaskId = -1;
				IFuture iFuture = new IFuture();
				int i = 0;
				for (TaskTermRelations rel : ttr) {
					retTaskId = rel.getTaskId();

					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
					}

				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
					rtnList.addAll(iFuture.getRtnList(second, ttr));
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

		return rtnList;
	}

	/**
	 * @desc 历史数据召测
	 * @param tmnlAssetNos
	 * @param realDataItems
	 * @param second
	 * @return
	 */
	public void callHisData(List<String> tmnlAssetNos,
			List<HisDataItem> hisDataItems) {
		count=hisDataItems.size();
		int jj=count/20;
		count=count%20==0?jj:(jj+1);
		StartListern.isRequireConnectCoherence();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, hisDataItems.get(0).getPoint());

		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callHisData(onlineAssetNo, hisDataItems);
				} else {
					ttr = aggreg.callHisData(onlineAssetNo, hisDataItems,
							TaskDeal.getName());
				}

				long retTaskId = -1;
				int i = 0;
				for (TaskTermRelations rel : ttr) {

					TaskTmnlPnRelation taskTmnlPnRelation = new TaskTmnlPnRelation();
					log.debug("历史数据召测：任务号taskId:-------------------------------" + rel.getTaskId()+
							",对应终端资产号内码：----------------------------"+ rel.getTmnlAssetNo());
					retTaskId = rel.getTaskId();
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
						taskTmnlPnRelation.setTtr(rel);
						taskTmnlPnRelation
								.setPn(hisDataItems.get(0).getPoint());
						totalttr.add(taskTmnlPnRelation);
					}
				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

	}

	/**
	 * @desc 参数设置与参数召测接口
	 * @param tmnlAssetNos
	 *            终端资产编号
	 * @param funccode
	 *            功能块
	 * @param paramItems
	 * @param second
	 *            任务执行时间
	 * @return
	 */
	public List<Response> qstTermParamTaskResult(List<String> tmnlAssetNos,
			short funccode, List<ParamItem> paramItems, int second) {

		List<Response> rtnList = new ArrayList<Response>();
		StartListern.isRequireConnectCoherence();

		// 判断与Coherence通信是否中断
		// isCacheRunning true：正常,false:中断
		// boolean isRunning = false;
		// try {
		// isRunning = getTaskHandle().isCacheRunning();
		// } catch (Exception e1) {
		// log.info("Web应用服务器与缓存服务器通信中断！");
		// e1.printStackTrace();
		// }
		//		
		// if(!isRunning){
		// Response response = new Response();
		// response.setTaskStatus((short)5);
		// rtnList.add(response);
		// return rtnList;
		// }
		//		
		// //判断与前置机通信是否中断
		// //isFrontAlive true：正常,false:中断
		// boolean isAlive = false;
		//		
		// try {
		// isAlive = getTaskHandle().isFrontAlive();
		// } catch (Exception e1) {
		// log.info("前置集群服务停止！");
		// e1.printStackTrace();
		// }
		//		
		// if(!isAlive){
		// Response response = new Response();
		// response.setTaskStatus((short)6);
		// rtnList.add(response);
		// return rtnList;
		// }

		IStatement statement = CoherenceStatement.getSharedInstance();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, paramItems.get(0).getPoint());
		rtnList.addAll(offlineResponse);
		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode,
							paramItems);
				} else {
					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode,
							paramItems, TaskDeal.getName());
				}

				long retTaskId = -1;
				IFuture iFuture = new IFuture();

				int i = 0;
				for (TaskTermRelations rel : ttr) {
					retTaskId = rel.getTaskId();

					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
					}

				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
					rtnList.addAll(iFuture.getRtnList(second, ttr));
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}

			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

		log.info("返回Response:" + rtnList.toString());
		return rtnList;
	}

	/**
	 * @desc 参数设置与参数召测接口
	 * @param tmnlAssetNos
	 *            终端资产编号
	 * @param funccode
	 *            功能块
	 * @param paramItems
	 * @param second
	 *            任务执行时间
	 * @return
	 */
	public void qstTermParamTaskResult(List<String> tmnlAssetNos,
			short funccode, List<ParamItem> paramItems) {
		StartListern.isRequireConnectCoherence();
		IStatement statement = CoherenceStatement.getSharedInstance();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, paramItems.get(0).getPoint());

		log.debug("funccode:----------------" + funccode);
		log.debug("paramItems:--------------" + paramItems.toString());

		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {

					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode,
							paramItems);
				} else {
					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode,
							paramItems, TaskDeal.getName());
				}

				long retTaskId = -1;

				int i = 0;
				for (TaskTermRelations rel : ttr) {
					TaskTmnlPnRelation taskTmnlPnRelation = new TaskTmnlPnRelation();
					retTaskId = rel.getTaskId();
					log.debug("taskId:-------------------------------"
							+ rel.getTaskId());
					log.debug("终端地址：----------------------------"
							+ rel.getTmnlAssetNo());
					log.debug("paramItems :=================="
							+ paramItems.get(0).getPoint());
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
						taskTmnlPnRelation.setTtr(rel);
						taskTmnlPnRelation.setPn(paramItems.get(0).getPoint());
						totalttr.add(taskTmnlPnRelation);
					}
				}

			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

	}

	/**
	 * @desc
	 * @param iFuture
	 * @param second
	 * @return
	 */
	public List<Response> getAllResponse(int second) {
		second = second - 3;
		if (totalttr.size() > 0) {
			Dispatcher.sendMessage(iFuture);
			log.debug("totalttr:     " + totalttr.size());
			offlineResponse.addAll(iFuture.getAllRtnList(second, totalttr));
		}
		System.out.println("offlineResponse======================="
				+ offlineResponse.toString());
		// log.info("offlineResponse=======================" +
		// offlineResponse.toString());
		// System.out.println("保有返回id：======================"
		// +totalttr.get(0).getTtr().getTaskId());
		// System.out.println("保有返回response：======================"
		// +TaskDeal.getResponse(totalttr.get(0).getTtr().getTaskId()).toString());
		return offlineResponse;
	}

	/**
	 * @desc 召唤事件数据接口
	 * @param tmnlAssetNos
	 *            终端资产编号
	 * @param eventItems
	 * @param second
	 *            任务执行时间
	 * @return
	 */
	public List<Response> callEvent(List<String> tmnlAssetNos,
			List<EventItem> eventItems, int second) {
		List<Response> rtnList = new ArrayList<Response>();
		StartListern.isRequireConnectCoherence();
		IStatement statement = CoherenceStatement.getSharedInstance();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, eventItems.get(0).getPoint());
		rtnList.addAll(offlineResponse);
		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callEvent(onlineAssetNo, eventItems);
				} else {
					ttr = aggreg.callEvent(onlineAssetNo, eventItems, TaskDeal
							.getName());
				}

				long retTaskId = -1;
				IFuture iFuture = new IFuture();

				int i = 0;
				for (TaskTermRelations rel : ttr) {
					retTaskId = rel.getTaskId();

					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
					}

				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
					rtnList.addAll(iFuture.getRtnList(second, ttr));
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

		return rtnList;
	}

	/**
	 * @desc 召唤事件数据接口
	 * @param tmnlAssetNos
	 *            终端资产编号
	 * @param eventItems
	 * @param second
	 *            任务执行时间
	 * @return
	 */
	public void callEvent(List<String> tmnlAssetNos, List<EventItem> eventItems) {
		StartListern.isRequireConnectCoherence();
		IStatement statement = CoherenceStatement.getSharedInstance();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		getOnlineAssetNo(tmnlAssetNos, eventItems.get(0).getPoint());

		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {
					ttr = aggreg.callEvent(onlineAssetNo, eventItems);
				} else {
					ttr = aggreg.callEvent(onlineAssetNo, eventItems, TaskDeal
							.getName());
				}

				long retTaskId = -1;
				int i = 0;
				for (TaskTermRelations rel : ttr) {
					TaskTmnlPnRelation taskTmnlPnRelation = new TaskTmnlPnRelation();
					retTaskId = rel.getTaskId();
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"
								+ ttr.get(i).getTmnlAssetNo()
								+ " 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
						taskTmnlPnRelation.setTtr(rel);
						taskTmnlPnRelation.setPn(eventItems.get(0).getPoint());
						totalttr.add(taskTmnlPnRelation);
					}

				}

				if (i > 0) {
					Dispatcher.sendMessage(iFuture);
				} else {
					log
							.info("---------------------全部终端无应答！---------------------------");
				}
			} catch (Exception e) {
				log
						.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}

		}

	}
	/**
	 *  电能表控制接口
	 * @param tmnlAssetNos 终端资产编号
	 * @param funccode 功能块
	 * @param paramItems
	 * @param second 任务执行时间
	 * @return
	 */
	public void qstTermParamTaskResult(List<String> tmnlAssetNos,
			short funccode, List<ParamItem> paramItems, List<MeterInfo> meterList) {
		StartListern.isRequireConnectCoherence();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		
		getOnlineAssetNo(tmnlAssetNos, paramItems.get(0).getPoint());

		log.debug("funccode:----------------" + funccode);
		log.debug("paramItems:--------------" + paramItems.toString());

		if (onlineAssetNo.size() > 0) {
			try {
				if ("".equals(TaskDeal.getName()) || null == TaskDeal.getName()) {

					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode, paramItems, meterList);
				} else {
					ttr = aggreg.qstTermParamTask(onlineAssetNo, funccode, paramItems, meterList, 

TaskDeal.getName());
				}

				long retTaskId = -1;

				int i = 0;
				for (TaskTermRelations rel : ttr) {
					TaskTmnlPnRelation taskTmnlPnRelation = new TaskTmnlPnRelation();
					retTaskId = rel.getTaskId();
					log.debug("taskId:-------------------------------" + rel.getTaskId());
					log.debug("终端地址：----------------------------"+ rel.getTmnlAssetNo());
					log.debug("paramItems :=================="+ paramItems.get(0).getPoint());
					if (retTaskId == -1) {
						log.info("-------------------任务终端资产号:"+ ttr.get(i).getTmnlAssetNo() + 

" 终端无应答！---------------------------");
					} else {
						i += 1;
						iFuture.addID(retTaskId);
						taskTmnlPnRelation.setTtr(rel);
						taskTmnlPnRelation.setPn(paramItems.get(0).getPoint());
						totalttr.add(taskTmnlPnRelation);
					}
				}

			} catch (Exception e) {
				log.info("---------------------终端无应答！---------------------------");
				e.printStackTrace();
			}
		}
	}

}
