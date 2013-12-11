package com.nari.runman.runstatusman.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.MainExceptionCategoryBean;
import com.nari.basicdata.OrgNoNameBean;
import com.nari.runman.runstatusman.MainExcepAnalyDao;
import com.nari.runman.runstatusman.MainExcepAnalyManager;
import com.nari.statreport.MainExceptionBean;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;


public class MainExcepAnalyManagerImpl implements MainExcepAnalyManager{

	private MainExcepAnalyDao mainExcepAnalyDao;

	public void setMainExcepAnalyDao(MainExcepAnalyDao mainExcepAnalyDao) {
		this.mainExcepAnalyDao = mainExcepAnalyDao;
	}

	public List<MainExceptionCategoryBean> getMainExceptionList() throws Exception {
		try {
			return this.mainExcepAnalyDao.findMainExceptionList();
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("主站异常类型查询出错");
		}
	}
	
	public Page<MainExceptionBean> findOrgNo(String orgNo,String orgType,String startDate,String endDate,String mainExcepCode,long start,int limit) throws Exception{
		try {
			return this.mainExcepAnalyDao.findOrgNo(orgNo,orgType,startDate,endDate,mainExcepCode,start,limit);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("主站异常类型查询出错");
		}
	}
	public Page<MainExceptionBean> findUsr(String consNo,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception{
		try{
		return this.mainExcepAnalyDao.findUsr(consNo,startDate,endDate,mainExcepCode,start,limit);
		}catch(DataAccessException dbe){
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("主站异常类型查询出错");
		}
	}
	public Page<MainExceptionBean> findLine(String lineId,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception{

		try{
		return this.mainExcepAnalyDao.findLine(lineId,startDate,endDate,mainExcepCode,start,limit);
		}catch(DataAccessException dbe){
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("主站异常类型查询出错");
		}
	
	}
    public Page<MainExceptionBean> findSub(String subsId,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception{

		try{
		return this.mainExcepAnalyDao.findSub(subsId,startDate,endDate,mainExcepCode,start,limit);
		}catch(DataAccessException dbe){
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("主站异常类型查询出错");
		}
	
    }
    public String getOrgName(String orgNo) throws Exception{
    	try{
    		return this.mainExcepAnalyDao.findOrgName(orgNo);
    		}catch(DataAccessException dbe){
    			throw new DBAccessException(BaseException.processDBException(dbe));
    		} catch (Exception e) {
    			throw new ServiceException("主站异常orgName查询出错");
    		}
    }
	
}
