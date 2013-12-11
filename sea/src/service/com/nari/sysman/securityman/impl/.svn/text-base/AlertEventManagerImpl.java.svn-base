package com.nari.sysman.securityman.impl;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.SimpleBEventMark;
import com.nari.basicdata.SimpleEventDef;
import com.nari.elementsdata.ETmnlEventFull;
import com.nari.elementsdata.SimpleETmnlEvent;
import com.nari.flowhandle.FEventSrc;
import com.nari.privilige.PSysUser;
import com.nari.runman.abnormalhandle.EventNoCreateDao;
import com.nari.support.Page;
import com.nari.sysman.alertevent.AlertDataItem;
import com.nari.sysman.alertevent.TerminalAddr;
import com.nari.sysman.securityman.IAlertEventDao;
import com.nari.sysman.securityman.IAlertEventManager;
import com.nari.util.DeviceAbnormalUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * Manager 类 AlertEventManagerImpl
 * 
 * @author zhangzhw
 * @describe 处理报警事件的服务类
 */
public class AlertEventManagerImpl implements IAlertEventManager {
	public IAlertEventDao alertEventDao;
	
	private EventNoCreateDao eventNoCreateDao;

	public void setEventNoCreateDao(EventNoCreateDao eventNoCreateDao) {
		this.eventNoCreateDao = eventNoCreateDao;
	}

	/**
	 * 方法 querySimpleEtmnlEvent
	 * 
	 * @param start
	 * @param limit
	 * @param username
	 * @return Page<SimpleETmnlEvent> 事件列表
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start, int limit,
			String alertLevel, String username, String consNo, String orgNo,
			String startDate, String endDate, PSysUser pSysUser)
			throws Exception {
		try {
			return alertEventDao.querySimpleEtmnlEvent(start, limit,
					alertLevel, username, consNo, orgNo, startDate, endDate,
					pSysUser);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYEVENTEXCEPTION);
		}
	}

	/**
	 * 方法 queryAlertDataItem
	 * 
	 * @param eventId
	 * @return List<AlertDataItem> 报警数据项明细
	 */
	@Override
	public List<AlertDataItem> queryAlertDataItem(String orgNo, String rd,
			String storeMode, PSysUser pSysUser) throws Exception {
		try {
			List<AlertDataItem> list = new ArrayList<AlertDataItem>();
			List<ETmnlEventFull> listE = alertEventDao.queryETmnlEventFull(
					orgNo, rd, pSysUser);
			// 理论上只有一条数据
			if (listE.size() > 0) {
				for (ETmnlEventFull e : listE) {
					String eventNo = e.getEventNo();
					// 取得事件相关的信息
					List<SimpleEventDef> ledf = null;
					if (storeMode.equals("0"))
						ledf = alertEventDao.queryEventDef(eventNo, "blank");
					else
						ledf = alertEventDao.queryEventDef(eventNo,
								parseItemNos(e.getEventData()));

					if (ledf == null || ledf.size() < 1)
						break;

					for (SimpleEventDef o : ledf) {
						if (o == null)
							break;
						int dataN = o.getDatan();
						String expDataN = o.getExplanDatan().toString();
						String dataValue = null;
						if (storeMode.equals("0"))
							dataValue = getDataValue(e, dataN);
						else
							dataValue = getDataValue(e.getEventData(), o
									.getItemNo());

						// 根据事件定义对值进行解析
						String dispDataValue = null;
						if (dataValue == null) {

						} else {
							if (expDataN.equals("0")) {
								dispDataValue = dataValue;
							} else if (expDataN.equals("1")) {
								dispDataValue = queryEventMark(eventNo, dataN,
										dataValue, "1");
							} else {
								dispDataValue = queryEventMark(eventNo, dataN,
										dataValue, "2");
							}
						}

						// 转换成前台显示的数据
						String dataName = o.getItemName();
						String dataUnit = o.getUnit();
						AlertDataItem a = new AlertDataItem();
						a.setDataName(dataName);
						a.setDataValue(dispDataValue);
						a.setDataUnit(dataUnit);
						list.add(a);
					}

					// list=alertEventDao.queryAlertEventItem(eventNo,sin);

				}
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYEVENTDATAITMEEXCEPTION);
		}
	}

