var origFrameQryID;

/**
 * @author 修改 陈国章 2010-08-20
 * @argument 增加心跳查询功能
 */
var DateStart, DateEnd, terminalAddr, protocolCode, messageData, onlineNum, offlineNum;
var labelText = new Ext.form.Label({

});
var treels = new LeftTreeListener({
			modelName : '原始报文查询',
			processClick : function(p, node, e) {
				treeclick(node, e);
			},
			processUserGridSelect : function(cm, row, record) {
				Ext.getCmp("origFrameQryID").setValue(record.get('consNo'));
				Ext.getCmp("origFrameQryName").setValue(record.get('consName'));
				origFrameQryID = record.get('consNo');
				getOrigFrameQryAsset();
				origFrameQryGridStore.removeAll();
				

			}
		});
// 点击左边树事件
function treeclick(node, e) {

	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	var name = node.text;
	if (node.isLeaf()) {
		Ext.getCmp("origFrameQryID").setValue(obj.consNo);
		Ext.getCmp("origFrameQryName").setValue(name);
		origFrameQryID = obj.consNo;
		getOrigFrameQryAsset();
		origFrameQryGridStore.removeAll();

	} else {
		return true;
	}
	
};

// 查询客户对应的终端资产号函数
function getOrigFrameQryAsset() {
	Ext.Ajax.request({
				url : 'baseapp/origFrameQry!queryOrigFrameQryAsset.action',
				params : {
					origFrameQryID : origFrameQryID
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					tmnlAssetNostore.loadData(result);
					tmnlAssetNostore2.loadData(result);
					if (!Ext.isEmpty(tmnlAssetNostore)
							&& 0 < tmnlAssetNostore.getCount()) {
						var terminalAddr = tmnlAssetNostore.getAt(0)
								.get('terminalAddr');
						protocolCode = tmnlAssetNostore.getAt(0)
								.get('protocolCode');
						origFrameQrycombo.setValue(terminalAddr);
					}
				}
			});
};
// 查询up报文
function queryorigFrameQry() {
	terminalAddr = Ext.getCmp("origFrameQryAsset").getValue();
	DateStart = Ext.getCmp("origFrameQryStart").getValue()
			.format('Y-m-d H:i:s');
	DateEnd = Ext.getCmp("origFrameQryEnd").getValue().format('Y-m-d H:i:s');
	origFrameQryGridStore.baseParams = {
		terminalAddr : terminalAddr,
		protocolCode : protocolCode,
		DateStart : DateStart,
		DateEnd : DateEnd
	};
	origFrameQryGridStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});

}

var tmnlAssetNostore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			// fields : ['origFrameQryAsset'],
			reader : new Ext.data.JsonReader({
						root : 'origFrameQryAssetList',
						idProperty : 'terminalAddr'
					}, ['terminalAddr', 'protocolCode'])
		});
var tmnlAssetNostore2 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			// fields : ['origFrameQryAsset'],
			reader : new Ext.data.JsonReader({
						root : 'origFrameQryAssetList',
						idProperty : 'terminalAddr'
					}, ['terminalAddr', 'protocolCode'])
		});

var origFrameQrycombo = new Ext.form.ComboBox({
			id : 'origFrameQryAsset',
			editable : false,
			store : tmnlAssetNostore,
			displayField : 'terminalAddr',
			// labelStyle : "text-align:right;width:80px;",
			valueField : 'terminalAddr',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '逻辑地址',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			width : 100,
			forceSelection : true
		});

var origFrameQrycombo2 = new Ext.form.ComboBox({
			id : 'origFrameQryAsset2',
			editable : false,
			store : tmnlAssetNostore2,
			displayField : 'terminalAddr',
			// labelStyle : "text-align:right;width:80px;",
			valueField : 'terminalAddr',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '逻辑地址',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			width : 100,
			forceSelection : true
		});

origFrameQrycombo.on('select', function(combox) {
			var value = combox.getValue();
			var record = tmnlAssetNostore.getById(value);
			protocolCode = record.get('protocolCode');
		});

