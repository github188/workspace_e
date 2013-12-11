/**
 * 
 */
package com.nari.advapp.statanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.statanalyse.ChargeStatisAnalyseDTO;
import com.nari.advapp.statanalyse.ChargeStatisAnalyseDao;
import com.nari.advapp.statanalyse.StatAnalyseCurveDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 负荷统计分析DAO
 * @author ChunMingLi
 * @since 2010-8-7
 *
 */
public class ChargeStatisAnalyseDaoImpl extends JdbcBaseDAOImpl implements
		ChargeStatisAnalyseDao {

	/* (non-Javadoc)
	 * @see com.nari.advapp.statanalyse.ChargeStatisAnalyseDao#findChargeStatisAnalyse(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatAnalyseCurveDto> findChargeStatisAnalyseCurve(int timeFlag,String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		if(orgType.equals("02")){
			sql.append("SELECT A.STAT_DATE, SUM(DECODE(A.ORG_TYPE, '03', A.AVG_P, 0)) AVG_P   \n");
			sql.append("FROM    \n");
		}else{
			sql.append("SELECT A.STAT_DATE, A.AVG_P   \n");
			sql.append("FROM    \n");
		}
		
		//组别表
		String r_table = "";
		//表关联约束
		String d_rule = "";
		if (nodeType.equals("ugp")) {
			r_table = "R_USER_GROUP_DETAIL R,";
			d_rule = "AND C.CONS_NO = R.CONS_NO";
		}
		if (nodeType.equals("cgp")) {
			r_table = "T_TMNL_GROUP_DETAIL D,";
			d_rule = "AND C.CONS_NO = D.CONS_NO";
		}
		
		if (nodeType.equals("org")) {
			if(timeFlag == 1){
				//单位按日查询
				sql.append("A_ORG_STAT_D A   \n") ;
			}else if(timeFlag == 2 || timeFlag == 3){
				//单位按月、年查询
				sql.append("A_ORG_STAT_M A   \n") ;
			}
		}
		if (nodeType.equals("usr")) {
			if(timeFlag == 1){
				//用户按日查询
				sql.append("A_CONS_STAT_D A   \n") ;
			}else if(timeFlag == 2 || timeFlag == 3){
				//用户按月、年查询
				sql.append("A_CONS_STAT_M A   \n") ;
			}
		}
		sql.append(",O_ORG O    \n");
		sql.append("WHERE A.ORG_NO = O.ORG_NO    \n");
		
		if(timeFlag == 1){
			//按日查询
			sql.append("AND A.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')    \n");
			sql.append("AND A.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd')    \n");
			parms.add(startDate);
			parms.add(endDate);
		}else if(timeFlag == 2 ){
			//单位按月、年查询
			sql.append("AND TO_CHAR( A.STAT_DATE , 'YYYY-MM') = ?    \n");
			parms.add(startDate);
		}else if( timeFlag == 3){
			sql.append("AND TO_CHAR( A.STAT_DATE , 'YYYY') = ?    \n");
			parms.add(startDate);
		}
		
		
		if (nodeType.equals("org")) {
			//省公司操作员不需要条件 市级以下添加查询条件
			if(!orgType.equals("02")){
				sql.append(" AND A.ORG_NO = ? \n");
				parms.add(nodeValue);
			}else {
				//默认省级查询不需要查询条件
				sql.append("  AND A.ORG_TYPE = '03' \n");
			}
//		} else if (nodeType.equals("sub")) {
//			sql.append(" AND C.SUBS_ID=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("line")) {
//			sql.append(" AND C.LINE_ID=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("ugp")) {
//			sql.append(" AND R.GROUP_NO=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("cgp")) {
//			sql.append(" AND D.GROUP_NO=? \n");
//			parms.add(nodeValue);
		} else if (nodeType.equals("usr")) {
			sql.append(" AND A.CONS_NO =? \n");
			parms.add(nodeValue);
		} else {
			return null;
		}
		 
		 sql.append("AND A.AVG_P IS NOT NULL    \n");
		if(orgType.equals("02")){
			sql.append("GROUP BY A.STAT_DATE    \n");
		}
		sql.append("ORDER BY A.STAT_DATE   \n");
		logger.debug(sql.toString());
		return super.getJdbcTemplate().query(sql.toString(), parms.toArray(), new ChargeStatRowMap());
	}

	/* (non-Javadoc)
	 * @see com.nari.advapp.statanalyse.ChargeStatisAnalyseDao#findCollectionPointDemandMonth(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date, java.util.Date, java.util.Date)
	 */
	@Override
	public Page<ChargeStatisAnalyseDTO> findChargeStatisAnalyseList(int timeFlag,String orgType, String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		
		sql.append("SELECT O.ORG_NO,O.ORG_NAME, C.CONS_NO, C.CONS_NAME,C.CONTRACT_CAP,  \n");
		sql.append("  A.STAT_DATE, A.MAX_P, --负荷峰值  \n");
		sql.append(" A.MAX_P_TIME, --负荷峰值发生时间   \n");
		sql.append(" A.MIN_P, --负荷谷值  \n");
		sql.append("  A.MIN_P_TIME, --负荷谷值发生时间   \n");
		sql.append("  A.AVG_P --平均负荷   \n");
		sql.append("   --负荷率   负荷率=平均负荷/峰值  \n");
		sql.append("   --峰谷差率  峰谷差率=峰值-谷值/峰值 \n");
		sql.append(" FROM  O_ORG O , C_CONS C   \n");
//		if (nodeType.equals("org")) {
//			if(timeFlag == 1){
//				//单位按日查询
//				sql.append(" , A_ORG_STAT_D A   \n") ;
//			}else if(timeFlag == 2 || timeFlag == 3){
//				//单位按月、年查询
//				sql.append(" , A_ORG_STAT_M A   \n") ;
//			}
//		}
//		if (nodeType.equals("usr")) {
			if(timeFlag == 1){
				//用户按日查询
				sql.append(" , A_CONS_STAT_D A   \n") ;
			}else if(timeFlag == 2 || timeFlag == 3){
				//用户按月、年查询
				sql.append(" , A_CONS_STAT_M A   \n") ;
			}
//		}
		sql.append("WHERE    \n");
		sql.append("   O.ORG_NO = C.ORG_NO  \n");
		sql.append("   AND C.CONS_NO = A.CONS_NO  \n");
		
		if(timeFlag == 1){
			//按日查询
			sql.append(" AND A.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')    \n");
			sql.append("AND A.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd')    \n");
			parms.add(startDate);
			parms.add(endDate);
		}else if(timeFlag == 2 ){
			//单位按月、年查询
			sql.append(" AND TO_CHAR( A.STAT_DATE , 'YYYY-MM') = ?    \n");
			parms.add(startDate);
		}else if( timeFlag == 3){
			sql.append(" AND TO_CHAR( A.STAT_DATE , 'YYYY') = ?      \n");
			parms.add(startDate);
		}
		
		//组别表
		String r_table = "";
		//表关联约束
		String d_rule = "";
		if (nodeType.equals("ugp")) {
			r_table = "R_USER_GROUP_DETAIL R,";
			d_rule = "AND C.CONS_NO = R.CONS_NO";
		}
		if (nodeType.equals("cgp")) {
			r_table = "T_TMNL_GROUP_DETAIL D,";
			d_rule = "AND C.CONS_NO = D.CONS_NO";
		}
		
		
		if (nodeType.equals("org")) {
			//省公司操作员不需要条件 市级以下添加查询条件
			if(!orgType.equals("02")){
				sql.append(" AND A.ORG_NO like ?||'%'  \n");
				parms.add(nodeValue);
			}else {
				//默认省级查询不需要查询条件
			}
//		} else if (nodeType.equals("sub")) {
//			sql.append(" AND C.SUBS_ID=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("line")) {
//			sql.append(" AND C.LINE_ID=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("ugp")) {
//			sql.append(" AND R.GROUP_NO=? \n");
//			parms.add(nodeValue);
//		} else if (nodeType.equals("cgp")) {
//			sql.append(" AND D.GROUP_NO=? \n");
//			parms.add(nodeValue);
		} else if (nodeType.equals("usr")) {
			sql.append(" AND A.CONS_NO =? \n");
			parms.add(nodeValue);
		} else {
			return null;
		}
		sql.append("    ORDER BY A.STAT_DATE \n");
		logger.debug(sql.toString());
		return this.pagingFind(sql.toString(), start, limit, new ChargeStatAnalyseRowMap() ,parms.toArray());
	}
	
	
	/**
	 * 
	 * 负荷统计分析映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-8-10
	 *
	 */
	class ChargeStatRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			StatAnalyseCurveDto sacDto = new StatAnalyseCurveDto();
			try {
				sacDto.setStatDate(rs.getString("STAT_DATE"));
				sacDto.setCurveDate(rs.getDouble("AVG_P"));
				return sacDto;
			} catch (Exception e) {
				return null;
			}
		}
		
	}
	
	/**
	 * 
	 * 负荷统计分析映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-8-10
	 *
	 */
	class ChargeStatAnalyseRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ChargeStatisAnalyseDTO csaDto = new ChargeStatisAnalyseDTO();
			try {
				csaDto.setOrgNo(rs.getString("ORG_NO"));
				csaDto.setOrgName(rs.getString("ORG_NAME"));
				csaDto.setConsNo(rs.getString("CONS_NO"));
				csaDto.setConsName(rs.getString("CONS_NAME"));
				csaDto.setAvg_p(rs.getDouble("avg_p"));
				csaDto.setStatDate(rs.getString("STAT_DATE"));
				csaDto.setContractCap(rs.getDouble("CONTRACT_CAP"));
				csaDto.setMax_p(rs.getDouble("MAX_P"));//负荷峰值
				csaDto.setMax_p_time(rs.getString("MAX_P_TIME"));//负荷峰值发生时间
				csaDto.setMin_p(rs.getDouble("MIN_P"));//负荷谷值
				csaDto.setMin_p_time(rs.getString("MIN_P_TIME"));//负荷谷值发生时间
				if(csaDto.getMax_p() != 0){
					csaDto.setChargeRate(csaDto.getAvg_p()/csaDto.getMax_p()); // 负荷率=平均负荷/峰值  
					csaDto.setFengGuRate((csaDto.getMax_p() - csaDto.getMin_p()) / csaDto.getMax_p());  // 峰谷差率=峰值-谷值/峰值
				}
				return csaDto;

			} catch (Exception e) {
				return null;
			}
		}

}

}
