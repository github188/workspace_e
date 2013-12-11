/*
 * ! Author: Longkongcao Date:11/27/2009 Description: 用户余额查看 Update History:
 * none
 */
Ext.onReady(function() {

	var imRadioGroup = new Ext.form.RadioGroup({
				fieldLabel : '监测接口',
				width : 300,
				id : 'imRadioGroup',
				columns : [100, 80, 80],
				items : [new Ext.form.Radio({
									boxLabel : '营销业务应用',
									name : 'im-radioGroup',
									inputValue : '01',
									checked : true,
									listeners : {
										'check' : function(r, c) {
											if (c) {
											};
										}
									}
								}), new Ext.form.Radio({
									boxLabel : '数据中心',
									name : 'im-radioGroup',
									inputValue : '02',
									listeners : {
										'check' : function(r, c) {
											if (c) {
											};
										}
									}
								}), new Ext.form.Radio({
									boxLabel : '调度系统',
									name : 'im-radioGroup',
									inputValue : '03',
									listeners : {
										'check' : function(r, c) {
											if (c) {
											};
										}
									}
								})]
			});

			
// 复选框选择接口业务类型
	var interfaceTypeCheck = new Ext.form.CheckboxGroup({
				id : 'interface_check_box',
				width : 500,
				columns : [70, 70, 90, 60, 70, 70, 70],
				// fieldLabel:'接口业务类型',
				items : [{
							boxLabel : '终端安装',
							inputValue : '01',
							name : 'interface_type_01'
						}, {
							boxLabel : '终端调式',
							inputValue : '02',
							name : 'interface_type_02'
						}, {
							boxLabel : '有序用电方案',
							inputValue : '03',
							name : 'interface_type_03'
						}, {
							boxLabel : '预购电',
							inputValue : '04',
							name : 'interface_type_04'
						}, {
							boxLabel : '营业报停',
							inputValue : '05',
							name : 'interface_type_05'
						}, {
							boxLabel : '催费控制',
							inputValue : '06',
							name : 'interface_type_06'
						}, {
							boxLabel : '远程抄表',
							inputValue : '07',
							name : 'interface_type_07'
						}]
			});

	// 接口类型
var interfaceStore = new Ext.data.ArrayStore({
			fields : ['interfaceTypeCode', 'interfaceTypeName'],
			data : [["01", "终端安装"], ["02", "终端调式"], ["03", "有序用电方案"],
					["04", "预购电"], ["05", "营业报停"], ["06", "催费控制"],
					["07", "远程抄表"]]
		})
		
// 接口业务类
var interfaceCombobox = new Ext.form.ComboBox({
			id : 'interface_type',
			fieldLabel : '接口的业务类',
			store : interfaceStore,
			triggerAction : 'all',
			editable : true,
			mode : 'local',
			valueField : 'interfaceTypeCode',
			displayField : 'interfaceTypeName',
			anchor : '90%',
			value : '01',
			emptyText : '接口的业务类型',
			selectOnFocus : true
		})
	

	var abPanel = new Ext.Panel({
		border : false,	
		height : 35,
		labelAlign : 'right',
		region : 'north',
		layout : 'table',
//		layoutConfig : {
//			columns : 5
//		},
		items : [ {
					layout : 'form',
					border : false,
					labelWidth : 50,
					width : 300,
					items : [imRadioGroup]
				},{
					layout : 'column',
					border : false,
					labelWidth : 50,
					width : 320,
					items : [{
								columnWidth : .6,
								layout : "form",
								labelWidth : 70,
								baseCls : "x-plain",

								items : [{
											id : "interface_dateFrom",
											xtype : "datefield",
											format : "Y-m-d",
											fieldLabel : "接口日期从",
											value : new Date().add(Date.DAY, -6),
											labelStyle : "text-align:right;width:50;",
											labelSeparator : ""
										}]
							}, {
								columnWidth : .4,
								layout : "form",
								labelWidth : 15,
								baseCls : "x-plain",
								items : [{
											id : "interface_dateTo",
											xtype : "datefield",
											format : "Y-m-d",
											fieldLabel : "到",
											value : new Date(),
											labelStyle : "text-align:right;width:50;",
											labelSeparator : ""
										}]
							}]
				},
				{
					layout : 'form',
					border : false,
					labelWidth : 15,
					width : 90,
					items : [{
								xtype : 'button',
								width : 70,
								text : '查询',
								handler : queryMonitorData
							}]
				}
				]
	});
	//查询参数设置
	function queryMonitorData() {
		if (!checkQueryData()) {
			return;
		}
		//复选框值
//		var pn = getCheckBoxList();
//		if(pn == null){
//			Ext.MessageBox.alert("提示", "请选择接口业务类型！");
//			return;
//		}
		var myMask = new Ext.LoadMask(abFormPanel.getId(), {
					msg : "加载中..."
				});
		myMask.show();
		
		store.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
			startDate : Ext.getCmp('interface_dateFrom').getValue(),
			// 检索结束时间
			endDate : Ext.getCmp('interface_dateTo').getValue(),
			// 工单状态
			monitorType : imRadioGroup.getValue().inputValue
		}
		store.load();
		store.on('load', function() {
					myMask.hide();
				});
		store.on('loadexception', function() {
					myMask.hide();
				})
	}
	
		// 校验查询条件
	function checkQueryData() {
		var start =  Ext.getCmp('interface_dateFrom').getValue();
		var end = Ext.getCmp('interface_dateTo').getValue();
		if (Ext.isEmpty(start)) {
			Ext.MessageBox.alert("提示", "请输入开始日期！");
			return false;
		}
		if (Ext.isEmpty(end)) {
			Ext.MessageBox.alert("提示", "请输入结束日期！");
			return false;
		}
		if ((start - end) > 0) {
			Ext.MessageBox.alert("提示", "开始时间应早于结束时间！");
			return false;
		}
		return true;
	}
	
	//多选组合panel
	var groupbox =  new Ext.Panel({
		border : false,
		labelAlign : 'left',
		labelWidth : 10,
		fieldLabel : '接口业务类型',
//		height : 30,
//		region : 'center',
		layout : 'fit',
		items : [interfaceTypeCheck]
	});
	//页面查询约束pannel
	var topPanel = new Ext.Panel({
		region : 'north',
		border : false,
		height : 30,
		layout : 'form',
		items : [abPanel/*,groupbox*/]
	})

	//接口查询列表store
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : './baseapp/interfaceMonitor!queryInterfaceMonitor.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'interfaceMonitorList',
							totalProperty : 'totalCount'
						}, [{
									name : 'interfaceType'
								}, {
									name : 'interfaceName'
								}, {
									name : 'sysNo'
								}, {
									name : 'content'
								}, {
									name : 'opTime'
								}, {
									name : 'respId'
								}, {
									name : 'logId'
								}, {
									name : 'errNo'
								}])
			});

	/**
	 * 接口查询列表Grid
	 */
	var grid = new Ext.grid.GridPanel({
				title : '接口监测',
				store : store,
				height : 320,
				region : 'center',
				// layout : 'fit',
				columns : [{
							header : "标志ID",
							// width : 100,
							dataIndex : 'logId',
							sortable : true
						},{
							header : "接口类型",
							// width : 120,
							dataIndex : 'interfaceType',
							sortable : true
						}, {
							header : "接口名称",
							// width : 180,
							dataIndex : 'interfaceName',
							sortable : true
						}, {
					header : "外围系统编码",
					// width : 115,
					dataIndex : 'sysNo',
					sortable : true,
					align : 'center',
					renderer : function(val) {
						if (val == '01') {
							val = "营销";
						}else if(val == '02'){
							val = "现场系统";
						}else if(val == '03'){
							val = "单点登录";
						}
						var html = '<div align = "left" ext:qtitle="资产编号" ext:qtip="'
								+ val + '">' + val + '</div>';
						return html;
					}
				}, {
							header : "接口内容",
							// width : 100,
							dataIndex : 'content',
							sortable : true
						}, {
							header : "接口交互时间",
							// width : 100,
							dataIndex : 'opTime',
							sortable : true
						}, {
							header : "反馈结果",
							// width : 100,
							dataIndex : 'respId',
							sortable : true
						}
//						,  {
//							header : '目前电量',
//							// id : 'mqdl',
//							// width : 100,
//							dataIndex : 'mqdl',
//							sortable : true
//						}, {
//							header : "剩余百分比",
//							// width : 100,
//							dataIndex : 'sybfb',
//							sortable : true
//						}
						],
				bbar : new Ext.ux.MyToolbar({
							store : store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
//加载数据
//	store.load();

	//接口监视主pannel
	var abFormPanel = new Ext.form.FormPanel({
				title : '查询条件',
				border : false,
				layout : 'border',
				autoScroll : true,
				items : [topPanel, grid]
			});
	renderModel(abFormPanel, '接口执行监测');
});


//解析复选框值
function getCheckBoxList(){
	//获得复选框值
			var pn = new Array();
			var checkValue = Ext.getCmp('interface_check_box').getValue();
			if (checkValue.length > 0) {
				for (var i = 0; i < checkValue.length; i++) {
					pn[i] = checkValue[i].inputValue;
				}
			}else {
				return null;
			}
			return pn;
}