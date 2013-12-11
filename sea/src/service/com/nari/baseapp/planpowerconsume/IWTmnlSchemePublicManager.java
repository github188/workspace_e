package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IWTmnlSchemePublicManager {
	public void updateAboutCtrlScheme(List ctrlSchemeIds) throws DBAccessException ;

	public void deleteCtrlSchemeUser(Long ctrlSchemeId, List userNos)  throws DBAccessException ;


	public <T> Page<T> findCtrlSchemeUser(Long ctrlSchemeId, long start,
			int limit)   throws DBAccessException;
	/***
	 * 找到一个控制方案下的所有的用户
	 * @param staffNo 操作人员id
	 * @param ctrlSchemeId 控制方案id
	 * @param start 
	 * @param limit
	 * ***/
	public Page findAllUsers(String staffNo,Long ctrlSchemeId,long start,int limit) throws DBAccessException;
}
