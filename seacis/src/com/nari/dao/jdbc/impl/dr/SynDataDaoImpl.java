package com.nari.dao.jdbc.impl.dr;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.nari.dao.BaseDao;
import com.nari.dao.jdbc.SynDataForMarketingDao;
import com.nari.exception.ExceptionHandle;
import com.nari.util.JdbcToHashMap;
import com.nari.util.NumberUtil;
import com.nari.util.SQLutil;
import com.nari.util.StringUtil;

/**
 * 数据同步接口的实现
 */
@SuppressWarnings("unchecked")
public class SynDataDaoImpl extends BaseDao implements SynDataForMarketingDao {

	private Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 根据表名同步中间库中的数据到采集库（定时任务）
	 * 
	 * @param tableName
	 * @return true or false
	 */
	public boolean synData(String tableName) {
		tableName = tableName.toLowerCase();
		boolean flag = false;
		String primaryKeyName = "";
		
		if("p_code".equals(tableName)){
			//新疆p_code中的数据的CODE_ID是自动生成的，所以同步前需要先清空
			this.delDcode();
			logger.info("清空d_code成功");
		}
		
		// 4.1.2.1供电单位信息/O_ORG
		if ("o_org".equals(tableName)) {
			primaryKeyName = "ORG_NO";
		// 4.1.1.2标准代码/P_CODE
		} else if ("p_code".equals(tableName)) {
			primaryKeyName = "code_id";
		// 4.1.1.1代码分类/P_CODE_SORT
		} else if ("p_code_sort".equals(tableName)) {
			primaryKeyName = "code_sort_id";
		// 4.1.2.2部门信息/O_DEPT
		} else if ("o_dept".equals(tableName)) {
			primaryKeyName = "dept_no";
		// 4.1.4.14变电站信息/g_subs
		} else if ("g_subs".equals(tableName)) {
			primaryKeyName = "subs_id";
		// 4.1.4.17	线路关系信息/G_LINE_RELA
		} else if ("g_line_rela".equals(tableName)) {
			primaryKeyName = "line_rela_id";
		// 4.1.4.11	台区线路关系信息/G_LINE_TG_RELA
		} else if ("g_line_tg_rela".equals(tableName)) {
			primaryKeyName = "line_tq_id";
		// 4.1.4.16	线路信息/G_LINE
		} else if ("g_line".equals(tableName)) {
			primaryKeyName = "line_id";
		// 4.1.4.12	台区信息/G_TG
		} else if ("g_tg".equals(tableName)) {
			primaryKeyName = "tg_id";
		}

		flag = synDataQuery(tableName,tableName, "1", "1" ,primaryKeyName,null,null);
		
		if("p_code".equals(tableName)){
			//新疆由于所有的设备厂家在一起，需要置终端厂家为有效
			this.updateTmnlFacCode();
			logger.info("终端厂家设置成功.");
		}
		
		logger.info("定时任务"+ tableName + "的档案数据同步完成!");
		return flag;
	}
	
	/**
	 * 清空p_code
	 */
	private void delDcode(){
		StringBuffer sql = new StringBuffer();
		sql.append(" truncate table p_code");
		logger.info(sql.toString());
		getJdbcDAO().update(sql.toString()) ;
	}
	
	/**
	 * 终端厂家为有效
	 */
	private void updateTmnlFacCode(){
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE p_code a ");
		sql.append(" SET content1 = '1' ");
		sql.append(" WHERE code_type = 'collTmnlFactory' ");
		sql.append(" AND EXISTS (SELECT * FROM r_tmnl_run WHERE factory_code = a.value) ");
		logger.info(sql.toString());
		getJdbcDAO().update(sql.toString()) ;
	}
	
	/**
	 * 显示同步档案日志
	 * 
	 * @param tableName 表名
	 * @param flag 成功标志
	 */
	public void synLog(String tableName, boolean flag){
		if (flag) {
			logger.info(tableName + "档案同步【 成功 】!");
		} else {
			logger.info(" ========== " + tableName + "档案同步【 失败 】! ========== ");
		}
	}
	
	/**
	 * 终端自动装接的时候根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * 
	 * @param cons_no 
	 * @param cons_id
	 * @param cp_no
	 * @param tmnlId
	 * @param tgId
	 * 
	 * @return true or false
	 */
	public boolean synDataByService(String cons_no, String cons_id,String cp_no, String tmnlId, String tgId, String appNo) {
		String querySql = null;
		List list = null;
		boolean flag = false;
		
		/*
		 * 东软营销根据电表是否变更判断营销发起调试的源头
		 * 业扩申请编号不为空，从业扩发起，从方案表中取档案
		 * 业扩申请编号为空，从采集菜单发起，从归档后的档案表中取数据
		 * 业扩申请编号等于1，为定时任务发起，从归档后的档案表中取数据
		 */
		String wkstAppNo = "";
		querySql = "SELECT T.WKST_APP_NO FROM I_TMNL_TASK T WHERE T.APP_NO =?"; 
		list = this.getJdbcDAO().queryForList(querySql, new Object[]{appNo});
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			wkstAppNo = StringUtil.removeNull(mapp.get("WKST_APP_NO"));
		}
		if(!"".equals(wkstAppNo) && !"1".equals(wkstAppNo)){
			flag = this.synDataByScheme(cons_no, cons_id, cp_no, tmnlId, tgId);
			return flag;
		}
	
		flag = synDataQuery("c_elec_addr","c_elec_addr","cons_id",cons_id,"cons_id",null,null);
		this.synLog("用电地址/c_elec_addr", flag);

		querySql = "SELECT C.TG_ID FROM C_MP C WHERE C.CONS_ID =?"; 
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cons_id});
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			tgId = StringUtil.removeNull(mapp.get("TG_ID"));
		}
		
		if (tgId != null && !"".equals(tgId)) {
			// 同步----4.1.4.12台区/g_tg
			flag = synDataQuery("g_tg","g_tg","tg_id",tgId,"tg_id",null,null);
			this.synLog("台区/g_tg", flag);
			
			String line_id = "";
			querySql = "SELECT R.LINE_ID FROM G_LINE_TG_RELA R WHERE R.TG_ID=?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tgId});
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map<?, ?> m = (Map<?, ?>) iterator.next();
					line_id = StringUtil.removeNull(m.get("LINE_ID"));
					// 同步 ----4.1.4.16 线路/g_line
					flag = synDataQuery("g_line","g_line","line_id", line_id, "line_id",null,null);
					this.synLog("线路/g_line", flag);
					
					querySql = "SELECT R.SUBS_ID FROM g_subs_line_rela R WHERE R.LINE_ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{line_id});
					for (Iterator itr = list.iterator(); itr.hasNext();) {
						Map<?, ?> map = (Map<?, ?>) itr.next();
						String subs_id = StringUtil.removeNull(map.get("SUBS_ID"));
						// 同步 ----变电站/g_subs
						flag = synDataQuery("g_subs", "g_subs", "subs_id", subs_id, "subs_id",null,null);
						this.synLog("变电站/g_subs", flag);
					}
					
					// 同步----变电站线路关系信息/g_subs_line_rela
					flag = synDataQuery("g_subs_line_rela","g_subs_line_rela","line_id",line_id,"rela_id",null,null);
					this.synLog("变电站线路关系信息/g_subs_line_rela", flag);
			}
			

			// 同步----4.1.4.11台区线路关系信息/g_line_tg_rela
			flag = synDataQuery("g_line_tg_rela","g_line_tg_rela","tg_id",tgId,"line_tq_id",null,null);
			this.synLog("台区线路关系信息/g_line_tg_rela", flag);

			// 同步----4.1.4.13变压器信息/g_tran
			if (tgId != null && !"".equals(tgId)) {
				querySql = "SELECT R.EQUIP_ID FROM G_TRAN R WHERE R.TG_ID =?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tgId });
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					String equipId = StringUtil.removeNull(mapp.get("EQUIP_ID"));
					flag = synDataQuery("g_tran","g_tran","equip_id",equipId,"equip_id",null,null);
					this.synLog("变压器信息/g_tran", flag);
				}
			}
		}

		// 同步----4.1.3.10受电点/c_sp
		querySql = "SELECT R.SP_ID FROM C_PS R WHERE R.CONS_ID =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cons_id });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String spId = StringUtil.removeNull(mapp.get("SP_ID"));
			flag = synDataQuery("c_sp","c_sp","sp_id",spId,"sp_id",null,null);
			this.synLog("受电点/c_sp", flag);
		}

