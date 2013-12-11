/*
 * ! Author: 杨传文 Date:08/11/2010 Description: 费控-电能表控制
 */
 
var overFlag = false;
var dataDetailPanel;
var mpCheckBoxPanel;
var callParaPanel;
var pnTotal = {
	pn : []
};

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

/* 弹出原始报文窗口 */
function origFrameQryShow(consNo, consName, tmnlAssetAddr) {
	staticConsNo = consNo;
	staticConsName = consName;
	staticTmnlAssetAddr = tmnlAssetAddr;
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp",false,"origFrameQry");
}

var meterControlStoreForPage = new Ext.data.ArrayStore({
			idIndex : 0,
			proxy : new Ext.data.MemoryProxy(),
			fields : [{
					name : 'keyId'
				}, {
					name : 'orgName'
				}, {
					name : 'consNo'
				}, {
					name : 'consName'
				}, {
					name : 'terminalAddr'
				}, {
					name : 'tmnlAssetNo'
				}, {
					name : 'meterId'
				}, {
					name : 'commAddr'
				}, {
					name : 'status'
				}, {
					name : 'protocolCode'
				}
		// , {
		// name : 'fetchData'
		// }
		]
	});
	
		// 初始块结构生成
function meterInitBolckData(dbData) {
	var outData = new Array();
	var dataNum = 0;
	for (var i = 0; i < dbData.length; i++) {
		var blockRule = dbData[i][6];
		if (blockRule.length == 3) {
			if (blockRule == 000 || blockRule == 100 || blockRule == 300
					|| blockRule == 500 || blockRule == 400) {
				outData.splice(dataNum, 0, dbData[i].slice(0));
				dataNum++;
			}
		} else {
			outData.splice(dataNum, 0, dbData[i].slice(0));
			dataNum++;
		}
	}
	return outData;
}

