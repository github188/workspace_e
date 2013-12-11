package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runcontrol.TDialChannel;
import com.nari.runcontrol.TDialGroupChannel;
import com.nari.runcontrol.TDnnChannel;
import com.nari.runcontrol.TNetChannel;
import com.nari.runman.archivesman.ICollectionChannelDao;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.util.exception.DBAccessException;

/**
 * 实现类 CollectionChannelDaoImpl
 * 
 * @author zhangzhw，yut
 * @describe 档案管理 采集通道维护 DAO实现
 */
public class CollectionChannelDaoImpl extends JdbcBaseDAOImpl implements
		ICollectionChannelDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TDialChannel> queryDialByTmnl(String assetNo) throws DBAccessException{

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DIAL_CHANNEL_ID,\n");
		sb.append("       TMNL_ASSET_NO,\n");
		sb.append("       BAUD_RATE,\n");
		sb.append("       PARITY_BIT,\n");
		sb.append("       STOP_BIT,\n");
		sb.append("       CARRIER_CTRL,\n");
		sb.append("       TXFIFO,\n");
		sb.append("       PHONE_CODE,\n");
		sb.append("       TRY_TIMES,\n");
		sb.append("       DIAL_GROUP_NO,\n");
		sb.append("       TS_IP,\n");
		sb.append("       TS_PORT,\n");
		sb.append("       SERIAL_PORT,\n");
		sb.append("       PROTOCOL_CODE,\n");
		sb.append("       PRI\n");
		sb.append("  FROM T_DIAL_CHANNEL\n");
		sb.append(" WHERE TMNL_ASSET_NO = ?");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { assetNo },
				new TDialChannelRowMapper());
	}
	/**
	 * 根据不同的条件查询拨号通道组
	 * @param queryType 查询条件类型
	 * @param t TDialGroupChannel对象
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TDialGroupChannel> queryDialGroupByValue(String queryType,TDialGroupChannel t) throws DBAccessException{
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DIAL_GROUP_NO,\n");
		sb.append("       DIAL_GROUP_NAME,\n");
		sb.append("       DIAL_TYPE,\n");
		sb.append("       TS_IP,\n");
		sb.append("       TS_PORT,\n");
		sb.append("       IS_USED,\n");
		sb.append("       USED_TIME\n");
		sb.append("  FROM T_DIAL_GROUP_CHANNEL");
		if("dialGroupName_insert".equals(queryType)){
			sb.append(" WHERE DIAL_GROUP_NAME = ?");
			list.add(t.getDialGroupName());
		}else if("dialGroupName_update".equals(queryType)){
			sb.append(" WHERE DIAL_GROUP_NAME = ? AND DIAL_GROUP_NO <> ?");
			list.add(t.getDialGroupName());
			list.add(t.getDialGroupNo());
		}else if("id_insert".equals(queryType)){
			sb.append(" WHERE TS_IP = ? AND TS_PORT = ?");
			list.add(t.getTsIp());
			list.add(t.getTsPort());
		}else if("id_update".equals(queryType)){
			sb.append(" WHERE TS_IP = ? AND TS_PORT = ? AND DIAL_GROUP_NO <> ?");
			list.add(t.getTsIp());
			list.add(t.getTsPort());
			list.add(t.getDialGroupNo());
		}
		String sql = sb.toString();
		
		return super.getJdbcTemplate().query(sql, list.toArray(),
				new TDialChannelRowMapper());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TDialGroupChannel> queryDialGroup() throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DIAL_GROUP_NO,\n");
		sb.append("       DIAL_GROUP_NAME,\n");
		sb.append("       DIAL_TYPE,\n");
		sb.append("       TS_IP,\n");
		sb.append("       TS_PORT,\n");
		sb.append("       IS_USED,\n");
		sb.append("       USED_TIME\n");
		sb.append("  FROM T_DIAL_GROUP_CHANNEL");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql,
				new TDialGroupChannelRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TDnnChannel> queryDnnByTmnl(String assetNo) throws DBAccessException{

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DDN_CHANNEL_ID DNN_CHANNEL_ID,\n");
		sb.append("       TMNL_ASSET_NO,\n");
		sb.append("       COMMAND,\n");
		sb.append("       TS_PORT PORT,\n");
		sb.append("       PROTOCOL_CODE,\n");
		sb.append("       PRI\n");
		sb.append("  FROM T_DDN_CHANNEL\n");
		sb.append(" WHERE TMNL_ASSET_NO = ?");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql,new Object[]{assetNo}, new TDnnChannelRowMapper());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TNetChannel> queryNetByTmnl(String assetNo) throws DBAccessException{

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT NET_CHANNEL_ID,\n");
		sb.append("       TMNL_ASSET_NO,\n");
		sb.append("       IP,\n");
		sb.append("       PORT,\n");
		sb.append("       PROTOCOL_CODE,\n");
		sb.append("       PRI\n");
		sb.append("  FROM T_NET_CHANNEL\n");
		sb.append(" WHERE TMNL_ASSET_NO = ?");

		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql,new Object[]{assetNo},  new TNetChannelRowMapper());

	}

	/**
	 * 新增拨号组
	 * @param tDialGroupChannel
	 */
	public void insertDialGroup(TDialGroupChannel tDialGroupChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO T_DIAL_GROUP_CHANNEL\n");
		sb.append("   (DIAL_GROUP_NAME,\n");
		sb.append("   DIAL_TYPE,\n");
		sb.append("   TS_IP,\n");
		sb.append("   TS_PORT,\n");
		sb.append("   IS_USED,\n");
		sb.append("   USED_TIME,\n");
		sb.append("  DIAL_GROUP_NO)\n");
		sb.append("VALUES\n");
		sb.append("   (?, --       dial_group_name,\n");
		sb.append("   ?, --       dial_type,\n");
		sb.append("   ?, --       ts_ip,\n");
		sb.append("   ?, --       ts_port,\n");
		sb.append("   ?, --       is_used,\n");
		sb.append("   to_date(?,'yyyy-MM-dd'),--       used_time,\n");
		sb.append("   S_DIAL_GROUP_CHANNEL.NEXTVAL --       dial_group_no\n");
		sb.append("   )");

		String sql=sb.toString();
		try{
			List<Object> list = this.saveDialGroupList(tDialGroupChannel);
			list.remove(list.size()-1);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增拨号组出错！");
			throw e;
		}
	}
	
	/**
	 * 新增拨号通道
	 */
	public void insertDial(TDialChannel tDialChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO T_DIAL_CHANNEL\n");
		sb.append("  (TMNL_ASSET_NO,\n");
		sb.append("   BAUD_RATE,\n");
		sb.append("   PARITY_BIT,\n");
		sb.append("   STOP_BIT,\n");
		sb.append("   CARRIER_CTRL,\n");
		sb.append("   TXFIFO,\n");
		sb.append("   PHONE_CODE,\n");
		sb.append("   TRY_TIMES,\n");
		sb.append("   DIAL_GROUP_NO,\n");
		sb.append("   TS_IP,\n");
		sb.append("   TS_PORT,\n");
		sb.append("   SERIAL_PORT,\n");
		sb.append("   PROTOCOL_CODE,\n");
		sb.append("   PRI,\n");
		sb.append("   DIAL_CHANNEL_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --TMNL_ASSET_NO,\n");
		sb.append("   ?, --       BAUD_RATE,\n");
		sb.append("   ?, --       PARITY_BIT,\n");
		sb.append("   ?, --       STOP_BIT,\n");
		sb.append("   ?, --       CARRIER_CTRL,\n");
		sb.append("   ?, --       TXFIFO,\n");
		sb.append("   ?, --       PHONE_CODE,\n");
		sb.append("   ?, --       TRY_TIMES,\n");
		sb.append("   ?, --       DIAL_GROUP_NO,\n");
		sb.append("   ?, --       TS_IP,\n");
		sb.append("   ?, --       TS_PORT,\n");
		sb.append("   ?, --       SERIAL_PORT,\n");
		sb.append("   ?, --       PROTOCOL_CODE,\n");
		sb.append("   ?, --       PRI,\n");
		sb.append("   S_T_DIAL_CHANNEL.Nextval --       DIAL_CHANNEL_ID\n");
		sb.append("   )");

		String sql=sb.toString();
		try{
			List<Object> list = this.saveDialList(tDialChannel);
			list.remove(list.size()-1);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增拨号通道出错！");
			throw e;
		}
	}
	
	/**
	 * 新增网络通道
	 */
	public void insertNet(TNetChannel tNetChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO T_NET_CHANNEL\n");
		sb.append("  (TMNL_ASSET_NO, IP, PORT, PROTOCOL_CODE, PRI, NET_CHANNEL_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (?, ?, ?, ?, ?, s_t_net_channel.nextval)");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveNetList(tNetChannel);
			list.remove(list.size()-1);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增网络通道出错！");
			throw e;
		}
	}
	
	/**
	 * 新增专线通道
	 */
	public void insertDnn(TDnnChannel tDnnChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO T_DDN_CHANNEL\n");
		sb.append("  (TMNL_ASSET_NO, COMMAND, TS_PORT, PROTOCOL_CODE, PRI, DDN_CHANNEL_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --tmnl_asset_no,\n");
		sb.append("   ?, --       command,\n");
		sb.append("   ?, --       port,\n");
		sb.append("   ?, --       protocol_code,\n");
		sb.append("   ?, --       pri,\n");
		sb.append("   s_t_dnn_channel.nextval --dnn_channel_id\n");
		sb.append("   )");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveDnnList(tDnnChannel);
			list.remove(list.size()-1);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增专线通道出错！");
			throw e;
		}
	}
	
	/**
	 * 保存拨号组
	 * @param tDialGroupChannel
	 */
	public void saveDialGroup(TDialGroupChannel tDialGroupChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE T_DIAL_GROUP_CHANNEL\n");
		sb.append("   SET DIAL_GROUP_NAME = ?,\n");
		sb.append("       DIAL_TYPE       = ?,\n");
		sb.append("       TS_IP           = ?,\n");
		sb.append("       TS_PORT         = ?,\n");
		sb.append("       IS_USED         = ?,\n");
		sb.append("       USED_TIME       = to_date(?,'yyyy-MM-dd')\n");
		sb.append(" WHERE DIAL_GROUP_NO = ?");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveDialGroupList(tDialGroupChannel);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存拨号组出错！");
			throw e;
		}
	}
	
	/**
	 * 保存拨号通道
	 */
	public void saveDial(TDialChannel tDialChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE T_DIAL_CHANNEL\n");
		sb.append("   SET TMNL_ASSET_NO = ?,\n");
		sb.append("       BAUD_RATE     = ?,\n");
		sb.append("       PARITY_BIT    = ?,\n");
		sb.append("       STOP_BIT      = ?,\n");
		sb.append("       CARRIER_CTRL  = ?,\n");
		sb.append("       TXFIFO        = ?,\n");
		sb.append("       PHONE_CODE    = ?,\n");
		sb.append("       TRY_TIMES     = ?,\n");
		sb.append("       DIAL_GROUP_NO = ?,\n");
		sb.append("       TS_IP         = ?,\n");
		sb.append("       TS_PORT       = ?,\n");
		sb.append("       SERIAL_PORT   = ?,\n");
		sb.append("       PROTOCOL_CODE = ?,\n");
		sb.append("       PRI           = ?\n");
		sb.append(" WHERE DIAL_CHANNEL_ID = ?");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveDialList(tDialChannel);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存拨号通道出错！");
			throw e;
		}
	}
	
	/**
	 * 保存网络通道
	 */
	public void saveNet(TNetChannel tNetChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE T_NET_CHANNEL\n");
		sb.append("   SET TMNL_ASSET_NO = ?, IP = ?, PORT = ?, PROTOCOL_CODE = ?, PRI = ?\n");
		sb.append(" WHERE NET_CHANNEL_ID = ?");

		String sql=sb.toString();
		try{
			List<Object> list = this.saveNetList(tNetChannel);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存网络通道出错！");
			throw e;
		}
	}
	
	/**
	 * 保存专线通道
	 */
	public void saveDnn(TDnnChannel tDnnChannel) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE T_DNN_CHANNEL\n");
		sb.append("   SET TMNL_ASSET_NO = ?,\n");
		sb.append("       COMMAND       = ?,\n");
		sb.append("       PORT          = ?,\n");
		sb.append("       PROTOCOL_CODE = ?,\n");
		sb.append("       PRI           = ?\n");
		sb.append(" WHERE DNN_CHANNEL_ID = ?");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveDnnList(tDnnChannel);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存专线通道出错！");
			throw e;
		}
	}
	
	/**
	 * 删除拨号组
	 * @param dialGroupNo
	 * @throws DBAccessException
	 */
	public void deleteDialGroup(String dialGroupNo) throws DBAccessException{
		String sql = "DELETE FROM T_DIAL_GROUP_CHANNEL WHERE DIAL_GROUP_NO = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{dialGroupNo});
		}catch (RuntimeException e) {
			this.logger.debug("删除拨号组出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除拨号通道
	 * @throws DBAccessException
	 */
	public void deleteDial(String dialChannelId) throws DBAccessException{
		String sql = "DELETE FROM T_DIAL_CHANNEL WHERE DIAL_CHANNEL_ID = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{dialChannelId});
		}catch (RuntimeException e) {
			this.logger.debug("删除拨号通道出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除网络通道
	 * @throws DBAccessException
	 */
	public void deleteNet(String netChannelId) throws DBAccessException{
		String sql = "DELETE FROM T_NET_CHANNEL WHERE NET_CHANNEL_ID = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{netChannelId});
		}catch (RuntimeException e) {
			this.logger.debug("删除网络通道出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除专线通道
	 * @throws DBAccessException
	 */
	public void deleteDnn(String dnnChannelId) throws DBAccessException{
		String sql = "DELETE FROM T_DDN_CHANNEL WHERE DNN_CHANNEL_ID = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{dnnChannelId});
		}catch (RuntimeException e) {
			this.logger.debug("删除专线通道出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 查询所有规约
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<VwProtocolCode> queryAllProtocol() throws DBAccessException{
		String sql = "SELECT protocol_code,protocol_name FROM vw_protocol_code ORDER BY protocol_code";
		try{
			return this.getJdbcTemplate().query(sql, new ProtocolRowMapper());
		}catch (RuntimeException e) {
			this.logger.debug("查询所有规约出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 保存拨号组的SQL参数
	 * @param t
	 * @return
	 */
	private List<Object> saveDialGroupList(TDialGroupChannel t){
		List<Object> list = new ArrayList<Object>();
		list.add(t.getDialGroupName());
		list.add(t.getDialType());
		list.add(t.getTsIp());
		list.add(t.getTsPort());
		list.add(t.getIsUsed());
		list.add(t.getUsedTime());
		list.add(t.getDialGroupNo());
		return list;
	}
	
	/**
	 * 保存拨号通道的SQL参数
	 * @param t
	 * @return
	 */
	private List<Object> saveDialList(TDialChannel t){
		List<Object> list = new ArrayList<Object>();
		list.add(t.getTmnlAssetNo());
		list.add(t.getBaudRate());
		list.add(t.getParityBit());
		list.add(t.getStopBit());
		list.add(t.getCarrierCtrl());
		list.add(t.getTxfifo());
		list.add(t.getPhoneCode());
		list.add(t.getTryTimes());
		list.add(t.getDialGroupNo());
		list.add(t.getTsIp());
		list.add(t.getTsPort());
		list.add(t.getSerialPort());
		list.add(t.getProtocolCode());
		list.add(t.getPri());
		list.add(t.getDialChannelId());
		return list;
	}
	
	/**
	 * 保存网络通道的SQL参数
	 * @param t
	 * @return
	 */
	private List<Object> saveNetList(TNetChannel t){
		List<Object> list = new ArrayList<Object>();
		list.add(t.getTmnlAssetNo());
		list.add(t.getIp());
		list.add(t.getPort());
		list.add(t.getProtocolCode());
		list.add(t.getPri());
		list.add(t.getNetChannelId());
		return list;
	}
	
	/**
	 * 保存专线通道的SQL参数
	 * @param t
	 * @return
	 */
	private List<Object> saveDnnList(TDnnChannel t){
		List<Object> list = new ArrayList<Object>();
		list.add(t.getTmnlAssetNo());
		list.add(t.getCommand());
		list.add(t.getPort());
		list.add(t.getProtocolCode());
		list.add(t.getPri());
		list.add(t.getDnnChannelId());
		return list;
	}
	
	
	
	/**
	 * 内部类 TDialChannelRowMapper
	 */
	class TDialChannelRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TDialChannel tdialchannel = new TDialChannel();
			try {
				tdialchannel.setDialChannelId(rs.getLong("DIAL_CHANNEL_ID"));
				tdialchannel.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tdialchannel.setBaudRate(rs.getLong("BAUD_RATE"));
				tdialchannel.setParityBit(rs.getInt("PARITY_BIT"));
				tdialchannel.setStopBit(rs.getInt("STOP_BIT"));
				tdialchannel.setCarrierCtrl(rs.getLong("CARRIER_CTRL"));
				tdialchannel.setTxfifo(rs.getLong("TXFIFO"));
				tdialchannel.setPhoneCode(rs.getString("PHONE_CODE"));
				tdialchannel.setTryTimes(rs.getShort("TRY_TIMES"));
				tdialchannel.setDialGroupNo(rs.getShort("DIAL_GROUP_NO"));
				tdialchannel.setTsPort(rs.getInt("TS_PORT"));
				tdialchannel.setSerialPort(rs.getInt("SERIAL_PORT"));
				tdialchannel.setTsIp(rs.getString("TS_IP"));
				tdialchannel.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				tdialchannel.setPri(rs.getShort("PRI"));
				return tdialchannel;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类 TDialGroupChannelRowMapper
	 */
	class TDialGroupChannelRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TDialGroupChannel tdialgroupchannel = new TDialGroupChannel();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tdialgroupchannel.setDialGroupNo(rs.getShort("DIAL_GROUP_NO"));
				tdialgroupchannel.setDialGroupName(rs
						.getString("DIAL_GROUP_NAME"));
				tdialgroupchannel.setDialType(rs.getByte("DIAL_TYPE"));
				tdialgroupchannel.setTsIp(rs.getString("TS_IP"));
				tdialgroupchannel.setTsPort(rs.getInt("TS_PORT"));
				tdialgroupchannel.setIsUsed(rs.getByte("IS_USED"));
				if(rs.getDate("USED_TIME")!=null)
					tdialgroupchannel.setUsedTime(sdf.format(rs.getDate("USED_TIME")));
				else tdialgroupchannel.setUsedTime("");
				return tdialgroupchannel;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类 TDnnChannelRowMapper
	 */
	class TDnnChannelRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TDnnChannel tdnnchannel = new TDnnChannel();
			try {
				tdnnchannel.setDnnChannelId(rs.getLong("DNN_CHANNEL_ID"));
				tdnnchannel.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tdnnchannel.setCommand(rs.getString("COMMAND"));
				tdnnchannel.setPort(rs.getInt("PORT"));
				tdnnchannel.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				tdnnchannel.setPri(rs.getShort("PRI"));
				return tdnnchannel;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类 TNetChannelRowMapper
	 */
	class TNetChannelRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TNetChannel tnetchannel = new TNetChannel();
			try {
				tnetchannel.setNetChannelId(rs.getLong("NET_CHANNEL_ID"));
				tnetchannel.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tnetchannel.setIp(rs.getString("IP"));
				tnetchannel.setPort(rs.getInt("PORT"));
				tnetchannel.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				tnetchannel.setPri(rs.getShort("PRI"));
				return tnetchannel;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 内部类 ProtocolRowMapper
	 */
	class ProtocolRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwProtocolCode t = new VwProtocolCode();
			try {
				t.setProtocolCode(rs.getString("protocol_code"));
				t.setProtocolName(rs.getString("protocol_name"));
				return t;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
