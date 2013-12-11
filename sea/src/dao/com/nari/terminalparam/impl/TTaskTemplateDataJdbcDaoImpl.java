package com.nari.terminalparam.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.terminalparam.TTaskTemplateDataJdbcDao;
import com.nari.util.exception.DBAccessException;

public class TTaskTemplateDataJdbcDaoImpl extends JdbcDaoSupport implements TTaskTemplateDataJdbcDao {

	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 根据模板id删除表中相关所有记录
	 * @param templateId 模板id
	 * @throws DBAccessException
	 */
	public void deleteByTemplateId(Long templateId) throws DBAccessException{
		String sql = "delete t_task_template_data where template_id = ?";
		try{
			this.getJdbcTemplate().update(sql,new Object[]{templateId});
		}catch(RuntimeException e){
			this.logger.debug("根据模板ID删除模板所有关联数据项出错！");
			throw e;
		}
	}
	
	/**
	 * 查询任务模板序列号
	 * @return
	 * @throws DBAccessException
	 */
	public Long querySequence() throws DBAccessException{
		String sql = "SELECT s_t_task_template.nextval FROM dual";
		try{
			return this.getJdbcTemplate().queryForLong(sql);
		}catch (RuntimeException e) {
			this.logger.info("查询任务模板序列号出错");
			throw e;
		}
	}

	/**
	 * 根据任务号查询任务模板
	 * @param taskNo 任务号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTaskTemplate> queryTemplateByTaskNo(String taskNo) throws DBAccessException{
		String sql = "SELECT t.template_id,t.template_name,t.task_property FROM t_task_template t " +
				"WHERE t.task_no = ? AND t.is_cancel = 0";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{taskNo}, new TTaskTemplateRowMapper());
		}catch (RuntimeException e) {
			this.logger.info("查询任务模板序列号出错");
			throw e;
		}
	}
	
	
	/**
	 * 内部类　TTaskTemplateRowMapper
	 * @author yut
	 * @describe BClearProtocol　RowMapper实现
	 */
	class TTaskTemplateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTaskTemplate tTaskTemplate = new TTaskTemplate();
			try {
				tTaskTemplate.setTemplateId(rs.getLong("template_id"));
				tTaskTemplate.setTemplateName(rs.getString("template_name"));
				tTaskTemplate.setTaskProperty(rs.getString("task_property"));
				return tTaskTemplate;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
