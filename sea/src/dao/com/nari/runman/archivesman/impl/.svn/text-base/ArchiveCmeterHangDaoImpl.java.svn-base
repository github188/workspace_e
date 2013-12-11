package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runman.archivesman.IArchiveCmeterHangDao;
import com.nari.runman.runstatusman.CMeterDto;

/**
 * 电能表挂表信息管理DAO实现
 * @author Taoshide
 *
 */
public class ArchiveCmeterHangDaoImpl extends JdbcBaseDAOImpl  implements IArchiveCmeterHangDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CMeterDto> queryCmeterIsHang(String cpNo) {
		
		String sql = "select cc.cons_no,cc.cons_name,cm.inst_loc,cc.mr_sect_no,cm.tmnl_asset_no,cm.fmr_asset_no,cm.asset_no " +
				    "   from sea.c_meter cm "+
					"	left join sea.c_cons cc on cm.cons_no = cc.cons_no"+
					"	inner join sea.r_Coll_Obj rco on rco.meter_id = cm.meter_id where rco.cp_no = ?";
		
		return super.getJdbcTemplate().query(sql, new Object[]{cpNo}, new CMeterRowMapper());
	}

	
	class CMeterRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			CMeterDto cMeterDto = new CMeterDto();
			try {
				cMeterDto.setConsno(rs.getString("cons_no"));
				cMeterDto.setConsname(rs.getString("cons_name"));
				cMeterDto.setInstloc(rs.getString("inst_loc"));
				cMeterDto.setMrsectno(rs.getString("mr_sect_no"));
				cMeterDto.setTmnlassetno(rs.getString("tmnl_asset_no"));
				cMeterDto.setFmrassetno(rs.getString("fmr_asset_no"));
				cMeterDto.setAssetno(rs.getString("asset_no"));
				return cMeterDto;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
}
