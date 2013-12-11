/**
 * 模块：重点用户管理
 * 
 * @author 李春明
 * @version 2010年5月20日10:42:00
 */

// 重点用户编号
var emphUserNo = new Ext.form.TextField({
			id : 'emphUserNo',
			width : 120,
			fieldLabel : '重点用户编号',
			allowBlank : false,
			emptyText : '请输入...'
		});
//数据来源单选框
	var dataSrcRadio= new Ext.form.RadioGroup({
		id : "data_src_radio",
		fieldLabel : '数据来源',
		colums : [100, 100],
		items : [{
					boxLabel : '来源于营销',
					name : 'data_src',
					inputValue : 0,
					checked : true
				}, {
					boxLabel : '来源于采集',
					name : 'data_src',
					inputValue : 2
				}]
	});

	//用户类别复选框
	var userTypeRadio= new Ext.form.RadioGroup({
		id : "user_type_id",
		fieldLabel : '用户类别',
		colums : [80, 80, 80],
		items : [{
					boxLabel : 'VIP用户',
					name : 'user_type',
					inputValue : 1,
					checked : true
				}, {
					boxLabel : '高耗能用户',
					name : 'user_type',
					inputValue : 2
				}, {
					boxLabel : '高危用户',
					name : 'user_type',
					inputValue : 3
				}]
	});
	
		
// 重点用户名称
var emphUserName = new Ext.form.TextField({
			id : 'emphUserName',
			width : 120,
			fieldLabel : '重点用户名称',
			allowBlank : false,
			emptyText : '请输入...'
		});

//重点用户管理button
var topTextButtonPanel = new Ext.Panel({
			border : false,
			layout : 'table',
			bodyStyle : 'padding: 5px 0px 0px 0px',
			items : [{
						layout : 'form',
						border : false,
						labelWidth : 100,
						labelAlign : 'right',
						items : [emphUserNo]
					},{
						layout : 'form',
						border : false,
						labelWidth : 60,
						labelAlign : 'right',
						items : [dataSrcRadio]
					},{
						layout : 'form',
						border : false,
						width : 20,
						items : []
					},{
						layout : 'form',
						border : false,
						width : 100,
						items : [{
									xtype : 'button',
									width : 70,
									text : '查询',
									listeners : {
									"click" : function(btn) {}
									}
								}]
					}, {
						layout : 'form',
						border : false,
						width : 100,
						items : [{
									xtype : 'button',
									width : 70,
									text : '保存规则'
								}]
					}]

		})
// 监视频度 按时间精度分： 时、日、月
var monitorDateStore = new Ext.data.ArrayStore({
			fields : ['dateCode', 'dateName'],
			data : [["1", "时"], ["2", "日"], ["3", "月"]]
		})

// 监视频度 按时间精度分： 时、日、月
var monitorDateCB = new Ext.form.ComboBox({
			id : 'monitorDateCB',
			value : '1',
			editable : true,
			fieldLabel : '监视频度',
			store : monitorDateStore,
			triggerAction : 'all',
			mode : 'local',
			valueField : 'dateCode',
			displayField : 'dateName',
			width : 40,
			selectOnFocus : true
		})
var isMonitorCheck = new Ext.form.CheckboxGroup({
			id : 'is_Monitor_Check',
//			width : 650,
			columns : [100,80,80,100,80,100,120],
			fieldLabel : '监测类型',
			// fieldLabel:'',
			items : [{
						boxLabel : '监视用电异常',
						inputValue : 0,
						name : 'monitor_elec_exce'
					}, {
						boxLabel : '监视工况',
						inputValue : 1,
						name : 'monitor_exce'
					}, {
						boxLabel : '监视电压',
						inputValue : 2,
						name : 'monitor_tension'
					}, {
						boxLabel : '计量设备监测',
						inputValue : 3,
						name : 'monitor_device'
					}, {
						boxLabel : '监视电流',
						inputValue : 4,
						name : 'monitor_current'
					}, {
						boxLabel : '监视终端事件',
						inputValue : 5,
						name : 'monitor_terminal_event'
					}, {
						boxLabel : '变压器运行监测',
						inputValue : 6,
						name : 'monitor_tran'
					}]
		});
	/*
	 * 事件监测
	 * 电流检测
	 * 变压器负载监测
	 * 电压质量检测
	 * 计量设备监测
	 * 终端工况监测
	 */

//电压越限告警规则 下限
var elecRuleDown = new Ext.form.TextField({
			id : 'elecRuleUp',
			width : 60,
			fieldLabel : '电压越限告警规则: 下限',
			emptyText : ''
		});
