package com.nari.runman.archivesman;

import java.util.List;
import java.util.Map;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.ServiceException;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public interface IArchivesmanManager {
	/***
	 * 相应左边树的点击事件返回数据
	 * 
	 * @param staffNo
	 *            操作人员的工号
	 * @param postData
	 *            点击后都通post传到后台的数据 格式 org_值_...
	 * @param start
	 *            分页的开始
	 * @param 分页的结束
	 * 
	 *            return {@link RETURN} page对象 结果集里面放置的map *
	 ***/
	Page findClick(PSysUser user, String postData);
	/***档案同步执行
	 * @param consNos 用户的编号列表
	 * @param consId 用户的id标示列表
	 * @param cpNos 采集器编号列表
	 * @param tmnlIds 终端编号列表
	 * @param tgIds 台区编号列表
	 * @return 如果同步成功返回null，如果同步失败返回
	 * CONS_ID为key  错误码value的Map
	 * @throws ServiceException ****/
	public Map addDoArchives(List<String> consNos, List<String> consIds,
			List<String> cpNos, List<String> tmnlIds, List<String> tgIds)
			throws ServiceException;

}
