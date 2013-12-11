/**
 * 标识日月年
 * @type int
 */
 var falgDate ;
 /**
 * 同比环比标识
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
  function displayTimeESA(num) {
	if (num == 1) {
		startDateESA.ownerCt.setVisible(true);
		endDateESA.ownerCt.setVisible(true);
		startMonthESA.ownerCt.setVisible(false);
		startYearESA.ownerCt.setVisible(false);
	} else if (num == 2) {
		startDateESA.ownerCt.setVisible(false);
		endDateESA.ownerCt.setVisible(false);
		startMonthESA.ownerCt.setVisible(true);
		startYearESA.ownerCt.setVisible(false);
	} else if (num = 3) {
		startDateESA.ownerCt.setVisible(false);
		endDateESA.ownerCt.setVisible(false);
		startMonthESA.ownerCt.setVisible(false);
		startYearESA.ownerCt.setVisible(true);
	}

}
 
 //节点文本框
 var fieldText = new Ext.form.TextField({
			fieldLabel : "选择节点名",// <font color='red'>*</font>",
			allowBlank : false,// 红星，必选
			id : 'esa_id_text',
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
 var startDateESA = new Ext.form.DateField({
			id : "esa_startDate_dateFrom",
			width : 100,
			format : "Y-m-d",
			fieldLabel : "日期起",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.MONTH, -1),
//			value : '2010-05-01',
			labelSeparator : ""
			});
 var endDateESA = new Ext.form.DateField({
			id : "esa_endDate_dateTo",
			format : "Y-m-d",
			width : 100,
			fieldLabel : "止",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
 var startMonthESA = new Ext.ux.MonthField({
			id : "esa_startMonth_dateFrom",
			format : "Y-m",
			fieldLabel : "日期起",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.MONTH, -1),
			labelSeparator : ""
			});
 var endMonth = new Ext.ux.MonthField({
			id : "esa_endMonth_dateTo",
			format : "Y-m",
			//fieldLabel : "止",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
		
 var startYearESA = new Ext.ux.MonthField({
			id : "esa_startYear_dateFrom",
			format : "Y",
			fieldLabel : "日期起",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.YEAR, -1),
			labelSeparator : ""
			});
 var endYear = new Ext.ux.MonthField({
			id : "esa_endYear_dateTo",
			format : "Y",
			//fieldLabel : "止",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
		
// 时间选择radio
var esaRadio = new Ext.form.RadioGroup({
			id : 'esaRadio',
			fieldLabel : '曲线类型',
			columns : [80, 80, 80],
			autoWidth : true,
			items : [{
						name : 'esaCurve',
						boxLabel : '日曲线',
						checked : true,			
						inputValue : 1,
						id : 'esaDate',
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									falgDate = 1;
									displayTimeESA(falgDate);
									Ext.getCmp("esa_checkbox_contrast").setValue(false);
									Ext.getCmp("esa_checkbox_contrast").setDisabled(true);
								}
							}
						}
					}, {
						name : 'esaCurve',
						boxLabel : '月曲线',
						id : 'esaMonth',
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									falgDate = 2;
									displayTimeESA(falgDate);
									Ext.getCmp("esa_checkbox_contrast").setDisabled(false);
								}
							}
						}
					}, {
						name : 'esaCurve',
						boxLabel : '年曲线',
						id : 'esaYear',
						inputValue : 3,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									falgDate = 3;
									displayTimeESA(falgDate);
									Ext.getCmp("esa_checkbox_contrast").setDisabled(false);
								}
							}
						}
					}]
		});

// 电量radio
var elecTypeRadio = new Ext.form.CheckboxGroup({
			id : 'elecTypeRadio',
			fieldLabel : '电量类别',
			columns : [110, 110, 110],
			autoWidth : true,
			items : [
					{
						name : 'elecType_PAP_E',
						boxLabel : '正向有功总电量',
						checked : true,			
						inputValue : 1,
						dataIndex : "PAP_E_DATA",
						id : 'elecType_PAP_E',
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
								}
							}
						}
					}, {
						name : 'elecType_RAP_E',
						boxLabel : '反向有功总电量',
						id : 'elecType_RAP_E',
						checked : true,	
						dataIndex : "RAP_E_DATA",
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
								}
							}
						}
//					}, {
//						name : 'elecType_AVG_Q',
//						boxLabel : '正向无功总电量',
//						id : 'elecType_AVG_Q',
//						inputValue : 3,
//						listeners : {
//							check : function(checkbox, checked) {
//								if (checked) {
//								}
//							}
//						}
					}]
		});
		
		
// 同比环比radio
var contrastRadioESA = new Ext.form.RadioGroup({
			id : 'esa_contrastRadio',
			//fieldLabel : '曲线类型',
			columns : [60, 60],
			disabled : true,
			autoWidth : true,
			items : [{
						name : 'esaContrast',
						boxLabel : '环比',
						checked : true,
						id : 'esaContrast_1',
						inputValue : 1,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									//contrast = 0;
								}
							}
						}
					}, {
						name : 'esaContrast',
						boxLabel : '同比',
						id : 'esaContrast_2',
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
						baseCls : "x-plain",
						items : [fieldText]
					}, {
									columnWidth : .2,
									layout : "form",
									labelWidth : 50,
									border:false,
									items : [startDateESA]
								}, { 
									columnWidth : .15,
									layout : "form",
									labelWidth : 15,
									border:false,
									items : [endDateESA]
								},{
									columnWidth : .2,
									layout : "form",
									border:false,
									labelWidth : 50,
									hidden:true,
									items : [startMonthESA]
								},{
									columnWidth : .2,
									layout : "form",
									border:false,
									hidden:true,
									labelWidth : 50,
									items : [startYearESA]
								},{
									columnWidth : .1,
									layout : "form",
									labelWidth : 1,
									baseCls : "x-plain",
									items : [{
												xtype : 'checkbox',
												boxLabel : '选择比对',
												id : 'esa_checkbox_contrast',
												name : 'esa_checkbox_contrast_name',
												disabled : true,
												inputValue : 1,
												listeners : {
													check : function(checkbox, checked) {
														if (checked) {
															contrastRadioESA.setDisabled(false);
														} else {
															contrastRadioESA.setDisabled(true);
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
									items : [contrastRadioESA]
								},{
									columnWidth : .1,
									//width : 100,
									layout : "form",
									defaultType : "button",
									baseCls : "x-plain",
									items : [{
												text : "查询",
												width : 50,
												handler : function() {
														//查询时间
														var queryStartDate ;
														//结束时间
														var queryEndDate;
														//节点名
														var textValue = Ext.getCmp("esa_id_text");
														//曲线标识
														var dateFlag = esaRadio.getValue().inputValue;
														//判断比对标识
														var checkboxFlag = Ext.getCmp("esa_checkbox_contrast").getValue();
														//电量类别
//														var elecType = elecTypeRadio.getValue().inputValue;
														// 电量类别 获得复选框值
														var elecType = new Array();
														elecType = getElecTypeCheckBoxList();
														var conFlag = 0;
														if (!textValue.isValid(true)) {
															textValue.markInvalid('不能为空');
															return true;
														}
														//设置传参时间
														if(dateFlag == 1){
															queryStartDate = startDateESA.getValue();
															queryEndDate = endDateESA.getValue();
															if (queryStartDate > queryEndDate) {
															Ext.MessageBox.alert("提示", "开始时间不能大于结束时间！");
															return;
														}
														}else if(dateFlag == 2){
															queryStartDate = startMonthESA.getValue();
															queryEndDate = startMonthESA.getValue();
														}else if(dateFlag == 3){
															queryStartDate = startYearESA.getValue();
															queryEndDate = startYearESA.getValue();
														}
														//设置对比标识值
														if(checkboxFlag){
															conFlag = contrastRadioESA.getValue().inputValue;
														}
														//图片数据
														curveChartESA(dateFlag,conFlag,elecType,queryStartDate,queryEndDate);
														//清除grid数据
														elecStatAStore.removeAll();
														elecStatAStore.baseParams = {
															startDate : queryStartDate,
															endDate : queryEndDate,
															nodeType : nodeType,
															nodeValue: nodeValue,
															orgType : orgType,
															timeFlag : dateFlag
														};
														elecStatAStore.load({
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
//		columnWidth : 0.3,
		border : false,
		defaults : {
		anchor : '95%'
		},
//		labelAlign : 'right',
		items : [ 
//								{
//									columnWidth : .2,
//									layout : "form",
//									labelWidth : 70,
//									baseCls : "x-plain",
//									items : [startDateESA]
//								}, {
//									columnWidth : .15,
//									layout : "form",
//									labelWidth : 15,
//									baseCls : "x-plain",
//									items : [endDateESA]
//								}
									{
						columnWidth : .4,
						layout : "form",
						labelWidth : 60,
						baseCls : "x-plain",
						items : [elecTypeRadio]
					},	{
						columnWidth : .35,
						layout : "form",
						labelWidth : 60,
						baseCls : "x-plain",
						items : [esaRadio]
					}]
	});
		//负荷统计分析
	var esaTopPanel = new Ext.Panel({
		height : 70,
		region : 'north',
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "form",
			style : "padding:5px",
			items : [topOneForm,topTwoForm]
		}]
		});
						
	
	//生成负荷曲线图
//	var elecStatAnalysePanel = new Ext.fc.FusionChart({
//						id : 'elecStatAnalysePanel',
//						name : 'elecStatAnalysePanel',
//						border : false,
//						title : '电量曲线',
//						wmode : 'transparent',
//						backgroundColor : 'ffffff',
//						url : 'fusionCharts/ScrollLine2D.swf',
//						DataXML : ""
//					});
	var chartPanel = new Ext.Panel({
				border : false
			});
	var elecStatAnalysePanel = new Ext.Panel({
				title : '电量曲线',
				border : false,
				region : "center",
				items : [chartPanel]
			});				
	

//曲线数据获得方法
	/**
	 * 
	 * @param {} dateFlag  日、月、年曲线表示 1 日曲线、2 月曲线、 3 年曲线
	 * @param {} conFlag   比对标识 1 同比、 2 环比 为0 标识不必对
	 * @param {} elecType  电量类别 1 正向有功总电量 、2 反向有功总电量
	 * @param {} queryStartDate 查询开始时间
	 * @param {} queryEndDate  查询结束时间
	 */
	function curveChartESA(dateFlag,conFlag,elecType,queryStartDate,queryEndDate){
			

			Ext.Ajax.request({
						url : 'advapp/elecStatAnalyse!queryElecStatAnalyse.action',
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
							//获得数据源MAP
							var elecSAMap = result.elecMap;
							if(elecSAMap == null){
								return true;
							}
			                //获得FusionChart的xmldata数据
//							var elecSAXmlData = getMultLineESAXMLData(elecSAMap, conFlag, dateFlag);
							var elecSAXmlData = createXml(elecSAMap, conFlag, dateFlag);
							var myChart = new FusionCharts(
								"./fusionCharts/ScrollLine2D.swf",
								"consume_info_js", elecStatAnalysePanel.getWidth(), elecStatAnalysePanel
										.getHeight());
							myChart.setDataXML(elecSAXmlData);
							myChart.setTransparent(true);
							myChart.render(chartPanel.getId());
//							elecStatAnalysePanel.changeDataXML(elecSAXmlData);
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
 * @param {} elecSAMap 传入数据集合
 * @param {} conFlag  比对标识
 * @param {} dateFlag  日期曲线表示
 */	

/*function getMultLineESAXMLData(elecSAMap, conFlag , dateFlag) {
	//图型属性名称
	var seriesName = '';
	var category = '';
	var dataset = '';
	var dataset1 = '';
	//图片XMLDATA数据
	var elecSAXmlData;
	//后台数据集合
	var elecStatConList;
	var elecStatList;
	
	// 得到查询集合
	elecStatList = elecSAMap.Curve_Date;
	if (null == elecStatList)
		return null;
		

	if (dateFlag == 1) {
		seriesName = '日曲线';
	} else if (dateFlag == 2) {
		seriesName = '月曲线';
	} else if (dateFlag == 3) {
		seriesName = '年曲线';
	}
	 category = '<categories>';
	 //正向有功PAP_E、
	//private double PAP_E_DATA;
	//反向有功RAP_E、
	//private double RAP_E_DATA;
	 dataset = '<dataset seriesName="'
			+ seriesName
			+ '" color="AFD8F8" anchorBorderColor="AFD8F8" anchorBgColor="AFD8F8">';
	 dataset1 = '<dataset seriesName="'
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

	for (var i = 0; i < elecStatList.length; i++) {
		category += '<category label="'
				+ elecStatList[i].statDate.substring(0, 10) + '" />';
		dataset += '<set value="' + elecStatList[i].curveDate
				+ '"  showvalues="0" /> ';
	}
	// 比对标识为1、2，时获得比对数据结合
	if (conFlag == 1 || conFlag == 2) {
		var elecStatConList = elecSAMap.Con_Curve_Date;
		for (var i = 0; i < elecStatConList.length; i++) {
			dataset1 += '<set value="' + elecStatConList[i].curveDate
					+ '"  showvalues="0" /> ';
		}
	}

	elecSAXmlData =  ' numberprefix=""'
					+ ' anchorRadius="1" divLineAlpha="10" divLineColor="CC3300" chartRightMargin="15" bgColor="FFFFFF,CC3300" ' 
					+ ' legendBorderAlpha="0" bgAngle="270" bgAlpha="10,10" baseFont="宋体" showvalues="0" '
					+ ' formatNumberScale="0" '
					+ ' baseFontSize="12"> ';
	category += '</categories>';
	dataset += '</dataset>';
	dataset1 += '</dataset>';
	elecSAXmlData = elecSAXmlData + category + dataset;

	// 组合xmlDATE数据 设置第二条数据线
	if (conFlag == 1 || conFlag == 2) {
		elecSAXmlData = elecSAXmlData + dataset1;
	}
	// xml数据组合完成
	elecSAXmlData = elecSAXmlData + '</chart>';

	elecSAXmlData = '<chart palette="2" caption="" ' + elecSAXmlData;
	return elecSAXmlData;
}*/

/**
 * 传入数据集合组成XMLdata数据
 * 
 * @param {} elecSAMap 传入数据集合
 * @param {} conFlag  比对标识
 * @param {} dateFlag  日期曲线表示
 */	
function createXml(elecSAMap, conFlag , dateFlag) {
	//后台数据集合
	var elecStatConList;
	var elecStatList;
	
	// 得到查询集合
	elecStatList = elecSAMap.Curve_Date;
	if (null == elecStatList)
		return null;
	// 比对标识为1、2，时获得比对数据结合
	if (conFlag == 1 || conFlag == 2) 
		elecStatConList = elecSAMap.Con_Curve_Date;
		
    //显示图型类别
    var cks = elecTypeRadio.getValue();
    var chartXML = [
        '<chart caption="" lineThickness="1" showValues="0"  anchorRadius="2" '
            + 'divLineAlpha="20"   divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="1" alternateHGridAlpha="5" '
            + 'alternateHGridColor="CC3300" decimals="2" shadowAlpha="40" labelStep="1"   numvdivlines="5" '
            + 'chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">',
        '<categories>'];
    var category = [];
    var dataset = {};
    var condataset = {};
    Ext.each(cks, function(c) {
          dataset[c.boxLabel] = [];
        });
    // 比对标识为1、2，时获得比对数据结合
	if (conFlag == 1) {
		Ext.each(cks, function(c) {
					condataset["环比" + c.boxLabel] = [];
				});
		Ext.each(elecStatConList, function(a) {
					Ext.each(cks, function(c) {
								var abc = "环比" + c.boxLabel
								condataset[abc].push('<set value="'+ a[c.dataIndex] + '" />');
							});
				});
	}else if( conFlag == 2){
		Ext.each(cks, function(c) {
          condataset["同比"+c.boxLabel] = [];
        });
        Ext.each(elecStatConList, function(a) {
					Ext.each(cks, function(c) {
								var abc = "同比" + c.boxLabel
								condataset[abc].push('<set value="'+ a[c.dataIndex] + '" />');
							});
				});
	}
    Ext.each(elecStatList, function(a) {
      category.push('<category label="' + a["statDate"].substring(0, 10)  + '" />');
      Ext.each(cks, function(c) {
            var abc = c.boxLabel
            dataset[abc].push('<set value="' + a[c.dataIndex] + '" />');
          });
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
					if (elecStatAStore && elecStatAStore.lastOptions
							&& elecStatAStore.lastOptions.params) {
						startRow = elecStatAStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var elecStatASm = new Ext.grid.CheckboxSelectionModel();
	var elecStatAStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'advapp/elecStatAnalyse!queryChargeStatisAnalyseList.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'elecStatList',
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
var elecStatACM = new Ext.grid.ColumnModel([rowNum,/*elecStatASm,*/
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
			align : 'center'
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

var elecStatAGrid = new Ext.grid.GridPanel({
			title : "电量特性分析",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : elecStatACM,
			sm : elecStatASm,
			ds : elecStatAStore,
			bbar : new Ext.ux.MyToolbar({
							store : elecStatAStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
		
		
		
		

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
		
//测量点冻结需求分析按日、按月组合面板
	var ESAGroupPanel = new Ext.TabPanel({
		region : 'center',
//		title : '负荷特性分析',
//		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[elecStatAnalysePanel,elecStatAGrid]
	});
	
	//测量点冻结需求分析
	var viewPanel_ESA = new Ext.Panel({
		layout : 'border',
		autoScroll : false,
		border : false,
		items : [esaTopPanel, ESAGroupPanel]
	});
	
	Ext.onReady(function(){
	var csaTree = new LeftTreeListener({
				modelName : '电量统计分析',
				processClick : function(p, node, e) {
							var obj = node.attributes.attributes;
							var type = node.attributes.type;
//							if (!node.isLeaf()) {
								if (node.attributes.type == 'org') {
									// if (obj.orgType != '02') {
									orgType = obj.orgType;
									nodeValue = obj.orgNo;
									nodeType = node.attributes.type;
									Ext.getCmp("esa_id_text").setValue(node.text);
									consType = null;
									// }
								}
								if (node.attributes.type == 'usr') {
									nodeValue = obj.consNo;
									nodeType = node.attributes.type;
									Ext.getCmp("esa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'cgp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("esa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'ugp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("esa_id_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'line') {
									nodeValue = obj.lineId;
									nodeType = node.attributes.type;
									Ext.getCmp("esa_id_text").setValue(node.text);
									consType = null;
								} else {
								}
//							} else {
//								return true;
//							}
						},
				processUserGridSelect : function(cm, row, record) {
					Ext.getCmp("esa_id_text").setValue(record.get('consName'));
					record.get('consNo');
//					autoSendstore.baseParams = {
//						
//					};
				}
			});
	renderModel(viewPanel_ESA,'电量统计分析');
	});
function getElecTypeCheckBoxList(){
	//获得复选框值
			var pn = new Array();
			var checkValue = Ext.getCmp('elecTypeRadio').getValue(); 
			if (checkValue.length > 0) {
				for (var i = 0; i < checkValue.length; i++) {
					pn[i] = checkValue[i].inputValue;
				}
			}
			return pn;
}
	