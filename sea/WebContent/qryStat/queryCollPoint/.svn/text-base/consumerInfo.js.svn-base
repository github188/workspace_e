consInfoAddr = '';
consIdforTerminal = '';// custId
consNoforCCust = '';
consIdNew = '';
var grtConsType;
var alertWindowParam = '';
var orgNoforalertWindow = '';
var staticSimNo = '';
var consumerInfoRadioValue = '1';
var dmeterStoreresult;
var sendDQType = 'usr';
var treels = new LeftTreeListener({
			modelName : '客户综合查询',
			processClick : function(p, node, e) {
				treeclick(node, e);
			},
			processUserGridSelect : function(cm, row, record) {
				// if (consIdNew.toString().substring(0, 1) == 'T') {
				// consIdNew = consIdNew.substring(1, 20);
				// }
				grtConsType = record.get('consType');
				consNoforCCust = record.get('consNo');
				if (grtConsType == 2) {
					sendDQType = 'usr';
					sendDQType = sendDQType + '2';
					consIdNew = record.get('consId');
				} else {
					sendDQType = 'usr';
					sendDQType = sendDQType + '1';
					consIdNew = record.get('consId');
				}
				// consIdforTerminal = record.get('custId');
				// orgNoforalertWindow = record.get('orgNo');
				// consInfoAddr = record.get('terminalId');
				Ext.getCmp("consumerInfoID").setValue(consNoforCCust);
				getconsumerInfoAsset();
				Ext.getCmp("consumerInfoAsset2").setValue('显示电能表资产号');
				tmnlAssetNostore2.removeAll();
				mapanel1.getForm().reset();
				ctfpanel.getForm().reset();
				tcpanelcenter1.getForm().reset();
				tipanel41.getForm().reset();
				dmeterStore.removeAll();
				ctpanelstore.removeAll();
				ctpanelstore2.removeAll();
				TmnlrunStore.removeAll();
				TmnlrunStore2.removeAll();

			}
		});
// 点击左边树事件
function treeclick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	var name = node.text;
	if (node.isLeaf()) {
		// consIdforTerminal = obj.custId;
		sendTypeFlagSend = node.attributes.attributes.consType;
		consNoforCCust = obj.consNo;
		if (consNoforCCust.toString().substring(0, 1) == 'T') {
			sendDQType = 'usr';
			sendDQType = sendDQType + '2';
		} else {
			sendDQType = 'usr';
			sendDQType = sendDQType + '1';
			consIdNew = obj.consId;
		}
		consInfoAddr = obj.terminalId;
		Ext.getCmp("consumerInfoID").setValue(consNoforCCust);
		getconsumerInfoAsset();
		// getconsumerInfoTmnl();
		Ext.getCmp("consumerInfoAsset2").setValue('显示电能表资产号');
		tmnlAssetNostore2.removeAll();
		mapanel1.getForm().reset();
		ctfpanel.getForm().reset();
		tcpanelcenter1.getForm().reset();
		tipanel41.getForm().reset();
		dmeterStore.removeAll();
		ctpanelstore.removeAll();
		ctpanelstore2.removeAll();
		TmnlrunStore.removeAll();
		TmnlrunStore2.removeAll();
	} else {
		return true;
	}
};
// 查询变压器档案
function getGtranMessage() {
	Ext.Ajax.request({
				url : 'qrystat/ccustAction!queryGtran.action',
				params : {
					CONS_ID : consIdNew,
					consType : grtConsType
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					Gridresult = Ext.decode(response.responseText);
					ctfpanel.getForm().setValues(Gridresult.gtranList[0]);
					ctpanelstore.loadData(result);
					ctpanelstore2.loadData(result);
				}
			});
};
// 查询电能表资产方法
function getMeterMessage() {
	var consAdr = Ext.getCmp("consumerInfoAsset").getValue();
	dmeterStore.baseParams = {
		consNo : consAdr
	};
	dmeterStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
	// mapanel1.getForm().setValues(gtranGridStore.DMeterList[0]);
	Ext.Ajax.request({
				url : 'qrystat/ccustAction!queryDMeter.action',
				params : {
					consNo : consAdr
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					dmeterStoreresult = result;
					dmeterStore.loadData(result);
					mapanel1.getForm().setValues(result.DMeterList[0]);
				}
			});
};
// 公用查询函数
function getConMessage() {
	Ext.Ajax.request({
				url : 'qrystat/consumerInfoAction!queryConsumerInfo.action',
				params : {
					CONS_NO : consNoforCCust,
					TG_ID : ''
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					TmnlrunStore.loadData(result);
					TmnlrunStore2.loadData(result);
					tcpanelcenter1.getForm()
							.setValues(result.consumerInfoList[0]);
					tipanel41.getForm().setValues(result.cconsRtmnlList[0]);
				}
			});
};

// 查询客户对应的终端资产号函数
function getconsumerInfoAsset() {
	Ext.Ajax.request({
				url : 'baseapp/origFrameQry!queryOrigFrameQryAsset.action',
				params : {
					origFrameQryID : consNoforCCust
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					tmnlAssetNostore.loadData(result);
					if (!Ext.isEmpty(tmnlAssetNostore)
							&& 0 < tmnlAssetNostore.getCount()) {
						var terminalAddr = tmnlAssetNostore.getAt(0)
								.get('terminalAddr');
						consumerInfocombo.setValue(terminalAddr);
					}
				}
			});
};
// ====consInfoSub================================================================================================================
// 终端档案的combo
var TmnlrunStore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			// proxy : new Ext.data.MemoryProxy(data),
			// baseParams : {statusCode : ''},
			reader : new Ext.data.JsonReader({
						root : 'cconsRtmnlList',
						idProperty : "cisAssetNo"
					}, [{
								name : 'terminalAddr'
							}, {
								name : 'runDate'
							}, {
								name : 'sendUpMode'
							}, {
								name : 'prioPsMode'
							}, {
								name : 'simNo'
							}, {
								name : 'factoryName'
							}, {
								name : 'protocolName'
							}, {
								name : 'commMode'
							}, {
								name : 'statusName'
							}, {
								name : 'tmnlType'
							}, {
								name : 'collMode'
							}, {
								name : 'freezeMode'
							}, {
								name : 'gateAttrFlag'
							}, {
								name : 'attachMeterFlag'
							}, {
								name : 'harmonicDevFlag'
							}, {
								name : 'acSamplingFlag'
							}, {
								name : 'eliminateFlag'
							}, {
								name : 'psEnsureFlag'
							}])
		});

// 用户变压器combo
var ctpanelstore2 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			reader : new Ext.data.JsonReader({
						root : 'gtranList',
						idProperty : "equipId"
					}, [{
								name : 'orgNo'
							}, {
								name : 'tranName'
							}, {
								name : 'plateCap'
							}, {
								name : 'typeCode'
							}, {
								name : 'instAddr'
							}, {
								name : 'instDate'
							}, {
								name : 'chgRemark'
							}, {
								name : 'runStatusCode'
							}, {
								name : 'pubPrivFlag'
							}, {
								name : 'madeDate'
							}, {
								name : 'modelNo'
							}, {
								name : 'factoryName'
							}, {
								name : 'protectMode'
							}, {
								name : 'frstsideVoltCode'
							}, {
								name : 'sndsideVoltCode'
							}, {
								name : 'prCode'
							}, {
								name : 'equipId'
							}])
		});

var ctpanelstore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(), // 数据源
			fields : ['equipId'],
			reader : new Ext.data.JsonReader({
						root : 'gtranList'
					}, ['equipId'])
		});
