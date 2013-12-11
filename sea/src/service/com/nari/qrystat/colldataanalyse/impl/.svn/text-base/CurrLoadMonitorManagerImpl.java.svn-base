package com.nari.qrystat.colldataanalyse.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.AOrgLoadStatDto;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorCurveBean;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorDto;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorJdbcDao;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorManager;
import com.nari.util.ArithmeticUtil;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 日用电负荷监测实现类
 * @author 姜炜超
 */
public class CurrLoadMonitorManagerImpl implements CurrLoadMonitorManager{
	private CurrLoadMonitorJdbcDao currLoadMonitorJdbcDao;//日用电负荷监测Dao对象
	
	public CurrLoadMonitorJdbcDao getCurrLoadMonitorJdbcDao() {
		return currLoadMonitorJdbcDao;
	}

	public void setCurrLoadMonitorJdbcDao(
			CurrLoadMonitorJdbcDao currLoadMonitorJdbcDao) {
		this.currLoadMonitorJdbcDao = currLoadMonitorJdbcDao;
	}

	/**
	 * 根据条件查询实时日用电负荷监测信息，用于grid显示
	 * @param list
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findCLMGridInfoByCond(List<CurrLoadMonitorDto> list, String queryDate, PSysUser pSysUser) throws Exception{
		if(null == list || 0 >= list.size()){
			return new ArrayList<CurrLoadMonitorDto>();
		}
		List<CurrLoadMonitorDto> gridList = new ArrayList<CurrLoadMonitorDto>();//定义返回列表
		try {
			for(int i = 0 ; i < list.size(); i++){
				String orgNo = ((CurrLoadMonitorDto)list.get(i)).getOrgNo();//供电单位编号
				String orgType = ((CurrLoadMonitorDto)list.get(i)).getOrgType();//供电单位类别
				String orgName = ((CurrLoadMonitorDto)list.get(i)).getOrgName();//供电单位名称
				
				//查询结果
				List<CurrLoadMonitorCurveBean> listDto = currLoadMonitorJdbcDao.findCLMInfoByCond(orgNo, orgType, queryDate, pSysUser);
				
				CurrLoadMonitorDto resultBean = new CurrLoadMonitorDto();//定义返回列表中的每个bean
				resultBean.setOrgNo(orgNo);
				resultBean.setOrgType(orgType);
				resultBean.setOrgName(orgName);
				
				if(null == listDto || 0 >= listDto.size()){
					gridList.add(resultBean);//保证无数据，也显示供电单位
					continue;
				}
				
				CurrLoadMonitorCurveBean tempBean = null;//查询结果有n条，临时获取bean
				CurrLoadMonitorCurveBean firstBean = (CurrLoadMonitorCurveBean)listDto.get(0);//第一条记录，用于获取初始值
				
				//查询出的数据只有两个字段，正常情况下有24条记录，并分别在dao中处理时把数据放入maxp和maxptime
				Double maxp = firstBean.getData();//峰值
				Double minp = firstBean.getData();//谷值
				String maxpTime = firstBean.getTime();//峰值时间
				String minpTime = firstBean.getTime();//谷值时间
				
				Double sump = (null != firstBean.getData() ? firstBean.getData() : 0.0);//合计数据
				
				//循环获取最大最小值和合计数据
				for(int j = 1; j < listDto.size(); j++){
					tempBean = (CurrLoadMonitorCurveBean)listDto.get(j);
					if(null == tempBean.getData()){
						continue;
					}
					if(null == maxp || null == minp){//主要是针对第一次赋值如果为空的情况
						maxp = tempBean.getData();
						minp = tempBean.getData();
						maxpTime = tempBean.getTime();
						minpTime = tempBean.getTime();
						continue;
					}
					if(tempBean.getData().doubleValue() > maxp){
						maxp = tempBean.getData();
						maxpTime = tempBean.getTime();
					}
					if(tempBean.getData().doubleValue() < minp){
						minp = tempBean.getData();
						minpTime = tempBean.getTime();
					}
					sump = ArithmeticUtil.add(sump, tempBean.getData());
				}

				if(null == maxp || null == minp){
					resultBean.setMidp(null);//平均负荷
					resultBean.setMmr(null);//峰谷比
					resultBean.setMmsr(null);//峰谷差率
					resultBean.setPr(null);//负荷率
					resultBean.setMaxp(null);//最大值
					resultBean.setMinp(null);//最小值
					resultBean.setMaxpTime(null);//最大值时间
					resultBean.setMinpTime(null);//最小值时间
				}else{
					resultBean.setMaxp(ArithmeticUtil.round(ArithmeticUtil.div(maxp, 10000.0,4),2));//最大值
					resultBean.setMinp(ArithmeticUtil.round(ArithmeticUtil.div(minp, 10000.0,4),2));//最小值
					resultBean.setMaxpTime(maxpTime);//最大值时间
					resultBean.setMinpTime(minpTime);//最小值时间
					Double midp = ArithmeticUtil.round(ArithmeticUtil.div(sump, 24.0,4),2);
		            resultBean.setMidp(ArithmeticUtil.round(ArithmeticUtil.div(midp, 10000.0,4),2));//平均负荷
		            if(0 != minp){
						resultBean.setMmr(ArithmeticUtil.round(ArithmeticUtil.div(maxp, minp ,4),2));//峰谷比
					}else{
						resultBean.setMmr(null);
					}
					if(0 != maxp){
						//峰谷差率=峰值-谷值/峰值
						resultBean.setMmsr(ArithmeticUtil.round(100*ArithmeticUtil.div(ArithmeticUtil.sub(maxp, minp), maxp,4),2));
						resultBean.setPr(ArithmeticUtil.round(100*ArithmeticUtil.div(midp, maxp,4),2));//负荷率=平均负荷/峰值
					}
					else{
						resultBean.setMmsr(null);
						resultBean.setPr(null);
					}
				}
				gridList.add(resultBean);
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CURRLOADMONITORGRIDINFO);
		}
		
		return gridList; 
	}
	
	/**
	 * 根据条件查询实时日用电负荷监测信息，针对曲线
	 * @param bean
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMRealCurInfoByCond(CurrLoadMonitorDto bean, String queryDate, PSysUser pSysUser) throws Exception{
		if(null == bean || null == bean.getOrgNo() || null == bean.getOrgType() || "".equals(bean.getOrgNo()) || "".equals(bean.getOrgType())){
			return new ArrayList<CurrLoadMonitorCurveBean>();
		}
		List<CurrLoadMonitorCurveBean> resultList = new ArrayList<CurrLoadMonitorCurveBean>();//返回list
		
		String [] timeStr = {"00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00", "06:00:00", "07:00:00",
				"08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00",
				"16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00"};
		
		try {
			List<CurrLoadMonitorCurveBean> list = currLoadMonitorJdbcDao.findCLMInfoByCond(bean.getOrgNo(), bean.getOrgType(), queryDate, pSysUser);

			if(null == list || 0 == list.size()){//查询没有值，必须保证能显示，可以无曲线
				for(int i = 0; i < timeStr.length ; i++){//保证24个点，都能显示
					CurrLoadMonitorCurveBean tempDto = new CurrLoadMonitorCurveBean();
					tempDto.setData(0.0);
					tempDto.setFlag(true);//true表示不显示
					if(i < 10){
						tempDto.setTime(timeStr[i].substring(1,2));
					}else{
						tempDto.setTime(timeStr[i].substring(0,2));
					}
					resultList.add(tempDto);	
				}
			}else{//保证出现漏点数据，也可以正常显示，如果时间有对应的，则把临时bean中的数据解析到返回列表对应的类中
				for(int i = 0; i < timeStr.length ; i++){//保证24个点，都能显示
					CurrLoadMonitorCurveBean tempDto = null;//塞入返回list的bean
					for(int j = 0; j < list.size(); j++){//查询返回结果可能只有几条
						CurrLoadMonitorCurveBean retDto = (CurrLoadMonitorCurveBean)list.get(j);
						if(timeStr[i].equals(retDto.getTime())){//防止时间点不对
							tempDto = new CurrLoadMonitorCurveBean();
							if(null == retDto.getData()){//true表示不显示
								tempDto.setFlag(true);
								tempDto.setData(0.0);
							}else{
							    tempDto.setData(ArithmeticUtil.round(ArithmeticUtil.div(retDto.getData(), 10000.0 ,4),2));
							    tempDto.setFlag(false);
							}
							if(i < 10){//截取时间点
								tempDto.setTime(timeStr[i].substring(1,2));
							}else{
								tempDto.setTime(timeStr[i].substring(0,2));
							}
							break;//如果有时间点一样的，则退出本次循环，进入下一个时间点
						}
					}
					if(null == tempDto){//如果时间点不对或漏点了，处理如下
						tempDto = new CurrLoadMonitorCurveBean();
						tempDto.setData(0.0);
						tempDto.setFlag(true);//true表示不显示
						if(i < 10){
							tempDto.setTime(timeStr[i].substring(1,2));
						}else{
							tempDto.setTime(timeStr[i].substring(0,2));
						}
					}
					resultList.add(tempDto);
				}
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CLMCURVEINFO);
		}
		
		return resultList;
	}
	
	/**
	 * 根据条件查询冻结日用电负荷监测信息，针对曲线
	 * @param bean
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMVSTATCurInfoByCond(CurrLoadMonitorDto bean, String queryDate, PSysUser pSysUser) throws Exception{
		if(null == bean || null == bean.getOrgNo() || null == bean.getOrgType() || "".equals(bean.getOrgNo()) || "".equals(bean.getOrgType())){
			return new ArrayList<CurrLoadMonitorCurveBean>();
		}
		List<CurrLoadMonitorCurveBean> resultList = new ArrayList<CurrLoadMonitorCurveBean>();//定义返回list
		
		String [] timeStr = {"00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00", "06:00:00", "07:00:00",
				"08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00",
				"16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00"};
		
		try {
			List<AOrgLoadStatDto> list = currLoadMonitorJdbcDao.findCLMLSTATInfoByCond(bean.getOrgNo(), bean.getOrgType(), queryDate, pSysUser);
			
			if(null == list || 0 == list.size()){//查询没有值，必须保证能显示，可以无曲线
				for(int i = 0; i < timeStr.length ; i++){//保证24个点，都能显示
					CurrLoadMonitorCurveBean tempDto = new CurrLoadMonitorCurveBean();
					tempDto.setData(0.0);
					tempDto.setFlag(true);//true表示不显示
					if(i < 10){
						tempDto.setTime(timeStr[i].substring(1,2));
					}else{
						tempDto.setTime(timeStr[i].substring(0,2));
					}
					resultList.add(tempDto);	
				}
			}else{//保证出现漏点数据，也可以正常显示，把一个类中的数据转换到不同的类中，比如有24个点，就拆分成24个类，并放入list中
				AOrgLoadStatDto retDto = (AOrgLoadStatDto)list.get(0);
				Method[] m = retDto.getClass().getDeclaredMethods();
				String getStr = "H";
				int i = 0;
				int n = 0;
				while(i < m.length){
					if(!m[i].getName().startsWith("get"+getStr)){//跳过供电单位
						i++; 
						continue;
					}
					CurrLoadMonitorCurveBean tempDto = new CurrLoadMonitorCurveBean();
					if(n < 10){//界面显示需要取数字显示
					    tempDto.setTime(timeStr[n].substring(1,2));
					}else{
						tempDto.setTime(timeStr[n].substring(0,2));
					}
					//设置值
				    tempDto.setData(new Double(m[i].invoke(retDto, null)==null?"0.0":m[i].invoke(retDto, null).toString()));
				    
				    if(null != tempDto.getData()){
				    	tempDto.setData(ArithmeticUtil.round(ArithmeticUtil.div(tempDto.getData(), 10000.0 ,4),2));
				    }
				    
				    if(m[i].invoke(retDto, null)==null){
				    	tempDto.setFlag(true);
				    }else{
				    	tempDto.setFlag(false);
				    }
					resultList.add(tempDto);
					i++;
					n++;
					continue;   	
			    }
			}
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_CLMVSTATCURVEINFO);
		}
		
		return resultList;
	}
	
	
	/**
	 * 根据供电单位查询该单位及子单位信息，点击左边树响应
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @return List<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findOrgInfo(String orgNo, String orgType, PSysUser pSysUser) throws Exception{
		try {
			return currLoadMonitorJdbcDao.findOrgInfo(orgNo, orgType, pSysUser);
		} catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_FINDORGINFO);
		}
	}
	
    /**
	 * 查询时间模板，为生成曲线而用，固定24个点
	 * @return 
	 */
	public List<CurrLoadMonitorDto> getTimeModelList(){
		List<CurrLoadMonitorDto> list = new ArrayList<CurrLoadMonitorDto>();
		String [] timeStr = {"00:00:00", "01:00:00", "02:00:00", "03:00:00", "04:00:00", "05:00:00", "06:00:00", "07:00:00",
				"08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00",
				"16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00", "21:00:00", "22:00:00", "23:00:00"};
		//应设计要求，显示的曲线x标为时间点
		for(int i = 0; i < timeStr.length; i++){
			CurrLoadMonitorDto tempDto = new CurrLoadMonitorDto();
			if(i < 10){
				tempDto.setMaxpTime(timeStr[i].substring(1,2));
			}else{
				tempDto.setMaxpTime(timeStr[i].substring(0,2));
			}
			list.add(tempDto);
		}
		return list;
	}
}
