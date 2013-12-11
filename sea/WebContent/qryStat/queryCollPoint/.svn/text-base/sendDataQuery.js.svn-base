var fieldConsNo = '';//查询实时抄表取得cons_no
var fieldassetNo = '';//取得表记资产号
sendDataQueryConId = '';
sendDataQueryAddr = '';
		 rootString = 'SDQListB';
		 
sendDataQueryFlag = 'everyday';
 MR_SECT_NO = '';
if (typeof(consIdNew) != 'undefined') {
	sendDataQueryConId = consIdNew;
}
sendDataQueryConsNO = '';
if (typeof(consNoforCCust) != 'undefined') {
	if (consNoforCCust.toString().substring(0, 1) == 'T') {
		consNoforCCust = consNoforCCust.substring(1, 20);
	}
	sendDataQueryConsNO = consNoforCCust;
}

DateStart = '';
DateEnd = '';
sendDQType = "usr";
sendTypeFlag = "";
if (typeof(sendTypeFlagSend) != 'undefined') {
	sendTypeFlag = sendTypeFlagSend;
}
sendDQOrgType = "";

Ext.onReady(function() {
	// 点击左边树事件
	var treels = new LeftTreeListener({
		modelName : '抄表数据查询',
		processClick : function(p, node, e) {
			treeclick(node, e);
		},
		processUserGridSelect : function(cm, row, record) {
			sendDataQueryStore1.removeAll();
			sendDataQueryStore2.removeAll();
			sendDataQueryStore222.removeAll();
			sendDQType = 'usr';
			Ext.getCmp("sendDataQueryConsNO").setValue(record.get('consNo'));
			Ext.getCmp('sendDataQueryButton').setDisabled(false);
			sendDataQueryConsNO = record.get('consNo');
			sendDataQueryConId = record.get('consId');
			sendDataQueryAddr = record.get('tmnlAssetNo');
			if (sendDataQueryConsNO.toString().substring(0, 1) == 'T') {
				sendTypeFlag = '2';
				sendDQType = sendDQType + '2';
			} else {
				sendTypeFlag = '1';
				sendDQType = sendDQType + '1';
			}
			if (sendDataQuerytab.getActiveTab().getId() == 'sendDataQuerytab1') {
				getSDQMessage();
			} else if (sendDataQuerytab.getActiveTab().getId() == 'sendDataQuerytab2') {
				getSDQMessageB();
			} else if (sendDataQuerytab.getActiveTab().getId() == 'sendDataQuerytab3') {
				getSDQMessageBB();
			}else if (sendDataQuerytab.getActiveTab().getId() == 'sendDataQuerytab4') {
				getSDQMessageDay();
			}			// getSDQMessage();
		}
	});
	function treeclick(node, e) {
		var obj = node.attributes.attributes;
		var type = node.attributes.type;
	//alert(node.attributes.attributes.orgType);
		if (type == 'usr') {
			Ext.getCmp('sendDataQueryButton').setDisabled(false);
			// Ext.getCmp('sendDataQueryDateStart').setValue(new
			// Date().add(Date.DAY, -8));
			// Ext.getCmp('sendDataQueryDateEnd').setValue(new
			// Date().add(Date.DAY, -1));
			sendTypeFlag = node.attributes.attributes.consType;
			sendDQType = type;
		}

		if (node.isLeaf()) {
			Ext.getCmp('sendDataQueryButton').setDisabled(false);
			// Ext.getCmp('sendDataQueryDateStart').setValue(new
			// Date().add(Date.DAY, -8));
			// Ext.getCmp('sendDataQueryDateEnd').setValue(new
			// Date().add(Date.DAY, -1));
			Ext.getCmp("sendDataQueryConsNO").setValue(obj.consNo);
			if (sendTypeFlag == '1') {
				sendDataQueryConsNO = obj.consNo;
				sendDataQueryAddr = obj.terminalId;
				sendDQType = sendDQType + '1';
			}
			if (sendTypeFlag == '2') {
				sendDataQueryConId = obj.consId;
				sendDataQueryAddr = obj.terminalId;
				sendDQType = sendDQType + '2';
				sendDataQueryConsNO = obj.consNo;
			}
			// alert(sendDataQueryConId);
			DateStart = Ext.getCmp("sendDataQueryDateStart").getValue();
			DateEnd = Ext.getCmp("sendDataQueryDateEnd").getValue();
			sendDataQueryStore1.removeAll();
			sendDataQueryStore2.removeAll();
			sendDataQueryStore222.removeAll();
		} else {
			if (!node.isLeaf()) {
				Ext.getCmp('sendDataQueryButton').setDisabled(true);
				Ext.getCmp('sendDataQueryDateStart').setValue(new Date().add(
						Date.DAY, -1));
				Ext.getCmp('sendDataQueryDateEnd').setValue(new Date().add(
						Date.DAY, -1));
				// alert(node.attributes.attributes.orgType);
				if (node.attributes.type == 'org'
						&& node.attributes.attributes.orgType != '02'
						) {
					sendDataQueryConsNO = obj.orgNo;
					sendDQOrgType = obj.orgType;
					sendDQType = node.attributes.type;
					Ext.getCmp("sendDataQueryConsNO").setValue(node.text);
				}
				if (node.attributes.type == 'sub') {
					sendDataQueryConsNO = obj.subsId;
					sendDQType = node.attributes.type;
					Ext.getCmp("sendDataQueryConsNO").setValue(node.text);
				}
				if (node.attributes.type == 'line') {
					sendDataQueryConsNO = obj.lineId;
					sendDQType = node.attributes.type;
					Ext.getCmp("sendDataQueryConsNO").setValue(node.text);
				}
				if (node.attributes.type == 'cgp') {
					sendDataQueryConsNO = obj.groupNo;
					sendDQType = node.attributes.type;
//					alert(sendDataQueryConsNO);
//					alert(sendDQType);
					Ext.getCmp("sendDataQueryConsNO").setValue(node.text);
				}
				if (node.attributes.type == 'ugp') {
					sendDataQueryConsNO = obj.groupNo;
					sendDQType = node.attributes.type;
					Ext.getCmp("sendDataQueryConsNO").setValue(node.text);
				}
			} else {
				return true;
			}
		}
	}
	// 公用查询函数
	function getSDQMessage() {
		sendDataQueryStore1.removeAll();
			sendDataQueryStore2.removeAll();
		DateStart = Ext.getCmp("sendDataQueryDateStart").getValue()
				.format('Y-m-d');
				var sendDataQueryFail = Ext.getCmp("sendDataQueryFail").getValue();
		DateEnd = Ext.getCmp("sendDataQueryDateEnd").getValue().format('Y-m-d');
		var sendDataQueryConsNONUM = Ext.getCmp("sendDataQueryConsNO")
				.getValue();
				MR_SECT_NO = Ext.getCmp("MR_SECT_NO")
				.getValue();
		if (!isNaN(sendDataQueryConsNONUM)) {
			sendDataQueryConsNO = sendDataQueryConsNONUM;
		}
		sendDataQueryStore1.baseParams = {
			MR_SECT_NO:MR_SECT_NO,
			sendDataQueryFail:sendDataQueryFail,
			sendDataQueryFlag:sendDataQueryFlag,
			sendDQOrgType : sendDQOrgType,
			sendDQType : sendDQType,
			CONS_NO : sendDataQueryConsNO,
			addr : sendDataQueryAddr,
			TG_ID : '',
			DateStart : DateStart,
			DateEnd : DateEnd
		};
		sendDataQueryStore1.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});

	};
	function getSDQMessageB() {
		sendDataQueryStore1.removeAll();
		sendDataQueryStore2.removeAll();
		sendDataQueryStore222.removeAll();
		var sendDataQueryFail = Ext.getCmp("sendDataQueryFail").getValue();
		// sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
		DateStart = Ext.getCmp("sendDataQueryDateStart").getValue()
				.format('Y-m-d');
		DateEnd = Ext.getCmp("sendDataQueryDateEnd").getValue().format('Y-m-d');
		var sendDataQueryConsNONUM = Ext.getCmp("sendDataQueryConsNO")
				.getValue();
				MR_SECT_NO = Ext.getCmp("MR_SECT_NO")
				.getValue();
		if (!isNaN(sendDataQueryConsNONUM)) {
			sendDataQueryConsNO = sendDataQueryConsNONUM;
		}
		sendDataQueryStore2.baseParams = {
			MR_SECT_NO:MR_SECT_NO,
			sendDataQueryFail:sendDataQueryFail,
			sendDataQueryFlag:sendDataQueryFlag,
			sendDQOrgType : sendDQOrgType,
			sendDQType : sendDQType,
			CONS_NO : sendDataQueryConsNO,
			addr : sendDataQueryAddr,
			TG_ID : sendDataQueryConId,
			DateStart : DateStart,
			DateEnd : DateEnd
		};
		sendDataQueryStore2.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});			
		};
	
	function getSDQMessageBB() {
		sendDataQueryStore1.removeAll();
		sendDataQueryStore2.removeAll();
		sendDataQueryStore222.removeAll();
		var sendDataQueryFail = Ext.getCmp("sendDataQueryFail").getValue();
		// sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
		DateStart = Ext.getCmp("sendDataQueryDateStart").getValue()
				.format('Y-m-d');
		DateEnd = Ext.getCmp("sendDataQueryDateEnd").getValue().format('Y-m-d');
		var sendDataQueryConsNONUM = Ext.getCmp("sendDataQueryConsNO")
				.getValue();
				MR_SECT_NO = Ext.getCmp("MR_SECT_NO")
				.getValue();
		if (!isNaN(sendDataQueryConsNONUM)) {
			sendDataQueryConsNO = sendDataQueryConsNONUM;
		}				
				sendDataQueryStore222.baseParams = {
			MR_SECT_NO:MR_SECT_NO,
			sendDataQueryFail:sendDataQueryFail,
			sendDataQueryFlag:sendDataQueryFlag,
			sendDQOrgType : sendDQOrgType,
			sendDQType : sendDQType,
			CONS_NO : sendDataQueryConsNO,
			addr : sendDataQueryAddr,
			TG_ID : sendDataQueryConId,
			DateStart : DateStart,
			DateEnd : DateEnd
		};
		sendDataQueryStore222.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};
	
	//查询实时抄表数据----------------------------------------------------------------------------------
	function getSDQMessageDay() {	
		sendDataQueryStoreDay.removeAll();
		var sendDataQueryFail = Ext.getCmp("sendDataQueryFail").getValue();
		// sendDataQueryConsNO = Ext.getCmp("sendDataQueryConsNO").getValue();
		DateStart = Ext.getCmp("sendDataQueryDateStart").getValue()
				.format('Y-m-d');
		DateEnd = Ext.getCmp("sendDataQueryDateEnd").getValue().format('Y-m-d');
		var sendDataQueryConsNONUM = Ext.getCmp("sendDataQueryConsNO")
				.getValue();
				MR_SECT_NO = Ext.getCmp("MR_SECT_NO")
				.getValue();
		if (!isNaN(sendDataQueryConsNONUM)) {
			sendDataQueryConsNO = sendDataQueryConsNONUM;
		}				
				sendDataQueryStoreDay.baseParams = {
			 fieldConsNo:fieldConsNo,
			fieldassetNo:fieldassetNo,
			MR_SECT_NO:MR_SECT_NO,
			sendDataQueryFail:sendDataQueryFail,
			sendDataQueryFlag:sendDataQueryFlag,
			sendDQOrgType : sendDQOrgType,
			sendDQType : sendDQType,
			CONS_NO : sendDataQueryConsNO,
			addr : sendDataQueryAddr,
			TG_ID : sendDataQueryConId,
			DateStart : DateStart,
			DateEnd : DateEnd
		};
		sendDataQueryStoreDay.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
	};

	// -----------------------------------------------------------------
	// 抄表数据查询panel1
		var senddata = [['成功'], ['失败']];
	var sendstore = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(senddata), // 数据源
				reader : new Ext.data.ArrayReader({}, [ // 如何解析
						{
									name : 'name'
								}])
			});
	sendstore.load();
	var sendDataQuerycombo = new Ext.form.ComboBox({
				store : sendstore,
				xtype : 'combo',
				id : 'sendDataQueryFail',
				fieldLabel : '抄表',
				width : 80,
				labelStyle : "text-align:right;width:30%;",
				valueField : 'name',
				editable : false,
				resizable : true,// 拖动柄缩放
				triggerAction : 'all',
				labelSeparator : ' ',
				forceSelection : true,
				selectOnFocus : true,// 值为 ture 时表示字段获取焦点时自动选择字段既有文本(默认为
										// false)
				displayField : 'name',// 组合框用以展示的数据的字段名（如果mode='remote'则默认为
											// undefined，如果mode = 'local' 则默认为
											// 'text'）
				mode : 'local',// 如果ComboBox读取本地数据则将值设为'local'（默认为 'remote'
								// 表示从服务器读取数据）
				value : '成功'
			});
			sendDataQuerycombo.on('select', function(combox) {
				var value = combox.getValue();
				if(value == '失败'){
					sendDataQuerytab.setActiveTab(3);
				}
			});

	var dataPanel = new Ext.Panel({
				width : 500,
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
							columnWidth : .275,
							layout : 'form',
							border : false,
							labelAlign : 'right',
							labelWidth : 20,
							width : '120',
							items : [{
										xtype : "datefield",
										format : "Y-m-d",
										border : false,
										id : 'sendDataQueryDateStart',
										 editable:false,
										fieldLabel : "从",
										value : new Date().add(Date.DAY, -7)
									}]

						}, {
							columnWidth : .275,
							layout : 'form',
							border : false,
							labelAlign : 'right',
							labelWidth : 20,
							width : '120',
							items : [{
										xtype : "datefield",
										format : "Y-m-d",
										id : 'sendDataQueryDateEnd',
										 editable:false,
										fieldLabel : "到",
										border : false,
										value : new Date().add(Date.DAY, 0)
									}]

						},{
									columnWidth : .175,
									layout : 'form',
									border : false,
									width : '150',
									labelWidth : 1,
									items : [{
												xtype : 'checkbox',
												id : 'sendDataQueryDateOnly',
												boxLabel : '逐日显示',
												checked:true,
												handler : function(e, checked) {
								if (checked) {
									sendDataQueryFlag = 'everyday';
								
									//alert(sendDataQueryFlag);
								}else{
										sendDataQueryFlag = 'onlyday';
									//alert(sendDataQueryFlag);
								}
							}
											}]
								}]
			});

	var sendDataQuery1 = new Ext.Panel({
		plain : true,
		region : 'north',
		border : false,
		layout : 'fit',
		height : 65,
		items : [{
			baseCls : "x-plain",
			layout : "column",
			style : "padding-top:5px",
			border : false,
			items : [{
						columnWidth : .3,
						layout : 'form',
						border : false,
						labelWidth : 50,
						items : [{
							xtype : 'textfield',
							id : 'sendDataQueryConsNO',
							fieldLabel : "节点号<font color='red'>*</font>",
							labelStyle : "text-align:right;width:50px",
							readOnly : true,
							//emptyText:'请选择区域及以下节点',
							labelSeparator : "",
							width : 180
								// ,
								// validator : function(val) {
								// if (Ext.isEmpty(val))
								// return false;
								// else
								// return true;
								// }
							}]

					},{
						columnWidth : .25,
						layout : 'form',
						border : false,
						labelWidth :60,
						items : [{
							xtype : 'textfield',
							id : 'MR_SECT_NO',
							fieldLabel : "抄表段号<font color='red'>*</font>",
							labelStyle : "text-align:right;width:60px",
							//readOnly : true,
							labelSeparator : "",
							width : 120
								// ,
								// validator : function(val) {
								// if (Ext.isEmpty(val))
								// return false;
								// else
								// return true;
								// }
							}]

					},{
									columnWidth : .25,
									layout : 'form',
									border : false,
									labelWidth : 30,
									items : [sendDataQuerycombo]
								}, {
						columnWidth : .1,
						layout : "form",
						border : false,
						defaultType : "button",
						baseCls : "x-plain",
						items : [{
							text : "查询",
							width : 50,
							listeners : {
								"click" : function() {
									//alert(sendDataQueryFlag);
									var sendDataQueryConsNO = Ext
											.getCmp("sendDataQueryConsNO");
									if (!sendDataQueryConsNO.isValid(true)) {
										sendDataQueryConsNO.markInvalid('不能为空');
										return true;
									};
									if (sendDataQuerytab.getActiveTab().getId() == 'sendDataQuerytab1') {
										getSDQMessage();
									} else if (sendDataQuerytab.getActiveTab()
											.getId() == 'sendDataQuerytab2') {
										getSDQMessageB();
									}else if (sendDataQuerytab.getActiveTab()
											.getId() == 'sendDataQuerytab3') {
										getSDQMessageBB();
									}else if (sendDataQuerytab.getActiveTab()
											.getId() == 'sendDataQuerytab4') {
										getSDQMessageDay();
									}
									// getSDQMessage();
								}
							}
						}]

					}, {
						columnWidth : .1,
						layout : "form",
						border : false,
						defaultType : "button",
						baseCls : "x-plain",

						items : [{
									id : 'sendDataQueryButton',
									text : "电能量分析",
									disabled : false,
									width : 50,
									listeners : {
										"click" : function() {
											sdqWindowToTableShow();
										}
									}
								}]

					},{
						columnWidth : 1,
						layout : 'column',
						border : false,
						items : [dataPanel]

					}]
		}]
	});

	var sendDataQueryStore1 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/sendDataQueryAction!querySendDataQuery.action'
				}),
		reader : new Ext.data.JsonReader({
					root : 'SDQList',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'consNo'
						}, {
							name : 'consId'
						}, {
							name : 'consName'
						}, {
							name : 'dataDate'
						}, {
							name : 'papRE'
						}, {
							name : 'papR1E'
						}, {
							name : 'papR2E'
						}, {
							name : 'papR3E'
						}, {
							name : 'papR4E'
						}, {
							name : 'assetNo'
						}, {
							name : 'terminalAddr'
						}, {
							name : 'mrSectNo'
						}, {
							name : 'tFactor'
						}])
	});

	var groupCM1 = new Ext.grid.ColumnModel([{
		header : '供电单位',
		width:160,
		// menuDisabled : true,
		dataIndex : 'orgName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '用户编码',
		// menuDisabled : true,
		dataIndex : 'consNo',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			return "<a href='javascript:' onclick=''>" + val + "</a>";
		}
	}, {
		header : '用户ID',
		// menuDisabled : true,
		dataIndex : 'consId',
		sortable : true,
		align : 'center',
		hidden:true
	}, {
		header : '用户名称',
		// menuDisabled : true,
		width : 120,
		dataIndex : 'consName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	},{
		header : '表计资产',
		width : 180,
		// menuDisabled : true,
		dataIndex : 'assetNo',
		sortable : true,
		align : 'center'
	}, {
		header : '对应终端',
		// menuDisabled : true,
		dataIndex : 'terminalAddr',
		sortable : true,
		align : 'center'
	}, {
		header : '抄表日期',
		// menuDisabled : true,
		dataIndex : 'dataDate',
		sortable : true,
		align : 'center'
	}, {
		header : '正向有功费率总',
		// menuDisabled : true,
		dataIndex : 'papRE',
		sortable : true,
		width : 120,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="正向有功费率总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-尖',
		// menuDisabled : true,
		dataIndex : 'papR1E',
		sortable : true,
		width : 80,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-峰',
		// menuDisabled : true,
		dataIndex : 'papR2E',
		sortable : true,
		width : 80,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-平',
		// menuDisabled : true,
		dataIndex : 'papR3E',
		sortable : true,
		width : 80,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-谷',
		// menuDisabled : true,
		dataIndex : 'papR4E',
		sortable : true,
		width : 80,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '抄表段编号',
		// menuDisabled : true,
		width : 80,
		dataIndex : 'mrSectNo',
		sortable : true,
		align : 'center'
	}, {
		header : '综合倍率',
		// menuDisabled : true,
		width : 80,
		dataIndex : 'tFactor',
		sortable : true,
		align : 'center'
	}]);

	var sendDataQuery21 = new Ext.grid.GridPanel({
				height : 600,
				autoScroll : true,
				stripeRows : true,
				viewConfig : {
					forceFit : false
				},
				cm : groupCM1,
				ds : sendDataQueryStore1,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : sendDataQueryStore1
						})
			});
			
	sendDataQuery21.addListener('cellclick', sendDataQuery21click);
