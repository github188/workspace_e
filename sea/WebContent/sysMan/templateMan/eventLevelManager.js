Ext.onReady(function() {
	/* 规约类型 */
	var typeComStore = new Ext.data.JsonStore({
				idProperty : 'protocolCode',
				url : './runman/abnormalhandle/eventManage!queryPortocolCode.action',
				fields : ['protocolCode', 'protocolName'],
				root : "pc",
				// autoLoad: true,
				listeners : {
					load : function() {
						gyComboBox.setValue(gyComboBox.getValue());
					}
				}
			});
	// 规约combobox
	var gyComboBox = new Ext.form.ComboBox({
				id : 'elmComboBox',
				fieldLabel : '规约',
				store : typeComStore,
				labelSeparator : '',
				bodyStyle : 'padding:10px;',
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				valueField : 'protocolCode',
				displayField : 'protocolName',
				anchor : '90%',
				emptyText : '请选择规约',
				listeners : {
					select : setTitle
				},
				selectOnFocus : true
			});

	// 规约类型json数据load
	typeComStore.load();
	// 初始化combobox的value值
	typeComStore.on("load", onstoreStoreLoad, typeComStore, true);

	// 设置combobox的初始化value值
	function onstoreStoreLoad() {
		if (typeComStore.getTotalCount() > 0) {
			gyComboBox.setValue(typeComStore.getAt(0).data.protocolCode);
			initRGAjax();
		}
	}

	// 生成规约类型对应的RadioGroup
	function setTitle() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryDataType.action',
					params : {
						// 从combobox中获取规约编码
						protocolCode : Ext.getCmp('elmComboBox').value
					},
					success : setTitleResponse
				});
	}

	function initRGAjax() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryDataType.action',
					params : {
						// 获取缺省的规约项编码
						protocolCode : typeComStore.getAt(0).data.protocolCode
					},
					success : setTitleResponse
				});
	}

	// 根据规约项名称设定规约GirdPanel的title名称
	function setTitleResponse(response) {
		var result = Ext.decode(response.responseText);
		if (result.dt.length > 0) {
			var protocolNo = result.dt[0].dataType;
			var protocolName = result.dt[0].dataName;
			var grid = Ext.getCmp('pfelmEditorGrid');
			var protcolField = Ext.getCmp('elmprotocolCode');
			protcolField.setValue(protocolNo);
			Ext.getCmp('titlelabel').setText(protocolName);
			loadGridData();
			// grid.setTitle(protocolName);
			grid.doLayout();
		}
	}

	/**
	 * 参数设置editorGridPanel相关
	 */
	// 参数设置编辑store
	var editorStore = new Ext.data.ArrayStore({
				fields : [{
							name : 'erc'
						}, {
							name : 'eventName'
						}, {
							name : 'normal'
						}, {
							name : 'lesser'
						}, {
							name : 'high'
						}, {
							name : 'eventNo'
						}, {
							name : 'eventLevel'
						}, {
							name : 'isRec'
						}]
			});

	var dataComStore2 = new Ext.data.ArrayStore({
				fields : ['dataValue', 'displayText']
			});

//    var normalCheckColumn = new Ext.grid.CheckColumn({
//        header: '一般',
//        dataIndex: 'normal',
//        width: 20,
//        align : 'center'
//     });
//    
//    var lesserCheckColumn = new Ext.grid.CheckColumn({
//        header: '次要',
//        dataIndex: 'lesser',
//        width: 20,
//        align : 'center'
//     });
//    
//    var highCheckColumn = new Ext.grid.CheckColumn({
//        header: '严重',
//        dataIndex: 'high',
//        width: 20,
//        align : 'center'
//     });

/* 查询流程状态 */
//var statusComStore = new Ext.data.JsonStore({
//		idProperty : 'statusCode',
//		url : './runman/abnormalhandle/deviceAbnormal!savaRecord.action',
//		fields : ['statusCode', 'statusName'],
//		root : "pc",
//		autoLoad: true
//	});

var levelComStore = new Ext.data.ArrayStore({
	fields : ['levelCode', 'levelName'],
	data : [["01", "一般"], ["02", "次要"], ["03", "严重"]]
})

