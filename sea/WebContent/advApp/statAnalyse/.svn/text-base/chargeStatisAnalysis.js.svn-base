
/**
 * 标识日月年 0标识日曲线,1标识月曲线,2标识年曲线
 * @type int
 */
 var falgDate ;
 /**
 * 同比环比标识 1标识同比,2标识环比
 * @type int
 */
 var contrast ;
 
 var orgType ;
 var nodeValue ;
 var nodeType;
 
 /**
  * 处理不同曲线显示不同时间组件
  * @param {} num  接受时间标识
  */
  function displayTimeCSA(num) {
	if (num == 1) {
		startDateCSA.ownerCt.setVisible(true);
		endDateCSA.ownerCt.setVisible(true);
		startMonthCSA.ownerCt.setVisible(false);
		startYearCSA.ownerCt.setVisible(false);
	} else if (num == 2) {
		startDateCSA.ownerCt.setVisible(false);
		endDateCSA.ownerCt.setVisible(false);
		startMonthCSA.ownerCt.setVisible(true);
		startYearCSA.ownerCt.setVisible(false);
	} else if (num = 3) {
		startDateCSA.ownerCt.setVisible(false);
		endDateCSA.ownerCt.setVisible(false);
		startMonthCSA.ownerCt.setVisible(false);
		startYearCSA.ownerCt.setVisible(true);
	}

}
 // 节点文本框
 var fieldText = new Ext.form.TextField({
			fieldLabel : "选择节点名",// <font color='red'>*</font>",
			allowBlank : false,// 红星，必选
			id : 'csa_id_text',
			readOnly : true,
			//labelStyle : "text-align:right;width:50;",
			// emptyText : '请输入...',
			labelSeparator : "",
			width : 160,
			validator : function(val) {
				if (Ext.isEmpty(val))
					return false;
				else
					return true;
			}
		})
 var startDateCSA = new Ext.form.DateField({
			id : "csa_startDate_dateFrom",
			format : "Y-m-d",
			width : 100,
			fieldLabel : "日期起",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.MONTH, -1),
//			value : '2010-05-01',
			labelSeparator : ""
			});
 var endDateCSA = new Ext.form.DateField({
			id : "csa_endDate_dateTo",
			format : "Y-m-d",
			width : 100,
			fieldLabel : "止",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
 var startMonthCSA = new Ext.ux.MonthField({
			id : "csa_startMonth_dateFrom",
			format : "Y-m",
			fieldLabel : "日期起",
//			hidden : true,
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.MONTH, -1),
			labelSeparator : ""
			});
 var endMonth = new Ext.ux.MonthField({
			id : "csa_endMonth_dateTo",
			format : "Y-m",
			fieldLabel : "止",
			hidden : true,
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
		
 var startYearCSA = new Ext.ux.MonthField({
			id : "csa_startYear_dateFrom",
			format : "Y",
			fieldLabel : "日期起",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.YEAR, -1),
			labelSeparator : ""
			});
 var endYear = new Ext.form.DateField({
			id : "csa_endYear_dateTo",
			format : "Y",
			fieldLabel : "止",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
		
		

		
		
// 时间选择radio
var csaRadio = new Ext.form.RadioGroup({
			id : 'csaRadio',
			fieldLabel : '曲线类型',
			columns : [80, 80, 80],
			autoWidth : true,
			items : [{
						name : 'csaCurve',
						boxLabel : '日曲线',
						checked : true,			
						inputValue : 1,
						id : 'csaDate',
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									falgDate = 1;
									displayTimeCSA(falgDate);
									Ext.getCmp("csa_checkbox_contrast").setValue(false);
									Ext.getCmp("csa_checkbox_contrast").setDisabled(true);
								}
							}
						}
					}, {
						name : 'csaCurve',
						boxLabel : '月曲线',
						id : 'csaMonth',
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									
									falgDate = 2;
									displayTimeCSA(falgDate);
									Ext.getCmp("csa_checkbox_contrast").setDisabled(false);
								}
							}
						}
					}, {
						name : 'csaCurve',
						boxLabel : '年曲线',
						id : 'csaYear',
						inputValue : 3,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									falgDate = 3;
									displayTimeCSA(falgDate);
									Ext.getCmp("csa_checkbox_contrast").setDisabled(false);
								}
							}
						}
					}]
		});
		
		// 同比环比radio
