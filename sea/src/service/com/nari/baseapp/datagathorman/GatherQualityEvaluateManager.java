package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.ATmnlCollQuality;
import com.nari.baseapp.datagatherman.CommMode;
import com.nari.baseapp.datagatherman.ConsType;
import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.baseapp.datagatherman.MissingTerminalDTO;
import com.nari.baseapp.datagatherman.TerminalTypeCode;
import com.nari.baseapp.datagatherman.TmnlModeCode;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

public interface GatherQualityEvaluateManager {

	/**
	 * 查询采集质量
	 * @param orgType 操作员单位类型
	 * @param orgNo 操作员单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 * @throws Exception
	 */
	public List<ATmnlCollQuality> queryGatherQuality(PSysUser pSysUser, String orgNo,
										String startDate,String endDate) throws Exception;
	
	/**
	 * 漏点补招
	 * @param gatherByHandDto 漏点信息
	 * @return
	 * @throws Exception
	 */
	public void recallByHand(GatherByHandDto[] gatherByHandDto) throws Exception;

	/**
	 * 查询漏召信息
	 * @param nodeValue 单位编码/线路Id/变电站Id/用户编号/群组编号(树节点值)
	 * @param nodeType	org/line/sub/usr/cgp、ugp(树节点类型)
	 * @param dataType	数据类型（抄表、负荷、电能质量）
	 * @param startDate	起始时间
	 * @param endDate	结束时间
	 * @param orgType	供电单位级别
	 * @return
	 * @throws Exception
	 */
	public Page<GatherByHandDto> queryUnGatherInfo(PSysUser pSysUser,long start,int limit,String nodeValue,String nodeType,String dataType,String startDate,String endDate,String orgType) throws Exception;
	
	/**
	 * 查询全部用户类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<ConsType> getAllConsType() throws Exception;
	
	/**
	 * 查询全部终端型号
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<TmnlModeCode> getAllTmnlModeCode() throws Exception;
	
	/**
	 * 查询全部终端类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<TerminalTypeCode> getAllTmnlType() throws Exception;
	
	/**
	 * 查询全部通信方式
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<CommMode> getAllCommMode() throws Exception;
	
	/**
	 * chenjg
	 * @param staffNo 工号
	 * @param orgNo
	 * @param consType 用户类型
	 * @param tmnlModeCode 终端型号
	 * @param commMode 通信方式
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 * @throws Exception
	 */
	public List<ATmnlCollQuality> queryGatherQuality(String staffNo,String orgType, String orgNo,String consType,String tmnlModeCode,String commMode,
			String startDate,String endDate) throws Exception;
	
	/**
	 * chenjg
	 * 查询采集失败终端
	 * @param start
	 * @param limit
	 * @param staffNo
	 * @param consType
	 * @param orgNo
	 * @param tmnlModeCode
	 * @param commMode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Page<MissingTerminalDTO> getMissingTerminal(long start,int limit,String staffNo,String consType,String orgNo,String tmnlModeCode,String commMode,String startDate,String endDate) throws Exception;
	
}
