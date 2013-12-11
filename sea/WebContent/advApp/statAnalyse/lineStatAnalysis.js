/**
 * @author 陈国章
 * @version 1.0
 */
var nodeNo, type, volt, voltCode, startDate, endDate;
var voltDegreeStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : './advapp/lineStatAnalysisAction!queryVoltByNodeNo.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'voltList'
			}, [{
						name : 'voltCode'
					}, {
						name : 'volt'
					}])
});
var lsaVoltDeg = new Ext.form.ComboBox({
			hideOnSelect : false,
			displayField : 'volt',
			hiddenName : "any",
			mode : "local",
			valueField : 'voltCode',
			name : 'lsaVoltDegree',
			id : 'lsaVoltDegree',
			store : voltDegreeStore,
			width : 120,
			fieldLabel : '电压等级',
			triggerAction : 'all',
			emptyText : '--请选择--',
			blankText : '--请选择--'

		});
var lsaQryPanel = new Ext.Panel({
	plain : true,
	border : false,
	region : 'north',
	layout : 'form',
	height : 70,
	items : [{
		border : false,
		layout : 'column',
		style : "padding-top:5px",
		items : [{
					columnWidth : .23,
					layout : 'form',
					labelAlign : 'right',
					border : false,
					labelWidth : 60,
					defaultType : "textfield",
					items : [{
								fieldLabel : "选择节点<font color='red'>*</font>",
								id : 'lsaNodeName',
								readOnly : true,
								emptyText : '请从左边选择...',
								labelSeparator : "",
								width : 150,
								validator : function(val) {
									if (Ext.isEmpty(val))
										return false;
									else
										return true;
								}
							}]
				}, {
					columnWidth : .23,
					layout : 'form',
					border : false,
					labelAlign : 'right',
					labelWidth : 60,
					items : [lsaVoltDeg]
				}, {
					columnWidth : .10,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 60,
					defaultType : "button",
					baseCls : "x-plain",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "查询",
						width : 70,
						listeners : {
							"click" : function() {
								var CONS = Ext.getCmp("lsaNodeName");
								if (!CONS.isValid(true)) {
									CONS.markInvalid('不能为空');
									return true;
								};
								var orgName = Ext.getCmp("lsaNodeName")
										.getValue();
								// alert(lsaTabPanel.getActiveTab().getTitle());
								if (lsaTabPanel.getActiveTab().getId() == "lsaMeterStatPanel") {

									queryPowerStat();
								} else if (lsaTabPanel.getActiveTab().getId() == "lsaLoadStatPanel") {
									queryLoadStat();
								}
							}
						}
					}]
				}]
	}, {
		layout : 'column',
		style : "padding-top:5px",
		border : false,
		items : [{
					columnWidth : .23,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 60,
					defaultType : "datefield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "从",
								editable : false,
								id : 'lsaStartTime',
								labelSeparator : "",
								format : 'Y-m-d',
								value : new Date().add(Date.DAY, -5),
								// anchor:'100%'
								width : 150
							}]
				}, {
					columnWidth : .23,
					layout : 'form',
					labelAlign : 'right',
					labelWidth : 60,
					defaultType : "datefield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "到",
								editable : false,
								id : 'lsaEndTime',
								labelSeparator : "",
								format : 'Y-m-d',
								value : new Date(),
								// anchor:'100%'
								width : 150
							}]
				}]
	}]
});
function queryPowerStat() {
	startDate = Ext.getCmp("lsaStartTime").getValue().format("Y-m-d");
	endDate = Ext.getCmp("lsaEndTime").getValue().format("Y-m-d");
	voltCode = lsaVoltDeg.getValue();
	lsaPowerStatStore.baseParams = {
		startDate : startDate,
		endDate : endDate,
		type : type,
		nodeNo : nodeNo,
		voltCode : voltCode
	}
	lsaPowerStatStore.load();
}
function queryLoadStat() {
	startDate = Ext.getCmp("lsaStartTime").getValue().format("Y-m-d");
	endDate = Ext.getCmp("lsaEndTime").getValue().format("Y-m-d");
	voltCode = lsaVoltDeg.getValue();
	lsaLoadStatStore.baseParams = {
		startDate : startDate,
		endDate : endDate,
		type : type,
		nodeNo : nodeNo,
		voltCode : voltCode
	}
	lsaLoadStatStore.load();
}
var lsaPowerStatStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './advapp/lineStatAnalysisAction!queryEnergyStat.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'esList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgNo'
							}, {
								name : 'orgName'
							}, {
								name : 'lineId'
							}, {
								name : 'lineName'
							}, {
								name : 'volt'
							}, {
								name : 'statDate'
							}, {
								name : 'pape'
							}, {
								name : 'pape1'
							}, {
								name : 'pape2'
							}, {
								name : 'pape3'
							}, {
								name : 'pape4'
							}, {
								name : 'showView'
							}])
		});
