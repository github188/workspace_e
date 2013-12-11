package com.nari.util;

/**
 * 流程节点定义类，目前仅仅预付费投入调试用到，考虑到前置错误无定义，这里也包括前置错误原因定义
 * @author 姜炜超
 */
public class FlowCodeNodeUtil {
	//初次购电流程节点定义----------------------------start
	/**
	 * 从营销系统取得预付费信息
	 */
	public final static  String FIR_GET_INFO = "10";
	
	/**
	 * 终端读电表示值(F33)
	 */
	public final static  String FIR_TMNL_READ = "11";
	
	/**
	 * 终端跳合闸
	 */
	public final static  String FIR_TMNL_TURN_CTRL = "12";
	
	/**
	 * 下发预付费参数
	 */
	public final static  String FIR_SEND_PREPAID_PARAM = "13";
	
	/**
	 * 召测终端预付费参数进行比较
	 */
	public final static  String FIR_CALL_PARAM_COMPARE = "14";
	
	/**
	 * 下发预付费方案
	 */
	public final static  String FIR_SEND_PREPAID_SCHEME = "15";
	
	/**
	 * 返回营销系统预付费执行信息
	 */
	public final static  String FIR_RETURN_INTO = "16";
	//初次购电流程节点定义------------------------------end
	
	//非初次购电流程节点定义----------------------------start
	/**
	 * 从营销系统取得预付费信息 
	 */
	public final static  String GET_INFO = "21";
	
	/**
	 * 终端读电表示值(F33)
	 */
	public final static  String TMNL_READ = "22";
	
	/**
	 * 下发预付费参数
	 */
	public final static  String SEND_PREPAID_PARAM = "23";
	
	/**
	 * 召测剩余值(剩余电量或电费)
	 */
	public final static  String CALL_LEFT_POWER = "24";
	
	/**
	 * 返回营销系统预付费执行信息
	 */
	public final static  String RETURN_INTO = "25";
	//非初次购电流程节点定义----------------------------end
	
	public final static  String FLOW_ERR0 = "在规定的时间内缓存没有返回";
	public final static  String FLOW_ERR1 = "终端不在线 ";
	public final static  String FLOW_ERR2 = "其他原因 ";
	
	public final static  String FRONT_STATE0 = "0";//在规定的时间内缓存没有返回 
	public final static  String FRONT_STATE1 = "1";//终端不在线
	public final static  String FRONT_STATE3 = "3";//成功
	public final static  String FRONT_STATE4 = "4";//失败
}
