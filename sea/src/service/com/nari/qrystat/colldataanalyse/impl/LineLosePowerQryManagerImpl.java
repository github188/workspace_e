package com.nari.qrystat.colldataanalyse.impl;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.ami.database.datapursue.ATmnlMissingMark;
import com.nari.ami.database.datapursue.ATmnlMissingMarkId;
import com.nari.ami.datapursue.TmnlTaskFactory;
import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineLosePowerBean;
import com.nari.qrystat.colldataanalyse.LineLosePowerQryJdbcDao;
import com.nari.qrystat.colldataanalyse.LineLosePowerQryManager;
import com.nari.qrystat.colldataanalyse.LineLosePowerReadBean;
import com.nari.qrystat.colldataanalyse.LosePowerStatReadBean;
import com.nari.support.Page;
import com.nari.util.ArithmeticUtil;
import com.nari.util.DateUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 线路用电损耗查询实现类
 * @author 姜炜超
 */
public class LineLosePowerQryManagerImpl implements LineLosePowerQryManager{
	
	private LineLosePowerQryJdbcDao lineLosePowerQryJdbcDao;

	public void setLineLosePowerQryJdbcDao(
			LineLosePowerQryJdbcDao lineLosePowerQryJdbcDao) {
		this.lineLosePowerQryJdbcDao = lineLosePowerQryJdbcDao;
	}

