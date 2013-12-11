package com.nari.sysman.templateman;

import java.util.List;

import com.nari.util.exception.DBAccessException;

/**
 * 终端规约类型DAO接口
 * @author 鲁兆淞
 */
public interface VwProtocolCodeDao {

	/**
	 * 查询所有终端规约类型带排序。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 终端规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwProtocolCode> findAll(String orderBy, String order) throws DBAccessException;

}
