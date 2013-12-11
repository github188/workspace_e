var flowStatusCode;
	/* 弹出主站分析异常查询窗口 */
function deviceExceptionShow(exceptionId) {
	window.exceptionId = exceptionId;
	openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
			"deviceAbnormal");
}

/* 弹出报警信息窗口 */
function alertWindowShow(rowId) {
	window.rowId = rowId;
	openTab("异常查询", "./runMan/abnormalHandle/exceptionSearch.jsp", false,
			"deviceAbnormal");
}
Ext.onReady(function(){
//-------------------------------------------------------------查询 begin	
	//开始日期
	var deviceA_startDate = new Ext.form.DateField({
		id : 'deviceA_startDate',
		fieldLabel : '从',
		name : 'deviceA_startDate',
//		labelStyle : "text-align:right;width:30px;",
//		width : 100,
		format: 'Y-m-d',
		editable:false,
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date().add(Date.DAY,-6)
    });
	
	//结束日期
	var deviceA_endDate = new Ext.form.DateField({
		id : 'deviceA_endDate',
		fieldLabel : '到',
		name : 'deviceA_endDate',
		editable:false,
//		labelStyle : "text-align:right;width:30px;",
//		width : 100,
		format: 'Y-m-d',
	    labelSeparator:'',
	    allowBlank:false,
	    value:new Date()
    });
	
    /* 查询异常 类型*/
//	var typeComStore = new Ext.data.JsonStore({
//				idProperty : 'eventTypeCode',
//				url : './runman/abnormalhandle/deviceAbnormal!savaRecord.action',
//				fields : ['eventTypeCode', 'eventTypeName'],
//				root : "pc",
//				autoLoad: true
//			});
			
	var typeComStore = new Ext.data.ArrayStore({
		fields : ['eventTypeCode', 'eventTypeName'],
		data : [[2, "终端上报事件"], [3, "主站分析终端异常"]]
//		data : [[1, "系统异常"], [2, "终端上报事件"], [3, "主站分析终端故障"],
//						[4, "主站分析用电异常"], [5, "主站分析数据异常"]]
	})
    
    var deviceCombobox = new Ext.form.ComboBox({
				fieldLabel : '异常类型',
				store : typeComStore,
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				valueField : 'eventTypeCode',
				displayField : 'eventTypeName',
				anchor : '90%',
				emptyText : '终端事件类型',
				selectOnFocus : true
    })
 
        /* 查询流程状态 */
//	var statusComStore = new Ext.data.JsonStore({
//				idProperty : 'statusCode',
//				url : './runman/abnormalhandle/deviceAbnormal!savaRecord.action',
//				fields : ['statusCode', 'statusName'],
//				root : "pc",
//				autoLoad: true
//			});

    var statusComStore = new Ext.data.ArrayStore({
		fields : ['statusCode', 'statusName'],
		data : [["00", "新工单"], ["02", "营销处理中"], ["03", "正常归档"],
						["04", "误报确认"], ["06", "挂起"], ["07", "本地处理中"],
						["08", "强制归档"]]
    })
	var statusCombobox = new Ext.form.ComboBox({
				// width : 170,
		fieldLabel : '流程状态',
		store : statusComStore,
		triggerAction : 'all',
		editable : false,
		mode : 'local',
		valueField : 'statusCode',
		displayField : 'statusName',
		anchor : '90%',
		emptyText : '流程状态',
		selectOnFocus : true
    })
    
	var deviceA_rg = new Ext.form.RadioGroup({
	    width : 180,
	    border : true,
	    columns :[65, 65],
	    items : [{
		    boxLabel : '未处理',
		    name : 'deviceA_type',
		    inputValue : 1,
		    checked : true
	    }, {
		    boxLabel : '已处理',
		    name : 'deviceA_type',
		    inputValue : 2
	    }]
	});
	
	var deviceA_systemParaPanel = new Ext.form.FormPanel({
		border:false,
		layout:'table',
		region : 'north',
		height:40,
		bodyStyle:'padding:10px 0px',
		items:[{
			border:false,
			labelAlign:'right',
			labelSeparator:'',
			labelWidth : 30,
			padding : '0px 0px 0px 10px',
			layout:'form',
			width : 145,
			items:[deviceA_startDate]
		},{
			border:false,
			labelAlign:'right',
			labelSeparator:'',
			labelWidth : 30,
			layout:'form',
			width : 145,
			items:[deviceA_endDate]
		},{
			border:false,
			padding : '0px 0px 0px 10px',
			layout:'form',
			labelWidth : 50,
			labelAlign:'right',
			width : 220,
			items:[deviceCombobox]
		},{
			border:false,
			padding : '0px 0px 0px 10px',
			layout:'form',
			labelWidth : 50,
			labelAlign:'right',
			width : 180,
			items:[statusCombobox]
		},{
			border:false,
			layout:'form',
			padding : '0px 0px 0px 10px',
			items:[{
				xtype:'button',
				text:'查询',
				width:70,
				handler : searchEventinfo
			}]
		}]
	});
	//查询工单列表
	function searchEventinfo() {
		if (!deviceA_systemParaPanel.getForm().isValid()) {
			Ext.Msg.alert('','请正确输入查询条件');
			return;
		}
		var myMask = new Ext.LoadMask(deviceAbnormalPanel.getId(), {
			msg : "加载中..."
		});
		systemStore.proxy = new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceAbnormal!searchEventInfo.action'
		}),
		myMask.show();
		systemStore.baseParams = {
			start : 0,
			limit : DEFAULT_PAGE_SIZE,
			// 检索开始时间
			startDate : deviceA_startDate.getValue(),
			// 检索结束时间
			endDate : deviceA_endDate.getValue().add(Date.DAY,1),
			// 工单状态
			eventStatus : statusCombobox.getValue(),
			// 异常类型
			eventType : deviceCombobox.getValue()
		}
		systemStore.load();
		systemStore.on('load', function() {
			myMask.hide();
		});
		systemStore.on('loadexception', function() {
			myMask.hide();
		})
	}
	
	function searchEventInfoByEventNo(fromId, eventType) {
		systemStore.proxy = new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceAbnormal!searchEventInfoByType.action'
		}),
		systemStore.baseParams = {
			// 异常来源ID
			fromId : fromId,
			// 异常类型
			eventType : eventType
		}
		systemStore.load();
	}
