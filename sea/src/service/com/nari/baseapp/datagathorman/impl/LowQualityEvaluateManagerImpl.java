package com.nari.baseapp.datagathorman.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagatherman.LowQualityEvaluate;
import com.nari.baseapp.datagathorman.LowQualityEvaluateDao;
import com.nari.baseapp.datagathorman.LowQualityEvaluateManager;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 低压采集质量service
 * 
 * @author YuTao
 * 
 */
public class LowQualityEvaluateManagerImpl implements LowQualityEvaluateManager {

	private LowQualityEvaluateDao lowQualityEvaluateDao;
	public LowQualityEvaluateDao getLowQualityEvaluateDao() {
		return lowQualityEvaluateDao;
	}

	public void setLowQualityEvaluateDao(
			LowQualityEvaluateDao lowQualityEvaluateDao) {
		this.lowQualityEvaluateDao = lowQualityEvaluateDao;
	}

	/**
	 * 根据左边树节点查询集抄采集成功率
	 * 
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param qualityValue
	 *            供电所orgNo或线路id
	 * @param type
	 *            org--供电所 line--线路
	 * @return
	 * @throws Exception
	 */
	public List<LowQualityEvaluate> getRateByNode(PSysUser pSysUser,
			String startDate, String endDate, String qualityValue, String type,
			int myDays) throws Exception {
		List<LowQualityEvaluate> list = new ArrayList<LowQualityEvaluate>();
		if (pSysUser == null)
			return list;
		try {
			// 查询抄表成功的数量
			List<LowQualityEvaluate> succList = this.lowQualityEvaluateDao
					.findLowQE(pSysUser, qualityValue, type, startDate, endDate);
			// 查询总表数量
			List<LowQualityEvaluate> totalList = this.lowQualityEvaluateDao
					.findTotalLowQE(qualityValue, type);

			if (succList == null || totalList == null)
				return list;

			Map<String, LowQualityEvaluate> succMap = new HashMap<String, LowQualityEvaluate>();

			for (LowQualityEvaluate lqe : succList) {
				succMap.put(lqe.getTmnlAssetNo(), lqe);
			}

			// 遍历总表数量的list
			for (LowQualityEvaluate lqe : totalList) {
				LowQualityEvaluate l = succMap.get(lqe.getTmnlAssetNo());
				if (l == null) {
					continue;
				}
				LowQualityEvaluate lqeDto = new LowQualityEvaluate();
				lqeDto.setConsName(l.getConsName());
				lqeDto.setConsNo(lqe.getConsNo());
				lqeDto.setFailCount(lqe.getTcount() * myDays - l.getScount() < 0 ? 0
								: lqe.getTcount() * myDays - l.getScount());
				lqeDto.setScount(l.getScount());
				lqeDto.setTcount(lqe.getTcount() * myDays);
				lqeDto.setTmnlAssetNo(lqe.getTmnlAssetNo());

				String succRate = "0";
				DecimalFormat df = new DecimalFormat("0.0");
				if (lqe.getTcount() == 0) {
					continue;
				} else {
					double lo = Double.valueOf(l.getScount())
							/ Double.valueOf(lqe.getTcount() * myDays);
					lo = lo < 0 ? 0 : lo;
					succRate = df.format(lo * 100);
				}
				lqeDto.setSuccRate(succRate);

				list.add(lqeDto);
			}

		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("根据左边树节点查询集抄采集成功率异常");
		}
		return list;
	}

	/**
	 * 根据终端资产号查询一段时期内每天的采集成功率
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param tmnlAssetNo 终端资产号
	 * @param consNo 用户号
	 * @return
	 * @throws Exception
	 */
	public List<LowQualityEvaluate> getRateByTmnl(PSysUser pSysUser,
			String startDate, String endDate, String tmnlAssetNo,
			String consNo, String consName, Long totalNum) throws Exception {
		List<LowQualityEvaluate> list = new ArrayList<LowQualityEvaluate>();
		if (pSysUser == null)
			return list;
		try {
			// 查询抄表成功的数量
			List<LowQualityEvaluate> tmnlSuccList = this.lowQualityEvaluateDao
					.findRateGbyDate(pSysUser, tmnlAssetNo, consNo, startDate,
							endDate);

			if (tmnlSuccList == null)
				return list;

			for (LowQualityEvaluate lqe : tmnlSuccList) {
				lqe.setConsNo(consNo);
				lqe.setConsName(consName);
				lqe.setTcount(totalNum);

				String succRate = "0";
				DecimalFormat df = new DecimalFormat("0.0");
				double lo = Double.valueOf(lqe.getScount())
						/ Double.valueOf(totalNum);
				lo = lo < 0 ? 0 : lo;
				succRate = df.format(lo * 100);
				lqe.setSuccRate(succRate);
				
				list.add(lqe);
			}
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("根据终端查询一段时间内每天的集抄采集成功率异常");
		}
		return list;
	}

	@Override
	public List<LowQualityEvaluate> findFail(String data, String tmnlNo)
			throws Exception {
		Map<Integer, LowQualityEvaluate> failMap = new HashMap<Integer, LowQualityEvaluate>();
		List<LowQualityEvaluate> failList = new ArrayList<LowQualityEvaluate>();
		try {
			int failValue = 1;
			List<LowQualityEvaluate> failA = this.lowQualityEvaluateDao
					.findFailA(data, tmnlNo);
			List<LowQualityEvaluate> failB = this.lowQualityEvaluateDao
					.findFailB(data, tmnlNo);
			
			if(failB == null || failB.size() == 0){
				return failList;
			}
			if(failA == null || failA.size() == 0){
				for (LowQualityEvaluate lowQualityEvaluate : failB) {
					failList.add(lowQualityEvaluate);
				}
				return failList;
			}
			
			for (LowQualityEvaluate lowQualityEvaluate : failB) {
				failMap.put(lowQualityEvaluate.getRegSn(), lowQualityEvaluate);
			}
			for (LowQualityEvaluate lowQualityEvaluate : failA) {
				String dm = lowQualityEvaluate.getDenizenMp();
				char[] dmChar = dm.toCharArray();
				for (char c : dmChar) {
					if (c == '0') {
						if(failMap.get(failValue)!=null)
							failList.add(failMap.get(failValue));
					}
					failValue++;
				}
			}
		} catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询低压用户抄表失败异常");
		}
		return failList;
	}
}
