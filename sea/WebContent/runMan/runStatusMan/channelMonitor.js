var factoryTitle ;
var orgNoTitle ;
var consTypeTitle;
var collModeTitle;
//加载图像数据 store orgNo
var orgNoChartGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy( {
		url : 'runman/queryChannelMonitor!queryChannelMonitorOrgNo.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'channelMonitorOrgNoList',
		totalProperty : 'totalCount'
	},[
	   {name : 'orgNo'},
	   {name : 'orgName'},
	   {name : 'orgType'},
	   {name : 'upTerminalNum'},
	   {name : 'offTerminalNum'}, 
	   {name : 'offLineScale'}
	   ])
});

//加载图像数据 store factoryCode
var terminalChartGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy( {
		url :'runman/queryChannelMonitor!queryChannelMonitorTerminal.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'channelMonitorTerminaList',
		totalProperty : 'totalCount'
	},[
	   {name : 'terminalNo'},
	   {name : 'terminalFacturer'},
	   {name : 'upTerminalNum'},
	   {name : 'offTerminalNum'},
	   {name : 'offLineScale'}
	   ])
});

//加载用户类型 图像数据 store consType
var consTypeChartGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy( {
		url :'runman/queryChannelMonitor!queryChannelMonitorConsType.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'channelMonitorConsTypeCollModeList',
		totalProperty : 'totalCount'
	},[
	   {name : 'typeCode'},
	   {name : 'typeNmae'},
	   {name : 'upTerminalNum'},
	   {name : 'offTerminalNum'},
	   {name : 'offLineScale'}
	   ])
});

//加载采集方式 图像数据 store collMode
var collModeChartGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy( {
		url :'runman/queryChannelMonitor!queryChannelMonitorCollMode.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'channelMonitorConsTypeCollModeList',
		totalProperty : 'totalCount'
	},[
	   {name : 'typeCode'},
	   {name : 'typeNmae'},
	   {name : 'upTerminalNum'},
	   {name : 'offTerminalNum'},
	   {name : 'offLineScale'}
	   ])
});

//图片orgNo panel
var orgNoChart = new Ext.fc.FusionChart({
	border : false,
	region : 'center',
	wmode : 'transparent',
	backgroundColor : '000000',
	url : 'fusionCharts/ScrollStackedColumn2D.swf',
	DataXML : ""
});

// 图片factory panel
var factoryChart = new Ext.fc.FusionChart({
	region:'center',
	border : false,
	wmode : 'transparent',
	backgroundColor : '000000',
	url : 'fusionCharts/ScrollStackedColumn2D.swf',
	DataXML : ""
});


//图片  用户类别图型 panel
var consTypeChart = new Ext.fc.FusionChart({
	region : 'center',
	border : false,
	wmode : 'transparent',
	backgroundColor : '000000',
	url : 'fusionCharts/ScrollStackedColumn2D.swf',
	DataXML : ""
});

// 图片采集方式图型   panel
var collModeChart = new Ext.fc.FusionChart({
	region:'center',
	border : false,
	wmode : 'transparent',
	backgroundColor : '000000',
	url : 'fusionCharts/ScrollStackedColumn2D.swf',
	DataXML : ""
});

/**
 * 厂商默认执行
 */
terminalChartGridStore.on("load", function(o, arr) {
	var xmlData = getTerminalXMLData(terminalChartGridStore.data);
	factoryChart.changeDataXML(xmlData);
});

terminalChartGridStore.load();

/**
 * 用户类别默认执行
 */
consTypeChartGridStore.on("load", function(o, arr) {
	var xmlData = createConsTypeXml(consTypeChartGridStore.data);
	consTypeChart.changeDataXML(xmlData);
});

consTypeChartGridStore.load();

/**
 * 采集方式默认执行
 */
collModeChartGridStore.on("load", function(o, arr) {
	var xmlData = createCollModeXml(collModeChartGridStore.data);
	collModeChart.changeDataXML(xmlData);
});