var origFrameQryPanel = new Ext.Panel({
	plain : true,
	border : false,
	region : 'north',
	layout : 'form',
	height : 65,
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
										fieldLabel : "户号<font color='red'>*</font>",
										id : 'origFrameQryID',
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
							defaultType : "textfield",
							items : [{
										fieldLabel : "户名",
										readOnly : true,
										id : 'origFrameQryName',
										labelSeparator : "",
										width : 150
									}]
						}, {
							columnWidth : .54,
							border : false,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 100,
							defaultType : '',
							items : [origFrameQrycombo]
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
							defaultType : "datetimefield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "从",
										editable : false,
										id : 'origFrameQryStart',
										labelSeparator : "",
										// format:'y-m-d H:i:s',
										value : new Date().clearTime(true),
										// anchor:'100%'
										width : 150
									}]
						}, {
							columnWidth : .23,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 60,
							defaultType : "datetimefield",
							baseCls : "x-plain",
							items : [{
								fieldLabel : "到",
								editable : false,
								id : 'origFrameQryEnd',
								labelSeparator : "",
								// format:'y-m-d H:i:s',
								value : new Date().add(Date.DAY, 1)
										.clearTime(true),
								// anchor:'100%'
								width : 150
							}]
						}, {
							columnWidth : .10,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 60,
							defaultType : "datetimefield",
							baseCls : "x-plain",
							defaultType : "button",
							baseCls : "x-plain",
							items : [{
										text : "查询",
										width : 70,
										handler : function() {
											var CONS = Ext
													.getCmp("origFrameQryID");
											if (!CONS.isValid(true)) {
												CONS.markInvalid('不能为空');
												return true;
											};
											queryorigFrameQry();
										}

									}]
						}, {
							layout : "form",
							defaultType : "button",
							baseCls : "x-plain",
							items : [{
								text : "心跳查询",
								width : 70,
								listeners : {
									"click" : function() {
										var CONS = Ext.getCmp("origFrameQryID");
										if (!CONS.isValid(true)) {
											CONS.markInvalid('不能为空');
											return true;
										};
										openHeartBeatStatus();
									}
								}
							}]

						}]
			}]
});
var heartBeat_store = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : 'baseapp/origFrameQry!queryHeartBeat.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'hbList',
				totalProperty : 'totalCount'

			}, [{
						name : 'orgName'
					}, {
						name : 'consNo'
					}, {
						name : 'consName'
					}, {
						name : 'tmnlAddr'
					}, {
						name : 'tmnlAssetNo'
					}, {
						name : 'isOnLine'
					}, {
						name : 'onOffTime'
					}])
});


var queryPanel = new Ext.Panel({
	plain : true,
	border : false,
	region : 'north',
	layout : 'column',
	// layoutConfig:{
	// forceFit:true
	// },
	height : 35,
	margins : 'top:5px,right:0,bottom:0,left:0',
	 bodyStyle:'padding:5px 0 0 0 ',
	items : [{
				columnWidth : .30,
				layout : 'form',
				labelAlign : 'right',
				labelWidth : 60,
				defaultType : "datetimefield",
				baseCls : "x-plain",
				items : [{
							fieldLabel : "从",
							editable : false,
							id : 'hbQryStart',
							labelSeparator : "",
							// format:'y-m-d H:i:s',
							value : new Date().clearTime(true),
							// anchor:'100%'
							width : 150
						}]
			}, {
				columnWidth : .30,
				layout : 'form',
				labelAlign : 'right',
				labelWidth : 60,
				defaultType : "datetimefield",
				baseCls : "x-plain",
				items : [{
					fieldLabel : "到",
					editable : false,
					id : 'hbQryEnd',
					labelSeparator : "",
					// format:'y-m-d H:i:s',
					value : new Date().add(Date.DAY, 1)
							.clearTime(true),
					// anchor:'100%'
					width : 150
				}]
			}, {

				border : false,
				columnWidth : .25,
				layout : 'form',
				labelAlign : 'right',
				labelWidth : 60,
				items : [origFrameQrycombo2]
			}, {
				columnWidth : .15,
				layout : 'form',
				labelAlign : 'right',
				labelWidth : 60,
				baseCls : "x-plain",
				defaultType : "button",
				baseCls : "x-plain",
				items : [{
							text : "查询",
							width : 70,
							listeners : {
								"click" : function() {
									queryHeartBeat();
								}
							}
						}]
			}]
});

