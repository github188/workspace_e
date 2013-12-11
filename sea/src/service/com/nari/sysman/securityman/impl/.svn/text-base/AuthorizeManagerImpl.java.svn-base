/**
 *创建人：陈建国
 *创建时间：20092009-11-15下午02:11:59
 *类描述：
 */
package com.nari.sysman.securityman.impl;

import java.util.ArrayList;
import java.util.List;

import com.nari.basicdata.VwConsType;
import com.nari.basicdata.VwConsTypeJdbcDao;
import com.nari.basicdata.VwTmnlFactory;
import com.nari.basicdata.VwTmnlFactoryJdbcDao;
import com.nari.oranization.OOrgDao;
import com.nari.orgnization.ODept;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PAccessConsType;
import com.nari.privilige.PAccessConsTypeId;
import com.nari.privilige.PAccessOrg;
import com.nari.privilige.PAccessOrgId;
import com.nari.privilige.PAccessTmnlFactory;
import com.nari.privilige.PAccessTmnlFactoryId;
import com.nari.privilige.PMenu;
import com.nari.privilige.PSysUser;
import com.nari.privilige.PSysUserJdbc;
import com.nari.privilige.PUserRoleRela;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.securityman.IAuthorizeManager;
import com.nari.sysman.securityman.IODeptDao;
import com.nari.sysman.securityman.IPAccessConsTypeJdbcDao;
import com.nari.sysman.securityman.IPAccessOrgJdbcDao;
import com.nari.sysman.securityman.IPAccessTmnlFactoryDao;
import com.nari.sysman.securityman.IPAccessTmnlFactoryJdbcDao;
import com.nari.sysman.securityman.IPSysUserDao;
import com.nari.sysman.securityman.IPUserRoleRelaDao;
import com.nari.sysman.securityman.IPUserRoleRelaJdbcDao;
import com.nari.util.TreeNode;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.DBAccessException;

/**
 * @author yutao
 * 
 */
public class AuthorizeManagerImpl implements IAuthorizeManager {

	IPSysUserDao iPSysUserDao;
	VwTmnlFactoryJdbcDao vwTmnlFactoryJdbcDao;
	IPAccessTmnlFactoryDao iPAccessTmnlFactoryDao;
	IPAccessTmnlFactoryJdbcDao iPAccessTmnlFactoryJdbcDao;
	IPUserRoleRelaDao iPUserRoleRelaDao;
	IPUserRoleRelaJdbcDao iPUserRoleRelaJdbcDao;
	VwConsTypeJdbcDao vwConsTypeJdbcDao;
	IPAccessConsTypeJdbcDao iPAccessConsTypeJdbcDao;
	OOrgDao oOrgDao;
	IODeptDao iODeptDao;
	IPAccessOrgJdbcDao iPAccessOrgJdbcDao;

	public void setiPSysUserDao(IPSysUserDao iPSysUserDao) {
		this.iPSysUserDao = iPSysUserDao;
	}

	public IPSysUserDao getiPSysUserDao() {
		return iPSysUserDao;
	}

	@Override
	public Page<PSysUserJdbc> findPage(long start, int limit)
			throws DBAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加用户信息
	 * 
	 * @param pSysUser
	 */
	public void addPSysUser(PSysUser pSysUser) throws Exception {
		this.iPSysUserDao.save(pSysUser);
	}

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PSysUser> getAllPSysUser() throws Exception {
		return this.iPSysUserDao.findAll();
	}

	public VwTmnlFactoryJdbcDao getVwTmnlFactoryJdbcDao() {
		return vwTmnlFactoryJdbcDao;
	}

	public void setVwTmnlFactoryJdbcDao(
			VwTmnlFactoryJdbcDao vwTmnlFactoryJdbcDao) {
		this.vwTmnlFactoryJdbcDao = vwTmnlFactoryJdbcDao;
	}

	public IPAccessTmnlFactoryDao getiPAccessTmnlFactoryDao() {
		return iPAccessTmnlFactoryDao;
	}

