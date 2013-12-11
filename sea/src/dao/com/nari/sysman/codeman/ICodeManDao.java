package com.nari.sysman.codeman;
import com.nari.support.Page;

public interface ICodeManDao {
	
	/**
	 * 查询营销数据的目录
	 * @author zhaoliang
	 *
	 */
	public Page<CodeManOut> queryCodeManOut(long start, int limit);
	
	/**
	 * 查询营销数据的具体菜单项
	 * @author zhaoliang
	 *
	 */
	public Page<CodeManOutSub> queryCodeManOutSub(String sortId,long start, int limit);
	
	/**
	 * 查询内部数据的目录
	 * @author zhaoliang
	 *
	 */
	public Page<CodeManIn> queryCodeManIn(long start, int limit);
	
	/**
	 * 查询内部数据的具体菜单项
	 * @author zhaoliang
	 *
	 */
	public Page<CodeManIn> queryCodeManInSub(String sortId,long start, int limit);
	
	/**
	 * 内部数据增删改操作
	 * @author zhaoliang
	 *
	 */
	public void CodeManSet(String sql);
}
