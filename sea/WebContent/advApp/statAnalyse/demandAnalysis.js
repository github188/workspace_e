/**
 * 测量点抄表日冻结需量及需量发生时间
 * 测量点月冻结最大需量及发生时间
 */
 //单位类别
 var orgType ;
 //节点值
 var nodeValue ;
 //节点类型
 var nodeType;
  //节点文本框
 var fieldText = new Ext.form.TextField({
			fieldLabel : "选择节点名",// <font color='red'>*</font>",
			allowBlank : false,// 红星，必选
			id : 'demandA_text',
			readOnly : true,
			//labelStyle : "text-align:right;width:50;",
			// emptyText : '请输入...',
			labelSeparator : "",
			width : 120,
			validator : function(val) {
				if (Ext.isEmpty(val))
					return false;
				else
					return true;
			}
		})
 var startDate = new Ext.form.DateField({
			id : "cpd_dateFrom",
			format : "Y-m-d",
			fieldLabel : "日期从",
			editable : false,
			labelStyle : "text-align:right;width:50;",
			value : new Date().add(Date.DAY, -6),
			labelSeparator : ""
			});
 var endDate = new Ext.form.DateField({
			id : "cpd_dateTo",
			format : "Y-m-d",
			fieldLabel : "到",
			editable : false,
			value : new Date(),
			labelStyle : "text-align:right;width:50;",
			labelSeparator : ""
		});
// 时间选择radio
var cpdRadio = new Ext.form.RadioGroup({
			id : 'cpdRadio',
			columns : [80, 80],
			autoWidth : true,
			items : [{
						name : 'cpdTime',
						boxLabel : '按日',
						checked : true,
						inputValue : 1,
						id : 'cpdDate',
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									cpdGroupPanel.layout.setActiveItem(0);
									//falgRD = 0;
								}
							}
						}
					}, {
						name : 'cpdTime',
						boxLabel : '按月',
						id : 'cpdMonth',
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									cpdGroupPanel.layout.setActiveItem(1);
									//falgRD = 1;
								}
							}
						}
					}]
		});
			//测量点冻结需求分析radio 表头面板
	var cpdTopPanel = new Ext.Panel({
		height : 35,
		region : 'north',
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding:5px",
			items : [{
									columnWidth : .16,
									layout : "column",
									baseCls : "x-plain",
									items : [cpdRadio]
								},{
									columnWidth : .22,
									layout : "form",
									labelWidth : 70,
									baseCls : "x-plain",
									items : [fieldText]
								}, {
									columnWidth : .2,
									layout : "form",
									labelWidth : 50,
									baseCls : "x-plain",
									items : [startDate]
								}, {
									columnWidth : .15,
									layout : "form",
									labelWidth : 15,
									baseCls : "x-plain",
									items : [endDate]
								}, {
									columnWidth : .1,
									layout : "form",
									defaultType : "button",
									baseCls : "x-plain",
									items : [{
												text : "查询",
												width : 50,
												handler : function() {
														var textValue = Ext.getCmp("demandA_text");
														var dateFlag = cpdRadio.getValue().inputValue;
														var dateFrom = startDate.getValue();
														var dateTo = endDate.getValue();
														if (dateFrom > dateTo) {
															Ext.MessageBox.alert("提示", "开始时间不能大于结束时间！");
															return;
														}
														if (!textValue.isValid(true)) {
															textValue.markInvalid('不能为空');
															return true;
														}
														if(dateFlag == 1){
															//清除grid数据
														CPDDateStore.removeAll();
														CPDDateStore.baseParams = {
															startDate : startDate.getValue(),
															endDate :endDate.getValue(),
															nodeType : nodeType,
															nodeValue: nodeValue,
															orgType : orgType
														};
														CPDDateStore.load({
																	params : {
																		start : 0,
																		limit : DEFAULT_PAGE_SIZE
																	}
																});
														}else if(dateFlag == 2){
															//清除grid数据
														CPDMonthStore.removeAll();
														CPDMonthStore.baseParams = {
															startDate : startDate.getValue(),
															endDate :endDate.getValue(),
															nodeType : nodeType,
															nodeValue: nodeValue,
															orgType : orgType
														};
														CPDMonthStore.load({
																	params : {
																		start : 0,
																		limit : DEFAULT_PAGE_SIZE
																	}
																});
														}
														
														
													}
											}]
								}]
		}]
	});
		
	/*
	 * 表格测量点抄表日冻结需量及需量发生时间	
	 */
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
					if (CPDDateStore && CPDDateStore.lastOptions
							&& CPDDateStore.lastOptions.params) {
						startRow = CPDDateStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var CPDDateSm = new Ext.grid.CheckboxSelectionModel();
	var CPDDateStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'advapp/collectionPointDemand!queryCollectionPointDemandDate.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'cpdDateList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consName'
							},{
								name : 'contractCap'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'assetNo'
							}, {
								name : 'terminalAddrs'
							}, {
								name : 'dataTime'
							},{
								name : 'colTime'
							}, {
								name : 'multiple'
							},  {
								name : 'mark'
							},{
								name : 'papDemand'
							}, {
								name : 'papDemandTime'
							}, {
								name : 'prpDemand'
							}, {
								name : 'prpDemandTime'
							}, {
								name : 'rapDemand'
							}, {
								name : 'rapDemandTime'
							}, {
								name : 'rrpDemand'
							}, {
								name : 'rrpDemandTime'
							}, {
								name : 'orgNo'
							}, {
								name : 'consNo'
							}])
		});
