package test.com.nari.pojo;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Test
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see test.com.nari.pojo.Test
 * @author MyEclipse Persistence Tools
 */

public class TestDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TestDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	protected void initDao() {
		// do nothing
	}

	public void save(Test transientInstance) {
		log.debug("saving Test instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Test persistentInstance) {
		log.debug("deleting Test instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Test findById(java.lang.String id) {
		log.debug("getting Test instance with id: " + id);
		try {
			Test instance = (Test) getHibernateTemplate().get(
					"test.com.nari.pojo.Test", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Test instance) {
		log.debug("finding Test instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Test instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Test as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findAll() {
		log.debug("finding all Test instances");
		try {
			String queryString = "from Test";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Test merge(Test detachedInstance) {
		log.debug("merging Test instance");
		try {
			Test result = (Test) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Test instance) {
		log.debug("attaching dirty Test instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Test instance) {
		log.debug("attaching clean Test instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TestDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TestDAO) ctx.getBean("TestDAO");
	}
}