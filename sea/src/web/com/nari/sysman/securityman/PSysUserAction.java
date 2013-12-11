package com.nari.sysman.securityman;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 类 PSysUserAction
 * 
 * @author zhangzhw
 * @describe 系统用户列表的演示action
 */
public class PSysUserAction extends BaseAction {

	// 要返回的值
	public List<PSysUser> root;
	public long totalCount;

	// 浏览器端请求的参数
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;

	// 动态注入的东西 实际开发的时候不允许直接使用DAO，需要从服务层取得数据
	public IPSysUserManager iPSysUserManager;

	// public IPSysUserDao iPSysUserDao;

	public String execute() throws Exception {
		Page<PSysUser> page = new Page<PSysUser>(this.getStart(), this
				.getLimit());
		Page<PSysUser> pi = this.iPSysUserManager.findPage(page);
		// 正常可以直接使用这种方式，但注入的东西目前调试通不过，暂时使用复制对象处理
		setRoot(pi.getResult());
		// if (start == 0)
		// throw new DBAccessException("unkown error");
		// this.root = new ArrayList<PSysUser>();
		// for (int i = 0; i < pi.getResult().size(); i++) {
		// PSysUser p = new PSysUser();
		// PSysUser p1 = pi.getResult().get(i);
		// p.setEmpNo(p1.getEmpNo());
		// p.setName(p1.getName());
		// p.setIp(p1.getIp());
		// p.setMac(p1.getMac());
		// p.setLockTime(p1.getLockTime());
		// this.root.add(p);
		// }

		this.setTotalCount(pi.getTotalCount());
		return "success";
	}

	public List<PSysUser> getRoot() {
		return this.root;
	}

	public void setRoot(List<PSysUser> root) {
		this.root = root;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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

	// @JSON(serialize = false)
	// public IPSysUserDao getiPSysUserDao() {
	// return iPSysUserDao;
	// }
	//
	// public void setiPSysUserDao(IPSysUserDao iPSysUserDao) {
	// this.iPSysUserDao = iPSysUserDao;
	// }

	// 通过此种注释方式可以使生成json 时不生成这个变量
	@JSON(serialize = false)
	public IPSysUserManager getiPSysUserManager() {
		return this.iPSysUserManager;
	}

	public void setiPSysUserManager(IPSysUserManager iPSysUserManager) {
		this.iPSysUserManager = iPSysUserManager;
	}

}