	public void setiPAccessTmnlFactoryDao(
			IPAccessTmnlFactoryDao iPAccessTmnlFactoryDao) {
		this.iPAccessTmnlFactoryDao = iPAccessTmnlFactoryDao;
	}

	public IPAccessTmnlFactoryJdbcDao getiPAccessTmnlFactoryJdbcDao() {
		return iPAccessTmnlFactoryJdbcDao;
	}

	public void setiPAccessTmnlFactoryJdbcDao(
			IPAccessTmnlFactoryJdbcDao iPAccessTmnlFactoryJdbcDao) {
		this.iPAccessTmnlFactoryJdbcDao = iPAccessTmnlFactoryJdbcDao;
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

	public void setiPUserRoleRelaJdbcDao(
			IPUserRoleRelaJdbcDao iPUserRoleRelaJdbcDao) {
		this.iPUserRoleRelaJdbcDao = iPUserRoleRelaJdbcDao;
	}

	public VwConsTypeJdbcDao getVwConsTypeJdbcDao() {
		return vwConsTypeJdbcDao;
	}

	public void setVwConsTypeJdbcDao(VwConsTypeJdbcDao vwConsTypeJdbcDao) {
		this.vwConsTypeJdbcDao = vwConsTypeJdbcDao;
	}

	public IPAccessConsTypeJdbcDao getiPAccessConsTypeJdbcDao() {
		return iPAccessConsTypeJdbcDao;
	}

	public void setiPAccessConsTypeJdbcDao(
			IPAccessConsTypeJdbcDao iPAccessConsTypeJdbcDao) {
		this.iPAccessConsTypeJdbcDao = iPAccessConsTypeJdbcDao;
	}

	public OOrgDao getoOrgDao() {
		return oOrgDao;
	}

	public void setoOrgDao(OOrgDao oOrgDao) {
		this.oOrgDao = oOrgDao;
	}

	public IODeptDao getiODeptDao() {
		return iODeptDao;
	}

	public void setiODeptDao(IODeptDao iODeptDao) {
		this.iODeptDao = iODeptDao;
	}

	public IPAccessOrgJdbcDao getiPAccessOrgJdbcDao() {
		return iPAccessOrgJdbcDao;
	}

	public void setiPAccessOrgJdbcDao(IPAccessOrgJdbcDao iPAccessOrgJdbcDao) {
		this.iPAccessOrgJdbcDao = iPAccessOrgJdbcDao;
	}

	/**
	 * 根据用户名模糊查询所有的用户信息
	 * 
	 * @param name
	 *            用户名
	 * @return 用户信息的List
	 * @throws DBAccessException
	 */
	public List<PSysUser> getAllPsPSysUserByName(String name)
			throws DBAccessException {
		return this.iPSysUserDao.findAllByName(name);
	}

	/**
	 * 根据工号删除系统用户
	 * 
	 * @param staffNo
	 *            工号
	 */
	public void deletePSysUser(String staffNo) throws DBAccessException {
		// 删除角色
		this.iPUserRoleRelaJdbcDao.delRole(staffNo);
		// 删除厂家
		this.iPAccessTmnlFactoryJdbcDao.delFac(staffNo);
		// 删除用电类型
		this.iPAccessConsTypeJdbcDao.delConsType(staffNo);
		// 删除单位权限
		this.iPAccessOrgJdbcDao.delOrg(staffNo);
		this.iPSysUserDao.delete(staffNo);
	}

	/**
	 * 保存用户信息
	 * 
	 * @param pSysUser
	 *            用户信息
	 */
	public void updatePSysUser(PSysUser pSysUser) throws DBAccessException {
		this.iPSysUserDao.update(pSysUser);
	}

	/**
	 * 更新相关权限
	 * 
	 * @param staffNo
	 * @param roles
	 *            选中的角色
	 * @param facs
	 *            选中的厂家
	 */
	public void updatePriv(String staffNo, String roles, String facs,
			String consTypes, String orgs) throws DBAccessException {
		// 删除角色
		this.iPUserRoleRelaJdbcDao.delRole(staffNo);
		// 插入角色
		if (roles != null && !"".equals(roles)) {
			String roleArray[] = roles.split(",");
			if (roleArray.length > 0) {
				for (int i = 0; i < roleArray.length; i++) {
					PUserRoleRela pUserRoleRela = new PUserRoleRela();
					pUserRoleRela.setStaffNo(staffNo);
					pUserRoleRela.setRoleId(Long.parseLong(roleArray[i]));
					this.iPUserRoleRelaDao.save(pUserRoleRela);
				}
			}
		}
		// 删除厂家
		this.iPAccessTmnlFactoryJdbcDao.delFac(staffNo);
		// 插入厂家
		if (facs != null && !"".equals(facs)) {
			String facArray[] = facs.split(",");
			if (facArray.length > 0) {
				for (int i = 0; i < facArray.length; i++) {
					PAccessTmnlFactory pAccessTmnlFactory = new PAccessTmnlFactory();

					PAccessTmnlFactoryId pAccessTmnlFactoryId = new PAccessTmnlFactoryId();
					pAccessTmnlFactoryId.setStaffNo(staffNo);
					pAccessTmnlFactoryId.setFactoryCode(facArray[i]);

					pAccessTmnlFactory.setId(pAccessTmnlFactoryId);
					this.iPAccessTmnlFactoryJdbcDao.saveFac(pAccessTmnlFactory);
				}
			}
		}
		// 删除用电类型
		this.iPAccessConsTypeJdbcDao.delConsType(staffNo);
		// 插入用电类型
		if (consTypes != null && !"".equals(consTypes)) {
			String consTypeArray[] = consTypes.split(",");
			if (consTypeArray.length > 0) {
				for (int i = 0; i < consTypeArray.length; i++) {
					PAccessConsType pAccessConsType = new PAccessConsType();

					PAccessConsTypeId pAccessConsTypeId = new PAccessConsTypeId();
					pAccessConsTypeId.setStaffNo(staffNo);
					pAccessConsTypeId.setConsType(Byte
							.parseByte(consTypeArray[i]));

					pAccessConsType.setId(pAccessConsTypeId);
					this.iPAccessConsTypeJdbcDao.saveConsType(pAccessConsType);
				}
			}
		}
		// 删除单位权限
		this.iPAccessOrgJdbcDao.delOrg(staffNo);
		// 插入单位权限
		if (orgs != null && !"".equals(orgs)) {
			String orgArray[] = orgs.split(",");
			if (orgArray.length > 0) {
				// 保存顶级访问权限
				PAccessOrg ppAccessOrg = new PAccessOrg();
				PAccessOrgId ppAccessOrgId = new PAccessOrgId();
				ppAccessOrgId.setStaffNo(staffNo);
				PSysUser pSysUser = this.iPSysUserDao.findById(staffNo);
				ppAccessOrgId.setOrgNo(pSysUser.getOrgNo());
				OOrg poOrg = this.oOrgDao.findById(pSysUser.getOrgNo());
				ppAccessOrgId.setOrgType(poOrg.getOrgType());
				ppAccessOrg.setId(ppAccessOrgId);
				this.iPAccessOrgJdbcDao.saveOrg(ppAccessOrg);
				// 保存其他级别权限
				for (int i = 0; i < orgArray.length; i++) {
					PAccessOrg pAccessOrg = new PAccessOrg();

					PAccessOrgId pAccessOrgId = new PAccessOrgId();
					pAccessOrgId.setStaffNo(staffNo);
					pAccessOrgId.setOrgNo(orgArray[i]);

					OOrg oOrg = new OOrg();
					oOrg = this.oOrgDao.findById(orgArray[i]);
					pAccessOrgId.setOrgType(oOrg.getOrgType());

					pAccessOrg.setId(pAccessOrgId);
					this.iPAccessOrgJdbcDao.saveOrg(pAccessOrg);

					if ("03".equals(oOrg.getOrgType())) {
						// 递归取下级所有区县
						List<OOrg> list = this.oOrgDao.findByPId(orgArray[i]);
						for (int j = 0; j < list.size(); j++) {
							pAccessOrgId.setOrgNo(list.get(j).getOrgNo());
							pAccessOrgId.setOrgType(list.get(j).getOrgType());
							pAccessOrg.setId(pAccessOrgId);
							this.iPAccessOrgJdbcDao.saveOrg(pAccessOrg);
						}
					}
				}
			}
		}
	}

	/**
	 * 厂家树
	 */
	public List<TreeNodeChecked> findFacTree(String staffNo)
			throws DBAccessException {
		List<VwTmnlFactory> list = this.vwTmnlFactoryJdbcDao.findAll();
		List<TreeNodeChecked> facInfo = new ArrayList<TreeNodeChecked>();
		for (int i = 0; i < list.size(); i++) {
			TreeNodeChecked tn = new TreeNodeChecked();
			tn.setId(list.get(i).getFactoryCode());
			tn.setText(list.get(i).getFactoryName());
			tn.setLeaf(true);
			if (checkRoleChecked(list.get(i).getFactoryCode(), staffNo)) {
				tn.setChecked(true);
			} else {
				tn.setChecked(false);
			}
			facInfo.add(tn);
		}
		return facInfo;
	}

	/**
	 * 用电用户类型树
	 */
	public List<TreeNodeChecked> findConsTypeTree(String staffNo)
			throws DBAccessException {
		List<VwConsType> list = this.vwConsTypeJdbcDao.findAll();
		List<TreeNodeChecked> consTypeInfo = new ArrayList<TreeNodeChecked>();
		for (int i = 0; i < list.size(); i++) {
			TreeNodeChecked tn = new TreeNodeChecked();
			tn.setId(list.get(i).getConsType());
			tn.setText(list.get(i).getConsTypeName());
			tn.setLeaf(true);
			if (checkConsTypeChecked(list.get(i).getConsType(), staffNo)) {
				tn.setChecked(true);
			} else {
				tn.setChecked(false);
			}
			consTypeInfo.add(tn);
		}
		return consTypeInfo;
	}

	/*
	 * 判断某个用电用户类型是否是操作员所属的类型
	 */
	private boolean checkConsTypeChecked(String consType, String staffNo)
			throws DBAccessException {
		boolean flag = false;
		if (staffNo != null) {
			List<PAccessConsType> list = iPAccessConsTypeJdbcDao
					.findByStaffNo(staffNo);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId().getConsType().equals(
						Byte.parseByte(consType))) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/*
	 * 判断某个厂家是否是用户所属的厂家
	 */
	private boolean checkRoleChecked(String factoryCode, String staffNo)
			throws DBAccessException {
		boolean flag = false;
		if (staffNo != null) {
			List<PAccessTmnlFactory> list = iPAccessTmnlFactoryJdbcDao
					.findByStaffNo(staffNo);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId().getFactoryCode().equals(factoryCode)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 单位树
	 */
	public List<TreeNodeChecked> findOrgTree(String orgNo, String staffNo)
			throws DBAccessException {
		// 处理客户中心本部的权限级别放大的问题
		if (orgNo != null && !"".equals(orgNo)) {
			OOrg oorg = this.oOrgDao.findById(orgNo);
			PSysUser pSysUser = this.iPSysUserDao.findByStaffNoAndPWD(staffNo,
					null);

			String orgType = oorg.getOrgType();

			if (null != pSysUser.getAccessLevel()) {			
				String accLevel = pSysUser.getAccessLevel();

				if (null != accLevel && !orgType.equals(accLevel)
						&& Integer.valueOf(orgType) > Integer.valueOf(accLevel)) {
					// if (!oorg.getOrgType().equals(pSysUser.getAccessLevel()))
					// {
					orgNo = oorg.getPOrgNo();
				}
			}
		}

		List<OOrg> list = this.oOrgDao.findByPId(orgNo);
		List<TreeNodeChecked> orgInfo = new ArrayList<TreeNodeChecked>();
		for (int i = 0; i < list.size(); i++) {
			TreeNodeChecked tn = new TreeNodeChecked();
			tn.setId(list.get(i).getOrgNo());
			tn.setText(list.get(i).getOrgName());
			tn.setLeaf(true);
			if (checkOrgChecked(list.get(i).getOrgNo(), staffNo)) {
				tn.setChecked(true);
			} else {
				tn.setChecked(false);
			}
			orgInfo.add(tn);
		}
		return orgInfo;
	}

	/*
	 * 判断操作员的单位权限
	 */
	private boolean checkOrgChecked(String orgNo, String staffNo)
			throws DBAccessException {
		boolean flag = false;
		if (staffNo != null) {
			List<PAccessOrg> list = iPAccessOrgJdbcDao.findByStaffNo(staffNo);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getId().getOrgNo().equals(orgNo)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public Page<PSysUser> findPage(Page<PSysUser> page)
			throws DBAccessException {
		return this.iPSysUserDao.findPage(page);

	}

	public Page<PSysUser> findPage(Page<PSysUser> page, String propertyName,
			Object value, MatchType matchType) throws DBAccessException {
		return this.iPSysUserDao.findPage(page, propertyName, value, matchType);
	}

	public Page<PSysUser> findPage(Page<PSysUser> page,
			List<PropertyFilter> filters) throws DBAccessException {
		return this.iPSysUserDao.findPage(page, filters);
	}

	@Override
	public PSysUser findByStaffNo(String staffNo) throws DBAccessException {

		return iPSysUserDao.findByStaffNo(staffNo);
	}

	@Override
	public List<TreeNode> findOrgByNode(String node, PSysUser pSysUser)
			throws DBAccessException {

		List<OOrg> list = null;

		if (node.equals("orgtree_root") || node.isEmpty()) {

			list = this.oOrgDao.findBy("orgNo", pSysUser.getOrgNo(), "sortNo",
					"asc");

			OOrg oorg = list.get(0);
			if (!oorg.getOrgType().equals(pSysUser.getAccessLevel()))
				list = this.oOrgDao.findBy("orgNo", oorg.getPOrgNo(), "sortNo",
						"asc");

		} else {
			list = this.oOrgDao.findByPId(node);
		}
		List<TreeNode> orgInfo = new ArrayList<TreeNode>();

		for (int i = 0; i < list.size(); i++) {
			TreeNode tn = new TreeNode();
			tn.setId(list.get(i).getOrgNo());
			tn.setName(list.get(i).getOrgName());
			tn.setText(list.get(i).getOrgName());

			orgInfo.add(tn);
		}
		return orgInfo;
	}

	@Override
	public ODept getDeptById(String deptNo) throws DBAccessException {
		// TODO Auto-generated method stub
		return this.iODeptDao.findById(deptNo);
	}

	@Override
	public OOrg getOrgById(String orgNo) throws DBAccessException {
		// TODO Auto-generated method stub
		return this.oOrgDao.findById(orgNo);
	}

	@Override
	public List<TreeNode> findDeptByNode(String node) throws DBAccessException {
		// TODO Auto-generated method stub
		List<ODept> list = null;
		list = this.iODeptDao.findByPId(node);
		List<TreeNode> deptInfo = new ArrayList<TreeNode>();

		for (int i = 0; i < list.size(); i++) {
			TreeNode tn = new TreeNode();
			tn.setId(list.get(i).getDeptNo());
			tn.setName(list.get(i).getName());
			tn.setText(list.get(i).getName());

			deptInfo.add(tn);
		}
		return deptInfo;
	}

	@Override
	public List<ODept> findAllDept() throws DBAccessException {
		// TODO Auto-generated method stub
		return iODeptDao.findAll();
	}

	@Override
	public List<OOrg> findAllOrg() throws DBAccessException {
		// TODO Auto-generated method stub
		return oOrgDao.findAll();
	}

}
