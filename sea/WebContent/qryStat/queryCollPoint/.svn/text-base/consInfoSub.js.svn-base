//终端档案的combo
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

//用户变压器combo
	var ctpanelstore2 = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(),
				reader : new Ext.data.JsonReader({
							root : 'gtranList',
							idProperty : "tranName"
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
								}])
			});
			
var ctpanelstore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(), // 数据源
			fields : ['tranName'],
			reader : new Ext.data.JsonReader({
						root : 'gtranList'
					}, ['tranName'])
		});
var ctpanelcombo = new Ext.form.ComboBox({
		name : 'tranName',
			id : 'tranName',
			editable : false,
			store : ctpanelstore,
			displayField : 'tranName',
			labelStyle : "text-align:right;width:80px;",
			valueField : 'tranName',
			triggerAction : 'all',
			typeAhead : true,
			mode : 'local',
			// resizable : true,
			fieldLabel : '设备名称',
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
//显示终端资产编号的combo
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
// 用户变压器信息的第一个panel
var ctpanel1 = new Ext.Panel({
			//height : 40,
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
						columnWidth : .3,
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "铭牌容量",
									name : 'plateCap',
									readOnly : true,
									labelStyle : "text-align:right;width:50;",
									labelSeparator : "",
									width : 124
								}]
					}]
		});

// 用户变压器信息的第二个panel
var ctpanel2 = new Ext.Panel({
			// height : 40,
			layout : "column",
			baseCls : "x-plain",
			//style : "padding-left:5px",
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
			//style : "padding-left:5px",
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
			//style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "设备型号",
									name : 'modelNo',
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
									width : 199
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
			//style : "padding-left:5px",
			items : [{
						columnWidth : .3,
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "保护方式",
									name : 'protectMode',
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
									width : 55
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
						dataIndex : 'typeCode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量编号",
						dataIndex : 'assetNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量点名称",
						dataIndex : 'mpNo',
						menuDisabled : true,
						align : "center"
					}, {
						header : "计量点类型",
						dataIndex : 'mpName',
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
			items : [ctpanel1, ctpanel2, ctpanel3, ctpanel4, ctpanel5]
		})


// 嵌入客户综合查询原来页面
var tcpanelcenter1 = new Ext.FormPanel({
			title : "用户/变电站档案",
			height : 140,
			layout : "column",
			//region:'north',
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
	//region:'south',
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
										fieldLabel : "实际通信方式",
										name : 'comm',
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
//电能表档案的grid和panel
var mapanel1 = new Ext.FormPanel({
	title : '电能表档案',
	plain : true,
	border : false,
	height : 165,
	//region:'center',
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
										fieldLabel : "标定电流",
										name : 'ratedCurrent',
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
										fieldLabel : "测量原理",
										name : 'measTheory',
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
							items : [
{
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
										fieldLabel : "出厂编号",
										name : 'madeNo',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "接线方式",
										name : 'wiringMode',
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
										fieldLabel : "频率",
										name : 'freqCode',
										readOnly : true,
										labelStyle : "text-align:right;width:80;",
										labelSeparator : "",
										width : 80
									}, {
										fieldLabel : "当前状态",
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
//						autoHeight : false,
//						border : false,
//						layout : 'border',
			items : [tcpanelcenter1,tipanel41,ctfpanel,mapanel1,ctlinkmanGrid]
		});
		
		
//终端事件
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
						hidden:true
					}, {
						header : '用户编号',
						dataIndex : 'consNo'
					}, {
						header : '用户名称',
						dataIndex : 'consName',
						hidden:true
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
						renderer : function(val,cellMeta,record) {
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
							return disp+' ['+record.get('fromNo')+']';
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
					},{
						header : '状态',
						dataIndex : 'flowStatusCode',
						renderer : function(val){
							if(val == "00"){
								return "新异常";
							}else{
								return "";
							}
						}
					}
//					,{
//						header :'相关操作',
//						renderer : function(){
//							var html = '<a href="javascript:f_attention();">关注 </a>&nbsp;&nbsp;&nbsp;<a href="javascript:f_dealWith();">处理</a>';
//							return html;
//						}
//					}
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
										},{
											name : 'flowStatusCode'
										}])
					});

			var consAmainGrid = new Ext.grid.GridPanel({
						title :'报警事件列表',
						border : false,
						region :'center',
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


