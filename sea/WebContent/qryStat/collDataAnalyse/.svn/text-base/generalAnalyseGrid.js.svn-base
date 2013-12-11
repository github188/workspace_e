// ---------------------------------终端------------------------------------
// 日冻结终端统计------------------------------------
	var generalAnalysetopTmnlDay = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .3,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											fieldLabel : "查询日期从",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateFrom',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .3,
								layout : "form",
								labelWidth : 20,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateTo',
											labelStyle : "text-align:right;"
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = generalAnalysetopTmnlDay.find("name","dataDateFrom")[0].getValue();
									                    var end = generalAnalysetopTmnlDay.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
														tmnlDayStore.load({
															params:{
																consNo : trim(Ext.getCmp('general_consNo').getValue()),
																dataDateFrom : start, 
																dataDateTo : end
															}
														});
												}
											},
											width : 80
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        generalAnalyseWindowTmnlDay.hide();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var tmnlDayStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/tmnlDayList!queryTmnlDayStatList.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'tmnlDayStatList',
						totalProperty : 'totalCount'
					}, [{name:'daySuplyTime'},
						{name:'dayResetNum'},
						{name:'dayEcjumpNum'},
						{name:'dayBcjumpNum'},
						{name:'dayPcjumpNum'},
						{name:'dayRcjumpNum'},
						{name:'dayComm'},
						{name:'dataDate'},
						{name:'tmnlAssetNo'}])
		});
	var cmTmnlDay = new Ext.grid.ColumnModel([{
					header : "终端资产号",
					dataIndex : 'tmnlAssetNo',
					width : 100,
					align : "center"
				},{
					header : "冻结日期",
					dataIndex : 'dataDate',
					width : 100,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "left">' + val
						    + '</div>';
				        return html;
			        }
				},{
					header : "终端供电时间",
					dataIndex : 'daySuplyTime',
					width : 100,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "left">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "终端复位累计次数",
					dataIndex : 'dayResetNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "月电控跳闸累计次数",
					dataIndex : 'dayEcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "购电跳闸累计次数",
					dataIndex : 'dayBcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "功控跳闸累计次数",
					dataIndex : 'dayPcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "遥控跳闸累计次数",
					dataIndex : 'dayRcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "终端与主站通信流量",
					width : 120,
					dataIndex : 'dayComm',
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}]);		
	var generalAnalyseGridTmnlDay = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		colModel : cmTmnlDay,
		ds : tmnlDayStore,
		anchor:'100%',
		viewConfig : {
			forceFit : false
		},
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : tmnlDayStore
		})
	});
	var generalAnalyseWindowTmnlDay = new Ext.Window({
		frame:true,
		width:800,
		height:420,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"hide",//将窗体隐藏而并不销毁
		title:"【日冻结终端统计】",
		items:[generalAnalysetopTmnlDay,generalAnalyseGridTmnlDay]
	});
