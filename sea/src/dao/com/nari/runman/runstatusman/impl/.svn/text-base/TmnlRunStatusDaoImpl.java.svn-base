package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.runstatusman.TmnlRunStatusDao;
import com.nari.statreport.TmnlFactory;
import com.nari.statreport.TmnlRunRemark;
import com.nari.statreport.TmnlRunStatus;
import com.nari.statreport.TmnlRunStatusRun;
import com.nari.statreport.TmnlTypeCode;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;

public class TmnlRunStatusDaoImpl extends JdbcBaseDAOImpl implements
		TmnlRunStatusDao {
	/**
	 * 备注信息保存
	 */
	public void updateRemark(Long exceptionId, String remark) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("update A_TMNL_EXCEPTION te set te.remark = ? where te.tmnl_exception_id = ?");

		getJdbcTemplate().update(sql.toString(), new Object[]{remark, exceptionId});
	}
	
	/**
	 * 备注查询
	 */
	public Page<TmnlRunRemark> findRemark(String consNo, long start, int limit){
		StringBuffer sql = new StringBuffer();
		 sql.append("select te.cons_no,\n" +
				 "       te.tmnl_asset_no,\n" + 
				 "       te.except_date,\n" + 
				 "       vte.except_name,\n" + 
				 "       vtf.factory_name\n" + 
				 "  from a_tmnl_exception te, vw_tmnl_factory vtf, vw_tmnl_except_code vte\n" + 
				 " where te.factory_code = vtf.factory_code\n" + 
				 "   and te.except_code = vte.except_code\n" +
				 "   and te.cons_no = ?");
		
		return super.pagingFind(sql.toString(), start, limit, new RemarkRowMapper(), new Object[]{consNo});
	}
	
	class RemarkRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TmnlRunRemark tmnlrunremark = new TmnlRunRemark();
			try {
				tmnlrunremark.setConsNo(rs.getString("CONS_NO"));
				tmnlrunremark.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tmnlrunremark.setExceptDate(rs.getTimestamp("EXCEPT_DATE"));
				tmnlrunremark.setExceptName(rs.getString("EXCEPT_NAME"));
				tmnlrunremark.setFactoryName(rs.getString("FACTORY_NAME"));
				return tmnlrunremark;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 左边树节点查询，单方法实现 
	 */
	public Page<TmnlRunStatus> queryPageTmnl(String nodeType, String nodeValue, String tmnlTypeCode, PSysUser userInfo, long start, long limit,
			Date startDate, Date endDate) {
		    StringBuffer sb = new StringBuffer();
		    List<Object> paraList = new ArrayList<Object>();
		    paraList.add(nodeValue + "%");
		    sb.append("SELECT TE.TMNL_EXCEPTION_ID,\n");
		    sb.append("       O.ORG_NO,\n");
		    sb.append("       O.ORG_NAME,\n");
		    sb.append("       C.CONS_NO,\n");
		    sb.append("       O.ORG_NAME,\n");
		    sb.append("       R.TERMINAL_ADDR,\n");
//		    sb.append("       V.TMNL_TYPE,\n");
		    sb.append("       TE.EXCEPT_DATE,\n");
		    sb.append("       TE.FLOW_STATUS_CODE,\n");
		    sb.append("       TE.SIM_NO,\n");
		    sb.append("       TE.REMARK,\n");
		    sb.append("       VTE.EXCEPT_NAME,\n");
		    sb.append("       TF.FACTORY_NAME\n");
		    sb.append("  FROM C_CONS C,      \n");
		    sb.append("		  O_ORG O,  \n");
		    sb.append("		  A_TMNL_EXCEPTION TE,  \n");
		    sb.append("		  A_TMNL_REAL_TIME TR,  \n");
		    sb.append("		  R_CONS_TMNL_RELA  RC,  \n");
		    sb.append("		  VW_TMNL_RUN R,  \n");
		    sb.append("		  VW_TMNL_FACTORY     TF,  \n");
//		    sb.append("		  VW_TMNL_TYPE_CODE   V,  \n");
		    sb.append("		  VW_TMNL_EXCEPT_CODE VTE  \n");
		    if ("cgp".equals(nodeType)) {
		      sb.append(",        T_TMNL_GROUP_DETAIL G\n");
		    } else if ("ugp".equals(nodeType)) {
		      sb.append(",        R_USER_GROUP_DETAIL G\n");
		    }
		    sb.append("WHERE \n");
		    if ("org".equals(nodeType)) {
		      sb.append("   C.ORG_NO like ?");
		      sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		    } else if ("line".equals(nodeType)) {
		      sb.append("   C.LINE_ID like ?");
		      sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		    } else if ("ugp".equals(nodeType) || "cgp".equals(nodeType)) {
		      sb.append("   G.CONS_NO = C.CONS_NO\n");
		      sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
		      sb.append("   AND G.GROUP_NO like ? ");
		      sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		      paraList.add(userInfo.getStaffNo());
		    } else if ("usr".equals(nodeType)) {
		      sb.append("   TE.TMNL_ASSET_NO like ?");
		    } else {
		    	return null;
		    }
		    paraList.add(startDate);
		    paraList.add(endDate);
		    sb.append("  AND C.AREA_NO = O.ORG_NO\n");
		    sb.append("  AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO\n");
		    sb.append("  AND R.TMNL_ASSET_NO = TR.TMNL_ASSET_NO(+)\n");
		    sb.append("  AND R.TMNL_ASSET_NO = TE.TMNL_ASSET_NO(+)\n");
		    sb.append("  AND TE.FACTORY_CODE = TF.FACTORY_CODE\n");
		    sb.append("  AND TE.EXCEPT_CODE = VTE.EXCEPT_CODE\n");
		    sb.append("  AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)\n");
		    sb.append("AND TE.EXCEPT_DATE BETWEEN ? AND ?");
		    return this.pagingFind(sb.toString(), start, limit, new TmnlRunStatusRowMapper(),
		        paraList.toArray());
		  }
	/**
	 * 左边树单方法结束
	 */
	
	//*********异常工况页面
	/**
	 * 根据左边树节点不同点击事件，不同的查询方法
	 * org、uer、line、cgp、ugp、sub
	 */
	@Override
	public Page<TmnlRunStatus> findOrgNo(String orgName, String orgType, Date startDate, Date endDate, String tmnlTypeCode, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
			 sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
			 sql.append("       c.cons_no,\n");
			 sql.append("       c.cons_name,\n"); 
			 sql.append("       R.TERMINAL_ADDR,\n");
			 sql.append("       R.TMNL_ASSET_NO,\n");
			 sql.append("       te.except_date,\n"); 
			 sql.append("       vte.except_name,\n"); 
			 sql.append("       te.flow_status_code,\n"); 
			 sql.append("       te.sim_no,\n"); 
			 sql.append("       tf.factory_name,\n"); 
			 sql.append("       te.remark\n"); 
			 sql.append("  from A_TMNL_EXCEPTION te,\n"); 
			 sql.append("       A_TMNL_REAL_TIME tr,\n"); 
			 sql.append("		R_TMNL_RUN        R,\n");
			 sql.append("		R_CONS_TMNL_RELA    RC,\n");
			 sql.append("       vw_tmnl_factory  tf,\n"); 
			 sql.append("       c_cons           c,\n"); 
			 sql.append("       o_org            o,\n"); 
//			 sql.append("		VW_TMNL_TYPE_CODE V , \n");
			 sql.append("       vw_tmnl_except_code vte\n");
			 sql.append(" where c.cons_no = RC.CONS_NO \n");
			 sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
			 sql.append("   and r.tmnl_asset_no = te.tmnl_asset_no(+)\n");
			 sql.append("   AND R.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
			 sql.append("   and te.factory_code = tf.factory_code\n");
			 sql.append("   and te.except_code = vte.except_code\n");
			 sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+)  \n");
			 sql.append("   and o.org_no = c.org_no\n");
//			 sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
	    if(!(orgName.equals("63101"))){
	    	
		if ("01".equals(orgType) || "02".equals(orgType)
				|| "03".equals(orgType) || "04".equals(orgType)) {
			orgName = "%" + orgName + "%";
			sql.append("   and c.area_no  like ?\n");
			runList.add(orgName);
		} else {
			sql.append("   and c.org_no = ?\n");
			runList.add(orgName);
		}
		
	    }
		sql.append("AND R.STATUS_CODE = '01' \n");
		sql.append("	and te.except_date between ? and ?  \n");
		runList.add(startDate);
		runList.add(endDate);
//		if(tmnlTypeCode.length() > 1){
//			sql.append("AND vte.except_code = ? \n ");
//			runList.add(tmnlTypeCode);
//		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND vte.except_code = ? \n ");
			runList.add(tmnlTypeCode);
		}
		logger.debug("\n 终端异常工况：" +sql.toString());	 
		//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
		return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
	}


	@Override
	public Page<TmnlRunStatus> findCgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode, long start, int limit){
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       R.TERMINAL_ADDR,\n");
		sql.append("       R.TMNL_ASSET_NO,\n");
		sql.append("       te.except_date,\n");
//		sql.append("vte.EXCEPT_NAME,  \n");
		sql.append("       vte.except_name,\n");
		sql.append("       te.flow_status_code,\n");
		sql.append("       te.sim_no,\n");
		sql.append("       tf.factory_name, \n");
		sql.append("       te.remark \n");
		sql.append("  from A_TMNL_EXCEPTION te, t_tmnl_group_detail tgd,\n");
		sql.append("       A_TMNL_REAL_TIME tr,\n");
		sql.append("       vw_tmnl_except_code vte,\n");
		sql.append("       vw_tmnl_factory  tf,\n");
		sql.append("       R_TMNL_RUN       r,\n");
		sql.append("		R_CONS_TMNL_RELA    RC,\n");
		sql.append("       c_cons           c, \n");
		sql.append("       o_org            o \n");
//		sql.append(" VW_TMNL_TYPE_CODE VT  \n");
		sql.append(" where c.cons_no = RC.CONS_NO \n");
		sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
		sql.append("   and te.except_code = vte.except_code\n");
		sql.append("   and r.tmnl_asset_no = te.tmnl_asset_no(+);\n");
		sql.append("   and te.factory_code = tf.factory_code\n");
		sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+);  \n");
		sql.append("	and r.tmnl_asset_no = tgd.tmnl_asset_no \n");
		sql.append("   and o.org_no = c.org_no\n");