	/**
	 * 线路用电损耗查询信息，主页面grid显示内容
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param queryDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerQry(String nodeId, String nodeType,String orgType,  
			Date startDate, Date endDate, long start, int limit, PSysUser pSysUser )throws Exception{
		Page<LineLosePowerBean> psc = null;//定义返回分页对象
		Integer stratD = 0;
		Integer endD = 0;
		
		try {
			stratD = Integer.valueOf(DateUtil.dateToStringNoV(startDate));//转换成 yyyymmdd格式
			endD = Integer.valueOf(DateUtil.afterDateNoV(endDate,1));//推后一天，并转换成 yyyymmdd格式
			
			psc = lineLosePowerQryJdbcDao.findLineLosePowerQry(nodeId, nodeType, orgType, stratD, endD, start, limit, pSysUser);
			
			if(null == psc){
				psc = new Page<LineLosePowerBean>();
			}else{
				if(null != psc.getResult() && 0 < psc.getResult().size()){
				    for(int i = 0 ; i < psc.getResult().size(); i++){
				    	LineLosePowerBean dto = (LineLosePowerBean)psc.getResult().get(i);
				    	dto.setStartDate(startDate);//仅塞一下，页面不做处理
				    	dto.setEndDate(endDate);//仅塞一下，页面不做处理
				    	
				    	if(null != dto.getSupplypq() && null != dto.getSpq()){
				    	    dto.setLpq(ArithmeticUtil.sub(dto.getSupplypq(), dto.getSpq()));//损失电量= 供电量-售电量
				    	}else{
				    		dto.setLpq(null);
				    	}
				    	
				    	if(null != dto.getLpq() && 0 != dto.getSupplypq()){
				    		//总电量不为0，则计算出有损线损率=（损失电量/供电量）*100%
				    		dto.setLlr(ArithmeticUtil.round(100*ArithmeticUtil.div(dto.getLpq(), dto.getSupplypq(),4),2));
				    	}else{
				    		dto.setLlr(null);
				    	}
				    	
				    	if(null != dto.getLlr() && null != dto.getIdx()){
				    		dto.setDiff(ArithmeticUtil.sub(dto.getLlr(), dto.getIdx()));//指标比差异=线损率-线损率指标
				    	}else{
				    		dto.setDiff(null);
				    	}
				    }
				}
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_LINELOSEPOWERQRYINFO);
		}
		
		return psc;
	}
	
	/**
	 * 根据条件查询时间段内线路用户抄表信息
	 * @param lineId 线路id 
	 * @param queryDate 开始日期
	 * @param endDate 结束日期
	 * @param flag 成功或失败 true表示成功，false表示失败
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerReadBean>
	 */
	public Page<LineLosePowerReadBean> findLineLPReadInfo(String lineId, Date startDate, Boolean flag, String ioValue,
			Date endDate,long start, int limit, PSysUser pSysUser)throws Exception{
		if(null == lineId || null == startDate || null ==endDate || null == ioValue){
			return new Page<LineLosePowerReadBean>();
		}
		try{
			if(flag){
			    return lineLosePowerQryJdbcDao.findLineLPReadSuccInfo(lineId, DateUtil.dateToString(startDate), 
					ioValue, DateUtil.dateToString(endDate), start, limit, pSysUser);
			}else{
				return lineLosePowerQryJdbcDao.findLineLPReadFailInfo(lineId, DateUtil.dateToString(startDate), 
						ioValue, DateUtil.dateToString(endDate), start, limit, pSysUser);
			}
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_LINELOSEPOWERQRYREADINFO);
		}
	}
	
	/**
	 * 补召
	 * @param checkAll 是否全选
	 * @param lineId 线路id
	 * @param queryDate 开始日期
	 * @param endDate 结束日期
	 * @param flag 成功或失败 true表示成功，false表示失败
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param tmnlAssetNoList 资产编号
	 * @return 
	 */
	public void recallData(Boolean checkAll, String lineId, Date startDate, Boolean flag, String ioValue, 
			Date endDate,List<String> tmnlAssetNoList)throws Exception{
		if(null == tmnlAssetNoList || 0 == tmnlAssetNoList.size()){
    		return ;
    	}
		List<GatherByHandDto> list = null;
		if(checkAll){
			if(null == lineId || null == startDate || null ==endDate || null == ioValue){
				return;
			}
			if(flag){
				return;
			}
			list = lineLosePowerQryJdbcDao.findTmnlMissingInfoByAll(lineId, DateUtil.dateToString(startDate), ioValue, 
					DateUtil.dateToString(endDate));
		}else{
			list = lineLosePowerQryJdbcDao.findTmnlMissingInfoByANList(tmnlAssetNoList, DateUtil.dateToString(startDate), 
					DateUtil.dateToString(endDate));
		}
		
		//调用补召接口
		if(null == list || 0 == list.size()){
			return;
		}
		for( int i = 0; i < list.size(); i++){
			if(null == list.get(i)){
				continue;
			}
			ATmnlMissingMark aTmnlMissingMark = new ATmnlMissingMark();
			ATmnlMissingMarkId aTmnlMissingMarkId = new ATmnlMissingMarkId();
			aTmnlMissingMarkId.setMpSn(list.get(i).getMpSn());
			aTmnlMissingMarkId.setProtNoList(list.get(i).getProtNoList());
			aTmnlMissingMarkId.setStatDate(list.get(i).getStatDate());
			aTmnlMissingMarkId.setTmnlAssetNo(list.get(i).getTmnlAssetNo());
			aTmnlMissingMark.setMarkId(aTmnlMissingMarkId);
			aTmnlMissingMark.setApplyCnt(list.get(i).getApplyCnt());
			aTmnlMissingMark.setDataGroup(list.get(i).getDataGroup());
			aTmnlMissingMark.setId(list.get(i).getDataId());
			aTmnlMissingMark.setDataSrc(list.get(i).getDataSrc());
			aTmnlMissingMark.setDenizenMp(list.get(i).getDenizenMp());
			aTmnlMissingMark.setFirstCollCnt(list.get(i).getFirstCollCnt());
			aTmnlMissingMark.setIsDenizen(list.get(i).getIsDenizen());
			aTmnlMissingMark.setMissingCnt(list.get(i).getMissingCnt());
			aTmnlMissingMark.setPowerCutCnt(list.get(i).getPowerCutCnt());
			aTmnlMissingMark.setTemplateId(list.get(i).getTemplateId());
			//调用补招接口进行补招
			List<TermTaskInfo> rtList = 
			TmnlTaskFactory.createManualRecallTask(aTmnlMissingMark);
			System.out.println(rtList.size());
		}
	}
	
	/**
	 * 重新计算
	 * @param orgNo
	 * @param queryDate 开始日期
	 * @param endDate 结束日期
	 * @return 
	 */
	public void recalcData(String orgNo, Date startDate, Date endDate)throws Exception{
		int interval = DateUtil.getInterval(endDate, startDate);
		for( int i = 0 ; i <= interval; i++){
			lineLosePowerQryJdbcDao.recalcData(orgNo, DateUtil.afterDateNC(startDate,i));
		}
	}
}
