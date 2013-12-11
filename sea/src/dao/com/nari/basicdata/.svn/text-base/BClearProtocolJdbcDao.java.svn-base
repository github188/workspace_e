package com.nari.basicdata;

import java.util.List;

import com.nari.util.exception.DBAccessException;


/**
 * 实现透明规约相关业务的Jdbc类
 * @author 余涛
 *
 */
public interface BClearProtocolJdbcDao{
	
	/**
	 * 根据模板ID查询相关联的规约
	 * @return 透明规约列表
	 * @throws DBAccessException
	 */
	public List<BClearProtocol> findAllByTemplateId(Long templateId);
	
	/**
	 * 任务模板中根据数据类型（一类、二类）查询透明规约编码
	 * @param dataType 数据类型
	 * @return
	 */
	public List<BClearProtocol> findAllByDataType(Byte dataType);
	
}