var heartBeat_cm = new Ext.grid.ColumnModel([{
	header : '供电单位 ',
	dateIndex : 'orgName',
	align : 'center'
}, {
	header : '用户编号',
	dateIndex : 'consNo',
	align : 'center'
}, {
	header : '用户名称',
	dateIndex : 'consName',
	align : 'center',
	renderer : function(v) {
		var html = '<span ext:qtitle="用户名称:" ext:qtip="' + v + '">'
				+ v + '</span>';
		return '<div align = "center">' + html + '</div>';
	}
}, {
	header : '逻辑地址',
	dateIndex : 'tmnlAddr',
	align : 'center',
	renderer : function(v) {
		var html = '<span ext:qtitle="逻辑地址:" ext:qtip="' + v + '">'
				+ v + '</span>';
		return '<div align = "center">' + html + '</div>';
	}
}, {
	header : '终端资产号',
	dateIndex : 'tmnlAssetNo',
	align : 'center',
	renderer : function(v) {
		var html = '<span ext:qtitle="终端资产号:" ext:qtip="' + v
				+ '">' + v + '</span>';
		return '<div align = "center">' + html + '</div>';
	}
}, {
	header : '状态',
	dateIndex : 'isOnLine',
	align : 'center',
	renderer : function(v) {
		var html = '<span ext:qtitle="状态:" ext:qtip="' + v + '">'
				+ v + '</span>';
		return '<div align = "center">' + html + '</div>';
	}
}, {
	header : '上线离线时间',
	dateIndex : 'onOffTime',
	align : 'center',
	renderer : function(v) {
		var html = '<span ext:qtitle="时间:" ext:qtip="' + v + '">'
				+ v + '</span>';
		return '<div align = "center">' + html + '</div>';
	}
}]);

var heartBeat_grid = new Ext.grid.GridPanel({
	id : 'heartBeat_grid',
	border : true,
	region : 'center',
	autoScroll : true,
	layoutConfig : {
		forceFit : true
	},
	bbar : new Ext.ux.MyToolbar({
				store : heartBeat_store,
				enableExpAll : true
			}),
	tbar : [{
				xtype : 'label',
				html : "<font font-weight:bold;>心跳查询</font>"
			}, '->', labelText],
	autoWidth : true,
	stripeRows : true,
	store : heartBeat_store,
	cm : heartBeat_cm
});
var heartBeat_win = new Ext.Window({
	name : 'heartBeat_Window',
	title : '心跳状态查询',
	width : 900,
	height : 400,
	closeAction : 'hide',
	// initHidden:true,
	// autoDestroy:false,
	
	layout : 'border',
	modal : true, // True 表示为当window显示时对其后面的一切内容进行遮罩,默认为false
	border : false,
	resizable : true,
	items : [queryPanel, heartBeat_grid]
});