//		// 同步----4.1.4.19供电电源信息/c_ps
//		flag = synDataQuery("c_ps","c_ps","cons_id",cons_id,"ps_id",null,null);
//		this.synLog("供电电源信息/c_ps", flag);
		
		// 根据cons_id从中间库c_cons取出cons_id对应的数据cust_id的值
		String cust_id = this.getStringById("c_cons", "cons_id", "CONS_ID",cons_id);

		// 同步----4.1.3.1客户信息/c_cust
		flag = synDataQuery("c_cust","c_cust","cust_id",cust_id,"cust_id",null,null);
		this.synLog("客户信息/c_cust", flag);

		// 同步----4.1.3.2用户基本信息/c_cons
		flag = synDataQuery("c_cons","c_cons", "cons_id", cons_id,"cons_id",tgId+","+tmnlId,"0");
		this.synLog("用户基本信息/c_cons", flag);
		
		// 同步----4.1.3.3用户联系信息/c_contact
		flag = synDataQuery("c_contact","c_contact","cust_id",cust_id,"contact_id",null,null);
		this.synLog("用户联系信息/c_contact", flag);

		// 同步----4.1.4.3终端资产信息/d_lc_equip
		flag = synDataQuery("d_lc_equip","d_lc_equip", "ID", tmnlId,"ID", null,null);
		this.synLog("终端资产信息/d_lc_equip", flag);

		// 同步----4.1.4.4运行终端信息/r_tmnl_run
		flag = synDataQuery("r_tmnl_run","r_tmnl_run", "TERMINAL_ID",tmnlId,"TERMINAL_ID",null,null);
		this.synLog("运行终端信息/r_tmnl_run", flag);
		
		// 同步----4.1.4.5采集对象信息/r_coll_obj
		//新疆特殊情况 未归档前COLL_OBJ_ID一直在变+++++需要先删除后更新+++++++++++++++++++++++++++++++++++++++++++++
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			this.delById("r_coll_obj", "meter_id", meter_id);
		}

		// 一终端多表
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));

			// 同步----4.1.4.6电能表信息/d_meter
			flag = synDataQuery("d_meter","d_meter", "meter_id", meter_id,"meter_id",null,null);
			this.synLog("电能表信息/d_meter", flag);

			// 同步----4.1.4.7运行电能表信息/c_meter   flag：0为专变
			flag = synDataQuery("c_meter", "c_meter", "meter_id", meter_id,"meter_id", cons_no, "0");
			this.synLog("运行电能表信息/c_meter", flag);

			// 同步----4.1.4.1计量点信息/c_mp
			querySql = "SELECT A.MP_ID FROM C_METER_MP_RELA A WHERE A.METER_ID=?";
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { meter_id });
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				String mp_id = StringUtil.removeNull(map.get("MP_ID"));
				
				// 计量点更新
				flag = synDataQuery("c_mp","c_mp", "mp_id", mp_id,"mp_id",null,null);
				this.synLog("计量点信息/c_mp", flag);
				
				String it_id = "";
				querySql = "SELECT R.IT_ID FROM c_mp_it_rela R WHERE R.MP_ID=?"; 
				List ls = this.getJdbcYXDAO().queryForList(querySql, new Object[]{mp_id});
				for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
						Map<?, ?> m = (Map<?, ?>) iterator.next();
						it_id = StringUtil.removeNull(m.get("IT_ID"));
						// 同步-----4.1.4.8运行互感器信息/c_it_run
						flag = synDataQuery("c_it_run","c_it_run", "it_id",it_id,"it_id",null,null);
						this.synLog("运行互感器信息/c_it_run", flag);
				}
				
				if (list != null && list.size() > 0) {
					// 同步----4.1.4.9互感器计量点关系信息/c_mp_it_rela
					flag = synDataQuery( "c_mp_it_rela","c_mp_it_rela",  "mp_id", mp_id,"it_mp_id", null,null);
					this.synLog("互感器计量点关系信息/c_mp_it_rela", flag);
					
					// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
					flag = synDataQuery( "c_meter_mp_rela","c_meter_mp_rela",  "mp_id", mp_id,"meter_mp_id", null,null);
					this.synLog("电能表计量点关系信息/c_meter_mp_rela", flag);
				}
			}
		}

		// 同步----4.1.4.2采集点信息/r_cp
		flag = synDataQuery("r_cp","r_cp", "cp_no",cp_no, "cp_no", null,null);
		this.synLog("采集点信息/r_cp", flag);
		
		// 同步----4.1.4.5采集对象信息/r_coll_obj
		//新疆特殊情况 未归档前COLL_OBJ_ID一直在变+++++需要先删除后更新+++++++++++++++++++++++++++++++++++++++++++++
		flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", String.valueOf(cp_no),"COLL_OBJ_ID",null,null);
		this.synLog("采集对象信息/r_coll_obj", flag);
		
		// 同步----4.1.4.5采集点计量点关系/r_cp_mp_rela
		flag = synDataQuery("r_cp_mp_rela","r_cp_mp_rela", "cp_no",cp_no, "CP_MEAS_ID", null,null);
		this.synLog("采集点计量点关系/r_cp_mp_rela", flag);
		
		// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
		flag = synDataQuery("r_cp_cons_rela","r_cp_cons_rela","cp_no",cp_no,"cp_cons_id", null,null);
		this.synLog("采集用户关系信息/r_cp_cons_rela", flag);
		
		// 同步---- 用户终端联系表
		flag = synRconsTmnlRela("r_cons_tmnl_rela", "cons_no", cons_no, tmnlId);
		this.synLog("用户终端联系表/r_cons_tmnl_rela", flag);

		logger.info("终端自动装接调用的档案数据同步完成!");
		return flag;
	}
	
	/**
	 * 集抄同步中间库数据
	 * 
	 * @param tmnlId
	 * @param cpNo
	 * @param tgId
	 * 
	 * @return boolean true or false
	 */
	public boolean synOtherDataByService(String tmnlId, String cpNo, String tgId, String appNo) {
		String querySql = null;
		List list = null;
		boolean flag = false;
		
		/*
		 * 东软营销根据电表是否变更判断营销发起调试的源头
		 * 业扩申请编号不为空，从业扩发起，从方案表中取档案
		 * 业扩申请编号为空，从采集菜单发起，从归档后的档案表中取数据
		 * 业扩申请编号等于1，为定时任务发起，从归档后的档案表中取数据
		 */
		String wkstAppNo = null;
		querySql = "SELECT T.WKST_APP_NO FROM I_TMNL_TASK T WHERE T.APP_NO =?"; 
		list = this.getJdbcDAO().queryForList(querySql, new Object[]{appNo});
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			wkstAppNo = StringUtil.removeNull(mapp.get("WKST_APP_NO"));
		}
		if(!"".equals(wkstAppNo) && !"1".equals(wkstAppNo)){
			flag = this.synDataByScheme("", "", cpNo, tmnlId, tgId);
			return flag;
		}
		
		// 同步----4.1.4.12台区/g_tg
		flag = synDataQuery("g_tg","g_tg",  "tg_id", tgId, "tg_id",null,null);
		this.synLog("台区/g_tg", flag);
		
		//同步-----同步居民用户到/c_cons
		querySql = "select distinct t.cons_id from c_mp t where t.tg_id=" + tgId;
		List tempList = getJdbcYXDAO().queryForList(querySql);
		for(Iterator it = tempList.iterator() ;it.hasNext();){
			Map<?, ?> m = (Map<?, ?>) it.next();
			querySql = "SELECT * FROM C_CONS G WHERE G.CONS_ID=?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{m.get("CONS_ID")});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) itr.next();
				String cons_id = StringUtil.removeNull(map.get("CONS_ID"));
				flag = synDataQuery("c_cust","c_cust", "cust_id", String.valueOf(map.get("CUST_ID")),"cust_id",null,null);
				this.synLog("客户信息/c_cust", flag);
				flag = synDataQuery("C_CONS","C_CONS", "cons_id", cons_id,"CONS_ID",tgId+","+tmnlId,"1");
				this.synLog("居民用户/c_cons", flag);
			}
		}
		//flag = synCcons(tgId, tmnlId);
		this.synLog("居民用户/c_cons", flag);
		
		//虚拟台区到用户表中,公变：大写T打头 + TG_NO++++++++++++20100621东软营销系统不需要虚拟台区用户++++++++++++++++
		flag = synTgToCcons(tgId);
		logger.info("虚拟台区到用户表更新成功");
		this.synLog("虚拟台区", flag);
		
		String line_id = "";
		querySql = "SELECT R.LINE_ID FROM G_LINE_TG_RELA R WHERE R.TG_ID=?"; 
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tgId});
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<?, ?> m = (Map<?, ?>) iterator.next();
				line_id = StringUtil.removeNull(m.get("LINE_ID"));
				// 同步 ----4.1.4.16 线路/g_line
				flag = synDataQuery("g_line","g_line",  "line_id", line_id,"line_id", null,null);
				this.synLog("线路/g_line", flag);
		}
		
		// 同步----4.1.4.11台区线路关系信息/g_line_tg_rela
		flag = synDataQuery("g_line_tg_rela","g_line_tg_rela",  "tg_id", tgId, "line_tq_id", null,null);
		this.synLog("台区线路关系信息/g_line_tg_rela", flag);

		// 同步----4.1.4.13变压器信息/g_tran
		if (tgId != null && !"".equals(tgId)) {
			querySql = "SELECT R.EQUIP_ID FROM G_TRAN R WHERE R.TG_ID =?";
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tgId });
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String equipId = StringUtil.removeNull(mapp.get("EQUIP_ID"));
				flag = synDataQuery( "g_tran","g_tran", "equip_id", equipId,"equip_id", null,null);
				this.synLog("变压器信息/g_tran", flag);
			}
		}
		
		// 同步----4.1.4.3终端资产信息/d_lc_equip
		if (tmnlId != null && !"".equals(tmnlId)) {
			querySql = "SELECT ASSET_ID FROM R_TMNL_RUN WHERE TERMINAL_ID =?";
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tmnlId });
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String assetId = StringUtil.removeNull(mapp.get("ASSET_ID"));
				flag = synDataQuery("d_lc_equip","d_lc_equip", "ID", assetId,"ID",null,null);
				this.synLog("终端资产信息/d_lc_equip", flag);
			}
		}

		// 同步----4.1.4.4运行终端信息/r_tmnl_run
		flag = synDataQuery("r_tmnl_run","r_tmnl_run", "TERMINAL_ID", String.valueOf(tmnlId),"TERMINAL_ID",null,null);
		this.synLog("运行终端信息/r_tmnl_run", flag);

		// 同步----4.1.4.5采集对象信息/r_coll_obj
		//新疆特殊情况 未归档前COLL_OBJ_ID一直在变+++++需要先删除后更新+++++++++++++++++++++++++++++++++++++++++++++
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cpNo });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			this.delById("r_coll_obj", "meter_id", meter_id);
		}
		

		// 同步-----4.1.4.25运行其它集抄设备/r_exec_other_dev
		/*querySql = "SELECT R.COLLECTOR_ID FROM R_COLL_OBJ R WHERE R.CP_NO=?";
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { cpNo });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String collectorID = StringUtil.removeNull(mapp.get("COLLECTOR_ID"));
			flag = synByService("r_exec_other_dev","collector_id", "collector_id", collectorID, "num");
			this.synLog("运行其它集抄设备/r_exec_other_dev", flag);
		}*/

		// 一终端多表
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { cpNo });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			// 同步----4.1.4.6电能表信息/d_meter
			flag = synDataQuery( "d_meter","d_meter",  "meter_id", meter_id,"meter_id", null,null);
			this.synLog("电能表信息/d_meter", flag);
			
			// 同步----4.1.4.7运行电能表信息/c_meter
			flag = synDataQuery("c_meter", "c_meter", "meter_id", meter_id,"meter_id",  tgId, "1");// flag:1为集抄
			this.synLog("运行电能表信息/c_meter", flag);

			// 同步----4.1.4.1计量点信息/c_mp
			querySql = "SELECT A.MP_ID FROM C_METER_MP_RELA A WHERE A.METER_ID=?";
			list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { meter_id });
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				String mp_id = StringUtil.removeNull(map.get("MP_ID"));
				
				// 计量点更新
				flag = synDataQuery("c_mp","c_mp", "mp_id", mp_id,"mp_id",null,null);
				this.synLog("计量点信息/c_mp", flag);
				
				String it_id = "";
				querySql = "SELECT R.IT_ID FROM c_mp_it_rela R WHERE R.MP_ID=?"; 
				List list1 = this.getJdbcYXDAO().queryForList(querySql, new Object[]{mp_id});
				for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
						Map<?, ?> m = (Map<?, ?>) iterator.next();
						it_id = StringUtil.removeNull(m.get("IT_ID"));
						// 同步-----4.1.4.8运行互感器信息/c_it_run
						flag = synDataQuery("c_it_run","c_it_run", "it_id",it_id,"it_id",null,null);
						this.synLog("行互感器信息/c_it_run", flag);
				}

				if (list != null && list.size() > 0) {
					// 同步----4.1.4.9互感器计量点关系信息/c_mp_it_rela
					flag = synDataQuery( "c_mp_it_rela","c_mp_it_rela", "mp_id", mp_id,"it_mp_id", null,null);
					this.synLog("互感器计量点关系信息/c_mp_it_rela", flag);
					
					// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
					flag = synDataQuery( "c_meter_mp_rela","c_meter_mp_rela", "mp_id", mp_id,"meter_mp_id", null,null);
					this.synLog("电能表计量点关系信息/c_meter_mp_rela", flag);
				}
			}
		}

		// 同步----4.1.4.2采集点信息/r_cp
		flag = synDataQuery("r_cp","r_cp", "cp_no", cpNo,"CP_NO",null,null);
		this.synLog("采集点信息/r_cp", flag);
		
		flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", String.valueOf(cpNo),"COLL_OBJ_ID",null,null);
		this.synLog("采集对象信息/r_coll_obj", flag);
		
		// 同步----4.1.4.5采集点计量点关系/r_cp_mp_rela
		flag = synDataQuery("r_cp_mp_rela","r_cp_mp_rela", "cp_no",cpNo, "CP_MEAS_ID", null,null);
		this.synLog("采集点计量点关系/r_cp_mp_rela", flag);

		// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
		flag = synDataQuery("r_cp_cons_rela","r_cp_cons_rela", "cp_no", cpNo,"cp_cons_id", null,null);
		this.synLog("采集用户关系信息/r_cp_cons_rela", flag);

		// 同步---- 用户终端联系表
		querySql = "SELECT G.TG_NO FROM G_TG G WHERE G.TG_ID=?";
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tgId });
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map<?, ?> map = (Map<?, ?>) it.next();
			String tgNo = StringUtil.removeNull(map.get("TG_NO"));
			flag = synRconsTmnlRela("r_cons_tmnl_rela", "cons_no", "T" + tgNo, tmnlId);
			this.synLog("用户终端联系表/r_cons_tmnl_rela", flag);
		}

		logger.info("终端自动装接调用的档案数据同步完成!");
		return flag;
	}

	/**
	 * 删除相关数据
	 */
	private void delById(String tableName, String id, String value){
		String sql = "DELETE FROM " + tableName +" WHERE " + id + "= ?";	
		getJdbcDAO().update(sql, new Object[]{value}) ;
	}
	
	/**
	 * 根据id从中间库queryTableName取出queryName对应的数据queryValue
	 * 
	 * @param queryTableName
	 * @param id
	 * @param zdName
	 * @param queryName
	 * 
	 * @return queryValue
	 */
	public String getStringById(String queryTableName, String id, String zdName, String queryName) {
		String queryValue="";
		String sql = "select * from "+queryTableName+" where "+queryName+"="+id;
		SqlRowSet rs = getJdbcYXDAO().queryForRowSet(sql);
		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		List ls = jdbcToHashMap.getListByCS(rs);
		int i = 0;
		for (Iterator it = ls.iterator(); it.hasNext();) {
			HashMap map = (HashMap) it.next();
			queryValue = StringUtil.removeNull(map.get(zdName.toUpperCase()));
			i++;
		}
		return queryValue;
	}

	/**
	 * 根据传入的表名,查询字段名,查询字段值等同步数据
	 * 
	 * @param String originTable 源表
	 * @param String destinationTable 目标表
	 * @param String keyName 键名
	 * @param String keyValue 键值
	 * @param String primaryKeyName 主键名
	 * @param String value 传一些特殊字段值
	 * @param String flag 集抄负控标识，"0"为负控，"1"为集抄
	 * 
	 * @return boolean true or false
	 */
	@SuppressWarnings("finally")
	public boolean synDataQuery(String originTable, String destinationTable,String keyName, String keyValue, String primaryKeyName,String value,String blag) {
		
		originTable = originTable.toUpperCase();
		destinationTable = destinationTable.toUpperCase();
		
		Object[] data = null;
		String insertSql = null;
		String updateSql = null;
		Integer result = null;
		
		//中间库SQL
		String queryMidColumeNameSql = " select column_name from user_tab_columns where table_name = '" + originTable + "'";
		//采集库SQL
		String queryColumeNameSql = " select column_name from user_tab_columns where table_name = '" + destinationTable + "'";
		
		List tempList = getJdbcDAO().queryForList(queryColumeNameSql);
		Set<String> collectSet = new HashSet();
		for (int i = 0; i < tempList.size(); i++) {
			Map map = (Map) tempList.get(i);
			collectSet.add((String) map.get("column_name"));
		}
		
		tempList = getJdbcYXDAO().queryForList(queryMidColumeNameSql);
		Set<String> midSet = new HashSet();
		for (int i = 0; i < tempList.size(); i++) {
			Map map = (Map) tempList.get(i);
			midSet.add((String) map.get("column_name"));
		}
		
		//采集库多出来的字段
		Set<String> difColumnNames = new HashSet<String>();
			
		//找出采集库必须填充的字段
		for (String str : collectSet) {
			if(!midSet.contains(str)){
				difColumnNames.add(str);
			}
		}
		
		// 按照参数从中间数据库中读取数据
		String sql = "select * from " + originTable + " where " + keyName + "=" + "'" + keyValue + "'";
		List ls = getJdbcYXDAO().queryForList(sql);
		
		if(ls.size() == 0) {
			logger.info("根据参数在中间库 " + originTable + " 表未查询到数据，无需同步 ！");
			logger.info(sql);
			return true;
		}
		boolean flag = false;
		List list = null;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = getTransactionManager().getTransaction(def);
		try {
			for (Iterator it = ls.iterator(); it.hasNext();) {
				Map<Object, Object> map = (Map<Object, Object>) it.next();
				
				for(String o : difColumnNames){
					map.put(o, null);
				}
				//负控C_CONS	
				if("C_CONS".equals(destinationTable ) && "0".equals(blag)){
					String tgId = value.substring(0, value.indexOf(","));
					String tmnlId = value.substring(value.indexOf(",")+1, value.length());
					
					// 中间库C_PS取得subs_id
					String querySql = "SELECT * FROM C_PS G WHERE G.CONS_ID=?";
					list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
					if(list != null && list.size() > 0){
						Map<?, ?> m = (Map<?, ?>) list.get(0);
						map.put("SUBS_ID", m.get("SUBS_ID"));
					}
					
					String cons_type = null;
					if("S_APP".equals(originTable) || "S_BATCH_CUST_APP".equals(originTable)){
						
						map.put("CUST_ID",null);
						
						// 中间库S_MP_SCHEME取得line_id,tg_id
						querySql = "SELECT * FROM S_MP_SCHEME G WHERE G.CONS_ID=?";
						list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
						if(list != null && list.size() > 0){
							Map<?, ?> m = (Map<?, ?>) list.get(0);
							map.put("LINE_ID", m.get("LINE_ID"));
							map.put("TG_ID",m.get("TG_ID"));
						}
						
						// 取得 用电用户类型
						querySql = "SELECT * FROM S_MP_SCHEME G WHERE G.CONS_ID=?";
						list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
						for (Iterator itr = list.iterator(); itr.hasNext();) {
							Map<?, ?> mapp = (Map<?, ?>) itr.next();
							String usageTypeCode = StringUtil.removeNull(mapp.get("USAGE_TYPE_CODE"));
							String typeCode = StringUtil.removeNull(mapp.get("TYPE_CODE"));
							String tmnlTypeCode = "01";
							cons_type= this.getConsType(tmnlTypeCode, usageTypeCode, typeCode);
						}
					}else{
						// 中间库C_MP取得line_id,tg_id
						querySql = "SELECT * FROM C_MP G WHERE G.CONS_ID=?";
						list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
						if(list != null && list.size() > 0){
							Map<?, ?> m = (Map<?, ?>) list.get(0);
							map.put("LINE_ID", m.get("LINE_ID"));
							map.put("TG_ID",m.get("TG_ID"));
						}
						
						// 取得 用电用户类型
						querySql = "SELECT * FROM C_MP G WHERE G.CONS_ID=?";
						list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
						for (Iterator itr = list.iterator(); itr.hasNext();) {
							Map<?, ?> mapp = (Map<?, ?>) itr.next();
							String usageTypeCode = StringUtil.removeNull(mapp.get("USAGE_TYPE_CODE"));
							String typeCode = StringUtil.removeNull(mapp.get("TYPE_CODE"));
							String tmnlTypeCode = "";
							//从用户终端联系表取得 tmnlTypeCode
							querySql = "SELECT G.TERMINAL_TYPE_CODE FROM R_TMNL_RUN G WHERE G.TERMINAL_ID=?"; 
							List l = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tmnlId});
							for (Iterator i = l.iterator(); i.hasNext();) {
								Map p = (Map<?, ?>) i.next();
								tmnlTypeCode = StringUtil.removeNull(p.get("TERMINAL_TYPE_CODE"));
							}
							cons_type= this.getConsType(tmnlTypeCode, usageTypeCode, typeCode);
						}
					}
			
					map.put("CONS_TYPE", cons_type);
					
					String cap_grade_no = this.getCapgrade(String.valueOf(map.get("CONTRACT_CAP")));
					
					map.put("CAP_GRADE_NO", cap_grade_no);
					map.put("AREA_NO", StringUtil.subStr((String) map.get("ORG_NO"), 0, 7));
				
				//集抄C_CONS
				}else if("C_CONS".equals(destinationTable ) && "1".equals(blag)){
					String tgId = value.substring(0, value.indexOf(","));
					String tmnlId = value.substring(value.indexOf(",")+1, value.length());
					
					map.put("CONS_ID", keyValue);
					map.put("AREA_NO", StringUtil.subStr((String) map.get("ORG_NO"), 0, 7));
					map.put("TG_ID", tgId);
					
					BigDecimal subs_id = null;
					BigDecimal line_id = null;
					
					//中间库取得line_id. subs_id.,tg_id
					String querySql = "SELECT * FROM C_MP G WHERE G.CONS_ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{keyValue});
					if (list.iterator().hasNext() && list.iterator().next() != null) {
						Map m = (Map<?, ?>) list.iterator().next();
						line_id = (BigDecimal) m.get("LINE_ID");
						querySql = "SELECT A.SUBS_ID FROM G_SUBS_LINE_RELA A WHERE A.LINE_ID=?"; 
						list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{line_id});                                                       
						if (list.iterator().hasNext() && list.iterator().next() != null)  {
							Map mapt = (Map<?, ?>) list.iterator().next();
							subs_id = (BigDecimal)mapt.get("SUBS_ID");
						}
					}
					
					map.put("SUBS_ID", subs_id);
					map.put("LINE_ID", line_id);
					
					String cons_type = null;
					//取得 用电用户类型
					querySql = "SELECT * FROM C_MP G WHERE G.CONS_ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{keyValue});
					for (Iterator itr = list.iterator(); itr.hasNext();) {
						Map pp = (Map<?, ?>) itr.next();
						String usageTypeCode = StringUtil.removeNull(pp.get("USAGE_TYPE_CODE"));
						String typeCode = StringUtil.removeNull(pp.get("TYPE_CODE"));
						String typeTmnlCode = "";
						//取得终端
						querySql = "SELECT G.TERMINAL_TYPE_CODE FROM R_TMNL_RUN G WHERE G.TERMINAL_ID=?"; 
						List l = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tmnlId});
						for (Iterator i = l.iterator(); i.hasNext();) {
							Map p = (Map<?, ?>) i.next();
							typeTmnlCode = StringUtil.removeNull(p.get("TERMINAL_TYPE_CODE"));
						}
						//取得用户类型
						cons_type= this.getConsType(typeTmnlCode, usageTypeCode, typeCode);
					}
					
					map.put("CONS_TYPE", cons_type);
				
					String cap_grade_no = this.getCapgrade(String.valueOf(map.get("CONTRACT_CAP")));
					map.put("CONTRACT_CAP", cap_grade_no);					
				}
				else if("R_CP".equals(destinationTable)){
					if("S_R_CP_SCHEME".equals(originTable)){
						map.put("CP_NO", StringUtil.removeNull(map.get("CP_SCHEME_NO")));//方案表中的cp_no
					}	
				}else if("R_TMNL_RUN".equals(destinationTable)){
					map.put("TMNL_ASSET_NO", String.valueOf(map.get("TERMINAL_ID")));        

					//terminal_addr = "6501" + terminal_addr;//20100611乌局的终端地址都没有区域码
					if(map.get("TERMINAL_ADDR") == null){
						logger.info("中间库的 R_TMNL_RUN 表未查询到 TERMINAL_ADDR, 同步 R_TMNL_RUN 表失败" );
						return false;
					}
					if(map.get("FACTORY_CODE") == null){
						logger.info("中间库的 R_TMNL_RUN 表未查询到 FACTORY_CODE, 同步 R_TMNL_RUN 表失败" );
						return false;
					}
					if(map.get("TERMINAL_TYPE_CODE") == null){
						logger.info("中间库的 R_TMNL_RUN 表未查询到 TERMINAL_TYPE_CODE, 同步 R_TMNL_RUN 表失败" );
						return false;
					}
					if(map.get("COLL_MODE") == null){
						logger.info("中间库的 R_TMNL_RUN 表未查询到 COLL_MODE, 同步 R_TMNL_RUN 表失败" );
						return false;
					}
					
					String querySql = "SELECT * FROM D_LC_EQUIP D WHERE D.ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{StringUtil.removeNull(map.get("TERMINAL_ID"))});
					if(list != null && list.size() > 0){
						Map<?, ?> m = (Map<?, ?>) list.get(0);
						map.put("CIS_ASSET_NO", StringUtil.removeNull(m.get("ASSET_NO")));
					}
					
					//取得通讯规约
					String  terminal_type_code =StringUtil.removeNull(map.get("TERMINAL_TYPE_CODE"));  
					String  protocol_code = this.getChTmnlProtocol(StringUtil.removeNull(map.get("PROTOCOL_CODE")));
					if ("".equals(protocol_code) || protocol_code == null) {
						//terminal_type_code为01、02表示05规约,为03~07表示698规约,为08表示关口
						if ("01".equals(terminal_type_code) || "02".equals(terminal_type_code)) {
							protocol_code = "1";
						} else if (!"08".equals(terminal_type_code)) {
							protocol_code = "5";
						}	
					}
					
					map.put("PROTOCOL_CODE", protocol_code);
					//01定时冻结：20100517前置机部分要求更改
					map.put("FREEZE_MODE", map.get("FREEZE_MODE") == null ? "01" : map.get("FREEZE_MODE"));
					map.put("FREEZE_CYCLE_NUM", map.get("FREEZE_CYCLE_NUM") == null ? 48 : map.get("FREEZE_CYCLE_NUM"));
				  
				  	if ("5".equals(protocol_code)) {
				  		map.put("SEND_UP_MODE", 1);
				  	} 
//				  	//任务上送方式
//				  	querySql = "SELECT SEND_UP_MODE from I_TMNL_PROT_SEND_SETUP T WHERE T.Protocol_Code = ?";
//					list = this.getJdbcDAO().queryForList(querySql, new Object[]{protocol_code});
//					for (Iterator itr = list.iterator(); itr.hasNext();) {
//						Map<?, ?> mapp = (Map<?, ?>) itr.next();
//						send_up_mode = NumberUtil.removeNull(mapp.get("SEND_UP_MODE"));
//					}
				  	//采集方式营销过来的03表示CDMA,前置机部分只处理02为GPRS的情况。20100723+陶晓峰,20100728-不需要处理了
				  	//map.put("COLL_MODE", "03".equals(map.get("COLL_MODE"))?"02":map.get("COLL_MODE"));
				}else if("R_COLL_OBJ".equals(destinationTable)){
					//coll_port = 80; 20100611不能为空
					map.put("COLL_PORT", 80);
					
				}else if("C_METER".equals(destinationTable)){
					//c_meter中的FMR_ASSET_NO，REG_STATUS，REG_SN字段不需要更新
					collectSet.remove("FMR_ASSET_NO");
					collectSet.remove("REG_STATUS");
					collectSet.remove("REG_SN");
					
					String querySql = null;
					String cons_no = null;
					String mp_id = null;
//					// 专变负控
//					if ("0".equals(blag)) {
//						cons_no = value;
//					// 公变集抄
//					} else {
					if("S_METER_SCHEME".equals(originTable)){
						//从方案表中取
						querySql = "SELECT C.CONS_NO, C.MP_ID FROM S_MP_METER_RELA_SCHMEM C, S_METER_SCHEME T WHERE C.METER_SCHEME_ID = T.METER_SCHEME_ID AND T.METER_ID = ?"; 
					}else{
						querySql = "SELECT C.CONS_NO, A.MP_ID FROM C_MP A, C_METER_MP_RELA B, C_CONS C WHERE B.MP_ID = A.MP_ID AND A.CONS_ID = C.CONS_ID  AND B.METER_ID = ?"; 
					}
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{keyValue});
					for (Object o : list) {
						Map<?, ?> mapp = (Map<?, ?>) o;
						cons_no = StringUtil.removeNull(mapp.get("CONS_NO"));
						mp_id = StringUtil.removeNull(mapp.get("MP_ID"));
						map.put("CONS_NO", cons_no);
						map.put("MP_ID", mp_id);
					}
