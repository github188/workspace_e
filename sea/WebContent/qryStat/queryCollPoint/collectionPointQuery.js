
//标识radio标志
var falgRD ;
var orgType;
var nodeType;
var nodeValue;
var consType;
var tmnlAssetNo;
Ext.onReady(function() {
	
	
	//左边树监听时点击树节点调用的方法		
function collectionPointQueryClick( node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	if (node.isLeaf()) {
	privateTerminalCon = obj.consNo;
	}
	if (!node.isLeaf()) {
		if (node.attributes.type == 'org') {
//				if (obj.orgType != '02') {
					nodeValue = obj.orgNo;
					orgType = obj.orgType;
					nodeType = node.attributes.type;
					Ext.getCmp("cpq_id_text").setValue(node.text);
					consType = null;
//				}
			}
		if (node.attributes.type == 'sub') {
			nodeValue = obj.subsId;
			nodeType = node.attributes.type;
			Ext.getCmp("cpq_id_text").setValue(node.text);
			consType = null;
		}if (node.attributes.type == 'cgp') {
			nodeValue = obj.groupNo;
			nodeType = node.attributes.type;
			Ext.getCmp("cpq_id_text").setValue(node.text);
			consType = null;
		}if (node.attributes.type == 'ugp') {
			nodeValue = obj.groupNo;
			nodeType = node.attributes.type;
			Ext.getCmp("cpq_id_text").setValue(node.text);
			consType = null;
		}
		if (node.attributes.type == 'line') {
			nodeValue = obj.lineId;
			nodeType = node.attributes.type;
			Ext.getCmp("cpq_id_text").setValue(node.text);
			consType = null;
		} else {
		}
	} else {
		return true;
	}
};
	//终端统计
	var collectionstore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'qrystat/publicTerminalAction!queryTmnlStatus.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'privateTerminalList'
						}, [{
									name : 'statusCode'
								}, {
									name : 'statusName'
								}])
			});
	collectionstore.load();
	
	var collectionCombo = new Ext.form.ComboBox({
				store : collectionstore,
				xtype : 'combo',
				id : 'collectionCombo',
				fieldLabel : '终端状态',
				width : 100,
				valueField : 'statusCode',
				editable : false,
				resizable : true,//拖动柄缩放
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,//值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为 false)
				displayField : 'statusName',//组合框用以展示的数据的字段名（如果mode='remote'则默认为 undefined，如果mode = 'local' 则默认为 'text'）
				mode : 'remote',//如果ComboBox读取本地数据则将值设为'local'（默认为 'remote' 表示从服务器读取数据）
				emptyText : '--请选择--'
			});
			
	//combo 默认		
	collectionstore.on('load', function() {
				collectionCombo.setValue(collectionstore.getAt(0).get('statusCode'));
			});
			
			
