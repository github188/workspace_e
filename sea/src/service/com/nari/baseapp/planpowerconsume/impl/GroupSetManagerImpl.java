package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.nari.baseapp.planpowerconsume.ITTmnlGroupJdbcDao;
import org.springframework.dao.DataAccessException;
import com.nari.baseapp.planpowerconsume.ICConsDao;
import com.nari.baseapp.planpowerconsume.IGroupSetManager;
import com.nari.baseapp.planpowerconsume.IRUserGroupDao;
import com.nari.baseapp.planpowerconsume.IRUserGroupDetailDao;
import com.nari.baseapp.planpowerconsume.ITTmnlGroupDao;
import com.nari.baseapp.planpowerconsume.ITTmnlGroupDetailDao;
import com.nari.baseapp.planpowerconsume.ITTmnlGroupDetailIdJdbcDao;
import com.nari.customer.CCons;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.terminalparam.RUserGroup;
import com.nari.terminalparam.RUserGroupDetail;
import com.nari.terminalparam.RUserGroupDetailId;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.terminalparam.TTmnlGroupDetail;
import com.nari.terminalparam.TTmnlGroupDetailDto;
import com.nari.terminalparam.TTmnlGroupDetailId;
import com.nari.terminalparam.TTmnlGroupDto;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;


/**
 * @author 姜海辉
 *
 * @创建时间:2009-12-28 下午06:55:05
 *
 * @类描述: 群组管理
 *	
 */

public class GroupSetManagerImpl implements IGroupSetManager {
	
	private ITTmnlGroupDao iTTmnlGroupDao;
	private ICConsDao iCConsDao;
	private ITTmnlGroupDetailDao iTTmnlGroupDetailDao;
	private IRUserGroupDao iRUserGroupDao;
	private IRUserGroupDetailDao  iRUserGroupDetailDao;
	private ITTmnlGroupJdbcDao iTTmnlGroupJdbcDao;
	private ITTmnlGroupDetailIdJdbcDao iTTmnlGroupDetailIdJdbcDao;
	
	
	/**
	 * 添加普通群组
	 * @param groupName         群组名称
	 * @param isShare 			是否共享
	 * @param staffNo           工号
	 * @param validDays         有效天数
	 * @param  tmnlList          终端列表
	 * @throws DBAccessException 数据库异常
	 */
	public int saveCommonGroup(String groupName,String staffNo,Date startDate,Short validDays,List<CtrlParamBean> tmnlList)throws Exception{
		try{
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			PropertyFilter o = new PropertyFilter("groupName",groupName);
			filters.add(o);
			List<RUserGroup> rlist=this.iRUserGroupDao.findBy(filters);
			if(rlist.size()>0)
			   return 0;
			RUserGroup  r=new  RUserGroup();
			r.setGroupName(groupName);
//			if(isShare==1)
//				r.setIsShare(true);
//			else
//				r.setIsShare(false);
			r.setIsShare(false);
			r.setValidDays(validDays);
			r.setStaffNo(staffNo);
			r.setCreateDate(startDate);
			this.iRUserGroupDao.save(r);
			rlist=new ArrayList<RUserGroup>();
			rlist=this.iRUserGroupDao.findBy(filters);
			if(null!=rlist&&rlist.size()==1){
				String groupNo=rlist.get(0).getGroupNo();
				for(int i=0;i<tmnlList.size();i++){
					RUserGroupDetailId  rUserGroupDetailId=new  RUserGroupDetailId();
					rUserGroupDetailId.setGroupNo(groupNo);
					rUserGroupDetailId.setConsNo(tmnlList.get(i).getConsNo());
					rUserGroupDetailId.setTmnlAssetNo(tmnlList.get(i).getTmnlAssetNo());
					RUserGroupDetail rUserGroupDetail=new RUserGroupDetail();
					rUserGroupDetail.setId(rUserGroupDetailId);
					this.iRUserGroupDetailDao.saveOrUpdate(rUserGroupDetail);
				}
			}
		    }catch (DataAccessException de) {
				throw new DBAccessException(BaseException.processDBException(de));
			}catch (Exception e) {
				throw new ServiceException(ExceptionConstants.BAE_SCBXYHCC);
			}
			return 1;
	}
	
