//去左空格; 
function ltrim(s) {
	return s.replace(/^\s*/, "");
}

// 去右空格;
function rtrim(s) {
	return s.replace(/\s*$/, "");
}

// 去左右空格;
function trim(s) {
	return rtrim(ltrim(s));
}

Ext.onReady(function() {
	var width = 0;
	var height = 0;
	var generalAnalyseChart;
	
	//生成曲线数据
	var curveDataArrayCm = new Array();
	var curveDataCm;
	var publicName="";
	var consId="";
	dataXml = getGAPowerMltXMLData('', '', '', '', '', '', '', '', '', '', '',
			'', '', '', '', '', '', '', '', '', '', 4, '', '', '');
	// 默认标题是测量点
		var curTab = "mpPanel";
		// ----事件函数定义 ---------------------
		// 总加组Grid点击事件,点击查询按钮和双击的时候都会触发该事件
		function totalClick(totalId, freezeCycleNum, dataDate, contrastDate, curveType,
				statCheckValue, conCheckValue, realCheckValue) {
			var consNo = trim(Ext.getCmp("general_consNo").getValue());
			if (consNo == "" || consNo == null) {
				Ext.MessageBox.alert('提示', '请从左边树选择用户节点，或输入户号！');
				return;
			}

			var url = "qrystat/generalData!queryTotalData.action";

			//防止数据出现问题
			if(Ext.isEmpty(freezeCycleNum)){
				freezeCycleNum = 0;
			}
			
			Ext.Ajax
					.request( {
						url : url,
						params : {
							totalId : totalId,
							freezeCycleNum: freezeCycleNum,
							dataDate : dataDate,
							contrastDate : contrastDate,
							consNo : consNo,
							curveType : curveType,
							statCheckValue : statCheckValue,
							conCheckValue : conCheckValue,
							realCheckValue : realCheckValue
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							
							if(null != result.headers && 0 < result.headers.length){
								curveDataArrayCm.length = 0;
								Ext.each(result.headers, function(h) {
									h.data = result;
									h.width = 160;
									h.align = "center";
									h.tooltip = h.header;
									curveDataArrayCm.push(h);
								});
								curveDataCm = new Ext.grid.ColumnModel(curveDataArrayCm);
								curveData_gridStore.removeAll();
								curveData_gridStore.loadData(result);
								curveData_gridPanel.reconfigure(curveData_gridStore,curveDataCm);
							}

							var generalPowerModelList = result.generalPowerModelList;
							var yAxisMaxValue = result.maxValue;
							var yAxisMinValue = result.minValue;

							if (null == generalPowerModelList) {
								return;
							}

							if (curveType == 1) {
								var generalPowerData = result.generalPowerData;
								var contrastPowerData = result.contrastPowerData;
								var generalAPowerData = result.generalAPowerData;
								var contrastAPowerData = result.contrastAPowerData;
								var generalBPowerData = result.generalBPowerData;
								var contrastBPowerData = result.contrastBPowerData;
								var generalCPowerData = result.generalCPowerData;
								var contrastCPowerData = result.contrastCPowerData;

								var mpDayRealPowerList = result.mpDayRealPowerList;
								var mpDayRealPowerConList = result.mpDayRealPowerConList;

								var generalPowerName = result.generalPowerName;
								var contrastPowerName = result.contrastPowerName;
								var generalAPowerName = result.generalAPowerName;
								var contrastAPowerName = result.contrastAPowerName;
								var generalBPowerName = result.generalBPowerName;
								var contrastBPowerName = result.contrastBPowerName;
								var generalCPowerName = result.generalCPowerName;
								var contrastCPowerName = result.contrastCPowerName;

								var mpDayRealPowerName = result.mpDayRealPowerName;
								var mpDayRealPowerConName = result.mpDayRealPowerConName;
							}
							if (statCheckValue) {//如果选中了冻结数据，默认显示冻结数据的负荷特性
								if (null != generalPowerData && curveType == 1) {
									var maxTmnl = 0;
									var minTmnl = 0;
									var maxTmnlTime = "";
									var minTmnlTime = "";
									var sum = 0;
									var i = 0;
									var j = 0;
									Ext.each(generalPowerData, function(obj) {
										if(!obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (i == 0) {
												maxTmnl = obj['data'];
												maxTmnlTime = obj['time'];
												i = i + 1;
											} else {
												if (maxTmnl < obj['data']) {
													maxTmnl = obj['data'];
													maxTmnlTime = obj['time'];
												}
											}
											sum += obj['data'];
										}
									});
									Ext.each(generalPowerData, function(obj) {
										if(!obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (j == 0) {
												minTmnl = obj['data'];
												minTmnlTime = obj['time'];
												j = j + 1;
											} else {
												if (minTmnl > obj['data']) {
													minTmnl = obj['data'];
													minTmnlTime = obj['time'];
												}
											}
										}
									});
									var maxAndMinProp = "";
									if (minTmnl != 0) {
										maxAndMinProp = maxTmnl
												/ (minTmnl + 0.0);
										maxAndMinProp = maxAndMinProp
												.toPrecision(3);
									}
									var maxAndMinDiff = "";
									if (maxTmnl != 0) {
										maxAndMinDiff = (maxTmnl - minTmnl)
												/ maxTmnl;
										maxAndMinDiff = maxAndMinDiff
												.toPrecision(3);
									}
									var loadFactor = "";
									if (maxTmnl != 0) {
										loadFactor = (sum + 0.0)
												/ (maxTmnl * generalPowerData.length);
										loadFactor = loadFactor.toPrecision(3);
									}
									var loadChar = {
										maxTmnl : maxTmnl,
										maxTmnlTime : maxTmnlTime,
										minTmnl : minTmnl,
										minTmnlTime : minTmnlTime,
										maxAndMinProp : maxAndMinProp,
										maxAndMinDiff : maxAndMinDiff,
										loadFactor : loadFactor
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);
								} else {//如果不是功率曲线，或者数据为null，则置空
									var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
								}
							} else if(realCheckValue){//如果没选中冻结数据，选中的是实时，那么显示实时数据的负荷特性
								if (null != mpDayRealPowerList
										&& curveType == 1) {
									var maxTmnl = 0;
									var minTmnl = 0;
									var maxTmnlTime = "";
									var minTmnlTime = "";
									var sum = 0;
									var i = 0;
									var j = 0;
									Ext.each(mpDayRealPowerList, function(obj) {
										if(!obj['beanFlag'] && !obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (i == 0) {
												maxTmnl = obj['power'];
												maxTmnlTime = obj['time'];
												i = i + 1;
											} else {
												if (maxTmnl < obj['power']) {
													maxTmnl = obj['power'];
													maxTmnlTime = obj['time'];
												}
											}
											sum += obj['power'];
										}
									});
									Ext.each(mpDayRealPowerList, function(obj) {
										if(!obj['beanFlag'] && !obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (j == 0) {
												minTmnl = obj['power'];
												minTmnlTime = obj['time'];
												j = j + 1;
											} else {
												if (minTmnl > obj['power']) {
													minTmnl = obj['power'];
													minTmnlTime = obj['time'];
												}
											}
										}
									});
									var maxAndMinProp = "";
									if (minTmnl != 0) {
										maxAndMinProp = maxTmnl
												/ (minTmnl + 0.0);
										maxAndMinProp = maxAndMinProp
												.toPrecision(3);
									}
									var maxAndMinDiff = "";
									if (maxTmnl != 0) {
										maxAndMinDiff = (maxTmnl - minTmnl)
												/ maxTmnl;
										maxAndMinDiff = maxAndMinDiff
												.toPrecision(3);
									}
									var loadFactor = "";
									if (maxTmnl != 0) {
										loadFactor = (sum + 0.0)
												/ (maxTmnl * mpDayRealPowerList.length);
										loadFactor = loadFactor.toPrecision(3);
									}
									var loadChar = {
										maxTmnl : maxTmnl,
										maxTmnlTime : maxTmnlTime,
										minTmnl : minTmnl,
										minTmnlTime : minTmnlTime,
										maxAndMinProp : maxAndMinProp,
										maxAndMinDiff : maxAndMinDiff,
										loadFactor : loadFactor
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);
								} else {//如果不是功率曲线，或者数据为null，则置空
									var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
								}
							}else{//实时和冻结都没选中，置空
								var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
							}

							// 生成曲线
							var step = generalPowerModelList.length / 12;
							dataXml = "";
							if (curveType == 1) {
								var pName = publicName + "总加组功率曲线";
								dataXml = getGATotalPowerMltXMLData(
										generalPowerModelList,
										generalPowerData, contrastPowerData,
										generalAPowerData, contrastAPowerData,
										generalBPowerData, contrastBPowerData,
										generalCPowerData, contrastCPowerData,
										mpDayRealPowerList,
										mpDayRealPowerConList,
										generalPowerName, contrastPowerName,
										generalAPowerName, contrastAPowerName,
										generalBPowerName, contrastBPowerName,
										generalCPowerName, contrastCPowerName,
										mpDayRealPowerName,
										mpDayRealPowerConName, step, pName,
										yAxisMaxValue, yAxisMinValue);
							} else {// 没有数据
								dataXml = getGATotalPowerMltXMLData(
										generalPowerModelList, '', '', '', '','', '', '', '','','',
										'', '', '', '','', '', '', '', '','',step,'','','');
							}

							generalAnalyseChart = "";
							generalAnalyseChart = new FusionCharts(
									"fusionCharts/MSLine.swf",
									"generalAnalyseChartId", width, height);
							generalAnalyseChart.setDataXML(dataXml);
							generalAnalyseChart.setTransparent(true);
							generalAnalyseChart.render(generalAnalyse3DPanel
									.getId());// 渲染
						}
					});
		}

		// 测量点Grid点击事件,点击查询按钮和双击的时候都会触发该事件
		function mpClick(dataId, meterNo, freezeCycleNum, dataDate, contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue) {
			var consNo = trim(Ext.getCmp("general_consNo").getValue());
			if (consNo == "" || consNo == null) {
				Ext.MessageBox.alert('提示', '请从左边树选择用户节点，或输入户号！');
				return;
			}
			var url = "qrystat/generalData!queryMpData.action";
			
			//防止数据出现问题
			if(Ext.isEmpty(freezeCycleNum)){
				freezeCycleNum = 0;
			}
			
			if(!statCheckValue && !realCheckValue){
				Ext.MessageBox.alert('提示', '请勾选实时或冻结数据！');
				return;
			}
			
			Ext.Ajax
					.request( {
						url : url,
						params : {
							dataId : dataId,
							freezeCycleNum:freezeCycleNum,
							dataDate : dataDate,
							contrastDate : contrastDate,
							consNo : consNo,
							curveType : curveType,
							statCheckValue : statCheckValue,
							conCheckValue : conCheckValue,
							realCheckValue : realCheckValue
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							
							if(null != result.headers && 0 < result.headers.length){
								curveDataArrayCm.length = 0;
								Ext.each(result.headers, function(h) {
									h.data = result;
									if (curveType == 5 || curveType == 6){
									    h.width = 190;
									}else{
										h.width = 160;
									}
									h.align = "center";
									h.tooltip = h.header;
									curveDataArrayCm.push(h);
								});
								curveDataCm = new Ext.grid.ColumnModel(curveDataArrayCm);
								curveData_gridStore.removeAll();
								curveData_gridStore.loadData(result);
								curveData_gridPanel.reconfigure(curveData_gridStore,curveDataCm);
							}

							var generalPowerModelList = result.generalPowerModelList;
							var yAxisMaxValue = result.maxValue;
							var yAxisMinValue = result.minValue;

							if (null == generalPowerModelList) {//如果时间模为空，则返回
								return;
							}

							if (curveType == 1) {//功率
								var generalPowerData = result.generalPowerData;
								var contrastPowerData = result.contrastPowerData;
								var generalAPowerData = result.generalAPowerData;
								var contrastAPowerData = result.contrastAPowerData;
								var generalBPowerData = result.generalBPowerData;
								var contrastBPowerData = result.contrastBPowerData;
								var generalCPowerData = result.generalCPowerData;
								var contrastCPowerData = result.contrastCPowerData;

								var mpDayRealPowerList = result.mpDayRealPowerList;
								var mpDayRealPowerConList = result.mpDayRealPowerConList;

								var generalPowerName = result.generalPowerName;
								var contrastPowerName = result.contrastPowerName;
								var generalAPowerName = result.generalAPowerName;
								var contrastAPowerName = result.contrastAPowerName;
								var generalBPowerName = result.generalBPowerName;
								var contrastBPowerName = result.contrastBPowerName;
								var generalCPowerName = result.generalCPowerName;
								var contrastCPowerName = result.contrastCPowerName;

								var mpDayRealPowerName = result.mpDayRealPowerName;
								var mpDayRealPowerConName = result.mpDayRealPowerConName;

							} else if (curveType == 2) {//电压
								var generalAVoltData = result.generalAVoltData;
								var contrastAVoltData = result.contrastAVoltData;
								var generalBVoltData = result.generalBVoltData;
								var contrastBVoltData = result.contrastBVoltData;
								var generalCVoltData = result.generalCVoltData;
								var contrastCVoltData = result.contrastCVoltData;

								var mpDayRealVoltList = result.mpDayRealVoltList;
								var mpDayRealVoltConList = result.mpDayRealVoltConList;

								var generalAVoltName = result.generalAVoltName;
								var contrastAVoltName = result.contrastAVoltName;
								var generalBVoltName = result.generalBVoltName;
								var contrastBVoltName = result.contrastBVoltName;
								var generalCVoltName = result.generalCVoltName;
								var contrastCVoltName = result.contrastCVoltName;

								var mpDayRealVoltName = result.mpDayRealVoltName;
								var mpDayRealVoltConName = result.mpDayRealVoltConName;

							} else if (curveType == 3) {//电流
								var generalCurData = result.generalCurData;
								var contrastCurData = result.contrastCurData;
								var generalACurData = result.generalACurData;
								var contrastACurData = result.contrastACurData;
								var generalBCurData = result.generalBCurData;
								var contrastBCurData = result.contrastBCurData;
								var generalCCurData = result.generalCCurData;
								var contrastCCurData = result.contrastCCurData;

								var mpDayRealCurList = result.mpDayRealCurList;
								var mpDayRealCurConList = result.mpDayRealCurConList;

								var generalCurName = result.generalCurName;
								var contrastCurName = result.contrastCurName;
								var generalACurName = result.generalACurName;
								var contrastACurName = result.contrastACurName;
								var generalBCurName = result.generalBCurName;
								var contrastBCurName = result.contrastBCurName;
								var generalCCurName = result.generalCCurName;
								var contrastCCurName = result.contrastCCurName;

								var mpDayRealCurName = result.mpDayRealCurName;
								var mpDayRealCurConName = result.mpDayRealCurConName;

							} else if (curveType == 4) {//功率因数
								var generalFactorData = result.generalFactorData;
								var contrastFactorData = result.contrastFactorData;
								var generalAFactorData = result.generalAFactorData;
								var contrastAFactorData = result.contrastAFactorData;
								var generalBFactorData = result.generalBFactorData;
								var contrastBFactorData = result.contrastBFactorData;
								var generalCFactorData = result.generalCFactorData;
								var contrastCFactorData = result.contrastCFactorData;

								var mpDayRealFactorList = result.mpDayRealFactorList;
								var mpDayRealFactorConList = result.mpDayRealFactorConList;

								var generalFactorName = result.generalFactorName;
								var contrastFactorName = result.contrastFactorName;
								var generalAFactorName = result.generalAFactorName;
								var contrastAFactorName = result.contrastAFactorName;
								var generalBFactorName = result.generalBFactorName;
								var contrastBFactorName = result.contrastBFactorName;
								var generalCFactorName = result.generalCFactorName;
								var contrastCFactorName = result.contrastCFactorName;

								var mpDayRealFactorName = result.mpDayRealFactorName;
								var mpDayRealFactorConName = result.mpDayRealFactorConName;

							}else if (curveType == 5) {//电能量
								var generalEnergyPapData = result.generalEnergyPapData;
								var contrastEnergyPapData = result.contrastEnergyPapData;
								var generalEnergyPrpData = result.generalEnergyPrpData;
								var contrastEnergyPrpData = result.contrastEnergyPrpData;
								var generalEnergyRapData = result.generalEnergyRapData;
								var contrastEnergyRapData = result.contrastEnergyRapData;
								var generalEnergyRrpData = result.generalEnergyRrpData;
								var contrastEnergyRrpData = result.contrastEnergyRrpData;

								var generalEnergyPapName = result.generalEnergyPapName;
								var contrastEnergyPapName = result.contrastEnergyPapName;
								var generalEnergyPrpName = result.generalEnergyPrpName;
								var contrastEnergyPrpName = result.contrastEnergyPrpName;
								var generalEnergyRapName = result.generalEnergyRapName;
								var contrastEnergyRapName = result.contrastEnergyRapName;
								var generalEnergyRrpName = result.generalEnergyRrpName;
								var contrastEnergyRrpName = result.contrastEnergyRrpName;

							}else if (curveType == 6) {//电能示值
								var generalReadPapData = result.generalReadPapData;
								var contrastReadPapData = result.contrastReadPapData;
								var generalReadPrpData = result.generalReadPrpData;
								var contrastReadPrpData = result.contrastReadPrpData;
								var generalReadRapData = result.generalReadRapData;
								var contrastReadRapData = result.contrastReadRapData;
								var generalReadRrpData = result.generalReadRrpData;
								var contrastReadRrpData = result.contrastReadRrpData;

								var generalReadPapName = result.generalReadPapName;
								var contrastReadPapName = result.contrastReadPapName;
								var generalReadPrpName = result.generalReadPrpName;
								var contrastReadPrpName = result.contrastReadPrpName;
								var generalReadRapName = result.generalReadRapName;
								var contrastReadRapName = result.contrastReadRapName;
								var generalReadRrpName = result.generalReadRrpName;
								var contrastReadRrpName = result.contrastReadRrpName;

							}
							
							if (statCheckValue) {//如果选中了冻结数据，默认显示冻结数据的负荷特性
								if (null != generalPowerData && curveType == 1) {
									var maxTmnl = 0;
									var minTmnl = 0;
									var maxTmnlTime = "";
									var minTmnlTime = "";
									var sum = 0;
									var i = 0;
									var j = 0;
									Ext.each(generalPowerData, function(obj) {
										if(!obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (i == 0) {											
												    maxTmnl = obj['data'];
												    maxTmnlTime = obj['time'];
												    i = i + 1;
											} else {
												if (maxTmnl < obj['data']) {
													maxTmnl = obj['data'];
													maxTmnlTime = obj['time'];
												}
											}
											sum += obj['data'];
									    }
									});
									Ext.each(generalPowerData, function(obj) {
										if(!obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (j == 0) {
												minTmnl = obj['data'];
												minTmnlTime = obj['time'];
												j = j + 1;
											} else {
												if (minTmnl > obj['data']) {
													minTmnl = obj['data'];
													minTmnlTime = obj['time'];
												}
											}
										}
									});
									var maxAndMinProp = "";
									if (minTmnl != 0) {
										maxAndMinProp = maxTmnl
												/ (minTmnl + 0.0);
										maxAndMinProp = maxAndMinProp
												.toPrecision(3);
									}
									var maxAndMinDiff = "";
									if (maxTmnl != 0) {
										maxAndMinDiff = (maxTmnl - minTmnl)
												/ maxTmnl;
										maxAndMinDiff = maxAndMinDiff
												.toPrecision(3);
									}
									var loadFactor = "";
									if (maxTmnl != 0) {
										loadFactor = (sum + 0.0)
												/ (maxTmnl * generalPowerData.length);
										loadFactor = loadFactor.toPrecision(3);
									}
									var loadChar = {
										maxTmnl : maxTmnl,
										maxTmnlTime : maxTmnlTime,
										minTmnl : minTmnl,
										minTmnlTime : minTmnlTime,
										maxAndMinProp : maxAndMinProp,
										maxAndMinDiff : maxAndMinDiff,
										loadFactor : loadFactor
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);
								} else {//如果不是功率曲线，或者数据为null，则置空
									var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
								}
							} else if(realCheckValue){//如果没选中冻结数据，选中的是实时，那么显示实时数据的负荷特性
								if (null != mpDayRealPowerList
										&& curveType == 1) {
									var maxTmnl = 0;
									var minTmnl = 0;
									var maxTmnlTime = "";
									var minTmnlTime = "";
									var sum = 0;
									var i = 0;
									var j = 0;
									Ext.each(mpDayRealPowerList, function(obj) {
										if(!obj['beanFlag'] && !obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (i == 0) {
												maxTmnl = obj['power'];
												maxTmnlTime = obj['time'];
												i = i + 1;
											} else {
												if (maxTmnl < obj['power']) {
													maxTmnl = obj['power'];
													maxTmnlTime = obj['time'];
												}
											}
											sum += obj['power'];
										}
									});
									Ext.each(mpDayRealPowerList, function(obj) {
										if(!obj['beanFlag'] && !obj['flag']){//flag为true表示漏点，漏点数据跳过
											if (j == 0) {
												minTmnl = obj['power'];
												minTmnlTime = obj['time'];
												j = j + 1;
											} else {
												if (minTmnl > obj['power']) {
													minTmnl = obj['power'];
													minTmnlTime = obj['time'];
												}
											}
										}
									});
									var maxAndMinProp = "";
									if (minTmnl != 0) {
										maxAndMinProp = maxTmnl
												/ (minTmnl + 0.0);
										maxAndMinProp = maxAndMinProp
												.toPrecision(3);
									}
									var maxAndMinDiff = "";
									if (maxTmnl != 0) {
										maxAndMinDiff = (maxTmnl - minTmnl)
												/ maxTmnl;
										maxAndMinDiff = maxAndMinDiff
												.toPrecision(3);
									}
									var loadFactor = "";
									if (maxTmnl != 0) {
										loadFactor = (sum + 0.0)
												/ (maxTmnl * mpDayRealPowerList.length);
										loadFactor = loadFactor.toPrecision(3);
									}
									var loadChar = {
										maxTmnl : maxTmnl,
										maxTmnlTime : maxTmnlTime,
										minTmnl : minTmnl,
										minTmnlTime : minTmnlTime,
										maxAndMinProp : maxAndMinProp,
										maxAndMinDiff : maxAndMinDiff,
										loadFactor : loadFactor
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);
								} else {//如果不是功率曲线，或者数据为null，则置空
									var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
								}
							}else{//实时和冻结都没选中，置空
								var loadChar = {
										maxTmnl : 0,
										maxTmnlTime : "",
										minTmnl : 0,
										minTmnlTime : "",
										maxAndMinProp : "",
										maxAndMinDiff : "",
										loadFactor : ""
									};
									generalAnalyseTmnlPanel.getForm()
											.setValues(loadChar);// 负荷特性赋值
							}

							if(Ext.isEmpty(meterNo)){
								meterNo = "";
							}
							// 生成曲线
							var step = generalPowerModelList.length / 12;
							dataXml = "";
							if (curveType == 1) {
                                var powerName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	powerName = "测量点功率曲线";
                                }else{
                                	powerName = publicName +"【"+ meterNo +"】"+ "测量点功率曲线";
                                }
								dataXml = getGAPowerMltXMLData(
										generalPowerModelList,
										generalPowerData, contrastPowerData,
										generalAPowerData, contrastAPowerData,
										generalBPowerData, contrastBPowerData,
										generalCPowerData, contrastCPowerData,
										mpDayRealPowerList,
										mpDayRealPowerConList,
										generalPowerName, contrastPowerName,
										generalAPowerName, contrastAPowerName,
										generalBPowerName, contrastBPowerName,
										generalCPowerName, contrastCPowerName,
										mpDayRealPowerName,
										mpDayRealPowerConName, step, powerName,
										yAxisMaxValue, yAxisMinValue);
							} else if (curveType == 2) {
								var voltName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	voltName = "测量点电压曲线";
                                }else{
                                	voltName = publicName +"【"+ meterNo +"】"+ "测量点电压曲线";
                                }
								dataXml = getGAVoltMltXMLData(
										generalPowerModelList,
										generalAVoltData, contrastAVoltData,
										generalBVoltData, contrastBVoltData,
										generalCVoltData, contrastCVoltData,
										mpDayRealVoltList,
										mpDayRealVoltConList, generalAVoltName,
										contrastAVoltName, generalBVoltName,
										contrastBVoltName, generalCVoltName,
										contrastCVoltName, mpDayRealVoltName,
										mpDayRealVoltConName, step, voltName,
										yAxisMaxValue, yAxisMinValue);
							} else if (curveType == 3) {
								var curName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	curName = "测量点电流曲线";
                                }else{
                                	curName = publicName +"【"+ meterNo +"】"+ "测量点电流曲线";
                                }
								dataXml = getGACurMltXMLData(
										generalPowerModelList, generalCurData,
										contrastCurData, generalACurData,
										contrastACurData, generalBCurData,
										contrastBCurData, generalCCurData,
										contrastCCurData, mpDayRealCurList,
										mpDayRealCurConList, generalCurName,
										contrastCurName, generalACurName,
										contrastACurName, generalBCurName,
										contrastBCurName, generalCCurName,
										contrastCCurName, mpDayRealCurName,
										mpDayRealCurConName, step, curName,
										yAxisMaxValue, yAxisMinValue);
							} else if (curveType == 4) {
								var factorName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	factorName = "测量点功率因数曲线";
                                }else{
                                	factorName = publicName +"【"+ meterNo +"】"+ "测量点功率因数曲线";
                                }
								dataXml = getGAFactorMltXMLData(
										generalPowerModelList,
										generalFactorData, contrastFactorData,
										generalAFactorData,
										contrastAFactorData,
										generalBFactorData,
										contrastBFactorData,
										generalCFactorData,
										contrastCFactorData,
										mpDayRealFactorList,
										mpDayRealFactorConList,
										generalFactorName, contrastFactorName,
										generalAFactorName,
										contrastAFactorName,
										generalBFactorName,
										contrastBFactorName,
										generalCFactorName,
										contrastCFactorName,
										mpDayRealFactorName,
										mpDayRealFactorConName, step,
										factorName, yAxisMaxValue,
										yAxisMinValue);
							}else if (curveType == 5) {
								var curName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	curName = "测量点电能量曲线";
                                }else{
                                	curName = publicName +"【"+ meterNo +"】"+ "测量点电能量曲线";
                                }
								dataXml = getGAEnergyMltXMLData(
										generalPowerModelList, generalEnergyPapData,
										contrastEnergyPapData, generalEnergyPrpData,
										contrastEnergyPrpData, generalEnergyRapData,
										contrastEnergyRapData, generalEnergyRrpData,
										contrastEnergyRrpData, generalEnergyPapName,
										contrastEnergyPapName, generalEnergyPrpName,
										contrastEnergyPrpName, generalEnergyRapName,
										contrastEnergyRapName, generalEnergyRrpName,
										contrastEnergyRrpName, step, curName,
										yAxisMaxValue, yAxisMinValue);
							} else if (curveType == 6) {
								var curName = "";
                                if(Ext.isEmpty(publicName.length)){
                                	curName = "测量点电能示值曲线";
                                }else{
                                	curName = publicName +"【"+ meterNo +"】"+ "测量点电能示值曲线";
                                }
								dataXml = getGAReadMltXMLData(
										generalPowerModelList, generalReadPapData,
										contrastReadPapData, generalReadPrpData,
										contrastReadPrpData, generalReadRapData,
										contrastReadRapData, generalReadRrpData,
										contrastReadRrpData, generalReadPapName,
										contrastReadPapName, generalReadPrpName,
										contrastReadPrpName, generalReadRapName,
										contrastReadRapName, generalReadRrpName,
										contrastReadRrpName, step, curName,
										yAxisMaxValue, yAxisMinValue);
							}else{
								return;
							}
							generalAnalyseChart = "";
							generalAnalyseChart = new FusionCharts(
									"fusionCharts/MSLine.swf",
									"generalAnalyseChartId", width, height);
							generalAnalyseChart.setDataXML(dataXml);
							generalAnalyseChart.setTransparent(true);
							generalAnalyseChart.render(generalAnalyse3DPanel
									.getId());// 渲染
						}
					});
		}

		// 查询按钮事件
		function queryEvent(consNo, dataDate) {
			if (consNo == "" || consNo == null) {
				Ext.MessageBox.alert('提示', '请从左边树选择用户！');
				return;
			}
			Ext.Ajax.request( {
				url : "qrystat/queryConsName!queryconsName.action",
				params : {
					consNo : trim(Ext.getCmp("general_consNo").getValue())
				},
				success : function(response) {
					var consName = Ext.decode(response.responseText).consName;
					var consType = Ext.decode(response.responseText).consType;
					if(!Ext.isEmpty(consType) && "5" == consType){
						Ext.MessageBox.show({
                        	title: '提示',
           					msg: '采集数据综合分析页面不支持台区居民用户，是否转入客户综合查询页面？',
           					width:300,
           					buttons: Ext.MessageBox.OKCANCEL,
           					fn: function(e){
           						if("ok" == e){
           					   	    opencsitab(trim(Ext.getCmp("general_consNo").getValue()),consId);
           					    }
           				    }
      			        });
      			        return;
					}
					gaConsName.setValue(consName);//添加户名
					if (Ext.isEmpty(consName)) {
						publicName = "";
					}else{
						publicName = consName;
					}
					generalAnalyseTotalStore.baseParams['consNo'] = consNo;
					generalAnalyseMPStore.baseParams['consNo'] = consNo;
					gaStore.baseParams = {
						consNo : consNo,
						dataDate : Ext.getCmp("general_date").getValue()
					};
					generalAnalyseMPStore.load();// 加载测量点
					generalAnalyseTotalStore.load();// 加载总加组
					gaStore.load();// 加载行业用户用电排名
		
					// tabPanel统计--总加组、终端冻结数据加载
					Ext.Ajax.request( {
						url : 'qrystat/tmnlAndTotalStat!queryTmnlAndTotalStat.action',
						params : {
							consNo : consNo,
							dataDate : dataDate
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							if (result.tmnlDayStat != null) {
								generalAnalysetabpanel1.getForm().setValues(
										result.tmnlDayStat);
							} else {
								generalAnalysetabpanel1.getForm().setValues( {
									daySuplyTime : '',
									dayResetNum : '',
									dayEcjumpNum : '',
									dayBcjumpNum : '',
									dayPcjumpNum : '',
									dayRcjumpNum : '',
									dayComm : ''
								});
							}
							if (result.tmnlMonStat != null) {
								generalAnalysetabpanel1.getForm().setValues(
										result.tmnlMonStat);
							} else {
								generalAnalysetabpanel1.getForm().setValues( {
									monSuplyTime : '',
									monResetNum : '',
									monEcjumpNum : '',
									monBcjumpNum : '',
									monPcjumpNum : '',
									monRcjumpNum : '',
									monComm : ''
								});
							}
							if (result.totalDayStat != null) {
								generalAnalysetabpanel2.getForm().setValues(
										result.totalDayStat);
							} else {
								generalAnalysetabpanel2.getForm().setValues( {
									dmaxAp : '',
									dmaxApTime : '',
									dminAp : '',
									dminApTime : '',
									dzeroApSumtime : '',
									daySume : '',
									daySumre : ''
								});
							}
							if (result.totalMonStat != null) {
								generalAnalysetabpanel2.getForm().setValues(
										result.totalMonStat);
							} else {
								generalAnalysetabpanel2.getForm().setValues( {
									mmaxAp : '',
									mmaxApTime : '',
									mminAp : '',
									mminApTime : '',
									mzeroApSumtime : '',
									monSume : '',
									monSumre : ''
								});
							}
						}
					});
				}
			});
			
		}

		// 采集数据综合分析顶层查询panel----------------------------------------------------------------
		// 定义radio选择组
