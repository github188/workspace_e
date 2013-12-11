package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.ChannelMonitorConsTypeCollMode;
import com.nari.runman.runstatusman.ChannelMonitorDao;
import com.nari.runman.runstatusman.ChannelMonitorDto;
import com.nari.runman.runstatusman.ChannelMonitorOrgNoDto;
import com.nari.runman.runstatusman.ChannelMonitorTerminalDto;
import com.nari.support.Page;

/**
 * 
 * 通信信道监测DAO
 * 
 * @author ChunMingLi
 * 2010-5-1
 *
 */
public class ChannelMonitorDaoImpl extends JdbcBaseDAOImpl implements
		ChannelMonitorDao {

	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorDao#findChannelMonitor(java.lang.String, java.lang.String, java.lang.String, long, int)
	 */
	@Override
	public Page<ChannelMonitorDto> findChannelMonitor(String onlineType , String statisticsType, String statisticFlag , long start, int limit , PSysUser pSysUser) {
		if(onlineType == null || statisticsType == null || statisticFlag == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT O.ORG_NAME,   \n");
		sb.append("   C.CONS_NO,   \n");
		sb.append("    C.CONS_NAME,   \n");
		sb.append("   R.TERMINAL_ADDR,   \n");
		sb.append("   S.STATUS_NAME,   \n");
		sb.append("    A.ONOFF_TIME,   \n");
		sb.append("   A.GATE_IP,   \n");
		sb.append("     A.GATE_PORT,   \n");
		sb.append("     P.PROTOCOL_NAME,   \n");
		sb.append("     T.TMNL_TYPE   \n");
		sb.append(" FROM A_TMNL_REAL_TIME    A,   \n");
		sb.append("     R_TMNL_RUN          R,   \n");
		sb.append("      R_CONS_TMNL_RELA    RC,   \n");
		sb.append("      C_CONS              C,   \n");
		sb.append("     VW_TMNL_STATUS_CALC S,   \n");
		sb.append("      VW_PROTOCOL_CODE    P,   \n");
		sb.append("      VW_TMNL_TYPE_CODE   T,   \n");
		sb.append("       VW_TMNL_FACTORY     F,   \n");
		sb.append("      O_ORG               O   \n");
		sb.append("WHERE A.TMNL_ASSET_NO = R.TMNL_ASSET_NO   \n");
		sb.append("AND RC.TMNL_ASSET_NO = R.TMNL_ASSET_NO   \n");
		sb.append("AND RC.CONS_NO = C.CONS_NO   \n");
		sb.append("AND R.STATUS_CODE = S.STATUS_CODE(+)   \n");
		sb.append(" AND R.PROTOCOL_CODE = P.PROTOCOL_CODE(+)   \n");
		
		sb.append("AND R.TERMINAL_TYPE_CODE = T.TMNL_TYPE_CODE(+)   \n");
		sb.append("AND R.FACTORY_CODE = F.FACTORY_CODE   \n");
		sb.append("AND RC.ORG_NO = O.ORG_NO(+)   \n");
		sb.append("AND EXISTS (SELECT 1   \n");
		sb.append("       FROM P_ACCESS_ORG PRI_ORG   \n");
		sb.append("      WHERE PRI_ORG.ORG_NO = RC.AREA_NO   \n");
		sb.append("        AND PRI_ORG.STAFF_NO = ? )   \n");
		
		sb.append("	AND A.IS_ONLINE = ?   \n");
		
		if(statisticFlag.equals("1")){
			sb.append("AND R.FACTORY_CODE = ?  \n");
		}else{
			sb.append("AND RC.ORG_NO = ?  \n");
		}
		String sql = sb.toString();
		logger.debug(sql);
		try {
			return pagingFind(sql.toString(), start, limit,new ChannelMonitorMapper(), new Object[]{ pSysUser.getStaffNo(), onlineType , statisticsType });
		} catch (RuntimeException e) {
			logger.error(sql);
				throw e;
		}
	}
	
	/**
	 * 
	 * ChannelMonitorDto 映射类 
	 * 
	 */
	class ChannelMonitorMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ChannelMonitorDto channelMonitorDto = new ChannelMonitorDto();
			try {
				channelMonitorDto.setOrgNo(rs.getString("ORG_NAME"));
				channelMonitorDto.setConsNo(rs.getString("CONS_NO"));
				channelMonitorDto.setConsName(rs.getString("CONS_NAME"));
				channelMonitorDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				channelMonitorDto.setTerminalstate(rs.getString("STATUS_NAME"));
				channelMonitorDto.setGateIp(rs.getString("GATE_IP"));
				channelMonitorDto.setGatePort(rs.getString("GATE_PORT"));
				channelMonitorDto.setOffTime(rs.getDate("ONOFF_TIME"));
				channelMonitorDto.setRuleType(rs.getString("PROTOCOL_NAME"));
				channelMonitorDto.setTerminalType(rs.getString("TMNL_TYPE"));
				return channelMonitorDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorDao#findChannelMonitorFacturerBean()
	 */
	@Override
	public Page<ChannelMonitorTerminalDto> findChannelMonitorFacturerBean(PSysUser pSysUser,long start,long limit) {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		/*
		 * 通过权限判断操作员所在级别叠加SQL. 级别分省级、市级、地区县级别;
		 */
		//向兵修改：由于R_CONS_TMNL_RELA存在多个用户对应一个终端的情况
		//在对终端进行统计时，因此需要用DISTINCT 来从R_CONS_TMNL_RELA唯一地选出终端信息
		if(provinceLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT VR.FACTORY_CODE,  \n");
			sb.append("VF.FACTORY_NAME,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append("FROM A_TMNL_REAL_TIME A,  \n");
			sb.append("    R_TMNL_RUN VR,  \n");
			sb.append("   VW_TMNL_FACTORY VF,   \n");
			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("AND A.TMNL_ASSET_NO = VR.TMNL_ASSET_NO  \n");
			sb.append(" AND VR.FACTORY_CODE = VF.FACTORY_CODE(+)    \n");
			sb.append(" AND VR.STATUS_CODE = '01'    \n");
			sb.append("  GROUP BY VR.FACTORY_CODE, VF.FACTORY_NAME \n");
		    
		}else if (cityLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT VR.FACTORY_CODE,  \n");
			sb.append("   VF.FACTORY_NAME,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append("FROM A_TMNL_REAL_TIME A,  \n");
			sb.append(" R_TMNL_RUN VR,  \n");
			sb.append("VW_TMNL_FACTORY VF,   \n");
			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append(" AND A.TMNL_ASSET_NO = VR.TMNL_ASSET_NO  \n");
			sb.append("AND VR.FACTORY_CODE = VF.FACTORY_CODE(+)   \n");
			sb.append(" AND RC.CITY_NO = ? \n");//--地市操作员单位入参
			sb.append(" AND VR.STATUS_CODE = '01'    \n");
			sb.append(" GROUP BY VR.FACTORY_CODE, VF.FACTORY_NAME  \n");
			list.add(pSysUser.getOrgNo());
		}else if(areaLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT VR.FACTORY_CODE,  \n");
			sb.append("  	 VF.FACTORY_NAME,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append("FROM A_TMNL_REAL_TIME A,  \n");
			sb.append("   R_TMNL_RUN VR,  \n");
			sb.append("   VW_TMNL_FACTORY VF,   \n");
			sb.append("    (SELECT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.TMNL_ASSET_NO, 1, 9) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("AND A.TMNL_ASSET_NO = VR.TMNL_ASSET_NO  \n");
			sb.append("AND VR.FACTORY_CODE = VF.FACTORY_CODE(+)   \n");
			sb.append("AND RC.CITY_NO = ?  \n");// --区县操作员单位入参  \n
			sb.append(" AND VR.STATUS_CODE = '01'    \n");
			sb.append("GROUP BY VR.FACTORY_CODE, VF.FACTORY_NAME  \n");
		    list.add(pSysUser.getOrgNo());
		}
		String sql = sb.toString();
		logger.debug(sql);
//		try{
			return pagingFind(sql, start, limit, new CMTerminalMapper(), list.toArray());
//			return  this.getJdbcTemplate().query(sql, list.toArray(), new CMTerminalMapper());
//		}catch(RuntimeException e){
//			this.logger.debug(sql);
//			throw e;
//		}
	}
	
	/**
	 * 
	 * ChannelMonitorTerminalDto 映射类 
	 * 
	 */
	class CMTerminalMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ChannelMonitorTerminalDto terminalDto = new ChannelMonitorTerminalDto();
			try {
				terminalDto.setTerminalNo(rs.getString("FACTORY_CODE"));
				terminalDto.setTerminalFacturer(rs.getString("FACTORY_NAME"));
				terminalDto.setUpTerminalNum(rs.getInt("IS_ONLINE"));
				terminalDto.setOffTerminalNum(rs.getInt("NO_ONLINE"));
				return terminalDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	
	//-------------------------modyfied by sungang 2010-10-06 -----start-------------
	@Override
	public Page<ChannelMonitorOrgNoDto> findChannelMonitorOrgNoBean(PSysUser pSysUser,long start,long limit) {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		/*
		 * 通过权限判断操作员所在级别叠加SQL. 级别分省级、市级、地区县级别;
		 */
		//向兵修改：由于R_CONS_TMNL_RELA存在多个用户对应一个终端的情况
		//在对终端进行统计时，因此需要用DISTINCT 来从R_CONS_TMNL_RELA唯一地选出终端信息
		if(provinceLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT RC.CITY_NO AS ORG_NO,  \n");
			sb.append("O.Org_Short_Name,O.ORG_TYPE,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
			sb.append("  	 O_ORG O,  R_TMNL_RUN        R,  \n");
			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n      AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("AND RC.CITY_NO = O.ORG_NO  \n");
			sb.append(" AND R.STATUS_CODE = '01'    \n");
			sb.append(" GROUP BY RC.CITY_NO, O.Org_Short_Name  ,O.ORG_TYPE\n");
			sb.append(" ORDER BY RC.CITY_NO		  \n");
		}else if (cityLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT RC.AREA_NO AS ORG_NO,  \n");
			sb.append("     O.Org_Short_Name,O.ORG_TYPE,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
			sb.append("   O_ORG O,  R_TMNL_RUN        R,  \n");
			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n     AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("AND RC.AREA_NO = O.ORG_NO  \n");
			sb.append("AND RC.CITY_NO = ?  \n");//地区县单位入参
			sb.append(" AND R.STATUS_CODE = '01'    \n");
			sb.append("GROUP BY RC.AREA_NO, O.Org_Short_Name ,O.ORG_TYPE \n");
			sb.append("ORDER BY RC.AREA_NO  \n");
			list.add(pSysUser.getOrgNo());
		}else if(areaLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT RC.ORG_NO,  \n");
			sb.append("   O.Org_Short_Name,O.ORG_TYPE,  \n");
			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
			sb.append("		O_ORG O,  R_TMNL_RUN        R,  \n");
			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
			sb.append("             R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.TMNL_ASSET_NO, 1, 9) AS CITY_NO,  \n");
			sb.append("              R.CONS_NO,  \n");
			sb.append("              R.TMNL_ASSET_NO  \n");
			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n     AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("AND RC.ORG_NO = O.ORG_NO  \n");
			sb.append("AND RC.CITY_NO = ?  \n");  //地区县单位入参
			sb.append(" AND R.STATUS_CODE = '01'    \n");
			sb.append("GROUP BY RC.ORG_NO, O.Org_Short_Name ,O.ORG_TYPE \n");
			sb.append(" ORDER BY RC.ORG_NO  \n");
			list.add(pSysUser.getOrgNo());
		}
		String sql = sb.toString();
		logger.debug(sql);
//		try{
			return pagingFind(sql, start, limit, new CMOrgNoMapper(), list.toArray());
//			return this.getJdbcTemplate().query(sql, list.toArray(), new CMOrgNoMapper());
//		}catch(RuntimeException e){
//			this.logger.debug(sql);
//			throw e;
//		}
	}
	
	//-------------------------modyfied by sungang 2010-10-06 -----end-------------
	/*
	 * (non-Javadoc)
	 * @see com.nari.runman.runstatusman.ChannelMonitorDao#findChannelMonitorOrgNoBean()
	 */
//	@Override
//	public Page<ChannelMonitorOrgNoDto> findChannelMonitorOrgNoBean(PSysUser pSysUser,long start,long limit) {
//		StringBuffer sb = new StringBuffer();
//		List<Object> list = new ArrayList<Object>();
//		/*
//		 * 通过权限判断操作员所在级别叠加SQL. 级别分省级、市级、地区县级别;
//		 */
//		//向兵修改：由于R_CONS_TMNL_RELA存在多个用户对应一个终端的情况
//		//在对终端进行统计时，因此需要用DISTINCT 来从R_CONS_TMNL_RELA唯一地选出终端信息
//		if(provinceLevel.equals(pSysUser.getAccessLevel())){
//			sb.append("SELECT RC.CITY_NO AS ORG_NO,  \n");
//			sb.append("O.Org_Short_Name,O.ORG_TYPE,  \n");
//			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
//			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
//			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
//			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
//			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
//			sb.append("  	 O_ORG O,  R_TMNL_RUN        R,  \n");
//			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
//			sb.append("             R.AREA_NO,  \n");
//			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
//			sb.append("              R.CONS_NO,  \n");
//			sb.append("              R.TMNL_ASSET_NO  \n");
//			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
//			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n      AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
//			sb.append("AND RC.CITY_NO = O.ORG_NO  \n");
//			sb.append(" AND R.STATUS_CODE = '01'    \n");
//			sb.append(" GROUP BY RC.CITY_NO, O.Org_Short_Name  ,O.ORG_TYPE\n");
//			sb.append(" ORDER BY RC.CITY_NO		  \n");
//		}else if (cityLevel.equals(pSysUser.getAccessLevel())){
//			sb.append("SELECT RC.AREA_NO AS ORG_NO,  \n");
//			sb.append("     O.Org_Short_Name,O.ORG_TYPE,  \n");
//			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
//			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
//			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
//			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
//			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
//			sb.append("   O_ORG O,  R_TMNL_RUN        R,  \n");
//			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
//			sb.append("             R.AREA_NO,  \n");
//			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
//			sb.append("              R.CONS_NO,  \n");
//			sb.append("              R.TMNL_ASSET_NO  \n");
//			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
//			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n     AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
//			sb.append("AND RC.AREA_NO = O.ORG_NO  \n");
//			sb.append("AND RC.CITY_NO = ?  \n");//地区县单位入参
//			sb.append(" AND R.STATUS_CODE = '01'    \n");
//			sb.append("GROUP BY RC.AREA_NO, O.Org_Short_Name ,O.ORG_TYPE \n");
//			sb.append("ORDER BY RC.AREA_NO  \n");
//			list.add(pSysUser.getOrgNo());
//		}else if(areaLevel.equals(pSysUser.getAccessLevel())){
//			sb.append("SELECT RC.ORG_NO,  \n");
//			sb.append("   O.Org_Short_Name,O.ORG_TYPE,  \n");
//			sb.append(" COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1  \n");
//			sb.append("        ELSE   NULL  END) IS_ONLINE,  \n");
//			sb.append("  COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0  \n");
//			sb.append("          ELSE   NULL  END) NO_ONLINE  \n");
//			sb.append(" FROM A_TMNL_REAL_TIME A,  \n");
//			sb.append("		O_ORG O,  R_TMNL_RUN        R,  \n");
//			sb.append("    (SELECT DISTINCT R.ORG_NO,  \n");
//			sb.append("             R.AREA_NO,  \n");
//			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  \n");
//			sb.append("              R.CONS_NO,  \n");
//			sb.append("              R.TMNL_ASSET_NO  \n");
//			sb.append("         FROM R_CONS_TMNL_RELA R ) RC  \n");
//			sb.append("WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n     AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
//			sb.append("AND RC.ORG_NO = O.ORG_NO  \n");
//			sb.append("AND RC.AREA_NO = ?  \n");  //地区县单位入参
//			sb.append(" AND R.STATUS_CODE = '01'    \n");
//			sb.append("GROUP BY RC.ORG_NO, O.Org_Short_Name ,O.ORG_TYPE \n");
//			sb.append(" ORDER BY RC.ORG_NO  \n");
//			list.add(pSysUser.getOrgNo());
//		}
//		String sql = sb.toString();
//		logger.debug(sql);
////		try{
//			return pagingFind(sql, start, limit, new CMOrgNoMapper(), list.toArray());
////			return this.getJdbcTemplate().query(sql, list.toArray(), new CMOrgNoMapper());
////		}catch(RuntimeException e){
////			this.logger.debug(sql);
////			throw e;
////		}
//	}
	
	/**
	 * 
	 * ChannelMonitorOrgNoDto 映射类 
	 * 
	 */
	class CMOrgNoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ChannelMonitorOrgNoDto orgNoDto = new ChannelMonitorOrgNoDto();
			try {
				orgNoDto.setOrgNo(rs.getString("ORG_NO"));
				orgNoDto.setOrgName(rs.getString("Org_Short_Name"));
				orgNoDto.setUpTerminalNum(rs.getInt("is_online"));
				orgNoDto.setOffTerminalNum(rs.getInt("no_online"));
				orgNoDto.setOrgType(rs.getString("ORG_TYPE"));
				return orgNoDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	/**
	 * 统计查询采集类型
	 * @param pSysUser  操作员信息
	 * @param start  开始页码
	 * @param limit  显示数量
	 * @return  page 返回集合
	 * @throws Exception  
	 */
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorConsType(PSysUser pSysUser,long start,long limit)throws Exception  {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		/*
		 * 通过权限判断操作员所在级别叠加SQL. 级别分省级、市级、地区县级别;
		 */
		if(provinceLevel.equals(pSysUser.getAccessLevel())){
			sb.append("  SELECT COUNT(C.CONS_NO)  C_STAT,    \n");
			sb.append("       C.CONS_TYPE TYPE_CODE,   \n");
			sb.append("       CT.CONS_TYPE_NAME TYPE_NAME,   \n");
			sb.append("     COUNT(CASE   WHEN A.IS_ONLINE = 1 THEN   1   ELSE  NULL  END) IS_ONLINE,\n");
			sb.append("     COUNT(CASE   WHEN A.IS_ONLINE = 0 THEN   0   ELSE  NULL  END) NO_ONLINE\n");
			sb.append("     FROM A_TMNL_REAL_TIME A,\n");
			sb.append("     O_ORG O,  R_TMNL_RUN R, C_CONS C, VW_CONS_TYPE CT,\n");
			sb.append("        (SELECT DISTINCT R.ORG_NO,   R.AREA_NO, \n");
			sb.append("                  SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,\n");
			sb.append("                  R.CONS_NO,  R.TMNL_ASSET_NO \n");
			sb.append("               FROM R_CONS_TMNL_RELA R) RC\n");
			sb.append("       WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO \n");
			sb.append("        AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO \n");
			sb.append("     AND C.CONS_NO = RC.CONS_NO \n");
			sb.append("       AND C.CONS_TYPE = CT.CONS_TYPE  \n");
			sb.append("      AND RC.CITY_NO = O.ORG_NO \n");
			sb.append("        AND R.STATUS_CODE = '01'\n");
			sb.append("      GROUP BY C.CONS_TYPE, CT.CONS_TYPE_NAME \n");
			sb.append("       ORDER BY C.CONS_TYPE  \n");
		}else if (cityLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT COUNT(C.CONS_NO)  C_STAT, \n");
			sb.append(" C.CONS_TYPE TYPE_CODE,  \n");
			sb.append(" CT.CONS_TYPE_NAME TYPE_NAME,   \n");
			sb.append(" COUNT(CASE   WHEN A.IS_ONLINE = 1 THEN   1   ELSE  NULL  END) IS_ONLINE,\n");
			sb.append(" COUNT(CASE   WHEN A.IS_ONLINE = 0 THEN   0   ELSE  NULL  END) NO_ONLINE \n");
			sb.append(" FROM A_TMNL_REAL_TIME A, \n");
			sb.append(" O_ORG O,  R_TMNL_RUN R,  C_CONS C,  VW_CONS_TYPE CT, \n");
			sb.append(" (SELECT DISTINCT R.ORG_NO,   R.AREA_NO,  \n");
			sb.append("           SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  R.CONS_NO,  R.TMNL_ASSET_NO    \n");
			sb.append("          FROM R_CONS_TMNL_RELA R) RC   \n");
			sb.append("   WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO     \n");
			sb.append("   AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO   \n");
			sb.append("    AND C.CONS_NO = RC.CONS_NO     \n");
			sb.append("    AND C.CONS_TYPE = CT.CONS_TYPE    \n");
			sb.append("  AND RC.AREA_NO = O.ORG_NO\n");
			sb.append("  AND R.STATUS_CODE = '01'\n");
			sb.append("  AND RC.CITY_NO = ?   \n");//地市单位入参
			sb.append("   GROUP BY C.CONS_TYPE, CT.CONS_TYPE_NAME  \n");
			sb.append(" ORDER BY C.CONS_TYPE   \n");
			list.add(pSysUser.getOrgNo());
		}else if(areaLevel.equals(pSysUser.getAccessLevel())){
			sb.append(" SELECT COUNT(C.CONS_NO)  C_STAT,  \n");
			sb.append(" C.CONS_TYPE TYPE_CODE,     \n");
			sb.append("  CT.CONS_TYPE_NAME TYPE_NAME,    \n");
			sb.append("  COUNT(CASE   WHEN A.IS_ONLINE = 1 THEN   1   ELSE  NULL  END) IS_ONLINE,    \n");
			sb.append("    COUNT(CASE   WHEN A.IS_ONLINE = 0 THEN   0   ELSE  NULL  END) NO_ONLINE  \n");
			sb.append("   FROM A_TMNL_REAL_TIME A, O_ORG O, R_TMNL_RUN R, C_CONS C, VW_CONS_TYPE CT,   \n");
			sb.append("     (SELECT DISTINCT R.ORG_NO,   R.AREA_NO,   SUBSTR(R.TMNL_ASSET_NO, 1, 9) AS CITY_NO,  \n");
			sb.append("	                    R.CONS_NO,  R.TMNL_ASSET_NO  FROM R_CONS_TMNL_RELA R) RC  \n");
			sb.append("      WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("       AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("        AND C.CONS_NO = RC.CONS_NO \n");
			sb.append("       AND C.CONS_TYPE = CT.CONS_TYPE  \n");
			sb.append("        AND RC.ORG_NO = O.ORG_NO\n");
			sb.append("        AND R.STATUS_CODE = '01'\n");
			sb.append("        AND RC.CITY_NO = ?  \n");  //地区县单位入参
			sb.append("        GROUP BY C.CONS_TYPE, CT.CONS_TYPE_NAME\n");
			sb.append("       ORDER BY C.CONS_TYPE  \n");
			list.add(pSysUser.getOrgNo());
		}
		String sql = sb.toString();
		logger.debug(sql);
		return pagingFind(sql, start, limit, new CMTypeMapper(), list.toArray());
	}
	
	/**
	 * 统计查询采集类型
	 * @param pSysUser  操作员信息
	 * @param start  开始页码
	 * @param limit  显示数量
	 * @return  page 返回集合
	 * @throws Exception  
	 */
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorCollectMode(PSysUser pSysUser,long start,long limit)throws Exception {
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		/*
		 * 通过权限判断操作员所在级别叠加SQL. 级别分省级、市级、地区县级别;
		 */
		if(provinceLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT R.COLL_MODE TYPE_CODE,CM.COMM_MODE TYPE_NAME, COUNT(*) C_STAT,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1   ELSE   NULL  END) IS_ONLINE,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0    ELSE   NULL  END) NO_ONLINE     \n");
			sb.append("FROM A_TMNL_REAL_TIME A,   O_ORG O,  R_TMNL_RUN R,  VW_COMM_MODE CM,   \n");
			sb.append("     (SELECT DISTINCT R.ORG_NO,    R.AREA_NO,   SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  R.CONS_NO, \n");
			sb.append("          R.TMNL_ASSET_NO   FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("     WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("      AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("     AND RC.CITY_NO = O.ORG_NO  \n");
			sb.append("     AND R.COLL_MODE = CM.COMM_MODE_CODE \n");
			sb.append("     AND R.STATUS_CODE = '01'    \n");
			sb.append("     GROUP BY R.COLL_MODE,CM.COMM_MODE \n");
			sb.append("      ORDER BY R.COLL_MODE  \n");
		}else if (cityLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT R.COLL_MODE TYPE_CODE,CM.COMM_MODE TYPE_NAME, COUNT(*) C_STAT,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1   ELSE   NULL  END) IS_ONLINE,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0    ELSE   NULL  END) NO_ONLINE     \n");
			sb.append("FROM A_TMNL_REAL_TIME A,   O_ORG O,  R_TMNL_RUN R,  VW_COMM_MODE CM,   \n");
			sb.append("     (SELECT DISTINCT R.ORG_NO,    R.AREA_NO,   SUBSTR(R.ORG_NO, 1, 5) AS CITY_NO,  R.CONS_NO, \n");
			sb.append("          R.TMNL_ASSET_NO   FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("     WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("      AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("    AND RC.AREA_NO = O.ORG_NO\n");
			sb.append("     AND R.COLL_MODE = CM.COMM_MODE_CODE \n");
			sb.append("     AND R.STATUS_CODE = '01'    \n");
			sb.append("  AND RC.CITY_NO = ?   \n");//地市单位入参
			sb.append("     GROUP BY R.COLL_MODE,CM.COMM_MODE \n");
			sb.append("      ORDER BY R.COLL_MODE  \n");
			list.add(pSysUser.getOrgNo());
		}else if(areaLevel.equals(pSysUser.getAccessLevel())){
			sb.append("SELECT R.COLL_MODE TYPE_CODE,CM.COMM_MODE TYPE_NAME, COUNT(*) C_STAT,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 1 THEN 1   ELSE   NULL  END) IS_ONLINE,     \n");
			sb.append("COUNT(CASE  WHEN A.IS_ONLINE = 0 THEN 0    ELSE   NULL  END) NO_ONLINE     \n");
			sb.append("FROM A_TMNL_REAL_TIME A,   O_ORG O,  R_TMNL_RUN R,  VW_COMM_MODE CM,   \n");
			sb.append("     (SELECT DISTINCT R.ORG_NO,    R.AREA_NO,   SUBSTR(R.TMNL_ASSET_NO, 1, 9) AS CITY_NO,  R.CONS_NO, \n");
			sb.append("          R.TMNL_ASSET_NO   FROM R_CONS_TMNL_RELA R ) RC  \n");
			sb.append("     WHERE A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("      AND R.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
			sb.append("        AND RC.ORG_NO = O.ORG_NO\n");
			sb.append("     AND R.COLL_MODE = CM.COMM_MODE_CODE \n");
			sb.append("     AND R.STATUS_CODE = '01'    \n");
			sb.append("        AND RC.CITY_NO = ?  \n");  //地区县单位入参
			sb.append("     GROUP BY R.COLL_MODE,CM.COMM_MODE \n");
			sb.append("      ORDER BY R.COLL_MODE  \n");
			list.add(pSysUser.getOrgNo());
		}
		String sql = sb.toString();
		logger.debug(sql);
		return pagingFind(sql, start, limit, new CMTypeMapper(), list.toArray());
	}
	
	
	/**
	 * 
	 * ChannelMonitorConsTypeCollMode 映射类 
	 * 
	 */
	class CMTypeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ChannelMonitorConsTypeCollMode dto = new ChannelMonitorConsTypeCollMode();
			try {
				dto.setTypeNmae(rs.getString("TYPE_NAME"));
				dto.setTypeCode(rs.getString("TYPE_CODE"));
				dto.setStat(rs.getInt("C_STAT"));
				dto.setUpTerminalNum(rs.getInt("IS_ONLINE"));
				dto.setOffTerminalNum(rs.getInt("NO_ONLINE"));
				return dto;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
