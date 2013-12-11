package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/***
 *   控制方案的jdbc 接口
 * @author  huangxuan 
 * ***/
public interface IWCtrlSchemeJdbcDao {
	/***
	 * 通过一个包含id的list在数据库中找到能够删除的list的借口
	 * 当前表中的id类型为number型.  
	 * 注意: 这个方法能返回有效的id列表 ，同时他对参数listId本身也进行了处理
	 * 删除了listId指向对象中的按需求是不能删除的id,保证在以后用到listId的时候正确,统一.
	 * 但是要注意这个方法必须先被执行
	 * @param listId 待删除的id列表  listId待删除的方案的id
	 * @return 满足需求要求能被删除的id列表
	 * @throws DBAccessException 
	 * **/
	@SuppressWarnings("unchecked")
	public <T> List<T> findValidateIds(List listId) throws DBAccessException;
	/**
	 * 删除经过验证满足要求的那些id对应的控制方案
	 *注意： 对不满足删除要求的已经进行了过来删除
	 *在内部调用了findValidateIds方法
	 * @param listId待删除的方案的id id必须是数字型的
	 * *
	 * @throws DBAccessException **/
	public void deleteValidateIds(List listId) throws DBAccessException;
	/***
	 * 找到所有的方案
	 *找到所有额控制方案的名称和对应的编号
	 * **/
	public Page findAllSchemeNames(String staffNo,long start,int limit);
	
	/**
	 * 按条件查询WCtrlScheme分页记录
	 * @param scheme
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<WCtrlScheme> findPage(PSysUser staff, WCtrlScheme scheme, long start, int limit);
}
