package com.nari.qrystat.colldataanalyse.impl;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.AreaPowerDistDto;
import com.nari.qrystat.colldataanalyse.AreaPowerDistJdbcDao;
import com.nari.qrystat.colldataanalyse.AreaPowerDistManager;
import com.nari.support.Page;
import com.nari.util.ArithmeticUtil;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 地区电量分布查询实现类
 * @author 姜炜超
 */
public class AreaPowerDistManagerImpl implements AreaPowerDistManager{
	private AreaPowerDistJdbcDao areaPowerDistJdbcDao;//地区电量分布Dao对象

	public void setAreaPowerDistJdbcDao(AreaPowerDistJdbcDao areaPowerDistJdbcDao) {
		this.areaPowerDistJdbcDao = areaPowerDistJdbcDao;
	}
	
	/**
	 * 根据条件查询地区电量分布信息
	 * @param pSysUser 操作员信息
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param compareDate 对比起始日期
	 * @param start 
	 * @param limit
	 * @return Page<AreaPowerDistDto>
	 */
	public Page<AreaPowerDistDto> findAPDInfoByCond(PSysUser pSysUser , Date startDate, Date endDate,
			Date compareDate, long start, int limit) throws Exception{
		//如果供电单位为空，则返回
		if(null == pSysUser.getOrgNo()){
			return new Page<AreaPowerDistDto> ();
		}
		Page<AreaPowerDistDto> psc = null;//定义查询时间分页对象
		Page<AreaPowerDistDto> comparePsc = null;//定义对比时间分页对象
		AreaPowerDistDto dto = null;//查询时间过渡bean
		AreaPowerDistDto compareDto = null;//对比时间过渡bean
		double totalE = 0;//总电量
		
		try {
			//先查询出该操作员的所属部门类型
			String orgType = areaPowerDistJdbcDao.findOrgType(pSysUser.getOrgNo());
			
			String sDate = DateUtil.dateToString(startDate);//开始日期
			String eDate = DateUtil.dateToString(endDate);//结束日期
			int interval = DateUtil.getInterval(endDate,startDate);//日期间隔
			String compareSDate = DateUtil.dateToString(compareDate);//对比开始日
			String compareEDate = DateUtil.afterDate(compareDate,interval);//对比结束日
			
			//定义返回分页对象
			psc = areaPowerDistJdbcDao.findAPDInfoByCond(pSysUser, orgType, sDate, eDate, start, limit);
			comparePsc = areaPowerDistJdbcDao.findAPDInfoByCond(pSysUser, orgType, compareSDate, compareEDate, start, limit);
			
			//循化获取需要的数据
			if(null != psc && null != psc.getResult() && 0 < psc.getResult().size()){
				Boolean flag = false;
				//判断对比日期查询是否有结果
				if(null != comparePsc && null != comparePsc.getResult() 
						&& 0 < comparePsc.getResult().size()){
					flag = true;//true表示查询有结果
	    		}
		    	for(int i = 0 ; i < psc.getResult().size(); i++){
		    		dto = (AreaPowerDistDto)psc.getResult().get(i);
		    		
		    		if(flag){//如果对比日没数据，那么查询数据只需要循环获取算出电量占比，true表示有数据，才执行如下代码
		    			for(int j = 0 ; j < comparePsc.getResult().size(); j++){
		    				compareDto = (AreaPowerDistDto)comparePsc.getResult().get(j);
		    				if(null != dto.getOrgNo() && null != compareDto.getOrgNo()
		    						&& dto.getOrgNo().equals(compareDto.getOrgNo())){
		    					
		    					if(null != dto.getPapEBasic() && null != compareDto.getPapEBasic()){
				    				dto.setPapEComp(compareDto.getPapEBasic());//对比日电量
					    		    dto.setPaq(ArithmeticUtil.sub(dto.getPapEBasic(),dto.getPapEComp()));//电量增长量 = 基准日电量-对比日电量
					    		}else{
					    			dto.setPaq(null);
					    		}
				    			if(null != dto.getPaq() && null != dto.getPapEComp() && 0 != dto.getPapEComp()){//如果对比日电量不为0
					    			//计算电量增长率 = 电量增长量/ 对比日电量 *100%
					    		    dto.setPar(ArithmeticUtil.round(100*ArithmeticUtil.div(dto.getPaq(),dto.getPapEComp(),4),2));
					    		}else{
					    			dto.setPar(null);
					    		}
		    					break;
		    				}else{
		    					continue;
		    				}
		    		    }
		    		}
		    		if(null != dto.getPapEBasic()){//循化获取总电量
		    		    totalE = ArithmeticUtil.add(totalE,dto.getPapEBasic());
		    		}
		    	}
		    	if(0 != totalE){
		    		for(int i = 0 ; i < psc.getResult().size(); i++){
			    		dto = (AreaPowerDistDto)psc.getResult().get(i);
			    		//电量占比=基准日电量 /总电量 *100%
			    		if(null != dto.getPapEBasic()){
			    		    dto.setPr(ArithmeticUtil.round(100*ArithmeticUtil.div(dto.getPapEBasic(),totalE,4),2));
			    		}else{
			    			dto.setPr(null);
			    		}
		    		}
				}
		    }
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_AREAPOWERDISTINFO);
		}
		
		return psc;
	}
}