var contrastRadioCSA = new Ext.form.RadioGroup({
			id : 'csa_contrastRadio',
			//fieldLabel : '曲线类型',
			columns : [60, 60],
			disabled : true,
			autoWidth : true,
			items : [{
						name : 'csaContrast',
						boxLabel : '环比',
						checked : true,
						id : 'csaContrast_1',
						inputValue : 1,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									//contrast = 0;
								}
							}
						}
					}, {
						name : 'csaContrast',
						boxLabel : '同比',
						id : 'csaContrast_2',
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									//contrast = 1;
								}
							}
						}
					}]
		});
		
		var topOneForm = new Ext.Panel({
			layout : 'table',
			border : false,
			defaults : {
				anchor : '95%'
			},
			labelAlign : 'right',
			items : [

			{
						columnWidth : .25,
						layout : "form",
						labelWidth : 70,
						border:false,
						items : [fieldText]
					},{
									columnWidth : .2,
									layout : "form",
									labelWidth : 50,
									border:false,
									items : [startDateCSA]
								}, { 
									columnWidth : .15,
									layout : "form",
									labelWidth : 15,
									border:false,
									items : [endDateCSA]
								},{
									columnWidth : .2,
									layout : "form",
									border:false,
									labelWidth : 50,
									hidden:true,
									items : [startMonthCSA]
								},{
									columnWidth : .2,
									layout : "form",
									border:false,
									hidden:true,
									labelWidth : 50,
									items : [startYearCSA]
								}, {
									columnWidth : .15,
									baseCls : "x-plain",
									labelWidth : 10,
									fieldLabel: '',
               						labelSeparator: '',
									layout : "form",
									defaultType : "button",
									items : [{
												text : "查询",
												width : 50,
												border:false,
												handler : function() {
														var queryStartDate ;
														var queryEndDate;
														var textValue = Ext.getCmp("csa_id_text");
														var dateFlag = csaRadio.getValue().inputValue;
														var checkboxFlag = Ext.getCmp("csa_checkbox_contrast").getValue();
														var conFlag = 0;
														if (!textValue.isValid(true)) {
															textValue.markInvalid('不能为空');
															return true;
														}
														//设置传参时间
														if(dateFlag == 1){
															queryStartDate = startDateCSA.getValue();
															queryEndDate = endDateCSA.getValue();
															if (queryStartDate > queryEndDate) {
																	Ext.MessageBox.alert("提示", "开始时间不能大于结束时间！");
																	return;
																}
														}else if(dateFlag == 2){
															queryStartDate = startMonthCSA.getValue();
															queryEndDate = startMonthCSA.getValue();
															var nowDate = new Date();
															if (queryStartDate > nowDate) {
																	Ext.MessageBox.alert("提示", "查询月不能大于当前月！");
																	return;
																}
														}else if(dateFlag == 3){
															queryStartDate = startYearCSA.getValue();
															queryEndDate = startYearCSA.getValue();
															var nowDate = new Date();
															if (queryStartDate > nowDate) {
																	Ext.MessageBox.alert("提示", "查询年不能大于当年月！");
																	return;
																}
														}
														//设置对比标识值
														if(checkboxFlag){
															conFlag = contrastRadioCSA.getValue().inputValue;
														}
														//图片数据
														curveChartCSA(dateFlag,conFlag,queryStartDate,queryEndDate);
														//清除grid数据
														chargeStatAStore.baseParams = {
															startDate : queryStartDate,
															endDate : queryEndDate,
															nodeType : nodeType,
															nodeValue: nodeValue,
															orgType : orgType,
															timeFlag : dateFlag
														};
														chargeStatAStore.load({
																	params : {
																		start : 0,
																		limit : DEFAULT_PAGE_SIZE
																	}
																});
														
													}
											}]
								}]
		});
		
		var topTwoForm = new Ext.Panel({
		layout : 'column',
		columnWidth : 0.3,
		border : false,
		defaults : {
		anchor : '95%'
		},
//		labelAlign : 'right',
		items : [ 
									{
									columnWidth : .1,
									layout : "form",
									labelWidth : 1,
									baseCls : "x-plain",
									items : [{
												xtype : 'checkbox',
												boxLabel : '选择比对',
												id : 'csa_checkbox_contrast',
												name : 'csa_checkbox_contrast_name',
												disabled : true,
												inputValue : 1,
												listeners : {
													check : function(checkbox, checked) {
														if (checked) {
															contrastRadioCSA.setDisabled(false);
														} else {
															contrastRadioCSA.setDisabled(true);
														}
													}
												}
											}
									]
								},
									{
									columnWidth : .15,
									layout : "form",
									labelWidth : 1,
									baseCls : "x-plain",
									items : [contrastRadioCSA]
								},{
						columnWidth : .35,
						layout : "form",
						labelWidth : 60,
						baseCls : "x-plain",
						items : [csaRadio]
					}]
		});
	//负荷统计分析
	var csaTopPanel = new Ext.Panel({
		height : 70,
		region : 'north',
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "form",
			style : "padding:5px",
			items : [topOneForm,topTwoForm
				
//					{
//									//columnWidth : .4,
//									width : 260,
//									layout : 'form',
//									labelWidth : 70,
//									baseCls : "x-plain",
//									items : [fieldText]
//								}, {
//									//columnWidth : .4,
//									width : 400,
//									labelWidth : 60,
//									layout : 'form',
//									baseCls : "x-plain",
//									items : [csaRadio]
//								},{
//									//columnWidth : .3,
//									width : 180,
//									layout : "form",
//									labelWidth : 70,
//									baseCls : "x-plain",
//									items : [startDateCSA]
//								}, {
//									//columnWidth : .2,
//									width : 120,
//									layout : "form",
//									labelWidth : 15,
//									baseCls : "x-plain",
//									items : [endDateCSA]
//								}, {
//									//columnWidth : .1,
//									width : 100,
//									layout : "form",
//									defaultType : "button",
//									baseCls : "x-plain",
//									items : [{
//												text : "查询",
//												width : 50,
//												handler : function() {
//												}
//											}]
//								}
								
								]
		}]
	});
	
	
	
	//生成负荷曲线图
