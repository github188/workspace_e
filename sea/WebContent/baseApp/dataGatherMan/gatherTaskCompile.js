var overFlat = false;
var h_taskTime = function(second, fn) {
	flag = 0;
	overFlat = false;
	var task_s = (second == "") ? 30 : second;
	Ext.MessageBox.show({
				title : '请等待',
				msg : '任务执行中...',
				progressText : '初始化...',
				width : 300,
				progress : true,
				closable : false,
				buttons : {
					"cancel" : "取消"
				},
				fn : function(e) {
					flag = 0;
					if (fn && typeof fn == "function") {
						fn();
					}
					clearInterval(aa);
				}
			});

	var f = function() {
		return function() {
			flag = flag + 1;

			if (flag == task_s + 1 || overFlat) {
				Ext.MessageBox.hide();
				flag = 0;
				if (fn && typeof fn == "function") {
					fn();
				}
				clearInterval(aa);
			} else {
				var i = flag;
				Ext.MessageBox.updateProgress(i / (task_s), i + ' 秒');
			}
		};
	};
	var aa = setInterval(f(), 1000);
};
// grid解锁
function unlockGrid() {
	userSm.unlock();
	userGrid.getGridEl().unmask();
	userGrid.getBottomToolbar().enable();
	gatherTcSelectAllcb.setValue(false);
}
// 清除任务执行状态
function clearTaskStatus() {
	if (userStore == null) {
		return true;
	}
	for (var i = 0; i < userStore.getCount(); i++) {
		userStore.getAt(i).set('taskStatus', '');
	}
	for (var i = 0; i < userArrayStore.getCount(); i++) {
		userArrayStore.getAt(i).set('taskStatus', '');
	}
	userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
}

/* 弹出任务信息窗口 */
function openTaskWindow(taskNo) {
	var ssResc = userSm.getSelections();
	if (ssResc == null)
		return true;
	if (ssResc[0].get('sendUpMode') == '1') {
		Ext.getCmp('gt_callButton').show();
		this.taskInfoWindow.show();
	} else if (ssResc[0].get('sendUpMode') == '0') {
		Ext.getCmp('gt_callButton').hide();
		this.taskInfoWindow.show();
	} else {
		return true;
	}
	Ext.Ajax.request({
				url : 'baseapp/gatherTaskCompile!queryTaskInfo.action',
				params : {
					tmnlAssetNo : getSelectValues('tmnlAssetNos'),
					taskNo : taskNo
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
					if (result.taskInfoDto == null
							|| result.taskInfoDto.tmnlAssetNo == null
							|| result.taskInfoDto.tmnlAssetNo == '') {
						Ext.Msg.alert('提示', '任务所属模板已删除');
						return true;
					}
					taskInfoForm.getForm().setValues(result.taskInfoDto);
				}
			});
}
/* 任务属性的码表 */
var propoComStore = new Ext.data.JsonStore({
			url : "sysman/taskModelMan!queryTaskPropertyJson.action",
			fields : ['taskProperty', 'taskPropertyName'],
			root : "tTaskPropertyList"
		});
propoComStore.load();

/* 任务编制store */
var modelComStore = new Ext.data.JsonStore({
			url : "sysman/taskModelMan!queryTaskTemplatesByTaskNo.action",
			fields : ['templateId', 'templateName', 'taskProperty'],
			root : "tTaskTemplateList"
		});
var taskTemp = new Ext.grid.CheckboxSelectionModel({
			singleSelect : true,
			header : ""
		});
var cm = new Ext.grid.ColumnModel([taskTemp, {
			header : '任务编号',
			width : 30,
			dataIndex : 'taskNo',
			align : 'center',
			editor : new Ext.form.NumberField({
						allowBlank : false
					})
		}, {
			header : '测量点',
			width : 60,
			dataIndex : 'mpsn',
			align : 'center',
			renderer : function(value) {
				if (value == null || value == '')
					return '-请选择-';
				else
					return value;
			},
			editor : new Ext.form.TextField({
						allowBlank : false,
						readOnly : true
					})
		}, {
			header : '任务模板',
			dataIndex : 'taskModel',
			align : 'center',
			editor : new Ext.form.ComboBox({
						typeAhead : true,
						triggerAction : 'all',
						store : modelComStore,
						editable : false,
						displayField : 'templateName',
						valueField : 'templateId',
						mode : 'remote',
						emptyText : '--请选择任务名称--',
						blankText : '--请选择任务名称--',
						selectOnFocus : true
					}),
			renderer : function(tempId) {
				if (tempId == null || tempId == '')
					return '-请选择-';
				var mc = modelComStore.query('templateId', tempId);
				if (mc != null && mc.length > 0)
					return "<div align = 'left'>"
							+ mc.get(0).get('templateName') + "</div>";
				else
					return '-请选择-';
			}
		}, {
			header : '任务属性',
			width : 40,
			dataIndex : 'taskPrpo',
			align : 'center',
			renderer : function(taskPr) {
				var no = propoComStore.find('taskProperty', taskPr);
				if (no > -1) {
					return "<div align = 'left'>"
							+ propoComStore.getAt(no).get('taskPropertyName')
							+ "</div>";
				} else {
					return '';
				}
			}
		}, {
			header : '操作',
			width : 50,
			dataIndex : 'viewModel',
			align : 'center',
			renderer : function(s, m, rec) {
				return "<a href='javascript:' onclick='templateWindowShow()'>查看模板</a>";
			}
		}]);

var modelInitData = [['1', '', '', '']];
var modelStore = new Ext.data.Store({
			proxy : new Ext.data.MemoryProxy(modelInitData),
			reader : new Ext.data.ArrayReader({}, [{
								name : 'taskNo'
							}, {
								name : 'mpsn'
							}, {
								name : 'taskModel'
							}, {
								name : 'taskPrpo'
							}])
		});
modelStore.load();
// 上面的可编辑模版grid
var modelGrid = new Ext.grid.EditorGridPanel({
			store : modelStore,
			cm : cm,
			sm : taskTemp,
			height : 90,
			autoExandColumn : 'taskModel',
			stripeRows : true,
			clicksToEdit : 2,
			autoWidth : true,
			autoScroll : true,
			monitorResize : true,
			tbar : [{
						xtype : 'label',
						html : "<font font-weight:bold;>采集任务列表</font>"
					}, {
						xtype : 'tbfill'
					}, {
						text : '增加任务',
						width : 30,
						iconCls : 'plus',
						handler : function() {
							var modelObj = new Object();
							modelObj.taskNo = modelStore.getCount() + 1;
							modelObj.mpsn = '';
							modelObj.taskModel = '';
							modelObj.taskPrpo = '';
							var modelRecord = new Ext.data.Record(modelObj);

							modelGrid.stopEditing();
							modelStore.add(modelRecord);

							var i = modelStore.getCount();
							modelGrid.getSelectionModel().selectRow(i - 1);
						}
					}, '-', {
						text : '删除任务',
						iconCls : 'minus',
						width : 30,
						handler : function() {
							var recs = taskTemp.getSelections();
							modelStore.remove(recs);
						}
					}]
		});

