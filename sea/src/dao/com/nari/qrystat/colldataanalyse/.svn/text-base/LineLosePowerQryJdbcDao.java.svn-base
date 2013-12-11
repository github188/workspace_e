package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;

import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 线路用电损耗查询Jdbc之Dao接口
 * @author 姜炜超
 */
public interface LineLosePowerQryJdbcDao {
	/**
	 * 线路用电损耗查询信息，主页面
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerQry(String nodeId, String nodeType, String orgType, Integer startDate, 
			Integer endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内线路用户抄表成功信息
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerReadBean>
	 */
	public Page<LineLosePowerReadBean> findLineLPReadSuccInfo(String lineId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内线路用户抄表失败信息
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerReadBean>
	 */
	public Page<LineLosePowerReadBean> findLineLPReadFailInfo(String lineId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内线路用户漏点信息，界面全选调用
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByAll(String lineId, String startDate, 
			String ioValue,String endDate);
	
	/**
	 * 根据条件查询时间段内线路用户漏点信息，非全选，仅仅对选中用户进行操作
	 * @param tmnlAssetNoList 终端资产编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByANList(List<String> tmnlAssetNoList, String startDate, String endDate);
	
	/**
	 * 重新计算
	 * @param orgNo
	 * @param statDate  
	 * @return 
	 */
	public void recalcData(final String orgNo, final Date statDate);
}