//	var chargeStatAnalysePanel = new Ext.fc.FusionChart({
//						id : 'chargeStatAnalysePanel',
//						name : 'chargeStatAnalysePanel',
//						border : false,
//						title : '负荷曲线',
//						wmode : 'transparent',
//						backgroundColor : 'ffffff',
//						url : 'fusionCharts/ScrollLine2D.swf',
//						DataXML : ""
//					});
					
					
	var csaChartPanel = new Ext.Panel({
				border : false
			});
	var chargeStatAnalysePanel = new Ext.Panel({
				title : '电量曲线',
				border : false,
				region : "center",
				items : [csaChartPanel]
			});	
	//曲线数据获得方法
	function curveChartCSA(dateFlag,conFlag,queryStartDate,queryEndDate){

			Ext.Ajax.request({
						url : 'advapp/chargeStatAnalyse!queryChargeStatAnalyse.action',
						params : {
							startDate : queryStartDate,
							endDate : queryEndDate,
							timeFlag : dateFlag,
							contrastFlag : conFlag,
							orgType : orgType,
							nodeValue : nodeValue,
							nodeType : nodeType
						},
						success : function(response) {
							
							var result = Ext.decode(response.responseText);
							if (result == null)
								return true;
							var chargeMap = result.chargeMap;
							if(chargeMap == null){
								return true;
							}
							// 图片XMLDATA数据
							var chargSAXmlData;
							chargSAXmlData = createCSAXmlData(chargeMap, conFlag , dateFlag);
							var csaChart = new FusionCharts(
								"./fusionCharts/ScrollLine2D.swf",
								"consume_info_js", chargeStatAnalysePanel.getWidth(), chargeStatAnalysePanel
										.getHeight());
							csaChart.setDataXML(chargSAXmlData);
							csaChart.setTransparent(true);
							csaChart.render(csaChartPanel.getId());
//							chargeStatAnalysePanel.changeDataXML(chargSAXmlData);
						},
						failure : function() {
							Ext.MessageBox.alert("提示", "失败");
							return;
						}
					});
					
	}
					
