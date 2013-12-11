package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IRemoteControlJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.WTmnlTurn;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 遥控Jdbc之Dao实现类
 * @author 姜炜超
 */
public class RemoteControlJdbcDaoImpl extends JdbcBaseDAOImpl implements IRemoteControlJdbcDao {
	protected Logger logger = Logger.getLogger(this.getClass());//定义日志
	
	public final static String LOAD_UI_GRID="ui";//遥测电压电流标记
	public final static String LOAD_POWER_GRID="power";//遥测功率标记
	public final static String LOAD_CTRL_GRID="ctrl";//遥测开关拉合闸标记
	
	@Override
	public void deleteBySchemeId(Long ctrlSchemeId) {
		String sql = "delete from w_tmnl_turn w where w.ctrl_scheme_id =?";
		getJdbcTemplate().update(sql, new Object[]{ctrlSchemeId});
	}

	/**
	 * 保存或修改遥控明细表，主要是添加方案适用
	 * @param turn
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlTurn turn) throws DataAccessException{	
		String sql =
			"merge into w_tmnl_turn dest\n" +
			"using (select ? as org_no,\n" + 
			"              ? as cons_no,\n" + 
			"              ? as tmnl_asset_no,\n" + 
			"              ? as total_no,\n" + 
			"              ? as ctrl_scheme_id,\n" +
			"              ? as alert_delay_hours,\n" + 
			"              ? as limit_mins,\n" + 
			"              ? as is_send_sms,\n" + 
			"              ? as status_code,\n" + 
			"              ? as staff_no,\n" + 
			"              ? as save_time,\n" + 
			"              ? as turn_flag\n" + 
			"         from dual) src\n" + 
			"on (dest.cons_no = src.cons_no and dest.tmnl_asset_no = src.tmnl_asset_no)\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"     set dest.ctrl_scheme_id = src.ctrl_scheme_id,\n" + 
			"         dest.alert_delay_hours  = src.alert_delay_hours,\n" + 
			"         dest.limit_mins     = src.limit_mins,\n" + 
			"         dest.is_send_sms    = src.is_send_sms,\n" + 
			"         dest.status_code    = src.status_code,\n" + 
			"         dest.staff_no       = src.staff_no,\n" + 
			"         dest.save_time      = src.save_time,\n" +
			"         dest.turn_flag      = src.turn_flag\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_turn_id,\n" + 
			"     org_no,\n" + 
			"     cons_no,\n" + 
			"     tmnl_asset_no,\n" + 
			"     total_no,\n" + 
			"     ctrl_scheme_id,\n" + 
			"     alert_delay_hours,\n" + 
			"     limit_mins,\n" + 
			"     is_send_sms,\n" + 
			"     status_code,\n" + 
			"     staff_no,\n" + 
			"     save_time,\n" + 
			"     turn_flag)\n" +
			"  values\n" + 
			"    (s_w_tmnl_turn.nextval,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?)";
		this.getJdbcTemplate().update(sql, objToArrayByScheme(turn));
	}
	
	/**
	 * 参数转换，用于方案添加时使用
	 * @param busi
	 * @return 
	 */
	private Object[] objToArrayByScheme(WTmnlTurn turn) {
		Object[] results = new Object[24];
		results[0] = turn.getOrgNo();
		results[1] = turn.getConsNo();
		results[2] = turn.getTmnlAssetNo();
		results[3] = turn.getTotalNo();
		results[4] = turn.getCtrlSchemeId();
		results[5] = turn.getAlertDelayHours();
		results[6] = turn.getLimitMins();
		results[7] = turn.getIsSendSms();
		results[8] = turn.getStatusCode();
		results[9] = turn.getStaffNo();
		results[10] = turn.getSaveTime();
		results[11] = turn.getTurnFlag();
		
		results[12] = turn.getOrgNo();
		results[13] = turn.getConsNo();
		results[14] = turn.getTmnlAssetNo();
		results[15] = turn.getTotalNo();
		results[16] = turn.getCtrlSchemeId();
		results[17] = turn.getAlertDelayHours();
		results[18] = turn.getLimitMins();
		results[19] = turn.getIsSendSms();
		results[20] = turn.getStatusCode();
		results[21] = turn.getStaffNo();
		results[22] = turn.getSaveTime();
		results[23] = turn.getTurnFlag();
		return results;
	}
	
	
	/**
	 * 根据用户号查询用户信息
	 * @param consNo
	 * @return List<RemoteControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo, String flag) throws DataAccessException{
		if(LOAD_POWER_GRID.equals(flag)){
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       c.run_cap," +
				"       e.total_no," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       e_data_total		e,\n" +
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"  and t.tmnl_asset_no = ?";
			try{
				return getJdbcTemplate().query(sql, new String[]{tmnlAssetNo}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据用户编号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)){
			String sql = 
				"select c.cons_name,\n" +
				"               c.cons_no,\n" + 
				"               o.org_no,\n" + 
				"               o.org_name,\n" + 
				"               t.terminal_addr,\n" + 
				"               e.mp_sn,\n" +  
				"               e.ct,\n" +
				"               e.pt,\n" +
				"               turn.ctrl_flag,\n" + 
				"               ar.is_online,\n" + 
				"               t.tmnl_asset_no,\n" + 
				"               t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"               t.protocol_code\n" + 
				"          from w_tmnl_turn       turn,\n" + 
				"               c_cons            c,\n" + 
				"               o_org             o,\n" + 
				"               vw_tmnl_run        t,\n" + 
				"               e_data_mp e,\n" + 
				"               a_tmnl_real_time  ar\n" + 
				"         where t.cons_no = c.cons_no\n" + 
				"           and c.org_no = o.org_no\n" + 
				"           and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"           and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"           and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"           and e.mp_sn <> 0\n" +
				"  			 and t.tmnl_asset_no = ?";
			try{
				return getJdbcTemplate().query(sql, new String[]{tmnlAssetNo}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据用户编号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select o.org_no,\n" +
				"       c.cons_no,\n" + 
				"       r.terminal_addr,\n" + 
				"       r.tmnl_asset_no,\n" + 
				"       c.cons_name,\n" + 
				"       o.org_name,\n" + 
				"       r.protocol_code,\n" + 
				"       r.ps_ensure_flag tmnl_paul_power,\n" + 
				"       ar.is_online,\n" + 
				"       w.turn_flag ctrl_flag\n" + 
				" from c_cons c, o_org o, w_tmnl_ctrl_status w, vw_tmnl_run r, a_tmnl_real_time  ar\n" + 
				" where r.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and r.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and r.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and r.tmnl_asset_no = ?";
			try{
				return getJdbcTemplate().query(sql, new String[]{tmnlAssetNo}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据用户编号查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}
	}
	
	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 * @return List<RemoteControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoByOrgNo(String orgNo,String orgType, PSysUser user, String flag) throws DataAccessException{
		if(LOAD_POWER_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.total_no," +
				"       c.run_cap," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       e_data_total		e,\n" +
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n";
				if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
					orgNo = "%"+orgNo+"%";
					sql += "   and c.area_no  like ?\n";
				} else if("06".equals(orgType)) {
					sql += "   and c.org_no = ?\n";
				} else {
					return new ArrayList<RemoteControlDto>();
				}
				sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{orgNo,user.getStaffNo()}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据供电单位号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.mp_sn,\n" + 
				"       e.ct,\n" +
				"       e.pt,\n" +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       e_data_mp e,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"   and e.mp_sn <> 0\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n";
				if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
					orgNo = "%"+orgNo+"%";
					sql += "   and c.area_no  like ?\n";
				} else if("06".equals(orgType)) {
					sql += "   and c.org_no = ?\n";
				} else {
					return new ArrayList<RemoteControlDto>();
				}
				sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{orgNo,user.getStaffNo()}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据供电单位号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       w.turn_flag ctrl_flag,\n" +
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_ctrl_status w, \n" +
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n";
				if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
					orgNo = "%"+orgNo+"%";
					sql += "   and c.area_no  like ?\n";
				} else if("06".equals(orgType)) {
					sql += "   and c.org_no = ?\n";
				} else {
					return new ArrayList<RemoteControlDto>();
				}
				sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{orgNo,user.getStaffNo()}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据供电单位号查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}
	}
	
	/**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 * @return List<RemoteControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoBySchemeNo(Long schemeNo, String flag) throws DataAccessException{
		if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"             c.cons_no,\n" + 
				"             o.org_no,\n" + 
				"             o.org_name,\n" + 
				"             t.terminal_addr,\n" + 
				"             wt.turn_flag ctrl_flag,\n" + 
				"             turn.alert_delay_hours,\n" + 
				"             turn.limit_mins,\n" + 
				"             ar.is_online,\n" + 
				"             t.tmnl_asset_no,\n" + 
				"             t.ps_ensure_flag as tmnl_paul_power,\n" + 
				"             t.protocol_code\n" + 
				"        from w_tmnl_turn       turn,\n" + 
				"             w_tmnl_ctrl_status  wt,\n"+
				"             c_cons            c,\n" + 
				"             o_org             o,\n" + 
				"             vw_tmnl_run        t,\n" + 
				"             a_tmnl_real_time  ar\n" + 
				"       where t.cons_no = c.cons_no\n" + 
				"         and c.org_no = o.org_no\n" +
				"         and t.tmnl_asset_no = wt.tmnl_asset_no(+)\n" +
				"         and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"         and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" + 
				"         and turn.ctrl_scheme_id = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{schemeNo}, new ctrlSchemeRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据方案id查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)){
			String sql = 
				"select c.cons_name,\n" +
				"             c.cons_no,\n" + 
				"             o.org_no,\n" + 
				"             o.org_name,\n" + 
				"             t.terminal_addr,\n" + 
				"             turn.ctrl_flag,\n" + 
				"             turn.alert_delay_hours,\n" + 
				"             turn.limit_mins,\n" + 
				"             ar.is_online,\n" + 
				"             e.ct,\n" +
				"             e.pt,\n" +
				"			   e.mp_sn,\n" +
				"             t.tmnl_asset_no,\n" + 
				"             t.ps_ensure_flag as tmnl_paul_power,\n" + 
				"             t.protocol_code\n" + 
				"        from w_tmnl_turn       turn,\n" + 
				"             c_cons            c,\n" + 
				"             e_data_mp e,\n" + 
				"             o_org             o,\n" + 
				"             vw_tmnl_run        t,\n" + 
				"             a_tmnl_real_time  ar\n" + 
				"       where t.cons_no = c.cons_no\n" + 
				"         and c.org_no = o.org_no\n" + 
				"         and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"         and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"         and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" + 
				"         and turn.ctrl_scheme_id = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{schemeNo}, new uiSchemeRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据用户编号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_POWER_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"             c.cons_no,\n" + 
				"             o.org_no,\n" + 
				"             o.org_name,\n" + 
				"             t.terminal_addr,\n" + 
				"             turn.ctrl_flag,\n" + 
				"             turn.alert_delay_hours,\n" + 
				"             turn.limit_mins,\n" + 
				"             ar.is_online,\n" + 
				"       	  c.run_cap," +
				"			  e.total_no,\n" +
				"             t.tmnl_asset_no,\n" + 
				"             t.ps_ensure_flag as tmnl_paul_power,\n" + 
				"             t.protocol_code\n" + 
				"        from w_tmnl_turn       turn,\n" + 
				"             c_cons            c,\n" + 
				"             o_org             o,\n" + 
				"			   e_data_total	e,\n" +
				"             vw_tmnl_run        t,\n" + 
				"             a_tmnl_real_time  ar\n" + 
				"       where t.cons_no = c.cons_no\n" + 
				"         and c.org_no = o.org_no\n" + 
				"         and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"         and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"         and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" + 
				"         and turn.ctrl_scheme_id = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{schemeNo}, new powerSchemeRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据用户编号查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}	
	}
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return List<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoByLineId(String lineId, PSysUser user, String flag){
		if(LOAD_POWER_GRID.equals(flag)) {	
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.total_no," +
				"       c.run_cap," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_total		e,\n" +
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and c.line_id = ?\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据线路Id查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.mp_sn,\n" +  
				"       e.ct,\n" +
				"       e.pt,\n" +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_mp e,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and e.mp_sn <> 0\n" +
				"   and c.line_id = ?\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据线路Id查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       w.turn_flag ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_ctrl_status w, \n" +
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and c.line_id = ?\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{lineId,user.getStaffNo()}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据线路Id查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}		
	}
	
	/**
	 * 根据组号查询用户信息
	 * @param groupNo
	 * @param start
	 * @param limit
	 * @return List<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoByGroupNo(String groupNo, String flag){
		if(LOAD_POWER_GRID.equals(flag)) {	
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.total_no," +
				"       c.run_cap," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       e_data_total		e,\n" +
				"       t_tmnl_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"  and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.mp_sn,\n" +  
				"       e.ct,\n" +
				"       e.pt,\n" +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       e_data_mp e,\n" + 
				"       t_tmnl_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and e.mp_sn <> 0\n" +
				"   and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       w.turn_flag ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_ctrl_status w, \n" +
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       t_tmnl_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" +
				"   and t.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"  and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoByUgpGroupNo(String groupNo, String flag) {
		if(LOAD_POWER_GRID.equals(flag)) {	
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.total_no," +
				"       c.run_cap," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_total		e,\n" +
				"       vw_tmnl_run        t,\n" + 
				"       r_user_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"  and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.mp_sn,\n" +  
				"       e.ct,\n" +
				"       e.pt,\n" +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_mp e,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       r_user_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and e.mp_sn <> 0\n" +
				"   and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       w.turn_flag ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_ctrl_status w, \n" +
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       r_user_group_detail gp,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" +
				"   and t.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"  and gp.group_no = ?";
			try{
				return getJdbcTemplate().query(sql, new Object[]{groupNo}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据组号查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}	
	}

	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return List<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RemoteControlDto> findUserInfoBySubsId(String subsId, PSysUser user, String flag){
		if(LOAD_POWER_GRID.equals(flag)) {	
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.total_no," +
				"       c.run_cap," +
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_total		e,\n" +
				"       g_subs_line_rela g,\n" +
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" +
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and c.line_id = g.line_id\n" +
				"  and (c.subs_id = ? or g.subs_id =?)\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{subsId,subsId,user.getStaffNo()}, new PowerRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据变电站标识查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_UI_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       e.mp_sn,\n" + 
				"       e.ct,\n" +
				"       e.pt,\n" + 
				"       turn.ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_turn       turn,\n" + 
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       e_data_mp e,\n" + 
				"       g_subs_line_rela g,\n" +
				"       vw_tmnl_run        t,\n" + 
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = turn.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = e.tmnl_asset_no\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and e.mp_sn <> 0\n" +
				"   and c.line_id = g.line_id\n" +
				"  and (c.subs_id = ? or g.subs_id =?)\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{subsId,subsId,user.getStaffNo()}, new UiRemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据变电站标识查询用户信息出错！");
				throw e;
			}
		} else if(LOAD_CTRL_GRID.equals(flag)) {
			String sql = 
				"select c.cons_name,\n" +
				"       c.cons_no,\n" + 
				"       o.org_no,\n" + 
				"       o.org_name,\n" + 
				"       t.terminal_addr,\n" + 
				"       w.turn_flag ctrl_flag,\n" + 
				"       ar.is_online,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.ps_ensure_flag as tmnl_paul_power, \n" + 
				"       t.protocol_code\n" + 
				"  from w_tmnl_ctrl_status w, \n" +
				"       c_cons            c,\n" + 
				"       o_org             o,\n" + 
				"       vw_tmnl_run        t,\n" + 
				"       g_subs_line_rela g,\n" +
				"       a_tmnl_real_time  ar\n" + 
				" where t.cons_no = c.cons_no\n" + 
				"   and c.org_no = o.org_no\n" + 
				"   and t.tmnl_asset_no = w.tmnl_asset_no(+)\n" + 
				"   and t.tmnl_asset_no = ar.tmnl_asset_no(+)\n" +
				"   and c.line_id = g.line_id\n" +
				"  and (c.subs_id = ? or g.subs_id =?)\n";
			sql += SysPrivilige.addOrgPri(user, "C");	
			try{
				return getJdbcTemplate().query(sql, new Object[]{subsId, subsId,user.getStaffNo()}, new RemoteCtrlRowMapper());
			}catch(RuntimeException e){
				this.logger.debug("根据变电站标识查询用户信息出错！");
				throw e;
			}
		} else {
			return new ArrayList<RemoteControlDto>();
		}	
	}
	
	/**
	 * 更新遥控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param turn
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateTurn(WTmnlTurn turn) throws DataAccessException{
		int ctrlFlag = 0;
		if(null != turn){
			if(turn.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		    	"update w_tmnl_turn turn\n" +
		        "set turn.ctrl_flag = ?,\n" + 
		        "    turn.send_time = ?\n" + 
		        "where turn.tmnl_asset_no = ?";
            this.getJdbcTemplate().update(sql, new Object[]{ctrlFlag,turn.getSendTime(),turn.getTmnlAssetNo()});
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findProItemNo(String turnType) {
		 String sql = "";
		if(LOAD_UI_GRID.equals(turnType)) {
			sql =
				"select b.protocol_no as prot_item_no\n" +
				"  from b_clear_protocol b\n" + 
				" where b.protocol_no = '1E114F'\n" + 
				"    or b.protocol_no = '1E11FF'";
		} else if(LOAD_POWER_GRID.equals(turnType)){
			sql = 
				"select b.protocol_no as prot_item_no\n" +
				"  from b_clear_protocol b\n" + 
				" where b.protocol_no = '35111F'\n" +
				"  or b.protocol_no = '3511FF'\n" +
				"  or b.protocol_no = '35112F'\n" +
				"  or b.protocol_no = '3511FF'";
		} else if(LOAD_CTRL_GRID.equals(turnType)){
			sql = 
				"select b.protocol_no as prot_item_no\n" +
				"  from b_clear_protocol b\n" + 
				" where b.protocol_no = '4E147F'\n" + 
				"    or b.protocol_no = '4E14FF'";	
		} else {
			return null;
		}
		return getJdbcTemplate().query(sql,  new stringRowMapper());
	}

	/**
	 * 自定义查询返回的值对象，用于遥控的Grid显示
	 */
	class UiRemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				remoteCtrlDto.setMpSn(rs.getShort("MP_SN"));
				remoteCtrlDto.setCt(rs.getInt("CT"));
				remoteCtrlDto.setPt(rs.getInt("PT"));
				remoteCtrlDto.setKeyId(rs.getString("TMNL_ASSET_NO") + rs.getShort("MP_SN"));
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class PowerRemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				remoteCtrlDto.setTotalNo(rs.getShort("TOTAL_NO"));
				remoteCtrlDto.setRunCap(rs.getString("RUN_CAP"));
				remoteCtrlDto.setKeyId(rs.getString("TMNL_ASSET_NO") + rs.getShort("TOTAL_NO"));
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class RemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class ctrlSchemeRemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setAlertDelayHours(rs.getShort("ALERT_DELAY_HOURS"));
				remoteCtrlDto.setLimitMins(rs.getShort("LIMIT_MINS"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class uiSchemeRemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setAlertDelayHours(rs.getShort("ALERT_DELAY_HOURS"));
				remoteCtrlDto.setLimitMins(rs.getShort("LIMIT_MINS"));
				remoteCtrlDto.setMpSn(rs.getShort("MP_SN"));
				remoteCtrlDto.setCt(rs.getInt("CT"));
				remoteCtrlDto.setPt(rs.getInt("PT"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class powerSchemeRemoteCtrlRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RemoteControlDto remoteCtrlDto = new RemoteControlDto();
			try {
				remoteCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				remoteCtrlDto.setConsNo(rs.getString("CONS_NO"));
				remoteCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				remoteCtrlDto.setConsName(rs.getString("CONS_NAME"));
				remoteCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				remoteCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				remoteCtrlDto.setCtrlFlag(rs.getString("CTRL_FLAG"));
				remoteCtrlDto.setTotalNo(rs.getShort("TOTAL_NO"));
				remoteCtrlDto.setAlertDelayHours(rs.getShort("ALERT_DELAY_HOURS"));
				remoteCtrlDto.setLimitMins(rs.getShort("LIMIT_MINS"));
				remoteCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				remoteCtrlDto.setRealTimeStatus(rs.getBoolean("IS_ONLINE"));
				remoteCtrlDto.setRunCap(rs.getString("RUN_CAP"));
				remoteCtrlDto.setTmnlPaulPower("1".equals(rs.getString("TMNL_PAUL_POWER"))?"是":"否");
				return remoteCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class stringRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			return rs.getString("prot_item_no");
		}
	}
}
