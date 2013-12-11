package com.nari.sysman.securityman.impl;

import java.util.List;

import com.nari.sysman.securityman.IDictionaryDao;
import com.nari.sysman.securityman.IDictionaryManager;

/**
 * 类 DictionaryManagerImpl
 * 
 * @author zhangzhw
 * 
 */
public class DictionaryManagerImpl implements IDictionaryManager {

	IDictionaryDao iDictionaryDao;

	/**
	 * 取得字典表列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getDictionary(String sql) {
		return iDictionaryDao.getDictionary(sql);
	}

	// getters and setters
	public IDictionaryDao getiDictionaryDao() {
		return iDictionaryDao;
	}

	public void setiDictionaryDao(IDictionaryDao iDictionaryDao) {
		this.iDictionaryDao = iDictionaryDao;
	}

}
