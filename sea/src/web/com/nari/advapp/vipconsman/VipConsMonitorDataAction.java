package com.nari.advapp.vipconsman;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Constans;
import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.GeneralDataBean;
import com.nari.support.Page;
import com.nari.util.ArithmeticUtil;
import com.nari.util.DateUtil;

/**
 * 重点用户监测数据查询Action
 * @author 姜炜超
 */
public class VipConsMonitorDataAction extends BaseAction {
	//定义日志
	private static final Logger logger = Logger.getLogger(VipConsMonitorDataAction.class);
	
	//自动注入重点用户监测数据业务层 
	private VipConsMonitorDataManager vipConsMonitorDataManager;
	
	//成功返回标记
	public boolean success = true;

	//分页总条数
	public long totalCount;
	
	//分页入参
	public long start = 0;
	public int limit = Constans.DEFAULT_PAGE_SIZE;
	private List<VipConsMonitorDataBean> dataList;//重点户
	
	private boolean realFlag;//实时标记
	private boolean statFlag;//冻结标记
	private String assetNo;//表计编号
	private Date date;//查询日期
	private String consNo;//客户编号
	private List<GeneralDataBean> timeModelList;//仅仅作为显示时间而存在
	private List<VipConsStatDataBean> statPowerBean;//冻结负荷数据
	private List<VipConsRealPowerDataBean> realPowerBean;//冻结实时数据
	private String realPowerName;//实时负荷数据命名
	private Double minValue = 0.0;//最小值，用于曲线坐标定位
	private Double maxValue = 0.0;//最大值，用于曲线坐标定位
	private List<EDataMp> assetList;//表计列表
	private List<VipConsPowerCurveDataBean> powerCurveList = new ArrayList<VipConsPowerCurveDataBean>();//grid显示列表
	
	private List<VipConsStatDataBean> energyBean;//电能量数据
	private List<VipConsEnergyCurveDataBean> energyCurveList = new ArrayList<VipConsEnergyCurveDataBean>();
	/**
	 * 查询重点用户监测数据
	 * @return String 
	 * @throws Exception
	 */
    public String queryGridData() throws Exception{
    	logger.debug("查询重点用户监测数据开始");
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	
    	Page<VipConsMonitorDataBean> psc = null;
    	    	
    	psc = vipConsMonitorDataManager.findVipConsMonitorData(pSysUser, start, limit);
    	dataList = psc.getResult();
    	totalCount = psc.getTotalCount();
    	logger.debug("查询重点用户监测数据结束");
    	return SUCCESS;
    }
    
    /**
	 * 查询表计信息
	 * @return
	 * @throws Exception
	 */
	public String queryAssetData() throws Exception {
		logger.debug("查询重点用户监测表计信息开始");
		if(null == consNo){
			return SUCCESS;
		}
		assetList = vipConsMonitorDataManager.findConsAssetInfo(consNo);
		logger.debug("查询重点用户监测表计信息结束");
		return SUCCESS;
	}
    
