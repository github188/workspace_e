package com.nari.qrystat.colldataanalyse.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.customer.CCons;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EDataMpBean;
import com.nari.qrystat.colldataanalyse.EDataTotalBean;
import com.nari.qrystat.colldataanalyse.EMpConsTmnlBean;
import com.nari.qrystat.colldataanalyse.EMpCurCurveBean;
import com.nari.qrystat.colldataanalyse.EMpCurveBean;
import com.nari.qrystat.colldataanalyse.EMpDayCompBean;
import com.nari.qrystat.colldataanalyse.EMpDayDemandBean;
import com.nari.qrystat.colldataanalyse.EMpDayEnergyBean;
import com.nari.qrystat.colldataanalyse.EMpDayIstatBean;
import com.nari.qrystat.colldataanalyse.EMpDayPftimeBean;
import com.nari.qrystat.colldataanalyse.EMpDayPowerBean;
import com.nari.qrystat.colldataanalyse.EMpDayReadBean;
import com.nari.qrystat.colldataanalyse.EMpDayUnbalanceBean;
import com.nari.qrystat.colldataanalyse.EMpDayVstatBean;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.EMpFactorCurveBean;
import com.nari.qrystat.colldataanalyse.EMpMonDemandBean;
import com.nari.qrystat.colldataanalyse.EMpMonEnergyBean;
import com.nari.qrystat.colldataanalyse.EMpMonIstatBean;
import com.nari.qrystat.colldataanalyse.EMpMonPftimeBean;
import com.nari.qrystat.colldataanalyse.EMpMonPowerBean;
import com.nari.qrystat.colldataanalyse.EMpMonReadBean;
import com.nari.qrystat.colldataanalyse.EMpMonUnbalanceBean;
import com.nari.qrystat.colldataanalyse.EMpMonVstatBean;
import com.nari.qrystat.colldataanalyse.EMpPowerCurveBean;
import com.nari.qrystat.colldataanalyse.EMpRdayDemandBean;
import com.nari.qrystat.colldataanalyse.EMpRdayReadBean;
import com.nari.qrystat.colldataanalyse.EMpReadBean;
import com.nari.qrystat.colldataanalyse.EMpReadCurveBean;
import com.nari.qrystat.colldataanalyse.EMpVolCurveBean;
import com.nari.qrystat.colldataanalyse.ETmnlDayStatBean;
import com.nari.qrystat.colldataanalyse.ETmnlMonStatBean;
import com.nari.qrystat.colldataanalyse.ETotalDayStatBean;
import com.nari.qrystat.colldataanalyse.ETotalEnergyCurveBean;
import com.nari.qrystat.colldataanalyse.ETotalMonStatBean;
import com.nari.qrystat.colldataanalyse.ETotalPowerCurveBean;
import com.nari.qrystat.colldataanalyse.GeneralAnalyseDao;
import com.nari.qrystat.colldataanalyse.GeneralAnalyseManager;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealCurDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealFactorDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealPowerDataBean;
import com.nari.qrystat.colldataanalyse.GeneralRealVoltDataBean;
import com.nari.qrystat.colldataanalyse.GroupNameBean;
import com.nari.qrystat.colldataanalyse.SameTradeCCons;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 采集数据综合分析服务实现类
 * 
 * @author 杨传文
 * @author 姜炜超  modify
 */
public class GeneralAnalyseManagerImpl implements GeneralAnalyseManager {
	private GeneralAnalyseDao generalAnalyseDao;//采集数据综合分析Dao对象
	
	public void setGeneralAnalyseDao(GeneralAnalyseDao generalAnalyseDao) {
		this.generalAnalyseDao = generalAnalyseDao;
	}

	/**
	 * 按用户编码查询用户姓名
	 * @param consNo 用户编码
	 * @return 用户姓名
	 */
	public CCons findConsNameByConsNo(String consNo) throws Exception {
		try {
			return this.generalAnalyseDao.findConsNameByConsNo(consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CONSINFO);
		}
	}

	/**
	 * 查询同行业用户用电信息
	 * @param consNo 用户编码
	 * @param dataDate 查询日期
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 同行业用户用电信息
	 */
	public Page<SameTradeCCons> findSameTradeCConsByConsNo(String consNo, Date dataDate,
			long start, int limit) throws Exception {
		try {
			return this.generalAnalyseDao.findSameTradeCConsByConsNo(consNo, dataDate, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_SAMETRADECCONSINFO);
		}
	}