// 月冻结终端统计------------------------------------	
	var generalAnalysetopTmnlMon = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .3,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m",
											fieldLabel : "查询日期从",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateFrom',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .3,
								layout : "form",
								labelWidth : 20,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m",
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name :'dataDateTo',
											labelStyle : "text-align:right;"
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = generalAnalysetopTmnlMon.find("name","dataDateFrom")[0].getValue();
									                    var end = generalAnalysetopTmnlMon.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
														tmnlMonStore.load({
															params:{
																consNo : trim(Ext.getCmp('general_consNo').getValue()),
																dataDateFrom : start, 
																dataDateTo : end
															}
														});
												}
											},
											width : 80
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        generalAnalyseWindowTmnlMon.hide();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var tmnlMonStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/tmnlMonList!queryTmnlMonStatList.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'tmnlMonStatList',
						totalProperty : 'totalCount'
					}, [{name:'monSuplyTime'},
						{name:'monResetNum'},
						{name:'monEcjumpNum'},
						{name:'monBcjumpNum'},
						{name:'monPcjumpNum'},
						{name:'monRcjumpNum'},
						{name:'monComm'},
						{name:'dataDate'},
						{name:'tmnlAssetNo'}])
		});
	var cmTmnlMon = new Ext.grid.ColumnModel([{
					header : "终端资产号",
					dataIndex : 'tmnlAssetNo',
					width : 100,
					align : "center"
				},{
					header : "冻结日期",
					dataIndex : 'dataDate',
					width : 100,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "left">' + val
						    + '</div>';
				        return html;
			        }
				},{
					header : "终端供电时间",
					dataIndex : 'monSuplyTime',
					width : 100,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "left">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "终端复位累计次数",
					dataIndex : 'monResetNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "月电控跳闸累计次数",
					dataIndex : 'monEcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "购电跳闸累计次数",
					dataIndex : 'monBcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "功控跳闸累计次数",
					dataIndex : 'monPcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "遥控跳闸累计次数",
					dataIndex : 'monRcjumpNum',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}, {
					header : "终端与主站通信流量",
					dataIndex : 'monComm',
					width : 120,
					align : 'center',
					renderer : function(val) {
						if(null == val){
							val = "";
						}
				        var html = '<div align = "right">' + val
						    + '</div>';
				        return html;
			        }
				}]);		
	var generalAnalyseGridTmnlMon = new Ext.grid.GridPanel({
		height:345,
		autoScroll:true,
		stripeRows : true,
		viewConfig : {
            forceFit : false
        },
		anchor:'100%',
		colModel : cmTmnlMon,
		ds : tmnlMonStore,
		bbar : new Ext.ux.MyToolbar({
			enableExpAll : true,
			store : tmnlMonStore
		})
	});
	var generalAnalyseWindowTmnlMon = new Ext.Window({
		frame:true,
		width:800,
		height:420,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"hide",//将窗体隐藏而并不销毁
		title:"【月冻结终端统计】",
		items:[generalAnalysetopTmnlMon,generalAnalyseGridTmnlMon]
	});
	
//	----------------------------------------总加组-------------------------------------------
//	日冻结总加组统计-----------------------------------------
	var generalAnalysetopTotalDay = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .3,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											fieldLabel : "查询日期从",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name : 'dataDateFrom',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .3,
								layout : "form",
								labelWidth : 20,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name : 'dataDateTo',
											labelStyle : "text-align:right;"//,
										}]
							},{
								columnWidth : .2,// ------------------
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														var start = generalAnalysetopTotalDay.find("name","dataDateFrom")[0].getValue();
									                    var end = generalAnalysetopTotalDay.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
														storeTotalDay.load({
															params:{consNo : trim(Ext.getCmp('general_consNo').getValue()), 
																	dataDateFrom : start, 
																	dataDateTo : end
																}
														});
												}
											},
											width : 80
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        generalAnalyseWindowTotalDay.hide();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var storeTotalDay = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/totalDayList!queryTotalDayStatList.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'totalDayStatList',
						totalProperty : 'totalCount'
					}, [{name:'tmnlAssetNo'},
						{name:'dataDate'},
						{name:'dmaxAp'},
						{name:'dmaxApTime'},
						{name:'dminAp'},
						{name:'dminApTime'},
						{name:'dzeroApSumtime'},
						{name:'daySume'},
						{name:'daySume1'},
						{name:'daySume2'},
						{name:'daySume3'},
						{name:'daySume4'},
						{name:'daySume5'},
						{name:'daySume6'},
						{name:'daySume7'},
						{name:'daySume8'},
						{name:'daySume9'},
						{name:'daySume10'},
						{name:'daySume11'},
						{name:'daySume12'},
						{name:'daySume13'},
						{name:'daySumre'}])
		});
	 var cmTotalDay = new Ext.grid.ColumnModel([{
				header : "终端资产号",
				sortable : true,
				dataIndex : 'tmnlAssetNo',
				align : "center"
			}, {
				header : "冻结日期",
				sortable : true,
				dataIndex : 'dataDate',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "最大有功功率",
				sortable : true,
				dataIndex : 'dmaxAp',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "发生时间",
				sortable : true,
				dataIndex : 'dmaxApTime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "最小有功功率",
				sortable : true,
				dataIndex : 'dminAp',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "发生时间",
				sortable : true,
				dataIndex : 'dminApTime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "为零月累计时间",
				sortable : true,
				dataIndex : 'dzeroApSumtime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "累计有功总电能量",
				sortable : true,
				dataIndex : 'daySume',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率1电能量",
				sortable : true,
				hidden:true,
				dataIndex : 'daySume1',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率2电能量",
				sortable : true,
				hidden:true,
				dataIndex : 'daySume2',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率3电能量",
				hidden:true,
				dataIndex : 'daySume3',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率4电能量",
				dataIndex : 'daySume4',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率5电能量",
				dataIndex : 'daySume5',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率6电能量",
				dataIndex : 'daySume6',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率7电能量",
				dataIndex : 'daySume7',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率8电能量",
				dataIndex : 'daySume8',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率9电能量",
				dataIndex : 'daySume9',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率10电能量",
				dataIndex : 'daySume10',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率11电能量",
				dataIndex : 'daySume11',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率12电能量",
				dataIndex : 'daySume12',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率13电能量",
				dataIndex : 'daySume13',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率无功总电能量",
				dataIndex : 'daySumre',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}]);		
	var generalAnalyseGridTotalDay = new Ext.grid.GridPanel({
			height:345,
			autoScroll:true,
			stripeRows : true,
			enableColumnMove : false,// 设置表头不可拖动
			viewConfig : {
	                    forceFit : false
	                },
			anchor:'100%',
			colModel : cmTotalDay,
			store : storeTotalDay,
			bbar : new Ext.ux.MyToolbar({
				enableExpAll : true,
				store : storeTotalDay
			})
		});		
	var generalAnalyseWindowTotalDay = new Ext.Window({
		frame:true,
		width:800,
		height:420,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"hide",//将窗体隐藏而并不销毁
		title:"【日冻结总加组统计】",
		items:[generalAnalysetopTotalDay,generalAnalyseGridTotalDay]
	});
	
