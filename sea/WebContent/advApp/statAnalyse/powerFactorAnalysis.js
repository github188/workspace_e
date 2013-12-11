var consNo, startTime, endTime, voltCode, nodeNo,type,voltValue;
// 电压等级下拉框列表
// var voltDegreeStore = new Ext.data.JsonStore({
// // proxy : new Ext.data.DataProxy({}),
// url : './advapp/powerFactorAnalysisAction!queryVoltByConsNo.action',
// root:'voltList',
// fields:[
// {name:'voltCode'},
// {name:'volt'}]
//	
// });
var voltDegreeStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : './advapp/powerFactorAnalysisAction!queryVoltByConsNo.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'voltList'
			}, [{
						name : 'voltCode'
					}, {
						name : 'volt'
					}])
});
var pfaVoltDeg = new Ext.form.ComboBox({
			hideOnSelect : false,
			displayField : 'volt',
			hiddenName : "any",
			mode : "local",
			valueField : 'voltCode',
			// lazyRender:true,
			name : 'pfaVoltDegree',
			id : 'pfaVoltDegree',
			store : voltDegreeStore,
			width : 120,
			// mode : 'remote',
			fieldLabel : '电压等级',
			triggerAction : 'all',
			emptyText : '--请选择--',
			blankText : '--请选择--'

		});

var powerQryPanel = new Ext.Panel({
			plain : true,
			border : false,
			region : 'north',
			layout : 'form',
			height : 35,
			items : [{
				layout : 'column',
				style : "padding-top:5px",
				border : false,
				items : [{
							border : false,
							columnWidth : .23,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 60,
							defaultType : "textfield",
							items : [{
										fieldLabel : "选择节点",
										readOnly : true,
										id : 'pfaNodeName',
										allowBlank:false, 										
										width : 170
									}]
						}, {
							border : false,
							columnWidth : .20,
							layout : 'form',
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [pfaVoltDeg]
						}, {
							columnWidth : .20,
							border : false,
							labelAlign : 'right',
							defaultType : "datefield",
							labelWidth : 60,
							layout : 'form',
							items : [{
										fieldLabel : "选择时间",
										editable : false,
										id : 'pfaTime',
										labelSeparator : "",
										format : 'Y-m-d',
										value : new Date(),
										width : 150
									}]
						},{
							columnWidth : .37,
							border : false,
							layout : 'form',
							labelWidth : 55,
							labelAlign : 'right',
							bodyStyle : 'padding:0px 0px 0px 10px',
							items : [{
										xtype : 'button',
										text : "查询",
										width : 70,
										listeners : {
											"click" : function() {
												var CONS = Ext
														.getCmp("pfaNodeName");
												if (!CONS.isValid(true)) {
													CONS.markInvalid('不能为空');
													return true;
												};
												queryPowerFactorList();
											}
										}
									}]
						}]
			}
//			, {
//				layout : 'column',
//				style : "padding-top:2px",
//				border : false,
//				items : [{
//							columnWidth : .3,
//							border : false,
//							labelAlign : 'right',
//							defaultType : "datefield",
//							labelWidth : 30,
//							layout : 'form',
//							items : [{
//										fieldLabel : "从",
//										editable : false,
//										id : 'pfaStartTime',
//										labelSeparator : "",
//										format : 'Y-m-d',
//										value : new Date().add(Date.DAY, -5),
//										width : 150
//									}]
//						}, {
//							columnWidth : .3,
//							border : false,
//							labelAlign : 'right',
//							labelWidth : 25,
//							defaultType : "datefield",
//							layout : 'form',
//							items : [{
//										fieldLabel : "到",
//										editable : false,
//										id : 'pfaEndTime',
//										labelSeparator : "",
//										format : 'Y-m-d',
//										value : new Date().add(Date.DAY, 1),
//										width : 150
//									}]
//						}]
//			}
			]
		});
