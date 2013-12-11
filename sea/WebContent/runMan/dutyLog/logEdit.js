/*
 * log edit by hx
 */

/*
 * weather forecast
 */

Ext.ux.staffTextfield = Ext.extend(Ext.form.TriggerField, {
			triggerClass : 'x-form-date-trigger',
			onTriggerClick : function() {
				var me = this;
				showStaffWin(function(r) {
							r && me.setValue(r.get("staffNo"));
						}, this.id);
			}
		});
			// 公共方法去
	function itemFromId(panel, itemId) {
		return panel.find("itemId", itemId)[0];
	}
Ext.reg('stafftextfield', Ext.ux.staffTextfield);
function showStaffWin(fn, id) {

	var storeR = new Ext.data.JsonStore({
				fields : ["staffNo", "orgNo", "staffName", "orgName", "deptNo",
						"deptName"],
				root : "resultList",
				totalProperty : "totalCount",
				url : "runman/dutyLog!findStaff.action"
			});
	var sm = new Ext.grid.CheckboxSelectionModel({
				header : "",
				singleSelect : true
			});
	var grid = new Ext.grid.GridPanel({
				tiltle : "操作人员列表",
				store : storeR,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				sm : sm,
				height : 300,
				width : 680,
				bbar : new Ext.ux.MyToolbar({
							store : storeR
						}),
				columns : [sm, {
							header : "操作人员编号",
							width : 50,
							dataIndex : "staffNo"
						}, {
							header : "操作人员名称",
							width : 50,
							dataIndex : "staffName"
						}, {
							header : "所在部门",
							width : 50,
							dataIndex : "deptName"
						}, {
							header : "所属供电公司",
							width : 50,
							dataIndex : "orgName"
						}],
				listeners : {
					rowdblclick : function(g, index) {
						var r = g.getSelectionModel().getSelected();
						if (!r) {
							return;
						}
						selectStaffWin.close();
						Ext.getCmp(id).setValue(r.get("staffNo"));
					}
				}
			});
	storeR.load({
				params : {
					start : 0,
					limit : 20
				}
			});
	// 上面的选择条件面板
	var topSelectPanel = new Ext.form.FieldSet({
				title : "请选择查询条件",
				titleCollapse : true,
				autoHeight : true,
				width : 680,
				layout : "table",
				layoutConfig : {
					columns : 4
				},
				defaults : {
					padding : "0 0 0 20px"
				},
				items : [{
							border : false,
							layout : "form",
							border : false,
							labelWidth : 60,
							labelAlign : "right",
							items : [{
										xtype : "textfield",
										allowBlank : true,
										border : false,
										labelStyle : "width:60;",
										width : 80,
										itemId : "log_staffNo",
										fieldLabel : "用户登陆名"
									}]
						}, {
							border : false,
							layout : "form",
							labelAlign : "right",
							border : false,
							items : [{
										xtype : "textfield",
										allowBlank : true,
										border : false,
										width : 80,
										labelStyle : "width:60;",
										itemId : "log_staffName",
										fieldLabel : "用户名"
									}]
						}, {
							border : false,
							layout : "form",
							border : false,
							labelAlign : "right",
							items : [{
										xtype : "textfield",
										allowBlank : true,
										border : false,
										labelStyle : "width:60;",
										width : 80,
										itemId : "log_deptName",
										fieldLabel : "用户的部门"
									}]
						}, {
							border : false,
							xtype : "button",
							itemId : "find",
							style : "padding:0 0 0 30px",
							text : "查询",
							handler : function() {
								storeR.baseParams = {
									"staffFindBean.staffName" : itemFromId(topSelectPanel,"log_staffName").getValue(),
									"staffFindBean.deptName" :itemFromId(topSelectPanel,"log_deptName").getValue(),
									"staffFindBean.staffNo" :
									itemFromId(topSelectPanel,"log_staffNo").getValue()
								};
								storeR.load();
							}
						}]
			});
	var selectStaffWin = new Ext.Window({
				title : "请选择操作人员",
				modal : true,
				height : 450,
				width : 700,
				layout : "border",
				keys : {
					key : Ext.EventObject.ENTER,
					fn : function() {
						var b = topSelectPanel.items.item("find");
						b.handler(b);
					}
				},
				layout : "fit",
				border : false,
				buttonAlign : "right",
				buttons : [{
							text : "清除",
							handler : function() {
								Ext.getCmp(id).setValue("");
								selectStaffWin.close();
							}
						}, {
							text : "确定",
							handler : function() {
								var r = grid.getSelectionModel().getSelected();
								fn = fn.createInterceptor(function() {
											selectStaffWin.close();
										});
								fn
										&& fn.createDelegate(showStaffWin, [r,
														grid])();
							}
						}, {
							text : "关闭",
							handler : function() {
								this.ownerCt.ownerCt.close();
							}
						}],
				items : [{
							xtype : "panel",
							border : false,
							layout : "vbox",
							bodyStyle : "padding:20px 5px 0 5px",
							items : [topSelectPanel, grid]
						}]
			});

	selectStaffWin.show();
	return selectStaffWin;
}

