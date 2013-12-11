package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IWTmnlPaulPowerJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WTmnlPaulPower;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 终端保电Jdbc之Dao实现类
 * @author 姜海辉
 */

public class WTmnlPaulPowerJdbcDaoImpl extends JdbcBaseDAOImpl implements IWTmnlPaulPowerJdbcDao {

	/**
	 * 保存或修改终端保电表,用于保存参数
	 * @param wt
	 * @throws DataAccessException
	 */
	public void saveOrUpdateByParam(WTmnlPaulPower wTmnlPaulPower) throws DataAccessException{
	     StringBuffer sb =new  StringBuffer();
	     sb.append(
				"merge into w_tmnl_paul_power dest\n" +
				"using (select  ? as org_no,\n" + 
				"               ? as cons_no,\n" + 
				"               ? as tmnl_asset_no,\n" + 
				"               ? as ctrl_scheme_id,\n" + 
				"               ? as auto_paul_power,\n" + 
				"               ? as security_value,\n" + 
				"               ? as duration,\n" + 
				"               ? as ctrl_flag,\n" + 
				"               ? as is_send_sms,\n" + 
				"               ? as status_code,\n" + 
				"               ? as staff_no,\n" + 
				"               ? as save_time,\n" + 
				"               ? as send_time,\n" + 
				"               ? as success_time,\n" + 
				"               ? as failure_code\n" + 
				"         from dual) src\n" + 
				"on(dest.tmnl_asset_no = src.tmnl_asset_no )\n" + 
				"when matched then\n"+
				"  update\n"+
				"          set dest.org_no = src.org_no,\n" +
			    "              dest.cons_no= src.cons_no,\n" + 
			    "              dest.status_code = src.status_code,\n" + 
			    "              dest.staff_no = src.staff_no,\n" +
			    "              dest.send_time= src.send_time,\n" + 
			    "              dest.success_time= src.success_time,\n" + 
			    "              dest.failure_code = src.failure_code,\n");
	 	 if(null!=wTmnlPaulPower.getAutoPaulPower()||null!=wTmnlPaulPower.getSecurityValue()){
	 		 sb.append( " dest.save_time= src.save_time,\n");	  
	 		 if(null==wTmnlPaulPower.getAutoPaulPower())
	 			 sb.append(" dest.security_value  = src.security_value\n");
	 		 else if(null==wTmnlPaulPower.getSecurityValue())
	 			 sb.append(" dest.auto_paul_power  = src.auto_paul_power\n");
	 		 else{	
	 			 sb.append(" dest.security_value  = src.security_value,\n"+
			 			   " dest.auto_paul_power  = src.auto_paul_power\n");
	 		 }
	 	 }else if(null!=wTmnlPaulPower.getCtrlFlag()){
	 		sb.append(" dest.duration = src.duration,\n"+
	 			      " dest.ctrl_flag = src.ctrl_flag\n"); 
	 	 }
	 	 else
	 		 return;
	 	 sb.append(
		 		"when not matched then\n" + 
				"         insert(tmnl_paul_power_id,\n" + 
				"               org_no,\n" + 
				"               cons_no,\n" + 
				"               tmnl_asset_no,\n" + 
				"               ctrl_scheme_id,\n" + 
				"               auto_paul_power,\n" + 
				"               security_value,\n" + 
				"               duration,\n" + 
				"               ctrl_flag,\n" + 
				"               is_send_sms,\n" + 
				"               status_code,\n" + 
				"               staff_no,\n" + 
				"               save_time,\n" + 
				"               send_time,\n" + 
				"               success_time,\n" + 
				"               failure_code)\n" + 
				"           values(\n" + 
				"               S_W_TMNL_PAUL_POWER.NEXTVAL,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?,\n" + 
				"               ?)");
		this.getJdbcTemplate().update(sb.toString(), objToArray(wTmnlPaulPower));
	}
	
