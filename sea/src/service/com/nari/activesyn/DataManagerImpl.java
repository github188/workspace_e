package com.nari.activesyn;

/**
 * 在这个接口中自定义数据处理
 */
public class DataManagerImpl implements DataManager{
	
	private DataDao dataDao;

	/**
	 * 同步2个库
	 * 
	 * @return boolean true or false
	 */
	public boolean synData(String consType,String value) {
		return this.dataDao.synData(consType,value);
	}
	

	public DataDao getDataDao() {
		return dataDao;
	}

	public void setDataDao(DataDao dataDao) {
		this.dataDao = dataDao;
	}
}