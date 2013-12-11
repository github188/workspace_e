package com.nari.qrystat.colldataanalyse.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.DaoUtils;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GChkUnitFinder;
import com.nari.qrystat.colldataanalyse.GChkunitRefIdx;
import com.nari.qrystat.colldataanalyse.Gchkunit;
import com.nari.qrystat.colldataanalyse.LeftTreeLineFinder;
import com.nari.qrystat.colldataanalyse.LosePowerManJdbcDao;
import com.nari.qrystat.colldataanalyse.LosePowerManManager;
import com.nari.qrystat.colldataanalyse.MeterMpFinder;
import com.nari.qrystat.colldataanalyse.TgFinder;
import com.nari.support.Page;
import com.nari.util.AutoLang;
import com.nari.util.ResultParse;
import com.nari.util.SelectSqlCreator;
import com.nari.util.exception.ServiceException;

/**
 * 台区线损管理实现类
 * 
 * @author huangxuan
 * 
 */
public class LosePowerManManagerImpl implements LosePowerManManager {
	private LosePowerManJdbcDao losePowerManJdbcDao;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 通过左边树来
	 * 
	 * @param user
	 * @param nodeStr
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findLeftTreeClick(PSysUser user, String nodeStr)
			throws ServiceException {
		try {
			if (null == nodeStr) {
				throw new RuntimeException("传入数据不能为空");
			}
			String arr[] = nodeStr.split("_");
			if (arr.length < 2) {
				throw new RuntimeException("传入的数据格式不正确");
			}
			final String type = arr[0];
			String value = arr[1];
			final StringBuilder svalue = new StringBuilder();
			SqlData sqlData = SqlData.getOne();
			final StringBuilder sb = new StringBuilder();
			if (type.equals("org")) {
				sb.append(sqlData.losePower_fingByOrg);
				svalue.append(value + "%");
			} else if (type.equals("line")) {
				sb.append(sqlData.losePower_fingByLine);
				svalue.append(value);
			} else if (type.equals("tmnl")) {
				sb.append(sqlData.losePower_findByTmnl);
				svalue.append(value);
			} else {
				throw new RuntimeException("不支持所选择的节点");
			}
			// 加入权限的代码
			// sb.append(SysPrivilige.add);
			return DaoUtils.run("[*,c]", new IDoDao() {
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					// System.out.println("in:"+sb.toString());
					// System.out.println("in:"+svalue.toString());
					return (T) jb.getJdbcTemplate().query(sb.toString(),
							new Object[] { svalue.toString() }, rm);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 通过考核单元id来查找一个考核单元下面的所有的台区
	 * 
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findPageTgsInGchkunit(final Integer gid, final long start,
			final int limit) throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@SuppressWarnings("unchecked")
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.pagingFind(sd.losePower_findInGCHKUNIT,
							start, limit, rm, new Object[] { gid });
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 分页版本的通过左边树来查找相应的台区的信息
	 * 
	 * @param user
	 * @param start
	 * @param limit
	 * @param nodeStr
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findPageLeftClick(PSysUser user, final long start,
			final int limit, String nodeStr, TgFinder finder)
			throws ServiceException {
		try {
			if (null == nodeStr) {
				throw new RuntimeException("传入数据不能为空");
			}
			String arr[] = nodeStr.split("_");
			if (arr.length < 2) {
				throw new RuntimeException("传入的数据格式不正确");
			}
			final String type = arr[0];
			String value = arr[1];
			final StringBuilder svalue = new StringBuilder();
			SqlData sqlData = SqlData.getOne();
			final LinkedList list = new LinkedList();
			final StringBuilder sb = new StringBuilder();
			ResultParse re = SelectSqlCreator.getSelectSql(finder);
			if (type.equals("org")) {
				if (re.getSql().trim().equals("")) {
					sb.append(sqlData.losePower_fingByOrg);
					list.add(value + "%");
				} else {
					sb.append(sqlData.losePower_fingByOrg + " where "
							+ re.getSql());
					list.add(value + "%");
					list.addAll(re.getArgs());
				}
			} else if (type.equals("line")) {
				if (re.getSql().trim().equals("")) {
					sb.append(sqlData.losePower_fingByLine);
					list.add(value);
				} else {
					sb.append(sqlData.losePower_fingByLine + " where "
							+ re.getSql());
					list.add(value);
					list.addAll(re.getArgs());
				}
			} else if (type.equals("tmnl")) {
				if (re.getSql().trim().equals("")) {
					sb.append(sqlData.losePower_findByTmnl);
					list.add(value);
				} else {
					sb.append(sqlData.losePower_findByTmnl + " where "
							+ re.getSql());
					list.add(value);
					list.addAll(re.getArgs());
				}
			} else {
				throw new RuntimeException("不支持所选择的节点");
			}
			// 加入权限的代码
			// sb.append(SysPrivilige.add);
			return DaoUtils.run("[*,c]", new IDoDao() {
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					// System.out.println("in:"+sb.toString());
					// System.out.println("in:"+svalue.toString());
					return (T) jb.pagingFind(sb.toString(), start, limit, rm,
							list.toArray());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 分页来通过finder实体来找到所有的满足要求的考核单元
	 * 
	 * @param user
	 *            当前的用户
	 * @param start
	 * @param limit
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findChkUnit(final PSysUser user, final long start,
			final int limit, final GChkUnitFinder finder)
			throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					ResultParse re = SelectSqlCreator.getSelectSql(finder);
					if (re.getSql().trim().equals("")) {
						return (T) jb.pagingFind(sd.losePower_findChkUnit,
								start, limit, rm, new Object[] {
										user.getStaffNo(),
										user.getOrgNo() + "%",
										user.getStaffNo() });
					}
					LinkedList list = new LinkedList(re.getArgs());
					list.addFirst(user.getOrgNo());
					list.addFirst(user.getOrgNo() + "%");
					list.addFirst(user.getStaffNo());
					return (T) jb.pagingFind(sd.losePower_findChkUnit
							+ " where " + re.getSql(), start, limit, rm, list
							.toArray());
				}
			});
		} catch (Exception e) {
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 注册一些台区到相应组合表中
	 * 
	 * @param g
	 * @param tgIds
	 * @throws ServiceException
	 */
	public void saveGChkunit(Gchkunit g, List<String> chkCycles,
			List<Integer> tgIds) throws ServiceException {
		try {
			g.setChkCycle(convertBs(chkCycles));
			losePowerManJdbcDao.saveGChkunit(g, tgIds);
		} catch (Exception e) {
			logger.error("saveGChkunit:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	};

	// 将日月季半年年转化为bs时间
	private String convertBs(Collection<String> cs) {
		StringBuilder sb = new StringBuilder("00000");
		for (String str : cs) {
			if (str.equals("日")) {
				sb.replace(0, 1, "1");
			} else if (str.equals("月")) {
				sb.replace(1, 2, "1");
			} else if (str.equals("季")) {
				sb.replace(2, 3, "1");
			} else if (str.equals("半年")) {
				sb.replace(3, 4, "1");
			} else if (str.equals("年")) {
				sb.replace(4, 5, "1");
			}
		}
		return sb.toString();
	}

	/**
	 * 找到某个考核单元下的所有的台区
	 * 
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page findTgsInGChkunit(PSysUser user, Integer gid, long start,
			int limit) throws ServiceException {
		try {
			return losePowerManJdbcDao.findTgsInGChkunit(user, gid, start,
					limit);
		} catch (Exception e) {
			logger.error("findTgsInGChkunit:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	};

	/**
	 * 修改一个考核单元中的内容
	 * 
	 * @param g
	 * @throws ServiceException
	 */
	public void updateGChkunit(Gchkunit g) throws ServiceException {
		try {
			losePowerManJdbcDao.updateGChkunit(g);
		} catch (Exception e) {
			logger.error("updateGChkunit:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	};

	/**
	 * 删除某个考核单元下的所指定的台区
	 * 
	 * @param gId
	 * @param tgs
	 * @throws ServiceException
	 */
	public void deleteGChkunitComp(Integer gId, List<Integer> tgs)
			throws ServiceException {
		try {
			losePowerManJdbcDao.deleteGChkunitComp(gId, tgs);
		} catch (Exception e) {
			logger.error("deleteGChkunitComp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	};

	/**
	 * 在考核单元中添加新的台区
	 * 
	 * @param gid
	 *            考核单元的名称
	 * @param tgs
	 *            添加的台区的id
	 */
	@SuppressWarnings("unchecked")
	public void addTgs(Integer gid, List tgs) throws ServiceException {
		try {
			losePowerManJdbcDao.addTgs(gid, tgs);
		} catch (Exception e) {
			logger.error("deleteGChkunitComp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	};

	/**
	 * 根据一个台区的id和用户来找到某个考核单元的详细信息
	 * 
	 * @param user
	 * @param gid
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Map findGchkunitById(final PSysUser user, final Integer gid)
			throws ServiceException {
		try {
			List<Map> list = DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.getJdbcTemplate().query(
							sd.losePower_findChkunitById, new Object[] { gid },
							rm);
				}
			});
			if (!list.isEmpty()) {
				return list.get(0);
			}
			return new HashMap();
		} catch (Exception e) {
			logger.error("deleteGChkunitComp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 按照finder来查找相应的计量点
	 * 
	 * @param tmnlAddr
	 * @param finder
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findMeterMp(final Integer gid, final String type,
			final MeterMpFinder finder, final long start, final int limit)
			throws ServiceException {

		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					ResultParse re = SelectSqlCreator.getSelectSql(finder);
					if (re.getSql().trim().equals("")) {
						if(type.equals("01")){
							return (T) jb.pagingFind(findTableByType(
									sd.losePower_findMeterMp, type), start, limit,
									rm, new Object[] { gid, gid, gid,gid, gid });
						}
						return (T) jb.pagingFind(findTableByType(
								sd.losePower_findMeterMp, type), start, limit,
								rm, new Object[] { gid, gid, gid, gid });
					}
					LinkedList list = new LinkedList(re.getArgs());
					list.addFirst(gid);
					list.addFirst(gid);
					list.addFirst(gid);
					list.addFirst(gid);
					if(type.equals("01")){
						list.addFirst(gid);
					}
					return (T) jb.pagingFind(findTableByType(
							sd.losePower_findMeterMp, type)
							+ " where " + re.getSql(), start, limit, rm, list
							.toArray());
				}
			});

		} catch (Exception e) {
			logger.error("findMeterMp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 按照finder来查找相应的计量点的未分页版本
	 * 
	 * @param gid
	 * @param finder
	 *            一个findr用来找到所有的数据
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findMeterMp(final Integer gid, final String type,
			final MeterMpFinder finder) throws ServiceException {

		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					ResultParse re = SelectSqlCreator.getSelectSql(finder);
					if (re.getSql().trim().equals("")) {
						if (type.equals("01")) {
							return (T) jb.getJdbcTemplate().query(
									findTableByType(
											sd.losePower_findMeterMpSimple,
											type),
									new Object[] { gid, gid, gid, gid, gid },
									rm);
						}
						return (T) jb.getJdbcTemplate().query(
								findTableByType(sd.losePower_findMeterMpSimple,
										type),
								new Object[] { gid, gid, gid, gid }, rm);
					}
					LinkedList list = new LinkedList(re.getArgs());
					list.addFirst(gid);
					list.addFirst(gid);
					list.addFirst(gid);
					list.addFirst(gid);
					if (type.equals("01")) {
						list.addFirst(gid);
					}
					return (T) jb.getJdbcTemplate().query(
							findTableByType(sd.losePower_findMeterMpSimple,
									type)
									+ " where " + re.getSql(), list.toArray(),
							rm);
				}
			});

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	public void deleteGChkunit(PSysUser user, List<Integer> gids)
			throws ServiceException {
		try {
			losePowerManJdbcDao.deleteGChkunit(user, gids);
		} catch (Exception e) {
			logger.error(":deleteGChkunit:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	public void setLosePowerManJdbcDao(LosePowerManJdbcDao losePowerManJdbcDao) {
		this.losePowerManJdbcDao = losePowerManJdbcDao;
	}

	public void mergeGiomp(Integer gid, Integer isIn, final Integer isVaild,
			List<String> mpIds, List<String> meterIds, List<String> dataIds,
			List<String> pqFlags, List<String> objIds) throws ServiceException {
		try {
			losePowerManJdbcDao.mergeGiomp(gid, isIn, isVaild, mpIds, meterIds,
					dataIds, pqFlags, objIds);
		} catch (Exception e) {
			logger.error(":mergeGiomp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 *通过终端地址来找采集器资产号
	 * 
	 * @param tmnlAddr
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findFrm(final String tmnlAddr) throws ServiceException {
		try {
			List<Map> list = DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {

					return (T) jb.getJdbcTemplate().query(sd.losePower_findFrm,
							new Object[] { tmnlAddr }, rm);
				}
			});
			if (list.size() == 1 && null == list.get(0)) {
				return new ArrayList<Map>();
			}
			return list;
		} catch (Exception e) {
			logger.error(":mergeGiomp:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 通过一个考核单元的ip来找到这个考核单元中包含的所有的 台区的终端资产号
	 * 
	 * @param gid
	 * @return
	 * @throws ServiceException
	 */
	public List findTmnlAddr(Integer gid, String type) throws ServiceException {
		try {
			return losePowerManJdbcDao.findAddrInGChkunit(gid, type);
		} catch (Exception e) {
			logger.error(":findTmnlAddr:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	@Override
	public void deleteRef(Integer gid) throws ServiceException {
		try {
			losePowerManJdbcDao.deleteRef(gid);
		} catch (Exception e) {
			logger.error(":deleteRef:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	@Override
	public Map findRefByChkunit(Integer gid) throws ServiceException {
		try {
			return losePowerManJdbcDao.findRefByChkunit(gid);
		} catch (Exception e) {
			logger.error(":findRefByChkunit:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	@Override
	public void saveOrUpdateRef(GChkunitRefIdx gr) throws ServiceException {
		try {
			losePowerManJdbcDao.saveOrUpdateRef(gr);
		} catch (Exception e) {
			logger.error(":saveOrUpdateRef:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	@Override
	public Page<Map> checkExistsTgs(List<Integer> tgIds, long start, int limit)
			throws ServiceException {
		try {
			return losePowerManJdbcDao.checkExistsTgs(tgIds, start, limit);
		} catch (Exception e) {
			logger.error(":saveOrUpdateRef:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	@Override
	public void deleteRefByIdxIds(List<Integer> idxIds) throws ServiceException {
		try {
			losePowerManJdbcDao.deleteRefByIdxIds(idxIds);
		} catch (Exception e) {
			logger.error(":deleteRefByIdxIds:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	@Override
	public List<GChkunitRefIdx> findRefsByChkunitId(Integer chkunitId)
			throws ServiceException {
		try {
			return losePowerManJdbcDao.findRefsByChkunitId(chkunitId);
		} catch (Exception e) {
			logger.error(":deleteRefByIdxIds:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	@Override
	public void updateRefs(List<GChkunitRefIdx> refs) throws ServiceException {
		try {
			losePowerManJdbcDao.updateRefs(refs);
		} catch (Exception e) {
			logger.error(":deleteRefByIdxIds:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 通过左边树来查找线路,进行分页查询
	 * 
	 * @param user
	 * @param nodeStr
	 * @param start
	 * @param limit
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findLeftByLine(PSysUser user, String nodeStr,
			final long start, final int limit, LeftTreeLineFinder finder)
			throws ServiceException {
		try {
			final StringBuilder sb = new StringBuilder();
			if (null == nodeStr) {
				throw new RuntimeException("传入数据不能为空");
			}
			String arr[] = nodeStr.split("_");
			if (arr.length < 2) {
				throw new RuntimeException("传入的数据格式不正确");
			}
			// 类型
			String type = arr[0];
			// 值
			String value = arr[1];
			SqlData sdata = SqlData.getOne();
			// 参数列表
			final List list = new ArrayList();
			ResultParse re = SelectSqlCreator.getSelectSql(finder);
			if (type.equals("org")) {
				sb.append(sdata.losePower_treeFindLineInOrg);
				list.add(value + "%");
				if (!re.getSql().trim().equals("")) {
					sb.append(" where " + re.getSql());
					list.addAll(re.getArgs());
				}
			} else if (type.equals("sub")) {
				sb.append(sdata.losePower_treeFindLineInSubs);
				list.add(value);
				if (!re.getSql().trim().equals("")) {
					sb.append(" where " + re.getSql());
					list.addAll(re.getArgs());
				}
			} else if (type.equals("line")) {
				sb.append(sdata.losePower_treeFindLineInLine);
				list.add(value);
				if (!re.getSql().trim().equals("")) {
					sb.append(" where " + re.getSql());
					list.addAll(re.getArgs());
				}
			} else {
				throw new RuntimeException("不支持的节点");
			}

			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.pagingFind(sb.toString(), start, limit, rm,
							list.toArray());
				}
			});
		} catch (Exception e) {
			logger.error(":findLeftByLine:" + e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 查找一个考核单元中已经注册的过的线路
	 * 
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findExistLine(PSysUser user, final Integer gid,
			final long start, final int limit) throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.pagingFind(sd.losePower_existLine, start,
							limit, rm, new Object[] { gid });
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	/**
	 * 找到在注册的时候已经注册过的线路
	 * 
	 * @param ids
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findCheckLine(final List<Integer> ids, final long start,
			final int limit) throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.pagingFind(sd.losePower_checkExistLine
							+ AutoLang.autoIn("gl.line_id", ids, 500), start,
							limit, rm, ids.toArray());
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}

	}

	// 输入不同的type来选择连接不同的表，用于查找计量点时使用
	private String findTableByType(String input, String type) {
		if (type.equals("01")) {
			return input
					.replace("#table", "line_id")
					.replace(
							"#type",
							" and exists (select 1 from g_chkunit_comp gcc where  gcc.chkunit_id=? and gcc.obj_id=cm.line_id ) and c.cons_type in (1,2,4,7)");
		}
		if (type.equals("02")) {
			return input.replace("#table", "tg_id").replace("#type", "");
		}
		throw new RuntimeException("不支持的类型type:" + type);
	}

	/**
	 * 查找分压等级
	 * 
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findVoltType() throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {

				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					return (T) jb.getJdbcTemplate().query(
							sd.losePower_voltType, new Object[] {}, rm);
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}

	/**
	 * 统计全省的当天的电量 
	 * @param user
	 * @param statDate 统计的时间
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findDayPqStat(PSysUser user,final Date statDate,final long start,final int limit) throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {
				
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm, SqlData sd) {
					return (T) jb.pagingFind(sd.everyDate_pq, start, limit, rm, new Object[]{statDate});
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
		
	}
	/**
	 * 通过不同的类型查询电量在明细信息
	 * @param user
	 * @param type  统计类型
	 * @param orgNo  供电单位的边和
	 * @param statDate 统计时间
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findDayPqStatByType(PSysUser user,final String orgNo,final String type,final Date statDate,final long start,final int limit) throws ServiceException {
		try {
			return DaoUtils.run("[*,c]", new IDoDao() {
				
				@Override
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm, SqlData sd) {
					return (T) jb.pagingFind(sd.refDate_pq, start, limit, rm, new Object[]{orgNo,statDate,type});
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}
	@Override
	public boolean findMenuPower(PSysUser user, String url) throws ServiceException {
		try {
			return losePowerManJdbcDao.hasMenuPower(user, url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(e, e.getMessage());
		}
	}
		
	
	
}
