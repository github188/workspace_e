package com.nari.qrystat.colldataanalyse.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.TradeTrendlineBean;
import com.nari.qrystat.colldataanalyse.TradeTrendlineDao;
import com.nari.qrystat.colldataanalyse.TradeTrendlineDataBean;
import com.nari.qrystat.colldataanalyse.TradeTrendlineDto;
import com.nari.qrystat.colldataanalyse.TradeTrendlineManager;
import com.nari.util.ArithmeticUtil;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 行业用电趋势分析Service实现类
 * @author jiangyike
 */
public class TradeTrendlineManagerImpl implements TradeTrendlineManager {
	
	private TradeTrendlineDao tradeTrendlineDao;//行业趋势dao对象
	
	public void setTradeTrendlineDao(TradeTrendlineDao tradeTrendlineDao) {
		this.tradeTrendlineDao = tradeTrendlineDao;
	}

	/**
	 * 查询行业用电对比信息，用于曲线显示
	 * @param pSysUser
	 * @param tradeList
	 * @param interval
	 * @param rg 判断是否分月还是分日
	 * @param queryDate
	 * @param compareDate
	 * @return List<TradeTrendlineDto>
	 */
	public List<TradeTrendlineBean> findTradeTrendline(PSysUser pSysUser, List<TradeTrendlineDto> tradeList , 
			int rg, int interval, Date startDate, Date endDate) throws Exception {
		List<TradeTrendlineBean> curveList  = new ArrayList<TradeTrendlineBean>();//返回列表
		TradeTrendlineBean bean = null;
		// 从前台获取字符串，如果信息有误，返回
		if(null == tradeList || 0 >= tradeList.size()){
			return curveList;
		}
		try {
			// 循环获取每个行业的相关信息
			for(int i = 0; i< tradeList.size(); i++){
				TradeTrendlineDto queryDto = (TradeTrendlineDto)tradeList.get(i);
				if(null == queryDto || null == queryDto.getTradeNo()){
					continue;
				}
				bean = new TradeTrendlineBean();
				List<TradeTrendlineDataBean> list = new ArrayList<TradeTrendlineDataBean>();
				bean.setTradeNo(queryDto.getTradeNo());
				bean.setTradeName(queryDto.getTradeName());
				if(1 == rg){//分日查询
					for(int j = 0; j <= interval; j++){
						TradeTrendlineDataBean dto = tradeTrendlineDao.findTradeTrendlineByDay(pSysUser, queryDto.getTradeNo(), 
								DateUtil.afterDate(startDate, j));
						if(null != dto){
							list.add(dto);
						}else{
							dto = new TradeTrendlineDataBean();
							dto.setFlag(true);
							list.add(dto);
						}
					}
				}else{//分月
					for(int j = 0; j <= interval; j++){
						String startMonth = DateUtil.afterMonth(startDate, j)+"-01";
						String endMonth = DateUtil.getNextMonthFirstDay(startMonth);
						TradeTrendlineDataBean dto = tradeTrendlineDao.findTradeTrendlineByMonth(pSysUser, queryDto.getTradeNo(), 
								startMonth, endMonth);
						if(null != dto){
							list.add(dto);
						}else{
							dto = new TradeTrendlineDataBean();
							dto.setFlag(true);
							list.add(dto);
						}
					}
				}
				bean.setList(list);
				bean.setFlag(true);
				curveList.add(bean);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TRADETRENDLINECURVEINFO);
		}
		
		return curveList;
	}
	