function sendDataQuery21click(sendDataQuery21, rowIndex, columnIndex, e){			
 if(rowIndex >= 0 && columnIndex==1){
    	  var record = sendDataQuery21.getStore().getAt(rowIndex);  // Get the Record
    var cellconsId = sendDataQuery21.getColumnModel().getDataIndex(columnIndex+1); // Get field name
    var cellconsNo= sendDataQuery21.getColumnModel().getDataIndex(columnIndex); // Get field name
     messageconsId = record.get(cellconsId);
     messageconsNo = record.get(cellconsNo);
		privateIDNew = messageconsId;
		privateTerminalConvalue = messageconsNo;
		sdqWindowShow2(privateTerminalConvalue);
		
    }}		

	var sendDataQueryStore2 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/sendDataQueryAction!querySendDataQueryB.action'
				}),
		reader : new Ext.data.JsonReader({
					root:'SDQListB',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'mrSectNo'
						},{
							name : 'consNo'
						},{
							name : 'consId'
						}, {
							name : 'prpR'
						}, {
							name : 'consName'
						}, {
							name : 'dataDate'
						}, {
							name : 'papR'
						}, {
							name : 'papR1'
						}, {
							name : 'papR2'
						}, {
							name : 'papR3'
						}, {
							name : 'papR4'
						}, {
							name : 'rapR'
						}, {
							name : 'rapR1'
						}, {
							name : 'rapR2'
						}, {
							name : 'rapR3'
						}, {
							name : 'rapR4'
						}, {
							name : 'rp1R'
						}, {
							name : 'rp4R'
						}, {
							name : 'tFactor'
						}, {
							name : 'assetNo'
						}, {
							name : 'terminalAddr'
						}, {
							name : 'rrpR'
						}, {
							name : 'mpSn'
						}])
	});

	var groupCM2 = new Ext.grid.ColumnModel([{
		header : '供电单位',
		width : 160,
		dataIndex : 'orgName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '用户编码',
		dataIndex : 'consNo',
		sortable : true,
		align : 'center',
		renderer : function(val) {
			return "<a href='javascript:' onclick=''>" + val + "</a>";
		}
	}, {
		header : '用户Id',
		dataIndex : 'consId',
		sortable : true,
		align : 'center',
		hidden:true
	}, {
		header : '用户名称',
		width : 120,
		dataIndex : 'consName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	},{
		header : '测量点号',
		dataIndex : 'mpSn',
		sortable : true,
		align : 'center'
	}, {
		header : '抄表段编号',
		// menuDisabled : true,
		width : 80,
		dataIndex : 'mrSectNo',
		sortable : true,
		align : 'center'
	}, {
		header : '表计资产',
		width : 180,
		dataIndex : 'assetNo',
		sortable : true,
		align : 'center'
	}, {
		header : '对应终端',
		dataIndex : 'terminalAddr',
		sortable : true,
		align : 'right'
	}, {
		header : '抄表日期',
		dataIndex : 'dataDate',
		sortable : true,
		align : 'right'
	}, {
		header : '正向有功总',
		width : 80,
		dataIndex : 'papR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="正向有功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-尖',
		width : 80,
		dataIndex : 'papR1',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-峰',
		width : 80,
		dataIndex : 'papR2',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-平',
		width : 80,
		dataIndex : 'papR3',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-谷',
		width : 80,
		dataIndex : 'papR4',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '反向无功总',
		width : 80,
		dataIndex : 'rrpR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="反向无功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	},{
		header : '正向无功总',
		width : 80,
		dataIndex : 'prpR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="正向无功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '一象限无功',
		hidden : true,
		width : 80,
		dataIndex : 'rp1R',
		sortable : true,
		align : 'centet',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="一象限无功" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '四象限无功',
		hidden : true,
		width : 80,
		dataIndex : 'rp4R',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="四象限无功" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '反向有功总',
		hidden : true,
		width : 80,
		dataIndex : 'rapR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="反向有功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-尖',
		hidden : true,
		width : 80,
		dataIndex : 'rapR1',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-峰',
		hidden : true,
		width : 80,
		dataIndex : 'rapR2',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-平',
		hidden : true,
		width : 80,
		dataIndex : 'rapR3',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-谷',
		hidden : true,
		width : 80,
		dataIndex : 'rapR4',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '综合倍率',
		width : 80,
		dataIndex : 'tFactor',
		sortable : true,
		align : 'left'
	}, {
		header : '实时抄表',
		width : 80,
		dataIndex : '',
		sortable : true,
		align : 'left',
		renderer:function(val){
				return '<a href="javascript:" onclick="">查看</a>';
			}
	}]);
	
