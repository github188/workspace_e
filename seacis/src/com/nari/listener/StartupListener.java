package com.nari.listener;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.TimerControlEvent;
import com.nari.eventbean.BackFeedEvent;
import com.nari.eventbean.CrCyEvent;
import com.nari.eventbean.PrepayCtrlEvent;
import com.nari.eventbean.RelayMeterReadingEvent;
import com.nari.eventbean.SendFrmTaskParamEvent;
import com.nari.eventbean.SendFrmTmnlParamEvent;
import com.nari.eventbean.SendTaskParamEvent;
import com.nari.eventbean.SendTmnlMpParamEvent;
import com.nari.eventbean.SendTmnlParamEvent;
import com.nari.eventbean.SendTmnlParamOtherEvent;
import com.nari.eventbean.StopCtrlEvent;
import com.nari.eventbean.TriggerTestEvent;
import com.nari.eventbean.hb.HBRelayMeterReadingEvent;
import com.nari.eventbean.hb.HBSendFrmTaskParamEvent;
import com.nari.eventbean.hb.HBSendFrmTmnlParamEvent;
import com.nari.eventbean.hb.HBSendTaskParamEvent;
import com.nari.eventbean.hb.HBSendTmnlMpParamEvent;
import com.nari.eventbean.hb.HBSendTmnlParamEvent;
import com.nari.eventbean.hb.HBSendTmnlParamOtherEvent;
import com.nari.eventbean.hb.HBTriggerTestEvent;
import com.nari.global.Constant;
import com.nari.monitor.BackFeedEventMonitor;
import com.nari.monitor.CrCyEventMonitor;
import com.nari.monitor.PrepayCtrlEventMonitor;
import com.nari.monitor.RelayMeterReadingEventMonitor;
import com.nari.monitor.SendFrmTaskParamEventMonitor;
import com.nari.monitor.SendFrmTmnlParamEventMonitor;
import com.nari.monitor.SendTaskParamEventMonitor;
import com.nari.monitor.SendTmnlMpParamEventMonitor;
import com.nari.monitor.SendTmnlParamEventMonitor;
import com.nari.monitor.SendTmnlParamOtherEventMonitor;
import com.nari.monitor.StopCtrlEventMonitor;
import com.nari.monitor.TriggerTestEventMonitor;
import com.nari.monitor.hb.HBRelayMeterReadingEventMonitor;
import com.nari.monitor.hb.HBSendFrmTaskParamEventMonitor;
import com.nari.monitor.hb.HBSendFrmTmnlParamEventMonitor;
import com.nari.monitor.hb.HBSendTaskParamEventMonitor;
import com.nari.monitor.hb.HBSendTmnlMpParamEventMonitor;
import com.nari.monitor.hb.HBSendTmnlParamEventMonitor;
import com.nari.monitor.hb.HBSendTmnlParamOtherEventMonitor;
import com.nari.monitor.hb.HBTriggerTestEventMonitor;
import com.nari.process.BackFeedEventProcess;
import com.nari.process.CrCyEventProcess;
import com.nari.process.PrepayCtrlEventProcess;
import com.nari.process.RelayMeterReadingEventProcess;
import com.nari.process.SendFrmTaskParamEventProcess;
import com.nari.process.SendFrmTmnlParamEventProcess;
import com.nari.process.SendTaskParamEventProcess;
import com.nari.process.SendTmnlMpParamEventProcess;
import com.nari.process.SendTmnlParamEventProcess;
import com.nari.process.SendTmnlParamOtherEventProcess;
import com.nari.process.StopCtrlEventProcess;
import com.nari.process.TriggerTestEventProcess;
import com.nari.process.hb.HBRelayMeterReadingEventProcess;
import com.nari.process.hb.HBSendFrmTaskParamEventProcess;
import com.nari.process.hb.HBSendFrmTmnlParamEventProcess;
import com.nari.process.hb.HBSendTaskParamEventProcess;
import com.nari.process.hb.HBSendTmnlMpParamEventProcess;
import com.nari.process.hb.HBSendTmnlParamEventProcess;
import com.nari.process.hb.HBSendTmnlParamOtherEventProcess;
import com.nari.process.hb.HBTriggerTestEventProcess;

