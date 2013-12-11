package com.nari.basedao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.ReflectionUtils;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

/**
 * 封装供DAO层子类调用的方法，作为扩展的泛型基类。<br>
 * 由于参数涉及到 Hibernate API，不允许外部直接使用。
 * @param <T> DAO 操作的对象类型
 * @param <PK> 主键类型
 * @author 鲁兆淞
 */
public class HibernateBaseDaoSimpleImpl<T, PK extends Serializable> extends HibernateDaoSupport {
	protected Logger logger = Logger.getLogger(this.getClass());
	protected Class<T> entityClass;

	/******************** 构造方法 ********************/

	/**
	 * 用于 Dao 层子类使用的构造函数。<br>
	 * 通过子类的泛型定义取得对象类型。<br>
	 * 例：public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public HibernateBaseDaoSimpleImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}

	/**
	 * 用于用于省略 Dao 层，在 Service 层直接使用通用 SimpleHibernateDao 的构造函数。<br>
	 * 在构造函数中定义对象类型。<br>
	 * 不推荐使用。<br>
	 * 例：SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory, User.class);
	 * @param sessionFactory sessionFactory 实例
	 * @param entityClass 对象类型
	 */
	@Deprecated
	public HibernateBaseDaoSimpleImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		this.setSessionFactory(sessionFactory);
		this.entityClass = entityClass;
	}

	/******************** 内部方法 ********************/

	/**
	 * 设置排序参数到 Order 对象列表。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return Order 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	protected List<Order> setOrderParameter(String orderBy, String order) throws DBAccessException {
		if(StringUtils.isEmpty(orderBy) || StringUtils.isEmpty(order)) {
			this.logger.error("排序参数不能为空");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		order = StringUtils.lowerCase(order);
		String[] orders = StringUtils.split(order, ',');
		for(String orderStr : orders) {
			if(!StringUtils.equals(Page.DESC, orderStr.trim()) && !StringUtils.equals(Page.ASC, orderStr.trim())) {
				this.logger.error("排序方向格式错误");
				throw new DBAccessException(ExceptionConstants.DBE_PARAMETERERROR);
			}
		}

		String[] orderByArray = StringUtils.split(orderBy, ',');
		String[] orderArray = StringUtils.split(order, ',');
		if(orderByArray.length != orderArray.length) {
			this.logger.error("排序字段与排序方向的个数不相等");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERERROR);
		}

		List<Order> criterionList = new ArrayList<Order>();
		for(int i = 0; i < orderByArray.length; i++) {
			if(Page.ASC.equals(orderArray[i].trim())) {
				criterionList.add(Order.asc(orderByArray[i].trim()));
			}else {
				criterionList.add(Order.desc(orderByArray[i].trim()));
			}
		}
		return criterionList;
	}

	/**
	 * 按属性过滤条件创建 Criterion。
	 * @param propertyName 属性名称
	 * @param propertyValue 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return Criterion 对象
	 * @throws DBAccessException 数据库异常
	 */
	protected Criterion buildPropertyFilterCriterion(String propertyName, Object propertyValue, MatchType matchType) throws DBAccessException {
		if(StringUtils.isEmpty(propertyName)) {
			this.logger.error("参数不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}
		Criterion criterion = null;
		try {
			if(MatchType.EQ.equals(matchType)) { // 等于
				criterion = Restrictions.eq(propertyName, propertyValue);
			} else if(MatchType.NEQ.equals(matchType)) { // 不等于
				criterion = Restrictions.or(Restrictions.not(Restrictions.eq(propertyName, propertyValue)), Restrictions.isNull(propertyName));
			} else if(MatchType.LIKE.equals(matchType)) { // 字符串模式匹配
				criterion = Restrictions.like(propertyName, (String)propertyValue, MatchMode.EXACT);
			} else if(MatchType.NLIKE.equals(matchType)) { // 非匹配
				criterion = Restrictions.not(Restrictions.like(propertyName, (String)propertyValue, MatchMode.EXACT));
			} else if(MatchType.LE.equals(matchType)) { // 小于等于
				criterion = Restrictions.le(propertyName, propertyValue);
			} else if(MatchType.LT.equals(matchType)) { // 小于
				criterion = Restrictions.lt(propertyName, propertyValue);
			} else if(MatchType.GE.equals(matchType)) { // 大于等于
				criterion = Restrictions.ge(propertyName, propertyValue);
			} else if(MatchType.GT.equals(matchType)) { // 大于
				criterion = Restrictions.gt(propertyName, propertyValue);
			} else {
				throw new Exception();
			}
		} catch(Exception e) {
			e.printStackTrace();
			this.logger.error("未知异常");
			throw new DBAccessException(ExceptionConstants.DBE_OTHERS);
		}
		return criterion;
	}

	/**
	 * 按属性过滤条件列表创建 Criterion 数组。
	 * @param filters 过滤条件列表
	 * @return Criterion 数组
	 * @throws DBAccessException 数据库异常
	 */
	protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) throws DBAccessException {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		for(PropertyFilter filter : filters) {
			Criterion criterion = this.buildPropertyFilterCriterion(filter.getPropertyNames(), filter.getPropertyValue(), filter.getMatchType());
			criterionList.add(criterion);
		}
		return criterionList.toArray(new Criterion[criterionList.size()]);
	}

	/**
	 * 根据 Criterion 条件创建 Criteria 对象。
	 * @param session Session 对象。
	 * @param criterions 数量可变的 Criterion 条件。
	 * @return Criteria 对象。
	 */
	protected Criteria createCriteria(Session session, Criterion... criterions) {
		Criteria criteria = session.createCriteria(this.entityClass);
		for(Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 根据 Criterion 条件及 Page 分页参数创建 Criteria 对象。
	 * @param session Session 对象。
	 * @param page 分页参数。
	 * @param criterions 数量可变的 Criterion 条件。
	 * @return Criteria 对象。
	 */
	protected Criteria createCriteria(Session session, Page<T> page, Criterion... criterions) {
		Criteria criteria = this.createCriteria(session, criterions);
		criteria.setFirstResult((int) page.getStartNo());
		criteria.setMaxResults(page.getPageSize());
		return criteria;
	}



	/**
	 * 按 HQL 语句查询对象。数量可变的参数。
	 * @param hql HQL 语句
	 * @param values 数量可变的参数按顺序绑定
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> find(final String hql, final Object... values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		List<X> resultList = query.list();
		this.releaseSession(session); // 关闭 Session
		return resultList;
	}

	/**
	 * 按 HQL 语句查询对象。Map 参数。
	 * @param hql HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return 对象列表
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> find(final String hql, final Map<String, Object> values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		List<X> resultList = query.list();
		this.releaseSession(session); // 关闭 Session
		return resultList;
	}

	/**
	 * 按 HQL 语句查询唯一对象。数量可变的参数。
	 * @param hql HQL 语句
	 * @param values 数量可变的参数按顺序绑定
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	protected <X> X findUnique(final String hql, final Object... values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		X result = (X)query.uniqueResult();
		this.releaseSession(session); // 关闭 Session
		return result;
	}

	/**
	 * 按 HQL 语句查询唯一对象。Map 参数。
	 * @param hql HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	protected <X> X findUnique(final String hql, final Map<String, Object> values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		X result = (X)query.uniqueResult();
		this.releaseSession(session); // 关闭 Session
		return result;
	}

	/**
	 * 按 HQL 语句批量修改或删除。数量可变的参数。
	 * @param hql HQL 语句
	 * @param values 数量可变的参数按顺序绑定
	 * @return 影响记录数
	 */
	protected int batchExecute(final String hql, final Object... values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		int result = query.executeUpdate();
		this.releaseSession(session); // 关闭 Session
		return result;
	}

	/**
	 * 按 HQL 语句批量修改或删除。Map 参数。
	 * @param hql HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return 影响记录数
	 */
	protected int batchExecute(final String hql, final Map<String, Object> values) {
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		int result = query.executeUpdate();
		this.releaseSession(session); // 关闭 Session
		return result;
	}

	/**
	 * 按 HQL 语句创建 Query 对象。数量可变的参数。<br>
	 * @param queryString HQL 语句
	 * @param values 数量可变的参数按顺序绑定
	 * @return Query 和当前 Session 对象集合，使用完后关闭 Session。
	 */
	protected List<Object> createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Session session = this.getSession();
		Query query = session.createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(query);
		resultList.add(session);
		return resultList;
	}

	/**
	 * 按 HQL 语句创建 Query 对象。Map 参数。
	 * @param queryString HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return Query 和当前 Session 对象集合，使用完后关闭 Session。
	 */
	protected List<Object> createQuery(final String queryString, final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Session session = this.getSession();
		Query query = session.createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		List<Object> resultList = new ArrayList<Object>();
		resultList.add(query);
		resultList.add(session);
		return resultList;
	}

	/**
	 * 为 Query 对象添加 distinct transformer。
	 * @param query Query 对象
	 * @return Query 对象
	 */
	protected Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 按 HQL 分页查询。数量可变的查询参数，按顺序绑定。
	 * @param page 分页参数，不支持其中的 orderBy 参数。
	 * @param hql HQL 语句
	 * @param values 数量可变的查询参数，按顺序绑定。
	 * @return 分页查询结果。
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	protected Page<T> findPage(final Page<T> page, final String hql, final Object... values) throws DBAccessException {
		Assert.notNull(page, "page不能为空");
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		if(page.isAutoCount()) {
			long totalCount = this.countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		this.setPageParameter(query, page);
		page.setResult(query.list());
		this.releaseSession(session); // 关闭 Session
		return page;
	}

	/**
	 * 按 HQL 分页查询。命名参数，按名称绑定。
	 * @param page 分页参数，不支持其中的 orderBy 参数。
	 * @param hql HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return 分页查询结果。
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	protected Page<T> findPage(final Page<T> page, final String hql, final Map<String, Object> values) throws DBAccessException {
		Assert.notNull(page, "page不能为空");
		List<Object> oList = this.createQuery(hql, values);
		Query query = (Query)oList.get(0);
		Session session = (Session)oList.get(1);

		if(page.isAutoCount()) {
			long totalCount = this.countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		this.setPageParameter(query, page);
		page.setResult(query.list());
		this.releaseSession(session); // 关闭 Session
		return page;
	}

	/**
	 * 设置分页参数到 Query 对象。
	 * @param q Query 对象
	 * @param page 分页参数
	 * @return 设置后的 Query 对象
	 */
	protected Query setPageParameter(final Query q, final Page<T> page) {
		q.setFirstResult((int)page.getStartNo());
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 执行 count 查询获得本次 HQL 查询所能获得的对象总数。
	 * @param hql HQL 语句
	 * @param values 数量可变的查询参数，按顺序绑定。
	 * @return 本次 HQL 查询获得的对象总数
	 * @throws DBAccessException 数据库异常
	 */
	protected long countHqlResult(final String hql, final Object... values) throws DBAccessException {
		Long count = 0L;
		String fromHql = hql;

		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");

		String countHql = "select count(*) " + fromHql;

		try {
			count = this.findUnique(countHql, values);
		} catch(Exception e) {
			e.printStackTrace();
			this.logger.error("发生未知异常");
			throw new DBAccessException(ExceptionConstants.DBE_OTHERS); // 抛出未知异常
		}
		return count;
	}

	/**
	 * 执行 count 查询获得本次 HQL 查询所能获得的对象总数。
	 * @param hql HQL 语句
	 * @param values 命名参数，按名称绑定。
	 * @return 本次 HQL 查询获得的对象总数
	 * @throws DBAccessException 数据库异常
	 */
	protected long countHqlResult(final String hql, final Map<String, Object> values) throws DBAccessException {
		Long count = 0L;
		String fromHql = hql;

		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");

		String countHql = "select count(*) " + fromHql;

		try {
			count = this.findUnique(countHql, values);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error("发生未知异常");
			throw new DBAccessException(ExceptionConstants.DBE_OTHERS); // 抛出未知异常
		}

		return count;
	}

	/**
	 * 强制加载对象。<br>
	 * 使用 load() 方法得到的仅是对象代理，即VO。<br>
	 * 该方法只初始化对象的直接属性，但不会初始化延迟加载的关联集合和属性。<br>
	 * 如需初始化关联属性，执行：<br>
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合。
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的 Description 属性。
	 * @param entity 对象
	 */
	protected void initEntity(T entity) {
		Hibernate.initialize(entity);
	}

	/**
	 * 强制加载对象列表。<br>
	 * 使用 load() 方法得到的仅是对象代理，即VO。<br>
	 * 该方法只初始化对象的直接属性，但不会初始化延迟加载的关联集合和属性。<br>
	 * 如需初始化关联属性，执行：<br>
	 * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合。
	 * Hibernate.initialize(user.getDescription())，初始化User的直接属性和延迟加载的 Description 属性。
	 * @param entityList 对象列表
	 */
	protected void initEntity(List<T> entityList) {
		for(T entity : entityList) {
			Hibernate.initialize(entity);
		}
	}

}