
// SIM卡流量 -----------------------------------------------------------------
function getSimMessage(){
	var SIM_NO = Ext.getCmp("SIM_NO")
												.getValue();
										if (SIM_NO != null) {
											simStore.baseParams = {
												SIM_NO : SIM_NO
											};
											simStore.load({
														params : {
															start : 0,
															limit : DEFAULT_PAGE_SIZE
														}
													});
										};
}
var stpaneltop = new Ext.Panel({// 查询条件的panel
	height : 35,
	plain : true,
	items : [{
		baseCls : "x-plain",
		layout : "column",
		style : "padding:5px",
		items : [{
					columnWidth : .3,// -------------------
					layout : "form",
					labelStyle : "text-align:right;width:80;",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
						xtype : "datefield",
						format : "Y-m-d",
						value : new Date().getFirstDateOfMonth(),
						fieldLabel : "计费日期从",
						id : 'simdateStart',
						labelStyle : "text-align:right;width:80;"// ,
							// labelSeparator : "",
							// width : 100
						}]
				}, {
					columnWidth : .3,// ------------
					layout : "form",
					labelWidth : 10,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
						xtype : "datefield",
						format : "Y-m-d",
						value :new Date().add(Date.DAY, -1),
						fieldLabel : "到",
						id : 'simdateEnd',
						labelStyle : "text-align:right;width:10;"// ,
							// labelSeparator : "",
							// width : 100
						}]
				}, {
					columnWidth : .075,// ------------------
					layout : "form",
					defaultType : "button",
					baseCls : "x-plain",
					items : [{
								text : "查询",
								width : 50,
								listeners : {
									"click" : function() {
										 getSimMessage();
									}
								}
							}]
				}]
	}]
});
// simTrafficGrid-----------------------------------------------------------------------------------
var simStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/ccustAction!querySIM.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'simList',
						totalProperty : 'totalCount'
					}, [{
								name : 'simNo'
							}, {
								name : 'gprsCode'
							}, {
								name : 'chargeDate'
							}, {
								name : 'overrunFlux'
							}, {
								name : 'actualFlux'
							}, {
								name : 'commCharge'
							}])
		});
var simTrafficGrid = new Ext.grid.GridPanel({
			title : 'SIM卡流量',
			height : 280,
			stripeRows : true,
			autoScroll : true,
			enableColumnMove : false,// 设置不可表头不可拖动
			colModel : new Ext.grid.ColumnModel([{
						header : "序号",
						menuDisabled : true,
						align : "center"
					},// 使下拉框消失
					{
						header : "SIM卡号",
						dataIndex : 'simNo',
						menuDisabled : true,
						align : "center"
					},// 设置标签文字居中
					{
						header : "GPRS号码",
						dataIndex : 'gprsCode',
						menuDisabled : true,
						align : "center"
					}, {
						header : "费用日期",
						dataIndex : 'chargeDate',
						menuDisabled : true,
						align : "center"
					}, {
						header : "安装日期",
						// dataIndex : '',
						menuDisabled : true,
						align : "center"
					}, {
						header : "超流量",
						dataIndex : 'overrunFlux',
						menuDisabled : true,
						align : "center"
					}, {
						header : "实际流量",
						dataIndex : 'actualFlux',
						menuDisabled : true,
						align : "center"
					}, {
						header : "通讯费【元】",
						dataIndex : 'commCharge',
						menuDisabled : true,
						align : "center"
					}]),
			ds : simStore,
			bbar : new Ext.ux.MyToolbar({
						store : simStore
					})
		});
// SIM卡流量window--------------------------
var simTrafficWindow = new Ext.Window({
			title:"SIM卡流量信息",
			frame : true,
			width : 800,
			height : 350,
			layout : "form",
			modal : true,
			closeAction : "hide",
			// labelWidth:45,
			plain : true,// 设置背景颜色
			resizable : false,// 不可移动
			bodyStyle : "padding:2px",
			draggable:false,
			buttonAlign : "center",// 按钮的位置
			// closable:false,//设置窗体关闭按钮
			closeAction : "hide",// 将窗体隐藏而并不销毁
			items : [stpaneltop, simTrafficGrid]
		});
function simTrafficWindowShow() {
	simTrafficWindow.show();
	 getSimMessage();
}