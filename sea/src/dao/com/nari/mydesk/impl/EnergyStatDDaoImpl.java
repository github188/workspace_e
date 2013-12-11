package com.nari.mydesk.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.mydesk.EnergyStatDDao;
import com.nari.mydesk.EnergyStatDDto;
import com.nari.privilige.PSysUser;

/**
 * 
 * 监视主页每日电量统计 dao
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public class EnergyStatDDaoImpl extends JdbcBaseDAOImpl implements
		EnergyStatDDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EnergyStatDDto> findEnergyStatDay(String provinceName,PSysUser userInfo,
			String startDate, String endDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		/*
		 * sql.append("  SELECT MD.ORG_NO,  O.ORG_SHORT_NAME, MD.UP_ENERGY, MD.PPQ,  \n");
		sql.append("		 MD.SPQ, MD.MUT_ENERGY, O.ORG_TYPE \n");
		sql.append("		FROM A_ORG_ENERGY_D MD, O_ORG O  \n");
		sql.append("		WHERE O.ORG_NO = MD.ORG_NO  \n");
		sql.append("		 AND MD.STAT_DATE = TO_DATE(? , 'yyyy-mm-dd')  \n");
		//sql.append("		AND MD.STAT_DATE < TO_DATE(? , 'yyyy-mm-dd')  \n");
		sql.append("		ORDER BY O.ORG_NO  \n");
		paraList.add(startDate);
		//paraList.add(endDate);
		sql.append("    AND EXISTS (SELECT 1 \n");
		sql.append("            FROM P_ACCESS_ORG P_A, P_SYS_USER P_U \n");
		sql.append("            WHERE P_A.STAFF_NO = P_U.STAFF_NO \n");
		sql.append("            AND P_A.ORG_NO =  O.ORG_NO \n");
		sql.append("            AND P_A.STAFF_NO = ?  ) \n");
		paraList.add(userInfo.getStaffNo());
		*/
		
		
		/*sql.append("SELECT '' ORG_NAME , to_char(ROWNUM)  ORG_NO ,ETD.VALUE DATA_VALUE, ETD.ENERGY_DET_TYPE ETD_FLAG \n");
		sql.append("FROM A_ORG_ENERGY_DET_D ETD \n");
		sql.append("WHERE ETD.ENERGY_DET_TYPE IN ('01', '02', '6', '7', '8') \n");
		sql.append("AND ETD.STAT_DATE = TRUNC(TO_DATE(? , 'yyyy-mm-dd')) \n");
		paraList.add(startDate);
		sql.append("UNION  \n");
		sql.append("SELECT '青海省上网电量' ORG_NAME, '6' ORG_NO, ED.UP_ENERGY  DATA_VALUE , ' ' ETD_FLAG \n");
		sql.append("FROM A_ORG_ENERGY_D ED  \n");
		sql.append("WHERE  ED.STAT_DATE = TRUNC(TO_DATE(? , 'yyyy-mm-dd')) AND ED.ORG_NO = '63101' \n");
		paraList.add(startDate);
		sql.append("UNION  \n");
		sql.append("SELECT O.ORG_NAME||'供电量' ORG_NAME,O.ORG_NO , ED.PPQ DATA_VALUE, ' 'ETD_FLAG \n");
		sql.append("FROM O_ORG O, A_ORG_ENERGY_D ED \n");
		sql.append("WHERE ED.ORG_NO = O.ORG_NO \n");
		sql.append("AND ED.ORG_NO NOT IN '63101' \n");
		sql.append("AND ED.STAT_DATE = TRUNC(TO_DATE(? , 'yyyy-mm-dd')) \n");
		paraList.add(startDate);
		sql.append("ORDER BY ORG_NO \n");*/
		
		//=======
		provinceName = provinceName + "省";
		sql.append(" SELECT O.ORG_NAME, TO_CHAR(ROWNUM) ORG_NO, ETD.VALUE DATA_VALUE, ETD.ENERGY_DET_TYPE ETD_FLAG \n");
		sql.append(" FROM A_ORG_ENERGY_DET_D ETD ,(SELECT EDT.CONS_TYPE_NAME ORG_NAME, EDT.CONS_TYPE \n");
		sql.append(" FROM VW_ENERGY_DET_TYPE EDT  WHERE EDT.CONS_TYPE IN ('01', '02', '6', '7', '8') ORDER BY EDT.CONS_TYPE ) O \n");
		sql.append("  WHERE    O.CONS_TYPE = ETD.ENERGY_DET_TYPE \n");
		sql.append("  AND ETD.ENERGY_DET_TYPE IN ('01', '02', '6', '7', '8') \n");
		sql.append(" AND ETD.STAT_DATE = TRUNC(TO_DATE(? , 'yyyy-mm-dd')) \n");
		paraList.add(startDate);
		sql.append("  AND ORG_NO = (SELECT ORG_NO  FROM O_ORG WHERE P_ORG_NO = '00000'  AND ROWNUM = 1) \n");
		sql.append("  UNION ALL \n");
		sql.append(" SELECT DECODE(AD.ORG_NO,  (SELECT ORG_NO FROM O_ORG WHERE P_ORG_NO = '00000' AND ROWNUM = 1), '" + provinceName + "'||'上网电量', O.ORG_NAME||'供电量') AS ORG_NAME, AD.ORG_NO, DECODE(AD.ORG_NO,   \n");
		sql.append(" (SELECT ORG_NO FROM O_ORG WHERE P_ORG_NO = '00000' AND ROWNUM = 1), AD.UP_ENERGY, AD.PPQ) AS DATA_VALUE, O.ORG_SHORT_NAME \n");
		sql.append("  FROM A_ORG_ENERGY_D AD \n");
		sql.append(" JOIN O_ORG O   ON (O.ORG_NO = AD.ORG_NO) \n");
		sql.append("  WHERE AD.STAT_DATE = TRUNC(TO_DATE(? , 'yyyy-mm-dd')) \n");
		paraList.add(startDate);
		sql.append(" ORDER BY ORG_NO  \n");
		
		
		 
		 
		logger.debug(sql.toString());
		return super.getJdbcTemplate().query(sql.toString(), paraList.toArray(),new EnergyStatDDtoRowMap());
	}
	/**
	 * 
	 * 查询主页每日电量统计SQL映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-29
	 *
	 */
	class EnergyStatDDtoRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EnergyStatDDto statDDto = new EnergyStatDDto();
			try {
				statDDto.setOrgNo(rs.getString("ORG_NO"));
				statDDto.setOrgName(rs.getString("ORG_NAME"));
				statDDto.setDataValue(rs.getDouble("DATA_VALUE"));
				statDDto.setEtdFlag(rs.getString("ETD_FLAG"));
				return statDDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	@Override
	public List<String> queryProvinceName() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O.ORG_NAME FROM O_ORG O WHERE P_ORG_NO = '00000' AND ROWNUM = 1");
		logger.debug(sql.toString());
		return super.getJdbcTemplate().query(sql.toString(), new ProvinceNameRowMap());
	}
	
	
	class ProvinceNameRowMap implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			String str = new String();
			try {
				str = rs.getString("ORG_NAME");
				return str;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
