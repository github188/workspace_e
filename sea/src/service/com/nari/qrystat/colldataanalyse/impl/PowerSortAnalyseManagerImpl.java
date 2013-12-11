package com.nari.qrystat.colldataanalyse.impl;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.PowerSortAnalyseDto;
import com.nari.qrystat.colldataanalyse.PowerSortAnalyseJdbcDao;
import com.nari.qrystat.colldataanalyse.PowerSortAnalyseManager;
import com.nari.support.Page;
import com.nari.util.ArithmeticUtil;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 用电客户排名分析Service实现类
 * @author 姜炜超
 */
public class PowerSortAnalyseManagerImpl implements PowerSortAnalyseManager{
	private PowerSortAnalyseJdbcDao powerSortAnalyseJdbcDao;//用电客户排名分析Dao处理类
	
	public void setPowerSortAnalyseJdbcDao(
			PowerSortAnalyseJdbcDao powerSortAnalyseJdbcDao) {
		this.powerSortAnalyseJdbcDao = powerSortAnalyseJdbcDao;
	}

	/**
	 * 根据条件查询用电客户排名分析信息，目前本期只考虑一个时间段，不单独考虑月，周
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param start
	 * @param limit
	 * @return Page<PowerSortAnalyseDto>
	 */
	public Page<PowerSortAnalyseDto> findPSAInfoByCond(String nodeId, String nodeType, String orgType,
			String startDate, String endDate, int sort,long start, int limit, PSysUser user) throws Exception{		
		PowerSortAnalyseDto dto = null;//中间bean，过渡使用，用来获取本期同比
		PowerSortAnalyseDto dto2 = null;//中间bean，过渡使用，用来获取用电占用比率
		Double totalE = 0.0;//总用电量
		Page<PowerSortAnalyseDto> psc = null;//定义返回分页对象
		try {
			psc = powerSortAnalyseJdbcDao.findPSAInfoByCond(nodeId, nodeType, orgType, startDate, endDate,
					DateUtil.beforeYear(startDate), DateUtil.beforeYear(endDate), sort, start, limit, user);

			if(null != psc && null != psc.getResult()&& 0 < psc.getResult().size()){
				for(int i = 0 ; i < psc.getResult().size(); i++){
					dto = (PowerSortAnalyseDto)psc.getResult().get(i);
					
					if(null != dto.getTqyd() && null != dto.getBqyd() && 0 != dto.getTqyd()){//如果同期用电为0，则本期同比，显示为空
						if(0 == ArithmeticUtil.sub(dto.getBqyd(),dto.getTqyd())){//如果同期和本期一样，则本期同比为0
							dto.setBqtb(0.0);
						}
						else{//本期同比=（本期用电-同期用电）/同期用电
							dto.setBqtb(ArithmeticUtil.round(100*ArithmeticUtil.div(ArithmeticUtil.sub(dto.getBqyd(),dto.getTqyd()),dto.getTqyd(),4),2));
						}
					}else{
						dto.setBqtb(null);
					}
					if(null != dto.getBqyd()){
					    totalE = ArithmeticUtil.add(totalE, dto.getBqyd());
					}
				}
				if(0 != totalE){//如果总用电量不为0，计算本期用电占用比率=本期用电/总用电量
		    		for(int i = 0 ; i < psc.getResult().size(); i++){
		    			dto2 = (PowerSortAnalyseDto)psc.getResult().get(i);
		    			if(null != dto2.getBqyd()){
		    			    dto2.setPowerRate(ArithmeticUtil.round(100*ArithmeticUtil.div(dto2.getBqyd(),totalE,4),2));
		    			}
		    		}
				}
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_POWERSORTANALYSEINFO);
		}
		return psc;
	}
	
	/**
	 * 根据条件查询某用电单位某时间段所有用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @return Double
	 */
	public Double queryPSATotalPower(String nodeId, String nodeType, String orgType,
			String startDate, String endDate, PSysUser user) throws Exception{
		try {
			return powerSortAnalyseJdbcDao.queryPSATotalPower(nodeId, nodeType, orgType, startDate, endDate, user);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_PSATOTALPOWERINFO);
		}
	}
	
	/**
	 * 根据条件查询某用电单位某时间段排名前n位用户的总耗电量
	 * @param nodeId
	 * @param nodeType
	 * @param startDate
	 * @param endDate
	 * @param sort
	 * @param start
	 * @param limit
	 * @return Double
	 */
	public Double queryPSASortPower(String nodeId, String nodeType, String orgType,
			String startDate, String endDate, int sort,PSysUser user) throws Exception{
		try {
			return powerSortAnalyseJdbcDao.queryPSASortPower(nodeId, nodeType, orgType, startDate, endDate, sort,user);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_PSASORTPOWERINFO);
		}
	}
}