	/**
	 * 统计当日报警事件数据
	 */
	@Override
	public List<Object> queryAlertEventCount(PSysUser pSysUser)
			throws Exception {
		try {
			return alertEventDao.queryAlertEventCount(pSysUser);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYEVENTCOUNTEXCEPTION);
		}
	}

	/**
	 * 方法　queryEventMark
	 * 
	 * @param eventNo
	 * @param dataN
	 * @param dataValue
	 * @param mark
	 * @return　查询并组合事件返回结果
	 */
	private String queryEventMark(String eventNo, int dataN, String dataValue,
			String mark) {
		String rtn = "";
		List<SimpleBEventMark> eventMark = alertEventDao.queryEventMark(
				eventNo, dataN);

		// 标志为1时　按mark0 和　mark1 组合　　
		if (mark.equals("1"))
			for (SimpleBEventMark s : eventMark) {

				for (int i = 0; i < dataValue.length(); i++) {
					if (dataValue.substring(i, i).equals("0"))
						rtn += s.getMarkName() + s.getMark0();
					else
						rtn += s.getMarkName() + s.getMark1();
				}
			}
		else // mark.equals("2") //标志为2时直接取值
		{
			for (SimpleBEventMark s : eventMark) {
				if (dataValue.equals(s.getMarkNo().toString())) {
					rtn += s.getMarkName();
					break;
				}

			}
		}

		return rtn;
	}

	/**
	 * 
	 * @param eventData
	 * @return　将指定的eventData解析为in　条件
	 */
	private String parseItemNos(String eventData) {
		String rtn = null;
		try {
			if (eventData == null || eventData.isEmpty())
				return null;
			rtn = "( ";
			String[] single = eventData.split(";");
			for (int i = 0; i < single.length; i++) {
				if (single[i] == null || single[i].isEmpty())
					break;
				String[] itemNo = single[i].split("=");
				rtn += "'" + itemNo[0].trim() + "',";
			}
			rtn = rtn.substring(0, rtn.length() - 1);
			rtn += ")";
		} catch (Exception e) {
			return null;
		} finally {
			return rtn;
		}
	}

	/**
	 * 
	 * @param value
	 * @return　解析数据
	 */
	@SuppressWarnings("finally")
	private String getDataValue(String eventData, String itemNo) {
		String rtn = null;
		try {
			String[] single = eventData.split(";");
			for (int i = 0; i < single.length; i++) {
				if (single[i] == null || single[i].isEmpty())
					break;
				String[] itemNos = single[i].split("=");
				if (itemNos[0].equals(itemNo)) {
					rtn = itemNos[1];
					break;
				}
			}
		} catch (Exception e) {
			return null;
		} finally {
			return rtn;
		}

	}

	// 通过dataN的定义取得 dataN
	private String getDataValue(ETmnlEventFull e, int dataN) {
		try {
			Method method = e.getClass().getDeclaredMethod("getData" + dataN,
					null);
			return (String) method.invoke(e, null);

		} catch (Exception ex) {
			return null;
		}

	}

	public IAlertEventDao getAlertEventDao() {
		return alertEventDao;
	}

	public void setAlertEventDao(IAlertEventDao alertEventDao) {
		this.alertEventDao = alertEventDao;
	}
	
	
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String type,String nodeId,String terminalAddr,String startDate,String endDate,String staffNo,String orgType,String consTYpe, String eventType) throws Exception{
		return alertEventDao.querySimpleEtmnlEvent(start, limit, type, nodeId, terminalAddr, startDate, endDate, staffNo,orgType,consTYpe, eventType);
	}

	/**
	 * chenjg
	 * 事件报警关注
	 */
	public void attentionEvent(String rowid,FEventSrc fEventSrc) throws Exception{
		//生成工单
		fEventSrc.setFlowStatusCode(DeviceAbnormalUtil.NEW_EXCEPTION);
		fEventSrc.setEvnetTime(new Date());
		//暂时使用EventInfoBean
		eventNoCreateDao.saveEventNo(fEventSrc);
		
		//更新事件状态
		alertEventDao.update(rowid, DeviceAbnormalUtil.NEW_EXCEPTION);
	}
	
	/**
	 * chenjg
	 * 根据rowid 查询 事件报警
	 * @return
	 * @throws Exception
	 */
	public ETmnlEventFull getEventByRowId(String rowid) throws Exception{
		return alertEventDao.findByRowId(rowid);
	}
	
	
	/**
	 * 根据rowid查询事件
	 * @param start
	 * @param limit
	 * @param rowid
	 * @return
	 * @throws Exception
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String rowid) throws Exception{
		return alertEventDao.querySimpleEtmnlEvent(start, limit, rowid);
	}

	/**
	 * chenjg
	 * 根据用户号查询终端
	 * @param consNo
	 * @return
	 * @throws Exception
	 */
	public List <TerminalAddr> getTerminalAddrByConsNo(String consNo) throws Exception{
		return alertEventDao.findTerminalAddrByConsNo(consNo);
	}

	@Override
	public List<BProtocolEvent> findEventType() throws Exception {
		try {
			return alertEventDao.findEventType();
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("事件类型视图查询出错");
		}
	}
}
