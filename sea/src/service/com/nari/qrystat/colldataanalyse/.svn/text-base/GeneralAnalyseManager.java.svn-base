package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import com.nari.customer.CCons;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 采集数据综合分析服务接口
 * 
 * @author 杨传文
 * @author 姜炜超  modify
 */
public interface GeneralAnalyseManager {
	/**
	 * 查询同行业用户用电信息
	 * @param consNo 用户编码
	 * @param dataDate 查询日期
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 同行业用户用电信息
	 */
	public Page<SameTradeCCons> findSameTradeCConsByConsNo(String consNo, Date dataDate,
			long start, int limit) throws Exception;
	
	/**
	 * 按用户编码查询用户姓名
	 * @param consNo 用户编码
	 * @return CCons
	 */
	public CCons findConsNameByConsNo(String consNo) throws Exception;
	
	/**
	 * 按用户编码查询总加组数据列表
	 * @param consNo 用户编码
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 总加组数据列表
	 */
	public Page<EDataTotalBean> findEDataTotalByConsNo(String consNo,
			long start, int limit) throws Exception;

	/**
	 * 按用户编码查询测量点数据列表
	 * @param consNo  用户编码
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 测量点数据列表
	 */
	public Page<EDataMpBean> findEDataMpByConsNo(String consNo, long start,
			int limit) throws Exception;

	/**
	 * 按用户编码和日期查询日冻结终端统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 日冻结终端统计数据
	 */
	public ETmnlDayStatBean findETmnlDayStatByDate(String consNo, Date dataDate, PSysUser pSysUser)
			throws Exception;

