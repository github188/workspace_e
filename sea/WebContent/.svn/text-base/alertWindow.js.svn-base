/**
 * 报警信息窗口
 */
//var ALERT_LEVEL = '00';
//Ext.onReady(function() {


	var terminalAddrStore = new Ext.data.JsonStore({
			url : "./sysman/alertevent!queryTerminalAddr.action",
			fields : [ 'terminalId', 'terminalAddr' ],
			root : "terminalList"
	});

		//
	var terminalAddrCombox = new Ext.form.ComboBox({
		store : terminalAddrStore,
		displayField : 'terminalAddr',
		valueField : 'terminalId',
		labelStyle : "text-align:right;",
		name : 'terminalAddrCombox',
		mode : 'remote',
		editable : false,
		triggerAction : 'all',
		anchor : '95%',
		fieldLabel : '终端地址'
	});
	terminalAddrStore.load();
	
	var alertEventTypeStore = new Ext.data.JsonStore({
		url : "./sysman/alertevent!queryEventType.action",
		fields : [ 'eventNo', 'eventName' ],
		root : "bpe"
	});
	
		//
	var alertEventTypeCombox = new Ext.form.ComboBox({
		store : alertEventTypeStore,
		displayField : 'eventName',
		valueField : 'eventNo',
		labelStyle : "text-align:right;",
		name : 'alertEventTypeCombox',
		mode : 'remote',
//		editable : false,
		triggerAction : 'all',
		anchor : '95%',
		fieldLabel : '事件类型'
	});
	alertEventTypeStore.load();

	//左边树监听时点击树节点调用的方法
	function alertClick(p, node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
		if(type == 'org'){
			nodeTextField.setValue(node.text);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.orgNo);
			hideOrgTypeTextField.setValue(obj.orgType);
			consTypeCombox.setDisabled(false);

			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		}else if (type == 'usr'){
			nodeTextField.setValue(obj.consName);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.consNo);
			
   			//加载用户下的终端地址
   			terminalAddrStore.baseParams = {
   				consNo : obj.consNo
   			};
   			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
			
			consTypeCombox.setValue("");
			consTypeCombox.setDisabled(true);
		}else if(type == 'line'){
			nodeTextField.setValue(node.text);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.lineId);
			consTypeCombox.setDisabled(false);

			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		}else if(type == 'cgp' || type=='ugp'){
			nodeTextField.setValue(node.text);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.groupNo);
			consTypeCombox.setDisabled(false);

			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		}else if(type == 'sub'){
			nodeTextField.setValue(node.text);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.subsId);
			consTypeCombox.setDisabled(false);

			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		}else if(type == 'trade'){
			nodeTextField.setValue(node.text);
			hideNodeTypeTextField.setValue(type);
			hideNodeTextField.setValue(obj.tradeNo);
			consTypeCombox.setDisabled(false);

			terminalAddrCombox.setValue("");
   			consTypeCombox.setValue("");
   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		}else {
			return true;
		}
	}
	//左边树监听时选择用户调用的方法
	function alertUserGridSelect(cm,row,record){
		nodeTextField.setValue(record.get('consName'));
			hideNodeTypeTextField.setValue('usr');
			consTypeCombox.setValue("");
			consTypeCombox.setDisabled(true);
			
			//加载用户下的终端地址
			terminalAddrStore.baseParams = {
				consNo : record.get('consNo')
			};
			terminalAddrCombox.setValue("");
			consTypeCombox.setValue("");
			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
			
			hideNodeTextField.setValue(record.get('consNo'));
	}
	//左边导航区事件监听
