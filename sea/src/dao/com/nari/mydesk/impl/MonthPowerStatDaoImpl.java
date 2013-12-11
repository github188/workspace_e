package com.nari.mydesk.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.mydesk.MonthPowerStatDao;
import com.nari.mydesk.MonthPowerStatDto;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.AutoSendQuery;

/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-8-20
 *
 */
public class MonthPowerStatDaoImpl extends JdbcBaseDAOImpl implements MonthPowerStatDao {

	/* (non-Javadoc)
	 * @see com.nari.mydesk.MonthPowerStatDao#findMonthPowerStat(com.nari.privilige.PSysUser, java.lang.String)
	 */
	@Override
	public List<MonthPowerStatDto> findMonthPowerStat(PSysUser userInfo,
			String queryDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		sql.append("   SELECT  M.ENERGY_VAL, ET.ELEC_TYPE  \n");
		sql.append("  FROM A_CIS_SPQ_M M, VW_ELEC_TYPE_CODE ET  \n");
		sql.append("   WHERE M.ELEC_TYPE_CODE = ET.ELEC_TYPE_CODE   \n");
		sql.append("   AND M.ORG_NO = ?    \n");
		parms.add(userInfo.getOrgNo());
		sql.append("   AND TO_CHAR(M.STAT_DATE, 'YYYY-MM') = ?    \n");
		parms.add(queryDate);
		sql.append("    ORDER BY M.ENERGY_VAL DESC  \n");
		logger.debug(sql.toString());
		
		return super.getJdbcTemplate().query(sql.toString(),parms.toArray(), new MonthPowerStatRowMapper());
	}
	
	/**
	 * 
	 * 上月电量统计
	 * 
	 * @author ChunMingLi
	 * @since 2010-8-20
	 *
	 */
	class MonthPowerStatRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MonthPowerStatDto dto = new MonthPowerStatDto();
			try {
				dto.setElecType(rs.getString("ELEC_TYPE"));
				dto.setEnergyVal(rs.getDouble("ENERGY_VAL"));
				return dto;
			} catch (Exception e) {
				logger.debug(e);
				return null;
			}
		}
	}

}