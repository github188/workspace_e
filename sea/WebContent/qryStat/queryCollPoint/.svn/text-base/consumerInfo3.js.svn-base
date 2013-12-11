/**
 * 底层grid调整，用户编号、电能表资产号window无数据，ACTION调用是否有误,grid"终端地址码"取消
 */
consInfoAddr = '';
consIdforTerminal = '';// custId
consNoforCCust = '';
consIdNew = '';
var alertWindowParam = '';
var orgNoforalertWindow = '';
var staticSimNo = '';
Ext.onReady(function() {

	var treels = new LeftTreeListener({
				modelName : '客户综合查询',
				processClick : function(p, node, e) {
					treeclick(node, e);
				},
				// processDblClick : function(p, node, e) {
				// treedblclick(node, e);
				// },
				processUserGridSelect : function(cm, row, record) {
					// Ext.getCmp("CONS_NO").setValue(record.get('consNo'));
					consIdNew = record.get('consId');
					if (consIdNew.toString().substring(0, 1) == 'T') {
						consIdNew = consIdNew.substring(1, 20);
					}
					// alert(consIdforTerminal);
					consNoforCCust = record.get('consNo');
					consIdforTerminal = record.get('custId');
					 //alert(consIdforTerminal);
					orgNoforalertWindow = record.get('orgNo');
					consInfoAddr = record.get('tmnlAssetNo');
					csipanel2.getForm().reset();
					csipanel3.getForm().reset();
					tmnlAssetNostore.removeAll();
					consumerInfoStore.removeAll();
					TmnlrunStore.removeAll();
					getConMessage();
				}
			});
	// 点击左边树事件
	function treeclick(node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if (node.isLeaf()) {
			// Ext.getCmp("CONS_NO").setValue(obj.consNo);
			consIdforTerminal = obj.custId;
			sendTypeFlagSend = node.attributes.attributes.consType;
			consIdNew = obj.consId;
			// alert(consIdNew);
			consNoforCCust = obj.consNo;
			consInfoAddr = obj.terminalId;
			csipanel2.getForm().reset();
			csipanel3.getForm().reset();
			tmnlAssetNostore.removeAll();
			consumerInfoStore.removeAll();
			TmnlrunStore.removeAll();
			getConMessage();
		} else {
			return true;
		}
	};
	/** *hx edit add twos arguments*** */
	// 公用查询函数
	function getConMessage(cno, tid) {

		var CONS_NO = consNoforCCust;
		if (CONS_NO.toString().substring(0, 1) == 'T') {
			consumerInfoStore.baseParams = {
				CONS_NO : cno || CONS_NO,
				TG_ID : tid || consIdNew
			};
					consumerInfoStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		Ext.Ajax.request({
					url : 'qrystat/consumerInfoAction!queryConsumerInfo.action',
					params : {
						CONS_NO : cno || CONS_NO,
						TG_ID : tid || consIdforTerminal
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						csipanel2.getForm()
								.setValues(result.consumerInfoList[0]);
						csipanel3.getForm().setValues(result.cconsRtmnlList[0]);
						staticSimNo = Ext.getCmp('consumer_sim_no').getValue();
						tmnlAssetNostore.loadData(result);
						// consumerInfoStore.loadData(result);
						TmnlrunStore.loadData(result);

						addStr = '';
						deleteStr = '';
					}
				});
		} else {
			consumerInfoStore.baseParams = {
				CONS_NO : cno || CONS_NO,
				TG_ID : ''
			};
					consumerInfoStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		Ext.Ajax.request({
					url : 'qrystat/consumerInfoAction!queryConsumerInfo.action',
					params : {
						CONS_NO : cno || CONS_NO,
						TG_ID : ''
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						csipanel2.getForm()
								.setValues(result.consumerInfoList[0]);
						csipanel3.getForm().setValues(result.cconsRtmnlList[0]);
						staticSimNo = Ext.getCmp('consumer_sim_no').getValue();
						tmnlAssetNostore.loadData(result);
						// consumerInfoStore.loadData(result);
						TmnlrunStore.loadData(result);

						addStr = '';
						deleteStr = '';
					}
				});
		}


	}
	// // 双击左边树事件
	// function treedblclick(node, e) {
	// var obj = node.attributes.attributes;
	// var type = node.attributes.type;
	// if (node.isLeaf()) {
	// // var CONS = Ext.getCmp("CONS_NO");
	// Ext.getCmp("CONS_NO").setValue(obj.consNo);
	// consIdforTerminal = obj.consId;
	// consNoforCCust = obj.consNo;
	// getConMessage();
	// // 树中的类型包括 OOrg usr 同电网树
	// } else {
	// return true;
	// }
	// };
	// -----------------------------------------------------------------
	// var csipanel1 = new Ext.FormPanel({// 查询条件的panel
	// // title : "查询条件",
	// height : 30,
	// plain : true,
	// border : false,
	// items : [{
	// baseCls : "x-plain",
	// layout : "column",
	// style : "padding:5px",
	// items : [{
	// columnWidth : .7,// ----------------------
	// layout : "form",
	// labelWidth : 50,
	// defaultType : "textfield",
	// baseCls : "x-plain",
	// items : [{
	// fieldLabel : "节点名<font color='red'>*</font>",
	// labelStyle : "text-align:right;width:50;",
	// labelSeparator : "",
	// allowBlank : true,
	// // blankText : '请输入...',
	// readOnly : true,
	// id : 'CONS_NO',
	// validator : function(val) {
	// if (Ext.isEmpty(val))
	// return false;
	// else
	// return true;
	// },
	// width : 200
	// }]
	// }, {
	// columnWidth : .3,// ------------------
	// layout : "form",
	// defaultType : "button",
	// baseCls : "x-plain",
	// items : [{
	// text : "查询",
	// width : 80,
	// listeners : {
	// "click" : function() {
	// var CONS = Ext.getCmp("CONS_NO");
	// if (!CONS.isValid(true)) {
	// CONS.markInvalid('不能为空');
	// return true;
	// };
	// getConMessage();
	// }
	// }
	// }]
	// }]
	// }]
	// });
	// 用电用户中的下panel-------------------------------------------------------------------------------
	var tcpanelcenter1 = new Ext.Panel({
		height : 120,
		layout : "column",
		baseCls : "x-plain",// ???????等于 border : false, + frame :false
		style : "padding:5px",
		items : [{
			columnWidth : .33,
			layout : "form",
			labelWidth : 60,
			defaultType : "textfield",
			baseCls : "x-plain",
			items : [{
				fieldLabel : "<a href='javascript:' onclick='customerInfoWindowShow()'>用户编号</a>",
				name : 'consNo',
				readOnly:true,
				id : 'consNo',
				labelStyle : "text-align:right;width:50;",
				labelSeparator : "",
				width : 200
			}, {
				fieldLabel : "用户名称",
				name : 'consName',
				readOnly:true,
				labelStyle : "text-align:right;width:50;",
				labelSeparator : "",
				width : 200
			}, {
				fieldLabel : "用户分类",
				name : 'consTypeName',
				readOnly:true,
				labelStyle : "text-align:right;width:50;",
				labelSeparator : "",
				width : 200
			}, {
				fieldLabel : "行业分类",
				name : 'tradeName',
				readOnly:true,
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
					items : [{
								fieldLabel : "用电地址",
								name : 'elecAddr',
								readOnly:true,
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "用电类别",
								name : 'elecType',
								readOnly:true,
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "合同容量",
								name : 'contractCap',
								readOnly:true,
								labelStyle : "text-align:right;width:50;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "<a href='javascript:' onclick='consTransformerWindowShow()'>运行容量</a>",
								name : 'runCap',
								readOnly:true,
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
					items : [{
								fieldLabel : "所属供电所",
								name : 'orgName',
								readOnly:true,
								labelStyle : "text-align:right;width:60;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "所属变电站",
								name : 'subsName',
								readOnly:true,
								labelStyle : "text-align:right;width:60;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "<a href='javascript:' onclick='consPowerWindowShow()'>主供线路</a>",
								name : 'lineName',
								readOnly:true,
								labelStyle : "text-align:right;width:60;",
								labelSeparator : "",
								width : 200
							}, {
								fieldLabel : "供电电压",
								name : 'volt',
								readOnly:true,
								labelStyle : "text-align:right;width:60;",
								labelSeparator : "",
								width : 200
							}]
				}]
	});
	// 用电用户的panel-----------------------------------------------------------------------------------
	var csipanel2 = new Ext.FormPanel({
				// title : "用电客户",
				plain : true,
				border : false,
				items : [tcpanelcenter1]
			});

	var tmnlAssetNostore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(), // 数据源
				fields : ['cisAssetNo'],
				reader : new Ext.data.JsonReader({
							root : 'cconsRtmnlList'
						}, ['cisAssetNo'])
			});
	var tmnlAssetNocombo = new Ext.form.ComboBox({
				name : 'cisAssetNo',
				store : tmnlAssetNostore,
				displayField : 'cisAssetNo',
				labelStyle : "text-align:right;width:80px;",
				valueField : 'cisAssetNo',
				triggerAction : 'all',
				typeAhead : true,
				mode : 'local',
				resizable : true,
				fieldLabel : '终端资产编号',
				labelSeparator : '',
				// forceSelection : true,
				emptyText : '--请选择--',
				blankText : '--请选择--',
				selectOnFocus : true,
				width : 100
			});
	tmnlAssetNocombo.on('select', function(combox) {
				var value = combox.getValue();
				var record = TmnlrunStore.getById(value);
				csipanel3.getForm().loadRecord(record);
			});
	// 运行终端----------------------------------------------------------------
	var tipanel41 = new Ext.Panel({

		plain : true,
		border : false,
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
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "终端投运日期",
									name : 'runDate',
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "任务上送方式",
									name : 'sendUpMode',
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "优先供电方式",
									name : 'psModeName',
									readOnly:true,
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
							fieldLabel : "<a href='javascript:' onclick='openSimDetailInfo()'>SIM卡号</a>",
							name : 'simNo',
							readOnly:true,
							id : 'consumer_sim_no',
							labelStyle : "text-align:right;width:80px;",
							labelSeparator : "",
							width : 100
						}, {
							fieldLabel : "生产厂家",
							name : 'factoryName',
							readOnly:true,
							labelStyle : "text-align:right;width:80px;",
							labelSeparator : "",
							width : 100
						}, {
							fieldLabel : "采用通讯规约",
							name : 'protocolName',
							readOnly:true,
							labelStyle : "text-align:right;width:80px;",
							labelSeparator : "",
							width : 100
						}, {
							fieldLabel : "实际通信方式",
							name : 'comm',
							readOnly:true,
							labelStyle : "text-align:right;width:80px;",
							labelSeparator : "",
							width : 100
						}, {
							fieldLabel : "终端运行状态",
							name : 'statusName',
							readOnly:true,
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
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "终端类型",
									name : 'tmnlType',
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "采集方式",
									name : 'coll',
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "冻结周期",
									name : 'freezeCycleNum',
									readOnly:true,
									labelStyle : "text-align:right;width:80px;",
									labelSeparator : "",
									width : 100
								}, {
									fieldLabel : "门接点属性",
									name : 'gateAttrFlag',
									readOnly:true,
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

	// 包装的panel
	var csipanel3 = new Ext.FormPanel({
		title : "<a href='javascript:' onclick='openmytab()'>运行终端>></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ "&nbsp;&nbsp;<a href='javascript:' onclick='javascript:getMessageByalertWindow()'>终端事件>></a>",
		region : 'north',
		collapsible : true,
		height : 165,
		border : false,
		items : [tipanel41]
	});

	// **************************序列号
	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});

	var rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (consumerInfoStore && consumerInfoStore.lastOptions
							&& consumerInfoStore.lastOptions.params) {
						startRow = consumerInfoStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	// **************************
	var consumerInfoSm = new Ext.grid.CheckboxSelectionModel();
	var consumerInfoStore = new Ext.data.Store({
				// proxy : new Ext.data.MemoryProxy(),
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/consumerInfoAction!queryConsumerInfo.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'cmeterList',
							totalProperty : 'totalCount'
						}, [{
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'elecAddr'
								}, {
									name : 'assetNo'
								},
								{
									name : 'mpNo'
								}, {
									name : 'mpName'
								},
								// {
								// name : 'instLoc'
								// }, {
								// name : 'commMode'
								// },
								{
									name : 'commNo'
								}, {
									name : 'commAddr1'
								}, {
									name : 'baudrate'
								}, {
									name : 'tFactor'
								},
								// {
								// name : 'tmnlAssetNo'
								// },
								{
									name : 'regStatus'
								}, {
									name : 'regSn'
								}])
			});
	var csipanel4 = new Ext.grid.GridPanel({
		title : "<a href='javascript:' onclick='sendDataQueryWindowShow()'>抄表数据>></a>"
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:' onclick='javascript:newMpDayPowerShow()'>负荷数据>></a>",
		region : 'center',
		border : true,
		sm : consumerInfoSm,
		split : true,
		viewConfig : {
			forceFit : false
		},
		autoScroll : true,
		colModel : new Ext.grid.ColumnModel([consumerInfoSm, rowNum, {
					header : "用户号",
					dataIndex : 'consNo',
					menuDisabled : true,// 菜单显示关闭
					align : "center"
				}, {
					header : "用户名",
					dataIndex : 'consName',
					menuDisabled : true,
					align : "center",
					renderer : function(val, metaData, record) {
						var s = '用户名: ' + record.get('consName');
						var html = '<span  ext:qtip="' + s + '">' + val
								+ '</span>';
						return '<div align = "left">' + html + '</div>';
					}
				}, {
					header : "用户地址",
					dataIndex : 'elecAddr',
					menuDisabled : true,
					align : "center",
					renderer : function(val, metaData, record) {
						var s = '用户地址: ' + record.get('elecAddr');
						var html = '<span  ext:qtip="' + s + '">' + val
								+ '</span>';
						return '<div align = "left">' + html + '</div>';
					}
				},
				// {
				// header : "终端地址码",//取消
				// dataIndex : 'tmnlAssetNo',
				// menuDisabled : true,
				// align : "center"
				// },
				{
					header : "电能表资产号",
					// id:'ASSET_NO',
					dataIndex : 'assetNo',
					menuDisabled : true,
					align : "center",
					width : 180,
					renderer : function(value) {
						return "<a href='javascript:' onclick='meterAssetWindowShow(\""
								+ value + "\")'>" + value + "</a>";
					}
				}, // 使下拉框消失
				// {
				// header : "安装位置",
				// dataIndex : 'instLoc',
				// menuDisabled : true,
				// align : "center",
				// renderer : function(val, metaData, record) {
				// var s = '安装位置: ' + record.get('instLoc');
				// var html = '<span ext:qtip="' + s + '">' + val + '</span>';
				// return '<div align = "left">' + html + '</div>';
				// }
				// }, // 设置标签文字居中
				// {
				// header : "通讯方式",
				// width : 60,
				// dataIndex : 'commMode',
				// menuDisabled : true,
				// align : "center"
				// },
				{
					header : "计量点编号",
					width : 80,
					dataIndex : 'mpNo',
					menuDisabled : true,
					align : "center"
				}, {
					header : "计量点名称",
					width : 80,
					dataIndex : 'mpName',
					menuDisabled : true,
					align : "center"
				},
				{
					header : "通讯规约",
					width : 60,
					dataIndex : 'commNo',
					menuDisabled : true,
					align : "center"
				}, {
					header : "通讯地址",
					dataIndex : 'commAddr1',
					menuDisabled : true,
					align : "center"
				}, {
					header : "波特率",
					width : 50,
					dataIndex : 'baudrate',
					menuDisabled : true,
					align : "center"
				}, {
					//
					header : "综合倍率",
					width : 60,
					dataIndex : 'tFactor',
					menuDisabled : true,
					align : "center",
					renderer : function(value) {
						return "<a href='javascript:' onclick='runPTCTWindowShow("
								+ value + ")'>" + value + "</a>";
					}
				}, {
					header : "注册状态",
					width : 60,
					dataIndex : 'regStatus',
					menuDisabled : true,
					align : "center",
					renderer : function(value) {
						if (value == 0) {
							return '未注册'
						} else {
							return '已注册'
						}
					}
				}, {
					header : "注册序号",
					dataIndex : 'regSn',
					menuDisabled : true,
					align : "center"
				}]),
		ds : consumerInfoStore,
		bbar : new Ext.ux.MyToolbar({
					store : consumerInfoStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	});
	// border布局的嵌套panel
	var tcinfopannelbottom1 = new Ext.Panel({
				// xtype : "panel",
				region : 'north',
				height : 120,
				border : false,
				items : [csipanel2]
			});

	// 设置顶层的panel
	var tcinfopannel = new Ext.Panel({
				autoScroll : true,
				layout : 'border',
				items : [tcinfopannelbottom1, {
							layout : 'border',
							region : 'center',
							items : [csipanel3, csipanel4]
						}]
			});
	// 销毁页面控件***************************************************************************
	tcinfopannel.on('beforeclose', function() {
				tcinfopannel.items.each(function(item) {
							Ext.destroy(item);
						})
			});
	// **************************************************************************************
	renderModel(tcinfopannel, '客户综合查询');
	// if (privateTerminalCon !== undefined)
	if (typeof(privateTerminalCon) != 'undefined') {
		if (!Ext.isEmpty(privateTerminalCon)) {
			// Ext.getCmp("CONS_NO").setValue(privateTerminalCon);
			consNoforCCust = privateTerminalCon;
			getConMessage();
			privateTerminalCon = null;
			consIdforTerminal = null;
		}
	}

	Ext.getCmp('客户综合查询').on('activate', function() {
				if (typeof(privateTerminalCon) != 'undefined') {
					if (!Ext.isEmpty(privateTerminalCon)) {
						// Ext.getCmp("CONS_NO").setValue(privateTerminalCon);
						consNoforCCust = privateTerminalCon;
						getConMessage();
						privateTerminalCon = null;
						consIdforTerminal = null;
					}
				}
			});

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

	/** **hx*** */

	/** *实现从别的tab里面连接到这个页面* */
	Ext.getCmp('客户综合查询').on("activate", function() {
				if (!window.archivesData) {
					return;
				}
				csipanel2.getForm().reset();
				csipanel3.getForm().reset();
				tmnlAssetNostore.removeAll();
				consumerInfoStore.removeAll();
				TmnlrunStore.removeAll();
				getConMessage(window.archivesData.cno, window.archivesData.tid);
				window.archivesData = undefined;
			});
	window.archivesData && Ext.getCmp("客户综合查询") && (function() {
		csipanel2.getForm().reset();
		csipanel3.getForm().reset();
		tmnlAssetNostore.removeAll();
		consumerInfoStore.removeAll();
		TmnlrunStore.removeAll();
		getConMessage(window.archivesData.cno, window.archivesData.tid);
		window.archivesData = undefined;
	})();

});

function openmytab(url, param) {
	var p = new Ext.Panel({
				closable : true,
				autoScroll : true,
				autoLoad : {
					url : "./qryStat/queryCollPoint/terminalInfo.jsp",
					nocache : true,
					text : 'Loading...',
					scripts : true,
					scope : p,
					param : consNoforCCust
				}

			});

	var win = new Ext.Window({
				title : '运行终端',
				draggable : false,
				autoScroll : true,
				width : 800,
				height : 580,
				items : [p]
			});
	win.show();
}
function sendDataQueryWindowShow() {
	sendDataQueryConsNOValue = consNoforCCust;
	sendDataQueryAddrValue = consInfoAddr;
	openTab("抄表数据查询", "./qryStat/queryCollPoint/sendDataQuery.jsp");
};
function getMessageByalertWindow() {
	alertconsNo = consNoforCCust;
	alertorgNo = orgNoforalertWindow;
	alertWindowParam = 'ok';
	if (consNoforCCust == "") {
		alert("请先查询");
	} else {
		openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
				"deviceAbnormal");
	}
}

function openSimDetailInfo() {
	staticSimNo = staticSimNo;
	openTab("SIM卡流量信息", "./qryStat/sIMQuery/simDetailInfo.jsp");
}
function newMpDayPowerShow() {
	openTab("当日实时负荷", "./qryStat/queryCollPoint/newMpDayPower.jsp");
};
