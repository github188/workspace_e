package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.DBAccessException;

/**
 * 终端数据保存或更新接口
 * @author longkc
 *
 */
public interface TmnlDataSaveORUpdateDao {
	/**
	 * 保存、更新终端数据
	 * @param ttmInfo
	 * @throws DBAccessException 
	 */
	public void saveORUpdateTerminalInfo(List<TTmnlParam> ttmInfo) throws DBAccessException;
}
