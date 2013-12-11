package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

/***
 * 控制方案中涉及按控制方案类别对不同表进行操作，
 * 但是这几个操作是类似的， 通过这个接口对数据进行统一操作
 * 
 * @author 黄轩
 * **/
public interface IWTmnlSchemePublicDao {
		/****
		 * 修改方案的启/停状态
		 * @param ctrlSchemeIds 控制方案的id
		 * *
		 * @throws DBAccessException ***/
		public void updateAboutCtrlScheme(List ctrlSchemeIds) throws DBAccessException;
		/****
		 * 找出当前的控制方案下的所有的订阅用电用户
		 * @param ctrlSchemeId 控制方案的id
		 * ***/
		public <T> Page<T> findCtrlSchemeUser(Long ctrlSchemeId,long start,int limit);
		/****
		 * 删除某个控制方案下的一个或者多个个用电用户
		 *  
		 *@param ctrlSchemeId 控制方案的id
		 *@param userNos 所有被传入的用户编号
		 * *
		 * @throws DBAccessException **/
		public void deleteCtrlSchemeUser(Long ctrlSchemeId,List userNos) throws DBAccessException;
		/****
		 * 通过id来查找功控的类型
		 * @param ctrlSchemeId 控制类型的id
		 * ***/
		public String findCtrlSchemeType(Long ctrlSchemeId);
		/***
		 * 找到一个控制方案下的所有的用户
		 * @param staffNo 操作人员id
		 * @param ctrlSchemeId 控制方案id
		 * @param start 
		 * @param limit
		 * ***/
		public Page findAllUsers(String staffNo,Long ctrlSchemeId,long start,int limit) throws DBAccessException;
}