//	var treeListener = new LeftTreeListener({
//		modelName : '事件报警查询',
//		processClick : function(p, node, e) {
//			var obj = node.attributes.attributes;
//			var type = node.attributes.type;
//			if(type == 'org'){
//				nodeTextField.setValue(node.text);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.orgNo);
//				hideOrgTypeTextField.setValue(obj.orgType);
//				consTypeCombox.setDisabled(false);
//
//				terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
//			}else if (type == 'usr'){
//				nodeTextField.setValue(obj.consName);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.consNo);
//				
//	   			//加载用户下的终端地址
//	   			terminalAddrStore.baseParams = {
//	   				consNo : obj.consNo
//	   			};
//	   			terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
//				
//				consTypeCombox.setValue("");
//				consTypeCombox.setDisabled(true);
//			}else if(type == 'line'){
//				nodeTextField.setValue(node.text);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.lineId);
//				consTypeCombox.setDisabled(false);
//
//				terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
//			}else if(type == 'cgp' || type=='ugp'){
//				nodeTextField.setValue(node.text);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.groupNo);
//				consTypeCombox.setDisabled(false);
//
//				terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
//			}else if(type == 'sub'){
//				nodeTextField.setValue(node.text);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.subsId);
//				consTypeCombox.setDisabled(false);
//
//				terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
//			}else if(type == 'trade'){
//				nodeTextField.setValue(node.text);
//				hideNodeTypeTextField.setValue(type);
//				hideNodeTextField.setValue(obj.tradeNo);
//				consTypeCombox.setDisabled(false);
//
//				terminalAddrCombox.setValue("");
//	   			consTypeCombox.setValue("");
//	   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//	   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
//			}else {
//				return true;
//			}
//		},
//		processUserGridSelect:function(cm,row,record){
//			nodeTextField.setValue(record.get('consName'));
//   			hideNodeTypeTextField.setValue('usr');
//   			consTypeCombox.setValue("");
//   			consTypeCombox.setDisabled(true);
//   			
//   			//加载用户下的终端地址
//   			terminalAddrStore.baseParams = {
//   				consNo : record.get('consNo')
//   			};
//   			terminalAddrCombox.setValue("");
//   			consTypeCombox.setValue("");
//   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
//   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
////   			terminalAddrStore.load();
//   			
//   			hideNodeTextField.setValue(record.get('consNo'));
//		}
//	});
	
	function getAlertMessage() {
		alertEventStore.baseParams = {
			consNo : alertconsNo,
			orgNo : alertorgNo
		};
		alertEventStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	//alertEventStore.load();
	};

	//节点名		
	var nodeTextField = new Ext.form.TextField({
		name : 'nodeTextField',
		fieldLabel:'节点名',
		readOnly : true,
		labelStyle : "text-align:right;",
		allowBlank:false,
		anchor : '95%',
		emptyText:'请选择节点...',
		tooltip : {
			text : ''
		},
		setValue : function (v){
			this.value = v;
			if(this.rendered){
				this.el.dom.value = (Ext.isEmpty(v) ? '' : v);
				this.validate();
			}
			new Ext.ToolTip( {
				target : this.id,
				trackMouse : false,
				draggable : true,
				title : this.tooltip.title,
				html : this.value,
				text : this.value
			});
			return this;
		}
	});
	
	//节点类型
	var hideNodeTypeTextField = new Ext.form.TextField({
		name : 'hideNodeTypeTextField',
		hidden:true
	});
	
	//orgType
	var hideOrgTypeTextField = new Ext.form.TextField({
		name : 'hideOrgTypeTextField',
		hidden:true
	});
	
	//节点id
	var hideNodeTextField = new Ext.form.TextField({
		name : 'hideNodeTextField',
		hidden:true
	});
	
	//开始时间		
	var startTimeField = new Ext.form.DateField({
		name : 'startTimeField',
		allowBlank: false,
		editable : false,
		labelStyle : "text-align:right;",
		emptyText : '请选择日期...',
		fieldLabel :'从',
		anchor : '95%',
		value : new Date().add(Date.DAY, -1),
		format : 'Y-m-d'
	});
	
	//结束时间
	var endTimeField = new Ext.form.DateField({
		name : 'endTimeField',
		allowBlank: false,
		labelStyle : "text-align:right;",
		editable : false,
		emptyText : '请选择日期...',
		fieldLabel :'到',
		anchor : '95%',
		value : new Date(),
		format : 'Y-m-d'
	});

	//用户类型 store
	var consTypeStore = new Ext.data.JsonStore({
			url : "baseapp/gatherQualityEvaluate!queryConsType.action",
			fields : [ 'consType', 'consTypeName' ],
			root : "consTypeList"
		});
	//用户类型
	var consTypeCombox = new Ext.form.ComboBox({
		store : consTypeStore,
		displayField : 'consTypeName',
		valueField : 'consType',
		labelStyle : "text-align:right;",
		name : 'consTypeCombox',
		mode : 'remote',
		editable : false,
		triggerAction : 'all',
		anchor : '95%',
		
		fieldLabel : '用户类型'
	});
	
	consTypeStore.load();
	
	function DeviceMonitorAlertMessage() {
		terminalAddrCombox.setValue("");
		consTypeCombox.setValue("");
		terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
		consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
		
		nodeTextField.setValue(excep_compName);
		hideNodeTextField.setValue(excep_orgNo);
		startTimeField.setValue(excep_startDate);
		endTimeField.setValue(excep_endDate);
		alertEventStore.baseParams = {
			orgNo : excep_orgNo,
			alertLevel : excep_seriousLevel,
			startDate : startTimeField.getValue().format('Y-m-d'),
			endDate : endTimeField.getValue().format('Y-m-d')
//			endDate : endTimeField.getValue().add(Date.DAY, 1).format('Y-m-d')
		
		};
		alertEventStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};
	
			var alertEventColumnModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '数据ID',
						dataIndex : 'dataId',
						hidden : true
					}, {
						header : '供电单位',
						dataIndex : 'orgName',
						width : 160
					}, {
						header : '供电单位编码',
						dataIndex : 'orgNo',
						width : 160,
						hidden:true
					}, {
						header : '用户编号',
						dataIndex : 'consNo'
					}, {
						header : '用户名称',
						dataIndex : 'consName',
						hidden:true
					}, {
						header : '事件名称',
						dataIndex : 'eventName',
						width : 160
					}, {
						header : '事件时间',
						dataIndex : 'eventTime',
						width : 160,
						format : 'Y-m-d H:i:s',
						xtype : 'datecolumn'
					}, {
						header : '事件来源',
						dataIndex : 'fromType',
						renderer : function(val,cellMeta,record) {
							var disp = '';
							switch (val) {
								case 0 :
									disp = '终端';
								case 1 :
									disp = '测量点';
									break;
								case 2 :
									disp = '总加组';
									break;
								case 3 :
									disp = '电能量差动';
									break;
								case 4 :
									disp = '直流模拟端口';
									break;
								default :
									disp = '未知';

							}
							return disp+' ['+record.get('fromNo')+']';
						}
					}, {
						header : '严重等级',
						dataIndex : 'eventLevel',
                        renderer : function(val) {
                            var rtn = '一般';
                            if (val == '03')
                                rtn = '严重';
                            else if (val == '02')
                                rtn = '次要';
                            else
                                rtn = '一般';
                            return rtn;

                        }
					}, {
						header : '起止标志',
						dataIndex : 'isStart',
						hidden : true,
						renderer : function(val) {
							var rtn = '其它';
							if (val == 1)
								rtn = '起始';
							else if (val == 0)
								rtn = '恢复';
							else
								rtn = '其它';
							return rtn;

						}

					}, {
						header : '接收时间',
						dataIndex : 'receiveTime',
						format : 'Y-m-d H:i:s',
						xtype : 'datecolumn',
						hidden : true

					}, {
						header : '来源编号',
						dataIndex : 'fromNo',
						hidden : true
					}, {
						header : '存储类型',
						dataIndex : 'storeType',
						hidden : true
					}, {
						header : '事件类型',
						dataIndex : 'eventType',
						hidden : true
					}, {
						header : '存储方式',
						dataIndex : 'storeMode',
						hidden : true
					}, {
						header : '规约编码',
						dataIndex : 'protocolCode',
						hidden : true
					}, {
						header : '终端逻辑地址',
						dataIndex : 'tmnlAssetNo',
						hidden : true
					}, {
						header : '终端编号',
						dataIndex : 'assetNo',
						hidden : true
					}, {
						header : '终端类型',
						dataIndex : 'mpType',
						hidden : true
					}, {
						header : '数据来源',
						dataIndex : 'dataSrc',
						hidden : true
					},{
						header : '状态',
						dataIndex : 'flowStatusCode',
						renderer : function(val){
							if(val == "00"){
								return "新异常";
							}else{
								return "";
							}
						}
					},{
						header :'相关操作',
						renderer : function(){
							var html = '<a href="javascript:f_attention();">关注 </a>&nbsp;&nbsp;&nbsp;<a href="javascript:f_dealWith();">处理</a>';
							return html;
						}
					}]);

					
			var alertEventStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : './sysman/alertevent!alertEvent.action'
								}),
						reader : new Ext.data.JsonReader({
									root : 'alertEventList',
									totalProperty : 'totalCount'
								}, [{
											name : 'rd'
										}, {
											name : 'dataId'
										}, {
											name : 'eventNo'
										}, {
											name : 'isStart'
										}, {
											name : 'eventTime',
											type : 'date',
											dateFormat : 'Y-m-dTH:i:s'
										}, {
											name : 'receiveTime',
											type : 'date',
											dateFormat : 'Y-m-dTH:i:s'
										}, {
											name : 'fromType'
										}, {
											name : 'fromNo'
										}, {
											name : 'storeType'
										}, {
											name : 'eventName'
										}, {
											name : 'eventType'
										}, {
											name : 'eventLevel'
										}, {
											name : 'storeMode'
										}, {
											name : 'protocolCode'
										}, {
											name : 'consNo'
										}, {
											name : 'consName'
										}, {
											name : 'tmnlAssetNo'
										}, {
											name : 'assetNo'
										}, {
											name : 'orgName'
										}, {
											name : 'orgNo'
										}, {
											name : 'mpType'
										}, {
											name : 'dataSrc'
										},{
											name : 'flowStatusCode'
										}])
					});

			var mainGrid = new Ext.grid.GridPanel({
						title :'报警事件列表',
						border : false,
						region :'center',
						cm : alertEventColumnModel,
						ds : alertEventStore,
						bbar : new Ext.ux.MyToolbar({
									store : alertEventStore,
									enableExpAll : true
								}),
						sm : new Ext.grid.RowSelectionModel({
									singleSelect : true
								})
					});


