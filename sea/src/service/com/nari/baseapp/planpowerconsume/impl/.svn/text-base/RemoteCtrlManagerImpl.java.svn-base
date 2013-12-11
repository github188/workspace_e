package com.nari.baseapp.planpowerconsume.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.baseapp.planpowerconsume.IRemoteControlJdbcDao;
import com.nari.baseapp.planpowerconsume.IWCtrlSchemeDao;
import com.nari.baseapp.planpowerconsume.RemoteCtrlManager;
import com.nari.baseapp.planpowerconsume.WtmnlCtrlStatusManger;
import com.nari.basicdata.VwLimitType;
import com.nari.basicdata.VwLimitTypeJdbcDao;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.DataCode;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.RemoteControlDto;
import com.nari.orderlypower.RmtCtrlSwitchDto;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.orderlypower.WTmnlCtrlStatus;
import com.nari.orderlypower.WTmnlTurn;
import com.nari.privilige.PSysUser;
import com.nari.support.PropertyFilter;
import com.nari.sysman.securityman.IPSysUserDao;
import com.nari.util.exception.DBAccessException;

/**
 * 遥控业务层接口实现类
 * @author 姜炜超
 */
public class RemoteCtrlManagerImpl implements RemoteCtrlManager{
	public final static String LOAD_UI_GRID="ui";//遥测电压电流标记
	public final static String LOAD_POWER_GRID="power";//遥测功率标记
	public final static String LOAD_CTRL_GRID="ctrl";//遥测开关拉合闸标记
	
	private IWCtrlSchemeDao iWCtrlSchemeDao;//方案控制Dao处理对象
	private VwLimitTypeJdbcDao vwLimitTypeJdbcDao;//限电类型Dao处理对象
	private IRemoteControlJdbcDao iRemoteControlJdbcDao;//遥控jdbc处理对象
	private IPSysUserDao iPSysUserDao; //操作员Dao处理对象
	
	private ILOpTmnlLogManager iLOpTmnlLogManager;//自动注入日志处理业务层 
	private WtmnlCtrlStatusManger wtmnlCtrlStatusManger;//记录终端实时状态业务服务
	
	public void setiWCtrlSchemeDao(IWCtrlSchemeDao iWCtrlSchemeDao) {
		this.iWCtrlSchemeDao = iWCtrlSchemeDao;
	}
	public void setVwLimitTypeJdbcDao(VwLimitTypeJdbcDao vwLimitTypeJdbcDao) {
		this.vwLimitTypeJdbcDao = vwLimitTypeJdbcDao;
	}
	public void setiRemoteControlJdbcDao(IRemoteControlJdbcDao iRemoteControlJdbcDao) {
		this.iRemoteControlJdbcDao = iRemoteControlJdbcDao;
	}
	public void setiPSysUserDao(IPSysUserDao iPSysUserDao) {
		this.iPSysUserDao = iPSysUserDao;
	}
	
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	public void setWtmnlCtrlStatusManger(WtmnlCtrlStatusManger wtmnlCtrlStatusManger) {
		this.wtmnlCtrlStatusManger = wtmnlCtrlStatusManger;
	}
	/**
	 * 查询方案类型
	 * @param schemeName
	 * @return String
	 * @throws DBAccessException 数据库异常
	 */
	public String querySchemeType(String schemeName)throws DBAccessException{
		if(null == schemeName){
			return null;
		}
//		List<VwCtrlSchemeType> ctrlSchemeTypeList = vwCtrlSchemeTypeDao.findAll();
//		if(null == ctrlSchemeTypeList){
//			return null;
//		}		
//		for(int i = 0; i < ctrlSchemeTypeList.size(); i++){
//			VwCtrlSchemeType type = (VwCtrlSchemeType)ctrlSchemeTypeList.get(i);
//			if(null == type || null == type.getId()){
//			    continue;
//			}
//		}
		return "05";
	}
	
