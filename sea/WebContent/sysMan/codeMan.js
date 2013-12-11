Ext.onReady(function() {
var codeManSQL = '';
var codeMandict_catalog = '';
	// -----------------------------------------------------------------------------------------------------
	var codeManRadioGroup = new Ext.form.RadioGroup({
				id : "codeManRadioGroup",
				width : 380,
				border : true,
				defaults : {
					style : {
						paddingLeft : "10px"
					}
				},
				columns : [80, 80],
				items : [{
					boxLabel : '营销数据',
					name : 'queryByFrom',
					inputValue : 1,
					checked : true,
					handler : function(e, checked) {
						if (checked) {
							Ext.getCmp('codeManAddButton1').setDisabled(true);
							Ext.getCmp('codeManUpdateButton1')
									.setDisabled(true);
							Ext.getCmp('codeManDeleteButton1')
									.setDisabled(true);
							Ext.getCmp('codeManAddButton2').setDisabled(true);
							Ext.getCmp('codeManUpdateButton2')
									.setDisabled(true);
							Ext.getCmp('codeManDeleteButton2')
									.setDisabled(true);
							Ext.getCmp('codeManSaveButton').setDisabled(true);
							codeManPanelLeft.layout.setActiveItem(1);
							codeManPanelRight.layout.setActiveItem(1);
							codeManStore11.load();
						}
					}
				}, {
					boxLabel : '内部数据',
					name : 'queryByFrom',
					inputValue : 2,
					handler : function(e, checked) {
						if (checked) {
							Ext.getCmp('codeManAddButton1').setDisabled(false);
							Ext.getCmp('codeManUpdateButton1')
									.setDisabled(false);
							Ext.getCmp('codeManDeleteButton1')
									.setDisabled(false);
							Ext.getCmp('codeManAddButton2').setDisabled(false);
							Ext.getCmp('codeManUpdateButton2')
									.setDisabled(false);
							Ext.getCmp('codeManDeleteButton2')
									.setDisabled(false);
							Ext.getCmp('codeManSaveButton').setDisabled(false);
							codeManPanelLeft.layout.setActiveItem(0);
							codeManPanelRight.layout.setActiveItem(0);
							codeManStore1.load();
						}
					}
				}]
			});

	var codeManRadioGroupLeft = new Ext.Panel({
				plain : true,
				baseCls : "x-plain",
				region : 'north',
				border : false,
				height : 35
			});
	var codeManRadioGroupRight = new Ext.Panel({
				plain : true,
				baseCls : "x-plain",
				region : 'north',
				border : false,
				height : 35
			});
	var codeManPanelTop = new Ext.Panel({
				plain : true,
				region : 'north',
				layout : 'fit',
				border : false,
				height : 35,
				// title:'TOP',
				items : [{
							baseCls : "x-plain",
							layout : "column",
							style : "padding:5px",
							border : false,
							items : [{
										columnWidth : .4,
										baseCls : "x-plain",
										items : codeManRadioGroupLeft
									}, {
										columnWidth : .4,
										baseCls : "x-plain",
										items : codeManRadioGroup
									}, {
										columnWidth : .3,
										baseCls : "x-plain",
										items : codeManRadioGroupRight
									}]
						}]
			});

	var codeManStore1 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/codeManAction!queryCodeManIn.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'codeManInList',
							totalProperty : 'totalCount'
						}, [{
									name : 'dictCatalogName'
								}, {
									name : 'dictCatalog'
								}])
			});

	var codeManGrid1sm = new Ext.grid.RowSelectionModel();
	var codeManGrid1 = new Ext.grid.GridPanel({
		height : 300,
		width : 300,
		hidden : false,
		// stripeRows: true,
		// autoHeight : true,
		// autoScroll:true,
		enableColumnMove : false,// 设置表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
			header : '字典目录名称',
			dataIndex : 'dictCatalogName',
			// menuDisabled : true,
			align : "center",
			renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="字典目录名称" ext:qtip="'
						+ val + '">' + val + '</div>';
				return html;
			}
		}, {
			header : "字典目录",
			dataIndex : 'dictCatalog',
			hidden : true,
			// menuDisabled : true,
			align : "center"
		}]),
		ds : codeManStore1,
		sm : codeManGrid1sm,
		bbar : new Ext.ux.MyToolbar({
					store : codeManStore1
				})
			// grid分页控件
		});
	var codeManStore11 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/codeManAction!queryCodeManOut.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'codeManOutList',
							totalProperty : 'totalCount'
						}, [{
									name : 'name'
								}, {
									name : 'codeSortId'
								}])
			});

	var codeManGrid11 = new Ext.grid.GridPanel({
		height : 300,
		width : 300,
		hidden : false,
		header : false,
		// stripeRows: true,
		// autoHeight : true,
		// autoScroll:true,
		enableColumnMove : false,// 设置表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
			header : '字典目录名称',
			dataIndex : 'name',
			// menuDisabled : true,
			align : "center",
			renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="字典目录" ext:qtip="'
						+ val + '">' + val + '</div>';
				return html;
			}
		}, {
			header : "字典目录",
			dataIndex : 'codeSortId',
			hidden : true,
			// menuDisabled : true,
			align : "center"
		}]),
		ds : codeManStore11,
		bbar : new Ext.ux.MyToolbar({
					store : codeManStore11
				})
			// grid分页控件
		});

	var codeManStore2 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/codeManAction!queryCodeManInSub.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'codeManInSubList',
							totalProperty : 'totalCount'
						}, [{
									name : 'dictNo'
								}, {
									name : 'dictName'
								}])
			});

	var codeManGrid2sm = new Ext.grid.RowSelectionModel();
	var codeManGrid2 = new Ext.grid.GridPanel({
		height : 300,
		hidden : false,
		enableColumnMove : false,// 设置表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
					header : "字典数据编码",
					dataIndex : 'dictNo',
					menuDisabled : true,
					align : "center"
				}, {
					header : "字典数据名称",
					dataIndex : 'dictName',
					menuDisabled : true,
					align : "center"
				}]),
		ds : codeManStore2,
		sm : codeManGrid2sm,
		bbar : new Ext.ux.MyToolbar({
					store : codeManStore2
				})
			// grid分页控件
		});

	var codeManStore22 = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/codeManAction!queryCodeManOutSub.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'codeManOutSubList',
							totalProperty : 'totalCount'
						}, [{
									name : 'value'
								}, {
									name : 'name'
								}])
			});
	var codeManGrid22 = new Ext.grid.GridPanel({
		height : 300,
		hidden : false,
		enableColumnMove : false,// 设置表头不可拖动
		colModel : new Ext.grid.ColumnModel([{
					header : "字典数据编码",
					dataIndex : 'value',
					menuDisabled : true,
					align : "center"
				}, {
					header : "字典数据名称",
					dataIndex : 'name',
					menuDisabled : true,
					align : "center"
				}]),
		ds : codeManStore22,
		bbar : new Ext.ux.MyToolbar({
					store : codeManStore22
				})
			// grid分页控件
		});
	// 添加营销数据目录点击事件
	codeManGrid11.addListener('cellclick', codeMancellclick);

	function codeMancellclick(codeManGrid11, rowIndex, columnIndex, e) {
		var record = codeManGrid11.getStore().getAt(rowIndex); // Get the
		// Record
		var codeSortId = codeManGrid11.getColumnModel()
				.getDataIndex(columnIndex + 1); // Get field name
		codeSortId = record.get(codeSortId);
		codeManStore22.load({
					params : {
						sortId : codeSortId,
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};
	// 添加内部数据目录点击事件
	codeManGrid1.addListener('cellclick', codeManIncellclick);
	function codeManIncellclick(codeManGrid1, rowIndex, columnIndex, e) {
		var record = codeManGrid1.getStore().getAt(rowIndex); // Get the
		// Record
		var codeSortId = codeManGrid1.getColumnModel().getDataIndex(columnIndex
				+ 1); // Get field name
		codeSortId = record.get(codeSortId);
		codeMandict_catalog = codeSortId;
		// alert(codeSortId);
		codeManStore2.load({
					params : {
						sortId : codeSortId,
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};
	var codeManPanelLeft = new Ext.Panel({
				plain : true,
				layout : 'card',
				activeItem : 1,
				buttonAlign : 'left',
				monitorResize : true,
				border : false,
				title : '目录',
				height : 370,
				items : [codeManGrid1, codeManGrid11],
				buttons : [{
							text : "增加",
							disabled : true,
							id : 'codeManAddButton1',
							width : 50,
							listeners : {
								"click" : function() {								
							var text1 = 'A';
							var text2 = 'B';
							Ext.MessageBox.prompt('提示', '请输入字典目录名称:',
									showResultText);
							function showResultText(btn, text) {
								text1 = text;
								if (btn != 'ok') {
									// Ext.Msg.alert('提示', '你疯了');
									return true;
								} else {

									Ext.MessageBox.prompt('提示', '请输入字典目录:',
											showResultText2);
								}
							}
							function showResultText2(btn, text) {
								text2 = text;
								if (btn != 'ok') {
									return true;
								} else {
									var modelObj = new Object();
									modelObj.dictCatalogName = text1;
									modelObj.dictCatalog = text2;
									var modelRecord = new Ext.data.Record(modelObj);
									codeManStore1.add(modelRecord);
									codeManSQL += "insert into b_sys_dictionary(dict_id,dict_catalog,dict_catalog_name) values("+10000+",'"+text2+"','"+text1+"');";
								}
							}
							// codeManStore1.load();
						
								}
							}
						}, {
							text : "修改",
							disabled : true,
							id : 'codeManUpdateButton1',
							width : 50,
							listeners : {
								"click" : function() {
									
							function showResultText3(btn, text) {
									text1 = text;
									if (btn != 'ok') {
										// Ext.Msg.alert('提示', '你疯了');
										return true;
									} else {

										Ext.MessageBox.prompt('提示', '请输入字典目录:',
												showResultText4);
									}
								}
								function showResultText4(btn, text) {
									text2 = text;
									if (btn != 'ok') {
										return true;
									} else {
										recs[0].set('dictCatalogName',text1);
										recs[0].set('dictCatalog',text2);
										codeManSQL +="update b_sys_dictionary set dict_Catalog_name='"+text1+"', dict_catalog='"+text2+"' where dict_catalog = '"+codeMandict_catalog+"';";
									}
								}
							var recs = codeManGrid1sm.getSelections();
							if (recs[0] != undefined) {
								var text1 = 'A';
								var text2 = 'B';
								Ext.MessageBox.prompt('提示', '请输入字典数据名称:',
										showResultText3);								
								// codeManStore1.load();
							}
						
								}
							}
						}, {
							text : "删除",
							disabled : true,
							id : 'codeManDeleteButton1',
							width : 50,
							listeners : {
								"click" : function() {
							var recs = codeManGrid1sm.getSelections();
							codeManStore1.remove(recs);
								var dictCatalog = recs[0].get('dictCatalog');
							codeManSQL +="delete from b_sys_dictionary where dict_catalog = '"+codeMandict_catalog+"';";
								}
							}
						}]
			});
	var codeManPanelRight = new Ext.Panel({
				plain : true,
				layout : 'card',
				activeItem : 1,
				buttonAlign : 'left',
				monitorResize : true,
				border : false,
				title : '明细',
				height : 370,
				items : [codeManGrid2, codeManGrid22],
				buttons : [{
					text : "增加",
					disabled : true,
					id : 'codeManAddButton2',
					width : 50,
					listeners : {
						"click" : function() {
							var text1 = 'A';
							var text2 = 'B';
							Ext.MessageBox.prompt('提示', '请输入字典数据编码:',
									showResultText);
							function showResultText(btn, text) {
								text1 = text;
								if (btn != 'ok') {
									// Ext.Msg.alert('提示', '你疯了');
									return true;
								} else {

									Ext.MessageBox.prompt('提示', '请输入字典数据名称:',
											showResultText2);
								}
							}
							function showResultText2(btn, text) {
								text2 = text;
								if (btn != 'ok') {
									return true;
								} else {
									var modelObj = new Object();
									modelObj.dictNo = text1;
									modelObj.dictName = text2;
									var modelRecord = new Ext.data.Record(modelObj);
									codeManStore2.add(modelRecord);
									codeManSQL += "insert into b_sys_dictionary(dict_id,dict_catalog,dict_no,dict_name) values("+10000+",'"+codeMandict_catalog+"','"+text1+"','"+text2+"');";
								
								}
							}
							// codeManStore1.load();
						}
					}
				}, {
					text : "修改",
					disabled : true,
					id : 'codeManUpdateButton2',
					width : 50,
					listeners : {
						"click" : function() {
							function showResultText3(btn, text) {
									text1 = text;
									if (btn != 'ok') {
										// Ext.Msg.alert('提示', '你疯了');
										return true;
									} else {

										Ext.MessageBox.prompt('提示', '请输入字典数据名称:',
												showResultText4);
									}
								}
								function showResultText4(btn, text) {
									text2 = text;
									if (btn != 'ok') {
										return true;
									} else {
										recs[0].set('dictNo',text1);
										recs[0].set('dictName',text2);
										codeManSQL +="update b_sys_dictionary set dict_no='"+text1+"', dict_name='"+text2+"' where dict_catalog = '"+codeMandict_catalog+"' and dict_no = '"+x+"';";
									}
								}
							var recs = codeManGrid2sm.getSelections();
							if (recs[0] != undefined) {
								var x = recs[0].get('dictNo');
								var y = recs[0].get('dictName');

								var text1 = 'A';
								var text2 = 'B';
								Ext.MessageBox.prompt('提示', '请输入字典数据编码:',
										showResultText3);								
								// codeManStore1.load();
							}
						}
					}
				}, {
					text : "删除",
					disabled : true,
					id : 'codeManDeleteButton2',
					width : 50,
					listeners : {
						"click" : function() {
							var recs = codeManGrid2sm.getSelections();
							codeManStore2.remove(recs);
								var dictNo = recs[0].get('dictNo');
							codeManSQL +="delete from b_sys_dictionary where dict_catalog = '"+codeMandict_catalog+"' and dict_No = '"+dictNo+"';";
						}
					}
				}]
			});
	var codeManPanelCenter = new Ext.Panel({
				plain : true,
				region : 'center',
				layout : 'fit',
				border : false,
				// title : 'CNETER',
				items : [{
							baseCls : "x-plain",
							layout : "column",
							border : false,
							items : [{
										columnWidth : .4,
										style : "padding-right:2px",
										items : [codeManPanelLeft]
									}, {
										columnWidth : .6,
										style : "padding-left:2px",
										items : [codeManPanelRight]
									}]
						}]
			});
	// 设置顶层的公变采集点查询panel
	var codeManpanel = new Ext.Panel({
				layout : 'border',
				autoScroll : true,
				border : false,
				buttonAlign : 'center',
				items : [codeManPanelTop, codeManPanelCenter],
				buttons : [{
							text : "保存",
							disabled : true,
							id : 'codeManSaveButton',
							width : 100
							//,
//							listeners : {
//								"click" : function() {
//									//codeManSQL+="commit;";								
//											Ext.Ajax.request({
//					url : 'sysman/codeManAction!codeManSet.action',
//					params : {
//						sql:codeManSQL
//					},
//					success : function(response) {
//						Ext.Msg.alert('提示', '保存成功');
//					}
//				});
//				codeManSQL = '';
//								}
//							}
						}]
			});
	renderModel(codeManpanel, '编码管理');
	// 去掉左边树显示
	// Ext.getCmp('mainwest').collapse();
	codeManStore11.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
});
