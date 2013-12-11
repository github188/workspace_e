package com.nari.baseapp.datagathorman.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.terminalparam.TTaskTemplate;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.ami.database.map.terminalparam.TTmnlTaskId;
import com.nari.baseapp.datagatherman.GatherParamsSendDto;
import com.nari.baseapp.datagatherman.GatherTaskCompileDto;
import com.nari.baseapp.datagatherman.TaskInfoDto;
import com.nari.baseapp.datagatherman.TbgTask;
import com.nari.baseapp.datagatherman.TbgTaskDetail;
import com.nari.baseapp.datagatherman.TbgTaskSend;
import com.nari.baseapp.datagathorman.GatherTaskCompileJdbcDao;
import com.nari.baseapp.datagathorman.GatherTaskCompileManager;
import com.nari.basicdata.BClearProtocol;
import com.nari.basicdata.BClearProtocolJdbcDao;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.privilige.PSysUser;
import com.nari.runman.dutylog.IGenerateKey;
import com.nari.util.CreateInsert;
import com.nari.util.NodeTypeUtils;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;
import com.tangosol.coherence.component.application.console.Coherence;

public class GatherTaskCompileManagerImpl implements GatherTaskCompileManager {

	private Logger logger = Logger.getLogger(this.getClass());

	private GatherTaskCompileJdbcDao gatherTaskCompileJdbcDao;
	// private TTmnlTaskDao tTmnlTaskDao;
	// private TTaskTemplateDao tTaskTemplateDao;
	private BClearProtocolJdbcDao bClearProtocolJdbcDao;

	public void setGatherTaskCompileJdbcDao(
			GatherTaskCompileJdbcDao gatherTaskCompileJdbcDao) {
		this.gatherTaskCompileJdbcDao = gatherTaskCompileJdbcDao;
	}

	// public void settTmnlTaskDao(TTmnlTaskDao tTmnlTaskDao) {
	// this.tTmnlTaskDao = tTmnlTaskDao;
	// }
	// public void settTaskTemplateDao(TTaskTemplateDao tTaskTemplateDao) {
	// this.tTaskTemplateDao = tTaskTemplateDao;
	// }
	public void setbClearProtocolJdbcDao(
			BClearProtocolJdbcDao bClearProtocolJdbcDao) {
		this.bClearProtocolJdbcDao = bClearProtocolJdbcDao;
	}

