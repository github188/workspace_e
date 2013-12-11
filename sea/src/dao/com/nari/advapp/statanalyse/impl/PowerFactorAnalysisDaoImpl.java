package com.nari.advapp.statanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.statanalyse.PowerFactor;
import com.nari.advapp.statanalyse.PowerFactorAnalysisDao;
import com.nari.advapp.statanalyse.VoltDegree;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.NodeTypeUtils;
import common.Logger;

/**
 * @category 功率因素分析
 * @author 陈国章
 * 
 */
public class PowerFactorAnalysisDaoImpl extends JdbcBaseDAOImpl implements
		PowerFactorAnalysisDao {
	Logger log=Logger.getLogger(PowerFactorAnalysisDaoImpl.class);
	/**
	 * 查询电压等级
	 * 
	 * @parameters consNo
	 * @return volt
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VoltDegree> queryVoltByConsNo(String consNo, String type) {
		// TODO Auto-generated method stub
		List<VoltDegree> volt = null;
		String sql;
		consNo += "%";
		if (type.equals(NodeTypeUtils.NODETYPE_USER)) {
			sql = "SELECT DISTINCT VVC.VOLT VOLT,VVC.VOLT_CODE VOLT_CODE FROM vw_volt_code VVC JOIN C_CONS "
					+ " C ON VVC.VOLT_CODE=C.VOLT_CODE WHERE C.CONS_NO like ?";
			volt = super.getJdbcTemplate().query(sql, new Object[] { consNo },
					new VoltMapper());
		} else if (type.equals(NodeTypeUtils.NODETYPE_ORG)) {
			sql = "SELECT DISTINCT VVC.VOLT VOLT,VVC.VOLT_CODE VOLT_CODE FROM vw_volt_code VVC JOIN C_CONS  C\n"
					+ "ON VVC.VOLT_CODE=C.VOLT_CODE JOIN O_ORG O on O.ORG_NO=C.ORG_NO WHERE O.ORG_NO like ?";
			
			volt = super.getJdbcTemplate().query(sql, new Object[] { consNo },
					new VoltMapper());

		} else {
			volt = new ArrayList<VoltDegree>();
		}
		return volt;
	}

	public class VoltMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			VoltDegree vd = new VoltDegree();
			vd.setVolt(rs.getString("VOLT"));
			vd.setVoltCode(rs.getString("VOLT_CODE"));
			return vd;
		}
	}

	/**
	 * 查詢功率因素列表
	 * 
	 * @param voltCode
	 *            ,consNo,startTime,endTime
	 * @return list
	 */
	
	@SuppressWarnings("unchecked")
	public Page<PowerFactor> queryPowerFactorList(String consNo,
			String voltCode, Date pfaTime, String type,long start,long limit) {
		Page<PowerFactor> pfList = null;
		String sql;
		consNo += "%";
		if (type.equals(NodeTypeUtils.NODETYPE_USER)) {
			if ((null != voltCode) && !("".equals(voltCode))) {
				
				sql =

					"select a.cons_no,a.org_no,a.org_name,a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n" +
					"select * from (\n" + 
					"    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join o_org o  on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and c.cons_no=? and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n" + 
					") where rid = 1) a,\n" + 
					"(  select * from\n" + 
					"    (select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join o_org o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"         and c.cons_no=?\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n" + 
					") where rid = 1) b\n" + 
					"where a.cons_no = b.cons_no and a.d = b.d";

//					"select a.cons_no, a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n"
//						+ "select * from (\n"
//						+ "    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n"
//						+ "       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n"
//						+ "    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n"
//						+ "         join o_org o on edm.org_no=o.org_no\n"
//						+ "         join c_cons c on edm.cons_no=c.cons_no\n"
//						+ "         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n"
//						+ "    WHERE C.CONS_NO like ? and crv.data_time >= to_date(?,'yyyy-mm-dd') AND crv.data_time < to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n"
//						+ ") where rid = 1) a,\n"
//						+ "(  select * from (\n"
//						+ "    select c.cons_no, crv.data_time, trunc(data_time) d, crv.f v,  ROW_NUMBER()\n"
//						+ "       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n"
//						+ "    from e_mp_curve crv\n"
//						+ "         join e_data_mp edm on edm.id=crv.id\n"
//						+ "         join o_org o on edm.org_no=o.org_no\n"
//						+ "         join c_cons c on edm.cons_no=c.cons_no\n"
//						+ "         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n"
//						+ "    WHERE C.CONS_NO like ? and crv.data_time >= to_date(?,'yyyy-mm-dd') AND crv.data_time < to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n"
//						+ ") where rid = 1) b\n"
//						+ "where a.cons_no = b.cons_no and a.d = b.d";
						
				pfList=super.pagingFind(sql, start, limit, new PowerFactorMapper(),
						new Object[] { consNo,DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), voltCode, consNo,
						DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime),voltCode });
//				pfList = super.getJdbcTemplate().query(
//						sql,
//						new Object[] { consNo,DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), voltCode, consNo,
//								DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), voltCode }, new PowerFactorMapper());
				log.debug(sql);
			} else {
				sql = 
					"select a.cons_no,a.org_no,a.org_name,a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n" +
					"select * from (\n" + 
					"    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join o_org o  on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and c.cons_no=? and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1\n" + 
					") where rid = 1) a,\n" + 
					"(  select * from\n" + 
					"    (select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join o_org o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"         and c.cons_no=?\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1\n" + 
					") where rid = 1) b\n" + 
					"where a.cons_no = b.cons_no and a.d = b.d";

//					"select a.cons_no, a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n"
//						+ "select * from (\n"
//						+ "    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n"
//						+ "       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n"
//						+ "    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n"
//						+ "         join o_org o on edm.org_no=o.org_no\n"
//						+ "         join c_cons c on edm.cons_no=c.cons_no\n"
//						+ "         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n"
//						+ "    WHERE C.CONS_NO like ? and crv.data_time >= to_date(?,'yyyy-mm-dd') AND crv.data_time < to_date(?,'yyyy-mm-dd')+1\n"
//						+ ") where rid = 1) a,\n"
//						+ "(  select * from (\n"
//						+ "    select c.cons_no, crv.data_time, trunc(data_time) d, crv.f v,  ROW_NUMBER()\n"
//						+ "       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n"
//						+ "    from e_mp_curve crv\n"
//						+ "         join e_data_mp edm on edm.id=crv.id\n"
//						+ "         join o_org o on edm.org_no=o.org_no\n"
//						+ "         join c_cons c on edm.cons_no=c.cons_no\n"
//						+ "         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n"
//						+ "    WHERE C.CONS_NO like ? and crv.data_time >= to_date(?,'yyyy-mm-dd') AND crv.data_time < to_date(?,'yyyy-mm-dd')+1 \n"
//						+ ") where rid = 1) b\n"
//						+ "where a.cons_no = b.cons_no and a.d = b.d";
				
				pfList=super.pagingFind(sql, start, limit, new PowerFactorMapper(),
						new Object[] { consNo,DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), consNo, DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime)});