// 监听editGrid编辑后事件
modelGrid.on('afteredit', function(e) {
			if (e.field == 'taskModel') {
				// Ext.Ajax.request({
				// url:'sysman/taskModelMan!queryTaskTemplateJson.action',
				// params:{templateId:e.record.get('taskModel')},
				// success:function(response) {
				// var result = Ext.decode(response.responseText);
				// if (!result.success) {
				// var msg;
				// if (!result.errors.msg)
				// msg = '未知错误！';
				// else
				// msg = result.errors.msg;
				// Ext.MessageBox.alert('后台错误', msg);
				// }
				// if(result.TTaskTemplate!=null)
				// e.record.set('taskPrpo',result.TTaskTemplate.taskProperty);
				// }
				// });
				var templateId = e.record.get('taskModel');
				var no = modelComStore.find('templateId', templateId);
				if (no > -1) {
					var taskPrpo = modelComStore.getAt(no).get('taskProperty');
					e.record.set('taskPrpo', taskPrpo);
				}
			} else if (e.field == 'taskNo') {
				e.record.set('taskModel', '');
				e.record.set('taskPrpo', '');
			}
		});
// 监听editGrid单元格被单击的事件
modelGrid.on('cellclick', function(thisGrid, rowIndex, columnIndex, e) {
	if (thisGrid.getColumnModel().getDataIndex(columnIndex) == 'mpsn') {
		modelGrid.stopEditing();
		cldWindow.show();
	} else if (thisGrid.getColumnModel().getDataIndex(columnIndex) == 'taskModel') {
		thisGrid.getStore().getAt(rowIndex).set('taskModel', '');
		thisGrid.getStore().getAt(rowIndex).set('taskPrpo', '');
		var taskNoValue = thisGrid.getStore().getAt(rowIndex).get('taskNo');
		modelComStore.setBaseParam('taskNo', taskNoValue);
		modelComStore.load();
		setTimeout(function() {
					modelGrid.startEditing(rowIndex, columnIndex);
				}, 200);
	} else if (thisGrid.getColumnModel().getDataIndex(columnIndex) == 'taskNo') {
		modelGrid.startEditing(rowIndex, columnIndex);
	}
});

// 测量点编辑弹出的窗口----------------------------------------------------------------------------------------------------------------------------
var cldCheckBoxs = new Ext.form.CheckboxGroup({
			xtype : 'checkboxgroup',
			autoHeight : true,
			width : 415,
			columns : 10,
			items : [{
						boxLabel : '1',
						name : '1',
						inputValue : '1'
					}, {
						boxLabel : '2',
						name : '2',
						inputValue : '2'
					}, {
						boxLabel : '3',
						name : '3',
						inputValue : '3'
					}, {
						boxLabel : '4',
						name : '4',
						inputValue : '4'
					}, {
						boxLabel : '5',
						name : '5',
						inputValue : '5'
					}, {
						boxLabel : '6',
						name : '6',
						inputValue : '6'
					}, {
						boxLabel : '7',
						name : '7',
						inputValue : '7'
					}, {
						boxLabel : '8',
						name : '8',
						inputValue : '8'
					}, {
						boxLabel : '9',
						name : '9',
						inputValue : '9'
					}, {
						boxLabel : '10',
						name : '10',
						inputValue : '10'
					}]
		});
var cldRadios = new Ext.form.RadioGroup({
			xtype : 'radiogroup',
			height : 20,
			items : [{
						boxLabel : '终端',
						name : 'cldType',
						inputValue : 1,
						listeners : {
							check : function(c, v) {
								var allCheck = Ext.getCmp("gg_allChecked");
								var clearAll = Ext.getCmp("gg_clearAll");
								if (v) {
									allCheck.setDisabled(true);
									clearAll.setDisabled(true);
								} else {
									allCheck.setDisabled(false);
									clearAll.setDisabled(false);
								}

							}
						}
					}, {
						boxLabel : '测量点',
						name : 'cldType',
						checked : true,
						inputValue : 2,
						listeners : {
							check : function(checkbox, checked) {
								if (checked) {
									Ext.getCmp('cldPanel').setDisabled(false);
								} else {
									Ext.getCmp('cldPanel').setDisabled(true);
								}
							}
						}
					}]
		});
var cldWindow = new Ext.Window({
			title : '测量点编辑',
			frame : true,
			width : 450,
			height : 450,
			layout : "form",
			labelWidth : 1,
			autoScroll : true,
			modal : true,
			plain : true, // 设置背景颜色
			resizable : false, // 不可移动
			buttonAlign : "center", // 按钮的位置
			closeAction : "hide", // 将窗体隐藏而并不销毁
			items : [cldRadios, {
						xtype : 'panel',
						id : 'cldPanel',
						title : '测量点',
						height : 355,
						autoScroll : true,
						border : false,
						items : [cldCheckBoxs]
					}],
			buttons : [{
				iconCls : 'plus',
				width : 70,
				handler : function() {
					var tNum = cldCheckBoxs.items.length;
					var items = cldCheckBoxs.items;
					var columns = cldCheckBoxs.panel.items;
					for (var i = tNum + 1; i < tNum + 11; i = i + 1) {
						var column = columns.itemAt(items.getCount()
								% columns.getCount());
						var checkbox = new Ext.form.Checkbox({
									boxLabel : i,
									name : i,
									inputValue : i
								});
						var checkboxItem = column.add(checkbox);
						cldCheckBoxs.items.add(checkboxItem);
					}
					cldCheckBoxs.doLayout();
				}
			}, {
				text : '全选',
				id : "gg_allChecked",
				handler : function() {
					var tNum = cldCheckBoxs.items.length;
					for (var i = 1; i < tNum + 1; i++) {
						cldCheckBoxs.setValue(i, true);
					};
					cldCheckBoxs.doLayout();
				}
			}, {
				text : '清空',
				id : "gg_clearAll",
				handler : function() {
					cldCheckBoxs.reset();
				}
			}, {
				text : '确定',
				handler : function() {
					cldWindow.hide();
					var selectedRecord = modelGrid.getSelectionModel()
							.getSelected();
					if (cldRadios.getValue().getRawValue() == 1) {
						selectedRecord.set('mpsn', '0');
					} else {
						var cbs = cldCheckBoxs.getValue();
						var mpsn = '';
						for (var i = 0; i < cbs.length; i = i + 1) {
							mpsn = mpsn + cbs[i].getRawValue() + ',';
						}
						if (mpsn != '')
							mpsn = mpsn.substring(0, mpsn.length - 1);
						selectedRecord.set('mpsn', mpsn);
					}
					cldCheckBoxs.reset();
				}
			}]
		});
// 启用、停用和删除任务弹出的窗口--------------------------------------------------------------------------
var eventType = ''; // 1--启用 0--停用 2--删除
var ynTaskCheckBoxs = new Ext.form.CheckboxGroup({
			xtype : 'checkboxgroup',
			autoHeight : true,
			width : 415,
			columns : 10,
			items : [{
						boxLabel : '1',
						name : '1',
						inputValue : '1'
					}, {
						boxLabel : '2',
						name : '2',
						inputValue : '2'
					}, {
						boxLabel : '3',
						name : '3',
						inputValue : '3'
					}, {
						boxLabel : '4',
						name : '4',
						inputValue : '4'
					}, {
						boxLabel : '5',
						name : '5',
						inputValue : '5'
					}, {
						boxLabel : '6',
						name : '6',
						inputValue : '6'
					}, {
						boxLabel : '7',
						name : '7',
						inputValue : '7'
					}, {
						boxLabel : '8',
						name : '8',
						inputValue : '8'
					}, {
						boxLabel : '9',
						name : '9',
						inputValue : '9'
					}, {
						boxLabel : '10',
						name : '10',
						inputValue : '10'
					}]
		});
