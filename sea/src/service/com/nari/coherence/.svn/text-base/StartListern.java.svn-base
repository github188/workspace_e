package com.nari.coherence;

import org.apache.log4j.Logger;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.cache.event.EventType;
import com.nari.fe.commdefine.task.Response;

public class StartListern {
	
	private static final Logger log = Logger.getLogger(StartListern.class);
	public static int cacheNumber = 0;
	/**
	 * @desc 是否需要重新连接缓存 :true:需要重启,false:不需要重启
	 * @return
	 */
	public static void isRequireConnectCoherence(){
		try {
			int number =  TaskDeal.getTaskHandle().getCacheMemNo();
			
			System.out.println("cacheNumber :" + cacheNumber);
			System.out.println("number" + number);
			
			if(number != cacheNumber){
				init();
				cacheNumber = number;
			}
		} catch (Exception e) {
			log.info("与缓存服务器连接建立失败!");
			e.printStackTrace();
		}
	}
	
	public static void init(){
		FrontListern listern = new FrontListern();
		IStatement statement = CoherenceStatement.getSharedInstance();
		
		try {
			statement.addCacheMapListerener(Response.class, listern,EventType.ALL);
			log.info("----------------Response 监听添加！-----------------");
			cacheNumber = TaskDeal.getTaskHandle().getCacheMemNo();
		} catch (Exception e) {
			log.info("------------与缓存服务器连接建立失败!-------------");
			e.printStackTrace();
		}
		log.info("------------与缓存服务器连接建立成功!-------------");
	}
	
}
