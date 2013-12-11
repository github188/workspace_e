package com.nari.advapp.vipconsman;

import java.util.List;

import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.support.Page;

/**
 * 重点用户监测数据查询业务层接口
 * @author 姜炜超
 */
public interface VipConsMonitorDataManager {
	/**
	 * 根据条件查询重点用户信息
	 * @param pSysUser
	 * @param start
	 * @param limit
	 * @return Page<VipConsMonitorDataBean>
	 */
	public Page<VipConsMonitorDataBean> findVipConsMonitorData(PSysUser pSysUser, long start, int limit) throws Exception;
	
	/**
	 * 查询时间模板，为生成曲线而用
	 * @param freezeCycleNum
	 * @return List<GeneralDataBean>
	 */
	public List<GeneralDataBean> getTimeModelList(int freezeCycleNum);
	
	/**
	 * 查询某用户下的表计信息
	 * @param consNo
	 * @return List<EDataMp>
	 */
	public List<EDataMp> findConsAssetInfo(String consNo) throws Exception;
	
	/**
	 * 查询表计查询其挂名下的终端的冻结周期
	 * @param assetNo
	 * @return freezeCycleNum
	 */
	public int findFreezeCycleNum(String assetNo) throws Exception ;
	
	/**
	 * 查询测量点功率曲线(实时数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsRealPowerDataBean>
	 */
	public List<VipConsRealPowerDataBean> findMpRealPowerData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum) throws Exception ;
	
	/**
	 * 查询测量点功率曲线(冻结数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsStatDataBean>
	 */
	public List<VipConsStatDataBean> findMpStatPowerData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum) throws Exception ;
	
	/**
	 * 查询测量点功率曲线(冻结数据)
	 * @param assetNo 表计编号
	 * @param dataDate 选择日期
	 * @param consNo 客户编号
	 * @param freezeCycleNum 冻结周期
	 * @return List<VipConsStatDataBean>
	 */
	public List<VipConsStatDataBean> findMpEnergyData(String assetNo, String dataDate, 
			String consNo , int freezeCycleNum) throws Exception ;
}
