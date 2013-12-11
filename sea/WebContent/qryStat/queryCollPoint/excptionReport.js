Ext.onReady(function() {
	var orgNo = '';
	var treeType = '';
	var treels = new LeftTreeListener({
    modelName : '异常告警信息统计',
    processClick : function(p, node, e) {
    treeClick(node, e);
    }
   });
function treeClick(node, e) {
	var obj = node.attributes.attributes;
	var type = node.attributes.type;
	orgNo = node.attributes.attributes.orgNo;
	treeType = node.attributes.attributes.orgType;
	//alert(node.attributes.attributes.orgNo);
	//publicTerminalStore.removeAll();
	if (!node.isLeaf()) {
		if (node.attributes.type == 'org' && treeType!='02') {
			Ext.getCmp("excptionReport1").setValue(node.text);
		}
		if (node.attributes.type == 'sub') {
			Ext.getCmp("excptionReport1").setValue(node.text);
		}if (node.attributes.type == 'cgp') {
			Ext.getCmp("excptionReport1").setValue(node.text);
		}if (node.attributes.type == 'ugp') {
			Ext.getCmp("excptionReport1").setValue(node.text);
		}
		if (node.attributes.type == 'line') {
			Ext.getCmp("excptionReport1").setValue(node.text);
		} else {
			// Ext.getCmp("pb_id_text").setValue("");
		}
	} else {
		return true;
	}
};
var erdataPanel = new Ext.Panel({
			width : 320,
			baseCls : "x-plain",
			layout : "column",
			style : "padding-top:5px",
			border : false,
			style : {
				marginTop : '0px',
				marginBottom : '10px',
				marginLeft : '2px',
				marginRight : '10px'
			},
			items : [{
						columnWidth : .42,
						layout : 'form',
						border : false,
						labelAlign : 'right',
						labelWidth : 20,
						width : '120',
						items : [{
									xtype : "datefield",
									format : "Y-m-d",
									border : false,
									id : 'excptionReportDateStart',
									fieldLabel : "从",
									value : new Date().add(Date.DAY, -7)
								}]

					}, {
						columnWidth : .5,
						layout : 'form',
						border : false,
						labelAlign : 'right',
						labelWidth : 20,
						width : '120',
						items : [{
									xtype : "datefield",
									format : "Y-m-d",
									id : 'excptionReportDateEnd',
									fieldLabel : "到",
									border : false,
									value : new Date().add(Date.DAY, 0)
								}]
					}]
		});


		var excptionRadiostore = new Ext.data.JsonStore({
							url : 'qrystat/excptionReportAction!queryConsType.action',
								fields : [ 'consType', 'consTypeName'],
								root : 'consTypeList'
			});
		var excptionRadiocombo = new Ext.form.ComboBox({
				store : excptionRadiostore,
				xtype : 'combo',
				id : 'excptionReport2',
				fieldLabel : '用户类型',
				labelStyle : "text-align:right;width:65;",
				width : 100,
				valueField : 'consType',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,// 值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为
										// false)
				displayField : 'consTypeName',// 组合框用以展示的数据的字段名（如果mode='remote'则默认为
											// undefined，如果mode = 'local' 则默认为
											// 'text'）
				mode : 'remote'
				,// 如果ComboBox读取本地数据则将值设为'local'（默认为 'remote'
								// 表示从服务器读取数据）
			emptyText : '请选择'
			});
			excptionRadiostore.load();
// 公变采集点查询panel1
var exceptionReport1 = new Ext.Panel({
			plain : true,
			border : false,
			layout : 'fit',
			region : 'north',
			height : 35,
			items : [{
						baseCls : "x-plain",
						layout : "column",
						style : "padding:5px",
						border : false,
						items : [{
						columnWidth : .3,// ----------------------
						layout : "form",
						labelWidth : 80,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [{
									fieldLabel : "供电单位",
									//disabled : true,
									labelStyle : "text-align:right;width:65;",
									id : 'excptionReport1',
									labelSeparator : "",
									width : 150
								}]
					},{
						columnWidth : .2,// ----------------------
						layout : "form",
						labelWidth : 60,
						defaultType : "textfield",
						baseCls : "x-plain",
						items : [excptionRadiocombo]
					}, {
									columnWidth : .4,
									layout : "form",
									baseCls : "x-plain",
									items : [erdataPanel]
								}, {
									columnWidth : .1,// ------------------
									layout : "form",
									defaultType : "button",
									baseCls : "x-plain",
									items : [{
												text : "统计",
												width : 50,
												listeners : {
													"click" : function() {
														//alert(Ext.getCmp("excptionReport2").getValue());
														if(Ext.getCmp("excptionReport2").getValue() !=''){
															var orgType = Ext.getCmp("excptionReport2").getValue();
																var dateStart = Ext.getCmp("excptionReportDateStart").getValue().format('Y-m-d');
														var dateEnd = Ext.getCmp("excptionReportDateEnd").getValue().format('Y-m-d');
														 publicTerminalStore2.removeAll();
														 publicTerminalStore2.baseParams
														 = {
														 	treeType:treeType,
														 orgNo : orgNo,
														 orgType : orgType,
														 dateStart :dateStart,
														 dateEnd : dateEnd
														 };
														 publicTerminalStore2.load({
														 params : {
														 start : 0,
														 limit :
														 DEFAULT_PAGE_SIZE
														 }
														 });
														}else{
																alert('请选择用户类型');														
														}
														
													
													}
												}
											}]
								}]
					}]
		});

// ------------------------------------------------------------------------------------------
// ***************翻页 页码递增
Ext.override(Ext.grid.RowNumberer, {
			renderer : function(v, p, record, rowIndex) {
				if (this.rowspan) {
					p.cellAttr = 'rowspan="' + this.rowspan + '"';
				}
				return rowIndex + 1;
			}
		});

// 按供电单位查询的grid
var publicTerminalStore2 = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'qrystat/excptionReportAction!queryexceptionReport.action'
					}),
			reader : new Ext.data.JsonReader({
						root : 'exceptionReportList',
						totalProperty : 'totalCount'
					}, [{
								name : 'orgName'
							}, {
								name : 'exceptcount'
							}, {
								name : 'ocount'
							}, {
								name : 'fcount'
							}, {
								name : 'fecount'
							}])
		});
