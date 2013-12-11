package com.nari.runman.runstatusman;

import java.util.Date;
import java.util.List;

import com.nari.basicdata.MainExceptionCategoryBean;
import com.nari.basicdata.OrgNoNameBean;
import com.nari.statreport.MainExceptionBean;
import com.nari.support.Page;

public interface MainExcepAnalyManager {
	public List<MainExceptionCategoryBean> getMainExceptionList() throws Exception;
	public Page<MainExceptionBean> findOrgNo(String orgNo,String orgType,String startDate,String endDate,String mainExcepCode,long start,int limit) throws Exception;
    public Page<MainExceptionBean> findUsr(String consNo,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception;
    public Page<MainExceptionBean> findLine(String lineId,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception;
    public Page<MainExceptionBean> findSub(String subsId,String startDate,String endDate,String mainExcepCode,long start,int limit)throws Exception;
    public String getOrgName(String orgNo) throws Exception;
}
