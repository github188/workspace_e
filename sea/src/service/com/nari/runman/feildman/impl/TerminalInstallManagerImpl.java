package com.nari.runman.feildman.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.xfire.client.Client;
import org.springframework.dao.DataAccessException;

import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.TerminalInstallDao;
import com.nari.runman.feildman.TerminalInstallManager;
import com.nari.support.Page;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.ITmnlTask;
import com.nari.terminalparam.MeterChgInfoBean;
import com.nari.terminalparam.MeterCommBean;
import com.nari.terminalparam.MeterMaintainInfoBean;
import com.nari.terminalparam.TerminalChgInfoBean;
import com.nari.terminalparam.TerminalDebugInfoBean;
import com.nari.terminalparam.TerminalInstallBean;
import com.nari.terminalparam.TerminalInstallDetailBean;
import com.nari.terminalparam.TerminalRmvAndChgBean;
import com.nari.util.NodeTypeUtils;
import com.nari.util.StatusCodeUtils;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;
import com.nari.util.ws.ClientAuthenticationHandler;
import com.nari.util.ws.XMLSwitchUtil;
/**
 * 终端安装业务层实现类
 * @author 姜炜超
 */
public class TerminalInstallManagerImpl implements TerminalInstallManager{
    private TerminalInstallDao terminalInstallDao;
    private static final String newTmnlTaskType = "03";
    private static final String oldTmnlStatus= "拆除";

	public void setTerminalInstallDao(TerminalInstallDao terminalInstallDao) {
		this.terminalInstallDao = terminalInstallDao;
	}
    
	/**
	 * 根据供电单位和查询时间，统计终端安装信息
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @param interType 接口类别
	 * @return List<TerminalInstallBean>
	 */
    public List<TerminalInstallBean> findTmnlInstallInfo(PSysUser user, String startDate, 
    		String endDate, String interType)  throws Exception{
    	List<TerminalInstallBean> list = new ArrayList<TerminalInstallBean>();//定义返回结果列表
    	if(null == user || null == user.getOrgNo() || null == user.getStaffNo() || null == startDate 
    			|| null == endDate || null == interType){
    		return list; 
    	}
    	try {
    		OOrg org = terminalInstallDao.findOrgInfo(user.getOrgNo());//获取供电单位信息
        	
        	if(null == org.getOrgType() || "".equals(org.getOrgType())){
        		return list; 
        	}
        	//如果供电单位是级别是02或03，则先查询其下一级别的子单位，然后对每个子单位进行查询统计
        	//接着进行终端数据的转换，如果是04的话，直接调用查询结果，而如果是05或06操作员，则把其父
        	//供电单位编号传给后台进行数据查询
        	if(NodeTypeUtils.NODETYPE_ORG02.equals(org.getOrgType())){
        		//查询子单位
        		List<OOrg> subList = terminalInstallDao.findSubOrgInfo(user.getStaffNo(), NodeTypeUtils.NODETYPE_ORG03);
        	    if(null != subList && 0 < subList.size()){
        	    	OOrg subOrg = null;
        	    	for(int i =0; i < subList.size(); i++){//每个子单位查询
        	    		subOrg = (OOrg)subList.get(i);
        	    		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlInstallInfo(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		Long succNum = terminalInstallDao.findTmnlUserOrMeterCount(subOrg.getOrgNo(), startDate, endDate, 
        	    				interType, StatusCodeUtils.DEBUG_STATUE_SUCCESS);
        	    		Long failNum = terminalInstallDao.findTmnlUserOrMeterCount(subOrg.getOrgNo(), startDate, endDate, 
        	    				interType, StatusCodeUtils.DEBUG_STATUE_FAILED);
        	    		getTmnlData(list, tmnlList, subOrg.getOrgName(), subOrg.getOrgNo(), succNum, failNum);
        	    	}
        	    }
        	}else if(NodeTypeUtils.NODETYPE_ORG03.equals(org.getOrgType())){
        		//查询子单位
        		List<OOrg> subList = terminalInstallDao.findSubOrgInfo(user.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04);
        		if(null != subList && 0 < subList.size()){
        	    	OOrg subOrg = null;
        	    	for(int i =0; i < subList.size(); i++){//每个子单位查询
        	    		subOrg = (OOrg)subList.get(i);
        	    		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlInstallInfo(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		Long succNum = terminalInstallDao.findTmnlUserOrMeterCount(subOrg.getOrgNo(), startDate, endDate, 
        	    				interType, StatusCodeUtils.DEBUG_STATUE_SUCCESS);
        	    		Long failNum = terminalInstallDao.findTmnlUserOrMeterCount(subOrg.getOrgNo(), startDate, endDate, 
        	    				interType, StatusCodeUtils.DEBUG_STATUE_FAILED);
        	    		getTmnlData(list, tmnlList, subOrg.getOrgName(), subOrg.getOrgNo(), succNum, failNum);
        	    	}
        	    }
        	}else if(NodeTypeUtils.NODETYPE_ORG04.equals(org.getOrgType())){
        		//直接查询
        		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlInstallInfo(user.getOrgNo(), startDate, 
        				endDate, interType);
        		Long succNum = terminalInstallDao.findTmnlUserOrMeterCount(user.getOrgNo(), startDate, endDate, 
	    				interType, StatusCodeUtils.DEBUG_STATUE_SUCCESS);
	    		Long failNum = terminalInstallDao.findTmnlUserOrMeterCount(user.getOrgNo(), startDate, endDate, 
	    				interType, StatusCodeUtils.DEBUG_STATUE_FAILED);
        		getTmnlData(list, tmnlList, org.getOrgName(), user.getOrgNo(), succNum, failNum);
        	}else if(NodeTypeUtils.NODETYPE_ORG05.equals(org.getOrgType()) || 
        			    NodeTypeUtils.NODETYPE_ORG06.equals(org.getOrgType())){
        		//传父供电单位编号
        		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlInstallInfo(org.getPOrgNo(), startDate, 
        				endDate, interType);
        		Long succNum = terminalInstallDao.findTmnlUserOrMeterCount(org.getPOrgNo(), startDate, endDate, 
	    				interType, StatusCodeUtils.DEBUG_STATUE_SUCCESS);
	    		Long failNum = terminalInstallDao.findTmnlUserOrMeterCount(org.getPOrgNo(), startDate, endDate, 
	    				interType, StatusCodeUtils.DEBUG_STATUE_FAILED);
        		OOrg orgInfo = terminalInstallDao.findOrgInfo(org.getPOrgNo());//获取父供电单位信息
        		getTmnlData(list, tmnlList, orgInfo.getOrgName(), org.getPOrgNo(), succNum, failNum);
        	}else{
        		return list;
        	}
    	} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALL);
		}
    	return list;
    }
    
