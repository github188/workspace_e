package com.nari.sysman.securityman.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PMenu;
import com.nari.privilige.PRolePrivRela;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.IPMenuDao;
import com.nari.sysman.securityman.IPMenuManager;
import com.nari.sysman.securityman.IPRolePrivRelaDao;
import com.nari.util.TreeNodeChecked;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;
import com.oracle.coherence.common.logging.Logger;

/**
 * 类 PMenuMangerImpl
 * 
 * @author zhangzhw
 * @version 2009年11月17日20时27分
 * @describe 菜单管理服务实现类
 */
public class PMenuMangerImpl implements IPMenuManager {

	public IPMenuDao iPMenuDao;

	public IPRolePrivRelaDao iPRolePrivRelaDao;

	@Override
	public List<PMenu> findByUpid(String PMenuNo, PSysUser pSysUser)
			throws Exception {
		try {
			return this.iPMenuDao.findByUpid(PMenuNo, pSysUser);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYMENUEXCEPTION);
		}
	}

	public List<TreeNodeChecked> findMenu(String node, String roleId,
			PSysUser pSysUser) throws Exception {
		try {
			// 在DAO 层取出全部菜单，按上级菜单排序
			List<PMenu> list = iPMenuDao.findAllMenu();
			List<PMenu> checkedList = iPMenuDao.findByRole(roleId);

			List<TreeNodeChecked> treeNode = new ArrayList<TreeNodeChecked>();

			/**
			 * 按上级菜单排序后循环处理
			 */
			for (PMenu p : list) {
				// 一级菜单处理
				if (p.getPMenuNo().equals("0")) {
					treeNode.add(covertPMenu2Tree(p, checkedList));
					continue;
				}
				// //二级及三级菜单处理
				loopout: for (TreeNodeChecked t : treeNode) {
					// 对比当前取到的菜单p的上级菜单id与当前构造的树 t 节点id比较
					// 是则属于当前树节点的子节点，直接退出循环
					// 否则循环树节点的子节点
					if (t.getId().equals(p.getPMenuNo())) {
						t.getChildren().add(covertPMenu2Tree(p, checkedList));
						break loopout;
					} else {
						for (TreeNodeChecked tc : t.getChildren()) {
							if (tc.getId().equals(p.getPMenuNo())) {
								tc.getChildren().add(
										covertPMenu2Tree(p, checkedList));
								// 找到则直接退出最外层循环
								break loopout;
							}else{
								if(!tc.isLeaf()){
									for(TreeNodeChecked tnc : tc.getChildren()){
										if(tnc.getId().equals(p.getPMenuNo())){
											tnc.getChildren().add(covertPMenu2Tree(p, checkedList));
											break loopout;
										}
									}
									
								}
							}
						}
					}
				}

			}

			return treeNode;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_QUERYTREEMENUEXCEPTION);
		}

	}

	/**
	 * 方法 covertPMenu2Tree
	 * 
	 * @param p
	 * @param checkedList
	 * @return 从菜单和权限生成 菜单树节点
	 */
	public TreeNodeChecked covertPMenu2Tree(PMenu p, List<PMenu> checkedList) {
		TreeNodeChecked tn = new TreeNodeChecked();
		tn.setId(p.getMenuNo());
		tn.setName(p.getTitle());
		tn.setText(p.getTitle());
		if (p.getMenuFolderFlag().equals("1")) {
			tn.setLeaf(false);
			tn.setChildren(new ArrayList<TreeNodeChecked>());
		} else {
			tn.setLeaf(true);
		}

		for (PMenu pl : checkedList) {
			if (pl.getMenuNo().equals(p.getMenuNo())) {
				tn.setChecked(true);
				break;
			}
		}

		return tn;
	}

	// @Override
	// public List<TreeNodeChecked> findMenu(String node, String roleId,
	// PSysUser pSysUser) throws DBAccessException {
	// // TODO Auto-generated method stub
	// List<PMenu> list = null;
	// //
	// if (node.equals("menu_root")) {
	// // 取一级菜单，一级菜单的上级菜单编码是“0”
	// list = iPMenuDao.findByUpid("0", pSysUser);
	// } else {
	// list = iPMenuDao.findByUpid(node, pSysUser);
	// }
	//
	// List<TreeNodeChecked> menuInfo = new ArrayList<TreeNodeChecked>();
	//
	// for (int i = 0; i < list.size(); i++) {
	// TreeNodeChecked tn = new TreeNodeChecked();
	// tn.setId(list.get(i).getMenuNo());
	// tn.setName(list.get(i).getTitle());
	// tn.setText(list.get(i).getTitle());
	// if (list.get(i).getMenuFolderFlag().equals("1")) {
	// tn.setLeaf(false);
	// List<TreeNodeChecked> children = this.findMenu(list.get(i)
	// .getMenuNo(), roleId, pSysUser);
	// tn.setChildren(children);
	// } else {
	// tn.setLeaf(true);
	// }
	// if (roleId != null) {
	// if (checkMenuChecked(list.get(i).getMenuNo(), Long
	// .parseLong(roleId))) {
	// tn.setChecked(true);
	// } else {
	// tn.setChecked(false);
	// }
	// } else {
	// tn.setChecked(false);
	// }
	// menuInfo.add(tn);
	// }
	//
	// return menuInfo;
	// }
	//
	// /*
	// * 判断某个菜单是否是角色拥有权限的菜单
	// */
	// private boolean checkMenuChecked(String menuNo, long roleId)
	// throws DBAccessException {
	// List<PRolePrivRela> list = iPRolePrivRelaDao.findByRoleId(roleId);
	// boolean flag = false;
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).getMenuNo() != null) {
	// if (list.get(i).getMenuNo().equals(menuNo)) {
	// flag = true;
	// }
	// }
	// }
	// return flag;
	// }

	public IPMenuDao getiPMenuDao() {
		return this.iPMenuDao;
	}

	public void setiPMenuDao(IPMenuDao iPMenuDao) {
		this.iPMenuDao = iPMenuDao;
	}

	public IPRolePrivRelaDao getiPRolePrivRelaDao() {
		return iPRolePrivRelaDao;
	}

	public void setiPRolePrivRelaDao(IPRolePrivRelaDao iPRolePrivRelaDao) {
		this.iPRolePrivRelaDao = iPRolePrivRelaDao;
	}

}
