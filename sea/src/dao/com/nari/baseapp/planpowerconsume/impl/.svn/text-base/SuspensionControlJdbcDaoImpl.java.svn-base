package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.ISuspensionControlJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.SuspensionControlDto;
import com.nari.orderlypower.WTmnlBusiness;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 营业报停控Jdbc之Dao实现类
 * @author 姜炜超
 */
public class SuspensionControlJdbcDaoImpl extends JdbcBaseDAOImpl implements ISuspensionControlJdbcDao{
	protected Logger logger = Logger.getLogger(this.getClass());//定义日志

	/**
	 * 保存或修改营业报停控明细表，主要是添加方案适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlBusiness busi) throws DataAccessException{
		//StringBuffer sb = new StringBuffer();
		String sql = 
			"merge into w_tmnl_business dest\n" +
			"using (select ? as org_no,\n" + 
			"              ? as cons_no,\n" + 
			"              ? as tmnl_asset_no,\n" + 
			"              ? as total_no,\n" + 
			"              ? as ctrl_scheme_id,\n" + 
			"              ? as stop_start,\n" + 
			"              ? as stop_end,\n" + 
			"              ? as stop_const,\n" + 
			"              ? as ctrl_flag,\n" + 
			"              ? as is_send_sms,\n" + 
			"              ? as status_code,\n" + 
			"              ? as staff_no,\n" + 
			"              ? as save_time,\n" + 
			"              ? as send_time,\n" + 
			"              ? as success_time,\n" + 
			"              ? as failure_time\n" + 
			"         from dual) src\n" + 
			"on (dest.tmnl_asset_no = src.tmnl_asset_no and dest.total_no = src.total_no)\n" + 
			"when matched then\n" + 
			"  update\n" +
			"     set dest.org_no  = src.org_no,\n" +
			"         dest.cons_no = src.cons_no,\n" + 
			"         dest.ctrl_scheme_id = src.ctrl_scheme_id,\n" + 
			"         dest.stop_start     = src.stop_start,\n" + 
			"         dest.stop_end       = src.stop_end,\n" + 
			"         dest.stop_const     = src.stop_const,\n" + 
			//"         dest.is_send_sms    = src.is_send_sms,\n" + 
			"         dest.status_code    = src.status_code,\n" + 
			"         dest.staff_no       = src.staff_no,\n" + 
			"         dest.save_time      = src.save_time\n" + 
			"         dest.send_time      = src.send_time,\n" + 
			"         dest.success_time     = src.success_time,\n" + 
			"         dest.failure_time     = src.failure_time\n"+
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_business_id,\n" + 
			"     org_no,\n" + 
			"     cons_no,\n" + 
			"     tmnl_asset_no,\n" + 
			"     total_no,\n" + 
			"     ctrl_scheme_id,\n" + 
			"     stop_start,\n" + 
			"     stop_end,\n" + 
			"     stop_const,\n" + 
			"     ctrl_flag,\n" + 
			"     is_send_sms,\n" +
			"     status_code,\n" +
			"     staff_no,\n" + 
			"     save_time,\n" +
			"     send_time,\n" +
			"     success_time,\n" +
			"     failure_time)\n" + 
			"  values\n" + 
			"    (s_w_tmnl_business.nextval,\n" + 
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
		this.getJdbcTemplate().update(sql, objToArray(busi));
	}
	
	
	/**
	 * 保存或修改营业报停控明细表，主要是针对参数下发
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlBusiness busi){
		StringBuffer sb=new StringBuffer();
        sb.append(
			"merge into w_tmnl_business dest\n" +
			"using (select ? as org_no,\n" + 
			"              ? as cons_no,\n" + 
			"              ? as tmnl_asset_no,\n" + 
			"              ? as total_no,\n" + 
			"              ? as ctrl_scheme_id,\n" + 
			"              ? as stop_start,\n" + 
			"              ? as stop_end,\n" + 
			"              ? as stop_const,\n" + 
			"              ? as ctrl_flag,\n" + 
			"              ? as is_send_sms,\n" + 
			"              ? as status_code,\n" + 
			"              ? as staff_no,\n" + 
			"              ? as save_time,\n" + 
			"              ? as send_time,\n" + 
			"              ? as success_time,\n" + 
			"              ? as failure_time\n" + 
			"         from dual) src\n" + 
			"on (dest.tmnl_asset_no = src.tmnl_asset_no and dest.total_no = src.total_no)\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"     set dest.org_no  = src.org_no,\n" +
			"         dest.cons_no = src.cons_no,\n" + 
			//"         dest.is_send_sms    = src.is_send_sms,\n" + 
			"         dest.status_code    = src.status_code,\n" + 
			"         dest.staff_no       = src.staff_no,\n" + 
			"         dest.send_time      = src.send_time,\n" + 
			"         dest.success_time     = src.success_time,\n" + 
			"         dest.failure_time     = src.failure_time,\n"); 
			if(null!=busi.getStopConst()){
				sb.append("         dest.save_time      = src.save_time,\n" + 
						  "         dest.stop_start     = src.stop_start,\n" + 
						  "         dest.stop_end       = src.stop_end,\n" + 
						  "         dest.stop_const     = src.stop_const\n");
			}
			else if(null!= busi.getCtrlFlag()){
				sb.append("         dest.ctrl_flag    = src.ctrl_flag\n");	
			}
			else 
				return;
			sb.append(
				"when not matched then\n" + 
				"  insert\n" + 
				"    (tmnl_business_id,\n" + 
				"     org_no,\n" + 
				"     cons_no,\n" + 
				"     tmnl_asset_no,\n" + 
				"     total_no,\n" + 
				"     ctrl_scheme_id,\n" + 
				"     stop_start,\n" + 
				"     stop_end,\n" + 
				"     stop_const,\n" + 
				"     ctrl_flag,\n" + 
				"     is_send_sms,\n" +
				"     status_code,\n" +
				"     staff_no,\n" + 
				"     save_time,\n" +
				"     send_time,\n" +
				"     success_time,\n" +
				"     failure_time)\n" + 
				"  values\n" + 
				"    (s_w_tmnl_business.nextval,\n" + 
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
		this.getJdbcTemplate().update(sb.toString(), objToArray(busi));
	}
	
	/**
	 * 参数转换，用于方案添加时使用
	 * @param busi
	 * @return 
	 */
	private Object[] objToArray(WTmnlBusiness busi) {
		Object[] results = new Object[32];
		results[0] = busi.getOrgNo();
		results[1] = busi.getConsNo();
		results[2] = busi.getTmnlAssetNo();
		results[3] = busi.getTotalNo();
		results[4] = busi.getCtrlSchemeId();
		results[5] = busi.getStopStart();
		results[6] = busi.getStopEnd();
		results[7] = busi.getStopConst();
		results[8] = busi.getCtrlFlag();
		results[9] = busi.getIsSendSms();
		results[10] = busi.getStatusCode();
		results[11] = busi.getStaffNo();
		results[12] = busi.getSaveTime();
		results[13] = busi.getSendTime();
		results[14] = busi.getSuccessTime();
		results[15] = busi.getFailureCode();
		results[16] = busi.getOrgNo();
		results[17] = busi.getConsNo();
		results[18] = busi.getTmnlAssetNo();
		results[19] = busi.getTotalNo();
		results[20] = busi.getCtrlSchemeId();
		results[21] = busi.getStopStart();
		results[22] = busi.getStopEnd();
		results[23] = busi.getStopConst();
		results[24] = busi.getCtrlFlag();
		results[25] = busi.getIsSendSms();
		results[26] = busi.getStatusCode();
		results[27] = busi.getStaffNo();
		results[28] = busi.getSaveTime();
		results[29] = busi.getSendTime();
		results[30] = busi.getSuccessTime();
		results[31] = busi.getFailureCode();
		return results;
	}
	
