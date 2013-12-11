/**
 * 模块：消息订阅
 * 
 * @author 李春明
 * @version 2010年5月20日10:42:00
 */

//开始日期
var msg_startDate = new Ext.Panel({
			border : false,
			layout : 'form',
			labelWidth : 80,
			labelAlign : 'right',
			bodyStyle : 'padding: 10px 0px 5px 0px ',
			items : [new Ext.form.DateField({
						id : 'msg_startDate',
						fieldLabel : '订阅日期从',
						name : 'msg_startDate',
						labelStyle : "text-align:right;width:80px ;",
						width : 100,
						format : 'Y-m-d',
						allowBlank : false,
						value : new Date().add(Date.DAY, -6)
					})]
		});

//结束日期
var msg_endDate = new Ext.Panel({
			border : false,
			layout : 'form',
			labelWidth : 80,
			labelAlign : 'right',
			bodyStyle : 'padding: 5px 0px 10px 0px ',
			items : [new Ext.form.DateField({
						id : 'msg_endDate',
						fieldLabel : '到',
						name : 'msg_endDate',
						labelStyle : "text-align:right;width:80px;",
						width : 100,
						format : 'Y-m-d',
						allowBlank : false,
						value : new Date()
					})]
		})

var buttonPanel = new Ext.Panel({
			border : false,
			layout : 'table',
			layoutConfig : {
				columns : 2
			},
			bodyStyle : 'padding: 10px 0px 10px 20px',
			items : [{
						layout : 'form',
						border : false,
						width : 100,
						items : [{
									xtype : 'button',
									width : 70,
									text : '保存订阅设置'
								}]
					}, {
						layout : 'form',
						border : false,
						width : 100,
						items : [{
									xtype : 'button',
									width : 70,
									text : '删除选中用户'
								}]
					}]

		})

var date_button_Panel = new Ext.Panel({
			layout : "form",
			columnWidth : .3,
			autoScroll : true,
			border : false,
			items : [{
						border : false,
						layout : 'table',
						layoutConfig : {
							columns : 1
						},
						items : [msg_startDate, msg_endDate, buttonPanel]
					}]
		});

/**
 * 面板头部多选框
 */
		//设置选择事件 是否多选
	var smNot = new Ext.grid.CheckboxSelectionModel({
					singleSelect : true
				});
var msgNotCM = new Ext.grid.ColumnModel([{
			header : '可订阅项',
			dataIndex : 'msgName',
			align : 'left',
			width : 150,
			//隐藏列表名称
			menuDisabled : true
		}
//		,{
//			header : '订阅项编码',
//			dataIndex : 'msgCode',
//			align : 'left',
//			width : 50,
//			//隐藏列表名称
//			menuDisabled : true
//		}
		]);

var doMsgNotStor = new Ext.data.ArrayStore({
			fields : ['msgCode', 'msgName'],
			data : [['1', "预付费成功消息通知"], ['2', "远程拉合闸消息通知"], ['3', "终端异常消息通知"],
					['4', "主站异常消息通知"], ['5', "催费控制消息通知"], ['6', "营销报停控制消息通知"],
					['7', "电量定值控制消息通知"]],
			sortInfo : {
				field : 'msgCode',
				direction : 'ASC'
			}
		});

var msgNotGridPanel = new Ext.grid.GridPanel({
			border : false,
			hideHeaders : true,
			stripeRows : true,
			title : '可订阅项',
			id : "msg_top_left_grid",
			store : doMsgNotStor,
			sm : smNot,
			cm : msgNotCM,
			listeners : {
						'dblclick' : addMessage
					}
	
		});
		
//包装已订阅项目选择的列表	
var msgNotPack = new Ext.Panel({
			border : true,
			layout : "fit",
			columnWidth : .3,
			height : 170,
			items : [msgNotGridPanel]
		});
//设置选择事件 是否多选
var smYet = new Ext.grid.CheckboxSelectionModel({
					singleSelect : false
				});
var msgYetCM = new Ext.grid.ColumnModel([{
								header : '已订阅项',
			dataIndex : 'msgName',
			align : 'left',
			width : 150,
			menuDisabled : true
		}
//		,{
//			header : '订阅项编码',
//			dataIndex : 'msgCode',
//			align : 'left',
//			width : 50,
//			//隐藏列表名称
//			menuDisabled : true
//		}
		]);

var msgYetStor = new Ext.data.ArrayStore({
			fields : [{
						name : 'msgName'
					}, {
						name : 'msgCode'
					}]
		});
