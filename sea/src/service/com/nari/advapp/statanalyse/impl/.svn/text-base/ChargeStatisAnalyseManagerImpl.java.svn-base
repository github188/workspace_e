/**
 * 
 */
package com.nari.advapp.statanalyse.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.statanalyse.ChargeStatisAnalyseDTO;
import com.nari.advapp.statanalyse.ChargeStatisAnalyseDao;
import com.nari.advapp.statanalyse.ChargeStatisAnalyseManager;
import com.nari.advapp.statanalyse.StatAnalyseCurveDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 负荷统计分析
 * @author ChunMingLi
 * @since 2010-8-7
 *
 */
public class ChargeStatisAnalyseManagerImpl implements	ChargeStatisAnalyseManager {
	//设置注入
	private ChargeStatisAnalyseDao chargeStatisAnalyseDao;
	private Calendar calendar = new GregorianCalendar();
	/**
	 *  the chargeStatisAnalyseDao to set
	 * @param chargeStatisAnalyseDao the chargeStatisAnalyseDao to set
	 */
	public void setChargeStatisAnalyseDao(
			ChargeStatisAnalyseDao chargeStatisAnalyseDao) {
		this.chargeStatisAnalyseDao = chargeStatisAnalyseDao;
	}

	/* (non-Javadoc)
	 * @see com.nari.advapp.statanalyse.ChargeStatisAnalyseManager#queryChargeStatisAnalyse(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date)
	 */
	@Override
	public Map<String, List<StatAnalyseCurveDto>> queryChargeStatisAnalyseCurve(String orgType, String nodeType, String nodeValue, PSysUser userInfo,  int timeFlag,
			int contrastFlag, Date startDate, Date endDate) throws Exception {
		try {
			SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
			Map<String, List<StatAnalyseCurveDto>> map = new HashMap<String, List<StatAnalyseCurveDto>>();
//			List<StatAnalyseCurveDto> statAnalyseList = new ArrayList<StatAnalyseCurveDto>();
//			List<StatAnalyseCurveDto> conStatAnalyseList = new ArrayList<StatAnalyseCurveDto>();
			List<StatAnalyseCurveDto> statAnalyseList = null;
			List<StatAnalyseCurveDto> conStatAnalyseList = null;
			
			// 比对查询时间
			Date contraStartDate = null;
			Date contraEndDate = null;
			
			String startTime = null;
			String endTime = null;
			String conStartTime = null;
			String conEndTime = null;
			
			// 获得比对时间
			if (contrastFlag == 1) {
				// 获得环比开始时间
				calendar.setTime(startDate);
				calendar.add(Calendar.MONTH, -1);
				contraStartDate = calendar.getTime();
				// 获得环比结束时间
//				calendar.setTime(endDate);
//				calendar.add(Calendar.MONTH, -1);
				contraEndDate = endDate;
			} else if (contrastFlag == 2) {
				// 获得同比开始时间
				calendar.setTime(startDate);
				calendar.add(Calendar.YEAR, -1);
				contraStartDate = calendar.getTime();
				// 获得同比结束时间
//				calendar.setTime(endDate);
//				calendar.add(Calendar.YEAR, -1);
				contraEndDate = endDate;
			}
			//转换时间类型
			if (timeFlag == 1) {
				startTime = sdf_date.format(startDate);
				endTime = sdf_date.format(endDate);
				if (contrastFlag == 1 || contrastFlag == 2 ) {
					conStartTime = sdf_date.format(contraStartDate);
					conEndTime = sdf_date.format(contraEndDate);
				}
			} else if (timeFlag == 2) {
				startTime = sdf_month.format(startDate);
				calendar.setTime(endDate);
				calendar.add(Calendar.MONTH, 1);
				endTime = sdf_month.format(calendar.getTime());
				if (contrastFlag == 2 || contrastFlag == 2 ) {
					conStartTime = sdf_month.format(contraStartDate);
					conEndTime = sdf_month.format(contraEndDate);
				}
				
			} else if (timeFlag == 3) {
				startTime = sdf_year.format(startDate);
				calendar.setTime(endDate);
				calendar.add(Calendar.YEAR, 1);
				endTime = sdf_year.format(calendar.getTime());
				if (contrastFlag == 1 || contrastFlag == 2 ) {
					conStartTime = sdf_year.format(contraStartDate);
					conEndTime = sdf_year.format(contraEndDate);
				}
			}
			//比对时间为空不查询
			if(contraStartDate != null && contraEndDate != null){
				//查询比对曲线数据
				conStatAnalyseList = chargeStatisAnalyseDao.findChargeStatisAnalyseCurve(timeFlag,orgType, nodeType, nodeValue, userInfo, conStartTime, conEndTime);
			}
			//查询曲线数据图
			statAnalyseList =  chargeStatisAnalyseDao.findChargeStatisAnalyseCurve(timeFlag,orgType, nodeType, nodeValue, userInfo, startTime, endTime);
			//曲线数据加入map
			map.put("Curve_Date", statAnalyseList);
			if(conStatAnalyseList != null){
				//对比曲线数据加入map
				map.put("Con_Curve_Date", conStatAnalyseList);
			}
			return map;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询负荷统计分析曲线查询操作出错");
		}
	}

	/* (non-Javadoc)
	 * @see com.nari.advapp.statanalyse.ChargeStatisAnalyseManager#queryCollectionPointDemandMonth(java.lang.String, java.lang.String, com.nari.privilige.PSysUser, long, long, java.util.Date, java.util.Date, java.util.Date, java.util.Date)
	 */
	@Override
	public Page<ChargeStatisAnalyseDTO> queryChargeStatisAnalyseList(int timeFlag,String orgType, String nodeType, String nodeValue, PSysUser userInfo, long start,
			long limit, Date startDate, Date endDate) throws Exception {
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf_month = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
		String startTime = null;
		String endTime = null;
		try {
			//转换时间类型
			if (timeFlag == 1) {
				startTime = sdf_date.format(startDate);
				endTime = sdf_date.format(endDate);
			} else if (timeFlag == 2) {
				startTime = sdf_month.format(startDate);
				calendar.setTime(endDate);
				calendar.add(Calendar.MONTH, 1);
				endTime = sdf_month.format(calendar.getTime());
				
			} else if (timeFlag == 3) {
				startTime = sdf_year.format(startDate);
				calendar.setTime(endDate);
				calendar.add(Calendar.YEAR, 1);
				endTime = sdf_year.format(calendar.getTime());
			}
			return chargeStatisAnalyseDao.findChargeStatisAnalyseList(timeFlag,orgType, nodeType, nodeValue, userInfo, start, limit, startTime, endTime);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询负荷统计分析明细查询操作出错");
		}
	}

}
