package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.AEventStatDBeanDao;
import com.nari.statreport.AEventStatDBean;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.statreport.AmmeterHDto;
import com.nari.statreport.AmmeterHWindowDto;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class AEventStatDBeanDaoImpl extends JdbcBaseDAOImpl implements AEventStatDBeanDao {

	@Override
	public Page<AEventStatDBean> findAEventStatDBean(PSysUser user, Date startDate,	Date endDate, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		if(user.getAccessLevel().equals("02")){
			sql.append("SELECT O.ORG_NAME, TTT.AREA_NO, TTT.TOTAL, TTT.SJCC, TTT.DBGZ, TTT.SDXJ, TTT.DNBTZ, TTT.DNBFZ, TTT.DNBCC, TTT.CBSB  \n" );
			sql.append("FROM O_ORG O,       \n" ); 
			sql.append("(    \n" ); 
			sql.append("	SELECT NVL(AAA.AREA_NO, AAA.P_ORG_NO) AS AREA_NO,       \n" ); 
		}else{
			sql.append("SELECT AAA.ORG_NAME, AAA.AREA_NO,\n" );
		}
			sql.append("      AAA.SJCC + AAA.DBGZ + AAA.SDXJ + AAA.DNBTZ + AAA.DNBFZ + AAA.DNBCC +  AAA.CBSB TOTAL,\n" ); 
			sql.append("      AAA.SJCC,\n" ); 
			sql.append("      AAA.DBGZ,\n" ); 
			sql.append("      AAA.SDXJ,\n" ); 
			sql.append("      AAA.DNBTZ,\n" ); 
			sql.append("      AAA.DNBFZ,\n" ); 
			sql.append("      AAA.DNBCC,\n" ); 
			sql.append("      AAA.CBSB\n" ); 
			sql.append("FROM(\n" );
			if(user.getAccessLevel().equals("02")){
				sql.append("	SELECT EV.AREA_NO, ORG.P_ORG_NO,        \n" ); 
				
			}else{
				sql.append("SELECT EV.AREA_NO, ORG.ORG_SHORT_NAME, ORG.ORG_NAME,\n" ); 
				
			}
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '0C', EVENT_CNT, 0)) AS sjcc,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '0D', EVENT_CNT, 0)) AS dbgz,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '1B', EVENT_CNT, 0)) AS sdxj,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '1E', EVENT_CNT, 0)) AS dnbtz,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '1D', EVENT_CNT, 0)) AS dnbfz,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '1C', EVENT_CNT, 0)) AS dnbcc,\n" ); 
			sql.append("       SUM(DECODE(SUBSTR(EV.EVENT_NO, -2, 2), '1F', EVENT_CNT, 0)) AS cbsb\n" ); 
			sql.append("  FROM B_PROTOCOL_EVENT PE, O_ORG ORG  \n"); 
			
			sql.append(" ,( SELECT ZB.AREA_NO, ORG.ORG_TYPE, ZB.CONS_NO, ZB.TMNL_ASSET_NO, SJ.EVENT_NO, COUNT(*) AS EVENT_CNT      \n");
			sql.append("   FROM E_TMNL_EVENT SJ, E_DATA_MP ZB, O_ORG ORG    \n");
			sql.append("   WHERE SJ.ID = ZB.ID    \n");
			sql.append("   AND SJ.AREA_CODE = ZB.AREA_CODE    \n");
			sql.append("   AND ZB.AREA_NO = ORG.ORG_NO    \n");
			if(user.getAccessLevel().equals("02")){
				// 省级用户显示市级列表 默认不需要参数
			}else{
				//
				String tempAreaCode = user.getOrgNo();
				tempAreaCode = tempAreaCode.substring( 3, 5);
				paraList.add(tempAreaCode);
				sql.append("   and sj.area_code = ?    \n");
			}
			//TO_DATE('2010-07-07', 'YYYY-MM-DD')
			paraList.add(startDateStr);
			paraList.add(endDateStr);
			sql.append("   AND SJ.EVENT_TIME >= TO_DATE( ? , 'YYYY-MM-DD')    \n");
			sql.append("   AND SJ.EVENT_TIME < TO_DATE( ? , 'YYYY-MM-DD')    \n");
			sql.append("   GROUP BY ZB.AREA_NO, ORG.ORG_TYPE,  ZB.CONS_NO, ZB.TMNL_ASSET_NO, SJ.EVENT_NO  ) EV   \n");
			
			sql.append("  WHERE EV.EVENT_NO = PE.EVENT_NO\n" ); 
			sql.append("   AND ORG.ORG_NO = EV.AREA_NO\n");
			sql.append("   AND PE.EVENT_TYPE = '03'\n" ); 
		if(user.getAccessLevel().equals("02")){
			// 省级用户显示市级列表 默认不需要参数
			//sql.append("   AND ORG.P_ORG_NO IN (SELECT ORG.ORG_NO FROM O_ORG ORG WHERE ORG.P_ORG_NO = ? )  \n");
		}else {
			paraList.add(user.getOrgNo());
			sql.append("   AND ORG.P_ORG_NO = ? \n");
		}
		
