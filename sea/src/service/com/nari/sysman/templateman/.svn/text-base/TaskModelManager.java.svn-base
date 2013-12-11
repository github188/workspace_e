package com.nari.sysman.templateman;

import java.util.List;

import com.nari.basicdata.BClearProtocol;
//import com.nari.terminalparam.TTaskTemplate;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;

import com.nari.ami.database.map.terminalparam.TTaskTemplate;

/**
 * 任务模板管理业务层接口
 * @author 鲁兆淞
 */
public interface TaskModelManager {

	/**
	 * 查询所有终端规约类型，按编码排序。
	 * @return 终端规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwProtocolCode> getProtocolCode() throws DBAccessException;

	/**
	 * 查询所有终端任务属性，按编码排序。
	 * @return 终端任务属性列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwTaskProperty> getAllTaskProperty() throws DBAccessException;

	/**
	 * 新增或修改任务模板
	 * @param tTaskTemplate
	 * @throws DBAccessException
	 */
	public void saveOrUpdateTaskTemplate(TTaskTemplate tTaskTemplate,String itemStr) throws DBAccessException;


	/**
	 * 修改任务模板对应的数据项
	 * @throws DBAccessException
	 */
	public void saveTTaskTemplateData(Long templateId,String itemStr) throws DBAccessException;

	/**
	 * 查询所有启用的任务模板
	 * @param orderBy 排序字段
	 * @param order 排序方式
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getAllStartedTemplate() throws DBAccessException;
	
	/**
	 * 根据任务号加载任务模板
	 * @param taskNo 任务号
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getTemplateByTaskNo(String taskNo) throws DBAccessException;
	
	/**
	 * 根据任务模板id查询任务模版
	 * @param templateId 模板id
	 * @return
	 * @throws DBAccessException
	 */
	public TTaskTemplate getTaskTemplateById(Long templateId) throws Exception;
	
	/**
	 * 根据规约项编码查询透明规约项
	 * @return
	 * @throws DBAccessException
	 */
	public List<TreeNode> getBClearProtocolByProtocolNo(Byte protocolNo) throws DBAccessException;
	
	/**
	 * 根据模板ID查询关联的透明规约
	 * @param templateId 模板ID
	 * @return
	 * @throws DBAccessException
	 */
	public List<BClearProtocol> getBClearProtocolByTemplateId(Long templateId) throws DBAccessException;
	
	/**
	 * 根据模板名称查询存在的模板
	 * @param templateName 模板名称
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getAllTaskTemplateByName(String templateName) throws DBAccessException;
	
	/**
	 * 查询模板名称符合当前值但ID不符合当前值的模板(保存模板时判断是否重名)
	 * 		返回结果不为空则说明该模板名已存在,不允许保存
	 * @param templateName 模板名称
	 * @param templateId 模板ID
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getAllTaskTemplateByNameId(String templateName,Long templateId) throws DBAccessException;
	
	/**
	 * 根据模板id注销模板（修改isCancel字段---true）
	 * @param ttemplateId
	 * @throws DBAccessException
	 */
	public String cancelTaskTemplate(Long templateId) throws Exception;
	
	/**
	 * 查询透明规约树节点列表
	 * @return 透明规约树节点列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<TreeNode> queryTreeNode(Byte dataType) throws DBAccessException ;
	
	/**
	 * 查询任务是否在用 0-不再用 1-再用
	 * @param templateId 任务模版id
	 * @return
	 * @throws Exception
	 */
	public String queryTaskSign(Long templateId) throws Exception;
}
