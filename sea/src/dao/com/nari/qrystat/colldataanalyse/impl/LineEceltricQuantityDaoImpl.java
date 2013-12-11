package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineEcelUserDto;
import com.nari.qrystat.colldataanalyse.LineEceltricQuantityDao;
import com.nari.qrystat.colldataanalyse.LineEceltricQuantityDto;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 线路电量查询dao类
 * @author ChunMingLi
 * @since  2010-6-23
 *
 */
public class LineEceltricQuantityDaoImpl extends JdbcBaseDAOImpl implements
		LineEceltricQuantityDao {
	/**
	 * 左边树加载线路电量
	 * 
	 * @param nodeType
	 *            节点类型
	 * @param nodeValue
	 *            节点值
	 * @param userInfo
	 *            用户信息
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @param startDate
	 *            查询开始时间
	 * @param endDate
	 *            查询结束时间
	 * @return page
	 * @throws DBAccessException
	 */
	public Page<LineEceltricQuantityDto> queryPageLineEcel(String nodeType,
			String nodeValue, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) {
		StringBuffer sb = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(nodeValue + "%");
		sb.append("SELECT A.ORG_NO,\n");
		sb.append("		  A.PAP_E,\n");
		sb.append("		  A.PAP_E1,\n");
		sb.append("		  A.PAP_E2,\n");
		sb.append("		  A.PAP_E3,\n");
		sb.append("		  A.PAP_E4,\n");
		sb.append("		  A.MAX_P,\n");
		sb.append("		  A.MAX_P_TIME,\n");
		sb.append("		  A.MIN_P,\n");
		sb.append("		  A.MIN_P_TIME,\n");
		sb.append("		  A.AVG_P,\n");
		sb.append("		  A.AVG_Q,\n");
		sb.append("		  A.MAX_Q,\n");
		sb.append("		  A.MAX_Q_TIME,\n");
		sb.append("		  A.MIN_Q,\n");
		sb.append("		  A.MIN_Q_TIME,\n");
		sb.append("       A.STAT_DATE,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       A.LINE_ID,\n");
		sb.append("       G.LINE_NAME\n");
		sb.append("  FROM  O_ORG O, A_LINE_STAT_D A, G_LINE G \n");
//		if ("cgp".equals(nodeType)) {
//			sb.append(",        T_TMNL_GROUP_DETAIL G\n");
//		} else if ("ugp".equals(nodeType)) {
//			sb.append(",        R_USER_GROUP_DETAIL G\n");
//		}
		sb.append("WHERE \n");
		if ("org".equals(nodeType)) {
			sb.append("   A.ORG_NO like ?");
		} else if ("line".equals(nodeType)) {
			sb.append("   A.LINE_ID like ?");
		} else {
			return null;
		}
		sb.append("    AND EXISTS (SELECT 1 \n");
		sb.append("            FROM P_ACCESS_ORG P_A, P_SYS_USER P_U \n");
		sb.append("            WHERE P_A.STAFF_NO = P_U.STAFF_NO \n");
		sb.append("            AND P_A.ORG_NO =  O.ORG_NO \n");
		sb.append("            AND P_A.STAFF_NO = ?  ) \n");
		paraList.add(userInfo.getStaffNo());
		 
		 
		
		   
//		else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
//			sb.append("   G.CONS_NO = C.CONS_NO\n");
//			sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
//			sb.append("   AND G.GROUP_NO like ? ");
//			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
//			paraList.add(userInfo.getStaffNo());
//			paraList.add(userInfo.getStaffNo());
//			paraList.add(userInfo.getStaffNo());
//		} else if ("usr".equals(nodeType)) {
//			sb.append("   TE.TMNL_ASSET_NO like ?");
//		}
		paraList.add(startDate);
		paraList.add(endDate);
		sb.append("  AND   A.ORG_NO = O.ORG_NO\n");
		sb.append("   AND A.LINE_ID = G.LINE_ID\n");
		sb.append("AND  A.STAT_DATE BETWEEN ? AND ?");
		
		logger.debug(sb.toString());
		return this.pagingFind(sb.toString(), start, limit, new LineRowMap(),paraList.toArray());
	}
	
	/**
	 * 
	 * 线路电量映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-6-23
	 *
	 */
	class LineRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LineEceltricQuantityDto lineEcelDto = new LineEceltricQuantityDto();
			try {
				lineEcelDto.setOrgNo(rs.getString("ORG_NO"));
				lineEcelDto.setOrgName(rs.getString("ORG_NAME"));
				lineEcelDto.setLineId(rs.getString("LINE_ID"));
				lineEcelDto.setLineName(rs.getString("LINE_NAME"));
				lineEcelDto.setStatDate(rs.getDate("STAT_DATE"));
				lineEcelDto.setPap_e(rs.getInt("PAP_E"));
				lineEcelDto.setPap_e1(rs.getInt("PAP_E1"));
				lineEcelDto.setPap_e2(rs.getInt("PAP_E2"));
				lineEcelDto.setPap_e3(rs.getInt("PAP_E3"));
				lineEcelDto.setPap_e4(rs.getInt("PAP_E4"));
				lineEcelDto.setMax_p(rs.getInt("MAX_P"));
				lineEcelDto.setMax_p_time(rs.getDate("MAX_P_TIME"));
				lineEcelDto.setMin_p(rs.getInt("MIN_P"));
				lineEcelDto.setMin_p_time(rs.getDate("MIN_P_TIME"));
				lineEcelDto.setMax_q(rs.getInt("MAX_Q"));
				lineEcelDto.setMax_q_time(rs.getDate("MAX_Q_TIME"));
				lineEcelDto.setMin_q(rs.getInt("MIN_Q"));
				lineEcelDto.setMin_q_time(rs.getDate("MIN_Q_TIME"));
				lineEcelDto.setAvg_p(rs.getInt("AVG_P"));
				lineEcelDto.setAvg_q(rs.getInt("AVG_Q"));
				return lineEcelDto;

			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * 查询线路用户列表
	 * 
	 * @param lineId  线路ID
	 * @param userInfo
	 *            用户信息
	 * @param start
	 *            页开始数量
	 * @param limit
	 *            页限制数量
	 * @param startDate
	 *            查询开始时间
	 * @param endDate
	 *            查询结束时间
	 * @return    page
	 */
	public Page<LineEcelUserDto> queryPageLineEcelUser(String lineId, PSysUser userInfo, long start, long limit,Date startDate, Date endDate) {
		StringBuffer sb = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		sb.append("SELECT O.ORG_NO,   \n");
		sb.append("   O.ORG_NAME,    \n");
		sb.append("   C.CONS_NO,    \n");
		sb.append("   C.CONS_NAME,    \n");
		sb.append("    A.TMNL_ASSET_NO,    \n");
		sb.append("    A.ASSET_NO,    \n");
		
		sb.append("    A.MP_TYPE,    \n");
		sb.append("    A.STAT_DATE,    \n");
		sb.append("    A.PAP_E,    \n");
		sb.append("    A.PAP_E1,    \n");
		sb.append("    A.PAP_E2,    \n");
		sb.append("    A.PAP_E3,    \n");
		sb.append("    A.PAP_E4,    \n");
		sb.append("    A.MAX_P,    \n");
		sb.append("    A.MAX_P_TIME,    \n");
		sb.append("    A.MIN_P,    \n");
		sb.append("    A.MIN_P_TIME,   \n");
		sb.append("    A.MAX_Q,    \n");
		sb.append("    A.MAX_Q_TIME,    \n");
		sb.append("    A.MIN_Q,    \n");
		sb.append("    A.MIN_Q_TIME,    \n");
		sb.append("    A.AVG_P,    \n");
		sb.append("    A.AVG_Q,    \n");
		sb.append("    VW.DATA_SRC_NO,   \n");
		sb.append("    VW.DATA_SRC   \n");
		
		sb.append("FROM C_CONS C, O_ORG O, A_CONS_STAT_D A , VW_DATA_SRC VW   \n");
		sb.append("WHERE C.ORG_NO = O.ORG_NO    \n");
		sb.append("AND C.CONS_NO = A.CONS_NO    \n");
		sb.append("AND A.DATA_SRC = VW.DATA_SRC_NO    \n");
		sb.append("AND C.LINE_ID = ?   \n");
		paraList.add(lineId);
		sb.append("AND  A.STAT_DATE BETWEEN ? AND ? \n");
		paraList.add(startDate);
		paraList.add(endDate);
		sb.append("ORDER BY A.CONS_NO, A.TMNL_ASSET_NO \n");
		logger.debug(sb.toString());
		return this.pagingFind(sb.toString(), start, limit, new LineUserRowMap(),paraList.toArray());
	}
	
	/**
	 * 
	 * 线路电量用户映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-6-25
	 *
	 */
	class LineUserRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LineEcelUserDto lineEcelUserDto = new LineEcelUserDto();
			try {
				lineEcelUserDto.setOrgNo(rs.getString("ORG_NO"));
				lineEcelUserDto.setOrgName(rs.getString("ORG_NAME"));
				lineEcelUserDto.setAssetNo(rs.getString("ASSET_NO"));
				lineEcelUserDto.setConsNo(rs.getString("CONS_NO"));
				lineEcelUserDto.setDataSrc(rs.getString("DATA_SRC"));
				lineEcelUserDto.setDataSrcNo(rs.getString("DATA_SRC_NO"));
				lineEcelUserDto.setConsName(rs.getString("CONS_Name"));
				lineEcelUserDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				lineEcelUserDto.setMpType(rs.getInt("MP_TYPE"));
				
				lineEcelUserDto.setStatDate(rs.getDate("STAT_DATE"));
				lineEcelUserDto.setPap_e(rs.getInt("PAP_E"));
				lineEcelUserDto.setPap_e1(rs.getInt("PAP_E1"));
				lineEcelUserDto.setPap_e2(rs.getInt("PAP_E2"));
				lineEcelUserDto.setPap_e3(rs.getInt("PAP_E3"));
				lineEcelUserDto.setPap_e4(rs.getInt("PAP_E4"));
				lineEcelUserDto.setMax_p(rs.getInt("MAX_P"));
				lineEcelUserDto.setMax_p_time(rs.getDate("MAX_P_TIME"));
				lineEcelUserDto.setMin_p(rs.getInt("MIN_P"));
				lineEcelUserDto.setMin_p_time(rs.getDate("MIN_P_TIME"));
				lineEcelUserDto.setMax_q(rs.getInt("MAX_Q"));
				lineEcelUserDto.setMax_q_time(rs.getDate("MAX_Q_TIME"));
				lineEcelUserDto.setMin_q(rs.getInt("MIN_Q"));
				lineEcelUserDto.setMin_q_time(rs.getDate("MIN_Q_TIME"));
				lineEcelUserDto.setAvg_p(rs.getInt("AVG_P"));
				lineEcelUserDto.setAvg_q(rs.getInt("AVG_Q"));
				return lineEcelUserDto;

			} catch (Exception e) {
				return null;
			}
		}
	}
}
