package com.nari.support;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.nari.action.baseaction.Constans;

/**
 * 与具体 ORM 实现无关的分页参数及查询结果封装。<br>
 * @param <T> Page 中记录的类型。
 * @author 鲁兆淞
 */
public class Page<T> {
	/* 公共变量 */
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/* 分页参数 */
	protected long startNo = 0;
	protected int pageSize = Constans.DEFAULT_PAGE_SIZE;
	protected String orderBy = null;
	protected String order = null;
	protected boolean autoCount = true;

	/* 返回结果 */
	protected List<T> result = Collections.emptyList();
	protected long totalCount = -1;

	/* 构造函数 */

	public Page() {
	}

	public Page(final int pageSize) {
		this.setPageSize(pageSize);
	}

	public Page(final long startNo, final int pageSize) {
		this.setStartNo(startNo);
		this.setPageSize(pageSize);
	}

	/* 访问查询参数方法 */

	/**
	 * 获得开始记录编号，序号从 0 开始，默认为 0。
	 */
	public long getStartNo() {
		return this.startNo;
	}

	/**
	 * 设置开始记录编号，序号从 0 开始，低于 0 时自动调整为 0。
	 * @param startNo 开始记录编号
	 */
	public void setStartNo(long startNo) {
		this.startNo = startNo;
		if(startNo < 0) {
			this.startNo = 0;
		}
	}

	/**
	 * 获得每页的记录数量，默认为 50。
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * 设置每页的记录数量，低于 1 时自动调整为 10。
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
		if(pageSize < 1) {
			this.pageSize = 10;
		}
	}

	/**
	 * 获得排序字段，无默认值。
	 */
	public String getOrderBy() {
		return this.orderBy;
	}

	/**
	 * 设置排序字段。
	 * @param orderBy 可选值为desc或asc，多个排序字段时用 ',' 分隔。
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 是否已设置排序字段，无默认值。
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(this.orderBy) && StringUtils.isNotBlank(this.order));
	}

	/**
	 * 获得排序方向。
	 */
	public String getOrder() {
		return this.order;
	}

	/**
	 * 设置排序方向。
	 * @param order 可选值为desc或asc，多个排序字段时用 ',' 分隔。
	 */
	public void setOrder(final String order) {
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for(String orderStr : orders) {
			if(!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
		}
		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * 查询对象时是否自动另外执行 count 查询获取总记录数，默认为 true。
	 */
	public boolean isAutoCount() {
		return this.autoCount;
	}

	/**
	 * 设置查询对象时是否自动另外执行 count 查询获取总记录数。
	 * @param autoCount 是否自动查询总记录数
	 */
	public void setAutoCount(final boolean autoCount) {
		this.autoCount = autoCount;
	}

	/* 访问查询结果方法 */

	/**
	 * 取得页内的记录列表。
	 */
	public List<T> getResult() {
		return this.result;
	}

	/**
	 * 设置页内的记录列表。
	 */
	public void setResult(final List<T> result) {
		this.result = result;
	}

	/**
	 * 取得总记录数，默认值为 -1。
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 设置总记录数。
	 */
	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 根据 pageSize 与 totalCount 计算总页数，默认值为 -1。
	 */
	public long getTotalPages() {
		if(this.totalCount < 0) return -1;
		long count = this.totalCount / this.pageSize;
		if(this.totalCount % this.pageSize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页。
	 */
	public boolean isHasNext() {
		return ((this.startNo / this.pageSize) + 1 <= this.getTotalPages());
	}

	/**
	 * 取得下页的页号，序号从 1 开始。
	 * 当前页为尾页时仍返回尾页序号。
	 */
	public long getNextPage() {
		if(this.isHasNext()) return (this.startNo / this.pageSize) + 1;
		else return (this.startNo / this.pageSize);
		
	}

	/**
	 * 是否还有上一页。
	 */
	public boolean isHasPre() {
		return ((this.startNo / this.pageSize) - 1 >= 1);
	}

	/**
	 * 取得上页的页号，序号从 1 开始。
	 * 当前页为首页时返回首页序号。
	 */
	public long getPrePage() {
		if(this.isHasPre()) return (this.startNo / this.pageSize) - 1;
		else return (this.startNo / this.pageSize);
		//return 1;
	}


}
