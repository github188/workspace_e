/**
 * @author陈国章
 * @description 自定义报表
 * @version 1.00
 */
Ext.onReady(function() {
	var reportParamsPanelSecond = new Ext.Panel({});
	var reportParamStore = {};
	var reportParamsPanel = {};
	var reportPanel = {};
	var nextProcess;
	var reportTypeGrid = {};
	var reportManCard = {};
	var reportId;
	var getParamsUrl = function() {
	};
	var dataSelect = true;
	var timeSelect = true;
	var ParamsUrl = '';
	var reportStore;
	var loadReportName = function() {
		reportStore.baseParams = {
			reportType : reportTypeCombobox.getRawValue()
		};
		reportStore.load();
	};
	var sm,repId,repType="",repName="",repDes="",defBoolean;
	var reportComboStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : './qrystat/reportTypeQueryAction!queryReportTypeList.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'reportList',
					fields : ['reportType', 'reportType']
				})
	});
	// 报表类型下拉框
	var reportTypeCombobox = new Ext.form.ComboBox({
				id : 'repMaintainMDPReportType',
				triggerAction : 'all',
				bodyStyle : 'padding: 10px 10px 10px 10px',
				fieldLabel : '选择报表类型',
				store : reportComboStore,
				editable : false,
				mode : 'remote',
				labelWidth : 75,
				width : 150,
				valueField : 'reportType',
				displayField : 'reportType',
				emptyText : '--请选择--',
				selectOnFocus : true,
				listeners : {
					'select' : loadReportName
				},
				selectOnFocus : true
			});

	reportTypeCombobox.on('select', loadReportName);
	var reportParamsPanel = new Ext.Panel({
				border : true,
				region : 'north',
				bodyStyle : 'padding:10px 0px',
				// autoHeight:true,
				layout : 'column',
				height : 40,
				items : [{
							border : false,
							columnWidth : .40,
							labelAlign : 'right',
							// labelWidth : 50,
							layout : 'form',
							items : [reportTypeCombobox]
						}]
			});
	var formUp = new Ext.form.FormPanel({
		labelAlign : 'right',
		title : '文件上传',
		// labelWidth: 60,
		buttonAlign : 'center',
		frame : true,
		url : './qrystat/reportTypeQueryAction!saveReport.action',
		defaults : {
			width : 300,
			height : 23,
			labelWidth : 100
		},
		fileUpload : true,
		items : [{
					xtype : 'textfield',
					anchor : '100%',
					width:250,
					fieldLabel : '报表ID',
					hidden:true,	
					hideLabel:true,
					name : 'reportId'
					// 文件类型
				},{
					xtype : 'textfield',
					anchor : '100%',
					width:250,
					fieldLabel : '报表文件',
					vtype : 'reports',
					name : 'reportFile',
					inputType : 'file'// 文件类型
				}, {
					xtype : 'textfield',
					fieldLabel : 'Js文件',
					vtype : 'jsType',
					name : 'jsFiles',
					inputType : 'file'// 文件类型
				}, {
					xtype : 'textfield',
					fieldLabel : '报表类型',
					value:repType,
					name : 'reportType',
					emptyText : '请输入报表类型'
   			    }, {
					xtype : 'textfield',
					fieldLabel : '报表名称',
					value:repName,
					name : 'reportName',
					emptyText : '请输入报表名称'
  			     }, {
					xtype : 'textarea',
					fieldLabel : '报表描述',
					value:repDes,
					height : 80,
					name : 'reportDescription',
					emptyText : '请输入报表描述',
					inputType : 'textarea'// 文件类型
				},{
					xtype : 'textfield',
					anchor : '100%',
					width:250,
					fieldLabel : '是否自定义报表',
					hidden:true,	
					hideLabel:true,
					name : 'defBool'
					// 文件类型
				}]
				});
	var sm = new Ext.grid.RowSelectionModel({
				singleSelect : true
			});

	var reportTypeCm = new Ext.grid.ColumnModel([{
				header : '报表ID',
				dateIndex : 'reportId',
				align : 'left',
				hidden : true			
			}, {
				header : '报表类型',
				dateIndex : 'reportType',
				align : 'left',
				hidden : true
			}, {
				header : '报表名称',
				dateIndex : 'reportName',
				align : 'left'
			}, {
				header : '报表描述',
				dateIndex : 'reportParam',
				align : 'left'
			}, {
				header : '是否是自定义报表',
				dateIndex : 'defBoolean',
				align : 'left',
				hidden : true
			}]);
	/** -----------------------存放报表信息的表格Store，第一个页面--------------- */
	var reportStore = new Ext.data.JsonStore({
				url : './qrystat/reportTypeQueryAction!queryReportInfoByType.action',
				fields : ['reportId', 'reportType', 'reportName',
						'reportParam', 'defBoolean'],
				root : 'reportList',
				totalProperty : "reportCount"
			});
	reportTypeGrid = new Ext.grid.GridPanel({
				mode : 'remote',
				region : 'south',
				cm : reportTypeCm,
				store : reportStore,
				sm : sm,
				loadMask : false,// true,加载数据时--"读取中"
				autoScroll : true,
				autoWidth : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				tbar : [{
							xtype : 'label',
							html : "<font font-weight:bold;>报表维护</font>"
						}, '->', {
							text : '增加报表',
							iconCls : 'plus',
							handler : function() {
								  var record1=reportTypeGrid.getSelectionModel().getSelected();
								 if(!Ext.isEmpty(record1))
								 {
								    repType=record1.get('reportType');
									formUp.getForm().findField('reportType').setValue(repType);
								 	repName=record1.get('reportName');
								 	formUp.getForm().findField('reportName').setValue(repName);
								 	repDes=record1.get('reportParam');
								 	formUp.getForm().findField('reportDescription').setValue(repDes);
								 }
								 templateInfoWindow.show();
							}
						}, {
							text : '修改报表',
							iconCls : 'plus',
							handler : function() {
								var record2 = reportTypeGrid.getSelectionModel()
										.getSelected();
								if ((null == record2) || Ext.isEmpty(record2)
										|| record2.length == 0) {
									Ext.Msg.alert("提示", "请选报表进行修改！");
									return;
								}
								else{
									formUp.getForm().reset();
									repId=record2.get('reportId');
									formUp.getForm().findField('reportId').setValue(repId);
									repType=record2.get('reportType');
									formUp.getForm().findField('reportType').setValue(repType);
								 	repName=record2.get('reportName');
								 	formUp.getForm().findField('reportName').setValue(repName);
								 	repDes=record2.get('reportParam');
								 	formUp.getForm().findField('reportDescription').setValue(repDes);
								 	defBoolean=record2.get('defBoolean');
								 	formUp.getForm().findField('defBool').setValue(defBoolean);
								 	formUp.getForm().url='./qrystat/reportTypeQueryAction!updateReport.action';
								}
								templateInfoWindow.show();
							}
						}, {
							text : '删除报表',
							iconCls : 'minus',
							handler : function() {
								var record = reportTypeGrid.getSelectionModel()
										.getSelected();
								if ((null == record) || Ext.isEmpty(record)
										|| record.length == 0) {
									Ext.Msg.alert("提示", "请选报表模板!");
									return;
								} else {
									Ext.MessageBox.show({
												title : "删除模板",
												msg : "你确定要删除此模板？",
												buttons : Ext.Msg.YESNOCANCEL,
												fn : deleteReport,
												icon : Ext.MessageBox.QUESTION
											});
								}
							}
						}]
			});
	Ext.apply(Ext.form.VTypes, {
				jsType : function(v) {
					return /.js$/.test(v);
				},
				jsTypeText : '文件必须为JS文件',
				reports : function(v) {
					return /.cpt$/.test(v);
				},
				reportsText : '文件必须为CPT文件'
			});
	
	var templateInfoWindow = new Ext.Window({
		title : ' 报表属性',
		frame : true,
		width : 525,
		height : 350,
		layout : "fit",
		layoutConfig : {
			columns : 5
		},
		// autoScroll : true,
		// modal : true,
		plain : true,// 设置背景颜色
		// resizable : true,// 不可移动
		buttonAlign : "center",// 按钮的位置
		closeAction : "hide",
		items : [formUp],
		buttons : [{
					text : '上传',
					handler : function() {
						formUp.getForm().submit({
									success : function(formUp, action) {
										Ext.Msg.alert('信息', '文件上传成功！');
										loadReportName();										
										templateInfoWindow.hide();
									},
									failure : function() {
										Ext.Msg.alert('错误', '文件上传失败');
									}
								})
					}
				}, {
					text : '取消',
					handler : function() {
						templateInfoWindow.hide();
					}
				}]
		});
	function deleteReport() {
		var record = reportTypeGrid.getSelectionModel().getSelected();
		Ext.Ajax.request({
					url : './qrystat/reportTypeQueryAction!deleteReport.action',
//					method : 'post',
					params : {
						'reportId' : reportTypeGrid.getSelectionModel()
								.getSelected().get('reportId')
					}
				});
		reportStore.remove(record);
		reportTypeGrid.reconfigure(reportStore, reportTypeCm);
	}
	// 利用Panel来存储GRID
	var bottomGridPanel = new Ext.Panel({
				layout : 'fit',
				region : 'center',
				autoScroll : true,
				border : false,
				plain : true,
				items : [reportTypeGrid]
			});
	// 第一个页面的综合Panel
	var reportTypePanel = new Ext.Panel({
				layout : 'border',
				border : false,
				items : [reportParamsPanel, bottomGridPanel]
			});
	// /**
	// ------------------------------------综合操作-----------------------------------
	// */
	//
	// // 上一步操作
	// var previousProcess = function(btn) {
	// if (reportManCard.layout.activeItem == reportTypePanel) {
	// } else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
	// reportManCard.remove(reportParamsPanelSecond);
	// reportManCard.layout.setActiveItem(0);
	// previousButton.hide();
	// }
	// };
	// // 下一步按钮处理方法
	// nextProcess = function() {
	// if (reportManCard.layout.activeItem == reportTypePanel) {
	//
	// if ((Ext.isEmpty(reportTypeGrid.getSelectionModel().getSelected()))) {
	// Ext.MessageBox.alert('提示', '请选择报表');
	// return;
	// }
	// var reportType = reportTypeGrid.getSelectionModel().getSelected()
	// .get('rawReportType');
	// var reportName = reportTypeGrid.getSelectionModel().getSelected()
	// .get('rawReportName');
	// reportManCard.remove(reportParamsPanelSecond);
	// Ext.Ajax.request({
	// url : './qrystat/reportTypeQueryAction!queryTemplateJSFile.action',
	// params : {
	// reportType : reportType,
	// reportName : reportName
	// },
	// success : function(response) {
	// var result = Ext.decode(response.responseText);
	// var scriptConent = result.scriptConent;
	// eval(scriptConent);
	// reportManCard.add(reportParamsPanelSecond);
	// reportManCard.doLayout();
	// reportManCard.layout.setActiveItem(1);
	// previousButton.show();
	// }
	// });
	// } else if (reportManCard.layout.activeItem == reportParamsPanelSecond) {
	// getParamsUrl();
	// ParamsUrl = ParamsUrl + "&reportId=" + reportId;
	// if (dataSelect && timeSelect) {
	// window.open("/sea/Report.servlet?" + ParamsUrl);
	// }
	// }
	// };

	// // 上一步按钮
	// var previousButton = new Ext.Button({
	// text : '上一步',
	// handler : previousProcess
	//
	// });
	// // 下一步按钮
	// var nextButton = new Ext.Button({
	// text : '下一步',
	// handler : nextProcess
	// });
	// // 综合界面
	// reportManCard = new Ext.Panel({
	// border : false,
	// region : 'center',
	// activeItem : 0,
	// layout : 'card',
	// // id : 'selfDefinitionReport',
	// buttonAlign : 'center',
	// items : [reportTypePanel, reportParamsPanelSecond],
	// buttons : [previousButton, nextButton]
	// });
	// previousButton.hide();
	renderModel(reportTypePanel, '报表维护');
});