var ctpanelcombo = new Ext.form.ComboBox({
			name : 'equipId',
			id : 'equipId',
			editable : false,
			store : ctpanelstore,
			displayField : 'equipId',
			labelStyle : "text-align:right;width:80px;",
			valueField : 'equipId',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '设备标识',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			width : 199,
			forceSelection : true
		});
ctpanelcombo.on('select', function(combox) {
			var value = combox.getValue();
			var record = ctpanelstore2.getById(value);
			ctfpanel.getForm().loadRecord(record);
		});
// 显示终端资产编号的combo
var TmnlrunStore2 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(), // 数据源
			fields : ['cisAssetNo'],
			reader : new Ext.data.JsonReader({
						root : 'cconsRtmnlList'
					}, ['cisAssetNo'])
		});
var tmnlAssetNocombo = new Ext.form.ComboBox({
			name : 'cisAssetNo',
			id : 'cisAssetNo',
			editable : false,
			store : TmnlrunStore2,
			displayField : 'cisAssetNo',
			labelStyle : "text-align:right;width:80px;",
			valueField : 'cisAssetNo',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '终端资产编号',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			width : 100,
			forceSelection : true
		});
tmnlAssetNocombo.on('select', function(combox) {
			var value = combox.getValue();
			var record = TmnlrunStore.getById(value);
			tipanel41.getForm().loadRecord(record);
		});
var capUnit = new Ext.form.Label( {
	text : '(kVA)',
	labelSeparator : '',
	style : 'font-size: 9pt',
	readOnly : true
//	width : 14
});
// 用户变压器信息的第一个panel
var ctpanel1 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			style : "padding-top:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "管理单位",
									name : 'orgNo',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [ctpanelcombo]
					}, {
						layout : "column",
						labelSeparator : '',
						columnWidth:0.3,
						border : false,						
						items:[{
//							labelWidth:50,
//							columnWidth : .7,
						    layout : "form",						    
						    border:false,
						    labelWidth:50,
						    items:[{ 
//						    	labelStyle : "text-align:right;width:50;",
						    	xtype : "textfield",
//						    	baseCls : "x-plain",
							    fieldLabel : "铭牌容量",
							    name : 'plateCap',
							    readOnly : true,							    
							    labelSeparator : ""	,
							   
							    width:110
						    }]						   
//						    width : 124
					 },{
//						columnWidth : .2,
						layout : "form",
						border : false,
						bodyStyle : 'padding:2px 2px 2px 2px',
						items:[capUnit]
					}]
					}]
		});

// 用户变压器信息的第二个panel
var ctpanel2 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "设备类型",
									name : 'typeCode',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "安装地址",
									name : 'instAddr',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 199
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "安装日期",
									name : 'instDate',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 124
								}]
					}]
		});

// 用户变压器信息的第三个panel
var ctpanel3 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "变更说明",
									name : 'chgRemark',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						columnWidth : .2,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "运行状态",
									name : 'runStatusCode',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 55
								}]
					}, {
						columnWidth : .2,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "公/专变标志",
									name : 'pubPrivFlag',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 55
								}]
					},

					{
						columnWidth : .3,
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "出厂日期",
									name : 'madeDate',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 125
								}]
					}]
		});
// 用户变压器信息的第四个panel
var ctpanel4 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "变损算法",
									name : 'tsAlgFlag',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						columnWidth : .4,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "厂家名称",
									name : 'factoryName',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 199,
									value:""
								}]
					}, {
						columnWidth : .3,
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "出厂编号",
									name : 'madeNo',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 125
								}]
					}]
		});

// 用户变压器信息的第五个panel
var ctpanel5 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			// style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "K值",
									name : 'kValue',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 120
								}]
					}, {
						columnWidth : .2,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "一次侧电压",
									name : 'frstsideVoltCode',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 70
								}]
					}, {
						columnWidth : .2,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "二次侧电压",
									name : 'sndsideVoltCode',
									readOnly : true,
									labelStyle : "text-align:right;width:80;",
									labelSeparator : "",
									width : 70
								}]
					},

					{
						columnWidth : .3,
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "产权",
									name : 'prCode',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 125
								}]
					}]
		});

// 变压器档案的grid-----------------------------------------------------------------------------------
var dmeterStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/ccustAction!queryDMeter.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'DMeterList',
						totalProperty : 'totalCount'
					}, [{
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'typeCode'
							}, {
								name : 'assetNo'
							}, {
								name : 'mpNo'
							}, {
								name : 'mpName'
							}, {
								name : 'tFactor'
							}])
		});

var ctlinkmanGrid = new Ext.grid.GridPanel({
			height : 200,
			stripeRows : true,
			autoScroll : true,
			enableColumnMove : false,// 设置不可表头不可拖动
			colModel : new Ext.grid.ColumnModel([{
						header : "用户编号",
						dataIndex : 'consNo',
						menuDisabled : true,
						align : "center"
					},// 使下拉框消失
					{
						header : "用户名称",
						dataIndex : 'consName',
						menuDisabled : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "电表资产号",
						dataIndex : 'assetNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量编号",
						dataIndex : 'mpNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量点名称",
						dataIndex : 'mpName',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量点类型",
						dataIndex : 'typeCode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "综合倍率",
						dataIndex : 'tFactor',
						menuDisabled : true,
						align : "center"
					}]),
			ds : dmeterStore,
			bbar : new Ext.ux.MyToolbar({
						store : dmeterStore
					})
		});

ctlinkmanGrid.addListener('rowclick', rowclick);

function rowclick(grid, rowIndex, e) {
	if (rowIndex >= 0) {
		var row = rowIndex;
		mapanel1.getForm().reset();
		mapanel1.getForm().setValues(dmeterStoreresult.DMeterList[row]);
		// alert(row + '行选择事件');
	}
};
// --------------------------
var ctfpanel = new Ext.FormPanel({
			title : "变压器档案",
			items : [ctpanel1, ctpanel2, ctpanel4, ctpanel3, ctpanel5]
		})

// 嵌入客户综合查询原来页面
var tcpanelcenter1 = new Ext.FormPanel({
			title : "用户/变电站档案",
			height : 140,
			layout : "column",
			// region:'north',
			// baseCls : "x-plain",
			items : [{
						columnWidth : .33,
						layout : "form",
						labelWidth : 60,
						defaultType : "textfield",
						baseCls : "x-plain",
						style : "padding-top:5px",
						items : [{
									fieldLabel : "用户编号",
									name : 'consNo',
									readOnly : true,
									id : 'consNo',
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "用户名称",
									name : 'consName',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "用户分类",
									name : 'consTypeName',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "行业分类",
									name : 'tradeName',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}]
					},
					// 分块
					{
						columnWidth : .33,
						layout : "form",
						labelWidth : 60,
						defaultType : "textfield",
						baseCls : "x-plain",
						style : "padding-top:5px",
						items : [{
									fieldLabel : "用电地址",
									name : 'elecAddr',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "用电类别",
									name : 'elecType',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "合同容量",
									name : 'contractCap',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "运行容量",
									name : 'runCap',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 200
								}]
					}, {
						columnWidth : .33,
						layout : "form",
						labelWidth : 60,
						defaultType : "textfield",
						baseCls : "x-plain",
						style : "padding-top:5px",
						items : [{
									fieldLabel : "所属供电所",
									name : 'orgName',
									readOnly : true,
									labelStyle : "text-align:right;width:60;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "所属变电站",
									name : 'subsName',
									readOnly : true,
									labelStyle : "text-align:right;width:60;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "主供线路",
									name : 'lineName',
									readOnly : true,
									labelStyle : "text-align:right;width:60;",
									labelSeparator : "",
									width : 200
								}, {
									fieldLabel : "供电电压",
									name : 'volt',
									readOnly : true,
									labelStyle : "text-align:right;width:60;",
									labelSeparator : "",
									width : 200
								}]
					}]
		});

