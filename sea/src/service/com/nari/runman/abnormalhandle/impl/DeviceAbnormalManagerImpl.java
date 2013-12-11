package com.nari.runman.abnormalhandle.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.flowhandle.EventSearchInfoBean;
import com.nari.flowhandle.FEventClose;
import com.nari.flowhandle.FEventFlow;
import com.nari.flowhandle.FEventInfoBean;
import com.nari.flowhandle.FEventShield;
import com.nari.flowhandle.FEventSrc;
import com.nari.runman.abnormalhandle.DeviceAbnormalDao;
import com.nari.runman.abnormalhandle.DeviceAbnormalHibernateDao;
import com.nari.runman.abnormalhandle.DeviceAbnormalManager;
import com.nari.runman.abnormalhandle.DeviceExceptionDao;
import com.nari.support.Page;
import com.nari.sysman.securityman.IAlertEventDao;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;
import com.nari.util.ws.ClientAuthenticationHandler;
import com.nari.util.ws.XMLSwitchUtil;

public class DeviceAbnormalManagerImpl implements DeviceAbnormalManager {
	// private Logger logger = Logger.getLogger(this.getClass());

	DeviceAbnormalDao deviceAbnormalDao;
	DeviceAbnormalHibernateDao deviceAbnormalHibernateDao;
	IAlertEventDao alertEventDao;

	DeviceExceptionDao deviceExceptionDao;

	public void setDeviceAbnormalHibernateDao(
			DeviceAbnormalHibernateDao deviceAbnormalHibernateDao) {
		this.deviceAbnormalHibernateDao = deviceAbnormalHibernateDao;
	}

	public void setDeviceAbnormalDao(DeviceAbnormalDao deviceAbnormalDao) {
		this.deviceAbnormalDao = deviceAbnormalDao;
	}
	
	public void setAlertEventDao(IAlertEventDao alertEventDao) {
		this.alertEventDao = alertEventDao;
	}

	public void setDeviceExceptionDao(DeviceExceptionDao deviceExceptionDao) {
		this.deviceExceptionDao = deviceExceptionDao;
	}