var weatherCombo = new Ext.Panel({
	layout : 'column',
	style : {
		marginTop : '10px'
	},
	height : 35,
	width : 218,
	border : false,
	labelAlign : 'right',
	items : [{
				layout : 'form',
				columnWidth : 1,
				border : false,
				labelWidth : 45,
				items : [{
							xtype : "combo",
							fieldLabel : '天气 &nbsp;',
							id : "userLog.weather",
							labelSeparator : '',
							typeAhead : true,
							triggerAction : 'all',
							lazyRender : true,
							mode : 'remote',
							editable : true,
							emptyText : '选择天气...',
							store : new Ext.data.JsonStore({
										fields : ['name'],
										url : "runman/dutyLog!findWeather.action",
										root : "resultList"
									}),
							valueField : 'name',
							displayField : 'name',
							width : 135
						}]
			}]
});

var temperature = new Ext.Panel({
			layout : 'column',
			height : 35,
			width : 210,
			border : false,
			labelAlign : 'right',
			items : [{
						layout : 'form',
						columnWidth : .5,
						border : false,
						labelWidth : 45,
						items : [{
									xtype : "numberfield",
									fieldLabel : '气温',
									allowBlank : true,
									labelSeparator : '',
									id : 'userLog.lowTemperature',
									width : 50
								}]
					}, {
						layout : 'form',
						columnWidth : .4,
						border : false,
						labelWidth : 20,
						items : [{
									xtype : "numberfield",
									fieldLabel : "～",
									labelSeparator : "",
									allowBlank : true,
									id : 'userLog.topTemperature',
									width : 40
								}]
					}, {
						layout : 'form',
						columnWidth : .1,
						border : false,
						items : [{
									xtype : "label",
									text : "℃"
								}]
					}]
		});

var wind = new Ext.Panel({
	layout : 'column',
	height : 200,
	width : 210,
	labelAlign : 'right',
	border : false,
	items : [{
		layout : 'form',
		columnWidth : .5,
		border : false,
		labelWidth : 45,
		items : [{
					xtype : "combo",
					fieldLabel : "风向&nbsp;",
					labelSeparator : '',
					allowBlank : true,
					labelSeparator : '',
					typeAhead : true,
					triggerAction : 'all',
					lazyRender : true,
					mode : 'remote',
					editable : true,
					emptyText : '选择风向...',
					store : new Ext.data.JsonStore({
								fields : ['name'],
								url : "runman/dutyLog!findWindDirection.action",
								root : "resultList"
							}),
					valueField : 'name',
					displayField : 'name',
					id : 'userLog.windDirection',
					width : 50
				}]
	}, {
		layout : 'form',
		columnWidth : .4,
		border : false,
		labelWidth : 35,
		items : [{
					xtype : "numberfield",
					labelSeparator : "",
					allowBlank : true,
					id : 'userLog.windForce',
					fieldLabel : "风力",
					width : 40
				}]
	}, {
		layout : 'form',
		columnWidth : .1,
		border : false,
		labelWidth : 20,
		items : [{
					xtype : "label",
					text : "级"
				}]
	}]
});

var weatherPanel = new Ext.Panel({
			title : "天气预报",
			layout : "form",
			labelAlign : "right",
			labelWidth : 45,
			height : 200,
			style : {
				marginLeft : '5px'
			},
			items : [weatherCombo, temperature, wind]
		});

/*
 * summary on duty
 */
var onDutySummaryPanel = new Ext.Panel({
			title : "值班摘要",
			layout : "fit",
			style : {
				textAlign : 'center'
			},
			viewConfig : {
				forceFit : true
			},
			items : [{
						xtype : 'textarea',
						border : false,
						allowBlank : true,
						id : 'userLog.profile',
						fieldLabel : ''
					}]
		});

/*
 * fusion chart
 */

// 随机图形中显示的颜色
function randomColor() {
	var rand = Math.floor(Math.random() * 0xFFFFFF).toString(16);
	if (rand.length == 6) {
		return rand;
	} else {
		return randomColor();
	}
}
// 定义图形渲染的panel
var chartPanel = new Ext.Panel({
			header : false,
			border : false
//			style : {
//				marginTop : '10px',
//				marginLeft : '10px'
//			}
		});

