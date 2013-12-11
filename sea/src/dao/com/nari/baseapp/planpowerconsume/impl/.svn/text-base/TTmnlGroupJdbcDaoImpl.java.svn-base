package com.nari.baseapp.planpowerconsume.impl;

/**
 * 群组设置JdbcDao接口实现类
 * @author姜海辉
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.ITTmnlGroupJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.terminalparam.TTmnlGroupDto;
import com.nari.util.exception.DBAccessException;

public class TTmnlGroupJdbcDaoImpl extends JdbcBaseDAOImpl implements ITTmnlGroupJdbcDao {
	
	/**
	 * 按条件查找控制群组信息
	 * @param groupType  群组类别
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDto> findCtrlGroupBy(String staffNo,String groupName,Date startDate,Date  finishDate,long start,long limit) throws DBAccessException{
		List<Object> list = new ArrayList<Object>();
		String sql = 
			"select t.group_no,\n" +
			"       o.org_name,\n" + 
			"       t.group_name,\n" + 
			"       v.CTRL_SCHEME_TYPE,\n" + 
			"       t.is_share,\n" + 
			"       t.staff_no,\n" + 
			"       t.create_date,\n" + 
			"       t.valid_days\n" + 
			"  from t_tmnl_group t, o_Org o, vw_ctrl_scheme_type v\n";
        String wheresql=" where o.org_no(+) = t.org_no\n" +
        				" and t.staff_no = ? \n"+
        		        " and v.CTRL_SCHEME_NO = t.ctrl_scheme_type";
        list.add(staffNo);
        if(groupName!=null&&!"".equals(groupName)){
        	wheresql=wheresql+" and t.group_name like '%'||?||'%'  ";
        	list.add(groupName);
        }
        if(startDate!=null&&!"".equals(startDate)&&finishDate!=null&&!"".equals(finishDate)){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String startD=sdf.format(startDate);
            String endD=sdf.format(finishDate);
        	wheresql=wheresql+" and T.VALID_DAYS >= TO_NUMBER(TO_DATE(?, 'YYYY-mm-dd') - T.CREATE_DATE)"+"AND T.VALID_DAYS <= TO_NUMBER(TO_DATE(?, 'YYYY-mm-dd') - T.CREATE_DATE)";
        	list.add(startD);
        	list.add(endD);
        }
        sql=sql+wheresql;
       // System.out.println(sql);
		try{
			return super.pagingFind(sql, start, limit,new TTmnlGroupRowMapper1(),list.toArray());
		}catch(RuntimeException e){
			this.logger.debug("查询群组出错！");
			throw e;
		}
	}
	
	/**
	 * 按条件查找普通群组信息
	 * @param groupType  群组类别
	 * @param groupName  群组名称
	 * @param startDate  开始日期
	 * @param finishDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TTmnlGroupDto> findCommonGroupBy(String staffNo,String groupName,Date startDate,Date finishDate,long start,long limit) throws DBAccessException{
		List<Object> list = new ArrayList<Object>();
		String sql = 
			"SELECT R.GROUP_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       R.GROUP_NAME,\n" + 
			"       R.IS_SHARE,\n" + 
			"       R.STAFF_NO,\n" + 
			"       R.CREATE_DATE,\n" + 
			"       R.VALID_DAYS\n" + 
			"  FROM R_USER_GROUP R, O_ORG O, P_SYS_USER P\n" ;
        String wheresql=" WHERE O.ORG_NO(+) = P.ORG_NO\n" + 
		                " AND P.STAFF_NO = R.STAFF_NO\n"+
		                " AND R.STAFF_NO = ?";
        list.add(staffNo);
        if(groupName!=null&&!"".equals(groupName)){
        	wheresql=wheresql+" and r.group_name like '%'||?||'%' ";
        	list.add(groupName);
        }
        if(startDate!=null&&!"".equals(startDate)&&finishDate!=null&&!"".equals(finishDate)){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String startD=sdf.format(startDate);
            String endD=sdf.format(finishDate);
        	wheresql=wheresql+" and r.VALID_DAYS >= TO_NUMBER(TO_DATE(?, 'YYYY-mm-dd') - r.CREATE_DATE)"+"AND r.VALID_DAYS <= TO_NUMBER(TO_DATE(?, 'YYYY-mm-dd') - r.CREATE_DATE)";
        	list.add(startD);
        	list.add(endD);
        }
        sql=sql+wheresql;
       // System.out.println(sql);
		try{
			return super.pagingFind(sql, start, limit,new TTmnlGroupRowMapper2(),list.toArray());
		}catch(RuntimeException e){
			this.logger.debug("查询群组出错！");
			throw e;
		}
	}
	
	/**
	 * 查询所有普通群组名称
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupDto>findCommonGroup(String staffNo)throws DBAccessException{
		String sql ="select r.group_no,r.group_name from r_user_group r where r.staff_no=?";
		try{
			List<TTmnlGroupDto> list=new ArrayList<TTmnlGroupDto>();
			list=this.getJdbcTemplate().query(sql,new Object[]{staffNo},new GroupRowMapper());
			return list;
		}catch(RuntimeException e){
			this.logger.debug("查询群组出错！");
			throw e;
		}
		
	}
	
    /**
     * 按照控制类别查询控制群组名称
     * @param ctrlSchemeType 控制方案分类 
     * @return
	 * @throws DBAccessException
     */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupDto>findCtrlGroup(String staffNo,String ctrlSchemeType)throws DBAccessException{
		String sql =
			"select t.group_no,t.group_name from t_tmnl_group t where t.staff_no=? and t.ctrl_scheme_type=?";
		try{
			List<TTmnlGroupDto> list=new ArrayList<TTmnlGroupDto>();
			list=this.getJdbcTemplate().query(sql,new Object[]{staffNo,ctrlSchemeType},new GroupRowMapper());
			return list;
		}catch(RuntimeException e){
			this.logger.debug("查询群组出错！");
			throw e;
		}	
	}
	
