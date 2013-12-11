package com.nari.qrystat.colldataanalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.customer.CCons;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.ArithmeticUtil;

/**
 * 采集数据综合分析Action
 * 
 * @author 杨传文
 * @author 姜炜超 modify
 */
public class GeneralAnalyseAction extends BaseAction {
	//记录日志
	private static final Logger logger = Logger.getLogger(GeneralAnalyseAction.class);

	private GeneralAnalyseManager generalAnalyseManager;// 采集数据综合分析业务对象

	// 返回数据
	private String consName;//客户名
	private List<SameTradeCCons> sameTradeCConsList;//同行业用户列表
	private List<EDataTotalBean> dataTotalList;//总加组列表
	private List<EDataMpBean> dataMpList;//测量点列表
	private ETmnlDayStatBean tmnlDayStat;//日冻结终端信息
	private ETmnlMonStatBean tmnlMonStat;//月冻结终端信息
	private ETotalDayStatBean totalDayStat;//日冻结总加组信息
	private ETotalMonStatBean totalMonStat;//月冻结总加组信息
	private List<ETmnlDayStatBean> tmnlDayStatList;//日冻结终端列表
	private List<ETmnlMonStatBean> tmnlMonStatList;//月冻结终端列表
	private List<ETotalDayStatBean> totalDayStatList;//日冻结总加组列表
	private List<ETotalMonStatBean> totalMonStatList;//月冻结总加组列表
	private List<GroupNameBean> groupList;//群组列表
	
	private long totalCount;//总数

	/* 测量点数据统计 */
	private List<EMpRdayReadBean> mpRdayReadList;//测量点抄表日冻结电能示值列表
	private List<EMpDayPowerBean> mpDayPowerList;//测量点日冻结总及分相有功功率列表
	private List<EMpDayVstatBean> mpDayVstatList;//测量点日冻结电压统计列表
	private List<EMpDayIstatBean> mpDayIstatList;//测量点日冻结电流越限统计列表
	private List<EMpDayReadBean> mpDayReadList;//测量点日冻结电能示值列表
	private List<EMpDayEnergyBean> mpDayEnergyList;//测量点日冻结电能量列表
	private List<EMpDayCompBean> mpDayCompList;//测量点日电容器累计补偿的无功电能量列表
	private List<EMpMonVstatBean> mpMonVstatList;//测量点月冻结电压统计列表
	private List<EMpMonReadBean> mpMonReadList;//月冻结电能量列表
	private List<EMpMonEnergyBean> mpMonEnergyList;//月冻结电能量列表
	private List<EMpRdayDemandBean> mpRdayDemandList;//抄表日冻结需量及需量发生时间列表
	private List<EMpDayDemandBean> mpDayDemandList;//日冻结需量及需量发生时间列表
	private List<EMpDayPftimeBean> mpDayPftimeList;//日冻结功率因数区段累计时间列表
	private List<EMpDayUnbalanceBean> mpDayUnbalanceList;//日不平衡度越限累计时间列表
	private List<EMpMonUnbalanceBean> mpMonUnbalanceList;//月不平衡度越限累计时间列表
	private List<EMpMonPftimeBean> mpMonPftimeList;//月冻结功率因数区段累计时间列表
	private List<EMpMonDemandBean> mpMonDemandList;//月冻结最大需量及需量发生时间列表
	private List<EMpMonPowerBean> mpMonPowerList;//月冻结总及分相有功功率数据列表
	private List<EMpMonIstatBean> mpMonIstatList;//月冻结电流越限统计数据列表
	private List<EMpReadBean> mpRealDataReadList;//当前电能示值列表
	private List<EMpCurveBean> mpRealCurveDataList;//当前测量点曲线列表

	private List<GeneralRealPowerDataBean> mpDayRealPowerList;//实时功率曲线列表
	private List<GeneralRealCurDataBean> mpDayRealCurList;//实时电流曲线列表
	private List<GeneralRealVoltDataBean> mpDayRealVoltList;//实时电压曲线列表
	private List<GeneralRealFactorDataBean> mpDayRealFactorList;//实时功率因数曲线列表
	private List<GeneralRealPowerDataBean> mpDayRealPowerConList;//对比日实时功率曲线列表
	private List<GeneralRealCurDataBean> mpDayRealCurConList;//对比日实时电流曲线列表
	private List<GeneralRealVoltDataBean> mpDayRealVoltConList;//对比日实时电流曲线列表
	private List<GeneralRealFactorDataBean> mpDayRealFactorConList;//对比日实时功率因数曲线列表
	
	private List<GeneralDataBean> generalPowerModelList;//仅仅作为显示时间而存在

	//冻结功率
	private List<GeneralDataBean> generalPowerData;//冻结功率曲线列表
	private List<GeneralDataBean> contrastPowerData;//对比日冻结功率曲线列表
	private List<GeneralDataBean> generalAPowerData;//冻结A相功率曲线列表
	private List<GeneralDataBean> contrastAPowerData;//对比日冻结A相功率曲线列表
	private List<GeneralDataBean> generalBPowerData;//冻结B相功率曲线列表
	private List<GeneralDataBean> contrastBPowerData;//对比日冻结B相功率曲线列表
	private List<GeneralDataBean> generalCPowerData;//冻结C相功率曲线列表
	private List<GeneralDataBean> contrastCPowerData;//对比日冻结C相功率曲线列表
	//冻结电流
	private List<GeneralDataBean> generalCurData;//冻结零序电流曲线列表
	private List<GeneralDataBean> contrastCurData;//对比日冻结零序电流曲线列表
	private List<GeneralDataBean> generalACurData;//冻结A相电流曲线列表
	private List<GeneralDataBean> contrastACurData;//对比日冻结A相电流曲线列表
	private List<GeneralDataBean> generalBCurData;//冻结B相电流曲线列表
	private List<GeneralDataBean> contrastBCurData;//对比日冻结B相电流曲线列表
	private List<GeneralDataBean> generalCCurData;//冻结C相电流曲线列表
	private List<GeneralDataBean> contrastCCurData;//对比日冻结C相电流曲线列表
	//冻结电压
	private List<GeneralDataBean> generalAVoltData;//冻结A相电压曲线列表
	private List<GeneralDataBean> contrastAVoltData;//对比日冻结A相电压曲线列表
	private List<GeneralDataBean> generalBVoltData;//冻结B相电压曲线列表
	private List<GeneralDataBean> contrastBVoltData;//对比日冻结B相电压曲线列表
	private List<GeneralDataBean> generalCVoltData;//冻结C相电压曲线列表
	private List<GeneralDataBean> contrastCVoltData;//对比日冻结C相电压曲线列表
	//冻结功率因数
	private List<GeneralDataBean> generalFactorData;//冻结功率因数曲线列表
	private List<GeneralDataBean> contrastFactorData;//对比日冻结功率因数曲线列表
	private List<GeneralDataBean> generalAFactorData;//冻结A相功率因数曲线列表
	private List<GeneralDataBean> contrastAFactorData;//对比日冻结A相功率因数曲线列表
	private List<GeneralDataBean> generalBFactorData;//冻结B相功率因数曲线列表
	private List<GeneralDataBean> contrastBFactorData;//对比日冻结B相功率因数曲线列表
	private List<GeneralDataBean> generalCFactorData;//冻结C相功率因数曲线列表
	private List<GeneralDataBean> contrastCFactorData;//对比日冻结C相功率因数曲线列表
	
	//冻结电能量
	private List<GeneralDataBean> generalEnergyPapData;//冻结电能量正向有功曲线列表
	private List<GeneralDataBean> generalEnergyPrpData;//冻结电能量正向无功曲线列表
	private List<GeneralDataBean> generalEnergyRapData;//冻结电能量反向有功曲线列表
	private List<GeneralDataBean> generalEnergyRrpData;//冻结电能量反向无功曲线列表
	private List<GeneralDataBean> contrastEnergyPapData;//对比日冻结电能量正向有功曲线列表
	private List<GeneralDataBean> contrastEnergyPrpData;//对比日冻结电能量正向无功曲线列表
	private List<GeneralDataBean> contrastEnergyRapData;//对比日冻结电能量反向有功曲线列表
	private List<GeneralDataBean> contrastEnergyRrpData;//对比日冻结电能量反向无功曲线列表
	
	//冻结电能示值
	private List<GeneralDataBean> generalReadPapData;//冻结电能示值正向有功曲线列表
	private List<GeneralDataBean> generalReadPrpData;//冻结电能示值正向无功曲线列表
	private List<GeneralDataBean> generalReadRapData;//冻结电能示值反向有功曲线列表
	private List<GeneralDataBean> generalReadRrpData;//冻结电能示值反向无功曲线列表
	private List<GeneralDataBean> contrastReadPapData;//对比日冻结电能示值正向有功曲线列表
	private List<GeneralDataBean> contrastReadPrpData;//对比日冻结电能示值正向无功曲线列表
	private List<GeneralDataBean> contrastReadRapData;//对比日冻结电能示值反向有功曲线列表
	private List<GeneralDataBean> contrastReadRrpData;//对比日冻结电能示值反向无功曲线列表
	
	//实时曲线命名
	private String mpDayRealPowerName;//实时功率曲线名
	private String mpDayRealCurName;//实时电流曲线名
	private String mpDayRealVoltName;//实时电压曲线名
	private String mpDayRealFactorName;//实时功率因数曲线名
	private String mpDayRealPowerConName;//对比日实时功率曲线名
	private String mpDayRealCurConName;//对比日实时电流曲线名
	private String mpDayRealVoltConName;//对比日实时电压曲线名
	private String mpDayRealFactorConName;//对比日实时功率因数曲线名
	//冻结功率命名
	private String generalPowerName;//冻结功率曲线名
	private String contrastPowerName;//对比日冻结功率曲线名
	private String generalAPowerName;//冻结A相功率曲线名
	private String contrastAPowerName;//对比日冻结A相功率曲线名
	private String generalBPowerName;//冻结B相功率曲线名
	private String contrastBPowerName;//对比日冻结B相功率曲线名
	private String generalCPowerName;//冻结C相功率曲线名
	private String contrastCPowerName;//对比日冻结C相功率曲线名
	//冻结电压命名
	private String generalAVoltName;//冻结A相电压曲线名
	private String contrastAVoltName;//对比日冻结A相电压曲线名
	private String generalBVoltName;//冻结B相电压曲线名
	private String contrastBVoltName;//对比日冻结B相电压曲线名
	private String generalCVoltName;//冻结C相电压曲线名
	private String contrastCVoltName;//对比日冻结C相电压曲线名
	//冻结电流命名
	private String generalCurName;//冻结零序电流曲线名
	private String contrastCurName;//对比日冻结零序电流曲线名
	private String generalACurName;//冻结A相电流曲线名
	private String contrastACurName;//对比日冻结A相电流曲线名
	private String generalBCurName;//冻结B相电流曲线名
	private String contrastBCurName;//对比日冻结B相电流曲线名
	private String generalCCurName;//冻结C相电流曲线名
	private String contrastCCurName;//对比日冻结C相电流曲线名
	//冻结功率因数命名
	private String generalFactorName;//冻结功率因数曲线名
	private String contrastFactorName;//对比日冻结功率因数曲线名
	private String generalAFactorName;//冻结A相功率因数曲线名
	private String contrastAFactorName;//对比日冻结A相功率因数曲线名
	private String generalBFactorName;//冻结B相功率因数曲线名
	private String contrastBFactorName;//对比日冻结B相功率因数曲线名
	private String generalCFactorName;//冻结C相功率因数曲线名
	private String contrastCFactorName;//对比日冻结C相功率因数曲线名
	//冻结电能示值命名
	private String generalReadPapName;//冻结电能示值正向有功曲线名
	private String generalReadPrpName;//冻结电能示值正向无功曲线名
	private String generalReadRapName;//冻结电能示值反向有功曲线名
	private String generalReadRrpName;//冻结电能示值反向无功曲线名
	private String contrastReadPapName;//对比日冻结电能示值正向有功曲线名
	private String contrastReadPrpName;//对比日冻结电能示值正向无功曲线名
	private String contrastReadRapName;//对比日冻结电能示值反向有功曲线名
	private String contrastReadRrpName;//对比日冻结电能示值反向无功曲线名
	//冻结电能量命名
	private String generalEnergyPapName;//冻结电能量正向有功曲线名
	private String generalEnergyPrpName;//冻结电能量正向无功曲线名
	private String generalEnergyRapName;//冻结电能量反向有功曲线名
	private String generalEnergyRrpName;//冻结电能量反向无功曲线名
	private String contrastEnergyPapName;//对比日冻结电能量正向有功曲线名
	private String contrastEnergyPrpName;//对比日冻结电能量正向无功曲线名
	private String contrastEnergyRapName;//对比日冻结电能量反向有功曲线名
	private String contrastEnergyRrpName;//对比日冻结电能量反向无功曲线名

	// 前台参数
	private Long totalId;//总加组数据id
	private Long dataId;//测量点数据id
	private String curveType;//曲线类型
	private boolean statCheckValue;//是否选择冻结数据
	private boolean conCheckValue;//是否显示对比日数据
	private boolean realCheckValue;//是否显示实时数据
	private String consNo;//客户编号
	private String groupNo;//组号
	private Date dataDate;//查询日期
	private Date contrastDate;//对比日期
	private Date dataDateFrom;//开始日期
	private Date dataDateTo;//结束日期
	private long start = 0;//分页参数
	private int limit = Constans.DEFAULT_PAGE_SIZE;//分页参数
	private boolean success = true;//
	
	//主要是针对相序查询
	private int DATA_TYPE0 = 0;//类别
	private int DATA_TYPE1 = 1;
	private int DATA_TYPE2 = 2;
	private int DATA_TYPE3 = 3;
	private int DATA_TYPE4 = 4;
	private int DATA_TYPE5 = 5;
	private int DATA_TYPE6 = 6;
	
	private Double minValue = 0.0;//最小值，用于曲线坐标定位
	private Double maxValue = 0.0;//最大值，用于曲线坐标定位
	private int freezeCycleNum;//冻结周期
	
	// 前端的动态的headers，用于动态生成cm
	private List<Map<String ,String >> headers=new ArrayList<Map<String ,String >>(16);
	private List<GeneralCurveDataBean> generalCurveDataList = new ArrayList<GeneralCurveDataBean>();//曲线数据
	private List<EMpConsTmnlBean> consTmnlList;//终端号，包括内码和实际值，用于下拉框显示
	private String tmnlType;//终端内码号
	private String consType;//客户类别，5表示居民用户
	
