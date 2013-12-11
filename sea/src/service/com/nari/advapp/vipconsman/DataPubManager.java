package com.nari.advapp.vipconsman;

import java.util.List;

import com.nari.advapp.BItemCodeMappingBean;
import com.nari.advapp.IPubDetailBean;
import com.nari.advapp.IPubSchemaBean;

public interface DataPubManager {
	/**
	 * 
	 * @param dataType 数据类型
	 * @return 该数据类型包含的数据项
	 */
	public List<BItemCodeMappingBean> queryItemCode(String dataType) throws Exception;
	
	/**
	 * 
	 * @param dataType 数据类型
	 * @param consType 用户类型
	 * @return 该数据类型发布机制
	 * @throws Exception 
	 */
	public List<IPubSchemaBean> queryPubSchema(String dataType, String consType) throws Exception;

	/**
	 * 
	 * @return 数据类型发布机制模板
	 * @throws Exception 
	 */
	public List<IPubSchemaBean> loadPubSchema(String consType) throws Exception;
	/**
	 * 
	 * @param pubSchemaId 发布计划号
	 * @return 发布数据类型包含数据项
	 * @throws Exception 
	 */
	public List<IPubDetailBean> queryPubDetail(String pubSchemaId, String dataType) throws Exception;
	
	/**
	 * 
	 * @param iPubSchema 发布计划明细
	 * @return void
	 * @throws Exception 
	 */
	public void saveOrUpdatePubSchema(IPubSchemaBean iPubSchema, List<IPubDetailBean> pubDetailList) throws Exception;
	
	/**
	 * 
	 * @param pubDetailList 发布数据项明细
	 * @return void
	 * @throws Exception 
	 */
	public void saveOrUpdatePubDetail(List<IPubDetailBean> pubDetailList) throws Exception;
}
