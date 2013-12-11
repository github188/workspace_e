package com.nari.basicdata;

import java.util.List;

/**
 * 限电类型Jdbc操作Dao接口
 * @author 姜炜超
 */
public interface VwLimitTypeJdbcDao {
	/**
	 * 查询限电类型
	 * @param 
	 * @return List<VwLimitType>
	 */
    public List<VwLimitType> findAll() ;
}
