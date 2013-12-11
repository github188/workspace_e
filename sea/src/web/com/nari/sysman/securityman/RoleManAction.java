/**
 * 作者:
 * 创建时间：2009-11-16 下午01:59:06
 * 描述：
 */

package com.nari.sysman.securityman;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.action.baseaction.Errors;
import com.nari.privilige.PRole;
import com.nari.support.Page;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.TreeNodeChecked;

public class RoleManAction extends BaseAction {

	private static Logger logger = Logger.getLogger(RoleManAction.class);

	IPRoleManager iPRoleManager;

	// 通过此种注释方式可以使生成json 时不生成这个变量
	@JSON(serialize = false)
	public IPRoleManager getiPRoleManager() {
		return this.iPRoleManager;
	}

	public void setiPRoleManager(IPRoleManager iPRoleManager) {
		this.iPRoleManager = iPRoleManager;
	}

	// 要返回的值
	public List<PRole> root;
	public List<TreeNodeChecked> treeNodeList;

	public long totalCount;
	public boolean success = true;
	// 浏览器端请求的参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;

	private Long roleId;
	private String roleDesc;
	private String fieldTypeCode;
	private String roleGrantFlag;
	private Byte rwStation;
	private Byte rwTmnl;
	private String roleLevel;
	// 选中的角色菜单
	private String roleMenus;
	private String staffNo;

	private String msg; // 出错信息

	public String execute() throws Exception {
		try {
			Page<PRole> page = new Page<PRole>(this.getStart(), this.getLimit());
			Page<PRole> pi = this.iPRoleManager.findPage(page);
			List<PRole> list = pi.getResult();
			PRole pRole = null;
			for (int i = 0; i < list.size(); i++) {
				pRole = list.get(i);
				if ("01".equals(pRole.getFieldTypeCode())) {
					pRole.setFieldTypeCode("计量");
				} else if ("02".equals(pRole.getFieldTypeCode())) {
					pRole.setFieldTypeCode("电费");
				} else if ("03".equals(pRole.getFieldTypeCode())) {
					pRole.setFieldTypeCode("95598");
				} else if ("04".equals(pRole.getFieldTypeCode())) {
					pRole.setFieldTypeCode("抄表");
				} else if ("05".equals(pRole.getFieldTypeCode())) {
					pRole.setFieldTypeCode("用检");
				}
				if ("02".equals(pRole.getRoleLevel())) {
					pRole.setRoleLevel("网省");
				} else if ("03".equals(pRole.getRoleLevel())) {
					pRole.setRoleLevel("地市");
				} else if ("04".equals(pRole.getRoleLevel())) {
					pRole.setRoleLevel("区县");
				} else if ("06".equals(pRole.getRoleLevel())) {
					pRole.setRoleLevel("供电所");
				}
			}
			this.root = pi.getResult();

			this.setTotalCount(pi.getTotalCount());
			// System.out.println("记录数："+pi.getTotalCount());
		} catch (Exception e) {
			logger.error(e);

		}

		return "success";
	}

	public String addRole() throws Exception {
		try {
			this.logger.debug("创建新角色开始");
			List<PRole> tlist = this.iPRoleManager.findByRoleDesc(roleDesc);
			if (tlist != null && tlist.size() > 0) {
				msg = "该角色名已经存在！";
				return SUCCESS;
			}

			PRole p = new PRole();
			p.setRoleDesc(this.roleDesc);
			p.setFieldTypeCode(this.fieldTypeCode);
			p.setRoleGrantFlag(this.roleGrantFlag);
			p.setRwStation(this.rwStation);
			p.setRwTmnl(this.rwTmnl);
			p.setRoleLevel(this.roleLevel);
			// System.out.println("。。。。。。。。。。。开始添加");
			this.iPRoleManager.addPRole(p);
			// System.out.println("。。。。。。。。。。。添加成功");
			// 添加角色菜单
			List<PRole> list = this.iPRoleManager.findByRoleDesc(roleDesc);
			this.iPRoleManager.updateRoleMenu(this.roleMenus, list.get(0)
					.getRoleId());
			super.addUserLog("增加角色－－角色名称:" + p.getRoleDesc(), "操作员管理", "02");
		} catch (Exception e) {
			logger.error(e);
		}
		return "success";
	}

	public String deleteRole() throws Exception {

		try {

			this.iPRoleManager.deletePRole(this.roleId);
			super.addUserLog("删除角色－－角色ID" + this.roleId, "角色管理", "03");
		} catch (Exception e) {
			logger.error(e);
		}
		return "success";
	}

