/**
 * author Mengyuan
 */

var rowNum;
var rowNum1;
var rowNum2;

var arrayToList;
var taskModel_sm = new Ext.grid.CheckboxSelectionModel();

// ----任务模板选择window开始

function openWindow_MYtaskWindow() {

	var taskModel_cm = new Ext.grid.ColumnModel([taskModel_sm, {
		header : '任务模板ID',
		dataIndex : 'templateId',
		sortable : true,
		align : 'center'
			// hidden : true
		}, {
		header : '任务模板名称',
		dataIndex : 'templateName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var html = '<span ext:qtitle="任务模板名称:" ext:qtip="' + v + '">' + v
					+ '</span>';
			return '<div align = "left">' + html + '</div>';
		}
	}, {
		header : '数据类型',
		dataIndex : 'dataName',
		sortable : true,
		align : 'center'
	}, {
		header : '任务属性',
		dataIndex : 'taskPropertyName',
		sortable : true,
		align : 'center'
	}]);

	var taskModel_store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryTTaskT.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'tTaskTemplateBeanList',
							totalProperty : 'totalCount',
							idProperty : 'templateId'
						}, [{
									name : 'templateId'
								}, {
									name : 'templateName'
								}, {
									name : 'dataName'
								}, {
									name : 'taskPropertyName'
								}])
			});

	var taskModel_grid = new Ext.grid.GridPanel({
				id : 'taskModel_grid',
				border : false,
				layout : 'form',
				store : taskModel_store,
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				bbar : new Ext.ux.MyToolbar({
							store : taskModel_store,
							enableExpAll : true, // excel导出全部数据
							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						}),
				cm : taskModel_cm,
				sm : taskModel_sm,
				buttonAlign : 'center',
				buttons : [{
							text : '保存',
							width : 60,
							handler : function() {
								var recs;
								var data = '';
								var array = new Array();
								recs = taskModel_sm.getSelections();// 点选到的所有行记录

								/*
								 * if (recs == null || recs.length < 1) {
								 * Ext.Msg.alert('', "请选择任务模板"); return true; }
								 */
								for (var i = 0; i < recs.length; i++) {// 遍历所有行点选记录
									array[i] = recs[i].get('templateId');
									if (data != '') {
										data = data + ','
												+ recs[i].get('templateId');
									} else {
										data = recs[i].get('templateId');
									}
								}

								Ext.getCmp("tepIDList_tmnlTaskConfig")
										.setValue(data);
								// Ext.Msg.alert('提示', "保存成功");

								window_taskModel.close();
							}
						}, {
							text : '关闭',
							width : 60,
							handler : function() {
								window_taskModel.close();
							}
						}]
			});

	taskModel_store.load();
	taskModel_store.on('load', function() {
				var w = Ext.getCmp("tepIDList_tmnlTaskConfig").getValue();
				arrayToList = w.split(",");// textField模板ID列表
				var recordsList = new Array();
				for (var i = 0; i < arrayToList.length; i++) {
					recordsList[i] = taskModel_store.getById(arrayToList[i]);
				}
				taskModel_sm.selectRecords(recordsList);
			})

	var window_taskModel = new Ext.Window({
				name : 'window_taskModel',
				title : '任务模板选择',
				width : 700,
				height : 450,
				layout : 'fit',
				border : false,
				modal : true,// true时表示window显示时对其后面的一切内容进行遮盖，默认为false
				resizable : false,
				autoScroll : true,
				closeAction : 'close',
				items : [taskModel_grid]
			});
	window_taskModel.show();

	// function gettaskSelect() {
	// var w = Ext.getCmp("tepIDList_tmnlTaskConfig").getValue();
	// arrayToList = w.split(",");// textField模板ID列表
	// var recordsList = new Array();
	// for (var i = 0; i < arrayToList.length; i++) {
	// recordsList[i] = taskModel_store.getById(arrayToList[i]);
	// }
	// taskModel_sm.selectRecords(recordsList);
	// }
}

// --------------- 任务模板选择window结束


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
/* 查看任务模板-------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
var gtctaskIdText1_M = new Ext.form.TextField({
			name : 'templateId',
			hidden : true,
			anchor : '100%'
		});
var gtcTaskNameText_M = new Ext.form.TextField({
			name : 'templateName',
			readOnly : true,
			fieldLabel : '任务名称',
			anchor : '100%'
		});
/* 数据类型 */
var gtcCurrDataRadio_M = new Ext.form.Radio({
			name : "dataType",
			inputValue : "1",
			boxLabel : "实时数据",
			checked : true
		});
var gtcHisDataRadio_M = new Ext.form.Radio({
			name : "dataType",
			inputValue : "2",
			boxLabel : "历史数据"
		});
var gtcdataTypeRg_M = new Ext.form.RadioGroup({
			anchor : '90%',
			fieldLabel : '',
			labelWidth : 1,
			border : false,
			items : [gtcCurrDataRadio_M, gtcHisDataRadio_M]
		});
/* 时间单位 */
var gtctimeData_M = [['0', '分'], ['2', '日'], ['3', '月']];
var gtctimeComStore_M = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : gtctimeData_M
		});
var gtctimeCombox_M = new Ext.form.ComboBox({
			store : gtctimeComStore_M,
			displayField : 'text',
			valueField : 'value',
			editable : false,
			readOnly : true,
			name : 'reportCycleUnit',
			mode : 'local',
			fieldLabel : '',
			forceSelection : true,
			triggerAction : 'all',
			selectOnFocus : true,
			anchor : '100%'
		});
