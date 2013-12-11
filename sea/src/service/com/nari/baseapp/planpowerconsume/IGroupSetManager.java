package com.nari.baseapp.planpowerconsume;

import java.util.Date;
import java.util.List;

import com.nari.customer.CCons;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.terminalparam.TTmnlGroupDetailDto;
import com.nari.terminalparam.TTmnlGroupDto;
import com.nari.util.exception.DBAccessException;

/**
 * @author 姜海辉
 *
 * @创建时间:2009-12-28 下午06:54:47
 *
 * @类描述: 群组管理
 *	
 */
public interface IGroupSetManager {
	

	/**
	 * 添加普通群组
	 * @param groupName         群组名称
	 * @param isShare           是否共享
	 *  * @param staffNo        工号
	 * @param validDays         有效天数
	 * @param consNoArray       用户编号  
	 * @param tmnlList           终端列表
	 * @throws DBAccessException 数据库异常
	 */
	public int saveCommonGroup(String groupName,String staffNo,Date startDate,Short validDays,List<CtrlParamBean> tmnlList)throws Exception; 
	
	
	/**
	 * 添加控制群组
	 * @param groupName         群组名称
	 * @param Addr              群组地址
	 * @param groupTypeCode     群组类型
	 * @param ctrlSchemeType    控制类别
	 * @param releaseStatusCode 下发状态
	 * @param isShare           是否共享
	 * @param pSysUser           操作员
	 * @param validDays         有效天数
	 * @param tmnlList          终端列表
	 * @return 
	 * @throws Exception
	 */
	public int saveContrlGroup(String groupName,String Addr,String groupTypeCode,String ctrlSchemeType,String releaseStatusCode,PSysUser pSysUser,Date startDate,Short validDays,List<CtrlParamBean> tmnlList)throws Exception;
	
	
	
	/**
	 * 加入群组
	 * @param groupType
	 * @param groupNo
	 * @param tmnlList  终端列表
	 * @throws Exception
	 */
	public void updateGroup(String groupType,String groupNo,List<CtrlParamBean> tmnlList)throws Exception;
	
	
	/**
	 * 设置共享
	 * @param groupNoArray 群组编号
	 * @param isShare      是否共享
	 * @throws DBAccessException
	 */
	public void updateShare(String groupType,String[] groupNoArray,Short isShare)throws DBAccessException;

	/**
	 * 添加群组
	 * @param tTmnlGroup 群组信息
	 * @throws DBAccessException 数据库异常
	 */
	public void saveTmnlGroup(TTmnlGroup tTmnlGroup) throws DBAccessException;
	
	/**
	 * 按类别查询群组
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroupDto> queryAllTmnlGroup(String staffNo,String groupType,String ctrlSchemeType)throws DBAccessException;	
	
	
	/**
	 * 按属性过滤条件列表查找群组信息
	 * @param filters 过滤条件列表
	 * @return 群组信息列表
	 * @throws DBAccessException 数据库异常
	 */
	public List<TTmnlGroup> findTmnlGroupBy(List<PropertyFilter> filters) throws DBAccessException;
	
	/**
	 * 按条件查找群组信息
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
    public Page<TTmnlGroupDto> qureyTmnlGroupBy(String staffNo,String groupType,String groupName,Date startDate,Date finishDate,long  start,long limit)throws DBAccessException;
	
	/**
	 * 群组更名 
	 * @param groupType 群组类别
	 * @param groupNo    群组编号
	 * @param groupName  群组名称
	 * @return
	 * @throws DBAccessException
	 */
    public Integer updateGroupName(String groupType,String groupNo,String groupName)throws DBAccessException;
	
    /**
     * 群组删除
     * @param groupType 群组类别
     * @param groupNoArray 群组编号数组
     * @return
     * @throws DBAccessException
     */
    public void deleteGroup(String groupType,List<String> groupNos)throws DBAccessException;
    
   
    /**
     * 群组明细查询
     * @param groupType 群组类别
     * @param groupNo   群组编号
     * @param consNo  用户编号
     * @return
     * @throws DBAccessException
     */
	public Page<TTmnlGroupDetailDto> queryGroupDetail(String groupType,String groupNo,String consNo,long start,long limit )throws DBAccessException;
	
	/**
	 * 删除群组明细
	 * @param groupType
	 * @param groupNo
	 * @param tmnlList
	 * @throws DBAccessException
	 */
	public void deleteGroupDetailBy(String groupType,String groupNo,List<CtrlParamBean> tmnlList)throws DBAccessException;
	
	
	
	/**
	 * @描述 分页查询用电客户
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<CCons> getAllCCons(Page page) throws Exception;
	
	
}
