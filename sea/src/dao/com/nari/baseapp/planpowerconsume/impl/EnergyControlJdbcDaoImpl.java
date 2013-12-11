package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IEnergyControlJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.EnergyControlDto;
import com.nari.orderlypower.WTmnlMonPctrl;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 月电量定值控Jdbc之Dao实现类
 * @author 姜炜超/姜海辉
 */
public class EnergyControlJdbcDaoImpl extends JdbcBaseDAOImpl implements IEnergyControlJdbcDao{
	protected Logger logger = Logger.getLogger(this.getClass());//定义日志
	
	/**
	 * 修改月电量定值控方案明细表
	 */
	public void updateMonPowerContrl(WTmnlMonPctrl wTmnlMonPctrl,short status) throws DataAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append("update w_tmnl_mon_pctrl w\n" )
		  .append(	"   set w.ctrl_flag = ?,\n")
		  .append(	"       w.send_time = ?,\n" );
		List<Object> list = new ArrayList<Object>();
		list.add(wTmnlMonPctrl.getCtrlFlag());
		list.add(wTmnlMonPctrl.getSendTime());
        if(3==status){
        	sb.append(	"   w.success_time=?\n");
        	list.add(wTmnlMonPctrl.getSaveTime());
        }
        else if(4==status){
        	sb.append(	"   w.failure_code=?\n");
        	list.add(wTmnlMonPctrl.getFailureCode());
        }
        else
        	return;
		sb.append(" where w.tmnl_asset_no = ?\n" )
		  .append("   and w.total_no = ?\n" )
		  .append("   and w.ctrl_scheme_id = ?");
		list.add(wTmnlMonPctrl.getTmnlAssetNo());
		list.add(wTmnlMonPctrl.getTotalNo());
		list.add(wTmnlMonPctrl.getCtrlSchemeId());
		try{
			this.getJdbcTemplate().update(sb.toString(),list.toArray());
		}catch(RuntimeException e){
			this.logger.debug("更新月电控方案明细表出错！");
			throw e;
		}
	}
	
	
	/**
	 * 保存或修改月电量定值控明细表，主要是参数下发适用
	 * @param monPctrl
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlMonPctrl wTmnlMonPctrl) throws DataAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"merge into w_tmnl_mon_pctrl dest\n" +
			"using (select ? as org_no,\n" + 
			"              ? as cons_no,\n" + 
			"              ? as tmnl_asset_no,\n" + 
			"              ? as total_no,\n" + 
			"              ? as ctrl_scheme_id,\n" + 
			"              ? as is_exec,\n" + 
			"              ? as float_value,\n" + 
			"              ? as power_const,\n" + 
			"              ? as ctrl_flag,\n" + 
			"              ? as is_send_sms,\n" + 
			"              ? as status_code,\n" + 
			"              ? as staff_no,\n" + 
			"              ? as save_time\n" + 
			"              ? as send_time,\n" + 
			"              ? as success_time,\n" + 
			"              ? as failure_code\n" + 
			"         from dual) src\n" + 
			"on (dest.tmnl_asset_no = src.tmnl_asset_no and dest.total_no = src.total_no)\n" + 
			"when matched then\n" + 
			"  update\n"+
			"     set dest.org_no = src.org_no,\n" +
			"         dest.cons_no= src.cons_no,\n" + 
			"         dest.status_code = src.status_code,\n" + 
			"         dest.staff_no = src.staff_no,\n" + 
			"         dest.send_time = src.send_time,\n" + 
			"         dest.success_time = src.success_time,\n" + 
			"         dest.failure_code = src.failure_code,\n");
		if(null!=wTmnlMonPctrl.getPowerConst()){
			sb.append("dest.is_exec = src.is_exec,\n" +
					  "dest.float_value = src.float_value,\n" +
					  "dest.power_const = src.power_const,\n" +
					  "dest.save_time = src.save_time\n");
		}
		else if(null!=wTmnlMonPctrl.getCtrlFlag()){
			sb.append("dest.ctrl_flag = src.ctrl_flag\n");
		}
		else 
			return;
		sb.append(
			"when not matched then\n" + 
			"  insert\n" + 
			"    (mon_pctrl_id,\n" + 
			"     org_no,\n" + 
			"     cons_no,\n" + 
			"     tmnl_asset_no,\n" + 
			"     total_no,\n" + 
			"     ctrl_scheme_id,\n" +
			"     is_exec,\n" + 
			"     float_value,\n" + 
			"     power_const,\n" + 
			"     ctrl_flag,\n" +
			"     is_send_sms,\n" + 
			"     status_code,\n" + 
			"     staff_no,\n" + 
			"     save_time,\n" +
			"     send_time,\n" +
			"     success_time,\n" +
			"     failure_code)\n" +
			"  values\n" + 
			"    (s_w_tmnl_mon_pctrl.nextval,\n" + 
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
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?)");
		this.getJdbcTemplate().update(sb.toString(), objToArray(wTmnlMonPctrl));
	}
	
	
	/**
	 * 保存或修改月电量定值控明细表，主要是针对参数下发
	 * @param monPctrl
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlMonPctrl wTmnlMonPctrl) throws DataAccessException{
		String sql = 
			"merge into w_tmnl_mon_pctrl dest\n" +
			"using (select ? as org_no,\n" + 
			"              ? as cons_no,\n" + 
			"              ? as tmnl_asset_no,\n" + 
			"              ? as total_no,\n" + 
			"              ? as ctrl_scheme_id,\n" + 
			"              ? as is_exec,\n" + 
			"              ? as float_value,\n" + 
			"              ? as power_const,\n" + 
			"              ? as ctrl_flag,\n" + 
			"              ? as is_send_sms,\n" + 
			"              ? as status_code,\n" + 
			"              ? as staff_no,\n" + 
			"              ? as save_time\n" + 
			"              ? as send_time,\n" + 
			"              ? as success_time,\n" + 
			"              ? as failure_code\n" + 
			"         from dual) src\n" + 
			"on (dest.tmnl_asset_no = src.tmnl_asset_no and dest.total_no = src.total_no)\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"     set dest.org_no = src.org_no,\n" +
			"         dest.cons_no= src.cons_no,\n" + 
			"         dest.ctrl_scheme_id= src.ctrl_scheme_id,\n" + 
			"         dest.is_exec = src.is_exec,\n" +
			"         dest.float_value  = src.float_value,\n" + 
			"         dest.power_const = src.power_const,\n" + 
			"         dest.status_code = src.status_code,\n" + 
			"         dest.staff_no = src.staff_no,\n" + 
			"         dest.save_time = src.save_time,\n" + 
			"         dest.send_time = src.send_time,\n" +
			"         dest.success_time = src.success_time,\n" + 
			"         dest.failure_code = src.failure_code\n"+
			"when not matched then\n" + 
			"  insert\n" + 
			"    (mon_pctrl_id,\n" + 
			"     org_no,\n" + 
			"     cons_no,\n" + 
			"     tmnl_asset_no,\n" + 
			"     total_no,\n" + 
			"     ctrl_scheme_id,\n" +
			"     is_exec,\n" + 
			"     float_value,\n" + 
			"     power_const,\n" + 
			"     ctrl_flag,\n" +
			"     is_send_sms,\n" + 
			"     status_code,\n" + 
			"     staff_no,\n" + 
			"     save_time,\n" +
			"     send_time,\n" +
			"     success_time,\n" +
			"     failure_code)\n" +
			"  values\n" + 
			"    (s_w_tmnl_mon_pctrl.nextval,\n" + 
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
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?,\n" + 
			"     ?)";
		this.getJdbcTemplate().update(sql, objToArray(wTmnlMonPctrl));
	}

	/**
	 * 参数转换
	 * @param wTmnlMonPctrl
	 * @return 
	 */
	private Object[] objToArray(WTmnlMonPctrl wTmnlMonPctrl) {
		Object[] results = new Object[32];
		results[0] = wTmnlMonPctrl.getOrgNo();
		results[1] = wTmnlMonPctrl.getConsNo();
		results[2] = wTmnlMonPctrl.getTmnlAssetNo();
		results[3] = wTmnlMonPctrl.getTotalNo();
		results[4] = wTmnlMonPctrl.getCtrlSchemeId();
		results[5] = wTmnlMonPctrl.getIsExec();
		results[6] = wTmnlMonPctrl.getFloatValue();
		results[7] = wTmnlMonPctrl.getPowerConst();
		results[8] = wTmnlMonPctrl.getCtrlFlag();
		results[9] = wTmnlMonPctrl.getIsSendSms();
		results[10] = wTmnlMonPctrl.getStatusCode();
		results[11] = wTmnlMonPctrl.getStaffNo();
		results[12] = wTmnlMonPctrl.getSaveTime();
		results[13] = wTmnlMonPctrl.getSendTime();
		results[14] = wTmnlMonPctrl.getSuccessTime();
		results[15] = wTmnlMonPctrl.getFailureCode();
		results[16] = wTmnlMonPctrl.getOrgNo();
		results[17] = wTmnlMonPctrl.getConsNo();
		results[18] = wTmnlMonPctrl.getTmnlAssetNo();
		results[19] = wTmnlMonPctrl.getTotalNo();
		results[20] = wTmnlMonPctrl.getCtrlSchemeId();
		results[21] = wTmnlMonPctrl.getIsExec();
		results[22] = wTmnlMonPctrl.getFloatValue();
		results[23] = wTmnlMonPctrl.getPowerConst();
		results[24] = wTmnlMonPctrl.getCtrlFlag();
		results[25] = wTmnlMonPctrl.getIsSendSms();
		results[26] = wTmnlMonPctrl.getStatusCode();
		results[27] = wTmnlMonPctrl.getStaffNo();
		results[28] = wTmnlMonPctrl.getSaveTime();
		results[29] = wTmnlMonPctrl.getSendTime();
		results[30] = wTmnlMonPctrl.getSuccessTime();
		results[31] = wTmnlMonPctrl.getFailureCode();
		return results;
	}
	
	/**
	 * 参数转换，用于方案添加时使用（针对下发操作）
	 * @param busi
	 * @return 
	 *//*
	private Object[] objToArrayByParam(WTmnlMonPctrl monPctrl) {
		Object[] results = new Object[22];
		results[0] = monPctrl.getOrgNo();
		results[1] = monPctrl.getConsNo();
		results[2] = monPctrl.getTmnlAssetNo();
		results[3] = monPctrl.getTotalNo();
		
		results[4] = monPctrl.getFloatValue();
		results[5] = monPctrl.getPowerConst();
		results[6] = monPctrl.getIsSendSms();
		results[7] = monPctrl.getStatusCode();
		results[8] = monPctrl.getStaffNo();
		results[9] = monPctrl.getSaveTime();
		results[10] = monPctrl.getSendTime();
		
		results[11] = monPctrl.getOrgNo();
		results[12] = monPctrl.getConsNo();
		results[13] = monPctrl.getTmnlAssetNo();
		results[14] = monPctrl.getTotalNo();
		results[15] = monPctrl.getFloatValue();
		results[16] = monPctrl.getPowerConst();
		results[17] = monPctrl.getIsSendSms();
		results[18] = monPctrl.getStatusCode();
		results[19] = monPctrl.getStaffNo();
		results[20] = monPctrl.getSaveTime();
		results[21] = monPctrl.getSendTime();
		return results;
	}*/
	
	/**
	 * 根据终端资产号查询用户信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo)throws DataAccessException{
		String sql = 
			"select t.org_no,\n" +
			"       o.org_name,\n" + 
			"       t.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       t.tmnl_asset_no,\n" + 
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" + 
			"       total.total_no,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.mon_pctrl_flag\n" + 
			"  from o_org              o,\n" + 
			"       c_cons             c,\n" + 
			"       vw_tmnl_run        t,\n" + 
			"       e_data_total       total,\n" + 
			"       w_tmnl_ctrl_status w\n" + 
			" where o.org_no = t.org_no\n" + 
			"   and c.cons_no = t.cons_no\n" + 
			"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
			"   and total.total_no is not null\n" + 
			"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
			"   and w.total_no(+) = total.total_no\n" + 
			"   and t.tmnl_asset_no = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{tmnlAssetNo},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据用户号查询用户信息
	 * @param consNo
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByConsNo(String consNo)throws DataAccessException{
		String sql=
			"  select c.cons_name,\n" +
			"      t.cons_no,\n" + 
			"      o.org_name,\n" + 
			"      t.terminal_addr,\n" +
			"      t.protocol_code,\n" + 
			"      total.total_no,\n" + 
			"      pctrl.power_const,\n" + 
			"      pctrl.float_value,\n" + 
			//"       pctrl.is_send_sms,\n" +  
			"      t.tmnl_asset_no,\n" + 
			"      paul.ctrl_flag as tmnl_paul_power,\n" + 
			"      o.org_no\n" + 
			" from w_tmnl_mon_pctrl pctrl, c_cons c, o_org o, vw_tmnl_run t, e_data_total total, w_tmnl_paul_power paul\n" + 
			"where t.cons_no = c.cons_no\n" + 
			"  and c.org_no = o.org_no\n" + 
			"  and t.tmnl_asset_no = total.tmnl_asset_no\n" + 
			"  and total.total_no is not null\n" + 
			"  and total.tmnl_asset_no = pctrl.tmnl_asset_no(+)\n" + 
			"  and total.total_no = pctrl.total_no(+)\n" + 
			"  and total.tmnl_asset_no = paul.tmnl_asset_no(+)\n" + 
			"  and c.cons_no = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{consNo},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据用户编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByOrgNo(String orgNo, String orgType,PSysUser pSysUser) throws DataAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append( 
				"select t.org_no,\n" +
				"       o.org_name,\n" + 
				"       t.cons_no,\n" + 
				"       c.cons_name,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.terminal_addr,\n" + 
				"       t.protocol_code,\n" + 
				"       total.total_no,\n" + 
				"       t.ps_ensure_flag,\n" + 
				"       w.mon_pctrl_flag\n" + 
				"  from o_org              o,\n" + 
				"       c_cons             c,\n" + 
				"       vw_tmnl_run         t,\n" + 
				"       e_data_total       total,\n" + 
				"       w_tmnl_ctrl_status w\n" + 
				" where o.org_no = t.org_no\n" + 
				"   and c.cons_no = t.cons_no\n" + 
				"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
				"   and total.total_no is not null\n" + 
				"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
				"   and w.total_no(+) = total.total_no\n");
			if("03".equals(orgType)||"04".equals(orgType)){
				orgNo="%"+orgNo+"%";
				sb.append("  and c.Area_No like ?");
			}else if("06".equals(orgType))
				sb.append("  and c.Org_No = ?");
			else
				 return null;
			sb.append(SysPrivilige.addOrgPri(pSysUser,"c"));
		try{
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{orgNo,pSysUser.getStaffNo()},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位号查询用户信息出错！");
			throw e;
		}
	}
	
	
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByLineId(String lineId,PSysUser pSysUser){
		StringBuffer sb=new StringBuffer();
		sb.append(
				"select t.org_no,\n" +
				"       o.org_name,\n" + 
				"       t.cons_no,\n" + 
				"       c.cons_name,\n" + 
				"       t.tmnl_asset_no,\n" + 
				"       t.terminal_addr,\n" + 
				"       t.protocol_code,\n" + 
				"       total.total_no,\n" + 
				"       t.ps_ensure_flag,\n" + 
				"       w.mon_pctrl_flag\n" + 
				"  from o_org              o,\n" + 
				"       c_cons             c,\n" + 
				"       vw_tmnl_run         t,\n" + 
				"       e_data_total       total,\n" + 
				"       w_tmnl_ctrl_status w\n" + 
				" where o.org_no = t.org_no\n" + 
				"   and c.cons_no = t.cons_no\n" + 
				"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
				"   and total.total_no is not null\n" + 
				"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
				"   and w.total_no(+) = total.total_no\n" + 
				"   and c.line_id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{lineId,pSysUser.getStaffNo()},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据组号查询用户信息
	 * @param groupNo
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByCommonGroupNo(String groupNo){
		//StringBuffer sb=new StringBuffer();
		String sql = 
			"select t.org_no,\n" +
			"       o.org_name,\n" + 
			"       t.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       t.tmnl_asset_no,\n" + 
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" + 
			"       total.total_no,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.mon_pctrl_flag\n" + 
			"  from o_org               o,\n" + 
			"       c_cons              c,\n" + 
			"       vw_tmnl_run          t,\n" + 
			"       e_data_total        total,\n" + 
			"       w_tmnl_ctrl_status  w,\n" + 
			"       r_user_group_detail gp\n"+
			" where o.org_no = t.org_no\n" + 
			"   and c.cons_no = t.cons_no\n" + 
			"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
			"   and total.total_no is not null\n" + 
			"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
			"   and w.total_no(+) = total.total_no\n" +
			"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"   and gp.group_no = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{groupNo},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据组号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据组号查询用户信息
	 * @param groupNo
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoByContrlGroupNo(String groupNo){
		//StringBuffer sb=new StringBuffer();
		String sql = 
			"select t.org_no,\n" +
			"       o.org_name,\n" + 
			"       t.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       t.tmnl_asset_no,\n" + 
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" + 
			"       total.total_no,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.mon_pctrl_flag\n" + 
			"  from o_org               o,\n" + 
			"       c_cons              c,\n" + 
			"       vw_tmnl_run          t,\n" + 
			"       e_data_total        total,\n" + 
			"       w_tmnl_ctrl_status  w,\n" + 
			"       t_tmnl_group_detail gp\n"+
			" where o.org_no = t.org_no\n" + 
			"   and c.cons_no = t.cons_no\n" + 
			"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
			"   and total.total_no is not null\n" + 
			"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
			"   and w.total_no(+) = total.total_no\n" +
			"   and t.tmnl_asset_no = gp.tmnl_asset_no\n" + 
			"   and gp.group_no = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{groupNo},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据组号查询用户信息出错！");
			throw e;
		}
	}
	
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoBySubsId(String subsId,PSysUser pSysUser){
		StringBuffer sb=new StringBuffer();
		//subsId = "%"+subsId+"%";
		sb.append(
			"select t.org_no,\n" +
			"       o.org_name,\n" + 
			"       t.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       t.tmnl_asset_no,\n" + 
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" + 
			"       total.total_no,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.mon_pctrl_flag\n" + 
			"  from o_org              o,\n" + 
			"       c_cons             c,\n" + 
			"       vw_tmnl_run         t,\n" + 
			"       e_data_total       total,\n" + 
			"       w_tmnl_ctrl_status w\n" + 
			" where o.org_no = t.org_no\n" + 
			"   and c.cons_no = t.cons_no\n" + 
			"   and total.tmnl_asset_no = t.tmnl_asset_no\n" + 
			"   and total.total_no is not null\n" + 
			"   and w.tmnl_asset_no(+) = total.tmnl_asset_no\n" + 
			"   and w.total_no(+) = total.total_no\n" + 
			"   and c.subs_id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"c"));
		try{
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{subsId,pSysUser.getStaffNo()},new EnergyCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateMonPctrl(WTmnlMonPctrl monPctrl) throws DataAccessException{
		int ctrlFlag = 0;
		if(null != monPctrl){
			if(monPctrl.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		    	"update w_tmnl_mon_pctrl pctrl\n" +
		        "set pctrl.ctrl_flag = ?,\n" + 
		        "    pctrl.send_time = ?\n" + 
		        "where pctrl.tmnl_asset_no = ? and pctrl.total_no = ?";
            this.getJdbcTemplate().update(sql, new Object[]{ctrlFlag,monPctrl.getSendTime(),monPctrl.getTmnlAssetNo(),monPctrl.getTotalNo()});
		}
	}
	
	/**
	 * 更新月电量定值控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param monPctrl
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateMonPctrlStatus(WTmnlMonPctrl monPctrl) throws DataAccessException{
		if(null != monPctrl){
		    String sql = 
		    	"update w_tmnl_mon_pctrl pctrl\n" +
		    	"set pctrl.status_code = ?\n" + 
		    	"where pctrl.tmnl_asset_no = ? and pctrl.total_no = ?";
		    this.getJdbcTemplate().update(sql, new Object[]{monPctrl.getStatusCode(),monPctrl.getTmnlAssetNo(),monPctrl.getTotalNo()});
		}
	}
	
	 /**
     * 根据方案Id删除剔除信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException{
    	String sql = 
    		"delete from w_tmnl_mon_pctrl w where w.ctrl_scheme_id = ?";
    	try{
	    	this.getJdbcTemplate().update(sql, new Object[]{schemeId});
		}catch(RuntimeException e){
			this.logger.debug("删除剔除信息出错！");
			throw e;
		}	
    }
    
    
    /**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 * @return Page<EnergyControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<EnergyControlDto> findUserInfoBySchemeId(Long schemeId) throws DataAccessException{
		//StringBuffer sb=new StringBuffer();
		String sql = 
			"select m.org_no,\n" +
			"       o.org_name,\n" + 
			"       m.cons_no,\n" + 
			"       c.cons_name,\n" + 
			"       m.tmnl_asset_no,\n" + 
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" + 
			"       m.total_no,\n" + 
			"       m.power_const,\n" + 
			"       m.float_value,\n" + 
			//"       m.is_send_sms,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.mon_pctrl_flag\n" + 
			"  from o_org              o,\n" + 
			"       c_cons             c,\n" + 
			"       vw_tmnl_run        t,\n" + 
			"       w_tmnl_mon_pctrl   m,\n" + 
			"       w_tmnl_ctrl_status w\n" + 
			" where o.org_no = m.org_no\n" + 
			"   and c.cons_no = m.cons_no\n" + 
			"   and t.tmnl_asset_no = m.tmnl_asset_no\n" + 
			"   and w.tmnl_asset_no(+) = m.tmnl_asset_no\n" + 
			"   and w.total_no(+) = m.total_no\n" + 
			"   and m.total_no is not null\n" + 
			"   and m.ctrl_scheme_id = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{schemeId},new EnergySchemeRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据方案id查询用户信息出错！");
			throw e;
		}
	}
	/**
	 * 自定义查询返回的值对象，用于月电量定值控的Grid显示
	 *//*
	class EnergyCtrlRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EnergyControlDto energyCtrlDto = new EnergyControlDto();
			try {
				energyCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				energyCtrlDto.setConsNo(rs.getString("CONS_NO"));
				energyCtrlDto.setTmnlAssetNo(rs
						.getString("TMNL_ASSET_NO"));
				energyCtrlDto.setConsName(rs.getString("CONS_NAME"));
				energyCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				energyCtrlDto.setTerminalAddr(rs
						.getString("TERMINAL_ADDR"));
				energyCtrlDto.setTotalNo(rs.getShort("TOTAL_NO"));
				//energyCtrlDto.setCtrlFlag(rs.getBoolean("CTRL_FLAG"));
				energyCtrlDto.setPowerConst(rs.getLong("POWER_CONST"));
				if(null!=rs.getString("FLOAT_VALUE")&&!"".equals(rs.getString("FLOAT_VALUE"))&&!"0".equals(rs.getString("FLOAT_VALUE"))){
					energyCtrlDto.setFloatValue(rs.getShort("FLOAT_VALUE"));
					if(0 < rs.getShort("FLOAT_VALUE")){
						energyCtrlDto.setFloatType("0");
					}
					else if(0 > rs.getShort("FLOAT_VALUE")){
						energyCtrlDto.setFloatType("1");
					}
				}
				else{
					energyCtrlDto.setFloatType("0");
				}
				energyCtrlDto.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getShort("TOTAL_NO"));
				if(null!=rs.getString("TMNL_PAUL_POWER")&&rs.getBoolean("TMNL_PAUL_POWER")==true)
					energyCtrlDto.setTmnlPaulPower("投入");
				if(null!=rs.getString("TMNL_PAUL_POWER")&&rs.getBoolean("TMNL_PAUL_POWER")==false)
					energyCtrlDto.setTmnlPaulPower("解除");
//				if(null!=rs.getString("IS_SEND_SMS")&&rs.getBoolean("IS_SEND_SMS")==true)
//					energyCtrlDto.setIsSendSms(true);
//				if(null!=rs.getString("IS_SEND_SMS")&&rs.getBoolean("IS_SEND_SMS")==false)
//					energyCtrlDto.setIsSendSms(false);
				energyCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				return energyCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}*/
	class EnergyCtrlRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EnergyControlDto energyControlDto = new EnergyControlDto();
			try {
				energyControlDto.setOrgNo(rs.getString("ORG_NO"));
				energyControlDto.setOrgName(rs.getString("ORG_NAME"));
				energyControlDto.setConsNo(rs.getString("CONS_NO"));
				energyControlDto.setConsName(rs.getString("CONS_NAME"));
				energyControlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				energyControlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				energyControlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				energyControlDto.setTotalNo(rs.getShort("TOTAL_NO")); 
				energyControlDto.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getString("TOTAL_NO"));
			    if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
			    	energyControlDto.setProtect("0");
			    else
			    	energyControlDto.setProtect(rs.getString("PS_ENSURE_FLAG"));
			    if(null==rs.getString("MON_PCTRL_FLAG")||"".equals(rs.getString("MON_PCTRL_FLAG")))
			    	energyControlDto.setCtrlStatus("-1");
		    	else
		    		energyControlDto.setCtrlStatus(rs.getString("MON_PCTRL_FLAG"));
				return energyControlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class EnergySchemeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EnergyControlDto energyControlDto = new EnergyControlDto();
			try {
				energyControlDto.setOrgNo(rs.getString("ORG_NO"));
				energyControlDto.setOrgName(rs.getString("ORG_NAME"));
				energyControlDto.setConsNo(rs.getString("CONS_NO"));
				energyControlDto.setConsName(rs.getString("CONS_NAME"));
				energyControlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				energyControlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				energyControlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				energyControlDto.setTotalNo(rs.getShort("TOTAL_NO")); 
				energyControlDto.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getString("TOTAL_NO"));
			    if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
			    	energyControlDto.setProtect("0");
			    else
			    	energyControlDto.setProtect(rs.getString("PS_ENSURE_FLAG"));
			    if(null==rs.getString("MON_PCTRL_FLAG")||"".equals(rs.getString("MON_PCTRL_FLAG")))
			    	energyControlDto.setCtrlStatus("-1");
		    	else
		    		energyControlDto.setCtrlStatus(rs.getString("MON_PCTRL_FLAG"));
			    if(null!=rs.getString("POWER_CONST")&&!"".equals(rs.getString("POWER_CONST")))
			    	energyControlDto.setPowerConst(rs.getLong("POWER_CONST"));
				if(null!=rs.getString("FLOAT_VALUE")&&!"".equals(rs.getString("FLOAT_VALUE"))&&!"0".equals(rs.getString("FLOAT_VALUE")))
					energyControlDto.setFloatValue(rs.getShort("FLOAT_VALUE"));
				return energyControlDto;
			}catch (Exception e) {
				return null;
			}
		}
	}
}
