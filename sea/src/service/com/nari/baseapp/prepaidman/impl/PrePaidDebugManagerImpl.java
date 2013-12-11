package com.nari.baseapp.prepaidman.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.baseapp.prepaidman.PPDebugTmnlInfoBean;
import com.nari.baseapp.prepaidman.PPDebugTotalBean;
import com.nari.baseapp.prepaidman.PPDebugTotalMpBean;
import com.nari.baseapp.prepaidman.PPDebugTotalSetBean;
import com.nari.baseapp.prepaidman.PPDebugUserInfoBean;
import com.nari.baseapp.prepaidman.PPDebugUserStatusInfoBean;
import com.nari.baseapp.prepaidman.PPDebugWFeectrlFlowBean;
import com.nari.baseapp.prepaidman.PrePaidDebugJdbcDao;
import com.nari.baseapp.prepaidman.PrePaidDebugManager;
import com.nari.elementsdata.EDataTotal;
import com.nari.oranization.OOrgDao;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.SendStatusCodeUtil;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 预付费投入调试业务层实现类
 * @author 姜炜超
 */
public class PrePaidDebugManagerImpl implements PrePaidDebugManager {
	
	private PrePaidDebugJdbcDao prePaidDebugJdbcDao;
	private OOrgDao oOrgDao;
		
	public void setPrePaidDebugJdbcDao(PrePaidDebugJdbcDao prePaidDebugJdbcDao) {
		this.prePaidDebugJdbcDao = prePaidDebugJdbcDao;
	}

