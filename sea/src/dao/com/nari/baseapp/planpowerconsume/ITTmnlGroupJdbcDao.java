package com.nari.baseapp.planpowerconsume;

import java.util.Date;
import java.util.List;

import com.nari.support.Page;
import com.nari.terminalparam.TTmnlGroupDto;
import com.nari.util.exception.DBAccessException;


/**
 * 群组设置JdbcDao接口
 * @author姜海辉
 *
 */

public interface ITTmnlGroupJdbcDao {
	
	/**
	 * 查询所有普通群组名称
	 * @return
	 * @throws DBAccessException
	 */
	public List<TTmnlGroupDto> findCommonGroup(String staffNo)throws DBAccessException;
	
//	/**
//	 * 查询所有控制群组名称
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public List<TTmnlGroupDto> findCtrlGroup()throws DBAccessException;
	
	 /**
     * 按照控制类别查询控制群组名称
     * @param ctrlSchemeType 控制方案分类 
     * @return
	 * @throws DBAccessException
     */
	public List<TTmnlGroupDto> findCtrlGroup(String staffNo,String ctrlSchemeType)throws DBAccessException;
	
	/**
	 * 按条件查找普通群组信息
	 * @param groupType  群组类别
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDto> findCommonGroupBy(String staffNo,String groupName,Date startDate,Date finishDate,long start,long limit) throws DBAccessException;
	
	/**
	 * 按条件查找控制群组信息
	 * @param groupType  群组类别
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDto> findCtrlGroupBy(String staffNo,String groupName,Date startDate,Date finishDate,long start,long limit) throws DBAccessException;

}
