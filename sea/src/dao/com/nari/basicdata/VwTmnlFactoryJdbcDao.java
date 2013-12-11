package com.nari.basicdata;

import java.util.List;

/**
 * 终端厂家Jdbc操作Dao接口
 * @author ww
 */
public interface VwTmnlFactoryJdbcDao {
	/**
	 * 终端厂家
	 * @param 
	 * @return List<VwTmnlFactory>
	 */
    public List<VwTmnlFactory> findAll() ;
}