// -------------------------------------------------------------查询 end
//-------------------------------------------------------------系统异常 begin
	var systemStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : './runman/abnormalhandle/deviceAbnormal!searchEventInfo.action'
			}),
		reader : new Ext.data.JsonReader({
			root : 'eventInfoList',
			totalProperty : 'totalCount'
			}, [{
				name : 'eventId'
			}, {
				name : 'orgName'
			}, {
				name : 'eventType'
			}, {
				name : 'eventCode'
			}, {
				name : 'fromId'
			}, {
				name : 'evnetTime'
			}, {
				name : 'flowStatusCode'
			}
//			, {
//				name : 'jjsj'
//			}, {
//				name : 'clr'
//			}
			])
		})
	
	var systemSM = new Ext.grid.CheckboxSelectionModel( {
//		singleSelect : true
	});
	var systemRN = new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (systemStore && systemStore.lastOptions
							&& systemStore.lastOptions.params) {
						startRow = systemStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			})
	var systemCM = new Ext.grid.ColumnModel([systemRN,
//	systemSM,
		{header:'工单号',dataIndex:'eventId',sortable:true,align:'center'},
		{header:'供电单位',dataIndex:'orgName',sortable:true,align:'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val
					+ '</div>';
			return html;
		}},
//		{header:'资产编号',dataIndex:'zcbh',sortable:true,align:'center'},
		{header:'异常类型',dataIndex:'eventType',sortable:true,align:'center',
		renderer : function(v, m, rec) {
			switch (v) {
				case 1 :
				return "<a href='javascript:'onclick='origFrameQryShow(\""
					+ rec.get('fromId') + "\");" + "'>系统异常</a>";
				case 2 :
				return "<a href='javascript:'onclick='alertWindowShow(\""
				+ rec.get('fromId')	+ "\");" + "'>终端上报事件</a>";
				case 3 :
				return "<a href='javascript:'onclick='deviceExceptionShow(\""
					+ rec.get('fromId')	+ "\");" + "'>主站分析终端故障</a>";
				case 4 :
				return "<a href='javascript:'onclick='origFrameQryShow(\""
					+ rec.get('fromId') + "\");" + "'>主站分析用电异常</a>";
				case 5 :
				return "<a href='javascript:'onclick='origFrameQryShow(\""
					+ rec.get('fromId')	+ "\");" + "'>主站分析数据异常</a>";
			}
		}
		},
		{header:'告警编码',dataIndex:'eventCode',sortable:true,align:'center'},
		{header:'来源',dataIndex:'fromId',sortable:true,align:'center',
		renderer : function(v, m, rec) {
			switch (rec.get('eventType')) {
				case 3 :
				return "主站分析";
				case 2 :
				return "终端";
			}
		}},
		{header:'工单处理状态',dataIndex:'flowStatusCode',sortable:true,align:'center',
		renderer : function(val) {
			switch (val) {
				case '00' :
				var html = '<div align = "left">' + '新异常'
					+ '</div>';
				return html;
				case '02' :
				var html = '<div align = "left">' + '营销处理中'
					+ '</div>';
				return html;
				case '03' :
				var html = '<div align = "left">' + '正常归档'
					+ '</div>';
				return html;
				case '04' :
				var html = '<div align = "left">' + '误报确认'
					+ '</div>';
				return html;
				case '06' :
				var html = '<div align = "left">' + '挂起'
					+ '</div>';
				return html;
				case '07' :
				var html = '<div align = "left">' + '本地处理中'
					+ '</div>';
				return html;
				case '08' :
				var html = '<div align = "left">' + '强制归档'
					+ '</div>';
				return html;
			}
		}},
		{header:'发生时间',dataIndex:'evnetTime',sortable:true,align:'center',
		renderer : function(val) {
			return Date.parseDate(val,'Y-m-dTH:i:s').format('Y-m-d');
		}}
//		{header:'派工时间',dataIndex:'pgsj',sortable:true,align:'center'},
//		{header:'解决时间',dataIndex:'jjsj',sortable:true,align:'center'},
//		{header:'处理人',dataIndex:'clr',sortable:true,align:'center'}
	]);
	
	var deviceA_systemGrid = new Ext.grid.GridPanel({
		cm:systemCM,
		region : 'center',
		layout:'fit',
		store:systemStore,
		viewConfig:{
			forceFit:true
		},
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>待处理异常列表</font>"
		}],
		bbar : new Ext.ux.MyToolbar( {
			enableExpAll : true,
			store : systemStore
		})
	});
	
	//选中工单明细显示
	deviceA_systemGrid.on("rowclick", function(grid, rowIndex) {
		eventNoText.setValue(grid.getStore().getAt(rowIndex).get("eventId"));
		eventNameText.setValue(grid.getStore().getAt(rowIndex).get("ycmc"));
		var status = grid.getStore().getAt(rowIndex).get("flowStatusCode");
		flowStatusCode = status;
		changeButtonStauts(status);
		deviceA_systemHandle.doLayout();
	})
	
	function changeButtonStauts(status) {
		switch (status) {
			case '00' :
				eventStatusText.setValue('新异常');
				sendMsgButton.setDisabled(false);
				localButton.setDisabled(false);
				sendToMarketButton.setDisabled(false);
				holdOnButton.setDisabled(false);
				saveRecordButton.setDisabled(false);
				saveMisstakeButton.setDisabled(false);
				break;
			//02营销处理中
			case '02' :
				eventStatusText.setValue('营销处理中');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(false);
				saveMisstakeButton.setDisabled(false);
				break;
			//03正常归档
			case '03' :
				eventStatusText.setValue('正常归档');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(true);
				saveMisstakeButton.setDisabled(true);
				break;
			//04误报确认
			case '04' :
				eventStatusText.setValue('误报确认');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(true);
				saveMisstakeButton.setDisabled(true);
				break;
			//06挂起
			case '06' :
				eventStatusText.setValue('挂起');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(true);
				saveMisstakeButton.setDisabled(true);
				break;
			//07本地处理中
			case '07' :
				eventStatusText.setValue('本地处理中');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(false);
				saveMisstakeButton.setDisabled(false);
				break;
			//08强制归档
			case '08' :
				eventStatusText.setValue('强制归档');
				sendMsgButton.setDisabled(true);
				localButton.setDisabled(true);
				sendToMarketButton.setDisabled(true);
				holdOnButton.setDisabled(true);
				saveRecordButton.setDisabled(true);
				saveMisstakeButton.setDisabled(true);
				break;
			}		
	}