var lsaPowerStatCM = new Ext.grid.ColumnModel([{
			header : '供电编号',
			dataIndex : 'orgNo',
			// sortable : true,
			// align : 'center'
			hidden : true
		}, {
			header : '供电单位',
			dataIndex : 'orgName',
			sortable : true,
			align : 'center'
		}, {
			header : '线路编号',
			dataIndex : 'lineId',
			// sortable : true,
			// align : 'center',
			hidden : true
		}, {
			header : '线路名称',
			dataIndex : 'lineName',
			sortable : true,
			align : 'center'
		}, {
			header : '电压等级',
			dataIndex : 'volt',
			sortable : true,
			align : 'center'
		}, {
			header : '统计日期',
			dataIndex : 'statDate',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总能',
			dataIndex : 'pape',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="正向有功总能" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000')
						+ '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}, {
			header : '尖电量',
			dataIndex : 'pape1',
			sortable : true,
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="尖电量" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}, {
			header : '峰电量',
			dataIndex : 'pape2',
			sortable : true,
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="峰电量" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}, {
			header : '平电量',
			dataIndex : 'pape3',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="谷电量" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}, {
			header : '谷电量',
			dataIndex : 'pape4',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="谷电量" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}, {
			header : '显示图形',
			dataIndex : 'showView',
			align : 'center',
			renderer : function(s, m, rec) {
				var html = "<a href='javascript:'onclick='openView(\""
						+ rec.get("orgNo") + '\",\"' + voltCode + '\",\"'+ rec.get("lineId")
						+ '\",\"'  + rec.get("statDate")
						+ '\");' + "'>" + "显示图形" + "</a>";
				return html;
			}
		}]);
var lsaPowerStatGrid = new Ext.grid.GridPanel({
			region : 'center',
			autoScroll : true,
			id : 'lsaPowerStatGrid',
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			cm : lsaPowerStatCM,
			store : lsaPowerStatStore,
			bbar : new Ext.ux.MyToolbar({
						store : lsaPowerStatStore,
						enableExpAll : true
					})
		});
var lsaLoadStatStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : './advapp/lineStatAnalysisAction!queryLoadStat.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'lsList',
				totalProperty : 'totalCount'
			}, [{
						name : 'orgName'
					}, {
						name : 'lineName'
					}, {
						name : 'volt'
					}, {
						name : 'statDate'
					}, {
						name : 'maxP'
					}, {
						name : 'maxTime'
					}, {
						name : 'minP'
					}, {
						name : 'minTime'
					}, {
						name : 'pvRatio'
					}, {
						name : 'loadRatio'
					}])
});
var lsaLoadStatCM = new Ext.grid.ColumnModel([{
			header : '供电单位',
			dataIndex : 'orgName',
			sortable : true,
			align : 'center'
		}, {
			header : '线路名称',
			dataIndex : 'lineName',
			sortable : true,
			align : 'center'
		}, {
			header : '电压等级',
			dataIndex : 'volt',
			sortable : true,
			align : 'center'
		}, {
			header : '统计时间',
			dataIndex : 'statDate',
			sortable : true,
			align : 'center'
		}, {
			header : '最大功率',
			dataIndex : 'maxP',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="最大功率" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
				}
		}, {
			header : '最大功率时间',
			dataIndex : 'maxTime',
			sortable : true
		}, {
			header : '最小功率',
			dataIndex : 'minP',
			sortable : true,
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="最小功率" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
				}
	   }, {
			header : '最小功率时间',
			dataIndex : 'minTime',
			sortable : true
		},  {
			header : '峰谷比',
			dataIndex : 'pvRatio',
			sortable : true,
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="峰谷比" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
				}
	   },{
			header : '负荷比',
			dataIndex : 'loadRatio',
			sortable : true,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "right" ext:qtitle="负荷比" ext:qtip="'
						+ Ext.util.Format.number(val, '0,000.000') + '">'
						+ Ext.util.Format.number(val, '0,000.000') + '</div>';
				return html;
			}
		}
