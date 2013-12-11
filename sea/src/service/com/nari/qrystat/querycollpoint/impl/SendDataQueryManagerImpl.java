package com.nari.qrystat.querycollpoint.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpCurCurveBean;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.qrystat.colldataanalyse.EMpVolCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalPowerCurveBean;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.qrystat.querycollpoint.ISendDataQueryDao;
import com.nari.qrystat.querycollpoint.ISendDataQueryManager;
import com.nari.qrystat.querycollpoint.SendDataQuery;
import com.nari.qrystat.querycollpoint.SendDataQueryB;
import com.nari.qrystat.querycollpoint.SendDataQueryBFail;
import com.nari.qrystat.querycollpoint.SendDataQueryC;
import com.nari.qrystat.querycollpoint.SendDataQueryD;
import com.nari.qrystat.querycollpoint.SendDataQueryDay;
import com.nari.qrystat.querycollpoint.SendDataQueryF;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

public class SendDataQueryManagerImpl implements ISendDataQueryManager {

	private ISendDataQueryDao sendDataQueryDao;

	/**
	 * @return the sendDataQueryDao
	 */
	public ISendDataQueryDao getSendDataQueryDao() {
		return sendDataQueryDao;
	}

	/**
	 * @param sendDataQueryDao
	 *            the sendDataQueryDao to set
	 */
	public void setSendDataQueryDao(ISendDataQueryDao sendDataQueryDao) {
		this.sendDataQueryDao = sendDataQueryDao;
	}

