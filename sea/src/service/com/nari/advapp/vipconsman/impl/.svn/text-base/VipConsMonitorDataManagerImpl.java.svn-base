package com.nari.advapp.vipconsman.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.vipconsman.VipConsMonitorDataBean;
import com.nari.advapp.vipconsman.VipConsStatDataBean;
import com.nari.advapp.vipconsman.VipConsMonitorDataJdbcDao;
import com.nari.advapp.vipconsman.VipConsMonitorDataManager;
import com.nari.advapp.vipconsman.VipConsRealPowerDataBean;
import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpCurCurveBean;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpFactorCurveBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.qrystat.colldataanalyse.EMpReadCurveBean;
import com.nari.qrystat.colldataanalyse.EMpVolCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalPowerCurveBean;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 重点用户监测数据查询业务层实现类
 * @author 姜炜超
 */
public class VipConsMonitorDataManagerImpl implements VipConsMonitorDataManager{
	private VipConsMonitorDataJdbcDao vipConsMonitorDataJdbcDao;//重点户监测数据Dao对象
	
	public void setVipConsMonitorDataJdbcDao(
			VipConsMonitorDataJdbcDao vipConsMonitorDataJdbcDao) {
		this.vipConsMonitorDataJdbcDao = vipConsMonitorDataJdbcDao;
	}

	/**
	 * 根据条件查询重点用户信息
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return Page<VipConsMonitorDataBean>
	 */
	public Page<VipConsMonitorDataBean> findVipConsMonitorData(PSysUser pSysUser, 
			long start, int limit) throws Exception{
		try{
			return vipConsMonitorDataJdbcDao.findVipConsMonitorData(pSysUser, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA);
		}
	}
	
	/**
	 * 查询曲线点数
	 * @return 曲线显示点数
	 */
	private int queryConsPoint(int freezeCycleNum){		
		int point = 0;
		if(96 == freezeCycleNum){
			point = 1;
		}else if(48 == freezeCycleNum){
			point = 2;
		}else if(24 == freezeCycleNum){
			point = 3;
		}else{
			point = 2;
		}
		return point;
	}
	
	/**
	 * 查询时间模板，为生成曲线而用
	 * @param freezeCycleNum
	 * @return List<GeneralDataBean>
	 */
	public List<GeneralDataBean> getTimeModelList(int freezeCycleNum){
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		if(1 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		if(2 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				if((i%2) != 0){
					continue;
				}
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		if(3 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				if((i%4) != 0){
					continue;
				}
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		return list;
	}
	
	/**
	 * 查询测量点功率曲线(冻结数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsStatDataBean>
	 */
	public List<VipConsStatDataBean> findMpStatPowerData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum)throws Exception {
		List<VipConsStatDataBean> retList = new ArrayList<VipConsStatDataBean>();
		Long mpId = 0L;
		try{
			mpId = vipConsMonitorDataJdbcDao.findMpId(consNo, assetNo, 1);//目前只考虑1
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA_MPID);
		}
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点

		for(int i = 1; i <= 8 ; i++){
			EMpPowerCurveBean curveBean = null;
			try{
				curveBean = vipConsMonitorDataJdbcDao.findEMpPowerCurveByDate(mpId, dataDate, i, consNo);
			} catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			} catch (Exception e) {
				throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_LOADSTATCURVE_DATA);
			}
			VipConsStatDataBean bean = generateStatCurveData(curveBean, flagVer1);
			if(1 == i){
			    bean.setCurveName("冻结有功功率");
			}else if(2 == i){
				bean.setCurveName("冻结A相有功功率");
			}else if(3 == i){
				bean.setCurveName("冻结B相有功功率");
			}else if(4 == i){
				bean.setCurveName("冻结C相有功功率");
			}else if(5 == i){
				bean.setCurveName("冻结无功功率");
			}else if(6 == i){
				bean.setCurveName("冻结A相无功功率");
			}else if(7 == i){
				bean.setCurveName("冻结B相无功功率");
			}else if(8 == i){
				bean.setCurveName("冻结C相无功功率");
			}
			retList.add(bean);
		}
		