collModeChartGridStore.load();

/**
 * 单位默认执行
 */
orgNoChartGridStore.on("load", function(o, arr) {
	var xmlData = getOrgNoXMLData(orgNoChartGridStore.data);
	orgNoChart.changeDataXML(xmlData);
	groupPanel.setTitle( orgNoTitle); 
});

orgNoChartGridStore.load();

//图形列表模型 orgNoChartInfoCM
var orgNoChartInfoCM = new Ext.grid.ColumnModel([
                                          {
											header : '供电单位编号',
											dataIndex : 'orgNo',
											hidden	  : true,
											sortable  : true,
											renderer  : '',
											align : 'center'
										},{
                                        	  header : '信道状态',
                                        	  dataIndex : 'channelMonitoe',
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = offCount / (upCount + offCount) ;
                                        	  	if(scale >= 0.5){
                                        	  		return '<img src="./images/channelMonitor_yichang.png" />';
                                        	  	}else{
                                        	  		return '<img src="./images/channelMonitor_zhengchang.png" />';
                                        	  	}
                                          	}
                                          },
                                          {
                                        	  header : '供电单位',
                                        	  dataIndex : 'orgName',
                                        	  width : 200,
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(val) {
													if(val == null){
															return '<div align = "center"></div>';
													}else{
															return '<div align = "left">'+ val + '</div>';
													}
														}
                                          },{
                                        	  header : '在线',
                                        	  dataIndex : 'upTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                              					return "<a href='javascript:'onclick='openTerminalRunStatusOrgNo(\""+rec.get('orgNo') +'\",\"'+ rec.get('orgName' ) + '\",\"'+ 1 + '\",\"'+ 0 + '\",\"'+ rec.get('orgType' ) + "\");" + "'>"+s+"</a>";
                                  				}
                                          },{
                                        	  header : '离线',
                                        	  dataIndex : 'offTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                            					return "<a href='javascript:'onclick='openTerminalRunStatusOrgNo(\""+rec.get('orgNo') + '\",\"'+ rec.get('orgName' ) + '\",\"'+ 0 + '\",\"'+ 0 + '\",\"'+ rec.get('orgType' )+ "\");" + "'>"+s+"</a>";
                            				}
                                          },{
                                        	  header : '上线率',
                                        	  dataIndex : 'offLineScale',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = upCount / (upCount + offCount) ;
//                                        	  	scale = scale.toPrecision(4);
                                        	  	scale = scale * 100 ;
                                        	  	if(scale == 0){
                                        	  		return '<div align = "right">'+ scale + '</div>';
                                        	  	}
                                        	  	scale = scale+"";
                                        	  	scale = scale.substring(0,4);
                                        	  	return '<div align = "right">'+ scale + '% </div>';
                                        	  }
                                          }
                                    ]);
//图形列表模型 terminalChartInfoCM
var terminalChartInfoCM = new Ext.grid.ColumnModel([
                                          {
											header : '终端厂商编号',
											dataIndex : 'terminalNo',
											hidden	  : true,
											sortable  : true,
											renderer  : '',
											align : 'center'
										},{
                                        	  header : '信道状态',
                                        	  dataIndex : 'channelMonitoe',
                                        	  align : 'center',
                                        	  iconCls : '',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = offCount / (upCount + offCount) ;
                                        	  	if(scale >= 0.5){
                                        	  		return '<img src="./images/channelMonitor_yichang.png" />';
                                        	  	}else{
                                        	  		return '<img src="./images/channelMonitor_zhengchang.png" />';
                                        	  	}
                                          	}
                                          },
                                          {
                                        	  header : '终端厂商',
                                        	  dataIndex : 'terminalFacturer',
                                        	  sortable : true,
                                        	  width : 160,
                                        	  align : 'center',
                                        	  renderer : function(val) {
															return '<div align = "center">'+ val + '</div>';
														}
                                          },{
                                        	  header : '在线',
                                        	  dataIndex : 'upTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo' ) +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 1 + '\",\"'+ 1 + "\");" + "'>" + s + "</a>";
                            				}
                                          },{
                                        	  header : '离线',
                                        	  dataIndex : 'offTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo') +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 0 + '\",\"'+ 1 + "\" );" + "'>" + s + "</a>";
                            				}
                                          },{
                                        	  header : '上线率',
                                        	  dataIndex : 'offLineScale',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = upCount / (upCount + offCount) ;
//                                        	  	scale = scale.toPrecision(4);
                                        	  	scale = scale * 100 ;
                                        	  	if(scale == 0){
                                        	  		return '<div align = "right">'+ scale + '</div>';
                                        	  	}
                                        	  	scale = scale+"";
                                        	  	scale = scale.substring(0,4);
                                        	  	return '<div align = "right">'+ scale + '% </div>';
                                        	  }
                                          }
                                    ]);

