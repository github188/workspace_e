/**
 * 远程调试页面
 **/

//远程调试列表Store
var remoteDebugStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : './runman/remoteDebug!queryRemoteDebugList.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'remotedebuglist',
		totalProperty : 'totalCount'
	}, [{name:'datakey'},
	    {name:'orgName'},{name:'consNo'},{name:'consName'},
		{name:'elecAddr'},{name:'mrSect_no'},{name:'mpName'},
		{name:'usageTypeName'},{name:'cisAssetNo'},{name:'terminalAddr'},
		{name:'comm_mode'},{name:'protocolName'},{name:'mpSn'},
		{name:'currz'},{name:'currf'},{name:'currp'},
		{name:'currg'},{name:'fmrAssetNo'},{name:'assetNo'},
		{name:'commAddr1'},{name:'tfactor'},{name:'tmnlAssetNo'},
		{name:'coll_mode'},{name:'protocol_code'}
	  ]
	)
});

var as_gridSm = new Ext.grid.CheckboxSelectionModel();
var remoteDebugPanel = new Ext.grid.GridPanel({
	title:'远程调试',
	store:remoteDebugStore,
	layout:'fit',
	sm : as_gridSm,
	viewConfig : {forceFit : false},
	columns:[as_gridSm,
			{header: '供电单位',dataindex:'orgName'},
			{header:'用户编号',dataindex:'consNo'},
			{header: '用户名称',dataindex:'consName'},
			{header: '用电地址',dataindex:'elecAddr'},
			{header: '抄表段',dataindex:'mrSect_no'},
			{header: '计量点名称',dataindex:'mpName'},
			{header: '计量点类型',dataindex:'usageTypeName'},
			{header: '终端资产号',dataindex:'cisAssetNo'},
			{header: '终端地址',dataindex:'terminalAddr'},
			{header: '通信方式',dataindex:'comm_mode'},
			{header: '通信规约',dataindex:'protocolName'},
			{header: "测量点序号",dataindex:'mpSn'},
			{header: "示数(总)",dataindex:'currz'},
			{header: "示数(峰)",dataindex:'currf'},
			{header: "示数(平)",dataindex:'currp'},
			{header: "示数(谷)",dataindex:'currg'},
			{header: "采集器编码",dataindex:'fmrAssetNo'},
			{header: "电表资产",dataindex:'assetNo'},
			{header: "表地址",dataindex:'commAddr1'},
			{header: "倍率",dataindex:'tfactor'}/*,
			{header:'终端资产号',dataindex:'tmnlAssetNo'},
			{header:'coll_mode',dataindex:'coll_mode'},
			{header:'protocol_code',dataindex:'protocol_code'}*/
	 	],
		bbar : new Ext.ux.MyToolbar({
			id:'mytobar',
			store : remoteDebugStore
		})
});

function recsToArray(recs) {
	var data = new Array();
	for (var i = 0; i < recs.length; i++) {
		data[i] = new Array();

		data[i][0] = recs[i].get('orgName');
		data[i][1] = recs[i].get('consNo');
		data[i][2] = recs[i].get('consName');
		data[i][3] = recs[i].get('elecAddr');
		data[i][4] = recs[i].get('mrSect_no');
		data[i][5] = '';
		data[i][6] = '';
		data[i][7] = '';
		data[i][8] = '';
	}
	return data;
};

/**
 * 发起调试请求
 * @return
 */
var remoteDebug = function(){
	var regSnArray = new Array();
	var tmnlAssetNo = '';

	var recs;
	recs = as_gridSm.getSelections();
	
	if (recs == null || recs.length < 1) {
		Ext.Msg.alert('', "请选择用户");
		return true;
	}
	as_resultStore.loadData(recsToArray(recs));
	as_resultWindow.show();

	/*tmnlAssetNo = recs[0].data.tmnlAssetNo;
	ov = true;
	for (var i = 0; i < recs.length; i = i + 1) {
		regSnArray[i] = recs[i].data.regSn;
	}
	getData(tmnlAssetNo, regSnArray, 0);*/
	//Ext.Msg.alert("调试功能");
};

//弹出Window中的GridPanel所使用的Store
var as_resultStore = new Ext.data.Store({
	proxy : new Ext.data.MemoryProxy(),
	reader : new Ext.data.ArrayReader({
		idIndex : 8,
		fields : [{
					name : 'orgName'
				}, {
					name : 'consNo'
				}, {
					name : 'consName'
				}, {
					name : 'elecAddr'
				}, {
					name : 'mrSect_no'
				}, {
					name : 'currz'
				}, {
					name : 'currf'
				}, {
					name : 'currp'
				}, {
					name : 'currg'
				}]
	})
});

var as_resultGrid = new Ext.grid.GridPanel({
	columns:[
		{header: "供电单位",dataindex:'orgName'},
		{header:'用户编号',dataindex:'consNo'},
		{header: "用户名称",dataindex:'consName'},
		{header: "用电地址",dataindex:'elecAddr'},
		{header: "抄表段",dataindex:'mrSect_no'},
		{header: "示数(总)",dataindex:'currz'},
		{header: "示数(峰)",dataindex:'currf'},
		{header: "示数(平)",dataindex:'currp'},
		{header: "示数(谷)",dataindex:'currg'}
	 ],
	store : as_resultStore,
	stripeRows : true,
	autoWidth : true,
	autoScroll : true,
	tbar : [{
				xtype : 'label',
				id : 'tbarLabel',
				html : "<font color='red'>任务执行中...</font>"
			}]
});

var as_resultWindow = new Ext.Window({
	title : '远程调试窗口',
	frame : true,
	width : 750,
	height : 450,
	layout : "fit",
	autoScroll : true,
	modal : true,
	plain : true, // 设置背景颜色
	resizable : false, // 不可移动
	buttonAlign : "center", // 按钮的位置
	closeAction : "hide", // 将窗体隐藏而并不销毁
	items:[as_resultGrid],
	buttons : [{
		text : '停止抄表',
		handler : function() {
			ov = false;
			Ext.getCmp('tbarLabel').setText(
					"<font color='red'>任务执行中止</font>", false);
		}
	}, {
		text : '关闭',
		handler : function() {
			as_resultWindow.hide();
		}
	}]
});

remoteDebugPanel.on("show",function(){
	remoteDebugStore.baseParams={
		cpNo:selectCpNo
	};
	remoteDebugStore.load({params:{
		cpNo:selectCpNo,
		start:0,
		limit:50
	}});
});