//		sql.append("  AND R.TERMINAL_TYPE_CODE = VT.TMNL_TYPE_CODE(+)  \n");
		sql.append("  	and tgd.group_no = ? \n");
		runList.add(groupNo);
		sql.append("AND R.STATUS_CODE = '01' \n");
		sql.append("	and te.except_date between ? and ?  \n");
		runList.add(startDate);
		runList.add(endDate);
		sql.append(" AND RT.TERMINAL_TYPE_CODE = '01';\n");
		sql.append(" order by o.org_no");
//		if(tmnlTypeCode.length() > 1){
//				sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//				runList.add(tmnlTypeCode);
//			}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND vte.except_code = ? \n ");
			runList.add(tmnlTypeCode);
		}
			
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
}


	@Override
	public Page<TmnlRunStatus> findUgp(String groupNo, Date startDate, Date endDate,String tmnlTypeCode, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       R.TERMINAL_ADDR,\n");
		sql.append("       R.TMNL_ASSET_NO,\n");
		sql.append("       te.except_date,\n");
		sql.append("       vte.except_name,\n");
//		sql.append("vte.EXCEPT_NAME,  \n");
		sql.append("       te.flow_status_code,\n");
		sql.append("       te.sim_no,\n");
		sql.append("       tf.factory_name,\n");
		sql.append("       te.remark\n");
		sql.append("  from A_TMNL_EXCEPTION te, r_user_group_detail ugd,\n");
		sql.append("       vw_tmnl_except_code vte,\n");
		sql.append("       A_TMNL_REAL_TIME tr,\n");
		sql.append("       vw_tmnl_factory  tf,\n");
		sql.append("       r_tmnl_run       r,\n");
		sql.append("		R_CONS_TMNL_RELA    RC,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       o_org            o\n");
//		sql.append(" VW_TMNL_TYPE_CODE VT  \n");
		sql.append(" where c.cons_no = RC.CONS_NO \n");
		sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
		sql.append("   and te.except_code = vte.except_code\n");
		sql.append("   and r.tmnl_asset_no = te.tmnl_asset_no(+)\n");
		sql.append("   and te.factory_code = tf.factory_code\n");
		sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+)  \n");
		sql.append("	and r.tmnl_asset_no = ugd.tmnl_asset_no  \n");
		sql.append("   and o.org_no = c.org_no\n");
