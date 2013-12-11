package com.nari.sysman.securityman.impl;

import com.nari.privilige.PSysUser;

/**
 * static类　SysPrivilige
 * 
 * @author zhangzhw
 * @describe 各个方法返回SQL为 AND *** ? *** 的形式 　　　　　　　查询时要根据添加条件的数量，在查询参数中代入相
 *           　同数量的　staffNo
 */
public class SysPrivilige {

	/**
	 * 方法　addOrgPri
	 * 
	 * @param username
	 *            用户名staffNo
	 * @param tbname
	 *            org_No 字段的表名
	 * @return　添加访问org权限的　SQL语句条件　 针对 c_cons表
	 */
	public static String addOrgPri(PSysUser username, String tbname) {
		String newTbName = tbname;
		if (newTbName == null || newTbName.isEmpty())
			newTbName = "C_CONS";

		StringBuffer sb = new StringBuffer();

		// 判断用户权限级别　
		/*
		 * if (username.getAccessLevel().equals("02")) {
		 * 
		 * } else
		 */
		if (Integer.valueOf(username.getAccessLevel()) <= 4) {
			// 地市级权限　则取　area_no
			sb.append("AND EXISTS (SELECT 1\n");
			sb.append("         FROM P_ACCESS_ORG PRI_ORG\n");
			sb.append("        WHERE PRI_ORG.ORG_NO = " + newTbName
					+ ".AREA_NO\n");
			sb.append("          AND PRI_ORG.STAFF_NO =?)");
		} else // 供电所权限直接取供电所　
		{
			sb.append("AND EXISTS (SELECT 1\n");
			sb.append("         FROM P_ACCESS_ORG PRI_ORG\n");
			sb.append("        WHERE PRI_ORG.ORG_NO = " + newTbName
					+ ".ORG_NO\n");
			sb.append("          AND PRI_ORG.STAFF_NO =?)");
		}

		String sql = sb.toString();

		return sql;
	}