	public String queryconsName() throws Exception {
		try {
			CCons cons = generalAnalyseManager.findConsNameByConsNo(consNo);
			consName = cons.getConsName();
			if(null != cons.getConsType()){
			    consType = cons.getConsType().toString();
			}else{
				consType = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 同行业用户用电排名
	 * 
	 * @return
	 * @throws Exception
	 */
	public String querySameTradeCCons() throws Exception {
		try {
			Page<SameTradeCCons> page = generalAnalyseManager
					.findSameTradeCConsByConsNo(consNo, dataDate, start, limit);
			sameTradeCConsList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 总加组主表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTotal() throws Exception {
		try {
			Page<EDataTotalBean> page = generalAnalyseManager
					.findEDataTotalByConsNo(consNo, start, limit);
			dataTotalList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点主表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMp() throws Exception {
		try {
			Page<EDataMpBean> page = generalAnalyseManager.findEDataMpByConsNo(
					consNo, start, limit);			
			dataMpList = page.getResult();
			totalCount = page.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日冻结终端和测量点单个信息，点击查询调用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlAndTotalStat() throws Exception {
		PSysUser pSysUser = getPSysUser();
		if (null == pSysUser) {
			return SUCCESS;
		}
		try {
			tmnlDayStat = generalAnalyseManager.findETmnlDayStatByDate(consNo,
					dataDate, pSysUser);
			tmnlMonStat = generalAnalyseManager.findETmnlMonStatByDate(consNo,
					dataDate, pSysUser);
			totalDayStat = generalAnalyseManager.findETotalDayStatByDate(
					consNo, dataDate);
			totalMonStat = generalAnalyseManager.findETotalMonStatByDate(
					consNo, dataDate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日冻结终端列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlDayStatList() throws Exception {
		PSysUser pSysUser = getPSysUser();
		if (null == pSysUser) {
			return ERROR;
		}
		try {
			Page<ETmnlDayStatBean> tmnlDayStatPage = generalAnalyseManager
					.findETmnlDayStatByDate(consNo, dataDateFrom, dataDateTo,
							start, limit, pSysUser);
			tmnlDayStatList = tmnlDayStatPage.getResult();
			totalCount = tmnlDayStatPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结终端列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlMonStatList() throws Exception {
		PSysUser pSysUser = getPSysUser();
		if (null == pSysUser) {
			return ERROR;
		}
		try {
			Page<ETmnlMonStatBean> tmnlMonStatPage = generalAnalyseManager
					.findETmnlMonStatByDate(consNo, dataDateFrom, dataDateTo,
							start, limit, pSysUser);
			tmnlMonStatList = tmnlMonStatPage.getResult();
			totalCount = tmnlMonStatPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日冻结总加组列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTotalDayStatList() throws Exception {
		try {
			Page<ETotalDayStatBean> totalDayStatPage = generalAnalyseManager
					.findETotalDayStatByDate(consNo, dataDateFrom, dataDateTo,
							start, limit);
			totalDayStatList = totalDayStatPage.getResult();
			totalCount = totalDayStatPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结总加组列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTotalMonStatList() throws Exception {
		try {
			Page<ETotalMonStatBean> totalMonStatPage = generalAnalyseManager
					.findETotalMonStatByDate(consNo, dataDateFrom, dataDateTo,
							start, limit);
			totalMonStatList = totalMonStatPage.getResult();
			totalCount = totalMonStatPage.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	// --------------------------测量点数据统计--------------------------------start
	/**
	 * 测量点抄表日冻结电能示值统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpRdayRead() throws Exception {
		try {
			Page<EMpRdayReadBean> psc = generalAnalyseManager
					.findEMpRdayReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpRdayReadList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日冻结总及分相有功功率
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayPower() throws Exception {
		try {
			Page<EMpDayPowerBean> psc = generalAnalyseManager
					.findEMpDayPowerByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayPowerList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日冻结电压统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayVstat() throws Exception {
		try {
			Page<EMpDayVstatBean> psc = generalAnalyseManager
					.findEMpDayVstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayVstatList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日冻结电流越限统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayIstat() throws Exception {
		try {
			Page<EMpDayIstatBean> psc = generalAnalyseManager
					.findEMpDayIstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayIstatList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日冻结电能示值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayRead() throws Exception {
		try {
			Page<EMpDayReadBean> psc = generalAnalyseManager
					.findEMpDayReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayReadList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日冻结电能量
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayEnergy() throws Exception {
		try {
			Page<EMpDayEnergyBean> psc = generalAnalyseManager
					.findEMpDayEnergyByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayEnergyList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点日电容器累计补偿的无功电能量
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayComp() throws Exception {
		try {
			Page<EMpDayCompBean> psc = generalAnalyseManager
					.findEMpDayCompByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayCompList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 测量点月冻结电压统计
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonVstat() throws Exception {
		try {
			Page<EMpMonVstatBean> psc = generalAnalyseManager
					.findEMpMonVstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonVstatList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结电能示值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonRead() throws Exception {
		try {
			Page<EMpMonReadBean> psc = generalAnalyseManager
					.findEMpMonReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonReadList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结电能量
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonEnergy() throws Exception {
		try {
			Page<EMpMonEnergyBean> psc = generalAnalyseManager
					.findEMpMonEnergyByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonEnergyList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 抄表日冻结需量及需量发生时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpRdayDemand() throws Exception {
		try {
			Page<EMpRdayDemandBean> psc = generalAnalyseManager
					.findEMpRdayDemanByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpRdayDemandList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日冻结需量及需量发生时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayDemand() throws Exception {
		try {
			Page<EMpDayDemandBean> psc = generalAnalyseManager
					.findEMpDayDemandByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayDemandList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日冻结功率因数区段累计时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayPftime() throws Exception {
		try {
			Page<EMpDayPftimeBean> psc = generalAnalyseManager
					.findEMpDayPftimeByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpDayPftimeList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 日不平衡度越限累计时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpDayUnbalance() throws Exception {
		try {
			Page<EMpDayUnbalanceBean> psc = generalAnalyseManager
					.findEMpDayUnbalanceByConsNo(consNo, dataDateFrom,
							dataDateTo, tmnlType, start, limit);
			mpDayUnbalanceList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月不平衡度越限累计时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonUnbalance() throws Exception {
		try {
			Page<EMpMonUnbalanceBean> psc = generalAnalyseManager
					.findEMpMonUnbalanceByConsNo(consNo, dataDateFrom,
							dataDateTo, tmnlType, start, limit);
			mpMonUnbalanceList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结功率因数区段累计时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonPftime() throws Exception {
		try {
			Page<EMpMonPftimeBean> psc = generalAnalyseManager
					.findEMpMonPftimeByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonPftimeList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结最大需量及需量发生时间
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonDemand() throws Exception {
		try {
			Page<EMpMonDemandBean> psc = generalAnalyseManager
					.findEMpMonDemandByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonDemandList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结总及分相有功功率数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonPower() throws Exception {
		try {
			Page<EMpMonPowerBean> psc = generalAnalyseManager
					.findEMpMonPowerByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonPowerList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 月冻结电流越限统计数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpMonIstat() throws Exception {
		try {
			Page<EMpMonIstatBean> psc = generalAnalyseManager
					.findEMpMonIstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
			mpMonIstatList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 当前电能示值
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpRealDataRead() throws Exception {
		try {
			Page<EMpReadBean> psc = generalAnalyseManager
					.findEMpRealDataReadByConsNo(consNo, dataDateFrom,
							dataDateTo, tmnlType, start, limit);
			mpRealDataReadList = psc.getResult();
			totalCount = psc.getTotalCount();
			consTmnlList = generalAnalyseManager.queryConsTmnlInfo(consNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 当前测量点曲线
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpRealCurveData() throws Exception {
		try {
			Page<EMpCurveBean> psc = generalAnalyseManager
					.findEMpRealCurveDataByConsNo(consNo, dataDateFrom,
							dataDateTo, tmnlType, start, limit);
			mpRealCurveDataList = psc.getResult();
			totalCount = psc.getTotalCount();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	// --------------------------测量点数据统计--------------------------------end

	/**
	 * 查询群组名称列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryGroupName() throws Exception {
		try {
			groupList = generalAnalyseManager.findGroupName();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	/**
	 * 查询总加组曲线，对于二类数据，只要查询功率曲线
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryTotalData() throws Exception {

		if (null == curveType || "".equals(curveType)) {
			return SUCCESS;
		}
		
		if(!realCheckValue && !statCheckValue){
			return SUCCESS;
		}
		
		generalPowerModelList = generalAnalyseManager.getTimeModelList(freezeCycleNum);
		//为动态生成曲线数据标题而调用
		dynamicTotalCrtCm(curveType, realCheckValue, statCheckValue, conCheckValue);
		
		List<Double[]> list = new ArrayList<Double[]>();
		
		if ("1".equals(curveType)) {
			if(realCheckValue){//是否显示实时数据
				//默认显示查询日实时数据
	    	    this.mpDayRealPowerList = generalAnalyseManager.findTotalRealPowerData(totalId, freezeCycleNum, dataDate, consNo);
	        
	            //获取最大值和最小值
	            if(!isPowerNullValue(mpDayRealPowerList)){
	        	    list.add(checkPowerMaxMinValue(mpDayRealPowerList));
	            }
	            
	            if(this.conCheckValue){//是否显示对比日实时数据
		    	    this.mpDayRealPowerConList = generalAnalyseManager.findTotalRealPowerData(totalId, freezeCycleNum, contrastDate, consNo);
		    	    if(!isPowerNullValue(mpDayRealPowerConList)){
			        	list.add(checkPowerMaxMinValue(mpDayRealPowerConList));
			        }
		        }
			}
	        
	        if(this.statCheckValue){//是否显示冻结数据
	        	//默认显示查询日冻结数据
				this.generalPowerData = generalAnalyseManager.findTotalPowerData(totalId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalAPowerData = generalAnalyseManager.findTotalPowerData(totalId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalBPowerData = generalAnalyseManager.findTotalPowerData(totalId, freezeCycleNum, dataDate, DATA_TYPE3, consNo);
				this.generalCPowerData = generalAnalyseManager.findTotalPowerData(totalId, freezeCycleNum, dataDate, DATA_TYPE4, consNo);
				
				//获取最大值和最小值
				if(!isStatNullValue(generalPowerData)){
		        	list.add(checkStatMaxMinValue(generalPowerData));
		        }
				
				if(!isStatNullValue(generalAPowerData)){
		        	list.add(checkStatMaxMinValue(generalAPowerData));
		        }
				
				if(!isStatNullValue(generalBPowerData)){
		        	list.add(checkStatMaxMinValue(generalBPowerData));
		        }
				
				if(!isStatNullValue(generalCPowerData)){
		        	list.add(checkStatMaxMinValue(generalCPowerData));
		        }
				
				if(this.conCheckValue){//是否显示对比日冻结数据
					this.contrastPowerData = generalAnalyseManager.findTotalPowerData(totalId,freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastAPowerData = generalAnalyseManager.findTotalPowerData(totalId,freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastBPowerData = generalAnalyseManager.findTotalPowerData(totalId,freezeCycleNum, contrastDate, DATA_TYPE3, consNo);
					this.contrastCPowerData = generalAnalyseManager.findTotalPowerData(totalId,freezeCycleNum, contrastDate, DATA_TYPE4, consNo);
					
					if(!isStatNullValue(contrastPowerData)){
			        	list.add(checkStatMaxMinValue(contrastPowerData));
			        }
					
					if(!isStatNullValue(contrastAPowerData)){
			        	list.add(checkStatMaxMinValue(contrastAPowerData));
			        }
					
					if(!isStatNullValue(contrastBPowerData)){
			        	list.add(checkStatMaxMinValue(contrastBPowerData));
			        }
					
					if(!isStatNullValue(contrastCPowerData)){
			        	list.add(checkStatMaxMinValue(contrastCPowerData));
			        }
				}
	        }
		    if(null != generalPowerData && 0 < generalPowerData.size()){
		    	generalPowerName = "查询日冻结功率";
		    }
		    if(null != contrastPowerData && 0 < contrastPowerData.size()){
		    	contrastPowerName = "对比日冻结功率";
		    }
		    if(null != generalAPowerData && 0 < generalAPowerData.size()){
		    	generalAPowerName = "查询日A相冻结功率";
		    }
		    if(null != contrastAPowerData && 0 < contrastAPowerData.size()){
		    	contrastAPowerName = "对比日A相冻结功率";
		    }
		    if(null != generalBPowerData && 0 < generalBPowerData.size()){
		    	generalBPowerName = "查询日B相冻结功率";
		    }
		    if(null != contrastBPowerData && 0 < contrastBPowerData.size()){
		    	contrastBPowerName = "对比日B相冻结功率";
		    }
		    if(null != generalCPowerData && 0 < generalCPowerData.size()){
		    	generalCPowerName = "查询日C相冻结功率";
		    }
		    if(null != contrastCPowerData && 0 < contrastCPowerData.size()){
		    	contrastCPowerName = "对比日C相冻结功率";
		    }
		    if(null != mpDayRealPowerList && 0 < mpDayRealPowerList.size()){
		    	mpDayRealPowerName = "查询日实时功率";
		    }
		    if(null != mpDayRealPowerConList && 0 < mpDayRealPowerConList.size()){
		    	mpDayRealPowerConName = "对比日实时功率";
		    }
		}else{
			return SUCCESS;
		}
		
		//获取最大值和最小值
		int j = list.size();
		if(j > 0){
			this.maxValue = ((Double[])list.get(0))[0];
			this.minValue = ((Double[])list.get(0))[1];
		}
		for(int i = 1; i < list.size();i++){
			Double[] temp = (Double[])list.get(i);
			if(ArithmeticUtil.sub(temp[0],this.maxValue) > 0){
				this.maxValue = temp[0];
			}
			if(ArithmeticUtil.sub(temp[1],this.minValue) < 0){
				this.minValue = temp[1];
			}
		}
		Double interval = ArithmeticUtil.sub(this.maxValue, this.minValue);
		interval = ArithmeticUtil.multi(interval, 0.1);
		
		this.maxValue = ArithmeticUtil.round(ArithmeticUtil.add(this.maxValue, interval),2);
		this.minValue = ArithmeticUtil.round(ArithmeticUtil.sub(this.minValue, interval),2);
		
		//生成曲线数据
	    generateTotalCurveData(curveType, realCheckValue, statCheckValue, conCheckValue);
	    
		return SUCCESS;
	}
	
	/**
	 * 查询测量点曲线
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryMpData() throws Exception {

		if (null == curveType || "".equals(curveType)) {
			return SUCCESS;
		}
		
		if(!realCheckValue && !statCheckValue){
			return SUCCESS;
		}
		
		generalPowerModelList = generalAnalyseManager.getTimeModelList(freezeCycleNum);
		
		dynamicMpCrtCm(curveType, realCheckValue, statCheckValue, conCheckValue);
		
		List<Double[]> list = new ArrayList<Double[]>();
		
		if ("1".equals(curveType)) {//功率
			if(realCheckValue){//是否显示实时数据
				this.mpDayRealPowerList = generalAnalyseManager.findMpRealPowerData(dataId, freezeCycleNum, dataDate, consNo);
		        
		        //获取最大值和最小值
		        if(!isPowerNullValue(mpDayRealPowerList)){
		        	list.add(checkPowerMaxMinValue(mpDayRealPowerList));
		        }
		        
		        if(this.conCheckValue){//是否显示对比日数据
		        	this.mpDayRealPowerConList = generalAnalyseManager.findMpRealPowerData(dataId, freezeCycleNum, contrastDate, consNo);
		        	
		        	if(!isPowerNullValue(mpDayRealPowerConList)){
			        	list.add(checkPowerMaxMinValue(mpDayRealPowerConList));
			        }
		        }
			}
	        
	        if(this.statCheckValue){//是否显示冻结数据
		    	this.generalPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalAPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalBPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, dataDate, DATA_TYPE3, consNo);
				this.generalCPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, dataDate, DATA_TYPE4, consNo);
				
		        //获取最大值和最小值
				if(!isStatNullValue(generalPowerData)){
		        	list.add(checkStatMaxMinValue(generalPowerData));
		        }
				
				if(!isStatNullValue(generalAPowerData)){
		        	list.add(checkStatMaxMinValue(generalAPowerData));
		        }
				
				if(!isStatNullValue(generalBPowerData)){
		        	list.add(checkStatMaxMinValue(generalBPowerData));
		        }
				
				if(!isStatNullValue(generalCPowerData)){
		        	list.add(checkStatMaxMinValue(generalCPowerData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastAPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum,  contrastDate, DATA_TYPE2, consNo);
					this.contrastBPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, contrastDate, DATA_TYPE3, consNo);
					this.contrastCPowerData = generalAnalyseManager.findMpPowerData(dataId, freezeCycleNum, contrastDate, DATA_TYPE4, consNo);
					
					if(!isStatNullValue(contrastPowerData)){
			        	list.add(checkStatMaxMinValue(contrastPowerData));
			        }
					
					if(!isStatNullValue(contrastAPowerData)){
			        	list.add(checkStatMaxMinValue(contrastAPowerData));
			        }
					
					if(!isStatNullValue(contrastBPowerData)){
			        	list.add(checkStatMaxMinValue(contrastBPowerData));
			        }
					
					if(!isStatNullValue(contrastCPowerData)){
			        	list.add(checkStatMaxMinValue(contrastCPowerData));
			        }
				}
		    }
		    if(null != generalPowerData && 0 < generalPowerData.size()){
		    	generalPowerName = "查询日冻结功率";
		    }
		    if(null != contrastPowerData && 0 < contrastPowerData.size()){
		    	contrastPowerName = "对比日冻结功率";
		    }
		    if(null != generalAPowerData && 0 < generalAPowerData.size()){
		    	generalAPowerName = "查询日冻结A相功率";
		    }
		    if(null != contrastAPowerData && 0 < contrastAPowerData.size()){
		    	contrastAPowerName = "对比日冻结A相功率";
		    }
		    if(null != generalBPowerData && 0 < generalBPowerData.size()){
		    	generalBPowerName = "查询日冻结B相功率";
		    }
		    if(null != contrastBPowerData && 0 < contrastBPowerData.size()){
		    	contrastBPowerName = "对比日冻结B相功率";
		    }
		    if(null != generalCPowerData && 0 < generalCPowerData.size()){
		    	generalCPowerName = "查询日冻结C相功率";
		    }
		    if(null != contrastCPowerData && 0 < contrastCPowerData.size()){
		    	contrastCPowerName = "对比日冻结C相功率";
		    }
		    if(null != mpDayRealPowerList && 0 < mpDayRealPowerList.size()){
		    	mpDayRealPowerName = "查询日实时功率";
		    }
		    if(null != mpDayRealPowerConList && 0 < mpDayRealPowerConList.size()){
		    	mpDayRealPowerConName = "对比日实时功率";
		    }
		}
		if ("2".equals(curveType)) {//电压
			if(realCheckValue){//是否显示实时数据
				this.mpDayRealVoltList = generalAnalyseManager.findMpRealVoltData(dataId, freezeCycleNum, dataDate, consNo);
			    
		        //获取最大值和最小值
		        if(!isVoltNullValue(mpDayRealVoltList)){
		        	list.add(checkVoltMaxMinValue(mpDayRealVoltList));
		        }
		        
		        if(this.conCheckValue){//是否显示对比日数据
		        	this.mpDayRealVoltConList = generalAnalyseManager.findMpRealVoltData(dataId, freezeCycleNum, contrastDate, consNo);
		        	
		        	if(!isVoltNullValue(mpDayRealVoltConList)){
		 	        	list.add(checkVoltMaxMinValue(mpDayRealVoltConList));
		 	        }
		        }
			}
	        
	        if(this.statCheckValue){//是否显示冻结数据
				this.generalAVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalBVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalCVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, dataDate, DATA_TYPE3, consNo);
		        
				//获取最大值和最小值
				if(!isStatNullValue(generalAVoltData)){
		        	list.add(checkStatMaxMinValue(generalAVoltData));
		        }
				
				if(!isStatNullValue(generalBVoltData)){
		        	list.add(checkStatMaxMinValue(generalBVoltData));
		        }
				
				if(!isStatNullValue(generalCVoltData)){
		        	list.add(checkStatMaxMinValue(generalCVoltData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastAVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastBVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastCVoltData = generalAnalyseManager.findMpVolData(dataId, freezeCycleNum, contrastDate, DATA_TYPE3, consNo);
					
					if(!isStatNullValue(contrastAVoltData)){
			        	list.add(checkStatMaxMinValue(contrastAVoltData));
			        }
					
					if(!isStatNullValue(contrastBVoltData)){
			        	list.add(checkStatMaxMinValue(contrastBVoltData));
			        }
					
					if(!isStatNullValue(contrastCVoltData)){
			        	list.add(checkStatMaxMinValue(contrastCVoltData));
			        }
				}
	        }
		    if(null != generalAVoltData && 0 < generalAVoltData.size()){
		    	generalAVoltName = "查询日冻结A相电压";
		    }
		    if(null != contrastAVoltData && 0 < contrastAVoltData.size()){
		    	contrastAVoltName = "对比日冻结A相电压";
		    }
		    if(null != generalBVoltData && 0 < generalBVoltData.size()){
		    	generalBVoltName = "查询日冻结B相电压";
		    }
		    if(null != contrastBVoltData && 0 < contrastBVoltData.size()){
		    	contrastBVoltName = "对比日冻结B相电压";
		    }
		    if(null != generalCVoltData && 0 < generalCVoltData.size()){
		    	generalCVoltName = "查询日冻结C相电压";
		    }
		    if(null != contrastCVoltData && 0 < contrastCVoltData.size()){
		    	contrastCVoltName = "对比日冻结C相电压";
		    }
		    if(null != mpDayRealVoltList && 0 < mpDayRealVoltList.size()){
		    	mpDayRealVoltName = "查询日实时电压";
		    }
		    if(null != mpDayRealVoltConList && 0 < mpDayRealVoltConList.size()){
		    	mpDayRealVoltConName = "对比日实时电压";
		    }
		}
		if ("3".equals(curveType)) {//电流
			if(realCheckValue){//是否显示实时数据
		    	this.mpDayRealCurList = generalAnalyseManager.findMpRealCurData(dataId, freezeCycleNum, dataDate, consNo);
			    
		        //获取最大值和最小值
		        if(!isCurNullValue(mpDayRealCurList)){
		        	list.add(checkCurMaxMinValue(mpDayRealCurList));
		        }
		        
		        if(this.conCheckValue){//是否显示对比日数据
		        	this.mpDayRealCurConList = generalAnalyseManager.findMpRealCurData(dataId, freezeCycleNum, contrastDate, consNo);
		        	
		        	if(!isCurNullValue(mpDayRealCurConList)){
			        	list.add(checkCurMaxMinValue(mpDayRealCurConList));
			        }
		        }
			}
			
	        if(this.statCheckValue){//是否显示冻结数据
				this.generalCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, dataDate, DATA_TYPE0, consNo);
				this.generalACurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalBCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalCCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, dataDate, DATA_TYPE3, consNo);
				
				//获取最大值和最小值
				if(!isStatNullValue(generalCurData)){
		        	list.add(checkStatMaxMinValue(generalCurData));
		        }
				
				if(!isStatNullValue(generalACurData)){
		        	list.add(checkStatMaxMinValue(generalACurData));
		        }
				
				if(!isStatNullValue(generalBCurData)){
		        	list.add(checkStatMaxMinValue(generalBCurData));
		        }
				
				if(!isStatNullValue(generalCCurData)){
		        	list.add(checkStatMaxMinValue(generalCCurData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, contrastDate, DATA_TYPE0, consNo);
					this.contrastACurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastBCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastCCurData = generalAnalyseManager.findMpCurData(dataId, freezeCycleNum, contrastDate, DATA_TYPE3, consNo);
					
					if(!isStatNullValue(contrastCurData)){
			        	list.add(checkStatMaxMinValue(contrastCurData));
			        }
					
					if(!isStatNullValue(contrastACurData)){
			        	list.add(checkStatMaxMinValue(contrastACurData));
			        }
					
					if(!isStatNullValue(contrastBCurData)){
			        	list.add(checkStatMaxMinValue(contrastBCurData));
			        }
					
					if(!isStatNullValue(contrastCCurData)){
			        	list.add(checkStatMaxMinValue(contrastCCurData));
			        }
				}
		    }
		    if(null != generalCurData && 0 < generalCurData.size()){
		    	generalCurName = "查询日冻结电流";
		    }
		    if(null != contrastCurData && 0 < contrastCurData.size()){
		    	contrastCurName = "对比日冻结电流";
		    }
		    if(null != generalACurData && 0 < generalACurData.size()){
		    	generalACurName = "查询日冻结A相电流";
		    }
		    if(null != contrastACurData && 0 < contrastACurData.size()){
		    	contrastACurName = "对比日冻结A相电流";
		    }
		    if(null != generalBCurData && 0 < generalBCurData.size()){
		    	generalBCurName = "查询日冻结B相电流";
		    }
		    if(null != contrastBCurData && 0 < contrastBCurData.size()){
		    	contrastBCurName = "对比日冻结B相电流";
		    }
		    if(null != generalCCurData && 0 < generalCCurData.size()){
		    	generalCCurName = "查询日冻结C相电流";
		    }
		    if(null != contrastCCurData && 0 < contrastCCurData.size()){
		    	contrastCCurName = "对比日冻结C相电流";
		    }
		    if(null != mpDayRealCurList && 0 < mpDayRealCurList.size()){
		    	mpDayRealCurName = "查询日实时电流";
		    }
		    if(null != mpDayRealCurConList && 0 < mpDayRealCurConList.size()){
		    	mpDayRealCurConName = "对比日实时电流";
		    }
		}
		if ("4".equals(curveType)) {//功率因数
			if(realCheckValue){//是否显示实时数据
		    	this.mpDayRealFactorList = generalAnalyseManager.findMpRealFactorData(dataId, freezeCycleNum, dataDate, consNo);
			    
		        //获取最大值和最小值
		        if(!isFactorNullValue(mpDayRealFactorList)){
		        	list.add(checkFactorMaxMinValue(mpDayRealFactorList));
		        }
		        
		        if(this.conCheckValue){//是否显示对比日数据
		        	this.mpDayRealFactorConList = generalAnalyseManager.findMpRealFactorData(dataId, freezeCycleNum, contrastDate, consNo);
		        	
		        	if(!isFactorNullValue(mpDayRealFactorConList)){
			        	list.add(checkFactorMaxMinValue(mpDayRealFactorConList));
			        }
		        }
			}
			
	        if(this.statCheckValue){//是否显示冻结数据
				this.generalFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, dataDate, DATA_TYPE0, consNo);
				this.generalAFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalBFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalCFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, dataDate, DATA_TYPE3, consNo);
		         
				//获取最大值和最小值
				if(!isStatNullValue(generalFactorData)){
		        	list.add(checkStatMaxMinValue(generalFactorData));
		        }
				
				if(!isStatNullValue(generalAFactorData)){
		        	list.add(checkStatMaxMinValue(generalAFactorData));
		        }
				
				if(!isStatNullValue(generalBFactorData)){
		        	list.add(checkStatMaxMinValue(generalBFactorData));
		        }
				
				if(!isStatNullValue(generalCFactorData)){
		        	list.add(checkStatMaxMinValue(generalCFactorData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, contrastDate, DATA_TYPE0, consNo);
					this.contrastAFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastBFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastCFactorData = generalAnalyseManager.findMpFactorData(dataId, freezeCycleNum, contrastDate, DATA_TYPE3, consNo);
					
					if(!isStatNullValue(contrastFactorData)){
			        	list.add(checkStatMaxMinValue(contrastFactorData));
			        }
					
					if(!isStatNullValue(contrastAFactorData)){
			        	list.add(checkStatMaxMinValue(contrastAFactorData));
			        }
					
					if(!isStatNullValue(contrastBFactorData)){
			        	list.add(checkStatMaxMinValue(contrastBFactorData));
			        }
					
					if(!isStatNullValue(contrastCFactorData)){
			        	list.add(checkStatMaxMinValue(contrastCFactorData));
			        }
				}
	        }
		    if(null != generalFactorData && 0 < generalFactorData.size()){
		    	generalFactorName = "查询日冻结功率因数";
		    }
		    if(null != contrastFactorData && 0 < contrastFactorData.size()){
		    	contrastFactorName = "对比日冻结功率因数";
		    }
		    if(null != generalAFactorData && 0 < generalAFactorData.size()){
		    	generalAFactorName = "查询日冻结A相功率因数";
		    }
		    if(null != contrastAFactorData && 0 < contrastAFactorData.size()){
		    	contrastAFactorName = "对比日冻结A相功率因数";
		    }
		    if(null != generalBFactorData && 0 < generalBFactorData.size()){
		    	generalBFactorName = "查询日冻结B相功率因数";
		    }
		    if(null != contrastBFactorData && 0 < contrastBFactorData.size()){
		    	contrastBFactorName = "对比日冻结B相功率因数";
		    }
		    if(null != generalCFactorData && 0 < generalCFactorData.size()){
		    	generalCFactorName = "查询日冻结C相功率因数";
		    }
		    if(null != contrastCFactorData && 0 < contrastCFactorData.size()){
		    	contrastCFactorName = "对比日冻结C相功率因数";
		    }
		    if(null != mpDayRealFactorList && 0 < mpDayRealFactorList.size()){
		    	 mpDayRealFactorName = "查询日实时功率因数";
		    }
		    if(null != mpDayRealFactorConList && 0 < mpDayRealFactorConList.size()){
		    	 mpDayRealFactorConName = "对比日实时功率因数";
		    }
		}
		if ("5".equals(curveType)) {//电能量			
	        if(this.statCheckValue){//是否显示冻结数据
				this.generalEnergyPapData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalEnergyPrpData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalEnergyRapData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, dataDate, DATA_TYPE5, consNo);
				this.generalEnergyRrpData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, dataDate, DATA_TYPE6, consNo);
		         
				//获取最大值和最小值
				if(!isStatNullValue(generalEnergyPapData)){
		        	list.add(checkStatMaxMinValue(generalEnergyPapData));
		        }
				
				if(!isStatNullValue(generalEnergyPrpData)){
		        	list.add(checkStatMaxMinValue(generalEnergyPrpData));
		        }
				
				if(!isStatNullValue(generalEnergyRapData)){
		        	list.add(checkStatMaxMinValue(generalEnergyRapData));
		        }
				
				if(!isStatNullValue(generalEnergyRrpData)){
		        	list.add(checkStatMaxMinValue(generalEnergyRrpData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastEnergyPapData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastEnergyPrpData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastEnergyRapData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, contrastDate, DATA_TYPE5, consNo);
					this.contrastEnergyRrpData = generalAnalyseManager.findMpEnergyData(dataId, freezeCycleNum, contrastDate, DATA_TYPE6, consNo);
					
					if(!isStatNullValue(contrastEnergyPapData)){
			        	list.add(checkStatMaxMinValue(contrastEnergyPapData));
			        }
					
					if(!isStatNullValue(contrastEnergyPrpData)){
			        	list.add(checkStatMaxMinValue(contrastEnergyPrpData));
			        }
					
					if(!isStatNullValue(contrastEnergyRapData)){
			        	list.add(checkStatMaxMinValue(contrastEnergyRapData));
			        }
					
					if(!isStatNullValue(contrastEnergyRrpData)){
			        	list.add(checkStatMaxMinValue(contrastEnergyRrpData));
			        }
				}
	        }
		    if(null != generalEnergyPapData && 0 < generalEnergyPapData.size()){
		    	generalEnergyPapName = "查询日冻结正向有功电能量";
		    }
		    if(null != contrastEnergyPapData && 0 < contrastEnergyPapData.size()){
		    	contrastEnergyPapName = "对比日冻结正向有功电能量";
		    }
		    if(null != generalEnergyPrpData && 0 < generalEnergyPrpData.size()){
		    	generalEnergyPrpName = "查询日冻结正向无功电能量";
		    }
		    if(null != contrastEnergyPrpData && 0 < contrastEnergyPrpData.size()){
		    	contrastEnergyPrpName = "对比日冻结正向无功电能量";
		    }
		    if(null != generalEnergyRapData && 0 < generalEnergyRapData.size()){
		    	generalEnergyRapName = "查询日冻结反向有功电能量";
		    }
		    if(null != contrastEnergyRapData && 0 < contrastEnergyRapData.size()){
		    	contrastEnergyRapName = "对比日冻结反向有功电能量";
		    }
		    if(null != generalEnergyRrpData && 0 < generalEnergyRrpData.size()){
		    	generalEnergyRrpName = "查询日冻结反向无功电能量";
		    }
		    if(null != contrastEnergyRrpData && 0 < contrastEnergyRrpData.size()){
		    	contrastEnergyRrpName = "对比日冻结反向无功电能量";
		    }
		}
		if ("6".equals(curveType)) {//电能示值		
	        if(this.statCheckValue){//是否显示冻结数据
				this.generalReadPapData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, dataDate, DATA_TYPE1, consNo);
				this.generalReadPrpData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, dataDate, DATA_TYPE2, consNo);
				this.generalReadRapData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, dataDate, DATA_TYPE5, consNo);
				this.generalReadRrpData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, dataDate, DATA_TYPE6, consNo);
		         
				//获取最大值和最小值
				if(!isStatNullValue(generalReadPapData)){
		        	list.add(checkStatMaxMinValue(generalReadPapData));
		        }
				
				if(!isStatNullValue(generalReadPrpData)){
		        	list.add(checkStatMaxMinValue(generalReadPrpData));
		        }
				
				if(!isStatNullValue(generalReadRapData)){
		        	list.add(checkStatMaxMinValue(generalReadRapData));
		        }
				
				if(!isStatNullValue(generalReadRrpData)){
		        	list.add(checkStatMaxMinValue(generalReadRrpData));
		        }
				
				if(this.conCheckValue){//是否显示对比日数据
					this.contrastReadPapData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, contrastDate, DATA_TYPE1, consNo);
					this.contrastReadPrpData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, contrastDate, DATA_TYPE2, consNo);
					this.contrastReadRapData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, contrastDate, DATA_TYPE5, consNo);
					this.contrastReadRrpData = generalAnalyseManager.findMpReadData(dataId, freezeCycleNum, contrastDate, DATA_TYPE6, consNo);
					
					if(!isStatNullValue(contrastReadPapData)){
			        	list.add(checkStatMaxMinValue(contrastReadPapData));
			        }
					
					if(!isStatNullValue(contrastReadPrpData)){
			        	list.add(checkStatMaxMinValue(contrastReadPrpData));
			        }
					
					if(!isStatNullValue(contrastReadRapData)){
			        	list.add(checkStatMaxMinValue(contrastReadRapData));
			        }
					
					if(!isStatNullValue(contrastReadRrpData)){
			        	list.add(checkStatMaxMinValue(contrastReadRrpData));
			        }
				}
	        }
		    if(null != generalReadPapData && 0 < generalReadPapData.size()){
		    	generalReadPapName = "查询日冻结正向有功电能示值";
		    }
		    if(null != contrastReadPapData && 0 < contrastReadPapData.size()){
		    	contrastReadPapName = "对比日冻结正向有功电能示值";
		    }
		    if(null != generalReadPrpData && 0 < generalReadPrpData.size()){
		    	generalReadPrpName = "查询日冻结正向无功电能示值";
		    }
		    if(null != contrastReadPrpData && 0 < contrastReadPrpData.size()){
		    	contrastReadPrpName = "对比日冻结正向无功电能示值";
		    }
		    if(null != generalReadRapData && 0 < generalReadRapData.size()){
		    	generalReadRapName = "查询日冻结反向有功电能示值";
		    }
		    if(null != contrastReadRapData && 0 < contrastReadRapData.size()){
		    	contrastReadRapName = "对比日冻结反向有功电能示值";
		    }
		    if(null != generalReadRrpData && 0 < generalReadRrpData.size()){
		    	generalReadRrpName = "查询日冻结反向无功电能示值";
		    }
		    if(null != contrastReadRrpData && 0 < contrastReadRrpData.size()){
		    	contrastReadRrpName = "对比日冻结反向无功电能示值";
		    }
		}
		
		//获取最大值和最小值
		int j = list.size();
		if(j > 0){
			this.maxValue = ((Double[])list.get(0))[0];
			this.minValue = ((Double[])list.get(0))[1];
		}
		for(int i = 1; i < list.size();i++){
			Double[] temp = (Double[])list.get(i);
			if(ArithmeticUtil.sub(temp[0],this.maxValue) > 0){
				this.maxValue = temp[0];
			}
			if(ArithmeticUtil.sub(temp[1],this.minValue) < 0){
				this.minValue = temp[1];
			}
		}
		
		Double interval = ArithmeticUtil.sub(this.maxValue, this.minValue);
		//这里对y轴显示小数点2位做处理，但是如果最大值和最小值之间都是小数点之后3位才有值，那么显示按照fusionchart自己调节
		interval = ArithmeticUtil.multi(interval, 0.1);
		
		this.maxValue = ArithmeticUtil.round(ArithmeticUtil.add(this.maxValue, interval),2);
		this.minValue = ArithmeticUtil.round(ArithmeticUtil.sub(this.minValue, interval),2);
		
        //生成曲线数据
	    generateMpCurveData(curveType, realCheckValue, statCheckValue, conCheckValue);
		return SUCCESS;
	}

	/**
	 * 加入群组
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addToGroup() throws Exception {
		if(null == groupNo || null == consNo){
			return SUCCESS;
		}
		try {
			super.addUserLog("加入群组", "采集数据综合分析", "02");
			generalAnalyseManager.addToGroup(groupNo, consNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 组装header（测量点）
	 * 
	 * @return
	 * @throws Exception
	 */
	public void dynamicMpCrtCm(String curveType, boolean realCheckValue, boolean statCheckValue, boolean conCheckValue){
		HashMap<String, String> n = null;

		//第一列是固定的
		n = new HashMap<String, String>();
		n.put("header","时间点");
		n.put("dataIndex","time");
		headers.add(n);
		
		if("1".equals(curveType)){
        	if(realCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时总功率(kW)");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时A相功率(kW)");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时B相功率(kW)");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时C相功率(kW)");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时总功率(kW)");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时A相功率(kW)");
    				n.put("dataIndex","data6");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时B相功率(kW)");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时C相功率(kW)");
    				n.put("dataIndex","data8");
            		headers.add(n);
        		}
        	}
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结总功率(kW)");
				n.put("dataIndex","data9");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结A相功率(kW)");
				n.put("dataIndex","data10");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结B相功率(kW)");
				n.put("dataIndex","data11");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结C相功率(kW)");
				n.put("dataIndex","data12");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结总功率(kW)");
    				n.put("dataIndex","data13");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结A相功率(kW)");
    				n.put("dataIndex","data14");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结B相功率(kW)");
    				n.put("dataIndex","data15");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结C相功率(kW)");
    				n.put("dataIndex","data16");
            		headers.add(n);
        		}
        	}
        }else if ("2".equals(curveType)){
        	if(realCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日实A相电压(V)");
				n.put("dataIndex","data1");
        		headers.add(n);
        		        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时B相电压(V)");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时C相电压(V)");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		if(conCheckValue){
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时A相电压(V)");
    				n.put("dataIndex","data4");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时B相电压(V)");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时C相电压(V)");
    				n.put("dataIndex","data6");
            		headers.add(n);
        		}
        	}
        	if(statCheckValue){        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结A相电压(V)");
				n.put("dataIndex","data7");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结B相电压(V)");
				n.put("dataIndex","data8");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结C相电压(V)");
				n.put("dataIndex","data9");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结A相电压(V)");
    				n.put("dataIndex","data10");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结B相电压(V)");
    				n.put("dataIndex","data11");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结C相电压(V)");
    				n.put("dataIndex","data12");
            		headers.add(n);
        		}
        	}
        }else if ("3".equals(curveType)){
        	if(realCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时零序电流(A)");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时A相电流(A)");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时B相电流(A)");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时C相电流(A)");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时零序电流(A)");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时A相电流(A)");
    				n.put("dataIndex","data6");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时B相电流(A)");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时C相电流(A)");
    				n.put("dataIndex","data8");
            		headers.add(n);
        		}
        	}
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结零序电流(A)");
				n.put("dataIndex","data9");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结A相电流(A)");
				n.put("dataIndex","data10");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结B相电流(A)");
				n.put("dataIndex","data11");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结C相电流(A)");
				n.put("dataIndex","data12");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结零序电流(A)");
    				n.put("dataIndex","data13");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结A相电流(A)");
    				n.put("dataIndex","data14");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结B相电流(A)");
    				n.put("dataIndex","data15");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结C相电流(A)");
    				n.put("dataIndex","data16");
            		headers.add(n);
        		}
        	}
        }else if ("4".equals(curveType)){
        	if(realCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时功率因数");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时A相功率因数");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时B相功率因数");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时C相功率因数");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时功率因数");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时A相功率因数");
    				n.put("dataIndex","data6");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时B相功率因数");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日实时C相功率因数");
    				n.put("dataIndex","data8");
            		headers.add(n);
        		}
        	}
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结功率因数");
				n.put("dataIndex","data9");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结A相功率因数");
				n.put("dataIndex","data10");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结B相功率因数");
				n.put("dataIndex","data11");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结C相功率因数");
				n.put("dataIndex","data12");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结功率因数");
    				n.put("dataIndex","data13");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结A相功率因数");
    				n.put("dataIndex","data14");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结B相功率因数");
    				n.put("dataIndex","data15");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结C相功率因数");
    				n.put("dataIndex","data16");
            		headers.add(n);
        		}
        	}
        }else if ("5".equals(curveType)){//电能量
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结正向有功电能量(kWh)");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结正向有无电能量(kWh)");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结反向有功电能量(kWh)");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结反向无功电能量(kWh)");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结正向有功电能量(kWh)");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结正向无功电能量(kWh)");
    				n.put("dataIndex","data6");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结反向有功电能量(kWh)");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结反向无功电能量(kWh)");
    				n.put("dataIndex","data8");
            		headers.add(n);
        		}
        	}
        }
        else if ("6".equals(curveType)){//电能示值
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结正向有功电能示值");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结正向有无电能示值");
				n.put("dataIndex","data2");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结反向有功电能示值");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结反向有无电能示值");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结正向有功电能示值");
    				n.put("dataIndex","data5");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结正向无功电能示值");
    				n.put("dataIndex","data6");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结反向有功电能示值");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结反向无功电能示值");
    				n.put("dataIndex","data8");
            		headers.add(n);
        		}
        	}
        }
	}
	
	/**
	 * 组装header（总加组）
	 * 
	 * @return
	 * @throws Exception
	 */
	public void dynamicTotalCrtCm(String curveType, boolean realCheckValue, boolean statCheckValue, boolean conCheckValue){
		HashMap<String, String> n = null;

		//第一列是固定的
		n = new HashMap<String, String>();
		n.put("header","时间点");
		n.put("dataIndex","time");
		headers.add(n);
		
		if("1".equals(curveType)){
        	if(realCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日实时总功率(kW)");
				n.put("dataIndex","data1");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日实时总功率(kW)");
    				n.put("dataIndex","data2");
            		headers.add(n);
        		}
        	}
        	if(statCheckValue){
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结总功率(kW)");
				n.put("dataIndex","data3");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结A相功率(kW)");
				n.put("dataIndex","data4");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结B相功率(kW)");
				n.put("dataIndex","data5");
        		headers.add(n);
        		
        		n = new HashMap<String, String>();
        		n.put("header","查询日冻结C相功率(kW)");
				n.put("dataIndex","data6");
        		headers.add(n);
        		
        		if(conCheckValue){
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结总功率(kW)");
    				n.put("dataIndex","data7");
            		headers.add(n);
            		
        			n = new HashMap<String, String>();
            		n.put("header","对比日冻结A相功率(kW)");
    				n.put("dataIndex","data8");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结B相功率(kW)");
    				n.put("dataIndex","data9");
            		headers.add(n);
            		
            		n = new HashMap<String, String>();
            		n.put("header","对比日冻结C相功率(kW)");
    				n.put("dataIndex","data10");
            		headers.add(n);
        		}
        	}
		}
	}
	
	/**
	 * 查询曲线数据（总加组）
	 * 
	 * @return
	 * @throws Exception
	 */
	public void generateTotalCurveData(String curveType, boolean realCheckValue, boolean statCheckValue, boolean conCheckValue){	
		if(null == generalPowerModelList){//如果时间点为空，则返回
			return;
		}
		
		for(int m = 0; m < generalPowerModelList.size(); m++){
			GeneralCurveDataBean curveBean = new GeneralCurveDataBean();
			curveBean.setTime(generalPowerModelList.get(m).getTime());
			curveBean.setId(m);
			generalCurveDataList.add(curveBean);
		}
		
		if("1".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(realCheckValue){//是否显示实时数据
					if(null != mpDayRealPowerList && 0 < mpDayRealPowerList.size()){
						GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)mpDayRealPowerList.get(j);
						if(bean.getBeanFlag()){//如果为true，表示全部漏点
							curveTmpBean.setData1(null);
						}else{//如果为false，则针对每个数据进行筛选
							if(bean.getFlag()){
								curveTmpBean.setData1(null);
							}else{
								curveTmpBean.setData1(bean.getPower());
							}
						}
					}
					if(conCheckValue){//是否显示对比日实时数据
						if(null != mpDayRealPowerConList && 0 < mpDayRealPowerConList.size()){
							GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)mpDayRealPowerConList.get(j);
							if(bean.getBeanFlag()){//如果为true，表示全部漏点
								curveTmpBean.setData5(null);
							}else{//如果为false，则针对每个数据进行筛选
								if(bean.getFlag()){
									curveTmpBean.setData5(null);
								}else{
									curveTmpBean.setData5(bean.getPower());
								}
							}
					    }
				    }
				}
				if(statCheckValue){//是否显示冻结数据
					if(null != generalPowerData && 0 < generalPowerData.size()){
						GeneralDataBean bean = generalPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData9(null);
						}else{
							curveTmpBean.setData9(bean.getData());
						}
					}
					if(null != generalAPowerData && 0 < generalAPowerData.size()){
						GeneralDataBean bean = generalAPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData10(null);
						}else{
							curveTmpBean.setData10(bean.getData());
						}
					}
					if(null != generalBPowerData && 0 < generalBPowerData.size()){
						GeneralDataBean bean = generalBPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData11(null);
						}else{
							curveTmpBean.setData11(bean.getData());
						}
					}
					if(null != generalCPowerData && 0 < generalCPowerData.size()){
						GeneralDataBean bean = generalCPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData12(null);
						}else{
							curveTmpBean.setData12(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastPowerData && 0 < contrastPowerData.size()){
							GeneralDataBean bean = contrastPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData13(null);
							}else{
								curveTmpBean.setData13(bean.getData());
							}
						}
						if(null != contrastAPowerData && 0 < contrastAPowerData.size()){
							GeneralDataBean bean = contrastAPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData14(null);
							}else{
								curveTmpBean.setData14(bean.getData());
							}
						}
						if(null != contrastBPowerData && 0 < contrastBPowerData.size()){
							GeneralDataBean bean = contrastBPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData15(null);
							}else{
								curveTmpBean.setData15(bean.getData());
							}
						}
						if(null != contrastCPowerData && 0 < contrastCPowerData.size()){
							GeneralDataBean bean = contrastCPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData16(null);
							}else{
								curveTmpBean.setData16(bean.getData());
							}
						}
					}
				}
		    }
		}
	}
	
	/**
	 * 查询曲线数据（测量点）
	 * 
	 * @return
	 * @throws Exception
	 */
	public void generateMpCurveData(String curveType, boolean realCheckValue, boolean statCheckValue, boolean conCheckValue){	
		if(null == generalPowerModelList){//如果时间点为空，则返回
			return;
		}
		
		for(int m = 0; m < generalPowerModelList.size(); m++){
			GeneralCurveDataBean curveBean = new GeneralCurveDataBean();
			curveBean.setTime(generalPowerModelList.get(m).getTime());
			curveBean.setId(m);
			generalCurveDataList.add(curveBean);
		}
		
		if("1".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(realCheckValue){//是否显示实时数据
					if(null != mpDayRealPowerList && 0 < mpDayRealPowerList.size()){
						GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)mpDayRealPowerList.get(j);
						if(bean.getBeanFlag()){//如果为true，表示全部漏点
							curveTmpBean.setData1(null);
							curveTmpBean.setData2(null);
							curveTmpBean.setData3(null);
							curveTmpBean.setData4(null);
						}else{//如果为false，则针对每个数据进行筛选
							if(bean.getFlag()){
								curveTmpBean.setData1(null);
							}else{
								curveTmpBean.setData1(bean.getPower());
							}
							if(bean.getFlagA()){
								curveTmpBean.setData2(null);
							}else{
								curveTmpBean.setData2(bean.getPowerA());
							}
							if(bean.getFlagB()){
								curveTmpBean.setData3(null);
							}else{
								curveTmpBean.setData3(bean.getPowerB());
							}
							if(bean.getFlagC()){
								curveTmpBean.setData4(null);
							}else{
								curveTmpBean.setData4(bean.getPowerC());
							}
						}
					}
					if(conCheckValue){//是否显示对比日实时数据
						if(null != mpDayRealPowerConList && 0 < mpDayRealPowerConList.size()){
							GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)mpDayRealPowerConList.get(j);
							if(bean.getBeanFlag()){//如果为true，表示全部漏点
								curveTmpBean.setData5(null);
								curveTmpBean.setData6(null);
								curveTmpBean.setData7(null);
								curveTmpBean.setData8(null);
							}else{//如果为false，则针对每个数据进行筛选
								if(bean.getFlag()){
									curveTmpBean.setData5(null);
								}else{
									curveTmpBean.setData5(bean.getPower());
								}
								if(bean.getFlagA()){
									curveTmpBean.setData6(null);
								}else{
									curveTmpBean.setData6(bean.getPowerA());
								}
								if(bean.getFlagB()){
									curveTmpBean.setData7(null);
								}else{
									curveTmpBean.setData7(bean.getPowerB());
								}
								if(bean.getFlagC()){
									curveTmpBean.setData8(null);
								}else{
									curveTmpBean.setData8(bean.getPowerC());
								}
							}
					    }
				    }
				}
				if(statCheckValue){//是否显示冻结数据
					if(null != generalPowerData && 0 < generalPowerData.size()){
						GeneralDataBean bean = generalPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData9(null);
						}else{
							curveTmpBean.setData9(bean.getData());
						}
					}
					if(null != generalAPowerData && 0 < generalAPowerData.size()){
						GeneralDataBean bean = generalAPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData10(null);
						}else{
							curveTmpBean.setData10(bean.getData());
						}
					}
					if(null != generalBPowerData && 0 < generalBPowerData.size()){
						GeneralDataBean bean = generalBPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData11(null);
						}else{
							curveTmpBean.setData11(bean.getData());
						}
					}
					if(null != generalCPowerData && 0 < generalCPowerData.size()){
						GeneralDataBean bean = generalCPowerData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData12(null);
						}else{
							curveTmpBean.setData12(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastPowerData && 0 < contrastPowerData.size()){
							GeneralDataBean bean = contrastPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData13(null);
							}else{
								curveTmpBean.setData13(bean.getData());
							}
						}
						if(null != contrastAPowerData && 0 < contrastAPowerData.size()){
							GeneralDataBean bean = contrastAPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData14(null);
							}else{
								curveTmpBean.setData14(bean.getData());
							}
						}
						if(null != contrastBPowerData && 0 < contrastBPowerData.size()){
							GeneralDataBean bean = contrastBPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData15(null);
							}else{
								curveTmpBean.setData15(bean.getData());
							}
						}
						if(null != contrastCPowerData && 0 < contrastCPowerData.size()){
							GeneralDataBean bean = contrastCPowerData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData16(null);
							}else{
								curveTmpBean.setData16(bean.getData());
							}
						}
					}
				}
		    }
		}else if("2".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(realCheckValue){//是否显示实时数据
					if(null != mpDayRealVoltList && 0 < mpDayRealVoltList.size()){
						GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)mpDayRealVoltList.get(j);
						if(bean.getBeanFlag()){//如果为true，表示全部漏点
							curveTmpBean.setData1(null);
							curveTmpBean.setData2(null);
							curveTmpBean.setData3(null);
						}else{//如果为false，则针对每个数据进行筛选
							if(bean.getFlagA()){
								curveTmpBean.setData1(null);
							}else{
								curveTmpBean.setData1(bean.getVoltA());
							}
							if(bean.getFlagB()){
								curveTmpBean.setData2(null);
							}else{
								curveTmpBean.setData2(bean.getVoltB());
							}
							if(bean.getFlagC()){
								curveTmpBean.setData3(null);
							}else{
								curveTmpBean.setData3(bean.getVoltC());
							}
						}
					}
					if(conCheckValue){//是否显示对比日实时数据
						if(null != mpDayRealVoltConList && 0 < mpDayRealVoltConList.size()){
							GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)mpDayRealVoltConList.get(j);
							if(bean.getBeanFlag()){//如果为true，表示全部漏点
								curveTmpBean.setData4(null);
								curveTmpBean.setData5(null);
								curveTmpBean.setData6(null);
							}else{//如果为false，则针对每个数据进行筛选
								if(bean.getFlagA()){
									curveTmpBean.setData4(null);
								}else{
									curveTmpBean.setData4(bean.getVoltA());
								}
								if(bean.getFlagB()){
									curveTmpBean.setData5(null);
								}else{
									curveTmpBean.setData5(bean.getVoltB());
								}
								if(bean.getFlagC()){
									curveTmpBean.setData6(null);
								}else{
									curveTmpBean.setData6(bean.getVoltC());
								}
							}
					    }
				    }
				}
				if(statCheckValue){//是否显示冻结数据
					if(null != generalAVoltData && 0 < generalAVoltData.size()){
						GeneralDataBean bean = generalAVoltData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData7(null);
						}else{
							curveTmpBean.setData7(bean.getData());
						}
					}
					if(null != generalBVoltData && 0 < generalBVoltData.size()){
						GeneralDataBean bean = generalBVoltData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData8(null);
						}else{
							curveTmpBean.setData8(bean.getData());
						}
					}
					if(null != generalCVoltData && 0 < generalCVoltData.size()){
						GeneralDataBean bean = generalCVoltData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData9(null);
						}else{
							curveTmpBean.setData9(bean.getData());
						}
					}
					
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastAVoltData && 0 < contrastAVoltData.size()){
							GeneralDataBean bean = contrastAVoltData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData10(null);
							}else{
								curveTmpBean.setData10(bean.getData());
							}
						}
						if(null != contrastBVoltData && 0 < contrastBVoltData.size()){
							GeneralDataBean bean = contrastBVoltData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData11(null);
							}else{
								curveTmpBean.setData11(bean.getData());
							}
						}
						if(null != contrastCVoltData && 0 < contrastCVoltData.size()){
							GeneralDataBean bean = contrastCVoltData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData12(null);
							}else{
								curveTmpBean.setData12(bean.getData());
							}
						}
					}
				}
		    }
		}else if("3".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(realCheckValue){//是否显示实时数据
					if(null != mpDayRealCurList && 0 < mpDayRealCurList.size()){
						GeneralRealCurDataBean bean = (GeneralRealCurDataBean)mpDayRealCurList.get(j);
						if(bean.getBeanFlag()){//如果为true，表示全部漏点
							curveTmpBean.setData1(null);
							curveTmpBean.setData2(null);
							curveTmpBean.setData3(null);
							curveTmpBean.setData4(null);
						}else{//如果为false，则针对每个数据进行筛选
							if(bean.getFlag0()){
								curveTmpBean.setData1(null);
							}else{
								curveTmpBean.setData1(bean.getCur0());
							}
							if(bean.getFlagA()){
								curveTmpBean.setData2(null);
							}else{
								curveTmpBean.setData2(bean.getCurA());
							}
							if(bean.getFlagB()){
								curveTmpBean.setData3(null);
							}else{
								curveTmpBean.setData3(bean.getCurB());
							}
							if(bean.getFlagC()){
								curveTmpBean.setData4(null);
							}else{
								curveTmpBean.setData4(bean.getCurC());
							}
						}
					}
					if(conCheckValue){//是否显示对比日实时数据
						if(null != mpDayRealCurConList && 0 < mpDayRealCurConList.size()){
							GeneralRealCurDataBean bean = (GeneralRealCurDataBean)mpDayRealCurConList.get(j);
							if(bean.getBeanFlag()){//如果为true，表示全部漏点
								curveTmpBean.setData5(null);
								curveTmpBean.setData6(null);
								curveTmpBean.setData7(null);
								curveTmpBean.setData8(null);
							}else{//如果为false，则针对每个数据进行筛选
								if(bean.getFlag0()){
									curveTmpBean.setData5(null);
								}else{
									curveTmpBean.setData5(bean.getCur0());
								}
								if(bean.getFlagA()){
									curveTmpBean.setData6(null);
								}else{
									curveTmpBean.setData6(bean.getCurA());
								}
								if(bean.getFlagB()){
									curveTmpBean.setData7(null);
								}else{
									curveTmpBean.setData7(bean.getCurB());
								}
								if(bean.getFlagC()){
									curveTmpBean.setData8(null);
								}else{
									curveTmpBean.setData8(bean.getCurC());
								}
							}
					    }
				    }
				}
				if(statCheckValue){//是否显示冻结数据
					if(null != generalCurData && 0 < generalCurData.size()){
						GeneralDataBean bean = generalCurData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData9(null);
						}else{
							curveTmpBean.setData9(bean.getData());
						}
					}
					if(null != generalACurData && 0 < generalACurData.size()){
						GeneralDataBean bean = generalACurData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData10(null);
						}else{
							curveTmpBean.setData10(bean.getData());
						}
					}
					if(null != generalBCurData && 0 < generalBCurData.size()){
						GeneralDataBean bean = generalBCurData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData11(null);
						}else{
							curveTmpBean.setData11(bean.getData());
						}
					}
					if(null != generalCCurData && 0 < generalCCurData.size()){
						GeneralDataBean bean = generalCCurData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData12(null);
						}else{
							curveTmpBean.setData12(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastCurData && 0 < contrastCurData.size()){
							GeneralDataBean bean = contrastCurData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData13(null);
							}else{
								curveTmpBean.setData13(bean.getData());
							}
						}
						if(null != contrastACurData && 0 < contrastACurData.size()){
							GeneralDataBean bean = contrastACurData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData14(null);
							}else{
								curveTmpBean.setData14(bean.getData());
							}
						}
						if(null != contrastBCurData && 0 < contrastBCurData.size()){
							GeneralDataBean bean = contrastBCurData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData15(null);
							}else{
								curveTmpBean.setData15(bean.getData());
							}
						}
						if(null != contrastCCurData && 0 < contrastCCurData.size()){
							GeneralDataBean bean = contrastCCurData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData16(null);
							}else{
								curveTmpBean.setData16(bean.getData());
							}
						}
					}
				}
		    }
		}else if("4".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(realCheckValue){//是否显示实时数据
					if(null != mpDayRealFactorList && 0 < mpDayRealFactorList.size()){
						GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)mpDayRealFactorList.get(j);
						if(bean.getBeanFlag()){//如果为true，表示全部漏点
							curveTmpBean.setData1(null);
							curveTmpBean.setData2(null);
							curveTmpBean.setData3(null);
							curveTmpBean.setData4(null);
						}else{//如果为false，则针对每个数据进行筛选
							if(bean.getFlag()){
								curveTmpBean.setData1(null);
							}else{
								curveTmpBean.setData1(bean.getFactor());
							}
							if(bean.getFlagA()){
								curveTmpBean.setData2(null);
							}else{
								curveTmpBean.setData2(bean.getFactorA());
							}
							if(bean.getFlagB()){
								curveTmpBean.setData3(null);
							}else{
								curveTmpBean.setData3(bean.getFactorB());
							}
							if(bean.getFlagC()){
								curveTmpBean.setData4(null);
							}else{
								curveTmpBean.setData4(bean.getFactorC());
							}
						}
					}
					if(conCheckValue){//是否显示对比日实时数据
						if(null != mpDayRealFactorConList && 0 < mpDayRealFactorConList.size()){
							GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)mpDayRealFactorConList.get(j);
							if(bean.getBeanFlag()){//如果为true，表示全部漏点
								curveTmpBean.setData5(null);
								curveTmpBean.setData6(null);
								curveTmpBean.setData7(null);
								curveTmpBean.setData8(null);
							}else{//如果为false，则针对每个数据进行筛选
								if(bean.getFlag()){
									curveTmpBean.setData5(null);
								}else{
									curveTmpBean.setData5(bean.getFactor());
								}
								if(bean.getFlagA()){
									curveTmpBean.setData6(null);
								}else{
									curveTmpBean.setData6(bean.getFactorA());
								}
								if(bean.getFlagB()){
									curveTmpBean.setData7(null);
								}else{
									curveTmpBean.setData7(bean.getFactorB());
								}
								if(bean.getFlagC()){
									curveTmpBean.setData8(null);
								}else{
									curveTmpBean.setData8(bean.getFactorC());
								}
							}
					    }
				    }
				}
				if(statCheckValue){//是否显示冻结数据
					if(null != generalFactorData && 0 < generalFactorData.size()){
						GeneralDataBean bean = generalFactorData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData9(null);
						}else{
							curveTmpBean.setData9(bean.getData());
						}
					}
					if(null != generalAFactorData && 0 < generalAFactorData.size()){
						GeneralDataBean bean = generalAFactorData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData10(null);
						}else{
							curveTmpBean.setData10(bean.getData());
						}
					}
					if(null != generalBFactorData && 0 < generalBFactorData.size()){
						GeneralDataBean bean = generalBFactorData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData11(null);
						}else{
							curveTmpBean.setData11(bean.getData());
						}
					}
					if(null != generalCFactorData && 0 < generalCFactorData.size()){
						GeneralDataBean bean = generalCFactorData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData12(null);
						}else{
							curveTmpBean.setData12(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastFactorData && 0 < contrastFactorData.size()){
							GeneralDataBean bean = contrastFactorData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData13(null);
							}else{
								curveTmpBean.setData13(bean.getData());
							}
						}
						if(null != contrastAFactorData && 0 < contrastAFactorData.size()){
							GeneralDataBean bean = contrastAFactorData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData14(null);
							}else{
								curveTmpBean.setData14(bean.getData());
							}
						}
						if(null != contrastBFactorData && 0 < contrastBFactorData.size()){
							GeneralDataBean bean = contrastBFactorData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData15(null);
							}else{
								curveTmpBean.setData15(bean.getData());
							}
						}
						if(null != contrastCFactorData && 0 < contrastCFactorData.size()){
							GeneralDataBean bean = contrastCFactorData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData16(null);
							}else{
								curveTmpBean.setData16(bean.getData());
							}
						}
					}
				}
		    }
		}else if("5".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(statCheckValue){//是否显示冻结数据
					if(null != generalEnergyPapData && 0 < generalEnergyPapData.size()){
						GeneralDataBean bean = generalEnergyPapData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData1(null);
						}else{
							curveTmpBean.setData1(bean.getData());
						}
					}
					if(null != generalEnergyPrpData && 0 < generalEnergyPrpData.size()){
						GeneralDataBean bean = generalEnergyPrpData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData2(null);
						}else{
							curveTmpBean.setData2(bean.getData());
						}
					}
					if(null != generalEnergyRapData && 0 < generalEnergyRapData.size()){
						GeneralDataBean bean = generalEnergyRapData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData3(null);
						}else{
							curveTmpBean.setData3(bean.getData());
						}
					}
					if(null != generalEnergyRrpData && 0 < generalEnergyRrpData.size()){
						GeneralDataBean bean = generalEnergyRrpData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData4(null);
						}else{
							curveTmpBean.setData4(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastEnergyPapData && 0 < contrastEnergyPapData.size()){
							GeneralDataBean bean = contrastEnergyPapData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData5(null);
							}else{
								curveTmpBean.setData5(bean.getData());
							}
						}
						if(null != contrastEnergyPrpData && 0 < contrastEnergyPrpData.size()){
							GeneralDataBean bean = contrastEnergyPrpData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData6(null);
							}else{
								curveTmpBean.setData6(bean.getData());
							}
						}
						if(null != contrastEnergyRapData && 0 < contrastEnergyRapData.size()){
							GeneralDataBean bean = contrastEnergyRapData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData7(null);
							}else{
								curveTmpBean.setData7(bean.getData());
							}
						}
						if(null != contrastEnergyRrpData && 0 < contrastEnergyRrpData.size()){
							GeneralDataBean bean = contrastEnergyRrpData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData8(null);
							}else{
								curveTmpBean.setData8(bean.getData());
							}
						}
					}
				}
		    }
		}else if("6".equals(curveType)){
			for(int j = 0; j < generalCurveDataList.size(); j++){//循环配置的时间点数，默认96次
				GeneralCurveDataBean curveTmpBean = (GeneralCurveDataBean)generalCurveDataList.get(j);
				if(statCheckValue){//是否显示冻结数据
					if(null != generalReadPapData && 0 < generalReadPapData.size()){
						GeneralDataBean bean = generalReadPapData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData1(null);
						}else{
							curveTmpBean.setData1(bean.getData());
						}
					}
					if(null != generalReadPrpData && 0 < generalReadPrpData.size()){
						GeneralDataBean bean = generalReadPrpData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData2(null);
						}else{
							curveTmpBean.setData2(bean.getData());
						}
					}
					if(null != generalReadRapData && 0 < generalReadRapData.size()){
						GeneralDataBean bean = generalReadRapData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData3(null);
						}else{
							curveTmpBean.setData3(bean.getData());
						}
					}
					if(null != generalReadRrpData && 0 < generalReadRrpData.size()){
						GeneralDataBean bean = generalReadRrpData.get(j);
						if(bean.getFlag()){
							curveTmpBean.setData4(null);
						}else{
							curveTmpBean.setData4(bean.getData());
						}
					}
	                if(conCheckValue){//是否显示对比日冻结数据
	                	if(null != contrastReadPapData && 0 < contrastReadPapData.size()){
							GeneralDataBean bean = contrastReadPapData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData5(null);
							}else{
								curveTmpBean.setData5(bean.getData());
							}
						}
						if(null != contrastReadPrpData && 0 < contrastReadPrpData.size()){
							GeneralDataBean bean = contrastReadPrpData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData6(null);
							}else{
								curveTmpBean.setData6(bean.getData());
							}
						}
						if(null != contrastReadRapData && 0 < contrastReadRapData.size()){
							GeneralDataBean bean = contrastReadRapData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData7(null);
							}else{
								curveTmpBean.setData7(bean.getData());
							}
						}
						if(null != contrastReadRrpData && 0 < contrastReadRrpData.size()){
							GeneralDataBean bean = contrastReadRrpData.get(j);
							if(bean.getFlag()){
								curveTmpBean.setData8(null);
							}else{
								curveTmpBean.setData8(bean.getData());
							}
						}
					}
				}
		    }
		}else{
			return;
		}
	}
	/**
	 * 判断实时功率列表是否全漏点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isPowerNullValue(List<GeneralRealPowerDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)list.get(i);
				if(bean.getBeanFlag()){//如果true，该bean属于漏点
					continue;
				}else{//如果bean不漏点，但是里面数据全漏点，依旧属于漏点
					if(bean.getFlag() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取实时功率最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkPowerMaxMinValue(List<GeneralRealPowerDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)list.get(i);
			if(bean.getBeanFlag()){
				continue;
			}
			if(j == 0){
				if(bean.getFlag() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
					continue;
				}else{
					if(!(bean.getFlag())){
						tempMax = bean.getPower();
						tempMin = bean.getPower();
					}else if(!(bean.getFlagA())){
						tempMax = bean.getPowerA();
						tempMin = bean.getPowerA();
					}else if(!(bean.getFlagB())){
						tempMax = bean.getPowerB();
						tempMin = bean.getPowerB();
					}else if(!(bean.getFlagC())){
						tempMax = bean.getPowerC();
						tempMin = bean.getPowerC();
					}else{
						continue;
					}
					j++;
				}
			}
			if(null != bean.getPower() && !(bean.getFlag())){
				if(ArithmeticUtil.sub(bean.getPower(),tempMax) > 0){
					tempMax = bean.getPower();
				}
				if(ArithmeticUtil.sub(bean.getPower(),tempMin) < 0){
					tempMin = bean.getPower();
				}
			}
			if(null != bean.getPowerA() && !(bean.getFlagA())){
				if(ArithmeticUtil.sub(bean.getPowerA(),tempMax) > 0){
					tempMax = bean.getPowerA();
				}
				if(ArithmeticUtil.sub(bean.getPowerA(),tempMin) < 0){
					tempMin = bean.getPowerA();
				}
			}
			if(null != bean.getPowerB() && !(bean.getFlagB())){
				if(ArithmeticUtil.sub(bean.getPowerB(),tempMax) > 0){
					tempMax = bean.getPowerB();
				}
				if(ArithmeticUtil.sub(bean.getPowerB(),tempMin) < 0){
					tempMin = bean.getPowerB();
				}
			}
			if(null != bean.getPowerC() && !(bean.getFlagC())){
				if(ArithmeticUtil.sub(bean.getPowerC(),tempMax) > 0){
					tempMax = bean.getPowerC();
				}
				if(ArithmeticUtil.sub(bean.getPowerC(),tempMin) < 0){
					tempMin = bean.getPowerC();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 判断实时电压是否全漏点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isVoltNullValue(List<GeneralRealVoltDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)list.get(i);
				if(bean.getBeanFlag()){
					continue;
				}else{
					if(bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	
	/**
	 * 获取实时电压最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkVoltMaxMinValue(List<GeneralRealVoltDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)list.get(i);
			if(bean.getBeanFlag()){
				continue;
			}
			if(j == 0){
				if(bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
					continue;
				}else{
					if(!(bean.getFlagA())){
						tempMax = bean.getVoltA();
						tempMin = bean.getVoltA();
					}else if(!(bean.getFlagB())){
						tempMax = bean.getVoltB();
						tempMin = bean.getVoltB();
					}else if(!(bean.getFlagC())){
						tempMax = bean.getVoltC();
						tempMin = bean.getVoltC();
					}else{
						continue;
					}
					j++;
				}
			}
			if(null != bean.getVoltA() && !(bean.getFlagA())){
				if(ArithmeticUtil.sub(bean.getVoltA(),tempMax) > 0){
					tempMax = bean.getVoltA();
				}
				if(ArithmeticUtil.sub(bean.getVoltA(),tempMin) < 0){
					tempMin = bean.getVoltA();
				}
			}
			if(null != bean.getVoltB() && !(bean.getFlagB())){
				if(ArithmeticUtil.sub(bean.getVoltB(),tempMax) > 0){
					tempMax = bean.getVoltB();
				}
				if(ArithmeticUtil.sub(bean.getVoltB(),tempMin) < 0){
					tempMin = bean.getVoltB();
				}
			}
			if(null != bean.getVoltC() && !(bean.getFlagC())){
				if(ArithmeticUtil.sub(bean.getVoltC(),tempMax) > 0){
					tempMax = bean.getVoltC();
				}
				if(ArithmeticUtil.sub(bean.getVoltC(),tempMin) < 0){
					tempMin = bean.getVoltC();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 判断实时电流列表是否全漏点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isCurNullValue(List<GeneralRealCurDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralRealCurDataBean bean = (GeneralRealCurDataBean)list.get(i);
				if(bean.getBeanFlag()){//如果true，该bean属于漏点
					continue;
				}else{//如果bean不漏点，但是里面数据全漏点，依旧属于漏点
					if(bean.getFlag0() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取实时电流最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkCurMaxMinValue(List<GeneralRealCurDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralRealCurDataBean bean = (GeneralRealCurDataBean)list.get(i);
			if(bean.getBeanFlag()){
				continue;
			}
			if(j == 0){
				if(bean.getFlag0() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
					continue;
				}else{
					if(!(bean.getFlag0())){
						tempMax = bean.getCur0();
						tempMin = bean.getCur0();
					}else if(!(bean.getFlagA())){
						tempMax = bean.getCurA();
						tempMin = bean.getCurA();
					}else if(!(bean.getFlagB())){
						tempMax = bean.getCurB();
						tempMin = bean.getCurB();
					}else if(!(bean.getFlagC())){
						tempMax = bean.getCurC();
						tempMin = bean.getCurC();
					}else{
						continue;
					}
					j++;
				}
			}
			if(null != bean.getCur0() && !(bean.getFlag0())){
				if(ArithmeticUtil.sub(bean.getCur0(),tempMax) > 0){
					tempMax = bean.getCur0();
				}
				if(ArithmeticUtil.sub(bean.getCur0(),tempMin) < 0){
					tempMin = bean.getCur0();
				}
			}
			if(null != bean.getCurA() && !(bean.getFlagA())){
				if(ArithmeticUtil.sub(bean.getCurA(),tempMax) > 0){
					tempMax = bean.getCurA();
				}
				if(ArithmeticUtil.sub(bean.getCurA(),tempMin) < 0){
					tempMin = bean.getCurA();
				}
			}
			if(null != bean.getCurB() && !(bean.getFlagB())){
				if(ArithmeticUtil.sub(bean.getCurB(),tempMax) > 0){
					tempMax = bean.getCurB();
				}
				if(ArithmeticUtil.sub(bean.getCurB(),tempMin) < 0){
					tempMin = bean.getCurB();
				}
			}
			if(null != bean.getCurC() && !(bean.getFlagC())){
				if(ArithmeticUtil.sub(bean.getCurC(),tempMax) > 0){
					tempMax = bean.getCurC();
				}
				if(ArithmeticUtil.sub(bean.getCurC(),tempMin) < 0){
					tempMin = bean.getCurC();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	
	/**
	 * 判断实时功率因数列表是否全漏点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isFactorNullValue(List<GeneralRealFactorDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)list.get(i);
				if(bean.getBeanFlag()){//如果true，该bean属于漏点
					continue;
				}else{//如果bean不漏点，但是里面数据全漏点，依旧属于漏点
					if(bean.getFlag() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取实时功率因数最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkFactorMaxMinValue(List<GeneralRealFactorDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)list.get(i);
			if(bean.getBeanFlag()){
				continue;
			}
			if(j == 0){
				if(bean.getFlag() && bean.getFlagA() && bean.getFlagB() && bean.getFlagC()){
					continue;
				}else{
					if(!(bean.getFlag())){
						tempMax = bean.getFactor();
						tempMin = bean.getFactor();
					}else if(!(bean.getFlagA())){
						tempMax = bean.getFactorA();
						tempMin = bean.getFactorA();
					}else if(!(bean.getFlagB())){
						tempMax = bean.getFactorB();
						tempMin = bean.getFactorB();
					}else if(!(bean.getFlagC())){
						tempMax = bean.getFactorC();
						tempMin = bean.getFactorC();
					}else{
						continue;
					}
					j++;
				}
			}
			if(null != bean.getFactor() && !(bean.getFlag())){
				if(ArithmeticUtil.sub(bean.getFactor(),tempMax) > 0){
					tempMax = bean.getFactor();
				}
				if(ArithmeticUtil.sub(bean.getFactor(),tempMin) < 0){
					tempMin = bean.getFactor();
				}
			}
			if(null != bean.getFactorA() && !(bean.getFlagA())){
				if(ArithmeticUtil.sub(bean.getFactorA(),tempMax) > 0){
					tempMax = bean.getFactorA();
				}
				if(ArithmeticUtil.sub(bean.getFactorA(),tempMin) < 0){
					tempMin = bean.getFactorA();
				}
			}
			if(null != bean.getFactorB() && !(bean.getFlagB())){
				if(ArithmeticUtil.sub(bean.getFactorB(),tempMax) > 0){
					tempMax = bean.getFactorB();
				}
				if(ArithmeticUtil.sub(bean.getFactorB(),tempMin) < 0){
					tempMin = bean.getFactorB();
				}
			}
			if(null != bean.getFactorC() && !(bean.getFlagC())){
				if(ArithmeticUtil.sub(bean.getFactorC(),tempMax) > 0){
					tempMax = bean.getFactorC();
				}
				if(ArithmeticUtil.sub(bean.getFactorC(),tempMin) < 0){
					tempMin = bean.getFactorC();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 判断冻结数据列表是否全漏点,true表示露点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isStatNullValue(List<GeneralDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralDataBean bean = (GeneralDataBean)list.get(i);
				if(bean.getFlag()){
					continue;
				}else{
				    flag = false;
				    break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取冻结数据最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkStatMaxMinValue(List<GeneralDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralDataBean bean = (GeneralDataBean)list.get(i);
		    if(bean.getFlag()){
		    	continue;
		    }
		    if(j == 0){
		    	tempMax = bean.getData();
		    	tempMin = bean.getData();
		    	j++;
		    }
			if(null != bean.getData()){
				if(ArithmeticUtil.sub(bean.getData(),tempMax) > 0){
					tempMax = bean.getData();
				}
				if(ArithmeticUtil.sub(bean.getData(),tempMin) < 0){
					tempMin = bean.getData();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 查询某用户下的终端信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryConsTmnlInfo() throws Exception {
		try {
			consTmnlList = generalAnalyseManager.queryConsTmnlInfo(consNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return SUCCESS;
	}

	// -----------------------getter and setter---------------------------
	public boolean isSuccess() {
		return success;
	}

	public Long getTotalId() {
		return totalId;
	}

	public void setTotalId(Long totalId) {
		this.totalId = totalId;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCurveType() {
		return curveType;
	}

	public void setCurveType(String curveType) {
		this.curveType = curveType;
	}

	public ETmnlDayStatBean getTmnlDayStat() {
		return tmnlDayStat;
	}

	public void setTmnlDayStat(ETmnlDayStatBean tmnlDayStat) {
		this.tmnlDayStat = tmnlDayStat;
	}

	public ETmnlMonStatBean getTmnlMonStat() {
		return tmnlMonStat;
	}

	public void setTmnlMonStat(ETmnlMonStatBean tmnlMonStat) {
		this.tmnlMonStat = tmnlMonStat;
	}

	public ETotalDayStatBean getTotalDayStat() {
		return totalDayStat;
	}

	public void setTotalDayStat(ETotalDayStatBean totalDayStat) {
		this.totalDayStat = totalDayStat;
	}

	public ETotalMonStatBean getTotalMonStat() {
		return totalMonStat;
	}

	public void setTotalMonStat(ETotalMonStatBean totalMonStat) {
		this.totalMonStat = totalMonStat;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Date getContrastDate() {
		return contrastDate;
	}

	public Date getDataDateFrom() {
		return dataDateFrom;
	}

	public void setDataDateFrom(Date dataDateFrom) {
		this.dataDateFrom = dataDateFrom;
	}

	public Date getDataDateTo() {
		return dataDateTo;
	}

	public void setDataDateTo(Date dataDateTo) {
		this.dataDateTo = dataDateTo;
	}

	public void setContrastDate(Date contrastDate) {
		this.contrastDate = contrastDate;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<EDataMpBean> getDataMpList() {
		return dataMpList;
	}

	public void setDataMpList(List<EDataMpBean> dataMpList) {
		this.dataMpList = dataMpList;
	}

	public List<EDataTotalBean> getDataTotalList() {
		return dataTotalList;
	}

	public void setDataTotalList(List<EDataTotalBean> dataTotalList) {
		this.dataTotalList = dataTotalList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setGeneralAnalyseManager(
			GeneralAnalyseManager generalAnalyseManager) {
		this.generalAnalyseManager = generalAnalyseManager;
	}

	@JSON(serialize = false)
	public GeneralAnalyseManager getGeneralAnalyseManager() {
		return generalAnalyseManager;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public List<ETmnlDayStatBean> getTmnlDayStatList() {
		return tmnlDayStatList;
	}

	public void setTmnlDayStatList(List<ETmnlDayStatBean> tmnlDayStatList) {
		this.tmnlDayStatList = tmnlDayStatList;
	}

	public List<ETmnlMonStatBean> getTmnlMonStatList() {
		return tmnlMonStatList;
	}

	public void setTmnlMonStatList(List<ETmnlMonStatBean> tmnlMonStatList) {
		this.tmnlMonStatList = tmnlMonStatList;
	}

	public List<ETotalDayStatBean> getTotalDayStatList() {
		return totalDayStatList;
	}

	public void setTotalDayStatList(List<ETotalDayStatBean> totalDayStatList) {
		this.totalDayStatList = totalDayStatList;
	}

	public List<ETotalMonStatBean> getTotalMonStatList() {
		return totalMonStatList;
	}

	public void setTotalMonStatList(List<ETotalMonStatBean> totalMonStatList) {
		this.totalMonStatList = totalMonStatList;
	}

	public List<EMpRdayReadBean> getMpRdayReadList() {
		return mpRdayReadList;
	}

	public void setMpRdayReadList(List<EMpRdayReadBean> mpRdayReadList) {
		this.mpRdayReadList = mpRdayReadList;
	}

	public List<EMpDayPowerBean> getMpDayPowerList() {
		return mpDayPowerList;
	}

	public void setMpDayPowerList(List<EMpDayPowerBean> mpDayPowerList) {
		this.mpDayPowerList = mpDayPowerList;
	}

	public List<EMpDayVstatBean> getMpDayVstatList() {
		return mpDayVstatList;
	}

	public void setMpDayVstatList(List<EMpDayVstatBean> mpDayVstatList) {
		this.mpDayVstatList = mpDayVstatList;
	}

	public List<EMpDayIstatBean> getMpDayIstatList() {
		return mpDayIstatList;
	}

	public void setMpDayIstatList(List<EMpDayIstatBean> mpDayIstatList) {
		this.mpDayIstatList = mpDayIstatList;
	}

	public List<EMpDayReadBean> getMpDayReadList() {
		return mpDayReadList;
	}

	public void setMpDayReadList(List<EMpDayReadBean> mpDayReadList) {
		this.mpDayReadList = mpDayReadList;
	}

	public List<EMpDayEnergyBean> getMpDayEnergyList() {
		return mpDayEnergyList;
	}

	public void setMpDayEnergyList(List<EMpDayEnergyBean> mpDayEnergyList) {
		this.mpDayEnergyList = mpDayEnergyList;
	}

	public List<EMpDayCompBean> getMpDayCompList() {
		return mpDayCompList;
	}

	public void setMpDayCompList(List<EMpDayCompBean> mpDayCompList) {
		this.mpDayCompList = mpDayCompList;
	}

	public List<EMpMonVstatBean> getMpMonVstatList() {
		return mpMonVstatList;
	}

	public void setMpMonVstatList(List<EMpMonVstatBean> mpMonVstatList) {
		this.mpMonVstatList = mpMonVstatList;
	}

	public List<EMpMonReadBean> getMpMonReadList() {
		return mpMonReadList;
	}

	public void setMpMonReadList(List<EMpMonReadBean> mpMonReadList) {
		this.mpMonReadList = mpMonReadList;
	}

	public List<EMpMonEnergyBean> getMpMonEnergyList() {
		return mpMonEnergyList;
	}

	public void setMpMonEnergyList(List<EMpMonEnergyBean> mpMonEnergyList) {
		this.mpMonEnergyList = mpMonEnergyList;
	}

	public List<EMpRdayDemandBean> getMpRdayDemandList() {
		return mpRdayDemandList;
	}

	public void setMpRdayDemandList(List<EMpRdayDemandBean> mpRdayDemandList) {
		this.mpRdayDemandList = mpRdayDemandList;
	}

	public List<EMpDayDemandBean> getMpDayDemandList() {
		return mpDayDemandList;
	}

	public void setMpDayDemandList(List<EMpDayDemandBean> mpDayDemandList) {
		this.mpDayDemandList = mpDayDemandList;
	}

	public List<EMpDayPftimeBean> getMpDayPftimeList() {
		return mpDayPftimeList;
	}

	public void setMpDayPftimeList(List<EMpDayPftimeBean> mpDayPftimeList) {
		this.mpDayPftimeList = mpDayPftimeList;
	}

	public List<EMpDayUnbalanceBean> getMpDayUnbalanceList() {
		return mpDayUnbalanceList;
	}

	public void setMpDayUnbalanceList(
			List<EMpDayUnbalanceBean> mpDayUnbalanceList) {
		this.mpDayUnbalanceList = mpDayUnbalanceList;
	}

	public List<EMpMonUnbalanceBean> getMpMonUnbalanceList() {
		return mpMonUnbalanceList;
	}

	public void setMpMonUnbalanceList(
			List<EMpMonUnbalanceBean> mpMonUnbalanceList) {
		this.mpMonUnbalanceList = mpMonUnbalanceList;
	}

	public List<EMpMonPftimeBean> getMpMonPftimeList() {
		return mpMonPftimeList;
	}

	public void setMpMonPftimeList(List<EMpMonPftimeBean> mpMonPftimeList) {
		this.mpMonPftimeList = mpMonPftimeList;
	}

	public List<EMpMonDemandBean> getMpMonDemandList() {
		return mpMonDemandList;
	}

	public void setMpMonDemandList(List<EMpMonDemandBean> mpMonDemandList) {
		this.mpMonDemandList = mpMonDemandList;
	}

	public List<EMpMonPowerBean> getMpMonPowerList() {
		return mpMonPowerList;
	}

	public void setMpMonPowerList(List<EMpMonPowerBean> mpMonPowerList) {
		this.mpMonPowerList = mpMonPowerList;
	}

	public List<EMpMonIstatBean> getMpMonIstatList() {
		return mpMonIstatList;
	}

	public void setMpMonIstatList(List<EMpMonIstatBean> mpMonIstatList) {
		this.mpMonIstatList = mpMonIstatList;
	}

	public String getConsName() {
		return consName;
	}

	public void setConsName(String consName) {
		this.consName = consName;
	}

	public List<SameTradeCCons> getSameTradeCConsList() {
		return sameTradeCConsList;
	}

	public void setSameTradeCConsList(List<SameTradeCCons> sameTradeCConsList) {
		this.sameTradeCConsList = sameTradeCConsList;
	}

	public List<GroupNameBean> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupNameBean> groupList) {
		this.groupList = groupList;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public List<GeneralRealPowerDataBean> getMpDayRealPowerList() {
		return mpDayRealPowerList;
	}

	public void setMpDayRealPowerList(
			List<GeneralRealPowerDataBean> mpDayRealPowerList) {
		this.mpDayRealPowerList = mpDayRealPowerList;
	}

	public List<GeneralRealCurDataBean> getMpDayRealCurList() {
		return mpDayRealCurList;
	}

	public void setMpDayRealCurList(
			List<GeneralRealCurDataBean> mpDayRealCurList) {
		this.mpDayRealCurList = mpDayRealCurList;
	}

	public List<GeneralRealVoltDataBean> getMpDayRealVoltList() {
		return mpDayRealVoltList;
	}

	public void setMpDayRealVoltList(
			List<GeneralRealVoltDataBean> mpDayRealVoltList) {
		this.mpDayRealVoltList = mpDayRealVoltList;
	}

	public List<GeneralRealFactorDataBean> getMpDayRealFactorList() {
		return mpDayRealFactorList;
	}

	public void setMpDayRealFactorList(
			List<GeneralRealFactorDataBean> mpDayRealFactorList) {
		this.mpDayRealFactorList = mpDayRealFactorList;
	}

	public List<EMpReadBean> getMpRealDataReadList() {
		return mpRealDataReadList;
	}

	public void setMpRealDataReadList(List<EMpReadBean> mpRealDataReadList) {
		this.mpRealDataReadList = mpRealDataReadList;
	}

	public boolean isStatCheckValue() {
		return statCheckValue;
	}

	public void setStatCheckValue(boolean statCheckValue) {
		this.statCheckValue = statCheckValue;
	}

	public boolean isRealCheckValue() {
		return realCheckValue;
	}

	public void setRealCheckValue(boolean realCheckValue) {
		this.realCheckValue = realCheckValue;
	}

	public List<GeneralDataBean> getGeneralPowerData() {
		return generalPowerData;
	}

	public void setGeneralPowerData(List<GeneralDataBean> generalPowerData) {
		this.generalPowerData = generalPowerData;
	}

	public List<GeneralDataBean> getContrastPowerData() {
		return contrastPowerData;
	}

	public void setContrastPowerData(List<GeneralDataBean> contrastPowerData) {
		this.contrastPowerData = contrastPowerData;
	}

	public List<GeneralDataBean> getGeneralCurData() {
		return generalCurData;
	}

	public void setGeneralCurData(List<GeneralDataBean> generalCurData) {
		this.generalCurData = generalCurData;
	}

	public List<GeneralDataBean> getContrastCurData() {
		return contrastCurData;
	}

	public void setContrastCurData(List<GeneralDataBean> contrastCurData) {
		this.contrastCurData = contrastCurData;
	}
	
	public List<GeneralDataBean> getGeneralFactorData() {
		return generalFactorData;
	}

	public void setGeneralFactorData(List<GeneralDataBean> generalFactorData) {
		this.generalFactorData = generalFactorData;
	}

	public List<GeneralDataBean> getContrastFactorData() {
		return contrastFactorData;
	}

	public void setContrastFactorData(List<GeneralDataBean> contrastFactorData) {
		this.contrastFactorData = contrastFactorData;
	}

	public List<GeneralRealPowerDataBean> getMpDayRealPowerConList() {
		return mpDayRealPowerConList;
	}

	public void setMpDayRealPowerConList(
			List<GeneralRealPowerDataBean> mpDayRealPowerConList) {
		this.mpDayRealPowerConList = mpDayRealPowerConList;
	}

	public List<GeneralRealCurDataBean> getMpDayRealCurConList() {
		return mpDayRealCurConList;
	}

	public void setMpDayRealCurConList(
			List<GeneralRealCurDataBean> mpDayRealCurConList) {
		this.mpDayRealCurConList = mpDayRealCurConList;
	}

	public List<GeneralRealVoltDataBean> getMpDayRealVoltConList() {
		return mpDayRealVoltConList;
	}

	public void setMpDayRealVoltConList(
			List<GeneralRealVoltDataBean> mpDayRealVoltConList) {
		this.mpDayRealVoltConList = mpDayRealVoltConList;
	}

	public List<GeneralRealFactorDataBean> getMpDayRealFactorConList() {
		return mpDayRealFactorConList;
	}

	public void setMpDayRealFactorConList(
			List<GeneralRealFactorDataBean> mpDayRealFactorConList) {
		this.mpDayRealFactorConList = mpDayRealFactorConList;
	}

	public String getMpDayRealPowerName() {
		return mpDayRealPowerName;
	}

	public void setMpDayRealPowerName(String mpDayRealPowerName) {
		this.mpDayRealPowerName = mpDayRealPowerName;
	}

	public String getMpDayRealCurName() {
		return mpDayRealCurName;
	}

	public void setMpDayRealCurName(String mpDayRealCurName) {
		this.mpDayRealCurName = mpDayRealCurName;
	}

	public String getMpDayRealVoltName() {
		return mpDayRealVoltName;
	}

	public void setMpDayRealVoltName(String mpDayRealVoltName) {
		this.mpDayRealVoltName = mpDayRealVoltName;
	}

	public String getMpDayRealFactorName() {
		return mpDayRealFactorName;
	}

	public void setMpDayRealFactorName(String mpDayRealFactorName) {
		this.mpDayRealFactorName = mpDayRealFactorName;
	}

	public String getMpDayRealPowerConName() {
		return mpDayRealPowerConName;
	}

	public void setMpDayRealPowerConName(String mpDayRealPowerConName) {
		this.mpDayRealPowerConName = mpDayRealPowerConName;
	}

	public String getMpDayRealCurConName() {
		return mpDayRealCurConName;
	}

	public void setMpDayRealCurConName(String mpDayRealCurConName) {
		this.mpDayRealCurConName = mpDayRealCurConName;
	}

	public String getMpDayRealVoltConName() {
		return mpDayRealVoltConName;
	}

	public void setMpDayRealVoltConName(String mpDayRealVoltConName) {
		this.mpDayRealVoltConName = mpDayRealVoltConName;
	}

	public String getMpDayRealFactorConName() {
		return mpDayRealFactorConName;
	}

	public void setMpDayRealFactorConName(String mpDayRealFactorConName) {
		this.mpDayRealFactorConName = mpDayRealFactorConName;
	}

	public String getGeneralCurName() {
		return generalCurName;
	}

	public void setGeneralCurName(String generalCurName) {
		this.generalCurName = generalCurName;
	}

	public String getContrastCurName() {
		return contrastCurName;
	}

	public void setContrastCurName(String contrastCurName) {
		this.contrastCurName = contrastCurName;
	}

	public String getGeneralFactorName() {
		return generalFactorName;
	}

	public void setGeneralFactorName(String generalFactorName) {
		this.generalFactorName = generalFactorName;
	}

	public String getContrastFactorName() {
		return contrastFactorName;
	}

	public void setContrastFactorName(String contrastFactorName) {
		this.contrastFactorName = contrastFactorName;
	}

	public List<GeneralDataBean> getGeneralPowerModelList() {
		return generalPowerModelList;
	}

	public void setGeneralPowerModelList(List<GeneralDataBean> generalPowerModelList) {
		this.generalPowerModelList = generalPowerModelList;
	}

	public List<GeneralDataBean> getGeneralAPowerData() {
		return generalAPowerData;
	}

	public void setGeneralAPowerData(List<GeneralDataBean> generalAPowerData) {
		this.generalAPowerData = generalAPowerData;
	}

	public List<GeneralDataBean> getContrastAPowerData() {
		return contrastAPowerData;
	}

	public void setContrastAPowerData(List<GeneralDataBean> contrastAPowerData) {
		this.contrastAPowerData = contrastAPowerData;
	}

	public List<GeneralDataBean> getGeneralBPowerData() {
		return generalBPowerData;
	}

	public void setGeneralBPowerData(List<GeneralDataBean> generalBPowerData) {
		this.generalBPowerData = generalBPowerData;
	}

	public List<GeneralDataBean> getContrastBPowerData() {
		return contrastBPowerData;
	}

	public void setContrastBPowerData(List<GeneralDataBean> contrastBPowerData) {
		this.contrastBPowerData = contrastBPowerData;
	}

	public List<GeneralDataBean> getGeneralCPowerData() {
		return generalCPowerData;
	}

	public void setGeneralCPowerData(List<GeneralDataBean> generalCPowerData) {
		this.generalCPowerData = generalCPowerData;
	}

	public List<GeneralDataBean> getContrastCPowerData() {
		return contrastCPowerData;
	}

	public void setContrastCPowerData(List<GeneralDataBean> contrastCPowerData) {
		this.contrastCPowerData = contrastCPowerData;
	}

	public List<GeneralDataBean> getGeneralACurData() {
		return generalACurData;
	}

	public void setGeneralACurData(List<GeneralDataBean> generalACurData) {
		this.generalACurData = generalACurData;
	}

	public List<GeneralDataBean> getContrastACurData() {
		return contrastACurData;
	}

	public void setContrastACurData(List<GeneralDataBean> contrastACurData) {
		this.contrastACurData = contrastACurData;
	}

	public List<GeneralDataBean> getGeneralBCurData() {
		return generalBCurData;
	}

	public void setGeneralBCurData(List<GeneralDataBean> generalBCurData) {
		this.generalBCurData = generalBCurData;
	}

	public List<GeneralDataBean> getContrastBCurData() {
		return contrastBCurData;
	}

	public void setContrastBCurData(List<GeneralDataBean> contrastBCurData) {
		this.contrastBCurData = contrastBCurData;
	}

	public List<GeneralDataBean> getGeneralCCurData() {
		return generalCCurData;
	}

	public void setGeneralCCurData(List<GeneralDataBean> generalCCurData) {
		this.generalCCurData = generalCCurData;
	}

	public List<GeneralDataBean> getContrastCCurData() {
		return contrastCCurData;
	}

	public void setContrastCCurData(List<GeneralDataBean> contrastCCurData) {
		this.contrastCCurData = contrastCCurData;
	}

	public List<GeneralDataBean> getGeneralAVoltData() {
		return generalAVoltData;
	}

	public void setGeneralAVoltData(List<GeneralDataBean> generalAVoltData) {
		this.generalAVoltData = generalAVoltData;
	}

	public List<GeneralDataBean> getContrastAVoltData() {
		return contrastAVoltData;
	}

	public void setContrastAVoltData(List<GeneralDataBean> contrastAVoltData) {
		this.contrastAVoltData = contrastAVoltData;
	}

	public List<GeneralDataBean> getGeneralBVoltData() {
		return generalBVoltData;
	}

	public void setGeneralBVoltData(List<GeneralDataBean> generalBVoltData) {
		this.generalBVoltData = generalBVoltData;
	}

	public List<GeneralDataBean> getContrastBVoltData() {
		return contrastBVoltData;
	}

	public void setContrastBVoltData(List<GeneralDataBean> contrastBVoltData) {
		this.contrastBVoltData = contrastBVoltData;
	}

	public List<GeneralDataBean> getGeneralCVoltData() {
		return generalCVoltData;
	}

	public void setGeneralCVoltData(List<GeneralDataBean> generalCVoltData) {
		this.generalCVoltData = generalCVoltData;
	}

	public List<GeneralDataBean> getContrastCVoltData() {
		return contrastCVoltData;
	}

	public void setContrastCVoltData(List<GeneralDataBean> contrastCVoltData) {
		this.contrastCVoltData = contrastCVoltData;
	}

	public List<GeneralDataBean> getGeneralAFactorData() {
		return generalAFactorData;
	}

	public void setGeneralAFactorData(List<GeneralDataBean> generalAFactorData) {
		this.generalAFactorData = generalAFactorData;
	}

	public List<GeneralDataBean> getContrastAFactorData() {
		return contrastAFactorData;
	}

	public void setContrastAFactorData(List<GeneralDataBean> contrastAFactorData) {
		this.contrastAFactorData = contrastAFactorData;
	}

	public List<GeneralDataBean> getGeneralBFactorData() {
		return generalBFactorData;
	}

	public void setGeneralBFactorData(List<GeneralDataBean> generalBFactorData) {
		this.generalBFactorData = generalBFactorData;
	}

	public List<GeneralDataBean> getContrastBFactorData() {
		return contrastBFactorData;
	}

	public void setContrastBFactorData(List<GeneralDataBean> contrastBFactorData) {
		this.contrastBFactorData = contrastBFactorData;
	}

	public List<GeneralDataBean> getGeneralCFactorData() {
		return generalCFactorData;
	}

	public void setGeneralCFactorData(List<GeneralDataBean> generalCFactorData) {
		this.generalCFactorData = generalCFactorData;
	}

	public List<GeneralDataBean> getContrastCFactorData() {
		return contrastCFactorData;
	}

	public void setContrastCFactorData(List<GeneralDataBean> contrastCFactorData) {
		this.contrastCFactorData = contrastCFactorData;
	}

	public String getGeneralPowerName() {
		return generalPowerName;
	}

	public void setGeneralPowerName(String generalPowerName) {
		this.generalPowerName = generalPowerName;
	}

	public String getContrastPowerName() {
		return contrastPowerName;
	}

	public void setContrastPowerName(String contrastPowerName) {
		this.contrastPowerName = contrastPowerName;
	}

	public String getGeneralAPowerName() {
		return generalAPowerName;
	}

	public void setGeneralAPowerName(String generalAPowerName) {
		this.generalAPowerName = generalAPowerName;
	}

	public String getContrastAPowerName() {
		return contrastAPowerName;
	}

	public void setContrastAPowerName(String contrastAPowerName) {
		this.contrastAPowerName = contrastAPowerName;
	}

	public String getGeneralBPowerName() {
		return generalBPowerName;
	}

	public void setGeneralBPowerName(String generalBPowerName) {
		this.generalBPowerName = generalBPowerName;
	}

	public String getContrastBPowerName() {
		return contrastBPowerName;
	}

	public void setContrastBPowerName(String contrastBPowerName) {
		this.contrastBPowerName = contrastBPowerName;
	}

	public String getGeneralCPowerName() {
		return generalCPowerName;
	}

	public void setGeneralCPowerName(String generalCPowerName) {
		this.generalCPowerName = generalCPowerName;
	}

	public String getContrastCPowerName() {
		return contrastCPowerName;
	}

	public void setContrastCPowerName(String contrastCPowerName) {
		this.contrastCPowerName = contrastCPowerName;
	}

	public String getGeneralAVoltName() {
		return generalAVoltName;
	}

	public void setGeneralAVoltName(String generalAVoltName) {
		this.generalAVoltName = generalAVoltName;
	}

	public String getContrastAVoltName() {
		return contrastAVoltName;
	}

	public void setContrastAVoltName(String contrastAVoltName) {
		this.contrastAVoltName = contrastAVoltName;
	}

	public String getGeneralBVoltName() {
		return generalBVoltName;
	}

	public void setGeneralBVoltName(String generalBVoltName) {
		this.generalBVoltName = generalBVoltName;
	}

	public String getContrastBVoltName() {
		return contrastBVoltName;
	}

	public void setContrastBVoltName(String contrastBVoltName) {
		this.contrastBVoltName = contrastBVoltName;
	}

	public String getGeneralCVoltName() {
		return generalCVoltName;
	}

	public void setGeneralCVoltName(String generalCVoltName) {
		this.generalCVoltName = generalCVoltName;
	}

	public String getContrastCVoltName() {
		return contrastCVoltName;
	}

	public void setContrastCVoltName(String contrastCVoltName) {
		this.contrastCVoltName = contrastCVoltName;
	}

	public String getGeneralACurName() {
		return generalACurName;
	}

	public void setGeneralACurName(String generalACurName) {
		this.generalACurName = generalACurName;
	}

	public String getContrastACurName() {
		return contrastACurName;
	}

	public void setContrastACurName(String contrastACurName) {
		this.contrastACurName = contrastACurName;
	}

	public String getGeneralBCurName() {
		return generalBCurName;
	}

	public void setGeneralBCurName(String generalBCurName) {
		this.generalBCurName = generalBCurName;
	}

	public String getContrastBCurName() {
		return contrastBCurName;
	}

	public void setContrastBCurName(String contrastBCurName) {
		this.contrastBCurName = contrastBCurName;
	}

	public String getGeneralCCurName() {
		return generalCCurName;
	}

	public void setGeneralCCurName(String generalCCurName) {
		this.generalCCurName = generalCCurName;
	}

	public String getContrastCCurName() {
		return contrastCCurName;
	}

	public void setContrastCCurName(String contrastCCurName) {
		this.contrastCCurName = contrastCCurName;
	}

	public String getGeneralAFactorName() {
		return generalAFactorName;
	}

	public void setGeneralAFactorName(String generalAFactorName) {
		this.generalAFactorName = generalAFactorName;
	}

	public String getContrastAFactorName() {
		return contrastAFactorName;
	}

	public void setContrastAFactorName(String contrastAFactorName) {
		this.contrastAFactorName = contrastAFactorName;
	}

	public String getGeneralBFactorName() {
		return generalBFactorName;
	}

	public void setGeneralBFactorName(String generalBFactorName) {
		this.generalBFactorName = generalBFactorName;
	}

	public String getContrastBFactorName() {
		return contrastBFactorName;
	}

	public void setContrastBFactorName(String contrastBFactorName) {
		this.contrastBFactorName = contrastBFactorName;
	}

	public String getGeneralCFactorName() {
		return generalCFactorName;
	}

	public void setGeneralCFactorName(String generalCFactorName) {
		this.generalCFactorName = generalCFactorName;
	}

	public String getContrastCFactorName() {
		return contrastCFactorName;
	}

	public void setContrastCFactorName(String contrastCFactorName) {
		this.contrastCFactorName = contrastCFactorName;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isConCheckValue() {
		return conCheckValue;
	}

	public void setConCheckValue(boolean conCheckValue) {
		this.conCheckValue = conCheckValue;
	}

	public List<Map<String ,String >> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Map<String ,String >> headers) {
		this.headers = headers;
	}

	public List<GeneralCurveDataBean> getGeneralCurveDataList() {
		return generalCurveDataList;
	}

	public void setGeneralCurveDataList(
			List<GeneralCurveDataBean> generalCurveDataList) {
		this.generalCurveDataList = generalCurveDataList;
	}

	public int getFreezeCycleNum() {
		return freezeCycleNum;
	}

	public void setFreezeCycleNum(int freezeCycleNum) {
		this.freezeCycleNum = freezeCycleNum;
	}

	public List<EMpCurveBean> getMpRealCurveDataList() {
		return mpRealCurveDataList;
	}

	public void setMpRealCurveDataList(List<EMpCurveBean> mpRealCurveDataList) {
		this.mpRealCurveDataList = mpRealCurveDataList;
	}

	public List<EMpConsTmnlBean> getConsTmnlList() {
		return consTmnlList;
	}

	public void setConsTmnlList(List<EMpConsTmnlBean> consTmnlList) {
		this.consTmnlList = consTmnlList;
	}

	public String getTmnlType() {
		return tmnlType;
	}

	public void setTmnlType(String tmnlType) {
		this.tmnlType = tmnlType;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public List<GeneralDataBean> getGeneralEnergyPapData() {
		return generalEnergyPapData;
	}

	public void setGeneralEnergyPapData(List<GeneralDataBean> generalEnergyPapData) {
		this.generalEnergyPapData = generalEnergyPapData;
	}

	public List<GeneralDataBean> getGeneralEnergyPrpData() {
		return generalEnergyPrpData;
	}

	public void setGeneralEnergyPrpData(List<GeneralDataBean> generalEnergyPrpData) {
		this.generalEnergyPrpData = generalEnergyPrpData;
	}

	public List<GeneralDataBean> getGeneralEnergyRapData() {
		return generalEnergyRapData;
	}

	public void setGeneralEnergyRapData(List<GeneralDataBean> generalEnergyRapData) {
		this.generalEnergyRapData = generalEnergyRapData;
	}

	public List<GeneralDataBean> getGeneralEnergyRrpData() {
		return generalEnergyRrpData;
	}

	public void setGeneralEnergyRrpData(List<GeneralDataBean> generalEnergyRrpData) {
		this.generalEnergyRrpData = generalEnergyRrpData;
	}

	public List<GeneralDataBean> getContrastEnergyPapData() {
		return contrastEnergyPapData;
	}

	public void setContrastEnergyPapData(List<GeneralDataBean> contrastEnergyPapData) {
		this.contrastEnergyPapData = contrastEnergyPapData;
	}

	public List<GeneralDataBean> getContrastEnergyPrpData() {
		return contrastEnergyPrpData;
	}

	public void setContrastEnergyPrpData(List<GeneralDataBean> contrastEnergyPrpData) {
		this.contrastEnergyPrpData = contrastEnergyPrpData;
	}

	public List<GeneralDataBean> getContrastEnergyRapData() {
		return contrastEnergyRapData;
	}

	public void setContrastEnergyRapData(List<GeneralDataBean> contrastEnergyRapData) {
		this.contrastEnergyRapData = contrastEnergyRapData;
	}

	public List<GeneralDataBean> getContrastEnergyRrpData() {
		return contrastEnergyRrpData;
	}

	public void setContrastEnergyRrpData(List<GeneralDataBean> contrastEnergyRrpData) {
		this.contrastEnergyRrpData = contrastEnergyRrpData;
	}

	public List<GeneralDataBean> getGeneralReadPapData() {
		return generalReadPapData;
	}

	public void setGeneralReadPapData(List<GeneralDataBean> generalReadPapData) {
		this.generalReadPapData = generalReadPapData;
	}

	public List<GeneralDataBean> getGeneralReadPrpData() {
		return generalReadPrpData;
	}

	public void setGeneralReadPrpData(List<GeneralDataBean> generalReadPrpData) {
		this.generalReadPrpData = generalReadPrpData;
	}

	public List<GeneralDataBean> getGeneralReadRapData() {
		return generalReadRapData;
	}

	public void setGeneralReadRapData(List<GeneralDataBean> generalReadRapData) {
		this.generalReadRapData = generalReadRapData;
	}

	public List<GeneralDataBean> getGeneralReadRrpData() {
		return generalReadRrpData;
	}

	public void setGeneralReadRrpData(List<GeneralDataBean> generalReadRrpData) {
		this.generalReadRrpData = generalReadRrpData;
	}

	public List<GeneralDataBean> getContrastReadPapData() {
		return contrastReadPapData;
	}

	public void setContrastReadPapData(List<GeneralDataBean> contrastReadPapData) {
		this.contrastReadPapData = contrastReadPapData;
	}

	public List<GeneralDataBean> getContrastReadPrpData() {
		return contrastReadPrpData;
	}

	public void setContrastReadPrpData(List<GeneralDataBean> contrastReadPrpData) {
		this.contrastReadPrpData = contrastReadPrpData;
	}

	public List<GeneralDataBean> getContrastReadRapData() {
		return contrastReadRapData;
	}

	public void setContrastReadRapData(List<GeneralDataBean> contrastReadRapData) {
		this.contrastReadRapData = contrastReadRapData;
	}

	public List<GeneralDataBean> getContrastReadRrpData() {
		return contrastReadRrpData;
	}

	public void setContrastReadRrpData(List<GeneralDataBean> contrastReadRrpData) {
		this.contrastReadRrpData = contrastReadRrpData;
	}

	public String getGeneralEnergyPapName() {
		return generalEnergyPapName;
	}

	public void setGeneralEnergyPapName(String generalEnergyPapName) {
		this.generalEnergyPapName = generalEnergyPapName;
	}

	public String getGeneralEnergyPrpName() {
		return generalEnergyPrpName;
	}

	public void setGeneralEnergyPrpName(String generalEnergyPrpName) {
		this.generalEnergyPrpName = generalEnergyPrpName;
	}

	public String getGeneralEnergyRapName() {
		return generalEnergyRapName;
	}

	public void setGeneralEnergyRapName(String generalEnergyRapName) {
		this.generalEnergyRapName = generalEnergyRapName;
	}

	public String getGeneralEnergyRrpName() {
		return generalEnergyRrpName;
	}

	public void setGeneralEnergyRrpName(String generalEnergyRrpName) {
		this.generalEnergyRrpName = generalEnergyRrpName;
	}

	public String getContrastEnergyPapName() {
		return contrastEnergyPapName;
	}

	public void setContrastEnergyPapName(String contrastEnergyPapName) {
		this.contrastEnergyPapName = contrastEnergyPapName;
	}

	public String getContrastEnergyPrpName() {
		return contrastEnergyPrpName;
	}

	public void setContrastEnergyPrpName(String contrastEnergyPrpName) {
		this.contrastEnergyPrpName = contrastEnergyPrpName;
	}

	public String getContrastEnergyRapName() {
		return contrastEnergyRapName;
	}

	public void setContrastEnergyRapName(String contrastEnergyRapName) {
		this.contrastEnergyRapName = contrastEnergyRapName;
	}

	public String getContrastEnergyRrpName() {
		return contrastEnergyRrpName;
	}

	public void setContrastEnergyRrpName(String contrastEnergyRrpName) {
		this.contrastEnergyRrpName = contrastEnergyRrpName;
	}

	public String getGeneralReadPapName() {
		return generalReadPapName;
	}

	public void setGeneralReadPapName(String generalReadPapName) {
		this.generalReadPapName = generalReadPapName;
	}

	public String getGeneralReadPrpName() {
		return generalReadPrpName;
	}

	public void setGeneralReadPrpName(String generalReadPrpName) {
		this.generalReadPrpName = generalReadPrpName;
	}

	public String getGeneralReadRapName() {
		return generalReadRapName;
	}

	public void setGeneralReadRapName(String generalReadRapName) {
		this.generalReadRapName = generalReadRapName;
	}

	public String getGeneralReadRrpName() {
		return generalReadRrpName;
	}

	public void setGeneralReadRrpName(String generalReadRrpName) {
		this.generalReadRrpName = generalReadRrpName;
	}

	public String getContrastReadPapName() {
		return contrastReadPapName;
	}

	public void setContrastReadPapName(String contrastReadPapName) {
		this.contrastReadPapName = contrastReadPapName;
	}

	public String getContrastReadPrpName() {
		return contrastReadPrpName;
	}

	public void setContrastReadPrpName(String contrastReadPrpName) {
		this.contrastReadPrpName = contrastReadPrpName;
	}

	public String getContrastReadRapName() {
		return contrastReadRapName;
	}

	public void setContrastReadRapName(String contrastReadRapName) {
		this.contrastReadRapName = contrastReadRapName;
	}

	public String getContrastReadRrpName() {
		return contrastReadRrpName;
	}

	public void setContrastReadRrpName(String contrastReadRrpName) {
		this.contrastReadRrpName = contrastReadRrpName;
	}
}
