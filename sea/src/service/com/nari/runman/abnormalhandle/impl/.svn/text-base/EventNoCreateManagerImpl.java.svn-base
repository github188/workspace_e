package com.nari.runman.abnormalhandle.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.flowhandle.FEventSrc;
import com.nari.runman.abnormalhandle.EventNoCreateDao;
import com.nari.runman.abnormalhandle.EventNoCreateManager;
import com.nari.util.DeviceAbnormalUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class EventNoCreateManagerImpl implements EventNoCreateManager {
	
	private EventNoCreateDao eventNoCreateDao;

	public void setEventNoCreateDao(EventNoCreateDao eventNoCreateDao) {
		this.eventNoCreateDao = eventNoCreateDao;
	}

	@Override
	public long createEventNo(FEventSrc fEventSrc) throws Exception {
//		String eventNo = this.getEventNo(fEventSrc.getOrgNo()); //生成工单号
//		fEventSrc.setEventId(Long.valueOf(eventNo));
		try {
			fEventSrc.setFlowStatusCode(DeviceAbnormalUtil.NEW_EXCEPTION);
			fEventSrc.setEvnetTime(new Date());
			//暂时使用EventInfoBean
			return this.eventNoCreateDao.saveEventNo(fEventSrc);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("工单生成异常");
		}
	}
	
	/**
	 * 生成工单号
	 * @param dwdm
	 * @return
	 */
//	private String getEventNo(String orgNo) {
//		if(orgNo == null ) return "";
//		//获取当前时间（年月日时分秒）
//		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		String time = dateFormat.format(cal.getTime());
//		// 工单号 生成规则：99 + 单位代码2位 + 年月日时分秒
//		String gdh = "99" + orgNo.substring(0, 2) + time;
//		return gdh;
//	}

}