function openHeartBeatStatus() {
	// alert(terminalAddr);//
	
	var tmnl = Ext.getCmp("origFrameQryAsset").getRawValue();

	DateStart = Ext.getCmp("origFrameQryStart").getValue()
			.format('Y-m-d H:i:s');
	DateEnd = Ext.getCmp("origFrameQryEnd").getValue().format('Y-m-d H:i:s');
	heartBeat_store.load({
		params : {
			DateStart : DateStart,
			DateEnd : DateEnd,
			protocolCode : protocolCode,
			terminalAddr : tmnl,
			consNo : origFrameQryID
		}
	});	
	Ext.Ajax.request({
							url : "baseapp/origFrameQry!onoffStat.action",
							params:{
								DateStart : DateStart,
			                    DateEnd : DateEnd,
			                    terminalAddr : tmnl,
			                    consNo : origFrameQryID
							},
							success : function(response) {
								var o = Ext.decode(response.responseText);
								onlineNum = o.onNum;
								offlineNum = o.offNum;
								labelText
										.setText(
												"<font font-weight:bold;color='red'>上线数"
														+ onlineNum + "  ,离线数"
														+ offlineNum
														+ "</font>", false);
							}
						});
	
	heartBeat_win.show();
}
function queryHeartBeat() {
	var tmnl1 = Ext.getCmp("origFrameQryAsset2").getRawValue();
	DateStart = Ext.getCmp("hbQryStart").getValue().format('Y-m-d H:i:s');
	DateEnd = Ext.getCmp("hbQryEnd").getValue().format('Y-m-d H:i:s');
	heartBeat_store.load({
				params : {
					DateStart : DateStart,
					DateEnd : DateEnd,
					protocolCode : protocolCode,
					terminalAddr : tmnl1,
					consNo : origFrameQryID
				}
			});
			Ext.Ajax.request({
							url : "baseapp/origFrameQry!onoffStat.action",
							params:{
								DateStart : DateStart,
			                    DateEnd : DateEnd,
			                    terminalAddr : tmnl1,
			                    consNo : origFrameQryID
							},
							success : function(response) {
								var o = Ext.decode(response.responseText);
								onlineNum = o.onNum;
								offlineNum = o.offNum;
								labelText
										.setText(
												"<font font-weight:bold;color='red'>上线数"
														+ onlineNum + "  ,离线数"
														+ offlineNum
														+ "</font>", false);
							}
						});
			
}

// var labelText=new Ext.form.Label({
//    	
// });
// var heartBeat_grid = new Ext.grid.GridPanel({
// id : 'heartBeat_grid',
// border : true,
// region : 'center',
// autoScroll : true,
// layoutConfig:{
// forceFit:true
// },
// bbar : new Ext.ux.MyToolbar({
// store : heartBeat_store,
// enableExpAll : true
// }),
// tbar:[{
// xtype : 'label',
// html : "<font font-weight:bold;>心跳查询</font>"
// }
// ,'->', labelText
// ],
// autoWidth : true,
// stripeRows : true,
// store : heartBeat_store,
// cm : heartBeat_cm
// });
//
// var heartBeat_win = new Ext.Window({
// name : 'heartBeat_Window',
// title : '心跳状态查询',
// width : 900,
// height : 500,
// closeAction:'hide',
// initHidden:true,
// autoDestroy:true,
// layout : 'border',
// modal : true, // True 表示为当window显示时对其后面的一切内容进行遮罩,默认为false
// border : false,
// resizable : true,
// items : [queryPanel, heartBeat_grid]
// });
// 弹出窗口显示统计每日心跳状态
// function openHeartBeatStatus() {
//
// // 取Store
//	
// Ext.Ajax.request({
// url : "baseapp/origFrameQry!onoffStat.action",
// success : function(response) {
// var o = Ext.decode(response.responseText);
// onlineNum=o.onNum;
// offlineNum=o.offNum;
// alert(onlineNum);
//
// }
// });
// // alert(terminalAddr);//
// var tmnl=Ext.getCmp("origFrameQryAsset").getRawValue();
// // var tmnl2=origFrameQrycombo2.getRawValue();
// // alert(tmnl2);
// // alert(tmnl);
// DateStart = Ext.getCmp("origFrameQryStart").getValue()
// .format('Y-m-d H:i:s');
// DateEnd = Ext.getCmp("origFrameQryEnd").getValue().format('Y-m-d H:i:s');
// heartBeat_store.load({
// params : {
// DateStart : DateStart,
// DateEnd : DateEnd,
// protocolCode : protocolCode,
// terminalAddr :tmnl,
// consNo : origFrameQryID
// }
// });
//	
// // if(!Ext.isEmpty(queryPanel)||queryPanel!=null)
// // {
// // heartBeat_win.remove(queryPanel);
// // heartBeat_win.doLayout();
// // heartBeat_win.add(queryPanel);
// // heartBeat_win.doLayout();
// //
// // }
//	
//
// heartBeat_win.show();
// }