// 运行终端----------------------------------------------------------------
var tipanel41 = new Ext.FormPanel({
	title : "终端档案",
	plain : true,
	border : false,
	// region:'south',
	height : 165,
	items : [{
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				items : [{
							columnWidth : .25,// ----------------------
							layout : "form",
							labelWidth : 80,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [tmnlAssetNocombo, {
										fieldLabel : "终端地址码",
										name : 'terminalAddr',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "终端投运日期",
										name : 'runDate',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "任务上送方式",
										name : 'sendUpMode',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "优先供电方式",
										name : 'psModeName',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}]
						}, {
							columnWidth : .25,// ----------------------
							layout : "form",
							labelWidth : 80,
							labelStyle : "text-align:right;width:80px;",
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "SIM卡号",
										name : 'simNo',
										readOnly : true,
										id : 'consumer_sim_no',
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "生产厂家",
										name : 'factoryName',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "采用通讯规约",
										name : 'protocolName',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "采集点编号",
										name : 'cpNo',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "终端运行状态",
										name : 'statusName',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}]
						}, {
							columnWidth : .25,// -------------------
							layout : "form",
							labelStyle : "text-align:right;width:80px;",
							labelWidth : 80,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "终端型号",
										name : 'modeName',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "终端类型",
										name : 'tmnlType',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "采集方式",
										name : 'coll',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "冻结周期",
										name : 'freezeCycleNum',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}, {
										fieldLabel : "门接点属性",
										name : 'gateAttrFlag',
										readOnly : true,
										labelStyle : "text-align:right;width:80px;",
										labelSeparator : "",
										width : 100
									}]
						}, {
							columnWidth : .25,
							baseCls : "x-plain",
							layout : 'form',
							border : false,
							style : "padding-left:10px",
							xtype : 'checkboxgroup',
							items : [{
										layout : 'form',

										items : [{
													name : 'attachMeterFlag',
													boxLabel : '是否附属于电能表',
													inputValue : 1
												}, {
													name : 'harmonicDevFlag',
													boxLabel : '是否接谐波装置',
													inputValue : 1
												}, {

													name : 'acSamplingFlag',
													boxLabel : '是否交流采样',
													inputValue : 1
												}, {

													name : 'eliminateFlag',
													boxLabel : '是否剔除',
													inputValue : 1
												}, {
													name : 'psEnsureFlag',
													boxLabel : '是否保电',
													inputValue : 1
												}]
									}]
						}]
			}]
});
// 电能表档案的grid和panel
var mapanel1 = new Ext.FormPanel({
	title : '电能表档案',
	plain : true,
	border : false,
	height : 165,
	// region:'center',
	items : [{
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				items : [{
							columnWidth : .25,// ----------------------
							layout : "form",
							labelWidth : 80,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "资产编号",
										name : 'assetNo',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电能表类别",
										name : 'sortCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电压",
										name : 'voltCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "生产厂家",
										name : '',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "脉冲常数",
										name : 'pulseConstantCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}]
						}, {
							columnWidth : .25,// ----------------------
							layout : "form",
							labelWidth : 80,
							labelStyle : "text-align:right;width:80px;",
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "条形码",
										name : 'barCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电能表类型",
										name : 'typeCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电能表位数",
										name : 'meterDigit',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "出厂日期",
										name : 'madeDate',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "复费率表",
										name : 'multiRateFlag',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}]
						}, {
							columnWidth : .25,// -------------------
							layout : "form",
							labelStyle : "text-align:right;width:80px;",
							labelWidth : 80,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "生产批次",
										name : 'lotNo',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电能表的型号",
										name : 'modelCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "过载倍数",
										name : 'overloadFactor',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "使用用途",
										name : 'meterUsage',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "通讯接口",
										name : 'ci',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}]
						}, {
							columnWidth : .25,// -------------------
							layout : "form",
							labelStyle : "text-align:right;width:80px;",
							labelWidth : 80,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "是否预付费",
										name : 'repayFlag',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "自身倍率",
										name : 'selfFactor',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "电能表常数",
										name : 'constCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "接入方式",
										name : 'wiringMode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "是否双向计量",
										name : '',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}]
						}]
			}]
});
var consumerInfoConsPanel = new Ext.Panel({
			// autoHeight : false,
			// border : false,
			// layout : 'border',
			items : [tcpanelcenter1, tipanel41, ctfpanel, mapanel1,
					ctlinkmanGrid]
		});

// 终端事件
var consAlertEventColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(), {
			header : '数据ID',
			dataIndex : 'dataId',
			hidden : true
		}, {
			header : '供电单位',
			dataIndex : 'orgName',
			width : 160
		}, {
			header : '供电单位编码',
			dataIndex : 'orgNo',
			width : 160,
			hidden : true
		}, {
			header : '用户编号',
			dataIndex : 'consNo'
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			hidden : true
		}, {
			header : '事件名称',
			dataIndex : 'eventName',
			width : 160
		}, {
			header : '事件时间',
			dataIndex : 'eventTime',
			width : 160,
			format : 'Y-m-d H:i:s',
			xtype : 'datecolumn'
		}, {
			header : '事件来源',
			dataIndex : 'fromType',
			renderer : function(val, cellMeta, record) {
				var disp = '';
				switch (val) {
					case 0 :
						disp = '终端';
					case 1 :
						disp = '测量点';
						break;
					case 2 :
						disp = '总加组';
						break;
					case 3 :
						disp = '电能量差动';
						break;
					case 4 :
						disp = '直流模拟端口';
						break;
					default :
						disp = '未知';

				}
				return disp + ' [' + record.get('fromNo') + ']';
			}
		}, {
			header : '严重等级',
			dataIndex : 'eventLevel',
			renderer : function(val) {
				var rtn = '一般';
				if (val == '03')
					rtn = '严重';
				else if (val == '02')
					rtn = '次要';
				else
					rtn = '一般';
				return rtn;

			}
		}, {
			header : '起止标志',
			dataIndex : 'isStart',
			hidden : true,
			renderer : function(val) {
				var rtn = '其它';
				if (val == 1)
					rtn = '起始';
				else if (val == 0)
					rtn = '恢复';
				else
					rtn = '其它';
				return rtn;

			}

		}, {
			header : '接收时间',
			dataIndex : 'receiveTime',
			format : 'Y-m-d H:i:s',
			xtype : 'datecolumn',
			hidden : true

		}, {
			header : '来源编号',
			dataIndex : 'fromNo',
			hidden : true
		}, {
			header : '存储类型',
			dataIndex : 'storeType',
			hidden : true
		}, {
			header : '事件类型',
			dataIndex : 'eventType',
			hidden : true
		}, {
			header : '存储方式',
			dataIndex : 'storeMode',
			hidden : true
		}, {
			header : '规约编码',
			dataIndex : 'protocolCode',
			hidden : true
		}, {
			header : '终端逻辑地址',
			dataIndex : 'tmnlAssetNo',
			hidden : true
		}, {
			header : '终端编号',
			dataIndex : 'assetNo',
			hidden : true
		}, {
			header : '终端类型',
			dataIndex : 'mpType',
			hidden : true
		}, {
			header : '数据来源',
			dataIndex : 'dataSrc',
			hidden : true
		}, {
			header : '状态',
			dataIndex : 'flowStatusCode',
			renderer : function(val) {
				if (val == "00") {
					return "新异常";
				} else {
					return "";
				}
			}
		}
// ,{
// header :'相关操作',
// renderer : function(){
// var html = '<a href="javascript:f_attention();">关注 </a>&nbsp;&nbsp;&nbsp;<a
// href="javascript:f_dealWith();">处理</a>';
// return html;
// }
// }
]);

var consAlertEventStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './sysman/alertevent!alertEvent.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'alertEventList',
						totalProperty : 'totalCount'
					}, [{
								name : 'rd'
							}, {
								name : 'dataId'
							}, {
								name : 'eventNo'
							}, {
								name : 'isStart'
							}, {
								name : 'eventTime',
								type : 'date',
								dateFormat : 'Y-m-dTH:i:s'
							}, {
								name : 'receiveTime',
								type : 'date',
								dateFormat : 'Y-m-dTH:i:s'
							}, {
								name : 'fromType'
							}, {
								name : 'fromNo'
							}, {
								name : 'storeType'
							}, {
								name : 'eventName'
							}, {
								name : 'eventType'
							}, {
								name : 'eventLevel'
							}, {
								name : 'storeMode'
							}, {
								name : 'protocolCode'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'assetNo'
							}, {
								name : 'orgName'
							}, {
								name : 'orgNo'
							}, {
								name : 'mpType'
							}, {
								name : 'dataSrc'
							}, {
								name : 'flowStatusCode'
							}])
		});

var consAmainGrid = new Ext.grid.GridPanel({
			title : '报警事件列表',
			border : false,
			region : 'center',
			cm : consAlertEventColumnModel,
			ds : consAlertEventStore,
			bbar : new Ext.ux.MyToolbar({
						store : consAlertEventStore,
						enableExpAll : true
					}),
			sm : new Ext.grid.RowSelectionModel({
						singleSelect : true
					})
		});

// ==========================================================consinfoSub
// 终端地址start-------------------------------------------------------------------------------------------------------------
var tmnlAssetNostore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			// fields : ['origFrameQryAsset'],
			reader : new Ext.data.JsonReader({
						root : 'origFrameQryAssetList',
						idProperty : 'terminalAddr'
					}, ['terminalAddr', 'protocolCode'])
		});
var consumerInfocombo = new Ext.form.ComboBox({
			name : 'consumerInfoAsset',
			id : 'consumerInfoAsset',
			editable : false,
			store : tmnlAssetNostore,
			displayField : 'terminalAddr',
			// labelStyle : "text-align:right;width:80px;",
			valueField : 'terminalAddr',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '终端地址',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--请选择--',
			blankText : '--请选择--',
			selectOnFocus : true,
			width : 110,
			forceSelection : true
		});
consumerInfocombo.on('select', function(combox) {
			var value = combox.getValue();
			var record = tmnlAssetNostore.getById(value);
			getconsumerInfoTmnl();
		});
// consumerInfocombo.on('change', function(thisValue,newValue,oldValue){
// alert(newValue);
// alert(oldValue);
// if(newValue != oldValue){
// getconsumerInfoTmnl();
// }
// });
// 终端地址end-------------------------------------------------------------------------------------------------------------
// 查询客户对应的表计资产号start-----------------------------------------------------------------------------------------------
function getconsumerInfoTmnl() {
	var TMNL_ASSET_NO = Ext.getCmp("consumerInfoAsset").getValue();
	Ext.Ajax.request({
		url : 'baseapp/origFrameQry!queryOrigFrameQryTmnl.action',
		params : {
			terminalAddr : TMNL_ASSET_NO
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			tmnlAssetNostore2.loadData(result);
			// if(!Ext.isEmpty(tmnlAssetNostore2) && 0 <
			// tmnlAssetNostore2.getCount()){
			var terminalAssetNo = tmnlAssetNostore2.getAt(0).get('tmnlAssetNo');
			consumerInfocombo2.setValue(terminalAssetNo);
		}
			// }
	});
};
var tmnlAssetNostore2 = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(),
			// fields : ['consumerInfoAsset'],
			reader : new Ext.data.JsonReader({
						root : 'origFrameQryAssetList2',
						idProperty : 'tmnlAssetNo'
					}, ['tmnlAssetNo'])
		});
var consumerInfocombo2 = new Ext.form.ComboBox({
			name : 'consumerInfoAsset2',
			id : 'consumerInfoAsset2',
			editable : false,
			// disabled :true,

			store : tmnlAssetNostore2,
			displayField : 'tmnlAssetNo',
			// labelStyle : "text-align:right;width:80px;",
			valueField : 'tmnlAssetNo',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '电能表',
			labelSeparator : '',
			// forceSelection : true,
			emptyText : '--全部--',
			blankText : '--全部--',
			selectOnFocus : true,
			width : 190,
			forceSelection : true
		});

// tmnlAssetNostore2.load();
// consumerInfocombo2.on('select', function(combox) {
// var value = combox.getValue();
// var record = tmnlAssetNostore2.getById(value);
// });
// 查询客户对应的表计资产号end-----------------------------------------------------------------------------------------------

var consdataPanel = new Ext.Panel({
			width : 320,
			baseCls : "x-plain",
			layout : "column",
			style : "padding-top:5px",
			border : false,
			style : {
				marginTop : '0px',
				marginBottom : '10px',
				marginLeft : '2px',
				marginRight : '10px'
			},
			items : [{
						columnWidth : .42,
						layout : 'form',
						border : false,
						labelAlign : 'right',
						labelWidth : 20,
						width : '120',
						items : [{
									xtype : "datefield",
									format : "Y-m-d",
									editable : false,
									border : false,
									id : 'consumerInfoDateStart',
									fieldLabel : "从",
									value : new Date().add(Date.DAY, -1)
								}]

					}, {
						columnWidth : .5,
						layout : 'form',
						border : false,
						labelAlign : 'right',
						labelWidth : 20,
						width : '120',
						items : [{
									xtype : "datefield",
									format : "Y-m-d",
									editable : false,
									id : 'consumerInfoDateEnd',
									fieldLabel : "到",
									border : false,
									value : new Date().add(Date.DAY, 0)
								}]
					}]
		});
// 加入：一次侧，二次侧
var consumerInfoRadio = new Ext.form.RadioGroup({
			plain : true,
			border : false,
			height : 35,
			layout : 'fit',
			columns : [100, 100],
			items : [{
						checked : true,
						style : "padding:5px",
						boxLabel : '一次侧',
						name : 'fav-color',
						inputValue : '1',
						id : 'consumerInfoRadio1',
						handler : function(e, checked) {
							if (checked) {
								consumerInfoRadioValue = '1';
							}
						}
					}, {
						boxLabel : '二次侧',
						style : "padding:5px",
						name : 'fav-color',
						inputValue : '2',
						id : 'consumerInfoRadio2',// 定义ID
						handler : function(e, checked) {
							if (checked) {
								consumerInfoRadioValue = '2';
							}
						}
					}]
		});
