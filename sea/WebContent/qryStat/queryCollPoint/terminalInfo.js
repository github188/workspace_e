function getrcpMessage() {
	if (consNoforCCust != '') {
		tiStore.baseParams = {
			consNo : consNoforCCust
		};
		tiStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		Ext.Ajax.request({
					url : 'qrystat/rcpAction!queryRCP.action',
					params : {
						consNo : consNoforCCust
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						tipanel2.getForm().setValues(result.rcpList[0]);
						tipanel3x.getForm()
								.setValues(result.rcpCommParaList[0]);
					}
				});
	}
};
Ext.override(Ext.Panel, {
			border : false
		});

// 采集点panel
var tipanel2 = new Ext.FormPanel({
	// title : "采集点",
	height : 60,
	layout:'fit',
	plain : true,
	items : [{
				// style : "padding:5px",
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				items : [{
							columnWidth : .5,// ----------------------
							layout : "form",
							labelWidth : 60,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "采集点名称",
										name : 'name',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 275
									}, {
										fieldLabel : "采集点地址",
										name : 'cpAddr',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 275
									}]
						}, {
							columnWidth : .25,// -------------------
							layout : "form",
							labelStyle : "text-align:right;width:80;",
							labelWidth : 60,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "采集点类型",
										name : 'cpTypeCode',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "GPS经度",
										name : 'gpsLongitude',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 80
									}]
						}, {
							columnWidth : .25,// ------------
							layout : "form",
							labelWidth : 60,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "状态",
										name : 'cpStatus',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "GPS维度",
										name : 'gpsLatitude',
										labelStyle : "text-align:right;width:60;",
										labelSeparator : "",
										width : 80
									}]
						}]
			}]
});

// 采集点通讯参数中的fieldset-------------------------------------------------------------------------------------
var tipanel32 = ({
	style : "padding:5px 10px 0px 10px",
	border : false,
	items : {
		border : false,
		xtype : 'fieldset',
		title : '网络通信',
		border : true,
		// autoHeight : true,
		items : [{
					baseCls : "x-plain",
					// style : "padding:5px",
					// xtype:'panel',
					layout : 'column',
					items : [{
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
									fieldLabel : "主用IP地址",
									name : 'masterIp',
									border : true,
									labelSeparator : "",
									width : 100
										// height:20
									}, {
									fieldLabel : "备用IP地址",
									name : 'spareIpAddr',
									border : true,
									labelSeparator : "",
									width : 100
										// height:20
									}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "主用端口",
											name : 'masterPort',
											border : true,
											labelSeparator : "",
											width : 80
										}, {
											fieldLabel : "备用端口",
											name : 'sparePort',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "网关IP地址",
											name : 'gatewayIp',
											border : true,
											labelSeparator : "",
											width : 100
										}, {
											fieldLabel : "代理IP地址",
											name : 'proxyIpAddr',
											border : true,
											labelSeparator : "",
											width : 100
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "网关端口",
											name : 'gatewayPort',
											border : true,
											labelSeparator : "",
											width : 80
										}, {
											fieldLabel : "代理端口",
											name : 'proxyPort',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}]

				}]
	}
});
// 采集点通讯参数中的fieldset-------------------------------------------------------------------------------------
var tipanel33 = ({
	style : "padding:0px 10px 0px 10px",
	border : false,
	items : {
		border : false,
		xtype : 'fieldset',
		title : 'GPRS通信',
		border : true,
		autoHeight : true,
		items : [{
					baseCls : "x-plain",
					layout : 'column',
					items : [{
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "GPRS号码",
											name : 'gprsCode',
											border : true,
											labelSeparator : "",
											width : 100
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "短信号码",
											name : 'smsNo',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "心跳周期",
											name : 'heartbeatCycle',
											border : true,
											labelSeparator : "",
											width : 100
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "启用日期",
											name : 'startDate',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}]

				}]
	}
});

// 采集点通讯参数中的fieldset-------------------------------------------------------------------------------------
var tipanel34 = ({
	border : false,
	style : "padding:0px 10px 0px 10px",
	items : {
		border : false,
		xtype : 'fieldset',
		title : '230M通信',
		border : true,
		autoHeight : true,
		items : [{
					baseCls : "x-plain",
					layout : 'column',
					items : [{
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "信道编号",
											name : 'channelNo',
											border : true,
											labelSeparator : "",
											width : 100
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "RTS开/关",
											name : 'onAndOff',
											// name:'rtsOff',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "传输延时",
											name : 'transmitDelay',
											border : true,
											labelSeparator : "",
											width : 100
										}]
							}, {
								columnWidth : .25,
								border : false,
								labelWidth : 60,
								labelAlign : 'right',
								layout : 'form',
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "响应超时",
											name : 'respTimeout',
											border : true,
											labelSeparator : "",
											width : 80
										}]
							}]

				}]
	}
});

// 采集点通讯参数----------------------------------------------------------------
var tipanel3x = new Ext.FormPanel({
			title : "采集点通讯参数",
			region : "north",
			collapsible : true,
			height : 280,
			border : false,
			items : [tipanel32, tipanel33, tipanel34]
		});

// 采集对象列表的gridpanel-----------------------------------------------------------------------------------
var tiStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/rcpAction!queryRCollObj.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'rCollObjList',
						totalProperty : 'totalCount'
					}, [{
								name : 'cpNo'
							}, {
								name : 'collPort'
							}, {
								name : 'assetNo'
							}, {
								name : 'ctRatio'
							}, {
								name : 'ptRatio'
							}, {
								name : 'tFactor'
							}, {
								name : 'meterConst'
							}, {
								name : 'portNo'
							}, {
								name : 'pulseAttr'
							}])
		});
var tipanelgrid4 = new Ext.grid.GridPanel({
			title : '采集对象列表',
			region : "center",
			viewConfig : {
				forceFit : false
			},
			bbar : new Ext.ux.MyToolbar({
						store : tiStore
					}),
			autoScroll : true,
			enableColumnMove : false,// 设置不可表头不可拖动
			// collapsible : true,
			colModel : new Ext.grid.ColumnModel([{
						header : "采集点编号",
						name : 'cpNo',
						menuDisabled : true,
						align : "center"
					},// 使下拉框消失
					{
						header : "采集端口",
						name : 'collPort',
						menuDisabled : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "电能表资产号",
						name : 'assetNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "CT变比值",
						name : 'ctRatio',
						menuDisabled : true,
						align : "center"
					}, {
						header : "PT变比值",
						name : 'ptRatio',
						menuDisabled : true,
						align : "center"
					}, {
						header : "综合倍率",
						name : 'tFactor',
						menuDisabled : true,
						align : "center"
					}, {
						header : "表常数",
						name : 'meterConst',
						menuDisabled : true,
						align : "center"
					}, {
						header : "端口号",
						name : 'portNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "脉冲属性",
						name : 'pulseAttr',
						menuDisabled : true,
						align : "center"
					}]),
			ds : tiStore
		});

// border布局的嵌套panel
var terminalInfopannelbottom = new Ext.Panel({
			layout : 'border',
			xtype : "panel",
			height : 460,
			// autoHeight:true,
			border : false,
			items : [tipanel3x, tipanelgrid4]
		});

// 设置顶层的panel
var terminalInfopannel = new Ext.Panel({
			title : '采集点信息',
			items : [tipanel2, terminalInfopannelbottom]
		});
terminalInfopannel.render("terminalInfo");
getrcpMessage();