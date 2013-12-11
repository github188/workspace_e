
package com.nari.baseapp.planpowerconsume;

import com.nari.util.exception.DBAccessException;

/**
 * 获取prot_item_no相关信息JdbcDao接口
 * @author 姜海辉
 *
 */
public interface IProtItemNoJdbcDao {
	
   /**
    * 查询终端规约码
    * @param tmnlAssetNo 终端资产编号
    * @return
    */
	public String findProtocolCode(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 查询prot_item_no
	 * @param protocolNo 规约编号
	 * @return
	 * @throws DBAccessException
	 */
	public String[] findProtItemNo(String protocolNo)throws DBAccessException;
}
