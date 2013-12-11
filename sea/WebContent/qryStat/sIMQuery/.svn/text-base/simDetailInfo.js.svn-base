//定义FusionCharts panel的高度宽度
var width = 0;
//查询SIM卡流量信息
function querySimDetailInfo(){
	var simNo = Ext.getCmp('simDetailInfo_simNo').getValue();
	var dateFrom = Ext.getCmp('simDetailInfo_dataFrom').getValue();
	var dateTo = Ext.getCmp('simDetailInfo_dataTo').getValue();
	
	groupStore.baseParams = {
		'dateFrom' :dateFrom,
		'dateTo' :dateTo,
		'simNo' :simNo
	};
	groupStore.load();
}

function createFusionChartsXmlDate(){
	var num = width/85;
	var xmlDate = '<chart formatNumberScale="0" lineThickness="2" showValues="0" anchorRadius="3" ' +
			'anchorBgAlpha="50" showAlternateVGridColor="1" numVisiblePlot="'+num +'" animation="0">';
	var lebelStr = '<categories>';
	groupStore.each(function(obj){//循环数组，设置lebel
		lebelStr += '<category label="' + obj.get('flowDate').substring(0,10) +'" />';
	});
	
	lebelStr += '</categories>';
	var valueStr = '<dataset color="AFD8F8" showValues="0">';
	groupStore.each(function(obj){//循环数组，设置值
		valueStr += '<set value="' + obj.get('allFlow') +'" />';
	});
	valueStr += '</dataset>';
	xmlDate = xmlDate + lebelStr +valueStr +'</chart>';
	return xmlDate;
}

	// SIM流量信息-----------------------------------------------------------------
	var simDetaiInfo1 = new Ext.Panel({
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding:5px 10px",
			items : [{
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 60,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "SIM卡号",
									id : 'simDetailInfo_simNo',
									allowBlank : false,
									labelStyle : "text-align:right;width:40;",
									labelSeparator : "",
									width : 120
								}]
					},{
					columnWidth : .25,// ----------------------
					layout : "form",
					labelWidth : 75,
					baseCls : "x-plain",
					items : [{
						xtype : "datefield",
						format : "Y-m-d",
						id:'simDetailInfo_dataFrom',
						fieldLabel : "查询日期从",
						value:new Date().add(Date.MONTH, -1),
						labelStyle : "text-align:right;width:50;",
						labelSeparator : ""
						}]
				},{
					columnWidth : .2,// ----------------------
					layout : "form",
					labelWidth : 10,
					baseCls : "x-plain",
					items : [{
						xtype : "datefield",
						format : "Y-m-d",
						id:'simDetailInfo_dataTo',
						fieldLabel : "到",
						value:new Date().add(Date.DAY, -1),
						labelStyle : "text-align:right;width:50;",
						labelSeparator : ""
						}]
				}, {
						columnWidth : .1,// --------------------------
						layout : "form",
						defaultType : "button",
						baseCls : "x-plain",
						items : [{
							text : "查询",
							listeners : {
								"click" : function() {
									var simNo = Ext.getCmp('simDetailInfo_simNo');
									if(!simNo.isValid(true)){
										Ext.MessageBox.alert("提示","SIM卡号不能为空");
										return ;
									}
									querySimDetailInfo();
								}
							},
							width : 50
						}]
					}]
		}]
	});
	// -----------------------------------------------------------------------------
	//SIM流量信息panel31
	var simDetaiInfo31 = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : true
			});

	// SIM流量信息panel3
	var simDetaiInfo3 = new Ext.Panel({
				border : false,
				bodyBorder : false,
				style : "padding-left:10px",
				items : [simDetaiInfo31]
			});
	// ------------------------------------------------------------------------------------------
	// SIM流量信息grid
	var groupStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url : 'qrystat/querySIMStat!querySimFlowInfo.action'
			}),
		reader : new Ext.data.JsonReader({
				root : 'simFlowList',
				totalProperty : 'totalCount'
			}, [{name:'simNo'},
				{name:'flowDate'},
				{name:'downFlow'},
				{name:'downMsg'},
				{name:'upFlow'},
				{name:'upMsg'},
				{name:'allFlow'}])
	});
//定义store的onload事件，渲染图形	
groupStore.on('load',function(){
	width = simDetaiInfo31.getWidth()-25;
	var simDetaiInfoChart1 = new FusionCharts("fusionCharts/ScrollLine2D.swf",
			"simDetaiInfoChartId", width, "255");
	simDetaiInfoChart1.setDataXML(createFusionChartsXmlDate());
	simDetaiInfoChart1.setTransparent(true);
	simDetaiInfoChart1.render(simDetaiInfo31.getId());// 渲染
});

	var groupCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), 
		{header : 'SIM卡号',dataIndex : 'simNo', sortable : true, hidden:true, align : 'center'},
		{header : '查询日期',dataIndex : 'flowDate', sortable : true, renderer : function(val) {
				return val.substring(0,10);
			},align : 'center'},
		{header : '下行流量',dataIndex : 'downFlow',sortable : true,align : 'center'}, 
		{header : '下行报文(帧)',dataIndex : 'downMsg',	sortable : true,align : 'center'}, 
		{header : '上行流量',dataIndex : 'upFlow',	sortable : true,align : 'center'}, 
		{header : '上行报文(帧)',dataIndex : 'upMsg',	sortable : true,align : 'center'}, 
		{header : '全部流量',dataIndex : 'allFlow',sortable : true,align : 'center'}
	]);

	var simDetaiInfo4 = new Ext.grid.GridPanel({
		autoScroll : true,
		stripeRows : true,
		region:'center',
		cm : groupCM,
		store : groupStore,
		bbar : new Ext.ux.MyToolbar({
					store : groupStore,
					enableExpAll : true, // excel导出全部数据
					expAllText : "全部",
					enableExpPage : true, // excel仅导出当前页
					expPageText : "当前页"
				})
	});
	
		var stpanel = new Ext.Panel({
		autoScroll : true,
		region:'north',
		height:300,
		border : false,
		items : [simDetaiInfo1, simDetaiInfo3]
	});
	
Ext.onReady(function() {
	// SIM流量信息顶层panel
	var simDetailInfo = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				border : false,
				items : [stpanel,simDetaiInfo4]
			});
	renderModel(simDetailInfo,'SIM卡流量信息');
	
	if(typeof(staticSimNo) != 'undefined'){
		Ext.getCmp("simDetailInfo_simNo").setValue(staticSimNo);
		querySimDetailInfo();
	}
	Ext.getCmp('SIM卡流量信息').on('activate', function() {
 		if(typeof(staticSimNo) != 'undefined'){
			Ext.getCmp("simDetailInfo_simNo").setValue(staticSimNo);
			querySimDetailInfo();
		}
	});
});