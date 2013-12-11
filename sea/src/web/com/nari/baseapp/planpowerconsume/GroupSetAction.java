package com.nari.baseapp.planpowerconsume;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.basicdata.VwCtrlSchemeType;
import com.nari.basicdata.VwCtrlSchemeTypeId;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TTmnlGroupUserDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.terminalparam.TTmnlGroupDetailDto;
import com.nari.terminalparam.TTmnlGroupDto;


/**
 * @author 姜海辉
 *
 * @创建时间:2009-01-09 下午14:39:24
 *
 * @类描述: 群组管理
 *	
 */
public class GroupSetAction extends BaseAction {

	public boolean success = true;


	private String orgNo;          //供电单位编号
	private String consNo;         //用户编号
	private String tmnlAssetNo;    //终端资产编号
    private String nodeType;       //节点类型
    private String groupNo;        //群组编号
    private String groupName;      //群组名称
   // private Short  isShare;        //是否共享
    private Short  validDays;      //有效日期 
    
    //private List<String> creatorList;   //创建者列表
    private List<String> groupNoList;   //群组编号列表
    private Date  startDate;      
    private Date  finishDate;
    private String groupTypeCode;       //群组类型
	private String ctrlSchemeType;      //控制方式
    private String groupType;           //群组类别
    private String orgType;             //单位类型
    private String lineId;			    //线路编号
    private String subsId;			    //变电站标识
    private Integer FLAG;
    List<String>  groupTmnlList;        //选择终端列表
    private long groupTotalCount;        //群组数量
    private long groupDetailTotalCount;   //群组明细数量
    private long start = 0;
	private long limit = Constans.DEFAULT_PAGE_SIZE;
    private List<TTmnlGroupUserDto> tTmnlGroupUserDtoList;
    private List<VwCtrlSchemeTypeId>  ctrlSchemeTypeIdList; 
    private List<TTmnlGroupDto> tTmnlGroupDtoList;
    private List<TTmnlGroupDetailDto> tTmnlGroupDetailDtoList;
    
    private ITTmnlGroupUserManager iTTmnlGroupUserManager; 
	
	public void setiTTmnlGroupUserManager(
			ITTmnlGroupUserManager iTTmnlGroupUserManager) {
		this.iTTmnlGroupUserManager = iTTmnlGroupUserManager;
	}
	private  IGroupSetManager iGroupSetManager;
	public void setiGroupSetManager(IGroupSetManager iGroupSetManager) {
		this.iGroupSetManager = iGroupSetManager;
	}
	private IVwCtrlSchemeTypeManager  iVwCtrlSchemeTypeManager;
	public void setiVwCtrlSchemeTypeManager(
			IVwCtrlSchemeTypeManager iVwCtrlSchemeTypeManager) {
		this.iVwCtrlSchemeTypeManager = iVwCtrlSchemeTypeManager;
	}
	
	
	/**
	 * 加载Grid数据
	 * @return String 
	 * @throws Exception
	 */
	public String generalGridByTree() throws Exception {
		if(null == nodeType || "".equals(nodeType)){
			this.success=false;
    		return SUCCESS;
    	}
		try{
			PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
			if("usr".equals(this.nodeType)){
				this.tTmnlGroupUserDtoList = this.iTTmnlGroupUserManager.queryTTmnlGroupUserByTmnlAssetNo(this.tmnlAssetNo);
	    	}
			else if("org".equals(this.nodeType)){
				this.tTmnlGroupUserDtoList = this.iTTmnlGroupUserManager.queryTTmnlGroupUserByOrgNo(this.orgNo,this.orgType,pSysUser);
	    	}
			else if("line".equals(this.nodeType)){
				this.tTmnlGroupUserDtoList = this.iTTmnlGroupUserManager.queryTTmnlGroupUserByLineId(this.lineId,pSysUser);
	    	}
			else if("cgp".equals(this.nodeType) || "ugp".equals(this.nodeType)){
				this.tTmnlGroupUserDtoList = this.iTTmnlGroupUserManager.queryTTmnlGroupUserByGroupNo(this.nodeType,this.groupNo);
			}
			else if("sub".equals(this.nodeType)){
				this.tTmnlGroupUserDtoList = this.iTTmnlGroupUserManager.queryTTmnlGroupUserBySubsId(this.subsId,pSysUser);
	    	}
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}		
	}
	