// 查询用户对应的电压等级
// function queryVolt() {
// Ext.Ajax.request({
// url : './advapp/powerFactorAnalysisAction!queryVoltByConsNo.action',
// params : {
// consNo : consNo
// },
// success : function(response) {
// var result = Ext.decode(response.responseText);
// voltDegreeStore.loadData(result.voltList);
// if(!Ext.isEmpty(voltDegreeStore) && 0 < voltDegreeStore.getCount()){
// var voltValue = voltDegreeStore.getAt(0).get('volt');
// voltCode = voltDegreeStore.getAt(0).get('voltCode');
// pfaVoltDeg.setValue(voltValue);
// }
// }
// });
// };
function queryPowerFactorList() {
	pfaTime = Ext.getCmp("pfaTime").getValue().format('Y-m-d');
//	endTime = Ext.getCmp("pfaEndTime").getValue().format('Y-m-d H:i:s');
	voltCode=pfaVoltDeg.getValue();
	powerFactorGridStore.baseParams = {
		consNo : nodeNo,
		voltCode : voltCode,
		type:type,
	    pfaTime: pfaTime
	};
	powerFactorGridStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
}
var powerFactorGridStore = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
				url : './advapp/powerFactorAnalysisAction!queryPowerFactorList.action'
			}),
	reader : new Ext.data.JsonReader({
				root : 'pfList',
				totalProperty : 'totalCount'
			}, [{
						name : 'orgName'
					}, {
						name : 'consNo'
					}, {
						name : 'consName'
					}, {
						name : 'contractCap'
					}, {
						name : 'volt'
					}, {
						name : 'maxFactor'
					}, {
						name : 'maxTime'
				}	, {
						name : 'minFactor'
					}])
});
var powerFactorCM = new Ext.grid.ColumnModel([{
			header : '供电单位',
			dataIndex : 'orgName',
			sortable : true,
			align : 'center'
		}, {
			header : '户号',
			dataIndex : 'consNo',
			sortable : true,
			align : 'center'
		}, {
			header : '户名',
			dataIndex : 'consName',
			sortable : true,
			align : 'center'
		}, {
			header : '合同容量',
			dataIndex : 'contractCap',
			sortable : true,
			align : 'center'
		}, {
			header : '电压等级',
			dataIndex : 'volt',
			sortable : true,
			align : 'center'
		}, {
			header : '最大功率因素',
			dataIndex : 'maxFactor',
			sortable : true
		}, {
			header : '最大功率时间',
			dataIndex : 'maxTime',
			sortable : true
//			,
//			align : 'center',
//			fomart : 'Y-m-d H:i:s',
//			renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')

		}, {
			header : '最小功率',
			dataIndex : 'minFactor',
			sortable : true,
			align : 'center'
		}]);
var powerFactorGrid = new Ext.grid.GridPanel({
			region : 'center',
			title : '用户列表',
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			cm : powerFactorCM,
			ds : powerFactorGridStore,
			bbar : new Ext.ux.MyToolbar({
						store : powerFactorGridStore,
						enableExpAll : true
					})
		});
Ext.onReady(function() {
	var pfaAllpanel = new Ext.Panel({
				autoScroll : true,
				border : false,
				layout : 'border',
				items : [powerQryPanel, powerFactorGrid]
			});
	// 点击左边树事件
	var listener = new LeftTreeListener({
		modelName : '功率因数分析',
		processClick : function(p, node, e) {
			var obj = node.attributes.attributes;
			type = node.attributes.type;
			var name = node.text;
			if (type == 'org' && obj.orgType != '02') {// obj.orgType
				// 省节点不可点选、查询
				Ext.getCmp("pfaNodeName").setValue(name);
				//这里节点编号代表供电单位名称
				nodeNo=obj.orgNo;
				voltDegreeStore.load({
							params : {
								type:type,
								consNo : nodeNo
							}
						});
				if (!Ext.isEmpty(voltDegreeStore)
						&& 0 < voltDegreeStore.getCount()) {
					voltValue = voltDegreeStore.getAt(0).get('volt');
					voltCode = voltDegreeStore.getAt(0).get('voltCode');
					pfaVoltDeg.setValue(voltValue);
				}

			}
			else if (type == 'usr') {
				Ext.getCmp("pfaNodeName").setValue(name);
				//这里节点编号代表供电单位名称
				nodeNo = obj.consNo;
				voltDegreeStore.load({
							params : {
								type:type,
								consNo : nodeNo
							}
						});
				if (!Ext.isEmpty(voltDegreeStore)
						&& 0 < voltDegreeStore.getCount()) {
					voltValue = voltDegreeStore.getAt(0).get('volt');
					voltCode = voltDegreeStore.getAt(0).get('voltCode');
					pfaVoltDeg.setValue(voltValue);
				}
			}
		},
		processUserGridSelect : function(cm, row, record) {
			Ext.getCmp("pfaNodeName").setValue(record.get('consName'));
			consNo = record.get('consNo');
			voltDegreeStore.load({
						params : {
							type:'usr',
							consNo : consNo
						}
					});
			if (!Ext.isEmpty(voltDegreeStore) && 0 < voltDegreeStore.getCount()) {
				voltValue = voltDegreeStore.getAt(0).get('volt');
				voltCode = voltDegreeStore.getAt(0).get('voltCode');
				pfaVoltDeg.setValue(voltValue);
				powerFactorGridStore.removeAll();

			}
		}
	});
	renderModel(pfaAllpanel, '功率因数分析');
})
