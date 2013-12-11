package com.nari.sysman.templateman.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.ami.database.map.terminalparam.TTaskTemplateData;
import com.nari.ami.database.map.terminalparam.TTaskTemplateDataId;
import com.nari.basicdata.BClearProtocol;
import com.nari.basicdata.BClearProtocolDao;
import com.nari.basicdata.BClearProtocolJdbcDao;
import com.nari.basicdata.VwClearDataType;
import com.nari.basicdata.VwClearDataTypeDao;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.templateman.TaskModelManager;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.sysman.templateman.VwProtocolCodeDao;
import com.nari.sysman.templateman.VwTaskProperty;
import com.nari.sysman.templateman.VwTaskPropertyDao;
import com.nari.terminalparam.TTaskTemplateDao;
import com.nari.terminalparam.TTaskTemplateDataJdbcDao;
import com.nari.util.TreeNode;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 任务模板管理业务层实现
 */
public class TaskModelManagerImpl implements TaskModelManager {
	protected Logger logger = Logger.getLogger(this.getClass());

	private VwProtocolCodeDao vwProtocolCodeDao;
	private VwTaskPropertyDao vwTaskPropertyDao;
	private TTaskTemplateDao tTaskTemplateDao;
	private BClearProtocolDao bClearProtocolDao;
	private BClearProtocolJdbcDao bClearProtocolJdbcDao;
	private TTaskTemplateDataJdbcDao tTaskTemplateDataJdbcDao;
	private VwClearDataTypeDao vwClearDataTypeDao;
	