//-------------------------------------------------------------------------------------------------------------------------抄表失败
var sendDataQueryStore222 = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/sendDataQueryAction!querySendDataQueryB.action'
				}),
		reader : new Ext.data.JsonReader({
					root:'SDQListBFail',
					totalProperty : 'totalCount'
				}, [{
							name : 'assetNo'
						}, {
							name : 'cisAssetNo'
						},{
							name : 'consName'
						}, {
							name : 'consNo'
						}, {
							name : 'consId'
						}, {
							name : 'elecAddr'
						}, {
							name : 'mrSectNo'
						}, {
							name : 'orgName'
						}, {
							name : 'runCap'
						}, {
							name : 'statDate'
						}, {
							name : 'tFactor'
						}, {
							name : 'terminalAddr'
						}, {
							name : 'tmnlAssetNo'
						}])
	});

	var groupCM222= new Ext.grid.ColumnModel([{
		header : '供电单位',
		width : 150,
		dataIndex : 'orgName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '用户编码',
		dataIndex : 'consNo',
		sortable : true,
		align : 'center'
//		,
//		renderer : function(val) {
//			return "<a href='javascript:' onclick=''>" + val + "</a>";
//		}
	}, {
		header : '用户Id',
		dataIndex : 'consId',
		sortable : true,
		align : 'center',
		hidden:true
	}, {
		header : '用户名称',
		width : 120,
		dataIndex : 'consName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '抄表段编号',
		// menuDisabled : true,
		width : 80,
		dataIndex : 'mrSectNo',
		sortable : true,
		align : 'center'
	}, {
		header : '用电地址',
		// menuDisabled : true,
		width : 120,
		dataIndex : 'elecAddr',
		sortable : true,
		align : 'center'
	},
//	{
//		header : '终端资产号',
//		// menuDisabled : true,
//		width : 80,
//		dataIndex : 'cisAssetNo',
//		sortable : true,
//		align : 'center'
//	},
		{
		header : '表计资产',
		width : 180,
		dataIndex : 'assetNo',
		sortable : true,
		align : 'center'
	}, {
		header : '对应终端',
		dataIndex : 'terminalAddr',
		sortable : true,
		align : 'right'
	}, {
		header : '抄表日期',
		dataIndex : 'statDate',
		sortable : true,
		align : 'right'
	}, {
		header : '综合倍率',
		width : 80,
		dataIndex : 'tFactor',
		sortable : true,
		align : 'left'
	}]);
		
	var sendDataQuery22 = new Ext.grid.GridPanel({
				autoScroll : true,
				stripeRows : true,
				height : 600,
				viewConfig : {
				},
				cm : groupCM2,
				ds : sendDataQueryStore2,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : sendDataQueryStore2
						})
			});

			//调用实时抄表查看调用的方法
