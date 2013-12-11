package com.nari.util.ws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 错误代码
 */
public class ErrorCode implements Serializable {

	private static final long serialVersionUID = 1771933378080534015L;
	public static final String code0 = "00";
	public static final String code1 = "01";
	public static final String code2 = "02";
	public static final String code3 = "03";
	public static final String code4 = "04";
	public static final String code5 = "05";
	public static final String code6 = "11";
	public static final String code7 = "12";
	public static final String code8 = "13";

	private static ErrorCode ec;

	private static Map<String, String> errorMap = new HashMap<String, String>();

	public static ErrorCode getInstance() {
		if (ec == null) {
			setMap();
		}
		return ec;
	}

	private static void setMap() {
		errorMap.put(code0, "正确");
		errorMap.put(code1, "中继命令没有返回");
		errorMap.put(code2, "设置内容非法");
		errorMap.put(code3, "密码权限不足");
		errorMap.put(code4, "无此项数据");
		errorMap.put(code5, "命令时间失效");
		errorMap.put(code6, "目标地址不存在");
		errorMap.put(code7, "发送失败");
		errorMap.put(code8, "短消息帧太长");
	}

	public String getErrorDesc(String code) {
		return (String) errorMap.get(code);
	}
	
	/**
	 * 标志：不是按约定格式的XML
	 */
	public static final int MARKET_INTERFACE_FLAG_ROOT_ERROR = -4;
	
	/**
	 * 是否调用webservice接口
	 */
	public static final int IS_WEBSERVICE = -250 ;
	
	/**
	 * 标志：从营销系统没有得到字典数据
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_DIC = -1000 ;

	/**
	 * 标志：从营销系统没有得到取供电所信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_POWERSUPPLY = -1001 ;

	/**
	 * 标志：从营销系统没有得到供电线路信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_LINE = -1002 ;

	/**
	 * 标志：从营销系统没有得到单位信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_DEPT = -1003 ;

	/**
	 * 标志：从营销系统没有得到客户档案信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO = -1004 ;
	
	/**
	 * 标志：从营销系统取客户档案信息超时
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO_TIMEOUT = -1114 ;

	/**
	 * 标志：从营销系统没有得到客户供电电源信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_POWER = -1005 ;

	/**
	 * 标志：从营销系统没有得到客户联系人信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_CONTACT = -1006 ;

	/**
	 * 标志：从营销系统没有得到客户关联户号信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_RELATECUST = -1007 ;

	/**
	 * 标志：从营销系统没有得到客户表计档案
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_AMETER = -1008 ;
	
	/**
	 * 标志：从营销系统取客户表计档案超时
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_AMETER_TIMEOUT = -1118 ;

	/**
	 * 标志：从营销系统没有得到客户经理信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_CUSTMANAGER = -1009 ;

	/**
	 * 标志：从营销系统没有得到客户变压器档案
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_TRANSFORMER = -1010 ;

	/**
	 * 标志：从营销系统没有得到客户某月电费信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_POWERRATE = -1011 ;

	/**
	 * 标志：从营销系统没有得到客户SIM卡档案
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_SIM = -1012 ;

	/**
	 * 标志：从营销系统没有得到开关信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_SWITCH = -1013 ;

	/**
	 * 标志：从营销系统没有得到客户终端档案
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_TERMINAL = -1014 ;
	
	/**
	 * 标志：从营销系统没有得到客户终端档案
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_TIMEOUT = -1214 ;

	/**
	 * 标志：从营销系统没有得到测量点信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_MEASUREPOINT = -1015 ;

	/**
	 * 标志：从营销系统没有得到行业分类信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_INDUSTRYCLASSIFICATION = -1016 ;
	
	/**
	 * 标志：从营销系统没有得到终端厂家信息
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_FACTORY = -1017 ;

	/**
	 * 标志：接口数据存储时，无对应用户
	 */
	public static final int MARKET_INTERFACE_FLAG_NORELATECUSTOMER = -2001 ;
	/**
	 * 与营销系统的接口的方法标志-取变更通知信息失败
	 */
	public static final int MARKET_INTERFACE_FLAG_MODIFY_NOTICE_INFO_FAIL = -2002 ;
	
	/**
	 * 标志：终端与表计没有对应关系
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_RELATION = -2101 ;
	
	/**
	 * 标志：SIM卡号已经存在
	 */
	public static final int MARKET_INTERFACE_FLAG_EXIST_SIMKH = -2102 ;
	
