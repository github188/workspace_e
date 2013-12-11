package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;


import com.nari.basicdata.MainExceptionCategoryBean;
import com.nari.basicdata.OrgNoNameBean;
import com.nari.support.Page;
import com.nari.statreport.MainExceptionBean;
import com.nari.util.exception.DBAccessException;

public interface MainExcepAnalyDao {
	public  List<MainExceptionCategoryBean> findMainExceptionList() throws DBAccessException;
	public Page<MainExceptionBean> findOrgNo(String orgNo,String orgType,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException;
    public Page<MainExceptionBean> findUsr(String consNo,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException;
    public Page<MainExceptionBean> findLine(String lineId,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException;
    public Page<MainExceptionBean> findSub(String subsId,String startDate,String endDate,String mainExcepCode,long start,int limit) throws DBAccessException;
    public String findOrgName(String orgNo) throws DBAccessException; 
}