/**
 * 传入数据集合组成XMLdata数据
 * 
 * @param {} chargeMap 传入数据集合
 * @param {} conFlag  比对标识
 * @param {} dateFlag  日期曲线表示
 */	

/*function getMultLineCSAXMLData(chargeMap, conFlag, dateFlag) {
	// 图型属性名称
	var seriesName = '';
	var category = '';
	var dataset = '';
	var dataset1 = '';
	// 图片XMLDATA数据
	var chargSAXmlData;
	// 后台数据集合
	var chargeStatConList;
	var chargeStatList;

	if (dateFlag == 1) {
		seriesName = '平均负荷日曲线';
	} else if (dateFlag == 2) {
		seriesName = '平均负荷月曲线';
	} else if (dateFlag == 3) {
		seriesName = '平均负荷年曲线';
	}
	category = '<categories>';
	dataset = '<dataset seriesName="'
			+ seriesName
			+ '" color="AFD8F8" anchorBorderColor="AFD8F8" anchorBgColor="AFD8F8">';
	// if(checkboxFlag == 1){
	if (conFlag == 1) {
		seriesName = "同比" + seriesName;
		dataset1 = '<dataset seriesName="'
				+ seriesName
				+ '" color="F6BD0F" anchorBorderColor="F6BD0F" anchorBgColor="F6BD0F">';
	} else if (conFlag == 2) {
		seriesName = "环比" + seriesName;
		dataset1 = '<dataset seriesName="'
				+ seriesName
				+ '" color="F1683C" anchorBorderColor="F1683C" anchorBgColor="F1683C">';
	}
	// }

	chargeStatList = chargeMap.Curve_Date;

	if (null == chargeStatList)
		return null;

	for (var i = 0; i < chargeStatList.length; i++) {
		category += '<category label="'
				+ chargeStatList[i].statDate.substring(0, 10) + '" />';
		dataset += '<set value="' + chargeStatList[i].curveDate + '"   /> ';
	}

	if (conFlag == 1 || conFlag == 2) {
		chargeStatConList = chargeMap.Con_Curve_Date;
		for (var i = 0; i < chargeStatConList.length; i++) {
			dataset1 += '<set value="' + chargeStatConList[i].curveDate + '"  /> ';
		}
	}

	chargSAXmlData = ' numberprefix=""'
			+ ' anchorRadius="1" divLineAlpha="10" divLineColor="CC3300" chartRightMargin="15" bgColor="FFFFFF,CC3300" '
			+ ' legendBorderAlpha="0" bgAngle="270" bgAlpha="10,10" baseFont="宋体" showvalues="0" '
			+ ' formatNumberScale="0" ' + ' baseFontSize="12"> ';
	category += '</categories>';
	dataset += '</dataset>';
	dataset1 += '</dataset>';
	chargSAXmlData = chargSAXmlData + category + dataset;

	// 组合xmlDATE数据 设置第二条数据线
	if (conFlag == 1 || conFlag == 2) {
		chargSAXmlData = chargSAXmlData + dataset1;
	}
	// xml数据组合完成
	chargSAXmlData = chargSAXmlData + '</chart>';

	chargSAXmlData = '<chart palette="2" caption="" ' + chargSAXmlData;
	return chargSAXmlData;
}*/

