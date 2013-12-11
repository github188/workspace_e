package com.nari.sysman.securityman;

import java.util.List;

/**
 * 接口 字典表访问接口
 * 
 * @author zhangzhw
 * 
 */
public interface IDictionaryDao {
	/**
	 * 方法 getDictionary
	 * 
	 * @param sql
	 * @return  取得字典表列表
	 */
	public List getDictionary(String sql);

}
