package com.nari.advapp.vipconsman;

import java.util.Date;
import java.util.List;

import com.nari.privilige.PSysUser;

public interface LogReleaseQueryManager {
	/**
	 * @description 查询并统计日志发布结果
	 * @param pubDate,pSysUser
	 * @return logReleaseList
	 */
	List<LogRelease> queryLogStastics(Date pubDate, PSysUser pSysUser);

}
