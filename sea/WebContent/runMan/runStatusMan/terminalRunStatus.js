var bz_Window; // "bz备注" 弹出窗口 定义

//通信方式store
var commModeStore = new Ext.data.JsonStore({
		url : "baseapp/gatherQualityEvaluate!queryCommMode.action",
		fields : [ 'commModeCode', 'commMode' ],
		root : "commModeList"
	});

//通信方式
var channelCombox = new Ext.form.ComboBox({
	store : commModeStore,
	displayField : 'commMode',
	valueField : 'commModeCode',
	name : 'channelCombox',
	anchor : '95%',
	editable : false,
	mode : 'remote',
	triggerAction : 'all',
	fieldLabel : '通信方式'
});

commModeStore.load();

//终端厂商store
var tmnlFactoryStore = new Ext.data.JsonStore({
		url : "runman/queryTmnlRunStatus!queryTmnlFactory.action",
		fields : [ 'factoryCode', 'factoryName' ],
		root : "factoryList"
	});

//终端厂商
var tmnlFactoryCombox = new Ext.form.ComboBox({
	store : tmnlFactoryStore,
	displayField : 'factoryName',
	valueField : 'factoryCode',
	name : 'tmnlFactory',
	anchor : '95%',
	editable : false,
	mode : 'remote',
	triggerAction : 'all',
	fieldLabel : '终端厂商'
});

tmnlFactoryStore.load();