//			//查询按钮点击事件
//			var queryEvent = function(){
//				
//			};

			//查询条件	
			var queryPanel = new Ext.Panel({
				region :'north',
				height:70,
				border :false,
				layout:'form',
				items : [{
					layout:'table',
					border : false,
					layoutConfig : {
						columns : 3
					},
					bodyStyle : 'padding:10px 0px 0px 0px',
					items : [{
						width : 250,
						layout:'form',
						labelWidth : 60,
						border : false,
						items:[nodeTextField]
					},{
						width : 200,
						labelWidth : 60,
						id : 'queryConditionPanel',
						name : 'queryConditionPanel',
						layout:'form',
						border : false,
						items:[terminalAddrCombox,consTypeCombox]
					},{
						width : 200,
						labelWidth : 60,
						id : 'eventTypePanel',
						name : 'eventTypePanel',
						layout:'form',
						border : false,
						items:[alertEventTypeCombox]
					},{
						layout : 'form',
						border : false,
						labelWidth : 60,
						width : '180',
						items : [startTimeField]
					},{
						layout : 'form',
						border : false,
						labelWidth : 60,
						width : '180',
						items : [endTimeField]
					},{
//						width : 60,
						layout:'form',
						border : false,
						items:[{
							xtype:'button',
							text:'查询',
							width : 60,
							handler:function (){
								if (nodeTextField.getValue() == null || nodeTextField.getValue() == ""){
									nodeTextField.focus();
									Ext.Msg.alert('提示','请选择节点名！');
									return ;
								}
								
								if(startTimeField.getValue() > endTimeField.getValue()){
									Ext.Msg.alert('提示','结束日期不能小于开始日期！');
									return ;
								}
								
								alertEventStore.baseParams={
									type : hideNodeTypeTextField.getValue(),
									nodeid : hideNodeTextField.getValue(),
									consType : consTypeCombox.getValue(),
									eventType : alertEventTypeCombox.getValue(),
									orgType : hideOrgTypeTextField.getValue(),
									terminalAddr : terminalAddrCombox.getValue(),
									startDate : startTimeField.getValue().format('Y-m-d'),
									endDate : endTimeField.getValue().format('Y-m-d')
								};
								
								alertEventStore.load({
									params : {
										start : 0,
										limit : DEFAULT_PAGE_SIZE
									}
								});
							
							}
						}]					
					}]
				}]
			});
			
			
			// 报警事件主窗口
			var center = new Ext.Panel({
//						title : '报警事件列表',
						region : "center",
						layout : "border",
						items : [queryPanel,mainGrid]
					});

			// 报警事件明细窗口
			var detail = new Ext.form.FormPanel({
						title : '报警事件明细信息',
						anchor : '100% 50%',
						bodyStyle : 'padding: 5px 5px 5px 5px',
						border : false,
						labelWidth : 80,
						labelAlign : "right",
						layout : "form",
						defaults : {
							width : 160
						},
						items : [new Ext.form.TextField({
											name : 'orgName',
											fieldLabel : '供电单位'
										}), new Ext.form.TextField({
											name : 'consNo',
											fieldLabel : '用户编号'
										}), new Ext.form.TextField({
											name : 'eventName',
											fieldLabel : '事件名称'
										}), new Ext.form.DateField({
											name : 'eventTime',
											fieldLabel : '发生时间',
											format : 'Y-m-d H:i:s'
										}), new Ext.form.ComboBox({
											store : new Ext.data.SimpleStore({
														fields : ['value',
																'disp'],
														data : [
																['1', '测量点'],
																['2', '总加组'],
																['3', '电能量差动'],
																['4', '直流模拟端口'],
																['0', '其它'],
																['5', '其它']]
													}),
											mode : 'local',
											displayField : 'disp',
											valueField : 'value',
											name : 'fromType',
											fieldLabel : '事件来源',
											triggerAction : 'all'

										}), new Ext.form.TextField({
											name : 'fromNo',
											fieldLabel : '来源编号'
										}), new Ext.form.TextField({
											name : 'tmnlAssetNo',
											fieldLabel : '终端地址'
										}), new Ext.form.TextField({
											name : 'dataId',
											fieldLabel : '数据ID',
											width : 200,
											hidden : true,
											hideLabel : true
										}), new Ext.form.ComboBox({
											store : new Ext.data.SimpleStore({
														fields : ['value',
																'disp'],
														data : [['1', '起始'],
																['0', '终止'],
																['2', '其它']]
													}),
											mode : 'local',
											displayField : 'disp',
											valueField : 'value',
											name : 'isStart',
											fieldLabel : '起止标志',
											triggerAction : 'all'

										}), new Ext.form.DateField({
											name : 'receiveTime',
											fieldLabel : '接收时间',
											format : 'Y-m-d H:i:s'
										})]
					});

			var subCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
						header : '数据项',
						dataIndex : 'dataName'
					}, {
						header : '数据值',
						dataIndex : 'dataValue'
					}, {
						header : '单位',
						dataIndex : 'dataUnit',
						hidden : true
					}]);

			var subStore = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : './sysman/alertevent!alertDataItem.action'
								}),
						reader : new Ext.data.JsonReader({
									root : 'alertDataItemList'
								}, [{
											name : 'dataName'
										}, {
											name : 'dataValue'
										}, {
											name : 'dataUnit'
										}])
					});

			var subGrid = new Ext.grid.GridPanel({
						border : false,
						cm : subCm,
						ds : subStore,
						autoScoll : true
					});

			var subdetail = new Ext.Panel({
						title : '数据项明细',
						border : false,
						anchor : '100% 50%',
						layout : "fit",
						height : 172,
						items : [subGrid]
					});
			var east = new Ext.Panel({
						// title : '报警事件明细信息',
						region : "east",
						width : 260,
						layout : "anchor",
						items : [detail, subdetail]
					});

			var panel = new Ext.Panel({
						width : 682,
						height : 415,
						border : false,
						layout : "border",
						items : [center, east]
					});

			// 点击事件
			mainGrid.getSelectionModel().on('rowselect',
					function(sm, row, rec) {
						detail.getForm().loadRecord(rec);
						subStore.baseParams = {
							orgNo : rec.get('orgNo'),
							rd : rec.get('rd'),
							storeMode : rec.get('storeMode')
						};
						subStore.load();

					});

			alertEventStore.on('load', function(store, record) {
						if (mainGrid && store.getCount() > 0)
							mainGrid.getSelectionModel().selectRow(0);
						else {
							detail.getForm().reset();
							subStore.clearData();
						}

					});

			// 声明 ALERT_LEVER 为全局变量，用于事件等级查询
			function loadAlertEventStore(b) {
//				terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(false);
//			   	consTypeCombox.getEl().up('.x-form-item').setDisplayed(true);
				if (b) {
					if (b.getId() == 'alertButton1')
						ALERT_LEVEL = '01';
					if (b.getId() == 'alertButton2')
						ALERT_LEVEL = '02';
					if (b.getId() == 'alertButton3')
						ALERT_LEVEL = '03';
				}
				if(ALERT_LEVEL != '00'){
					
				alertEventStore.baseParams = {
					alertLevel : ALERT_LEVEL
				};

				// alert(ALERT_LEVEL);
				alertEventStore.load();				
				}
			};

