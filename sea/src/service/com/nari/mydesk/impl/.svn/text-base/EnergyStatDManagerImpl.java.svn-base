package com.nari.mydesk.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.mydesk.EnergyStatDDao;
import com.nari.mydesk.EnergyStatDDto;
import com.nari.mydesk.EnergyStatDManager;
import com.nari.privilige.PSysUser;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 
 * 监视主页每日电量统计 manager
 * @author ChunMingLi
 * @since  2010-7-28
 *
 */
public class EnergyStatDManagerImpl implements EnergyStatDManager {
	//注入DAO
	private EnergyStatDDao energyStatDDao;
	/**
	 *  the energyStatDDao to set
	 * @param energyStatDDao the energyStatDDao to set
	 */
	public void setEnergyStatDDao(EnergyStatDDao energyStatDDao) {
		this.energyStatDDao = energyStatDDao;
	}
	@Override
	public List<EnergyStatDDto> queryEnergyStatDay(PSysUser userInfo,
			String startDate, String endDate) throws Exception {
		
		try {
			List<String> nameList = energyStatDDao.queryProvinceName();
			String provinceName = null;
			int index = -1;
			if(nameList.size() == 1){
				for(String str : nameList){
					provinceName = str;
					index = provinceName.indexOf("省");
					if(index == -1){
						index = provinceName.indexOf("电力公司");
					}
					provinceName = provinceName.substring(0,index);
				}
			}else{
				provinceName = "";
			}
			List<EnergyStatDDto> tempList = energyStatDDao.findEnergyStatDay(provinceName,userInfo, startDate, endDate);
			List<EnergyStatDDto> dDtoList = new ArrayList<EnergyStatDDto>();
			if(tempList == null ){
				return null;
			}
			
			//列表处理
//			for(EnergyStatDDto dto : tempList){
//				if(dto.getEtdFlag().equals("01")){
//					dto.setOrgName("青海省全网用电量");
//				}else if(dto.getEtdFlag().equals("02")){
//					dto.setOrgName("青海省公司用电量");
//				}else if(dto.getEtdFlag().equals("6")){
//					dto.setOrgName("省际互供电量");
//				}else if(dto.getEtdFlag().equals("7")){
//					dto.setOrgName("青供甘电量");
//				}else if(dto.getEtdFlag().equals("8")){
//					dto.setOrgName("甘供青电量");
//				}
//				dDtoList.add(dto);
//			}
//			if(dDtoList == null ){
//				return null;
//			}
			return tempList;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("查询主页每日电量统计分析SQL查询操作出错");
		}
	}

}
