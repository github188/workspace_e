package com.nari.runman.feildman;

import java.util.List;
import java.util.Map;

import com.nari.basicdata.BCommProtItemListId;
import com.nari.basicdata.BCommProtocol;
import com.nari.basicdata.BCommProtocolItem;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.terminalparam.CallValueBean;
import com.nari.terminalparam.MpMeterInfoBean;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.DBAccessException;

/**
 * 终端参数设定业务层接口
 * 
 * @author longkc
 */
public interface TerminalParaSetManager {
	/**
	 * 查询所有终端规约类型，按编码排序。
	 * 
	 * @return 终端规约类型列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<VwProtocolCodeBean> getProCodeList() throws Exception;

	/**
	 * 查询终端规约对应数据类型。
	 * 
	 * @param proCode
	 *            通信规约类型
	 * @param proNO
	 *            规约项编码
	 * @return 终端规约对应数据类型列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<VwDataTypePBean> getDataTypeList(String proCode, String proNO)
			throws Exception;

	/**
	 * 查询终端规约。
	 * 
	 * @param dataType
	 *            数据类型
	 * @param proCode
	 *            通信规约类型
	 * @return 终端规约列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<BCommProtocol> getProtocolList(String dataType, String proCode)
			throws Exception;

	/**
	 * 查询终端规约明细。
	 * 
	 * @param proNO
	 *            规约项编码
	 * @return 终端规约明细列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<BCommProtocolItem> getProtocolItemList(String proNO)
			throws Exception;

	/**
	 * 查询规约项数据编码明细。
	 * 
	 * @param proNO
	 *            规约项数据编码
	 * @return 规约项数据编码明细列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<BCommProtItemListId> getProtlItemListId(String proItemNO)
			throws Exception;

	/**
	 * 查询规约项数据编码明细。
	 * 
	 * @param proNo
	 *            规约项编码
	 * @param terminalNo
	 *            终端资产编号
	 * @return 规约项数据编码明细列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<FetchInfoBean> getTerminalList(String proNo, String terminalNo)
			throws Exception;

	/**
	 * 查询测量点号。
	 * 
	 * @param tmnlAssetNo
	 *            终端资产编号
	 * @param pn
	 *            测量点号
	 * @return 规约项数据编码明细列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<EDataMp> getMpInfoList(String tmnlAssetNo, short pn)
			throws Exception;

	/**
	 * 查询测量点号规约项数据编码明细。
	 * 
	 * @param tmnlAssetNo
	 *            终端资产编号
	 * @param pn
	 *            测量点号
	 * @return 规约项数据编码明细列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<FetchInfoBean> getTmnlMpList(String proNo, String tmnlAssetNo,
			short[] pn) throws Exception;

	/**
	 * 保存终端召测数据。
	 * 
	 * @param ttmInfo
	 *            召测数据
	 * @return void
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public void saveTerminal(List<TTmnlParam> ttmInfo) throws Exception;
	
	/**
	 * 更新终端召测数据状态。
	 * 
	 * @param ttmInfo
	 *            召测数据
	 * @return void
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public void updateTerminal(List<TTmnlParam> ttmInfo) throws Exception;

	/**
	 * 保存终端测量点召测数据。
	 * 
	 * @param mpInfo
	 *            召测数据
	 * @return void
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public void saveMpInfo(List<TTmnlMpParam> mpInfo) throws Exception;

	/**
	 *更新终端测量点召测数据状态。
	 * 
	 * @param mpInfo
	 *            召测数据
	 * @return void
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public void updateMpInfo(List<TTmnlMpParam> mpInfo) throws Exception;
	
	/**
	 * @param nodeType
	 *            节点类型
	 * @param nodeValue
	 *            节点值
	 * @param userInfo
	 *            用户信息
	 * @param protCode
	 *            规约类型
	 * @return 查询终端list
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<TerminalInfoBean> getTermList(String nodeType,
			String nodeValue, PSysUser userInfo, String protCode) throws Exception;
	
	/**
	 * @param tmnlAssetNoArr
	 *            终端集合
	 * @param userInfo
	 *            用户信息
	 * @param protCode
	 *            规约类型
	 * @return 查询终端list
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<TerminalInfoBean> getTmnlArrList(String[] tmnlAssetNoArr, PSysUser userInfo, String protCode) throws Exception;
	/**
	 * 终端表记参数查询
	 * 
	 * @param tmnlAssetNo 终端资产号
	 * @param protocolNo  规约编码
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<FetchInfoBean>> getTermMeterInfo(String tmnlAssetNo, String protocolNo) throws Exception;

	/**
	 * 终端表记信息查询
	 * 
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws Exception
	 */
	public List<MpMeterInfoBean> getTermMeterInfo(String tmnlAssetNo) throws Exception;
	
	/**
	 * 终端信息查询
	 * 
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws Exception
	 */
	public List<TerminalInfoBean> getTermInfo(String tmnlAssetNo) throws Exception;
	
	/**
	 * 终端标记参数更新
	 * @param fiBean 终端表计数据
	 * @throws Exception
	 */
	public void updateMertInfo(List<FetchInfoBean> fiBean) throws Exception;
	
	//增加没有测量点的时候保存callValue值	
	public void saveOrUpdateFetchInfoBeanForTP(final List<FetchInfoBean> pubDetailList);
	//增加有测量点的时候保存callValue值	
	public void saveOrUpdateFetchInfoBeanForMP(final List<FetchInfoBean> pubDetailList);
	//查询明细无测量的情况
	public Page<CallValueBean> getCallValueBean(String tmnlAssetNo, String protNO,List blockSn, long start, int limit) throws DBAccessException;
	//查询明细有测量点
	public Page<CallValueBean> getCallValueBeanMpSn(String tmnlAssetNo, String protNO,
			List mpSn, List blockSn, long start, int limit) throws DBAccessException;

}
