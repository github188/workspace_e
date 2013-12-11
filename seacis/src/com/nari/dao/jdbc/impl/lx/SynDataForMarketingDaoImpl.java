package com.nari.dao.jdbc.impl.lx;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;
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

@SuppressWarnings("unchecked")
public class SynDataForMarketingDaoImpl extends BaseDao implements SynDataForMarketingDao{
	
	private static Logger logger = Logger.getLogger(SynDataForMarketingDaoImpl.class); 
	
	/**
	 * 定义数据同步处理
	 * @param tableName
	 * @return true or false
	 */
	public boolean synData(String tableName) {
		tableName=tableName.toLowerCase();
		boolean flag=false;
		JdbcToHashMap jdbcToHashMap=new JdbcToHashMap();
		String insertSql="";
		Object[] objIn = new Object[]{};
		//定义每张表的主键字段名称
		String primaryKeyName = "";
		String delSql="";
		// 4.1.2供电单位信息类
		if("o_org".equals(tableName)){
			String sql="select * from "+StringUtil.removeNull(tableName);
			SqlRowSet rs = getJdbcYXDAO().queryForRowSet(sql);
			List ls=jdbcToHashMap.getListByCS(rs);
			int i = 0;
			try{
				for(Iterator it = ls.iterator() ;it.hasNext();){
					HashMap map = (HashMap)it.next();
					String org_no=StringUtil.removeNull(map.get("ORG_NO"));
					String org_name=StringUtil.removeNull(map.get("ORG_NAME"));
					String p_org_no=StringUtil.removeNull(map.get("P_ORG_NO"));
					String org_type=StringUtil.removeNull(map.get("ORG_TYPE"));
					Number sort_no=NumberUtil.removeNull(map.get("SORT_NO"));
					String area_code=StringUtil.subStr(org_no,2,4);
					
					objIn = new Object[]{org_no,org_name,p_org_no,org_type,sort_no,0,"",area_code,0,"",0,0};
					insertSql="insert into temp_o_org" +
							" (org_no,org_name,p_org_no,org_type,sort_no,is_bulk_sale,tmnl_code,area_code,is_direct,inter_org_no,calc_order,is_stat) " +
							" values (?,?,?,?,?,?,?,?,?,?,?,?)";
					getJdbcDAO().update(insertSql, objIn);
					i++; 
				}
				String mergerSql="MERGE INTO o_org a USING " +
					" (SELECT g.org_no,g.org_name,g.p_org_no,g.org_type,g.sort_no,g.is_bulk_sale,g.tmnl_code,g.area_code,g.is_direct,g.calc_order FROM temp_o_org g ) b " +
					" ON (a.org_no = b.org_no) WHEN MATCHED THEN UPDATE SET org_name= b.org_name, p_org_no= b.p_org_no, org_type= b.org_type, sort_no= b.sort_no,is_bulk_sale= b.is_bulk_sale, tmnl_code= b.tmnl_code, area_code = b.area_code, " +
					" is_direct = b.is_direct, calc_order= b.calc_order " +
					" WHEN NOT MATCHED THEN INSERT (org_no,org_name,p_org_no,org_type,sort_no,is_bulk_sale,tmnl_code,area_code,is_direct,calc_order)" +
					" VALUES (b.org_no,b.org_name,b.p_org_no,b.org_type,b.sort_no,b.is_bulk_sale,b.tmnl_code,b.area_code,b.is_direct,b.calc_order)";
				getJdbcDAO().update(mergerSql);
				delSql="delete temp_o_org ";
				getJdbcDAO().update(delSql);
				flag=true;
			} catch (DataAccessException ex) {
				 
				throw ex;
			}
			 
		}else {
			// 4.1.1标准代码类
			if ("p_code".equals(tableName)) {
				primaryKeyName = "code_id";
			} else if ("p_code_sort".equals(tableName)) {
				primaryKeyName = "code_sort_id";
			// 4.1.2供电单位信息类
			} else if ("o_dept".equals(tableName)) {
				primaryKeyName = "dept_no";
			//4.1.4.14变电站信息/g_subs
			} else if ("g_subs".equals(tableName)) {
				primaryKeyName = "subs_id";
			//4.1.4.15变电站线路关系/g_subs_line_rela
			} else if ("g_subs_line_rela".equals(tableName)) {
				primaryKeyName = "rela_id";
			//1.1.1.17线路关系信息/g_line_rela
			} else if ("g_line_rela".equals(tableName)) {
				primaryKeyName = "line_rela_id";
			// 4.1.4.23运行其他设备/c_run_other_dev
			} else if ("c_run_other_dev".equals(tableName)) {//采集没有这张表
				primaryKeyName = "rec_id";
			} 
			flag=sync(tableName, tableName, primaryKeyName);
		}
		logger.debug(tableName+"的档案数据同步完成!");
		return flag;
	}
	
	
	/**
	 * 根据传入的表名,查询字段名,查询字段值等同步数据
	 * @param tableName  c_meter
	 * @param meter_id    meter_id
	 * @param meter_id    电能表ID
	 * @param tg_id       台区ID
	 * @flag           专变和公变标识
	 */
	@SuppressWarnings("finally")
	public boolean synCmeter(String tableName,String qKeyName,String qKeyValue, String value, String flag) {
		
		String cons_no = null;
		//专变
		if ("0".equals(flag)) {
			cons_no = value;
		} else {
			String querySql = "SELECT C.CONS_NO FROM C_MP A, C_METER_MP_RELA B, C_CONS C WHERE B.MP_ID = A.MP_ID AND A.CONS_ID = C.CONS_ID  AND B.METER_ID =?"; 
			List list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{qKeyValue});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				cons_no = StringUtil.removeNull(mapp.get("CONS_NO"));
			}
			//为空取T+台区编号
			if ("".equals(cons_no) || cons_no == null) {
				querySql = "SELECT TG_NO FROM G_TG WHERE TG_ID =?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{value});
				Map<?,?> map = (Map<?,?>) list.get(0);
				cons_no = "T"+(String) map.get("TG_NO");
			}
		}
		tableName=tableName.toLowerCase();
		//按照参数从中间数据库中读取数据
		String sql = "select * from " + tableName + " where "+ qKeyName + "=" + "'"+qKeyValue+"'";
		List<?> ls = this.getJdbcYXDAO().queryForList(sql);
		int i = 0;
		String mergerSql="";
		boolean blag=false;
		try{
			//把数据保存到采集数据库相对应的临时表中
			for(int j =0; j < ls.size(); j++){
				Map<?,?> map = (Map<?,?>)ls.get(j);
				BigDecimal meter_id =(BigDecimal)map.get("METER_ID");
				//取得表计资产号
				String asset_no = null;
				String querySql = "SELECT D.ASSET_NO FROM D_METER D WHERE D.METER_ID=?"; 
				List list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{qKeyValue});
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					asset_no = StringUtil.removeNull(mapp.get("ASSET_NO"));
				}
				String mp_id = ""; 
				String queryMpSql = "SELECT * FROM C_METER_MP_RELA C WHERE C.METER_ID=?"; 
				List mpList = this.getJdbcYXDAO().queryForList(queryMpSql, new Object[]{qKeyValue});
				if (null != mpList && mpList.size() > 0) {
					Map<?,?> mpMap = (Map<?,?>) mpList.get(0);
					mp_id = StringUtil.removeNull(mpMap.get("MP_ID"));
				}
				String org_no=StringUtil.removeNull(map.get("ORG_NO"));
				String area_no =StringUtil.subStr(org_no, 0, 7);
				String baudrate=StringUtil.removeNull(map.get("BAUDRATE"));
				String comm_no=StringUtil.removeNull(map.get("COMM_NO"));
				String comm_addr1=StringUtil.removeNull(map.get("COMM_ADDR1"));
			
				//表地址 和表通讯规约取自（采集点对象）
				querySql = "SELECT R.COMM_ADDR1 , R.COMM_PROT_NO FROM R_COLL_OBJ R WHERE R.METER_ID=?"; 
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{meter_id});
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					comm_addr1 = StringUtil.removeNull(mapp.get("COMM_ADDR1"));
					comm_no =  StringUtil.removeNull(mapp.get("COMM_PROT_NO"));
				}
				String comm_addr2=StringUtil.removeNull(map.get("COMM_ADDR2"));
				String comm_mode=StringUtil.removeNull(map.get("COMM_MODE"));
				String inst_loc=StringUtil.removeNull(map.get("INST_LOC"));
				Number t_factor=NumberUtil.removeNull(map.get("T_FACTOR"));
				String ref_meter_flag=StringUtil.removeNull(map.get("REF_METER_FLAG"));
				Number ref_meter_id=NumberUtil.removeNull(map.get("REF_METER_ID"));
				String validate_code=StringUtil.removeNull(map.get("VALIDATE_CODE"));
				String module_no=StringUtil.removeNull(map.get("MODULE_NO"));
				String mr_factor=StringUtil.removeNull(map.get("MR_FACTOR"));
//				Date last_chk_date=(Date)map.get("LAST_CHK_DATE");
				Number rotate_cycle=NumberUtil.removeNull(map.get("ROTATE_CYCLE"));