	/**
	 * 查询行业信息，用于Grid显示
	 * @param pSysUser
	 * @param tradeList
	 * @param interval
	 * @param rg 判断是否分月还是分日
	 * @param startDate
	 * @param endData
	 * @return List<TradeTrendlineDto>
	 */
	public List<TradeTrendlineDto> findTradeTrendline4Grid(PSysUser pSysUser, List<TradeTrendlineDto> tradeList, 
			int rg, int interval, Date startDate, Date endDate) throws Exception {
		List<TradeTrendlineDto> gridList  = new ArrayList<TradeTrendlineDto>();//返回列表
		TradeTrendlineDto gridDto = null;
		
		//从前台获取字符串，如果信息有误，返回
		if(null == tradeList || 0 >= tradeList.size()){
			return gridList;
		}
		try {
			// 循环获取每个行业的相关信息
			for(int i = 0; i< tradeList.size(); i++){
				TradeTrendlineDto queryDto = (TradeTrendlineDto)tradeList.get(i);
				
				if(null == queryDto || null == queryDto.getTradeNo()){
					continue;
				}
				
				gridDto = new TradeTrendlineDto();
				gridDto.setTradeNo(queryDto.getTradeNo());
				gridDto.setTradeName(queryDto.getTradeName());
				Double count = 0.0;
				
				for(int j = 0; j <= interval; j++){
					TradeTrendlineDataBean dto = null;
					if(1 == rg){
					    dto = tradeTrendlineDao.findTradeTrendlineByDay(pSysUser, queryDto.getTradeNo(), DateUtil.afterDate(startDate, j));
					}else{
						String startMonth = DateUtil.afterMonth(startDate, j)+"-01";
						String endMonth = DateUtil.getNextMonthFirstDay(startMonth);
						dto = tradeTrendlineDao.findTradeTrendlineByMonth(pSysUser, queryDto.getTradeNo(), 
								startMonth, endMonth);
					}
					if(j == 0){
						if(null != dto && null != dto.getData()){//如果查询结果不为空，则循化获取合计数据
							gridDto.setDate0(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate0(null);
						}
					}else if(j == 1){
						if(null != dto && null != dto.getData()){
							gridDto.setDate1(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate1(null);
						}
					}else if(j == 2){
						if(null != dto && null != dto.getData()){
							gridDto.setDate2(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate2(null);
						}
					}else if(j == 3){
						if(null != dto && null != dto.getData()){
							gridDto.setDate3(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate3(null);
						}
					}else if(j == 4){
						if(null != dto && null != dto.getData()){
							gridDto.setDate4(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate4(null);
						}
					}else if(j == 5){
						if(null != dto && null != dto.getData()){
							gridDto.setDate5(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate5(null);
						}
					}else if(j == 6){
						if(null != dto && null != dto.getData()){
							gridDto.setDate6(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate6(null);
						}
					}else if(j == 7){
						if(null != dto && null != dto.getData()){
							gridDto.setDate7(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate7(null);
						}
					}else if(j == 8){
						if(null != dto && null != dto.getData()){
							gridDto.setDate8(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate8(null);
						}
					}else if(j == 9){
						if(null != dto && null != dto.getData()){
							gridDto.setDate9(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate9(null);
						}
					}else if(j == 10){
						if(null != dto && null != dto.getData()){
							gridDto.setDate10(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate10(null);
						}
					}else if(j == 11){
						if(null != dto && null != dto.getData()){
							gridDto.setDate11(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate11(null);
						}
					}else if(j == 12){
						if(null != dto && null != dto.getData()){
							gridDto.setDate12(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate12(null);
						}
					}else if(j == 13){
						if(null != dto && null != dto.getData()){
							gridDto.setDate13(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate13(null);
						}
					}else if(j == 14){
						if(null != dto && null != dto.getData()){
							gridDto.setDate14(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate14(null);
						}
					}else if(j == 15){
						if(null != dto && null != dto.getData()){
							gridDto.setDate15(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate15(null);
						}
					}else if(j == 16){
						if(null != dto && null != dto.getData()){
							gridDto.setDate16(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate16(null);
						}
					}else if(j == 17){
						if(null != dto && null != dto.getData()){
							gridDto.setDate17(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate17(null);
						}
					}else if(j == 18){
						if(null != dto && null != dto.getData()){
							gridDto.setDate18(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate18(null);
						}
					}else if(j == 19){
						if(null != dto && null != dto.getData()){
							gridDto.setDate19(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate19(null);
						}
					}else if(j == 20){
						if(null != dto && null != dto.getData()){
							gridDto.setDate20(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate20(null);
						}
					}else if(j == 21){
						if(null != dto && null != dto.getData()){
							gridDto.setDate21(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate21(null);
						}
					}else if(j == 22){
						if(null != dto && null != dto.getData()){
							gridDto.setDate22(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate22(null);
						}
					}else if(j == 23){
						if(null != dto && null != dto.getData()){
							gridDto.setDate23(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate23(null);
						}
					}else if(j == 24){
						if(null != dto && null != dto.getData()){
							gridDto.setDate24(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate24(null);
						}
					}else if(j == 25){
						if(null != dto && null != dto.getData()){
							gridDto.setDate25(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate25(null);
						}
					}else if(j == 26){
						if(null != dto && null != dto.getData()){
							gridDto.setDate26(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate26(null);
						}
					}else if(j == 27){
						if(null != dto && null != dto.getData()){
							gridDto.setDate27(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate27(null);
						}
					}else if(j == 28){
						if(null != dto && null != dto.getData()){
							gridDto.setDate28(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate28(null);
						}
					}else if(j == 29){
						if(null != dto && null != dto.getData()){
							gridDto.setDate29(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate29(null);
						}
					}else if(j == 30){
						if(null != dto && null != dto.getData()){
							gridDto.setDate30(dto.getData());
							count = ArithmeticUtil.add(count, dto.getData());
						}else{
							gridDto.setDate30(null);
						}
					}else{
					    continue;
					}
				}
				if(count == 0){
					gridDto.setCount(null);
				}else{
					gridDto.setCount(ArithmeticUtil.round(count,2));
				}
				gridList.add(gridDto);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_TRADETRENDLINEGRIDINFO);
		}
		
		return gridList;
	}
}