var ynTaskWindow = new Ext.Window({
			title : '任务号选择',
			frame : true,
			width : 450,
			height : 300,
			layout : "form",
			labelWidth : 1,
			autoScroll : true,
			modal : true,
			plain : true,// 设置背景颜色
			resizable : false,// 不可移动
			buttonAlign : "center",// 按钮的位置
			closeAction : "hide",// 将窗体隐藏而并不销毁
			items : [{
						xtype : 'panel',
						height : 225,
						autoScroll : true,
						border : false,
						items : [ynTaskCheckBoxs]
					}],
			buttons : [{
				iconCls : 'plus',
				width : 70,
				handler : function() {
					var tNum = ynTaskCheckBoxs.items.length;
					var items = ynTaskCheckBoxs.items;
					var columns = ynTaskCheckBoxs.panel.items;
					for (var i = tNum + 1; i < tNum + 11; i = i + 1) {
						var column = columns.itemAt(items.getCount()
								% columns.getCount());
						var checkbox = new Ext.form.Checkbox({
									boxLabel : i,
									name : i,
									inputValue : i
								});
						var checkboxItem = column.add(checkbox);
						ynTaskCheckBoxs.items.add(checkboxItem);
					}
					ynTaskCheckBoxs.doLayout();
				}
			}, {
				text : '全选',
				handler : function() {
					var tNum = ynTaskCheckBoxs.items.length;
					for (var i = 1; i < tNum + 1; i++) {
						ynTaskCheckBoxs.setValue(i, true);
					};
					ynTaskCheckBoxs.doLayout();
				}
			}, {
				text : '确定',
				handler : ynDelTask
			}]

		});
// 启用停用删除任务
function ynDelTask() {
	var recs2 = userSm.getSelections();

	var tasks = ynTaskCheckBoxs.getValue();
	var taskNos = '';
	for (var i = 0; i < tasks.length; i = i + 1) {
		taskNos = taskNos + tasks[i].getRawValue() + '#';
	}
	if (taskNos == '') {
		Ext.Msg.alert('提示', '请选择任务号');
		return;
	} else {
		taskNos = taskNos.substring(0, taskNos.length - 1);
	}

	ynTaskWindow.hide();
	ynTaskCheckBoxs.reset();

	clearTaskStatus(); // 清除前次任务执行状态

	var seconds = 20;

	if (eventType == '0' || eventType == '1') {
		var ov = true;
		h_taskTime(seconds, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : 'baseapp/gatherTaskCompile!ynTaskStatus.action',
			params : {
				taskNos : taskNos,
				eventType : eventType,
				consNos : getSelectValues('consNos'),
				terminalAddrs : getSelectValues('terminalAddrs'),
				tmnlAssetNos : getSelectValues('tmnlAssetNos'),
				sendUpModes : getSelectValues('sendUpModes'),
				protocolCodes : getSelectValues('protocolCodes'),
				enableTaskNos : getSelectValues('yTaskNos'),
				unableTaskNos : getSelectValues('nTaskNos'),
				taskSecond : seconds
			},
			success : function(response) {
				if (!ov) {
					return true;
				}
				var result = Ext.decode(response.responseText);
				if (!result) {
					Ext.Msg.alert('提示', '后台无数据返回');
					return true;
				}
				if (result.msg && result.msg != '') {
					Ext.Msg.alert('提示', result.msg);
					return true;
				}
				for (var l = 0; l < result.gatherTaskCompileList.length; l = l
						+ 1) {
					var rec = userStore
							.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
					if (rec != null) {
						rec.set('taskStatus',
								result.gatherTaskCompileList[l].taskStatus);
						rec.set('yTaskNo',
								result.gatherTaskCompileList[l].yTaskNo);
						rec.set('nTaskNo',
								result.gatherTaskCompileList[l].nTaskNo);
						rec.commit();
					}
				}
				userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));

				// 更新下发状态
				if (gatherTcSelectAllcb.checked) {
					unlockGrid();
					userArrayStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var l = 0; l < result.gatherTaskCompileList.length; l++) {
						var arrayRec = userArrayStore
								.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
						arrayRec.set('taskStatus',
								result.gatherTaskCompileList[l].taskStatus);
						arrayRec.set('yTaskNo',
								result.gatherTaskCompileList[l].yTaskNo);
						arrayRec.set('nTaskNo',
								result.gatherTaskCompileList[l].nTaskNo);
						arrayRec.commit();
					}
				}

				if (eventType == '0')
					Ext.MessageBox.alert('提示', '停用结束');
				if (eventType == '1')
					Ext.MessageBox.alert('提示', '启用结束');
				eventType = '';

				overFlat = true;
			}
		});
	} else if (eventType == '2') {
		var ov = true;
		h_taskTime(seconds, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : 'baseapp/gatherTaskCompile!deleteTask.action',
			params : {
				taskNos : taskNos,
				consNos : getSelectValues('consNos'),
				terminalAddrs : getSelectValues('terminalAddrs'),
				tmnlAssetNos : getSelectValues('tmnlAssetNos'),
				sendUpModes : getSelectValues('sendUpModes'),
				protocolCodes : getSelectValues('protocolCodes'),
				enableTaskNos : getSelectValues('yTaskNos'),
				unableTaskNos : getSelectValues('nTaskNos'),
				taskSecond : seconds
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!result) {
					Ext.Msg.alert('提示', '后台无数据返回');
					return true;
				}
				if (result.msg && result.msg != '') {
					Ext.Msg.alert('提示', result.msg);
					return true;
				}

				for (var l = 0; l < result.gatherTaskCompileList.length; l = l
						+ 1) {
					var rec = userStore
							.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
					if (rec != null) {
						rec.set('taskStatus',
								result.gatherTaskCompileList[l].taskStatus);
						rec.set('yTaskNo',
								result.gatherTaskCompileList[l].yTaskNo);
						rec.set('nTaskNo',
								result.gatherTaskCompileList[l].nTaskNo);
						rec.commit();
					}
				}
				userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));

				// 更新下发状态
				if (gatherTcSelectAllcb.checked) {
					unlockGrid();
					userArrayStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var l = 0; l < result.gatherTaskCompileList.length; l++) {
						var arrayRec = userArrayStore
								.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
						arrayRec.set('taskStatus',
								result.gatherTaskCompileList[l].taskStatus);
						arrayRec.set('yTaskNo',
								result.gatherTaskCompileList[l].yTaskNo);
						arrayRec.set('nTaskNo',
								result.gatherTaskCompileList[l].nTaskNo);
						arrayRec.commit();
					}
				}

				overFlat = true;
				Ext.MessageBox.alert('提示', '删除成功');
			}
		});
		eventType = '';
	}
}