//图形 用户类别列表模型 consTypeChartInfoCM
var consTypeChartInfoCM = new Ext.grid.ColumnModel([
                                          {
											header : '用户类别',
											dataIndex : 'typeCode',
											hidden	  : true,
											sortable  : true,
											renderer  : '',
											align : 'center'
										},{
                                        	  header : '信道状态',
                                        	  dataIndex : 'channelMonitoe',
                                        	  align : 'center',
                                        	  iconCls : '',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = offCount / (upCount + offCount) ;
                                        	  	if(scale >= 0.5){
                                        	  		return '<img src="./images/channelMonitor_yichang.png" />';
                                        	  	}else{
                                        	  		return '<img src="./images/channelMonitor_zhengchang.png" />';
                                        	  	}
                                          	}
                                          },
                                          {
                                        	  header : '用户类别',
                                        	  dataIndex : 'typeNmae',
                                        	  sortable : true,
                                        	  width : 160,
                                        	  align : 'center',
                                        	  renderer : function(val) {
															return '<div align = "center">'+ val + '</div>';
														}
                                          },{
                                        	  header : '在线',
                                        	  dataIndex : 'upTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : ''
//                                        		  function(s, m, rec){
//                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo' ) +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 1 + '\",\"'+ 1 + "\");" + "'>" + s + "</a>";
//                            				}
                                          },{
                                        	  header : '离线',
                                        	  dataIndex : 'offTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : ''
//                                        		  function(s, m, rec){
//                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo') +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 0 + '\",\"'+ 1 + "\" );" + "'>" + s + "</a>";
//                            				}
                                          },{
                                        	  header : '上线率',
                                        	  dataIndex : 'offLineScale',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = upCount / (upCount + offCount) ;
//                                        	  	scale = scale.toPrecision(4);
                                        	  	scale = scale * 100 ;
                                        	  	if(scale == 0){
                                        	  		return '<div align = "right">'+ scale + '</div>';
                                        	  	}
                                        	  	scale = scale+"";
                                        	  	scale = scale.substring(0,4);
                                        	  	return '<div align = "right">'+ scale + '% </div>';
                                        	  }
                                          }
                                    ]);