//	月冻结总加组统计-----------------------------------------	
	var generalAnalysetopTotalMon = new Ext.Panel({
		height : 40,
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .3,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m",
											fieldLabel : "查询日期从",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name : 'dataDateFrom',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .3,
								layout : "form",
								labelWidth : 20,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m",
											fieldLabel : "到",
											editable:false,
											allowBlank:false,
											labelSeparator:'',
											name : 'dataDateTo',
											labelStyle : "text-align:right;"//,
										}]
							},{
								columnWidth : .2,// ------------------
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){		
														var start = generalAnalysetopTotalMon.find("name","dataDateFrom")[0].getValue();
									                    var end = generalAnalysetopTotalMon.find("name","dataDateTo")[0].getValue();
									                    if((start - end) > 0){
									                    	Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
									                    	return;
									                    }
														storeTotalMon.load({
															params:{consNo : trim(Ext.getCmp('general_consNo').getValue()), 
																	dataDateFrom : start, 
																	dataDateTo : end
																}
														});
												}
											},
											width : 80
										}]
							},{
								columnWidth : .2,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
									text : "退出",
									listeners : {
										"click": function (){
									        generalAnalyseWindowTotalMon.hide();
										}
									},
									width : 80
								
								}]
							}]
				}]
	});
	var storeTotalMon = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/totalMonList!queryTotalMonStatList.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'totalMonStatList',
						totalProperty : 'totalCount'
					}, [{name:'tmnlAssetNo'},
						{name:'dataDate'},
						{name:'mmaxAp'},
						{name:'mmaxApTime'},
						{name:'mminAp'},
						{name:'mminApTime'},
						{name:'mzeroApSumtime'},
						{name:'monSume'},
						{name:'monSume1'},
						{name:'monSume2'},
						{name:'monSume3'},
						{name:'monSume4'},
						{name:'monSume5'},
						{name:'monSume6'},
						{name:'monSume7'},
						{name:'monSume8'},
						{name:'monSume9'},
						{name:'monSume10'},
						{name:'monSume11'},
						{name:'monSume12'},
						{name:'monSume13'},
						{name:'monSumre'}])
		});
	 var cmTotalMon = new Ext.grid.ColumnModel([{
				header : "终端资产号",
				dataIndex : 'tmnlAssetNo',
				align : "center"
			}, {
				header : "冻结日期",
				dataIndex : 'dataDate',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "最大有功功率",
				dataIndex : 'mmaxAp',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "发生时间",
				dataIndex : 'mmaxApTime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "最小有功功率",
				dataIndex : 'mminAp',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "发生时间",
				dataIndex : 'mminApTime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "left">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "为零月累计时间",
				dataIndex : 'mzeroApSumtime',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "累计有功总电能量",
				dataIndex : 'monSume',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率1电能量",
				hidden:true,
				dataIndex : 'monSume1',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率2电能量",
				hidden:true,
				dataIndex : 'monSume2',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率3电能量",
				hidden:true,
				dataIndex : 'monSume3',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率4电能量",
				dataIndex : 'monSume4',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率5电能量",
				dataIndex : 'monSume5',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率6电能量",
				dataIndex : 'monSume6',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率7电能量",
				dataIndex : 'monSume7',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率8电能量",
				dataIndex : 'monSume8',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率9电能量",
				dataIndex : 'monSume9',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率10电能量",
				dataIndex : 'monSume10',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率11电能量",
				dataIndex : 'monSume11',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率12电能量",
				dataIndex : 'monSume12',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率13电能量",
				dataIndex : 'monSume13',
				hidden:true,
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}, {
				header : "费率无功总电能量",
				dataIndex : 'monSumre',
				align : 'center',
				renderer : function(val) {
					if(null == val){
						val = "";
					}
			        var html = '<div align = "right">' + val
					    + '</div>';
			        return html;
		        }
			}]);
	var generalAnalyseGridTotalMon = new Ext.grid.GridPanel({
			height:345,
			autoScroll:true,
			stripeRows : true,
			enableColumnMove : false,// 设置表头不可拖动
			viewConfig : {
	                    forceFit : false
	                },
			anchor:'100%',
			colModel : cmTotalMon,
			store : storeTotalMon,
			bbar : new Ext.ux.MyToolbar({
				enableExpAll : true,
				store : storeTotalMon
			})
		});		
	var generalAnalyseWindowTotalMon = new Ext.Window({
		frame:true,
		width:800,
		height:420,
		layout:"form",
		modal:true,
		plain:true,//设置背景颜色
		resizable:false,
		draggable:false,//不可移动
		buttonAlign:"center",//按钮的位置
		closeAction:"hide",//将窗体隐藏而并不销毁
		title:"【月冻结总加组统计】",
		items:[generalAnalysetopTotalMon,generalAnalyseGridTotalMon]
	});
	
