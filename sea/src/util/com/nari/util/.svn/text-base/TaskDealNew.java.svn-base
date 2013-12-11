package com.nari.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.coherence.Dispatcher;
import com.nari.coherence.IFuture;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.param.TaskTermRelations;
import com.nari.fe.commdefine.task.EventItem;
import com.nari.fe.commdefine.task.FuncAggregationImpl;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.IFuncAggregation;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;

public class TaskDealNew {
	private static final Logger log = Logger.getLogger(TaskDealNew.class);
	/**
	 * @desc 实时数据召测
	 * @param tmnlAssetNos
	 * @param realDataItems
	 * @param second
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Response> callRealData(List<String> tmnlAssetNos,List<RealDataItem> realDataItems,int second){
		List<Response> rtnList = new ArrayList<Response>();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List <TaskTermRelations> ttr = null;
		
		try {
			for (String no : tmnlAssetNos) {
				for(RealDataItem r:realDataItems){
					List nos=new ArrayList();
					List rs=new ArrayList();
					nos.add(no);
					rs.add(r);
					ttr = aggreg.callRealData(nos, rs, "ydfrone1");
					long retTaskId = -1;
					IFuture iFuture = new IFuture();
					int i =0;
					for (TaskTermRelations rel : ttr) {
						retTaskId = rel.getTaskId();
						if(retTaskId == -1){
							log.info("-------------------任务终端资产号:" +ttr.get(i).getTmnlAssetNo() +" 终端无应答！---------------------------");
						}else{
							i += 1;
							iFuture.addID(retTaskId);
						}
					}

					if(i > 0){
						Dispatcher.sendMessage(iFuture);
					rtnList.addAll(iFuture.getRtnList(second,ttr));
					}else{
						log.info("---------------------全部终端无应答！---------------------------");
					}
				}
			}
			
		} catch (Exception e) {
			log.info("---------------------终端无应答！---------------------------");
			e.printStackTrace();
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
	@SuppressWarnings("unchecked")
	public static List<Response> callHisData(List<String> tmnlAssetNos,List<HisDataItem> hisDataItems,int second){
		List<Response> rtnList = new ArrayList<Response>();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List <TaskTermRelations> ttr = null;
		
		try {
			for (String no : tmnlAssetNos) {
				for(HisDataItem h:hisDataItems){
					List nos=new ArrayList();
					List hs=new ArrayList();
					nos.add(no);
					hs.add(h);
					ttr = aggreg.callHisData(nos, hs, "ydfrone1");
					long retTaskId = -1;
					IFuture iFuture = new IFuture();
					int i =0;
					for (TaskTermRelations rel : ttr) {
						retTaskId = rel.getTaskId();
						if(retTaskId == -1){
							log.info("-------------------任务终端资产号:" +ttr.get(i).getTmnlAssetNo() +" 终端无应答！---------------------------");
						}else{
							i += 1;
							iFuture.addID(retTaskId);
						}
					}

					if(i > 0){
						Dispatcher.sendMessage(iFuture);
						rtnList.addAll(iFuture.getRtnList(second,ttr));
					}else{
						log.info("---------------------全部终端无应答！---------------------------");
					}
				}
				}
			
		} catch (Exception e) {
			log.info("---------------------终端无应答！---------------------------");
			e.printStackTrace();
		}

		return rtnList;
	}
	/**
	 * @desc 召唤事件数据接口
	 * @param tmnlAssetNos 终端资产编号
	 * @param eventItems
	 * @param second 任务执行时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Response> callEvent(List<String> tmnlAssetNos,List<EventItem> eventItems,int second){
		List<Response> rtnList = new ArrayList<Response>();

		IStatement statement = CoherenceStatement.getSharedInstance();
		IFuncAggregation aggreg = new FuncAggregationImpl();
		List<TaskTermRelations> ttr = null;
		
		try {
			for (String no : tmnlAssetNos) {
				for(EventItem e:eventItems){
					List nos=new ArrayList();
					List es=new ArrayList();
					nos.add(no);
					es.add(e);
					ttr = aggreg.callEvent(nos, es, "ydfrone1");
					long retTaskId = -1;
					IFuture iFuture = new IFuture();
					
					int i =0;
					for (TaskTermRelations rel : ttr) {
						retTaskId = rel.getTaskId();
						if(retTaskId == -1){
							log.info("-------------------任务终端资产号:" +ttr.get(i).getTmnlAssetNo() +" 终端无应答！---------------------------");
						}else{
							i += 1;
							iFuture.addID(retTaskId);
						}
					}

					if(i > 0){
						Dispatcher.sendMessage(iFuture);
						rtnList.addAll(iFuture.getRtnList(second,ttr));
					}else{
						log.info("---------------------全部终端无应答！---------------------------");
					}
				}
				}
			
		} catch (Exception e) {
			log.info("---------------------终端无应答！---------------------------");
			e.printStackTrace();
		}
		
		return rtnList;
	}
}
