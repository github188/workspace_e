package com.nari.runman.feildman.impl;

import java.util.List;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.runman.feildman.MpInfoSaveDao;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.util.exception.DBAccessException;

public class MpInfoSaveDaoImpl extends
		HibernateBaseDaoImpl<TTmnlMpParam, String> implements MpInfoSaveDao {

	@Override
	public void saveMpInfo(List<TTmnlMpParam> tTmnlMpParams)
			throws DBAccessException {
		for (TTmnlMpParam tt : tTmnlMpParams) {
			this.saveOrUpdate(tt);
		}
	}

	@Override
	public void deletMpInfo(List<TTmnlMpParam> tTmnlMpParams)
			throws DBAccessException {
		for (TTmnlMpParam tt : tTmnlMpParams) {
			this.delete(tt);
		}

	}
}
