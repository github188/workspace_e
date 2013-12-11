package com.nari.advapp.vipconsman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nari.elementsdata.EDataMp;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.vipconsman.VipConsMonitorDataBean;
import com.nari.advapp.vipconsman.VipConsMonitorDataJdbcDao;
import com.nari.advapp.vipconsman.VipConsRealPowerDataBean;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 重点用户监测数据查询Jdbc之Dao实现类
 * @author 姜炜超
 */
public class VipConsMonitorDataJdbcDaoImpl extends JdbcBaseDAOImpl implements VipConsMonitorDataJdbcDao{
	
	//定义日志
	private static final Logger logger = Logger.getLogger(VipConsMonitorDataJdbcDaoImpl.class);
			
	/**
	 * 根据条件查询重点用户信息
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return Page<VipConsMonitorDataBean>
	 */
	public Page<VipConsMonitorDataBean> findVipConsMonitorData(PSysUser pSysUser,
			long start, int limit) throws DBAccessException{
		StringBuffer sql = new StringBuffer();
		List<Object> vipList = new ArrayList<Object>();
		sql.append("SELECT VIP.ORG_NO,VIP.CONS_NO,ORG.ORG_NAME,CONS.CONS_NAME\n")
			.append("FROM C_CONS_VIP VIP, C_CONS CONS, O_ORG ORG\n")
			.append("WHERE VIP.CONS_NO = CONS.CONS_NO\n")
			.append("AND VIP.ORG_NO = ORG.ORG_NO");
		sql.append(" AND VIP.STAFF_NO = ? \n");
		vipList.add(pSysUser.getStaffNo());
		logger.debug(sql);
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsMonitorDataRowMapper(),
					vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("查询重点用户监测数据信息出错！");
			throw e;
		}
	}
	
	/**
	 * 查询某用户下的表计信息,datasrc是作测量点号存在的，取最大的，中间用","隔开，备用，
	 * 以便在将来一个表出现多个测量点查询曲线
	 * @param consNo
	 * @return List<EDataMp>
	 */
	public List<EDataMp> findConsAssetInfo(String consNo) throws DBAccessException{
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return new ArrayList<EDataMp>();
		}
		String sql = 
			"select asset_no, MAX(SUBSTR(SYS_CONNECT_BY_PATH(data_src, ','), 2)) as data_src\n" +
			"  from (select mp.asset_no,\n" + 
			"               mp.data_src,\n" + 
			"               ROW_NUMBER() OVER(PARTITION BY mp.asset_no ORDER BY mp.asset_no, mp.data_src asc) RN\n" + 
			"          from e_data_mp mp\n" + 
			"         where mp.cons_no = ? and mp.area_code = ? \n" + 
			"           and mp.ASSET_NO is not null)\n" + 
			" start with rn = 1\n" + 
			"connect by rn = prior rn + 1\n" + 
			"       and asset_no = prior asset_no\n" + 
			" group by asset_no\n" + 
			" order by asset_no asc";
		logger.debug(sql);
		List<EDataMp> list = null;
		try{
		    list = getJdbcTemplate().query(sql, new Object[] { consNo, areaCode }, new eMpConsAssetMapper());
		}catch(RuntimeException e){
			this.logger.debug("查询重点用户表计信息出错！");
			throw e;
		}
		if(null == list){
			return new ArrayList<EDataMp>();
		}
		return list;
	}
	
	/**
	 * 查询某用户下的表计对应的测量点，目前青海只考虑485口
	 * @param consNo
	 * @param assetNo
	 * @param dataSrc
	 * @return mpId
	 */
	public Long findMpId(String consNo, String assetNo, int dataSrc) throws DBAccessException{
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		Long mpId;
		sql.append("SELECT ED.ID \n")
			.append("FROM  E_DATA_MP ED\n")
			.append("WHERE ED.CONS_NO = ? AND ED.AREA_CODE = ? AND ED.ASSET_NO = ? AND ED.DATA_SRC = ? \n");
		logger.debug(sql);
		try{
			mpId = getJdbcTemplate().queryForLong(sql.toString(), new Object[] { consNo, areaCode, assetNo, 1 });
		}catch(RuntimeException e){
			this.logger.debug("查询重点用户表计测量点信息出错！");
			throw e;
		}
		if(null != mpId){
		    return mpId;
		}else{
			return 0L;
		}
	}
	
	/**
	 * 查询表计查询其挂名下的终端的冻结周期
	 * @param assetNo
	 * @return freezeCycleNum
	 */
	public int findFreezeCycleNum(String assetNo) throws DBAccessException{
		String sql = 
			"select tmnl.freeze_cycle_num\n" +
			"  from e_data_mp mp, r_tmnl_run tmnl\n" + 
			" where mp.tmnl_asset_no = tmnl.tmnl_asset_no\n" + 
			"   and mp.asset_no = ?";
		logger.debug(sql);
		Long freezeCycleNum;
		try{
			freezeCycleNum = getJdbcTemplate().queryForLong(sql, new Object[] { assetNo });
		}catch(RuntimeException e){
			this.logger.debug("查询表计查询其挂名下的终端的冻结周期出错！");
			throw e;
		}
		if(null != freezeCycleNum){
		    return freezeCycleNum.intValue();
		}else{
			return 48;
		}
	}
	
	/**
	 * 按测量点ID和日期查询日测量点功率曲线(冻结数据)
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return EMpPowerCurveBean
	 */
	public EMpPowerCurveBean findEMpPowerCurveByDate(Long mpId, String dataDate, int dataType, String consNo) throws DBAccessException{
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
		List<EMpPowerCurveBean> list = null;
		try {
			list= super.pagingFindList(sql.toString(),
				0, 2, new VipConsStatPowerCurveMapper(), new Object[] { mpId, dataType,
			        dataDate,areaCode });
		}
		catch(RuntimeException e){
			this.logger.debug("查询重点用户测量点冻结功率曲线信息出错！");
			throw e;
		}
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
	 * @return List<VipConsRealPowerDataBean>
	 */
	public List<VipConsRealPowerDataBean> findEMpRealPowerCurveByDate(Long mpId, String dataDate, String consNo) throws DBAccessException{
		String areaCode = super.getETableAreaByConsNo(consNo);
		if(null == areaCode || "".equals(areaCode)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DATA_TIME, P, PA, PB, PC, Q, QA, QB, QC\n")
		    .append(" FROM E_MP_CURVE \n")
			.append("WHERE ID = ? \n")
			.append("AND DATA_TIME >= TO_DATE(?,'YYYY-MM-DD')\n")
			.append("AND DATA_TIME < (TO_DATE(?,'YYYY-MM-DD') + 1)  AND AREA_CODE= ?\n");
		logger.debug(sql.toString());
		List<VipConsRealPowerDataBean> list = null;
		try {
			list = super.getJdbcTemplate().query(sql.toString(), new Object[] { mpId,
					dataDate, dataDate,areaCode}, new VipConsRealPowerCurveMapper());
		}
		catch(RuntimeException e){
			this.logger.debug("查询重点用户测量点实时功率曲线信息出错！");
			throw e;
		}
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 按测量点ID和日期查询日测量点电能量曲线(冻结数据)
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return EMpEnergyCurveBean
	 */
	public EMpEnergyCurveBean findEMpEnergyCurveByDate(Long mpId, String dataDate, int dataType, 
			String consNo) throws DBAccessException{
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
		List<EMpEnergyCurveBean> list = null;
		try {
			list= super.pagingFindList(sql.toString(),
				0, 2, new VipConsEnergyCurveMapper(), new Object[] { mpId, dataType,
			        dataDate,areaCode });
		}
		catch(RuntimeException e){
			this.logger.debug("查询重点用户测量点电能量曲线信息出错！");
			throw e;
		}
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户监测数据
	 */
	class VipConsMonitorDataRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsMonitorDataBean bean = new VipConsMonitorDataBean();
			try {
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsMonitorDataJdbcDaoImpl.this.logger.error("取 VipConsMonitorDataRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户监测数据
	 */
	class eMpConsAssetMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EDataMp bean = new EDataMp();
			try {
				bean.setAssetNo(rs.getString("ASSET_NO"));
				bean.setDataSrc(rs.getString("DATA_SRC"));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsMonitorDataJdbcDaoImpl.this.logger.error("取 eMpConsAssetMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户冻结负荷曲线数据
	 */
	class VipConsStatPowerCurveMapper implements RowMapper {
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
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户实时负荷曲线数据,TRUE表示露点数据
	 */
	class VipConsRealPowerCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsRealPowerDataBean bean = new VipConsRealPowerDataBean();
			try {
				bean.setTime(rs.getString("DATA_TIME"));
				if(null == rs.getString("P") || "".equals(rs.getString("P"))){
					bean.setP(0.0);
					bean.setFlagP(true);
				}else{
					bean.setP(rs.getDouble("P"));
					bean.setFlagP(false);
				}
				if(null == rs.getString("PA") || "".equals(rs.getString("PA"))){
					bean.setpA(0.0);
					bean.setFlagPA(true);
				}else{
					bean.setpA(rs.getDouble("PA"));
					bean.setFlagPA(false);
				}
				if(null == rs.getString("PB") || "".equals(rs.getString("PB"))){
					bean.setpB(0.0);
					bean.setFlagPB(true);
				}else{
					bean.setpB(rs.getDouble("PB"));
					bean.setFlagPB(false);
				}
				if(null == rs.getString("PC") || "".equals(rs.getString("PC"))){
					bean.setpC(0.0);
					bean.setFlagPC(true);
				}else{
					bean.setpC(rs.getDouble("PC"));
					bean.setFlagPC(false);
				}
				if(null == rs.getString("Q") || "".equals(rs.getString("Q"))){
					bean.setQ(0.0);
					bean.setFlagQ(true);
				}else{
					bean.setQ(rs.getDouble("Q"));
					bean.setFlagQ(false);
				}
				if(null == rs.getString("QA") || "".equals(rs.getString("QA"))){
					bean.setqA(0.0);
					bean.setFlagQA(true);
				}else{
					bean.setqA(rs.getDouble("QA"));
					bean.setFlagQA(false);
				}
				if(null == rs.getString("QB") || "".equals(rs.getString("QB"))){
					bean.setqB(0.0);
					bean.setFlagQB(true);
				}else{
					bean.setqB(rs.getDouble("QB"));
					bean.setFlagQB(false);
				}
				if(null == rs.getString("QC") || "".equals(rs.getString("QC"))){
					bean.setqC(0.0);
					bean.setFlagQC(true);
				}else{
					bean.setqC(rs.getDouble("QC"));
					bean.setFlagQC(false);
				}
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户电能量曲线数据
	 */
	class VipConsEnergyCurveMapper implements RowMapper {
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
}
