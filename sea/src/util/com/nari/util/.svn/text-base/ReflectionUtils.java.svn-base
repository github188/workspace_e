package com.nari.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.UtilException;

/**
 * 反射工具集合。
 * 提供访问私有变量，获取泛型类型，提取集合中元素的属性等工具。
 * 详细使用方法请参照单元测试类。
 * @author 鲁兆淞
 */
public class ReflectionUtils {
	private static Logger logger = Logger.getLogger(ReflectionUtils.class);

	/**
	 * 直接获取对象字段值，无视 private 或 protected 修饰符，不经过 getter 函数。
	 * @param object 对象
	 * @param fieldName 字段名
	 * @return 字段值
	 * @throws UtilException 工具类异常
	 */
	public static Object getFieldValue(final Object object, final String fieldName) throws UtilException {
		Field field = getDeclaredField(object, fieldName); // 获取字段 Field 对象
		Object result = null;
		try {
			field.setAccessible(true); // 强行设置字段可访问
			result = field.get(object); // 获取字段的值
		} catch(NullPointerException e) { // 没找到字段的处理
			logger.error("在类 " + object + " 中没有找到字段 " + fieldName);
			throw new UtilException(ExceptionConstants.UTL_FIELDNOTFOUND);
		} catch(Exception e) { // 未知异常的处理
			e.printStackTrace();
			logger.error("未知异常");
			throw new UtilException(ExceptionConstants.UTL_OTHERS);
		}
		return result;
	}

