var autoSendQueryByName = '';
var autoSendQueryByNo = '';
var assetNo = '';


//Ext.onReady(function() {
	var autoSendQueryID;
//	var treels = new LeftTreeListener({
//				modelName : '居民集抄户查询',
//				processClick : function(p, node, e) {
//					autoSendQueryClick(p, node, e);
//				},
//				processUserGridSelect : function(cm, row, record) {
//					// Ext.getCmp("origFrameQryID").setValue(record.get('consNo'));
//					Ext.getCmp("autoSendQueryTEXT").setValue(record
//							.get('consName'));
//					Ext.getCmp("autoSendQueryByName").enable();
//					Ext.getCmp("autoSendQueryByNo").enable();
//					autoSendQueryStore.removeAll();
//					autoSendQueryID = record.get('consId');
//					autoSendstore.baseParams = {
//						tgId : autoSendQueryID
//					};
//				}
//			});
/*			
	//左边树监听时点击树节点调用的方法		
	function autoSendQueryClick(p, node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if (node.isLeaf() && obj.consType == '2') {
			Ext.getCmp("autoSendQueryTEXT").setValue(node.text);
			Ext.getCmp("autoSendQueryByName").enable();
			Ext.getCmp("autoSendQueryByNo").enable();
			autoSendQueryStore.removeAll();
			autoSendQueryID = obj.consId;
					autoSendstore.baseParams = {
			tgId : autoSendQueryID
		};
		} else {
			return true;
		}
	}
	
	//左边树监听时选择用户调用的方法
	function autoSendQuerySelect(cm, row, record) {
					// Ext.getCmp("origFrameQryID").setValue(record.get('consNo'));
					Ext.getCmp("autoSendQueryTEXT").setValue(record.get('consName'));
					Ext.getCmp("autoSendQueryByName").enable();
					Ext.getCmp("autoSendQueryByNo").enable();
					autoSendQueryStore.removeAll();
					autoSendQueryID = record.get('consId');
					autoSendstore.baseParams = {
						tgId : autoSendQueryID
					};
				}
	// ************my*************
	var autoSendstore = new Ext.data.Store({
				params:{tgId : autoSendQueryID},
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/autoSendQueryAction!querytmnlAssetNo.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'ASQList'
						}, [{
									name : 'tmnlAssetNo'
								}])
			});

	var autoSendcombo = new Ext.form.ComboBox({
				store : autoSendstore,
				xtype : 'combo',
				id : 'autoSendName',
				fieldLabel : '集中器',
				labelStyle : "text-align:right;width:65;",
				width : 100,
				valueField : 'tmnlAssetNo',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,// 值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为
										// false)
				displayField : 'tmnlAssetNo',// 组合框用以展示的数据的字段名（如果mode='remote'则默认为
											// undefined，如果mode = 'local' 则默认为
											// 'text'）
				mode : 'remote',// 如果ComboBox读取本地数据则将值设为'local'（默认为 'remote'
								// 表示从服务器读取数据）
				emptyText : '全部'
			});

	// **************my***************
*/

