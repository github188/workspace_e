package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runcontrol.CmpJdbc;
import com.nari.runman.archivesman.IArchiveCmpManDao;

/**
 * DAO 实现 ArchiveCmpManDaoImpl
 * 
 * @author zhangzhw
 * @describe 计量点DAO实现
 */
public class ArchiveCmpManDaoImpl extends JdbcBaseDAOImpl implements
		IArchiveCmpManDao {

	/**
	 * 通过consNo查询Cmp 列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CmpJdbc> queryCmpByConsNo(String consNo) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MP_ID,\n");
		sb.append("       MP_SECT_ID,\n");
		sb.append("       SP_ID,\n");
		sb.append("       MP_NO,\n");
		sb.append("       MP_NAME,\n");
		sb.append("       ORG_NO,\n");
		sb.append("       CONS_ID,\n");
		sb.append("       CONS_NO,\n");
		sb.append("       TG_ID,\n");
		sb.append("       LINE_ID,\n");
		sb.append("       MR_SECT_NO,\n");
		sb.append("       MP_ADDR,\n");
		sb.append("       TYPE_CODE,\n");
		sb.append("       MP_ATTR_CODE,\n");
		sb.append("       USAGE_TYPE_CODE,\n");
		sb.append("       SIDE_CODE,\n");
		sb.append("       VOLT_CODE,\n");
		sb.append("       APP_DATE,\n");
		sb.append("       RUN_DATE,\n");
		sb.append("       WIRING_MODE,\n");
		sb.append("       MEAS_MODE,\n");
		sb.append("       SWITCH_NO,\n");
		sb.append("       EXCHG_TYPE_CODE,\n");
		sb.append("       MD_TYPE_CODE,\n");
		sb.append("       MR_SN,\n");
		sb.append("       MP_SN,\n");
		sb.append("       METER_FLAG,\n");
		sb.append("       STATUS_CODE,\n");
		sb.append("       LC_FLAG,\n");
		sb.append("       EARTH_MODE\n");
		sb.append("  FROM C_MP\n");
		sb.append(" WHERE CONS_NO = ?");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { consNo },
				new CmpRowMapper());
	}

	/**
	 * 保存Cmp列表
	 * 
	 * @see 未判断是否重复
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveCmp(CmpJdbc[] cmpJdbc) {

		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		for (CmpJdbc cmp : cmpJdbc) {
			if (null == cmp.getMpId() || 0 == cmp.getMpId()) {
				sqlList.add(saveCmpSql(cmp));
				paramList.add(saveCmpList(cmp));
			} else {
				sqlList.add(updateCmpSql(cmp));
				paramList.add(updateCmpList(cmp));
			}
		}

		super.doTransaction(sqlList, paramList, "保存计量点信息时出错");

		return true;
	}

	/**
	 * 方法 saveCmpSql
	 * 
	 * @param cmpjdbc
	 * @return 保存 Cmp 的Sql
	 */
	private String saveCmpSql(CmpJdbc cmpjdbc) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_MP\n");
		sb.append("  (MP_ID,\n");
		sb.append("   MP_SECT_ID,\n");
		sb.append("   SP_ID,\n");
		sb.append("   MP_NO,\n");
		sb.append("   MP_NAME,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   CONS_ID,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   TG_ID,\n");
		sb.append("   LINE_ID,\n");
		sb.append("   MR_SECT_NO,\n");
		sb.append("   MP_ADDR,\n");
		sb.append("   TYPE_CODE,\n");
		sb.append("   MP_ATTR_CODE,\n");
		sb.append("   USAGE_TYPE_CODE,\n");
		sb.append("   SIDE_CODE,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   APP_DATE,\n");
		sb.append("   RUN_DATE,\n");
		sb.append("   WIRING_MODE,\n");
		sb.append("   MEAS_MODE,\n");
		sb.append("   SWITCH_NO,\n");
		sb.append("   EXCHG_TYPE_CODE,\n");
		sb.append("   MD_TYPE_CODE,\n");
		sb.append("   MR_SN,\n");
		sb.append("   MP_SN,\n");
		sb.append("   METER_FLAG,\n");
		sb.append("   STATUS_CODE,\n");
		sb.append("   LC_FLAG,\n");
		sb.append("   EARTH_MODE)\n");
		sb.append("VALUES\n");
		sb.append("  (S_C_MP.NEXTVAL, --MP_ID,\n");
		sb.append("   ?, --     MP_SECT_ID,\n");
		sb.append("   ?, --     SP_ID,\n");
		sb.append("   ?, --     MP_NO,\n");
		sb.append("   ?, --     MP_NAME,\n");
		sb.append("   ?, --     ORG_NO,\n");
		sb.append("   ?, --     CONS_ID,\n");
		sb.append("   ?, --     CONS_NO,\n");
		sb.append("   ?, --     TG_ID,\n");
		sb.append("   ?, --     LINE_ID,\n");
		sb.append("   ?, --     MR_SECT_NO,\n");
		sb.append("   ?, --     MP_ADDR,\n");
		sb.append("   ?, --     TYPE_CODE,\n");
		sb.append("   ?, --     MP_ATTR_CODE,\n");
		sb.append("   ?, --     USAGE_TYPE_CODE,\n");
		sb.append("   ?, --     SIDE_CODE,\n");
		sb.append("   ?, --     VOLT_CODE,\n");
		sb.append("   ?, --     APP_DATE,\n");
		sb.append("   ?, --     RUN_DATE,\n");
		sb.append("   ?, --     WIRING_MODE,\n");
		sb.append("   ?, --     MEAS_MODE,\n");
		sb.append("   ?, --     SWITCH_NO,\n");
		sb.append("   ?, --     EXCHG_TYPE_CODE,\n");
		sb.append("   ?, --     MD_TYPE_CODE,\n");
		sb.append("   ?, --     MR_SN,\n");
		sb.append("   ?, --     MP_SN,\n");
		sb.append("   ?, --     METER_FLAG,\n");
		sb.append("   ?, --     STATUS_CODE,\n");
		sb.append("   ?, --     LC_FLAG,\n");
		sb.append("   ? --     EARTH_MODE\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 saveCmpList
	 * 
	 * @param cmpjdbc
	 * @return 保存Cmp的SQL参数
	 */
	private List<Object> saveCmpList(CmpJdbc cmpjdbc) {
		List<Object> list = new ArrayList<Object>();
		//list.add(cmpjdbc.getMpId());
		list.add(cmpjdbc.getMpSectId());
		list.add(cmpjdbc.getSpId());
		list.add(cmpjdbc.getMpNo());
		list.add(cmpjdbc.getMpName());
		list.add(cmpjdbc.getOrgNo());
		list.add(cmpjdbc.getConsId());
		list.add(cmpjdbc.getConsNo());
		list.add(cmpjdbc.getTgId());
		list.add(cmpjdbc.getLineId());
		list.add(cmpjdbc.getMrSectNo());
		list.add(cmpjdbc.getMpAddr());
		list.add(cmpjdbc.getTypeCode());
		list.add(cmpjdbc.getMpAttrCode());
		list.add(cmpjdbc.getUsageTypeCode());
		list.add(cmpjdbc.getSideCode());
		list.add(cmpjdbc.getVoltCode());
		list.add(cmpjdbc.getAppDate());
		list.add(cmpjdbc.getRunDate());
		list.add(cmpjdbc.getWiringMode());
		list.add(cmpjdbc.getMeasMode());
		list.add(cmpjdbc.getSwitchNo());
		list.add(cmpjdbc.getExchgTypeCode());
		list.add(cmpjdbc.getMdTypeCode());
		list.add(cmpjdbc.getMrSn());
		list.add(cmpjdbc.getMpSn());
		list.add(cmpjdbc.getMeterFlag());
		list.add(cmpjdbc.getStatusCode());
		list.add(cmpjdbc.getLcFlag());
		list.add(cmpjdbc.getEarthMode());

		return list;
	}

	/**
	 * 方法 updateCmpSql
	 * 
	 * @param cmpjdbc
	 * @return 更新Cmp 的SQL
	 */
	private String updateCmpSql(CmpJdbc cmpjdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE C_MP\n");
		sb.append("   SET MP_SECT_ID      = ?,\n");
		sb.append("       SP_ID           = ?,\n");
		sb.append("       MP_NO           = ?,\n");
		sb.append("       MP_NAME         = ?,\n");
		sb.append("       ORG_NO          = ?,\n");
		sb.append("       CONS_ID         = ?,\n");
		sb.append("       CONS_NO         = ?,\n");
		sb.append("       TG_ID           = ?,\n");
		sb.append("       LINE_ID         = ?,\n");
		sb.append("       MR_SECT_NO      = ?,\n");
		sb.append("       MP_ADDR         = ?,\n");
		sb.append("       TYPE_CODE       = ?,\n");
		sb.append("       MP_ATTR_CODE    = ?,\n");
		sb.append("       USAGE_TYPE_CODE = ?,\n");
		sb.append("       SIDE_CODE       = ?,\n");
		sb.append("       VOLT_CODE       = ?,\n");
		sb.append("       APP_DATE        = ?,\n");
		sb.append("       RUN_DATE        = ?,\n");
		sb.append("       WIRING_MODE     = ?,\n");
		sb.append("       MEAS_MODE       = ?,\n");
		sb.append("       SWITCH_NO       = ?,\n");
		sb.append("       EXCHG_TYPE_CODE = ?,\n");
		sb.append("       MD_TYPE_CODE    = ?,\n");
		sb.append("       MR_SN           = ?,\n");
		sb.append("       MP_SN           = ?,\n");
		sb.append("       METER_FLAG      = ?,\n");
		sb.append("       STATUS_CODE     = ?,\n");
		sb.append("       LC_FLAG         = ?,\n");
		sb.append("       EARTH_MODE      = ?\n");
		sb.append(" WHERE MP_ID = ?");

		String sql = sb.toString();
		return sql;

	}

	/**
	 * 方法 updateCmpList
	 * 
	 * @param cmpjdbc
	 * @return 更新 Cmp 的SQL参数
	 */
	private List<Object> updateCmpList(CmpJdbc cmpjdbc) {
		List<Object> list = new ArrayList<Object>();
		list.add(cmpjdbc.getMpSectId());
		list.add(cmpjdbc.getSpId());
		list.add(cmpjdbc.getMpNo());
		list.add(cmpjdbc.getMpName());
		list.add(cmpjdbc.getOrgNo());
		list.add(cmpjdbc.getConsId());
		list.add(cmpjdbc.getConsNo());
		list.add(cmpjdbc.getTgId());
		list.add(cmpjdbc.getLineId());
		list.add(cmpjdbc.getMrSectNo());
		list.add(cmpjdbc.getMpAddr());
		list.add(cmpjdbc.getTypeCode());
		list.add(cmpjdbc.getMpAttrCode());
		list.add(cmpjdbc.getUsageTypeCode());
		list.add(cmpjdbc.getSideCode());
		list.add(cmpjdbc.getVoltCode());
		list.add(cmpjdbc.getAppDate());
		list.add(cmpjdbc.getRunDate());
		list.add(cmpjdbc.getWiringMode());
		list.add(cmpjdbc.getMeasMode());
		list.add(cmpjdbc.getSwitchNo());
		list.add(cmpjdbc.getExchgTypeCode());
		list.add(cmpjdbc.getMdTypeCode());
		list.add(cmpjdbc.getMrSn());
		list.add(cmpjdbc.getMpSn());
		list.add(cmpjdbc.getMeterFlag());
		list.add(cmpjdbc.getStatusCode());
		list.add(cmpjdbc.getLcFlag());
		list.add(cmpjdbc.getEarthMode());
		list.add(cmpjdbc.getMpId());

		return list;
	}

	/**
	 * 内部类 CmpRowMapper
	 * 
	 * @author zhangzhw
	 * 
	 */
	class CmpRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CmpJdbc cmpjdbc = new CmpJdbc();
			try {
				cmpjdbc.setMpId(rs.getLong("MP_ID"));
				cmpjdbc.setMpSectId(rs.getLong("MP_SECT_ID"));
				cmpjdbc.setSpId(rs.getLong("SP_ID"));
				cmpjdbc.setMpNo(rs.getString("MP_NO"));
				cmpjdbc.setMpName(rs.getString("MP_NAME"));
				cmpjdbc.setOrgNo(rs.getString("ORG_NO"));
				cmpjdbc.setConsId(rs.getLong("CONS_ID"));
				cmpjdbc.setConsNo(rs.getString("CONS_NO"));
				cmpjdbc.setTgId(rs.getLong("TG_ID"));
				cmpjdbc.setLineId(rs.getLong("LINE_ID"));
				cmpjdbc.setMrSectNo(rs.getString("MR_SECT_NO"));
				cmpjdbc.setMpAddr(rs.getString("MP_ADDR"));
				cmpjdbc.setTypeCode(rs.getString("TYPE_CODE"));
				cmpjdbc.setMpAttrCode(rs.getString("MP_ATTR_CODE"));
				cmpjdbc.setUsageTypeCode(rs.getString("USAGE_TYPE_CODE"));
				cmpjdbc.setSideCode(rs.getString("SIDE_CODE"));
				cmpjdbc.setVoltCode(rs.getString("VOLT_CODE"));
				cmpjdbc.setAppDate(rs.getTimestamp("APP_DATE"));
				cmpjdbc.setRunDate(rs.getTimestamp("RUN_DATE"));
				cmpjdbc.setWiringMode(rs.getString("WIRING_MODE"));
				cmpjdbc.setMeasMode(rs.getString("MEAS_MODE"));
				cmpjdbc.setSwitchNo(rs.getString("SWITCH_NO"));
				cmpjdbc.setExchgTypeCode(rs.getString("EXCHG_TYPE_CODE"));
				cmpjdbc.setMdTypeCode(rs.getString("MD_TYPE_CODE"));
				cmpjdbc.setMrSn(rs.getInt("MR_SN"));
				cmpjdbc.setMpSn(rs.getInt("MP_SN"));
				cmpjdbc.setMeterFlag(rs.getString("METER_FLAG"));
				cmpjdbc.setStatusCode(rs.getString("STATUS_CODE"));
				cmpjdbc.setLcFlag(rs.getString("LC_FLAG"));
				cmpjdbc.setEarthMode(rs.getString("EARTH_MODE"));
				return cmpjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