	/**
	 * 新增普通群组
	 */
	public String addCommonGroup() throws Exception {
		try{
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()
					||null==this.groupName||"".equals(this.groupName)
					||null==this.startDate||null==this.validDays){
				this.success=false;
				return SUCCESS;
			}
			PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			String staffNo=pSysUser.getStaffNo();
			if(null==staffNo||"".equals(staffNo)){
				this.success=false;
				return SUCCESS;
			}
	        this.FLAG=this.iGroupSetManager.saveCommonGroup(this.groupName,staffNo,new java.sql.Date(this.startDate.getTime()),this.validDays,tmnlList);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}
	
	/**
	 * 新增控制群组
	 */
	public String addCtrlGroup() throws Exception {
        try{
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==tmnlList||0==tmnlList.size()
				||null==this.groupName||"".equals(this.groupName)
				||null==this.groupTypeCode||"".equals(this.groupTypeCode)
				||null==this.ctrlSchemeType||"".equals(this.ctrlSchemeType)
				||null==this.startDate||null==this.validDays){
				this.success=false;
				return SUCCESS;
			}
			PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
				return SUCCESS;
			}
			if(null==pSysUser.getStaffNo()||"".equals(pSysUser.getStaffNo())){
				this.success=false;
				return SUCCESS;
			}
			this.FLAG=this.iGroupSetManager.saveContrlGroup(this.groupName, null,this.groupTypeCode,this.ctrlSchemeType,null,pSysUser, new java.sql.Date(this.startDate.getTime()),this.validDays,tmnlList);
			return SUCCESS;
        }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 查询控制类别1
	 */
	public String queryCtrlSchemeType1()throws Exception{
		try{
			List<VwCtrlSchemeType> vwCtrlSchemeTypeList=new ArrayList<VwCtrlSchemeType>();
			vwCtrlSchemeTypeList=this.iVwCtrlSchemeTypeManager.queryCtrlSchemeType();
			if(null!=vwCtrlSchemeTypeList &&0< vwCtrlSchemeTypeList.size()){
				this.ctrlSchemeTypeIdList = new ArrayList<VwCtrlSchemeTypeId>();
				for(int i=0;i<vwCtrlSchemeTypeList.size();i++){
					this.ctrlSchemeTypeIdList.add(vwCtrlSchemeTypeList.get(i).getId());
				}
			}
	        return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 查询控制类别2
	 */
	public String queryCtrlSchemeType2()throws Exception{
		try{
			this.ctrlSchemeTypeIdList = new ArrayList<VwCtrlSchemeTypeId>();
			VwCtrlSchemeTypeId vwCtrlSchemeTypeId =new VwCtrlSchemeTypeId();
			vwCtrlSchemeTypeId.setCtrlSchemeNo("-1");
			vwCtrlSchemeTypeId.setCtrlSchemeType("无");
			this.ctrlSchemeTypeIdList.add(vwCtrlSchemeTypeId);
			List<VwCtrlSchemeType> vwCtrlSchemeTypeList=new ArrayList<VwCtrlSchemeType>();
			vwCtrlSchemeTypeList=this.iVwCtrlSchemeTypeManager.queryCtrlSchemeType();
			if(null!=vwCtrlSchemeTypeList &&0< vwCtrlSchemeTypeList.size()){
				for(int i=0;i<vwCtrlSchemeTypeList.size();i++){
					this.ctrlSchemeTypeIdList.add(vwCtrlSchemeTypeList.get(i).getId());
				}
			}
	        return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 增加明细到群组
	 */
	public String insertToGroup() throws Exception{
		try{
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==this.groupType||"".equals(this.groupType)
				||null==this.groupNo||"".equals(this.groupNo)	
				||null==tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;	
			}
			this.iGroupSetManager.updateGroup(this.groupType,this.groupNo,tmnlList);
			return SUCCESS;
	    }catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 查询群组名称
	 */
	public String queryGroup()throws Exception{
		try{
			PSysUser pSysUser =getPSysUser();
			if(null==pSysUser){
				this.success=false;
	    		return SUCCESS;	
			}
			String staffNo=pSysUser.getStaffNo();
			if(null==staffNo||"".equals(staffNo)||null==this.groupType||"".equals(this.groupType)){
				this.success=false;
				return SUCCESS;	
			}
			this.tTmnlGroupDtoList=this.iGroupSetManager.queryAllTmnlGroup(staffNo,this.groupType,this.ctrlSchemeType);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 按条件查询群组  
	 */
	public String queryGroupBy()throws Exception{
		try{
			PSysUser pSysUser =getPSysUser();
			if(null==pSysUser){
				this.success=false;
	    		return SUCCESS;	
			}
			String staffNo=pSysUser.getStaffNo();
			if(null==staffNo||"".equals(staffNo)||null==this.groupType||"".equals(this.groupType)){
				this.success=false;
				return SUCCESS;	
			}
			Page<TTmnlGroupDto> tTmnlGroupDtoPage=this.iGroupSetManager.qureyTmnlGroupBy(staffNo,this.groupType,this.groupName, this.startDate, this.finishDate,this.start,this.limit);
			this.tTmnlGroupDtoList = tTmnlGroupDtoPage.getResult();
			this.groupTotalCount=tTmnlGroupDtoPage.getTotalCount();
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
//	/**
//	 * 设置共享
//	 */
//	public String setShare()throws Exception{
//		if(null == this.groupNos||"".equals(this.groupNos)
//		   ||null==this.groupType||"".equals(this.groupType)
//		   ||null==this.isShare)
//	    		return SUCCESS;
//		String groupNoArray[]=this.parse(this.groupNos);
//		if(groupNoArray==null||groupNoArray.length<=0)
//			    return SUCCESS;
//	    this.iGroupSetManager.updateShare(this.groupType,groupNoArray,this.isShare);
//		return SUCCESS;	
//	}
	
	/**
	 * 更改群组名称
	 */
	public String rejiggerGroupName()throws Exception{ 
		if(null==this.groupType||"".equals(this.groupType)
		   ||null == this.groupNo||"".equals(this.groupNo)
		   ||null == this.groupName||"".equals(this.groupName)){
			this.success=false;
			return SUCCESS;
		}
		try{
			this.FLAG=this.iGroupSetManager.updateGroupName(this.groupType,this.groupNo, this.groupName);
		    return SUCCESS;	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/*public String rejiggerGroupName()throws Exception{ 
		if(null==this.groupType||"".equals(this.groupType)
		   ||null == this.groupNo||"".equals(this.groupNos)
		   ||null == this.groupName||"".equals(this.groupName))
    		return SUCCESS;
		rejiggerGroupNameFlag rejiggerFlag=new rejiggerGroupNameFlag();
		rejiggerFlag=this.iGroupSetManager.updateGroupName(this.groupType,this.groupNo, this.groupName);
		if(null!=rejiggerFlag){
			this.FLAG=rejiggerFlag.getFlag();
			this.groupName=rejiggerFlag.getGroupName();
		}
	    return SUCCESS;	
	}*/
	
/*	*//**
	 * 删除群组（判断staffNo）
	 *//*
	public String deleteGroup()throws Exception{ 
		try{
			PSysUser pSysUser =(PSysUser)getSession().getAttribute("pSysUser");
			if(null==pSysUser){
				this.success=false;
	    		return SUCCESS;	
			}
			String staffNo=pSysUser.getStaffNo();
			List<String> creators =this.parse(this.creatorList);
			if(null==staffNo||"".equals(staffNo)||null==creators||0==creators.size()){
				this.success=false;
			    return SUCCESS;
			}
			for(int i=0;i<creators.size();i++){
				if(!staffNo.equals(creators.get(i))){
					this.FLAG=0;
					return SUCCESS;
				}
			}
			List<String> groupNos =this.parse(this.groupNoList);
			if(null==this.groupType||"".equals(this.groupType)||null==groupNos||0==groupNos.size()){
				this.success=false;
			    return SUCCESS;
			}
			this.iGroupSetManager.deleteGroup(this.groupType,groupNos);
			this.FLAG=1;
			return SUCCESS;		
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}*/
	
	/**
	 * 删除群组
	 */
	public String deleteGroup()throws Exception{ 
		try{
			List<String> groupNos =this.parse(this.groupNoList);
			if(null==this.groupType||"".equals(this.groupType)||null==groupNos||0==groupNos.size()){
				this.success=false;
			    return SUCCESS;
			}
			this.iGroupSetManager.deleteGroup(this.groupType,groupNos);
			return SUCCESS;		
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
		
	/**
	 * 查找群组用户
	 */
	public String  queryGroupUser() throws Exception{
		if(null==this.groupType||"".equals(this.groupType)||null == this.groupNo||"".equals(this.groupNo)){
			this.success=false;
	   		return SUCCESS;
		}
		try{
			Page<TTmnlGroupDetailDto> tTmnlGroupDetailDtoPage=this.iGroupSetManager.queryGroupDetail(this.groupType, this.groupNo,this.consNo,this.start,this.limit);
			this.tTmnlGroupDetailDtoList =tTmnlGroupDetailDtoPage.getResult();
			this.groupDetailTotalCount=tTmnlGroupDetailDtoPage.getTotalCount();
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除群组明细
	 */
	public String  deletegroupDetailBy() throws Exception{
		try{
			List<CtrlParamBean> tmnlList = parseTmnlList();
			if(null==this.groupType||"".equals(this.groupType)
			 ||null == this.groupNo||"".equals(this.groupNo)
			 ||null == tmnlList||0==tmnlList.size()){
				this.success=false;
				return SUCCESS;
			}
			this.iGroupSetManager.deleteGroupDetailBy(this.groupType, this.groupNo, tmnlList);
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	 /**
	 * 提取页面选择的终端
	 */
	public List<CtrlParamBean> parseTmnlList() {
		List<CtrlParamBean> tmnlList = new ArrayList<CtrlParamBean>();
		if(null==this.groupTmnlList||0==this.groupTmnlList.size())
			return null;
		for (int i = 0; i < this.groupTmnlList.size(); i++) {
			String[] tmnl = this.groupTmnlList.get(i).split("`");
			if("".equals(tmnl[1])){
				continue;
			}
			CtrlParamBean bean = new CtrlParamBean();
			bean.setConsNo(tmnl[0]);
			bean.setTmnlAssetNo(tmnl[1]);
			tmnlList.add(bean);
		}
		return tmnlList;
	}
	
	public List<String> parse(List<String> s){
		if(null==s||0==s.size())
			return null;
		 List<String> list =new ArrayList<String>();
	     for(int i=0;i<s.size();i++){
	    	 String st=s.get(i);
	    	 if(!("").equals(st)){
	    		 list.add(st);
	    	 }
	     }
	     return list;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public List<TTmnlGroupUserDto> gettTmnlGroupUserDtoList() {
		return tTmnlGroupUserDtoList;
	}

	public void settTmnlGroupUserDtoList(
			List<TTmnlGroupUserDto> tTmnlGroupUserDtoList) {
		this.tTmnlGroupUserDtoList = tTmnlGroupUserDtoList;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Short getValidDays() {
		return validDays;
	}
	
	public void setValidDays(Short validDays) {
		this.validDays = validDays;
	}
	
//	public Short getIsShare() {
//		return isShare;
//	}
//	public void setIsShare(Short isShare) {
//		this.isShare = isShare;
//	}
	
	public Integer getFLAG() {
		return FLAG;
	}
	
	public void setFLAG(Integer fLAG) {
		FLAG = fLAG;
	}
	
	public List<VwCtrlSchemeTypeId> getCtrlSchemeTypeIdList() {
		return ctrlSchemeTypeIdList;
	}
	
	public void setCtrlSchemeTypeIdList(
			List<VwCtrlSchemeTypeId> ctrlSchemeTypeIdList) {
		this.ctrlSchemeTypeIdList = ctrlSchemeTypeIdList;
	}
	
//	public String getOrgsNos() {
//		return orgsNos;
//	}
//	public void setOrgsNos(String orgsNos) {
//		this.orgsNos = orgsNos;
//	}
	
	public List<TTmnlGroupDto> gettTmnlGroupDtoList() {
		return tTmnlGroupDtoList;
	}
	
	public List<TTmnlGroupDto> getTTmnlGroupDtoList() {
		return tTmnlGroupDtoList;
	}
	
	public void settTmnlGroupDtoList(List<TTmnlGroupDto> tTmnlGroupDtoList) {
		this.tTmnlGroupDtoList = tTmnlGroupDtoList;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getFinishDate() {
		return finishDate;
	}
	
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	
	public String getGroupNo() {
		return groupNo;
	}
	
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	
	public String getGroupTypeCode() {
		return groupTypeCode;
	}
	
	public void setGroupTypeCode(String groupTypeCode) {
		this.groupTypeCode = groupTypeCode;
	}
	
	public String getCtrlSchemeType() {
		return ctrlSchemeType;
	}
	
	public void setCtrlSchemeType(String ctrlSchemeType) {
		this.ctrlSchemeType = ctrlSchemeType;
	}
	
	public String getGroupType() {
		return groupType;
	}
	
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	public List<TTmnlGroupDetailDto> gettTmnlGroupDetailDtoList() {
		return tTmnlGroupDetailDtoList;
	}
	
	public List<TTmnlGroupDetailDto> getTTmnlGroupDetailDtoList() {
		return tTmnlGroupDetailDtoList;
	}
	
	public void settTmnlGroupDetailDtoList(
			List<TTmnlGroupDetailDto> tTmnlGroupDetailDtoList) {
		this.tTmnlGroupDetailDtoList = tTmnlGroupDetailDtoList;
	}
	
	public String getLineId() {
		return lineId;
	}
	
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	public String getSubsId() {
		return subsId;
	}
	
	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}
	
	public String getOrgType() {
		return orgType;
	}
	
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	
	public List<String> getGroupTmnlList() {
		return groupTmnlList;
	}
	
	public void setGroupTmnlList(List<String> groupTmnlList) {
		this.groupTmnlList = groupTmnlList;
	}
//	public List<String> getCreatorList() {
//		return creatorList;
//	}
//	public void setCreatorList(List<String> creatorList) {
//		this.creatorList = creatorList;
//	}

	public List<String> getGroupNoList() {
		return groupNoList;
	}

	public void setGroupNoList(List<String> groupNoList) {
		this.groupNoList = groupNoList;
	}

	public long getGroupTotalCount() {
		return groupTotalCount;
	}

	public void setGroupTotalCount(long groupTotalCount) {
		this.groupTotalCount = groupTotalCount;
	}

	public long getGroupDetailTotalCount() {
		return groupDetailTotalCount;
	}

	public void setGroupDetailTotalCount(long groupDetailTotalCount) {
		this.groupDetailTotalCount = groupDetailTotalCount;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}
   
}