//			renderModel(panel, '事件报警查询');
			if(typeof(alertWindowParam) != 'undefined'){
				if(alertWindowParam == 'ok'){
					getAlertMessage();
				}
			}else if(typeof(deviceMonitorParam) != 'undefined'&&deviceMonitorParam=='deviceMonitor'){
				DeviceMonitorAlertMessage();
				deviceMonitorParam = null;
			}else{
			 	loadAlertEventStore();
			}
//			if(Ext.getCmp('alertButton1')!=null)
//				Ext.getCmp('alertButton1').on('click', loadAlertEventStore);
//			if(Ext.getCmp('alertButton2')!=null)
//				Ext.getCmp('alertButton2').on('click', loadAlertEventStore);
//			if(Ext.getCmp('alertButton3')!=null)
//				Ext.getCmp('alertButton3').on('click', loadAlertEventStore);
			
			//重点用户监测数据迁移时调用函数
			function searchVipMoitorData() {
				vipConsMonitorData_Flag1 = false;
				nodeTextField.setValue(vipConsMonitorData_consName1);
				hideNodeTypeTextField.setValue('usr');
				hideNodeTextField.setValue(vipConsMonitorData_consNo1);
				alertEventStore.baseParams={
						type : hideNodeTypeTextField.getValue(),
						nodeid : hideNodeTextField.getValue(),
						orgType : hideOrgTypeTextField.getValue(),
						startDate : startTimeField.getValue().format('Y-m-d'),
						endDate : endTimeField.getValue().format('Y-m-d')
				};
					
				alertEventStore.load({
						params : {
							start : 0,
							limit : DEFAULT_PAGE_SIZE
						}
				});
			}
	function searchByRowID() {
		getEventByRowid(window.rowId);
		alertEventStore.on('datachanged', function() {
			if(nodeTextField.getValue() == ""){
				var rec = alertEventStore.getAt(0);
				if (rec != null){
					nodeTextField.setValue(rec.get("consName"));
					hideNodeTypeTextField.setValue("usr");
					consTypeCombox.setValue("");
					consTypeCombox.setDisabled(true);
					hideNodeTextField.setValue(rec.get("consNo"));

					terminalAddrStore.baseParams = {
		   				consNo : rec.get("consNo")
		   			};
		   			terminalAddrCombox.setValue("");
		   			consTypeCombox.setValue("");
		   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
		   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);

		   			window.rowId = "";
				}
			}
		})		
	}
			//added by 姜炜超 for 重点户监测数据------------start
