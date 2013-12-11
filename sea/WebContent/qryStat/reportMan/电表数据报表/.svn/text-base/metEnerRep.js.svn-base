// 电表电量数据报表
/**
 * @author 陈国章
 * @2010-08
 */
reportParamStore = new Ext.ux.LocalStore({
			dataKey : "cons_no",
			// url:'./qrystat/reportTypeQueryAction!queryTemplateConsInfo.action',
			fields : ["abc", "bcd", "org_name", "cons_no", "cons_name",
					"terminal_addr", "tmnl_asset_no"]
		});
//reportParamStore.on('load', function() {
//			Ext.getCmp('metDatRepMERGrid').getSelectionModel().selectAll();
//		});
var params=[];
		
function clearDataState(obj, key) {
		dataResult = {};
		if (Ext.isArray(obj)) {
			Ext.each(obj, function(o) {
						newData && (function() {
							delete newData[o.get(key)];
						})();
						dataState && (function() {
							delete dataState[o.get(key)];
						})();
					});
		} else {
			newData = {};
			dataState = {};
		}
		// clearSingle();
	}

var defBool = reportTypeGrid.getSelectionModel().getSelected()
		.get('defBoolean');
if (defBool == 1) {
	Ext.Ajax.request({
				url : "./qrystat/reportTypeQueryAction!queryConsNoList.action",
				params : {
					"reportId" : reportTypeGrid.getSelectionModel()
							.getSelected().get('reportId')
				},
				success : function(response) {
					var o = Ext.decode(response.responseText);
					reportParamStore.addDatas(o.consInfoList);
					
				}
			});
} else {

}

var reportParamsPanel = new Ext.Panel({
			border : true,
			region : 'north',
			bodyStyle : 'padding:10px 0px',
			layout : 'column',
			height : 40,
			items : [{
						border : false,
						columnWidth : .56,

						labelWidth : 80,
						layout : 'column',
						items : [{
									layout : "form",
									border : false,
									columnWidth : .50,
									labelAlign : 'right',
									items : [{

												id : 'metDatRepMERStartTime',
												xtype : 'datefield',
												value : new Date().add(
														Date.DAY, -6),
												editable:false,
												fieldLabel : '起始时间',
												format : "Y-m-d"
											}]
								}, {
									layout : "form",
									border : false,
									columnWidth : .50,
									labelAlign : 'right',
									items : [{

												id : 'metDatRepMEREndTime',
												xtype : 'datefield',
												value : new Date(),
												editable:false,
												fieldLabel : '结束时间',
												format : "Y-m-d"
											}]
								}]
					}]
		});
userTerminal = new Ext.grid.CheckboxSelectionModel({
//                    listeners : {
//				check : function(c, v) {
//					if (v) {
//						var recCount = reportParamStore.getCount();
//						var records = reportParamStore.getRange(0, recCount);
//						userTerminal.selectRecords(records);
//					}
//					else
//					{
//						var recCount = reportParamStore.getCount();
//						var records = reportParamStore.getRange(0, recCount);
//						userTerminal.clearSelections();
//					}
//				}
// function(c, v) {
// if (v) {
//										reportParamStore.setAllSelect(true);
//										userTerminal.selectAll();
//									} else {
//										reportParamStore.clearAllSelect();
//										userTerminal.clearSelections();										
//									}
//								}
//								}
								});
var userTerminaNumber = new Ext.grid.RowNumberer({
	renderer : function(v, p, record, rowIndex) {
		if (this.rowspan) {
			p.cellAttr = 'rowspan="' + this.rowspan + '"';
		}

		reportParamStore.lastOptions.params = reportParamStore.lastOptions.params
				|| {
					start : 0
				};
		return reportParamStore.lastOptions.params.start + rowIndex + 1;
	}
});

// 报表参数列模型
var reportParamCm = new Ext.grid.ColumnModel([userTerminaNumber, userTerminal,
		{
			header : '供电单位',
			dateIndex : 'org_name',
			align : 'left'
		}, {
			header : '用户编号',
			dateIndex : 'cons_no',
			align : 'left'
		}, {
			header : '用户名称',
			dateIndex : 'cons_name',
			align : 'left'
		}, {
			header : '终端地址',
			dateIndex : 'terminal_addr',
			align : 'left'
		}]);
var sefDefType = reportTypeGrid.getSelectionModel().getSelected()
		.get('reportType');
