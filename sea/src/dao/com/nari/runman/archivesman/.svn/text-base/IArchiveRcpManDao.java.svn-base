package com.nari.runman.archivesman;

import java.util.List;

import com.nari.cp.REODev;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runman.runstatusman.CMeterDto;
import com.nari.runman.runstatusman.RCollObj;
import com.nari.support.Page;

/**
 * DAO 接口 IArchiveRcpManDao
 * 
 * @author zhangzhw
 * @describe 采集点DAO接口
 */
public interface IArchiveRcpManDao {

	/**
	 * 方法 queryRcpParaByconsId
	 * 
	 * @param consId
	 * @return 通过consId 查询Rcp参数
	 */
	public List<RcpParaJdbc> queryRcpParaByconsId(String consId);

	/**
	 * 方法 queryRcpByconsId
	 * 
	 * @param consId
	 * @return 通过 consId 查询 Rcp
	 */
	public List<RcpRunJdbc> queryRcpByconsId(String consId);

	/**
	 * 方法 saveRcps
	 * 
	 * @param rcpRunJdbc
	 * @param rcpParaJdbc
	 * @return 保存采集点及采集点参数信息
	 */
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc, RcpParaJdbc[] rcpParaJdbc,String consId);
	
	/**
	 * 方法 saveRcps
	 * 
	 * @param rcpRunJdbc
	 * @param rcpParaJdbc
	 * @return 保存采集点及采集点参数信息
	 */
	public boolean saveRcps(RcpRunJdbc[] rcpRunJdbc,String consId);
	
	/**
	 * 删除采集点
	 * @param cpNo 要进行删除的采集点的标识
	 * @return 成功返回1 失败返回0 采集点下存在数据不能进行删除返回-1 采集点不存在返回-2
	 */
	public int deleteRcp(String cpNo);
	
	/**
	 * 根据采集点标识查询此采集点下的所有采集器信息
	 * @param cpno 采集点标识
	 * @return 此采集点下所有采集器List
	 */
	public List<REODev> queryREODev(String cpno);
	
	/**
	 * 保存采集点信息
	 * @param reodev 要进行保存的采集点
	 * @return 成功返回1 失败返回0
	 * @throws Exception
	 */
	public int saveREODev(REODev reodev) throws Exception;
	
	/**
	 * 修改采集器信息
	 * @param reodev
	 * @return
	 */
	public int updateREODev(REODev[] reodev);
	/**
	 * 删除采集器信息
	 * @param collId 要删除的采集器标识
	 * @return 成功返回1 失败返回0
	 * @throws Exception
	 */
	public int deleteREODev(String collId) throws Exception;

	/**
	 * 查询已挂信息
	 * @param cpNo
	 * @return
	 * @throws Exception
	 */
	public Page<CMeterDto> queryCmeterIsHang(String cpNo,long start,int limit) throws Exception;
	
	/**
	 * 查询待挂电能表信息
	 * @param cpNo 采集点编号
	 * @param start 查询起始位置
	 * @param limit 每页显示记录数
	 * @return
	 * @throws Exception
	 */
	public Page<CMeterDto> queryCmeterIsNotHang(String cpNo,long start,int limit) throws Exception;
	
	/**
	 * 保存采集对象信息
	 * @param rco 要进行保存的采集对象
	 * @return 成功:1 失败:0
	 * @throws Exception
	 */
	public String saveCmeterHangInfo(RCollObj rco) throws Exception;
	
	/**
	 * 删除采集对象信息
	 * @param collobjid
	 * @return
	 * @throws Exception
	 */
	public int deleteCmeterHang(String collobjid) throws Exception;
}
