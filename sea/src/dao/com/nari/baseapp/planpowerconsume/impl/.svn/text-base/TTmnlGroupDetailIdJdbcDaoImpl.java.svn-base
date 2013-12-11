package com.nari.baseapp.planpowerconsume.impl;
/**
 * 群组明细JdbcDao接口实现类
 * @author 姜海辉
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.ITTmnlGroupDetailIdJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.terminalparam.TTmnlGroupDetailDto;
import com.nari.util.exception.DBAccessException;

public class TTmnlGroupDetailIdJdbcDaoImpl extends JdbcBaseDAOImpl implements
		ITTmnlGroupDetailIdJdbcDao {
	    
	/**
	    * 删除控制群组明细
	    * @param groupNo 群组编号
	    * @throws DBAccessException
	    */
		public void deleteCtrlGroupDetail(String groupNo)throws DBAccessException{
			try{
				String sql ="delete from t_tmnl_group_detail t where t.group_no=?";
				this.getJdbcTemplate().update(sql, new Object[]{groupNo});
			}catch(RuntimeException e){
				this.logger.debug("删除出错！");
				throw e;
			}
		}
		
		/**
	    * 删除普通群组明细
	    * @param groupNo 群组编号
	    * @throws DBAccessException
	    */
		public void deleteCommonGroupDetail(String groupNo)throws DBAccessException{
			try{
				String sql ="delete from R_USER_GROUP_DETAIL RD WHERE RD.GROUP_NO=?";
				this.getJdbcTemplate().update(sql, new Object[]{groupNo});
			}catch(RuntimeException e){
				this.logger.debug("删除出错！");
				throw e;
			}
		}
		/**
		 * 查询控制群组明细
		 * @param groupNo 群组编号
		 * @param ConsNo 用户编号
		 * @throws DBAccessException
		 */
		public Page<TTmnlGroupDetailDto> queryCtrlGroupDetail(String groupNo,String ConsNo,long start,long limit)throws DBAccessException{
			List<Object> list = new ArrayList<Object>();
			String sql =
				"SELECT O.ORG_NAME,\n" + 
				"       C.CONS_NO,\n"  +             
				"       C.CONS_NAME,\n" + 
				"       C.ELEC_ADDR,\n" + 
				"       G.LINE_NAME,\n" + 
				"       B.TRADE_NAME,\n" + 
				"       TD.TMNL_ASSET_NO,\n" + 
				"       T.CREATE_DATE,\n" + 
				"       T.VALID_DAYS\n" +
				"  FROM T_TMNL_GROUP_DETAIL TD,\n" + 
				"       O_ORG               O,\n" + 
				"       C_CONS              C,\n" + 
				"       G_LINE              G,\n" + 
				"       VW_TRADE           B,\n" + 
				"       T_TMNL_GROUP        T\n" + 
				" WHERE C.CONS_NO = TD.CONS_NO\n" + 
				"   AND O.ORG_NO = C.ORG_NO\n" + 
				"   AND G.LINE_ID = C.LINE_ID\n" + 
				"   AND B.TRADE_NO(+) = C.TRADE_CODE\n" + 
				"   AND T.GROUP_NO = TD.GROUP_NO\n" + 
				"   AND TD.GROUP_NO = ?";
			list.add(groupNo);
			if(null!=ConsNo&&!"".equals(ConsNo)){
				sql=sql+"   AND TD.CONS_NO LIKE '%'||?||'%'";
				list.add(ConsNo);
			}
			try{
				return super.pagingFind(sql, start, limit, new TTmnlGroupDetailRowMapper(),list.toArray());
			}catch(RuntimeException e){
				this.logger.debug("查询出错！");
				throw e;
			}
		}
		
		/**
		 * 查询普通群组明细
		 * @param groupNo 群组编号
		 * @param ConsNo 用户编号
		 * @throws DBAccessException
		 */
		public Page<TTmnlGroupDetailDto> queryCommonGroupDetail(String groupNo,String ConsNo,long start,long limit)throws DBAccessException{
			List<Object> list = new ArrayList<Object>();
			String sql =
				"SELECT O.ORG_NAME,\n" +
				"       C.CONS_NO,\n"  +     
				"       C.CONS_NAME,\n" + 
				"       C.ELEC_ADDR,\n" + 
				"       G.LINE_NAME,\n" + 
				"       B.TRADE_NAME,\n" + 
				"       RD.TMNL_ASSET_NO,\n" + 
				"       R.CREATE_DATE,\n" + 
				"       R.VALID_DAYS\n" + 
				"  FROM R_USER_GROUP_DETAIL RD,\n" + 
				"       O_ORG               O,\n" + 
				"       C_CONS              C,\n" + 
				"       G_LINE              G,\n" + 
				"       VW_TRADE           B,\n" + 
				"       r_User_Group        R\n" + 
				" WHERE C.CONS_NO = RD.CONS_NO\n" + 
				"   AND O.ORG_NO = C.ORG_NO\n" + 
				"   AND G.LINE_ID = C.LINE_ID\n" + 
				"   AND B.TRADE_NO(+) = C.TRADE_CODE\n" + 
				"   AND R.GROUP_NO = RD.GROUP_NO\n" + 
				"   AND RD.GROUP_NO = ?";
			list.add(groupNo);
			if(null!=ConsNo&&!"".equals(ConsNo)){
				sql=sql+"   AND RD.CONS_NO LIKE '%'||?||'%'";
				list.add(ConsNo);
			}
			System.out.println(sql);
			try{
				return super.pagingFind(sql, start, limit, new TTmnlGroupDetailRowMapper(),list.toArray());
			}catch(RuntimeException e){
				this.logger.debug("查询出错！");
				throw e;
			}
		}

		class TTmnlGroupDetailRowMapper implements RowMapper{
			public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
				TTmnlGroupDetailDto tTmnlGroupDetailDto=new TTmnlGroupDetailDto();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try{
					tTmnlGroupDetailDto.setOrgName(rs.getString("ORG_NAME"));
					tTmnlGroupDetailDto.setConsNo(rs.getString("CONS_NO"));
					tTmnlGroupDetailDto.setConsName(rs.getString("CONS_NAME"));
					tTmnlGroupDetailDto.setConsAddr(rs.getString("Elec_Addr"));
					tTmnlGroupDetailDto.setLine(rs.getString("Line_Name"));
					tTmnlGroupDetailDto.setTrade(rs.getString("Trade_Name"));
					tTmnlGroupDetailDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
					tTmnlGroupDetailDto.setStartDate(sdf.format(rs.getDate("create_date")));
					tTmnlGroupDetailDto.setFinishDate(sdf.format(new Date(rs.getDate("create_date").getTime()+rs.getLong("valid_days")*24*60*60*1000)));
					return  tTmnlGroupDetailDto;
				}catch (Exception e) {
					return null;
				}
			}
		}
}
