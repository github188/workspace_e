package com.nari.runman.archivesman;

import java.util.List;

import com.nari.cp.REODev;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runman.runstatusman.CMeterDto;
import com.nari.runman.runstatusman.RCollObj;
import com.nari.support.Page;

/**
 * 服务接口 iArchiveRcpManManager
 * 
 * @author zhangzhw
 * @describe 档案管理采集点管理接口
 * 
 */
public interface IArchiveRcpManManager {

	/**
	 * 方法 queryRcpParaByconsId
	 * 
	 * @param consId
	 * @return 通过consId 查询Rcp参数
	 */
	public List<RcpParaJdbc> queryRcpParaByConsId(String consId)
			throws Exception;

	/**
	 * 方法 queryRcpByconsId
	 * 
	 * @param consId
	 * @return 通过 consId 查询 Rcp
	 */
	public List<RcpRunJdbc> queryRcpByConsId(String consId) throws Exception;

	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc, RcpParaJdbc[] rcpParaJdbc,String consId)
			throws Exception;
	
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc,String consId) throws Exception;
	
	/**
	 * 删除采集点信息
	 * @param cpNo 要进行删除的采集点标识
	 * @return 成功返回TRUE，失败返回FALSE
	 */
	public boolean deleteRcp(String cpNo) throws Exception;
	
	/**
	 * 根据采集点标识查询此采集点下的所有采集器信息
	 * @param cpno 采集点标识
	 * @return 此采集点下所有采集器List
	 */
	public List<REODev> queryREODev(String cpno) throws Exception;

	/**
	 * 保存采集器
	 * @return
	 * @throws Exception
	 */
	public boolean saveREODev(REODev reodev) throws Exception;
	
	/**
	 * 修改采集器信息
	 * @param reodev
	 * @return
	 */
	public boolean updateREODev(REODev[] reodev);
	/**
	 * 删除采集器信息
	 * @param collId 要删除的采集器标识
	 * @return 成功返回TRUE 失败返回FALSE
	 * @throws Exception
	 */
	public boolean deleteREODev(String collId) throws Exception;
	
	/**
	 * 根据采集点信息查询已挂电能力信息
	 * @param cpNo 采集点编号
	 * @return
	 */
	public Page<CMeterDto> queryCmeterIsHang(String cpNo,long start,int limit) throws Exception;
	
	/**
	 * 根据采集点编号查询待挂电能表信息
	 * @param cpNo
	 * @param start 查询起始位置
	 * @param limit 每页显示记录数
	 * @return
	 * @throws Exception
	 */
	public Page<CMeterDto> queryCmeterIsNotHang(String cpNo,long start,int limit) throws Exception;
	
	/**
	 * 保存采集对象信息
	 * @param rco
	 * @return 成功返回采集对象标识 失败返回为空
	 * @throws Exception
	 */
	public String saveCmeterHangInfo(RCollObj rco) throws Exception;
	
	/**
	 * 删除已挂载的采集对象信息
	 * @param collobjid 采集对象标识
	 * @return
	 * @throws Exception
	 */
	public boolean deleteCmeterHang(String collobjid) throws Exception;
}