function DayQuerycellclick(sendDataQuery22, rowIndex, columnIndex, e){
    if(rowIndex >= 0 && columnIndex==21){
    	sendDataQuerytab.setActiveTab(2);
     var record = sendDataQuery22.getStore().getAt(rowIndex);  // Get the Record
     fieldConsNo = sendDataQuery22.getColumnModel().getDataIndex(1);
      fieldassetNo = sendDataQuery22.getColumnModel().getDataIndex(4); 
     fieldConsNo = record.get(fieldConsNo);
     fieldassetNo = record.get(fieldassetNo);
     getSDQMessageDay();
//     alert(fieldConsNo);
 // 	alert(fieldassetNo);
    }
      else if(rowIndex >= 0 && columnIndex==1){
    	  var record = sendDataQuery22.getStore().getAt(rowIndex);  // Get the Record
    var cellconsId = sendDataQuery22.getColumnModel().getDataIndex(columnIndex+1); // Get field name
    var cellconsNo= sendDataQuery22.getColumnModel().getDataIndex(columnIndex); // Get field name
     messageconsId = record.get(cellconsId);
     messageconsNo = record.get(cellconsNo);
		privateIDNew = messageconsId;
		privateTerminalConvalue = messageconsNo;
		sdqWindowShow2(privateTerminalConvalue);
		
    }
};