//电压告警规则 上限
var elecRuleUp = new Ext.form.TextField({
			id : 'elecRuleDown',
			width : 60,
			fieldLabel : '上限',
			emptyText : ''
		});
//变压器负载监视规则 上浮
var transformerRuleUp = new Ext.form.TextField({
			id : 'transformerRuleUp',
			width : 60,
			fieldLabel : '上浮',
			emptyText : ''
		});

//变压器监视规则 下浮
var transformerRuleDown = new Ext.form.TextField({
			id : 'transformerRuleDown',
			width : 60,
			fieldLabel : '变压器负载监视规则:	  下浮',
			emptyText : ''
		});
		
		
//工况监视
var taskMonitor = new Ext.form.TextField({
			id : 'taskMonitor',
//			labelWidth : 100,
//			labelAlign : 'right',
			width : 120,
			fieldLabel : '工况监视',
			allowBlank : false,
			emptyText : '请输入...'
		});
		
// 事件监视
var eventMonitor = new Ext.form.TextField({
			id : 'eventMonitor',
			labelWidth : 100,
			labelAlign : 'right',
			width : 120,
			fieldLabel : '事件监视',
			allowBlank : false,
			emptyText : '请输入...'
		});
		
		
// 监测类型store
var monitorTypeStore = new Ext.data.ArrayStore({
			fields : ['monitorTypeCode', 'monitorTypeName'],
			data : [["01", "终端工况监测"], ["02", "计量设备监测"], ["03", "电压质量监测"],
					["04", "变压器负载监测"], ["05", "电流监测"]]
		})

// 监测类型
var monitorCombobox = new Ext.form.ComboBox({
			id : 'monitor_type',
			fieldLabel : '监测类型',
			store : monitorTypeStore,
			triggerAction : 'all',
			editable : true,
			mode : 'local',
			valueField : 'monitorTypeCode',
			displayField : 'monitorTypeName',
			emptyText : '',
			selectOnFocus : true
		})
		

//后台监视
var backstageMonitor = new Ext.form.Checkbox({
			width : 120,
			id : "checkbox",
			boxLabel : '启动后台监视',
			name : 'checkbox',
			inputValue : 1
//			checked : true
		})

//数据单选框
	var radioGroup= new Ext.form.RadioGroup({
//		width : 200,
		id : "radioGroup",
		colums : [80, 80],
		items : [{
					boxLabel : '相对值',
					name : 'magType',
					inputValue : 1,
					checked : true
				}, {
					boxLabel : '绝对值',
					name : 'magType',
					inputValue : 2
				}],
		listeners : {
			'change' : function(othis, checked) {}
		}
	});
//监测规则设置第一行
var monitorSetRuleOne = new Ext.Panel({
			border : false,
			layout : 'table',
			bodyStyle : 'padding: 5px 0px 0px 0px',
			items : [{
						layout : 'form',
						border : false,
						labelWidth : 10,
						labelAlign : 'right',
						width : 130,
						items : [radioGroup]
					},{
						layout : 'form',
						border : false,
						labelWidth : 150,
						labelAlign : 'right',
						items : [elecRuleDown]
					},{
						layout : 'form',
						border : false,
						labelWidth : 40,
						labelAlign : 'right',
						items : [elecRuleUp]
					},{
						layout : 'form',
						border : false,
						labelWidth : 100,
						labelAlign : 'right',
						items : [monitorCombobox]
					}]

		})
	//监测规则设置第二行
var monitorSetRuleTwo = new Ext.Panel({
			border : false,
			layout : 'table',
			bodyStyle : 'padding: 0px 0px 10px 0px',
			items : [{
						layout : 'form',
						border : false,
						labelWidth : 10,
//						labelAlign : 'right',
						width : 130,
						items : [backstageMonitor]
					},{
						layout : 'form',
						border : false,
						labelWidth : 150,
						labelAlign : 'right',
						items : [transformerRuleDown]
					},{
						layout : 'form',
						border : false,
						labelWidth : 40,
						labelAlign : 'right',
						items : [transformerRuleUp]
					}
					,{
						layout : 'form',
						border : false,
						labelWidth : 100,
						labelAlign : 'right',
						items : [eventMonitor]
					}
					]

		})	
var topMonitorPanel = new Ext.Panel({
			border : false,
//			title : '监测规则设置',
			layout : 'fit',
			region : 'center',
			autoScroll : false,
//			labelWidth : 80,
//			labelAlign : 'rigth',
			monitorResize : false,
			tbar : [{
					xtype : 'label',
					text : '监测规则设置',
					style : 'font-weight:bold;color:#15428b'
				}],
			items : [isMonitorCheck]
		})