/**
 * 传入数据集合组成XMLdata数据
 * 
 * @param {} chargeMap 传入数据集合
 * @param {} conFlag  比对标识
 * @param {} dateFlag  日期曲线表示
 */	

function createCSAXmlData(chargeMap, conFlag , dateFlag) {
	//后台数据集合
	var chargeStatConList;
	var chargeStatList;
	
	// 得到查询集合
	chargeStatList = chargeMap.Curve_Date;
	if (null == chargeStatList)
		return null;
	// 比对标识为1、2，时获得比对数据结合
	if (conFlag == 1 || conFlag == 2) 
		chargeStatConList = chargeMap.Con_Curve_Date;
		
    //显示图型类别
    var chartXML = [
        '<chart caption="" lineThickness="1" showValues="0"  anchorRadius="2" '
            + 'divLineAlpha="20"   divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="1" alternateHGridAlpha="5" '
            + 'alternateHGridColor="CC3300" decimals="2" shadowAlpha="40" labelStep="1"   numvdivlines="5" '
            + 'chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">',
        '<categories>'];
    var category = [];
    var dataset = {};
    var condataset = {};
    // 比对标识为1、2，时获得比对数据结合
	if (conFlag == 1) {
					var abc = "环比平均负荷";
					condataset[abc] = [];
		Ext.each(chargeStatConList, function(a) {
					condataset[abc].push('<set value="' + a.curveDate + '" />');
				});
	} else if (conFlag == 2) {
					var abc = "同比平均负荷";
					condataset[abc] = [];
		Ext.each(chargeStatConList, function(a) {
					condataset[abc].push('<set value="' + a.curveDate + '" />');
				});
	}
	var abc = "平均负荷";
	dataset[abc] = [];
    Ext.each(chargeStatList, function(a) {
				category.push('<category label="'+ a["statDate"].substring(0, 10) + '" />');
				dataset[abc].push('<set value="' + a.curveDate + '" />');
			});
    var abc = ["FF8040", "800080", "FF8040", "F1683C","FFFF00","F6BD0F"];
    chartXML = chartXML.concat(category);
    chartXML.push("</categories>");
    var i = 0;
    Ext.iterate(dataset, function(k, v) {
      chartXML.push('<dataset seriesName="'
              + k
              + '" anchorBgColor="1D8BD1" anchorBorderColor="1D8BD1" color="'
              + abc[i++] + '">');
      chartXML = chartXML.concat(v);
      chartXML.push("</dataset>");
    });
    if (conFlag == 1) {
		var i = 2;
		Ext.iterate(condataset, function(k, v) {
			chartXML.push('<dataset seriesName="'
							+ k
							+ '" anchorBgColor="FF8040" anchorBorderColor="FF8040"  color="'
							+ abc[i++] + '">');
			chartXML = chartXML.concat(v);
			chartXML.push("</dataset>");
		});
	} else if (conFlag == 2) {
		var i = 2;
		Ext.iterate(condataset, function(k, v) {
			chartXML.push('<dataset seriesName="'
							+ k
							+ '" color="FF8040" anchorBorderColor="FF8040" anchorBgColor="'
							+ abc[i++] + '">');
			chartXML = chartXML.concat(v);
			chartXML.push("</dataset>");
		});
	}
    chartXML.push("</chart>");
    return chartXML.join("");
  }






		// ***************翻页 页码递增
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});

	// rowNum、rowNum_1:分别定义与2个grid
	var rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (chargeStatAStore && chargeStatAStore.lastOptions
							&& chargeStatAStore.lastOptions.params) {
						startRow = chargeStatAStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
//	var chargeStatASm = new Ext.grid.CheckboxSelectionModel();
	var chargeStatAStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'advapp/chargeStatAnalyse!queryChargeStatAnalyseDemandDate.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'chargeStatList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consNo'
							},{
								name : 'consName'
							}, {
								name : 'contractCap'
							}, {
								name : 'statDate'
							}, {
								name : 'max_p'
							}, {
								name : 'max_p_time'
							},{
								name : 'min_p'
							}, {
								name : 'min_p_time'
							}, {
								name : 'avg_p'
							}, {
								name : 'chargeRate'
							},{
								name : 'fengGuRate'
							}, {
								name : 'orgNo'
							}])
		});