// 上方的panel，包括modelGrid和几个按钮--------------------------------------------------------
var topPanel = new Ext.Panel({
	frame : false,
	height : 180,
	region : 'north',
	border : false,
	buttonAlign : 'center',
	layout : 'fit',
	items : [modelGrid],
	buttons : [{
				text : '下发',
				handler : sendOutTask
			}, {
				text : "后台下发",
				handler : function() {
					var r = taskTemp.getSelected();
					if (!r) {
						Ext.Msg.alert('提示', '请选择要下发的任务');
						return true;
					}
					if (r.get('taskNo') == '' || r.get('mpsn') == ''
							|| r.get('taskModel') == '') {
						Ext.Msg.alert('提示', '请完整填写任务信息');
						return true;
					}

					var users = userSm.getSelections();
					if (users.length < 1) {
						Ext.Msg.alert('提示', '请选择要下发的用户');
						return true;
					}
					// 测量点的列表
					var pns = eval("[" + r.get("mpsn") + "]");
					// 生成后头的datas的格式 终端资产好为key protocolCode_sendupmode
					var params = {};
					Ext.each(users, function(d) {
								var key = "datas." +"Z"+ d.get("tmnlAssetNo");
								var value = d.get("protocolCode") + "_"
										+ d.get("sendUpMode");
								params[key] = value;
							});
					params["taskNo"] = r.get("taskNo");
					params["pns"] = pns;
					params["templateId"] = r.get("taskModel");
					Ext.getBody().mask("后台下发执行中...");
					Ext.Ajax.request({
								url : 'baseapp/gatherTaskCompile!bgSendTask.action',
								params : params,
								callback:function(){
								Ext.getBody().unmask();
								},
								success : function(response) {
									var o = Ext.decode(response.responseText);
									if (o && o.msg && o.msg.trim() != "") {
										return !!Ext.Msg.alert("错误", o.msg);
									}
									Ext.Msg.alert("提示","保存成功");
								}
							});
				}
			}, {
				text : '启用',
				handler : function() {
					var recs2 = userSm.getSelections();
					if (recs2.length < 1) {
						Ext.Msg.alert('提示', '请选择要启用任务的用户');
						return;
					}
					eventType = '1';
					ynTaskWindow.show();
				}
			}, {
				text : '停用',
				handler : function() {
					var recs2 = userSm.getSelections();
					if (recs2.length < 1) {
						Ext.Msg.alert('提示', '请选择要停用任务的用户');
						return;
					}
					eventType = '0';
					ynTaskWindow.show();
				}
			}, {
				text : '删除',
				handler : function() {
					var recs2 = userSm.getSelections();
					if (recs2.length < 1) {
						Ext.Msg.alert('提示', '请选择要删除任务的用户');
						return;
					}
					eventType = '2';
					ynTaskWindow.show();
				}
			}]
});
// 下发任务
function sendOutTask() {
	var recs1 = taskTemp.getSelections();
	if (recs1.length < 1) {
		Ext.Msg.alert('提示', '请选择要下发的任务');
		return true;
	}
	if (recs1[0].get('taskNo') == '' || recs1[0].get('mpsn') == ''
			|| recs1[0].get('taskModel') == '') {
		Ext.Msg.alert('提示', '请完整填写任务信息');
		return true;
	}

	var recs2 = userSm.getSelections();
	if (recs2.length < 1) {
		Ext.Msg.alert('提示', '请选择要下发的用户');
		return true;
	}

	clearTaskStatus(); // 清除前次任务执行状态

	var userGridBbar = userGrid.getBottomToolbar();
	var pageSize = userGridBbar.pageSize;
	var activePage = userGridBbar.getPageData().activePage;
	var start = pageSize * (activePage - 1);

	var seconds = 20;
	var ov = true;
	h_taskTime(seconds, function() {
				ov = false;
			});
	Ext.Ajax.request({
		url : 'baseapp/gatherTaskCompile!sendOut.action',
		params : {
			taskNo : recs1[0].get('taskNo'),
			mpsn : recs1[0].get('mpsn'),
			taskModel : recs1[0].get('taskModel'),
			consNos : getSelectValues('consNos'),
			terminalAddrs : getSelectValues('terminalAddrs'),
			tmnlAssetNos : getSelectValues('tmnlAssetNos'),
			sendUpModes : getSelectValues('sendUpModes'),
			protocolCodes : getSelectValues('protocolCodes'),
			enableTaskNos : getSelectValues('yTaskNos'),
			unableTaskNos : getSelectValues('nTaskNos'),
			taskSecond : seconds,
			limit : pageSize,
			start : start
		},
		success : function(response) {
			if (!ov) {
				return true;
			}
			var result = Ext.decode(response.responseText);
			if (!result) {
				Ext.Msg.alert('提示', '后台无数据返回');
				return true;
			}

			if (result.msg && result.msg != '') {
				Ext.Msg.alert('提示', result.msg);
				return true;
			}

			for (var l = 0; l < result.gatherTaskCompileList.length; l = l + 1) {
				var rec = userStore
						.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
				if (rec != null) {
					rec.set('taskStatus',
							result.gatherTaskCompileList[l].taskStatus);
					rec.set('yTaskNo', result.gatherTaskCompileList[l].yTaskNo);
					rec.set('nTaskNo', result.gatherTaskCompileList[l].nTaskNo);
					rec.commit();
				}
			}
			userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));

			// 更新下发状态
			if (gatherTcSelectAllcb.checked) {
				unlockGrid();
				userArrayStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
			} else {
				for (var l = 0; l < result.gatherTaskCompileList.length; l++) {
					var arrayRec = userArrayStore
							.getById(result.gatherTaskCompileList[l].tmnlAssetNo);
					arrayRec.set('taskStatus',
							result.gatherTaskCompileList[l].taskStatus);
					arrayRec.set('yTaskNo',
							result.gatherTaskCompileList[l].yTaskNo);
					arrayRec.set('nTaskNo',
							result.gatherTaskCompileList[l].nTaskNo);
					arrayRec.commit();
				}
			}

			overFlat = true;
			Ext.MessageBox.alert('提示', '下发结束');
		}
	});
}
// 用户列表----------------------------------------------------------------------------------------
var userSm = new Ext.grid.CheckboxSelectionModel();
// var size = userGrid.getBottomToolbar().pageSize;

var rowNum = new Ext.grid.RowNumberer({
			renderer : function(v, p, record, rowIndex) {
				var startRow = 0;
				if (userArrayStore && userArrayStore.lastOptions
						&& userArrayStore.lastOptions.params) {
					startRow = userArrayStore.lastOptions.params.start;
				}
				return startRow + rowIndex + 1;
			}
		});
