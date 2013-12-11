package com.nari.runman.feildman;

import java.util.List;
import com.nari.support.Page;
import com.nari.privilige.PSysUser;
import com.nari.terminalparam.CallValueBean;
import com.nari.terminalparam.MpMeterInfoBean;

/**
 * 终端参数DAO接口
 * 
 * @author longkc
 * 
 */
public interface TerminalListDao {
	/**
	 * 
	 * @param terminalNO
	 *            终端资产编号
	 * @param proNo
	 *            规约项编码
	 * @return 终端详细参数
	 */
	public List<FetchInfoBean> FindTerminalList(String proNo, String terminalNO);

	/**
	 * 
	 * @param terminalNO
	 *            终端资产编号
	 * @param proNo
	 *            规约项编码
	 * @return 终端参数删除条数
	 */
	public int deleteTerminalInfo(String proNo, String terminalNO);

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
	 */
	public List<TerminalInfoBean> searchTerminalList(String nodeType,
			String nodeValue, PSysUser userInfo, String protCode);
	
	/**
	 * @param tmnlAssetNoArr
	 *            终端集合
	 * @param userInfo
	 *            用户信息
	 * @param protCode
	 *            规约类型
	 * @return 查询终端list
	 */
	public List<TerminalInfoBean> searchTerminaArrlList(String[] tmnlAssetNoArr, PSysUser userInfo, String protCode);
	
	/**
	 * 查询终端信息
	 * 
	 * @param termAssertNo
	 *            终端资产号
	 * @return 终端信息
	 */
	public List<TerminalInfoBean> searchTerminalInfo(String termAssertNo);
	
	/**
	 * 查询终端表计信息
	 * 
	 * @param termAssertNo 终端资产号
	 * @param mpSn 测量点号
	 * @return
	 */
	public List<MpMeterInfoBean> getTermMeterInfo(String termAssertNo);
	
	/**
	 * 终端数据更新
	 * 
	 * @param fiBean 终端数据
	 */
	public void updateTmnlTermInfo(List<FetchInfoBean> fiBean);
	
	//增加没有测量点的时候保存callValue值	
	public void saveOrUpdateFetchInfoBeanForTP(final List<FetchInfoBean> pubDetailList);
	//增加有测量点的时候保存callValue值	
	public void saveOrUpdateFetchInfoBeanForMP(final List<FetchInfoBean> pubDetailList);
	//查询明细
	public Page<CallValueBean> getCallValueBean(String tmnlAssetNo,String protNO,List blockSn,long start, int limit);
	//查询明细有测量点
	public Page<CallValueBean> getCallValueBeanMpSn(String tmnlAssetNo,String protNO,List mpSn,List blockSn,long start, int limit);
}