var consumerInfoPanel = new Ext.Panel({
	plain : true,
	border : false,
	region : 'north',
	// layout : 'fit',
	height : 70,
	items : [{
		baseCls : "x-plain",
		Width : 600,
		layout : "column",
		style : "padding:5px",
		border : false,
		items : [{
					columnWidth : .4,
					layout : "form",
					labelWidth : 40,
					// height : 35,
					defaultType : "textfield",
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [{
								fieldLabel : "户号<font color='red'>*</font>",
								id : 'consumerInfoID',
								readOnly : true,
								emptyText : '请选择用户（变电站）节点信息',
								labelSeparator : "",
								width : 210,
								validator : function(val) {
									if (Ext.isEmpty(val))
										return false;
									else
										return true;
								}
							}]
				}, {
					columnWidth : .25,
					layout : "form",
					labelWidth : 70,
					defaultType : "textfield",
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [consumerInfocombo]
				}, {
					columnWidth : .35,
					layout : "form",
					labelWidth : 70,
					defaultType : "textfield",
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [consumerInfocombo2]
				}, {
					columnWidth : .35,
					layout : "form",
					labelWidth : 40,
					defaultType : "datetimefield",
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [consdataPanel]
				}, {
					columnWidth : .35,
					layout : "form",
					labelWidth : 40,
					baseCls : "x-plain",
					labelAlign : 'right',
					items : [consumerInfoRadio]
				}, {
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
						text : "查询",
						width : 70,
						listeners : {
							"click" : function() {
								var nowAssetNo = Ext
										.getCmp("consumerInfoAsset2")
										.getValue();
								var CONS = Ext.getCmp("consumerInfoID");
								if (!CONS.isValid(true)) {
									CONS.markInvalid('不能为空');
									return true;
								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab2') {
									consumerInfoGridStore2.baseParams = {
										CONS_NO : consNoforCCust
									};
									consumerInfoGridStore2.load({
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});
								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab1') {
									assetNo = Ext.getCmp("consumerInfoAsset2")
											.getValue();
									getMeterMessage();
									getConMessage();
									getGtranMessage();
								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab4') {
									DateStart = Ext
											.getCmp("consumerInfoDateStart")
											.getValue().format('Y-m-d');
									DateEnd = Ext.getCmp("consumerInfoDateEnd")
											.getValue().format('Y-m-d');
									consumerInfoGridStore4.baseParams = {
										MR_SECT_NO : '',
										sendDataQueryFail : '成功',
										sendDataQueryFlag : 'everyday',
										sendDQOrgType : '',
										sendDQType : sendDQType,
										CONS_NO : consNoforCCust,
										addr : '',
										TG_ID : consIdNew,
										DateStart : DateStart,
										DateEnd : DateEnd
									};
									consumerInfoGridStore4.load({
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});

								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab5') {
									DateStart = Ext
											.getCmp("consumerInfoDateStart")
											.getValue().format('Y-m-d');
									DateEnd = Ext.getCmp("consumerInfoDateEnd")
											.getValue().format('Y-m-d');
									consumerInfoGridStore5.baseParams = {
										MR_SECT_NO : '',
										sendDataQueryFail : '成功',
										sendDataQueryFlag : 'everyday',
										sendDQOrgType : '',
										sendDQType : sendDQType,
										CONS_NO : consNoforCCust,
										addr : '',
										TG_ID : consIdNew,
										DateStart : DateStart,
										DateEnd : DateEnd
									};
									consumerInfoGridStore5.load({
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});

								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab3') {
									// Ext.getCmp("consumerInfoDateStart").setValue(new
									// Date().add(Date.DAY, -2));
									// Ext.getCmp("consumerInfoDateStart").setValue(new
									// Date().add(Date.DAY, -1));
									DateStart = Ext
											.getCmp("consumerInfoDateStart")
											.getValue().format('Y-m-d');
									DateEnd = Ext.getCmp("consumerInfoDateEnd")
											.getValue().format('Y-m-d');
									// mpDayPowerStore.baseParams['dataDateFrom']
									// =
									// mpDayEnergy.find("name","dataDateFrom")[0].getValue();
									// mpDayPowerStore.baseParams['dataDateTo']
									// =
									// mpDayEnergy.find("name","dataDateTo")[0].getValue();
									var CONS_NO_newMpDay = consNoforCCust;
									if (CONS_NO_newMpDay != "") {
										mpDayPowerStore.load({
											params : {
												assetNo : nowAssetNo,
												flagValue : consumerInfoRadioValue,
												CONS_NO : CONS_NO_newMpDay,
												DateStart : DateStart,
												DateEnd : DateEnd
											}
										});
									}
								} else if (consumerInfotab.getActiveTab()
										.getId() == 'consumerInfotab7') {
									consAlertEventStore.baseParams = {
										type : 'usr',
										nodeid : consNoforCCust,
										consType : '',
										eventType : '',
										orgType : '',
										terminalAddr : '',
										startDate : Ext
												.getCmp("consumerInfoDateStart")
												.getValue().format('Y-m-d'),
										endDate : Ext
												.getCmp("consumerInfoDateEnd")
												.getValue().format('Y-m-d')
										// endDate :
										// endTimeField.getValue().add(Date.DAY,
										// 1).format('Y-m-d')

									};
									consAlertEventStore.load({
												params : {
													start : 0,
													limit : DEFAULT_PAGE_SIZE
												}
											});

								}
							}
						}
					}]
				}, {
					columnWidth : .1,
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
								text : "图形展示",
								width : 70,
								listeners : {
									"click" : function() {
										// generalAnalyseShow();
										showChartWin();
									}
								}
							}]
				}]
	}]
});

// ------------------------------------------------------------------------------------------
var consumerInfoGridStore2 = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/consumerInfoAction!queryCmeterInfo.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'cmeterInfoList',
						totalProperty : 'totalCount'
					}, [{
								name : 'cpNo'
							}, {
								name : 'name'
							}, {
								name : 'cpTypeCode'
							}, {
								name : 'statusCode'
							}, {
								name : 'cpAddr'
							}, {
								name : 'gpsLongitude'
							}, {
								name : 'gpsLatitude'
							}, {
								name : 'rtu'
							}])
		});
var groupCM2 = new Ext.grid.ColumnModel([{
			header : '采集点编号 ',
			width : 100,
			dataIndex : 'cpNo',
			sortable : true,
			align : 'center'
		}, {
			header : '终端地址',
			width : 100,
			dataIndex : 'rtu',
			sortable : true,
			align : 'center'
		}, {
			header : '采集点名称',
			width : 100,
			dataIndex : 'name',
			sortable : true,
			align : 'center'
		}, {
			header : '采集点类型 ',
			width : 100,
			dataIndex : 'cpTypeCode',
			sortable : true,
			align : 'center'
		}, {
			header : '状态',
			width : 100,
			dataIndex : 'statusCode',
			sortable : true,
			align : 'center'
		}, {
			header : '采集点地址',
			width : 180,
			dataIndex : 'cpAddr',
			sortable : true,
			align : 'center'
		}, {
			header : 'GPS经度',
			width : 100,
			dataIndex : 'gpsLongitude',
			sortable : true,
			align : 'center'
		}, {
			header : 'GPS纬度',
			width : 100,
			dataIndex : 'gpsLatitude',
			sortable : true,
			align : 'center'
		}]);

var consumerInfoGrid2 = new Ext.grid.GridPanel({
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : groupCM2,
			ds : consumerInfoGridStore2,
			bbar : new Ext.ux.MyToolbar({
						store : consumerInfoGridStore2,
						enableExpAll : true
					})
		});
// 负荷数据------------------------------------------------------------------------------------------
// var mpDayPowerStore = new Ext.data.GroupingStore({
var mpDayPowerStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : 'qrystat/consumerInfoAction!queryNewMpDayPower.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'newMpDayPowerList'
			}, [{
				name : 'dataTime1'
					// type: 'date',
					// dateFormat:'Y-m-d\\TH:i:s'
				}, {
				name : 'dataTime2'
			}, {
				name : 'tFactor'
			}, {
				name : 'assetNo'
			}, {
				name : 'p'
			}, {
				name : 'pa'
			}, {
				name : 'pb'
			}, {
				name : 'pc'
			}, {
				name : 'ua'
			}, {
				name : 'ub'
			}, {
				name : 'uc'
			}, {
				name : 'ia'
			}, {
				name : 'ib'
			}, {
				name : 'ic'
			}, {
				name : 'i0'
			}, {
				name : 'q'
			}, {
				name : 'qa'
			}, {
				name : 'qa'
			}, {
				name : 'qb'
			}, {
				name : 'qc'
			}, {
				name : 'f'
			}, {
				name : 'fa'
			}, {
				name : 'fb'
			}, {
				name : 'fc'
			}, {
				name : 'papE'
			}, {
				name : 'prpE'
			}, {
				name : 'rapE'
			}, {
				name : 'rrpE'
			}, {
				name : 'papR'
			}, {
				name : 'prpR'
			}, {
				name : 'rapR'
			}, {
				name : 'rrpR'
			}, {
				name : 'terminalAddr'
			}, {
				name : 'consNo'
			}, {
				name : 'consName'
			}, {
				name : 'elecAddr'
			}])
		// ,
		// sortInfo : {
		// field : 'dataTime1',
		// direction : 'ASC'
		// },
		// groupField : 'dataTime1'
	});
