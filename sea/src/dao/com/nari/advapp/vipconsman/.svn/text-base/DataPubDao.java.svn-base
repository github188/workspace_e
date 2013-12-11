package com.nari.advapp.vipconsman;

import java.util.List;

import com.nari.advapp.BItemCodeMappingBean;
import com.nari.advapp.IPubDetailBean;
import com.nari.advapp.IPubSchemaBean;

public interface DataPubDao {
	/**
	 * 
	 * @param dataType 数据类型
	 * @return 该数据类型包含的数据项
	 */
	public List<BItemCodeMappingBean> queryItemCode(String dataType);
	
	/**
	 * 
	 * @param dataType 数据类型
	 * @param consType 用户类型
	 * @return 该数据类型发布机制
	 */
	public List<IPubSchemaBean> queryPubSchema(String dataType, String consType);
	
	/**
	 * 
	 * @return 所有发布数据模板
	 */
	public List<IPubSchemaBean> loadPubSchema(String consType);
	
	/**
	 * 
	 * @param pubSchemaId 发布计划号
	 * @return 发布数据类型包含数据项
	 */
	public List<IPubDetailBean> queryPubDetail(String pubSchemaId, String dataType);
	
	/**
	 * 
	 * @param iPubSchema 发布计划明细
	 * @return void
	 */
	public void saveOrUpdatePubSchema(IPubSchemaBean iPubSchema);
	
	/**
	 * 
	 * @param pubDetailList 发布数据项明细
	 * @return void
	 */
	public void saveOrUpdatePubDetail(List<IPubDetailBean> pubDetailList);
	
	/**
	 * 
	 * @param pubDetailList 发布数据项明细
	 * @return void
	 */
	public void deletePubDetailById(String pubSchemaId);
}
