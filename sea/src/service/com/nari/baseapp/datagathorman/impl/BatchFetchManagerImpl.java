package com.nari.baseapp.datagathorman.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.TbgTask;
import com.nari.baseapp.datagatherman.TbgTaskDetail;
import com.nari.baseapp.datagatherman.TbgTaskFinder;
import com.nari.baseapp.datagatherman.TbgTaskStatisticsFinder;
import com.nari.baseapp.datagathorman.IBatchFetchJdbcDao;
import com.nari.baseapp.datagathorman.IBatchFetchManager;
import com.nari.baseapp.datagathorman.IDataFetchJdbcDao;
import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.dutylog.IGenerateKey;
import com.nari.support.Page;
import com.nari.util.CreateInsert;
import com.nari.util.ResultParse;
import com.nari.util.SelectSqlCreator;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/****
 * 批量巡测
 * 
 * 使用SqlData操作数据库
 * 
 * @author huangxuan *
 *****/
public class BatchFetchManagerImpl implements IBatchFetchManager {
	protected Logger logger = Logger.getLogger(this.getClass());
	private IDataFetchJdbcDao idataFetchJdbcDao;
	private IBatchFetchJdbcDao ibatchFetchJdbcDao;

	public void setIdataFetchJdbcDao(IDataFetchJdbcDao idataFetchJdbcDao) {
		this.idataFetchJdbcDao = idataFetchJdbcDao;
	}

	public void setIbatchFetchJdbcDao(IBatchFetchJdbcDao ibatchFetchJdbcDao) {
		this.ibatchFetchJdbcDao = ibatchFetchJdbcDao;
	}

