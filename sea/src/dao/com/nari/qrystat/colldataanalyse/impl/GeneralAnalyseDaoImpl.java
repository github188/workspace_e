package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.customer.CCons;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EDataMpBean;
import com.nari.qrystat.colldataanalyse.EDataTotalBean;
import com.nari.qrystat.colldataanalyse.EMpConsTmnlBean;
import com.nari.qrystat.colldataanalyse.EMpCurCurveBean;
import com.nari.qrystat.colldataanalyse.EMpCurveBean;
import com.nari.qrystat.colldataanalyse.EMpDayCompBean;
import com.nari.qrystat.colldataanalyse.EMpDayDemandBean;
import com.nari.qrystat.colldataanalyse.EMpDayEnergyBean;
import com.nari.qrystat.colldataanalyse.EMpDayIstatBean;
import com.nari.qrystat.colldataanalyse.EMpDayPftimeBean;
import com.nari.qrystat.colldataanalyse.EMpDayPowerBean;
import com.nari.qrystat.colldataanalyse.EMpDayReadBean;
import com.nari.qrystat.colldataanalyse.EMpDayUnbalanceBean;
import com.nari.qrystat.colldataanalyse.EMpDayVstatBean;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpFactorCurveBean;
import com.nari.qrystat.colldataanalyse.EMpMonDemandBean;
import com.nari.qrystat.colldataanalyse.EMpMonEnergyBean;
import com.nari.qrystat.colldataanalyse.EMpMonIstatBean;
import com.nari.qrystat.colldataanalyse.EMpMonPftimeBean;
import com.nari.qrystat.colldataanalyse.EMpMonPowerBean;
import com.nari.qrystat.colldataanalyse.EMpMonReadBean;
import com.nari.qrystat.colldataanalyse.EMpMonUnbalanceBean;
import com.nari.qrystat.colldataanalyse.EMpMonVstatBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.qrystat.colldataanalyse.EMpRdayDemandBean;
import com.nari.qrystat.colldataanalyse.EMpRdayReadBean;
import com.nari.qrystat.colldataanalyse.EMpReadBean;
import com.nari.qrystat.colldataanalyse.EMpReadCurveBean;
import com.nari.qrystat.colldataanalyse.EMpVolCurveBean;
import com.nari.qrystat.colldataanalyse.ETmnlDayStatBean;
import com.nari.qrystat.colldataanalyse.ETmnlMonStatBean;
import com.nari.qrystat.colldataanalyse.ETotalDayStatBean;
import com.nari.qrystat.colldataanalyse.ETotalEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalMonStatBean;
import com.nari.qrystat.colldataanalyse.ETotalPowerCurveBean;
import com.nari.qrystat.colldataanalyse.GeneralAnalyseDao;
import com.nari.qrystat.colldataanalyse.GeneralRealCurDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealFactorDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealPowerDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealVoltDataBean;
import com.nari.qrystat.colldataanalyse.GroupNameBean;
import com.nari.qrystat.colldataanalyse.SameTradeCCons;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.DateUtil;

/**
 * 采集数据综合分析DAO实现类
 * 
 * @author 杨传文
 * @author 姜炜超 modify
 */