    /**
	 * 根据供电单位和查询时间，统计终端拆换信息
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @param interType 接口类别
	 * @return List<TerminalRmvAndChgBean>
	 */
    public List<TerminalRmvAndChgBean> findTmnlRmvAndChgInfo(PSysUser user, String startDate, 
    		String endDate, String interType)  throws Exception{
    	List<TerminalRmvAndChgBean> list = new ArrayList<TerminalRmvAndChgBean>();//定义返回结果列表
    	if(null == user || null == user.getOrgNo() || null == user.getStaffNo() || null == startDate 
    			|| null == endDate || null == interType){
    		return list; 
    	}
    	try {
    		OOrg org = terminalInstallDao.findOrgInfo(user.getOrgNo());//获取供电单位信息
        	
        	if(null == org.getOrgType() || "".equals(org.getOrgType())){
        		return list; 
        	}
        	//如果供电单位是级别是02或03，则先查询其下一级别的子单位，然后对每个子单位进行查询统计
        	//接着进行终端数据的转换，如果是04的话，直接调用查询结果，而如果是05或06操作员，则把其父
        	//供电单位编号传给后台进行数据查询
        	if(NodeTypeUtils.NODETYPE_ORG02.equals(org.getOrgType())){
        		//查询子单位
        		List<OOrg> subList = terminalInstallDao.findSubOrgInfo(user.getStaffNo(), NodeTypeUtils.NODETYPE_ORG03);
        	    if(null != subList && 0 < subList.size()){
        	    	OOrg subOrg = null;
        	    	for(int i =0; i < subList.size(); i++){//每个子单位查询
        	    		subOrg = (OOrg)subList.get(i);
        	    		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlRmvAndChgInfo(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		List<ITmnlTask> tmnlList1 = terminalInstallDao.findTmnlRmvAndChgInfo1(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		List<ITmnlTask> tmnlList2 = terminalInstallDao.findTmnlRmvAndChgInfo2(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		getTmnlRmvAndChgData(list, tmnlList, tmnlList1, tmnlList2, subOrg.getOrgName(), subOrg.getOrgNo());
        	    	}
        	    }
        	}else if(NodeTypeUtils.NODETYPE_ORG03.equals(org.getOrgType())){
        		//查询子单位
        		List<OOrg> subList = terminalInstallDao.findSubOrgInfo(user.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04);
        		if(null != subList && 0 < subList.size()){
        	    	OOrg subOrg = null;
        	    	for(int i =0; i < subList.size(); i++){//每个子单位查询
        	    		subOrg = (OOrg)subList.get(i);
        	    		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlRmvAndChgInfo(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		List<ITmnlTask> tmnlList1 = terminalInstallDao.findTmnlRmvAndChgInfo1(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		List<ITmnlTask> tmnlList2 = terminalInstallDao.findTmnlRmvAndChgInfo2(subOrg.getOrgNo(), 
        	    				startDate, endDate, interType);
        	    		getTmnlRmvAndChgData(list, tmnlList, tmnlList1, tmnlList2, subOrg.getOrgName(), subOrg.getOrgNo());
        	    	}
        	    }
        	}else if(NodeTypeUtils.NODETYPE_ORG04.equals(org.getOrgType())){
        		//直接查询
        		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlRmvAndChgInfo(user.getOrgNo(), startDate, 
        				endDate, interType);
        		List<ITmnlTask> tmnlList1 = terminalInstallDao.findTmnlRmvAndChgInfo1(user.getOrgNo(), startDate, 
        				endDate, interType);
        		List<ITmnlTask> tmnlList2 = terminalInstallDao.findTmnlRmvAndChgInfo2(user.getOrgNo(), startDate, 
        				endDate, interType);
        		getTmnlRmvAndChgData(list, tmnlList, tmnlList1, tmnlList2, org.getOrgName(), user.getOrgNo());
        	}else if(NodeTypeUtils.NODETYPE_ORG05.equals(org.getOrgType()) || 
        			    NodeTypeUtils.NODETYPE_ORG06.equals(org.getOrgType())){
        		//传父供电单位编号
        		List<ITmnlTask> tmnlList = terminalInstallDao.findTmnlRmvAndChgInfo(org.getPOrgNo(), startDate, 
        				endDate, interType);
        		List<ITmnlTask> tmnlList1 = terminalInstallDao.findTmnlRmvAndChgInfo1(org.getPOrgNo(), startDate, 
        				endDate, interType);
        		List<ITmnlTask> tmnlList2 = terminalInstallDao.findTmnlRmvAndChgInfo2(org.getPOrgNo(), startDate, 
        				endDate, interType);
        		OOrg orgInfo = terminalInstallDao.findOrgInfo(org.getPOrgNo());//获取父供电单位信息
        		getTmnlRmvAndChgData(list, tmnlList, tmnlList1, tmnlList2, orgInfo.getOrgName(), org.getPOrgNo());
        	}else{
        		return list;
        	}
    	} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALL);
		}
    	return list;
    }
    
    /**
	 * 把查询结果进行转换，针对终端安装
	 * @param list
	 * @param tmnlList
	 * @param orgName
	 * @param orgNo
	 * @return 
	 */
    private void getTmnlData(List<TerminalInstallBean> list, List<ITmnlTask> tmnlList, 
    		String orgName, String orgNo, Long succNum, Long failNum){
    	if(null == list || null == tmnlList){
    		return;
    	}
    	//对查询结果进行循环处理
    	if(null != tmnlList && 0 < tmnlList.size()){
			TerminalInstallBean bean = new TerminalInstallBean();
			Long total = 0L;
			for(int i = 0 ; i < tmnlList.size(); i++){
				ITmnlTask tmnl = (ITmnlTask)tmnlList.get(i);
				if(null == tmnl.getStatusCode()){
					continue;
				}
				//对每个终端任务对象，进行类型判别
				if(StatusCodeUtils.DEBUG_STATUE_INIT.equals(tmnl.getStatusCode())){
					bean.setInitNum(tmnl.getNum().longValue());
					total = total +tmnl.getNum().longValue();
				}else if(StatusCodeUtils.DEBUG_STATUE_SUCCESS.equals(tmnl.getStatusCode())){
					bean.setSuccNum(tmnl.getNum().longValue());
					total = total +tmnl.getNum().longValue();
				}else if(StatusCodeUtils.DEBUG_STATUE_FAILED.equals(tmnl.getStatusCode())){
					bean.setFailedNum(tmnl.getNum().longValue());
					total = total +tmnl.getNum().longValue();
				}else if(StatusCodeUtils.DEBUG_STATUE_ING.equals(tmnl.getStatusCode())){
					bean.setDealingNum(tmnl.getNum().longValue());
					total = total +tmnl.getNum().longValue();
				}else if(StatusCodeUtils.DEBUG_STATUE_SYN_FAILED.equals(tmnl.getStatusCode())){
					bean.setSynFailedNum(tmnl.getNum().longValue());
					total = total +tmnl.getNum().longValue();
				}else{
					continue;
				}
			}
			if(0 != total){
			    bean.setTotalNum(total);
			}
			bean.setOrgNo(orgNo);
			bean.setOrgName(orgName);
			bean.setSuccCountNum(succNum);
			bean.setFailCountNum(failNum);
			list.add(bean);
    	}
    }
    
    /**
	 * 把查询结果进行转换，针对终端拆换
	 * @param list
	 * @param tmnlList
	 * @param orgName
	 * @param orgNo
	 * @return 
	 */
    private void getTmnlRmvAndChgData(List<TerminalRmvAndChgBean> list, List<ITmnlTask> tmnlList, 
    		List<ITmnlTask> tmnlList1, List<ITmnlTask> tmnlList2, String orgName, String orgNo){
    	if(null == list){
    		return;
    	}
    	
    	TerminalRmvAndChgBean bean = new TerminalRmvAndChgBean();
    	
    	//对查询结果进行循环处理
    	if(null != tmnlList && 0 < tmnlList.size()){
			for(int i = 0 ; i < tmnlList.size(); i++){
				ITmnlTask tmnl = (ITmnlTask)tmnlList.get(i);
				if(null == tmnl.getStatusCode() || "".equals(tmnl.getStatusCode())){
					continue;
				}
				//对每个终端任务对象，进行类型判别
				if(StatusCodeUtils.TMNL_TASK_TYPE1.equals(tmnl.getStatusCode())){
					bean.setNewTmnlNum(tmnl.getNum().longValue());
				}else if(StatusCodeUtils.TMNL_TASK_TYPE2.equals(tmnl.getStatusCode())){
					bean.setRemoveTmnlNum(tmnl.getNum().longValue());
				}else if(StatusCodeUtils.TMNL_TASK_TYPE3.equals(tmnl.getStatusCode())){
					bean.setChgTmnlNum(tmnl.getNum().longValue());
				}else if(StatusCodeUtils.TMNL_TASK_TYPE6.equals(tmnl.getStatusCode())){
					bean.setMendTmnlNum(tmnl.getNum().longValue());
				}else{
					continue;
				}
			}
    	}
    	
    	//对查询结果进行循环处理
    	if(null != tmnlList1 && 0 < tmnlList1.size()){
			for(int i = 0 ; i < tmnlList1.size(); i++){
				ITmnlTask tmnl = (ITmnlTask)tmnlList1.get(i);
				if(null == tmnl.getStatusCode() || "".equals(tmnl.getStatusCode())){
					continue;
				}
				//对每个终端任务对象，进行类型判别
				if(StatusCodeUtils.TMNL_METER_FLAG2.equals(tmnl.getStatusCode())){
					bean.setNewTmnlNum(tmnl.getNum().longValue());
				}else{
					continue;
				}
			}
    	}
    	
    	//对查询结果进行循环处理
    	if(null != tmnlList2 && 0 < tmnlList2.size()){
			for(int i = 0 ; i < tmnlList2.size(); i++){
				ITmnlTask tmnl = (ITmnlTask)tmnlList2.get(i);
				if(null == tmnl.getStatusCode() || "".equals(tmnl.getStatusCode())){
					continue;
				}
				//对每个终端任务对象，进行类型判别
				if(StatusCodeUtils.TMNL_CONS_CHG_TYPE2.equals(tmnl.getStatusCode())){
					bean.setDelUserNum(tmnl.getNum().longValue());
				}else if(StatusCodeUtils.TMNL_CONS_CHG_TYPE3.equals(tmnl.getStatusCode())){
					bean.setSuspUserNum(tmnl.getNum().longValue());
				}else{
					continue;
				}
			}
    	}
    	bean.setOrgNo(orgNo);
		bean.setOrgName(orgName);
		list.add(bean);
    }
    
    /**
	 * 根据供电单位，时间等信息查询该单位下所有用户的终端装接调试信息，弹出tab页调用
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param consNo
	 * @param debugStatus
	 * @param appNo
	 * @param interType
	 * @return Page<TerminalInstallDetailBean>
	 */
	public List<TerminalInstallDetailBean>  findConsTmnlDebugInfo(String orgNo, String startDate,
			String endDate, String consNo, String debugStatus,String appNo, String interType) throws Exception{
		List<TerminalInstallDetailBean> list = null;
		try {
			list = terminalInstallDao.findConsTmnlDebugInfo(orgNo, startDate, endDate, consNo, 
					debugStatus, appNo, interType);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALLDETAIL);
		}
		return list;
	}
	
	/**
	 * 根据申请单号查询终端调试信息
	 * @param appNo
	 * @param consType
	 * @param start
	 * @param limit
	 * @return Page<TerminalDebugInfoBean>
	 */
	public Page<TerminalDebugInfoBean>  findTmnlDebugInfo(String appNo, Integer consType,long start, int limit) throws Exception{
		Page<TerminalDebugInfoBean> psc = null;
		try {
			psc = terminalInstallDao.findTmnlDebugInfo(appNo, consType,start, limit);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALLDEBUG);
		}
		return psc;
	}
	
	/**
	 * 查询规约信息
	 * @return List<VwProtocolCode>
	 */
	public List<VwProtocolCode>  findProtocalCodeInfo() throws Exception{
		List<VwProtocolCode> list = null;
		try {
			list = terminalInstallDao.findProtocalCodeInfo();
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALLPROTOCOL);
		}
		if(null == list){
			list = new ArrayList<VwProtocolCode>();
		}
		return list;
	}
	
