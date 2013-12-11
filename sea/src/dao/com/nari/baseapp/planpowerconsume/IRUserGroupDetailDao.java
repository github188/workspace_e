package com.nari.baseapp.planpowerconsume;

import com.nari.terminalparam.RUserGroupDetail;
import com.nari.terminalparam.RUserGroupDetailId;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者:姜海辉
 * 创建时间：2010-1-11 下午05:02:58 
 * 描述：普通群组明细Dao接口
 */

public interface IRUserGroupDetailDao{
	/**
	 * 添加普通群组明细
	 * @param rUserGroupDetailId 普通群组明细信息
	 * @throws DBAccessException 数据库异常
	 */
	public void save(RUserGroupDetail rUserGroupDetail) throws DBAccessException;
	
	
	/**
	 * 更新普通群组明细
	 * @param rUserGroupDetailId 普通群组明细信息
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdate(RUserGroupDetail rUserGroupDetail) throws DBAccessException;
	
	
	/**
	 * 删除普通群组明细
	 * @param rUserGroupDetail 普通群组明细信息
	 * @throws DBAccessException
	 */
	public void delete(RUserGroupDetailId rUserGroupDetailId)throws DBAccessException;
	
}
