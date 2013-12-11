package com.nari.activesyn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nari.util.SQLutil;
import com.nari.util.StringUtil;

/**
 * DataDao接口的实现
 */
@SuppressWarnings("unchecked")
public class DataDaoImpl extends BaseDao implements DataDao {

	private static final Log logger = LogFactory.getLog(DataDaoImpl.class);
	
	/**
	 * 同步营销档案
	 * @param consType 用户类型 "1"为专变，"2"为公变
	 * @param value 专变传consNo,公变传tgNo
	 * 
	 * @return true or false
	 */
	public boolean synData(String consType,String value) {
		
		if("1".equals(consType)){
			return this.synDataForZb(value);
		}else if("2".equals(consType)){
			return this.synDataForGb(value);
		}
			
		return false;
		
	}
	/**
	 * 根据参数同步专变用户信息
	 * @param consNo 客户编号
	 * 
	 * @return boolean true or false
	 */
	public boolean synDataForZb(String consNo){
		
		String querySql = null;
		List list = null;
		boolean flag = false;
		Map map = null;
		
		String custId = null; 
		String consId = null;
		String consName = null;
		
		querySql = "select * from epm_jx.c_cons where cons_no = '" + consNo + "'" ;
		list = getJdbcYXDAO().queryForList(querySql);
		if(list != null && list.size() > 0){
			map = (Map)list.get(0);
			custId = String.valueOf(map.get("cust_id")); 
			consId = String.valueOf(map.get("CONS_ID"));
			consName = String.valueOf(map.get("CONS_NAME"));
		}
		
		// 客户信息/c_cust
		flag = synDataQuery("c_cust","c_cust","cust_id",custId,"cust_id", null);
		this.synLog("客户信息/c_cust", flag);

		String custNo = null; 
		querySql = "select CUST_NO from epm_jx.c_cust where cust_id ='" + custId + "'";
		list = getJdbcYXDAO().queryForList(querySql);
		if(list != null && list.size() > 0){
			map = (Map)list.get(0);
			custNo = String.valueOf(map.get("cust_no"));
		}
		// 用户基本信息/c_cons
		flag = synDataQuery("c_cons","c_cons", "cons_no", consNo,"cons_id",custNo+","+consId);
		this.synLog("用户基本信息/c_cons", flag);
		
		// 用户联系信息/c_contact
		flag = synDataQuery("c_contact","c_contact","cust_id",custId,"CONTACT_ID",null);
		this.synLog("用户联系信息/c_contact", flag);
		
		// 用电客户联系信息关系/C_CONS_CONTACT_RELA
		flag = synDataQuery("C_CONS_CONTACT_RELA","C_CONS_CONTACT_RELA","CONS_ID",consId,"CONTACT_ID",null);
		this.synLog("用电客户联系信息关系/C_CONS_CONTACT_RELA", flag);
		
		// 用户用电地址/C_ELEC_ADDR
		flag = synDataQuery("C_ELEC_ADDR","C_ELEC_ADDR","CONS_ID",consId,"CSA_ID",null);
		this.synLog("用户用电地址/C_ELEC_ADDR", flag);
		
		// 受电点/C_SP
		flag = synDataQuery("C_SP","C_SP","CONS_ID",consId,"SP_ID",null);
		this.synLog("受电点/C_SP", flag);
		
		// 供电电源/C_PS
		flag = synDataQuery("c_ps","c_ps","cons_id",consId,"ps_id",null);
		this.synLog("供电电源/C_PS", flag);
			
		// 计量点/c_mp
		flag = synDataQuery("c_mp","c_mp","cons_no",consNo,"MP_ID",null);
		this.synLog("计量点/c_mp", flag);
		
		// 电能表计量点关系/C_METER_MP_RELA
		flag = synDataQuery("C_METER_MP_RELA","C_METER_MP_RELA","cons_no",consNo,"METER_MP_ID",null);
		this.synLog("电能表计量点关系/C_METER_MP_RELA", flag);
		
		// 互感器计量点关系/C_MP_IT_RELA
		flag = synDataQuery("C_MP_IT_RELA","C_MP_IT_RELA","cons_no",consNo,"IT_MP_ID",null);
		this.synLog("互感器计量点关系/C_MP_IT_RELA", flag);
		
		querySql = "select it_id,mp_id from epm_jx.C_MP_IT_RELA c where c.cons_no ='" + consNo + "'";
		list = getJdbcYXDAO().queryForList(querySql);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			map = (Map) itr.next();	
			String itId = String.valueOf(map.get("it_id"));
			String mp_id = String.valueOf(map.get("mp_id"));
			
			// 运行互感器/C_IT_RUN
			flag = synDataQuery("C_IT_RUN","C_IT_RUN","it_id",itId,"IT_ID",consNo+","+mp_id);
			this.synLog("运行互感器/C_IT_RUN", flag);
		}
		
