package com.nari.advapp.vipconsman;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.advapp.BItemCodeMappingBean;
import com.nari.advapp.IPubDetailBean;
import com.nari.advapp.IPubSchemaBean;

/**
 * 数据发布管理
 * 
 * @author lkc
 * 
 */
public class DataPubAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());

	private boolean success = true;

	// 参数
	private String dataType;
	private String pubSchemaId;
	private String consType;
	private IPubSchemaBean iPubSchema;
	private String schemaJsonStr;
	private String detailJsonStr;

	// 返回结果
	private List<BItemCodeMappingBean> itemCodeList;
	private List<IPubSchemaBean> iPubSchemaList;
	private List<IPubDetailBean> iPubDetailList;
	// 注入service层
	private DataPubManager dataPubManager;

	public void setDataPubManager(DataPubManager dataPubManager) {
		this.dataPubManager = dataPubManager;
	}

	/**
	 * 根据用户类型和数据类型查询发布机制
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryDataItem() throws Exception {
		this.logger.debug("数据发布机制查询开始");
		// 数据类型包含的数据项
		this.setItemCodeList(this.dataPubManager.queryItemCode(this.dataType));
		// 数据类型发布机制
		List<IPubSchemaBean> pubSchList = this.dataPubManager.queryPubSchema(
				this.dataType, this.consType);
		this.setiPubSchemaList(pubSchList);
		if (pubSchList.size() == 1) {
			// 发布数据类型包含数据项
			this.setiPubDetailList(this.dataPubManager.queryPubDetail(
					pubSchList.get(0).getPubSchemaId(), this.dataType));
		}
		this.logger.debug("数据发布机制查询结束");
		return SUCCESS;
	}

	public String queryPubDataItem() throws Exception {
		this.logger.debug("发布数据类型对应数据项编码查询开始");

		this.logger.debug("发布数据类型对应数据项编码查询结束");
		return SUCCESS;
	}

	public String loadPubSchema() throws Exception {
		this.logger.debug("数据类型发布计划模板查询开始");
		this.setiPubSchemaList(this.dataPubManager.loadPubSchema(this.consType));
		this.logger.debug("数据类型发布计划模板查询结束");
		return SUCCESS;
	}

	public String savePubSchema() throws Exception {
		this.logger.debug("数据类型对应发布计划保存开始");
		JSONArray iPubSchema = JSONArray.fromObject(this.schemaJsonStr);

		JSONArray iPubDetail = JSONArray.fromObject(this.detailJsonStr);
		JsonConfig jsonConfig = new JsonConfig();

		// json为数组的解析模式
		jsonConfig.setArrayMode(JsonConfig.MODE_OBJECT_ARRAY);

		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(IPubSchemaBean.class);

		// 取出发布计划对应的参数
		IPubSchemaBean[] psItem = (IPubSchemaBean[]) JSONSerializer.toJava(
				iPubSchema, jsonConfig);

		// 指定Json数据对应的javaBean对象class名
		jsonConfig.setRootClass(IPubDetailBean.class);

		// 发布计划对应的
		IPubDetailBean[] pdItem = (IPubDetailBean[]) JSONSerializer.toJava(
				iPubDetail, jsonConfig);

		List<IPubSchemaBean> schemaList = new ArrayList<IPubSchemaBean>();

		List<IPubDetailBean> detailList = new ArrayList<IPubDetailBean>();

		for (IPubSchemaBean isb : psItem) {
			schemaList.add(isb);
		}
		for (IPubDetailBean ipb : pdItem) {
			detailList.add(ipb);
		}
		this.dataPubManager.saveOrUpdatePubSchema(psItem[0], detailList);
		this.logger.debug("数据类型对应发布计划保存结束");
		return SUCCESS;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPubSchemaId() {
		return pubSchemaId;
	}

	public void setPubSchemaId(String pubSchemaId) {
		this.pubSchemaId = pubSchemaId;
	}

	public String getConsType() {
		return consType;
	}

	public void setConsType(String consType) {
		this.consType = consType;
	}

	public IPubSchemaBean getiPubSchema() {
		return iPubSchema;
	}

	public void setiPubSchema(IPubSchemaBean iPubSchema) {
		this.iPubSchema = iPubSchema;
	}

	public DataPubManager getDataPubManager() {
		return dataPubManager;
	}

	public List<BItemCodeMappingBean> getItemCodeList() {
		return itemCodeList;
	}

	public void setItemCodeList(List<BItemCodeMappingBean> itemCodeList) {
		this.itemCodeList = itemCodeList;
	}

	public List<IPubSchemaBean> getiPubSchemaList() {
		return iPubSchemaList;
	}

	public void setiPubSchemaList(List<IPubSchemaBean> iPubSchemaList) {
		this.iPubSchemaList = iPubSchemaList;
	}

	public List<IPubDetailBean> getiPubDetailList() {
		return iPubDetailList;
	}

	public void setiPubDetailList(List<IPubDetailBean> iPubDetailList) {
		this.iPubDetailList = iPubDetailList;
	}

	public String getSchemaJsonStr() {
		return schemaJsonStr;
	}

	public void setSchemaJsonStr(String schemaJsonStr) {
		this.schemaJsonStr = schemaJsonStr;
	}

	public String getDetailJsonStr() {
		return detailJsonStr;
	}

	public void setDetailJsonStr(String detailJsonStr) {
		this.detailJsonStr = detailJsonStr;
	}
}
