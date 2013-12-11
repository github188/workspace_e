package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runcontrol.CmeterJdbc;
import com.nari.runman.archivesman.IArchiveCmeterManDao;

/**
 * DAO 实现 ArchiveCmeterManDaoImpl
 * 
 * @author zhangzhw
 * @describe 电能表DAO实现
 */
public class ArchiveCmeterManDaoImpl extends JdbcBaseDAOImpl implements
		IArchiveCmeterManDao {

	/**
	 * 通过 consNo 查询 Cmeter 列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CmeterJdbc> queryCmeterByConsNo(String consNo) {

		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT METER_ID,\n");
		sb.append("       ASSET_NO,\n");
		sb.append("       MP_ID,\n");
		sb.append("       ORG_NO,\n");
		sb.append("       AREA_NO,\n");
		sb.append("       CONS_NO,\n");
		sb.append("       BAUDRATE,\n");
		sb.append("       COMM_NO,\n");
		sb.append("       COMM_ADDR1,\n");
		sb.append("       COMM_ADDR2,\n");
		sb.append("       COMM_MODE,\n");
		sb.append("       INST_LOC,\n");
		sb.append("       INST_DATE,\n");
		sb.append("       T_FACTOR,\n");
		sb.append("       REF_METER_FLAG,\n");
		sb.append("       REF_METER_ID,\n");
		sb.append("       VALIDATE_CODE,\n");
		sb.append("       MODULE_NO,\n");
		sb.append("       MR_FACTOR,\n");
		sb.append("       LAST_CHK_DATE,\n");
		sb.append("       ROTATE_CYCLE,\n");
		sb.append("       ROTATE_VALID_DATE,\n");
		sb.append("       CHK_CYCLE,\n");
		sb.append("       TMNL_ASSET_NO,\n");
		sb.append("       FMR_ASSET_NO,\n");
		sb.append("       REG_STATUS,\n");
		sb.append("       REG_SN\n");
		sb.append("  FROM C_METER\n");
		sb.append(" WHERE EXISTS (SELECT 1\n");
		sb.append("          FROM C_MP\n");
		sb.append("         WHERE C_MP.MP_ID = C_METER.MP_ID\n");
		sb.append("           AND C_MP.CONS_NO = ?)");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { consNo },
				new CmeterRowMapper());
	}

	/**
	 * 保存Cmeter 列表
	 * 
	 * @see 未判断重复 （电能表资产号不允许重复）
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveCmeter(CmeterJdbc[] cmeterJdbc) {

		List<String> sqlList = new ArrayList<String>();
		List<List> paramList = new ArrayList<List>();

		for (CmeterJdbc cm : cmeterJdbc) {
			if (null == cm.getMeterId() || 0 == cm.getMeterId()) {
				sqlList.add(saveCmeterSql(cm));
				paramList.add(saveCmeterList(cm));
			} else {
				sqlList.add(updateCmeterSql(cm));
				paramList.add(updateCmeterList(cm));
			}
		}

		super.doTransaction(sqlList, paramList, "保存电能表信息时出错");
		return true;
	}

	/**
	 * 方法 saveCmeterSQL
	 * 
	 * @param cmeterjdbc
	 * @return 保存Cmeter的SQL
	 */
	private String saveCmeterSql(CmeterJdbc cmeterjdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO C_METER\n");
		sb.append("  (METER_ID,\n");
		sb.append("   ASSET_NO,\n");
		sb.append("   MP_ID,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   AREA_NO,\n");
		sb.append("   CONS_NO,\n");
		sb.append("   BAUDRATE,\n");
		sb.append("   COMM_NO,\n");
		sb.append("   COMM_ADDR1,\n");
		sb.append("   COMM_ADDR2,\n");
		sb.append("   COMM_MODE,\n");
		sb.append("   INST_LOC,\n");
		sb.append("   INST_DATE,\n");
		sb.append("   T_FACTOR,\n");
		sb.append("   REF_METER_FLAG,\n");
		sb.append("   REF_METER_ID,\n");
		sb.append("   VALIDATE_CODE,\n");
		sb.append("   MODULE_NO,\n");
		sb.append("   MR_FACTOR,\n");
		sb.append("   LAST_CHK_DATE,\n");
		sb.append("   ROTATE_CYCLE,\n");
		sb.append("   ROTATE_VALID_DATE,\n");
		sb.append("   CHK_CYCLE,\n");
		sb.append("   TMNL_ASSET_NO,\n");
		sb.append("   FMR_ASSET_NO,\n");
		sb.append("   REG_STATUS,\n");
		sb.append("   REG_SN)\n");
		sb.append("VALUES\n");		
		sb.append("(    S_C_METER.NEXTVAL, --       METER_ID,\n");
		sb.append("   ?, --       ASSET_NO,\n");
		sb.append("   ?, --       MP_ID,\n");
		sb.append("   ?, --       ORG_NO,\n");
		sb.append("   ?, --       AREA_NO,\n");
		sb.append("   ?, --       CONS_NO,\n");
		sb.append("   ?, --       BAUDRATE,\n");
		sb.append("   ?, --       COMM_NO,\n");
		sb.append("   ?, --       COMM_ADDR1,\n");
		sb.append("   ?, --       COMM_ADDR2,\n");
		sb.append("   ?, --       COMM_MODE,\n");
		sb.append("   ?, --       INST_LOC,\n");
		sb.append("   ?, --       INST_DATE,\n");
		sb.append("   ?, --       T_FACTOR,\n");
		sb.append("   ?, --       REF_METER_FLAG,\n");
		sb.append("   ?, --       REF_METER_ID,\n");
		sb.append("   ?, --       VALIDATE_CODE,\n");
		sb.append("   ?, --       MODULE_NO,\n");
		sb.append("   ?, --       MR_FACTOR,\n");
		sb.append("   ?, --       LAST_CHK_DATE,\n");
		sb.append("   ?, --       ROTATE_CYCLE,\n");
		sb.append("   ?, --       ROTATE_VALID_DATE,\n");
		sb.append("   ?, --       CHK_CYCLE,\n");
		sb.append("   ?, --       TMNL_ASSET_NO,\n");
		sb.append("   ?, --       FMR_ASSET_NO,\n");
		sb.append("   ?, --       REG_STATUS,\n");
		sb.append("   ? --       REG_SN\n");
		sb.append("   )");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 saveCmeterList
	 * 
	 * @param cmeterjdbc
	 * @return 保存Cmeter 的 SQL 参数
	 */
	private List<Object> saveCmeterList(CmeterJdbc cmeterjdbc) {
		List<Object> list = new ArrayList<Object>();
		//list.add(cmeterjdbc.getMeterId());
		list.add(cmeterjdbc.getAssetNo());
		list.add(cmeterjdbc.getMpId());
		list.add(cmeterjdbc.getOrgNo());
		list.add(cmeterjdbc.getAreaNo());
		list.add(cmeterjdbc.getConsNo());
		list.add(cmeterjdbc.getBaudrate());
		list.add(cmeterjdbc.getCommNo());
		list.add(cmeterjdbc.getCommAddr1());
		list.add(cmeterjdbc.getCommAddr2());
		list.add(cmeterjdbc.getCommMode());
		list.add(cmeterjdbc.getInstLoc());
		list.add(cmeterjdbc.getInstDate());
		list.add(cmeterjdbc.gettFactor());
		list.add(cmeterjdbc.getRefMeterFlag());
		list.add(cmeterjdbc.getRefMeterId());
		list.add(cmeterjdbc.getValidateCode());
		list.add(cmeterjdbc.getModuleNo());
		list.add(cmeterjdbc.getMrFactor());
		list.add(cmeterjdbc.getLastChkDate());
		list.add(cmeterjdbc.getRotateCycle());
		list.add(cmeterjdbc.getRotateValidDate());
		list.add(cmeterjdbc.getChkCycle());
		list.add(cmeterjdbc.getTmnlAssetNo());
		list.add(cmeterjdbc.getFmrAssetNo());
		list.add(cmeterjdbc.getRegStatus());
		list.add(cmeterjdbc.getRegSn());

		return list;
	}

	/**
	 * 方法 updateCmeter
	 * 
	 * @param cmeterjdbc
	 * @return 更新Cmeter的SQL
	 */
	private String updateCmeterSql(CmeterJdbc cmeterjdbc) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE C_METER\n");
		sb.append("   SET ASSET_NO          = ?,\n");
		sb.append("       MP_ID             = ?,\n");
		sb.append("       ORG_NO            = ?,\n");
		sb.append("       AREA_NO           = ?,\n");
		sb.append("       CONS_NO           = ?,\n");
		sb.append("       BAUDRATE          = ?,\n");
		sb.append("       COMM_NO           = ?,\n");
		sb.append("       COMM_ADDR1        = ?,\n");
		sb.append("       COMM_ADDR2        = ?,\n");
		sb.append("       COMM_MODE         = ?,\n");
		sb.append("       INST_LOC          = ?,\n");
		sb.append("       INST_DATE         = ?,\n");
		sb.append("       T_FACTOR          = ?,\n");
		sb.append("       REF_METER_FLAG    = ?,\n");
		sb.append("       REF_METER_ID      = ?,\n");
		sb.append("       VALIDATE_CODE     = ?,\n");
		sb.append("       MODULE_NO         = ?,\n");
		sb.append("       MR_FACTOR         = ?,\n");
		sb.append("       LAST_CHK_DATE     = ?,\n");
		sb.append("       ROTATE_CYCLE      = ?,\n");
		sb.append("       ROTATE_VALID_DATE = ?,\n");
		sb.append("       CHK_CYCLE         = ?,\n");
		sb.append("       TMNL_ASSET_NO     = ?,\n");
		sb.append("       FMR_ASSET_NO      = ?,\n");
		sb.append("       REG_STATUS        = ?,\n");
		sb.append("       REG_SN            = ?\n");
		sb.append(" WHERE METER_ID = ?");

		String sql = sb.toString();

		return sql;

	}

	/**
	 * 方法 updateCmeterList
	 * 
	 * @param cmeterjdbc
	 * @return
	 */
	private List<Object> updateCmeterList(CmeterJdbc cmeterjdbc) {
		List<Object> list = new ArrayList<Object>();

		list.add(cmeterjdbc.getAssetNo());
		list.add(cmeterjdbc.getMpId());
		list.add(cmeterjdbc.getOrgNo());
		list.add(cmeterjdbc.getAreaNo());
		list.add(cmeterjdbc.getConsNo());
		list.add(cmeterjdbc.getBaudrate());
		list.add(cmeterjdbc.getCommNo());
		list.add(cmeterjdbc.getCommAddr1());
		list.add(cmeterjdbc.getCommAddr2());
		list.add(cmeterjdbc.getCommMode());
		list.add(cmeterjdbc.getInstLoc());
		list.add(cmeterjdbc.getInstDate());
		list.add(cmeterjdbc.gettFactor());
		list.add(cmeterjdbc.getRefMeterFlag());
		list.add(cmeterjdbc.getRefMeterId());
		list.add(cmeterjdbc.getValidateCode());
		list.add(cmeterjdbc.getModuleNo());
		list.add(cmeterjdbc.getMrFactor());
		list.add(cmeterjdbc.getLastChkDate());
		list.add(cmeterjdbc.getRotateCycle());
		list.add(cmeterjdbc.getRotateValidDate());
		list.add(cmeterjdbc.getChkCycle());
		list.add(cmeterjdbc.getTmnlAssetNo());
		list.add(cmeterjdbc.getFmrAssetNo());
		list.add(cmeterjdbc.getRegStatus());
		list.add(cmeterjdbc.getRegSn());
		list.add(cmeterjdbc.getMeterId());
		return list;
	}

	/**
	 * 内部类 CmeterRowMapper
	 * 
	 * @author zhangzhw
	 * 
	 */
	class CmeterRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CmeterJdbc cmeterjdbc = new CmeterJdbc();
			try {
				cmeterjdbc.setMeterId(rs.getLong("METER_ID"));
				cmeterjdbc.setAssetNo(rs.getString("ASSET_NO"));
				cmeterjdbc.setMpId(rs.getLong("MP_ID"));
				cmeterjdbc.setOrgNo(rs.getString("ORG_NO"));
				cmeterjdbc.setAreaNo(rs.getString("AREA_NO"));
				cmeterjdbc.setConsNo(rs.getString("CONS_NO"));
				cmeterjdbc.setBaudrate(rs.getString("BAUDRATE"));
				cmeterjdbc.setCommNo(rs.getString("COMM_NO"));
				cmeterjdbc.setCommAddr1(rs.getString("COMM_ADDR1"));
				cmeterjdbc.setCommAddr2(rs.getString("COMM_ADDR2"));
				cmeterjdbc.setCommMode(rs.getString("COMM_MODE"));
				cmeterjdbc.setInstLoc(rs.getString("INST_LOC"));
				cmeterjdbc.setInstDate(rs.getTimestamp("INST_DATE"));
				cmeterjdbc.settFactor(rs.getDouble("T_FACTOR"));
				cmeterjdbc.setRefMeterFlag(rs.getString("REF_METER_FLAG"));
				cmeterjdbc.setRefMeterId(rs.getLong("REF_METER_ID"));
				cmeterjdbc.setValidateCode(rs.getString("VALIDATE_CODE"));
				cmeterjdbc.setModuleNo(rs.getString("MODULE_NO"));
				cmeterjdbc.setMrFactor(rs.getString("MR_FACTOR"));
				cmeterjdbc.setLastChkDate(rs.getTimestamp("LAST_CHK_DATE"));
				cmeterjdbc.setRotateCycle(rs.getInt("ROTATE_CYCLE"));
				cmeterjdbc.setRotateValidDate(rs
						.getTimestamp("ROTATE_VALID_DATE"));
				cmeterjdbc.setChkCycle(rs.getInt("CHK_CYCLE"));
				cmeterjdbc.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				cmeterjdbc.setFmrAssetNo(rs.getString("FMR_ASSET_NO"));
				cmeterjdbc.setRegStatus(rs.getByte("REG_STATUS"));
				cmeterjdbc.setRegSn(rs.getInt("REG_SN"));
				return cmeterjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
