package com.nari.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来存储与营销接口的参数与字段的对应关系（即接口定义的参数与现场系统相关表中的字段的对应关系）
 */
public class ParamFieldMapUtil {
	
	/**
	 * 取字典信息映射
	 * @return
	 */
	public static final Map<String, String> getDicMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ZDBM", "SJBM");
		map.put("ZDMC", "SJMC");
		return map;
	}

	/**
	 * 取供电所信息映射
	 * 
	 * @return
	 */
	public static final Map<String, String> getPowerSupplyMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DM", "YXXTID");
		map.put("MC", "JDM");
		return map;
	}

	/**
	 * 取供电线路信息映射
	 * @return
	 */
	public static final Map<String, String> getLineInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("XLID", "YXXTID");
		map.put("XLBH", "XLBH");
		map.put("GDFW", "GDFW");
		map.put("XLMC", "JDM");
		map.put("GDS", "SJJDID");
		map.put("SFLLXL", "SFLLXL");
		map.put("SJXLID", "SJXLID");
		map.put("LLXLID", "LLXLID");
		return map;
	}

	/**
	 * 取客户总数映射
	 * @return
	 */
	public static final Map<String, String> getCustomerNumMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("YHS", "YHS");
		map.put("HTRL", "HTRL");
		return map;
	}

	/**
	 * 取客户档案映射
	 * @return
	 */
	public static final Map<String, String> getCustomerInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("HM", "HM");
		map.put("YDDZ", "DWDZ");
		map.put("HYFL", "HYID");
		map.put("LHRQ", "KHSJ");
		map.put("BCCBRQ", "CBR");
		map.put("SCCBRQ", "SCCBR");
		map.put("YDSX", "YDSX");
		map.put("JLFS", "JLFS");
		map.put("DY", "GDDY");
		map.put("HTRL", "SDRL");
		map.put("GDS", "GDSID");
		map.put("SFVIP", "VIP");
		map.put("KHJLZH", "KHJLID");
		map.put("CBQM", "CBQM");
		map.put("XHRQ", "XHRQ");
		map.put("LLBZ", "LLBZ");
		map.put("HMPYM", "HMPYM");
		map.put("DZPYM", "DZPYM");
		map.put("YHBS", "YHBS");
		map.put("GDFS", "GDFS");
		return map;
	}

	/**
	 * 客户供电电源信息映射
	 * @return
	 */
	public static final Map<String, String> getCustomerPowerInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DYBH", "DYBH");
		map.put("XLID", "XLID");
		map.put("DYXZ", "DYSZ");
		map.put("GDDY", "GDDY");
		map.put("BHFS", "BHFS");
		map.put("GDRL", "GDRL");
		map.put("LDFS", "LDFS");
		return map;
	}

	/**
	 * 客户联系人信息映射
	 * @return
	 */
	public static final Map<String, String> getCustomerContactMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("LXLX", "LXRLX");
		map.put("LXR", "XM");
		map.put("LXDH", "GDDH");
		map.put("YDDH", "SJHM");
		map.put("CZ", "CZ");
		map.put("DZYX", "DY");
		map.put("LXYXJ", "YXJ");
		return map;
	}

	/**
	 * 取客户表计档案映射
	 * @return
	 */
	public static final Map<String, String> getAmeterInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("JH", "BJJH");
		map.put("LX", "BLX");
		map.put("XH", "DBXH");
		map.put("XX", "JXFS");
		map.put("GY1", "GY1");
		map.put("TXDZ1", "TXDZ1");
		map.put("GY2", "GY2");
		map.put("TXDZ2", "TXDZ2");
		map.put("ZCRQ", "ZCRQ");
		map.put("CHRQ", "CHRQ");
		map.put("DQZT", "ZT");
		map.put("MCCS", "MCCS");
		map.put("CJ", "CSBM");
		map.put("SYM", "SYM");
		map.put("EDDY", "EDDY");
		map.put("EDDL", "EDDL");
		map.put("ZSBL", "ZSBL");		
		map.put("CT", "CT");
		map.put("PT", "PT");
		map.put("BJDYCLDH", "BJDYCLDH");
		map.put("BJJDQ", "BJJDQ");
		map.put("JLDBH", "JLDBH");
		map.put("BJWS", "WS");
		return map;
	}

	/**
	 * 取客户变压器档案映射
	 * @return
	 */
	public static final Map<String, String> getTransformerInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("BYQZH", "BYQZH");
		map.put("BYQBH", "BH");
		map.put("MPRL", "RL");
		map.put("BYQCCRQ", "CCRQ");
		map.put("AZRQ", "AZRQ");
		map.put("CHRQ", "CHRQ");
		map.put("BYQZK", "DLZK");
		map.put("KZSH", "KZSH");
		map.put("FZSH", "FZSH");
		map.put("ZKDY", "ZKDY");
		map.put("KZDL", "KZDL");
		map.put("BYQZT", "BYQZT");
		map.put("ECDY", "ECDY");
		map.put("JXZB", "JXZB");
		map.put("LQFS", "LQFS");
		map.put("GYDDJBZ", "GYDDJBZ");
		map.put("SHBZ", "SHBZ");
		map.put("SHDM", "SHDM");
		map.put("BYQXH", "XH");
		map.put("YCDY", "YCDY");
		map.put("YH", "YH");
		map.put("BYQCCBH", "CCBH");
		map.put("BYQCJ", "ZZCJ");
		map.put("YGCU", "YGCU");
		map.put("WGCU", "WGCU");
		map.put("YGFE", "YGFE");
		map.put("WGFE", "WGFE");
		map.put("EDDL_GY", "EDDL_GY");
		map.put("EDDL_ZY", "EDDL_ZY");
		map.put("EDDL_DY", "EDDL_DY");
		map.put("EDDY_GY", "EDDY_GY");
		map.put("EDDY_ZY", "EDDY_ZY");
		map.put("EDDY_DY", "EDDY_DY");
		map.put("XLID", "XLID");
		map.put("XSL", "XSL");
		return map;
	}

	/**
	 * 取客户终端档案映射
	 * @return
	 */
	public static final Map<String, String> getCustomerTermInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ZDJH", "ZDJH");
		map.put("ZDLJDZ", "ZDLJDZ");
		map.put("ZDZZCJ", "ZZCJBM");
		map.put("ZDXH", "ZDXH");
		map.put("ZDCCRQ", "CCRQ");
		map.put("ZDCCBM", "CCBH");
		map.put("ZDSYM", "ZDSYM");
		map.put("ZDGY", "GYLX");
		map.put("ZDCT", "CT");
		map.put("ZDPT", "PT");
		map.put("XX", "JXFS");
		map.put("DL", "EDDL");
		map.put("DY", "EDDY");
		map.put("ZDGJMM", "GQXMM");
		map.put("ZDDJMM", "DQXMM");
		map.put("ZDRJVER", "RJBB");
		map.put("ZDYJVER", "YJBB");
		map.put("ZDZT", "ZT");
		map.put("ZDDYCLDH", "ZDDYCLDH");
		map.put("TXDKH", "TXDKH");
		map.put("ZRRID", "ZRRID");
		map.put("SIMHM", "SIMKH");
		map.put("DHHM", "DHHM");
		map.put("ZDZTXDZ", "ZDZTXDZ");
		map.put("ZDTXDZB1", "ZDTXDZB1");
		map.put("ZDTXDZB2", "ZDTXDZB2");
		map.put("ZDAPN", "APN");
		map.put("ZDDXZXH", "ZDDXZXHM");
		map.put("TXWGID", "QZJID");
		map.put("JLDBH", "JLDBH");
		map.put("LX", "ZDLX");
		return map;
	}

	/**
	 * 取终端通讯参数方案映射
	 * @return
	 */
	public static final Map<String, String> getTermParamSchemeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ZDTXDZID", "TXFAID");
		map.put("ZDZTXDZ", "ZTXDZ");
		map.put("ZDTXDZB1", "TXDZB1");
		map.put("ZDTXDZB2", "TXDZB2");
		map.put("ZDAPN", "APN");
		map.put("ZDDXZXH", "DXZXHM");
		map.put("TXWGID", "TXWGID");
		return map;
	}

	/**
	 * 取客户SIM卡档案映射
	 * @return
	 */
	public static final Map<String, String> getSIMInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("SIMID", "SIMID");
		map.put("SIMISP", "TXFWS");
		map.put("SIMIP", "IP");
		map.put("SIMZT", "ZT");
		return map;
	}

	/**
	 * 取开关信息映射
	 * @return
	 */
	public static final Map<String, String> getSwitchInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("HH", "HH");
		map.put("ZDJH", "ZDJH");
		map.put("KGBH", "KGBH");
		map.put("KGRL", "KGRL");
		map.put("KGLX", "KGSX");
		map.put("SSLC", "LCH");
		map.put("SFKZ", "SFKZ");
		return map;
	}

	/**
	 * 取单位信息
	 * @return
	 */
	public static final Map<String, String> getDeptInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DM", "DWDM");
		map.put("MC", "JDM");
		return map;
	}

	/**
	 * 取行业信息
	 * @return
	 */
	public static final Map<String, String> getIndustryClassificationInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DM", "HYID");
		map.put("MC", "HYM");
		map.put("SJDM", "SJHYID");
		return map;
	}

	/**
	 * 取测量点信息
	 * @return
	 */
	public static final Map<String, String> getMeasurePointInfoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("HH", "HH");
		map.put("ZDJH", "ZDJH");
		map.put("BJJH", "CLDJH");
		map.put("CLDH", "CLDH");
		return map;
	}
	
	/**
	 * 取抄表数据映射
	 * @return
	 */
	public static final Map<String, String> getAmeterDataMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("DW", "DWDM");
		map.put("HH", "HH");
		map.put("RQ", "RQ");
		map.put("JH", "CLDJH");
		map.put("ZDJH", "ZDJH");
		map.put("SJBH", "SJBH");
		map.put("SJZ", "SJZ");
		map.put("SJCBRQ", "RQ");
		map.put("CBQM", "CBQM");
		return map;
	}

	/**
	 * 取表计计度器与日电量表中的字段的对应MAP
	 * @return
	 */
	public static final Map<String, String> getJDQTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("102", "Z9011"); // 正向有功尖峰
		map.put("103", "Z9012"); // 正向有功高峰
		map.put("104", "Z9014"); // 正向有功低谷
		map.put("105", "Z9013"); // 正向有功平段
		map.put("106", "Z9010"); // 正向有功总
		map.put("111", "Z9021"); // 反向有功尖峰
		map.put("112", "Z9022"); // 反向有功高峰
		map.put("113", "Z9024"); // 反向有功低谷
		map.put("114", "Z9023"); // 反向有功平段
		map.put("115", "Z9020"); // 反向有功总
		map.put("211", "Z9110"); // 正向无功总
		map.put("214", "Z9132"); // 正向无功高峰
		map.put("215", "Z9134"); // 正向无功低谷
		map.put("222", "Z9120"); // 反向无功总
		map.put("224", "Z9142"); // 反向无功高峰
		map.put("225", "Z9144"); // 反向无功低谷
		map.put("301", "ZA010"); // 需量
		return map;
	}

	/**
	 * 营销与现场系统的终端状态对应关系
	 * @return
	 */
	public static final Map<String, String> getTerminalStatusMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "00"); // 营销-已验收，现场-待配
		map.put("12", "01"); // 待校
		map.put("13", "07"); // 待修
		map.put("14", "03"); // 待领
		map.put("15", "04"); // 待装
		map.put("16", "10"); // 营销-已装未投运，现场-'已装未投运', '已关联用户'
		map.put("17", "05"); // 运行
		map.put("18", "06"); // 待拆
		map.put("19", "02"); // 待退
		map.put("22", "08"); // 营销-已退厂，现场-在厂
		map.put("23", "09"); // 待报废
		map.put("24", "13"); // 营销-报废，现场-已报废
		return map;
	}
	
	public static final Map<String, String> getNoticeInfoChanged() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("JKFWMC", "FWM");
		return map;
	}
}