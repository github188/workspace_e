package test.com.nari.test;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * Spring 单元测试父类。<br>
 * Dao 层单元测试先继承此类。
 * @author 鲁兆淞
 */
public class SpringJunitTest extends AbstractTransactionalDataSourceSpringContextTests {

	public SpringJunitTest() {
		super();
		this.setAutowireMode(AUTOWIRE_BY_NAME);
	}

	@Override
	protected String[] getConfigLocations() {
		String[] config = new String[] { "spring-config\\applicationContext-*.xml" };
		return config;
	}

}