	/**
	 * 更新营业报停控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param busi
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateBusiness(WTmnlBusiness busi){
		int ctrlFlag = 0;
		if(null != busi){
			if(busi.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		        "update w_tmnl_business busi\n" +
		        "set busi.ctrl_flag = ?,\n" + 
		        "    busi.send_time = ?\n" + 
		        "where busi.tmnl_asset_no = ? and busi.total_no = ?";
            this.getJdbcTemplate().update(sql, new Object[]{ctrlFlag,busi.getSendTime(),busi.getTmnlAssetNo(),busi.getTotalNo()});
		}
	}
	
	/**
	 * 更新营业报停控方案，用于操作后对表的状态字段statusCode的修改（下发、投入、解除）
	 * @param busiList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateBusiStatus(WTmnlBusiness busi){
		if(null != busi){
		    String sql = 
		    	"update w_tmnl_business busi\n" +
		    	"set busi.status_code = ?\n" + 
		    	"where busi.tmnl_asset_no = ? and busi.total_no = ?";
		    this.getJdbcTemplate().update(sql, new Object[]{busi.getStatusCode(),busi.getTmnlAssetNo(),busi.getTotalNo()});
		}
	}
	
	/**
	 * 根据资产编号查询用户信息
	 * @param tmnlAssetNo
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByTmnlAssetNo(String tmnlAssetNo)throws DBAccessException{
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
			"       w.business_flag\n" + 
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
			"   and t.tmnl_asset_no = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据用户号查询用户信息
	 * @param consNo
	 * @return 
	 *//*
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByConsNo(String consNo) {
		//StringBuffer sb=new StringBuffer();
		String sql = 
			"  select c.cons_name,\n" +
			"      t.cons_no,\n" + 
			"      o.org_name,\n" + 
			"      t.terminal_addr,\n" + 
			"      t.protocol_code,\n" +
			"      total.total_no,\n" + 
			"      busi.stop_start,\n" + 
			"      busi.stop_end,\n" + 
			"      busi.stop_const,\n" + 
			//"      busi.ctrl_flag,\n" + 
			"      t.tmnl_asset_no,\n" + 
			"      paul.ctrl_flag as tmnl_paul_power,\n" + 
			"      o.org_no\n" + 
			" from w_tmnl_business busi, c_cons c, o_org o, vw_tmnl_run t, e_data_total total, w_tmnl_paul_power paul\n" + 
			" where t.cons_no = c.cons_no\n" + 
			"  and c.org_no = o.org_no\n" + 
			"  and t.tmnl_asset_no = total.tmnl_asset_no\n" + 
			"  and total.total_no is not null\n" + 
			"  and total.tmnl_asset_no = busi.tmnl_asset_no(+)\n" + 
			"  and total.total_no = busi.total_no(+)\n" + 
			"  and total.tmnl_asset_no = paul.tmnl_asset_no(+)\n" + 
			"  and c.cons_no = ?";
		System.out.println("=================\n"+sql);
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{consNo},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据用户编号查询用户信息出错！");
			throw e;
		}
	}*/

	