//终端调用函数	
function generalAnalyseWindow1Show(type){
	var consNo = Ext.getCmp('general_consNo').getValue();
	consNo= trim(consNo);
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','请从左边树选择用户节点，或输入户号！');
	    return;
    }
	var dayTmnlDateTo = Ext.getCmp('general_date').getValue();
	var dayTmnlDateFrom = dayTmnlDateTo.add(Date.DAY, -7);
	var monthTmnlDateTo = Ext.getCmp('general_date').getValue();
	var dataDateFromTmp = Ext.getCmp('general_date').getRawValue();
	var year = dataDateFromTmp.substring(0, dataDateFromTmp.indexOf('-'));
	var month = dataDateFromTmp.substring(5, dataDateFromTmp.indexOf('-'));
	var day = dataDateFromTmp.substring(dataDateFromTmp.length, dataDateFromTmp.lastIndexOf('-')+ 1);
	var monthTmnlDateFrom = year+"-01-"+day;
	if(type == 1){
		Ext.Ajax.request({
			url : "qrystat/queryConsName!queryconsName.action",
			params : {
				consNo : consNo
			},
			success : function(response) {
				var consName = Ext.decode(response.responseText).consName;
				generalAnalyseWindowTmnlDay.setTitle((consName==null?"":consName) + "【日冻结终端统计】");
				generalAnalysetopTmnlDay.find("name","dataDateFrom")[0].setValue(dayTmnlDateFrom);
				generalAnalysetopTmnlDay.find("name","dataDateTo")[0].setValue(dayTmnlDateTo);
				tmnlDayStore.load({
					params:{consNo : consNo, 
							dataDateFrom : dayTmnlDateFrom, 
							dataDateTo : dayTmnlDateTo}
				});
				generalAnalyseWindowTmnlDay.show();
			}
		});
	} else {
		Ext.Ajax.request({
			url : "qrystat/queryConsName!queryconsName.action",
			params : {
				consNo : consNo
			},
			success : function(response) {
				var consName = Ext.decode(response.responseText).consName;
				generalAnalyseWindowTmnlMon.setTitle((consName==null?"":consName) + "【月冻结终端统计】");
				
				generalAnalysetopTmnlMon.find("name","dataDateFrom")[0].setValue(monthTmnlDateFrom);
				generalAnalysetopTmnlMon.find("name","dataDateTo")[0].setValue(monthTmnlDateTo);
				tmnlMonStore.load({
					params:{consNo : consNo, 
							dataDateFrom : monthTmnlDateFrom, 
							dataDateTo : monthTmnlDateTo}
				});
				generalAnalyseWindowTmnlMon.show();
			}
		});
	}
}