//		, {
//			header : '显示图形',
//			dataIndex : 'showView',
//			align : 'center',
//			renderer : function(s, m, rec) {
//				var html = "<a href='javascript:'onclick='openView(\""
//						+ rec.get(consNo)
//				return html;
//			}
		]);
var lsaLoadStatGrid = new Ext.grid.GridPanel({
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			cm : lsaLoadStatCM,
			store : lsaLoadStatStore,
			bbar : new Ext.ux.MyToolbar({
						store : lsaLoadStatStore,
						enableExpAll : true
					})
		});
var lsaTabPanel = new Ext.TabPanel({
			activeTab : 0,
			region : 'center',
			plain : true,
			defaults : {
				autoScroll : true
			},
			items : [{
						title : '电量统计',
						id : 'lsaMeterStatPanel',
						height : 810,
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						autoScroll : true,
						items : [lsaPowerStatGrid]
					}, {
						title : '负荷统计',
						id : 'lsaLoadStatPanel',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [lsaLoadStatGrid]
					}
			// , {
			// title : '线路利用率',
			// id : 'lsaLineRatioPanel',
			// layout : 'fit',
			// border : false,
			// baseCls : "x-plain",
			// height : 350,
			// autoScroll : true,
			// items : [consumerInfoGrid3]
			// }
			]
		});
var chartPanel = new Ext.Panel({
			border : false
		});
var panel = new Ext.Panel({
			border : false,
			region : "center",
			items : [chartPanel]
		});
function createXml(arr) {
	arr.sort(function(a, b) {
				var str1 = a["dataTime1"] + a["dataTime2"] + "";
				var str2 = b["dataTime1"] + b["dataTime2"] + "";
				str1 = str1.replace(/-/g, "");
				str2 = str2.replace(/-/g, "");
				str1 = new Number(str1);
				str2 = new Number(str2);
				if (str1 >= str2) {
					return -1;
				} else {
					return 1;
				}
			});
	// var cks = activeGroup.getValue();
	var rs = [
			'<chart caption="'
					+ "线路统计曲线"
					+ '"  lineThifckness="1" showValues="0"  anchorRadius="2" '
					+ 'divLineAlpha="20"   divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="5" alternateHGridAlpha="5" '
					+ 'alternateHGridColor="CC3300" decimals="2" shadowAlpha="40" labelStep="1"   numvdivlines="5" '
					+ 'chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">',
			'<categories>'];
	var curveData={};
	var curveAxis=[];
	var curveList=['正向有功功率','尖电量','峰电量','平电量','谷电量'];
	var nameList=['pape','pape1','pape2','pape3','pape4']
	
	var cgs = [];
	var dss = {};
	Ext.each(curveList,function(val){
		curveData[val]=[];
	})
//	Ext.each(arr, function(val) {
//				curveAxis.push('<category label="' + val["statDate"] + '" />')
//				Ext.each(nameList,function(v){
//					var curveName=v;
//					curveData[curveName].push('<set value="' + val[v]
//									+ '" />')
//				})
//			});
	Ext.each(arr, function(val) {
				curveAxis.push('<category label="' + val["statDate"] + '" />')
				 for(i=0,j=0;i<curveList.length,j<nameList.length;i++,j++)
				 {
				 	curveData[curveList[i]].push('<set value="' + val[nameList[j]]+ '" />')
				 }
				 
			});		
	
	var colorArray = ["ff0000", "3600ff", "7eff00", "000000","980023"];
	rs=rs.concat(curveAxis)
	rs.push("</categories>");
	var i=0;
	Ext.iterate(curveData, function(k, v) {
				rs.push('<dataset seriesName="' + k
						+ '"  anchorBorderColor="1D8BD1" color="' + colorArray[i++]
						+ '">');
				rs = rs.concat(v);
				rs.push("</dataset>");
			});
	rs.push("</chart>");
	return rs.join("");
//	Ext.each(arr, function(a) {
//				cgs.push('<category label="' + a["statDate"] + " "
//						+ a["dataTime2"] + '" />');
//				Ext.each(cks, function(c) {
//							var abc = c.boxLabel
//							dss[abc].push('<set value="' + a[c.dataIndex]
//									+ '" />');
//						});
//			});
//	var colorArray = ["ff0000", "3600ff", "7eff00", "000000"];
//	rs=rs.concat(curveAxis)
//	rs = rs.concat(cgs);
//	rs.push("</categories>");
//	var i = 0;
//	Ext.iterate(dss, function(k, v) {
//				rs.push('<dataset seriesName="' + k
//						+ '"  anchorBorderColor="1D8BD1" color="' + colorArray[i++]
//						+ '">');
//				rs = rs.concat(v);
//				rs.push("</dataset>");
//			});
//	rs.push("</chart>");
//	return rs.join("");
}
var chartPanel = new Ext.Panel({
			border : false
		});