var userCm = new Ext.grid.ColumnModel([rowNum, userSm, {
	header : '供电单位',
	dataIndex : 'orgName',
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
				+ '">' + val + '</div>';
		return html;
	}
}, {
	header : '用户编号',
	dataIndex : 'consNo',
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "left" ext:qtitle="用户编号" ext:qtip="' + val
				+ '">' + val + '</div>';
		return html;
	}
}, {
	header : '用户名称',
	dataIndex : 'consName',
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val
				+ '">' + val + '</div>';
		return html;
	}
}, {
	header : '终端地址',
	dataIndex : 'terminalAddr',
	align : 'center'
}, {
	header : '规约编码',
	dataIndex : 'protocolCode',
	align : 'center',
	hidden : true
}, {
	header : '终端资产编号',
	hidden : true,
	id : 'tmnlAssetNo',
	dataIndex : 'tmnlAssetNo',
	align : 'center'
}, {
	header : '终端厂商',
	dataIndex : 'factoryName',
	align : 'center',
	renderer : function(val) {
		var html = '<div align = "left" ext:qtitle="终端厂商" ext:qtip="' + val
				+ '">' + val + '</div>';
		return html;
	}
}, {
	header : '启用任务',
	dataIndex : 'yTaskNo',
	align : 'center',
	renderer : function(value) {
		if (value == null || value == '')
			return "";
		var s = value.split(",");
		var href = '';
		for (var i = 0; i < s.length; i = i + 1) {
			if (s[i] != 0) {
				href = href + "<a href='javascript:' onclick='"
						+ "openTaskWindow(" + s[i] + ");"
						+ "'><font color='green';font-weight:bold>[" + s[i]
						+ "]</font></a>" + "&nbsp;";
			}
		}
		return href;
	}
}, {
	header : '停用任务',
	dataIndex : 'nTaskNo',
	align : 'center',
	renderer : function(value) {
		if (value == null || value == '')
			return "";
		var s = value.split(",");
		var href = '';
		for (var i = 0; i < s.length; i = i + 1) {
			if (s[i] != 0) {
				href = href + "<a href='javascript:' onclick='"
						+ "openTaskWindow(" + s[i] + ");"
						+ "'><font color='red';font-weight:bold>[" + s[i]
						+ "]</font></a>" + "&nbsp;";
			}
		}
		return href;
	}
}, {
	header : '数据采集方式',
	dataIndex : 'sendUpMode',
	align : 'center',
	renderer : function(value) {
		if (value == 0)
			return '主站主动召测';
		else if (value == 1)
			return '终端自动上送';
		else
			return '';
	}
}, {
	header : '状态',
	dataIndex : 'taskStatus',
	align : 'center',
	renderer : function(value) {
		if (value == '成功') {
			return "<font color='green';font-weight:bold>" + value + "</font>";
		} else if (value != null && value != '') {
			return "<font color='red';font-weight:bold>" + value + "</font>";
		} else
			return "";
	}
}, {
	header : '报文',
	dataIndex : 'origQry',
	align : 'center',
	renderer : function(s, m, rec) {
		return "<a href='javascript:' onclick='origFrameQuery(\""
				+ rec.get('consNo') + "\",\"" + rec.get('consName') + "\",\""
				+ rec.get('terminalAddr') + "\");" + "'>查看</a>";
	}
}]);

var data = [];
var userStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : "baseapp/gatherTaskCompile!generalGridByTree.action",
						method : "POST"
					}),
			reader : new Ext.data.JsonReader({
						root : 'gatherTaskCompileList',
						idProperty : "tmnlAssetNo",
						totalProperty : "totalCount"
					}, [{
								name : 'orgName'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'protocolCode'
							}, {
								name : 'terminalAddr'
							}, {
								name : 'tmnlAssetNo'
							}, {
								name : 'factoryName'
							}, {
								name : 'yTaskNo'
							}, {
								name : 'nTaskNo'
							}, {
								name : 'sendUpMode'
							}, {
								name : 'taskStatus'
							}])
		});

var userArrayStore = new Ext.data.Store({
			remoteSort : true,
			reader : new Ext.data.ArrayReader({
						idIndex : 5,
						fields : [{
									name : 'orgName'
								}, {
									name : 'consNo'
								}, {
									name : 'consName'
								}, {
									name : 'protocolCode'
								}, {
									name : 'terminalAddr'
								}, {
									name : 'tmnlAssetNo'
								}, {
									name : 'factoryName'
								}, {
									name : 'yTaskNo'
								}, {
									name : 'nTaskNo'
								}, {
									name : 'sendUpMode'
								}, {
									name : 'taskStatus'
								}]
					})
		});
function storeToArray(valStore) {
	var data = new Array();
	for (var i = 0; i < valStore.getCount(); i++) {
		data[i] = new Array();
		// 供电单位
		data[i][0] = valStore.getAt(i).data.orgName;
		// 用户编号
		data[i][1] = valStore.getAt(i).data.consNo;
		// 用户名称
		data[i][2] = valStore.getAt(i).data.consName;
		// 规约编码
		data[i][3] = valStore.getAt(i).data.protocolCode;
		// 终端地址
		data[i][4] = valStore.getAt(i).data.terminalAddr;
		// 终端资产编号
		data[i][5] = valStore.getAt(i).data.tmnlAssetNo;
		// 生产厂商
		data[i][6] = valStore.getAt(i).data.factoryName
		// 启用任务号
		data[i][7] = valStore.getAt(i).data.yTaskNo
		// 禁用任务号
		data[i][8] = valStore.getAt(i).data.nTaskNo
		// 上送方式
		data[i][9] = valStore.getAt(i).data.sendUpMode
		// 任务状态
		data[i][10] = valStore.getAt(i).data.taskStatus
	}
	return data;
}
userStore.on('load', function(thisstore, recs, obj) {
	userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(thisstore));
	userArrayStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
	userSm.selectAll();
	userGrid.doLayout();
});
// toolbar上的checkbox
var gatherTcSelectAllcb = new Ext.form.Checkbox({
			boxLabel : '全选',
			name : 'tpselectAllcb',
			checked : false,
			listeners : {
				'check' : function(r, c) {
					if (c) {
						userSm.selectAll();
						// userGrid.onDisable();
						userGrid.getGridEl().mask();
						userGrid.getBottomToolbar().disable();
					} else {
						userSm.clearSelections();
						userGrid.getGridEl().unmask();
						userGrid.getBottomToolbar().enable();
					}
				}
			}
		});

var userGrid = new Ext.grid.GridPanel({
	store : userArrayStore,
	loadMask : true,
	cm : userCm,
	sm : userSm,
	stripeRows : true,
	monitorResize : true,
	autoWidth : true,
	border : false,
	autoScroll : true,
	tbar : [{
		xtype : 'label',
//		html : "<font font-weight:bold;>备选用户</font><font color='red'>【请选左边树上的线路、供电所、群组或用户节点】</font>"
		html : "<font font-weight:bold;>备选用户</font>"
	}, {
		xtype : 'tbfill'
	}, gatherTcSelectAllcb, '-', {
		text : '删除选中用户',
		iconCls : 'cancel',
		handler : function() {
			if (gatherTcSelectAllcb.checked) {
				userStore.removeAll();
				unlockGrid();
			} else {
				var recs = userSm.getSelections();
				for (var i = 0; i < recs.length; i = i + 1) {
					userStore.remove(userStore
							.getById(recs[i].data.tmnlAssetNo));
				}
			}
			userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			userArrayStore.load({
						params : {
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						}
					});
		}
	}, '-', {
		text : '加入群组',
		iconCls : 'plus',
		handler : function() {
			var groupTmnlArray = new Array();
			if (gatherTcSelectAllcb.checked) {
				for (var i = 0; i < userStore.getCount(); i++) {
					var tmnl = userStore.getAt(i).get('consNo') + '`'
							+ userStore.getAt(i).get('tmnlAssetNo');
					groupTmnlArray[i] = tmnl;
				}
			} else {
				var recs = userSm.getSelections();
				for (var i = 0; i < recs.length; i++) {
					var tmnl = recs[i].get('consNo') + '`'
							+ recs[i].get('tmnlAssetNo');
					groupTmnlArray[i] = tmnl;
				}
			}
			if (groupTmnlArray.length == 0) {
				Ext.Msg.alert('提示', '请选择要加入群组的用户');
			} else {
				saveOrUpdateGroupWindowShow(groupTmnlArray);
				if (gatherTcSelectAllcb.checked) {
					unlockGrid();
				}
			}
		}
	}, '-', {
		text : '删除成功用户',
		iconCls : 'minus',
		handler : function() {
			if (userStore != null && userStore.getCount() > 0) {
				var records = new Array();
				for (var i = 0; i < userStore.getCount(); i = i + 1) {
					if (userStore.getAt(i).get('taskStatus') == '成功')
						records.push(userStore.getAt(i));
				}
			}
			userStore.remove(records);
			userArrayStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(userStore));
			userArrayStore.load({
						params : {
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						}
					});
			userGrid.doLayout();
		}
	}],
	viewConfig : {
		forceFit : false
	},
	bbar : new Ext.ux.MyToolbar({
				store : userArrayStore,
				enableExpAll : true,
				allStore : userStore
			})
});