/** *xml数据生成器，生成数据** */
function showChart(data) {
	// caption="操作次数"
	var xmlData = '<chart palette="2"  shownames="1" showvalues="0" decimals="0" numberPrefix="" useRoundEdges="1" legendBorderAlpha="0">'
			+ '<categories>'
			+ '<tpl for="resultList"><category label="{orgName}" /></tpl>'
			+ '</categories>'
			+ '<dataset seriesName="参数下发" color="'
			+ randomColor()
			+ '" showValues="0">'
			+ '<tpl for="resultList"><set value="{paramCnt}" /></tpl>'
			+ '</dataset>'
			+ '<dataset seriesName="控制下发" color="'
			+ randomColor()
			+ '" showValues="0">'
			+ '<tpl for="resultList"><set value="{ctrlCnt}" /></tpl>'
			+ '</dataset>'
			+ '<dataset seriesName="新增终端" color="'
			+ randomColor()
			+ '" showValues="0">'
			+ '<tpl for="resultList"><set value="{attachTmnl}" /></tpl>'
			+ '</dataset>'
			+ '<dataset seriesName="删除终端" color="'
			+ randomColor()
			+ '" showValues="0">'
			+ '<tpl for="resultList"><set value="{detachTmnl}" /></tpl>'
			+ '</dataset>' + '</chart>';
	var t = new Ext.XTemplate(xmlData);
	xmlData = t.applyTemplate(data);
	// alert(xmlData);
	var myChart = new FusionCharts("./fusionCharts/MSColumn2D.swf",
			"myChartId", "550", "187");
	myChart.setDataXML(xmlData);
	myChart.setTransparent(true);
	myChart.render(chartPanel.getId());
}

/*
 * operation
 */
var cm = new Ext.grid.ColumnModel([{
			header : "供电单位",
			sortable : true,
			dataIndex : "orgName"
		}, {
			header : "参数下发",
			sortable : true,
			dataIndex : "paramCnt",
			renderer : function(v) {
				if (!v) {
					return v;
				}
				return "<a href='#' onclick='return false'>" + v + "</a>";
			}
		}, {
			header : "控制下发",
			sortable : true,
			dataIndex : "ctrlCnt",
			renderer : function(v) {
				if (!v) {
					return v;
				}
				return "<a href='#' onclick='return false'>" + v + "</a>";
			}
		}, {
			header : "新增终端",
			sortable : true,
			dataIndex : "attachTmnl",
			renderer : function(v) {
				if (!v) {
					return v;
				}
				return "<a href='#' onclick='return false'>" + v + "</a>";
			}
		}, {
			header : "删除终端",
			sortable : true,
			dataIndex : "detachTmnl",
			renderer : function(v) {
				if (!v) {
					return v;
				}
				return "<a href='#' onclick='return false'>" + v + "</a>";
			}
		}]);
var dataResult = {};
var storeEdit = new Ext.data.JsonStore({
			url : "runman/dutyLog!findOpTypeData.action",
			fields : ["paramCnt", "ctrlCnt", "attachTmnl", "detachTmnl",
					"orgNo", "orgName"],
			root : "resultList",
			totalProperty : "totalCount",
			listeners : {
				"load" : function() {
					showChart(dataResult);
				}
			}
		});
Ext.apply(storeEdit.reader, {
			read : function(response) {
				var json = response.responseText;
				var o = Ext.decode(json);
				if (!o) {
					throw {
						message : "JsonReader.read: Json object not found"
					};
				}
				dataResult = o;
				return this.readRecords(o);
			}
		});
// 生成一个新的分页的类，使其可以在初始化的时候设置pageSize
Ext.ux.newToolBar = Ext.extend(Ext.ux.MyToolbar, {
			constructor : function(config) {
				this.pageSize = config.pageSize || 50
				Ext.ux.newToolBar.superclass.constructor.call(this, config);
			}
		})

var logPager = new Ext.ux.newToolBar({
			pageSize : 3,
			store : storeEdit
		});