Ext.onReady(function() {
	Ext.QuickTips.init();// 支持tips提示
	
	var topRadio = new Ext.form.RadioGroup({
		width:500,
		style:'padding:7px',
		items:[new Ext.form.Radio({
			boxLabel:'催费告警',
			name:'controlType',
			inputValue:'01',
			checked:true
		}),{
			boxLabel:'告警解除',
			name:'controlType',
			inputValue:'02'
		},{
			boxLabel:'合闸',
			name:'controlType',
			inputValue:'03'
		},{
			boxLabel:'分闸',
			name:'controlType',
			inputValue:'04'
		},{
			boxLabel:'保电',
			name:'controlType',
			inputValue:'05'
		},{
			boxLabel:'保电解除',
			name:'controlType',
			inputValue:'06'
		}]
	});
	
	// grid解锁
	function unlockGrid() {
		resultSm.unlock();
		resultGrid.onEnable();
		resultGrid.getBottomToolbar().enable();
		Ext.getCmp('meter_tpselectAllcb').setValue(false);
	}
	// grid锁定
	function lockGrid() {
		resultSm.lock();
		resultGrid.onDisable();
		resultGrid.getBottomToolbar().disable();
	}
	
		/* 规约类型 */
	var typeComStore = new Ext.data.JsonStore({
			idProperty : 'protocolCode',
			url : './runman/feildman/terminalparaset!queryPortocolCode.action',
			fields : ['protocolCode', 'protocolName'],
			root : "pc"
		});
		
	// 初始化RadioGroup
	function initRGAjax() {
		Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!queryDataType.action',
				params : {
					// 获取缺省的规约项编码
					protocolCode : '5'
				},
				success : comboxResponse
		});
	}
	
	// 规约类型json数据load
	typeComStore.load();
	// 初始化combobox的value值
	typeComStore.on("load", onstoreStoreLoad, typeComStore, true);
	
	//设置combobox的初始化value值
	function onstoreStoreLoad() {
		if (typeComStore.getTotalCount() > 0) {
			initRGAjax();
		}
	}
	
	//动态生成RadioGroup和tabPanel
	function comboxResponse(response) {
		creatTPAjax();
	}
	
	//创建规约明细tabPanel的Ajax事件
	function creatTPAjax() {
		Ext.Ajax.request({
				url : './baseapp/meterControl!queryPortocol.action',
				params : {
					// 获取规约项编码
					protocolCode : '5',
					// 获取数据类型
					dataType : '25'
				},
				success : tpResponse
		});
	}
	
	// 创建规约明细tabPanel的Ajax事件响应
	function tpResponse(response) {
		var result = Ext.decode(response.responseText);
		if (result.bcp.length > 0) {
			var mainTab = Ext.getCmp('meter_panelForTab');
			mainTab.getBottomToolbar().removeAll();
			mainTab.getBottomToolbar().add(loadbbarItem());
			// 移除panel中的组件
			mainTab.removeAll();
			// 创建新的tabPanel
			var myTabPanel = creatUserTab();
			for (var i = 0; i < result.bcp.length; i++) {
				// 在tabPanel中增加子panel
				var addpanle = addTable(result.bcp[i].shortName,
						result.bcp[i].protocolNo, result.bcp[i].protocolName);
				myTabPanel.add(addpanle);
			}
			mainTab.add(myTabPanel);
			// 显示第一个panel
			myTabPanel.setActiveTab(0);
			mainTab.doLayout();
		} else {
			// 移除panel中的组件
			var mainTab = Ext.getCmp('meter_panelForTab');
			mainTab.removeAll();
			mainTab.doLayout();
			Ext.Msg.alert('', "该分类数据不存在，请确认");
		}
	}
	
	//load参数grid的bbar项
	function loadbbarItem() {
		var itemArray = new Array();
		itemArray.push([{
					xtype : 'textfield',
					id : 'meter_tpstoretext',
					hidden : true
				}, '->', {
					xtype : 'button',
					width : '40',
					text : '参数下发',
					handler : function () {
						ctrlCheckStaff(sendInfoAjax, '');
					}
				}
//				'-', {
//					xtype : 'button',
//					width : '40',
//					text : '召测',
//					handler : function () {
//						ctrlCheckStaff(getCallParaAjax, '');
//					}
//				}, '-', {
//					xtype : 'button',
//					width : '70',
//					text : '保存召测值',
//					handler : function () {
//						Ext.MessageBox.confirm('提示', '确定保存召测值吗?', saveInfoAjax);
//					}
//				},
//				'-', new Ext.SplitButton({
//					menu: new Ext.menu.Menu({
//				        items: [
//					        {text: '保存参数', handler: function () {
//								Ext.MessageBox.confirm('提示', '确定保存页面参数吗?', savePageInfo);
//							}},
//					        {text: '后台下发', handler: function () {
//								Ext.MessageBox.confirm('提示', '确定后台下发参数吗?', saveTBgTaskInfo);
//							}}
//				        ]
//					})
//
//				})
				]);
		return itemArray;
	}
	
		// 参数下发Ajax事件
	function sendInfoAjax(btn) {
		if (btn == 'no') {
			return;
		}
		var recs;
		if (Ext.getCmp('meter_tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('meter_tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 下发终端集合
		var termialArray = new Array();

		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;

		// 从title中取FN号
		var fnStr = Ext.getCmp('meter_tpTbpanel').getActiveTab().id
		var fn = null;
		if (fnStr.length == 5) {
			fn = parseInt(fnStr.substring(3,5), 16);
		} else {
			Ext.Msg.alert('', '规约项不正确')
			return;
		}
		// 根据数据类型设定PN值
		var pn = new Array();
		var protocolCode = '5';
		// var protocolName = Ext.getCmp('gyComboBox').lastSelectionText;
		var dataType = '25';
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo + '`' + recs[i].data.meterId + '`'+ recs[i].data.commAddr;
			} else {
				Ext.Msg.alert('', recs[i].data.tmnlAssetNo
								+ '终端规约类型与页面下发参数规约类型不一致，请确认');
				return;
			}
		}
		if (termialArray.length == 0) {
			Ext.Msg.alert('', "请选择终端");
			return;
		}
		if (dataType == 22 || dataType == 24) {
			var checkValue = Ext.getCmp('tpmpcbg').getValue();
			var checkValue2 = pnTotal.pn;
			for (var i = 0; i < checkValue2.length; i++) {
				checkValue = checkValue.concat(checkValue2[i]);
			}
			if (checkValue.length > 0) {
				for (var i = 0; i < checkValue.length; i++) {
					pn[i] = checkValue[i].boxLabel;
				}
			} else {
				Ext.Msg.alert('', "请选择测量点号");
				return;
			}
		} else {
			pn[0] = 0;
		}
		var store = Ext.getCmp('meter_gp' + Ext.getCmp('meter_tpTbpanel').getActiveTab().id).getStore();
		var recData = recs[0].json;
		gridData[0] = new Array();
		// 构造json数据源对象
		// var dbData = recs[0].data.dataDetails[0];
		for (var j = 0; j < store.getCount(); j++) {
			gridData[0][j] = {};
			gridData[0][j].protItemNo = store.getAt(j).data.protItemNo;
			gridData[0][j].tmnlAssetNo = recs[0].data.tmnlAssetNo;
			if (Ext.isEmpty(store.getAt(j).data.dataValue)) {
				Ext.Msg.alert("", store.getAt(j).data.protItemName
								+ "值未输入");
				return;
			} else {
				gridData[0][j].currentValue = store.getAt(j).data.dataValue;
			}

			gridData[0][j].saveTime = new Date();
			gridData[0][j].blockSn = store.getAt(j).data.index;
			gridData[0][j].innerBlockSn = store.getAt(j).data.protItemSn;
		};
		var taskSecond=Ext.Ajax.timeout/1000;
		Ext.Ajax.timeout = (taskSecond + 20) * 1000
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : './baseapp/meterControl!sendPagePara.action',
			params : {
				taskSecond : taskSecond,
				fn : fn,
				pn : pn,
				dataFlag : false,
				dataType : dataType,
				//控制类型
				controlType :topRadio.getValue().getRawValue(),
				// 获取当前table页的ID(规约项编码)
				proNO : Ext.getCmp('meter_tpTbpanel').getActiveTab().id,
				tmnlAssetNoArr : termialArray,
				jsonData : Ext.encode(gridData)
			},
			success : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				var result = Ext.decode(response.responseText);
				if(result.mpflg == 4 || result.mpflg == 5) {
					Ext.Msg.alert('', "前置集群通信或服务中断");
					return;
				}
				if (result.tmnlflg == 1) {
					Ext.Msg.alert('', "请选择终端");
					return;
				}
				if (result.tmnlflg == 2) {
					Ext.Msg.alert('', "请输入下发参数");
					return;
				}
				// 更新下发状态
				var store = Ext.getCmp('meter_tpresultGrid').getStore();
				for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
					var rec = meterControlStoreForPage.getById(result.tmnlAssetNoArr[i]);
					rec.set('status', result.status[i]);
					rec.commit();
					// getSendIfno(result.tmnlAssetNoArr[i]);
				}
				store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
				if (Ext.getCmp('meter_tpselectAllcb').checked) {
					unlockGrid();
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
						var rec = store.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						rec.commit();
						// getSendIfno(result.tmnlAssetNoArr[i]);
					}
				}
				// Ext.getCmp('meter_tpresultGrid').doLayout();
				overFlat = true;
				Ext.Ajax.timeout=30000;
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
	
	// 创建规约明细tabpanel
	function creatUserTab() {
		var userTab = new Ext.TabPanel({
				id : 'meter_tpTbpanel',
				enableTabScroll : true,
				deferredRender : false,
				layoutOnTabChange : true,
				border : true,
				region : "center",
				items : [],
				listeners : {
					// tab间切换时响应事件
					tabchange : creatGridAjax
				}
			});
		return userTab;
	}
	
		// 创建规约明细子panel
	function addTable(shortName, protocolNo, protocolName) {
		// 数据项
		var store = new Ext.data.ArrayStore({
					fields : [{
								name : 'index'
							}, {
								name : 'protItemName'
							}, {
								name : 'dataValue'
							}, {
								name : 'unit'
							}, {
								name : 'decripton'
							}, {
								name : 'protItemNo'
							}, {
								name : 'blockRule'
							}, {
								name : 'dataStyle'
							}, {
								name : 'occupyBits'
							}, {
								name : 'maxVal'
							}, {
								name : 'minVal'
							}, {
								name : 'protItemSn'
							}, {
								name : 'defaultVaule'
							}]
				});

		Ext.ux.MultiEditColumnModel = Ext.extend(Ext.grid.ColumnModel, {
			getCellEditor : function(colIndex, rowIndex) {
				if (colIndex !== 2)
					return this.config[colIndex].getCellEditor(rowIndex);
				// 获取数据表现格式
				var ed = store.getAt(rowIndex).get('decripton');
				// 获取数据格式
				var sd = parseInt(store.getAt(rowIndex).get('dataStyle'));
				if (ed == null) {
					// 设定输入格式为普通字符串型输入框
					ed = '2';
				}
				if (sd == null) {
					// 设定输入格式为普通字符串型输入框
					sd = '2';
				}
				var maxVal = store.getAt(rowIndex).get('maxVal');
				if (Ext.isEmpty(maxVal)) {
					maxVal = Number.POSITIVE_INFINITY;
				}
				var minVal = store.getAt(rowIndex).get('minVal');
				if (Ext.isEmpty(minVal)) {
					minVal = Number.NEGATIVE_INFINITY;
				}
				var defaultValue = store.getAt(rowIndex).get('defaultVaule');
				if (Ext.isEmpty(defaultValue)) {
					defaultValue = '';
				}
				if (ed == 0) {
					switch (sd) {
						case 0 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true,
										regex : /^[0-9ABCDEFabcdef]{1,2}$/,
										value : defaultValue,
										regexText : '请输入十六进制格式数据'
									}));
						case 1 :
							return new Ext.grid.GridEditor(new Ext.form.NumberField(
									{
										selectOnFocus : true,
										regex : /^-?\d+$/,
										regexText : '请输入整型数字',
										maxValue : maxVal,
										minValue : minVal,
										value : defaultValue
									}))
						case 2 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true
											// regex : /^\w+$/,
											// regexText : '请输入字符串'
										}));
						case 3 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true,
										regex : /^[01]*$/,
										regexText : '请输入二进制数字'
									}));
						case 4 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true,
										regex : /^-?\d+(\.)?\d*$/,
										regexText : '请输入浮点型数字'
									}));
						case 5 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
									value : defaultValue,
									selectOnFocus : true,
									regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
									regexText : '请输入正确格式的IP地址（192.168.2.12）'
										// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址（192.168.2.12）');}
								}));
						case 6 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true,
										regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d{1,5}$/,
										regexText : '请输入正确格式的IP地址和端口号（192.168.2.12:8080）'
											// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
									}));
						case 7 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true,
										regex : /^[\u4e00-\u9fa5]{1,20}$|^[\u4e00-\u9fa5]{1,}[-\s\/]?[\u4e00-\u9fa5]{1,}$/,
										regexText : '请输入汉字'
											// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
										}));
						case 8 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
										value : defaultValue,
										selectOnFocus : true,
										format : 'Y-m-d',
										regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/,
										regexText : '请以YYYY-MM-DD型输入日期'
									}));
						case 9 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
									value : defaultValue,
									selectOnFocus : true,
									// format : 'Y-m-d H:i',
									regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
									regexText : '请以YYYY-MM-DD HH24:MI型输入日期'
								}));
						case 10 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								format : 'Y-m-d H:i:s',
								regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以YYYY-MM-DD HH24:MI:SS型输入日期'
							}));
						case 11 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'm-d H:i',
								// dateFormat : 'm-d',
								// timeFormat : 'H:i',
								regex : /^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以MM-DD HH24:MI型输入日期'
							}));
						case 12 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'd H:i',
								// dateFormat : 'd',
								// timeFormat : 'H:i',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以DD HH24:MI型输入日期'
							}));
						case 13 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'H:i:s',
								// dateFormat : '',
								// timeFormat : 'H:i:s',
								regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以HH24:MI:SS型输入日期'
							}));
						case 14 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'H:i',
								regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
								regexText : '请以HH24:MI型输入日期'
							}));
						case 15 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'd H:i:s',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
								regexText : '请以DD HH24:MI:SS型输入日期'
							}));
						case 16 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'Y-m',
								regex : /^\d{4}-(0[1-9]|1[0-2])$/,
								regexText : '请以YYYY-MM型输入日期'
							}));
						case 17 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								// format : 'd H',
								regex : /^(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4])$/,
								regexText : '请以DD HH型输入日期'
							}));
						case 18 :
							return new Ext.grid.GridEditor(new Ext.form.TextField({
								value : defaultValue,
								selectOnFocus : true,
								regex : /^([0-9ABCDEF]{2}-){5}[0-9ABCDEF]{2}$/,
								regexText : '请输入正确的MAC地址（00-16-71-09-F2-2E）'
									// validator:function(){Ext.Msg.alert('请输入正确的MAC地址（00-16-71-09-F2-2E）');}
								}));
					}
				}
				if (ed == 1) {
					return editorCombox;
				}
				if (ed == 2) {
					return new Ext.grid.GridEditor(new Ext.form.TextField({
						value : defaultValue,
						selectOnFocus : true
						}));
				}
			}

		});

		var bfield = new Ext.form.Field({
					autoCreate : {
						tag : 'select',
						children : [{
									tag : 'option',
									value : 'true',
									html : 'true'
								}, {
									tag : 'option',
									value : 'false',
									html : 'false'
								}]
					},
					getValue : function() {
						return this.el.dom.value == 'true';
					}
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

		var editorCombox = new Ext.grid.GridEditor(comboxfield, {
						autoSize : 'both'
					})
		// Grid参数值项目数据加载Ajax事件
		function getListAjax(proItemNo) {
			var result = null;
			Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!queryPorItemList.action',
				params : {
					// 获取规约项编码
					proItemNo : proItemNo
				},
				success : function(response) {
					result = Ext.decode(response.responseText);
				}
			});
			return result;
		}

		// 可编辑的数据类型
		function renderData(type, val, combox) {
			switch (type) {
				case 0 :
					return val;
				case 1 :
					return val;
				case 2 :
					return val;
				case 3 :
					return val;
				case 4 :
					return val;
				case 5 :
					return val;
				case 6 :
					return val;
				case 7 :
					return val;
				case 8 :
					return val;
					// return val ? val.format('Y-m-d') : val;
				case 9 :
					return val;
					// return val ? val.format('Y-m-d H:i') : val;
				case 10 :
					return val;
					// return val ? val.format('Y-m-d H:i:s') : val;
				case 11 :
					return val;
					// return val ? val.format('m-d H:i') : val;
				case 12 :
					return val;
					// return val ? val.format('d H:i') : val;
				case 13 :
					return val;
					// return val ? val.format('H:i:s') : val;
				case 14 :
					return val;
					// return val ? val.format('H:i') : val;
				case 15 :
					return val;
					// return val ? val.format('d H:i:s') : val;
				case 16 :
					return val;
					// return val ? val.format('Y-m') : val;
				case 17 :
					return val;
					// return val ? val.format('d H') : val;
				case 18 :
					return val;
				case 19 :
					return combox.lastSelectionText;
			}
		}
		// title
		var userCm = new Ext.ux.MultiEditColumnModel({
					defaults : {
						sortable : false,
						remoteSort : false
					},
					columns : [{
								header : '块序号',
								dataIndex : 'index',
								width : 30,
								align : 'left'
							}, {
								header : '参数项',
								dataIndex : 'protItemName',
								width : 100,
								align : 'left'
							}, {
								header : '参数值',
								dataIndex : 'dataValue',
								width : 30,
								align : 'center',
								renderer : function(value, metaData, record,
										rowIndex, colIndex, store) {
									var ed = store.getAt(rowIndex)
											.get('decripton');
									// 获取数据格式
									var sd = parseInt(store.getAt(rowIndex)
											.get('dataStyle'));
									if (ed == 0) {
										return renderData(sd, value);
									}
									// combobox渲染
									if (ed == 1) {
										return renderData(19, value,
												comboxfield);
									}
									if (ed == 2) {
										return new Ext.grid.GridEditor(new Ext.form.TextField({
										selectOnFocus : true
											// regex : /^\w+$/,
											// regexText : '请输入字符串'
										}));
									}

								},
								editor : new Ext.form.TextField({
											allowBlank : false
										})
							}, {
								header : '单位',
								dataIndex : 'unit',
								width : 30,
								align : 'left'
						}]
				});

		// 创建gridPanel
		var userGrid = new Ext.grid.EditorGridPanel({
			id : 'meter_gp' + protocolNo,
			store : store,
			border : false,
			cm : userCm,
			listeners : {
				// 如果为combox，则加载数据
				beforeedit : function(val) {
					if (val.record.get('decripton') == 1) {
						comboxfield.getStore().baseParams = {
							proItemNo : val.record.get('protItemNo')
						};
						comboxfield.getStore().load();
					}
					// var obj = val.grid.getColumnModel().getCellEditor(
					// val.column, val.row).field;
					// if (obj && obj.store) {
					// var comboxstore = obj.getStore();
					// comboxstore.baseParams = {
					// proItemNo : val.record.get('protItemNo')
					// };
					// comboxstore.load();
					// }
				},
				// 块规则下，对应块的生成
				afteredit : function(val) {
					var ruleGridStore = new Ext.data.ArrayStore({
								fields : [{
											name : 'index'
										}, {
											name : 'protItemName'
										}, {
											name : 'dataValue'
										}, {
											name : 'unit'
										}, {
											name : 'decripton'
										}, {
											name : 'protItemNo'
										}, {
											name : 'blockRule'
										}, {
											name : 'dataStyle'
										}, {
											name : 'occupyBits'
										}, {
											name : 'maxVal'
										}, {
											name : 'minVal'
										}, {
											name : 'protItemSn'
										}, {
											name : 'defaultVaule'
										}]
							})
					var data = Ext.getCmp('meter_tpstoretext').getValue();
					var dbData = Ext.decode(data);

					var blockRule = val.record.data.blockRule;
					var protItemNo = val.record.data.protItemNo;
					if (blockRule.length == 3) {
						// 第一类块循环规则下，第一层块的生成
						if (blockRule == 100) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								if (block != 000 && block != 100) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							for (var i = 0; i < val.value; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(0, 1);
									if (block == 2) {
										recordData[0] = i + 1;
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);
						}

						if (protItemNo.substring(5, 8) == '002' && !Ext.isEmpty(val.value)) {
							var indexVa = val.record.get('index');
							for (var i = 0; i < store.getCount(); i++) {
								var rec = store.getAt(i);
								var recIndex = rec.get('index');
								var protItemNoI = rec.get('protItemNo');
								if (protItemNoI.substring(5, 8) == '002' && indexVa !== recIndex && recIndex == val.value) {
									Ext.Msg.alert("提示", "已存在相同的块");
									val.record.set('dataValue', '');
									val.record.commit()
									return;
								}
							}
							for (var i = 0; i < store.getCount(); i++) {
								var rec = store.getAt(i);
								var recIndex = rec.get('index');
								var idx = (recIndex.toString()).indexOf('.');
								if (rec.get('index') == indexVa) {
									rec.set('index', val.value);
									rec.commit();
								} else if (idx !== -1) {
									rec.set('index', val.value + recIndex.substring(idx, recIndex.length+1));
									rec.commit();
								}
							}
						}
						
						// 第一类块循环规则下，第二层块的生成
						if (blockRule.substring(1, 3) == 10) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								var index = dataArray[j][0];
								if (index.length > 1
										&& val.record.data.index == index
												.substring(0, 1)) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							for (var i = 0; i < val.value; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(1, 2);
									if (block == 2) {
										recordData[0] = String(val.record.data.index)
												.concat('.', i + 1);
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);
						}

						if (blockRule.substring(2, 3) == 1) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								var index = dataArray[j][0];
								if (index.length > 3
										&& val.record.data.index == index
												.substring(0, 3)) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							for (var i = 0; i < val.value; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(2, 3);
									if (block == 2) {
										recordData[0] = String(val.record.data.index)
												.concat('.', i + 1);
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);

						}

						if (blockRule == 300) {

						}

						if (blockRule.substring(1, 3) == 30) {

						}

						if (blockRule.substring(2, 3) == 3) {

						}

						if (blockRule == 500) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								if (block != 000 && block != 500) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							var patt = new RegExp('1', 'g');
							for (var i = 0; i < String(val.value).match(patt).length; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(0, 1);
									if (block == 6) {
										recordData[0] = i + 1;
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);

						}

						if (blockRule.substring(1, 3) == 50) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								var index = dataArray[j][0];
								if (index.length > 1
										&& val.record.data.index == index
												.substring(0, 1)) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							var patt = new RegExp('1', 'g');
							for (var i = 0; i < String(val.value).match(patt).length; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(1, 2);
									if (block == 6) {
										recordData[0] = String(val.record.data.index)
												.concat('.', i + 1);
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);
						}

						if (blockRule.substring(2, 3) == 5) {
							var dataStore = val.grid.getStore();
							var dataArray = new Array();
							Ext.each(dataStore.getRange(), function(record) {
										var recordData = new Array();
										recordData[0] = record.data.index;
										recordData[1] = record.data.protItemName;
										recordData[2] = record.data.dataValue;
										recordData[3] = record.data.unit;
										recordData[4] = record.data.decripton;
										recordData[5] = record.data.protItemNo;
										recordData[6] = record.data.blockRule;
										recordData[7] = record.data.dataStyle;
										recordData[8] = record.data.occupyBits;
										recordData[9] = record.data.maxVal;
										recordData[10] = record.data.minVal;
										recordData[11] = record.data.protItemSn;
										recordData[12] = record.data.defaultVaule;
										dataArray.push(recordData);
									});
							for (var j = dataStore.getCount() - 1; j >= 0; j--) {
								var block = dataArray[j][6];
								var index = dataArray[j][0];
								if (index.length > 3
										&& val.record.data.index == index
												.substring(0, 3)) {
									dataArray.splice(j, 1)
								}
							}
							var count = 1;
							var patt = new RegExp('1', 'g');
							for (var i = 0; i < String(val.value).match(patt).length; i++) {
								for (var j = 0; j < dbData.length; j++) {
									var recordData = dbData[j].slice(0);
									var block = dbData[j][6].substring(2, 3);
									if (block == 6) {
										recordData[0] = String(val.record.data.index)
												.concat('.', i + 1);
										dataArray.splice(val.row + count, 0,
												recordData);
										count++;
									}
								}
							}
							val.grid.getStore().loadData(dataArray);
						}
					}
				}
			},
			autoScroll : true
		});
		var itempanel = new Ext.Panel({
				title : shortName,
				id : protocolNo,
				tabTip : protocolName,
				autoScroll : true,
				layout : 'fit',
				border : false,
				items : [userGrid]
			});
		return itempanel;
	}
	
	// tabPanel数据加载Ajax事件
	function creatGridAjax() {
		Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!queryPortocolItem.action',
				params : {
					// 获取当前table页的ID
					proNO : Ext.getCmp('meter_tpTbpanel').getActiveTab().id
				},
				success : gridResponse
			});
	}
	
	function updateTermnIfno() {
		var rec = meterControlStoreForPage.getRange(0, meterControlStoreForPage.getCount())
		var detailData = new Array();
		for (var i = 0; i < rec.length; i++) {
			rec[i].set('status', '');
			rec[i].set('dataDetails', detailData);
			rec[i].commit();
		}
		var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
		rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
		rsStore.load({
				params : {
					start : 0,
					limit : DEFAULT_PAGE_SIZE
				}
			});
	}
	
	// tabPanel数据加载Ajax事件的响应
	function gridResponse(response) {
		var result = Ext.decode(response.responseText);
		var data = new Array();
		var tpresultGrid = Ext.getCmp('meter_tpresultGrid');
		if (result.bcpi.length > 0) {
			for (var i = 0; i < result.bcpi.length; i++) {
				data[i] = new Array();
				data[i][0] = 0;
				data[i][1] = result.bcpi[i].protItemName;
				data[i][2] = result.bcpi[i].defaultVaule;
				data[i][3] = result.bcpi[i].unit;
				data[i][4] = result.bcpi[i].htmlFormat;
				data[i][5] = result.bcpi[i].protItemNo;
				data[i][6] = result.bcpi[i].blockRule;
				data[i][7] = result.bcpi[i].dataStyle;
				data[i][8] = result.bcpi[i].occupyBits;
				data[i][9] = result.bcpi[i].maxVal;
				data[i][10] = result.bcpi[i].minVal;
				data[i][11] = result.bcpi[i].protItemSn;
				data[i][12] = result.bcpi[i].defaultVaule;
			}
		}
		Ext.getCmp('meter_tpstoretext').setValue(Ext.encode(data));
		updateTermnIfno();
		// 加载grid数据
		Ext.getCmp('meter_gp' + Ext.getCmp('meter_tpTbpanel').getActiveTab().id).getStore()
				.loadData(meterInitBolckData(data));
		Ext.getCmp('meter_tpTbpanel').getActiveTab().doLayout();
	}
	
	// 生成用于包裹tabPanel的panel
	var panelForTab = new Ext.Panel({
			title : '',
			border : false,
			id : 'meter_panelForTab',
			region : 'center',
			layout : 'fit',
			items : [],
			bbar : []
	});
			
	// ComboBox和RadioGroup面板
	var formPanel = new Ext.Panel({
			border : false,
			id : 'meter_ptFormPanel',
			region : 'north',
			layout : 'border',
			split : true,
			height : 280,
			items : [{
							// bodyStyle : 'padding:10px;',
							xtype : 'panel',
							region : 'north',
							height : 40,
							layout : 'table',
							layoutConfig : {
								columns : 1
							},
							border : false,
							items : [{
										width : 600,
										id : 'panelRGroup',
										layout : 'form',
										border : false,
										items : [topRadio]
									}]
						}, panelForTab]
	});
	
	var resultStore = new Ext.data.Store({
			remoteSort : true,
			reader : new Ext.data.ArrayReader({
					idIndex : 0,
					fields : [{
								name :'keyId'
							}, {
								name : 'orgName'
							}, {
								name : 'consNo'
							}, {
								name : 'consName'
							}, {
								name : 'terminalAddr'
							}, {
								name : 'tmnlAssetNo'	
							}, {
								name : 'meterId'
							}, {
								name : 'commAddr'	
							}, {
								name : 'status'
							}, {
								name : 'protocolCode'
							}, {
								name : 'protocolData'
							}]
				})
		});
	
	//讲大store的数据转化为数组
	function storeToArray(val) {
		var data = new Array();
		for (var i = 0; i < val.getCount(); i++) {
			data[i] = new Array();
			data[i][0] = val.getAt(i).data.keyId;
			// 供电单位
			data[i][1] = val.getAt(i).data.orgName;
			// 用户编号
			data[i][2] = val.getAt(i).data.consNo;
			// 用户名称
			data[i][3] = val.getAt(i).data.consName;
			// 终端地址
			data[i][4] = val.getAt(i).data.terminalAddr;
			// 终端地址
			data[i][5] = val.getAt(i).data.tmnlAssetNo;
			// 电能表ID
			data[i][6] = val.getAt(i).data.meterId;
			// 数据明细
			data[i][7] = val.getAt(i).data.commAddr;
			// 通信状态
			data[i][8] = val.getAt(i).data.status;
			// 规约编码
			data[i][9] = val.getAt(i).data.protocolCode;
		}
		return data;
	}
		
	var resultSm = new Ext.grid.CheckboxSelectionModel();

	var resultCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
				// header : '序',
				renderer : function(v, p, record, rowIndex) {
					var startRow = 0;
					if (resultStore && resultStore.lastOptions
							&& resultStore.lastOptions.params) {
						startRow = resultStore.lastOptions.params.start;
					}
					return startRow + rowIndex + 1;
				}
			}), resultSm, {
			header : '供电单位',
			dataIndex : 'orgName',
			width : 70,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val + '">' + val
						+ '</div>';
				return html;
			}
		}, {
			header : '用户编号',
			dataIndex : 'consNo',
			width : 45,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户编号" ext:qtip="' + val + '">' + val
						+ '</div>';
				return html;
			}
		}, {
			header : '用户名称',
			dataIndex : 'consName',
			width : 50,
			align : 'center',
			renderer : function(val) {
				var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val + '">' + val
						+ '</div>';
				return html;
			}
		}, {
			header : '终端地址',
			dataIndex : 'terminalAddr',
			width : 45,
			align : 'center'
		}, {
			header : '电能表ID',
			dataIndex : 'meterId',
			width : 40,
			align : 'center'
		}, {
			header : '电能表地址',
			dataIndex : 'commAddr',
			width : 40,
			align : 'center'	
		}, {
			header : '状态',
			dataIndex : 'status',
			width : 55,
			renderer : function(val) {
				switch (val) {
					case '1' :
					return "<font color='green';font-weight:bold>" + '控制成功'
								+ "</font>";
					case '-1' :
					return "<font color='red';font-weight:bold>" + '终端无应答'
								+ "</font>";
					case '-27' :
					return "<font color='red';font-weight:bold>" + '其他错误'
								+ "</font>";
					case '-28' :
					return "<font color='red';font-weight:bold>" + '无请求数据'
								+ "</font>";
					case '-29' :
					return "<font color='red';font-weight:bold>" + '密码错/未授权'
								+ "</font>";
					case '-30' :
					return "<font color='red';font-weight:bold>" + '通信速率不能更改'
								+ "</font>";
					case '-40' :
					return "<font color='red';font-weight:bold>" + '年时区数超'
								+ "</font>";
					case '-41' :
					return "<font color='red';font-weight:bold>" + '日时段数超'
								+ "</font>";
					case '-42' :
					return "<font color='red';font-weight:bold>" + '费率数超'
								+ "</font>";
					case '-43' :
					return "<font color='red';font-weight:bold>" + '保留'
								+ "</font>";			
					case '-44' :
					return "<font color='red';font-weight:bold>" + '购电超囤积'
								+ "</font>";
					case '-45' :
					return "<font color='red';font-weight:bold>" + '充值次数错误'
								+ "</font>";
					case '-46' :
					return "<font color='red';font-weight:bold>" + '客户编号不匹配'
								+ "</font>";
					case '-47' :
					return "<font color='red';font-weight:bold>" + '身份认证错误'
								+ "</font>";
					case '-48' :
					return "<font color='red';font-weight:bold>" + '验证失败'
								+ "</font>";
					case '-49' :
					return "<font color='red';font-weight:bold>" + '重复充值'
								+ "</font>";
					case '-50' :
					return "<font color='red';font-weight:bold>" + '合闸失败'
								+ "</font>";			
					case '-51' :
					return "<font color='red';font-weight:bold>" + '注册加密机失败'
								+ "</font>";			
					case '-52' :
					return "<font color='red';font-weight:bold>" + '催费失败'
								+ "</font>";		
					case '-53' :
					return "<font color='red';font-weight:bold>" + '催费解除失败'
								+ "</font>";		
					case '-54' :
					return "<font color='red';font-weight:bold>" + '催费报电解除失败'
								+ "</font>";		
					case '-55' :
					return "<font color='red';font-weight:bold>" + '分闸失败'
								+ "</font>";		
					default :
						 return val;
				}
			},
			align : 'center'
		}, {
			header : '原始报文',
			dataIndex : 'protocolData',
			width : 40,
			align : 'center',
			renderer : function(s, m, rec) {
				return "<a href='javascript:'onclick='origFrameQryShow(\""+ rec.get('consNo') + "\",\"" + rec.get('consName')
						+ "\",\"" + rec.get('terminalAddr') + "\");" + "'>原始报文</a>";
			}
		}
	]);
	
	// 创建gridPanel
	var resultGrid = new Ext.grid.GridPanel({
		// title : '终端列表',
		id : 'meter_tpresultGrid',
		store : resultStore,
		cm : resultCm,
		border : false,
		sm : resultSm,
		region : 'center',
		tbar : [{
			xtype : 'label',
			id : 'meter_tpTtLab',
			html : "<font font-weight:bold;>备选用户</font>"
		}, {
			xtype : 'tbfill'
		}, '-', {
			xtype : 'checkbox',
			id : 'meter_tpselectAllcb',
			boxLabel : '全选',
			name : 'tpselectAllcb',
			checked : false,
			listeners : {
				'check' : function(r, c) {
					if (c) {
						resultSm.selectAll();
						lockGrid();
					} else {
						unlockGrid();
						resultSm.clearSelections()
					}
				}
			}

		}, '-', {
			icon : '',
			text : '删除选中用户',
			iconCls : 'cancel',
			handler : function() {
				if (Ext.getCmp('meter_tpselectAllcb').checked) {
					meterControlStoreForPage.removeAll(true);
					unlockGrid();
				} else {
					var recs = resultSm.getSelections();
					for (var i = 0; i < recs.length; i = i + 1) {
						meterControlStoreForPage.remove(meterControlStoreForPage
								.getById(recs[i].data.keyId));
					}
				}
				var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
			}
		}, '-', {
			icon : '',
			text : '删除成功用户',
			iconCls : 'minus',
			handler : function() {
				for (var i = 0; i < meterControlStoreForPage.getCount(); i++) {
					var statuscode = meterControlStoreForPage.getAt(i).data.status;
					if (statuscode == '04' || statuscode == '31') {
						meterControlStoreForPage.remove(meterControlStoreForPage.getAt(i));
					}
				}
				var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
			}
		}],
		bbar : new Ext.ux.MyToolbar({
					store : resultStore,
					enableExpAll : true,
					allStore : meterControlStoreForPage
				})
	});
	
	var panel = new Ext.form.FormPanel({
			// title : '终端参数',
			border : false,
			id : 'meter_tpPanel',
			layout : 'border',
			items : [formPanel, resultGrid]
		});
	
		// 获取操作对象用户信息
	new LeftTreeListener({
			modelName : '电能表控制',
			processUserGridSelect : getTermnInfo,
			processClick : getNodeInfo,
			processUserGridAllSelect : function(r) {
				var protocolCode = '5';
				var store = r.getStore();
				var tmnlAssetNoArr = new Array();
				var n = 0;
				for (var i=0; i < store.getCount(); i++) {
					var rec = store.getAt(i);
					if (rec.data.protocolCode == protocolCode) {
						tmnlAssetNoArr[n++] = rec.data.tmnlAssetNo;
					}
				};
				if (tmnlAssetNoArr.length == 0) {
					Ext.getCmp('meter_tpTtLab').setText("<font font-weight:bold;>备选用户</font><font color='red'>【所选终端与页面规约一致的个数为0】</font>", false);
					return;
				}
				Ext.Ajax.request({
					url : './baseapp/meterControl!queryTmnlArrList.action',
					params : {
						tmnlAssetNoArr : tmnlAssetNoArr,
						protocolCode : protocolCode
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (!result.tmnlList) {
							return;
						}
						var data = new Array();
						for (var i = 0; i < result.tmnlList.length; i++) {
							data[i] = new Array();
							data[i][0] = result.tmnlList[i].keyId;
							// 供电单位
							data[i][1] = result.tmnlList[i].orgName;
							// 用户编号
							data[i][2] = result.tmnlList[i].consNO;
							// 用户名称
							data[i][3] = result.tmnlList[i].consName;
							// 终端地址
							data[i][4] = result.tmnlList[i].terminalAddr;
							// 数据索引
							data[i][5] = result.tmnlList[i].tmnlAssetNo;
							// 终端资产编号
							data[i][6] = result.tmnlList[i].meterId;
							//电表地址
							data[i][7] = result.tmnlList[i].commAddr;
							// 数据明细
							data[i][9] = result.tmnlList[i].protocolCode
						}
						meterControlStoreForPage.loadData(data, true);
						var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
	
						rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
						rsStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
						resultSm.selectAll();
						Ext.getCmp('meter_tpresultGrid').doLayout();
					}
				});
			}
		});	
	
	function getTermnInfo(sm, row, record) {
		var protocolCode = '5';
		if (record.data.protocolCode !== protocolCode) {
			Ext.Msg.alert('', '所选终端规约类型与设置规约类型不一致！');
		} else {
			// 获取数据库参数值
			Ext.Ajax.request({
				url : './baseapp/meterControl!queryTmnlList.action',
				params : {
					nodeType : 'usr',
					nodeValue : record.data.tmnlAssetNo,
					protocolCode : protocolCode
				},
				success : function(response) {
					var result = Ext.decode(response.responseText);
					if (!result.tmnlList) {
						return;
					}
					var data = new Array();
					for (var i = 0; i < result.tmnlList.length; i++) {
						data[i] = new Array();
						data[i][0] = result.tmnlList[i].keyId;
						// 供电单位
						data[i][1] = result.tmnlList[i].orgName;
						// 用户编号
						data[i][2] = result.tmnlList[i].consNO;
						// 用户名称
						data[i][3] = result.tmnlList[i].consName;
						// 终端地址
						data[i][4] = result.tmnlList[i].terminalAddr;
						// 数据索引
						data[i][5] = result.tmnlList[i].tmnlAssetNo;
						// 终端资产编号
						data[i][6] = result.tmnlList[i].meterId;
						//电表地址
						data[i][7] = result.tmnlList[i].commAddr;
						// 数据明细
						data[i][9] = result.tmnlList[i].protocolCode
					}
					meterControlStoreForPage.loadData(data, true);
					var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();

					rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
					rsStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
					resultSm.selectAll();
					Ext.getCmp('meter_tpresultGrid').doLayout();
				}
			});
		}
	}	
	
	// 获取节点用户信息
	function getNodeInfo(p, node, e) {
		var obj = node.attributes.attributes;
		var nodeType = node.attributes.type;
		var nodeValue = null;
		var protocolCode = '5';
		// Ext.Msg.alert('提示','只加载规约类型与下发规约一致的终端');
		if (nodeType == 'org') {
			if (obj.orgType == '02') {
				Ext.Msg.alert('提示', '请选择省公司以下节点');
				return;
			}
			nodeValue = obj.orgNo;
		} else if (nodeType == 'usr') {
			nodeValue = obj.tmnlAssetNo;
		} else if (nodeType == 'line') {
			nodeValue = obj.lineId;
		} else if (nodeType == 'cgp' || nodeType == 'ugp') {
			nodeValue = obj.groupNo;
		} else {
			Ext.Msg.alert('提示', '请选择省公司以下节点');
			return;
		}
		// 获取数据库参数值
		Ext.Ajax.request({
			url : './baseapp/meterControl!queryTmnlList.action',
			params : {
				nodeType : nodeType,
				nodeValue : nodeValue,
				protocolCode : protocolCode
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				if (!result.tmnlList) {
					return;
				}
				var data = new Array();
				if (result.tmnlList.length == 0) {
					Ext.getCmp('meter_tpTtLab').setText("<font font-weight:bold;>备选用户</font><font color='red'>【该节点与页面规约一致的终端个数为0】</font>", false);
//					Ext.Msg.alert('', '该节点不存在与页面规约一致的终端');
					return;
				}
				Ext.getCmp('meter_tpTtLab').setText("<font font-weight:bold;>备选用户</font>", false);
				for (var i = 0; i < result.tmnlList.length; i++) {
					data[i] = new Array();
					data[i][0] = result.tmnlList[i].keyId;
					// 供电单位
					data[i][1] = result.tmnlList[i].orgName;
					// 用户编号
					data[i][2] = result.tmnlList[i].consNO;
					// 用户名称
					data[i][3] = result.tmnlList[i].consName;
					// 终端地址
					data[i][4] = result.tmnlList[i].terminalAddr;
					// 数据索引
					data[i][5] = result.tmnlList[i].tmnlAssetNo;
					// 终端资产编号
					data[i][6] = result.tmnlList[i].meterId;
					//电表地址
					data[i][7] = result.tmnlList[i].commAddr;
					// 数据明细
					data[i][9] = result.tmnlList[i].protocolCode
				}
				meterControlStoreForPage.loadData(data, true);
				var rsStore = Ext.getCmp('meter_tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(meterControlStoreForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
				resultSm.selectAll();
			}
		});
	}
	
	renderModel(panel, '电能表控制')
});