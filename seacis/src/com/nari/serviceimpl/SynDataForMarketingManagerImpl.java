package com.nari.serviceimpl;

import java.util.Date;

import com.nari.dao.jdbc.SynDataForMarketingDao;
import com.nari.service.SynDataForMarketingManager;

public class SynDataForMarketingManagerImpl implements SynDataForMarketingManager{
	
	private SynDataForMarketingDao synDataForMarketingDao;

	public void setSynDataForMarketingDao(SynDataForMarketingDao synDataForMarketingDao){
		this.synDataForMarketingDao = synDataForMarketingDao;
	}

	public boolean synData(String tableName) {
		return this.synDataForMarketingDao.synData(tableName);
	}

	public boolean synDataByService(String cons_no,String cons_id,String meter_id, String tmnl_id, String tgId, String appNo){
		return this.synDataForMarketingDao.synDataByService(cons_no, cons_id, meter_id, tmnl_id, tgId, appNo);
	}
	
	public boolean synOtherDataByService(String tmnlId, String cpNo, String tgId, String appNo) {
		return this.synDataForMarketingDao.synOtherDataByService(tmnlId,cpNo,tgId, appNo);
	}
	
	public boolean synDataCollect(Date sDate){
		return this.synDataForMarketingDao.synDataCollect(sDate);
	}

	@Override
	public boolean synDueDataByService() {
	
		return this.synDataForMarketingDao.synDueDataByService();
	}

	@Override
	public boolean synPayDataByService(String userID) {
		return this.synDataForMarketingDao.synPayDataByService(userID);
	}
}