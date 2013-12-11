package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.LowQualityEvaluate;
import com.nari.baseapp.datagathorman.LowQualityEvaluateDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.impl.AutoSendQueryDaoImpl;
import com.nari.util.NodeTypeUtils;

/**
 * 低压采集质量dao
 * @author YuTao
 *
 */
public class LowQualityEvaluateDaoImpl extends JdbcBaseDAOImpl implements
		LowQualityEvaluateDao {
	Logger logger = Logger.getLogger(AutoSendQueryDaoImpl.class);
	
	/**
	 * 查询线路或供电所 下属台区的抄表情况
	 * @param qulityValue 线路Id或供电站orgNo或者是用户号+终端资产号
	 * @param type line/org
	 * @param startDate 开始时间
	 * @param endDate 结束时间 
	 */
	@SuppressWarnings("unchecked")
	public List<LowQualityEvaluate> findLowQE(PSysUser pSysUser,String qualityValue,String type, String startDate,
			String endDate) {
		if(qualityValue == null) return null;
		String[] values = null;
		
		String areaCode = null;
		if(NodeTypeUtils.NODETYPE_LINE.equals(type)){
			areaCode = this.getETableAreaByLineId(qualityValue);
		}else if(NodeTypeUtils.NODETYPE_ORG.equals(type)){
			areaCode = this.getETableAreaByOrgNo(qualityValue);
		}else if(NodeTypeUtils.NODETYPE_USER.equals(type)){
			values = qualityValue.split(",");
			if(values == null || values.length<2) return null;
			
			areaCode = this.getETableAreaByConsNo(values[0]);
		}
		
		if(areaCode == null || "".equals(areaCode)){
			return null;
		}
		String[] param = null;
		
		//startDate = startDate+" 00:00:00";
		//endDate = endDate+" 23:59:59";
		
		StringBuffer sb=new StringBuffer();
		
		if(NodeTypeUtils.NODETYPE_LINE.equals(type)){
			sb.append("SELECT C.CONS_NO, C.CONS_NAME, TMNL.TMNL_ASSET_NO, COUNT(R.ID) AS SCOUNT\n");
			sb.append("  FROM C_CONS C, E_DATA_MP E, E_MP_DAY_READ R, VW_TMNL_RUN TMNL\n");
			sb.append(" WHERE TMNL.TMNL_ASSET_NO = E.TMNL_ASSET_NO\n");
			sb.append("   AND R.ID = E.ID(+)\n");
			sb.append("   AND R.AREA_CODE = ?\n");
			sb.append("   AND E.AREA_CODE = R.AREA_CODE\n");
			sb.append("   AND R.DATA_DATE(+) BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
			sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
			sb.append("   AND C.CONS_TYPE = 2\n");
			sb.append("   AND C.LINE_ID = ?\n");
			sb.append("   AND C.CONS_NO = TMNL.CONS_NO\n");
			sb.append("   AND EXISTS (SELECT 1\n");
			sb.append("          FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n");
			sb.append("         WHERE PRI_CONS_TYPE.CONS_TYPE = C.CONS_TYPE\n");
			sb.append("           AND PRI_CONS_TYPE.STAFF_NO = ?)\n");
			sb.append("   AND EXISTS (SELECT 1\n");
			sb.append("          FROM P_ACCESS_TMNL_FACTORY PRI_FACTORY\n");
			sb.append("         WHERE PRI_FACTORY.FACTORY_CODE = TMNL.FACTORY_CODE\n");
			sb.append("           AND PRI_FACTORY.STAFF_NO = ?)\n");
			sb.append(" GROUP BY C.CONS_NO, TMNL.TMNL_ASSET_NO, C.CONS_NAME");
			
			param = new String[]{areaCode,startDate,endDate,qualityValue,pSysUser.getStaffNo(),pSysUser.getStaffNo()};
		}else if(NodeTypeUtils.NODETYPE_ORG.equals(type)){
			sb.append("SELECT C.CONS_NO, C.CONS_NAME, TMNL.TMNL_ASSET_NO, COUNT(R.ID) AS SCOUNT\n");
			sb.append("  FROM C_CONS C, E_DATA_MP E, E_MP_DAY_READ R, VW_TMNL_RUN TMNL\n");
			sb.append(" WHERE TMNL.TMNL_ASSET_NO = E.TMNL_ASSET_NO\n");
			sb.append("   AND R.ID = E.ID(+)\n");
			sb.append("   AND R.AREA_CODE = ?\n");
			sb.append("   AND E.AREA_CODE = R.AREA_CODE\n");
			sb.append("   AND R.DATA_DATE(+) BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
			sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
			sb.append("   AND C.CONS_TYPE = 2\n");
			sb.append("   AND C.ORG_NO = ?\n");
			sb.append("   AND C.CONS_NO = TMNL.CONS_NO\n");
			sb.append("   AND EXISTS (SELECT 1\n");
			sb.append("          FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n");
			sb.append("         WHERE PRI_CONS_TYPE.CONS_TYPE = C.CONS_TYPE\n");
			sb.append("           AND PRI_CONS_TYPE.STAFF_NO = ?)\n");
			sb.append("   AND EXISTS (SELECT 1\n");
			sb.append("          FROM P_ACCESS_TMNL_FACTORY PRI_FACTORY\n");
			sb.append("         WHERE PRI_FACTORY.FACTORY_CODE = TMNL.FACTORY_CODE\n");
			sb.append("           AND PRI_FACTORY.STAFF_NO = ?)\n");
			sb.append(" GROUP BY C.CONS_NO, TMNL.TMNL_ASSET_NO, C.CONS_NAME");

			param = new String[]{areaCode,startDate,endDate,qualityValue,pSysUser.getStaffNo(),pSysUser.getStaffNo()};
		}else if(NodeTypeUtils.NODETYPE_USER.equals(type)){
			sb.append("SELECT C.CONS_NO, C.CONS_NAME, T.TMNL_ASSET_NO, COUNT(R.ID) AS SCOUNT\n");
			sb.append("  FROM R_CONS_TMNL_RELA T, C_CONS C, E_DATA_MP E, E_MP_DAY_READ R\n");
			sb.append(" WHERE T.TMNL_ASSET_NO = E.TMNL_ASSET_NO\n");
			sb.append("   AND R.ID(+) = E.ID AND R.AREA_CODE = ?\n");
			sb.append("   AND R.DATA_DATE(+) BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
			sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
			sb.append("   AND C.CONS_NO = T.CONS_NO\n");
			sb.append("   AND E.TMNL_ASSET_NO = ?");
			sb.append("   GROUP BY C.CONS_NO, T.TMNL_ASSET_NO, C.CONS_NAME");
			param = new String[]{areaCode,startDate,endDate,values[1]};
		}else {
			return null;
		}

		String sql=sb.toString();

		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, param, new succLowQualityEvaluateRowMapper());
	}
	
	/**
	 * 查询线路或供电所 下属台区的总表数量
	 * @param qulityValue 线路Id或供电站orgNo
	 * @param type line/org
	 */
	@SuppressWarnings("unchecked")
	public List<LowQualityEvaluate> findTotalLowQE(String qualityValue,String type) {
		if(qualityValue == null) return null;
		String[] param = null;

		StringBuffer sb=new StringBuffer();
		
		if(NodeTypeUtils.NODETYPE_LINE.equals(type)){
			sb.append("SELECT CM.TMNL_ASSET_NO, TMNL.CONS_NO, COUNT(CM.METER_ID) AS TCOUNT\n");
			sb.append("  FROM C_METER CM, VW_TMNL_RUN TMNL, C_CONS CONS\n");
			sb.append(" WHERE CM.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO\n");
			sb.append("   AND CONS.CONS_NO = TMNL.CONS_NO\n");
			sb.append("   AND CONS.CONS_TYPE = 2\n");
			sb.append("   AND CONS.LINE_ID = ?\n");
			sb.append("   AND CM.REG_STATUS = 1\n");
			sb.append(" GROUP BY CM.TMNL_ASSET_NO, TMNL.CONS_NO");

			param = new String[]{qualityValue};
		}else if(NodeTypeUtils.NODETYPE_ORG.equals(type)){
			sb.append("SELECT CM.TMNL_ASSET_NO, TMNL.CONS_NO, COUNT(CM.METER_ID) AS TCOUNT\n");
			sb.append("  FROM C_METER CM, VW_TMNL_RUN TMNL, C_CONS CONS\n");
			sb.append(" WHERE CM.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO\n");
			sb.append("   AND CONS.CONS_NO = TMNL.CONS_NO\n");
			sb.append("   AND CONS.CONS_TYPE = 2\n");
			sb.append("   AND CONS.ORG_NO = ?\n");
			sb.append("   AND CM.REG_STATUS = 1\n");
			sb.append(" GROUP BY CM.TMNL_ASSET_NO, TMNL.CONS_NO");

			param = new String[]{qualityValue};
		}else if (NodeTypeUtils.NODETYPE_USER.equals(type)){
			String[] values = qualityValue.split(",");
			if(values == null || values.length<2) return null;
			param = new String[]{values[1]};
			
			sb.append("SELECT CM.TMNL_ASSET_NO, T.CONS_NO, COUNT(CM.METER_ID) AS tcount\n");
			sb.append("  FROM C_METER CM, R_CONS_TMNL_RELA T\n");
			sb.append(" WHERE CM.TMNL_ASSET_NO = ?");
			sb.append("   AND T.TMNL_ASSET_NO = CM.TMNL_ASSET_NO\n");
			sb.append("	  AND CM.REG_STATUS = 1\n");
			sb.append(" GROUP BY CM.TMNL_ASSET_NO, T.CONS_NO");
		}else if (NodeTypeUtils.NODETYPE_CGP.equals(type)){
			sb.append(", T_TMNL_GROUP_DETAIL G WHERE T.TMNL_ASSET_NO = G.TMNL_ASSET_NO AND G.GROUP_NO = ?");
			sb.append("   AND T.TMNL_ASSET_NO = CM.TMNL_ASSET_NO\n");
			sb.append("	  AND CM.REG_STATUS = 1\n");
			sb.append(" GROUP BY CM.TMNL_ASSET_NO, T.CONS_NO");
		}else if (NodeTypeUtils.NODETYPE_UGP.equals(type)){
			sb.append(", R_USER_GROUP_DETAIL G WHERE T.TMNL_ASSET_NO = G.TMNL_ASSET_NO AND G.GROUP_NO = ?");
			sb.append("   AND T.TMNL_ASSET_NO = CM.TMNL_ASSET_NO\n");
			sb.append("	  AND CM.REG_STATUS = 1\n");
			sb.append(" GROUP BY CM.TMNL_ASSET_NO, T.CONS_NO");
		}else {
			return null;
		}
		String sql=sb.toString();

		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, param, new totaLowQualityEvaluateRowMapper());
	}
	
	/**
	 * 根据终端查询一段时间内每天的采集成功个数
	 * @param pSysUser
	 * @param tmnlAssetNo
	 * @param consNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LowQualityEvaluate> findRateGbyDate(PSysUser pSysUser, String tmnlAssetNo,String consNo,String startDate,String endDate){
		String areaCode = this.getETableAreaByConsNo(consNo);
		
		if(areaCode == null || "".equals(areaCode)){
			return null;
		}
		
		startDate = startDate+" 00:00:00";
		endDate = endDate+" 23:59:59";
		String[] param = {areaCode,startDate,endDate,tmnlAssetNo};
		
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.TMNL_ASSET_NO,\n");
		sb.append("       TO_CHAR(R.DATA_DATE, 'MM-dd') AS STAT_DATE,\n");
		sb.append("       COUNT(R.ID) AS SCOUNT\n");
		sb.append("  FROM R_CONS_TMNL_RELA T, C_CONS C, E_DATA_MP E, E_MP_DAY_READ R\n");
		sb.append(" WHERE T.TMNL_ASSET_NO = E.TMNL_ASSET_NO\n");
		sb.append("   AND R.ID(+) = E.ID\n");
		sb.append("   AND R.AREA_CODE = ?\n");
		sb.append("   AND R.DATA_DATE(+) BETWEEN TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') AND\n");
		sb.append("       TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')\n");
		sb.append("   AND C.CONS_NO = T.CONS_NO\n");
		sb.append("   AND E.TMNL_ASSET_NO = ?\n");
		sb.append(" GROUP BY T.TMNL_ASSET_NO, TO_CHAR(R.DATA_DATE, 'MM-dd')");
		sb.append(" ORDER BY TO_CHAR(R.DATA_DATE, 'MM-dd')");

		String sql=sb.toString();
		logger.debug(sql);
		
		return this.getJdbcTemplate().query(sql, param, new RateGbyDateRowMapper());
	}
	
	class succLowQualityEvaluateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LowQualityEvaluate lowqualityevaluate = new LowQualityEvaluate();
			try {
				lowqualityevaluate.setScount(rs.getLong("SCOUNT"));
				lowqualityevaluate.setConsName(rs.getString("CONS_NAME"));
				lowqualityevaluate
						.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				lowqualityevaluate.setConsNo(rs.getString("CONS_NO"));
				return lowqualityevaluate;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class RateGbyDateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LowQualityEvaluate lowqualityevaluate = new LowQualityEvaluate();
			try {
				lowqualityevaluate.setScount(rs.getLong("SCOUNT"));
				lowqualityevaluate.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				lowqualityevaluate.setStatDate(rs.getString("STAT_DATE"));
				return lowqualityevaluate;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class totaLowQualityEvaluateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LowQualityEvaluate lowqualityevaluate = new LowQualityEvaluate();
			try {
				lowqualityevaluate.setTcount(rs.getLong("TCOUNT"));
				lowqualityevaluate
						.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				lowqualityevaluate.setConsNo(rs.getString("CONS_NO"));
				return lowqualityevaluate;
			} catch (Exception e) {
				return null;
			}
		}
	}
	class FailALowQualityEvaluateRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		LowQualityEvaluate lowqualityevaluate = new LowQualityEvaluate();
		 try { 
		lowqualityevaluate.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
		lowqualityevaluate.setStatDate(rs.getString("STAT_DATE"));
		lowqualityevaluate.setDenizenMp(rs.getString("DENIZEN_MP"));
		return lowqualityevaluate;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	class FailBLowQualityEvaluateRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		LowQualityEvaluate lowqualityevaluate = new LowQualityEvaluate();
		 try { 
		lowqualityevaluate.setRegSn(rs.getInt("REG_SN"));
		lowqualityevaluate.setConsName(rs.getString("CONS_NAME"));
		lowqualityevaluate.setConsNo(rs.getString("CONS_NO"));
		lowqualityevaluate.setAssetNo(rs.getString("ASSET_NO"));
		return lowqualityevaluate;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}

	/**
	 * 查询低压抄表成功与否的字符串
	 * @param data 查询日期
	 * @param tmnlNo 终端资产号
	 */
	@SuppressWarnings("unchecked")
	public List<LowQualityEvaluate> findFailA(String data, String tmnlNo) {

		StringBuffer sb=new StringBuffer();
		sb.append("select r.tmnl_asset_no, r.stat_date, r.denizen_mp\n");
		sb.append("  from r_tmnl_missing_mark r\n");
		sb.append(" where r.stat_date between trunc(TO_DATE(?, 'yyyy-mm-dd')) and trunc(TO_DATE(?, 'yyyy-mm-dd'))\n");
		sb.append("   and r.tmnl_asset_no = ?");
		String sql=sb.toString();
		String[] param = {data,data,tmnlNo};
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, param, new FailALowQualityEvaluateRowMapper());
	}
	/**
	 * 查询低压抄表所有应有数据
	 * @param data 查询日期
	 * @param tmnlNo 终端资产号
	 */
	@SuppressWarnings("unchecked")
	public List<LowQualityEvaluate> findFailB(String data, String tmnlNo) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT M.REG_SN, C.CONS_NAME, C.CONS_NO, M.ASSET_NO\n");
		sb.append("  FROM C_CONS C, C_METER M\n");
		sb.append(" WHERE C.CONS_NO = M.CONS_NO\n");
		sb.append("   AND M.TMNL_ASSET_NO = ?\n");
		sb.append(" ORDER BY M.REG_SN");
		String sql=sb.toString();
		String[] param = {tmnlNo};
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, param, new FailBLowQualityEvaluateRowMapper());
	}
}