	/**
	 * 添加控制群组
	 * @param groupName         群组名称
	 * @param Addr              群组地址
	 * @param groupTypeCode     群组类型
	 * @param ctrlSchemeType    控制类别
	 * @param releaseStatusCode 下发状态
	 * @param isShare           是否共享
	 * @param staffNo           操作员
	 * @param validDays         有效天数
	 * @param tmnlList          终端列表
	 * @return
	 * @throws Exception
	 */
	public int saveContrlGroup(String groupName,String Addr,String groupTypeCode,String ctrlSchemeType,String releaseStatusCode,PSysUser pSysUser,Date startDate,Short validDays,List<CtrlParamBean> tmnlList)throws Exception{
		try{
			List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
			PropertyFilter o = new PropertyFilter("groupName",groupName);
			filters.add(o);
			List<TTmnlGroup> rlist=this.iTTmnlGroupDao.findBy(filters);
			if(rlist.size()>0)
				   return 0;
			TTmnlGroup  tTmnlGroup=new TTmnlGroup();
			tTmnlGroup.setOrgNo(pSysUser.getOrgNo());
			tTmnlGroup.setGroupName(groupName);
			tTmnlGroup.setGroupTypeCode(groupTypeCode);
			tTmnlGroup.setCtrlSchemeType(ctrlSchemeType);
//			if(isShare==1)
//				tTmnlGroup.setIsShare(true);
//			else
//				tTmnlGroup.setIsShare(false);
			tTmnlGroup.setIsShare(false);
			tTmnlGroup.setValidDays(validDays);
			tTmnlGroup.setStaffNo(pSysUser.getStaffNo());
			tTmnlGroup.setCreateDate(startDate);
			this.iTTmnlGroupDao.save(tTmnlGroup);
			rlist=new ArrayList<TTmnlGroup>();
			rlist=this.iTTmnlGroupDao.findBy(filters);
			if(rlist.size()==1){
				String groupNo=rlist.get(0).getGroupNo();
				for(int i=0;i<tmnlList.size();i++){
					TTmnlGroupDetailId  tTmnlGroupDetailId=new TTmnlGroupDetailId();
					tTmnlGroupDetailId.setGroupNo(groupNo);
					tTmnlGroupDetailId.setConsNo(tmnlList.get(i).getConsNo());
					tTmnlGroupDetailId.setTmnlAssetNo(tmnlList.get(i).getTmnlAssetNo());
					TTmnlGroupDetail tTmnlGroupDetail=new TTmnlGroupDetail();
					tTmnlGroupDetail.setId(tTmnlGroupDetailId);
					this.iTTmnlGroupDetailDao.saveOrUpdate(tTmnlGroupDetail);
				
				}
			}	
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_SCBXYHCC);
		}
		return 1;

	}

	
	/**
	 * 加入群组
	 * @param ctrlSchemeType   控制类型
	 * @param groupNo          群组编号
	 * @param consNoArray      用户编号  
	 * @param tmnlList         终端列表
	 * @return
	 * @throws Exception
	 */
	public void updateGroup(String groupType,String groupNo,List<CtrlParamBean> tmnlList)throws Exception{
		try{
			if("0".equals(groupType)){
				List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
				PropertyFilter o = new PropertyFilter("groupNo",groupNo);
				filters.add(o);
				List<RUserGroup> rlist=this.iRUserGroupDao.findBy(filters);
				if(rlist.size()==1){
					for(int i=0;i<tmnlList.size();i++){
						RUserGroupDetailId rUserGroupDetailId=new  RUserGroupDetailId();
						rUserGroupDetailId.setGroupNo(groupNo);
						rUserGroupDetailId.setConsNo(tmnlList.get(i).getConsNo());
						rUserGroupDetailId.setTmnlAssetNo(tmnlList.get(i).getTmnlAssetNo());
						RUserGroupDetail rUserGroupDetail=new RUserGroupDetail();
						rUserGroupDetail.setId(rUserGroupDetailId);
						this.iRUserGroupDetailDao.saveOrUpdate(rUserGroupDetail);   
					}
					return ;
				}
				else
					throw new DBAccessException(ExceptionConstants.DBE_UNCATEGORIZED);
			}
			else if("1".equals(groupType)){
				List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
				PropertyFilter o = new PropertyFilter("groupNo",groupNo);
				filters.add(o);
				List<TTmnlGroup> tlist=new ArrayList<TTmnlGroup>();
				tlist=this.iTTmnlGroupDao.findBy(filters);
				if(tlist.size()==1){
					for(int i=0;i<tmnlList.size();i++){
						TTmnlGroupDetailId  tTmnlGroupDetailId=new TTmnlGroupDetailId();
						tTmnlGroupDetailId.setGroupNo(groupNo);
						tTmnlGroupDetailId.setConsNo(tmnlList.get(i).getConsNo());
						tTmnlGroupDetailId.setTmnlAssetNo(tmnlList.get(i).getTmnlAssetNo());
						TTmnlGroupDetail tTmnlGroupDetail=new TTmnlGroupDetail();
						tTmnlGroupDetail.setId(tTmnlGroupDetailId);
						this.iTTmnlGroupDetailDao.saveOrUpdate(tTmnlGroupDetail);
					}
					return ;
				}
				else
					throw new DBAccessException(ExceptionConstants.DBE_UNCATEGORIZED);
			}
			return ;	
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_SCBXYHCC);
		}
	}
	
	/**
	 * 按照控制方式查询群组
	 * @throws DBAccessException 数据库异常
	 */
	public  List<TTmnlGroupDto> queryAllTmnlGroup(String staffNo,String groupType,String ctrlSchemeType) throws DBAccessException{
       try{
    	   if("0".equals(groupType))
    		   return this.iTTmnlGroupJdbcDao.findCommonGroup(staffNo);
    	   else if("1".equals(groupType)){
    		   if(null!=ctrlSchemeType&&!"".equals(ctrlSchemeType))
    			   return this.iTTmnlGroupJdbcDao.findCtrlGroup(staffNo,ctrlSchemeType); 
    	   }
    	   return null;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} 
	}
	
	public void saveTmnlGroup(TTmnlGroup tTmnlGroup) throws DBAccessException{
		try {
			this.iTTmnlGroupDao.save(tTmnlGroup);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
		
	}
	public List<TTmnlGroup> findTmnlGroupBy(List<PropertyFilter> filters) throws DBAccessException{
		try {
			return this.iTTmnlGroupDao.findBy(filters);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
		
	}
	
	/**
	 * 设置共享
	 * @param groupNoArray 群组编号
	 * @param isShare      是否共享
	 * @throws DBAccessException
	 */
	public void updateShare(String groupType,String[] groupNoArray,Short isShare)throws DBAccessException{
		try{
			if("1".equals(groupType)){
				for(int i=0;i<groupNoArray.length;i++){
					List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
					PropertyFilter o = new PropertyFilter("groupNo",groupNoArray[i]);
					filters.add(o);
					List<TTmnlGroup> rlist=this.iTTmnlGroupDao.findBy(filters);
					if(rlist.size()==1){
						TTmnlGroup tTmnlGroup=new TTmnlGroup();
						tTmnlGroup=rlist.get(0);
						if(isShare==0)
						  tTmnlGroup.setIsShare(false);
						else if(isShare==1)
						  tTmnlGroup.setIsShare(true);
						this.iTTmnlGroupDao.update(tTmnlGroup);
					}
				}	
			}
			else if("0".equals(groupType)){
				for(int j=0;j<groupNoArray.length;j++){
					List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
					PropertyFilter o = new PropertyFilter("groupNo",groupNoArray[j]);
					filters.add(o);
					List<RUserGroup>rlist=this.iRUserGroupDao.findBy(filters);
					if(rlist.size()==1){
						RUserGroup  rUserGroup=new RUserGroup();
						rUserGroup=rlist.get(0);
						if(isShare==0)
							rUserGroup.setIsShare(false);
						else if(isShare==1)
							rUserGroup.setIsShare(true);
						this.iRUserGroupDao.update(rUserGroup);
					}
				}
			}
			return ;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
	}
	
	
	
	/**
	 * 按条件查找群组信息
	 * @param groupType  群组类别
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
    public Page<TTmnlGroupDto> qureyTmnlGroupBy(String staffNo,String groupType,String groupName,Date startDate,Date finishDate,long start,long limit)throws DBAccessException{
    	try{
    		if("0".equals(groupType))
    			return this.iTTmnlGroupJdbcDao.findCommonGroupBy(staffNo,groupName, startDate, finishDate,start,limit);
    		else if("1".equals(groupType))
    			return this.iTTmnlGroupJdbcDao.findCtrlGroupBy(staffNo,groupName, startDate, finishDate,start,limit);
    		return null;
    	}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	  	
    }
    
    /**
     * 群组明细查询
     * @param groupType 群组类别
     * @param groupNo   群组编号
     * @return
     * @throws DBAccessException
     */
	public Page<TTmnlGroupDetailDto> queryGroupDetail(String groupType,String groupNo,String consNo,long start,long limit)throws DBAccessException{
		try{
			if("1".equals(groupType))
				return this.iTTmnlGroupDetailIdJdbcDao.queryCtrlGroupDetail(groupNo,consNo,start,limit);
			else if("0".equals(groupType))
				return this.iTTmnlGroupDetailIdJdbcDao.queryCommonGroupDetail(groupNo,consNo,start,limit);
			return null;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	 
	}
	
	
    /**
	 * 群组更名 
	 * @param groupType 群组类别
	 * @param groupNo    群组编号
	 * @param groupName  群组名称
	 * @return
	 * @throws DBAccessException
	 */
	  public Integer updateGroupName(String groupType,String groupNo,String groupName)throws DBAccessException{
		try{
			if("0".equals(groupType)){
				List<PropertyFilter> filters1=new ArrayList<PropertyFilter>();
				PropertyFilter o = new PropertyFilter("groupName",groupName);
				filters1.add(o);
				List<RUserGroup> rlist1=this.iRUserGroupDao.findBy(filters1);
				if(rlist1.size()>0)
					return 0;
				List<PropertyFilter> filters2=new ArrayList<PropertyFilter>();
				PropertyFilter p = new PropertyFilter("groupNo",groupNo);
				filters2.add(p);
				List<RUserGroup> rlist2=this.iRUserGroupDao.findBy(filters2);
				if(rlist2.size()==1){
					RUserGroup rUserGroup=new RUserGroup();
					rUserGroup=rlist2.get(0);
					rUserGroup.setGroupName(groupName);
					this.iRUserGroupDao.update(rUserGroup);
				}
				return 1;
			}
			else if("1".equals(groupType)){
	    			List<PropertyFilter> filters1=new ArrayList<PropertyFilter>();
	    			PropertyFilter o = new PropertyFilter("groupName",groupName);
	    			filters1.add(o);
	    			List<TTmnlGroup> rlist1=this.iTTmnlGroupDao.findBy(filters1);
	    			if(rlist1.size()>0)
	    				return 0;
	    			List<PropertyFilter> filters2=new ArrayList<PropertyFilter>();
					PropertyFilter p = new PropertyFilter("groupNo",groupNo);
					filters2.add(p);
					List<TTmnlGroup> rlist2=this.iTTmnlGroupDao.findBy(filters2);
					if(rlist2.size()==1){
						TTmnlGroup tTmnlGroup=new TTmnlGroup();
						tTmnlGroup=rlist2.get(0);
						tTmnlGroup.setGroupName(groupName);
						this.iTTmnlGroupDao.update(tTmnlGroup);
					}
					return 1;
			}
			return null;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}		
	 }
    /*public rejiggerGroupNameFlag updateGroupName(String groupType,String groupNo,String groupName)throws DBAccessException{
    	try{	
    		rejiggerGroupNameFlag rejiggerFlag=new rejiggerGroupNameFlag();
    		if("0".equals(groupType)){
    			List<PropertyFilter> filters1=new ArrayList<PropertyFilter>();
    			PropertyFilter o = new PropertyFilter("groupName",groupName);
    			filters1.add(o);
    			List<RUserGroup> rlist1=this.iRUserGroupDao.findBy(filters1);
    			if(rlist1.size()>0){
    				rejiggerFlag.setFlag(0);
    				return rejiggerFlag;
    			}   
    			List<PropertyFilter> filters2=new ArrayList<PropertyFilter>();
    			PropertyFilter p = new PropertyFilter("groupNo",groupNo);
    			filters2.add(p);
    			List<RUserGroup> rlist2=this.iRUserGroupDao.findBy(filters2);
    			if(rlist2.size()==1){
    				RUserGroup rUserGroup=new RUserGroup();
    				rUserGroup=rlist2.get(0);
    				rUserGroup.setGroupName(groupName);
    				this.iRUserGroupDao.update(rUserGroup);
    				rejiggerFlag.setFlag(1);
    				rlist2=new ArrayList<RUserGroup>();
    				rlist2=this.iRUserGroupDao.findBy(filters2);
    				if(rlist2.size()==1){
    					rejiggerFlag.setGroupName(rlist2.get(0).getGroupName());
    				}
    			}
    			return rejiggerFlag;
    		}
    		else if("1".equals(groupType)){
	    			List<PropertyFilter> filters1=new ArrayList<PropertyFilter>();
	    			PropertyFilter o = new PropertyFilter("groupName",groupName);
	    			filters1.add(o);
	    			List<TTmnlGroup> rlist1=this.iTTmnlGroupDao.findBy(filters1);
	    			if(rlist1.size()>0){
	    				rejiggerFlag.setFlag(0);
	    				return rejiggerFlag;
	    			}   
	    			List<PropertyFilter> filters2=new ArrayList<PropertyFilter>();
					PropertyFilter p = new PropertyFilter("groupNo",groupNo);
					filters2.add(p);
					List<TTmnlGroup> rlist2=this.iTTmnlGroupDao.findBy(filters2);
					if(rlist2.size()==1){
						TTmnlGroup tTmnlGroup=new TTmnlGroup();
						tTmnlGroup=rlist2.get(0);
						tTmnlGroup.setGroupName(groupName);
						this.iTTmnlGroupDao.update(tTmnlGroup);
						rejiggerFlag.setFlag(1);
						rlist2=new ArrayList<TTmnlGroup>();
						rlist2=this.iTTmnlGroupDao.findBy(filters2);
						if(rlist2.size()==1){
							rejiggerFlag.setGroupName(rlist2.get(0).getGroupName());
						}
					}
					return rejiggerFlag;
    		}
			return null;
    	}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
    	
    }
    
    /**
     * 群组删除
     * @param groupNoArray 群组编号数组
     * @return
     * @throws DBAccessException
     */
    public void deleteGroup(String groupType,List<String> groupNos)throws DBAccessException{
    	try{
    		if("0".equals(groupType)){
    			for(int i=0;i<groupNos.size();i++){
    				this.iTTmnlGroupDetailIdJdbcDao.deleteCommonGroupDetail(groupNos.get(i));
    				this.iRUserGroupDao.delete(groupNos.get(i));
    			}
    		}
    		else if("1".equals(groupType)){
		    	for(int j=0;j<groupNos.size();j++){
		    		this.iTTmnlGroupDetailIdJdbcDao.deleteCtrlGroupDetail(groupNos.get(j));
			    	this.iTTmnlGroupDao.delete(groupNos.get(j));
		    	}
			}
    	}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	
    }	
    
    /**
	 * 删除群组明细
	 * @param groupType
	 * @param groupNo
	 * @param groupNoArray
	 * @param tmnlAssetNoArray
	 * @throws DBAccessException
	 */
	public void deleteGroupDetailBy(String groupType,String groupNo,List<CtrlParamBean> tmnlList)throws DBAccessException{
		try{
			if("1".equals(groupType)){
					for(int j=0;j<tmnlList.size();j++){
						TTmnlGroupDetailId tTmnlGroupDetailId=new TTmnlGroupDetailId();
						tTmnlGroupDetailId.setGroupNo(groupNo);
						tTmnlGroupDetailId.setConsNo(tmnlList.get(j).getConsNo());
						tTmnlGroupDetailId.setTmnlAssetNo(tmnlList.get(j).getTmnlAssetNo());
						this.iTTmnlGroupDetailDao.delete(tTmnlGroupDetailId);
					}
			}
			if("0".equals(groupType)){
				for(int i=0;i<tmnlList.size();i++){
					RUserGroupDetailId rUserGroupDetailId=new RUserGroupDetailId();
					rUserGroupDetailId.setGroupNo(groupNo);
					rUserGroupDetailId.setConsNo(tmnlList.get(i).getConsNo());
					rUserGroupDetailId.setTmnlAssetNo(tmnlList.get(i).getTmnlAssetNo());
					this.iRUserGroupDetailDao.delete(rUserGroupDetailId);
				}
			}
			return ;
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}	 	
	}
	
	/**
	 * @描述 分页查询用电客户
	 */
	public Page<CCons> getAllCCons(Page page) throws Exception{
		return iCConsDao.findAllCCons(page);
	}
	
	public void setiCConsDao(ICConsDao iCConsDao) {
		this.iCConsDao = iCConsDao;
	}

	public void setiTTmnlGroupDao(ITTmnlGroupDao iTTmnlGroupDao) {
		this.iTTmnlGroupDao = iTTmnlGroupDao;
	}
	public void setiTTmnlGroupDetailDao(ITTmnlGroupDetailDao iTTmnlGroupDetailDao) {
		this.iTTmnlGroupDetailDao = iTTmnlGroupDetailDao;
	}
	public void setiRUserGroupDao(IRUserGroupDao iRUserGroupDao) {
		this.iRUserGroupDao = iRUserGroupDao;
	}
	public void setiRUserGroupDetailDao(IRUserGroupDetailDao iRUserGroupDetailDao) {
		this.iRUserGroupDetailDao = iRUserGroupDetailDao;
	}

	public void setiTTmnlGroupJdbcDao(ITTmnlGroupJdbcDao iTTmnlGroupJdbcDao) {
		this.iTTmnlGroupJdbcDao = iTTmnlGroupJdbcDao;
	}

	public void setiTTmnlGroupDetailIdJdbcDao(
			ITTmnlGroupDetailIdJdbcDao iTTmnlGroupDetailIdJdbcDao) {
		this.iTTmnlGroupDetailIdJdbcDao = iTTmnlGroupDetailIdJdbcDao;
	}
}