	/**
	 * 更新终端信息
	 * @param tmnlId 
	 * @param protocolCode 
	 * @param sendUpMode 
	 * @throws Exception
	 */
	public void saveTmnlInfo(String tmnlId, String protocolCode, String sendUpMode) throws Exception{
		try {
			terminalInstallDao.saveTmnlInfo(tmnlId, protocolCode, sendUpMode);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALLMAINTAIN);
		}
	}
	
	/**
	 * 加载终端变更信息
	 * @param appNo
	 * @return List<TerminalChgInfoBean>
	 */
	public List<TerminalChgInfoBean>  findTmnlChgInfo(String appNo) throws Exception{
		List<TerminalChgInfoBean> list = new ArrayList<TerminalChgInfoBean>();//定义返回结果列表
		TerminalChgInfoBean bean = null;
		try {
			List<TerminalChgInfoBean> retlist = terminalInstallDao.findTmnlChgInfo(appNo);
			TerminalChgInfoBean retBean = null;
			if(null != retlist && 0 < retlist.size()){
				retBean = (TerminalChgInfoBean)retlist.get(0);//如果查询结果不为空，则获取第一条记录
			}else{
				return list;
			}
			//如果是新增，则要判断进行两次处理，分别判断旧和新终端，如果是旧终端，则要赋予其状态为"拆除"
			if(newTmnlTaskType.equals(retBean.getTmnlTaskType())){
				if(null != retBean.getTmnlAddr()){
					bean = new TerminalChgInfoBean();
					bean.setTmnlAddr(retBean.getTmnlAddr());
					if(null != retBean.getTmnlTaskTypeName()){
						bean.setStatus(oldTmnlStatus);
					}
					list.add(bean);
				}
				if(null != retBean.getNewTmnlAddr()){
					bean = new TerminalChgInfoBean();
					bean.setTmnlAddr(retBean.getNewTmnlAddr());
					if(null != retBean.getTmnlTaskTypeName() && 2 <= retBean.getTmnlTaskTypeName().length()){
						bean.setStatus(retBean.getTmnlTaskTypeName().substring(2));
					}
					list.add(bean);
				}
			}else{
				if(null != retBean.getTmnlAddr()){
					bean = new TerminalChgInfoBean();
					bean.setTmnlAddr(retBean.getTmnlAddr());
					if(null != retBean.getTmnlTaskTypeName() && 2 <= retBean.getTmnlTaskTypeName().length()){
						bean.setStatus(retBean.getTmnlTaskTypeName().substring(2));
					}
					list.add(bean);
				}
			}
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_TMNLINSTALLCHG);
		}
		return list;
	}
	
	/**
	 * 加载电能表变更信息
	 * @param appNo
	 * @return List<MeterChgInfoBean>
	 */
	public List<MeterChgInfoBean>  findMeterChgInfo(String appNo) throws Exception{
		List<MeterChgInfoBean> list = null;
		try {
			list = terminalInstallDao.findMeterChgInfo(appNo);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_METERINSTALLCHG);
		}
		if(null == list){
			list  = new ArrayList<MeterChgInfoBean>();
		}
		return list;
	}

	/**
	 * 手工触发终端调试
	 * @param appList
	 * @return flag
	 * @throws ServiceException 
	 */
	@SuppressWarnings("unchecked")
	public int handEvent(List<String> appList) throws ServiceException {
		try {
			List list=new ArrayList();
			for(int i =0 ;i < appList.size(); i++){
				HashMap<String,String> hm=new HashMap<String, String>();
				hm.put("APP_NO", appList.get(i));
				list.add(hm);
	    	}
			String	url=findConfig("SEA_URL");
			String	username=findConfig("SEA_USERNAME");
			String	password=findConfig("SEA_PASSWORD");
			XMLSwitchUtil xsu=new XMLSwitchUtil();
//			String url = PropertiesUtils.getProperty("url");
//			String username = PropertiesUtils
//					.getProperty("username");
//			String password = PropertiesUtils
//					.getProperty("password");
			Client client = new Client(new URL(url));
			client.addOutHandler(new ClientAuthenticationHandler(username,
					password));
		
			Object result[] = client.invoke("WS_ZZ_HANDEVENT", new Object[] {xsu.switchListToXML(list)});
			String resultXml=result[0].toString();
//			List rlist=xsu.switchXMLToList(resultXml);
			return 1;
		}catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	private String findConfig(String no) throws ServiceException{
		SqlData sd=SqlData.getOne();
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			return	 (String) tree.getJdbcTemplate().queryForObject(sd.archives_config,new Object[]{no}, String.class);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 加载电能表维护信息
	 * @param appNo 申请单号
	 * @param succFlag 是否成功
	 * @return List<MeterMaintainInfoBean>
	 */
	public Page<MeterMaintainInfoBean>  findMeterMaintainInfo(String appNo, int succFlag, long start, int limit)throws Exception{
		try {
			return terminalInstallDao.findMeterMaintainInfo(appNo, succFlag,start,limit);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_METERMAINTAINCHG);
		}
	}
	
	/**
	 * 加载电能表通讯协议
	 * @return List<MeterCommBean>
	 */
	public List<MeterCommBean> findMeterCommInfo()throws Exception{
		List<MeterCommBean> list = null;
		try {
			list = terminalInstallDao.findMeterCommInfo();
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_METERCOMMINFO);
		}
		if(null == list){
			list  = new ArrayList<MeterCommBean>();
		}
		return list;
	}

	@Override
	public int delTmnlDebugInfo(String tmnlAddr, String flowNode) throws Exception {
		try {
			return terminalInstallDao.delTmnlDebugInfo(tmnlAddr, flowNode);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.RUE_METERCOMMINFO);
		}
	}
	@Override
	public void updateResetState(PSysUser user, String[] appNos) throws ServiceException {
		try {
			terminalInstallDao.resetState(user, appNos);
		} catch (Exception e) {
		throw new ServiceException(e,e.getMessage());
		}
	}
}