/* 采集任务周期 */
var gtcreportCycleData_M = [['5', '5'], ['15', '15'], ['30', '30'], ['60', '60']];
var gtcreportCycleStore_M = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : gtcreportCycleData_M
		});
var gtcreportCycleCombox_M = new Ext.form.ComboBox({
			store : gtcreportCycleStore_M,
			fieldLabel : '采集周期',
			displayField : 'text',
			valueField : 'value',
			name : 'reportCycle',
			readOnly : true,
			editable : false,
			mode : 'local',
			forceSelection : true,
			triggerAction : 'all',
			selectOnFocus : true,
			anchor : '100%'
		});
/* 任务属性 */
var gtcpropoComStore_M = new Ext.data.JsonStore({
			url : "sysman/taskModelMan!queryTaskPropertyJson.action",
			fields : ['taskProperty', 'taskPropertyName'],
			root : "tTaskPropertyList"
		});

var gtcpropoCombox_M = new Ext.form.ComboBox({
			store : gtcpropoComStore_M,
			name : 'taskProperty',
			displayField : 'taskPropertyName',
			valueField : 'taskProperty',
			typeAhead : true,
			editable : false,
			readOnly : true,
			mode : 'remote',
			fieldLabel : '任务属性',
			labelSeparator : '',
			forceSelection : true,
			triggerAction : 'all',
			selectOnFocus : true,
			anchor : '100%'
		});
var gtctaskNoNumField_M = new Ext.form.NumberField({
			fieldLabel : '任务号',
			name : 'taskNo',
			labelSeparator : '',
			readOnly : true,
			anchor : '100%'
		});
var gtcreferenceTimeField_M = new Ext.form.TextField({
			fieldLabel : '采集开始基准时间',
			name : 'referenceTime',
			readOnly : true,
			anchor : '100%'
		});
var gtcrandomValueNumField_M = new Ext.form.NumberField({
			fieldLabel : '采集随机最大数',
			name : 'randomValue',
			readOnly : true,
			anchor : '100%'
		});
var gtcrandomValueUnitField_M = new Ext.form.TextField({
			readOnly : true,
			value : '秒',
			name : 'randomValueUnit',
			anchor : '100%'
		});
var gtcmasterRNumField_M = new Ext.form.NumberField({
			fieldLabel : '单次采集数据点数',
			readOnly : true,
			name : 'masterR',
			anchor : '100%'
		});
var gtcrecallPolicyField_M = new Ext.form.TextField({
			fieldLabel : '补召策略',
			readOnly : true,
			name : 'recallPolicy',
			anchor : '100%'
		});