//					}
					
					// 取得表计资产号
					querySql = "SELECT D.ASSET_NO FROM D_METER D WHERE D.METER_ID=?";
					list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
					if(list != null && list.size() > 0){
						Map<?, ?> m = (Map<?, ?>) list.get(0);
						map.put("ASSET_NO", m.get("ASSET_NO"));
						map.put("AREA_NO",StringUtil.subStr((String) map.get("ORG_NO"), 0, 7) );
					}
					//新疆营销系统的通讯地址保存在D_meter的METER_KIND中
					//表地址 取自（采集点对象）
					querySql = "SELECT R.METER_KIND FROM D_METER R WHERE R.METER_ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{ map.get("METER_ID")});
					if(list!= null && list.size() > 0){
						Map m = (Map<?, ?>) list.get(0);
						//comm_addr1 = "00"+StringUtil.removeNull(mapp.get("METER_KIND"));//20100605东软集抄需要12位的表地址,需要考虑是否应该在前置机部分自动补0
						//营销的表地址是10位,采集系统补00
						map.put("COMM_ADDR1", m.get("METER_KIND"));
					}
					
					querySql = "SELECT T.PROTOCOL_CODE FROM R_TMNL_RUN T WHERE T.CP_NO = (SELECT T.CP_NO FROM R_COLL_OBJ T WHERE T.METER_ID = ?)";
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{ map.get("METER_ID")});
					if(list!= null && list.size() > 0){
						Map m = (Map<?, ?>) list.get(0);
						String protocolCode = StringUtil.removeNull(m.get("PROTOCOL_CODE"));
						if("07".endsWith(protocolCode)){
							//comm_mode = "05";//20100605东软规约这个不填05召不回来数据
							map.put("COMM_MODE", "05");
							if(map.get("COMM_ADDR1") != null){
								map.put("COMM_ADDR1", "00" + map.get("COMM_ADDR1"));
							}
						}
					}
					
					// 取得终端资产号
					querySql = "SELECT N.TERMINAL_ID FROM R_COLL_OBJ R, R_TMNL_RUN N WHERE R.CP_NO=N.CP_NO AND R.METER_ID=?";
					list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { keyValue });
					if(list != null && list.size() > 0){
						Map m = (Map<?, ?>) list.get(0);
						map.put("TMNL_ASSET_NO", m.get("TERMINAL_ID"));
					}
				}
				
				//一般的，是按主键更新，特殊的，按业务需要，可能根据另外的字段更新
				data = SQLutil.genUpdateData(map, collectSet, primaryKeyName);
				if (insertSql == null) {
				updateSql = SQLutil.genUpdateSql(destinationTable,collectSet,primaryKeyName);   
				insertSql = SQLutil.genInsertSql(destinationTable,collectSet,primaryKeyName);   
				}
				
				// 试图更新
				result = getJdbcDAO().update(updateSql, data);
				// 没有要更新的记录，则插入
				if (result == 0) {
					result = getJdbcDAO().update(insertSql, data); 	
				}
			}
			
			if(result == 1){
				flag = true;
			}
			
		} catch (DataAccessException ex) {
			flag = false;
			ex.printStackTrace();
			getTransactionManager().rollback(status);
			throw ex;
		}finally{
			getTransactionManager().commit(status);
			return flag;
		}
	}
		


	/**
	 * 同步用户终端联系表
	 * 
	 * @param tableName 表名
	 * @param qKeyName 键名
	 * @param consNo 客户编号
	 * @param tmnlId 
	 * 
	 * @return true or false 
	 */
	public boolean synRconsTmnlRela(String tableName, String qKeyName, String consNo, String tmnlId) {
		boolean flag = true;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = getTransactionManager().getTransaction(def);
		try{	
			String areaNo = null;
			String orgNo = null;
			String querySql = null;
			String param = null;
			List list = null;
			Integer i = null;
			int j = consNo.indexOf("T");
			if (j == 0) {
				querySql = "SELECT ORG_NO FROM G_TG WHERE TG_NO=?";
				param = consNo.substring(1, consNo.length());
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{param});
			} else {
				querySql = "SELECT ORG_NO FROM C_CONS WHERE CONS_NO=?";
				list = this.getJdbcDAO().queryForList(querySql, new Object[]{consNo});
			}
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();		
				orgNo = StringUtil.removeNull(mapp.get("ORG_NO"));
				if(orgNo.length() < 7){
					areaNo = orgNo;
				}else{
					areaNo = orgNo.substring(0, 7);
				}
				String updateSql = "UPDATE R_CONS_TMNL_RELA A SET ORG_NO='"+orgNo+"',AREA_NO='"+areaNo+"' WHERE CONS_NO=? AND TMNL_ASSET_NO=?";
				i = getJdbcDAO().update(updateSql, new Object[]{consNo, tmnlId});
				if (i == 0) {
					String insertSql=" INSERT INTO R_CONS_TMNL_RELA(ORG_NO, AREA_NO, CONS_NO, TMNL_ASSET_NO) VALUES(?,?,?,?)";
					//采集库的tmnlAssetNo等于中间库的tmnlId
					getJdbcDAO().update(insertSql, new Object[]{orgNo, areaNo, consNo, tmnlId});
				}
			}
		} catch (DataAccessException ex) {
			flag = false;
			ex.printStackTrace();
			getTransactionManager().rollback(status);
			throw ex;
		}finally{
			getTransactionManager().commit(status);
			return flag;
		}
		
	}
	
	/**
	 * 取得容量等级
	 * 
	 * @param contract_cap
	 * @return 容量等级
	 */
	private String getCapgrade(final String contract_cap) {
		Object obj = this.getJdbcDAO().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {
				CallableStatement cstmt = null;
				String result = "";
				try {
					cstmt = conn.prepareCall("{?=call get_cap_grade(?)}");
					cstmt.registerOutParameter(1, Types.VARCHAR);
					cstmt.setString(2, contract_cap);
					cstmt.execute();
					result = cstmt.getString(1);
				} catch (Exception e) {
					ExceptionHandle.handleUnCheckedException(e);
				} finally {
					if (cstmt != null) {
						try {
							cstmt.close();
						} catch (SQLException e) {
						}
					}
				}
				result = result == null ? "" : result.trim();
				return result;
			}
		});
		return obj.toString();
	}

	/**
	 * 取得通信规约类型
	 * 
	 * @param protocol_code
	 * @return 通信规约类型
	 */
	private String getChTmnlProtocol(final String protocol_code) {
		Object obj = this.getJdbcDAO().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {
				CallableStatement cstmt = null;
				String result = "";
				try {
					cstmt = conn.prepareCall("{?=call ch_tmnl_protocol(?)}");
					cstmt.registerOutParameter(1, Types.VARCHAR);
					cstmt.setString(2, protocol_code);
					cstmt.execute();
					result = cstmt.getString(1);
				} catch (Exception e) {
					ExceptionHandle.handleUnCheckedException(e);
				} finally {
					if (cstmt != null) {
						try {
							cstmt.close();
						} catch (SQLException e) {
						}
					}
				}
				result = result == null ? "" : result.trim();
				return result;
			}
		});
		return obj.toString();
	}
	
	/**
	 * 取得用电用户类型
	 * @param usage_type_code
	 * @param type_code
	 * @param cons_sort_code
	 * @param tg_id
	 * @return
	 */
	private String getConsType(final String tmnlTypeCode, final String usage_type_code,final String type_code) {
		Object obj = this.getJdbcDAO().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {
				CallableStatement cstmt = null;
				String result = "";
				try {
					cstmt = conn.prepareCall("{?=call get_cons_type(?,?,?)}");
					cstmt.registerOutParameter(1, Types.VARCHAR);
					cstmt.setString(2, tmnlTypeCode);
					cstmt.setString(3, usage_type_code);
					cstmt.setString(4, type_code);
					cstmt.execute();
					result = cstmt.getString(1);
				} catch (Exception e) {
					ExceptionHandle.handleUnCheckedException(e);
				} finally {
					if (cstmt != null) {
						try {
							cstmt.close();
						} catch (SQLException e) {
						}
					}
				}
				result = result == null ? "" : result.trim();
				return result;
			}
		});
		return obj.toString();
	}
	
	/**
	 * 虚拟台区到用户表中
	 * @param tgId  台区ID
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean synTgToCcons(String tgId) {
		Number consId = Long.valueOf(tgId);
		String orgNo = "";
		String areaNo = "";
		String consNo = "";
		String consName = "";
		Number lineId = null;
		String consSortCode = "";
		String elecAddr = "";
		Number consType = null;
		String elecTypeCode = "";
		Number contractCap = null;
		Number runCap = null;
		String capGradeNo = "";
		Date buileDate = null;
		String statusCode = "";
		Integer result = null;
		
		String sqlTg = "SELECT * FROM G_TG T WHERE T.TG_ID = "+tgId; 
		List<?> listTg = getJdbcYXDAO().queryForList(sqlTg.toString());
		if(listTg != null && listTg.size() > 0){
			Map<?, ?> mapp = (Map<?, ?>) listTg.get(0);
			orgNo = StringUtil.removeNull(mapp.get("ORG_NO"));
			areaNo = StringUtil.subStr(orgNo, 0, 7);
			consNo = "T" + StringUtil.removeNull(mapp.get("TG_NO"));
			consName = StringUtil.removeNull(mapp.get("TG_NAME"));
			//根据台区ID从G_LINE_TG_RELA取得线路信息
			String sqlLine = "SELECT LINE_ID FROM G_LINE_TG_RELA T WHERE T.TG_ID = "+tgId;
			List<?> listLine = getJdbcYXDAO().queryForList(sqlLine.toString());
			if(listLine != null && listLine.size() > 0){
				Map<?, ?> mapLine = (Map<?, ?>) listLine.get(0);
				lineId = NumberUtil.removeNull(mapLine.get("LINE_ID"));
			}
			consSortCode = "00";
			elecAddr = StringUtil.removeNull(mapp.get("INST_ADDR"));
			consType = 2;
			elecTypeCode = "000";
			contractCap = NumberUtil.removeNull(mapp.get("TG_CAP"));
			runCap = NumberUtil.removeNull(mapp.get("TG_CAP"));
			int cap= runCap.intValue();
			capGradeNo = this.getCapgrade(runCap.toString());
			buileDate = (Date)mapp.get("CHG_DATE");
			statusCode = "0";
		}
		
		String sqlCons = "SELECT * FROM C_CONS T WHERE T.CONS_ID = " + tgId + " AND T.TG_ID = "+tgId;
		List<?> listCons = getJdbcDAO().queryForList(sqlCons.toString());
		if(listCons != null && listCons.size() == 0){
			Object[] objIn = new Object[]{};
			String insertSql="";
			objIn = new Object[]{tgId, orgNo ,areaNo ,consNo ,consName ,tgId ,lineId, consSortCode, elecAddr, consType, elecTypeCode, contractCap, runCap, capGradeNo, buileDate, statusCode};
			insertSql="insert into c_cons" +
					" (CONS_ID, ORG_NO, AREA_NO, CONS_NO, CONS_NAME , TG_ID, LINE_ID, CONS_SORT_CODE, ELEC_ADDR, CONS_TYPE, ELEC_TYPE_CODE, CONTRACT_CAP, RUN_CAP, CAP_GRADE_NO, BUILD_DATE, STATUS_CODE) " +
					" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			result =getJdbcDAO().update(insertSql, objIn);
		}else{
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("UPDATE C_CONS SET ORG_NO = ?, AREA_NO = ?, CONS_NO = ?, CONS_NAME = ?, TG_ID = ?, LINE_ID = ?, CONS_SORT_CODE = ?, ELEC_ADDR = ?, CONS_TYPE = ?, ELEC_TYPE_CODE = ?, CONTRACT_CAP = ?, RUN_CAP = ?, CAP_GRADE_NO = ?, BUILD_DATE = ?, STATUS_CODE = ?");
			updateSql.append("	WHERE CONS_ID = ?");
			Object[] objIn = new Object[]{};
			objIn = new Object[]{orgNo ,areaNo ,consNo ,consName ,tgId ,lineId, consSortCode, elecAddr, consType, elecTypeCode, contractCap, runCap, capGradeNo, buileDate, statusCode, tgId};
			result =getJdbcDAO().update(updateSql.toString(), objIn);
		}
		
		if(result == 1){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 更新 关系表
	 * @param tableName
	 * @param columnName
	 * @param kValue
	 * @return true or false
	 */
	@SuppressWarnings("unused")
	private boolean synRelaTable(String tableName, String columnName, String kValue, String primaryKey) {
		String querySql = " SELECT * FROM "+tableName+"  WHERE  "+columnName+"="+kValue;
		List list = this.getJdbcYXDAO().queryForList(querySql);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> map = (Map<?, ?>) itr.next();
			Set<String> column = (Set<String>) map.keySet();
			StringBuffer startSF = new StringBuffer(" SELECT ");
			StringBuffer conditionSF = new StringBuffer(" ");
			StringBuffer updateSF = new StringBuffer(" ");
			StringBuffer insertValueSF = new StringBuffer(" ");
			StringBuffer insertKeySF = new StringBuffer(" ");
			for (String col : column) {
				String key = col;
				String value = StringUtil.removeNull(map.get(col));
				//源句
				startSF.append("'"+value+"'"+" AS "+key+","); 
				//更新
				if (!primaryKey.equalsIgnoreCase(col)) {
					updateSF.append("A."+key+"="+"B."+key+",");
				}
				//插入KEY
				insertValueSF.append("B."+key+",");
				//插入value
				insertKeySF.append(key+",");
			}
			//条件
			conditionSF.append(" A."+primaryKey+"="+"B."+primaryKey);
			
			startSF = new StringBuffer(startSF.toString().substring(0, startSF.length()-1));
			updateSF = new StringBuffer(updateSF.substring(0, updateSF.length()-1));
			insertValueSF  = new StringBuffer(insertValueSF.substring(0, insertValueSF.length()-1));
			insertKeySF  = new StringBuffer(insertKeySF.substring(0, insertKeySF.length()-1));
			startSF.append(" FROM DUAL");
			
			StringBuffer mergeSql = new StringBuffer("  MERGE INTO  "+tableName+" A USING ("+startSF.toString()+") B ON ("+conditionSF.toString()+")  "); 
			mergeSql.append("  WHEN MATCHED THEN UPDATE SET  "+updateSF.toString()+"   ");
			mergeSql.append("  WHEN NOT MATCHED THEN INSERT ("+insertKeySF.toString()+") VALUES("+insertValueSF.toString()+")  ");
			this.getJdbcDAO().update(mergeSql.toString());
		}
		return true;
	}
	
	/**
	 * 终端自动装接归档前测试（根据营销提供的方案表归档成档案数据）---负控
	 * 
	 * @param cons_no 
	 * @param cons_id
	 * @param cp_no
	 * @param tmnlId
	 * @param tgId
	 * 
	 * @return true or false
	 */
	public boolean synDataByScheme(String cons_no, String cons_id,String cp_no, String tmnlId, String tgId) {
		
		boolean flag = false;
		String querySql = null;
		List list = null;

		// 同步----4.1.4.5采集对象信息/r_coll_obj
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			this.delById("r_coll_obj", "meter_id", meter_id);
		}
		
		// 同步----4.1.4.4运行终端信息/r_tmnl_run  TOTO方案表需要特殊处理
		flag = synDataQuery("r_tmnl_run","r_tmnl_run", "TERMINAL_ID", String.valueOf(tmnlId),"TERMINAL_ID",null,null);
		this.synLog("运行终端信息/r_tmnl_run", flag);
		
		// 一终端多表
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			
			// 同步----4.1.4.6电能表信息/d_meter
			flag = synDataQuery("d_meter","d_meter", "meter_id", meter_id,"meter_id",null,null);
			if (flag) {
				logger.info("电能表信息/d_meter更新成功!");
			} else {
				logger.info("电能表信息/d_meter更新档案同步失败或无需同步!");
			}
			
			// 同步----4.1.4.7运行电能表信息/c_meter   flag：0为专变、1为集抄
			flag = synDataQuery("s_meter_scheme","c_meter", "meter_id", meter_id,"meter_id",cons_no,"0");
			this.synLog("运行电能表信息/c_meter", flag);
			
			querySql = "SELECT A.MP_ID FROM S_MP_METER_RELA_SCHMEM A WHERE A.METER_ID=?";
			list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { meter_id });
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				String mp_id = StringUtil.removeNull(map.get("MP_ID"));
				
				// 计量点更新/c_mp
				flag = synDataQuery("S_MP_SCHEME","c_mp", "mp_id", mp_id,"mp_id",null,null);
			}
			
			// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
			flag = synDataQuery( "S_MP_METER_RELA_SCHMEM","c_meter_mp_rela",  "meter_id", meter_id,"meter_mp_id", null,null);
			this.synLog("电能表计量点关系信息/c_meter_mp_rela", flag);
		}
		
		// 同步----4.1.3.2用户基本信息/c_cons TOTO方案表需要特殊处理
		flag = synDataQuery("S_BATCH_CUST_APP","c_cons", "cons_id", cons_id,"cons_id", tgId+","+tmnlId,"0");
		this.synLog("用户基本信息/c_cons", flag);
		flag = synDataQuery("S_APP","c_cons", "cons_id", cons_id,"cons_id",tgId+","+tmnlId,"0");
		this.synLog("单个居民用户/c_cons", flag);
		
		// 同步---- 用户终端联系表
		flag = synRconsTmnlRela("r_cons_tmnl_rela", "cons_no", cons_no, tmnlId);
		this.synLog("用户终端联系表/r_cons_tmnl_rela", flag);
		
		// 同步----4.1.4.2采集点信息/r_cp
		flag = synDataQuery("S_R_CP_SCHEME","r_cp", "CP_SCHEME_NO",cp_no, "cp_no", null,null);
		this.synLog("采集点信息/r_cp", flag);
		
		flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", cp_no,"COLL_OBJ_ID",null,null);
		this.synLog("采集对象信息/r_coll_obj", flag);
		
		// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
		flag = synDataQuery("r_cp_cons_rela","r_cp_cons_rela","cp_no",cp_no,"cp_cons_id", null,null);
		this.synLog("采集用户关系信息/r_cp_cons_rela", flag);
		
		return flag;
	}
	
	/**
	 * 终端自动装接归档前测试（根据营销提供的方案表归档成档案数据）---集抄
	 * 
	 * @param cons_no 
	 * @param cons_id
	 * @param cp_no
	 * @param tmnlId
	 * @param tgId
	 * 
	 * @return true or false
	 */
	public boolean synDataBySchemeOther(String cons_no, String cons_id,String cp_no, String tmnlId, String tgId) {
		
		boolean flag=false;
		String querySql = null;
		List list = null;

		// 同步----4.1.4.5采集对象信息/r_coll_obj
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			this.delById("r_coll_obj", "meter_id", meter_id);
		}
		
		
		// 同步----4.1.4.4运行终端信息/r_tmnl_run  TOTO方案表需要特殊处理
		flag = synDataQuery("r_tmnl_run","r_tmnl_run", "TERMINAL_ID", String.valueOf(tmnlId),"TERMINAL_ID",null,null);
		this.synLog("运行终端信息/r_tmnl_run", flag);
		
		// 一终端多表
		querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?";
		list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { cp_no });
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
			
			// 同步----4.1.4.6电能表信息/d_meter
			flag = synDataQuery("d_meter","d_meter", "meter_id", meter_id,"meter_id",null,null);
			this.synLog("电能表信息/d_meter", flag);
			
			// 同步----4.1.4.7运行电能表信息/c_meter   flag：0为专变、1为集抄
			flag = synDataQuery("s_meter_scheme","c_meter", "meter_id", meter_id,"meter_id",cons_no,"0");
			this.synLog("运行电能表信息/c_meter", flag);
			
			querySql = "SELECT A.MP_ID FROM S_MP_METER_RELA_SCHMEM A WHERE A.METER_ID=?";
			list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { meter_id });
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				String mp_id = StringUtil.removeNull(map.get("MP_ID"));
				
				// 计量点更新
				flag = synDataQuery("S_MP_SCHEME","c_mp", "mp_id", mp_id,"mp_id",null,null);
			}
			
			// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
			flag = synDataQuery( "S_MP_METER_RELA_SCHMEM","c_meter_mp_rela","meter_id", meter_id,"meter_mp_id", null,null);
			this.synLog("电能表计量点关系信息/c_meter_mp_rela", flag);
		}
		
		//同步-----同步居民用户到/c_cons
		querySql = "select distinct t.cons_id from c_mp t where t.tg_id=" + tgId;
		List tempList = getJdbcYXDAO().queryForList(querySql);
		for(Iterator it = tempList.iterator() ;it.hasNext();){
			Map<?, ?> m = (Map<?, ?>) it.next();
			querySql = "SELECT * FROM C_CONS G WHERE G.CONS_ID=?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{m.get("CONS_ID")});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) itr.next();
				cons_id = StringUtil.removeNull(map.get("CONS_ID"));
				// 同步----4.1.3.2用户基本信息/c_cons TOTO方案表需要特殊处理
				flag = synDataQuery("S_BATCH_CUST_APP","c_cons", "cons_id", cons_id,"cons_id", tgId+","+tmnlId,"0");
				this.synLog("批量居民用户/c_cons", flag);
				flag = synDataQuery("S_APP","c_cons", "cons_id", cons_id,"cons_id",tgId+","+tmnlId,"0");
				this.synLog("单个居民用户/c_cons", flag);
			}
		}
		
		// 同步----4.1.4.2采集点信息/r_cp
		flag = synDataQuery("S_R_CP_SCHEME","r_cp", "CP_SCHEME_NO",cp_no, "cp_no", null,null);
		this.synLog("采集点信息/r_cp", flag);
		
		flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", cp_no,"COLL_OBJ_ID",null,null);
		this.synLog("采集对象信息/r_coll_obj", flag);
		
		// 同步---- 用户终端联系表
		querySql = "SELECT G.TG_NO FROM G_TG G WHERE G.TG_ID=?";
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tgId });
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map<?, ?> map = (Map<?, ?>) it.next();
			String tgNo = StringUtil.removeNull(map.get("TG_NO"));
			flag = synRconsTmnlRela("r_cons_tmnl_rela", "cons_no", "T" + tgNo, tmnlId);
			this.synLog("用户终端联系表/r_cons_tmnl_rela", flag);
		}
		
		// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
		flag = synDataQuery("r_cp_cons_rela","r_cp_cons_rela","cp_no",cp_no,"cp_cons_id", null,null);
		this.synLog("采集用户关系信息/r_cp_cons_rela", flag);
		
		return flag;
	}
	
	public static void main(String[] args) {
		//FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/config/applicationContext-dao.xml");
		//SynDataDaoImpl im = (SynDataDaoImpl) ctx.getBean("synDataForMarketingDao");
		//im.synCmeter("c_meter","meter_id","3157772", "13800", "1");//flag:1为集抄 
	}

	@Override
	public boolean synDataCollect(Date sDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean synGateDataByService(String subId, String tmnlId, String cpNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean synDueDataByService() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean synPayDataByService(String userID) {
		// TODO Auto-generated method stub
		return false;
	}

}