	/**
	 * 直接设置对象属性值，无视 private 或 protected 修饰符，不经过 setter 函数。
	 * @param object 对象
	 * @param fieldName 字段名
	 * @param value 字段值
	 * @throws UtilException 工具类异常
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) throws UtilException {
		Field field = getDeclaredField(object, fieldName); // 获取字段 Field 对象
		try {
			field.setAccessible(true); // 强行设置字段可访问
			field.set(object, value); // 设置字段的值
		} catch(NullPointerException e) { // 没找到字段的处理
			logger.error("在类 " + object + " 中没有找到字段 " + fieldName);
			throw new UtilException(ExceptionConstants.UTL_FIELDNOTFOUND);
		} catch(IllegalArgumentException e) { // 字段值类型出错的处理
			logger.error("字段类型 " + field.getType() + " 与字段值类型 " + value.getClass() + " 不匹配");
			throw new UtilException(ExceptionConstants.UTL_PARAMETERTYPEERROR);
		} catch(Exception e) { // 未知异常的处理
			e.printStackTrace();
			logger.error("未知异常");
			throw new UtilException(ExceptionConstants.UTL_OTHERS);
		}
	}

	/**
	 * 直接调用对象方法，无视 private 或 protected 修饰符。
	 * @param object 对象
	 * @param methodName 方法名
	 * @param parameterTypes 方法参数类型数组
	 * @param parameters 方法参数值数组
	 * @return 对象方法返回结果
	 * @throws UtilException 工具类异常
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters) throws UtilException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes); // 获取方法 Method 对象
		if(method == null) { // 没有方法 Method 对象的处理
			logger.error("在类 " + object + " 中没有找到方法 " + methodName);
			throw new UtilException(ExceptionConstants.UTL_METHODNOTFOUND);
		}
		method.setAccessible(true); // 强行设置方法可访问
		try {
			return method.invoke(object, parameters);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("未知异常：" + e.getMessage());
			throw new UtilException(ExceptionConstants.UTL_OTHERS);
		}
	}

	/**
	 * 获取 Class 定义中声明的父类的泛型参数的类型。
	 * @param clazz 类名
	 * @return 定义中声明的父类的泛型参数的类型。如果无法找到则返回Object.class。
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 获取 Class 定义中声明的父类的泛型参数的类型。
	 * @param clazz 类名
	 * @param index 泛型参数序号
	 * @return 定义中声明的父类的泛型参数的类型。如果无法找到则返回Object.class。
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass(); // 获取父类类型
		if(!(genType instanceof ParameterizedType)) { // 如果父类不是泛型的处理
			logger.warn("类 " + clazz.getSimpleName() + " 的父类不是泛型");
			return Object.class;
		}
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments(); // 获取泛型参数数组
		if(index >= params.length || index < 0) { // 泛型参数数组出错的处理
			logger.warn("泛型参数序号错误");
			return Object.class;
		}
		if(!(params[index] instanceof Class)) { // 泛型参数类型出错的处理
			logger.warn("该泛型参数不是 Class 类型");
			return Object.class;
		}
		return (Class)params[index];
	}

	/**
	 * 提取容器中的对象的字段（通过 getter 方法）组合成 List。
	 * @param collection 容器
	 * @param propertyName 对象的字段名
	 * @return 对象的字段值组成的 List
	 * @throws UtilException 工具类异常
	 */
	@SuppressWarnings("unchecked")
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) throws UtilException {
		List list = new ArrayList();
		for(Object obj : collection) {
			try {
				list.add(PropertyUtils.getProperty(obj, propertyName)); // 将容器中对象的字段值塞给 List
			} catch(NoSuchMethodException e) {
				logger.error("在类 " + obj.getClass() + " 中未找到字段 " + propertyName);
				throw new UtilException(ExceptionConstants.UTL_FIELDNOTFOUND);
			} catch(Exception e) {
				e.printStackTrace();
				logger.error("未知异常：" + e.getMessage());
				throw new UtilException(ExceptionConstants.UTL_OTHERS);
			}
		}
		return list;
	}

	/**
	 * 提取容器中的对象的字段（通过 getter 方法）组合成由分割符分隔的字符串。
	 * @param collection 容器
	 * @param propertyName 对象的字段名
	 * @param separator 分隔符
	 * @return 对象的字段值组成的字符串
	 * @throws UtilException 工具类异常
	 */
	@SuppressWarnings("unchecked")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName, final String separator) throws UtilException {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list.toArray(), separator);
	}

	/**
	 * 循环向上转型，获取对象的已声明字段。
	 * @param object 对象
	 * @param fieldName 字段名
	 * @return 返回字段的 Field 对象。如向上转型到 Object 仍无法找到，则返回 NULL。
	 * @throws UtilException 工具类异常
	 */
	private static Field getDeclaredField(final Object object, final String fieldName) throws UtilException {
		if(object == null) { // 参数 object 为 NULL 的处理
			logger.error("参数 object 为 NULL");
			throw new UtilException(ExceptionConstants.UTL_OBJECTISNULL);
		}
		if(StringUtils.isEmpty(fieldName)) { // 参数 fieldName 为空的处理
			logger.error("参数 fieldName 为空");
			throw new UtilException(ExceptionConstants.UTL_STRINGISEMPTY);
		}

		for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) { // object 向上转型直到 Object 类
			try {
				return superClass.getDeclaredField(fieldName); // 如果在当前类找到字段声明则返回字段的 Field 对象
			} catch(NoSuchFieldException e) { // 字段不在当前类声明则继续向上转型
			}
		}
		return null;
	}

	/**
	 * 循环向上转型，获取对象的已声明方法。
	 * @param object 对象
	 * @param methodName 方法名
	 * @param parameterTypes 方法参数类型数组
	 * @return 返回方法的 Method 对象。如向上转型到 Object 仍无法找到，则返回 NULL。
	 * @throws UtilException 工具类异常
	 */
	private static Method getDeclaredMethod(final Object object, final String methodName, final Class<?>[] parameterTypes) throws UtilException {
		if(object == null) { // 参数 object 为 NULL 的处理
			logger.error("参数 object 为 NULL");
			throw new UtilException(ExceptionConstants.UTL_OBJECTISNULL);
		}
		if(StringUtils.isEmpty(methodName)) { // 参数 methodName 为空的处理
			logger.error("参数 methodName 为空");
			throw new UtilException(ExceptionConstants.UTL_STRINGISEMPTY);
		}

		for(Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) { // object 向上转型直到 Object 类
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes); // 如果在当前类找到方法声明则返回方法的 Method 对象
			} catch (NoSuchMethodException e) { // 方法不在当前类声明则继续向上转型
			}
		}
		return null;
	}

}
