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
import com.nari.util.exception.DBAccessException;

public interface GatherQualityEvaluateJdbcDao {

	/**
	 * 根据操作员单位编码（orgNO）和指定的时间段查询其下属单位的采集质量情况
	 * 
	 * @param orgType
	 *            操作员单位级别
	 * @param orgNo
	 *            操作员单位编码
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public List<ATmnlCollQuality> findGatherQuality(PSysUser pSysUser,
			String orgType, String orgNo, String startDate, String endDate)
			throws DBAccessException;

	/**
	 * 根据用户查询未召信息
	 * 
	 * @param consNo
	 *            户号
	 * @param startDate
	 *            起始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByUsr(long start, int limit,
			String consNo, String startDate, String endDate, String dataType)
			throws DBAccessException;

	/**
	 * 根据群组查询未召信息
	 * 
	 * @param groupNo
	 *            群组编号
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param groupType
	 *            群组类型
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByGrp(long start, int limit,
			String groupNo, String startDate, String endDate, String groupType,
			String dataType) throws DBAccessException;

	/**
	 * 根据线路或变电站查询未召信息
	 * 
	 * @param nodeValue
	 *            节点值（线路id/变电站id）
	 * @param nodeType
	 *            节点类型（线路/变电站）
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfo(PSysUser pSysUser,
			long start, int limit, String nodeValue, String nodeType,
			String startDate, String endDate, String dataType)
			throws DBAccessException;

	/**
	 * 根据供电所、区县查询未召信息
	 * 
	 * @param orgNo
	 *            单位编码
	 * @param orgType
	 *            单位级别
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 * @throws DBAccessException
	 */
	public Page<GatherByHandDto> findUnGatherInfoByOrg(PSysUser pSysUser,
			long start, int limit, String orgNo, String orgType,
			String startDate, String endDate, String dataType)
			throws DBAccessException;
	
	/**
	 * chenjg
	 * 查询全部用户类型
	 * @return
	 */
	public List<ConsType> findAllConsType();
	
	/**
	 * chenjg
	 * 查询全部终端型号
	 * @return
	 */
	public List<TmnlModeCode> findAllTmnlModeCode();
	
	/**
	 * chenjg
	 * 查询全部终端类型
	 * @return
	 */
	public List<TerminalTypeCode> findAllTmnlType();
	/**
	 * chenjg
	 * 查询全部通信方式
	 * @return
	 */
	public List<CommMode> findAllCommMode();
	
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
			String startDate,String endDate) ;
	
	/**
	 * 查询采集失败终端
	 * chenjg
	 * @param staffNo
	 * @param consType
	 * @param orgNo
	 * @param tmnlModeCode
	 * @param commMode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Page<MissingTerminalDTO> findMissingTermianl(long start,int limit,String staffNo,String consType,String orgNo,String tmnlModeCode,String commMode,String startDate,String endDate);
}