public class GeneralAnalyseDaoImpl extends JdbcBaseDAOImpl implements
		GeneralAnalyseDao {

	//定义日志
	private static final Logger logger = Logger.getLogger(GeneralAnalyseDaoImpl.class);
	
	/**
	 * 按用户编码查询用户姓名
	 * @param consNo 用户编码
	 * @return CCons
	 */
	public CCons findConsNameByConsNo(String consNo) {
		String sql = "SELECT CONS_NAME,CONS_SORT_CODE,CONS_TYPE FROM C_CONS WHERE CONS_NO = ?";
		logger.debug(sql);
		List<CCons> cConsList = super.pagingFindList(sql, 0, 2, new consRowMapper(),consNo);
		if(cConsList == null || cConsList.size() == 0) {
			return new CCons();
		}
		return cConsList.get(0);
	}

	/**
	 * 查询同行业用户用电信息，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param dataDate 查询日期
	 * @param start
	 * @param limit
	 * @return 同行业用户用电信息
	 */
	public Page<SameTradeCCons> findSameTradeCConsByConsNo(String consNo, Date dataDate,long start, int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROW_NUMBER() OVER(ORDER BY S.PAP_E DESC) AS ROWINDEX,\n")
			.append("O.ORG_NAME, C.CONS_NO, C.CONS_NAME, C.CONS_ID,\n")
			.append("C.ELEC_ADDR, C.RUN_CAP, T.TRADE_NAME, V.VOLT,\n")
			.append("E.ELEC_TYPE, TMNL.CIS_ASSET_NO AS TMNL_ASSET_NO, S.ASSET_NO, S.PAP_E,\n")
			.append("S.MAX_P, S.MAX_P_TIME, S.MIN_P, S.MIN_P_TIME\n")
			.append("FROM C_CONS C, O_ORG O, A_CONS_STAT_D S, VW_TMNL_RUN TMNL,\n")
			.append("VW_TRADE T, VW_VOLT_CODE V, VW_ELEC_TYPE_CODE E\n")
			.append("WHERE C.ORG_NO = O.ORG_NO(+)\n")
			.append("AND C.TRADE_CODE = T.TRADE_NO(+)\n")
			.append("AND C.VOLT_CODE = V.VOLT_CODE(+)\n")
			.append("AND C.ELEC_TYPE_CODE = E.ELEC_TYPE_CODE(+)\n")
			.append("AND C.CONS_NO = S.CONS_NO AND S.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO AND S.CONS_NO = TMNL.CONS_NO \n")
			.append("AND S.STAT_DATE = TO_DATE(?,'YYYY-MM-DD')\n")
			.append("AND C.TRADE_CODE=(SELECT TRADE_CODE FROM C_CONS WHERE CONS_NO = ?)\n")
			.append("ORDER BY S.PAP_E DESC");
		return super.pagingFind(sql.toString(), start, limit, new sameTradeCConsMapper(), new Object[] {dateToString(dataDate), consNo});
	}

	/**
	 * 按用户编码查询测量点数据列表，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param start
	 * @param limit
	 * @return 测量点数据列表
	 */
	public Page<EDataMpBean> findEDataMpByConsNo(String consNo, long start,
			int limit) {
        String sql = 
        	"SELECT MP.ID,\n" +
        	"       MP.ASSET_NO,\n" + 
        	"       S.DATA_SRC,\n" + 
        	"       T.FREEZE_CYCLE_NUM,\n" + 
        	"       MP.TERMINAL_ADDR,\n" + 
        	"       CP.MP_NO,\n" + 
        	"       CP.MP_NAME\n" + 
        	"  FROM E_DATA_MP       MP,\n" + 
        	"       R_TMNL_RUN      T,\n" + 
        	"       VW_DATA_SRC     S,\n" + 
        	"       C_METER_MP_RELA RELA,\n" + 
        	"       C_MP            CP\n" + 
        	" WHERE MP.TMNL_ASSET_NO = T.TMNL_ASSET_NO\n" + 
        	"   AND MP.DATA_SRC = S.DATA_SRC_NO\n" + 
        	"   AND MP.IS_VALID = 1\n" + 
        	"   AND MP.METER_ID = RELA.METER_ID(+)\n" + 
        	"   AND RELA.MP_ID = CP.MP_ID(+) AND MP.MP_SN != 0\n" + 
        	"   AND MP.CONS_NO = ?\n" + 
        	" ORDER BY MP.TMNL_ASSET_NO DESC";

		return super.pagingFind(sql, start, limit, new eDataMpMapper(), consNo);
	}

	/**
	 * 按用户编码查询总加组数据列表，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param start
	 * @param limit
	 * @return 总加组数据列表
	 */
	public Page<EDataTotalBean> findEDataTotalByConsNo(String consNo, long start,
			int limit) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ROW_NUMBER() OVER(ORDER BY T.ID) AS ROWINDEX, \n")
		   .append("T.ID,TMNL.FREEZE_CYCLE_NUM,T.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO,T.TOTAL_NO \n")
		   .append("FROM E_DATA_TOTAL T, R_TMNL_RUN TMNL \n")
		   .append("WHERE T.tmnl_asset_no = TMNL.tmnl_asset_no AND T.CONS_NO = ? \n")
		   .append("ORDER BY T.TMNL_ASSET_NO,T.ID DESC ");
		return super.pagingFind(sql.toString(), start, limit, new eDataTotalMapper(), consNo);
	}

	/**
	 * 按用户编码和日期查询日冻结终端统计数据，查询按钮点击 调用，默认取第一个
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 日冻结终端统计数据
	 */
	public ETmnlDayStatBean findETmnlDayStatByDate(String consNo, Date dataDate, PSysUser pSysUser) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ET.SUPLY_TIME, ET.RESET_NUM, ET.DAY_ECJUMP_NUM, ")
			.append("ET.DAY_BCJUMP_NUM, ET.DAY_PCJUMP_NUM, ET.DAY_RCJUMP_NUM, ET.DAY_COMM\n")
			.append("FROM E_TMNL_DAY_STAT ET\n")
			.append("JOIN VW_TMNL_RUN RT ON ET.Id = RT.TMNL_ASSET_NO\n")
			.append("WHERE RT.CONS_NO = ?\n")
			.append("AND ET.DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
			.append(SysPrivilige.addTmnlFactory("RT"))
			.append(" ORDER BY ET.Id ASC ");
		logger.debug(sql.toString());
		List<ETmnlDayStatBean> list = super.pagingFindList(sql.toString(), 0,
				2, new eTmnlDayStatMapper(), new Object[]{consNo, dateToString(dataDate), areaCode, pSysUser.getStaffNo()});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按用户编码和日期范围查询日冻结终端统计数据列表，把该客户下所有终端信息列出来
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @param pSysUser 操作员
	 * @return 日冻结终端统计数据列表
	 */
	public Page<ETmnlDayStatBean> findETmnlDayStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit, PSysUser pSysUser) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<ETmnlDayStatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT RT.CIS_ASSET_NO AS TMNL_ASSET_NO, ET.DATA_DATE, ET.SUPLY_TIME, ET.RESET_NUM, ET.DAY_ECJUMP_NUM, ")
		.append("ET.DAY_BCJUMP_NUM, ET.DAY_PCJUMP_NUM, ET.DAY_RCJUMP_NUM, ET.DAY_COMM\n")
		.append("FROM E_TMNL_DAY_STAT ET\n")
		.append("JOIN VW_TMNL_RUN RT ON ET.Id = RT.TMNL_ASSET_NO\n")
		.append("WHERE RT.CONS_NO = ?\n")
		.append("AND ET.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n")
		.append("AND ET.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1) AND ET.AREA_CODE = ?\n")
		.append(SysPrivilige.addTmnlFactory("RT"))
		.append(" ORDER BY ET.DATA_DATE ");
		return super.pagingFind(sql.toString(), start, limit,
				new eTmnlDayStatListMapper(), new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, pSysUser.getStaffNo() });
	}

	/**
	 * 按用户编码和日期查询月冻结终端统计数据，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 月冻结终端统计数据
	 */
	public ETmnlMonStatBean findETmnlMonStatByDate(String consNo, Date dataDate, PSysUser pSysUser) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ET.SUPLY_TIME, ET.RESET_NUM, ET.MON_ECJUMP_NUM, ")
			.append("ET.MON_BCJUMP_NUM, ET.MON_PCJUMP_NUM, ET.MON_RCJUMP_NUM, ET.MON_COMM\n")
			.append("FROM E_TMNL_MON_STAT ET\n")
			.append("JOIN VW_TMNL_RUN RT ON ET.Id = RT.TMNL_ASSET_NO\n")
			.append("WHERE RT.CONS_NO = ?\n")
			.append("AND ET.DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
			.append(SysPrivilige.addTmnlFactory("RT"))
			.append(" ORDER BY ET.Id ASC ");
		logger.debug(sql.toString());
		List<ETmnlMonStatBean> list = super.pagingFindList(sql.toString(), 0,
				2, new eTmnlMonStatMapper(), new Object[]{consNo, dateToStr(dataDate), areaCode, pSysUser.getStaffNo()});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按用户编码和日期范围查询月冻结终端统计数据列表 
	 * @param consNo  用户编号
	 * @param dataDateFrom  数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit  每页记录条数
	 * @param pSysUser 操作员
	 * @return 月冻结终端统计数据列表
	 */
	public Page<ETmnlMonStatBean> findETmnlMonStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit, PSysUser pSysUser) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<ETmnlMonStatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT RT.CIS_ASSET_NO AS TMNL_ASSET_NO, ET.DATA_DATE, ET.SUPLY_TIME, ET.RESET_NUM, ET.MON_ECJUMP_NUM, ")
		.append("ET.MON_BCJUMP_NUM, ET.MON_PCJUMP_NUM, ET.MON_RCJUMP_NUM, ET.MON_COMM\n")
		.append("FROM E_TMNL_MON_STAT ET\n")
		.append("JOIN VW_TMNL_RUN RT ON ET.Id = RT.TMNL_ASSET_NO\n")
		.append("WHERE RT.CONS_NO = ?\n")
		.append("AND ET.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n")
		.append("AND ET.DATA_DATE < TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
		.append(SysPrivilige.addTmnlFactory("RT"))
		.append(" ORDER BY ET.DATA_DATE");
		return super.pagingFind(sql.toString(), start, limit,
				new eTmnlMonStatListMapper(), new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, pSysUser.getStaffNo()});
	}

	/**
	 * 按用户编码和日期查询日冻结总加组统计数据，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 日冻结总加组统计数据
	 */
	public ETotalDayStatBean findETotalDayStatByDate(String consNo,
			Date dataDate) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ET.DMAX_AP, ET.DMAX_AP_TIME, ET.DMIN_AP, \n")
			.append("ET.DMIN_AP_TIME, ET.DZERO_AP_SUMTIME, ET.DAY_SUME, ET.DAY_SUMRE\n")
			.append("FROM E_TOTAL_DAY_STAT ET\n") 
			.append("JOIN E_DATA_TOTAL ED ON ET.ID = ED.ID\n")
			.append("WHERE ED.CONS_NO = ?\n")
			.append("AND ET.DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
            .append("ORDER BY ED.TMNL_ASSET_NO ASC");
		logger.debug(sql.toString());
		List<ETotalDayStatBean> list = super.pagingFindList(sql.toString(), 0,
				2, new eTotalDayStatMapper(), new Object[] { consNo, dateToString(dataDate) , areaCode});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按用户编码和日期范围查询日冻结总加组统计数据列表 
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 日冻结总加组统计数据列表
	 */
	public Page<ETotalDayStatBean> findETotalDayStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<ETotalDayStatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, ET.DATA_DATE, ET.DMAX_AP, ET.DMAX_AP_TIME, ET.DMIN_AP, ET.DMIN_AP_TIME, ET.DZERO_AP_SUMTIME,")
			.append("ET.DAY_SUME, ET.DAY_SUME1, ET.DAY_SUME2,ET.DAY_SUME3, ET.DAY_SUME4,ET.DAY_SUME5, ET.DAY_SUME6, ET.DAY_SUME7,\n")
			.append("ET.DAY_SUME8, ET.DAY_SUME9, ET.DAY_SUME10, ET.DAY_SUME11, ET.DAY_SUME12, ET.DAY_SUME13, ET.DAY_SUMRE\n")
			.append("FROM E_TOTAL_DAY_STAT ET, \n")
			.append("E_DATA_TOTAL ED \n")
			.append("WHERE ET.ID = ED.ID AND ED.CONS_NO = ?\n")
		    .append("AND ET.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND ET.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1) AND ET.AREA_CODE = ?\n")
			.append("ORDER BY ET.DATA_DATE");

		return super.pagingFind(sql.toString(), start, limit,
				new eTotalDayStatListMapper(), new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo) , areaCode });
	}

	/**
	 * 按用户编码和日期查询月冻结总加组统计数据，查询按钮点击 调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 月冻结总加组统计数据
	 */
	public ETotalMonStatBean findETotalMonStatByDate(String consNo,
			Date dataDate) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ET.MMAX_AP, ET.MMAX_AP_TIME, ET.MMIN_AP, \n")
			.append("ET.MMIN_AP_TIME, ET.MZERO_AP_SUMTIME, ET.MON_SUME, ET.MON_SUMRE\n")
			.append("FROM E_TOTAL_MON_STAT ET\n")
			.append("JOIN E_DATA_TOTAL ED ON ET.ID = ED.ID\n")
			.append("WHERE ED.CONS_NO = ?\n")
			.append("AND ET.DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
		    .append("ORDER BY ED.TMNL_ASSET_NO ASC");
		logger.debug(sql.toString());
		List<ETotalMonStatBean> list = super.pagingFindList(sql.toString(), 0,
				2, new eTotalMonStatMapper(), new Object[] { consNo, dateToStr(dataDate),areaCode});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按用户编码和日期范围查询月冻结总加组统计数据列表 
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 月冻结总加组统计数据列表
	 */
	public Page<ETotalMonStatBean> findETotalMonStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<ETotalMonStatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO,ET.DATA_DATE, ET.MMAX_AP, ET.MMAX_AP_TIME, ET.MMIN_AP, ET.MMIN_AP_TIME, ET.MZERO_AP_SUMTIME,\n")
				.append("ET.MON_SUME, ET.MON_SUME1, ET.MON_SUME2,ET.MON_SUME3, ET.MON_SUME4,ET.MON_SUME5, ET.MON_SUME6, ET.MON_SUME7,\n")
				.append("ET.MON_SUME8, ET.MON_SUME9, ET.MON_SUME10, ET.MON_SUME11, ET.MON_SUME12, ET.MON_SUME13, ET.MON_SUMRE\n")
				.append("FROM E_TOTAL_MON_STAT ET, \n")
				.append("E_DATA_TOTAL ED\n")
				.append("WHERE ET.ID = ED.ID AND ED.CONS_NO = ?\n")
		        .append("AND ET.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n")
		        .append("AND ET.DATA_DATE < TO_DATE(?,'YYYY-MM-DD') AND ET.AREA_CODE = ?\n")
				.append("ORDER BY ET.DATA_DATE");

		return super.pagingFind(sql.toString(), start, limit,
				new eTotalMonStatListMapper(), new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)) ,areaCode});
	}

	/**
	 * 按总加组ID和日期查询日总加组功率曲线
	 * @param totalId 总加组ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public ETotalPowerCurveBean findETotalPowerCurveByDate(Long totalId,
			Date dataDate,  int dataType, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12,\n")
			.append(" P13, P14, P15, P16, P17, P18, P19, P20, P21, P22, P23, P24,\n")
			.append(" P25, P26, P27, P28, P29, P30, P31, P32, P33, P34, P35, P36,\n")
			.append(" P37, P38, P39, P40, P41, P42, P43, P44, P45, P46, P47, P48,\n")
			.append(" P49, P50, P51, P52, P53, P54, P55, P56, P57, P58, P59, P60,\n")
			.append(" P61, P62, P63, P64, P65, P66, P67, P68, P69, P70, P71, P72,\n")
			.append(" P73, P74, P75, P76, P77, P78, P79, P80, P81, P82, P83, P84,\n")
			.append(" P85, P86, P87, P88, P89, P90, P91, P92, P93, P94, P95, P96\n")
			.append("FROM E_TOTAL_POWER_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TYPE = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<ETotalPowerCurveBean> list = super.pagingFindList(sql.toString(), 0,
				2, new eTotalPowerCurveMapper(), new Object[]{totalId, dataType, dateToString(dataDate),areaCode});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 按总加组ID和日期查询日总加组电能量曲线
	 * @param totalId 总加组ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组电能量曲线
	 */
	public ETotalEnergyCurveBean findETotalEnergyCurveByDate(Long totalId,
			Date dataDate, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12,\n")
			.append(" R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24,\n")
			.append(" R25, R26, R27, R28, R29, R30, R31, R32, R33, R34, R35, R36,\n")
			.append(" R37, R38, R39, R40, R41, R42, R43, R44, R45, R46, R47, R48,\n")
			.append(" R49, R50, R51, R52, R53, R54, R55, R56, R57, R58, R59, R60,\n")
			.append(" R61, R62, R63, R64, R65, R66, R67, R68, R69, R70, R71, R72,\n")
			.append(" R73, R74, R75, R76, R77, R78, R79, R80, R81, R82, R83, R84,\n")
			.append(" R85, R86, R87, R88, R89, R90, R91, R92, R93, R94, R95, R96\n")
			.append("FROM E_TOTAL_ENERGY_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<ETotalEnergyCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eTotalEnergyCurveMapper(), new Object[] { totalId,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 按测量点ID和日期查询日测量点电流曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电流曲线
	 */
	public EMpCurCurveBean findEMpCurCurveByDate(Long mpId, Date dataDate, int dataType, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, I11, I12,\n")
			.append(" I13, I14, I15, I16, I17, I18, I19, I20, I21, I22, I23, I24,\n")
			.append(" I25, I26, I27, I28, I29, I30, I31, I32, I33, I34, I35, I36,\n")
			.append(" I37, I38, I39, I40, I41, I42, I43, I44, I45, I46, I47, I48,\n")
			.append(" I49, I50, I51, I52, I53, I54, I55, I56, I57, I58, I59, I60,\n")
			.append(" I61, I62, I63, I64, I65, I66, I67, I68, I69, I70, I71, I72,\n")
			.append(" I73, I74, I75, I76, I77, I78, I79, I80, I81, I82, I83, I84,\n")
			.append(" I85, I86, I87, I88, I89, I90, I91, I92, I93, I94, I95, I96\n")
			.append("FROM E_MP_CUR_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND PHASE_FLAG = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpCurCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpCurCurveMapper(), new Object[] { mpId, dataType,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按测量点ID和日期查询日测量点功率曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点功率曲线
	 */
	public EMpPowerCurveBean findEMpPowerCurveByDate(Long mpId, Date dataDate, int dataType, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12,\n")
			.append(" P13, P14, P15, P16, P17, P18, P19, P20, P21, P22, P23, P24,\n")
			.append(" P25, P26, P27, P28, P29, P30, P31, P32, P33, P34, P35, P36,\n")
			.append(" P37, P38, P39, P40, P41, P42, P43, P44, P45, P46, P47, P48,\n")
			.append(" P49, P50, P51, P52, P53, P54, P55, P56, P57, P58, P59, P60,\n")
			.append(" P61, P62, P63, P64, P65, P66, P67, P68, P69, P70, P71, P72,\n")
			.append(" P73, P74, P75, P76, P77, P78, P79, P80, P81, P82, P83, P84,\n")
			.append(" P85, P86, P87, P88, P89, P90, P91, P92, P93, P94, P95, P96\n")
			.append("FROM E_MP_POWER_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TYPE = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpPowerCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpPowerCurveMapper(), new Object[] { mpId, dataType,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按测量点ID和日期查询日测量点总电能量曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能量曲线
	 */
	public EMpEnergyCurveBean findEMpEnergyCurveByDate(Long mpId, Date dataDate, int dataType, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12,\n")
			.append(" E13, E14, E15, E16, E17, E18, E19, E20, E21, E22, E23, E24,\n")
			.append(" E25, E26, E27, E28, E29, E30, E31, E32, E33, E34, E35, E36,\n")
			.append(" E37, E38, E39, E40, E41, E42, E43, E44, E45, E46, E47, E48,\n")
			.append(" E49, E50, E51, E52, E53, E54, E55, E56, E57, E58, E59, E60,\n")
			.append(" E61, E62, E63, E64, E65, E66, E67, E68, E69, E70, E71, E72,\n")
			.append(" E73, E74, E75, E76, E77, E78, E79, E80, E81, E82, E83, E84,\n")
			.append(" E85, E86, E87, E88, E89, E90, E91, E92, E93, E94, E95, E96\n")
			.append("FROM E_MP_ENERGY_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TYPE = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpEnergyCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpEnergyCurveMapper(), new Object[] { mpId, dataType,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 按测量点ID和日期查询日测量点电压曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能量曲线
	 */
	public EMpVolCurveBean findEMpVolCurveByDate(Long mpId, Date dataDate, int dataType, String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, U1, U2, U3, U4, U5, U6, U7, U8, U9, U10, U11, U12,\n")
			.append(" U13, U14, U15, U16, U17, U18, U19, U20, U21, U22, U23, U24,\n")
			.append(" U25, U26, U27, U28, U29, U30, U31, U32, U33, U34, U35, U36,\n")
			.append(" U37, U38, U39, U40, U41, U42, U43, U44, U45, U46, U47, U48,\n")
			.append(" U49, U50, U51, U52, U53, U54, U55, U56, U57, U58, U59, U60,\n")
			.append(" U61, U62, U63, U64, U65, U66, U67, U68, U69, U70, U71, U72,\n")
			.append(" U73, U74, U75, U76, U77, U78, U79, U80, U81, U82, U83, U84,\n")
			.append(" U85, U86, U87, U88, U89, U90, U91, U92, U93, U94, U95, U96\n")
			.append("FROM E_MP_VOL_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND PHASE_FLAG = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpVolCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpVolCurveMapper(), new Object[] { mpId, dataType,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 按测量点ID和日期查询日测量点功率因素曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点功率曲线
	 */
	public EMpFactorCurveBean findEMpFactorCurveByDate(Long mpId, Date dataDate, int dataType, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12,\n")
		    .append(" C13, C14, C15, C16, C17, C18, C19, C20, C21, C22, C23, C24,\n")
		    .append(" C25, C26, C27, C28, C29, C30, C31, C32, C33, C34, C35, C36,\n")
		    .append(" C37, C38, C39, C40, C41, C42, C43, C44, C45, C46, C47, C48,\n")
		    .append(" C49, C50, C51, C52, C53, C54, C55, C56, C57, C58, C59, C60,\n")
		    .append(" C61, C62, C63, C64, C65, C66, C67, C68, C69, C70, C71, C72,\n")
		    .append(" C73, C74, C75, C76, C77, C78, C79, C80, C81, C82, C83, C84,\n")
		    .append(" C85, C86, C87, C88, C89, C90, C91, C92, C93, C94, C95, C96\n")	
			.append("FROM E_MP_FACTOR_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND PHASE_FLAG = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpFactorCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpFactorCurveMapper(), new Object[] { mpId, dataType,
						dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 按测量点ID和日期查询日测量点总电能示值曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能示值曲线
	 */
	public EMpReadCurveBean findEMpReadCurveByDate(Long mpId, Date dataDate, int dataType, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12,\n")
			.append(" R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24,\n")
			.append(" R25, R26, R27, R28, R29, R30, R31, R32, R33, R34, R35, R36,\n")
			.append(" R37, R38, R39, R40, R41, R42, R43, R44, R45, R46, R47, R48,\n")
			.append(" R49, R50, R51, R52, R53, R54, R55, R56, R57, R58, R59, R60,\n")
			.append(" R61, R62, R63, R64, R65, R66, R67, R68, R69, R70, R71, R72,\n")
			.append(" R73, R74, R75, R76, R77, R78, R79, R80, R81, R82, R83, R84,\n")
			.append(" R85, R86, R87, R88, R89, R90, R91, R92, R93, R94, R95, R96\n")
			.append("FROM E_MP_READ_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TYPE = ? \n")
			.append("AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD') AND AREA_CODE= ?");
		logger.debug(sql.toString());
		List<EMpReadCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpReadCurveMapper(), new Object[] { mpId, dataType,
			        dateToString(dataDate),areaCode });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 按测量点ID和日期查询日测量点实时功率曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return List<GeneralRealPowerDataBean>
	 */
	public List<GeneralRealPowerDataBean> findEMpRealPowerCurveByDate(Long mpId, Date dataDate, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, P, PA, PB, PC\n")
		    .append(" FROM E_MP_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
			.append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1)  AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<GeneralRealPowerDataBean> list = 
			super.getJdbcTemplate().query(sql.toString(), new Object[] { mpId,
					dateToString(dataDate), dateToString(dataDate),areaCode}, new eMpRealPowerCurveMapper());
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	
	/**
	 * 按测量点ID和日期查询日测量点实时电流曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return List<GeneralRealCurDataBean>
	 */
	public List<GeneralRealCurDataBean> findEMpRealCurCurveByDate(Long mpId, Date dataDate, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, I0, IA, IB, IC\n")
	        .append(" FROM E_MP_CURVE \n")
		    .append("WHERE ID = ? \n")
		    .append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1) AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<GeneralRealCurDataBean> list =  
			super.getJdbcTemplate().query(sql.toString(), new Object[] { mpId,
				dateToString(dataDate), dateToString(dataDate),areaCode}, new eMpRealCurCurveMapper());
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 按测量点ID和日期查询日测量点实时电压曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return List<GeneralRealVoltDataBean> 
	 */
	public List<GeneralRealVoltDataBean> findEMpRealVoltCurveByDate(Long mpId, Date dataDate, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, UA, UB, UC\n")
		    .append(" FROM E_MP_CURVE \n")
		    .append("WHERE ID = ? \n")
		    .append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1) AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<GeneralRealVoltDataBean> list = 
			super.getJdbcTemplate().query(sql.toString(), new Object[] { mpId,
				dateToString(dataDate), dateToString(dataDate),areaCode}, new eMpRealVoltCurveMapper());
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 按测量点ID和日期查询日测量点实时功率因素曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return List<GeneralRealFactorDataBean>
	 */
	public List<GeneralRealFactorDataBean> findEMpRealFactorCurveByDate(Long mpId, Date dataDate, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, F, FA, FB, FC\n")
	        .append(" FROM E_MP_CURVE \n")
		    .append("WHERE ID = ? \n")
		    .append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1) AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<GeneralRealFactorDataBean> list =
			super.getJdbcTemplate().query(sql.toString(), new Object[] { mpId,
				dateToString(dataDate), dateToString(dataDate),areaCode}, new eMpRealFactorCurveMapper());
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 按总加组ID和日期查询日总加组实时功率曲线
	 * @param totalId 总加组ID
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public List<GeneralRealPowerDataBean> findETotalRealPowerCurveByDate(Long totalId,
			Date dataDate, String consNo){
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, P\n")
	        .append(" FROM E_TOTAL_CURVE \n")
		    .append("WHERE ID = ? \n")
		    .append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1) AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<GeneralRealPowerDataBean> list =
			super.getJdbcTemplate().query(sql.toString(), new Object[] { totalId,
				dateToString(dataDate), dateToString(dataDate),areaCode}, new eTotalRealPowerCurveMapper());
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	
//	--------------------------------------------------测量点数据统计----------------------------------------------------
	/**
	 * 测量点日电容器累计补偿的无功电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayCompBean>
	 */
	public Page<EMpDayCompBean> findEMpDayCompByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayCompBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.CAP_USE_TIME1, EM.CAP_USE_TIME2, EM.CAP_USE_TIME3,CAP_USE_TIME4, EM.CAP_USE_TIME5, EM.\n")
			.append("CAP_USE_TIME6, EM.CAP_USE_TIME7, EM.CAP_USE_TIME8, EM.CAP_USE_TIME9, EM.CAP_USE_NUM1,CAP_USE_NUM2, EM.CAP_USE_NUM3, EM.\n")
			.append("CAP_USE_NUM4, EM.CAP_USE_NUM5, EM.CAP_USE_NUM6, EM.CAP_USE_NUM7, EM.CAP_USE_NUM8, EM.CAP_USE_NUM9, EM.DAY_COMP_E, EM.MON_COMP_E,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_COMP EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayCompListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayCompListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 日冻结需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayDemandBean>
	 */
	public Page<EMpDayDemandBean> findEMpDayDemandByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayDemandBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME, EM.PAP_DEMAND, EM.PAP_DEMAND_TIME, EM.PRP_DEMAND,\n")
			.append("PRP_DEMAND_TIME, EM.RAP_DEMAND, EM.RAP_DEMAND_TIME, EM.RRP_DEMAND, EM.RRP_DEMAND_TIME,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_DEMAND EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayDemandListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayDemandListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点日冻结电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayEnergyBean>
	 */
	public Page<EMpDayEnergyBean> findEMpDayEnergyByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayEnergyBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.PAP_E, EM.PAP_E1, EM.PAP_E2, EM.PAP_E3, EM.PAP_E4, EM.PRP_E, EM.PRP_E1, EM.PRP_E2, \n")
			.append("EM.PRP_E3, EM.PRP_E4, EM.RAP_E, EM.RAP_E1, EM.RAP_E2, EM.RAP_E3, EM.RAP_E4, EM.RRP_E, EM.RRP_E1, EM.RRP_E2, EM.RRP_E3, EM.RRP_E4,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_ENERGY EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayEnergyListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayEnergyListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点日冻结电流越限统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayIstatBean>
	 */
	public Page<EMpDayIstatBean> findEMpDayIstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayIstatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.CT, EM.IA_UUP_TIME, EM.IA_UP_TIME, EM.IB_UUP_TIME, EM.IB_UP_TIME, EM.IC_UUP_TIME, EM.IC_UP_TIME,\n")
			.append("EM.I0_UP_TIME, EM.IA_MAX, EM.IA_MAX_TIME, EM.IB_MAX, EM.IB_MAX_TIME, EM.IC_MAX, EM.IC_MAX_TIME, EM.I0_MAX, EM.I0_MAX_TIME,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_ISTAT EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayIstatListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayIstatListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 日冻结功率因数区段累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayPftimeBean>
	 */
	public Page<EMpDayPftimeBean> findEMpDayPftimeByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayPftimeBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.SUM_TIME1, EM.SUM_TIME2, EM.SUM_TIME3,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_PFTIME EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayPftimeListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayPftimeListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点日冻结总及分相有功功率
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayPowerBean>
	 */
	public Page<EMpDayPowerBean> findEMpDayPowerByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayPowerBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.CT, EM.PT, EM.TOTAL_MAX_P, EM.TOTAL_MAX_P_TIME, EM.TOTAL_MAX_PA, \n")
			.append("EM.TOTAL_MAX_PA_TIME, EM.TOTAL_MAX_PB, EM.TOTAL_MAX_PB_TIME, EM.TOTAL_MAX_PC, \n")
			.append("EM.TOTAL_MAX_PC_TIME, EM.TOTAL_P_ZERO, EM.TOTAL_PA_ZERO, EM.TOTAL_PB_ZERO, EM.TOTAL_PC_ZERO,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_POWER EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayPowerListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayPowerListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点日冻结电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayReadBean> 
	 */
	public Page<EMpDayReadBean> findEMpDayReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayReadBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME, EM.CT, EM.PT, EM.PAP_R, EM.PAP_R1, EM.PAP_R2, EM.PAP_R3, EM.PAP_R4,\n")
			.append("PRP_R, EM.PRP_R1, EM.PRP_R2, EM.PRP_R3, EM.PRP_R4, EM.RAP_R, EM.RAP_R1, EM.RAP_R3, EM.RAP_R2, EM.RAP_R4,\n")
			.append("RRP_R, EM.RRP_R1, EM.RRP_R2, EM.RRP_R3, EM.RRP_R4, EM.RP1_R, EM.RP4_R, EM.RP2_R, EM.RP3_R ,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n")
			.append("FROM E_MP_DAY_READ EM , \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 日不平衡度越限累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayUnbalanceBean>
	 */
	public Page<EMpDayUnbalanceBean> findEMpDayUnbalanceByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayUnbalanceBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.I_UB_TIME, EM.U_UB_TIME, \n")
			.append("EM.I_UB_MAX_VAL, EM.I_UB_MAX_TIME, EM.U_UB_MAX_VAL, EM.U_UB_MAX_TIME,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_UNBALANCE EM,\n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayUnbalanceListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayUnbalanceListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点日冻结电压统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayVstatBean>
	 */
	public Page<EMpDayVstatBean> findEMpDayVstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpDayVstatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.PT, EM.UA_UUP_TIME, EM.UA_LLW_TIME, EM.UA_UP_TIME, EM.UA_LOW_TIME, EM.UA_NML_TIME, \n")
			.append("EM.UB_UUP_TIME, EM.UB_LLW_TIME, EM.UB_UP_TIME, EM.UB_LOW_TIME, EM.UB_NML_TIME, EM.UC_UUP_TIME, EM.UC_LLW_TIME, \n")
			.append("EM.UC_UP_TIME, EM.UC_LOW_TIME, EM.UC_NML_TIME, EM.UA_MAX, EM.UA_MAX_TIME, EM.UA_MIN, EM.UA_MIN_TIME, EM.UB_MAX, \n")
			.append("EM.UB_MAX_TIME, EM.UB_MIN, EM.UB_MIN_TIME, EM.UC_MAX, EM.UC_MAX_TIME, EM.UC_MIN, EM.UC_MIN_TIME, EM.UA_AVERAGE, \n") 
			.append("EM.UB_AVERAGE, EM.UC_AVERAGE,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_DAY_VSTAT EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayVstatListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpDayVstatListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结最大需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonDemandBean>
	 */
	public Page<EMpMonDemandBean> findEMpMonDemandByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonDemandBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME, EM.PAP_DEMAND, EM.PAP_DEMAND_TIME,\n")
			.append("EM.PRP_DEMAND, EM.PRP_DEMAND_TIME, EM.RAP_DEMAND, EM.RAP_DEMAND_TIME,\n") 
			.append("EM.RRP_DEMAND, EM.RRP_DEMAND_TIME,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_MON_DEMAND EM, \n") 
            .append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonDemandListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonDemandListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonEnergyBean>
	 */
	public Page<EMpMonEnergyBean> findEMpMonEnergyByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonEnergyBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.PAP_E, EM.PAP_E1, EM.PAP_E2, EM.PAP_E3, EM.PAP_E4,\n")
			.append("EM.PRP_E, EM.PRP_E1, EM.PRP_E2, EM.PRP_E3, EM.PRP_E4,\n")
			.append("EM.RAP_E, EM.RAP_E1, EM.RAP_E2, EM.RAP_E3, EM.RAP_E4,\n")
			.append("EM.RRP_E, EM.RRP_E1, EM.RRP_E2, EM.RRP_E3, EM.RRP_E4, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n")
			.append("FROM E_MP_MON_ENERGY EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");  
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonEnergyListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonEnergyListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结电流越限统计数据
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonIstatBean> 
	 */
	public Page<EMpMonIstatBean> findEMpMonIstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonIstatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.CT, EM.IA_UUP_TIME, EM.IA_UP_TIME, EM.IB_UUP_TIME,\n")
			.append("EM.IB_UP_TIME, EM.IC_UUP_TIME, EM.IC_UP_TIME, EM.I0_UP_TIME, EM.IA_MAX,\n")
			.append("EM.IA_MAX_TIME, EM.IB_MAX, EM.IB_MAX_TIME, EM.IC_MAX, EM.IC_MAX_TIME,\n") 
			.append("EM.I0_MAX, EM.I0_MAX_TIME , ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n")
			.append("FROM E_MP_MON_ISTAT EM, \n") 
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonIstatListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonIstatListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结功率因数区段累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonPftimeBean>
	 */
	public Page<EMpMonPftimeBean> findEMpMonPftimeByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonPftimeBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.SUM_TIME1, EM.SUM_TIME2, EM.SUM_TIME3,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR \n")
			.append("FROM E_MP_MON_PFTIME EM ,\n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonPftimeListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonPftimeListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结总及分相有功功率数据
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonPowerBean>
	 */
	public Page<EMpMonPowerBean> findEMpMonPowerByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonPowerBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.CT, EM.PT, EM.TOTAL_MAX_P, EM.TOTAL_MAX_P_TIME,\n")
			.append("EM.TOTAL_MAX_PA, EM.TOTAL_MAX_PA_TIME, EM.TOTAL_MAX_PB, EM.TOTAL_MAX_PB_TIME,\n")
			.append("EM.TOTAL_MAX_PC, EM.TOTAL_MAX_PC_TIME, EM.TOTAL_P_ZERO, EM.TOTAL_PA_ZERO,\n")
			.append("EM.TOTAL_PB_ZERO, EM.TOTAL_PC_ZERO ,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n")
			.append("FROM E_MP_MON_POWER EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonPowerListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonPowerListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月冻结电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonReadBean>
	 */
	public Page<EMpMonReadBean> findEMpMonReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonReadBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME, EM.CT, EM.PT,\n")
			.append("EM.PAP_R, EM.PAP_R1, EM.PAP_R2, EM.PAP_R3, EM.PAP_R4, EM.PRP_R,\n") 
			.append("EM.PRP_R1, EM.PRP_R2, EM.PRP_R3, EM.PRP_R4, EM.RAP_R, EM.RAP_R1,\n") 
			.append("EM.RAP_R3, EM.RAP_R2, EM.RAP_R4, EM.RRP_R, EM.RRP_R1, EM.RRP_R2,\n") 
			.append("EM.RRP_R3, EM.RRP_R4, EM.RP1_R, EM.RP4_R, EM.RP2_R, EM.RP3_R, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_MON_READ EM ,\n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonReadListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonReadListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 月不平衡度越限累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonUnbalanceBean>
	 */
	public Page<EMpMonUnbalanceBean> findEMpMonUnbalanceByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonUnbalanceBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.I_UB_TIME, EM.U_UB_TIME,\n")
			.append("EM.I_UB_MAX_VAL, EM.I_UB_MAX_TIME, EM.U_UB_MAX_VAL, EM.U_UB_MAX_TIME,ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_MON_UNBALANCE EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD') \n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonUnbalanceListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonUnbalanceListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点月冻结电压统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonVstatBean> 
	 */
	public Page<EMpMonVstatBean> findEMpMonVstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpMonVstatBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.PT, EM.UA_UUP_TIME, EM.UA_LLW_TIME,\n")
			.append("EM.UA_UP_TIME, EM.UA_LOW_TIME, EM.UA_NML_TIME, EM.UB_UUP_TIME,\n") 
			.append("EM.UB_LLW_TIME, EM.UB_UP_TIME, EM.UB_LOW_TIME, EM.UB_NML_TIME,\n") 
			.append("EM.UC_UUP_TIME, EM.UC_LLW_TIME, EM.UC_UP_TIME, EM.UC_LOW_TIME,\n") 
			.append("EM.UC_NML_TIME, EM.UA_MAX, EM.UA_MAX_TIME, EM.UA_MIN, EM.UA_MIN_TIME,\n") 
			.append("EM.UB_MAX, EM.UB_MAX_TIME, EM.UB_MIN, EM.UB_MIN_TIME, EM.UC_MAX, EM.UC_MAX_TIME,\n") 
			.append("EM.UC_MIN, EM.UC_MIN_TIME, EM.UA_AVERAGE, EM.UB_AVERAGE, EM.UC_AVERAGE, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_MON_VSTAT EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < TO_DATE(?,'YYYY-MM-DD')\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonVstatListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpMonVstatListMapper(), 
					new Object[] { consNo, dateToStr(dataDateFrom), getNextMonthFirstDay(dateToStr(dataDateTo)), areaCode, tmnlType });
		}
	}

	/**
	 * 抄表日冻结需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpRdayDemandBean>
	 */
	public Page<EMpRdayDemandBean> findEMpRdayDemanByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpRdayDemandBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME,\n")
			.append("EM.PAP_DEMAND, EM.PAP_DEMAND_TIME, EM.PRP_DEMAND, EM.PRP_DEMAND_TIME,\n") 
			.append("EM.RAP_DEMAND, EM.RAP_DEMAND_TIME, EM.RRP_DEMAND, EM.RRP_DEMAND_TIME, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_RDAY_DEMAND EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
            .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRdayDemandListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRdayDemandListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}

	/**
	 * 测量点抄表日冻结电能示值统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpRdayReadBean>
	 */
	public Page<EMpRdayReadBean> findEMpRdayReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpRdayReadBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_DATE, EM.COL_TIME, EM.CT, EM.PT,\n")
			.append("EM.PAP_R, EM.PAP_R1, EM.PAP_R2, EM.PAP_R3, EM.PAP_R4, EM.PRP_R,\n") 
			.append("EM.PRP_R1, EM.PRP_R2, EM.PRP_R3, EM.PRP_R4, EM.RAP_R, EM.RAP_R1,\n") 
			.append("EM.RAP_R3, EM.RAP_R2, EM.RAP_R4, EM.RRP_R, EM.RRP_R1, EM.RRP_R2,\n") 
			.append("EM.RRP_R3, EM.RRP_R4, EM.RP1_R, EM.RP4_R, EM.RP2_R, EM.RP3_R, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_RDAY_READ EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_DATE >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_DATE < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRdayReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_DATE");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRdayReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}
	
	/**
	 * 当前电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpReadBean>
	 */
	public Page<EMpReadBean> findEMpRealDataReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpReadBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_TIME, EM.COL_TIME, EM.CT, EM.PT,\n")
			.append("EM.PAP_R, EM.PAP_R1, EM.PAP_R2, EM.PAP_R3, EM.PAP_R4, EM.PRP_R,\n") 
			.append("EM.PRP_R1, EM.PRP_R2, EM.PRP_R3, EM.PRP_R4, EM.RAP_R, EM.RAP_R1,\n") 
			.append("EM.RAP_R3, EM.RAP_R2, EM.RAP_R4, EM.RRP_R, EM.RRP_R1, EM.RRP_R2,\n") 
			.append("EM.RRP_R3, EM.RRP_R4, EM.RP1_R, EM.RP4_R, EM.RP2_R, EM.RP3_R, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_READ EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_TIME");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRealDataReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_TIME");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRealDataReadListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}
	
	/**
	 * 当前测量点曲线
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpCurveBean>
	 */
	public Page<EMpCurveBean> findEMpRealCurveDataByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new Page<EMpCurveBean>();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ED.CIS_TMNL_ASSET_NO AS TMNL_ASSET_NO, EM.DATA_TIME,EM.CT,EM.PT,EM.MARK,EM.UA,EM.UB,EM.UC,EM.IA,\n")
			.append("EM.IB,EM.IC,EM.I0,EM.P,EM.PA,EM.PB,EM.PC,EM.Q,EM.QA,EM.QB,EM.QC,\n") 
			.append("EM.LOAD_MARK, EM.F,EM.FA, EM.FB, EM.FC, EM.AN_UABA, EM.AN_UB, EM.AN_UCBC,\n") 
			.append("EM.AN_IA, EM.AN_IB, EM.AN_IC, EM.PAP_E,EM.PRP_E, EM.RAP_E, EM.RRP_E, EM.PAP_R,\n") 
			.append("EM.PRP_R,EM.RAP_R, EM.RRP_R, ED.ASSET_NO,CP.MP_NO,CP.MP_NAME,ED.TERMINAL_ADDR\n") 
			.append("FROM E_MP_CURVE EM, \n")
			.append("E_DATA_MP ED , C_METER_MP_RELA RELA, C_MP CP\n")
			.append("WHERE EM.ID = ED.ID\n")
			.append("AND EM.AREA_CODE = ED.AREA_CODE\n")
			.append("AND ED.METER_ID = RELA.METER_ID(+) AND RELA.MP_ID = CP.MP_ID(+)\n")
			.append("AND ED.CONS_NO = ?\n")
			.append("AND EM.DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n") 
			.append("AND EM.DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1)\n")
		    .append("AND EM.AREA_CODE = ?\n");
		if("*".equals(tmnlType)){
			sql.append("ORDER BY EM.ID, EM.DATA_TIME");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRealCurveDataListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode });
		}else{
			sql.append("AND ED.TMNL_ASSET_NO = ?\n");
			sql.append("ORDER BY EM.ID, EM.DATA_TIME");
			logger.debug(sql.toString());
			return super.pagingFind(sql.toString(), start, limit, new eMpRealCurveDataListMapper(), 
					new Object[] { consNo, dateToString(dataDateFrom), dateToString(dataDateTo), areaCode, tmnlType });
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 查询全部普通群组列表
	 * @return 群组名称和群组编号对象列表
	 */
	public List<GroupNameBean> findGroupName() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT R.GROUP_NO,R.GROUP_NAME FROM R_USER_GROUP R");
		logger.debug(sql.toString());
		return getJdbcTemplate().query(sql.toString(), new groupRowMapper());
	}

	/**
	 * 保存群组明细
	 * @param groupNo
	 * @param consNo
	 */
	public void addToGroup(String groupNo, String consNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into r_user_group_detail(group_no,tmnl_asset_no,cons_no) select '")
			.append(groupNo)
			.append("', r.cis_asset_no, '")
			.append(consNo)
			.append("'from vw_tmnl_run r where r.cons_no='")
			.append(consNo)
			.append("'\n")
			.append("and not exists (select 1\n")
			.append("from r_user_group_detail g\n") 
			.append("where g.tmnl_asset_no = r.tmnl_asset_no\n") 
			.append("and g.group_no = '")
			.append(groupNo)
			.append("')");
		logger.debug(sql.toString());
		getJdbcTemplate().execute(sql.toString());
	}
	
	/**
	 * 查询某用户下的终端信息
	 * @param consNo
	 * @return List<EMpConsTmnlBean>
	 */
	public List<EMpConsTmnlBean> queryConsTmnlInfo(String consNo) {
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new ArrayList<EMpConsTmnlBean>();
		}
		String sql = 
			"SELECT '全部终端' AS cis_asset_no, '*' AS TMNL_ASSET_NO\n" +
			"  FROM DUAL\n" + 
			"UNION\n" + 
			"SELECT DISTINCT ED.CIS_TMNL_ASSET_NO AS cis_asset_no, ED.TMNL_ASSET_NO\n" + 
			"  FROM E_DATA_MP ED\n" + 
			" WHERE ED.CONS_NO = ? AND ED.AREA_CODE = ?\n" + 
			" ORDER BY TMNL_ASSET_NO ASC";

		List<EMpConsTmnlBean> list = getJdbcTemplate().query(sql, new Object[] { consNo, areaCode }, new eMpConsTmnlMapper());
		if(null == list){
			return new ArrayList<EMpConsTmnlBean>();
		}
		return list;
	}

	// ---------------------------------------------------------------------------------------------
	/*	
	 * DTO Mappers
	 * 数据传输对象与JDBC结果集的映射类
	*/	
	class consRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			CCons cons = new CCons();
			try {
				cons.setConsName(rs.getString("CONS_NAME"));
				cons.setConsSortCode(rs.getString("CONS_SORT_CODE"));
				cons.setConsType(rs.getByte("CONS_TYPE"));
				return cons;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class sameTradeCConsMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SameTradeCCons sametradeccons = new SameTradeCCons();
		 try { 
			sametradeccons.setRowindex(rs.getLong("ROWINDEX"));
			sametradeccons.setOrgName(rs.getString("ORG_NAME"));
			sametradeccons.setConsNo(rs.getString("CONS_NO"));
			sametradeccons.setConsName(rs.getString("CONS_NAME"));
			sametradeccons.setElecAddr(rs.getString("ELEC_ADDR"));
			sametradeccons.setRunCap((null != rs.getString("RUN_CAP"))? rs.getDouble("RUN_CAP") : null);
			sametradeccons.setTradeName(rs.getString("TRADE_NAME"));
			sametradeccons.setVolt(rs.getString("VOLT"));
			sametradeccons.setElecType(rs.getString("ELEC_TYPE"));
			sametradeccons.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
			sametradeccons.setAssetNo(rs.getString("ASSET_NO"));
			sametradeccons.setPapE((null != rs.getString("PAP_E"))? rs.getDouble("PAP_E") : null);
			sametradeccons.setMaxP((null != rs.getString("MAX_P"))? rs.getDouble("MAX_P") : null);
			sametradeccons.setMaxPTime(timeStampToStr(rs.getTimestamp("MAX_P_TIME")));
			sametradeccons.setMinP((null != rs.getString("MIN_P"))? rs.getDouble("MIN_P") : null);
			sametradeccons.setMinPTime(timeStampToStr(rs.getTimestamp("MIN_P_TIME")));
			sametradeccons.setConsId(rs.getString("CONS_ID"));
			return sametradeccons;
		 }catch (Exception e) {
			return null;
		 }
		}
	}
	class eDataMpMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EDataMpBean bean = new EDataMpBean();
			try {
				bean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				bean.setDataId(rs.getLong("ID"));
				bean.setDataSrc(rs.getString("DATA_SRC"));
				bean.setMeterNo(rs.getString("ASSET_NO"));
				bean.setFreezeCycleNum(rs.getInt("FREEZE_CYCLE_NUM"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setMpNo(rs.getString("MP_NO"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eDataTotalMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EDataTotalBean bean = new EDataTotalBean();
			try {
				bean.setRowNum(rs.getInt("ROWINDEX"));
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setTotalId(rs.getLong("ID"));
				bean.setTotalNo(rs.getShort("TOTAL_NO"));
				bean.setFreezeCycleNum(rs.getInt("FREEZE_CYCLE_NUM"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTmnlDayStatMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETmnlDayStatBean bean = new ETmnlDayStatBean();
			try {
				bean.setDayBcjumpNum(rs.getShort("DAY_BCJUMP_NUM"));
				bean.setDayComm(rs.getLong("DAY_COMM"));
				bean.setDayEcjumpNum(rs.getShort("DAY_ECJUMP_NUM"));
				bean.setDayPcjumpNum(rs.getShort("DAY_PCJUMP_NUM"));
				bean.setDayRcjumpNum(rs.getShort("DAY_RCJUMP_NUM"));
				bean.setDayResetNum(rs.getInt("RESET_NUM"));
				bean.setDaySuplyTime(rs.getInt("SUPLY_TIME"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTmnlDayStatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETmnlDayStatBean bean = new ETmnlDayStatBean();
			try {
				bean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				bean.setDayBcjumpNum((null != rs.getString("DAY_BCJUMP_NUM"))? rs.getShort("DAY_BCJUMP_NUM") : null);
				bean.setDayComm((null != rs.getString("DAY_COMM"))? rs.getLong("DAY_COMM") : null);
				bean.setDayEcjumpNum((null != rs.getString("DAY_ECJUMP_NUM"))? rs.getShort("DAY_ECJUMP_NUM") : null);
				bean.setDayPcjumpNum((null != rs.getString("DAY_PCJUMP_NUM"))? rs.getShort("DAY_PCJUMP_NUM") : null);
				bean.setDayRcjumpNum((null != rs.getString("DAY_RCJUMP_NUM"))? rs.getShort("DAY_RCJUMP_NUM") : null);
				bean.setDayResetNum((null != rs.getString("RESET_NUM"))? rs.getInt("RESET_NUM") : null);
				bean.setDaySuplyTime((null != rs.getString("SUPLY_TIME"))? rs.getInt("SUPLY_TIME") : null);
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTmnlMonStatMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETmnlMonStatBean bean = new ETmnlMonStatBean();
			try {
				bean.setMonBcjumpNum(rs.getShort("MON_BCJUMP_NUM"));
				bean.setMonComm(rs.getLong("MON_COMM"));
				bean.setMonEcjumpNum(rs.getShort("MON_ECJUMP_NUM"));
				bean.setMonPcjumpNum(rs.getShort("MON_PCJUMP_NUM"));
				bean.setMonRcjumpNum(rs.getShort("MON_RCJUMP_NUM"));
				bean.setMonResetNum(rs.getInt("RESET_NUM"));
				bean.setMonSuplyTime(rs.getInt("SUPLY_TIME"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTmnlMonStatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETmnlMonStatBean bean = new ETmnlMonStatBean();
			try {
				bean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				bean.setMonBcjumpNum((null != rs.getString("MON_BCJUMP_NUM"))? rs.getShort("MON_BCJUMP_NUM") : null);
				bean.setMonComm((null != rs.getString("MON_COMM"))? rs.getLong("MON_COMM") : null);
				bean.setMonEcjumpNum((null != rs.getString("MON_ECJUMP_NUM"))? rs.getShort("MON_ECJUMP_NUM") : null);
				bean.setMonPcjumpNum((null != rs.getString("MON_PCJUMP_NUM"))? rs.getShort("MON_PCJUMP_NUM") : null);
				bean.setMonRcjumpNum((null != rs.getString("MON_RCJUMP_NUM"))? rs.getShort("MON_RCJUMP_NUM") : null);
				bean.setMonResetNum((null != rs.getString("RESET_NUM"))? rs.getInt("RESET_NUM") : null);
				bean.setMonSuplyTime((null != rs.getString("SUPLY_TIME"))? rs.getInt("SUPLY_TIME") : null);
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTotalDayStatMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETotalDayStatBean bean = new ETotalDayStatBean();
			try {
				bean.setDmaxAp(rs.getDouble("DMAX_AP"));
				bean.setDmaxApTime(rs.getTime("DMAX_AP_TIME").toString());
				bean.setDminAp(rs.getDouble("DMIN_AP"));
				bean.setDminApTime(rs.getTime("DMIN_AP_TIME").toString());
				bean.setDzeroApSumtime(rs.getInt("DZERO_AP_SUMTIME"));
				bean.setDaySume(rs.getDouble("DAY_SUME"));
				bean.setDaySumre(rs.getDouble("DAY_SUMRE"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTotalDayStatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETotalDayStatBean bean = new ETotalDayStatBean();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				bean.setDmaxAp((null != rs.getString("DMAX_AP"))? rs.getDouble("DMAX_AP") : null);
				bean.setDmaxApTime(rs.getTime("DMAX_AP_TIME").toString());
				bean.setDminAp((null != rs.getString("DMIN_AP"))? rs.getDouble("DMIN_AP") : null);
				bean.setDminApTime(rs.getTime("DMIN_AP_TIME").toString());
				bean.setDzeroApSumtime((null != rs.getString("DZERO_AP_SUMTIME"))? rs.getInt("DZERO_AP_SUMTIME") : null);
				bean.setDaySume((null != rs.getString("DAY_SUME"))? rs.getDouble("DAY_SUME") : null);
				bean.setDaySume1((null != rs.getString("DAY_SUME1"))? rs.getDouble("DAY_SUME1") : null);
				bean.setDaySume2((null != rs.getString("DAY_SUME2"))? rs.getDouble("DAY_SUME2") : null);
				bean.setDaySume3((null != rs.getString("DAY_SUME3"))? rs.getDouble("DAY_SUME3") : null);
				bean.setDaySume4((null != rs.getString("DAY_SUME4"))? rs.getDouble("DAY_SUME4") : null);
				bean.setDaySume5((null != rs.getString("DAY_SUME5"))? rs.getDouble("DAY_SUME5") : null);
				bean.setDaySume6((null != rs.getString("DAY_SUME6"))? rs.getDouble("DAY_SUME6") : null);
				bean.setDaySume7((null != rs.getString("DAY_SUME7"))? rs.getDouble("DAY_SUME7") : null);
				bean.setDaySume8((null != rs.getString("DAY_SUME8"))? rs.getDouble("DAY_SUME8") : null);
				bean.setDaySume9((null != rs.getString("DAY_SUME9"))? rs.getDouble("DAY_SUME9") : null);
				bean.setDaySume10((null != rs.getString("DAY_SUME10"))? rs.getDouble("DAY_SUME10") : null);
				bean.setDaySume11((null != rs.getString("DAY_SUME11"))? rs.getDouble("DAY_SUME11") : null);
				bean.setDaySume12((null != rs.getString("DAY_SUME12"))? rs.getDouble("DAY_SUME12") : null);
				bean.setDaySume13((null != rs.getString("DAY_SUME13"))? rs.getDouble("DAY_SUME13") : null);
				bean.setDaySumre((null != rs.getString("DAY_SUMRE"))? rs.getDouble("DAY_SUMRE") : null);
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTotalMonStatMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETotalMonStatBean bean = new ETotalMonStatBean();
			try {
				bean.setMmaxAp(rs.getDouble("MMAX_AP"));
				bean.setMmaxApTime(timeStampToStr(rs.getTimestamp("MMAX_AP_TIME")));
				bean.setMminAp(rs.getDouble("MMIN_AP"));
				bean.setMminApTime(timeStampToStr(rs.getTimestamp("MMIN_AP_TIME")));
				bean.setMzeroApSumtime(rs.getInt("MZERO_AP_SUMTIME"));
				bean.setMonSume(rs.getDouble("MON_SUME"));
				bean.setMonSumre(rs.getDouble("MON_SUMRE"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTotalMonStatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			ETotalMonStatBean bean = new ETotalMonStatBean();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				bean.setMmaxAp((null != rs.getString("MMAX_AP"))? rs.getDouble("MMAX_AP") : null);
				bean.setMmaxApTime(timeStampToStr(rs.getTimestamp("MMAX_AP_TIME")));
				bean.setMminAp((null != rs.getString("MMIN_AP"))? rs.getDouble("MMIN_AP") : null);
				bean.setMminApTime(timeStampToStr(rs.getTimestamp("MMIN_AP_TIME")));
				bean.setMzeroApSumtime((null != rs.getString("MZERO_AP_SUMTIME"))? rs.getInt("MZERO_AP_SUMTIME") : null);
				bean.setMonSume((null != rs.getString("MON_SUME"))? rs.getDouble("MON_SUME") : null);
				bean.setMonSume1((null != rs.getString("MON_SUME1"))? rs.getDouble("MON_SUME1") : null);
				bean.setMonSume2((null != rs.getString("MON_SUME2"))? rs.getDouble("MON_SUME2") : null);
				bean.setMonSume3((null != rs.getString("MON_SUME3"))? rs.getDouble("MON_SUME3") : null);
				bean.setMonSume4((null != rs.getString("MON_SUME4"))? rs.getDouble("MON_SUME4") : null);
				bean.setMonSume5((null != rs.getString("MON_SUME5"))? rs.getDouble("MON_SUME5") : null);
				bean.setMonSume6((null != rs.getString("MON_SUME6"))? rs.getDouble("MON_SUME6") : null);
				bean.setMonSume7((null != rs.getString("MON_SUME7"))? rs.getDouble("MON_SUME7") : null);
				bean.setMonSume8((null != rs.getString("MON_SUME8"))? rs.getDouble("MON_SUME8") : null);
				bean.setMonSume9((null != rs.getString("MON_SUME9"))? rs.getDouble("MON_SUME9") : null);
				bean.setMonSume10((null != rs.getString("MON_SUME10"))? rs.getDouble("MON_SUME10") : null);
				bean.setMonSume11((null != rs.getString("MON_SUME11"))? rs.getDouble("MON_SUME11") : null);
				bean.setMonSume12((null != rs.getString("MON_SUME12"))? rs.getDouble("MON_SUME12") : null);
				bean.setMonSume13((null != rs.getString("MON_SUME13"))? rs.getDouble("MON_SUME13") : null);
				bean.setMonSumre((null != rs.getString("MON_SUMRE"))? rs.getDouble("MON_SUMRE") : null);
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eTotalPowerCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ETotalPowerCurveBean etotalpowercurvebean = new ETotalPowerCurveBean();
			try {
				etotalpowercurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				etotalpowercurvebean.setP1(rs.getString("P1"));
				etotalpowercurvebean.setP2(rs.getString("P2"));
				etotalpowercurvebean.setP3(rs.getString("P3"));
				etotalpowercurvebean.setP4(rs.getString("P4"));
				etotalpowercurvebean.setP5(rs.getString("P5"));
				etotalpowercurvebean.setP6(rs.getString("P6"));
				etotalpowercurvebean.setP7(rs.getString("P7"));
				etotalpowercurvebean.setP8(rs.getString("P8"));
				etotalpowercurvebean.setP9(rs.getString("P9"));
				etotalpowercurvebean.setP10(rs.getString("P10"));
				etotalpowercurvebean.setP11(rs.getString("P11"));
				etotalpowercurvebean.setP12(rs.getString("P12"));
				etotalpowercurvebean.setP13(rs.getString("P13"));
				etotalpowercurvebean.setP14(rs.getString("P14"));
				etotalpowercurvebean.setP15(rs.getString("P15"));
				etotalpowercurvebean.setP16(rs.getString("P16"));
				etotalpowercurvebean.setP17(rs.getString("P17"));
				etotalpowercurvebean.setP18(rs.getString("P18"));
				etotalpowercurvebean.setP19(rs.getString("P19"));
				etotalpowercurvebean.setP20(rs.getString("P20"));
				etotalpowercurvebean.setP21(rs.getString("P21"));
				etotalpowercurvebean.setP22(rs.getString("P22"));
				etotalpowercurvebean.setP23(rs.getString("P23"));
				etotalpowercurvebean.setP24(rs.getString("P24"));
				etotalpowercurvebean.setP25(rs.getString("P25"));
				etotalpowercurvebean.setP26(rs.getString("P26"));
				etotalpowercurvebean.setP27(rs.getString("P27"));
				etotalpowercurvebean.setP28(rs.getString("P28"));
				etotalpowercurvebean.setP29(rs.getString("P29"));
				etotalpowercurvebean.setP30(rs.getString("P30"));
				etotalpowercurvebean.setP31(rs.getString("P31"));
				etotalpowercurvebean.setP32(rs.getString("P32"));
				etotalpowercurvebean.setP33(rs.getString("P33"));
				etotalpowercurvebean.setP34(rs.getString("P34"));
				etotalpowercurvebean.setP35(rs.getString("P35"));
				etotalpowercurvebean.setP36(rs.getString("P36"));
				etotalpowercurvebean.setP37(rs.getString("P37"));
				etotalpowercurvebean.setP38(rs.getString("P38"));
				etotalpowercurvebean.setP39(rs.getString("P39"));
				etotalpowercurvebean.setP40(rs.getString("P40"));
				etotalpowercurvebean.setP41(rs.getString("P41"));
				etotalpowercurvebean.setP42(rs.getString("P42"));
				etotalpowercurvebean.setP43(rs.getString("P43"));
				etotalpowercurvebean.setP44(rs.getString("P44"));
				etotalpowercurvebean.setP45(rs.getString("P45"));
				etotalpowercurvebean.setP46(rs.getString("P46"));
				etotalpowercurvebean.setP47(rs.getString("P47"));
				etotalpowercurvebean.setP48(rs.getString("P48"));
				etotalpowercurvebean.setP49(rs.getString("P49"));
				etotalpowercurvebean.setP50(rs.getString("P50"));
				etotalpowercurvebean.setP51(rs.getString("P51"));
				etotalpowercurvebean.setP52(rs.getString("P52"));
				etotalpowercurvebean.setP53(rs.getString("P53"));
				etotalpowercurvebean.setP54(rs.getString("P54"));
				etotalpowercurvebean.setP55(rs.getString("P55"));
				etotalpowercurvebean.setP56(rs.getString("P56"));
				etotalpowercurvebean.setP57(rs.getString("P57"));
				etotalpowercurvebean.setP58(rs.getString("P58"));
				etotalpowercurvebean.setP59(rs.getString("P59"));
				etotalpowercurvebean.setP60(rs.getString("P60"));
				etotalpowercurvebean.setP61(rs.getString("P61"));
				etotalpowercurvebean.setP62(rs.getString("P62"));
				etotalpowercurvebean.setP63(rs.getString("P63"));
				etotalpowercurvebean.setP64(rs.getString("P64"));
				etotalpowercurvebean.setP65(rs.getString("P65"));
				etotalpowercurvebean.setP66(rs.getString("P66"));
				etotalpowercurvebean.setP67(rs.getString("P67"));
				etotalpowercurvebean.setP68(rs.getString("P68"));
				etotalpowercurvebean.setP69(rs.getString("P69"));
				etotalpowercurvebean.setP70(rs.getString("P70"));
				etotalpowercurvebean.setP71(rs.getString("P71"));
				etotalpowercurvebean.setP72(rs.getString("P72"));
				etotalpowercurvebean.setP73(rs.getString("P73"));
				etotalpowercurvebean.setP74(rs.getString("P74"));
				etotalpowercurvebean.setP75(rs.getString("P75"));
				etotalpowercurvebean.setP76(rs.getString("P76"));
				etotalpowercurvebean.setP77(rs.getString("P77"));
				etotalpowercurvebean.setP78(rs.getString("P78"));
				etotalpowercurvebean.setP79(rs.getString("P79"));
				etotalpowercurvebean.setP80(rs.getString("P80"));
				etotalpowercurvebean.setP81(rs.getString("P81"));
				etotalpowercurvebean.setP82(rs.getString("P82"));
				etotalpowercurvebean.setP83(rs.getString("P83"));
				etotalpowercurvebean.setP84(rs.getString("P84"));
				etotalpowercurvebean.setP85(rs.getString("P85"));
				etotalpowercurvebean.setP86(rs.getString("P86"));
				etotalpowercurvebean.setP87(rs.getString("P87"));
				etotalpowercurvebean.setP88(rs.getString("P88"));
				etotalpowercurvebean.setP89(rs.getString("P89"));
				etotalpowercurvebean.setP90(rs.getString("P90"));
				etotalpowercurvebean.setP91(rs.getString("P91"));
				etotalpowercurvebean.setP92(rs.getString("P92"));
				etotalpowercurvebean.setP93(rs.getString("P93"));
				etotalpowercurvebean.setP94(rs.getString("P94"));
				etotalpowercurvebean.setP95(rs.getString("P95"));
				etotalpowercurvebean.setP96(rs.getString("P96"));
				return etotalpowercurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eTotalEnergyCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ETotalEnergyCurveBean etotalenergycurvebean = new ETotalEnergyCurveBean();
			try {
				etotalenergycurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				etotalenergycurvebean.setR1(rs.getDouble("R1"));
				etotalenergycurvebean.setR2(rs.getDouble("R2"));
				etotalenergycurvebean.setR3(rs.getDouble("R3"));
				etotalenergycurvebean.setR4(rs.getDouble("R4"));
				etotalenergycurvebean.setR5(rs.getDouble("R5"));
				etotalenergycurvebean.setR6(rs.getDouble("R6"));
				etotalenergycurvebean.setR7(rs.getDouble("R7"));
				etotalenergycurvebean.setR8(rs.getDouble("R8"));
				etotalenergycurvebean.setR9(rs.getDouble("R9"));
				etotalenergycurvebean.setR10(rs.getDouble("R10"));
				etotalenergycurvebean.setR11(rs.getDouble("R11"));
				etotalenergycurvebean.setR12(rs.getDouble("R12"));
				etotalenergycurvebean.setR13(rs.getDouble("R13"));
				etotalenergycurvebean.setR14(rs.getDouble("R14"));
				etotalenergycurvebean.setR15(rs.getDouble("R15"));
				etotalenergycurvebean.setR16(rs.getDouble("R16"));
				etotalenergycurvebean.setR17(rs.getDouble("R17"));
				etotalenergycurvebean.setR18(rs.getDouble("R18"));
				etotalenergycurvebean.setR19(rs.getDouble("R19"));
				etotalenergycurvebean.setR20(rs.getDouble("R20"));
				etotalenergycurvebean.setR21(rs.getDouble("R21"));
				etotalenergycurvebean.setR22(rs.getDouble("R22"));
				etotalenergycurvebean.setR23(rs.getDouble("R23"));
				etotalenergycurvebean.setR24(rs.getDouble("R24"));
				etotalenergycurvebean.setR25(rs.getDouble("R25"));
				etotalenergycurvebean.setR26(rs.getDouble("R26"));
				etotalenergycurvebean.setR27(rs.getDouble("R27"));
				etotalenergycurvebean.setR28(rs.getDouble("R28"));
				etotalenergycurvebean.setR29(rs.getDouble("R29"));
				etotalenergycurvebean.setR30(rs.getDouble("R30"));
				etotalenergycurvebean.setR31(rs.getDouble("R31"));
				etotalenergycurvebean.setR32(rs.getDouble("R32"));
				etotalenergycurvebean.setR33(rs.getDouble("R33"));
				etotalenergycurvebean.setR34(rs.getDouble("R34"));
				etotalenergycurvebean.setR35(rs.getDouble("R35"));
				etotalenergycurvebean.setR36(rs.getDouble("R36"));
				etotalenergycurvebean.setR37(rs.getDouble("R37"));
				etotalenergycurvebean.setR38(rs.getDouble("R38"));
				etotalenergycurvebean.setR39(rs.getDouble("R39"));
				etotalenergycurvebean.setR40(rs.getDouble("R40"));
				etotalenergycurvebean.setR41(rs.getDouble("R41"));
				etotalenergycurvebean.setR42(rs.getDouble("R42"));
				etotalenergycurvebean.setR43(rs.getDouble("R43"));
				etotalenergycurvebean.setR44(rs.getDouble("R44"));
				etotalenergycurvebean.setR45(rs.getDouble("R45"));
				etotalenergycurvebean.setR46(rs.getDouble("R46"));
				etotalenergycurvebean.setR47(rs.getDouble("R47"));
				etotalenergycurvebean.setR48(rs.getDouble("R48"));
				etotalenergycurvebean.setR49(rs.getDouble("R49"));
				etotalenergycurvebean.setR50(rs.getDouble("R50"));
				etotalenergycurvebean.setR51(rs.getDouble("R51"));
				etotalenergycurvebean.setR52(rs.getDouble("R52"));
				etotalenergycurvebean.setR53(rs.getDouble("R53"));
				etotalenergycurvebean.setR54(rs.getDouble("R54"));
				etotalenergycurvebean.setR55(rs.getDouble("R55"));
				etotalenergycurvebean.setR56(rs.getDouble("R56"));
				etotalenergycurvebean.setR57(rs.getDouble("R57"));
				etotalenergycurvebean.setR58(rs.getDouble("R58"));
				etotalenergycurvebean.setR59(rs.getDouble("R59"));
				etotalenergycurvebean.setR60(rs.getDouble("R60"));
				etotalenergycurvebean.setR61(rs.getDouble("R61"));
				etotalenergycurvebean.setR62(rs.getDouble("R62"));
				etotalenergycurvebean.setR63(rs.getDouble("R63"));
				etotalenergycurvebean.setR64(rs.getDouble("R64"));
				etotalenergycurvebean.setR65(rs.getDouble("R65"));
				etotalenergycurvebean.setR66(rs.getDouble("R66"));
				etotalenergycurvebean.setR67(rs.getDouble("R67"));
				etotalenergycurvebean.setR68(rs.getDouble("R68"));
				etotalenergycurvebean.setR69(rs.getDouble("R69"));
				etotalenergycurvebean.setR70(rs.getDouble("R70"));
				etotalenergycurvebean.setR71(rs.getDouble("R71"));
				etotalenergycurvebean.setR72(rs.getDouble("R72"));
				etotalenergycurvebean.setR73(rs.getDouble("R73"));
				etotalenergycurvebean.setR74(rs.getDouble("R74"));
				etotalenergycurvebean.setR75(rs.getDouble("R75"));
				etotalenergycurvebean.setR76(rs.getDouble("R76"));
				etotalenergycurvebean.setR77(rs.getDouble("R77"));
				etotalenergycurvebean.setR78(rs.getDouble("R78"));
				etotalenergycurvebean.setR79(rs.getDouble("R79"));
				etotalenergycurvebean.setR80(rs.getDouble("R80"));
				etotalenergycurvebean.setR81(rs.getDouble("R81"));
				etotalenergycurvebean.setR82(rs.getDouble("R82"));
				etotalenergycurvebean.setR83(rs.getDouble("R83"));
				etotalenergycurvebean.setR84(rs.getDouble("R84"));
				etotalenergycurvebean.setR85(rs.getDouble("R85"));
				etotalenergycurvebean.setR86(rs.getDouble("R86"));
				etotalenergycurvebean.setR87(rs.getDouble("R87"));
				etotalenergycurvebean.setR88(rs.getDouble("R88"));
				etotalenergycurvebean.setR89(rs.getDouble("R89"));
				etotalenergycurvebean.setR90(rs.getDouble("R90"));
				etotalenergycurvebean.setR91(rs.getDouble("R91"));
				etotalenergycurvebean.setR92(rs.getDouble("R92"));
				etotalenergycurvebean.setR93(rs.getDouble("R93"));
				etotalenergycurvebean.setR94(rs.getDouble("R94"));
				etotalenergycurvebean.setR95(rs.getDouble("R95"));
				etotalenergycurvebean.setR96(rs.getDouble("R96"));	
				return etotalenergycurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpPowerCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpPowerCurveBean emppowercurvebean = new EMpPowerCurveBean();
			try {
				emppowercurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				emppowercurvebean.setP1(rs.getString("P1"));
				emppowercurvebean.setP2(rs.getString("P2"));
				emppowercurvebean.setP3(rs.getString("P3"));
				emppowercurvebean.setP4(rs.getString("P4"));
				emppowercurvebean.setP5(rs.getString("P5"));
				emppowercurvebean.setP6(rs.getString("P6"));
				emppowercurvebean.setP7(rs.getString("P7"));
				emppowercurvebean.setP8(rs.getString("P8"));
				emppowercurvebean.setP9(rs.getString("P9"));
				emppowercurvebean.setP10(rs.getString("P10"));
				emppowercurvebean.setP11(rs.getString("P11"));
				emppowercurvebean.setP12(rs.getString("P12"));
				emppowercurvebean.setP13(rs.getString("P13"));
				emppowercurvebean.setP14(rs.getString("P14"));
				emppowercurvebean.setP15(rs.getString("P15"));
				emppowercurvebean.setP16(rs.getString("P16"));
				emppowercurvebean.setP17(rs.getString("P17"));
				emppowercurvebean.setP18(rs.getString("P18"));
				emppowercurvebean.setP19(rs.getString("P19"));
				emppowercurvebean.setP20(rs.getString("P20"));
				emppowercurvebean.setP21(rs.getString("P21"));
				emppowercurvebean.setP22(rs.getString("P22"));
				emppowercurvebean.setP23(rs.getString("P23"));
				emppowercurvebean.setP24(rs.getString("P24"));
				emppowercurvebean.setP25(rs.getString("P25"));
				emppowercurvebean.setP26(rs.getString("P26"));
				emppowercurvebean.setP27(rs.getString("P27"));
				emppowercurvebean.setP28(rs.getString("P28"));
				emppowercurvebean.setP29(rs.getString("P29"));
				emppowercurvebean.setP30(rs.getString("P30"));
				emppowercurvebean.setP31(rs.getString("P31"));
				emppowercurvebean.setP32(rs.getString("P32"));
				emppowercurvebean.setP33(rs.getString("P33"));
				emppowercurvebean.setP34(rs.getString("P34"));
				emppowercurvebean.setP35(rs.getString("P35"));
				emppowercurvebean.setP36(rs.getString("P36"));
				emppowercurvebean.setP37(rs.getString("P37"));
				emppowercurvebean.setP38(rs.getString("P38"));
				emppowercurvebean.setP39(rs.getString("P39"));
				emppowercurvebean.setP40(rs.getString("P40"));
				emppowercurvebean.setP41(rs.getString("P41"));
				emppowercurvebean.setP42(rs.getString("P42"));
				emppowercurvebean.setP43(rs.getString("P43"));
				emppowercurvebean.setP44(rs.getString("P44"));
				emppowercurvebean.setP45(rs.getString("P45"));
				emppowercurvebean.setP46(rs.getString("P46"));
				emppowercurvebean.setP47(rs.getString("P47"));
				emppowercurvebean.setP48(rs.getString("P48"));
				emppowercurvebean.setP49(rs.getString("P49"));
				emppowercurvebean.setP50(rs.getString("P50"));
				emppowercurvebean.setP51(rs.getString("P51"));
				emppowercurvebean.setP52(rs.getString("P52"));
				emppowercurvebean.setP53(rs.getString("P53"));
				emppowercurvebean.setP54(rs.getString("P54"));
				emppowercurvebean.setP55(rs.getString("P55"));
				emppowercurvebean.setP56(rs.getString("P56"));
				emppowercurvebean.setP57(rs.getString("P57"));
				emppowercurvebean.setP58(rs.getString("P58"));
				emppowercurvebean.setP59(rs.getString("P59"));
				emppowercurvebean.setP60(rs.getString("P60"));
				emppowercurvebean.setP61(rs.getString("P61"));
				emppowercurvebean.setP62(rs.getString("P62"));
				emppowercurvebean.setP63(rs.getString("P63"));
				emppowercurvebean.setP64(rs.getString("P64"));
				emppowercurvebean.setP65(rs.getString("P65"));
				emppowercurvebean.setP66(rs.getString("P66"));
				emppowercurvebean.setP67(rs.getString("P67"));
				emppowercurvebean.setP68(rs.getString("P68"));
				emppowercurvebean.setP69(rs.getString("P69"));
				emppowercurvebean.setP70(rs.getString("P70"));
				emppowercurvebean.setP71(rs.getString("P71"));
				emppowercurvebean.setP72(rs.getString("P72"));
				emppowercurvebean.setP73(rs.getString("P73"));
				emppowercurvebean.setP74(rs.getString("P74"));
				emppowercurvebean.setP75(rs.getString("P75"));
				emppowercurvebean.setP76(rs.getString("P76"));
				emppowercurvebean.setP77(rs.getString("P77"));
				emppowercurvebean.setP78(rs.getString("P78"));
				emppowercurvebean.setP79(rs.getString("P79"));
				emppowercurvebean.setP80(rs.getString("P80"));
				emppowercurvebean.setP81(rs.getString("P81"));
				emppowercurvebean.setP82(rs.getString("P82"));
				emppowercurvebean.setP83(rs.getString("P83"));
				emppowercurvebean.setP84(rs.getString("P84"));
				emppowercurvebean.setP85(rs.getString("P85"));
				emppowercurvebean.setP86(rs.getString("P86"));
				emppowercurvebean.setP87(rs.getString("P87"));
				emppowercurvebean.setP88(rs.getString("P88"));
				emppowercurvebean.setP89(rs.getString("P89"));
				emppowercurvebean.setP90(rs.getString("P90"));
				emppowercurvebean.setP91(rs.getString("P91"));
				emppowercurvebean.setP92(rs.getString("P92"));
				emppowercurvebean.setP93(rs.getString("P93"));
				emppowercurvebean.setP94(rs.getString("P94"));
				emppowercurvebean.setP95(rs.getString("P95"));
				emppowercurvebean.setP96(rs.getString("P96"));				
				return emppowercurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpEnergyCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpEnergyCurveBean empenergycurvebean = new EMpEnergyCurveBean();
			try {
				empenergycurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empenergycurvebean.setE1(rs.getString("E1"));
				empenergycurvebean.setE2(rs.getString("E2"));
				empenergycurvebean.setE3(rs.getString("E3"));
				empenergycurvebean.setE4(rs.getString("E4"));
				empenergycurvebean.setE5(rs.getString("E5"));
				empenergycurvebean.setE6(rs.getString("E6"));
				empenergycurvebean.setE7(rs.getString("E7"));
				empenergycurvebean.setE8(rs.getString("E8"));
				empenergycurvebean.setE9(rs.getString("E9"));
				empenergycurvebean.setE10(rs.getString("E10"));
				empenergycurvebean.setE11(rs.getString("E11"));
				empenergycurvebean.setE12(rs.getString("E12"));
				empenergycurvebean.setE13(rs.getString("E13"));
				empenergycurvebean.setE14(rs.getString("E14"));
				empenergycurvebean.setE15(rs.getString("E15"));
				empenergycurvebean.setE16(rs.getString("E16"));
				empenergycurvebean.setE17(rs.getString("E17"));
				empenergycurvebean.setE18(rs.getString("E18"));
				empenergycurvebean.setE19(rs.getString("E19"));
				empenergycurvebean.setE20(rs.getString("E20"));
				empenergycurvebean.setE21(rs.getString("E21"));
				empenergycurvebean.setE22(rs.getString("E22"));
				empenergycurvebean.setE23(rs.getString("E23"));
				empenergycurvebean.setE24(rs.getString("E24"));
				empenergycurvebean.setE25(rs.getString("E25"));
				empenergycurvebean.setE26(rs.getString("E26"));
				empenergycurvebean.setE27(rs.getString("E27"));
				empenergycurvebean.setE28(rs.getString("E28"));
				empenergycurvebean.setE29(rs.getString("E29"));
				empenergycurvebean.setE30(rs.getString("E30"));
				empenergycurvebean.setE31(rs.getString("E31"));
				empenergycurvebean.setE32(rs.getString("E32"));
				empenergycurvebean.setE33(rs.getString("E33"));
				empenergycurvebean.setE34(rs.getString("E34"));
				empenergycurvebean.setE35(rs.getString("E35"));
				empenergycurvebean.setE36(rs.getString("E36"));
				empenergycurvebean.setE37(rs.getString("E37"));
				empenergycurvebean.setE38(rs.getString("E38"));
				empenergycurvebean.setE39(rs.getString("E39"));
				empenergycurvebean.setE40(rs.getString("E40"));
				empenergycurvebean.setE41(rs.getString("E41"));
				empenergycurvebean.setE42(rs.getString("E42"));
				empenergycurvebean.setE43(rs.getString("E43"));
				empenergycurvebean.setE44(rs.getString("E44"));
				empenergycurvebean.setE45(rs.getString("E45"));
				empenergycurvebean.setE46(rs.getString("E46"));
				empenergycurvebean.setE47(rs.getString("E47"));
				empenergycurvebean.setE48(rs.getString("E48"));
				empenergycurvebean.setE49(rs.getString("E49"));
				empenergycurvebean.setE50(rs.getString("E50"));
				empenergycurvebean.setE51(rs.getString("E51"));
				empenergycurvebean.setE52(rs.getString("E52"));
				empenergycurvebean.setE53(rs.getString("E53"));
				empenergycurvebean.setE54(rs.getString("E54"));
				empenergycurvebean.setE55(rs.getString("E55"));
				empenergycurvebean.setE56(rs.getString("E56"));
				empenergycurvebean.setE57(rs.getString("E57"));
				empenergycurvebean.setE58(rs.getString("E58"));
				empenergycurvebean.setE59(rs.getString("E59"));
				empenergycurvebean.setE60(rs.getString("E60"));
				empenergycurvebean.setE61(rs.getString("E61"));
				empenergycurvebean.setE62(rs.getString("E62"));
				empenergycurvebean.setE63(rs.getString("E63"));
				empenergycurvebean.setE64(rs.getString("E64"));
				empenergycurvebean.setE65(rs.getString("E65"));
				empenergycurvebean.setE66(rs.getString("E66"));
				empenergycurvebean.setE67(rs.getString("E67"));
				empenergycurvebean.setE68(rs.getString("E68"));
				empenergycurvebean.setE69(rs.getString("E69"));
				empenergycurvebean.setE70(rs.getString("E70"));
				empenergycurvebean.setE71(rs.getString("E71"));
				empenergycurvebean.setE72(rs.getString("E72"));
				empenergycurvebean.setE73(rs.getString("E73"));
				empenergycurvebean.setE74(rs.getString("E74"));
				empenergycurvebean.setE75(rs.getString("E75"));
				empenergycurvebean.setE76(rs.getString("E76"));
				empenergycurvebean.setE77(rs.getString("E77"));
				empenergycurvebean.setE78(rs.getString("E78"));
				empenergycurvebean.setE79(rs.getString("E79"));
				empenergycurvebean.setE80(rs.getString("E80"));
				empenergycurvebean.setE81(rs.getString("E81"));
				empenergycurvebean.setE82(rs.getString("E82"));
				empenergycurvebean.setE83(rs.getString("E83"));
				empenergycurvebean.setE84(rs.getString("E84"));
				empenergycurvebean.setE85(rs.getString("E85"));
				empenergycurvebean.setE86(rs.getString("E86"));
				empenergycurvebean.setE87(rs.getString("E87"));
				empenergycurvebean.setE88(rs.getString("E88"));
				empenergycurvebean.setE89(rs.getString("E89"));
				empenergycurvebean.setE90(rs.getString("E90"));
				empenergycurvebean.setE91(rs.getString("E91"));
				empenergycurvebean.setE92(rs.getString("E92"));
				empenergycurvebean.setE93(rs.getString("E93"));
				empenergycurvebean.setE94(rs.getString("E94"));
				empenergycurvebean.setE95(rs.getString("E95"));
				empenergycurvebean.setE96(rs.getString("E96"));				
				return empenergycurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpCurCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpCurCurveBean empcurcurvebean = new EMpCurCurveBean();
			try {
				empcurcurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empcurcurvebean.setI1(rs.getString("I1"));
				empcurcurvebean.setI2(rs.getString("I2"));
				empcurcurvebean.setI3(rs.getString("I3"));
				empcurcurvebean.setI4(rs.getString("I4"));
				empcurcurvebean.setI5(rs.getString("I5"));
				empcurcurvebean.setI6(rs.getString("I6"));
				empcurcurvebean.setI7(rs.getString("I7"));
				empcurcurvebean.setI8(rs.getString("I8"));
				empcurcurvebean.setI9(rs.getString("I9"));
				empcurcurvebean.setI10(rs.getString("I10"));
				empcurcurvebean.setI11(rs.getString("I11"));
				empcurcurvebean.setI12(rs.getString("I12"));
				empcurcurvebean.setI13(rs.getString("I13"));
				empcurcurvebean.setI14(rs.getString("I14"));
				empcurcurvebean.setI15(rs.getString("I15"));
				empcurcurvebean.setI16(rs.getString("I16"));
				empcurcurvebean.setI17(rs.getString("I17"));
				empcurcurvebean.setI18(rs.getString("I18"));
				empcurcurvebean.setI19(rs.getString("I19"));
				empcurcurvebean.setI20(rs.getString("I20"));
				empcurcurvebean.setI21(rs.getString("I21"));
				empcurcurvebean.setI22(rs.getString("I22"));
				empcurcurvebean.setI23(rs.getString("I23"));
				empcurcurvebean.setI24(rs.getString("I24"));
				empcurcurvebean.setI25(rs.getString("I25"));
				empcurcurvebean.setI26(rs.getString("I26"));
				empcurcurvebean.setI27(rs.getString("I27"));
				empcurcurvebean.setI28(rs.getString("I28"));
				empcurcurvebean.setI29(rs.getString("I29"));
				empcurcurvebean.setI30(rs.getString("I30"));
				empcurcurvebean.setI31(rs.getString("I31"));
				empcurcurvebean.setI32(rs.getString("I32"));
				empcurcurvebean.setI33(rs.getString("I33"));
				empcurcurvebean.setI34(rs.getString("I34"));
				empcurcurvebean.setI35(rs.getString("I35"));
				empcurcurvebean.setI36(rs.getString("I36"));
				empcurcurvebean.setI37(rs.getString("I37"));
				empcurcurvebean.setI38(rs.getString("I38"));
				empcurcurvebean.setI39(rs.getString("I39"));
				empcurcurvebean.setI40(rs.getString("I40"));
				empcurcurvebean.setI41(rs.getString("I41"));
				empcurcurvebean.setI42(rs.getString("I42"));
				empcurcurvebean.setI43(rs.getString("I43"));
				empcurcurvebean.setI44(rs.getString("I44"));
				empcurcurvebean.setI45(rs.getString("I45"));
				empcurcurvebean.setI46(rs.getString("I46"));
				empcurcurvebean.setI47(rs.getString("I47"));
				empcurcurvebean.setI48(rs.getString("I48"));
				empcurcurvebean.setI49(rs.getString("I49"));
				empcurcurvebean.setI50(rs.getString("I50"));
				empcurcurvebean.setI51(rs.getString("I51"));
				empcurcurvebean.setI52(rs.getString("I52"));
				empcurcurvebean.setI53(rs.getString("I53"));
				empcurcurvebean.setI54(rs.getString("I54"));
				empcurcurvebean.setI55(rs.getString("I55"));
				empcurcurvebean.setI56(rs.getString("I56"));
				empcurcurvebean.setI57(rs.getString("I57"));
				empcurcurvebean.setI58(rs.getString("I58"));
				empcurcurvebean.setI59(rs.getString("I59"));
				empcurcurvebean.setI60(rs.getString("I60"));
				empcurcurvebean.setI61(rs.getString("I61"));
				empcurcurvebean.setI62(rs.getString("I62"));
				empcurcurvebean.setI63(rs.getString("I63"));
				empcurcurvebean.setI64(rs.getString("I64"));
				empcurcurvebean.setI65(rs.getString("I65"));
				empcurcurvebean.setI66(rs.getString("I66"));
				empcurcurvebean.setI67(rs.getString("I67"));
				empcurcurvebean.setI68(rs.getString("I68"));
				empcurcurvebean.setI69(rs.getString("I69"));
				empcurcurvebean.setI70(rs.getString("I70"));
				empcurcurvebean.setI71(rs.getString("I71"));
				empcurcurvebean.setI72(rs.getString("I72"));
				empcurcurvebean.setI73(rs.getString("I73"));
				empcurcurvebean.setI74(rs.getString("I74"));
				empcurcurvebean.setI75(rs.getString("I75"));
				empcurcurvebean.setI76(rs.getString("I76"));
				empcurcurvebean.setI77(rs.getString("I77"));
				empcurcurvebean.setI78(rs.getString("I78"));
				empcurcurvebean.setI79(rs.getString("I79"));
				empcurcurvebean.setI80(rs.getString("I80"));
				empcurcurvebean.setI81(rs.getString("I81"));
				empcurcurvebean.setI82(rs.getString("I82"));
				empcurcurvebean.setI83(rs.getString("I83"));
				empcurcurvebean.setI84(rs.getString("I84"));
				empcurcurvebean.setI85(rs.getString("I85"));
				empcurcurvebean.setI86(rs.getString("I86"));
				empcurcurvebean.setI87(rs.getString("I87"));
				empcurcurvebean.setI88(rs.getString("I88"));
				empcurcurvebean.setI89(rs.getString("I89"));
				empcurcurvebean.setI90(rs.getString("I90"));
				empcurcurvebean.setI91(rs.getString("I91"));
				empcurcurvebean.setI92(rs.getString("I92"));
				empcurcurvebean.setI93(rs.getString("I93"));
				empcurcurvebean.setI94(rs.getString("I94"));
				empcurcurvebean.setI95(rs.getString("I95"));
				empcurcurvebean.setI96(rs.getString("I96"));	
				return empcurcurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpVolCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpVolCurveBean empvolcurvebean = new EMpVolCurveBean();
			try {
				empvolcurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empvolcurvebean.setU1(rs.getString("U1"));
				empvolcurvebean.setU2(rs.getString("U2"));
				empvolcurvebean.setU3(rs.getString("U3"));
				empvolcurvebean.setU4(rs.getString("U4"));
				empvolcurvebean.setU5(rs.getString("U5"));
				empvolcurvebean.setU6(rs.getString("U6"));
				empvolcurvebean.setU7(rs.getString("U7"));
				empvolcurvebean.setU8(rs.getString("U8"));
				empvolcurvebean.setU9(rs.getString("U9"));
				empvolcurvebean.setU10(rs.getString("U10"));
				empvolcurvebean.setU11(rs.getString("U11"));
				empvolcurvebean.setU12(rs.getString("U12"));
				empvolcurvebean.setU13(rs.getString("U13"));
				empvolcurvebean.setU14(rs.getString("U14"));
				empvolcurvebean.setU15(rs.getString("U15"));
				empvolcurvebean.setU16(rs.getString("U16"));
				empvolcurvebean.setU17(rs.getString("U17"));
				empvolcurvebean.setU18(rs.getString("U18"));
				empvolcurvebean.setU19(rs.getString("U19"));
				empvolcurvebean.setU20(rs.getString("U20"));
				empvolcurvebean.setU21(rs.getString("U21"));
				empvolcurvebean.setU22(rs.getString("U22"));
				empvolcurvebean.setU23(rs.getString("U23"));
				empvolcurvebean.setU24(rs.getString("U24"));
				empvolcurvebean.setU25(rs.getString("U25"));
				empvolcurvebean.setU26(rs.getString("U26"));
				empvolcurvebean.setU27(rs.getString("U27"));
				empvolcurvebean.setU28(rs.getString("U28"));
				empvolcurvebean.setU29(rs.getString("U29"));
				empvolcurvebean.setU30(rs.getString("U30"));
				empvolcurvebean.setU31(rs.getString("U31"));
				empvolcurvebean.setU32(rs.getString("U32"));
				empvolcurvebean.setU33(rs.getString("U33"));
				empvolcurvebean.setU34(rs.getString("U34"));
				empvolcurvebean.setU35(rs.getString("U35"));
				empvolcurvebean.setU36(rs.getString("U36"));
				empvolcurvebean.setU37(rs.getString("U37"));
				empvolcurvebean.setU38(rs.getString("U38"));
				empvolcurvebean.setU39(rs.getString("U39"));
				empvolcurvebean.setU40(rs.getString("U40"));
				empvolcurvebean.setU41(rs.getString("U41"));
				empvolcurvebean.setU42(rs.getString("U42"));
				empvolcurvebean.setU43(rs.getString("U43"));
				empvolcurvebean.setU44(rs.getString("U44"));
				empvolcurvebean.setU45(rs.getString("U45"));
				empvolcurvebean.setU46(rs.getString("U46"));
				empvolcurvebean.setU47(rs.getString("U47"));
				empvolcurvebean.setU48(rs.getString("U48"));
				empvolcurvebean.setU49(rs.getString("U49"));
				empvolcurvebean.setU50(rs.getString("U50"));
				empvolcurvebean.setU51(rs.getString("U51"));
				empvolcurvebean.setU52(rs.getString("U52"));
				empvolcurvebean.setU53(rs.getString("U53"));
				empvolcurvebean.setU54(rs.getString("U54"));
				empvolcurvebean.setU55(rs.getString("U55"));
				empvolcurvebean.setU56(rs.getString("U56"));
				empvolcurvebean.setU57(rs.getString("U57"));
				empvolcurvebean.setU58(rs.getString("U58"));
				empvolcurvebean.setU59(rs.getString("U59"));
				empvolcurvebean.setU60(rs.getString("U60"));
				empvolcurvebean.setU61(rs.getString("U61"));
				empvolcurvebean.setU62(rs.getString("U62"));
				empvolcurvebean.setU63(rs.getString("U63"));
				empvolcurvebean.setU64(rs.getString("U64"));
				empvolcurvebean.setU65(rs.getString("U65"));
				empvolcurvebean.setU66(rs.getString("U66"));
				empvolcurvebean.setU67(rs.getString("U67"));
				empvolcurvebean.setU68(rs.getString("U68"));
				empvolcurvebean.setU69(rs.getString("U69"));
				empvolcurvebean.setU70(rs.getString("U70"));
				empvolcurvebean.setU71(rs.getString("U71"));
				empvolcurvebean.setU72(rs.getString("U72"));
				empvolcurvebean.setU73(rs.getString("U73"));
				empvolcurvebean.setU74(rs.getString("U74"));
				empvolcurvebean.setU75(rs.getString("U75"));
				empvolcurvebean.setU76(rs.getString("U76"));
				empvolcurvebean.setU77(rs.getString("U77"));
				empvolcurvebean.setU78(rs.getString("U78"));
				empvolcurvebean.setU79(rs.getString("U79"));
				empvolcurvebean.setU80(rs.getString("U80"));
				empvolcurvebean.setU81(rs.getString("U81"));
				empvolcurvebean.setU82(rs.getString("U82"));
				empvolcurvebean.setU83(rs.getString("U83"));
				empvolcurvebean.setU84(rs.getString("U84"));
				empvolcurvebean.setU85(rs.getString("U85"));
				empvolcurvebean.setU86(rs.getString("U86"));
				empvolcurvebean.setU87(rs.getString("U87"));
				empvolcurvebean.setU88(rs.getString("U88"));
				empvolcurvebean.setU89(rs.getString("U89"));
				empvolcurvebean.setU90(rs.getString("U90"));
				empvolcurvebean.setU91(rs.getString("U91"));
				empvolcurvebean.setU92(rs.getString("U92"));
				empvolcurvebean.setU93(rs.getString("U93"));
				empvolcurvebean.setU94(rs.getString("U94"));
				empvolcurvebean.setU95(rs.getString("U95"));
				empvolcurvebean.setU96(rs.getString("U96"));	
				return empvolcurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpFactorCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpFactorCurveBean empfactorcurvebean = new EMpFactorCurveBean();
			try {
				empfactorcurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empfactorcurvebean.setC1(rs.getString("C1"));
				empfactorcurvebean.setC2(rs.getString("C2"));
				empfactorcurvebean.setC3(rs.getString("C3"));
				empfactorcurvebean.setC4(rs.getString("C4"));
				empfactorcurvebean.setC5(rs.getString("C5"));
				empfactorcurvebean.setC6(rs.getString("C6"));
				empfactorcurvebean.setC7(rs.getString("C7"));
				empfactorcurvebean.setC8(rs.getString("C8"));
				empfactorcurvebean.setC9(rs.getString("C9"));
				empfactorcurvebean.setC10(rs.getString("C10"));
				empfactorcurvebean.setC11(rs.getString("C11"));
				empfactorcurvebean.setC12(rs.getString("C12"));
				empfactorcurvebean.setC13(rs.getString("C13"));
				empfactorcurvebean.setC14(rs.getString("C14"));
				empfactorcurvebean.setC15(rs.getString("C15"));
				empfactorcurvebean.setC16(rs.getString("C16"));
				empfactorcurvebean.setC17(rs.getString("C17"));
				empfactorcurvebean.setC18(rs.getString("C18"));
				empfactorcurvebean.setC19(rs.getString("C19"));
				empfactorcurvebean.setC20(rs.getString("C20"));
				empfactorcurvebean.setC21(rs.getString("C21"));
				empfactorcurvebean.setC22(rs.getString("C22"));
				empfactorcurvebean.setC23(rs.getString("C23"));
				empfactorcurvebean.setC24(rs.getString("C24"));
				empfactorcurvebean.setC25(rs.getString("C25"));
				empfactorcurvebean.setC26(rs.getString("C26"));
				empfactorcurvebean.setC27(rs.getString("C27"));
				empfactorcurvebean.setC28(rs.getString("C28"));
				empfactorcurvebean.setC29(rs.getString("C29"));
				empfactorcurvebean.setC30(rs.getString("C30"));
				empfactorcurvebean.setC31(rs.getString("C31"));
				empfactorcurvebean.setC32(rs.getString("C32"));
				empfactorcurvebean.setC33(rs.getString("C33"));
				empfactorcurvebean.setC34(rs.getString("C34"));
				empfactorcurvebean.setC35(rs.getString("C35"));
				empfactorcurvebean.setC36(rs.getString("C36"));
				empfactorcurvebean.setC37(rs.getString("C37"));
				empfactorcurvebean.setC38(rs.getString("C38"));
				empfactorcurvebean.setC39(rs.getString("C39"));
				empfactorcurvebean.setC40(rs.getString("C40"));
				empfactorcurvebean.setC41(rs.getString("C41"));
				empfactorcurvebean.setC42(rs.getString("C42"));
				empfactorcurvebean.setC43(rs.getString("C43"));
				empfactorcurvebean.setC44(rs.getString("C44"));
				empfactorcurvebean.setC45(rs.getString("C45"));
				empfactorcurvebean.setC46(rs.getString("C46"));
				empfactorcurvebean.setC47(rs.getString("C47"));
				empfactorcurvebean.setC48(rs.getString("C48"));
				empfactorcurvebean.setC49(rs.getString("C49"));
				empfactorcurvebean.setC50(rs.getString("C50"));
				empfactorcurvebean.setC51(rs.getString("C51"));
				empfactorcurvebean.setC52(rs.getString("C52"));
				empfactorcurvebean.setC53(rs.getString("C53"));
				empfactorcurvebean.setC54(rs.getString("C54"));
				empfactorcurvebean.setC55(rs.getString("C55"));
				empfactorcurvebean.setC56(rs.getString("C56"));
				empfactorcurvebean.setC57(rs.getString("C57"));
				empfactorcurvebean.setC58(rs.getString("C58"));
				empfactorcurvebean.setC59(rs.getString("C59"));
				empfactorcurvebean.setC60(rs.getString("C60"));
				empfactorcurvebean.setC61(rs.getString("C61"));
				empfactorcurvebean.setC62(rs.getString("C62"));
				empfactorcurvebean.setC63(rs.getString("C63"));
				empfactorcurvebean.setC64(rs.getString("C64"));
				empfactorcurvebean.setC65(rs.getString("C65"));
				empfactorcurvebean.setC66(rs.getString("C66"));
				empfactorcurvebean.setC67(rs.getString("C67"));
				empfactorcurvebean.setC68(rs.getString("C68"));
				empfactorcurvebean.setC69(rs.getString("C69"));
				empfactorcurvebean.setC70(rs.getString("C70"));
				empfactorcurvebean.setC71(rs.getString("C71"));
				empfactorcurvebean.setC72(rs.getString("C72"));
				empfactorcurvebean.setC73(rs.getString("C73"));
				empfactorcurvebean.setC74(rs.getString("C74"));
				empfactorcurvebean.setC75(rs.getString("C75"));
				empfactorcurvebean.setC76(rs.getString("C76"));
				empfactorcurvebean.setC77(rs.getString("C77"));
				empfactorcurvebean.setC78(rs.getString("C78"));
				empfactorcurvebean.setC79(rs.getString("C79"));
				empfactorcurvebean.setC80(rs.getString("C80"));
				empfactorcurvebean.setC81(rs.getString("C81"));
				empfactorcurvebean.setC82(rs.getString("C82"));
				empfactorcurvebean.setC83(rs.getString("C83"));
				empfactorcurvebean.setC84(rs.getString("C84"));
				empfactorcurvebean.setC85(rs.getString("C85"));
				empfactorcurvebean.setC86(rs.getString("C86"));
				empfactorcurvebean.setC87(rs.getString("C87"));
				empfactorcurvebean.setC88(rs.getString("C88"));
				empfactorcurvebean.setC89(rs.getString("C89"));
				empfactorcurvebean.setC90(rs.getString("C90"));
				empfactorcurvebean.setC91(rs.getString("C91"));
				empfactorcurvebean.setC92(rs.getString("C92"));
				empfactorcurvebean.setC93(rs.getString("C93"));
				empfactorcurvebean.setC94(rs.getString("C94"));
				empfactorcurvebean.setC95(rs.getString("C95"));
				empfactorcurvebean.setC96(rs.getString("C96"));				
				return empfactorcurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpReadCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpReadCurveBean empreadcurvebean = new EMpReadCurveBean();
			try {
				empreadcurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empreadcurvebean.setR1(rs.getString("R1"));
				empreadcurvebean.setR2(rs.getString("R2"));
				empreadcurvebean.setR3(rs.getString("R3"));
				empreadcurvebean.setR4(rs.getString("R4"));
				empreadcurvebean.setR5(rs.getString("R5"));
				empreadcurvebean.setR6(rs.getString("R6"));
				empreadcurvebean.setR7(rs.getString("R7"));
				empreadcurvebean.setR8(rs.getString("R8"));
				empreadcurvebean.setR9(rs.getString("R9"));
				empreadcurvebean.setR10(rs.getString("R10"));
				empreadcurvebean.setR11(rs.getString("R11"));
				empreadcurvebean.setR12(rs.getString("R12"));
				empreadcurvebean.setR13(rs.getString("R13"));
				empreadcurvebean.setR14(rs.getString("R14"));
				empreadcurvebean.setR15(rs.getString("R15"));
				empreadcurvebean.setR16(rs.getString("R16"));
				empreadcurvebean.setR17(rs.getString("R17"));
				empreadcurvebean.setR18(rs.getString("R18"));
				empreadcurvebean.setR19(rs.getString("R19"));
				empreadcurvebean.setR20(rs.getString("R20"));
				empreadcurvebean.setR21(rs.getString("R21"));
				empreadcurvebean.setR22(rs.getString("R22"));
				empreadcurvebean.setR23(rs.getString("R23"));
				empreadcurvebean.setR24(rs.getString("R24"));
				empreadcurvebean.setR25(rs.getString("R25"));
				empreadcurvebean.setR26(rs.getString("R26"));
				empreadcurvebean.setR27(rs.getString("R27"));
				empreadcurvebean.setR28(rs.getString("R28"));
				empreadcurvebean.setR29(rs.getString("R29"));
				empreadcurvebean.setR30(rs.getString("R30"));
				empreadcurvebean.setR31(rs.getString("R31"));
				empreadcurvebean.setR32(rs.getString("R32"));
				empreadcurvebean.setR33(rs.getString("R33"));
				empreadcurvebean.setR34(rs.getString("R34"));
				empreadcurvebean.setR35(rs.getString("R35"));
				empreadcurvebean.setR36(rs.getString("R36"));
				empreadcurvebean.setR37(rs.getString("R37"));
				empreadcurvebean.setR38(rs.getString("R38"));
				empreadcurvebean.setR39(rs.getString("R39"));
				empreadcurvebean.setR40(rs.getString("R40"));
				empreadcurvebean.setR41(rs.getString("R41"));
				empreadcurvebean.setR42(rs.getString("R42"));
				empreadcurvebean.setR43(rs.getString("R43"));
				empreadcurvebean.setR44(rs.getString("R44"));
				empreadcurvebean.setR45(rs.getString("R45"));
				empreadcurvebean.setR46(rs.getString("R46"));
				empreadcurvebean.setR47(rs.getString("R47"));
				empreadcurvebean.setR48(rs.getString("R48"));
				empreadcurvebean.setR49(rs.getString("R49"));
				empreadcurvebean.setR50(rs.getString("R50"));
				empreadcurvebean.setR51(rs.getString("R51"));
				empreadcurvebean.setR52(rs.getString("R52"));
				empreadcurvebean.setR53(rs.getString("R53"));
				empreadcurvebean.setR54(rs.getString("R54"));
				empreadcurvebean.setR55(rs.getString("R55"));
				empreadcurvebean.setR56(rs.getString("R56"));
				empreadcurvebean.setR57(rs.getString("R57"));
				empreadcurvebean.setR58(rs.getString("R58"));
				empreadcurvebean.setR59(rs.getString("R59"));
				empreadcurvebean.setR60(rs.getString("R60"));
				empreadcurvebean.setR61(rs.getString("R61"));
				empreadcurvebean.setR62(rs.getString("R62"));
				empreadcurvebean.setR63(rs.getString("R63"));
				empreadcurvebean.setR64(rs.getString("R64"));
				empreadcurvebean.setR65(rs.getString("R65"));
				empreadcurvebean.setR66(rs.getString("R66"));
				empreadcurvebean.setR67(rs.getString("R67"));
				empreadcurvebean.setR68(rs.getString("R68"));
				empreadcurvebean.setR69(rs.getString("R69"));
				empreadcurvebean.setR70(rs.getString("R70"));
				empreadcurvebean.setR71(rs.getString("R71"));
				empreadcurvebean.setR72(rs.getString("R72"));
				empreadcurvebean.setR73(rs.getString("R73"));
				empreadcurvebean.setR74(rs.getString("R74"));
				empreadcurvebean.setR75(rs.getString("R75"));
				empreadcurvebean.setR76(rs.getString("R76"));
				empreadcurvebean.setR77(rs.getString("R77"));
				empreadcurvebean.setR78(rs.getString("R78"));
				empreadcurvebean.setR79(rs.getString("R79"));
				empreadcurvebean.setR80(rs.getString("R80"));
				empreadcurvebean.setR81(rs.getString("R81"));
				empreadcurvebean.setR82(rs.getString("R82"));
				empreadcurvebean.setR83(rs.getString("R83"));
				empreadcurvebean.setR84(rs.getString("R84"));
				empreadcurvebean.setR85(rs.getString("R85"));
				empreadcurvebean.setR86(rs.getString("R86"));
				empreadcurvebean.setR87(rs.getString("R87"));
				empreadcurvebean.setR88(rs.getString("R88"));
				empreadcurvebean.setR89(rs.getString("R89"));
				empreadcurvebean.setR90(rs.getString("R90"));
				empreadcurvebean.setR91(rs.getString("R91"));
				empreadcurvebean.setR92(rs.getString("R92"));
				empreadcurvebean.setR93(rs.getString("R93"));
				empreadcurvebean.setR94(rs.getString("R94"));
				empreadcurvebean.setR95(rs.getString("R95"));
				empreadcurvebean.setR96(rs.getString("R96"));				
				return empreadcurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpRealPowerCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GeneralRealPowerDataBean bean = new GeneralRealPowerDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("P") || "".equals(rs.getString("P"))){
					bean.setPower(0.0);
					bean.setFlag(true);
				}else{
					bean.setPower(rs.getDouble("P"));
					bean.setFlag(false);
				}
				if(null == rs.getString("PA") || "".equals(rs.getString("PA"))){
					bean.setPowerA(0.0);
					bean.setFlagA(true);
				}else{
					bean.setPowerA(rs.getDouble("PA"));
					bean.setFlagA(false);
				}
				if(null == rs.getString("PB") || "".equals(rs.getString("PB"))){
					bean.setPowerB(0.0);
					bean.setFlagB(true);
				}else{
					bean.setPowerB(rs.getDouble("PB"));
					bean.setFlagB(false);
				}
				if(null == rs.getString("PC") || "".equals(rs.getString("PC"))){
					bean.setPowerC(0.0);
					bean.setFlagC(true);
				}else{
					bean.setPowerC(rs.getDouble("PC"));
					bean.setFlagC(false);
				}
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpRealCurCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GeneralRealCurDataBean bean = new GeneralRealCurDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("I0") || "".equals(rs.getString("I0"))){
					bean.setCur0(0.0);
					bean.setFlag0(true);
				}else{
					bean.setCur0(rs.getDouble("I0"));
					bean.setFlag0(false);
				}
				if(null == rs.getString("IA") || "".equals(rs.getString("IA"))){
					bean.setCurA(0.0);
					bean.setFlagA(true);
				}else{
					bean.setCurA(rs.getDouble("IA"));
					bean.setFlagA(false);
				}
				if(null == rs.getString("IB") || "".equals(rs.getString("IB"))){
					bean.setCurB(0.0);
					bean.setFlagB(true);
				}else{
					bean.setCurB(rs.getDouble("IB"));
					bean.setFlagB(false);
				}
				if(null == rs.getString("IC") || "".equals(rs.getString("IC"))){
					bean.setCurC(0.0);
					bean.setFlagC(true);
				}else{
					bean.setCurC(rs.getDouble("IC"));
					bean.setFlagC(false);
				}	
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpRealVoltCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GeneralRealVoltDataBean bean = new GeneralRealVoltDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("UA") || "".equals(rs.getString("UA"))){
					bean.setVoltA(0.0);
					bean.setFlagA(true);
				}else{
					bean.setVoltA(rs.getDouble("UA"));
					bean.setFlagA(false);
				}
				if(null == rs.getString("UB") || "".equals(rs.getString("UB"))){
					bean.setVoltB(0.0);
					bean.setFlagB(true);
				}else{
					bean.setVoltB(rs.getDouble("UB"));
					bean.setFlagB(false);
				}
				if(null == rs.getString("UC") || "".equals(rs.getString("UC"))){
					bean.setVoltC(0.0);
					bean.setFlagC(true);
				}else{
					bean.setVoltC(rs.getDouble("UC"));
					bean.setFlagC(false);
				}	
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpRealFactorCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GeneralRealFactorDataBean bean = new GeneralRealFactorDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("F") || "".equals(rs.getString("F"))){
					bean.setFactor(0.0);
					bean.setFlag(true);
				}else{
					bean.setFactor(rs.getDouble("F"));
					bean.setFlag(false);
				}
				if(null == rs.getString("FA") || "".equals(rs.getString("FA"))){
					bean.setFactorA(0.0);
					bean.setFlagA(true);
				}else{
					bean.setFactorA(rs.getDouble("FA"));
					bean.setFlagA(false);
				}
				if(null == rs.getString("FB") || "".equals(rs.getString("FB"))){
					bean.setFactorB(0.0);
					bean.setFlagB(true);
				}else{
					bean.setFactorB(rs.getDouble("FB"));
					bean.setFlagB(false);
				}
				if(null == rs.getString("FC") || "".equals(rs.getString("FC"))){
					bean.setFactorC(0.0);
					bean.setFlagC(true);
				}else{
					bean.setFactorC(rs.getDouble("FC"));
					bean.setFlagC(false);
				}
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eTotalRealPowerCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GeneralRealPowerDataBean bean = new GeneralRealPowerDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("P") || "".equals(rs.getString("P"))){
					bean.setPower(0.0);
					bean.setFlag(true);
				}else{
					bean.setPower(rs.getDouble("P"));
					bean.setFlag(false);
				}
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class eMpDayCompListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayCompBean empdaycompbean = new EMpDayCompBean();
			try {
				empdaycompbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdaycompbean.setAssetNo(rs.getString("ASSET_NO"));
				empdaycompbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				empdaycompbean.setCapUseTime1((null != rs.getString("CAP_USE_TIME1"))? rs.getLong("CAP_USE_TIME1") : null);
				empdaycompbean.setCapUseTime2((null != rs.getString("CAP_USE_TIME2"))? rs.getLong("CAP_USE_TIME2") : null);
				empdaycompbean.setCapUseTime3((null != rs.getString("CAP_USE_TIME3"))? rs.getLong("CAP_USE_TIME3") : null);
				empdaycompbean.setCapUseTime4((null != rs.getString("CAP_USE_TIME4"))? rs.getLong("CAP_USE_TIME4") : null);
				empdaycompbean.setCapUseTime5((null != rs.getString("CAP_USE_TIME5"))? rs.getLong("CAP_USE_TIME5") : null);
				empdaycompbean.setCapUseTime6((null != rs.getString("CAP_USE_TIME6"))? rs.getLong("CAP_USE_TIME6") : null);
				empdaycompbean.setCapUseTime7((null != rs.getString("CAP_USE_TIME7"))? rs.getLong("CAP_USE_TIME7") : null);
				empdaycompbean.setCapUseTime8((null != rs.getString("CAP_USE_TIME8"))? rs.getLong("CAP_USE_TIME8") : null);
				empdaycompbean.setCapUseTime9((null != rs.getString("CAP_USE_TIME9"))? rs.getLong("CAP_USE_TIME9") : null);
				empdaycompbean.setCapUseNum1((null != rs.getString("CAP_USE_NUM1"))? rs.getLong("CAP_USE_NUM1") : null);
				empdaycompbean.setCapUseNum2((null != rs.getString("CAP_USE_NUM2"))? rs.getLong("CAP_USE_NUM2") : null);
				empdaycompbean.setCapUseNum3((null != rs.getString("CAP_USE_NUM3"))? rs.getLong("CAP_USE_NUM3") : null);
				empdaycompbean.setCapUseNum4((null != rs.getString("CAP_USE_NUM4"))? rs.getLong("CAP_USE_NUM4") : null);
				empdaycompbean.setCapUseNum5((null != rs.getString("CAP_USE_NUM5"))? rs.getLong("CAP_USE_NUM5") : null);
				empdaycompbean.setCapUseNum6((null != rs.getString("CAP_USE_NUM6"))? rs.getLong("CAP_USE_NUM6") : null);
				empdaycompbean.setCapUseNum7((null != rs.getString("CAP_USE_NUM7"))? rs.getLong("CAP_USE_NUM7") : null);
				empdaycompbean.setCapUseNum8((null != rs.getString("CAP_USE_NUM8"))? rs.getLong("CAP_USE_NUM8") : null);
				empdaycompbean.setCapUseNum9((null != rs.getString("CAP_USE_NUM9"))? rs.getLong("CAP_USE_NUM9") : null);
				empdaycompbean.setDayCompE((null != rs.getString("DAY_COMP_E"))? rs.getDouble("DAY_COMP_E") : null);
				empdaycompbean.setMonCompE((null != rs.getString("MON_COMP_E"))? rs.getDouble("MON_COMP_E") : null);
				empdaycompbean.setMpNo(rs.getString("MP_NO"));
				empdaycompbean.setMpName(rs.getString("MP_NAME"));
				empdaycompbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdaycompbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayDemandListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayDemandBean empdaydemandbean = new EMpDayDemandBean();
			try {
				empdaydemandbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdaydemandbean.setAssetNo(rs.getString("ASSET_NO"));
				empdaydemandbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				empdaydemandbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				empdaydemandbean.setPapDemand((null != rs.getString("PAP_DEMAND"))? rs.getDouble("PAP_DEMAND") : null);
				empdaydemandbean.setPapDemandTime(timeStampToStr(rs.getTimestamp("PAP_DEMAND_TIME")));
				empdaydemandbean.setPrpDemand((null != rs.getString("PRP_DEMAND"))? rs.getDouble("PRP_DEMAND") : null);
				empdaydemandbean.setPrpDemandTime(timeStampToStr(rs.getTimestamp("PRP_DEMAND_TIME")));
				empdaydemandbean.setRapDemand((null != rs.getString("RAP_DEMAND"))? rs.getDouble("RAP_DEMAND") : null);
				empdaydemandbean.setRapDemandTime(timeStampToStr(rs.getTimestamp("RAP_DEMAND_TIME")));
				empdaydemandbean.setRrpDemand((null != rs.getString("RRP_DEMAND"))? rs.getDouble("RRP_DEMAND") : null);
				empdaydemandbean.setRrpDemandTime(timeStampToStr(rs.getTimestamp("RRP_DEMAND_TIME")));
				empdaydemandbean.setMpNo(rs.getString("MP_NO"));
				empdaydemandbean.setMpName(rs.getString("MP_NAME"));
				empdaydemandbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdaydemandbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayEnergyListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayEnergyBean empdayenergybean = new EMpDayEnergyBean();
			try {
				empdayenergybean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdayenergybean.setAssetNo(rs.getString("ASSET_NO"));
				empdayenergybean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("DATA_DATE")));
				empdayenergybean.setPapE((null != rs.getString("PAP_E"))? rs.getDouble("PAP_E") : null);
				empdayenergybean.setPapE1((null != rs.getString("PAP_E1"))? rs.getDouble("PAP_E1") : null);
				empdayenergybean.setPapE2((null != rs.getString("PAP_E2"))? rs.getDouble("PAP_E2") : null);
				empdayenergybean.setPapE3((null != rs.getString("PAP_E3"))? rs.getDouble("PAP_E3") : null);
				empdayenergybean.setPapE4((null != rs.getString("PAP_E4"))? rs.getDouble("PAP_E4") : null);
				empdayenergybean.setPrpE((null != rs.getString("PRP_E"))? rs.getDouble("PRP_E") : null);
				empdayenergybean.setPrpE1((null != rs.getString("PRP_E1"))? rs.getDouble("PRP_E1") : null);
				empdayenergybean.setPrpE2((null != rs.getString("PRP_E2"))? rs.getDouble("PRP_E2") : null);
				empdayenergybean.setPrpE3((null != rs.getString("PRP_E3"))? rs.getDouble("PRP_E3") : null);
				empdayenergybean.setPrpE4((null != rs.getString("PRP_E4"))? rs.getDouble("PRP_E4") : null);
				empdayenergybean.setRapE((null != rs.getString("RAP_E"))? rs.getDouble("RAP_E") : null);
				empdayenergybean.setRapE1((null != rs.getString("RAP_E1"))? rs.getDouble("RAP_E1") : null);
				empdayenergybean.setRapE2((null != rs.getString("RAP_E2"))? rs.getDouble("RAP_E2") : null);
				empdayenergybean.setRapE3((null != rs.getString("RAP_E3"))? rs.getDouble("RAP_E3") : null);
				empdayenergybean.setRapE4((null != rs.getString("RAP_E4"))? rs.getDouble("RAP_E4") : null);
				empdayenergybean.setRrpE((null != rs.getString("RRP_E"))? rs.getDouble("RRP_E") : null);
				empdayenergybean.setRrpE1((null != rs.getString("RRP_E1"))? rs.getDouble("RRP_E1") : null);
				empdayenergybean.setRrpE2((null != rs.getString("RRP_E2"))? rs.getDouble("RRP_E2") : null);
				empdayenergybean.setRrpE3((null != rs.getString("RRP_E3"))? rs.getDouble("RRP_E3") : null);
				empdayenergybean.setRrpE4((null != rs.getString("RRP_E4"))? rs.getDouble("RRP_E4") : null);
				empdayenergybean.setMpNo(rs.getString("MP_NO"));
				empdayenergybean.setMpName(rs.getString("MP_NAME"));
				empdayenergybean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdayenergybean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayIstatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayIstatBean empdayistatbean = new EMpDayIstatBean();
			try {
				empdayistatbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdayistatbean.setAssetNo(rs.getString("ASSET_NO"));
				empdayistatbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdayistatbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empdayistatbean.setIaUupTime((null != rs.getString("IA_UUP_TIME"))? rs.getInt("IA_UUP_TIME") : null);
				empdayistatbean.setIaUpTime((null != rs.getString("IA_UP_TIME"))? rs.getInt("IA_UP_TIME") : null);
				empdayistatbean.setIbUupTime((null != rs.getString("IB_UUP_TIME"))? rs.getInt("IB_UUP_TIME") : null);
				empdayistatbean.setIbUpTime((null != rs.getString("IB_UP_TIME"))? rs.getInt("IB_UP_TIME") : null);
				empdayistatbean.setIcUupTime((null != rs.getString("IC_UUP_TIME"))? rs.getInt("IC_UUP_TIME") : null);
				empdayistatbean.setIcUpTime((null != rs.getString("IC_UP_TIME"))? rs.getInt("IC_UP_TIME") : null);
				empdayistatbean.setI0UpTime((null != rs.getString("I0_UP_TIME"))? rs.getInt("I0_UP_TIME") : null);
				empdayistatbean.setIaMax((null != rs.getString("IA_MAX"))? rs.getDouble("IA_MAX") : null);
				empdayistatbean.setIaMaxTime(timeStampToStr(rs.getTimestamp("IA_MAX_TIME")));
				empdayistatbean.setIbMax((null != rs.getString("IB_MAX"))? rs.getDouble("IB_MAX") : null);
				empdayistatbean.setIbMaxTime(timeStampToStr(rs.getTimestamp("IB_MAX_TIME")));
				empdayistatbean.setIcMax((null != rs.getString("IC_MAX"))? rs.getDouble("IC_MAX") : null);
				empdayistatbean.setIcMaxTime(timeStampToStr(rs.getTimestamp("IC_MAX_TIME")));
				empdayistatbean.setI0Max((null != rs.getString("I0_MAX"))? rs.getDouble("I0_MAX") : null);
				empdayistatbean.setI0MaxTime(timeStampToStr(rs.getTimestamp("I0_MAX_TIME")));
				empdayistatbean.setMpNo(rs.getString("MP_NO"));
				empdayistatbean.setMpName(rs.getString("MP_NAME"));
				empdayistatbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdayistatbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayPftimeListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayPftimeBean empdaypftimebean = new EMpDayPftimeBean();
			try {
				empdaypftimebean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdaypftimebean.setAssetNo(rs.getString("ASSET_NO"));
				empdaypftimebean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdaypftimebean.setSumTime1((null != rs.getString("SUM_TIME1"))? rs.getInt("SUM_TIME1") : null);
				empdaypftimebean.setSumTime2((null != rs.getString("SUM_TIME2"))? rs.getInt("SUM_TIME2") : null);
				empdaypftimebean.setSumTime3((null != rs.getString("SUM_TIME3"))? rs.getInt("SUM_TIME3") : null);
				empdaypftimebean.setMpNo(rs.getString("MP_NO"));
				empdaypftimebean.setMpName(rs.getString("MP_NAME"));
				empdaypftimebean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdaypftimebean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayPowerListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayPowerBean empdaypowerbean = new EMpDayPowerBean();
			try {
				empdaypowerbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdaypowerbean.setAssetNo(rs.getString("ASSET_NO"));
				empdaypowerbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdaypowerbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empdaypowerbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				empdaypowerbean.setTotalMaxP((null != rs.getString("TOTAL_MAX_P"))? rs.getDouble("TOTAL_MAX_P") : null);
				empdaypowerbean.setTotalMaxPTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_P_TIME")));
				empdaypowerbean.setTotalMaxPa((null != rs.getString("TOTAL_MAX_PA"))? rs.getDouble("TOTAL_MAX_PA") : null);
				empdaypowerbean.setTotalMaxPaTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PA_TIME")));
				empdaypowerbean.setTotalMaxPb((null != rs.getString("TOTAL_MAX_PB"))? rs.getDouble("TOTAL_MAX_PB") : null);
				empdaypowerbean.setTotalMaxPbTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PB_TIME")));
				empdaypowerbean.setTotalMaxPc((null != rs.getString("TOTAL_MAX_PC"))? rs.getDouble("TOTAL_MAX_PC") : null);
				empdaypowerbean.setTotalMaxPcTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PC_TIME")));
				empdaypowerbean.setTotalPZero((null != rs.getString("TOTAL_P_ZERO"))? rs.getInt("TOTAL_P_ZERO") : null);
				empdaypowerbean.setTotalPaZero((null != rs.getString("TOTAL_PA_ZERO"))? rs.getInt("TOTAL_PA_ZERO") : null);
				empdaypowerbean.setTotalPbZero((null != rs.getString("TOTAL_PB_ZERO"))? rs.getInt("TOTAL_PB_ZERO") : null);
				empdaypowerbean.setTotalPcZero((null != rs.getString("TOTAL_PC_ZERO"))? rs.getInt("TOTAL_PC_ZERO") : null);
				empdaypowerbean.setMpNo(rs.getString("MP_NO"));
				empdaypowerbean.setMpName(rs.getString("MP_NAME"));
				empdaypowerbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdaypowerbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayReadListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayReadBean empdayreadbean = new EMpDayReadBean();
			try {
				empdayreadbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdayreadbean.setAssetNo(rs.getString("ASSET_NO"));
				empdayreadbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdayreadbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));				
				empdayreadbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empdayreadbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				empdayreadbean.setPapR((null != rs.getString("PAP_R"))? rs.getDouble("PAP_R") : null);
				empdayreadbean.setPapR1((null != rs.getString("PAP_R1"))? rs.getDouble("PAP_R1") : null);
				empdayreadbean.setPapR2((null != rs.getString("PAP_R2"))? rs.getDouble("PAP_R2") : null);
				empdayreadbean.setPapR3((null != rs.getString("PAP_R3"))? rs.getDouble("PAP_R3") : null);
				empdayreadbean.setPapR4((null != rs.getString("PAP_R4"))? rs.getDouble("PAP_R4") : null);
				empdayreadbean.setPrpR((null != rs.getString("PRP_R"))? rs.getDouble("PRP_R") : null);
				empdayreadbean.setPrpR1((null != rs.getString("PRP_R1"))? rs.getDouble("PRP_R1") : null);
				empdayreadbean.setPrpR2((null != rs.getString("PRP_R2"))? rs.getDouble("PRP_R2") : null);
				empdayreadbean.setPrpR3((null != rs.getString("PRP_R3"))? rs.getDouble("PRP_R3") : null);
				empdayreadbean.setPrpR4((null != rs.getString("PRP_R4"))? rs.getDouble("PRP_R4") : null);
				empdayreadbean.setRapR((null != rs.getString("RAP_R"))? rs.getDouble("RAP_R") : null);
				empdayreadbean.setRapR1((null != rs.getString("RAP_R1"))? rs.getDouble("RAP_R1") : null);
				empdayreadbean.setRapR2((null != rs.getString("RAP_R2"))? rs.getDouble("RAP_R2") : null);
				empdayreadbean.setRapR3((null != rs.getString("RAP_R3"))? rs.getDouble("RAP_R3") : null);
				empdayreadbean.setRapR4((null != rs.getString("RAP_R4"))? rs.getDouble("RAP_R4") : null);
				empdayreadbean.setRrpR((null != rs.getString("RRP_R"))? rs.getDouble("RRP_R") : null);
				empdayreadbean.setRrpR1((null != rs.getString("RRP_R1"))? rs.getDouble("RRP_R1") : null);
				empdayreadbean.setRrpR2((null != rs.getString("RRP_R2"))? rs.getDouble("RRP_R2") : null);
				empdayreadbean.setRrpR3((null != rs.getString("RRP_R3"))? rs.getDouble("RRP_R3") : null);
				empdayreadbean.setRrpR4((null != rs.getString("RRP_R4"))? rs.getDouble("RRP_R4") : null);
				empdayreadbean.setRp1R((null != rs.getString("RP1_R"))? rs.getDouble("RP1_R") : null);
				empdayreadbean.setRp4R((null != rs.getString("RP4_R"))? rs.getDouble("RP4_R") : null);
				empdayreadbean.setRp2R((null != rs.getString("RP2_R"))? rs.getDouble("RP2_R") : null);
				empdayreadbean.setRp3R((null != rs.getString("RP3_R"))? rs.getDouble("RP3_R") : null);
				empdayreadbean.setMpNo(rs.getString("MP_NO"));
				empdayreadbean.setMpName(rs.getString("MP_NAME"));
				empdayreadbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdayreadbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayUnbalanceListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayUnbalanceBean empdayunbalancebean = new EMpDayUnbalanceBean();
			try {
				empdayunbalancebean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdayunbalancebean.setAssetNo(rs.getString("ASSET_NO"));
				empdayunbalancebean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdayunbalancebean.setIUbTime((null != rs.getString("I_UB_TIME"))? rs.getInt("I_UB_TIME") : null);
				empdayunbalancebean.setUUbTime((null != rs.getString("U_UB_TIME"))? rs.getInt("U_UB_TIME") : null);
				empdayunbalancebean.setIUbMaxVal((null != rs.getString("I_UB_MAX_VAL"))? rs.getDouble("I_UB_MAX_VAL") : null);
				empdayunbalancebean.setIUbMaxTime(timeStampToStr(rs.getTimestamp("I_UB_MAX_TIME")));
				empdayunbalancebean.setUUbMaxVal((null != rs.getString("U_UB_MAX_VAL"))? rs.getDouble("U_UB_MAX_VAL") : null);
				empdayunbalancebean.setUUbMaxTime(timeStampToStr(rs.getTimestamp("U_UB_MAX_TIME")));
				empdayunbalancebean.setMpNo(rs.getString("MP_NO"));
				empdayunbalancebean.setMpName(rs.getString("MP_NAME"));
				empdayunbalancebean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdayunbalancebean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpDayVstatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpDayVstatBean empdayvstatbean = new EMpDayVstatBean();
			try {
				empdayvstatbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empdayvstatbean.setAssetNo(rs.getString("ASSET_NO"));
				empdayvstatbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empdayvstatbean.setPt(rs.getDouble("PT"));
				empdayvstatbean.setUaUupTime((null != rs.getString("UA_UUP_TIME"))? rs.getInt("UA_UUP_TIME") : null);
				empdayvstatbean.setUaLlwTime((null != rs.getString("UA_LLW_TIME"))? rs.getInt("UA_LLW_TIME") : null);
				empdayvstatbean.setUaUpTime((null != rs.getString("UA_UP_TIME"))? rs.getInt("UA_UP_TIME") : null);
				empdayvstatbean.setUaLowTime((null != rs.getString("UA_LOW_TIME"))? rs.getInt("UA_LOW_TIME") : null);
				empdayvstatbean.setUaNmlTime((null != rs.getString("UA_NML_TIME"))? rs.getInt("UA_NML_TIME") : null);
				empdayvstatbean.setUbUupTime((null != rs.getString("UB_UUP_TIME"))? rs.getInt("UB_UUP_TIME") : null);
				empdayvstatbean.setUbLlwTime((null != rs.getString("UB_LLW_TIME"))? rs.getInt("UB_LLW_TIME") : null);
				empdayvstatbean.setUbUpTime((null != rs.getString("UB_UP_TIME"))? rs.getInt("UB_UP_TIME") : null);
				empdayvstatbean.setUbLowTime((null != rs.getString("UB_LOW_TIME"))? rs.getInt("UB_LOW_TIME") : null);
				empdayvstatbean.setUbNmlTime((null != rs.getString("UB_NML_TIME"))? rs.getInt("UB_NML_TIME") : null);
				empdayvstatbean.setUcUupTime((null != rs.getString("UC_UUP_TIME"))? rs.getInt("UC_UUP_TIME") : null);
				empdayvstatbean.setUcLlwTime((null != rs.getString("UC_LLW_TIME"))? rs.getInt("UC_LLW_TIME") : null);
				empdayvstatbean.setUcUpTime((null != rs.getString("UC_UP_TIME"))? rs.getInt("UC_UP_TIME") : null);
				empdayvstatbean.setUcLowTime((null != rs.getString("UC_LOW_TIME"))? rs.getInt("UC_LOW_TIME") : null);
				empdayvstatbean.setUcNmlTime((null != rs.getString("UC_NML_TIME"))? rs.getInt("UC_NML_TIME") : null);
				empdayvstatbean.setUaMax((null != rs.getString("UA_MAX"))? rs.getDouble("UA_MAX") : null);
				empdayvstatbean.setUaMaxTime(timeStampToStr(rs.getTimestamp("UA_MAX_TIME")));
				empdayvstatbean.setUaMin((null != rs.getString("UA_MIN"))? rs.getDouble("UA_MIN") : null);
				empdayvstatbean.setUaMinTime(timeStampToStr(rs.getTimestamp("UA_MIN_TIME")));
				empdayvstatbean.setUbMax((null != rs.getString("UB_MAX"))? rs.getDouble("UB_MAX") : null);
				empdayvstatbean.setUbMaxTime(timeStampToStr(rs.getTimestamp("UB_MAX_TIME")));
				empdayvstatbean.setUbMin((null != rs.getString("UB_MIN"))? rs.getDouble("UB_MIN") : null);
				empdayvstatbean.setUbMinTime(timeStampToStr(rs.getTimestamp("UB_MIN_TIME")));
				empdayvstatbean.setUcMax((null != rs.getString("UC_MAX"))? rs.getDouble("UC_MAX") : null);
				empdayvstatbean.setUcMaxTime(timeStampToStr(rs.getTimestamp("UC_MAX_TIME")));
				empdayvstatbean.setUcMin((null != rs.getString("UC_MIN"))? rs.getDouble("UC_MIN") : null);
				empdayvstatbean.setUcMinTime(timeStampToStr(rs.getTimestamp("UC_MIN_TIME")));
				empdayvstatbean.setUaAverage((null != rs.getString("UA_AVERAGE"))? rs.getDouble("UA_AVERAGE") : null);
				empdayvstatbean.setUbAverage((null != rs.getString("UB_AVERAGE"))? rs.getDouble("UB_AVERAGE") : null);
				empdayvstatbean.setUcAverage((null != rs.getString("UC_AVERAGE"))? rs.getDouble("UC_AVERAGE") : null);
				empdayvstatbean.setMpNo(rs.getString("MP_NO"));
				empdayvstatbean.setMpName(rs.getString("MP_NAME"));
				empdayvstatbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empdayvstatbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonDemandListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonDemandBean empmondemandbean = new EMpMonDemandBean();
			try {
				empmondemandbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmondemandbean.setAssetNo(rs.getString("ASSET_NO"));
				empmondemandbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmondemandbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				empmondemandbean.setPapDemand((null != rs.getString("PAP_DEMAND"))? rs.getDouble("PAP_DEMAND") : null);
				empmondemandbean.setPapDemandTime(timeStampToStr(rs.getTimestamp("PAP_DEMAND_TIME")));
				empmondemandbean.setPrpDemand((null != rs.getString("PRP_DEMAND"))? rs.getDouble("PRP_DEMAND") : null);
				empmondemandbean.setPrpDemandTime(timeStampToStr(rs.getTimestamp("PRP_DEMAND_TIME")));
				empmondemandbean.setRapDemand((null != rs.getString("RAP_DEMAND"))? rs.getDouble("RAP_DEMAND") : null);
				empmondemandbean.setRapDemandTime(timeStampToStr(rs.getTimestamp("RAP_DEMAND_TIME")));
				empmondemandbean.setRrpDemand((null != rs.getString("RRP_DEMAND"))? rs.getDouble("RRP_DEMAND") : null);
				empmondemandbean.setRrpDemandTime(timeStampToStr(rs.getTimestamp("RRP_DEMAND_TIME")));
				empmondemandbean.setMpNo(rs.getString("MP_NO"));
				empmondemandbean.setMpName(rs.getString("MP_NAME"));
				empmondemandbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmondemandbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonEnergyListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonEnergyBean empmonenergybean = new EMpMonEnergyBean();
			try {
				empmonenergybean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonenergybean.setAssetNo(rs.getString("ASSET_NO"));
				empmonenergybean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));			
				empmonenergybean.setPapE((null != rs.getString("PAP_E"))? rs.getDouble("PAP_E") : null);
				empmonenergybean.setPapE1((null != rs.getString("PAP_E1"))? rs.getDouble("PAP_E1") : null);
				empmonenergybean.setPapE2((null != rs.getString("PAP_E2"))? rs.getDouble("PAP_E2") : null);
				empmonenergybean.setPapE3((null != rs.getString("PAP_E3"))? rs.getDouble("PAP_E3") : null);
				empmonenergybean.setPapE4((null != rs.getString("PAP_E4"))? rs.getDouble("PAP_E4") : null);
				empmonenergybean.setPrpE((null != rs.getString("PRP_E"))? rs.getDouble("PRP_E") : null);
				empmonenergybean.setPrpE1((null != rs.getString("PRP_E1"))? rs.getDouble("PRP_E1") : null);
				empmonenergybean.setPrpE2((null != rs.getString("PRP_E2"))? rs.getDouble("PRP_E2") : null);
				empmonenergybean.setPrpE3((null != rs.getString("PRP_E3"))? rs.getDouble("PRP_E3") : null);
				empmonenergybean.setPrpE4((null != rs.getString("PRP_E4"))? rs.getDouble("PRP_E4") : null);
				empmonenergybean.setRapE((null != rs.getString("RAP_E"))? rs.getDouble("RAP_E") : null);
				empmonenergybean.setRapE1((null != rs.getString("RAP_E1"))? rs.getDouble("RAP_E1") : null);
				empmonenergybean.setRapE2((null != rs.getString("RAP_E2"))? rs.getDouble("RAP_E2") : null);
				empmonenergybean.setRapE3((null != rs.getString("RAP_E3"))? rs.getDouble("RAP_E3") : null);
				empmonenergybean.setRapE4((null != rs.getString("RAP_E4"))? rs.getDouble("RAP_E4") : null);
				empmonenergybean.setRrpE((null != rs.getString("RRP_E"))? rs.getDouble("RRP_E") : null);
				empmonenergybean.setRrpE1((null != rs.getString("RRP_E1"))? rs.getDouble("RRP_E1") : null);
				empmonenergybean.setRrpE2((null != rs.getString("RRP_E2"))? rs.getDouble("RRP_E2") : null);
				empmonenergybean.setRrpE3((null != rs.getString("RRP_E3"))? rs.getDouble("RRP_E3") : null);
				empmonenergybean.setRrpE4((null != rs.getString("RRP_E4"))? rs.getDouble("RRP_E4") : null);
				empmonenergybean.setMpNo(rs.getString("MP_NO"));
				empmonenergybean.setMpName(rs.getString("MP_NAME"));
				empmonenergybean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonenergybean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonIstatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonIstatBean empmonistatbean = new EMpMonIstatBean();
			try {
				empmonistatbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonistatbean.setAssetNo(rs.getString("ASSET_NO"));
				empmonistatbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonistatbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empmonistatbean.setIaUupTime((null != rs.getString("IA_UUP_TIME"))? rs.getInt("IA_UUP_TIME") : null);
				empmonistatbean.setIaUpTime((null != rs.getString("IA_UP_TIME"))? rs.getInt("IA_UP_TIME") : null);
				empmonistatbean.setIbUupTime((null != rs.getString("IB_UUP_TIME"))? rs.getInt("IB_UUP_TIME") : null);
				empmonistatbean.setIbUpTime((null != rs.getString("IB_UP_TIME"))? rs.getInt("IB_UP_TIME") : null);
				empmonistatbean.setIcUupTime((null != rs.getString("IC_UUP_TIME"))? rs.getInt("IC_UUP_TIME") : null);
				empmonistatbean.setIcUpTime((null != rs.getString("IC_UP_TIME"))? rs.getInt("IC_UP_TIME") : null);
				empmonistatbean.setI0UpTime((null != rs.getString("I0_UP_TIME"))? rs.getInt("I0_UP_TIME") : null);
				empmonistatbean.setIaMax((null != rs.getString("IA_MAX"))? rs.getDouble("IA_MAX") : null);
				empmonistatbean.setIaMaxTime(timeStampToStr(rs.getTimestamp("IA_MAX_TIME")));
				empmonistatbean.setIbMax((null != rs.getString("IB_MAX"))? rs.getDouble("IB_MAX") : null);
				empmonistatbean.setIbMaxTime(timeStampToStr(rs.getTimestamp("IB_MAX_TIME")));
				empmonistatbean.setIcMax((null != rs.getString("IC_MAX"))? rs.getDouble("IC_MAX") : null);
				empmonistatbean.setIcMaxTime(timeStampToStr(rs.getTimestamp("IC_MAX_TIME")));
				empmonistatbean.setI0Max((null != rs.getString("I0_MAX"))? rs.getDouble("I0_MAX") : null);
				empmonistatbean.setI0MaxTime(timeStampToStr(rs.getTimestamp("I0_MAX_TIME")));
				empmonistatbean.setMpNo(rs.getString("MP_NO"));
				empmonistatbean.setMpName(rs.getString("MP_NAME"));
				empmonistatbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonistatbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonPftimeListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonPftimeBean empmonpftimebean = new EMpMonPftimeBean();
			try {
				empmonpftimebean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonpftimebean.setAssetNo(rs.getString("ASSET_NO"));
				empmonpftimebean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonpftimebean.setSumTime1((null != rs.getString("SUM_TIME1"))? rs.getInt("SUM_TIME1") : null);
				empmonpftimebean.setSumTime2((null != rs.getString("SUM_TIME2"))? rs.getInt("SUM_TIME2") : null);
				empmonpftimebean.setSumTime3((null != rs.getString("SUM_TIME3"))? rs.getInt("SUM_TIME3") : null);
				empmonpftimebean.setMpNo(rs.getString("MP_NO"));
				empmonpftimebean.setMpName(rs.getString("MP_NAME"));
				empmonpftimebean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonpftimebean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonPowerListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonPowerBean empmonpowerbean = new EMpMonPowerBean();
			try {
				empmonpowerbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonpowerbean.setAssetNo(rs.getString("ASSET_NO"));
				empmonpowerbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonpowerbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empmonpowerbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				empmonpowerbean.setTotalMaxP((null != rs.getString("TOTAL_MAX_P"))? rs.getDouble("TOTAL_MAX_P") : null);
				empmonpowerbean.setTotalMaxPTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_P_TIME")));
				empmonpowerbean.setTotalMaxPa((null != rs.getString("TOTAL_MAX_PA"))? rs.getDouble("TOTAL_MAX_PA") : null);
				empmonpowerbean.setTotalMaxPaTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PA_TIME")));
				empmonpowerbean.setTotalMaxPb((null != rs.getString("TOTAL_MAX_PB"))? rs.getDouble("TOTAL_MAX_PB") : null);
				empmonpowerbean.setTotalMaxPbTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PB_TIME")));
				empmonpowerbean.setTotalMaxPc((null != rs.getString("TOTAL_MAX_PC"))? rs.getDouble("TOTAL_MAX_PC") : null);
				empmonpowerbean.setTotalMaxPcTime(timeStampToStr(rs.getTimestamp("TOTAL_MAX_PC_TIME")));
				empmonpowerbean.setTotalPZero((null != rs.getString("TOTAL_P_ZERO"))? rs.getInt("TOTAL_P_ZERO") : null);
				empmonpowerbean.setTotalPaZero((null != rs.getString("TOTAL_PA_ZERO"))? rs.getInt("TOTAL_PA_ZERO") : null);
				empmonpowerbean.setTotalPbZero((null != rs.getString("TOTAL_PB_ZERO"))? rs.getInt("TOTAL_PB_ZERO") : null);
				empmonpowerbean.setTotalPcZero((null != rs.getString("TOTAL_PC_ZERO"))? rs.getInt("TOTAL_PC_ZERO") : null);
				empmonpowerbean.setMpNo(rs.getString("MP_NO"));
				empmonpowerbean.setMpName(rs.getString("MP_NAME"));
				empmonpowerbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonpowerbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonReadListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonReadBean empmonreadbean = new EMpMonReadBean();
			try {
				empmonreadbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonreadbean.setAssetNo(rs.getString("ASSET_NO"));
				empmonreadbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonreadbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				empmonreadbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				empmonreadbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				empmonreadbean.setPapR((null != rs.getString("PAP_R"))? rs.getDouble("PAP_R") : null);
				empmonreadbean.setPapR1((null != rs.getString("PAP_R1"))? rs.getDouble("PAP_R1") : null);
				empmonreadbean.setPapR2((null != rs.getString("PAP_R2"))? rs.getDouble("PAP_R2") : null);
				empmonreadbean.setPapR3((null != rs.getString("PAP_R3"))? rs.getDouble("PAP_R3") : null);
				empmonreadbean.setPapR4((null != rs.getString("PAP_R4"))? rs.getDouble("PAP_R4") : null);
				empmonreadbean.setPrpR((null != rs.getString("PRP_R"))? rs.getDouble("PRP_R") : null);
				empmonreadbean.setPrpR1((null != rs.getString("PRP_R1"))? rs.getDouble("PRP_R1") : null);
				empmonreadbean.setPrpR2((null != rs.getString("PRP_R2"))? rs.getDouble("PRP_R2") : null);
				empmonreadbean.setPrpR3((null != rs.getString("PRP_R3"))? rs.getDouble("PRP_R3") : null);
				empmonreadbean.setPrpR4((null != rs.getString("PRP_R4"))? rs.getDouble("PRP_R4") : null);
				empmonreadbean.setRapR((null != rs.getString("RAP_R"))? rs.getDouble("RAP_R") : null);
				empmonreadbean.setRapR1((null != rs.getString("RAP_R1"))? rs.getDouble("RAP_R1") : null);
				empmonreadbean.setRapR2((null != rs.getString("RAP_R2"))? rs.getDouble("RAP_R2") : null);
				empmonreadbean.setRapR3((null != rs.getString("RAP_R3"))? rs.getDouble("RAP_R3") : null);
				empmonreadbean.setRapR4((null != rs.getString("RAP_R4"))? rs.getDouble("RAP_R4") : null);
				empmonreadbean.setRrpR((null != rs.getString("RRP_R"))? rs.getDouble("RRP_R") : null);
				empmonreadbean.setRrpR1((null != rs.getString("RRP_R1"))? rs.getDouble("RRP_R1") : null);
				empmonreadbean.setRrpR2((null != rs.getString("RRP_R2"))? rs.getDouble("RRP_R2") : null);
				empmonreadbean.setRrpR3((null != rs.getString("RRP_R3"))? rs.getDouble("RRP_R3") : null);
				empmonreadbean.setRrpR4((null != rs.getString("RRP_R4"))? rs.getDouble("RRP_R4") : null);
				empmonreadbean.setRp1R((null != rs.getString("RP1_R"))? rs.getDouble("RP1_R") : null);
				empmonreadbean.setRp4R((null != rs.getString("RP4_R"))? rs.getDouble("RP4_R") : null);
				empmonreadbean.setRp2R((null != rs.getString("RP2_R"))? rs.getDouble("RP2_R") : null);
				empmonreadbean.setRp3R((null != rs.getString("RP3_R"))? rs.getDouble("RP3_R") : null);
				empmonreadbean.setMpNo(rs.getString("MP_NO"));
				empmonreadbean.setMpName(rs.getString("MP_NAME"));
				empmonreadbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonreadbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonUnbalanceListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonUnbalanceBean empmonunbalancebean = new EMpMonUnbalanceBean();
			try {
				empmonunbalancebean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonunbalancebean.setAssetNo(rs.getString("ASSET_NO"));
				empmonunbalancebean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonunbalancebean.setIUbTime((null != rs.getString("I_UB_TIME"))? rs.getInt("I_UB_TIME") : null);
				empmonunbalancebean.setUUbTime((null != rs.getString("U_UB_TIME"))? rs.getInt("U_UB_TIME") : null);
				empmonunbalancebean.setIUbMaxVal((null != rs.getString("I_UB_MAX_VAL"))? rs.getDouble("I_UB_MAX_VAL") : null);
				empmonunbalancebean.setIUbMaxTime(timeStampToStr(rs.getTimestamp("I_UB_MAX_TIME")));
				empmonunbalancebean.setUUbMaxVal((null != rs.getString("U_UB_MAX_VAL"))? rs.getDouble("U_UB_MAX_VAL") : null);
				empmonunbalancebean.setUUbMaxTime(timeStampToStr(rs.getTimestamp("U_UB_MAX_TIME")));
				empmonunbalancebean.setMpNo(rs.getString("MP_NO"));
				empmonunbalancebean.setMpName(rs.getString("MP_NAME"));
				empmonunbalancebean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonunbalancebean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpMonVstatListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpMonVstatBean empmonvstatbean = new EMpMonVstatBean();
			try {
				empmonvstatbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				empmonvstatbean.setAssetNo(rs.getString("ASSET_NO"));
				empmonvstatbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				empmonvstatbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				empmonvstatbean.setUaUupTime((null != rs.getString("UA_UUP_TIME"))? rs.getInt("UA_UUP_TIME") : null);
				empmonvstatbean.setUaLlwTime((null != rs.getString("UA_LLW_TIME"))? rs.getInt("UA_LLW_TIME") : null);
				empmonvstatbean.setUaUpTime((null != rs.getString("UA_UP_TIME"))? rs.getInt("UA_UP_TIME") : null);
				empmonvstatbean.setUaLowTime((null != rs.getString("UA_LOW_TIME"))? rs.getInt("UA_LOW_TIME") : null);
				empmonvstatbean.setUaNmlTime((null != rs.getString("UA_NML_TIME"))? rs.getInt("UA_NML_TIME") : null);
				empmonvstatbean.setUbUupTime((null != rs.getString("UB_UUP_TIME"))? rs.getInt("UB_UUP_TIME") : null);
				empmonvstatbean.setUbLlwTime((null != rs.getString("UB_LLW_TIME"))? rs.getInt("UB_LLW_TIME") : null);
				empmonvstatbean.setUbUpTime((null != rs.getString("UB_UP_TIME"))? rs.getInt("UB_UP_TIME") : null);
				empmonvstatbean.setUbLowTime((null != rs.getString("UB_LOW_TIME"))? rs.getInt("UB_LOW_TIME") : null);
				empmonvstatbean.setUbNmlTime((null != rs.getString("UB_NML_TIME"))? rs.getInt("UB_NML_TIME") : null);
				empmonvstatbean.setUcUupTime((null != rs.getString("UC_UUP_TIME"))? rs.getInt("UC_UUP_TIME") : null);
				empmonvstatbean.setUcLlwTime((null != rs.getString("UC_LLW_TIME"))? rs.getInt("UC_LLW_TIME") : null);
				empmonvstatbean.setUcUpTime((null != rs.getString("UC_UP_TIME"))? rs.getInt("UC_UP_TIME") : null);
				empmonvstatbean.setUcLowTime((null != rs.getString("UC_LOW_TIME"))? rs.getInt("UC_LOW_TIME") : null);
				empmonvstatbean.setUcNmlTime((null != rs.getString("UC_NML_TIME"))? rs.getInt("UC_NML_TIME") : null);
				empmonvstatbean.setUaMax((null != rs.getString("UA_MAX"))? rs.getDouble("UA_MAX") : null);
				empmonvstatbean.setUaMaxTime(timeStampToStr(rs.getTimestamp("UA_MAX_TIME")));
				empmonvstatbean.setUaMin((null != rs.getString("UA_MIN"))? rs.getDouble("UA_MIN") : null);
				empmonvstatbean.setUaMinTime(timeStampToStr(rs.getTimestamp("UA_MIN_TIME")));
				empmonvstatbean.setUbMax((null != rs.getString("UB_MAX"))? rs.getDouble("UB_MAX") : null);
				empmonvstatbean.setUbMaxTime(timeStampToStr(rs.getTimestamp("UB_MAX_TIME")));
				empmonvstatbean.setUbMin((null != rs.getString("UB_MIN"))? rs.getDouble("UB_MIN") : null);
				empmonvstatbean.setUbMinTime(timeStampToStr(rs.getTimestamp("UB_MIN_TIME")));
				empmonvstatbean.setUcMax((null != rs.getString("UC_MAX"))? rs.getDouble("UC_MAX") : null);
				empmonvstatbean.setUcMaxTime(timeStampToStr(rs.getTimestamp("UC_MAX_TIME")));
				empmonvstatbean.setUcMin((null != rs.getString("UC_MIN"))? rs.getDouble("UC_MIN") : null);
				empmonvstatbean.setUcMinTime(timeStampToStr(rs.getTimestamp("UC_MIN_TIME")));
				empmonvstatbean.setUaAverage((null != rs.getString("UA_AVERAGE"))? rs.getDouble("UA_AVERAGE") : null);
				empmonvstatbean.setUbAverage((null != rs.getString("UB_AVERAGE"))? rs.getDouble("UB_AVERAGE") : null);
				empmonvstatbean.setUcAverage((null != rs.getString("UC_AVERAGE"))? rs.getDouble("UC_AVERAGE") : null);
				empmonvstatbean.setMpNo(rs.getString("MP_NO"));
				empmonvstatbean.setMpName(rs.getString("MP_NAME"));
				empmonvstatbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return empmonvstatbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpRdayDemandListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpRdayDemandBean emprdaydemandbean = new EMpRdayDemandBean();
			try {
				emprdaydemandbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				emprdaydemandbean.setAssetNo(rs.getString("ASSET_NO"));
				emprdaydemandbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				emprdaydemandbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				emprdaydemandbean.setPapDemand((null != rs.getString("PAP_DEMAND"))? rs.getDouble("PAP_DEMAND") : null);
				emprdaydemandbean.setPapDemandTime(timeStampToStr(rs.getTimestamp("PAP_DEMAND_TIME")));
				emprdaydemandbean.setPrpDemand((null != rs.getString("PRP_DEMAND"))? rs.getDouble("PRP_DEMAND") : null);
				emprdaydemandbean.setPrpDemandTime(timeStampToStr(rs.getTimestamp("PRP_DEMAND_TIME")));
				emprdaydemandbean.setRapDemand((null != rs.getString("RAP_DEMAND"))? rs.getDouble("RAP_DEMAND") : null);
				emprdaydemandbean.setRapDemandTime(timeStampToStr(rs.getTimestamp("RAP_DEMAND_TIME")));
				emprdaydemandbean.setRrpDemand((null != rs.getString("RRP_DEMAND"))? rs.getDouble("RRP_DEMAND") : null);
				emprdaydemandbean.setRrpDemandTime(timeStampToStr(rs.getTimestamp("RRP_DEMAND_TIME")));
				emprdaydemandbean.setMpNo(rs.getString("MP_NO"));
				emprdaydemandbean.setMpName(rs.getString("MP_NAME"));
				emprdaydemandbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return emprdaydemandbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpRdayReadListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpRdayReadBean emprdayreadbean = new EMpRdayReadBean();
			try {
				emprdayreadbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				emprdayreadbean.setAssetNo(rs.getString("ASSET_NO"));
				emprdayreadbean.setDataDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("DATA_DATE")));
				emprdayreadbean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				emprdayreadbean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				emprdayreadbean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				emprdayreadbean.setPapR((null != rs.getString("PAP_R"))? rs.getDouble("PAP_R") : null);
				emprdayreadbean.setPapR1((null != rs.getString("PAP_R1"))? rs.getDouble("PAP_R1") : null);
				emprdayreadbean.setPapR2((null != rs.getString("PAP_R2"))? rs.getDouble("PAP_R2") : null);
				emprdayreadbean.setPapR3((null != rs.getString("PAP_R3"))? rs.getDouble("PAP_R3") : null);
				emprdayreadbean.setPapR4((null != rs.getString("PAP_R4"))? rs.getDouble("PAP_R4") : null);
				emprdayreadbean.setPrpR((null != rs.getString("PRP_R"))? rs.getDouble("PRP_R") : null);
				emprdayreadbean.setPrpR1((null != rs.getString("PRP_R1"))? rs.getDouble("PRP_R1") : null);
				emprdayreadbean.setPrpR2((null != rs.getString("PRP_R2"))? rs.getDouble("PRP_R2") : null);
				emprdayreadbean.setPrpR3((null != rs.getString("PRP_R3"))? rs.getDouble("PRP_R3") : null);
				emprdayreadbean.setPrpR4((null != rs.getString("PRP_R4"))? rs.getDouble("PRP_R4") : null);
				emprdayreadbean.setRapR((null != rs.getString("RAP_R"))? rs.getDouble("RAP_R") : null);
				emprdayreadbean.setRapR1((null != rs.getString("RAP_R1"))? rs.getDouble("RAP_R1") : null);
				emprdayreadbean.setRapR2((null != rs.getString("RAP_R2"))? rs.getDouble("RAP_R2") : null);
				emprdayreadbean.setRapR3((null != rs.getString("RAP_R3"))? rs.getDouble("RAP_R3") : null);
				emprdayreadbean.setRapR4((null != rs.getString("RAP_R4"))? rs.getDouble("RAP_R4") : null);
				emprdayreadbean.setRrpR((null != rs.getString("RRP_R"))? rs.getDouble("RRP_R") : null);
				emprdayreadbean.setRrpR1((null != rs.getString("RRP_R1"))? rs.getDouble("RRP_R1") : null);
				emprdayreadbean.setRrpR2((null != rs.getString("RRP_R2"))? rs.getDouble("RRP_R2") : null);
				emprdayreadbean.setRrpR3((null != rs.getString("RRP_R3"))? rs.getDouble("RRP_R3") : null);
				emprdayreadbean.setRrpR4((null != rs.getString("RRP_R4"))? rs.getDouble("RRP_R4") : null);
				emprdayreadbean.setRp1R((null != rs.getString("RP1_R"))? rs.getDouble("RP1_R") : null);
				emprdayreadbean.setRp4R((null != rs.getString("RP4_R"))? rs.getDouble("RP4_R") : null);
				emprdayreadbean.setRp2R((null != rs.getString("RP2_R"))? rs.getDouble("RP2_R") : null);
				emprdayreadbean.setRp3R((null != rs.getString("RP3_R"))? rs.getDouble("RP3_R") : null);
				emprdayreadbean.setMpNo(rs.getString("MP_NO"));
				emprdayreadbean.setMpName(rs.getString("MP_NAME"));
				emprdayreadbean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return emprdayreadbean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpRealDataReadListMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpReadBean bean = new EMpReadBean();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setDataDate(timeStampToStr(rs.getTimestamp("DATA_TIME")));
				bean.setColTime(timeStampToStr(rs.getTimestamp("COL_TIME")));
				bean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				bean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				bean.setPapR((null != rs.getString("PAP_R"))? rs.getDouble("PAP_R") : null);
				bean.setPapR1((null != rs.getString("PAP_R1"))? rs.getDouble("PAP_R1") : null);
				bean.setPapR2((null != rs.getString("PAP_R2"))? rs.getDouble("PAP_R2") : null);
				bean.setPapR3((null != rs.getString("PAP_R3"))? rs.getDouble("PAP_R3") : null);
				bean.setPapR4((null != rs.getString("PAP_R4"))? rs.getDouble("PAP_R4") : null);
				bean.setPrpR((null != rs.getString("PRP_R"))? rs.getDouble("PRP_R") : null);
				bean.setPrpR1((null != rs.getString("PRP_R1"))? rs.getDouble("PRP_R1") : null);
				bean.setPrpR2((null != rs.getString("PRP_R2"))? rs.getDouble("PRP_R2") : null);
				bean.setPrpR3((null != rs.getString("PRP_R3"))? rs.getDouble("PRP_R3") : null);
				bean.setPrpR4((null != rs.getString("PRP_R4"))? rs.getDouble("PRP_R4") : null);
				bean.setRapR((null != rs.getString("RAP_R"))? rs.getDouble("RAP_R") : null);
				bean.setRapR1((null != rs.getString("RAP_R1"))? rs.getDouble("RAP_R1") : null);
				bean.setRapR2((null != rs.getString("RAP_R2"))? rs.getDouble("RAP_R2") : null);
				bean.setRapR3((null != rs.getString("RAP_R3"))? rs.getDouble("RAP_R3") : null);
				bean.setRapR4((null != rs.getString("RAP_R4"))? rs.getDouble("RAP_R4") : null);
				bean.setRrpR((null != rs.getString("RRP_R"))? rs.getDouble("RRP_R") : null);
				bean.setRrpR1((null != rs.getString("RRP_R1"))? rs.getDouble("RRP_R1") : null);
				bean.setRrpR2((null != rs.getString("RRP_R2"))? rs.getDouble("RRP_R2") : null);
				bean.setRrpR3((null != rs.getString("RRP_R3"))? rs.getDouble("RRP_R3") : null);
				bean.setRrpR4((null != rs.getString("RRP_R4"))? rs.getDouble("RRP_R4") : null);
				bean.setRp1R((null != rs.getString("RP1_R"))? rs.getDouble("RP1_R") : null);
				bean.setRp4R((null != rs.getString("RP4_R"))? rs.getDouble("RP4_R") : null);
				bean.setRp2R((null != rs.getString("RP2_R"))? rs.getDouble("RP2_R") : null);
				bean.setRp3R((null != rs.getString("RP3_R"))? rs.getDouble("RP3_R") : null);
				bean.setAssetNo(rs.getString("ASSET_NO"));
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpRealCurveDataListMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpCurveBean bean = new EMpCurveBean();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setAssetNo(rs.getString("ASSET_NO"));
				bean.setDataTime(timeStampToStr(rs.getTimestamp("DATA_TIME")));
				bean.setCt((null != rs.getString("CT"))? rs.getDouble("CT") : null);
				bean.setPt((null != rs.getString("PT"))? rs.getDouble("PT") : null);
				bean.setMark(rs.getBoolean("MARK"));
				bean.setUa((null != rs.getString("UA"))? rs.getDouble("UA") : null);
				bean.setUb((null != rs.getString("UB"))? rs.getDouble("UB") : null);
				bean.setUc((null != rs.getString("UC"))? rs.getDouble("UC") : null);
				bean.setIa((null != rs.getString("IA"))? rs.getDouble("IA") : null);
				bean.setIb((null != rs.getString("IB"))? rs.getDouble("IB") : null);
				bean.setIc((null != rs.getString("IC"))? rs.getDouble("IC") : null);
				bean.setI0((null != rs.getString("I0"))? rs.getDouble("I0") : null);
			    bean.setP((null != rs.getString("P"))? rs.getDouble("P") : null);
			    bean.setPa((null != rs.getString("PA"))? rs.getDouble("PA") : null);
			    bean.setPb((null != rs.getString("PB"))? rs.getDouble("PB") : null);
			    bean.setPc((null != rs.getString("PC"))? rs.getDouble("PC") : null);
			    bean.setQ((null != rs.getString("Q"))? rs.getDouble("Q") : null);
			    bean.setQa((null != rs.getString("QA"))? rs.getDouble("QA") : null);
			    bean.setQb((null != rs.getString("QB"))? rs.getDouble("QB") : null);
			    bean.setQc((null != rs.getString("QC"))? rs.getDouble("QC") : null);
			    bean.setLoadMark((null != rs.getString("LOAD_MARK"))? rs.getByte("LOAD_MARK") : null);
			    bean.setAnUaba((null != rs.getString("AN_UABA"))? rs.getDouble("AN_UABA") : null);
			    bean.setAnUb((null != rs.getString("AN_UB"))? rs.getDouble("AN_UB") : null);
			    bean.setAnUcbc((null != rs.getString("AN_UCBC"))? rs.getDouble("AN_UCBC") : null);
			    bean.setAnIa((null != rs.getString("AN_IA"))? rs.getDouble("AN_IA") : null);
			    bean.setAnIb((null != rs.getString("AN_IB"))? rs.getDouble("AN_IB") : null);
			    bean.setAnIc((null != rs.getString("AN_IC"))? rs.getDouble("AN_IC") : null);
			    bean.setPapE((null != rs.getString("PAP_E"))? rs.getDouble("PAP_E") : null);
			    bean.setPrpE((null != rs.getString("PRP_E"))? rs.getDouble("PRP_E") : null);
			    bean.setRapE((null != rs.getString("RAP_E"))? rs.getDouble("RAP_E") : null);
			    bean.setRrpE((null != rs.getString("RRP_E"))? rs.getDouble("RRP_E") : null);
			    bean.setPapR((null != rs.getString("PAP_R"))? rs.getDouble("PAP_R") : null);
			    bean.setPrpR((null != rs.getString("PRP_R"))? rs.getDouble("PRP_R") : null);
			    bean.setRapR((null != rs.getString("RAP_R"))? rs.getDouble("RAP_R") : null);
			    bean.setRrpR((null != rs.getString("RRP_R"))? rs.getDouble("RRP_R") : null);
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				return bean;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class groupRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			GroupNameBean group = new GroupNameBean();
			try {
				group.setGroupName(rs.getString("GROUP_NAME"));
				group.setGroupNo(rs.getString("GROUP_NO"));
				return group;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	class eMpConsTmnlMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			EMpConsTmnlBean param = new EMpConsTmnlBean();
			try {
				param.setTmnlAssetNo(null != rs.getString("cis_asset_no") ? rs.getString("cis_asset_no") : "");
				param.setTmnlAssetNumber(rs.getString("TMNL_ASSET_NO"));
				return param;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
    /**
	 * date转化为String，如果有错返回当日信息
	 * @param date
	 * @return String 
	 */
	private String dateToString(Date date){
		return DateUtil.dateToString(date);
	}
	
    /**
	 * date转化为String，主要是返回查询日的所在月份的第一天，如果有错返回当月首日
	 * @param date
	 * @return String 
	 */
    private String dateToStr(Date date){
    	return DateUtil.fristDayByDate(date);
    }
    
    /**
	 * 得到输入月份的下个月第一天
	 * @param date
	 * @return String 
	 */
    private String getNextMonthFirstDay(String date){
    	return DateUtil.getNextMonthFirstDay(date);
    }
    
    /**
	 * timeStamp转化为String
	 * @param date
	 * @return String 
	 */
    private String timeStampToStr(Timestamp time){
    	return DateUtil.timeStampToStr(time);
    }    
}