// Ext.apply(userGrid, {
// onDisable : function() {
// if (this.rendered && this.maskDisabled) {
// this.body.mask();
// }
// Ext.Panel.superclass.onDisable.call(this);
// },
// onEnable : function(){
// if(this.rendered && this.maskDisabled){
// this.body.unmask();
// }
// Ext.Panel.superclass.onEnable.call(this);
// }
// });

// 下方的panel页-----------------------------------------------------------------------------------------
var userTab = new Ext.Panel({
			region : 'center',
			monitorResize : true,
			layout : 'fit',
			items : [userGrid],
			border : false
		});

// 监听左边树点击事件
var treeListener = new LeftTreeListener({
			modelName : '采集任务编制',
			processClick : function(p, node, e) {
				var obj = node.attributes.attributes;
				var type = node.attributes.type;
				if (type == 'org') {
					if (obj.orgType == '02') {
						Ext.Msg.alert('提示', '请选择省公司以下节点');
						return true;
					}
					userStore.baseParams = {
						nodeType : type,
						orgNo : obj.orgNo
					};
					userStore.load({
								callback : function(recs, options, success) {
									if (success)
										userGrid.getSelectionModel()
												.selectRecords(recs, true);
								},
								add : true
							});
				} else if (type == 'usr') {
					var tmnlAssetNoArr = new Array();
					tmnlAssetNoArr[0] = obj.tmnlAssetNo;
					userStore.baseParams = {
						consNo : obj.consNo,
						nodeType : type,
						tmnlAssetNoArr : tmnlAssetNoArr
					};
					userStore.load({
								callback : function(recs, options, success) {
									if (success)
										userGrid.getSelectionModel()
												.selectRecords(recs, true);
								},
								add : true
							});
				} else if (type == 'line') {
					userStore.baseParams = {
						lineId : obj.lineId,
						nodeType : type,
						orgNo : obj.orgNo
					};
					userStore.load({
								callback : function(recs, options, success) {
									if (success)
										userGrid.getSelectionModel()
												.selectRecords(recs, true);
								},
								add : true
							});
				} else if (type == 'cgp' || type == 'ugp') {
					userStore.baseParams = {
						groupNo : obj.groupNo,
						nodeType : type
					};
					userStore.load({
								callback : function(recs, options, success) {
									if (success)
										userGrid.getSelectionModel()
												.selectRecords(recs, true);
								},
								add : true
							});
				} else {
					// alert(type);
					Ext.Msg.alert('提示', '请选择省公司以下节点');
				}
			},
			processUserGridSelect : function(cm, row, record) {
				var tmnlAssetNoArr = new Array();
				tmnlAssetNoArr[0] = record.get('tmnlAssetNo');
				userStore.baseParams = {
					consNo : record.get('consNo'),
					nodeType : 'usr',
					tmnlAssetNoArr : tmnlAssetNoArr
				};
				userStore.load({
							callback : function(recs, options, success) {
								if (success)
									userGrid.getSelectionModel().selectRecords(
											recs, true);
							},
							add : true
						});
			},
			processUserGridAllSelect : function(r) {
				var store = r.getStore();
				var tmnlAssetNoArr = new Array();
				var n = 0;
				for (var i=0; i < store.getCount(); i++) {
					var rec = store.getAt(i);
					tmnlAssetNoArr[n++] = rec.data.tmnlAssetNo;
				};
				userStore.baseParams = {
						nodeType : 'usr',
						tmnlAssetNoArr : tmnlAssetNoArr
					};
				userStore.load({
							callback : function(recs, options, success) {
								if (success)
									userGrid.getSelectionModel().selectRecords(
											recs, true);
							},
							add : true
						});
			}
		});
// 取下面的备选用户grid被选中行的相关信息-------------------------------------------------------
function getSelectValues(value) {
	var consNos = '';
	var terminalAddrs = '';
	var tmnlAssetNos = '';
	var protocolCodes = '';
	var sendUpModes = '';
	var yTaskNos = '';
	var nTaskNos = '';
	var valueRecs;
	if (gatherTcSelectAllcb.checked) {
		valueRecs = userStore.getRange(0, userStore.getCount());
	} else {
		valueRecs = userSm.getSelections();
	}
	for (var i = 0; i < valueRecs.length; i = i + 1) {
		consNos += valueRecs[i].get('consNo') + "#";
		terminalAddrs += valueRecs[i].get('terminalAddr') + "#";
		tmnlAssetNos += valueRecs[i].get('tmnlAssetNo') + "#";
		sendUpModes += valueRecs[i].get('sendUpMode') + "#";
		yTaskNos += (valueRecs[i].get('yTaskNo') == '' ? '0' : valueRecs[i]
				.get('yTaskNo'))
				+ "#";
		nTaskNos += (valueRecs[i].get('nTaskNo') == '' ? '0' : valueRecs[i]
				.get('nTaskNo'))
				+ "#";
		protocolCodes += valueRecs[i].get('protocolCode') + "#";
	}
	if ('consNos' == value)
		return consNos == '' ? '' : consNos.substring(0, consNos.length - 1);
	if ('terminalAddrs' == value)
		return terminalAddrs == '' ? '' : terminalAddrs.substring(0,
				terminalAddrs.length - 1);
	if ('tmnlAssetNos' == value)
		return tmnlAssetNos == '' ? '' : tmnlAssetNos.substring(0,
				tmnlAssetNos.length - 1);
	if ('protocolCodes' == value)
		return protocolCodes == '' ? '' : protocolCodes.substring(0,
				protocolCodes.length - 1);
	if ('sendUpModes' == value)
		return sendUpModes == '' ? '' : sendUpModes.substring(0,
				sendUpModes.length - 1);
	if ('yTaskNos' == value)
		return yTaskNos == '' ? '' : yTaskNos.substring(0, yTaskNos.length - 1);
	if ('nTaskNos' == value)
		return nTaskNos == '' ? '' : nTaskNos.substring(0, nTaskNos.length - 1);
}

/* 显示任务具体信息窗口----------------------------------------------------------------------------------------------------- */
var tmnlNoTf = new Ext.form.TextField({
			fieldLabel : '终端资产编号',
			labelSeparator : '',
			readOnly : true,
			name : 'tmnlAssetNo',
			anchor : '100%'
		});
var templateNameTf = new Ext.form.TextField({
			fieldLabel : '模板名称',
			readOnly : true,
			labelSeparator : '',
			name : 'templateName',
			anchor : '100%'
		});
