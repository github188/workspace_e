package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IWTmnlRejectJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WTmnlReject;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 终端剔除JdbcDao接口实现类
 * @author 姜海辉
 *
 */
public class WTmnlRejectJdbcDaoIml extends JdbcBaseDAOImpl implements IWTmnlRejectJdbcDao {
	
	/**
	 * 保存或修改终端剔除表，主要是保存参数适用
	 * @param busi
	 * @return 
	 */
	public void saveOrUpdateByParam(WTmnlReject wTmnlReject) throws DataAccessException{
		String  sql=
			"merge into w_tmnl_reject dest\n" +
			"using (select  ? as org_no,\n" + 
			"               ? as cons_no,\n" + 
			"               ? as tmnl_asset_no,\n" + 
			"               ? as total_no,\n" + 
			"               ? as ctrl_scheme_id,\n" + 
			"               ? as is_reject,\n" +
			"               ? as is_send_sms,\n" + 
			"               ? as status_code,\n" + 
			"               ? as staff_no,\n" + 
			"               ? as save_time,\n" + 
			"               ? as send_time,\n" + 
			"               ? as success_time,\n" + 
			"               ? as failure_code\n" + 
			"         from dual) src\n" + 
			"on(dest.tmnl_asset_no = src.tmnl_asset_no)\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"          set dest.org_no = src.org_no,\n" +
		    "              dest.cons_no= src.cons_no,\n" + 
			"              dest.is_reject = src.is_reject,\n" + 
			"              dest.status_code  = src.status_code,\n" + 
			"              dest.staff_no = src.staff_no,\n" + 
			"              dest.save_time= src.save_time,\n" + 
			"              dest.send_time= src.send_time,\n" + 
			"              dest.success_time= src.success_time,\n" + 
			"              dest.failure_code = src.failure_code\n" + 
			"when not matched then\n" + 
			"         insert(tmnl_reject_id,\n" + 
			"               org_no,\n" + 
			"               cons_no,\n" + 
			"               tmnl_asset_no,\n" + 
			"               total_no,\n" + 
			"               ctrl_scheme_id,\n" + 
			"               is_reject,\n" + 
			"               is_send_sms,\n" + 
			"               status_code,\n" + 
			"               staff_no,\n" + 
			"               save_time,\n" + 
			"               send_time,\n" + 
			"               success_time,\n" + 
			"               failure_code)\n" + 
			"           values(\n" + 
			"               S_W_TMNL_REJECT.NEXTVAL,\n" + 
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
		this.getJdbcTemplate().update(sql, objToArray(wTmnlReject));
	}
	