		querySql = "select c.meter_id,c.mp_id from epm_jx.C_METER_MP_RELA c where c.cons_no ='" + consNo + "'";
		list = getJdbcYXDAO().queryForList(querySql);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			map = (Map) itr.next();	
			String meterId = String.valueOf(map.get("meter_id"));
			String mpId = String.valueOf(map.get("mp_id"));
			
			// 电能表信息/d_meter
			flag = synDataQuery("d_meter","d_meter", "meter_id", meterId,"meter_id",null);
			this.synLog("电能表信息/d_meter", flag);

			// 运行电能表信息/c_meter    
			flag = synDataQuery("c_meter","c_meter", "meter_id", meterId,"meter_id",consNo+","+mpId);
			this.synLog("运行电能表信息/c_meter", flag);		
		}
		
		querySql = "select cp_no from epm_jx.R_CP_CONS_RELA r where r.cons_id = '" + consId + "'";
		list = getJdbcYXDAO().queryForList(querySql);
		if(list != null && list.size() > 0){
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				map = (Map) itr.next();	
				String cp_no = String.valueOf(map.get("cp_no"));
				
				// 采集点/R_CP
				flag = synDataQuery("R_CP","R_CP", "cp_no", cp_no,"CP_NO",null);
				this.synLog("采集点/R_CP", flag);
				
				// 采集用户关系/R_CP_CONS_RELA 
				flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "CONS_ID", consId,"CP_CONS_ID",null);
				this.synLog("采集用户关系/R_CP_CONS_RELA", flag);
				
				// 运行终端信息/r_tmnl_run
				flag = synDataQuery("r_tmnl_run","r_tmnl_run", "cp_no",cp_no,"TERMINAL_ID",null);
				this.synLog("运行终端信息/r_tmnl_run", flag);
				
				// 采集对象信息/r_coll_obj
				flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", cp_no,"COLL_OBJ_ID",null);
				this.synLog("采集对象信息/r_coll_obj", flag);
				
				}
		}else {
			// 采集点/R_CP 捏出个R_CP
			flag = synDataQuery("R_CP","R_CP", "anyString", "anyString","CP_NO",consNo+","+consName);
			this.synLog("捏出采集点/R_CP", flag);
			
			// 采集用户关系/R_CP_CONS_RELA 捏出个关系
			flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "anyString", "anyString","CP_CONS_ID",consNo+","+consId);
			this.synLog("捏出采集用户关系/R_CP_CONS_RELA", flag);
		}
		
		logger.info("档案同步完成！");
		return flag;
	}
	
	/**
	 * 根据参数同步公变用户信息
	 * @param tgNo 台区编号
	 * 
	 * @return boolean true or false
	 */
	public boolean synDataForGb(String tgNo){
	
		String querySql = null;
		List list = null;
		boolean flag = false;
		Map map = null;
		
		// 同步----4.1.4.12台区/g_tg
		flag = synDataQuery("g_tg","g_tg",  "tg_no", tgNo, "tg_id",null);
		this.synLog("台区/g_tg", flag);
		
		String tg_id = null;
		querySql = "select tg_id from epm_jx.g_tg g where g.tg_no = '" + tgNo + "'";
		list = getJdbcYXDAO().queryForList(querySql);
		if(list != null && list.size() > 0){
			map = (Map)list.get(0);
			tg_id = String.valueOf(map.get("tg_id"));
		}else{
			logger.info(querySql);
			logger.info("根据台区编号未查询到台区信息");
			return false;
		}
		
		querySql = "select distinct t.cons_id,t.cons_no from epm_jx.c_mp t where t.tg_id=" + tg_id;
		list = getJdbcYXDAO().queryForList(querySql);
		
		Map mm = null;
		List ls = null;
		
		for(Iterator it = list.iterator() ;it.hasNext();){
			String cons_id = null;
			String consNo = null;
			String custId = null;
			
			map = (Map<?, ?>) it.next();
			cons_id = String.valueOf(map.get("CONS_ID"));
			consNo = String.valueOf(map.get("CONS_NO"));
			
			String str = "select cust_id from epm_jx.c_cons c where c.cons_no = '" + consNo + "'";
			ls = getJdbcYXDAO().queryForList(str);
			
			if(ls.size() > 0 && ls != null){
				mm = (Map)ls.get(0);
				custId = String.valueOf(mm.get("cust_id"));
			}
		
			// 客户信息/c_cust
			flag = synDataQuery("c_cust","c_cust", "cust_id", custId,"cust_id",null);
			this.synLog("客户信息/c_cust", flag);
				
			// 居民用户/c_cons
			flag = synDataQuery("C_CONS","C_CONS", "cons_id", cons_id,"CONS_ID",consNo+","+cons_id);
			this.synLog("居民用户/c_cons", flag);
				
			// 计量点/c_mp
			flag = synDataQuery("c_mp","c_mp","cons_no",consNo,"MP_ID",null);
			this.synLog("计量点/c_mp", flag);
				
			// 电能表计量点关系/C_METER_MP_RELA
			flag = synDataQuery("C_METER_MP_RELA","C_METER_MP_RELA","cons_no",consNo,"METER_MP_ID",null);
			this.synLog("电能表计量点关系/C_METER_MP_RELA", flag);
				
			// 互感器计量点关系/C_MP_IT_RELA
			flag = synDataQuery("C_MP_IT_RELA","C_MP_IT_RELA","cons_no",consNo,"IT_MP_ID",null);
			this.synLog("互感器计量点关系/C_MP_IT_RELA", flag);
			
			// 采集用户关系/R_CP_CONS_RELA 
			flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "CONS_ID", cons_id,"CP_CONS_ID",null);
			this.synLog("采集用户关系/R_CP_CONS_RELA", flag);
				
			querySql = "select it_id,mp_id from epm_jx.C_MP_IT_RELA c where c.cons_no ='" + consNo + "'";
			List li = getJdbcYXDAO().queryForList(querySql);
			
			for (Iterator iterator = li.iterator(); iterator.hasNext();) {
				mm = (Map) iterator.next();	
				String itId = String.valueOf(mm.get("it_id"));
				String mp_id = String.valueOf(mm.get("mp_id"));
				
				// 运行互感器/C_IT_RUN
				flag = synDataQuery("C_IT_RUN","C_IT_RUN","it_id",itId,"IT_ID",consNo+","+mp_id);
				this.synLog("运行互感器/C_IT_RUN", flag);
			}
			
			querySql = "select c.meter_id,c.mp_id from epm_jx.C_METER_MP_RELA c where c.cons_no ='" + consNo + "'";
			li = getJdbcYXDAO().queryForList(querySql);
			for (Iterator ite = li.iterator(); ite.hasNext();) {
				mm = (Map) ite.next();	
				String meterId = String.valueOf(mm.get("meter_id"));
				String mpId = String.valueOf(mm.get("mp_id"));
				// 电能表信息/d_meter
				flag = synDataQuery("d_meter","d_meter", "meter_id", meterId,"meter_id",null);
				this.synLog("电能表信息/d_meter", flag);

				// 运行电能表信息/c_meter    
				flag = synDataQuery("c_meter","c_meter", "meter_id", meterId,"meter_id",consNo+","+mpId);
				this.synLog("运行电能表信息/c_meter", flag);			
			}
		}
		
		String line_id = null;
		querySql = "SELECT R.LINE_ID FROM epm_jx.G_LINE_TG_RELA R WHERE R.TG_ID=?"; 
		list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tg_id});
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				mm = (Map<?, ?>) iterator.next();
				line_id = StringUtil.removeNull(mm.get("LINE_ID"));
				// 同步 ----4.1.4.16 线路/g_line
				flag = synDataQuery("g_line","g_line",  "line_id", line_id,"line_id", null);
				this.synLog("线路/g_line", flag);
		}
		
		// 同步----4.1.4.11台区线路关系信息/g_line_tg_rela
		flag = synDataQuery("g_line_tg_rela","g_line_tg_rela",  "tg_id", tg_id, "line_tq_id", null);
		this.synLog("台区线路关系信息/g_line_tg_rela", flag);

		// 同步----4.1.4.13变压器信息/g_tran
		if (tg_id != null && !"".equals(tg_id)) {
			querySql = "SELECT R.EQUIP_ID FROM epm_jx.G_TRAN R WHERE R.TG_ID =?";
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[] { tg_id });
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String equipId = StringUtil.removeNull(mapp.get("EQUIP_ID"));
				flag = synDataQuery( "g_tran","g_tran", "equip_id", equipId,"equip_id", null);
				this.synLog("变压器信息/g_tran", flag);
			}
		}
		
		querySql = "select t.cons_id,t.cons_no,t.cons_name from c_cons t where t.tg_id=" + tg_id + " and cons_type = '2'" ;
		list = getJdbcDAO().queryForList(querySql);
		String str = null;
		List lst = null;
		for(Iterator it = list.iterator() ;it.hasNext();){
			map = (Map<?, ?>) it.next();
			String consId = String.valueOf(map.get("CONS_ID"));
			String consNo = String.valueOf(map.get("CONS_NO"));
			String consName = String.valueOf(map.get("CONS_NAME"));
			str = "select cp_no from epm_jx.R_CP_CONS_RELA r where r.cons_id = '" + consId + "'";
			lst = getJdbcYXDAO().queryForList(str);
			if(lst.size() > 0){
				for (Iterator itr = lst.iterator(); itr.hasNext();) {
					map = (Map) itr.next();	
					String cp_no = String.valueOf(map.get("cp_no"));
					
					// 采集点/R_CP
					flag = synDataQuery("R_CP","R_CP", "cp_no", cp_no,"CP_NO",null);
					this.synLog("采集点/R_CP", flag);

					// 采集用户关系/R_CP_CONS_RELA 
					flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "CONS_ID", consId,"CP_CONS_ID",null);
					this.synLog("采集用户关系/R_CP_CONS_RELA", flag);
					
					// 运行终端信息/r_tmnl_run
					flag = synDataQuery("r_tmnl_run","r_tmnl_run", "cp_no",cp_no,"TERMINAL_ID",null);
					this.synLog("运行终端信息/r_tmnl_run", flag);
					
					// 采集对象信息/r_coll_obj
					flag = synDataQuery("r_coll_obj","r_coll_obj", "cp_no", cp_no,"COLL_OBJ_ID",null);
					this.synLog("采集对象信息/r_coll_obj", flag);
				}
			}else {
				
				// 采集点/R_CP 捏出个R_CP
				flag = synDataQuery("R_CP","R_CP", "anyString", "anyString","CP_NO",consNo+","+consName);
				this.synLog("捏出采集点/R_CP", flag);

//				// 采集用户关系/R_CP_CONS_RELA 捏出个关系
//				flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "anyString", "anyString","CP_CONS_ID",consNo+","+consId);
//				this.synLog("采集用户关系/R_CP_CONS_RELA", flag);
				
				String cp_no = consNo;
				
				str = "select distinct t.cons_id,t.cons_no from c_mp t where t.tg_id=" + tg_id;
				lst = getJdbcDAO().queryForList(str);
				for (Iterator ittr = lst.iterator(); ittr.hasNext();) {
					
					Map ml = (Map)ittr.next();
					String cons_id = String.valueOf(ml.get("cons_id"));
					
					// 采集用户关系/R_CP_CONS_RELA 捏出个关系
					flag = synDataQuery("R_CP_CONS_RELA","R_CP_CONS_RELA", "anyString", "anyString","CP_CONS_ID",cp_no+","+cons_id);
					this.synLog("捏出采集用户关系/R_CP_CONS_RELA", flag);
					
				}				
			}
		}
		
		logger.info(" 公变用户档案同步完成！");
		
		return flag;
	}
	
	/**
	 * 根据传入的表名,查询字段名,查询字段值等同步数据
	 * 
	 * @param originTable 源表
	 * @param destinationTable 目标表
	 * @param keyName 键名
	 * @param keyValue 键值
	 * @param primaryKeyName 主键名
	 * @param value 一些特殊值
	 * 
	 * @return boolean true or false
	 */
	@SuppressWarnings("finally")
	public boolean synDataQuery(String originTable, String destinationTable,String keyName, String keyValue, String primaryKeyName,String value) {
		
		originTable = originTable.toUpperCase();
		destinationTable = destinationTable.toUpperCase();
		
		Object[] data = null;
		String insertSql = null;
		String updateSql = null;
		Integer result = null;
		String sql = null;
		
		// 按照参数从中间数据库中读取数据
		if(("R_CP".equals(originTable) && !"".equals(value) && null != value)
			|| 	("R_CP_CONS_RELA".equals(originTable) && !"".equals(value) && null != value)){
			sql = "select * from epm_jx." + originTable + " where 1=1 and rownum <=1" ;
		}else {
			sql = "select * from epm_jx." + originTable + " where " + keyName + "='" + keyValue + "'";
		}
		List ls = getJdbcYXDAO().queryForList(sql);
		
		if(ls.size() == 0) {
			logger.info("根据参数在源库 " + originTable + " 表未查询到数据，无需同步 ！");
			logger.info(sql);
			return true;
		}
		
		Map tempMap = (Map)ls.get(0);
		Set<String> midSet = new HashSet();
		midSet = tempMap.keySet();
				
		//采集库SQL
		String queryColumeNameSql = " select column_name from user_tab_columns where table_name = '" + destinationTable + "'";
		
		List tempList = getJdbcDAO().queryForList(queryColumeNameSql);
		Set<String> collectSet = new HashSet();
		for (int i = 0; i < tempList.size(); i++) {
			Map map = (Map) tempList.get(i);
			collectSet.add((String) map.get("column_name"));
		}
		
		
		//采集库多出来的字段
		Set<String> difColumnNames = new HashSet<String>();
			
		//找出采集库必须填充的字段
		for (String str : collectSet) {
			if(!midSet.contains(str)){
				difColumnNames.add(str);
			}
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
		
				if("C_CONS".equals(destinationTable)){
					
					String custNo = value.substring(0, value.indexOf(","));
					String consId = value.substring(value.indexOf(",")+1, value.length());
					map.put("cust_no", custNo);			
			
					// 中间库C_PS取得subs_id
					String querySql = "SELECT * FROM epm_jx.C_PS G WHERE G.CONS_ID=?";
					list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { consId });
					if(list != null && list.size() > 0){
						Map<?, ?> m = (Map<?, ?>) list.get(0);
						map.put("SUBS_ID", m.get("SUBS_ID"));
					}
				
					// 中间库C_MP取得line_id,tg_id
					querySql = "SELECT * FROM epm_jx.C_MP G WHERE G.CONS_ID=?";
					list = this.getJdbcYXDAO().queryForList(querySql,new Object[] { consId });
					if(list != null && list.size() > 0){
						Map<?, ?> m = (Map<?, ?>) list.get(0);
						map.put("LINE_ID", m.get("LINE_ID"));
						map.put("TG_ID",m.get("TG_ID"));
					}

					// 取得 用电用户类型 
					String cons_sort_code = String.valueOf(map.get("CONS_SORT_CODE"));
					String cons_type = null;
					if("00".equals(cons_sort_code)) {
						cons_type = "2";
					}else if("01".equals(cons_sort_code)) {
						cons_type = "1";
					}else if("02".equals(cons_sort_code)) {
						cons_type = "5";
					}else if("03".equals(cons_sort_code)) {
						cons_type = "5";
					}else if( null == cons_sort_code || "".equals(cons_sort_code)){
						cons_type = "10";
					}
					map.put("CONS_TYPE", cons_type);
					
					String cap_grade_no = this.getCapgrade(String.valueOf(map.get("CONTRACT_CAP")));
					map.put("CAP_GRADE_NO", cap_grade_no);
					
					map.put("AREA_NO", StringUtil.subStr((String) map.get("ORG_NO"), 0, 7));
					
				}else if("C_IT_RUN".equals(destinationTable)){
					String consNo = value.substring(0, value.indexOf(","));
					String mpId = value.substring(value.indexOf(",")+1, value.length());
					map.put("CONS_NO", consNo);
					map.put("MP_ID", mpId);
					
				}else if("R_CP".equals(destinationTable)){
					if(!"".equals(value) && null != value){
						String consNo = value.substring(0, value.indexOf(","));
						String consName = value.substring(value.indexOf(",")+1, value.length());
						
						map.put("CP_NO",consNo );
						map.put("NAME",consName );
						map.put("CP_TYPE_CODE", "2");
						map.put("STATUS_CODE", "04");
						map.put("CP_ADDR", consName);
					}
				}else if("R_CP_CONS_RELA".equals(destinationTable)){
					if(!"".equals(value) && null != value){
						String cpNo = value.substring(0, value.indexOf(","));
						String consId = value.substring(value.indexOf(",")+1, value.length());
						
						map.put("CP_CONS_ID",consId );
						map.put("CONS_ID",consId );
						map.put("CP_NO", cpNo);
					}
				}else if("C_METER".equals(destinationTable)){
					collectSet.remove("FMR_ASSET_NO");
					collectSet.remove("REG_STATUS");
					collectSet.remove("REG_SN");
					
					String s = null;
					List l = null;
					Map m = null;
					
					String consNo = value.substring(0, value.indexOf(","));
					String mpId = value.substring(value.indexOf(",")+1, value.length());
					
					map.put("CONS_NO", consNo);
					map.put("MP_ID", mpId);
					
					map.put("AREA_NO",StringUtil.subStr((String) map.get("ORG_NO"), 0, 7) );
					
					s = "SELECT T.PROTOCOL_CODE FROM epm_jx.R_TMNL_RUN T WHERE T.CP_NO = (SELECT T.CP_NO FROM epm_jx.R_COLL_OBJ T WHERE T.METER_ID = '" + keyValue + "')";
					l = this.getJdbcYXDAO().queryForList(s);
					if(list!= null && list.size() > 0){
						m = (Map<?, ?>) list.get(0);
						String protocolCode = StringUtil.removeNull(m.get("PROTOCOL_CODE"));
						if("07".endsWith(protocolCode)){
							//comm_mode = "05";//20100605东软规约这个不填05召不回来数据
							map.put("COMM_MODE", "05");
							map.put("COMM_ADDR1", "00" + map.get("COMM_ADDR1"));
						}
					}
					
					// 取得终端资产号
					s = "SELECT N.TERMINAL_ID FROM epm_jx.R_COLL_OBJ R, epm_jx.R_TMNL_RUN N WHERE R.CP_NO=N.CP_NO AND R.METER_ID='" + keyValue +"'";
					l = this.getJdbcYXDAO().queryForList(s);
					if(l!= null && l.size() > 0){
						m = (Map<?, ?>) list.get(0);
						map.put("TMNL_ASSET_NO", m.get("TERMINAL_ID"));
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
	 * 显示同步档案日志
	 * 
	 * @param tableName 表名
	 * @param flag 成功或失败标志
	 */
	public void synLog(String tableName, boolean flag){
		if (flag) {
			logger.info(tableName + "档案同步【 成功 】!");
		} else {
			logger.info(" ========== " + tableName + "档案同步【 失败 】! ========== ");
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
					e.printStackTrace();
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
					e.printStackTrace();
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
}