var mpDayPowerCm = new Ext.grid.ColumnModel([{
	header : "日期",
	dataIndex : 'dataTime1',
	align : "center"
		// fomart:'Y-m-d H:i:s',
		// renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
	header : "时间",
	dataIndex : 'dataTime2',
	align : "center"
		// fomart:'Y-m-d H:i:s',
		// renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
	header : "倍率",
	dataIndex : 'tFactor',
	align : "center"
}, {
	header : "电能表资产",
	width : 180,
	dataIndex : 'assetNo',
	align : "center"
}, {
	header : "有功功率 ",
	width : 120,
	dataIndex : 'p',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="有功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "A相有功功率 ",
	width : 120,
	dataIndex : 'pa',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="A相有功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "B相有功功率 ",
	width : 120,
	dataIndex : 'pb',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="B相有功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "C相有功功率 ",
	width : 120,
	dataIndex : 'pc',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="C相有功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "A相电压",
	width : 120,
	dataIndex : 'ua',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="A相电压" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "B相电压",
	width : 120,
	dataIndex : 'ub',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="B相电压" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "C相电压",
	width : 120,
	dataIndex : 'uc',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="C相电压" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "A相电流",
	width : 120,
	dataIndex : 'ia',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="A相电流" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "B相电流",
	width : 120,
	dataIndex : 'ib',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="B相电流" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "C相电流",
	width : 120,
	dataIndex : 'ic',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="C相电流" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "零序电流",
	width : 120,
	dataIndex : 'i0',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="零序电流" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "无功功率",
	width : 120,
	dataIndex : 'q',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="无功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "A相无功功率",
	width : 120,
	dataIndex : 'qa',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="A相无功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "B相无功功率",
	width : 120,
	dataIndex : 'qb',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="B相无功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "C相无功功率",
	width : 120,
	dataIndex : 'qc',
	align : "center",
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="C相无功功率" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : "功率因素",
	dataIndex : 'f',
	align : "center"
}, {
	header : "A相功率因素",
	dataIndex : 'fa',
	align : "center"
}, {
	header : "B相功率因素",
	dataIndex : 'fb',
	align : "center"
}, {
	header : "C相功率因素",
	dataIndex : 'fc',
	align : "center"
}, {
	header : "正向有功总电量",
	hidden : true,
	dataIndex : 'papE',
	align : "center"
}, {
	header : "正向无功总电量",
	hidden : true,
	dataIndex : 'prpE',
	align : "center"
}, {
	header : "反向有功总电量",
	hidden : true,
	dataIndex : 'rapE',
	align : "center"
}, {
	header : "反向无功总电量",
	hidden : true,
	dataIndex : 'rrpE',
	align : "center"
}, {
	header : "正向有功总示值",
	hidden : true,
	dataIndex : 'papR',
	align : "center"
}, {
	header : "正向无功总示值",
	hidden : true,
	dataIndex : 'prpR',
	align : "center"
}, {
	header : "反向有功总示值",
	hidden : true,
	dataIndex : 'rapR',
	align : "center"
}, {
	header : "反向无功总示值",
	hidden : true,
	dataIndex : 'rrpR',
	align : "center"
}, {
	header : "终端地址",
	hidden : true,
	dataIndex : 'terminalAddr',
	align : "center"
}, {
	header : "用户编号",
	hidden : true,
	dataIndex : 'consNo',
	align : "center"
}, {
	header : "用户名称",
	hidden : true,
	dataIndex : 'consName',
	align : "center"
}, {
	header : "用电地址",
	hidden : true,
	dataIndex : 'elecAddr',
	align : "center"
}]);

var consumerInfoGrid3 = new Ext.grid.GridPanel({
	region : 'center',
	autoScroll : true,
	anchor : '100%',
	stripeRows : true,
	viewConfig : {
		forceFit : false
	},
	colModel : mpDayPowerCm,
	ds : mpDayPowerStore,
	bbar : new Ext.ux.MyToolbar({
				store : mpDayPowerStore,
				enableExpAll : true
			})
		// ,
		// view : new Ext.grid.GroupingView({
		// forceFit : false,
		// groupTextTpl : '{text} ({[values.rs.length]} {[values.rs.length > 1 ?
		// "Items" : "Item"]})'
		// }),
		// animCollapse : false
	});

