package com.nari.baseapp.prepaidman;

import java.util.List;

import com.nari.elementsdata.EDataTotal;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.TreeNodeChecked;

/**
 * 预付费投入调试业务层接口
 * @author 姜炜超
 */
public interface PrePaidDebugManager {
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
	 * @return Page<WFeeCtrlBean>
	 */
	public Page<PPDebugUserInfoBean> findUserInfo(int control, String buyOrder, String startDate,
			String endDate, String consNo, String orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user, long start, int limit) throws Exception;
	
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
			String endDate, String consNo, String orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user)throws Exception;
	
	/**
	 * 供电单位树。
	 * @param pSysUser 操作员信息
	 * @return
	 * @throws Exception 
	 */
	public List<TreeNodeChecked> findOrgTree(PSysUser pSysUser) throws Exception;
	
	/**
	 * 根据购电单号查询详细信息
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @return PPDebugUserInfoBean
	 * @throws Exception 
	 */
	public PPDebugUserInfoBean findOrderParamInfo(Long feeCtrlId,String buyOrder) throws Exception;
	
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
	public void updatePPDPara(Long feeCtrlId,String buyOrder,Integer jumpValue,Integer buyValue, 
			Integer alarmValue, String turnFlag, Short totalNo) throws Exception;
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return PPDebugTmnlInfoBean
	 */
	public PPDebugTmnlInfoBean findTmnlInfo(String appNo, String terminalId, String meterId) throws Exception;
	
	/**
	 * 查询预付费用户调试状态具体流程信息
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return List<PPDebugUserStatusInfoBean>
	 */
	public List<PPDebugUserStatusInfoBean> findUserDebugInfo(String appNo, String terminalId, String meterId) throws Exception;
	
	/**
	 * 根据终端查询总加组列表
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<EDataTotal>
	 * @throws Exception 
	 */
	public List<EDataTotal> findParamTotalInfo(String tmnlAssetNo) throws Exception;
	
	/**
	 * 修改费控主表状态
	 * @Param feeCtrlId 费控id
	 * @param status 状态
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlStatus(Long feeCtrlId, String status) throws Exception;
	
	/**
	 * 修改或新增费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void saveOrUpdateWFeeCtrlFlow(PPDebugWFeectrlFlowBean bean) throws Exception;
	
	/**
	 * 修改费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlFlowStatus(PPDebugWFeectrlFlowBean bean) throws Exception;
	
	/**
	 * 根据终端查询总加组列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalBean>
	 * @throws 
	 */
	public List<PPDebugTotalBean> findTotalInfo(String tmnlAssetNo)throws Exception;
	
	/**
	 * 根据终端查询总加组下属测量点列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @param totalNoAll 是否查询终端下所有测量点
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	public List<PPDebugTotalMpBean> findTotalMpInfo(String tmnlAssetNo, Integer totalNo, Boolean totalNoAll)throws Exception;
	
	/**
	 * 总加组设置新建总加组
	 * @param List<PPDebugTotalSetBean> 总加组信息
	 * @param newFlag 是否新建
	 * @return 0 表示失败，1表示成功
	 * @throws 
	 */
	public int addOrUpdateTotalInfo(List<PPDebugTotalSetBean> bean, Boolean newFlag) throws Exception;
	
	/**
	 * 校验终端下某个总加组是否下发投入
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return record 终端下某个总加组已经投入的记录条数
	 * @throws 
	 */
	public int checkTmnlTotal(String tmnlAssetNo, Integer totalNo) throws Exception;
	
	/**
	 * 删除终端下某个总加组信息
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 0 表示失败，1表示成功
	 * @throws 
	 */
	public int dltTmnlTotal(String tmnlAssetNo, Integer totalNo) throws Exception;
}