public class StartupListener extends ContextLoaderListener implements ServletContextListener {
	private EPServiceProvider epService;
	private static final Log LOG = LogFactory.getLog(StartupListener.class);
	public void contextInitialized(ServletContextEvent event) {
		LOG.info("initializing context...");
		ServletContext context = event.getServletContext();
		//初始化线程池
		LOG.info("initializing threadpool...");
		int corePoolSize = Integer.parseInt(context.getInitParameter("corePoolSize"));
		int maximumPoolSize = Integer.parseInt(context.getInitParameter("maximumPoolSize"));
		int keepAliveTime = Integer.parseInt(context.getInitParameter("keepAliveTime"));
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		LOG.info(".performTest Starting thread pool, threads=" + 3);
		Constant.setPool(pool);
		//init esper
		LOG.info("initializing esper...");
		Configuration configuration = new Configuration();
		configuration.getEngineDefaults().getThreading().setListenerDispatchPreserveOrder(false);
		//配置这块
		configuration.addEventType("PrepayCtrl",PrepayCtrlEvent.class.getName());
		configuration.addEventType("CrCy",CrCyEvent.class.getName());
		configuration.addEventType("StopCtrl",StopCtrlEvent.class.getName());
		configuration.addEventType("BackFeed",BackFeedEvent.class.getName());
		configuration.addEventType("TriggerTest",TriggerTestEvent.class.getName());
		configuration.addEventType("SendTmnlParam",SendTmnlParamEvent.class.getName());
		configuration.addEventType("RelayMeterReading",RelayMeterReadingEvent.class.getName());
		configuration.addEventType("SendTmnlMpParam",SendTmnlMpParamEvent.class.getName());
		configuration.addEventType("SendTaskParam",SendTaskParamEvent.class.getName());
		configuration.addEventType("SendFrmTmnlParam",SendFrmTmnlParamEvent.class.getName());
		configuration.addEventType("SendFrmTaskParam",SendFrmTaskParamEvent.class.getName());
		configuration.addEventType("SendTmnlParamOther",SendTmnlParamOtherEvent.class.getName());
		
		configuration.addEventType("HBTriggerTest",HBTriggerTestEvent.class.getName());
		configuration.addEventType("HBSendTmnlParam",HBSendTmnlParamEvent.class.getName());
		configuration.addEventType("HBRelayMeterReading",HBRelayMeterReadingEvent.class.getName());
		configuration.addEventType("HBSendTmnlMpParam",HBSendTmnlMpParamEvent.class.getName());
		configuration.addEventType("HBSendTaskParam",HBSendTaskParamEvent.class.getName());
		configuration.addEventType("HBSendFrmTmnlParam",HBSendFrmTmnlParamEvent.class.getName());
		configuration.addEventType("HBSendFrmTaskParam",HBSendFrmTaskParamEvent.class.getName());
		configuration.addEventType("HBSendTmnlParamOther",HBSendTmnlParamOtherEvent.class.getName());
		
		epService = EPServiceProviderManager.getProvider("IEIGInterface", configuration);
        epService.initialize();

		//预购电控制服务事件
		PrepayCtrlEventMonitor prepayCtrlEventMonitor = new PrepayCtrlEventMonitor(epService.getEPAdministrator());
		prepayCtrlEventMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				PrepayCtrlEventProcess prepayCtrlEventProcess = new PrepayCtrlEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(prepayCtrlEventProcess);
            }
        });	
		//催费控制服务事件
		CrCyEventMonitor crCyEventMonitor = new CrCyEventMonitor(epService.getEPAdministrator());
		crCyEventMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				CrCyEventProcess crCyEventProcess = new CrCyEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(crCyEventProcess);
            }
        });	
		//营业报停服务事件
		StopCtrlEventMonitor stopCtrlEventMonitor = new StopCtrlEventMonitor(epService.getEPAdministrator());
		stopCtrlEventMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				StopCtrlEventProcess stopCtrlEventProcess = new StopCtrlEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(stopCtrlEventProcess);
            }
        });
		//运行管理-反馈营销
		BackFeedEventMonitor backFeedMonitor = new BackFeedEventMonitor(epService.getEPAdministrator());
		backFeedMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				BackFeedEventProcess backFeedEventProcess = new BackFeedEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(backFeedEventProcess);
            }
        });
		//运行管理-触发调试
		TriggerTestEventMonitor triggerTestMonitor = new TriggerTestEventMonitor(epService.getEPAdministrator());
		triggerTestMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				TriggerTestEventProcess triggerTestEventProcess = new TriggerTestEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(triggerTestEventProcess);
            }
        });
		//运行管理-下发终端参数
		SendTmnlParamEventMonitor sendTmnlParamMonitor = new SendTmnlParamEventMonitor(epService.getEPAdministrator());
		sendTmnlParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendTmnlParamEventProcess sendTmnlParamEventProcess = new SendTmnlParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendTmnlParamEventProcess);
            }
        });
		
		//运行管理-集抄下发终端参数
		SendFrmTmnlParamEventMonitor sendFrmTmnlParamMonitor = new SendFrmTmnlParamEventMonitor(epService.getEPAdministrator());
		sendFrmTmnlParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendFrmTmnlParamEventProcess sendFrmTmnlParamEventProcess = new SendFrmTmnlParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendFrmTmnlParamEventProcess);
            }
        });
		//运行管理-抄读电表数据
		RelayMeterReadingEventMonitor relayMeterReadingMonitor = new RelayMeterReadingEventMonitor(epService.getEPAdministrator());
		relayMeterReadingMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				RelayMeterReadingEventProcess relayMeterReadingEventProcess = new RelayMeterReadingEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(relayMeterReadingEventProcess);
            }
        });
		//运行管理-下发测量点终端参数
		SendTmnlMpParamEventMonitor sendTmnlMpParamMonitor = new SendTmnlMpParamEventMonitor(epService.getEPAdministrator());
		sendTmnlMpParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendTmnlMpParamEventProcess sendTmnlMpParamEventProcess = new SendTmnlMpParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendTmnlMpParamEventProcess);
            }
        });
		//运行管理-下发任务参数
		SendTaskParamEventMonitor sendTaskParamMonitor = new SendTaskParamEventMonitor(epService.getEPAdministrator());
		sendTaskParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendTaskParamEventProcess sendTaskParamEventProcess = new SendTaskParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendTaskParamEventProcess);
            }
        });
		//运行管理-下发任务参数
		SendFrmTaskParamEventMonitor sendFrmTaskParamMonitor = new SendFrmTaskParamEventMonitor(epService.getEPAdministrator());
		sendFrmTaskParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendFrmTaskParamEventProcess sendFrmTaskParamEventProcess = new SendFrmTaskParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendFrmTaskParamEventProcess);
            }
        });

		//运行管理-下发终端参数等（非国网规约）
		SendTmnlParamOtherEventMonitor sendTmnlParamOtherMonitor = new SendTmnlParamOtherEventMonitor(epService.getEPAdministrator());
		sendTmnlParamOtherMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				SendTmnlParamOtherEventProcess sendTmnlParamOtherEventProcess = new SendTmnlParamOtherEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(sendTmnlParamOtherEventProcess);
            }
        });
		
		/***********************************************河北**********************************************************/
		
		//运行管理-触发调试
		HBTriggerTestEventMonitor hbtriggerTestMonitor = new HBTriggerTestEventMonitor(epService.getEPAdministrator());
		hbtriggerTestMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBTriggerTestEventProcess hbtriggerTestEventProcess = new HBTriggerTestEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbtriggerTestEventProcess);
            }
        });
		//运行管理-下发终端参数
		HBSendTmnlParamEventMonitor hbsendTmnlParamMonitor = new HBSendTmnlParamEventMonitor(epService.getEPAdministrator());
		hbsendTmnlParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendTmnlParamEventProcess hbsendTmnlParamEventProcess = new HBSendTmnlParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendTmnlParamEventProcess);
            }
        });
		
		//运行管理-集抄下发终端参数
		HBSendFrmTmnlParamEventMonitor hbsendFrmTmnlParamMonitor = new HBSendFrmTmnlParamEventMonitor(epService.getEPAdministrator());
		hbsendFrmTmnlParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendFrmTmnlParamEventProcess hbsendFrmTmnlParamEventProcess = new HBSendFrmTmnlParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendFrmTmnlParamEventProcess);
            }
        });
		//运行管理-抄读电表数据
		HBRelayMeterReadingEventMonitor hbrelayMeterReadingMonitor = new HBRelayMeterReadingEventMonitor(epService.getEPAdministrator());
		hbrelayMeterReadingMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBRelayMeterReadingEventProcess hbrelayMeterReadingEventProcess = new HBRelayMeterReadingEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbrelayMeterReadingEventProcess);
            }
        });
		//运行管理-下发测量点终端参数
		HBSendTmnlMpParamEventMonitor hbsendTmnlMpParamMonitor = new HBSendTmnlMpParamEventMonitor(epService.getEPAdministrator());
		hbsendTmnlMpParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendTmnlMpParamEventProcess hbsendTmnlMpParamEventProcess = new HBSendTmnlMpParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendTmnlMpParamEventProcess);
            }
        });
		//运行管理-下发任务参数
		HBSendTaskParamEventMonitor hbsendTaskParamMonitor = new HBSendTaskParamEventMonitor(epService.getEPAdministrator());
		hbsendTaskParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendTaskParamEventProcess hbsendTaskParamEventProcess = new HBSendTaskParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendTaskParamEventProcess);
            }
        });
		//运行管理-下发任务参数
		HBSendFrmTaskParamEventMonitor hbsendFrmTaskParamMonitor = new HBSendFrmTaskParamEventMonitor(epService.getEPAdministrator());
		hbsendFrmTaskParamMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendFrmTaskParamEventProcess hbsendFrmTaskParamEventProcess = new HBSendFrmTaskParamEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendFrmTaskParamEventProcess);
            }
        });
		
		//运行管理-下发终端参数等（非国网规约）
		HBSendTmnlParamOtherEventMonitor hbsendTmnlParamOtherMonitor = new HBSendTmnlParamOtherEventMonitor(epService.getEPAdministrator());
		hbsendTmnlParamOtherMonitor.addListener(new UpdateListener (){
		    public void update(EventBean[] newEvents, EventBean[] oldEvents){
				if (newEvents == null) {
					return;
				}
				HBSendTmnlParamOtherEventProcess hbsendTmnlParamOtherEventProcess = new HBSendTmnlParamOtherEventProcess(newEvents,oldEvents);
				Constant.getPool().execute(hbsendTmnlParamOtherEventProcess);
            }
        });
		/***************************************************************************************************/
		
		
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
        Constant.setEpService(epService);       
        //init spring
		super.contextInitialized(event);
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		Constant.setCtx(ctx);				
	}
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}