//				pfList = super.getJdbcTemplate().query(
//						sql,
//						new Object[] { consNo,DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), consNo, DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime),
//								}, new PowerFactorMapper());
				log.debug(sql);

			}
				
//			return pfList;
		} else if (type.equals(NodeTypeUtils.NODETYPE_ORG)) {
			consNo += "%";
			if ((null != voltCode) && !("".equals(voltCode))) {
				sql = 
					"select a.cons_no,a.org_no,a.org_name,a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n" +
					"select * from (\n" + 
					"    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join (select org_no, org_name from o_org where org_no like ?) o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n" + 
					") where rid = 1) a,\n" + 
					"(  select * from\n" + 
					"    (select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join (select org_no, org_name from o_org where org_no like ?) o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1 and vv.volt_code=?\n" + 
					") where rid = 1) b\n" + 
					"where a.cons_no = b.cons_no and a.d = b.d";				
				
				pfList=super.pagingFind(sql, start, limit, new PowerFactorMapper(), 
						new Object[] { consNo, DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), voltCode, consNo,
								DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), voltCode });		
				log.debug(sql);
			} else {
				sql = 

					"select a.cons_no,a.org_no,a.org_name,a.cons_name,a.contract_cap,a.volt,a.data_time max_time, a.v max_factor, b.data_time mintime, b.v min_factor  from (\n" +
					"select * from (\n" + 
					"    select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f desc, crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join (select org_no, org_name from o_org where org_no like ?) o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1\n" + 
					") where rid = 1) a,\n" + 
					"(  select * from\n" + 
					"    (select c.cons_no,c.cons_name,o.org_no,o.org_name,c.contract_cap, crv.data_time, trunc(data_time) d, crv.f v, vv.VOLT,  ROW_NUMBER()\n" + 
					"       OVER (PARTITION BY c.cons_no, trunc(crv.data_time) ORDER BY crv.f , crv.data_time ) AS rid\n" + 
					"    from e_mp_curve crv     join e_data_mp edm on edm.id=crv.id\n" + 
					"         join (select org_no, org_name from o_org where org_no like ?) o on edm.org_no=o.org_no\n" + 
					"         join c_cons c on edm.cons_no=c.cons_no\n" + 
					"         join vw_volt_code vv on vv.VOLT_CODE=c.volt_code\n" + 
					"    and crv.data_time>=to_date(?,'yyyy-mm-dd') and crv.data_time<to_date(?,'yyyy-mm-dd')+1\n" + 
					") where rid = 1) b\n" + 
					"where a.cons_no = b.cons_no and a.d = b.d";

				
				pfList = super.pagingFind(sql, start, limit, new PowerFactorMapper(), 
						new Object[] { consNo, DateUtil.dateToString(pfaTime),
					DateUtil.dateToString(pfaTime), consNo,DateUtil.dateToString(pfaTime), DateUtil.dateToString(pfaTime) });
//					super.getJdbcTemplate().query(sql,
//						new Object[] { consNo, DateUtil.dateToString(pfaTime),DateUtil.dateToString(pfaTime), consNo,DateUtil.dateToString(pfaTime), DateUtil.dateToString(pfaTime) },
//						new PowerFactorMapper());		
				log.debug(sql);
			}
		}
		return pfList;

	}

	class PowerFactorMapper implements RowMapper {		
		public Object mapRow(ResultSet rs, int arg1)  {
			try{
			// TODO Auto-generated method stub
				PowerFactor pf = new PowerFactor();
				pf.setOrgName(rs.getString("ORG_NAME"));
				pf.setConsNo(rs.getString("CONS_NO"));
				pf.setConsName(rs.getString("CONS_NAME"));
				pf.setContractCap(rs.getFloat("CONTRACT_CAP"));
				pf.setMaxFactor(rs.getFloat("MAX_FACTOR"));
				pf.setMaxTime(DateUtil.timeStampToStr(rs.getTimestamp("MAX_TIME")));
				pf.setVolt(rs.getString("VOLT"));
				pf.setMinFactor(rs.getFloat("MIN_FACTOR"));
				return pf;
			}
			catch(Exception e){
				log.debug("映射类时出错");
				return null;
			}
		}
	}

}
