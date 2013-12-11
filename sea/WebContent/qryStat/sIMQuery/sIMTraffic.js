var width;//屏幕宽度
function createFusionChartsXmlDate(groupStore){
	var num = width/85;
	var xmlDate = '<chart formatNumberScale="0" lineThickness="2" showValues="0" anchorRadius="3" ' +
			'anchorBgAlpha="50" showAlternateVGridColor="1" numVisiblePlot="'+num +'" animation="0">';
	var lebelStr = '<categories>';
	groupStore.each(function(obj){//循环数组，设置lebel
		lebelStr += '<category label="' + obj.get('orgName') +'" />';
	});
	
	lebelStr += '</categories>';
	var valueStr1 = '<dataset seriesName="基准流量(M)"color="AFD8F8" showValues="0">';
	groupStore.each(function(obj){//循环数组，设置值
		valueStr1 += '<set value="' + obj.get('baseFlow') +'" />';
	});
	valueStr1 += '</dataset>';
	
	var valueStr2 = '<dataset seriesName="超流量(M)" color="F6BD0F" showValues="0">';
	groupStore.each(function(obj){//循环数组，设置值
		valueStr2 += '<set value="' + obj.get('overFlow') +'" />';
	});
	valueStr2 += '</dataset>';
	xmlDate = xmlDate + lebelStr +valueStr1+ valueStr2 +'</chart>';
	return xmlDate;
}

function simTrafficFeePer(groupStore){
	var xmlData = '<chart formatNumberScale="0" palette="4" decimals="0" enableSmartLabels="1" ' +
			'enableRotation="0" bgColor="99CCFF,FFFFFF" bgAlpha="40,100" bgRatio="0,100" ' +
			'bgAngle="360" showBorder="1" startingAngle="70">';
	var str = '';		
	groupStore.each(function(obj){//循环数组，设置值
		var totalFee = obj.get('baseFee') + obj.get('overFee');
		str += '<set label="' + obj.get('orgName') + '" value="' + totalFee +'"/>';
	});
	xmlData = xmlData+str + '</chart>';
	return xmlData;
}

	// SIM卡超流量统计-----------------------------------------------------------------
	var sIMTraffic1 = new Ext.Panel({
		// title : "查询条件",
		// height : 100,
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding:5px",
			items : [{
								columnWidth : .23,// ------------------
								layout : "table",
								layoutConfig : { columns : 2 },
								//defaultType : "button",
								baseCls : "x-plain",
								items : [{xtype:'radiogroup',
								width:200,
								style : "padding-left:22px",
								items:[{
											xtype:'radio',
											boxLabel : '通信费占比',
											//style : "padding:5px",
											name : 'fav-color',
											inputValue : '饼图',
											id : 'sIMTrafficimage2',
											handler : function() {
												if (Ext.getCmp('sIMTrafficimage2').checked) {
													sIMTraffic31.hide();
													sIMTraffic32.show();
												}
											}
										},{
											xtype:'radio',
											checked : true,
											//style : "padding-left:20px",
											boxLabel : '通信流量',
											name : 'fav-color',
											inputValue : '柱状',
											id : 'sIMTrafficimage1',
											handler : function() {
												// 根据ID获取组件判断是否选中
												if (Ext.getCmp('sIMTrafficimage1').checked) {
													sIMTraffic32.hide();// 隐藏
													sIMTraffic31.show();// 显示
												}
											}
										}]}]
							},{
								columnWidth : .24,// ----------------------
								layout : "form",
								labelWidth : 50,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											fieldLabel : "节点名",
											labelStyle : "text-align:right;width:50;",
											id:'sIMTrafficOrgName',
											emptyText : '请选择统计地区...',
											readOnly:true,
											labelSeparator : "",
											width : 125
										},{xtype:'hidden',id:'sIMTrafficOrgNo'}]
							},{
								columnWidth : .24,// ----------------------
								layout : "form",
								labelWidth : 75,
								baseCls : "x-plain",
								items : [{
									xtype : "datefield",
									id:'simTraffic_dateFrom',
									format : "Y-m",
									editable:false,
									fieldLabel : "统计日期从",
									value:Date.parseDate((new Date().getFullYear()) + '-01', 'Y-m'),
									labelStyle : "text-align:right;width:50;",
									labelSeparator : ""
									}]
							},{
								columnWidth : .18,// ----------------------
								layout : "form",
								labelWidth : 15,
								baseCls : "x-plain",

								items : [{
									xtype : "datefield",
									format : "Y-m",
									id:'simTraffic_dateTo',
									fieldLabel : "到",
									editable:false,
									value:new Date(),
									labelStyle : "text-align:right;width:50;",
									labelSeparator : ""
									}]
							},{
						columnWidth : .1,// --------------------------
						layout : "form",
						defaultType : "button",
						baseCls : "x-plain",
						items : [{
									text : "查询",
									width : 50,
									handler:function(){
										var dateFrom = Ext.getCmp("simTraffic_dateFrom").getValue();
										var dateTo = Ext.getCmp("simTraffic_dateTo").getValue();
										var orgNo = Ext.getCmp("sIMTrafficOrgNo").getValue();
										if(dateFrom >dateTo) {
											Ext.MessageBox.alert("提示","开始时间不能大于结束时间！");
											return;
										}
										dateFrom = dateFrom.format('Y-m');
										dateTo = dateTo.add(Date.MONTH, 1).format('Y-m');
										groupStore.baseParams = {
											trafficDateFrom:dateFrom,
											trafficDateTo:dateTo,
											orgNo : orgNo
										}
										groupStore.load();
									}
								}]
					}]
		}]
	});
	
	Ext.getCmp("sIMTrafficOrgName").on('focus',function(){
		Ext.getCmp("sIMTrafficOrgName").setValue("");
		Ext.getCmp("sIMTrafficOrgNo").setValue("");
	});
	// -----------------------------------------------------------------------------
	//SIM卡超流量统计panel31
	var sIMTraffic31 = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : true
			});
