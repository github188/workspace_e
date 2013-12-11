/*
 * ! Author: Longkongcao Date:12/05/2009 Description: 终端参数设置 Update History:
 * none
 */
var overFlag = false;

var dataDetailPanel;

var mpCheckBoxPanel;

var blockCheckBoxPanel;

var callValueStore;

var callParaPanel;
var pnTotal = {
	pn : []
};
var snTotal = {
	sn : []
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
	openTab("原始报文查询", "./baseApp/dataGatherMan/origFrameQry.jsp", false,
			"origFrameQry");

}

var storeForPage = new Ext.data.ArrayStore({
			idIndex : 6,
			fields : [{
						name : 'bureauNo'
					}, {
						name : 'userNumber'
					}, {
						name : 'userName'
					}, {
						name : 'terminalAddr'
					}, {
						name : 'dataIndex'
					}, {
						name : 'status'
					}, {
						name : 'tmnlAssetNo'
					}, {
						name : 'dataDetails'
					}, {
						name : 'protocolCode'
					}
			// , {
			// name : 'fetchData'
			// }
			]
		});;

function showCallPara(val) {
	callParaPanel.show();
	var data = new Array();
	var tpresultGrid = Ext.getCmp('callParaGrid');
	if (val.length > 0) {
		for (var i = 0; i < val.length; i++) {
			data[i] = new Array();
			data[i][0] = 0;
			data[i][1] = val[i].protItemName;
			data[i][2] = val[i].defaultVaule;
			data[i][3] = val[i].unit;
			data[i][4] = val[i].htmlFormat;
			data[i][5] = val[i].protItemNo;
			data[i][6] = val[i].blockRule;
			data[i][7] = val[i].dataStyle;
			data[i][8] = val[i].occupyBits;
			data[i][9] = val[i].maxVal;
			data[i][10] = val[i].minVal;
			data[i][11] = val[i].protItemSn;
		}
	}
	Ext.getCmp('cpstoretext').setValue(Ext.encode(data));
	// 加载grid数据
	tpresultGrid.getStore().loadData(initBolckData(data));
	tpresultGrid.doLayout();
}

