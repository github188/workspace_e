package com.nari.baseapp.datagathorman.impl;

import junit.framework.Assert;

import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;

public class DaoUtils {
	//此类继承雨spring的jdbcdaosupport，保证了多线程操作的安全性
	//本身这个类就不是充血的，多线程操作对其影响不大
	private static LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
	//SQLdata 没有进行任何修改操作，不存在线程同步问题
	private static SqlData sd = SqlData.getOne();
	/**
	 * 
	 * @param <T>
	 * @param desc  MapResultMapper生成时的描述
	 * @param dao  一个回调
	 * @return 通过回调得到的结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T run(String desc, IDoDao dao) {
		Assert.assertNotNull(desc);
		Assert.assertNotNull(dao);
		MapResultMapper mrp = new MapResultMapper(desc);
		return (T)dao.execute(tree, mrp, sd);
	}
}