//图形 采集方式列表模型 collModeChartInfoCM
var collModeChartInfoCM = new Ext.grid.ColumnModel([
                                          {
											header : '采集类别',
											dataIndex : 'typeCode',
											hidden	  : true,
											sortable  : true,
											renderer  : '',
											align : 'center'
										},{
                                        	  header : '信道状态',
                                        	  dataIndex : 'channelMonitoe',
                                        	  align : 'center',
                                        	  iconCls : '',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = offCount / (upCount + offCount) ;
                                        	  	if(scale >= 0.5){
                                        	  		return '<img src="./images/channelMonitor_yichang.png" />';
                                        	  	}else{
                                        	  		return '<img src="./images/channelMonitor_zhengchang.png" />';
                                        	  	}
                                          	}
                                          },
                                          {
                                        	  header : '采集方式',
                                        	  dataIndex : 'typeNmae',
                                        	  sortable : true,
                                        	  width : 160,
                                        	  align : 'center',
                                        	  renderer : function(val) {
															return '<div align = "center">'+ val + '</div>';
														}
                                          },{
                                        	  header : '在线',
                                        	  dataIndex : 'upTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : ''
//                                        		  function(s, m, rec){
//                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo' ) +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 1 + '\",\"'+ 1 + "\");" + "'>" + s + "</a>";
//                            				}
                                          },{
                                        	  header : '离线',
                                        	  dataIndex : 'offTerminalNum',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : ''
//                                        		  function(s, m, rec){
//                            					return "<a href='javascript:'onclick='openTerminalRunStatusFactory(\"" + rec.get('terminalNo') +'\",\"'+ rec.get('terminalFacturer' ) + '\",\"' + 0 + '\",\"'+ 1 + "\" );" + "'>" + s + "</a>";
//                            				}
                                          },{
                                        	  header : '上线率',
                                        	  dataIndex : 'offLineScale',
                                        	  sortable : true,
                                        	  align : 'center',
                                        	  renderer : function(s, m, rec){
                                        	  	var upCount = rec.get("upTerminalNum");
                                        	  	var offCount = rec.get("offTerminalNum");
                                        	  	var scale = upCount / (upCount + offCount) ;
//                                        	  	scale = scale.toPrecision(4);
                                        	  	scale = scale * 100 ;
                                        	  	if(scale == 0){
                                        	  		return '<div align = "right">'+ scale + '</div>';
                                        	  	}
                                        	  	scale = scale+"";
                                        	  	scale = scale.substring(0,4);
                                        	  	return '<div align = "right">'+ scale + '% </div>';
                                        	  }
                                          }
                                    ]);

//orgno图形列表面板
var  orgNoChartGrid = new Ext.grid.GridPanel({
	region : 'east',
	width : 400,
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : orgNoChartInfoCM,
	ds : orgNoChartGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : orgNoChartGridStore,
		enableExpAll : true
	})
});
//factory图形列表面板
var  factoryChartGrid = new Ext.grid.GridPanel({
	region : 'east',
	width : 400,
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : terminalChartInfoCM,
	ds : terminalChartGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : terminalChartGridStore,
		enableExpAll : true
	})
});

//constype用户类别图形列表面板
var  consTypeChartGrid = new Ext.grid.GridPanel({
	region : 'east',
	width : 400,
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : consTypeChartInfoCM,
	ds : consTypeChartGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : consTypeChartGridStore,
		enableExpAll : true
	})
});

//collMode用户类别图形列表面板
var collModeChartGrid = new Ext.grid.GridPanel({
	region : 'east',
	width : 400,
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : collModeChartInfoCM,
	ds : collModeChartGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : collModeChartGridStore,
		enableExpAll : true
	})
});


