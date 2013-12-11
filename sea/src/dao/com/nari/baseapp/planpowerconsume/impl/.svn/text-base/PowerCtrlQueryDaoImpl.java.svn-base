package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.PowerCtrlQueryDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.PwrctrlSchemeExecBean;
import com.nari.orderlypower.WCtrlSchemeSection;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WPwrctrlTemplateDetail;
import com.nari.orderlypower.WPwrctrlTemplateDetailId;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

public class PowerCtrlQueryDaoImpl extends JdbcBaseDAOImpl implements
		PowerCtrlQueryDao {
	@Override
	public List<WCtrlSchemeSection> findSectionBySchemeId(Long schemeId) {
		return null;
	}

	/*----------------------------------------终端时段控查询------------------------------------------------*/
	@Override
	public String findSchemeTemplateName(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select wt.template_name res\n")
			.append("from w_ctrl_scheme_section ws, w_pwrctrl_template wt\n") 
			.append("where ws.section_template_id = wt.template_id\n") 
			.append("and ws.ctrl_scheme_id = ?");
		List<String> templateNameList = pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
		if(templateNameList == null || templateNameList.size() == 0) {
			return "";
		}
		return templateNameList.get(0);
	}

	@Override
	public String findSchemeTemplateId(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select wt.template_id res\n")
			.append("from w_ctrl_scheme_section ws, w_pwrctrl_template wt\n") 
			.append("where ws.section_template_id = wt.template_id\n") 
			.append("and ws.ctrl_scheme_id = ?");
		List<String> templateIdList = pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
		if(templateIdList == null || templateIdList.size() == 0) {
			return "";
		}
		return templateIdList.get(0);
	}
	
	/*@Override
	public String findCxkSchemeTemplateName(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select wt.template_name res\n")
			.append("from w_ctrl_scheme_section ws, w_factctrl_template wt\n") 
			.append("where ws.section_template_id = wt.template_id\n") 
			.append("and ws.ctrl_scheme_id = ?");
		List<String> templateNameList = pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
		if(templateNameList == null || templateNameList.size() == 0) {
			return "";
		}
		return templateNameList.get(0);
	}*/
	
	@Override
	public String findCxkSchemeTemplateName(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select wt.template_id res\n")
			.append("from w_ctrl_scheme_section ws, w_factctrl_template wt\n") 
			.append("where ws.section_template_id = wt.template_id\n") 
			.append("and ws.ctrl_scheme_id = ?");
		List<String> templateNameList = pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
		if(templateNameList == null || templateNameList.size() == 0) {
			return "";
		}
		return templateNameList.get(0);
	}
	@Override
	public List<String> findSchemeValidSectionNo(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct wd.section_no res\n")
			.append("from w_tmnl_pwrctrl_detail wd,\n")
			.append("w_tmnl_pwrctrl w\n")
			.append("where wd.tmnl_pwrctrl_id = w.tmnl_pwrctrl_id\n")
			.append("and w.ctrl_scheme_id = ?\n")
			.append("and wd.is_valid =1\n")
			.append("order by wd.section_no");

		return pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
	}

	@Override
	public String findSchemeCurveNo(Long schemeId) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct t.curve_no res from w_tmnl_pwrctrl t\n")
			.append("where t.ctrl_scheme_id = ?");
		List<String> curveNoList = pagingFindList(sql.toString(), 0, 8, new stringMapper(),schemeId);
		if(curveNoList == null || curveNoList.size() == 0) {
			return "";
		}
		return curveNoList.get(0);
	}
	
	@Override
	public Integer findSchemeSecLength(Long schemeId)  throws DBAccessException{
		String sql =
			"select count(1) as res\n" +
			"  from w_ctrl_scheme_section     ws,\n" + 
			"       w_pwrctrl_template        wt,\n" + 
			"       w_pwrctrl_template_detail wtd\n" + 
			" where ws.section_template_id = wt.template_id\n" + 
			"   and wtd.template_id = wt.template_id\n" + 
			"   and ws.ctrl_scheme_id = ?";
		List<String> curveNoList = pagingFindList(sql, 0, 8, new stringMapper(),schemeId);
		if(curveNoList == null || curveNoList.size() == 0) {
			return 0;
		}
		return Integer.parseInt(curveNoList.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		String sql = 
			"select c.cons_name,\n" +
			"             c.cons_no,\n" + 
			"             o.org_name,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             w.total_no,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" +
			"             w.org_no,\n" + 
			"             p.pwrctrl_flag ctrl_flag\n" + 
			"       from w_tmnl_pwrctrl w, c_cons c, o_org o, vw_tmnl_run r, w_tmnl_ctrl_status p\n" + 
			"       where w.cons_no = c.cons_no\n" + 
			"         and w.org_no = o.org_no\n" + 
			"         and w.tmnl_asset_no = r.tmnl_asset_no\n" + 
			"		   and w.tmnl_asset_no = p.tmnl_asset_no(+)\n" +
			"         and w.total_no = p.total_no(+)\n" +
			"         and w.ctrl_scheme_id = ?";
		try{
			return getJdbcTemplate().query(sql,new Object[]{schemeId}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按方案ID查询时段控方案执行列表信息出错！");
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.pwrctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n" + 
			"     and e.tmnl_asset_no = ?";
		try{
			return getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按用户编号查询时段控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByOrgNo(String orgNo,String orgType, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.pwrctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n";
			if("03".equals(orgType)||"04".equals(orgType)){
				orgNo = "%"+orgNo+"%";
				sql += "   and c.area_no  like ?\n";
			} else if("06".equals(orgType)) {
				sql += "   and c.org_no = ?\n";
			} else {
				return new ArrayList<PwrctrlSchemeExecBean>();
			}
		sql += SysPrivilige.addOrgPri(user, "c");	
		try{
			return getJdbcTemplate().query(sql, new Object[]{orgNo,user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按供电单位编号查询时段控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		String sql = 
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.pwrctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  t_tmnl_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql,new Object[]{groupNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按控制群组编号查询时段控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByLineId(
			Long lineId, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.pwrctrl_flag ctrl_flag\n" + 
			" from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  g_line g\n" + 
			" where r.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and g.line_id = c.line_id\n" + 
			"   and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"   and e.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
			"   and e.total_no = w.total_no(+)\n" + 
			"   and e.total_no is not null\n" + 
			"   and g.line_id = ?";
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按行业ID查询时段控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecBySubsId(
			Long subsId, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.pwrctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,\n" + 
			"       g_subs_line_rela g\n" +
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and c.line_id = g.line_id\n" +
			"     and e.total_no is not  null\n";
		sql +="  and (c.subs_id = ? or g.subs_id =?)\n";
		sql += SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql,new Object[]{subsId,subsId,user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按变电站ID查询时段控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findPwrctrlSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		String sql = 
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.pwrctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  r_user_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{groupNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按普通群组编号查询时段控方案执行列表信息出错！");
			throw e;
		}
	}
	
	/*----------------------------------------终端厂休控查询--------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.factctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n" + 
			"     and e.tmnl_asset_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{tmnlAssetNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按用户编号编号查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByOrgNo(
			String orgNo, String orgType,PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.factctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n";
		    if("03".equals(orgType)||"04".equals(orgType)){
		    	orgNo = "%"+orgNo+"%";
				sql +="   and c.area_no like ?\n";
			} else if("06".equals(orgType)) {
				sql +="   and c.org_no = ?\n";
			} else {
				return new ArrayList<PwrctrlSchemeExecBean>();
			}
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{orgNo, user.getStaffNo()},new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按供电单位或区域查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		String sql = 
			"select c.cons_name,\n" +
			"             c.cons_no,\n" + 
			"             o.org_name,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             w.total_no,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" +
			"             w.org_no,\n" + 
			"             p.factctrl_flag ctrl_flag\n" + 
			"       from w_tmnl_factctrl w, c_cons c, o_org o, vw_tmnl_run r, w_tmnl_ctrl_status p\n" + 
			"       where w.cons_no = c.cons_no\n" + 
			"         and w.org_no = o.org_no\n" + 
			"         and w.tmnl_asset_no = r.tmnl_asset_no\n" + 
			"		   and w.tmnl_asset_no = p.tmnl_asset_no(+)\n" +
			"         and w.total_no = p.total_no(+)\n" +
			"         and w.ctrl_scheme_id = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{schemeId}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按方案ID编号查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.factctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  t_tmnl_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{groupNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按控制群组编号查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByLineId(
			Long lineId,PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.factctrl_flag ctrl_flag\n" + 
			" from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  g_line g\n" + 
			" where r.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and g.line_id = c.line_id\n" + 
			"   and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"   and e.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
			"   and e.total_no = w.total_no(+)\n" + 
			"   and e.total_no is not null\n" + 
			"   and g.line_id = ?";
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按行业ID查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecBySubsId(
			Long subsId, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.factctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,\n" + 
			"       g_subs_line_rela g\n" +
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and c.line_id = g.line_id\n" +
			"     and e.total_no is not  null\n";
		sql +="  and (c.subs_id = ? or g.subs_id =?)\n";
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{subsId, subsId, user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按变电站ID查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFactctrlSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.factctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  r_user_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{groupNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按普通群组编号查询厂休控方案执行列表信息出错！");
			throw e;
		}
	}
	
	/*----------------------------------------终端下浮控查询--------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByTmnlAssetNo(
			String tmnlAssetNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.down_ctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n" + 
			"     and e.tmnl_asset_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{tmnlAssetNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按用户编号编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByOrgNo(String orgNo,String orgType, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.down_ctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r\n" + 
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and e.total_no is not  null\n" ;
		if("03".equals(orgType)||"04".equals(orgType)){
			orgNo = "%"+orgNo+"%";
			sql +="   and c.area_no like ?\n";
		} else if("06".equals(orgType)) {
			sql +="   and c.org_no = ?\n";
		} else {
			return new ArrayList<PwrctrlSchemeExecBean>();
		}
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{orgNo,user.getStaffNo()}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按单位编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySchemeId(
			Long schemeId) throws DBAccessException {
		String sql = 
			"select c.cons_name,\n" +
			"             c.cons_no,\n" + 
			"             o.org_name,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             w.total_no,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" +
			"             w.org_no,\n" + 
			"             p.down_ctrl_flag ctrl_flag\n" + 
			"       from w_float_down_ctrl w, c_cons c, o_org o, vw_tmnl_run r, w_tmnl_ctrl_status p\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and w.tmnl_asset_no = r.tmnl_asset_no\n" + 
			"		   and w.tmnl_asset_no = p.tmnl_asset_no(+)\n" +
			"         and w.total_no = p.total_no(+)\n" +
			"         and w.ctrl_scheme_id = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{schemeId}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按方案ID编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@Override
	public WFloatDownCtrl findfloatDownCtrl(Long schemeId)
			throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select float_down_wtime,\n")
			.append("       alert_time1,\n") 
			.append("       alert_time2,\n") 
			.append("       alert_time3,\n") 
			.append("       alert_time4,\n") 
			.append("       float_down_percent,\n") 
			.append("       power_freezetime,\n") 
			.append("       ctrl_time\n") 
			.append("  from w_float_down_ctrl\n") 
			.append("where ctrl_scheme_id = ?");
		try{
			List<WFloatDownCtrl> list = pagingFindList(sql.toString(), 0, 2, new floatDownCtrlRowMapper(),schemeId);
			if(list == null || list.size() == 0){
				return null;
			}
			return list.get(0);	
		}catch(RuntimeException e){
			this.logger.debug("按方案ID编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByCgroupNo(
			String groupNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.down_ctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  t_tmnl_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{groupNo}, new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按控制群组编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByLineId(Long lineId, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.down_ctrl_flag ctrl_flag\n" + 
			" from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  g_line g\n" + 
			" where r.cons_no = c.cons_no\n" + 
			"   and c.org_no = o.org_no\n" + 
			"   and g.line_id = c.line_id\n" + 
			"   and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"   and e.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
			"   and e.total_no = w.total_no(+)\n" + 
			"   and e.total_no is not null\n" + 
			"   and g.line_id = ?";
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()},  new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按线路ID查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecBySubsId(Long subsId, PSysUser user) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"         c.cons_no,\n" + 
			"         r.terminal_addr,\n" + 
			"         r.tmnl_asset_no,\n" + 
			"         e.total_no,\n" + 
			"         c.cons_name,\n" + 
			"         o.org_name,\n" + 
			"         r.protocol_code,\n" + 
			"         r.ps_ensure_flag status_code,\n" + 
			"         w.down_ctrl_flag ctrl_flag\n" + 
			"   from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,\n" + 
			"       g_subs_line_rela g\n" +
			"   where r.cons_no = c.cons_no\n" + 
			"     and c.org_no = o.org_no\n" + 
			"     and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"     and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"     and w.total_no(+) = e.total_no\n" + 
			"     and c.line_id = g.line_id\n" +
			"     and e.total_no is not  null\n" ;
		sql +="  and (c.subs_id = ? or g.subs_id =?)\n";
		sql +=SysPrivilige.addOrgPri(user, "c");
		try{
			return getJdbcTemplate().query(sql, new Object[]{subsId,subsId, user.getStaffNo()},  new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按变电站ID查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PwrctrlSchemeExecBean> findFloatSchemeExecByUgroupNo(
			String groupNo) throws DBAccessException {
		String sql =
			"select o.org_no,\n" +
			"             c.cons_no,\n" + 
			"             r.terminal_addr,\n" + 
			"             r.tmnl_asset_no,\n" + 
			"             e.total_no,\n" + 
			"             c.cons_name,\n" + 
			"             o.org_name,\n" + 
			"             r.protocol_code,\n" + 
			"             r.ps_ensure_flag status_code,\n" + 
			"             w.down_ctrl_flag ctrl_flag\n" + 
			"       from e_data_total e, c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r,  r_user_group_detail gp\n" + 
			"       where r.cons_no = c.cons_no\n" + 
			"         and c.org_no = o.org_no\n" + 
			"         and r.tmnl_asset_no = e.tmnl_asset_no\n" + 
			"         and e.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"         and w.tmnl_asset_no(+)  = e.tmnl_asset_no\n" + 
			"         and w.total_no(+) = e.total_no\n" + 
			"         and e.total_no is not  null\n" + 
			"         and gp.group_no = ?";
		try{
			return getJdbcTemplate().query(sql, new Object[]{groupNo},  new pwrctrlSchemeExecMapper());
		}catch(RuntimeException e){
			this.logger.debug("按普通群组编号查询下浮控方案执行列表信息出错！");
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WPwrctrlTemplateDetail> findWPwrctrlTemplateDetailBytemplateId(
			Long templateId) {
		String sql = 
			"select template_id,\n" +
			"       is_valid,\n" + 
			"       section_no,\n" + 
			"       section_start,\n" + 
			"       section_end,\n" + 
			"       const1,\n" + 
			"       const2,\n" + 
			"       const3\n" + 
			"  from w_pwrctrl_template_detail"+
			"  where template_id=?"+
			"  order by section_start";
		try{
			return getJdbcTemplate().query(sql, new Object[]{templateId}, new templateDetailRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("查询时段控模板明细列表出错！");
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean queryCtrlSchemeName(String ctrlSchemeName, String staffNo) {
		String sql = 
			"SELECT 1 res\n" +
			"  FROM W_CTRL_SCHEME W\n" + 
			" WHERE W.CTRL_SCHEME_NAME = ?\n" + 
			"   AND STAFF_NO = ?";

		List<String> list = getJdbcTemplate().query(sql, new Object[]{ctrlSchemeName,staffNo}, new stringMapper());
		if(list==null || list.size()<=0){
			return false;
		}
		return true;
	}

	/*--------------------------------mapper----------------------------------------*/
	class pwrctrlSchemeExecMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PwrctrlSchemeExecBean pwrctrlschemeexecbean = new PwrctrlSchemeExecBean();
			 try { 
				pwrctrlschemeexecbean.setConsName(rs.getString("CONS_NAME"));
				pwrctrlschemeexecbean.setConsNo(rs.getString("CONS_NO"));
				pwrctrlschemeexecbean.setOrgName(rs.getString("ORG_NAME"));
				pwrctrlschemeexecbean.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				pwrctrlschemeexecbean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				pwrctrlschemeexecbean.setTotalNo(rs.getShort("TOTAL_NO"));
				pwrctrlschemeexecbean.setCtrlFlag(rs.getString("CTRL_FLAG"));
				pwrctrlschemeexecbean.setOrgNo(rs.getString("ORG_NO"));
				pwrctrlschemeexecbean.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				pwrctrlschemeexecbean.setTmnlPaulPower("1".equals(rs.getString("STATUS_CODE"))?"是":("0".equals(rs.getString("STATUS_CODE"))?"否":""));
				pwrctrlschemeexecbean.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getShort("TOTAL_NO"));
				return pwrctrlschemeexecbean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class stringMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			try { 
				return rs.getString("res");
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class floatDownCtrlRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			WFloatDownCtrl floatDownCtrl = new WFloatDownCtrl();
			try { 
				floatDownCtrl.setAlertTime1(rs.getInt("alert_time1"));
				floatDownCtrl.setAlertTime2(rs.getInt("alert_time2"));
				floatDownCtrl.setAlertTime3(rs.getInt("alert_time3"));
				floatDownCtrl.setAlertTime4(rs.getInt("alert_time4"));
				floatDownCtrl.setFloatDownPercent(rs.getShort("float_down_percent"));
				floatDownCtrl.setFloatDownWtime(rs.getShort("float_down_wtime"));
				floatDownCtrl.setPowerFreezetime(rs.getShort("power_freezetime"));
				floatDownCtrl.setCtrlTime(rs.getShort("ctrl_time"));
				return floatDownCtrl;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class templateDetailRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			WPwrctrlTemplateDetail detail = new WPwrctrlTemplateDetail();
			WPwrctrlTemplateDetailId id = new WPwrctrlTemplateDetailId();
			try { 
				id.setTemplateId(rs.getLong("template_id"));
				id.setSectionStart(rs.getString("section_start"));
				id.setSectionEnd(rs.getString("section_end"));
				id.setSectionNo(rs.getInt("section_no"));
				id.setIsValid(rs.getBoolean("is_valid"));
				id.setConst1(rs.getFloat("const1"));
				id.setConst2(rs.getFloat("const2"));
				id.setConst3(rs.getFloat("const3"));
				detail.setId(id);
				return detail;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