var taskNoTf = new Ext.form.TextField({
			fieldLabel : '任务号',
			readOnly : true,
			labelSeparator : '',
			name : 'taskNo',
			anchor : '100%'
		});
var dataTypeTf = new Ext.form.TextField({
			fieldLabel : '数据类型',
			readOnly : true,
			labelSeparator : '',
			name : 'dataType',
			anchor : '100%'
		});
var timeData = [['0', '分'], ['1', '时'], ['2', '日'], ['3', '月']];
var timeComStore = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : timeData
		});
var timeCombox = new Ext.form.ComboBox({
			store : timeComStore,
			displayField : 'text',
			valueField : 'value',
			name : 'reportCycleUnit',
			mode : 'local',
			fieldLabel : '',
			forceSelection : true,
			triggerAction : 'all',
			value : '0',
			selectOnFocus : true,
			readOnly : true,
			anchor : '100%'
		});
var taskInfoForm = new Ext.form.FormPanel({
			title : '任务明细信息',
			region : 'center',
			frame : false,
			layout : 'table',
			labelAlign : 'right',
			monitorResize : true,
			border : false,
			bodyStyle : 'padding:5px',
			layoutConfig : {
				columns : 2
			},
			defaults : {
				width : 240,
				height : 35
			},
			items : [{
						border : false,
						layout : 'form',
						items : [tmnlNoTf]
					}, {
						border : false,
						layout : 'form',
						items : [templateNameTf]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '任务属性',
									readOnly : true,
									labelSeparator : '',
									name : 'taskPropertyName',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [taskNoTf]
					}, {
						border : false,
						layout : 'form',
						items : [dataTypeTf]
					}, {
						border : false,
						layout : 'table',
						layoutConfig : {
							columns : 2
						},
						items : [{
									border : false,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '采集周期',
												readOnly : true,
												labelSeparator : '',
												name : 'reportCycle',
												width : 90,
												anchor : '100%'
											}]
								}, {
									border : false,
									layout : 'form',
									labelWidth : 1,
									width : 60,
									items : [timeCombox]
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '测量点号',
									readOnly : true,
									labelSeparator : '',
									name : 'mpSn',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '采集基准时间',
									readOnly : true,
									labelSeparator : '',
									name : 'referenceTime',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '采集数据点数',
									readOnly : true,
									labelSeparator : '',
									name : 'masterR',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '数据抽取倍率',
									readOnly : true,
									labelSeparator : '',
									name : 'tmnlR',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						width : 450,
						height : 100,
						colspan : 2,
						items : [{
									xtype : 'textarea',
									width : 300,
									height : 100,
									fieldLabel : '任务数据项',
									readOnly : true,
									labelSeparator : '',
									name : 'taskItems',
									anchor : '100%'
								}]
					}]
		});
var timeCombox1 = new Ext.form.ComboBox({
			store : timeComStore,
			displayField : 'text',
			valueField : 'value',
			name : 'callReportCycleUnit',
			mode : 'local',
			fieldLabel : '上报周期单位',
			labelSeparator : '',
			forceSelection : true,
			triggerAction : 'all',
			value : '',
			selectOnFocus : true,
			readOnly : true,
			anchor : '100%'
		});
// 召测数据显示
var callInfoForm = new Ext.form.FormPanel({
			title : '召测结果',
			region : 'south',
			// collapsible:true,
			height : 145,
			frame : false,
			layout : 'table',
			hidden : true,
			labelAlign : 'right',
			monitorResize : true,
			border : false,
			bodyStyle : 'padding:5px',
			layoutConfig : {
				columns : 2
			},
			defaults : {
				width : 240,
				height : 35
			},
			items : [{
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '上报周期',
									readOnly : true,
									labelSeparator : '',
									name : 'callReportCycle',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						width : 260,
						items : [timeCombox1]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '上报基准时间',
									readOnly : true,
									labelSeparator : '',
									name : 'callReferenceTime',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '数据抽取倍率',
									readOnly : true,
									labelSeparator : '',
									name : 'callMasterR',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '测量点',
									readOnly : true,
									labelSeparator : '',
									name : 'callMpSn',
									anchor : '100%'
								}]
					}, {
						border : false,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '任务数据项',
									readOnly : true,
									labelSeparator : '',
									name : 'callClearItems',
									anchor : '100%'
								}]
					}]
		});

var taskInfoWindow = new Ext.Window({
	title : '',
	frame : true,
	width : 525,
	height : 380,
	layout : "border",
	autoScroll : true,
	modal : true,
	plain : true,// 设置背景颜色
	resizable : false,// 不可移动
	buttonAlign : "center",// 按钮的位置
	closeAction : "hide",// 将窗体隐藏而并不销毁
	items : [taskInfoForm, callInfoForm],
	buttons : [{
		text : '召测',
		id : 'gt_callButton',
		handler : function() {
			callInfoForm.setVisible(true);
			taskInfoWindow.setHeight(525);
			taskInfoWindow.doLayout();

			var seconds = 20;
			var ov = true;
			h_taskTime(seconds, function() {
						ov = false;
					});
			var userCallRecs = userSm.getSelections();
			Ext.Ajax.request({
						url : 'baseapp/gatherTaskCompile!callTaskPara.action',
						params : {
							taskNo : taskNoTf.getValue(),
							tmnlAssetNo : userCallRecs[0].get('tmnlAssetNo'),
							protocolCodes : userCallRecs[0].get('protocolCode'),
							taskSecond : seconds,
							dataType : dataTypeTf.getValue()
						},
						success : function(response) {
							if (!ov) {
								return;
							}
							var result = Ext.decode(response.responseText);
							if (!result) {
								Ext.Msg.alert('提示', '后台无数据返回');
								return true;
							}
							if (result.msg && result.msg != '') {
								Ext.Msg.alert('提示', result.msg);
								return true;
							}
							callInfoForm.getForm().reset();
							callInfoForm.getForm()
									.setValues(result.taskInfoDto);
							Ext.MessageBox.alert('提示', '召测结束');

							overFlat = true;
						}
					});
		}
	}, {
		text : '关闭',
		handler : function() {
			taskInfoWindow.hide();
			callInfoForm.setVisible(false);
			taskInfoWindow.setHeight(380);
			taskInfoForm.getForm().reset();
			callInfoForm.getForm().reset();
		}
	}]
});
/* 查看任务模板-------------------------------------------------------------------------------------------------------------------------------------------------------------------- */
var gtctaskIdText1 = new Ext.form.TextField({
			name : 'templateId',
			hidden : true,
			anchor : '100%'
		});
var gtcTaskNameText = new Ext.form.TextField({
			name : 'templateName',
			readOnly : true,
			fieldLabel : '任务名称',
			anchor : '100%'
		});
/* 数据类型 */
var gtcCurrDataRadio = new Ext.form.Radio({
			name : "dataType",
			inputValue : "1",
			boxLabel : "实时数据",
			checked : true
		});
var gtcHisDataRadio = new Ext.form.Radio({
			name : "dataType",
			inputValue : "2",
			boxLabel : "历史数据"
		});
var gtcdataTypeRg = new Ext.form.RadioGroup({
			anchor : '90%',
			fieldLabel : '',
			labelWidth : 1,
			border : false,
			items : [gtcCurrDataRadio, gtcHisDataRadio]
		});
