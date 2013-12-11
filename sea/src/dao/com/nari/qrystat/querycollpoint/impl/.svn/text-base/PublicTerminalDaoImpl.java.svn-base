package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.IpublicTerminalDao;
import com.nari.qrystat.querycollpoint.PublicTerminal;
import com.nari.support.Page;

public class PublicTerminalDaoImpl extends JdbcBaseDAOImpl implements
		IpublicTerminalDao {
	Logger logger = Logger.getLogger(PublicTerminalDaoImpl.class);

	
	/**
	 * 终端状态  修改
	 */
	@SuppressWarnings("unchecked")
	public List<PublicTerminal> findTmnlStatus() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from vw_tmnl_status_calc ");
		return getJdbcTemplate().query(sql.toString(), new statusRunMapper());
	}

	class statusRunMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PublicTerminal publicterminal = new PublicTerminal();
			try {
				publicterminal.setStatusCode(rs.getString("STATUS_CODE"));
				publicterminal.setStatusName(rs.getString("STATUS_NAME"));
				return publicterminal;
			} catch (Exception e) {
				return null;
			}
		}
	}
	

	
	/**
	 *公变查询	 修改
	 */
	@Override
	public Page<PublicTerminal> findPBTerminal(String consType, String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser) {
		StringBuffer sb = new StringBuffer();
		String d = "";
		String r = "";
		List<Object> parms = new ArrayList<Object>();
		if (pbtype.equals("ugp")) {
			r = "R_USER_GROUP_DETAIL R,";
			d = "AND C.CONS_NO = R.CONS_NO(+)";
		}
		if (pbtype.equals("cgp")) {
			r = "T_TMNL_GROUP_DETAIL D,";
			d = "AND C.CONS_NO = D.CONS_NO(+)";
		}
		
		sb.append("SELECT O.ORG_NAME,\n" + "       C.CONS_ID,C.CONTRACT_CAP,\n"
				+ "       C.CONS_NO,\n" + "       C.CONS_NAME,\n"
				+ "       C.TG_ID,\n"
				+ "       C.ELEC_ADDR,\n" + "       C.RUN_CAP,\n"
				+ "       V.VOLT,\n" + "       T.CIS_ASSET_NO,\n"
				+ "       T.TERMINAL_ADDR,\n" + "       T.SIM_NO,\n"
				+ "       M.TMNL_TYPE,\n" + "       F.FACTORY_NAME,\n"
				+ "       W.COMM_MODE,\n" + "       S.STATUS_NAME,\n"
				+ "       TO_CHAR(T.RUN_DATE, 'yyyy-mm-dd') RUN_DATE\n"
				+ "  FROM C_CONS              C,\n"
				+ "       O_ORG               O,\n"
				+ "       VW_VOLT_CODE        V,\n"
				+ "       VW_TMNL_RUN          T,\n"
				+ "       VW_TMNL_TYPE_CODE   M,\n"
				+ "       VW_TMNL_FACTORY     F,\n"
				+ "       VW_COMM_MODE        W," + r + "\n"
				+ "       VW_TMNL_STATUS_CALC S\n"
				+ " WHERE C.ORG_NO = O.ORG_NO(+) " + d + "\n"
				+ "   AND C.VOLT_CODE = V.VOLT_CODE(+)\n"
				+ "   AND C.CONS_NO = T.CONS_NO(+)\n"
				+ "   AND T.TERMINAL_TYPE_CODE = M.TMNL_TYPE_CODE(+)\n"
				+ "   AND T.FACTORY_CODE = F.FACTORY_CODE(+)\n"
				+ "   AND T.COLL_MODE = W.COMM_MODE_CODE(+)\n"
				+ "   AND T.STATUS_CODE = S.STATUS_CODE(+)\n");

		if (!statusName.equals("00")) {
			sb.append("   AND T.STATUS_CODE=? ");
			parms.add(statusName);
		}

		sb.append("   AND C.CONS_TYPE = '2' ");
		if (pbtype.equals("org")) {
			//省公司操作员不需要条件 市级以下添加查询条件
			if(!publicOrgType.equals("02")){
				sb.append(" AND C.ORG_NO like ?||'%' ");
				parms.add(pbid);
			}
		} else if (pbtype.equals("sub")) {
			sb.append(" AND C.SUBS_ID=? ");
			parms.add(pbid);
		} else if (pbtype.equals("line")) {
			sb.append(" AND C.LINE_ID=? ");
			parms.add(pbid);
		} else if (pbtype.equals("ugp")) {
			sb.append(" AND R.GROUP_NO=? ");
			parms.add(pbid);
		} else if (pbtype.equals("cgp")) {
			sb.append(" AND D.GROUP_NO=? ");
			parms.add(pbid);
		} else if(consType != null && consType.equals("2")){
			sb.append(" AND C.CONS_NO =?");
			parms.add(pbid);
		}

		//sb.append(SysPrivilige.addPri(pSysUser, "C", "T", 7));
		sb.append(" AND EXISTS (SELECT 1 FROM P_ACCESS_ORG PRI_ORG\n"				
				+ "     WHERE PRI_ORG.ORG_NO = C.AREA_NO AND PRI_ORG.STAFF_NO =?)\n"
				+ " AND EXISTS (SELECT 1 FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n"
				+ "     WHERE PRI_CONS_TYPE.CONS_TYPE = '2' AND PRI_CONS_TYPE.STAFF_NO = ?)\n" 
				+ " AND EXISTS (SELECT 1 FROM P_ACCESS_TMNL_FACTORY PRI_FACTORY\n"
				+ "     WHERE PRI_FACTORY.FACTORY_CODE = T.FACTORY_CODE AND PRI_FACTORY.STAFF_NO = ?)\n");
		parms.add(pSysUser.getStaffNo());
		parms.add(pSysUser.getStaffNo());
		parms.add(pSysUser.getStaffNo());
		sb.append(" order by C.ORG_NO,C.CONS_NO\n"); 
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new PBTRowMapper(), parms.toArray());
	}

	/**
	 * 专变查询  	 修改
	 * 
	 * @param pbid
	 * @param pbtype
	 * @param start
	 * @param limit
	 * @return
	 */
	public Page<PublicTerminal> findPRTerminal(String consType,String pbid, String statusName,
			String publicOrgType, String pbtype, long start, int limit,
			PSysUser pSysUser) {
		StringBuffer sb = new StringBuffer();
		String d = "";
		String r = "";

		List<Object> parms = new ArrayList<Object>();
		// Object[] newObj = new Object[] {pbid,statusName,
		// pSysUser.getStaffNo(),pSysUser.getStaffNo(),pSysUser.getStaffNo()};

		if (pbtype.equals("ugp")) {
			r = "R_USER_GROUP_DETAIL R,";
			d = "AND C.CONS_NO = R.CONS_NO(+)";
		}
		if (pbtype.equals("cgp")) {
			r = "T_TMNL_GROUP_DETAIL D,";
			d = "AND C.CONS_NO = D.CONS_NO(+)";
		}
		
		sb.append("SELECT O.ORG_NAME,\n"
				+ "        C.CONS_ID,C.CONTRACT_CAP,\n" + "       C.CONS_NO,\n"
				+ "       C.CONS_NAME,\n" + "       C.ELEC_ADDR,\n"
				+ "       C.RUN_CAP,\n" + "       V.VOLT,\n"
				+ "       C.TG_ID,\n"
				+ "       T.CIS_ASSET_NO,\n" + "       T.TERMINAL_ADDR,\n"
				+ "       T.SIM_NO,\n" + "       M.TMNL_TYPE,\n"
				+ "       F.FACTORY_NAME,\n" + "       W.COMM_MODE,\n"
				+ "       S.STATUS_NAME,\n"
				+ "       TO_CHAR(T.RUN_DATE, 'yyyy-mm-dd') RUN_DATE\n"
				+ "  FROM C_CONS              C,\n"
				+ "       O_ORG               O,\n"
				+ "       VW_VOLT_CODE        V,\n"
				+ "       VW_TMNL_RUN          T,\n"
				+ "       VW_TMNL_TYPE_CODE   M,\n"
				+ "       VW_TMNL_FACTORY     F,\n"
				+ "       VW_COMM_MODE        W," + r + "\n"
				+ "       VW_TMNL_STATUS_CALC S\n"
				+ "  WHERE C.ORG_NO = O.ORG_NO(+) " + d + "\n"
				+ "   AND C.VOLT_CODE = V.VOLT_CODE(+)\n"
				+ "   AND C.CONS_NO = T.CONS_NO(+)\n"
				+ "   AND T.TERMINAL_TYPE_CODE = M.TMNL_TYPE_CODE(+)\n"
				+ "   AND T.FACTORY_CODE = F.FACTORY_CODE(+)\n"
				+ "   AND T.COLL_MODE = W.COMM_MODE_CODE(+)\n"
				+ "   AND T.STATUS_CODE = S.STATUS_CODE(+)\n");

		if (!statusName.equals("00")) {
			sb.append("   AND T.STATUS_CODE=? ");
			parms.add(statusName);
		}

		sb.append("   AND C.CONS_TYPE = 1 ");

		if (pbtype.equals("org")) {
			//省公司操作员不需要条件 市级以下添加查询条件
			if(!publicOrgType.equals("02")){
				sb.append(" AND C.ORG_NO like ?||'%' ");
				parms.add(pbid);
			}
		} else if (pbtype.equals("sub")) {
			sb.append(" AND C.SUBS_ID=? ");
			parms.add(pbid);
		} else if (pbtype.equals("line")) {
			sb.append(" AND C.LINE_ID=? ");
			parms.add(pbid);
		} else if (pbtype.equals("ugp")) {
			sb.append(" AND R.GROUP_NO=? ");
			parms.add(pbid);
		} else if (pbtype.equals("cgp")) {
			sb.append(" AND D.GROUP_NO=? ");
			parms.add(pbid);
		} else if(consType != null && consType.equals("1")){
			sb.append(" AND C.CONS_NO =?");
			parms.add(pbid);
		}

		//sb.append(SysPrivilige.addPri(pSysUser, "C", "T", 7));
		sb.append(" AND EXISTS (SELECT 1 FROM P_ACCESS_ORG PRI_ORG\n"				
				+ "     WHERE PRI_ORG.ORG_NO = C.AREA_NO AND PRI_ORG.STAFF_NO =?)\n"
				+ " AND EXISTS (SELECT 1 FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n"
				+ "     WHERE PRI_CONS_TYPE.CONS_TYPE = '1' AND PRI_CONS_TYPE.STAFF_NO = ?)\n" 
				+ " AND EXISTS (SELECT 1 FROM P_ACCESS_TMNL_FACTORY PRI_FACTORY\n"
				+ "     WHERE PRI_FACTORY.FACTORY_CODE = T.FACTORY_CODE AND PRI_FACTORY.STAFF_NO = ?)\n");
		parms.add(pSysUser.getStaffNo());
		parms.add(pSysUser.getStaffNo());
		parms.add(pSysUser.getStaffNo());
		sb.append(" order by C.ORG_NO,C.CONS_NO\n");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new PBTRowMapper(), parms.toArray());
	}

	/**
	 * 查询公变采集点内部类
	 * 
	 * @author zhaoliang
	 * 
	 */
	class PBTRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PublicTerminal publicterminal = new PublicTerminal();
			try {
				publicterminal.setOrgName(rs.getString("ORG_NAME"));
				publicterminal.setConsId(rs.getLong("CONS_ID"));
				publicterminal.setTgId(rs.getString("TG_ID"));
				publicterminal.setConsNo(rs.getString("CONS_NO"));
				publicterminal.setConsName(rs.getString("CONS_NAME"));
				publicterminal.setElecAddr(rs.getString("ELEC_ADDR"));
				publicterminal.setRunCap(rs.getDouble("RUN_CAP"));
				publicterminal.setContractCap(rs.getDouble("CONTRACT_CAP"));
				Double capTop1 = rs.getDouble("RUN_CAP");
				Double capTop2 = rs.getDouble("CONTRACT_CAP");
				String capTop = capTop2.toString() + "/" + capTop1.toString();
				publicterminal.setCapTop(capTop);
				publicterminal.setVolt(rs.getString("VOLT"));
				publicterminal.setTmnlAssetNo(rs.getString("CIS_ASSET_NO"));
				publicterminal.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				publicterminal.setSimNo(rs.getString("SIM_NO"));
				publicterminal.setTmnlType(rs.getString("TMNL_TYPE"));
				publicterminal.setFactoryName(rs.getString("FACTORY_NAME"));
				publicterminal.setCommMode(rs.getString("COMM_MODE"));
				publicterminal.setStatusName(rs.getString("STATUS_NAME"));
				publicterminal.setRunDate(rs.getString("RUN_DATE"));
				return publicterminal;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