// ------------------------------------------------------------------------------------------
var origFrameQryGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'baseapp/origFrameQry!queryorigFrameQry.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'origFrameQryList',
						totalProperty : 'totalCount'
					}, [{
								name : 'commTime',
								type : 'date',
								dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00

							}, {
								name : 'terminalAddr'
							}, {
								name : 'commMode'
							}, {
								name : 'msgLen'
							}, {
								name : 'direction'
							}, {
								name : 'message'
							}, {
								name : 'fromAddr'
							}, {
								name : 'toAddr'
							}])
		});
var groupCM = new Ext.grid.ColumnModel([{
			header : '时间',
			width : 120,
			dataIndex : 'commTime',
			sortable : true,
			align : 'center',
			fomart : 'Y-m-d H:i:s',
			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
		}, {
			header : '逻辑地址',
			width : 100,
			dataIndex : 'terminalAddr',
			sortable : true,
			align : 'center'
		}, {
			header : '通信方式',
			width : 60,
			dataIndex : 'commMode',
			sortable : true,
			align : 'center'
		}, {
			header : '字节数',
			width : 50,
			dataIndex : 'msgLen',
			sortable : true,
			align : 'center'
		}, {
			header : '方向',
			width : 50,
			dataIndex : 'direction',
			sortable : true,
			align : 'center',
			renderer : function(value) {
				if (value == '1') {
					return '<font color = "green">上行</font>';
				} else if (value == '2') {
					return '<font color = "red">下行</font>';
				}
			}
		}, {
			header : '报文内容',
			width : 400,
			// editor:new Ext.form.TextField(),
			dataIndex : 'message',
			sortable : true,
			// align : 'center',
			align : 'center',
			renderer : function(v) {
				var s = "";
				var vlength = v.length;
				var x = 0;
				while (vlength > 0) {
					s += v.substring(x, x + 45) + "<br>";
					x = x + 45;
					vlength = vlength - 45;
				};
				var html = '<span ext:qtitle="报文内容" ext:qtip="' + s + '">' + v
						+ '</span>';
				return html;
			}
			// readOnly:true
		}, {
			header : '来源地址',
			width : 120,
			dataIndex : 'fromAddr',
			sortable : true,
			align : 'center'
		}, {
			header : '目标地址',
			width : 120,
			dataIndex : 'toAddr',
			sortable : true,
			align : 'center'
		}]);

var origFrameQryGrid = new Ext.grid.GridPanel({
			region : 'center',
			title : '报文查询',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : groupCM,
			ds : origFrameQryGridStore,
			bbar : new Ext.ux.MyToolbar({
						store : origFrameQryGridStore,
						enableExpAll : true
					})
		});

origFrameQryGrid.addListener('cellclick', OFQScellclick);

