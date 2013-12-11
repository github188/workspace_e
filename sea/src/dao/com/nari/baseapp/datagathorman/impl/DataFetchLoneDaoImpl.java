package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.nari.ami.database.map.measurepoint.EDataMp;
import com.nari.baseapp.datagathorman.DataFetchLoneDao;
import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orgnization.OOrg;
import com.nari.support.Page;
import com.nari.util.CheckedTreeNode;
import com.nari.util.TreeNode;
import com.nari.util.exception.DBAccessException;
import com.tangosol.java.type.Type;

public class DataFetchLoneDaoImpl extends JdbcBaseDAOImpl implements
		DataFetchLoneDao {
	/**
	 * 通过终端资产号内码找到底下直接连接的用户
	 * 
	 * @param tmnlAssetNo
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findUnderUser(final String tmnlAssetNo, final long start,
			final int limit) throws DBAccessException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select c.cons_type,c.cons_sort,c.cons_no,c.cons_name,cm.meter_id,e.id,e.mp_sn, e.asset_no, decode(mp_sn,0,'终端','测量点' || mp_sn) pn\n"
							+ "  from e_data_mp e\n"
							+ "  left join c_meter cm on (cm.meter_id = e.meter_id)\n"
							//+ "  join c_cons c on (c.cons_no = e.cons_no)\n"
							+ "  join c_cons c on (c.cons_no = cm.cons_no)\n"
							+ " where e.tmnl_asset_no = ?\n"
							+ "   and e.is_valid = 1\n"
							+ "   and (cm.tmnl_asset_no = cm.fmr_asset_no or cm.fmr_asset_no is null) order by mp_sn";
					logger.debug(sql);
					return (T) jb.pagingFind(sql, start, limit, rm,
							new Object[] { tmnlAssetNo });
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	/**
	 * 通过终端资产号内码找到底下挂的采集器
	 * 
	 * @param tmnlAssetNo
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findFrmUnderTmnl(final String tmnlAssetNo,
			final long start, final long limit) throws DBAccessException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select distinct e.tmnl_asset_no, dlc.id, dlc.asset_no, red.comm_addr\n"
							+ "  from e_data_mp e\n"
							+ "  join c_meter cm on (cm.meter_id = e.meter_id)\n"
							+ "  join D_LC_EQUIP dlc on (dlc.id = cm.fmr_asset_no)\n"
							+ "  join R_EXEC_OTHER_DEV red on (red.collector_id = dlc.id)\n"
							+ " where e.tmnl_asset_no = ?\n"
							+ "   and e.is_valid = 1 ";
					logger.debug(sql);
					return (T) jb.pagingFind(sql, start, limit, rm,
							new Object[] { tmnlAssetNo });
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	/**
	 * 通过终端资产号内码和采集器的id找到底下的测量点
	 * 
	 * @param tmnlAssetNo
	 * @param frmId
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findMeterUnderFrm(final String tmnlAssetNo,
			final String frmId, final long start, final long limit)
			throws DBAccessException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select e.asset_no,c.cons_name,c.cons_no,c.cons_sort,e.meter_id,e.id,'测量点'||mp_sn pn,e.mp_sn,c.cons_type\n"
							+ "    from e_data_mp e\n"
							+ "    join c_cons c on (e.cons_no = c.cons_no)\n"
						    + "    join c_meter cm on (cm.meter_id = e.meter_id)\n"
					
							+ "   where e.tmnl_asset_no = ?\n"
							+ "     and e.is_valid = 1\n"
							+ "     and cm.fmr_asset_no = ?\n"
							+ "     and (cm.fmr_asset_no > cm.tmnl_asset_no or\n"
							+ "         cm.fmr_asset_no < cm.tmnl_asset_no) order by mp_sn ";
					logger.debug(sql);
					return (T) jb.pagingFind(sql, start, limit, rm,
							new Object[] { tmnlAssetNo, frmId });
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	/**
	 * 通过不同的节点类型和不同的值来查询分页树所需要的数据
	 * 
	 * @param nodeStr
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public Page<TreeNode> generalPointTree(String nodeStr, long start, int limit)
			throws DBAccessException {
		try {
			String arr[] = nodeStr.split("_");
			String type = arr[0];
			String tmnlNo = arr[1];
			String frmId = arr[2];
			Page<TreeNode> pageTree = new Page<TreeNode>();
			List<TreeNode> list = new ArrayList<TreeNode>();
			// 如果是个终端
			if (type.equals("tmnl")) {
				// 初始化只挂终端
				Page<Map> directTmnl = findUnderUser(tmnlNo, start, limit);
				for (Map m : directTmnl.getResult()) {
					CheckedTreeNode tn = new CheckedTreeNode();
					// tn.setAttributes(m.get(""));
					tn.setName("用户名");

					tn.setType("meter");
					Map<String, Object> qtipCfg = new HashMap<String, Object>();
					String qtip = m.get("assetNo") == null ? "终端" : m
							.get("consNo")
							+ "";
					qtipCfg.put("text", "<textarea style=font-size:12px;>"
							+ qtip + "</textarea>");
					qtipCfg.put("autoHide", false);
					qtipCfg.put("title", "用户编号");
					tn.setQtipCfg(qtipCfg);
					tn.setChecked(false);
					if (null == m.get("assetNo")) {
						tn.setIconCls("DataFetch");
						qtipCfg.put("title", "");
						tn.setText("终端");
					} else {
						tn.setText(tn.getName() + ":" + m.get("consName")
								+ ";用户编号:" + m.get("consNo"));
					}
					// 初始化表计节点的值
					tn.setId("meter_" + m.get("id") + "_" + m.get("mpSn") + "_"
							+ m.get("consSort"));
					// tn.setQtip();
					tn.setLeaf(true);
					qtip = null;
					// tn.setIconCls("net-" + m.get(""));
					list.add(tn);
				}
				// 初始化采集器
				Page<Map> frms = findFrmUnderTmnl(tmnlNo, start, limit);
				for (Map m : frms.getResult()) {
					CheckedTreeNode tn = new CheckedTreeNode();
					// tn.setAttributes(m.get(""));
					tn.setName("采集器:" + m.get("assetNo") + "");
					tn.setText(tn.getName());
					tn.setType("frm");
					tn.setId("frm_" + m.get("tmnlAssetNo") + "_" + m.get("id"));
					Map<String, Object> qtipCfg = new HashMap<String, Object>();
					qtipCfg.put("text", "<textarea style=font-size:12px;>"
							+ m.get("commAddr") + "</textarea>");
					qtipCfg.put("autoHide", false);
					qtipCfg.put("title", "采集器地址");
					tn.setLeaf(false);
					tn.setQtipCfg(qtipCfg);
					// tn.setIconCls("net-" + m.get(""));
					list.add(tn);
				}
				pageTree.setTotalCount(directTmnl.getTotalCount());
				pageTree.setResult(list);

			} else
			// 如果是个采集器
			if (type.equals("frm")) {
				Page<Map> meters = findMeterUnderFrm(tmnlNo, frmId, start,
						limit);
				for (Map m : meters.getResult()) {
					CheckedTreeNode tn = new CheckedTreeNode();
					// tn.setAttributes(m.get(""));
					tn.setName("用户名");
					tn.setType("meter");
					// 初始化表计节点的值
					tn.setId("meter_" + m.get("id") + "_" + m.get("mpSn") + "_"
							+ m.get("consSort"));
					Map<String, Object> qtipCfg = new HashMap<String, Object>();
					String qtip = m.get("assetNo") == null ? "终端" : m
							.get("consNo")
							+ "";
					qtipCfg.put("text", "<textarea style=font-size:12px;>"
							+ qtip + "</textarea>");
					qtipCfg.put("autoHide", false);
					qtipCfg.put("title", "用户编号");
					tn.setQtipCfg(qtipCfg);
					tn.setChecked(false);
					if (null == m.get("assetNo")) {
						tn.setIconCls("DataFetch");
						qtipCfg.put("title", "");
						tn.setText("终端");
					} else {
						tn.setText(tn.getName() + ":" + m.get("consName")
								+ ";用户编号:" + m.get("consNo"));
					}
					// tn.setQtip("<textarea style=font-size:12px;>"+m.get("assetNo")+"</textarea>");
					tn.setLeaf(true);
					qtip = null;
					// tn.setIconCls("net-" + m.get(""));
					list.add(tn);
				}
			}
			// 计算数据的总长度,填充结果
//			pageTree.setTotalCount(list.size());
//			pageTree.setResult(list);
			return pageTree;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}

	}

	/**
	 * 通过e_data_mp中的id来找到相关的数据
	 * 
	 * @param id
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public Map findDataById(final String id) throws DBAccessException {

		try {
			List<Map> list = DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "SELECT E.CT,\n"
							+ "       E.PT,\n"
							+ "       E.MP_SN,\n"
							+ "       E.ASSET_NO,\n"
							+ "       C.CONS_NO,\n"
							+ "       E.METER_ID,\n"
							+ "       C.CONS_NAME,\n"
							+ "       C.CONS_TYPE,\n"
							+ "       STATUS.STATUS_NAME,\n"
							+ "       CTYPE.CONS_TYPE_NAME,\n"
							+ "       CM.T_FACTOR,\n"
							+ "       UTYPE.USAGE_TYPE_NAME,\n"
							+ "       TCODE.TYPE_NAME\n"
							+ "  FROM E_DATA_MP E\n"
							+ "  JOIN C_METER CM ON (E.METER_ID = CM.METER_ID)\n"
							+ "  JOIN C_METER_MP_RELA CMR ON (CM.METER_ID = CMR.METER_ID)\n"
							+ "  JOIN C_MP CP ON (CP.MP_ID = CMR.MP_ID)\n"
							+ "  JOIN VW_MP_USAGE_TYPE UTYPE ON (UTYPE.USAGE_TYPE_CODE =\n"
							+ "                                 CP.USAGE_TYPE_CODE)\n"
							+ "  JOIN VW_MP_TYPE_CODE TCODE ON (TCODE.TYPE_CODE = CP.TYPE_CODE)\n"
							+ "  JOIN C_CONS C ON (C.CONS_NO = E.CONS_NO)\n"
							+ "  JOIN VW_CONS_STATUS_CODE STATUS ON (STATUS.STATUS_CODE = C.STATUS_CODE)\n"
							+ "  JOIN VW_CONS_TYPE CTYPE ON (CTYPE.CONS_TYPE = C.CONS_TYPE)\n"
							+ " WHERE E.IS_VALID = 1\n" + "   AND E.ID = ?\n"
							+ "   AND ROWNUM = 1";
					logger.debug(sql);
					return (T) jb.getJdbcTemplate().query(sql,
							new Object[] { id }, rm);
				}
			});
			if (list.isEmpty()) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}

	}

	/**
	 * 查找一个终端下用户的总数
	 * 
	 * @param tmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	public Long findCoutUser(String tmnlAssetNo) throws DBAccessException {
		try {
			String sql = "SELECT COUNT(DISTINCT E.MP_SN) AS CNT\n"
					+ "  FROM E_DATA_MP E\n"
					+ "  JOIN C_METER CM ON (CM.METER_ID = E.METER_ID)\n"
					+ "  JOIN C_METER_MP_RELA CMR ON (CMR.METER_ID = CM.METER_ID)\n"
					+ "  JOIN C_MP CP ON (CP.MP_ID = CMR.MP_ID)\n"
					+ " WHERE E.IS_VALID = 1\n"
					+ "   AND CP.TYPE_CODE = '01' \n"
					+ "   AND E.TMNL_ASSET_NO = ? ";
			logger.debug(sql);
			return this.getJdbcTemplate().queryForLong(sql,
					new Object[] { tmnlAssetNo });
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	// 通过规约项的root节点来找到数据一类二类分类的节点root
	private List<TreeNode> queryClearRoot(String... args)
			throws DBAccessException {
		try {
			List<TreeNode> list = new ArrayList<TreeNode>();
			if (args.length == 0) {
				TreeNode cn1 = new TreeNode();
				cn1.setType("afn");
				cn1.setLeaf(false);
				cn1.setText("实时数据");
				cn1.setId("afn_" + 1 + "_xxoo");
				list.add(cn1);
				TreeNode cn2 = new TreeNode();
				cn2.setType("afn");
				cn2.setLeaf(false);
				cn2.setText("历史数据");
				cn2.setId("afn_" + 2 + "_xxoo");
				list.add(cn2);
				TreeNode cn3 = new TreeNode();
				cn3.setType("afn");
				cn3.setLeaf(false);
				cn3.setText("事件数据");
				cn3.setId("afn_" + 3 + "_xxoo");
				list.add(cn3);
			} else {
				String type = args[0];
				if (type.equals("1")) {
					TreeNode cn1 = new TreeNode();
					cn1.setType("afn");
					cn1.setLeaf(false);
					cn1.setText("实时数据");
					cn1.setId("afn_" + 1 + "_xxoo");
					list.add(cn1);
				} else if (type.equals("2")) {
					TreeNode cn2 = new TreeNode();
					cn2.setType("afn");
					cn2.setLeaf(false);
					cn2.setText("历史数据");
					cn2.setId("afn_" + 2 + "_xxoo");
					list.add(cn2);
				} else if (type.equals("2")) {
					TreeNode cn3 = new TreeNode();
					cn3.setType("afn");
					cn3.setLeaf(false);
					cn3.setText("事件数据");
					cn3.setId("afn_" + 3 + "_xxoo");
					list.add(cn3);
				}
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	// 通过一类二类节点来找到下面的类型数据类型 afn节点
	private List<TreeNode> queryAfnNode(final String type)
			throws DBAccessException {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			if (type.equals("3")) {
				CheckedTreeNode cn1 = new CheckedTreeNode();
				cn1.setType("dataType");
				cn1.setLeaf(true);
				cn1.setText("一般事件");
				cn1.setLeaf(true);
				cn1.setId("dataType_" + type + "_1");
				CheckedTreeNode cn2 = new CheckedTreeNode();
				cn2.setType("dataType");
				cn2.setLeaf(true);
				cn2.setText("重要事件");
				cn2.setLeaf(true);
				cn2.setId("dataType_" + type + "_2");
				list.add(cn1);
				list.add(cn2);
				return list;
			}
			// 查找数据类型所对应的数据
			String sql = " SELECT * FROM VW_CLEAR_DATA_TYPE";
			logger.debug(sql);
			getJdbcTemplate().query(sql, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					TreeNode cn = new TreeNode();
					cn.setText(rs.getString("data_name"));
					cn.setId("dataType_" + type + "_"
							+ rs.getString("data_type"));
					cn.setType("dataType");
					cn.setLeaf(false);
					list.add(cn);
					return null;
				}
			});
			logger.debug(sql);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}

	}

	/**
	 * 通过不同的数据类型来查找下面的大节点，或者单独的节点 dataType节点
	 * 
	 * @param dataType
	 *            数据的类型
	 * @param type
	 *            是一类数据还是二类数据
	 * @return
	 * @throws DBAccessException
	 */
	private List<TreeNode> queryDataTypeNode(String dataType, final String type)
			throws DBAccessException {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			// 查找数据类型所对应的数据
			String sql = "select b.protocol_no,\n"
					+ "       b.protocol_name,\n"
					+ "       b.data_type,\n"
					+ "       b.data_group,\n"
					+ "       nvl((select 0\n"
					+ "             from dual\n"
					+ "            where exists\n"
					+ "            (select 1\n"
					+ "                     from b_clear_protocol bb\n"
					+ "                    where bb.protocol_no != b.protocol_no\n"
					+ "                      and bb.protocol_no like\n"
					+ "                          substr(b.protocol_no,\n"
					+ "                                 1,\n"
					+ "                                 instr(b.protocol_no, 'F') - 1) || '%')),\n"
					+ "           1) as is_leaf\n"
					+ "  from b_clear_protocol b\n"
					+ " where b.data_type = ?\n"
					+ "   and b.data_group in (?, 3)\n"
					+ "   and instr(b.protocol_no, 'F') > 0";
			logger.debug(sql);
			this.getJdbcTemplate().query(sql, new Object[] { dataType, type },
					new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							CheckedTreeNode cn = new CheckedTreeNode();
							cn.setText(rs.getString("protocol_name"));
							String is_leaf = rs.getString("is_leaf");
							if (is_leaf.equals("1")) {
								cn.setLeaf(true);
								cn.setId("small_" + rs.getString("protocol_no")
										+ "_" + type);
								cn.setType("small");
							} else if (is_leaf.equals("0")) {
								cn.setType("big");
								cn.setId("big_" + rs.getString("protocol_no")
										+ "_" + type);
								cn.setLeaf(false);
							}
							list.add(cn);
							return null;
						}
					});
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}

	}

	// 通过大项的节点找下面的小型 big 小项为small
	private List<TreeNode> queryBigNode(String code, final String type)
			throws DBAccessException {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			// 查找数据类型所对应的数据
			String sql = "select *\n"
					+ "  from b_clear_protocol b\n"
					+ " where b.data_group in (?, 3)\n"
					+ "   and b.protocol_no != ? and b.protocol_no like substr(?,1,instr(?, 'F') - 1)||'%'";
			logger.debug(sql);
			getJdbcTemplate().query(sql,
					new Object[] { type, code, code, code }, new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							TreeNode cn = new TreeNode();
							cn.setText(rs.getString("protocol_name"));
							cn.setType("small");
							cn.setId("small_" + rs.getString("protocol_no")
									+ "_" + type);
							cn.setLeaf(true);
							list.add(cn);
							return null;
						}
					});
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}
	}

	/**
	 * 统一处理不同的类型的规约项的节点。来生成树
	 * 
	 * @param nodeStr
	 *            节点字符串,通过_连接
	 * @return
	 * @throws DBAccessException
	 */
	public List<TreeNode> dealClearTree(String nodeStr)
			throws DBAccessException {
		try {
			List<TreeNode> list = new ArrayList<TreeNode>();
			String arr[] = nodeStr.split("_");
			String type = arr[0];
			if (type.equals("root")) {
				list = queryClearRoot();
			} else if (type.equals("afn")) {
				list = queryAfnNode(arr[1]);
			} else if (type.equals("dataType")) {
				list = queryDataTypeNode(arr[2], arr[1]);
			} else if (type.equals("big")) {
				list = queryBigNode(arr[1], arr[2]);
			} else if (type.equals("small")) {

			}
			// 查找数据类型所对应的数据
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e, e.getMessage());
		}

	}

	// 按不同类别的用户来生成规约项
	// 通过不同的根节点的id来生成不同类别的树
	private List<TreeNode> createTypeByRoot(String nodeStr) {
		try {
			List<TreeNode> list = new ArrayList<TreeNode>();
			String arr[] = nodeStr.split("_");
			// type表示a-f类用户的分类
			String userType = arr[2];
			TreeNode tn1 = new TreeNode();
			tn1.setName("实时数据");
			tn1.setId("afn_1_" + userType);
			tn1.setLeaf(false);
			tn1.setText(tn1.getName());
			TreeNode tn2 = new TreeNode();
			tn2.setName("历史数据");
			tn2.setLeaf(false);
			tn2.setId("afn_2_" + userType);
			tn2.setText(tn2.getName());
			// TreeNode tn3 = new TreeNode();
			// tn3.setLeaf(false);
			// tn3.setName("事件数据");
			// tn3.setId("afn_3_" + userType);
			// tn3.setText(tn3.getName());
			list.add(tn1);
			list.add(tn2);
			// list.add(tn3);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	// 通过不同的数据类型afn节点来生成不同的dataType节点
	private List<TreeNode> createDownDataTypeByType(String nodeStr) {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			final String arr[] = nodeStr.split("_");
			// 数据的afn类型
			final String type = arr[1];
			if (type.equals("3")) {
				CheckedTreeNode cn1 = new CheckedTreeNode();
				cn1.setType("dataType");
				cn1.setLeaf(true);
				cn1.setText("一般事件");
				cn1.setLeaf(true);
				cn1.setId("dataType_" + type + "_1");
				CheckedTreeNode cn2 = new CheckedTreeNode();
				cn2.setType("dataType");
				cn2.setLeaf(true);
				cn2.setText("重要事件");
				cn2.setLeaf(true);
				cn2.setId("dataType_" + type + "_2");
				list.add(cn1);
				list.add(cn2);
				return list;
			}
			// 查找数据类型所对应的数据
			String sql = " SELECT * FROM VW_CLEAR_DATA_TYPE";
			logger.debug(sql);
			getJdbcTemplate().query(sql, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					TreeNode cn = new TreeNode();
					cn.setText(rs.getString("data_name"));
					cn.setId("dataType_" + type + "_"
							+ rs.getString("data_type") + "_"
							+ arr[2].toUpperCase());
					cn.setType("dataType");
					cn.setLeaf(false);
					list.add(cn);
					return null;
				}
			});
			logger.debug(sql);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	// 通过不同的dataype节点来生成不同的dataype节点
	private List<TreeNode> createDownBigByType(String nodeStr) {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			String arr[] = nodeStr.split("_");
			// 不同的规约的类型
			String dataType = arr[2];
			// 用户的不同的类型
			String userType = arr[3];
			// 不同的afn类型
			final String type = arr[1];
			String sql = "SELECT DISTINCT B.DATA_TYPE,\n"
					+ "       B.DATA_GROUP,\n"
					+ "       BCP.CLEAR_PROT_NO,\n"
					+ "       BC.USUA_USE_NAME,\n"
					+ "       BC.IS_USUA_USE,\n"
					+ "       decode((select count(1)\n"
					+ "                from b_clear_protocol bb\n"
					+ "               where bb.protocol_no like\n"
					+ "                     replace(b.protocol_no, 'F', '') || '%'),\n"
					+ "              1,\n"
					+ "              1,\n"
					+ "              0) is_leaf\n"
					+ "  FROM B_CONS_SORT_PROTOCOL_NO BC\n"
					+ "  JOIN B_COMM_PROTOCOL BCP ON (BC.PROTOCOL_NO = BCP.PROTOCOL_NO)\n"
					+ "  JOIN B_CLEAR_PROTOCOL B ON (BCP.CLEAR_PROT_NO = B.PROTOCOL_NO)";

			StringBuilder sb = new StringBuilder(sql);
			sb.append(" WHERE BC.CONS_SORT in (");
			sb.append(getConsStrByType(userType));
			sb.append(")  AND B.DATA_TYPE = ?\n  AND B.DATA_GROUP IN (?, 3)");
			logger.debug(sb.toString());
			getJdbcTemplate().query(sb.toString(),
					new Object[] { dataType, type }, new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							String isLeaf = rs.getString("is_leaf");
							CheckedTreeNode cn = new CheckedTreeNode();
							cn.setName(rs.getString("USUA_USE_NAME"));
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("isUsuaUse", rs.getString("IS_USUA_USE"));
							hm.put("dataGroup", rs.getString("DATA_GROUP"));
							cn.setAttributes(hm);
							cn.setText(cn.getName());
							cn.setId("big_" + rs.getString("CLEAR_PROT_NO")
									+ "_" + type);
							if (isLeaf.equals("1")) {
								cn.setLeaf(true);
							} else {
								cn.setLeaf(false);
							}
							list.add(cn);
							return null;
						}
					});
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	// 通过不同的用户类型来生成需要的sql
	private String getConsStrByType(String type) {
		String sb2 = new String("'A','B','F'");
		if (sb2.indexOf(type) >= 0) {
			return sb2.toString();
		}
		sb2 = new String("'C'");
		if (sb2.indexOf(type) >= 0) {
			return sb2.toString();
		}
		sb2 = new String("'D','E'");
		if (sb2.indexOf(type) >= 0) {
			return sb2.toString();
		}
		sb2 = new String("'G'");
		if (sb2.indexOf(type) >= 0) {
			return sb2.toString();
		}
		throw new RuntimeException("暂时不支持的的用户类型");
	}

	// 通过大项来生成小项的节点
	private List<TreeNode> createDownSmallByType(String nodeStr) {
		try {
			final List<TreeNode> list = new ArrayList<TreeNode>();
			String arr[] = nodeStr.split("_");
			// afn类型
			final String type = arr[2];
			// 透明规约的编码
			String code = arr[1];
			// 找到某个透明规约编码下的所有的小项
			String sql = "select *\n" + "  from b_clear_protocol b\n"
					+ " where b.protocol_no like replace(?, 'F', '') || '%'\n"
					+ "   and b.protocol_no != ?\n" + "   and b.data_group = ?";
			logger.debug(sql);
			getJdbcTemplate().query(sql, new Object[] { code, code, type },
					new RowMapper() {
						@Override
						public Object mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							TreeNode tn = new TreeNode();
							tn.setName(rs.getString("protocol_name"));
							tn.setText(tn.getName());
							tn.setType("small");
							tn.setLeaf(true);
							tn.setId("small_" + rs.getString("protocol_no")
									+ type);
							list.add(tn);
							return null;
						}
					});
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过不同的节点来生成不同的下级节点,向外部暴露的处理不同类型树的方法
	 * 
	 * @param nodeStr
	 *            传入的节点id
	 * @return
	 * @throws DBAccessException
	 */
	public List<TreeNode> createTypeTree(String nodeStr)
			throws DBAccessException {
		try {
			List<TreeNode> list = new ArrayList<TreeNode>();
			String arr[] = nodeStr.split("_");
			String nodeType = arr[0];
			if (nodeType.equals("root")) {
				list = createTypeByRoot(nodeStr);
			} else if (nodeType.equals("afn")) {
				list = createDownDataTypeByType(nodeStr);
			} else if (nodeType.equals("dataType")) {
				list = createDownBigByType(nodeStr);
			} else if (nodeType.equals("big")) {
				list = createDownSmallByType(nodeStr);
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DBAccessException(e.getMessage());
		}
	}
}