	/**
	 * 保存或修改终端保电表，主要是添加方案适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByScheme(WTmnlPaulPower wt) throws DataAccessException{
		String  sql=
			"merge into w_tmnl_paul_power dest\n" +
			"using (select  ? as org_no,\n" + 
			"               ? as cons_no,\n" + 
			"               ? as tmnl_asset_no,\n" + 
			"               ? as ctrl_scheme_id,\n" + 
			"               ? as auto_paul_power,\n" + 
			"               ? as security_value,\n" + 
			"               ? as duration,\n" + 
			"               ? as ctrl_flag,\n" + 
			"               ? as is_send_sms,\n" + 
			"               ? as status_code,\n" + 
			"               ? as staff_no,\n" + 
			"               ? as save_time,\n" + 
			"               ? as send_time,\n" + 
			"               ? as success_time,\n" + 
			"               ? as failure_code\n" + 
			"         from dual) src\n" + 
			"on(dest.tmnl_asset_no = src.tmnl_asset_no )\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"          set dest.org_no = src.org_no,\n" +
		    "              dest.cons_no= src.cons_no,\n" + 
			"			   dest.ctrl_scheme_id = src.ctrl_scheme_id,\n" + 
			"              dest.auto_paul_power= src.auto_paul_power,\n" + 
			"              dest.security_value =src.security_value,\n" + 
			"              dest.duration = src.duration,\n" + 
			"              dest.ctrl_flag=src.ctrl_flag,\n" + 
			"              dest.status_code = src.status_code,\n" + 
			"              dest.staff_no = src.staff_no,\n" + 
			"              dest.save_time= src.save_time,\n" +
			"              dest.send_time= src.send_time,\n" + 
			"              dest.success_time= src.success_time,\n" + 
			"              dest.failure_code = src.failure_code\n"+
			"when not matched then\n" + 
			"         insert(tmnl_paul_power_id,\n" + 
			"               org_no,\n" + 
			"               cons_no,\n" + 
			"               tmnl_asset_no,\n" + 
			"               ctrl_scheme_id,\n" + 
			"               auto_paul_power,\n" + 
			"               security_value,\n" + 
			"               duration,\n" + 
			"               ctrl_flag,\n" + 
			"               is_send_sms,\n" + 
			"               status_code,\n" + 
			"               staff_no,\n" + 
			"               save_time,\n" + 
			"               send_time,\n" + 
			"               success_time,\n" + 
			"               failure_code)\n" + 
			"           values(\n" + 
			"               S_W_TMNL_PAUL_POWER.NEXTVAL,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?,\n" + 
			"               ?)";
		this.getJdbcTemplate().update(sql, objToArray(wt));
	}
	/**
	 * 参数转换，用于方案添加时使用
	 * @param busi
	 * @return 
	 */
	private Object[] objToArray(WTmnlPaulPower wTmnlPaulPower) {
		Object[] results = new Object[30];
		results[0]=wTmnlPaulPower.getOrgNo();
		results[1]=wTmnlPaulPower.getConsNo();
		results[2]=wTmnlPaulPower.getTmnlAssetNo();
		results[3]=wTmnlPaulPower.getCtrlSchemeId();
		results[4]=wTmnlPaulPower.getAutoPaulPower();
		results[5]=wTmnlPaulPower.getSecurityValue();
		results[6]=wTmnlPaulPower.getDuration();
		results[7]=wTmnlPaulPower.getCtrlFlag();
		results[8]=wTmnlPaulPower.getIsSendSms();
		results[9]=wTmnlPaulPower.getStatusCode();
		results[10]=wTmnlPaulPower.getStaffNo();
		results[11]=wTmnlPaulPower.getSaveTime();             
		results[12]=wTmnlPaulPower.getSendTime();
		results[13]=wTmnlPaulPower.getSuccessTime();
		results[14]=wTmnlPaulPower.getFailureCode();
		results[15]=wTmnlPaulPower.getOrgNo();
		results[16]=wTmnlPaulPower.getConsNo();
		results[17]=wTmnlPaulPower.getTmnlAssetNo();
		results[18]=wTmnlPaulPower.getCtrlSchemeId();
		results[19]=wTmnlPaulPower.getAutoPaulPower();
		results[20]=wTmnlPaulPower.getSecurityValue();
		results[21]=wTmnlPaulPower.getDuration();
		results[22]=wTmnlPaulPower.getCtrlFlag();
		results[23]=wTmnlPaulPower.getIsSendSms();
		results[24]=wTmnlPaulPower.getStatusCode();
		results[25]=wTmnlPaulPower.getStaffNo();
		results[26]=wTmnlPaulPower.getSaveTime();             
		results[27]=wTmnlPaulPower.getSendTime();
		results[28]=wTmnlPaulPower.getSuccessTime();
		results[29]=wTmnlPaulPower.getFailureCode();
		return results;
	}
	

	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		String sql =
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO= ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据供电单位编号查询终端相关信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.Cons_Name,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n");
		if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
			orgNo="%"+orgNo+"%";
			sb.append("  and C.Area_No like ?");
		}else if("06".equals(orgType))
			sb.append("  and C.Org_No= ?");
		else
			 return null;
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[]{orgNo,pSysUser.getStaffNo()},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据线路Id查询终端相关信息
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" +
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Line_Id= ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {lineId,pSysUser.getStaffNo()},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据普通群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,R_USER_GROUP_DETAIL RU\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = RU.TMNL_ASSET_NO\n" + 
			"  and RU.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据普通群组编号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据控制群组编号查询终端相关信息
	 * @param groupNo  控制群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" +
			"       R.PROTOCOL_CODE,\n" + 
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,T_TMNL_GROUP_DETAIL  TT\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = TT.TMNL_ASSET_NO\n" + 
			"  and TT.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据控制群组编号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站查询终端相关信息
	 * @param subsId 变电站标识
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findTmnlBySubsId(String subsId, PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		//subsId="%"+subsId+"%";
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" +
			"       R.PROTOCOL_CODE,\n" +
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Subs_Id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {subsId,pSysUser.getStaffNo()},new TmnlProtectRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询关联的终端出错！");
			throw e;
		}	
	}
	
	/**
     * 根据方案ID查询终端信息
     * @param schemeId  方案ID
     * @return
     * @throws DBAccessException
     */
	@SuppressWarnings("unchecked")
	public List<WTmnlPaulPower> findProtectTmnlBySchemeId(Long schemeId) {
		String sql = 
			"select W.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       W.CONS_NO,\n" + 
			"       C.Cons_Name,\n" + 
			"       R.TERMINAL_ADDR,\n" +
			"       R.PROTOCOL_CODE,\n" + 
			"       W.TMNL_ASSET_NO,\n" + 
			"       W.Auto_Paul_Power,\n" + 
			"       W.Security_Value,\n" + 
			"       W.Duration,\n" + 
			"       R.PS_ENSURE_FLAG\n"+
			"  FROM W_Tmnl_Paul_Power W,C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where W.ORG_NO=O.ORG_NO\n" + 
			"  and   W.CONS_NO=C.Cons_No\n" + 
			"  and   W.Tmnl_Asset_No=R.Tmnl_Asset_No\n" + 
			"  and   W.CTRL_SCHEME_ID=?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{schemeId},new TmnlProtectSchemeRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据方案ID查询关联的终端出错！");
			throw e;
		}
	}
	
	  /**
     * 根据方案Id删除保电信息
     * @param schemeId
     * @throws DBAccessException
     */
    public void deleteBySchemeId(Long schemeId) throws DBAccessException{
    	String sql = 
    		"delete from w_tmnl_paul_power w where w.ctrl_scheme_id = ?";
    	try{
	    	this.getJdbcTemplate().update(sql, new Object[]{schemeId});
		}catch(RuntimeException e){
			this.logger.debug("删除保电信息出错！");
			throw e;
		}
    }
	
	class TmnlProtectRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WTmnlPaulPower wTmnlPaulPower=new WTmnlPaulPower();
			try {
				wTmnlPaulPower.setOrgNo(rs.getString("ORG_NO"));
				wTmnlPaulPower.setOrgName(rs.getString("ORG_NAME"));
				wTmnlPaulPower.setConsNo(rs.getString("CONS_NO"));
				wTmnlPaulPower.setConsName(rs.getString("CONS_NAME"));
				wTmnlPaulPower.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				wTmnlPaulPower.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				wTmnlPaulPower.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
					wTmnlPaulPower.setProtectStatus("0");
				else
					wTmnlPaulPower.setProtectStatus(rs.getString("PS_ENSURE_FLAG"));
				return wTmnlPaulPower;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class TmnlProtectSchemeRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WTmnlPaulPower wTmnlPaulPower=new WTmnlPaulPower();
			try {
				wTmnlPaulPower.setOrgNo(rs.getString("ORG_NO"));
				wTmnlPaulPower.setOrgName(rs.getString("ORG_NAME"));
				wTmnlPaulPower.setConsNo(rs.getString("CONS_NO"));
				wTmnlPaulPower.setConsName(rs.getString("CONS_NAME"));
				wTmnlPaulPower.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				wTmnlPaulPower.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				wTmnlPaulPower.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				if(null!=rs.getString("Auto_Paul_Power")&&!"".equals(rs.getString("Auto_Paul_Power")))
					wTmnlPaulPower.setAutoPaulPower(rs.getInt("Auto_Paul_Power"));
				if(null!=rs.getString("Security_Value")&&!"".equals(rs.getString("Security_Value")))
					wTmnlPaulPower.setSecurityValue(rs.getFloat("Security_Value"));
				if(null!=rs.getString("Duration")&&!"".equals(rs.getString("Duration")))
					wTmnlPaulPower.setDuration(rs.getShort("Duration"));
				if(null==rs.getString("PS_ENSURE_FLAG")||"".equals(rs.getString("PS_ENSURE_FLAG")))
					wTmnlPaulPower.setProtectStatus("0");
				else
					wTmnlPaulPower.setProtectStatus(rs.getString("PS_ENSURE_FLAG"));
				return wTmnlPaulPower;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
