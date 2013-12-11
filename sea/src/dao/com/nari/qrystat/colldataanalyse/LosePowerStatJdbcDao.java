package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 台区用电损耗查询Jdbc之Dao接口
 * @author 姜炜超
 */
public interface LosePowerStatJdbcDao {
	/**
	 * 根据条件查询时间段内台区用电损耗统计信息，主页面
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatDto>
	 */
	public Page<LosePowerStatDto> findLPSInfoByCond(String nodeId, String nodeType, String orgType, Integer startDate, 
			Integer endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内台区用户抄表成功信息
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @Param succFalg *表示全部，1表示成功，0表示失败
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatReadBean>
	 */
	public Page<LosePowerStatReadBean> findLPSReadSuccInfo(String tgId, String startDate, String ioValue,String succFalg,
			String endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内台区用户抄表失败信息
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatReadBean>
	 */
	public Page<LosePowerStatReadBean> findLPSReadFailInfo(String tgId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser);
	
	/**
	 * 根据条件查询时间段内台区用户漏点信息，界面全选调用
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByAll(String tgId, String startDate, 
			String ioValue,String endDate);
	
	/**
	 * 根据条件查询时间段内台区用户漏点信息，非全选，仅仅对选中用户进行操作
	 * @param tmnlAssetNoList 终端资产编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByANList(List<String> tmnlAssetNoList, String startDate, String endDate);
	
	/**
	 * 重新计算
	 * @param orgNo，台区的供电单位
	 * @param statDate  
	 * @return 
	 */
	public void recalcData(final String orgNo, final Date statDate);
	/**
	 * 从数据库中取出与图形相关的数据
	 * @param tgId  台区id
	 * @param start 统计的开始时间
	 * @param end  统计的结束时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<Map> findChartData(Integer tgId, Date start, Date end);
}