var cpRadio = new Ext.form.RadioGroup({
		id : 'cpRadio',
		columns : [100,100,100,100],
		items : [{
		name : 'collectionPointType',
		boxLabel : '专变',
		checked : true,
		id : 'cpTypeZB',
		listeners : {
			check : function(checkbox, checked) {
				if (checked) {
					cpGroupPanel.layout.setActiveItem(0);
					collectionCombo.ownerCt.show();
					falgRD = 0;
				}
			}
		}
	}, {
		name : 'collectionPointType',
		boxLabel : '公变',
		id : 'cpTypeGB',
		listeners : {
			check : function(checkbox, checked) {
				if (checked) {
					cpGroupPanel.layout.setActiveItem(1);
					collectionCombo.ownerCt.show();
					falgRD = 1;
				}
			}
		}
	}, {
		name : 'collectionPointType',
		boxLabel : '低压集抄',
		id : 'cpTypeJM',
		listeners : {
			check : function(checkbox, checked) {
				if (checked) {
					cpGroupPanel.layout.setActiveItem(2);
					collectionCombo.ownerCt.hide();
					falgRD = 2;
				}
			}
		}
	}, {
		name : 'collectionPointType',
		boxLabel : '关口',
		id : 'cpTypeGK',
		listeners : {
			check : function(checkbox, checked) {
				if (checked) {
					cpGroupPanel.layout.setActiveItem(3);
					collectionCombo.ownerCt.show();
					falgRD = 3;
				}
			}
		}
	}]
	});
	
	
	//采集点综合查询radio 表头面板
	var collectionPointQueryPanel = new Ext.Panel({
		height : 70,
		region : 'north',
		border : false,
		plain : true,
		items : [{
			baseCls : "x-plain",
			layout : "form",
			style : "padding:5px",
			items : [{
				baseCls : "x-plain",
				layout : "column",
				style : "padding:5px",
				border : false,
				items : [{
							columnWidth : .35,// ----------------------
							layout : "form",
							labelWidth : 50,
							defaultType : "textfield",
							baseCls : "x-plain",
							items : [{
										fieldLabel : "节点名",//<font color='red'>*</font>",
										allowBlank : false,//红星，必选
										id : 'cpq_id_text',
										readOnly : true,
										labelStyle : "text-align:right;width:50;",
										// emptyText : '请输入...',
										labelSeparator : "",
										width : 200,
										validator : function(val) {
											if (Ext.isEmpty(val))
												return false;
											else
												return true;
										}
									}]
						}, {
							columnWidth : .3,// ------------------
							layout : "form",
							defaultType : "combo",
							labelWidth : 50,
							labelAlign : 'right',
							baseCls : "x-plain",
							items : [collectionCombo]
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
											var cpqTextValue = Ext.getCmp("cpq_id_text");
											var cpqCombo = Ext.getCmp('collectionCombo').getValue();
											if (!cpqTextValue.isValid(true)) {
												cpqTextValue.markInvalid('不能为空');
												return true;
											}
											if (falgRD == 0) {
												 if (consType == '1') {// 专变
												 	privateTerminalStoreQuery(nodeType,cpqCombo,null,nodeValue,consType,tmnlAssetNo);
												 }else if(nodeType != null){
												
												 privateTerminalStoreQuery(nodeType,cpqCombo,orgType,nodeValue,null,null);
												 }
												 
											}
											if (falgRD == 1) {
												if (consType == '2') {// 公变
													publicTerminalStoreQuery(nodeType,cpqCombo,null,nodeValue,consType,tmnlAssetNo);
												}else if(nodeType != null){
												// 调用公变左边树监听
												publicTerminalStoreQuery(nodeType,cpqCombo,orgType,nodeValue,null,null);
													
												}
											}
											if (falgRD == 2) {
												if (consType == '5') {// 低压集抄
													autoSendQueryStoreQuery(null,nodeType,nodeValue,consType,tmnlAssetNo,null);
												}else if(nodeType != null){
												// 调用低压集抄左边树监听  
												autoSendQueryStoreQuery(orgType,nodeType,nodeValue,null,null,null);
													
												}

											}
											if (falgRD == 3) {
											// 调用关口左边树监听
											// gateTerminalSelect(cm, row, record);
											if (consType != null) {
												if (consType == '3'
														|| consType == '6'
														|| consType == '7') {// 关口
												}
											} else  if(nodeType != null){
											}

										}

										}
										}
									}]
						}]
			},{
						width : 500,
						layout : "column",
						baseCls : "x-plain",
						items : [cpRadio]
					}]
		}]
	});
	
	//采集点公变、专变、低压集抄、关口 组合面板
	var cpGroupPanel = new Ext.Panel({
		region : 'center',
//		title : '采集点综合查询',
		layout : 'card',
		activeItem : 0,
		border : false,
    	items:[privateTerminalpanel, publicTerminalpanel, autoSendQuerypanel, gateTerminalpanel]
	});
	

	var leftTreeListener = new LeftTreeListener(	{
			modelName : '采集点综合查询',
			processClick : function(p, node, e) {
			//采集点查询加载左边树
				collectionPointQueryClick(node, e);
			},
			processUserGridSelect : function(cm, row, record) {
					nodeValue = record.get('consNo');
					privateID = record.get('consId');
					consType = record.get('consType');
					tmnlAssetNo = record.get('tmnlAssetNo');
					nodeType = null;
//					Ext.getCmp("cpq_id_text").setValue(record.get('consName'));

					if (falgRD == 0) {
						if (consType == '1') {// 专变
							Ext.getCmp("cpq_id_text").setValue(record.get('consName'));
						} else if (nodeType != null) {

						}

					}
					if (falgRD == 1) {
						if (consType == '2') {// 公变
							Ext.getCmp("cpq_id_text").setValue(record.get('consName'));
						} else if (nodeType != null) {

						}
					}
					if (falgRD == 2) {
						if (consType == '5') {// 低压集抄
							Ext.getCmp("cpq_id_text").setValue(record.get('consName'));
						} else if (nodeType != null) {
							// 调用低压集抄左边树监听

						}

					}
					if (falgRD == 3) {
						// 调用关口左边树监听
						// gateTerminalSelect(cm, row, record);
						if (consType != null) {
							if (consType == '3' || consType == '6'
									|| consType == '7') {// 关口
										Ext.getCmp("cpq_id_text").setValue(record.get('consName'));
							}
						} else if (nodeType != null) {
							
						}

					}
				}
		
	});
	// 采集点查询页面模板
	var viewPanel = new Ext.Panel({
		layout : 'border',
		autoScroll : false,
		border : false,
		items : [collectionPointQueryPanel, cpGroupPanel]
	});
	renderModel(viewPanel,'采集点综合查询');
	//默认标识左边树显示radio
	falgRD = 0;
publicTerminalWindowShow = function publicTerminalWindowShow() {
	var record = publicTerminal2.getSelectionModel().getSelected();
	var consNo ;
	var tgId;
	if (record != null){
//		consNo = record.get('consNo');
		tgId = record.get('tgId');
		
	}
//	publicTerminalconsId = consNo;
	publicTerminalTgId = tgId;
	//跳转页面低压集抄
	cpGroupPanel.layout.setActiveItem(2);
	//设置radio
	cpRadio.setValue('cpTypeJM',true);
	//触发页面事件
	touchAutoSendQuery();
};
	
});

