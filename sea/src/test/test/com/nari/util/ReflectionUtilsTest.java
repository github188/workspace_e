package test.com.nari.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.nari.util.ReflectionUtils;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.UtilException;

/**
 * ReflectionUtils 类单元测试
 * @author 鲁兆淞
 */
public class ReflectionUtilsTest extends Assert {
	private String className = "com.nari.util.ReflectionUtils"; // 测试私有方法反射需要使用的类名
	private String methodName; // 测试私有方法反射需要使用的方法名

	/**
	 * 测试 getFieldValue 方法。
	 */
	@Test
	public void testGetFieldValue() {
		try {
			ChildTestBean childTestBean = new ChildTestBean(); // 实例化子测试类
			assertEquals("获取子测试类 Private 字段失败", "childPrivateField", ReflectionUtils.getFieldValue(childTestBean, "childPrivateField"));
			assertEquals("获取子测试类 Public 字段失败", "childPublicField", ReflectionUtils.getFieldValue(childTestBean, "childPublicField"));
			assertEquals("获取父测试类 Private 字段失败", "parentPrivateField", ReflectionUtils.getFieldValue(childTestBean, "parentPrivateField"));
			assertEquals("获取父测试类 Public 字段失败", "parentPublicField", ReflectionUtils.getFieldValue(childTestBean, "parentPublicField"));

			try {
				ReflectionUtils.getFieldValue(null, ""); // object 为 NULL
				assertTrue("未捕获到 object 为 NULL 异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_OBJECTISNULL, e.getMessage());
			}
			try {
				ReflectionUtils.getFieldValue(childTestBean, ""); // fieldName 为空
				assertTrue("未捕获到 fieldName 为空异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_STRINGISEMPTY, e.getMessage());
			}
			try {
				ReflectionUtils.getFieldValue(childTestBean, "abcdefg"); // childTestBean 类及其父类没有 abcdefg 字段
				assertTrue("未捕获到字段未找到异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_FIELDNOTFOUND, e.getMessage());
			}
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 测试 setFieldValue 方法。
	 */
	@Test
	public void testSetFieldValue() {
		ChildTestBean childTestBean = new ChildTestBean(); // 实例化子测试类
		try {
			ReflectionUtils.setFieldValue(childTestBean, "childPrivateField", "1");
			ReflectionUtils.setFieldValue(childTestBean, "childPublicField", "2");
			ReflectionUtils.setFieldValue(childTestBean, "parentPrivateField", "3");
			ReflectionUtils.setFieldValue(childTestBean, "parentPublicField", "4");

			assertEquals("设置子测试类 Private 字段失败", "1", childTestBean.getChildPrivateField());
			assertEquals("设置子测试类 Public 字段失败", "2", childTestBean.getChildPublicField());
			assertEquals("设置父测试类 Private 字段失败", "3", childTestBean.getParentPrivateField());
			assertEquals("设置父测试类 Public 字段失败", "4", childTestBean.getParentPublicField());

			try {
				ReflectionUtils.setFieldValue(null, "", ""); // object 为 NULL
				assertTrue("未捕获到 object 为 NULL 异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_OBJECTISNULL, e.getMessage());
			}
			try {
				ReflectionUtils.setFieldValue(childTestBean, "", ""); // fieldName 为空
				assertTrue("未捕获到 fieldName 为空异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_STRINGISEMPTY, e.getMessage());
			}
			try {
				ReflectionUtils.setFieldValue(childTestBean, "abcdefg", ""); // childTestBean 类及其父类没有 abcdefg 字段
				assertTrue("未捕获到字段未找到异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_FIELDNOTFOUND, e.getMessage());
			}
			try {
				ReflectionUtils.setFieldValue(childTestBean, "childPrivateField", 12); // 字段与字段值类型不同
				assertTrue("未捕获到类型转换异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_PARAMETERTYPEERROR, e.getMessage());
			}
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 测试 invokeMethod 方法。
	 */
	@Test
	public void testInvokeMethod() {
		ChildTestBean childTestBean = new ChildTestBean(); // 实例化子测试类
		try {
			assertEquals("获取子测试类 Private 方法失败", "childPrivateMethod", ReflectionUtils.invokeMethod(childTestBean, "childPrivateMethod", null, null));
			assertEquals("获取子测试类 Public 方法失败", "childPublicMethod", ReflectionUtils.invokeMethod(childTestBean, "childPublicMethod", null, null));
			assertEquals("获取父测试类 Private 方法失败", "parentPrivateMethod", ReflectionUtils.invokeMethod(childTestBean, "parentPrivateMethod", null, null));
			assertEquals("获取父测试类 Public 方法失败", "parentPublicMethod", ReflectionUtils.invokeMethod(childTestBean, "parentPublicMethod", null, null));

			try {
				ReflectionUtils.invokeMethod(null, "", null, null); // object 为 NULL
				assertTrue("未捕获到 object 为 NULL 异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_OBJECTISNULL, e.getMessage());
			}
			try {
				ReflectionUtils.invokeMethod(childTestBean, "", null, null); // methodName 为空
				assertTrue("未捕获到 methodName 为空异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_STRINGISEMPTY, e.getMessage());
			}
			try {
				ReflectionUtils.invokeMethod(childTestBean, "abcdefg", null, null); // childTestBean 类及其父类没有 abcdefg 方法
				assertTrue("未捕获到方法未找到异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_METHODNOTFOUND, e.getMessage());
			}
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 测试 getSuperClassGenricType 方法。
	 */
	@Test
	public void testGetSuperClassGenricType() {
		assertEquals("第1个泛型参数类型出错", ChildTestBean.class, ReflectionUtils.getSuperClassGenricType(UserDao.class));
		assertEquals("第2个泛型参数类型出错", String.class, ReflectionUtils.getSuperClassGenricType(UserDao.class, 1));
		assertEquals("父类非泛型测试出错", Object.class, ReflectionUtils.getSuperClassGenricType(ChildTestBean.class));
		assertEquals("无父类测试出错", Object.class, ReflectionUtils.getSuperClassGenricType(ParentTestBean.class));
	}

	/**
	 * 测试 convertElementPropertyToString 方法。
	 */
	@Test
	public void testConvertElementPropertyToString() {
		List<ChildTestBean> childTestBeanList = new ArrayList<ChildTestBean>();

		ChildTestBean childTestBean1 = new ChildTestBean();
		childTestBean1.setChildPublicField("1");
		childTestBeanList.add(childTestBean1);

		ChildTestBean childTestBean2 = new ChildTestBean();
		childTestBean2.setChildPublicField("2");
		childTestBeanList.add(childTestBean2);

		try {
			assertEquals("1,2", ReflectionUtils.convertElementPropertyToString(childTestBeanList, "childPublicField", ","));
			try {
				assertEquals("1,2", ReflectionUtils.convertElementPropertyToString(childTestBeanList, "aaa", ",")); // 没有 aaa 字段
				assertTrue("未捕获到字段异常", false);
			} catch(UtilException e) {
				assertEquals("异常信息出错", ExceptionConstants.UTL_FIELDNOTFOUND, e.getMessage());
			}
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 测试 getDeclaredField 方法。
	 * 该方法为私有方法，需通过反射得到。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeclaredField() {
		this.methodName = "getDeclaredField";
		try {
			Class<ReflectionUtils> clazz = (Class<ReflectionUtils>)Class.forName(this.className); // 通过类名获取测试类
			Method testMethod = clazz.getDeclaredMethod(this.methodName, Object.class, String.class); // 通过方法名和方法参数类型列表获取测试方法
			testMethod.setAccessible(true); // 设置该私有方法可访问

			ChildTestBean childTestBean = new ChildTestBean(); // 实例化子测试类
			Field childPrivateField = (Field)testMethod.invoke(clazz, childTestBean, "childPrivateField"); // 执行 getDeclaredField 方法得到子测试类的 Private 字段
			Field childPublicField = (Field)testMethod.invoke(clazz, childTestBean, "childPublicField"); // 执行 getDeclaredField 方法得到子测试类的 Public 字段
			Field parentPrivateField = (Field)testMethod.invoke(clazz, childTestBean, "parentPrivateField"); // 执行 getDeclaredField 方法得到父测试类的 Private 字段
			Field parentPublicField = (Field)testMethod.invoke(clazz, childTestBean, "parentPublicField"); // 执行 getDeclaredField 方法得到父测试类的 Public 字段

			childPrivateField.setAccessible(true); // 设置子测试类私有字段可访问
			parentPrivateField.setAccessible(true); // 设置父测试类私有字段可访问

			assertEquals("获取子测试类 Private 字段失败", childPrivateField.get(childTestBean), "childPrivateField");
			assertEquals("获取子测试类 Public 字段失败", childPublicField.get(childTestBean), "childPublicField");
			assertEquals("获取父测试类 Private 字段失败", parentPrivateField.get(childTestBean), "parentPrivateField");
			assertEquals("获取父测试类 Public 字段失败", parentPublicField.get(childTestBean), "parentPublicField");
		} catch(ClassNotFoundException e) {
			assertTrue("没有找到类：" + this.className, false);
		} catch(NoSuchMethodException e) {
			assertTrue("没有找到方法：" + this.methodName, false);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 测试 getDeclaredMethod 方法。
	 * 该方法为私有方法，需通过反射得到。
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetDeclaredMethod() {
		this.methodName = "getDeclaredMethod";
		try {
			Class<ReflectionUtils> clazz = (Class<ReflectionUtils>)Class.forName(this.className); // 通过类名获取测试类
			Method testMethod = clazz.getDeclaredMethod(this.methodName, Object.class, String.class, Class[].class); // 通过方法名和方法参数类型列表获取测试方法
			testMethod.setAccessible(true); // 设置该私有方法可访问

			ChildTestBean childTestBean = new ChildTestBean(); // 实例化子测试类
			Method childPrivateMethod = (Method)testMethod.invoke(clazz, childTestBean, "childPrivateMethod", null); // 执行 getDeclaredMethod 方法得到子测试类的 Private 方法
			Method childPublicMethod = (Method)testMethod.invoke(clazz, childTestBean, "childPublicMethod", null); // 执行 getDeclaredMethod 方法得到子测试类的 Public 方法
			Method parentPrivateMethod = (Method)testMethod.invoke(clazz, childTestBean, "parentPrivateMethod", null); // 执行 getDeclaredMethod 方法得到父测试类的 Private 方法
			Method parentPublicMethod = (Method)testMethod.invoke(clazz, childTestBean, "parentPublicMethod", null); // 执行 getDeclaredMethod 方法得到父测试类的 Public 方法

			childPrivateMethod.setAccessible(true); // 设置子测试类私有方法可访问
			parentPrivateMethod.setAccessible(true); // 设置父测试类私有方法可访问

			assertEquals("获取子测试类 Private 方法失败", childPrivateMethod.invoke(childTestBean), "childPrivateMethod");
			assertEquals("获取子测试类 Public 方法失败", childPublicMethod.invoke(childTestBean), "childPublicMethod");
			assertEquals("获取父测试类 Private 方法失败", parentPrivateMethod.invoke(childTestBean), "parentPrivateMethod");
			assertEquals("获取父测试类 Public 方法失败", parentPublicMethod.invoke(childTestBean), "parentPublicMethod");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			assertTrue("没有找到类：" + this.className, false);
		} catch(NoSuchMethodException e) {
			e.printStackTrace();
			assertTrue("没有找到方法：" + this.methodName, false);
		} catch(Exception e) {
			e.printStackTrace();
			assertTrue("发生未知异常", false);
		}
	}

	/**
	 * 父测试类
	 * @author 鲁兆淞
	 */
	@SuppressWarnings("unused")
	private class ParentTestBean {
		public String parentPublicField = "parentPublicField";
		private String parentPrivateField = "parentPrivateField";

		public String parentPublicMethod() {
			return "parentPublicMethod";
		}

		private String parentPrivateMethod() {
			return "parentPrivateMethod";
		}

		public String getParentPublicField() {
			return this.parentPublicField;
		}

		public void setParentPublicField(String parentPublicField) {
			this.parentPublicField = parentPublicField;
		}

		public String getParentPrivateField() {
			return this.parentPrivateField;
		}

		public void setParentPrivateField(String parentPrivateField) {
			this.parentPrivateField = parentPrivateField;
		}

	}

	/**
	 * 子测试类
	 * @author 鲁兆淞
	 */
	@SuppressWarnings("unused")
	public class ChildTestBean extends ParentTestBean {
		public String childPublicField = "childPublicField";
		private String childPrivateField = "childPrivateField";

		public String childPublicMethod() {
			return "childPublicMethod";
		}

		private String childPrivateMethod() {
			return "childPrivateMethod";
		}

		public String getChildPublicField() {
			return this.childPublicField;
		}

		public void setChildPublicField(String childPublicField) {
			this.childPublicField = childPublicField;
		}

		public String getChildPrivateField() {
			return this.childPrivateField;
		}

		public void setChildPrivateField(String childPrivateField) {
			this.childPrivateField = childPrivateField;
		}

	}

	/**
	 * 父泛型测试类
	 * @author 鲁兆淞
	 */
	private class HibernateDao<T, PK> {
	}

	/**
	 * 子泛型测试类
	 * @author 鲁兆淞
	 */
	private class UserDao extends HibernateDao<ChildTestBean, String> {
	}

}