	/**
	 * 主要用来查询某种类型的所有方案
	 * @param schemeType
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByType(String schemeType, String staffNo)throws DBAccessException{
		if(null == schemeType || "".equals(schemeType)){
			return null;
		}
		return iWCtrlSchemeDao.findBySchemeTypeAndStaffNo(schemeType, staffNo);
	}
	
	/**
	 * 新增或修改方案，针对新增加方案的业务处理，主要是写入方案主表和遥控表
	 * @param scheme
	 * @param turnList
	 * @return 方案列表
	 * @throws DBAccessException 数据库异常
	 */
	public void saveOrUpdateScheme(WCtrlScheme scheme, List<WTmnlTurn> turnList) throws DBAccessException{
		if(null == scheme || null == turnList || 0 >= turnList.size()){
			return;
		}
		if(scheme.getCtrlSchemeId() ==null && scheme.getCtrlSchemeName() !=null) {
			iWCtrlSchemeDao.saveOrUpdate(scheme);//新增方案
			
			//获取id后循环写入遥控明细信息表
			for(int i = 0 ; i < turnList.size(); i++){
				WTmnlTurn turn = (WTmnlTurn)turnList.get(i);
				turn.setCtrlSchemeId(scheme.getCtrlSchemeId());
				try {
					iRemoteControlJdbcDao.saveOrUpdateByScheme(turn);
				} catch (Exception e) {
					throw new DBAccessException();
				}
			}
		} else if(scheme.getCtrlSchemeId() !=null) {
			//先清除原有方案明细
			iRemoteControlJdbcDao.deleteBySchemeId(scheme.getCtrlSchemeId());
			
			iWCtrlSchemeDao.saveOrUpdate(scheme);//修改方案
			
			//获取id后循环写入遥控明细信息表
			for(int i = 0 ; i < turnList.size(); i++){
				WTmnlTurn turn = (WTmnlTurn)turnList.get(i);
				turn.setCtrlSchemeId(scheme.getCtrlSchemeId());
				try {
					iRemoteControlJdbcDao.saveOrUpdateByScheme(turn);
				} catch (Exception e) {
					throw new DBAccessException();
				}
			}
		}
	}
	
	/**
	 * 查询限电类型
	 * @param
	 * @return List<VwLimitType>
	 * @throws DBAccessException 数据库异常
	 */
	public List<VwLimitType> queryLimitTypeList()throws DBAccessException{
		return	vwLimitTypeJdbcDao.findAll();
	}
	
	/**
	 * 根据供电单位编号查询遥控Grid数据分页列表
	 * @param orgNo
	 * @param start
	 * @param limit 
	 * @return Page<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridByOrgNo(String orgNo, String orgType, PSysUser user, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoByOrgNo(orgNo,orgType,user, flag);
		return psc;
	}
	
	/**
	 * 根据客户编号查询遥控Grid数据分页列表
	 * @param consNo
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridByTmnlAssetNo(String tmnlAssetNo, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoByTmnlAssetNo(tmnlAssetNo, flag);
		return psc;
	}
	
	/**
	 * 根据线路Id查询遥控Grid数据分页列表
	 * @param lineId
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridByLineId(String lineId, PSysUser user, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoByLineId(lineId,user, flag);
		return psc;
	}
	
	/**
	 * 根据组号查询遥控Grid数据分页列表
	 * @param groupNo
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridByGroupNo(String groupNo, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoByGroupNo(groupNo, flag);
		return psc;
	}
	
	/**
	 * 根据普通组号查询遥控Grid数据分页列表
	 * @param groupNo
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridByUgpGroupNo(String groupNo, String flag) {
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoByUgpGroupNo(groupNo, flag);
		return psc;
	}
	/**
	 * 根据变电站标识查询遥控Grid数据分页列表
	 * @param subsId
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridBySubsId(String subsId, PSysUser user, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoBySubsId(subsId,user, flag);
		return psc;
	}
	
	/**
	 * 根据方案号查询遥控Grid数据分页列表
	 * @param schemeNo
	 * @param start
	 * @param limit 
	 * @return List<RemoteControlDto>
	 * @throws DBAccessException 数据库异常
	 */
	@Override
	public List<RemoteControlDto> queryRemoteCtrlGridBySchemeNo(Long schemeNo, String flag)throws DBAccessException{
		List<RemoteControlDto> psc = iRemoteControlJdbcDao.findUserInfoBySchemeNo(schemeNo, flag);
		return psc;
	}
		
	/**
	 * 更新遥控方案，用于操作后对表的某些字段ctrlFlag,sendTime的修改（投入、解除）
	 * @param turnList
	 * @return 
	 * @throws DBAccessException 数据库异常
	 */
	public void updateTurn(WTmnlTurn turn) throws DBAccessException{
		iRemoteControlJdbcDao.updateTurn(turn);
	}
	