	/**
	 * 方法　addOrgPri
	 * 
	 * @param username
	 *            用户名staffNo
	 * @param tbname
	 *            org_No 字段的表名
	 * @return　添加访问org权限的　SQL语句条件　 针对org_No 字段权限
	 */
	public static String addOrgPriOrg(PSysUser username, String unitType,
			String tbname) {
		String newTbName = tbname;
		if (newTbName == null || newTbName.isEmpty())
			newTbName = "O_ORG";

		StringBuffer sb = new StringBuffer();

		// 取用户最大权限　无父节点的情况
		if (unitType.equals("00")) {
			sb.append(" AND EXISTS (SELECT 1\n");
			sb.append("         FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n");
			sb.append("        WHERE P_A.STAFF_NO = P_U.STAFF_NO\n");
			sb.append("          AND P_U.ACCESS_LEVEL = P_A.ORG_TYPE\n");
			sb.append("          AND P_A.ORG_NO =  " + newTbName + ".ORG_NO\n");
			sb.append("          AND P_A.STAFF_NO ='" + username.getStaffNo()
					+ "'  ) ");
		}// 有父节点的情况
		else {
			int orgType = Integer.valueOf(unitType);
			int accLevel = Integer.valueOf(username.getAccessLevel());

			// accLevel 有网省　、地市　、区县　

			if (orgType == 2) { // 通过网省级父节点==2取地市级，仅对网省级权限进行过滤
				// 仅有网省用户才存在地市用户权限
				sb.append(" AND EXISTS (SELECT 1\n");
				sb.append("         FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n");
				sb.append("        WHERE P_A.STAFF_NO = P_U.STAFF_NO\n");
				sb.append("          AND P_A.ORG_TYPE='03' \n");
				sb.append("          AND P_A.ORG_NO =  " + newTbName
						+ ".ORG_NO\n");
				sb.append("          AND P_A.STAFF_NO ='"
						+ username.getStaffNo() + "'  ) ");

			} else if (orgType == 3) { // 地市级父节点取区县级
				if (accLevel == 3) {
					sb.append(" AND EXISTS (SELECT 1\n");
					sb
							.append("         FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n");
					sb.append("        WHERE P_A.STAFF_NO = P_U.STAFF_NO\n");
					sb.append("          AND P_A.ORG_TYPE='04' \n");
					sb.append("          AND P_A.ORG_NO =  " + newTbName
							+ ".ORG_NO\n");
					sb.append("          AND P_A.STAFF_NO ='"
							+ username.getStaffNo() + "'  ) ");
				}

			} else if (orgType == 4) {// 区县级父节点取　供电所
				if (accLevel == 4) {
					sb.append(" AND EXISTS (SELECT 1\n");
					sb
							.append("         FROM P_ACCESS_ORG P_A, P_SYS_USER P_U\n");
					sb.append("        WHERE P_A.STAFF_NO = P_U.STAFF_NO\n");
					sb.append("          AND P_A.ORG_TYPE in ('05','06') \n");
					sb.append("          AND P_A.ORG_NO =  " + newTbName
							+ ".ORG_NO\n");
					sb.append("          AND P_A.STAFF_NO ='"
							+ username.getStaffNo() + "'  ) ");
				}
			}
		}
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 方法　addConsType
	 * 
	 * 
	 * @return　添加访问ConsType权限的　SQL语句条件　
	 */
	public static String addConsType(String tbname) {
		String newTbName = tbname;
		if (newTbName == null || newTbName.isEmpty())
			newTbName = "C_CONS";
		StringBuffer sb = new StringBuffer();
		sb.append("AND EXISTS (SELECT 1\n");
		sb.append("          FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n");
		sb.append("         WHERE PRI_CONS_TYPE.CONS_TYPE = " + newTbName
				+ ".CONS_TYPE\n");
		sb.append("           AND PRI_CONS_TYPE.STAFF_NO = ?) ");

		String sql = sb.toString();

		return sql;
	}

	/**
	 * 方法　addTmnlFactory
	 * 
	 * @param tbname
	 *            主SQL语句中包含factory_code 字段的表名（有别名的话是别名）
	 * @return　添加访问TmnlFactory权限的　SQL语句条件　
	 */
	public static String addTmnlFactory(String tbname) {
		String newTbName = tbname;
		if (newTbName == null || newTbName.isEmpty())
			newTbName = "VW_TMNL_RUN";

		StringBuffer sb = new StringBuffer();
		sb.append("AND EXISTS (SELECT 1\n");
		sb.append("          FROM P_ACCESS_TMNL_FACTORY PRI_FACTORY\n");
		sb.append("         WHERE PRI_FACTORY.FACTORY_CODE = " + newTbName
				+ ".FACTORY_CODE\n");
		sb.append("           AND PRI_FACTORY.STAFF_NO = ?)");

		String sql = sb.toString();

		return sql;
	}

	/**
	 * 方法　addTmnlFactory
	 * 
	 * @param username
	 *            用户Bean
	 * @param constbname
	 *            客户表名称
	 * @param tmnltbname
	 *            要关联的终端表名
	 * @param orgFlag
	 * @param constypeFlag
	 * @param factoryFlag
	 * @return 添加访问TmnlFactory权限的　SQL语句条件　
	 */
	public static String addPri(PSysUser username, String constbname,
			String tmnltbname, boolean orgFlag, boolean constypeFlag,
			boolean factoryFlag) {
		String newSql = "";
		if (orgFlag)
			newSql += addOrgPri(username, constbname);
		if (constypeFlag)
			newSql += addConsType(constbname);
		if (factoryFlag)
			newSql += addTmnlFactory(tmnltbname);
		return newSql;
	}

	/**
	 * 方法　addTmnlFactory
	 * 
	 * * @param username 用户Bean
	 * 
	 * @param constbname
	 *            客户表名称
	 * @param tmnltbname
	 *            要关联的终端表名
	 * @return 添加所有权限的方法
	 */
	public static String addPri(PSysUser username, String constbname,
			String tmnltbname) {
		return addPri(username, constbname, tmnltbname, true, true, true);
	}

	/**
	 * 方法 addPri
	 * 
	 * @param username
	 *            用户Bean
	 * @param constbname
	 *            客户表名称
	 * @param tmnltbname
	 *            要关联的终端表名
	 * @param flag
	 *            要添加权限的 标志的和 org_No 1 consType 2 fac 4
	 * @return 根据 flag 进行权限处理后的字符串
	 * @describe 如： 要添加 org_No 权限和 factory 权限 flag= 1+4 全部权限为 flag=1+2+4
	 */
	public static String addPri(PSysUser username, String constbname,
			String tmnltbname, int flag) {
		boolean orgflag = false;
		boolean consflag = false;
		boolean facflag = false;

		if (flag % 2 == 1)
			orgflag = true;
		if ((flag >> 1) % 2 == 1)
			consflag = true;
		if ((flag >> 2) % 2 == 1)
			facflag = true;

		return addPri(username, constbname, tmnltbname, orgflag, consflag,
				facflag);
	}

}