var sefDefName = reportTypeGrid.getSelectionModel().getSelected()
		.get('reportName')
var tBarText;
if (defBool == '0') {
	tBarText = "保存为模板";
	sefDefType = "自定义" + sefDefType;
	sefDefName = "自定义" + sefDefName;
} else if (defBool == '1') {
	tBarText = "更新模板";
}
var reportTypePanel = new Ext.form.TextField({
			name : 'metDatRepMERType',
			readOnly : true,
			fieldLabel : '自定义报表类型',
			anchor : '100%',
			value : sefDefType
		});
var reportNamePanel = new Ext.form.TextField({
			name : 'metDatRepMERName',
			readOnly : false,
			fieldLabel : '自定义报表名称',
			anchor : '100%',
			value : sefDefName
		});

var reportTemplatInfo = new Ext.form.FormPanel({
			title : '报表属性修改',
			region : 'center',
			frame : false,
			layout : 'table',
			labelAlign : 'right',
			monitorResize : true,
			border : false,
			bodyStyle : 'padding:5px',
			layoutConfig : {
				columns : 1
			},
			defaults : {
				width : 450,
				height : 35,
				labelWidth : 100
			},
			items : [{
						border : false,
						layout : 'form',
						items : [reportTypePanel]
					}, {
						border : false,
						layout : 'form',
						items : [reportNamePanel]
					}, {
						border : false,
						layout : 'form',
						width : 450,
						height : 100,
						colspan : 2,
						items : [{
							xtype : 'textarea',
							width : 150,
							height : 60,
							fieldLabel : '报表描述',
							readOnly : false,
							labelSeparator : '',
							name : 'metDatRepMERDescription',
							anchor : '100%',
							value : reportTypeGrid.getSelectionModel()
									.getSelected().get('reportParam')

						}]
					}]
		});
var templateInfoWindow = new Ext.Window({
			title : ' 报表属性修改',
			frame : true,
			width : 525,
			height : 250,
			layout : "border",
			autoScroll : true,
			modal : true,
			plain : true,// 设置背景颜色
			resizable : false,// 不可移动
			buttonAlign : "center",// 按钮的位置
			closeAction : "hide",
			items : [reportTemplatInfo],
			buttons : [{
						text : '确定',
						handler : function() {
							// 将窗体隐藏而并不销毁
							templateManager();
							this.ownerCt.ownerCt.close();
						}
					}, {
						text : '取消',
						handler : function() {
							this.ownerCt.ownerCt.close();
						}
					}]
		});
function templateManager() {
	Ext.getCmp('metDatRepMERGrid').getStore().eachAll(function(key,v){
    params.push(v["cons_no"]);});
//	var recs = userTerminal.getSelections();
	var consNoList = '';
	for (var i = 0; i < params.length; i++) {
		var record = params[i];
		consNoList = consNoList + record;
		if (i < (params.length - 1)) {
			consNoList = consNoList + ',';
		}
	}
	Ext.Ajax.request({
				url : './qrystat/reportTypeQueryAction!saveConsNoList.action',
				method : 'post',
				params : {
					'defBoolean' : 1,
					'reportId' : reportTypeGrid.getSelectionModel()
							.getSelected().get('reportId'),
					'reportType' : reportTemplatInfo.getForm()
							.findField("metDatRepMERType").getValue(),
					'reportName' : reportTemplatInfo.getForm()
							.findField("metDatRepMERName").getValue(),
					'consNoList' : consNoList,
					'reportParam' : reportTemplatInfo.getForm()
							.findField("metDatRepMERDescription").getValue()
				},
				success : function(response, opts) {
					Ext.Msg.alert('提示信息', '保存成功');
				},
				failure : function(response, opts) {
					Ext.Msg.alert('提示信息', '数据保存出错');
				},
				waitTitle : '请稍后',
				waitMsg : '正在保存用户列表...'
			});
};

