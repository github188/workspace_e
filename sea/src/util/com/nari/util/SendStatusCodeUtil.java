package com.nari.util;

public class SendStatusCodeUtil {
	/**
	 * 仅保存参数
	 */
	public final static  String ONLY_SAVE_PARAM = "01";
	
	/**
	 * 参数在线等待
	 */
	public final static  String PARAM_WAITING_ONLINE = "02";
	
	/**
	 * 待下发参数
	 */
	public final static String TO_SENGIDNG_PARAM= "03";
	
	/**
	 * 下发参数成功
	 */
	public final static String PARAM_SEND_SUCCESS = "04";
	
	/**
	 * 下发参数失败
	 */
	public final static String PARAM_SEND_FAILED="05";
	
	/**
	 * 下发参数超次数
	 */
	public final static String PARAM_SEND_TIME_OVER = "06";
	
	/**
	 * 接口下发参数中
	 */
	public final static String  INTERFACE_SENDING_PARAM="07";
	
	/**
	 * 参数下发中
	 */
	public final static String  PARAM_SEND_DOING="08";
	
	/**
	 * 仅保存命令
	 */
	public final static  String ONLY_SAVE_CONTROL = "11";
	
	/**
	 * 命令在线等待
	 */
	public final static  String CONTROL_WAITING_ONLINE = "12";
	
	/**
	 * 待下发命令
	 */
	public final static String TO_SENGIDNG_CONTROL= "13";
	
	/**
	 * 下发命令成功
	 */
	public final static String CONTROL_SEND_SUCCESS="14";
	
	/**
	 * 下发命令失败
	 */
	public final static String CONTROL_SEND_FAILED="15";
	
	/**
	 * 下发命令超次数
	 */
	public final static String CONTROL_SEND_TIME_OVER = "16";
	
	/**
	 * 接口下发命令中
	 */
	public final static String  INTERFACE_SENDING_CONTROL="17";
}
