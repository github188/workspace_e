package com.nari.baseapp.prepaidman.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.baseapp.planpowerconsume.WtmnlCtrlStatusManger;
import com.nari.baseapp.prepaidman.MeterControlInfoBean;
import com.nari.baseapp.prepaidman.MeterInfoBean;

import com.nari.baseapp.prepaidman.PrePaidControlBean;
import com.nari.baseapp.prepaidman.PrePaidControlTmnlBean;
import com.nari.baseapp.prepaidman.PrePaidDao;
import com.nari.baseapp.prepaidman.PrePaidExecResultBean;
import com.nari.baseapp.prepaidman.PrePaidManager;
import com.nari.baseapp.prepaidman.PrePaidParamSetBean;
import com.nari.baseapp.prepaidman.PrePaidParamSetTmnlBean;
import com.nari.basicdata.BCommProtocol;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.WTmnlCtrlStatus;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class PrePaidManagerImpl implements PrePaidManager {
	private PrePaidDao prePaidDao;
	protected ILOpTmnlLogManager iLOpTmnlLogManager;
	private WtmnlCtrlStatusManger wtmnlCtrlStatusManger;

	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}

	public void setPrePaidDao(PrePaidDao prePaidDao) {
		this.prePaidDao = prePaidDao;
	}

	public void setWtmnlCtrlStatusManger(
			WtmnlCtrlStatusManger wtmnlCtrlStatusManger) {
		this.wtmnlCtrlStatusManger = wtmnlCtrlStatusManger;
	}

	@Override
	public List<PrePaidControlBean> queryPrePaidCtrlList(String orgNo,
			String appNo, String consNo, String tmnlAddr)
			throws DBAccessException {
		List<PrePaidControlBean> list = null;
		try {
			list = prePaidDao.queryPrePaidCtrlList(orgNo, appNo, consNo,
					tmnlAddr);
			return list;
		} catch (Exception e) {
			throw new DBAccessException("查询预付费控制用户列表出错");
		}
	}

	@Override
	public Page<PrePaidControlBean> queryPrePaidCtrlPage(PSysUser user,
			String orgNo, String appNo, String consNo, String tmnlAddr,
			long start, long limit) throws DBAccessException {
		Page<PrePaidControlBean> page = new Page<PrePaidControlBean>();
		try {
			page = prePaidDao.queryPrePaidCtrlPage(user, orgNo, appNo, consNo,
					tmnlAddr, start, limit);
			return page;
		} catch (Exception e) {
			throw new DBAccessException("查询预付费控制用户分页列表出错");
		}
	}

	@Override
	public List<PrePaidParamSetBean> queryPrePaidParamSetList(String orgNo,
			String sendStatus, String appNo, String consNo)
			throws DBAccessException {
		List<PrePaidParamSetBean> list = null;
		try {
			list = prePaidDao.queryPrePaidParamSetList(orgNo, sendStatus,
					appNo, consNo);
			return list;
		} catch (Exception e) {
			throw new DBAccessException("查询预付费参数下发用户列表出错");
		}
	}

	@Override
	public Page<PrePaidParamSetBean> queryPrePaidParamSetPage(PSysUser user,
			String orgNo, String sendStatus, String appNo, String consNo,
			String buyFlag, long start, long limit) throws DBAccessException {
		Page<PrePaidParamSetBean> page = new Page<PrePaidParamSetBean>();
		try {
			page = prePaidDao.queryPrePaidParamSetPage(user, orgNo, sendStatus,
					appNo, consNo, buyFlag, start, limit);
			return page;
		} catch (Exception e) {
			throw new DBAccessException("查询预付费参数下发用户分页列表出错");
		}
	}

	@Override
	public List<PrePaidParamSetTmnlBean> prePaidParamFetch(
			List<PrePaidParamSetTmnlBean> tmnlList, int second)
			throws DBAccessException {
		List<PrePaidParamSetTmnlBean> resultList = new ArrayList<PrePaidParamSetTmnlBean>();
		if (tmnlList == null || tmnlList.size() <= 0) {
			return resultList;
		}
		TaskDeal taskDeal = new TaskDeal();// 生成请求对象

		PrePaidParamSetTmnlBean flagBean = tmnlList.get(0);
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		List<String> tmnlAssetNoList = new ArrayList<String>();
		for (int n = 0; n < tmnlList.size(); n++) {
			PrePaidParamSetTmnlBean bean = tmnlList.get(n);
			if (n == 0) {
				tmnlAssetNoList.add(bean.getTmnlAssetNo());// 加入终端资产编号列表

				ParamItem coloumItem = new ParamItem();
				coloumItem.setFn((short) 47);// 设置FN号
				coloumItem.setPoint(bean.getTotalNo());// 设置point
				// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)

				List<Item> items = new ArrayList<Item>();
				coloumItem.setItems((ArrayList<Item>) items);
				paramList.add(coloumItem);

				if (tmnlList.size() == 1) {// 如果前台终端List长度为1，直接进行下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
							FrontConstant.QUERY_PARAMS, paramList);
				}
				continue;
			}

			// 参数项设置
			if (!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())) {
				// 参数下发
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.QUERY_PARAMS, paramList);

				// 一个终端组一个包，遇到终端不同的要新建paramList对象
				paramList = new ArrayList<ParamItem>();

				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(bean.getTmnlAssetNo());
				flagBean = bean;
			}

			ParamItem coloumItem = new ParamItem();
			coloumItem.setFn((short) 47);// 设置FN号
			coloumItem.setPoint(bean.getTotalNo());// 设置point
			// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)

			List<Item> items = new ArrayList<Item>();
			coloumItem.setItems((ArrayList<Item>) items);
			paramList.add(coloumItem);

			if (n == tmnlList.size() - 1) {
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.QUERY_PARAMS, paramList);
			}
		}

		List<Response> list = taskDeal.getAllResponse(second);
		Response res = null;
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				PrePaidParamSetTmnlBean tf = new PrePaidParamSetTmnlBean();
				res = (Response) list.get(i);
				Short status = res.getTaskStatus();
				if (status == 3 || status == 4) {
					// 循环responseItems
					for (int j = 0; j < res.getResponseItems().size(); j++) {
						ResponseItem items = res.getResponseItems().get(j);
						if (j % 5 == 0) {
							tf.setKeyId(res.getTmnlAssetNo() + items.getPn());
							tf.setAppNo(items.getValue());
							tf.setTmnlAssetNo(res.getTmnlAssetNo());
							tf.setTotalNo(items.getPn());
						} else if (j % 5 == 1) {
							tf.setRefreshFlag(items.getValue());
						} else if (j % 5 == 2) {
							tf.setBuyValue(Integer.parseInt(items.getValue()));
						} else if (j % 5 == 3) {
							tf
									.setAlarmValue(Integer.parseInt(items
											.getValue()));
						} else {
							tf.setJumpValue(Integer.parseInt(items.getValue()));
							resultList.add(tf);
						}
					}
				}
			}
		}
		return resultList;
	}

	@Override
	public List<PrePaidExecResultBean> prePaidParamSet(
			List<PrePaidParamSetBean> tmnlList, String turnFlag, int second,
			PSysUser user, String localIp) throws DBAccessException {
		List<PrePaidParamSetTmnlBean> list = new ArrayList<PrePaidParamSetTmnlBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidParamSetTmnlBean bean = new PrePaidParamSetTmnlBean();
			PrePaidParamSetBean obj = tmnlList.get(i);
			bean.setAlarmValue(obj.getAlarmValue());
			bean.setAlarmValueUnit(obj.getAlarmValueUnit());
			bean.setAppNo(obj.getAppNo());
			bean.setBuyFlag(obj.getBuyFlag());
			bean.setBuyValue(obj.getBuyValue());
			bean.setBuyValueUnit(obj.getBuyValueUnit());
			bean.setConsNo(obj.getConsNo());
			bean.setJumpValue(obj.getJumpValue());
			bean.setJumpValueUnit(obj.getJumpValueUnit());
			bean.setOrgNo(obj.getOrgNo());
			bean.setProtocolCode(obj.getProtocolCode());
			bean.setRefreshFlag(obj.getRefreshFlag());
			bean.setTotalNo(obj.getTotalNo());
			bean.setTmnlAssetNo(obj.getTmnlAssetNo());

			list.add(bean);
		}
		return prePaidParamSetView(list, turnFlag, second, user, localIp);
	}

	@Override
	public List<PrePaidExecResultBean> prePaidControl(
			List<PrePaidControlBean> tmnlList, int second, PSysUser user,
			String localIp) throws DBAccessException {
		List<PrePaidControlTmnlBean> list = new ArrayList<PrePaidControlTmnlBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidControlTmnlBean bean = new PrePaidControlTmnlBean();
			PrePaidControlBean o = tmnlList.get(i);
			bean.setAppNo(o.getAppNo());
			bean.setConsNo(o.getConsNo());
			bean.setOrgNo(o.getOrgNo());
			bean.setProtocolCode(o.getProtocolCode());
			bean.setTmnlAssetNo(o.getTmnlAssetNo());
			bean.setTotalNo(o.getTotalNo());
		}
		return prePaidControlView(list, second, user, localIp);
	}

	@Override
	public List<PrePaidExecResultBean> prePaidControlView(
			List<PrePaidControlTmnlBean> tmnlList, int second, PSysUser user,
			String localIp) throws DBAccessException {
		List<PrePaidExecResultBean> execResultList = new ArrayList<PrePaidExecResultBean>();
		if (tmnlList == null || tmnlList.size() <= 0) {
			return execResultList;
		}
		TaskDeal taskDeal = new TaskDeal();// 生成请求对象

		schemeRelease(tmnlList, taskDeal, (short) 16);

		List<Response> list = taskDeal.getAllResponse(second);
		Response res = null;
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				res = (Response) list.get(i);
				Short Status = res.getTaskStatus();// 记录终端返回状态

				LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志
				// 设置共有属性值
				l.setOrgNo(user.getOrgNo());
				l.setEmpNo(user.getStaffNo());
				l.setIpAddr(localIp);
				l.setOpModule("预付费控制投入");
				l.setOpButton("投入");
				l.setOpType((short) 2);// 控制下发
				l.setTmnlAssetNo(res.getTmnlAssetNo());
				l.setOpTime(new Date());
				l.setCurStatus(Status.toString());
				l.setOpObj((short) 4);

				if (Status == 3 || Status == 4) {
					String protocolCode = getProtocolCodeByTmnlAssetNo(
							tmnlList, res.getTmnlAssetNo());
					for (int j = 0; j < res.getResponseItems().size(); j++) {
						ResponseItem resItem = res.getResponseItems().get(j);

						// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
						l.setOpObjNo((long) resItem.getPn());
						l.setProtocolNo(protocolCode + "0510");
						iLOpTmnlLogManager.saveTmnlLog(l);

						// 向前台页面返回参数下发结果
						if (Status == 3) {
							// 创建终端实时状态Bean,并对各控制状态共属性赋值
							WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

							ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
							ctrlStatus.setTotalNo(resItem.getPn());
							ctrlStatus.setFactctrlFlag((short) 1);// 投入设置成1
							wtmnlCtrlStatusManger
									.saveOrUpdateTmnlCtrlStatusFeectrlFlag(ctrlStatus);// 修改终端实时状态

							execResultList.add(new PrePaidExecResultBean(res
									.getTmnlAssetNo()
									+ resItem.getPn(), "1", Status.toString()));
						} else {
							execResultList
									.add(new PrePaidExecResultBean(res
											.getTmnlAssetNo()
											+ resItem.getPn(), "-1", Status
											.toString()));
						}
					}
				} else {
					// 对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
					List<PrePaidControlTmnlBean> noResponseList = noCtrlResponseTmnl(
							tmnlList, res.getTmnlAssetNo());
					if (noResponseList == null) {
						continue;
					}
					for (int j = 0; j < noResponseList.size(); j++) {
						PrePaidControlTmnlBean bean = noResponseList.get(j);

						// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
						l.setOpObjNo((long) bean.getTotalNo());
						l.setProtocolNo(bean.getProtocolCode() + "0510");
						iLOpTmnlLogManager.saveTmnlLog(l);

						execResultList.add(new PrePaidExecResultBean(bean
								.getTmnlAssetNo()
								+ bean.getTotalNo(), "-1", Status.toString()));
					}
				}
			}
		} else {
			for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list
				// 为空，则返回全部失败
				PrePaidControlTmnlBean bean = tmnlList.get(n);
				execResultList.add(new PrePaidExecResultBean(bean
						.getTmnlAssetNo()
						+ bean.getTotalNo(), "-1", "0"));
			}
		}
		return execResultList;
	}

	@Override
	public List<PrePaidExecResultBean> prePaidRelease(
			List<PrePaidControlBean> tmnlList, int second, PSysUser user,
			String localIp) throws DBAccessException {
		List<PrePaidControlTmnlBean> list = new ArrayList<PrePaidControlTmnlBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidControlTmnlBean bean = new PrePaidControlTmnlBean();
			PrePaidControlBean o = tmnlList.get(i);
			bean.setAppNo(o.getAppNo());
			bean.setConsNo(o.getConsNo());
			bean.setOrgNo(o.getOrgNo());
			bean.setProtocolCode(o.getProtocolCode());
			bean.setTmnlAssetNo(o.getTmnlAssetNo());
			bean.setTotalNo(o.getTotalNo());
		}
		return prePaidReleaseView(list, second, user, localIp);
	}

	@Override
	public List<PrePaidExecResultBean> prePaidReleaseView(
			List<PrePaidControlTmnlBean> tmnlList, int second, PSysUser user,
			String localIp) throws DBAccessException {
		List<PrePaidExecResultBean> execResultList = new ArrayList<PrePaidExecResultBean>();
		if (tmnlList == null || tmnlList.size() <= 0) {
			return execResultList;
		}
		TaskDeal taskDeal = new TaskDeal();// 生成请求对象

		schemeRelease(tmnlList, taskDeal, (short) 24);

		List<Response> list = taskDeal.getAllResponse(second);
		Response res = null;
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				res = (Response) list.get(i);
				Short Status = res.getTaskStatus();// 记录终端返回状态

				LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志
				// 设置共有属性值
				l.setOrgNo(user.getOrgNo());
				l.setEmpNo(user.getStaffNo());
				l.setIpAddr(localIp);
				l.setOpModule("预付费控制解除");
				l.setOpButton("投入");
				l.setOpType((short) 2);// 控制下发
				l.setTmnlAssetNo(res.getTmnlAssetNo());
				l.setOpTime(new Date());
				l.setCurStatus(Status.toString());
				l.setOpObj((short) 4);

				if (Status == 3 || Status == 4) {
					String protocolCode = getProtocolCodeByTmnlAssetNo(
							tmnlList, res.getTmnlAssetNo());
					for (int j = 0; j < res.getResponseItems().size(); j++) {
						ResponseItem resItem = res.getResponseItems().get(j);

						// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
						l.setOpObjNo((long) resItem.getPn());
						l.setProtocolNo(protocolCode + "0518");
						iLOpTmnlLogManager.saveTmnlLog(l);

						// 向前台页面返回参数下发结果
						if (Status == 3) {
							// 创建终端实时状态Bean,并对各控制状态共属性赋值
							WTmnlCtrlStatus ctrlStatus = new WTmnlCtrlStatus();

							ctrlStatus.setTmnlAssetNo(res.getTmnlAssetNo());
							ctrlStatus.setTotalNo(resItem.getPn());
							ctrlStatus.setFactctrlFlag((short) 0);// 解除设置成0
							wtmnlCtrlStatusManger
									.saveOrUpdateTmnlCtrlStatusFeectrlFlag(ctrlStatus);// 修改终端实时状态

							execResultList.add(new PrePaidExecResultBean(res
									.getTmnlAssetNo()
									+ resItem.getPn(), "0", Status.toString()));
						} else {
							execResultList
									.add(new PrePaidExecResultBean(res
											.getTmnlAssetNo()
											+ resItem.getPn(), "-1", Status
											.toString()));
						}
					}
				} else {
					// 对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
					List<PrePaidControlTmnlBean> noResponseList = noCtrlResponseTmnl(
							tmnlList, res.getTmnlAssetNo());
					if (noResponseList == null) {
						continue;
					}
					for (int j = 0; j < noResponseList.size(); j++) {
						PrePaidControlTmnlBean bean = noResponseList.get(j);

						// 对于控制解除到总加组或测量点的情况，将操作日志当前值记录为总加组或测量点号
						l.setOpObjNo((long) bean.getTotalNo());
						l.setProtocolNo(bean.getProtocolCode() + "0518");
						iLOpTmnlLogManager.saveTmnlLog(l);

						execResultList.add(new PrePaidExecResultBean(bean
								.getTmnlAssetNo()
								+ bean.getTotalNo(), "-1", Status.toString()));
					}
				}
			}
		} else {
			for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list
				// 为空，则返回全部失败
				PrePaidControlTmnlBean bean = tmnlList.get(n);
				execResultList.add(new PrePaidExecResultBean(bean
						.getTmnlAssetNo()
						+ bean.getTotalNo(), "-1", "0"));
			}
		}
		return execResultList;
	}

	@Override
	/*
	 * public List<PrePaidExecResultBean>
	 * prePaidParamSetView(List<PrePaidParamSetTmnlBean> tmnlList, String
	 * turnFlag, int second, PSysUser user, String localIp) throws
	 * DBAccessException {
	 * 
	 * StringBuffer turnValue = new StringBuffer();
	 * //将前台传的轮次编号(2,6)转化成(01000100)的形式 String[] turnFlags =
	 * turnFlag.split(","); for (char j = '1' ; j < '9' ; j++) { byte flag = 0;
	 * for (int i = 0; i < turnFlags.length; i++) { char c =
	 * turnFlags[i].charAt(0); if(c == j) { turnValue.append("1"); flag = 1; } }
	 * if(flag ==0) { turnValue.append("0"); } } turnFlag =
	 * turnValue.toString();
	 * 
	 * List<PrePaidExecResultBean> execResultList = new
	 * ArrayList<PrePaidExecResultBean>(); if(tmnlList==null ||
	 * tmnlList.size()<=0){ return execResultList; } TaskDeal taskDeal = new
	 * TaskDeal();//生成请求对象 TaskDeal turnDeal = new TaskDeal();//下发购电轮次
	 * 
	 * PrePaidParamSetTmnlBean flagBean = tmnlList.get(0);
	 * 
	 * List<ParamItem> paramList = new ArrayList<ParamItem>(); List<ParamItem>
	 * turnList = new ArrayList<ParamItem>();
	 * 
	 * List<String> tmnlAssetNoList = new ArrayList<String>(); //循环终端进行参数下发 for
	 * (int n = 0; n < tmnlList.size(); n++) { PrePaidParamSetTmnlBean bean =
	 * tmnlList.get(n); // 终端资产号集合 if(n==0){
	 * tmnlAssetNoList.add(bean.getTmnlAssetNo());//加入终端资产编号列表
	 * 
	 * ParamItem factItem = setParamItem(bean); paramList.add(factItem);
	 * 
	 * ParamItem turnItem = setTurnItem(bean,turnFlag); turnList.add(turnItem);
	 * 
	 * if(tmnlList.size()==1) {//如果前台终端List长度为1，直接进行下发
	 * taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, paramList);
	 * //turnDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, turnList); } continue; }
	 * 
	 * // 参数项设置
	 * if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){//参数下发
	 * taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, paramList);
	 * //turnDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, turnList);
	 * 
	 * //一个终端组一个包，遇到终端不同的要新建paramList对象 paramList = new ArrayList<ParamItem>();
	 * turnList = new ArrayList<ParamItem>();
	 * 
	 * tmnlAssetNoList = new ArrayList<String>();
	 * tmnlAssetNoList.add(bean.getTmnlAssetNo()); flagBean = bean; }
	 * 
	 * ParamItem factItem =setParamItem(bean); paramList.add(factItem);
	 * ParamItem turnItem = setTurnItem(bean,turnFlag); turnList.add(turnItem);
	 * 
	 * if(n==tmnlList.size()-1) {//list结尾调用接口，循环结束
	 * taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, paramList);
	 * //turnDeal.qstTermParamTaskResult(tmnlAssetNoList,
	 * FrontConstant.SET_PARAMETERS, turnList); } }
	 * 
	 * Response res = null; List<Response> list =
	 * taskDeal.getAllResponse(second/2); if (null != list && 0 < list.size()) {
	 * for (int i = 0; i < list.size(); i++) { res = (Response) list.get(i);
	 * Short Status = res.getTaskStatus();//记录终端返回状态
	 * 
	 * LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志
	 * 
	 * // 设置共有属性值 l.setOrgNo(user.getOrgNo()); l.setEmpNo(user.getStaffNo());
	 * l.setIpAddr(localIp); l.setOpModule("预付费控制参数下发"); l.setOpButton("参数下发");
	 * l.setOpType((short) 1);// 参数下发 l.setTmnlAssetNo(res.getTmnlAssetNo());
	 * l.setOpTime(new Date()); l.setCurStatus(Status.toString());
	 * l.setOpObj((short) 4);
	 * 
	 * List<ResponseItem> resItems = res.getResponseItems(); if(Status ==3
	 * ||Status ==4) { for (int j = 0; j < resItems.size(); j++) { ResponseItem
	 * resItem = resItems.get(j); PrePaidParamSetTmnlBean logBean =
	 * getBeanByTmnlAssetNo(tmnlList, res.getTmnlAssetNo(),
	 * resItems.get(j).getPn()); if(logBean != null) { String protocolCode =
	 * logBean.getProtocolCode(); l.setOpObjNo((long)resItems.get(j).getPn());
	 * // 对各规约项分别设置，并保存 l.setCurValue(logBean.getAppNo());
	 * l.setProtItemNo(protocolCode+"042F001");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(logBean.getRefreshFlag());
	 * l.setProtItemNo(protocolCode+"042F002");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(logBean.getBuyValue().toString());
	 * l.setProtItemNo(protocolCode+"042F003");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(logBean.getAlarmValue().toString());
	 * l.setProtItemNo(protocolCode+"042F004");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(logBean.getJumpValue().toString());
	 * l.setProtItemNo(protocolCode+"042F005");
	 * iLOpTmnlLogManager.saveTmnlLog(l); }
	 * 
	 * // 向前台页面返回参数下发结果 if (Status == 3) execResultList.add(new
	 * PrePaidExecResultBean( res.getTmnlAssetNo()+ resItem.getPn(), "2",
	 * Status.toString())); else execResultList.add(new PrePaidExecResultBean(
	 * res.getTmnlAssetNo()+ resItem.getPn(), "-1", Status.toString())); } }
	 * else { //对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
	 * List<PrePaidParamSetTmnlBean> noResponseList = noResponseTmnl(tmnlList,
	 * res.getTmnlAssetNo()); if(noResponseList ==null) { continue; } for (int j
	 * = 0; j < noResponseList.size(); j++) { PrePaidParamSetTmnlBean bean =
	 * noResponseList.get(j); l.setOpObjNo((long)bean.getTotalNo()); //
	 * 对各规约项分别设置，并保存 l.setCurValue(bean.getAppNo());
	 * l.setProtItemNo(bean.getProtocolCode()+"042F001");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(bean.getRefreshFlag());
	 * l.setProtItemNo(bean.getProtocolCode()+"042F002");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(bean.getBuyValue().toString());
	 * l.setProtItemNo(bean.getProtocolCode()+"042F003");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(bean.getAlarmValue().toString());
	 * l.setProtItemNo(bean.getProtocolCode()+"042F004");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * l.setCurValue(bean.getJumpValue().toString());
	 * l.setProtItemNo(bean.getProtocolCode()+"042F005");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * execResultList.add(new PrePaidExecResultBean(bean.getTmnlAssetNo()+
	 * bean.getTotalNo(), "-1", Status.toString())); } } } } else { for (int n =
	 * 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
	 * PrePaidParamSetTmnlBean bean = tmnlList.get(n); execResultList.add(new
	 * PrePaidExecResultBean(bean.getTmnlAssetNo() + bean.getTotalNo(), "-1",
	 * "0")); } }
	 * 
	 * List<PrePaidExecResultBean> turnResultList = new
	 * ArrayList<PrePaidExecResultBean>(); //等待轮次下发返回结果 List<Response>
	 * turnResList = taskDeal.getAllResponse(second/2); if (null != turnResList
	 * && 0 < turnResList.size()) { for (int i = 0; i < turnResList.size(); i++)
	 * { res = (Response) turnResList.get(i); Short Status =
	 * res.getTaskStatus();//记录终端返回状态
	 * 
	 * LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志
	 * 
	 * // 设置共有属性值 l.setOrgNo(user.getOrgNo()); l.setEmpNo(user.getStaffNo());
	 * l.setIpAddr(localIp); l.setOpModule("电控轮次下发"); l.setOpButton("参数下发");
	 * l.setOpType((short) 1);// 参数下发 l.setTmnlAssetNo(res.getTmnlAssetNo());
	 * l.setOpTime(new Date()); l.setCurStatus(Status.toString());
	 * l.setOpObj((short) 4);
	 * 
	 * List<ResponseItem> resItems = res.getResponseItems(); if(Status ==3
	 * ||Status ==4) { for (int j = 0; j < resItems.size(); j++) { ResponseItem
	 * resItem = resItems.get(j); PrePaidParamSetTmnlBean logBean =
	 * getBeanByTmnlAssetNo(tmnlList, res.getTmnlAssetNo(),
	 * resItems.get(j).getPn()); if(logBean != null) { String protocolCode =
	 * logBean.getProtocolCode(); l.setOpObjNo((long)resItems.get(j).getPn());
	 * // 对各规约项分别设置，并保存 l.setCurValue(turnFlag);
	 * l.setProtItemNo(protocolCode+"0430001");
	 * iLOpTmnlLogManager.saveTmnlLog(l); }
	 * 
	 * // 向前台页面返回参数下发结果 if (Status == 3) turnResultList.add(new
	 * PrePaidExecResultBean(res.getTmnlAssetNo()+ resItem.getPn(), "3",
	 * Status.toString())); else turnResultList.add(new
	 * PrePaidExecResultBean(res.getTmnlAssetNo()+ resItem.getPn(), "-1",
	 * Status.toString())); } } else {
	 * //对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
	 * List<PrePaidParamSetTmnlBean> noResponseList = noResponseTmnl(tmnlList,
	 * res.getTmnlAssetNo()); if(noResponseList ==null) { continue; } for (int j
	 * = 0; j < noResponseList.size(); j++) { PrePaidParamSetTmnlBean bean =
	 * noResponseList.get(j); l.setOpObjNo((long)bean.getTotalNo()); //
	 * 对各规约项分别设置，并保存 l.setCurValue(turnFlag);
	 * l.setProtItemNo(bean.getProtocolCode()+"0430001");
	 * iLOpTmnlLogManager.saveTmnlLog(l);
	 * 
	 * turnResultList.add(new PrePaidExecResultBean(bean.getTmnlAssetNo()+
	 * bean.getTotalNo(), "-1", Status.toString())); } } } } else { for (int n =
	 * 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list 为空，则返回全部失败
	 * PrePaidParamSetTmnlBean bean = tmnlList.get(n); turnResultList.add(new
	 * PrePaidExecResultBean(bean.getTmnlAssetNo() + bean.getTotalNo(), "-1",
	 * "0")); } }
	 * 
	 * List<PrePaidExecResultBean> resultList = new
	 * ArrayList<PrePaidExecResultBean>();
	 * 
	 * for (int i = 0; i < turnResultList.size(); i++) { PrePaidExecResultBean
	 * resBean = turnResultList.get(i); PrePaidExecResultBean bean =
	 * getBeanByTmnlAssetNo(execResultList, resBean.getKeyId()); if(bean ==null)
	 * { resultList.add(new PrePaidExecResultBean(resBean.getKeyId(), "-1",
	 * resBean.getStatusCode())); } else if("3".equals(resBean.getExecFlag()) &&
	 * "2".equals(bean.getExecFlag())) { resultList.add(new
	 * PrePaidExecResultBean(resBean.getKeyId(), "3", resBean.getStatusCode()));
	 * } else { resultList.add(new PrePaidExecResultBean(resBean.getKeyId(),
	 * "-1", resBean.getStatusCode())); } }
	 * 
	 * return resultList; }
	 */
	public List<PrePaidExecResultBean> prePaidParamSetView(
			List<PrePaidParamSetTmnlBean> tmnlList, String turnFlag,
			int second, PSysUser user, String localIp) throws DBAccessException {

		StringBuffer turnValue = new StringBuffer();
		// 将前台传的轮次编号(2,6)转化成(01000100)的形式
		String[] turnFlags = turnFlag.split(",");
		for (char j = '8'; j > '0'; j--) {
			byte flag = 0;
			for (int i = 0; i < turnFlags.length; i++) {
				char c = turnFlags[i].charAt(0);
				if (c == j) {
					turnValue.append("1");
					flag = 1;
				}
			}
			if (flag == 0) {
				turnValue.append("0");
			}
		}
		turnFlag = turnValue.toString();

		List<PrePaidExecResultBean> resultList = new ArrayList<PrePaidExecResultBean>();
		if (tmnlList == null || tmnlList.size() <= 0) {
			return resultList;
		}
		TaskDeal taskDeal = new TaskDeal();// 生成请求对象

		PrePaidParamSetTmnlBean flagBean = tmnlList.get(0);

		List<ParamItem> paramList = new ArrayList<ParamItem>();

		List<String> tmnlAssetNoList = new ArrayList<String>();
		// 循环终端进行参数下发
		for (int n = 0; n < tmnlList.size(); n++) {
			PrePaidParamSetTmnlBean bean = tmnlList.get(n);
			// 终端资产号集合
			if (n == 0) {
				tmnlAssetNoList.add(bean.getTmnlAssetNo());// 加入终端资产编号列表

				ParamItem factItem = setParamItem(bean);
				paramList.add(factItem);

				ParamItem turnItem = setTurnItem(bean, turnFlag);
				paramList.add(turnItem);

				if (tmnlList.size() == 1) {// 如果前台终端List长度为1，直接进行下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
							FrontConstant.SET_PARAMETERS, paramList);
				}
				continue;
			}

			// 参数项设置
			if (!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())) {// 参数下发
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.SET_PARAMETERS, paramList);

				// 一个终端组一个包，遇到终端不同的要新建paramList对象
				paramList = new ArrayList<ParamItem>();

				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(bean.getTmnlAssetNo());
				flagBean = bean;
			}

			ParamItem factItem = setParamItem(bean);
			paramList.add(factItem);
			ParamItem turnItem = setTurnItem(bean, turnFlag);
			paramList.add(turnItem);

			if (n == tmnlList.size() - 1) {// list结尾调用接口，循环结束
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.SET_PARAMETERS, paramList);
			}
		}

		Response res = null;
		List<Response> list = taskDeal.getAllResponse(second / 2);
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				res = (Response) list.get(i);
				Short Status = res.getTaskStatus();// 记录终端返回状态

				LOpTmnlLog l = new LOpTmnlLog();// 写终端操作日志

				// 设置共有属性值
				l.setOrgNo(user.getOrgNo());
				l.setEmpNo(user.getStaffNo());
				l.setIpAddr(localIp);
				l.setOpModule("预付费控制参数下发");
				l.setOpButton("参数下发");
				l.setOpType((short) 1);// 参数下发
				l.setTmnlAssetNo(res.getTmnlAssetNo());
				l.setOpTime(new Date());
				l.setCurStatus(Status.toString());
				l.setOpObj((short) 4);

				List<ResponseItem> resItems = res.getResponseItems();
				if (Status == 3 || Status == 4) {
					for (int j = 0; j < resItems.size(); j++) {
						ResponseItem resItem = resItems.get(j);
						PrePaidParamSetTmnlBean logBean = getBeanByTmnlAssetNo(
								tmnlList, res.getTmnlAssetNo(), resItems.get(j)
										.getPn());
						if (logBean != null) {
							String protocolCode = logBean.getProtocolCode();
							l.setOpObjNo((long) resItems.get(j).getPn());
							// 对各规约项分别设置，并保存
							l.setCurValue(logBean.getAppNo());
							l.setProtItemNo(protocolCode + "042F001");
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setCurValue(logBean.getRefreshFlag());
							l.setProtItemNo(protocolCode + "042F002");
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setCurValue(logBean.getBuyValue().toString());
							l.setProtItemNo(protocolCode + "042F003");
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setCurValue(logBean.getAlarmValue().toString());
							l.setProtItemNo(protocolCode + "042F004");
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setCurValue(logBean.getJumpValue().toString());
							l.setProtItemNo(protocolCode + "042F005");
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setCurValue(turnFlag);
							l.setProtItemNo(protocolCode + "0430001");
							iLOpTmnlLogManager.saveTmnlLog(l);
						}

						// 向前台页面返回参数下发结果
						if (Status == 3)
							resultList.add(new PrePaidExecResultBean(res
									.getTmnlAssetNo()
									+ resItem.getPn(), "2", Status.toString()));
						else
							resultList
									.add(new PrePaidExecResultBean(res
											.getTmnlAssetNo()
											+ resItem.getPn(), "-1", Status
											.toString()));
					}
				} else {
					// 对于超时，或终端不在线的情况，responseItems为空，即整个终端的所有总加组全部下放失败
					List<PrePaidParamSetTmnlBean> noResponseList = noResponseTmnl(
							tmnlList, res.getTmnlAssetNo());
					if (noResponseList == null) {
						continue;
					}
					for (int j = 0; j < noResponseList.size(); j++) {
						PrePaidParamSetTmnlBean bean = noResponseList.get(j);
						l.setOpObjNo((long) bean.getTotalNo());
						// 对各规约项分别设置，并保存
						l.setCurValue(bean.getAppNo());
						l.setProtItemNo(bean.getProtocolCode() + "042F001");
						iLOpTmnlLogManager.saveTmnlLog(l);

						l.setCurValue(bean.getRefreshFlag());
						l.setProtItemNo(bean.getProtocolCode() + "042F002");
						iLOpTmnlLogManager.saveTmnlLog(l);

						l.setCurValue(bean.getBuyValue().toString());
						l.setProtItemNo(bean.getProtocolCode() + "042F003");
						iLOpTmnlLogManager.saveTmnlLog(l);

						l.setCurValue(bean.getAlarmValue().toString());
						l.setProtItemNo(bean.getProtocolCode() + "042F004");
						iLOpTmnlLogManager.saveTmnlLog(l);

						l.setCurValue(bean.getJumpValue().toString());
						l.setProtItemNo(bean.getProtocolCode() + "042F005");
						iLOpTmnlLogManager.saveTmnlLog(l);

						l.setCurValue(turnFlag);
						l.setProtItemNo(bean.getProtocolCode() + "0430001");
						iLOpTmnlLogManager.saveTmnlLog(l);
						resultList.add(new PrePaidExecResultBean(bean
								.getTmnlAssetNo()
								+ bean.getTotalNo(), "-1", Status.toString()));
					}
				}
			}
		} else {
			for (int n = 0; n < tmnlList.size(); n++) {// 若通信出错，返回response list
				// 为空，则返回全部失败
				PrePaidParamSetTmnlBean bean = tmnlList.get(n);
				resultList.add(new PrePaidExecResultBean(bean.getTmnlAssetNo()
						+ bean.getTotalNo(), "-1", "0"));
			}
		}

		return resultList;
	}

	/**
	 * 按终端资产编号和总加组号返回PrePaidExecResultBean对象
	 * 
	 * @param list
	 * @param tmnlAssetNo
	 * @return
	 */
	protected PrePaidExecResultBean getBeanByTmnlAssetNo(
			List<PrePaidExecResultBean> list, String keyId) {
		for (PrePaidExecResultBean bean : list) {
			if (keyId.equals(bean.getKeyId()))
				return bean;
		}
		return null;
	}

	/**
	 * 设置预付费下发参数设置
	 * 
	 * @param bean
	 * @return
	 */
	public ParamItem setParamItem(PrePaidParamSetTmnlBean bean) {
		ParamItem factItem = new ParamItem();
		List<Item> items = null;
		Item item = null;
		factItem.setFn((short) 47);// 设置FN号
		factItem.setPoint(bean.getTotalNo());// 设置point
		// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		// 编码对象集合
		items = new ArrayList<Item>();
		// 设置购电单号
		item = new Item(bean.getProtocolCode() + "042F001");
		item.setValue(bean.getAppNo());
		items.add(item);
		// 设置追加/刷新标志
		item = new Item(bean.getProtocolCode() + "042F002");
		item.setValue(bean.getRefreshFlag());
		items.add(item);
		item = new Item(bean.getProtocolCode() + "042F003");
		item.setValue(bean.getBuyValue().toString());
		items.add(item);
		item = new Item(bean.getProtocolCode() + "042F004");
		item.setValue(bean.getAlarmValue().toString());
		items.add(item);
		item = new Item(bean.getProtocolCode() + "042F005");
		item.setValue(bean.getJumpValue().toString());
		items.add(item);

		factItem.setItems((ArrayList<Item>) items);
		return factItem;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	public ParamItem setTurnItem(PrePaidParamSetTmnlBean bean, String turnFlag) {
		ParamItem factItem = new ParamItem();
		List<Item> items = null;
		Item item = null;
		factItem.setFn((short) 48);// 设置FN号
		factItem.setPoint(bean.getTotalNo());// 设置point
		// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		// 编码对象集合
		items = new ArrayList<Item>();
		// 设置购电单号
		item = new Item(bean.getProtocolCode() + "0430001");
		item.setValue(turnFlag);
		items.add(item);

		factItem.setItems((ArrayList<Item>) items);
		return factItem;
	}

	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list
	 * 
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<PrePaidParamSetTmnlBean> noResponseTmnl(
			List<PrePaidParamSetTmnlBean> tmnlList, String tmnlAssetNo) {
		if (tmnlList == null || tmnlList.size() <= 0 || tmnlAssetNo == null) {
			return null;
		}
		List<PrePaidParamSetTmnlBean> resList = new ArrayList<PrePaidParamSetTmnlBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidParamSetTmnlBean bean = tmnlList.get(i);
			if (tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}

	/**
	 * 从前台页面传入的终端list中查询终端号为指定编号的总加组list
	 * 
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	private List<PrePaidControlTmnlBean> noCtrlResponseTmnl(
			List<PrePaidControlTmnlBean> tmnlList, String tmnlAssetNo) {
		if (tmnlList == null || tmnlList.size() <= 0 || tmnlAssetNo == null) {
			return null;
		}
		List<PrePaidControlTmnlBean> resList = new ArrayList<PrePaidControlTmnlBean>();
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidControlTmnlBean bean = tmnlList.get(i);
			if (tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				resList.add(bean);
			}
		}
		return resList;
	}

	/**
	 * 根据终端资产编号获取终端终端信息对象
	 * 
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return 终端信息对象
	 */
	protected PrePaidParamSetTmnlBean getBeanByTmnlAssetNo(
			List<PrePaidParamSetTmnlBean> tmnlList, String tmnlAssetNo,
			Short totalNo) {
		if (tmnlAssetNo == null || "".equals(tmnlAssetNo))
			return null;
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidParamSetTmnlBean bean = tmnlList.get(i);
			if (tmnlAssetNo.equals(bean.getTmnlAssetNo())
					&& totalNo == bean.getTotalNo()) {
				return bean;
			}
		}
		return null;
	}

	/**
	 * 根据终端资产编号获取终端规约编码
	 * 
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	protected String getProtocolCodeByTmnlAssetNo(
			List<PrePaidControlTmnlBean> tmnlList, String tmnlAssetNo) {
		if (tmnlAssetNo == null || "".equals(tmnlAssetNo))
			return "";
		for (int i = 0; i < tmnlList.size(); i++) {
			PrePaidControlTmnlBean bean = tmnlList.get(i);
			if (tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}

	/**
	 * 无参数方案公用方法 将前台传入的终端list信息组包，发送给前置接口
	 */
	protected void schemeRelease(List<PrePaidControlTmnlBean> tmnlList,
			TaskDeal taskDeal, short ctrlType) {
		PrePaidControlTmnlBean flagBean = tmnlList.get(0);
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		List<String> tmnlAssetNoList = new ArrayList<String>();
		tmnlAssetNoList.add(flagBean.getTmnlAssetNo());
		for (int n = 0; n < tmnlList.size(); n++) {
			PrePaidControlTmnlBean bean = tmnlList.get(n);
			if (n == 0) {
				ParamItem factItem = new ParamItem();
				factItem.setFn(ctrlType);// 设置FN号-F17时段控解除, F18厂休控解除,
				// F20当前功率下浮控解除
				factItem.setPoint(bean.getTotalNo());// 设置point
				// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				// 编码对象集合
				List<Item> items = new ArrayList<Item>();

				factItem.setItems((ArrayList<Item>) items);
				paramList.add(factItem);

				if (tmnlList.size() == 1) {// 如果前台终端List长度为1，直接进行下发
					taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
							FrontConstant.CONTROL_COMMAND, paramList);
				}
				continue;
			}

			// 参数项设置
			if (!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())) {
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.CONTROL_COMMAND, paramList);

				// 一个终端组一个包，遇到终端不同的要新建paramList对象
				paramList = new ArrayList<ParamItem>();

				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(bean.getTmnlAssetNo());

				flagBean = bean;
			}

			ParamItem factItem = new ParamItem();
			factItem.setFn(ctrlType);// 设置FN号-F17时段控解除, F18厂休控解除, F20当前功率下浮控解除
			factItem.setPoint(bean.getTotalNo());// 设置point
			// (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
			// 编码对象集合
			List<Item> items = new ArrayList<Item>();

			factItem.setItems((ArrayList<Item>) items);
			paramList.add(factItem);

			if (n == tmnlList.size() - 1) {
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,
						FrontConstant.CONTROL_COMMAND, paramList);
			}
		}
	}

	@Override
	public List<BCommProtocol> getProtocolList(String proCode) throws Exception {
		try {
			return prePaidDao.findType(proCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("电能表控制规约项查询出错");
		}
	}

	@Override
	 public List<MeterInfoBean> queryTermList(String nodeType, String
	 nodeValue,
	 PSysUser userInfo, String protocolCode) throws Exception{
	 try {
			return prePaidDao.queryTermList(nodeType, nodeValue, userInfo,
	 protocolCode);
	 } catch (DataAccessException dbe) {
	 throw new DBAccessException(BaseException.processDBException(dbe));
	 } catch (Exception e) {
	 throw new ServiceException("电能表控制规约项查询出错");
	 }
	 }


	
	@Override
	public List<MeterInfoBean> queryTmnlArrList(String nodeType,
			String nodeValue, PSysUser userInfo, String protocolCode)
			throws Exception {
		try {
			return prePaidDao.queryTmnlArrList(nodeType, nodeValue, userInfo,
					protocolCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("电能表控制规约项查询出错");
		}
	}
	// ---------------------------四川盘龙电表通断service层实现-------------
	@Override
	public List<MeterControlInfoBean> queryMeterList(String nodeType,
			String nodeValue, PSysUser userInfo, String protocolCode)
			throws Exception {
		try {
			return prePaidDao.queryMeterList(nodeType, nodeValue, userInfo,
					protocolCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("电能表查询出错");
		}
	}
}
