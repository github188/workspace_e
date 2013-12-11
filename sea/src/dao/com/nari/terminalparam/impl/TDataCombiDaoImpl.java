package com.nari.terminalparam.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.terminalparam.TDataCombi;
import com.nari.terminalparam.TDataCombiDao;
import com.nari.util.exception.DBAccessException;

public class TDataCombiDaoImpl extends HibernateBaseDaoImpl<TDataCombi, Long>
		implements TDataCombiDao {

	@Override
	public List<TDataCombi> findBy(String staffNo) throws DBAccessException {
		return findBy("staffNo", staffNo, MatchType.EQ);
	}

	/****
	 * 通过传进来的参数对按规则处理
	 * 
	 *格式：组合名称##clearNo##status##staffno 分割符号## 标示从数据库中得到的(不处理)<br>
	 * 1 db 忽略
	 * 2 del 标示会被删除<br>
	 * 3 edit 标示已经被标记过<br>
	 * 4 add 标示添加进来的树<br>
	 * 
	 * @param parseString
	 *            待处理数据，可以将它转化为实体bean 进行数据库操作<br>
	 *            *
	 **/
	public void parseEdit(List<String> parseString) throws DBAccessException {
		for (String str : parseString) {
			String[] strs = str.split("##");
			if (strs.length != 4) {
				logger.error("错误的数据格式");
				throw new DBAccessException("意外错误，错误的数据格式");
			}
			String combiName = strs[0];
			String clearNo = strs[1];
			String status = strs[2];
			String staffNo = strs[3];
			TDataCombi tdc = new TDataCombi();
			// 查找一个公共的信息出来，在添加的时候使用
			TDataCombi data = findOneBy(combiName, null, staffNo);
			if (null == data) {
				logger.warn("未发现组合"+combiName+",操作用户"+staffNo+"的数据,可能已经被删除");
				continue;
				//throw new DBAccessException("数据可能已经被删除");
			}
			if (status.equals("add")) {
				tdc.setClearProtNo(clearNo);
				tdc.setCombiName(combiName);
				tdc.setCreateDate(new Date());
				tdc.setIsShare(data.getIsShare());
				tdc.setStaffNo(staffNo);
				tdc.setValidDays(data.getValidDays());
				save(tdc);
				// tdc.setIsShare(isShare);
			} else if (status.equals("del")) {
				// 查到此数据在原先的表中的记录
				TDataCombi dataNow = findOneBy(combiName, clearNo, staffNo);
				if (null == dataNow) {
					logger.warn(combiName+"透明公约的"+clearNo+"的项已经被删除");
					//throw new DBAccessException("数据已经被删除");
					continue;
				}
				delete(dataNow);
			}
		}
	}

	/****
	 * 
	 * 通过名称，透明编码编号，操作人员的编号来查找一个组合 如果clearNo为null，忽略clear添加进行判断
	 * 
	 * @param name
	 *            组合 名称
	 * @param clearNo
	 *            透明公约编码
	 * @param staffNo
	 *            工作人员的编号
	 * @return 如果clearNo为null 按照name和staffNo查找的第一个项<br>
	 *         如果clearNo不为null 按照三个项一起查的第一个项
	 ***/
	public TDataCombi findOneBy(final String name, final String clearNo,
			final String staffNo) throws DBAccessException {
		return (TDataCombi) getHibernateTemplate().execute(
				new HibernateCallback() {

					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria c = session.createCriteria(TDataCombi.class);
						c.add(Restrictions.eq("combiName", name));
						if (null != clearNo) {
							c.add(Restrictions.eq("clearProtNo", clearNo));
						}
						c.add(Restrictions.eq("staffNo", staffNo));
						return c.uniqueResult();
					}
				});
	}
	/***
	 * 按照工作人员的编号来查找召测组合
	 * @param staffNo 操作人员编号
	 * @return 操作人员所对应的召测组合
	 * ****/
	//public void 
	@SuppressWarnings("unchecked")
	public List<TDataCombi> findByStaffNo(final String staffNo) throws DBAccessException{
	return	getHibernateTemplate().executeFind(new HibernateCallback() {
		
		@Override
		public Object doInHibernate(Session s) throws HibernateException,
				SQLException {
			Criteria c=s.createCriteria(TDataCombi.class);
			//c.add(Restrictions.eq("staffNo", staffNo));
			c.add(Restrictions.disjunction().add(Restrictions.eq("staffNo", staffNo)));
			c.addOrder(Order.asc("combiName")).addOrder(Order.asc("clearProtNo"));
			return c.list();
		}
	});
	}
}