reportParamsPanelSecond = new Ext.Panel({
			layout : 'border',
			border : false,
			items : [reportParamsPanel, {
				xtype : 'panel',
				height : 120,
				region : 'center',
				layout : "fit",
				autoScroll : true,
				border : false,
				width : 800,
				plain : true,
				items : [{
							xtype : 'grid',
							mode : 'remote',
							id : 'metDatRepMERGrid',
							name : 'metDatRepMERGrid',
							// ds : reportParamStore,
							region : 'south',
							cm : reportParamCm,
							store : reportParamStore,
							sm : userTerminal,
							loadMask : false,
							autoScroll : true,
							stripeRows : true,
//							autoLoad : true,
							bbar : new Ext.ux.MyToolbar({
										store : reportParamStore

									}),
							viewConfig : {
								forceFit : true

							}

						}],
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>电表电量数据报表</font>"
						},'-', {
							xtype : "checkbox",
							boxLabel : "全选",
							id : "metDatRepMER_selectAll",
							listeners : {
								check : function(c, v) {
									if (v) {
										reportParamStore.setAllSelect(true);
										userTerminal.selectAll();
									} else {
										reportParamStore.clearAllSelect();
										userTerminal.clearSelections();										
									}
								}
							}
						}, '->', {
							text : tBarText,
							iconCls : 'plus',
							handler : function() {
								var recs = userTerminal.getSelections();
								if (recs.length = 0 || Ext.isEmpty(recs)) {
									Ext.MessageBox.alert("提示", "请选择数据!");
									return;
								} else {
									templateInfoWindow.show();
								}
							}
						}, '-', {
					text : '删除所选用户',
					iconCls : 'minus',
					width : 30,
					handler : function() {
						if (Ext.getCmp('metDatRepMER_selectAll').checked) {
							reportParamStore.clearAll();
							clearDataState();
//							unlockGrid();
						} else {
							var selectTerminal =userTerminal.getSelections();
//							clearDataState(selectTerminal, 'cons_no');
							reportParamStore.removeDatas(selectTerminal);
						}
					}
				}]
			}]
		});
// reportParamStore.setGrid(Ext.getCmp('metDatRepMERGrid'));
treeListener = new LeftTreeListener({
			modelName : sefDefType,
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				Ext.Ajax.request({
							url : "baseapp/dataFetch!dealTree.action",
							params : {
								"node" : type == "usr"
										? ("tmnl_" + obj.tmnlAssetNo + "_" + obj.tmnlAssetNo)
										: node.id
							},
							success : function(response) {
								var o = Ext.decode(response.responseText);
								reportParamStore.addDatas(o.resultMap);
								Ext.getCmp('metDatRepMERGrid').getSelectionModel().selectAll();
							}});
			},

			processUserGridSelect : function(sm, row, r) {		
				var tmnl = r.get("tmnlAssetNo");
				Ext.Ajax.request({
					url : "baseapp/dataFetch!dealTree.action",
					params : {
						"node" : ("tmnl_" + tmnl + "_" + tmnl)							
					},
					success : function(response) {
						var o = Ext.decode(response.responseText);
						reportParamStore.addDatas(o.resultMap);
						Ext.getCmp('metDatRepMERGrid').getSelectionModel().selectAll();
					}
				});
			},
			processUserGridAllSelect : function(grid) {
				var tmnls = [];
				grid.getStore().each(function(r) {
							tmnls.push(r.get("tmnlAssetNo"));
						});
				Ext.Ajax.request({
							url : "baseapp/dataFetch!dealTree.action",
							params : {
								start : 0,
								limit : 400,
								"node" : "arr_50_x_" + tmnls.join("_")
							},
							success : function(response) {
								var o = Ext.decode(response.responseText);
								reportParamStore.addDatas(o.resultMap);
								Ext.getCmp('metDatRepMERGrid').getSelectionModel().selectAll();							}
						});
			}
		});
getParamsUrl = function() {

	var startTime = Ext.getCmp("metDatRepMERStartTime").getRawValue();
	var endTime = Ext.getCmp("metDatRepMEREndTime").getRawValue();
	var consList = new Array();
	var recs = userTerminal.getSelections();
	if (startTime <= endTime) {
		timeSelect = true;
	} else {
		Ext.MessageBox.alert("提示", "起始时间必须小于截止时间!");
		timeSelect = false
		return;
	}
	if (null == recs || 0 == recs.length) {
		Ext.MessageBox.alert("提示", "请选择数据!");
		dataSelect = false;
		return;
	} else {
		dataSelect = true;
	}
	ParamsUrl = "&startDate='" + startTime + "'&endDate='" + endTime
			+ "'&consList=";
	for (var i = 0; i < recs.length; i++) {
		consList[i] = recs[i].get('cons_no');
		ParamsUrl = ParamsUrl + "'" + consList[i] + "'";
		if (i < (recs.length - 1))
			ParamsUrl = ParamsUrl + ","
	}
};