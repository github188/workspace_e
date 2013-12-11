package com.nari.runman.runstatusman;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.ChannelMonitorOrgNoDto;
import com.nari.runman.runstatusman.ChannelMonitorTerminalDto;
import com.nari.support.Page;

/**
 * 
 * 通信信道监测 service
 * 
 * @author ChunMingLi
 * 2010-5-1
 *
 */
public interface ChannelMonitorManager {
	
	//在线状态
	public static final int ON_LINE_FALG = 1;
	//离线状态
	public static final int OFF_LINE_FALG = 0;
	
	/**
	 * 
	 * 终端通信监测 基于供电单位查找
	 * 
	 * @param pSysUser  用户角色
	 * @return 返回终端统计数据集合
	 */
	public Page<ChannelMonitorOrgNoDto> findChannelMonitorOrgNoBean(PSysUser pSysUser,long start,long limit)throws Exception ;
	
	/**
	 * 
	 * 终端通信监测 基于终端厂商查找
	 * 
	 * @return 返回终端统计数据集合
	 */
	public Page<ChannelMonitorTerminalDto > findChannelMonitorFacturerBean(PSysUser pSysUser,long start,long limit)throws Exception ;
	
	/**
	 * 统计查询用户类型
	 * @param pSysUser  操作员信息
	 * @param start  开始页码
	 * @param limit  显示数量
	 * @return  page 返回集合
	 * @throws Exception  
	 */
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorConsType(PSysUser pSysUser,long start,long limit)throws Exception ;
	
	/**
	 * 统计查询采集类型
	 * @param pSysUser  操作员信息
	 * @param start  开始页码
	 * @param limit  显示数量
	 * @return  page 返回集合
	 * @throws Exception  
	 */
	public Page<ChannelMonitorConsTypeCollMode> findChannelMonitorCollectMode(PSysUser pSysUser,long start,long limit)throws Exception;
	
	/**
	 * 
	 * 终端通信监测集合
	 * 
	 * @param onlineType 是否在线
	 * @param statisticsType 统计类型
	 * @param statisticFlag  查询标识
	 * @param start  当前页码
	 * @param limit  每页显示数量
	 * 
	 * @return channelmonitorDto 集合
	 */
	public Page<ChannelMonitorDto> findChannelMonitorBean(String onlineType , String statisticsType, String statisticFlag, long start, int limit, PSysUser pSysUser)throws Exception;
	
	

}