	/**
	 * 按用户编码和日期范围查询日冻结终端统计数据列表
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @param pSysUser 操作员
	 * @return 日冻结终端统计数据列表
	 */
	public Page<ETmnlDayStatBean> findETmnlDayStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit, PSysUser pSysUser)
			throws Exception;

	/**
	 * 按用户编码和日期查询月冻结终端统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 月冻结终端统计数据
	 */
	public ETmnlMonStatBean findETmnlMonStatByDate(String consNo, Date dataDate, PSysUser pSysUser)
			throws Exception;

	/**
	 * 按用户编码和日期范围查询月冻结终端统计数据列表
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo  数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @param pSysUser 操作员
	 * @return 月冻结终端统计数据列表
	 */
	public Page<ETmnlMonStatBean> findETmnlMonStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit, PSysUser pSysUser)
			throws Exception;

	/**
	 * 按用户编码和日期查询日冻结总加组统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 日冻结总加组统计数据
	 */
	public ETotalDayStatBean findETotalDayStatByDate(String consNo,
			Date dataDate) throws Exception;

	/**
	 * 按用户编码和日期范围查询日冻结总加组统计数据列表
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo  数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 日冻结总加组统计数据列表
	 */
	public Page<ETotalDayStatBean> findETotalDayStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit)
			throws Exception;

	/**
	 * 按用户编码和日期查询月冻结总加组统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 月冻结总加组统计数据
	 */
	public ETotalMonStatBean findETotalMonStatByDate(String consNo,
			Date dataDate) throws Exception;

	/**
	 * 按用户编码和日期范围查询月冻结总加组统计数据列表 
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 月冻结总加组统计数据列表
	 */
	public Page<ETotalMonStatBean> findETotalMonStatByDate(String consNo,
			Date dataDateFrom, Date dataDateTo, long start, int limit)
			throws Exception;

	/**
	 * 按用户编号和日期查询日总加组功率曲线
	 * @param totalId 总加组id
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public List<GeneralDataBean> findTotalPowerData(Long totalId,  int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception;

	/**
	 * 按用户编号和日期查询日总加组电能量曲线 
	 * @param totalId 总加组id
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组电能量曲线
	 */
	public List<GeneralDataBean> findTotalEnergyData(Long totalId, Date dataDate, String consNo)
			throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点功率曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点功率曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpPowerData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception;

	/**
	 * 按测量点ID和日期查询日测量点总电能量曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能量曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpEnergyData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo) 
			throws Exception;

	/**
	 * 按用户编号和日期查询日测量点电流曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电流曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpCurData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception;

	/**
	 * 按用户编号和日期查询日测量点电压曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电压曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpVolData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点功率因素曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点功率因素曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpFactorData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception;
	
	/**
	 * 按测量点ID和日期查询日测量点总电能示值曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能示值曲线
	 */
	public List<GeneralDataBean> findMpReadData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
	        throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点实时功率曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点功率曲线
	 * @throws Exception
	 */
	public List<GeneralRealPowerDataBean> findMpRealPowerData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点实时电流曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点电流曲线
	 * @throws Exception
	 */
	public List<GeneralRealCurDataBean> findMpRealCurData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点实时电压曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点电压曲线
	 * @throws Exception
	 */
	public List<GeneralRealVoltDataBean> findMpRealVoltData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception;
	
	/**
	 * 按用户编号和日期查询日测量点实时功率因素曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点功率因素曲线
	 * @throws Exception
	 */
	public List<GeneralRealFactorDataBean> findMpRealFactorData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception;
	
	/**
	 * 按总加组ID和日期查询日总加组实时功率曲线
	 * @param totalId 总加组ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public List<GeneralRealPowerDataBean> findTotalRealPowerData(Long totalId, int freezeCycleNum, 
			Date dataDate, String consNo)throws Exception;
	
	/**
	 * 当选择日期数据点数和对比日期数据点数不同时，返回长度较小的数据列表
	 * @param generalData 选择日期数据列表
	 * @param contrastData 选择日期数据对比日期数据列表
	 * @return 曲线数据列表
	 * @throws Exception
	 */
	public List<GeneralDataBean> getMinLengthList(List<GeneralDataBean> generalData,
			List<GeneralDataBean> contrastData) throws Exception;
	
	/*测量点各项数据统计开始------*/
	/**
	 * 测量点日电容器累计补偿的无功电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayCompBean>
	 */
	public Page<EMpDayCompBean> findEMpDayCompByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)throws Exception;

	/**
	 * 日冻结需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayDemandBean>
	 */
	public Page<EMpDayDemandBean> findEMpDayDemandByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 测量点日冻结电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayEnergyBean>
	 */
	public Page<EMpDayEnergyBean> findEMpDayEnergyByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 测量点日冻结电流越限统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayIstatBean>
	 */
	public Page<EMpDayIstatBean> findEMpDayIstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 日冻结功率因数区段累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayPftimeBean>
	 */
	public Page<EMpDayPftimeBean> findEMpDayPftimeByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 测量点日冻结总及分相有功功率
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayPowerBean>
	 */
	public Page<EMpDayPowerBean> findEMpDayPowerByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;
	
	/**
	 * 测量点日冻结电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayReadBean> 
	 */
	public Page<EMpDayReadBean> findEMpDayReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 日不平衡度越限累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayUnbalanceBean>
	 */
	public Page<EMpDayUnbalanceBean> findEMpDayUnbalanceByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 测量点日冻结电压统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpDayVstatBean>
	 */
	public Page<EMpDayVstatBean> findEMpDayVstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 月冻结最大需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonDemandBean>
	 */
	public Page<EMpMonDemandBean> findEMpMonDemandByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 月冻结电能量
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonEnergyBean>
	 */
	public Page<EMpMonEnergyBean> findEMpMonEnergyByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 月冻结电流越限统计数据
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonIstatBean> 
	 */
	public Page<EMpMonIstatBean> findEMpMonIstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 月冻结功率因数区段累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonPftimeBean>
	 */
	public Page<EMpMonPftimeBean> findEMpMonPftimeByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 月冻结总及分相有功功率数据
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonPowerBean>
	 */
	public Page<EMpMonPowerBean> findEMpMonPowerByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 月冻结电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonReadBean>
	 */
	public Page<EMpMonReadBean> findEMpMonReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 月不平衡度越限累计时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonUnbalanceBean>
	 */
	public Page<EMpMonUnbalanceBean> findEMpMonUnbalanceByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 测量点月冻结电压统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpMonVstatBean> 
	 */
	public Page<EMpMonVstatBean> findEMpMonVstatByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception;

	/**
	 * 抄表日冻结需量及需量发生时间
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpRdayDemandBean>
	 */
	public Page<EMpRdayDemandBean> findEMpRdayDemanByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;

	/**
	 * 测量点抄表日冻结电能示值统计
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpRdayReadBean>
	 */
	public Page<EMpRdayReadBean> findEMpRdayReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;
	
	/**
	 * 当前电能示值
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpReadBean>
	 */
	public Page<EMpReadBean> findEMpRealDataReadByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception ;	
	
	/**
	 * 当前测量点曲线
	 * @param consNo 用户编号
	 * @param dataDateFrom 数据日期起
	 * @param dataDateTo 数据日期止
	 * @param tmnlType 终端类别，默认*
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<EMpCurveBean>
	 */
	public Page<EMpCurveBean> findEMpRealCurveDataByConsNo(String consNo,
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit) 
			throws Exception;
	/*------测量点各项数据统计结束*/
	
	/**
	 * 查询全部普通群组列表
	 * @return 群组名称和群组编号对象列表
	 */
	public List<GroupNameBean> findGroupName() throws Exception;

	/**
	 * 保存群组明细
	 * @param groupNo
	 * @param consNo
	 * @return
	 */
	public void addToGroup(String groupNo, String consNo)  throws Exception;
	
	/**
	 * 查询时间模板，为生成曲线而用
	 * @param freezeCycleNum
	 * @return List<GeneralDataBean>
	 */
	public List<GeneralDataBean> getTimeModelList(int freezeCycleNum) throws Exception;
	
	
	/**
	 * 查询某用户下的终端信息
	 * @param consNo
	 * @return List<EMpConsTmnlBean>
	 */
	public List<EMpConsTmnlBean> queryConsTmnlInfo(String consNo) throws Exception;
}