	/**
	 * 根据过滤条件查询方案
	 * @filters 
	 * @return List<WCtrlScheme>
	 * @throws DBAccessException 数据库异常
	 */
	public List<WCtrlScheme> querySchemeListByCond(List<PropertyFilter> filters) throws DBAccessException{
		if(null != filters && 0 < filters.size()){
			return iWCtrlSchemeDao.findBy(filters);
		}
		return null;
	}
	
	/**
	 * 根据工号查询其供电单位
	 * @param staffNo
	 * @return String
	 */
    public String findOrgNoByStaffNo(String staffNo) throws DBAccessException {
    	PSysUser user = null;
    	if(null != staffNo && !"".equals(staffNo)){
    		user = iPSysUserDao.findByStaffNo(staffNo);
    	}
    	if(null != user){
    		return user.getOrgNo();
    	}
    	return "";
    }
    
	@Override
	public List<String> findProItemNo(String turnType)  throws DBAccessException {
			return iRemoteControlJdbcDao.findProItemNo(turnType);
	}
	
	@Override
	public List<RmtCtrlSwitchDto> fetchSwitchStatus(List<CtrlParamBean> tmnlList, int second)
			throws DBAccessException {
		List<RmtCtrlSwitchDto> rtSwitchList= new ArrayList<RmtCtrlSwitchDto>();
		TaskDeal taskDeal = new TaskDeal();//生成请求对象
		if(tmnlList==null || tmnlList.size()<=0){
			return rtSwitchList;
		}
		
		rtSwitchList = new ArrayList<RmtCtrlSwitchDto>();
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);	
			
//			终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(bean.getTmnlAssetNo());
						
			//参数项设置
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			
			RealDataItem ritem = null;
			ArrayList<Item> codes = null;
			Item item = null;
			ritem = new RealDataItem();
			codes = new ArrayList<Item>();
			
			List<String> proItemNo = findProItemNo(LOAD_CTRL_GRID);
			for (int i = 0; i < proItemNo.size(); i++) {
				item = new Item(proItemNo.get(i));
				codes.add(item);
			}
			
			ritem.setCodes(codes);
			ritem.setPoint((short)0);
			
