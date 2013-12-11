package com.nari.runman.feildman.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.TerminalInstallDao;
import com.nari.support.Page;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.ITmnlTask;
import com.nari.terminalparam.MeterChgInfoBean;
import com.nari.terminalparam.MeterCommBean;
import com.nari.terminalparam.MeterMaintainInfoBean;
import com.nari.terminalparam.TerminalChgInfoBean;
import com.nari.terminalparam.TerminalDebugInfoBean;
import com.nari.terminalparam.TerminalInstallDetailBean;
import com.nari.util.DateUtil;
import com.nari.util.StatusCodeUtils;

/**
 * 终端安装Dao实现类
 * @author 姜炜超
 */
public class TerminalInstallDaoImpl extends JdbcBaseDAOImpl implements TerminalInstallDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(TerminalInstallDaoImpl.class);
	
	/**
	 * 根据供电单位和查询时间，统计终端安装信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlInstallInfo(String orgNo, String startDate, String endDate, 
			String interType){
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"select task.debug_status_code as status_code, count(*) as num\n" +
			"  from i_tmnl_task task\n" + 
			" where task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1)\n";
		orgNo = orgNo + "%";
		
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		sql += " group by task.debug_status_code";
		
		logger.debug(sql);
		List<ITmnlTask> list = super.getJdbcTemplate().query(sql, obj.toArray(), new TmnlInstallInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<ITmnlTask>();
	    }
	    return list; 
	}
	
	/**
	 * 根据供电单位和查询时间，统计终端安装时终端下属用户或电表总数，如果是集抄，查询的是用户信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @param debugStatusCode
	 * @return Long
	 */
	public Long findTmnlUserOrMeterCount(String orgNo, String startDate, String endDate, 
			String interType, String debugStatusCode){
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			" select count(*) from c_meter meter where meter.tmnl_asset_no in ( \n"+
			" select distinct task.terminal_id from i_tmnl_task task \n" +
			" where task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1)\n";
		
		orgNo = orgNo + "%";
		
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		
		//目前终端类别编码，01，02，03，04属于专变，05，06属于集抄用户，08属于关口
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		sql += " and task.debug_status_code = ? )\n";
		obj.add(debugStatusCode);
		
		//如果是集抄用户，那么统计的是分表用户，不要考虑总表用户
		if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and meter.tmnl_asset_no != meter.fmr_asset_no and meter.ref_meter_flag != 1\n";
		}
		
		orgNo = orgNo + "%";
		logger.debug(sql);
		return this.getJdbcTemplate().queryForLong(sql, obj.toArray()); 
	}
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括新装、拆除，更换，检修)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo(String orgNo, String startDate, String endDate, 
			String interType){
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"select task.TMNL_TASK_TYPE AS STATUS_CODE, count(*) as num\n" +
			"  from i_tmnl_task task\n" + 
			" where task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1)\n" + 
			"   AND TASK.TMNL_TASK_TYPE IS NOT NULL\n" +
			"   AND TASK.TMNL_TASK_TYPE <> ?\n";

		orgNo = orgNo + "%";
		
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		obj.add(StatusCodeUtils.TMNL_TASK_TYPE5);
		
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		sql += " group by task.TMNL_TASK_TYPE";
		
		logger.debug(sql);
		List<ITmnlTask> list = super.getJdbcTemplate().query(sql, obj.toArray(), new TmnlInstallInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<ITmnlTask>();
	    }
	    return list; 
	}
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括终端换表)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo1(String orgNo, String startDate, String endDate, 
			String interType){
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"select task.METER_FLAG AS STATUS_CODE, count(*) as num\n" +
			"  from i_tmnl_task task\n" + 
			" where task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1)\n" + 
			"   AND TASK.TMNL_TASK_TYPE IS NOT NULL\n" +
			"   AND TASK.TMNL_TASK_TYPE = ?\n";

		orgNo = orgNo + "%";
		
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		obj.add(StatusCodeUtils.TMNL_TASK_TYPE5);
		
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		sql += " group by task.METER_FLAG";
		
		logger.debug(sql);
		List<ITmnlTask> list = super.getJdbcTemplate().query(sql, obj.toArray(), new TmnlInstallInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<ITmnlTask>();
	    }
	    return list; 
	}
	
	/**
	 * 根据供电单位和查询时间，统计终端拆换信息(包括用户暂停,用户销户)
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param interType
	 * @return List<ITmnlTask>
	 */
	@SuppressWarnings("unchecked")
	public List<ITmnlTask> findTmnlRmvAndChgInfo2(String orgNo, String startDate, String endDate, 
			String interType){
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"select task.CONS_CHG_TYPE AS STATUS_CODE, count(*) as num\n" +
			"  from i_tmnl_task task\n" + 
			" where task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1)\n" + 
			"   AND TASK.TMNL_TASK_TYPE IS NOT NULL\n" +
			"   AND TASK.TMNL_TASK_TYPE = ?\n";

		orgNo = orgNo + "%";
		
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		obj.add(StatusCodeUtils.TMNL_TASK_TYPE5);
		
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		sql += " group by task.CONS_CHG_TYPE";
		
		logger.debug(sql);
		List<ITmnlTask> list = super.getJdbcTemplate().query(sql, obj.toArray(), new TmnlInstallInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<ITmnlTask>();
	    }
	    return list; 
	}
	
	/**
	 * 根据操作员信息查询该操作员可操作下属单位
	 * @param staffNo
	 * @param orgType
	 * @return List<OOrg>
	 */
	public List<OOrg> findSubOrgInfo(String staffNo, String orgType){
		String sql = 
			"select p.org_no, p.org_type, org.org_name\n" +
			"  from p_access_org p, o_org org\n" + 
			" where p.org_no = org.org_no\n" + 
			"   and p.staff_no = ?\n" + 
			"   and p.org_type = ?";
		logger.debug(sql);
		List<OOrg> list = super.getJdbcTemplate().query(sql, new Object[] {staffNo, orgType}, new TmnlInstallSubOrgInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<OOrg>();
	    }
	    return list;
	}
	
	/**
	 * 根据供电单位查询该单位相关信息
	 * @param orgNo
	 * @return OOrg
	 */
	public OOrg findOrgInfo(String orgNo){
		String sql = " select org.org_type, org.org_name, org.p_org_no from o_org org where org.org_no = ?";
		logger.debug(sql);
		List<OOrg> list= super.getJdbcTemplate().query(sql, new Object[] {orgNo}, new TmnlInstallOrgInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return new OOrg();
	    }
	    return (OOrg)list.get(0);
	}
	
	/**
	 * 根据供电单位，时间等信息查询该单位下所有用户的终端调试信息
	 * @param orgNo
	 * @param startDate
	 * @param endDate
	 * @param consNo
	 * @param debugStatus
	 * @param appNo
	 * @param interType
	 * @return List<TerminalInstallDetailBean>
	 */
	public List<TerminalInstallDetailBean>  findConsTmnlDebugInfo(String orgNo, String startDate,
			String endDate, String consNo, String debugStatus,String appNo, String interType){
		List<TerminalInstallDetailBean> list = null;
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"select task.cons_no,\n" +
			"       task.app_no,\n" + 
			"       cons.cons_name,cons.cons_type,\n" + 
			"       tmnl.terminal_addr,\n" + 
			"       tmnl.terminal_id,\n" + 
			"       tmnl.protocol_code,\n" + 
			"       tmnl.send_up_mode,\n" + 
			"       tmnl.tmnl_asset_no,\n" + 
			"       tt.TMNL_TYPE as terminal_type,\n" + 
			"       tt1.tmnl_task_type_name as tmnl_task_type,\n" + 
			"       nvl(tt2.cons_chg_type_name,'未知') as cons_chg_type,\n" + 
			"       task.meter_flag,\n" + 
			"       decode(task.meter_flag,\n" + 
			"              '1',\n" + 
			"              '电表变更',\n" + 
			"              '0',\n" + 
			"              '电表未变更',\n" + 
			"              '未知') as meter_flag_name,\n" + 
			"       temp.asset_no,'报文查询，数据召测，任务编制' as oper_link\n" + 
			"  from (select t.app_no, count(meter.asset_no) as asset_no\n" + 
			"          from i_tmnl_task t, r_coll_obj obj, c_meter meter\n" + 
			"         where t.cp_no = obj.cp_no\n" + 
			"           and obj.meter_id = meter.meter_id\n" + 
			"         group by t.app_no) temp,\n" + 
			"       (select decode(tak.tmnl_task_type,'03',tak.new_terminal_id, tak.terminal_id) as terminal_id,\n" +
			"           tak.cons_no, tak.terminal_type_code, tak.cons_chg_type, tak.app_no,\n" +
			"           tak.org_no, tak.debug_time, tak.debug_status_code, tak.tmnl_task_type, tak.meter_flag" +
			"          from i_tmnl_task tak) task,\n" + 
			"       c_cons cons,\n" + 
			"       r_tmnl_run tmnl,\n" + 
			"       vw_tmnl_type_code tt,\n" + 
			"       vw_tmnl_task_type tt1,\n" + 
			"       vw_cons_chg_type tt2\n" + 
			" where task.cons_no = cons.cons_no(+)\n" + 
			"   and task.terminal_id = tmnl.terminal_id(+)\n" + 
			"   and task.terminal_type_code = tt.TMNL_TYPE_CODE(+)\n" + 
			"   and task.tmnl_task_type = tt1.tmnl_task_type(+)\n" + 
			"   and task.cons_chg_type = tt2.cons_chg_type(+)\n" + 
			"   and task.app_no = temp.app_no(+)\n" + 
			"   and task.org_no like ?\n" + 
			"   and task.debug_time >= to_Date(?, 'yyyy-mm-dd')\n" + 
			"   and task.debug_time < (to_date(?, 'yyyy-mm-dd') + 1) \n";

		orgNo = orgNo +"%";
		obj.add(orgNo);
		obj.add(startDate);
		obj.add(endDate);
		
		//添加终端类别接口判断
		if(StatusCodeUtils.INTER_TYPE_CODE1.equals(interType)){
			sql += " and task.terminal_type_code in (?,?,?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE1);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE2);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE3);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE4);
		}else if(StatusCodeUtils.INTER_TYPE_CODE2.equals(interType)){
			sql += " and task.terminal_type_code in (?,?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE5);
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE6);
		}else {
			sql += " and task.terminal_type_code in (?)\n";
			obj.add(StatusCodeUtils.TMNL_TYPE_CODE8);
		}
		
		if(null != consNo && 0 < consNo.length()){
			consNo = "%"+consNo+"%";
			sql = sql + "and task.cons_no like ?\n";
			obj.add(consNo);
		}
		if(null != appNo && 0 < appNo.length()){
			appNo = "%"+appNo+"%";
			sql = sql + "and task.app_no like ?\n";
			obj.add(appNo);
		}
		if(null != debugStatus && 0 < debugStatus.length()){
			sql = sql + "and task.debug_status_code = ?\n";
			obj.add(debugStatus);
		}
		sql = sql + "order by task.app_no desc";
		logger.debug(sql);
		try{
			list = super.getJdbcTemplate().query(sql, obj.toArray(), new TmnlInstallDetRowMapper());
		}catch(RuntimeException e){
			logger.debug("根据条件查询该单位下所有用户的终端调试信息出错！");
			throw e;
		}
			
		if(null == list){
			list = new ArrayList<TerminalInstallDetailBean>();
		}
		return list;
	}
	
	/**
	 * 根据申请单号查询终端调试信息
	 * @param appNo
	 * @param start
	 * @param limit
	 * @return Page<TerminalDebugInfoBean>
	 */
	public Page<TerminalDebugInfoBean>  findTmnlDebugInfo(String appNo, Integer consType, long start, int limit){
		Page<TerminalDebugInfoBean> psc = null;
		String sql = null;
		if(2 != consType){
			sql = 
				"select dg.app_no,\n" +
				"       decode(dg.debug_progress,'-1','初始状态','0','启动测试','10','下发终端参数开始','11',\n" +
				"           '下发终端参数成功','12','下发终端参数失败','20','抄表测试开始','21','抄表测试成功',\n" + 
				"           '22','抄表测试失败','30','下发测量点参数开始','31','下发测量点参数成功','32','下发测量点参数失败',\n" + 
				"           '40','下发任务开始','41','下发任务成功','42','下发任务失败','100','测试成功',null) as debug_progress,"+
				"       dg.fail_cause,\n" + 
				"       dg.debug_time,\n" + 
				"       tmnl.terminal_addr,\n" + 
				"       meter.asset_no\n" + 
				"  from i_tmnl_debug dg, vw_tmnl_run tmnl, c_meter meter\n" + 
				" where dg.terminal_id = tmnl.terminal_id(+)\n" + 
				"   and dg.meter_id = meter.meter_id(+)\n" + 
				"   and dg.app_no = ?" +
				"   order by dg.debug_time desc, dg.debug_progress desc";
			logger.debug(sql);
			try{
				psc = this.pagingFind(sql, start, limit, new TerminalDebugInfoRowMapper(),new Object[]{appNo});
			}catch(RuntimeException e){
				logger.debug("根据条件查询该单位某用户的终端调试信息出错！");
				throw e;
			}
		}else{
			sql =
				"SELECT TMNL.TERMINAL_ADDR,\n" +
				"       DECODE(PRO.FLOW_NODE,\n" + 
				"              '01',\n" + 
				"              '设备建档',\n" + 
				"              '02',\n" + 
				"              '电表注册',\n" + 
				"              '03',\n" + 
				"              '核对电表注册参数',\n" + 
				"              '04',\n" + 
				"              '设置测量点参数',\n" + 
				"              '05',\n" + 
				"              '设置任务',\n" + 
				"              '') AS FLOW_NODE,\n" + 
				"       DECODE(PRO.FLOW_STATUS, '0', '成功', '1', '失败', '成功') AS FLOW_STATUS,\n" + 
				"       PRO.FLOW_NODE AS FLOW_NO,  \n" +
				"       PRO.MP_SN_LIST  \n" + 
				"  FROM I_FLOW_PROCESS PRO, VW_TMNL_RUN TMNL\n" + 
				" WHERE PRO.TERMINAL_ID = TMNL.TERMINAL_ID(+)\n" + 
				"   AND PRO.APP_NO = ?\n" + 
				" ORDER BY PRO.FLOW_NODE ASC";
			logger.debug(sql);
			try{
				psc = this.pagingFind(sql, start, limit, new TerminalTgDebugInfoRowMapper(),new Object[]{appNo});
			}catch(RuntimeException e){
				logger.debug("根据条件查询该单位某用户的终端调试信息出错！");
				throw e;
			}
		}
		
		return psc;
	}
	
	/**
	 * 查询规约信息
	 * @return List<VwProtocolCode>
	 */
	public List<VwProtocolCode>  findProtocalCodeInfo(){
		String sql = "SELECT p.PROTOCOL_CODE, p.PROTOCOL_NAME FROM VW_PROTOCOL_CODE p";
		logger.debug(sql);
		List<VwProtocolCode> list = super.getJdbcTemplate().query(sql, new Object[] {}, new ProtocalCodeMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<VwProtocolCode>();
	    }
	    return list; 
	}
	
	/**
	 * 更新终端信息
	 * @param tmnlId 
	 * @param protocolCode 
	 * @param sendUpMode 
	 */
	public void saveTmnlInfo(String tmnlId, String protocolCode, String sendUpMode){
		String sql = 
			"update r_tmnl_run tmnl set tmnl.protocol_code = ?, tmnl.send_up_mode=? where tmnl.terminal_id=?";
		logger.debug(sql);
		this.getJdbcTemplate().update(sql,
				new Object[] { protocolCode, sendUpMode,tmnlId});
	}
	
	/**
	 * 加载终端变更信息
	 * @param appNo
	 * @return List<TerminalChgInfoBean>
	 */
	public List<TerminalChgInfoBean>  findTmnlChgInfo(String appNo){
		String sql = 
			"select s.tmnl_task_type_name,s.tmnl_task_type,\n" +
			"       (select tmnl.terminal_addr\n" + 
			"          from vw_tmnl_run tmnl\n" + 
			"         where tmnl.terminal_id = task.terminal_id) as terminal_addr,\n" + 
			"       (select tmnl.terminal_addr\n" + 
			"          from vw_tmnl_run tmnl\n" + 
			"         where tmnl.terminal_id(+) = task.new_terminal_id) as new_terminal_addr\n" + 
			"  from i_tmnl_task task, vw_tmnl_task_type s\n" + 
			" where task.tmnl_task_type = s.tmnl_task_type(+)\n" + 
			"   and task.app_no = ? ";
		logger.debug(sql);
		List<TerminalChgInfoBean> list = super.getJdbcTemplate().query(sql, new Object[] {appNo}, new TerminalChgInfoRowMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<TerminalChgInfoBean>();
	    }
	    return list; 
	}
	
	/**
	 * 加载电能表变更信息
	 * @param appNo
	 * @return List<MeterChgInfoBean>
	 */
	public List<MeterChgInfoBean>  findMeterChgInfo(String appNo){
		String sql = 
			"select tmnl.last_mr_num,\n" +
			"       tmnl.new_mr_num,\n" + 
			"       (select met.asset_no\n" + 
			"          from c_meter met\n" + 
			"         where tmnl.old_meter_id = met.meter_id) as old_asset_no,\n" + 
			"       (select met.asset_no\n" + 
			"          from c_meter met\n" + 
			"         where tmnl.new_meter_id = met.meter_id) as new_asset_no\n" + 
			"  from i_tmnl_task_sr tmnl\n" + 
			" where tmnl.app_no = ? ";
		logger.debug(sql);
		List<MeterChgInfoBean> list = super.getJdbcTemplate().query(sql, new Object[] {appNo}, new MeterChgInfoRowMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<MeterChgInfoBean>();
	    }
	    return list; 
	}
	
	/**
	 * 加载电能表维护信息
	 * @param appNo
	 * @param succFlag
	 * @return Page<MeterMaintainInfoBean>
	 */
	public Page<MeterMaintainInfoBean>  findMeterMaintainInfo(String appNo, int succFlag, long start, int limit){
		String sql = 
			"select task.cons_no,cons.cons_name,\n" +
			"       mp.mp_name,mp.mp_no,\n" + 
			"       meter.asset_no,\n" + 
			"       meter.comm_no,decode(meter.comm_no,null,'-请选择-',nvl((select p.name from p_code p where p.code_type='commProtocol' and meter.comm_no = p.value),'-请选择-')) as comm_num,\n" + 
			"       meter.comm_addr1,\n" + 
			"       tmnl.cis_asset_no,\n" + 
			"       emp.mp_sn,\n" + 
			"       decode(rd.mr_num,'-9999','',rd.mr_num) as mr_num,\n" + 
			"       rd.mr_time,\n" + 
			"       decode(rd.mr_flag, '1', '成功', '0', '失败', null) as mr_flag\n" + 
			"  from i_tmnl_task  task,\n" + 
			"       c_mp         mp,\n" + 
			"       r_coll_obj   obj,\n" + 
			"       c_meter      meter,\n" + 
			"       vw_tmnl_run   tmnl,\n" + 
			"       e_data_mp    emp,\n" + 
			"       i_meter_read rd, c_cons cons\n" + 
			" where task.cp_no = obj.cp_no\n" + 
			"   and obj.meter_id = meter.meter_id\n" + 
			"   and task.terminal_id = tmnl.terminal_id(+)\n" + 
			"   and meter.mp_id = mp.mp_id(+)\n" + 
			"   and meter.meter_id = emp.meter_id\n" + 
			"   and emp.is_valid = 1\n" + 
			"   and obj.meter_id = rd.meter_id(+) and task.cons_no = cons.cons_no(+)\n" + 
			"   and task.app_no = ?\n" ;
			
		if(1 == succFlag){//1表示成功
			sql += " and rd.mr_flag = 1 order by cis_asset_no, asset_no";
			logger.debug(sql);
		}else if(0 == succFlag){//0表示失败
			sql += " and rd.mr_flag = 0 order by cis_asset_no, asset_no";
			logger.debug(sql);
		}else{//表示全部
			logger.debug(sql);
		}
		return super.pagingFind(sql, start, limit, new MeterMaintainInfoRowMapper(),new Object[] {appNo});
	}
	
	/**
	 * 加载电能表通讯协议
	 * @return List<MeterCommBean>
	 */
	public List<MeterCommBean> findMeterCommInfo(){
		String sql = "  select p.value,p.name from p_code p where code_type='commProtocol'";
		logger.debug(sql);
		List<MeterCommBean> list = super.getJdbcTemplate().query(sql, new Object[] {}, new MeterCommInfoRowMapper());
	    if(list == null || 0 == list.size()){
	    	return new ArrayList<MeterCommBean>();
	    }
	    return list; 
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询终端安装信息
	 */
	class TmnlInstallInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlTask dto = new ITmnlTask();
			try {
				dto.setStatusCode(rs.getString("STATUS_CODE"));
				dto.setNum(rs.getDouble("NUM"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询操作员下属供电单位信息
	 */
	class TmnlInstallSubOrgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OOrg dto = new OOrg();
			try {
				dto.setOrgType(rs.getString("ORG_TYPE"));
				dto.setOrgNo(rs.getString("ORG_NO"));
				dto.setOrgName(rs.getString("ORG_NAME"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询供电单位信息
	 */
	class TmnlInstallOrgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OOrg dto = new OOrg();
			try {
				dto.setOrgType(rs.getString("ORG_TYPE"));
				dto.setPOrgNo(rs.getString("P_ORG_NO"));
				dto.setOrgName(rs.getString("ORG_NAME"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询某单位下终端安装详细信息
	 */
	class TmnlInstallDetRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalInstallDetailBean dto = new TerminalInstallDetailBean();
			try {
				dto.setAppNo(rs.getString("APP_NO"));
				dto.setConsNo(rs.getString("CONS_NO"));
				dto.setConsType(rs.getInt("CONS_TYPE"));
				dto.setConsName(rs.getString("CONS_NAME"));
				dto.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				dto.setTerminalId(rs.getString("TERMINAL_ID"));
				dto.setTmnlType(rs.getString("TERMINAL_TYPE"));
				dto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				dto.setConsChgType(rs.getString("CONS_CHG_TYPE"));
				dto.setMeterFlag(rs.getString("METER_FLAG"));
				dto.setTmnlTaskType(rs.getString("TMNL_TASK_TYPE"));
				dto.setMeterNo(rs.getString("ASSET_NO"));
				dto.setMeterFlagName(rs.getString("METER_FLAG_NAME"));
				dto.setProtocolCode(null != rs.getString("PROTOCOL_CODE") ? rs.getString("PROTOCOL_CODE") : "");
				dto.setSendUpMode(null != rs.getString("SEND_UP_MODE") ? rs.getString("SEND_UP_MODE") : "");
				dto.setOperLink(rs.getString("OPER_LINK"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询某单号下终端调试信息
	 */
	class TerminalDebugInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalDebugInfoBean dto = new TerminalDebugInfoBean();
			try {
				dto.setAppNo(rs.getString("APP_NO"));
				dto.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				dto.setMeterNo(rs.getString("ASSET_NO"));
				dto.setFailCause(rs.getString("FAIL_CAUSE"));
				dto.setDebugTime(DateUtil.timeStampToStr(rs.getTimestamp("DEBUG_TIME")));
				dto.setDebugProcess(rs.getString("DEBUG_PROGRESS"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询某单号下终端调试信息(台区用户)
	 */
	class TerminalTgDebugInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalDebugInfoBean dto = new TerminalDebugInfoBean();
			try {
				dto.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				dto.setFlowNo(rs.getString("FLOW_NO"));
				dto.setFlowNode(rs.getString("FLOW_NODE"));
				dto.setFlowStatus(rs.getString("FLOW_STATUS"));
				dto.setMpSnList(rs.getString("MP_SN_LIST"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询规约信息
	 */
	class ProtocalCodeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwProtocolCode dto = new VwProtocolCode();
			try {
				dto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				dto.setProtocolName(rs.getString("PROTOCOL_NAME"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询终端变更信息
	 */
	class TerminalChgInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalChgInfoBean dto = new TerminalChgInfoBean();
			try {
				dto.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				dto.setTmnlTaskTypeName(rs.getString("TMNL_TASK_TYPE_NAME"));
				dto.setTmnlTaskType(rs.getString("TMNL_TASK_TYPE"));
				dto.setNewTmnlAddr(rs.getString("NEW_TERMINAL_ADDR"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询电能表变更信息
	 */
	class MeterChgInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MeterChgInfoBean dto = new MeterChgInfoBean();
			try {
				dto.setOldAssetNo(rs.getString("OLD_ASSET_NO"));
				dto.setNewAssetNo(rs.getString("NEW_ASSET_NO"));
				dto.setNewMRNum((null != rs.getString("NEW_MR_NUM"))? rs.getDouble("NEW_MR_NUM") : null);
				dto.setOldMRNum((null != rs.getString("LAST_MR_NUM"))? rs.getDouble("LAST_MR_NUM") : null);
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询电能表维护信息
	 */
	class MeterMaintainInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MeterMaintainInfoBean dto = new MeterMaintainInfoBean();
			try {
				dto.setTmnlAssetNo(rs.getString("cis_asset_no"));
				dto.setMeterNo(rs.getString("asset_no"));
				dto.setCommNo(rs.getString("comm_no"));
				dto.setCommAddr(rs.getString("comm_addr1"));
				dto.setMpSn(rs.getInt("mp_sn"));
				dto.setMpNo(rs.getString("mp_no"));
				dto.setMpName(rs.getString("mp_name"));
				dto.setMrFlag(rs.getString("mr_flag"));
				dto.setMrNum(rs.getString("mr_num"));
				dto.setMrTime(timeStampToStr(rs.getTimestamp("mr_time")));
				dto.setConsNo(rs.getString("cons_no"));
				dto.setConsName(rs.getString("cons_name"));
				dto.setCommNum(rs.getString("comm_num"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	

	/**
	 * 自定义查询返回的值对象，用于查询电能表通讯信息
	 */
	class MeterCommInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MeterCommBean dto = new MeterCommBean();
			try {
				dto.setValue(rs.getString("value"));
				dto.setName(rs.getString("name"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}	
	
    /**
	 * timeStamp转化为String
	 * @param date
	 * @return String 
	 */
    private String timeStampToStr(Timestamp time){
    	return DateUtil.timeStampToStr(time);
    }

	@Override
	public int delTmnlDebugInfo(String tmnlAddr, String flowNode) throws Exception {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		sql.append("DELETE I_FLOW_PROCESS FR WHERE FR.FLOW_NODE = ?  AND FR.TERMINAL_ID = (SELECT R.TERMINAL_ID FROM R_TMNL_RUN R WHERE R.TERMINAL_ADDR = ?)  \n");
		parms.add(flowNode);
		parms.add(tmnlAddr);
		return super.deletePara(sql.toString(), parms.toArray());
	}   
	public void resetState(final PSysUser user,final String appNos []){
		try {
			String sql=
				"UPDATE I_TMNL_TASK T SET T.DEBUG_STATUS_CODE=-9 WHERE T.DEBUG_STATUS_CODE=1 AND T.APP_NO=?\n" +
				"AND (T.ORG_NO LIKE ? OR '00000'= (SELECT O.P_ORG_NO FROM O_ORG O WHERE O.ORG_NO=? AND ROWNUM=1))";
				getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
					
					public void setValues(PreparedStatement ps, int index) throws SQLException {
						int i=1;
						ps.setString(i++, appNos[index]);
						ps.setString(i++, user.getOrgNo()+"%");
						ps.setString(i++, user.getOrgNo());
					}
					
					@Override
					public int getBatchSize() {
						return appNos.length;
					}
				});
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
}