//	function viewDay(){
//	
//	DayQuerycellclick();
//	//getSDQMessageDay();
//		// 添加实时抄表点击事件
//	}
	
	sendDataQuery22.addListener('cellclick', DayQuerycellclick);
var sendDataQuery222 = new Ext.grid.GridPanel({
				autoScroll : true,
				stripeRows : true,
				height : 600,
				viewConfig : {
					forceFit : false
				},
				cm : groupCM222,
				ds : sendDataQueryStore222,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : sendDataQueryStore222
						})
			});

//--------------------------------------------------------------------------------------------------------------------------------
				var sendDataQueryStoreDay = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'qrystat/sendDataQueryAction!querySendDataQueryDay.action'
				}),
		reader : new Ext.data.JsonReader({
					root:'SDQListDay',
					totalProperty : 'totalCount'
				}, [{
							name : 'orgName'
						}, {
							name : 'mrSectNo'
						},{
							name : 'consNo'
						},{
							name : 'consId'
						}, {
							name : 'prpR'
						}, {
							name : 'consName'
						}, {
							name : 'dataDate',
							type: 'date',
         						dateFormat:'Y-m-d\\TH:i:s' //2009-04-03T00:00:00 
						}, {
							name : 'papR'
						}, {
							name : 'papR1'
						}, {
							name : 'papR2'
						}, {
							name : 'papR3'
						}, {
							name : 'papR4'
						}, {
							name : 'rapR'
						}, {
							name : 'rapR1'
						}, {
							name : 'rapR2'
						}, {
							name : 'rapR3'
						}, {
							name : 'rapR4'
						}, {
							name : 'rp1R'
						}, {
							name : 'rp4R'
						}, {
							name : 'tFactor'
						}, {
							name : 'assetNo'
						}, {
							name : 'terminalAddr'
						}])
	});

	var sendDataQueryStoreDayCM = new Ext.grid.ColumnModel([{
		header : '抄表日期',
		dataIndex : 'dataDate',
		sortable : true,
		width:130,
		align : 'right',
		fomart:'Y-m-d H:i:s',
			renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	},{
		header : '供电单位',
		width : 160,
		dataIndex : 'orgName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="供电单位" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '用户编码',
		dataIndex : 'consNo',
		sortable : true,
		align : 'center'
//		,
//		renderer : function(val) {
//			return "<a href='javascript:' onclick=''>" + val + "</a>";
//		}
	}, {
		header : '用户Id',
		dataIndex : 'consId',
		sortable : true,
		align : 'center',
		hidden:true
	}, {
		header : '用户名称',
		width : 120,
		dataIndex : 'consName',
		sortable : true,
		align : 'center',
		renderer : function(v) {
			var s = "";
			var vlength = v.length;
			var x = 0;
			while (vlength > 0) {
				s += v.substring(x, x + 45) + "<br>";
				x = x + 45;
				vlength = vlength - 45;
			};
			var html = '<span ext:qtitle="用户名称" ext:qtip="' + s + '">' + v
					+ '</span>';
			return html;
		}
	}, {
		header : '抄表段编号',
		// menuDisabled : true,
		width : 80,
		dataIndex : 'mrSectNo',
		sortable : true,
		align : 'center'
	}, {
		header : '表计资产',
		width : 180,
		dataIndex : 'assetNo',
		sortable : true,
		align : 'center'
	}, {
		header : '对应终端',
		dataIndex : 'terminalAddr',
		sortable : true,
		align : 'right'
	}, {
		header : '正向有功总',
		width : 80,
		dataIndex : 'papR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="正向有功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-尖',
		width : 80,
		dataIndex : 'papR1',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-峰',
		width : 80,
		dataIndex : 'papR2',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-平',
		width : 80,
		dataIndex : 'papR3',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-谷',
		width : 80,
		dataIndex : 'papR4',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '正向无功总',
		width : 80,
		dataIndex : 'prpR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="正向无功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '一象限无功',
		hidden : true,
		width : 80,
		dataIndex : 'rp1R',
		sortable : true,
		align : 'centet',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="一象限无功" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '四象限无功',
		hidden : true,
		width : 80,
		dataIndex : 'rp4R',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="四象限无功" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '反向有功总',
		hidden : true,
		width : 80,
		dataIndex : 'rapR',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="反向有功总" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-尖',
		hidden : true,
		width : 80,
		dataIndex : 'rapR1',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-尖" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-峰',
		hidden : true,
		width : 80,
		dataIndex : 'rapR2',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-峰" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-平',
		hidden : true,
		width : 80,
		dataIndex : 'rapR3',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-平" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '<-谷',
		hidden : true,
		width : 80,
		dataIndex : 'rapR4',
		sortable : true,
		align : 'center',
		renderer:function(val){
				var html = '<div align = "right" ext:qtitle="<-谷" ext:qtip="' + Ext.util.Format.number(val,'0,000.000')
				+ '">' + Ext.util.Format.number(val,'0,000.000') + '</div>';
				return html;
			}
	}, {
		header : '综合倍率',
		width : 80,
		dataIndex : 'tFactor',
		sortable : true,
		align : 'left'
	}]);
	
	
	var sendDataQueryDay = new Ext.grid.GridPanel({
				autoScroll : true,
				stripeRows : true,
				height : 600,
				viewConfig : {
					forceFit : false
				},
				cm : sendDataQueryStoreDayCM,
				ds : sendDataQueryStoreDay,
				bbar : new Ext.ux.MyToolbar({
							enableExpAll : true,
							store : sendDataQueryStoreDay
						})
			});
			
	var sendDataQuerytab = new Ext.TabPanel({
				activeTab : 0,
				region : 'center',
				plain : true,
				defaults : {
					autoScroll : true
				},
				items : [{
							title : '冻结电能示值',
							id : 'sendDataQuerytab2',
							layout : 'fit',
							border : false,
							baseCls : "x-plain",
							height : 350,
							autoScroll : true,
							items : [sendDataQuery22]
						}, {
							title : '日电能量',
							id : 'sendDataQuerytab1',
							layout : 'fit',
							// baseCls : "x-plain",
							border : false,
							baseCls : "x-plain",
							autoScroll : true,
							height : 350,
							items : [sendDataQuery21]
						}, {
							title : '实时抄表',
							id : 'sendDataQuerytab4',
							layout : 'fit',
							// baseCls : "x-plain",
							border : false,
							baseCls : "x-plain",
							autoScroll : true,
							height : 350,
							items : [sendDataQueryDay]
						}, {
							title : '抄表失败',
							id : 'sendDataQuerytab3',
							layout : 'fit',
							// baseCls : "x-plain",
							border : false,
							baseCls : "x-plain",
							autoScroll : true,
							height : 350,
							items : [sendDataQuery222]
						}]
			});
	// 设置顶层的抄表数据查询panel
	var sendDataQuerypanel = new Ext.Panel({
				autoScroll : true,
				layout : 'border',
				// autoHeight : true,
				border : false,
				items : [sendDataQuery1, sendDataQuerytab]
			});
	renderModel(sendDataQuerypanel, '抄表数据查询');
	if (typeof(sendDataQueryConsNOValue) != 'undefined'
			&& typeof(sendDataQueryAddrValue) != 'undefined') {
		if (!Ext.isEmpty(sendDataQueryConsNOValue)) {
			Ext.getCmp("sendDataQueryConsNO")
					.setValue(sendDataQueryConsNOValue);
			sendDataQueryAddrValue = sendDataQueryAddr;
			sendDataQueryConsNOValue = null;
			sendDataQueryAddrValue = null;
			sendDataQueryStore1.removeAll();
			sendDataQueryStore2.removeAll();

		}
	};

	Ext.getCmp('抄表数据查询').on('activate', function() {
		if (typeof(sendDataQueryConsNOValue) != 'undefined'
				&& typeof(sendDataQueryAddrValue) != 'undefined') {
			if (!Ext.isEmpty(sendDataQueryConsNOValue)) {
				Ext.getCmp("sendDataQueryConsNO")
						.setValue(sendDataQueryConsNOValue);
				sendDataQueryAddrValue = sendDataQueryAddr;
				sendDataQueryConsNOValue = null;
				sendDataQueryAddrValue = null;
				sendDataQueryStore1.removeAll();
				sendDataQueryStore2.removeAll();
			}
		}
	});
});

function sdqWindowShow2(privateTerminalConvalue) {
	privateTerminalCon = privateTerminalConvalue;
	privateIDCon = privateIDNew;
	openTab("客户综合查询", "./qryStat/queryCollPoint/consumerInfo.jsp");
}

function sdqWindowToTableShow() {
	if (sendTypeFlag == '1') {
		sdqWindowToTableNoShow();
	} else if (sendTypeFlag == '2') {
		sdqWindowToTableTgShow();
	} else {
		alert('请先从左边树选择相应用户节点!');
	}
};
function sdqWindowToTableNoShow() {
	openTab("冻结电能量分析", "./qryStat/queryCollPoint/sdqWindowToTableNo.jsp");
};
function sdqWindowToTableTgShow() {
	openTab("台区损耗分析", "./qryStat/queryCollPoint/sdqWindowToTableTg.jsp");
}
