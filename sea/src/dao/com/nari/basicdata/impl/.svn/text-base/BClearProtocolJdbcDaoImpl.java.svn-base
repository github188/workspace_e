package com.nari.basicdata.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.basicdata.BClearProtocol;
import com.nari.basicdata.BClearProtocolJdbcDao;
import com.nari.util.exception.DBAccessException;

/**
 * 实现透明规约相关业务的Jdbc类
 * @author 余涛
 *
 */
public class BClearProtocolJdbcDaoImpl extends JdbcDaoSupport implements BClearProtocolJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 根据模板ID查询相关联的规约
	 * @return 透明规约列表
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<BClearProtocol> findAllByTemplateId(Long templateId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.PROTOCOL_NO, B.PROTOCOL_NAME, B.DATA_TYPE\n")
		.append("  FROM B_CLEAR_PROTOCOL B\n")
		.append("  LEFT JOIN T_TASK_TEMPLATE_DATA T ON T.TEMPLATE_ID = ?\n")
		.append(" WHERE T.PROTOCOL_NO = B.PROTOCOL_NO\n")
		.append(" ORDER BY T.PROTOCOL_NO");
		try{
			List<BClearProtocol> list = new ArrayList<BClearProtocol>();
			list = this.getJdbcTemplate().query(sb.toString(), new Object[]{templateId}, new BClearProtocolRowMapper());
			return list;
		}catch(RuntimeException e){
			this.logger.debug("根据模板ID查询关联的透明规约出错！");
			throw e;
		}
	}
	
	/**
	 * 任务模板中根据数据类型（一类、二类）查询透明规约编码
	 * @param dataType 数据类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BClearProtocol> findAllByDataType(Byte dataType){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT A.PROTOCOL_NO, A.PROTOCOL_NAME, A.DATA_TYPE\n")
		.append("  FROM B_CLEAR_PROTOCOL A\n")
		.append(" WHERE PROTOCOL_NO LIKE '%F'\n")
		.append("   AND EXISTS\n")
		.append(" (SELECT 1\n")
		.append("          FROM B_DATA_MAPPING\n")
		.append("         WHERE DATA_GROUP = ?\n")
		.append("           AND SUBSTR(PROTOCOL_NO,\n")
		.append("                      1,\n")
		.append("                      DECODE(SUBSTR(A.PROTOCOL_NO, -2, 2), 'FF', 4, 5)) =\n")
		.append("               SUBSTR(A.PROTOCOL_NO,\n")
		.append("                      1,\n")
		.append("                      DECODE(SUBSTR(A.PROTOCOL_NO, -2, 2), 'FF', 4, 5)))");
		try{
			List<BClearProtocol> list = new ArrayList<BClearProtocol>();
			list = this.getJdbcTemplate().query(sb.toString(), new Object[]{dataType}, new BClearProtocolRowMapper());
			return list;
		}catch(RuntimeException e){
			this.logger.debug("根据数据类型查询关联的透明规约出错！");
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 内部类　BClearProtocolRowMapper
	 * @author yut
	 * @describe BClearProtocol　RowMapper实现
	 */
	class BClearProtocolRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BClearProtocol bClearProtocol = new BClearProtocol();
			try {
				bClearProtocol.setDataType(rs.getByte("data_type"));
				bClearProtocol.setProtocolName(rs.getString("protocol_name"));
				bClearProtocol.setProtocolNo(rs.getString("protocol_no"));
				return bClearProtocol;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