logPager.findByType("numberfield")[1].readOnly = true;
var operationGridPanel = new Ext.grid.GridPanel({
			header : false,
			region : 'center',
			store : storeEdit,
//			padding : '0px 5px 0px 5px',
			// region : 'center',
			// heigth : 400,
			cm : cm,
			// stripeRows : true,
			// viewConfig : {
			// forceFit : true,
			// deferEmptyText : '请等待...',
			// emptyText : '没有数据'
			// },
			bbar : logPager,
			listeners : {
				"cellclick" : function(g, ri, ci) {
					var r = g.getStore().getAt(ri); // Get the
					var dx = g.getColumnModel().getDataIndex(ci); // Get field
																	// name
					var type = dx == "paramCnt" && 1 || dx == "ctrlCnt" && 2
							|| dx == "attachTmnl" && 3 || dx == "detachTmnl"
							&& 4;
					window.dataType = type;
					openTab("日志查询", "./runMan/dutyLog/logQuery.jsp");
				}
			}
		});


var staffPanel = new Ext.Panel({
			layout : 'form',
			border : false,
			labelWidth : 80,
			columnWidth : .2,
			labelAlign : "right",
			style : {
				marginTop : '10px'
			},
			items : [{
						xtype : "stafftextfield",
						id : "userLog.counterpartNo",
						labelSeparator : "",
						allowBlank : false,
						width : 70,
						fieldLabel : "交接人员"
						
					}]
		});

var saveButtonPanel = new Ext.Button({
			style : {
				marginRight : '30px'
			},
			text : "保存",
			style : {
				marginTop : '10px'
			},
			handler : function() {
				var arr = getCom();
				var weather = Ext.getCmp("userLog.weather");
				var lowTem = Ext.getCmp("userLog.lowTemperature");
				var highTem = Ext.getCmp("userLog.topTemperature");
				var windDirection = Ext.getCmp("userLog.windDirection");
				var windForce = Ext.getCmp("userLog.windForce")
				var profile = Ext.getCmp("userLog.profile")
				var dutyDate = Ext.getCmp("userLog.logoutDate").getValue()
						.format("Y-m-d");
				var dutyTime = Ext.getCmp("userLog.logoutTime").getValue();
				// 交班时间
				var otherDate = dutyDate.replace(/-/g, "/") + " " + dutyTime;
				otherDate = new Date(otherDate);
				var otherStaff = Ext.getCmp("userLog.counterpartNo");
				var flat = true;
				var map = {
					"userLog.profile" : "值班摘要",
					"userLog.logoutTime" : "时间"
				}
				var msg = "";
				dataCom = arr;
				params = {};
				params["userLog.weather"] = weather.getValue();
				params["userLog.lowTemperature"] = lowTem.getValue();
				params["userLog.topTemperature"] = highTem.getValue();
				params["userLog.windDirection"] = windDirection.getValue();
				params["userLog.windForce"] = windForce.getValue();
				params["userLog.profile"] = profile.getValue();
				params["userLog.logoutDate"] = otherDate;
				params["userLog.counterpartNo"] = otherStaff.getValue();
				Ext.each(arr, function(a) {
							if (!a.validate()) {
								msg = a.fieldLabel + a.activeError;
								return flat = false;
							}
						});
				if (!flat) {
					return Ext.Msg.alert("错误", msg);
				}
				Ext.getBody().mask("保存中");
				Ext.Ajax.request({
							url : "runman/dutyLog!saveLog.action",
							params : params,
							callback:function(o,s,response){
								Ext.getBody().unmask();
								var o = Ext.decode(response.responseText);
								if (o.message && o.message.trim() != "") {
									setTimeout(function(){
										alert("bbb");
									Ext.Msg.alert("警告", o.message);
									},1500);
								}else{
								Ext.Msg.alert("提示","成功");
								}
							}
						});
			}
		});

var dateField = new Ext.Panel({
			layout : 'column',
			border : false,
			items : [{
						layout : 'form',
						columnWidth : .25,
						border : false,
						style : {
							marginTop : '10px'
						},
						labelWidth : 65,
						labelAlign:"right",
						items : [{
									xtype : "textfield",
									name : "userLog.logEntryDate",
									disabled : true,
									id : "userLog.logEntryDate",
									fieldLabel : "上班时间",
									labelSeparator : "",
									anchor : "100%"
								}]
					}, {
						layout : 'form',
						columnWidth : .2,
						border : false,
						style : {
							marginTop : '10px'
						},
						labelWidth : 65,
						items : [{
									xtype : "datefield",
									name : "offDutyDate",
									format : 'Y-m-d',
									editable : false,
									allowBlank : false,
									emptyText : '选择日期 ...',
									fieldLabel : "交班时间",
									id : "userLog.logoutDate",
									labelSeparator : "",
									value : new Date()
								}]
					}, {
						layout : 'form',
						border : false,
						labelWidth : 1,
						style : {
							marginTop : '10px'
						},
						columnWidth : .2,
						items : [{
									xtype : "timefield",
									name : "offDutyTime",
									format : 'H:i:s',
									id : "userLog.logoutTime",
									allowBlank : false,
									emptyText : '选择时间 ...',
									fieldLabel : "",
									labelSeparator : "",
									value : new Date(),
									width : 80
								}]
					}, staffPanel, saveButtonPanel]
		});