	/**
	 * 根据供电单位号查询用户信息
	 * @param orgNo
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByOrgNo(String orgNo,String orgType,PSysUser pSysUser) {
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
			"       w.business_flag\n" + 
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
		}
		else if("06".equals(orgType))
			sb.append("  and c.Org_No = ?");
		else
			 return null;
		sb.append(SysPrivilige.addOrgPri(pSysUser,"c"));
		try{
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{orgNo,pSysUser.getStaffNo()},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位号查询用户信息出错！");
			throw e;
		}
	}
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId
	 * @return Page<SuspensionControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByLineId(String lineId, PSysUser pSysUser){
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
			"       w.business_flag\n" + 
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
		sb.append(SysPrivilige.addOrgPri(pSysUser,"c"));
		try{
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{lineId,pSysUser.getStaffNo()},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据组号查询用户信息（普通群组）
	 * @param groupNo
	 * @return Page<SuspensionControlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByCommonGroupNo(String groupNo){
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
			"       w.business_flag\n" + 
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
			return this.getJdbcTemplate().query(sql, new Object[]{groupNo},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据组号查询用户信息出错！");
			throw e;
		}
	}
	/**
	 * 根据组号查询用户信息（控制群组）
	 * @param groupNo
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoByContrlGroupNo(String groupNo){
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
			"       w.business_flag\n" + 
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
			return this.getJdbcTemplate().query(sql, new Object[]{groupNo},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据组号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoBySubsId(String subsId,PSysUser pSysUser){
		//subsId = "%"+subsId+"%";
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
			"       w.business_flag\n" + 
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
			return this.getJdbcTemplate().query(sb.toString(), new Object[]{subsId,pSysUser.getStaffNo()},new SuspensionCtrlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据方案id查询用户信息
	 * @param schemeNo
	 */
	@SuppressWarnings("unchecked")
	public List<SuspensionControlDto> findUserInfoBySchemeId(Long schemeNo) {
		//StringBuffer sb=new StringBuffer();
		String sql = 
			"select busi.org_no,\n" +
			"       o.org_name,\n" +
			"       busi.cons_no,\n" + 
			"		c.cons_name,\n" +
			"       busi.tmnl_asset_no,\n" +
			"       t.terminal_addr,\n" + 
			"       t.protocol_code,\n" +
			"       busi.total_no,\n" + 
			"       t.ps_ensure_flag,\n" + 
			"       w.business_flag,\n" +  
			"       busi.stop_const,\n" +  
			"       busi.stop_start,\n" +  
			"       busi.stop_end\n" +  
			"  from w_tmnl_business busi, c_cons c, o_org o, vw_tmnl_run t, w_tmnl_ctrl_status w\n" + 
			" where c.cons_no = busi.cons_no\n" + 
			"   and o.org_no = busi.org_no \n" + 
			"   and t.tmnl_asset_no = busi.tmnl_asset_no\n"+
			"   and w.tmnl_asset_no(+) = busi.tmnl_asset_no\n" + 
			"   and w.total_no(+) = busi.total_no\n" +
			"   and busi.total_no is not null\n" + 
			"   and busi.ctrl_scheme_id = ?";
		try{
			return this.getJdbcTemplate().query(sql, new Object[]{schemeNo},new SuspensionSchemeRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据方案id查询用户信息出错！");
			throw e;
		}
	}
	
	 /**
     * 根据方案Id删除剔除信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException{
    	String sql = 
    		"delete from w_tmnl_business w where w.ctrl_scheme_id = ?";
    	try{
	    	this.getJdbcTemplate().update(sql, new Object[]{schemeId});
		}catch(RuntimeException e){
			this.logger.debug("删除剔除信息出错！");
			throw e;
		}	
    }
	
	class SuspensionCtrlRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SuspensionControlDto suspensionCtrlDto = new SuspensionControlDto();
			try {
				suspensionCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				suspensionCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				suspensionCtrlDto.setConsNo(rs.getString("CONS_NO"));
				suspensionCtrlDto.setConsName(rs.getString("CONS_NAME"));
				suspensionCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				suspensionCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				suspensionCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				suspensionCtrlDto.setTotalNo(rs.getShort("TOTAL_NO")); 
				suspensionCtrlDto.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getString("TOTAL_NO"));
			    if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
			    	suspensionCtrlDto.setProtect("0");
			    else
			    	suspensionCtrlDto.setProtect(rs.getString("PS_ENSURE_FLAG"));
			    if(null==rs.getString("BUSINESS_FLAG")||"".equals(rs.getString("BUSINESS_FLAG")))
			    	suspensionCtrlDto.setCtrlStatus("-1");
		    	else
		    		suspensionCtrlDto.setCtrlStatus(rs.getString("BUSINESS_FLAG"));
				return suspensionCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class SuspensionSchemeRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SuspensionControlDto suspensionCtrlDto = new SuspensionControlDto();
			try {
				suspensionCtrlDto.setOrgNo(rs.getString("ORG_NO"));
				suspensionCtrlDto.setOrgName(rs.getString("ORG_NAME"));
				suspensionCtrlDto.setConsNo(rs.getString("CONS_NO"));
				suspensionCtrlDto.setConsName(rs.getString("CONS_NAME"));
				suspensionCtrlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				suspensionCtrlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				suspensionCtrlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				suspensionCtrlDto.setTotalNo(rs.getShort("TOTAL_NO")); 
				suspensionCtrlDto.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getString("TOTAL_NO"));
			    if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
			    	suspensionCtrlDto.setProtect("0");
			    else
			    	suspensionCtrlDto.setProtect(rs.getString("PS_ENSURE_FLAG"));
			    if(null==rs.getString("BUSINESS_FLAG")||"".equals(rs.getString("BUSINESS_FLAG")))
			    	suspensionCtrlDto.setCtrlStatus("-1");
		    	else
		    		suspensionCtrlDto.setCtrlStatus(rs.getString("BUSINESS_FLAG"));
			    if(null!=rs.getString("STOP_CONST")&&!"".equals(rs.getString("STOP_CONST")))
			    	suspensionCtrlDto.setStopConst(rs.getFloat("STOP_CONST"));
		    	suspensionCtrlDto.setStopStart(rs.getString("STOP_START"));
		    	suspensionCtrlDto.setStopEnd(rs.getString("STOP_END"));	
				return suspensionCtrlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