	/**
	 * 根据用户查询相关的信息放入备选用户的Grid里
	 * 
	 * @param consNo
	 *            用户编号
	 * @throws DBAccessException
	 */
	public List<GatherTaskCompileDto> queryGatherTaskCompileByTree(
			PSysUser pSysUser, String consNo, String orgNo, String lineId,
			String nodeType, String groupNo, String[] tmnlAssetNo)
			throws Exception {
		try {
			if (NodeTypeUtils.NODETYPE_USER.equals(nodeType)) {
				List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
				list = gatherTaskCompileJdbcDao.findGatherTaskCompileByCons(
						tmnlAssetNo, false);
				return list;
			} else if (NodeTypeUtils.NODETYPE_ORG.equals(nodeType)) {
				return gatherTaskCompileJdbcDao.findGatherTaskCompileByOrg(
						pSysUser, orgNo, nodeType);
			} else if (NodeTypeUtils.NODETYPE_LINE.equals(nodeType)) {
				return gatherTaskCompileJdbcDao.findGatherTaskCompileByOrg(
						pSysUser, lineId, nodeType);
			} else if (NodeTypeUtils.NODETYPE_UGP.equals(nodeType)
					|| NodeTypeUtils.NODETYPE_CGP.equals(nodeType)) {
				return gatherTaskCompileJdbcDao.findGatherTaskCompileByOrg(
						pSysUser, groupNo, nodeType);
			} else {
				List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
				return list;
			}
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_SCBXYHCC);
		}
	}

	/**
	 * 下发任务，然后查询下发后结果
	 * 
	 * @param taskNo
	 *            任务号
	 * @param mpSn
	 *            测量点
	 * @param taskModel
	 *            任务模板id
	 * @param consNos
	 *            户号
	 * @param tmnlAssetNos
	 *            终端资产号
	 * @param sendUpModes
	 *            数据采集方式（0--主站主动召测，1--终端自动上送）
	 * @param protocolCodes
	 *            终端规约编码
	 * @param taskSecond
	 *            延时时间（秒）
	 * @return
	 * @throws Exception
	 */
	public List<GatherTaskCompileDto> sendOutAndQuery(String taskNo,
			String mpSn, String taskModel, String consNos, String tmnlAssetNos,
			String sendUpModes, String protocolCodes, String yTaskNos,
			String nTaskNos, int taskSecond, String staffNo) throws Exception {
		String[] consNoArray = consNos.split("#");
		String[] tmnlAssetNoArray = tmnlAssetNos.split("#");
		String[] protocolCodeArray = protocolCodes.split("#");
		String[] sendUpModeArray = sendUpModes.split("#");
		String[] yTaskNoArray = yTaskNos.split("#");
		String[] nTaskNoArray = nTaskNos.split("#");
		String[] mpSnArray = mpSn.split(",");
		List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
		TaskDeal taskDeal = new TaskDeal();
		try {
			if (consNoArray != null) {
				IStatement statement = CoherenceStatement.getSharedInstance();

				TTaskTemplate tTaskTemplate = new TTaskTemplate();
				tTaskTemplate = (TTaskTemplate) statement.getValueByKey(
						TTaskTemplate.class, Long.parseLong(taskModel));
				if (tTaskTemplate == null) {
					this.logger.info("下发的任务模板不存在");
					return list;
				}

				// 构造测量点和透明规约编码的字符串用来设置数据标识
				List<BClearProtocol> bClearProtocolList = bClearProtocolJdbcDao
						.findAllByTemplateId(Long.parseLong(taskModel));
				StringBuffer protocolMpsn = new StringBuffer();
				if (bClearProtocolList != null) {
					for (int i = 0; i < bClearProtocolList.size(); i++) {
						for (int j = 0; j < mpSnArray.length; j++) {
							protocolMpsn.append(mpSnArray[j] + ":"
									+ bClearProtocolList.get(i).getProtocolNo()
									+ ";");
						}
					}
				}
				String protocolStr = protocolMpsn.toString();

				Map<String, String> assentNoProtocol = new HashMap<String, String>();
				List<GatherParamsSendDto> gpsList = new ArrayList<GatherParamsSendDto>();
				Map<String, String> yTaskNoMap = new HashMap<String, String>();
				Map<String, String> nTaskNoMap = new HashMap<String, String>();

				for (int i = 0; i < tmnlAssetNoArray.length; i++) {
					assentNoProtocol.put(tmnlAssetNoArray[i],
							protocolCodeArray[i]);
					yTaskNoMap.put(tmnlAssetNoArray[i], yTaskNoArray[i]);
					nTaskNoMap.put(tmnlAssetNoArray[i], nTaskNoArray[i]);

					// 主动召测的终端不需要设置参数，这里假想设置状态为3，后面针对终端统一处理
					if ("0".equals(sendUpModeArray[i])) { // 主站召测的终端,直接将状态置为3，统一处理
						GatherParamsSendDto gpsDto = new GatherParamsSendDto();
						gpsDto.setStatusCode((short) 3);
						gpsDto.setTaskNo(taskNo);
						gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
						gpsList.add(gpsDto);
						continue;
					} else if ("1".equals(sendUpModeArray[i])
							&& !TaskDeal.isFrontAlive()) {
						GatherParamsSendDto gpsDto = new GatherParamsSendDto();
						gpsDto.setStatusCode((short) 5);
						gpsDto.setTaskNo(taskNo);
						gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
						gpsList.add(gpsDto);
						continue;
					}
					// 构造调用前置机接口时要用到的终端资产编号list
					List<String> tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(tmnlAssetNoArray[i]);

					// 根据数据类型构造paramList（参数项设置）
					List<ParamItem> paramList = new ArrayList<ParamItem>();
					ParamItem pitem = new ParamItem(); // 设置任务参数
					ParamItem pitem1 = new ParamItem(); // 启用要下发的任务

					// 根据任务模板的数据类型构造不同的下发参数（dataType:1--历史数据--1类数据，2--实时数据--2类数据）
					if (tTaskTemplate.getDataType() == 1) {
						pitem.setFn((short) 65);
						pitem.setPoint(Short.valueOf(taskNo));
						List<Item> items = new ArrayList<Item>();
						Item item1 = new Item(protocolCodeArray[i] + "0441001");
						Item item2 = new Item(protocolCodeArray[i] + "0441002");
						Item item3 = new Item(protocolCodeArray[i] + "0441003");
						Item item4 = new Item(protocolCodeArray[i] + "0441004");
						Item item5 = new Item(protocolCodeArray[i] + "0441005");
						Item item6 = new Item(protocolCodeArray[i] + "0441006");
						item1.setValue(tTaskTemplate.getReportCycleUnit());
						item2.setValue(String.valueOf(tTaskTemplate
								.getReportCycle()));
						item3.setValue(genReferenceTime(tTaskTemplate
								.getReferenceTime(), tTaskTemplate
								.getRandomValue()));
						item4
								.setValue(String.valueOf(tTaskTemplate
										.getTmnlR()));
						item5.setValue(String.valueOf(tTaskTemplate
								.getDataCount()));
						item6.setValue(protocolStr);
						items.add(item1);
						items.add(item2);
						items.add(item3);
						items.add(item4);
						items.add(item5);
						items.add(item6);
						pitem.setItems((ArrayList<Item>) items);
						paramList.add(pitem);

						pitem1.setFn((short) 67);
						pitem1.setPoint(Short.valueOf(taskNo));
						List<Item> items1 = new ArrayList<Item>();
						Item item = new Item(protocolCodeArray[i] + "0443001"); // 1类数据
						item.setValue("55");
						items1.add(item);
						pitem1.setItems((ArrayList<Item>) items1);
						paramList.add(pitem1);
					} else if (tTaskTemplate.getDataType() == 2) {
						pitem.setFn((short) 66);
						pitem.setPoint(Short.valueOf(taskNo));
						List<Item> items = new ArrayList<Item>();
						Item item1 = new Item(protocolCodeArray[i] + "0442001");
						Item item2 = new Item(protocolCodeArray[i] + "0442002");
						Item item3 = new Item(protocolCodeArray[i] + "0442003");
						Item item4 = new Item(protocolCodeArray[i] + "0442004");
						Item item5 = new Item(protocolCodeArray[i] + "0442005");
						Item item6 = new Item(protocolCodeArray[i] + "0442006");
						item1.setValue(tTaskTemplate.getReportCycleUnit());
						item2.setValue(String.valueOf(tTaskTemplate
								.getReportCycle()));
						item3.setValue(genReferenceTime(tTaskTemplate
								.getReferenceTime(), tTaskTemplate
								.getRandomValue()));
						item4
								.setValue(String.valueOf(tTaskTemplate
										.getTmnlR()));
						item5.setValue(String.valueOf(tTaskTemplate
								.getDataCount()));
						item6.setValue(protocolStr);
						items.add(item1);
						items.add(item2);
						items.add(item3);
						items.add(item4);
						items.add(item5);
						items.add(item6);
						pitem.setItems((ArrayList<Item>) items);
						paramList.add(pitem);

						pitem1.setFn((short) 68);
						pitem1.setPoint(Short.valueOf(taskNo));
						List<Item> items1 = new ArrayList<Item>();
						Item item = new Item(protocolCodeArray[i] + "0444001"); // 1类数据
						item.setValue("55");
						items1.add(item);
						pitem1.setItems((ArrayList<Item>) items1);
						paramList.add(pitem1);
					}
					// int len = tmnlAssetNoArray.length;
					this.paramsSendout(tmnlAssetNoList, paramList, taskDeal);
					// gpsList.addAll(gpList);
				}

				// 获取终端返回结果
				List<GatherParamsSendDto> gpList = this.getResponses(20,
						taskDeal);
				// 将终端返回结果累加到主站主动召测结果中
				gpsList.addAll(gpList);

				Map<Short, String> statusMaps = getStatusMaps();

				for (int i = 0; i < gpsList.size(); i++) {
					if (gpsList.get(i).getStatusCode() == 3) {

						Short mpCnt = Short.parseShort((String.valueOf(mpSn
								.split(",").length)));
						// 构造任务ID
						TTmnlTaskId tTmnlTaskId = new TTmnlTaskId();
						tTmnlTaskId.setTaskNo(Short.parseShort(taskNo));
						tTmnlTaskId.setTmnlAssetNo(gpsList.get(i)
								.getTmnlAssetNo());
						// 构造任务
						TTmnlTask tTmnlTask = new TTmnlTask();
						tTmnlTask.setId(tTmnlTaskId);
						tTmnlTask.setTemplateId(Long.valueOf(taskModel));
						tTmnlTask.setTaskProperty(tTaskTemplate
								.getTaskProperty());
						tTmnlTask.setMasterR(tTaskTemplate.getMasterR());
						tTmnlTask.setMpCnt(mpCnt);
						tTmnlTask.setMpSn(mpSn);
						tTmnlTask.setRecallPolicy(tTaskTemplate
								.getRecallPolicy());
						tTmnlTask.setSendTime(new Date());
						tTmnlTask.setEnableDate(new Date());
						tTmnlTask.setProtocolCode(assentNoProtocol.get(gpsList
								.get(i).getTmnlAssetNo()));
						tTmnlTask.setIsEnable(true);
						tTmnlTask.setIsValid(true);
						tTmnlTask.setStaffNo(staffNo);
						tTmnlTask.setStatusCode("04");
						tTmnlTask.setReferenceTime(genReferenceTime(
								tTaskTemplate.getReferenceTime(), tTaskTemplate
										.getRandomValue()));

						// this.tTmnlTaskDao.saveOrUpdate(tTmnlTask);
						// 调用分布式缓存保存任务
						statement.executeUpdate(TTmnlTask.class, tTmnlTask
								.getId(), tTmnlTask);

						String yTaskNo = yTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						String nTaskNo = nTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						yTaskNo = this.genTaskNos(yTaskNo, gpsList.get(i)
								.getTaskNo(), "add");
						nTaskNo = this.genTaskNos(nTaskNo, taskNo, "remove");

						GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
						gtcDto.setTmnlAssetNo(gpsList.get(i).getTmnlAssetNo());
						gtcDto.setyTaskNo(yTaskNo);
						gtcDto.setnTaskNo(nTaskNo);
						gtcDto.setTaskStatus("成功");
						list.add(gtcDto);
					} else {
						String yTaskNo = yTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						String nTaskNo = nTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());

						GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
						String status = statusMaps.get(gpsList.get(i)
								.getStatusCode());
						gtcDto.setTmnlAssetNo(gpsList.get(i).getTmnlAssetNo());
						gtcDto.setyTaskNo(yTaskNo);
						gtcDto.setnTaskNo(nTaskNo);
						gtcDto.setTaskStatus(status == null ? "失败" : status);
						list.add(gtcDto);
					}
				}
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstants.BAE_SENDOUTTASK);
		}
	}

	/**
	 * 上报基准时间计算
	 * 
	 * @param referTime
	 *            模板基准时间
	 * @param i
	 *            上报随机数
	 * @return 任务基准时间
	 */
	public String genReferenceTime(String referTime, int i) {
		if (referTime == null || "".equals(referTime))
			return "";
		referTime = referTime.replace("：", ":");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Double dValue = Math.random() * i;
		try {
			Date d = sdf.parse(referTime);
			Date d1 = new Date(d.getTime() + dValue.intValue() * 1000);
			String s1 = sdf.format(d1);
			String tDate = sdf1.format(new Date());
			s1 = tDate + " " + s1;
			return s1;
		} catch (ParseException e) {
			return "";
		}
	}

	/**
	 * 将mpSn1和mpSn2中的测量点号合并起来
	 * 
	 * @param mpSn1
	 * @param mpSn2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String genMpSn(String mpSn1, String mpSn2) {
		String mpsn = "";
		mpSn1 = mpSn1 == null ? "" : mpSn1;
		mpSn2 = mpSn2 == null ? "" : mpSn2;

		String[] mpSnArray1 = mpSn1.split(",");
		String[] mpSnArray2 = mpSn2.split(",");
		ArrayList al = new ArrayList();
		for (int i = 0; i < mpSnArray1.length; i++)
			al.add(Integer.valueOf(mpSnArray1[i]));

		for (int i = 0; i < mpSnArray2.length; i++) {
			if (al.indexOf(Integer.valueOf(mpSnArray2[i])) == -1)
				al.add(Integer.valueOf(mpSnArray2[i]));
		}
		Collections.sort(al);
		for (int i = 0; i < al.size(); i++)
			mpsn += al.get(i) + ",";

		mpsn = mpsn.equals("") ? "" : mpsn.substring(0, mpsn.length() - 1);
		return mpsn;
	}

	/**
	 * 下发、启用、禁用和删除任务时，处理任务号
	 * 
	 * @param taskNos
	 *            原先的任务号字符串
	 * @param taskNo
	 *            操作的任务号
	 * @param type
	 *            添加、删除
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String genTaskNos(String taskNos, String taskNo, String type) {
		if (taskNos == null || taskNo == null)
			return "";
		String returnValue = "";

		String[] taskNoArray = taskNos.split(",");
		ArrayList al = new ArrayList();
		for (int i = 0; i < taskNoArray.length; i++)
			al.add(Integer.valueOf(taskNoArray[i]));

		if ("remove".equals(type)) {
			if (al.indexOf(Integer.valueOf(taskNo)) != -1)
				al.remove(Integer.valueOf(taskNo));
		} else if ("add".equals(type)) {
			if (al.indexOf(Integer.valueOf(taskNo)) == -1)
				al.add(Integer.valueOf(taskNo));
		}

		Collections.sort(al);
		for (int i = 0; i < al.size(); i++)
			returnValue += al.get(i) + ",";

		returnValue = returnValue.equals("") ? "" : returnValue.substring(0,
				returnValue.length() - 1);

		return returnValue;
	}

	/**
	 * 获取参数返回值时调用的接口
	 * 
	 * @param taskSecond
	 *            任务总执行时间
	 * @return
	 */
	private List<GatherParamsSendDto> getResponses(int taskSecond,
			TaskDeal taskDeal) {

		List<Response> list = new ArrayList<Response>();
		list = taskDeal.getAllResponse(20);

		List<GatherParamsSendDto> gaList = new ArrayList<GatherParamsSendDto>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				this.logger.debug("size--" + list.size() + "\nlist(" + i
						+ "):----------------- " + list.get(i).toString());
			}
		}

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				List<ResponseItem> rsItems = list.get(i).getResponseItems();
				for (int j = 0; j < rsItems.size(); j++) {
					GatherParamsSendDto gDto = new GatherParamsSendDto();
					gDto.setTmnlAssetNo(list.get(i).getTmnlAssetNo());
					gDto.setStatusCode(list.get(i).getTaskStatus());
					gDto.setTaskNo(String.valueOf(rsItems.get(j).getPn()));
					gaList.add(gDto);
				}
			}
		}
		return gaList;
	}

	/**
	 * 参数下发时调用的接口
	 * 
	 * @param tmnlAssetNoList
	 *            资产号list
	 * @param paramList
	 * @param taskSecond
	 *            任务总执行时间
	 * @param len
	 *            任务个数
	 * @param taskNo
	 *            任务号
	 * @return
	 */
	private void paramsSendout(List<String> tmnlAssetNoList,
			List<ParamItem> paramList, TaskDeal taskDeal) {
		if (paramList != null && paramList.size() > 0) {
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.SET_PARAMETERS, paramList);
		}
	}

	/**
	 * 启用、停用任务
	 * 
	 * @param consNos
	 *            户号字符串
	 * @param tmnlAssetNos
	 *            终端资产编号字符串
	 * @param sendUpModes
	 *            上送方式字符串
	 * @param taskNos
	 *            任务号字符串
	 * @param eventType
	 *            事件类型 0--停用， 1--启用
	 * @return
	 * @throws Exception
	 */
	public List<GatherTaskCompileDto> ynTaskStatus(String consNos,
			String tmnlAssetNos, String sendUpModes, String taskNos,
			String eventType, String protocolCodes, String yTaskNos,
			String nTaskNos, int taskSecond) throws Exception {
		String[] tmnlAssetNoArray = tmnlAssetNos.split("#");
		String[] protocolCodeArray = protocolCodes.split("#");
		String[] sendUpModeArray = sendUpModes.split("#");
		String[] taskNoArray = taskNos.split("#");
		String[] yTaskNoArray = yTaskNos.split("#");
		String[] nTaskNoArray = nTaskNos.split("#");
		boolean isEnable = eventType.equals("1") ? true : false;
		List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
		TaskDeal taskDeal = new TaskDeal();
		boolean sendFlag = false;
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();

			if (taskNoArray != null && tmnlAssetNoArray != null) {
				List<GatherParamsSendDto> gpsList = new ArrayList<GatherParamsSendDto>();
				Map<String, String> yTaskNoMap = new HashMap<String, String>();
				Map<String, String> nTaskNoMap = new HashMap<String, String>();
				for (int i = 0; i < tmnlAssetNoArray.length; i++) {

					yTaskNoMap.put(tmnlAssetNoArray[i], yTaskNoArray[i]);
					nTaskNoMap.put(tmnlAssetNoArray[i], nTaskNoArray[i]);

					if ("1".equals(sendUpModeArray[i])
							&& !TaskDeal.isFrontAlive()) { // 判断前置机服务是否中断
						GatherParamsSendDto gpsDto = new GatherParamsSendDto();
						gpsDto.setStatusCode((short) 5);
						gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
						gpsList.add(gpsDto);
						continue;
					}
					// 参数项设置
					List<ParamItem> paramList = new ArrayList<ParamItem>();
					/* 终端资产编号list */
					List<String> tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(tmnlAssetNoArray[i]);
					for (int j = 0; j < taskNoArray.length; j++) {
						TaskInfoDto taskInfoDto = new TaskInfoDto();
						taskInfoDto = this.gatherTaskCompileJdbcDao
								.findTaskInfoById(tmnlAssetNoArray[i],
										taskNoArray[j]);
						if (taskInfoDto == null) {
							continue;
						}

						if ("0".equals(sendUpModeArray[i])) { // 主站召测的终端,直接将状态置为3，统一处理
							GatherParamsSendDto gpsDto = new GatherParamsSendDto();
							gpsDto.setStatusCode((short) 3);
							gpsDto.setTaskNo(taskNoArray[j]);
							gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
							gpsList.add(gpsDto);
						} else if ("1".equals(sendUpModeArray[i])) { // 自动上送任务需要下发参数给终端
							ParamItem pitem = new ParamItem();
							if (taskInfoDto.getDataType() == 1) {
								pitem.setFn((short) 67);
								// 编码对象集合
								List<Item> items = new ArrayList<Item>();
								Item item = new Item(protocolCodeArray[i]
										+ "0443001"); // 1类数据
								if (isEnable) {
									item.setValue("55");
								} else {
									item.setValue("AA");
								}
								items.add(item);
								pitem.setItems((ArrayList<Item>) items);
							} else if (taskInfoDto.getDataType() == 2) {
								pitem.setFn((short) 68);
								// 编码对象集合
								List<Item> items = new ArrayList<Item>();
								Item item = new Item(protocolCodeArray[i]
										+ "0444001"); // 2类数据
								if (isEnable) {
									item.setValue("55");
								} else {
									item.setValue("AA");
								}
								items.add(item);
								pitem.setItems((ArrayList<Item>) items);
							} else {
								continue;
							}
							pitem.setPoint(Short.valueOf(taskNoArray[j]));
							paramList.add(pitem);

							// int len = taskNoArray.length *
							// tmnlAssetNoArray.length;
							// gpsList.addAll(gpList);
						}
					}
					if (paramList.size() == 0 && sendFlag == false) {
						sendFlag = false;
					} else {
						sendFlag = true;
						this
								.paramsSendout(tmnlAssetNoList, paramList,
										taskDeal);
					}
				}

				if (sendFlag) {
					// 获取终端返回结果
					List<GatherParamsSendDto> gpList = this.getResponses(20,
							taskDeal);
					// 将终端返回结果累加到主站主动召测结果中
					gpsList.addAll(gpList);
				}

				Map<Short, String> statusMaps = getStatusMaps();

				Map<String, GatherTaskCompileDto> gatherMap = new HashMap<String, GatherTaskCompileDto>();

				if (gpsList != null && gpsList.size() > 0) {
					for (int i = 0; i < gpsList.size(); i++) {
						if (gpsList.get(i).getStatusCode() == 3) {
							TTmnlTask tTmnlTask = new TTmnlTask();
							TTmnlTaskId taskId = new TTmnlTaskId();
							taskId.setTaskNo(Short.parseShort(gpsList.get(i)
									.getTaskNo()));
							taskId.setTmnlAssetNo(gpsList.get(i)
									.getTmnlAssetNo());
							tTmnlTask = (TTmnlTask) statement.getValueByKey(
									TTmnlTask.class, taskId);
							if (tTmnlTask != null
									&& tTmnlTask.getTemplateId() != null) {
								tTmnlTask.setIsEnable(isEnable);
								if (isEnable) {
									tTmnlTask.setEnableDate(new Date());
								} else {
									tTmnlTask.setDisableDate(new Date());
								}
								// this.tTmnlTaskDao.saveOrUpdate(tTmnlTask);
								statement.executeUpdate(TTmnlTask.class,
										tTmnlTask.getId(), tTmnlTask);
							}

							String yTaskNo = yTaskNoMap.get(gpsList.get(i)
									.getTmnlAssetNo());
							String nTaskNo = nTaskNoMap.get(gpsList.get(i)
									.getTmnlAssetNo());

							if (isEnable) {
								yTaskNo = this.genTaskNos(yTaskNo, gpsList.get(
										i).getTaskNo(), "add");
								yTaskNoMap.put(gpsList.get(i).getTmnlAssetNo(),
										yTaskNo);
								nTaskNo = this.genTaskNos(nTaskNo, gpsList.get(
										i).getTaskNo(), "remove");
								nTaskNoMap.put(gpsList.get(i).getTmnlAssetNo(),
										nTaskNo);
							} else {
								yTaskNo = this.genTaskNos(yTaskNo, gpsList.get(
										i).getTaskNo(), "remove");
								yTaskNoMap.put(gpsList.get(i).getTmnlAssetNo(),
										yTaskNo);
								nTaskNo = this.genTaskNos(nTaskNo, gpsList.get(
										i).getTaskNo(), "add");
								nTaskNoMap.put(gpsList.get(i).getTmnlAssetNo(),
										nTaskNo);
							}
							GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
							gtcDto.setTmnlAssetNo(gpsList.get(i)
									.getTmnlAssetNo());
							gtcDto.setyTaskNo(yTaskNo);
							gtcDto.setnTaskNo(nTaskNo);
							if (gatherMap.get(gpsList.get(i).getTmnlAssetNo()) != null
									&& !"成功".equals(gatherMap.get(
											gpsList.get(i).getTmnlAssetNo())
											.getTaskStatus())) {
								gtcDto.setTaskStatus(gatherMap.get(
										gpsList.get(i).getTmnlAssetNo())
										.getTaskStatus());
							} else {
								gtcDto.setTaskStatus("成功");
							}

							gatherMap.put(gpsList.get(i).getTmnlAssetNo(),
									gtcDto);
						} else {
							String yTaskNo = yTaskNoMap.get(gpsList.get(i)
									.getTmnlAssetNo());
							String nTaskNo = nTaskNoMap.get(gpsList.get(i)
									.getTmnlAssetNo());

							GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
							String status = statusMaps.get(gpsList.get(i)
									.getStatusCode());
							gtcDto.setTmnlAssetNo(gpsList.get(i)
									.getTmnlAssetNo());
							gtcDto.setyTaskNo(yTaskNo);
							gtcDto.setnTaskNo(nTaskNo);
							gtcDto
									.setTaskStatus(status == null ? "失败"
											: status);

							gatherMap.put(gpsList.get(i).getTmnlAssetNo(),
									gtcDto);
						}
					}

					Collection<GatherTaskCompileDto> gatherColl = gatherMap
							.values();
					list.addAll(gatherColl);
					// Iterator<GatherTaskCompileDto> it =
					// gatherColl.iterator();
					// while (it.hasNext()){
					// list.add(it.next());
					// }
				}
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstants.BAE_YNGATHERTASK);
		}
	}

	/**
	 * 删除任务
	 * 
	 * @param consNos
	 *            户号字符串
	 * @param tmnlAssetNos
	 *            终端资产编号字符串
	 * @param sendUpModes
	 *            上送方式字符串
	 * @param taskNos
	 *            任务号字符串
	 * @return
	 * @throws Exception
	 */
	public List<GatherTaskCompileDto> deleteTask(String consNos,
			String tmnlAssetNos, String sendUpModes, String taskNos,
			String protocolCodes, String yTaskNos, String nTaskNos,
			int taskSecond) throws Exception {
		// String[] consNoArray = consNos.split("#");
		String[] tmnlAssetNoArray = tmnlAssetNos.split("#");
		String[] sendUpModeArray = sendUpModes.split("#");
		String[] protocolCodeArray = protocolCodes.split("#");
		String[] yTaskNoArray = yTaskNos.split("#");
		String[] nTaskNoArray = nTaskNos.split("#");
		String[] taskNoArray = taskNos.split("#");
		List<GatherTaskCompileDto> list = new ArrayList<GatherTaskCompileDto>();
		TaskDeal taskDeal = new TaskDeal();
		boolean sendFlag = false;
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();

			List<GatherParamsSendDto> gpsList = new ArrayList<GatherParamsSendDto>();
			Map<String, String> yTaskNoMap = new HashMap<String, String>();
			Map<String, String> nTaskNoMap = new HashMap<String, String>();

			for (int i = 0; i < tmnlAssetNoArray.length; i++) {
				/* 终端资产编号list */
				List<String> tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlAssetNoArray[i]);

				yTaskNoMap.put(tmnlAssetNoArray[i], yTaskNoArray[i]);
				nTaskNoMap.put(tmnlAssetNoArray[i], nTaskNoArray[i]);

				if ("1".equals(sendUpModeArray[i]) && !TaskDeal.isFrontAlive()) { // 判断前置机服务是否中断
					GatherParamsSendDto gpsDto = new GatherParamsSendDto();
					gpsDto.setStatusCode((short) 5);
					gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
					gpsList.add(gpsDto);
					continue;
				}
				// 参数项设置
				List<ParamItem> paramList = new ArrayList<ParamItem>();
				for (int j = 0; j < taskNoArray.length; j++) {

					TaskInfoDto taskInfoDto = this.gatherTaskCompileJdbcDao
							.findTaskInfoById(tmnlAssetNoArray[i],
									taskNoArray[j]);
					if (taskInfoDto != null
							&& taskInfoDto.getTemplateName() != null) {
						if ("0".equals(sendUpModeArray[i])) {
							GatherParamsSendDto gpsDto = new GatherParamsSendDto();
							gpsDto.setStatusCode((short) 3);
							gpsDto.setTaskNo(taskNoArray[j]);
							gpsDto.setTmnlAssetNo(tmnlAssetNoArray[i]);
							gpsList.add(gpsDto);
						} else if ("1".equals(sendUpModeArray[i])) { // 自动上送的终端要禁用任务
							ParamItem pitem = new ParamItem();
							if (taskInfoDto.getDataType() == 1) {
								pitem.setFn((short) 67);
								// 编码对象集合
								List<Item> items = new ArrayList<Item>();
								Item item = new Item(protocolCodeArray[i]
										+ "0443001"); // 1类数据
								item.setValue("AA");
								items.add(item);
								pitem.setItems((ArrayList<Item>) items);
							} else if (taskInfoDto.getDataType() == 2) {
								pitem.setFn((short) 68);
								// 编码对象集合
								List<Item> items = new ArrayList<Item>();
								Item item = new Item(protocolCodeArray[i]
										+ "0444001"); // 2类数据
								item.setValue("AA");
								items.add(item);
								pitem.setItems((ArrayList<Item>) items);
							} else {
								continue;
							}
							pitem.setPoint(Short.valueOf(taskNoArray[j]));
							paramList.add(pitem);

							if (paramList.size() == 0 && sendFlag == false) {
								sendFlag = false;
							} else {
								sendFlag = true;
								this.paramsSendout(tmnlAssetNoList, paramList,
										taskDeal);
							}
						}
					}
				}
			}

			if (sendFlag == true) {
				// 获取终端返回结果
				List<GatherParamsSendDto> gpList = this.getResponses(20,
						taskDeal);
				// 将终端返回结果累加到主站主动召测结果中
				gpsList.addAll(gpList);
			}

			Map<Short, String> statusMaps = getStatusMaps();

			Map<String, GatherTaskCompileDto> gatherMap = new HashMap<String, GatherTaskCompileDto>();

			if (gpsList != null && gpsList.size() > 0) {
				for (int i = 0; i < gpsList.size(); i++) {
					if (gpsList.get(i).getStatusCode() == 3) {
						TTmnlTaskId taskId = new TTmnlTaskId();
						taskId.setTaskNo(Short.parseShort(gpsList.get(i)
								.getTaskNo()));
						taskId.setTmnlAssetNo(gpsList.get(i).getTmnlAssetNo());
						statement.removeValueByKey(TTmnlTask.class, taskId);

						String yTaskNo = yTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						String nTaskNo = nTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						yTaskNo = this.genTaskNos(yTaskNo, gpsList.get(i)
								.getTaskNo(), "remove");
						yTaskNoMap
								.put(gpsList.get(i).getTmnlAssetNo(), yTaskNo);
						nTaskNo = this.genTaskNos(nTaskNo, gpsList.get(i)
								.getTaskNo(), "remove");
						nTaskNoMap
								.put(gpsList.get(i).getTmnlAssetNo(), nTaskNo);

						GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
						gtcDto.setTmnlAssetNo(gpsList.get(i).getTmnlAssetNo());
						gtcDto.setyTaskNo(yTaskNo);
						gtcDto.setnTaskNo(nTaskNo);
						if (gatherMap.get(gpsList.get(i).getTmnlAssetNo()) != null
								&& !"成功".equals(gatherMap.get(
										gpsList.get(i).getTmnlAssetNo())
										.getTaskStatus())) {
							gtcDto.setTaskStatus(gatherMap.get(
									gpsList.get(i).getTmnlAssetNo())
									.getTaskStatus());
						} else {
							gtcDto.setTaskStatus("成功");
						}

						gatherMap.put(gpsList.get(i).getTmnlAssetNo(), gtcDto);

					} else {
						String yTaskNo = yTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());
						String nTaskNo = nTaskNoMap.get(gpsList.get(i)
								.getTmnlAssetNo());

						GatherTaskCompileDto gtcDto = new GatherTaskCompileDto();
						String status = statusMaps.get(gpsList.get(i)
								.getStatusCode());
						gtcDto.setTmnlAssetNo(gpsList.get(i).getTmnlAssetNo());
						gtcDto.setyTaskNo(yTaskNo);
						gtcDto.setnTaskNo(nTaskNo);
						gtcDto.setTaskStatus(status == null ? "失败" : status);

						gatherMap.put(gpsList.get(i).getTmnlAssetNo(), gtcDto);
					}
				}
				Collection<GatherTaskCompileDto> gatherColl = gatherMap
						.values();
				list.addAll(gatherColl);
				// Iterator<GatherTaskCompileDto> it = gatherColl.iterator();
				// while (it.hasNext()){
				// list.add(it.next());
				// }

			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionConstants.BAE_DELGATHERTASK);
		}
	}

	/**
	 * 根据终端资产编号和任务号查询任务信息
	 * 
	 * @param tmnlAssetNo
	 *            终端资产编号
	 * @param taskNo
	 *            任务号
	 * @return
	 * @throws Exception
	 */
	public TaskInfoDto queryTaskInfo(String tmnlAssetNo, String taskNo)
			throws Exception {
		TaskInfoDto taskInfoDto = new TaskInfoDto();
		try {
			taskInfoDto = this.gatherTaskCompileJdbcDao.findTaskInfoById(
					tmnlAssetNo, taskNo);
			if (taskInfoDto != null && taskInfoDto.getTemplateId() != null) {
				List<BClearProtocol> bcList = new ArrayList<BClearProtocol>();
				bcList = this.bClearProtocolJdbcDao
						.findAllByTemplateId(taskInfoDto.getTemplateId());
				if (bcList != null && bcList.size() > 0) {
					String taskItems = "";
					for (int i = 0; i < bcList.size(); i++) {
						taskItems += bcList.get(i).getProtocolName() + ",\n";
					}
					taskItems = "".equals(taskItems) ? "" : taskItems
							.substring(0, taskItems.length() - 1);
					taskInfoDto.setTaskItems(taskItems);
				}
			}
			return taskInfoDto;
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QUERYINFOERROR);
		}
	}

	/**
	 * 召测任务信息
	 * 
	 * @param tmnlAssetNo
	 *            终端资产号
	 * @param taskNo
	 *            任务号
	 * @param dataType
	 *            数据类型
	 * @return
	 * @throws Exception
	 */
	public TaskInfoDto callTaskPara(String tmnlAssetNo, String taskNo,
			String dataType, int taskSecond) throws Exception {
		TaskInfoDto taskInfoDto = new TaskInfoDto();
		TaskDeal taskDeal = new TaskDeal();
		try {
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(tmnlAssetNo);
			// 参数项设置
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			if ("1".equals(dataType)) {
				ParamItem pitem = new ParamItem();
				pitem.setFn((short) 65);// 设置FN号
				pitem.setPoint(Short.parseShort(taskNo));// 设置point(为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				List<Item> items = new ArrayList<Item>();
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
			}
			if ("2".equals(dataType)) {
				ParamItem pitem = new ParamItem();
				pitem.setFn((short) 66);// 设置FN号
				pitem.setPoint(Short.parseShort(taskNo));// 设置point//
				// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				List<Item> items = new ArrayList<Item>();
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
			}
			List<Response> respList = null;
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
					FrontConstant.QUERY_PARAMS, paramList);
			respList = taskDeal.getAllResponse(20);
			if (respList != null && respList.size() > 0) {
				this.logger.debug("size---" + respList.size()
						+ "\n respList0---" + respList.get(0));
				if (respList.get(0).getTaskStatus() == 3) {
					for (int j = 0; j < respList.get(0).getResponseItems()
							.size(); j++) {
						if (respList.get(0).getResponseItems().get(j).getCode()
								.endsWith("0442001")) {
							taskInfoDto.setCallReportCycleUnit(respList.get(0)
									.getResponseItems().get(j).getValue());
						} else if (respList.get(0).getResponseItems().get(j)
								.getCode().endsWith("0442002")) {
							taskInfoDto.setCallReportCycle(respList.get(0)
									.getResponseItems().get(j).getValue());
						} else if (respList.get(0).getResponseItems().get(j)
								.getCode().endsWith("0442003")) {
							taskInfoDto.setCallReferenceTime(respList.get(0)
									.getResponseItems().get(j).getValue());
						} else if (respList.get(0).getResponseItems().get(j)
								.getCode().endsWith("0442004")) {
							taskInfoDto.setCallMasterR(respList.get(0)
									.getResponseItems().get(j).getValue());
						} else if (respList.get(0).getResponseItems().get(j)
								.getCode().endsWith("0442006")) {
							String mpsnItems = respList.get(0)
									.getResponseItems().get(j).getValue();
							taskInfoDto.setCallClearItems(mpsnItems);
						}
					}
				}
			}
			return taskInfoDto;
		} catch (Exception e) {
			throw new ServiceException("召测任务信息异常");
		}
	}

	/****
	 * 批量下发
	 * 
	 * @param taskNo
	 *            任务编号
	 * @param templateId
	 *            任务模板的编号
	 * @param pns
	 *            pn的列表
	 * @param datas
	 *            格式为<br>
	 *            key 为终端资产号<br>
	 *            value 为：（终端规约编码_数据采集方式) <br>
	 *            （0--主站主动召测，1--终端自动上送）
	 * @throws ServiceException
	 *             *
	 ***/
	@SuppressWarnings("unchecked")
	public void batchSendOut(PSysUser user, String taskNo, String templateId,
			List<String> pns, Map datas) throws ServiceException {
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();
			TTaskTemplate tTaskTemplate = new TTaskTemplate();
			tTaskTemplate = (TTaskTemplate) statement.getValueByKey(
					TTaskTemplate.class, Long.parseLong(templateId));
			if (tTaskTemplate == null) {
				this.logger.info("下发的任务模板不存在");
				throw new ServiceException("下发的任务模板不存在或者已经被删除");
			}
			// 构造测量点和透明规约编码的字符串用来设置数据标识
			List<BClearProtocol> bClearProtocolList = bClearProtocolJdbcDao
					.findAllByTemplateId(Long.parseLong(templateId));
			StringBuffer protocolMpsn = new StringBuffer();
			if (bClearProtocolList != null) {
				for (int i = 0; i < bClearProtocolList.size(); i++) {
					for (int j = 0; j < pns.size(); j++) {
						protocolMpsn.append(pns.get(j) + ":"
								+ bClearProtocolList.get(i).getProtocolNo()
								+ ";");
					}
				}
			}
			String protocolStr = protocolMpsn.toString();

			// 在主表中插入一条数据
			CreateInsert c = new CreateInsert(true);
			Integer id = null;
			Date now = new Date();
			Iterator it = datas.entrySet().iterator();
			while (it.hasNext()) {
				Entry en = (Entry) it.next();
				String tmnlNo = (String) en.getKey();
				tmnlNo=tmnlNo.replaceFirst("Z", "");
				String value = (String) en.getValue();
				String[] arr = value.split("_");
				if (arr.length < 2) {
					throw new ServiceException("数据格式不正确");
				}
				String pcode = arr[0];
				String sendMode = arr[1];
				TbgTask tt = new TbgTask();
				if (id != null) {
					tt.setTaskId(id);
				}
				tt.setTaskTime(now);
				tt.setTmnlAssetNo(tmnlNo);
				tt.setStaffNo(user.getStaffNo());
				tt.setObjType(0);
				tt.setRwFlag(0);
				tt.setTaskName(tTaskTemplate.getTemplateName());
				tt.setTaskType(2);
				tt.setOrgNo(user.getOrgNo());
				tt.setSendTime(now);
				tt.setNextSendTime(DateUtils.addMinutes(now, 5));
				tt.setTaskStatus("02");
				c.save(tt);
				id = tt.getTaskId();
				// 不自动初始化主键
				c.setInitId(false);
				// 通过值而不是通过主键生成策略来生成主键
				c.setGenerateKey(null);
				Short mpCnt = Short.parseShort(String.valueOf(pns.size()));
				// 构造任务ID
				TTmnlTaskId tTmnlTaskId = new TTmnlTaskId();
				tTmnlTaskId.setTaskNo(Short.parseShort(taskNo));
				tTmnlTaskId.setTmnlAssetNo(tmnlNo);
				// 构造任务,在终端任务表中添加数据
				TTmnlTask tTmnlTask = new TTmnlTask();
				tTmnlTask.setId(tTmnlTaskId);
				tTmnlTask.setTemplateId(Long.valueOf(templateId));
				tTmnlTask.setTaskProperty(tTaskTemplate.getTaskProperty());
				tTmnlTask.setMasterR(tTaskTemplate.getMasterR());
				tTmnlTask.setMpCnt(mpCnt);
				tTmnlTask.setMpSn(pns.toString().replaceFirst("\\[", "")
						.replaceFirst("\\]", ""));
				tTmnlTask.setRecallPolicy(tTaskTemplate.getRecallPolicy());
				tTmnlTask.setSendTime(new Date());
				tTmnlTask.setEnableDate(new Date());
				tTmnlTask.setProtocolCode(pcode);
				tTmnlTask.setIsEnable(true);
				tTmnlTask.setIsValid(true);
				tTmnlTask.setStaffNo(user.getStaffNo());
				tTmnlTask.setStatusCode("04");
				tTmnlTask
						.setReferenceTime(genReferenceTime(tTaskTemplate
								.getReferenceTime(), tTaskTemplate
								.getRandomValue()));

				// this.tTmnlTaskDao.saveOrUpdate(tTmnlTask);
				// 调用分布式缓存保存任务
				statement.executeUpdate(TTmnlTask.class, tTmnlTask.getId(),
						tTmnlTask);
				// 主动召测的终端
				if ("0".equals(sendMode)) {
					continue;
					// 终端主动上送忽略
				} else {
					// 区别一类 二类数据进行下发
					// 向t_bg_task_send中插数据
					TbgTaskSend t1 = new TbgTaskSend();

					t1.setTaskNo(taskNo);
					t1.setTmnlAssetNo(tmnlNo);
					t1.setDataValue(tTaskTemplate.getReportCycleUnit());
					TbgTaskSend t2 = new TbgTaskSend();
					t2.setDataValue(String.valueOf(tTaskTemplate
							.getReportCycle()));
					t2.setTaskNo(taskNo);
					t2.setTmnlAssetNo(tmnlNo);
					TbgTaskSend t3 = new TbgTaskSend();
					t3
							.setDataValue(genReferenceTime(tTaskTemplate
									.getReferenceTime(), tTaskTemplate
									.getRandomValue()));
					t3.setTaskNo(taskNo);
					t3.setTmnlAssetNo(tmnlNo);
					TbgTaskSend t4 = new TbgTaskSend();
					t4.setDataValue(String.valueOf(tTaskTemplate.getTmnlR()));
					t4.setTaskNo(taskNo);
					t4.setTmnlAssetNo(tmnlNo);
					TbgTaskSend t5 = new TbgTaskSend();
					t5.setDataValue(String
							.valueOf(tTaskTemplate.getDataCount()));
					t5.setTaskNo(taskNo);
					t5.setTmnlAssetNo(tmnlNo);
					TbgTaskSend t6 = new TbgTaskSend();
					t6.setDataValue(protocolStr);
					t6.setTaskNo(taskNo);
					t6.setTmnlAssetNo(tmnlNo);
					// t7用来启用任务,默认为启用
					TbgTaskSend t7 = new TbgTaskSend();
					t7.setTmnlAssetNo(tmnlNo);
					t7.setTaskNo(taskNo);
					t7.setDataValue("55");
					if (tTaskTemplate.getDataType().equals((byte) 1)) {
						// 向t_bg_task_detail插入数据
						t1.setDataItemNo(pcode + "0441001");
						t2.setDataItemNo(pcode + "0441002");
						t3.setDataItemNo(pcode + "0441003");
						t4.setDataItemNo(pcode + "0441004");
						t5.setDataItemNo(pcode + "0441005");
						t6.setDataItemNo(pcode + "0441006");
						t7.setDataItemNo(pcode + "0443001");
					} else if (tTaskTemplate.getDataType().equals((byte) 2)) {
						// 向t_bg_task_detail插入数据

						// 向t_bg_task_send中插数据
						t1.setDataItemNo(pcode + "0442001");
						t2.setDataItemNo(pcode + "0442002");
						t3.setDataItemNo(pcode + "0442003");
						t4.setDataItemNo(pcode + "0442004");
						t5.setDataItemNo(pcode + "0442005");
						t6.setDataItemNo(pcode + "0442006");
						t7.setDataItemNo(pcode + "0444001");
					} else {
						continue;
					}
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t1);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t2);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t3);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t4);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t5);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t6);
					gatherTaskCompileJdbcDao.mergeBgTaskSend(t7);
				}
			}
			TbgTaskDetail ttd = new TbgTaskDetail();
			TbgTaskDetail ttd1 = new TbgTaskDetail();
			if (tTaskTemplate.getDataType().equals((byte) 1)) {
				ttd.setDataType(Integer.valueOf(taskNo));
				ttd.setDataItemNo("41");
				ttd.setTaskId(id);
				ttd1.setDataType(Integer.valueOf(taskNo));
				ttd1.setDataItemNo("43");
				ttd1.setTaskId(id);
				c.save(ttd1);
				c.save(ttd);

			} else if (tTaskTemplate.getDataType().equals((byte) 2)) {
				ttd.setDataType(Integer.valueOf(taskNo));
				ttd.setDataItemNo("42");
				ttd.setTaskId(id);
				ttd1.setDataType(Integer.valueOf(taskNo));
				ttd1.setDataItemNo("44");
				ttd1.setTaskId(id);
				c.save(ttd1);
				c.save(ttd);
			} else {
				throw new RuntimeException("不支持模板类型");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	private Map<Short, String> getStatusMaps() {
		Map<Short, String> statusMaps = new HashMap<Short, String>();
		statusMaps.put((short) 0, "终端响应超时");
		statusMaps.put((short) 1, "终端不在线");
		statusMaps.put((short) 2, "终端无应答");
		statusMaps.put((short) 3, "成功");
		statusMaps.put((short) 4, "失败");
		statusMaps.put((short) 5, "前置集群服务中断");

		return statusMaps;
	}
}
