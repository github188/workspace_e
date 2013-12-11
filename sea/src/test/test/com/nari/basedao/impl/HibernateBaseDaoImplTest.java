package test.com.nari.basedao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;

import test.com.nari.test.SpringJunitTest;

import com.nari.basedao.impl.HibernateBaseDaoImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.support.PropertyFilter;
import com.nari.support.PropertyFilter.MatchType;
import com.nari.sysman.templateman.TaskModelManager;
import com.nari.sysman.templateman.VwTaskProperty;
import com.nari.sysman.templateman.impl.TaskModelManagerImpl;
import com.nari.terminalparam.TTaskTemplate;
import com.nari.util.exception.DBAccessException;

public class HibernateBaseDaoImplTest extends SpringJunitTest {

	public void test1() throws DBAccessException {
		PSysUserDaoImpl pSysUserDaoImpl = new PSysUserDaoImpl();

		PSysUser pSysUser = new PSysUser();
		pSysUser.setStaffNo("999");
		pSysUser.setEmpNo("999");
		pSysUser.setDeptNo("1");
		pSysUser.setName("鲁兆淞");
		pSysUserDaoImpl.save(pSysUser);

		pSysUser.setName("鲁兆淞111");
		pSysUserDaoImpl.update(pSysUser);

		pSysUserDaoImpl.delete("999");

		Page<PSysUser> page = new Page<PSysUser>(0, 999);

		List<PropertyFilter> propertyFilterList = new ArrayList<PropertyFilter>();
		PropertyFilter propertyFilter1 = new PropertyFilter("curStatusCode", "14", MatchType.NEQ);
		PropertyFilter propertyFilter2 = new PropertyFilter("curStatusCode", "11", MatchType.NEQ);
		propertyFilterList.add(propertyFilter1);
		propertyFilterList.add(propertyFilter2);

		page = pSysUserDaoImpl.findPage(page, propertyFilterList);
		for(PSysUser p : page.getResult()) {
			System.out.println(p.getEmpNo() + " | " + p.getName());
		}
		System.out.println("总数：" + page.getTotalCount());
	}

	public void test2() throws DBAccessException {
		VwTaskPropertyDaoImpl vwTaskPropertyDaoImpl = new VwTaskPropertyDaoImpl();
		List<VwTaskProperty> vwTaskPropertyList = vwTaskPropertyDaoImpl.findAll();
		for(VwTaskProperty v : vwTaskPropertyList) {
			System.out.println(v.getTaskProperty() + " | " + v.getTaskPropertyName());
		}
	}

	@Test
	public void test3() throws DBAccessException {
		//		TTaskTemplateDao tTaskTemplateDao = (com.nari.terminalparam.impl.TTaskTemplateDaoImpl)HibernateBaseDaoImplTest.this.applicationContext.getBean("tTaskTemplateDao");
		//		List<TTaskTemplate> ttTaskTemplateList = tTaskTemplateDao.findAll("templateName", "asc");
		//		System.out.println(ttTaskTemplateList.size());
		TaskModelManager taskModelManager = (TaskModelManagerImpl)HibernateBaseDaoImplTest.this.applicationContext.getBean("taskModelManager");
//		TTaskTemplate tTaskTemplate = taskModelManager.getTaskTemplateById(Long.parseLong("100002"));
//		System.out.println(tTaskTemplate.getTemplateName());
	}

	private class PSysUserDaoImpl extends HibernateBaseDaoImpl<PSysUser, String> {
		private SessionFactory sessionFactory = (SessionFactory)HibernateBaseDaoImplTest.this.applicationContext.getBean("sessionFactory");

		private PSysUserDaoImpl() {
			super();
			this.setSessionFactory(this.sessionFactory);
		}

	}

	private class VwTaskPropertyDaoImpl extends HibernateBaseDaoImpl<VwTaskProperty, String> {
		private SessionFactory sessionFactory = (SessionFactory)HibernateBaseDaoImplTest.this.applicationContext.getBean("sessionFactory");

		private VwTaskPropertyDaoImpl() {
			super();
			this.setSessionFactory(this.sessionFactory);
		}

	}

	@SuppressWarnings("unused")
	private class TTaskTemplateDaoImpl extends HibernateBaseDaoImpl<TTaskTemplate, Long> {
		private SessionFactory sessionFactory = (SessionFactory)HibernateBaseDaoImplTest.this.applicationContext.getBean("sessionFactory");

		private TTaskTemplateDaoImpl() {
			super();
			this.setSessionFactory(this.sessionFactory);
		}
	}

}