	@Override
	public Page<SendDataQuery> findSDQuery(String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,String DateStart, String DateEnd,
			String CONS_NO, String TG_ID, long start, int limit,PSysUser pSysUser)
			throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQuery(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType,DateStart, DateEnd,
					CONS_NO, TG_ID, start, limit,pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<SendDataQueryB> findSendDataQueryB(String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQueryB(MR_SECT_NO,sendDataQueryFlag,addr,sendDQOrgType,sendDQType, DateStart, DateEnd, CONS_NO, TG_ID, start, limit, pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	@Override
	public Page<SendDataQueryDay> findSendDataQueryDay(String fieldConsNo,String fieldassetNo,String MR_SECT_NO,String sendDataQueryFlag,String addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQueryDay(fieldConsNo,fieldassetNo,MR_SECT_NO, sendDataQueryFlag, addr, sendDQOrgType, sendDQType, DateStart, DateEnd, CONS_NO, TG_ID, start, limit, pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public List<SendDataQueryC> findSendDataQueryC(String DateStart,
			String DateEnd, String CONS_NO,PSysUser pSysUser) throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQueryC(DateStart, DateEnd,
					CONS_NO,pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	public Map<String, List<SendDataQueryC>> findSendDataQueryD(
			String DateStart, String DateEnd, String CONS_NO,PSysUser pSysUser) {

		Map<String, List<SendDataQueryC>> mapD = new HashMap<String, List<SendDataQueryC>>();

		try {
			List<SendDataQueryC> list = this.findSendDataQueryC(DateStart,
					DateEnd, CONS_NO,pSysUser);
			if (list != null && list.size() != 0 ) {
				String myData = list.get(0).getAssetNo();
				List<SendDataQueryC> mylist = new ArrayList<SendDataQueryC>();
				for (int i = 0;i<list.size();i++) {
					SendDataQueryC sendDataQueryC = list.get(i);
					if ((i < list.size()-1) && (myData.equals(sendDataQueryC.getAssetNo()))) {
						SendDataQueryC sdqc = new SendDataQueryC();
						sdqc.setDataDate(sendDataQueryC.getDataDate());
						sdqc.setAssetNo(sendDataQueryC.getAssetNo());
						sdqc.setPapE(sendDataQueryC.getPapE());
						mylist.add(sdqc);
					}else if(i == list.size()-1 && (myData.equals(sendDataQueryC.getAssetNo()))){
						SendDataQueryC sdqc = new SendDataQueryC();
						sdqc.setDataDate(sendDataQueryC.getDataDate());
						sdqc.setAssetNo(sendDataQueryC.getAssetNo());
						sdqc.setPapE(sendDataQueryC.getPapE());
						mylist.add(sdqc);
						mapD.put(myData, mylist);
					}			
					else {
						if(mylist.size() != 0){
							mapD.put(myData, mylist);
							mylist = new ArrayList<SendDataQueryC>();
						}
						myData = sendDataQueryC.getAssetNo();
						SendDataQueryC sdqc = new SendDataQueryC();
						sdqc.setDataDate(sendDataQueryC.getDataDate());
						sdqc.setAssetNo(sendDataQueryC.getAssetNo());
						sdqc.setPapE(sendDataQueryC.getPapE());
						mylist = new ArrayList<SendDataQueryC>();
						mylist.add(sdqc);
					}

				}
			}
		} catch (DBAccessException e) {
			e.printStackTrace();
		}
		return mapD;
	}
	
	public Page<SendDataQueryD> findSendDataQueryD(String CurDate,String TG_ID,long start, int limit,PSysUser pSysUser) throws DBAccessException{
		try {
			return sendDataQueryDao.findSendDataQueryD(CurDate, TG_ID,start,limit,pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}


	@Override
	public List<GeneralDataBean> findSendDataQueryE(String consNo,String asserNo, String dataDate,PSysUser pSysUser)
			throws DBAccessException {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		EMpEnergyCurveBean mpVol = sendDataQueryDao.findSendDataQueryE(consNo,asserNo, dataDate,pSysUser);
		if(mpVol == null){
			return  list;
		}
		Integer flag = mpVol.getDataPointFlag();
		return  getGeneralDataBeanArray(mpVol, flag);
	};
	/**
	 * 将各曲线对象转化为曲线图数据点列表
	 * @param curveBean 曲线对象
	 * @param flag 曲线对象点数类型
	 * @return 曲线图数据点列表
	 */
	public List<GeneralDataBean> getGeneralDataBeanArray(Object curveBean, Integer flag) {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		Method[] m = curveBean.getClass().getDeclaredMethods();
		int n = 1;
		int i = 0;
		String [] timeStr = {"00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45",
								"02:00", "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45",
								"04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45",
								"06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45",
								"08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45",
								"10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
								"12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
								"14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45",
								"16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45",
								"18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45",
								"20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45",
								"22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45"};
		String getStr = "";
		if(curveBean instanceof ETotalPowerCurveBean){
			getStr = "P";
		} else if(curveBean instanceof ETotalEnergyCurveBean){
			getStr = "R";
		} else if(curveBean instanceof EMpPowerCurveBean){
			getStr = "P";
		} else if(curveBean instanceof EMpEnergyCurveBean){
			getStr = "E";
		} else if(curveBean instanceof EMpVolCurveBean){
			getStr = "U";
		} else if(curveBean instanceof EMpCurCurveBean){
			getStr = "I";
		} else {
			return list;
		}
		while(i < m.length){
			if(!m[i].getName().startsWith("get"+getStr)){i++; continue;}
			try {
				if(flag == 1){
					GeneralDataBean bean = new GeneralDataBean();
					bean.setTime(timeStr[n-1]);
					bean.setData(new Double(m[i].invoke(curveBean, null)==null?"0.0":m[i].invoke(curveBean, null).toString()));
					list.add(bean);
					i++;
					n++;
					continue;
				}
				if(flag == 2){
					int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
					if((index-1)%2 == 0 ){
						GeneralDataBean bean = new GeneralDataBean();
						bean.setTime(timeStr[n-1]);
						bean.setData(new Double(m[i].invoke(curveBean, null)==null?"0.0":m[i].invoke(curveBean, null).toString()));
						list.add(bean);
					}
					i++;
					n++;
					continue;
				}
				if(flag == 3){
					int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
					if((index-1)%4 == 0 ){
						GeneralDataBean bean = new GeneralDataBean();
						bean.setTime(timeStr[n-1]);
						bean.setData(new Double(m[i].invoke(curveBean, null)==null?"0.0":m[i].invoke(curveBean, null).toString()));
						list.add(bean);
					}
					i++;
					n++;
					continue;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		return list;
	}

	@Override
	public List<SendDataQueryF> findSendDataQueryF(String DateStart,
			String DateEnd, String CONS_NO, String TG_ID,PSysUser pSysUser)
			throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQueryF(DateStart, DateEnd,
					CONS_NO,TG_ID,pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}

	@Override
	public Page<SendDataQueryBFail> findSendDataQueryBFail(String MR_SECT_NO,
			String sendDataQueryFlag, String addr, String sendDQOrgType,
			String sendDQType, String DateStart, String DateEnd,
			String CONS_NO, String TG_ID, int start, int limit,
			PSysUser pSysUser) throws DBAccessException {
		try {
			return sendDataQueryDao.findSendDataQueryBFail(MR_SECT_NO,sendDataQueryFlag, addr, sendDQOrgType, sendDQType, DateStart, DateEnd, CONS_NO, TG_ID, start, limit, pSysUser);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
}
