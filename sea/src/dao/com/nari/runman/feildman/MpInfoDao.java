package com.nari.runman.feildman;

import java.util.List;

import com.nari.elementsdata.EDataMp;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * @author longkc
 * 
 */
public interface MpInfoDao {
	/**
	 * 
	 * @return
	 */
	public List<EDataMp> findMpInfo(String tmnlAssetNo, short pn);

	/**
	 * 
	 * @return
	 */
	public List<FetchInfoBean> findTmnlMpParam(String proNo, String tmnlAssetNo, short[] pn);
	
	/**
	 * 
	 * @return
	 */
	public long findTmnlMpParamCount(String dataId, String proNo);
	
	/**
	 * 
	 * @return
	 */
	public int deleteTmnlMpParam(String dataId, String proNo);
	
	/**
	 * 
	 * @param tTmnlMpParams
	 *            更新参数List
	 * @return void
	 */
	public int updateMpInfo(List<TTmnlMpParam> tTmnlMpParams) throws DBAccessException;

}