//		sql.append("AND R.TERMINAL_TYPE_CODE = VT.TMNL_TYPE_CODE(+)  \n");
		sql.append("  	and ugd.group_no = ?  \n");
		runList.add(groupNo);
		sql.append("AND R.STATUS_CODE = '01' \n");
		sql.append("	and te.except_date between ? and ?  \n");
		runList.add(startDate);
		runList.add(endDate);
//		if(tmnlTypeCode.length() > 1){
//			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//			runList.add(tmnlTypeCode);
//		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND vte.except_code = ? \n ");
			runList.add(tmnlTypeCode);
		}
		sql.append(" order by o.org_no  \n");
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
}

	@Override
	public Page<TmnlRunStatus> findUsr(String consNo, Date startDate, Date endDate,String tmnlTypeCode, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       R.TERMINAL_ADDR,\n");
		sql.append("       R.TMNL_ASSET_NO,\n");
		sql.append("       te.except_date,\n");
		sql.append("       vte.except_name,\n");
//		sql.append("VT.TMNL_TYPE,  \n");
		sql.append("       te.flow_status_code,\n");
		sql.append("       te.sim_no,\n");
		sql.append("       tf.factory_name,\n");
		sql.append("       te.remark\n");
		sql.append("  from A_TMNL_EXCEPTION te,\n");
		sql.append("       vw_tmnl_except_code vte,\n");
		sql.append("       A_TMNL_REAL_TIME tr,\n");
		sql.append("       vw_tmnl_factory  tf,\n");
		sql.append("       r_tmnl_run       r,\n");
		sql.append("		R_CONS_TMNL_RELA    RC,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       o_org            o \n");
//		sql.append("VW_TMNL_TYPE_CODE VT  \n");
		sql.append(" where c.cons_no = RC.CONS_NO \n");
		sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
		sql.append("   and r.tmnl_asset_no = te.tmnl_asset_no(+)\n");
		sql.append("   and te.except_code = vte.except_code\n");
		sql.append("   and te.factory_code = tf.factory_code\n");
		sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+)  \n");
		sql.append("   and o.org_no = c.org_no\n");
//		sql.append("AND R.TERMINAL_TYPE_CODE = VT.TMNL_TYPE_CODE(+)  \n");
		sql.append("   and r.tmnl_asset_no = ?  \n");
		runList.add(consNo);
		sql.append("AND R.STATUS_CODE = '01' \n");
		sql.append("	and te.except_date between ? and ?  \n");
		runList.add(startDate);
		runList.add(endDate);
//		if(tmnlTypeCode.length() > 1){
//			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//			runList.add(tmnlTypeCode);
//		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND vte.except_code = ? \n ");
			runList.add(tmnlTypeCode);
		}
		sql.append(" order by o.org_no  \n");
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
}
	
	@Override
	public Page<TmnlRunStatus> findLine(String lineId, Date startDate, Date endDate,String tmnlTypeCode, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		 sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
			sql.append("       c.cons_no,\n"); 
			sql.append("       c.cons_name,\n"); 
			sql.append("       R.TERMINAL_ADDR,\n"); 
			sql.append("       R.TMNL_ASSET_NO,\n");
			sql.append("       te.except_date,\n"); 
			sql.append("       vte.except_name,\n"); 
//			sql.append("VT.TMNL_TYPE,  \n");
			sql.append("       te.flow_status_code,\n"); 
			sql.append("       te.sim_no,\n"); 
			sql.append("       tf.factory_name,\n"); 
			sql.append("       te.remark\n"); 
			sql.append("  from A_TMNL_EXCEPTION te, g_line g,\n");
			sql.append("       vw_tmnl_except_code vte,\n");
			sql.append("       A_TMNL_REAL_TIME tr,\n"); 
			sql.append("       vw_tmnl_factory  tf,\n"); 
			sql.append("       r_tmnl_run       r,\n"); 
			sql.append("		R_CONS_TMNL_RELA    RC,\n");
			sql.append("       c_cons           c, \n"); 
			sql.append("       o_org            o \n"); 
//			sql.append("VW_TMNL_TYPE_CODE VT  \n");
			sql.append(" where c.cons_no = RC.CONS_NO \n");
			sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
			sql.append("   and te.except_code = vte.except_code\n");
			sql.append("   and r.terminal_addr = te.terminal_addr\n");
			sql.append("   and g.line_id = c.line_id\n");
			sql.append("   and te.factory_code = tf.factory_code\n");
			sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+)  \n");
			sql.append("   and o.org_no = c.org_no\n");