//			sql.append("   AND EV.STAT_DATE BETWEEN ? AND ?  \n" ); 
		if(user.getAccessLevel().equals("02")){
			paraList.add(user.getOrgNo());
			sql.append(" GROUP BY CUBE(EV.AREA_NO), ORG.P_ORG_NO)      \n" ); 
			sql.append("   AAA)   TTT     \n" ); 
			sql.append(" WHERE TTT.AREA_NO = O.ORG_NO      \n" ); 
			sql.append("  AND O.P_ORG_NO = ?      \n" ); 
			sql.append("   ORDER BY TTT.AREA_NO     \n" ); 
		}else{
			sql.append(" GROUP BY EV.AREA_NO, ORG.ORG_SHORT_NAME, ORG.ORG_NAME   \n" ); 
			sql.append(" ) AAA  \n" ); 
			sql.append(" ORDER BY AAA.AREA_NO  ");
		}
		this.logger.debug(sql.toString());
		 return super.pagingFind(sql.toString(), start, limit, new aEventStatDBeanMapper(), paraList.toArray());
	}
	
	class aEventStatDBeanMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AEventStatDBean aeventstatdbean = new AEventStatDBean();
			try {
				aeventstatdbean.setOrgNo(rs.getString("AREA_NO"));// LCM 修改
				aeventstatdbean.setOrgName(rs.getString("ORG_NAME"));
				aeventstatdbean.setTotal(rs.getString("TOTAL"));
				aeventstatdbean.setCbsb(rs.getString("CBSB"));
				aeventstatdbean.setDnbtz(rs.getString("DNBTZ"));
				aeventstatdbean.setDnbfz(rs.getString("DNBFZ"));
				aeventstatdbean.setDnbcc(rs.getString("DNBCC"));
				aeventstatdbean.setSdxj(rs.getString("SDXJ"));
				aeventstatdbean.setDbgz(rs.getString("DBGZ"));
				aeventstatdbean.setSjcc(rs.getString("SJCC"));
				return aeventstatdbean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	@Override
	public Page<AEventStatDWindowDto> findAEventStatDWindow(String orgNo,
			String eventNo ,Date startDate, Date endDate ,long start, int limit) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		sql.append("SELECT A.STAT_DATE , SUM (A.EVENT_CNT) AS EVENT_CNT  ,A.ORG_TYPE  \n");
		sql.append("FROM A_EVENT_STAT_D A     \n");
		orgNo = orgNo + "%";
		paraList.add(orgNo);
		sql.append("WHERE A.ORG_NO LIKE ?   \n");
		eventNo = "%" + eventNo;
		paraList.add(eventNo);
		sql.append("AND A.EVENT_NO LIKE ?    \n");
		paraList.add(startDateStr);
		paraList.add(endDateStr);
		sql.append("AND A.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')    \n");
		sql.append("AND A.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd')    \n");
		sql.append("GROUP BY A.STAT_DATE  ,A.ORG_TYPE   \n");
		sql.append("ORDER BY A.STAT_DATE DESC   \n");
		
		this.logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit, new AEventStatDWindowMapper(), paraList.toArray());
	}
	
	/**
	 * 
	 * 映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-14
	 *
	 */
	class AEventStatDWindowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AEventStatDWindowDto aWindowDto = new AEventStatDWindowDto();
			try {
				aWindowDto.setStatDate(rs.getDate("STAT_DATE"));
				aWindowDto.setEventCnt(rs.getString("EVENT_CNT"));
				aWindowDto.setOrgType(rs.getString("ORG_TYPE"));
				return aWindowDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	@Override
	public Page<AmmeterHDto> findAmmeterHDto(PSysUser user,String orgNo, String eventNo, Date startDate,
			Date endDate, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		String startDateStr = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
		String endDateStr = new SimpleDateFormat("yyyy-MM-dd").format(endDate);
		sql.append("SELECT O.ORG_NO, O.ORG_NAME, C.CONS_NO, C.CONS_NAME, TTT.EVENT_TIME,   \n");
		sql.append("       TTT.RECEIVE_TIME, TTT.TMNL_ASSET_NO,   \n");
		sql.append("       TTT.EVENT_NO,PE.EVENT_NAME, TTT.ASSET_NO      \n");
		sql.append("FROM O_ORG O, C_CONS C, B_PROTOCOL_EVENT PE,   \n");
		sql.append("     (SELECT ET.EVENT_NO,ET.EVENT_TIME, ET.RECEIVE_TIME, ED.AREA_NO,   \n");
		sql.append("             ED.CONS_NO, ED.TMNL_ASSET_NO, ED.ASSET_NO   \n");
		sql.append("      FROM E_TMNL_EVENT ET, E_DATA_MP ED   \n");
		sql.append("      WHERE ET.ID = ED.ID   \n");
		sql.append("      AND ET.AREA_CODE = ED.AREA_CODE   \n");
		paraList.add(eventNo);
		sql.append("AND SUBSTR(ET.EVENT_NO, -2, 2) =  ?   \n");
		
		if(user.getAccessLevel().equals("02")){
			orgNo = orgNo +"%";
			paraList.add(orgNo);
			sql.append("AND ED.AREA_NO LIKE ? \n");
		}else{
			paraList.add(orgNo);
			sql.append("AND ED.AREA_NO = ? \n");
		}
		paraList.add(startDateStr);
		paraList.add(endDateStr);
		sql.append("      AND ET.EVENT_TIME >=  TO_DATE( ? , 'YYYY-MM-DD')  AND  ET.EVENT_TIME <  TO_DATE( ? , 'YYYY-MM-DD')   \n");
		sql.append("              ) TTT   \n");
		sql.append("WHERE C.CONS_NO = TTT.CONS_NO   \n");
		//sql.append("AND C.CONS_NO = MP.CONS_NO   \n");
		sql.append("AND TTT.EVENT_NO = PE.EVENT_NO     \n");
		sql.append("AND O.ORG_NO = C.ORG_NO   \n");
		sql.append("AND TTT.AREA_NO = C.AREA_NO  \n");
		//sql.append("AND TTT.TMNL_ASSET_NO = RT.TMNL_ASSET_NO  \n");
		sql.append("AND PE.EVENT_TYPE = '03'   \n");
		
		
		this.logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit, new AmmeterHDtoMapper(), paraList.toArray());
	}
	
	/**
	 * 
	 * 电能表异常映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-3
	 *
	 */
	class AmmeterHDtoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AmmeterHDto ammeterHDto  = new AmmeterHDto();
			try {
				ammeterHDto.setOrgNo(rs.getString("ORG_NO"));
				ammeterHDto.setOrgName(rs.getString("ORG_NAME"));
				ammeterHDto.setConsNo(rs.getString("CONS_NO"));
				ammeterHDto.setConsName(rs.getString("CONS_NAME"));
				ammeterHDto.setAssetNo(rs.getString("ASSET_NO"));
				ammeterHDto.setEventNo(rs.getString("EVENT_NO"));
				ammeterHDto.setEventName(rs.getString("EVENT_NAME"));
				//ammeterHDto.setMpName(rs.getString("MP_NAME"));
				//ammeterHDto.setMpNo(rs.getString("MP_NO"));
				ammeterHDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				ammeterHDto.setEventTime(rs.getTimestamp("EVENT_TIME"));
				ammeterHDto.setReceiveTime(rs.getTimestamp("RECEIVE_TIME"));
				return ammeterHDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	@Override
	public Page<AmmeterHWindowDto> findAmmeterHWindowList(String eventId,
			String areaCode, String eventNo, long start, int limit)
			throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		
		sql.append("SELECT ET.ID,ET.AREA_CODE,ET.EVENT_NO,ET.EVENT_TIME,ET.FROM_TYPE,ET.FROM_NO,ET.STORE_TYPE,ET.EVENT_DATA,ET.DATA1,    \n");
		sql.append("	   ET.DATA2,ET.DATA3,ET.DATA4,ET.DATA5,ET.DATA6,ET.DATA7,ET.DATA8,ET.DATA9,ET.DATA10,ET.DATA11,ET.DATA12,ET.DATA13,ET.DATA14,    \n");
		sql.append("	   ET.DATA15,ET.DATA16,ET.DATA17,ET.DATA18,ET.DATA19,ET.DATA20,ET.DATA21,ET.DATA22,ET.DATA23,ET.DATA24,ET.DATA25,ET.DATA26,ET.DATA27,    \n");
		sql.append("	   ET.DATA28,ET.DATA29,ET.DATA30,ET.DATA31,ET.DATA32,ET.DATA33,ET.DATA34,ET.DATA35,ET.DATA36,ET.DATA37,ET.DATA38,ET.DATA39,ET.DATA40,    \n");
		sql.append("	   ET.DATA41,ET.DATA42,ET.DATA43,ET.DATA44,ET.DATA45,ET.DATA46,ET.DATA47,ET.DATA48,ET.DATA49,ET.DATA50,ED.CONS_NO,ED.TMNL_ASSET_NO,    \n");
		sql.append("	   ED.TMNL_ASSET_NO,ED.ASSET_NO    \n");
		sql.append("FROM E_TMNL_EVENT     ET,    \n");
		sql.append("	 E_DATA_MP        ED    \n");
		sql.append("WHERE ET.ID = ED.ID    \n");
		sql.append("AND ET.AREA_CODE = ED.AREA_CODE    \n");
		paraList.add(eventId);
		sql.append("AND ET.ID = ?    \n");
		paraList.add(areaCode);
		sql.append("AND ET.AREA_CODE = ?    \n");
		paraList.add(eventNo);
		sql.append("AND ET.EVENT_NO = ?    \n");
		this.logger.debug(sql.toString());
		return null;
//		return super.pagingFind(sql.toString(), start, limit, new AmmeterHWindowDtoMapper(), paraList.toArray());
	}
	
	/**
	 * 
	 * 异常窗口映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-10
	 *
	 */
	class AmmeterHWindowDtoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AmmeterHWindowDto ammeterHWindowDto  = new AmmeterHWindowDto();
			try {
				ammeterHWindowDto.setOrgNo(rs.getString("ORG_NO"));
				ammeterHWindowDto.setOrgName(rs.getString("ORG_NAME"));
				ammeterHWindowDto.setConsNo(rs.getString("CONS_NO"));
				ammeterHWindowDto.setConsName(rs.getString("CONS_NAME"));
				ammeterHWindowDto.setAssetNo(rs.getString("ASSET_NO"));
				ammeterHWindowDto.setEventNo(rs.getString("EVENT_NO"));
				ammeterHWindowDto.setEventName(rs.getString("EVENT_NAME"));
				ammeterHWindowDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				ammeterHWindowDto.setEventTime(rs.getTimestamp("EVENT_TIME"));
				ammeterHWindowDto.setReceiveTime(rs.getTimestamp("RECEIVE_TIME"));
				ammeterHWindowDto.setStore_type(rs.getString("STORE_TYPE"));
				ammeterHWindowDto.setFromType(rs.getString("FROM_TYPE"));
				ammeterHWindowDto.setEventData(rs.getString("EVENT_DATA"));
				ammeterHWindowDto.setData1(rs.getString("DATA1"));
				ammeterHWindowDto.setData2(rs.getString("DATA2"));
				ammeterHWindowDto.setData3(rs.getString("DATA3"));
				ammeterHWindowDto.setData4(rs.getString("DATA4"));
				ammeterHWindowDto.setData5(rs.getString("DATA5"));
				ammeterHWindowDto.setData6(rs.getString("DATA6"));
				ammeterHWindowDto.setData7(rs.getString("DATA7"));
				ammeterHWindowDto.setData8(rs.getString("DATA8"));
				ammeterHWindowDto.setData9(rs.getString("DATA9"));
				ammeterHWindowDto.setData10(rs.getString("DATA10"));
				ammeterHWindowDto.setData11(rs.getString("DATA11"));
				ammeterHWindowDto.setData12(rs.getString("DATA12"));
				ammeterHWindowDto.setData13(rs.getString("DATA13"));
				ammeterHWindowDto.setData14(rs.getString("DATA14"));
				ammeterHWindowDto.setData15(rs.getString("DATA15"));
				ammeterHWindowDto.setData16(rs.getString("DATA16"));
				ammeterHWindowDto.setData17(rs.getString("DATA17"));
				ammeterHWindowDto.setData18(rs.getString("DATA18"));
				ammeterHWindowDto.setData19(rs.getString("DATA19"));
				ammeterHWindowDto.setData20(rs.getString("DATA20"));
				ammeterHWindowDto.setData21(rs.getString("DATA21"));
				ammeterHWindowDto.setData22(rs.getString("DATA22"));
				ammeterHWindowDto.setData23(rs.getString("DATA23"));
				ammeterHWindowDto.setData24(rs.getString("DATA24"));
				ammeterHWindowDto.setData25(rs.getString("DATA25"));
				ammeterHWindowDto.setData26(rs.getString("DATA26"));
				ammeterHWindowDto.setData27(rs.getString("DATA27"));
				ammeterHWindowDto.setData28(rs.getString("DATA28"));
				ammeterHWindowDto.setData29(rs.getString("DATA29"));
				ammeterHWindowDto.setData30(rs.getString("DATA30"));
				ammeterHWindowDto.setData31(rs.getString("DATA31"));
				ammeterHWindowDto.setData32(rs.getString("DATA32"));
				ammeterHWindowDto.setData33(rs.getString("DATA33"));
				ammeterHWindowDto.setData34(rs.getString("DATA34"));
				ammeterHWindowDto.setData35(rs.getString("DATA35"));
				ammeterHWindowDto.setData36(rs.getString("DATA36"));
				ammeterHWindowDto.setData37(rs.getString("DATA37"));
				ammeterHWindowDto.setData38(rs.getString("DATA38"));
				ammeterHWindowDto.setData39(rs.getString("DATA39"));
				ammeterHWindowDto.setData40(rs.getString("DATA40"));
				ammeterHWindowDto.setData41(rs.getString("DATA41"));
				ammeterHWindowDto.setData42(rs.getString("DATA42"));
				ammeterHWindowDto.setData43(rs.getString("DATA43"));
				ammeterHWindowDto.setData44(rs.getString("DATA44"));
				ammeterHWindowDto.setData45(rs.getString("DATA45"));
				ammeterHWindowDto.setData46(rs.getString("DATA46"));
				ammeterHWindowDto.setData47(rs.getString("DATA47"));
				ammeterHWindowDto.setData48(rs.getString("DATA48"));
				ammeterHWindowDto.setData49(rs.getString("DATA49"));
				ammeterHWindowDto.setData50(rs.getString("DATA50"));
				return ammeterHWindowDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

	

	
}