	@Override
	public Page<FEventInfoBean> getEventInfo(EventSearchInfoBean eventInfoBean,
			long start, int limit) throws Exception {
		try {
			return this.deviceAbnormalDao.getEventInfo(eventInfoBean, start,
					limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单处理查询出错");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getMaketingInf(FEventSrc fEventSrc, FEventFlow fEventFlow) throws Exception {
		try {
			List list = new ArrayList();
			//生成营销派工信息
			Map<String,Object> hm = new HashMap<String,Object>();
			hm.put("ORG_NO", fEventSrc.getOrgNo());
			hm.put("COLL_OBJ_ID", "001");
			hm.put("ALARM_CODE", fEventSrc.getEventCode());
			hm.put("ALARM_DESC", fEventSrc.getEventName());
			hm.put("TYPE_CODE", fEventSrc.getEventType());
			hm.put("OCCUR_TIME", fEventSrc.getEvnetTime());
			hm.put("RPT_EMP_NO", fEventSrc.getStaffNo());
			hm.put("TERMINAL_ID", "100");
			list.add(hm);
			
			XMLSwitchUtil xsu=new XMLSwitchUtil();
			
			//获取webservice通信连接
			String	url = findConfig("sea_url");
			String	username = findConfig("SEA_USERNAME");
			String	password = findConfig("SEA_PASSWORD");
			Client client = new Client(new URL(url));
			client.addOutHandler(new ClientAuthenticationHandler(username,
					password));
			//营销接口派工
			Object result[] = client.invoke("WS_ZZ_EXCP_ALARM", new Object[] {xsu.switchListToXML(list)});
			String resultFlag = result[0].toString();
			if(null == resultFlag|| "0".equals(resultFlag))
			{
				throw new ServiceException("派工进营销处理失败");
			} else {
				// 删除屏蔽周期
				this.deviceAbnormalDao.deleteEventShield(fEventSrc.getEventId());
				// 更新工单源状态
				this.deviceAbnormalHibernateDao.updateEventSrc(fEventSrc);
				// 插入工单流水
				this.deviceAbnormalHibernateDao.insertEventFlow(fEventFlow);
				//更新已处理异常的状态code
				this.updateExceptionStatus(fEventSrc);
			}
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("派工进营销处理出错");
		}
	}

	@Override
	public void holdOn(FEventSrc fEventSrc, FEventFlow fEventFlow, FEventShield fEventShield) throws Exception {
		try {
			// 删除屏蔽周期
			this.deviceAbnormalDao.deleteEventShield(fEventShield.getId().getEventId());
			// 插入屏蔽周期
			this.deviceAbnormalHibernateDao.insertEventShield(fEventShield);
			// 更新工单源状态
			this.deviceAbnormalHibernateDao.updateEventSrc(fEventSrc);
			// 插入工单流水
			this.deviceAbnormalHibernateDao.insertEventFlow(fEventFlow);
			//更新已处理异常的状态code
			this.updateExceptionStatus(fEventSrc);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单处理挂起操作出错");
		}
	}

	@Override
	public void localHandle(FEventSrc fEventSrc, FEventFlow fEventFlow)
			throws Exception {
		try {
			// 删除屏蔽周期
			this.deviceAbnormalDao.deleteEventShield(fEventSrc.getEventId());
			// 更新工单源状态
			this.deviceAbnormalHibernateDao.updateEventSrc(fEventSrc);
			// 插入工单流水
			this.deviceAbnormalHibernateDao.insertEventFlow(fEventFlow);
			//更新已处理异常的状态code
			this.updateExceptionStatus(fEventSrc);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单处理本地处理出错");
		}
	}

	@Override
	public int reportManager(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void savaRecord(FEventClose fEventClose, FEventFlow fEventFlow)
			throws Exception {
		try {
			// 删除屏蔽周期
			this.deviceAbnormalDao.deleteEventShield(fEventClose.getId().getEventId());
			//工单源
			List<FEventSrc> fslist = this.getEventInfo(fEventClose.getId().getEventId());
			FEventSrc fEventSrc = null;
			if (fslist !=null && fslist.size() != 0) {
				fEventSrc = fslist.get(0);
			} else {
				throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
			}
			fEventSrc.setFlowStatusCode(fEventFlow.getId().getFlowStatusCode());
			// 更新工单源状态
			this.deviceAbnormalHibernateDao.updateEventSrc(fEventSrc);
//			// 删除工单源信息
//			this.deviceAbnormalDao.deleteEventSrc(fEventClose.getEventId());
			// 插入工单流水
			this.deviceAbnormalHibernateDao.insertEventFlow(fEventFlow);
			//工单归档
			this.deviceAbnormalHibernateDao.insertEventClose(fEventClose);
			//更新已处理异常的状态code
			this.updateExceptionStatus(fEventSrc);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单处理归档操作出错");
		}
	}

	@Override
	public int sendToMarketBatch(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FEventSrc> getEventInfo(long eventId) throws Exception {
		try {
			// 取得工单信息
			return this.deviceAbnormalDao.getEventInfo(eventId);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}
	
	@Override
	public List<FEventInfoBean> getEventInfo(String fromId, Byte eventType) throws Exception {
		try {
			// 取得工单信息
			return this.deviceAbnormalDao.getEventInfo(fromId, eventType);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}
	
	/**
	 * 根据异常类型更新异常异常状态
	 * @param fEventSrc
	 * @throws Exception
	 */
	private void updateExceptionStatus(FEventSrc fEventSrc) throws Exception {
		List<FEventSrc> rs = this.deviceAbnormalDao.getEventInfo(fEventSrc.getEventId());
		if (rs.size() == 1) {
			Byte eventType = rs.get(0).getEventType();
			String fromId = rs.get(0).getFromId();
			if (eventType == 2) {
				this.alertEventDao.update(fromId, fEventSrc.getFlowStatusCode());
			} else if (eventType == 3) {
				this.deviceExceptionDao.updateFlowStatusByExId(fromId, fEventSrc.getFlowStatusCode());
			}
		} else {
			throw new ServiceException("异常查询出错");
		}
		
	}
	
	private String findConfig(String no) throws ServiceException{
		SqlData sd=SqlData.getOne();
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			return	 (String) tree.getJdbcTemplate().queryForObject(sd.archives_config,new Object[]{no}, String.class);
		} catch (Exception e) {
			return "";
		}
	}
}