/* 左边的表单 */
var gtcleftForm_M = new Ext.FormPanel({
			defaultType : 'panel',
			labelAlign : 'right',
			labelWidth : 100,
			monitorResize : true,
			layout : 'table',
			border : false,
			bodyStyle : 'padding:5px 5px 0px 5px',
			layoutConfig : {
				columns : 2
			},
			defaults : {
				width : 300,
				height : 40,
				colspan : 2
			},
			items : [{
						border : false,
						layout : 'form',
						items : [gtctaskIdText1_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcTaskNameText_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						width : 317,
						items : [gtcpropoCombox_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtctaskNoNumField_M]
					}, {
						border : false,
						layout : 'form',
						items : [gtcreferenceTimeField_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 1,
						width : 253,
						items : [gtcrandomValueNumField_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 1,
						width : 35,
						labelWidth : 1,
						items : [gtcrandomValueUnitField_M]
					}, {
						border : false,
						layout : 'form',
						width : 270,
						colspan : 1,
						items : [gtcreportCycleCombox_M]
					}, {
						border : false,
						layout : 'form',
						width : 52,
						colspan : 1,
						labelWidth : 1,
						items : [gtctimeCombox_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcmasterRNumField_M]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcrecallPolicyField_M]
					}
			// ,{
			// border:false,
			// layout:'form',
			// colspan:2,
			// items:[isShareCheck]
			// }
			]
		});
/* 数据项Panel---------------------------------------------------------------- */
var gtcitemSelectStore_M = new Ext.data.JsonStore({
			proxy : new Ext.data.MemoryProxy(),
			fields : ['protocolNo', 'protocolName', 'dataType'],
			root : "BClearProtocolList"
		});
var gtcselectPanel_M = new Ext.Panel({
			title : '数据项',
			buttonAlign : 'center',
			monitorResize : true,
			border : false,
			lableWidth : 1,
			width : 260,
			layout : 'fit',
			items : [{
						xtype : 'multiselect',
						fieldLabel : '',
						labelSeparator : '',
						name : 'taskData',
						displayField : 'protocolName',
						valueField : 'protocolNo',
						hiddenName : 'name',
						width : 260,
						height : 300,
						allowBlank : true,
						store : gtcitemSelectStore_M,
						ddReorder : false
					}]
		});
var gtcrightForm_M = new Ext.Panel({
			bodyStyle : 'padding:5px 5px 8px 10px',
			border : false,
			lableWidth : 1,
			layout : 'form',
			items : [{
						xtype : 'panel',
						border : false,
						layout : 'fit',
						items : [gtcdataTypeRg_M]
					}, gtcselectPanel_M]
		});

var taskTemplateInfoWin_M = new Ext.Window({
			title : '任务模板明细',
			frame : true,
			width : 650,
			height : 420,
			layout : 'column',
			autoScroll : true,
			padding : '5px',
			modal : true,
			plain : true,// 设置背景颜色
			resizable : false,// 不可移动
			buttonAlign : "center",// 按钮的位置
			closeAction : "hide",// 将窗体隐藏而并不销毁
			items : [{
						columnWidth : .55,
						border : false,
						items : [gtcleftForm_M]
					}, {
						columnWidth : .45,
						border : false,
						items : [gtcrightForm_M]
					}]
		});
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

function openTaskWindow_MY(ID) {
	gtcpropoComStore_M.load();
	gtcleftForm_M.getForm().reset();
	gtcitemSelectStore_M.removeAll();
	Ext.Ajax.request({
				url : 'sysman/taskModelMan!queryTaskTemplateJson.action',
				params : {
					templateId : ID
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (!result.success) {
						var msg;
						if (!result.errors.msg)
							msg = '未知错误！';
						else
							msg = result.errors.msg;
						Ext.MessageBox.alert('后台错误', msg);
					}
					if (result.TTaskTemplate != null) {
						gtcleftForm_M.getForm().setValues(result.TTaskTemplate);
						gtcrandomValueUnitField_M.setValue('秒');
						var value = result.TTaskTemplate.dataType;
						if (value == '1') {
							gtcCurrDataRadio_M.setValue(true);
							gtcHisDataRadio_M.setValue(false);

							gtcCurrDataRadio_M.setDisabled(false);
							gtcHisDataRadio_M.setDisabled(true);
						} else if (value == '2') {
							gtcHisDataRadio_M.setValue(true);
							gtcCurrDataRadio_M.setValue(false);

							gtcHisDataRadio_M.setDisabled(false);
							gtcCurrDataRadio_M.setDisabled(true);
						}
						gtcitemSelectStore_M.loadData(result);
					}
				}
			});
	taskTemplateInfoWin_M.show();
}		
		
		
		

Ext.onReady(function() {

	Ext.override(Ext.grid.RowNumberer, {
				renderer : function(v, p, record, rowIndex) {
					if (this.rowspan) {
						p.cellAttr = 'rowspan="' + this.rowspan + '"';
					}
					return rowIndex + 1;
				}
			});
	rowNum = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (tmnlTaskStore && tmnlTaskStore.lastOptions
							&& tmnlTaskStore.lastOptions.params) {
						startRow = tmnlTaskStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	rowNum1 = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (uesrStore && uesrStore.lastOptions
							&& uesrStore.lastOptions.params) {
						startRow = uesrStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	rowNum2 = new Ext.grid.RowNumberer({
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (taskModel_store && taskModel_store.lastOptions
							&& taskModel_store.lastOptions.params) {
						startRow = taskModel_store.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});

	// combo***************************
	// 终端厂家
	var tmnlFactoryStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryTmnlFactory.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlProtSentSetupBeanList'
						}, [{
									name : 'factoryCode'
								}, {
									name : 'factoryName'
								}])
			});
	tmnlFactoryStore.load();

	var tmnlFactoryCombo = new Ext.form.ComboBox({
				store : tmnlFactoryStore,
				id : 'tmnlFactoryName_tmnlTaskConfig',
				name : 'factoryCode',
				fieldLabel : '终端厂家',
				anchor : '95%',
				valueField : 'factoryCode',
				displayField : 'factoryName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	var tmnlFactory1Combo = new Ext.form.ComboBox({
				store : tmnlFactoryStore,
				id : 'tmnlFactory1Name_tmnlTaskConfig',
				name : 'factoryCode',
				fieldLabel : '终端厂家',
				anchor : '95%',
				valueField : 'factoryCode',
				displayField : 'factoryName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	// 终端型号
	var tmnlModeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryTmnlMode.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlProtSentSetupBeanList'
						}, [{
									name : 'modeCode'
								}, {
									name : 'modeName'
								}])
			});
	tmnlModeStore.load();

	var tmnlModeCombo = new Ext.form.ComboBox({
				store : tmnlModeStore,
				id : 'tmnlModeName_tmnlTaskConfig',
				name : 'modelCode',
				fieldLabel : '终端型号',
				anchor : '95%',
				valueField : 'modeCode',
				displayField : 'modeName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	var tmnlMode1Combo = new Ext.form.ComboBox({
				store : tmnlModeStore,
				id : 'tmnlMode1Name_tmnlTaskConfig',
				name : 'modelCode',
				fieldLabel : '终端型号',
				anchor : '95%',
				valueField : 'modeCode',
				displayField : 'modeName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	// 终端类型
	var tmnlTypeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryTmnlType.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlProtSentSetupBeanList'
						}, [{
									name : 'tmnlTypeCode'
								}, {
									name : 'tmnlType'
								}])
			});
	tmnlTypeStore.load();

	var tmnlTypeCombo = new Ext.form.ComboBox({
				store : tmnlTypeStore,
				id : 'tmnlTypeName_tmnlTaskConfig',
				name : 'terminalTypeCode',
				fieldLabel : '终端类型',
				anchor : '95%',
				valueField : 'tmnlTypeCode',
				displayField : 'tmnlType',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	var tmnlType1Combo = new Ext.form.ComboBox({
				store : tmnlTypeStore,
				id : 'tmnlType1Name_tmnlTaskConfig',
				name : 'terminalTypeCode',
				fieldLabel : '终端类型',
				anchor : '95%',
				valueField : 'tmnlTypeCode',
				displayField : 'tmnlType',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	// 采集方式
	var commModeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryCommMode.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlProtSentSetupBeanList'
						}, [{
									name : 'commModeCode'
								}, {
									name : 'commMode'
								}])
			});
	commModeStore.load();

	var commModeCombo = new Ext.form.ComboBox({
				store : commModeStore,
				id : 'commModeName_tmnlTaskConfig',
				name : 'collMode',
				fieldLabel : '采集方式',
				anchor : '95%',
				valueField : 'commModeCode',
				displayField : 'commMode',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	var data_lll = [['', '--请选择--'], ['0', '主站主动召测'], ['1', '终端自动上送']];
	var sentUpModeStore = new Ext.data.SimpleStore({
				fields : ['value', 'text'],
				data : data_lll
			});

	// 上送方式
	/*
	 * var sentUpModeStore = new Ext.data.Store({ proxy : new
	 * Ext.data.HttpProxy({ url :
	 * 'sysman/tmnlTaskConfigAction!querySentUpMode.action' }), reader : new
	 * Ext.data.JsonReader({ root : 'iTmnlProtSentSetupBeanList' }, [{ name :
	 * 'sendUpMode' },{ name : 'sendUpModeCode' }]) });
	 */

	// sentUpModeStore.load();
	var sentUpModeCombo = new Ext.form.ComboBox({
				store : sentUpModeStore,
				id : 'sentUpModeName_tmnlTaskConfig',
				name : 'sendUpMode',
				fieldLabel : '上送方式',
				anchor : '95%',
				valueField : 'value',
				displayField : 'text',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'local',
				emptyText : '--请选择--'
			});

	// 通讯规约
	var protocolCodeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'sysman/tmnlTaskConfigAction!queryProtocolCode.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'iTmnlProtSentSetupBeanList'
				}, [{
							name : 'protocolCode'
						}, {
							name : 'protocolName'
						}])
	});
	protocolCodeStore.load();

	var protocolCodeCombo = new Ext.form.ComboBox({
				store : protocolCodeStore,
				id : 'protocolCodeName_tmnlTaskConfig',
				name : 'protocolCode',
				fieldLabel : '通讯规约',
				anchor : '95%',
				valueField : 'protocolCode',
				displayField : 'protocolName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	var protocolCode1Combo = new Ext.form.ComboBox({
				store : protocolCodeStore,
				id : 'protocolCode1Name_tmnlTaskConfig',
				name : 'protocolCode',
				fieldLabel : '通讯规约',
				anchor : '95%',
				valueField : 'protocolCode',
				displayField : 'protocolName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	// 用户类型
	var consTypeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryConsType.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlTaskSetupBeanList'
						}, [{
									name : 'consType'
								}, {
									name : 'consTypeName'
								}])
			});
	consTypeStore.load();

	var consTypeCombo = new Ext.form.ComboBox({
				store : consTypeStore,
				id : 'consTypeName_tmnlTaskConfig',
				name : 'consType',
				fieldLabel : '用户类型',
				anchor : '95%',
				valueField : 'consType',
				displayField : 'consTypeName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});

	// 容量等级cap_grade
	var capGradeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'sysman/tmnlTaskConfigAction!queryCapGrade.action'
						}),
				reader : new Ext.data.JsonReader({
							root : 'iTmnlTaskSetupBeanList'
						}, [{
									name : 'capGradeNo'
								}, {
									name : 'capGradeName'
								}])
			});
	capGradeStore.load();

	var capGradeCombo = new Ext.form.ComboBox({
				store : capGradeStore,
				id : 'capGradeName_tmnlTaskConfig',
				name : 'capGradeNo',
				fieldLabel : '容量等级',
				anchor : '95%',
				valueField : 'capGradeNo',
				displayField : 'capGradeName',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : '',
				forceSelection : true,
				selectOnFocus : true,
				mode : 'remote',
				emptyText : '--请选择--'
			});
	// combo************************************************

	var topPanel_1 = new Ext.Panel({
				border : false,
				layout : 'column',
				bodyStyle : 'padding:8px 0px',
				items : [{
							columnWidth : .01,
							layout : 'form',
							xtype : 'hidden',
							id : 'protSendSetupId_tmnTaskConfig',
							name : 'protSendSetupId'
						}, {
							columnWidth : .27,
							border : false,
							labelAlign : 'right',
							labelWidth : 70,
							layout : 'form',
							items : [tmnlFactoryCombo]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [tmnlModeCombo]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [tmnlTypeCombo]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [commModeCombo]
						}]
			});

	var topPanel_2 = new Ext.Panel({
				border : false,
				layout : 'column',
				items : [{
							columnWidth : .27,
							border : false,
							layout : 'column',
							items : [{
										columnWidth : .55,
										border : false,
										labelAlign : 'right',
										labelWidth : 70,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													id : 'tmnlAddrS_tmnlTaskConfig',
													labelSeparator : '',
													anchor : '100%',
													name : 'tmnlAddrS',
													fieldLabel : '终端地址从',
													emptyText : '--请输入--'
												}]
									}, {
										columnWidth : .42,
										border : false,
										labelAlign : 'right',
										labelWidth : 15,
										layout : 'form',
										items : [{
													xtype : 'textfield',
													id : 'tmnlAddrD_tmnlTaskConfig',
													labelSeparator : '',
													anchor : '95%',
													name : 'tmnlAddrE',
													fieldLabel : '到',
													emptyText : '--请输入--'
												}]
									}]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [sentUpModeCombo]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [protocolCodeCombo]
						}, {
							columnWidth : .24,
							border : false,
							labelAlign : 'right',
							labelWidth : 9,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										boxLabel : '是否网络表',
										id : 'attachMeterFlag_tmnlTaskConfig',
										name : 'attachMeterFlag',
										inputValue : 1
									}]
						}]
			});

	// first终端任务上送--顶部panel
	var topPanel = new Ext.FormPanel({
		id : 'topPanel_tmnlTask',
		layout : 'form',
		region : 'north',
		height : 105,
		border : true,
		items : [topPanel_1, topPanel_2, {
			layout : 'column',
			border : false,
			items : [{
						columnWidth : .38,
						border : false,
						baseCls : "x-plain",
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '',
									hidden : true,
									labelSeparator : '',
									anchor : '100%'
								}]
					}, {
						columnWidth : .08,
						layout : 'form',
						border : false,
						bodyStyle : 'padding:3px 0px',
						items : [{
									xtype : 'button',
									text : '新增规则',
									handler : addInfo
								}]
					}, {
						columnWidth : .08,
						layout : 'form',
						border : false,
						bodyStyle : 'padding:3px 0px',
						items : [{
							xtype : 'button',
							text : '删除规则',
							handler : function() {
								var gyId = topPanel
										.getForm()
										.findField('protSendSetupId_tmnTaskConfig')
										.getValue().trim();
								if (Ext.isEmpty(gyId)) {
									Ext.Msg.alert('信息', '请选择要删除的规则!');
									return;
								}
								Ext.MessageBox.confirm('确认', '是否删除该用户！',
										deleteRule);
							}
						}]
					}, {
						columnWidth : .08,
						layout : 'form',
						border : false,
						bodyStyle : 'padding:3px 0px',
						items : [{
							xtype : 'button',
							text : '保存规则',
							handler : function() {
								Ext.MessageBox.confirm('确认', '是否保存该配置?',
										saveOrUpdateInfo);
							}
						}]
					}]
		}]
	});

	var secondPanel_1 = new Ext.Panel({
				border : false,
				layout : 'column',
				bodyStyle : 'padding:8px 0px',
				items : [{
							columnWidth : .01,
							layout : 'form',
							xtype : 'hidden',
							id : 'taskSetupId_tmnTaskConfig',
							name : 'taskSetupId'
						}, {
							columnWidth : .25,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [consTypeCombo]
						}, {
							columnWidth : .25,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [capGradeCombo]
						}, {
							columnWidth : .25,
							border : false,
							labelAlign : 'right',
							labelWidth : 80,
							layout : 'form',
							items : [tmnlMode1Combo]
						}, {
							columnWidth : .25,
							border : false,
							labelAlign : 'right',
							labelWidth : 60,
							layout : 'form',
							items : [tmnlFactory1Combo]
						}]
			});

	var secondPanel_2 = new Ext.Panel({
		border : false,
		layout : 'column',
		items : [{
					columnWidth : .25,
					border : false,
					labelAlign : 'right',
					labelWidth : 60,
					layout : 'form',
					items : [protocolCode1Combo]
				}, {
					columnWidth : .25,
					border : false,
					labelAlign : 'right',
					labelWidth : 60,
					layout : 'form',
					items : [tmnlType1Combo]
				}, {
					columnWidth : .25,
					border : false,
					labelAlign : 'right',
					labelWidth : 80,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						labelSeparator : '',
						anchor : '95%',
						name : 'templateIdList',
						id : 'tepIDList_tmnlTaskConfig',
						readOnly : true,
						fieldLabel : "<a href='javascript:' onclick='openWindow_MYtaskWindow()'>任务模板</a>",
						emptyText : '--请选择--'
							// setValue : function(value) {
							// if (value == null || value == '')
							// return "";
							// var s = value.split(",");
							// var href = '';
							// for (var i = 0; i < s.length; i++) {
							// if (!Ext.isEmpty(s[i])) {
							// href = href + "[" + s[i] + "] ";
							// }
							// }
							// Ext.form.TextField.prototype.setValue.call(this,href);
							// return this;
							// }

					}]
				}]
	});

	// second用户与终端--顶部panel
	var secondPanel = new Ext.FormPanel({
				id : 'secondPanel_tmnlTask',
				layout : 'form',
				region : 'north',
				height : 105,
				border : true,
				items : [secondPanel_1, secondPanel_2, {
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .38,
								border : false,
								baseCls : "x-plain",
								layout : 'form',
								items : [{
											xtype : 'textfield',
											fieldLabel : '',
											hidden : true,
											labelSeparator : '',
											anchor : '100%'
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								bodyStyle : 'padding:3px 0px',
								border : false,
								items : [{
											xtype : 'button',
											text : '新增规则',
											handler : addInfo_1
										}]
							}, {
								columnWidth : .08,
								layout : 'form',
								bodyStyle : 'padding:3px 0px',
								border : false,
								items : [{
									xtype : 'button',
									text : '删除规则',
									handler : function() {
										var zdpz = secondPanel
												.getForm()
												.findField('taskSetupId_tmnTaskConfig')
												.getValue().trim();
										if (Ext.isEmpty(zdpz)) {
											Ext.Msg.alert('信息', '请选择要删除的规则!');
											return;
										}
										Ext.MessageBox.confirm('确认',
												'是否删除该用户！', deleteRuleH);
									}
								}]
							}, {
								columnWidth : .08,
								layout : 'form',
								bodyStyle : 'padding:3px 0px',
								border : false,
								items : [{
											xtype : 'button',
											text : '保存规则',
											handler : saveOrUpdateInfo_1
										}]
							}]
				}]

			});

	// card布局的顶部panel
	var cardPanel = new Ext.Panel({
				region : 'north',
				height : 105,
				id : 'cardPanel_TmnlTaskConfig',
				layout : 'card',
				border : false,
				activeItem : 0,
				items : [topPanel, secondPanel]
			});

	// ----下方grid 开始
	// 终端任务上送方式配置grid
	var tmnlTaskSm = new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					rowselect : function(sm, row, rec) {
						Ext.getCmp("topPanel_tmnlTask").getForm()
								.loadRecord(rec);
						// sentUpModeCombo.setValue("1");
						// tmnlFactoryCombo.setValue(rec.get('factoryCode'));
						// tmnlFactoryCombo.setValue("02");
						// alert(rec.get('factoryCode'));
						// alert(tmnlFactoryCombo.getValue());
					}
				}
			});

	var tmnlTaskCm = new Ext.grid.ColumnModel([rowNum, {
				header : "规约及上送方式ID",
				align : 'center',
				dataIndex : 'protSendSetupId',
				sortable : true,
				hidden : true
			}, {
				header : "供电单位",
				align : 'center',
				dataIndex : 'orgName',
				sortable : true,
				width : 150,
				hidden : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="供电单位:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "生产厂家",
				align : 'center',
				dataIndex : 'factoryName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="生产厂家:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "厂家代码",
				align : 'center',
				dataIndex : 'factoryCode',
				sortable : true,
				hidden : true
			}, {
				header : "终端型号",
				align : 'center',
				dataIndex : 'modeName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端型号:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "型号代码",
				align : 'center',
				dataIndex : 'modelCode',
				sortable : true,
				hidden : true
			}, {
				header : "终端类型",
				align : 'center',
				dataIndex : 'tmnlType',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端类型:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "类型代码",
				align : 'center',
				dataIndex : 'terminalTypeCode',
				sortable : true,
				hidden : true
			}, {
				header : "通讯规约",
				align : 'center',
				dataIndex : 'protocolName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="通讯规约:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "规约代码",
				align : 'center',
				dataIndex : 'protocolCode',
				sortable : true,
				hidden : true
			}, {
				header : "采集方式",
				align : 'center',
				dataIndex : 'commMode',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="采集方式:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "采集代码",
				align : 'center',
				dataIndex : 'collMode',
				sortable : true,
				hidden : true
			}, {
				header : "终端地址（开始）",
				align : 'center',
				dataIndex : 'tmnlAddrS',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端地址（开始）:" ext:qtip="' + v
							+ '">' + v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : '终端地址（结束）',
				align : 'center',
				dataIndex : 'tmnlAddrE',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端地址（结束）:" ext:qtip="' + v
							+ '">' + v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "上送方式",
				align : 'center',
				dataIndex : 'sendUpMode',
				sortable : true,
				renderer : function(value) {
					if (value == "0") {
						return '主站主动召测';
					} else if (value == "1") {
						return '终端自动上送';
					} else {
						return '';
					}
				}
			}, {
				header : "是否网络表",
				align : 'center',
				dataIndex : 'attachMeterFlag',
				sortable : true,
				renderer : function(value) {
					if (value == "0") {
						return "<font color='red'>否</font>";
					} else if (value == "1") {
						return "<font color='green'>是</font>";
					}
				}
			}]);

	var tmnlTaskStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'sysman/tmnlTaskConfigAction!queryTmnlTaskConfig.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'iTmnlProtSentSetupBeanList',
					totalProperty : 'totalCount'
				}, [{
							name : 'protSendSetupId'
						}, {
							name : 'orgName'
						}, {
							name : 'factoryName'
						}, {
							name : 'factoryCode'
						}, {
							name : 'modeName'
						}, {
							name : 'modelCode'
						}, {
							name : 'tmnlType'
						}, {
							name : 'terminalTypeCode'
						}, {
							name : 'protocolName'
						}, {
							name : 'protocolCode'
						}, {
							name : 'commMode'
						}, {
							name : 'collMode'
						}, {
							name : 'tmnlAddrS'
						}, {
							name : 'tmnlAddrE'
						}, {
							name : 'sendUpMode'
						}, {
							name : 'attachMeterFlag'
						}])
	});

	var tmnlTaskGrid = new Ext.grid.GridPanel({
				store : tmnlTaskStore,
				id : 'tmnlTaskGrid_tmnlTask',
				sm : tmnlTaskSm,
				cm : tmnlTaskCm,
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				region : 'center',
				bbar : new Ext.ux.MyToolbar({
							store : tmnlTaskStore,
//							enableExpAll : true, // excel导出全部数据
//							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
	tmnlTaskStore.load();

	// 用户与终端任务配置grid
	var userSm = new Ext.grid.RowSelectionModel({
				singleSelect : true,
				listeners : {
					rowselect : function(sm, row, rec) {
						Ext.getCmp("secondPanel_tmnlTask").getForm()
								.loadRecord(rec);
					}
				}
			});

	var uesrCm = new Ext.grid.ColumnModel([rowNum1, {
				header : "终端任务配置ID",
				align : 'center',
				dataIndex : 'taskSetupId',
				sortable : true,
				hidden : true
			}, {
				header : "任务模板",
				align : 'center',
				dataIndex : 'templateIdList',
				sortable : true,
				renderer : function(value) {
					if (value == null || value == '')
						return "";
					var s = value.split(",");
					var href = '';
					for (var i = 0; i < s.length; i++) {
						if (!Ext.isEmpty(s[i])) {
							href = href
									+ "<a href='javascript:' onclick='openTaskWindow_MY("
									+ s[i] + ");'>[" + s[i] + "]</a>"
									+ "&nbsp;";
						}
					}
					return href;
				}
			}, {
				header : "供电单位",
				align : 'center',
				dataIndex : 'orgName',
				sortable : true,
				width : 160,
				hidden : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="供电单位:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "用户类型",
				align : 'center',
				dataIndex : 'consTypeName',
				sortable : true
			}, {
				header : "用户代码",
				align : 'center',
				dataIndex : 'consType',
				sortable : true,
				hidden : true
			}, {
				header : "容量等级",
				align : 'center',
				dataIndex : 'capGradeName',
				sortable : true,
				renderer : function(val) {
					if (val == null) {
						return "";
					}
					return "<div align = 'left'>" + val + "</div>";
				}
			}, {
				header : "容量代码",
				align : 'center',
				dataIndex : 'capGradeNo',
				sortable : true,
				hidden : true
			}, {
				header : "终端型号",
				align : 'center',
				dataIndex : 'modeName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端型号:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "型号代码",
				align : 'center',
				dataIndex : 'modelCode',
				sortable : true,
				hidden : true
			}, {
				header : "终端厂家",
				align : 'center',
				dataIndex : 'factoryName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端厂家:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "厂家代码",
				align : 'center',
				dataIndex : 'factoryCode',
				sortable : true,
				hidden : true
			}, {
				header : "通讯规约",
				align : 'center',
				dataIndex : 'protocolName',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="通讯规约:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "规约代码",
				align : 'center',
				dataIndex : 'protocolCode',
				sortable : true,
				hidden : true
			}, {
				header : "终端类型",
				align : 'center',
				dataIndex : 'tmnlType',
				sortable : true,
				renderer : function(v) {
					if (v == null) {
						return "";
					}
					var html = '<span ext:qtitle="终端类型:" ext:qtip="' + v + '">'
							+ v + '</span>';
					return '<div align = "left">' + html + '</div>';
				}
			}, {
				header : "类型代码",
				align : 'center',
				dataIndex : 'terminalTypeCode',
				sortable : true,
				hidden : true
			}]);

	var uesrStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'sysman/tmnlTaskConfigAction!queryUserTmnlConfig.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'iTmnlTaskSetupBeanList',
					totalProperty : 'totalCount'
				}, [{
							name : 'taskSetupId'
						}, {
							name : 'templateIdList'
						}, {
							name : 'orgName'
						}, {
							name : 'consTypeName'
						}, {
							name : 'consType'
						}, {
							name : 'capGradeName'
						}, {
							name : 'capGradeNo'
						}, {
							name : 'modeName'
						}, {
							name : 'modelCode'
						}, {
							name : 'factoryName'
						}, {
							name : 'factoryCode'
						}, {
							name : 'protocolName'
						}, {
							name : 'protocolCode'
						}, {
							name : 'tmnlType'
						}, {
							name : 'terminalTypeCode'
						}])
	});

	var uesrGrid = new Ext.grid.GridPanel({
				store : uesrStore,
				id : 'uesrGrid_tmnlTask',
				cm : uesrCm,
				sm : userSm,
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				region : 'center',
				bbar : new Ext.ux.MyToolbar({
							store : uesrStore,
//							enableExpAll : true, // excel导出全部数据
//							expAllText : "全部",
							enableExpPage : true, // excel仅导出当前页
							expPageText : "当前页"
						})
			});
	uesrStore.load();

	// 下方的grid tab页
	var gridTab = new Ext.TabPanel({
				activeTab : 1,
				region : 'center',
				split : true,
				items : [{
							xtype : "panel",
							name : 'grid1',
							title : "终端任务上送方式配置",
							layout : 'fit',
							items : [tmnlTaskGrid]
						}, {
							xtype : "panel",
							name : 'grid2',
							title : "用户与终端任务配置",
							layout : 'fit',
							items : [uesrGrid]
						}],
				border : false
			});

	// tabPanel切换事件，顶部panel变换
	gridTab.on('tabchange', function(t, p) {
				if (p.name == "grid1") {
					cardPanel.layout.setActiveItem(0);
				} else if (p.name == "grid2") {
					cardPanel.layout.setActiveItem(1);
				}
			}, this);

	// 终端页面的新增按钮
	function addInfo() {
		Ext.getCmp('topPanel_tmnlTask').getForm().reset(); // 清空表单数据
	}
	// 用户页面的新增按钮
	function addInfo_1() {
		Ext.getCmp('secondPanel_tmnlTask').getForm().reset(); // 清空表单数据
		// Ext.getCmp('tepIDList_tmnlTaskConfig').setValue(null);
	}

	function deleteRule(btn) {
		if (btn == 'no')
			return;
		Ext.Ajax.request({
					url : 'sysman/tmnlTaskConfigAction!deleteTmnlTaskSend.action',
					params : {
						protSendSetupId : Ext
								.getCmp('protSendSetupId_tmnTaskConfig')
								.getValue()
					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '用户删除成功');
						tmnlTaskStore.load();
					}

				});
	}

	function deleteRuleH(btn) {
		if (btn == 'no')
			return;
		Ext.Ajax.request({
					url : 'sysman/tmnlTaskConfigAction!deleteUserSend.action',
					params : {
						taskSetupId : Ext.getCmp('taskSetupId_tmnTaskConfig')
								.getValue()
					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '用户删除成功');
						uesrStore.load();
					}

				});
	}

	function saveOrUpdateInfo(btn) {
		if (btn == 'no')
			return;
		// 传code代码过去，不是name
		var tmnlFactoryName = Ext.getCmp("tmnlFactoryName_tmnlTaskConfig")
				.getValue();
		var tmnlModeName = Ext.getCmp("tmnlModeName_tmnlTaskConfig").getValue();
		var tmnlTypeName = Ext.getCmp("tmnlTypeName_tmnlTaskConfig").getValue();
		var protocolCodeName = Ext.getCmp("protocolCodeName_tmnlTaskConfig")
				.getValue();
		var commModeName = Ext.getCmp("commModeName_tmnlTaskConfig").getValue();
		var tmnlAddrS = Ext.getCmp("tmnlAddrS_tmnlTaskConfig").getValue();
		var tmnlAddrD = Ext.getCmp("tmnlAddrD_tmnlTaskConfig").getValue();
		var sentUpModeName = Ext.getCmp("sentUpModeName_tmnlTaskConfig")
				.getValue();
		var attachMeterFlag = Ext.getCmp("attachMeterFlag_tmnlTaskConfig")
				.getValue();
		var protSendSetupId = Ext.getCmp("protSendSetupId_tmnTaskConfig")
				.getValue();

		if ((tmnlFactoryName == null || tmnlFactoryName == '')
				&& (tmnlModeName == null || tmnlModeName == '')
				&& (tmnlTypeName == null || tmnlTypeName == '')
				&& (protocolCodeName == null || protocolCodeName == '')
				&& (commModeName == null || commModeName == '')
				&& (tmnlAddrS == null || tmnlAddrS == '')
				&& (tmnlAddrD == null || tmnlAddrD == '')
				&& (sentUpModeName == null || sentUpModeName == '')
				&& (attachMeterFlag == null || attachMeterFlag == '')) {
			Ext.MessageBox.alert('提示', '配置项不可全部为空');
			return;
		}

		Ext.Ajax.request({
					url : 'sysman/tmnlTaskConfigAction!saveOrUpdateInfo.action',
					params : {
						// 将代码code赋值给bean中对应的code值
						'bean.factoryCode' : tmnlFactoryName,
						'bean.modelCode' : tmnlModeName,
						'bean.tmnlTypeCode' : tmnlTypeName,
						'bean.protocolCode' : protocolCodeName,
						'bean.collMode' : commModeName,
						'bean.tmnlAddrS' : tmnlAddrS,
						'bean.tmnlAddrE' : tmnlAddrD,
						'bean.sendUpMode' : sentUpModeName,
						'bean.attachMeterFlag' : attachMeterFlag,
						'bean.protSendSetupId' : Ext
								.getCmp("protSendSetupId_tmnTaskConfig")
								.getValue()
					},

					success : function(response) {
						Ext.MessageBox.alert('提示', '规则保存成功');
						var fhxx = Ext.decode(response.responseText).tmnlResult;// fhxx返回信息
						if (fhxx != "" && fhxx != null) {
							Ext.MessageBox.alert(fhxx);
						}
						tmnlTaskStore.load();
					}

				});
	}

	function saveOrUpdateInfo_1(btn) {
		if (btn == 'no')
			return;
		var tmnlFactoryName = Ext.getCmp("tmnlFactory1Name_tmnlTaskConfig")
				.getValue();
		var tmnlModeName = Ext.getCmp("tmnlMode1Name_tmnlTaskConfig")
				.getValue();
		var tmnlTypeName = Ext.getCmp("tmnlType1Name_tmnlTaskConfig")
				.getValue();
		var protocolCodeName = Ext.getCmp("protocolCode1Name_tmnlTaskConfig")
				.getValue();
		var consTypeName = Ext.getCmp("consTypeName_tmnlTaskConfig").getValue();
		var capGradeName = Ext.getCmp("capGradeName_tmnlTaskConfig").getValue();
		var templateIdList = Ext.getCmp("tepIDList_tmnlTaskConfig").getValue();
		var taskSetupId = Ext.getCmp("taskSetupId_tmnTaskConfig").getValue();

		if ((tmnlFactoryName == null || tmnlFactoryName == '')
				&& (tmnlModeName == null || tmnlModeName == '')
				&& (tmnlTypeName == null || tmnlTypeName == '')
				&& (protocolCodeName == null || protocolCodeName == '')
				&& (consTypeName == null || consTypeName == '')
				&& (capGradeName == null || capGradeName == '')
				&& (templateIdList == null || templateIdList == '')) {
			Ext.MessageBox.alert('提示', '配置项不可全部为空');
			return;
		}

		Ext.Ajax.request({
					url : 'sysman/tmnlTaskConfigAction!saveOrUpdateInfo_1.action',
					params : {
						// 将代码code赋值给bean中对应的code值
						'beanUser.factoryCode' : tmnlFactoryName,
						'beanUser.modelCode' : tmnlModeName,
						'beanUser.terminalTypeCode' : tmnlTypeName,
						'beanUser.protocolCode' : protocolCodeName,
						'beanUser.consType' : consTypeName,
						'beanUser.capGradeNo' : capGradeName,
						'beanUser.templateIdList' : templateIdList,
						'beanUser.taskSetupId' : Ext
								.getCmp("taskSetupId_tmnTaskConfig").getValue()
					},
					success : function(response) {
						Ext.MessageBox.alert('提示', '规则保存成功');
						var fhxx = Ext.decode(response.responseText).tmnlResult;// fhxx返回信息
						if (fhxx != "" && fhxx != null) {
							Ext.MessageBox.alert(fhxx);
						}
						uesrStore.load();
					}

				});
	}

	var viewPanel_tmnlTaskConfig = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [cardPanel, gridTab]
			});

	renderModel(viewPanel_tmnlTaskConfig, '终端任务配置');
});
