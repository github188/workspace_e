package com.nari.advapp.vipconsman.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nari.advapp.BItemCodeMappingBean;
import com.nari.advapp.IPubDetailBean;
import com.nari.advapp.IPubSchemaBean;
import com.nari.advapp.vipconsman.DataPubDao;

public class DataPubDaoImpl extends JdbcDaoSupport implements DataPubDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<BItemCodeMappingBean> queryItemCode(String dataType) {
		String[] para = { dataType };

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT B.TABLE_NAME,\n");
		sb.append("       B.COLUMN_NAME,\n");
		sb.append("       B.ITEM_NAME,\n");
		sb.append("       B.SEA_ITEM_CODE,\n");
		sb.append("       B.COLL_ITEM_CODE,\n");
		sb.append("       B.DATA_GROUP\n");
		sb.append("  FROM B_ITEM_CODE_MAPPING B\n");
		sb.append(" WHERE B.DATA_GROUP = ?");
		sb.append(" ORDER BY B.SEA_ITEM_CODE");

		return this.getJdbcTemplate().query(sb.toString(),para, new ItemCodeMapper());
	}

	// BItemCodeMappingBean的RowMapper类
	class ItemCodeMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BItemCodeMappingBean tp = new BItemCodeMappingBean();
			try {
				tp.setCollItemCode(rs.getString("COLL_ITEM_CODE"));
				tp.setColumnName(rs.getString("COLUMN_NAME"));
				tp.setDataGroup(rs.getString("DATA_GROUP"));
				tp.setItemName(rs.getString("ITEM_NAME"));
				tp.setSeaItemCode(rs.getString("SEA_ITEM_CODE"));
				tp.setTableName(rs.getString("TABLE_NAME"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IPubDetailBean> queryPubDetail(String pubSchemaId, String dataType) {
		String[] para = { pubSchemaId, dataType };

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT IPD.PUB_SCHEMA_ID, IPD.SEA_DATA_CODE, BICM.ITEM_NAME\n");
		sb.append("  FROM I_PUB_DETAIL IPD, B_ITEM_CODE_MAPPING BICM\n");
		sb.append(" WHERE IPD.PUB_SCHEMA_ID = ? AND BICM.DATA_GROUP = ? AND IPD.SEA_DATA_CODE = BICM.SEA_ITEM_CODE");
		sb.append(" ORDER BY IPD.SEA_DATA_CODE");

		return this.getJdbcTemplate().query(sb.toString(),para, new PubDetailMapper());
	}

	// IPubDetailBean的RowMapper类
	class PubDetailMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			IPubDetailBean tp = new IPubDetailBean();
			try {
				tp.setPubSchemaId(rs.getString("PUB_SCHEMA_ID"));
				tp.setSeaItemCode(rs.getString("SEA_DATA_CODE"));
				tp.setItemName(rs.getString("ITEM_NAME"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IPubSchemaBean> queryPubSchema(String dataType, String consType) {
		String[] para = { dataType, consType };
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT IPS.PUB_SCHEMA_ID,\n");
		sb.append("       IPS.DATA_TYPE,\n");
		sb.append("       IPS.CONS_TYPE,\n");
		sb.append("       IPS.PUB_BASE_TIME,\n");
		sb.append("       IPS.PUB_CYCLE,\n");
		sb.append("       IPS.PUB_CYCLE_UNIT,\n");
		sb.append("       IPS.IS_VALID\n");
		sb.append("  FROM I_PUB_SCHEMA IPS\n");
		sb.append(" WHERE IPS.DATA_TYPE = ?\n");
		sb.append("   AND IPS.CONS_TYPE = ?");

		return this.getJdbcTemplate().query(sb.toString(),para, new PubSchemaMapper());
	}

	// IPubSchemaBean的RowMapper类
	class PubSchemaMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			IPubSchemaBean tp = new IPubSchemaBean();
			try {
				tp.setIsvalid(rs.getString("IS_VALID"));
				tp.setConsType(rs.getString("CONS_TYPE"));
				tp.setDataType(rs.getString("DATA_TYPE"));
				tp.setPubBaseTime(rs.getString("PUB_BASE_TIME"));
				tp.setPubCycle(rs.getString("PUB_CYCLE"));
				tp.setPubCycleUnit(rs.getString("PUB_CYCLE_UNIT"));
				tp.setPubSchemaId(rs.getString("PUB_SCHEMA_ID"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
	
	
	
	@Override
	public void saveOrUpdatePubDetail(final List<IPubDetailBean> pubDetailList) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO I_PUB_DETAIL (PUB_SCHEMA_ID, SEA_DATA_CODE)\n");
		sb.append("  VALUES(?, ?)");

		String sql=sb.toString();

		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				int i = 0;
				IPubDetailBean bpe = pubDetailList.get(index);
				ps.setString(++i, bpe.getPubSchemaId());
				ps.setString(++i, bpe.getSeaItemCode());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return pubDetailList.size();
			}
		});
	}

	@Override
	public void saveOrUpdatePubSchema(IPubSchemaBean iPubSchema) {
		List<Object> paraList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE I_PUB_SCHEMA\n");
		sb.append("   SET PUB_BASE_TIME = ?,\n");
		paraList.add(iPubSchema.getPubBaseTime());
		sb.append("       PUB_CYCLE = ?,\n");
		paraList.add(iPubSchema.getPubCycle());
		sb.append("       PUB_CYCLE_UNIT = ?,\n");
		paraList.add(iPubSchema.getPubCycleUnit());
		sb.append("       IS_VALID = ?\n");
		paraList.add(iPubSchema.getIsvalid());
		sb.append(" WHERE PUB_SCHEMA_ID = ?");
		paraList.add(iPubSchema.getPubSchemaId());
		//更新数据发布计划
		this.getJdbcTemplate().update(sb.toString(), paraList.toArray());
	}

	@Override
	public void deletePubDetailById(String pubSchemaId) {
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(pubSchemaId);
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE I_PUB_DETAIL WHERE PUB_SCHEMA_ID = ?\n");
		this.getJdbcTemplate().update(sb.toString(), paraList.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IPubSchemaBean> loadPubSchema(String consType) {
		String[] para = { consType };
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT IPS.PUB_SCHEMA_ID,\n");
		sb.append("       IPS.DATA_TYPE,\n");
		sb.append("       IPS.CONS_TYPE,\n");
		sb.append("       IPS.PUB_BASE_TIME,\n");
		sb.append("       IPS.PUB_CYCLE,\n");
		sb.append("       IPS.PUB_CYCLE_UNIT,\n");
		sb.append("       IPS.IS_VALID\n");
		sb.append("  FROM I_PUB_SCHEMA IPS\n");
		sb.append(" WHERE IPS.CONS_TYPE = ?");

		return this.getJdbcTemplate().query(sb.toString(),para, new PubSchemaMapper());
	}

}
