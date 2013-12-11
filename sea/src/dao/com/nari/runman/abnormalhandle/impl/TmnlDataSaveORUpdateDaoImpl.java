package com.nari.runman.abnormalhandle.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.runman.abnormalhandle.TmnlDataSaveORUpdateDao;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.DBAccessException;

public class TmnlDataSaveORUpdateDaoImpl  extends HibernateBaseDaoImpl<TTmnlParam, String> implements TmnlDataSaveORUpdateDao{

	@Override
	public void saveORUpdateTerminalInfo(List<TTmnlParam> ttmInfo) throws DBAccessException {
		for (TTmnlParam tt :ttmInfo) {			
			this.saveOrUpdate(tt);
		}		
	}

}
