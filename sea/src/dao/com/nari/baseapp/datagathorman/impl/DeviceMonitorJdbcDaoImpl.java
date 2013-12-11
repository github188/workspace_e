package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.DeviceMonitorDto;
import com.nari.baseapp.datagathorman.DeviceMonitorJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.VwTmnlFactory;
import com.nari.privilige.PSysUser;
import com.nari.statreport.AEventStatDWindowDto;
import com.nari.util.exception.DBAccessException;

public class DeviceMonitorJdbcDaoImpl extends JdbcBaseDAOImpl implements
		DeviceMonitorJdbcDao {
	private int vwSize=0;
	/**
	 * 查询设备监测情况
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<DeviceMonitorDto> findAllByOrg(PSysUser pSysUser, String orgType,String orgNo,String startDate,String endDate) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		if("02".equals(pSysUser.getAccessLevel())){
			sb.append("SELECT T.*, ORG.ORG_NAME, ORG.ORG_NO\n");
			sb.append("  FROM (SELECT O.P_ORG_NO,\n");
			sb.append("               SUM(DECODE(CLASS_NO, '03', A.EVENT_CNT, NULL)) AS SERISEVENTS,\n");
			sb.append("               SUM(DECODE(CLASS_NO, '02', A.EVENT_CNT, NULL)) AS MINOREVENTS,\n");
			sb.append("               SUM(DECODE(CLASS_NO, '01', A.EVENT_CNT, NULL)) AS GENEREVENTS\n");
			sb.append("          FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
			sb.append("         WHERE A.ORG_NO = O.ORG_NO\n");
			sb.append("           AND O.P_ORG_NO IN\n");
			sb.append("               (SELECT DISTINCT (PO.ORG_NO)\n");
			sb.append("                  FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
			sb.append("                 WHERE PO.STAFF_NO = PS.STAFF_NO\n");
			sb.append("                   AND PS.STAFF_NO = ?\n");
			sb.append("                   AND PO.ORG_TYPE = O1.ORG_TYPE\n");
			sb.append("                   AND O1.P_ORG_NO = O2.ORG_NO\n");
			sb.append("                   AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
			sb.append("           AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("               TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("           AND A.EVENT_CLASS = '2'\n");
			sb.append("         GROUP BY O.P_ORG_NO) T,\n");
			sb.append("       O_ORG ORG\n");
			sb.append(" WHERE ORG.ORG_NO = T.P_ORG_NO\n");
			sb.append(" ORDER BY ORG.ORG_NO");
		}else if("03".equals(pSysUser.getAccessLevel())){
			sb.append("SELECT O.ORG_NO,\n");
			sb.append("       O.ORG_NAME,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '03', A.EVENT_CNT, NULL)) AS SERISEVENTS,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '02', A.EVENT_CNT, NULL)) AS MINOREVENTS,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '01', A.EVENT_CNT, NULL)) AS GENEREVENTS\n");
			sb.append("  FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
			sb.append(" WHERE A.ORG_NO = O.ORG_NO\n");
			sb.append("   AND O.ORG_NO IN\n");
			sb.append("       (SELECT DISTINCT (PO.ORG_NO)\n");
			sb.append("          FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
			sb.append("         WHERE PO.STAFF_NO = PS.STAFF_NO\n");
			sb.append("           AND PS.STAFF_NO = ?\n");
			sb.append("           AND PO.ORG_TYPE = O1.ORG_TYPE\n");
			sb.append("           AND O1.P_ORG_NO = O2.ORG_NO\n");
			sb.append("           AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
			sb.append("   AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("       TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("   AND A.EVENT_CLASS = '2'\n");
			sb.append(" GROUP BY O.ORG_NO, O.ORG_NAME");
		}else if("04".equals(pSysUser.getAccessLevel())){
			sb.append("SELECT O.ORG_NO,\n");
			sb.append("       O.ORG_NAME,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '03', A.EVENT_CNT, NULL)) AS SERISEVENTS,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '02', A.EVENT_CNT, NULL)) AS MINOREVENTS,\n");
			sb.append("       SUM(DECODE(CLASS_NO, '01', A.EVENT_CNT, NULL)) AS GENEREVENTS\n");
			sb.append("  FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
			sb.append(" WHERE A.ORG_NO = O.ORG_NO\n");
			sb.append("   AND O.ORG_NO IN (SELECT DISTINCT (PO.ORG_NO)\n");
			sb.append("                      FROM P_ACCESS_ORG PO\n");
			sb.append("                     WHERE PO.STAFF_NO = ?\n");
			sb.append("                       AND PO.ORG_TYPE = '04')\n");
			sb.append("   AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
			sb.append("       TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("   AND A.EVENT_CLASS = '2'\n");
			sb.append(" GROUP BY O.ORG_NO, O.ORG_NAME");
		}
		try{
			this.logger.debug(sb.toString());
			List<DeviceMonitorDto> list = new ArrayList<DeviceMonitorDto>();
			list = this.getJdbcTemplate().query(sb.toString(), new Object[]{pSysUser.getStaffNo(),startDate,endDate}, new DeviceMonitorDtoMapper());
			return list;
		}catch(RuntimeException e){
			this.logger.debug("查询设备监测情况出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AEventStatDWindowDto> findDaySeriousLevel(PSysUser pSysUser, String orgNo,String levelNo, String startDate, String endDate,long start, int limit)
			throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		sql.append("SELECT A.ORG_TYPE, A.STAT_DATE , SUM (A.EVENT_CNT) AS EVENT_CNT  \n");
		sql.append("FROM A_EVENT_CLASS_STAT_D A     \n");
		orgNo = orgNo + "%";
		paraList.add(orgNo);
		sql.append("WHERE A.ORG_NO LIKE ?  \n");
		paraList.add(startDate);
		paraList.add(endDate);
		sql.append("AND A.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')    \n");
		sql.append("AND A.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd')    \n");
		paraList.add(levelNo);
		sql.append("AND A.CLASS_NO = ?   \n");
		sql.append("GROUP BY A.STAT_DATE ,A.ORG_TYPE   \n");
		sql.append("ORDER BY A.STAT_DATE DESC   \n");
		
		this.logger.debug(sql.toString());
		return this.getJdbcTemplate().query(sql.toString(), paraList.toArray(), new DMDaySeriousLevelWindowMapper());
	}
	/**
	 * 
	 * 映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-16
	 *
	 */
	class DMDaySeriousLevelWindowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AEventStatDWindowDto aWindowDto = new AEventStatDWindowDto();
			try {
				aWindowDto.setStatDate(rs.getDate("STAT_DATE"));
				aWindowDto.setEventCnt(rs.getString("EVENT_CNT"));
				aWindowDto.setOrgType(rs.getString("ORG_TYPE"));
				return aWindowDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	/**
	 * 查询设备监测（分生产厂家）
	 * @param orgType 单位类型
	 * @param orgNo 单位编码
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findMonitorByFactory(PSysUser pSysUser,String orgType,String orgNo,String startDate,String endDate)throws DBAccessException{
		String sql = "select tf.FACTORY_CODE, tf.FACTORY_NAME from  VW_TMNL_FACTORY tf order by tf.FACTORY_CODE";
        try{
            List<VwTmnlFactory> list = new ArrayList<VwTmnlFactory>();
            list = this.getJdbcTemplate().query(sql, new VwTmnlFactoryRowMapper());
            if(list==null) return null;
            vwSize = list.size();
            StringBuffer sb=new StringBuffer();
            if("03".equals(pSysUser.getAccessLevel())){
            	sb.append("SELECT A.ORG_NO,\n");
            	for(int i=0;i<vwSize;i++){
            		sb.append("       SUM(DECODE(FACTORY_CODE, '"+list.get(i).getFactoryCode()+"', A.EVENT_CNT, NULL)) AS FACTORY"+(i+1)+",\n");
            	}
            	sb.append("       O.ORG_NAME\n");
            	sb.append("  FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
            	sb.append(" WHERE A.ORG_NO = O.ORG_NO\n");
            	sb.append("   AND O.ORG_NO IN\n");
            	sb.append("       (SELECT DISTINCT (PO.ORG_NO)\n");
            	sb.append("          FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
				sb.append("         WHERE PO.STAFF_NO = PS.STAFF_NO\n");
				sb.append("           AND PS.STAFF_NO = ?\n");
				sb.append("           AND PO.ORG_TYPE = O1.ORG_TYPE\n");
				sb.append("           AND O1.P_ORG_NO = O2.ORG_NO\n");
				sb.append("           AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
            	sb.append("   AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
            	sb.append("       TO_DATE(?, 'yyyy-MM-dd')\n");
            	sb.append(" AND A.EVENT_CLASS = '2' \n");
            	sb.append(" GROUP BY A.ORG_NO, O.ORG_NAME");
            }else if("02".equals(pSysUser.getAccessLevel())){
            	sb.append("SELECT ORG.ORG_NAME, T.*\n");
            	sb.append("  FROM (SELECT \n");
            	for(int i=0;i<vwSize;i++){
            		sb.append("       SUM(DECODE(FACTORY_CODE, '"+list.get(i).getFactoryCode()+"', A.EVENT_CNT, NULL)) AS FACTORY"+(i+1)+",\n");
            	}
            	sb.append("  O.P_ORG_NO FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
            	sb.append("         WHERE A.ORG_NO = O.ORG_NO\n");
            	sb.append("           AND O.P_ORG_NO IN\n");
				sb.append("               (SELECT DISTINCT (PO.ORG_NO)\n");
				sb.append("                  FROM P_ACCESS_ORG PO, O_ORG O1, O_ORG O2, P_SYS_USER PS\n");
				sb.append("                 WHERE PO.STAFF_NO = PS.STAFF_NO\n");
				sb.append("                   AND PS.STAFF_NO = ?\n");
				sb.append("                   AND PO.ORG_TYPE = O1.ORG_TYPE\n");
				sb.append("                   AND O1.P_ORG_NO = O2.ORG_NO\n");
				sb.append("                   AND O2.ORG_TYPE = PS.ACCESS_LEVEL)\n");
            	sb.append("   AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
            	sb.append("       TO_DATE(?, 'yyyy-MM-dd')\n");
            	sb.append(" AND A.EVENT_CLASS = '2' \n");
            	sb.append("         GROUP BY O.P_ORG_NO) T,\n");
            	sb.append("       O_ORG ORG\n");
            	sb.append(" WHERE ORG.ORG_NO = T.P_ORG_NO\n");
            	sb.append(" ORDER BY T.P_ORG_NO");

            }else if("04".equals(pSysUser.getAccessLevel())){
            	sb.append("SELECT A.ORG_NO,\n");
            	for(int i=0;i<vwSize;i++){
            		sb.append("       SUM(DECODE(FACTORY_CODE, '"+list.get(i).getFactoryCode()+"', A.EVENT_CNT, NULL)) AS FACTORY"+(i+1)+",\n");
            	}
            	sb.append("       O.ORG_NAME\n");
            	sb.append("  FROM A_EVENT_CLASS_STAT_D A, O_ORG O\n");
            	sb.append(" WHERE A.ORG_NO = O.ORG_NO\n");
            	sb.append("   AND O.ORG_NO IN (SELECT DISTINCT (PO.ORG_NO)\n");
    			sb.append("                      FROM P_ACCESS_ORG PO\n");
    			sb.append("                     WHERE PO.STAFF_NO = ?\n");
    			sb.append("                       AND PO.ORG_TYPE = '04')\n");
            	sb.append("   AND A.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND\n");
            	sb.append("       TO_DATE(?, 'yyyy-MM-dd')\n");
            	sb.append(" AND A.EVENT_CLASS = '2' \n");
            	sb.append(" GROUP BY A.ORG_NO, O.ORG_NAME");
            }
            this.logger.debug(sb.toString());
            List<DeviceMonitorDto> list1 = new ArrayList<DeviceMonitorDto>();
			list1 = this.getJdbcTemplate().query(sb.toString(), new Object[]{pSysUser.getStaffNo(),startDate,endDate},new MonitorFactoryDtoMapper());
			return list1;
        } catch(RuntimeException e){
              this.logger.debug("查询终端厂家出错！");
              throw e;
        }
	}
	
	class DeviceMonitorDtoMapper implements RowMapper {
		@Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 try { 
			 DeviceMonitorDto deviceMonitorDto = new DeviceMonitorDto();
			 deviceMonitorDto.setOrgName(rs.getString("ORG_NAME"));
			 deviceMonitorDto.setOrgNo(rs.getString("ORG_NO"));
			 deviceMonitorDto.setSerisEvents(rs.getInt("SERISEVENTS"));
			 deviceMonitorDto.setGenerEvents(rs.getInt("GENEREVENTS"));
			 deviceMonitorDto.setMinorEvents(rs.getInt("MINOREVENTS"));
			return deviceMonitorDto;
		 }catch (Exception e) {
			 return null;
		 	}
		}
	}
	
	class MonitorFactoryDtoMapper implements RowMapper {
		@Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 try { 
			 DeviceMonitorDto deviceMonitorDto = new DeviceMonitorDto();
			 int[] values = new int[vwSize];
			 deviceMonitorDto.setOrgName(rs.getString("ORG_NAME"));
			 for(int i=0;i<vwSize;i++){
				 values[i] = rs.getInt("factory"+(i+1));
			 }
			 deviceMonitorDto.setFactories(values);
			return deviceMonitorDto;
		 }catch (Exception e) {
			 return null;
		 	}
		}
	}
	
	class VwTmnlFactoryRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			VwTmnlFactory vwTmnlFactory = new VwTmnlFactory();
			try { 
				vwTmnlFactory.setFactoryCode(rs.getString("FACTORY_CODE"));
				vwTmnlFactory.setFactoryName(rs.getString("FACTORY_NAME"));
			    return vwTmnlFactory;
			} catch (Exception e) {
			    return null;
			}
		}
	}


}