//			if((typeof(vipConsMonitorData_Flag1) != 'undefined') && vipConsMonitorData_Flag1){
//				vipConsMonitorData_Flag1 = false;
//				nodeTextField.setValue(vipConsMonitorData_consName1);
//				hideNodeTypeTextField.setValue('usr');
//				hideNodeTextField.setValue(vipConsMonitorData_consNo1);
//				alertEventStore.baseParams={
//						type : hideNodeTypeTextField.getValue(),
//						nodeid : hideNodeTextField.getValue(),
//						orgType : hideOrgTypeTextField.getValue(),
//						startDate : startTimeField.getValue().format('Y-m-d'),
//						endDate : endTimeField.getValue().format('Y-m-d')
//				};
//					
//				alertEventStore.load({
//						params : {
//							start : 0,
//							limit : DEFAULT_PAGE_SIZE
//						}
//				});
//			}
			//added by 姜炜超 for 重点户监测数据------------end

			//页面非激活状态下调用方法
//			if (!Ext.isEmpty(window.rowId)) {
//				getEventByRowid(window.rowId);
//				alertEventStore.on('datachanged', function() {
//					if(nodeTextField.getValue() == ""){
//						var rec = alertEventStore.getAt(0);
//						if (rec != null){
//							nodeTextField.setValue(rec.get("consName"));
//							hideNodeTypeTextField.setValue("usr");
//							consTypeCombox.setValue("");
//							consTypeCombox.setDisabled(true);
//							hideNodeTextField.setValue(rec.get("consNo"));
//	
//							terminalAddrStore.baseParams = {
//				   				consNo : rec.get("consNo")
//				   			};
//				   			terminalAddrCombox.setValue("");
//				   			consTypeCombox.setValue("");
//				   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
//				   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
//	
//				   			window.rowId = "";
//						}
//					}
//				})
//			}
			