//				Date rotate_valid_date=(Date)map.get("ROTATE_VALID_DATE");
				Number chk_cycle=NumberUtil.removeNull(map.get("CHK_CYCLE"));
				String tmnl_asset_no="";
				String fmr_asset_no="";
				//取得终端资产号
				querySql = "SELECT N.TMNL_ASSET_NO FROM R_COLL_OBJ R, R_TMNL_RUN N WHERE R.CP_NO=N.CP_NO AND R.METER_ID=?"; 
				list = this.getJdbcDAO().queryForList(querySql, new Object[]{qKeyValue});
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					tmnl_asset_no = StringUtil.removeNull(mapp.get("TMNL_ASSET_NO"));
				}
				//取得采集器资产编号
				querySql = "SELECT R.COLLECTOR_ID FROM R_COLL_OBJ R WHERE R.METER_ID=? "; 
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{qKeyValue});
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					fmr_asset_no = StringUtil.removeNull(mapp.get("COLLECTOR_ID"));//取得colloctorId作为采集器的资产编号
				}
				Number reg_status=0;
				Number reg_sn=0;
				i++; 
				mergerSql=" MERGE INTO c_meter a USING " +
				" (SELECT '"+meter_id+"' AS meter_id,'"+asset_no+"' AS asset_no,'"+mp_id+"' AS mp_id,'"+org_no+"' AS org_no,'"+area_no+"' AS area_no,'"+cons_no+"' AS cons_no,'"+baudrate+"' AS baudrate,'"+comm_no+"' AS comm_no,'"+comm_addr1+"' AS comm_addr1,'"+comm_addr2+"' AS comm_addr2,'"+comm_mode+"' AS comm_mode,'"+inst_loc+"' AS inst_loc,'"+t_factor+"' AS t_factor,'"+ref_meter_flag+"' AS ref_meter_flag,'"+ref_meter_id+"' AS ref_meter_id,'"+validate_code+"' AS validate_code,'"+module_no+"' AS module_no,'"+mr_factor+"' AS mr_factor,'"+rotate_cycle+"' AS rotate_cycle,'"+chk_cycle+"' AS chk_cycle,'"+tmnl_asset_no+"' AS tmnl_asset_no,'"+fmr_asset_no+"' AS fmr_asset_no,'"+reg_status+"'  AS reg_status,'"+reg_sn+"' AS reg_sn FROM DUAL) b " +
				" ON (a.meter_id = b.meter_id) WHEN MATCHED THEN UPDATE SET asset_no=b.asset_no,mp_id=b.mp_id,org_no=b.org_no,area_no=b.area_no,cons_no=b.cons_no,baudrate=b.baudrate,comm_no=b.comm_no,comm_addr1=b.comm_addr1,comm_addr2=b.comm_addr2,comm_mode=b.comm_mode,inst_loc=b.inst_loc, " +
				" t_factor=b.t_factor,ref_meter_flag=b.ref_meter_flag,ref_meter_id=b.ref_meter_id,validate_code=b.validate_code,module_no=b.module_no,mr_factor=b.mr_factor,rotate_cycle=b.rotate_cycle, " +
				" chk_cycle=b.chk_cycle,tmnl_asset_no=b.tmnl_asset_no,fmr_asset_no=b.fmr_asset_no,reg_status=b.reg_status,reg_sn=b.reg_sn  " +
				" WHEN NOT MATCHED THEN INSERT (meter_id,asset_no,mp_id,org_no,area_no,cons_no,baudrate,comm_no,comm_addr1,comm_addr2,comm_mode,inst_loc,t_factor,ref_meter_flag,ref_meter_id,validate_code,module_no,mr_factor,rotate_cycle,chk_cycle,tmnl_asset_no,fmr_asset_no,reg_status,reg_sn)" +
				" VALUES (b.meter_id,b.asset_no,b.mp_id,b.org_no,b.area_no,b.cons_no,b.baudrate,b.comm_no,b.comm_addr1,b.comm_addr2,b.comm_mode,b.inst_loc,b.t_factor,b.ref_meter_flag,b.ref_meter_id,b.validate_code,b.module_no,b.mr_factor,b.rotate_cycle,b.chk_cycle,b.tmnl_asset_no,b.fmr_asset_no,b.reg_status,b.reg_sn)";
			
			}
			
			this.getJdbcDAO().update(mergerSql);
			 	
			blag=true;
		} catch (DataAccessException ex) {
			blag = false;
			ex.printStackTrace();
		}
		return blag;
	}
	
	/**
	 * 根据台区ID同步台区下的居民用户
	 * @param tgid
	 * @return
	 */
	@SuppressWarnings("finally")
	public boolean synCcons(String tgid, String tmnlID) {
		
		//按照参数从中间数据库中读取数据
		String sql = "select distinct t.cons_id from c_mp t where t.tg_id='"+tgid+"' and t.cons_id is not null ";
		List<?> ls = this.getJdbcYXDAO().queryForList(sql);
		String mergerSql="";
		boolean blag=false;
		try {
			int i = 0;
			String[] merge =  new String[ls.size()];
			
			//把数据保存到采集数据库相对应的临时表中
			for(int j = 0; j < ls.size(); j++){
				Map<?, ?> cmMap = (Map<?, ?>) ls.get(j);
				BigDecimal cons_id =(BigDecimal)cmMap.get("CONS_ID");
				
				String querySql = "SELECT * FROM C_CONS G WHERE G.CONS_ID=?"; 
				List<?> list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cons_id});
				
				String org_no="";
				String cons_no= "";
				String cons_name="";
				String cust_no="";
				String orgn_cons_no="";
				String elec_addr ="";
				String trade_code="";
				String contract_cap="";
				String elec_type_code="";
				Number run_cap=0;
				Number shift_no=0;
				Number lode_attr_code=0;
				String volt_code="";
				String hec_industry_code="";
				String holiday ="";
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String build_date ="";
				String status_code ="";
				String rrio_code ="";
				Number chk_cycle  =0;
				String last_chk_date =df.format(new Date());
				String checker_no ="";
				String poweroff_code ="";
				String transfer_code ="";
				String mr_sect_no = "";
				String note_type_code="";
				String tmp_flag="";
				String tmp_date="";
				String cust_id = "";
				if (list != null && list.size() > 0) {
					Map<?, ?> map = (Map<?, ?>) list.get(0);
					cust_id =StringUtil.removeNull(map.get("CUST_ID"));
					synByService("c_cust", "c_cust", "cust_id", "cust_id",cust_id);
					org_no=StringUtil.removeNull(map.get("ORG_NO"));
					cons_no= String.valueOf(map.get("CONS_NO"));
					cons_name=StringUtil.removeNull(map.get("CONS_NAME"));
					cust_no=StringUtil.removeNull(map.get("CUST_NO"));
					orgn_cons_no=StringUtil.removeNull(map.get("ORGN_CONS_NO"));
					elec_addr =StringUtil.removeNull(map.get("ELEC_ADDR"));
					trade_code=StringUtil.removeNull(map.get("TRADE_CODE"));
					contract_cap=StringUtil.removeNull(map.get("CONTRACT_CAP"));
					elec_type_code=StringUtil.removeNull(map.get("ELEC_TYPE_CODE"));
					run_cap=NumberUtil.removeNull(map.get("RUN_CAP"));
					shift_no=NumberUtil.removeNull(map.get("SHIFT_NO"));
					lode_attr_code=NumberUtil.removeNull(map.get("LODE_ATTR_CODE"));
					volt_code=StringUtil.removeNull(map.get("VOLT_CODE"));
					hec_industry_code=StringUtil.removeNull(map.get("HEC_INDUSTRY_CODE"));
					holiday =StringUtil.removeNull(map.get("HOLIDAY"));
					build_date =df.format((Date)map.get("BUILD_DATE")==null?new Date():(Date)map.get("BUILD_DATE"));
					status_code =StringUtil.removeNull(map.get("STATUS_CODE"));
					rrio_code =StringUtil.removeNull(map.get("RRIO_CODE"));
					chk_cycle  =0;
					last_chk_date =df.format(new Date());
					checker_no ="";
					poweroff_code =StringUtil.removeNull(map.get("POWEROFF_CODE"));
					transfer_code ="";
					mr_sect_no = StringUtil.removeNull(map.get("MR_SECT_NO"));
					note_type_code="";
					tmp_flag=StringUtil.removeNull(map.get("TMP_FLAG"));
					tmp_date=df.format((Date)map.get("TMP_DATE")==null?new Date():(Date)map.get("TMP_DATE"));
				}
				
				String subs_id="0";
				String tg_id="0";
				String line_id ="0";

				//中间库取得line_id. subs_id.,tg_id
				line_id = StringUtil.removeNull(cmMap.get("LINE_ID"));
				tg_id = tgid;
				querySql = "SELECT A.SUBS_ID FROM G_SUBS_LINE_RELA A WHERE A.LINE_ID=?"; 
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{line_id});                                                       
				if (list.iterator().hasNext() && list.iterator().next() != null)  {
					Map mapt = (Map<?, ?>) list.iterator().next();
					subs_id = StringUtil.removeNull(mapt.get("SUBS_ID"));
				}

				String cust_query_no="";
				String tmp_pay_rela_no="";
				String cons_type = "";
				
				//取得 用电用户类型

				String usageTypeCode = StringUtil.removeNull(cmMap.get("USAGE_TYPE_CODE"));
				String typeCode = StringUtil.removeNull(cmMap.get("TYPE_CODE"));
				String typeTmnlCode = "";
				
				//取得终端
				querySql = "SELECT G.TERMINAL_TYPE_CODE FROM R_TMNL_RUN G WHERE G.TERMINAL_ID=?"; 
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tmnlID});
				Map<?,?> tmnlMap = (Map<?,?>) list.get(0);
				typeTmnlCode = StringUtil.removeNull(tmnlMap.get("TERMINAL_TYPE_CODE"));
				
				//取得用户类型
				cons_type= this.getConsType(typeTmnlCode, usageTypeCode, typeCode);
				String cap_grade_no = this.getCapgrade(contract_cap);
				String cons_sort_code="0";
			
				String ps_date =df.format(new Date());
				String due_date =df.format(new Date());
				String notify_mode ="";
				String settle_mode ="";
				
				String area_no=StringUtil.subStr(org_no, 0, 7);
				String apply_no="";
				String apply_date=df.format(new Date());
				
				mergerSql="MERGE INTO c_cons a USING " +
				" (SELECT '"+cons_id+"' as cons_id, '"+cust_id+"' as cust_id ,'"+org_no+"' as org_no ,'"+cons_no+"' as cons_no ,'"+cons_name+"' as cons_name ,'"+cust_no+"' as cust_no ,'"+subs_id+"' as subs_id ,'"+tg_id+"' as tg_id ,'"+line_id+"' as line_id ,'"+cust_query_no+"' as cust_query_no ,'"+tmp_pay_rela_no+"' as tmp_pay_rela_no ,'"+orgn_cons_no+"' as orgn_cons_no ,'"+cons_sort_code+"' as cons_sort_code ,'"+elec_addr+"' as elec_addr ,'"+trade_code+"' as trade_code ,'"+cons_type+"' as cons_type ,'"+elec_type_code+"' as elec_type_code ,'"+contract_cap+"' as contract_cap ,'"+run_cap+"' as run_cap ,'"+cap_grade_no+"' as cap_grade_no ,'"+shift_no+"' as shift_no ,'"+lode_attr_code+"' as lode_attr_code ,'"+volt_code+"' as volt_code ,'"+hec_industry_code+"' as hec_industry_code ,'"+holiday+"' as holiday ,to_date('"+build_date+"','yyyy-mm-dd') as build_date ,to_date('"+ps_date+"','yyyy-mm-dd') as ps_date ,to_date('"+due_date+"','yyyy-mm-dd') as due_date ,'"+notify_mode+"' as notify_mode ,'"+settle_mode+"' as settle_mode ,'"+status_code+"' as status_code ,'"+rrio_code+"' as rrio_code ,'"+chk_cycle+"' as chk_cycle ,to_date('"+last_chk_date+"','yyyy-mm-dd') as last_chk_date ,'"+checker_no+"' as checker_no ,'"+poweroff_code+"' as poweroff_code ,'"+transfer_code+"' as transfer_code ,'"+mr_sect_no+"' as mr_sect_no ,'"+note_type_code+"' as note_type_code ,'"+tmp_flag+"' as tmp_flag ,to_date('"+tmp_date+"','yyyy-mm-dd') as tmp_date ,'"+area_no+"' as area_no,'"+apply_no+"' as apply_no,to_date('"+apply_date+"','yyyy-mm-dd') as apply_date FROM dual) b " +
				" ON (a.cons_id = b.cons_id) WHEN MATCHED THEN UPDATE SET cust_id =b.cust_id ,org_no =b.org_no ,cons_no =b.cons_no ,cons_name =b.cons_name ,cust_no =b.cust_no ,subs_id =b.subs_id ,tg_id =b.tg_id ,line_id =b.line_id ,cust_query_no =b.cust_query_no ,tmp_pay_rela_no =b.tmp_pay_rela_no ,orgn_cons_no =b.orgn_cons_no ,cons_sort_code =b.cons_sort_code ,elec_addr =b.elec_addr ,trade_code =b.trade_code ,cons_type =b.cons_type ,elec_type_code =b.elec_type_code ,contract_cap =b.contract_cap ,run_cap =b.run_cap ,cap_grade_no =b.cap_grade_no ,shift_no =b.shift_no ,lode_attr_code =b.lode_attr_code ,volt_code =b.volt_code ,hec_industry_code =b.hec_industry_code ,holiday =b.holiday ,build_date =b.build_date ,ps_date =b.ps_date ,due_date =b.due_date ,notify_mode =b.notify_mode ,settle_mode =b.settle_mode ,status_code =b.status_code ,rrio_code =b.rrio_code ,chk_cycle =b.chk_cycle ,last_chk_date =b.last_chk_date ,checker_no =b.checker_no ,poweroff_code=b.poweroff_code ,transfer_code =b.transfer_code ,mr_sect_no =b.mr_sect_no ,note_type_code =b.note_type_code ,tmp_flag =b.tmp_flag ,tmp_date =b.tmp_date ,area_no =b.area_no ,apply_no =b.apply_no ,apply_date =b.apply_date " +
				" WHEN NOT MATCHED THEN INSERT (cons_id, cust_id ,org_no ,cons_no ,cons_name ,cust_no ,subs_id ,tg_id ,line_id ,cust_query_no ,tmp_pay_rela_no ,orgn_cons_no ,cons_sort_code ,elec_addr ,trade_code ,cons_type ,elec_type_code ,contract_cap ,run_cap ,cap_grade_no ,shift_no ,lode_attr_code ,volt_code ,hec_industry_code ,holiday ,build_date ,ps_date ,due_date ,notify_mode ,settle_mode ,status_code ,rrio_code ,chk_cycle ,last_chk_date ,checker_no ,poweroff_code ,transfer_code ,mr_sect_no ,note_type_code ,tmp_flag ,tmp_date ,area_no,apply_no,apply_date)" +
				" VALUES (b.cons_id, b.cust_id ,b.org_no ,b.cons_no ,b.cons_name ,b.cust_no ,b.subs_id ,b.tg_id ,b.line_id ,b.cust_query_no ,b.tmp_pay_rela_no ,b.orgn_cons_no ,b.cons_sort_code ,b.elec_addr ,b.trade_code ,b.cons_type ,b.elec_type_code ,b.contract_cap ,b.run_cap ,b.cap_grade_no ,b.shift_no ,b.lode_attr_code ,b.volt_code ,b.hec_industry_code ,b.holiday ,b.build_date ,b.ps_date ,b.due_date ,b.notify_mode ,b.settle_mode ,b.status_code ,b.rrio_code ,b.chk_cycle ,b.last_chk_date ,b.checker_no ,b.poweroff_code ,b.transfer_code ,b.mr_sect_no ,b.note_type_code ,b.tmp_flag ,b.tmp_date ,b.area_no,b.apply_no,b.apply_date)";
				
				merge[i] = mergerSql;
				i++;
			}
			this.getJdbcDAO().batchUpdate(merge);
			 
			blag=true;
		} catch (DataAccessException ex) {
			blag = false;
			ex.printStackTrace();
		}
		return blag;
	}
	
	/**
	 * 根据传入的表名,查询字段名,查询字段值等同步数据
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("finally")
	public boolean synDataQuery(String tableName,String qKeyName,String qKeyValue) {
		
		tableName=tableName.toLowerCase();

		String[] array = null;
		String sql = null;
		//
		if ("c_mp".equals(tableName)) {
			array = qKeyValue.split("/");//mp_id   
			sql = "select * from " + tableName + " where "+ qKeyName + "=" + "'"+array[0]+"'";
		} else {
			//按照参数从中间数据库中读取数据
			sql = "select * from " + tableName + " where "+ qKeyName + "=" + "'"+qKeyValue+"'";
		}
		List<?> ls = this.getJdbcYXDAO().queryForList(sql);
		int i = 0;
		String mergerSql="";

		boolean blag=false;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String[] merge = new String[ls.size()];
		try{
			//4.1.4.1计量点信息/c_mp
			if("c_mp".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					BigDecimal mp_id =(BigDecimal)map.get("MP_ID");
					String mp_sect_id ="";
					String mp_no =StringUtil.removeNull(map.get("MP_NO"));
					String mp_name=StringUtil.removeNull(map.get("MP_NAME"));
					String org_no=StringUtil.removeNull(map.get("ORG_NO"));
					String cons_no= "";
					//取得表ID
					String querySql = "SELECT * FROM C_METER C WHERE C.METER_ID=?"; 
					List list = this.getJdbcDAO().queryForList(querySql, new Object[]{array[1]});
					if (null != list && list.size() > 0) {
						Map<?,?> consmap = (Map<?,?>) list.get(0);
						cons_no = StringUtil.removeNull(consmap.get("CONS_NO"));
					}
					String line_id=StringUtil.removeNull(map.get("LINE_ID"));
					String tg_id=StringUtil.removeNull(map.get("TG_ID"));	
					String cons_id=array[2];
					String mr_sect_no=StringUtil.removeNull(map.get("MR_SECT_NO"));
					String mp_addr=StringUtil.removeNull(map.get("MP_ADDR"));
					String type_code=StringUtil.removeNull(map.get("TYPE_CODE"));
					String mp_attr_code=StringUtil.removeNull(map.get("MP_ATTR_CODE"));
					String usage_type_code=StringUtil.removeNull(map.get("USAGE_TYPE_CODE"));
					String side_code=StringUtil.removeNull(map.get("SIDE_CODE"));
					String volt_code=StringUtil.removeNull(map.get("VOLT_CODE"));
					String app_date=df.format((Date)map.get("APP_DATE")==null?new Date():(Date)map.get("APP_DATE"));
					String run_date=df.format((Date)map.get("RUN_DATE")==null?new Date():(Date)map.get("RUN_DATE"));
					String wiring_mode=StringUtil.removeNull(map.get("WIRING_MODE"));
					String meas_mode=StringUtil.removeNull(map.get("MEAS_MODE"));
					String switch_no=StringUtil.removeNull(map.get("SWITCH_NO"));
					String exchg_type_code=StringUtil.removeNull(map.get("EXCHG_TYPE_CODE"));
					String md_type_code=StringUtil.removeNull(map.get("MD_TYPE_CODE"));
					Number mr_sn=NumberUtil.removeNull(map.get("MR_SN"));
					Number mp_sn=NumberUtil.removeNull(map.get("MP_SN"));
					String meter_flag=StringUtil.removeNull(map.get("METER_FLAG"));
					String status_code=StringUtil.removeNull(map.get("STATUS_CODE"));
					String lc_flag =StringUtil.removeNull(map.get("LC_FLAG"));
					String earth_mode="";

					i++; 
					mergerSql="MERGE INTO c_mp a USING " +
					" (SELECT '"+mp_id+"' AS mp_id,'"+mp_sect_id+"' as mp_sect_id,'"+mp_no+"' as mp_no, '"+mp_name+"' as mp_name,'"+org_no+"' as org_no,'"+cons_id+"' as cons_id,'"+cons_no+"' as cons_no,'"+line_id+"' as line_id,'"+tg_id+"' as tg_id,'"+mr_sect_no+"' as mr_sect_no,'"+mp_addr+"' as mp_addr,'"+type_code+"' as type_code,'"+mp_attr_code+"' as mp_attr_code,'"+usage_type_code+"' as usage_type_code,'"+side_code+"' as side_code,'"+volt_code+"' as volt_code,to_date('"+app_date+"','yyyy-mm-dd') as app_date,to_date('"+run_date+"','yyyy-mm-dd') as run_date,'"+wiring_mode+"' as wiring_mode,'"+meas_mode+"' as meas_mode,'"+switch_no+"' as switch_no,'"+exchg_type_code+"' as exchg_type_code,'"+md_type_code+"' as md_type_code,'"+mr_sn+"' as mr_sn,'"+mp_sn+"' as mp_sn, '"+meter_flag+"' as meter_flag,'"+status_code+"' as  status_code,'"+lc_flag+"' as lc_flag,'"+earth_mode+"' as earth_mode FROM dual) b " +
					" ON (a.mp_id = b.mp_id) WHEN MATCHED THEN UPDATE SET mp_sect_id=b.mp_sect_id, mp_no= b.mp_no, mp_name= b.mp_name, org_no= b.org_no,cons_id= b.cons_id, cons_no= b.cons_no, line_id = b.line_id, " +
					" tg_id = b.tg_id, mr_sect_no= b.mr_sect_no,mp_addr= b.mp_addr, type_code= b.type_code, mp_attr_code= b.mp_attr_code, usage_type_code = b.usage_type_code, " +
					" side_code = b.side_code, volt_code = b.volt_code , app_date = b.app_date, run_date = b.run_date, wiring_mode = b.wiring_mode," +
					" meas_mode = b.meas_mode, switch_no = b.switch_no, exchg_type_code = b.exchg_type_code, md_type_code = b.md_type_code, mr_sn = b.mr_sn, mp_sn = b.mp_sn, meter_flag = b.meter_flag, status_code = b.status_code, lc_flag = b.lc_flag, earth_mode = b.earth_mode " +
					" WHEN NOT MATCHED THEN INSERT (mp_id,mp_sect_id,mp_no,mp_name,org_no,cons_id,cons_no,line_id,tg_id,mr_sect_no,mp_addr,type_code,mp_attr_code,usage_type_code,side_code,volt_code,app_date,run_date,wiring_mode,meas_mode,switch_no,exchg_type_code,md_type_code,mr_sn,mp_sn,meter_flag,status_code,lc_flag,earth_mode)" +
					" VALUES (b.mp_id,b.mp_sect_id,b.mp_no,b.mp_name,b.org_no,b.cons_id,b.cons_no,b.line_id,b.tg_id,b.mr_sect_no,b.mp_addr,b.type_code,b.mp_attr_code,b.usage_type_code,b.side_code,b.volt_code,b.app_date,b.run_date,b.wiring_mode,b.meas_mode,b.switch_no,b.exchg_type_code,b.md_type_code,b.mr_sn,b.mp_sn,b.meter_flag,b.status_code,b.lc_flag,b.earth_mode)";
				}
				this.getJdbcDAO().update(mergerSql);
				blag=true;
			// 同步----4.1.3.2用户基本信息/c_cons
			}else if("c_cons".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					BigDecimal cons_id =(BigDecimal)map.get("CONS_ID");
					String cust_id =StringUtil.removeNull(map.get("CUST_ID"));
					String org_no=StringUtil.removeNull(map.get("ORG_NO"));
					String cons_no= String.valueOf(map.get("CONS_NO"));
					String cons_name=StringUtil.removeNull(map.get("CONS_NAME"));
					String cust_no=StringUtil.removeNull(map.get("CUST_NO"));
					String subs_id="0";
					String tg_id="0";
					String line_id ="0";
					//中间库取得line_id. subs_id.,tg_id
					String querySql = "SELECT * FROM C_PS G WHERE G.CONS_ID=?"; 
					List list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{qKeyValue});
					if (list != null && list.size() > 0) {
						Map<?, ?> mapp = (Map<?, ?>) list.get(0);
						line_id = StringUtil.removeNull(mapp.get("LINE_ID"));
						tg_id = StringUtil.removeNull(mapp.get("TG_ID"));
						subs_id = StringUtil.removeNull(mapp.get("SUBS_ID"));
					}
					String cust_query_no="";
					String tmp_pay_rela_no="";
					String orgn_cons_no=StringUtil.removeNull(map.get("ORGN_CONS_NO"));
					String elec_addr =StringUtil.removeNull(map.get("ELEC_ADDR"));
					String trade_code=StringUtil.removeNull(map.get("TRADE_CODE"));
					String contract_cap=StringUtil.removeNull(map.get("CONTRACT_CAP"));
					String cons_type = "";
					//取得 用电用户类型
					querySql = "SELECT * FROM C_MP G WHERE G.CONS_ID=?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{qKeyValue});
					if (null != list && list.size() > 0) {
						Map<?, ?> mapp = (Map<?, ?>) list.get(0);
						String usageTypeCode = StringUtil.removeNull(mapp.get("USAGE_TYPE_CODE"));
						String typeCode = StringUtil.removeNull(mapp.get("TYPE_CODE"));
						String tmnlTypeCode = "";
						
						querySql = " SELECT r.terminal_type_code from r_tmnl_run r , r_cp_cons_rela rela WHERE rela.cons_id=? AND rela.cp_no=r.cp_no";
						List listtmnl = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cons_id});
						Map<?,?> tmnlMap = null;
						if (null != listtmnl && listtmnl.size() > 0) {
							tmnlMap = (Map<?, ?>) listtmnl.get(0);
							tmnlTypeCode = StringUtil.removeNull(tmnlMap.get("TERMINAL_TYPE_CODE"));
						}
						cons_type= this.getConsType(tmnlTypeCode, usageTypeCode, typeCode);
					}
					String cap_grade_no = this.getCapgrade(contract_cap);
					String cons_sort_code="0"; 
					String elec_type_code=StringUtil.removeNull(map.get("ELEC_TYPE_CODE"));
					Number run_cap=NumberUtil.removeNull(map.get("RUN_CAP"));
					Number shift_no=NumberUtil.removeNull(map.get("SHIFT_NO"));
					Number lode_attr_code=NumberUtil.removeNull(map.get("LODE_ATTR_CODE"));
					String volt_code=StringUtil.removeNull(map.get("VOLT_CODE"));
					String hec_industry_code=StringUtil.removeNull(map.get("HEC_INDUSTRY_CODE"));
					String holiday =StringUtil.removeNull(map.get("HOLIDAY"));
					String build_date = df.format(new Date());  
					String ps_date =df.format(new Date());
					String due_date =df.format(new Date());
					String notify_mode ="";
					String settle_mode ="";
					String status_code =StringUtil.removeNull(map.get("STATUS_CODE"));
					String rrio_code =StringUtil.removeNull(map.get("RRIO_CODE"));
					Number chk_cycle  =0;
					String last_chk_date = df.format(new Date());
					String checker_no ="";
					String poweroff_code =StringUtil.removeNull(map.get("POWEROFF_CODE")); 
					String transfer_code ="";
					String mr_sect_no =StringUtil.removeNull(map.get("MR_SECT_NO"));
					String note_type_code="";
					String tmp_flag=StringUtil.removeNull(map.get("TMP_FLAG")); 
					String tmp_date=df.format((Date)map.get("TMP_DATE")==null?new Date():(Date)map.get("TMP_DATE"));
					String area_no=StringUtil.subStr(org_no, 0, 7);
					String apply_no="";
					String apply_date=df.format(new Date());
					i++; 
					mergerSql="MERGE INTO c_cons a USING " +
					" (SELECT '"+cons_id+"' as cons_id, '"+cust_id+"' as cust_id ,'"+org_no+"' as org_no ,'"+cons_no+"' as cons_no ,'"+cons_name+"' as cons_name ,'"+cust_no+"' as cust_no ,'"+subs_id+"' as subs_id ,'"+tg_id+"' as tg_id ,'"+line_id+"' as line_id ,'"+cust_query_no+"' as cust_query_no ,'"+tmp_pay_rela_no+"' as tmp_pay_rela_no ,'"+orgn_cons_no+"' as orgn_cons_no ,'"+cons_sort_code+"' as cons_sort_code ,'"+elec_addr+"' as elec_addr ,'"+trade_code+"' as trade_code ,'"+cons_type+"' as cons_type ,'"+elec_type_code+"' as elec_type_code ,'"+contract_cap+"' as contract_cap ,'"+run_cap+"' as run_cap ,'"+cap_grade_no+"' as cap_grade_no ,'"+shift_no+"' as shift_no ,'"+lode_attr_code+"' as lode_attr_code ,'"+volt_code+"' as volt_code ,'"+hec_industry_code+"' as hec_industry_code ,'"+holiday+"' as holiday ,to_date('"+build_date+"','yyyy-mm-dd') as build_date ,to_date('"+ps_date+"','yyyy-mm-dd') as ps_date ,to_date('"+due_date+"','yyyy-mm-dd') as due_date ,'"+notify_mode+"' as notify_mode ,'"+settle_mode+"' as settle_mode ,'"+status_code+"' as status_code ,'"+rrio_code+"' as rrio_code ,'"+chk_cycle+"' as chk_cycle ,to_date('"+last_chk_date+"','yyyy-mm-dd') as last_chk_date ,'"+checker_no+"' as checker_no ,'"+poweroff_code+"' as poweroff_code ,'"+transfer_code+"' as transfer_code ,'"+mr_sect_no+"' as mr_sect_no ,'"+note_type_code+"' as note_type_code ,'"+tmp_flag+"' as tmp_flag ,to_date('"+tmp_date+"','yyyy-mm-dd') as tmp_date ,'"+area_no+"' as area_no,'"+apply_no+"' as apply_no,to_date('"+apply_date+"','yyyy-mm-dd') as apply_date FROM dual) b " +
					" ON (a.cons_id = b.cons_id) WHEN MATCHED THEN UPDATE SET cust_id =b.cust_id ,org_no =b.org_no ,cons_no =b.cons_no ,cons_name =b.cons_name ,cust_no =b.cust_no ,subs_id =b.subs_id ,tg_id =b.tg_id ,line_id =b.line_id ,cust_query_no =b.cust_query_no ,tmp_pay_rela_no =b.tmp_pay_rela_no ,orgn_cons_no =b.orgn_cons_no ,cons_sort_code =b.cons_sort_code ,elec_addr =b.elec_addr ,trade_code =b.trade_code ,cons_type =b.cons_type ,elec_type_code =b.elec_type_code ,contract_cap =b.contract_cap ,run_cap =b.run_cap ,cap_grade_no =b.cap_grade_no ,shift_no =b.shift_no ,lode_attr_code =b.lode_attr_code ,volt_code =b.volt_code ,hec_industry_code =b.hec_industry_code ,holiday =b.holiday ,build_date =b.build_date ,ps_date =b.ps_date ,due_date =b.due_date ,notify_mode =b.notify_mode ,settle_mode =b.settle_mode ,status_code =b.status_code ,rrio_code =b.rrio_code ,chk_cycle =b.chk_cycle ,last_chk_date =b.last_chk_date ,checker_no =b.checker_no ,poweroff_code=b.poweroff_code ,transfer_code =b.transfer_code ,mr_sect_no =b.mr_sect_no ,note_type_code =b.note_type_code ,tmp_flag =b.tmp_flag ,tmp_date =b.tmp_date ,area_no =b.area_no ,apply_no =b.apply_no ,apply_date =b.apply_date " +
					" WHEN NOT MATCHED THEN INSERT (cons_id, cust_id ,org_no ,cons_no ,cons_name ,cust_no ,subs_id ,tg_id ,line_id ,cust_query_no ,tmp_pay_rela_no ,orgn_cons_no ,cons_sort_code ,elec_addr ,trade_code ,cons_type ,elec_type_code ,contract_cap ,run_cap ,cap_grade_no ,shift_no ,lode_attr_code ,volt_code ,hec_industry_code ,holiday ,build_date ,ps_date ,due_date ,notify_mode ,settle_mode ,status_code ,rrio_code ,chk_cycle ,last_chk_date ,checker_no ,poweroff_code ,transfer_code ,mr_sect_no ,note_type_code ,tmp_flag ,tmp_date ,area_no,apply_no,apply_date)" +
					" VALUES (b.cons_id, b.cust_id ,b.org_no ,b.cons_no ,b.cons_name ,b.cust_no ,b.subs_id ,b.tg_id ,b.line_id ,b.cust_query_no ,b.tmp_pay_rela_no ,b.orgn_cons_no ,b.cons_sort_code ,b.elec_addr ,b.trade_code ,b.cons_type ,b.elec_type_code ,b.contract_cap ,b.run_cap ,b.cap_grade_no ,b.shift_no ,b.lode_attr_code ,b.volt_code ,b.hec_industry_code ,b.holiday ,b.build_date ,b.ps_date ,b.due_date ,b.notify_mode ,b.settle_mode ,b.status_code ,b.rrio_code ,b.chk_cycle ,b.last_chk_date ,b.checker_no ,b.poweroff_code ,b.transfer_code ,b.mr_sect_no ,b.note_type_code ,b.tmp_flag ,b.tmp_date ,b.area_no,b.apply_no,b.apply_date)";
				}
				this.getJdbcDAO().update(mergerSql);
				blag=true;
			// 同步----4.1.4.2采集点信息/r_cp
			}else if("r_cp".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					String cp_no =StringUtil.removeNull(map.get("CP_NO"));
					String name =StringUtil.removeNull(map.get("NAME"));
					String cp_type_code=StringUtil.removeNull(map.get("CP_TYPE_CODE"));
					String status_code=StringUtil.removeNull(map.get("STATUS_CODE"));
					String cp_addr=StringUtil.removeNull(map.get("CP_ADDR"));
					String gps_longitude=StringUtil.removeNull(map.get("GPS_LONGITUDE"));
					String gps_latitude=StringUtil.removeNull(map.get("GPS_LATITUDE"));
					
					i++; 
					mergerSql="MERGE INTO r_cp a USING " +
					" (SELECT '"+cp_no+"' as cp_no, '"+name+"' as name ,'"+cp_type_code+"' as cp_type_code ,'"+status_code+"' as status_code ,'"+cp_addr+"' as cp_addr ,'"+gps_longitude+"' as gps_longitude ,'"+gps_latitude+"' as gps_latitude FROM dual ) b " +
					" ON (a.cp_no = b.cp_no) WHEN MATCHED THEN UPDATE SET name =b.name ,cp_type_code =b.cp_type_code ,status_code =b.status_code ,cp_addr =b.cp_addr ,gps_longitude =b.gps_longitude ,gps_latitude =b.gps_latitude  " +
					" WHEN NOT MATCHED THEN INSERT (cp_no, name ,cp_type_code ,status_code ,cp_addr ,gps_longitude ,gps_latitude)" +
					" VALUES (b.cp_no, b.name ,b.cp_type_code ,b.status_code ,b.cp_addr ,b.gps_longitude ,b.gps_latitude)";
				}
				this.getJdbcDAO().update(mergerSql);
				blag=true;
			// 同步----4.1.4.3终端资产信息/d_lc_equip
			}else if("d_lc_equip".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					BigDecimal id = (BigDecimal)(map.get("ID"));
					String pr_org ="";
					String dept_no ="";
					String asset_no =StringUtil.removeNull(map.get("ASSET_NO"));
					String model_code =StringUtil.removeNull(map.get("MODEL_CODE"));
					String manufacturer =StringUtil.removeNull(map.get("MANUFACTURER"));
					String type_code =StringUtil.removeNull(map.get("TYPE_CODE"));
					String coll_mode =StringUtil.removeNull(map.get("COLL_MODE"));
					String mp_id =StringUtil.removeNull(map.get("MP_ID"));
					String attach_meter_flag =StringUtil.removeNull(map.get("ATTACH_METER_FLAG"));
					String deliver_date =df.format((Date)map.get("DELIVER_DATE")==null?new Date():(Date)map.get("DELIVER_DATE"));
					String deliver_remark ="";
					String made_no =StringUtil.removeNull(map.get("MADE_NO"));
					String made_date =df.format(new Date());
					String lot_id =StringUtil.removeNull(map.get("LOT_ID"));
					String remove_operator_no  =StringUtil.removeNull(map.get("REMOVE_OPERATOR_NO"));
					String rmv_date =df.format((Date)map.get("RMV_DATE")==null?new Date():(Date)map.get("RMV_DATE"));
					String doc_creator_no  ="";
					String doc_create_date =df.format(new Date());
					String installer_no =StringUtil.removeNull(map.get("INSTALLER_NO"));
					String checker_no ="";
					String inst_date =df.format((Date)map.get("INST_DATE")==null?new Date():(Date)map.get("INST_DATE"));
					String chk_date =df.format(new Date());
					String descard_operator_no  ="";
					String descard_date =df.format(new Date());
					String cur_status_code =StringUtil.removeNull(map.get("CUR_STATUS_CODE"));
					String person_resp_no =StringUtil.removeNull(map.get("PERSON_RESP_NO"));
					String tel =StringUtil.removeNull(map.get("TEL"));
					Number life_time =NumberUtil.removeNull(map.get("LIFE_TIME"));
					String remark =StringUtil.removeNull(map.get("REMARK"));
					String box_bar_code =StringUtil.removeNull(map.get("BOX_BAR_CODE"));
					Number store_loc_id =0;
					Number wh_id =0;
					Number wh_area_id =0;
					Number store_area_id=0;
	
					i++; 
					mergerSql="MERGE INTO d_lc_equip a USING " +
					" (SELECT '"+id+"' as id ,'"+pr_org+"' as pr_org ,'"+dept_no+"' as dept_no ,'"+asset_no+"' as asset_no ,'"+model_code+"' as model_code ,'"+manufacturer+"' as manufacturer ,'"+type_code+"' as type_code ,'"+coll_mode+"' as coll_mode ,'"+mp_id+"' as mp_id ,'"+attach_meter_flag+"' as attach_meter_flag ,to_date('"+deliver_date+"','yyyy-mm-dd') as deliver_date ,'"+deliver_remark+"' as deliver_remark ,'"+made_no+"' as made_no ,to_date('"+made_date+"','yyyy-mm-dd') as made_date ,'"+lot_id+"' as lot_id ,'"+remove_operator_no+"' as remove_operator_no, to_date('"+rmv_date+"','yyyy-mm-dd') as rmv_date ,'"+doc_creator_no+"' as doc_creator_no ,to_date('"+doc_create_date+"','yyyy-mm-dd') as doc_create_date ,'"+installer_no+"' as installer_no ,'"+checker_no+"' as checker_no ,to_date('"+inst_date+"','yyyy-mm-dd') as inst_date ,to_date('"+chk_date+"','yyyy-mm-dd') as chk_date ,'"+descard_operator_no+"' as descard_operator_no ,to_date('"+descard_date+"','yyyy-mm-dd') as descard_date ,'"+cur_status_code+"' as cur_status_code ,'"+person_resp_no+"' as person_resp_no ,'"+tel+"' as tel ,'"+life_time+"' as life_time ,'"+remark+"' as remark ,'"+box_bar_code+"' as box_bar_code ,'"+store_loc_id+"' as store_loc_id ,'"+wh_id+"' as wh_id ,'"+wh_area_id+"' as wh_area_id ,'"+store_area_id+"' as store_area_id FROM dual) b " +
					" ON (a.id = b.id) WHEN MATCHED THEN UPDATE SET pr_org =b.pr_org ,dept_no =b.dept_no ,asset_no =b.asset_no ,model_code =b.model_code ," +
					" manufacturer =b.manufacturer ,type_code =b.type_code ,coll_mode =b.coll_mode ,mp_id =b.mp_id ,attach_meter_flag =b.attach_meter_flag ,deliver_date =b.deliver_date ," +
					" deliver_remark =b.deliver_remark ,made_no =b.made_no ,made_date =b.made_date ,lot_id =b.lot_id ,remove_operator_no =b.remove_operator_no ,rmv_date =b.rmv_date ," +
					" doc_creator_no =b.doc_creator_no ,doc_create_date =b.doc_create_date ,installer_no =b.installer_no ,checker_no =b.checker_no ,inst_date =b.inst_date ,chk_date =b.chk_date ," +
					" descard_operator_no =b.descard_operator_no ,descard_date =b.descard_date ,cur_status_code =b.cur_status_code ,person_resp_no =b.person_resp_no ,tel =b.tel ,life_time =b.life_time ," +
					" remark =b.remark ,box_bar_code =b.box_bar_code ,store_loc_id =b.store_loc_id ,wh_id =b.wh_id ,wh_area_id =b.wh_area_id ,store_area_id =b.store_area_id  " +
					" WHEN NOT MATCHED THEN INSERT (id ,pr_org ,dept_no ,asset_no ,model_code ,manufacturer ,type_code ,coll_mode ,mp_id ,attach_meter_flag ,deliver_date ,deliver_remark ,made_no ,made_date ,lot_id ,remove_operator_no, rmv_date ,doc_creator_no ,doc_create_date ,installer_no ,checker_no ,inst_date ,chk_date ,descard_operator_no ,descard_date ,cur_status_code ,person_resp_no ,tel ,life_time ,remark ,box_bar_code ,store_loc_id ,wh_id ,wh_area_id ,store_area_id)" +
					" VALUES (b.id ,b.pr_org ,b.dept_no ,b.asset_no ,b.model_code ,b.manufacturer ,b.type_code ,b.coll_mode ,b.mp_id ,b.attach_meter_flag ,b.deliver_date ,b.deliver_remark ,b.made_no ,b.made_date ,b.lot_id ,b.remove_operator_no, b.rmv_date ,b.doc_creator_no ,b.doc_create_date ,b.installer_no ,b.checker_no ,b.inst_date ,b.chk_date ,b.descard_operator_no ,b.descard_date ,b.cur_status_code ,b.person_resp_no ,b.tel ,b.life_time ,b.remark ,b.box_bar_code ,b.store_loc_id ,b.wh_id ,b.wh_area_id ,b.store_area_id)";
				}
				this.getJdbcDAO().update(mergerSql);
				blag=true;
			// 同步----4.1.4.4运行终端信息/r_tmnl_run
			}else if("r_tmnl_run".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?, ?> map = (Map<?, ?>) ls.get(j);
					String  terminal_id =StringUtil.removeNull(map.get("TERMINAL_ID"));        
					String  cp_no =StringUtil.removeNull(map.get("CP_NO"));              
					String  terminal_addr =StringUtil.removeNull(map.get("TERMINAL_ADDR"));      
					String  tmnl_asset_no = terminal_id;
					String  cis_asset_no=""; 
					String querySql = "SELECT * FROM D_LC_EQUIP D WHERE D.ID=?"; 
					List list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{terminal_id});
					if (list != null && list.size() > 0) {
						Map<?, ?> mapp = (Map<?, ?>) list.get(0);
						cis_asset_no = StringUtil.removeNull(mapp.get("ASSET_NO"));
					}
					//sim卡号
					String  sim_no ="";  
					String simSql = " SELECT d.* FROM C_RUN_OTHER_DEV c INNER JOIN D_OTHER_DEV d ON c.RELA_ID=? AND c.EQUIP_ID=d.ID ";
					List<?> simLst = this.getJdbcYXDAO().queryForList(simSql, new Object[]{simSql});
					if (simLst != null && simLst.size() > 0) {
						Map<?,?> simMap = (Map<?,?>) simLst.get(0);
						sim_no = StringUtil.removeNull(simMap.get("MADE_NO"));
					}
					
//					String  id =StringUtil.removeNull(map.get("ID"));                 
					String  factory_code =StringUtil.removeNull(map.get("FACTORY_CODE"));       
					String  attach_meter_flag = "0";  
					String searchflag = " SELECT d.* FROM D_METER D INNER JOIN R_COLL_OBJ R ON R.CP_NO = ? AND R.METER_ID = D.METER_ID AND D.ATTACHEQUIP_TYPE_CODE IS NOT NULL ";
					List<?> flagLst = this.getJdbcYXDAO().queryForList(searchflag, new Object[]{cp_no});
					if (flagLst != null && flagLst.size() > 0) {
						attach_meter_flag = "1";
					}
					
//					String  comm_password =""; 
					String  terminal_type_code =StringUtil.removeNull(map.get("TERMINAL_TYPE_CODE"));  
					String  coll_mode =StringUtil.removeNull(map.get("COLL_MODE"));
//					if ("03".equals(coll_mode)) {
//						coll_mode = "02";
//					}
					//取得通讯规约
					String  protocol_code = this.getChTmnlProtocol(StringUtil.removeNull(map.get("PROTOCOL_CODE")));
					if ("".equals(protocol_code) || protocol_code == null) {
						if ("01".equals(terminal_type_code) || "02".equals(terminal_type_code)) {
							protocol_code = "1";
						} else if (!"08".equals(terminal_type_code)) {
							protocol_code = "5";// terminal_type_code为01、02表示05规约,为03~07表示698规约,为08表示关口？
						}	
					}
					Object run_date = null;
					if (map.get("RUN_DATE") == null || "".equals(map.get("RUN_DATE"))) {
						run_date =  new Date();
					} else {
						run_date  =(Date)map.get("RUN_DATE");
					}
					String  status_code = "02".equals(StringUtil.removeNull(map.get("STATUS_CODE"))) ? "02" : "04" ;         
//					String  harmonic_dev_flag =StringUtil.removeNull(map.get("HARMONIC_DEV_FLAG"));  
//					String  ps_ensure_flag  =StringUtil.removeNull(map.get("PS_ENSURE_FLAG"));    
//					String  ac_sampling_flag =StringUtil.removeNull(map.get("AC_SAMPLING_FLAG"));
//				  	String  eliminate_flag =StringUtil.removeNull(map.get("ELIMINATE_FLAG"));
//				  	String  gate_attr_flag =StringUtil.removeNull(map.get("GATE_ATTR_FLAG"));
//				  	String  prio_ps_mode =StringUtil.removeNull(map.get("PRIO_PS_MODE"));
				  	String  freeze_mode  = StringUtil.removeNull(map.get("FREEZE_MODE") == null ? "01" : map.get("FREEZE_MODE"));//01定时冻结：20100517前置机部分要求更改。
				  	Number  freeze_cycle_num = 0;
				  	if ("5".equals(protocol_code)) {
				  		freeze_cycle_num = 24;
				  	} else {
				  		String freezeSql = " SELECT C.CAP_GRADE_NO FROM C_CONS C INNER JOIN R_CONS_TMNL_RELA R ON R.TMNL_ASSET_NO=? AND C.CONS_NO=R.CONS_NO  ";
				  		List<?> freezelst = this.getJdbcDAO().queryForList(freezeSql, new Object[]{terminal_id});
				  		if (freezelst != null && freezelst.size() > 0) {
				  			Map<?,?> freezeMap = (Map<?,?>) freezelst.get(0);
					  		String capGradeNo = StringUtil.removeNull(freezeMap.get("CAP_GRADE_NO"));
					  		if ("00".equals(capGradeNo) || "01".equals(capGradeNo)) {
					  			freeze_cycle_num = 24;
					  		} else if ("02".equals(capGradeNo)) {
					  			freeze_cycle_num = 48;
					  		} else if ("03".equals(capGradeNo) || "04".equals(capGradeNo)) {
					  			freeze_cycle_num = 96;
					  		}
				  		}
				  	}
//				  	Number  max_load_curve_days =NumberUtil.removeNull(map.get("MAX_LOAD_CURVE_DAYS"));
//				  	Number  ps_line_len   =NumberUtil.removeNull(map.get("PS_LINE_LEN"));      
//				  	String  work_ps  =StringUtil.removeNull(map.get("WORK_PS"));          
//				  	String  speaker_flag =StringUtil.removeNull(map.get("SPEAKER_FLAG"));      
//				  	Number  speaker_dist =NumberUtil.removeNull(map.get("SPEAKER_DIST"));  
				  	Number  send_up_mode = 0;
//				  	if ("05".equals(terminal_type_code) || "06".equals(terminal_type_code)) {
//				  		send_up_mode = 0;
//				  	}
//				  	String  comm_mode  ="";        
//				  	Number  frame_number =0;      
//				  	Date  power_cut_date =new Date();  
					i++; 
//					mergerSql="MERGE INTO r_tmnl_run a USING " +
//					" (SELECT '"+terminal_id+"' AS terminal_id,'"+cp_no+"' AS cp_no,'"+tmnl_asset_no+"' AS tmnl_asset_no,'"+terminal_addr+"' AS terminal_addr,'"+cis_asset_no+"' AS cis_asset_no,'"+sim_no+"' AS sim_no,id,'"+factory_code+"' AS factory_code,'"+attach_meter_flag+"' AS attach_meter_flag,'"+terminal_type_code+"' AS terminal_type_code,'"+coll_mode+"' AS coll_mode,'"+protocol_code+"' AS protocol_code,'"+comm_password+"' AS comm_password,'"+run_date+"' AS run_date,'"+status_code+"' AS status_code,'"+harmonic_dev_flag+"' AS harmonic_dev_flag,'"+ps_ensure_flag+"' AS ps_ensure_flag,'"+ac_sampling_flag+"' AS ac_sampling_flag," +
//					" '"+eliminate_flag+"' AS eliminate_flag,'"+gate_attr_flag+"' AS gate_attr_flag,'"+prio_ps_mode+"' AS prio_ps_mode,'"+freeze_mode+"' AS freeze_mode,'"+freeze_cycle_num+"' AS freeze_cycle_num,'"+max_load_curve_days+"' AS max_load_curve_days,'"+ps_line_len+"' AS ps_line_len,'"+work_ps+"' AS work_ps,'"+speaker_flag+"' AS speaker_flag,'"+speaker_dist+"' AS speaker_dist,'"+send_up_mode+"' AS send_up_mode,'"+comm_mode+"' AS comm_mode,'"+frame_number+"' AS frame_number,'"+power_cut_date+"' AS power_cut_date FROM DUAL ) b " +
//					" ON (a.terminal_id = b.terminal_id) WHEN MATCHED THEN UPDATE SET cp_no=b.cp_no,tmnl_asset_no=b.tmnl_asset_no,terminal_addr=b.terminal_addr," +
//					" cis_asset_no=b.cis_asset_no,sim_no=b.sim_no,id=b.id,factory_code=b.factory_code,attach_meter_flag=b.attach_meter_flag," +
//					" terminal_type_code=b.terminal_type_code,coll_mode=b.coll_mode,protocol_code=b.protocol_code,comm_password=b.comm_password," +
//					" run_date=b.run_date,status_code=b.status_code,harmonic_dev_flag=b.harmonic_dev_flag,ps_ensure_flag=b.ps_ensure_flag,ac_sampling_flag=b.ac_sampling_flag," +
//					" eliminate_flag=b.eliminate_flag,gate_attr_flag=b.gate_attr_flag,prio_ps_mode=b.prio_ps_mode,freeze_mode=b.freeze_mode," +
//					" freeze_cycle_num=b.freeze_cycle_num,max_load_curve_days=b.max_load_curve_days,ps_line_len=b.ps_line_len,work_ps=b.work_ps," +
//					" speaker_flag=b.speaker_flag,speaker_dist=b.speaker_dist,send_up_mode=b.send_up_mode,comm_mode=b.comm_mode,frame_number=b.frame_number," +
//					" power_cut_date=b.power_cut_date " +
//					" WHEN NOT MATCHED THEN INSERT (terminal_id,cp_no,tmnl_asset_no,terminal_addr,cis_asset_no,sim_no,id,factory_code,attach_meter_flag,terminal_type_code,coll_mode,protocol_code,comm_password,run_date,status_code,harmonic_dev_flag,ps_ensure_flag,ac_sampling_flag," +
//					" eliminate_flag,gate_attr_flag,prio_ps_mode,freeze_mode,freeze_cycle_num,max_load_curve_days,ps_line_len,work_ps,speaker_flag,speaker_dist,send_up_mode,comm_mode,frame_number,power_cut_date)" +
//					" VALUES (b.terminal_id,b.cp_no,b.tmnl_asset_no,b.terminal_addr,b.cis_asset_no,b.sim_no,b.id,b.factory_code,b.attach_meter_flag,b.terminal_type_code,b.coll_mode,b.protocol_code,b.comm_password,b.run_date,b.status_code,b.harmonic_dev_flag,b.ps_ensure_flag,b.ac_sampling_flag,b.eliminate_flag,b.gate_attr_flag," +
//					" b.prio_ps_mode,b.freeze_mode,b.freeze_cycle_num,b.max_load_curve_days,b.ps_line_len,b.work_ps,b.speaker_flag,b.speaker_dist,b.send_up_mode,b.comm_mode,b.frame_number,b.power_cut_date)";
//				
					mergerSql="MERGE INTO r_tmnl_run a USING " +
					" (SELECT '"+terminal_id+"' AS terminal_id,'"+cp_no+"' AS cp_no,'"+tmnl_asset_no+"' AS tmnl_asset_no,'"+terminal_addr+"' AS terminal_addr,'"+terminal_type_code+"' AS terminal_type_code,'"+coll_mode+"' AS coll_mode,'"+protocol_code+"' AS protocol_code, '"+factory_code+"' AS factory_code,'"+cis_asset_no+"' AS cis_asset_no,'"+run_date+"' AS run_date,'"+status_code+"' AS status_code, '"+freeze_cycle_num+"' AS freeze_cycle_num,'"+send_up_mode+"' AS send_up_mode,'"+sim_no+"' as sim_no, '"+attach_meter_flag+"' AS attach_meter_flag, '"+freeze_mode+"' as freeze_mode from dual) b " +
					" ON (a.terminal_id = b.terminal_id) WHEN MATCHED THEN UPDATE SET terminal_addr=b.terminal_addr,terminal_type_code=b.terminal_type_code, coll_mode=b.coll_mode,protocol_code=b.protocol_code, factory_code=b.factory_code,cis_asset_no=b.cis_asset_no,status_code=b.status_code, freeze_cycle_num=b.freeze_cycle_num, send_up_mode=b.send_up_mode,sim_no=b.sim_no,attach_meter_flag=b.attach_meter_flag, freeze_mode=b.freeze_mode   " +
					" WHEN NOT MATCHED THEN INSERT (terminal_id,cp_no,tmnl_asset_no,terminal_addr,terminal_type_code,coll_mode,protocol_code, factory_code, cis_asset_no,status_code, freeze_cycle_num, send_up_mode, sim_no,attach_meter_flag, freeze_mode) " +
					" VALUES (b.terminal_id,b.cp_no,b.tmnl_asset_no,b.terminal_addr,b.terminal_type_code,b.coll_mode,b.protocol_code,b.factory_code,b.cis_asset_no,b.status_code, b.freeze_cycle_num, b.send_up_mode,b.sim_no,b.attach_meter_flag,b.freeze_mode) " ;
				}
				this.getJdbcDAO().update(mergerSql);
				//增加了修改< 运行终端>表中任务上送方式字段的修改
				//getJdbcDAO().update("UPDATE R_TMNL_RUN R SET R.SEND_UP_MODE=(SELECT A.SEND_UP_MODE FROM R_TMNL_RUN T, I_TMNL_PROT_SEND_SETUP A WHERE T.protocol_code=A.protocol_code AND T.TERMINAL_ID='"+qKeyValue+"') WHERE R.TERMINAL_ID="+qKeyValue);
				blag=true;
			// 同步----4.1.4.5采集对象信息/r_coll_obj
			}else if("r_coll_obj".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					BigDecimal coll_obj_id =(BigDecimal)map.get("COLL_OBJ_ID");
					String meter_id =StringUtil.removeNull(map.get("METER_ID"));
					String cp_no=StringUtil.removeNull(map.get("CP_NO"));
					Number coll_port=NumberUtil.removeNull(map.get("COLL_PORT"));
					Number ct_ratio=NumberUtil.removeNull(map.get("CT_RATIO"));
					Number pt_ratio=NumberUtil.removeNull(map.get("PT_RATIO"));
					Number t_factor=NumberUtil.removeNull(map.get("T_FACTOR"));
					Number meter_const=NumberUtil.removeNull(map.get("METER_CONST"));
					Number port_no=NumberUtil.removeNull(map.get("PORT_NO"));
					String pulse_attr=StringUtil.removeNull(map.get("PULSE_ATTR"));
					i++; 
					mergerSql="MERGE INTO r_coll_obj a USING " +
					" (SELECT '"+coll_obj_id+"' as coll_obj_id,'"+meter_id+"' as meter_id,'"+cp_no+"' as cp_no,'"+coll_port+"' as coll_port,'"+ct_ratio+"' as ct_ratio,'"+pt_ratio+"' as pt_ratio,'"+t_factor+"' as t_factor,'"+meter_const+"' as meter_const,'"+port_no+"' as port_no,'"+pulse_attr+"' as pulse_attr FROM dual) b " +
					" ON (a.meter_id = b.meter_id) WHEN MATCHED THEN UPDATE SET coll_obj_id =b.coll_obj_id ,cp_no =b.cp_no ,coll_port =b.coll_port ,ct_ratio =b.ct_ratio ,pt_ratio =b.pt_ratio ,t_factor =b.t_factor ,meter_const =b.meter_const,port_no =b.port_no,pulse_attr =b.pulse_attr  " +
					" WHEN NOT MATCHED THEN INSERT (coll_obj_id,meter_id,cp_no,coll_port,ct_ratio,pt_ratio,t_factor,meter_const,port_no,pulse_attr)" +
					" VALUES (b.coll_obj_id,b.meter_id,b.cp_no,b.coll_port,b.ct_ratio,b.pt_ratio,b.t_factor,b.meter_const,b.port_no,b.pulse_attr)";
					merge[j] = mergerSql;
				}
				this.getJdbcDAO().batchUpdate(merge);
				blag=true;
			// 同步----4.1.4.8运行互感器信息/c_it_run
			}else if("c_it_run".equals(tableName)){
				//把数据保存到采集数据库相对应的临时表中
				for(int j =0; j < ls.size(); j++){
					Map<?,?> map = (Map<?,?>)ls.get(j);
					BigDecimal it_id =(BigDecimal)map.get("IT_ID");
					String mp_id=StringUtil.removeNull(map.get("MP_ID"));
					String org_no=StringUtil.removeNull(map.get("ORG_NO"));
					String cons_no=StringUtil.removeNull(map.get("CONS_ID"));
					String inst_loc =StringUtil.removeNull(map.get("INST_LOC"));
					String inst_date=df.format((Date)map.get("INST_DATE")==null? new Date():(Date)map.get("INST_DATE"));
					String sort_code=StringUtil.removeNull(map.get("SORT_CODE"));
					String phase_code=StringUtil.removeNull(map.get("PHASE_CODE"));
					String current_ratio_code=StringUtil.removeNull(map.get("CURRENT_RATIO_CODE"));
					String volt_ratio_code=StringUtil.removeNull(map.get("VOLT_RATIO_CODE"));
					String priv_flag=StringUtil.removeNull(map.get("PRIV_FLAG"));
					String winding_no=StringUtil.removeNull(map.get("WINDING_NO"));
					String inst_mode=StringUtil.removeNull(map.get("INST_MODE"));
					String last_chk_date=df.format((Date)map.get("LAST_CHK_DATE")==null? new Date():(Date)map.get("LAST_CHK_DATE"));
					Number rotate_cycle=NumberUtil.removeNull(map.get("ROTATE_CYCLE"));
					String rotate_valid_date=df.format((Date)map.get("ROTATE_VALID_DATE")==null? new Date():(Date)map.get("ROTATE_VALID_DATE"));
					Number chk_cycle=NumberUtil.removeNull(map.get("CHK_CYCLE"));

					i++; 
					mergerSql="MERGE INTO c_it_run a USING " +
					" (SELECT '"+it_id+"' as it_id,'"+mp_id+"' as mp_id,'"+org_no+"' as org_no,'"+cons_no+"' as cons_no,'"+inst_loc+"' as inst_loc,to_date('"+inst_date+"','yyyy-mm-dd') as inst_date,'"+sort_code+"' as sort_code,'"+phase_code+"' as phase_code,'"+current_ratio_code+"' as current_ratio_code,'"+volt_ratio_code+"' as volt_ratio_code,'"+priv_flag+"' as priv_flag,'"+winding_no+"' as winding_no,'"+inst_mode+"' as inst_mode,to_date('"+last_chk_date+"','yyyy-mm-dd') as last_chk_date,'"+rotate_cycle+"' as rotate_cycle,to_date('"+rotate_valid_date+"','yyyy-mm-dd') as rotate_valid_date,'"+chk_cycle+"' as chk_cycle FROM dual) b " +
					" ON (a.it_id = b.it_id) WHEN MATCHED THEN UPDATE SET mp_id=b.mp_id,org_no=b.org_no,cons_no=b.cons_no,inst_loc=b.inst_loc,inst_date=b.inst_date,sort_code=b.sort_code,phase_code=b.phase_code,current_ratio_code=b.current_ratio_code,volt_ratio_code=b.volt_ratio_code,priv_flag=b.priv_flag,winding_no=b.winding_no,inst_mode=b.inst_mode,last_chk_date=b.last_chk_date,rotate_cycle=b.rotate_cycle,rotate_valid_date=b.rotate_valid_date,chk_cycle=b.chk_cycle" +
					" WHEN NOT MATCHED THEN INSERT (it_id,mp_id,org_no,cons_no,inst_loc,inst_date,sort_code,phase_code,current_ratio_code,volt_ratio_code,priv_flag,winding_no,inst_mode,last_chk_date,rotate_cycle,rotate_valid_date,chk_cycle)" +
					" VALUES (b.it_id,b.mp_id,b.org_no,b.cons_no,b.inst_loc,b.inst_date,b.sort_code,b.phase_code,b.current_ratio_code,b.volt_ratio_code,b.priv_flag,b.winding_no,b.inst_mode,b.last_chk_date,b.rotate_cycle,b.rotate_valid_date,b.chk_cycle)";
				}
				this.getJdbcDAO().update(mergerSql);
				blag=true;
			}

			 
		} catch (DataAccessException ex) {
			blag = false;
			ex.printStackTrace();
		} 
		return blag;
	}
	
	/**
	 * 根据根据id从中间库queryTableName取出queryName对应的数据queryValue
	 * 
	 */
	public String getNumById(String queryTableName,String id,String zdName,String queryName){
		
		String queryValue="";
		try {
			String sql = "select * from "+queryTableName+" where "+queryName+"='"+id+"'";
			SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(sql);
			JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
			List ls = jdbcToHashMap.getListByCS(rs);
			int i = 0;
			for (Iterator it = ls.iterator(); it.hasNext();) {
				HashMap map = (HashMap) it.next();
				queryValue = (String)(map.get(zdName.toUpperCase()));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			return queryValue;
		}
	}
	
	/**
	 * 根据根据id从中间库queryTableName取出queryName对应的数据queryValue
	 * 
	 */   
	public String getStringById(String queryTableName, String queryName, String zdName,String id){
		
		String queryValue="";
		
		try {
			String sql = "select * from "+queryTableName+" where "+queryName+"='"+id+"'";
			SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(sql);
			JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
			List ls = jdbcToHashMap.getListByCS(rs);
			int i = 0;
			for (Iterator it = ls.iterator(); it.hasNext();) {
				HashMap map = (HashMap) it.next();
				queryValue = StringUtil.removeNull(map.get(zdName.toUpperCase()));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return queryValue;
		}
	}
	
	/**
	 * 同步SIM信息
	 * @param tmnlId
	 * @return
	 */
	private boolean synDotherDev(String tmnlId) {
		
		boolean blag = true;
		try {
			String cRunSql = " SELECT * FROM C_RUN_OTHER_DEV T WHERE T.RELA_ID=?";
			List<?> cRunLst = this.getJdbcYXDAO().queryForList(cRunSql, new Object[]{tmnlId});
			for (int i = 0; i < cRunLst.size(); i++) {
				Map<?, ?> cRunMap = (Map<?, ?>) cRunLst.get(i);
				String equipId = StringUtil.removeNull(cRunMap.get("EQUIP_ID"));//SIM卡ID
				
				String dOtherDevSql = " SELECT * FROM D_OTHER_DEV T WHERE T.ID=? ";
				List<?> dOtherDevLst = this.getJdbcYXDAO().queryForList(dOtherDevSql, new Object[]{equipId});
				
				for (int j = 0; j < dOtherDevLst.size(); j++) {
					Map<?, ?> dOtherDevMap = (Map<?, ?>) dOtherDevLst.get(i);
					String id = StringUtil.removeNull(dOtherDevMap.get("ID"));                   //SIM卡ID
					String belongDept = StringUtil.removeNull(dOtherDevMap.get("BELONG_DEPT"));    //所在单位
					String equipCateg = StringUtil.removeNull(dOtherDevMap.get("EQUIP_CATEG"));  //设备类别  
					String assetNo = StringUtil.removeNull(dOtherDevMap.get("ASSET_NO"));        //资产编号
					String madeNo = StringUtil.removeNull(dOtherDevMap.get("MADE_NO"));          //出厂编号
					String factory = StringUtil.removeNull(dOtherDevMap.get("MANUFACTURER"));    //制造单位

					StringBuffer sf = new StringBuffer();
					sf .append("   MERGE INTO D_OTHER_DEV A ");
					sf .append("   		 USING (SELECT '"+id+"' AS ID,                                                        ");
					sf .append("   		            '"+belongDept+"' AS BELONG_DEPT,                                          ");
					sf .append("   		            '"+equipCateg+"' AS EQUIP_CATEG,                                          ");
					sf .append("   		            '"+assetNo+"' AS ASSET_NO,                                                ");
					sf .append("   		            '"+madeNo+"' AS MADE_NO,                                                  ");
					sf .append("   		            '"+factory+"' AS MANUFACTURER FROM DUAL)B                                 ");
					sf .append("   		 ON (A.ID = B.ID)                                                                     ");
					sf .append("   		 WHEN MATCHED THEN                                                                    ");
					sf .append("   		   UPDATE SET A.BELONG_DEPT=B.BELONG_DEPT,                                            ");
					sf .append("   		              A.EQUIP_CATEG=B.EQUIP_CATEG,                                            ");
					sf .append("   		              A.ASSET_NO=B.ASSET_NO,                                                  ");
					sf .append("   		              A.MADE_NO=B.MADE_NO,                                                    ");
					sf .append("   		              A.MANUFACTURER=B.MANUFACTURER                                           ");
					sf .append("   		 WHEN NOT MATCHED THEN                                                                ");
					sf .append("   	       INSERT (ID, BELONG_DEPT, EQUIP_CATEG, ASSET_NO, MADE_NO, MANUFACTURER)             ");
					sf .append("   		   VALUES (B.ID, B.BELONG_DEPT, B.EQUIP_CATEG, B.ASSET_NO, B.MADE_NO, B.MANUFACTURER) ");
					this.getJdbcDAO().update(sf.toString());
				}
			}
			 
		} catch(Exception e) {
			blag = false;
			e.printStackTrace();
		} 
		return blag;
	}
	
	/**
	 * 终端自动装接的时候根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * 
	 */    
	public boolean synDataByService(String cons_no, String cons_id, String cp_no, String tmnlId, String tgId, String appNo) {
		
		String querySql = null;
		List list = null;
		
		//同步了对终端运行有用的数据库表
		boolean blag = false;
		
		try {
			// 同步----4.1.3.4用电地址/c_elec_addr
			blag = synByService("c_elec_addr", "c_elec_addr", "csa_id", "cons_id", cons_id);
			logger.debug("用电地址/c_elec_addr更新成功");
			
			//同步----SIM卡资产
			blag = synDotherDev(tmnlId);
			logger.debug("SIM卡资产/d_other_dev更新成功");
			
			querySql = "SELECT C.TG_ID FROM C_MP C WHERE C.CONS_ID =?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cons_id});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				tgId = StringUtil.removeNull(mapp.get("TG_ID"));
			}

			if (tgId != null && !"".equals(tgId)) {
				//同步----4.1.4.12台区/g_tg
				blag = synByService("g_tg", "g_tg", "tg_id", "tg_id", tgId);
				logger.debug("台区/g_tg更新成功");
				
				//同步 ----4.1.4.16 线路/g_line
				blag = this.synByOtherService("g_line", "line_id", tgId);
				logger.debug("线路/g_line更新成功");
				
				//同步----4.1.4.11台区线路关系信息/g_line_tg_rela
				blag = synRelaTable("g_line_tg_rela", "tg_id", tgId, "LINE_ID", "TG_ID");
				logger.debug("台区线路关系信息/g_line_tg_rela更新成功");
			}
			
			// 同步----4.1.4.13变压器信息/g_tran
			blag = synByService("g_tran", "g_tran", "equip_id", "cons_id", cons_id);
			logger.debug("变压器信息/g_tran更新成功");
			
			// 同步----4.1.3.10受电点/c_sp
			querySql = "SELECT R.SP_ID FROM C_PS R WHERE R.CONS_ID =?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cons_id});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String spId = StringUtil.removeNull(mapp.get("SP_ID"));
				// 同步----4.1.3.10受电点/c_sp
				blag = synByService("c_sp", "c_sp", "sp_id", "sp_id", spId);
			}
			logger.debug("受电点/c_sp更新成功");
			
			// 同步----4.1.4.19供电电源信息/c_ps
			blag = synByService("c_ps", "c_ps", "ps_id", "cons_id", cons_id);
			logger.debug("供电电源信息/c_ps更新成功");
			
			//同步---- 用户终端联系表/r_cons_tmnl_rela
			blag = synRconsTmnlRela("r_cons_tmnl_rela","cons_no",cons_no, tmnlId);
			logger.debug("用户终端联系表/r_cons_tmnl_rel更新成功");
			
			// 根据cons_id从中间库c_cons取出cons_id对应的数据cust_id的值
			String cust_id = this.getStringById("c_cons","cons_id","cust_id", cons_id);
			
			// 同步----4.1.3.3用户联系信息/c_contact
//			blag = synByService("c_contact", "c_contact", "contact_id", "cust_id",cust_id);
			logger.debug("用户联系信息/c_contact更新成功");
			
			// 同步----4.1.3.1客户信息/c_cust
			blag = synByService("c_cust", "c_cust", "cust_id", "cust_id", cust_id);
			logger.debug("客户信息/c_cust更新成功");
			
			// 同步----4.1.3.2用户基本信息/c_cons
			blag = synDataQuery("c_cons","cons_id",cons_id+"");
			logger.debug("用户基本信息/c_cons更新成功");
			
			// 同步----4.1.4.3终端资产信息/d_lc_equip
			blag = synDataQuery("d_lc_equip","ID",tmnlId+"");
			logger.debug("终端资产信息/d_lc_equip更新成功");
			
			// 同步----4.1.4.4运行终端信息/r_tmnl_run
			blag = synDataQuery("r_tmnl_run","TERMINAL_ID",String.valueOf(tmnlId));
			logger.debug("运行终端信息/r_tmnl_run更新成功");
			
			// 同步----4.1.4.5采集对象信息/r_coll_obj
			blag = synDataQuery("r_coll_obj","cp_no",String.valueOf(cp_no));
			logger.debug("采集对象信息/r_coll_obj更新成功");
			
			// 同步----4.1.4.5采集点计量点关系/r_cp_mp_rela
			blag = synRelaTable("r_cp_mp_rela", "cp_no", cp_no, "CP_NO", "MP_ID");
			logger.debug("采集点计量点关系/r_cp_mp_rela更新成功");
			
			//一终端多表
			querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cp_no});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
				
				// 同步----4.1.4.6电能表信息/d_meter
				blag = synByService("d_meter", "d_meter", "meter_id", "meter_id",meter_id);
				logger.debug("电能表信息/d_meter更新成功");
				
				// 同步----4.1.4.7运行电能表信息/c_meter
				blag = synCmeter("c_meter","meter_id",meter_id+"", cons_no, "0");//flag：0为专变
				logger.debug("运行电能表信息/c_meter更新成功");
				
				//同步----4.1.4.1计量点信息/c_mp
				querySql = "SELECT A.MP_ID FROM C_METER_MP_RELA A WHERE A.METER_ID=?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{meter_id});
				for (Iterator it = list.iterator(); it.hasNext();) {
					Map<?, ?> map = (Map<?, ?>) it.next();
					String mp_id = StringUtil.removeNull(map.get("MP_ID"));
					//计量点更新
					blag = synDataQuery("c_mp","mp_id", mp_id+"/"+meter_id+"/"+cons_id);
					querySql = "SELECT R.IT_ID FROM C_IT_RUN R WHERE R.MP_ID =?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{mp_id});
					for (Iterator itt = list.iterator(); itt.hasNext();) {
						Map<?, ?> mapt = (Map<?, ?>) itt.next();
						String itId = StringUtil.removeNull(mapt.get("IT_ID"));
						//同步-----4.1.4.8运行互感器信息/c_it_run
						blag = synByService("c_it_run", "c_it_run", "it_id","it_id", itId);
						logger.debug("运行互感器信息/c_it_run更新成功");
					}
					
					// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
					blag = synRelaTable("c_meter_mp_rela", "mp_id", mp_id, "METER_ID", "MP_ID");
					logger.debug("电能表计量点关系信息/c_meter_mp_rela更新成功");
					
					if (list!= null && list.size() > 0) {
						// 同步----4.1.4.9互感器计量点关系信息/c_mp_it_rela
						blag = synRelaTable("c_mp_it_rela", "mp_id", mp_id, "MP_ID", "IT_ID");
						logger.debug("互感器计量点关系信息/c_mp_it_rela更新成功");
					}
				}
			}
		
			// 同步----4.1.4.2采集点信息/r_cp
			blag = synDataQuery("r_cp","cp_no",String.valueOf(cp_no));
			logger.debug("采集点信息/r_cp更新成功");
			// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
			blag = synRelaTable("r_cp_cons_rela", "cons_id", cons_id, "CONS_ID", "CP_NO");
			logger.debug("采集用户关系信息/r_cp_cons_rela更新成功");
			
			
			logger.debug("终端自动装接调用的档案数据同步完成!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return blag;
	}
	
	/**
	 * 集抄同步中间库数据
	 * @return boolean true or false
	 */
	public boolean synOtherDataByService(String tmnlId, String cpNo, String tgId, String appNo) {

		String querySql = null;
		List list = null;
		//同步了对终端运行有用的数据库表
		boolean blag = false;
	   
		try {
			// 同步----4.1.4.12台区/g_tg
			blag = synByService("g_tg", "g_tg", "tg_id", "tg_id", tgId);
			logger.debug("台区/g_tg更新成功");
			
			//同步-----同步居民用户到/c_cons
			blag = synCcons(tgId, tmnlId);
			logger.debug("台区/c_cons更新成功");
			
			//虚拟台区到用户表中，公变：大写T打头 + TG_NO（详见：台区和线路户号处理方案.doc）
			blag = synTgToCcons(tgId);
			logger.debug("虚拟台区到用户表---更新成功");
			
			//同步 ----4.1.4.16 线路/g_line
			blag = this.synByOtherService("g_line", "line_id", tgId);
			logger.debug("线路/g_line更新成功");
			
			//同步----4.1.4.11台区线路关系信息/g_line_tg_rela
			blag = synRelaTable("g_line_tg_rela", "tg_id", tgId, "LINE_ID", "TG_ID");
			logger.debug("台区线路关系信息/g_line_tg_rela更新成功");
			
			// 同步----4.1.4.13变压器信息/g_tran
			if (tgId != null && !"".equals(tgId)) {
				querySql = "SELECT R.EQUIP_ID FROM G_TRAN R WHERE R.TG_ID =?"; 
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tgId});
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					Map<?, ?> mapp = (Map<?, ?>) itr.next();
					String equipId = StringUtil.removeNull(mapp.get("EQUIP_ID"));
					blag = synByService("g_tran", "g_tran", "equip_id", "equip_id", equipId);
					logger.debug("变压器信息/g_tran更新成功");
				}
			}
			
			// 同步----4.1.4.3终端资产信息/d_lc_equip
			blag = synDataQuery("d_lc_equip","ID", tmnlId+"");
			logger.debug("终端资产信息/d_lc_equip更新成功");
			
			// 同步----4.1.4.4运行终端信息/r_tmnl_run
			blag = synDataQuery("r_tmnl_run","TERMINAL_ID",String.valueOf(tmnlId));
			logger.debug("运行终端信息/r_tmnl_run更新成功");
			
			// 同步----4.1.4.5采集对象信息/r_coll_obj
			blag = synDataQuery("r_coll_obj","cp_no",String.valueOf(cpNo));
			logger.debug("采集对象信息/r_coll_obj更新成功");
			
			// 同步----4.1.4.5采集点计量点关系/r_cp_mp_rela
			blag = synRelaTable("r_cp_mp_rela", "cp_no", cpNo, "CP_NO", "MP_ID");
			logger.debug("采集点计量点关系/r_cp_mp_rela更新成功");
			
			//同步-----4.1.4.25运行其它集抄设备/r_exec_other_dev
			querySql = "SELECT R.COLLECTOR_ID FROM R_COLL_OBJ R WHERE R.CP_NO=?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cpNo});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String collectorID = StringUtil.removeNull(mapp.get("COLLECTOR_ID"));
				blag = synByService("r_exec_other_dev", "r_exec_other_dev", "collector_id", "collector_id", collectorID);
				//同步 -----采集器资产信息
				blag = synDataQuery("d_lc_equip","ID", collectorID+"");
				logger.debug("采集器资产信息/d_lc_equip更新成功");
			}
			logger.debug("其他运行设备/r_exec_other_dev更新成功");
			
			//一终端多表
			querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cpNo});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
				// 同步----4.1.4.6电能表信息/d_meter
				blag = synByService("d_meter", "d_meter", "meter_id", "meter_id",meter_id);
				logger.debug("电能表信息/d_meter更新成功");
				// 同步----4.1.4.7运行电能表信息/c_meter
				blag = synCmeter("c_meter","meter_id",meter_id+"", tgId, "1");//flag:1为集抄 
				logger.debug("METER_ID "+meter_id+"  运行电能表信息/c_meter更新成功");

				//同步----4.1.4.1计量点信息/c_mp
				querySql = "SELECT A.MP_ID FROM C_METER_MP_RELA A WHERE A.METER_ID=?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{meter_id});
				for (Iterator it = list.iterator(); it.hasNext();) {
					Map<?, ?> map = (Map<?, ?>) it.next();
					String mp_id = StringUtil.removeNull(map.get("MP_ID"));
					//计量点更新
					blag = synDataQuery("c_mp", "mp_id", mp_id+"/"+meter_id+"/"+tgId);
					logger.debug("MP_ID "+mp_id+" 计量点信息/c_mp更新成功");
					
					// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
					blag = synRelaTable("c_meter_mp_rela", "mp_id", mp_id, "METER_ID", "MP_ID");
					logger.debug("电能表计量点关系信息/c_meter_mp_rela更新成功");
					
					querySql = "SELECT R.IT_ID FROM C_IT_RUN R WHERE R.MP_ID =?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{mp_id});
					for (Iterator itt = list.iterator(); itt.hasNext();) {
						Map<?, ?> mapt = (Map<?, ?>) itt.next();
						String itId = StringUtil.removeNull(mapt.get("IT_ID"));
						//同步-----4.1.4.8运行互感器信息/c_it_run
						blag = synByService("c_it_run", "c_it_run", "it_id","it_id", itId);
						logger.debug("IT_ID "+itId+" 运行互感器信息/c_it_run更新成功");
					}
					if (list!= null && list.size() > 0) {
						// 同步----4.1.4.9互感器计量点关系信息/c_mp_it_rela
						blag = synRelaTable("c_mp_it_rela", "mp_id", mp_id, "MP_ID", "IT_ID");
						logger.debug("互感器计量点关系信息/c_mp_it_rela更新成功");
					}
				}
			}

			// 同步----4.1.4.2采集点信息/r_cp
			blag = synDataQuery("r_cp","cp_no",String.valueOf(cpNo));
			logger.debug("采集点信息/r_cp更新成功");
			
			// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
			blag = synRelaTable("r_cp_cons_rela", "cp_no", cpNo, "CP_NO", "CONS_ID");//公变的情况 cons_id就是tg_Id
			logger.debug("采集用户关系信息/r_cp_cons_rela更新成功");
			
			//同步---- 用户终端联系表
			querySql = "SELECT G.TG_NO FROM G_TG G WHERE G.TG_ID=?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{tgId});
			for (Iterator it = list.iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				String tgNo = StringUtil.removeNull(map.get("TG_NO"));
				logger.debug("********************台区编号: T"+tgNo+"***************************");
				blag = synRconsTmnlRela("r_cons_tmnl_rela","cons_no","T"+tgNo, tmnlId);
				logger.debug("用户终端联系表/r_cons_tmnl_rela更新成功");
			}
			
			logger.debug("终端自动装接调用的档案数据同步完成!");
		} catch(Exception e) {
			e.printStackTrace();
		} 
		return blag;
	}
	
	
	/**
	 * 终端自动装接的时候根据参数来同步用户信息类、运行终端信息类的表数据到主站数据库
	 * @param subs_id
	 * @param tmnlId
	 * @param cp_no
	 */    
	public boolean synGateDataByService(String subs_id, String tmnlId, String cp_no) {
		
		String querySql = null;
		List list = null;

		boolean blag = false;
		try {
			
			//同步 ----4.1.4.16 线路/g_line
			blag = this.synLineForgate("g_line", "line_id", subs_id);
			logger.debug("线路/g_line更新成功");
			
			//同步 ----4.1.4.16 变电站/g_subs
			blag = this.synByService("g_subs", "g_subs", "subs_id", "subs_id", subs_id);
			logger.debug("变电站/g_subs更新成功");
			
			//同步----4.1.4.11变电站线路关系信息/g_subs_line_rela
			blag = synRelaTable("g_subs_line_rela", "subs_id", subs_id, "subs_id", "line_id");
			logger.debug("变电站线路关系信息/g_subs_line_rela更新成功");
			
			String subsNo = "";
			String subSql = " SELECT * FROM G_SUBS T WHERE T.SUBS_ID=?";
			list = this.getJdbcYXDAO().queryForList(subSql, new Object[]{subs_id});
			if (list != null && list.size() > 0) {
				Map<?,?> map = (Map<?,?>) list.get(0);
				subsNo = StringUtil.removeNull(map.get("SUBS_NO"));
			}
			//同步---- 用户终端联系表/r_cons_tmnl_rela
			blag = synRconsTmnlRela("r_cons_tmnl_rela","cons_no","S"+subsNo, tmnlId);
			logger.debug("用户终端联系表/r_cons_tmnl_rel更新成功");
			
			//虚拟关口到用户表中，公变：大写S打头 + SUBS_NO
			blag = synSubsToCcons(subs_id);
			logger.debug("虚拟台区到用户表---更新成功");
			
			// 同步----4.1.4.3终端资产信息/d_lc_equip
			blag = synDataQuery("d_lc_equip","ID", tmnlId+"");
			logger.debug("终端资产信息/d_lc_equip更新成功");
			
			// 同步----4.1.4.4运行终端信息/r_tmnl_run
			blag = synDataQuery("r_tmnl_run", "TERMINAL_ID", String.valueOf(tmnlId));
			logger.debug("运行终端信息/r_tmnl_run更新成功");
			
			// 同步----4.1.4.5采集对象信息/r_coll_obj
			blag = synDataQuery("r_coll_obj","cp_no",String.valueOf(cp_no));
			logger.debug("采集对象信息/r_coll_obj更新成功");
			
			// 同步----4.1.4.5采集点计量点关系/r_cp_mp_rela
			blag = synRelaTable("r_cp_mp_rela", "cp_no", cp_no, "CP_NO", "MP_ID");
			logger.debug("采集点计量点关系/r_cp_mp_rela更新成功");
			
			//一终端多表
			querySql = "SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO =?"; 
			list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{cp_no});
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				Map<?, ?> mapp = (Map<?, ?>) itr.next();
				String meter_id = StringUtil.removeNull(mapp.get("METER_ID"));
				
				// 同步----4.1.4.6电能表信息/d_meter
				blag = synByService("d_meter", "d_meter", "meter_id", "meter_id",meter_id);
				logger.debug("电能表信息/d_meter更新成功");
				
				// 同步----4.1.4.7运行电能表信息/c_meter
				blag = synCmeter("c_meter","meter_id", meter_id+"", "S"+subsNo, "0");//flag：0为专变 
				logger.debug("运行电能表信息/c_meter更新成功");
				
				//同步----4.1.4.1计量点信息/c_mp
				querySql = "SELECT A.MP_ID FROM C_METER_MP_RELA A WHERE A.METER_ID=?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{meter_id});
				for (Iterator it = list.iterator(); it.hasNext();) {
					Map<?, ?> map = (Map<?, ?>) it.next();
					String mp_id = StringUtil.removeNull(map.get("MP_ID"));
					//计量点更新
					blag = synDataQuery("c_mp","mp_id", mp_id+"/"+meter_id+"/"+subs_id);
					querySql = " SELECT R.IT_ID FROM C_IT_RUN R WHERE R.MP_ID =?"; 
					list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{mp_id});
					for (Iterator itt = list.iterator(); itt.hasNext();) {
						Map<?, ?> mapt = (Map<?, ?>) itt.next();
						String itId = StringUtil.removeNull(mapt.get("IT_ID"));
						//同步-----4.1.4.8运行互感器信息/c_it_run
						blag = synByService("c_it_run", "c_it_run", "it_id","it_id", itId);
						logger.debug("运行互感器信息/c_it_run更新成功");
					}
					
					// 同步----4.1.4.10电能表计量点关系信息/c_meter_mp_rela
					blag = synRelaTable("c_meter_mp_rela", "mp_id", mp_id, "METER_ID", "MP_ID");
					logger.debug("电能表计量点关系信息/c_meter_mp_rela更新成功");
					
					if (list!= null && list.size() > 0) {
						// 同步----4.1.4.9互感器计量点关系信息/c_mp_it_rela
						blag = synRelaTable("c_mp_it_rela", "mp_id", mp_id, "MP_ID", "IT_ID");
						logger.debug("互感器计量点关系信息/c_mp_it_rela更新成功");
					}
				}
			}
		
			// 同步----4.1.4.2采集点信息/r_cp
			blag = synDataQuery("r_cp","cp_no",String.valueOf(cp_no));
			logger.debug("采集点信息/r_cp更新成功");
			// 同步----4.1.4.18采集用户关系信息/r_cp_cons_rela
			blag = synRelaTable("r_cp_cons_rela", "cp_no", cp_no, "CONS_ID", "CP_NO");
			logger.debug("采集用户关系信息/r_cp_cons_rela更新成功");
			
			logger.debug("终端自动装接调用的档案数据同步完成!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return blag;
	}
	
	
	/**
	 * 更新 关系表
	 * @param tableName
	 * @param columnName
	 * @param kValue
	 * @return true or false
	 */
	private boolean synRelaTable(String tableName, String columnName, String kValue, String key1, String key2) {
		
		try {
			String querySql = " SELECT * FROM "+tableName+"  WHERE  "+columnName+"='"+kValue+"'";
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
					if (!key1.equalsIgnoreCase(col) && !key2.equalsIgnoreCase(col)) {
						updateSF.append("A."+key+"="+"B."+key+",");
					}
					//插入KEY
					insertValueSF.append("B."+key+",");
					//插入value
					insertKeySF.append(key+",");
				}
				//条件
				conditionSF.append(" A."+key1+"="+"B."+key1+" AND "+"A."+key2+"="+"B."+key2);
				
				startSF = new StringBuffer(startSF.toString().substring(0, startSF.length()-1));
				updateSF = new StringBuffer(updateSF.substring(0, updateSF.length()-1));
				insertValueSF  = new StringBuffer(insertValueSF.substring(0, insertValueSF.length()-1));
				insertKeySF  = new StringBuffer(insertKeySF.substring(0, insertKeySF.length()-1));
				startSF.append(" FROM DUAL");
				
				StringBuffer mergeSql = new StringBuffer("  MERGE INTO  "+tableName+" A USING ("+startSF.toString()+") B ON ("+conditionSF.toString()+")  "); 
				mergeSql.append("  WHEN MATCHED THEN UPDATE SET  "+updateSF.toString()+"   ");
				mergeSql.append("  WHEN NOT MATCHED THEN INSERT ("+insertKeySF.toString()+") VALUES("+insertValueSF.toString()+")  ");

				//merge
				this.getJdbcDAO().update(mergeSql.toString());
			}
			 	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
	}
	
	/**
	 * 虚拟台区到用户表中
	 * @param tgId  台区ID
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean synTgToCcons(String tgId) {
		
		try {
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
			
			String sqlTg = "SELECT * FROM G_TG T WHERE T.TG_ID = '"+tgId+"'"; 
			List<?> listTg = this.getJdbcYXDAO().queryForList(sqlTg.toString());
			if(listTg != null && listTg.size() > 0){
				Map<?, ?> mapp = (Map<?, ?>) listTg.get(0);
				orgNo = StringUtil.removeNull(mapp.get("ORG_NO"));
				areaNo = StringUtil.subStr(orgNo, 0, 7);
				consNo = "T" + StringUtil.removeNull(mapp.get("TG_NO"));
				consName = StringUtil.removeNull(mapp.get("TG_NAME"));
				//根据台区ID从G_LINE_TG_RELA取得线路信息
				String sqlLine = "SELECT LINE_ID FROM G_LINE_TG_RELA T WHERE T.TG_ID = '"+tgId+"'";
				List<?> listLine = this.getJdbcYXDAO().queryForList(sqlLine.toString());
				if(listLine != null){
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
			
			String sqlCons = "SELECT * FROM C_CONS T WHERE T.CONS_ID = '" + tgId + "' AND T.TG_ID = '"+tgId+"'";
			List<?> listCons = this.getJdbcDAO().queryForList(sqlCons.toString());
			if(listCons != null && listCons.size() == 0){
				Object[] objIn = new Object[]{};
				String insertSql="";
				objIn = new Object[]{tgId, orgNo ,areaNo ,consNo ,consName ,tgId ,lineId, consSortCode, elecAddr, consType, elecTypeCode, contractCap, runCap, capGradeNo, buileDate, statusCode};
				insertSql="insert into c_cons" +
						" (CONS_ID, ORG_NO, AREA_NO, CONS_NO, CONS_NAME , TG_ID, LINE_ID, CONS_SORT_CODE, ELEC_ADDR, CONS_TYPE, ELEC_TYPE_CODE, CONTRACT_CAP, RUN_CAP, CAP_GRADE_NO, BUILD_DATE, STATUS_CODE) " +
						" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				this.getJdbcDAO().update(insertSql, objIn);
			}else{
				StringBuffer updateSql = new StringBuffer();
				updateSql.append("UPDATE C_CONS SET ORG_NO = ?, AREA_NO = ?, CONS_NO = ?, CONS_NAME = ?, TG_ID = ?, LINE_ID = ?, CONS_SORT_CODE = ?, ELEC_ADDR = ?, CONS_TYPE = ?, ELEC_TYPE_CODE = ?, CONTRACT_CAP = ?, RUN_CAP = ?, CAP_GRADE_NO = ?, BUILD_DATE = ?, STATUS_CODE = ?");
				updateSql.append("	WHERE CONS_ID = ?");
				Object[] objIn = new Object[]{};
				objIn = new Object[]{orgNo ,areaNo ,consNo ,consName ,tgId ,lineId, consSortCode, elecAddr, consType, elecTypeCode, contractCap, runCap, capGradeNo, buileDate, statusCode, tgId};
				this.getJdbcDAO().update(updateSql.toString(), objIn);
			}
			 	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
	}

	/**
	 * 为关口虚拟用户
	 * @param subsId
	 * @return
	 */
	private boolean synSubsToCcons(String subsId) {
	
		try {
			Number consId = Long.valueOf(subsId);
			String orgNo = "";
			String areaNo = "";
			String consNo = "";
			String consName = "";
			String elecAddr = "";
			Number consType = null;
			String statusCode = "";
			
			String sqlSubs = "SELECT * FROM G_SUBS T WHERE T.SUBS_ID = '"+subsId+"'"; 
			List<?> listSubs = this.getJdbcYXDAO().queryForList(sqlSubs.toString());
			if(listSubs != null && listSubs.size() > 0){
				Map<?, ?> mapp = (Map<?, ?>) listSubs.get(0);
				orgNo = StringUtil.removeNull(mapp.get("ORG_NO"));
				areaNo = StringUtil.subStr(orgNo, 0, 7);
				consNo = "S" + StringUtil.removeNull(mapp.get("SUBS_NO"));
				consName = StringUtil.removeNull(mapp.get("SUBS_NAME"));
				elecAddr = StringUtil.removeNull(mapp.get("INST_ADDR"));
				consType = 3;
				statusCode = "0";
			}
			
			//MERGE
			StringBuffer mergeCcons = new StringBuffer();
			mergeCcons.append("   MERGE INTO C_CONS A  ");
			mergeCcons.append("       USING (SELECT '"+consId+"' AS CONS_ID, '"+orgNo+"' AS ORG_NO,                                                                 \n");
			mergeCcons.append("                 '"+areaNo+"' AS AREA_NO, '"+consNo+"' AS CONS_NO,                                                                   \n");
			mergeCcons.append("                 '"+consName+"' AS CONS_NAME, '"+elecAddr+"' AS ELEC_ADDR,                                                           \n");
			mergeCcons.append("                 '"+consType+"' AS CONS_TYPE, SYSDATE AS BUILD_DATE,                                                         \n");
			mergeCcons.append("                 '"+statusCode+"' AS STATUS_CODE,'"+subsId+"' AS SUBS_ID FROM DUAL) B                                                                        \n");
			mergeCcons.append("            ON (A.CONS_ID = B.CONS_ID)                                                                                               \n");
			mergeCcons.append("        WHEN MATCHED THEN                                                                                                            \n");
			mergeCcons.append("            UPDATE SET A.AREA_NO=B.AREA_NO, A.ORG_NO=B.ORG_NO,                                                                       \n");
			mergeCcons.append("                       A.CONS_NO=B.CONS_NO, A.CONS_NAME=B.CONS_NAME,                                                                 \n");
			mergeCcons.append("                       A.ELEC_ADDR=B.ELEC_ADDR, A.CONS_TYPE=B.CONS_TYPE,                                                             \n");
			mergeCcons.append("                       A.BUILD_DATE=B.BUILD_DATE, A.STATUS_CODE=B.STATUS_CODE, A.SUBS_ID=B.SUBS_ID                                                       \n");
			mergeCcons.append("        WHEN NOT MATCHED THEN                                                                                                        \n");
			mergeCcons.append("            INSERT (CONS_ID, ORG_NO, AREA_NO, CONS_NO, CONS_NAME, ELEC_ADDR, CONS_TYPE, BUILD_DATE, STATUS_CODE, SUBS_ID)                     \n");
			mergeCcons.append("            VALUES (B.CONS_ID, B.ORG_NO, B.AREA_NO, B.CONS_NO, B.CONS_NAME, B.ELEC_ADDR, B.CONS_TYPE, B.BUILD_DATE, B.STATUS_CODE, B.SUBS_ID)   \n"); 
			//merge
			this.getJdbcDAO().update(mergeCcons.toString());
			 	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	
	
	/**
	 * 同步用户终端联系表
	 * @param tableName
	 * @param qKeyName
	 * @param qKeyValue
	 * @return
	 */
	public boolean synRconsTmnlRela(String tableName,String qKeyName, String consNo, String tmnlId) {
	
		try {
			logger.debug("*************************************建立用户终端关系表开始*************************************");
			String areaNo = "";
			String orgNo = "";
			String querySql = null;
			String param = null;
			List list = null;
			int t = consNo.indexOf("T");
			int s = consNo.indexOf("S");
			if (t == 0 && s != 0) {
				querySql = "SELECT ORG_NO FROM G_TG WHERE TG_NO=? ";
				param = consNo.substring(1, consNo.length());
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{param});
			} else if (t != 0 && s == 0){
				querySql = "SELECT ORG_NO FROM G_SUBS G WHERE SUBS_NO =? ";
				param = consNo.substring(1, consNo.length());
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{param});
			} else {
				querySql = "SELECT ORG_NO FROM C_CONS WHERE CONS_NO=?";
				list = this.getJdbcYXDAO().queryForList(querySql, new Object[]{consNo});
			}
			
			Map<?,?> map = (Map<?,?>) list.get(0);
			orgNo = StringUtil.removeNull(map.get("ORG_NO"));
			if (orgNo.length() > 7) {
				areaNo = orgNo.substring(0, 7);
			} else {
				areaNo = orgNo;
			}
			logger.debug("***************orgNo: "+orgNo+"****areaNo: "+areaNo+"****************************");
			
			//merge
			StringBuffer sql = new StringBuffer();
			sql.append(" MERGE INTO R_CONS_TMNL_RELA A USING (SELECT '"+orgNo+"' AS ORG_NO, '"+areaNo+"' AS AREA_NO, '"+consNo+"' AS CONS_NO, '"+tmnlId+"' AS TMNL_ASSET_NO FROM DUAL) B ");
			sql.append("     ON (A.TMNL_ASSET_NO = B.TMNL_ASSET_NO AND A.CONS_NO=B.CONS_NO) ");
			sql.append("     	WHEN MATCHED THEN ");
			sql.append("     		UPDATE SET A.ORG_NO=B.ORG_NO, A.AREA_NO=B.AREA_NO ");
			sql.append("     	WHEN NOT MATCHED THEN  ");
			sql.append("     		INSERT (ORG_NO, AREA_NO, CONS_NO, TMNL_ASSET_NO) ");
			sql.append("     		VALUES (B.ORG_NO, B.AREA_NO, B.CONS_NO, B.TMNL_ASSET_NO) ");
			
			//update
			this.getJdbcDAO().update(sql.toString());
			 	
			logger.debug("***************建立用户终端关系表结束*****户号: "+consNo+"****终端资产号: "+tmnlId+"****************************");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
	}

	/**
	 * 把自动化抄表数据保存到中间库中（同步昨天数据）
	 * 
	 * @param sDate
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	public boolean synDataCollect(Date sDate) {

		String interval = null;
		String lastTime = null;
		String querySql = "";
		StringBuffer sb = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sDate);
		calendar.add(calendar.DAY_OF_MONTH, -1);
		sDate=calendar.getTime();
		DecimalFormat dt = new DecimalFormat("#.####");
		
		//////////////////////////////////////////////////测量点日冻结电能(按小时截取导入中间库)////////////////////////////
		//取间隔时间
		querySql = "SELECT * FROM B_SYS_PARAMETER B WHERE B.PARAM_NO='INTERFACE_TRANSPORT'"; 
		List list = getJdbcDAO().queryForList(querySql);
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> map = (Map<?, ?>) itr.next();
			String paramItemNo = StringUtil.removeNull(map.get("PARAM_ITEM_NO"));
			if ("INTERVAL".equals(paramItemNo)) {
				interval = StringUtil.removeNull(map.get("PARAM_ITEM_VAL"));
				lastTime = StringUtil.removeNull(map.get("LAST_TIME_MP_READ"));
			}
		}

		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		String sql = "select * from vw_org_area where org_type='03'";
		SqlRowSet re = getJdbcDAO().queryForRowSet(sql);
		list = jdbcToHashMap.getListByCS(re);
		boolean blag=false;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = getTransactionManager().getTransaction(def);
		try{
			//插入DEMAND表
			this.insertDemand(list, sDate);

			//读数据开始时间（日抄表）
			long startTime =  this.getStartTime(sDate);
			//读数据结束时间（日抄表）
			long endTime = this.getEndTime();

			/**
			 * 时间循环（每一段时间内各个供电局循环）
			 * 循环的时间为interval （单位：分钟）
			 */
			for (long i = startTime; i <= endTime; i+=Long.parseLong(interval)*60000) {
				
				//计算循环的时间参数
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
			    date.setTime(i);
			    String start = df.format(date);
			    logger.debug("=-=============================="+start+"=======================================");
			   
			    long j = i + Long.parseLong(interval)*60000;
			    date.setTime(j);
			    String end = df.format(date);
			    logger.debug("=-=============================="+end+"=======================================");
			    
				//下次参数时间
				Object[] obj = new Object[]{start, end};
				
				
				/**
				 * 根据数据库中取得的interval时间
				 * 去每个局数据中取得满足这段时间
				 * 内的数据    （局循环）
				 */
				int flag = 0; //免读数据库标识
				Map columCode = new HashMap();
				for (Iterator itr = list.iterator(); itr.hasNext();) {
					
					Map mapp = (HashMap) itr.next();
					String area_code = StringUtil.removeNull(mapp.get("AREA_CODE"));
					
					/**
					 * 测量点日冻结电能示值
					 * E_MP_DAY_READ(01~07)
					 */
					// 电表抄表数据表 e_mped_day_read,mark为1表示补招的数据
					querySql = "select * from E_MP_DAY_READ" + area_code + " where rec_time > to_date(?, 'yyyy/mm/dd hh24:mi:ss') and rec_time <to_date(?, 'yyyy/mm/dd hh24:mi:ss')";
					List eMpReadList = getJdbcDAO().queryForList(querySql,obj);
					
					
					StringBuffer sfUnion = null;
					/**
					 * 局对应的数据循环
					 */
					for (Iterator eMpReadit = eMpReadList.iterator(); eMpReadit.hasNext();) {
						Map<?, ?> mapeMpRead = (Map<?, ?>) eMpReadit.next();
						sfUnion = new StringBuffer();
						//根据ID取COLL_OBJ_ID
						Number data_id = (BigDecimal)(mapeMpRead.get("DATA_ID"));
						// 根据data_id得到数据来源  COLL_OBJ_ID , ORG_NO
						querySql = "select * from E_DATA_MP where data_id=? and mp_sn<>?";
						Object[] object = new Object[] { data_id ,"0"};
						SqlRowSet rst = getJdbcDAO().queryForRowSet(querySql, object);
						List lst = jdbcToHashMap.getListByCS(rst);
						Number coll_obj_id = null;                                         //表计ID
						String org_no = null;                                              //单位
						Date ymdDate = (Date)mapeMpRead.get("DATA_DATE");                      //取得YMD
						SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
						String ymd = sf.format(ymdDate);
						for (Iterator its = lst.iterator(); its.hasNext();) {
							Map ss = (HashMap) its.next();
							coll_obj_id = (BigDecimal)(ss.get("COLL_OBJ_ID"));
							org_no = StringUtil.removeNull(ss.get("ORG_NO"));
						}
						if (coll_obj_id != null ){
							/**
							 * 1；取得营销CODE
							 * 2；组合UNION语句
							 */
							Set<String> set = (Set<String>) mapeMpRead.keySet();
							for (String colum: set) {
								
								if (!colum.equalsIgnoreCase("DATA_ID") && !colum.equalsIgnoreCase("DATA_DATE") 
										&& !colum.equalsIgnoreCase("COL_TIME") &&  !colum.equalsIgnoreCase("REC_TIME") 
										&& !colum.equalsIgnoreCase("CT") && !colum.equalsIgnoreCase("PT") 
										&&  !colum.equalsIgnoreCase("MARK")) {
									String cisCode = null; //营销对应数字码
									//第一次循环
									if (flag == 0) {
										//搜索营销对应数字码
										String sqlForCol = "SELECT B.COLL_ITEM_CODE FROM B_COLL_ITEM_SETUP B WHERE B.COLUMN_NAME=? AND B.SEND_FLAG=1";
										List collItemSetUp = getJdbcDAO().queryForList(sqlForCol, new Object[]{colum});
										
										for (Iterator itColl = collItemSetUp.iterator(); itColl.hasNext();) {
											Map<?, ?> mapCollSetUp = (Map<?, ?>) itColl.next();
											//取得营销对应的数字编码
											cisCode = StringUtil.removeNull(mapCollSetUp.get("COLL_ITEM_CODE"));
											columCode.put(colum, cisCode);
										}
										
									} else {
										//根据标识flag不为0, 节省重复搜索数据库
										cisCode = StringUtil.removeNull(columCode.get(colum));
									}
									if (cisCode != null && !"".equals(cisCode)) {
										//取得营销CODE对应的值
										String dataValue = dt.format(NumberUtil.removeNull(mapeMpRead.get(colum)));
										
										//组装 USING语句
										sfUnion.append("  SELECT  '"+coll_obj_id+"' AS  COLL_OBJ_ID, '"+org_no+"' AS ORG_NO, TO_DATE('" +ymd+"','YYYY/MM/DD') AS YMD, '"+cisCode+"' AS COLL_ITEM_CODE, '"+dataValue+"' AS DATA_VALUE FROM DUAL UNION");
									}
								}
							}
						}
						
						//循环完后 MERGE
						if (sfUnion.length() > 0) {
							String usingStr = sfUnion.substring(0, sfUnion.length()-5);
							//循环完1个局后进行merge
							StringBuffer mergeSql = new StringBuffer(); 
							mergeSql.append(" MERGE INTO R_AR_DATA A USING ("+usingStr+")B ");
							mergeSql.append("    ON (A.COLL_OBJ_ID = B.COLL_OBJ_ID AND A.COLL_ITEM_CODE = B.COLL_ITEM_CODE AND A.YMD = B.YMD) ");
							mergeSql.append("        WHEN MATCHED THEN UPDATE SET A.DATA_VALUE = B.DATA_VALUE, A.ORG_NO = B.ORG_NO,A.WRITE_DATE = SYSDATE ");
							mergeSql.append("  		 WHEN NOT MATCHED THEN INSERT (A.COLL_OBJ_ID,  A.YMD, A.ORG_NO, A.COLL_ITEM_CODE, A.DATA_VALUE, A.WRITE_DATE) ");
							mergeSql.append("			VALUES  (B.COLL_OBJ_ID, B.YMD, B.ORG_NO, B.COLL_ITEM_CODE, B.DATA_VALUE, SYSDATE)");
							logger.debug(mergeSql.toString());
						    this.getJdbcYXDAO().update(mergeSql.toString());
						}		
					}	flag++;
				}
				
			}
		    blag=true;
		} catch (Exception ex) {
			 
			ex.printStackTrace();
		}
		 	
		return blag;
	}
	
	/**
	 * 更新
	 * @param list
	 * @throws ParseException 
	 */
	public void insertDemand(List list, Date sDate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String sdate = sf.format(sDate);
		DecimalFormat dt = new DecimalFormat("#.####");
		/**
		 * 根据数据库中取得的interval时间
		 * 去每个局数据中取得满足这段时间
		 * 内的数据    （局循环）
		 */
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			Map<?, ?> mapp = (Map<?, ?>) itr.next();
			String area_code = StringUtil.removeNull(mapp.get("AREA_CODE"));
			
			/**
			 * 测量点日冻结需量及需量发生时间0
			 * E_MP_DAY_DEMAND
			 */
			// 电表抄表数据表 E_MP_DAY_DEMAND,mark为1表示补招的数据
			String querySql = "select * from E_MP_DAY_DEMAND" + area_code + " where DATA_DATE = to_date(?, 'yyyymmdd')";
			List demandList = getJdbcDAO().queryForList(querySql,new Object[]{sdate});
			for (Iterator demandit = demandList.iterator(); demandit.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) demandit.next();
				Number data_id = (BigDecimal)(map.get("DATA_ID"));
				Date ymd =(Date)map.get("DATA_DATE");
				String y = sf.format(ymd);
				ymd = sf.parse(y);
				
				// 当日正向有功总最大需量  A010
				String pap_demand = dt.format(NumberUtil.removeNull(map.get("PAP_DEMAND")));

				// 当日正向有功总最大需量时间  B010
				Date pap_demand_time = (Date)map.get("PAP_DEMAND_TIME");
		
				//当日正向无功总最大需量 A110
				String prp_demand = dt.format(NumberUtil.removeNull(map.get("PRP_DEMAND")));
					
				//当日正向无功总最大需量时间 B110
				Date prp_demand_time = (Date)(map.get("PRP_DEMAND_TIME"));
		
				// 根据data_id得到数据来源 DATA_SRC
				querySql = "select * from E_DATA_MP where data_id=? and mp_sn<>?";
				Object[] obj2 = new Object[] { data_id ,"0"};
				list = getJdbcDAO().queryForList(querySql, obj2);
				Number coll_obj_id = null;
				String org_no = null;
				for (Iterator its = list.iterator(); its.hasNext();) {
					Map<?,?> ss = (Map<?,?>) its.next();
					coll_obj_id = (BigDecimal)(ss.get("COLL_OBJ_ID"));
					org_no = StringUtil.removeNull(ss.get("ORG_NO"));
				}
				//组合插入SQL
				if(!"".equals(coll_obj_id)&&(coll_obj_id!=null)){
					logger.debug("----------------------------------调用开始------------------------------------");
					//A010
					String update = " UPDATE R_AR_DATA R SET DATA_VALUE=?, WRITE_DATE=SYSDATE WHERE R.COLL_OBJ_ID=? " +
					" AND R.COLL_ITEM_CODE='A010' AND YMD=? " ;
					String insert = " insert into r_ar_data (coll_obj_id, coll_item_code, ymd, org_no, data_value, write_date) values (?, 'A010',? , ?, ?, SYSDATE)";
					int i = getJdbcYXDAO().update(update, new Object[]{pap_demand, coll_obj_id, ymd});
					if (i == 0) {
						getJdbcYXDAO().update(insert, new Object[]{coll_obj_id, ymd, org_no, pap_demand});
					}
					//A110
					update = " UPDATE R_AR_DATA R SET DATA_VALUE=?, WRITE_DATE=SYSDATE  WHERE R.COLL_OBJ_ID=? " +
					" AND R.COLL_ITEM_CODE='A110' AND YMD=?" ;
					insert = " insert into r_ar_data (coll_obj_id, coll_item_code, ymd, org_no, data_value, write_date) values (?, 'A110',? , ?, ?, SYSDATE)";
					i = getJdbcYXDAO().update(update, new Object[]{prp_demand, coll_obj_id, ymd});
					if (i == 0) {
						getJdbcYXDAO().update(insert, new Object[]{coll_obj_id, ymd, org_no, prp_demand});
					}
					//B010
					update = " UPDATE R_AR_DATA R SET DATA_VALUE=?, WRITE_DATE=SYSDATE  WHERE R.COLL_OBJ_ID=? " +
					" AND R.COLL_ITEM_CODE='B010' AND YMD=?" ;
					insert = " insert into R_AR_DATA (coll_obj_id, coll_item_code, ymd, org_no, data_value, write_date) values (?, 'B010', ? , ?, '0', SYSDATE)";
					i = getJdbcYXDAO().update(update, new Object[]{0, coll_obj_id, pap_demand_time});
					if (i == 0) {
						getJdbcYXDAO().update(insert, new Object[]{coll_obj_id, pap_demand_time, org_no});
					}
					
					//B110
					update = " UPDATE R_AR_DATA R SET DATA_VALUE=? , WRITE_DATE=SYSDATE WHERE R.COLL_OBJ_ID=? " +
					" AND R.COLL_ITEM_CODE='B110' AND YMD=?" ;
					insert = " insert into r_ar_data (coll_obj_id, coll_item_code, ymd, org_no, data_value, write_date) values (?, 'B110',? , ?,'0' , SYSDATE)";
					i = getJdbcYXDAO().update(update, new Object[]{0, coll_obj_id, prp_demand_time});
					if (i == 0) {
						getJdbcYXDAO().update(insert, new Object[]{coll_obj_id,  prp_demand_time, org_no});
					}
					logger.debug("----------------------------------调用结束------------------------------------");
				}
			}
		}
	}
	
	/**
	 * 取得最后倒数据时间
	 * @param sDate    当天定时更新时间（06:30）
	 * @return 返回时间
	 */
	private long getStartTime(Date sDate) throws ParseException {
		//起始时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
		String s = df.format(sDate);
		sDate = df.parse(s);
		return sDate.getTime();
	}
	
	/**
	 * 增加每次取数据的时间区间
	 * @param dateParam
	 * @param df
	 * @param interval
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	private long getEndTime() throws ParseException {
		//结束时间
		Date endDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
		String addOneday = df.format(endDate);
		endDate = df.parse(addOneday);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(calendar.SECOND, -1);
		return calendar.getTime().getTime();
	}
	
	/**
	 * 同步两张表的数据, 要求两张表的列是相同的
	 * 
	 * @param originTable 		源表名称
	 * @param destinationTabel 	目标表名称
	 * @param primaryKeyName  	主键名称
	 * @param sKeyName 			查询字段
	 * @param sKeyValue 		查询字段值
	 */
	public boolean synByService(String originTable, String destinationTabel,
			String primaryKeyName, String sKeyName, String sKeyValue) {
		
		String selectSql = "select * from " + originTable + " where "+ sKeyName + "='" + sKeyValue+"'";
		SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(selectSql);
		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		List ls = jdbcToHashMap.getListByCS(rs);
		String insertSql = null;
		String updateSql = null;
		Set<String> columnNames = null;
		int result = 0;
		boolean blag=false;
		
		try{
			for (Iterator it = ls.iterator(); it.hasNext();) {
				Map map = (HashMap) it.next();
				if (insertSql == null) {
					columnNames = map.keySet();
					if ("c_it_run".equals(destinationTabel)) {
						columnNames.remove("BAR_CODE");
						columnNames.remove("CONS_ID");
						columnNames.remove("ASSET_NO");
						columnNames.remove("MADE_NO");
					}
					updateSql = SQLutil.genUpdateSql(destinationTabel,columnNames,primaryKeyName); // update语句
					insertSql = SQLutil.genInsertSql(destinationTabel,columnNames,primaryKeyName); // insert 语句
				}
				Object[] data = SQLutil.genUpdateData(map, columnNames, primaryKeyName);
				result = this.getJdbcDAO().update(updateSql, data); 		// 试图更新
				if (result == 0) { 									// 没有更新的记录
					result = this.getJdbcDAO().update(insertSql, data); 	// 插入
				}
			}
			 	
			blag=true;
		} catch (DataAccessException ex) {
			throw ex;
		} 
		return blag;
	}
	
	
	/**
	 * 同步两张表的数据, 要求两张表的列是相同的
	 * 
	 * @param originTable 		源表名称
	 * @param primaryKeyName  	主键名称
	 * @param sKeyValue 		查询字段值
	 */
	public boolean synByOtherService(String originTable,String primaryKeyName, String sKeyValue) {

		String selectSql = " SELECT L.LINE_ID, L.LINE_NO, L.LINE_NAME, L.ORG_NO, L.VOLT_CODE, L.WIRE_SPEC_CODE, L.WIRE_LEN, L.SUBLINE_FLAG, L.CHG_DATE, L.LN_FLAG, L.RURAL_GRID_FLAG, L.RUN_STATUS_CODE, L.LL_CALC_MODE, L.AP_LL_VALUE, L.RP_LL_VALUE, L.UNIT_RESI, L.UNIT_REAC  FROM G_LINE L,G_LINE_TG_RELA R WHERE R.LINE_ID=L.LINE_ID AND R.TG_ID="+sKeyValue;
		SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(selectSql);
		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		List ls = jdbcToHashMap.getListByCS(rs);
		String insertSql = null;
		String updateSql = null;
		Set<String> columnNames = null;
		int result = 0;
		boolean blag=false;
		
		try{
			for (Iterator it = ls.iterator(); it.hasNext();) {
				Map map = (HashMap) it.next();
				if (insertSql == null) {
					columnNames = map.keySet();
					updateSql = SQLutil.genUpdateSql(originTable,columnNames,primaryKeyName); // update语句
					insertSql = SQLutil.genInsertSql(originTable,columnNames,primaryKeyName); // insert 语句
				}
				Object[] data = SQLutil.genUpdateData(map, columnNames, primaryKeyName);
				result = this.getJdbcDAO().update(updateSql, data); 		// 试图更新
				if (result == 0) {                           		// 没有更新的记录
					result = getJdbcDAO().update(insertSql, data); 	// 插入
				}
			}
			 	
			blag=true;
		} catch (DataAccessException ex) {
			ex.printStackTrace();
			 
		} 
		return blag;
	}
	
	/**
	 * 同步线路(关口)
	 * @param originTable
	 * @param primaryKeyName
	 * @param sKeyValue
	 * @return
	 */
	private boolean synLineForgate(String originTable,String primaryKeyName, String sKeyValue) {
		
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("  SELECT L.LINE_ID, L.LINE_NO, L.LINE_NAME, L.ORG_NO, L.VOLT_CODE, L.WIRE_SPEC_CODE, L.WIRE_LEN, L.SUBLINE_FLAG, ");
		selectSql.append("  	 L.CHG_DATE, L.LN_FLAG, L.RURAL_GRID_FLAG,L.RUN_STATUS_CODE, L.LL_CALC_MODE, L.AP_LL_VALUE, L.RP_LL_VALUE, ");
		selectSql.append("		 L.UNIT_RESI, L.UNIT_REAC FROM G_LINE L INNER JOIN G_SUBS_LINE_RELA G ON G.LINE_ID=L.LINE_ID AND G.SUBS_ID='"+sKeyValue+"' ");
		SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(selectSql.toString());
		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		List ls = jdbcToHashMap.getListByCS(rs);
		String insertSql = null;
		String updateSql = null;
		Set<String> columnNames = null;
		int result = 0;
		boolean blag=false;
		try{
			for (Iterator it = ls.iterator(); it.hasNext();) {
				Map map = (HashMap) it.next();
				if (insertSql == null) {
					columnNames = map.keySet();
					updateSql = SQLutil.genUpdateSql(originTable,columnNames,primaryKeyName); // update语句
					insertSql = SQLutil.genInsertSql(originTable,columnNames,primaryKeyName); // insert 语句
				}
				Object[] data = SQLutil.genUpdateData(map, columnNames, primaryKeyName);
				result = this.getJdbcDAO().update(updateSql, data); 		// 试图更新
				if (result == 0) {                           		// 没有更新的记录
					result = this.getJdbcDAO().update(insertSql, data); 	// 插入
				}
			}
			 	
			blag=true;
		} catch (DataAccessException ex) {
			 
			throw ex;
		}
		return blag;
	}
	

	/**
	 * 同步两张表的数据, 要求两张表的列是相同的
	 * 
	 * @param originTable 		源表名称
	 * @param destinationTabel 	目标表名称
	 * @param primaryKeyName 	主键名称
	 */
	public boolean sync(String originTable, String destinationTabel,String primaryKeyName) {

		String selectSql = "select * from " + originTable;
		SqlRowSet rs = this.getJdbcYXDAO().queryForRowSet(selectSql);
		JdbcToHashMap jdbcToHashMap = new JdbcToHashMap();
		List ls = jdbcToHashMap.getListByCS(rs);
		String insertSql = null;
		String updateSql = null;
		Set<String> columnNames = null;
		boolean blag=false;
		int result = 0;
		try{
			for (Iterator it = ls.iterator(); it.hasNext();) {
				Map map = (HashMap) it.next();
				if (insertSql == null) {
					columnNames = map.keySet();
					updateSql = SQLutil.genUpdateSql(destinationTabel,columnNames,primaryKeyName); // update语句
					insertSql = SQLutil.genInsertSql(destinationTabel,columnNames,primaryKeyName); // insert 语句
				}
				Object[] data = SQLutil.genUpdateData(map, columnNames, primaryKeyName);
				result = this.getJdbcDAO().update(updateSql, data); 		// 试图更新
				if (result == 0) { 									// 没有更新的记录
					result = this.getJdbcDAO().update(insertSql, data); 	// 插入
				}
			}
			if(result==1){
				blag=true;
			}
			 	
		} catch (DataAccessException ex) {
			 
			throw ex;
		}
		return blag;
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
	 * 取得容量等级
	 * @param contract_cap
	 * @return
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
	 * @param protocol_code
	 * @return
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
	 * just for test
	 * please don't delete
	 * @param args
	 */
	public static void main(String[] args) {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("WebContent/WEB-INF/config/applicationContext-dao.xml");
		SynDataForMarketingDaoImpl im = (SynDataForMarketingDaoImpl) ctx.getBean("synDataForMarketingDao");
		
//		im.synDataByService("0000009515", "3351497", "0000834001", "210955486", "14041", "");
		im.synCcons("31467", "210955486");
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