var groupCM2 = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : '供电单位',
			dataIndex : 'orgName'
		}, {
			header : '异常数',
			dataIndex : 'exceptcount',
			width : 160
		}, {
			header : '转出数',
			dataIndex : 'ocount',
			width : 160
		}, {
			header : '误报数',
			dataIndex : 'fcount'
		}, {
			header : '误报率(%)',
			dataIndex : 'fecount',
			width : 160
		}]);
var exceptionReport2 = new Ext.grid.GridPanel({
			title : "异常告警信息统计",
			//region : 'center',
			// height : 420,
			autoScroll : true,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			cm : groupCM2,
			ds : publicTerminalStore2,
			bbar : new Ext.ux.MyToolbar({
						store : publicTerminalStore2,
						enableExpAll : true, // excel导出全部数据
						expAllText : "全部",
						enableExpPage : true, // excel仅导出当前页
						expPageText : "当前页"
					})
		});

		var exceptionReportpanelSUB = new Ext.Panel({
						region : 'center',
						autoScroll : true,
						border : false,
						layout : 'card',
						activeItem : 0,
						items : [exceptionReport2]
					});
			// 设置顶层的公变采集点查询panel
			var exceptionReportpanel = new Ext.Panel({
						// title : "专变用户采集点明细",
						autoScroll : true,
						border : false,
						layout : 'border',
						items : [exceptionReport1,exceptionReportpanelSUB]
					});
			renderModel(exceptionReportpanel, '异常告警信息统计');
		});