/* 时间单位 */
var gtctimeData = [['0', '分'], ['2', '日'], ['3', '月']];
var gtctimeComStore = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : gtctimeData
		});
var gtctimeCombox = new Ext.form.ComboBox({
			store : gtctimeComStore,
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
var gtcreportCycleData = [['5', '5'], ['15', '15'], ['30', '30'], ['60', '60']];
var gtcreportCycleStore = new Ext.data.SimpleStore({
			fields : ['value', 'text'],
			data : gtcreportCycleData
		});
var gtcreportCycleCombox = new Ext.form.ComboBox({
			store : gtcreportCycleStore,
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
var gtcpropoComStore = new Ext.data.JsonStore({
			url : "sysman/taskModelMan!queryTaskPropertyJson.action",
			fields : ['taskProperty', 'taskPropertyName'],
			root : "tTaskPropertyList"
		});

var gtcpropoCombox = new Ext.form.ComboBox({
			store : gtcpropoComStore,
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
var gtctaskNoNumField = new Ext.form.NumberField({
			fieldLabel : '任务号',
			name : 'taskNo',
			labelSeparator : '',
			readOnly : true,
			anchor : '100%'
		});
var gtcreferenceTimeField = new Ext.form.TextField({
			fieldLabel : '采集开始基准时间',
			name : 'referenceTime',
			readOnly : true,
			anchor : '100%'
		});
var gtcrandomValueNumField = new Ext.form.NumberField({
			fieldLabel : '采集随机最大数',
			name : 'randomValue',
			readOnly : true,
			anchor : '100%'
		});
var gtcrandomValueUnitField = new Ext.form.TextField({
			readOnly : true,
			value : '秒',
			name : 'randomValueUnit',
			anchor : '100%'
		});
var gtcmasterRNumField = new Ext.form.NumberField({
			fieldLabel : '单次采集数据点数',
			readOnly : true,
			name : 'masterR',
			anchor : '100%'
		});
var gtcrecallPolicyField = new Ext.form.TextField({
			fieldLabel : '补召策略',
			readOnly : true,
			name : 'recallPolicy',
			anchor : '100%'
		});
/* 左边的表单 */
var gtcleftForm = new Ext.FormPanel({
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
						items : [gtctaskIdText1]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcTaskNameText]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						width : 317,
						items : [gtcpropoCombox]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtctaskNoNumField]
					}, {
						border : false,
						layout : 'form',
						items : [gtcreferenceTimeField]
					}, {
						border : false,
						layout : 'form',
						colspan : 1,
						width : 253,
						items : [gtcrandomValueNumField]
					}, {
						border : false,
						layout : 'form',
						colspan : 1,
						width : 35,
						labelWidth : 1,
						items : [gtcrandomValueUnitField]
					}, {
						border : false,
						layout : 'form',
						width : 270,
						colspan : 1,
						items : [gtcreportCycleCombox]
					}, {
						border : false,
						layout : 'form',
						width : 52,
						colspan : 1,
						labelWidth : 1,
						items : [gtctimeCombox]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcmasterRNumField]
					}, {
						border : false,
						layout : 'form',
						colspan : 2,
						items : [gtcrecallPolicyField]
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
var gtcitemSelectStore = new Ext.data.JsonStore({
			proxy : new Ext.data.MemoryProxy(),
			fields : ['protocolNo', 'protocolName', 'dataType'],
			root : "BClearProtocolList"
		});
var gtcselectPanel = new Ext.Panel({
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
						store : gtcitemSelectStore,
						ddReorder : false
					}]
		});
var gtcrightForm = new Ext.Panel({
			bodyStyle : 'padding:5px 5px 8px 10px',
			border : false,
			lableWidth : 1,
			layout : 'form',
			items : [{
						xtype : 'panel',
						border : false,
						layout : 'fit',
						items : [gtcdataTypeRg]
					}, gtcselectPanel]
		});

var taskTemplateInfoWin = new Ext.Window({
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
						items : [gtcleftForm]
					}, {
						columnWidth : .45,
						border : false,
						items : [gtcrightForm]
					}]
		});
		
		
/* 弹出任务模板信息窗口 */
function templateWindowShow() {
	var record = modelGrid.getSelectionModel().getSelected();
	var temp = '';
	if (record != null)
		temp = record.get('taskModel');
	if (temp == null || temp == '') {
		Ext.Msg.alert('提示', '未选择任务模板');
		return;
	}
	// openTab("任务模板管理", "./sysMan/templateMan/taskModel.jsp");
	gtcpropoComStore.load();
	gtcleftForm.getForm().reset();
	gtcitemSelectStore.removeAll();
	Ext.Ajax.request({
				url : 'sysman/taskModelMan!queryTaskTemplateJson.action',
				params : {
					templateId : temp
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
						gtcleftForm.getForm().setValues(result.TTaskTemplate);
						gtcrandomValueUnitField.setValue('秒');
						var value = result.TTaskTemplate.dataType;
						if (value == '1') {
							gtcCurrDataRadio.setValue(true);
							gtcHisDataRadio.setValue(false);

							gtcCurrDataRadio.setDisabled(false);
							gtcHisDataRadio.setDisabled(true);
						} else if (value == '2') {
							gtcHisDataRadio.setValue(true);
							gtcCurrDataRadio.setValue(false);

							gtcHisDataRadio.setDisabled(false);
							gtcCurrDataRadio.setDisabled(true);
						}
						gtcitemSelectStore.loadData(result);
					}
				}
			});
	taskTemplateInfoWin.show();
}

/* 查询报文 */
function origFrameQuery(consNo, consName, tmnlAssetAddr) {
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp", false,
			"origFrameQry");
}

Ext.onReady(function() {
			Ext.QuickTips.init();
			var viewPanel = new Ext.form.FormPanel({
						frame : false,
						layout : 'border',
						items : [topPanel, userTab],
						monitorResize : true,
						border : false
					});
			renderModel(viewPanel, '采集任务编制');
			
			//added by 姜炜超 for 终端安装调用------------start
			Ext.getCmp('采集任务编制').on('activate', function() {
				if((typeof(tid_GatherTaskCompile_Flag) != 'undefined') && tid_GatherTaskCompile_Flag){
					tid_GatherTaskCompile_Flag = false;
					userStore.baseParams = {
						consNo : tid_GatherTaskCompile_consNo,
						nodeType : 'usr',
						tmnlAssetNo : tid_GatherTaskCompile_TmnlAssetNo
					};
					userStore.load({
						callback : function(recs, options, success) {
							if (success)
								userGrid.getSelectionModel()
										.selectRecords(recs, true);
							},
							add : false
						});
				}
			});
			//added by 姜炜超 for 终端安装调用------------end
			
			//added by 姜炜超 for 终端安装调用------------start
			if((typeof(tid_GatherTaskCompile_Flag) != 'undefined') && tid_GatherTaskCompile_Flag){
				tid_GatherTaskCompile_Flag = false;
				userStore.baseParams = {
					consNo : tid_GatherTaskCompile_consNo,
					nodeType : 'usr',
					tmnlAssetNo : tid_GatherTaskCompile_TmnlAssetNo
				};
				userStore.load({
					callback : function(recs, options, success) {
						if (success)
							userGrid.getSelectionModel()
									.selectRecords(recs, true);
						},
						add : false
					});
			}
			//added by 姜炜超 for 终端安装调用------------end
		});