var calendar = new Ext.Panel({
			layout : {
				type : 'vbox',
				padding : '15px',
				align : 'center'
			},
			style : {
				marginLeft : '10px'
			},
			width : 200,
			height : 200,
			defaultType : 'label',
			items : [{
						text : year() + '年' + month() + '月' + ' ' + week()
					}, {
						width : 100,
						height : 100,
						text : day(),
						style : {
							color : 'red',
							fontWeight : 'bold',
							fontSize : '600%',
							textAlign : 'center'
						}
					}, {
						text : lunarYear() + '年' + cDay(month(), day())
					}]
		});

var dateAndOperator = new Ext.Panel({
			border : false,
			autoHeight : true,
			padding : '5px 0px 5px 20px',
			items : [dateField]
		});
var dataCom = [];
function getCom() {
	if (dataCom.length != 0) {
		return dataCom;
	}
	var arr = [];
	var weather = Ext.getCmp("userLog.weather");
	arr.push(weather);
	var lowTem = Ext.getCmp("userLog.lowTemperature");
	arr.push(lowTem);
	var highTem = Ext.getCmp("userLog.topTemperature");
	arr.push(highTem);
	var windDirection = Ext.getCmp("userLog.windDirection");
	arr.push(windDirection);
	var windForce = Ext.getCmp("userLog.windForce")
	arr.push(windForce);
	var profile = Ext.getCmp("userLog.profile")
	arr.push(profile);
	arr.push(Ext.getCmp("userLog.logoutDate"));
	arr.push(Ext.getCmp("userLog.logoutTime"));
	arr.push(Ext.getCmp("userLog.counterpartNo"));
	dataCom = arr;
	return arr;
}

var northNorthWestRegion = new Ext.Panel({
			border : false,
			layout : 'column',
			width : 450,
			height : 200,
			region : 'north',
			items : [calendar, weatherPanel]
		});

var northNorthCenterRegin = new Ext.Panel({
			layout : 'fit',
			width : 440,
//			height : 154,
			region : 'center',
			border : false,
			padding : '0px 15px 0px 10px',
			items : [onDutySummaryPanel]
		});

var northCenterRegion = new Ext.Panel({
			border : false,
			layout : 'fit',
			region : 'north',
			height : 200,
			// autoEl:"center",
			items : [chartPanel]
		});



/** ***找到当前用户的上班时间*** */
Ext.Ajax.request({
			url : "runman/dutyLog!findWorkTime.action",
			method : "post",
			success : function(response) {
				var o = Ext.decode(response.responseText);
				if (!o.workTime) {
					// Ext.Msg.alert("警告", "今天你还有没登录，请重新登录");
					return;
				}
				var workTime = o.workTime.replace(/-/g, function(v) {
							return "/";
						}).replace("T", " ");
				var d = new Date(workTime);
				d = d.format("Y-m-d H:i:s");
				var login=Ext.getCmp("userLog.logEntryDate");
				login.setValue(d);
				//login.el.dom.setAttribute("ext:qtip",d);
			}
		});
Ext.onReady(function() {
			var westPanel = new Ext.Panel({
						region : "west",
						border : false,
						width : 450,
						layout : "border",
						items : [northNorthWestRegion, northNorthCenterRegin]
					});
			var centerPanel = new Ext.Panel({
						region : "center",
						border : false,
						layout : "border",
						padding : '0px 10px 0px 10px',
						items : [northCenterRegion, operationGridPanel]
					});
			var southPanel = new Ext.Panel({
						region : "south",
						border : false,
						height : 42,
						items : [dateAndOperator]
					});
			var upCenterPanel = new Ext.Panel({
						region : "center",
						border : false,
						layout : "border",
						items : [westPanel, centerPanel]
					});
			/*
			 * 
			 * 
			 * panel
			 */
			var fillPanel = new Ext.Panel({
						border : false,
						// autoScroll : true,
						layout : 'border',
//						bodyStyle : 'padding:15px',
						items : [{
									region : 'center',
									autoScroll : true,
									layout : "border",
									items : [upCenterPanel, southPanel]
								}]
					})
			renderModel(fillPanel, '日志记录');
			storeEdit.load({
			params : {
				start : 0,
				limit : 3
			}
		});
		});