	/**
	 * 查询负荷数据
	 * @return
	 * @throws Exception
	 */
	public String queryLoadData() throws Exception {
		logger.debug("查询重点用户监测负荷数据开始");
		if(null == assetNo){
			return SUCCESS;
		}
		int freezeCycleNum = vipConsMonitorDataManager.findFreezeCycleNum(assetNo);
		timeModelList = vipConsMonitorDataManager.getTimeModelList(freezeCycleNum);
		List<Double[]> list = new ArrayList<Double[]>();
		
		if(statFlag){
		    statPowerBean = vipConsMonitorDataManager.findMpStatPowerData(assetNo, DateUtil.dateToString(date),
				consNo, freezeCycleNum);
		    if(null != statPowerBean){
		    	for(int i = 0; i < statPowerBean.size(); i++){
		    		VipConsStatDataBean bean = (VipConsStatDataBean)statPowerBean.get(i);
				    if(bean.getCurveFlag()){//true表示漏点，没采集到数据
				    	continue;
				    }else{
			    		//获取最大值和最小值
						if(!isStatNullValue(bean.getCurveList())){
				        	list.add(checkStatMaxMinValue(bean.getCurveList()));
				        }
					}
		    	}
		    }
		}
		if(realFlag){
		    realPowerBean = vipConsMonitorDataManager.findMpRealPowerData(assetNo, DateUtil.dateToString(date),
				consNo, freezeCycleNum);
			//获取最大值和最小值
	        if(!isRealPowerNullValue(realPowerBean)){
	        	list.add(checkRealPowerMaxMinValue(realPowerBean));
	        }
		}
		if(null != realPowerBean && 0 < realPowerBean.size()){
			realPowerName = "实时功率";
		}
		
		//获取最大值和最小值
		int j = list.size();
		if(j > 0){
			this.maxValue = ((Double[])list.get(0))[0];
			this.minValue = ((Double[])list.get(0))[1];
		}
		for(int i = 1; i < list.size();i++){
			Double[] temp = (Double[])list.get(i);
			if(ArithmeticUtil.sub(temp[0],this.maxValue) > 0){
				this.maxValue = temp[0];
			}
			if(ArithmeticUtil.sub(temp[1],this.minValue) < 0){
				this.minValue = temp[1];
			}
		}
		
		Double interval = ArithmeticUtil.sub(this.maxValue, this.minValue);
		//这里对y轴显示小数点2位做处理，但是如果最大值和最小值之间都是小数点之后3位才有值，那么显示按照fusionchart自己调节
		interval = ArithmeticUtil.multi(interval, 0.1);
		
		this.maxValue = ArithmeticUtil.round(ArithmeticUtil.add(this.maxValue, interval),2);
		this.minValue = ArithmeticUtil.round(ArithmeticUtil.sub(this.minValue, interval),2);
				
		generatePowerCurveData(realFlag,statFlag);
		logger.debug("查询重点用户监测负荷数据开始");
		return SUCCESS;
	}
	
	/**
	 * 查询电能量数据
	 * @return
	 * @throws Exception
	 */
	public String queryEnergyData() throws Exception {
		logger.debug("查询重点用户监测电能量数据开始");
		if(null == assetNo){
			return SUCCESS;
		}
		int freezeCycleNum = vipConsMonitorDataManager.findFreezeCycleNum(assetNo);
		timeModelList = vipConsMonitorDataManager.getTimeModelList(freezeCycleNum);
		List<Double[]> list = new ArrayList<Double[]>();
		
		energyBean = vipConsMonitorDataManager.findMpEnergyData(assetNo, DateUtil.dateToString(date),
				consNo, freezeCycleNum);
		
		if(null != energyBean){
	    	for(int i = 0; i < energyBean.size(); i++){
	    		VipConsStatDataBean bean = (VipConsStatDataBean)energyBean.get(i);
			    if(bean.getCurveFlag()){//true表示漏点，没采集到数据
			    	continue;
			    }else{
		    		//获取最大值和最小值
					if(!isStatNullValue(bean.getCurveList())){
			        	list.add(checkStatMaxMinValue(bean.getCurveList()));
			        }
				}
	    	}
	    }
		
		//获取最大值和最小值
		int j = list.size();
		if(j > 0){
			this.maxValue = ((Double[])list.get(0))[0];
			this.minValue = ((Double[])list.get(0))[1];
		}
		for(int i = 1; i < list.size();i++){
			Double[] temp = (Double[])list.get(i);
			if(ArithmeticUtil.sub(temp[0],this.maxValue) > 0){
				this.maxValue = temp[0];
			}
			if(ArithmeticUtil.sub(temp[1],this.minValue) < 0){
				this.minValue = temp[1];
			}
		}
		
		Double interval = ArithmeticUtil.sub(this.maxValue, this.minValue);
		//这里对y轴显示小数点2位做处理，但是如果最大值和最小值之间都是小数点之后3位才有值，那么显示按照fusionchart自己调节
		interval = ArithmeticUtil.multi(interval, 0.1);
		
		this.maxValue = ArithmeticUtil.round(ArithmeticUtil.add(this.maxValue, interval),2);
		this.minValue = ArithmeticUtil.round(ArithmeticUtil.sub(this.minValue, interval),2);
		
		generateEnergyCurveData();
		logger.debug("查询重点用户监测电能量数据结束");
		return SUCCESS;
	}
	