	public String updateRole() throws Exception {

		try {

			PRole p = new PRole();
			p.setRoleId(this.roleId);
			p.setRoleDesc(this.roleDesc);
			p.setFieldTypeCode(this.fieldTypeCode);
			p.setRoleGrantFlag(this.roleGrantFlag);
			p.setRwStation(this.rwStation);
			p.setRwTmnl(this.rwTmnl);
			p.setRoleLevel(this.roleLevel);
			// this.iPRoleManager.updatePRole(p);
			// 修改角色菜单
			// this.iPRoleManager.updateRoleMenu(this.roleMenus, this.roleId);
			String flag = iPRoleManager.updateRoleMenu(getRoleMenus(), p);

			if (!flag.equals("success")) {
				setSuccess(false);
				Errors er = new Errors();
				er.setMsg("保存角色失败");
				er.setTitle("错误");

			} else if (this.getRoleId() == null || this.getRoleId() == 0)
				super
						.addUserLog("增加角色－－角色名称:" + p.getRoleDesc(), "操作员管理",
								"02");
			else
				super
						.addUserLog("修改角色－－角色名称： " + p.getRoleDesc(), "角色管理",
								"04");
		} catch (Exception e) {
			logger.error(e);
		}
		return "success";
	}

	public String queryRole() throws Exception {
		try {
			if (roleDesc != null && !"".equals(roleDesc)) {
				// List<PropertyFilter> filters=new ArrayList<PropertyFilter>();
				// PropertyFilter f = new PropertyFilter("roleDesc",roleDesc);
				// filters.add(f);
				String propertyName = "roleDesc";
				Page<PRole> page = new Page<PRole>(this.getStart(), this
						.getLimit());
				Page<PRole> pi = this.iPRoleManager.findPage(page,
						propertyName, "%" + roleDesc + "%", MatchType.LIKE);
				List<PRole> list = pi.getResult();
				PRole pRole = null;
				for (int i = 0; i < list.size(); i++) {
					pRole = list.get(i);
					if ("01".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("计量");
					} else if ("02".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("电费");
					} else if ("03".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("95598");
					} else if ("04".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("抄表");
					} else if ("05".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("用检");
					}
					if ("02".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("网省");
					} else if ("03".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("地市");
					} else if ("04".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("区县");
					} else if ("06".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("供电所");
					}
				}
				this.root = pi.getResult();
			} else {
				Page<PRole> page = new Page<PRole>(this.getStart(), this
						.getLimit());
				Page<PRole> pi = this.iPRoleManager.findPage(page);
				List<PRole> list = pi.getResult();
				PRole pRole = null;
				for (int i = 0; i < list.size(); i++) {
					pRole = list.get(i);
					if ("01".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("计量");
					} else if ("02".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("电费");
					} else if ("03".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("95598");
					} else if ("04".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("抄表");
					} else if ("05".equals(pRole.getFieldTypeCode())) {
						pRole.setFieldTypeCode("用检");
					}
					if ("02".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("网省");
					} else if ("03".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("地市");
					} else if ("04".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("区县");
					} else if ("06".equals(pRole.getRoleLevel())) {
						pRole.setRoleLevel("供电所");
					}
				}
				this.root = pi.getResult();
				this.setTotalCount(pi.getTotalCount());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "success";
	}

	/* 角色树 */
	public String roleTree() throws Exception {
		this.treeNodeList = this.iPRoleManager.findRoleTree(this.staffNo);
		return SUCCESS;
	}

	public long getStart() {
		return this.start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<PRole> getRoot() {
		return this.root;
	}

	public void setRoot(List<PRole> root) {
		this.root = root;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getFieldTypeCode() {
		return this.fieldTypeCode;
	}

	public void setFieldTypeCode(String fieldTypeCode) {
		this.fieldTypeCode = fieldTypeCode;
	}

	public String getRoleGrantFlag() {
		return this.roleGrantFlag;
	}

	public void setRoleGrantFlag(String roleGrantFlag) {
		this.roleGrantFlag = roleGrantFlag;
	}

	public Byte getRwStation() {
		return this.rwStation;
	}

	public void setRwStation(Byte rwStation) {
		this.rwStation = rwStation;
	}

	public Byte getRwTmnl() {
		return this.rwTmnl;
	}

	public void setRwTmnl(Byte rwTmnl) {
		this.rwTmnl = rwTmnl;
	}

	public String getRoleLevel() {
		return this.roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(String roleMenus) {
		this.roleMenus = roleMenus;
	}

	public List<TreeNodeChecked> getTreeNodeList() {
		return treeNodeList;
	}

	public void setTreeNodeList(List<TreeNodeChecked> treeNodeList) {
		this.treeNodeList = treeNodeList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
