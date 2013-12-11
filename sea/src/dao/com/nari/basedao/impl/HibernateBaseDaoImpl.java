package com.nari.basedao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

/**
 * 封装扩展通用的公共的DAO层方法泛型基类。<br>
 * 扩展功能包括分页查询，按属性过滤条件列表查询。<br>
 * 可在 Service 层直接使用，也可以扩展泛型 DAO 子类使用，见两个构造函数的注释。
 * @param <T> DAO 操作的对象类型
 * @param <PK> 主键类型
 * @author 鲁兆淞
 */
public class HibernateBaseDaoImpl<T, PK extends Serializable> extends HibernateBaseDaoSimpleImpl<T, PK> {

	/******************** 构造方法 ********************/

	/**
	 * 用于 Dao 层子类使用的构造函数。<br>
	 * 通过子类的泛型定义取得对象类型。<br>
	 * 例：public class UserDao extends SimpleHibernateDao<User, Long>
	 */
	public HibernateBaseDaoImpl() {
		super();
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
	public HibernateBaseDaoImpl(final SessionFactory sessionFactory, final Class<T> entityClass) {
		super(sessionFactory, entityClass);
	}

	/******************** 公共方法 ********************/

	/**
	 * 新增对象。
	 * @param entity 对象
	 * @throws DBAccessException 数据库异常
	 */
	public void save(final T entity) throws DBAccessException {
		if(null == entity) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.save(entity);
				return null;
			}
		});

	}

	/**
	 * 新增对象列表。
	 * @param entityList 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	public void save(final List<T> entityList) throws DBAccessException {
		if(null == entityList) {
			this.logger.error("对象列表不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				for(T entity : entityList) {
					session.save(entity);
				}
				return null;
			}
		});

	}

	/**
	 * 修改对象。
	 * @param entity 对象
	 * @throws DBAccessException 数据库异常
	 */
	public void update(final T entity) throws DBAccessException {
		if(null == entity) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.update(entity);
				return null;
			}
		});

	}

	/**
	 * 新增或修改对象。
	 * @param entity 对象
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdate(final T entity) throws DBAccessException {
		if(null == entity) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.saveOrUpdate(entity);
				return null;
			}
		});

	}

	/**
	 * 删除对象。<br>
	 * 对象必须是 session 中的对象或含主键的瞬时对象。
	 * @param entity 对象
	 * @throws DBAccessException 数据库异常
	 */
	public void delete(final T entity) throws DBAccessException {
		if(null == entity) {
			this.logger.error("对象不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.delete(entity);
				return null;
			}
		});

	}

	/**
	 * 按主键删除对象。
	 * @param id 主键
	 * @throws DBAccessException 数据库异常
	 */
	public void delete(final PK id) throws DBAccessException {
		if(null == id) {
			this.logger.error("主键不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		this.delete(this.findById(id));
	}

	/**
	 * 按主键查询对象。
	 * @param id 主键
	 * @return 对象
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public T findById(final PK id) throws DBAccessException {
		if(null == id) {
			this.logger.error("主键不能为 NULL");
			throw new DBAccessException(ExceptionConstants.DBE_PARAMETERISNULL);
		}

		return (T)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				T result = (T)session.load(HibernateBaseDaoImpl.this.entityClass, id);
				HibernateBaseDaoImpl.this.initEntity(result);
				return result;
			}
		});
	}

	/**
	 * 查询全部对象列表。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws DBAccessException {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session);
				return criteria.list();
			}
		});
	}

	/**
	 * 查询全部对象列表，带排序。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String orderBy, String order) throws DBAccessException {
		final List<Order> orderList = HibernateBaseDaoImpl.this.setOrderParameter(orderBy, order);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件查找对象列表，匹配方式为相等。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value) throws DBAccessException {
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, MatchType.EQ);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件查找对象列表，匹配方式为相等，带排序。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value, String orderBy, String order) throws DBAccessException {
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, MatchType.EQ);
		final List<Order> orderList = HibernateBaseDaoImpl.this.setOrderParameter(orderBy, order);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件查找对象列表，支持多种匹配方式。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value, MatchType matchType) throws DBAccessException {
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, matchType);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件查找对象列表，支持多种匹配方式，带排序。
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(String propertyName, Object value, MatchType matchType, String orderBy, String order) throws DBAccessException {
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, matchType);
		final List<Order> orderList = HibernateBaseDaoImpl.this.setOrderParameter(orderBy, order);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件列表查找对象列表。
	 * @param filters 过滤条件列表
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(List<PropertyFilter> filters) throws DBAccessException {
		final Criterion[] criterions = this.buildPropertyFilterCriterions(filters);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterions);
				return criteria.list();
			}
		});
	}

	/**
	 * 按属性过滤条件列表查找对象列表，带排序。
	 * @param filters 过滤条件列表
	 * @param orderBy 排序字段，用逗号分割，数量必须和排序方向一致。
	 * @param order 排序方向，为 "asc" 或 "desc"，用逗号分割，数量必须和排序字段一致。
	 * @return 对象列表
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public List<T> findBy(List<PropertyFilter> filters, String orderBy, String order) throws DBAccessException {
		final Criterion[] criterions = this.buildPropertyFilterCriterions(filters);
		final List<Order> orderList = HibernateBaseDaoImpl.this.setOrderParameter(orderBy, order);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterions);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				return criteria.list();
			}
		});
	}

	/**
	 * 分页查询全部对象。
	 * @param page 分页参数
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page) throws DBAccessException {
		List<Order> orderListTemp = new ArrayList<Order>();
		if(page.isOrderBySetted()) {
			orderListTemp = HibernateBaseDaoImpl.this.setOrderParameter(page.getOrderBy(), page.getOrder());
		}
		final List<Order> orderList = orderListTemp;

		return (Page<T>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Page<T> resultPage = page;
				Criteria criteria = session.createCriteria(HibernateBaseDaoImpl.this.entityClass);
				resultPage.setTotalCount((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult());

				criteria = HibernateBaseDaoImpl.this.createCriteria(session, page);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				resultPage.setResult(criteria.list());
				return resultPage;
			}
		});
	}

	/**
	 * 按属性过滤条件分页查找对象列表，匹配方式为相等。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String propertyName, final Object value) throws DBAccessException {
		List<Order> orderListTemp = new ArrayList<Order>();
		if(page.isOrderBySetted()) {
			orderListTemp = HibernateBaseDaoImpl.this.setOrderParameter(page.getOrderBy(), page.getOrder());
		}
		final List<Order> orderList = orderListTemp;
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, MatchType.EQ);

		return (Page<T>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Page<T> resultPage = page;
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				resultPage.setTotalCount((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult());

				criteria = HibernateBaseDaoImpl.this.createCriteria(session, page, criterion);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				resultPage.setResult(criteria.list());
				return resultPage;
			}
		});
	}

	/**
	 * 按属性过滤条件分页查找对象列表，支持多种匹配方式。
	 * @param page 分页参数
	 * @param propertyName 属性名称
	 * @param value 比较值
	 * @param matchType 匹配方式，见 PropertyFilter 类。
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final String propertyName, final Object value, final MatchType matchType) throws DBAccessException {
		List<Order> orderListTemp = new ArrayList<Order>();
		if(page.isOrderBySetted()) {
			orderListTemp = HibernateBaseDaoImpl.this.setOrderParameter(page.getOrderBy(), page.getOrder());
		}
		final List<Order> orderList = orderListTemp;
		final Criterion criterion = this.buildPropertyFilterCriterion(propertyName, value, matchType);

		return (Page<T>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Page<T> resultPage = page;
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterion);
				resultPage.setTotalCount((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult());

				criteria = HibernateBaseDaoImpl.this.createCriteria(session, page, criterion);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				resultPage.setResult(criteria.list());
				return resultPage;
			}
		});
	}

	/**
	 * 按属性过滤条件列表分页查找对象列表。
	 * @param page 分页参数
	 * @param filters 过滤条件列表
	 * @return 分页查询结果
	 * @throws DBAccessException 数据库异常
	 */
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) throws DBAccessException {
		List<Order> orderListTemp = new ArrayList<Order>();
		if(page.isOrderBySetted()) {
			orderListTemp = HibernateBaseDaoImpl.this.setOrderParameter(page.getOrderBy(), page.getOrder());
		}
		final List<Order> orderList = orderListTemp;
		final Criterion[] criterions = this.buildPropertyFilterCriterions(filters);

		return (Page<T>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Page<T> resultPage = page;
				Criteria criteria = HibernateBaseDaoImpl.this.createCriteria(session, criterions);
				resultPage.setTotalCount((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult());

				criteria = HibernateBaseDaoImpl.this.createCriteria(session, page, criterions);
				for(Order o : orderList) {
					criteria.addOrder(o);
				}
				resultPage.setResult(criteria.list());
				return resultPage;
			}
		});
	}

}
