package com.nari.terminalparam;

import java.util.List;

import com.nari.ami.database.map.terminalparam.TTaskTemplate;

import com.nari.util.exception.DBAccessException;

public interface TTaskTemplateDataJdbcDao {
	
	/**
	 * 根据模板id删除表中相关所有记录
	 * @param templateId 模板id
	 * @throws DBAccessException
	 */
	public void deleteByTemplateId(Long templateId) throws DBAccessException;

	/**
	 * 查询任务模板序列号
	 * @return
	 * @throws DBAccessException
	 */
	public Long querySequence() throws DBAccessException;
	
	/**
	 * 根据任务号查询任务模板
	 * @param taskNo 任务号
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> queryTemplateByTaskNo(String taskNo) throws DBAccessException;
}
