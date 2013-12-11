package com.nari.support;


/**
 * 与具体 ORM 实现无关的属性过滤条件封装类。<br>
 * 主要记录页面中简单的搜索过滤条件。
 * @author 鲁兆淞
 */
public class PropertyFilter {

	/**
	 * 属性比较类型。
	 */
	public enum MatchType {
		EQ, // 等于
		NEQ, // 不等于
		LIKE, // 字符串模式匹配
		NLIKE, // 非匹配
		LT, // 小于
		GT, // 大于
		LE, // 小于等于
		GE; // 大于等于
	}

	private String propertyName = null; // 属性名
	private Object propertyValue = null; // 比较值
	private MatchType matchType = MatchType.EQ; // 比较类型

	/**
	 * 不可访问默认构造方法。
	 */
	@SuppressWarnings("unused")
	private PropertyFilter() {
	}

	/**
	 * 构造方法，默认比较类型为等于。
	 * @param propertyNames 属性名
	 * @param propertyValue 比较值
	 */
	public PropertyFilter(final String propertyNames, final Object propertyValue) {
		this.propertyName = propertyNames;
		this.propertyValue = propertyValue;
	}

	/**
	 * 构造方法。
	 * @param propertyNames 属性名
	 * @param propertyValue 比较值
	 * @param matchType 比较类型，见 MatchType。
	 */
	public PropertyFilter(final String propertyNames, final Object propertyValue, final MatchType matchType) {
		this.propertyName = propertyNames;
		this.propertyValue = propertyValue;
		this.matchType = matchType;
	}

	/**
	 * 获取属性名
	 * @return 属性名
	 */
	public String getPropertyNames() {
		return this.propertyName;
	}

	/**
	 * 设置属性名
	 * @param propertyNames 属性名
	 */
	public void setPropertyNames(String propertyNames) {
		this.propertyName = propertyNames;
	}

	/**
	 * 获取比较值
	 * @return 比较值
	 */
	public Object getPropertyValue() {
		return this.propertyValue;
	}

	/**
	 * 设置比较值
	 * @param propertyValue 比较值
	 */
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * 获取比较类型，见 MatchType。
	 * @return 比较类型
	 */
	public MatchType getMatchType() {
		return this.matchType;
	}

	/**
	 * 设置比较类型，见 MatchType。
	 * @param matchType 比较类型
	 */
	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

}
