package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.util.exception.DBAccessException;

/**
 * 规约类型DAO接口
 * @author longkc
 *
 */
public interface VwProtocolCodeDao {
	/**
	 * 查询规约类型
	 * @return 规约类型
	 */
	public List<VwProtocolCodeBean> findType() throws DBAccessException;

}
