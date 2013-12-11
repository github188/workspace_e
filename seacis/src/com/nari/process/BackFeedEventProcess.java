package com.nari.process;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EventBean;
import com.nari.global.Constant;
import com.nari.global.Globals;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.XMLSwitchUtil;
import com.nari.webserviceclient.MarketingWSClient;

/**
 * 反馈营销
 */
@SuppressWarnings("unused")
public class BackFeedEventProcess implements Runnable{
	
	private static final Log log = LogFactory.getLog(BackFeedEventProcess.class);
	private MarketTerminalConfigManager marketTerminalConfigManager =  null;
	private MarketingWSClient marketingWSClient = null;
	
	private EventBean[] newEvents;
	private EventBean[] oldEvents;
	public BackFeedEventProcess(EventBean[] newEvents,EventBean[] oldEvents){
		this.newEvents = newEvents;
		this.oldEvents = oldEvents;
	}
	
	public void run() {
		log.info("BackFeedEventProcess begin...");
		marketTerminalConfigManager = (MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		marketingWSClient = (MarketingWSClient)Constant.getCtx().getBean("marketingWSClient");
		
		String appNo = (String) newEvents[0].get("appNo");
		Long debugId = (Long) newEvents[0].get("debugId");
		
		//根据调试明细判断调试结果是否成功
		List<?> listITmnlDebug = marketTerminalConfigManager.getITmnlDebug(appNo, Globals.DEBUG_END);
		if(listITmnlDebug == null){
			//更新调试状态为测试失败
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_FAILED);
		}else{
			//更新调试状态为测试成功
			marketTerminalConfigManager.updateTestStatue(appNo, Globals.DEBUG_STATUE_SUCCESS);
		}
		
		List<?> list = marketTerminalConfigManager.getRTmnlDebugByDebugId(debugId);
		String terminalId = null;
		String debugTime = null;
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		if(list != null && list.size() > 0) {
			Map<?, ?> mapDebug = (Map<?, ?>) list.get(0);
			terminalId = (String) mapDebug.get("TERMINAL_ID");
			debugTime = df.format(mapDebug.get("DEBUG_TIME"));;
		}else{
			log.info("该调试信息不存在，debugId：" + debugId);
		}
		// 发送反馈信息至营销系统 
		XMLSwitchUtil xmlSwitchUtil = new XMLSwitchUtil();
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("TMNL_DEBUG_ID", debugId.toString());
		responseMap.put("TERMINAL_ID", terminalId);
		responseMap.put("DEBUG_TIME", debugTime);
		
		String xmlString = xmlSwitchUtil.switchMapToXMLClient(responseMap, "WS_TMNL_DEBUG_INFO");
		log.info("response msg:" + xmlString);
		
		int resultResponse = marketingWSClient.backFeedTmnlDebugInfo(xmlString);
		// 结果信息判断 
		// 如果是网络问题，重发三次 
		int countRepeat = 0;
		while (countRepeat < 3 && resultResponse < 0) {
			resultResponse = marketingWSClient.backFeedTmnlDebugInfo(xmlString);
			// 结果信息判断 
			countRepeat++;
			log.info("调试编号:" + debugId + " 终端局号:" + terminalId + " 发送反馈信息失败,重发第"
					+ countRepeat + "次!");
		}
	}
}