//-------------------------------------------------------------系统异常 end
	
//-------------------------------------------------------------异常处理 begin
	
	//异常原因
	var reasonPanel = new Ext.Panel({
		border:false,
		layout:'form',
		labelSeparator:' ',
		autoWidth:true,
		labelAlign:'right',
		items:[{
			xtype:'textarea',
			fieldLabel:'异常原因',
			readOnly : true,
			autoScroll:true,
			width:630
		}]
	});
	
	//异常原因
	var deviceA_reason = new Ext.form.TextField({
		border:false,
	    fieldLabel:'异常信息',
	    labelAlign:'right',
	    readOnly : true,
//	    height:25,
		width:630
	});
	
	//处理说明
	var deviceA_done = new Ext.form.TextArea({
	    border:false,
	    fieldLabel:'处理说明',
	    labelAlign:'right',
	    height:40,
	    labelSeparator:'',
		width:630
	});
	
	var eventNoText = new Ext.form.TextField({
			    	autoWidth : true,
				    fieldLabel:'异常工单号',
				    readOnly : true,
				    anchor : '90%'
			    })
	var eventNameText =new Ext.form.TextField({
				    fieldLabel:'异常名称',
				    readOnly : true,
				    anchor : '95%'
			    })
	var eventStatusText = new Ext.form.TextField({
				    fieldLabel:'处理状态',
				    readOnly : true,
				    anchor : '95%'
			    })
	var  handlePersonText = new Ext.form.TextField({
				    fieldLabel:'处理人',
				    autoWidth : true,
				    anchor : '90%'
//				    width:100
			    })
	var handleDateText = new Ext.form.DateField({
				    fieldLabel:'处理日期',
				    format: 'Y-m-d',
				    value: new Date()
//				    width:100
			    })
	var sendMsgButton = new Ext.Button({
					disabled : true,
					text : '通知客户经理',
					width : 70,
					handler : function () {
						Ext.MessageBox.confirm('提示', '确定通知客户经理吗?', sendMsgToManager);
					}
				})
	var localButton = new Ext.Button({
					disabled : true,
					text : '本地处理',
					width : 70,
					handler : function () {
						Ext.MessageBox.confirm('提示', '确定本地处理吗?', doLocal);
					}
				})
	var sendToMarketButton = new Ext.Button({
					disabled : true,		
					text : '派工',
					width : 70,
					handler : function () {
						Ext.MessageBox.confirm('提示', '确定营销派工吗?', sendToMarket);
					}
				})
	var holdOnButton = new Ext.Button({
					disabled : true,
					text : '挂起',
					width : 70,
					handler : function () {
						Ext.MessageBox.confirm('提示', '确定挂起吗?', holdOnEvent);
					}
				})
	var saveRecordButton = new Ext.Button({
				disabled : true,		
				text : '归档',
				width : 70,
				handler : function () {
					Ext.MessageBox.confirm('提示', '确定归档吗?', saveRecord);
				}
			})
	var saveMisstakeButton = new Ext.Button({
				disabled : true,
				text : '误报确认',
				width : 70,
				handler : function () {
					flowStatusCode = '04';
					Ext.MessageBox.confirm('提示', '确定误报确认吗?', saveRecord);
				}
			})
	var sendToMBButton = new Ext.Button({
					disabled : true,
					text:'批量派工',
					width : 70,
					handler : function () {
						Ext.MessageBox.confirm('提示', '确定批量派工吗?', sendToMarketBatch);
					}
				})
	var deviceA_systemHandle = new Ext.FormPanel({
		border:false,
		layout:'form',
		region:'south',
		height:170,
		tbar : [{
			xtype:'label',
			html : "<font font-weight:bold;>待处理异常明细</font>"
		}],
		items:[{
			border:false,
		    layout:'table',
		    bodyStyle:'padding:5px 0px 0px 0px;',
		    items:[{
			    width:250,
			    border:false,
			    layout:'form',
//			    labelSeparator:' ',
//			    labelWidth:60,
			    labelAlign:'right',
//			    padding:'0px 0px 0px 40px',
			    items:[eventNoText]
		    }
//		    ,{
//			    width:200,
//			    border:false,
//			    layout:'form',
//			    labelAlign:'right',
//			    padding:'0px 0px 0px 15px',
//			    items:[{
//				    xtype:'button',
//				    text:'处理记录',
//				    width:80
//			    }]
//		    }
//		    ,{
//			    width:250,
//			    border:false,
//			    layout:'form',
//			    labelWidth:55,
//			    padding:'0px 0px 0px 10px',
//			    labelAlign:'right',
//			    items:[eventNameText]
//		    }
		    ,{
			    width:250,
			    border:false,
			    layout:'form',
			    labelWidth:55,
			    labelAlign:'right',
			    items:[eventStatusText]
		    }]
		}
//		, {
//			border:false,
//			layout:'form',
//			labelAlign:'right',
//			items:[deviceA_reason]
//		}
		, {
			border:false,
		    layout:'table',
		    bodyStyle:'padding:5px 0px 0px 0px;',
		    items:[{
			    width:250,
			    border:false,
			    layout:'form',
			    labelAlign:'right',
			    items:[handlePersonText]
		    }, {
			    width:250,
			    border:false,
			    layout:'form',
			    labelWidth:55,
			    labelAlign:'right',
			    items:[handleDateText]
		    }]
		}, {
			border:false,
			layout:'form',
			labelAlign:'right',
			items:[deviceA_done]
		}, {
			border : false,
			layout : 'table',
			labelAlign : 'right',
			padding : '5px 0px 0px 100px',
			items : [{
				border : false,
				layout : 'form',
				width : 100,
				labelAlign : 'right',
				items : [sendMsgButton]
				}, {
				border : false,
				width : 90,
				layout : 'form',
				labelAlign : 'right',
				items : [localButton]
			}, {
				border : false,
				layout : 'form',
				width : 90,
				labelAlign : 'right',
				items : [sendToMarketButton]
				}, {
				border : false,
				layout : 'form',
				width : 90,
				labelAlign : 'right',
				items : [holdOnButton]
				}, {
				border : false,
				layout : 'form',
				width : 90,
				labelAlign : 'right',
				items : [saveRecordButton]
				}, {
				border : false,
				layout : 'form',
				width : 90,
				labelAlign : 'right',
				items : [saveMisstakeButton]
				}, {
				border : false,
				layout : 'form',
				width : 90,
				labelAlign : 'right',
				items : [sendToMBButton]
				}]
		}
		]
	});
	//通知客户经理请求
	function sendMsgToManager(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			url : './runman/abnormalhandle/deviceAbnormal!reportManager.action',
			success : function(response) {
				var rec = deviceA_systemGrid.getSelectionModel().getSelected();
				rec.set('flowStatusCode', '');
				rec.commit();
				changeButtonStauts('');
			}
		})
	}
	//本地处理请求
	function doLocal(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			params : {
				eventId : eventNoText.getValue(),
				eventStatus : flowStatusCode,
				eventData : deviceA_done.getValue()
			},
			url : './runman/abnormalhandle/deviceAbnormal!localHandle.action',
			success : function(response) {
				var rec = deviceA_systemGrid.getSelectionModel().getSelected();
				//成功后状态变更为本地处理中
				rec.set('flowStatusCode', '07');
				rec.commit();
				changeButtonStauts('07');
			}
		})
	}
	//营销派工请求
	function sendToMarket(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			params : {
				eventId : eventNoText.getValue(),
				eventStatus : flowStatusCode,
				eventData : deviceA_done.getValue()
			},
			url : './runman/abnormalhandle/deviceAbnormal!sendToMarketing.action',
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (result.success) {
					var rec = deviceA_systemGrid.getSelectionModel().getSelected();
					//处理成功后状态为营销处理中
					rec.set('flowStatusCode', '02');
					rec.commit();
					changeButtonStauts('02');
				}
			}
		})
	}
	//挂起请求
	function holdOnEvent(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			url : './runman/abnormalhandle/deviceAbnormal!holdOn.action',
			success : function(response) {
				var rec = deviceA_systemGrid.getSelectionModel().getSelected();
				//处理成功后状态为挂起
				rec.set('flowStatusCode', '06');
				rec.commit();
				changeButtonStauts('06');
			}
		})
	}
	//归档请求
	function saveRecord(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			params : {
				eventId : eventNoText.getValue(),
				eventStatus : flowStatusCode,
				eventData : deviceA_done.getValue()
			},			
			url : './runman/abnormalhandle/deviceAbnormal!savaRecord.action',
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var rec = deviceA_systemGrid.getSelectionModel().getSelected();
				rec.set('flowStatusCode', result.eventStatus);
				rec.commit();
				changeButtonStauts(result.eventStatus);
			}
		})
	}
	//批量派工请求
	function sendToMarketBatch(btn) {
		if (btn == 'no') {
			return;
		}
		Ext.Ajax.request({
			url : './runman/abnormalhandle/deviceAbnormal!sendToMarketBatch.action',
			success : function(response) {
//				var rec = deviceA_systemGrid.getSelectionModel().getSelected();
//				rec.set('flowStatusCode', '');
//				rec.commit();
//				changeButtonStauts(result.eventStatus);
			}
		})
	}
	
	var deviceAbnormalPanel = new Ext.Panel({
		autoScroll : true,
		border : false,
		layout:'border',
		items : [deviceA_systemParaPanel,deviceA_systemGrid,deviceA_systemHandle]
	});

	renderModel(deviceAbnormalPanel,'现场设备异常');
	//页面非激活状态下调用方法
	if (!Ext.isEmpty(window.fromId)) {
		searchEventInfoByEventNo(window.fromId, window.eventType);
		window.fromId = '';
		window.eventType = '';
	}
	//页面为激活状态时调用此方法
	Ext.getCmp('现场设备异常').on('activate', function() {
		if (!Ext.isEmpty(window.fromId)) {
			searchEventInfoByEventNo(window.fromId, window.eventType);
			eventNoText.setValue('');
			eventStatusText.setValue('');
			handlePersonText.setValue('');
			sendMsgButton.setDisabled(true);
			localButton.setDisabled(true);
			sendToMarketButton.setDisabled(true);
			holdOnButton.setDisabled(true);
			saveRecordButton.setDisabled(true);
			saveMisstakeButton.setDisabled(true);
			window.fromId = '';
			window.eventType = '';
		}
	});
//-------------------------------------------------------------异常处理 end			
});