var CPDDateCM = new Ext.grid.ColumnModel([rowNum,CPDDateSm,{
			header : '供电单位',
			dataIndex : 'orgName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '数据ID: ' + record.get('dataID');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '存放区域: ' + record.get('areaCode');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '合同容量',
			dataIndex : 'contractCap',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '终端资产编号',
			dataIndex : 'tmnlAssetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '资产编号',
			dataIndex : 'assetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '终端地址码',
			dataIndex : 'terminalAddrs',
			sortable : true,
			align : 'center',
			renderer : ''
		},  {
			header : '数据日期',
			dataIndex : 'dataTime',
			width:160,
			sortable : true,
			align : 'center',
			renderer : ''
		},  {
			header : '综合倍率（PT×CT）',
			dataIndex : 'multiple',
            width:100,
			sortable : true,
			align : 'center'
		},{
			header : '终端抄表时间',
			dataIndex : 'colTime',
			sortable : true,
			align : 'center',
			hidden : true
		}, {
			header : '电流互感器的倍率',
			dataIndex : 'ct',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '电流互感器的倍率: ' + record.get('ct');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '电压互感器的倍率',
			dataIndex : 'pt',
            width:100,
			sortable : true,
			align : 'center'
		},{
			header : '补全标记',
			dataIndex : 'mark',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量',
			dataIndex : 'papDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量发生时间',
            width:60,
			dataIndex : 'papDemandTime',
			sortable : true,
			align : 'center',
                renderer : ''
		}, {
			header : '正向无功总最大需量',
            width:60,
			dataIndex : 'prpDemand',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '正向无功总最大需量发生时间',
			dataIndex : 'prpDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量发生时间',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '反向无功总最大需量',
			dataIndex : 'rrpDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '反向无功总最大需量发生时间',
			dataIndex : 'rrpDemandTime',
			sortable : true,
			align : 'center'
		}]);

var cpdDateGrid = new Ext.grid.GridPanel({
//			title : "测量点抄表日冻结",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : CPDDateCM,
			sm : CPDDateSm,
			ds : CPDDateStore,
			bbar : new Ext.ux.MyToolbar({
							store : CPDDateStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
		
		
		
	/*
	 * 测量点月冻结最大需量及发生时间
	 */	
		

	// rowNum、rowNum_1:分别定义与2个grid
	var rowNumMon = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (CPDMonthStore && CPDMonthStore.lastOptions
							&& CPDMonthStore.lastOptions.params) {
						startRow = CPDMonthStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var CPDMonthSm = new Ext.grid.CheckboxSelectionModel();
	var CPDMonthStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'advapp/collectionPointDemand!queryCollectionPointDemandMonth.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'cpdMonthList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'consName'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'assetNo'
							}, {
								name : 'terminalAddrs'
							}, {
								name : 'dataTime'
							},{
								name : 'colTime'
							}, {
								name : 'multiple'
							}, {
								name : 'contractCap'
							}, {
								name : 'mark'
							},{
								name : 'papDemand'
							}, {
								name : 'papDemandTime'
							}, {
								name : 'prpDemand'
							}, {
								name : 'prpDemandTime'
							}, {
								name : 'rapDemand'
							}, {
								name : 'rapDemandTime'
							}, {
								name : 'rrpDemand'
							}, {
								name : 'rrpDemandTime'
							}, {
								name : 'orgNo'
							}, {
								name : 'consNo'
							}])
		});