function generalAnalyseShow() {
	consNo_newMpDayPower = consNoforCCust;
	openTab("采集数据综合分析", "./qryStat/collDataAnalyse/generalAnalyse.jsp");
};
// 黄轩加，显示图形
// showChartWin();
function showChartWin() {
	if (consNoforCCust == '') {
		return !!Ext.Msg.alert("提示", "请先选择用户节点");
	}
	function changeRadio(v) {
		if (v == 1) {
			PGroup.show();
			activeGroup = PGroup;
			AGroup.hide();
			voltGroup.hide();
		} else if (v == 2) {
			PGroup.hide();
			AGroup.hide();
			voltGroup.show();
			activeGroup = voltGroup;
		} else if (v == 3) {
			PGroup.hide();
			AGroup.show();
			voltGroup.hide();
			activeGroup = AGroup;
		}
	}

	// 第一行的radio
	var radios = new Ext.form.RadioGroup({
				autoEl : {
					tag : "center"
				},
				columns : [120, 120, 120],
				defaults : {
					name : "anyany"
				},
				items : [{
							boxLabel : "功率",
							checked : true,
							inputValue : 1
						}, {
							boxLabel : "电压",
							inputValue : 2
						}, {
							boxLabel : "电流",
							inputValue : 3
						}],
				listeners : {
					change : function(me, r) {
						changeRadio(r.inputValue);
					}
				}

			});
	var startDate = new Ext.form.DateField({
				format : "Y-m-d",
				editable : false,
				fieldLabel : "从",
				border : false,
				value : new Date().add(Date.DAY, -1)
			});
	var endDate = new Ext.form.DateField({
				format : "Y-m-d",
				editable : false,
				fieldLabel : "到",
				border : false,
				value : new Date()
			});
	// 电压checkgroup
	var voltGroup = new Ext.form.CheckboxGroup({
				autoEl : {
					tag : "center"
				},
				defaults : {
					checked : true
				},
				hidden : true,
				name : "电压",
				columns : [100, 100, 100],
				items : [{
							boxLabel : "A相电压",
							inputValue : 2,
							dataIndex : "ua"
						}, {
							boxLabel : "B相电压",
							inputValue : 3,
							dataIndex : "ub"
						}, {
							boxLabel : "C相电压",
							inputValue : 4,
							dataIndex : "ub"
						}]
			});
	// 电流checkgroup
	var AGroup = new Ext.form.CheckboxGroup({
				autoEl : {
					tag : "center"
				},
				defaults : {
					checked : true
				},
				hidden : true,
				name : "电流",
				columns : [100, 100, 100],
				items : [{
							boxLabel : "A相电流",
							inputValue : 2,
							dataIndex : "ia"
						}, {
							boxLabel : "B相电流",
							inputValue : 3,
							dataIndex : "ib"
						}, {
							boxLabel : "C相电流",
							inputValue : 4,
							dataIndex : "ic"
						}]
			});
	// 功率checkgroup
	var PGroup = new Ext.form.CheckboxGroup({
				autoEl : {
					tag : "center"
				},
				name : "功率",
				defaults : {
					checked : true
				},
				columns : [100, 100, 100, 100],
				items : [{
							boxLabel : "有功功率",
							inputValue : 1,
							dataIndex : "p"
						}, {
							boxLabel : "A有功功率",
							inputValue : 2,
							dataIndex : "pa"
						}, {
							boxLabel : "B有功功率",
							inputValue : 3,
							dataIndex : "pb"
						}, {
							boxLabel : "C有功功率",
							inputValue : 4,
							dataIndex : "pc"
						}]
			});
	// 第三行
	var threePanel = new Ext.Panel({
				border : false,
				autoEl : {
					tag : "center"
				},
				layout : "table",
				items : [voltGroup, AGroup, PGroup]
			});
	var findBtn = new Ext.Button({
				width : 80,
				text : "查询",
				handler : function() {
					showChart();
				}
			});
	var activeGroup = PGroup;
	// 第二行，时间，查询
	var twoPanel = new Ext.Panel({
				border : false,
				layout : "table",
				autoEl : {
					tag : "center"
				},
				items : [{
							border : false,
							layout : "form",
							labelWidth : 20,
							labelAlign : "right",
							items : [startDate]
						}, {
							border : false,
							layout : "form",
							labelWidth : 50,
							labelAlign : "right",
							items : [endDate]
						}, {
							border : false,
							width : 120,
							autoEl : {
								tag : "center"
							},
							items : [findBtn]
						}]
			});
	var topPanel = new Ext.Panel({
				border : false,
				layout : "form",
				height : 120,
				labelAlign : "right",
				labelWidth : 120,
				autoEl : {
					tag : "center"
				},
				region : "north",
				items : [radios, twoPanel, threePanel]
			});
	var chartPanel = new Ext.Panel({
				border : false
			});
	var panel = new Ext.Panel({
				border : false,
				region : "center",
				items : [chartPanel]
			});
	var win = new Ext.Window({
				width : 700,
				height : 500,
				layout : "border",
				items : [topPanel, panel]

			});
	win.show();
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
		var cks = activeGroup.getValue();
		var rs = [
				'<chart caption="'
						+ activeGroup.getName()
						+ "曲线"
						+ '"  lineThickness="1" showValues="0"  anchorRadius="2" '
						+ 'divLineAlpha="20"   divLineColor="CC3300" divLineIsDashed="1" showAlternateHGridColor="5" alternateHGridAlpha="5" '
						+ 'alternateHGridColor="CC3300" decimals="2" shadowAlpha="40" labelStep="1"   numvdivlines="5" '
						+ 'chartRightMargin="35" bgColor="FFFFFF,CC3300" bgAngle="270" bgAlpha="10,10">',
				'<categories>'];
		var cgs = [];
		var dss = {};
		Ext.each(cks, function(c) {
					dss[c.boxLabel] = [];
				});
		Ext.each(arr, function(a) {
			cgs.push('<category label="' + a["dataTime1"] + " "
					+ a["dataTime2"] + '" />');
			Ext.each(cks, function(c) {
						var abc = c.boxLabel
						dss[abc].push('<set value="' + a[c.dataIndex] + '" />');
					});
		});
		var abc = ["ff0000", "3600ff", "7eff00", "000000"];
		rs = rs.concat(cgs);
		rs.push("</categories>");
		var i = 0;
		Ext.iterate(dss, function(k, v) {
			rs
					.push('<dataset seriesName="'
							+ k
							+ '"  anchorBorderColor="1D8BD1" color="'
							+ abc[i++] + '">');
			rs = rs.concat(v);
			rs.push("</dataset>");
		});
		rs.push("</chart>");
		return rs.join("");
	}
	// showChart();
	showChart();
	function showChart() {
		
		var params = {
			start : 0,
			limit : 50,
			assetNo : Ext.getCmp("consumerInfoAsset2").getValue(),
			flagValue : consumerInfoRadioValue,
			CONS_NO : consNoforCCust,
			DateStart : startDate.getValue().format('Y-m-d'),
			DateEnd : endDate.getValue().format('Y-m-d')
		};
		Ext.Ajax.request({
					params : params,
					url : 'qrystat/consumerInfoAction!queryNewMpDayPower.action',
					success : function(response) {
						var o = Ext.decode(response.responseText);
						var list = o.newMpDayPowerList;
						var xmlData = createXml(list);
						var myChart = new FusionCharts(
								"./fusionCharts/ScrollLine2D.swf",
								"consume_info_js", panel.getWidth(), panel
										.getHeight());
						myChart.setDataXML(xmlData);
						myChart.setTransparent(true);
						myChart.render(chartPanel.getId());
					}
				});
	}
}
// Ext.getCmp('当日实时负荷').on('activate', function() {
// mpDayPowerStore.removeAll();
// });

// ------------------------------------------------------------------------------------------
var consumerInfoGridStore4 = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/sendDataQueryAction!querySendDataQueryB.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'SDQListB',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'mrSectNo'
							}, {
								name : 'consNo'
							}, {
								name : 'prpR'
							}, {
								name : 'consName'
							}, {
								name : 'dataDate'
							}, {
								name : 'papR'
							}, {
								name : 'papR1'
							}, {
								name : 'papR2'
							}, {
								name : 'papR3'
							}, {
								name : 'papR4'
							}, {
								name : 'rapR'
							}, {
								name : 'rapR1'
							}, {
								name : 'rapR2'
							}, {
								name : 'rapR3'
							}, {
								name : 'rapR4'
							}, {
								name : 'rp1R'
							}, {
								name : 'rp4R'
							}, {
								name : 'tFactor'
							}, {
								name : 'assetNo'
							}, {
								name : 'terminalAddr'
							}])
		});
var groupCM4 = new Ext.grid.ColumnModel([{
	header : '供电单位',
	width : 160,
	dataIndex : 'orgName',
	sortable : true,
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
		var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
				+ '</span>';
		return html;
	}
}, {
	header : '用户编码',
	dataIndex : 'consNo',
	sortable : true,
	align : 'center'
		// ,
		// renderer : function(val) {
		// return "<a href='javascript:' onclick='sdqWindowShow2(\"" + val
		// + "\")'>" + val + "</a>";
		// }
	}, {
	header : '用户名称',
	width : 120,
	dataIndex : 'consName',
	sortable : true,
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
		var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
				+ '</span>';
		return html;
	}
}, {
	header : '抄表段编号',
	// menuDisabled : true,
	width : 80,
	dataIndex : 'mrSectNo',
	sortable : true,
	align : 'center'
}, {
	header : '表计资产',
	width : 180,
	dataIndex : 'assetNo',
	sortable : true,
	align : 'center'
}, {
	header : '对应终端',
	dataIndex : 'terminalAddr',
	sortable : true,
	align : 'right'
}, {
	header : '抄表日期',
	dataIndex : 'dataDate',
	sortable : true,
	align : 'right'
}, {
	header : '正向有功总',
	width : 80,
	dataIndex : 'papR',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="正向有功总" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-尖',
	width : 80,
	dataIndex : 'papR1',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-峰',
	width : 80,
	dataIndex : 'papR2',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-平',
	width : 80,
	dataIndex : 'papR3',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-谷',
	width : 80,
	dataIndex : 'papR4',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '正向无功总',
	width : 80,
	dataIndex : 'prpR',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="正向无功总" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '一象限无功',
	hidden : true,
	width : 80,
	dataIndex : 'rp1R',
	sortable : true,
	align : 'centet',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="一象限无功" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '四象限无功',
	hidden : true,
	width : 80,
	dataIndex : 'rp4R',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="四象限无功" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '反向有功总',
	hidden : true,
	width : 80,
	dataIndex : 'rapR',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="反向有功总" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-尖',
	hidden : true,
	width : 80,
	dataIndex : 'rapR1',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-峰',
	hidden : true,
	width : 80,
	dataIndex : 'rapR2',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-平',
	hidden : true,
	width : 80,
	dataIndex : 'rapR3',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-谷',
	hidden : true,
	width : 80,
	dataIndex : 'rapR4',
	sortable : true,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '综合倍率',
	width : 80,
	dataIndex : 'tFactor',
	sortable : true,
	align : 'left'
}]);