var panel = new Ext.Panel({
			border : false,
			region : "center",
			items : [chartPanel]
		});
var win = new Ext.Window({
	        closeAction:'hide',
			width : 800,
			height : 600,
			layout : "border",
			items : [panel]
		});
function openView(orgNo,voltCode,lineId,statDate)
{
	Ext.Ajax.request({
			params :{
				orgNo:orgNo,
				voltCode:voltCode,
				lineId:lineId,
				statDate:statDate				
			} ,
			url : './advapp/lineStatAnalysisAction!queryLineEnergy.action',
			success : function(response) {
				var o = Ext.decode(response.responseText);
				var list = o.esList;
				var xmlData = createXml(list);
				var myChart = new FusionCharts(
						"./fusionCharts/ScrollLine2D.swf", "consume_info_js",
						panel.getWidth(), panel.getHeight());
				myChart.setDataXML(xmlData);
				myChart.setTransparent(true);
				myChart.render(chartPanel.getId());
			}
		})
		win.show();
}

Ext.onReady(function() {
	var lsaAllpanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout : 'border',
				items : [lsaQryPanel, lsaTabPanel]
			});
	var listener = new LeftTreeListener({
				modelName : '线路统计分析',
				processClick : function(p, node, e) {
					var obj = node.attributes.attributes;
					type = node.attributes.type;
					var name = node.text;
					if (type == 'org' && obj.orgType != '02') {// obj.orgType
						// 省节点不可点选、查询
						Ext.getCmp("lsaNodeName").setValue(name);
						// 这里节点编号代表供电单位名称
						nodeNo = obj.orgNo;
						voltDegreeStore.load({
									params : {
										type : type,
										nodeNo : nodeNo
									}
								});
						if (!Ext.isEmpty(voltDegreeStore)
								&& 0 < voltDegreeStore.getCount()) {
							voltValue = voltDegreeStore.getAt(0).get('volt');
							voltCode = voltDegreeStore.getAt(0).get('voltCode');
							lsaVoltDeg.setValue(voltValue);
						}

					} else if (type == 'line') {
						Ext.getCmp("lsaNodeName").setValue(name);
						nodeNo = obj.lineId;
						voltDegreeStore.load({
									params : {
										type : type,
										nodeNo : nodeNo
									}
								});
						if (!Ext.isEmpty(voltDegreeStore)
								&& 0 < voltDegreeStore.getCount()) {
							voltValue = voltDegreeStore.getAt(0).get('volt');
							voltCode = voltDegreeStore.getAt(0).get('voltCode');
							lsaVoltDeg.setValue(voltValue);
						}
					} else if (type == 'sub') {
						Ext.getCmp("lsaNodeName").setValue(name);
						nodeNo = obj.subsId;
						voltDegreeStore.load({
									params : {
										type : type,
										nodeNo : nodeNo
									}
								});
						if (!Ext.isEmpty(voltDegreeStore)
								&& 0 < voltDegreeStore.getCount()) {
							voltValue = voltDegreeStore.getAt(0).get('volt');
							voltCode = voltDegreeStore.getAt(0).get('voltCode');
							lsaVoltDeg.setValue(voltValue);
						}

					}

				}
			})
	renderModel(lsaAllpanel, '线路统计分析');
});