//	var sIMTrafficChart1 = new FusionCharts("fusionCharts/MSColumn3D.swf",
//			"sIMTrafficChartId", "720", "250");
//	sIMTrafficChart1.setDataURL("./qryStat/sIMQuery/ms3dDate.xml");
//	sIMTrafficChart1.setTransparent(true);

	// SIM卡超流量统计panel22
	var sIMTraffic32 = new Ext.Panel({
				border : false,
				bodyBorder : false,
				layout : 'fit',
				monitorResize : true
			});
//	var sIMTrafficChart2 = new FusionCharts("fusionCharts/Pie3D.swf",
//			"sIMTrafficChartId", "720", "250");
//	sIMTrafficChart2.setDataURL("./qryStat/sIMQuery/pie3dDate2.xml");
//	sIMTrafficChart2.setTransparent(true);

	// SIM卡超流量统计panel3
	var sIMTraffic3 = new Ext.Panel({
				border : false,
				bodyBorder : false,
				style : "padding-left:20px",
				items : [sIMTraffic31, sIMTraffic32]
			});
	// ------------------------------------------------------------------------------------------
	// SIM卡超流量统计grid
	var groupStore = new Ext.data.Store({
        proxy : new Ext.data.HttpProxy({
					url:'./qrystat/simTraffic!querySimTraffic.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'simTrafficList',
					totalProperty : 'totalCount'
				}, [
				   {name : 'orgNo'},
				   {name : 'orgName'}, 
				   {name : 'flowDate'}, 
				   {name : 'downFlow'}, 
				   {name : 'downMsg'}, 
				   {name : 'upFlow'}, 
				   {name:'upMsg'},
				   {name:'allFlow'},
				    {name:'baseFlow'},
				   {name :'overFlow'}, 
				   {name :'baseFee'},
				   {name:'overFee'}
				   ])
		});
	groupStore.on('load',function(){
		if (Ext.getCmp('sIMTrafficimage1').checked) {//选中通信费占比
			sIMTraffic32.hide();// 隐藏
			sIMTraffic31.show();// 显示
			width = sIMTraffic31.getWidth()-25;
		}
		if (Ext.getCmp('sIMTrafficimage2').checked) {//选中通信流量
			sIMTraffic31.hide();
			sIMTraffic32.show();
			width = sIMTraffic32.getWidth()-25;
		}
			
		var simTrafficChart2 = new FusionCharts("fusionCharts/Pie3D.swf",
				"simTrafficChart2", width, "255");
		simTrafficChart2.setDataXML(simTrafficFeePer(groupStore));
		simTrafficChart2.setTransparent(true);
		simTrafficChart2.render(sIMTraffic32.getId());
		
		var simTrafficChart1 = new FusionCharts("fusionCharts/ScrollStackedColumn2D.swf",
				"simTrafficChart1", width, "255");
		simTrafficChart1.setDataXML(createFusionChartsXmlDate(groupStore));
		simTrafficChart1.setTransparent(true);
		simTrafficChart1.render(sIMTraffic31.getId());// 渲染
	});	
		
	var groupCM = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),{
				header : '单位编号',
				dataIndex : 'orgNo',
				sortable : true,
				hidden:true,
				align : 'center'
			}, {
				header : '供电单位',
				dataIndex : 'orgName',
				sortable : true,
				renderer : function(val) {
					var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">'
							+ val + '</div>';
					return html;
				},
				align : 'center'
			}, {
				header : '统计日期',
				dataIndex : 'flowDate',
				sortable : true,
				renderer : function(val) {
					var dateFrom = val.substring(0,7);
					var dateTo = val.substring(8);
					dateTo = Date.parseDate(dateTo,'Y-m').add(Date.MONTH, -1).format('Y-m');
					var dateBtween = dateFrom+'~' +dateTo;
					return '<div align = "right">'+ dateBtween + '</div>';
				},
				align : 'center'
			}, {
				header : '下行流量(M)',
				dataIndex : 'downFlow',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '下行报文(帧)',
				dataIndex : 'downMsg',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '上行流量(M)',
				dataIndex : 'upFlow',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '上行报文(帧)',
				dataIndex : 'upMsg',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '全部流量(M)',
				dataIndex : 'allFlow',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '基准流量(M)',
				dataIndex : 'baseFlow',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '超流量(M)',
				dataIndex : 'overFlow',
				sortable : true,
				renderer : function(s,m, rec) {
					return '<div align = "right">' +
						"<a href='javascript:'onclick='openOverTraffic(\""+rec.get('orgNo')+"\",\""+rec.get('orgName') +"\");" + "'>"+s +'</a></div>';
				},
				align : 'center'
			}, {
				header : '基准费(元)',
				dataIndex : 'baseFee',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}, {
				header : '超费用(元)',
				dataIndex : 'overFee',
				sortable : true,
				renderer : function(val) {
					return '<div align = "right">'+ val + '</div>';
				},
				align : 'center'
			}]);

	var sIMTraffic4 = new Ext.grid.GridPanel({
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
				items : [sIMTraffic1, sIMTraffic3]
			});
