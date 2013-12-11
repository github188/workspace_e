package com.nari.runman.feildman.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.runman.feildman.TerminalSaveDao;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.DBAccessException;

public class TerminalSaveDaoImpl extends HibernateBaseDaoImpl<TTmnlParam, String> implements TerminalSaveDao{

	@Override
	public void saveTerminalInfo(List<TTmnlParam> tTmnlParams) throws DBAccessException{
		for (TTmnlParam tt :tTmnlParams) {			
			this.saveOrUpdate(tt);
		}		
	}

}
