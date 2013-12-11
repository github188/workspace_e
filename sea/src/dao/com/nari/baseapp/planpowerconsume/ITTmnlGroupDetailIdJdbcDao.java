package com.nari.baseapp.planpowerconsume;

import com.nari.support.Page;
import com.nari.terminalparam.TTmnlGroupDetailDto;
import com.nari.util.exception.DBAccessException;


/**
 * 群组明细JdbcDao接口
 * @author 姜海辉
 *
 */

public interface ITTmnlGroupDetailIdJdbcDao {
   /**
    * 删除控制群组明细
    * @param groupNo 群组编号
    * @throws DBAccessException
    */
	public void deleteCtrlGroupDetail(String groupNo)throws DBAccessException;
	
	/**
    * 删除控制群组明细
    * @param groupNo 群组编号
    * @throws DBAccessException
    */
	public void deleteCommonGroupDetail(String groupNo)throws DBAccessException;
	
	
	/**
	 * 查询控制群组明细
	 * @param groupNo 群组编号
	 * @param consNo  用户编号
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDetailDto> queryCtrlGroupDetail(String groupNo,String consNo,long start,long limit)throws DBAccessException;
	
	/**
	 * 查询普通群组明细
	 * @param groupNo 群组编号
	 * @param consNo  用户编号
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDetailDto> queryCommonGroupDetail(String groupNo,String consNo,long start,long limit)throws DBAccessException;
	
}
