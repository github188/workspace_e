package com.nari.runman.feildman;

import java.util.List;

import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.ITmnlTask;
import com.nari.terminalparam.MeterChgInfoBean;
import com.nari.terminalparam.MeterCommBean;
import com.nari.terminalparam.MeterMaintainInfoBean;
import com.nari.terminalparam.TerminalChgInfoBean;
import com.nari.terminalparam.TerminalDebugInfoBean;
import com.nari.terminalparam.TerminalInstallDetailBean;

/**
 * 终端安装Dao接口
 * @author 姜炜超
 */
public interface TerminalInstallDao {
	/**
	 * 根据供电单位和查询时间，统计终端安装信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	public List<ITmnlTask> findTmnlInstallInfo(String orgNo, String startDate, String endDate, String interType);
	
	/**
	 * 根据供电单位和查询时间，统计终端安装时终端下属用户或电表总数，如果是集抄，查询的是用户信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @param debugStatusCode
	 * @return Long
	 */
	public Long findTmnlUserOrMeterCount(String orgNo, String startDate, String endDate, 
			String interType, String debugStatusCode);
	
	/**
	 * 根据操作员信息查询该操作员可操作下属单位
	 * @param staffNo
	 * @param orgType
	 * @return List<OOrg>
	 */
	public List<OOrg> findSubOrgInfo(String staffNo, String orgType);
	
	/**
	 * 根据供电单位查询该单位相关信息
	 * @param orgNo
	 * @return OOrg
	 */
	public OOrg findOrgInfo(String orgNo);
	
	/**
	 * 根据供电单位，时间等信息查询该单位下所有用户的终端调试信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param consNo
	 * @param debugStatus
	 * @param appNo
	 * @param interType
	 * @return List<TerminalInstallDetailBean>
	 */
	public List<TerminalInstallDetailBean> findConsTmnlDebugInfo(String orgNo, String startDate, 
			String endDate, String consNo, String debugStatus,String appNo, String interType);
	
	/**
	 * 根据申请单号查询终端调试信息
	 * @param appNo
	 * @param consType 用户类别
	 * @param start
	 * @param limit
	 * @return Page<TerminalDebugInfoBean>
	 */
	public Page<TerminalDebugInfoBean>  findTmnlDebugInfo(String appNo, Integer consType, long start, int limit);
	
	/**
	 * 查询规约信息
	 * @return List<VwProtocolCode>
	 */
	public List<VwProtocolCode>  findProtocalCodeInfo();
	
	/**
	 * 更新终端信息
	 * @param tmnlId 
	 * @param protocolCode 
	 * @param sendUpMode 
	 */
	public void saveTmnlInfo(String tmnlId, String protocolCode, String sendUpMode);
	
	/**
	 * 加载终端变更信息
	 * @param appNo
	 * @return List<TerminalChgInfoBean>
	 */
	public List<TerminalChgInfoBean>  findTmnlChgInfo(String appNo);
	
	/**
	 * 加载电能表变更信息
	 * @param appNo
	 * @return List<MeterChgInfoBean>
	 */
	public List<MeterChgInfoBean>  findMeterChgInfo(String appNo);
	
	/**
	 * 加载电能表维护信息
	 * @param appNo
	 * @param succFlag
	 * @return Page<MeterMaintainInfoBean>
	 */
	public Page<MeterMaintainInfoBean>  findMeterMaintainInfo(String appNo, int succFlag, long start, int limit);
	
	/**
	 * 加载电能表通讯协议
	 * @return List<MeterCommBean>
	 */
	public List<MeterCommBean> findMeterCommInfo();
	
	/**
	 * 删除终端调试信息
	 * 
	 * @param tmnlAddr 终端通信的地址码
	 * @param flowNode 流程节点
	 * @return int
	 * @throws Exception
	 * @author ChunMingLi
	 */
	public int delTmnlDebugInfo(String tmnlAddr, String flowNode)throws Exception;
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括新装、拆除，更换，检修)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo(String orgNo, String startDate, String endDate, 
			String interType);
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括终端换表)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo1(String orgNo, String startDate, String endDate, 
			String interType);
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括用户暂停,用户销户)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo2(String orgNo, String startDate, String endDate, 
			String interType);
	/**
	 * 黄轩加，重置状态
	 * @param user
	 * @param appNos
	 */
	void resetState(PSysUser user, String[] appNos);
}