		return retList;
	}
	
	/**
	 * 处理冻结数据
	 * @param powerBean 
	 * @param flag 
	 * @return VipConsMonitorDataCurveBean
	 */
	private VipConsStatDataBean generateStatCurveData(Object curveBean, int flag){
		//定义返回的bean，保证不为空
		VipConsStatDataBean statBean = new VipConsStatDataBean();
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		//定义时间数组
		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		if(null == curveBean){//无结果的处理
			if(1 == flag){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean dataBean  = new GeneralDataBean();
					dataBean.setFlag(true);
					dataBean.setData(0.0);
					list.add(dataBean);
				}
			}else if(2 == flag){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean dataBean  = new GeneralDataBean();
					dataBean.setFlag(true);
					dataBean.setData(0.0);
					list.add(dataBean);
				}
				
			}else if(3 == flag){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean dataBean  = new GeneralDataBean();
					dataBean.setFlag(true);
					dataBean.setData(0.0);
					list.add(dataBean);
				}
			}			
			statBean.setCurveFlag(true);//如果查询为空，表示漏点了
		}else{//有结果的处理
			String getStr = "";
			if(curveBean instanceof EMpPowerCurveBean){
				getStr = "P";
			} else if(curveBean instanceof EMpEnergyCurveBean){
				getStr = "E";
			} else {
				return statBean;
			}
			Method[] m = curveBean.getClass().getDeclaredMethods();
			int i = 0;
			while(i < m.length){
				if(!m[i].getName().startsWith("get"+getStr)){i++; continue;}
				try {
					if(flag == 1){//96点
						GeneralDataBean dataBean = new GeneralDataBean();
						if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
							dataBean.setFlag(true);
							dataBean.setData(0.0);
						}else{
							dataBean.setFlag(false);
							dataBean.setData(new Double(m[i].invoke(curveBean, null).toString()));
						}
						list.add(dataBean);
						i++;
						continue;
					}
					if(flag == 2){//48点
						int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
						if((index-1)%2 == 0 ){
							GeneralDataBean dataBean = new GeneralDataBean();
							if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
								dataBean.setFlag(true);
								dataBean.setData(0.0);
							}else{
								dataBean.setFlag(false);
								dataBean.setData(new Double(m[i].invoke(curveBean, null).toString()));
							}
							list.add(dataBean);
						}
						i++;
						continue;
					}
					if(flag == 3){//24点
						int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
						if((index-1)%4 == 0 ){
							GeneralDataBean dataBean = new GeneralDataBean();
							if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
								dataBean.setFlag(true);
								dataBean.setData(0.0);
							}else{
								dataBean.setFlag(false);
								dataBean.setData(new Double(m[i].invoke(curveBean, null).toString()));
							}
							list.add(dataBean);
						}
						i++;
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
			statBean.setCurveFlag(false);
		}
		statBean.setCurveList(list);
		
		return statBean;
	}
	
	/**
	 * 查询测量点功率曲线(实时数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsRealPowerDataBean>
	 */
	public List<VipConsRealPowerDataBean> findMpRealPowerData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum)throws Exception {
		List<VipConsRealPowerDataBean> retList = new ArrayList<VipConsRealPowerDataBean>();
		Long mpId = 0L;
		try{
			mpId = vipConsMonitorDataJdbcDao.findMpId(consNo, assetNo, 1);//目前只考虑1
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA_MPID);
		}
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		//定义时间点
        String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
        List<VipConsRealPowerDataBean> list = null;
        try{
        	list = vipConsMonitorDataJdbcDao.findEMpRealPowerCurveByDate(mpId, dataDate, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_LOADREALCURVE_DATA);
		}
		if(null == list){
			list = new ArrayList<VipConsRealPowerDataBean>();
		}
		String a = null;
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dataDate + " " + timeStr[i];
				VipConsRealPowerDataBean tempBean = null;
				for(int j = 0; j < list.size(); j++){
					VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)list.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new VipConsRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setP(bean.getP());
			        	    tempBean.setpA(bean.getpA());
			        	    tempBean.setpB(bean.getpB());
			        	    tempBean.setpC(bean.getpC());
			        	    tempBean.setFlagP(null != bean.getFlagP() ? bean.getFlagP() :false);
				        	tempBean.setFlagPA(null != bean.getFlagPA() ? bean.getFlagPA() :false);
				        	tempBean.setFlagPB(null != bean.getFlagPB() ? bean.getFlagPB() :false);
				        	tempBean.setFlagPC(null != bean.getFlagPC() ? bean.getFlagPC() :false);
				        	tempBean.setQ(bean.getQ());
			        	    tempBean.setqA(bean.getqA());
			        	    tempBean.setqB(bean.getqB());
			        	    tempBean.setqC(bean.getqC());
			        	    tempBean.setFlagQ(null != bean.getFlagQ() ? bean.getFlagQ() :false);
				        	tempBean.setFlagQA(null != bean.getFlagQA() ? bean.getFlagQA() :false);
				        	tempBean.setFlagQB(null != bean.getFlagQB() ? bean.getFlagQB() :false);
				        	tempBean.setFlagQC(null != bean.getFlagQC() ? bean.getFlagQC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new VipConsRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setP(0.0);
		        	tempBean.setpA(0.0);
		        	tempBean.setpB(0.0);
		        	tempBean.setpC(0.0);
		        	tempBean.setFlagP(true);
		        	tempBean.setFlagPA(true);
		        	tempBean.setFlagPB(true);
		        	tempBean.setFlagPC(true);
					tempBean.setQ(0.0);
		        	tempBean.setqA(0.0);
		        	tempBean.setqB(0.0);
		        	tempBean.setqC(0.0);
		        	tempBean.setFlagQ(true);
		        	tempBean.setFlagQA(true);
		        	tempBean.setFlagQB(true);
		        	tempBean.setFlagQC(true);
		        	tempBean.setBeanFlag(true);
				}
				retList.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dataDate + " " + timeStr[i];
				VipConsRealPowerDataBean tempBean = null;
				for(int j = 0; j < list.size(); j++){
					VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)list.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new VipConsRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setP(bean.getP());
			        	    tempBean.setpA(bean.getpA());
			        	    tempBean.setpB(bean.getpB());
			        	    tempBean.setpC(bean.getpC());
			        	    tempBean.setFlagP(null != bean.getFlagP() ? bean.getFlagP() :false);
				        	tempBean.setFlagPA(null != bean.getFlagPA() ? bean.getFlagPA() :false);
				        	tempBean.setFlagPB(null != bean.getFlagPB() ? bean.getFlagPB() :false);
				        	tempBean.setFlagPC(null != bean.getFlagPC() ? bean.getFlagPC() :false);
				        	tempBean.setQ(bean.getQ());
			        	    tempBean.setqA(bean.getqA());
			        	    tempBean.setqB(bean.getqB());
			        	    tempBean.setqC(bean.getqC());
			        	    tempBean.setFlagQ(null != bean.getFlagQ() ? bean.getFlagQ() :false);
				        	tempBean.setFlagQA(null != bean.getFlagQA() ? bean.getFlagQA() :false);
				        	tempBean.setFlagQB(null != bean.getFlagQB() ? bean.getFlagQB() :false);
				        	tempBean.setFlagQC(null != bean.getFlagQC() ? bean.getFlagQC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new VipConsRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setP(0.0);
		        	tempBean.setpA(0.0);
		        	tempBean.setpB(0.0);
		        	tempBean.setpC(0.0);
		        	tempBean.setFlagP(true);
		        	tempBean.setFlagPA(true);
		        	tempBean.setFlagPB(true);
		        	tempBean.setFlagPC(true);
		        	tempBean.setQ(0.0);
		        	tempBean.setqA(0.0);
		        	tempBean.setqB(0.0);
		        	tempBean.setqC(0.0);
		        	tempBean.setFlagQ(true);
		        	tempBean.setFlagQA(true);
		        	tempBean.setFlagQB(true);
		        	tempBean.setFlagQC(true);
		        	tempBean.setBeanFlag(true);
				}
				retList.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dataDate + " " + timeStr[i];
				VipConsRealPowerDataBean tempBean = null;
				for(int j = 0; j < list.size(); j++){
					VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)list.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new VipConsRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setP(bean.getP());
			        	    tempBean.setpA(bean.getpA());
			        	    tempBean.setpB(bean.getpB());
			        	    tempBean.setpC(bean.getpC());
			        	    tempBean.setFlagP(null != bean.getFlagP() ? bean.getFlagP() :false);
				        	tempBean.setFlagPA(null != bean.getFlagPA() ? bean.getFlagPA() :false);
				        	tempBean.setFlagPB(null != bean.getFlagPB() ? bean.getFlagPB() :false);
				        	tempBean.setFlagPC(null != bean.getFlagPC() ? bean.getFlagPC() :false);
				        	tempBean.setQ(bean.getQ());
			        	    tempBean.setqA(bean.getqA());
			        	    tempBean.setqB(bean.getqB());
			        	    tempBean.setqC(bean.getqC());
			        	    tempBean.setFlagQ(null != bean.getFlagQ() ? bean.getFlagQ() :false);
				        	tempBean.setFlagQA(null != bean.getFlagQA() ? bean.getFlagQA() :false);
				        	tempBean.setFlagQB(null != bean.getFlagQB() ? bean.getFlagQB() :false);
				        	tempBean.setFlagQC(null != bean.getFlagQC() ? bean.getFlagQC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new VipConsRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setP(0.0);
		        	tempBean.setpA(0.0);
		        	tempBean.setpB(0.0);
		        	tempBean.setpC(0.0);
		        	tempBean.setFlagP(true);
		        	tempBean.setFlagPA(true);
		        	tempBean.setFlagPB(true);
		        	tempBean.setFlagPC(true);
		        	tempBean.setQ(0.0);
		        	tempBean.setqA(0.0);
		        	tempBean.setqB(0.0);
		        	tempBean.setqC(0.0);
		        	tempBean.setFlagQ(true);
		        	tempBean.setFlagQA(true);
		        	tempBean.setFlagQB(true);
		        	tempBean.setFlagQC(true);
		        	tempBean.setBeanFlag(true);
				}
				retList.add(tempBean);
			}
		}
		return retList;
	}
	
	/**
	 * 查询测量点功率曲线(冻结数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsStatDataBean>
	 */
	public List<VipConsStatDataBean> findMpEnergyData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum) throws Exception{
		List<VipConsStatDataBean> retList = new ArrayList<VipConsStatDataBean>();
		Long mpId = 0L;
		try{
			mpId = vipConsMonitorDataJdbcDao.findMpId(consNo, assetNo, 1);//目前只考虑1
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA_MPID);
		}
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点

		for(int i = 1; i <= 6 ; i++){
			if( 3 == i || 4 == i){
				continue;
			}
			EMpEnergyCurveBean curveBean = null;
			try{
				curveBean = vipConsMonitorDataJdbcDao.findEMpEnergyCurveByDate(mpId, dataDate, i, consNo);
			} catch(DataAccessException e) {
				throw new DBAccessException(BaseException.processDBException(e));
			} catch (Exception e) {
				throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_ENERGYCURVE_DATA);
			}
			VipConsStatDataBean bean = generateStatCurveData(curveBean, flagVer1);
			if(1 == i){
			    bean.setCurveName("正向有功电能量");
			}else if(2 == i){
				bean.setCurveName("正向无功电能量");
			}else if(5 == i){
				bean.setCurveName("反向有功电能量");
			}else if(6 == i){
				bean.setCurveName("反向无功电能量");
			}
			retList.add(bean);
		}
		
		return retList;
	}
	
	/**
	 * 查询表计查询其挂名下的终端的冻结周期
	 * @param assetNo
	 * @return freezeCycleNum
	 */
	public int findFreezeCycleNum(String assetNo) throws Exception{
		try{
			return vipConsMonitorDataJdbcDao.findFreezeCycleNum(assetNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA_FREEZECYCLENUM);
		}
	}
	
	/**
	 * 查询某用户下的表计信息
	 * @param consNo
	 * @return List<EDataMp>
	 */
	public List<EDataMp> findConsAssetInfo(String consNo) throws Exception{
		try{
			return vipConsMonitorDataJdbcDao.findConsAssetInfo(consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.ADE_QRY_VIP_MONITOR_DATA_ASSETNO);
		}
	}
}