/*
//加载明细统计数据 store
var detailGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy( {
		url :'runman/queryChannelMonitor!queryChannelMonitor.action'
	}),
	reader : new Ext.data.JsonReader({
		root : 'channelMonitorList',
		totalProperty : 'totalCount'
	},[
	   {name : 'orgNo'},
	   {name : 'consNo'},
	   {name : 'consName'},
	   {name : 'terminalAddr'},
	   {name : 'terminalstate'},
	   {
		   	name : 'offTime',
			type : Date,
			dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
	   },
	   {name : 'gateIp'},
	   {name : 'gatePort'},
	   {name : 'ruleType'},
	   {name : 'terminalType'}
	   ])
});

//页面明细列表模型
var datailInfoCM = new Ext.grid.ColumnModel([
	{
		header : '供电单位',
		width  : 150,
		dataIndex :'orgNo',
		sortable : true,
		align : 'center',
		renderer : function(val) {
					return '<div align = "left">'+ val + '</div>';
				}
	},
	{
		header : '用户编号',
		dataIndex :'consNo',
		sortable : true,
		align : 'center',
		renderer : function(val) {
					return '<div align = "left">'+ val + '</div>';
				}
	},
	{
		header : '用户名称',
		dataIndex :'consName',
		width : 160,
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if(val == null){
					return '<div align = "center"></div>';
			}else{
					return '<div align = "left">'+ val + '</div>';
			}
				}
	},
	{
		header : '终端地址',
		dataIndex :'terminalAddr',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if(val == null){
					return '<div align = "center"></div>';
			}else{
					return '<div align = "left">'+ val + '</div>';
			}
				}
	},
	{
		header : '终端状态',
		dataIndex :'terminalstate',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if(val == null){
					return '<div align = "center"></div>';
			}else{
					return '<div align = "left">'+ val + '</div>';
			}
				}
	},
	{
		header : '离线时间',
		dataIndex :'offTime',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if(val == null){
					return '<div align = "center"></div>';
			}else{
					return '<div align = "center">'+ val + '</div>';
			}
				}
	},
	{
		header : '网关地址',
		dataIndex :'gateIp',
		sortable : true,
		align : 'center',
		renderer : function(val) {
				if (val == null) {
					return '<div align = "center"></div>';
				} else {
					return '<div align = "center">' + val + '</div>';
				}
				}
	},
	{
		header : '网关端口',
		dataIndex :'gatePort',
		sortable : true,
		align : 'center',
		renderer : function(val) {
					if (val == null) {
						return '<div align = "center"></div>';
					} else {
						return '<div align = "center">' + val + '</div>';
					}
				}
	},
	{
		header : '规约类型',
		dataIndex :'ruleType',
		sortable : true,
		align : 'center',
		renderer : function(val) {
					if (val == null) {
						return '<div align = "center"></div>';
					} else {
						return '<div align = "center">' + val + '</div>';
					}
				}
	},
	{
		header : '终端类型',
		dataIndex :'terminalType',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			if(val == null){
					return '<div align = "center"></div>';
			}else{
					return '<div align = "center">'+ val + '</div>';
			}
				}
	}
	]);

//页面明细列表panel
var detailPanel = new Ext.grid.GridPanel({
	region : 'center',
	title : '终端统计明细',
	autoScroll : true,
	stripeRows : true,
	viewConfig : {
		forceFit : true
	},
	cm : datailInfoCM,
	ds : detailGridStore,
	bbar : new Ext.ux.MyToolbar( {
		store : detailGridStore,
		enableExpAll : true
	})
});

*/

//页面顶部选择类型pannel
var opTypePanel = new Ext.Panel( {
	region : 'north',
	height : 35,
	width : 500,
	bodyStyle : 'padding:5px 0px 0px 50px',
	border : false,
	items : [{
		xtype : 'radiogroup',
		id : 'tpmpcbg',
		columns : [100, 100, 100, 100],
		items : [ {
			name : 'opType',
			boxLabel : '供电单位',
			checked : true,
			id : 'CMOpTypeImage',
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(0);
						groupPanel.setTitle( orgNoTitle);
					}
				}
			}
		}, {
			name : 'opType',
			boxLabel : '终端厂商',
			id : 'CMFactoryImage',
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(1);
						groupPanel.setTitle( factoryTitle);
					}
				}
			}
		}, {
			name : 'opType',
			boxLabel : '用户类型',
			id : 'CMConsTypeImage',
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(2);
						groupPanel.setTitle( consTypeTitle);
					}
				}
			}
		}, {
			name : 'opType',
			boxLabel : '采集方式',
			id : 'CMCollModeImage',
			listeners : {
				check : function(checkbox, checked) {
					if (checked) {
						groupPanel.layout.setActiveItem(3);
						groupPanel.setTitle( collModeTitle);
					}
				}
			}
		}]
	}]
});

	//返回终端厂商面板
	var factoryPanel = new Ext.Panel({
		id:'factory_panel',
		activeTab:0,
		layout : 'border',
		border:false,
		items:[factoryChart, factoryChartGrid]
	});	
	
	//返回供电单位面板
	var orgNoPanel = new Ext.Panel({
		id:'orgno_panel',
		activeTab:0,
		layout : 'border',
		items:[orgNoChart ,orgNoChartGrid]
	});
	//返回用户类别面板
	var consTypePanel = new Ext.Panel({
		id:'consType_panel',
		activeTab:0,
		layout : 'border',
		items:[consTypeChart ,consTypeChartGrid]
	});
	//返回采集类型面板
	var collModePanel = new Ext.Panel({
		id:'collMode_panel',
		activeTab:0,
		layout : 'border',
		items:[collModeChart ,collModeChartGrid]
	});
	//图表组合面板厂商、单位、用户类别、采集方式
	var groupPanel = new Ext.Panel({
		region : 'center',
		title : '终端状态统计',
		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[orgNoPanel, factoryPanel, consTypePanel, collModePanel]
	});