			realDataItems.add(ritem);
			//参数下发
			taskDeal.callRealData(tmnlAssetNoList, realDataItems);
		}
		
		//获取结果
		List<Response> list = taskDeal.getAllResponse(second);
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {//循环response list 最外层循环
				Response res = (Response) list.get(i);
				List<DbData> datas = res.getDbDatas();
				if(datas!= null && datas.size()>0){
					Short Status = res.getTaskStatus();
					//TODO 如何判断返回成功res.getErrorCode()
					if (Status == 3) {//返回状态为成功时，设置值返回页面
						for (int j = 0; j < datas.size(); j++) {//循环Fn， 在召测参数list中有几个Fn，res.getDbDatas()的长度就为几
							List<DataCode> dataCodes = datas.get(j).getDataCodes();
							if(dataCodes!= null && dataCodes.size()>0) {
								RmtCtrlSwitchDto rtSwitch = new RmtCtrlSwitchDto();
								rtSwitch.setStatusCode(Status.toString());
								rtSwitch.setTmnlAssetNo(res.getTmnlAssetNo());
								for (int k = 0; k < dataCodes.size(); k++) {//Fn一类数据召回的是全部数据，循环所有数据项，取得需要数据
									DataCode dataCode = dataCodes.get(k);
									for (int l = 0; l < 8; l++) {//8个儿开关分别设置
										char c = dataCode.getValue().toString().charAt(l);
										String stat = "分";
										if(c=='1'){
											stat = "合";
										}
										if(l==0) {
											rtSwitch.setSwitch1(stat);
										} else if(l==1) {
											rtSwitch.setSwitch2(stat);
										} else if(l==2) {
											rtSwitch.setSwitch3(stat);
										} else if(l==3) {
											rtSwitch.setSwitch4(stat);
										} else if(l==4) {
											rtSwitch.setSwitch5(stat);
										} else if(l==5) {
											rtSwitch.setSwitch6(stat);
										} else if(l==6) {
											rtSwitch.setSwitch7(stat);	
										} else if(l==7) {
											rtSwitch.setSwitch8(stat);	
										}
									}
								}
								rtSwitchList.add(rtSwitch);
							}
						}
					} else{
						RmtCtrlSwitchDto rtSwitch = new RmtCtrlSwitchDto();
						rtSwitch.setTmnlAssetNo(res.getTmnlAssetNo());
						rtSwitch.setStatusCode(Status.toString());
						rtSwitchList.add(rtSwitch);
					}
				}
			}
		}
		return rtSwitchList;
	}
	
	@Override
	public List<RemoteControlDto> remoteCtrlClose(List<CtrlParamBean> tmnlList,
			int second, PSysUser user, String turnFlag, short alertDelayHours, short limitMins, String localIp) throws DBAccessException {
		List<RemoteControlDto> resultList = new ArrayList<RemoteControlDto>();
		if(tmnlList==null || tmnlList.size()<=0){
			return resultList;
		}
		//取轮次
		String[] rounds = turnFlag.split(",");
		if(null == rounds || 0 >= rounds.length){
			return resultList;
		}
		TaskDeal taskDeal = new TaskDeal();//生成请求对象		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(bean.getTmnlAssetNo());
			
			//参数项设置，编码对象集合，取规约项时，1位表示规约，2-3位表示AFN，4-5位表示FN
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = null;
			for (int i = 0; i < rounds.length; i++) {
				pitem = new ParamItem();
				pitem.setFn((short) 1);//设置FN号
				pitem.setPoint(Short.parseShort(rounds[i]));
				List<Item> items = new ArrayList<Item>();
				Item item = null;
				item = new Item(bean.getProtocolCode()+"0501001");
				item.setValue(String.valueOf(alertDelayHours));//控制延时时间
				items.add(item);
				
				item = new Item(bean.getProtocolCode()+"0501002");
				item.setValue(String.valueOf(limitMins));//限电时间
				items.add(item);
				
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
			}
			
			//调用接口
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
		}	
		//获取结果
		List<Response> list = taskDeal.getAllResponse(second);
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				RemoteControlDto rc = new RemoteControlDto();
				Response res = (Response) list.get(i);
				Short Status = res.getTaskStatus();
				rc.setTmnlAssetNo(res.getTmnlAssetNo());
				rc.setStatusCode(Status.toString());

				/* 记录日志，修改终端实时状态 */
				for (int n = 0; n < tmnlList.size(); n++) {
					CtrlParamBean bean = tmnlList.get(n);
					if (bean.getTmnlAssetNo().equals(res.getTmnlAssetNo())) {
						// 写日志
						LOpTmnlLog l = new LOpTmnlLog();
						l.setOrgNo(user.getOrgNo());
						l.setEmpNo(user.getStaffNo());
						l.setIpAddr(localIp);
						l.setOpModule("遥控");
						l.setOpButton("拉闸");
						l.setOpType((short)2);//控制下发
						l.setTmnlAssetNo(res.getTmnlAssetNo());
						l.setOpTime(new Date());
						l.setOpObj((short)6);//控制轮次
						l.setCurStatus(Status.toString());//下发返回状态
						for (int j = 0; j < rounds.length; j++) {
							l.setOpObjNo((long)j);//轮次号
							
							l.setProtItemNo(bean.getProtocolCode() + "0501001");// 警告延时时间记录日志
							l.setCurValue(Short.toString(alertDelayHours));
							iLOpTmnlLogManager.saveTmnlLog(l);

							l.setProtItemNo(bean.getProtocolCode()+ "0501002");// 限电时间记录日志
							l.setCurValue(Short.toString(limitMins));
							iLOpTmnlLogManager.saveTmnlLog(l);
						}

						// 对控制成功的终端，修改或保存终端实时状态
						//TODO 如何判断返回成功res.getErrorCode()
						if (Status == 3) {
							// 创建终端实时状态Bean,并对各控制状态共属性赋值
							WTmnlCtrlStatus ctrlStatus = wtmnlCtrlStatusManger.createWTmnlCtrlStatus(bean);

							ctrlStatus.setTurnFlag((short) 1);// 拉闸设置成1
							wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusTurnFlag(ctrlStatus);// 修改终端实时状态
							rc.setExecFlag("1");//拉闸成功
						} else {
							rc.setExecFlag("-1");//失败
						}
						resultList.add(rc);
						break;
					}
				}
			}
		} else {
			for (int n = 0; n < tmnlList.size(); n++) {//若通信出错，返回response list 为空，则返回全部失败
				CtrlParamBean bean = tmnlList.get(n);
				RemoteControlDto rc = new RemoteControlDto();
				rc.setTmnlAssetNo(bean.getTmnlAssetNo());
				rc.setCtrlFlag("-1");//失败
				
				resultList.add(rc);
			}
		}
		return resultList;
	}
	
	@Override
	public List<RemoteControlDto> remoteCtrlOpen(List<CtrlParamBean> tmnlList,
			int second, PSysUser user, String turnFlag, String localIp) throws DBAccessException {
		List<RemoteControlDto> resultList = new ArrayList<RemoteControlDto>();
		//取轮次
		String[] rounds = turnFlag.split(",");
		if(null == rounds || 0 >= rounds.length){
			return resultList;
		}
		
		if(tmnlList==null || tmnlList.size()<=0){
			return resultList;
		}
		TaskDeal taskDeal = new TaskDeal();//生成请求对象
		
		for (int n = 0; n < tmnlList.size(); n++) {
			CtrlParamBean bean = tmnlList.get(n);
			
//			终端资产号集合
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(bean.getTmnlAssetNo());
			
			//参数项设置，编码对象集合，取规约项时，1位表示规约，2-3位表示AFN，4-5位表示FN
			List<ParamItem> paramList = new ArrayList<ParamItem>();
			ParamItem pitem = null;
			for (int i = 0; i < rounds.length; i++) {
				pitem = new ParamItem();
				pitem.setFn((short) 2);//设置FN号
				pitem.setPoint(Short.parseShort(rounds[i]));
		
				pitem.setItems(new ArrayList<Item>());
				paramList.add(pitem);
			}
			
			//参数下发
		   taskDeal.qstTermParamTaskResult(tmnlAssetNoList, FrontConstant.CONTROL_COMMAND, paramList);
		}
		//获取下发结果
		List<Response> list = taskDeal.getAllResponse(second);
		if (null != list && 0 < list.size()) {
			for (int i = 0; i < list.size(); i++) {
				RemoteControlDto	rc = new RemoteControlDto();
				Response res = (Response) list.get(i);
				rc.setTmnlAssetNo(res.getTmnlAssetNo());
				Short Status = res.getTaskStatus();
				rc.setStatusCode(Status.toString());
				
				/* 记录日志，修改终端实时状态 */
				for (int n = 0; n < tmnlList.size(); n++) {
					CtrlParamBean bean = tmnlList.get(n);
					if (bean.getTmnlAssetNo().equals(res.getTmnlAssetNo())) {
						// 写日志
						LOpTmnlLog l = new LOpTmnlLog();
						l.setOrgNo(user.getOrgNo());
						l.setEmpNo(user.getStaffNo());
						l.setIpAddr(localIp);
						l.setOpModule("遥控");
						l.setOpButton("合闸");
						l.setOpType((short) 2);//控制下发
						l.setTmnlAssetNo(bean.getTmnlAssetNo());
						l.setOpTime(new Date());

						l.setProtocolNo(bean.getProtocolCode() + "0502");
						l.setOpObj((short)0);
						l.setCurStatus(Status.toString());
						this.iLOpTmnlLogManager.saveTmnlLog(l);
						//TODO 如何判断返回成功res.getErrorCode()
						if (Status == 3) {
							// 创建终端实时状态Bean,并对各控制状态共属性赋值
							WTmnlCtrlStatus ctrlStatus = wtmnlCtrlStatusManger.createWTmnlCtrlStatus(bean);

							ctrlStatus.setTurnFlag((short) 0);// 合闸设置成0
							wtmnlCtrlStatusManger.saveOrUpdateTmnlCtrlStatusTurnFlag(ctrlStatus);// 修改终端实时状态
							 rc.setExecFlag("0");//合闸成功
						} else {
							 rc.setExecFlag("-1");//失败
						}
						resultList.add(rc);
						break;
					}
				}
			}
		} else {
			for (int n = 0; n < tmnlList.size(); n++) {//若通信出错，返回response list 为空，则返回全部失败
				CtrlParamBean bean = tmnlList.get(n);
				RemoteControlDto rc = new RemoteControlDto();
				rc.setTmnlAssetNo(bean.getTmnlAssetNo());
				rc.setCtrlFlag("-1");//失败
				
				resultList.add(rc);
			}
		}
		return resultList;
	}
}