//	/**
//	 * 查询所有控制群组名称
//	 * @return
//	 * @throws DBAccessException
//	 */
//	@SuppressWarnings("unchecked")
//	public List<TTmnlGroupDto>findCtrlGroup()throws DBAccessException{
//		String sql =
//			"select t.group_no,t.group_name from t_tmnl_group t";
//		try{
//			List<TTmnlGroupDto> list=new ArrayList<TTmnlGroupDto>();
//			list=this.getJdbcTemplate().query(sql,new GroupRowMapper());
//			return list;
//		}catch(RuntimeException e){
//			this.logger.debug("查询群组出错！");
//			throw e;
//		}	
//	}
	
	class GroupRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{ 
			TTmnlGroupDto tTmnlGroupDto=new TTmnlGroupDto();
			try {
				tTmnlGroupDto.setGroupNo(rs.getString("group_no"));
				tTmnlGroupDto.setGroupName(rs.getString("group_name"));
				return  tTmnlGroupDto;	
			}catch (Exception e) {
				return null;
			}
		}
	}
	

	class TTmnlGroupRowMapper1 implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{ 
			TTmnlGroupDto tTmnlGroupDto=new TTmnlGroupDto();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tTmnlGroupDto.setGroupNo(rs.getString("group_no"));
				tTmnlGroupDto.setOrgName(rs.getString("org_name"));
				tTmnlGroupDto.setGroupName(rs.getString("group_name"));
				tTmnlGroupDto.setCtrlSchemeType(rs.getString("CTRL_SCHEME_TYPE"));
				if("0".equals(rs.getString("is_share")))
					tTmnlGroupDto.setIsShare("否");
				else if("1".equals(rs.getString("is_share")))
					tTmnlGroupDto.setIsShare("是");
				tTmnlGroupDto.setStaffNo(rs.getString("staff_no"));
				tTmnlGroupDto.setStartDate(sdf.format(rs.getDate("create_date")));
                tTmnlGroupDto.setFinishDate(sdf.format(new Date(rs.getDate("create_date").getTime()+rs.getLong("valid_days")*24*60*60*1000)));
                //tTmnlGroupDto.setGroupType("1");
				return  tTmnlGroupDto;	
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class TTmnlGroupRowMapper2 implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{ 
			TTmnlGroupDto tTmnlGroupDto=new TTmnlGroupDto();
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				tTmnlGroupDto.setGroupNo(rs.getString("group_no"));
				tTmnlGroupDto.setOrgName(rs.getString("org_name"));
				tTmnlGroupDto.setGroupName(rs.getString("group_name")); 
				if("0".equals(rs.getString("is_share")))
					tTmnlGroupDto.setIsShare("否");
				else if("1".equals(rs.getString("is_share")))
					tTmnlGroupDto.setIsShare("是");
				tTmnlGroupDto.setStaffNo(rs.getString("staff_no"));
				tTmnlGroupDto.setStartDate(sdf.format(rs.getDate("create_date")));
                tTmnlGroupDto.setFinishDate(sdf.format(new Date(rs.getDate("create_date").getTime()+rs.getLong("valid_days")*24*60*60*1000)));
                //tTmnlGroupDto.setGroupType("0");
                return  tTmnlGroupDto;	
			}catch (Exception e) {
				return null;
			}
		}
	}
}

