package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.querycollpoint.CollectionCountDTO;
import com.nari.qrystat.querycollpoint.CollectionCountDao;
import com.nari.support.Page;

public class CollectionCountDaoImpl extends JdbcBaseDAOImpl implements CollectionCountDao{
	Logger logger = Logger.getLogger(CollectionCountDaoImpl.class);
	private CollectionCountDTO collectionCountDTO;
	public void setCollectionCountDTO(CollectionCountDTO collectionCountDTO) {
		this.collectionCountDTO = collectionCountDTO;
	}
	@Override
	public Page<CollectionCountDTO> findCollectionCount(String treeType,String orgNo,String consNo,
			long start, int limit,PSysUser pSysUser,String dateStart,String dateEnd) {
		StringBuffer sb=new StringBuffer();
		Object[] objects = new Object[]{orgNo,dateStart,dateEnd};
		String sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.ORG_NO =? ");
		if(treeType != null && treeType.equals("03")){
			 sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?) ");
		}else if(treeType != null && treeType.equals("04")){
			 sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.P_ORG_NO = ? ");
			}
		sb.append("SELECT O.ORG_NAME,\n");
		sb.append("       O.ORG_NO,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '01', 1, NULL)),0) as TMNL_TASK_TYPE1,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '02', 1, NULL)),0) as TMNL_TASK_TYPE2,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '03', 1, NULL)),0) as TMNL_TASK_TYPE3,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '04', 1, NULL)),0) as TMNL_TASK_TYPE4,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '05', 1, NULL)),0) as TMNL_TASK_TYPE5,\n");
		sb.append("       nvl(COUNT(decode(I.TMNL_TASK_TYPE, '06', 1, NULL)),0) as TMNL_TASK_TYPE6\n");
		sb.append("  FROM i_tmnl_task I, O_ORG O\n");
		sb.append(sqlString);
		sb.append(" AND I.DEBUG_TIME BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') ");
		sb.append(" group by O.ORG_NO, O.ORG_NAME");
		String sql=sb.toString();
this.logger.debug(sql);
try{
	return  super.pagingFind(sb.toString(),start,limit,new CollectionCountRowMapper(),objects);
}catch(RuntimeException e){
	this.logger.debug(sql);
	throw e;
}
	}
	

	@Override
	public List<CollectionCountDTO> findCollectionCountSUM(String treeType,String orgNo,String consNo,
			PSysUser pSysUser,String dateStart,String dateEnd) {
		StringBuffer sb=new StringBuffer();
		Object[] objects = new Object[]{orgNo,dateStart,dateEnd};
		String sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.ORG_NO =? ");
		if(treeType != null && treeType.equals("03")){
			 sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?) ");
		}else if(treeType != null && treeType.equals("04")){
			 sqlString = new String(" WHERE I.ORG_NO = O.ORG_NO\n AND O.P_ORG_NO = ? ");
			}
		sb.append("SELECT nvl(SUM(decode(I.TMNL_TASK_TYPE, '01', 1, NULL)),0) as TMNL_TASK_TYPE1SUM,\n");
		sb.append("       nvl(SUM(decode(I.TMNL_TASK_TYPE, '02', 1, NULL)),0) as TMNL_TASK_TYPE2SUM,\n");
		sb.append("       nvl(SUM(decode(I.TMNL_TASK_TYPE, '03', 1, NULL)),0) as TMNL_TASK_TYPE3SUM,\n");
		sb.append("       nvl(SUM(decode(I.TMNL_TASK_TYPE, '04', 1, NULL)),0) as TMNL_TASK_TYPE4SUM,\n");
		sb.append("       nvl(SUM(decode(I.TMNL_TASK_TYPE, '05', 1, NULL)),0) as TMNL_TASK_TYPE5SUM,\n");
		sb.append("       nvl(SUM(decode(I.TMNL_TASK_TYPE, '06', 1, NULL)),0) as TMNL_TASK_TYPE6SUM \n");
		sb.append("  FROM i_tmnl_task I, O_ORG O\n");
		sb.append(sqlString);
		sb.append(" AND I.DEBUG_TIME BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') ");
		String sql=sb.toString();
			this.logger.debug(sql);
	return  (List<CollectionCountDTO>)super.getJdbcTemplate().query(sb.toString(),objects,new CollectionCountSUMRowMapper());
	}
	
	class CollectionCountRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		CollectionCountDTO collectioncountdto = new CollectionCountDTO();
		 try { 
		collectioncountdto.setOrgName(rs.getString("ORG_NAME"));
		collectioncountdto.setOrgNo(rs.getString("ORG_NO"));
		collectioncountdto.setTmnlTaskType1(rs.getLong("TMNL_TASK_TYPE1"));
		collectioncountdto.setTmnlTaskType2(rs.getLong("TMNL_TASK_TYPE2"));
		collectioncountdto.setTmnlTaskType3(rs.getLong("TMNL_TASK_TYPE3"));
		collectioncountdto.setTmnlTaskType4(rs.getLong("TMNL_TASK_TYPE4"));
		collectioncountdto.setTmnlTaskType5(rs.getLong("TMNL_TASK_TYPE5"));
		collectioncountdto.setTmnlTaskType6(rs.getLong("TMNL_TASK_TYPE6"));
		return collectioncountdto;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	class CollectionCountSUMRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		CollectionCountDTO collectioncountdto = new CollectionCountDTO();
		 try { 
		collectioncountdto.setTmnlTaskType1(rs.getLong("TMNL_TASK_TYPE1SUM"));
		collectioncountdto.setTmnlTaskType2(rs.getLong("TMNL_TASK_TYPE2SUM"));
		collectioncountdto.setTmnlTaskType3(rs.getLong("TMNL_TASK_TYPE3SUM"));
		collectioncountdto.setTmnlTaskType4(rs.getLong("TMNL_TASK_TYPE4SUM"));
		collectioncountdto.setTmnlTaskType5(rs.getLong("TMNL_TASK_TYPE5SUM"));
		collectioncountdto.setTmnlTaskType6(rs.getLong("TMNL_TASK_TYPE6SUM"));
		return collectioncountdto;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
}
