package com.nari.qrystat.colldataanalyse.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.HasAnnotation;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.DaoUtils;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GChkunitRefIdx;
import com.nari.qrystat.colldataanalyse.Gchkunit;
import com.nari.qrystat.colldataanalyse.GchkunitComp;
import com.nari.qrystat.colldataanalyse.LosePowerManJdbcDao;
import com.nari.support.Page;
import com.nari.util.AutoLang;
import com.nari.util.CreateInsert;
import com.nari.util.NamedCreateInsert;
import com.tangosol.dev.assembler.Try;

/**
 * 台区线损管理jdbc实现类
 * 
 * @author huangxuan
 * 
 */
public class LosePowerManJdbcDaoImpl extends JdbcBaseDAOImpl implements
		LosePowerManJdbcDao {
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 注册一些台区到相应组合表中
	 * 
	 * @param g
	 * @param tgIds
	 */
	public void saveGChkunit(Gchkunit g, List<Integer> tgIds) {
		// true参数表示save后g的id也被自动的填充
		CreateInsert c = new CreateInsert(true);
		g.setDataSrc("02");

		c.save(g);
		// 去掉自动填充id
		c.setInitId(false);
		// 如果tgIds为空只在主表中插数据，不在从表中插数据
		if (null == tgIds) {
			return;
		}
		for (Integer id : tgIds) {
			GchkunitComp gc = new GchkunitComp();
			gc.setChkunitId(g.getChkunitId());
			gc.setObjId(id);
			gc.setObjTypeCode(g.getChkunitTypeCode());
			c.save(gc);
		}
	}

	/**
	 * 修改一个考核单元中的内容
	 * 
	 * @param g
	 */
	public void updateGChkunit(Gchkunit g) {
		g.setDataSrc("02");
		String sql = "update g_chkunit g\n"
				+ "   set g.chkunit_type_code = :chkunitTypeCode,\n"
				+ "       g.chkunit_name      = :chkunitName,\n"
				+ "       g.org_no            = :orgNo,\n"
				+ "       g.exam_flag         = :examFlag,\n"
				+ "       g.link_flag         = :linkFlag,\n"
				+ "       g.resp_emp_no       = :respEmpNo,\n"
				+ "       g.chk_cycle         = :chkCycle,\n"
				+ "       g.det_sort_code     = :detSortCode,\n"
				+ "       g.data_src          = :dataSrc,\n"
				+ "       g.status_code          = :statusCode\n"
				+ " where g.chkunit_id = :chkunitId";
		NamedCreateInsert nc = new NamedCreateInsert();
		nc.getNamedParameterJdbcTemplate().update(sql,
				AutoLang.fromBean(g, false));
	}

	/**
	 * 删除某个考核单元下的所指定的台区
	 * 
	 * @param gId
	 * @param tgs
	 */
	public void deleteGChkunitComp(Integer gId, List<Integer> tgs) {
		Assert.assertNotNull(gId);
		String sql = "delete from g_chkunit_comp gc\n"
				+ "  where gc.chkunit_id = ?\n" + "    and   ";
		StringBuilder sb = new StringBuilder(sql);
		sb.append(AutoLang.autoIn("gc.obj_id", tgs, 500));
		LinkedList<Integer> list = new LinkedList<Integer>(tgs);
		list.addFirst(gId);
		getJdbcTemplate().update(sb.toString(), list.toArray());
	}

	/**
	 * 找到某个考核单元下的所有的台区
	 * 
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findTgsInGChkunit(PSysUser user, final Integer gid,
			final long start, final int limit) {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select gc.*, gt.tg_name, r.terminal_addr\n"
							+ "  from g_chkunit_comp gc\n"
							+ "  join g_tg gt on (gt.tg_id = gc.obj_id)\n"
							+ "  join c_cons c on (c.tg_id=gt.tg_id)\n"
							+ "  join o_org o on (o.org_no = gt.org_no)\n "
							+ "  join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
							+ "where gc.chkunit_id = ? and c.cons_type=2 ";
					return (T) jb.pagingFind(sql, start, limit, rm,
							new Object[] { gid });
				}
			});
		} catch (Exception e) {
			logger.error("findTgsInGChkunit:error" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 在考核单元中添加新的台区
	 * 
	 * @param gid
	 *            考核单元的名称
	 * @param tgs
	 *            添加的台区的id
	 */
	@SuppressWarnings("unchecked")
	public void addTgs(final Integer gid, final List tgs) {
		try {
			StringBuilder sb = new StringBuilder(
					"merge into g_chkunit_comp gc\n"
							+ "using (select 1 from dual)\n"
							+ "on (gc.obj_id = ? and gc.chkunit_id = ?)\n"
							+ "when not matched then\n"
							+ "  insert values (s_g_chkunit_comp.nextval,?, ?, '02')");

			getJdbcTemplate().batchUpdate(sb.toString(),
					new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							Object id = tgs.get(i);
							ps.setObject(1, id);
							ps.setObject(2, gid);
							ps.setObject(3, gid);
							ps.setObject(4, id);
						}

						@Override
						public int getBatchSize() {

							return tgs.size();
						}
					});
		} catch (Exception e) {
			logger.error("addTgs" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除指定的考核单元
	 * 
	 * @param user
	 * @param gids
	 *            选择要被删除的考核单元
	 */
	public void deleteGChkunit(final PSysUser user, final List<Integer> gids) {
		Assert.assertNotNull(gids);
		try {
			final JdbcBaseDAOImpl me = this;
			getJdbcTemplate()
					.query(
							"select table_name\n"
									+ "  from user_tab_cols\n"
									+ " where column_name = 'CHKUNIT_ID'\n"
									+ " order by case when table_name='G_CHKUNIT'  then 99999\n"
									+ " else 1 end asc", new Object[] {},
							new RowMapper() {

								@Override
								public Object mapRow(ResultSet rs, int rowNum)
										throws SQLException {
									String sql = "delete from "
											+ rs.getString("table_name")
											+ " where CHKUNIT_ID=? ";

									getJdbcTemplate().batchUpdate(sql,
											new BatchPreparedStatementSetter() {

												@Override
												public void setValues(
														PreparedStatement ps,
														int i)
														throws SQLException {
													Integer id = gids.get(i);
													ps.setObject(1, id);
												}

												@Override
												public int getBatchSize() {
													return gids.size();
												}
											});

									return null;
								}
							});

			// getJdbcTemplate()
			// .batchUpdate(
			// " delete   from  g_chkunit  cascade   where chkunit_id=? and  resp_emp_no=? ",
			// new BatchPreparedStatementSetter() {
			//
			// @Override
			// public void setValues(PreparedStatement ps,
			// int i) throws SQLException {
			// Integer id = gids.get(i);
			// ps.setObject(1, id);
			// ps.setString(2, user.getStaffNo());
			// }
			//
			// @Override
			// public int getBatchSize() {
			//
			// return gids.size();
			// }
			// });

			// getJdbcTemplate().update(, new Object []{gid,
			// user.getStaffNo()
			// });
		} catch (Exception e) {
			logger.error("deleteGChkunit" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void logoutGchkunit(final PSysUser user, final List<Integer> gids) {
		try {
			updateState(user, gids, 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	private void updateState(final PSysUser user, final List<Integer> gids,
			final Integer statusCode) {

		String sql = "update g_chkunit set status_code =? where resp_emp_no=? and chkunit_id=?";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setInt(1, statusCode);
				ps.setString(2, user.getStaffNo());
				ps.setInt(3, gids.get(i));
			}

			@Override
			public int getBatchSize() {
				return gids.size();
			}
		});

	}

	/**
	 * 向G_IO_MP中加入数据，通过METER_ID和MP_ID两个字段来merge
	 * 
	 * @param gid
	 *            考核单元id
	 * @param isIn
	 *            是流入还是流出
	 * @param mpIds
	 *            所选的电能表id列表
	 * @param meterIds
	 *            所选的表计id列表
	 * @param dataIds
	 *            e_data_mp中的id
	 */
	public void mergeGiomp(final Integer gid, final Integer isIn,
			final Integer isVaild, final List<String> mpIds,
			final List<String> meterIds, final List<String> dataIds,
			final List<String> pqFlags, final List<String> objIds) {
		try {
			String sql = null;
			if (isVaild.equals(0)) {
				sql = "merge into g_io_mp gm\n"
						+ "using (select 1 from dual)\n"
						+ "on (chkunit_id = ? and mp_id = ? and meter_id = ?)\n"
						+ "when matched then\n"
						+ "  update set data_id=?,is_in = ?,obj_id=?,pq_flag=?,is_valid=?,UNUSED_DATE=trunc(sysdate)\n"
						+ "when not matched then\n"
						+ "  insert values (s_g_io_mp.nextval,?,?,?,?,?,'81',?,trunc(sysdate),null,1)";
			} else if (isVaild.equals(1)) {
				sql = "merge into g_io_mp gm\n"
						+ "using (select 1 from dual)\n"
						+ "on (chkunit_id = ? and mp_id = ? and meter_id = ?)\n"
						+ "when matched then\n"
						+ "  update set data_id=?,is_in = ?,obj_id=?,pq_flag=?,is_valid=?\n"
						+ "when not matched then\n"
						+ "  insert values (s_g_io_mp.nextval,?,?,?,?,?,'81',?,trunc(sysdate),null,1)";
			}

			getJdbcTemplate().batchUpdate(sql,
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							String mpId = mpIds.get(i);
							String meterId = meterIds.get(i);
							String dataId = dataIds.get(i);
							String pqFlag = pqFlags.get(i);
							if(pqFlag.trim().equals("")||pqFlag.equals("-1")){
								pqFlag="81";
							}
							String objId = objIds.get(i);
							int j = 1;
							ps.setInt(j++, gid);
							ps.setString(j++, mpId);
							ps.setString(j++, meterId);
							ps.setString(j++, dataId);
							ps.setInt(j++, isIn);
							ps.setString(j++, objId);
							ps.setString(j++, pqFlag);
							ps.setInt(j++, isVaild);
							ps.setInt(j++, gid);
							ps.setString(j++, objId);
							ps.setString(j++, mpId);
							ps.setString(j++, meterId);
							ps.setString(j++, dataId);
							ps.setInt(j++, isIn);
//							System.out.println(gid);
//							System.out.println(objId);
//							System.out.println(mpId);
//							System.out.println(meterId);
//							System.out.println(dataId);
//							System.out.println(isIn);
						}

						public int getBatchSize() {
							return mpIds.size();
						}
					});

		} catch (Exception e) {
			logger.error("deleteGChkunit" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 找到一个考核单元下所有的终端的终端地址
	 * 
	 * @param gid
	 * @param type
	 *            根据不同的type来找到终端地址
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findAddrInGChkunit(final Integer gid, final String type) {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql=null;
					if (type.equals("01") ) {
							sql="select distinct r.terminal_addr\n"
								+ "  from g_chkunit_comp gc\n"
								+ "  join c_cons c on (gc.obj_id = c.line_id)\n"
								+ "  join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
								+ " where gc.chkunit_id = ?";
					} else if (type.equals("02")) {
						sql = "select distinct r.terminal_addr\n"
							+ "  from g_chkunit_comp gc\n"
							+ "  join g_tg gt on (gt.tg_id = gc.obj_id)\n"
							+ "  join c_cons c on (c.tg_id = gt.tg_id)\n"
							+ "  join vw_tmnl_run r on (c.cons_no = r.cons_no)\n"
							+ " where gc.chkunit_id = ?";
					}
					return (T) jb.getJdbcTemplate().query(sql,
							new Object[] { gid }, rm);
				}
			});
		} catch (Exception e) {
			logger.error("findTgsInGChkunit:error" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过对chkunit_id的判断来更新或者插入一条参考指标
	 * 
	 * @param gr
	 */
	public void saveOrUpdateRef(GChkunitRefIdx gr) {
		try {
			String sql = "merge into g_chkunit_ref_idx gr\n"
					+ "using (select 1 from dual)\n"
					+ "on (chkunit_id=:chkunitId)\n"
					+ "when matched then\n"
					+ "update set ll_idx_name=:llIdxName,ll_idx_value=:llIdxValue,chk_cycle=:chkCycle\n"
					+ "when not matched then\n"
					+ "insert values(s_g_chkunit_ref_idx.nextval,:chkunitId,:llIdxName,:llIdxValue,:chkCycle)";
			NamedCreateInsert nc = new NamedCreateInsert();
			nc.getNamedParameterJdbcTemplate().update(sql,
					AutoLang.fromBean(gr, false));
		} catch (Exception e) {
			logger.error("saveOrUpdateRef:error" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/**
	 * 删除一个考核指标，通过chkunit_id
	 * 
	 * @param gid
	 */
	public void deleteRef(Integer gid) {
		try {
			String sql = "delete from g_chkunit_ref_idx where chkunit_id = ?";
			getJdbcTemplate().update(sql, new Object[] { gid });
		} catch (Exception e) {
			logger.error("saveOrUpdateRef:error" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过一个id来找到一个参考指标的,如果没有找到返回null
	 * 
	 * @param gid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map findRefByChkunit(final Integer gid) {
		try {

			List<Map> list = DaoUtils.run("[*,c]", new IDoDao() {

				@SuppressWarnings("unchecked")
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select * from g_chkunit_ref_idx where chkunit_id=? and rownum=1";

					return (T) jb.getJdbcTemplate().query(sql,
							new Object[] { gid }, rm);
				}
			});
			if (list.size() == 0) {
				return null;
			} else
				return list.get(0);
		} catch (Exception e) {
			logger.error("saveOrUpdateRef:error" + e.getMessage());
			throw new RuntimeException(e);
		}

	}
/**
 * 判断某个用户是不是有某个url的访问权限
 * @param user
 * @param url
 * @return
 */
	public boolean hasMenuPower(PSysUser user, String url) {
		try {
			if (null == url) {
				return false;
			}
			url = url.trim().toLowerCase();
			String sql = "SELECT 1\n"
					+ "     FROM DUAL\n"
					+ "    WHERE 'admin'=? OR EXISTS (SELECT DISTINCT PM.TITLE\n"
					+ "             FROM P_USER_ROLE_RELA PRR\n"
					+ "             JOIN P_ROLE_PRIV_RELA PRP ON (PRR.ROLE_ID = PRP.ROLE_ID)\n"
					+ "             JOIN P_MENU PM ON (PM.MENU_NO = PRP.MENU_NO) WHERE PRR.STAFF_NO=?  AND LOWER(TRIM(PM.URL)) =?)";
			final StringBuilder sb=new StringBuilder();
			getJdbcTemplate().queryForObject(sql, new Object[] {user.getStaffNo(),user.getStaffNo(),url},
					new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							sb.append("ok");
							return null;
						}
					});
			if (sb.length()==0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("查询失败"+e.getMessage());
		}
		return false;
	}

	/**
	 * 在新建台区的时候，在提交的台区中找到已经被注册的， <br>
	 * 并且把找的已经被注册的台区的被注册的考核单元的名称和id等信息返回
	 * 
	 * @param tgIds
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> checkExistsTgs(final List<Integer> tgIds,
			final long start, final int limit) {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select o.org_name,gt.tg_id, gt.tg_no, gt.tg_name, gt.run_status_code, gc.*\n"
							+ "   from g_chkunit_comp gcc\n"
							+ "   join g_tg gt on (gt.tg_id = gcc.obj_id)\n"
							+ "   join g_chkunit gc on (gc.chkunit_id = gcc.chkunit_id)\n"
							+ "   join o_org o on (gc.org_no=o.org_no)\n"
							+ "  where  gc.chkunit_type_code='02' and ";
					return (T) jb.pagingFind(sql
							+ AutoLang.autoIn("gcc.obj_id", tgIds, 500), start,
							limit, rm, tgIds.toArray());
				}
			});
		} catch (Exception e) {
			logger.error("checkExistsTg" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	// 修改参考指标的实现
	/**
	 * 找到一个考核单元下面的所有的参考指标
	 * 
	 * @param chkunitId
	 *            考核周期名称
	 */
	@SuppressWarnings("unchecked")
	public List<GChkunitRefIdx> findRefsByChkunitId(Integer chkunitId) {
		try {
			String sql = "select gr.*,g.chkunit_name,g.data_src from g_chkunit_ref_idx gr"
					+ " join g_chkunit g on(g.chkunit_id=gr.chkunit_id) where gr.chkunit_id=?";
			return getJdbcTemplate().query(sql, new Object[] { chkunitId },
					new BeanPropertyRowMapper(GChkunitRefIdx.class));
		} catch (Exception e) {
			logger.error("findRefsByChkunitId" + e.getMessage());
			throw new RuntimeException(e);
		}

	}

	/**
	 * 通过msg的不同更新或者保存一个参考指标的列表 此方法能保证操作后chkunit和周期能唯一确定一条记录 <br>
	 * 对于增加
	 * 
	 * @param refs
	 */
	public void updateRefs(List<GChkunitRefIdx> refs) {
		try {
			// 分两条语句是因为如果这个记录认为是被插入，就不能更新语句
			// 如果是被认为是更新语句就不能插入，避免可能出现的同事操作数据而出现的大多数问题
			String updateSql = "merge into g_chkunit_ref_idx gi\n"
					+ "using (select 1 from dual)\n"
					+ "on (chkunit_id = ? and chk_cycle = ?)\n"
					+ "when matched then\n"
					+ "  update set ll_idx_name = ?,ll_idx_value = ?";
			String insertSql = "\n"
					+ "merge into g_chkunit_ref_idx gi\n"
					+ "using (select 1 from dual)\n"
					+ "on (chkunit_id = ? and chk_cycle = ?)\n"
					+ "when not matched then\n"
					+ "  insert values (s_g_chkunit_ref_idx.nextval, ?, ?, ?, ?)";
			String deleteSql = "delete from g_chkunit_ref_idx\n"
					+ " where chkunit_id = ?\n" + "   and chk_cycle = ?";

			for (GChkunitRefIdx gcf : refs) {
				// 假设这个参考指标是被认为要被添加的
				if (gcf.getMsg().equals("new")) {
					getJdbcTemplate().update(
							insertSql,
							new Object[] { gcf.getChkunitId(),
									gcf.getChkCycle(), gcf.getChkunitId(),
									gcf.getLlIdxName(), gcf.getLlIdxValue(),
									gcf.getChkCycle() });
				} else
				// 假设这个参考指标被认为是要进行更新操作的
				if (gcf.getMsg().equals("edit")) {
					getJdbcTemplate().update(
							updateSql,
							new Object[] { gcf.getChkunitId(),
									gcf.getChkCycle(), gcf.getLlIdxName(),
									gcf.getLlIdxValue() });
				} else
				// 如果被认为是删除操作
				if (gcf.getMsg().equals("del")) {
					getJdbcTemplate().update(
							deleteSql,
							new Object[] { gcf.getChkunitId(),
									gcf.getChkCycle() });
				}
			}
		} catch (Exception e) {
			logger.error("updateRefs" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过参考指标的id列表来删除
	 * 
	 * @param idxIds
	 */
	public void deleteRefByIdxIds(List<Integer> idxIds) {
		try {
			String sql = "delete from g_chkunit_ref_idx  where "
					+ AutoLang.autoIn("ll_idx_id", idxIds, 500);
			getJdbcTemplate().update(sql, idxIds.toArray());
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}
