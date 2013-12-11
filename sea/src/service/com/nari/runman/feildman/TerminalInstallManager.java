package com.nari.runman.feildman;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.MeterChgInfoBean;
import com.nari.terminalparam.MeterCommBean;
import com.nari.terminalparam.MeterMaintainInfoBean;
import com.nari.terminalparam.TerminalChgInfoBean;
import com.nari.terminalparam.TerminalDebugInfoBean;
import com.nari.terminalparam.TerminalInstallBean;
import com.nari.terminalparam.TerminalInstallDetailBean;
import com.nari.terminalparam.TerminalRmvAndChgBean;
import com.nari.util.exception.ServiceException;

/**
 * 终端安装业务层接口
 * @author 姜炜超
 */
public interface TerminalInstallManager {
	/**
	 * 根据供电单位和查询时间，统计终端安装信息
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @param interType 接口类别
	 * @return List<TerminalInstallBean>
	 */
    public List<TerminalInstallBean> findTmnlInstallInfo(PSysUser user, String startDate, 
    		String endDate, String interType)  throws Exception;
    
	/**
	 * 根据供电单位，时间等信息查询该单位下所有用户的终端调试信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param consNo
	 * @param debugStatus
	 * @param appNo
	 * @param interType
	 * @return Page<TerminalInstallDetailBean>
	 */
	public List<TerminalInstallDetailBean>  findConsTmnlDebugInfo(String orgNo, String startDate,
			String endDate, String consNo, String debugStatus, String appNo, String interType) throws Exception;
	
	/**
	 * 根据申请单号查询终端调试信息
	 * @param appNo
	 * @param consType
	 * @param start
	 * @param limit
	 * @return Page<TerminalDebugInfoBean>
	 */
	public Page<TerminalDebugInfoBean>  findTmnlDebugInfo(String appNo, Integer consType,long start, int limit) throws Exception;
	
	/**
	 * 查询规约信息
	 * @return List<VwProtocolCode>
	 */
	public List<VwProtocolCode>  findProtocalCodeInfo() throws Exception;
	
	/**
	 * 更新终端信息
	 * @param tmnlId 
	 * @param protocolCode 
	 * @param sendUpMode 
	 * @throws Exception
	 */
	public void saveTmnlInfo(String tmnlId, String protocolCode, String sendUpMode) throws Exception;
	
	/**
	 * 加载终端变更信息
	 * @param appNo
	 * @return List<TerminalChgInfoBean>
	 */
	public List<TerminalChgInfoBean>  findTmnlChgInfo(String appNo) throws Exception;
	
	/**
	 * 加载电能表变更信息
	 * @param appNo
	 * @return List<MeterChgInfoBean>
	 */
	public List<MeterChgInfoBean>  findMeterChgInfo(String appNo) throws Exception;

	/**
	 * 手工触发终端调试
	 * @param appList
	 * @return flag
	 * @throws ServiceException 
	 */
	public int handEvent(List<String> appList) throws ServiceException;
	
	/**
	 * 加载电能表维护信息
	 * @param appNo 申请单号
	 * @param succFlag 是否成功
	 * @return Page<MeterMaintainInfoBean>
	 */
	public Page<MeterMaintainInfoBean>  findMeterMaintainInfo(String appNo, int succFlag, long start, int limit)throws Exception;
	
	/**
	 * 加载电能表通讯协议
	 * @return List<MeterCommBean>
	 */
	public List<MeterCommBean> findMeterCommInfo()throws Exception;
	
	/**
	 * 删除终端调试信息
	 * 
	 * @param tmnlAddr 终端通信的地址码
	 * @return int
	 * @throws Exception
	 */
	public int delTmnlDebugInfo(String tmnlAddr, String flowNode)throws Exception;
	
    /**
	 * 根据供电单位和查询时间，统计终端拆换信息
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @param interType 接口类别
	 * @return List<TerminalRmvAndChgBean>
	 */
    public List<TerminalRmvAndChgBean> findTmnlRmvAndChgInfo(PSysUser user, String startDate, 
    		String endDate, String interType)  throws Exception;
	/**
	 * 黄轩加，重置状态
	 * @param user
	 * @param appNos
	 * @throws ServiceException 
	 */
	void updateResetState(PSysUser user, String[] appNos) throws ServiceException;
}
