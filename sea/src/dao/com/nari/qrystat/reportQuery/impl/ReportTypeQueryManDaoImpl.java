package com.nari.qrystat.reportQuery.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import oracle.sql.BLOB;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fr.base.FRContext;
import com.fr.web.core.ServerEnv;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.reportQuery.CommMode;
import com.nari.qrystat.reportQuery.ConsInfo;
import com.nari.qrystat.reportQuery.IReportTypeQueryManDao;
import com.nari.qrystat.reportQuery.LeftTreeInfo;
import com.nari.qrystat.reportQuery.OrgInfo;
import com.nari.qrystat.reportQuery.ReportJdbc;
import com.nari.qrystat.reportQuery.ReportTemplat;
import com.nari.qrystat.reportQuery.RunCap;
import com.nari.qrystat.reportQuery.VoltDegree;
import com.nari.util.AutoLang;
import com.nari.util.NodeTypeUtils;

/**
 * 报表查询DAo
 * 
 * @author chengzh
 * @see ReportTypeQueryManDaoImpl
 */
public class ReportTypeQueryManDaoImpl extends JdbcBaseDAOImpl implements
		IReportTypeQueryManDao {
	final Logger logger = Logger.getLogger(ReportTypeQueryManDaoImpl.class);

	static WebApplicationContext webContext = null;

	private void initWebContext() {
		if (null != webContext)
			return;

		ServerEnv env = (ServerEnv) FRContext.getCurrentEnv();
		if (null != env) {
			webContext = WebApplicationContextUtils
					.getWebApplicationContext(env.getServletContext());
		}
	}

	/**
	 * 根据报表类型查询报表列表
	 * 
	 * @param 报表类型
	 * @return 报表列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportJdbc> queryReportListByType(String reportType) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_TYPE=?");
		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql, new Object[] { reportType },
				new ReportRowMapper());
	}

	/**
	 * 查询报表类型
	 * 
	 * @param null
	 * @return 报表类型列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportJdbc> queryReportTypeList() {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT \n");
		sb.append("       distinct REPORT_TYPE\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql, new ReportTypeRowMapper());
		/**
		 * List<String> list = new ArrayList<String>(); list.add("电表数据报表");
		 * list.add("采集点数据报表"); list.add("地区数据报表"); list.add("用户数据报表");
		 * list.add("统计报表");
		 * 
		 * return list;
		 */
	}

	/**
	 * 查询具体报表
	 * 
	 * @param null
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream queryReport(String reportType, String reportName) {
		// TODO Auto-generated method stub
		byte[] reportByte = null;
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_TYPE=?");
		sb.append(" AND REPORT_NAME=?");
		String sql = sb.toString();
		List<ReportJdbc> report = super.getJdbcTemplate().query(sql,
				new Object[] { reportType, reportName }, new ReportRowMapper());
		for (ReportJdbc rj : report) {
			reportByte = rj.getReportContent();
		}
		return new ByteArrayInputStream(reportByte);
	}

	@SuppressWarnings("unchecked")
	public ByteArrayInputStream queryReport(int reportId) {
		// TODO Auto-generated method stub
		byte[] reportByte = null;
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_ID=?");
		String sql = sb.toString();

		JdbcTemplate jdbc = null;

		initWebContext();
		if (null != webContext)
			jdbc = this.createJdbcTemplate((DataSource) webContext
					.getBean("dataSource"));
		if (null == jdbc)
			return null;

		List<ReportJdbc> report = jdbc.query(sql, new Object[] { reportId },
				new ReportRowMapper());
		for (ReportJdbc rj : report) {
			reportByte = rj.getReportContent();
		}
		return new ByteArrayInputStream(reportByte);
	}

	/*
	 * 查询每个报表类型对应的JS文件
	 * 
	 * @param null
	 * 
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ByteArrayInputStream queryJSFile(String reportType, String reportName) {
		// TODO Auto-generated method stub
		byte[] jsFile = null;
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_TYPE=?");
		sb.append(" AND REPORT_NAME=?");
		String sql = sb.toString();
		List<ReportJdbc> report = super.getJdbcTemplate().query(sql,
				new Object[] { reportType, reportName }, new ReportRowMapper());
		for (ReportJdbc rj : report) {
			jsFile = rj.getJsFile();
		}
		return new ByteArrayInputStream(jsFile);
	}
 
	/**
	 * 报表中查询左边数 的信息，加入到GRID中
	 * 
	 * @param null
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	public List<LeftTreeInfo> queryLeftTreeInfo(String orgNo, String orgType,
			PSysUser pSysUser) {

		String sql = "";
		List<LeftTreeInfo> list = new ArrayList<LeftTreeInfo>();
		if (NodeTypeUtils.NODETYPE_ORG02.equals(orgType)) {
			sql = "select org_no, org_name, org_type\n"
					+ "  from (select org_no, org_name, org_type\n"
					+ "          from o_org\n"
					+ "         where org_no = ? \n"
					+ "        union\n"
					+ "        select o.org_no, o.org_name, o.org_type from o_org o, \n"
					+ "        (select org_no, org_type from p_access_org where staff_no \n"
					+ "         = ? and org_type='03') acc where o.org_no = acc.org_no )\n"
					+ " order by org_no";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql,
					new Object[] { orgNo, pSysUser.getStaffNo() },
					new LeftTreeRowMapper());
		} else if (NodeTypeUtils.NODETYPE_ORG03.equals(orgType)) {
			String orgNo2 = orgNo + "%";
			sql = "select org_no, org_name, org_type\n"
					+ "  from (select org_no, org_name, org_type\n"
					+ "          from o_org\n"
					+ "         where org_no = ? \n"
					+ "        union\n"
					+ "        select o.org_no, o.org_name, o.org_type from o_org o, \n"
					+ "        (select org_no, org_type from p_access_org where staff_no \n"
					+ "         = ? and org_type='04' and org_no like ? ) acc where o.org_no = acc.org_no )\n"
					+ " order by org_no";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql,
					new Object[] { orgNo, pSysUser.getStaffNo(), orgNo2 },
					new LeftTreeRowMapper());
		} else if (NodeTypeUtils.NODETYPE_ORG04.equals(orgType)) {
			sql = "select org_no, org_name, org_type\n" + "      from o_org\n"
					+ "      where org_no = ?";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] { orgNo },
					new LeftTreeRowMapper());

		} else {
			// do nothing
		}
		// if(null == list){
		// System.out.println("list出错————————");
		// return new ArrayList<LeftTreeInfo>();
		// }
		return list;
	}

	/**
	 * 查询电压等级
	 * 
	 * @param null
	 * @return 电压等级列表
	 */
	@SuppressWarnings("unchecked")
	public List<VoltDegree> queryVoltDegree() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT distinct VOLT_CODE,\n");
		sb.append("       VOLT\n");
		sb.append("  FROM vw_volt_code order by volt\n");
		String sql = sb.toString();
		List<VoltDegree> voltDegree = super.getJdbcTemplate().query(sql,
				new VoltDegreeRowMapper());
		return voltDegree;
	};

	/**
	 * 查询通信方式
	 * 
	 * @param null
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<CommMode> queryCommMode() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT distinct COMM_MODE_CODE,\n");
		sb.append("       COMM_MODE\n");
		sb.append("  FROM vw_comm_mode order by COMM_MODE\n");
		String sql = sb.toString();
		List<CommMode> commMode = super.getJdbcTemplate().query(sql,
				new CommModeRowMapper());
		return commMode;
	};
	/**
	 * 查询运行容量
	 ** @param null
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<RunCap> queryRunCap() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT distinct RUN_CAP\n");
//		sb.append("       CAP_GRADE_NO\n");
		sb.append("  FROM C_CONS order by RUN_CAP\n");
		String sql = sb.toString();
		List<RunCap> runCap = super.getJdbcTemplate().query(sql,
				new RunCapRowMapper());
		return runCap;
	};
	/**
	 * @description数据库得到的报表的相关信息
	 * @author 陈国章
	 * 
	 */
	class ReportTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportJdbc reportJdbc = new ReportJdbc();
			try {
				reportJdbc.setReportType(rs.getString("REPORT_TYPE"));
				return reportJdbc;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询报表信息时出错");
				return null;
			}

		}
	}

	/**
	 * @description数据库得到的报表的相关信息
	 * @author 陈国章
	 * 
	 */
	class ReportRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportJdbc reportJdbc = new ReportJdbc();
			try {
				reportJdbc.setReportId(rs.getString("Report_ID"));
				reportJdbc.setReportType(rs.getString("REPORT_TYPE"));
				reportJdbc.setReportName(rs.getString("REPORT_NAME"));
				reportJdbc.setReportLen(rs.getLong("REPORT_LEN"));
				reportJdbc.setReportContent(rs.getBytes("REPORT_CONTENT"));
				reportJdbc.setReportParam(rs.getString("REPORT_PARAM"));
				reportJdbc.setValid(rs.getBoolean("IS_VALID"));
				reportJdbc.setJsFile(rs.getBytes("JS_FILE"));
				reportJdbc.setTemplateJSFile(rs.getBytes("TEMPLATE_JS_FILE"));
				return reportJdbc;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询报表信息时出错");
				return null;
			}

		}
	}

	/**
	 * @description数据库得到的通信方式
	 * @author 陈国章
	 * 
	 */
	class CommModeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CommMode commMode = new CommMode();
			try {
				commMode.setCommModeCode(rs.getString("COMM_MODE_CODE"));
				commMode.setCommMode(rs.getString("COMM_MODE"));
				return commMode;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询通信方式时出错");
				return null;
			}
		}
	}

	/**
	 * @description数据库得到左边数的供电单位相关信息
	 * @author 陈国章
	 * 
	 */
	class LeftTreeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			LeftTreeInfo leftTreeInfo = new LeftTreeInfo();
			try {
				leftTreeInfo.setOrgNo(rs.getString("ORG_NO"));
				leftTreeInfo.setOrgName(rs.getString("ORG_NAME"));
				leftTreeInfo.setOrgType(rs.getString("ORG_TYPE"));
				return leftTreeInfo;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询报表信息时出错");
				return null;
			}
		}
	}

	/**
	 * @description电压等级查询
	 * @author 陈国章
	 * 
	 */
	class VoltDegreeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VoltDegree voltDegree = new VoltDegree();
			try {
				voltDegree.setVolt(rs.getString("VOLT_CODE"));
				voltDegree.setVoltDegree(rs.getString("VOLT"));
				return voltDegree;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询电压等级出错");
				return null;
			}

		}
	}

	/**
	 * @description电压等级查询
	 * @author 陈国章
	 * 
	 */
	class RunCapRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RunCap runCap = new RunCap();
			try {
//				runCap.setCapGrade(rs.getString("CAP_GRADE_NO"));
				runCap.setRunCap(rs.getFloat("RUN_CAP"));
				return runCap;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询电压等级出错");
				return null;
			}

		}
	}
	
	
	
	/**------------------------------------------------------报表模板----------------------------------------------------------------**/
	/**
	 * @description保存用户模板
	 * @author 陈国章
	 * 
	 */
	class ReportTemplateRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportTemplat reportTemplat = new ReportTemplat();
			try {
                reportTemplat.setTemplateId(rs.getInt("TEMPLATE_ID"));
                reportTemplat.setReportId(rs.getInt("REPORT_ID"));
                reportTemplat.setRawReportType(rs.getString("RAW_REPORT_TYPE"));
                reportTemplat.setRawReportName(rs.getString("RAW_REPORT_NAME"));
               	reportTemplat.setReportType(rs.getString("REPORT_TYPE"));
				reportTemplat.setReportName(rs.getString("REPORT_NAME"));
				reportTemplat.setConsNoList(rs.getBinaryStream("CONSNO_LIST"));
				reportTemplat.setReportParam(rs.getString("REPORT_PARAM"));
				return reportTemplat;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存用户模板出错");
				return null;
			}

		}
	}
	/**
	 * @description用户信息Mapper
	 * @author 陈国章
	 * 
	 */
	class consInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ConsInfo consInfo = new ConsInfo();
			try {
				consInfo.setOrg_name(rs.getString("ORG_NAME"));
				consInfo.setCons_no(rs.getString("CONS_NO"));
				consInfo.setCons_name(rs.getString("CONS_NAME"));
				consInfo.setTerminal_addr(rs.getString("TMNL_ADDR"));
				consInfo.setTmnl_asset_no(rs.getString("TMNL_ASSET_NO"));
				return consInfo;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存consInfo用户信息出错");
				return null;
			}

		}
	}
	/**
	 * @description保存用户列表
	 * @author 陈国章
	 * 
	 */
	class ReportTemplateConsRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportTemplat reportTemplat = new ReportTemplat();
			try {
                reportTemplat.setConsNoList(rs.getBinaryStream("CONSNO_LIST"));
			
				return reportTemplat;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存用户模板列表出错");
				return null;
			}

		}
	}
	/**
	 * @description保存用户列表
	 * @author 陈国章
	 * 
	 */
	class ReportTemplateOrgRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportTemplat reportTemplat = new ReportTemplat();
			try {
                reportTemplat.setOrgNoList(rs.getBinaryStream("ORGNO_LIST"));			
				return reportTemplat;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存用户模板列表出错");
				return null;
			}

		}
	}
	
	
	/**
	 * @description保存用户模板
	 * @author 陈国章
	 * 
	 */
	class ReportTemplateTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportTemplat reportTemplat = new ReportTemplat();
			try {
               	reportTemplat.setReportType(rs.getString("REPORT_TYPE"));
				return reportTemplat;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存用户模板出错");
				return null;
			}

		}
	}

	@Override
	public void saveTemplate2DB() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 将用户列表保存到数据库 
	 * @return
	 */
	public void saveConsNoListTemplate(final int reportId,final String rawReportType,final String rawReportName,final String reportType,final String reportName,final String consNoList,final String reportParam)
	{
		StringBuffer sb =new StringBuffer();

		sb.append("\n");
		sb.append("INSERT INTO  b_report_consTemplate(TEMPLATE_ID,REPORT_ID,RAW_REPORT_TYPE,RAW_REPORT_NAME,REPORT_TYPE,REPORT_NAME,CONSNO_LIST,REPORT_PARAM)\n");
		sb.append("       VALUES(REPORT_TEMPLATE_SEQUENCE.nextVal,?,?,?,?,?,?,?)\n");
		String sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setInt(1, reportId);
			        	ps.setString(2, rawReportType); 
				        ps.setString(3, rawReportName); 
			            ps.setString(4, reportType); 
			            ps.setString(5, reportName); 
			            // 设置 CLOB 字段
			            lobCreator.setBlobAsBytes(ps,6, consNoList.getBytes());
			            // 设置 BLOB 字段
			            ps.setString(7, reportParam); 
			          }});

					
	} 
	/**
	 * 更新列表中的用户列表
	 * @params templateId,reportType,reportName,consNoList,reportParam
	 */
	public void updateConsNoListTemplate(final int templateId, final String reportType,
			final String reportName, final String consNoList, final String reportParam){
		StringBuffer sb =new StringBuffer();
		sb.append("\n");
		sb.append("update  b_report_consTemplate set REPORT_TYPE=?,REPORT_NAME=?,CONSNO_LIST=?,REPORT_PARAM=?\n");
		sb.append("       where template_id=?\n");
		String sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setString(1, reportType); 
				        ps.setString(2, reportName); 
				        lobCreator.setBlobAsBytes(ps,3, consNoList.getBytes());
				        ps.setString(4, reportParam); 
			            ps.setInt(5, templateId);     // 设置 CLOB 字段			           
			          }});		
        }
	/**
	 * 删除模板用户列表
	 * @params templateId
	 */
	public void deleteConsNoListTemplate(int templateId)
	{
		StringBuffer sb =new StringBuffer();
		sb.append("\n");
		sb.append("delete from  b_report_consTemplate\n");
		sb.append("       where template_id=?\n");
		String sql = sb.toString();
		super.getJdbcTemplate().update(sql,new Object[]{templateId});
	}
	
	public Blob String2Blob(String str) throws Exception
	{
		byte[] bytes=str.getBytes();
		BLOB b = BLOB.getEmptyBLOB();
		b.setBytes(bytes);
		return b;
	}
	/**
	 * 将供电单位列表列表保存到数据库 
	 * @return
	 */
	@Override
	public void saveOrgNoListTemplate(String rawReportType,String rawReportName,String reportType, String reportName,
			String orgNoList, String reportParam) {
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * 根据传入的报表模板类型查询报表名称
	 * 
	 * @param reportType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportTemplat> queryReportTemplateByType(String reportType){
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT TEMPLATE_ID,\n");
		sb.append("       REPORT_ID,\n");
		sb.append("       raw_report_type,\n");
		sb.append("       raw_report_name,\n");
		sb.append("       report_type,\n");
		sb.append("       report_name,\n");
		sb.append("       consno_list,\n");
		sb.append("       report_param\n");
		sb.append("  FROM b_report_consTemplate\n");
		sb.append(" WHERE REPORT_TYPE=?");
		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql,new Object[] {reportType}, new ReportTemplateRowMapper());
	
	}
	/**
	 *查询报表模板类型列表
	 * 
	 * @param reportType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportTemplat> queryReportTemplateList()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT \n");
		sb.append("       distinct REPORT_TYPE\n");
		sb.append("  FROM b_report_consTemplate\n");
		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql,new ReportTemplateTypeRowMapper());
	}
	/**
	 * 查询报表文件对应的参数页面JS代码
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	@SuppressWarnings("unchecked")
	public ByteArrayInputStream queryTemplateJSFile(String reportType,String reportName)
	{
		// TODO Auto-generated method stub
		byte[] jsFile = null;
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_TYPE=?");
		sb.append(" AND REPORT_NAME=?");
		String sql = sb.toString();
		List<ReportJdbc> report = super.getJdbcTemplate().query(sql,
				new Object[] { reportType, reportName }, new ReportRowMapper());
		for (ReportJdbc rj : report) {
			jsFile = rj.getTemplateJSFile();
		}
		return new ByteArrayInputStream(jsFile);
	}
	/**
	 * 查询报表m模板中的用户信息
	 * @param consNoList
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	public List<ConsInfo> queryTemplateConsInfo(int templateId)
	{
		StringBuffer sb=new StringBuffer();
		List<String> consNoList=queryConsInfo(templateId);
		sb.append("\n");
		sb.append("         select o.org_name ORG_NAME,c.cons_no CONS_NO,c.cons_name CONS_NAME,r.terminal_addr TMNL_ADDR,\n");
		sb.append("         r.tmnl_asset_no TMNL_ASSET_NO from vw_tmnl_run r join\n");
		sb.append("         c_cons c on (r.cons_no=c.cons_no) join  o_org o  on(r.org_no=o.org_no)\n");
		sb.append("        WHERE c.cons_no ");
		sb.append(AutoLang.autoIn("",consNoList, 500));
		String sql=sb.toString();
		List<ConsInfo> ciList = super.getJdbcTemplate().query(sql, consNoList.toArray(), new consInfoMapper());
		return ciList;
	}
	/**
	 * 查询用户列表信息
	 * @param reportType,reportName
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryConsInfo(int templateId)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("\n");
		sb.append("      SELECT   CONSNO_LIST FROM\n");
		sb.append("       B_REPORT_CONSTEMPLATE \n");
		sb.append("       WHERE TEMPLATE_ID=? \n");
		String sql=sb.toString();
		List<ReportTemplat> reportTempList=super.getJdbcTemplate().query(sql,new Object[]{templateId},new ReportTemplateConsRowMapper());
		InputStream is = null;
		for(ReportTemplat repTem: reportTempList)
		{
			is=repTem.getConsNoList();
		}
		byte[] bytes = null;
		try {
			bytes=InputStreamToByte(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String consArray=new String(bytes);
		List<String> consList = splitString(consArray);
		return consList;
		
	} 
	/**
	 * 将输入流转坏为字节数组
	 * @param is
	 * @return 字节数组
	 */
	private byte[] InputStreamToByte(InputStream is) throws IOException {   
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();   
	    int ch;   
	    while ((ch = is.read()) != -1) {   
		bytestream.write(ch);   
		}   
		byte bytes[] = bytestream.toByteArray();   
		bytestream.close();   
		return bytes;   
	} 
	/**
	 * 将String转化为List
	 * @param is
	 * @return 字节数组
	 */
	private List<String> splitString(String str)
	{
		String[] consArray=str.split(",");
		List<String> consList=Arrays.asList(consArray);
		return consList;		
	}
	/**
	 * 查询报表m模板中的供电单位列表
	 * @param reportType,reportName
	 * @return 报表文件
	 */	
	@SuppressWarnings("unchecked")
	public List<String> queryTemplateOrgList(int templateId){
		StringBuffer sb=new StringBuffer();
		sb.append("\n");
		sb.append("      SELECT  ORGNO_LIST FROM\n");
		sb.append("       B_REPORT_CONSTEMPLATE \n");
		sb.append("       WHERE TEMPLATE_ID=? \n");
		String sql=sb.toString();
		List<ReportTemplat> reportTempList=super.getJdbcTemplate().query(sql,new Object[]{templateId},new ReportTemplateOrgRowMapper());
		InputStream is = null;
		for(ReportTemplat repTem: reportTempList)
		{
			is=repTem.getOrgNoList();
		}
		byte[] bytes = null;
		try {
			bytes=InputStreamToByte(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String orgArray=new String(bytes);
		List<String> orgList = splitString(orgArray);
		return orgList;
	}
	/**
	 * 将供电单位列表保存到数据库 
	 * @params rawReportType,rawReportName,reportType,reportName,orgNoList,orgNoList,orgNoList,reportParam
	 */
	public void saveOrgInfoListTemplate(final int reportId,final String rawReportType,final String rawReportName,final String reportType,final String reportName,final String orgNoList,final String reportParam)
	{
		StringBuffer sb =new StringBuffer();
		sb.append("\n");
		sb.append("INSERT INTO  b_report_consTemplate(TEMPLATE_ID,REPORT_ID,RAW_REPORT_TYPE,RAW_REPORT_NAME,REPORT_TYPE,REPORT_NAME,ORGNO_LIST,REPORT_PARAM)\n");
		sb.append("       VALUES(REPORT_TEMPLATE_SEQUENCE.nextVal,?,?,?,?,?,?,?)\n");
		String sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setInt(1, reportId);
			        	ps.setString(2, rawReportType); 
				        ps.setString(3, rawReportName); 
			            ps.setString(4, reportType); 
			            ps.setString(5, reportName); 
			            // 设置 CLOB 字段
			            lobCreator.setBlobAsBytes(ps,6, orgNoList.getBytes());
			            // 设置 BLOB 字段
			            ps.setString(7, reportParam); 
			          }});

					
	} 
	/**
	 * @description用户信息Mapper
	 * @author 陈国章
	 * 
	 */
	class orgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OrgInfo orgInfo = new OrgInfo();
			try {
				orgInfo.setOrgName(rs.getString("ORG_NAME"));
				orgInfo.setOrgNo(rs.getString("ORG_NO"));
				orgInfo.setOrgType(rs.getString("ORG_TYPE"));
				return orgInfo;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存orgInfo供电单位信息出错");
				return null;
			}

		}
	}
	/**
	 * 查询报表m模板中的供电单位
	 * @param consNoList
	 * @return 报表文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LeftTreeInfo> queryTemplateOrgInfo(int templateId) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		List<String> orgNoList=queryTemplateOrgList(templateId);
		sb.append("\n");
		sb.append("          select org_no, org_name, org_type from o_org\n");
		sb.append("        WHERE org_no ");
		sb.append(AutoLang.autoIn("",orgNoList, 500));
		String sql=sb.toString();
		List<LeftTreeInfo> LtiList = super.getJdbcTemplate().query(sql, orgNoList.toArray(), new LeftTreeRowMapper());
		return LtiList;
	}
	/**
	 * 删除模板供电单位
	 * @params templateId
	 */
	public void deleteOrgNoListTemplate(int templateId)
	{
		StringBuffer sb =new StringBuffer();
		sb.append("\n");
		sb.append("delete from  b_report_consTemplate\n");
		sb.append("       where template_id=?\n");
		String sql = sb.toString();
		super.getJdbcTemplate().update(sql,new Object[]{templateId});
	}
	/**
	 * 保存模板用户或者供电单位的信息
	 * @param defBoolean 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void saveConsNoList(int reportId,final String reportType, final String reportName,
			final String consNoList, final String reportParam,  int defBoolean)
	{
		StringBuffer sb;
		String sql;
		sb =new StringBuffer();
		sb.append("         SELECT REPORT_ID,REPORT_TYPE,REPORT_NAME, REPORT_LEN,REPORT_CONTENT,\n");
		sb.append("         REPORT_PARAM,IS_VALID,JS_FILE,TEMPLATE_JS_FILE,CONSNO_LIST,DEF_BOOLEAN");
		sb.append("         FROM B_REPORT_SETUP WHERE REPORT_ID=?");
		sql=sb.toString();
		List<ReportJdbc> reportList=super.getJdbcTemplate().query(sql, new Object[]{reportId},new ReportInfoRowMapper());
		byte[] jf = null,rc = null;
		for(ReportJdbc r:reportList)
		{
			jf=r.getJsFile();
			rc=r.getReportContent();
		}
		final byte[] jsFile=jf;
		final byte[] reportContent =rc;
		sb=new StringBuffer();
		sb.append("\n");
		sb.append("INSERT INTO  B_REPORT_SETUP(REPORT_ID,REPORT_TYPE,REPORT_NAME,CONSNO_LIST,REPORT_PARAM,REPORT_CONTENT,JS_FILE,DEF_BOOLEAN)\n");
		sb.append("       VALUES(B_REPORT_SEQ.nextVal,?,?,?,?,?,?,?)\n");
		sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setString(1, reportType); 
				        ps.setString(2, reportName); 
				        lobCreator.setBlobAsBytes(ps,3, consNoList.getBytes());
			            ps.setString(4, reportParam); 
			            lobCreator.setBlobAsBytes(ps,5, reportContent);
			            lobCreator.setBlobAsBytes(ps,6, jsFile);
			            ps.setInt(7, 1); 			         
			          }});
	}
	/**
	 * 用户列表RowMapper
	 *
	 */
	private class consRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportJdbc reportJdbc = new ReportJdbc();
			try {
				reportJdbc.setConsNoList(rs.getBytes("CONSNO_LIST"));
				return reportJdbc;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("保存用户信息时出错");
				return null;
			}

		}
	}
	/**
	 * 用户列表RowMapper
	 *
	 */
	private class ReportInfoRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportJdbc reportJdbc = new ReportJdbc();
			try {
				reportJdbc.setReportId(rs.getString("Report_ID"));
				reportJdbc.setReportType(rs.getString("REPORT_TYPE"));
				reportJdbc.setReportName(rs.getString("REPORT_NAME"));
				reportJdbc.setReportLen(rs.getLong("REPORT_LEN"));
				reportJdbc.setReportContent(rs.getBytes("REPORT_CONTENT"));
				reportJdbc.setReportParam(rs.getString("REPORT_PARAM"));
				reportJdbc.setValid(rs.getBoolean("IS_VALID"));
				reportJdbc.setJsFile(rs.getBytes("JS_FILE"));
				reportJdbc.setTemplateJSFile(rs.getBytes("TEMPLATE_JS_FILE"));
				reportJdbc.setConsNoList(rs.getBytes("CONSNO_LIST"));
				reportJdbc.setDefBoolean(rs.getInt("DEF_BOOLEAN"));
				return reportJdbc;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询报表信息时出错");
				return null;
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	private List<String> queryConsList(int reportId)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("\n");
		sb.append("      SELECT   CONSNO_LIST FROM\n");
		sb.append("       B_REPORT_SETUP \n");
		sb.append("       WHERE REPORT_ID=? \n");
		String sql=sb.toString();
		List<ReportJdbc> reportList=super.getJdbcTemplate().query(sql,new Object[]{reportId},new consRowMapper());
		byte[] bytes =null ;
		for(ReportJdbc rj:reportList)
		{
			bytes=rj.getConsNoList();
		}
		String consArray=new String(bytes);
		List<String> consList = splitString(consArray);
		return consList;
	}
	/**
	 * 查询模板用户单位列表的信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ConsInfo> queryConsNoList(int reportId)
	{
		StringBuffer sb=new StringBuffer();
		List<String> consNoList=queryConsList(reportId);
		sb.append("\n");
		sb.append("         select o.org_name ORG_NAME,c.cons_no CONS_NO,c.cons_name CONS_NAME,r.terminal_addr TMNL_ADDR,\n");
		sb.append("         r.tmnl_asset_no TMNL_ASSET_NO from vw_tmnl_run r join\n");
		sb.append("         c_cons c on (r.cons_no=c.cons_no) join  o_org o  on(r.org_no=o.org_no)\n");
		sb.append("        WHERE c.cons_no ");
		sb.append(AutoLang.autoIn("",consNoList, 500));
		System.out.println(consNoList.toArray());
		System.out.println("结束结束");
		String sql=sb.toString();
		List<ConsInfo> ciList = super.getJdbcTemplate().query(sql, consNoList.toArray(), new consInfoMapper());
		return ciList;
	}
	/**
	 * 更新模板用户或者供电单位列表的信息
	 * 
	 * @return
	 */
	public void updateConsNoList(final int reportId,final String reportType, final String reportName,
			final String consNoList, final String reportParam){
		{
			StringBuffer sb =new StringBuffer();
			sb.append("\n");
			sb.append("update  B_REPORT_SETUP SET REPORT_TYPE=?,REPORT_NAME=?,CONSNO_LIST=?,REPORT_PARAM=?\n");
			sb.append("       where REPORT_Id=?\n");
			String sql = sb.toString();
			final LobHandler lobHandler=new DefaultLobHandler();
			System.out.println(reportId);
			super.getJdbcTemplate().execute(sql,
				      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
				          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
				                      throws SQLException {
				        	ps.setString(1, reportType); 
					        ps.setString(2, reportName); 
					        lobCreator.setBlobAsBytes(ps,3, consNoList.getBytes());
					        ps.setString(4, reportParam); 
				            ps.setInt(5, reportId);     // 设置 CLOB 字段			           
				          }});		
	        }
	}
	public class reportShcutRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ReportJdbc reportJdbc = new ReportJdbc();
			try {
				reportJdbc.setReportId(rs.getString("Report_ID"));
				reportJdbc.setReportType(rs.getString("REPORT_TYPE"));
				reportJdbc.setReportName(rs.getString("REPORT_NAME"));
				reportJdbc.setReportParam(rs.getString("REPORT_PARAM"));
				reportJdbc.setDefBoolean(rs.getInt("DEF_BOOLEAN"));
				return reportJdbc;
			} catch (Exception e) {
				e.printStackTrace();
				ReportTypeQueryManDaoImpl.this.logger.error("从报表数据库查询报表信息时出错");
				return null;
			}

		}
	}
	/**
	 * 查询报表列表信息
	 * @param reportType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportJdbc> queryReportInfoByType(String reportType)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       DEF_BOOLEAN\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_TYPE=?");
		String sql = sb.toString();
		return super.getJdbcTemplate().query(sql, new Object[] { reportType },
				new reportShcutRowMapper());
	}
	/**
	 *根据报表ID查询报表文件
	 * @param reportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ByteArrayInputStream queryJSFile(int reportId)
	{
		byte[] jsFile = null;
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("SELECT REPORT_ID,\n");
		sb.append("       REPORT_TYPE,\n");
		sb.append("       REPORT_NAME,\n");
		sb.append("       REPORT_LEN,\n");
		sb.append("       REPORT_CONTENT,\n");
		sb.append("       REPORT_PARAM,\n");
		sb.append("       IS_VALID,\n");
		sb.append("       JS_FILE,\n");
		sb.append("       template_js_file\n");
		sb.append("  FROM B_REPORT_SETUP\n");
		sb.append(" WHERE REPORT_ID=?");
		String sql = sb.toString();
		List<ReportJdbc> report = super.getJdbcTemplate().query(sql,
				new Object[] { reportId}, new ReportRowMapper());
		for (ReportJdbc rj : report) {
			jsFile = rj.getJsFile();
		}
		return new ByteArrayInputStream(jsFile);
	}
	/**
	 * 查询模板供電单位列表的信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LeftTreeInfo> queryOrgNoList(int reportId) {
		StringBuffer sb = new StringBuffer();
		List<String> orgNoList = queryConsList(reportId);
		sb.append("\n");
		sb.append("          select org_no, org_name, org_type from o_org\n");
		sb.append("        WHERE org_no ");
		sb.append(AutoLang.autoIn("", orgNoList, 500));
		String sql = sb.toString();
		List<LeftTreeInfo> LtiList = super.getJdbcTemplate().query(sql,
				orgNoList.toArray(), new LeftTreeRowMapper());
		return LtiList;		
	}
	/**
	 * 删除报表或者报表模板
	 * @param reportId
	 * @return
	 */
	public void deleteReport(int reportId)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("          DELETE FROM  B_REPORT_SETUP WHERE REPORT_ID=?\n");
		String sql = sb.toString();
		super.getJdbcTemplate().update(sql,
				new Object[]{reportId});
	}
	/**
	 * 上传文件
	 * @param reportType,reportName,reportDescription,reportFile,jsFiles
	 */
	public void saveReport(final String reportType, final String reportName,
			final String reportDescription, File reportFile, File jsFiles)
	{   
		FileInputStream rfis = null,jsfis;
		int rfLen=(int) reportFile.length();
		byte[] rfData=new byte[rfLen];
		int jsLen =(int) jsFiles.length();		
		byte[] jsData=new byte[jsLen];		
		try {
			rfis=new FileInputStream(reportFile);
			rfis.read(rfData);
			jsfis=new FileInputStream(jsFiles);
			jsfis.read(jsData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final byte[] rfd=rfData;
		final byte[] jsd=jsData;
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO  B_REPORT_SETUP(REPORT_ID,REPORT_TYPE,REPORT_NAME,REPORT_PARAM,REPORT_CONTENT,JS_FILE,DEF_BOOLEAN)\n");
		sb.append("       VALUES(B_REPORT_SEQ.nextVal,?,?,?,?,?,?)\n");
		String sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setString(1, reportType); 
				        ps.setString(2, reportName); 
			            ps.setString(3, reportDescription); 
			            lobCreator.setBlobAsBytes(ps,4, rfd);
			            lobCreator.setBlobAsBytes(ps,5, jsd);
			            ps.setInt(6, 0);
			          }});		
	}
	/**
	* 修改文件
	* @param reportId,reportType,reportName,reportDescription,reportFile,jsFiles
	*/
	public void updateReport(final int reportId, final String reportType,
			final String reportName, final String reportDescription, File reportFile,
			File jsFiles,final int defBool)
	{
		FileInputStream rfis = null,jsfis;
		int rfLen=(int) reportFile.length();
		byte[] rfData=new byte[rfLen];
		int jsLen =(int) jsFiles.length();		
		byte[] jsData=new byte[jsLen];		
		try {
			rfis=new FileInputStream(reportFile);
			rfis.read(rfData);
			jsfis=new FileInputStream(jsFiles);
			jsfis.read(jsData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final byte[] rfd=rfData;
		final byte[] jsd=jsData;
		StringBuffer sb=new StringBuffer();
		sb.append("update  B_REPORT_SETUP SET REPORT_TYPE=?,REPORT_NAME=?,REPORT_PARAM=?,REPORT_CONTENT=?,JS_FILE=?,DEF_BOOLEAN=?\n");
		sb.append("       WHERE REPORT_ID=?\n");
		String sql = sb.toString();
		final LobHandler lobHandler=new DefaultLobHandler();
		super.getJdbcTemplate().execute(sql,
			      new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			          protected void setValues(PreparedStatement ps,LobCreator lobCreator)
			                      throws SQLException {
			        	ps.setString(1, reportType); 
				        ps.setString(2, reportName); 
			            ps.setString(3, reportDescription); 
			            lobCreator.setBlobAsBytes(ps,4, rfd);
			            lobCreator.setBlobAsBytes(ps,5, jsd);
			            ps.setInt(6, defBool);
			            ps.setInt(7, reportId);
			          }});		
	}
	
}