var recComStore = new Ext.data.ArrayStore({
	fields : ['recCode', 'recName'],
	data : [["1", "打开"], ["0", "屏蔽"]]
})
    
	var userCm = new Ext.grid.ColumnModel ({
				defaults : {
					sortable : false,
					remoteSort : false
				},
				columns : [{
							header : '事件代码ERC',
							dataIndex : 'erc',
							width : 30,
							align : 'left'
						}, {
							header : '事件项目',
							dataIndex : 'eventName',
							width : 30,
							align : 'left'
						},
						{
			                header: '报警级别',
			                dataIndex: 'eventLevel',
			                width: 130,
			                editor: new Ext.form.ComboBox({
			            		store : levelComStore,
			            		triggerAction : 'all',
//			            		editable : false,
			            		mode : 'local',
			            		valueField : 'levelCode',
			            		displayField : 'levelName',
			            		selectOnFocus : true
			                }),
			                renderer : function (value) {
								if (value == null || value == '')
									return '-请选择-';
								var mc = levelComStore.query('levelCode', value);
								if (mc != null && mc.length > 0)
									return "<div align = 'left'>"
											+ mc.get(0).get('levelName') + "</div>";
								else
									return '-请选择-';
							}
			            },
			            {
			                header: '记录标志',
			                dataIndex: 'isRec',
			                width: 130,
			                editor: new Ext.form.ComboBox({
			            		store : recComStore,
			            		triggerAction : 'all',
//			            		editable : false,
			            		mode : 'local',
			            		valueField : 'recCode',
			            		displayField : 'recName',
			            		selectOnFocus : true
			                }),
			                renderer : function (value) {
								if (value == null || value == '')
									return '-请选择-';
								var mc = recComStore.query('recCode', value);
								if (mc != null && mc.length > 0)
									return "<div align = 'left'>"
											+ mc.get(0).get('recName') + "</div>";
								else
									return '-请选择-';
							}
			            }
//			            ,
//						normalCheckColumn,
//						lesserCheckColumn,
//						highCheckColumn
						]
			});

	var userGrid = new Ext.grid.EditorGridPanel({
				id : 'elmEditorGrid',
				store : editorStore,
				width : 800,
				border : false,
//				plugins: [normalCheckColumn,lesserCheckColumn,highCheckColumn],
				cm : userCm,
				autoScroll : true
			});

	function loadGridData() {
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!queryEventInfo.action',
					params : {
						protocolCode : Ext.getCmp('elmComboBox').value
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var gridData = new Array();
						for (var i = 0; i < result.pe.length; i++) {
							gridData[i] = new Array();
							var eventNo = result.pe[i].eventNo;
							var recNo = parseInt(eventNo.substring(3, 5), 16);
							gridData[i][0] = 'ERC' + recNo;
							gridData[i][1] = result.pe[i].eventName;
//							if (result.pe[i].eventLevel == '03') {
//								gridData[i][2] = false;
//								gridData[i][3] = false;
//								gridData[i][4] = true;
//							} else if (result.pe[i].eventLevel == '02'){
//								gridData[i][2] = false;
//								gridData[i][3] = true;
//								gridData[i][4] = false;
//							} else if (result.pe[i].eventLevel == '01') {
//								gridData[i][2] = true;
//								gridData[i][3] = false;
//								gridData[i][4] = false;
//							}
							gridData[i][5] = eventNo;
							gridData[i][6] = result.pe[i].eventLevel;
							gridData[i][7] = ''+result.pe[i].isRec + '';
						}
						var editorGrid = Ext.getCmp('elmEditorGrid');
						editorGrid.getStore().loadData(gridData);
						editorGrid.doLayout();
					}
				});
	}

	var itempanel = new Ext.Panel({
				// title : '终端事件记录配置设置',
				id : 'pfelmEditorGrid',
				// autoScroll : true,
				layout : 'fit',
				region : 'center',
				border : true,
				buttonAlign : 'center',
				tbar : [{
							xtype : 'label',
							id : 'titlelabel',
							text : '终端事件级别配置'
						}, {
							xtype : 'textfield',
							id : 'elmprotocolCode',
							hidden : true
						}],
				buttons : [{
							width : '100',
							text : '保存',
							handler : saveEventInfo
						}],
				items : [userGrid]
			});

	function saveEventInfo() {
		var rec = editorStore.getRange();
		Ext.getBody().mask("正在处理...");
		var eventInfo = new Array();
		for (var i = 0; i < rec.length; i++) {
			eventInfo[i] = {};
			eventInfo[i].eventNo = rec[i].get('eventNo');
			eventInfo[i].eventLevel = rec[i].get('eventLevel');
			eventInfo[i].isRec = rec[i].get('isRec');
		}
		Ext.Ajax.request({
					url : './runman/abnormalhandle/eventManage!saveEventInfo.action',
					params : {
						jsonData : Ext.encode(eventInfo)
					},
					success : function(response) {
						Ext.getBody().unmask();
						var result = Ext.decode(response.responseText);
						var isError = result.success;
						if (isError) {
							Ext.Msg.alert('提示', '事件级别更新成功');
						}
					},
					failure : function(response) {
						Ext.getBody().unmask();
					}	
		});
	};
	// ComboBox和RadioGroup面板
	var panel = new Ext.Panel({
				border : false,
				id : 'elmFormPanel',
				region : 'north',
				layout : 'border',
				split : true,
				height : 280,
				items : [{
							xtype : 'panel',
							region : 'north',
							height : 40,
							border : false,
							items : [{
										layout : 'form',
										labelAlign : 'right',
										bodyStyle : 'padding:10px 10px 10px 10px',
										width : 180,
										border : false,
										labelWidth : 30,
										items : [gyComboBox]
									}]
						}, itempanel]

			});

//	var panel = new Ext.form.FormPanel({
//				// title : '终端参数',
//				border : false,
//				id : 'emPanel',
//				layout : 'border',
//				items : [formPanel]
//			});
	renderModel(panel, '事件级别管理');
})