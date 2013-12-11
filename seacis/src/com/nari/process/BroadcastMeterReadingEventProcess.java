package com.nari.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EventBean;
import com.nari.coherence.TaskDeal;
import com.nari.eventbean.BackFeedEvent;
import com.nari.eventbean.BroadcastMeterReadingEvent;
import com.nari.eventbean.SendTmnlMpParamEvent;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.service.SynDataForMarketingManager;

/**
 * 广播抄表
 */
@SuppressWarnings("unused")
public class BroadcastMeterReadingEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(BroadcastMeterReadingEventProcess.class);
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public BroadcastMeterReadingEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents=newEvents;
		this.oldEvents=oldEvents;
	}
	
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	TaskDeal taskDeal = new TaskDeal();
	
	public void run() {
		log.info("RelayMeterReadingEventProcess begin...");
		
		String appNo = (String) newEvents[0].get("appNo");
		String tmnlAssetNo = (String) newEvents[0].get("tmnlAssetNo");
		String ameterAssetNo = (String) newEvents[0].get("ameterAssetNo");
		String mpSn = (String) newEvents[0].get("mpSn");
		Long debugId = (Long) newEvents[0].get("debugId");
		
		String Status = null;
		//终端上不止一个表计，不能进行广播抄表
		
		//更新测试状态为：广播抄表开始
		marketTerminalConfigManager.updateTestStatue(appNo, Globals.CSJD_ZJCBST);
		Status = roadcastMeterReading(tmnlAssetNo, mpSn);
		
		if("3".equals(Status)){
			//更新测试状态为：广播抄表成功
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.CSJD_GYDZCG);
			//触发事件：下发终端参数
			//Constant.sendEvent(new SendTmnlMpParamEvent());//TODU
		}else{
			//更新测试状态为：广播抄表失败
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.CSJD_GYDZSB);
			log.info("广播抄表失败, tmnlAssetNo = " + tmnlAssetNo);
			//反馈营销
			Constant.sendEvent(new BackFeedEvent(appNo, debugId));
			return;
		}
	}

	/**
	 * 广播抄表
	 * 
	 * @param tmnlAssetNo
	 * @param mpSn
	 * @throws Exception
	 */
	private String roadcastMeterReading(String tmnlAssetNo, String mpSn) {
		//终端资产号集合
		List<String> tmnlAssetNos = new ArrayList<String>();
		tmnlAssetNos.add(tmnlAssetNo);
		// 参数项设置
		List<ParamItem> params = new ArrayList<ParamItem>();
			ParamItem pitem = new ParamItem();
			pitem.setFn((short) Integer.parseInt("33"));
			pitem.setPoint((short) Integer.parseInt(mpSn));
		params.add(pitem);
		
		// 带上表地址/规约/终端485端口号/广播地址进行中继抄表 TODU(?)表地址/规约/广播地址 如何传参？
		
		List<Response> respList = null;
		this.taskDeal.qstTermParamTaskResult(tmnlAssetNos,
				FrontConstant.QUERY_PARAMS, params, 20);
		respList = taskDeal.getAllResponse(20);
		//抄表状态
		String Status = String.valueOf(respList.get(0).getTaskStatus());
		return Status;
	}
}