/*	var autoSendQuery1 = new Ext.Panel({
		plain : true,
		region : 'north',
		layout : 'fit',
		border : false,
		height : 35,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding:5px",
			border : false,
			items : [{
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 65,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "台区名称<font color='red'>*</font>",
									labelStyle : "text-align:right;width:65;",
									id : 'autoSendQueryTEXT',
									emptyText : '请从左边选择...',
									readOnly : true,
									labelSeparator : "",
									width : 200
								}]
					}, {
						columnWidth : .2,// ----------------------
						layout : "form",
						labelWidth : 50,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [autoSendcombo]
					}, {
						columnWidth : .2,// ----------------------
						layout : "form",
						labelWidth : 30,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "户名",
									disabled : true,
									labelStyle : "text-align:right;width:65;",
									id : 'autoSendQueryByName',
									labelSeparator : "",
									width : 100
								}]
					}, {
						columnWidth : .2,// ----------------------
						layout : "form",
						labelWidth : 30,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "户号",
									disabled : true,
									labelStyle : "text-align:right;width:65;",
									id : 'autoSendQueryByNo',
									labelSeparator : "",
									width : 100
								}]
					}, {
						columnWidth : .1,// ------------------
						layout : "form",
						defaultType : "button",
						baseCls : "x-plain",
						items : [{
							text : "查询",
							width : 50,
							listeners : {
								"click" : function() {
									var autoSendQueryTEXT = Ext
											.getCmp("autoSendQueryTEXT");
									if (!autoSendQueryTEXT.isValid(true)) {
										autoSendQueryTEXT.markInvalid('不能为空');
										return;
									}
									autoSendQueryByName = Ext
											.getCmp("autoSendQueryByName")
											.getValue();
									autoSendQueryByNo = Ext
											.getCmp("autoSendQueryByNo")
											.getValue();
									assetNo  =  Ext.getCmp("autoSendName").getValue();
									getASQMessage();
								}
							}
						}]
					}]
		}]
	});
	
	*/
	//运行store
	function autoSendQueryStoreQuery(orgType,nodeType,nodeValue,consType,tmnlAssetNo,tgId){
		//清除grid数据
		autoSendQueryStore.removeAll();
		autoSendQueryStore.baseParams = {
			nodeType : nodeType,
			tmnlAssetNo : tmnlAssetNo,
			nodeValue: nodeValue,
			consType : consType,
			orgType : orgType,
			tgId	: tgId
		};
		autoSendQueryStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	}
	
	var autoSendQueryStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/autoSendQueryAction!queryAutoSendQuery.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'ASQList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						},{
							name : 'tgName'
						},  {
							name : 'consNo'
						}, {
							name : 'consName'
						}, {
							name : 'mrSectNo'
						}, {
							name : 'tmnlAssetNo'
						}, {
							name : 'assetNo'
						}, {
							name : 'regStatus'
						}, {
							name : 'commMode'
						}, {
							name : 'tFactor'
						}, {
							name : 'instDate',
							type : 'date',
							dateFormat : 'Y-m-d\\TH:i:s' // 2009-04-03T00:00:00
						}, {
							name : 'elecAddr'
						}])
	});

	var groupCM3 = new Ext.grid.ColumnModel([{
		header : '供电单位',
		dataIndex : 'orgName',
		width : 160,
		sortable : true,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		},
		align : 'center'
	}, {
		header : '台区名称',
		dataIndex : 'tgName',
		sortable : true,
		renderer : function(val) {
			return '<div align = "right">' + val + '</div>';
		},
		align : 'center'
	}, {
		header : '用户编号',
		dataIndex : 'consNo',
		sortable : true,
		renderer : function(val) {
			return '<div align = "right">' + val + '</div>';
		},
		align : 'center'
	}, {
		header : '用户名称',
		dataIndex : 'consName',
		width : 120,
		sortable : true,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		},
		align : 'center'
	}, {
		header : '抄表段号',
		width : 60,
		dataIndex : 'mrSectNo',
		sortable : true,
		renderer : function(val) {
			return '<div align = "right">' + val + '</div>';
		},
		align : 'right'
	}, {
		header : '集中器资产',
		dataIndex : 'tmnlAssetNo',
		sortable : true,
		renderer : function(val) {
			if(val == null){
				val = '';
			}
			return '<div align = "right">' + val + '</div>';
		},
		align : 'right'
	}, {
		header : '表计资产号',
		dataIndex : 'assetNo',
		sortable : true,
		renderer : function(val) {
		if(val == null){
			val = '';
		}
			return '<div align = "right">' + val + '</div>';
		},
		align : 'right'
	}, {
		header : '通信方式',
		width : 60,
		dataIndex : 'commMode',
		sortable : true,
		renderer : function(val) {
			return '<div align = "left">' + val + '</div>';
		},
		align : 'center'
	}, {
		header : '综合倍率',
		width : 60,
		dataIndex : 'tFactor',
		sortable : true,
		align : 'center'
	}, {
		header : '投运日期',
		width : 120,
		dataIndex : 'instDate',
		sortable : true,
		align : 'right',
		fomart : 'Y-m-d H:i:s',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		header : '用电地址',
		dataIndex : 'elecAddr',
		sortable : true,
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用电地址" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		},
		align : 'center'
	}, {
		header : '注册状态',
		dataIndex : 'regStatus',
		sortable : true,
		align : 'center',
		width : 60,
		renderer : function(val) {
			if (val == 1) {
				return '已注册';
			} else {
				return '未注册';
			}
		}
	}]);

	var autoSendQuery2 = new Ext.grid.GridPanel({
		title : "居民集抄户明细",
		height : 420,
		region : 'center',
		autoScroll : true,
		stripeRows : true,
		viewConfig : {
			forceFit : false
		},
		cm : groupCM3,
		ds : autoSendQueryStore,
		bbar : new Ext.ux.MyToolbar({
					store : autoSendQueryStore
				})
			// grid分页控件
		});

	// 设置顶层的公变采集点查询panel
	var autoSendQuerypanel = new Ext.Panel({
				layout : 'border',
				autoScroll : true,
				border : false,
				items : [/*autoSendQuery1,*/ autoSendQuery2]
			});
			
			
//	renderModel(autoSendQuerypanel, '居民集抄户查询');

//	if (typeof(publicTerminalTgId) != 'undefined') {
//		if (!Ext.isEmpty(publicTerminalTgId)) {
//			Ext.getCmp("autoSendQueryTEXT").setValue(publicTerminalTgId);
//			autoSendQueryID = publicTerminalconsId;
//			// alert(autoSendQueryID);
//			getASQMessage();
//			publicTerminalTgId = null;
//			autoSendQueryID = null;
//		}
//	}

//	Ext.getCmp('居民集抄户查询').on('activate', function() {
//				if (typeof(publicTerminalTgId) != 'undefined') {
//					if (!Ext.isEmpty(publicTerminalTgId)) {
//						Ext.getCmp("autoSendQueryTEXT")
//								.setValue(publicTerminalTgId);
//						autoSendQueryID = publicTerminalconsId;
//						getASQMessage();
//						publicTerminalTgId = null;
//						autoSendQueryID = null;
//					}
//				}
//			});
			
			
//});


/**
 * 点击公变下钻低压集抄
 */
function touchAutoSendQuery(){
	if (typeof(publicTerminalTgId) != 'undefined') {
					if (!Ext.isEmpty(publicTerminalTgId)) {
						//调用store执行查询
						autoSendQueryStoreQuery(null,null,null,null,null,publicTerminalTgId);
						//清空标识
						publicTerminalTgId = null;
					}
				}
};