//总加组调用函数
function generalAnalyseWindow2Show(type){
	var consNo = Ext.getCmp('general_consNo').getValue();
	consNo= trim(consNo);
	if(null == consNo || 0 >= consNo.length){
		Ext.MessageBox.alert('提示','请从左边树选择用户节点，或输入户号！');
	    return;
    }
	var dayTotalDateTo = Ext.getCmp('general_date').getValue();
	var dayTotalDateFrom = dayTotalDateTo.add(Date.DAY, -7);
	var monthTotalDateTo = Ext.getCmp('general_date').getValue();
	var dataDateFromTmp = Ext.getCmp('general_date').getRawValue();
	var year = dataDateFromTmp.substring(0, dataDateFromTmp.indexOf('-'));
	var month = dataDateFromTmp.substring(5, dataDateFromTmp.indexOf('-'));
	var day = dataDateFromTmp.substring(dataDateFromTmp.length, dataDateFromTmp.lastIndexOf('-')+ 1);
	var monthTotalDateFrom = year+"-01-"+day;
	if(type == 1){
		Ext.Ajax.request({
			url : "qrystat/queryConsName!queryconsName.action",
			params : {
				consNo : consNo
			},
			success : function(response) {
				var consName = Ext.decode(response.responseText).consName;
				generalAnalyseWindowTotalDay.setTitle((consName==null?"":consName) + "【日冻结总加组统计】");
				
				generalAnalysetopTotalDay.find("name","dataDateFrom")[0].setValue(dayTotalDateFrom);
				generalAnalysetopTotalDay.find("name","dataDateTo")[0].setValue(dayTotalDateTo);
				storeTotalDay.load({
					params:{consNo : consNo, 
							dataDateFrom : dayTotalDateFrom, 
							dataDateTo : dayTotalDateTo}
				});
				storeTotalDay.baseParams['consNo'] = consNo;
				storeTotalDay.baseParams['dataDateFrom'] = dayTotalDateFrom;
				storeTotalDay.baseParams['dataDateTo'] = dayTotalDateTo;
				generalAnalyseWindowTotalDay.show();
			}
		});
	} else {
		Ext.Ajax.request({
			url : "qrystat/queryConsName!queryconsName.action",
			params : {
				consNo : consNo
			},
			success : function(response) {
				var consName = Ext.decode(response.responseText).consName;
				generalAnalyseWindowTotalMon.setTitle((consName==null?"":consName) + "【月冻结总加组统计】");
				
				generalAnalysetopTotalMon.find("name","dataDateFrom")[0].setValue(monthTotalDateFrom);
				generalAnalysetopTotalMon.find("name","dataDateTo")[0].setValue(monthTotalDateTo);
				storeTotalMon.load({
					params:{consNo : consNo, 
							dataDateFrom : monthTotalDateFrom, 
							dataDateTo : monthTotalDateTo}
				});
				storeTotalMon.baseParams['consNo'] = consNo;
				storeTotalMon.baseParams['dataDateFrom'] = monthTotalDateFrom;
				storeTotalMon.baseParams['dataDateTo'] = monthTotalDateTo;
				generalAnalyseWindowTotalMon.show();
			}
		});
	}
}