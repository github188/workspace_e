package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.ltd.getahead.dwr.util.Logger;

import com.nari.baseapp.datagatherman.HeartBeat;
import com.nari.util.DateUtil;

public class HeartBeatMapper implements RowMapper {
	private Logger log=Logger.getLogger(HeartBeatMapper.class);
	@Override
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
		HeartBeat hb = new HeartBeat();
		try {
			hb.setOrgName(rs.getString("ORG_NAME"));
			hb.setConsNo(rs.getString("CONS_NO"));
			hb.setConsName(rs.getString("CONS_NAME"));
			hb.setTmnlAddr(rs.getString("Terminal_Addr"));
			hb.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
			hb.setIsOnLine(rs.getString("IS_ONLINE"));
			hb.setOnOffTime(DateUtil.timeStampToStr(rs.getTimestamp("ONOFF_TIME")));		
			return hb;
		} catch (Exception e) {
			log.debug("实体映射出错");
			return null;
		}
	}
}