//通信状况图形和列表panel
var belowPanel = new Ext.Panel({
//	autoScroll : true,
	region : 'center',
	border : false,
	layout : 'border',
	items : [opTypePanel, groupPanel]
});

Ext.onReady(function(){
	//通信信道监测页面
	var viewPanel = new Ext.Panel({
		layout : 'border',
		autoScroll : false,
		border : false,
		items : [belowPanel]
	});
	
//	groupPanel.setTitle( orgNoTitle); 
	renderModel(viewPanel,'通信信道监测');
});





function getOrgNoXMLData(json) {
	//计算总上线数
	var allOrgNoUpSum = 0;
	//计算总离线数
	var allOrgNoDownSum = 0;
	//总上线率
	var allOrgNoUpscale;
	var xmlData = '<chart palette="2" caption="终端状态统计" shownames="1" showvalues="0" ' +
		' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

	var str = "<categories>";
	for (var i = 0; i < json.length; i++) {
		var name = json.get(i).get('orgName');
		if(name == null){
			name = '未知';
		}
		str += "<category label='" + name + "' />";
	}
	str += "</categories>";
	
	

	var str2 = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str2 += "<set value='" + json.get(i).get('upTerminalNum') + "' />";
		//计算总上线数
		allOrgNoUpSum += json.get(i).get('upTerminalNum');
	}
	str2 += "</dataset>";
	
	var str1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str1 += "<set value='" + json.get(i).get('offTerminalNum') + "'/>";
		//计算总离线数
		allOrgNoDownSum += json.get(i).get('offTerminalNum');
	}
	str1 += "</dataset>";
	

	
	xmlData = xmlData + str + str1 + str2 +  "</chart>";
	
	//计算上线率
	allOrgNoUpscale = allOrgNoUpSum / (allOrgNoUpSum + allOrgNoDownSum);
	allOrgNoUpscale = allOrgNoUpscale * 100;
	allOrgNoUpscale = allOrgNoUpscale.toString();
	allOrgNoUpscale = allOrgNoUpscale.substring(0,4);
	
	orgNoTitle = "终端状态统计  " + "<font color='red'>终端上线数[ " + allOrgNoUpSum + " ] 终端离线数[ " + allOrgNoDownSum +" ] 终端上线率 [ " + allOrgNoUpscale +"% ]</font>";
//	groupPanel.setTitle( orgNoTitle);
	return xmlData;

}