// 初始块结构生成
function initBolckData(dbData) {
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

function showMpCheckBox() {
	mpCheckBoxPanel.show();
}

function showBlockCheckBox() {
	blockCheckBoxPanel.show();
}
// function showTpDataDetail(val) {
// dataDetailPanel.show();
// if (val) {
// // var store = Ext.getCmp('tpdetailsGrid').getStore();
// var protocolNO = Ext.getCmp('tpTbpanel').getActiveTab().id;
//
// var itemStore = new Ext.data.Store({
// proxy : new Ext.data.HttpProxy({
// url : "./runman/feildman/terminalparaset!queryPortocolItem.action",
// method : "POST"
// }),
// reader : new Ext.data.JsonReader({
// root : 'bcpi',
// idProperty : "protItemNo",
// totalProperty : "totalCount"
// }, [{
// name : 'protItemName'
// }, {
// name : 'protItemNo'
// }, {
// name : 'unit'
// }])
// });
// itemStore.load({
// params : {
// proNO : protocolNO
// }
// });
// itemStore.on("load", function() {
// var protocolCode = Ext.getCmp('gyComboBox').value;
// var pn = new Array();
// var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
// var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
// var recs = selectModel.getSelections();
// // 下发终端集合
// var termialArray = new Array();
// for (var i = 0; i < recs.length; i++) {
// termialArray[i] = recs[i].data.tmnlAssetNo;
// }
// // 获取数据库参数值
// Ext.Ajax.request({
// url : './runman/feildman/terminalparaset!queryTmnlInfoList.action',
// params : {
// // 获取当前table页的ID(规约项编码)
// proNO : Ext.getCmp('tpTbpanel').getActiveTab().id,
// // 终端资产编号
// terminalNo : termialArray[0],
// dataType : dataType
// // pn : pn
// },
// success : function(response) {
// var result = Ext.decode(response.responseText);
// var dbData = new Array();
// for (var i = 0; i < result.tmnlInfo.length; i++) {
// dbData[i] = Ext.encode(result.tmnlInfo[i]);
// }
//
// var rec = Ext.getCmp('tpresultGrid').getStore()
// .getById(termialArray[0]);
// // rec.commit('dataDetails',
// // detailData);
// var detailData = rec.get('dataDetails');
// if (!detailData) {
// detailData = new Array();
// }
// var length = dbData.length > detailData.length
// ? dbData.length
// : detailData.length;
// var data = new Array();
// for (var i = 0; i < length; i++) {
// data[i] = new Array();
// var dbDataCount = dbData.length;
// // 数据值存在时，显示数据库值
// if (i < dbData.length) {
// var dbDatai = Ext.decode(dbData[i]);
// data[i][3] = dbDatai.currentValue;
// data[i][0] = dbDatai.blockSn;
// data[i][1] = itemStore.getById(dbDatai.protItemNo).data.protItemName;
// data[i][4] = itemStore.getById(dbDatai.protItemNo).data.unit;
// data[i][9] = dbDatai.mpSn;
// }
// // var indexStr = i + 1;
// // data[i][0] = indexStr;
// // 召测值存在时，显示召测值
// if (i < detailData.length) {
// var fetchData = detailData[i];
// data[i][0] = fetchData.blockSn;
// data[i][2] = fetchData.currentValue;
// data[i][5] = fetchData.statusCode;
// data[i][1] = itemStore
// .getById(fetchData.protItemNo).data.protItemName;
// data[i][4] = itemStore
// .getById(fetchData.protItemNo).data.unit;
// }
// }
// // 加载grid数据
// Ext.getCmp('tpdetailsGrid').getStore().loadData(data);
// Ext.getCmp('tpdetailsGrid').doLayout();
// }
// });
// });

// } else {
// Ext.getCmp('tpdetailsGrid').getStore().removeAll(false);
// Ext.getCmp('tpdetailsGrid').doLayout();
// }
// }

Ext.onReady(function() {
	Ext.QuickTips.init();// 支持tips提示

	/* 规约类型 */
	var typeComStore = new Ext.data.JsonStore({
				idProperty : 'protocolCode',
				url : './runman/feildman/terminalparaset!queryPortocolCode.action',
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
				id : 'gyComboBox',
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
					select : creatRGAjax
				},
				selectOnFocus : true
			});

	// 规约类型json数据load
	typeComStore.load();
	// 初始化combobox的value值
	typeComStore.on("load", onstoreStoreLoad, typeComStore, true);

	// 生成规约类型对应的RadioGroup
	function creatRGAjax() {
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryDataType.action',
					params : {
						// 从combobox中获取规约编码
						protocolCode : Ext.getCmp('gyComboBox').value
					},
					success : comboxResponse
				});
	}

	// 初始化RadioGroup
	function initRGAjax() {
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryDataType.action',
					params : {
						// 获取缺省的规约项编码
						protocolCode : typeComStore.getAt(0).data.protocolCode
					},
					success : comboxResponse
				});
	}

	// 设置combobox的初始化value值
	function onstoreStoreLoad() {
		if (typeComStore.getTotalCount() > 0) {
			gyComboBox.setValue(typeComStore.getAt(0).data.protocolCode);
			initRGAjax();
		}
	}

	// 动态生成RadioGroup和tabPanel
	function comboxResponse(response) {
		// 设置抬头
		Ext.getCmp('tpTtLab').setText("<font font-weight:bold;>备选用户</font>",
				false);
		// 清空备选用户
		storeForPage.removeAll(true);
		resultStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
		resultStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		var result = Ext.decode(response.responseText);
		if (result.dt.length > 0) {
			// RadioGroup(result.list.toA)
			// radio属性数组
			var item = new Array();
			// radio间距数组
			var cloums = new Array();
			// 创建子radio属性、间距数组
			for (var i = 0; i < result.dt.length; i++) {
				item[i] = new Array();
				item[i][0] = result.dt[i].dataName;
				cloums[i] = result.dt[i].dataName.length * 12 + 30;
				item[i][1] = result.dt[i].dataType;
			}
			// 设置默认第一个子radio为checked
			item[0][2] = true;
			// 获取RadioGroup
			var panelItem = Ext.getCmp('panelRGroup');
			panelItem.removeAll(true);
			// 生成新的RadioGroup
			panelItem.add(RadioGroup('typeRadio', '', item, cloums));
			panelItem.doLayout();
		} else {
			// 获取RadioGroup
			var panelItem = Ext.getCmp('panelRGroup');
			panelItem.removeAll(true);
			panelItem.doLayout();
			Ext.Msg.alert('', '规约项明细不存在，请确认');
		}

		creatTPAjax();

	}

	// 创建规约明细tabPanel的Ajax事件
	function creatTPAjax() {
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryPortocol.action',
					params : {
						// 获取规约项编码
						protocolCode : Ext.getCmp('gyComboBox').value,
						// 获取数据类型
						dataType : Ext.getCmp('tpRadioGroup').getValue().inputValue
					},
					success : tpResponse
				});
	}

	// 创建规约明细tabPanel的Ajax事件响应
	function tpResponse(response) {
		var result = Ext.decode(response.responseText);
		if (result.bcp.length > 0) {
			var mainTab = Ext.getCmp('panelForTab');
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
			var mainTab = Ext.getCmp('panelForTab');
			mainTab.removeAll();
			mainTab.doLayout();
			Ext.Msg.alert('', "该分类数据不存在，请确认");
		}
	}

	// Ext.form.RadioGroup扩展
	Ext.override(Ext.form.RadioGroup, {
				getItems : function() {
					return this.items;
				},
				setItems : function(data) {
					this.items = data;
				}
			});

	function RadioGroup(_name, fLable, _items, _columns) {
		var rg = new Ext.form.RadioGroup({
					name : _name,
					fieldLabel : fLable,
					id : 'tpRadioGroup',
					hideLabel : true
				});

		if (_columns != null)
			rg.columns = _columns;
		// 动态绑定
		var items = new Array();
		if (_items != null) {
			for (var i = 0; i < _items.length; i++) {
				items[i] = {};
				items[i].name = _name;
				items[i].boxLabel = _items[i][0];
				items[i].inputValue = _items[i][1];
				if (_items[i].length > 2)
					items[i].checked = _items[i][2];
				items[i].listeners = {
					'check' : function(r, c) {
						if (c) {
							creatTPAjax();
						}
					}
				};
			}
		}

		rg.setItems(items);

		return rg;
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
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
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
									}));
						case 2 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true
										// regex : /^\w+$/,
										// regexText : '请输入字符串'
									}));
						case 3 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										regex : /^[01]*$/,
										regexText : '请输入二进制数字'
									}));
						case 4 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										regex : /^-?\d+(\.)?\d*$/,
										regexText : '请输入浮点型数字'
									}));
						case 5 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
										regexText : '请输入正确格式的IP地址（192.168.2.12）'
										// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址（192.168.2.12）');}
									}));
						case 6 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										regex : /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d{1,5}$/,
										regexText : '请输入正确格式的IP地址和端口号（192.168.2.12:8080）'
										// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
									}));
						case 7 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										regex : /^$/,
										regexText : '请输入汉字'
										// validator:function(){Ext.Msg.alert('请输入正确格式的IP地址和端口号（192.168.2.12:8080）');}
									}));
						case 8 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										format : 'Y-m-d',
										regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/,
										regexText : '请以YYYY-MM-DD型输入日期'
									}));
						case 9 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'Y-m-d H:i',
										regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
										regexText : '请以YYYY-MM-DD HH24:MI型输入日期'
									}));
						case 10 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										format : 'Y-m-d H:i:s',
										regex : /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
										regexText : '请以YYYY-MM-DD HH24:MI:SS型输入日期'
									}));
						case 11 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'm-d H:i',
										// dateFormat : 'm-d',
										// timeFormat : 'H:i',
										regex : /^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
										regexText : '请以MM-DD HH24:MI型输入日期'
									}));
						case 12 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'd H:i',
										// dateFormat : 'd',
										// timeFormat : 'H:i',
										regex : /^(0[0-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
										regexText : '请以DD HH24:MI型输入日期'
									}));
						case 13 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'H:i:s',
										// dateFormat : '',
										// timeFormat : 'H:i:s',
										regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
										regexText : '请以HH24:MI:SS型输入日期'
									}));
						case 14 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'H:i',
										regex : /^([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0)$/,
										regexText : '请以HH24:MI型输入日期'
									}));
						case 15 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'd H:i:s',
										regex : /^(0[0-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4]):([0-5][0-9]|[6]0):([0-5][0-9]|[6]0)$/,
										regexText : '请以DD HH24:MI:SS型输入日期'
									}));
						case 16 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'Y-m',
										regex : /^\d{4}-(0[1-9]|1[0-2])$/,
										regexText : '请以YYYY-MM型输入日期'
									}));
						case 17 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
										value : defaultValue,
										selectOnFocus : true,
										// format : 'd H',
										regex : /^(0[0-9]|[12][0-9]|3[01]) ([0-1][0-9]|2[0-4])$/,
										regexText : '请以DD HH型输入日期'
									}));
						case 18 :
							return new Ext.grid.GridEditor(new Ext.form.TextField(
									{
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
							// regex : /^\w+$/,
							// regexText : '请输入字符串'
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

		// var render = new Ext.ux.RelativeRender({
		// header : '参数值',
		// dataIndex : 'dataValue',
		// ds : dataComStore,//
		// paramsName : {
		// name : 'dataName',
		// value : 'dataVal'
		// }
		// });

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
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							var ed = store.getAt(rowIndex).get('decripton');
							// 获取数据格式
							var sd = parseInt(store.getAt(rowIndex)
									.get('dataStyle'));
							if (ed == 0) {
								return renderData(sd, value);
							}
							// combobox渲染
							if (ed == 1) {
								// var grid = Ext.getCmp('gp' +
								// protocolNo)
								// var combox = grid.getColumnModel()
								// .getCellEditor(colIndex,
								// rowIndex).field;
								return renderData(19, value, comboxfield);
							}
							if (ed == 2) {
								return new Ext.grid.GridEditor(new Ext.form.TextField(
										{
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
					}
			// , {
			// header : '数据录入描述',
			// dataIndex : 'decripton',
			// align : 'left',
			// hidden : true
			// }, {
			// header : '规约项数据编码',
			// dataIndex : 'protItemNo',
			// width : 100,
			// hidden : true
			// }, {
			// header : '块循环规则',
			// dataIndex : 'blockRule',
			// width : 100,
			// hidden : true
			// }, {
			// header : '数据类型',
			// dataIndex : 'dataStyle',
			// width : 100,
			// hidden : true
			// }, {
			// header : '字节数',
			// dataIndex : 'occupyBits',
			// width : 100,
			// hidden : true
			// }, {
			// header : '最大值',
			// dataIndex : 'maxVal',
			// width : 100,
			// hidden : true
			// }, {
			// header : '最小值',
			// dataIndex : 'minVal',
			// width : 100,
			// hidden : true
			// }
			]
		});

		// 创建gridPanel
		var userGrid = new Ext.grid.EditorGridPanel({
			id : 'gp' + protocolNo,
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
					var data = Ext.getCmp('tpstoretext').getValue();
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

						if (protItemNo.substring(5, 8) == '002'
								&& !Ext.isEmpty(val.value)) {
							var indexVa = val.record.get('index');
							for (var i = 0; i < store.getCount(); i++) {
								var rec = store.getAt(i);
								var recIndex = rec.get('index');
								var protItemNoI = rec.get('protItemNo');
								if (protItemNoI.substring(5, 8) == '002'
										&& indexVa !== recIndex
										&& recIndex == val.value) {
									Ext.Msg.alert("提示", "已存在相同的块");
									val.record.set('dataValue', '');
									val.record.commit();
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
									rec
											.set(
													'index',
													val.value
															+ recIndex
																	.substring(
																			idx,
																			recIndex.length
																					+ 1));
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
					// Ext.getCmp('gp'
					// + Ext.getCmp('tpTbpanel').getActiveTab().id)
					// .getStore().loadData(recordArr);
				}
			},
			// clicksToEdit : 2,
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

	// 创建规约明细tabpanel
	function creatUserTab() {
		var userTab = new Ext.TabPanel({
					id : 'tpTbpanel',
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

	function loadbbarItem() {
		var itemArray = new Array();
		if (Ext.getCmp('tpRadioGroup')) {
			var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
			if (dataType == '22' || dataType == '24') {
				cldCheckBoxs.reset();
				itemArray.push([{
							xtype : 'label',
							text : '测量点'
						}, {
							xtype : 'checkboxgroup',
							id : 'tpmpcbg',
							columns : [30, 30, 30, 30, 30],
							// columns : 8,
							items : [{
										boxLabel : '0',
										name : 'cb-horiz-0',
										checked : false
									}, {
										boxLabel : '1',
										name : 'cb-horiz-1',
										checked : false
									}, {
										boxLabel : '2',
										name : 'cb-horiz-2',
										checked : false
									}, {
										boxLabel : '3',
										name : 'cb-horiz-3',
										checked : false
									}, {
										boxLabel : '4',
										name : 'cb-horiz-4',
										checked : false
									}
							// , {
							// boxLabel : '5',
							// name : 'cb-horiz-5',
							// checked : false
							// }, {
							// boxLabel : '6',
							// name : 'cb-horiz-6',
							// checked : false
							// }, {
							// boxLabel : '7',
							// name : 'cb-horiz-7',
							// checked : false
							// }
							]
						}, {
							xtype : 'button',
							width : '50',
							text : '更多测量点',
							handler : showMpCheckBox
						}]);
			}
		}

		itemArray.push([{
					xtype : 'textfield',
					id : 'tpstoretext',
					hidden : true
				}, '->', {
					xtype : 'checkbox',
					boxLabel : '下发数据库值',
					id : 'tpfetchcb',
					inputValue : 1,
					width : '90',
					checked : false
				},
				// '-', {
				// xtype : 'button',
				// width : '50',
				// text : '后台下发',
				// handler : function () {
				// Ext.MessageBox.confirm('提示', '确定后台下发参数吗?', savePageInfo);
				// }
				// },
				'-', {
					xtype : 'button',
					width : '40',
					text : '下发',
					handler : function() {
						ctrlCheckStaff(sendInfoAjax, '');
					}
				}, '-', {
					xtype : 'button',
					width : '40',
					text : '召测',
					handler : function() {
						ctrlCheckStaff(getCallParaAjax, '');
					}
				}, '-', {
					xtype : 'button',
					width : '70',
					text : '保存召测值',
					handler : function() {
						Ext.MessageBox.confirm('提示', '确定保存召测值吗?', saveInfoAjax);
					}
				}, '-', new Ext.SplitButton({
							menu : new Ext.menu.Menu({
										items : [{
											text : '保存参数',
											handler : function() {
												Ext.MessageBox.confirm('提示',
														'确定保存页面参数吗?',
														savePageInfo);
											}
										}, {
											text : '后台下发',
											handler : function() {
												Ext.MessageBox.confirm('提示',
														'确定后台下发参数吗?',
														saveTBgTaskInfo);
											}
										}]
									})

						})]);
		return itemArray;
	}

	// 生成用于包裹tabPanel的panel
	var panelForTab = new Ext.Panel({
				title : '',
				border : false,
				id : 'panelForTab',
				region : 'center',
				layout : 'fit',
				items : [],
				bbar : []
			});

	// 参数召测界面生成
	function getCallParaAjax() {
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		var protocolCode = Ext.getCmp('gyComboBox').value;
		// 召测终端集合
		var termialArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
			} else {
				var protocolName = Ext.getCmp('gyComboBox').getStore()
						.getById(recs[i].data.protocolCode).data.protocolName;
				Ext.Msg.alert('', '所选终端规约类型【' + protocolName + '】与设置规约类型不一致！');
				return;
			}
		}
		if (termialArray.length == 0) {
			Ext.Msg.alert('', "请选择终端");
			return;
		}
		// 从title中取FN号
		var protNO = Ext.getCmp('tpTbpanel').getActiveTab().id;

		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryPortocolItem.action',
					params : {
						// 获取当前table页的ID
						// proNO : protNO.replace(/04/, '0A')
						proNO : protNO
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						if (result.bcpi.length > 0) {
							showCallPara(result.bcpi);
						} else {
							callInfoAjax('1');
						}
					}

				});
	}

	// 参数召测Ajax事件
	function callInfoAjax(val) {
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		var protocolCode = Ext.getCmp('gyComboBox').value;

		// 召测终端集合
		var termialArray = new Array();
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
			} else {
				var protocolName = Ext.getCmp('gyComboBox').getStore()
						.getById(recs[i].data.protocolCode).data.protocolName;
				Ext.Msg.alert('', '所选终端规约类型【' + protocolName + '】与设置规约类型不一致！');
				return;
			}
		}
		if (termialArray.length == 0) {
			Ext.Msg.alert('', "请选择终端");
			return;
		}
		// 从title中取FN号
		var fnStr = Ext.getCmp('tpTbpanel').getActiveTab().id
		var fn = null;
		if (fnStr.length == 5) {
			fn = parseInt(fnStr.substring(3, 5), 16);
		} else {
			Ext.Msg.alert('', '规约项不正确');
			return;
		}
		// 根据数据类型设定PN值
		var pn = new Array();
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		// 22测量点数据
		if (dataType == 22 || dataType == 24) {
			var checkValue = Ext.getCmp('tpmpcbg').getValue();
			var checkValue2 = pnTotal.pn;
			for (var i = 0; i < checkValue2.length; i++) {
				checkValue = checkValue.concat(checkValue2[i]);
			}
			// var checkValue = checkValue2.length == 0 ? checkValue1 :
			// checkValue1.concat(checkValue2);
			// var checkValue = checkValue2.length > 0 ? checkValue2 :
			// checkValue1;
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
		var bcpi = new Array();
		if (val != 1) {
			// 取召测类型
			var store = Ext.getCmp('callParaGrid').getStore();
			for (var i = 0; i < store.getTotalCount(); i++) {
				bcpi[i] = {};
				bcpi[i].protItemNo = store.getAt(i).data.protItemNo;
				bcpi[i].defaultVaule = store.getAt(i).data.dataValue;
				if (!Ext.isEmpty(store.getAt(i).data.dataValue)) {
					bcpi[i].defaultVaule = store.getAt(i).data.dataValue;
				} else {
					Ext.Msg
							.alert("", store.getAt(i).data.protItemName
											+ "值未输入");
					return;
				}
			}
			callParaPanel.hide();
		}
		var taskSecond = Ext.Ajax.timeout / 1000;
		Ext.Ajax.timeout = (taskSecond + 20) * 1000
		var ov = true;
		h_taskTime(taskSecond, function() {
					ov = false;
				});
		Ext.Ajax.request({
			url : './runman/feildman/terminalparaset!fetchPara.action',
			params : {
				// 获取当前table页的ID
				fn : fn,
				pn : pn,
				taskSecond : taskSecond,
				tmnlAssetNoArr : termialArray,
				jsonData : Ext.encode(bcpi)
			},
			success : function(response) {
				if (!ov) {
					Ext.Msg.alert('提示', '请求超时');
					unlockGrid();
					return;
				}
				var result = Ext.decode(response.responseText);
				if (result.mpflg == 4) {
					Ext.Msg.alert('', "前置集群通信中断");
					return;
				}
				if (result.mpflg == 5) {
					Ext.Msg.alert('', "前置集群服务中断");
					return;
				}
				if (result.mpflg == 2) {
					Ext.Msg.alert('', result.terminalNo + result.mpflg
									+ "测量点不存在");
					return;
				}
				if (result.tmnlflg == 1) {
					Ext.Msg.alert('', "请选择终端");
					return;
				}
				if (result.fiBeanList.length > 0) {

				}
				var store = Ext.getCmp('tpresultGrid').getStore();
				for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
					var rec = storeForPage.getById(result.tmnlAssetNoArr[i]);
					rec.set('status', result.status[i]);
					rec.set('dataDetails', result.fiBeanList[i]);
					rec.commit();
				}
				store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				// 更新下发状态
				if (Ext.getCmp('tpselectAllcb').checked) {
					unlockGrid();
					store.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
				} else {
					for (var i = 0; i < result.fiBeanList.length; i++) {
						var rec = store.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						// var detailData = new Array();
						// detailData[0] = rec.get('dataDetails')[0];
						// detailData[1] = result.fiBeanList[i];
						rec.set('dataDetails', result.fiBeanList[i]);
						rec.commit();
					}
				}
				overFlat = true;
				Ext.Ajax.timeout = 30000;
				// Ext.getCmp('tpresultGrid').doLayout();
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

	// grid解锁
	function unlockGrid() {
		resultSm.unlock();
		resultGrid.onEnable();
		resultGrid.getBottomToolbar().enable();
		Ext.getCmp('tpselectAllcb').setValue(false);
	}

	// grid解锁
	function lockGrid() {
		resultSm.lock();
		resultGrid.onDisable();
		resultGrid.getBottomToolbar().disable();
	}

	// 保存召测值Ajax事件
	function saveInfoAjax(btn) {
		if (btn == 'no') {
			return;
		}
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 召测数据类型
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;
		if (recs.length == 0) {
			Ext.Msg.alert('', '请选择终端');
			return;
		}
		for (var i = 0; i < recs.length; i++) {
			var recData = recs[i].json;
			// 构造json数据源对象
			if (recs[i].data.dataDetails.length > 0) {
				var fetchData = recs[i].data.dataDetails;
				// var dbData = recs[i].data.dataDetails[0];
				for (var j = 0; j < fetchData.length; j++) {
					gridData[indexNum] = {};
					gridData[indexNum].tmnlAssetNo = fetchData[j].tmnlAssetNo;
					gridData[indexNum].protItemNo = fetchData[j].protItemNo;
					gridData[indexNum].blockSn = fetchData[j].blockSn;
					gridData[indexNum].mpSn = fetchData[j].mpSn;
					gridData[indexNum].innerBlockSn = fetchData[j].innerBlockSn;
					gridData[indexNum].currentValue = fetchData[j].currentValue;
					gridData[indexNum].statusCode = fetchData[j].statusCode;
					gridData[indexNum].saveTime = new Date();
					gridData[indexNum].staffNo = 'test';
					indexNum++;
				};
			} else {
				Ext.Msg.alert('', '第' + (i + 1) + '个选中终端无召测数据');
				return;
			}
		}
		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!saveTerminalInfo.action',

					params : {
						dataType : dataType,
						jsonData : Ext.encode(gridData)
					},
					success : function(response) {
						if (!ov) {
							Ext.Msg.alert('提示', '请求超时');
							unlockGrid();
							return;
						}
						if (Ext.getCmp('tpselectAllcb').checked) {
							unlockGrid();
						}
						Ext.Msg.alert('', '数据保存成功');
						overFlat = true;
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

	// 保存召测值Ajax事件CallValue
	function saveCallValueAjax() {
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 召测数据类型
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;
		if (recs.length == 0) {
			Ext.Msg.alert('', '请选择终端');
			return;
		}
		for (var i = 0; i < recs.length; i++) {
			var recData = recs[i].json;
			// 构造json数据源对象
			if (recs[i].data.dataDetails.length > 0) {
				var fetchData = recs[i].data.dataDetails;
				// var dbData = recs[i].data.dataDetails[0];
				for (var j = 0; j < fetchData.length; j++) {
					gridData[indexNum] = {};
					gridData[indexNum].tmnlAssetNo = fetchData[j].tmnlAssetNo;
					gridData[indexNum].protItemNo = fetchData[j].protItemNo;
					gridData[indexNum].blockSn = fetchData[j].blockSn;
					gridData[indexNum].mpSn = fetchData[j].mpSn;
					gridData[indexNum].innerBlockSn = fetchData[j].innerBlockSn;
					gridData[indexNum].callValue = fetchData[j].currentValue;
					gridData[indexNum].statusCode = fetchData[j].statusCode;
					gridData[indexNum].saveTime = new Date();
					gridData[indexNum].staffNo = 'test';
					indexNum++;
				};
			} else {
				Ext.Msg.alert('', '所选终端无召测值!');
				return;
			}
		}
		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!saveCallValue.action',

					params : {
						dataType : dataType,
						jsonData : Ext.encode(gridData)
					},
					success : function(response) {
						if (!ov) {
							Ext.Msg.alert('提示', '请求超时');
							unlockGrid();
							return;
						}
						if (Ext.getCmp('tpselectAllcb').checked) {
							unlockGrid();
						}
						Ext.Msg.alert('', '数据保存成功');
						overFlat = true;
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
	function savePageInfo(btn) {
		if (btn == 'no') {
			return;
		}
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 下发终端集合
		var termialArray = new Array();

		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;
		// 从title中取FN号
		var fnStr = Ext.getCmp('tpTbpanel').getActiveTab().id
		var fn = null;
		if (fnStr.length == 5) {
			fn = parseInt(fnStr.substring(3, 5), 16);
		} else {
			Ext.Msg.alert('', '规约项不正确')
			return;
		}
		// 根据数据类型设定PN值
		var pn = new Array();
		var protocolCode = Ext.getCmp('gyComboBox').value;
		// var protocolName = Ext.getCmp('gyComboBox').lastSelectionText;
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		var dataFlag = Ext.getCmp('tpfetchcb').checked;

		// 保存页面值
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
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
		var store = Ext
				.getCmp('gp' + Ext.getCmp('tpTbpanel').getActiveTab().id)
				.getStore();
		for (var i = 0; i < recs.length; i++) {
			var recData = recs[i].json;
			gridData[i] = new Array();
			// 构造json数据源对象
			// var dbData = recs[i].data.dataDetails[0];
			for (var j = 0; j < store.getCount(); j++) {
				gridData[i][j] = {};
				gridData[i][j].protItemNo = store.getAt(j).data.protItemNo;
				gridData[i][j].tmnlAssetNo = recs[i].data.tmnlAssetNo;
				if (!Ext.isEmpty(store.getAt(j).data.dataValue)) {
					gridData[i][j].currentValue = store.getAt(j).data.dataValue;
				} else {
					Ext.Msg
							.alert("", store.getAt(j).data.protItemName
											+ "值未输入");
					return;
				}
				gridData[i][j].saveTime = new Date();
				gridData[i][j].blockSn = store.getAt(j).data.index;
				gridData[i][j].innerBlockSn = store.getAt(j).data.protItemSn;
			};
		}
		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!savePageInfo.action',
					params : {
						fn : fn,
						pn : pn,
						dataFlag : dataFlag,
						dataType : dataType,
						// 获取当前table页的ID(规约项编码)
						proNO : Ext.getCmp('tpTbpanel').getActiveTab().id,
						tmnlAssetNoArr : termialArray,
						jsonData : Ext.encode(gridData)
					},
					success : function(response) {
						if (!ov) {
							Ext.Msg.alert('提示', '请求超时');
							unlockGrid();
							return;
						}
						if (Ext.getCmp('tpselectAllcb').checked) {
							unlockGrid();
						}
						var result = Ext.decode(response.responseText);
						if (result.mpflg == 1) {
							Ext.Msg.alert('', '数据保存成功 ，测量点数据主表中不存在的测量点数据未保存');
						} else {
							Ext.Msg.alert('', '数据保存成功');
						}

						overFlat = true;
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

	// 保存后台下发参数Ajax事件
	function saveTBgTaskInfo(btn) {
		if (btn == 'no') {
			return;
		}
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 下发终端集合
		var termialArray = new Array();

		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;
		// 从title中取FN号
		var fnStr = Ext.getCmp('tpTbpanel').getActiveTab().id
		var fn = null;
		if (fnStr.length == 5) {
			fn = parseInt(fnStr.substring(3, 5), 16);
		} else {
			Ext.Msg.alert('', '规约项不正确')
			return;
		}
		// 根据数据类型设定PN值
		var pn = new Array();
		var protocolCode = Ext.getCmp('gyComboBox').value;
		// var protocolName = Ext.getCmp('gyComboBox').lastSelectionText;
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		var dataFlag = Ext.getCmp('tpfetchcb').checked;

		// 保存页面值
		for (var i = 0; i < recs.length; i++) {
			if (recs[i].data.protocolCode == protocolCode) {
				termialArray[i] = recs[i].data.tmnlAssetNo;
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
		if (!dataFlag) {
			var store = Ext.getCmp('gp'
					+ Ext.getCmp('tpTbpanel').getActiveTab().id).getStore();
			for (var i = 0; i < recs.length; i++) {
				var recData = recs[i].json;
				gridData[i] = new Array();
				// 构造json数据源对象
				// var dbData = recs[i].data.dataDetails[0];
				for (var j = 0; j < store.getCount(); j++) {
					gridData[i][j] = {};
					gridData[i][j].protItemNo = store.getAt(j).data.protItemNo;
					gridData[i][j].tmnlAssetNo = recs[i].data.tmnlAssetNo;
					if (!Ext.isEmpty(store.getAt(j).data.dataValue)) {
						gridData[i][j].currentValue = store.getAt(j).data.dataValue;
					} else {
						Ext.Msg.alert("", store.getAt(j).data.protItemName
										+ "值未输入");
						return;
					}
					gridData[i][j].saveTime = new Date();
					gridData[i][j].blockSn = store.getAt(j).data.index;
					gridData[i][j].innerBlockSn = store.getAt(j).data.protItemSn;
				};
			}
		}

		var ov = true;
		h_taskTime(20, function() {
					ov = false;
				});
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!saveTBgTaskInfo.action',
					params : {
						fn : fn,
						pn : pn,
						dataFlag : dataFlag,
						dataType : dataType,
						// 获取当前table页的ID(规约项编码)
						proNO : Ext.getCmp('tpTbpanel').getActiveTab().id,
						tmnlAssetNoArr : termialArray,
						jsonData : Ext.encode(gridData)
					},
					success : function(response) {
						if (!ov) {
							Ext.Msg.alert('提示', '请求超时');
							unlockGrid();
							return;
						}
						if (Ext.getCmp('tpselectAllcb').checked) {
							unlockGrid();
						}
						var result = Ext.decode(response.responseText);
						if (result.mpflg == 1) {
							Ext.Msg.alert('', '数据保存成功 ，测量点数据主表中不存在的测量点数据未保存');
						} else {
							Ext.Msg.alert('', '数据保存成功');
						}

						overFlat = true;
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

	// 参数下发Ajax事件
	function sendInfoAjax(btn) {
		if (btn == 'no') {
			return;
		}
		var recs;
		if (Ext.getCmp('tpselectAllcb').checked) {
			recs = storeForPage.getRange(0, storeForPage.getCount());
		} else {
			var selectModel = Ext.getCmp('tpresultGrid').getSelectionModel();
			recs = selectModel.getSelections();
		}
		// 下发终端集合
		var termialArray = new Array();

		// 构造json数据源对象
		var gridData = new Array();
		var indexNum = 0;

		// 从title中取FN号
		var fnStr = Ext.getCmp('tpTbpanel').getActiveTab().id
		var fn = null;
		if (fnStr.length == 5) {
			fn = parseInt(fnStr.substring(3, 5), 16);
		} else {
			Ext.Msg.alert('', '规约项不正确')
			return;
		}
		// 根据数据类型设定PN值
		var pn = new Array();
		var protocolCode = Ext.getCmp('gyComboBox').value;
		// var protocolName = Ext.getCmp('gyComboBox').lastSelectionText;
		var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
		var dataFlag = Ext.getCmp('tpfetchcb').checked;
		// 下发数据库值
		if (dataFlag) {
			for (var i = 0; i < recs.length; i++) {
				if (recs[i].data.protocolCode == protocolCode) {
					termialArray[i] = recs[i].data.tmnlAssetNo;
				} else {
					var protocolName = Ext.getCmp('gyComboBox').getStore()
							.getById(recs[i].data.protocolCode).data.protocolName;
					Ext.Msg.alert('', '所选终端规约类型【' + protocolName
									+ '】与设置规约类型不一致！');
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
			var taskSecond = Ext.Ajax.timeout / 1000;
			Ext.Ajax.timeout = (taskSecond + 20) * 1000
			var ov = true;
			h_taskTime(taskSecond, function() {
						ov = false;
					});
			Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!sendDBPara.action',
				params : {
					taskSecond : taskSecond,
					fn : fn,
					pn : pn,
					// 获取当前table页的ID(规约项编码)
					proNO : Ext.getCmp('tpTbpanel').getActiveTab().id,
					dataType : dataType,
					tmnlAssetNoArr : termialArray
				},
				success : function(response) {
					if (!ov) {
						Ext.Msg.alert('提示', '请求超时');
						unlockGrid();
						return;
					}
					var result = Ext.decode(response.responseText);
					if (result.tmnlflg == 1) {
						Ext.Msg.alert('', "请选择终端");
						return;
					}
					var store = Ext.getCmp('tpresultGrid').getStore();
					for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
						var rec = storeForPage
								.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						rec.commit();
						// getSendIfno(result.tmnlAssetNoArr[i]);
					}
					store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
					// 更新下发状态
					if (Ext.getCmp('tpselectAllcb').checked) {
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
					overFlat = true;
					Ext.Ajax.timeout = 30000;
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
		} else {
			// 下发页面值
			for (var i = 0; i < recs.length; i++) {
				if (recs[i].data.protocolCode == protocolCode) {
					termialArray[i] = recs[i].data.tmnlAssetNo;
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
			var store = Ext.getCmp('gp'
					+ Ext.getCmp('tpTbpanel').getActiveTab().id).getStore();
			for (var i = 0; i < recs.length; i++) {
				var recData = recs[i].json;
				gridData[i] = new Array();
				// 构造json数据源对象
				// var dbData = recs[i].data.dataDetails[0];
				for (var j = 0; j < store.getCount(); j++) {
					gridData[i][j] = {};
					gridData[i][j].protItemNo = store.getAt(j).data.protItemNo;
					gridData[i][j].tmnlAssetNo = recs[i].data.tmnlAssetNo;
					if (Ext.isEmpty(store.getAt(j).data.dataValue)) {
						Ext.Msg.alert("", store.getAt(j).data.protItemName
										+ "值未输入");
						return;
					} else {
						gridData[i][j].currentValue = store.getAt(j).data.dataValue;
					}

					// if (dbData[j]) {
					// gridData[i][j].historyValue =
					// Ext.decode(dbData[j]).currentValue;
					// }
					gridData[i][j].saveTime = new Date();
					gridData[i][j].blockSn = store.getAt(j).data.index;
					gridData[i][j].innerBlockSn = store.getAt(j).data.protItemSn;
					// gridData[i][j].staffNo = pSysUser.staffNo;
					// gridData[i][j].staffNo = 'test';
				};
			}
			var taskSecond = Ext.Ajax.timeout / 1000;
			Ext.Ajax.timeout = (taskSecond + 20) * 1000
			var ov = true;
			h_taskTime(taskSecond, function() {
						ov = false;
					});
			Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!sendPagePara.action',
				params : {
					taskSecond : taskSecond,
					fn : fn,
					pn : pn,
					dataFlag : dataFlag,
					dataType : dataType,
					// 获取当前table页的ID(规约项编码)
					proNO : Ext.getCmp('tpTbpanel').getActiveTab().id,
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
					if (result.mpflg == 4) {
						Ext.Msg.alert('', "前置集群服务中断");
						return;
					}
					if (result.mpflg == 5) {
						Ext.Msg.alert('', "前置集群通信中断");
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
					var store = Ext.getCmp('tpresultGrid').getStore();
					for (var i = 0; i < result.tmnlAssetNoArr.length; i++) {
						var rec = storeForPage
								.getById(result.tmnlAssetNoArr[i]);
						rec.set('status', result.status[i]);
						rec.commit();
						// getSendIfno(result.tmnlAssetNoArr[i]);
					}
					store.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
					if (Ext.getCmp('tpselectAllcb').checked) {
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
					// Ext.getCmp('tpresultGrid').doLayout();
					overFlat = true;
					Ext.Ajax.timeout = 30000;
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
	}

	// 获取操作对象用户信息
	new LeftTreeListener({
		modelName : '终端参数设置',
		processUserGridSelect : getTermnInfo,
		processClick : getNodeInfo,
		processUserGridAllSelect : function(r) {
			var protocolCode = Ext.getCmp('gyComboBox').value;
			var store = r.getStore();
			var tmnlAssetNoArr = new Array();
			var n = 0;
			for (var i = 0; i < store.getCount(); i++) {
				var rec = store.getAt(i);
				if (rec.data.protocolCode == protocolCode) {
					tmnlAssetNoArr[n++] = rec.data.tmnlAssetNo;
				}
			};
			if (tmnlAssetNoArr.length == 0) {
				Ext
						.getCmp('tpTtLab')
						.setText(
								"<font font-weight:bold;>备选用户</font><font color='red'>【所选终端与页面规约一致的个数为0】</font>",
								false);
				return;
			}
			Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!queryTmnlArrList.action',
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
						// 供电单位
						data[i][0] = result.tmnlList[i].orgName;
						// 用户编号
						data[i][1] = result.tmnlList[i].consNO;
						// 用户名称
						data[i][2] = result.tmnlList[i].consName;
						// 终端地址
						data[i][3] = result.tmnlList[i].terminalAddr;
						// 数据索引
						data[i][4] = result.tmnlList[i].tmnlAssetNo;
						// 通信状态
						// data[0][5] = '1';
						// 终端资产编号
						data[i][6] = result.tmnlList[i].tmnlAssetNo;
						// 数据明细
						data[i][8] = result.tmnlList[i].protocolCode
					}
					storeForPage.loadData(data, true);
					var rsStore = Ext.getCmp('tpresultGrid').getStore();

					rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
					rsStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
					resultSm.selectAll();
					Ext.getCmp('tpresultGrid').doLayout();
				}
			});
		}
	});
	function getTermnInfo(sm, row, record) {
		var protocolCode = Ext.getCmp('gyComboBox').value;
		if (record.data.protocolCode !== protocolCode) {
			var protocolName = Ext.getCmp('gyComboBox').getStore()
					.getById(record.data.protocolCode).data.protocolName;
			Ext.Msg.alert('', '所选终端规约类型【' + protocolName + '】与设置规约类型不一致！');
		} else {
			// 获取数据库参数值
			Ext.Ajax.request({
				url : './runman/feildman/terminalparaset!queryTmnlList.action',
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
						// 供电单位
						data[i][0] = result.tmnlList[i].orgName;
						// 用户编号
						data[i][1] = result.tmnlList[i].consNO;
						// 用户名称
						data[i][2] = result.tmnlList[i].consName;
						// 终端地址
						data[i][3] = result.tmnlList[i].terminalAddr;
						// 数据索引
						data[i][4] = result.tmnlList[i].tmnlAssetNo;
						// 通信状态
						// data[0][5] = '1';
						// 终端资产编号
						data[i][6] = result.tmnlList[i].tmnlAssetNo;
						// 数据明细
						data[i][8] = result.tmnlList[i].protocolCode
					}
					storeForPage.loadData(data, true);
					var rsStore = Ext.getCmp('tpresultGrid').getStore();

					rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
					rsStore.load({
								params : {
									start : 0,
									limit : DEFAULT_PAGE_SIZE
								}
							});
					resultSm.selectAll();
					// // 数据加载
					// Ext.getCmp('tpresultGrid').getStore().loadData(
					// data, true);
					Ext.getCmp('tpresultGrid').doLayout();
				}
			});
		}

	}

	function storeToArray(val) {
		var data = new Array();
		for (var i = 0; i < val.getCount(); i++) {
			data[i] = new Array();
			// 供电单位
			data[i][0] = val.getAt(i).data.bureauNo;
			// 用户编号
			data[i][1] = val.getAt(i).data.userNumber;
			// 用户名称
			data[i][2] = val.getAt(i).data.userName;
			// 终端地址
			data[i][3] = val.getAt(i).data.terminalAddr;
			// 数据索引
			data[i][4] = val.getAt(i).data.dataIndex;
			// 通信状态
			data[i][5] = val.getAt(i).data.status;
			// 终端资产编号
			data[i][6] = val.getAt(i).data.tmnlAssetNo;
			// 数据明细
			data[i][7] = val.getAt(i).data.dataDetails
			// 规约编码
			data[i][8] = val.getAt(i).data.protocolCode
		}
		return data;
	}

	// 获取节点用户信息
	function getNodeInfo(p, node, e) {
		var obj = node.attributes.attributes;
		var nodeType = node.attributes.type;
		var nodeValue = null;
		var protocolCode = Ext.getCmp('gyComboBox').value;
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
			url : './runman/feildman/terminalparaset!queryTmnlList.action',
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
					Ext
							.getCmp('tpTtLab')
							.setText(
									"<font font-weight:bold;>备选用户</font><font color='red'>【该节点与页面规约一致的终端个数为0】</font>",
									false);
					// Ext.Msg.alert('', '该节点不存在与页面规约一致的终端');
					return;
				}
				Ext.getCmp('tpTtLab').setText(
						"<font font-weight:bold;>备选用户</font>", false);
				for (var i = 0; i < result.tmnlList.length; i++) {
					data[i] = new Array();
					// 供电单位
					data[i][0] = result.tmnlList[i].orgName;
					// 用户编号
					data[i][1] = result.tmnlList[i].consNO;
					// 用户名称
					data[i][2] = result.tmnlList[i].consName;
					// 终端地址
					data[i][3] = result.tmnlList[i].terminalAddr;
					// 数据索引
					data[i][4] = result.tmnlList[i].tmnlAssetNo;
					// 通信状态
					// data[0][5] = '1';
					// 终端资产编号
					data[i][6] = result.tmnlList[i].tmnlAssetNo;
					// 数据明细
					data[i][8] = result.tmnlList[i].protocolCode
				}
				storeForPage.loadData(data, true);
				var rsStore = Ext.getCmp('tpresultGrid').getStore();
				rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
				rsStore.load({
							params : {
								start : 0,
								limit : DEFAULT_PAGE_SIZE
							}
						});
				resultSm.selectAll();
				// 数据加载
				// Ext.getCmp('tpresultGrid').getStore().loadData(data,
				// true);
				// Ext.getCmp('tpresultGrid').doLayout();
			}
		});
	}

	function updateTermnIfno() {
		var rec = storeForPage.getRange(0, storeForPage.getCount())
		var detailData = new Array();
		for (var i = 0; i < rec.length; i++) {
			rec[i].set('status', '');
			rec[i].set('dataDetails', detailData);
			rec[i].commit();
		}
		var rsStore = Ext.getCmp('tpresultGrid').getStore();
		rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
		rsStore.load({
					params : {
						start : 0,
						limit : DEFAULT_PAGE_SIZE
					}
				});
		// for (var i = 0; i < store.getCount(); i++) {
		// var recshow = store.getAt(i);
		// recshow.set('status', 1);
		// recshow.set('dataDetails', detailData);
		// recshow.commit();
		// }
	}

	// tabPanel数据加载Ajax事件
	function creatGridAjax() {
		// if (Ext.getCmp('gp' + Ext.getCmp('tpTbpanel').getActiveTab().id)
		// .getStore().getCount() == 0) {
		Ext.Ajax.request({
					url : './runman/feildman/terminalparaset!queryPortocolItem.action',
					params : {
						// 获取当前table页的ID
						proNO : Ext.getCmp('tpTbpanel').getActiveTab().id
					},
					success : gridResponse
				});
		// } else {
		// Ext.getCmp('tpTbpanel').getActiveTab().doLayout();
		// }
	}

	// tabPanel数据加载Ajax事件的响应
	function gridResponse(response) {
		var result = Ext.decode(response.responseText);
		var data = new Array();
		var tpresultGrid = Ext.getCmp('tpresultGrid');
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
		Ext.getCmp('tpstoretext').setValue(Ext.encode(data));
		updateTermnIfno();
		// 加载grid数据
		Ext.getCmp('gp' + Ext.getCmp('tpTbpanel').getActiveTab().id).getStore()
				.loadData(initBolckData(data));
		Ext.getCmp('tpTbpanel').getActiveTab().doLayout();
	}

	function creatCallPanel() {
		var userCm = new Ext.grid.ColumnModel({
					defaults : {
						sortable : false,
						remoteSort : false
					},
					columns : [{
								header : '序号',
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

		// 创建gridPanel
		var userGrid = new Ext.grid.EditorGridPanel({
			id : 'callParaGrid',
			store : store,
			cm : userCm,
			border : false,
			listeners : {
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
					var data = Ext.getCmp('cpstoretext').getValue();
					var dbData = Ext.decode(data);

					var blockRule = val.record.data.blockRule;
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
					}
				}
			},
			// clicksToEdit : 2,
			bbar : [{
						width : '70',
						xtype : 'button',
						text : '确定',
						handler : callInfoAjax
					}, {
						xtype : 'textfield',
						id : 'cpstoretext',
						hidden : true
					}],
			autoScroll : true
		});
		var callparapanel = new Ext.Panel({
					autoScroll : true,
					layout : 'fit',
					border : true,
					buttonAlign : "center", // 按钮的位置
					closeAction : "hide", // 将窗体隐藏而并不销毁
					items : [userGrid]
				});
		return callparapanel;
	}

	this.callParaPanel = new Ext.Window({
				title : '该召测数据存在块循环，请输入块信息',
				width : 750,
				height : 460,
				layout : 'fit',
				closeAction : 'hide',
				resizable : false,
				items : [creatCallPanel()]
			});
	var tpsQueryPanel = new Ext.Panel({
		height : 90,
		region : 'north',
		layout : "column",
		baseCls : "x-plain",
		style : "padding-top:5px",
		items : [{
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "用户编号",
								name : 'consNo',
								id : 'tpsConsNo',
								readOnly : true,
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .4,
					layout : "form",
					labelWidth : 80,
					defaultType : "textfield",
					baseCls : "x-plain",
					items : [{
								fieldLabel : "终端资产号",
								name : 'tmnlAssetNo',
								id : 'tpsTmnlAssetNo',
								readOnly : true,
								labelStyle : "text-align:right;width:80;",
								labelSeparator : "",
								width : 120
							}]
				}, {
					columnWidth : .1,
					layout : "form",
					xtype : "button",
					baseCls : "x-plain",
					text : "查询",
					width : 50,
					listeners : {
						"click" : function() {
							var protNO = Ext.getCmp('tpTbpanel').getActiveTab().id;
							var tmnlAssetNo = Ext.getCmp("tpsTmnlAssetNo")
									.getValue();
							var mpSn = Ext.getCmp("tpmpcbg2").getValue();
							var blockSn = Ext.getCmp("tpmpcbg3").getValue();
							// 根据数据类型设定PN值
							var pn = new Array();
							var dataType = Ext.getCmp('tpRadioGroup')
									.getValue().inputValue;
							// 根据数据类型设定blockSn值
							var sn = new Array();
							// 22测量点数据
							if (dataType == 22 || dataType == 24) {
								var checkValueX = Ext.getCmp('tpmpcbg2')
										.getValue();
								var checkValueY = Ext.getCmp("cbgwbox")
										.getValue();
								var mycheckValue = checkValueX
										.concat(checkValueY);
								if (mycheckValue.length > 0) {
									for (var i = 0; i < mycheckValue.length; i++) {
										pn[i] = mycheckValue[i].boxLabel;
									}
								} else {
									Ext.Msg.alert('', "请选择测量点号");
									return;
								}
							} else {
								pn[0] = 0;
								var checkValueSub = Ext.getCmp("blockbox")
										.getValue();
								var blockValue = Ext.getCmp('tpmpcbg3')
										.getValue();
								var blockcheckValue = checkValueSub
										.concat(blockValue);
								if (blockcheckValue.length > 0) {
									for (var i = 0; i < blockcheckValue.length; i++) {
										sn[i] = blockcheckValue[i].boxLabel;
									}
								} else {
									Ext.Msg.alert('', "请选择块序号");
									return;
								}
							}

							callValueStore.load({
										params : {
											start : 0,
											limit : DEFAULT_PAGE_SIZE,
											dataType : dataType,
											protNO : protNO,
											tmnlAssetNo : tmnlAssetNo,
											mpSn : pn,
											blockSn : sn
										}
									});
						}
					}
				}, {
					columnWidth : .8,
					layout : "form",
					baseCls : "x-plain",
					items : [{
								xtype : 'checkboxgroup',
								id : 'tpmpcbg2',
								disabled : true,
								columns : [80, 80, 80, 80, 80],
								items : [{
											boxLabel : '0',
											name : '0',
											checked : true
										}, {
											boxLabel : '1',
											name : '1',
											checked : false
										}, {
											boxLabel : '2',
											name : '2',
											checked : false
										}, {
											boxLabel : '3',
											name : '3',
											checked : false
										}, {
											boxLabel : '4',
											name : '4',
											checked : false
										}]
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					baseCls : "x-plain",
					items : [{
								id : 'tpmpcbg2Button',
								xtype : 'button',
								disabled : true,
								width : '50',
								text : '更多测量号',
								handler : showMpCheckBox
							}]
				}, {
					columnWidth : .8,
					layout : "form",
					baseCls : "x-plain",
					items : [{
								xtype : 'checkboxgroup',
								id : 'tpmpcbg3',
								labelWidth : 1,
								columns : [80, 80, 80, 80, 80],
								items : [{
											boxLabel : '0',
											name : 'cb-horiz-0',
											checked : true
										}, {
											boxLabel : '1',
											name : 'cb-horiz-1',
											checked : false
										}, {
											boxLabel : '2',
											name : 'cb-horiz-2',
											checked : false
										}, {
											boxLabel : '3',
											name : 'cb-horiz-3',
											checked : false
										}, {
											boxLabel : '4',
											name : 'cb-horiz-4',
											checked : false
										}]
							}]
				}, {
					columnWidth : .2,
					layout : "form",
					baseCls : "x-plain",
					items : [{
								xtype : 'button',
								id : 'tpmpcbg3Button',
								width : '50',
								text : '更多块序号',
								handler : showBlockCheckBox
							}]
				}]
	});
	this.dataDetailPanel = new Ext.Window({
				title : '明细数据',
				width : 750,
				height : 460,
				layout : 'border',
				closeAction : 'hide',
				resizable : false,
				items : [tpsQueryPanel, getDataDetails()]
			});

	// 测量点编辑弹出的窗口----------------------------------------------------------------------------------------------------------------------------
	var cldCheckBoxs = new Ext.form.CheckboxGroup({
				xtype : 'checkboxgroup',
				autoHeight : true,
				id : 'cbgwbox',
				width : 340,
				columns : 5,
				items : [{
							boxLabel : '5',
							name : '5'
						}, {
							boxLabel : '6',
							name : '6'
						}, {
							boxLabel : '7',
							name : '7'
						}, {
							boxLabel : '8',
							name : '8'
						}, {
							boxLabel : '9',
							name : '9'
						}]
			});

	this.mpCheckBoxPanel = new Ext.Window({
				title : '测量点',
				frame : true,
				width : 380,
				height : 450,
				layout : "form",
				labelWidth : 1,
				autoScroll : true,
				modal : true,
				plain : true, // 设置背景颜色
				resizable : false, // 不可移动
				buttonAlign : "center", // 按钮的位置
				closeAction : "hide", // 将窗体隐藏而并不销毁
				items : [cldCheckBoxs],
				buttons : [{
					iconCls : 'plus',
					width : 45,
					handler : function() {
						var tNum = cldCheckBoxs.items.length;
						var items = cldCheckBoxs.items;
						var columns = cldCheckBoxs.panel.items;
						for (var i = tNum + 5; i < tNum + 10; i = i + 1) {
							var column = columns.itemAt(items.getCount()
									% columns.getCount());
							var checkbox = new Ext.form.Checkbox({
								boxLabel : i,
								name : i
									// ,
									// inputValue : i
								});
							var checkboxItem = column.add(checkbox);
							cldCheckBoxs.items.add(checkboxItem);
						}
						cldCheckBoxs.doLayout();
					}
				}, {
					text : '全选',
					width : 50,
					handler : function() {
						var tNum = cldCheckBoxs.items.length;
						for (var i = 5; i < tNum + 5; i++) {
							cldCheckBoxs.setValue(i, true);
						};
						cldCheckBoxs.doLayout();
					}
				}, {
					text : '清空',
					width : 50,
					handler : function() {
						cldCheckBoxs.reset();
						cldCheckBoxs.doLayout();
					}
				}, {
					text : '确定',
					width : 50,
					handler : function() {
						mpCheckBoxPanel.hide();
						var cbs = cldCheckBoxs.getValue();
						pnTotal.pn = cbs;
						// cldCheckBoxs.reset();
					}
				}]
			});
	// 块序号编辑弹出的窗口----------------------------------------------------------------------------------------------------------------------------
	var blockCheckBoxs = new Ext.form.CheckboxGroup({
				xtype : 'checkboxgroup',
				autoHeight : true,
				id : 'blockbox',
				width : 340,
				columns : 5,
				items : [{
							boxLabel : '5',
							name : '5'
						}, {
							boxLabel : '6',
							name : '6'
						}, {
							boxLabel : '7',
							name : '7'
						}, {
							boxLabel : '8',
							name : '8'
						}, {
							boxLabel : '9',
							name : '9'
						}]
			});
	// 更多块序号
	this.blockCheckBoxPanel = new Ext.Window({
				title : '块序号',
				frame : true,
				width : 380,
				height : 450,
				layout : "form",
				labelWidth : 1,
				autoScroll : true,
				modal : true,
				plain : true, // 设置背景颜色
				resizable : false, // 不可移动
				buttonAlign : "center", // 按钮的位置
				closeAction : "hide", // 将窗体隐藏而并不销毁
				items : [blockCheckBoxs],
				buttons : [{
					iconCls : 'plus',
					width : 45,
					handler : function() {
						var tNum = blockCheckBoxs.items.length;
						var items = blockCheckBoxs.items;
						var columns = blockCheckBoxs.panel.items;
						for (var i = tNum + 5; i < tNum + 10; i = i + 1) {
							var column = columns.itemAt(items.getCount()
									% columns.getCount());
							var checkbox = new Ext.form.Checkbox({
								boxLabel : i,
								name : i
									// ,
									// inputValue : i
								});
							var checkboxItem = column.add(checkbox);
							blockCheckBoxs.items.add(checkboxItem);
						}
						blockCheckBoxs.doLayout();
					}
				}, {
					text : '全选',
					width : 50,
					handler : function() {
						var tNum = blockCheckBoxs.items.length;
						for (var i = 5; i < tNum + 5; i++) {
							blockCheckBoxs.setValue(i, true);
						};
						blockCheckBoxs.doLayout();
					}
				}, {
					text : '清空',
					width : 50,
					handler : function() {
						blockCheckBoxs.reset();
						blockCheckBoxs.doLayout();
					}
				}, {
					text : '确定',
					width : 50,
					handler : function() {
						blockCheckBoxPanel.hide();
						var cbs = blockCheckBoxs.getValue();
						snTotal.pn = cbs;
						// cldCheckBoxs.reset();
					}
				}]
			});

	function getDataDetails() {
		
		callValueStore = new Ext.data.Store({
			// proxy : new Ext.data.MemoryProxy(),
			proxy : new Ext.data.HttpProxy({
				url : './runman/feildman/terminalparaset!queryCallValueBean.action'
			
			}),
			reader : new Ext.data.JsonReader({
						root : 'callValueBeanList',
						totalProperty : 'totalCount'
					}, [{
								name : 'blockSn'
							}, {
								name : 'unit'
							}, {
								name : 'protItemName'
							}, {
								name : 'statusCode'
							}, {
								name : 'currentValue'
							}, {
								name : 'callValue'
							}, {
								name : 'mpSn'
							}])
		});
		// title
		var userCm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
					// header : '序',
					renderer : function(v, p, record, rowIndex) {
						var startRow = 0;
						if (callValueStore && callValueStore.lastOptions
								&& callValueStore.lastOptions.params) {
							startRow = callValueStore.lastOptions.params.start;
						}
						return startRow + rowIndex + 1;
					}
				}), {
			header : '块序号',
			dataIndex : 'blockSn',
			width : 30,
			align : 'center'
		}, {
			header : '参数项',
			dataIndex : 'protItemName',
			width : 100,
			align : 'left'
		}, {
			header : 'PN值',
			dataIndex : 'mpSn',
			width : 30,
			align : 'left'
		}, {
			header : '数据库值',
			dataIndex : 'currentValue',
			width : 30,
			align : 'center'
		}, {
			header : '实时数据',
			dataIndex : 'callValue',
			width : 30,
			align : 'center',
			renderer : function(val, m, r) {
				var html;
				if (r.get('callValue') == r.get('currentValue')) {
					return val;
				} else {
					return "<font color='red';font-weight:bold>" + val
							+ "</font>";
				}
			}
		}, {
			header : '单位',
			dataIndex : 'unit',
			width : 30,
			align : 'left'
		}, {
			header : '状态',
			dataIndex : 'statusCode',
			width : 30,
			align : 'center',
			renderer : function(val) {
//				if (val == '04') {
//					return '下发成功';
//				} else {
//					return '下发不成功';
//				}
				//仅为四川盘龙测试用
				if (val != '00') {
					return '成功';
				} else {
					return '不成功';
				}
			}
		}
		// , {
		// header : '规约项数据编码',
		// dataIndex : 'protItemNo',
		// width : 100,
		// hidden : true
		// }, {
		// header : '块序号',
		// dataIndex : 'blockNo',
		// width : 30,
		// hidden : true
		// }, {
		// header : '块内序号',
		// dataIndex : 'innerBlockNo',
		// width : 30,
		// hidden : true
		// }
		]);
		// 创建gridPanel
		var userGrid = new Ext.grid.GridPanel({
					store : callValueStore,
					cm : userCm,
					region : 'center',
					id : 'tpdetailsGrid',
					// border : false,
					// clicksToEdit : 2,
					autoScroll : true,
					bbar : new Ext.ux.MyToolbar({
								store : callValueStore,
								enableExpAll : true
							})
				});
		// var itempanel = new Ext.Panel({
		// autoScroll : true,
		// //layout : 'fit',
		// region:'center',
		// border : true,
		// items : [userGrid]
		// });

		return userGrid;
	}

	// ComboBox和RadioGroup面板
	var formPanel = new Ext.Panel({
				border : false,
				id : 'ptFormPanel',
				region : 'north',
				layout : 'border',
				split : true,
				height : 250,
				items : [{
							// bodyStyle : 'padding:10px;',
							xtype : 'panel',
							region : 'north',
							height : 40,
							layout : 'table',
							layoutConfig : {
								columns : 2
							},
							border : false,
							items : [{
										layout : 'form',
										labelAlign : 'right',
										bodyStyle : 'padding:10px 10px 10px 10px',
										width : 180,
										border : false,
										labelWidth : 30,
										items : [gyComboBox]
									}, {
										width : 520,
										id : 'panelRGroup',
										layout : 'form',
										border : false,
										items : []
									}]
						}, panelForTab]

			});

	var resultStore = new Ext.data.Store({
				remoteSort : true,
				reader : new Ext.data.ArrayReader({
							idIndex : 6,
							fields : [{
										name : 'bureauNo'
									}, {
										name : 'userNumber'
									}, {
										name : 'userName'
									}, {
										name : 'terminalAddr'
									}, {
										name : 'dataIndex'
									}, {
										name : 'status'
									}, {
										name : 'tmnlAssetNo'
									}, {
										name : 'dataDetails'
									}, {
										name : 'protocolCode'
									}, {
										name : 'protocolData'
									}
							// , {
							// name : 'fetchData'
							// }
							]
						})
			});

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
		dataIndex : 'bureauNo',
		width : 70,
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="供电单位" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '用户编号',
		dataIndex : 'userNumber',
		width : 45,
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户编号" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '用户名称',
		dataIndex : 'userName',
		width : 50,
		align : 'center',
		renderer : function(val) {
			var html = '<div align = "left" ext:qtitle="用户名称" ext:qtip="' + val
					+ '">' + val + '</div>';
			return html;
		}
	}, {
		header : '终端地址',
		dataIndex : 'terminalAddr',
		width : 45,
		align : 'center'
	}, {
		header : '数据明细',
		dataIndex : 'dataIndex',
		width : 40,
		align : 'center',
		renderer : function(val) {
			return "<a href='javascript:' onclick=''>数据明细</a>";
		}
	}, {
		header : '状态',
		dataIndex : 'status',
		width : 55,
		renderer : function(val) {
			switch (val) {
				case '31' :
					return "<font color='green';font-weight:bold>" + '召测成功'
							+ "</font>";
				case '32' :
					return "<font color='red';font-weight:bold>" + '召测失败'
							+ "</font>";
				case '04' :
					return "<font color='green';font-weight:bold>" + '下发成功'
							+ "</font>";
				case '05' :
					return "<font color='red';font-weight:bold>" + '下发失败'
							+ "</font>";
				case '40' :
					return "<font color='red';font-weight:bold>" + '所选测量点不存在'
							+ "</font>";
				case '41' :
					return "<font color='red';font-weight:bold>" + '终端数据库中无数据'
							+ "</font>";
				case '42' :
					return "<font color='red';font-weight:bold>" + '终端不在线'
							+ "</font>";
				case '43' :
					return "<font color='red';font-weight:bold>" + '终端响应超时'
							+ "</font>";
				case '44' :
					return "<font color='red';font-weight:bold>" + '终端无应答'
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
			return "<a href='javascript:'onclick='origFrameQryShow(\""
					+ rec.get('userNumber') + "\",\"" + rec.get('userName')
					+ "\",\"" + rec.get('terminalAddr') + "\");" + "'>原始报文</a>";
		}
	}, {
		header : '终端资产号',
		dataIndex : 'tmnlAssetNo',
		hidden : true
	}
	// , {
	// header : '详细数据',
	// dataIndex : 'dataDetails',
	// hidden : true
	// }
	]);

	// 创建gridPanel
	var resultGrid = new Ext.grid.GridPanel({
		// title : '终端列表',
		id : 'tpresultGrid',
		store : resultStore,
		cm : resultCm,
		border : false,
		sm : resultSm,
		region : 'center',
		tbar : [{
					xtype : 'label',
					id : 'tpTtLab',
					html : "<font font-weight:bold;>备选用户</font>"
				}, {
					xtype : 'tbfill'
				}, '-', {
					xtype : 'checkbox',
					id : 'tpselectAllcb',
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
						if (Ext.getCmp('tpselectAllcb').checked) {
							storeForPage.removeAll(true);
							unlockGrid();
						} else {
							var recs = resultSm.getSelections();
							for (var i = 0; i < recs.length; i = i + 1) {
								storeForPage.remove(storeForPage
										.getById(recs[i].data.tmnlAssetNo));
							}
						}
						var rsStore = Ext.getCmp('tpresultGrid').getStore();
						rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
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
						for (var i = 0; i < storeForPage.getCount(); i++) {
							var statuscode = storeForPage.getAt(i).data.status;
							if (statuscode == '04' || statuscode == '31') {
								storeForPage.remove(storeForPage.getAt(i));
							}
						}
						var rsStore = Ext.getCmp('tpresultGrid').getStore();
						rsStore.proxy = new Ext.ux.data.PagingMemoryProxy(storeToArray(storeForPage));
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
					allStore : storeForPage
				})
	});

	resultGrid.addListener('cellclick', resultcellclick);

	function resultcellclick(resultGrid, rowIndex, columnIndex, e) {
		if (rowIndex >= 0 && columnIndex == 6) {
			var record = resultGrid.getStore().getAt(rowIndex); // Get the
			// Record
			var crrvar = record.get('dataIndex');
			var consNo = record.get('userNumber');
			var tmnlAssetNo = record.get('tmnlAssetNo');
			callValueStore.removeAll();
			dataDetailPanel.show();
			Ext.getCmp("tpsConsNo").setValue(consNo);
			Ext.getCmp("tpsTmnlAssetNo").setValue(tmnlAssetNo);
			var dataType = Ext.getCmp('tpRadioGroup').getValue().inputValue;
			if (dataType == 22 || dataType == 24) {
				Ext.getCmp("tpmpcbg2").setDisabled(false);
				Ext.getCmp("tpmpcbg2Button").setDisabled(false);
				Ext.getCmp("tpmpcbg3").setDisabled(true);
				Ext.getCmp("tpmpcbg3Button").setDisabled(true);
			} else {
				Ext.getCmp("tpmpcbg2").setDisabled(true);
				Ext.getCmp("tpmpcbg2Button").setDisabled(true);
				Ext.getCmp("tpmpcbg3").setDisabled(false);
				Ext.getCmp("tpmpcbg3Button").setDisabled(false);
			}
			saveCallValueAjax();
			cldCheckBoxs.reset();
			blockCheckBoxs.reset();
			Ext.getCmp("tpmpcbg2").reset();
			Ext.getCmp("tpmpcbg3").reset();
		}
	};

	var panel = new Ext.form.FormPanel({
				// title : '终端参数',
				border : false,
				id : 'tpPanel',
				layout : 'border',
				items : [formPanel, resultGrid]
			});
	renderModel(panel, '终端参数设置');
});