	/**
	 * 保存或修改终端剔除表，主要是添加方案适用
	 * @param busi
	 * @return 
	 */
	@Override
	public void saveOrUpdateByScheme(WTmnlReject wt) throws DataAccessException {
		String  sql=
			"merge into w_tmnl_reject dest\n" +
			"using (select  ? as org_no,\n" + 
			"               ? as cons_no,\n" + 
			"               ? as tmnl_asset_no,\n" + 
			"               ? as total_no,\n" + 
			"               ? as ctrl_scheme_id,\n" + 
			"               ? as is_reject,\n" +
			"               ? as is_send_sms,\n" + 
			"               ? as status_code,\n" + 
			"               ? as staff_no,\n" + 
			"               ? as save_time,\n" + 
			"               ? as send_time,\n" + 
			"               ? as success_time,\n" + 
			"               ? as failure_code\n" + 
			"         from dual) src\n" + 
			"on(dest.org_no = src.org_no and dest.cons_no = src.cons_no and dest.tmnl_asset_no = src.tmnl_asset_no)\n" + 
			"when matched then\n" + 
			"  update\n" + 
			"          set dest.org_no = src.org_no,\n" +
		    "              dest.cons_no= src.cons_no,\n" + 
		    "              dest.ctrl_scheme_id = src.ctrl_scheme_id,\n" + 
			"              dest.is_reject = src.is_reject,\n" + 
			"              dest.status_code  = src.status_code,\n" + 
			"              dest.staff_no = src.staff_no,\n" + 
			"              dest.save_time= src.save_time,\n" + 
			"              dest.send_time= src.send_time,\n" + 
			"              dest.success_time= src.success_time,\n" + 
			"              dest.failure_code = src.failure_code\n" + 
			"when not matched then\n" + 
			"         insert(tmnl_reject_id,\n" + 
			"               org_no,\n" + 
			"               cons_no,\n" + 
			"               tmnl_asset_no,\n" + 
			"               total_no,\n" + 
			"               ctrl_scheme_id,\n" + 
			"               is_reject,\n" + 
			"               is_send_sms,\n" + 
			"               status_code,\n" + 
			"               staff_no,\n" + 
			"               save_time,\n" + 
			"               send_time,\n" + 
			"               success_time,\n" + 
			"               failure_code)\n" + 
			"           values(\n" + 
			"               S_W_TMNL_REJECT.NEXTVAL,\n" + 
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
	private Object[] objToArray(WTmnlReject wTmnlReject) {
		Object[] results = new Object[26];
		results[0]=wTmnlReject.getOrgNo();
		results[1]=wTmnlReject.getConsNo();
		results[2]=wTmnlReject.getTmnlAssetNo();
		results[3]=wTmnlReject.getTotalNo();
		results[4]=wTmnlReject.getCtrlSchemeId();
		results[5]=wTmnlReject.getIsReject();
		results[6]=wTmnlReject.getIsSendSms();
		results[7]=wTmnlReject.getStatusCode();
		results[8]=wTmnlReject.getStaffNo();
		results[9]=wTmnlReject.getSaveTime();             
		results[10]=wTmnlReject.getSendTime();
		results[11]=wTmnlReject.getSuccessTime();
		results[12]=wTmnlReject.getFailureCode();
		results[13]=wTmnlReject.getOrgNo();
		results[14]=wTmnlReject.getConsNo();
		results[15]=wTmnlReject.getTmnlAssetNo();
		results[16]=wTmnlReject.getTotalNo();
		results[17]=wTmnlReject.getCtrlSchemeId();
		results[18]=wTmnlReject.getIsReject();
		results[19]=wTmnlReject.getIsSendSms();
		results[20]=wTmnlReject.getStatusCode();
		results[21]=wTmnlReject.getStaffNo();
		results[22]=wTmnlReject.getSaveTime();             
		results[23]=wTmnlReject.getSendTime();
		results[24]=wTmnlReject.getSuccessTime();
		results[25]=wTmnlReject.getFailureCode();
		return results;
	}
	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlReject> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		String sql =
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO= ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new TmnlEliminateRowMapper());
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
	public List<WTmnlReject> findTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.Cons_Name,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n");
		if("03".equals(orgType)||"04".equals(orgType)){
			orgNo="%"+orgNo+"%";
			sb.append("  and C.Area_No like ?");
		}else if("06".equals(orgType))
			sb.append("  and C.Org_No= ?");
		else
			 return null;
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[]{orgNo,pSysUser.getStaffNo()},new TmnlEliminateRowMapper());
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
	public List<WTmnlReject> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" +
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Line_Id= ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {lineId,pSysUser.getStaffNo()},new TmnlEliminateRowMapper());
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
	public List<WTmnlReject> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE,\n" + 
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,R_USER_GROUP_DETAIL RU\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = RU.TMNL_ASSET_NO\n" + 
			"  and RU.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TmnlEliminateRowMapper());
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
	public List<WTmnlReject> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" +
			"       R.PROTOCOL_CODE,\n" + 
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,T_TMNL_GROUP_DETAIL  TT\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = TT.TMNL_ASSET_NO\n" + 
			"  and TT.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TmnlEliminateRowMapper());
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
	public List<WTmnlReject> findTmnlBySubsId(String subsId, PSysUser pSysUser) throws DBAccessException{
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
			"       R.ELIMINATE_FLAG\n"+
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Subs_Id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {subsId,pSysUser.getStaffNo()},new TmnlEliminateRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询关联的终端出错！");
			throw e;
		}	
	}
	
	
	/**
	 * 根据方案ID查询终端剔除信息
	 * @param schemeId 方案ID
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlReject> findTmnlBySchemeId(Long schemeId) throws DBAccessException{
		String sql = 
			"SELECT   W.Org_No,\n" +
			"         O.ORG_NAME,\n" + 
			"         W.CONS_NO,\n" + 
			"         C.CONS_NAME,\n" + 
			"         R.TERMINAL_ADDR,\n" + 
			"         R.PROTOCOL_CODE,\n" + 
			"         W.TMNL_ASSET_NO,\n" +
			"      	  R.ELIMINATE_FLAG,\n"+
			"         W.Is_Reject\n" + 
			"  FROM  W_Tmnl_Reject w,C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  WHERE O.ORG_NO = W.Org_No\n" + 
			"  AND   C.CONS_NO= W.CONS_NO\n" + 
			"  AND   R.Tmnl_Asset_No=W.TMNL_ASSET_NO\n" + 
			"  AND   w.ctrl_scheme_id=?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{schemeId},new TmnlEliminateSchemeRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据方案ID查询关联的终端出错！");
			throw e;
		}
	}
	/**
	 * 更新终端剔除状态
	 * @param tTmnlAssetNo
	 * @param type
	 * @throws DBAccessException
	 */
	public void updateEliminate(String tmnlAssetNo,Integer flag)throws DBAccessException{
		String sql ="update r_tmnl_run r set r.eliminate_flag = ? where r.tmnl_asset_no = ?";
		try{
			getJdbcTemplate().update(sql, new Object[]{flag,tmnlAssetNo});
		}catch(RuntimeException e){
			this.logger.debug("更新终端剔除信息出错！");
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
    		"delete from w_tmnl_reject w where w.ctrl_scheme_id = ?";
    	try{
	    	this.getJdbcTemplate().update(sql, new Object[]{schemeId});
		}catch(RuntimeException e){
			this.logger.debug("删除剔除信息出错！");
			throw e;
		}	
    }
    
	class TmnlEliminateRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WTmnlReject wTmnlReject=new WTmnlReject();
			try {
				wTmnlReject.setOrgNo(rs.getString("ORG_NO"));
				wTmnlReject.setOrgName(rs.getString("ORG_NAME"));
				wTmnlReject.setConsNo(rs.getString("CONS_NO"));
				wTmnlReject.setConsName(rs.getString("CONS_NAME"));
				wTmnlReject.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				wTmnlReject.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				wTmnlReject.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				if(null==rs.getString("ELIMINATE_FLAG")||"".equals(rs.getString("ELIMINATE_FLAG")))
					wTmnlReject.setEliminateStatus("0");
				else
					wTmnlReject.setEliminateStatus(rs.getString("ELIMINATE_FLAG"));
				return wTmnlReject;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	class TmnlEliminateSchemeRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WTmnlReject wTmnlReject=new WTmnlReject();
			try {
				wTmnlReject.setOrgNo(rs.getString("ORG_NO"));
				wTmnlReject.setOrgName(rs.getString("ORG_NAME"));
				wTmnlReject.setConsNo(rs.getString("CONS_NO"));
				wTmnlReject.setConsName(rs.getString("CONS_NAME"));
				wTmnlReject.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				wTmnlReject.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				wTmnlReject.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				if(null==rs.getString("ELIMINATE_FLAG")||"".equals(rs.getString("ELIMINATE_FLAG")))
					wTmnlReject.setEliminateStatus("0");
				else
					wTmnlReject.setEliminateStatus(rs.getString("ELIMINATE_FLAG"));
				if(null==rs.getString("Is_Reject")||"".equals(rs.getString("Is_Reject")))
					wTmnlReject.setIsReject(false);
				else
					wTmnlReject.setIsReject(rs.getBoolean("Is_Reject"));
				return wTmnlReject;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