function getTerminalXMLData(json) {
	//计算总上线数
	var allFacturerUpSum = 0;
	//计算总离线数
	var allFacturerDownSum = 0;
	//总上线率
	var allFacturerUpscale;

	var xmlData = '<chart palette="2" caption="终端状态统计" shownames="1" showvalues="0" ' +
		' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

	var str = "<categories>";
	for (var i = 0; i < json.length; i++) {
		var facturerName = json.get(i).get('terminalFacturer');
		if(facturerName == null){
			facturerName = '未知';
		}
		str += "<category label='" + facturerName + "' />";
	}
	str += "</categories>";
	

	var str2 = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str2 += "<set value='" + json.get(i).get('upTerminalNum') + "' />";
		//计算总上线数
		allFacturerUpSum += json.get(i).get('upTerminalNum');
	}
	str2 += "</dataset>";
	
	var str1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str1 += "<set value='" + json.get(i).get('offTerminalNum') + "'/>";
		//计算总离线数
		allFacturerDownSum += json.get(i).get('offTerminalNum');
	}
	str1 += "</dataset>";
	xmlData = xmlData + str + str1 + str2 +  "</chart>";
	//计算上线率
	allFacturerUpscale = allFacturerUpSum / (allFacturerUpSum + allFacturerDownSum);
	allFacturerUpscale = allFacturerUpscale * 100;
	allFacturerUpscale = allFacturerUpscale.toString();
	allFacturerUpscale = allFacturerUpscale.substring(0,4);
	
	factoryTitle = "终端状态统计  " + "<font color='red'>终端上线数[ " + allFacturerUpSum + " ] 终端离线数[ " + allFacturerDownSum +" ] 终端上线率 [ " + allFacturerUpscale +"% ]</font>";
//	groupPanel.setTitle( factoryTitle);
	return xmlData;

}

//查询终端统计明细
function queryChannelMonitorDetailFactory(factoryCode, isOnline, statisticsType) {
	detailGridStore.baseParams = {
		// 在线类型 分离线形式和在线形式
		onlineType : isOnline,
		// 统计类型 分供电单位统计和终端厂商
		statisticsType : factoryCode,
		// 标记统计类型
		statisticFlag : statisticsType
	};
	detailGridStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
}

// //查询终端统计明细供电单位
function queryChannelMonitorDetailOrgNo(orgNo, isOnline, statisticsType) {
	detailGridStore.baseParams = {
		// 在线类型 分离线形式和在线形式
		onlineType : isOnline,
		//统计类型 分供电单位统计和终端厂商
		statisticsType : orgNo,
		//标记统计类型
		statisticFlag : statisticsType
	};
	detailGridStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
}

//连接到终端设备运行状态根据供电单位
function openTerminalRunStatusOrgNo(orgNo, orgName , isOnline, statisticsType ,orgType){
	staticChannelmonitorFactoryCode = undefined;
	staticChannelmonitorFactoryName = undefined;
	staticChannelmonitorOrgType = orgType;
	staticChannelmonitorOrgNo = orgNo;
	staticChannelmonitorOrgName = orgName;
	staticChannelMonitorIsonline = isOnline;
	staticChannelMonitorType = statisticsType;
	openTab("终端设备运行状态", "./runMan/runStatusMan/terminalRunStatus.jsp");
}

/*
 * 连接到终端设备运行状态根据厂商
 * param : orgNo 
 * param :
 * param : 标识统计类型
 */
function openTerminalRunStatusFactory(factoryCode, factoryName ,isOnline, statisticsType){
	staticChannelmonitorOrgNo = undefined;
	staticChannelmonitorOrgName = undefined;
	staticChannelmonitorFactoryCode = factoryCode;
	staticChannelmonitorFactoryName = factoryName;
	staticChannelMonitorIsonline = isOnline;
	staticChannelMonitorType = statisticsType;
	openTab("终端设备运行状态", "./runMan/runStatusMan/terminalRunStatus.jsp");
}

/**
 * 拼装图型 xmldata
 * @param modeList  传入数据集合
 * @return 返回拼装的xmldata
 */