Ext.onReady(function() {			
	// SIM卡超流量统计顶层panel
	var sIMTrafficpanel = new Ext.Panel({
				autoScroll : true,
				layout:'border',
				border : false,
				items : [stpanel,sIMTraffic4]
			});
	renderModel(sIMTrafficpanel,'SIM卡流量分析');
//	sIMTrafficChart1.render(sIMTraffic31.getId());// 渲染
	
	var simLeftTreeListener = new LeftTreeListener({
		modelName : 'SIM卡流量分析',
		processClick : function(p, node, e) {
			if (node.isLeaf())
				return;
			var obj = node.attributes.attributes;
			var type = node.attributes.type;
			Ext.getCmp("sIMTrafficOrgName").setValue(obj.orgName);
			Ext.getCmp("sIMTrafficOrgNo").setValue(obj.orgNo);
		}
	});
});
// 打开新的tab
function openOverTraffic(orgNo,orgName) {
	staticOverTrafficOrgNo = orgNo;
	staticOverTrafficOrgName = orgName;
	staticOverTrafficDateFrom = Ext.getCmp("simTraffic_dateFrom").getValue().format('Y-m-d');
	staticOverTrafficDateTo = Ext.getCmp("simTraffic_dateTo").getValue().format('Y-m-d');
	openTab("SIM卡超流量统计", "./qryStat/sIMQuery/sIMOverTraffic.jsp");
}