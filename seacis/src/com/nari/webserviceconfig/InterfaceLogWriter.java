package com.nari.webserviceconfig;

import org.apache.log4j.Logger;

import com.nari.service.MarketTerminalConfigManager;

public class InterfaceLogWriter{
	
	private static Logger logger = Logger.getLogger(InterfaceLogWriter.class); 
	private MarketTerminalConfigManager marketTerminalConfigManager;
	
	public MarketTerminalConfigManager getMarketTerminalConfigManager() {
		return marketTerminalConfigManager;
	}

	public void setMarketTerminalConfigManager(MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	/**
	 * 写日志
	 * @param SYS_NO
	 * 系统编码（外围系统编码：01 营销；02 客服系统；03 单点登录）
	 * @param INTERFACE_TYPE 
	 * 调用类型（与外围系统接口调用类型：01 收到其他系统请求；02 收到其他系统应答；03 本系统请求；04 本系统应答）
	 * @param INTERFACE_NAME
	 * 接口名称
	 * @param INTERFACE_CONTENT
	 * 接口关键内容
	 * @param ERR_NO
	 * 出错编码
	 */
	public void writeLog(String logId, String sysNo, String interfaceType, String interfaceName, String interfaceContent, String errNo){
		marketTerminalConfigManager.writeLog(logId, sysNo, interfaceType, interfaceName, interfaceContent, errNo);
	}
	
	/**
	 * 将信息打印到控制台
	 * @param info
	 * 要打印的信息
	 */
	public void writeToConsole(String info) {
		System.out.println(info);
	}

	/**
	 * 将信息写入日志文件
	 * @param info
	 * 要写入的信息
	 */
	public void writeToLogFile(String info){
		logger.info(info);
	}
}