	public void setVwClearDataTypeDao(VwClearDataTypeDao vwClearDataTypeDao) {
		this.vwClearDataTypeDao = vwClearDataTypeDao;
	}
	public void setVwProtocolCodeDao(VwProtocolCodeDao vwProtocolCodeDao) {
		this.vwProtocolCodeDao = vwProtocolCodeDao;
	}
	public void setVwTaskPropertyDao(VwTaskPropertyDao vwTaskPropertyDao) {
		this.vwTaskPropertyDao = vwTaskPropertyDao;
	}
	public void settTaskTemplateDao(TTaskTemplateDao tTaskTemplateDao) {
		this.tTaskTemplateDao = tTaskTemplateDao;
	}
	public void setbClearProtocolDao(BClearProtocolDao bClearProtocolDao) {
		this.bClearProtocolDao = bClearProtocolDao;
	}
	public void setbClearProtocolJdbcDao(BClearProtocolJdbcDao bClearProtocolJdbcDao) {
		this.bClearProtocolJdbcDao = bClearProtocolJdbcDao;
	}
	public void settTaskTemplateDataJdbcDao(
			TTaskTemplateDataJdbcDao tTaskTemplateDataJdbcDao) {
		this.tTaskTemplateDataJdbcDao = tTaskTemplateDataJdbcDao;
	}
	/**
	 * 查询所有终端规约类型，按编码排序。
	 * @return 终端规约类型列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwProtocolCode> getProtocolCode() throws DBAccessException {
		try {
			return this.vwProtocolCodeDao.findAll("protocolCode", "asc");
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	/**
	 * 查询所有终端任务属性，按编码排序。
	 * @return 终端任务属性列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwTaskProperty> getAllTaskProperty() throws DBAccessException {
		try {
			return this.vwTaskPropertyDao.findAll("taskProperty", "asc");
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	/**
	 * 新增或修改任务模板
	 * @param tTaskTemplate
	 * @throws DBAccessException
	 */
	public void saveOrUpdateTaskTemplate(TTaskTemplate tTaskTemplate,String itemStr) throws DBAccessException {
		try {
//			this.tTaskTemplateDao.saveOrUpdate(tTaskTemplate);
			if(tTaskTemplate.getTemplateId()==null){
				tTaskTemplate.setTemplateId(this.tTaskTemplateDataJdbcDao.querySequence());
			}
			IStatement statement = CoherenceStatement.getSharedInstance();
			
			statement.executeUpdate(TTaskTemplate.class, tTaskTemplate.getTemplateId(), tTaskTemplate);
			saveTTaskTemplateData(tTaskTemplate.getTemplateId(),itemStr);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改任务模板对应的数据项
	 * @throws DBAccessException
	 */
	public void saveTTaskTemplateData(Long templateId,String itemStr) throws DBAccessException{
		
		this.tTaskTemplateDataJdbcDao.deleteByTemplateId(templateId);
		
		String itemArray[] = itemStr.split(",");
		if(itemArray.length>0){
			for(short i=1;i<itemArray.length+1;i++){
				TTaskTemplateDataId tTaskTemplateDataId = new TTaskTemplateDataId();
				if("".equals(itemArray[i-1])){
					continue;
				}else{
					tTaskTemplateDataId.setProtocolNo(itemArray[i-1]);
					tTaskTemplateDataId.setProtocolSn(i);
				}
				tTaskTemplateDataId.setTemplateId(templateId);
				
				TTaskTemplateData tTaskTemplateData = new TTaskTemplateData();
				tTaskTemplateData.setId(tTaskTemplateDataId);
				
				try {
					IStatement statement = CoherenceStatement.getSharedInstance();
					
					statement.executeUpdate(TTaskTemplateData.class, tTaskTemplateData.getId(), tTaskTemplateData);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				this.tTaskTemplateDataDao.saveOrUpdate(tTaskTemplateData);
			}
		}
	}

	/**
	 * 查询所有启用的任务模板
	 */
	public List<TTaskTemplate> getAllStartedTemplate()throws DBAccessException {
		try{
			return this.tTaskTemplateDao.findBy("isCancel",false,"templateName","asc");
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	/**
	 * 根据任务模板id查询任务模版
	 * @param templateId 模板id
	 * @return
	 * @throws DBAccessException
	 */
	public TTaskTemplate getTaskTemplateById(Long templateId) throws Exception{
		try{
//			return this.tTaskTemplateDao.findById(templateId);
			IStatement statement = CoherenceStatement.getSharedInstance();
			
			return (TTaskTemplate)statement.getValueByKey(TTaskTemplate.class, templateId);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据规约项编码查询透明规约项
	 * @return
	 * @throws DBAccessException
	 */
	public List<TreeNode> getBClearProtocolByProtocolNo(Byte protocolNo) throws DBAccessException{
		String protocolNo1 = "35";
		PropertyFilter pf = new PropertyFilter("protocolNo", protocolNo1, MatchType.LIKE);
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(pf);
		try{
			List<BClearProtocol> list = new ArrayList<BClearProtocol>();
			list = this.bClearProtocolDao.findBy(filters, "protocolNo", "asc");
			List<TreeNode> itemTreeList = new ArrayList<TreeNode>();
			if (list != null)
				for(BClearProtocol bcpVo : list){
					TreeNode itemTreeNode = new TreeNode();
					itemTreeNode.setId(bcpVo.getProtocolNo());
					itemTreeNode.setText(bcpVo.getProtocolName());
					itemTreeNode.setLeaf(true);
					itemTreeList.add(itemTreeNode);
				}
			return itemTreeList;
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 根据模板ID查询关联的透明规约
	 * @param templateId 模板ID
	 * @return
	 * @throws DBAccessException
	 */
	public List<BClearProtocol> getBClearProtocolByTemplateId(Long templateId) throws DBAccessException{
		try{
			return bClearProtocolJdbcDao.findAllByTemplateId(templateId);
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 根据模板名称查询存在的模板
	 * @param templateName 模板名称
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getAllTaskTemplateByName(String templateName) throws DBAccessException{
		try{
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter pf1 = new PropertyFilter("templateName", templateName);
			PropertyFilter pf2 = new PropertyFilter("isCancel", false);
			filters.add(pf1);filters.add(pf2);
//			return tTaskTemplateDao.findBy("templateName", templateName);
			return tTaskTemplateDao.findBy(filters);
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	/**
	 * 根据任务号加载任务模板
	 * @param taskNo 任务号
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getTemplateByTaskNo(String taskNo) throws DBAccessException{
		List<TTaskTemplate> list = new ArrayList<TTaskTemplate>();
		try{
			return this.tTaskTemplateDataJdbcDao.queryTemplateByTaskNo(taskNo);
		}catch(Exception e){
			e.printStackTrace();
			return list;
		}
	}
	/**
	 * 查询模板名称符合但ID不符合的模板(保存模板时判断是否重名)
	 * 		返回结果不为空则说明该模板名已存在
	 * @param templateName 模板名称
	 * @param templateId 模板ID
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTaskTemplate> getAllTaskTemplateByNameId(String templateName,Long templateId) throws DBAccessException{
		try{
			PropertyFilter pf1 = new PropertyFilter("templateName", templateName);
			PropertyFilter pf2 = new PropertyFilter("templateId", templateId, MatchType.NEQ);
			PropertyFilter pf3 = new PropertyFilter("isCancel", false);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			filters.add(pf1);filters.add(pf2);filters.add(pf3);
			return tTaskTemplateDao.findBy(filters);
		}catch(DataAccessException e){
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 根据模板id注销模板（修改isCancel字段---true）
	 * @param ttemplateId
	 * @throws DBAccessException
	 */
	public String cancelTaskTemplate(Long templateId) throws Exception{
		try{
			TTaskTemplate tTaskTemplate = new TTaskTemplate();
			IStatement statement = CoherenceStatement.getSharedInstance();
			tTaskTemplate = (TTaskTemplate)statement.getValueByKey(TTaskTemplate.class, templateId);
			String sign = "模板不存在";
			
			if(tTaskTemplate.getTemplateId()!=null){
				if("1".equals(this.queryTaskSign(tTaskTemplate.getTemplateId()))){
					tTaskTemplate.setIsCancel(true);
					tTaskTemplate.setCancelDate(new Date());
					statement.executeUpdate(TTaskTemplate.class, tTaskTemplate.getTemplateId(), tTaskTemplate);
				}else{
					statement.removeValueByKey(TTaskTemplate.class, tTaskTemplate.getTemplateId());
				}
				sign = "注销成功";
			}
			return sign;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询透明规约树节点列表
	 * @return 透明规约树节点列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<TreeNode> queryTreeNode(Byte type) throws DBAccessException {
		List<BClearProtocol> bClearProtocolList = this.bClearProtocolJdbcDao.findAllByDataType(type); // 根据数据类型查询相关透明规约编码列表
		if(bClearProtocolList==null || bClearProtocolList.size()==0){
			LinkedList<TreeNode> treeNodeLiv1List = new LinkedList<TreeNode>(); // 1级子节点列表
			return treeNodeLiv1List;
		}
		
		Iterator<BClearProtocol> bClearProtocolListIterator = bClearProtocolList.iterator(); // 透明规约编码列表迭代器

		LinkedList<TreeNode> treeNodeTrailingF = new LinkedList<TreeNode>(); // 以【F】结尾的透明规约树节点列表
		while(bClearProtocolListIterator.hasNext()) { // 遍历透明规约编码列表构造以【F】结尾的透明规约树节点列表
			BClearProtocol bClearProtocol = bClearProtocolListIterator.next();
			String protocolNo = bClearProtocol.getProtocolNo();
			String protocolName = bClearProtocol.getProtocolName();
			String dataType = bClearProtocol.getDataType().toString();
			if(protocolNo.substring(protocolNo.length() - 1).equals("F") && !protocolNo.substring(protocolNo.length() - 2).equals("FF")) { // 以【F】结尾的透明规约编码
				TreeNode treeNode = new TreeNode();
				treeNode.setId(protocolNo);
				treeNode.setText(protocolName);
				treeNode.setDataType(dataType);
				treeNodeTrailingF.addLast(treeNode);
				bClearProtocolListIterator.remove(); // 从透明规约编码列表中删除此编码
			}
		}

		Iterator<TreeNode> treeNodeTrailingFIterator = treeNodeTrailingF.iterator(); // 以【F】结尾的透明规约树节点列表迭代器
		while(treeNodeTrailingFIterator.hasNext()) {
			TreeNode treeNode = treeNodeTrailingFIterator.next();
			List<TreeNode> treeNodeChildList = new ArrayList<TreeNode>(); // 透明规约编码子节点列表
			bClearProtocolListIterator = bClearProtocolList.iterator(); // 透明规约编码列表迭代器
			while(bClearProtocolListIterator.hasNext()) { // 遍历透明规约编码列表构造以【F】结尾的透明规约树节点的子节点
				BClearProtocol bClearProtocol = bClearProtocolListIterator.next();
				String protocolNo = bClearProtocol.getProtocolNo();
				String protocolName = bClearProtocol.getProtocolName();
				if(protocolNo.substring(0, bClearProtocol.getProtocolNo().length() - 1).equals(treeNode.getId().substring(0, treeNode.getId().length() - 1))) {
					TreeNode treeNodeChild = new TreeNode();
					treeNodeChild.setId(protocolNo);
					treeNodeChild.setText(protocolName);
					treeNodeChild.setLeaf(true);
					treeNodeChildList.add(treeNodeChild);
					bClearProtocolListIterator.remove(); // 从透明规约编码列表中删除此编码
				}
			}
			treeNode.setChildren(treeNodeChildList);
			if(treeNode.getChildren().size() == 0) treeNode.setLeaf(true);
		}

		bClearProtocolListIterator = bClearProtocolList.iterator(); // 透明规约编码列表迭代器
		LinkedList<TreeNode> treeNodeTrailingFF = new LinkedList<TreeNode>(); // 以【FF】结尾的透明规约树节点列表
		while(bClearProtocolListIterator.hasNext()) { // 遍历透明规约编码列表构造以【FF】结尾的透明规约树节点列表
			BClearProtocol bClearProtocol = bClearProtocolListIterator.next();
			String protocolNo = bClearProtocol.getProtocolNo();
			String protocolName = bClearProtocol.getProtocolName();
			String dataType = bClearProtocol.getDataType().toString();
			if(protocolNo.substring(protocolNo.length() - 2).equals("FF")) { // 以【FF】结尾的透明规约编码
				TreeNode treeNode = new TreeNode();
				treeNode.setId(protocolNo);
				treeNode.setText(protocolName);
				treeNode.setDataType(dataType);
				treeNodeTrailingFF.addLast(treeNode);
				bClearProtocolListIterator.remove(); // 从透明规约编码列表中删除此编码
			}
		}

		Iterator<TreeNode> treeNodeTrailingFFIterator = treeNodeTrailingFF.iterator(); // 以【FF】结尾的透明规约树节点列表迭代器
		while(treeNodeTrailingFFIterator.hasNext()) {
			TreeNode treeNode = treeNodeTrailingFFIterator.next();
			List<TreeNode> treeNodeChildList = new ArrayList<TreeNode>(); // 透明规约编码子节点列表
			bClearProtocolListIterator = bClearProtocolList.iterator(); // 透明规约编码列表迭代器
			while(bClearProtocolListIterator.hasNext()) { // 遍历透明规约编码列表构造以【FF】结尾的透明规约树节点的子节点
				BClearProtocol bClearProtocol = bClearProtocolListIterator.next();
				String protocolNo = bClearProtocol.getProtocolNo();
				String protocolName = bClearProtocol.getProtocolName();
				if(protocolNo.substring(0, bClearProtocol.getProtocolNo().length() - 2).equals(treeNode.getId().substring(0, treeNode.getId().length() - 2))) {
					TreeNode treeNodeChild = new TreeNode();
					treeNodeChild.setId(protocolNo);
					treeNodeChild.setText(protocolName);
					treeNodeChild.setLeaf(true);
					treeNodeChildList.add(treeNodeChild);
					bClearProtocolListIterator.remove(); // 从透明规约编码列表中删除此编码
				}
			}
			treeNodeTrailingFIterator = treeNodeTrailingF.iterator(); // 以【F】结尾的透明规约树节点列表迭代器
			while(treeNodeTrailingFIterator.hasNext()) {
				TreeNode treeNodeChild = treeNodeTrailingFIterator.next();
				if(treeNodeChild.getId().substring(0, treeNodeChild.getId().length() -2).equals(treeNode.getId().substring(0, treeNode.getId().length() - 2))) {
					treeNodeChildList.add(treeNodeChild);
					treeNodeTrailingFIterator.remove(); // 从以【F】结尾的树节点列表中删除此节点
				}
			}

			Collections.sort(treeNodeChildList); // 按TreeNode类中的自定义排序规则排序
			treeNode.setChildren(treeNodeChildList);
			if(treeNode.getChildren().size() == 0) treeNode.setLeaf(true);
		}

		LinkedList<TreeNode> treeNodeLiv1List = new LinkedList<TreeNode>(); // 1级子节点列表
		List<VwClearDataType> vwClearDataTypeList = this.vwClearDataTypeDao.findAll(); // 查询透明规约分类列表
		for(VwClearDataType vwClearDataType : vwClearDataTypeList) { // 构造1级子节点列表
			TreeNode treeNodeLiv1 = new TreeNode();
			treeNodeLiv1.setId(vwClearDataType.getDataType());
			treeNodeLiv1.setText(vwClearDataType.getDataName());
			treeNodeLiv1.setLeaf(false);
			treeNodeLiv1List.add(treeNodeLiv1);
		}

		Iterator<TreeNode> treeNodeLiv1Iterator = treeNodeLiv1List.iterator(); // 1级子节点列表迭代器
		while(treeNodeLiv1Iterator.hasNext()) {
			TreeNode treeNodeLiv1 = (TreeNode)treeNodeLiv1Iterator.next();
			LinkedList<TreeNode> treeNodeLiv2List = new LinkedList<TreeNode>(); // 2级子节点列表

			treeNodeTrailingFIterator = treeNodeTrailingF.iterator(); // 以【F】结尾的透明规约树节点列表迭代器
			while(treeNodeTrailingFIterator.hasNext()) {
				TreeNode treeNodeLiv2 = (TreeNode)treeNodeTrailingFIterator.next();
				if(treeNodeLiv2.getDataType().equals(treeNodeLiv1.getId())) {
					treeNodeLiv2List.add(treeNodeLiv2);
				}
			}
			treeNodeTrailingFFIterator = treeNodeTrailingFF.iterator(); // 以【FF】结尾的透明规约树节点列表迭代器
			while(treeNodeTrailingFFIterator.hasNext()) {
				TreeNode treeNodeLiv2 = (TreeNode)treeNodeTrailingFFIterator.next();
				if(treeNodeLiv2.getDataType().equals(treeNodeLiv1.getId())) {
					treeNodeLiv2List.add(treeNodeLiv2);
				}
			}

			Collections.sort(treeNodeLiv2List); // 按TreeNode类中的自定义排序规则排序
			treeNodeLiv1.setChildren(treeNodeLiv2List);
			if(treeNodeLiv1.getChildren().size() == 0) treeNodeLiv1.setLeaf(true);
		}

		if(bClearProtocolList.size() > 0) {
			this.logger.error("BClearProtocol表中存在" + bClearProtocolList.size() + "条脏数据：");
			for(BClearProtocol bClearProtocol : bClearProtocolList) {
				this.logger.error(bClearProtocol.getProtocolNo() + " | " + bClearProtocol.getProtocolName());
			}
		}

		return treeNodeLiv1List;
	}
	
	/**
	 * 查询任务是否在用 0-不再用 1-再用
	 * @param templateId 任务模版id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryTaskSign(Long templateId) throws Exception{
		String useSign = "0";
		try{
			IStatement statement = CoherenceStatement.getSharedInstance();
			Collection tasks = statement.executeQuery("select * from TTmnlTask where templateId = "+templateId+";");
			if(tasks!=null && !tasks.isEmpty()){
				useSign = "1";
			}
			return useSign;
		}catch(Exception e){
			e.printStackTrace();
			return useSign;
		}
	}
}
