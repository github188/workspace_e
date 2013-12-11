package com.nari.baseapp.planpowerconsume;

import com.nari.util.exception.DBAccessException;

/**
 * 获取prot_item_no相关信息接口
 * @author 姜海辉
 *
 */
public interface IProtItemNoManager {
   /**
    * 查询终端规约码
    * @param tmnlAssetNo
    * @return
    */
	public String qureyProtocolCode(String tmnlAssetNo)throws DBAccessException;
	
	/**
	 * 查询规约项编码
	 * @param protocolCode
	 * @return
	 * @throws DBAccessException
	 */
	public String[] qureyProtItemNo(String tmnlAssetNo,String type,Integer fn)throws DBAccessException;
	
}