function openWindow_tmnlRunS(rec) {
	ycid = rec.get('tmnlExceptionId');
	yhbh = rec.get('consNo');
	remark = rec.get('remark');
	// ycid, yhbh, remark
	var field = new Ext.form.TextArea({
				width : 480,
				id : 'bz_remark',
				height : 120,
				value : remark,
				fieldLabel : '修改'
			});

	var fieldPanel = new Ext.form.FormPanel({
		border : true,
		layout : 'form',
		region : 'north',
		height : 160,
		labelAlign : 'right',// 只在form布局中才起作用
		labelWidth : 40, // 只在form布局中才起作用
		items : [field],
		buttonAlign : 'center',
		buttons : [{
			text : '保存',
			width : 60,
			listeners : {
				"click" : function() {
					Ext.Ajax.request({
								url : 'runman/queryTmnlRunStatus!updateRemark.action',
								params : {
									exceptionId : ycid,
									remark : field.getValue()
								},
								success : function(response) {
									rec.set('remark', field.getValue());
									rec.commit();
									Ext.MessageBox.alert('提示', '保存成功');
								}
							});
				}
			}
		}, {
			text : '关闭',
			width : 60,
			handler : function() {
				bz_Window.close();
			}
		}]
	});

	var bz_cm = new Ext.grid.ColumnModel([{
				header : '异常ID',
				dateIndex : 'tmnlExceptionId',
				align : 'center',
				hidden : true
			}, {
				header : '用户编号',
				dateIndex : 'consNo',
				align : 'center'
			}, {
				header : '终端地址',
				dateIndex : 'tmnlAssetNo',
				align : 'center'
			}, {
				header : '故障名称',
				dateIndex : 'exceptName',
				align : 'center'
			}, {
				header : '发生时间',
				dateIndex : 'exceptDate',
				align : 'center',
				renderer : function(val) {
					return Date.parseDate(val,'Y-m-dTH:i:s').format('Y-m-d H:i:s');
				}
			}, {
				header : '终端厂商',
				dateIndex : 'factoryName',
				align : 'center'
			}]);

	var bz_store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'runman/queryTmnlRunStatus!queryRemark.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'tmnlRunRemarkList',
							totalProperty : 'totalCount'
						}, [{
									name : 'tmnlExceptionId'
								}, {
									name : 'consNo'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'exceptName'
								}, {
									name : 'exceptDate',
									type : 'date',
									dateFormat : 'Y-m-d H:i:s' // 2009-04-03T00:00:00
								}, {
									name : 'factoryName'
								}])
			});

	var bz_grid = new Ext.grid.GridPanel({
				id : 'bz_grid',
				border : true,
				region : 'center',
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				store : bz_store,
				cm : bz_cm,
				bbar : new Ext.ux.MyToolbar({
							store : bz_store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});

	bz_Window = new Ext.Window({
				name : 'bzbzbz',
				title : '终端异常工况-备注',
				width : 600,
				height : 500,
				layout : 'border',
				modal : true, // True 表示为当window显示时对其后面的一切内容进行遮罩,默认为false
				border : false,
				resizable : false,
				items : [fieldPanel, bz_grid]
			});

	// 取Store
	bz_store.baseParams = {
		consNo : yhbh
	};

	bz_store.load();

	// 备注表格查询,自动load ??
	// Ext.Ajax.request({
	// url : '',
	// params : {},
	// success : function(response) {
	// var obj = Ext.decode(response.responseText);
	// field.setValue(obj.remark);
	// }
	// })
	bz_Window.show();
}

// 复选框选择终端是否在线
var isOnlineCheck = new Ext.form.CheckboxGroup({
			id : 'is_online_check_box',
			width : 160,
//			labelWidth : 10,
			// fieldLabel:'',
			items : [{
						boxLabel : '在线终端',
						inputValue : 1,
						name : 'onlineTerminalBox'
					}, {
						boxLabel : '离线终端',
						inputValue : 0,
						name : 'offlineTerminalBox'
					}]
		});


// 终端类型
var typeComStore = new Ext.data.ArrayStore({
			fields : ['tmnlTypeCode', 'tmnlTypeName'],
			data : [["-1", "----全部----"],["01", "负荷控制终端"], ["02", "负荷监测终端"], ["03", "配变终端"],
					["04", "配变监控终端"], ["05", "低压集中器"], ["06", "低压采集终端"],
					["07", "采集模块"], ["08", "关口电能量终端"]]
		});

// 终端类型 TMNL_TYPE TMNL_TYPE_CODE
var terminalCombobox = new Ext.form.ComboBox({
			id : 'rt_tmnl_type',
			fieldLabel : '终端类型',
			store : typeComStore,
			triggerAction : 'all',
			editable : false,
			mode : 'local',
			valueField : 'tmnlTypeCode',
			displayField : 'tmnlTypeName',
			anchor : '92%',
			emptyText : '终端类型',
			selectOnFocus : true
		});

//异常类型
var typeExComStore = new Ext.data.JsonStore({
			url : "runman/queryTmnlRunStatus!queryTmnlTypeCode.action",
			fields : ['tmnlTypeCode', 'tmnlTypeName'],
			root : "tmnlTypeCodeList",
			listeners:{
				load:function(){
					terminalExCombobox.setValue(terminalExCombobox.getValue());
				}
			}
	});

// 异常类型 TMNL_TYPE TMNL_TYPE_CODE
var terminalExCombobox = new Ext.form.ComboBox({
			id : 'ex_tmnl_type',
			fieldLabel : '异常类型',
			store : typeExComStore,
			triggerAction : 'all',
			editable : false,
			mode : 'local',
			valueField : 'tmnlTypeCode',
			displayField : 'tmnlTypeName',
			anchor : '98%',
			emptyText : '异常类型',
			selectOnFocus : true
		});
typeExComStore.load();

Ext.onReady(function() {

	// "分厂家统计"按钮页面 开始
	// var fcjtj_form = new Ext.Panel({
	// // border:false, //“分厂家统计按钮”弹出页面 上方panel
	// layout : 'column',
	// region:'north',
	// bodyStyle : 'padding:10px 0px 0px 0px',
	// height : 40,
	// items : [{
	// columnWidth : .7,
	// layout : 'form',
	// border : false,
	// labelAlign : 'right',
	// items : [{
	// xtype : 'radiogroup',
	// width:200,
	// fieldLabel : '用户类型',
	// items : [{
	// boxLabel : '专变',
	// name : 'rb-auto',
	// inputValue : 1,
	// checked : true
	// }, {
	// boxLabel : '公变',
	// name : 'rb-auto',
	// inputValue : 2
	// }, {
	// boxLabel : '低压',
	// name : 'rb-auto',
	// inputValue : 3
	// }, {
	// boxLabel : '关口',
	// name : 'rb-auto',
	// inputValue : 4
	// }]
	// }]
	// }, {
	// columnWidth : .15,
	// border : false,
	// items : [{
	// xtype : 'button',
	// text : '导出EXCEL',
	// width : 70
	// }]
	// }]
	// })
	var fcjtj_cm = new Ext.grid.ColumnModel([{ // "分厂家统计"按钮弹出页面下方grid
		header : '供电单位',
		dataIndex : 'comp',
		align : 'center'
	}, {
		header : '国电南瑞',
		dataIndex : 'gdnr',
		align : 'center'
	}, {
		header : '杭州华隆',
		dataIndex : 'hzhl',
		align : 'center'
	}, {
		header : '浙江高科',
		dataIndex : 'zjgk',
		align : 'center'
	}, {
		header : '浙江华立',
		dataIndex : 'zjhl',
		align : 'center'
	}, {
		header : '南京新联',
		dataIndex : 'njxl',
		align : 'center'
	}, {
		header : '上海协同',
		dataIndex : 'shxt',
		align : 'center'
	}, {
		header : '上海华冠',
		dataIndex : 'shhg',
		align : 'center'
	}, {
		header : '浙江万胜',
		dataIndex : 'zjws',
		align : 'center'
	}, {
		header : '合计',
		dataIndex : 'hj',
		align : 'center'
	}]);
	var fcjtj_data = [
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 55', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '32 '],
			['长春城郊农电局', ' 55', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '32 '],
			['长春城郊农电局', ' 55', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '32 '],
			['长春城郊农电局', ' 55', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '32 '],
			['长春城郊农电局', ' 55', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '32 '],
			['长春城郊农电局', ' 43', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '54 '],
			['长春城郊农电局', ' 56', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '52 '],
			['长春城郊农电局', ' 22', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '12 '],
			['其他单位 ', '41 ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' 41'],
			['合计', '  ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '  ']];
	var fcjtj_store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(fcjtj_data),
				reader : new Ext.data.ArrayReader({}, [{
									name : 'comp'
								}, {
									name : 'gdnr'
								}, {
									name : 'hzhl'
								}, {
									name : 'zjgk'
								}, {
									name : 'zjhl'
								}, {
									name : 'njxl'
								}, {
									name : 'shxt'
								}, {
									name : 'shhg'
								}, {
									name : 'zjws'
								}, {
									name : 'hj'
								}])
			});
	fcjtj_store.load();

	var fcjtj_grid = new Ext.grid.GridPanel({
				id : 'fcjtj_grid',
				border : false,
				autoScroll : true,
				bodyStyle : 'padding:2px 0px 2px 0px',
				store : fcjtj_store,
				cm : fcjtj_cm,
				stripeRows : true,
				autoWidth : true,
				viewConfig : {
					sortAscText : '升序',
					sortDescText : '降序',
					columnsText : '显示列',
					deferEmptyText : '请等待...',
					emptyText : '没有数据'
				},
				bbar : new Ext.ux.MyToolbar({
							store : fcjtj_store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});

	var window_cjtj = new Ext.Window({ // 分厂家统计按钮 window
		name : 'window_cjtj',
		title : '异常工况情况-分厂家统计',
		width : 700,
		height : 400,
		layout : 'fit',
		resizable : false,
		autoScroll : true,
		closeAction : 'hide',
		items : [fcjtj_grid]
	});

	// 分类型统计按钮 开始
	// var flxtj_form = new Ext.Panel({
	// layout : 'column',
	// region:'north',
	// bodyStyle : 'padding:10px 0px 0px 0px',
	// height : 40,
	// items : [{
	// columnWidth : .7,
	// layout : 'form',
	// border : false,
	// labelAlign : 'right',
	// items : [{
	// xtype : 'radiogroup',
	// width:200,
	// fieldLabel : '用户类型',
	// items : [{
	// boxLabel : '专变',
	// name : 'rb-auto',
	// inputValue : 1,
	// checked : true
	// }, {
	// boxLabel : '公变',
	// name : 'rb-auto',
	// inputValue : 2
	// }, {
	// boxLabel : '低压',
	// name : 'rb-auto',
	// inputValue : 3
	// }, {
	// boxLabel : '关口',
	// name : 'rb-auto',
	// inputValue : 4
	// }]
	// }]
	// }, {
	// columnWidth : .15,
	// border : false,
	// items : [{
	// xtype : 'button',
	// text : '导出EXCEL',
	// width : 70
	// }]
	// }]
	// })
	var flxtj_cm = new Ext.grid.ColumnModel([{ // "分类型查询"按钮弹出页面下方grid
		header : '供电单位',
		dataIndex : 'comp',
		align : 'center'
	}, {
		header : '终端与主站无通讯',
		dataIndex : 'yc1',
		align : 'center'
	}, {
		header : '终端与表计无通讯',
		dataIndex : 'yc2',
		align : 'center'
	}, {
		header : '终端告警过多',
		dataIndex : 'yc3',
		align : 'center'
	}, {
		header : '终端时钟错误',
		dataIndex : 'yc4',
		align : 'center'
	}, {
		header : '终端控制未取消',
		dataIndex : 'yc5',
		align : 'center'
	}, {
		header : '无上报任务',
		dataIndex : 'yc6',
		align : 'center'
	}, {
		header : 'SIM卡号不一致',
		dataIndex : 'yc7',
		align : 'center'
	}, {
		header : '终端没建档',
		dataIndex : 'yc8',
		align : 'center'
	}, {
		header : '合计',
		dataIndex : 'hj',
		align : 'center'
	}]);
	var flxtj_data = [
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['长春城郊农电局', ' 41', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '41 '],
			['其他单位 ', '41 ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' 41'],
			['其他单位1 ', '41 ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' 41'],
			['合计', '  ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '  ']];
	var flxtj_store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(flxtj_data),
				reader : new Ext.data.ArrayReader({}, [{
									name : 'comp'
								}, {
									name : 'yc1'
								}, {
									name : 'yc2'
								}, {
									name : 'yc3'
								}, {
									name : 'yc4'
								}, {
									name : 'yc5'
								}, {
									name : 'yc6'
								}, {
									name : 'yc7'
								}, {
									name : 'yc8'
								}, {
									name : 'hj'
								}])
			});
	flxtj_store.load();

	var flxtj_grid = new Ext.grid.GridPanel({
				border : false,
				store : flxtj_store,
				cm : flxtj_cm,
				stripeRows : true,
				viewConfig : {
					sortAscText : '升序',
					sortDescText : '降序',
					columnsText : '显示列',
					deferEmptyText : '请等待...',
					emptyText : '没有数据'
				},
				bbar : new Ext.ux.MyToolbar({
							store : flxtj_store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});

	var window_lxtj = new Ext.Window({ // 分类型统计按钮 window
		name : 'window_lxtj',
		title : '异常工况情况-分类型统计',
		width : 700,
		layout : 'fit',
		height : 400,
		border : false,
		resizable : false,
		autoScroll : true,
		closeAction : 'hide',
		items : [flxtj_grid]
	});

	// 通信方式按钮页面设置 开始
	var cjtj_cm = new Ext.grid.ColumnModel([{
				header : '通信方式',
				dateIndex : 'txfs',
				align : 'center'
			}, {
				header : '终端数量',
				dateIndex : 'zdsl',
				align : 'center'
			}]);

	var cjtj_data = [['GPRS', '22'], ['短信', '33'], ['有线', ' ']];

	var cjtj_store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(cjtj_data),
				reader : new Ext.data.ArrayReader({}, [{
									name : 'txfs'
								}, {
									name : 'zdsl'
								}])
			});
	cjtj_store.load();

	var cjtj_grid = new Ext.grid.GridPanel({
				border : false,
				layout : 'form',
				autoScroll : true,
				store : cjtj_store,
				stripeRows : true,
				bbar : new Ext.ux.MyToolbar({
							store : cjtj_store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						}),
				cm : cjtj_cm
			});
	var window_txfs = new Ext.Window({ // 通讯方式按钮 点击页面
		name : 'window_txfs',
		title : '终端实时工况-通讯方式',
		width : 500,
		height : 300,
		layout : 'fit',
		border : false,
		resizable : false,
		autoScroll : true,
		closeAction : 'hide',
		items : [cjtj_grid]
	});
	// **************************************************************************************以上为弹出按钮弹出的
	// window页面

	// ------------radio1上方panel
	var tmnlForm = new Ext.Panel({
				border : true,
				region : 'north',
				bodyStyle : 'padding:10px 0px',
				layout : 'column',
				height : 45,
				items : [{
							columnWidth : .22,
							border : false,
							labelAlign : 'right',
							labelWidth : 50,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										id : 'tmnlRS_orgName',
										allowBlank : false,
										width : 120,
										readOnly : true,
										labelSeparator : ' ',
										fieldLabel : '节点名 ',
										emptyText : '请选择'
									}, {
										xtype : 'hidden',// **********增加隐藏组件，对应各左边树选取的节点赋值，是否有更简便的方法?
										id : 'tmnlRS_orgNo'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_tmnlAssetNo'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_lineId'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_groupNo'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_nodeType'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_orgType'
									}, {
										xtype : 'hidden',
										id : 'tmnlRS_subsId'
									}, {
										xtype : 'hidden',
										id : 'factory_code'
									}]
						}, {
							border : false,
							columnWidth : .18,
							labelAlign : 'right',
							labelWidth : 70,
							layout : 'form',
							items : [{
										id : 'timeS_tmnlRunE',
										xtype : 'datefield',
										labelSeparator : ' ',
										width : 90,
										editable : false,
										value : new Date().add(Date.DAY, -1),
										fieldLabel : '发生时间从',
										format : "Y-m-d"
									}]
						}, {
							columnWidth : .16,
							border : false,
							labelAlign : 'right',
							labelWidth : 15,
							layout : 'form',
							items : [{
										id : 'timeE_tmnlRunE',
										xtype : 'datefield',
										labelSeparator : ' ',
										width : 90,
										editable : false,
										value : new Date(),
										maxValue : new Date(),
										fieldLabel : '到',
										format : "Y-m-d"
									}]
						}, {
							columnWidth : .23,
							border : false,
							layout : 'form',
							labelWidth : 50,
							labelAlign : 'right',
							items : [terminalExCombobox]
						}, {
							columnWidth : .09,
							border : false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '查询',
								width : 60,
								listeners : {
									"click" : function(btn) {
										var panelMask = new Ext.LoadMask(panel
														.getId(), {// 加载数据时，防止重复提交
													msg : "加载中,请稍后..."
												}); // 第一个参数panel为要遮盖的控件,
										// msg为提示信息
										// 最好不要直接遮盖按钮详细构造方法可以参考 EXT
										// 文档要在请求前遮盖
										panelMask.show();
										var jdm = Ext.getCmp("tmnlRS_orgName")
												.getValue();
										var type = Ext
												.getCmp("tmnlRS_nodeType")
												.getValue();
										var startSj = Ext
												.getCmp("timeS_tmnlRunE")
												.getValue();
										var endSj = Ext
												.getCmp("timeE_tmnlRunE")
												.getValue();

										if (jdm == null || jdm.trim() == "") {
											Ext.MessageBox.minWidth = 120;// 控制窗口大小
											Ext.MessageBox.alert("提示",
													"请选择节点名！");
											panelMask.hide();// 提示后关闭或隐藏
											return;
										}
										if (type == 'org') {
											storeTe.baseParams = {
												orgNo : Ext
														.getCmp("tmnlRS_orgNo")
														.getValue(),
												orgType : Ext
														.getCmp("tmnlRS_orgType")
														.getValue(),
												tmnlTypeCode : Ext
														.getCmp("ex_tmnl_type")
														.getValue(),
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											};
											storeTe.load();

										} else if (type == 'usr') {
											storeTe.baseParams = {
												consNo : Ext
														.getCmp("tmnlRS_tmnlAssetNo")
														.getValue(),
												tmnlTypeCode : Ext
														.getCmp("ex_tmnl_type")
														.getValue(),
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											};
											storeTe.load();

										} else if (type == 'line') {
											storeTe.baseParams = {
												lineId : Ext
														.getCmp("tmnlRS_lineId")
														.getValue(),
												tmnlTypeCode : Ext
														.getCmp("ex_tmnl_type")
														.getValue(),
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											};
											storeTe.load();

										} else if (type == 'cgp'
												|| type == 'ugp') {
											storeTe.baseParams = {
												groupNo : Ext
														.getCmp("tmnlRS_groupNo")
														.getValue(),
												tmnlTypeCode : Ext
														.getCmp("ex_tmnl_type")
														.getValue(),
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											};
											storeTe.load();

										} else if (type == 'sub') {
											storeTe.baseParams = {
												subsId : Ext
														.getCmp("tmnlRS_subsId")
														.getValue(),
												tmnlTypeCode : Ext
														.getCmp("ex_tmnl_type")
														.getValue(),
												nodeType : type,
												startDate : startSj,
												endDate : endSj
											};
											storeTe.load();

										}
										// loadMask.......
										storeTe.on('load', function() {
													panelMask.hide();
												}); // 由于是异步，必须在请求返回数据处理完成后才关闭LoadMask
										storeTe.on('loadexception', function() {
													panelMask.hide();
												}); // 要考虑即使载入失败也可关闭LoadMask
									}
								}
							}]
						},
						{
							columnWidth : .09,
							border : false,
							layout : 'form',
							items : [{
								xtype : 'button',
								text : '加入群组',
								width : 60,
								handler : function() { // 调用加入群组，判断
									var groupTmnlArray = new Array();
									var recs = sm.getSelections();
									if (recs.length < 1) {
										Ext.Msg.alert('提示', '请选择要加入群组的用户');
										return;
									}
									for (var i = 0; i < recs.length; i++) {
										var tmnl = recs[i].get('consNo') + '`'
												+ recs[i].get('tmnlAssetNo');
										groupTmnlArray.push(tmnl); // groupTmnlArray[i]
																	// = tmnl;
									}
									saveOrUpdateGroupWindowShow(groupTmnlArray);
								}

							}]
						}]
			});

	var tmnlExForm = new Ext.Panel({ // ----------------终端实时工况radio2中的panel
//		bodyStyle : 'padding:10px 0px',
		region : 'north',
		height : 60,
		border : false,
		layout : 'table',
		layoutConfig : {
			columns : 3
		},
		items : [{
					border : false,
					width : 220,
					labelAlign : 'right',
					labelWidth : 60,
					layout : 'form',
					items : [{
								xtype : 'textfield',
								id : 'tmnlRS_ex_orgName',
								allowBlank : false,
								width : 120,
								labelSeparator : ' ',
								fieldLabel : '节点名',
								readOnly : true,
								emptyText : '请选择'
							}, {
								xtype : 'hidden',// 隐藏panel，用于选取左边树节点信息
								id : 'tmnlRS_ex_orgNo'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_tmnlAssetNo'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_lineId'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_groupNo'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_nodeType'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_orgType'
							}, {
								xtype : 'hidden',
								id : 'tmnlRS_ex_subsId'
							}]
				}, {
					width : 200,
					border : false,
					layout : 'form',
					labelWidth : 60,
					labelAlign : 'right',
					items : [channelCombox]
				}, {
					width : 200,
					border : false,
					layout : 'form',
					labelWidth : 60,
					labelAlign : 'right',
					items : [tmnlFactoryCombox]
				}, {
					width : 200,
					border : false,
					layout : 'form',
					labelWidth : 60,
					labelAlign : 'right',
					items : [terminalCombobox]
				}, {
					width : 200,
					border : false,
					layout : 'form',
					labelWidth : 10,
					items : [isOnlineCheck]
				}, {
				width : 200,
				border : false,
				layout : 'table',
				items : [{
					width : 80,
					border : false,
					layout : 'form',
					fieldLabel : '',
					labelWidth : 10,
					items : [{
						xtype : 'button',
						text : '查询',
						width : 60,
						listeners : {
							"click" : function(btn) {
								var panelMask = new Ext.LoadMask(panelEx
												.getId(), {
											msg : "加载中,请稍后..."
										});
								panelMask.show();
								var jdm = Ext.getCmp("tmnlRS_ex_orgName")
										.getValue();
								var type = Ext.getCmp("tmnlRS_ex_nodeType")
										.getValue();
								// 获得复选框值
								var pn = new Array();
								pn = getCheckBoxList();

								if (jdm == null || jdm.trim() == "") {
									Ext.MessageBox.minWidth = 120; // 控制窗口大小
									Ext.MessageBox.alert("提示", '请选择节点名！');
									panelMask.hide();// 提示后关闭或隐藏
									return;
								}
								if (type == 'org') {
									storeTr.baseParams = {
										orgNo : Ext.getCmp("tmnlRS_ex_orgNo")
												.getValue(),
										orgType : Ext
												.getCmp("tmnlRS_ex_orgType")
												.getValue(),
										tmnlTypeCode : Ext
												.getCmp("rt_tmnl_type")
												.getValue(),
										isOnline : pn,
										nodeType : type,
										commMode : channelCombox.getValue(),
										tmnlFactory : tmnlFactoryCombox
												.getValue()
									}
									storeTr.load();
								} else if (type == 'usr') {
									storeTr.baseParams = {
										consNo : Ext
												.getCmp("tmnlRS_ex_tmnlAssetNo")
												.getValue(),
										tmnlTypeCode : Ext
												.getCmp("rt_tmnl_type")
												.getValue(),
										isOnline : pn,
										nodeType : type
									};
									storeTr.load();

								} else if (type == 'line') {
									storeTr.baseParams = {
										lineId : Ext.getCmp("tmnlRS_ex_lineId")
												.getValue(),
										tmnlTypeCode : Ext
												.getCmp("rt_tmnl_type")
												.getValue(),
										isOnline : pn,
										nodeType : type,
										commMode : channelCombox.getValue(),
										tmnlFactory : tmnlFactoryCombox
												.getValue()
									};
									storeTr.load();

								} else if (type == 'cgp' || type == 'ugp') {
									storeTr.baseParams = {
										groupNo : Ext
												.getCmp("tmnlRS_ex_groupNo")
												.getValue(),
										tmnlTypeCode : Ext
												.getCmp("rt_tmnl_type")
												.getValue(),
										isOnline : pn,
										nodeType : type,
										commMode : channelCombox.getValue(),
										tmnlFactory : tmnlFactoryCombox
												.getValue()
									};
									storeTr.load();

								} else if (type == 'sub') {
									storeTr.baseParams = {
										subsId : Ext.getCmp("tmnlRS_ex_subsId")
												.getValue(),
										tmnlTypeCode : Ext
												.getCmp("rt_tmnl_type")
												.getValue(),
										isOnline : pn,
										nodeType : type,
										commMode : channelCombox.getValue(),
										tmnlFactory : tmnlFactoryCombox
												.getValue()
									};
									storeTr.load();

								}
								storeTr.on('load', function() {
											panelMask.hide();
										});
								storeTr.on('loadexception', function() {
											panelMask.hide();
										});
							}
						}
					}]
				}, {
					width : 80,
					border : false,
					layout : 'form',
					items : [{
						xtype : 'button',
						text : '加入群组',
						width : 60,
						handler : function() { // 调用加入群组，判断
							var groupTmnlArray = new Array();
							var recs = rtSm.getSelections();
							if (recs.length < 1) {
								Ext.Msg.alert('提示', '请选择要加入群组的用户');
								return;
							}
							for (var i = 0; i < recs.length; i++) {
								var tmnl = recs[i].get('consNo') + '`'
										+ recs[i].get('tmnlAssetNo');
								// groupTmnlArray[i] = tmnl;
								groupTmnlArray.push(tmnl);
							}
							saveOrUpdateGroupWindowShow(groupTmnlArray);
						}
					}]
				}]}]
	});

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
					if (storeTe && storeTe.lastOptions
							&& storeTe.lastOptions.params) {
						startRow = storeTe.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var rowNum_1 = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex_1) {
					var startRow = 0;
					if (storeTr && storeTr.lastOptions
							&& storeTr.lastOptions.params) {
						startRow = storeTr.lastOptions.params.start;
					}
					return startRow + rowIndex_1 + 1;
				}
			});
	// *****************页码递增结束

	// radio1的grid
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel([rowNum, sm, {
				header : '异常ID',
				dataIndex : 'tmnlExceptionId',
				align : 'center',
				hidden : true
			}, {
				header : '供电单位',
				dataIndex : 'orgName',
				align : 'center',
				width : 150,
				// --------------------------------------------以下注释保留！！！
				// renderer : function(value, metaData, record, rowIndex,
				// colIndex, store) {
				// var s = '用户名称:' + record.get('consName');
				// var html = '<span ext:qtip="' + s + '">' + value + '</span>';
				// return html;
				// },

				// renderer : function(val, metaData, record) {
				// // return '<div align = "left">' + val + '</div>';
				// var s = '供电单位:' + record.get('orgName');
				// var html = '<span ext:qtip="' + s + '">' + val + '</span>';
				// return '<div align = "left">' + html + '</div>';
				// }

				renderer : function(v) {
					var html = '<span ext:qtitle="供电单位:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '用户编号',
				dataIndex : 'consNo',
				align : 'center',
				renderer : function(val) {
					return "<a href='javascript:' onclick='openTab_cjsjfx(\"" + val + "\")'>" + val + "</a>";
				}
			}, {
				header : '用户名称',
				dataIndex : 'consName',
				align : 'center',
				width : 140,
				renderer : function(v) {
					var html = '<span ext:qtitle="用户名称:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '终端地址',
				dataIndex : 'terminalAddr',
				align : 'center',
				renderer : function(v) {
					var html = '<span ext:qtitle="终端地址:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '故障名称',
				dataIndex : 'exceptName',
				align : 'center',
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : '发生时间',
				dataIndex : 'exceptDate',
				align : 'center',
				width : 140,
				renderer : function(val) {
					return Date.parseDate(val,'Y-m-dTH:i:s').format('Y-m-d H:i:s');
				}
//				renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
			}, {
				header : '流程状态',
				dataIndex : 'flowStatusCode',
				align : 'center',
				hidden : true
			}, {
				header : 'SIM',
				dataIndex : 'simNo',
				align : 'center'
			}, {
				header : '终端厂商',
				dataIndex : 'factoryName',
				align : 'center',
				renderer : function(val) {
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : '备注',
				dataIndex : 'remark',
				align : 'center',
				renderer : function(val) { // value,meta,rec
					return '<font color="blue">' + val + '</font>';
					// var ycid=rec.get('tmnlExceptionId');
					// var yhbh=rec.get('consNo');
					// var remark=rec.get('remark');
					// return "<a href='javascript:'
					// onclick='openWindow_tmnlRunS(\""+ycid+"\",\""+yhbh+"\",\""+remark
					// +"\");'>查看修改备注</a>";
				}
			}]);

	var storeTe = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'runman/queryTmnlRunStatus!queryTerminalRunStatus.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'tmnlRunStatusList',
					totalProperty : 'totalCount'
				}, [{
							name : 'tmnlExceptionId'// 异常ID
						}, {
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consName'
						}, {
							name : 'tmnlAssetNo'
						},{
							name : 'terminalAddr'
						}, {
							name : 'exceptName'
						}, {
							name : 'exceptDate',
//							type : 'date',
							dateFormat : 'Y-m-d H:i:s' // 2009-04-03T00:00:00
						}, {
							name : 'flowStatusCode'
						}, {
							name : 'simNo'
						}, {
							name : 'factoryName'
						}, {
							name : 'remark'
						}])
	});

	var teGrid = new Ext.grid.GridPanel({ // 异常工况表格
		ds : storeTe,
		region : 'center',
		cm : cm,
		sm : sm,
		loadMask : false,// true,加载数据时--"读取中"
		autoScroll : true,
		autoWidth : true,
		stripeRows : true,// 显示行分隔
		viewConfig : {// 视图配置项
			forceFit : false
			// 整个column宽度自适应大小
		},
		bbar : new Ext.ux.MyToolbar({
					store : storeTe,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	});

	teGrid.on('cellclick', function(grid, row, col) {
				var cm = grid.getColumnModel();
				var id = cm.getColumnId(col);
				// alert(id);

				if (id == 12) {
					var rec = grid.getStore().getAt(row);
					openWindow_tmnlRunS(rec);
				}
			});

	// radio2的grid,
	var rtSm = new Ext.grid.CheckboxSelectionModel();
	var rtCm = new Ext.grid.ColumnModel([rowNum_1, rtSm, {
		header : '供电单位',
		dataIndex : 'orgName',
		align : 'center',
		width : 150,
		renderer : function(v) {
			var html = '<span ext:qtitle="供电单位:" ext:qtip="' + v + '">' + v
					+ '</span>';
			return '<div align = "left">' + html + '</div>';
		}
	}, {
		header : '用户编号',
		dataIndex : 'consNo',
		align : 'center',
		renderer : function(val) {
			return "<a href='javascript:' onclick='openTab_cjsjfx(\"" + val
					+ "\")'>" + val + "</a>";
		}
	}, {
		header : '用户名称',
		dataIndex : 'consName',
		align : 'center',
		width : 140,
		renderer : function(v) {
			var html = '<span ext:qtitle="用户名称:" ext:qtip="' + v + '">' + v
					+ '</span>';
			return '<div align = "left">' + html + '</div>';
		}
	}, {
		header : '用电地址',
		dataIndex : 'elecAddr',
		align : 'center',
		width : 120,
		renderer : function(v) {
			var html = '<span ext:qtitle="用电地址:" ext:qtip="' + v + '">' + v
					+ '</span>';
			return '<div align = "left">' + html + '</div>';
		}
	}, {
		header : '终端地址',
		dataIndex : 'terminalAddr',
		align : 'center',
		renderer : function(v) {
			var html = '<span ext:qtitle="终端地址:" ext:qtip="' + v + '">' + v
					+ '</span>';
			return '<div align = "left">' + html + '</div>';
		}
	}, {
		header : '通信方式',
		dataIndex : 'commMode',
		align : 'center',
		width : 90,
		renderer : function(val) {
			if (val == null) {
				return "<div align = 'center'></div>";
			} else {
				return "<div align = 'left'>" + val + "</div>";
			}
		}
	}, {
		header : '是否在线',
		dataIndex : 'isOnline',
		align : 'center',
		width : 70,
		renderer : function(value) {// SQL中进行判断过，0="离线"，1="在线"；否则：value直接取0或1
			if (value == "离线") {
				return "<font color='red'>离线</font>";
			} else if (value == "在线") {
				return "<font color='green'>在线</font>";
			}
		}
	}, {
		header : '离线时间(小时)',
		dataIndex : 'lastTime',
		align : 'center',
		width : 120,
		renderer : function(value,cellMeta,record){
			if(record.get('isOnline') == '离线')	{
				return value;
			}else{
				return '';
			}
		}
	}, {
		header : 'SIM IP',
		dataIndex : 'gateIp',
		align : 'center'
	}, {
		header : '当前负荷',
		dataIndex : 'currentLoad',
		align : 'center',
		width : 70
	}, {
		header : '负荷时间',
		dataIndex : 'loadTime',
		align : 'center',
		width : 120
//		fomart:'Y-m-d H:i:s',
//		renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}]);

	var storeTr = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'runman/queryTmnlRunStatus!queryTerminalRunStatusRun.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'tmnlRunStatusRunList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consName'
						}, {
							name : 'elecAddr'
						}, {
							name : 'terminalAddr'
						},{
							name : 'tmnlAssetNo'
						}, {
							name : 'commMode'
						}, {
							name : 'isOnline'
						}, {
							name : 'lastTime'
						}, {
							name : 'gateIp'
						}, {
							name : 'currentLoad'
						}, {
							name : 'loadTime'
//							type: 'date',
//         					dateFormat:'Y-m-d\\TH:i:s' //2009-04-03T00:00:00 
						}])
	});

	var trGrid = new Ext.grid.GridPanel({
				ds : storeTr,
				region : 'center',
				cm : rtCm,
				sm : rtSm,
				loadMask : false,// true,加载数据时--"读取中"
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				bbar : new Ext.ux.MyToolbar({
							store : storeTr,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});

	// 顶端radio切换 开始
	var panel_top = new Ext.FormPanel({
				labelWidth : 1,
				region : 'north',
				height : 40,
				bodyStyle : 'padding:10px',
				border : false,
				items : [{
							xtype : 'radiogroup',
							id : 'trsRadio',
							width : 255,
							items : [{
										boxLabel : '终端异常工况',
										name : 'condRadio',
										id : 'radio_01',
//										checked : true,
										listeners : {
											check : function(checkbox, checked) {
												if (checked) {
													panel_low.layout.setActiveItem(0);// activeItem"0,1",对应下面card布局
												} else {
													panel_low.layout.setActiveItem(1);
												}
											}
										}
									}, {
										boxLabel : '终端实时工况',
										name : 'condRadio',
										id : 'radio_02',
										 checked : true
								}]
						}]
			});
			
//	var cpRadio = new Ext.form.RadioGroup({
//		id : 'trsRadio',
//		columns : [100,100],
//		items : [{
//		name : 'condRadio',
//		boxLabel : '终端异常工况',
//		checked : true,
//		id : 'cpTypeZB',
//		listeners : {
//											check : function(checkbox, checked) {
//												if (checked) {
//													panel_low.layout.setActiveItem(0);// activeItem"0,1",对应下面card布局
//												} else {
//													panel_low.layout.setActiveItem(1);
//												}
//											}
//										}
//	}, {
//		name : 'condRadio',
//		boxLabel : '终端实时工况',
//		id : 'cpTypeGB',
//		listeners : ''
//	}]
//	});

	var panel = new Ext.Panel({ // 终端异常工况radio页面
		border : false,
		layout : 'border',
		items : [tmnlForm, teGrid]
	});

	var panelEx = new Ext.Panel({ // 终端实时工况radio页面
		layout : 'border',
		border : false,
		items : [tmnlExForm, trGrid]
	});

	var panel_low = new Ext.Panel({
				region : 'center',
				layout : 'card', // card布局，在上面按钮check时，activeItem："0,1",则"panel, panelEx"
				activeItem : 1,
				border : false,
				items : [panel, panelEx]
			});

	var viewPanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [panel_top, panel_low]
			});

	// 监听左边树点击 暂无行业 **
	var treeListener = new LeftTreeListener({
		modelName : '终端设备运行状态',// !
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			var type = node.attributes.type;

			// var rq = form_DXcyhs.find('name', 'sjDay')[0].getValue();
			// ***补充查询条件***

			if (type == 'org' && obj.orgType != '02') {// obj.orgType != '02' 省节点不可点选、查询
				Ext.getCmp("tmnlRS_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_orgName").setValue(obj.orgName);
				Ext.getCmp("tmnlRS_orgNo").setValue(obj.orgNo);
				Ext.getCmp("tmnlRS_orgType").setValue(obj.orgType);

				Ext.getCmp("tmnlRS_ex_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_ex_orgName").setValue(obj.orgName);
				Ext.getCmp("tmnlRS_ex_orgNo").setValue(obj.orgNo);
				Ext.getCmp("tmnlRS_ex_orgType").setValue(obj.orgType);

			} else if (type == 'usr') {
				Ext.getCmp("tmnlRS_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_orgName").setValue(obj.consName);
				Ext.getCmp("tmnlRS_tmnlAssetNo").setValue(obj.tmnlAssetNo);

				Ext.getCmp("tmnlRS_ex_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_ex_orgName").setValue(obj.consName);
				Ext.getCmp("tmnlRS_ex_tmnlAssetNo").setValue(obj.tmnlAssetNo);

			} else if (type == 'line') {
				Ext.getCmp("tmnlRS_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_orgName").setValue(obj.lineName);
				Ext.getCmp("tmnlRS_lineId").setValue(obj.lineId);

				Ext.getCmp("tmnlRS_ex_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_ex_orgName").setValue(obj.lineName);
				Ext.getCmp("tmnlRS_ex_lineId").setValue(obj.lineId);

			} else if (type == 'cgp' || type == 'ugp') {
				Ext.getCmp("tmnlRS_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_orgName").setValue(obj.groupName);
				Ext.getCmp("tmnlRS_groupNo").setValue(obj.groupNo);

				Ext.getCmp("tmnlRS_ex_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_ex_orgName").setValue(obj.groupName);
				Ext.getCmp("tmnlRS_ex_groupNo").setValue(obj.groupNo);

			} else if (type == 'sub') {
				Ext.getCmp("tmnlRS_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_orgName").setValue(obj.subsName);
				Ext.getCmp("tmnlRS_subsId").setValue(obj.subsId);

				Ext.getCmp("tmnlRS_ex_nodeType").setValue(type);
				Ext.getCmp("tmnlRS_ex_orgName").setValue(obj.subsName);
				Ext.getCmp("tmnlRS_ex_subsId").setValue(obj.subsId);

			} else {
				return true;
			}
		},
		// 用户区
		processUserGridSelect : function(cm, row, record) {

			Ext.getCmp("tmnlRS_nodeType").setValue('usr');
			Ext.getCmp("tmnlRS_orgName").setValue(record.get('consName'));
			Ext.getCmp("tmnlRS_tmnlAssetNo")
					.setValue(record.get('tmnlAssetNo'));
			Ext.getCmp("tmnlRS_ex_nodeType").setValue('usr');
			Ext.getCmp("tmnlRS_ex_orgName").setValue(record.get('consName'));
			Ext.getCmp("tmnlRS_ex_tmnlAssetNo").setValue(record
					.get('tmnlAssetNo'));

		}
	});

	// viewPanel.render('terminalRunStatus'); //原rander方法
	renderModel(viewPanel, '终端设备运行状态');
	// --------------------------监听
	if (typeof(staticChannelMonitorIsonline) != 'undefined') {
		if (staticChannelMonitorIsonline == "0") {

//			Ext.getCmp("is_online_check_box").setValue(
			isOnlineCheck.setValue(
				{
						'offlineTerminalBox' : true,
						'onlineTerminalBox' : false
					}
					);
		} else if (staticChannelMonitorIsonline == "1") {

//			Ext.getCmp("is_online_check_box").setValue(
				isOnlineCheck.setValue(
				{
						'offlineTerminalBox' : false,
						'onlineTerminalBox' : true
					}
					);
		}
	}
	//获得复选框值
			var pn = new Array();
			pn = getCheckBoxList();
			
	if (typeof(staticChannelmonitorOrgName) != 'undefined'
			&& typeof(staticChannelmonitorOrgNo) != 'undefined') {
		
		Ext.getCmp("tmnlRS_ex_nodeType").setValue("org");
		Ext.getCmp("tmnlRS_ex_orgName").setValue(staticChannelmonitorOrgName);
		Ext.getCmp("tmnlRS_ex_orgNo").setValue(staticChannelmonitorOrgNo);
		//设置异常类型 默认为空
			Ext.getCmp("rt_tmnl_type").setValue("");
		
		//==============================
		Ext.getCmp("tmnlRS_ex_orgType").setValue(staticChannelmonitorOrgType);
		//===================================
		storeTr.baseParams = {
			orgNo : Ext.getCmp("tmnlRS_ex_orgNo").getValue(),
			isOnline : pn,
			nodeType : Ext.getCmp("tmnlRS_ex_nodeType").getValue(),
			tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
			orgType  : Ext.getCmp("tmnlRS_ex_orgType").getValue() 
			
		}
		storeTr.load();
	} else if (typeof(staticChannelmonitorFactoryCode) != 'undefined'
			&& typeof(staticChannelmonitorFactoryName) != 'undefined') {
				
		Ext.getCmp("tmnlRS_ex_nodeType").setValue("fac");
		Ext.getCmp("tmnlRS_ex_orgName").setValue(staticChannelmonitorFactoryName);
		Ext.getCmp("factory_code").setValue(staticChannelmonitorFactoryCode);
		//设置异常类型 默认为空
			Ext.getCmp("rt_tmnl_type").setValue("");
		storeTr.baseParams = {
			factoryCode : Ext.getCmp("factory_code").getValue(),
			isOnline : pn,
			tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
			nodeType : Ext.getCmp("tmnlRS_ex_nodeType").getValue()
		}
		storeTr.load();
	}
	
	//added by 姜炜超 for 重点户监测数据------------start
	if((typeof(vipConsMonitorData_Flag) != 'undefined') && vipConsMonitorData_Flag){
		vipConsMonitorData_Flag = false;
		Ext.getCmp("tmnlRS_nodeType").setValue('usr');
		Ext.getCmp("tmnlRS_orgName").setValue(vipConsMonitorData_consName);
		Ext.getCmp("tmnlRS_tmnlAssetNo").setValue(vipConsMonitorData_consNo);
		
		Ext.getCmp("tmnlRS_ex_nodeType").setValue('usr');
		Ext.getCmp("tmnlRS_ex_orgName").setValue(vipConsMonitorData_consName);
		Ext.getCmp("tmnlRS_ex_tmnlAssetNo").setValue(vipConsMonitorData_consNo);
		
		storeTr.baseParams = {
				consNo : Ext.getCmp("tmnlRS_ex_tmnlAssetNo").getValue(),
				tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
				isOnline :pn,
				nodeType : 'usr'
		};
		storeTr.load();
	}
	//added by 姜炜超 for 重点户监测数据------------end
	
	
	// 触发事件加载数据
	Ext.getCmp('终端设备运行状态').on('activate', function() {
		if (typeof(staticChannelMonitorIsonline) != 'undefined') {
			panel_low.layout.setActiveItem(1);
			Ext.getCmp("trsRadio").setValue("radio_02",true);
			if (typeof(staticChannelMonitorIsonline) != 'undefined') {
				if (staticChannelMonitorIsonline == "0") {

//			Ext.getCmp("is_online_check_box").setValue(
			isOnlineCheck.setValue(
				{
						'offlineTerminalBox' : true,
						'onlineTerminalBox' : false
					}
					);
		} else if (staticChannelMonitorIsonline == "1") {

//			Ext.getCmp("is_online_check_box").setValue(
			isOnlineCheck.setValue(
				{
						'offlineTerminalBox' : false,
						'onlineTerminalBox' : true
					}
					);
		}
			}
		}
		
		//获得复选框值
			var pn = new Array();
			pn = getCheckBoxList();
			
		if (typeof(staticChannelmonitorOrgName) != 'undefined'
				&& typeof(staticChannelmonitorOrgNo) != 'undefined') {

			Ext.getCmp("tmnlRS_ex_nodeType").setValue("org");
			Ext.getCmp("tmnlRS_ex_orgName")
					.setValue(staticChannelmonitorOrgName);
			Ext.getCmp("tmnlRS_ex_orgNo").setValue(staticChannelmonitorOrgNo);
			//===========================================
			Ext.getCmp("tmnlRS_ex_orgType").setValue(staticChannelmonitorOrgType);
			//===============================================
			//设置异常类型 默认为空
			Ext.getCmp("rt_tmnl_type").setValue("");

			storeTr.baseParams = {
				orgNo : Ext.getCmp("tmnlRS_ex_orgNo").getValue(),
				isOnline : pn,
				nodeType : Ext.getCmp("tmnlRS_ex_nodeType").getValue(),
				tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
				orgType  : Ext.getCmp("tmnlRS_ex_orgType").getValue()
			}
			storeTr.load();
		} else if (typeof(staticChannelmonitorFactoryCode) != 'undefined'
				&& typeof(staticChannelmonitorFactoryName) != 'undefined') {

			Ext.getCmp("tmnlRS_ex_nodeType").setValue("fac");
			Ext.getCmp("tmnlRS_ex_orgName")
					.setValue(staticChannelmonitorFactoryName);
			Ext.getCmp("factory_code")
					.setValue(staticChannelmonitorFactoryCode);
			//设置异常类型 默认为空		
			Ext.getCmp("rt_tmnl_type").setValue("");

			storeTr.baseParams = {
				factoryCode : Ext.getCmp("factory_code").getValue(),
				isOnline : pn,
				tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
				nodeType : Ext.getCmp("tmnlRS_ex_nodeType").getValue()
			}
			storeTr.load();
		}
		
		//added by 姜炜超 for 重点户监测数据------------start
		if((typeof(vipConsMonitorData_Flag) != 'undefined') && vipConsMonitorData_Flag){
			vipConsMonitorData_Flag = false;
			Ext.getCmp("tmnlRS_nodeType").setValue('usr');
			Ext.getCmp("tmnlRS_orgName").setValue(vipConsMonitorData_consName);
			Ext.getCmp("tmnlRS_tmnlAssetNo").setValue(vipConsMonitorData_consNo);
			
			Ext.getCmp("tmnlRS_ex_nodeType").setValue('usr');
			Ext.getCmp("tmnlRS_ex_orgName").setValue(vipConsMonitorData_consName);
			Ext.getCmp("tmnlRS_ex_tmnlAssetNo").setValue(vipConsMonitorData_consNo);
			
			storeTr.baseParams = {
					consNo : Ext.getCmp("tmnlRS_ex_tmnlAssetNo").getValue(),
					tmnlTypeCode : Ext.getCmp("rt_tmnl_type").getValue(),
					isOnline :pn,
					nodeType : 'usr'
			};
			storeTr.load();
		}
		//added by 姜炜超 for 重点户监测数据------------end
	});
	
	//added by wushaoxiong for 异常监测------------start
	function linkedByExceptionMonitor(){
		
		Ext.Ajax.request({
    	url:'./runman/mainExceptionAnalysis!queryOrgName.action',
    	params:{
    		orgNo:userInfoOrgNo
    	},
    	success:function(response)
    	{
    		var o=Ext.decode(response.responseText);
    		Ext.getCmp("tmnlRS_orgName").setValue(o.orgName);
    		
    	}
    })
		
	    Ext.getCmp("trsRadio").setValue("radio_01",true);
	    panel_low.layout.setActiveItem(0);
	    Ext.getCmp('ex_tmnl_type').setValue(tmnlExceptionCategory);
	    Ext.getCmp('timeS_tmnlRunE').setValue(new Date().add(Date.DAY, -30));
	    Ext.getCmp('timeE_tmnlRunE').setValue(new Date());
	    Ext.getCmp('tmnlRS_orgNo').setValue(userInfoOrgNo);
	    Ext.getCmp("tmnlRS_nodeType").setValue('org');
		Ext.getCmp("tmnlRS_orgType").setValue('03');
        	    
	    storeTe.baseParams = {
			orgNo : Ext.getCmp('tmnlRS_orgNo').getValue(),
			orgType : '03',
			tmnlTypeCode : Ext.getCmp('ex_tmnl_type').getValue(),
			nodeType : 'org',
			startDate : Ext.getCmp('timeS_tmnlRunE').getValue(),
			endDate : Ext.getCmp('timeE_tmnlRunE').getValue()
		};
		storeTe.load();
	    
	}
	if((typeof(tmnlExceptionCategory)!='undefined')&&!Ext.isEmpty(tmnlExceptionCategory)){
		linkedByExceptionMonitor();
	}
	tmnlExceptionCategory = undefined;
	Ext.getCmp('终端设备运行状态').on('activate', function() {
	    linkedByExceptionMonitor();
	});
	//added by wushaoxiong for 异常监测------------end

});

// 打开数据采集综合分析
function openTab_cjsjfx(consNo) {
	consNo_terminalRunStatus = consNo;
	openTab("采集数据综合分析", "./qryStat/collDataAnalyse/generalAnalyse.jsp");

}

function getCheckBoxList(){
	//获得复选框值
			var pn = new Array();
			var checkValue = Ext.getCmp('is_online_check_box').getValue(); 
			if (checkValue.length > 0) {
				for (var i = 0; i < checkValue.length; i++) {
					pn[i] = checkValue[i].inputValue;
				}
			}
			return pn;
}
