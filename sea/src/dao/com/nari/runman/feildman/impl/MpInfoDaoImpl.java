package com.nari.runman.feildman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.elementsdata.EDataMp;
import com.nari.runman.feildman.FetchInfoBean;
import com.nari.runman.feildman.MpInfoDao;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.util.exception.DBAccessException;

public class MpInfoDaoImpl extends JdbcBaseDAOImpl implements MpInfoDao {

	@Override
	public List<EDataMp> findMpInfo(String tmnlAssetNo, short pn) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String[] para = { tmnlAssetNo, String.valueOf(pn) };
		sb.append("select ID, ORG_NO, TMNL_ASSET_NO ,IS_VALID \n").append(
				"from E_DATA_MP \n").append(
				"where TMNL_ASSET_NO = ? AND MP_SN = ?\n");

		return super
				.pagingFindList(sb.toString(), 0, 100, new mpMapper(), para);
	}

	// BCommProtItemList的RowMapper类
	class mpMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EDataMp mpist = new EDataMp();
			try {
				mpist.setDataId(rs.getLong("ID"));
				mpist.setOrgNo(rs.getString("ORG_NO"));
				mpist.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				if (rs.getInt("IS_VALID") == 0) {
					mpist.setIsValid(true);
				} else {
					mpist.setIsValid(false);
				}
				return mpist;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	@Override
	public List<FetchInfoBean> findTmnlMpParam(String proNo,
			String tmnlAssetNo, short[] pn) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
//		StringBuffer pnSb = new StringBuffer();
//		if (pn != null) {
//			pnSb.append("(");
//			for (int i = 0; i < pn.length; i++) {
//				if (i == pn.length - 1) {
//					pnSb.append(pn[i]);
//				} else {
//					pnSb.append(pn[i]);
//					pnSb.append(",");
//				}
//			}
//			pnSb.append(")");
//
//		}
		List<String> para = new ArrayList<String>();
		para.add(tmnlAssetNo);
		para.add(proNo + "%");
//		if (pn != null) {
//			para.add(pnSb.toString());
//		}

		sb.append("SELECT\n").append("e.mp_sn AS mp_sn,\n").append(
				"t.ID AS ID, prot_item_no,\n").append(
				"current_value, history_value, block_sn,\n").append(
				"inner_block_sn,status_code, staff_no,\n").append(
				"save_time, resend_count, send_time,\n").append(
				"success_time, last_send_time,\n").append(
				"next_send_time, failure_code\n").append("FROM\n").append(
				"e_data_mp e,\n").append("t_tmnl_mp_param t\n").append(
				"WHERE\n").append("e.tmnl_asset_no = ?\n").append("AND\n")
				.append("e.is_valid = '1'\n").append(
						"AND e.ID = t.ID\n").append(
						"and t.prot_item_no like ?\n");
		if (pn != null) {
			sb.append("and e.mp_sn in (");
			for (int i = 0; i < pn.length; i++) {
				para.add(String.valueOf(pn[i]));
				if (i == pn.length - 1) {
					sb.append("?");
				} else {
					sb.append("?,");
				}
			}
			sb.append(")\n");
		}
		sb.append("order by mp_sn, prot_item_no");
		return super.pagingFindList(sb.toString(), 0, 100, new tmpMapper(),
				para.toArray());
	}

	// BCommProtItemList的RowMapper类
	class tmpMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			FetchInfoBean tmprs = new FetchInfoBean();
			try {
				// 使用dataId字段存储测量点号
				tmprs.setMpSn(rs.getString("mp_sn"));
				tmprs.setDataId(rs.getLong("ID"));
				tmprs.setBlockSn(rs.getString("block_sn"));
				tmprs.setCurrentValue(rs.getString("current_value"));
				tmprs.setFailureCode(rs.getString("failure_code"));
				tmprs.setHistoryValue(rs.getString("history_value"));
				tmprs.setInnerBlockSn(rs.getInt("inner_block_sn"));
				tmprs.setLastSendTime(rs.getDate("last_send_time"));
				tmprs.setNextSendTime(rs.getDate("next_send_time"));
				tmprs.setProtItemNo(rs.getString("prot_item_no"));
				tmprs.setResendCount(rs.getByte("resend_count"));
				tmprs.setSaveTime(rs.getDate("save_time"));
				tmprs.setSendTime(rs.getDate("send_time"));
				tmprs.setStaffNo(rs.getString("staff_no"));
				tmprs.setStatusCode(rs.getString("status_code"));
				tmprs.setSuccessTime(rs.getDate("success_time"));
				return tmprs;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	@Override
	public long findTmnlMpParamCount(String dataId, String proNo) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String[] para = { dataId, proNo + "%" };
		sb.append("SELECT\n").append("e.mp_sn AS mp_sn,\n").append(
				"t.ID AS ID, prot_item_no,\n").append(
				"current_value, history_value, block_sn,\n").append(
				"inner_block_sn,status_code, staff_no,\n").append(
				"save_time, resend_count, send_time,\n").append(
				"success_time, last_send_time,\n").append(
				"next_send_time, failure_code\n").append("FROM\n").append(
				"e_data_mp e,\n").append("t_tmnl_mp_param t\n").append(
				"WHERE\n").append("e.is_valid = '0'\n").append(
				"AND e.ID = ?\n").append("AND prot_item_no like ?\n");
		return super.pagingFindCount(sb.toString(), para);
	}

	@Override
	public int deleteTmnlMpParam(String dataId, String proNo) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String[] para = { dataId, proNo + "%" };
		sb
				.append("DELETE t_tmnl_mp_param WHERE ID = ? and prot_item_no LIKE ?");
		return super.deletePara(sb.toString(), para);
	}

	@Override
	public int updateMpInfo(List<TTmnlMpParam> tTmnlMpParams)
			throws DBAccessException {
		int count = 0;
		for (TTmnlMpParam tt : tTmnlMpParams) {
			String dataId = String.valueOf(tt.getId().getDataId());
			String protItemNo = tt.getId().getProtItemNo();
			String staturs = tt.getId().getStatusCode();
			String blockSn = tt.getId().getBlockSn();
			String innerBlockSn = tt.getId().getInnerBlockSn().toString();
			String[] para = { staturs, dataId, protItemNo, blockSn,
					innerBlockSn };
			StringBuffer sb = new StringBuffer();
			sb
					.append("update t_tmnl_mp_param\n")
					.append("set status_code = ?\n")
					.append("where ID = ? and prot_item_no = ? and block_sn = ? and inner_block_sn = ?");
			count = count + super.updatePara(sb.toString(), para);
		}
		return count;
	}
}