//			Ext.getCmp('事件报警查询').on('activate', function() {
//				
//				if(typeof(window.rowId) != 'undefined' && !Ext.isEmpty(window.rowId)){
//					getEventByRowid(window.rowId);
//					alertEventStore.on('datachanged', function() {
//						var rec = alertEventStore.getAt(0);
//						nodeTextField.setValue(rec.get("consName"));
//						hideNodeTypeTextField.setValue("usr");
//						consTypeCombox.setValue("");
//						consTypeCombox.setDisabled(true);
//						hideNodeTextField.setValue(rec.get("consNo"));
//
//						terminalAddrCombox.setValue("");
//			   			consTypeCombox.setValue("");
//			   			terminalAddrCombox.getEl().up('.x-form-item').setDisplayed(true);
//			   			consTypeCombox.getEl().up('.x-form-item').setDisplayed(false);
//						
//						window.rowId = "";
//					})
//				}
//				
//				if (typeof(deviceMonitorParam) != 'undefined' && !Ext.isEmpty(deviceMonitorParam)) {
//					DeviceMonitorAlertMessage();
//					deviceMonitorParam = null;
//				}else if(typeof(alertWindowParam) != 'undefined' && alertWindowParam == 'ok'){
//					getAlertMessage();
//					alertWindowParam = null;
//				}else{
//					loadAlertEventStore();
//				}
//				//added by 姜炜超 for 重点户监测数据------------start
//				if((typeof(vipConsMonitorData_Flag1) != 'undefined') && vipConsMonitorData_Flag1){
//					vipConsMonitorData_Flag1 = false;
//					nodeTextField.setValue(vipConsMonitorData_consName1);
//					hideNodeTypeTextField.setValue('usr');
//					hideNodeTextField.setValue(vipConsMonitorData_consNo1);
//					alertEventStore.baseParams={
//							type : hideNodeTypeTextField.getValue(),
//							nodeid : hideNodeTextField.getValue(),
//							orgType : hideOrgTypeTextField.getValue(),
//							startDate : startTimeField.getValue().format('Y-m-d'),
//							endDate : endTimeField.getValue().format('Y-m-d')
//					};
//						
//					alertEventStore.load({
//							params : {
//								start : 0,
//								limit : DEFAULT_PAGE_SIZE
//							}
//					});
//				}
//				//added by 姜炜超 for 重点户监测数据------------end
//			});
//		});
	function getEventByRowid(rowid){
		alertEventStore.load({
								params : {
									fromId : rowid,
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
	}
			
	function f_attention(){
		var record = mainGrid.getSelectionModel().getSelected();
		if (record != null){
			
			var flowStatusCode = record.get('flowStatusCode');
			if(flowStatusCode == '00'){
				Ext.MessageBox.alert("提示","此事件报警已经被关注！");
				return ;
			}else{
				Ext.getBody().mask("正在处理...");
				Ext.Ajax.request({
					url : './sysman/alertevent!attentionEvent.action',
					params : {
						fromId : record.get('rd'),
						eventCode : record.get('eventNo'),
						eventName : record.get('eventName')
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var isError = result.success;
						Ext.getBody().unmask();
						if(!isError){
							Ext.MessageBox.alert("提示","关注失败！");
						}else{
//							Ext.MessageBox.alert("提示", "关注成功！");
							record.set('flowStatusCode', '00');
							record.commit();
						}
					}
				});
				
			}
			
		}
	}
	
	function f_dealWith(){
		var record = mainGrid.getSelectionModel().getSelected();
		if (record != null){
			
			var flowStatusCode = record.get('flowStatusCode');
			if(flowStatusCode == '00'){
				window.fromId = record.get('rd');
				window.eventType = '2';
				openTab("现场设备异常", "./runMan/abnormalHandle/deviceAbnormal.jsp",
					false, "deviceAbnormal");
//				Ext.MessageBox.alert("提示","此事件报警已经被处理！");
				return ;
			}else{
				Ext.getBody().mask("正在处理...");
				Ext.Ajax.request({
					url : './sysman/alertevent!attentionEvent.action',
					params : {
						fromId : record.get('rd'),
						eventCode : record.get('eventNo'),
						eventName : record.get('eventName')
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var isError = result.success;
						Ext.getBody().unmask();
						if(!isError){
							Ext.MessageBox.alert("提示","处理失败！");
						}else{
//							Ext.MessageBox.alert("提示", "关注成功！");
							record.set('flowStatusCode', '00');
							record.commit();
							window.fromId = record.get('rd');
							window.eventType = '2';
							openTab("现场设备异常", "./runMan/abnormalHandle/deviceAbnormal.jsp",
								false, "deviceAbnormal");
						}
					}
				});
				
			}
			
		}
	}
	/**
	 * 电能表异常查询
	 * @return
	 */
	function searchAmmeterH(){
			nodeTextField.setValue(staticAmmeterRunStatusOrgName);
			hideNodeTypeTextField.setValue('org');
			hideOrgTypeTextField.setValue(staticAmmeterRunStatusOrgType);
			hideNodeTextField.setValue(staticAmmeterRunStatusOrgNo);
			//事件类型(视图)
			alertEventTypeCombox.setValue("01" + staticAmmeterRunStatusFlag);
			startTimeField.setValue(staticAmmeterRunStatusStartDate);
			endTimeField.setValue(staticAmmeterRunStatusEndDate);
			alertEventStore.baseParams={
					type : hideNodeTypeTextField.getValue(),
					nodeid : hideNodeTextField.getValue(),
					orgType : hideOrgTypeTextField.getValue(),
					eventType : alertEventTypeCombox.getValue(),
					startDate : startTimeField.getValue().format('Y-m-d'),
					endDate : endTimeField.getValue().add(Date.DAY, 1).format('Y-m-d')
			};
				
			alertEventStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
			});
		}
	