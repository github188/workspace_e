package com.nari.runman.archivesman.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;

import com.nari.baseapp.datagathorman.SqlData;
import com.nari.baseapp.datagathorman.impl.LeftTreeDaoImpl;
import com.nari.baseapp.planpowerconsume.impl.MapResultMapper;
import com.nari.privilige.PSysUser;
import com.nari.runman.archivesman.IArchivesmanManager;
import com.nari.runman.archivesman.archivesmanAction;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.ServiceException;
import com.nari.util.ws.ClientAuthenticationHandler;
import com.nari.util.ws.PropertiesUtils;
import com.nari.util.ws.XMLSwitchUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
/****
 * 档案同步
 * @author huangxuan
 * *****/
public class ArchivesmanManagerImpl implements IArchivesmanManager {
	protected Logger logger = Logger.getLogger(this.getClass());
	/***
	 * 相应左边树的点击事件返回数据
	 * 前端分页 分页代码,最多一次可以装载500条信息，如果查过了，提示出错
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
	@SuppressWarnings("unchecked")
	public Page findClick(PSysUser user, String postData) {
		if (null == postData) {
			throw new RuntimeException("传入数据不能为空");
		}
		String arr[] = postData.split("_");
		if (arr.length < 2) {
			throw new RuntimeException("传入的数据格式不正确");
		}
		// 左边树的默认实现类
		LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
		String type = arr[0];
		String value = arr[1];
		 String level=arr[2];
		//String consType=arr[3];
		SqlData sd = SqlData.getOne();
		// 使用[*,c]可以转化为骆驼命名法
		MapResultMapper mrp = new MapResultMapper("[*,c]");
		if (type.equals("org")) {
			if(level.equals("06")){
				return tree.pagingFind(sd.archives_run_org_06
						+ SysPrivilige.addPri(user, "c", "r", 7),0,501,
						mrp, new Object[] { value , user.getStaffNo(),
								user.getStaffNo(), user.getStaffNo()
						});
			}
			return tree.pagingFind(sd.archives_run_org
					+ SysPrivilige.addPri(user, "c", "r", 7),0,501,
					mrp, new Object[] { value + "_%", user.getStaffNo(),
							user.getStaffNo(), user.getStaffNo()
					});
		} else if (type.equals("sub")) {
			return tree.pagingFind(sd.archives_run_sub
					+ SysPrivilige.addPri(user, "c", "r", 7), 0,501,
					mrp, new Object[] { value, user.getStaffNo(),
							user.getStaffNo(), user.getStaffNo() });
		} else if (type.equals("line")) {
			return tree.pagingFind(sd.archives_run_line
					+ SysPrivilige.addPri(user, "c", "r", 7),0,501,
					mrp, new Object[] { value, user.getStaffNo(),
							user.getStaffNo(), user.getStaffNo() });
		} else if (type.equals("trade")) {
			return tree.pagingFind(sd.archives_run,0,501 , mrp,
					new Object[] {});
		} else if (type.equals("tmnl")) {
			return tree.pagingFind(sd.archives_run_tmnl
					+ SysPrivilige.addPri(user, "c", "r", 7),0,501, 
					mrp, new Object[] { value, user.getStaffNo(),
							user.getStaffNo(), user.getStaffNo() });
		} else if (type.equals("ugp")) {
			// group_click
			return tree.pagingFind(sd.archives_ugp
					+ SysPrivilige.addTmnlFactory("r")
					+ SysPrivilige.addConsType("c"),0,501 , mrp,
					new Object[] { value, user.getStaffNo(), value,user.getStaffNo(),user.getStaffNo() });
			// throw new RuntimeException("不支持此节点");
		} else {
			logger.warn("不支持的节点类型");
			throw new RuntimeException("不支持此节点");
		}
	}
	
	/*****
	 *通过用户来查找终端档案
	 * 
	 * ***/
	public Map findTmnlArchives(PSysUser user){
	
	return null;	
	}
	/****
	 * 
	 * 通过用户来查找采集器档 案
	 * ****/
	public Map findCollArchives(){
		return null;
	}
	/*****
	 * 通过用户查找开关档案
	 * ****/
	public Page<Map> findSwitchArchives(){
		
		return null;
	}
	/*****
	 * 通过用户查找变电器档案的信息
	 * *****/
	public Map findChangeArchives(){
		return null;
	}
	/***档案同步执行
	 * @param consNos 用户的编号列表
	 * @param consId 用户的id标示列表
	 * @param cpNos 采集器编号列表
	 * @param tmnlIds 终端编号列表
	 * @param tgIds 台区编号列表
	 * @return 如果同步成功返回null，如果同步失败返回
	 * CONS_ID为key  错误码value的Map
	 * @throws ServiceException ****/
	@SuppressWarnings("unchecked")
	public Map addDoArchives(List<String> consNos,List<String> consIds,
			List<String> cpNos,List<String> tmnlIds,List<String> tgIds) throws ServiceException{
		try {
			int size=consNos.size();
			List list=new ArrayList();
			for(int i=0;i<size;i++){
					HashMap<String,String> hm=new HashMap<String, String>();
					hm.put("CONS_NO", consNos.get(i));
					hm.put("CONS_ID", consIds.get(i));
					hm.put("CP_NO", cpNos.get(i));
					hm.put("TERMINAL_ID", tmnlIds.get(i));
					hm.put("TG_ID", tgIds.get(i));
					list.add(hm);
			}
			XMLSwitchUtil xsu=new XMLSwitchUtil();
			
//			String url = PropertiesUtils.getProperty("url");
//			
//			String username = PropertiesUtils
//					.getProperty("username");
//			String password = PropertiesUtils
//					.getProperty("password");
			String	url=findConfig("sea_url");
			String	username=findConfig("SEA_USERNAME");
			String	password=findConfig("SEA_PASSWORD");
			Client client = new Client(new URL(url));
			client.addOutHandler(new ClientAuthenticationHandler(username,
					password));
		
			Object result[] = client.invoke("WS_ZZ_SYNDATA", new Object[] {xsu.switchListToXML(list)});
			String resultXml=result[0].toString();
			List rlist=xsu.switchXMLToList(resultXml);
			if(null==rlist||rlist.isEmpty())
			{
				return null;
			}else{
				HashMap rm=new HashMap();
				for(Object m:rlist){
					Map mm=(Map) m;
					rm.put(mm.get("CONS_ID"), mm.get("RLT_FLAG"));
				}
				return rm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		
	}
	private String findConfig(String no) throws ServiceException{
		SqlData sd=SqlData.getOne();
		try {
			LeftTreeDaoImpl tree = new LeftTreeDaoImpl();
			return	 (String) tree.getJdbcTemplate().queryForObject(sd.archives_config,new Object[]{no}, String.class);
		} catch (Exception e) {
			return "";
		}
	}
	
}