	/***
	 * 得到一个用户的所有的召测组合
	 * 
	 * @param user
	 *            登陆用户
	 * @param type
	 *            召测组合的类别
	 * @return 返回所有的召测组合 *
	 ***/
	@SuppressWarnings("unchecked")
	public List<Map> findAllCombi(PSysUser user, String type) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		// 结果为骆驼命名法
		MapResultMapper mrp = new MapResultMapper("[*,c]");
		SqlData sd = SqlData.getOne();
		return tree.getJdbcTemplate().query(sd.batchFetch_findCombi,
				new Object[] { user.getStaffNo(), type }, mrp);
	}

	/***
	 * 得到一个召测组合的所有的小项
	 * 
	 * @param user
	 *            登陆用户
	 * @param combiId
	 *            召测组合的id
	 * @param 返回一个召测组合所含有的召测组合项
	 *            *
	 ****/
	@SuppressWarnings("unchecked")
	public List<Map> findComItem(PSysUser user, String combiId) {
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		MapResultMapper mrp = new MapResultMapper("[*,c]");
		SqlData sd = SqlData.getOne();
		return tree.getJdbcTemplate().query(sd.batchFetch_findCombi_item,
				new Object[] { user.getStaffNo(), combiId }, mrp);
	}

	/****
	 * 通过必要的数据来更新两个表t_bg_task和t_bg_task_detail
	 * 
	 * @param tt
	 *            后台任务主表
	 * @param ttds
	 *            后台任务从表
	 * @return *
	 ***/
	public void addBgTask(List<TbgTask> tts, List<TbgTaskDetail> ttds) {
		// 处理pn
		tts = buildList(tts);
		// 对两个列表进行处理，使4开头的规约能自动的变成pn为o
		for (TbgTask tt : tts) {
			for (TbgTaskDetail td : ttds) {
				if (td.getDataItemNo().startsWith("4")) {
					tt.setObjType(0);
				}
			}
		}
		boolean flat = false;
		CreateInsert c = new CreateInsert(true);
		for (TbgTask tt : tts) {
			// true表示save完成后初始化id
			c.save(tt);
			final Object id = c.getId();
			if (flat == false) {
				c.setGenerateKey(new IGenerateKey() {
					@SuppressWarnings("unchecked")
					@Override
					public String createKey(Field f, List args) {
						args.add(id);
						return "?";
					}
				});
				c.setInitId(false);
				flat = true;
			}
			// 禁用初始化id

			for (TbgTaskDetail td : ttds) {
				c.save(td);
			}
		}
	}

	/**** 通过一个组合id，来得到组合的名称 ***/
	public String findCombiNameById(PSysUser user, String combiId) {
		return idataFetchJdbcDao.findNameById(combiId);
	}

	/***
	 * 通过一个组合的id来得到所有的召测组合的透明公约，并且进行找到这些项 中的所有的的大项编码
	 * 
	 * @param combiId
	 *            组合项的id
	 * @return 去掉了重复的大项的透明编码
	 * **/
	public List<String> findAndGetFather(PSysUser user, String combiId)
			throws ServiceException {
		try {
			HashSet<String> hs = new HashSet<String>();
			List<String> codes = idataFetchJdbcDao.findItemsById(user, combiId);
			for (String str : codes) {
				String f = idataFetchJdbcDao.getFather(str);
				if (null == f) {
					f = str;
				}
				if (!hs.contains(f)) {
					hs.add(f);
				}
			}
			return new ArrayList<String>(hs);
		} catch (DBAccessException e) {
			throw new ServiceException(e.message);
		}
	}

	/*******
	 * 查询后台任务主表中的数据
	 * 
	 * @param user
	 *            登陆用户
	 * @param start
	 *            开始
	 * @param 限制条数
	 * @return 以骆驼命名法返回结果 *
	 ******/
	@SuppressWarnings("unchecked")
	public Page<Map> findTbgTask(PSysUser user, long start, int limit,
			TbgTaskFinder finder) throws ServiceException {
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			MapResultMapper mrp = new MapResultMapper("[*,c]");
			SqlData sd = SqlData.getOne();
			ResultParse re = SelectSqlCreator.getSelectSql(finder);
			re.getArgs().add(user.getStaffNo());
			return tree.pagingFind(
					String.format(sd.batchFetch_findTask, re.getSql().trim()
							.equals("") ? "" : (re.getSql() + " and ")), start,
					limit, mrp, re.getArgs().toArray());
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}

	/*****
	 * 查询后台任务的结果
	 * 
	 * @param user
	 *            登陆用户 以后备用
	 * @param taskId
	 *            任务id
	 * @return 查找后来的结果
	 * @throws ServiceException
	 *             *
	 *****/
	@SuppressWarnings("unchecked")
	public List<Map> findTbgTaskResult(PSysUser user, String taskId)
			throws ServiceException {
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			MapResultMapper mrp = new MapResultMapper("[*,c]");
			SqlData sd = SqlData.getOne();
			return tree.getJdbcTemplate().query(sd.batchFetch_taskResult,
					new Object[] { taskId }, mrp);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/*** 一个方法对TbgTask列表进行处理，对pn超过10个的情况重新处理 ***/
	private List<TbgTask> buildList(List<TbgTask> tts) {
		List<TbgTask> result = new ArrayList<TbgTask>();
		for (TbgTask t : tts) {
			int flat = 0;
			String pns = t.getObjList();
			if (t.getObjType().equals(0)) {
				result.add(t);
				continue;
			}
			List<String> pn = dealPn(pns);
			for (String str : pn) {
				TbgTask tb = cloneBean(t);
				result.add(tb);
			}
		}
		return result;
	}

	/***** 对一个pns列表进行处理，如果pn数超过十个，列表中的值个数会大于一 *****/
	public List<String> dealPn(String pn) {
		int flat = 0;
		int lastIndex = -1;
		StringBuilder sb = new StringBuilder();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < pn.length(); i++) {
			if (pn.charAt(i) == '1') {
				flat++;
			}
			if (flat == 10 || i == pn.length() - 1) {
				flat = 0;
				String v = pn.substring(lastIndex + 1, i + 1);
				lastIndex = i;
				result.add(createZero(result.size()) + v);
			}
		}
		return result;
	}

	/**** 生成len长度的0 *****/
	private String createZero(int len) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < len) {
			sb.append("0000000000");
			i++;
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		BatchFetchManagerImpl bi = new BatchFetchManagerImpl();
		System.out.println("1234".substring(1, 3));
		System.out.println(bi.dealPn("111111110111111011"));
		StringBuilder sb = new StringBuilder();
		System.out.println(sb.toString());
	}

	@SuppressWarnings("unchecked")
	private <T> T cloneBean(Object obj) {
		try {
			return (T) BeanUtils.cloneBean(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/****
	 * 任务的启用
	 * 
	 * @param taskId
	 *            任务的id
	 * @throws ServiceException
	 *             *
	 ***/
	public void enableTask(String taskId) throws ServiceException {
		try {
			ibatchFetchJdbcDao.enableTask(taskId);

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	/***
	 * 
	 * 任务的禁用
	 * 
	 * @param taskId
	 *            任务的id
	 * @throws ServiceException
	 * **/
	public void disableTask(String taskId) throws ServiceException {
		try {
			ibatchFetchJdbcDao.disableTask(taskId);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * 查找 后台任务的统计相关的信息
	 * @param user
	 * @param start
	 * @param limit
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Page<Map> findStatistics(PSysUser user,final long start,final int limit,TbgTaskStatisticsFinder finder) throws ServiceException {
		try {
		return	DaoUtils.run("[*,c]", new IDoDao() {
			public <T> T execute(JdbcBaseDAOImpl jd, RowMapper rm, SqlData sd) {
				return (T) jd.pagingFind(sd.batchFetch_statistics, start, limit, rm);
			}
		});
		} catch (Exception e) {
			throw new ServiceException(e,e.getMessage());
		}
	}

}