	/**
	 * 按用户编码查询测量点数据列表
	 * @param consNo  用户编码
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 测量点数据列表
	 */
	public Page<EDataMpBean> findEDataMpByConsNo(String consNo, long start,
			int limit) throws Exception {
		try {
			return this.generalAnalyseDao.findEDataMpByConsNo(consNo, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPINFO);
		}
	}

	/**
	 * 按用户编码查询总加组数据列表
	 * @param consNo 用户编码
	 * @param start 分页参数
	 * @param limit 每页记录条数
	 * @return 总加组数据列表
	 */
	public Page<EDataTotalBean> findEDataTotalByConsNo(String consNo,
			long start, int limit) throws Exception {
		try {
			return this.generalAnalyseDao.findEDataTotalByConsNo(consNo, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findETmnlDayStatByDate(consNo, dataDateFrom, dataDateTo, start, limit, pSysUser);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TMNLDAYSTATINFO);
		}
	}

	/**
	 * 按用户编码和日期查询日冻结终端统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 日冻结终端统计数据
	 */
	public ETmnlDayStatBean findETmnlDayStatByDate(String consNo, Date dataDate, PSysUser pSysUser)
			throws Exception {
		try {
			return this.generalAnalyseDao.findETmnlDayStatByDate(consNo, dataDate, pSysUser);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TMNLDAYSTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findETmnlMonStatByDate(consNo, dataDateFrom, dataDateTo, start, limit, pSysUser);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TMNLMONSTATINFO);
		}
	}

	/**
	 * 按用户编码和日期查询月冻结终端统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @param pSysUser 操作员
	 * @return 月冻结终端统计数据
	 */
	public ETmnlMonStatBean findETmnlMonStatByDate(String consNo, Date dataDate, PSysUser pSysUser)
			throws Exception {
		try {
			return this.generalAnalyseDao.findETmnlMonStatByDate(consNo, dataDate, pSysUser);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TMNLMONSTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findETotalDayStatByDate(consNo, dataDateFrom, dataDateTo, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALDAYSTATINFO);
		}
	}

	/**
	 * 按用户编码和日期查询日冻结总加组统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 日冻结总加组统计数据
	 */
	public ETotalDayStatBean findETotalDayStatByDate(String consNo,
			Date dataDate) throws Exception {
		try {
			return this.generalAnalyseDao.findETotalDayStatByDate(consNo, dataDate);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALDAYSTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findETotalMonStatByDate(consNo, dataDateFrom, dataDateTo, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALMONSTATINFO);
		}
	}

	/**
	 * 按用户编码和日期查询月冻结总加组统计数据，点击查询调用
	 * @param consNo 用户编码
	 * @param dataDate 数据日期
	 * @return 月冻结总加组统计数据
	 */
	public ETotalMonStatBean findETotalMonStatByDate(String consNo,
			Date dataDate) throws Exception {
		try {
			return this.generalAnalyseDao.findETotalMonStatByDate(consNo, dataDate);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALMONSTATINFO);
		}
	}

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
			Date dataDateFrom, Date dataDateTo, String tmnlType, long start, int limit)
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayCompByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYCOMPINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayDemandByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYDEMANDINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayEnergyByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYENERGYINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayIstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYISTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayPftimeByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYPFTIMEINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayPowerByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYPOWERINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYREADINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayUnbalanceByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYUNBALANCEINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpDayVstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPDAYVSTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonDemandByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONDEMANDINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonEnergyByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONENERGYINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonIstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONISTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonPftimeByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONPFTIMEINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonPowerByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONPOWERINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONREADINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonUnbalanceByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONUNBALANCEINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpMonVstatByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPMONVSTATINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpRdayDemanByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPRDAYDEMANDINFO);
		}
	}

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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpRdayReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPRDAYREADINFO);
		}
	}
	
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
			throws Exception {
		try {
			return this.generalAnalyseDao.findEMpRealDataReadByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPREALDATAREADINFO);
		}
	}
	
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
			throws Exception{
		try {
			return this.generalAnalyseDao.findEMpRealCurveDataByConsNo(consNo, dataDateFrom, dataDateTo, tmnlType, start, limit);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPREALCURVEDATAINFO);
		}
	}

	/**
	 * 按用户编号和日期查询日总加组功率曲线
	 * @param totalId 总加组id
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public List<GeneralDataBean> findTotalPowerData(Long totalId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception {
		
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		ETotalPowerCurveBean totalPower = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
		    totalPower = generalAnalyseDao.findETotalPowerCurveByDate(totalId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALPOWERDATAINFO);
		}
		
		if(totalPower == null){			
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		
		Integer flag = totalPower.getDataPointFlag();
		List<GeneralDataBean> retList = null;
		
		try {
			retList = getGeneralDataBeanArray(totalPower, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日总加组电能量曲线 ,no use
	 * @param totalId 总加组id
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组电能量曲线
	 */
	public List<GeneralDataBean> findTotalEnergyData(Long totalId, Date dataDate, String consNo)
			throws Exception {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		ETotalEnergyCurveBean totalEnergy = null;
		
		try {
			totalEnergy = generalAnalyseDao.findETotalEnergyCurveByDate(totalId, dataDate, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TOTALENERGYDATAINFO);
		}
		
		if(totalEnergy == null){
			return list;
		}
		Integer flag = totalEnergy.getDataPointFlag();
		
        List<GeneralDataBean> retList = null;
		
		try {
			retList = getGeneralDataBeanArray(totalEnergy, flag, 1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日测量点功率曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点功率曲线
	 * @throws DBAccessException
	 */
	public List<GeneralDataBean> findMpPowerData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpPowerCurveBean mpPower = null;

		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpPower = generalAnalyseDao.findEMpPowerCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPPOWERDATAINFO);
		}
		
		if(mpPower == null){
			
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		
		Integer flag = mpPower.getDataPointFlag();
		
        List<GeneralDataBean> retList = null;
		
		try {
			retList = getGeneralDataBeanArray(mpPower, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
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
			throws Exception {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpEnergyCurveBean mpEnergy = null;
		
        int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpEnergy = generalAnalyseDao.findEMpEnergyCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPCURDATAINFO);
		}
		
		if(mpEnergy == null){
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		
		Integer flag = mpEnergy.getDataPointFlag();
		
		List<GeneralDataBean> retList = null;
			
		try {
			retList = getGeneralDataBeanArray(mpEnergy, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPENERGYDATAINFO);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日测量点电流曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点电流曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpCurData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpCurCurveBean mpCur = null;

		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpCur = generalAnalyseDao.findEMpCurCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPCURDATAINFO);
		}
		
		if(mpCur == null){
			
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		Integer flag = mpCur.getDataPointFlag();
		
		List<GeneralDataBean> retList = null;
			
		try {
			retList = getGeneralDataBeanArray(mpCur, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日测量点电压曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点电压曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpVolData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception {
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpVolCurveBean mpVol = null;

		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpVol = generalAnalyseDao.findEMpVolCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPVOLTDATAINFO);
		}
		
		if(mpVol == null){
			
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		Integer flag = mpVol.getDataPointFlag();
		
		List<GeneralDataBean> retList = null;
		
		try {
			retList = getGeneralDataBeanArray(mpVol, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日测量点功率因素曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点功率因素曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpFactorData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
			throws Exception{
        List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpFactorCurveBean mpFactor = null;

		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpFactor = generalAnalyseDao.findEMpFactorCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPFACTORDATAINFO);
		}
		
		if(mpFactor == null){
			
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		Integer flag = mpFactor.getDataPointFlag();
		
        List<GeneralDataBean> retList = null;
		
		try {
			retList = getGeneralDataBeanArray(mpFactor, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURO2CURD);
		}
		return retList;
	}
	
	/**
	 * 按测量点ID和日期查询日测量点总电能示值曲线
	 * @param mpId 测量点ID
	 * @param dataDate 选择日期
	 * @param dataType 数据类型
	 * @param consNo 客户编号
	 * @return 日测量点电能示值曲线
	 * @throws Exception
	 */
	public List<GeneralDataBean> findMpReadData(Long dataId, int freezeCycleNum, Date dataDate, int dataType, String consNo)
	        throws Exception{
        List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		
		EMpReadCurveBean mpRead = null;
		
        int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		try {
			mpRead = generalAnalyseDao.findEMpReadCurveByDate(dataId, dataDate, dataType, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPREADDATAINFO);
		}
		
		if(mpRead == null){
			String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
					"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
					"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
					"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
					"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
					"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
					"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
					"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
					"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
					"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
					"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
					"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
			
			if(1 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}else if(2 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%2) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
				
			}else if(3 == flagVer1){
				for(int i = 0; i < timeStr.length; i++){
					if( (i%4) != 0){
						continue;
					}
					GeneralDataBean bean  = new GeneralDataBean();
					bean.setTime(timeStr[i]);
					bean.setFlag(true);
					bean.setData(0.0);
					list.add(bean);
				}
			}			
			return list;
		}
		
		Integer flag = mpRead.getDataPointFlag();
		
		List<GeneralDataBean> retList = null;
			
		try {
			retList = getGeneralDataBeanArray(mpRead, flag, flagVer1);
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPENERGYDATAINFO);
		}
		return retList;
	}
	
	/**
	 * 按用户编号和日期查询日测量点实时功率曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点实时功率曲线
	 * @throws Exception
	 */
	public List<GeneralRealPowerDataBean> findMpRealPowerData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception{
		//定义返回结果列表
        List<GeneralRealPowerDataBean> list = new ArrayList<GeneralRealPowerDataBean>();
		
        //定义查询结果列表
        List<GeneralRealPowerDataBean> retList = null;
        
        try {
        	retList = generalAnalyseDao.findEMpRealPowerCurveByDate(dataId, dataDate, consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPREALPOWERDATAINFO);
		}
		
        //定义时间点
        String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		String dateS = DateUtil.dateToString(dataDate);
		String a = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		//如果没有数据,执行如下流程，创建对象，保证前台永远可以显示
		if(null == retList){
			retList = new ArrayList<GeneralRealPowerDataBean>();
		}
		
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
			        	    tempBean.setPowerA(bean.getPowerA());
			        	    tempBean.setPowerB(bean.getPowerB());
			        	    tempBean.setPowerC(bean.getPowerC());
			        	    tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
		        	tempBean.setPowerA(0.0);
		        	tempBean.setPowerB(0.0);
		        	tempBean.setPowerC(0.0);
		        	tempBean.setFlag(true);
		        	tempBean.setFlagA(true);
		        	tempBean.setFlagB(true);
		        	tempBean.setFlagC(true);
		        	tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
			        	    tempBean.setPowerA(bean.getPowerA());
			        	    tempBean.setPowerB(bean.getPowerB());
			        	    tempBean.setPowerC(bean.getPowerC());
			        	    tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
		        	tempBean.setPowerA(0.0);
		        	tempBean.setPowerB(0.0);
		        	tempBean.setPowerC(0.0);
		        	tempBean.setFlag(true);
		        	tempBean.setFlagA(true);
		        	tempBean.setFlagB(true);
		        	tempBean.setFlagC(true);
		        	tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
			        	    tempBean.setPowerA(bean.getPowerA());
			        	    tempBean.setPowerB(bean.getPowerB());
			        	    tempBean.setPowerC(bean.getPowerC());
			        	    tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
				        	tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
		        	tempBean.setPowerA(0.0);
		        	tempBean.setPowerB(0.0);
		        	tempBean.setPowerC(0.0);
		        	tempBean.setFlag(true);
		        	tempBean.setFlagA(true);
		        	tempBean.setFlagB(true);
		        	tempBean.setFlagC(true);
		        	tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		
		return list;
	}
	
	/**
	 * 按用户编号和日期查询日测量点实时电流曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点实时电流曲线
	 * @throws Exception
	 */
	public List<GeneralRealCurDataBean> findMpRealCurData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception{
        List<GeneralRealCurDataBean> list = new ArrayList<GeneralRealCurDataBean>();
		
        List<GeneralRealCurDataBean> retList = generalAnalyseDao.findEMpRealCurCurveByDate(dataId, dataDate, consNo);

		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		String dateS = DateUtil.dateToString(dataDate);
		String a = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		//如果没有数据,执行如下流程
		if(null == retList){
			retList = new ArrayList<GeneralRealCurDataBean>();
		}
		
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dateS + " " + timeStr[i];
				GeneralRealCurDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealCurDataBean bean = (GeneralRealCurDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealCurDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setCur0(bean.getCur0());
			        	    tempBean.setCurA(bean.getCurA());
			        	    tempBean.setCurB(bean.getCurB());
			        	    tempBean.setCurC(bean.getCurC());
			        	    tempBean.setFlag0(null != bean.getFlag0() ? bean.getFlag0() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealCurDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
		        	tempBean.setCur0(0.0);
		        	tempBean.setCurA(0.0);
		        	tempBean.setCurB(0.0);
		        	tempBean.setCurC(0.0);
		        	tempBean.setFlag0(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealCurDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealCurDataBean bean = (GeneralRealCurDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealCurDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setCur0(bean.getCur0());
			        	    tempBean.setCurA(bean.getCurA());
			        	    tempBean.setCurB(bean.getCurB());
			        	    tempBean.setCurC(bean.getCurC());
			        	    tempBean.setFlag0(null != bean.getFlag0() ? bean.getFlag0() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealCurDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
		        	tempBean.setCur0(0.0);
		        	tempBean.setCurA(0.0);
		        	tempBean.setCurB(0.0);
		        	tempBean.setCurC(0.0);
		        	tempBean.setFlag0(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealCurDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealCurDataBean bean = (GeneralRealCurDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealCurDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setCur0(bean.getCur0());
			        	    tempBean.setCurA(bean.getCurA());
			        	    tempBean.setCurB(bean.getCurB());
			        	    tempBean.setCurC(bean.getCurC());
			        	    tempBean.setFlag0(null != bean.getFlag0() ? bean.getFlag0() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealCurDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
		        	tempBean.setCur0(0.0);
		        	tempBean.setCurA(0.0);
		        	tempBean.setCurB(0.0);
		        	tempBean.setCurC(0.0);
		        	tempBean.setFlag0(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		
		return list;
	}
	
	/**
	 * 按用户编号和日期查询日测量点实时电压曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点实时电压曲线
	 * @throws Exception
	 */
	public List<GeneralRealVoltDataBean> findMpRealVoltData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception{
		
        List<GeneralRealVoltDataBean> list = new ArrayList<GeneralRealVoltDataBean>();
		
        List<GeneralRealVoltDataBean> retList = generalAnalyseDao.findEMpRealVoltCurveByDate(dataId, dataDate, consNo);

		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		String dateS = DateUtil.dateToString(dataDate);
		String a = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		//如果没有数据,执行如下流程
		if(null == retList){
			retList = new ArrayList<GeneralRealVoltDataBean>();
		}
		
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dateS + " " + timeStr[i];
				GeneralRealVoltDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealVoltDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setVoltA(bean.getVoltA());
			        	    tempBean.setVoltB(bean.getVoltB());
			        	    tempBean.setVoltC(bean.getVoltC());
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealVoltDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setVoltA(0.0);
		        	tempBean.setVoltB(0.0);
		        	tempBean.setVoltC(0.0);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealVoltDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealVoltDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setVoltA(bean.getVoltA());
			        	    tempBean.setVoltB(bean.getVoltB());
			        	    tempBean.setVoltC(bean.getVoltC());
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealVoltDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setVoltA(0.0);
		        	tempBean.setVoltB(0.0);
		        	tempBean.setVoltC(0.0);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealVoltDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealVoltDataBean bean = (GeneralRealVoltDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealVoltDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setVoltA(bean.getVoltA());
			        	    tempBean.setVoltB(bean.getVoltB());
			        	    tempBean.setVoltC(bean.getVoltC());
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealVoltDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setVoltA(0.0);
		        	tempBean.setVoltB(0.0);
		        	tempBean.setVoltC(0.0);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		
		return list;
	}
	
	/**
	 * 按用户编号和日期查询日测量点实时功率因素曲线
	 * @param dataId 测量点ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 数据日期
	 * @param consNo 客户编号
	 * @return 日测量点实时功率因素曲线
	 * @throws Exception
	 */
	public List<GeneralRealFactorDataBean> findMpRealFactorData(Long dataId, int freezeCycleNum, Date dataDate, String consNo)
			throws Exception{
				
        List<GeneralRealFactorDataBean> list = new ArrayList<GeneralRealFactorDataBean>();
		
        List<GeneralRealFactorDataBean> retList = generalAnalyseDao.findEMpRealFactorCurveByDate(dataId, dataDate, consNo);

		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		String dateS = DateUtil.dateToString(dataDate);
		String a = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		//如果没有数据,执行如下流程
		if(null == retList){
			retList = new ArrayList<GeneralRealFactorDataBean>();
		}
		
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dateS + " " + timeStr[i];
				GeneralRealFactorDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealFactorDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setFactor(bean.getFactor());
			        	    tempBean.setFactorA(bean.getFactorA());
			        	    tempBean.setFactorB(bean.getFactorB());
			        	    tempBean.setFactorC(bean.getFactorC());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealFactorDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setFactor(0.0);
		        	tempBean.setFactorA(0.0);
		        	tempBean.setFactorB(0.0);
		        	tempBean.setFactorC(0.0);
	        	    tempBean.setFlag(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealFactorDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealFactorDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setFactor(bean.getFactor());
			        	    tempBean.setFactorA(bean.getFactorA());
			        	    tempBean.setFactorB(bean.getFactorB());
			        	    tempBean.setFactorC(bean.getFactorC());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealFactorDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setFactor(0.0);
		        	tempBean.setFactorA(0.0);
		        	tempBean.setFactorB(0.0);
		        	tempBean.setFactorC(0.0);
	        	    tempBean.setFlag(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealFactorDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealFactorDataBean bean = (GeneralRealFactorDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealFactorDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setFactor(bean.getFactor());
			        	    tempBean.setFactorA(bean.getFactorA());
			        	    tempBean.setFactorB(bean.getFactorB());
			        	    tempBean.setFactorC(bean.getFactorC());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
				        	tempBean.setFlagA(null != bean.getFlagA() ? bean.getFlagA() :false);
				        	tempBean.setFlagB(null != bean.getFlagB() ? bean.getFlagB() :false);
				        	tempBean.setFlagC(null != bean.getFlagC() ? bean.getFlagC() :false);
			        	    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealFactorDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setFactor(0.0);
		        	tempBean.setFactorA(0.0);
		        	tempBean.setFactorB(0.0);
		        	tempBean.setFactorC(0.0);
	        	    tempBean.setFlag(true);
	        	    tempBean.setFlagA(true);
	        	    tempBean.setFlagB(true);
	        	    tempBean.setFlagC(true);
	        	    tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		
		return list;
	}
	
	/**
	 * 按总加组ID和日期查询日总加组实时功率曲线
	 * @param totalId 总加组ID
	 * @param freezeCycleNum 冻结周期
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @return 日总加组功率曲线
	 */
	public List<GeneralRealPowerDataBean> findTotalRealPowerData(Long totalId, int freezeCycleNum,
			Date dataDate, String consNo) throws Exception{
        List<GeneralRealPowerDataBean> list = new ArrayList<GeneralRealPowerDataBean>();
		
        List<GeneralRealPowerDataBean> retList = generalAnalyseDao.findETotalRealPowerCurveByDate(totalId, dataDate, consNo);
        
        String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		
		String dateS = DateUtil.dateToString(dataDate);
		String a = null;
		
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		
		//如果没有数据,执行如下流程，如果数据库数据错误，今天之后应该是没数据，如果有，过滤
		if(null == retList){
			retList = new ArrayList<GeneralRealPowerDataBean>();
		}
		
		if(1 == flagVer1){//96个点
			for(int i = 0; i < timeStr.length ; i++){
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
						    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
					tempBean.setFlag(true);
					tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(2 == flagVer1){//48个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%2) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
						    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
					tempBean.setFlag(true);
					tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		if(3 == flagVer1){//24个点
			for(int i = 0; i < timeStr.length ; i++){
				if( (i%4) != 0){
					continue;
				}
				a = dateS + " " + timeStr[i];
				GeneralRealPowerDataBean tempBean = null;
				for(int j = 0; j < retList.size(); j++){
					GeneralRealPowerDataBean bean = (GeneralRealPowerDataBean)retList.get(j);
					if(null != bean && null != bean.getTime()){
						if(bean.getTime().length() > 19 
								&& bean.getTime().substring(0, 19).equals(a)){
						    tempBean = new GeneralRealPowerDataBean();
						    tempBean.setTime(timeStr[i].substring(0,5));
						    tempBean.setPower(bean.getPower());
				        	tempBean.setFlag(null != bean.getFlag() ? bean.getFlag() :false);
						    tempBean.setBeanFlag(false);
			        	    break;
						}
					}
				}
				if(null == tempBean){
					tempBean = new GeneralRealPowerDataBean();
					tempBean.setTime(timeStr[i].substring(0,5));
					tempBean.setPower(0.0);
					tempBean.setFlag(true);
					tempBean.setBeanFlag(true);
				}
				list.add(tempBean);
			}
		}
		
		return list;
	}
	
	/**
	 * 查询全部普通群组列表
	 * @return 群组名称和群组编号对象列表
	 */
	public List<GroupNameBean> findGroupName() throws Exception{
		try {
			return generalAnalyseDao.findGroupName();
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_FINDGROUPNAMEINFO);
		}
	}

	/**
	 * 保存群组明细
	 * @param groupNo
	 * @param consNo
	 * @return
	 */
	public void addToGroup(String groupNo, String consNo) throws Exception {
		try {
			generalAnalyseDao.addToGroup(groupNo,consNo);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_ADDTOGROUP);
		}
	}

	/**
	 * 将各曲线对象转化为曲线图数据点
	 * @param curveBean 曲线对象
	 * @param flag 曲线对象点数类型
	 * @return 曲线图数据点列表
	 */
	public List<GeneralDataBean> getGeneralDataBeanArray(Object curveBean, Integer flag, int flagVer1) throws Exception{
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		Method[] m = curveBean.getClass().getDeclaredMethods();
		int n = 1;
		int i = 0;
		String [] timeStr = {"00:00", "00:15", "00:30", "00:45", "01:00", "01:15", "01:30", "01:45",
								"02:00", "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45",
								"04:00", "04:15", "04:30", "04:45", "05:00", "05:15", "05:30", "05:45",
								"06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45",
								"08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45",
								"10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45",
								"12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45",
								"14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45",
								"16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45",
								"18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45",
								"20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45",
								"22:00", "22:15", "22:30", "22:45", "23:00", "23:15", "23:30", "23:45"};
		String getStr = "";
		if(curveBean instanceof ETotalPowerCurveBean){
			getStr = "P";
		} else if(curveBean instanceof ETotalEnergyCurveBean){
			getStr = "R";
		} else if(curveBean instanceof EMpPowerCurveBean){
			getStr = "P";
		} else if(curveBean instanceof EMpEnergyCurveBean){
			getStr = "E";
		} else if(curveBean instanceof EMpVolCurveBean){
			getStr = "U";
		} else if(curveBean instanceof EMpCurCurveBean){
			getStr = "I";
		} else if(curveBean instanceof EMpFactorCurveBean){
			getStr = "C";
		}else if(curveBean instanceof EMpReadCurveBean){
			getStr = "R";
		}else {
			return list;
		}
		while(i < m.length){
			if(!m[i].getName().startsWith("get"+getStr)){i++; continue;}
			try {
				if(flagVer1 == 1){//96点
					GeneralDataBean bean = new GeneralDataBean();
					bean.setTime(timeStr[n-1]);
					if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
						bean.setFlag(true);
						bean.setData(0.0);
					}else{
						bean.setFlag(false);
						bean.setData(new Double(m[i].invoke(curveBean, null).toString()));
					}
					list.add(bean);
					i++;
					n++;
					continue;
				}
				if(flagVer1 == 2){//48点
					int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
					if((index-1)%2 == 0 ){
						GeneralDataBean bean = new GeneralDataBean();
						bean.setTime(timeStr[n-1]);
						if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
							bean.setFlag(true);
							bean.setData(0.0);
						}else{
							bean.setFlag(false);
							bean.setData(new Double(m[i].invoke(curveBean, null).toString()));
						}
						list.add(bean);
					}
					i++;
					n++;
					continue;
				}
				if(flagVer1 == 3){//24点
					int index = new Integer(m[i].getName().substring(m[i].getName().indexOf(getStr)+1));
					if((index-1)%4 == 0 ){
						GeneralDataBean bean = new GeneralDataBean();
						bean.setTime(timeStr[n-1]);
						if(null == m[i].invoke(curveBean, null) || "".equals(m[i].invoke(curveBean, null).toString())){
							bean.setFlag(true);
							bean.setData(0.0);
						}else{
							bean.setFlag(false);
							bean.setData(new Double(m[i].invoke(curveBean, null).toString()));
						}
						list.add(bean);
					}
					i++;
					n++;
					continue;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		return list;
	}

	/**
	 * 可以通过该函数，进行代码的缩写，对于flag标记可以不用多次判断，通过该函数可以直接操作
	 */
	public List<GeneralDataBean> getMinLengthList(List<GeneralDataBean> generalData,
			List<GeneralDataBean> contrastData){
		if(generalData.size() ==0 || contrastData.size() == 0){
			return new ArrayList<GeneralDataBean>();
		}
		else if (generalData.size() <= contrastData.size()) {
			return generalData;
		} else {
			int num = generalData.size()/contrastData.size();
			List<GeneralDataBean> resList = new ArrayList<GeneralDataBean>();
			for (int i = 0; i < contrastData.size(); i++) {
				resList.add(generalData.get(i*num));
			}
			return resList;
		}
	}
	
	/**
	 * 查询曲线点数
	 * @return 曲线显示点数
	 */
	public int queryConsPoint(int freezeCycleNum) throws Exception{		
		int point = 0;
		if(96 == freezeCycleNum){
			point = 1;
		}else if(48 == freezeCycleNum){
			point = 2;
		}else if(24 == freezeCycleNum){
			point = 3;
		}else{
			point = 2;
		}
		return point;
	}
	
	/**
	 * 查询时间模板，为生成曲线而用
	 * @param freezeCycleNum
	 * @return List<GeneralDataBean>
	 */
	public List<GeneralDataBean> getTimeModelList(int freezeCycleNum) throws Exception{
		List<GeneralDataBean> list = new ArrayList<GeneralDataBean>();
		String [] timeStr = {"00:00:00", "00:15:00", "00:30:00", "00:45:00", "01:00:00", "01:15:00", "01:30:00", "01:45:00",
				"02:00:00", "02:15:00", "02:30:00", "02:45:00", "03:00:00", "03:15:00", "03:30:00", "03:45:00",
				"04:00:00", "04:15:00", "04:30:00", "04:45:00", "05:00:00", "05:15:00", "05:30:00", "05:45:00",
				"06:00:00", "06:15:00", "06:30:00", "06:45:00", "07:00:00", "07:15:00", "07:30:00", "07:45:00",
				"08:00:00", "08:15:00", "08:30:00", "08:45:00", "09:00:00", "09:15:00", "09:30:00", "09:45:00",
				"10:00:00", "10:15:00", "10:30:00", "10:45:00", "11:00:00", "11:15:00", "11:30:00", "11:45:00",
				"12:00:00", "12:15:00", "12:30:00", "12:45:00", "13:00:00", "13:15:00", "13:30:00", "13:45:00",
				"14:00:00", "14:15:00", "14:30:00", "14:45:00", "15:00:00", "15:15:00", "15:30:00", "15:45:00",
				"16:00:00", "16:15:00", "16:30:00", "16:45:00", "17:00:00", "17:15:00", "17:30:00", "17:45:00",
				"18:00:00", "18:15:00", "18:30:00", "18:45:00", "19:00:00", "19:15:00", "19:30:00", "19:45:00",
				"20:00:00", "20:15:00", "20:30:00", "20:45:00", "21:00:00", "21:15:00", "21:30:00", "21:45:00",
				"22:00:00", "22:15:00", "22:30:00", "22:45:00", "23:00:00", "23:15:00", "23:30:00", "23:45:00"};
		int flagVer1 = this.queryConsPoint(freezeCycleNum);//曲线显示点数，默认48个点
		if(1 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		if(2 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				if((i%2) != 0){
					continue;
				}
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		if(3 == flagVer1){
			for(int i = 0; i < timeStr.length; i++){
				if((i%4) != 0){
					continue;
				}
				GeneralDataBean bean  = new GeneralDataBean();
				bean.setTime(timeStr[i].substring(0,5));
			    list.add(bean);
			}
		}
		return list;
	}
	
	/**
	 * 查询某用户下的终端信息
	 * @param consNo
	 * @return List<EMpConsTmnlBean>
	 */
	public List<EMpConsTmnlBean> queryConsTmnlInfo(String consNo) throws Exception{
		List<EMpConsTmnlBean> list = null;
		try {
			List<EMpConsTmnlBean> resList = this.generalAnalyseDao.queryConsTmnlInfo(consNo);
			if(null != resList && resList.size() > 0){
				list = new ArrayList<EMpConsTmnlBean>();
				for(int i = 0; i < resList.size(); i++){
					EMpConsTmnlBean tempBean = (EMpConsTmnlBean)resList.get(i);
					if(null != tempBean && null != tempBean.getTmnlAssetNo()
							&& !"".equals(tempBean.getTmnlAssetNo())){
						list.add(tempBean);
					}
				}
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_MPCONSTMNLINFO);
		}
		if(null == list){
			list = new ArrayList<EMpConsTmnlBean>();
			EMpConsTmnlBean bean = new EMpConsTmnlBean();
			bean.setTmnlAssetNumber("*");
			bean.setTmnlAssetNo("全部终端");
			list.add(bean);
		}
		return list;
	}
}
