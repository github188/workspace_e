package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 线路用电损耗查询接口
 * @author 姜炜超
 */
public interface LineLosePowerQryManager {
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
			Date startDate, Date endDate, long start, int limit, PSysUser pSysUser )throws Exception;
	
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
			Date endDate,long start, int limit, PSysUser pSysUser)throws Exception;
	
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
			Date endDate,List<String> tmnlAssetNoList)throws Exception;
	
	/**
	 * 重新计算
	 * @param orgNo
	 * @param queryDate 开始日期
	 * @param endDate 结束日期
	 * @return 
	 */
	public void recalcData(String orgNo, Date startDate, Date endDate)throws Exception;
}
