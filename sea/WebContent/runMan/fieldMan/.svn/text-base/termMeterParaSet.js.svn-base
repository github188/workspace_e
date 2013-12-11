/** ************** */
// 任务进度条 参见f_taskTime
/** ***稍作特别化改动，如果召测结束关闭等待*** */
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
Ext.onReady(function() {
	Ext.QuickTips.init();// 支持tips提示

	var comStore = {};
	var protocolItemList;
	var handText = new Ext.form.TextField({
			fieldLabel:'用户名',
			allowBlank : false,
			labelSeparator:'',
			name:'nodeName',
			readOnly : true,
			emptyText:'请选择左边用户节点',
			anchor:'100%',
			border:false
		});
	var hideValueText = new Ext.form.TextField({
			labelSeparator:'',
			hidden:true,
			name:'nodeValue',
			anchor:'50%'
		});
	var hideTypeText = new Ext.form.TextField({
			labelSeparator:'',
			hidden:true,
			name:'nodeType',
			anchor:'50%'
		});
		
	handText.on('change',function(tf,nValue,oValue){
		if(isNaN(nValue)){
			Ext.Msg.alert('提示','用户编号必须是数字');
			handText.setValue(oValue);
		}else{
			hideTypeText.setValue('usr');
			hideValueText.setValue(nValue);
			orgType = '';
		}
	});

	
	
	var handRadioForm =new Ext.form.RadioGroup({
			columns : [.5, .5],
			autoWidth : true,
			items : [new Ext.form.Radio({
						boxLabel : 'F10表/交采',
						name : 'dataType',
						readOnly:true,
						checked : true,
						inputValue : '040A',
						listeners : {
							'check' : function(r, c) {
								if (c) {
									var protocolCode = tmnlInfoPanel.getForm().getFieldValues().protocolCode;
									if (protocolCode) {
										//查询F10规约项编码和编码名称
										creatGridAjax(protocolCode + '040A');
									}
								}
							}
						}
					}),
					new Ext.form.Radio({
						boxLabel : 'F11脉冲',
						name : 'dataType',
						readOnly:true,
						inputValue : '040B',
						listeners : {
							'check' : function(r, c) {
								if (c) {
									var protocolCode = tmnlInfoPanel.getForm().getFieldValues().protocolCode;
									if (protocolCode) {
										//查询F11规约项编码和编码名称
										creatGridAjax(protocolCode + '040B');
									}
								}
							}
						}
					})]
		});

	var consFormField1 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '供电单位',
				name : 'orgName',
				readOnly : true
			}), new Ext.form.TextField({
				fieldLabel : '终端地址',
				name : 'terminalAddr',
				readOnly : true
			})]
		})
	var consFormField2 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '用户编号',
				name : 'consNO',
				readOnly : true
			}), new Ext.form.TextField({
				id : 'tmmpsProtocolName',
				fieldLabel : '规约类型',
				name : 'protocolName',
				readOnly : true
			})]
		})
	var consFormField3 = new Ext.Panel({
		layout : 'form',
		columnWidth : 0.33,
		border : false,
		defaults : {
			anchor : '95%'
		},
		labelAlign : 'right',
		items : [new Ext.form.TextField({
				fieldLabel : '用户名称',
				name : 'consName',
				readOnly : true
			}),new Ext.form.TextField({
				name : 'protocolCode',
				readOnly : true,
				hidden : true
			})]
		})
			
	var handQueryTop = new Ext.Panel({
		labelAlign : 'right',
		height: 33,
		region:'north',
		bodyStyle:'padding:5px 0px 5px 0px',
		layout : 'column',
		border : false,
		items : [{
					columnWidth : .26,
					border : false,
					layout:'form',
					labelWidth:50,
					items : [handText]
				},{
	                columnWidth:.01,
	                border:false,
	                layout: 'form',
	                hidden:true,
	                items: [{
						xtype:"label"
						}]
                },{
					columnWidth : .01,
					border : false,
					layout:'form',
					labelWidth:1,
					items : [hideValueText]
				},{
					columnWidth : .01,
					border : false,
					layout:'form',
					labelWidth:1,
					items : [hideTypeText]
				},{
					columnWidth : .2,
					border : false,
					layout:'form',
					labelWidth:1,
					items : [handRadioForm]
				}
				,{
					columnWidth : .2,
					border : false,
					layout:'form',
					labelWidth:1,
					items : [{
						xtype : 'button',
						text : '查询',
						width : 50,
						handler : loadData
					}]
				}
				]
	});

	//获取终端用户信息和规约信息
	function loadData() {
//		if(!handQueryPanel.getForm().isValid()){
//			Ext.Msg.alert('提示','请完整填写查询条件');
//			return true;
//		}
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!queryTmnlUserInfo.action',
				params : {
					// 终端资产编号
					terminalNo : hideValueText.getValue()
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var tmnlInfo = result.tmnlList;
					if (tmnlInfo.length > 0) {
						tmnlInfoPanel.getForm().setValues(tmnlInfo[0]);
						var type = handRadioForm.getValue().inputValue;
						creatGridAjax(tmnlInfo[0].protocolCode + type);
					} else {
						Ext.Msg.alert("提示","终端信息不正确");
						return;
					}
				}
		});
	}
	
	var tmnlInfoPanel = new Ext.form.FormPanel({
		region:'center',
		border : false,
		title : '终端信息',
		bodyStyle:'padding:10px 0px 0px 0px',
		layout:'column',
		items:[consFormField1, consFormField2, consFormField3]
	});

	var dataComStore = new Ext.data.JsonStore({
		url : './runman/feildman/terminalparaset!queryPorItemList.action',
		fields : ['dataName', 'dataValue'],
		root : "bpil"
	});
	
	var comboxfield = new Ext.form.ComboBox({
		store : dataComStore,
		labelSeparator : '',
		triggerAction : 'all',
		editable : false,
		mode : 'local',
		valueField : 'dataValue',
		displayField : 'dataName',
		lazyRender : true,
		emptyText : '请选择',
		selectOnFocus : true
	});

	var handRowNumberer = new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (handStore && handStore.lastOptions
							&& handStore.lastOptions.params) {
						startRow = handStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			});
	var handStore = new Ext.data.Store();
	var storeForPage = new Ext.data.Store();
	var handSm = new Ext.grid.CheckboxSelectionModel();
	var handCm = new Ext.grid.ColumnModel({});
	var handRecordGrid = new Ext.grid.EditorGridPanel({
		cm : handCm,
		sm : handSm,
		ds : handStore,
		viewConfig : {
			forceFit: false
		},
		tbar : [{
			xtype : 'label',
			id : 'tmpTtLab',
			html : "<font font-weight:bold;>终端表计信息</font>"
		}, {
			xtype : 'tbfill'
		}, '-', {
			icon : '',
			text : '下发',
			iconCls : '',
			handler : function () {
						ctrlCheckStaff(sendInfoAjax, '');
					}
		}, '-'],
		bbar : new Ext.ux.MyToolbar({
			store : handStore,
			enableExpAll : true,
			allStore : storeForPage
		})
	});	
	var recordGridPanel = new Ext.Panel({
			region:'center',
			monitorResize : true,
			layout:'fit',
			items : [handRecordGrid],
			border : false
		});
	// tabPanel数据加载Ajax事件
	function creatGridAjax(val) {
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryPortocolItem.action',
					params : {
						// 获取当前table页的ID
						proNO : val
					},
					success : gridResponse
				});
	}

	// 根据表计参数F10/F11的选择动态生成数据显示Grid表头
	function gridResponse(response) {
		var result = Ext.decode(response.responseText);
		var columns = new Array();
		var fields = new Array();
		var piLength = result.bcpi.length;
		if (piLength > 1) {

			columns[0] = handRowNumberer;
			
			columns[1] = handSm;
			
			columns[piLength + 1] = {
				header : '测量点号',
				dataIndex : 'mpSn',
				align : 'center',
				tooltip : '测量点号',
				sortable : true
			};

			columns[piLength + 2] = {
				header : '计量点编号',
				dataIndex : 'mpNo',
				align : 'center',
				tooltip : '计量点编号'
			};
			
			columns[piLength + 3] = {
				header : '计量点名称',
				dataIndex : 'mpName',
				align : 'center',
				tooltip : '计量点名称'
			};

			columns[piLength + 4] = {
				header : '电能表资产号',
				dataIndex : 'meterAssetNo',
				tooltip : '电能表资产号',
				align : 'center'
			};
			fields[piLength - 1] = {name : 'mpSn'};
			fields[piLength] = {name : 'mpNo'};
			fields[piLength + 1] = {name : 'mpName'};
			fields[piLength + 2] = {name : 'meterAssetNo'};
			fields[piLength + 3] = {name : 'blockSn'};
			protocolItemList = result.bcpi;
			for (var i = 2; i < result.bcpi.length + 1; i++) {
				var pcInfo = result.bcpi[i-1];
//				var cm = new Ext.grid.Column();
//				var fd = new Ext.data.Field();
				//构造表头和数据项
				var cm = {};
				var fd = {};
				cm.header = pcInfo.protItemName.substring(3, (pcInfo.protItemName).length);
				cm.dataIndex = pcInfo.protItemNo;
				fd.name = pcInfo.protItemNo;
				cm.align = 'center';
				cm.tooltip = pcInfo.protItemName;
				var maxVal = pcInfo.maxVal;
				if (Ext.isEmpty(maxVal)) {
					maxVal = Number.POSITIVE_INFINITY;
				}
				var minVal = pcInfo.minVal;
				if (Ext.isEmpty(minVal)) {
					minVal = Number.NEGATIVE_INFINITY;
				}
				if (pcInfo.htmlFormat == 0) {
					switch (parseInt(pcInfo.dataStyle)) {
						case 0 :
							cm.editor = new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^[0-9ABCDEFabcdef]{1,2}$/,
										regexText : '请输入十六进制格式数据'
									}));
							break;
						case 1 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.NumberField(
									{
										selectOnFocus : true,
										regex : /^-?\d+$/,
										regexText : '请输入整型数字',
										maxValue : maxVal,
										minValue : minVal
									}))
							break;
						case 2 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true
											// regex : /^\w+$/,
											// regexText : '请输入字符串'
										}));
							break;
						case 3 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^[01]*$/,
										regexText : '请输入二进制数字'
									}));
							break;
						case 4 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^-?\d+(\.)?\d*$/,
										regexText : '请输入浮点型数字'
									}));
							break;
						case 5 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
									selectOnFocus : true,
									regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
									regexText : '请输入正确格式的IP地址（192.168.2.12）'
										// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址（192.168.2.12）');}
								}));
							break;
						case 6 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d{1,5}$/,
										regexText : '请输入正确格式的IP地址和端口号（192.168.2.12:8080）'
											// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
									}));
							break;
						case 7 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^$/,
										regexText : '请输入汉字'
											// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
										}));
							break;
						case 8 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										format : 'Y-m-d',
										regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/,
										regexText : '请以YYYY-MM-DD型输入日期'
									}));
							break;
						case 9 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
									selectOnFocus : true,
									// format : 'Y-m-d H:i',
									regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
									regexText : '请以YYYY-MM-DD HH24:MI型输入日期'
								}));
							break;
						case 10 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								format : 'Y-m-d H:i:s',
								regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以YYYY-MM-DD HH24:MI:SS型输入日期'
							}));
							break;
						case 11 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'm-d H:i',
								// dateFormat : 'm-d',
								// timeFormat : 'H:i',
								regex : /^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以MM-DD HH24:MI型输入日期'
							}));
							break;
						case 12 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'd H:i',
								// dateFormat : 'd',
								// timeFormat : 'H:i',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以DD HH24:MI型输入日期'
							}));
							break;
						case 13 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'H:i:s',
								// dateFormat : '',
								// timeFormat : 'H:i:s',
								regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以HH24:MI:SS型输入日期'
							}));
							break;
						case 14 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'H:i',
								regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以HH24:MI型输入日期'
							}));
							break;
						case 15 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'd H:i:s',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以DD HH24:MI:SS型输入日期'
							}));
							break;
						case 16 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'Y-m',
								regex : /^\d{4}-(0[1-9]|1[0-2])$/,
								regexText : '请以YYYY-MM型输入日期'
							}));
							break;
						case 17 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								// format : 'd H',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4])$/,
								regexText : '请以DD HH型输入日期'
							}));
							break;
						case 18 :
							cm.editor =  new Ext.grid.GridEditor(new Ext.form.TextField({
								selectOnFocus : true,
								regex : /^([0-9ABCDEF]{2}-){5}[0-9ABCDEF]{2}$/,
								regexText : '请输入正确的MAC地址（00-16-71-09-F2-2E）'
									// validator:function(){Ext.Msg.alert('请输入正确的MAC地址（00-16-71-09-F2-2E）');}
								}));
							break;
					}
				} else if (pcInfo.htmlFormat == 1) {
					comStore[pcInfo.protItemNo] = new Ext.data.JsonStore({
						url : './runman/feildman/terminalparaset!queryPorItemList.action',
						fields : ['dataName', 'dataValue'],
						root : "bpil",
						autoLoad : true,
						baseParams :{
							proItemNo : pcInfo.protItemNo
						}
					});

					var comboxfield = new Ext.form.ComboBox({
								store : comStore[pcInfo.protItemNo],
								labelSeparator : '',
								triggerAction : 'all',
								editable : false,
								mode : 'remote',
								valueField : 'dataValue',
								displayField : 'dataName',
								lazyRender : true,
								emptyText : '请选择',
								selectOnFocus : true
							});

					cm.editor = new Ext.grid.GridEditor(comboxfield, {
								autoSize : 'both'
							})
					cm.renderer = function (value, metaData, record, rowIndex, colIndex) {
						if (value == null || value == '')
							return '-请选择-';
						var mc = comStore[handCm.getDataIndex(colIndex)].query('dataValue', value);
						if (mc != null && mc.length > 0)
							return "<div align = 'left'>"
									+ mc.get(0).get('dataName') + "</div>";
						else
							return '-请选择-';
					}
				} else {
					cm.editor = new Ext.grid.GridEditor(new Ext.form.TextField({
						selectOnFocus : true
					}));
				}
				
				fields[i-2] = fd;
				columns[i] = cm;
			}
			handCm = new Ext.grid.ColumnModel({
				columns: columns
			});
			handStore = new Ext.data.Store({
//				autoDestroy: true,
				sortInfo: {
				    field: 'mpSn',
				    direction: 'ASC'
				},
			    reader: new Ext.data.ArrayReader({
			    	fields : new Ext.data.Record.create(fields)
			    }
			    )
			});
			storeForPage = new Ext.data.Store({
				sortInfo: {
				    field: 'mpSn',
				    direction: 'ASC'
				},
			    reader: new Ext.data.ArrayReader(
			        {
			            idIndex: 0  
			        },
			        new Ext.data.Record.create(fields)
			    )
			});
			handRecordGrid.reconfigure(handStore, handCm);
			handRecordGrid.getBottomToolbar().bind(handStore);
			handRecordGrid.getBottomToolbar().bindAllStore(storeForPage);
			handRecordGrid.doLayout();
			queryTmnlTermInfo();
		}
	}

	//查询终端表计信息
	function queryTmnlTermInfo() {
		var type = handRadioForm.getValue().inputValue;
		var protocolCode = tmnlInfoPanel.getForm().getFieldValues().protocolCode;
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!queryTmnlTermInfoList.action',
				params : {
					// 终端资产编号
					terminalNo : hideValueText.getValue(),
					proNO : protocolCode + type
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					var tmnlTermDataList = result.tmnlTermDataList;
					var tmnlTermMap = result.tmnlTermMap;
					if (tmnlTermDataList.length == 0) {
						Ext.Msg.alert("提示","终端表计信息不存在");
						return;
					} else {
						var i = 0;
						var gridData = new Array();
						Ext.iterate(tmnlTermDataList, function (key, value) {
							gridData[i++] = new Array();
							var j = 0;
							Ext.each(value, function (val) {
								Ext.iterate(val, function(k, v) {
									if (k == 'currentValue')
										gridData[i - 1][j++] = v;
								});
							});
							if(!tmnlTermMap[key]){
							return;
							}
							gridData[i - 1][j++] = tmnlTermMap[key].mpSn;
							gridData[i - 1][j++] = tmnlTermMap[key].mpNo;
							gridData[i - 1][j++] = tmnlTermMap[key].mpName;
							gridData[i - 1][j++] = tmnlTermMap[key].meterAssetNo;
							gridData[i - 1][j++] = value[0].blockSn;
						});
						handStore.proxy = new Ext.ux.data.PagingMemoryProxy(gridData);
						storeForPage.loadData(gridData);
						handStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
					}
				}
		})
	}
	
	function sendInfoAjax() {
		//取得选中的表计参数
		var selectModel = handRecordGrid.getSelectionModel();
		recs = selectModel.getSelections();
		if (recs.length == 0) {
			Ext.Msg.alert("提示", "请选择下发的表计");
			return;
		}
		var pn = [];
		var gridData = new Array();
		pn[0] = 0;
		var tmnlAssetNo = hideValueText.getValue();
		var n = 0;
		for (var i = 0; i < recs.length; i++) {
			var itemLength = protocolItemList.length;
			gridData[0] = {};
			gridData[0].protItemNo = protocolItemList[0].protItemNo;
			gridData[0].currentValue = recs.length;
			gridData[0].tmnlAssetNo = tmnlAssetNo;
			gridData[0].saveTime = new Date();
			gridData[0].blockSn = "0";
			// 构造json数据源对象
			// var dbData = recs[i].data.dataDetails[0];
			for (var j = 1; j < itemLength; j++) {
				gridData[n*(itemLength - 1) + j] = {};
//				if ((protocolItemList[j].protItemNo).substring(5, 8) == '001') {
//					gridData[0][j].protItemNo = protocolItemList[j].protItemNo;
//					gridData[0][j].currentValue = recs.length;
//					gridData[0][j].tmnlAssetNo = tmnlAssetNo;
//				} else {
				gridData[n*(itemLength - 1) + j].protItemNo = protocolItemList[j].protItemNo;
				var currentValue = recs[i].get(protocolItemList[j].protItemNo);
				if (Ext.isEmpty(currentValue)) {
					Ext.Msg.alert("提示", protocolItemList[j].protItemName
									+ "有值未输入");
					return;
				} else {
					gridData[n*(itemLength - 1) + j].currentValue = currentValue;
				}

				gridData[n*(itemLength - 1) + j].tmnlAssetNo = tmnlAssetNo;
				gridData[n*(itemLength - 1) + j].saveTime = new Date();
				gridData[n*(itemLength - 1) + j].blockSn = recs[i].get('blockSn');
// }
			};
			n++;
		}
		var taskSecond = Ext.Ajax.timeout / 1000;
		Ext.Ajax.timeout = (taskSecond + 20) * 1000
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});
		var type = handRadioForm.getValue().inputValue;
		var protocolCode = tmnlInfoPanel.getForm().getFieldValues().protocolCode;
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!sendMeterPara.action',
			params : {
				taskSecond : taskSecond,
				fn :parseInt(type.substring(2,4), 16),
				pn : pn,
				proNo : protocolCode + type,
				terminalNo : tmnlAssetNo,
				jsonData : Ext.encode(gridData)
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				var statusStr='';
				if (result.success) {
					var val = result.statusCode;
					switch (val) {
						case '31' :
						statusStr = "<font color='green';font-weight:bold>" + '召测成功'
									+ "</font>";
						case '32' :
						statusStr =  "<font color='red';font-weight:bold>" + '召测失败'
									+ "</font>";
						case '04' :
						statusStr =  "<font color='green';font-weight:bold>" + '下发成功'
									+ "</font>";
						case '05' :
						statusStr =  "<font color='red';font-weight:bold>" + '下发失败'
									+ "</font>";
						case '40' :
						statusStr =  "<font color='red';font-weight:bold>" + '所选测量点不存在'
									+ "</font>";
						case '41' :
						statusStr =  "<font color='red';font-weight:bold>" + '终端数据库中无数据'
									+ "</font>";
						case '42' :
						statusStr =  "<font color='red';font-weight:bold>" + '终端不在线'
									+ "</font>";
						case '43' :
						statusStr =  "<font color='red';font-weight:bold>" + '终端响应超时'
									+ "</font>";
						case '44' :
						statusStr =  "<font color='red';font-weight:bold>" + '终端无应答'
									+ "</font>";
						default :
							 return val;
					}
				}
				if (Ext.isEmpty(statusStr)) {
					Ext.getCmp('tmpTtLab').setText("<font font-weight:bold;>终端表计信息</font>", false);
				} else {
					Ext.getCmp('tmpTtLab').setText("<font font-weight:bold;>终端表计信息</font>" + "(" + statusStr + ")", false);
				}
			},
			failure : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				Ext.MessageBox.alert('提示', '请求超时或失败！');
				overFlat = true;
				var action = Ext.decode(response.responseText);
				if (action.message && action.message != "") {
					return Ext.Msg.alert(message);
				}
				return;
			}
		});
	}
	
	// 左边树操作获取操作对象用户信息
	new LeftTreeListener({
				modelName : '电能表注册',
				processUserGridSelect : getTermnInfo,
				processClick : getNodeInfo
			});

	function getTermnInfo(sm, row, record) {
   		handText.setValue(record.get('consName'));
   		hideTypeText.setValue('usr');
   		hideValueText.setValue(record.get('tmnlAssetNo'));
   		loadData();
	}

	// 获取节点用户信息
	function getNodeInfo(p, node, e) {
		var obj = node.attributes.attributes;
		var nodeType = node.attributes.type;
		if (nodeType == 'usr') {
			handText.setValue(obj.consName);
			hideTypeText.setValue(nodeType);
			hideValueText.setValue(obj.tmnlAssetNo);
			loadData();
		} else {
			Ext.Msg.alert('提示', '请选择用户节点');
			return;
		}
	}

	var handQueryPanel = new Ext.Panel({
		labelAlign : 'right',
		frame : false,
		height : 130,
		region : 'north',
		bodyStyle : 'padding:5px 0px 0px 0px',
		layout : 'border',
		border : false,
		items : [handQueryTop, tmnlInfoPanel]
	});
	
	var handViewPanel = new Ext.Panel({
		bodyStyle:'padding:5px 5px 5px 5px',
		layout: 'border' ,
		items: [handQueryPanel,recordGridPanel],
		border : false
	});
	//电能表注册
	renderModel(handViewPanel,'电能表注册');
})