	/**
	 * 标志：终端逻辑地址已经存在
	 */
	public static final int MARKET_INTERFACE_FLAG_EXIST_TERMINALLOGIC_ADDRESS = -2103 ;
	
	/**
	 * 标志：表计局号已经存在且属于另一个用户
	 */
	public static final int MARKET_INTERFACE_FLAG_EXIST_AMETER = -2104 ;
	
	/**
	 * 标志：终端与表计对应关系中的终端局号为空
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGETTERMINAL_RELATION = -2105 ;
	
	/**
	 * 标志：表地址或表规约为空
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_AMETER_ADDRESS = -2106 ;
	
	/**
	 * 标志：终端局号已经存在，且属于另一个用户
	 */
	public static final int MARKET_INTERFACE_FLAG_EXIST_TERMINAL_JH = -2107;
	
	/**
	 * 标志：终端逻辑地址为空
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_TERMINALLOGIC_ADDRESS = -2108;
	
	/**
	 * 标志：终端与表计对应关系中的表计局号为空
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGETAMETER_RELATION = -2109 ;
	
	/**
	 * 标志：终端索引码为空
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_ZDSYM = -2110;
	
	/**
	 * 标志：销户异常
	 */
	public static final int MARKET_INTERFACE_FLAG_MERGE_USER_ERROR = -2111 ;
	
	/**
	 * 标志：终端密码超长
	 */
	public static final int MARKET_INTERFACE_FLAG_TERMINALPASS_ERROR = -2112;
	
	/**
	 * 标志：该传单已存在且建档成功，请检查后再处理
	 */
	public static final int MARKET_INTERFACE_FLAG_CDBH_EXIST = -2113;
	
	/**
	 * 标志：未知错误
	 */
	public static final int MARKET_INTERFACE_FLAG_OTHER_ERROR= -2199;
	
	/**
	 * 标志：存储字典数据异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_DIC_ERROR = -1200 ;
	
	/**
	 * 标志：存储供电所信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_POWERSUPPLY_ERROR = -1201 ;
	
	/**
	 * 标志：存储供电线路信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_LINE_ERROR = -1202 ;
	
	/**
	 * 标志：存储单位信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_DEPT_ERROR = -1203 ;
	
	/**
	 * 标志：存储客户档案信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_CUSTINFO_ERROR = -1204 ;
	
	/**
	 * 标志：存储客户供电电源信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_POWER_ERROR = -1205 ;
	
	/**
	 * 标志：存储客户联系人信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_CONTACT_ERROR = -1206 ;
	
	/**
	 * 标志：存储客户变压器档案异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_TRANSFORMER_ERROR = -1210 ;
	
	/**
	 * 标志：存储客户SIM卡档案异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_SIM_ERROR = -1212 ;
	
	/**
	 * 标志：存储开关信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_SWITCH_ERROR = -1213 ;
	
	/**
	 * 标志：存储行业分类信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_INDUSTRYCLASSIFICATION_ERROR = -1216 ;
	
	/**
	 * 标志：存储终端厂家信息异常
	 */
	public static final int MARKET_INTERFACE_FLAG_SAVE_FACTORY_ERROR = -1217 ;
	
	/**
	 * 标志：传单编号为空
	 */
	public static final int MARKET_INTERFACE_FLAG_CDBH = -3100;
	
	/**
	 * 标志：现场系统中不存在此传单
	 */
	public static final int MARKET_INTERFACE_FLAG_NOTEXIST_CDBH = -3101;
	
	/**
	 * 标志：已进入下发测量点参数或下发任务流程,无法撤单
	 */
	public static final int MARKET_INTERFACE_FLAG_SEND_PARAMS = -3102;
	
	/**
	 * 标志：装接方案已确认,无法撤单
	 */
	public static final int MARKET_INTERFACE_FLAG_CONFIRM_PROGRAM = -3103;
	
	/**
	 * 标志：数据更新异常,撤单失败
	 */
	public static final int MARKET_INTERFACE_FLAG_UPDATE_ERROR = -3104;
	
	/**
	 * 标志：流程已经结束,无法撤单.
	 */
	public static final int MARKET_INTERFACE_FLAG_FLOWOVER = -3105;
}