	/**
	 * 判断冻结数据列表是否全漏点,true表示露点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isStatNullValue(List<GeneralDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				GeneralDataBean bean = (GeneralDataBean)list.get(i);
				if(bean.getFlag()){
					continue;
				}else{
				    flag = false;
				    break;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取冻结数据最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkStatMaxMinValue(List<GeneralDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			GeneralDataBean bean = (GeneralDataBean)list.get(i);
		    if(bean.getFlag()){
		    	continue;
		    }
		    if(j == 0){
		    	tempMax = bean.getData();
		    	tempMin = bean.getData();
		    	j++;
		    }
			if(null != bean.getData()){
				if(ArithmeticUtil.sub(bean.getData(),tempMax) > 0){
					tempMax = bean.getData();
				}
				if(ArithmeticUtil.sub(bean.getData(),tempMin) < 0){
					tempMin = bean.getData();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 判断实时功率列表是否全漏点
	 * 
	 * @return
	 * @throws Exception
	 */
	private Boolean isRealPowerNullValue(List<VipConsRealPowerDataBean> list){
		Boolean flag = true;
		if(null  != list && 0 < list.size()){
			for(int i = 0; i < list.size(); i++){
				VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)list.get(i);
				if(bean.getBeanFlag()){//如果true，该bean属于漏点
					continue;
				}else{//如果bean不漏点，但是里面数据全漏点，依旧属于漏点
					if(bean.getFlagP() && bean.getFlagPA() && bean.getFlagPB() && bean.getFlagPC()
							&& bean.getFlagQ() && bean.getFlagQA() && bean.getFlagQB() && bean.getFlagQC()	){
						continue;
					}else{
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取实时功率最大和最小值
	 * 
	 * @return
	 * @throws Exception
	 */
	private Double[] checkRealPowerMaxMinValue(List<VipConsRealPowerDataBean> list){
		Double tempMax = 0.0;
		Double tempMin = 0.0;
		Double[] aa = new Double[2];
		int j = 0;
		for(int i = 0; i < list.size(); i++){
			VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)list.get(i);
			if(bean.getBeanFlag()){
				continue;
			}
			if(j == 0){
				if(bean.getFlagP() && bean.getFlagPA() && bean.getFlagPB() && bean.getFlagPC()
						&& bean.getFlagQ() && bean.getFlagQA() && bean.getFlagQB() && bean.getFlagQC()){
					continue;
				}else{
					if(!(bean.getFlagP())){
						tempMax = bean.getP();
						tempMin = bean.getP();
					}else if(!(bean.getFlagPA())){
						tempMax = bean.getpA();
						tempMin = bean.getpA();
					}else if(!(bean.getFlagPB())){
						tempMax = bean.getpB();
						tempMin = bean.getpB();
					}else if(!(bean.getFlagPC())){
						tempMax = bean.getpC();
						tempMin = bean.getpC();
					}else if(!(bean.getFlagQ())){
						tempMax = bean.getqA();
						tempMin = bean.getqA();
					}else if(!(bean.getFlagQA())){
						tempMax = bean.getqA();
						tempMin = bean.getqA();
					}else if(!(bean.getFlagQB())){
						tempMax = bean.getqB();
						tempMin = bean.getqB();
					}else if(!(bean.getFlagQC())){
						tempMax = bean.getqC();
						tempMin = bean.getqC();
					}else{
						continue;
					}
					j++;
				}
			}
			if(null != bean.getP() && !(bean.getFlagP())){
				if(ArithmeticUtil.sub(bean.getP(),tempMax) > 0){
					tempMax = bean.getP();
				}
				if(ArithmeticUtil.sub(bean.getP(),tempMin) < 0){
					tempMin = bean.getP();
				}
			}
			if(null != bean.getpA() && !(bean.getFlagPA())){
				if(ArithmeticUtil.sub(bean.getpA(),tempMax) > 0){
					tempMax = bean.getpA();
				}
				if(ArithmeticUtil.sub(bean.getpA(),tempMin) < 0){
					tempMin = bean.getpA();
				}
			}
			if(null != bean.getpB() && !(bean.getFlagPB())){
				if(ArithmeticUtil.sub(bean.getpB(),tempMax) > 0){
					tempMax = bean.getpB();
				}
				if(ArithmeticUtil.sub(bean.getpB(),tempMin) < 0){
					tempMin = bean.getpB();
				}
			}
			if(null != bean.getpC() && !(bean.getFlagPC())){
				if(ArithmeticUtil.sub(bean.getpC(),tempMax) > 0){
					tempMax = bean.getpC();
				}
				if(ArithmeticUtil.sub(bean.getpC(),tempMin) < 0){
					tempMin = bean.getpC();
				}
			}if(null != bean.getQ() && !(bean.getFlagQ())){
				if(ArithmeticUtil.sub(bean.getQ(),tempMax) > 0){
					tempMax = bean.getQ();
				}
				if(ArithmeticUtil.sub(bean.getQ(),tempMin) < 0){
					tempMin = bean.getQ();
				}
			}
			if(null != bean.getqA() && !(bean.getFlagQA())){
				if(ArithmeticUtil.sub(bean.getqA(),tempMax) > 0){
					tempMax = bean.getqA();
				}
				if(ArithmeticUtil.sub(bean.getqA(),tempMin) < 0){
					tempMin = bean.getqA();
				}
			}
			if(null != bean.getqB() && !(bean.getFlagQB())){
				if(ArithmeticUtil.sub(bean.getqB(),tempMax) > 0){
					tempMax = bean.getqB();
				}
				if(ArithmeticUtil.sub(bean.getqB(),tempMin) < 0){
					tempMin = bean.getqB();
				}
			}
			if(null != bean.getqC() && !(bean.getFlagQC())){
				if(ArithmeticUtil.sub(bean.getqC(),tempMax) > 0){
					tempMax = bean.getqC();
				}
				if(ArithmeticUtil.sub(bean.getqC(),tempMin) < 0){
					tempMin = bean.getqC();
				}
			}
		}
		aa[0] = tempMax;
		aa[1] = tempMin;
		return aa;
	}
	
	/**
	 * 组装负荷曲线Grid数据
	 * @return
	 * @throws Exception
	 */
	private void generatePowerCurveData(boolean realCheckValue, boolean statCheckValue){	
		if(null == timeModelList){//如果时间点为空，则返回
			return;
		}
		for(int m = 0; m < timeModelList.size(); m++){
			VipConsPowerCurveDataBean curveBean = new VipConsPowerCurveDataBean();
			curveBean.setTime(timeModelList.get(m).getTime());
			curveBean.setAssetNo(assetNo);
			powerCurveList.add(curveBean);
		}
		//因为取出的冻结数据在一个list中，如果单循环效率太低，这里取出来分别循环
		List<GeneralDataBean> statPList = null;
		List<GeneralDataBean> statPaList = null;
		List<GeneralDataBean> statPbList = null;
		List<GeneralDataBean> statPcList = null;
		List<GeneralDataBean> statQList = null;
		List<GeneralDataBean> statQaList = null;
		List<GeneralDataBean> statQbList = null;
		List<GeneralDataBean> statQcList = null;
		
		if(statCheckValue){//是否显示冻结数据，这里循环不需要做判断，后台已经处理
			if(null != statPowerBean && 8 == statPowerBean.size()){
				if(null != (VipConsStatDataBean)statPowerBean.get(0)
						&& null != ((VipConsStatDataBean)statPowerBean.get(0)).getCurveList()){
					statPList = ((VipConsStatDataBean)statPowerBean.get(0)).getCurveList();
				}else{
					statPList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(1)
						&& null != ((VipConsStatDataBean)statPowerBean.get(1)).getCurveList()){
					statPaList = ((VipConsStatDataBean)statPowerBean.get(1)).getCurveList();
				}else{
					statPaList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(2)
						&& null != ((VipConsStatDataBean)statPowerBean.get(2)).getCurveList()){
					statPbList = ((VipConsStatDataBean)statPowerBean.get(2)).getCurveList();
				}else{
					statPbList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(3)
						&& null != ((VipConsStatDataBean)statPowerBean.get(3)).getCurveList()){
					statPcList = ((VipConsStatDataBean)statPowerBean.get(3)).getCurveList();
				}else{
					statPcList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(4)
						&& null != ((VipConsStatDataBean)statPowerBean.get(4)).getCurveList()){
					statQList = ((VipConsStatDataBean)statPowerBean.get(4)).getCurveList();
				}else{
					statQList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(5)
						&& null != ((VipConsStatDataBean)statPowerBean.get(5)).getCurveList()){
					statQaList = ((VipConsStatDataBean)statPowerBean.get(5)).getCurveList();
				}else{
					statQaList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(6)
						&& null != ((VipConsStatDataBean)statPowerBean.get(6)).getCurveList()){
					statQbList = ((VipConsStatDataBean)statPowerBean.get(6)).getCurveList();
				}else{
					statQbList = new ArrayList<GeneralDataBean>();
				}
				if(null != (VipConsStatDataBean)statPowerBean.get(7)
						&& null != ((VipConsStatDataBean)statPowerBean.get(7)).getCurveList()){
					statQcList = ((VipConsStatDataBean)statPowerBean.get(7)).getCurveList();
				}else{
					statQcList = new ArrayList<GeneralDataBean>();
				}
			}
		}

		for(int j = 0; j < powerCurveList.size(); j++){//循环配置的时间点数，默认96次
			VipConsPowerCurveDataBean curveTmpBean = (VipConsPowerCurveDataBean)powerCurveList.get(j);
			if(realCheckValue){//是否显示实时数据
				if(null != realPowerBean && 0 < realPowerBean.size()){
					VipConsRealPowerDataBean bean = (VipConsRealPowerDataBean)realPowerBean.get(j);
					if(bean.getBeanFlag()){//如果为true，表示全部漏点
						curveTmpBean.setRealP(null);
						curveTmpBean.setRealPa(null);
						curveTmpBean.setRealPb(null);
						curveTmpBean.setRealPc(null);
						curveTmpBean.setRealQ(null);
						curveTmpBean.setRealQa(null);
						curveTmpBean.setRealQb(null);
						curveTmpBean.setRealQc(null);
					}else{//如果为false，则针对每个数据进行筛选
						if(bean.getFlagP()){
							curveTmpBean.setRealP(null);
						}else{
							curveTmpBean.setRealP(bean.getP());
						}
						if(bean.getFlagPA()){
							curveTmpBean.setRealPa(null);
						}else{
							curveTmpBean.setRealPa(bean.getpA());
						}
						if(bean.getFlagPB()){
							curveTmpBean.setRealPb(null);
						}else{
							curveTmpBean.setRealPb(bean.getpB());
						}
						if(bean.getFlagPC()){
							curveTmpBean.setRealPc(null);
						}else{
							curveTmpBean.setRealPc(bean.getpC());
						}
						if(bean.getFlagQ()){
							curveTmpBean.setRealQ(null);
						}else{
							curveTmpBean.setRealQ(bean.getQ());
						}
						if(bean.getFlagQA()){
							curveTmpBean.setRealQa(null);
						}else{
							curveTmpBean.setRealQa(bean.getqA());
						}
						if(bean.getFlagQB()){
							curveTmpBean.setRealQb(null);
						}else{
							curveTmpBean.setRealQb(bean.getqB());
						}
						if(bean.getFlagQC()){
							curveTmpBean.setRealQc(null);
						}else{
							curveTmpBean.setRealQc(bean.getqC());
						}
					}
				}
			}
			if(statCheckValue){//是否显示冻结数据					
				if(null != statPList && 0 < statPList.size()){
					GeneralDataBean bean = statPList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatP(null);
					}else{
						curveTmpBean.setStatP(bean.getData());
					}
				}
				if(null != statPaList && 0 < statPaList.size()){
					GeneralDataBean bean = statPaList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatPa(null);
					}else{
						curveTmpBean.setStatPa(bean.getData());
					}
				}
				if(null != statPbList && 0 < statPbList.size()){
					GeneralDataBean bean = statPbList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatPb(null);
					}else{
						curveTmpBean.setStatPb(bean.getData());
					}
				}
				if(null != statPcList && 0 < statPcList.size()){
					GeneralDataBean bean = statPcList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatPc(null);
					}else{
						curveTmpBean.setStatPc(bean.getData());
					}
				}
				if(null != statQList && 0 < statQList.size()){
					GeneralDataBean bean = statQList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatQ(null);
					}else{
						curveTmpBean.setStatQ(bean.getData());
					}
				}
				if(null != statQaList && 0 < statQaList.size()){
					GeneralDataBean bean = statQaList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatQa(null);
					}else{
						curveTmpBean.setStatQa(bean.getData());
					}
				}
				if(null != statQbList && 0 < statQbList.size()){
					GeneralDataBean bean = statQbList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatQb(null);
					}else{
						curveTmpBean.setStatQb(bean.getData());
					}
				}
				if(null != statQcList && 0 < statQcList.size()){
					GeneralDataBean bean = statQcList.get(j);
					if(bean.getFlag()){
						curveTmpBean.setStatQc(null);
					}else{
						curveTmpBean.setStatQc(bean.getData());
					}
				}
			}
	    }
	}
	
	/**
	 * 组装电能量曲线Grid数据
	 * @return
	 * @throws Exception
	 */
	private void generateEnergyCurveData(){	
		if(null == timeModelList){//如果时间点为空，则返回
			return;
		}
		for(int m = 0; m < timeModelList.size(); m++){
			VipConsEnergyCurveDataBean curveBean = new VipConsEnergyCurveDataBean();
			curveBean.setTime(timeModelList.get(m).getTime());
			curveBean.setAssetNo(assetNo);
			energyCurveList.add(curveBean);
		}
		
		//因为取出的冻结数据在一个list中，如果单循环效率太低，这里取出来分别循环
		List<GeneralDataBean> papEList = null;
		List<GeneralDataBean> prpEList = null;
		List<GeneralDataBean> rapEList = null;
		List<GeneralDataBean> rrpEList = null;
		
		if(null != energyBean && 4 == energyBean.size()){
			if(null != (VipConsStatDataBean)energyBean.get(0)
					&& null != ((VipConsStatDataBean)energyBean.get(0)).getCurveList()){
				papEList = ((VipConsStatDataBean)energyBean.get(0)).getCurveList();
			}else{
				papEList = new ArrayList<GeneralDataBean>();
			}
			if(null != (VipConsStatDataBean)energyBean.get(1)
					&& null != ((VipConsStatDataBean)energyBean.get(1)).getCurveList()){
				prpEList = ((VipConsStatDataBean)energyBean.get(1)).getCurveList();
			}else{
				prpEList = new ArrayList<GeneralDataBean>();
			}
			if(null != (VipConsStatDataBean)energyBean.get(2)
					&& null != ((VipConsStatDataBean)energyBean.get(2)).getCurveList()){
				rapEList = ((VipConsStatDataBean)energyBean.get(2)).getCurveList();
			}else{
				rapEList = new ArrayList<GeneralDataBean>();
			}
			if(null != (VipConsStatDataBean)energyBean.get(3)
					&& null != ((VipConsStatDataBean)energyBean.get(3)).getCurveList()){
				rrpEList = ((VipConsStatDataBean)energyBean.get(3)).getCurveList();
			}else{
				rrpEList = new ArrayList<GeneralDataBean>();
			}
		}
		
		for(int j = 0; j < energyCurveList.size(); j++){//循环配置的时间点数，默认96次
			VipConsEnergyCurveDataBean curveTmpBean = (VipConsEnergyCurveDataBean)energyCurveList.get(j);
			if(null != papEList && 0 < papEList.size()){
				GeneralDataBean bean = papEList.get(j);
				if(bean.getFlag()){
					curveTmpBean.setPapE(null);
				}else{
					curveTmpBean.setPapE(bean.getData());
				}
			}
			if(null != prpEList && 0 < prpEList.size()){
				GeneralDataBean bean = prpEList.get(j);
				if(bean.getFlag()){
					curveTmpBean.setPrpE(null);
				}else{
					curveTmpBean.setPrpE(bean.getData());
				}
			}
			if(null != rapEList && 0 < rapEList.size()){
				GeneralDataBean bean = rapEList.get(j);
				if(bean.getFlag()){
					curveTmpBean.setRapE(null);
				}else{
					curveTmpBean.setRapE(bean.getData());
				}
			}
			if(null != rrpEList && 0 < rrpEList.size()){
				GeneralDataBean bean = rrpEList.get(j);
				if(bean.getFlag()){
					curveTmpBean.setRrpE(null);
				}else{
					curveTmpBean.setRrpE(bean.getData());
				}
			}
		}
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setVipConsMonitorDataManager(
			VipConsMonitorDataManager vipConsMonitorDataManager) {
		this.vipConsMonitorDataManager = vipConsMonitorDataManager;
	}

	public List<VipConsMonitorDataBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<VipConsMonitorDataBean> dataList) {
		this.dataList = dataList;
	}

	public boolean isRealFlag() {
		return realFlag;
	}

	public void setRealFlag(boolean realFlag) {
		this.realFlag = realFlag;
	}

	public boolean isStatFlag() {
		return statFlag;
	}

	public void setStatFlag(boolean statFlag) {
		this.statFlag = statFlag;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public List<GeneralDataBean> getTimeModelList() {
		return timeModelList;
	}

	public void setTimeModelList(List<GeneralDataBean> timeModelList) {
		this.timeModelList = timeModelList;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public List<VipConsStatDataBean> getStatPowerBean() {
		return statPowerBean;
	}

	public void setStatPowerBean(List<VipConsStatDataBean> statPowerBean) {
		this.statPowerBean = statPowerBean;
	}

	public List<VipConsRealPowerDataBean> getRealPowerBean() {
		return realPowerBean;
	}

	public void setRealPowerBean(List<VipConsRealPowerDataBean> realPowerBean) {
		this.realPowerBean = realPowerBean;
	}

	public String getRealPowerName() {
		return realPowerName;
	}

	public void setRealPowerName(String realPowerName) {
		this.realPowerName = realPowerName;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public List<EDataMp> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<EDataMp> assetList) {
		this.assetList = assetList;
	}

	public List<VipConsPowerCurveDataBean> getPowerCurveList() {
		return powerCurveList;
	}

	public void setPowerCurveList(List<VipConsPowerCurveDataBean> powerCurveList) {
		this.powerCurveList = powerCurveList;
	}

	public List<VipConsStatDataBean> getEnergyBean() {
		return energyBean;
	}

	public void setEnergyBean(List<VipConsStatDataBean> energyBean) {
		this.energyBean = energyBean;
	}

	public List<VipConsEnergyCurveDataBean> getEnergyCurveList() {
		return energyCurveList;
	}

	public void setEnergyCurveList(List<VipConsEnergyCurveDataBean> energyCurveList) {
		this.energyCurveList = energyCurveList;
	}
}