var CPDMonthCM = new Ext.grid.ColumnModel([rowNumMon,CPDMonthSm,{
			header : '供电单位',
			dataIndex : 'orgName',
			width:160,
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '数据ID: ' + record.get('dataID');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			sortable : true,
			align : 'center',
			renderer : function(val, metaData, record) {
					var s = '存放区域: ' + record.get('areaCode');
					var html = '<span  ext:qtip="' + s + '">' + val + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
		},{
			header : '终端资产编号',
			dataIndex : 'tmnlAssetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '合同容量',
			dataIndex : 'contractCap',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '资产编号',
			dataIndex : 'assetNo',
			sortable : true,
			align : 'center',
			renderer : ''
		},{
			header : '终端地址码',
			dataIndex : 'terminalAddrs',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '数据日期',
			dataIndex : 'dataTime',
			width:160,
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '终端抄表时间',
			dataIndex : 'colTime',
			sortable : true,
			align : 'center',
			hidden : true
		}, {
			header : '综合倍率（PT×CT）',
			dataIndex : 'multiple',
            width:100,
			sortable : true,
			align : 'center'
		},{
			header : '补全标记',
			dataIndex : 'mark',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量',
			dataIndex : 'papDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '正向有功总最大需量发生时间',
            width:60,
			dataIndex : 'papDemandTime',
			sortable : true,
			align : 'center',
                renderer : ''
		}, {
			header : '正向无功总最大需量',
            width:60,
			dataIndex : 'prpDemand',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '正向无功总最大需量发生时间',
			dataIndex : 'prpDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center'
		}, {
			header : '反向有功总最大需量发生时间',
			dataIndex : 'rapDemandTime',
			sortable : true,
			align : 'center',
			renderer : ''
		}, {
			header : '反向无功总最大需量',
			dataIndex : 'rrpDemand',
			sortable : true,
			align : 'center'
		}, {
			header : '反向无功总最大需量发生时间',
			dataIndex : 'rrpDemandTime',
			sortable : true,
			align : 'center'
		}]);

var cpdMonthGrid = new Ext.grid.GridPanel({
//			title : "测量点月冻结",
			region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : false
			},
			cm : CPDMonthCM,
			sm : CPDMonthSm,
			ds : CPDMonthStore,
			bbar : new Ext.ux.MyToolbar({
							store : CPDMonthStore,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部"
//							,
//							enableExpPage : true, // excel仅导出当前页
//							expPageText : "当前页"
						})
		});
		
//测量点冻结需求分析按日、按月组合面板
	var cpdGroupPanel = new Ext.Panel({
		region : 'center',
		title : '测量点冻结需求分析',
		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[cpdDateGrid, cpdMonthGrid]
	});
	
	//测量点冻结需求分析
	var viewPanel = new Ext.Panel({
		layout : 'border',
		autoScroll : false,
		border : false,
		items : [cpdTopPanel, cpdGroupPanel]
	});
	
	Ext.onReady(function(){
		var daTree = new LeftTreeListener({
				modelName : '需量分析',
				processClick : function(p, node, e) {
							var obj = node.attributes.attributes;
							var type = node.attributes.type;
//							if (!node.isLeaf()) {
								if (node.attributes.type == 'org') {
									// if (obj.orgType != '02') {
									orgType = obj.orgType;
									nodeValue = obj.orgNo;
									nodeType = node.attributes.type;
									Ext.getCmp("demandA_text").setValue(node.text);
									consType = null;
									// }
								}
								if (node.attributes.type == 'usr') {
									nodeValue = obj.consNo;
									nodeType = node.attributes.type;
									Ext.getCmp("demandA_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'cgp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("demandA_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'ugp') {
									nodeValue = obj.groupNo;
									nodeType = node.attributes.type;
									Ext.getCmp("demandA_text").setValue(node.text);
									consType = null;
								}
								if (node.attributes.type == 'line') {
									nodeValue = obj.lineId;
									nodeType = node.attributes.type;
									Ext.getCmp("demandA_text").setValue(node.text);
									consType = null;
								} else {
								}
//							} else {
//								return true;
//							}
						},
				processUserGridSelect : function(cm, row, record) {
					Ext.getCmp("demandA_text").setValue(record.get('consName'));
					record.get('consNo');
//					autoSendstore.baseParams = {
//						
//					};
				}
			});
	renderModel(viewPanel,'需量分析');
	//默认标识左边树显示radio
	});
		
	