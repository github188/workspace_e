package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.orderlypower.WCtrlScheme;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/**
 * 方案控制表操作Dao接口
 * @author 姜炜超
 */
public interface IWCtrlSchemeDao {
	
	/**
	 * 主要用来新增加方案写入方案主表
	 * @param scheme
	 * @throws DBAccessException
	 */
	public void save(WCtrlScheme scheme) throws DBAccessException;
	
	/**
	 * 主要用来修改方案写入方案主表
	 * @param scheme
	 * @throws DBAccessException
	 */
	public void update(WCtrlScheme scheme) throws DBAccessException;
	
	
	/**
	 * 主要用来新增加方案写入方案主表，或者修改方案写入方案主表
	 * @param scheme 
	 * @return 
	 */
    public void saveOrUpdate(WCtrlScheme scheme) throws DBAccessException;
    
    /**
	 * 主要用来查询方案
	 * @param filters 
	 * @param value
	 * @return List<WCtrlScheme>
	 */
	public List<WCtrlScheme> findBy(List<PropertyFilter> filters)throws DBAccessException;
	
    /**
	 * 主要用来查询某种类型的所有方案
	 * @param propertyName 
	 * @param value
	 * @return List<WCtrlScheme>
	 */
	public List<WCtrlScheme> findBy(String propertyName, Object value)throws DBAccessException;
	
	/**
	 * 
	 * @param page
	 * @return
	 * @throws DBAccessException
	 */
	public Page <WCtrlScheme> findAllWCtrlScheme(Page page) throws DBAccessException;

	public  Page<WCtrlScheme> findPage(WCtrlScheme scheme,long start, int limit) throws DBAccessException;

	/**
	 * 按方案类型和操作员编号查询方案
	 * @param schemeType
	 * @param staffNo
	 * @return
	 */
	public List<WCtrlScheme> findBySchemeTypeAndStaffNo(String schemeType, String staffNo)  throws DBAccessException;
}