//		var generalA_RadioGroup = new Ext.form.RadioGroup( {
//			width : 270,
//			height : 20,
//			items : [ new Ext.form.Radio( {
//				boxLabel : '功率',
//				name : 'apd-radioGroup',
//				inputValue : '1',
//				checked : true
//			}), new Ext.form.Radio( {
//				boxLabel : '电压',
//				name : 'apd-radioGroup',
//				inputValue : '2'
//			}), new Ext.form.Radio( {
//				boxLabel : '电流',
//				name : 'apd-radioGroup',
//				inputValue : '3'
//			}), new Ext.form.Radio( {
//				boxLabel : '功率因数',
//				name : 'apd-radioGroup',
//				inputValue : '4'
//			}) ]
//		});
		//曲线类型
		var curveTypeStore = new Ext.data.ArrayStore({
					fields : ['curveValue', 'curveName'],
					data : [['1', '功率'], ["2", '电压'],['3', '电流'], ["4", '功率因数']
					        , ["5", '电能量'], ["6", '电能示值']]
		});

		//曲线类型
		var curveTypeComboBox = new Ext.form.ComboBox({
					fieldLabel : '曲线类型',
					store : curveTypeStore,
					bodyStyle: 'padding:10px;',
					triggerAction : 'all',
					mode : 'local',
					valueField : 'curveValue',
					displayField : 'curveName',
					width: 90,
					value : 1,
					selectOnFocus : true,
					allowBlank : false,
					editable : false,
					labelSeparator:'',
					listeners : {
							'select' : function(c,r,i) {
								if (curTab == "mpPanel") {
								    if('5' == this.getValue() || '6' == this.getValue()){
								    	realCheckbox.setValue(false);
									    realCheckbox.setDisabled(true);
									    generalCheckbox.setValue(true);
								    }else{
									    realCheckbox.setDisabled(false);
									    realCheckbox.setValue(true);
									    generalCheckbox.setValue(false);
								    }
								}else if (curTab == "totalPanel") {
									if('1' != this.getValue()){
										curveTypeComboBox.setValue(1);
										Ext.MessageBox.alert('提示', '总加组只支持查询功率曲线！');
				                        return;
									}
								}
							}
					}
		});

		// 定义checkBox,用于是否显示冻结数据
		var generalCheckbox = new Ext.form.Checkbox( {
			boxLabel : '冻结',
			name : 'statCurve',
			inputValue : 1
		});
		
		// 定义checkBox,用于是否显示冻结数据
		var realCheckbox = new Ext.form.Checkbox( {
			boxLabel : '实时',
			checked : true,
			name : 'realCurve',
			inputValue : 1
		});

		// 查询日期
		var ga_general_date = new Ext.form.DateField( {
			id : 'general_date',
			fieldLabel : '日期',
			name : 'dataDate',
			labelStyle : "text-align:right;",
			format : 'Y-m-d',
			labelSeparator : '',
			editable : false,
			value : new Date(),
			allowBlank : false,
			anchor : '90%'
		});
		
		// 户号
		var gaConsNo = new Ext.form.TextField({
					fieldLabel : "户号<font color='red'>*</font>",
					labelStyle : "text-align:right;",
					id : 'general_consNo',
					name:'gaConsNo',
					emptyText : '请从左边树选择用户',
					labelSeparator : "",
					readOnly:true,
					anchor : '95%'
				});
				
		// 户名
		var gaConsName = new Ext.form.TextField({
					fieldLabel : "户名",
					labelStyle : "text-align:right;",
					id : 'general_consName',
					name:'gaConsName',
					emptyText : '户名',
					labelSeparator : "",
					readOnly:true,
					anchor : '95%'
				});
				
		 gaConsNo.on('change', function(){
		 	generalAnalyseMPStore.removeAll();
				generalAnalyseTotalStore.removeAll();
				curveData_gridStore.removeAll();
				gaConsName.setValue("");
				dataXml = getGAPowerMltXMLData('', '', '', '', '', '', '', '', '', '', '',
			        '', '', '', '', '', '', '', '', '', '', 4, '', '', '');
				generalAnalyseChart = new FusionCharts(
								"fusionCharts/MSLine.swf",
									"generalAnalyseChartId", width, height);
				generalAnalyseChart.setDataXML(dataXml);
				generalAnalyseChart.setTransparent(true);
				generalAnalyseChart.render(generalAnalyse3DPanel
							.getId());// 渲染
		 });

		// 查询条件上部分panel
		var generalAnalyseNorthPanel = new Ext.Panel(
				{
					border : false,
					plain : true,
					region : 'north',
					height : 30,
					items : [ {
						layout : "column",
						style : "padding:1px",
						border : false,
						items : [
								{
									columnWidth : .18,
									layout : "form",
									labelWidth : 35,
									border : false,
									bodyStyle : 'padding:3px 0px 0px 0px',
									items : [gaConsNo]
								},
								{
									columnWidth : .18,
									layout : "form",
									labelWidth : 25,
									border : false,
									bodyStyle : 'padding:3px 0px 0px 0px',
									items : [ gaConsName ]
								},
								{
									columnWidth : .20,
									layout : "form",
									labelWidth : 55,
									border : false,
									bodyStyle : 'padding:3px 0px 0px 0px',
									items : [ curveTypeComboBox ]
								},
								{
									columnWidth : .44,
									layout : "column",
									border : false,
									items : [
											{
												columnWidth : .18,
												layout : "form",
												labelWidth : 1,
												border : false,
												bodyStyle : 'padding:3px 0px 0px 0px',
												items : [ generalCheckbox ]
											},
											{
												columnWidth : .18,
												layout : "form",
												labelWidth : 1,
												border : false,
												bodyStyle : 'padding:3px 0px 0px 0px',
												items : [ realCheckbox ]
											},
											{
												columnWidth : .45,
												layout : "form",
												labelWidth : 40,
												bodyStyle : 'padding:3px 0px 0px 0px',
												border : false,
												items : [ ga_general_date ]
											},
											{
												columnWidth : .19,
												layout : "form",
												defaultType : "button",
												bodyStyle : 'padding:3px 0px 0px 0px',
												border : false,
												items : [ {
													text : "查询",
													listeners : {
														"click" : function() {
															var consNo = trim(Ext
																	.getCmp(
																			"general_consNo")
																	.getValue());
															var dataDate = Ext
																	.getCmp(
																			"general_date")
																	.getValue();
															queryEvent(consNo,
																	dataDate);
														}
													},
													width : 60
												} ]
											}]
								} ]
					} ]
				});

		// --------------------加入群组调用函数---------------------
		function groupComboxWindowShow(consNo) {
			if (consNo == "" || consNo == null) {
				Ext.MessageBox.alert('提示', '请从左边树选择用户！');
				return;
			}
			var groupStore = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : 'qrystat/queryGroup!queryGroupName.action'
				}),
				reader : new Ext.data.JsonReader( {
					root : 'groupList'
				}, [ {
					name : 'groupNo'
				}, {
					name : 'groupName'
				} ])
			});
			groupStore.load();
			var groupComboBox = new Ext.form.ComboBox( {
				store : groupStore,
				xtype : 'combo',
				name : 'combo_groupName',
				fieldLabel : '群组名称',
				valueField : 'groupNo',
				editable : false,
				triggerAction : 'all',
				forceSelection : true,
				mode : 'remote',
				selectOnFocus : true,
				displayField : 'groupName',
				emptyText : '--请选择群组--',
				labelSeparator : '',
				anchor : '85%'
			});

			var groupPanel = new Ext.Panel( {
				border : true,
				layout : 'form',
				height : 100,
				bodyStyle : 'padding:15px 0px 0px 0px',
				buttonAlign : 'center',
				items : [ {
					bodyStyle : 'padding:0px 0px 0px 20px',
					border : false,
					layout : 'form',
					labelSeparator : '',
					labelWidth : 50,
					items : [ groupComboBox ]
				} ],
				buttons : [ {
					text : "加入群组",
					listeners : {
						"click" : function() {
							var groupNo = groupComboBox.getValue();
							if (groupNo == "" || groupNo == null) {
								Ext.MessageBox.alert('提示', '请选择群组！');
								return;
							}
							Ext.Ajax.request( {
								url : './qrystat/addToGroup!addToGroup.action',
								params : {
									consNo : consNo,
									groupNo : groupNo
								},
								success : function() {
									Ext.MessageBox.alert("提示", "加入群组成功");
									return;
								},
								failure : function() {
									Ext.MessageBox.alert("提示", "加入群组失败");
									return;
								}
							});
						}
					},
					width : 50
				} ]
			});

			var groupWimdow = new Ext.Window( {
				title : '用户加入群组',
				name : 'group',
				height : 130,
				width : 320,
				closeAction : 'hide',
				resizable : false,
				layout : 'form',
				items : [ groupPanel ]
			});
			groupWimdow.show();
		}

		// 采集数据综合分析左上角-负荷特性页面panel----------------------------------------------------------------

		// 负荷峰值
		var ga_maxTmnl = new Ext.form.TextField( {
			fieldLabel : "负荷峰值",
			name : 'maxTmnl',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 负荷峰值单位
		var ga_maxTmnlUnit = new Ext.form.Label( {
			text : '(kW)',
			labelSeparator : '',
			readOnly : true,
			style : 'font-size: 8pt',
			width : 10
		});

		// 峰值时间
		var ga_maxTmnlTime = new Ext.form.TextField( {
			fieldLabel : "峰值时间",
			name : 'maxTmnlTime',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 谷值时间
		var ga_minTmnlTime = new Ext.form.TextField( {
			fieldLabel : "谷值时间",
			name : 'minTmnlTime',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 负荷谷值
		var ga_minTmnl = new Ext.form.TextField( {
			fieldLabel : "负荷谷值",
			name : 'minTmnl',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 负荷谷值单位
		var ga_minTmnlUnit = new Ext.form.Label( {
			text : '(kW)',
			labelSeparator : '',
			readOnly : true,
			style : 'font-size: 8pt',
			width : 10
		});

		// 峰谷比
		var ga_maxAndMinProp = new Ext.form.TextField( {
			fieldLabel : "峰谷比",
			name : 'maxAndMinProp',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 峰谷比单位
		var ga_maxAndMinPropUnit = new Ext.form.Label( {
			text : '(%)',
			labelSeparator : '',
			style : 'font-size: 8pt',
			readOnly : true,
			width : 10
		});

		// 峰谷差率
		var ga_maxAndMinDiff = new Ext.form.TextField( {
			fieldLabel : "峰谷差率",
			name : 'maxAndMinDiff',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 峰谷差率单位
		var ga_maxAndMinDiffUnit = new Ext.form.Label( {
			text : '(%)',
			labelSeparator : '',
			style : 'font-size: 8pt',
			readOnly : true,
			width : 10
		});

		// 负荷率
		var ga_loadFactor = new Ext.form.TextField( {
			fieldLabel : "负荷率",
			name : 'loadFactor',
			labelSeparator : '',
			readOnly : true,
			labelStyle : "text-align:right;",
			emptyText : '',
			anchor : '100%'
		});

		// 负荷率单位
		var ga_loadFactorUnit = new Ext.form.Label( {
			text : '(%)',
			labelSeparator : '',
			style : 'font-size: 8pt',
			readOnly : true,
			width : 10
		});

		// 对比日期
		var ga_contrast_date = new Ext.form.DateField( {
			id : 'contrast_date',
			fieldLabel : '日期',
			name : 'ga_contrast_date',
			labelStyle : "text-align:right;",
			format : 'Y-m-d',
			editable: false,
			labelSeparator : '',
			value : new Date().add(Date.DAY, -7),
			disabled:true,
			anchor : '85%'
		});
		
		// 定义checkBox,用于是否显示对比日数据
		var conCheckBox = new Ext.form.Checkbox( {
			boxLabel : '',
			name : 'conCheckBox',
			inputValue : 1,
			listeners:{
			    'check' : function(c) {
			        if (c.checked) {
			        	ga_contrast_date.enable();
			        }else{
			        	ga_contrast_date.disable();
			        }
		        }
		    }
		});

		var generalAnalyseTmnlPanel = new Ext.FormPanel( {
			region : 'north',
			layout : "form",
			labelAlign : 'right',
			labelSeparator : ' ',
			labelWidth : 80,
			defaults : {
				anchor : '98%'
			},
			height : 250,
			plain : true,
			border : true,
			items : [ {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				bodyStyle : 'padding:2px 0px 0px 0px',
				border : false,
				items : [ {
					columnWidth : .85,
					border : false,
					layout : "form",
					items : [ ga_maxTmnl ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ ga_maxTmnlUnit ]
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				bodyStyle : 'padding:2px 0px 0px 0px',
				border : false,
				items : [ {
					columnWidth : .85,
					layout : "form",
					border : false,
					items : [ ga_maxTmnlTime ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : []
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				border : false,
				items : [ {
					columnWidth : .85,
					border : false,
					layout : "form",
					items : [ ga_minTmnl ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ ga_minTmnlUnit ]
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				bodyStyle : 'padding:2px 0px 0px 0px',
				border : false,
				items : [ {
					columnWidth : .85,
					layout : "form",
					border : false,
					items : [ ga_minTmnlTime ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : []
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				border : false,
				items : [ {
					columnWidth : .85,
					border : false,
					layout : "form",
					items : [ ga_maxAndMinProp ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ ga_maxAndMinPropUnit ]
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				border : false,
				items : [ {
					columnWidth : .85,
					border : false,
					layout : "form",
					items : [ ga_maxAndMinDiff ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ ga_maxAndMinDiffUnit ]
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				labelWidth : 50,
				border : false,
				items : [ {
					columnWidth : .85,
					border : false,
					layout : "form",
					items : [ ga_loadFactor ]
				}, {
					columnWidth : .15,
					layout : "form",
					border : false,
					bodyStyle : 'padding:3px 0px 0px 0px',
					items : [ ga_loadFactorUnit ]
				} ]
			}, {
				layout : "column",
				labelSeparator : '',
				bodyStyle : 'padding:2px 0px 0px 0px',
				border : false,
				items : [ {
					columnWidth : .12,
					layout : "form",
					labelWidth : 1,
					border : false,
					items : [conCheckBox]
				}, {
					columnWidth : .88,
					layout : "form",
					labelWidth : 25,
					border : false,
					items : [ ga_contrast_date ]
				} ]
			} ],
            tbar : [{
					xtype:'label',
					html : "<font font-weight:bold;>负荷特性</font>"
			}, {xtype: 'tbfill'},'-',
				{
					text : '加入群组',
					iconCls: 'plus',
					handler : function() {
				        var consNo = trim(Ext.getCmp("general_consNo").getValue());
						groupComboxWindowShow(consNo);
					}
			 }]
		});

		// 总加组对应panel-----------------------------------------------start
		var generalAnalyseTotalStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'qrystat/datatotal!queryTotal.action'
			}),
			baseParams : {
				consNo : ''
			},
			reader : new Ext.data.JsonReader( {
				root : 'dataTotalList',
				totalProperty : 'totalCount'
			}, [ {
				name : 'totalId'
			}, {
				name : 'rowNum'
			}, {
				name : 'tmnlAssetNo'
			}, {
				name : 'freezeCycleNum'
			}, {
				name : 'totalNo'
			} ])
		});

		generalAnalyseTotalStore.on("load", function() {
			if (curTab == "mpPanel") {
				return;
			}
			var dataDate = Ext.getCmp("general_date").getValue();
			var contrastDate = Ext.getCmp("contrast_date").getValue();
			var curveType = curveTypeComboBox.getValue();
			var statCheckValue = generalCheckbox.getValue();
			var conCheckValue = conCheckBox.getValue();
			var realCheckValue = realCheckbox.getValue();
			if (this.getCount() == 0) {
				totalClick('-1', 0,dataDate, contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue);
			} else {
				totalClick(this.getAt(0).get("totalId"), this.getAt(0).get("freezeCycleNum"), dataDate,
						contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue);
			}
		});

		var generalAnalyseTotalPanel = new Ext.grid.GridPanel( {
			title : '总加组',
			name : 'totalPanel',
			layout : 'fit',
			anchor : '100%',
			border : false,
			stripeRows : true,
			autoScroll : true,
			viewConfig : {
				forceFit : false
			},
			enableColumnMove : false,// 设置表头不可拖动
			colModel : new Ext.grid.ColumnModel( [ {
				header : "编号",
				width : 41,
				sortable : true,
				dataIndex : 'rowNum',
				align : "center"
			},// 使下拉框消失
					{
						header : "终端资产编号",
						width : 95,
						dataIndex : 'tmnlAssetNo',
						sortable : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "总加组号",
						dataIndex : 'totalNo',
						sortable : true,
						width : 61,
						align : "center"
					} ]),
			ds : generalAnalyseTotalStore
		});
		// 总加组列表双击事件
		generalAnalyseTotalPanel.addListener('cellclick', function(grid,
				rowIndex, columnIndex, e) {
			var totalId = grid.getStore().getAt(rowIndex).get("totalId");
			var freezeCycleNum = grid.getStore().getAt(rowIndex).get("freezeCycleNum");
			var dataDate = Ext.getCmp("general_date").getValue();
			var contrastDate = Ext.getCmp("contrast_date").getValue();
			var curveType = curveTypeComboBox.getValue();
			var statCheckValue = generalCheckbox.getValue();
			var conCheckValue = conCheckBox.getValue();
			var realCheckValue = realCheckbox.getValue();
			totalClick(totalId, freezeCycleNum, dataDate, contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue);
		});
		// 总加组对应panel-----------------------------------------------end

		// 测量点对应panel----------------------------------------------start
		var generalAnalyseMPStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'qrystat/datamp!queryMp.action'
			}),
			baseParams : {
				consNo : ''
			},
			reader : new Ext.data.JsonReader( {
				root : 'dataMpList',
				totalProperty : 'totalCount'
			}, [ {
				name : 'dataId'
			}, {
				name : 'tmnlAddr'
			}, {
				name : 'freezeCycleNum'
			}, {
				name : 'meterNo'
			}, {
				name : 'mpNo'
			}, {
				name : 'mpName'
			}, {
				name : 'dataSrc'
			} ])
		});

		generalAnalyseMPStore.on("load", function() {
			if (curTab == "totalPanel") {
				return;
			}
			var dataDate = Ext.getCmp("general_date").getValue();
			var contrastDate = Ext.getCmp("contrast_date").getValue();
			var curveType = curveTypeComboBox.getValue();
			var statCheckValue = generalCheckbox.getValue();
			var conCheckValue = conCheckBox.getValue();
			var realCheckValue = realCheckbox.getValue();
			if (this.getCount() == 0) {
				mpClick('-1','', 0,dataDate, contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue);
			} else {
				mpClick(this.getAt(0).get("dataId"), this.getAt(0).get("meterNo"), this.getAt(0).get("freezeCycleNum"), dataDate, contrastDate,
						curveType, statCheckValue, conCheckValue, realCheckValue);
			}
		});

		var generalAnalyseMPPanel = new Ext.grid.GridPanel( {
			title : '测量点',
			layout : 'fit',
			name : 'mpPanel',
			anchor : '100%',
			border : false,
			stripeRows : true,
			autoScroll : true,
			viewConfig : {
				forceFit : false
			},
			enableColumnMove : false,// 设置表头不可拖动
			colModel : new Ext.grid.ColumnModel( [ {
				header : "终端地址",
				width : 70,
				dataIndex : 'tmnlAddr',
				sortable : true,
				align : "center"
			},// 使下拉框消失
					{
						header : "表计资产",
						// menuDisabled : true,
						width : 70,
						dataIndex : 'meterNo',
						sortable : true,
						align : "center",
						renderer : function(val) {
							if(Ext.isEmpty(val)){
								val = "";
							}
							var html = '<div align = "center" ext:qtitle="表计资产" ext:qtip="' + val
							    + '">' + val + '</div>';
							return html;
				        }
					},
					{
						header : "计量点编号",
						width : 70,
						dataIndex : 'mpNo',
						sortable : true,
						align : "center"
					} ,
					{
						header : "计量点名称",
						width : 70,
						dataIndex : 'mpName',
						sortable : true,
						align : "center"
					} ,
					{
						header : "数据来源",
						width : 70,
						dataIndex : 'dataSrc',
						sortable : true,
						align : "center"
					} ]),
			ds : generalAnalyseMPStore
		});
		// 测量点列表双击事件
		generalAnalyseMPPanel.addListener('cellclick', function(grid,
				rowIndex, columnIndex, e) {
			var dataId = grid.getStore().getAt(rowIndex).get("dataId");
			var meterNo = grid.getStore().getAt(rowIndex).get("meterNo");
			var freezeCycleNum = grid.getStore().getAt(rowIndex).get("freezeCycleNum");
			var dataDate = Ext.getCmp("general_date").getValue();
			var contrastDate = Ext.getCmp("contrast_date").getValue();
			var curveType = curveTypeComboBox.getValue();
			var statCheckValue  = generalCheckbox.getValue();
			var conCheckValue = conCheckBox.getValue();
			var realCheckValue = realCheckbox.getValue();
			mpClick(dataId, meterNo, freezeCycleNum, dataDate, contrastDate, curveType, statCheckValue, conCheckValue, realCheckValue);
		});
		// 测量点对应panel----------------------------------------------end

		// 同行业用户用电排名------------------------------------------------------
		var gaStore = new Ext.data.Store( {
			proxy : new Ext.data.HttpProxy( {
				url : 'qrystat/sameTradeCCons!querySameTradeCCons.action'
			}),
			baseParams : {
				consNo : ''
			},
			reader : new Ext.data.JsonReader( {
				root : 'sameTradeCConsList',
				totalProperty : 'totalCount'
			}, [ {
				name : 'rowindex'
			}, {
				name : 'orgName'
			}, {
				name : 'consNo'
			}, {
				name : 'consName'
			}, {
				name : 'consId'
			}, {
				name : 'elecAddr'
			}, {
				name : 'runCap'
			}, {
				name : 'tradeName'
			}, {
				name : 'volt'
			}, {
				name : 'elecType'
			}, {
				name : 'tmnlAssetNo'
			}, {
				name : 'assetNo'
			}, {
				name : 'papE'
			}, {
				name : 'maxP'
			}, {
				name : 'maxPTime'
			}, {
				name : 'minP'
			}, {
				name : 'minPTime'
			} ])
		});

		var generalAnalyseSTPanel = new Ext.grid.GridPanel(
				{
					stripeRows : true,
					autoScroll : true,
					border : false,
					enableColumnMove : false,// 设置表头不可拖动
					bbar : new Ext.ux.MyToolbar( {
						enableExpAll : true,
						store : gaStore
					}),
					ds : gaStore,
					colModel : new Ext.grid.ColumnModel(
							[
									{
										header : "排名",
										width : 50,
										dataIndex : 'rowindex',
										sortable : true,
										align : "center"
									},
									{
										header : "供电单位",
										dataIndex : 'orgName',
										sortable : true,
										width : 150,
										align : "center"
									},
									{
										header : '用户编号',
										dataIndex : 'consNo',
										sortable : true,
										align : 'center',
										renderer : function(s, m, rec) {
											return "<a href='javascript:' onclick='opencsitab(\""
													+ rec.get('consNo')
													+ "\",\""
													+ rec.get('consId')
													+ "\")'>"
													+ rec.get('consNo')
													+ "</a>";
										}
									},
									{
										header : "用户名称",
										dataIndex : 'consName',
										sortable : true,
										width : 120,
										align : "center"
									},
									{
										header : "用电地址",
										hidden : true,
										dataIndex : 'elecAddr',
										sortable : true,
										width : 150,
										align : "center"
									},
									{
										header : "运行容量",
										dataIndex : 'runCap',
										sortable : true,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "right">' + val + '</div>';
											return html;
										}
									},
									{
										header : "正向有功总电能量",
										dataIndex : 'papE',
										sortable : true,
										width : 120,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "right">' + val + '</div>';
											return html;
										}
									},
									{
										header : "最大有功功率",
										dataIndex : 'maxP',
										sortable : true,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "right">' + val + '</div>';
											return html;
										}
									},
									{
										header : "行业名称",
										dataIndex : 'tradeName',
										sortable : true,
										width : 160,
										align : "center"
									},
									{
										header : "电压等级",
										dataIndex : 'volt',
										sortable : true,
										align : "center"
									},
									{
										header : "用电类别",
										dataIndex : 'elecType',
										sortable : true,
										align : "center"
									},
									{
										header : "终端资产编号",
										dataIndex : 'tmnlAssetNo',
										sortable : true,
										align : "center"
									},
									{
										header : "资产编号",
										dataIndex : 'assetNo',
										sortable : true,
										align : "center"
									},
									{
										header : "最大有功功率发生时间",
										hidden : true,
										dataIndex : 'maxPTime',
										width : 140,
										sortable : true,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "left">' + val + '</div>';
											return html;
										}
									},
									{
										header : "最小有功功率",
										hidden : true,
										dataIndex : 'minP',
										sortable : true,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "right">' + val + '</div>';
											return html;
										}
									},
									{
										header : "发生时间",
										hidden : true,
										dataIndex : 'minPTime',
										sortable : true,
										width : 140,
										align : 'center',
										renderer : function(val) {
											if (null == val) {
												val = "";
											}
											var html = '<div align = "left">' + val + '</div>';
											return html;
										}
									} ]),
					viewConfig : {
						forceFit : false
					}
				});
		// 定义三维图标panel-------------------------------------------------------------
		var generalAnalyse3DPanel = new Ext.Panel( {
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : false
		});

		// afterlayout初始化时会加载3次，之后如果页面有变化，会加载2次，第一次是原来的值，第二次是变化后的值
		// 用于实现fusionchart动态变化
		generalAnalyse3DPanel.on("afterlayout", function(view, layout) {
			width = generalAnalyseCurvePanel.getWidth();
			height = generalAnalyseCurvePanel.getHeight() - 4;
			generalAnalyseChart = new FusionCharts("fusionCharts/MSLine.swf",
					"generalAnalyseChartId", width, height);
			generalAnalyseChart.setDataXML(dataXml);
			generalAnalyseChart.setTransparent(true);
			generalAnalyseChart.render(generalAnalyse3DPanel.getId());// 渲染
			}, generalAnalyse3DPanel);

		// 采集数据综合分析曲线图
		var generalAnalyseCurvePanel = new Ext.Panel( {
			title : "曲线图",
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : true,
			style : "padding:2px",
			items : [ generalAnalyse3DPanel ]
		});
		
		// 采集数据综合分析曲线数据
		var curveData_gridStore = new Ext.data.Store({
		 	        proxy : new Ext.data.MemoryProxy(),
					reader : new Ext.data.JsonReader({
								root : 'generalCurveDataList'
							}, [
							   {name : 'time'},
							   {name : 'data1'},
							   {name : 'data2'},
							   {name : 'data3'}, 
							   {name : 'data4'}, 
							   {name : 'data5'}, 
							   {name : 'data6'}, 
							   {name : 'data7'},
							   {name : 'data8'},
							   {name : 'data9'},
							   {name : 'data10'}, 
							   {name : 'data11'}, 
							   {name : 'data12'},
							   {name : 'data13'},
							   {name : 'data14'},
							   {name : 'data15'},
							   {name : 'data16'}
							   ])
			});
	
		curveDataCm = new Ext.grid.ColumnModel([{
					header : '时间点',
					dataIndex : 'time',
					sortable : true,
					width: 160,
					align : 'center'
				}, {
					header : '查询日实时总功率(kW)',
					dataIndex : 'data1',
					sortable : true,
					width: 160,
					align : 'center'
				}, {
					header : '查询日实时A相功率(kW)',
					dataIndex : 'data2',
					sortable : true,
					width: 160,
					align : 'center'
				}, {
					header : '查询日实时B相功率(kW)',
					dataIndex : 'data3',
					sortable : true,
					width: 160,
					align : 'center'
				}, {
					header : '查询日实时C相功率(kW)',
					dataIndex : 'data4',
					sortable : true,
					width: 160,
					align : 'center'
				}]);
	
		var curveData_gridPanel = new Ext.grid.GridPanel({
			        autoScroll : true,
					stripeRows : true,
					viewConfig : {
						forceFit : false
					},
					cm : curveDataCm,
					store : curveData_gridStore
				});
				
		var generalAnalyseCurveDataPanel = new Ext.Panel( {
			autoScroll : true,
			border : false,
			bodyBorder : false,
			layout : 'fit',
			monitorResize : true,
			style : "padding:2px",
			items : [ curveData_gridPanel ]
		});

		// 终端冻结数据tab panel
		var generalAnalysetabpanel1 = new Ext.FormPanel(
				{
					layout : "column",
					baseCls : "x-plain",
					autoScroll : true,
					items : [
							{
								columnWidth : .5,
								layout : "form",
								autoScroll : true,
								defaultType : "textfield",
								labelWidth : 140,
								border : false,
								items : [
										{
											xtype : 'label',
											style : 'font-size: 10pt',
											html : "<font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:generalAnalyseWindow1Show(1)'>日冻结终端统计>></a></font><br>"
										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'daySuplyTime',
											fieldLabel : "终端供电时间(min)"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayResetNum',
											fieldLabel : "终端复位累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayEcjumpNum',
											fieldLabel : "月电控跳闸日累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayBcjumpNum',
											fieldLabel : "购电控跳闸日累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayPcjumpNum',
											fieldLabel : "功控跳闸日累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayRcjumpNum',
											fieldLabel : "遥控跳闸日累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'dayComm',
											fieldLabel : "终端与主站日通信流量(k)"

										} ]
							},
							{
								columnWidth : .5,
								layout : "form",
								labelWidth : 100,
								autoScroll : true,
								defaultType : "textfield",
								labelWidth : 140,
								border : false,
								items : [
										{
											xtype : 'label',
											style : 'font-size: 10pt',
											html : "<font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:generalAnalyseWindow1Show(2)'>月冻结终端统计>></a></font><br>"

										}, {
											labelStyle : "text-align:right;",
											labelSeparator : "",
											width : 120,
											name : 'monSuplyTime',
											fieldLabel : "终端供电时间(min)"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monResetNum',
											fieldLabel : "终端复位累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monEcjumpNum',
											fieldLabel : "月电控跳闸月累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monBcjumpNum',
											fieldLabel : "购电控跳闸月累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monPcjumpNum',
											fieldLabel : "功控跳闸月累计次数"
										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monRcjumpNum',
											fieldLabel : "遥控跳闸月累计次数"

										}, {
											labelStyle : "text-align:right;",
											width : 120,
											labelSeparator : "",
											name : 'monComm',
											fieldLabel : "终端与主站月通信流量(k)"

										} ]
							} ]
				});

		// 总加组冻结数据tab panel
		var generalAnalysetabpanel2 = new Ext.FormPanel(
				{
					layout : "column",
					autoScroll : true,
					baseCls : "x-plain",
					items : [
							{
								columnWidth : .5,
								layout : "form",
								labelWidth : 140,
								autoScroll : true,
								defaultType : "textfield",
								border : false,
								items : [
										{
											xtype : 'label',
											style : 'font-size: 10pt',
											html : "<font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:' onclick='generalAnalyseWindow2Show(1)'>日冻结总加组统计>></a></font><br>"

										},
										{
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'dmaxAp',
											fieldLabel : "日最大有功功率"

										},
										{
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'dmaxApTime',
											fieldLabel : "日最大有功功率发生时间",
											renderer : Ext.util.Format
													.dateRenderer('hh:mm:ss')
										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'dminAp',
											fieldLabel : "日最小有功功率"
										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'dminApTime',
											fieldLabel : "日最小有功功率发生时间"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'dzeroApSumtime',
											fieldLabel : "功率为零月累计时间"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'daySume',
											fieldLabel : "日累计有功总电能量"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'daySumre',
											fieldLabel : "日累计无功总电能量"

										} ]
							},
							{
								columnWidth : .5,
								layout : "form",
								labelWidth : 140,
								autoScroll : true,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [
										{
											xtype : 'label',
											style : 'font-size: 10pt',
											html : "<font color='blue'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:' onclick='generalAnalyseWindow2Show(2)'>月冻结总加组统计>></a></font><br>"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'mmaxAp',
											fieldLabel : "月最大有功功率"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'mmaxApTime',
											fieldLabel : "月最大有功功率发生时间"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'mminAp',
											fieldLabel : "月最小有功功率"
										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'mminApTime',
											fieldLabel : "月最小有功功率发生时间"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'mzeroApSumtime',
											fieldLabel : "功率为零月累计时间"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'monSume',
											fieldLabel : "月累计有功总电能量"

										}, {
											labelStyle : "text-align:right;",
											width : 130,
											labelSeparator : "",
											name : 'monSumre',
											fieldLabel : "月累计无功总电能量"

										} ]
							} ]
				});
		// 测量点数据
		var generalAnalysetabpanel3 = new Ext.Panel(
				{
					layout : "column",
					autoScroll : true,
					border : false,
					items : [
							new Ext.Panel(
									{
										columnWidth : .5,
										layout : "form",
										autoScroll : true,
										defaultType : "label",
										style : 'font-size: 10pt',
										bodyStyle : 'padding:0px 0px 0px 10px',
										border : false,
										items : [
												{
													html : "<br><a href='javascript:mpRdayReadWindowShow()'>抄表日冻结电能示值>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayPowerWindowShow()'>日冻结总及分相有功功率数据>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayVstatWindowShow()'>日冻结电压统计数据>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayIstatWindowShow()'>日冻结电流越限统计数据>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayReadWindowShow()'>日冻结电能示值>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayEnergyWindowShow()'>日冻结电能量>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayCompWindowShow()'>日电容器累计补偿的无功电能量>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonVstatWindowShow()'>月冻结电压统计数据>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonReadAndEnergyWindowShow()'>月冻结电能示值及电能量>></a><br><br>"
												},
												{
													html : "<hr></hr>"
												},
												{
													html : "<br><a href='javascript:mpRealDataReadWindowShow()'>当前电能示值>></a><br><br>"
												} ]
									}),
							new Ext.Panel(
									{
										columnWidth : .5,
										layout : "form",
										autoScroll : true,
										defaultType : "label",
										baseCls : "x-plain",
										style : 'font-size: 10pt',
										bodyStyle : 'padding:0px 0px 0px 0px',
										items : [
												{
													html : "<br><a href='javascript:mpRdayDemanWindowShow()'>抄表日冻结需量及需量发生时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayDemanWindowShow()'>日冻结需量及需量发生时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayPftimeWindowShow()'>日冻结功率因数区段累计时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpDayUnbalanceWindowShow()'>日不平衡度越限累计时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonUnbalanceWindowShow()'>月不平衡度越限累计时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonPftimeWindowShow()'>月冻结功率因数区段累计时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonDemanWindowShow()'>月冻结最大需量及发生时间>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonPowerWindowShow()'>月冻结总及分相有功功率数据>></a><br><br>"
												},
												{
													html : "<a href='javascript:mpMonIstatWindowShow()'>月冻结电流越限统计数据>></a><br><br>"
												}, {
													html : "<hr></hr>"
												},
												{
													html : "<br><a href='javascript:mpRealCurveDataWindowShow()'>当前测量点曲线数据>></a><br><br>"
												} ]
									}) ]
				});

		// 右下角tabpanel
		var generalAnalyseRTabPanel = new Ext.TabPanel( {
			activeTab : 0,
			border : false,
			height : 500,
			items : [ generalAnalyseCurvePanel
			, {
				title : '曲线数据',
				border : false,
				baseCls : "x-plain",
				layout : 'fit',
				autoScroll : true,
				items : [generalAnalyseCurveDataPanel]
			}, {
				title : '测量点数据',
				border : false,
				layout : 'form',
				autoScroll : true,
				items : [ generalAnalysetabpanel3 ]
			}, {
				title : '终端冻结数据',
				border : false,
				baseCls : "x-plain",
				autoScroll : true,
				items : [ generalAnalysetabpanel1 ]
			}, {
				title : '总加组冻结数据',
				border : false,
				baseCls : "x-plain",
				autoScroll : true,
				items : [ generalAnalysetabpanel2 ]
			}, {
				title : "同行业用户",
				border : false,
				layout : 'fit',
				baseCls : "x-plain",
				autoScroll : true,
				items : [ generalAnalyseSTPanel ]
			}]
		});

		// 包装中间面板左边和右边的Tabpanel-------------------------------------------------
		var generalAnalyseLTabPanel = new Ext.TabPanel( {
			region : 'center',
			activeItem : 0,
			items : [ generalAnalyseMPPanel, generalAnalyseTotalPanel ]
		});
		generalAnalyseLTabPanel.on('tabchange', function(t, p) {
			if (p.name == "totalPanel") {
				curTab = "totalPanel";
				realCheckbox.setDisabled(false);
				curveTypeComboBox.setValue(1);
				realCheckbox.setValue(true);
				generalCheckbox.setValue(false);
			} else {
				curTab = "mpPanel";
				curveTypeComboBox.setValue(1);
				realCheckbox.setValue(true);
				generalCheckbox.setValue(false);
			}
		}, this);

		var generalAnalyseBorderPanel = new Ext.Panel( {
			border : false,
			plain : true,
			layout : 'border',
			region : 'center',
			items : [ {
				region : 'west',
				width : 200,
				layout : "border",
				baseCls : "x-plain",
				items : [ generalAnalyseTmnlPanel, generalAnalyseLTabPanel ]
			}, {
				region : 'center',
				width : 200,
				layout : "fit",
				items : [ generalAnalyseRTabPanel ]
			} ]
		});
		// 设置顶层的采集数据综合分析panel
		var generalAnalysepanel = new Ext.Panel( {
			autoScroll : true,
			layout : 'border',
			border : false,
			items : [ generalAnalyseNorthPanel, generalAnalyseBorderPanel ]
		});
		renderModel(generalAnalysepanel, '采集数据综合分析');

		
		  // *****************mengyuan
		//typeof(staticOverTrafficOrgNo) != 'undefined'
		//!Ext.isEmpty(consNo_terminalRunStatus)
		
		if (typeof(consNo_terminalRunStatus) != 'undefined'&&!Ext.isEmpty(consNo_terminalRunStatus)) {
			Ext.getCmp("general_consNo").setValue(consNo_terminalRunStatus);
		}
		if (typeof(consNo_newMpDayPower) != 'undefined'&&!Ext.isEmpty(consNo_newMpDayPower)) {
			Ext.getCmp("general_consNo").setValue(consNo_newMpDayPower);
		}
		Ext.getCmp('采集数据综合分析').on('activate', function() {
			if (typeof(consNo_terminalRunStatus) != 'undefined'&&!Ext.isEmpty(consNo_terminalRunStatus)) {
				Ext.getCmp("general_consNo").setValue(consNo_terminalRunStatus);
			}
		});
		Ext.getCmp('采集数据综合分析').on('beforeclose', function(p) {
			if (typeof(consNo_terminalRunStatus) != 'undefined'&&!Ext.isEmpty(consNo_terminalRunStatus)) {
				consNo_terminalRunStatus = '';
				consNo_newMpDayPower = '';
			}
		});
		// *****************mengyuan

		
		
		// 监听左边树点击事件
		var treeListener = new LeftTreeListener( {
			modelName : '采集数据综合分析',
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				if (type == 'usr') {
					if(!Ext.isEmpty(obj.consType) && '5' == obj.consType){
						Ext.MessageBox.show({
	                        title: '提示',
	           				msg: '采集数据综合分析页面不支持台区居民用户，是否转入客户综合查询页面？',
	           				width:300,
	           				buttons: Ext.MessageBox.OKCANCEL,
	           				fn: function(e){
	           					if("ok" == e){
	           					    opencsitab(obj.consNo,obj.consId);
	           					}
	           				}
	      			     });
					}else{
						publicName = node.text;
						gaConsNo.setValue(obj.consNo);//添加户号
						gaConsName.setValue(obj.consName);//添加户名
						consId = obj.consId;
						//清空曲线图，曲线数据，负荷特性
						generalAnalyseMPStore.removeAll();
						generalAnalyseTotalStore.removeAll();
						curveData_gridStore.removeAll();
						dataXml = getGAPowerMltXMLData('', '', '', '', '', '', '', '', '', '', '',
				            '', '', '', '', '', '', '', '', '', '', 4, '', '', '');
						generalAnalyseChart = new FusionCharts(
										"fusionCharts/MSLine.swf",
										"generalAnalyseChartId", width, height);
						generalAnalyseChart.setDataXML(dataXml);
						generalAnalyseChart.setTransparent(true);
						generalAnalyseChart.render(generalAnalyse3DPanel
									.getId());// 渲染
						var loadChar = {
							maxTmnl : 0,
							maxTmnlTime : "",
							minTmnl : 0,
							minTmnlTime : "",
							maxAndMinProp : "",
							maxAndMinDiff : "",
							loadFactor : ""
						};
						generalAnalyseTmnlPanel.getForm().setValues(loadChar);// 负荷特性赋值
					}
				} else {
					return true;
				}
			},
			processUserGridSelect : function(cm, row, record) {
				if(!Ext.isEmpty(record.get('consType')) && '5' == record.get('consType')){
					Ext.MessageBox.show({
                        title: '提示',
           				msg: '采集数据综合分析页面不支持台区居民用户，是否转入客户综合查询页面？',
           				width:300,
           				buttons: Ext.MessageBox.OKCANCEL,
           				fn: function(e){
           					if("ok" == e){
           					    opencsitab(record.get('consNo'),record.get('consId'));
           					}
           				}
      			     });
				}else{
					gaConsNo.setValue(record.get('consNo'));//添加户号
					gaConsName.setValue(record.get('consName'));//添加户名
					consId = record.get('consId');
					//清空曲线图，曲线数据，负荷特性
					generalAnalyseMPStore.removeAll();
					generalAnalyseTotalStore.removeAll();
					curveData_gridStore.removeAll();
					dataXml = getGAPowerMltXMLData('', '', '', '', '', '', '', '', '', '', '',
				        '', '', '', '', '', '', '', '', '', '', 4, '', '', '');
					generalAnalyseChart = new FusionCharts(
									"fusionCharts/MSLine.swf",
										"generalAnalyseChartId", width, height);
					generalAnalyseChart.setDataXML(dataXml);
					generalAnalyseChart.setTransparent(true);
					generalAnalyseChart.render(generalAnalyse3DPanel
								.getId());// 渲染
					
					var loadChar = {
						maxTmnl : 0,
						maxTmnlTime : "",
						minTmnl : 0,
						minTmnlTime : "",
						maxAndMinProp : "",
						maxAndMinDiff : "",
						loadFactor : ""
					};
					generalAnalyseTmnlPanel.getForm().setValues(loadChar);// 负荷特性赋值
				}
			}
		});
	});

// 打开新的tab
function opencsitab(consNo,consId) {
	privateTerminalCon = consNo;
	privateIDCon = consId;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");

}