var msgYetGridPanel = new Ext.grid.GridPanel({
			border : false,
			hideHeaders : true,
			stripeRows : true,
			title : '已订阅项',
			id : "msg_top_right_grid",
			store : msgYetStor,
			sm : smYet,
			cm : msgYetCM
		});
// 包装未订阅项目选择的列表
var msgYetPack = new Ext.Panel({
			border : true,
			layout : "fit",
			columnWidth : .3,
			height : 170,
			items : [msgYetGridPanel]
		});

/*
 * 消息订阅页面头部多选框以及其他组件
 */
var msgTopPanel = new Ext.Panel({
			border : false,
			autoScroll : true,
			region : 'north',
			monitorSize : true,
			height : 180,
			layout : 'column',
			items : [msgNotPack, new Ext.Panel({
								columnWidth : .02,
								border : false,
								width : 10,
								height : 170
							}), msgYetPack, date_button_Panel]
		});
		
//添加信息订阅
		function addMessage(){
		var notMsg = msgNotGridPanel.getSelectionModel().getSelected();
		var yetMsg = msgYetGridPanel.getSelectionModel();
		if(yetMsg != 'undefined' && notMsg != 'undefined'){
			var msgObj = new Object();
			msgObj.msgName = notMsg.get("msgName");
			msgObj.msgCode = notMsg.get("msgCode");
			var msgRecord = new Ext.data.Record(msgObj);
			msgYetStor.add(msgRecord);
		}
	}

// 订阅消息面板

// 用电用户列表
// var userElecRM = new Ext.grid.RowNumberer();
// var msgSM = new Ext.grid.CheckboxSelectionModel({
// singleSelect : false
// });
var userElecColumn = new Ext.grid.ColumnModel([{
			header : "供电单位",
			sortable : true,
			resizable : true,
			dataIndex : "orgName",
			width : 100
		}, {
			header : "用户编号",
			sortable : true,
			resizable : true,
			dataIndex : "consNo",
			width : 100
		}, {
			header : "用户名称",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "负荷性质",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "重要性等级",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "联系人",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "联系电话",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "安装/运行容量",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "所属行业",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "用电类型",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "电压等级",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "用电地址",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}]

);

var userElecStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './sysman/gatherTaskTemplate!queryUser.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'userElecList',
						totalProperty : 'totalCount'
					}, [{
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}])
		});

// 系统用电用户列表
//	var sysUserRM = new Ext.grid.RowNumberer();
var sysUserColumn = new Ext.grid.ColumnModel([{
			header : "供电单位",
			sortable : true,
			resizable : true,
			dataIndex : "orgName",
			width : 100
		}, {
			header : "用户编号",
			sortable : true,
			resizable : true,
			dataIndex : "consNo",
			width : 100
		}, {
			header : "用户名称",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "联系人",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "联系电话",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "订阅开始日",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "订阅结束日",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}, {
			header : "订阅内容",
			sortable : true,
			resizable : true,
			dataIndex : "",
			width : 100
		}]

);

var sysUserStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : './sysman/gatherTaskTemplate!queryUser.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'sysUserList',
						totalProperty : 'totalCount'
					}, [{
								name : ''
							}, {
								name : 'name'
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}, {
								name : ''
							}])
		});

var userToolbar = new Ext.Toolbar([{
			text : "删除选中行"
		}, {
			text : "增加新用户"
		}, {
			text : "删除成功用户"
		}]);
var sysUserToolbar = new Ext.Toolbar([{
			text : "删除选中行"
		}, {
			text : "增加新用户"
		}, {
			text : "删除成功用户"
		}]);

var sysUserGrid = new Ext.grid.GridPanel({
			title : '系统用户',
			cm : sysUserColumn,
			layout : 'fit',
			//				sm : userElecRM,
			ds : sysUserStore,
			tbar : sysUserToolbar,
			autoScroll : true
		});

var userElecGrid = new Ext.grid.GridPanel({
			title : '用电用户',
			cm : userElecColumn,
			layout : 'fit',
			//				sm : userElecRM,
			ds : userElecStore,
			tbar : userToolbar,
			autoScroll : true
		});

var userTabPanel = new Ext.TabPanel({
	region : 'center',
	activeTab : 0,
	monitorSize : true,
	items : [userElecGrid, sysUserGrid]

		//                doLayout : function() {                    
		//                    Ext.Panel.prototype.doLayout.call(this);
		//                }
	});

// 渲染到主页面
var mainPanel = new Ext.Panel({
			layout : "border",
			autoScroll : true,
			monitorSize : true,
			items : [msgTopPanel, userTabPanel]

		});
// --以上为界面构造-------//
Ext.onReady(function() {
			renderModel(mainPanel, '消息订阅');
		});
