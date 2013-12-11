package com.nari.qrystat.taskQuery.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.taskQuery.TaskStatTypeDto;
import com.nari.qrystat.taskQuery.TaskStateDao;
import com.nari.qrystat.taskQuery.TaskStateArea;

/**
 * 
 * 工单查询统计DAO实现类
 * 
 * @author ChunMingLi
 * 2010-4-28
 *
 */
public class TaskStateDaoImpl extends JdbcBaseDAOImpl implements TaskStateDao {

	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateDao#findTaskOrgNo()
	 */
	public List<String> findTaskOrgNo(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O.ORG_NO  /n");
		sql.append("FROM O_ORG O   /n");
		sql.append("GROUP BY O.ORG_NO  /n");
		this.logger.debug(sql.toString());
		try{
			return this.getJdbcTemplate().query(sql.toString(), new TaskStateOrgNameRowMapper());
		}catch(RuntimeException e){
			this.logger.debug(sql);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateDao#findTaskOrgName()
	 */
	public List<String> findTaskOrgNo(Date dateFrom, Date dateTo){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT O.ORG_NO  \n");
		sb.append("FROM F_EVENT_SRC F,  \n");
		sb.append("     O_ORG O    \n");
		sb.append("WHERE F.ORG_NO = O.ORG_NO    \n");
		sb.append("AND F.EVNET_TIME BETWEEN    \n");
		sb.append("?  \n");
		sb.append("AND  ?  \n");
		sb.append("GROUP BY O.ORG_NO  \n");
		sb.append("UNION     \n");
		sb.append("SELECT O.ORG_NO  \n");
		sb.append("FROM  O_ORG           O,    \n");
		sb.append("      F_EVENT_CLOSE   F    \n");
		sb.append("WHERE F.ORG_NO = O.ORG_NO    \n");
		sb.append("AND F.EVNET_TIME BETWEEN    \n");
		sb.append("?  \n");
		sb.append("AND  ?  \n");
		sb.append("GROUP BY O.ORG_NO  \n");
		
		String sql = sb.toString();
		this.logger.debug(sql);
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{dateFrom, dateTo, dateFrom, dateTo} ,new TaskStateOrgNameRowMapper());
		}catch(RuntimeException e){
			this.logger.debug(sql);
			return null;
		}
	}
	
	/**
	 * 
	 * 供电单位集合映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-5-08
	 *
	 */
	class TaskStateOrgNameRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			try {
				return rs.getString("org_no");
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateDao#findTaskOrgno(java.lang.String, java.lang.String)
	 */
	public List<TaskStateArea> findTaskArea(Date dateFrom, Date dateTo){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT O.ORG_NO,  \n");
		sb.append("   O.ORG_NAME,  \n");
		sb.append("   F.FLOW_STATUS_CODE,  \n");
		sb.append("   F.EVENT_ID  \n");
		sb.append("FROM F_EVENT_SRC F,  \n");
		sb.append("     O_ORG O  \n");
		sb.append("WHERE F.ORG_NO = O.ORG_NO  \n");
		sb.append("AND F.EVNET_TIME BETWEEN  \n");
		sb.append("?  \n");
		sb.append("AND  ?  \n");
		sb.append("UNION   \n");
		sb.append("SELECT O.ORG_NO,  \n");
		sb.append("   O.ORG_NAME,  \n");
		sb.append("   '08' AS FLOW_STATUS_CODE,  \n");
		sb.append("   F.EVENT_ID  \n");
		sb.append("FROM O_ORG O,  \n");
		sb.append("   F_EVENT_CLOSE F  \n");
		sb.append("WHERE F.ORG_NO = O.ORG_NO  \n");
		sb.append("AND F.EVNET_TIME BETWEEN  \n");
		sb.append("?  \n");
		sb.append("AND  ?  \n");
	    
		String sql = sb.toString();
		this.logger.debug(sql);
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{dateFrom, dateTo, dateFrom, dateTo}, new TaskStateQryRowMapper());
		}catch(RuntimeException e){
			this.logger.debug(sql);
			throw e;
		}
	}
	
	/**
	 * 
	 * 工单查询统计映射类
	 * 
	 * @author ChunMingLi
	 * 2010-4-28
	 *
	 */
	class TaskStateQryRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TaskStateArea taskState = new TaskStateArea();
			try {
				taskState.setEventID(rs.getInt("EVENT_ID"));
				taskState.setOrgNo(rs.getString("ORG_NO"));
				taskState.setOrgName(rs.getString("ORG_NAME"));
				taskState.setEventStatuCode(rs.getString("FLOW_STATUS_CODE"));
				return taskState;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateDao#findTask(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TaskStatTypeDto> findTaskType(Date dateFrom, Date dateTo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT  F.EVENT_TYPE,   \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '00' THEN 0 ELSE  NULL  END) OP_TYPE_STATE_DBGD,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '02' THEN 2 ELSE  NULL  END) OP_TYPE_STATE_YXCLZ,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '03' THEN 3 ELSE  NULL  END) OP_TYPE_STATE_ZCGD,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '04' THEN 4 ELSE  NULL  END) OP_TYPE_STATE_WBQR,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '06' THEN 6 ELSE  NULL  END) OP_TYPE_STATE_GQ,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '07' THEN 7 ELSE  NULL  END) OP_TYPE_STATE_BDCLZ,  \n");
		sb.append("		COUNT(CASE WHEN F.FLOW_STATUS_CODE = '08' THEN 8 ELSE  NULL  END) OP_TYPE_STATE_QZGD,  \n");
		sb.append("		COUNT (F.FLOW_STATUS_CODE ) TOTAL  \n");
		sb.append("FROM F_EVENT_SRC F,  \n");
		sb.append("	   O_ORG 	   O  \n");
		sb.append(" WHERE F.ORG_NO = O.ORG_NO  \n");
		sb.append(" AND F.EVNET_TIME BETWEEN ?  \n");
		sb.append("AND   ?  \n");
		sb.append(" GROUP BY F.EVENT_TYPE  \n");
		
		String sql = sb.toString();
		this.logger.debug(sql);
		try{
			return  super.getJdbcTemplate().query(sql, new Object[]{dateFrom,dateTo}, new TaskStateTypeRowMapper());
		}catch(RuntimeException e){
			this.logger.debug(sql);
			return null;
		}
	}
	
	/**
	 * 
	 * 工单查询统计按类型映射类
	 * 
	 * @author ChunMingLi
	 * 2010-4-28
	 *
	 */
	class TaskStateTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TaskStatTypeDto typeDto = new TaskStatTypeDto();
			try {
				typeDto.setEventType(rs.getInt("EVENT_TYPE"));
				typeDto.setOpTypeStateBDCLZ(rs.getInt("OP_TYPE_STATE_BDCLZ"));
				typeDto.setOpTypeStateDBGD(rs.getInt("OP_TYPE_STATE_DBGD"));
				typeDto.setOpTypeStateGQ(rs.getInt("OP_TYPE_STATE_GQ"));
				typeDto.setOpTypeStateQZGD(rs.getInt("OP_TYPE_STATE_QZGD"));
				typeDto.setOpTypeStateWBQR(rs.getInt("OP_TYPE_STATE_WBQR"));
				typeDto.setOpTypeStateYXCLZ(rs.getInt("OP_TYPE_STATE_YXCLZ"));
				typeDto.setOpTypeStateZCGD(rs.getInt("OP_TYPE_STATE_ZCGD"));
				typeDto.setTotal(rs.getInt("TOTAL"));
				return typeDto;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
