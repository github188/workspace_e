package com.nari.advapp.vipconsman;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 重点用户定义action
 * 
 * @author ChunMingLi
 * @since 2010-6-2
 * 
 */
public class VipConsManagerAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean success = true;
	private long start = 0;
	private int limit = Constans.DEFAULT_PAGE_SIZE;
	private long totalCount;
//	private List<VipConsManager> vipConsUserList;
	//待定义重点用户列表
	private List<VipConsManagerDto> managerDtoList;
	//重点用户列表
	private List<VipConsManagerDto> vipUserDtoList;
	
	//重点用户新增列表
	private List<String> addVipPageList ;
	
	//重点用户删除页面参数列表
	private List<String> delVipPageList ;
	

	//单位类型
	private String orgType;
	//线路编号
    private String lineId;
    //供电单位编号
    private String orgNo;
    //用户编号
	private String consNo;
	//终端资产编号
	private String tmnlAssetNo;
	//节点类型
    private String nodeType;
    private String groupNo;
    private String subsId;
	
    //查询重点用户manager
    private VipConsDefineManager vipConsDefineManager;
    
    //新增及删除重点用户manager
    private VipConsUserManager  vipConsUserManager;
	private int flag;
    
    
	/**
	 *  the vipConsUserManager to set
	 * @param vipConsUserManager the vipConsUserManager to set
	 */
	public void setVipConsUserManager(VipConsUserManager vipConsUserManager) {
		this.vipConsUserManager = vipConsUserManager;
	}
	/**
	 *  the vipConsDefineManager to set
	 * @param vipConsDefineManager the vipConsDefineManager to set
	 */
	public void setVipConsDefineManager(VipConsDefineManager vipConsDefineManager) {
		this.vipConsDefineManager = vipConsDefineManager;
	}
	/**
	 * 左边树加载数据
	 * @return String 
	 * @throws Exception
	 */
	public String generalGridByTree() throws Exception {
		logger.debug("左边树加载数据 :generalGridByTree() start");
		if(null == nodeType || "".equals(nodeType)){
			this.success=false;
    		return SUCCESS;
    	}
		Page<VipConsManagerDto> vipPage = null;
		try{
			PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
			if("usr".equals(this.nodeType)){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListByTmnlAssetNo(tmnlAssetNo, start, limit);
	    	}
			else if("org".equals(this.nodeType)){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListByOrgNo(orgNo, orgType, pSysUser, start, limit);
	    	}
			else if("line".equals(this.nodeType)){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListByLine(lineId, pSysUser, start, limit);
	    	}
			else if("cgp".equals(this.nodeType) ){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListByCommonGroupNo(groupNo, start, limit);
			}
			else if("ugp".equals(this.nodeType)){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListByCtrlGroupNo(groupNo, start, limit);
			}
			else if("sub".equals(this.nodeType)){
				vipPage = this.vipConsDefineManager.queryVipConsDefineListBySub(subsId, pSysUser, start, limit);
	    	}
			if (null != vipPage) {
				if(this.managerDtoList != null){
					managerDtoList.clear();
				}
				this.managerDtoList = vipPage.getResult();
				totalCount = vipPage.getTotalCount();
			}
			logger.debug("左边树加载数据 :generalGridByTree() end");
			return SUCCESS;
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}		
	}
	/**
	 * 默认根据StaffNo查询vip用户
	 * @return
	 * @throws Exception
	 */
	public String findVipCons() throws Exception {
		logger.debug("findVipCons() start");
		PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
		if (null == pSysUser) {
			this.success = false;
			return SUCCESS;
		}
		try {
			Page<VipConsManagerDto> page = null;
			String staffNo = pSysUser.getStaffNo();
			page = vipConsDefineManager.findVipConsListByStaffNo(staffNo,
					start, limit);
			if (null != page) {
				this.vipUserDtoList = page.getResult();
				totalCount = page.getTotalCount();
			}
			logger.debug("findVipCons() end");
			return SUCCESS;
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	/**
	 * 根据左边树查询出来选择用户保存为重点用户
	 * @return string
	 * @throws Exception
	 */
	public String saveVipCons()throws Exception{
		logger.debug("saveVipCons() start");
		if(addVipPageList == null || addVipPageList.size() < 1){
			this.success=false;
    		return SUCCESS;
		}
		try {
			//操作员信息
			PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
			
			List<VipConsManager> vipConsUserList = new ArrayList<VipConsManager>();
			for(String vipStr : addVipPageList ){
				String[] vipcons = vipStr.split(",");
				//设置主键
				VipConsManagerId vipConsManagerId = new VipConsManagerId();
				vipConsManagerId.setOrgNo(vipcons[0]);
				vipConsManagerId.setConsId(Long.parseLong(vipcons[1]));
				
				VipConsManager vipUser = new VipConsManager();
				vipUser.setId(vipConsManagerId);
				vipUser.setConsNo(vipcons[2]);
				vipUser.setMonitorLevel(vipcons[3]);
				vipUser.setStaffNo(pSysUser.getStaffNo());
				vipUser.setConsSort(1);
				vipUser.setMonitorFlag(1);
				vipConsUserList.add(vipUser);
				}
			if(vipConsUserList.size() < 1){
				this.success=false;
	    		return SUCCESS;
			}else {
				vipConsUserManager.saveVipUser(vipConsUserList);
				this.flag = 1;
			}
			logger.debug("saveVipCons() end");	
		return SUCCESS;
		
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		
	}
	
	/**
	 * 删除重点用户
	 * @return string
	 * @throws Exception
	 */
	public String deleteVipCons()throws Exception{
		logger.debug("deleteVipCons() start");
		if(delVipPageList == null || delVipPageList.size() < 1){
			this.success=false;
    		return SUCCESS;
		}
		try{
			PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
	    		return SUCCESS;	
			}
			String staffNo=pSysUser.getStaffNo();
			List<VipConsManager> delVipUserList = new ArrayList<VipConsManager>();
			for(String vipStr : delVipPageList ){
				String[] vipcons = vipStr.split(",");
				//设置主键
				VipConsManagerId vipConsManagerId = new VipConsManagerId();
				vipConsManagerId.setOrgNo(vipcons[0]);
				vipConsManagerId.setConsId(Long.parseLong(vipcons[1]));
				
				VipConsManager vipUser = new VipConsManager();
				vipUser.setId(vipConsManagerId);
				vipUser.setStaffNo(staffNo);
				delVipUserList.add(vipUser);
				}
			if(delVipUserList.size() < 1){
				this.success=false;
			}else{
				this.vipConsUserManager.deleteVipUser(delVipUserList);
				this.flag = 1;
			}
			logger.debug("deleteVipCons() end");
			return SUCCESS;	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
//	/**
//	 *  the vipConsUserList
//	 * @return the vipConsUserList
//	 */
//	public List<VipConsManager> getVipConsUserList() {
//		return vipConsUserList;
//	}
//	/**
//	 *  the vipConsUserList to set
//	 * @param vipConsUserList the vipConsUserList to set
//	 */
//	public void setVipConsUserList(List<VipConsManager> vipConsUserList) {
//		this.vipConsUserList = vipConsUserList;
//	}
	
	/**
	 * the success
	 * 
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * the success to set
	 * 
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * the start
	 * 
	 * @return the start
	 */
	public long getStart() {
		return start;
	}

	/**
	 * the start to set
	 * 
	 * @param start
	 *            the start to set
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * the limit
	 * 
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * the limit to set
	 * 
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * the totalCount
	 * 
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}

	/**
	 * the totalCount to set
	 * 
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 *  the managerDtoList
	 * @return the managerDtoList
	 */
	public List<VipConsManagerDto> getManagerDtoList() {
		return managerDtoList;
	}
	/**
	 *  the managerDtoList to set
	 * @param managerDtoList the managerDtoList to set
	 */
	public void setManagerDtoList(List<VipConsManagerDto> managerDtoList) {
		this.managerDtoList = managerDtoList;
	}
	/**
	 *  the orgType
	 * @return the orgType
	 */
	public String getOrgType() {
		return orgType;
	}
	/**
	 *  the orgType to set
	 * @param orgType the orgType to set
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	/**
	 *  the lineId
	 * @return the lineId
	 */
	public String getLineId() {
		return lineId;
	}
	/**
	 *  the lineId to set
	 * @param lineId the lineId to set
	 */
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	/**
	 *  the orgNo
	 * @return the orgNo
	 */
	public String getOrgNo() {
		return orgNo;
	}
	/**
	 *  the orgNo to set
	 * @param orgNo the orgNo to set
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	/**
	 *  the consNo
	 * @return the consNo
	 */
	public String getConsNo() {
		return consNo;
	}
	/**
	 *  the consNo to set
	 * @param consNo the consNo to set
	 */
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	/**
	 *  the tmnlAssetNo
	 * @return the tmnlAssetNo
	 */
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	/**
	 *  the tmnlAssetNo to set
	 * @param tmnlAssetNo the tmnlAssetNo to set
	 */
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	/**
	 *  the nodeType
	 * @return the nodeType
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 *  the nodeType to set
	 * @param nodeType the nodeType to set
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 *  the groupNo
	 * @return the groupNo
	 */
	public String getGroupNo() {
		return groupNo;
	}
	/**
	 *  the groupNo to set
	 * @param groupNo the groupNo to set
	 */
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	/**
	 *  the subsId
	 * @return the subsId
	 */
	public String getSubsId() {
		return subsId;
	}
	/**
	 *  the subsId to set
	 * @param subsId the subsId to set
	 */
	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}
	/**
	 *  the vipUserDtoList
	 * @return the vipUserDtoList
	 */
	public List<VipConsManagerDto> getVipUserDtoList() {
		return vipUserDtoList;
	}
	/**
	 *  the vipUserDtoList to set
	 * @param vipUserDtoList the vipUserDtoList to set
	 */
	public void setVipUserDtoList(List<VipConsManagerDto> vipUserDtoList) {
		this.vipUserDtoList = vipUserDtoList;
	}
	/**
	 *  the addVipPageList
	 * @return the addVipPageList
	 */
	public List<String> getAddVipPageList() {
		return addVipPageList;
	}
	/**
	 *  the addVipPageList to set
	 * @param addVipPageList the addVipPageList to set
	 */
	public void setAddVipPageList(List<String> addVipPageList) {
		this.addVipPageList = addVipPageList;
	}
	/**
	 *  the delVipPageList
	 * @return the delVipPageList
	 */
	public List<String> getDelVipPageList() {
		return delVipPageList;
	}
	/**
	 *  the delVipPageList to set
	 * @param delVipPageList the delVipPageList to set
	 */
	public void setDelVipPageList(List<String> delVipPageList) {
		this.delVipPageList = delVipPageList;
	}
	/**
	 *  the flag
	 * @return the flag
	 */
	public int getFlag() {
		return flag;
	}
	/**
	 *  the flag to set
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	

}
