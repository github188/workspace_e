package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.ChannelMonitorOrgNoDto;
import com.nari.runman.runstatusman.OnOffLineMonitorDto;
import com.nari.runman.runstatusman.OnlineMonitorDao;
import com.nari.runman.runstatusman.impl.ChannelMonitorDaoImpl.CMOrgNoMapper;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class OnlineMonitorDaoImpl extends JdbcBaseDAOImpl implements
		OnlineMonitorDao {

	@Override
	public Page<OnOffLineMonitorDto> findPSysUsers(long start, long limit)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT O.ORG_NAME, \n");
		sb.append("P.STAFF_NO,P.NAME,P.IP  \n");
		sb.append(" FROM O_ORG O, P_SYS_USER P  \n");
		sb.append("WHERE O.ORG_NO = P.ORG_NO  \n ");
		sb.append("AND P.ACCESS_LEVEL > '02'  \n ");
		String sql = sb.toString();
		logger.debug(sql);
		return pagingFind(sql, start, limit, new OnOffLineConsMapper());

	}

	/**
	 * 
	 * ChannelMonitorOrgNoDto 映射类
	 * 
	 */
	class OnOffLineConsMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OnOffLineMonitorDto onofflineDto = new OnOffLineMonitorDto();
			try {
				onofflineDto.setOrgName(rs.getString("ORG_NAME"));
				onofflineDto.setStaffNo(rs.getString("STAFF_NO"));
				onofflineDto.setStaffName(rs.getString("NAME"));
				onofflineDto.setBoundIp(rs.getString("IP"));
				return onofflineDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