var chargeStatACM = new Ext.grid.ColumnModel([rowNum,/*chargeStatASm,*/
	{
			header : '供电单位',
			dataIndex : 'orgName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '供电单位: ' + record.get('orgName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '用户编号',
			dataIndex : 'consNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '用户名称',
			dataIndex : 'consName',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '用户名称: ' + record.get('consName');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '合同容量',
			dataIndex : 'contractCap',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '统计时间',
			dataIndex : 'statDate',
			sortable : true,
			align : 'center',
			renderer : function(val) {
					var html = '<span  ext:qtip="' + val.substring(0, 10) + '">' + val.substring(0, 10) + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '负荷峰值',
			dataIndex : 'max_p',
			sortable : true,
			align : 'center',
			renderer : ''
		},  {
			header : '发生时间',
			dataIndex : 'max_p_time',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '负荷谷值',
			dataIndex : 'min_p',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '发生时间',
			dataIndex : 'min_p_time',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '平均负荷',
			dataIndex : 'avg_p',
            width:100,
			sortable : true,
			align : 'center'
		},{
			header : '负荷率',
			dataIndex : 'chargeRate',
			sortable : true,
			align : 'center',
			renderer : function(val) {
					val = val * 100;
					if(val == 0){
						var html = '<span  ext:qtip="' + Ext.util.Format.number(val,'0,000.00')  + '">' + Ext.util.Format.number(val,'0,000.00')  + '</span>';
					}else{
						var html = '<span  ext:qtip="' + Ext.util.Format.number(val,'0,000.00') + '%' + '">' + Ext.util.Format.number(val,'0,000.00') + '%' + '</span>';
					}
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '峰谷差率',
			dataIndex : 'fengGuRate',
			sortable : true,
			align : 'center',
			renderer : function(val) {
					val = val * 100;
					if(val == 0){
						var html = '<span  ext:qtip="' + Ext.util.Format.number(val,'0,000.00')  + '">' + Ext.util.Format.number(val,'0,000.00')  + '</span>';
					}else{
						var html = '<span  ext:qtip="' + Ext.util.Format.number(val,'0,000.00') + '%' + '">' + Ext.util.Format.number(val,'0,000.00') + '%' + '</span>';
					}
					return '<div align = "left">' + html + '</div>';
				}
		}]);

var chargeStatAGrid = new Ext.grid.GridPanel({
			title : "负荷特性分析",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : chargeStatACM,
//			sm : chargeStatASm,
			ds : chargeStatAStore,
			bbar : new Ext.ux.MyToolbar({
							store : chargeStatAStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
		
		
		
		
/*
	// rowNum、rowNum_1:分别定义与2个grid
	var rowNumMon = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (CPDMonthStore && CPDMonthStore.lastOptions
							&& CPDMonthStore.lastOptions.params) {
						startRow = CPDMonthStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var CPDMonthSm = new Ext.grid.CheckboxSelectionModel();
	var CPDMonthStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'advapp/collectionPointDemand!queryCollectionPointDemandMonth.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'cpdMonthList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consName'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'assetNo'
							}, {
								name : 'terminalAddrs'
							}, {
								name : 'dataTime'
							},{
								name : 'colTime'
							}, {
								name : 'ct'
							}, {
								name : 'pt'
							}, {
								name : 'mark'
							},{
								name : 'papDemand'
							}, {
								name : 'papDemandTime'
							}, {
								name : 'prpDemand'
							}, {
								name : 'prpDemandTime'
							}, {
								name : 'rapDemand'
							}, {
								name : 'rapDemandTime'
							}, {
								name : 'rrpDemand'
							}, {
								name : 'rrpDemandTime'
							}, {
								name : 'orgNo'
							}, {
								name : 'consNo'
							}])
		});
var CPDMonthCM = new Ext.grid.ColumnModel([rowNumMon,CPDMonthSm,{
			header : '供电单位',
			dataIndex : 'orgName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '数据ID: ' + record.get('dataID');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '存放区域: ' + record.get('areaCode');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '终端资产编号',
			dataIndex : 'tmnlAssetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '资产编号',
			dataIndex : 'assetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '终端地址码',
			dataIndex : 'terminalAddrs',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '数据日期',
			dataIndex : 'dataTime',
			width:160,
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '终端抄表时间',
			dataIndex : 'colTime',
			sortable : true,
			align : 'center',
			hidden : true
		}, {
			header : '电流互感器的倍率',
			dataIndex : 'ct',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '电流互感器的倍率: ' + record.get('ct');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '电压互感器的倍率',
			dataIndex : 'pt',
            width:100,
			sortable : true,
			align : 'center'
		},{
			header : '补全标记',
			dataIndex : 'mark',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量',
			dataIndex : 'papDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量发生时间',
            width:60,
			dataIndex : 'papDemandTime',
			sortable : true,
			align : 'center',
                renderer : ''
		}, {
			header : '正向无功总最大需量',
            width:60,
			dataIndex : 'prpDemand',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '正向无功总最大需量发生时间',
			dataIndex : 'prpDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量发生时间',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '反向无功总最大需量',
			dataIndex : 'rrpDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '反向无功总最大需量发生时间',
			dataIndex : 'rrpDemandTime',
			sortable : true,
			align : 'center'
		}]);

var cpdMonthGrid = new Ext.grid.GridPanel({
			title : "测量点月冻结最大需量及发生时间",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : CPDMonthCM,
			sm : CPDMonthSm,
			ds : CPDMonthStore,
			bbar : new Ext.ux.MyToolbar({
							store : CPDMonthStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
		*/
//负荷统计分析组合面板
	var csaGroupPanel = new Ext.TabPanel({
		region : 'center',
//		title : '负荷特性分析',
//		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[chargeStatAnalysePanel,chargeStatAGrid]
	});
	
	//负荷统计分析分析
	var viewPanel_CSA = new Ext.Panel({
		layout : 'border',
		autoScroll : false,
		border : false,
		items : [csaTopPanel, csaGroupPanel]
	});
	
	Ext.onReady(function(){
		var csaTree = new LeftTreeListener({
				modelName : '负荷统计分析',
				processClick : function(p, node, e) {
							var obj = node.attributes.attributes;
							var type = node.attributes.type;
//							if (!node.isLeaf()) {
								if (node.attributes.type == 'org') {
									// if (obj.orgType != '02') {
									orgType = obj.orgType;
									nodeValue = obj.orgNo;
									nodeType = node.attributes.type;
									Ext.getCmp("csa_id_text").setValue(node.text);
									consType = null;
									// }
								}
								if (node.attributes.type == 'usr') {
									nodeValue = obj.consNo;
									nodeType = node.attributes.type;
									Ext.getCmp("csa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'cgp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("csa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'ugp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("csa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'line') {
									nodeValue = obj.lineId;
									nodeType = node.attributes.type;
									Ext.getCmp("csa_id_text").setValue(node.text);
									consType = null;
								} else {
									return true;
								}
//							} else {
//								return true;
//							}
						},
				processUserGridSelect : function(cm, row, record) {
					Ext.getCmp("csa_id_text").setValue(record.get('consName'));
					record.get('consNo');
//					autoSendstore.baseParams = {
//						
//					};
				}
			});
	renderModel(viewPanel_CSA,'负荷统计分析');
	});
		
	