function OFQScellclick(origFrameQryGrid, rowIndex, columnIndex, e) {
	if (rowIndex >= 0 && columnIndex == 5) {
		var record = origFrameQryGrid.getStore().getAt(rowIndex); // Get the
		// Record

		var fieldName = origFrameQryGrid.getColumnModel()
				.getDataIndex(columnIndex); // Get field name

		messageData = record.get(fieldName);
		OFQSubWindowShow(messageData, protocolCode);
	}
};
Ext.onReady(function() {
	// 设置顶层的公变采集点查询panel
	var origFrameQryAllpanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				listeners : {
					'beforedestroy' : function() {
						heartBeat_win.close();
					}
				},
				layout : 'border',
				items : [origFrameQryPanel, origFrameQryGrid]
			});
	renderModel(origFrameQryAllpanel, '原始报文查询');

	if (typeof(staticConsNo) != 'undefined'
			&& typeof(staticConsName) != 'undefined'
			&& typeof(staticTmnlAssetAddr) != 'undefined') {
		Ext.getCmp("origFrameQryID").setValue(staticConsNo);
		Ext.getCmp("origFrameQryName").setValue(staticConsName);
		Ext.getCmp("origFrameQryAsset").setValue(staticTmnlAssetAddr);
		origFrameQryID = staticConsNo;
		tmnlAssetNostore.removeAll();
		origFrameQrycombo.clearValue();
		getOrigFrameQryAsset();
		origFrameQryGridStore.removeAll();
		// queryorigFrameQry();
	}
	Ext.getCmp('原始报文查询').on('activate', function() {
		if (typeof(staticConsNo) != 'undefined'
				&& typeof(staticConsName) != 'undefined'
				&& typeof(staticTmnlAssetAddr) != 'undefined') {
			Ext.getCmp("origFrameQryID").setValue(staticConsNo);
			Ext.getCmp("origFrameQryName").setValue(staticConsName);
			Ext.getCmp("origFrameQryAsset").setValue(staticTmnlAssetAddr);
			origFrameQryID = staticConsNo;
			tmnlAssetNostore.removeAll();
			origFrameQrycombo.clearValue();
			getOrigFrameQryAsset();
			origFrameQryGridStore.removeAll();
			// queryorigFrameQry();
		}
		// added by 姜炜超------------start
		// 该页面激活的时候，终端安装调用此模块
		if ((typeof(tmnlInstllDet_Flag) != 'undefined') && tmnlInstllDet_Flag) {
			tmnlInstllDet_Flag = false;
			if ((typeof(tmnlInstllDet_ConsNo) != 'undefined')
					&& !Ext.isEmpty(tmnlInstllDet_ConsNo)
					&& 'null' != tmnlInstllDet_ConsNo) {
				Ext.getCmp("origFrameQryID").setValue(tmnlInstllDet_ConsNo);
				Ext.getCmp("origFrameQryName").setValue(tmnlInstllDet_ConsName);
				origFrameQryID = tmnlInstllDet_ConsNo;
				tmnlAssetNostore.removeAll();
				origFrameQrycombo.clearValue();
				getOrigFrameQryAsset();
				origFrameQryGridStore.removeAll();
			}
		}
		if ((typeof(ppd_Frame_Flag) != 'undefined') && ppd_Frame_Flag) {
			ppd_Frame_Flag = false;
			if ((typeof(ppd_Frame_ConsNo) != 'undefined')
					&& !Ext.isEmpty(ppd_Frame_ConsNo)
					&& 'null' != ppd_Frame_ConsNo) {
				Ext.getCmp("origFrameQryID").setValue(ppd_Frame_ConsNo);
				Ext.getCmp("origFrameQryName").setValue(ppd_Frame_ConsName);
				origFrameQryID = ppd_Frame_ConsNo;
				tmnlAssetNostore.removeAll();
				origFrameQrycombo.clearValue();
				getOrigFrameQryAsset();
				origFrameQryGridStore.removeAll();
			}
		}
			// added by 姜炜超 ------------end
	});

	// added by 姜炜超------------start
	if ((typeof(tmnlInstllDet_Flag) != 'undefined') && tmnlInstllDet_Flag) {
		tmnlInstllDet_Flag = false;
		if ((typeof(tmnlInstllDet_ConsNo) != 'undefined')
				&& !Ext.isEmpty(tmnlInstllDet_ConsNo)
				&& 'null' != tmnlInstllDet_ConsNo) {
			Ext.getCmp("origFrameQryID").setValue(tmnlInstllDet_ConsNo);
			Ext.getCmp("origFrameQryName").setValue(tmnlInstllDet_ConsName);
			origFrameQryID = tmnlInstllDet_ConsNo;
			tmnlAssetNostore.removeAll();
			origFrameQrycombo.clearValue();
			getOrigFrameQryAsset();
			origFrameQryGridStore.removeAll();
		}
	}
	if ((typeof(ppd_Frame_Flag) != 'undefined') && ppd_Frame_Flag) {
		ppd_Frame_Flag = false;
		if ((typeof(ppd_Frame_ConsNo) != 'undefined')
				&& !Ext.isEmpty(ppd_Frame_ConsNo) && 'null' != ppd_Frame_ConsNo) {
			Ext.getCmp("origFrameQryID").setValue(ppd_Frame_ConsNo);
			Ext.getCmp("origFrameQryName").setValue(ppd_Frame_ConsName);
			origFrameQryID = ppd_Frame_ConsNo;
			tmnlAssetNostore.removeAll();
			origFrameQrycombo.clearValue();
			getOrigFrameQryAsset();
			origFrameQryGridStore.removeAll();
		}
	}
		// added by 姜炜超 ------------end
});