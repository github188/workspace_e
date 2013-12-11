package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.util.exception.DBAccessException;

/**
 * 方案类别service
 * @author 姜海辉
 *
 */

public interface IVwCtrlSchemeTypeManager {

	public List<VwCtrlSchemeType>  queryCtrlSchemeType()throws DBAccessException;
}
