
/**
 * 作者: 姜海辉
 * 创建时间：2009-11-16 上午11:36:13
 * 描述：
 */
package com.nari.sysman.securityman.impl;
import java.util.ArrayList;
import java.util.List;

import com.nari.privilige.PRole;
import com.nari.privilige.PRolePrivRela;
import com.nari.privilige.PUserRoleRela;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.IPRoleDao;
import com.nari.sysman.securityman.IPRoleManager;
import com.nari.sysman.securityman.IPRolePrivRelaDao;
import com.nari.sysman.securityman.IPRolePrivRelaJdbcDao;
import com.nari.sysman.securityman.IPUserRoleRelaDao;
import com.nari.sysman.securityman.IPUserRoleRelaJdbcDao;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.DBAccessException;

public class PRoleManagerImpl implements IPRoleManager{

	IPRoleDao iPRoleDao;
	IPRolePrivRelaDao iPRolePrivRelaDao;
	IPRolePrivRelaJdbcDao iPRolePrivRelaJdbcDao;
	IPUserRoleRelaDao iPUserRoleRelaDao;
	IPUserRoleRelaJdbcDao iPUserRoleRelaJdbcDao;
	public IPRoleDao getiPRoleDao() {
		return this.iPRoleDao;
	}

	public void setiPRoleDao(IPRoleDao iPRoleDao) {
		this.iPRoleDao = iPRoleDao;
	}

	public IPRolePrivRelaDao getiPRolePrivRelaDao() {
		return iPRolePrivRelaDao;
	}

	public void setiPRolePrivRelaDao(IPRolePrivRelaDao iPRolePrivRelaDao) {
		this.iPRolePrivRelaDao = iPRolePrivRelaDao;
	}
	
	public IPRolePrivRelaJdbcDao getiPRolePrivRelaJdbcDao() {
		return iPRolePrivRelaJdbcDao;
	}

	public void setiPRolePrivRelaJdbcDao(IPRolePrivRelaJdbcDao iPRolePrivRelaJdbcDao) {
		this.iPRolePrivRelaJdbcDao = iPRolePrivRelaJdbcDao;
	}

	public IPUserRoleRelaDao getiPUserRoleRelaDao() {
		return iPUserRoleRelaDao;
	}

	public void setiPUserRoleRelaDao(IPUserRoleRelaDao iPUserRoleRelaDao) {
		this.iPUserRoleRelaDao = iPUserRoleRelaDao;
	}

	public IPUserRoleRelaJdbcDao getiPUserRoleRelaJdbcDao() {
		return iPUserRoleRelaJdbcDao;
	}

	public void setiPUserRoleRelaJdbcDao(IPUserRoleRelaJdbcDao iPUserRoleRelaJdbcDao) {
		this.iPUserRoleRelaJdbcDao = iPUserRoleRelaJdbcDao;
	}

	/**
	 * 增加角色信息
	 * @param pRole
	 */
	public void addPRole(PRole pRole)throws Exception{
		this.iPRoleDao.save(pRole);
	}

	/**
	 * 删除角色信息
	 * @param pRole
	 */
	public void deletePRole(PRole pRole)throws Exception{
		this.iPRoleDao.delete(pRole);

	}
	
	
	

	@Override
	public String updateRoleMenu(String menus, PRole pRole) throws Exception {
		return iPRolePrivRelaJdbcDao.updateRoleMenu(menus,pRole);
	}

	/**
	 * 根据角色标识删除角色信息和相关权限
	 * @param roleId
	 */
	public void deletePRole(long roleId)throws Exception{
		this.iPRolePrivRelaJdbcDao.deleteByRoleId(roleId);
		this.iPUserRoleRelaJdbcDao.deleteByRoleId(roleId);
		this.iPRoleDao.delete(roleId);
	}

	/**
	 * 修改角色信息
	 * @param pRole
	 */
	public void updatePRole(PRole pRole)throws Exception{
		this.iPRoleDao.update(pRole);
	}

	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<PRole> getAllPRole()throws Exception{
		return this.iPRoleDao.findAll();
	}

	/**
	 * 根据角色标识查询用户信息
	 * @param roleId
	 * @return
	 */
	public PRole getAllPRole(String roleId)throws Exception{
		return  this.iPRoleDao.findById(Long.parseLong(roleId));

	}
		
	/**
	 * 根据角色名称查询存在的角色
	 * @param roleDesc 角色名称
	 * @return
	 */
	public List<PRole> findByRoleDesc(String roleDesc)throws Exception{
		return this.iPRoleDao.findByRoleDesc(roleDesc);
	}
	
	/**
	 * 根据角色名称查询存在的角色
	 * @param roleMenus 角色菜单
	 * @param roleId 角色ID
	 * @return
	 * @throws DBAccessException 
	 */
	public void updateRoleMenu(String roleMenus, Long roleId) throws Exception {
		// 删除角色菜单
		this.iPRolePrivRelaJdbcDao.deleteByRoleId(roleId);
		if(roleMenus != null && !"".equals(roleMenus)){
			String menuArray[] = roleMenus.split(",");
			if(menuArray.length>0){
				for(int i = 0; i < menuArray.length; i++){
					PRolePrivRela pRolePrivRela = new PRolePrivRela();
					pRolePrivRela.setRoleId(roleId);
					pRolePrivRela.setMenuNo(menuArray[i]);
					
					this.iPRolePrivRelaDao.save(pRolePrivRela);
				}
			}
		}
	}
	
	/**
	 * 角色树。
	 * @return
	 * @throws Exception 
	 */
	public List<TreeNodeChecked> findRoleTree(String staffNo) throws Exception {
		List<PRole> list = this.iPRoleDao.findAll();
		//判断当前的用户的权限是不是超级管理员，老周说直接过滤掉admin
		staffNo=staffNo==null?"":staffNo;
		boolean flat=staffNo.equals("admin")?true:false;
		List<TreeNodeChecked> roleInfo = new ArrayList<TreeNodeChecked>();
		for (int i = 0;  i < list.size(); i++) {
			TreeNodeChecked tn = new TreeNodeChecked();
			Long rid = list.get(i).getRoleId();
			if(!flat&&rid.equals(143l)){
				continue;
			}
			tn.setId(Long.toString(rid));
			tn.setText(list.get(i).getRoleDesc());
			tn.setLeaf(true);
			if(checkRoleChecked(list.get(i).getRoleId(), staffNo)){
				tn.setChecked(true);
			}else{
				tn.setChecked(false);
			}
			roleInfo.add(tn);
		}
		return roleInfo;
	}
	
	/*
	 * 判断某个角色是否是用户所属的角色
	 */
	private boolean checkRoleChecked(Long roleId, String staffNo) throws DBAccessException{
		boolean flag = false;
		if(staffNo != null){
			List<PUserRoleRela> list = iPUserRoleRelaDao.findByStaffNo(staffNo);	
			for (int i = 0;  i < list.size(); i++) {
				if(list.get(i).getRoleId().equals(roleId)){
					flag = true;
				}
			}
		}
		return flag;
	}

	public Page<PRole> findPage(Page<PRole> page) throws DBAccessException {
		return this.iPRoleDao.findPage(page);
	}


	public Page<PRole> findPage(Page<PRole> page, String propertyName, Object value, MatchType matchType) throws DBAccessException {
		return this.iPRoleDao.findPage(page, propertyName, value, matchType);
	}


	public Page<PRole> findPage(Page<PRole> page, List<PropertyFilter> filters) throws DBAccessException {
		return this.iPRoleDao.findPage(page, filters);
	}
}