	public void setoOrgDao(OOrgDao oOrgDao) {
		this.oOrgDao = oOrgDao;
	}

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
			PSysUser user, long start, int limit) throws Exception{
		if(null == debugStatus || null == orgNo){
			return new Page<PPDebugUserInfoBean>();
		}
		//解析状态编码
		String debugStatusCode = "";
		if("1".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_SUCCESS;
		}else if("2".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_FAILED;
		}else if("3".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.TO_SENGIDNG_PARAM;
		}else if("4".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_DOING;
		}else{
			debugStatusCode = "*";
		}
		
		//解析供电单位
		String[] orgs = orgNo.split(",");
		if(null == orgs || 0 == orgs.length){
			return new Page<PPDebugUserInfoBean>();
		}
		
		try{
			return this.prePaidDebugJdbcDao.findUserInfo(control, buyOrder, startDate, endDate, 
					consNo, orgs, tmnlAddr, debugStatusCode, user, start, limit);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAIDDEBUG_INFOBYCOND);
		}
	}	
	
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
			PSysUser user)throws Exception{
		if(null == debugStatus || null == orgNo){
			return new ArrayList<PPDebugUserInfoBean>();
		}
		//解析状态编码
		String debugStatusCode = "";
		if("1".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_SUCCESS;
		}else if("2".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_FAILED;
		}else if("3".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.TO_SENGIDNG_PARAM;
		}else if("4".equals(debugStatus)){
			debugStatusCode = SendStatusCodeUtil.PARAM_SEND_DOING;
		}else{
			debugStatusCode = "*";
		}
		
		//解析供电单位
		String[] orgs = orgNo.split(",");
		if(null == orgs || 0 == orgs.length){
			return new ArrayList<PPDebugUserInfoBean>();
		}
		
		try{
			return this.prePaidDebugJdbcDao.findUserInfoByAll(control, buyOrder, startDate, endDate, 
					consNo, orgs, tmnlAddr, debugStatusCode, user);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAIDDEBUG_INFOBYCOND);
		}
	}
	/**
	 * 供电单位树。
	 * @param pSysUser 操作员信息
	 * @return
	 * @throws Exception 
	 */
	public List<TreeNodeChecked> findOrgTree(PSysUser pSysUser) throws Exception{
		if(null == pSysUser || null == pSysUser.getOrgNo() || null == pSysUser.getStaffNo()){
			return new ArrayList<TreeNodeChecked>();
		}
		OOrg oorg = this.oOrgDao.findById(pSysUser.getOrgNo());
		if(null == oorg || null == oorg.getOrgNo() || null == oorg.getOrgType()){
			return new ArrayList<TreeNodeChecked>();
		}
		
		List<OOrg> list = this.prePaidDebugJdbcDao.findOrgTree(pSysUser.getStaffNo(), oorg.getOrgNo(), oorg.getOrgType());
		List<TreeNodeChecked> roleInfo = new ArrayList<TreeNodeChecked>();
		for (int i = 0;  i < list.size(); i++) {
			TreeNodeChecked tn = new TreeNodeChecked();
			tn.setId(list.get(i).getOrgNo());
			tn.setText(list.get(i).getOrgName());
			tn.setLeaf(true);
			tn.setChecked(true);
			roleInfo.add(tn);
		}
		return roleInfo;
	}
	
	/**
	 * 根据购电单号查询预付费参数详细信息
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @return PPDebugUserInfoBean
	 */
	public PPDebugUserInfoBean findOrderParamInfo(Long feeCtrlId,String buyOrder) throws Exception{
		if(null == buyOrder){
			return new PPDebugUserInfoBean();
		}
		try{
			return this.prePaidDebugJdbcDao.findOrderParamInfo(feeCtrlId, buyOrder);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_PARAM_INFO);
		}
	}
	
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
			Integer buyValue, Integer alarmValue, String turnFlag, Short totalNo) throws Exception{
		if(null == buyOrder){
			return ;
		}
		try{
			this.prePaidDebugJdbcDao.updatePPDPara(feeCtrlId, buyOrder,jumpValue, buyValue, alarmValue, turnFlag, totalNo);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_UPDATE_PREPAID_PARAM);
		}
	}
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return PPDebugTmnlInfoBean
	 */
	public PPDebugTmnlInfoBean findTmnlInfo(String appNo, String terminalId, String meterId) throws Exception{
		if(null == appNo || null == terminalId || null == meterId){
			return new PPDebugTmnlInfoBean();
		}
		try{
			return this.prePaidDebugJdbcDao.findTmnlInfo(appNo, terminalId, meterId);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_TMNL_INFO);
		}
	}
	
	/**
	 * 查询预付费用户调试状态具体流程信息
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return List<PPDebugUserStatusInfoBean>
	 */
	public List<PPDebugUserStatusInfoBean> findUserDebugInfo(String appNo, String terminalId, String meterId) throws Exception{
		if(null == appNo || null == terminalId || null == meterId){
			return new ArrayList<PPDebugUserStatusInfoBean>();
		}
		try{
			return this.prePaidDebugJdbcDao.findUserDebugInfo(appNo, terminalId, meterId);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_USER_STATUS_INFO);
		}
	}
	
	/**
	 * 根据终端查询总加组列表
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<EDataTotal>
	 * @throws Exception 
	 */
	public List<EDataTotal> findParamTotalInfo(String tmnlAssetNo) throws Exception{
		if(null == tmnlAssetNo){
			return new ArrayList<EDataTotal>();
		}
		try{
			return this.prePaidDebugJdbcDao.findParamTotalInfo(tmnlAssetNo);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_TOTAL_INFO);
		}
	}
	
	/**
	 * 修改费控主表状态
	 * @Param feeCtrlId 费控id
	 * @param status 状态
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlStatus(Long feeCtrlId, String status) throws Exception{
		if(null == status){
			return;
		}
		try{
			prePaidDebugJdbcDao.updateWFeeCtrlStatus(feeCtrlId,status);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_UPDATE_PREPAID_WFEECTRL);
		}
	}
	
	/**
	 * 修改或新增费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void saveOrUpdateWFeeCtrlFlow(PPDebugWFeectrlFlowBean bean) throws Exception{
		if(null == bean){
			return;
		}
		try{
			prePaidDebugJdbcDao.saveOrUpdateWFeeCtrlFlow(bean);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_SAVEORUPDATE_PREPAID_WFEECTRLFLOW);
		}
	}
	
	/**
	 * 修改费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlFlowStatus(PPDebugWFeectrlFlowBean bean) throws Exception{
		if(null == bean){
			return;
		}
		try{
			prePaidDebugJdbcDao.updateWFeeCtrlFlowStatus(bean);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_UPDATE_PREPAID_WFEECTRLFLOW);
		}
	}
	
	/**
	 * 根据终端查询总加组详细列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalBean>
	 * @throws 
	 */
	public List<PPDebugTotalBean> findTotalInfo(String tmnlAssetNo)throws Exception{
		if(null == tmnlAssetNo){
			return new ArrayList<PPDebugTotalBean>();
		}
		List<PPDebugTotalBean> list = null;
		
		try{
			list = prePaidDebugJdbcDao.findTotalInfo(tmnlAssetNo);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_TOTAL_DETINFO);
		}
		
		if(null == list || 0 == list.size()){
			return new ArrayList<PPDebugTotalBean>();
		}
		
		return list;
	}
	
	/**
	 * 根据终端查询总加组下属测量点列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @param totalNoAll 是否查询终端下所有测量点
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	public List<PPDebugTotalMpBean> findTotalMpInfo(String tmnlAssetNo, Integer totalNo, Boolean totalNoAll)throws Exception{
		List<PPDebugTotalMpBean> mpList = null;
		
		try{
			if(totalNoAll){//新建调用
				if(null == tmnlAssetNo){
					return new ArrayList<PPDebugTotalMpBean>();
				}
				mpList = prePaidDebugJdbcDao.findTmnlMpInfo(tmnlAssetNo);
			}else{
				if(null == tmnlAssetNo || null == totalNo){
					return new ArrayList<PPDebugTotalMpBean>();
				}
			    mpList = prePaidDebugJdbcDao.findTotalMpInfo(tmnlAssetNo,totalNo.shortValue());
			}
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QRY_PREPAID_TOTAL_MP_INFO);
		}		
		
		if(null == mpList || 0 == mpList.size()){
			return new ArrayList<PPDebugTotalMpBean>();
		}
		
		return mpList;
	}	
	
	/**
	 * 总加组设置新建总加组
	 * @param List<PPDebugTotalSetBean> 总加组信息
	 * @param newFlag 是否新建
	 * @return 0表示失败，1表示成功
	 * @throws 
	 */
	public int addOrUpdateTotalInfo(List<PPDebugTotalSetBean> bean, Boolean newFlag) throws Exception{
		if(null == bean || bean.size() == 0){
			return 0;
		}
		if(newFlag){//新建则增加
			try{
				prePaidDebugJdbcDao.addEDataTotal(bean.get(0));
				for(int i = 0; i < bean.size(); i++){
					prePaidDebugJdbcDao.addTTmnlTotalInfo(bean.get(i));
				}
			}catch (Exception e) {
				return 0;//如果异常，返回0表示操作失败
			}
			return 1;//1表示操作成功
		}else{//修改，先删除，后新增，这样操作比较简单
			try{
				prePaidDebugJdbcDao.dltTTmnlTotalInfo(bean.get(0).getTmnlAssetNo(), bean.get(0).getTotalNo());
				for(int i = 0; i < bean.size(); i++){
					prePaidDebugJdbcDao.addTTmnlTotalInfo(bean.get(i));
				}
			}catch (Exception e) {
				return 0;//如果异常，返回0表示操作失败
			}
			return 1;//1表示操作成功
		}
	}
	
	/**
	 * 校验终端下某个总加组是否下发投入
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return record 终端下某个总加组已经投入的记录条数
	 * @throws 
	 */
	public int checkTmnlTotal(String tmnlAssetNo, Integer totalNo) throws Exception{
		if(null == tmnlAssetNo || null == totalNo){
			return 0;
		}
		return prePaidDebugJdbcDao.checkTmnlTotal(tmnlAssetNo,totalNo.shortValue());
	}
	
	/**
	 * 删除终端下某个总加组信息
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 0表示失败，1表示成功
	 * @throws 
	 */
	public int dltTmnlTotal(String tmnlAssetNo, Integer totalNo) throws Exception{
		if(null == tmnlAssetNo || null == totalNo){
			return 0;
		}
		try{
			prePaidDebugJdbcDao.updateEDataTotalInvalid(tmnlAssetNo,totalNo.shortValue());
			prePaidDebugJdbcDao.dltTTmnlTotalInfo(tmnlAssetNo, totalNo.shortValue());
		}catch (Exception e) {
			return 0;//如果异常，返回0表示操作失败
		}
		return 1;//1表示操作成功
	}
}