var consumerInfoGrid4 = new Ext.grid.GridPanel({
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : groupCM4,
			ds : consumerInfoGridStore4,
			bbar : new Ext.ux.MyToolbar({
						store : consumerInfoGridStore4,
						enableExpAll : true
					})
		});
// ------------------------------------------------------------------------------------------
var consumerInfoGridStore5 = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/sendDataQueryAction!querySendDataQuery.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'SDQList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'dataDate'
							}, {
								name : 'papRE'
							}, {
								name : 'papR1E'
							}, {
								name : 'papR2E'
							}, {
								name : 'papR3E'
							}, {
								name : 'papR4E'
							}, {
								name : 'assetNo'
							}, {
								name : 'terminalAddr'
							}, {
								name : 'mrSectNo'
							}, {
								name : 'tFactor'
							}])
		});
var groupCM5 = new Ext.grid.ColumnModel([{
	header : '供电单位',
	width : 160,
	// menuDisabled : true,
	dataIndex : 'orgName',
	sortable : true,
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
		var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
				+ '</span>';
		return html;
	}
}, {
	header : '用户编码',
	// menuDisabled : true,
	dataIndex : 'consNo',
	sortable : true,
	align : 'center'
		// ,
		// renderer : function(val) {
		// return "<a href='javascript:' onclick='sdqWindowShow2(\"" + val
		// + "\")'>" + val + "</a>";
		// }
	}, {
	header : '用户名称',
	// menuDisabled : true,
	width : 120,
	dataIndex : 'consName',
	sortable : true,
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
		var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
				+ '</span>';
		return html;
	}
}, {
	header : '表计资产',
	width : 180,
	// menuDisabled : true,
	dataIndex : 'assetNo',
	sortable : true,
	align : 'center'
}, {
	header : '对应终端',
	// menuDisabled : true,
	dataIndex : 'terminalAddr',
	sortable : true,
	align : 'center'
}, {
	header : '抄表日期',
	// menuDisabled : true,
	dataIndex : 'dataDate',
	sortable : true,
	align : 'center'
}, {
	header : '正向有功费率总',
	// menuDisabled : true,
	dataIndex : 'papRE',
	sortable : true,
	width : 120,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="正向有功费率总" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-尖',
	// menuDisabled : true,
	dataIndex : 'papR1E',
	sortable : true,
	width : 80,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-峰',
	// menuDisabled : true,
	dataIndex : 'papR2E',
	sortable : true,
	width : 80,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-平',
	// menuDisabled : true,
	dataIndex : 'papR3E',
	sortable : true,
	width : 80,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '<-谷',
	// menuDisabled : true,
	dataIndex : 'papR4E',
	sortable : true,
	width : 80,
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="'
				+ Ext.util.Format.number(val, '0,000.000') + '">'
				+ Ext.util.Format.number(val, '0,000.000') + '</div>';
		return html;
	}
}, {
	header : '抄表段编号',
	// menuDisabled : true,
	width : 80,
	dataIndex : 'mrSectNo',
	sortable : true,
	align : 'center'
}, {
	header : '综合倍率',
	// menuDisabled : true,
	width : 80,
	dataIndex : 'tFactor',
	sortable : true,
	align : 'center'
}]);

var consumerInfoGrid5 = new Ext.grid.GridPanel({
			region : 'center',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : groupCM5,
			ds : consumerInfoGridStore5,
			bbar : new Ext.ux.MyToolbar({
						store : consumerInfoGridStore5,
						enableExpAll : true
					})
		});
// =========================================================================================================================
var consumerInfoPanel6 = new Ext.Panel({
			autoScroll : true,
			border : false,
			layout : 'border',
			title : '主站异常分析',
			items : []
		});
// ==============================================================================================================================
var consumerInfoPanel7 = new Ext.Panel({
			autoScroll : true,
			border : false,
			layout : 'border',
			title : '终端事件',
			items : []
		});
// ==============================================================================================================================
var consumerInfotab = new Ext.TabPanel({
			activeTab : 0,
			region : 'center',
			plain : true,
			defaults : {
				autoScroll : true
			},
			items : [{
						title : '用户档案',
						id : 'consumerInfotab1',
						height : 810,
						// layout : 'fit',
						border : false,
						baseCls : "x-plain",
						autoScroll : true,
						items : [consumerInfoConsPanel]
					}, {
						title : '采集点信息',
						id : 'consumerInfotab2',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [consumerInfoGrid2]
					}, {
						title : '负荷数据',
						id : 'consumerInfotab3',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [consumerInfoGrid3]
					}, {
						title : '抄表数据',
						id : 'consumerInfotab4',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [consumerInfoGrid4]
					}, {
						title : '电量数据',
						id : 'consumerInfotab5',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [consumerInfoGrid5]
					}, {
						title : '终端事件',
						id : 'consumerInfotab7',
						layout : 'fit',
						border : false,
						baseCls : "x-plain",
						height : 350,
						autoScroll : true,
						items : [consAmainGrid]
					}
			// ,{
			// title : '主站异常分析',
			// id : 'consumerInfotab6',
			// border : false,
			// baseCls : "x-plain",
			// height : 350,
			// autoScroll : true,
			// items : [consumerInfoPanel6]
			// }
			]
		});

consumerInfotab.on('tabchange', function(t, p) {
			if (p.title == '负荷数据') {
				consumerInfocombo2.ownerCt.show();
				/**
				 * 切换TAB页负荷数据,显示一、二次测，其他隐藏 author chunmingli 2010-08-18
				 */
				consumerInfoRadio.ownerCt.show();
			} else {
				consumerInfocombo2.ownerCt.hide();
				consumerInfoRadio.ownerCt.hide();
			}
		});
Ext.onReady(function() {
			// 设置顶层的公变采集点查询panel
			var consumerInfoAllpanel = new Ext.Panel({
						autoScroll : true,
						border : false,
						layout : 'border',
						items : [consumerInfoPanel, consumerInfotab]
					});
			renderModel(consumerInfoAllpanel, '客户综合查询');
			consumerInfocombo2.ownerCt.hide();

			if ((typeof(privateTerminalCon) != 'undefined')
					&& privateTerminalCon) {
				consNoforCCust = privateTerminalCon;
				consIdNew = privateIDCon;
				Ext.getCmp('consumerInfoID').setValue(privateTerminalCon);
				getconsumerInfoAsset();
			}

			Ext.getCmp('客户综合查询').on('activate', function() {
				if ((typeof(privateTerminalCon) != 'undefined')
						&& privateTerminalCon) {
					consNoforCCust = privateTerminalCon;
					consIdNew = privateIDCon;
					Ext.getCmp('consumerInfoID').setValue(privateTerminalCon);
					getconsumerInfoAsset();
				}
			});

		});
