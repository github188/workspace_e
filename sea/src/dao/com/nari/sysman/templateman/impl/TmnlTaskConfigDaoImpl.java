package com.nari.sysman.templateman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.sysman.templateman.TmnlTaskConfigDao;
import com.nari.terminalparam.ITmnlProtSentSetupBean;
import com.nari.terminalparam.ITmnlTaskSetupBean;
import com.nari.terminalparam.TTaskTemplateBean;

public class TmnlTaskConfigDaoImpl extends JdbcBaseDAOImpl implements
		TmnlTaskConfigDao {

	// 终端厂家
	@SuppressWarnings("unchecked")
	public List<ITmnlProtSentSetupBean> findTmnlFactory() {
		String sql = "SELECT * FROM VW_TMNL_FACTORY";
		return getJdbcTemplate().query(sql, new TmnlFactoryRowMapper());
	}

	class TmnlFactoryRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setFactoryCode(rs
						.getString("FACTORY_CODE"));
				itmnlprotsentsetupbean.setFactoryName(rs
						.getString("FACTORY_NAME"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 终端型号
	@SuppressWarnings("unchecked")
	public List<ITmnlProtSentSetupBean> findTmnlModel() {
		String sql = "SELECT * FROM VW_TMNL_MODE_CODE";
		return getJdbcTemplate().query(sql, new TmnlModeRowMapper());
	};

	class TmnlModeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setModeCode(rs.getString("MODE_CODE"));
				itmnlprotsentsetupbean.setModeName(rs.getString("MODE_NAME"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 终端类型
	@SuppressWarnings("unchecked")
	public List<ITmnlProtSentSetupBean> findTmnlType() {
		String sql = "SELECT * FROM VW_TMNL_TYPE_CODE";
		return getJdbcTemplate().query(sql, new TmnlTypeRowMapper());
	}

	class TmnlTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setTmnlTypeCode(rs
						.getString("TMNL_TYPE_CODE"));
				itmnlprotsentsetupbean.setTmnlType(rs.getString("TMNL_TYPE"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 采集方式
	@SuppressWarnings("unchecked")
	public List<ITmnlProtSentSetupBean> findCollMode() {
		String sql = "SELECT * FROM VW_COMM_MODE";
		return getJdbcTemplate().query(sql, new CommModeRowMapper());
	}

	class CommModeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setCommModeCode(rs
						.getString("COMM_MODE_CODE"));
				itmnlprotsentsetupbean.setCommMode(rs.getString("COMM_MODE"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

//	// 上送方式
//	@SuppressWarnings("unchecked")
//	public List<ITmnlProtSentSetupBean> findSendUp() {
//		String sql = "SELECT DISTINCT "
//				+ "           	SEND_UP_MODE\n" 
//				+ "  FROM I_TMNL_PROT_SEND_SETUP";
//		return getJdbcTemplate().query(sql, new SentUpModeRowMapper());
//	}
//
//	class SentUpModeRowMapper implements RowMapper {
//		@Override
//		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
//			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
//			try {
//				
//				String code = rs.getString("SEND_UP_MODE");
//				itmnlprotsentsetupbean.setSendUpMode(code);
//				if ("0".equals(code)) {
//					itmnlprotsentsetupbean.setSendUpMode("主站主动召测");
//				} else if("1".equals(code)){
//					itmnlprotsentsetupbean.setSendUpMode("终端自动上送");
//				} else {
//					itmnlprotsentsetupbean.setSendUpMode("");
//				}
//				
//				
//				return itmnlprotsentsetupbean;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//	}

	// 通讯规约
	@SuppressWarnings("unchecked")
	public List<ITmnlProtSentSetupBean> findProtocol() {
		String sql = "select vp.PROTOCOL_CODE,vp.PROTOCOL_name from vw_protocol_code vp";
		return getJdbcTemplate().query(sql, new ProtocolCodeRowMapper());
	}

	class ProtocolCodeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setProtocolCode(rs
						.getString("PROTOCOL_CODE"));
				itmnlprotsentsetupbean.setProtocolName(rs
						.getString("PROTOCOL_NAME"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 用户类型
	@SuppressWarnings("unchecked")
	public List<ITmnlTaskSetupBean> findConsType() {
		String sql = "SELECT * FROM VW_CONS_TYPE";
		return getJdbcTemplate().query(sql, new ConsTypeRowMapper());
	}

	class ConsTypeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlTaskSetupBean itmnltasksetupbean = new ITmnlTaskSetupBean();
			try {
				itmnltasksetupbean.setConsType(rs.getString("CONS_TYPE"));
				itmnltasksetupbean.setConsTypeName(rs
						.getString("CONS_TYPE_NAME"));
				return itmnltasksetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 容量等级
	@SuppressWarnings("unchecked")
	public List<ITmnlTaskSetupBean> findCapGrade() {
		String sql = "SELECT T.CAP_GRADE_NO, T.CAP_GRADE_NAME FROM VW_CAP_GRADE T";
		return getJdbcTemplate().query(sql, new CapGradeRowMapper());
	}

	class CapGradeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlTaskSetupBean itmnltasksetupbean = new ITmnlTaskSetupBean();
			try {
				itmnltasksetupbean.setCapGradeNo(rs.getString("CAP_GRADE_NO"));
				itmnltasksetupbean.setCapGradeName(rs
						.getString("CAP_GRADE_NAME"));
				return itmnltasksetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 终端任务模板
	public Page<TTaskTemplateBean> findTTaskT(long start, int limit) {
		String sql = "SELECT TT.TEMPLATE_ID,TT.TEMPLATE_NAME, VTP.TASK_PROPERTY_NAME, VD.DATA_NAME\n"
				+ "  FROM T_TASK_TEMPLATE TT, VW_TASK_PROPERTY VTP, VW_DATA_TYPE VD\n"
				+ " WHERE TT.DATA_TYPE = VD.DATA_TYPE\n"
				+ "   AND TT.TASK_PROPERTY = VTP.TASK_PROPERTY\n"
				+ "	  AND TT.IS_CANCEL = 0";

		return super.pagingFind(sql, start, limit, new TTaskTRowMapper());
	}

	class TTaskTRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTaskTemplateBean ttasktemplatebean = new TTaskTemplateBean();
			try {
				ttasktemplatebean.setTemplateId(rs.getString("TEMPLATE_ID"));
				ttasktemplatebean
						.setTemplateName(rs.getString("TEMPLATE_NAME"));
				ttasktemplatebean.setTaskPropertyName(rs
						.getString("TASK_PROPERTY_NAME"));
				ttasktemplatebean.setDataName(rs.getString("DATA_NAME"));
				return ttasktemplatebean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 终端任务上送方式配置--新增、保存规则按钮
	@SuppressWarnings("unchecked")
	public String saveOrUpdate(ITmnlProtSentSetupBean bean) {
		if (bean == null) {
			return "";
		}
		if ("true".equals(bean.getAttachMeterFlag())) {//是否网络表
			bean.setAttachMeterFlag("1");
		}else {
			bean.setAttachMeterFlag("0");
		}
		if (bean.getProtSendSetupId() != null
				&& !"".equals(bean.getProtSendSetupId())) {
			String sql = "UPDATE I_TMNL_PROT_SEND_SETUP I\n"
					+ "   SET I.FACTORY_CODE       = ?,\n"
					+ "       I.MODEL_CODE         = ?,\n"
					+ "       I.TERMINAL_TYPE_CODE = ?,\n"
					+ "       I.COLL_MODE          = ?,\n"
					+ "       I.TMNL_ADDR_S        = ?,\n"
					+ "       I.TMNL_ADDR_E        = ?,\n"
					+ "       I.ATTACH_METER_FLAG  = ?,\n"
					+ "       I.PROTOCOL_CODE      = ?,\n"
					+ "       I.SEND_UP_MODE       = ?\n"
					+ " WHERE I.PROT_SEND_SETUP_ID = ?";
			getJdbcTemplate().update(
					sql,
					new Object[] { bean.getFactoryCode(),
							bean.getModelCode(), bean.getTmnlTypeCode(),
							bean.getCollMode(), bean.getTmnlAddrS(),
							bean.getTmnlAddrE(), bean.getAttachMeterFlag(),
							bean.getProtocolCode(), bean.getSendUpMode(),
							bean.getProtSendSetupId()});
			return "";
		} else {
			String querySql = "SELECT 1 RES\n"
					+ "  FROM I_TMNL_PROT_SEND_SETUP I\n"
					+ " WHERE I.FACTORY_CODE = ?\n"
					+ "   AND I.MODEL_CODE = ?\n"
					+ "   AND I.TERMINAL_TYPE_CODE = ?\n"
					+ "   AND I.COLL_MODE = ?\n" + "   AND I.TMNL_ADDR_S = ?\n"
					+ "   AND I.TMNL_ADDR_E = ?\n"
					+ "   AND I.ATTACH_METER_FLAG = ?\n"
					+ "   AND I.PROTOCOL_CODE = ?\n"
					+ "   AND I.SEND_UP_MODE = ?";
			List list = getJdbcTemplate().query(
					querySql,
					new Object[] {bean.getFactoryCode(), bean.getModelCode(),
							bean.getTmnlTypeCode(), bean.getCollMode(),
							bean.getTmnlAddrS(), bean.getTmnlAddrE(),
							bean.getAttachMeterFlag(), bean.getProtocolCode(),
							bean.getSendUpMode() }, new StringRowMapper());
			if (list.size() > 0) {
				return "此配置规则已存在";
			}else {
				String sql = 
					"INSERT INTO I_TMNL_PROT_SEND_SETUP\n" +
					"  (PROT_SEND_SETUP_ID,\n" + 
					"   FACTORY_CODE,\n" + 
					"   MODEL_CODE,\n" + 
					"   TERMINAL_TYPE_CODE,\n" + 
					"   COLL_MODE,\n" + 
					"   TMNL_ADDR_S,\n" + 
					"   TMNL_ADDR_E,\n" + 
					"   ATTACH_METER_FLAG,\n" + 
					"   PROTOCOL_CODE,\n" + 
					"   SEND_UP_MODE) VALUES\n" + 
					"  (S_I_TMNL_PROT_SEND_SETUP.NEXTVAL,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?,\n" + 
					"   ?)";
				getJdbcTemplate().update(
						sql,
						new Object[] { bean.getFactoryCode(),
								bean.getModelCode(), bean.getTmnlTypeCode(),
								bean.getCollMode(), bean.getTmnlAddrS(),
								bean.getTmnlAddrE(), bean.getAttachMeterFlag(),
								bean.getProtocolCode(), bean.getSendUpMode() });
				return "";
			}

		}
	}


	/**
	 * @return 用户与终端任务配置  新增规则、保存规则
	 */
	@SuppressWarnings("unchecked")
	public String saveOrUpdate_1(ITmnlTaskSetupBean beanUser){
		if (beanUser == null) {
			return "";
		}
		if (beanUser.getTaskSetupId() != null
				&& !"".equals(beanUser.getTaskSetupId())) {
			String sql = 
				"UPDATE I_TMNL_TASK_SETUP II\n" +
				"   SET II.CONS_TYPE          = ?,\n" + 
				"       II.CAP_GRADE_NO       = ?,\n" + 
				"       II.MODEL_CODE         = ?,\n" + 
				"       II.FACTORY_CODE       = ?,\n" + 
				"       II.PROTOCOL_CODE      = ?,\n" + 
				"       II.TERMINAL_TYPE_CODE = ?,\n" + 
				"       II.TEMPLATE_ID_LIST   = ?\n" + 
				" WHERE II.TASK_SETUP_ID = ?";

			getJdbcTemplate().update(
					sql,
					new Object[] {beanUser.getConsType(),
							beanUser.getCapGradeNo(), beanUser.getModelCode(),
							beanUser.getFactoryCode(), beanUser.getProtocolCode(),
							beanUser.getTerminalTypeCode(), beanUser.getTemplateIdList(),
							beanUser.getTaskSetupId()});
			return "";
		} else {
			String sql = 
				"SELECT 1\n" +
				"  FROM I_TMNL_TASK_SETUP II\n" + 
				" WHERE II.CONS_TYPE = ?\n" + 
				"   AND II.CAP_GRADE_NO = ?\n" + 
				"   AND II.MODEL_CODE = ?\n" + 
				"   AND II.FACTORY_CODE = ?\n" + 
				"   AND II.PROTOCOL_CODE = ?\n" + 
				"   AND II.TERMINAL_TYPE_CODE = ?\n" + 
				"   AND II.TEMPLATE_ID_LIST = ?";

			List list = getJdbcTemplate().query(
					sql,
					new Object[] {beanUser.getConsType(),
							beanUser.getCapGradeNo(), beanUser.getModelCode(),
							beanUser.getFactoryCode(), beanUser.getProtocolCode(),
							beanUser.getTerminalTypeCode(), beanUser.getTemplateIdList()}, 
							new StringRowMapper());
			if (list.size() > 0) {
				return "此配置规则已存在";
			}else {
				String sql1 = 

					"INSERT INTO I_TMNL_TASK_SETUP II\n" +
					"  (II.TASK_SETUP_ID,\n" + 
					"   II.CONS_TYPE,\n" + 
					"   II.CAP_GRADE_NO,\n" + 
					"   II.MODEL_CODE,\n" + 
					"   II.FACTORY_CODE,\n" + 
					"   II.PROTOCOL_CODE,\n" + 
					"   II.TERMINAL_TYPE_CODE,\n" + 
					"   II.TEMPLATE_ID_LIST)\n" + 
					"VALUES\n" + 
					"  (S_I_TMNL_TASK_SETUP.NEXTVAL,\n" + 
					"  ?,\n" + 
					"  ?,\n" + 
					"  ?,\n" + 
					"  ?,\n" + 
					"  ?,\n" + 
					"  ?,\n" + 
					"  ?)";
				getJdbcTemplate().update(
						sql1,
						new Object[] {beanUser.getConsType(),
								beanUser.getCapGradeNo(), beanUser.getModelCode(),
								beanUser.getFactoryCode(), beanUser.getProtocolCode(),
								beanUser.getTerminalTypeCode(), beanUser.getTemplateIdList()});
				return "";
			}

		}
	}
	
	//保存修改按钮	判断是否有重复"规则配置"的共用内部类
	class StringRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			try {
				return rs.getString("res");
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	
	// 终端任务上送方式配置--删除规则按钮
	public void deleteRule(String protSendSetupId) {
		String sql = "DELETE I_TMNL_PROT_SEND_SETUP WHERE PROT_SEND_SETUP_ID = ?";

		getJdbcTemplate().update(sql, new Object[] { protSendSetupId });
	}

	// 用户与终端任务配置--删除规则按钮
	public void deleteRuleH(String taskSetupId) {
		String sql = "DELETE I_TMNL_TASK_SETUP WHERE TASK_SETUP_ID = ?";

		getJdbcTemplate().update(sql, new Object[] { taskSetupId });
	}

	// 终端任务上送方式配置查询
	@Override
	public Page<ITmnlProtSentSetupBean> findTmnlTask(long start, int limit) {
		String sql =


			"SELECT II.PROT_SEND_SETUP_ID,\n" +
			"       O.ORG_NAME,\n" + 
			"       VF.FACTORY_NAME,\n" + 
			"       II.FACTORY_CODE,\n" + 
			"       VM.MODE_NAME,\n" + 
			"       II.MODEL_CODE,\n" + 
			"       VT.TMNL_TYPE,\n" + 
			"       II.TERMINAL_TYPE_CODE,\n" + 
			"       VP.PROTOCOL_NAME,\n" + 
			"       II.PROTOCOL_CODE,\n" + 
			"       V.COMM_MODE,\n" + 
			"       II.COLL_MODE,\n" + 
			"       II.TMNL_ADDR_S,\n" + 
			"       II.TMNL_ADDR_E,\n" + 
			"       II.SEND_UP_MODE,\n" + 
			"       II.ATTACH_METER_FLAG\n" + 
			"  FROM I_TMNL_PROT_SEND_SETUP II,\n" + 
			"       O_ORG                  O,\n" + 
			"       VW_TMNL_FACTORY        VF,\n" + 
			"       VW_TMNL_MODE_CODE      VM,\n" + 
			"       VW_TMNL_TYPE_CODE      VT,\n" + 
			"       VW_PROTOCOL_CODE       VP,\n" + 
			"       VW_COMM_MODE           V\n" + 
			" WHERE O.ORG_NO(+) = II.ORG_NO\n" + 
			"   AND VF.FACTORY_CODE(+) = II.FACTORY_CODE\n" + 
			"   AND VM.MODE_CODE(+) = II.MODEL_CODE\n" + 
			"   AND VP.PROTOCOL_CODE(+) = II.PROTOCOL_CODE\n" + 
			"   AND V.COMM_MODE_CODE(+) = II.COLL_MODE\n" + 
			"   AND VT.TMNL_TYPE_CODE(+) = II.TERMINAL_TYPE_CODE";


		return super.pagingFind(sql, start, limit,
				new ITmnlProtSentSetupRowMapper());
	}

	class ITmnlProtSentSetupRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlProtSentSetupBean itmnlprotsentsetupbean = new ITmnlProtSentSetupBean();
			try {
				itmnlprotsentsetupbean.setProtSendSetupId(rs
						.getLong("PROT_SEND_SETUP_ID"));
				itmnlprotsentsetupbean.setOrgName(rs.getString("ORG_NAME"));
				itmnlprotsentsetupbean.setFactoryName(rs
						.getString("FACTORY_NAME"));
				itmnlprotsentsetupbean.setFactoryCode(rs
						.getString("FACTORY_CODE"));
				itmnlprotsentsetupbean.setModeName(rs.getString("MODE_NAME"));
				itmnlprotsentsetupbean.setModelCode(rs.getString("MODEL_CODE"));
				itmnlprotsentsetupbean.setTmnlType(rs.getString("TMNL_TYPE"));
				itmnlprotsentsetupbean.setTerminalTypeCode(rs
						.getString("TERMINAL_TYPE_CODE"));
				itmnlprotsentsetupbean.setProtocolName(rs
						.getString("PROTOCOL_NAME"));
				itmnlprotsentsetupbean.setProtocolCode(rs
						.getString("PROTOCOL_CODE"));
				itmnlprotsentsetupbean.setCommMode(rs.getString("COMM_MODE"));
				itmnlprotsentsetupbean.setCollMode(rs.getString("COLL_MODE"));
				itmnlprotsentsetupbean
						.setTmnlAddrS(rs.getString("TMNL_ADDR_S"));
				itmnlprotsentsetupbean
						.setTmnlAddrE(rs.getString("TMNL_ADDR_E"));
				itmnlprotsentsetupbean
						.setSendUpMode(rs.getString("SEND_UP_MODE"));
				itmnlprotsentsetupbean.setAttachMeterFlag(rs
						.getString("ATTACH_METER_FLAG"));
				return itmnlprotsentsetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	// 用户与终端任务配置查询
	public Page<ITmnlTaskSetupBean> findUserTask(long start, int limit) {
		String sql =



			"SELECT I.TASK_SETUP_ID,\n" +
			"       I.TEMPLATE_ID_LIST,\n" + 
			"       O.ORG_NAME,\n" + 
			"       VC.CONS_TYPE_NAME,\n" + 
			"       I.CONS_TYPE,\n" + 
			"       VG.CAP_GRADE_NAME,\n" + 
			"       I.CAP_GRADE_NO,\n" + 
			"       VM.MODE_NAME,\n" + 
			"       I.MODEL_CODE,\n" + 
			"       VF.FACTORY_NAME,\n" + 
			"       I.FACTORY_CODE,\n" + 
			"       VP.PROTOCOL_NAME,\n" + 
			"       I.PROTOCOL_CODE,\n" + 
			"       VT.TMNL_TYPE,\n" + 
			"       I.TERMINAL_TYPE_CODE\n" + 
			"  FROM O_ORG             O,\n" + 
			"       VW_CONS_TYPE      VC,\n" + 
			"       VW_TMNL_FACTORY   VF,\n" + 
			"       I_TMNL_TASK_SETUP I,\n" + 
			"       VW_CAP_GRADE      VG,\n" + 
			"       VW_TMNL_TYPE_CODE VT,\n" + 
			"       VW_TMNL_MODE_CODE VM,\n" + 
			"       VW_PROTOCOL_CODE  VP\n" + 
			" WHERE I.ORG_NO = O.ORG_NO(+)\n" + 
			"   AND I.CONS_TYPE = VC.CONS_TYPE(+)\n" + 
			"   AND VG.CAP_GRADE_NO(+) = I.CAP_GRADE_NO\n" + 
			"   AND VM.MODE_CODE(+) = I.MODEL_CODE\n" + 
			"   AND VF.FACTORY_CODE(+) = I.FACTORY_CODE\n" + 
			"   AND VP.PROTOCOL_CODE(+) = I.PROTOCOL_CODE\n" + 
			"   AND VT.TMNL_TYPE_CODE(+) = I.TERMINAL_TYPE_CODE";


		return super.pagingFind(sql, start, limit,
				new ITmnlTaskSetupRowMapper());
	}

	class ITmnlTaskSetupRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ITmnlTaskSetupBean itmnltasksetupbean = new ITmnlTaskSetupBean();
			try {
				itmnltasksetupbean.setTaskSetupId(rs.getLong("TASK_SETUP_ID"));
				itmnltasksetupbean.setTemplateIdList(rs
						.getString("TEMPLATE_ID_LIST"));
				itmnltasksetupbean.setOrgName(rs.getString("ORG_NAME"));
				itmnltasksetupbean.setConsTypeName(rs
						.getString("CONS_TYPE_NAME"));
				itmnltasksetupbean.setConsType(rs.getString("CONS_TYPE"));
				itmnltasksetupbean.setCapGradeName(rs
						.getString("CAP_GRADE_NAME"));
				itmnltasksetupbean.setCapGradeNo(rs.getString("CAP_GRADE_NO"));
				itmnltasksetupbean.setModeName(rs.getString("MODE_NAME"));
				itmnltasksetupbean.setModelCode(rs.getString("MODEL_CODE"));
				itmnltasksetupbean.setFactoryName(rs.getString("FACTORY_NAME"));
				itmnltasksetupbean.setFactoryCode(rs.getString("FACTORY_CODE"));
				itmnltasksetupbean.setProtocolName(rs
						.getString("PROTOCOL_NAME"));
				itmnltasksetupbean.setProtocolCode(rs
						.getString("PROTOCOL_CODE"));
				itmnltasksetupbean.setTmnlType(rs.getString("TMNL_TYPE"));
				itmnltasksetupbean.setTerminalTypeCode(rs
						.getString("TERMINAL_TYPE_CODE"));
				return itmnltasksetupbean;
			} catch (Exception e) {
				return null;
			}
		}
	}

}
