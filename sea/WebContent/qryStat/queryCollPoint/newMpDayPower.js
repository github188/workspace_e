// 测量点日冻结总及分相有用功率------------------------------------
   var treels = new LeftTreeListener({
    modelName : '当日实时负荷',
    processClick : function(p, node, e) {
   		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if (node.isLeaf()) {
			   		consNoforCCust = obj.consNo;
		} else {
			return true;
		}
    },
    processUserGridSelect:function(cm,row,record){
					consNoforCCust = record.get('consNo');
				}
   });
var DateStart = new Date().add(Date.DAY, -8).format('Y-m-d');
var DateEnd = new Date().add(Date.DAY, -1).format('Y-m-d');
	var mpDayPower = new Ext.Panel({
		title:'【当日实时负荷】',
		region:'north',
		height : 70,
				layout:'fit',
		plain : true,
		items : [{
					baseCls : "x-plain",
					layout : "column",
					style : "padding:8px",
					items : [ {
								columnWidth : .3,
								layout : "form",
								labelStyle : "text-align:right;width:80;",
								labelWidth : 80,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											id:'newMpDayStart',
											value:new Date().add(Date.DAY, -8),
											fieldLabel : "查询日期从",
											name :'dataDateFrom',
											labelStyle : "text-align:right;width:80px;"
										}]
							},{
								columnWidth : .3,
								layout : "form",
								labelWidth : 10,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : [{
											xtype:"datefield",
											format:"Y-m-d",
											id:'newMpDayEnd',
											value:new Date().add(Date.DAY, -1),
											fieldLabel : "到",
											name :'dataDateTo',
											labelStyle : "text-align:right;width:10px;"
										}]
							},{
								columnWidth : .2,
								layout : "form",
								labelWidth : 50,
								defaultType : "textfield",
								baseCls : "x-plain",
								items : []
							},{
								columnWidth : .1,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "查询",
											listeners : {
												"click": function (){	
														DateStart = Ext.getCmp("newMpDayStart").getValue().format('Y-m-d');
														DateEnd = Ext.getCmp("newMpDayEnd").getValue().format('Y-m-d');
//														mpDayPowerStore.baseParams['dataDateFrom'] = mpDayEnergy.find("name","dataDateFrom")[0].getValue();
//														mpDayPowerStore.baseParams['dataDateTo'] = mpDayEnergy.find("name","dataDateTo")[0].getValue();
														CONS_NO_newMpDay = consNoforCCust;
														if(CONS_NO_newMpDay != ""){
														mpDayPowerStore.load({
															params:{
																CONS_NO:CONS_NO_newMpDay,
																DateStart:DateStart,
																DateEnd:DateEnd}
														});
														}
												}
											},
											width : 50
										}]
							}, {
								columnWidth : .1,
								layout : "form",
								defaultType : "button",
								baseCls : "x-plain",
								items : [{
											text : "曲线查看",
											width : 50,listeners : {
												"click": function (){	
														generalAnalyseShow();
												}
											}
										}]
							}]
				}]
	});

	var mpDayPowerStore = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/consumerInfoAction!queryNewMpDayPower.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'newMpDayPowerList'
					}, [{name:'dataTime1'
//								type: 'date',
//         						dateFormat:'Y-m-d\\TH:i:s'
         						},
         						{name:'dataTime2'},
						{name:'tFactor'},
						{name:'assetNo'},
						{name:'p'},
						{name:'pa'},
						{name:'pb'},
						{name:'pc'},
						{name:'ua'},
						{name:'ub'},
						{name:'uc'},
						{name:'ia'},
						{name:'ib'},
						{name:'ic'},
						{name:'i0'},
						{name:'q'},
						{name:'qa'},
						{name:'qa'},
						{name:'qb'},
						{name:'qc'},
						{name:'f'},
						{name:'fa'},
						{name:'fb'},
						{name:'fc'},
						{name:'papE'},
						{name:'prpE'},
						{name:'rapE'},
						{name:'rrpE'},
						{name:'papR'},
						{name:'prpR'},
						{name:'rapR'},
						{name:'rrpR'},
						{name:'terminalAddr'},
						{name:'consNo'},
						{name:'consName'},
						{name:'elecAddr'}]),
						 sortInfo : {
		  		field : 'dataTime1',
		  		direction : 'ASC'
			},
			groupField:'dataTime1'
		});
	var mpDayPowerCm = new Ext.grid.ColumnModel([{
					header : "日期",
					dataIndex : 'dataTime1',
					align : "center"
//					fomart:'Y-m-d H:i:s',
//			renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
				},{
					header : "时间",
					dataIndex : 'dataTime2',
					align : "center"
//					fomart:'Y-m-d H:i:s',
//			renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
				},{
					header : "倍率",
					dataIndex : 'tFactor',
					align : "center"
				},{
					header : "电能表资产",
					dataIndex : 'assetNo',
					align : "center"
				},{
					header : "有功功率 ",
					dataIndex : 'p',
					align : "center"
				},{
					header : "A相有功功率 ",
					dataIndex : 'pa',
					align : "center"
				},{
					header : "B相有功功率 ",
					dataIndex : 'pb',
					align : "center"
				},{
					header : "C相有功功率 ",
					dataIndex : 'pc',
					align : "center"
				},{
					header : "A相电压",
					dataIndex : 'ua',
					align : "center"
				},{
					header : "B相电压",
					dataIndex : 'ub',
					align : "center"
				},{
					header : "C相电压",
					dataIndex : 'uc',
					align : "center"
				},{
					header : "A相电流",
					dataIndex : 'ia',
					align : "center"
				},{
					header : "B相电流",
					dataIndex : 'ib',
					align : "center"
				},{
					header : "C相电流",
					dataIndex : 'ic',
					align : "center"
				},{
					header : "零序电流",
					dataIndex : 'i0',
					align : "center"
				},{
					header : "无功功率",
					dataIndex : 'q',
					align : "center"
				},{
					header : "A相无功功率",
					dataIndex : 'qa',
					align : "center"
				},{
					header : "B相无功功率",
					dataIndex : 'qb',
					align : "center"
				},{
					header : "C相无功功率",
					dataIndex : 'qc',
					align : "center"
				},{
					header : "功率因素",
					dataIndex : 'f',
					align : "center"
				},{
					header : "A相功率因素",
					dataIndex : 'fa',
					align : "center"
				},{
					header : "B相功率因素",
					dataIndex : 'fb',
					align : "center"
				},{
					header : "C相功率因素",
					dataIndex : 'fc',
					align : "center"
				},{
					header : "正向有功总电量",
					hidden:true,
					dataIndex : 'papE',
					align : "center"
				},{
					header : "正向无功总电量",
					hidden:true,
					dataIndex : 'prpE',
					align : "center"
				},{
					header : "反向有功总电量",
					hidden:true,
					dataIndex : 'rapE',
					align : "center"
				},{
					header : "反向无功总电量",
					hidden:true,
					dataIndex : 'rrpE',
					align : "center"
				},{
					header : "正向有功总示值",
					hidden:true,
					dataIndex : 'papR',
					align : "center"
				},{
					header : "正向无功总示值",
					hidden:true,
					dataIndex : 'prpR',
					align : "center"
				},{
					header : "反向有功总示值",
					hidden:true,
					dataIndex : 'rapR',
					align : "center"
				},{
					header : "反向无功总示值",
					hidden:true,
					dataIndex : 'rrpR',
					align : "center"
				},{
					header : "终端地址",
					hidden:true,
					dataIndex : 'terminalAddr',
					align : "center"
				},{
					header : "用户编号",
					hidden:true,
					dataIndex : 'consNo',
					align : "center"
				},{
					header : "用户名称",
					hidden:true,
					dataIndex : 'consName',
					align : "center"
				},{
					header : "用电地址",
					hidden:true,
					dataIndex : 'elecAddr',
					align : "center"
				}]);		
	var mpDayPowerGrid = new Ext.grid.GridPanel({
		region:'center',
		autoScroll:true,
		anchor:'100%',
		colModel : mpDayPowerCm,
		ds : mpDayPowerStore,
		view: new Ext.grid.GroupingView({
	        forceFit:false,
	        groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
	    }),
	    animCollapse: false
	});
	var mpDayPowerWindow = new Ext.Panel({
		layout : 'border',
		items:[mpDayPower,mpDayPowerGrid]
	});
	renderModel(mpDayPowerWindow,'当日实时负荷');
	
	function generalAnalyseShow() {
		consNo_newMpDayPower = consNoforCCust;
	openTab("采集数据综合分析", "./qryStat/collDataAnalyse/generalAnalyse.jsp");
};

Ext.getCmp('当日实时负荷').on('activate', function() {
				mpDayPowerStore.removeAll();
			});