function createConsTypeXml(json) {
	

	//计算总上线数
	var allConsTypeUpSum = 0;
	//计算总离线数
	var allConsTypeDownSum = 0;
	//总上线率
	var allConsTypeUpscale;

	var xmlData = '<chart palette="2" caption="终端状态统计" shownames="1" showvalues="0" ' +
		' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

	var str = "<categories>";
	for (var i = 0; i < json.length; i++) {
		var typeName = json.get(i).get('typeNmae');
		if(typeName == null){
			typeName = '未知';
		}
		str += "<category label='" + typeName + "' />";
	}
	str += "</categories>";
	

	var str2 = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str2 += "<set value='" + json.get(i).get('upTerminalNum') + "' />";
		//计算总上线数
		allConsTypeUpSum += json.get(i).get('upTerminalNum');
	}
	str2 += "</dataset>";
	
	var str1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str1 += "<set value='" + json.get(i).get('offTerminalNum') + "'/>";
		//计算总离线数
		allConsTypeDownSum += json.get(i).get('offTerminalNum');
	}
	str1 += "</dataset>";
	xmlData = xmlData + str + str1 + str2 +  "</chart>";
	//计算上线率
	allConsTypeUpscale = allConsTypeUpSum / (allConsTypeUpSum + allConsTypeDownSum);
	allConsTypeUpscale = allConsTypeUpscale * 100;
	allConsTypeUpscale = allConsTypeUpscale.toString();
	allConsTypeUpscale = allConsTypeUpscale.substring(0,4);
	
	consTypeTitle = "终端状态统计  " + "<font color='red'>终端上线数[ " + allConsTypeUpSum + " ] 终端离线数[ " + allConsTypeDownSum +" ] 终端上线率 [ " + allConsTypeUpscale +"% ]</font>";
//	groupPanel.setTitle( factoryTitle);
	return xmlData;
}


/**
 * 拼装图型 xmldata
 * @param modeList  传入数据集合
 * @return 返回拼装的xmldata
 */
function createCollModeXml(json) {
	

	//计算总上线数
	var allCollModeUpSum = 0;
	//计算总离线数
	var allCollModeDownSum = 0;
	//总上线率
	var allCollModeUpscale;

	var xmlData = '<chart palette="2" caption="终端状态统计" shownames="1" showvalues="0" ' +
		' numberPrefix="" showSum="1" decimals="0" useRoundEdges="1" formatNumberScale="0">';

	var str = "<categories>";
	for (var i = 0; i < json.length; i++) {
		var typeName = json.get(i).get('typeNmae');
		if(typeName == null){
			typeName = '未知';
		}
		str += "<category label='" + typeName + "' />";
	}
	str += "</categories>";
	

	var str2 = '<dataset seriesName="上线数" color="8BBA00" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str2 += "<set value='" + json.get(i).get('upTerminalNum') + "' />";
		//计算总上线数
		allCollModeUpSum += json.get(i).get('upTerminalNum');
	}
	str2 += "</dataset>";
	
	var str1 = '<dataset seriesName="离线数" color="FF0000" showValues="0">';
	for (var i = 0; i < json.length; i++) {
		str1 += "<set value='" + json.get(i).get('offTerminalNum') + "'/>";
		//计算总离线数
		allCollModeDownSum += json.get(i).get('offTerminalNum');
	}
	str1 += "</dataset>";
	xmlData = xmlData + str + str1 + str2 +  "</chart>";
	//计算上线率
	allCollModeUpscale = allCollModeUpSum / (allCollModeUpSum + allCollModeDownSum);
	allCollModeUpscale = allCollModeUpscale * 100;
	allCollModeUpscale = allCollModeUpscale.toString();
	allCollModeUpscale = allCollModeUpscale.substring(0,4);
	
	collModeTitle = "终端状态统计  " + "<font color='red'>终端上线数[ " + allCollModeUpSum + " ] 终端离线数[ " + allCollModeDownSum +" ] 终端上线率 [ " + allCollModeUpscale +"% ]</font>";
//	groupPanel.setTitle( factoryTitle);
	return xmlData;
}