// 重点用户列表
var sm = new Ext.grid.CheckboxSelectionModel();
var emphUserCM = new Ext.grid.ColumnModel([sm,{
			header : "供电单位",
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'orgNo'
		}, {
			header : "用户编号",
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'consNo'
		}, {
			header : "用户名称",
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'consName'
		}
		, {
			header : "行业",//行业
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'trade'
		}, {
			header : "合同容量",//合同容量
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'contractVol'
		}, {
			header : "运行容量",//运行容量
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'runVol'
		}, {
			header : "线路",//线路
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'line'
		}, {
			header : "用电地址",//是否VIP 或 用电地址
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'elecAdd'
		}
		, {
			header : "用户类别",
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'uesrType'
		}
		, {
			header : "数据来源",
			sortable : true,
			resizable : true,
			align : 'center',
			dataIndex : 'dataSrc'
		}
//		, {
//			header : "安装/运行容量",
//			sortable : true,
//			resizable : true,
//			dataIndex : ''
//		}, {
//			header : "所属行业",
//			sortable : true,
//			resizable : true,
//			dataIndex : ''
//		}, {
//			header : "用电类型",
//			sortable : true,
//			resizable : true,
//			dataIndex : ''
//		}, {
//			header : "电压等级",
//			sortable : true,
//			resizable : true,
//			dataIndex : ''
//		}, {
//			header : "用电地址",
//			sortable : true,
//			resizable : true,
//			dataIndex : ''
//		}
		]

);

var emphUserStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './advapp/vipconsman/gatherTaskTlate!queryUser.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'issueMsgList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgNo'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'trade'
							}, {
								name : 'contractVol'
							}, {
								name : 'runVol'
							}, {
								name : 'line'
							}, {
								name : 'elecAdd'
							}, {
								name : 'uesrType'
							}, {
								name : 'dataSrc'
							}])
		});

var emphUserToolbar = new Ext.Toolbar([{
			xtype : 'label',
			id : 'tpTtLab',
			html : "<font font-weight:bold;>重点用户列表</font>"
		}, {
			xtype : 'tbfill'
		}, {
			xtype : 'checkbox',
			id : 'vipSelectAllcb',
			boxLabel : '全选',
			name : 'vipSelectAllcb',
			checked : false,
			listeners : {
				'check' : function(r, c) {
				}
			}
		},  {
			icon : '',
			text : '删除所选用户',
//			iconCls : 'cancel',
			handler : function() {
				if (Ext.getCmp('vipSelectAllcb').checked) {
					emphUserStore.removeAll(true);
					unlockGrid();
				} else {
					var recs = resultSm.getSelections();
					for (var i = 0; i < recs.length; i = i + 1) {
						emphUserStore.remove(emphUserStore
								.getById(recs[i].data.tmnlAssetNo));
					}
				}
				var rsStore = Ext.getCmp('tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(emphUserStore));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
			}
		},{
			text : '保存为重点用户',
			handler : function() { // 调用加入群组，判断
									var groupTmnlArray = new Array();
									var recs = sm.getSelections();
									if (recs.length < 1) {
										Ext.Msg.alert('提示', '请选择要加入群组的用户');
										return;
									}
									for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('cons_no') + '`'
												+ recs[i].get('tmnl_asset_no');
										groupTmnlArray.push(tmnl); // groupTmnlArray[i]
																	// = tmnl;
									}
									saveOrUpdateGroupWindowShow(groupTmnlArray);
								}
		}]);

var emphUserGrid = new Ext.grid.GridPanel({
//			title : '系统用户',
			region : 'center',
			autoScroll : true,
			layout : 'fit',
			viewConfig : {
					forceFit : false
				},
			cm : emphUserCM,
			sm : sm,
			ds : emphUserStore,
			tbar : emphUserToolbar
		});
var topPanel = new Ext.Panel({
			border : false,
//			title : '监测规则设置',
			layout : 'form',
			region : 'north',
			height : 85,
			autoScroll : true,
			monitorResize : true,
			items : [topTextButtonPanel,topMonitorPanel]
		})


// 渲染到主页面
var mainPanel = new Ext.Panel({
			layout : "border",
			autoScroll : true,
			monitorSize : true,
			items : [topPanel,emphUserGrid]

		});
// --以上为界面构造-------//
Ext.onReady(function() {
			renderModel(mainPanel, '重点用户设置');
		});
	// grid解锁
	function unlockGrid() {
		resultSm.unlock();
		resultGrid.onEnable();
		resultGrid.getBottomToolbar().enable();
		Ext.getCmp('tpselectAllcb').setValue(false);
	}

	// grid解锁
	function lockGrid() {
		resultSm.lock();
		resultGrid.onDisable();
		resultGrid.getBottomToolbar().disable();
	}