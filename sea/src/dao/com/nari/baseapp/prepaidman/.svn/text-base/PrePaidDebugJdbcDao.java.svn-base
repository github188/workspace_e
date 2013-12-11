package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.elementsdata.EDataTotal;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 预付费投入调试DAO接口
 * @author 姜炜超
 */
public interface PrePaidDebugJdbcDao {
	/**
	 * 查询预付费投入调试用户信息，查询按钮调用
	 * @param control 控制方式
	 * @param buyOrder 购电单号 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param consNo 用户编号
	 * @param orgNo 供电单位编号
	 * @param tmnlAddr 终端地址
	 * @param debugStatus 调试状态
	 * @param user 操作员信息
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<PPDebugUserInfoBean>
	 */
	public Page<PPDebugUserInfoBean> findUserInfo(int control, String buyOrder, String startDate,
			String endDate, String consNo, String[] orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user, long start, int limit);
	
	/**
	 * 查询预付费投入调试用户信息，供全选选中调用
	 * @param control 控制方式
	 * @param buyOrder 购电单号 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param consNo 用户编号
	 * @param orgNo 供电单位编号
	 * @param tmnlAddr 终端地址
	 * @param debugStatus 调试状态
	 * @param user 操作员信息
	 * @return List<PPDebugUserInfoBean>
	 */
	public List<PPDebugUserInfoBean> findUserInfoByAll(int control, String buyOrder, String startDate,
			String endDate, String consNo, String[] orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user);
	
	/**
	 * 供电单位树。
	 * @param staffNo 操作员编号
	 * @param orgNo 供电单位编号
	 * @param orgType 供电单位类别
	 * @return List<OOrg> 
	 */
	public List<OOrg> findOrgTree(String staffNo, String orgNo, String orgType);
	
	/**
	 * 根据购电单号查询预付费参数详细信息
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @return PPDebugUserInfoBean
	 */
	public PPDebugUserInfoBean findOrderParamInfo(Long feeCtrlId,String buyOrder);
	
	/**
	 * 更新预付费参数
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @param jumpValue
	 * @param buyValue
	 * @param alarmValue
	 * @param turnFlag
	 * @param totalNo
	 * @return 
	 * @throws Exception 
	 */
	public void updatePPDPara(Long feeCtrlId,String buyOrder,Integer jumpValue,
			Integer buyValue, Integer alarmValue, String turnFlag, Short totalNo);
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return PPDebugTmnlInfoBean
	 */
	public PPDebugTmnlInfoBean findTmnlInfo(String appNo, String terminalId, String meterId);
	
	/**
	 * 查询预付费用户调试状态具体流程信息
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return List<PPDebugUserStatusInfoBean>
	 */
	public List<PPDebugUserStatusInfoBean> findUserDebugInfo(String appNo, String terminalId, String meterId);
	
	/**
	 * 根据终端查询总加组列表，预付费参数显示调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<EDataTotal>
	 * @throws Exception 
	 */
	public List<EDataTotal> findParamTotalInfo(String tmnlAssetNo);
	
	/**
	 * 修改费控主表状态
	 * @Param feeCtrlId 费控id
	 * @param status 状态
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlStatus(Long feeCtrlId, String status);
	
	/**
	 * 修改或新增费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void saveOrUpdateWFeeCtrlFlow(PPDebugWFeectrlFlowBean bean);
	
	/**
	 * 修改费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlFlowStatus(PPDebugWFeectrlFlowBean bean);
	
	/**
	 * 根据终端查询总加组列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalBean>
	 * @throws 
	 */
	public List<PPDebugTotalBean> findTotalInfo(String tmnlAssetNo);
	
	/**
	 * 根据终端查询总加组所属测量点列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组编号
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	public List<PPDebugTotalMpBean> findTotalMpInfo(String tmnlAssetNo, Short totalNo);
	
	/**
	 * 根据终端查询终端下的测量点列表，总加组设置调用，新建调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	public List<PPDebugTotalMpBean> findTmnlMpInfo(String tmnlAssetNo);
	
	/**
	 * 新增总加组主表记录
	 * @Param PPDebugTotalSetBean 总加组信息
	 * @return 
	 * @throws Exception 
	 */
	public void addEDataTotal(PPDebugTotalSetBean bean);
	
	/**
	 * 新增终端总加组主表记录
	 * @Param PPDebugTotalSetBean 总加组信息
	 * @return 
	 * @throws Exception 
	 */
	public void addTTmnlTotalInfo(PPDebugTotalSetBean bean);
	
	/**
	 * 删除终端总加组表
	 * @Param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 
	 * @throws Exception 
	 */
	public void dltTTmnlTotalInfo(String tmnlAssetNo, Short totalNo);
	
	/**
	 * 校验终端下某个总加组是否下发投入
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return record 终端下某个总加组已经投入的记录条数
	 * @throws 
	 */
	public int checkTmnlTotal(String tmnlAssetNo, Short totalNo);
	
	/**
	 * 修改总加组主表为无效（删除操作）
	 * @Param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 
	 * @throws Exception 
	 */
	public void updateEDataTotalInvalid(String tmnlAssetNo, Short totalNo);
}