//			sql.append("AND R.TERMINAL_TYPE_CODE = VT.TMNL_TYPE_CODE(+)  \n");
			sql.append("  	and g.line_id = ?  \n");
			runList.add(lineId);
			sql.append("AND R.STATUS_CODE = '01' \n");
			sql.append("	and te.except_date between ? and ?  \n");
			runList.add(startDate);
			runList.add(endDate);
//			if(tmnlTypeCode.length() > 1){
//				sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//				runList.add(tmnlTypeCode);
//			}
			if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
				sql.append("AND vte.except_code = ? \n ");
				runList.add(tmnlTypeCode);
			}
			sql.append(" order by o.org_no  \n");
		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
}
	
	@Override
	public Page<TmnlRunStatus> findSub(String subsId, Date startDate, Date endDate, String tmnlTypeCode, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		
		List<Object> runList = new ArrayList<Object>();
		 sql.append("select te.tmnl_exception_id,o.org_name,o.org_no,\n");
			sql.append("       c.cons_no,\n"); 
			sql.append("       c.cons_name,\n"); 
			sql.append("       R.TERMINAL_ADDR,\n"); 
			sql.append("       R.TMNL_ASSET_NO,\n");
			sql.append("       te.except_date,\n"); 
			sql.append("       vte.except_name,\n"); 
//			sql.append("VT.TMNL_TYPE,  \n");
			sql.append("       te.flow_status_code,\n"); 
			sql.append("       te.sim_no,\n"); 
			sql.append("       tf.factory_name,\n"); 
			sql.append("       te.remark\n"); 
			sql.append("  from A_TMNL_EXCEPTION te,\n"); 
			sql.append("       vw_tmnl_except_code vte,\n");
			sql.append("       A_TMNL_REAL_TIME tr,\n"); 
			sql.append("       vw_tmnl_factory  tf,\n"); 
			sql.append("       r_tmnl_run       r,\n"); 
			sql.append("		R_CONS_TMNL_RELA    RC,\n");
			sql.append("       c_cons           c,\n"); 
			sql.append("       o_org            o \n"); 
//			sql.append("VW_TMNL_TYPE_CODE VT  \n");
			sql.append(" where c.cons_no = RC.CONS_NO \n");
			sql.append(" AND RC.TMNL_ASSET_NO = TR.TMNL_ASSET_NO \n");
			sql.append("   and te.except_code = vte.except_code\n");
			sql.append("   and r.tmnl_asset_no = te.tmnl_asset_no(+)\n"); 
			sql.append("   and te.factory_code = tf.factory_code\n");
			sql.append("	and r.tmnl_asset_no = tr.tmnl_asset_no(+)  \n");
			sql.append("   and o.org_no = c.org_no\n");
			sql.append("AND R.TERMINAL_TYPE_CODE = VT.TMNL_TYPE_CODE(+)  \n");
			sql.append("   and c.subs_id like ?\n");
			String subsStr = "%" +subsId + "%";
			runList.add(subsStr);
			sql.append("AND R.STATUS_CODE = '01' \n");
			sql.append("	and te.except_date between ? and ?  \n");
			runList.add(startDate);
			runList.add(endDate);
//			if(tmnlTypeCode.length() > 1){
//				sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//				runList.add(tmnlTypeCode);
//			}
			if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
				sql.append("AND vte.except_code = ? \n ");
				runList.add(tmnlTypeCode);
			}
			sql.append(" order by o.org_no  \n");
		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRowMapper(), runList.toArray());
}

	class TmnlRunStatusRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TmnlRunStatus tmnlrunstatus = new TmnlRunStatus();
			try {
				tmnlrunstatus.setTmnlExceptionId(rs.getString("TMNL_EXCEPTION_ID"));
				tmnlrunstatus.setOrgName(rs.getString("ORG_NAME"));
				tmnlrunstatus.setOrgNo(rs.getString("ORG_NO"));
				tmnlrunstatus.setConsNo(rs.getString("CONS_NO"));
				tmnlrunstatus.setConsName(rs.getString("CONS_NAME"));
				tmnlrunstatus.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tmnlrunstatus.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tmnlrunstatus.setExceptDate(rs.getTimestamp("EXCEPT_DATE"));
				tmnlrunstatus.setExceptName(rs.getString("EXCEPT_NAME"));
				tmnlrunstatus.setFlowStatusCode(rs.getByte("FLOW_STATUS_CODE"));
				tmnlrunstatus.setSimNo(rs.getString("SIM_NO"));
				tmnlrunstatus.setFactoryName(rs.getString("FACTORY_NAME"));
				tmnlrunstatus.setRemark(rs.getString("REMARK"));
//				tmnlrunstatus.setTmnlType(rs.getString("TMNL_TYPE"));
				return tmnlrunstatus;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
//***********实时工况页面 ，暂时与异常相同
	/**
	 * 根据左边树节点不同点击事件，不同的查询方法
	 * org、uer、line、cgp、ugp、sub
	 */
	@Override
	public Page<TmnlRunStatusRun> findOrgNoRun(String orgName, String orgType, String isOnline, String tmnlTypeCode,String commCode,String tmnlFactory,  long start, int limit) {
		List<Object> runList = new ArrayList<Object>();
 		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O.ORG_NO,  \n");
		sql.append(" O.ORG_NAME,  \n");
		sql.append("C.CONS_NO,  \n");
		sql.append(" C.CONS_NAME,  \n");
		sql.append("C.ELEC_ADDR,  \n");
		sql.append("A.TMNL_IP,  \n");
		sql.append("A.GATE_PORT,  \n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append("ROUND((SYSDATE - A.ONOFF_TIME) * 24)  LAST_TIME,\n");
		sql.append(" R.TERMINAL_ADDR,  \n");
		sql.append(" R.TMNL_ASSET_NO,  \n");
		sql.append(" M.COMM_MODE,  \n");
		sql.append("CASE WHEN A.IS_ONLINE = 0   \n");
		sql.append("THEN '离线' ELSE '在线' END IS_ONLINE,  \n");
		sql.append("A.CURRENT_LOAD,  \n");
		sql.append("A.LOAD_TIME  \n");
		sql.append(" FROM O_ORG             O,  \n");
		sql.append(" C_CONS            C,  \n");
		sql.append("R_CONS_TMNL_RELA  RC,  \n");
		sql.append("R_TMNL_RUN        R,  \n");
		sql.append("A_TMNL_REAL_TIME  A,  \n");
		sql.append("VW_COMM_MODE      M,  \n");

		if("02".equals(orgType)){
			sql.append("(SELECT OO.ORG_NO\n");
			sql.append("  FROM o_org oo\n");
			sql.append(" START WITH oo.org_no = ?\n");
			sql.append("CONNECT BY PRIOR oo.org_no = oo.p_org_no) org,\n");
			runList.add(orgType);
		}

		sql.append("VW_TMNL_TYPE_CODE V  \n");
		sql.append(" WHERE O.ORG_NO = C.ORG_NO  \n");
		
		//通信方式
		if(null != commCode && !"".equals(commCode) && !"-1".equals(commCode)){
			sql.append("	AND R.COLL_MODE = ?  \n");
			runList.add(commCode);
		}
		//生产厂商
		if(null != tmnlFactory && !"".equals(tmnlFactory) && !"-1".equals(tmnlFactory)){
			sql.append("	AND R.FACTORY_CODE = ?  \n");
			runList.add(tmnlFactory);
		}
		sql.append("AND C.CONS_NO = RC.CONS_NO  \n");
		sql.append("AND A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
		sql.append(" AND R.TMNL_ASSET_NO = A.TMNL_ASSET_NO \n");
		sql.append(" AND R.COLL_MODE = M.COMM_MODE_CODE(+)   \n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		
		if("02".equals(orgType)){
			sql.append("	AND ORG.ORG_NO = C.AREA_NO   \n");
		}
		
		if ( "03".equals(orgType) || "04".equals(orgType)) {
			orgName = "%" + orgName + "%";
			sql.append("   AND C.AREA_NO LIKE ? \n");
			runList.add(orgName);
		} else if ("06".equals(orgType)) {
			sql.append(" AND C.ORG_NO = ? \n");
			runList.add(orgName);
		} 
		sql.append("AND R.STATUS_CODE = '01' \n");
		if("0".equals(isOnline) || "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(Integer.parseInt(isOnline));
		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
		
		logger.debug("\n 终端实时工况：" +sql.toString());
		
		return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
	}

	@Override
	public Page<TmnlRunStatusRun> findCgpRun(String groupNo,String tmnlTypeCode,String isOnline, String commCode,String tmnlFactory, long start, int limit){
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		 sql.append("select o.org_name,o.org_no,\n" );
		 sql.append("       c.cons_no,\n");
		 sql.append("       c.cons_name,\n" );
		 sql.append( "       c.elec_addr,\n");
		 sql.append("       t.TMNL_IP,\n" );
		 sql.append("       t.gate_port,\n");
		 sql.append(" R.TERMINAL_ADDR ,  \n");
		 sql.append(" R.TMNL_ASSET_NO ,  \n");
		 sql.append("V.TMNL_TYPE,  \n");
		 sql.append( " ROUND((SYSDATE - T.ONOFF_TIME) * 24)  LAST_TIME,  \n" );
		 sql.append("       m.COMM_MODE,\n");
		 sql.append("       case\n" );
		 sql.append("         when t.is_online = 0 then\n");
		 sql.append( "          '离线'\n");
		 sql.append( "         else\n" );
		 sql.append("          '在线'\n");
		 sql.append("       end is_online,\n" );
		 sql.append("       t.current_load,\n");
		 sql.append("       t.load_time\n");
		 sql.append( "  from o_org            o,\n");
		 sql.append("       c_cons           c,\n");
		 sql.append("       r_cons_tmnl_rela rc,\n");
		 sql.append("R_TMNL_RUN        R,  \n");
		 sql.append("       a_tmnl_real_time t,\n");
		 sql.append( "       vw_comm_mode     m,\n");
		 sql.append("       t_tmnl_group_detail tgd,\n");
		 sql.append("VW_TMNL_TYPE_CODE V  \n");
		 sql.append(" where o.org_no = rc.org_no\n");

		 //通信方式
		if(null != commCode && !"".equals(commCode) && !"-1".equals(commCode)){
			sql.append("	AND R.COLL_MODE = ?  \n");
			runList.add(commCode);
		}
		//生产厂商
		if(null != tmnlFactory && !"".equals(tmnlFactory) && !"-1".equals(tmnlFactory)){
			sql.append("	AND R.FACTORY_CODE = ?  \n");
			runList.add(tmnlFactory);
		}
		 
		 sql.append( "   and rc.tmnl_asset_no = tgd.tmnl_asset_no\n");
		 sql.append("   and c.cons_no = rc.cons_no\n");
		 sql.append("   and t.tmnl_asset_no = rc.tmnl_asset_no\n");
		 sql.append(" AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		 sql.append(" AND R.COLL_MODE = M.COMM_MODE_CODE(+)   \n");
		 sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		 sql.append("   and tgd.group_no = ?\n");
		 runList.add(groupNo);
		 sql.append("AND R.STATUS_CODE = '01' \n");
		if("0".equals(isOnline) || "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(Integer.parseInt(isOnline));
		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
		 sql.append("   order by o.org_no  \n");
		 
	//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
}


	@Override
	public Page<TmnlRunStatusRun> findUgpRun(String groupNo,String tmnlTypeCode,String isOnline, String commCode,String tmnlFactory, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       c.elec_addr,\n");
		sql.append("       t.TMNL_IP,\n");
		sql.append("       t.gate_port,\n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append(" ROUND((SYSDATE - T.ONOFF_TIME) * 24)  LAST_TIME,  \n");
		sql.append(" R.TERMINAL_ADDR ,  \n");
		sql.append(" R.TMNL_ASSET_NO ,  \n");
		sql.append("       m.COMM_MODE,\n");
		sql.append("       case\n");
		sql.append("         when t.is_online = 0 then\n" );
		sql.append("          '离线'\n");
		sql.append( "         else\n");
		sql.append( "          '在线'\n" );
		sql.append( "       end is_online,\n" );
		sql.append("       t.current_load,\n");
		sql.append("       t.load_time\n");
		sql.append("  from o_org            o,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       r_cons_tmnl_rela rc,\n");
		sql.append("       a_tmnl_real_time t,\n" );
		sql.append("R_TMNL_RUN        R, \n");
		sql.append("       vw_comm_mode     m,\n");
		sql.append("       r_user_group_detail ugd,\n" );
		 sql.append("VW_TMNL_TYPE_CODE V  \n");
		sql.append(" where o.org_no = rc.org_no\n" );
		 //通信方式
		if(null != commCode && !"".equals(commCode) && !"-1".equals(commCode)){
			sql.append("	AND R.COLL_MODE = ?  \n");
			runList.add(commCode);
		}
		//生产厂商
		if(null != tmnlFactory && !"".equals(tmnlFactory) && !"-1".equals(tmnlFactory)){
			sql.append("	AND R.FACTORY_CODE = ?  \n");
			runList.add(tmnlFactory);
		}
		
		sql.append("   and rc.tmnl_asset_no = ugd.tmnl_asset_no\n" );
		sql.append("   and c.cons_no = rc.cons_no\n");
		sql.append("   and t.tmnl_asset_no = rc.tmnl_asset_no\n" );
		sql.append(" AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		sql.append(" AND R.COLL_MODE = M.COMM_MODE_CODE(+)   \n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		sql.append("   and ugd.group_no = ?\n");
		runList.add(groupNo);
		sql.append("AND R.STATUS_CODE = '01' \n");
		if("0".equals(isOnline) || "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(Integer.parseInt(isOnline));
		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
//		sql.append("   order by o.org_no \n");
		
		
		
		
	//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
}

	@Override
	public Page<TmnlRunStatusRun> findUsrRun(String consNo,String tmnlTypeCode,String isOnline,  long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		sql.append("select o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       c.elec_addr,\n");
		sql.append("       t.TMNL_IP,\n");
		sql.append("       t.gate_port,\n");
		sql.append(" R.TERMINAL_ADDR ,  \n");
		sql.append(" R.TMNL_ASSET_NO ,  \n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append(" ROUND((SYSDATE - T.ONOFF_TIME) * 24)  LAST_TIME, \n");
		sql.append("       m.COMM_MODE,\n");
		sql.append("       case\n");
		sql.append("         when t.is_online = 0 then\n");
		sql.append("          '离线'\n");
		sql.append("         else\n");
		sql.append("          '在线'\n");
		sql.append("       end is_online,\n");
		sql.append("       t.current_load,\n");
		sql.append("       t.load_time\n");
		sql.append("  from o_org            o,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       r_cons_tmnl_rela rc,\n");
		sql.append("       a_tmnl_real_time t,\n");
		sql.append("R_TMNL_RUN        R, \n");
		sql.append("       vw_comm_mode     m,\n");
		sql.append("VW_TMNL_TYPE_CODE V  \n");
		sql.append(" where o.org_no = rc.org_no\n");
		sql.append("   and c.cons_no = rc.cons_no\n");
		sql.append("   and t.tmnl_asset_no = rc.tmnl_asset_no\n");
		sql.append(" AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		sql.append("   and t.comm_mode = m.COMM_MODE_CODE(+)\n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		sql.append("   and rc.tmnl_asset_no = ? \n");
		runList.add(consNo);
		sql.append("AND R.STATUS_CODE = '01' \n");
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		} 
		if(isOnline.length() > 1){
		}
		else if( "0".equals(isOnline) ||  "1".equals(isOnline)){
			sql.append("AND T.IS_ONLINE =  ? \n");
			runList.add(isOnline);
		}
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
}
	
	@Override
	public Page<TmnlRunStatusRun> findLineRun(String lineId,String tmnlTypeCode,String isOnline, String commCode,String tmnlFactory, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> runList = new ArrayList<Object>();
		 
		sql.append("select o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       c.elec_addr,\n");
		sql.append("       t.TMNL_IP,\n");
		sql.append("       t.gate_port,\n");
		sql.append(" R.TERMINAL_ADDR ,  \n");
		sql.append(" R.TMNL_ASSET_NO ,  \n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append(" ROUND((SYSDATE - T.ONOFF_TIME) * 24)  LAST_TIME, \n");
		sql.append("       m.COMM_MODE,\n");
		sql.append("       case\n");
		sql.append("         when t.is_online = 0 then\n");
		sql.append("          '离线'\n");
		sql.append("         else\n");
		sql.append("          '在线'\n");
		sql.append("       end is_online,\n");
		sql.append("       t.current_load,\n");
		sql.append("       t.load_time\n");
		sql.append("  from o_org            o,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       r_cons_tmnl_rela rc,\n");
		sql.append("       a_tmnl_real_time t,\n");
		sql.append("R_TMNL_RUN        R, \n");
		sql.append("       vw_comm_mode     m,\n");
		sql.append("       g_line g ,\n");
		sql.append("VW_TMNL_TYPE_CODE V  \n");
		sql.append(" where o.org_no = rc.org_no\n");
		 //通信方式
		if(null != commCode && !"".equals(commCode) && !"-1".equals(commCode)){
			sql.append("	AND R.COLL_MODE = ?  \n");
			runList.add(commCode);
		}
		//生产厂商
		if(null != tmnlFactory && !"".equals(tmnlFactory) && !"-1".equals(tmnlFactory)){
			sql.append("	AND R.FACTORY_CODE = ?  \n");
			runList.add(tmnlFactory);
		}
		
		sql.append("   and g.line_id = c.line_id\n");
		sql.append("   and c.cons_no = rc.cons_no\n");
		sql.append("   and t.tmnl_asset_no = rc.tmnl_asset_no\n");
		sql.append(" AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		sql.append(" AND R.COLL_MODE = M.COMM_MODE_CODE(+)   \n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		sql.append("   and g.line_id = ?\n");
		runList.add(lineId);
		sql.append("AND R.STATUS_CODE = '01' \n");
		if("0".equals(isOnline) || "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(Integer.parseInt(isOnline));
		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
		sql.append("   order by o.org_no \n");
		 
	//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
}
	
	@Override
	public Page<TmnlRunStatusRun> findSubRun(String subsId,String tmnlTypeCode, String isOnline,String commCode,String tmnlFactory, long start, int limit) {
		StringBuffer sql = new StringBuffer();
		String subsStr = "%" +subsId + "%";
		List<Object> runList = new ArrayList<Object>();

		sql.append("select o.org_name,o.org_no,\n");
		sql.append("       c.cons_no,\n");
		sql.append("       c.cons_name,\n");
		sql.append("       c.elec_addr,\n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append(" ROUND((SYSDATE - T.ONOFF_TIME) * 24)  LAST_TIME, \n");
		sql.append("       t.TMNL_IP,\n");
		sql.append("       t.gate_port,\n");
		sql.append(" R.TERMINAL_ADDR ,  \n");
		sql.append(" R.TMNL_ASSET_NO ,  \n");
		sql.append("       m.COMM_MODE,\n");
		sql.append("       case\n");
		sql.append("         when t.is_online = 0 then\n");
		sql.append("          '离线'\n");
		sql.append("         else\n");
		sql.append("          '在线'\n");
		sql.append("       end is_online,\n");
		sql.append("       t.current_load,\n");
		sql.append("       t.load_time\n");
		sql.append("  from o_org            o,\n");
		sql.append("       c_cons           c,\n");
		sql.append("       r_cons_tmnl_rela rc,\n");
		sql.append("R_TMNL_RUN        R, \n");
		sql.append("       a_tmnl_real_time t,\n");
		sql.append("       vw_comm_mode     m,\n");
		sql.append("VW_TMNL_TYPE_CODE V  \n");
		sql.append(" where o.org_no = rc.org_no\n");
		 //通信方式
		if(null != commCode && !"".equals(commCode) && !"-1".equals(commCode)){
			sql.append("	AND R.COLL_MODE = ?  \n");
			runList.add(commCode);
		}
		//生产厂商
		if(null != tmnlFactory && !"".equals(tmnlFactory) && !"-1".equals(tmnlFactory)){
			sql.append("	AND R.FACTORY_CODE = ?  \n");
			runList.add(tmnlFactory);
		}
		
		sql.append("   and c.cons_no = rc.cons_no\n");
		sql.append("   and t.tmnl_asset_no = rc.tmnl_asset_no\n");
		sql.append(" AND R.TMNL_ASSET_NO = T.TMNL_ASSET_NO \n");
		sql.append(" AND R.COLL_MODE = M.COMM_MODE_CODE(+)   \n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		sql.append("   and c.subs_id like ? \n");
		runList.add(subsStr);
		sql.append("AND R.STATUS_CODE = '01' \n");
		if("0".equals(isOnline) || "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(Integer.parseInt(isOnline));
		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
	//有几个查询条件，{}中返回几个参数	单位公变类型时间等		
	return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
}
	
	@Override
	public Page<TmnlRunStatusRun> findFacRun(String factoryCode,PSysUser user, String tmnlTypeCode,String isOnline, long start, int limit) {
		List<Object> runList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O.ORG_NO,  \n");
		sql.append("O.ORG_NAME,  \n");
		sql.append("C.CONS_NO,  \n");
		sql.append("C.CONS_NAME,  \n");
		sql.append("C.ELEC_ADDR,  \n");
		sql.append("A.TMNL_IP,  \n");
		sql.append("A.GATE_PORT,  \n");
		sql.append("V.TMNL_TYPE,  \n");
		sql.append("ROUND((SYSDATE - A.ONOFF_TIME) * 24) LAST_TIME,  \n");
		sql.append(" R.TERMINAL_ADDR ,  \n");
		sql.append(" R.TMNL_ASSET_NO ,  \n");
		sql.append("M.COMM_MODE,  \n");
		sql.append("CASE WHEN A.IS_ONLINE = 0   \n");
		sql.append("THEN '离线' ELSE '在线' END IS_ONLINE,  \n");
		sql.append("A.CURRENT_LOAD,  \n");
		sql.append("A.LOAD_TIME  \n");
		sql.append("FROM O_ORG             O,  \n");
		sql.append("      C_CONS            C,  \n");
		sql.append("	  R_CONS_TMNL_RELA  RC,  \n");
		sql.append("       R_TMNL_RUN        R,  \n");
		sql.append("      A_TMNL_REAL_TIME  A,  \n");
		sql.append("      VW_COMM_MODE      M,  \n");
		sql.append("     VW_TMNL_TYPE_CODE V,  \n");
		sql.append("      VW_TMNL_FACTORY   F  \n");
		sql.append("WHERE O.ORG_NO = C.ORG_NO  \n");
		sql.append("AND C.CONS_NO = RC.CONS_NO  \n");
		sql.append(" AND A.TMNL_ASSET_NO = RC.TMNL_ASSET_NO  \n");
		sql.append("AND R.TMNL_ASSET_NO = A.TMNL_ASSET_NO  \n");
		sql.append("AND A.COMM_MODE = M.COMM_MODE_CODE(+)  \n");
		sql.append(" AND R.TERMINAL_TYPE_CODE = V.TMNL_TYPE_CODE(+)  \n");
		sql.append("AND R.FACTORY_CODE = F.factory_code(+)  \n");
		sql.append("AND R.FACTORY_CODE = ?  \n");
		runList.add(factoryCode);
//		if(tmnlTypeCode.length() > 1){
//			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
//			runList.add(tmnlTypeCode);
//		}
		if(null != tmnlTypeCode && !"".equals(tmnlTypeCode) && !"-1".equals(tmnlTypeCode)){
			sql.append("AND R.TERMINAL_TYPE_CODE = ? \n ");
			runList.add(tmnlTypeCode);
		}
		if(isOnline.length() > 1){
		}
		else if( "0".equals(isOnline) ||  "1".equals(isOnline)){
			sql.append("AND A.IS_ONLINE =  ? \n");
			runList.add(isOnline);
		}
		sql.append("AND R.STATUS_CODE = '01' \n");
		if (cityLevel.equals(user.getAccessLevel())){
			sql.append(" AND SUBSTR(RC.ORG_NO, 1, 5) = ? \n");//--地市操作员单位入参
			runList.add(user.getOrgNo());
		} else if (areaLevel.equals(user.getAccessLevel())){
			sql.append("AND RC.AREA_NO = ?  \n");// --区县操作员单位入参  \n
		}
		return super.pagingFind(sql.toString(), start, limit, new TmnlRunStatusRunRowMapper(), runList.toArray());
	}

	/**
	 * 
	 * 实时工况映射类
	 * 
	 * @author MengYuan
	 * @since 2010-5-18
	 *
	 */
	class TmnlRunStatusRunRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TmnlRunStatusRun tmnlrunstatusrun = new TmnlRunStatusRun();
			try {
				tmnlrunstatusrun.setOrgName(rs.getString("ORG_NAME"));
				tmnlrunstatusrun.setConsNo(rs.getString("CONS_NO"));
				tmnlrunstatusrun.setConsName(rs.getString("CONS_NAME"));
				tmnlrunstatusrun.setElecAddr(rs.getString("ELEC_ADDR"));
				tmnlrunstatusrun.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tmnlrunstatusrun.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tmnlrunstatusrun.setCommMode(rs.getString("COMM_MODE"));
				tmnlrunstatusrun.setIsOnline(rs.getString("IS_ONLINE"));
				tmnlrunstatusrun.setCurrentLoad(rs.getDouble("CURRENT_LOAD"));
				tmnlrunstatusrun.setLoadTime(rs.getTimestamp("LOAD_TIME"));
				tmnlrunstatusrun.setGateIp(rs.getString("TMNL_IP"));
				tmnlrunstatusrun.setGatePort(rs.getString("GATE_PORT"));
				tmnlrunstatusrun.setLastTime(rs.getString("LAST_TIME"));
				tmnlrunstatusrun.setTmnlType(rs.getString("TMNL_TYPE"));
				return tmnlrunstatusrun;
			} catch (Exception e) {
				return null;
			}
		}
	}


	/**
	 * chenjg
	 * 查询所有生产厂商
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TmnlFactory> findAllFactory(){
		String sql = "SELECT * FROM vw_tmnl_factory  UNION SELECT '-1' AS factory_code, '-----全部-----' AS factory_name FROM vw_tmnl_factory";
		logger.debug("\n 查询生产厂商：" + sql);
		return getJdbcTemplate().query(sql, new FactoryRowMapper());
	}
	
	class FactoryRowMapper implements RowMapper{

		@Override
		public Object mapRow(ResultSet rs, int paraInt) throws SQLException {
			TmnlFactory tmnlFactory = new TmnlFactory();
			tmnlFactory.setFactoryCode(rs.getString("factory_code"));
			tmnlFactory.setFactoryName(rs.getString("factory_name"));
			return tmnlFactory;
		}
		
	}
	/**
	 * 查询终端类型
	 * @return 类型集合
	 */
	@SuppressWarnings("unchecked")
	public List<TmnlTypeCode> findTmnlTypeCode(){
		String sql = "SELECT *   FROM VW_TMNL_EXCEPT_CODE UNION SELECT '-1' AS EXCEPT_CODE, '-----全部-----' AS EXCEPT_NAME   FROM VW_TMNL_EXCEPT_CODE";
		logger.debug("\n 查询终端异常类型：" + sql);
		return getJdbcTemplate().query(sql, new TmnlTypeCodeRowMapper());
	}
	
	class TmnlTypeCodeRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paraInt) throws SQLException {
			TmnlTypeCode tmnlTypeCode = new TmnlTypeCode();
			tmnlTypeCode.setTmnlTypeCode(rs.getString("EXCEPT_CODE"));
			tmnlTypeCode.setTmnlTypeName(rs.getString("EXCEPT_NAME"));
			return tmnlTypeCode;
		}
		
	}
}
