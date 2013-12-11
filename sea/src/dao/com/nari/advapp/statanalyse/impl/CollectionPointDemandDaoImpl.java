package com.nari.advapp.statanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.statanalyse.CollectionPointDemandDao;
import com.nari.advapp.statanalyse.CollectionPointDemandDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 测量点需量分析 DAO实现类
 * 
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public class CollectionPointDemandDaoImpl extends JdbcBaseDAOImpl implements
		CollectionPointDemandDao {

	/* (non-Javadoc)
	 * @see com.nari.advapp.chargeMonitor.CollectionPointDemandDao#findCollectionPointDemandDate(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date)
	 */
	@Override
	public Page<CollectionPointDemandDto> findCollectionPointDemandDate(String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		List<Object> paraList = new ArrayList<Object>();
		
		sb.append("SELECT O.ORG_NO,  \n");
		sb.append("		  O.ORG_NAME,  \n");
		sb.append("		  C.CONS_NO,  \n");
		sb.append("		  C.CONS_NAME,  \n");
		sb.append("		 C.CONTRACT_CAP,  \n");
		sb.append("		 MP.TMNL_ASSET_NO,  \n");
		sb.append("		 MP.TERMINAL_ADDR,  \n");
		sb.append("		 MP.ASSET_NO,  \n");
		sb.append("		  DE.DATA_DATE,  \n");
		sb.append("		  DE.COL_TIME,  \n");
		sb.append("		  DE.CT,  \n");
		sb.append("		  DE.PT,	\n");
		sb.append("		  DE.MARK,	\n");
		sb.append("		  DE.PAP_DEMAND,	\n");
		sb.append("		  DE.PAP_DEMAND_TIME,	\n");
		sb.append("		  DE.PRP_DEMAND,	\n");
		sb.append("		  DE.PRP_DEMAND_TIME,	\n");
		sb.append("       DE.RAP_DEMAND,	\n");
		sb.append("       DE.RAP_DEMAND_TIME,	\n");
		sb.append("       DE.RRP_DEMAND,	\n");
		sb.append("       DE.RRP_DEMAND_TIME	\n");
		sb.append("  FROM E_DATA_MP MP, C_CONS C, O_ORG O, 	 \n");
		sb.append("       (SELECT DE.ID,DE.DATA_DATE,DE.COL_TIME,DE.CT,DE.PT, DE.MARK,DE.PAP_DEMAND,DE.PAP_DEMAND_TIME,  	\n");
		sb.append(" 					 DE.PRP_DEMAND,  DE.PRP_DEMAND_TIME,DE.RAP_DEMAND,DE.RAP_DEMAND_TIME, DE.RRP_DEMAND,DE.RRP_DEMAND_TIME	\n");
		sb.append(" 					 FROM E_MP_DAY_DEMAND DE	\n");
		sb.append("       WHERE DE.DATA_DATE >= TO_DATE(?, 'yyyy-mm-dd')	\n");
		sb.append("       AND DE.DATA_DATE < TO_DATE(?, 'yyyy-mm-dd')) DE	\n");
		paraList.add(sdf.format(startDate));
		paraList.add(sdf.format(endDate));
		
		sb.append("WHERE \n");
		sb.append("  MP.ID = DE.ID	\n");
		sb.append("  AND MP.CONS_NO =  C.CONS_NO  \n");
		sb.append("AND C.ORG_NO = O.ORG_NO   \n");

		if ("org".equals(nodeType)) {
			if(!userInfo.getAccessLevel().equals("02")){
				nodeValue = nodeValue + "%";
				paraList.add(nodeValue);
				sb.append("  AND MP.ORG_NO LIKE ? \n");
			}
		}else if ("usr".equals(nodeType)) {
			paraList.add(nodeValue);
			sb.append("   AND MP.CONS_NO = ? \n");
		} else {
			return null;
		}
//		sb.append("    AND EXISTS (SELECT 1 \n");
//		sb.append("            FROM P_ACCESS_ORG P_A, P_SYS_USER P_U \n");
//		sb.append("            WHERE P_A.STAFF_NO = P_U.STAFF_NO \n");
//		sb.append("            AND P_A.ORG_NO =  O.ORG_NO \n");
//		sb.append("            AND P_A.STAFF_NO = ?  ) \n");
//		paraList.add(userInfo.getStaffNo());
		
		
		logger.debug(sb.toString());
		return this.pagingFind(sb.toString(), start, limit, new CollectionPointDemandRowMap(),paraList.toArray());
	}
	
	

	/* (non-Javadoc)
	 * @see com.nari.advapp.chargeMonitor.CollectionPointDemandDao#findCollectionPointDemandMonth(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date)
	 */
	@Override
	public Page<CollectionPointDemandDto> findCollectionPointDemandMonth(String orgType,
			String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		List<Object> paraList = new ArrayList<Object>();
		
		sb.append("SELECT O.ORG_NO,  \n");
		sb.append("		  O.ORG_NAME,  \n");
		sb.append("		  C.CONS_NO,  \n");
		sb.append("		  C.CONS_NAME,  \n"); 
		sb.append("		 C.CONTRACT_CAP,  \n");
		sb.append("		 MP.TMNL_ASSET_NO,  \n");
		sb.append("		 MP.TERMINAL_ADDR,  \n");
		sb.append("		 MP.ASSET_NO,  \n");
		sb.append("		  DE.DATA_DATE,  \n");
		sb.append("		  DE.COL_TIME,  \n");
		sb.append("		  DE.CT,  \n");
		sb.append("		  DE.PT,	\n");
		sb.append("		  DE.MARK,	\n");
		sb.append("		  DE.PAP_DEMAND,	\n");
		sb.append("		  DE.PAP_DEMAND_TIME,	\n");
		sb.append("		  DE.PRP_DEMAND,	\n");
		sb.append("		  DE.PRP_DEMAND_TIME,	\n");
		sb.append("       DE.RAP_DEMAND,	\n");
		sb.append("       DE.RAP_DEMAND_TIME,	\n");
		sb.append("       DE.RRP_DEMAND,	\n");
		sb.append("       DE.RRP_DEMAND_TIME	\n");
		sb.append("  FROM E_DATA_MP MP, C_CONS C, O_ORG O, 	 \n");
		sb.append("       (SELECT DE.ID,DE.DATA_DATE,DE.COL_TIME,DE.CT,DE.PT, DE.MARK,DE.PAP_DEMAND,DE.PAP_DEMAND_TIME,  	\n");
		sb.append(" 					 DE.PRP_DEMAND,  DE.PRP_DEMAND_TIME,DE.RAP_DEMAND,DE.RAP_DEMAND_TIME, DE.RRP_DEMAND,DE.RRP_DEMAND_TIME	\n");
		sb.append(" 					 FROM E_MP_MON_DEMAND DE	\n");
		sb.append("       WHERE DE.DATA_DATE >= TO_DATE(?, 'yyyy-mm-dd')	\n");
		sb.append("       AND DE.DATA_DATE < TO_DATE(?, 'yyyy-mm-dd')) DE	\n");
		paraList.add(sdf.format(startDate));
		paraList.add(sdf.format(endDate));
		
		sb.append("WHERE \n");
		sb.append("  MP.ID = DE.ID	\n");
		sb.append("   AND MP.CONS_NO =  C.CONS_NO  \n");
		sb.append("AND C.ORG_NO = O.ORG_NO   \n");
		
		if ("org".equals(nodeType)) {
			if(!userInfo.getAccessLevel().equals("02")){
				nodeValue = nodeValue + "%";
				paraList.add(nodeValue);
				sb.append("  AND MP.ORG_NO LIKE ? \n");
			}
		}else if ("usr".equals(nodeType)) {
			paraList.add(nodeValue);
			sb.append("   AND MP.CONS_NO = ? \n");
		} else {
			return null;
		}
//		sb.append("    AND EXISTS (SELECT 1 \n");
//		sb.append("            FROM P_ACCESS_ORG P_A, P_SYS_USER P_U \n");
//		sb.append("            WHERE P_A.STAFF_NO = P_U.STAFF_NO \n");
//		sb.append("            AND P_A.ORG_NO =  O.ORG_NO \n");
//		sb.append("            AND P_A.STAFF_NO = ?  ) \n");
//		paraList.add(userInfo.getStaffNo());
		
		logger.debug(sb.toString());
		return this.pagingFind(sb.toString(), start, limit, new CollectionPointDemandRowMap(),paraList.toArray());
	}
	/**
	 * 
	 * 测量点需量分析映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-29
	 *
	 */
	class CollectionPointDemandRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CollectionPointDemandDto cpdDto = new CollectionPointDemandDto();
			try {
				cpdDto.setOrgNo(rs.getString("ORG_NO"));
				cpdDto.setOrgName(rs.getString("ORG_NAME"));
				cpdDto.setConsNo(rs.getString("CONS_NO"));
				cpdDto.setConsName(rs.getString("CONS_NAME"));
				cpdDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				cpdDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				cpdDto.setAssetNo(rs.getString("ASSET_NO"));
				cpdDto.setDataTime(rs.getDate("DATA_DATE"));
				cpdDto.setColTime(rs.getTimestamp("COL_TIME"));
				cpdDto.setMultiple(rs.getDouble("CT")*rs.getDouble("PT"));
				cpdDto.setContractCap(rs.getString("CONTRACT_CAP"));
				cpdDto.setMark(rs.getString("MARK"));
				cpdDto.setPapDemand(rs.getString("PAP_DEMAND"));
				cpdDto.setPapDemandTime(rs.getTimestamp("PAP_DEMAND_TIME"));
				cpdDto.setPrpDemand(rs.getString("PRP_DEMAND"));
				cpdDto.setPrpDemandTime(rs.getTimestamp("PRP_DEMAND_TIME"));
				cpdDto.setRapDemand(rs.getString("RAP_DEMAND"));
				cpdDto.setRapDemandTime(rs.getTimestamp("RAP_DEMAND_TIME"));
				cpdDto.setRrpDemand(rs.getString("RRP_DEMAND"));
				cpdDto.setRrpDemandTime(rs.getTimestamp("RRP_DEMAND_TIME"));
				return cpdDto;

			} catch (Exception e) {
				return null;
			}
		}

}
}
